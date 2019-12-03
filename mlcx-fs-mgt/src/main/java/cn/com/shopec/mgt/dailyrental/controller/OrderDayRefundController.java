package cn.com.shopec.mgt.dailyrental.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.shopec.common.Operator;
import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.common.constants.Constant;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.dailyrental.model.OrderDay;
import cn.com.shopec.core.dailyrental.service.MerchantService;
import cn.com.shopec.core.dailyrental.service.OrderDayService;
import cn.com.shopec.core.finace.service.DepositRefundService;
import cn.com.shopec.mgt.common.BaseController;

@Controller
@RequestMapping("refund")
public class OrderDayRefundController extends BaseController {
	private static final Log log = LogFactory.getLog(OrderDayRefundController.class);
	@Resource
	public OrderDayService orderDayService;
	@Resource
	public MerchantService merchantService;
	/*
	 * 显示日租订单退款列表页
	 */
	@RequestMapping("toOrderDayRefundList")
	public String toOrderDayList(ModelMap modelMap) {
		return "dailyrental/finance/order_day_refund_list";
	}

	/*
	 * 日租订单退款列表分页
	 */
	@RequestMapping("pageListOrderDayRefund")
	@ResponseBody
	public PageFinder<OrderDay> pageListOrderDay(@ModelAttribute("orderDay") OrderDay orderDay, Query query) {
		Query q = new Query(query.getPageNo(), query.getPageSize(), orderDay);
		return orderDayService.getOrderDayRefundPagedList(q);
	}
	
	@RequestMapping("toOrderDayAlipayRefund")
	@ResponseBody
	public ResultInfo<String> toOrderDayAlipayRefund(String orderNo,Double refundAmount) throws Exception {
		ResultInfo<String> resultInfo = new ResultInfo<String>();
		resultInfo.setCode(Constant.FAIL);
		resultInfo.setMsg("系统错误，请稍后再试");
		//判断当前押金是否支持退款--调用支付宝单笔交易
		Operator operator = getOperator();
		if(null == operator){
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg("登录信息过期，请重新登录后进行操作");
			return resultInfo;
		}
		//验证当前订单是否在有效期内
		Map<String, String> tradeInfo = new HashMap<String, String>();
		ResultInfo<String> result = orderDayService.signOrder(orderNo, tradeInfo);
		
		log.info("## tradeInfo=" + tradeInfo);
		
		if(result == null) {
			log.error("signOrder fail ,result is null");
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg("系统异常，请稍后再试");
			return resultInfo;
		}
		if ("0".equals(result.getCode())) {
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg("TRADE_NOT_EXIST");
			return resultInfo;
		}
		String remark = "协商押金退款";
		log.info("result.code = " + result.getCode() + ",result.data=" + result.getData() + ",result.msg=" + result.getMsg());
		//查询成功，在有效期，进入退款页面
		if(Constant.SECCUESS.equals(result.getCode()) && "TRADE_SUCCESS".equals(result.getData())){		
			ResultInfo<OrderDay> depositRefundResult = orderDayService.toOrderDayRefundAvoidPwd(orderNo,refundAmount,operator, remark);
			if ("1".equals(depositRefundResult.getCode())) {
				resultInfo.setCode(Constant.SECCUESS);
				resultInfo.setMsg("退款成功");
			}else {
				resultInfo.setCode(Constant.FAIL);
				resultInfo.setMsg(depositRefundResult.getMsg());
			}
		}
		return resultInfo;
	}
	@RequestMapping("toOrderDayWxRefund")
	@ResponseBody
	public ResultInfo<String> toOrderDayWxRefund(String orderNo,Double refundAmount) throws Exception {
		ResultInfo<String> resultInfo = new ResultInfo<String>();
		resultInfo.setCode(Constant.FAIL);
		resultInfo.setMsg("系统错误，请稍后再试");
		Operator operator = getOperator();
		if(null == operator){
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg("登录信息过期，请重新登录后进行操作");
			return resultInfo;
		}
		OrderDay orderDay = orderDayService.getOrderDay(orderNo);
		if (orderDay==null) {
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg("订单不存在");
			return resultInfo;
		}
		ResultInfo<OrderDay> result = orderDayService.orderDayRefundWX(orderDay,refundAmount,operator);
		if ("1".equals(result.getCode())) {
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg("退款成功");
			return resultInfo;
		}else {
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(result.getMsg());
			return resultInfo;
		}
	}
}
