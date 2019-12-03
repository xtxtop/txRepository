package cn.com.shopec.mapi.marketing.vo;

import java.util.Date;

import cn.com.shopec.core.common.Entity;

/**
 * 优惠卷方案表 数据实体类
 */
public class CouponVo extends Entity<String> {

	private static final long serialVersionUID = -7684823387692067730L;
	//优惠卷编号
	private String couponNo;
	//优惠卷名称
	private String title;
	//有效期
	private String vailableTime2;
	//优惠卷价格
	private Double  amount;
	//说明
	private String memo;
	//优惠卷类型(1 打折 2 直减)
	private Integer couponMethod; 
	//折扣
	private  Double discount;
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
	public String getVailableTime2() {
		return vailableTime2;
	}
	public void setVailableTime2(String vailableTime2) {
		this.vailableTime2 = vailableTime2;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@Override
	public String toString() {
		return "CouponVo [couponNo=" + couponNo + ", title=" + title + ", vailableTime2=" + vailableTime2 + ", amount="
				+ amount + ", memo=" + memo + "]";
	}
	public Integer getCouponMethod() {
		return couponMethod;
	}
	public void setCouponMethod(Integer couponMethod) {
		this.couponMethod = couponMethod;
	}
	public Double getDiscount() {
		return discount;
	}
	public void setDiscount(Double discount) {
		this.discount = discount;
	}
	
}
