package cn.com.shopec.mgt.order.controller;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.common.constants.Constant;
import cn.com.shopec.common.utils.ECNumberUtils;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.marketing.service.PricingPackageService;
import cn.com.shopec.core.order.common.OrderConstant;
import cn.com.shopec.core.order.model.Order;
import cn.com.shopec.core.order.model.OrderStrikeBalance;
import cn.com.shopec.core.order.service.OrderService;
import cn.com.shopec.core.order.service.OrderStrikeBalanceService;
import cn.com.shopec.mgt.common.BaseController;

@Controller
@RequestMapping("orderStrikeBalance")
public class OrderStrikeBalanceController extends BaseController {
	@Resource
	private OrderStrikeBalanceService orderStrikeBalanceService;
	@Resource
	private PricingPackageService pricingPackageService;
	@Resource
	private OrderService orderService;

	/**
	 * 订单冲账列表页面
	 * 
	 * @return
	 */
	@RequestMapping("toOrderStrikeBalanceList")
	public String toOrderStrikeBalanceList(ModelMap modelMap) {
		return "order/order_strike_balance_list";
	}

	/**
	 * 首页点击 待办事项 里面 订单冲账 跳转 订单冲账列表页面
	 * 
	 * @return
	 */
	@RequestMapping("toOrderStrikeBalanceListTodo")
	public String toOrderStrikeBalanceListTodo(ModelMap modelMap) {
		Integer censorStatus = 0;
		modelMap.addAttribute("censorStatus", censorStatus);
		return "order/order_strike_balance_list";
	}

	/**
	 * 订单冲账页面分页
	 */
	@RequestMapping("pageListOrderStrikeBalance")
	@ResponseBody
	public PageFinder<OrderStrikeBalance> pageListOrderStrikeBalance(
			@ModelAttribute("orderStrikeBalance") OrderStrikeBalance orderStrikeBalance, Query query) {
		Query q = new Query(query.getPageNo(), query.getPageSize(), orderStrikeBalance);
		PageFinder<OrderStrikeBalance> page = orderStrikeBalanceService.getOrderStrikeBalancePagedList(q);
		
		//添加权限
		Integer roleAdminTag = getLoginSysUserRoleAdmin();
		List<OrderStrikeBalance> orderStrikeBalanceList = page.getData();
		if(orderStrikeBalanceList != null && !orderStrikeBalanceList.isEmpty()){
			for (OrderStrikeBalance temp : orderStrikeBalanceList) {
				temp.setRoleAdminTag(roleAdminTag);
			}
		}
		return page;
	}

	/**
	 * 订单冲账详情
	 * 
	 * @return
	 */
	@RequestMapping("toOrderStrikeBalanceView")
	public String toOrderStrikeBalanceView(String strikeBalanceNo, ModelMap modelMap) {
		OrderStrikeBalance orderStrikeBalance = orderStrikeBalanceService.getOrderStrikeBalance(strikeBalanceNo);
		modelMap.put("orderStrikeBalance", orderStrikeBalance);
		return "order/order_strike_balance_view";
	}

	/**
	 * 订单冲账审核页面
	 * 
	 * @return
	 */
	@RequestMapping("toOrderStrikeBalanceCencor")
	public String toOrderStrikeBalanceCencor(String strikeBalanceNo, ModelMap modelMap) {
		OrderStrikeBalance orderStrikeBalance = orderStrikeBalanceService.getOrderStrikeBalance(strikeBalanceNo);
		modelMap.put("orderStrikeBalance", orderStrikeBalance);
		return "order/order_strike_balance_cencor";
	}

