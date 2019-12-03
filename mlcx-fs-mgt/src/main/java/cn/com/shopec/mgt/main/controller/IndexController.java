package cn.com.shopec.mgt.main.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.common.constants.Constant;
import cn.com.shopec.core.car.service.CarOnlineCountService;
import cn.com.shopec.core.car.service.CarService;
import cn.com.shopec.core.car.service.CarStatusService;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.customer.service.CustomerFeedbackService;
import cn.com.shopec.core.customer.service.OrderCommentsService;
import cn.com.shopec.core.finace.model.Invoice;
import cn.com.shopec.core.finace.service.DepositRefundService;
import cn.com.shopec.core.finace.service.InvoiceService;
import cn.com.shopec.core.member.service.MemberService;
import cn.com.shopec.core.monitor.service.WarningService;
import cn.com.shopec.core.order.service.OrderService;
import cn.com.shopec.core.order.service.PricingPackOrderService;
import cn.com.shopec.core.resource.service.ParkOpeningService;
import cn.com.shopec.core.resource.service.ParkService;
import cn.com.shopec.core.system.model.SysPermission;
import cn.com.shopec.core.system.service.SysPermissionService;
import cn.com.shopec.mgt.common.BaseController;

@Controller
@RequestMapping("/index")
public class IndexController extends BaseController {

	@Resource
	private OrderService orderService;

	@Resource
	private MemberService memberService;

	@Resource
	private CarService carService;

	@Resource
	private CarStatusService carStatusService;

	@Resource
	private ParkService parkService;

	@Resource
	private WarningService warningService;

	@Resource
	private DepositRefundService depositRefundService;

	@Resource
	private PricingPackOrderService pricingPackOrderService;

	@Resource
	private InvoiceService invoiceService;

	@Resource
	private CarOnlineCountService carOnlineCountService;

	@Resource
	private ParkOpeningService parkOpeningService;

	@Resource
	public CustomerFeedbackService customerFeedbackService;

	@Resource
	private OrderCommentsService orderCommentsService;
	@Resource
	private SysPermissionService sysPermissionService;

	@RequestMapping("test")
	public String test() {
		return "main/test";
	}

	/**
	 * 首页头部数据
	 * 
	 * @return
	 */
	@RequestMapping("headerData")
	@ResponseBody
	public ResultInfo<Map<String, Object>> headerData() {
		ResultInfo<Map<String, Object>> resultInfo = new ResultInfo<>();
		Map<String, Object> map = new HashMap<>();
		SysPermission sp = new SysPermission();
		sp.setIsAvailable(1);
		// 订单总数id

		sp.setPermName("订单列表");
		Query qo = new Query(sp);
		List<SysPermission> spo = sysPermissionService.list(qo);
		if (spo != null && spo.size() > 0) {
			map.put("orderNumId", spo.get(0).getPermId());
		}
		// 会员总数id

		sp.setPermName("会员列表");
		Query qm = new Query(sp);
		List<SysPermission> spm = sysPermissionService.list(qm);
		if (spm != null && spm.size() > 0) {
			map.put("memberNumId", spm.get(0).getPermId());
			map.put("censoredMemberNumId", spm.get(0).getPermId()); // 认证会员总数id
		}
		// 车辆总数id
		sp.setPermName("车辆列表");
		Query qc = new Query(sp);
		List<SysPermission> spc = sysPermissionService.list(qc);
		if (spc != null && spc.size() > 0) {
			map.put("carNumId", spc.get(0).getPermId());

		}
		// 场站总数id
		sp.setPermName("场站列表");
		Query qp = new Query(sp);
		List<SysPermission> sqp = sysPermissionService.list(qp);
		if (sqp != null && sqp.size() > 0) {
			map.put("parkNumId", sqp.get(0).getPermId());

		}

		map.put("orderNum", orderService.countOrder(new Query())); // 订单总数
		map.put("memberNum", memberService.count(new Query())); // 会员总数
		map.put("censoredMemberNum", memberService.countMemberByCensorStatus(1)); // 认证会员总数
		map.put("carNum", carService.count(new Query())); // 车辆总数
		map.put("parkNum", parkService.count(new Query())); // 场站总数
		resultInfo.setCode(Constant.SECCUESS);
		resultInfo.setData(map);
		return resultInfo;
	}

