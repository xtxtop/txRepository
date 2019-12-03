package cn.com.shopec.mgt.member.controller;

import java.text.ParseException;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.common.constants.Constant;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.member.model.MemberPointsRule;
import cn.com.shopec.core.member.service.MemberPointsRuleService;
import cn.com.shopec.mgt.common.BaseController;

/**
 * 会员积分规则管理
 * 
 * @author zln
 *
 */
@Controller
@RequestMapping("/memberPointsRule")
public class MemberPointsRuleController extends BaseController {

	@Resource
	private MemberPointsRuleService memberPointsRuleService;
	@Value("${base_path}")
	private String basePath;
	/**
	 * 进入会员积分规则列表页面
	 * 
	 * @return
	 */
	@RequestMapping("toMemberPointsRule")
	public String toMemberPointsRule() {
		return "/member/member_points_rule_list";
	}

	/**
	 * 查询会员积分规则列表
	 * 
	 * @param query
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping("pageListMemberPointsRule")
	@ResponseBody
	public PageFinder<MemberPointsRule> pageListMemberPointsRule(@ModelAttribute("memberPointsRule") MemberPointsRule memberPointsRule,@RequestParam("pageNo") int pageNo,
			@RequestParam("pageSize") int pageSize) throws ParseException {
		Query q = new Query(pageNo, pageSize, memberPointsRule);
		return memberPointsRuleService.getMemberPointsRulePagedList(q);
	}

	/**
	 * 会员积分规则详情
	 * 
	 * @param
	 * @return
	 */
	@RequestMapping("toMemberPointsRuleView")
	public String toMemberPointsRuleView(String ruleId, ModelMap model) {
		MemberPointsRule memberPointsRule = memberPointsRuleService.getMemberPointsRule(ruleId);
		model.addAttribute("memberPointsRule", memberPointsRule);
		return "/member/member_points_rule_view";
	}

	/**
	 * 会员积分规则添加页面
	 * 
	 * @param
	 * @return
	 */
	@RequestMapping("toAddMemberPointsRule")
	public String toAddMemberPointsRule() {
		return "/member/member_points_rule_add";
	}

	/**
	 * 会员积分规则添加
	 * 
	 * @param 
	 * @return
	 */
	@RequestMapping("addMemberPointsRule")
	@ResponseBody
	public ResultInfo<MemberPointsRule> addMemberPointsRule(@ModelAttribute("memberPointsRule") MemberPointsRule memberPointsRule) {
		ResultInfo<MemberPointsRule> resultInfo=new ResultInfo<MemberPointsRule>();
		//根据业务类型查询是否已经存在积分规则
		MemberPointsRule mprSearch=new MemberPointsRule();
		mprSearch.setBusinessType(memberPointsRule.getBusinessType());
		mprSearch.setPointsType(memberPointsRule.getPointsType());
		List<MemberPointsRule> ruleList=memberPointsRuleService.getMemberPointsRuleList(new Query(mprSearch));
		if(ruleList!=null&&ruleList.size()>0){
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg("该业务类型的积分规则已存在！");
			return resultInfo;
		}else{
			memberPointsRule.setIsAvailable(1);
			memberPointsRule.setIsDefault(1);
			return  memberPointsRuleService.addMemberPointsRule(memberPointsRule, getOperator());
		}
	}
	/**
	 * 会员积分规则编辑页面
	 * 
	 * @param
	 * @return
	 */
	@RequestMapping("toUpdateMemberPointsRule")
	public String toUpdateMemberPointsRule(String ruleId, ModelMap model ) {
		MemberPointsRule memberPointsRule = memberPointsRuleService.getMemberPointsRule(ruleId);
		model.addAttribute("memberPointsRule", memberPointsRule);
		return "/member/member_points_rule_edit";
	}

	/**
	 * 会员积分规则修改
	 * 
	 * @param 
	 * @return
	 */
	@RequestMapping("updateMemberPointsRule")
	@ResponseBody
	public ResultInfo<MemberPointsRule> updateMemberPointsRule(@ModelAttribute("memberPointsRule") MemberPointsRule memberPointsRule) {
		return  memberPointsRuleService.updateMemberPointsRule(memberPointsRule, getOperator());
	}   
	/**
	 * 会员积分规则删除
	 * */
	@RequestMapping("delMemberPointsRule")
	@ResponseBody
	public ResultInfo<MemberPointsRule> delMemberPointsRule(String ruleId) {
		MemberPointsRule mpr=new MemberPointsRule();
		mpr.setRuleId(ruleId);
		mpr.setIsDeleted(1);
		return  memberPointsRuleService.updateMemberPointsRule(mpr, getOperator());
	}   
}
