package cn.com.shopec.mgt.statement.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import cn.com.shopec.core.common.Entity;

/** 
 * 会员运营统计
 */
public class MemberCountVo implements Serializable{
	
	private static final long serialVersionUID = 1l;
	
	/*Auto generated properties start*/
	  
	//会员注册数量
	private String memberRegisterCount;
	 
	//会员认证数量  
	private String memberAuthenticationCount;
	 
	//会员押金缴费统计  
	private String memberDepositPaymentCount;

	//会员退款金额统计  r
	private String memberRefundAmountCount;
	 
	 
	public String getMemberRegisterCount() {
		return memberRegisterCount;
	}

	public void setMemberRegisterCount(String memberRegisterCount) {
		this.memberRegisterCount = memberRegisterCount;
	}

	public String getMemberAuthenticationCount() {
		return memberAuthenticationCount;
	}

	public void setMemberAuthenticationCount(String memberAuthenticationCount) {
		this.memberAuthenticationCount = memberAuthenticationCount;
	}

	public String getMemberDepositPaymentCount() {
		return memberDepositPaymentCount;
	}

	public void setMemberDepositPaymentCount(String memberDepositPaymentCount) {
		this.memberDepositPaymentCount = memberDepositPaymentCount;
	}

	public String getMemberRefundAmountCount() {
		return memberRefundAmountCount;
	}

	public void setMemberRefundAmountCount(String memberRefundAmountCount) {
		this.memberRefundAmountCount = memberRefundAmountCount;
	}

	 
}
