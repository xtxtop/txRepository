package cn.com.shopec.mgt.dailyrental.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.common.constants.Constant;
import cn.com.shopec.core.dailyrental.model.MerchantOrder;
import cn.com.shopec.core.dailyrental.service.MerchantOrderService;
import cn.com.shopec.mgt.common.BaseController;

@Controller
@RequestMapping("merchantOrder")
public class MerchantOrderController extends BaseController {

	@Resource
	public MerchantOrderService  merchantOrderService;
	
	
	/*
	 * 确认还车修改商家订单状态
	 */
	@RequestMapping("confirmChangeCar")
	@ResponseBody
	public ResultInfo<String> getMerchant(String orderNo) {
		ResultInfo<String> result = new ResultInfo<String>();
		if(orderNo!=null&&!"".equals(orderNo)){
			MerchantOrder merchantOrder = merchantOrderService.getOrderDayNo(orderNo);
			if (merchantOrder!=null) {
				MerchantOrder merchantorderForUpdate = new MerchantOrder();
				merchantorderForUpdate.setMerchantOrderId(merchantOrder.getMerchantOrderId());
				merchantorderForUpdate.setMerchantOrderStatus(7);
				ResultInfo<MerchantOrder> res = merchantOrderService.updateMerchantOrder(merchantorderForUpdate, getOperator());
				if ("1".equals(res.getCode())) {
					result.setCode(Constant.SECCUESS);
				}else {
					result.setCode(Constant.FAIL);
				}
			}else {
				result.setCode(Constant.FAIL);
				result.setMsg("商家订单不存在");
			}
		}else{
			result.setCode(Constant.FAIL);
			result.setMsg("订单编号为空");
		}
		return result;
	}
	
}