	/**
	 * 待办数据
	 * 
	 * @return
	 */
	@RequestMapping("toDoData")
	@ResponseBody
	public ResultInfo<Map<String, Object>> toDoData() {
		ResultInfo<Map<String, Object>> resultInfo = new ResultInfo<>();
		Map<String, Object> map = new HashMap<>();
		SysPermission sp = new SysPermission();
		sp.setIsAvailable(1);
		// 押金退款id
		sp.setPermName("押金退款列表");
		Query qwrf = new Query(sp);
		List<SysPermission> swrf = sysPermissionService.list(qwrf);
		if (swrf != null && swrf.size() > 0) {
			map.put("waitRefundNumId", swrf.get(0).getPermId());
		}
		// 会员总数id
		sp.setPermName("会员列表");
		Query qm = new Query(sp);
		List<SysPermission> spm = sysPermissionService.list(qm);
		if (spm != null && spm.size() > 0) {
			map.put("waitCensorMemberNumId", spm.get(0).getPermId());

		}
		// 非订单用车Id
		sp.setPermName("非订单用车");
		Query qonOrderNum = new Query(sp);
		List<SysPermission> sonOrderNum = sysPermissionService.list(qonOrderNum);
		if (sonOrderNum != null && sonOrderNum.size() > 0) {
			map.put("onOrderNumId", sonOrderNum.get(0).getPermId());

		}
		// 未开发票总数
		sp.setPermName("发票开票列表");
		Query qinvoiceNum = new Query(sp);
		List<SysPermission> sinvoiceNum = sysPermissionService.list(qinvoiceNum);
		if (sinvoiceNum != null && sinvoiceNum.size() > 0) {
			map.put("invoiceNumId", sinvoiceNum.get(0).getPermId());

		}
		// 客服反馈列表未回复数量Id
		sp.setPermName("客服回复列表");
		Query qcustomerFeedbackNum = new Query(sp);
		List<SysPermission> scustomerFeedbackNum = sysPermissionService.list(qcustomerFeedbackNum);
		if (scustomerFeedbackNum != null && scustomerFeedbackNum.size() > 0) {
			map.put("customerFeedbackNumId", scustomerFeedbackNum.get(0).getPermId());

		}
		// 今天新增网点反馈数量id
		sp.setPermName("网点开通建议");
		Query qnewParkOpeningNum = new Query(sp);
		List<SysPermission> snewParkOpeningNum = sysPermissionService.list(qnewParkOpeningNum);
		if (snewParkOpeningNum != null && snewParkOpeningNum.size() > 0) {
			map.put("newParkOpeningNumId", snewParkOpeningNum.get(0).getPermId());

		}
		sp.setPermName("订单评价信息");
		Query qo = new Query(sp);
		List<SysPermission> spo = sysPermissionService.list(qo);
		if (spo != null && spo.size() > 0) {
			map.put("orderCommentsNumId", spo.get(0).getPermId());
		}
		map.put("waitCensorMemberNum", memberService.countMemberByCensorStatus(2)); // 待审核会员总数
		map.put("waitRefundNum", depositRefundService.getTodoIndexValue()); // .押金退款待审核总数
		map.put("onOrderNum", carStatusService.countNoOrderUseCar()); // 非订单用车
		// map.put("lowPowerNum", carStatusService.countCarMinLowPower()); //
		// 小电瓶低电
		map.put("newParkOpeningNum", parkOpeningService.countParkOpening()); // 今天新增网点反馈数量
		map.put("customerFeedbackNum", customerFeedbackService.countCustomerFeedbackNum()); // 客服反馈列表未回复数量
		map.put("orderCommentsNum", orderCommentsService.countOrderComments()); // 当天新增订单评价数量

		Invoice Invoice = new Invoice();
		Invoice.setInvoiceStatus(0);
		map.put("invoiceNum", invoiceService.count(new Query())); // 未开发票总数
		resultInfo.setCode(Constant.SECCUESS);
		resultInfo.setData(map);
		return resultInfo;
	}

