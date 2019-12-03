package cn.com.shopec.core.marketing.model;

import java.util.Date;

import cn.com.shopec.core.common.Entity;

/**
 * 优惠券方案表 数据实体类
 */
public class CouponPlan extends Entity<String> {

	private static final long serialVersionUID = -7684823387692067730L;

	private String planNo;
	private String title;
	private String subtitle;
	private String photoUrl;
	private Integer couponType;//(1、默认 2、订单分享优惠方案)
	private Integer couponUseType;//优惠券使用类型 1、分时，2、日租
	private Integer couponMethod;
	private Double discountAmount;
	private Double discount;
	private Double discountMaxAmount;
	private Double consumeAmount;
	private Date vailableTime1;
	private Date vailableTime2;
	private Integer availableDays;
	private String carModelId;
	private String carModelName;
	private String cityId;
	private String cityName;
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
	private Integer maxQuantity;
	private Integer existingQuantity;	
	
	// 以下字段做查询用，并无存入数据库
	private String censorName;//发放人（查看详情用）
	private Integer redQuantity;//兑换数量
	private Date availableTime1Start;
	private Date availableTime1End;
	private Date availableTime2Start;
	private Date availableTime2End;
	
	public String getPlanNo() {
		return planNo;
	}

	public void setPlanNo(String planNo) {
		this.planNo = planNo;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSubtitle() {
		return subtitle;
	}

	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}

	public String getPhotoUrl() {
		return photoUrl;
	}

	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}

	public Integer getCouponType() {
		return couponType;
	}

	public void setCouponType(Integer couponType) {
		this.couponType = couponType;
	}

	public Integer getCouponUseType() {
		return couponUseType;
	}

	public void setCouponUseType(Integer couponUseType) {
		this.couponUseType = couponUseType;
	}

	public Integer getCouponMethod() {
		return couponMethod;
	}

	public void setCouponMethod(Integer couponMethod) {
		this.couponMethod = couponMethod;
	}

	public Double getDiscountAmount() {
		return discountAmount;
	}

	public void setDiscountAmount(Double discountAmount) {
		this.discountAmount = discountAmount;
	}

	public Double getDiscount() {
		return discount;
	}

	public void setDiscount(Double discount) {
		this.discount = discount;
	}

	public Double getDiscountMaxAmount() {
		return discountMaxAmount;
	}

	public void setDiscountMaxAmount(Double discountMaxAmount) {
		this.discountMaxAmount = discountMaxAmount;
	}

	public Date getVailableTime1() {
		return vailableTime1;
	}

	public void setVailableTime1(Date vailableTime1) {
		this.vailableTime1 = vailableTime1;
	}

	public Date getVailableTime2() {
		return vailableTime2;
	}

	public void setVailableTime2(Date vailableTime2) {
		this.vailableTime2 = vailableTime2;
	}

	public String getCarModelId() {
		return carModelId;
	}

	public void setCarModelId(String carModelId) {
		this.carModelId = carModelId;
	}

	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
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

	public Integer getAvailableDays() {
		return availableDays;
	}

	public void setAvailableDays(Integer availableDays) {
		this.availableDays = availableDays;
	}

	public Integer getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Integer isDeleted) {
		this.isDeleted = isDeleted;
	}

	public Double getConsumeAmount() {
		return consumeAmount;
	}

	public void setConsumeAmount(Double consumeAmount) {
		this.consumeAmount = consumeAmount;
	}

	public String getCensorName() {
		return censorName;
	}

	public void setCensorName(String censorName) {
		this.censorName = censorName;
	}
	
	public String getCarModelName() {
		return carModelName;
	}

	public void setCarModelName(String carModelName) {
		this.carModelName = carModelName;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
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
	public Integer getMaxQuantity() {
		return maxQuantity;
	}

	public void setMaxQuantity(Integer maxQuantity) {
		this.maxQuantity = maxQuantity;
	}

	public Integer getExistingQuantity() {
		return existingQuantity;
	}
	
	public void setExistingQuantity(Integer existingQuantity) {
		this.existingQuantity = existingQuantity;
	}

	public Integer getRedQuantity() {
		return redQuantity;
	}

	public void setRedQuantity(Integer redQuantity) {
		this.redQuantity = redQuantity;
	}

	@Override
	public String toString() {
		return "CouponPlan [planNo=" + planNo + ", title=" + title + ", subtitle=" + subtitle + ", photoUrl=" + photoUrl
				+ ", couponType=" + couponType + ", couponMethod=" + couponMethod + ", discountAmount=" + discountAmount
				+ ", discount=" + discount + ", discountMaxAmount=" + discountMaxAmount + ", consumeAmount="
				+ consumeAmount + ", vailableTime1=" + vailableTime1 + ", vailableTime2=" + vailableTime2
				+ ", availableDays=" + availableDays + ", carModelId=" + carModelId + ", carModelName=" + carModelName
				+ ", cityId=" + cityId + ", cityName=" + cityName + ", remark=" + remark + ", censorStatus="
				+ censorStatus + ", censorTime=" + censorTime + ", censorId=" + censorId + ", censorMemo=" + censorMemo
				+ ", isAvailable=" + isAvailable + ", availableUpdateTime=" + availableUpdateTime + ", createTime="
				+ createTime + ", updateTime=" + updateTime + ", operatorId=" + operatorId + ", operatorType="
				+ operatorType + ", isDeleted=" + isDeleted + ", maxQuantity=" + maxQuantity + ", existingQuantity="
				+ existingQuantity + ", censorName=" + censorName + ", availableTime1Start=" + availableTime1Start
				+ ", availableTime1End=" + availableTime1End + ", availableTime2Start=" + availableTime2Start
				+ ", availableTime2End=" + availableTime2End + "]";
	}
}
