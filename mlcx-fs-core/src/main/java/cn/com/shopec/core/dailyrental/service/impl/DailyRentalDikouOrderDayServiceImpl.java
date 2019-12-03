package cn.com.shopec.core.dailyrental.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.com.shopec.common.utils.ECCalculateUtils;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.dailyrental.model.OrderDay;
import cn.com.shopec.core.dailyrental.service.DailyRentalDikouOrderDayService;
import cn.com.shopec.core.dailyrental.vo.CouponVo;
import cn.com.shopec.core.marketing.model.Coupon;
import cn.com.shopec.core.marketing.model.CouponPlan;
import cn.com.shopec.core.marketing.service.CouponPlanService;
import cn.com.shopec.core.marketing.service.CouponService;
@Service
public class DailyRentalDikouOrderDayServiceImpl implements DailyRentalDikouOrderDayService {
	@Resource
	private CouponService couponService;
	@Resource
	private CouponPlanService couponPlanService;
	/**
	 * 查找会员可用优惠券列表，并标记出最大金额优惠券
	 * 
	 * @param order
	 * @return
	 */
	@Override
	public List<CouponVo> caculateCouponDikouAmount(OrderDay order) {
		List<CouponVo> resultList = new ArrayList<CouponVo>();
		//查询会员可用优惠券
		Coupon coupon = new Coupon();
		coupon.setMemberNo(order.getMemberNo());
		coupon.setUsedStatus(0);// 未使用
		coupon.setIsAvailable(1);// 可用
		coupon.setAvailableTime2Start(order.getAppointmentTakeTime());// 计算时只需判断优惠券有效期开始时间是否大于订单预约开始时间即可
		List<Coupon> couponList = couponService.getCouponList(new Query(coupon));
		Map<Double, String> map = new HashMap<Double, String>();
		for (Coupon cp : couponList) {
			CouponVo couponVo = new CouponVo();
			String planNo = cp.getPlanNo();
			if (planNo != null && !"".equals(planNo)) {
				CouponPlan couponPlan = couponPlanService.getCouponPlan(planNo);
				if (couponPlan != null) {
					// 首先根据车型和城市判断优惠券是否可用
					String cityId = order.getCityId();
					String carModelId = order.getCarModelId();
					//couponUseType优惠券使用类型 1、分时，2、日租
					if (couponPlan.getCouponUseType()!=null&&couponPlan.getCouponUseType()==2&&couponPlan.getCarModelId().contains(carModelId) && couponPlan.getCityId().contains(cityId)) {
						// 优惠方式(1.打折，2.直减)
						Integer couponMethod = couponPlan.getCouponMethod();
						Double discountAmount = 0.0;
						// 优惠方式：打折 打折有封顶
						if (couponMethod == 1) {
							// 打折金额
							discountAmount = ECCalculateUtils.mul(order.getOrderAmount(),ECCalculateUtils.sub(1.0, couponPlan.getDiscount()));
							// 打折封顶金额
							Double discountMaxAmount = couponPlan.getDiscountMaxAmount();
							// 判断是否达到封顶金额
							if (discountAmount.compareTo(discountMaxAmount) > 0) {
								discountAmount = discountMaxAmount;
							}
							map.put(discountAmount, cp.getCouponNo());
							couponVo.setCouponNo(cp.getCouponNo());
							couponVo.setCouponMethod(couponMethod);
							couponVo.setDiscountAmount(ECCalculateUtils.round(discountAmount, 2));
							couponVo.setTitle(cp.getTitle());
							couponVo.setVailableTime2(cp.getVailableTime2());
							couponVo.setIsDefault(0);
							resultList.add(couponVo);
						} else if (couponMethod == 2) {// 优惠方式：直减(满减)// 满consumeAmount减// discountAmount
							// 订单金额满此优惠券满减金额才可进行满减
							if (order.getOrderAmount().compareTo(couponPlan.getConsumeAmount()) >= 0) {
								// 直减金额（满减金额）
								discountAmount = couponPlan.getDiscountAmount();
								map.put(discountAmount, cp.getCouponNo());
								couponVo.setCouponNo(cp.getCouponNo());
								couponVo.setCouponMethod(couponMethod);
								couponVo.setDiscountAmount(ECCalculateUtils.round(discountAmount, 2));
								couponVo.setTitle(cp.getTitle());
								couponVo.setVailableTime2(cp.getVailableTime2());
								couponVo.setIsDefault(0);
								resultList.add(couponVo);
							}
						}
						
					}
				}
			}
		}
		Double couponMaxDiscountAmount = 0d;// 优惠券抵扣的最大金额
		String couponNo = "";// 优惠券抵扣的最大金额对应的优惠券编号
		if (map.size() > 0) {
			Map<Double, String> resultMap = bubbleSort(map);
			for (Double discountAmount : resultMap.keySet()) {
				couponNo = resultMap.get(discountAmount);
				couponMaxDiscountAmount = discountAmount;
			}
		}
		if (couponMaxDiscountAmount>0.0&&!"".equals(couponNo)) {
			for(CouponVo couponVo:resultList){
				if (couponVo.getCouponNo().equals(couponNo)) {
					couponVo.setIsDefault(1);
					break;
				}
			}
		}
		return resultList;
	}
	private Map<Double, String> bubbleSort(Map<Double, String> map) {
		// doubel值最大的键值对存入此map返回
		Map<Double, String> resultMap = new HashMap<Double, String>();
		double a[] = new double[map.size()];
		int k = 0;
		for (double d : map.keySet()) {
			a[k] = d;
			k++;
		}
		// double
		// a[]={49.60,38.25,65,97,76,13,27,49,78,34,12,64,5,4,62,99.6,98,54,56,17,18,23,34,15,35,25,53,51};
		double temp = 0;
		for (int i = 0; i < a.length - 1; i++) {
			for (int j = 0; j < a.length - 1 - i; j++) {
				if (a[j] > a[j + 1]) {
					temp = a[j];
					a[j] = a[j + 1];
					a[j + 1] = temp;
				}
			}
		}
		// for(int i=0;i<a.length;i++)
		// System.out.println(a[i]);
		Double maxDoule = a[a.length - 1];
		resultMap.put(maxDoule, map.get(maxDoule));
		return resultMap;
	}
}
