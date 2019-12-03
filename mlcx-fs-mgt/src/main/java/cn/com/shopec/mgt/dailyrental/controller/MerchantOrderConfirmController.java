package cn.com.shopec.mgt.dailyrental.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

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
import cn.com.shopec.core.dailyrental.model.Merchant;
import cn.com.shopec.core.dailyrental.model.MerchantOrderConfirm;
import cn.com.shopec.core.dailyrental.service.MerchantOrderConfirmService;
import cn.com.shopec.core.dailyrental.service.MerchantService;
import cn.com.shopec.mgt.common.BaseController;

@Controller
@RequestMapping("merchantOrderConfirm")
public class MerchantOrderConfirmController extends BaseController {

	@Resource
	public MerchantService  merchantService;
	@Resource
	public MerchantOrderConfirmService merchantOrderConfirmService;
	/*
	 * 显示商家对账列表页
	 */
	@RequestMapping("toMerchantOrderConfirmList")
	public String toMerchantList(ModelMap map) {
		Merchant queryMerchant = new Merchant();
		List<Merchant> merchantList = merchantService.getMerchantList(new Query(queryMerchant));
		map.addAttribute("merchantList", merchantList);
		MerchantOrderConfirm queryMerchantOrderConfirm = new MerchantOrderConfirm();
		List<MerchantOrderConfirm> merchanOrderConfirmtList = merchantOrderConfirmService.getMerchantOrderConfirmList(new Query(queryMerchantOrderConfirm));
		if(merchanOrderConfirmtList.size()>0){
			Set<String> set = new HashSet<String>();
			for(MerchantOrderConfirm merchantOrderConfirm:merchanOrderConfirmtList){
				set.add(merchantOrderConfirm.getDate());
			}
			map.addAttribute("dateList", set);
		}
		return "dailyrental/finance/merchant_order_confirm_list";
	}

	/*
	 * 显示商家对账列表页
	 */
	@RequestMapping("pageListMerchantOrderConfirm")
	@ResponseBody
	public PageFinder<MerchantOrderConfirm> pageListMerchantOrderConfirm(@ModelAttribute("merchantOrderConfirm") MerchantOrderConfirm merchantOrderConfirm, Query query) {
		Query q = new Query(query.getPageNo(), query.getPageSize(), merchantOrderConfirm);
		return merchantOrderConfirmService.getMerchantOrderConfirmPagedListForMgt(q);
	}

	/*
	 * 确认商家对账单结算
	 */
	@RequestMapping("merchantOrderConfirmSettled")
	@ResponseBody
	public ResultInfo<MerchantOrderConfirm> merchantOrderConfirmSettled(String id) {
		ResultInfo<MerchantOrderConfirm> result = new ResultInfo<MerchantOrderConfirm>();
		if(id==null||"".equals(id)){
			result.setCode(Constant.FAIL);
			result.setMsg("对账编号为空");
			return result;
		}
		MerchantOrderConfirm merchantOrderConfirm = new MerchantOrderConfirm();
		merchantOrderConfirm.setId(id);
		merchantOrderConfirm.setIsSettled(1);
		Operator operator = getOperator();
		if (operator==null) {
			result.setCode(Constant.FAIL);
			result.setMsg("请重新登录");
			return result;
		}
		return merchantOrderConfirmService.updateMerchantOrderConfirm(merchantOrderConfirm, operator);
	}

}
