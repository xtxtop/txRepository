package cn.com.shopec.core.dailyrental.vo;

import java.util.Date;

import cn.com.shopec.core.common.Entity;

/**
 * 优惠券方案表 数据实体类
 */
public class CouponVo{
	//优惠券编号
	private String couponNo;
	//优惠券主题（名称）
	private String title;
	//优惠方式(1.打折，2.直减)
	private Integer couponMethod;
	//有效结束日期
	private Date vailableTime2;
	//折扣金额
	private Double discountAmount;
	//是否默认抵扣
	private Integer isDefault;
	public String getCouponNo() {
		return couponNo;
	}
	public void setCouponNo(String couponNo) {
		this.couponNo = couponNo;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Integer getCouponMethod() {
		return couponMethod;
	}
	public void setCouponMethod(Integer couponMethod) {
		this.couponMethod = couponMethod;
	}
	public Date getVailableTime2() {
		return vailableTime2;
	}
	public void setVailableTime2(Date vailableTime2) {
		this.vailableTime2 = vailableTime2;
	}
	public Double getDiscountAmount() {
		return discountAmount;
	}
	public void setDiscountAmount(Double discountAmount) {
		this.discountAmount = discountAmount;
	}
	public Integer getIsDefault() {
		return isDefault;
	}
	public void setIsDefault(Integer isDefault) {
		this.isDefault = isDefault;
	}
	
}
