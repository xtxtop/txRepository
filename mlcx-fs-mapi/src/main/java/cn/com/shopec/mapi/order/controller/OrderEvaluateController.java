package cn.com.shopec.mapi.order.controller;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.common.constants.Constant;
import cn.com.shopec.core.ml.service.COrderEvaluateService;
import cn.com.shopec.mapi.common.controller.BaseController;

/**
 * app订单评价
 * 
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/app/orderEvaluate")
public class OrderEvaluateController extends BaseController {

	@Resource
	private COrderEvaluateService orderEvaluateService;

	/**
	 * 订单评价
	 * 
	 * @param orderNO
	 *            订单编号
	 * @param evaluateGrade
	 *            评价分
	 * @return
	 */
	@RequestMapping("toAddOrderEvaluate")
	@ResponseBody
	public ResultInfo<Object> toAddOrderEvaluate(@RequestParam String orderNO, @RequestParam String evaluateGrade) {
		ResultInfo<Object> resultInfo = new ResultInfo<Object>();
		// 判断订单编号不存在，直接返回参数无效
		if (StringUtils.isEmpty(orderNO)) {
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG);
			return resultInfo;
		}
		return orderEvaluateService.toInsertOrderEvaluate(orderNO, evaluateGrade, getOperator());
	}
}
