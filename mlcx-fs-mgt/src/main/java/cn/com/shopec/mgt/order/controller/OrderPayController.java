package cn.com.shopec.mgt.order.controller;

import java.text.ParseException;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.core.pay.UnionPayService;
import cn.com.shopec.mgt.common.BaseController;
@Controller
@RequestMapping("/orderPay")
public class OrderPayController extends BaseController{
	
	@Resource
	private UnionPayService unionPayService;
	
	@RequestMapping("/unionPreBackPreTrans")
	@ResponseBody
	public ResultInfo<String> unionPreBackPreTrans(String orderNo) throws ParseException {
		return unionPayService.backPreTrans(orderNo);
	}
	
	@RequestMapping("/unionFinalPreTrans")
	@ResponseBody
	public ResultInfo<String> unionFinalPreTrans(String orderNo) throws ParseException {
		return unionPayService.finalPreTrans(orderNo);
	}
	
	@RequestMapping("/unionFinalAndBackPreTrans")
	@ResponseBody
	public ResultInfo<String> unionFinalAndBackPreTrans(String orderNo) throws ParseException {
		return unionPayService.finalAndBackPreTrans(orderNo);
	}

}
