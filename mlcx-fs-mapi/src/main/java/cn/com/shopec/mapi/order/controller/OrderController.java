package cn.com.shopec.mapi.order.controller;

import java.awt.Image;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import com.alibaba.fastjson.JSONObject;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;

import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.common.component.ECCustomerDateEditor;
import cn.com.shopec.common.constants.Constant;
import cn.com.shopec.common.utils.ECCalculateUtils;
import cn.com.shopec.common.utils.ECDateUtils;
import cn.com.shopec.common.utils.ECGeoCoordinateTransformUtil;
import cn.com.shopec.common.utils.ECNumberUtils;
import cn.com.shopec.common.utils.HttpURLRequestUtil;
import cn.com.shopec.common.utils.JsonUtils;
import cn.com.shopec.core.car.common.CarConstant;
import cn.com.shopec.core.car.model.Car;
import cn.com.shopec.core.car.model.CarStatus;
import cn.com.shopec.core.car.service.CarAccidentService;
import cn.com.shopec.core.car.service.CarIllegalService;
import cn.com.shopec.core.car.service.CarService;
import cn.com.shopec.core.car.service.CarStatusService;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.customer.model.OrderComments;
import cn.com.shopec.core.customer.service.OrderCommentsService;
import cn.com.shopec.core.device.model.Device;
import cn.com.shopec.core.device.service.DeviceService;
import cn.com.shopec.core.finace.common.DepositConstant;
import cn.com.shopec.core.marketing.model.AppLocation;
import cn.com.shopec.core.marketing.model.CarRedPacket;
import cn.com.shopec.core.marketing.model.CarRedPacketVo;
import cn.com.shopec.core.marketing.model.PeakHoursCost;
import cn.com.shopec.core.marketing.model.PricingRule;
import cn.com.shopec.core.marketing.model.PricingRuleCustomizedDate;
import cn.com.shopec.core.marketing.service.AppLocationService;
import cn.com.shopec.core.marketing.service.CarRedPacketService;
import cn.com.shopec.core.marketing.service.PricingDeductionRecordService;
import cn.com.shopec.core.marketing.service.PricingRuleCustomizedDateService;
import cn.com.shopec.core.marketing.service.PricingRuleService;
import cn.com.shopec.core.member.common.MemberConstant;
import cn.com.shopec.core.member.service.MemberService;
import cn.com.shopec.core.order.common.OrderConstant;
import cn.com.shopec.core.order.dao.OrderDao;
import cn.com.shopec.core.order.model.Order;
import cn.com.shopec.core.order.model.OrderMileage;
import cn.com.shopec.core.order.model.PricingPackOrder;
import cn.com.shopec.core.order.service.OrderMileageService;
import cn.com.shopec.core.order.service.OrderService;
import cn.com.shopec.core.order.service.PricingPackOrderService;
import cn.com.shopec.core.order.vo.OrderStatusVo;
import cn.com.shopec.core.resource.model.Park;
import cn.com.shopec.core.resource.service.ParkService;
import cn.com.shopec.core.system.model.DataDictCat;
import cn.com.shopec.core.system.model.DataDictItem;
import cn.com.shopec.core.system.model.SysParam;
import cn.com.shopec.core.system.model.SysRegion;
import cn.com.shopec.core.system.service.DataDictCatService;
import cn.com.shopec.core.system.service.DataDictItemService;
import cn.com.shopec.core.system.service.SysParamService;
import cn.com.shopec.core.system.service.SysRegionService;
import cn.com.shopec.mapi.common.controller.BaseController;
import cn.com.shopec.mapi.order.vo.OrderCancelVo;
import cn.com.shopec.mapi.order.vo.OrderCommentsVo;
import cn.com.shopec.mapi.order.vo.OrderInvoiceVo;
import cn.com.shopec.mapi.order.vo.OrderVO;
import cn.com.shopec.mapi.order.vo.OrderVOTrip;
import cn.com.shopec.mapi.order.vo.PearTimeCostVo;
import cn.com.shopec.mapi.resource.vo.AppLocationVOSearchBaiDu;
import cn.com.shopec.mapi.resource.vo.AppLocationVOSearchBaiDu.AppLocationVOBaiDu.AddressComponent;

@Controller
@RequestMapping("/app/order")
public class OrderController extends BaseController{
	
	private static String DEVICE_KEY = "";
	
	static {
		StringBuilder sb = new StringBuilder("");
		for(int i = 0; i < 128; i++) {
			sb.append('0');
		}
		DEVICE_KEY = sb.toString();
	}
	
	@Resource
	private OrderService orderService;
	
	@Resource
	private CarService carService;
	
	@Resource
	private SysParamService sysParamService;
	
	@Resource
	private CarStatusService carStatusService;
	
	@Resource
	private DeviceService deviceService;
	
	@Resource
	private ParkService parkService;
	
	@Resource
	private PricingRuleService pricingRuleService;
	
	@Resource
	private MemberService memberService;
	
	@Resource
	private OrderDao orderDao;
	
	@Resource
	private PricingPackOrderService pricingPackOrderService;
	@Resource
	private OrderCommentsService commentsService;
	@Resource
	private PricingDeductionRecordService pricingDeductionRecordService;
	@Resource
	private CarAccidentService carAccidentService;
	@Resource
	private CarIllegalService carIllegalService;
	@Resource
	private OrderMileageService orderMileageService;
	@Resource
	private PricingRuleCustomizedDateService pricingRuleCustomizedDateService;
	@Resource
	private DataDictItemService dataDictItemService;
	
	@Resource
	private SysRegionService sysRegionService;
	@Resource
	private AppLocationService appLocationService;
	@Value("${image_path}")
	private String imgPath;
	@Value("${ak}")
	private String ak;
	@Resource
	private CarRedPacketService carRedPacketService;
//	@Value("${image}")
//	private String image;
	/**
	 * 获取正在进行中的订单
	 * @param memberNo	会员编号
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping("/getNowadayOrder")
	@ResponseBody
	public ResultInfo<OrderStatusVo> getNowadayOrder(String memberNo,String longitude,String latitude) throws ParseException{
		ResultInfo<OrderStatusVo> result = new ResultInfo<OrderStatusVo>();
		if (memberNo != null && memberNo.trim().length() > 0) {
			//提交订单向AppLocation 添加数据(app打开时所在位置)
			 if(longitude != null && !"".equals(longitude)  && latitude != null && !"".equals(latitude)){
					AppLocation appLocation = new AppLocation();
					appLocation.setAppLatitude(Double.valueOf(latitude));
					appLocation.setAppLongitude(Double.valueOf(longitude));
					appLocation.setType(1);
					appLocation.setMemberNo(memberNo);
					 try {
						//根据经纬度反查地址
						 String urlString = "http://api.map.baidu.com/geocoder/v2/?ak="+ak+"&location="+latitude+","+longitude+"&output=json&pois=0";  
						String result1 = HttpURLRequestUtil.doMsgGet(urlString);
						Gson gson = new Gson();
						AppLocationVOSearchBaiDu appLocationVOSearchBaiDu = gson.fromJson(result1, AppLocationVOSearchBaiDu.class);
						if(appLocationVOSearchBaiDu != null && appLocationVOSearchBaiDu.getStatus()==0 && appLocationVOSearchBaiDu.getResult() != null ){
							 appLocation.setAddrStreet( appLocationVOSearchBaiDu.getResult().getFormatted_address());
							AddressComponent addressComponent =appLocationVOSearchBaiDu.getResult().getAddressComponent();
							appLocation.setAddrRegion1Name(addressComponent.getProvince());
							SysRegion sr1=sysRegionService.getRegionIdByRegionName(addressComponent.getCity());
							if(sr1 != null){
								appLocation.setAddrRegion1Id(sr1.getRegionId());
							}
							if(addressComponent.getDistrict()!= null && !"".equals(addressComponent.getDistrict())){
								appLocation.setAddrRegion2Name(addressComponent.getCity());
								SysRegion sr2=sysRegionService.getRegionIdByRegionName(addressComponent.getCity());
								if(sr2 != null){
									appLocation.setAddrRegion2Id(sr2.getRegionId());
								}
								//根据找到的市区匹配数据字典
								DataDictItem ddi =dataDictItemService.getItemValue(addressComponent.getCity(), "CITY");
								if(ddi != null){
									appLocation.setCityId(ddi.getDataDictItemId());
								}
							}
							 if(addressComponent.getDistrict() != null && !"".equals(addressComponent.getDistrict())){
								 SysRegion sr2=sysRegionService.getRegionIdByRegionName(addressComponent.getCity());
								 appLocation.setAddrRegion3Name(addressComponent.getDistrict());
								 SysRegion s= new SysRegion();
								  s.setRegionName(addressComponent.getDistrict());
								 List<SysRegion> sr3=sysRegionService.list(new Query(s));
								 if(sr3 != null && sr3.size()>0){
									 appLocation.setAddrRegion3Id(sr3.get(0).getParentId());
								 }
							 }
							 AppLocation a =new AppLocation();
							 a.setMemberNo(memberNo);
							 a.setType(1);
							 List<AppLocation> app=appLocationService.getAppLocationList(new Query(a));
							 if(app != null && app.size()>0){
								 appLocation.setId(app.get(0).getId());
								 appLocationService.updateAppLocation(appLocation, getOperator());
							 }else{
								 appLocationService.addAppLocation(appLocation, getOperator());
							 }
							
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
		}
	
		
		return orderService.getNowadayOrder(memberNo,DEVICE_KEY);
	}
	
	
	/**
	 * 取车时或者还车时拍照 和细节描述
	 * 
	 * @param orderNo
	 *            订单编号
	 * @param type 1 取车  2 还车 
	 *            
	 * @param 
	 * @return
	 */
	@RequestMapping("/photoCarUrl")
	@ResponseBody
	public ResultInfo<Order> photoCarUrl(String orderNo, String type, String carUrl1, String carUrl2,String carUrl3,String carUrl4,String carTakeMemo,String carBackMemo,Integer pickupcarstatus,Integer returncarstatus) {
		Order o=orderService.getOrder(orderNo);
		
		
		if (type.equals("1")) {
			o.setPickupcarstatus(pickupcarstatus);
		}else if(type.equals("2")){
			o.setReturncarstatus(returncarstatus);
		}
		ResultInfo<Order> result=new ResultInfo<Order>();
		String imgPaths = imgPath;
		String url ="";
		String pathUrl1=carUrl1.substring(imgPaths.length()+1);
		String pathUrl2=carUrl2.substring(imgPaths.length()+1);
		String pathUrl3=carUrl3.substring(imgPaths.length()+1);
		
		if(carUrl4!=null&&!carUrl4.equals("")){//如果第四张拍照了则添加第四张
			String pathUrl4=carUrl4.substring(imgPaths.length()+1);
			url=pathUrl1+","+pathUrl2+","+pathUrl3+","+pathUrl4;
		}else{
			url=pathUrl1+","+pathUrl2+","+pathUrl3;
		}
		if (type.equals("1")) {
			o.setCarTakeUrl(url);
			o.setCarTakeMemo(carTakeMemo);
			result=orderService.updateOrder(o, getOperator());
		}else if(type.equals("2")){
			o.setCarBackUrl(url);
			o.setCarBackMemo(carBackMemo);
			result=orderService.updateOrder(o, getOperator());
		}else{
			result.setCode("0");
		}
			result.setData(null);
			return result;
			
		}
	

	
	
	
	
