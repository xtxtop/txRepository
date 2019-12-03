package cn.com.shopec.mgt.eportform.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.common.constants.Constant;
import cn.com.shopec.common.utils.ECCalculateUtils;
import cn.com.shopec.common.utils.ECDateUtils;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.marketing.model.PricingDeductionRecord;
import cn.com.shopec.core.marketing.model.PricingPackage;
import cn.com.shopec.core.marketing.service.PricingDeductionRecordService;
import cn.com.shopec.core.marketing.service.PricingPackageService;
import cn.com.shopec.core.marketing.vo.PricingPackageStat;
import cn.com.shopec.core.member.common.MemberConstant;
import cn.com.shopec.core.order.model.Order;
import cn.com.shopec.core.order.model.PricingPackOrder;
import cn.com.shopec.core.order.service.OrderService;
import cn.com.shopec.core.order.service.PricingPackOrderService;
import cn.com.shopec.core.order.vo.OrderAmountDayVo;
import cn.com.shopec.core.order.vo.OrderReportFormVo;
import cn.com.shopec.core.order.vo.OrderReportStatisticalVo;
import cn.com.shopec.core.system.model.SysParam;
import cn.com.shopec.core.system.service.SysParamService;
import cn.com.shopec.mgt.common.BaseController;

/**
 * 运营报表
 * @author likai
 * @version 1.1 LiHuan 修改 
 */
@Controller
@RequestMapping("/eportForm")
public class EportFormController extends BaseController {
	private static final Log log = LogFactory.getLog(EportFormController.class);
	@Resource
	private PricingPackOrderService pricingPackOrderService;
	@Resource
	private PricingDeductionRecordService pricingDeductionRecordService;
	@Resource
	private OrderService orderService;
	@Resource
	private PricingPackageService pricingPackageService;
	@Resource
	private SysParamService sysParamService;

	/**
	 * 进入套餐抵扣统计页面
	 * 
	 */
	@RequestMapping("toPackageDeductibleEportForm")
	public String toPackageDeductibleEportForm() {
		return "/reportForm/packageDeductibleEportForm";
	}

	/**
	 * 进入套餐销售统计页面
	 * 
	 */
	@RequestMapping("toSalePackageEportForm")
	public String toSalePackageEportForm() {
		return "/reportForm/salePackageEportForm";
	}

	/**
	 * 进入订单统计页面
	 * 
	 */
	@RequestMapping("toOrderEportForm")
	public String toOrderEportForm() {
		return "/reportForm/orderEportForm";
	}

	/**
	 * 查找订单统计信息（全部）
	 */
	@RequestMapping("findOrderEportForm")
	@ResponseBody
	public OrderReportFormVo findOrderEportForm() {

		// 查找已取消的订单
		Order o = new Order();
		o.setOrderStatus(4);
		List<Order> ls = orderService.getOrderList(new Query(o));
		// 查找订单数量和金额
		Order o2 = new Order();
		o2.setIsDelete(0);
		OrderReportFormVo vo = orderService.orderNumberNs(new Query(o2));
		vo.setOrderDeNumber(ls.size() + "");
		return vo;
	}

	/**
	 * 按条件查找订单统计信息
	 * 订单销售量、订单销售额按月统计
	 * @throws ParseException
	 */
	@RequestMapping("getOrderEportForm")
	@ResponseBody
	public List<OrderReportStatisticalVo> getOrderEportFormMonth(String createTimeStart, String createTimeEnd)throws ParseException {
		List<OrderReportStatisticalVo> ls = new ArrayList<OrderReportStatisticalVo>();

		if (null == createTimeStart || "".equals(createTimeStart)) {
			Date date = new Date();
			SysParam sysParam = sysParamService.getByParamKey(MemberConstant.month_parameter_key);
			int monthParmaeter = Integer.parseInt(sysParam.getParamValue());// 单位：月
			createTimeStart = ECDateUtils.getSpecifiedmonth(date, monthParmaeter);
			createTimeEnd = ECDateUtils.formatDate(date, "yyyy-MM");
		}

		// 获取所选的所有月份
		List<String> ml = ECDateUtils.getMonthBetween(createTimeStart, createTimeEnd);
		for (String time : ml) {
			OrderReportStatisticalVo vo = new OrderReportStatisticalVo();
			//新增订单数
			Order aVo = new Order();
			aVo.setEportTime(time);
			OrderReportStatisticalVo addOrder = orderService.orderNumberReportMonth(new Query(aVo));
			if (addOrder != null) {
				if (addOrder.getOrderNumber() != null) {
					vo.setOrderNumber(addOrder.getOrderNumber());
				} else {
					vo.setOrderNumber("0");
				}
			}
			//完成订单数
			Order fOrder = new Order();
			fOrder.setStartTime(time);
			OrderReportStatisticalVo finishOrder = orderService.orderNumberReportMonth(new Query(fOrder));
			if (finishOrder != null) {
				if (finishOrder.getOrderNumber() != null) {
					vo.setOrderFhNumber(finishOrder.getOrderNumber());
				} else {
					vo.setOrderFhNumber("0");
				}
			}
			//取消订单数
			Order cOrder = new Order();
			cOrder.setOrderStatus(4);
			cOrder.setEndTime(time);
			OrderReportStatisticalVo canOrder = orderService.orderNumberReportMonth(new Query(cOrder));
			if (canOrder != null) {
				if (canOrder.getOrderDeNumber() != null) {
					vo.setOrderDeNumber(canOrder.getOrderDeNumber());
				} else {
					vo.setOrderDeNumber("0");
				}
			}
			
			//订单金额和余额抵扣额
			OrderReportStatisticalVo order1 = orderService.getOrderAmountAndBalanceMonthData(time);
			if(order1 != null){
				if(order1.getOrderAmount() != null){
					vo.setOrderAmount(order1.getOrderAmount());
				} else{
					vo.setOrderAmount(0d);
				}
				
				if(order1.getBalanceAmount() != null){
					vo.setBalanceAmount(order1.getBalanceAmount());
				} else{
					vo.setBalanceAmount(0d);
				}
			} else{
				vo.setOrderAmount(0d);
				vo.setBalanceAmount(0d);
			}
			//订单付款额
			OrderReportStatisticalVo order2 = orderService.getOrderPayAmountMonthData(time);
			if(order2 != null){
				vo.setPayAmount(order2.getPayAmount());
			} else{
				vo.setPayAmount(0d);
			}
			
			vo.setTime(time);
			ls.add(vo);
		}
		return ls;
	}

