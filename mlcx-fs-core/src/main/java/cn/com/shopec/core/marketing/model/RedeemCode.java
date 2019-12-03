package cn.com.shopec.core.marketing.model;

import java.util.Date;
import java.util.List;

import cn.com.shopec.core.common.Entity;

/**
 * 兑换码表 数据实体类
 */
public class RedeemCode extends Entity<String> {

	private static final long serialVersionUID = -7684823387692067730L;
	
	private String redCode;
	private String redName;
	private Integer availableTimes;
	private Date availableTime1;
	private Date availableTime2;
	private String remark;
	private Integer censorStatus;
	private Date censorTime;
	private String censorId;
	private String censorMemo;
	private Integer isAvailable;
	private Date availableUpdateTime;
	private Date createTime;
	private Date updateTime;
	private String operatorId;
	private Integer operatorType;
	private Integer isDeleted;
	private List<RedeemCouponPlan> redeemCouponPlans;
	
	// 以下字段做查询用，并无存入数据库
	private String censorName;
	private Date availableTime1Start;
	private Date availableTime1End;
	private Date availableTime2Start;
	private Date availableTime2End;
	private String couponPlanString;
	public String getRedCode() {
		return redCode;
	}
	public void setRedCode(String redCode) {
		this.redCode = redCode;
	}
	public String getRedName() {
		return redName;
	}
	public void setRedName(String redName) {
		this.redName = redName;
	}
	public Integer getAvailableTimes() {
		return availableTimes;
	}
	public void setAvailableTimes(Integer availableTimes) {
		this.availableTimes = availableTimes;
	}
	public Date getAvailableTime1() {
		return availableTime1;
	}
	public void setAvailableTime1(Date availableTime1) {
		this.availableTime1 = availableTime1;
	}
	public Date getAvailableTime2() {
		return availableTime2;
	}
	public void setAvailableTime2(Date availableTime2) {
		this.availableTime2 = availableTime2;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Integer getCensorStatus() {
		return censorStatus;
	}
	public void setCensorStatus(Integer censorStatus) {
		this.censorStatus = censorStatus;
	}
	public Date getCensorTime() {
		return censorTime;
	}
	public void setCensorTime(Date censorTime) {
		this.censorTime = censorTime;
	}
	public String getCensorId() {
		return censorId;
	}
	public void setCensorId(String censorId) {
		this.censorId = censorId;
	}
	public String getCensorMemo() {
		return censorMemo;
	}
	public void setCensorMemo(String censorMemo) {
		this.censorMemo = censorMemo;
	}
	public Integer getIsAvailable() {
		return isAvailable;
	}
	public void setIsAvailable(Integer isAvailable) {
		this.isAvailable = isAvailable;
	}
	public Date getAvailableUpdateTime() {
		return availableUpdateTime;
	}
	public void setAvailableUpdateTime(Date availableUpdateTime) {
		this.availableUpdateTime = availableUpdateTime;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public String getOperatorId() {
		return operatorId;
	}
	public void setOperatorId(String operatorId) {
		this.operatorId = operatorId;
	}
	public Integer getOperatorType() {
		return operatorType;
	}
	public void setOperatorType(Integer operatorType) {
		this.operatorType = operatorType;
	}
	public Integer getIsDeleted() {
		return isDeleted;
	}
	public void setIsDeleted(Integer isDeleted) {
		this.isDeleted = isDeleted;
	}
	public Date getAvailableTime1Start() {
		return availableTime1Start;
	}
	public void setAvailableTime1Start(Date availableTime1Start) {
		this.availableTime1Start = availableTime1Start;
	}
	public Date getAvailableTime1End() {
		return availableTime1End;
	}
	public void setAvailableTime1End(Date availableTime1End) {
		this.availableTime1End = availableTime1End;
	}
	public Date getAvailableTime2Start() {
		return availableTime2Start;
	}
	public void setAvailableTime2Start(Date availableTime2Start) {
		this.availableTime2Start = availableTime2Start;
	}
	public Date getAvailableTime2End() {
		return availableTime2End;
	}
	public void setAvailableTime2End(Date availableTime2End) {
		this.availableTime2End = availableTime2End;
	}
	
	public List<RedeemCouponPlan> getRedeemCouponPlans() {
		return redeemCouponPlans;
	}
	public void setRedeemCouponPlans(List<RedeemCouponPlan> redeemCouponPlans) {
		this.redeemCouponPlans = redeemCouponPlans;
	}
	
	public String getCensorName() {
		return censorName;
	}
	public void setCensorName(String censorName) {
		this.censorName = censorName;
	}
	
 	public String getCouponPlanString() {
		return couponPlanString;
	}
	public void setCouponPlanString(String couponPlanString) {
		this.couponPlanString = couponPlanString;
	}
	@Override
	public String toString() {
		return "RedeemCode [redCode=" + redCode + ", redName=" + redName + ", availableTimes=" + availableTimes
				+ ", availableTime1=" + availableTime1 + ", availableTime2=" + availableTime2 + ", remark=" + remark
				+ ", censorStatus=" + censorStatus + ", censorTime=" + censorTime + ", censorId=" + censorId
				+ ", censorMemo=" + censorMemo + ", isAvailable=" + isAvailable + ", availableUpdateTime="
				+ availableUpdateTime + ", createTime=" + createTime + ", updateTime=" + updateTime + ", operatorId="
				+ operatorId + ", operatorType=" + operatorType + ", isDeleted=" + isDeleted + ", availableTime1Start="
				+ availableTime1Start + ", availableTime1End=" + availableTime1End + ", availableTime2Start="
				+ availableTime2Start + ", availableTime2End=" + availableTime2End + "]";
	}
	
}