	/**
	 * 交易数据
	 * 
	 * @return
	 */
	@RequestMapping("transactionData")
	@ResponseBody
	public ResultInfo<Map<String, Object>> transactionData() {
		ResultInfo<Map<String, Object>> resultInfo = new ResultInfo<>();
		Map<String, Object> map = orderService.getTransactionCountMap();
		SysPermission sp = new SysPermission();
		sp.setIsAvailable(1);
		sp.setPermName("订单列表");
		Query qo = new Query(sp);
		List<SysPermission> spo = sysPermissionService.list(qo);
		if (spo != null && spo.size() > 0) {
			map.put("notPayOrderId", spo.get(0).getPermId());
			map.put("todayOrderId", spo.get(0).getPermId());
			map.put("doingOrderId", spo.get(0).getPermId());

		}
		sp.setPermName("套餐订单列表");
		Query qrechargeNum = new Query(sp);
		List<SysPermission> srechargeNum = sysPermissionService.list(qrechargeNum);
		if (srechargeNum != null && srechargeNum.size() > 0) {
			map.put("rechargeNumId", srechargeNum.get(0).getPermId());

		}

		map.put("rechargeNum", pricingPackOrderService.countRecharge()); // 今日充值笔数
		resultInfo.setCode(Constant.SECCUESS);
		resultInfo.setData(map);
		return resultInfo;
	}

	/**
	 * 车务数据
	 * 
	 * @return
	 */
	@RequestMapping("carServiceData")
	@ResponseBody
	public ResultInfo<Map<String, Object>> carServiceData() {
		ResultInfo<Map<String, Object>> resultInfo = new ResultInfo<>();
		Map<String, Object> map = carService.carServiceMap();
		SysPermission sp = new SysPermission();
		sp.setIsAvailable(1);

		sp.setPermName("场站列表");
		Query qp = new Query(sp);
		List<SysPermission> sqp = sysPermissionService.list(qp);
		if (sqp != null && sqp.size() > 0) {
			map.put("lotParkingSpaceId", sqp.get(0).getPermId());

		}

		sp.setPermName("调度工单列表");
		Query qwaitWorkerOrder = new Query(sp);
		List<SysPermission> swaitWorkerOrder = sysPermissionService.list(qwaitWorkerOrder);
		if (swaitWorkerOrder != null && swaitWorkerOrder.size() > 0) {
			map.put("waitWorkerOrderId", swaitWorkerOrder.get(0).getPermId());
			map.put("doingWorkerOrderId", swaitWorkerOrder.get(0).getPermId());
		}

		sp.setPermName("车辆状态列表");
		Query qcarLowPower = new Query(sp);
		List<SysPermission> scarLowPower = sysPermissionService.list(qcarLowPower);
		if (scarLowPower != null && scarLowPower.size() > 0) {
			map.put("carLowPowerId", scarLowPower.get(0).getPermId());

		}
		sp.setPermName("车辆列表");
		Query qcar = new Query(sp);
		List<SysPermission> scar = sysPermissionService.list(qcar);
		if (scar != null && scar.size() > 0) {
			map.put("onlineId", scar.get(0).getPermId());
			map.put("offlineId", scar.get(0).getPermId());

		}
		resultInfo.setCode(Constant.SECCUESS);
		resultInfo.setData(map);
		return resultInfo;
	}