	/**
	 * 订单数据累计-按月累计订单数、按月累计订单金额
	 */
	@RequestMapping("getOrderEportFormNs")
	@ResponseBody
	public List<OrderReportStatisticalVo> getOrderEportFormNs(String createTimeStart, String createTimeEnd)throws ParseException {
		log.info("进入订单数据按月累计方法...");
		List<OrderReportStatisticalVo> listOrder = new ArrayList<OrderReportStatisticalVo>();

		if (null == createTimeStart || "".equals(createTimeStart)) {
			Date date = new Date();
			SysParam sysParam = sysParamService.getByParamKey(MemberConstant.month_parameter_key);
			int monthParmaeter = Integer.parseInt(sysParam.getParamValue());// 单位：月

			createTimeStart = ECDateUtils.getSpecifiedmonth(date, monthParmaeter);
			createTimeEnd = ECDateUtils.formatDate(date, "yyyy-MM");
		}

		// 获取所选的所有月份
		List<String> ml = ECDateUtils.getMonthBetween(createTimeStart, createTimeEnd);
		String firstMonth = ml.get(0);
		
		//新增订单数
		int orderSum = 0;
		Order o3 = new Order();
		o3.setEportTime(firstMonth);
		OrderReportStatisticalVo addOrder = orderService.getFirstMonthOrderData(new Query(o3));
		if (addOrder != null) {
			if (addOrder.getOrderNumber() != null) {
				orderSum = Integer.parseInt(addOrder.getOrderNumber());
			}
		}
		//完成订单数
		int finishSum = 0;
		Order o1 = new Order();
		o1.setStartTime(firstMonth);
		OrderReportStatisticalVo finishOrder = orderService.getFirstMonthOrderData(new Query(o1));
		if (finishOrder != null) {
			if (finishOrder.getOrderNumber() != null) {
				finishSum = Integer.parseInt(finishOrder.getOrderNumber());
			}
		}
		//取消订单数
		int calSum = 0;
		Order o2 = new Order();
		o2.setOrderStatus(4);
		o2.setEndTime(firstMonth);
		OrderReportStatisticalVo cancelOrder = orderService.getFirstMonthOrderData(new Query(o2));
		if (cancelOrder != null) {
			if (cancelOrder.getOrderNumber() != null) {
				calSum = Integer.parseInt(cancelOrder.getOrderNumber());
			} 
		}
		//订单金额
		Double sumOrderAmount = 0d;
		Double sumBalanceAmount = 0d;
		OrderReportStatisticalVo  order1 = orderService.getOrderAmountAndBalanceFirstMonth(firstMonth);
		if(order1 != null){
			if(order1.getOrderAmount() != null){
				sumOrderAmount = order1.getOrderAmount();
			} else {
				sumOrderAmount = 0d;
			} 
			
			if(order1.getBalanceAmount() != null){
				sumBalanceAmount = order1.getBalanceAmount();
			} else {
				sumBalanceAmount = 0d;
			}
		} else{
			sumOrderAmount = 0d;
			sumBalanceAmount = 0d;
		}
		//订单付款额
		Double payAmount = 0d;
		OrderReportStatisticalVo order2 = orderService.getOrderPayAmountFirstMonth(firstMonth);
		if(order2 != null){
			if(order2.getPayAmount() != null){
				payAmount = order2.getPayAmount();
			} else{
				payAmount = 0d;
			}
		} else{
			payAmount = 0d;
		} 
		
		//循环所有系统参数配置的天数
		//当循环第一周时统计后，直接continue
		int sums = 0;//订单变量
		int finishSums = 0;//完成订单变量
		int calSums = 0;//取消订单变量
		Double sumOrderAmounts = 0d;
		Double sumBalanceAmounts = 0d;
		Double payAmounts = 0d;
		
		for (String time : ml) {
			OrderReportStatisticalVo vo = new OrderReportStatisticalVo();
			if(time.equals(firstMonth)){
				vo.setOrderNumber(String.valueOf(orderSum));
				sums = orderSum;
				vo.setOrderFhNumber(String.valueOf(finishSum));
				finishSums = finishSum;
				vo.setOrderDeNumber(String.valueOf(calSum));
				calSums = calSum;
				vo.setOrderAmount(sumOrderAmount);
				sumOrderAmounts = sumOrderAmount;
				vo.setBalanceAmount(sumBalanceAmount);
				sumBalanceAmounts = sumBalanceAmount;
				vo.setPayAmount(payAmount);
				payAmounts = payAmount;
				continue;
			}
			
			//从第二天开始查询 
			//新增订单数
			Order addOr = new Order();
			addOr.setEportTime(time);
			OrderReportStatisticalVo vo1 = orderService.orderNumberReportMonth(new Query(addOr));
			if (vo1 != null) {
				if (vo1.getOrderNumber() != null) {
					sums = Integer.parseInt(vo1.getOrderNumber()) + sums;
					vo.setOrderNumber(String.valueOf(sums));
				} else {
					vo.setOrderNumber(String.valueOf(orderSum + 0));
				}
			} else {
				vo.setOrderNumber(String.valueOf(orderSum + 0));
			}
			//完成订单数
			Order finOr = new Order();
			finOr.setStartTime(time);
			OrderReportStatisticalVo vo2 = orderService.orderNumberReportMonth(new Query(finOr));
			if(vo2 != null){
				if(vo2.getOrderNumber() != null){
					finishSums = Integer.parseInt(vo2.getOrderNumber()) + finishSums;
					vo.setOrderFhNumber(String.valueOf(finishSums));
				} else {
					vo.setOrderFhNumber(String.valueOf(finishSum + 0));
				} 
			} else {
				vo.setOrderFhNumber(String.valueOf(finishSum + 0));
			}
			//取消订单数
			Order calOr = new Order();
			calOr.setOrderStatus(4);
			calOr.setEndTime(time);
			OrderReportStatisticalVo vo3 = orderService.orderNumberReportMonth(new Query(calOr));
			if (vo3 != null) {
				if (vo3.getOrderNumber() != null) {
					calSums = Integer.parseInt(vo3.getOrderNumber()) + calSums;
					vo.setOrderDeNumber(String.valueOf(calSums));
				} else {
					vo.setOrderDeNumber(String.valueOf(calSum + 0));
				} 
			} else {
				vo.setOrderDeNumber(String.valueOf(calSum + 0));
			}
			//订单金额和余额抵扣额统计
			OrderReportStatisticalVo  order = orderService.getOrderAmountAndBalanceMonthData(time);
			if(order != null){
				if(order.getOrderAmount() != null){
					sumOrderAmounts = ECCalculateUtils.add(order.getOrderAmount(), sumOrderAmounts);
					vo.setOrderAmount(sumOrderAmounts);
				} else {
					vo.setOrderAmount(ECCalculateUtils.add(0d, sumOrderAmounts));
				}
				
				if(order.getBalanceAmount() != null){
					sumBalanceAmounts = ECCalculateUtils.add(order.getBalanceAmount(), sumBalanceAmounts);
					vo.setBalanceAmount(sumBalanceAmounts);
				} else{
					vo.setBalanceAmount(ECCalculateUtils.add(0d, sumBalanceAmounts));
				}
			} else {
				vo.setOrderAmount(ECCalculateUtils.add(0d, sumOrderAmounts));
				vo.setBalanceAmount(ECCalculateUtils.add(0d, sumBalanceAmounts));
			}
			//订单付款额统计
			OrderReportStatisticalVo payOrder = orderService.getOrderPayAmountMonthData(time);
			if(payOrder != null){
				if(payOrder.getPayAmount() != null){
					payAmounts = ECCalculateUtils.add(payOrder.getPayAmount(), payAmounts);
					vo.setPayAmount(payAmounts);
				} else{
					vo.setPayAmount(ECCalculateUtils.add(0d, payAmounts));
				}
			} else{
				vo.setPayAmount(ECCalculateUtils.add(0d, payAmounts));
			}
			vo.setTime(time);
			listOrder.add(vo);

		}
		return listOrder;
	}

	/**
	 * 订单金额按日统计
	 * @throws ParseException
	 */
	@RequestMapping("getOrderAmountForDayStat")
	@ResponseBody
	public List<OrderAmountDayVo> getOrderAmountForDayStat(String createTimeStart, String createTimeEnd)throws ParseException {
		List<OrderAmountDayVo> listOrder = new ArrayList<OrderAmountDayVo>();
		if (null == createTimeStart || "".equals(createTimeStart)) {
			Date date = new Date();
			SysParam sysParam = sysParamService.getByParamKey(MemberConstant.day_parameter_key);
			int dayParmaeter = Integer.parseInt(sysParam.getParamValue());// 单位：天

			createTimeStart = getSpecifiedDayBefore(date, dayParmaeter);
			createTimeEnd = ECDateUtils.formatDate(date, "yyyy-MM-dd");
		}
		//默认获取近11天的订单金额统计
		List<String> days = ECDateUtils.collectLocalDates(createTimeStart, createTimeEnd);
		for (String time : days) {
			OrderAmountDayVo vo = new OrderAmountDayVo();
			
			//订单金额和余额抵扣额
			OrderAmountDayVo  order1 = orderService.getOrderAmountAndBalanceData(time);
			if(order1 != null){
				if(order1.getOrderAmount() != null){
					vo.setOrderAmount(order1.getOrderAmount());
				} else{
					vo.setOrderAmount(0d);
				}
				
				if(order1.getBalanceAmount() != null){
					vo.setBalanceAmount(order1.getBalanceAmount());
				} else{
					vo.setBalanceAmount(0d);
				}
			} else{
				vo.setOrderAmount(0d);
				vo.setBalanceAmount(0d);
			}
			//订单付款额
			OrderAmountDayVo order2 = orderService.getOrderPayAmountData(time);
			if(order2 != null){
				vo.setPayAmount(order2.getPayAmount());
			} else{
				vo.setPayAmount(0d);
			}
			vo.setTime(time);
			listOrder.add(vo);
		}
		return listOrder;
	}
	
	
	/**
	 * 按条件查找订单统计信息(天)
	 * 
	 * @throws ParseException
	 */
	@RequestMapping("getOrderEportFormDay")
	@ResponseBody
	public List<OrderReportFormVo> getOrderEportFormDay(String createTimeStart, String createTimeEnd)throws ParseException {
		List<OrderReportFormVo> ls = new ArrayList<OrderReportFormVo>();
		if (null == createTimeStart || "".equals(createTimeStart)) {
			Date date = new Date();
			SysParam sysParam = sysParamService.getByParamKey(MemberConstant.day_parameter_key);
			int dayParmaeter = Integer.parseInt(sysParam.getParamValue());// 单位：天

			createTimeStart = getSpecifiedDayBefore(date, dayParmaeter);
			createTimeEnd = ECDateUtils.formatDate(date, "yyyy-MM-dd");
		}
		// 获取所选的所有天
		List<String> ml = ECDateUtils.collectLocalDates(createTimeStart, createTimeEnd);
		for (String time : ml) {
			OrderReportFormVo vo = new OrderReportFormVo();
			Order o = new Order();
			// 新增订单数
			o.setEportTime(time);
			OrderReportFormVo v = orderService.orderNumberReport(new Query(o));
			if (v != null) {
				if (v.getOrderNumber() != null) {
					vo.setOrderNumber(v.getOrderNumber());
				} else {
					vo.setOrderNumber("0");
				}
			}
			Order o1 = new Order();
			// 完成订单数
			o1.setStartTime(time);
			OrderReportFormVo v1 = orderService.orderNumberReport(new Query(o1));
			if (v1 != null) {
				if (v1.getOrderNumber() != null) {
					vo.setOrderFhNumber(v1.getOrderNumber());
				} else {
					vo.setOrderFhNumber("0");
				}
			}

			// 取消订单数
			Order o2 = new Order();
			o2.setOrderStatus(4);
			o2.setEndTime(time);
			OrderReportFormVo v2 = orderService.orderNumberReport(new Query(o2));
			if (v2 != null) {
				if (v2.getOrderNumber() != null) {
					vo.setOrderDeNumber(v2.getOrderNumber());
				} else {
					vo.setOrderDeNumber("0");
				}
			}
			vo.setTime(time);
			vo.setTotal("0");
			ls.add(vo);
		}
		return ls;
	}

