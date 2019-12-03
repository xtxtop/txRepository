package cn.com.shopec.mgt.dailyrental.controller;

import java.text.SimpleDateFormat;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.common.constants.Constant;
import cn.com.shopec.common.utils.ECDateUtils;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.car.model.CarModel;
import cn.com.shopec.core.dailyrental.model.Merchant;
import cn.com.shopec.core.dailyrental.model.MerchantInventory;
import cn.com.shopec.core.dailyrental.model.MerchantInventoryDate;
import cn.com.shopec.core.dailyrental.model.MerchantOrder;
import cn.com.shopec.core.dailyrental.model.OrderDay;
import cn.com.shopec.core.dailyrental.model.ParkDay;
import cn.com.shopec.core.car.service.CarModelService;
import cn.com.shopec.core.dailyrental.service.MerchantInventoryDateService;
import cn.com.shopec.core.dailyrental.service.MerchantInventoryService;
import cn.com.shopec.core.dailyrental.service.MerchantOrderService;
import cn.com.shopec.core.dailyrental.service.MerchantService;
import cn.com.shopec.core.dailyrental.service.OrderDayService;
import cn.com.shopec.core.dailyrental.service.ParkDayService;
import cn.com.shopec.core.member.service.MemberService;
import cn.com.shopec.core.system.model.SysParam;
import cn.com.shopec.core.system.service.SysParamService;
import cn.com.shopec.mgt.common.BaseController;

@Controller
@RequestMapping("orderDay")
public class OrderDayController extends BaseController {

	@Resource
	public OrderDayService orderDayService;
	@Resource
	public MemberService memberService;
	@Resource
	public MerchantOrderService merchantOrderService;
	@Resource
	public MerchantService merchantService;
	@Resource
	public ParkDayService parkDayService;
	@Resource
	private MerchantInventoryService merchantInventoryService;
	@Resource
	private MerchantInventoryDateService merchantInventoryDateService;
	@Resource
	private CarModelService carModelService;
	@Resource
	private SysParamService sysParamService;
	
	/*
	 * 显示日租列表页
	 */
	@RequestMapping("toOrderDayList")
	public String toOrderDayList(ModelMap modelMap) {
		List<Merchant> merchantList = merchantService.getMerchantList(new Query());
		modelMap.put("merchantList", merchantList);
		SysParam sys = sysParamService.getByParamKey("takeCarDoorAmount");
		SysParam sysc = sysParamService.getByParamKey("cancelAmount");
		if(sys != null){
			modelMap.put("takeCarDoorAmount", sys.getParamValue());
		}
		if(sysc != null){
			modelMap.put("cancelAmount", sysc.getParamValue());
		}
		return "dailyrental/orderday/order_day_list";
	}

	/*
	 * 显示日租订单列表页
	 */
	@RequestMapping("pageListOrderDay")
	@ResponseBody
	public PageFinder<OrderDay> pageListOrderDay(@ModelAttribute("orderDay") OrderDay orderDay, Query query) {
		Query q = new Query(query.getPageNo(), query.getPageSize(), orderDay);
		return orderDayService.getOrderDayPagedListForMgt(q);
	}

	
	/*
	 * 送车上门未取车 取消订单收取违约金
	 */
	@RequestMapping("cancelOrderDay")
	@ResponseBody
	public ResultInfo<OrderDay> cancelOrderDay(String orderNo) {
		ResultInfo<OrderDay> result = new ResultInfo<OrderDay>();
		OrderDay orderDay =orderDayService.getOrderDay(orderNo);
		SysParam sys = sysParamService.getByParamKey("takeCarDoorAmount");
		if(orderDay != null){
			orderDay.setOrderStatus(3);
			if(sys != null ){
				orderDay.setTakeCarDoorAmount(Double.valueOf(sys.getParamValue()));
			}else{
				orderDay.setTakeCarDoorAmount(0.0);
			}
			ResultInfo<OrderDay> orderDays=orderDayService.updateOrderDay(orderDay, getOperator());
			if(orderDays.getCode().equals("1")){
				MerchantOrder  merchantOrder = merchantOrderService.getOrderDayNo(orderNo);
				if(merchantOrder != null){
					merchantOrder.setMerchantOrderStatus(6);
				}
				merchantOrderService.updateMerchantOrder(merchantOrder, getOperator());
				//释放商家和平台预占库存
				SimpleDateFormat myFmt=new SimpleDateFormat("yyyy-MM-dd"); 
				MerchantInventoryDate merchantInventoryDate = new MerchantInventoryDate();
				merchantInventoryDate.setInventoryDateStart(myFmt.format(orderDay.getAppointmentTakeTime()));
				merchantInventoryDate.setInventoryDateEnd(myFmt.format(orderDay.getAppointmentReturnTime()));
				merchantInventoryDate.setCarModelId(merchantOrder.getCarModelId());
				merchantInventoryDate.setMerchantId(merchantOrder.getMerchantId());
				merchantInventoryDateService.releaseLeasedQuantity(merchantInventoryDate,orderDay,getOperator());

			}
			result.setCode(Constant.SECCUESS);
			result.setMsg("取消成功");
			return result;
		}else{
			result.setCode(Constant.FAIL);
			result.setMsg("未找到订单");
			return result;
		}
	}
	
	
	
