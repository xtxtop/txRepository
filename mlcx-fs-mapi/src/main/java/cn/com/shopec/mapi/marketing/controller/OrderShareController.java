package cn.com.shopec.mapi.marketing.controller;


import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.common.constants.Constant;
import cn.com.shopec.common.utils.ECCalculateUtils;
import cn.com.shopec.common.utils.ECDateUtils;
import cn.com.shopec.common.utils.GenerateInvitationCodeUtil;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.marketing.model.Accounts;
import cn.com.shopec.core.marketing.model.Coupon;
import cn.com.shopec.core.marketing.model.CouponPlan;
import cn.com.shopec.core.marketing.model.OrderShareRecord;
import cn.com.shopec.core.marketing.model.OrderShareSetting;
import cn.com.shopec.core.marketing.model.PricingPackage;
import cn.com.shopec.core.marketing.service.AccountsService;
import cn.com.shopec.core.marketing.service.CouponPlanService;
import cn.com.shopec.core.marketing.service.CouponService;
import cn.com.shopec.core.marketing.service.OrderShareRecordService;
import cn.com.shopec.core.marketing.service.OrderShareSettingService;
import cn.com.shopec.core.marketing.service.PricingPackageService;
import cn.com.shopec.core.member.model.Member;
import cn.com.shopec.core.member.model.MemberLevel;
import cn.com.shopec.core.member.service.MemberLevelService;
import cn.com.shopec.core.member.service.MemberService;
import cn.com.shopec.core.order.model.Order;
import cn.com.shopec.core.order.model.PricingPackOrder;
import cn.com.shopec.core.order.service.OrderService;
import cn.com.shopec.core.order.service.PricingPackOrderService;
import cn.com.shopec.core.system.model.SysParam;
import cn.com.shopec.core.system.service.SysParamService;
import cn.com.shopec.mapi.common.controller.BaseController;
import cn.com.shopec.mapi.marketing.vo.OrderShareSettingForAppVo;
import cn.com.shopec.mapi.marketing.vo.OrderShareSettingVo;
import cn.com.shopec.mapi.marketing.vo.OrderShareVo;

@Controller
@RequestMapping("/app/orderShare")
public class OrderShareController extends BaseController{
	
	@Resource
	private OrderService orderService;
	@Resource
	private SysParamService sysParamService;
	@Resource
	private OrderShareRecordService orderShareRecordService;
	@Resource
	private MemberService memberService;
	@Resource
	private MemberLevelService memberLevelService;
	@Resource
	private PricingPackageService pricingPackageService;
	@Resource
	private PricingPackOrderService pricingPackOrderService;
	@Resource
	private AccountsService accountsService;
	@Resource
	private CouponService couponService;
	@Resource
	private CouponPlanService couponPlanService;
	@Resource
	private OrderShareSettingService orderShareSettingService;
	