	/**
	 * 抵扣金额套餐
	 * @param orderNo
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping("dikouPackOrdeAmount")
	@ResponseBody ResultInfo<Double> dikouPackOrdeAmount(String orderNo) throws ParseException{
		ResultInfo<Double> res = new ResultInfo<Double>();
		SysParam param = sysParamService.getByParamKey("IS_DIKOUPACKORDERAMOUNT");
		if(param==null||param.getParamValue()==null){
			res.setCode("2");
			res.setMsg("不可抵扣");
			return res;
		}
		if(param!=null||param.getParamValue()!=null){
			String isDikouPackOrderAmount = param.getParamValue();
			int isPermitDikou = 0;
			try {
				isPermitDikou = Integer.parseInt(isDikouPackOrderAmount);
				if(isPermitDikou==0){
					res.setCode("2");
					res.setMsg("不可抵扣");
					return res;
				}
			} catch (Exception e) {
				res.setCode("2");
				res.setMsg("不可抵扣");
				return res;
			}
			//允许抵扣
			if(isPermitDikou==1){
				Order beforeDiKouOrder = orderService.getOrder(orderNo);
				if(beforeDiKouOrder==null){
					res.setCode(Constant.FAIL);
					res.setMsg("订单不存在");
					return res;
				}
				//订单已支付
				if(beforeDiKouOrder.getPayStatus()==1){
					res.setCode(Constant.FAIL);
					res.setMsg("订单已支付");
					return res;
				}
				//抵扣前应支付金额
				Double beforePayableAmount = beforeDiKouOrder.getPayableAmount();
				//抵扣
				Order afterDikouOrder = orderService.diKouPricingPackOrderAmout(beforeDiKouOrder, beforeDiKouOrder.getPayableAmount());
				//抵扣前应支付金额>抵扣后应支付金额
				if(beforePayableAmount.compareTo(afterDikouOrder.getPayableAmount())>0){
					Order order = new Order();
					order.setOrderNo(beforeDiKouOrder.getOrderNo());
					order.setPackAmountDiscountAmount(afterDikouOrder.getPackAmountDiscountAmount());//抵扣后的总抵扣金额
					if(afterDikouOrder.getPayableAmount().compareTo(0d)==0){
						order.setPayStatus(1);
					}
					order.setPayableAmount(afterDikouOrder.getPayableAmount());//抵扣后的应支付金额
					ResultInfo<Order> result = orderService.updateOrder(order, null);
					if(result.getCode().equals(Constant.SECCUESS)){
						res.setCode(result.getCode());
						//实际抵扣金额
						Double actualDikouAmout = ECCalculateUtils.sub(beforePayableAmount,afterDikouOrder.getPayableAmount());
						res.setData(actualDikouAmout);
					}else{
						res.setCode(result.getCode());
						res.setMsg("抵扣失败");
					}
				}else{
					res.setCode("3");
					res.setMsg("暂无余额抵扣");
				}
			}
		}
		return res;
	}
	
	/**
	 * 将查出来的Order对象转OrderStatusVo
	 * */
	public OrderStatusVo orderToOrderStatusVo(Order order, OrderStatusVo orderStatusVo) {
		//如果首次开门时间不为空，那就开门状态就是已开车门；
		if (order.getOpenCarDoorTime()!=null) {
			orderStatusVo.setIsOpenDoor(1);
		}else {
			orderStatusVo.setIsOpenDoor(0);//是否已经开过门
		}
		orderStatusVo.setMemberNo(order.getMemberNo());//订单ID
		orderStatusVo.setOrderNo(order.getOrderNo());//会员ID
		orderStatusVo.setOrderStatus(order.getOrderStatus());//订单状态
		orderStatusVo.setCreateTime(ECDateUtils.formatTime(order.getCreateTime()));//下单时间  formatTime
		orderStatusVo.setOpenCarDoorTime(ECDateUtils.formatTime(order.getOpenCarDoorTime()));//取车时间   formatTime
		orderStatusVo.setStrtChargingTime(sysParamService.getByParamKey(CarConstant.cancelordertime_param_key).getParamValue());//自动计费时长
		orderStatusVo.setCancelOrderTime(sysParamService.getByParamKey(CarConstant.cancelordertime_param_key).getParamValue());//自动取消订
		orderStatusVo.setNowTime(ECDateUtils.formatTime(new Date()));
//		order=orderService.orderTripView(order).getData();
		orderStatusVo.setOrderDuration(order.getOrderDuration());
		orderStatusVo.setNowAmount(ECNumberUtils.roundDoubleWithScale(order.getOrderAmount(),2));//订单计费
		// 车牌号
		orderStatusVo.setCarPlateNo(order.getCarPlateNo());
		//key,这里先写死
		orderStatusVo.setDeviceKey(DEVICE_KEY);
//		TERMINAL_DEVICE_NO
		//根据车牌号得到车辆状态
		CarStatus carStatus = carStatusService.getCarStatus(order.getCarNo());
		if (carStatus!=null) {
			orderStatusVo.setRangeMileage(carStatus.getRangeMileage());//可续航里程
			orderStatusVo.setLatitude(carStatus.getLatitude());//坐标纬度
			orderStatusVo.setLongitude(carStatus.getLongitude());//坐标经度
			
			//MAC地址
			Device d = deviceService.getDevice(carStatus.getDeviceNo());
			if (d!=null) {
				orderStatusVo.setMacAddr(d.getMacAddr());
				orderStatusVo.setDeviceSn(d.getDeviceSn());
				orderStatusVo.setWxDeviceId(d.getWxDeviceId());
			}
		}
		return orderStatusVo;
	}

	/**
	 * 判断当前是否有正在进行的订单
	 */
	@RequestMapping("/judgeNowadayOrder")
	@ResponseBody
	public ResultInfo<Boolean> judgeNowadayOrder(String memberNo){
		ResultInfo<Boolean> result = new ResultInfo<>();
		int n = orderService.judgeNowadayOrder(memberNo);
		if (n==1) {
			result.setCode(OrderConstant.success_code);
			result.setData(true);
			result.setMsg("");
		}else {
			result.setCode(OrderConstant.fail_code);
			result.setData(false);
			result.setMsg(OrderConstant.fail_msg);
		}
		return result;
	}
	
	/**
	 * 订单中的开门(首次开门时间)
	 */
	@RequestMapping("/openDoor")
	@ResponseBody
	public ResultInfo<String> openDoor(String orderNo){
		ResultInfo<String> result = new ResultInfo<>();
		//判断订单id是否为空
		if (orderNo!=null&&orderNo.trim().length()>0) {
			Date OpenCarDoorTime = new Date();
			result = orderService.startOrdeOpenCarDoorTime(orderNo,OpenCarDoorTime);
			if (result.getCode().equals("1")) {
				//记录取车的地点
				orderService.recordTakeCarLocation(orderNo);
			}
		}
		return result;
	}
	

	/**
	 * 订单中的计费
	 */
	@RequestMapping("/charge")
	@ResponseBody
	public ResultInfo<String> orderCharge(String orderNo){
		ResultInfo<String> result = new ResultInfo<>();
		if (orderNo!=null&&orderNo.trim().length()>0) {
			Date StartBillingTime = new Date();
			result = orderService.setStartBillingTime(orderNo,StartBillingTime);
		}
		return result;
	}

	/**
	 * 查看未开发票的订单记录
	 * 查询条件：时间（当前一个月内），开票的状态：未开
	 */
//	@RequestMapping("/orderinvoiceRecord")
//	@ResponseBody
//	public ResultInfo<List<OrderInvoiceVo>> orderInvoiceRecord(String memberNo){
//		
//		ResultInfo<List<OrderInvoiceVo>> result = new ResultInfo<List<OrderInvoiceVo>>();
//		Date date  = ECDateUtils.getDateMonthAfter(new Date(), -1);
//		
//		if (memberNo!=null&&memberNo.trim().length()>0) {
//			Order or = new  Order();
//			or.setMemberNo(memberNo);
//			or.setPayStatus(1);			// 支付状态（0、未支付，1、已支付，默认0）
//			or.setIsNeedInvoice(0);		// Integer isNeedInvoice 是否需开票（0，不开票，1、需开票，默认0）
//			or.setIsInvoiceIssued(0);	//Integer isInvoiceIssued  是否已开发票（0，未开票，1，已开票，默认0）
//			or.setStartBillingTimeStart(date);//和计费开始时间比较
//			
//			Query q = new Query();
//			q.setQ(or);//查询的条件
//			List<Order> orders=new ArrayList<Order>();
//			List<Order> orList = orderService.queryAllInvoice(q);
//			for(Order order:orList){
//				if(order.getPayableAmount()>0){
//				Order order2=new Order();
//				order2.setOrderNo(order.getOrderNo());
//				order2.setPayableAmount(order.getPayableAmount());
//				order2.setPayStatus(order.getPayStatus());
//				order2.setStartBillingTime(order.getStartBillingTime());
//				orders.add(order2);
//				}
//			}
//			result = orderInvoiceToVo(result, orders);
//		}else {
//			result.setCode(OrderConstant.fail_code);
//			result.setMsg(OrderConstant.fail_parameters);
//		}
//		return result;
//	}
	
	/**
	 * 查看未开发票的订单记录  点击 更多
	 * 查询条件：开票的状态：未开
	 */
	@RequestMapping("/moreOrderInvoiceRecord")
	@ResponseBody
	public ResultInfo<List<OrderInvoiceVo>> moreOrderInvoiceRecord(String memberNo,@RequestParam("pageNo") int pageNo
			) throws ParseException {
		
		ResultInfo<List<OrderInvoiceVo>> result = new ResultInfo<List<OrderInvoiceVo>>();
		Date date  = new Date();
		
		if (memberNo!=null&&memberNo.trim().length()>0) {
			Order or = new  Order();
			or.setMemberNo(memberNo);
			or.setPayStatus(1);			// 支付状态（0、未支付，1、已支付，默认0）
			or.setIsNeedInvoice(0);		// Integer isNeedInvoice 是否需开票（0，不开票，1、需开票，默认0）
			or.setIsInvoiceIssued(0);	//Integer isInvoiceIssued  是否已开发票（0，未开票，1，已开票，默认0）
			or.setStartBillingTimeEnd(date);//和计费开始时间比较
			int pageSize = 10;
			//根据系统参数 查找对应参数来获取 
			SysParam  sysParamOrder=sysParamService.getByParamKey(CarConstant.orderPageSize);
			if(sysParamOrder != null){
				pageSize=Integer.parseInt(sysParamOrder.getParamValue());
			}
			Query q = new Query(pageNo,pageSize,or);
			List<Order> orList = new ArrayList<Order>();
			PageFinder<Order>	finder = orderService.getOrderPagedListPayableAmount(q);
			for(Order order:finder.getData()){
				if(order.getPayableAmount()>0){
				Order order2=new Order();
				order2.setOrderNo(order.getOrderNo());
				order2.setPayableAmount(order.getPayableAmount());
				order2.setPayStatus(order.getPayStatus());
				order2.setStartBillingTime(order.getStartBillingTime());
				order2.setCarPlateNo(order.getCarPlateNo());
				order2.setCarModelName(order.getCarModelName());
				order2.setOrderMileage(order.getOrderMileage());
				order2.setStartParkNo(order.getStartParkNo());
				order2.setActualTerminalParkNo(order.getActualTerminalParkNo());
				order2.setOrderDuration(order.getOrderDuration());
				order2.setFinishTime(order.getFinishTime());
				orList.add(order2);
				}
			}
			result = orderInvoiceToVo(result, orList);
		}else {
			result.setCode(OrderConstant.fail_code);
			result.setMsg(OrderConstant.fail_parameters);
		}
		return result;
	}
	
	private ResultInfo<List<OrderInvoiceVo>> orderInvoiceToVo(ResultInfo<List<OrderInvoiceVo>> result, List<Order> orList) {
		if (orList!=null&&orList.size()>0) {
			List<OrderInvoiceVo> invoiceVooList = new ArrayList<OrderInvoiceVo>();
			for (Order order : orList) {
				OrderInvoiceVo or = new OrderInvoiceVo();
				or.setOrderNo(order.getOrderNo());
				or.setPayableAmount(order.getPayableAmount());
				or.setPayStatus(order.getPayStatus());
				or.setStartBillingTime(ECDateUtils.formatTime(order.getStartBillingTime()));
				if(order.getStartBillingTime() != null){
					Calendar c = Calendar.getInstance();
					c.setTime(order.getStartBillingTime());
					or.setYear(c.get(Calendar.YEAR));
					//月份 ;
					or.setMonth(order.getStartBillingTime().getMonth()+1);
				}
				
				//订单结束时间
				or.setFinishTime(ECDateUtils.formatTime(order.getFinishTime()));
				or.setCarPlateNo(order.getCarPlateNo());
				or.setCarModelName(order.getCarModelName());
				Car car= carService.getCarByPlateNo(order.getCarPlateNo());
				if(car!= null){
					or.setCarBrandName(car.getCarBrandName());
				}
				//订单里程
				if(order.getOrderMileage() != null){
					or.setOrderMileage(order.getOrderMileage());
				}
				//取车场站
				if(order.getStartParkNo() != null){
					Park park =parkService.getPark(order.getStartParkNo());
					if(park != null){
						or.setStartPark(park.getParkName());
					}else{
						or.setStartPark("");
					}
				}else{
					or.setStartPark("");
				}
				
				//顶单时长
				or.setOrderDuration(order.getOrderDuration());
				//还车场站
				if(order.getActualTerminalParkNo() != null){
					Park park =parkService.getPark(order.getActualTerminalParkNo());
					if(park != null){
						or.setActualTerminalPark(park.getParkName());
					}else{
						or.setActualTerminalPark("");
					}
				}else{
					or.setActualTerminalPark("");
				}
				
				
				invoiceVooList.add(or);
			}
			result.setData(invoiceVooList);
			result.setCode(OrderConstant.success_code);
			result.setMsg("");
		}else {
			result.setCode(OrderConstant.fail_code);
			result.setMsg("无可开的订单发票！");
		}
		return result;
	}

