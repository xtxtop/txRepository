package cn.com.shopec.mgt.order.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;

import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.common.constants.Constant;
import cn.com.shopec.common.utils.ECCalculateUtils;
import cn.com.shopec.common.utils.ECDateUtils;
import cn.com.shopec.common.utils.ECStringUtils;
import cn.com.shopec.core.car.common.CarConstant;
import cn.com.shopec.core.car.model.Car;
import cn.com.shopec.core.car.model.CarStatus;
import cn.com.shopec.core.car.service.CarService;
import cn.com.shopec.core.car.service.CarStatusService;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.customer.model.OrderComments;
import cn.com.shopec.core.customer.service.OrderCommentsService;
import cn.com.shopec.core.device.model.Device;
import cn.com.shopec.core.device.service.DeviceService;
import cn.com.shopec.core.finace.model.Invoice;
import cn.com.shopec.core.finace.service.InvoiceService;
import cn.com.shopec.core.marketing.model.PricingRule;
import cn.com.shopec.core.marketing.model.PricingRuleCustomizedDate;
import cn.com.shopec.core.marketing.service.PricingRuleService;
import cn.com.shopec.core.member.common.MemberConstant;
import cn.com.shopec.core.order.model.ControlPowerLog;
import cn.com.shopec.core.order.model.Order;
import cn.com.shopec.core.order.model.OrderCountVo;
import cn.com.shopec.core.order.model.OrderFinish;
import cn.com.shopec.core.order.model.OrderMileage;
import cn.com.shopec.core.order.model.OrderStrikeBalance;
import cn.com.shopec.core.order.model.PricingPackOrder;
import cn.com.shopec.core.order.service.ControlPowerLogService;
import cn.com.shopec.core.order.service.OrderFinishService;
import cn.com.shopec.core.order.service.OrderMileageService;
import cn.com.shopec.core.order.service.OrderService;
import cn.com.shopec.core.order.service.OrderStrikeBalanceService;
import cn.com.shopec.core.order.service.PricingPackOrderService;
import cn.com.shopec.core.resource.model.Park;
import cn.com.shopec.core.resource.service.ParkService;
import cn.com.shopec.core.system.model.SysParam;
import cn.com.shopec.core.system.service.SysParamService;
import cn.com.shopec.mgt.common.BaseController;
@Controller
@RequestMapping("/order")
public class OrderController extends BaseController{
	@Resource
	private OrderService orderService;
	@Resource
	private CarService carService;
	@Resource
	private OrderFinishService orderFinishService;
	@Resource
	private CarStatusService carStatusService;
	@Resource
	private ParkService parkService;
	@Resource
	private OrderStrikeBalanceService orderStrikeBalanceService;
	@Resource
	private DeviceService deviceService;
	@Resource
	private SysParamService sysParamService;
	@Resource
	private PricingPackOrderService pricingPackOrderService;
	@Resource
	private ControlPowerLogService controlPowerLogService;
	@Resource
	private  OrderMileageService orderMileageService;
	@Resource
	private PricingRuleService  pricingRuleService;
	@Resource
	private InvoiceService  invoiceService;
	@Resource
	private OrderCommentsService orderCommentsService;
	
	/**
	 * 订单页面
	 * @return
	 */
	@RequestMapping("/toOrderList")
	public String toOrderList(Order order, String today, ModelMap modelMap){
		if(today != null && today.equals("yes")){
			modelMap.put("today", ECDateUtils.formatDate(new Date(), ECDateUtils.Format_Date));
		}
		modelMap.put("order", order);
		return "order/order_list";
	}
	
	/**
	 * 超时订单页面
	 * @return
	 */
	@RequestMapping("/toOverOrderList")
	public String toOverOrderList(){
		return "order/over_order_list";
	}
	/**
	 * 订单结束页面
	 * @return
	 */
	@RequestMapping("/toOrderOver")
	public String toOrderOver(String orderNo,String flag,ModelMap modelMap){
		Order order=orderService.getOrder(orderNo);
		Car car=carService.getCar(order.getCarNo());
		CarStatus carStatus=carStatusService.getCarStatus(order.getCarNo());
		modelMap.put("order", order);
		modelMap.put("car", car);
		modelMap.put("carStatus", carStatus);
		modelMap.put("flag", flag);
		return "order/order_over";
	}
	
	 /*
     * 订单页面分页
     */
    @RequestMapping("/pageListOrder")
    @ResponseBody
    public PageFinder<Order> pageListOrder(@ModelAttribute("order")Order order,Query query){
    	Query q = new Query(query.getPageNo(),query.getPageSize(),order);
    	return orderService.getOrderPagedList(q);
    }
    
