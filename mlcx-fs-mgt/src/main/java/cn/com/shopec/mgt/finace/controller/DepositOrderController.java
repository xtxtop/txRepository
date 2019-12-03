package cn.com.shopec.mgt.finace.controller;

import java.text.ParseException;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.finace.model.DepositOrderForMgt;
import cn.com.shopec.core.finace.service.DepositOrderService;
import cn.com.shopec.core.member.model.Member;
import cn.com.shopec.core.member.service.MemberService;
import cn.com.shopec.mgt.common.BaseController;
/**
 * 押金退款
 * */

@Controller
@RequestMapping("depositPay")
public class DepositOrderController extends BaseController{
	
	private static final Log log = LogFactory.getLog(DepositOrderController.class);
	@Resource
	private DepositOrderService depositOrderService;
	@Resource
	private MemberService memberService ;
	/*
	 * 进入押金缴纳列表页面
	 */
	@RequestMapping("toDepositPayList")
	public String mainPage(ModelMap modelMap, String memberNo) {
		Member m =memberService.getMember(memberNo);
		if(m !=null){
			modelMap.put("mobilePhone", m.getMobilePhone());
		}
		
		return "finace/depositOrder_list";
	}
	
	/*
	 * 显示押金退款列表信息
	 */
	@RequestMapping("pageListDepositOrder")
	@ResponseBody
	public PageFinder<DepositOrderForMgt> pageListDepositOrder(DepositOrderForMgt depositOrderForMgt,Query query) throws ParseException {
		Query q = new Query(query.getPageNo(),query.getPageSize(),depositOrderForMgt);
		return depositOrderService.getMemberDepositList(q);
	}
	
}