	/**
	 * 订单数按日累计
	 * @throws ParseException
	 */
	@RequestMapping("getOrderEportFormDayNs")
	@ResponseBody
	public List<OrderReportFormVo> getOrderEportFormDayNs(String createTimeStart, String createTimeEnd)throws ParseException {
		List<OrderReportFormVo> ls = new ArrayList<OrderReportFormVo>();
		if (null == createTimeStart || "".equals(createTimeStart)) {
			Date date = new Date();
			SysParam sysParam = sysParamService.getByParamKey(MemberConstant.day_parameter_key);
			int dayParmaeter = Integer.parseInt(sysParam.getParamValue());// 单位：天

			createTimeStart = getSpecifiedDayBefore(date, dayParmaeter);
			createTimeEnd = ECDateUtils.formatDate(date, "yyyy-MM-dd");
		}
		// 获取所选的所有天
		List<String> days = ECDateUtils.collectLocalDates(createTimeStart, createTimeEnd);
		//先统计X轴默认的第一天三个订单数 新增订单数、完成订单数和取消订单数
		String defaultDay = days.get(0);
		
		//新增订单数
		int orderSum = 0;
		Order o = new Order();
		o.setEportTime(defaultDay);
		OrderReportFormVo v = orderService.getOrderFirstDayData(new Query(o));
		if (v != null) {
			if (v.getOrderNumber() != null) {
				orderSum = Integer.parseInt(v.getOrderNumber());
			}
		}
		//完成订单数
		int finishSum = 0;
		Order o1 = new Order();
		o1.setStartTime(defaultDay);
		OrderReportFormVo v1 = orderService.getOrderFirstDayData(new Query(o1));
		if (v1 != null) {
			if (v1.getOrderNumber() != null) {
				finishSum = Integer.parseInt(v1.getOrderNumber());
			}
		}
		//取消订单数
		int calSum = 0;
		Order o2 = new Order();
		o2.setOrderStatus(4);
		o2.setEndTime(defaultDay);
		OrderReportFormVo v2 = orderService.getOrderFirstDayData(new Query(o2));
		if (v2 != null) {
			if (v2.getOrderNumber() != null) {
				calSum = Integer.parseInt(v2.getOrderNumber());
			} 
		}
		
		//循环所有系统参数配置的天数
		//当循环第一天时不做统计，直接continue
		int sums = 0;//订单变量
		int finishSums = 0;//完成订单变量
		int calSums = 0;//取消订单变量
		for (String time : days) {
			OrderReportFormVo vo = new OrderReportFormVo();
			if(time.equals(defaultDay)){
				vo.setOrderNumber(String.valueOf(orderSum));
				sums = orderSum;
				vo.setOrderFhNumber(String.valueOf(finishSum));
				finishSums = finishSum;
				vo.setOrderDeNumber(String.valueOf(calSum));
				calSums = calSum;
				continue;
			}
			
			//从第二天开始查询 新增订单数
			Order addOr = new Order();
			addOr.setEportTime(time);
			OrderReportFormVo vo1 = orderService.orderNumberReport(new Query(addOr));
			if (vo1 != null) {
				if (vo1.getOrderNumber() != null) {
					sums = Integer.parseInt(vo1.getOrderNumber()) + sums;
					vo.setOrderNumber(String.valueOf(sums));
				} else {
					vo.setOrderNumber("0");
				}
			}
			
			//完成订单数
			Order finOr = new Order();
			finOr.setStartTime(time);
			OrderReportFormVo vo2 = orderService.orderNumberReport(new Query(finOr));
			if(vo2 != null){
				if(vo2.getOrderNumber() != null){
					finishSums = Integer.parseInt(vo2.getOrderNumber()) + finishSums;
					vo.setOrderFhNumber(String.valueOf(finishSums));
				} else {
					vo.setOrderFhNumber("0");
				}
			}
			
			//取消订单数
			Order calOr = new Order();
			calOr.setOrderStatus(4);
			calOr.setEndTime(time);
			OrderReportFormVo vo3 = orderService.orderNumberReport(new Query(calOr));
			if (vo3 != null) {
				if (vo3.getOrderNumber() != null) {
					calSums = Integer.parseInt(vo3.getOrderNumber()) + calSums;
					vo.setOrderDeNumber(String.valueOf(calSums));
				} else {
					vo.setOrderDeNumber("0");
				}
			}
			
			vo.setTime(time);
			ls.add(vo);
		}
		return ls;
	}
	
	/**
	 * 订单金额按日累计
	 * @throws ParseException
	 */
	@RequestMapping("getOrderAmountDayCumulative")
	@ResponseBody
	public List<OrderAmountDayVo> getOrderAmountDayCumulative(String createTimeStart, String createTimeEnd)throws ParseException {
		List<OrderAmountDayVo> listOrder = new ArrayList<OrderAmountDayVo>();
		if (null == createTimeStart || "".equals(createTimeStart)) {
			Date date = new Date();
			SysParam sysParam = sysParamService.getByParamKey(MemberConstant.day_parameter_key);
			int dayParmaeter = Integer.parseInt(sysParam.getParamValue());// 单位：天

			createTimeStart = getSpecifiedDayBefore(date, dayParmaeter);
			createTimeEnd = ECDateUtils.formatDate(date, "yyyy-MM-dd");
		}
		//默认获取近10天的订单金额统计
		List<String> days = ECDateUtils.collectLocalDates(createTimeStart, createTimeEnd);
		//先统计X轴第一天的数值 包括订单金额、订单付款额和余额抵扣额
		String defaultDay = days.get(0);
		
		//订单金额
		Double sumOrderAmount = 0d;
		Double sumBalanceAmount = 0d;
		OrderAmountDayVo  order1 = orderService.getOrderAmountAndBalanceDataFirst(defaultDay);
		if(order1 != null){
			if(order1.getOrderAmount() != null){
				sumOrderAmount = order1.getOrderAmount();
			} else {
				sumOrderAmount = 0d;
			} 
			
			if(order1.getBalanceAmount() != null){
				sumBalanceAmount = order1.getBalanceAmount();
			} else {
				sumBalanceAmount = 0d;
			}
		} else{
			sumOrderAmount = 0d;
			sumBalanceAmount = 0d;
		}
		
		//订单付款额
		Double payAmount = 0d;
		OrderAmountDayVo order2 = orderService.getOrderPayAmountDataFirst(defaultDay);
		if(order2 != null){
			if(order2.getPayAmount() != null){
				payAmount = order2.getPayAmount();
			} else{
				payAmount = 0d;
			}
		} else{
			payAmount = 0d;
		} 
		
		//循环所有系统参数配置的天数
		//当循环第一天时不做统计，直接continue
		Double sumOrderAmounts = 0d;
		Double sumBalanceAmounts = 0d;
		Double payAmounts = 0d;
		for (String time : days) {
			OrderAmountDayVo vo = new OrderAmountDayVo();
			
			if(time.equals(defaultDay)){
				vo.setOrderAmount(sumOrderAmount);
				sumOrderAmounts = sumOrderAmount;
				vo.setBalanceAmount(sumBalanceAmount);
				sumBalanceAmounts = sumBalanceAmount;
				vo.setPayAmount(payAmount);
				payAmounts = payAmount;
				continue;
			}
			
			//第一天统计后 跳出循环 开始后面的天数统计
			//订单金额和余额抵扣额统计
			OrderAmountDayVo  order = orderService.getOrderAmountAndBalanceData(time);
			if(order != null){
				if(order.getOrderAmount() != null){
					sumOrderAmounts = ECCalculateUtils.add(order.getOrderAmount(), sumOrderAmounts);
					vo.setOrderAmount(sumOrderAmounts);
				} else {
					vo.setOrderAmount(ECCalculateUtils.add(0d, sumOrderAmounts));
				}
				
				if(order.getBalanceAmount() != null){
					sumBalanceAmounts = ECCalculateUtils.add(order.getBalanceAmount(), sumBalanceAmounts);
					vo.setBalanceAmount(sumBalanceAmounts);
				} else{
					vo.setBalanceAmount(ECCalculateUtils.add(0d, sumBalanceAmounts));
				}
			} else {
				vo.setOrderAmount(ECCalculateUtils.add(0d, sumOrderAmounts));
				vo.setBalanceAmount(ECCalculateUtils.add(0d, sumBalanceAmounts));
			}
			
			//订单付款额统计
			OrderAmountDayVo payOrder = orderService.getOrderPayAmountData(time);
			if(payOrder != null){
				if(payOrder.getPayAmount() != null){
					payAmounts = ECCalculateUtils.add(payOrder.getPayAmount(), payAmounts);
					vo.setPayAmount(payAmounts);
				} else{
					vo.setPayAmount(ECCalculateUtils.add(0d, payAmounts));
				}
			} else{
				vo.setPayAmount(ECCalculateUtils.add(0d, payAmounts));
			}
			vo.setTime(time);
			listOrder.add(vo);
		}
		return listOrder;
	}

