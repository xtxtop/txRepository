package cn.com.shopec.mgt.marketing.controller;

import java.util.ArrayList;
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
import cn.com.shopec.common.utils.ECDateUtils;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.marketing.model.CouponPlan;
import cn.com.shopec.core.marketing.service.CouponPlanService;
import cn.com.shopec.core.system.model.SysUser;
import cn.com.shopec.core.system.service.DataDictItemService;
import cn.com.shopec.core.system.service.SysUserService;
import cn.com.shopec.mgt.common.BaseController;

@Controller
@RequestMapping("couponPlan")
public class CouponPlanController extends BaseController {
	@Resource
	private CouponPlanService couponPlanService;
	@Resource
	private DataDictItemService dataDictItemService;
	@Resource
    private SysUserService sysUserService;
	/**
	 * 优惠卷列表页面
	 * 
	 * @return
	 */
	@RequestMapping("/toCouponPlanList")
	public String toCouponPlanList() {
		return "marketing/couponPlan_list";
	}

	/**
	 * 优惠卷列表页面分页
	 * 
	 * @return
	 */
	@RequestMapping("/pageListCouponPlan")
	@ResponseBody
	public PageFinder<CouponPlan> pageListCouponPlan(@ModelAttribute("couponPlan") CouponPlan couponPlan, Query query) {
		Query q = new Query(query.getPageNo(), query.getPageSize(), couponPlan);
		return couponPlanService.getCouponPlanPagedList(q);
	}
	
	/**
	 * 查询会员发放优惠券数据
	 * 已审核、可用且在有效期内
	 */
	@RequestMapping("/pageListCouponPlanNs")
	@ResponseBody
	public PageFinder<CouponPlan> pageListCouponPlanNs(@ModelAttribute("couponPlan") CouponPlan couponPlan,Query query) {
		couponPlan.setAvailableTime2Start(new Date());
		Query q = new Query(query.getPageNo(), query.getPageSize(), couponPlan);
		return couponPlanService.queryCouponPlanPagedList(q);
	}

	/**
	 * 兑换卷详情下的优惠卷列表页面分页
	 * 
	 * @return
	 */
	@RequestMapping("/getPageForRedemmCodePlan")
	@ResponseBody
	public PageFinder<CouponPlan> getPageForRedemmCodePlan(String redCode, Query query) {
		PageFinder<CouponPlan> page = new PageFinder<CouponPlan>();
		List<CouponPlan> list = null;
		if(redCode != null && !redCode.equals("")){
			list =  couponPlanService.getCodePlanByRedCode(redCode);
			page.setData(list);
			page.setRowCount(list.size());
		}
		if(list == null){
			page.setData(new ArrayList<CouponPlan>());
			page.setRowCount(0);
		}
		return page;
	}
	
	
	/**
	 * 优惠卷详情页面
	 * 
	 * @return
	 */
	@RequestMapping("/toCouponPlanView")
	public String toCouponPlanView(String planNo, ModelMap modelMap) {
		CouponPlan couponPlan = couponPlanService.getCouponPlan(planNo);
		if(couponPlan != null ){
			SysUser sysUser=sysUserService.detail(couponPlan.getCensorId());
			if(sysUser != null){
				couponPlan.setCensorName(sysUser.getUserName());
			}
			if(couponPlan.getCarModelName() != null){
				couponPlan.setCarModelName(couponPlan.getCarModelName().replaceAll(",", ", "));
			}
			if(couponPlan.getCityName() != null){
				couponPlan.setCityName(couponPlan.getCityName().replaceAll(",", ", "));
			}
		}
		modelMap.put("couponPlan", couponPlan);
		return "marketing/couponPlan_view";
	}

	/**
	 * 优惠卷列表页面分页
	 * 
	 * @return
	 */
	@RequestMapping("/pageViewCoupon")
	@ResponseBody
	public CouponPlan pageViewCoupon(@ModelAttribute("coupon") CouponPlan coupon, Query query) {
		return 	couponPlanService.getCouponPlan(coupon.getPlanNo());
	}
	
