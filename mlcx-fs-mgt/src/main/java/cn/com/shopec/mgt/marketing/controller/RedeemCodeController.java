package cn.com.shopec.mgt.marketing.controller;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.common.constants.Constant;
import cn.com.shopec.common.utils.JsonUtils;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.marketing.model.CouponPlan;
import cn.com.shopec.core.marketing.model.RedeemCode;
import cn.com.shopec.core.marketing.model.RedeemCouponPlan;
import cn.com.shopec.core.marketing.service.CouponPlanService;
import cn.com.shopec.core.marketing.service.RedeemCodeService;
import cn.com.shopec.core.system.model.SysUser;
import cn.com.shopec.core.system.service.SysUserService;
import cn.com.shopec.mgt.common.BaseController;

@Controller
@RequestMapping("redeemCode")
public class RedeemCodeController extends BaseController {
	@Resource
	private RedeemCodeService redeemCodeService;
	@Resource
    private SysUserService sysUserService;
	@Resource
	private CouponPlanService  couponPlanService;
	/**
	 * 兑换码列表页面
	 * 
	 * @return
	 */
	@RequestMapping("/toRedeemCodeList")
	public String toRedeemCodeList() {
		return "marketing/redeemCode_list";
	}

	/**
	 * 兑换码列表页面分页
	 * 
	 * @return
	 */
	@RequestMapping("/pageListRedeemCode")
	@ResponseBody
	public PageFinder<RedeemCode> pageListRedeemCode(@ModelAttribute("redeemCode") RedeemCode redeemCode, Query query) {
		Query q = new Query(query.getPageNo(), query.getPageSize(), redeemCode);
		return redeemCodeService.getRedeemCodePagedList(q);
	}

	/**
	 * 兑换码详情页面
	 * 
	 * @return
	 */
	@RequestMapping("/toRedeemCodeView")
	public String toRedeemCodeView(String redCode, ModelMap modelMap) {
		RedeemCode redeemCode = redeemCodeService.getRedeemCode(redCode);
		if(redeemCode != null ){
			SysUser sysUser = sysUserService.detail(redeemCode.getCensorId());
			if(sysUser != null){
				redeemCode.setCensorName(sysUser.getUserName());
			}
		}
		modelMap.put("redeemCode", redeemCode);
		return "marketing/redeemCode_view";
	}

	/**
	 * 兑换码列表页面分页
	 * 
	 * @return
	 */
	@RequestMapping("/pageViewCoupon")
	@ResponseBody
	public RedeemCode pageViewCoupon(@ModelAttribute("coupon") RedeemCode coupon, Query query) {
		return 	redeemCodeService.getRedeemCode(coupon.getRedCode());
	}
	
	/**
	 * 兑换码增加页面
	 * 
	 * @return
	 */
	@RequestMapping("/toAddRedeemCode")
	public String toAddRedeemCode() {
		return "marketing/redeemCode_add";
	}

	/**
	 * 兑换码添加
	 * 
	 * @return
	 */
	@RequestMapping("/addRedeemCode")
	@ResponseBody
	public ResultInfo<RedeemCode> addRedeemCode(@ModelAttribute("redeemCode") RedeemCode redeemCode) {
		List<RedeemCouponPlan> redeemCouponPlans = JsonUtils.parse2ListObject(redeemCode.getCouponPlanString(), RedeemCouponPlan.class);
		if(redeemCouponPlans != null){
			for (RedeemCouponPlan rcp : redeemCouponPlans) {
				if(rcp.getPlanNo() != null){//判断优惠券方案限制数量和已经发放的数量
					CouponPlan cp = couponPlanService.getCouponPlan(rcp.getPlanNo());
					if(cp != null && cp.getMaxQuantity() != null){
						if(cp.getExistingQuantity() == null){
							cp.setExistingQuantity(0);
						}
						if((cp.getMaxQuantity() - cp.getExistingQuantity()) < rcp.getRedQutity().intValue()){
							ResultInfo<RedeemCode> resultInfo =new ResultInfo<RedeemCode>();
							resultInfo .setCode(Constant.FAIL);
							resultInfo.setMsg("优惠券方案["+cp.getTitle()+"]的兑换数量不能大于可发放的数量！");
							return resultInfo;
						}
						
					}
				}
			}
		}
		redeemCode.setRedeemCouponPlans(redeemCouponPlans);
		return redeemCodeService.addRedeemCode(redeemCode, getOperator());
	}
	