	/**
	 * 订单销售量、订单销售额按周统计
	 * @throws ParseException
	 */
	@RequestMapping("orderEportWeek")
	@ResponseBody
	public List<OrderReportStatisticalVo> orderEportWeek(Date createTimeStartWeek, Date createTimeEndWeek)throws ParseException {
		List<OrderReportStatisticalVo> ls = new ArrayList<OrderReportStatisticalVo>();
		SysParam sysParam = sysParamService.getByParamKey(MemberConstant.week_parameter_key);
		int weekParmaeter = Integer.parseInt(sysParam.getParamValue());// 单位：周
		// 获取系统当前日期
		SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM-dd");

		if (createTimeEndWeek == null || "".equals(createTimeEndWeek)) {
			createTimeEndWeek = new Date();
			createTimeEndWeek = dft.parse(dft.format(createTimeEndWeek));
			createTimeStartWeek = ECDateUtils.getSpecifiedDay(createTimeEndWeek, weekParmaeter * 7);
		} else {
			long from = dft.parse(dft.format(createTimeStartWeek)).getTime();
			long to = dft.parse(dft.format(createTimeEndWeek)).getTime();
			weekParmaeter = (int) ((to - from) / (1000 * 3600 * 24 * 7)) + 1;
		}

		for (int i = 0; i < weekParmaeter; i++) {
			OrderReportStatisticalVo vo = new OrderReportStatisticalVo();
			// 开始时间
			String startTime = "";
			// 结束时间
			String endTime = "";

			if (i == 0) {
				startTime = getSpecifiedWeek(createTimeStartWeek, i);
				endTime = getSpecifiedWeek(createTimeStartWeek, i + 1);
			} else {
				startTime = getSpecifiedWk(getSpecifiedWeekNs(createTimeStartWeek, i), i);
				endTime = getSpecifiedWk(getSpecifiedWeekNs(createTimeStartWeek, i + 1), i);
			}
			
			//新增订单数
			Order aVo = new Order();
			aVo.setStartTime(startTime);
			aVo.setEndTime(endTime);
			OrderReportStatisticalVo v = orderService.orderNumberReportStatWeek(new Query(aVo));
			if (v != null) {
				if (v.getOrderNumber() != null) {
					vo.setOrderNumber(v.getOrderNumber());
				} else {
					vo.setOrderNumber("0");
				}
			}
			
			//完成订单
			Order fOrder = new Order();
			fOrder.setFisStartTime(startTime);
			fOrder.setFisEndTime(endTime);
			OrderReportStatisticalVo fVo2 = orderService.orderNumberReportStatWeek(new Query(fOrder));
			if (fVo2 != null) {
				if (fVo2.getOrderNumber() != null) {
					vo.setOrderFhNumber(fVo2.getOrderNumber());
				} else {
					vo.setOrderFhNumber("0");
				}
			}
			
			//取消订单
			Order cOrder = new Order();
			cOrder.setOrderStatus(4);
			cOrder.setCalStartTime(startTime);
			cOrder.setCalEndTime(endTime);
			OrderReportStatisticalVo cVo2 = orderService.orderNumberReportStatWeek(new Query(cOrder));
			if (cVo2 != null) {
				if (cVo2.getOrderDeNumber() != null) {
					vo.setOrderDeNumber(cVo2.getOrderDeNumber());
				} else {
					vo.setOrderDeNumber("0");
				}
			}
			vo.setTime(startTime + "/" + endTime);
			
			//订单金额和余额抵扣额
			Order pOrder = new Order();
			pOrder.setPayMentTimeS(startTime);
			pOrder.setPayMentTimeE(endTime);
			OrderReportStatisticalVo  order1 = orderService.getOrderAmountAndBalanceWeekData(new Query(pOrder));
			if(order1 != null){
				if(order1.getOrderAmount() != null){
					vo.setOrderAmount(order1.getOrderAmount());
				} else{
					vo.setOrderAmount(0d);
				}
				
				if(order1.getBalanceAmount() != null){
					vo.setBalanceAmount(order1.getBalanceAmount());
				} else{
					vo.setBalanceAmount(0d);
				}
			} else{
				vo.setOrderAmount(0d);
				vo.setBalanceAmount(0d);
			}
			//订单付款额
			Order pOrder1 = new Order();
			pOrder1.setPaidTimeS(startTime);
			pOrder1.setPaidTimeE(endTime);
			OrderReportStatisticalVo order2 = orderService.getOrderPayAmountWeekData(new Query(pOrder1));
			if(order2 != null){
				vo.setPayAmount(order2.getPayAmount());
			} else{
				vo.setPayAmount(0d);
			}
		
			ls.add(vo);
		}
		return ls;
	}