    /*
     * 超时订单页面分页
     */
    @RequestMapping("/pageListoverOrder")
    @ResponseBody
    public PageFinder<Order> pageListoverOrder(@ModelAttribute("order")Order order,Query query){
    	Query q = new Query(query.getPageNo(),query.getPageSize(),order);
    	order.setWarningOrder(1);//实际查询代表wariningOrder 1和2的
    	PageFinder<Order> orders= orderService.getOrderPagedListW(q);
    	if(orders.getData() != null &&  orders.getData().size()>0){
    		for (Order o : orders.getData()) {
				Device d=deviceService.getDeviceByCarNo(o.getCarNo());
				if(d != null){
					o.setDeviceNo(d.getTerminalDeviceNo());
				}
				Integer min =(int)((new Date().getTime() - o.getStartBillingTime().getTime()) / (1000 * 60));
				o.setOrderDuration(min);
				CarStatus c=new CarStatus();
				c.setCarNo(o.getCarNo());
				Query qs=new Query(c);
				List<CarStatus> cst= carStatusService.getCarStatusList(qs);
				if(cst!= null && cst.size()>0){
					if(cst.get(0).getCarStatus() != null){
						o.setCarStatus(cst.get(0).getCarStatus());
					}
					
				}
				
				//我的余额
				Query qr = new Query();
				PricingPackOrder ppor = new PricingPackOrder();
				ppor.setPayStatus(1);// 已经支付的
				ppor.setIsAvailable(1);// 可用
				ppor.setNowTime(new Date());
				ppor.setMemberNo(o.getMemberNo());
				qr.setQ(ppor);
				List<PricingPackOrder>  pporList = pricingPackOrderService.getPricingPackOrderListBypo(qr);
				Double poa=0.0;
				Double uoa=0.0;
				for (PricingPackOrder pricingPackOrder : pporList) {
					if(pricingPackOrder.getPackOrderAmount() != null ){
						poa=ECCalculateUtils.add(poa, pricingPackOrder.getPackOrderAmount());
					if(pricingPackOrder.getUseredOrderAmount() != null){
						uoa=ECCalculateUtils.add(uoa,pricingPackOrder.getUseredOrderAmount());
					}
					
					}
				}
				
				o.setMemberBalance(ECCalculateUtils.sub(poa, uoa));
			}
    	}
		return orders;
    }
    //开关动力日志页面
	@RequestMapping("/orderDevice")
	public String orderDevice(String orderNo,ModelMap model) {
//		model.put("carUpWhy", carUpWhy);
//		model.put("carDownWhy", carDownWhy);
		model.addAttribute("orderNo", orderNo);
		return "order/order_powerLog_list";
	}
	/*
     * 开关动力日志页面
     */
    @RequestMapping("/pageListOrderLogs")
    @ResponseBody
    public PageFinder<ControlPowerLog> pageListOrderLog(@ModelAttribute("ControlPowerLog") ControlPowerLog controlPowerLog,Query query){
    	Query q = new Query(query.getPageNo(),query.getPageSize(),controlPowerLog);
    	return controlPowerLogService.getControlPowerLogPagedList(q);
    }
    /**
	 * 订单详情
	 * @return
	 */
	@RequestMapping("/toOrderView")
	public String toOrderView(String orderNo,ModelMap modelMap){
		Order order=orderService.getOrder(orderNo);
		Car car=carService.getCar(order.getCarNo());
		Park park1=new Park();
		//添加系统参数 订单时长大于分钟免收取车费
		SysParam spa=sysParamService.getByParamKey("VOID_SERVICE_FEE_GET");
		if(spa != null && "1".equals(spa.getParamValue())){
			SysParam spav=sysParamService.getByParamKey("VOID_ORDER_DURATION");
			if(spav != null){
				if(order.getOrderDuration() > Integer.valueOf(spav.getParamValue())){
					park1.setServiceFeeGet(0d);
				}else{
					if(order.getStartParkNo()!=null&&!order.getStartParkNo().equals("")){
						park1=parkService.getPark(order.getStartParkNo());
					}
				}
			}
		}else{
			if(order.getStartParkNo()!=null&&!order.getStartParkNo().equals("")){
				park1=parkService.getPark(order.getStartParkNo());
			}
		}
		
		Park park2=null;
		if(order.getActualTerminalParkNo()!=null&&!order.getActualTerminalParkNo().equals("")){
			park2=parkService.getPark(order.getActualTerminalParkNo());
		}
		Invoice invoice = invoiceService.getInvoiceDetailByOrderNo(orderNo);
		if(invoice != null){
			order.setInvioceNo(invoice.getInvoiceNo());
			order.setInvoiceTime(invoice.getInvoiceTime());
			modelMap.put("invoiceType", invoice.getInvoiceType());
			modelMap.put("invoiceCreateTime", invoice.getCreateTime());
		}
		modelMap.put("park1", park1);
		modelMap.put("park2", park2);
		modelMap.put("order", order);
		modelMap.put("car", car);
    	//获取 订单里面的 附加费用
    	Double serviceFeeAmount=0d;
    	if(order != null && order.getServiceFeeAmount() != null){
    		serviceFeeAmount=serviceFeeAmount+order.getServiceFeeAmount();
        	modelMap.addAttribute("serviceFeeAmount", serviceFeeAmount);
    	}else{
    		modelMap.addAttribute("serviceFeeAmount", serviceFeeAmount);
    	}
		
		//起步价和订单计费方式
		PricingRule pr=pricingRuleService.getPricingRule(order.getRuleNo());
		if(pr != null &&  pr.getBaseFee() != null  &&  pr.getRuleType() != null){
			modelMap.put("BaseFee", pr.getBaseFee());
			modelMap.put("RuleType", pr.getRuleType());
		}
		Double dayFD = 0d;
		Double orderAmount = 0d;
		// 里程金额
		Double mileageAmount = 0d;
		// 时长金额
		Double minutesAmount = 0d;
		OrderMileage omQuery = new OrderMileage();
		omQuery.setOrderNo(order.getOrderNo());
		List<OrderMileage> orderMileageList = orderMileageService.getOrderMileageList(new Query(omQuery));
		int i=0;
		for (OrderMileage om : orderMileageList) {
			if (om != null) {
				int orderCaculateType = 0;
				try{
					int ruleType = pr.getRuleType();
					if(ruleType ==1 || ruleType ==2){//目前只有两种规则
						orderCaculateType = ruleType;
					}
				}catch(Exception e){
					e.printStackTrace();
				}
				if(orderCaculateType==0){
					SysParam sysparam1 = sysParamService.getByParamKey("ORDER_CACULATE_TYPE");
					if(sysparam1!=null&&sysparam1.getParamValue()!=null){
						orderCaculateType = Integer.parseInt(sysparam1.getParamValue().trim());
					}
				}
				Double orderMileageDayAmount = om.getOrderAmountOfDay();
				if (orderCaculateType == 2) {
					dayFD = pr.getBillingCapPerDay();
					//订单是否前多少分钟免时长费
					SysParam param = sysParamService.getByParamKey("IS_ORDER_FREEMINUTES");
					if(param!=null&&param.getParamValue()!=null){
						int isOrderFreeMinutes = Integer.parseInt(param.getParamValue().trim());
						//如果是前FREEMINUTES分钟时长免费，则前FREEMINUTES分钟的时长金额不变，超过免费时长开始计费
						if(isOrderFreeMinutes==1){
							param = sysParamService.getByParamKey("FREEMINUTES");
							if(param!=null&&param.getParamValue()!=null){
								int freeMinutes = Integer.parseInt(param.getParamValue().trim());
								if(i==0){
									if(order.getOrderDuration()>=freeMinutes){
										if(om.getMinutes()>0){
											//计算第一天元/分钟
											Double priceOfMinute = ECCalculateUtils.div(om.getMinutesAmount(), om.getMinutes());
											//时长费减免freeMinutes分钟
											om.setMinutesAmount(ECCalculateUtils.mul(om.getMinutes()-freeMinutes, priceOfMinute));
											om.setMinutesAmount(ECCalculateUtils.round(om.getMinutesAmount(), 2));//取两位小数
											//重新计算一天费用
											orderMileageDayAmount = ECCalculateUtils.add(om.getMinutesAmount(), om.getMileageAmount());
										}
									}else{
										minutesAmount = 0d;
										om.setMinutesAmount(0d);
										//重新计算一天费用
										orderMileageDayAmount = om.getMileageAmount();
									}
								}
							}
						}
					}
					//订单是否前多少分钟免里程费
					SysParam param1 = sysParamService.getByParamKey("IS_ORDER_FREEMILEAGE");
					//如果是前FREEMINUTES分钟里程免费，则前FREEMINUTES分钟的里程金额不变，超过免费时长开始计费
					if(param1!=null&&param1.getParamValue()!=null){
						int isOrderFreeMileage = Integer.parseInt(param1.getParamValue().trim());
						if(isOrderFreeMileage==1){
							param = sysParamService.getByParamKey("FREEMINUTES");
							if(param!=null&&param.getParamValue()!=null){
								int freeMinutes = Integer.parseInt(param.getParamValue().trim());
								if(i==0){
									if(order.getOrderDuration()>=freeMinutes){
										if(om.getMileage()>0){
											//计算第一天元/分钟
											Double priceOfMileage = ECCalculateUtils.div(om.getMileageAmount(), om.getMileage());
											if(om.getFreeMileage()==null){
												om.setFreeMileage(0.0);
											}
											//freeMinutes分钟的里程费
											Double freeMileageAmount = ECCalculateUtils.mul(om.getFreeMileage(), priceOfMileage);
											//里程费减免freeMinutes分钟的里程费
											om.setMileageAmount(ECCalculateUtils.sub(om.getMileageAmount(), freeMileageAmount));
											om.setMileageAmount(ECCalculateUtils.round(om.getMileageAmount(), 2));//取两位小数
											//重新计算一天费用
											orderMileageDayAmount = ECCalculateUtils.add(om.getMinutesAmount(), om.getMileageAmount());
										}
									}else{
										minutesAmount = 0d;
										om.setMileageAmount(0d);
										//重新计算一天费用
										orderMileageDayAmount = om.getMinutesAmount();
									}
								}
							}
						}
					}
				}
				
				if (orderMileageDayAmount != null) {
					orderMileageDayAmount=ECCalculateUtils.add(om.getMileageAmount(), om.getMinutesAmount());
					//订单封顶金额是否计算里程费用（0计算 1 不计算）
					SysParam sysp = sysParamService.getByParamKey("IS_SUM_FREEMILEAGE");
					if(sysp!=null&&sysp.getParamValue()!=null && Integer.parseInt(sysp.getParamValue().trim())==1){
							//orderMileageDayAmount =	ECCalculateUtils.sub(orderMileageDayAmount,mileageAmount);
							//如果时长费用超过封顶金额 则总金额=封顶金额+里程费用
							if (minutesAmount > dayFD) {
								Double orderAmountm=om.getMileageAmount();
								orderAmount+=ECCalculateUtils.add(orderAmountm, dayFD);
							} else {//如果时长费用没有超过封顶金额 则总金额=时长费用+里程费用
								orderAmount = ECCalculateUtils.add(orderAmount, orderMileageDayAmount);
							}
						}else{
							if (orderMileageDayAmount > dayFD) {
								orderAmount = ECCalculateUtils.add(orderAmount, dayFD);
							} else {
								orderAmount = ECCalculateUtils.add(orderAmount, orderMileageDayAmount);
							}
						}
					
					
				}
				minutesAmount = ECCalculateUtils.add(minutesAmount, om.getMinutesAmount());
				mileageAmount = ECCalculateUtils.add(mileageAmount, om.getMileageAmount());
			}
			i++;
		}
		// 里程金额
		order.setMileageAmount(mileageAmount);
		// 时长金额
		order.setMinutesAmount(minutesAmount);
		
		modelMap.put("minutesAmount", minutesAmount);
		modelMap.put("mileageAmount", mileageAmount);
    	modelMap.addAttribute("serviceFeeAmount", serviceFeeAmount);
    	Query q = new Query();
    	OrderComments orderComments = new OrderComments();
    	orderComments.setOrderNo(orderNo);
    	q.setQ(orderComments);
    	PageFinder<OrderComments> commentsPage = orderCommentsService.getOrderCommentsPagedList(q);
    	if(commentsPage != null){
    		modelMap.put("commentsList", commentsPage.getData());
    	}
    	
    	if(order.getCarTakeUrl()!= null && !"".equals(order.getCarTakeUrl())){
    		String[]url=order.getCarTakeUrl().split(",");
    		modelMap.put("carTakeUrl1", url[0]);
    		modelMap.put("carTakeUrl2", url[1]);
    		modelMap.put("carTakeUrl3", url[2]);
    		if(url.length > 3){
    			modelMap.put("carTakeUrl4", url[3]);
    		}
    		
    	}
    	if(order.getCarBackUrl()!= null && !"".equals(order.getCarBackUrl())){
    		String[]urlb=order.getCarBackUrl().split(",");
    		modelMap.put("carBackUrl1", urlb[0]);
    		modelMap.put("carBackUrl2", urlb[1]);
    		modelMap.put("carBackUrl3", urlb[2]);
    		if(urlb.length > 3){
    			modelMap.put("carBackUrl4", urlb[3]);
    		}
    		
    	}
    	
		return "order/order_view";
	}
	/*
     * 订单修改
     */
    @RequestMapping("/updateOrder")
    @ResponseBody
    public ResultInfo<Order> updateOrder(@ModelAttribute("order")Order order){
    	ResultInfo<Order> resultInfo=null;
    	if(order.getOrderNo()!=null&&order.getOrderNo().length()!=0){
    		resultInfo=orderService.updateOrder(order, getOperator());
    	}
    	return resultInfo;
    }
    
