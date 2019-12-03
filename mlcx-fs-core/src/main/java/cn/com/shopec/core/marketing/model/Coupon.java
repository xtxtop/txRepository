package cn.com.shopec.core.marketing.model;

import java.util.Date;

import cn.com.shopec.core.common.Entity;

/**
 * 优惠券方案表 数据实体类
 */
public class Coupon extends Entity<String> {

	private static final long serialVersionUID = -7684823387692067730L;

	private String couponNo;
	private String planNo;
	private String memberNo;
	private Integer bizObjType;
	private String bizObjNo;
	private String title;
	private Integer couponType;
	private Integer couponMethod;
	private Double discount;
	private Double discountAmount;
	private Double deductibleAmount;
	private Date vailableTime1;
	private Date vailableTime2;
	private Integer availableDays;
	private Integer issueMethod;
	private String issueChannel;
	private Date issueTime;
	private String issuerId;
	private Integer isAvailable;
	private Date availableUpdateTime;
	private Integer usedStatus;
	private Date usedTime;
	
	/** 该字段仅做详细查询用，并无存入数据库 **/
	private String issueorName;
	private Date availableTime1Start;
	private Date availableTime1End;
	private Date availableTime2Start;
	private Date availableTime2End;
	private String memberName;
	private String mobilePhone;
	/** 该变量为批量增加时前台传值用，各个会员编号间采用英文逗号间隔**/
	private String memberNos;
	
	private Date issueTimeStart;
	private Date issueTimeEnd;
	
	/**
	 * 发放数量
	 */
	private Integer sendQuantity;
	
	public String getCouponNo() {
		return couponNo;
	}
	public void setCouponNo(String couponNo) {
		this.couponNo = couponNo;
	}
	public String getPlanNo() {
		return planNo;
	}
	public void setPlanNo(String planNo) {
		this.planNo = planNo;
	}
	public String getMemberNo() {
		return memberNo;
	}
	public void setMemberNo(String memberNo) {
		this.memberNo = memberNo;
	}
	public Integer getBizObjType() {
		return bizObjType;
	}
	public void setBizObjType(Integer bizObjType) {
		this.bizObjType = bizObjType;
	}
	public String getBizObjNo() {
		return bizObjNo;
	}
	public void setBizObjNo(String bizObjNo) {
		this.bizObjNo = bizObjNo;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Integer getCouponType() {
		return couponType;
	}
	public void setCouponType(Integer couponType) {
		this.couponType = couponType;
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
	public Double getDeductibleAmount() {
		return deductibleAmount;
	}
	public void setDeductibleAmount(Double deductibleAmount) {
		this.deductibleAmount = deductibleAmount;
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
	public Integer getAvailableDays() {
		return availableDays;
	}
	public void setAvailableDays(Integer availableDays) {
		this.availableDays = availableDays;
	}
	public Integer getIssueMethod() {
		return issueMethod;
	}
	public void setIssueMethod(Integer issueMethod) {
		this.issueMethod = issueMethod;
	}
	public String getIssueChannel() {
		return issueChannel;
	}
	public void setIssueChannel(String issueChannel) {
		this.issueChannel = issueChannel;
	}
	public Date getIssueTime() {
		return issueTime;
	}
	public void setIssueTime(Date issueTime) {
		this.issueTime = issueTime;
	}
	public String getIssuerId() {
		return issuerId;
	}
	public void setIssuerId(String issuerId) {
		this.issuerId = issuerId;
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
	public Integer getUsedStatus() {
		return usedStatus;
	}
	public void setUsedStatus(Integer usedStatus) {
		this.usedStatus = usedStatus;
	}
	public Date getUsedTime() {
		return usedTime;
	}
	public void setUsedTime(Date usedTime) {
		this.usedTime = usedTime;
	}
	public Double getDiscount() {
		return discount;
	}
	public void setDiscount(Double discount) {
		this.discount = discount;
	}
	public String getIssueorName() {
		return issueorName;
	}
	public void setIssueorName(String issueorName) {
		this.issueorName = issueorName;
	}
	public String getMemberNos() {
		return memberNos;
	}
	public void setMemberNos(String memberNos) {
		this.memberNos = memberNos;
	}
	public Date getIssueTimeStart() {
		return issueTimeStart;
	}
	public void setIssueTimeStart(Date issueTimeStart) {
		this.issueTimeStart = issueTimeStart;
	}
	public Date getIssueTimeEnd() {
		return issueTimeEnd;
	}
	public void setIssueTimeEnd(Date issueTimeEnd) {
		this.issueTimeEnd = issueTimeEnd;
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
	public String getMemberName() {
		return memberName;
	}
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}
	public String getMobilePhone() {
		return mobilePhone;
	}
	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}		
	public Integer getSendQuantity() {
		return sendQuantity;
	}
	public void setSendQuantity(Integer sendQuantity) {
		this.sendQuantity = sendQuantity;
	}
	@Override
	public String toString() {
		return "Coupon [couponNo=" + couponNo + ", planNo=" + planNo + ", memberNo=" + memberNo + ", bizObjType="
				+ bizObjType + ", bizObjNo=" + bizObjNo + ", title=" + title + ", couponType=" + couponType
				+ ", couponMethod=" + couponMethod + ", discount=" + discount + ", discountAmount=" + discountAmount
				+ ", deductibleAmount=" + deductibleAmount + ", vailableTime1=" + vailableTime1 + ", vailableTime2="
				+ vailableTime2 + ", availableDays=" + availableDays + ", issueMethod=" + issueMethod
				+ ", issueChannel=" + issueChannel + ", issueTime=" + issueTime + ", issuerId=" + issuerId
				+ ", isAvailable=" + isAvailable + ", availableUpdateTime=" + availableUpdateTime + ", usedStatus="
				+ usedStatus + ", usedTime=" + usedTime + ", issueorName=" + issueorName + ", availableTime1Start="
				+ availableTime1Start + ", availableTime1End=" + availableTime1End + ", availableTime2Start="
				+ availableTime2Start + ", availableTime2End=" + availableTime2End + ", memberName=" + memberName
				+ ", mobilePhone=" + mobilePhone + ", memberNos=" + memberNos + ", issueTimeStart=" + issueTimeStart
				+ ", issueTimeEnd=" + issueTimeEnd + "]";
	}
	
}