	/**
	 * 监控告警数据
	 * 
	 * @return
	 */
	@RequestMapping("warningData")
	@ResponseBody
	public ResultInfo<Map<String, Object>> warningData() {
		ResultInfo<Map<String, Object>> resultInfo = new ResultInfo<>();
		Map<String, Object> map = new HashMap<>();
		SysPermission sp = new SysPermission();
		sp.setIsAvailable(1);
		sp.setPermName("车辆列表");
		Query qcar = new Query(sp);
		List<SysPermission> scar = sysPermissionService.list(qcar);
		if (scar != null && scar.size() > 0) {
			map.put("carIdleNumId", scar.get(0).getPermId());
		}

		sp.setPermName("车辆状态列表");
		Query qcarLowPower = new Query(sp);
		List<SysPermission> scarLowPower = sysPermissionService.list(qcarLowPower);
		if (scarLowPower != null && scarLowPower.size() > 0) {
			map.put("carBrokenLineNumId", scarLowPower.get(0).getPermId());
			map.put("lowPowerNumId", scarLowPower.get(0).getPermId());
		}
		// 非订单用车Id
		sp.setPermName("非订单用车");
		Query qonOrderNum = new Query(sp);
		List<SysPermission> sonOrderNum = sysPermissionService.list(qonOrderNum);
		if (sonOrderNum != null && sonOrderNum.size() > 0) {
			map.put("onOrderNumId", sonOrderNum.get(0).getPermId());
		}
		sp.setPermName("订单列表");
		Query qo = new Query(sp);
		List<SysPermission> spo = sysPermissionService.list(qo);
		if (spo != null && spo.size() > 0) {
			map.put("excessOrderNumId", spo.get(0).getPermId());
		}
		map.put("carIdleNum", carService.countCarIdle()); // 闲置车辆
		map.put("onOrderNum", carStatusService.countNoOrderUseCar()); // 非订单用车
		map.put("lowPowerNum", carStatusService.countCarMinLowPower()); // 小电瓶低电
		map.put("excessOrderNum", orderService.countExcessOrder()); // 超额订单
		map.put("carBrokenLineNum", carStatusService.countCarBrokenLine()); // 断电车辆
		resultInfo.setCode(Constant.SECCUESS);
		resultInfo.setData(map);
		return resultInfo;
	}

	/**
	 * 获取近10天的订单数
	 * 
	 * @return
	 */
	@RequestMapping("getOrderDay10Count")
	@ResponseBody
	public ResultInfo<Map<String, Object>> getOrderDay10Count() {
		ResultInfo<Map<String, Object>> resultInfo = new ResultInfo<>();
		Map<String, Object> map = orderService.getOrderDay10CountMap();
		resultInfo.setCode(Constant.SECCUESS);
		resultInfo.setData(map);
		return resultInfo;
	}

	/**
	 * 获取近10天的会员数
	 * 
	 * @return
	 */
	@RequestMapping("getMember10Count")
	@ResponseBody
	public ResultInfo<Map<String, Object>> getMember10Count() {
		ResultInfo<Map<String, Object>> resultInfo = new ResultInfo<>();
		Map<String, Object> map = memberService.getMemberDay10CountMap();
		resultInfo.setCode(Constant.SECCUESS);
		resultInfo.setData(map);
		return resultInfo;
	}

	/**
	 * 获取近10天的车辆上下线数
	 * 
	 * @return
	 */
	@RequestMapping("getCarLine10Count")
	@ResponseBody
	public ResultInfo<Map<String, Object>> getCarLine10Count() {
		ResultInfo<Map<String, Object>> resultInfo = new ResultInfo<>();
		Map<String, Object> map = carOnlineCountService.getCarLine10CountMap();
		resultInfo.setCode(Constant.SECCUESS);
		resultInfo.setData(map);
		return resultInfo;
	}

	/**
	 * 获取近10天的告警线数
	 * 
	 * @return
	 */
	@RequestMapping("getWarning10Count")
	@ResponseBody
	public ResultInfo<Map<String, Object>> getWarning10Count() {
		ResultInfo<Map<String, Object>> resultInfo = new ResultInfo<>();
		Map<String, Object> map = warningService.getWarningDay10CountMap();
		resultInfo.setCode(Constant.SECCUESS);
		resultInfo.setData(map);
		return resultInfo;
	}

	/**
	 * 获取近10天的订单数
	 * 
	 * @return
	 */
	@RequestMapping("getCarOrderCount")
	@ResponseBody
	public ResultInfo<Map<String, Object>> getCarOrderCount() {
		ResultInfo<Map<String, Object>> resultInfo = new ResultInfo<>();
		Map<String, Object> map = orderService.getCarOrderCountMap();
		resultInfo.setCode(Constant.SECCUESS);
		resultInfo.setData(map);
		return resultInfo;
	}

}
