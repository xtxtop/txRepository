package cn.com.shopec.mgt.ml.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.ml.model.CAccountBalance;
import cn.com.shopec.core.ml.model.CAccountRecord;
import cn.com.shopec.core.ml.service.CAccountBalanceService;
import cn.com.shopec.core.ml.service.CAccountRecordService;
import cn.com.shopec.core.ml.vo.AccountBalanceVo;
import cn.com.shopec.mgt.common.BaseController;

@Controller
@RequestMapping("accountBalance")
public class AccountBalanceController extends BaseController{
	@Resource
	private CAccountBalanceService accountBalanceService;
	@Resource
	private CAccountRecordService accountRecordService;
	
	/**
	 * 进入会员账户列表首页
	 * @return
	 */
	@RequestMapping("/getAccountBalance")
	public String getAccountBalance(){
		return "ml/accountBalance_list";
	}
	/**
	 * 获取会员账户列表
	 * @param accountBalanceVo
	 * @param query
	 * @return
	 */
	@RequestMapping("/getPageFinderAccountBalanceList")
	@ResponseBody
	public PageFinder<AccountBalanceVo> getPageFinalAccountBalanceList(@ModelAttribute("accountBalanceVo") AccountBalanceVo accountBalanceVo, Query query){
		return accountBalanceService.getCAccountBalancePagedListTwo(new Query(query.getPageNo(), query.getPageSize(), accountBalanceVo));
	}
	
	/**
	 * 查看详情
	 * @param stationNo
	 * @param model
	 * @return
	 */
	@RequestMapping("/toAccountBalanceView")
	public String toAccountBalanceView(String memberNo,Model model){
		
		model.addAttribute("accountBalance",accountBalanceService.pageListTwo_No(memberNo));
		return "ml/accountBalance_view";
	}
	/**
	 * 验证用户是否充值
	 * @param memberNo
	 * @return
	 */
	@RequestMapping("/checkAccountBalance")
	@ResponseBody
	public ResultInfo<CAccountRecord> checkAccountBalance(String memberNo){
		ResultInfo<CAccountRecord> result = new ResultInfo<CAccountRecord>();
		long l=accountRecordService.getAccountRecord_No(memberNo);
		if (l<1) {
			result.setCode("0");
			result.setMsg("暂无充值记录!");
			return result;
		}else{
			result.setCode("1");
			return result;
		}
		
	}
	/**
	 * 进入个人充值记录页面
	 * @param memberNo
	 * @param model
	 * @return
	 */
	@RequestMapping("/toaccountRecord")
	public String toaccountRecord(String memberNo,Model model){
		model.addAttribute("memberNo", memberNo);
		return "ml/accountRecord_list";
	}
	/**
	 * 获取充值记录列表
	 * @param memberNo
	 * @return
	 */
	@RequestMapping("/toaccountRecordList")
	@ResponseBody
	public PageFinder<CAccountRecord> toaccountRecordList(@ModelAttribute("CAccountRecord") CAccountRecord accountRecord, Query query){
		return accountRecordService.getAccountRecord(new Query(query.getPageNo(), query.getPageSize(),accountRecord));
	}
	/**
	 * 进入会员充值页面
	 * @param memberNo
	 * @param model
	 * @return
	 */
	@RequestMapping("/toAccountBalanceEdit")
	private String toAccountBalanceEdit(String memberNo,Model model){
		model.addAttribute("member", accountBalanceService.pageListTwo_No(memberNo));
		return "ml/accountBalance_edit";
	}
	/**
	 * 修改账户余额
	 * @param accountBalance
	 * @return
	 */
	@RequestMapping("/editAccountBalance")
	@ResponseBody
	public ResultInfo<CAccountBalance> editAccountBalance(@ModelAttribute("CAccountBalance") CAccountBalance accountBalance){
		return accountBalanceService.updateCAccountBalance(accountBalance, getOperator());
		
	}
}