	/**
	 *取消订单（修改订单和车辆状态）
	 * */
	@RequestMapping("/cancelOrder")
	@ResponseBody
	public ResultInfo<OrderVO> cancelOrder(String orderNo) {
		//取消订单时增加关闭动力的指令，但无需等待成功响应
		Order order = orderService.getOrder(orderNo);
		Device device = deviceService.getDeviceByCarNo(order.getCarNo());
		try {
			if(device!=null&&"1".equals(device.getVersionType())){
				carStatusService.controlPower(device.getDeviceSn(),"0");
			}
		} catch (Exception e) {
		}
		ResultInfo<Order> res=orderService.cancelOrder(orderNo);
		ResultInfo<OrderVO> result = new ResultInfo<OrderVO>();
		return orderToVoOne(result,res.getData());
	}
	/**
	 * 判断取消订单还剩多少次
	 * @throws Exception 
	 */
	@RequestMapping("/cancelOrderSum")
	@ResponseBody
	public ResultInfo<OrderCancelVo> cancelOrderSum(String memberNo) throws Exception {
		//取消订单时增加关闭动力的指令，但无需等待成功响应
		String sum="";
		String mm="";
		ResultInfo<OrderCancelVo> result = new ResultInfo<OrderCancelVo>();
		OrderCancelVo cv=new OrderCancelVo();
		result.setData(cv);
		try {
			SysParam sysParam =sysParamService.getByParamKey("CANCEL_ORDER_SUM");
			if(sysParam != null && sysParam.getParamValue() != null && !"".equals(sysParam.getParamValue())){
				result.getData().setCancelSum(sysParam.getParamValue());
				Order or=new Order();
				or.setMemberNo(memberNo);
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
				String dateNowStr = sdf.format(new Date());
				String start=dateNowStr+" 00:00:00";
				String end=dateNowStr+" 23:59:59";
				or.setCreateTimeStart(ECDateUtils.parseTime(start));
				or.setCreateTimeEnd(ECDateUtils.parseTime(end));
				or.setOrderStatus(4);
				Query qu=new Query(or);
				List<Order> orders= orderService.getOrderList(qu);
				if(orders != null && orders.size()>0){
					if(orders.size() >= Integer.parseInt(sysParam.getParamValue())){
						result.setCode(OrderConstant.order_fail);
						result.setMsg("您达到取消订单数上限 今天在不能执行此操作！");
						return result;
					}else{
						result.setCode(OrderConstant.success_code);
						sum=Integer.parseInt(sysParam.getParamValue())- orders.size()+"";
						mm="今天取消订单最多"+sysParam.getParamValue()+" 今天你还可以取消"+sum+"次,是否要取消订单？超过取消次数就不能订车！";
						result.setMsg(mm);
						result.getData().setSum(sum);
						return result;
					}
				}else{
					result.setCode(OrderConstant.success_code);
					sum=sysParam.getParamValue();
					mm="今天取消订单最多"+sysParam.getParamValue()+" 今天你还可以取消"+sum+"次,是否要取消订单？ 超过取消次数就不能订车！";
					result.setMsg(mm);
					result.getData().setSum(sum);
					return result;
				}
				
			}else{
				result.setCode(OrderConstant.fail_code);
				result.setMsg("系统参数没有配置上限的值");
				return result;
			}
			
		} catch (Exception e) {
		}
		
		return result;
	}

		

		
		/**
		 *行程中的订单信息查看（所查询的订单时长和计费为动态获取，非订单表数据）
		 * @throws ParseException 
		 * */
		@RequestMapping("/tripOrderDetail")
		@ResponseBody
		public ResultInfo<OrderVOTrip> tripOrderDetail(String orderNo,String memberNo) throws ParseException {
			if(orderService.getOrder(orderNo)!=null){
//				if(orderService.getOrder(orderNo).getOrderStatus().equals(3)){
//					ResultInfo<OrderVOTrip> result = new ResultInfo<OrderVOTrip>();
//					result.setCode(Constant.FAIL);
//					result.setMsg(OrderConstant.overOrder);
//					return result;
//				}else if(orderService.getOrder(orderNo).getOrderStatus().equals(2)){
					ResultInfo<Order> res=orderService.getTripOrder(orderNo);
					ResultInfo<OrderVOTrip> result = new ResultInfo<OrderVOTrip>();
					return orderToVoTripOne(result,res.getData());
//				}
			}else{
				ResultInfo<OrderVOTrip> result = new ResultInfo<OrderVOTrip>();
				result.setCode(Constant.FAIL);
				result.setMsg(OrderConstant.noOrderExsit);
				return result;
			}
//			return null;
		}
		
	/**
	 * 方法说明：删除订单
	 */
	
	@RequestMapping("/deleteOrder")
	@ResponseBody
	public ResultInfo<String> deleteOrder(String orderNo){
		ResultInfo<String> result = new ResultInfo<>();
		//根据订单id得到订单对象
		Order or = orderService.getOrder(orderNo);
		//订单在取消或者是已结算的情况下才可以删除
		if ((or.getOrderStatus().equals(4)&&or.getPayStatus().equals(0)&&or.getPayableAmount()<=0.0)||(or.getOrderStatus().equals(3)&&or.getPayStatus().equals(1))) {
			ResultInfo<Order> rs = orderService.delOrder(orderNo, null);
			if (rs.getCode().equals("1")) {
				result.setCode(OrderConstant.success_code);
				result.setMsg("");
			}else {
				result.setCode(OrderConstant.fail_code);
				result.setMsg(OrderConstant.fail_msg);
			}
		}else {
			result.setCode(OrderConstant.order_del_code);
			result.setMsg(OrderConstant.order_del_msg);
		}
		return result;
	}
	
	/**
	 * 方法说明:查看近一个月的订单
	 */
	@RequestMapping("/myOrder")
	@ResponseBody
	public ResultInfo<List<OrderVO>> getOneMonthMyOrder(String memberNo){
		
		ResultInfo<List<OrderVO>> result = new ResultInfo<List<OrderVO>>();
		Date date  = ECDateUtils.getDateMonthAfter(new Date(), -1);
//		Date date  = new Date();
//		String month = (date.getMonth()+1)+"";
		if (memberNo!=null&&memberNo.trim().length()>0) {
			Order or = new  Order();
			or.setMemberNo(memberNo);
//			or.setStartBillingTimeStart(date);//和计费开始时间比较
			or.setIsDelete(0);//订单删除状态
			or.setCreateTimeStart(date);
			Query q = new Query();
			q.setQ(or);//查询的条件
			
			List<Order> orList = orderService.getOrderList(q);
			
			result = orderToVo(result, orList);
			
		}else {
			result.setCode(OrderConstant.fail_code);
			result.setMsg(OrderConstant.fail_parameters);
		}
		return result;
	}
	
	/**
	 * 方法说明：查看更多的订单
	 */
	@RequestMapping("/moreOrder")
	@ResponseBody
	public ResultInfo<List<OrderVO>> getMoreOrder(String memberNo,@RequestParam("pageNo") int pageNo
			) throws ParseException {

		ResultInfo<List<OrderVO>> result = new ResultInfo<List<OrderVO>>();
		Date date  =new Date();
//		Date date  = new Date();
//		String month = (date.getMonth()+1)+"";
		if (memberNo!=null&&memberNo.trim().length()>0) {
			Order or = new  Order();
			or.setMemberNo(memberNo);
//			or.setStartBillingTimeStart(date);//和计费开始时间比较
			or.setIsDelete(0);//订单删除状态
			or.setCreateTimeEnd(date);
			int pageSize = 10;
			//根据系统参数 查找对应参数来获取 
			SysParam  sysParamOrder=sysParamService.getByParamKey(CarConstant.orderPageSize);
			if(sysParamOrder != null){
				pageSize=Integer.parseInt(sysParamOrder.getParamValue());
			}
			Query q = new Query(pageNo,pageSize,or);
//			q.setQ(or);//查询的条件
//			q.setPageNo(pageNo);
//			q.setPageSize(pageSize); 
			
			//根据条件，分页查找Order记录
			PageFinder<Order>	finder = orderService.getOrderPagedList(q);
			List<Order> orList = finder.getData();
			result = orderToVo(result, orList);
		}else {
			result.setCode(OrderConstant.fail_code);
			result.setMsg(OrderConstant.fail_parameters);
		}
		return result;
	}

