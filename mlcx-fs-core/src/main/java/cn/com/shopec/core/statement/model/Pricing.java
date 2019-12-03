package cn.com.shopec.core.statement.model;

import java.util.Date;

import cn.com.shopec.core.common.Entity;

/**
 * 套餐汇总  数据实体类
 */
public class Pricing extends Entity<String> {

	private static final long serialVersionUID = 1l;

	/* Auto generated properties start */

	//套餐编号
	private String pricingNo;
	//套餐名称
	private String pricingName;
	//套餐单价
	private Double pricingUnitPrice;
	//套餐时长
	private Double pricingDuration;
	//套餐类型
	private Double pricingType;
	//套餐购买量
	private Integer pricingNum;
	//套餐金额合计
	private Double pricingAmount;
	//套餐实收
	private Double pricingAlreadyAmount;
	//微信收入
	private Double pricingWxAmount;
	//微信手续费
	private Double pricingWxCharge;
	//支付宝收入
	private Double pricingAlipayAmount;
	//支付宝手续费
	private Double pricingAlipayCharge;
	//开始时间
	private Date startTime;
	//结束时间
	private Date endTime;
	public String getPricingNo() {
		return pricingNo;
	}
	public void setPricingNo(String pricingNo) {
		this.pricingNo = pricingNo;
	}
	public String getPricingName() {
		return pricingName;
	}
	public void setPricingName(String pricingName) {
		this.pricingName = pricingName;
	}
	public Double getPricingUnitPrice() {
		return pricingUnitPrice;
	}
	public void setPricingUnitPrice(Double pricingUnitPrice) {
		this.pricingUnitPrice = pricingUnitPrice;
	}
	public Double getPricingDuration() {
		return pricingDuration;
	}
	public void setPricingDuration(Double pricingDuration) {
		this.pricingDuration = pricingDuration;
	}
	public Double getPricingType() {
		return pricingType;
	}
	public void setPricingType(Double pricingType) {
		this.pricingType = pricingType;
	}
	public Integer getPricingNum() {
		return pricingNum;
	}
	public void setPricingNum(Integer pricingNum) {
		this.pricingNum = pricingNum;
	}
	public Double getPricingAmount() {
		return pricingAmount;
	}
	public void setPricingAmount(Double pricingAmount) {
		this.pricingAmount = pricingAmount;
	}
	public Double getPricingAlreadyAmount() {
		return pricingAlreadyAmount;
	}
	public void setPricingAlreadyAmount(Double pricingAlreadyAmount) {
		this.pricingAlreadyAmount = pricingAlreadyAmount;
	}
	public Double getPricingWxAmount() {
		return pricingWxAmount;
	}
	public void setPricingWxAmount(Double pricingWxAmount) {
		this.pricingWxAmount = pricingWxAmount;
	}
	public Double getPricingWxCharge() {
		return pricingWxCharge;
	}
	public void setPricingWxCharge(Double pricingWxCharge) {
		this.pricingWxCharge = pricingWxCharge;
	}
	public Double getPricingAlipayAmount() {
		return pricingAlipayAmount;
	}
	public void setPricingAlipayAmount(Double pricingAlipayAmount) {
		this.pricingAlipayAmount = pricingAlipayAmount;
	}
	public Double getPricingAlipayCharge() {
		return pricingAlipayCharge;
	}
	public void setPricingAlipayCharge(Double pricingAlipayCharge) {
		this.pricingAlipayCharge = pricingAlipayCharge;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	
	

}
