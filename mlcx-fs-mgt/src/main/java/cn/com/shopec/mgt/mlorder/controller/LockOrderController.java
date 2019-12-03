package cn.com.shopec.mgt.mlorder.controller;

import java.text.ParseException;
import java.util.Date;

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
import cn.com.shopec.core.ml.service.ChargingStationService;
import cn.com.shopec.core.ml.service.LockBillingSchemeService;
import cn.com.shopec.core.ml.service.ParkingLockService;
import cn.com.shopec.core.mlorder.model.LockOrder;
import cn.com.shopec.core.mlorder.service.LockOrderService;
import cn.com.shopec.mgt.common.BaseController;

/**
 * @category 地锁订单
 */
@Controller
@RequestMapping("lockOrder")
public class LockOrderController extends BaseController {

	@Resource
	private LockOrderService lockOrderServiceImpl;
	@Resource
	private LockBillingSchemeService  lockBillingSchemeServiceImpl;
	@Resource
	private ChargingStationService chargingStationServiceImpl;
	@Resource
	private ParkingLockService parkingLockServiceImpl;
	
	/**
	 * 进入地锁订单页面
	 * @param model
	 * @return
	 */
	@RequestMapping("/getLockOrder")
	public String getlockOrder(Model model) {
		model.addAttribute("csList", chargingStationServiceImpl.getChargingStationList(new Query()));
		return "mlorder/lockOrder_list";
	}

	/**
	 * 地锁订单列表展示
	 * @param lockOrder
	 * @param query
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping("/pageListlockOrder")
	@ResponseBody
	public PageFinder<LockOrder> pageListlockOrder(@ModelAttribute("lockOrder") LockOrder lockOrder, Query query)
			throws ParseException {
		return lockOrderServiceImpl.getLockOrderPagedList(new Query(query.getPageNo(), query.getPageSize(), lockOrder));
	}
	/**
	 * 查看详情
	 * @param orderNo
	 * @param model
	 * @return
	 */
	@RequestMapping("/toLockOrderView")
	public String tolockOrderView(String orderNo,Model model){
		model.addAttribute("lockOrder", lockOrderServiceImpl.getLockOrder(orderNo));
		return "mlorder/lockOrder_view";
	}
	/**
	 * 结束地锁
	 * @param lockOrder
	 * @return
	 */
	
	@RequestMapping("/disableLockOrder")
	@ResponseBody
	public ResultInfo<LockOrder> disablelockOrder(@ModelAttribute("lockOrder") LockOrder lockOrder){
		LockOrder lockOrder2 = lockOrderServiceImpl.getLockOrder(lockOrder.getOrderNo());
		lockOrder2.setFinishReason(lockOrder.getFinishReason());//结束理由
		// 结束地锁
		lockOrder2.setOrderEndTime(new Date());// 结束时间
		lockOrder2.setFinishType(1);// 结束类型 1、后台结束
		ParkingLock parkingLock = parkingLockServiceImpl.getParkingLock(lockOrder2.getParkingLockNo());//获取地锁信息
		return lockOrderServiceImpl.updateLockOrder_two(lockOrder2, getOperator(), lockBillingSchemeServiceImpl.getLockBillingScheme(parkingLock.getParkingLockChargingNo()));
	}

}
