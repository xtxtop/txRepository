package cn.com.shopec.core.marketing.model;

import cn.com.shopec.core.common.Entity;

/**
 * 兑换码，优惠券方案关系表 数据实体类
 */
public class RedeemCouponPlan extends Entity<String> {

	private static final long serialVersionUID = 9009533407801114501L;
	
	private String redCode;
	private String planNo;
	private Integer redQutity;
	//以下为查询字段
	private String planTitle;
	
	public String getRedCode() {
		return redCode;
	}
	public void setRedCode(String redCode) {
		this.redCode = redCode;
	}
	public String getPlanNo() {
		return planNo;
	}
	public void setPlanNo(String planNo) {
		this.planNo = planNo;
	}
	public Integer getRedQutity() {
		return redQutity;
	}
	public void setRedQutity(Integer redQutity) {
		this.redQutity = redQutity;
	}
	public String getPlanTitle() {
		return planTitle;
	}
	public void setPlanTitle(String planTitle) {
		this.planTitle = planTitle;
	}
	@Override
	public String toString() {
		return "RedeemCouponPlan [redCode=" + redCode + ", planNo=" + planNo + ", redQutity=" + redQutity + "]";
	}



}