	/**
	 * 方法说明:将按特定条件查询的记录转换成自定vo对象
	 */
	 ResultInfo<List<OrderVO>> orderToVo(ResultInfo<List<OrderVO>> result,List<Order> orList){
	
		if (orList!=null&&orList.size()>0) {
			List<OrderVO> voList = new ArrayList<OrderVO>();
			for (Order order : orList) {
				OrderVO or = new OrderVO();
				or.setAppointmentTime(ECDateUtils.formatTime(order.getAppointmentTime()));
				or.setMemberNo(order.getMemberNo());
				//订单金额计算
				if(order.getOrderStatus()==2){
					try {
						Order temp = orderService.orderTripView(order).getData();
						//订单金额
						or.setOrderAmount(ECNumberUtils.roundDoubleWithScale(temp.getOrderAmount(),2));
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}else{
					//订单金额
					or.setOrderAmount(ECNumberUtils.roundDoubleWithScale(order.getOrderAmount(),2));
				}
				or.setOrderNo(order.getOrderNo());
				or.setOrderStatus(order.getOrderStatus());
				or.setPayableAmount(ECNumberUtils.roundDoubleWithScale(order.getPayableAmount(),2));
				if(order.getDiscountAmount()==null){
					order.setDiscountAmount(order.getDiscountAmount());
				}else{
					order.setDiscountAmount(0d);
				}
				Car car =carService.getCarByPlateNo(order.getCarPlateNo());
				if(car != null){
					or.setCarPlateNo(order.getCarPlateNo());
					or.setCarModelName(car.getCarModelName());
					or.setCarBrandName(car.getCarBrandName());
				}
				//查询订单 事故
				or.setAccidentStatus(order.getIsAccident());
				//查询订单 违章
				if(order.getIsIllegal()==1){
					or.setIllegalStatus(order.getIsIllegal());
				}else{
					if(order.getFinishTime() ==null || "".equals(order.getFinishTime())){
						or.setIllegalStatus(order.getIsIllegal());
					}else{
						Date date =new Date();
						SysParam sysp = sysParamService.getByParamKey(DepositConstant.no_refund_days);
						Long days=Long.parseLong(sysp.getParamValue());
						Long day=ECDateUtils.differDays(date, order.getFinishTime());
						if(day>days){
							or.setIllegalStatus(2);
						}else{
							or.setIllegalStatus(0);
						}
					}
				}
				
				//开始时间
				if(order.getStartBillingTime() != null){
					or.setStartBillingTime(ECDateUtils.formatTime(order.getStartBillingTime()));
				}
				//结束时间
				if(order.getFinishTime()!= null){
					or.setFinishTime(ECDateUtils.formatTime(order.getFinishTime()));
				}else{
					or.setFinishTime(ECDateUtils.formatTime(new Date()));
					order.setFinishTime(new Date());
				}
				or.setOrderDuration(ECDateUtils.differMinutes(order.getStartBillingTime(), order.getFinishTime()).intValue());
				if(order.getPackMinutesDiscountAmount()==null){
					order.setPackMinutesDiscountAmount(order.getPackMinutesDiscountAmount());
				}else{
					order.setPackMinutesDiscountAmount(0d);
				}
//				or.setNeedPayaAmount(ECNumberUtils.roundDoubleWithScale(order.getOrderAmount()-order.getPackMinutesDiscountAmount(),2));
				or.setNeedPayaAmount(ECNumberUtils.roundDoubleWithScale(order.getPayableAmount(),2));
				//取车地址
				if(order.getActualTakeLoacton() != null && !"".equals(order.getActualTakeLoacton()) ){
					or.setActualTakeLoacton(order.getActualTakeLoacton());
				}else{
					if(order.getStartAddress() != null && !"".equals(order.getStartAddress())){
						or.setActualTakeLoacton(order.getStartAddress());
					}else{
						or.setActualTakeLoacton("");
					}
				}
				
				// 实际还车地点（场站名称，通过场站编号得到）
				if(order.getOrderStatus()==3 || order.getOrderStatus()==4 ){
					Park park2=new Park();
					if(order.getActualTerminalParkNo()!=null&&!order.getActualTerminalParkNo().equals("")){
						 park2= parkService.getPark(order.getActualTerminalParkNo());
						 if (park2!=null) {
								String location = park2.getParkName();
								or.setActualTerminalLocation(location);
							}
					}else{
						//有可能是场站外的车
						if(order.getTerminalAddress() != null && !"".equals(order.getTerminalAddress())){
							or.setActualTerminalLocation(order.getTerminalAddress());
						}else{
							if(order.getStartAddress() != null && !"".equals(order.getStartAddress())){
								or.setActualTerminalLocation(order.getStartAddress());
							}else{
								or.setActualTerminalLocation("");
							}
						}
					}
					
				}else{
					or.setActualTerminalLocation("");
				}
				
				//订单行驶的里程，这里由盒子给出数据，
				if (order.getOrderMileage()!=null) {
					or.setOrderMileage(ECNumberUtils.roundDoubleWithScale(order.getOrderMileage(),2));
				}else {
					or.setOrderMileage(0.0);
				}
				if(order.getWarningOrder() != null){
					or.setWarningOrder(order.getWarningOrder());
				}else{
					or.setWarningOrder(0);
				}
				if(order.getPayStatus().equals(0)){
					or.setAlreadPayAmount(0d);
				}else if(order.getPayStatus().equals(1)){
					or.setAlreadPayAmount(ECNumberUtils.roundDoubleWithScale(order.getPayableAmount(),2));
				}
				or.setPayStatus(order.getPayStatus());
				or.setCreateTime(ECDateUtils.formatTime(order.getCreateTime()));
				voList.add(or);
			}
			result.setData(voList);
			result.setCode(OrderConstant.success_code);
			result.setMsg(""); 
		}else {
			result.setCode(OrderConstant.fail_code);
			result.setMsg("无订单");
		}
		return result;
	}
	 
	 	/**
		 * 方法说明:将按特定order对象转换成自定vo对象
		 */
		 ResultInfo<OrderVO> orderToVoOne(ResultInfo<OrderVO> result,Order order){
			 if(order!=null){
					OrderVO or = new OrderVO();
					or.setAppointmentTime(ECDateUtils.formatTime(order.getAppointmentTime()));
					or.setMemberNo(order.getMemberNo());
					or.setOrderAmount(order.getOrderAmount());
					or.setOrderNo(order.getOrderNo());
					or.setOrderStatus(order.getOrderStatus());
					or.setPayableAmount(order.getPayableAmount());
					or.setCreateTime(ECDateUtils.formatTime(order.getCreateTime()));
					result.setData(or);
					result.setCode(OrderConstant.success_code);
					result.setMsg(""); 
			 }else{
				 result.setCode(OrderConstant.fail_code);
				 result.setMsg(OrderConstant.fail_msg); 
			 }
			return result;
		}
		 
		 /**
			 * 方法说明:将按特定order对象转换成自定vo对象
		 * @throws ParseException 
			 */
			 ResultInfo<OrderVOTrip> orderToVoTripOne(ResultInfo<OrderVOTrip> result,Order order) throws ParseException{
				 if(order!=null){
					 OrderVOTrip or = new OrderVOTrip();
					 SysParam sysParams = sysParamService.getByParamKey("RETURN_CAR_OUTSIDE_SWITCH");//1.只能在场内还车；2.场站内外都允许还车；3.没有场站只有区域还车
						if(sysParams != null){
							if("1".equals(sysParams.getParamValue())){
								or.setReturnCarStatus(1);
							}else if("2".equals(sysParams.getParamValue())){
								or.setReturnCarStatus(2);
							}else{
								or.setReturnCarStatus(3);
							}
						}
						or.setMemberNo(order.getMemberNo());
						or.setOrderNo(order.getOrderNo());
						or.setCarPlateNo(order.getCarPlateNo());
						or.setCarModelName(order.getCarModelName());
						or.setPayStatus(order.getPayStatus());
						//这里的计费时间如果为空的话，那就是在下单时间的基础上往后推迟20分钟
						if (order.getStartBillingTime()!=null) {
							or.setStartBillingTime(ECDateUtils.formatTime(order.getStartBillingTime()));
						}else {
							//下单时间加20分钟
							Calendar c = Calendar.getInstance();
							c.setTime(order.getCreateTime());
							c.add(Calendar.MINUTE, 20);
							or.setStartBillingTime(ECDateUtils.formatTime(c.getTime()));
						}
						or.setOrderStatus(order.getOrderStatus());
						//实际取车地点
						Park park=null;
						if(order.getStartParkNo()!=null&& !"".equals(order.getStartParkNo())){
							park = parkService.getPark(order.getStartParkNo());
						}
						if (park!=null) {
							String location = park.getParkName();
							or.setActualTakeLoacton(location);
							or.setParkNo(order.getStartParkNo());
							
						}
						
						or.setActualTakeLoacton(order.getActualTakeLoacton());
						
						// 实际还车地点（场站名称，通过场站编号得到）
						Park park2=null;
						if(order.getActualTerminalParkNo()!=null&& !"".equals(order.getActualTerminalParkNo())){
							park2 = parkService.getPark(order.getActualTerminalParkNo());
						}
						if (park2!=null) {
							String location = park2.getAddrRegion1Name()+park2.getAddrRegion2Name()+park2.getAddrRegion3Name()+park2.getAddrStreet();
							or.setActualTerminalLocation(location);
						}
						// 结束时间
						or.setFinishTime(ECDateUtils.formatTime(order.getFinishTime()));
						or.setOrderDuration(order.getOrderDuration());
						if(orderService.orderTripView(order).getData()== null){
							 result.setCode(OrderConstant.fail_code);
							 result.setMsg("计费规则失效，请联系客服"); 
						}else{
							//动态获取订单的计费时长，金额，结束时间（当前时间）
							order=orderService.orderTripView(order).getData()== null ? order :orderService.orderTripView(order).getData();
							//订单行驶的里程，这里由盒子给出数据，
							if (order.getOrderMileage()!=null) {
								or.setOrderMileage(ECNumberUtils.roundDoubleWithScale(order.getOrderMileage(),2));
							}else {
								or.setOrderMileage(0.0);
							}
							or.setNowAmount(ECNumberUtils.roundDoubleWithScale(order.getOrderAmount(),2));
							//时长金额
							or.setMinutesAmount(ECNumberUtils.roundDoubleWithScale(order.getMinutesAmount(),2));
							//里程金额
							or.setMileageAmount(ECNumberUtils.roundDoubleWithScale(order.getMileageAmount(),2));
							//积分（在数据库中 暂时没有这个字段，统一默认为0）
							or.setIntegral(0);
							//可续航里程和车辆的经纬度坐标
							if(order.getCarNo()!=null&&!order.getCarNo().equals("")){
								CarStatus c=carStatusService.getCarStatus(order.getCarNo());
								if(c!=null){
									if(c.getRangeMileage()!=null){
										or.setRangeMileage(c.getRangeMileage());
									}else{
										or.setRangeMileage(0.0);
									}
									if(c.getPower() != null){
										or.setPower(c.getPower());
									}else{
										or.setPower(0d);
									}
									// 坐标转换
									if(c.getLongitude()!=null&&c.getLatitude()!=null){
										double[] bdCoord = ECGeoCoordinateTransformUtil.wgs84tobd09(c.getLongitude(), c.getLatitude());
										or.setLongitude(bdCoord[0]);// 经度（百度坐标）
										or.setLatitude(bdCoord[1]);// 纬度（百度坐标）
									}
								}
							}
							if(order.getWarningOrder() != null){
								or.setWarningOrder(order.getWarningOrder());
							}else{
								or.setWarningOrder(0);
							}
							//红包车红包标志
							CarRedPacket carRedPacket = carRedPacketService.getCarRedPacketByCarPlateNo(order.getCarPlateNo(), "2");
							if (carRedPacket!=null) {
								or.setIsCarRedPakcet("1");
								CarRedPacketVo carRedPacketVo =new CarRedPacketVo();
								carRedPacketVo.setIsCharge(carRedPacket.getIsCharge());
								carRedPacketVo.setIsOrderAmountLimit(carRedPacket.getIsOrderAmountLimit());
								if(carRedPacket.getIsOrderAmountLimit() ==1){
									carRedPacketVo.setOrderAmountLimit(carRedPacket.getOrderAmountLimit());
								}else{
									carRedPacketVo.setOrderAmountLimit(0.0);
								}
								or.setCarRedPacketVo(carRedPacketVo);
							}
							CarStatus cs = carStatusService.getCarStatus(order.getCarNo());
							if(cs != null){
								if(cs.getChargeState() != null){
									or.setChargeState(cs.getChargeState());
								}else{
									or.setChargeState(0);
								}
							
							}
							result.setData(or);
							result.setCode(OrderConstant.success_code);
							result.setMsg(""); 
						}
						
				 }else{
					 result.setCode(OrderConstant.fail_code);
					 result.setMsg(OrderConstant.fail_msg); 
				 }
			
				return result;
			}	
			 //获取订单详情
			 @RequestMapping("/getOrderDetail")
			@ResponseBody
				public ResultInfo<OrderVOTrip> getOrderDetail(String orderNo) throws ParseException{
					ResultInfo<OrderVOTrip> result = new ResultInfo<OrderVOTrip>();
					//判断传过来的orderNo是否为空
					if (orderNo!=null&&orderNo.trim().length()>0) {
							//得到当前订单 
							Order order = orderService.getOrder(orderNo);
							if (order!=null) {
								result = orderToVoDetailOne(result,order);
							}else if(order==null){
								result.setCode(OrderConstant.fail_code);
								result.setMsg(OrderConstant.noOrderExsit);
							}
					}
				return result;
				} 
			 /**
				 * 方法说明:将按特定order对象转换成自定vo对象
			 * @throws ParseException 
				 */
				 ResultInfo<OrderVOTrip> orderToVoDetailOne(ResultInfo<OrderVOTrip> result,Order order) throws ParseException{
					 if(order!=null){
						 OrderVOTrip or = new OrderVOTrip();
						 //支付方式
						 if(order.getPaymentMethod() != null){
							 or.setPayType(order.getPaymentMethod());
						 }
							or.setMemberNo(order.getMemberNo());
							//or.setMemberName(order.getMemberName());
							or.setOrderNo(order.getOrderNo());
							or.setCarPlateNo(order.getCarPlateNo());
							//or.setCarModelId(order.getCarModelId());
							or.setCarModelName(order.getCarModelName());
							or.setIsIllegal(order.getIsIllegal());
							or.setIsAccident(order.getIsAccident());
							Car car=carService.getCar(order.getCarNo());
							if(car!=null){
								or.setCarBrandName(car.getCarBrandName());
							}
							if(order.getOrderMileage()!=null){
								or.setOrderMileage(ECNumberUtils.roundDoubleWithScale(order.getOrderMileage(),2));
							}else{
								or.setOrderMileage(0d);
							}
							or.setOrderStatus(order.getOrderStatus());
							
							if(order.getIsTakeInPark() == 0){//场站内取车
								Park park = null;
								if(order.getStartParkNo()!=null&&!order.getStartParkNo().equals("")){
									park=parkService.getPark(order.getStartParkNo());
								}
								
								if (park!=null) {
									//String location = park.getAddrRegion1Name()+park.getAddrRegion2Name()+park.getAddrRegion3Name()+park.getAddrStreet();
									String location = park.getParkName();
									or.setActualTakeLoacton(location);
								}
							}else{
								or.setActualTakeLoacton(order.getStartAddress());
							}
							if(order.getOrderStatus()==3 || order.getOrderStatus()==4){
								if(order.getIsReturnInPark() != null && !"".equals(order.getIsReturnInPark())){
									if(order.getIsReturnInPark() == 0){
										Park park2=new Park();
										if(order.getActualTerminalParkNo()!=null&&!order.getActualTerminalParkNo().equals("")){
											 park2= parkService.getPark(order.getActualTerminalParkNo());
										}
										if (park2!=null) {
											String location = park2.getParkName();
											or.setActualTerminalLocation(location);
										}
									}else{
										or.setActualTerminalLocation(order.getTerminalAddress());
									}
								}else{
									if(order.getStartAddress() != null && !"".equals(order.getStartAddress())){
										or.setActualTerminalLocation(order.getStartAddress());
									}
									
								}
							}else{
								or.setActualTerminalLocation("");
							}
							int orderDurationOfNoCapPerDay = 0;//不满一整天的总时长
							Double mileageOfNoCapPerDay = 0d;//不满一整天的总里程
							Double minutesAmountOfNoCapPerDay = 0d;//不满一整天的总时长费用
							Double mileageAmountOfNoCapPerDay = 0d;//不满一整天的总里程费用
							// 进行中的
							if(order.getOrderStatus().equals(2)){
								Double minutesAmount = 0d;
								Double mileageAmount = 0d;
								Double mileage = 0d;
								Double dayFD = 0d;
								Double billingCapPerDaySum = 0d;
								int orderDuration = 0;
								int daySum=0;//已计费
								boolean discount=true;
								or.setStartBillingTime(ECDateUtils.formatTime(order.getStartBillingTime()));
								or.setFinishTime(ECDateUtils.formatTime(new Date()));
								int minutes=ECDateUtils.differMinutes(order.getStartBillingTime(), new Date()).intValue();
								//or.setOrderDuration(minutes);
								//订单结算开始
								order=orderService.orderTripView(order).getData();
								OrderMileage om = new OrderMileage();
								om.setOrderNo(order.getOrderNo());
								List<OrderMileage> orderMileageList = orderMileageService.getOrderMileageList(new Query(om));
								int i=0;
								List<PearTimeCostVo>  list=new  ArrayList<PearTimeCostVo>();
								for(OrderMileage orderMileage:orderMileageList){
									//判断日期是周几
									int dayOfWeek = ECDateUtils.getDayOfWeek(orderMileage.getOrderMileageDate());
									Double orderMileageDayAmount = orderMileage.getOrderAmountOfDay();
									//查出对应的计费规则
									 if(order.getRuleNo() != null && !"".equals(order.getRuleNo())){
										 PricingRule pr= pricingRuleService.getPricingRule(order.getRuleNo());
										 if(pr != null){//查出起步价
											 or.setBaseFee(pr.getBaseFee());
											 if(pr.getRuleType()==1){
												 PricingRuleCustomizedDate pricingRuleCustomizedDate = pricingRuleCustomizedDateService.getPricingRuleCustomizedDate(pr.getRuleNo(), orderMileage.getOrderMileageDate());
												 if(pricingRuleCustomizedDate!=null){//自定义日期封顶金额
													 dayFD = pricingRuleCustomizedDate.getBillingCapPerDayCustomized();
												 }else if(dayOfWeek==Calendar.SATURDAY||dayOfWeek==Calendar.SUNDAY){//周末封顶金额
													 dayFD = pr.getBillingCapPerDayOrdinary();
												 }else{//工作日封顶金额
													 dayFD = pr.getBillingCapPerDay();
												 }
											 }else if(pr.getRuleType()==2){
												 dayFD = pr.getBillingCapPerDay();
												 //计算普通时段分钟单价--元/分钟
												 Double priceOfMinute = 0.0;
												 if (orderMileage.getMinutes()>0.0) {
													 priceOfMinute = ECCalculateUtils.div(orderMileage.getMinutesAmount(), orderMileage.getMinutes());
												 }
												 //订单是否前多少分钟免时长费
												 SysParam param = sysParamService.getByParamKey("IS_ORDER_FREEMINUTES");
												 if(param!=null&&param.getParamValue()!=null){
													 int isOrderFreeMinutes = Integer.parseInt(param.getParamValue().trim());
													 if(isOrderFreeMinutes==1){
														 or.setIsOrderFreeMinutes(isOrderFreeMinutes);
														 param = sysParamService.getByParamKey("FREEMINUTES");
														 if(param!=null&&param.getParamValue()!=null){
															 int freeMinutes = Integer.parseInt(param.getParamValue().trim());
															 or.setFreeMinutes(freeMinutes);//免费时长
															 if(i==0){
																 if(order.getOrderDuration()>=freeMinutes){
																	//时长费减免freeMinutes分钟
																	orderMileage.setMinutesAmount(ECCalculateUtils.mul(orderMileage.getMinutes()-freeMinutes, priceOfMinute));
																	orderMileage.setMinutesAmount(ECCalculateUtils.round(orderMileage.getMinutesAmount(), 2));//取两位小数
																	//重新计算一天费用
																	orderMileageDayAmount = ECCalculateUtils.add(orderMileage.getMinutesAmount(), orderMileage.getMileageAmount());
																 }else{
																	minutesAmount = 0d;
																	orderMileage.setMinutesAmount(0d);;
																	orderMileageDayAmount = orderMileage.getMileageAmount();
																}
															}
														}
													}
												}
												//计算普通时段里程单价--元/分钟
												Double priceOfMileage = 0d;
												if (orderMileage.getMileage()>0.0) {
													priceOfMileage = ECCalculateUtils.div(orderMileage.getMileageAmount(), orderMileage.getMileage());
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
																	if(orderMileage.getMileage()>0){
																		//freeMinutes分钟的里程费
																		Double freeMileageAmount = ECCalculateUtils.mul(orderMileage.getFreeMileage(), priceOfMileage);
																		//里程费减免freeMinutes分钟的里程费
																		orderMileage.setMileageAmount(ECCalculateUtils.sub(orderMileage.getMileageAmount(), freeMileageAmount));
																		orderMileage.setMileageAmount(ECCalculateUtils.round(orderMileage.getMileageAmount(), 2));//取两位小数
																		//重新计算一天费用
																		orderMileageDayAmount = ECCalculateUtils.add(orderMileage.getMinutesAmount(), orderMileage.getMileageAmount());
																	}
																}else{
																	mileageAmount = 0d;
																	orderMileage.setMileageAmount(0d);
																	//重新计算一天费用
																	orderMileageDayAmount = orderMileage.getMinutesAmount();
																}
															}
														}
													}
												}
												
												if (orderMileage.getPearTimeCost()!=null) {
													int pearkTimeTotalMinutes = 0;
													Double pearkTimeTotalMinutesAmount = 0d;
													Double peakTimeTotalMileage = 0d;
													Double peakTimeTotalMileageAmount = 0d;
													List<PeakHoursCost> peakHoursCostList = JsonUtils.parse2ListObject(orderMileage.getPearTimeCost(), PeakHoursCost.class);;
													if (peakHoursCostList!=null&&peakHoursCostList.size()>0) {
														for (PeakHoursCost peakHoursCost:peakHoursCostList) {
															PearTimeCostVo pearTimeCostVo = new PearTimeCostVo();
															
															String start = ECDateUtils.formatDate(peakHoursCost.getTimePeriodStartTime(), "HH:mm");
															String end= ECDateUtils.formatDate(peakHoursCost.getTimePeriodEndTime(), "HH:mm");
															pearTimeCostVo.setTimePeriod(start+"~"+end);
															String Day = ECDateUtils.formatDate(peakHoursCost.getTimePeriodStartTime(), "MM/dd");
															pearTimeCostVo.setTimeDay(Day);
															pearTimeCostVo.setTimePeriodMileage(String.valueOf(peakHoursCost.getTimePeriodMileage()));
															pearTimeCostVo.setTimePeriodMileageAmount(String.valueOf(peakHoursCost.getTimePeriodMileageAmount()));
															pearTimeCostVo.setTimePeriodMinutes(String.valueOf(peakHoursCost.getTimePeriodMinutes()));
															pearTimeCostVo.setTimePeriodMinutesAmount(String.valueOf(peakHoursCost.getTimePeriodMinutesAmount()));
															list.add(pearTimeCostVo);
															
															
															
															pearkTimeTotalMinutes = pearkTimeTotalMinutes + peakHoursCost.getTimePeriodMinutes();
															pearkTimeTotalMinutesAmount = ECCalculateUtils.add(peakHoursCost.getTimePeriodMinutesAmount(), pearkTimeTotalMinutesAmount);
															peakTimeTotalMileage = ECCalculateUtils.add(peakTimeTotalMileage, peakHoursCost.getTimePeriodMileage());
															peakTimeTotalMileageAmount = ECCalculateUtils.add(peakTimeTotalMileageAmount, peakHoursCost.getTimePeriodMileageAmount());
															
														}
														int ordinaryTimeTotalMinutes = orderMileage.getMinutes()-pearkTimeTotalMinutes;//其余的时长
														Double ordinaryTimeTotalMinutesAmount = ECCalculateUtils.mul(priceOfMinute, ordinaryTimeTotalMinutes);//其余的时长金额
														Double ordinaryTimeTotalMileage = 0d;
														Double ordinaryTimeTotalMileageAmount = 0d;
														if (orderMileage.getMileageAmount()>0.0) {
															ordinaryTimeTotalMileage = ECCalculateUtils.sub(orderMileage.getMileage(), peakTimeTotalMileage);//其余里程
															ordinaryTimeTotalMileageAmount = ECCalculateUtils.mul(priceOfMileage, ordinaryTimeTotalMileage);//其余里程费
														}
														orderMileage.setMinutesAmount(ECCalculateUtils.add(ordinaryTimeTotalMinutesAmount, pearkTimeTotalMinutesAmount));
														orderMileage.setMileageAmount(ECCalculateUtils.add(ordinaryTimeTotalMileageAmount, peakTimeTotalMileageAmount));
														orderMileageDayAmount = ECCalculateUtils.add(orderMileage.getMinutesAmount(), orderMileage.getMileageAmount());
													
//														or.setOrdinaryTimeTotalMinutes(ordinaryTimeTotalMinutes+ordinaryTimeTotalMinutes);
//														or.setOrdinaryTimeTotalMinutesAmount(ECCalculateUtils.add(ordinaryTimeTotalMinutesAmount,ordinaryTimeTotalMinutesAmount));
//														or.setOrdinaryTimeTotalMileage(ECCalculateUtils.add(ordinaryTimeTotalMileage,ordinaryTimeTotalMileage));
//														or.setOrdinaryTimeTotalMileageAmount(ECCalculateUtils.add(ordinaryTimeTotalMileageAmount,ordinaryTimeTotalMileageAmount));
													}
												}
											 }
											 if(orderMileageDayAmount!=null){
												 //订单封顶金额是否计算里程费用（0计算 1 不计算）
												 SysParam sysp = sysParamService.getByParamKey("IS_SUM_FREEMILEAGE");
												 if(sysp!=null&&sysp.getParamValue()!=null && Integer.parseInt(sysp.getParamValue().trim())==1){
													 //orderMileageDayAmount = ECCalculateUtils.sub(orderMileageDayAmount,mileageAmount);
													 if(orderMileage.getMinutesAmount()>=dayFD){//如果 时长费用大于 封顶金额 
														 daySum+=1;
														 billingCapPerDaySum =ECCalculateUtils.add(billingCapPerDaySum, dayFD);
														 if(orderMileage.getMinutesAmount()!=null){//时长费用
															 minutesAmount = ECCalculateUtils.add(minutesAmount, orderMileage.getMinutesAmount());
														 }
														 if(orderMileage.getMileageAmount()!=null){//里程费用
															 mileageAmount = ECCalculateUtils.add(mileageAmount, orderMileage.getMileageAmount());
															
														 }
														 if(orderMileage.getMinutes()!=null){//时长分钟
															 orderDuration += orderMileage.getMinutes();
														 }
														 if(orderMileage.getMileage()!=null){//订单里程
															 mileage += orderMileage.getMileage();
														 }
													 }else{
														 discount=false;
														 if(orderMileage.getMinutesAmount()!=null){//时长费用
															 minutesAmount = ECCalculateUtils.add(minutesAmount, orderMileage.getMinutesAmount());
															 minutesAmountOfNoCapPerDay = ECCalculateUtils.add(minutesAmountOfNoCapPerDay, orderMileage.getMinutesAmount());;
														 }
														 if(orderMileage.getMileageAmount()!=null){//里程费用
															 mileageAmount = ECCalculateUtils.add(mileageAmount, orderMileage.getMileageAmount());
															 SysParam sysppp = sysParamService.getByParamKey("IS_SUM_FREEMILEAGE");
																if(sysppp!=null&&sysp.getParamValue()!=null && Integer.parseInt(sysppp.getParamValue().trim())==1){
																	mileageAmountOfNoCapPerDay = mileageAmount;
																}else{
																	mileageAmountOfNoCapPerDay = ECCalculateUtils.add(mileageAmountOfNoCapPerDay, orderMileage.getMileageAmount());
																}
															 
														 }
														 if(orderMileage.getMinutes()!=null){//时长分钟
															 orderDuration = orderMileage.getMinutes();
															 orderDurationOfNoCapPerDay += orderDuration;
														 }
														 if(orderMileage.getMileage()!=null){//订单里程
															 mileage = ECCalculateUtils.add(mileage,orderMileage.getMileage());
															 SysParam syspp = sysParamService.getByParamKey("IS_SUM_FREEMILEAGE");
																if(syspp!=null&&syspp.getParamValue()!=null && Integer.parseInt(syspp.getParamValue().trim())==1){
																	mileageOfNoCapPerDay = mileage;
																}else{
																	 mileageOfNoCapPerDay = ECCalculateUtils.add(mileageOfNoCapPerDay,orderMileage.getMileage());
																}
														 }
													 }
												 }else{ //订单封顶金额是否计算里程费用（0计算 1 不计算）
														 if(orderMileageDayAmount>=dayFD){
															 daySum+=1;
															 billingCapPerDaySum =ECCalculateUtils.add(billingCapPerDaySum, dayFD);
															 if(orderMileage.getMinutesAmount()!=null){//时长费用
																 minutesAmount = ECCalculateUtils.add(minutesAmount, orderMileage.getMinutesAmount());
															 }
															 if(orderMileage.getMileageAmount()!=null){//里程费用
																 mileageAmount = ECCalculateUtils.add(mileageAmount, orderMileage.getMileageAmount());
																
															 }
															 if(orderMileage.getMinutes()!=null){//时长分钟
																 orderDuration += orderMileage.getMinutes();
															 }
															 if(orderMileage.getMileage()!=null){//订单里程
																 mileage += orderMileage.getMileage();
															 }
														 }else{
															 discount=false;
															 if(orderMileage.getMinutesAmount()!=null){//时长费用
																 minutesAmount = ECCalculateUtils.add(minutesAmount, orderMileage.getMinutesAmount());
																 minutesAmountOfNoCapPerDay = ECCalculateUtils.add(minutesAmountOfNoCapPerDay, orderMileage.getMinutesAmount());;
															 }
															 if(orderMileage.getMileageAmount()!=null){//里程费用
																 mileageAmount = ECCalculateUtils.add(mileageAmount, orderMileage.getMileageAmount());
																 SysParam sysppp = sysParamService.getByParamKey("IS_SUM_FREEMILEAGE");
																	if(sysppp!=null&&sysp.getParamValue()!=null && Integer.parseInt(sysppp.getParamValue().trim())==1){
																		mileageAmountOfNoCapPerDay = mileageAmount;
																	}else{
																		mileageAmountOfNoCapPerDay = ECCalculateUtils.add(mileageAmountOfNoCapPerDay, orderMileage.getMileageAmount());
																	}
																 
															 }
															 if(orderMileage.getMinutes()!=null){//时长分钟
																 orderDuration = orderMileage.getMinutes();
																 orderDurationOfNoCapPerDay += orderDuration;
															 }
															 if(orderMileage.getMileage()!=null){//订单里程
																 mileage = ECCalculateUtils.add(mileage,orderMileage.getMileage());
																 SysParam syspp = sysParamService.getByParamKey("IS_SUM_FREEMILEAGE");
																	if(syspp!=null&&syspp.getParamValue()!=null && Integer.parseInt(syspp.getParamValue().trim())==1){
																		mileageOfNoCapPerDay = mileage;
																	}else{
																		 mileageOfNoCapPerDay = ECCalculateUtils.add(mileageOfNoCapPerDay,orderMileage.getMileage());
																	}
															 }
														 }
												 }
										
											 }
										 	
										 }
										 
									 }
									 i++;
								}
								if(discount){
									//订单里程
									or.setOrderMileage(mileage);
									//时长金额
									or.setMinutesAmount(ECNumberUtils.roundDoubleWithScale(minutesAmount,2));
									//里程金额
									or.setMileageAmount(ECNumberUtils.roundDoubleWithScale(mileageAmount,2));
									//订单分钟时长
									or.setOrderDuration(orderDuration);
								}else{
									//订单里程
									or.setOrderMileage(ECNumberUtils.roundDoubleWithScale(mileageOfNoCapPerDay,2));
									//时长金额
									or.setMinutesAmount(ECNumberUtils.roundDoubleWithScale(minutesAmountOfNoCapPerDay,2));
									//里程金额
									or.setMileageAmount(ECNumberUtils.roundDoubleWithScale(mileageAmountOfNoCapPerDay,2));
									//订单分钟时长
									or.setOrderDuration(orderDurationOfNoCapPerDay);
								}
								//订单总金额
								or.setNowAmount(ECNumberUtils.roundDoubleWithScale(order.getOrderAmount(),2));
								//订单封顶天数
								or.setDaySum(daySum);
								//封顶金额
								or.setBillingCapPerDaySum(billingCapPerDaySum);
								//不计免赔
								if(order.getRegardlessFranchise() == null){
									order.setRegardlessFranchise(0d);
								}
								
