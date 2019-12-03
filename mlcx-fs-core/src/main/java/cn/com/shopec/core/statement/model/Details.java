package cn.com.shopec.core.statement.model;

import java.util.Date;

import cn.com.shopec.core.common.Entity;

/**
 * 订单表 数据实体类
 */
public class Details extends Entity<String> {

	private static final long serialVersionUID = 1l;

	/* Auto generated properties start */

	// 订单号
	private String orderNo;
	// 会员编号
	private String memberNo;
	// 会员姓名
	private String memberName;
	// 手机号
	private String mobilePhone;
	// 预约时间
	private Date appointmentTime;
	// 创建时间
	private Date createTime;
	// 结束时间
	private Date finishTime;
	// 订单时长(分钟数)
	private Integer orderDuration;
	// 实际取车地点
	private String actualTakeLoacton;
	// 实际还车地点
	private String actualTerminalLoacton;
	// 车牌号
	private String carPlateNo;
	// 订单总金额
	private Double orderAmount;
	// 套餐时长抵扣金额
	private Double packMinutesDiscountAmount;
	// 金额套餐抵扣金额
	private Double packAmountDiscountAmount;
	// 折扣金额
	private Double discountAmount;
	// 冲账金额
	private Double strikeBalanceAmount;
	// 应收金额
	private Double payableAmount;
	// 实收金额
	private Double alreadyAmount;
	// 支付方式（1、支付宝、2、微信）
	private Integer paymentMethod;
	// 支付流水号
	private String paymentFlowNo;
	
	// 计费时间
	private Date startBillingTime;
	// 结束时间查询开始
	private Date finishTimeStart;
	// 结束时间查询结束
	private Date finishTimeEnd;
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getMemberNo() {
		return memberNo;
	}
	public void setMemberNo(String memberNo) {
		this.memberNo = memberNo;
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
	public Date getAppointmentTime() {
		return appointmentTime;
	}
	public void setAppointmentTime(Date appointmentTime) {
		this.appointmentTime = appointmentTime;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getFinishTime() {
		return finishTime;
	}
	public void setFinishTime(Date finishTime) {
		this.finishTime = finishTime;
	}
	public Integer getOrderDuration() {
		return orderDuration;
	}
	public void setOrderDuration(Integer orderDuration) {
		this.orderDuration = orderDuration;
	}
	public String getActualTakeLoacton() {
		return actualTakeLoacton;
	}
	public void setActualTakeLoacton(String actualTakeLoacton) {
		this.actualTakeLoacton = actualTakeLoacton;
	}
	public String getActualTerminalLoacton() {
		return actualTerminalLoacton;
	}
	public void setActualTerminalLoacton(String actualTerminalLoacton) {
		this.actualTerminalLoacton = actualTerminalLoacton;
	}
	public String getCarPlateNo() {
		return carPlateNo;
	}
	public void setCarPlateNo(String carPlateNo) {
		this.carPlateNo = carPlateNo;
	}
	public Double getOrderAmount() {
		return orderAmount;
	}
	public void setOrderAmount(Double orderAmount) {
		this.orderAmount = orderAmount;
	}
	public Double getPackMinutesDiscountAmount() {
		return packMinutesDiscountAmount;
	}
	public void setPackMinutesDiscountAmount(Double packMinutesDiscountAmount) {
		this.packMinutesDiscountAmount = packMinutesDiscountAmount;
	}
	public Double getStrikeBalanceAmount() {
		return strikeBalanceAmount;
	}
	public void setStrikeBalanceAmount(Double strikeBalanceAmount) {
		this.strikeBalanceAmount = strikeBalanceAmount;
	}
	public Double getAlreadyAmount() {
		return alreadyAmount;
	}
	public void setAlreadyAmount(Double alreadyAmount) {
		this.alreadyAmount = alreadyAmount;
	}
	public Integer getPaymentMethod() {
		return paymentMethod;
	}
	public void setPaymentMethod(Integer paymentMethod) {
		this.paymentMethod = paymentMethod;
	}
	public String getPaymentFlowNo() {
		return paymentFlowNo;
	}
	public void setPaymentFlowNo(String paymentFlowNo) {
		this.paymentFlowNo = paymentFlowNo;
	}
	public Date getFinishTimeStart() {
		return finishTimeStart;
	}
	public void setFinishTimeStart(Date finishTimeStart) {
		this.finishTimeStart = finishTimeStart;
	}
	public Date getFinishTimeEnd() {
		return finishTimeEnd;
	}
	public void setFinishTimeEnd(Date finishTimeEnd) {
		this.finishTimeEnd = finishTimeEnd;
	}
	public Double getPayableAmount() {
		return payableAmount;
	}
	public void setPayableAmount(Double payableAmount) {
		this.payableAmount = payableAmount;
	}
	public Date getStartBillingTime() {
		return startBillingTime;
	}
	public void setStartBillingTime(Date startBillingTime) {
		this.startBillingTime = startBillingTime;
	}
	public Double getPackAmountDiscountAmount() {
		return packAmountDiscountAmount;
	}
	public void setPackAmountDiscountAmount(Double packAmountDiscountAmount) {
		this.packAmountDiscountAmount = packAmountDiscountAmount;
	}
	public Double getDiscountAmount() {
		return discountAmount;
	}
	public void setDiscountAmount(Double discountAmount) {
		this.discountAmount = discountAmount;
	}
	

}
