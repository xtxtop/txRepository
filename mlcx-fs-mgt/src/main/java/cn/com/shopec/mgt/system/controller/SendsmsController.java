package cn.com.shopec.mgt.system.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.shopec.common.Operator;
import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.system.model.SendSms;
import cn.com.shopec.core.system.service.SendSmsService;
import cn.com.shopec.mgt.common.BaseController;

@Controller
@RequestMapping("/sendsms")
public class SendsmsController extends BaseController {
	@Resource
	public SendSmsService sendSmsService;

	/**
	 * 进入商城发送短信记录列表页面
	 * 
	 * @return
	 */
	@RequestMapping("sendsms")
	public String sendsms() {
		return "/system/sendsms_list";
	}

	/**
	 * 查询商城发送短信记录信息（分页）
	 */
	@RequestMapping("querySendSms")
	@ResponseBody
	public PageFinder<SendSms> querySendSms(@ModelAttribute("sendSms") SendSms sendSms, Query query) {
		Query q = new Query(query.getPageNo(), query.getPageSize(), sendSms);
		return this.sendSmsService.getSendSmsPagedList(q);
	}
	
	/**
	 * 根据主键，查询一条商城发送短信记录信息
	 */
	@RequestMapping("getSendSms")
	@ResponseBody
	public SendSms getSendSms(@RequestParam("smsId") String smsId) {
		return sendSmsService.getSendSms(smsId);
	}

}