	/*
     * 订单修改(超额订单)
     */
    @RequestMapping("/updateOrderOverAmount")
    @ResponseBody
    public ResultInfo<Order> updateOrderOverAmount(String orderNo){
    	ResultInfo<Order> resultInfo=null;
    	if(orderNo != null && !"".equals(orderNo) ){
    		Order order=orderService.getOrder(orderNo);
    		order.setWarningOrder(2);
    		resultInfo=orderService.updateOrder(order, getOperator());
    	}
    	return resultInfo;
    }
    /*
     * 订单强制结束前判断
     */
    @SuppressWarnings("unchecked")
	@RequestMapping("/checkBeforeOrderForceOver")
    @ResponseBody
    public ResultInfo<OrderFinish> checkBeforeOrderForceOver(@ModelAttribute("orderFinish")OrderFinish orderFinish) throws Exception{
    	ResultInfo<OrderFinish> resultInfo=new ResultInfo<OrderFinish>();
    	if(orderFinish.getOrderNo()!=null&&orderFinish.getOrderNo().length()!=0){
    		//租车之前先判断该车辆是否是新设备，有则发送还车的指令,并且等待成功响应
    		Order order = orderService.getOrder(orderFinish.getOrderNo());
			Device device = deviceService.getDeviceByCarNo(order.getCarNo());
			if(device!=null){//&&"1".equals(device.getVersionType())为1是新设备，可以使用用车还车指令，且等待响应完成后继续执行后续业务
				ResultInfo<String> userCarResult = new ResultInfo<String>();
				String res = "";
				try {
					//发送还车指令出现异常时，允许结束订单
					res	= carStatusService.returnCarSendCmd(device.getDeviceSn(), order.getMemberNo(),null,"1");
				} catch (Exception e) {
					resultInfo.setCode(Constant.FAIL);
					resultInfo.setMsg("还车指令发送异常");
					return resultInfo;
				}
				if(!"".equals(res)){
					Gson gson = new Gson();
					userCarResult = gson.fromJson(res, ResultInfo.class);
					resultInfo.setCode(Constant.FAIL);
					resultInfo.setMsg(userCarResult.getMsg());
					return resultInfo;
				}
			}else{//旧设备直接返回1
				resultInfo.setCode(Constant.SECCUESS);
			}
    	}
    	return resultInfo;
    }
    /*
     * 订单强制结束
     */
	@RequestMapping("/orderForceOver")
    @ResponseBody
    public ResultInfo<OrderFinish> orderForceOver(@ModelAttribute("orderFinish")OrderFinish orderFinish) throws Exception{
    	return orderFinishService.orderOver(orderFinish, getOperator());
    }
    /*
     * 订单正常结束提交
     */
    @SuppressWarnings("unchecked")
	@RequestMapping("/orderOver")
    @ResponseBody
    public ResultInfo<OrderFinish> orderOver(@ModelAttribute("orderFinish")OrderFinish orderFinish){
    	ResultInfo<OrderFinish> resultInfo=new ResultInfo<OrderFinish>();
    	if(orderFinish.getOrderNo()!=null&&orderFinish.getOrderNo().length()!=0){
    		if(orderFinish.getIsCarAccident()==null){
    			orderFinish.setIsCarAccident(0);
    		}
    		if(orderFinish.getIsCarFault()==null){
    			orderFinish.setIsCarFault(0);
    		}
    		//还车之前先判断该车辆是否是新设备，有则发送还车的指令,并且等待成功响应
    		Order order = orderService.getOrder(orderFinish.getOrderNo());
    		Device device = deviceService.getDeviceByCarNo(order.getCarNo());
			if(device!=null){//&&"1".equals(device.getVersionType())为1是新设备，可以使用用车还车指令，且等待响应完成后继续执行后续业务
				ResultInfo<String> userCarResult = new ResultInfo<String>();
				String res = "";
				try {
					//发送还车指令出现异常时，允许结束订单
					res	= carStatusService.returnCarSendCmd(device.getDeviceSn(), order.getMemberNo(),null,"1");
				} catch (Exception e) {
					resultInfo.setCode(Constant.FAIL);
					resultInfo.setMsg("发送指令异常");
					return resultInfo;
				}
				if(!"".equals(res)){
					Gson gson = new Gson();
					userCarResult = gson.fromJson(res, ResultInfo.class);
					if (userCarResult.getCode().equals(Constant.SECCUESS)) {
						resultInfo=orderFinishService.orderOver(orderFinish, getOperator());
					} else { 
						//不允许结束订单
						resultInfo.setCode(Constant.FAIL);
						resultInfo.setMsg(userCarResult.getMsg());
					} 
					return resultInfo;
				}
			}else{//旧设备按原业务处理
				resultInfo=orderFinishService.orderOver(orderFinish, getOperator());
			}
    	}
    	return resultInfo;
    }
    //判断订单的订单冲账记录是否有未审核的冲账
    @RequestMapping("/orderStrikeBalanceCensorTag")
    @ResponseBody
    public ResultInfo<Integer> orderStrikeBalanceCensorTag(String orderNo){
    	ResultInfo<Integer> resultInfo=new ResultInfo<Integer>();
    	Query q=new Query();
    	OrderStrikeBalance orderStrikeSearch = new OrderStrikeBalance();
    	orderStrikeSearch.setCensorStatus(0);
    	orderStrikeSearch.setOrderNo(orderNo);
    	q.setQ(orderStrikeSearch);
    	List<OrderStrikeBalance> list=orderStrikeBalanceService.getOrderStrikeBalanceList(q);
    	Integer tag=0;
    	if(list!=null&&list.size()>0){
    		tag=1;
    	}
    	resultInfo.setCode(Constant.SECCUESS);
    	resultInfo.setData(tag);
    	return resultInfo;
    }
    /**
     * 订单冲账页面
     * */
    @RequestMapping("/toOrderStrikeBalanceAdd")
	public String toOrderStrikeBalanceAdd(String orderNo,ModelMap modelMap){
    	Order order=orderService.getOrder(orderNo);
    	modelMap.put("order", order);
//    	OrderFinish orderFinish=new OrderFinish();
//    	orderFinish.setOrderNo(order.getOrderNo());
//    	//orderFinish.setFinishType(0);//正常结束
//    	List<OrderFinish> finishList=orderFinishService.getOrderFinishList(new Query(orderFinish));
//    	Double serviceFee=0d;
//    	if(finishList!=null&&finishList.size()>0){
//    		for(OrderFinish oFinish:finishList){
//    			if(oFinish.getAdditionFees()!=null&&oFinish.getAdditionFees()>0){
//    				serviceFee=serviceFee+oFinish.getAdditionFees();
//    			}
//    		}
//    	}
    	//获取 订单里面的 附加费用
    	Double serviceFeeAmount=0d;
    	if(order != null && order.getServiceFeeAmount() != null){
    		serviceFeeAmount=serviceFeeAmount+order.getServiceFeeAmount();
        	modelMap.addAttribute("serviceFeeAmount", serviceFeeAmount);
    	}else{
    		modelMap.addAttribute("serviceFeeAmount", serviceFeeAmount);
    	}
    	
    	modelMap.addAttribute("serviceFeeAmount", serviceFeeAmount);
    	
		return "order/order_strike_balance_add";
	}
    /*
     *列表页面-- 订单正常结束提交
     */
    @RequestMapping("/orderOverNormal")
    @ResponseBody
    public ResultInfo<Order> orderOverNormal(String orderNo) throws ParseException{
    	ResultInfo<Order> resultInfo=new ResultInfo<Order>();
    	Order order = orderService.getOrder(orderNo);
		CarStatus carStatus = carStatusService.getCarStatus(order.getCarNo());
		//车辆状态（1、已启动，2、已熄火）
		if(carStatus.getCarStatus()==1){
			resultInfo.setCode(CarConstant.car_NOFlameout);
			resultInfo.setMsg("车辆未熄火");
			return resultInfo;
		}else{
	    	return carStatusService.returnCar(orderNo,null,getOperator());
		}
    }
    