	/**
	 * 订单冲账审核操作 冲账审核通过时，修改冲账记录表的审核信息，修改订单表的冲账金额=原来的+冲账金额， 修改订单的应付金额=原来的-冲账金额
	 * 如果应付金额为0则修改支付状态为已支付
	 * 
	 * @return
	 */
	@RequestMapping("cencorOrderStrikeBalance")
	@ResponseBody
	public ResultInfo<OrderStrikeBalance> cencorOrderStrikeBalance(
			@ModelAttribute("orderStrikeBalance") OrderStrikeBalance orderStrikeBalance) {
		ResultInfo<OrderStrikeBalance> resultInfo = new ResultInfo<OrderStrikeBalance>();
		
		orderStrikeBalance.setCensorTime(new Date());
		orderStrikeBalance.setCensorPersonId(getOperator().getOperatorId());
		orderStrikeBalance.setCensorPersonName(getOperator().getOperatorUserName());
		OrderStrikeBalance osb = orderStrikeBalanceService
				.getOrderStrikeBalance(orderStrikeBalance.getStrikeBalanceNo());
		if (orderStrikeBalance.getCensorStatus().equals(1)) {// 审核通过
			Order o = orderService.getOrder(osb.getOrderNo());
			// 分时订单
			if (o != null) {
				o.setOrderNo(osb.getOrderNo());
				if (o.getStrikeBalanceAmount() == null) {
					o.setStrikeBalanceAmount(0d);
				}
				Double payAmount = ECNumberUtils.roundDoubleWithScale(o.getPayableAmount(), 2);
				Double strilkeAmount = ECNumberUtils.roundDoubleWithScale(osb.getStrikeBalanceAmount(), 2);
				if (payAmount < strilkeAmount) {
					resultInfo.setCode(OrderConstant.order_ge_strike);
					resultInfo.setMsg(OrderConstant.order_ge_strike_msg);
					return resultInfo;
				} else {
					o.setStrikeBalanceAmount(ECNumberUtils
							.roundDoubleWithScale(o.getStrikeBalanceAmount() + osb.getStrikeBalanceAmount(), 2));
					o.setPayableAmount(
							ECNumberUtils.roundDoubleWithScale(o.getPayableAmount() - osb.getStrikeBalanceAmount(), 2));
					if (o.getPayableAmount().equals(0.0)) {
						o.setPayStatus(1);
						o.setPaymentTime(new Date());
					}
					if(o.getPackMinutesDiscountAmount()==null){
						o.setPackMinutesDiscountAmount(0d);
					}
					if(o.getPackAmountDiscountAmount()==null){
						o.setPackAmountDiscountAmount(0d);
					}
					//订单冲账金额 
					double temp1 = ECNumberUtils.roundDoubleWithScale(o.getStrikeBalanceAmount(), 2);
					//订单实际需要冲账金额 = 订单金额-套餐抵扣费用+订单附加服务费
					double temp2 = ECNumberUtils.roundDoubleWithScale((o.getOrderAmount() - o.getPackMinutesDiscountAmount()+ o.getPackAmountDiscountAmount() + (o.getServiceFeeAmount()==null?0L:o.getServiceFeeAmount())), 2);
					
					if (temp1 > temp2) {
						resultInfo.setCode(OrderConstant.order_ge_strike_pay);
						resultInfo.setMsg(OrderConstant.order_ge_strike_pay_msg);
						return resultInfo;
					}
					orderService.updateOrder(o, getOperator());
					return orderStrikeBalanceService.updateOrderStrikeBalance(orderStrikeBalance, getOperator());
				}
			} else {
				resultInfo.setCode(Constant.FAIL);
				resultInfo.setMsg("订单不存在");
				return resultInfo;
			}

		} else if (orderStrikeBalance.getCensorStatus().equals(2)) {
			return orderStrikeBalanceService.updateOrderStrikeBalance(orderStrikeBalance, getOperator());
		} else {
			return resultInfo;
		}

	}

	/**
	 * 订单冲账提交
	 */
	@RequestMapping("orderStrikeBalanceAdd")
	@ResponseBody
	public ResultInfo<OrderStrikeBalance> orderStrikeBalanceAdd(
			@ModelAttribute("orderStrikeBalance") OrderStrikeBalance orderStrikeBalance) {
		orderStrikeBalance.setSubmitTtime(new Date());
		orderStrikeBalance.setSubmitId(getOperator().getOperatorId());
		orderStrikeBalance.setSubmitName(getOperator().getOperatorUserName());
		orderStrikeBalance.setCensorStatus(0);// 未审核
		return orderStrikeBalanceService.addOrderStrikeBalanceAndUpdateOrder(orderStrikeBalance, getOperator());
	}
}
