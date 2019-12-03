package cn.com.shopec.mgt.marketing.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.marketing.model.Coupon;
import cn.com.shopec.core.marketing.service.CouponService;
import cn.com.shopec.core.member.model.Member;
import cn.com.shopec.core.member.service.MemberService;
import cn.com.shopec.core.system.model.SysUser;
import cn.com.shopec.core.system.service.DataDictItemService;
import cn.com.shopec.core.system.service.SysUserService;
import cn.com.shopec.mgt.common.BaseController;

@Controller
@RequestMapping("coupon")
public class CouponController extends BaseController {
	@Resource
	private CouponService couponService;
	@Resource
	private DataDictItemService dataDictItemService;
	@Resource
	private SysUserService sysUserService;

	@Resource
	private MemberService memberService;

	/**
	 * 优惠卷列表页面
	 * 
	 * @return
	 */
	@RequestMapping("/toCouponList")
	public String toCouponList() {
		return "marketing/coupon_list";
	}

	/**
	 * 优惠卷列表页面分页
	 * 
	 * @return
	 */
	@RequestMapping("/pageListCoupon")
	@ResponseBody
	public PageFinder<Coupon> pageListCoupon(@ModelAttribute("coupon") Coupon coupon, Query query) {
		Query q = new Query(query.getPageNo(), query.getPageSize(), coupon);
		return couponService.getMemberCouponPageList(q);
	}

	/**
	 * 优惠卷详情页面
	 * 
	 * @return
	 */
	@RequestMapping("/toCouponView")
	public String toCouponView(String couponNo, ModelMap modelMap) {
		Coupon coupon = couponService.getCoupon(couponNo);
		SysUser sysUser = sysUserService.detail(coupon.getIssuerId());
		if (sysUser != null) {
			coupon.setIssueorName(sysUser.getUserName());
		}
		Member member = memberService.getMember(coupon.getMemberNo());
		if (member != null) {
			coupon.setMemberName(member.getMemberName());
		}
		modelMap.put("coupon", coupon);
		return "marketing/coupon_view";
	}

	/**
	 * 优惠卷增加页面
	 * 
	 * @return
	 */
	@RequestMapping("/toAddCoupon")
	public String toAddCoupon(String[] memberNos,Model model){
	    StringBuffer buf = new StringBuffer();    
	    for(String no : memberNos){
	    	buf.append(",");
	    	buf.append(no);
	    }
		model.addAttribute("memberNos", buf.toString().replaceFirst(",", ""));
		return "marketing/coupon_add";
	}

	/**
	 * 优惠卷添加（coupon中memberNo含有多个逗号间隔）
	 * 
	 * @return
	 */
	@RequestMapping("/addCoupon")
	@ResponseBody
	public ResultInfo<Coupon> addCoupon(@ModelAttribute("coupon") Coupon coupon) {
		String[] memberArray = null;
		if (coupon.getMemberNos() != null && !coupon.getMemberNos().equals("")) {
			memberArray = coupon.getMemberNos().split(",");
		}
		coupon.setIssueMethod(3);// 设置手动发放标识
		return couponService.addCoupon(coupon, memberArray, getOperator());
	}

	/**
	 * 优惠卷启停用提交
	 * 
	 * @return
	 */
	@RequestMapping("/updateCouponAvailable")
	@ResponseBody
	public ResultInfo<Coupon> updateCouponAvailable(@ModelAttribute("coupon") Coupon coupon) {
		return couponService.updateCouponAvailable(coupon, getOperator());
	}

}
