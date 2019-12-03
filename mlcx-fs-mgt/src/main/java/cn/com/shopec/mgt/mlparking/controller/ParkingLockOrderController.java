package cn.com.shopec.mgt.mlparking.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.ml.model.ParkingLock;
import cn.com.shopec.core.mlorder.model.LockOrder;
import cn.com.shopec.core.mlparking.model.CParkBilling;
import cn.com.shopec.core.mlparking.model.CParkOrder;
import cn.com.shopec.core.mlparking.model.CParking;
import cn.com.shopec.core.mlparking.service.CParkBillingService;
import cn.com.shopec.core.mlparking.service.CParkOrderService;
import cn.com.shopec.core.mlparking.service.CParkingService;
import cn.com.shopec.mgt.common.BaseController;

/**
 * @author daiyuanbao
 * @category 停车场订单
 *
 */
@Controller
@RequestMapping("parkingOrder")
public class ParkingLockOrderController extends BaseController {
	@Resource
	private CParkOrderService parkOrderService;
	@Resource
	private CParkingService parkingService;
	@Resource
	private CParkBillingService parkBillingService;
	/**
	 * 进入订单列表
	 * @return
	 */
	@RequestMapping("/getOrder")
	public String getOrder(Model model){
		model.addAttribute("parking", parkingService.getCParkingList(new Query()));
		return "mlpark/parkingOrder_list";
	}
	
	/**
	 * 获取订单列表
	 * @param parkOrder
	 * @param query
	 * @return
	 */
	@RequestMapping("/toParkOrderList")
	@ResponseBody
	public PageFinder<CParkOrder> toParkOrderList(@ModelAttribute("CParkOrder") CParkOrder parkOrder,
            Query query){
		return parkOrderService.getCParkOrderPagedList(new Query(query.getPageNo(), query.getPageSize(), parkOrder));
	}
	/**
	 * 查看详情
	 * @param parkOrderNo
	 * @param model
	 * @return
	 */
	@RequestMapping("/ParkOrderView")
	public String ParkOrderView(String parkOrderNo,Model model){
		model.addAttribute("parkOrder", parkOrderService.getCParkOrder(parkOrderNo));
		return "mlpark/parkingOrder_view";
	}
	
	
	/**
	 * 结束订单--地锁
	 * @param lockOrder
	 * @return
	 */
	
	@RequestMapping("/disableparkingOrder")
	@ResponseBody
	public ResultInfo<CParkOrder> disableparkingOrder(@ModelAttribute("CParkOrder") CParkOrder parkOrder){
		CParkOrder cParkOrder = parkOrderService.getCParkOrder(parkOrder.getParkOrderNo());
		cParkOrder.setFinishReason(parkOrder.getFinishReason());
		cParkOrder.setFinishType(1);// 结束类型 1、后台结束
		CParking cParking = parkingService.getCParking(cParkOrder.getParkingNo());
		CParkBilling cParkBilling = parkBillingService.getCParkBilling(cParking.getBillingSchemeNo());
		return parkOrderService.updateCParkOrder_two(cParkOrder,cParkBilling,cParking,getOperator());
	}

}