	/*
	 * 门店未取车 取消订单收取违约金
	 */
	@RequestMapping("menCancelOrderDay")
	@ResponseBody
	public ResultInfo<OrderDay> menCancelOrderDay(String orderNo) {
		ResultInfo<OrderDay> result = new ResultInfo<OrderDay>();
		OrderDay orderDay =orderDayService.getOrderDay(orderNo);
		SysParam sysc = sysParamService.getByParamKey("cancelAmount");
		if(orderDay != null){
			orderDay.setOrderStatus(3);
			if(sysc != null ){
				orderDay.setCancelAmount(Double.valueOf(sysc.getParamValue()));
			}else{
				orderDay.setCancelAmount(0.0);
			}
			ResultInfo<OrderDay> orderDays=orderDayService.updateOrderDay(orderDay, getOperator());
			if(orderDays.getCode().equals("1")){
				MerchantOrder  merchantOrder = merchantOrderService.getOrderDayNo(orderNo);
				if(merchantOrder != null){
					merchantOrder.setMerchantOrderStatus(6);
				}
				merchantOrderService.updateMerchantOrder(merchantOrder, getOperator());
				//释放商家和平台预占库存
				SimpleDateFormat myFmt=new SimpleDateFormat("yyyy-MM-dd"); 
				MerchantInventoryDate merchantInventoryDate = new MerchantInventoryDate();
				merchantInventoryDate.setInventoryDateStart(myFmt.format(orderDay.getAppointmentTakeTime()));
				merchantInventoryDate.setInventoryDateEnd(myFmt.format(orderDay.getAppointmentReturnTime()));
				merchantInventoryDate.setCarModelId(merchantOrder.getCarModelId());
				merchantInventoryDate.setMerchantId(merchantOrder.getMerchantId());
				merchantInventoryDateService.releaseLeasedQuantity(merchantInventoryDate,orderDay,getOperator());
			}
			result.setCode(Constant.SECCUESS);
			result.setMsg("取消成功");
			return result;
		}else{
			result.setCode(Constant.FAIL);
			result.setMsg("未找到订单");
			return result;
		}
	}
	
	
	
	/**
	 * 日租订单详情
	 * 
	 * @return
	 */
	@RequestMapping("toOrderDayView")
	public String toOrderDayView(String orderNo, ModelMap modelMap) {
		OrderDay orderDay = orderDayService.getOrderDay(orderNo);
		modelMap.put("orderDay", orderDay);
		CarModel carModel = carModelService.getCarModel(orderDay.getCarModelId());
		modelMap.put("carModel", carModel);
		MerchantOrder merchantOrder =  merchantOrderService.getOrderDayByOrderNo(orderNo);
		if(merchantOrder!=null){
			modelMap.put("merchantOrder", merchantOrder);
			Merchant merchant = merchantService.getMerchant(merchantOrder.getMerchantId());
			modelMap.put("merchant", merchant);
		}
		return "dailyrental/orderday/order_day_view";
	}