	@Value("${image_path}")
	private String imgPath;
	
	
	/**
	 * 订单是否可分享
	 * @param orderNo
	 * @return
	 */
	@RequestMapping("isOrderShare")
	@ResponseBody
	public ResultInfo<OrderShareSettingForAppVo> isOrderShare(String orderNo) {
		ResultInfo<OrderShareSettingForAppVo> result = new ResultInfo<OrderShareSettingForAppVo>();
		result.setCode(Constant.FAIL);
		if (orderNo==null||"".equals(orderNo)) {
			result.setCode(Constant.FAIL);
			result.setMsg("订单号为空");
			return result;
		}
		Order order = orderService.getOrder(orderNo);
		if (order==null) {
			result.setCode(Constant.FAIL);
			result.setMsg("订单不存在");
			return result;
		}
		
		SysParam s7 = sysParamService.getByParamKey("IS_SHARE");
		if(s7 != null && "0".equals(s7.getParamValue())){
			result.setCode(Constant.FAIL);
			result.setMsg("分享功能没有开启");
			return result;
		}
		//大鹏业务，订单金额不满足条件，不能分享
		Double orderAmount = order.getOrderAmount();
		String orderAmountStr = sysParamService.getParamValueByParamKey("order_share_min_order_amount");
		if (orderAmountStr!=null&&!"".equals(orderAmountStr)) {
			Double orderAmountSys = Double.parseDouble(orderAmountStr);
			if (orderAmount.compareTo(orderAmountSys)<0) {
				result.setCode(Constant.FAIL);
				return result;
			}
		}
//		Date finishTime = order.getFinishTime();
//		String daysStr = sysParamService.getParamValueByParamKey("order_share_available_days");
//		if (daysStr!=null&&!"".equals(daysStr)) {
//			int daySys = Integer.parseInt(daysStr);
//			Date nowTime = new Date();
//			int diffDays = ECDateUtils.differDays2(nowTime, finishTime).intValue();
//			if (diffDays>daySys) {
//				result.setCode(Constant.FAIL);
//				result.setMsg("订单分享过了有效期");
//				return result;
//			}
//		}
		OrderShareSetting queryOrderShareSetting = new OrderShareSetting();
		queryOrderShareSetting.setIsAvailable(1);
		List<OrderShareSetting> orderShareList = orderShareSettingService.getOrderShareSettingList(new Query(queryOrderShareSetting));
		if (orderShareList.size()>0) {
			OrderShareSetting orderShareSetting = orderShareList.get(0);
			if (orderShareSetting!=null) {
				String orderShareMaxTime = sysParamService.getParamValueByParamKey("order_share_max_time");
				OrderShareSettingForAppVo settingForAppVo = new OrderShareSettingForAppVo();
				settingForAppVo.setOrderShareName(orderShareSetting.getOrderShareName());
				settingForAppVo.setOrderShareMemo(orderShareSetting.getOrderShareMemo());
				settingForAppVo.setOrderShareIconUrl(imgPath+"/"+orderShareSetting.getOrderShareIconUrl());
				settingForAppVo.setOrderShareUrl(orderShareSetting.getLinkUrl());
				settingForAppVo.setOrderShareRedPacketNum(orderShareMaxTime);
				result.setData(settingForAppVo);
				result.setCode(Constant.SECCUESS);
			}
		}
		return result;
	}
	/**
	 * 订单分享领取优惠
	 * @param orderNo
	 * @param mobilePhone
	 * @return
	 */
	@RequestMapping("getOrderShareConfig")
	@ResponseBody
	public ResultInfo<OrderShareSettingVo> orderShareGetOffers(String orderNo){
		ResultInfo<OrderShareSettingVo> resultInfo = new ResultInfo<OrderShareSettingVo>();
		resultInfo.setCode(Constant.FAIL);
		OrderShareSetting queryOrderShareSetting = new OrderShareSetting();
		queryOrderShareSetting.setIsAvailable(1);
		List<OrderShareSetting> orderShareList = orderShareSettingService.getOrderShareSettingList(new Query(queryOrderShareSetting));
		if (orderShareList.size()>0) {
			OrderShareSetting orderShareSetting = orderShareList.get(0);
			if (orderShareSetting!=null) {
				OrderShareSettingVo vo = new OrderShareSettingVo();
				vo.setOrderShareName(orderShareSetting.getOrderShareName());
				String days = sysParamService.getParamValueByParamKey("order_share_available_days");
				if (days!=null&&!"".equals(days)) {
					orderShareSetting.setOrderShareContent(orderShareSetting.getOrderShareContent().replace("days", days));
				}
				String orderShareMaxTime = sysParamService.getParamValueByParamKey("order_share_max_time");
				if (orderShareMaxTime!=null&&!"".equals(orderShareMaxTime)) {
					orderShareSetting.setOrderShareContent(orderShareSetting.getOrderShareContent().replace("num", orderShareMaxTime));
				}
				vo.setOrderShareContent(delHTMLTag(orderShareSetting.getOrderShareContent()));
				vo.setOrderSharePicUrl(orderShareSetting.getOrderSharePicUrl());
				
				Order order = orderService.getOrder(orderNo);
				if (order==null) {
					resultInfo.setCode(Constant.FAIL);
					resultInfo.setMsg("订单不存在");
					return resultInfo;
				}
				Double orderAmount = order.getOrderAmount();
				String orderAmountStr = sysParamService.getParamValueByParamKey("order_share_min_order_amount");
				if (orderAmountStr!=null&&!"".equals(orderAmountStr)) {
					Double orderAmountSys = Double.parseDouble(orderAmountStr);
					if (orderAmount.compareTo(orderAmountSys)<0) {
						vo.setIsOrderGetOffer("0");
					}else{
						vo.setIsOrderGetOffer("1");
					}
				}
				resultInfo.setCode(Constant.SECCUESS);
				resultInfo.setData(vo);
			}else{
				resultInfo.setMsg("订单分享页配置不存在");
			}
		}else{
			resultInfo.setMsg("订单分享页配置不存在");
		}
		return resultInfo;
	}
	/**
	 * 订单分享领取优惠
	 * @param orderNo
	 * @param mobilePhone
	 * @return
	 */
	@RequestMapping("orderShareGetOffers")
	@ResponseBody
	public ResultInfo<OrderShareVo> orderShareGetOffers(String orderNo,String mobilePhone) {
		ResultInfo<OrderShareVo> result = new ResultInfo<OrderShareVo>();
		if (orderNo==null||"".equals(orderNo)) {
			result.setCode(Constant.FAIL);
			result.setMsg("订单号为空");
			return result;
		}
		if (mobilePhone==null||"".equals(mobilePhone)) {
			result.setCode(Constant.FAIL);
			result.setMsg("手机号为空");
			return result;
		}else{
			String reg ="^(13[0-9]|15[0-9]|17[0-9]|18[0-9]|14[57])[0-9]{8}$";
			Pattern regex = Pattern.compile(reg);
	        Matcher matcher = regex.matcher(mobilePhone);
	        boolean flag = matcher.matches();
			if (!flag) {
				result.setCode(Constant.FAIL);
				result.setMsg("手机号不正确");
				return result;
			}
		}
		Order order = orderService.getOrder(orderNo);
		if (order==null) {
			result.setCode(Constant.FAIL);
			result.setMsg("订单不存在");
			return result;
		}
		Date finishTime = order.getFinishTime();
		String daysStr = sysParamService.getParamValueByParamKey("order_share_available_days");
		if (daysStr!=null&&!"".equals(daysStr)) {
			int daySys = Integer.parseInt(daysStr);
			Date nowTime = new Date();
			int diffDays = ECDateUtils.differDays2(nowTime, finishTime).intValue();
			if (diffDays>=daySys) {
				result.setCode(Constant.FAIL);
				result.setMsg("您来晚了，活动已经结束了");
				return result;
			}
		}
		Double orderAmount = order.getOrderAmount();
		String orderAmountStr = sysParamService.getParamValueByParamKey("order_share_min_order_amount");
		if (orderAmountStr!=null&&!"".equals(orderAmountStr)) {
			Double orderAmountSys = Double.parseDouble(orderAmountStr);
			if (orderAmount.compareTo(orderAmountSys)<0) {
				result.setCode(Constant.FAIL);
				result.setMsg("您来晚了，活动已经结束了");
				return result;
			}
		}
		//查询orderNo对应的订单已领取优惠的次数
		OrderShareRecord queryOrderShareRecord = new OrderShareRecord();
		queryOrderShareRecord.setOrderNo(orderNo);
		List<OrderShareRecord> orderShareRecordList = orderShareRecordService.getOrderShareRecordList(new Query(queryOrderShareRecord));
		//订单分享后可被领取最大次数
		String orderShareMaxTimeSys = sysParamService.getParamValueByParamKey("order_share_max_time");
		int orderShareMaxTime = Integer.parseInt(orderShareMaxTimeSys);
		//达到最大领取次数
		if (orderShareRecordList.size()>=orderShareMaxTime) {
			result.setCode(Constant.FAIL);
			result.setMsg("您来晚了，优惠券已被抢光了");
			return result;
		}
		Member member = memberService.getMemberByPhone(mobilePhone);
		if (member!=null) {
			OrderShareRecord orderShareRecord = orderShareRecordService.getOrderShareRecord(orderNo, member.getMemberNo());
			if (orderShareRecord!=null) {
				result.setCode("2");
				result.setMsg("您已领取过优惠");
				return result;
			}
		}else {
			//注册会员
			Member memberRegister = new Member();
			memberRegister.setMobilePhone(mobilePhone);	
			Member memberInvited = memberService.getMember(order.getMemberNo());
			if (memberInvited != null) {
				memberRegister.setRefereeId(memberInvited.getMemberNo());
				String memberNo = memberService.generatePK();
				memberRegister.setMemberNo(memberNo);
				// 生成会员自己的推荐码
				String memberInvitationCode = GenerateInvitationCodeUtil.toRandomCode(Long.valueOf(memberNo));
				memberRegister.setInvitationCode(memberInvitationCode);
			} 
			memberRegister.setCensorStatus(0);
			// 新注册用户积分值和会员等级设置
			memberRegister.setMemberPointsValues(0);
			MemberLevel ml = new MemberLevel();
			ml.setIsAvailable(1);
			ml.setIsDeleted(0);
			List<MemberLevel> mList = memberLevelService.getMemberLevelList(new Query(ml));
			if (mList != null && mList.size() > 0) {
				memberRegister.setMemberLevelId(mList.get(0).getMemberLevelId());
			}
			ResultInfo<Member> resultInfo = memberService.addMember(memberRegister, null);
			if ("1".equals(resultInfo.getCode())) {
				member = resultInfo.getData();
			}else {
				result.setCode(Constant.FAIL);
				result.setMsg("手机号为"+mobilePhone+"的会员注册失败");
				return result;
			}
		}
		boolean flag = false;
		//订单分享领取的优惠有效期(从领取时开始)
		String availableDaysStr = sysParamService.getParamValueByParamKey("order_share_offers_available_days");
		int availableDays = 7;
		if (availableDaysStr!=null&&!"".equals(availableDaysStr)) {
			availableDays = Integer.parseInt(availableDaysStr);
		}
		//订单分享领取的优惠类型（1、套餐 2、优惠券）
		String offersType = sysParamService.getParamValueByParamKey("order_share_offers_type");
		OrderShareVo orderShareVo = new OrderShareVo();
		OrderShareRecord orderShareRecord = new OrderShareRecord();
		if (offersType.equals("1")) {
			PricingPackage pack = new PricingPackage();
			pack.setPackageType(4);
			pack.setIsAvailable(1);
			List<PricingPackage> packageList = pricingPackageService.getPricingPackageList(new Query(pack));
			if (packageList.size()==0) {
				result.setCode(Constant.FAIL);
				result.setMsg("套餐不存在");
				return result;
			}
			PricingPackage pricePackage = packageList.get(0);
			PricingPackOrder pricingPackOrder = new PricingPackOrder();
			pricingPackOrder.setMemberName(member.getMemberName());
			pricingPackOrder.setMemberNo(member.getMemberNo());
			pricingPackOrder.setMobilePhone(member.getMobilePhone());
			pricingPackOrder.setPackageId(pricePackage.getPackageNo());
			pricingPackOrder.setPackageName(pricePackage.getPackageName());
			pricingPackOrder.setUserdMinute(0);
			pricingPackOrder.setPaymentMethod(4);//赠送的套餐，支付方式为其他。
			pricingPackOrder.setPayStatus(1);;
			pricingPackOrder.setIsAvailable(1);
			pricingPackOrder.setPackAmount(pricePackage.getPrice());
			pricingPackOrder.setPackOrderAmount(pricePackage.getPackAmount());
			pricingPackOrder.setUseredOrderAmount(0d);
			pricingPackOrder.setPayableAmount(pricePackage.getPrice());
			pricingPackOrder.setInvoiceStatus(0);
			//有效期的起止时间
			pricingPackOrder.setVailableTime1(ECDateUtils.getCurrentDateTime());
			Date availableTime2 = ECDateUtils.getDateAfter(pricingPackOrder.getVailableTime1(), availableDays);
			pricingPackOrder.setVailableTime2(availableTime2);
			ResultInfo<PricingPackOrder> res = pricingPackOrderService.addPricingPackOrder(pricingPackOrder, getOperator());
			if(res.getCode().equals(Constant.SECCUESS)){
				orderShareVo.setDiscountAmount(pricingPackOrder.getPackOrderAmount().toString());
				orderShareVo.setAvailableTime1(ECDateUtils.formatDate(pricingPackOrder.getVailableTime1(),"yyyy-MM-dd"));
				orderShareVo.setAvailableTime2(ECDateUtils.formatDate(pricingPackOrder.getVailableTime2(),"yyyy-MM-dd"));
				
				//查询我的余额
				Query qr = new Query();
				PricingPackOrder ppor = new PricingPackOrder();
				ppor.setPayStatus(1);// 已经支付的
				ppor.setIsAvailable(1);// 可用
				ppor.setNowTime(new Date());
				ppor.setMemberNo(pricingPackOrder.getMemberNo());
				qr.setQ(ppor);
				List<PricingPackOrder>  pporList = pricingPackOrderService.getPricingPackOrderListBypo(qr);
				Double poa=0.0;
				Double uoa=0.0;
				for (PricingPackOrder packOrder : pporList) {
					if(!packOrder.getPackageId().equals(pricingPackOrder.getPackageId())){
						if(packOrder.getPackOrderAmount() != null ){
							poa=ECCalculateUtils.add(poa,packOrder.getPackOrderAmount());
						}
						if(packOrder.getUseredOrderAmount() != null){
							uoa=ECCalculateUtils.add(uoa,packOrder.getUseredOrderAmount());
						}
					}
				}
				//金额套餐赠送成功后给记账表添加记录
				Double ye = ECCalculateUtils.sub(poa, uoa);
				Accounts ac = new Accounts();
				ac.setMemberNo(pricingPackOrder.getMemberNo());
				ac.setBusinessNo(pricingPackOrder.getPackOrderNo());
				ac.setBusinessType(2);
				ac.setAccountType(2);
				ac.setAccountMoney(pricingPackOrder.getPackOrderAmount());
				ac.setAccountBeforeMoney(ye);
				ac.setAccountOverMoney(ECCalculateUtils.add(ye, ac.getAccountMoney()));
				ac.setAccountTime(new Date());
				accountsService.addAccounts(ac, null);
				
				flag = true;
				//添加优惠领取记录
				orderShareRecord.setBizObjNo(res.getData().getPackOrderNo());
				orderShareRecord.setBizObjType(1);
			}
		}else if (offersType.equals("2")) {
			CouponPlan queryCouponPlan = new CouponPlan();
			queryCouponPlan.setCouponType(2);
			queryCouponPlan.setIsAvailable(1);
			List<CouponPlan> couponPlans = couponPlanService.getCouponPlanList(new Query(queryCouponPlan));
			if (couponPlans.size()==0) {
				result.setCode(Constant.FAIL);
				result.setMsg("优惠券方案不存在");
				return result;
			}
			CouponPlan couponPlan = couponPlans.get(0);
			
			Coupon coupon = new Coupon();
			coupon.setMemberNo(member.getMemberNo());
			//开始为选择的用户发放优惠券
			String planCode = sysParamService.getParamValueByParamKey("COUPON_CODE");
			coupon.setCouponNo(planCode + couponService.generatePK());
			coupon.setPlanNo(couponPlan.getPlanNo());
			coupon.setTitle(couponPlan.getTitle());
			coupon.setCouponType(couponPlan.getCouponType());
			coupon.setCouponMethod(couponPlan.getCouponMethod());
			coupon.setDiscount(couponPlan.getDiscount());
			coupon.setDiscountAmount(couponPlan.getDiscountAmount());
			Date now = new Date();
			coupon.setVailableTime1(now);
			coupon.setVailableTime2(ECDateUtils.getDateAfter(now,availableDays));
			coupon.setAvailableDays(availableDays);
			// 设置发放时间为当前时间
			coupon.setIssueTime(now);

			// 填充其他默认值
			coupon.setIsAvailable(1);
			coupon.setAvailableUpdateTime(now);
			coupon.setUsedStatus(0);
			//设置手动发放优惠券时的发放人id
			coupon.setIssueMethod(2);
			ResultInfo<Coupon> res = couponService.addCoupon(coupon, null);
			if ("1".equals(res.getCode())) {
				
				if (coupon.getDiscountAmount()!=null) {
					orderShareVo.setDiscountAmount(coupon.getDiscountAmount().toString());
				}else {
					orderShareVo.setDiscount(coupon.getDiscount().toString());
				}
				orderShareVo.setAvailableTime1(ECDateUtils.formatDate(coupon.getVailableTime1(),"yyyy-MM-dd"));
				orderShareVo.setAvailableTime2(ECDateUtils.formatDate(coupon.getVailableTime2(),"yyyy-MM-dd"));
				//求出这次发放后优惠券的总数量
				int existingQuantity = couponPlan.getExistingQuantity() == null ? 0 : couponPlan.getExistingQuantity();
				existingQuantity = existingQuantity + 1;
				//更新方案表信息
				CouponPlan couponPlanTemp = new CouponPlan();
				couponPlanTemp.setPlanNo(couponPlan.getPlanNo());
				couponPlanTemp.setExistingQuantity(existingQuantity);
				couponPlanService.update(couponPlanTemp, null);
				
				flag = true;
				//添加优惠领取记录
				orderShareRecord.setBizObjNo(res.getData().getCouponNo());
				orderShareRecord.setBizObjType(2);
			}
		}
		if (flag) {
			orderShareRecord.setMemberNo(member.getMemberNo());
			orderShareRecord.setMobilePhone(member.getMobilePhone());
			orderShareRecord.setOrderNo(orderNo);
			orderShareRecordService.addOrderShareRecord(orderShareRecord, null);
		}
		result.setCode(Constant.SECCUESS);
		result.setMsg("领取成功");
		result.setData(orderShareVo);
		return result;	
	}
	private String delHTMLTag(String htmlStr){ 
        String regEx_script="<script[^>]*?>[\\s\\S]*?<\\/script>"; //定义script的正则表达式 
        String regEx_style="<style[^>]*?>[\\s\\S]*?<\\/style>"; //定义style的正则表达式 
        String regEx_html="<[^>]+>"; //定义HTML标签的正则表达式 
         
        Pattern p_script=Pattern.compile(regEx_script,Pattern.CASE_INSENSITIVE); 
        Matcher m_script=p_script.matcher(htmlStr); 
        htmlStr=m_script.replaceAll(""); //过滤script标签 
         
        Pattern p_style=Pattern.compile(regEx_style,Pattern.CASE_INSENSITIVE); 
        Matcher m_style=p_style.matcher(htmlStr); 
        htmlStr=m_style.replaceAll(""); //过滤style标签 
         
        Pattern p_html=Pattern.compile(regEx_html,Pattern.CASE_INSENSITIVE); 
        Matcher m_html=p_html.matcher(htmlStr); 
        htmlStr=m_html.replaceAll(""); //过滤html标签 

        return htmlStr.trim(); //返回文本字符串 
    } 
	
}
