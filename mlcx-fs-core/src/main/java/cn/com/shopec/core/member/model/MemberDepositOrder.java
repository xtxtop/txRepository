package cn.com.shopec.core.member.model;

import java.util.Date;

import cn.com.shopec.core.common.Entity;

/** 
 * Member 数据实体类
 */
public class MemberDepositOrder extends Entity<String> {
	
	private static final long serialVersionUID = 1l;
	 
	//会员编号
	private String memberNo; 
	//姓名
	private String memberName; 
	//手机号
	private String mobilePhone;
	
	private String depositAmount;
	
	private String paymentFlowNo;
	
	private String paymemtId;
	
	private String bizObjNo;
	
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
	public String getDepositAmount() {
		return depositAmount;
	}
	public void setDepositAmount(String depositAmount) {
		this.depositAmount = depositAmount;
	}
	public String getPaymentFlowNo() {
		return paymentFlowNo;
	}
	public void setPaymentFlowNo(String paymentFlowNo) {
		this.paymentFlowNo = paymentFlowNo;
	}
	public String getPaymemtId() {
		return paymemtId;
	}
	public void setPaymemtId(String paymemtId) {
		this.paymemtId = paymemtId;
	}
	public String getBizObjNo() {
		return bizObjNo;
	}
	public void setBizObjNo(String bizObjNo) {
		this.bizObjNo = bizObjNo;
	}
	   
}