	/**
	 * 订单销售量、订单销售额按周累计图
	 */
	@RequestMapping("orderEportWeekNs")
	@ResponseBody
	public List<OrderReportStatisticalVo> orderEportWeekNs(Date createTimeStartWeek, Date createTimeEndWeek)throws ParseException {
		log.info("进入获取订单销售量、订单销售额按周统计方法... ...");
		List<OrderReportStatisticalVo> ls = new ArrayList<OrderReportStatisticalVo>();
		SysParam sysParam = sysParamService.getByParamKey(MemberConstant.week_parameter_key);
		int weekParmaeter = Integer.parseInt(sysParam.getParamValue());// 单位：周
		// 获取系统当前日期
		SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM-dd");
		if (createTimeEndWeek == null || "".equals(createTimeEndWeek)) {
			createTimeEndWeek = new Date();
			createTimeEndWeek = dft.parse(dft.format(createTimeEndWeek));
			createTimeStartWeek = ECDateUtils.getSpecifiedDay(createTimeEndWeek, weekParmaeter * 7);
		} else {
			long from = dft.parse(dft.format(createTimeStartWeek)).getTime();
			long to = dft.parse(dft.format(createTimeEndWeek)).getTime();
			weekParmaeter = (int) ((to - from) / (1000 * 3600 * 24 * 7)) + 1;
		}
		//先根据X周第一周的第一天获取当前时间之前的新增订单数、完成订单数、取消订单数、订单金额、订单付款额和余额抵扣额
		String firstDay = ECDateUtils.formatDate(createTimeStartWeek,"yyyy-MM-dd");
		//新增订单数
		int orderSum = 0;
		Order o3 = new Order();
		o3.setEportTime(firstDay);
		OrderReportFormVo addOrder = orderService.getOrderFirstWeekDayData(new Query(o3));
		if (addOrder != null) {
			if (addOrder.getOrderNumber() != null) {
				orderSum = Integer.parseInt(addOrder.getOrderNumber());
			}
		}
		//完成订单数
		int finishSum = 0;
		Order o1 = new Order();
		o1.setStartTime(firstDay);
		OrderReportFormVo finishOrder = orderService.getOrderFirstWeekDayData(new Query(o1));
		if (finishOrder != null) {
			if (finishOrder.getOrderNumber() != null) {
				finishSum = Integer.parseInt(finishOrder.getOrderNumber());
			}
		}
		//取消订单数
		int calSum = 0;
		Order o2 = new Order();
		o2.setOrderStatus(4);
		o2.setEndTime(firstDay);
		OrderReportFormVo cancelOrder = orderService.getOrderFirstWeekDayData(new Query(o2));
		if (cancelOrder != null) {
			if (cancelOrder.getOrderNumber() != null) {
				calSum = Integer.parseInt(cancelOrder.getOrderNumber());
			} 
		}
		//订单金额
		Double sumOrderAmount = 0d;
		Double sumBalanceAmount = 0d;
		OrderAmountDayVo  order1 = orderService.getOrderAmountAndBalanceWeekDataFirst(firstDay);
		if(order1 != null){
			if(order1.getOrderAmount() != null){
				sumOrderAmount = order1.getOrderAmount();
			} else {
				sumOrderAmount = 0d;
			} 
			
			if(order1.getBalanceAmount() != null){
				sumBalanceAmount = order1.getBalanceAmount();
			} else {
				sumBalanceAmount = 0d;
			}
		} else{
			sumOrderAmount = 0d;
			sumBalanceAmount = 0d;
		}
		//订单付款额
		Double payAmount = 0d;
		OrderAmountDayVo order2 = orderService.getOrderPayAmountWeekDataFirst(firstDay);
		if(order2 != null){
			if(order2.getPayAmount() != null){
				payAmount = order2.getPayAmount();
			} else{
				payAmount = 0d;
			}
		} else{
			payAmount = 0d;
		} 
		//循环所有系统参数配置的天数
		//当循环第一周时统计后，直接continue
		int sums = 0;//订单变量
		int finishSums = 0;//完成订单变量
		int calSums = 0;//取消订单变量
		Double sumOrderAmounts = 0d;
		Double sumBalanceAmounts = 0d;
		Double payAmounts = 0d;
		for (int i = 0; i < weekParmaeter; i++) {
			OrderReportStatisticalVo vo = new OrderReportStatisticalVo();
			String startTime = "";// 开始时间
			String endTime = "";// 结束时间

			if (i == 0) {
				startTime = getSpecifiedWeek(createTimeStartWeek, i);
				endTime = getSpecifiedWeek(createTimeStartWeek, i + 1);
			} else {
				startTime = getSpecifiedWk(getSpecifiedWeekNs(createTimeStartWeek, i), i);
				endTime = getSpecifiedWk(getSpecifiedWeekNs(createTimeStartWeek, i + 1), i);
			}
			vo.setTime(startTime + "/" + endTime);
			//如果第一天等于该X周第一天 先统计第一周的数据 统计完后直接跳出该循环 开始执行后续周数的统计
			if(startTime.equals(firstDay)){
				//新增订单数
				Order aVo = new Order();
				aVo.setStartTime(startTime);
				aVo.setEndTime(endTime);
				OrderReportStatisticalVo v = orderService.orderNumberReportStatWeek(new Query(aVo));
				if (v != null) {
					if (v.getOrderNumber() != null) {
						sums = Integer.parseInt(v.getOrderNumber()) + orderSum;
						vo.setOrderNumber(String.valueOf(sums));
					} else {
						sums = orderSum + 0;
						vo.setOrderNumber(String.valueOf(orderSum + 0));
					}
				} else {
					sums = orderSum + 0;
					vo.setOrderNumber(String.valueOf(orderSum + 0));
				}
				//完成订单
				Order fOrder = new Order();
				fOrder.setFisStartTime(startTime);
				fOrder.setFisEndTime(endTime);
				OrderReportStatisticalVo fVo2 = orderService.orderNumberReportStatWeek(new Query(fOrder));
				if (fVo2 != null) {
					if (fVo2.getOrderNumber() != null) {
						finishSums = Integer.parseInt(fVo2.getOrderNumber()) + finishSum;
						vo.setOrderFhNumber(String.valueOf(finishSums));
					} else {
						finishSums = finishSum + 0;
						vo.setOrderFhNumber(String.valueOf(finishSum + 0));
					}
				} else {
					finishSums = finishSum + 0;
					vo.setOrderFhNumber(String.valueOf(finishSum + 0));
				}
				//取消订单数
				Order cOrder = new Order();
				cOrder.setOrderStatus(4);
				cOrder.setCalStartTime(startTime);
				cOrder.setCalEndTime(endTime);
				OrderReportStatisticalVo cVo2 = orderService.orderNumberReportStatWeek(new Query(cOrder));
				if (cVo2 != null) {
					if (cVo2.getOrderNumber() != null) {
						calSums = Integer.parseInt(cVo2.getOrderNumber()) + calSum;
						vo.setOrderDeNumber(String.valueOf(calSums));
					} else {
						calSums = calSum + 0;
						vo.setOrderDeNumber(String.valueOf(calSum + 0));
					}
				} else {
					calSums = calSum + 0;
					vo.setOrderDeNumber(String.valueOf(calSum + 0));
				}
				//订单金额和余额抵扣额
				Order pOrder = new Order();
				pOrder.setPayMentTimeS(startTime);
				pOrder.setPayMentTimeE(endTime);
				OrderReportStatisticalVo  porder1 = orderService.getOrderAmountAndBalanceWeekData(new Query(pOrder));
				if(porder1 != null){
					if(porder1.getOrderAmount() != null){
						sumOrderAmounts = ECCalculateUtils.add(porder1.getOrderAmount(), sumOrderAmount);
						vo.setOrderAmount(sumOrderAmounts);
					} else{
						sumOrderAmounts = ECCalculateUtils.add(0d, sumOrderAmount);
						vo.setOrderAmount(ECCalculateUtils.add(0d, sumOrderAmount));
					}
					if(porder1.getBalanceAmount() != null){
						sumBalanceAmounts = ECCalculateUtils.add(porder1.getBalanceAmount(), sumBalanceAmount);
						vo.setBalanceAmount(sumBalanceAmounts);
					} else{
						sumBalanceAmounts = ECCalculateUtils.add(0d, sumBalanceAmount);
						vo.setBalanceAmount(ECCalculateUtils.add(0d, sumBalanceAmount));
					}
				} else{
					sumOrderAmounts = ECCalculateUtils.add(0d, sumOrderAmount);
					sumBalanceAmounts = ECCalculateUtils.add(0d, sumBalanceAmount);
					vo.setOrderAmount(ECCalculateUtils.add(0d, sumOrderAmount));
					vo.setBalanceAmount(ECCalculateUtils.add(0d, sumBalanceAmount));
				}
				//订单付款额
				Order pOrder1 = new Order();
				pOrder1.setPaidTimeS(startTime);
				pOrder1.setPaidTimeE(endTime);
				OrderReportStatisticalVo porder2 = orderService.getOrderPayAmountWeekData(new Query(pOrder1));
				if(porder2 != null){
					if(porder2.getPayAmount() != null){
						payAmounts = ECCalculateUtils.add(porder2.getPayAmount(), payAmount);
						vo.setPayAmount(payAmounts);
					} else {
						payAmounts = ECCalculateUtils.add(0d, payAmount);
						vo.setPayAmount(ECCalculateUtils.add(0d, payAmount));
					}
				} else{
					payAmounts = ECCalculateUtils.add(0d, payAmount);
					vo.setPayAmount(ECCalculateUtils.add(0d, payAmount));
				}
				continue;
			}
			//新增订单数
			Order aVo = new Order();
			aVo.setStartTime(startTime);
			aVo.setEndTime(endTime);
			OrderReportStatisticalVo v = orderService.orderNumberReportStatWeek(new Query(aVo));
			if (v != null) {
				if (v.getOrderNumber() != null) {
					sums = Integer.parseInt(v.getOrderNumber()) + sums;
					vo.setOrderNumber(String.valueOf(sums));
				} else {
					sums = sums + 0;
					vo.setOrderNumber(String.valueOf(sums + 0));
				}
			} else {
				sums = sums + 0;
				vo.setOrderNumber(String.valueOf(sums + 0));
			}
			//完成订单
			Order fOrder = new Order();
			fOrder.setFisStartTime(startTime);
			fOrder.setFisEndTime(endTime);
			OrderReportStatisticalVo fVo2 = orderService.orderNumberReportStatWeek(new Query(fOrder));
			if (fVo2 != null) {
				if (fVo2.getOrderNumber() != null) {
					finishSums = Integer.parseInt(fVo2.getOrderNumber()) + finishSums;
					vo.setOrderFhNumber(String.valueOf(finishSums));
				} else {
					finishSums = finishSums + 0;
					vo.setOrderFhNumber(String.valueOf(finishSums + 0));
				}
			} else {
				finishSums = finishSums + 0;
				vo.setOrderFhNumber(String.valueOf(finishSums + 0));
			}
			//取消订单数
			Order cOrder = new Order();
			cOrder.setOrderStatus(4);
			cOrder.setCalStartTime(startTime);
			cOrder.setCalEndTime(endTime);
			OrderReportStatisticalVo cVo2 = orderService.orderNumberReportStatWeek(new Query(cOrder));
			if (cVo2 != null) {
				if (cVo2.getOrderNumber() != null) {
					calSums = Integer.parseInt(cVo2.getOrderNumber()) + calSums;
					vo.setOrderDeNumber(String.valueOf(calSums));
				} else {
					calSums = calSums + 0;
					vo.setOrderDeNumber(String.valueOf(calSums + 0));
				}
			} else {
				calSums = calSums + 0;
				vo.setOrderDeNumber(String.valueOf(calSums + 0));
			}
			//订单金额和余额抵扣额
			Order pOrder = new Order();
			pOrder.setPayMentTimeS(startTime);
			pOrder.setPayMentTimeE(endTime);
			OrderReportStatisticalVo  porder1 = orderService.getOrderAmountAndBalanceWeekData(new Query(pOrder));
			if(porder1 != null){
				if(porder1.getOrderAmount() != null){
					sumOrderAmounts = ECCalculateUtils.add(porder1.getOrderAmount(), sumOrderAmounts);
					vo.setOrderAmount(sumOrderAmounts);
				} else{
					sumOrderAmounts = ECCalculateUtils.add(0d, sumOrderAmounts);
					vo.setOrderAmount(ECCalculateUtils.add(0d, sumOrderAmounts));
				}
				if(porder1.getBalanceAmount() != null){
					sumBalanceAmounts = ECCalculateUtils.add(porder1.getBalanceAmount(), sumBalanceAmounts);
					vo.setBalanceAmount(sumBalanceAmounts);
				} else{
					sumBalanceAmounts = ECCalculateUtils.add(0d, sumBalanceAmounts);
					vo.setBalanceAmount(ECCalculateUtils.add(0d, sumBalanceAmounts));
				}
			} else{
				sumOrderAmounts = ECCalculateUtils.add(0d, sumOrderAmounts);
				sumBalanceAmounts = ECCalculateUtils.add(0d, sumBalanceAmounts);
				vo.setOrderAmount(ECCalculateUtils.add(0d, sumOrderAmounts));
				vo.setBalanceAmount(ECCalculateUtils.add(0d, sumBalanceAmounts));
			}
			//订单付款额
			Order pOrder1 = new Order();
			pOrder1.setPaidTimeS(startTime);
			pOrder1.setPaidTimeE(endTime);
			OrderReportStatisticalVo porder2 = orderService.getOrderPayAmountWeekData(new Query(pOrder1));
			if(porder2 != null){
				if(porder2.getPayAmount() != null){
					payAmounts = ECCalculateUtils.add(porder2.getPayAmount(), payAmounts);
					vo.setPayAmount(payAmounts);
				} else {
					payAmounts = ECCalculateUtils.add(0d, payAmounts);
					vo.setPayAmount(ECCalculateUtils.add(0d, payAmounts));
				}
			} else{
				payAmounts = ECCalculateUtils.add(0d, payAmounts);
				vo.setPayAmount(ECCalculateUtils.add(0d, payAmounts));
			}
			
			ls.add(vo);
		}
		return ls;
	}

	/**
	 * 查找套餐销售统计信息
	 */
	@RequestMapping("findSalePackageEportForm")
	@ResponseBody
	public List<PricingPackage> findSalePackageEportForm(
			@ModelAttribute("pricingPackage") PricingPackage pricingPackage) {
		// 查找类型为销售的套餐
		pricingPackage.setPackageType(2);
		pricingPackage.setCencorStatus(1);
		pricingPackage.setIsAvailable(1);
		List<PricingPackage> ls = pricingPackageService.getPricingPackageList(new Query(pricingPackage));
		if (ls.size() > 0) {
			// 在套餐订单表查询套餐有没有销售
			for (PricingPackage p : ls) {
				PricingPackOrder pr = new PricingPackOrder();
				pr.setPackageId(p.getPackageNo());
				List<PricingPackOrder> ps = pricingPackOrderService.getPricingPackOrderList(new Query(pr));

				if (ps.size() > 0) {
					// 销售额总计
					Double sum = pricingPackOrderService.getPackageSum(p.getPackageNo());
					p.setSum(sum);
					// 销售套餐数量
					Long cn = pricingPackOrderService.getPackageCount(p.getPackageNo());
					p.setCount(cn);

				} else {
					p.setSum(0D);
					p.setCount(0L);
				}

			}

		}

		return ls;
	}