	/**
	 * 日租订单结束页面
	 * 
	 * @return
	 */
	@RequestMapping("toOrderDayOver")
	public String toOrderDayOver(String orderNo, ModelMap modelMap) {
		OrderDay order = orderDayService.getOrderDay(orderNo);
		modelMap.put("order", order);
		if(order!=null&&order.getIsTakeCarDoor()!=1){
			ParkDay parkTake = parkDayService.getParkDay(order.getStartParkNo());
			modelMap.put("startParkDayName", parkTake.getParkName());
		}else{
			modelMap.put("startParkDayName", order.getActualTakePakeName());
		}
		if (order!=null&&order.getIsBackCarDoor()!=1) {
			ParkDay parkReturn = parkDayService.getParkDay(order.getTerminalParkNo());
			modelMap.put("returnParkDayName", parkReturn.getParkName());
		}else{
			modelMap.put("returnParkDayName", order.getActualTakePakeName());
		}
		return "dailyrental/orderday/order_day_over";
	}
	/**
	 * 指派页面
	 * 
	 * @return
	 */
	@RequestMapping("toAssignOrderDay")
	public String toAssignOrderDay(String orderNo, ModelMap modelMap) {
		OrderDay order = orderDayService.getOrderDay(orderNo);
		modelMap.put("order", order);
		if(order!=null&&order.getIsTakeCarDoor()!=1){
			ParkDay parkTake = parkDayService.getParkDay(order.getStartParkNo());
			modelMap.put("startParkDayName", parkTake.getParkName());
		}else{
			modelMap.put("startParkDayName", order.getActualTakePakeName());
		}
		if (order!=null&&order.getIsBackCarDoor()!=1) {
			ParkDay parkReturn = parkDayService.getParkDay(order.getTerminalParkNo());
			modelMap.put("returnParkDayName", parkReturn.getParkName());
		}else{
			modelMap.put("returnParkDayName", order.getActualTakePakeName());
		}
		return "dailyrental/orderday/order_day_assign";
	}
	/**
	 * 指派页面列表
	 */
	@RequestMapping("pageListAssignMerchantInventory")
	@ResponseBody
	public PageFinder<MerchantInventory> pageListAssignParkDay(String carModelId,String cityId,Query query) {
		MerchantInventory merchantInventory = new MerchantInventory();
		merchantInventory.setCarModelId(carModelId);
		merchantInventory.setCityId(cityId);
		Query q = new Query(query.getPageNo(), query.getPageSize(), merchantInventory);
		//根据车型去商家库存表查找有库存的对应的商家 
		PageFinder<MerchantInventory> merchantInventories = merchantInventoryService.getMerchantInventoryByCarModelId(q);//查出所有商家
		return merchantInventories;
	}
	/**
	 * 指派订单提交
	 */
	@RequestMapping("saveMerchantOrder")
	@ResponseBody
	public ResultInfo<MerchantOrder> saveMerchantOrder(String orderNo,String merchantId) {
		ResultInfo<MerchantOrder> result = new ResultInfo<MerchantOrder>();
		OrderDay orderDay = orderDayService.getOrderDay(orderNo);
		if(orderDay!=null){
			OrderDay orderDayForUpdate = new OrderDay();
			orderDayForUpdate.setOrderNo(orderNo);
			orderDayForUpdate.setAssignee(2);
			orderDayService.updateOrderDay(orderDayForUpdate, getOperator());
			
			MerchantOrder merchantOrderQuery = new MerchantOrder();
			merchantOrderQuery.setOrderNo(orderNo);
			merchantOrderQuery.setMerchantOrderStatus(0);
			List<MerchantOrder> merchantOrderList = merchantOrderService.getMerchantOrderList(new Query(merchantOrderQuery));
			if(merchantOrderList.size()>0){
				Merchant merchant = merchantService.getMerchant(merchantOrderList.get(0).getMerchantId());
				result.setCode(Constant.FAIL);
				result.setMsg("订单已指派给此加盟商:"+merchant.getMerchantName());
				return result;
			}
			
			MerchantOrder merchantOrder = new MerchantOrder();
			merchantOrder.setMerchantOrderStatus(0);
			merchantOrder.setMerchantId(merchantId);
			merchantOrder.setMemberName(orderDay.getMemberName());
			merchantOrder.setOrderNo(orderDay.getOrderNo());
			merchantOrder.setMemberNo(orderDay.getMemberNo());
			merchantOrder.setCarModelId(orderDay.getCarModelId());
			CarModel carModel = carModelService.getCarModel(orderDay.getCarModelId());
			merchantOrder.setCarModelName(carModel.getCarModelName());
			merchantOrder.setTakeCarParkNo(orderDay.getStartParkNo());
			ParkDay startParkDay=parkDayService.getParkDay(orderDay.getStartParkNo());
			if(startParkDay!=null){
				merchantOrder.setTakeCarParkName(startParkDay.getParkName());
			}
			merchantOrder.setReturnCarParkNo(orderDay.getTerminalParkNo());
			ParkDay returnParkDay=parkDayService.getParkDay(orderDay.getTerminalParkNo());
			if(returnParkDay!=null){
				merchantOrder.setReturnCarParkName(returnParkDay.getParkName());
			}
			return merchantOrderService.addMerchantOrder(merchantOrder, getOperator());
		}else{
			result.setCode(Constant.FAIL);
			result.setMsg("订单不存在");
			return result;
		}
	}
	/**
	 * 日租订单结束提交
	 */
	@RequestMapping("orderDayOver")
	@ResponseBody
	public ResultInfo<OrderDay> orderDayOver(@ModelAttribute("orderDay") OrderDay orderDay) {
		ResultInfo<OrderDay> resultInfo = new ResultInfo<OrderDay>();
		if (orderDay.getOrderNo() != null && orderDay.getOrderNo().length() != 0) {
			OrderDay order = orderDayService.getOrderDay(orderDay.getOrderNo());
			OrderDay updateOrderDay = new OrderDay();
			updateOrderDay.setOrderNo(order.getOrderNo());
			updateOrderDay.setActualReturnTime(ECDateUtils.getCurrentDateTime());
			if(order!=null&&order.getIsBackCarDoor()==0){
				ParkDay returnParkDay = parkDayService.getParkDay(order.getTerminalParkNo());
				if(returnParkDay!=null){
					updateOrderDay.setActualTerminalParkNo(returnParkDay.getParkId());
					updateOrderDay.setActualTerminalParkName(returnParkDay.getParkName());
				}
			}
			updateOrderDay.setActualReturnTime(ECDateUtils.getCurrentDateTime());//结束时间
			updateOrderDay.setFinshiType(3);//后台结束
			updateOrderDay.setOrderStatus(3);//已完成
			resultInfo = orderDayService.updateOrderDay(updateOrderDay, getOperator());
			MerchantOrder merchantOrder =  merchantOrderService.getOrderDayNo(orderDay.getOrderNo());
			if (merchantOrder!=null) {
				MerchantOrder merchantOrderForUpdate = new MerchantOrder();
				merchantOrderForUpdate.setMerchantOrderId(merchantOrder.getMerchantOrderId());
				merchantOrderForUpdate.setMerchantOrderStatus(5);
				merchantOrderService.updateMerchantOrder(merchantOrderForUpdate, getOperator());
				//释放商家和平台预占库存
				SimpleDateFormat myFmt=new SimpleDateFormat("yyyy-MM-dd"); 
				MerchantInventoryDate merchantInventoryDate = new MerchantInventoryDate();
				merchantInventoryDate.setInventoryDateStart(myFmt.format(order.getAppointmentTakeTime()));
				merchantInventoryDate.setInventoryDateEnd(myFmt.format(order.getAppointmentReturnTime()));
				merchantInventoryDate.setCarModelId(merchantOrder.getCarModelId());
				merchantInventoryDate.setMerchantId(merchantOrder.getMerchantId());
				merchantInventoryDateService.releaseLeasedQuantity(merchantInventoryDate,order,getOperator());
			}
			
		}else{
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg("参数错误");
		}
		return resultInfo;
	}

}
