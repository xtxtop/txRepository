package cn.com.shopec.mgt.member.controller;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.common.constants.Constant;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.member.model.Member;
import cn.com.shopec.core.member.service.MemberService;
import cn.com.shopec.core.order.model.PricingPackOrder;
import cn.com.shopec.core.order.service.PricingPackOrderService;
import cn.com.shopec.core.order.service.impl.PricingPackOrderServiceImpl;
import cn.com.shopec.mgt.common.BaseController;
import cn.com.shopec.mgt.order.vo.PricingPackOrderVo;

/**
 * 会员账户资金
 */
@Controller
@RequestMapping("memberAccountAmount")
public class MemberAccountAmountController extends BaseController {
	private static final Log log = LogFactory.getLog(PricingPackOrderServiceImpl.class);
	@Resource
	private MemberService memberService;
	@Resource
	private PricingPackOrderService pricingPackOrderService;
	/**
	 * 会员账户资金列表页面
	 * 
	 * @return
	 */
	@RequestMapping("toMemberAccountAmount")
	public String toMemberBalanceList() {
		return "member/member_account_amount";
	}

	/**
	 *会员账户资金列表页面分页
	 * 
	 * @return
	 */
	@RequestMapping("pageListMemberAccountAmount")
	@ResponseBody
	public PageFinder<Member> pageListMemberAccountAmount(@ModelAttribute("member") Member member, Query query) {
		Query q = new Query(query.getPageNo(), query.getPageSize(), member);
		return memberService.getMemberAccountAmount(q);
	}
	
	/**
	 *会员账户资金列表页面分页
	 * 
	 * @return
	 */
	@RequestMapping("toMemberRecharge")
	public String toMemberRecharge(String[] memberNos, Model model) {
		List<Member> memberList = memberService.getMemberByIds(memberNos);
		if(memberList != null){
			StringBuilder noString = new StringBuilder();
			StringBuilder nameString = new StringBuilder();
			for (Member member : memberList) {
				noString.append(member.getMemberNo());
				noString.append(",");
				if(member.getMemberName() != null && !member.getMemberName().equals("") ){
					nameString.append(member.getMemberName());
					nameString.append(",");
				}
			}
			noString.deleteCharAt(noString.length()-1);
			if(nameString.length() > 0){
				nameString.deleteCharAt(nameString.length()-1);
			}
			model.addAttribute("memberNos",noString.toString());
			model.addAttribute("memberNames",nameString.toString());

		}
		return "member/member_recharge_add";
	}
	
	/**
	 * 手工充值的套餐添加
	 * 
	 * @param pricePackage
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("rechargeMemberAccount")
	@ResponseBody
	public ResultInfo<PricingPackOrder> rechargeMemberAccount(@ModelAttribute("PricingPackOrderVo") PricingPackOrderVo pricingPackOrderVo,String memo) throws Exception {
		 ResultInfo<PricingPackOrder>  res = new  ResultInfo<PricingPackOrder> ();
		
		 if(getLoginSysUserRoleAdmin() != null && getLoginSysUserRoleAdmin() != 0){
			 StringBuilder failMemberNames = new StringBuilder(); 
			 for(String memberNo: pricingPackOrderVo.getMemberNos()){
				try {
					if(pricingPackOrderVo.getPackAmount() < 0){
						pricingPackOrderService.dikou(memberNo, pricingPackOrderVo.getPackAmount(), memo, this.getOperator());//扣余额
					}else if(pricingPackOrderVo.getPackAmount() > 0){
						pricingPackOrderService.recharge(memberNo,pricingPackOrderVo.getPackAmount(),memo, this.getOperator());//加余额
					}
				} catch (Exception e) {
					log.error(e.getMessage(), e);
					Member member = memberService.getMember(memberNo);
					if(member != null && !member.getMemberName().equals("")){
						failMemberNames.append(member.getMemberName());
						failMemberNames.append(",");
					}
				}
			}
			if(failMemberNames.toString().trim().equals("")){
				res.setCode(Constant.SECCUESS);
			}else if(pricingPackOrderVo.getMemberNos().size() == 1){
				res.setCode(Constant.FAIL);
				res.setMsg("");
			}else{
				String memberNames = failMemberNames.deleteCharAt(failMemberNames.length()-1).toString();
				res.setCode(Constant.SECCUESS);
				res.setMsg("部分会员手工充值失败，失败会员名如下: " + memberNames);
			}
		}else{
			res.setCode(Constant.FAIL);
			res.setMsg("当前用户没有权限操作");
		}
		return res;
	}
}