								if(list != null  && list.size()>0){
									or.setPearTimeCostVo(list);
								}
								or.setRegardlessFranchise(order.getRegardlessFranchise());
								or.setDiscount(discount);
								
							//订单结束	
							}else{
								List<PearTimeCostVo>  listOver = new  ArrayList<PearTimeCostVo>();
								Double minutesAmount = 0d;
								Double mileageAmount = 0d;
								Double mileage = 0d;
								Double dayFD = 0d;
								Double billingCapPerDaySum = 0d;
								int orderDuration = 0;
								int daySum=0;
								boolean discount=true;
								if(order.getStartBillingTime()!=null&&order.getFinishTime()!=null){
									or.setStartBillingTime(ECDateUtils.formatTime(order.getStartBillingTime()));
									if(!order.getOrderStatus().equals(1)){
										or.setFinishTime(ECDateUtils.formatTime(order.getFinishTime()));
									}
									
									OrderMileage om = new OrderMileage();
									om.setOrderNo(order.getOrderNo());
									List<OrderMileage> orderMileageList = orderMileageService.getOrderMileageList(new Query(om));
									int i=0;
									
									for(OrderMileage orderMileage:orderMileageList){//查出当日的总金额和封顶的价格进行比较
										//判断日期是周几
										int dayOfWeek = ECDateUtils.getDayOfWeek(orderMileage.getOrderMileageDate());
										Double orderMileageDayAmount = orderMileage.getOrderAmountOfDay();
										//查出对应的计费规则
										 if(order.getRuleNo() != null && !"".equals(order.getRuleNo())){
											 PricingRule pr= pricingRuleService.getPricingRule(order.getRuleNo());
											 if(pr != null){//查出起步价
												 or.setBaseFee(pr.getBaseFee());
												 if(pr.getRuleType()==1){
												    PricingRuleCustomizedDate pricingRuleCustomizedDate = pricingRuleCustomizedDateService.getPricingRuleCustomizedDate(pr.getRuleNo(), orderMileage.getOrderMileageDate());
													if(pricingRuleCustomizedDate!=null){//自定义日期封顶金额
														dayFD = pricingRuleCustomizedDate.getBillingCapPerDayCustomized();
													}else if(dayOfWeek==Calendar.SATURDAY||dayOfWeek==Calendar.SUNDAY){//周末封顶金额
														dayFD = pr.getBillingCapPerDayOrdinary();
													}else{//工作日封顶金额
														dayFD = pr.getBillingCapPerDay();
													}
												 }else if(pr.getRuleType()==2){
													 dayFD = pr.getBillingCapPerDay();
													//计算普通时段分钟单价--元/分钟
													 Double priceOfMinute = 0.0;
													 if (orderMileage.getMinutes()>0.0) {
														 priceOfMinute = ECCalculateUtils.div(orderMileage.getMinutesAmount(), orderMileage.getMinutes());
													 }
													//订单是否前多少分钟免时长费
													SysParam param = sysParamService.getByParamKey("IS_ORDER_FREEMINUTES");
													if(param!=null&&param.getParamValue()!=null){
														int isOrderFreeMinutes = Integer.parseInt(param.getParamValue().trim());
														if(isOrderFreeMinutes==1){
															or.setIsOrderFreeMinutes(isOrderFreeMinutes);
															param = sysParamService.getByParamKey("FREEMINUTES");
															if(param!=null&&param.getParamValue()!=null){
																int freeMinutes = Integer.parseInt(param.getParamValue().trim());
																or.setFreeMinutes(freeMinutes);//免费时长
																if(i==0){
																	if(order.getOrderDuration()>=freeMinutes){
																		//时长费减免freeMinutes分钟
																		orderMileage.setMinutesAmount(ECCalculateUtils.mul(orderMileage.getMinutes()-freeMinutes, priceOfMinute));
																		orderMileage.setMinutesAmount(ECCalculateUtils.round(orderMileage.getMinutesAmount(), 2));//取两位小数
																		//重新计算一天费用
																		orderMileageDayAmount = ECCalculateUtils.add(orderMileage.getMinutesAmount(), orderMileage.getMileageAmount());
																	}else{
																		minutesAmount = 0d;
																		orderMileage.setMinutesAmount(0d);;
																		orderMileageDayAmount = orderMileage.getMileageAmount();
																	}
																}
															}
														}
													}
													//计算普通时段里程单价--元/分钟
													Double priceOfMileage = 0d;
													if (orderMileage.getMileage()>0.0) {
														priceOfMileage = ECCalculateUtils.div(orderMileage.getMileageAmount(), orderMileage.getMileage());
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
																		if(orderMileage.getMileage()>0){
																			//freeMinutes分钟的里程费
																			Double freeMileageAmount = ECCalculateUtils.mul(orderMileage.getFreeMileage(), priceOfMileage);
																			//里程费减免freeMinutes分钟的里程费
																			orderMileage.setMileageAmount(ECCalculateUtils.sub(orderMileage.getMileageAmount(), freeMileageAmount));
																			orderMileage.setMileageAmount(ECCalculateUtils.round(orderMileage.getMileageAmount(), 2));//取两位小数
																			//重新计算一天费用
																			orderMileageDayAmount = ECCalculateUtils.add(orderMileage.getMinutesAmount(), orderMileage.getMileageAmount());
																		}
																	}else{
																		mileageAmount = 0d;
																		orderMileage.setMileageAmount(0d);
																		//重新计算一天费用
																		orderMileageDayAmount = orderMileage.getMinutesAmount();
																	}
																}
															}
														}
													}
													if (orderMileage.getPearTimeCost()!=null) {
														int pearkTimeTotalMinutes = 0;
														Double pearkTimeTotalMinutesAmount = 0d;
														Double peakTimeTotalMileage = 0d;
														Double peakTimeTotalMileageAmount = 0d;
														List<PeakHoursCost> peakHoursCostList = JsonUtils.parse2ListObject(orderMileage.getPearTimeCost(), PeakHoursCost.class);;
														if (peakHoursCostList!=null&&peakHoursCostList.size()>0) {
															for (PeakHoursCost peakHoursCost:peakHoursCostList) {
																PearTimeCostVo pearTimeCostVo = new PearTimeCostVo();
																String start = ECDateUtils.formatDate(peakHoursCost.getTimePeriodStartTime(), "HH:mm");
																String end= ECDateUtils.formatDate(peakHoursCost.getTimePeriodEndTime(), "HH:mm");
																pearTimeCostVo.setTimePeriod(start+"~"+end);
																String Day = ECDateUtils.formatDate(peakHoursCost.getTimePeriodStartTime(), "MM/dd");
																pearTimeCostVo.setTimeDay(Day);
																pearTimeCostVo.setTimePeriodMileage(String.valueOf(peakHoursCost.getTimePeriodMileage()));
																pearTimeCostVo.setTimePeriodMileageAmount(String.valueOf(peakHoursCost.getTimePeriodMileageAmount()));
																pearTimeCostVo.setTimePeriodMinutes(String.valueOf(peakHoursCost.getTimePeriodMinutes()));
																pearTimeCostVo.setTimePeriodMinutesAmount(String.valueOf(peakHoursCost.getTimePeriodMinutesAmount()));
																listOver.add(pearTimeCostVo);
																
																pearkTimeTotalMinutes = pearkTimeTotalMinutes + peakHoursCost.getTimePeriodMinutes();
																pearkTimeTotalMinutesAmount = ECCalculateUtils.add(peakHoursCost.getTimePeriodMinutesAmount(), pearkTimeTotalMinutesAmount);
																peakTimeTotalMileage = ECCalculateUtils.add(peakTimeTotalMileage, peakHoursCost.getTimePeriodMileage());
																peakTimeTotalMileageAmount = ECCalculateUtils.add(peakTimeTotalMileageAmount, peakHoursCost.getTimePeriodMileageAmount());
																
															}
															int ordinaryTimeTotalMinutes = orderMileage.getMinutes()-pearkTimeTotalMinutes;
															Double ordinaryTimeTotalMinutesAmount = ECCalculateUtils.mul(priceOfMinute, ordinaryTimeTotalMinutes);
															Double ordinaryTimeTotalMileage = 0d;
															Double ordinaryTimeTotalMileageAmount = 0d;
															if (orderMileage.getMileageAmount()>0.0) {
																ordinaryTimeTotalMileage = ECCalculateUtils.sub(orderMileage.getMileage(), peakTimeTotalMileage);
																ordinaryTimeTotalMileageAmount = ECCalculateUtils.mul(priceOfMileage, ordinaryTimeTotalMileage);
															}
															orderMileage.setMinutesAmount(ECCalculateUtils.add(ordinaryTimeTotalMinutesAmount, pearkTimeTotalMinutesAmount));
															orderMileage.setMileageAmount(ECCalculateUtils.add(ordinaryTimeTotalMileageAmount, peakTimeTotalMileageAmount));
															orderMileageDayAmount = ECCalculateUtils.add(orderMileage.getMinutesAmount(), orderMileage.getMileageAmount());
														
															
														}
													}

													
												 }
												 if(orderMileageDayAmount!=null){
													//订单封顶金额是否计算里程费用（0计算 1 不计算）
														SysParam sysp = sysParamService.getByParamKey("IS_SUM_FREEMILEAGE");
														if(sysp!=null&&sysp.getParamValue()!=null && Integer.parseInt(sysp.getParamValue().trim())==1){
																//orderMileageDayAmount =	ECCalculateUtils.sub(orderMileageDayAmount,mileageAmount);
																 if(orderMileage.getMinutesAmount()>=dayFD){//如果时长费用超过封顶金额 则总金额=封顶金额+里程费用
																	 daySum+=1;
																	 billingCapPerDaySum =ECCalculateUtils.add(billingCapPerDaySum, dayFD);
																	 if(orderMileage.getMinutesAmount()!=null){//时长费用
																		 minutesAmount = ECCalculateUtils.add(minutesAmount, orderMileage.getMinutesAmount());
																	 }
																	 if(orderMileage.getMileageAmount()!=null){//里程费用
																		 mileageAmount = ECCalculateUtils.add(mileageAmount, orderMileage.getMileageAmount());
																		
																	 }
																	 if(orderMileage.getMinutes()!=null){//时长分钟
																		 orderDuration += orderMileage.getMinutes();
																	 }
																	 if(orderMileage.getMileage()!=null){//订单里程
																		 mileage += orderMileage.getMileage();
																	 }
																 }else{
																	 discount=false;
																	 if(orderMileage.getMinutesAmount()!=null){//时长费用
																		 minutesAmount = ECCalculateUtils.add(minutesAmount, orderMileage.getMinutesAmount());
																		 minutesAmountOfNoCapPerDay = ECCalculateUtils.add(minutesAmountOfNoCapPerDay, orderMileage.getMinutesAmount());;
																	 }
																	 if(orderMileage.getMileageAmount()!=null){//里程费用
																		 mileageAmount = ECCalculateUtils.add(mileageAmount, orderMileage.getMileageAmount());
																		 SysParam sysppp = sysParamService.getByParamKey("IS_SUM_FREEMILEAGE");
																			if(sysppp!=null&&sysppp.getParamValue()!=null && Integer.parseInt(sysppp.getParamValue().trim())==1){
																				mileageAmountOfNoCapPerDay = mileageAmount;
																			}else{
																				mileageAmountOfNoCapPerDay = ECCalculateUtils.add(mileageAmountOfNoCapPerDay, orderMileage.getMileageAmount());
																			}
																	 }
																	 if(orderMileage.getMinutes()!=null){//时长分钟
																		 orderDuration = orderMileage.getMinutes();
																		 orderDurationOfNoCapPerDay += orderDuration;
																	 }
																	 if(orderMileage.getMileage()!=null){//订单里程
																		 mileage = ECCalculateUtils.add(mileage,orderMileage.getMileage());
																		 SysParam syspp = sysParamService.getByParamKey("IS_SUM_FREEMILEAGE");
																			if(syspp!=null&&syspp.getParamValue()!=null && Integer.parseInt(syspp.getParamValue().trim())==1){
																				mileageOfNoCapPerDay = mileage;
																			}else{
																				 mileageOfNoCapPerDay = ECCalculateUtils.add(mileageOfNoCapPerDay,orderMileage.getMileage());
																			}
																	 }
																 }	
														}else{//////////////////////////////////////////
															 if(orderMileageDayAmount>=dayFD){//如果时长费用超过封顶金额 则总金额=封顶金额+里程费用
																 daySum+=1;
																 billingCapPerDaySum =ECCalculateUtils.add(billingCapPerDaySum, dayFD);
																 if(orderMileage.getMinutesAmount()!=null){//时长费用
																	 minutesAmount = ECCalculateUtils.add(minutesAmount, orderMileage.getMinutesAmount());
																 }
																 if(orderMileage.getMileageAmount()!=null){//里程费用
																	 mileageAmount = ECCalculateUtils.add(mileageAmount, orderMileage.getMileageAmount());
																	
																 }
																 if(orderMileage.getMinutes()!=null){//时长分钟
																	 orderDuration += orderMileage.getMinutes();
																 }
																 if(orderMileage.getMileage()!=null){//订单里程
																	 mileage += orderMileage.getMileage();
																 }
															 }else{
																 discount=false;
																 if(orderMileage.getMinutesAmount()!=null){//时长费用
																	 minutesAmount = ECCalculateUtils.add(minutesAmount, orderMileage.getMinutesAmount());
																	 minutesAmountOfNoCapPerDay = ECCalculateUtils.add(minutesAmountOfNoCapPerDay, orderMileage.getMinutesAmount());;
																 }
																 if(orderMileage.getMileageAmount()!=null){//里程费用
																	 mileageAmount = ECCalculateUtils.add(mileageAmount, orderMileage.getMileageAmount());
																	 SysParam sysppp = sysParamService.getByParamKey("IS_SUM_FREEMILEAGE");
																		if(sysppp!=null&&sysppp.getParamValue()!=null && Integer.parseInt(sysppp.getParamValue().trim())==1){
																			mileageAmountOfNoCapPerDay = mileageAmount;
																		}else{
																			mileageAmountOfNoCapPerDay = ECCalculateUtils.add(mileageAmountOfNoCapPerDay, orderMileage.getMileageAmount());
																		}
																 }
																 if(orderMileage.getMinutes()!=null){//时长分钟
																	 orderDuration = orderMileage.getMinutes();
																	 orderDurationOfNoCapPerDay += orderDuration;
																 }
																 if(orderMileage.getMileage()!=null){//订单里程
																	 mileage = ECCalculateUtils.add(mileage,orderMileage.getMileage());
																	 SysParam syspp = sysParamService.getByParamKey("IS_SUM_FREEMILEAGE");
																		if(syspp!=null&&syspp.getParamValue()!=null && Integer.parseInt(syspp.getParamValue().trim())==1){
																			mileageOfNoCapPerDay = mileage;
																		}else{
																			 mileageOfNoCapPerDay = ECCalculateUtils.add(mileageOfNoCapPerDay,orderMileage.getMileage());
																		}
																 }
															 }
															
														}
											
												 }
													
											 }
											 
										 }
										 i++;
									}
									
								}else{
									or.setOrderDuration(0);
								}
								if(discount){
									//订单里程
									or.setOrderMileage(mileage);
									//时长金额
									or.setMinutesAmount(ECNumberUtils.roundDoubleWithScale(minutesAmount,2));
									//里程金额
									or.setMileageAmount(ECNumberUtils.roundDoubleWithScale(mileageAmount,2));
									//订单分钟时长
									or.setOrderDuration(orderDuration);
								}else{
									//订单里程
									or.setOrderMileage(ECNumberUtils.roundDoubleWithScale(mileageOfNoCapPerDay,2));
									//时长金额
									or.setMinutesAmount(ECNumberUtils.roundDoubleWithScale(minutesAmountOfNoCapPerDay,2));
									//里程金额
									or.setMileageAmount(ECNumberUtils.roundDoubleWithScale(mileageAmountOfNoCapPerDay,2));
									//订单分钟时长
									or.setOrderDuration(orderDurationOfNoCapPerDay);
								}
								//订单总金额
								or.setNowAmount(ECNumberUtils.roundDoubleWithScale(order.getOrderAmount(),2));
								//订单封顶天数
								or.setDaySum(daySum);
								//封顶金额
								or.setBillingCapPerDaySum(billingCapPerDaySum);
								//不计免赔
								if(order.getRegardlessFranchise() == null){
									order.setRegardlessFranchise(0d);
								}
								or.setRegardlessFranchise(order.getRegardlessFranchise());
								or.setDiscount(discount);
								if(listOver!= null  && listOver.size()>0){
									or.setPearTimeCostVo(listOver);
								}
								//会员等级折扣金额
								or.setMemberLevelDiscountAmount(order.getMemberLevelDiscountAmount());
								
							}
							
							or.setPayableAmount(ECNumberUtils.roundDoubleWithScale(order.getPayableAmount(),2));
							OrderComments orderCommentsQ = new OrderComments();
							orderCommentsQ.setOrderNo(order.getOrderNo());
							List<OrderComments> commentList = commentsService.getOrderCommentsList(new Query(orderCommentsQ));
							if(commentList.size()>0){
								or.setIsCommented(1);
							}else{
								or.setIsCommented(0);
							}
							if(order.getDiscountAmount()!=null){
								or.setDiscountAmount(order.getDiscountAmount());
							}else{
								or.setDiscountAmount(0d);
							}
							if(order.getPackMinutesDiscountAmount()!=null){
								Double yue = order.getPackAmountDiscountAmount()==null?0d:order.getPackAmountDiscountAmount();
								or.setPackMinutesDiscountAmount(order.getPackMinutesDiscountAmount() + yue);
							}else{
								or.setPackMinutesDiscountAmount(0d);
							}
							or.setNeedPayaAmount(ECNumberUtils.roundDoubleWithScale(order.getOrderAmount()-order.getPackMinutesDiscountAmount(),2));
							if(order.getPayStatus().equals(0)){
								or.setAlreadPayAmount(0d);
							}else if(order.getPayStatus().equals(1)){
								or.setAlreadPayAmount(ECNumberUtils.roundDoubleWithScale(order.getPayableAmount(),2));
							}
							or.setPayStatus(order.getPayStatus());
							//积分（在数据库中 暂时没有这个字段，统一默认为0）
							or.setIntegral(0);
							
							//取车费用
							//添加系统参数 订单时长大于分钟免收取车费
							SysParam spa=sysParamService.getByParamKey("VOID_SERVICE_FEE_GET");
							if(spa != null && "1".equals(spa.getParamValue())){
								SysParam spav=sysParamService.getByParamKey("VOID_ORDER_DURATION");
								if(spav != null){
									if(order.getOrderDuration() > Integer.valueOf(spav.getParamValue())){
										or.setStartParkAmount(0d);
									}else{
										if(order.getOrderStatus()==4){
											or.setStartParkAmount(0d);
										}else{
											if(order.getStartParkNo() != null && !"".equals(order.getStartParkNo())){
												Park pa=parkService.getPark(order.getStartParkNo());
												if(pa != null){
													or.setStartParkAmount(pa.getServiceFeeGet());
												}else{
													or.setStartParkAmount(0d);
												}
											}
										}
									}
								}
								
							}else{
								if(order.getOrderStatus()==4){
									or.setStartParkAmount(0d);
								}else{
									if(order.getStartParkNo() != null && !"".equals(order.getStartParkNo())){
										Park pa=parkService.getPark(order.getStartParkNo());
										if(pa != null){
											or.setStartParkAmount(pa.getServiceFeeGet());
										}else{
											or.setStartParkAmount(0d);
										}
									}
								}
							}
							
							
							
							//结束订单时  查看订单时长 和配置的时间比较 如果订单时长 小于 配置时间 则没有取车服务费
							SysParam spm=sysParamService.getByParamKey("SMALL_SERVICE_FEE_GET");
							if(spm != null && "1".equals(spm.getParamValue()) && order.getOrderStatus()==3){
								SysParam spam=sysParamService.getByParamKey("SMALL_ORDER_DURATION");
									if(spam != null){
										if(order.getOrderDuration() < Integer.valueOf(spam.getParamValue())){
											or.setStartParkAmount(0d);
										}
									}
							}
							
							
							//还车费用
							if(order.getActualTerminalParkNo() != null && !"".equals(order.getActualTerminalParkNo())){
								Park pk=parkService.getPark(order.getActualTerminalParkNo());
								if(pk != null){
									or.setFinishParkAmount(pk.getServiceFeeBack());
								}else{
									or.setFinishParkAmount(0d);
								}
							}
							//附加费用
							if(order.getServiceFeeAmount() != null){
								or.setServiceFeeAmount(order.getServiceFeeAmount());
							}else{
								or.setServiceFeeAmount(0d);
							}
							//金豆抵扣金额
							if(order.getGlodBeansDedutionAmount()!=null){
								or.setGlodBeansDedutionAmount(order.getGlodBeansDedutionAmount());
								
							}
							 SysParam isp = sysParamService.getByParamKey("IS_SUM_FREEMILEAGE");
							 if(isp!=null&&isp.getParamValue()!=null){
									or.setIsSumFreeMileage(Integer.valueOf(isp.getParamValue()));
							}else{
								or.setIsSumFreeMileage(0);
							}
							or.setShareTitle("来自"+order.getMemberName()+"的分享");
							or.setShareContent("将你的邀请码分享到朋友圈好友，你的信用值会增加");
							or.setIconUrl(imgPath + "/share_photo/" + "app_icon.png");
							//or.setShareUrl(image + "/" + "share.html");
							or.setIsFinish("0");
							result.setData(or);
							result.setCode(OrderConstant.success_code);
							result.setMsg(""); 
					 }else{
						 result.setCode(OrderConstant.fail_code);
						 result.setMsg(OrderConstant.fail_msg); 
					 }
				
					return result;
				}	
				 
				 //订单超时结束订单
				public Order getOrderCancel(Order order) throws ParseException{
					//计算当前时间和订单 创建的时间的差
					int minute = ECDateUtils.differMinutes( order.getCreateTime(),new Date()).intValue();
					//得到（系统参数设置的）60分钟，自动取消订单时长；
					SysParam sysp = sysParamService.getByParamKey(CarConstant.cancelordertime_param_key);
					int dingshi = 60;
					if (sysp!=null&&sysp.getParamValue()!=null&&!sysp.getParamValue().equals("")) {
						dingshi = Integer.parseInt(sysp.getParamValue());
					}
					
					if (minute>=dingshi) {

						CarStatus carStatusse = carStatusService.getCarStatus(order.getCarNo());
						
						//根据传的车辆位置，查找周边场站距离最近的场站
						String parkNo="";
						parkNo=parkService.getCurrentParkNoByCarLocation(carStatusse.getLongitude(),carStatusse.getLatitude());

						if(carStatusse.getCarStatus()==2 && parkNo != null){//车辆状态（1、已启动，2、已熄火）
							//如果当前已经超过倒计时的时间，那么则 修改订单状态，让其变为已结束；（需要付钱）
							order.setOrderStatus(3);
							order.setCancelTime(new Date());
							order.setFinishTime(order.getCancelTime());
							int minutes=0;
							if(order.getStartBillingTime()!=null){
								minutes = ECDateUtils.differMinutes(order.getStartBillingTime(),new Date()).intValue();
							}
							
							order.setOrderDuration(minutes);//设置订单时长
							
		//---------------------------------------*****---------------下面是引用订单结算的代码------------------***-----------------------------------------------					
							order=orderService.orderBalance(order,parkNo).getData();
		//---------------------------------------*****---------------上面是引用订单结算的代码------------------***-----------------------------------------------					
							order.setUpdateTime(new Date());
							CarStatus carStatus=carStatusService.getCarStatus(order.getCarNo());
							if(carStatus!=null){
								carStatus.setUserageStatus(0);
								CarStatus c=new CarStatus();
								c.setCarNo(carStatus.getCarNo());
								c.setUserageStatus(0);
								carStatusService.updateCarStatus(c, null);
							}
							orderService.updateOrder(order,null);
						}
						
					}else{	
						order = orderService.orderTripView(order).getData();
					}
					return order;
					} 
					//订单评论项展示
				    @RequestMapping("showOrderCommentItem")
				    @ResponseBody
					public ResultInfo<List<OrderCommentsVo>> showOrderCommentItem(){
						ResultInfo<List<OrderCommentsVo>> result = new ResultInfo<List<OrderCommentsVo>>();
						OrderCommentsVo commentOne = new OrderCommentsVo();
						List<OrderCommentsVo> commentList = new ArrayList<OrderCommentsVo>();
						List<DataDictItem> starLevelOne = dataDictItemService.getDataDictItemListByCatCode("STAR_LEVEL_ONE");// 订单评价一星级
						commentOne.setScore("1");//一星级
						if(starLevelOne.size()>=4){
							commentOne.setTitleContent(starLevelOne.get(0).getMemo());
							commentOne.setScoreItem1Id(starLevelOne.get(0).getDataDictItemId());
							commentOne.setScoreItem2Id(starLevelOne.get(1).getDataDictItemId());
							commentOne.setScoreItem3Id(starLevelOne.get(2).getDataDictItemId());
							commentOne.setScoreItem4Id(starLevelOne.get(3).getDataDictItemId());
							
							commentOne.setScoreItem1(starLevelOne.get(0).getItemValue());
							commentOne.setScoreItem2(starLevelOne.get(1).getItemValue());
							commentOne.setScoreItem3(starLevelOne.get(2).getItemValue());
							commentOne.setScoreItem4(starLevelOne.get(3).getItemValue());
						}else if(starLevelOne.size()>=5){
							commentOne.setTitleContent(starLevelOne.get(0).getMemo());
							commentOne.setScoreItem1Id(starLevelOne.get(0).getDataDictItemId());
							commentOne.setScoreItem2Id(starLevelOne.get(1).getDataDictItemId());
							commentOne.setScoreItem3Id(starLevelOne.get(2).getDataDictItemId());
							commentOne.setScoreItem4Id(starLevelOne.get(3).getDataDictItemId());
							commentOne.setScoreItem5Id(starLevelOne.get(4).getDataDictItemId());
							
							commentOne.setScoreItem1(starLevelOne.get(0).getItemValue());
							commentOne.setScoreItem2(starLevelOne.get(1).getItemValue());
							commentOne.setScoreItem3(starLevelOne.get(2).getItemValue());
							commentOne.setScoreItem4(starLevelOne.get(3).getItemValue());
							commentOne.setScoreItem5(starLevelOne.get(4).getItemValue());
						}
						commentList.add(commentOne);
						OrderCommentsVo commentTwo = new OrderCommentsVo();
						List<DataDictItem> starLevelTwo = dataDictItemService.getDataDictItemListByCatCode("STAR_LEVEL_TWO");// 订单评价二星级
						commentTwo.setScore("2");//二星级
						if(starLevelTwo.size()>=4){
							commentTwo.setTitleContent(starLevelTwo.get(0).getMemo());
							commentTwo.setScoreItem1Id(starLevelTwo.get(0).getDataDictItemId());
							commentTwo.setScoreItem2Id(starLevelTwo.get(1).getDataDictItemId());
							commentTwo.setScoreItem3Id(starLevelTwo.get(2).getDataDictItemId());
							commentTwo.setScoreItem4Id(starLevelTwo.get(3).getDataDictItemId());
							
							commentTwo.setScoreItem1(starLevelTwo.get(0).getItemValue());
							commentTwo.setScoreItem2(starLevelTwo.get(1).getItemValue());
							commentTwo.setScoreItem3(starLevelTwo.get(2).getItemValue());
							commentTwo.setScoreItem4(starLevelTwo.get(3).getItemValue());
						}else if(starLevelTwo.size()>=5){
							commentTwo.setTitleContent(starLevelTwo.get(0).getMemo());
							commentTwo.setScoreItem1Id(starLevelTwo.get(0).getDataDictItemId());
							commentTwo.setScoreItem2Id(starLevelTwo.get(1).getDataDictItemId());
							commentTwo.setScoreItem3Id(starLevelTwo.get(2).getDataDictItemId());
							commentTwo.setScoreItem4Id(starLevelTwo.get(3).getDataDictItemId());
							commentTwo.setScoreItem5Id(starLevelTwo.get(4).getDataDictItemId());
							
							commentTwo.setScoreItem1(starLevelTwo.get(0).getItemValue());
							commentTwo.setScoreItem2(starLevelTwo.get(1).getItemValue());
							commentTwo.setScoreItem3(starLevelTwo.get(2).getItemValue());
							commentTwo.setScoreItem4(starLevelTwo.get(3).getItemValue());
							commentTwo.setScoreItem5(starLevelTwo.get(4).getItemValue());
						}
						commentList.add(commentTwo);
						OrderCommentsVo commentThree = new OrderCommentsVo();
						List<DataDictItem> starLevelThree = dataDictItemService.getDataDictItemListByCatCode("STAR_LEVEL_THREE");// 订单评价三星级
						commentThree.setScore("3");//三星级
						if(starLevelThree.size()>=4){
							commentThree.setTitleContent(starLevelThree.get(0).getMemo());
							commentThree.setScoreItem1Id(starLevelThree.get(0).getDataDictItemId());
							commentThree.setScoreItem2Id(starLevelThree.get(1).getDataDictItemId());
							commentThree.setScoreItem3Id(starLevelThree.get(2).getDataDictItemId());
							commentThree.setScoreItem4Id(starLevelThree.get(3).getDataDictItemId());
							
							commentThree.setScoreItem1(starLevelThree.get(0).getItemValue());
							commentThree.setScoreItem2(starLevelThree.get(1).getItemValue());
							commentThree.setScoreItem3(starLevelThree.get(2).getItemValue());
							commentThree.setScoreItem4(starLevelThree.get(3).getItemValue());
						}else if(starLevelThree.size()>=5){
							commentThree.setTitleContent(starLevelThree.get(0).getMemo());
							commentThree.setScoreItem1Id(starLevelThree.get(0).getDataDictItemId());
							commentThree.setScoreItem2Id(starLevelThree.get(1).getDataDictItemId());
							commentThree.setScoreItem3Id(starLevelThree.get(2).getDataDictItemId());
							commentThree.setScoreItem4Id(starLevelThree.get(3).getDataDictItemId());
							commentThree.setScoreItem5Id(starLevelThree.get(4).getDataDictItemId());
							
							commentThree.setScoreItem1(starLevelThree.get(0).getItemValue());
							commentThree.setScoreItem2(starLevelThree.get(1).getItemValue());
							commentThree.setScoreItem3(starLevelThree.get(2).getItemValue());
							commentThree.setScoreItem4(starLevelThree.get(3).getItemValue());
							commentThree.setScoreItem5(starLevelThree.get(4).getItemValue());
						}
						commentList.add(commentThree);
						OrderCommentsVo commentFour = new OrderCommentsVo();
						List<DataDictItem> starLevelFour = dataDictItemService.getDataDictItemListByCatCode("STAR_LEVEL_FOUR");// 订单评价四星级
						commentFour.setScore("4");//四星级
						if(starLevelFour.size()>=4){
							commentFour.setTitleContent(starLevelFour.get(0).getMemo());
							commentFour.setScoreItem1Id(starLevelFour.get(0).getDataDictItemId());
							commentFour.setScoreItem2Id(starLevelFour.get(1).getDataDictItemId());
							commentFour.setScoreItem3Id(starLevelFour.get(2).getDataDictItemId());
							commentFour.setScoreItem4Id(starLevelFour.get(3).getDataDictItemId());
							
							commentFour.setScoreItem1(starLevelFour.get(0).getItemValue());
							commentFour.setScoreItem2(starLevelFour.get(1).getItemValue());
							commentFour.setScoreItem3(starLevelFour.get(2).getItemValue());
							commentFour.setScoreItem4(starLevelFour.get(3).getItemValue());
						}else if(starLevelFour.size()>=5){
							commentFour.setTitleContent(starLevelFour.get(0).getMemo());
							commentFour.setScoreItem1Id(starLevelFour.get(0).getDataDictItemId());
							commentFour.setScoreItem2Id(starLevelFour.get(1).getDataDictItemId());
							commentFour.setScoreItem3Id(starLevelFour.get(2).getDataDictItemId());
							commentFour.setScoreItem4Id(starLevelFour.get(3).getDataDictItemId());
							commentFour.setScoreItem5Id(starLevelFour.get(4).getDataDictItemId());
							
							commentFour.setScoreItem1(starLevelFour.get(0).getItemValue());
							commentFour.setScoreItem2(starLevelFour.get(1).getItemValue());
							commentFour.setScoreItem3(starLevelFour.get(2).getItemValue());
							commentFour.setScoreItem4(starLevelFour.get(3).getItemValue());
							commentFour.setScoreItem5(starLevelFour.get(4).getItemValue());
						}
						commentList.add(commentFour);
						OrderCommentsVo commentFive = new OrderCommentsVo();
						List<DataDictItem> starLevelFive = dataDictItemService.getDataDictItemListByCatCode("STAR_LEVEL_FIVE");// 订单评价五星级
						commentFive.setScore("5");//五星级
						if(starLevelFive.size()>=4){
							commentFive.setTitleContent(starLevelFive.get(0).getMemo());
							commentFive.setScoreItem1Id(starLevelFive.get(0).getDataDictItemId());
							commentFive.setScoreItem2Id(starLevelFive.get(1).getDataDictItemId());
							commentFive.setScoreItem3Id(starLevelFive.get(2).getDataDictItemId());
							commentFive.setScoreItem4Id(starLevelFive.get(3).getDataDictItemId());
							
							commentFive.setScoreItem1(starLevelFive.get(0).getItemValue());
							commentFive.setScoreItem2(starLevelFive.get(1).getItemValue());
							commentFive.setScoreItem3(starLevelFive.get(2).getItemValue());
							commentFive.setScoreItem4(starLevelFive.get(3).getItemValue());
						}else if(starLevelFive.size()>=5){
							commentFive.setTitleContent(starLevelFive.get(0).getMemo());
							commentFive.setScoreItem1Id(starLevelFive.get(0).getDataDictItemId());
							commentFive.setScoreItem2Id(starLevelFive.get(1).getDataDictItemId());
							commentFive.setScoreItem3Id(starLevelFive.get(2).getDataDictItemId());
							commentFive.setScoreItem4Id(starLevelFive.get(3).getDataDictItemId());
							commentFive.setScoreItem5Id(starLevelFive.get(4).getDataDictItemId());
							
							commentFive.setScoreItem1(starLevelFive.get(0).getItemValue());
							commentFive.setScoreItem2(starLevelFive.get(1).getItemValue());
							commentFive.setScoreItem3(starLevelFive.get(2).getItemValue());
							commentFive.setScoreItem4(starLevelFive.get(3).getItemValue());
							commentFive.setScoreItem5(starLevelFive.get(4).getItemValue());
						}
						commentList.add(commentFive);
						result.setCode(Constant.SECCUESS);
						result.setData(commentList);
						return result;
					} 
				    //订单评论提交
				    @RequestMapping("orderComment")
				    @ResponseBody
					public ResultInfo<OrderCommentsVo> orderComment(OrderComments orderComments){
						ResultInfo<OrderCommentsVo> result = new ResultInfo<OrderCommentsVo>();
						//判断传过来的orderNo是否为空
						if (orderComments!=null&&orderComments.getOrderNo().trim().length()>0) {
							String content = "";
							if(orderComments.getScoreItem1()!=null&&!"".equals(orderComments.getScoreItem1())){
								DataDictItem item = dataDictItemService.getDataDictItem(orderComments.getScoreItem1());
								content+=item.getItemValue()+",";
							}
							if(orderComments.getScoreItem2()!=null&&!"".equals(orderComments.getScoreItem2())){
								DataDictItem item = dataDictItemService.getDataDictItem(orderComments.getScoreItem2());
								content+=item.getItemValue()+",";
							}
							if(orderComments.getScoreItem3()!=null&&!"".equals(orderComments.getScoreItem3())){
								DataDictItem item = dataDictItemService.getDataDictItem(orderComments.getScoreItem3());
								content+=item.getItemValue()+",";
							}
							if(orderComments.getScoreItem4()!=null&&!"".equals(orderComments.getScoreItem4())){
								DataDictItem item = dataDictItemService.getDataDictItem(orderComments.getScoreItem4());
								content+=item.getItemValue()+",";
							}
							if(orderComments.getScoreItem5()!=null&&!"".equals(orderComments.getScoreItem5())){
								DataDictItem item = dataDictItemService.getDataDictItem(orderComments.getScoreItem5());
								content+=item.getItemValue()+",";
							}
							if(orderComments.getScoreItem6()!=null&&!"".equals(orderComments.getScoreItem6())){
								DataDictItem item = dataDictItemService.getDataDictItem(orderComments.getScoreItem6());
								content+=item.getItemValue()+",";
							}
							if(content.length()>0){
								content=content.substring(0, content.length()-1);
							}
							orderComments.setContent(content);
							ResultInfo<OrderComments> resultInfo=commentsService.addOrderComments(orderComments, null);
							if (resultInfo.getCode().equals("1")) {
								result.setCode(OrderConstant.success_code);
								result.setMsg(OrderConstant.pingjiaOrder_msg);
							}else if(resultInfo.getCode().equals("0")){
								result.setCode(OrderConstant.fail_code);
								result.setMsg(OrderConstant.fail_msg);
							}
						}
						return result;
					} 
				    //订单评论显示
				    @RequestMapping("orderCommentView")
				    @ResponseBody
					public ResultInfo<OrderCommentsVo> orderCommentView(String orderNo){
				    	ResultInfo<OrderCommentsVo> result = new ResultInfo<OrderCommentsVo>();
				    	if(orderNo==null||"".equals(orderNo)){
				    		result.setCode(Constant.FAIL);
				    		result.setMsg("订单编号为空");
				    		return result;
				    	}
				    	OrderComments orderCommentsQ = new OrderComments();
						orderCommentsQ.setOrderNo(orderNo);
						List<OrderComments> commentList = commentsService.getOrderCommentsList(new Query(orderCommentsQ));
						OrderComments orderComments = commentList.get(0);
				    	OrderCommentsVo commtentsVo = new OrderCommentsVo();
				    	if(orderComments!=null){
				    		if(orderComments.getScoreItem1()!=null&&!"".equals(orderComments.getScoreItem1())){
				    			DataDictItem item = dataDictItemService.getDataDictItem(orderComments.getScoreItem1());
				    			commtentsVo.setScoreItem1(item.getItemValue());
				    		}
				    		if(orderComments.getScoreItem2()!=null&&!"".equals(orderComments.getScoreItem2())){
				    			DataDictItem item = dataDictItemService.getDataDictItem(orderComments.getScoreItem2());
				    			commtentsVo.setScoreItem2(item.getItemValue());
				    		}
				    		if(orderComments.getScoreItem3()!=null&&!"".equals(orderComments.getScoreItem3())){
				    			DataDictItem item = dataDictItemService.getDataDictItem(orderComments.getScoreItem3());
				    			commtentsVo.setScoreItem3(item.getItemValue());
				    		}
				    		if(orderComments.getScoreItem4()!=null&&!"".equals(orderComments.getScoreItem4())){
				    			DataDictItem item = dataDictItemService.getDataDictItem(orderComments.getScoreItem4());
				    			commtentsVo.setScoreItem4(item.getItemValue());
				    		}
				    		if(orderComments.getScoreItem5()!=null&&!"".equals(orderComments.getScoreItem5())){
				    			DataDictItem item = dataDictItemService.getDataDictItem(orderComments.getScoreItem5());
				    			commtentsVo.setScoreItem1(item.getItemValue());
				    		}
				    		if(orderComments.getScoreItem6()!=null&&!"".equals(orderComments.getScoreItem6())){
				    			DataDictItem item = dataDictItemService.getDataDictItem(orderComments.getScoreItem6());
				    			commtentsVo.setScoreItem4(item.getItemValue());
				    		}
				    		commtentsVo.setScore(orderComments.getScore());
				    		if("1".equals(orderComments.getScore())){
				    			List<DataDictItem> starLevelFive = dataDictItemService.getDataDictItemListByCatCode("STAR_LEVEL_ONE");// 订单评价一星级
				    			commtentsVo.setTitleContent(starLevelFive.get(0).getMemo());
				    		}else if("2".equals(orderComments.getScore())){
				    			List<DataDictItem> starLevelFive = dataDictItemService.getDataDictItemListByCatCode("STAR_LEVEL_TWO");// 订单评价二星级
				    			commtentsVo.setTitleContent(starLevelFive.get(0).getMemo());
				    		}else if("3".equals(orderComments.getScore())){
				    			List<DataDictItem> starLevelFive = dataDictItemService.getDataDictItemListByCatCode("STAR_LEVEL_THREE");// 订单评价三星级
				    			commtentsVo.setTitleContent(starLevelFive.get(0).getMemo());
				    		}else if("4".equals(orderComments.getScore())){
				    			List<DataDictItem> starLevelFive = dataDictItemService.getDataDictItemListByCatCode("STAR_LEVEL_FOUR");// 订单评价四星级
				    			commtentsVo.setTitleContent(starLevelFive.get(0).getMemo());
				    		}else if("5".equals(orderComments.getScore())){
				    			List<DataDictItem> starLevelFive = dataDictItemService.getDataDictItemListByCatCode("STAR_LEVEL_FIVE");// 订单评价五星级
				    			commtentsVo.setTitleContent(starLevelFive.get(0).getMemo());
				    		}
				    		commtentsVo.setContent(orderComments.getContent());
				    		commtentsVo.setRemarks(orderComments.getRemarks());
				    		result.setCode(Constant.SECCUESS);
				    		result.setData(commtentsVo);
				    		return result;
				    	}else{
				    		result.setCode(Constant.FAIL);
				    		result.setMsg("您还没有评论");
				    		return result;
				    	}
				    }
}