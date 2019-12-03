package cn.com.shopec.mapi.test.controller;

import java.util.List;
import java.util.SortedMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.finace.model.PaymentRecord;
import cn.com.shopec.core.finace.service.PaymentRecordService;
import cn.com.shopec.core.member.model.Member;
import cn.com.shopec.core.member.model.MemberLevel;
import cn.com.shopec.core.member.model.MemberPointsRecord;
import cn.com.shopec.core.member.model.MemberPointsRule;
import cn.com.shopec.core.member.service.MemberLevelService;
import cn.com.shopec.core.member.service.MemberPointsRecordService;
import cn.com.shopec.core.member.service.MemberPointsRuleService;
import cn.com.shopec.core.member.service.MemberService;
import cn.com.shopec.core.order.model.Order;
import cn.com.shopec.core.order.model.PricingPackOrder;
import cn.com.shopec.core.order.service.OrderService;
import cn.com.shopec.core.order.service.PricingPackOrderService;
import cn.com.shopec.core.pay.PayService;
import cn.com.shopec.mapi.common.controller.BaseController;
/**
 *将之前的订单和套餐购买添加积分记录
 * */
@Controller
@RequestMapping("/app/test")
public class TestController extends BaseController{
	
	@Resource
	private PaymentRecordService paymentRecordService;
	@Resource
	private PayService payService;
	@Resource
	private MemberPointsRuleService memberPointsRuleService;
	@Resource
	private PricingPackOrderService pricingPackOrderService;
	@Resource
	private MemberService memberService;
	@Resource
	private OrderService orderService;
	@Resource
	private MemberPointsRecordService memberPointsRecordService;
	@Resource
	private MemberLevelService memberLevelService;

	
	@RequestMapping("/addOrderRe")
	@ResponseBody
	public String  addPricingOrderRe(){
		List<PaymentRecord> payList=paymentRecordService.getPaymentRecordList(new Query());
		for(PaymentRecord p:payList){
			payOverAddRecord(p);
		}
		return "success";
	}
	public void payOverAddRecord(PaymentRecord p){
		if(!p.getBizObjType().equals(3)){
			MemberPointsRecord mpr=new MemberPointsRecord();
			Member member=new Member();
			if(p.getBizObjType().equals(1)){//套餐订单
				mpr.setBusinessType(2);//套餐支付
				if(pricingPackOrderService.getPricingPackOrder(p.getBizObjNo())!=null){
					member=memberService.getMember(pricingPackOrderService.getPricingPackOrder(p.getBizObjNo()).getMemberNo());
				}
				mpr.setRecordMemo("套餐支付获得积分");
			}else if(p.getBizObjType().equals(2)){//订单
				mpr.setBusinessType(1);//订单
				if(orderService.getOrder(p.getBizObjNo())!=null){
					member=memberService.getMember(orderService.getOrder(p.getBizObjNo()).getMemberNo());
				}
				mpr.setRecordMemo("订单支付获得积分");
			}
			mpr.setPointsType(0);//会员经验积分
			mpr.setOpType(1);//增加积分
			//获取订单支付积分规则
			Query q=new Query();
			MemberPointsRule mpRule=new MemberPointsRule();
			mpRule.setBusinessType(2);
			mpRule.setIsAvailable(1);
			mpRule.setIsDefault(1);
			mpRule.setIsDeleted(0);
			q.setQ(mpRule);
			List<MemberPointsRule> mpRuleResultList=memberPointsRuleService.getMemberPointsRuleList(q);
			Integer pointsValue=0;
			if(mpRuleResultList!=null&&mpRuleResultList.size()>0){
				Double amount=p.getPayableAmount();
				String val=amount.toString().substring(0, amount.toString().lastIndexOf("."));
				Integer amount1=Integer.parseInt(val);
				pointsValue=mpRuleResultList.get(0).getPointsValue()*amount1;
			}
			mpr.setPointsValue(pointsValue);
			mpr.setBusinessData(p.getBizObjNo());
			mpr.setMemberNo(member.getMemberNo());
			if(mpr.getPointsValue()>0){
				List<MemberPointsRecord> list=memberPointsRecordService.getMemberPointsRecordList(new Query(mpr));
				if(list==null||list.size()<=0){
					memberPointsRecordService.addMemberPointsRecord(mpr, null);
					if(member!=null){
						//会员当前积分值
						if(member.getMemberPointsValues()==null){
							member.setMemberPointsValues(0);
						}
						member.setMemberPointsValues(member.getMemberPointsValues()+mpr.getPointsValue());
						Integer pointsValueLevel=memberLevelService.getNowLevelPoints(member.getMemberPointsValues());
						MemberLevel mLevelSearch=new MemberLevel();
						mLevelSearch.setIsAvailable(1);
						mLevelSearch.setIsDeleted(0);
						mLevelSearch.setUpgradePoint(pointsValueLevel);
						List<MemberLevel> mLevelList=memberLevelService.getMemberLevelList(new Query(mLevelSearch));
						if(mLevelList!=null&&mLevelList.size()>0){
							//会员等级
							member.setMemberLevelId(mLevelList.get(0).getMemberLevelId());
						}
						Member mUp=new Member();
						mUp.setMemberNo(member.getMemberNo());
						mUp.setMemberPointsValues(member.getMemberPointsValues());
						mUp.setMemberLevelId(member.getMemberLevelId());
						memberService.updateMember(mUp, null);
					}
				}
			}
	}
	}
}