	/**
	 * 兑换码修改页面
	 * 
	 * @return
	 */
	@RequestMapping("/toEditRedeemCode")
	public String toEditRedeemCode(String redCode, ModelMap modelMap) {
		RedeemCode redeemCode = redeemCodeService.getRedeemCode(redCode);
		modelMap.put("redeemCode", redeemCode);
		String planNos = "";
		String planTitle = "";
		List<RedeemCouponPlan> redeemCouponPlans = redeemCode.getRedeemCouponPlans();
		if(!redeemCouponPlans.isEmpty()){
			for (RedeemCouponPlan redeemCouponPlan : redeemCouponPlans) {
				planNos += redeemCouponPlan.getPlanNo() + ",";
				planTitle += redeemCouponPlan.getPlanNo()  + ",";
			}
			modelMap.put("planNos", planNos.substring(0, planNos.length()-1));
			modelMap.put("planTitle", planTitle.substring(0, planTitle.length()-1));
		}
		return "marketing/redeemCode_edit";
	}

	/**
	 * 兑换码修改或删除
	 * 
	 * @return
	 */
	@RequestMapping("/updateRedeemCode")
	@ResponseBody
	public ResultInfo<RedeemCode> updateOrDelRedeemCode(@ModelAttribute("redeemCode") RedeemCode redeemCode) {
		List<RedeemCouponPlan> redeemCouponPlans = JsonUtils.parse2ListObject(redeemCode.getCouponPlanString(), RedeemCouponPlan.class);
		if(redeemCouponPlans != null){
			for (RedeemCouponPlan rcp : redeemCouponPlans) {
				if(rcp.getPlanNo() != null){//判断优惠券方案限制数量和已经发放的数量
					CouponPlan cp = couponPlanService.getCouponPlan(rcp.getPlanNo());
					if(cp != null && cp.getMaxQuantity() != null){
						if(cp.getExistingQuantity() == null){
							cp.setExistingQuantity(0);
						}
						if((cp.getMaxQuantity() - cp.getExistingQuantity()) < rcp.getRedQutity().intValue()){
							ResultInfo<RedeemCode> resultInfo =new ResultInfo<RedeemCode>();
							resultInfo .setCode(Constant.FAIL);
							resultInfo.setMsg("优惠券方案["+cp.getTitle()+"]的兑换数量不能大于可发放的数量！");
							return resultInfo;
						}
						
					}
				}
			}
		}
		redeemCode.setRedeemCouponPlans(redeemCouponPlans);
		return redeemCodeService.updateRedeemCode(redeemCode, getOperator());
	}
	
	
	/**
	 * 兑换码审核页面
	 * 
	 * @return
	 */
	@RequestMapping("/toCensorRedeemCode")
	public String toCensorRedeemCode(String redCode, ModelMap modelMap) {
		RedeemCode redeemCode = redeemCodeService.getRedeemCode(redCode);
		modelMap.put("redeemCode", redeemCode);
		return "marketing/redeemCode_censor";
	}
	
	/**
	 * 兑换码审核操作
	 */
	@RequestMapping("/censorRedeemCode")
	@ResponseBody
	public ResultInfo<RedeemCode> censorRedeemCode(@ModelAttribute("redeemCode") RedeemCode redeemCode) {
		if(redeemCode.getCensorStatus() == 2){
			redeemCode.setIsAvailable(0);
			redeemCode.setAvailableUpdateTime(new Date());
		} else {
			redeemCode.setIsAvailable(1);
			redeemCode.setAvailableUpdateTime(new Date());
		}
		return redeemCodeService.updateRedeemCode(redeemCode, getOperator());
	}
	
}
