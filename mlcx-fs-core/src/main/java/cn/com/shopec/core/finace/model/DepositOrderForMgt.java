package cn.com.shopec.core.finace.model;

import java.io.Serializable;
import java.util.Date;

/** 
 * 押金缴纳列表使用实体类
 */
public class DepositOrderForMgt implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3361252011392490073L;
	//会员名称
	private String memberName;
	//手机号
	private String mobilePhone;
	//支付方式（1、支付宝、2、微信 3、微信H5）
	private Integer paymentMethod;
	//内部支付流水号
	private String partTradeFlowNo;
	//押金金额
	private Double depositAmount;
	//押金缴纳时间
	private Date paymentTime;
	//退款状态（0、未退款，1、已退款，2、退款失败 默认0）
	private Integer refundStatus;
	//退款时间
	private Date refundTime;
	//押金退款金额
	private Double refundAmount;
	//退款方式（1、支付宝，2、微信，3、线下退款）
	private Integer refundMethod;
	
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
	public Integer getPaymentMethod() {
		return paymentMethod;
	}
	public void setPaymentMethod(Integer paymentMethod) {
		this.paymentMethod = paymentMethod;
	}
	public String getPartTradeFlowNo() {
		return partTradeFlowNo;
	}
	public void setPartTradeFlowNo(String partTradeFlowNo) {
		this.partTradeFlowNo = partTradeFlowNo;
	}
	public Double getDepositAmount() {
		return depositAmount;
	}
	public void setDepositAmount(Double depositAmount) {
		this.depositAmount = depositAmount;
	}
	public Date getPaymentTime() {
		return paymentTime;
	}
	public void setPaymentTime(Date paymentTime) {
		this.paymentTime = paymentTime;
	}
	public Integer getRefundStatus() {
		return refundStatus;
	}
	public void setRefundStatus(Integer refundStatus) {
		this.refundStatus = refundStatus;
	}
	public Date getRefundTime() {
		return refundTime;
	}
	public void setRefundTime(Date refundTime) {
		this.refundTime = refundTime;
	}
	public Double getRefundAmount() {
		return refundAmount;
	}
	public void setRefundAmount(Double refundAmount) {
		this.refundAmount = refundAmount;
	}
	public Integer getRefundMethod() {
		return refundMethod;
	}
	public void setRefundMethod(Integer refundMethod) {
		this.refundMethod = refundMethod;
	}
	
}