	/**
	 * 查看2个时间之间有多少个月
	 * 
	 */
	@RequestMapping("getMonth")
	@ResponseBody
	public ResultInfo<String> getMonth(Date createTimeStartMonth, Date createTimeEndMonth) {
		ResultInfo<String> resultInfo = new ResultInfo<String>();
		// Date s = ECDateUtils.parseDate(createTimeStartMonth+"00", "yyyy-MM");
		// Date e = ECDateUtils.parseDate(createTimeEndMonth, "yyyy-MM");
		int m = ECDateUtils.getMonth(createTimeStartMonth, createTimeEndMonth);
		if (m > 11) {
			resultInfo.setCode(Constant.NO + "");
		} else {
			resultInfo.setCode(Constant.YES + "");
		}

		return resultInfo;
	}

	/**
	 * 查看2个时间之间有多少周
	 * 
	 */
	@RequestMapping("getWeek")
	@ResponseBody
	public ResultInfo<String> getWeek(Date createTimeStartWeek, Date createTimeEndWeek) {
		ResultInfo<String> resultInfo = new ResultInfo<String>();
		SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM-dd");

		try {
			long st = dft.parse(dft.format(createTimeStartWeek)).getTime();
			long ed = dft.parse(dft.format(createTimeEndWeek)).getTime();
			int m = (int) ((ed - st) / (1000 * 3600 * 24 * 7)) + 1;
			if (m > 10) {
				resultInfo.setCode(Constant.NO + "");
			} else {
				resultInfo.setCode(Constant.YES + "");
				resultInfo.setData(m + "");
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return resultInfo;
	}

	/**
	 * 查看2个时间之间有多少天
	 * 
	 */
	@RequestMapping("getDay")
	@ResponseBody
	public ResultInfo<String> getDay(Date createTimeStartDay, Date createTimeEndDay) {
		ResultInfo<String> resultInfo = new ResultInfo<String>();
		// Date s = ECDateUtils.parseDate(createTimeStartMonth+"00", "yyyy-MM");
		// Date e = ECDateUtils.parseDate(createTimeEndMonth, "yyyy-MM");
		Long m = ECDateUtils.differDays(createTimeStartDay, createTimeEndDay);
		if (m > 30) {
			resultInfo.setCode(Constant.NO + "");
		} else {
			resultInfo.setCode(Constant.YES + "");
		}

		return resultInfo;
	}

	/**
	 * 充值套餐按周统计销售量、销售额和充值额
	 * @throws ParseException
	 */
	@RequestMapping("salePackageEportWeek")
	@ResponseBody
	public List<PricingPackageStat> salePackageEportWeek(Date createTimeStartWeek, Date createTimeEndWeek)throws ParseException {
		log.info("进入充值套餐按周统计销售量、销售额和充值方法...");
		List<PricingPackageStat> listPackage = new ArrayList<PricingPackageStat>();
		SysParam sysParam = sysParamService.getByParamKey(MemberConstant.week_parameter_key);
		int weekParmaeter = Integer.parseInt(sysParam.getParamValue());// 单位：周
		// 获取系统当前日期
		SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM-dd");
		if (createTimeEndWeek == null || "".equals(createTimeEndWeek)) {
			createTimeEndWeek = new Date();
			createTimeEndWeek = dft.parse(dft.format(createTimeEndWeek));
			createTimeStartWeek = ECDateUtils.getSpecifiedDay(createTimeEndWeek, weekParmaeter * 7);
		} else {
			long from = dft.parse(dft.format(createTimeStartWeek)).getTime();
			long to = dft.parse(dft.format(createTimeEndWeek)).getTime();
			weekParmaeter = (int) ((to - from) / (1000 * 3600 * 24 * 7)) + 1;
		}

		for (int i = 0; i < weekParmaeter; i++) {
			// 开始时间
			String startTime = "";
			// 结束时间
			String endTime = "";
			if (i == 0) {
				startTime = getSpecifiedWeek(createTimeStartWeek, i);
				endTime = getSpecifiedWeek(createTimeStartWeek, i + 1);
			} else {
				startTime = getSpecifiedWk(getSpecifiedWeekNs(createTimeStartWeek, i), i);
				endTime = getSpecifiedWk(getSpecifiedWeekNs(createTimeStartWeek, i + 1), i);
			}
			PricingPackageStat vo = new PricingPackageStat();
			PricingPackageStat pps =pricingPackageService.getPricingPackageWeekData(startTime,endTime);
			if(pps != null){
				if(pps.getSalesNumber() != null){
					vo.setSalesNumber(pps.getSalesNumber());
				} else {
					vo.setSalesNumber(0);
				}
				if(pps.getSalesAmount() != null){
					vo.setSalesAmount(pps.getSalesAmount());
				} else {
					vo.setSalesAmount(0d);
				}
				if(pps.getTopUpAmount() != null){
					vo.setTopUpAmount(pps.getTopUpAmount());
				} else {
					vo.setTopUpAmount(0d);
				}
			} else {
				vo.setSalesNumber(0);
				vo.setSalesAmount(0d);
				vo.setTopUpAmount(0d);
			}
			vo.setTime(startTime + "/" + endTime);
			listPackage.add(vo);
		}
		return listPackage;
	}

	/**
	 * 充值套餐按周累计销售量、销售额和充值额
	 * @throws ParseException
	 */
	@RequestMapping("salePackageEportWeekNs")
	@ResponseBody
	public List<PricingPackageStat> salePackageEportWeekNs(Date createTimeStartWeek, Date createTimeEndWeek)throws ParseException {
		log.info("进入值套餐按周累计销售量、销售额和充值额方法...");
		List<PricingPackageStat> listPackage = new ArrayList<PricingPackageStat>();
		SysParam sysParam = sysParamService.getByParamKey(MemberConstant.week_parameter_key);
		int weekParmaeter = Integer.parseInt(sysParam.getParamValue());// 单位：周
		// 获取系统当前日期
		SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM-dd");

		if (createTimeEndWeek == null || "".equals(createTimeEndWeek)) {
			createTimeEndWeek = new Date();
			createTimeEndWeek = dft.parse(dft.format(createTimeEndWeek));
			createTimeStartWeek = ECDateUtils.getSpecifiedDay(createTimeEndWeek, weekParmaeter * 7);
		} else {
			long from = dft.parse(dft.format(createTimeStartWeek)).getTime();
			long to = dft.parse(dft.format(createTimeEndWeek)).getTime();
			weekParmaeter = (int) ((to - from) / (1000 * 3600 * 24 * 7)) + 1;
		}
		//先根据X轴第一周的第一天获取当前时间之前的销售量、销售额和充值额
		String firstDay = ECDateUtils.formatDate(createTimeStartWeek,"yyyy-MM-dd");
		int salesCount = 0;//销售量初始化
		Double salesAmount = 0d;//销售额初始化
		Double topUpAmount = 0d;//充值额初始化
		PricingPackageStat pps = pricingPackageService.getWeekFirstDayPricingPackageData(firstDay);
		if(pps != null){
			if(pps.getSalesNumber() != null){
				salesCount = pps.getSalesNumber();
			}
			if(pps.getSalesAmount() != null){
				salesAmount = pps.getSalesAmount();
			}
			if(pps.getTopUpAmount() != null){
				topUpAmount = pps.getTopUpAmount();
			}
		} 
		//循环所有系统参数配置的周
		//当循环第一周时统计后，直接continue
		int salesCounts = 0;
		Double salesAmounts = 0d;
		Double topUpAmounts = 0d;
		for (int i = 0; i < weekParmaeter; i++) {
			PricingPackageStat vo = new PricingPackageStat();
			String startTime = "";// 开始时间
			String endTime = "";	// 结束时间
			if (i == 0) {
				startTime = getSpecifiedWeek(createTimeStartWeek, i);
				endTime = getSpecifiedWeek(createTimeStartWeek, i + 1);
			} else {
				startTime = getSpecifiedWk(getSpecifiedWeekNs(createTimeStartWeek, i), i);
				endTime = getSpecifiedWk(getSpecifiedWeekNs(createTimeStartWeek, i + 1), i);
			}
			vo.setTime(startTime + "/" + endTime);
			//如果第一天等于该X周第一天 先统计第一周的数据 统计完后直接跳出该循环 开始执行后续周数的统计
			if(startTime.equals(firstDay)){
				PricingPackageStat ppsWeek = pricingPackageService.getPricingPackageWeekData(startTime, endTime);
				if(ppsWeek != null){
					if(ppsWeek.getSalesNumber() != null){
						salesCounts = salesCount + ppsWeek.getSalesNumber();
						vo.setSalesNumber(salesCounts);
					} else {
						vo.setSalesNumber(salesCount + 0);
					}
					
					if(ppsWeek.getSalesAmount() != null){
						salesAmounts = ECCalculateUtils.add(salesAmount, ppsWeek.getSalesAmount());
						vo.setSalesAmount(salesAmounts);
					} else {
						salesAmounts = ECCalculateUtils.add(salesAmount, 0d);
						vo.setSalesAmount(ECCalculateUtils.add(salesAmount, 0d));
					}
					
					if(ppsWeek.getTopUpAmount() != null){
						topUpAmounts = ECCalculateUtils.add(topUpAmount, ppsWeek.getTopUpAmount());
						vo.setTopUpAmount(topUpAmounts);
					} else {
						topUpAmounts = ECCalculateUtils.add(topUpAmount, 0d);
						vo.setTopUpAmount(ECCalculateUtils.add(topUpAmount, 0d));
					}
				} else{
					vo.setSalesNumber(salesCount + 0);
					vo.setSalesAmount(ECCalculateUtils.add(salesAmount, 0d));
					vo.setTopUpAmount(ECCalculateUtils.add(topUpAmount, 0d));
				}
				continue;
			}
			PricingPackageStat ppsWeek = pricingPackageService.getPricingPackageWeekData(startTime, endTime);
			if(ppsWeek != null){
				if(ppsWeek.getSalesNumber() != null){
					salesCounts = salesCounts + ppsWeek.getSalesNumber();
					vo.setSalesNumber(salesCounts);
				} else {
					vo.setSalesNumber(salesCounts + 0);
				}
				
				if(ppsWeek.getSalesAmount() != null){
					salesAmounts = ECCalculateUtils.add(salesAmounts, ppsWeek.getSalesAmount());
					vo.setSalesAmount(salesAmounts);
				} else {
					salesAmounts = ECCalculateUtils.add(salesAmounts, 0d);
					vo.setSalesAmount(ECCalculateUtils.add(salesAmounts, 0d));
				}
				
				if(ppsWeek.getTopUpAmount() != null){
					topUpAmounts = ECCalculateUtils.add(topUpAmounts, ppsWeek.getTopUpAmount());
					vo.setTopUpAmount(topUpAmounts);
				} else {
					topUpAmounts = ECCalculateUtils.add(topUpAmounts, 0d);
					vo.setTopUpAmount(ECCalculateUtils.add(topUpAmounts, 0d));
				}
			} else{
				vo.setSalesNumber(salesCounts + 0);
				vo.setSalesAmount(ECCalculateUtils.add(salesAmounts, 0d));
				vo.setTopUpAmount(ECCalculateUtils.add(topUpAmounts, 0d));
			}
			
			listPackage.add(vo);
		}
		return listPackage;
	}

	/**
	 * 充值套餐销售数按日统计
	 * 
	 */
	@RequestMapping("salePackageEportDay")
	@ResponseBody
	public List<PricingPackageStat> salePackageEportDay(String createTimeStartDay, String createTimeEndDay) {
		log.info("进入充值套餐销售数、销售额和充值额按日统计方法... ...");
		List<PricingPackageStat> listPackage = new ArrayList<PricingPackageStat>();
		if (null == createTimeEndDay || "".equals(createTimeEndDay)) {
			Date date = new Date();
			SysParam sysParam = sysParamService.getByParamKey(MemberConstant.day_parameter_key);
			int dayParmaeter = Integer.parseInt(sysParam.getParamValue());// 单位：天

			createTimeStartDay = getSpecifiedDayBefore(date, dayParmaeter);
			createTimeEndDay = ECDateUtils.formatDate(date, "yyyy-MM-dd");
		}

		// 获取所选的所有天
		List<String> ml = ECDateUtils.collectLocalDates(createTimeStartDay, createTimeEndDay);
		for (String time : ml) {
			PricingPackageStat ppsVo = new PricingPackageStat();
			
			PricingPackageStat pps = pricingPackageService.getPricingPackageDataDay(time);
			if(pps != null){
				ppsVo.setSalesNumber(pps.getSalesNumber());
				if(pps.getSalesAmount() != null){
					ppsVo.setSalesAmount(pps.getSalesAmount());
				} else {
					ppsVo.setSalesAmount(0d);
				}
				if(pps.getTopUpAmount() != null){
					ppsVo.setTopUpAmount(pps.getTopUpAmount());
				} else {
					ppsVo.setTopUpAmount(0d);
				}
			} else {
				ppsVo.setSalesNumber(0);
				ppsVo.setSalesAmount(0d);
				ppsVo.setTopUpAmount(0d);
			}
			ppsVo.setTime(time);
			listPackage.add(ppsVo);
		}
		return listPackage;
	}

	/**
	 * 充值套餐销售量、销售额和充值额按日累计
	 */
	@RequestMapping("salePackageEportDayNs")
	@ResponseBody
	public List<PricingPackageStat> salePackageEportDayNs(String createTimeStartDay, String createTimeEndDay) {
		log.info("进入充值套餐销售量、销售额和充值额按日累计方法... ...");
		List<PricingPackageStat> listPackage = new ArrayList<PricingPackageStat>();
		if (null == createTimeEndDay || "".equals(createTimeEndDay)) {
			Date date = new Date();
			SysParam sysParam = sysParamService.getByParamKey(MemberConstant.day_parameter_key);
			int dayParmaeter = Integer.parseInt(sysParam.getParamValue());// 单位：天
			createTimeStartDay = getSpecifiedDayBefore(date, dayParmaeter);
			createTimeEndDay = ECDateUtils.formatDate(date, "yyyy-MM-dd");
		}

		// 获取所选的所有天
		List<String> ml = ECDateUtils.collectLocalDates(createTimeStartDay, createTimeEndDay);
		//先统计第一天之前包括第一天的销售量、销售额和充值额数据
		String firstDay = ml.get(0);
		//销售量
		int salesSum = 0;
		//销售额
		Double salesAmount = 0d;
		//充值额
		Double topUpAmount = 0d;
		PricingPackageStat pps = pricingPackageService.getFirstDayPricingPackageData(firstDay);
		if (pps != null) {
			if(pps.getSalesNumber() != null){
				salesSum = pps.getSalesNumber();
			}
			if(pps.getSalesAmount() != null){
				salesAmount = pps.getSalesAmount();
			}
			if(pps.getTopUpAmount() != null){
				topUpAmount = pps.getTopUpAmount();
			}
		}
		
		//循环所有系统参数配置的天数
		//当循环第一天时统计后，直接continue
		int salesSums = 0;
		Double salesAmounts = 0d;
		Double topUpAmounts = 0d;
		for (String time : ml) {
			PricingPackageStat vo = new PricingPackageStat();
			if(time.equals(firstDay)){
				vo.setSalesNumber(salesSum);
				salesSums = salesSum;
				vo.setSalesAmount(salesAmount);
				salesAmounts = salesAmount;
				vo.setTopUpAmount(topUpAmount);
				topUpAmounts = topUpAmount;
				continue;
			}
			PricingPackageStat queryVo = pricingPackageService.getPricingPackageDataDay(time);
			if(queryVo != null){
				if(queryVo.getSalesNumber() != null){
					salesSums = queryVo.getSalesNumber() + salesSums;
					vo.setSalesNumber(salesSums);
				} else {
					vo.setSalesNumber(salesSum + 0);
				}
				
				if(queryVo.getSalesAmount() != null){
					salesAmounts = ECCalculateUtils.add(queryVo.getSalesAmount(), salesAmounts);
					vo.setSalesAmount(salesAmounts);
				} else {
					vo.setSalesAmount(ECCalculateUtils.add(0d, salesAmounts));
				}
				
				if(queryVo.getTopUpAmount() != null){
					topUpAmounts = ECCalculateUtils.add(queryVo.getTopUpAmount(), topUpAmounts);
					vo.setTopUpAmount(topUpAmounts);
				} else{
					vo.setTopUpAmount(ECCalculateUtils.add(0d, topUpAmounts));
				}
			} else{
				vo.setSalesNumber(salesSum + 0);
				vo.setSalesAmount(ECCalculateUtils.add(0d, salesAmounts));
				vo.setTopUpAmount(ECCalculateUtils.add(0d, topUpAmounts));
			}
			
			vo.setTime(time);
			listPackage.add(vo);
		}
		return listPackage;
	}

	/**
	 * 充值套餐按月累计销售量、销售额和充值额
	 * @throws ParseException 
	 * 
	 */
	@RequestMapping("salePackageEportMonth")
	@ResponseBody
	public List<PricingPackageStat> salePackageEportMonth(String createTimeStartMonth, String createTimeEndMonth) throws ParseException {
		log.info("进入充值套餐按月累计销售量、销售额和充值额方法...");
		List<PricingPackageStat> listPackage = new ArrayList<PricingPackageStat>();
		if (null == createTimeEndMonth || "".equals(createTimeEndMonth)) {
			Date date = new Date();
			SysParam sysParam = sysParamService.getByParamKey(MemberConstant.month_parameter_key);
			int monthParmaeter = Integer.parseInt(sysParam.getParamValue());// 单位：月

			createTimeStartMonth = ECDateUtils.getSpecifiedmonth(date, monthParmaeter);
			createTimeEndMonth = ECDateUtils.formatDate(date, "yyyy-MM");
		}

		// 获取所选的所有月份
		List<String> ml = ECDateUtils.getMonthBetween(createTimeStartMonth, createTimeEndMonth);
		String firstMonth = ml.get(0);
		int salesCount = 0;//销售量
		Double salesAmount = 0d;//销售额
		Double topUpAmount = 0d;//充值额
		
		PricingPackageStat pps = pricingPackageService.getMonthFirstPricingPackageData(firstMonth);
		if(pps != null){
			if(pps.getSalesNumber() != null){
				salesCount = pps.getSalesNumber();
			}
			if(pps.getSalesAmount() != null){
				salesAmount = pps.getSalesAmount();
			}
			if(pps.getTopUpAmount() != null){
				topUpAmount = pps.getTopUpAmount();
			}
		} 
		
		//循环所有系统参数配置的月
		//当循环第一月统计后，直接continue
		int salesCounts = 0;
		Double salesAmounts = 0d;
		Double topUpAmounts = 0d;
		for (String time : ml) {
			PricingPackageStat	vo = new PricingPackageStat();
			if(time.equals(firstMonth)){
				vo.setSalesNumber(salesCount);
				salesCounts = salesCount;
				vo.setSalesAmount(salesAmount);
				salesAmounts = salesAmount;
				vo.setTopUpAmount(topUpAmount);
				topUpAmounts = topUpAmount;
				continue;
			}
			
			PricingPackageStat ppsMonth = pricingPackageService.getPricingPackageDataMonth(time);
			if(ppsMonth != null){
				if(ppsMonth.getSalesNumber() != null){
					salesCounts = salesCounts + ppsMonth.getSalesNumber();
					vo.setSalesNumber(salesCounts);
				} else {
					salesCounts = salesCounts + 0;
					vo.setSalesNumber(salesCounts + 0);
				}
				
				if(ppsMonth.getSalesAmount() != null){
					salesAmounts = ECCalculateUtils.add(salesAmounts, ppsMonth.getSalesAmount());
					vo.setSalesAmount(salesAmounts);
				} else {
					salesAmounts = ECCalculateUtils.add(salesAmounts, 0d);
					vo.setSalesAmount(ECCalculateUtils.add(salesAmounts, 0d));
				}
				
				if(ppsMonth.getTopUpAmount() != null){
					topUpAmounts = ECCalculateUtils.add(topUpAmounts, ppsMonth.getTopUpAmount());
					vo.setTopUpAmount(topUpAmounts);
				} else {
					topUpAmounts = ECCalculateUtils.add(topUpAmounts, 0d);
					vo.setTopUpAmount(ECCalculateUtils.add(topUpAmounts, 0d));
				}
			} else{
				vo.setSalesNumber(salesCounts + 0);
				vo.setSalesAmount(ECCalculateUtils.add(salesAmounts, 0d));
				vo.setTopUpAmount(ECCalculateUtils.add(topUpAmounts, 0d));
			}
			vo.setTime(time);
			listPackage.add(vo);
		}	
				
		return listPackage;
	}

	/**
	 * 充值套餐按月统计销售量、销售额和充值额
	 * @throws ParseException 
	 * 
	 */
	@RequestMapping("salePackageEportMonthNs")
	@ResponseBody
	public List<PricingPackageStat> salePackageEportMonthNs(String createTimeStartMonth, String createTimeEndMonth) throws ParseException {
		log.info("进入充值套餐按月统计销售量、销售额和充值额方法...");
		List<PricingPackageStat> listPackage = new ArrayList<PricingPackageStat>();
		if (null == createTimeEndMonth || "".equals(createTimeEndMonth)) {
			Date date = new Date();
			SysParam sysParam = sysParamService.getByParamKey(MemberConstant.month_parameter_key);
			int monthParmaeter = Integer.parseInt(sysParam.getParamValue());// 单位：月

			createTimeStartMonth = ECDateUtils.getSpecifiedmonth(date, monthParmaeter);
			createTimeEndMonth = ECDateUtils.formatDate(date, "yyyy-MM");
		}

		// 获取所选的所有月份
		List<String> ml = ECDateUtils.getMonthBetween(createTimeStartMonth, createTimeEndMonth);
		for (String time : ml) {
			PricingPackageStat ppsVo = new PricingPackageStat();
			PricingPackageStat pps = pricingPackageService.getPricingPackageDataMonth(time);
			if(pps != null){
				ppsVo.setSalesNumber(pps.getSalesNumber());
				if(pps.getSalesAmount() != null){
					ppsVo.setSalesAmount(pps.getSalesAmount());
				} else {
					ppsVo.setSalesAmount(0d);
				}
				if(pps.getTopUpAmount() != null){
					ppsVo.setTopUpAmount(pps.getTopUpAmount());
				} else {
					ppsVo.setTopUpAmount(0d);
				}
			} else {
				ppsVo.setSalesNumber(0);
				ppsVo.setSalesAmount(0d);
				ppsVo.setTopUpAmount(0d);
			}
			ppsVo.setTime(time);
			listPackage.add(ppsVo);
			
		}
		return listPackage;
	}

	/**
	 * 查找套餐抵扣统计信息（全部）
	 * 
	 */
	@RequestMapping("eportFormPackageDeductibleAll")
	@ResponseBody
	public List<PricingDeductionRecord> eportFormPackageDeductibleAll() {

		return pricingDeductionRecordService.eportFormPackageDeductibleAll();
	}

	/**
	 * 查找套餐抵扣统计信息（年）
	 * 
	 */
	@RequestMapping("eportFormPackageDeductibleList")
	@ResponseBody
	public List<PricingDeductionRecord> eportFormPackageDeductibleList(
			@ModelAttribute("pricingDeductionRecord") PricingDeductionRecord pricingDeductionRecord,
			String createTimeStart, String createTimeEnd) {
		pricingDeductionRecord.setCreateTimeStart(ECDateUtils.parseDate(createTimeStart, "yyyy"));
		pricingDeductionRecord.setCreateTimeEnd(ECDateUtils.parseDate(createTimeEnd, "yyyy"));

		return pricingDeductionRecordService.eportFormPackageDeductibleList(new Query(pricingDeductionRecord));
	}

	/**
	 * 查找套餐订单信息（月份）
	 * 
	 */
	@RequestMapping("eportFormPackageDeductibleMonth")
	@ResponseBody
	public List<PricingDeductionRecord> eportFormPackageDeductibleMonth(
			@ModelAttribute("pricingDeductionRecord") PricingDeductionRecord pricingDeductionRecord,
			String createTime) {

		pricingDeductionRecord.setTimeMonth(createTime);
		return pricingDeductionRecordService.eportFormPackageDeductibleMonth(new Query(pricingDeductionRecord));
	}

	/**
	 * 查找套餐订单信息（天）
	 * 
	 */
	@RequestMapping("eportFormPackageDeductibleDay")
	@ResponseBody
	public List<PricingDeductionRecord> eportFormPackageDeductibleDay(
			@ModelAttribute("pricingDeductionRecord") PricingDeductionRecord pricingDeductionRecord,
			String createTime) {

		pricingDeductionRecord.setTimeDay(createTime);
		return pricingDeductionRecordService.eportFormPackageDeductibleDay(new Query(pricingDeductionRecord));
	}

	/**
	 * 进入赠送套餐统计页面
	 * 
	 */
	@RequestMapping("toPackageEportForm")
	public String toPackageEportForm() {
		return "/reportForm/packageEportForm";
	}

	/**
	 * 查找套餐订单信息
	 * 
	 */
	@RequestMapping("reportFormPricingPack")
	@ResponseBody
	public List<PricingPackOrder> reportFormPricingPack(
			@ModelAttribute("pricingPackOrder") PricingPackOrder pricingPackOrder, String createTimeStart,
			String createTimeEnd) {

		pricingPackOrder.setCreateTimeStart(ECDateUtils.parseDate(createTimeStart, "yyyy"));
		pricingPackOrder.setCreateTimeEnd(ECDateUtils.parseDate(createTimeEnd, "yyyy"));
		return pricingPackOrderService.eportFormPackageList(new Query(pricingPackOrder));
	}

	/**
	 * 查找套餐订单信息（月份）
	 * 
	 */
	@RequestMapping("eportFormPackageListMonth")
	@ResponseBody
	public List<PricingPackOrder> eportFormPackageListMonth(
			@ModelAttribute("pricingPackOrder") PricingPackOrder pricingPackOrder, String createTime) {

		pricingPackOrder.setTimeMonth(createTime);
		;
		return pricingPackOrderService.eportFormPackageListMonth(new Query(pricingPackOrder));
	}

	/**
	 * 查找套餐订单信息（日）
	 * 
	 */
	@RequestMapping("eportFormPackageListDay")
	@ResponseBody
	public List<PricingPackOrder> eportFormPackageListDay(
			@ModelAttribute("pricingPackOrder") PricingPackOrder pricingPackOrder, String createTime) {
		pricingPackOrder.setTimeDay(createTime);
		// pricingPackOrder.setCreateTime(ECDateUtils.parseDate(createTime,"yyyyMM"));
		return pricingPackOrderService.eportFormPackageListDay(new Query(pricingPackOrder));
	}

	/*
	 * 查询赠送套餐报表信息（全部）
	 */
	@RequestMapping("eportFormPackageListAll")
	@ResponseBody
	public List<PricingPackOrder> eportFormPackageListAll() {

		return pricingPackOrderService.eportFormPackageListAll();
	}

	public static String getSpecifiedWeek(Date specifiedDay, int i) {
		Calendar c = Calendar.getInstance();
		c.setTime(specifiedDay);
		int day = c.get(Calendar.DATE);
		c.set(Calendar.DATE, day + i * 6);

		String dayBefore = new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
		return dayBefore;
	}

	public static Date getSpecifiedWeekNs(Date specifiedDay, int i) {
		Calendar c = Calendar.getInstance();
		c.setTime(specifiedDay);
		int day = c.get(Calendar.DATE);
		c.set(Calendar.DATE, day + i * 6);

		return c.getTime();
	}

	public static String getSpecifiedWk(Date specifiedDay, int i) {
		Calendar c = Calendar.getInstance();
		c.setTime(specifiedDay);
		int day = c.get(Calendar.DATE);
		c.set(Calendar.DATE, day + i);

		String dayBefore = new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
		return dayBefore;
	}

	public static String getSpecifiedDayBefore(Date specifiedDay, int i) {
		Calendar c = Calendar.getInstance();
		c.setTime(specifiedDay);
		int day = c.get(Calendar.DATE);
		c.set(Calendar.DATE, day - i);

		String dayBefore = new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
		return dayBefore;
	}

}