	/**
	 * 优惠卷增加页面
	 * 
	 * @return
	 */
	@RequestMapping("/toAddCouponPlan")
	public String toAddCouponPlan() {
		return "marketing/couponPlan_add";
	}

	/**
	 * 优惠卷添加
	 * 
	 * @return
	 */
	@RequestMapping("/addCouponPlan")
	@ResponseBody
	public ResultInfo<CouponPlan> addCouponPlan(@ModelAttribute("couponPlan") CouponPlan couponPlan) {
		ResultInfo<CouponPlan> result = new ResultInfo<>();
		if(couponPlan!=null&&couponPlan.getAvailableDays()!=null){
			couponPlan.setVailableTime1(ECDateUtils.getCurrentDateTime());
			Date vailableTime2 = ECDateUtils.getDateAfter(couponPlan.getVailableTime1(), couponPlan.getAvailableDays());
			couponPlan.setVailableTime2(vailableTime2);
		}
		if (couponPlan!=null&&new Integer(2).equals(couponPlan.getCouponType())) {
			CouponPlan queryCouponPlan = new CouponPlan();
			queryCouponPlan.setCouponType(2);
			queryCouponPlan.setIsAvailable(1);
			List<CouponPlan> couponPlans = couponPlanService.getCouponPlanList(new Query(queryCouponPlan));
			if (couponPlans.size()>0) {
				result.setCode(Constant.FAIL);
				result.setMsg("订单分享类优惠券只能添加一个");
				return result;
			}
		}
		//初始化审核状态：未审核
		couponPlan.setCensorStatus(0);
		return couponPlanService.addCouponPlan(couponPlan, getOperator());
	}
	
	
	/**
	 * 优惠卷修改页面
	 * 
	 * @return
	 */
	@RequestMapping("/toEditCouponPlan")
	public String toEditCouponPlan(String planNo, ModelMap modelMap) {
		CouponPlan couponPlan = couponPlanService.getCouponPlan(planNo);
		modelMap.put("couponPlan", couponPlan);
		return "marketing/couponPlan_edit";
	}

	/**
	 * 优惠卷修改或删除
	 * 
	 * @return
	 */
	@RequestMapping("/updateOrDelCouponPlan")
	@ResponseBody
	public ResultInfo<CouponPlan> updateOrDelCouponPlan(@ModelAttribute("couponPlan") CouponPlan couponPlan) {
		return couponPlanService.updateForBusiness(couponPlan, getOperator());
	}
	
	/**
	 * 优惠卷审核页面
	 * 
	 * @return
	 */
	@RequestMapping("/toCensorCouponPlan")
	public String toCensorCouponPlan(String planNo, ModelMap modelMap) {
		CouponPlan couponPlan = couponPlanService.getCouponPlan(planNo);
		if(couponPlan != null ){
			if(couponPlan.getCarModelName() != null){
				couponPlan.setCarModelName(couponPlan.getCarModelName().replaceAll(",", ", "));
			}
			if(couponPlan.getCityName() != null){
				couponPlan.setCityName(couponPlan.getCityName().replaceAll(",", ", "));
			}
		}
		modelMap.put("couponPlan", couponPlan);
		return "marketing/couponPlan_censor";
	}
	
	/**
	 * 优惠卷启审核
	 * 
	 * @return
	 */
	@RequestMapping("/updateCouponPlanCensor")
	@ResponseBody
	public ResultInfo<CouponPlan> updateCouponPlanCensor(@ModelAttribute("coupon") CouponPlan couponPlan) {
		return couponPlanService.updateCouponPlanCensor(couponPlan, getOperator());
	}
	
	/**
	 * 优惠卷启停用提交
	 * 
	 * @return
	 */
	@RequestMapping("/updateCouponPlanAvailable")
	@ResponseBody
	public ResultInfo<CouponPlan> updateCouponAvailable(@ModelAttribute("couponPlan") CouponPlan couponPlan) {
		return couponPlanService.updateCouponPlanAvailable(couponPlan, getOperator());
	}
}