    /**
     * 导出 订单信息
     */
    @RequestMapping("/toOrderExport")
    public void toOrderExport(@ModelAttribute("order")Order order,HttpServletRequest request, HttpServletResponse response){
    	try {
			Query q=new Query();
			q.setQ(order);
			List<Order> orders=orderService.getOrderList(q);
			 // 声明一个工作薄  
			String path=request.getRealPath("/")+"res"+File.separator+"order.xls";
    		File ff=new File(path);
	        InputStream is = new FileInputStream(ff);  
	        HSSFWorkbook workbook =new HSSFWorkbook(is);
	        // 生成一个表格  
	        HSSFSheet sheet = workbook.getSheetAt(0);
	        int i=1;
	        for(Order orderData:orders){
	        	 sheet.createRow(i);
	        	 
	        	 setColumnValue(sheet,i,0,ECStringUtils.toStringForNull(orderData.getOrderNo()));
	        	 setColumnValue(sheet,i,1,ECStringUtils.toStringForNull(orderData.getCityName()));
	        	 setColumnValue(sheet,i,2,ECStringUtils.toStringForNull(orderData.getCarModelName()));
	        	 setColumnValue(sheet,i,3,ECStringUtils.toStringForNull(orderData.getCarPlateNo()));
	        	 setColumnValue(sheet,i,4,ECStringUtils.toStringForNull(orderData.getMemberName()));
	        	 setColumnValue(sheet,i,5,ECStringUtils.toStringForNull(orderData.getMobilePhone()));
	        	 setColumnValue(sheet,i,6,ECStringUtils.toStringForNull(orderData.getCompanyName()));
	        	 setColumnValue(sheet,i,7,ECDateUtils.formatTime(orderData.getStartBillingTime()));
	        	 setColumnValue(sheet,i,8,ECDateUtils.formatTime(orderData.getFinishTime()));
	        	 setColumnValue(sheet,i,9,ECStringUtils.toStringForNull(orderData.getActualTakeLoacton()));
	        	 setColumnValue(sheet,i,10,ECStringUtils.toStringForNull(orderData.getActualTerminalParkName()));
	        		// 订单状态（0、已提交，1、已预约，2、已计费，3、已结束，4、已取消，默认0）
	        	 String orderStatus = "已提交";
	        	 if(orderData.getOrderStatus() == 0){
	        		 orderStatus ="已提交";
	        	 }else if(orderData.getOrderStatus() == 1){
	        		 orderStatus ="已预约";
	        	 }else if(orderData.getOrderStatus() == 2){
	        		 orderStatus ="已计费";
	        	 }else if(orderData.getOrderStatus() == 3){
	        		 orderStatus ="已结束";
	        	 }else if(orderData.getOrderStatus() == 4){
	        		 orderStatus ="已取消";
	        	 }
	        	 setColumnValue(sheet,i,11,orderStatus);
	        	 
	        	 setColumnValue(sheet,i,12,ECStringUtils.toStringForNull(orderData.getOrderAmount()));
	        	 //setColumnValue(sheet,i,10,ECStringUtils.toStringForNull(orderData.getOrderDuration()));
	        	 setColumnValue(sheet,i,13,ECStringUtils.toStringForNull(orderData.getPayableAmount()));
	        	 if(orderData.getPayStatus() != null && orderData.getPayStatus()==1){
	        		 setColumnValue(sheet,i,14,ECStringUtils.toStringForNull(orderData.getPayableAmount()));
	        	 }else{
	        		 setColumnValue(sheet,i,14,ECStringUtils.toStringForNull(0.00));
	        	 }
	        	 
	        	 setColumnValue(sheet,i,15,ECStringUtils.toStringForNull(orderData.getPackMinutesDiscountAmount()));
	        	 String payStatus = "未支付";
	        	 if(orderData.getPayStatus() == 0){
	        		 payStatus ="未支付";
	        	 }else if(orderData.getPayStatus() == 1){
	        		 payStatus ="已支付";
	        	 }
	        	 setColumnValue(sheet,i,16,payStatus);
	        	
	        	
	        	 
	        	 i++;
	        }
	        response.reset();// 清空输出流   
	        response.setContentType("text/plain;charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            response.addHeader("Content-Disposition", "attachment;filename=order.xls");
            ServletOutputStream os=response.getOutputStream();
            workbook.write(os);
	        os.flush();
		    os.close(); // 关闭流
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
    	
    }
    
     /**
     * 进入订单运营统计报表页面 
     * @return
     */
	@RequestMapping("/mainOperateOrderCount")
	public String mainOperatePage(ModelMap modelMap){
		List<Park> parkList = parkService.getParkList(new Query());

		modelMap.put("parkList", parkList);
		return "operateCount/order_operate_count";
	}
	
    /*
	 * 订单运营报表信息-按日
	 */
	@RequestMapping("/dayOrderOperateCount")
	@ResponseBody
	public ResultInfo<OrderCountVo> dayOrderOperateCount(Date startTime,Date endTime,String parkNameTempString,Query query) throws Exception {
		
		ResultInfo<OrderCountVo> orderCountVo  = new ResultInfo<OrderCountVo>();
		 
		String[] temp1 = new String[] {};
		List<String> list = new ArrayList<String>();
		
		if(parkNameTempString != null && !"".equals(parkNameTempString)){
			temp1 = parkNameTempString.split(",");
			list = Arrays.asList(temp1);
		}
		
		SysParam sysParam = sysParamService.getByParamKey(MemberConstant.day_parameter_key);
		int dayParmaeter = Integer.parseInt(sysParam.getParamValue());//单位：天
		
		//获取系统当前日期 
		SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM-dd");
		
//		if(endTime == null ||  "".equals(endTime)){
//			endTime = new Date();
//			endTime = dft.parse(dft.format(endTime));
//		}else{
//			dayParmaeter = Integer.parseInt(String.valueOf(ECDateUtils.getDistanceDays(dft.format(startTime),dft.format(endTime))));
//		}
		 
		if(endTime == null ||  "".equals(endTime)){

			if(startTime == null ||  "".equals(startTime)){
				endTime = new Date();
				endTime = dft.parse(dft.format(endTime));
			}else{
				String endTimeStr = ECDateUtils.getSpecifiedDayBefore(startTime,dayParmaeter*-1);
				endTime = dft.parse(endTimeStr);
			}
			
		}else{
			if(startTime == null ||  "".equals(startTime)){
				String startTimeStr = ECDateUtils.getSpecifiedDayBefore(endTime,dayParmaeter);
				startTime = dft.parse(startTimeStr);
			}
			
			dayParmaeter = Integer.parseInt(String.valueOf(ECDateUtils.getDistanceDays(dft.format(startTime),dft.format(endTime))));
		}
		
		orderCountVo.setData(orderService.orderOperateCount(startTime,endTime, dayParmaeter,list));
		 
		  
		return orderCountVo;
	}
    

    /*
	 * 订单运营报表信息-按周
	 */
	@RequestMapping("/weekOrderOperateCount")
	@ResponseBody
	public ResultInfo<OrderCountVo> weekOrderOperateCount(Date startTime,Date endTime,String parkNameTempString,Query query) throws ParseException {

		ResultInfo<OrderCountVo> orderCountVo  = new ResultInfo<OrderCountVo>();

		String[] temp1 = new String[] {};
		List<String> list = new ArrayList<String>();

		if(parkNameTempString != null && !"".equals(parkNameTempString)){
			temp1 = parkNameTempString.split(",");
			list = Arrays.asList(temp1);
		}
		
		SysParam sysParam = sysParamService.getByParamKey(MemberConstant.week_parameter_key);
		int weekParmaeter = Integer.parseInt(sysParam.getParamValue());//单位：周
		
		//获取系统当前日期 
		SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM-dd");
		
//		if(endTime == null ||  "".equals(endTime)){
//			endTime = new Date();
//			endTime = dft.parse(dft.format(endTime));
//			
//			startTime = ECDateUtils.getSpecifiedDay(endTime,weekParmaeter*7);
//		}else{
//		   
//            long from = dft.parse(dft.format(startTime)).getTime();
//            long to = dft.parse(dft.format(endTime)).getTime();
//           
//            weekParmaeter = (int) ((to-from)/(1000*3600*24*7)) + 1;
//		}

		if(endTime == null ||  "".equals(endTime)){

			if(startTime == null ||  "".equals(startTime)){
				endTime = new Date();
				endTime = dft.parse(dft.format(endTime));
				
				startTime = ECDateUtils.getSpecifiedDay(endTime,weekParmaeter*7);
			}else{
				endTime = ECDateUtils.getSpecifiedDay(startTime,weekParmaeter*7*-1);
			}
			
		}else{
			if(startTime == null ||  "".equals(startTime)){ 
				startTime = ECDateUtils.getSpecifiedDay(endTime,weekParmaeter*7);
			}

            long from = dft.parse(dft.format(startTime)).getTime();
            long to = dft.parse(dft.format(endTime)).getTime();
           
            weekParmaeter = (int) ((to-from)/(1000*3600*24*7)) + 1;
		}
		
		orderCountVo.setData(orderService.weekOrderOperateCount(startTime,endTime, weekParmaeter,list));
		
		return orderCountVo;
	}

    /*
	 * 订单运营报表信息-按月
	 */
	@RequestMapping("/monthOrderOperateCount")
	@ResponseBody
	public ResultInfo<OrderCountVo> monthOrderOperateCount(Date startTime,Date endTime,String parkNameTempString,Query query) throws ParseException {

		ResultInfo<OrderCountVo> orderCountVo  = new ResultInfo<OrderCountVo>();
		 
		String[] temp1 = new String[] {};
		List<String> list = new ArrayList<String>();

		if(parkNameTempString != null && !"".equals(parkNameTempString)){
			temp1 = parkNameTempString.split(",");
			list = Arrays.asList(temp1);
		}
		
		SysParam sysParam = sysParamService.getByParamKey(MemberConstant.month_parameter_key);
		int monthParmaeter = Integer.parseInt(sysParam.getParamValue());//单位：月
		
		//获取系统当前日期 
		SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM");
		
//		if(endTime == null ||  "".equals(endTime)){
//			endTime = new Date();
//			endTime = dft.parse(dft.format(endTime));
//			startTime = dft.parse(ECDateUtils.getSpecifiedmonth(endTime,monthParmaeter));
//		}else{
//			monthParmaeter = ECDateUtils.getMonthSpace(dft.format(startTime),dft.format(endTime)) +1;
//		}

		if(endTime == null ||  "".equals(endTime)){

			if(startTime == null ||  "".equals(startTime)){
				endTime = new Date();
				endTime = dft.parse(dft.format(endTime));

				startTime = dft.parse(ECDateUtils.getSpecifiedmonth(endTime,monthParmaeter));
			}else{
				endTime = dft.parse(ECDateUtils.getSpecifiedmonth(startTime,monthParmaeter*-1)); 
			}
			
		}else{
			if(startTime == null ||  "".equals(startTime)){ 
				startTime = dft.parse(ECDateUtils.getSpecifiedmonth(endTime,monthParmaeter));
			}
 
			monthParmaeter = ECDateUtils.getMonthSpace(dft.format(startTime),dft.format(endTime)) +1;
		}
		
		orderCountVo.setData(orderService.monthOrderOperateCount(startTime,endTime, monthParmaeter,list));
		
		return orderCountVo;
	}

    /*
	 * 订单运营报表信息-按年
	 */
	@RequestMapping("/yearOrderOperateCount")
	@ResponseBody
	public ResultInfo<OrderCountVo> yearOrderOperateCount(Date startTime,Date endTime,String parkNameTempString,Query query) throws ParseException {

		ResultInfo<OrderCountVo> orderCountVo  = new ResultInfo<OrderCountVo>();

		String[] temp1 = new String[] {};
		List<String> list = new ArrayList<String>();

		if(parkNameTempString != null && !"".equals(parkNameTempString)){
			temp1 = parkNameTempString.split(",");
			list = Arrays.asList(temp1);
		}
		
		SysParam sysParam = sysParamService.getByParamKey(MemberConstant.year_parameter_key);
		int yearParmaeter = Integer.parseInt(sysParam.getParamValue());//单位：年
		
		//获取系统当前日期 
		SimpleDateFormat dft = new SimpleDateFormat("yyyy");
		
//		if(endTime == null ||  "".equals(endTime)){
//			endTime = new Date();
//			endTime = dft.parse(dft.format(endTime));
//			startTime = dft.parse(ECDateUtils.getSpecifiedYear(endTime,yearParmaeter));
//		}else{
//			yearParmaeter = ECDateUtils.getYearSpace(dft.format(startTime),dft.format(endTime)) +1;
//		}

		if(endTime == null ||  "".equals(endTime)){

			if(startTime == null ||  "".equals(startTime)){
				endTime = new Date();
				endTime = dft.parse(dft.format(endTime));

				startTime = dft.parse(ECDateUtils.getSpecifiedYear(endTime,yearParmaeter));
			}else{
				endTime = dft.parse(ECDateUtils.getSpecifiedYear(startTime,yearParmaeter*-1)); 
			}
			
		}else{
			if(startTime == null ||  "".equals(startTime)){ 
				startTime = dft.parse(ECDateUtils.getSpecifiedYear(endTime,yearParmaeter)); 
			}

			yearParmaeter = ECDateUtils.getYearSpace(dft.format(startTime),dft.format(endTime)) +1;
		}
		
		orderCountVo.setData(orderService.yearOrderOperateCount(startTime,endTime, yearParmaeter,list));
		
		return orderCountVo;
	}
	

    /**
    * 进入客户订单运营统计报表页面 
    * @return
    */
	@RequestMapping("/mainUserOrderCount")
	public String mainUserOrderCount(ModelMap modelMap){
		return "operateCount/order_user_count";
	}
	

	/**
	 * 客户订单运营报表信息-按日
	 * @param startTime
	 * @param endTime
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/dayUserOrderOperateCount")
	@ResponseBody
	public ResultInfo<List<OrderCountVo>> dayUserOrderOperateCount(Date startTime,Date endTime) throws Exception {
		
		ResultInfo<List<OrderCountVo>> orderCountVo  = new ResultInfo<List<OrderCountVo>>();
		
		SysParam sysParam = sysParamService.getByParamKey(MemberConstant.day_parameter_key);
		int dayParmaeter = Integer.parseInt(sysParam.getParamValue());//单位：天
		
		//获取系统当前日期 
		SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM-dd");
		
//		if(endTime == null ||  "".equals(endTime)){
//			endTime = new Date();
//			endTime = dft.parse(dft.format(endTime));
//		}else{
//			dayParmaeter = Integer.parseInt(String.valueOf(ECDateUtils.getDistanceDays(dft.format(startTime),dft.format(endTime))));
//		}

		if(endTime == null ||  "".equals(endTime)){

			if(startTime == null ||  "".equals(startTime)){
				endTime = new Date();
				endTime = dft.parse(dft.format(endTime));
			}else{
				String endTimeStr = ECDateUtils.getSpecifiedDayBefore(startTime,dayParmaeter*-1);
				endTime = dft.parse(endTimeStr);
			}
			
		}else{
			if(startTime == null ||  "".equals(startTime)){
				String startTimeStr = ECDateUtils.getSpecifiedDayBefore(endTime,dayParmaeter);
				startTime = dft.parse(startTimeStr);
			}
			
			dayParmaeter = Integer.parseInt(String.valueOf(ECDateUtils.getDistanceDays(dft.format(startTime),dft.format(endTime))));
		}
		
		orderCountVo.setData(orderService.orderUserOperateCount(startTime,endTime, dayParmaeter));
		 
		  
		return orderCountVo;
	}
    

	/**
	 * 客户订单运营报表信息-按周
	 * @param startTime
	 * @param endTime
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/weekUserOrderOperateCount")
	@ResponseBody
	public ResultInfo<List<OrderCountVo>> weekUserOrderOperateCount(Date startTime,Date endTime) throws ParseException {

		ResultInfo<List<OrderCountVo>> orderCountVo  = new ResultInfo<List<OrderCountVo>>();
 
		SysParam sysParam = sysParamService.getByParamKey(MemberConstant.week_parameter_key);
		int weekParmaeter = Integer.parseInt(sysParam.getParamValue());//单位：周
		
		//获取系统当前日期 
		SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM-dd");
		
//		if(endTime == null ||  "".equals(endTime)){
//			endTime = new Date();
//			endTime = dft.parse(dft.format(endTime));
//			
//			startTime = ECDateUtils.getSpecifiedDay(endTime,weekParmaeter*7);
//		}else{
//		   
//            long from = dft.parse(dft.format(startTime)).getTime();
//            long to = dft.parse(dft.format(endTime)).getTime();
//           
//            weekParmaeter = (int) ((to-from)/(1000*3600*24*7)) + 1;
//		}

		if(endTime == null ||  "".equals(endTime)){

			if(startTime == null ||  "".equals(startTime)){
				endTime = new Date();
				endTime = dft.parse(dft.format(endTime));
				
				startTime = ECDateUtils.getSpecifiedDay(endTime,weekParmaeter*7);
			}else{
				endTime = ECDateUtils.getSpecifiedDay(startTime,weekParmaeter*7*-1);
			}
			
		}else{
			if(startTime == null ||  "".equals(startTime)){ 
				startTime = ECDateUtils.getSpecifiedDay(endTime,weekParmaeter*7);
			}

            long from = dft.parse(dft.format(startTime)).getTime();
            long to = dft.parse(dft.format(endTime)).getTime();
           
            weekParmaeter = (int) ((to-from)/(1000*3600*24*7)) + 1;
		}
		
		orderCountVo.setData(orderService.weekUserOrderOperateCount(startTime,endTime, weekParmaeter));
		
		return orderCountVo;
	}


	/**
	 * 客户订单运营报表信息-按月
	 * @param startTime
	 * @param endTime
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/monthUserOrderOperateCount")
	@ResponseBody
	public ResultInfo<List<OrderCountVo>> monthUserOrderOperateCount(Date startTime,Date endTime) throws ParseException {

		ResultInfo<List<OrderCountVo>> orderCountVo  = new ResultInfo<List<OrderCountVo>>();
		
		SysParam sysParam = sysParamService.getByParamKey(MemberConstant.month_parameter_key);
		int monthParmaeter = Integer.parseInt(sysParam.getParamValue());//单位：月
		
		//获取系统当前日期 
		SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM");
		
//		if(endTime == null ||  "".equals(endTime)){
//			endTime = new Date();
//			endTime = dft.parse(dft.format(endTime));
//			startTime = dft.parse(ECDateUtils.getSpecifiedmonth(endTime,monthParmaeter));
//		}else{
//			monthParmaeter = ECDateUtils.getMonthSpace(dft.format(startTime),dft.format(endTime)) +1;
//		}

		if(endTime == null ||  "".equals(endTime)){

			if(startTime == null ||  "".equals(startTime)){
				endTime = new Date();
				endTime = dft.parse(dft.format(endTime));

				startTime = dft.parse(ECDateUtils.getSpecifiedmonth(endTime,monthParmaeter));
			}else{
				endTime = dft.parse(ECDateUtils.getSpecifiedmonth(startTime,monthParmaeter*-1)); 
			}
			
		}else{
			if(startTime == null ||  "".equals(startTime)){ 
				startTime = dft.parse(ECDateUtils.getSpecifiedmonth(endTime,monthParmaeter));
			}
 
			monthParmaeter = ECDateUtils.getMonthSpace(dft.format(startTime),dft.format(endTime)) +1;
		}
		
		orderCountVo.setData(orderService.monthUserOrderOperateCount(startTime,endTime, monthParmaeter));
		
		return orderCountVo;
	}


	/**
	 * 客户订单运营报表信息-按年
	 * @param startTime
	 * @param endTime
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/yearUserOrderOperateCount")
	@ResponseBody
	public ResultInfo<List<OrderCountVo>> yearUserOrderOperateCount(Date startTime,Date endTime) throws ParseException {

		ResultInfo<List<OrderCountVo>> orderCountVo  = new ResultInfo<List<OrderCountVo>>();

		SysParam sysParam = sysParamService.getByParamKey(MemberConstant.year_parameter_key);
		int yearParmaeter = Integer.parseInt(sysParam.getParamValue());//单位：年
		
		//获取系统当前日期 
		SimpleDateFormat dft = new SimpleDateFormat("yyyy");
		
//		if(endTime == null ||  "".equals(endTime)){
//			endTime = new Date();
//			endTime = dft.parse(dft.format(endTime));
//			startTime = dft.parse(ECDateUtils.getSpecifiedYear(endTime,yearParmaeter));
//		}else{
//			yearParmaeter = ECDateUtils.getYearSpace(dft.format(startTime),dft.format(endTime)) +1;
//		}

		if(endTime == null ||  "".equals(endTime)){

			if(startTime == null ||  "".equals(startTime)){
				endTime = new Date();
				endTime = dft.parse(dft.format(endTime));

				startTime = dft.parse(ECDateUtils.getSpecifiedYear(endTime,yearParmaeter));
			}else{
				endTime = dft.parse(ECDateUtils.getSpecifiedYear(startTime,yearParmaeter*-1)); 
			}
			
		}else{
			if(startTime == null ||  "".equals(startTime)){ 
				startTime = dft.parse(ECDateUtils.getSpecifiedYear(endTime,yearParmaeter)); 
			}

			yearParmaeter = ECDateUtils.getYearSpace(dft.format(startTime),dft.format(endTime)) +1;
		}
		
		orderCountVo.setData(orderService.yearUserOrderOperateCount(startTime,endTime, yearParmaeter));
		
		return orderCountVo;
	}
	
	
}
