package cn.com.shopec.core.member.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import cn.com.shopec.core.common.Entity;

/** 
 * 会员运营统计
 */
public class MemberCountVo extends Entity<String> {
	
	private static final long serialVersionUID = 1l;
	
	/*Auto generated properties start*/
	  
	//会员注册数量
	private Integer memberRegisterCount;
	 
	//会员认证数量  
	private Integer memberAuthenticationCount;
	 
	//会员押金缴费统计  
	private Integer memberDepositPaymentCount;

	//会员退款金额统计  
	private Integer memberRefundAmountCount;

	//会员注册数量合计
	private Integer memberRegisterCountzj;
	 
	//会员认证数量  合计
	private Integer memberAuthenticationCountzj;
	 
	//会员押金缴费统计  合计
	private Integer memberDepositPaymentCountzj;

	//会员退款金额统计  合计
	private Integer memberRefundAmountCountzj;
	
	private String memberDay;
	
	private String memberMonth;
	
	private String memberYear;
	 
	public String getMemberMonth() {
		return memberMonth;
	}

	public void setMemberMonth(String memberMonth) {
		this.memberMonth = memberMonth;
	}

	public String getMemberYear() {
		return memberYear;
	}

	public void setMemberYear(String memberYear) {
		this.memberYear = memberYear;
	}

	public Integer getMemberRegisterCountzj() {
		return memberRegisterCountzj;
	}

	public void setMemberRegisterCountzj(Integer memberRegisterCountzj) {
		this.memberRegisterCountzj = memberRegisterCountzj;
	}

	public Integer getMemberAuthenticationCountzj() {
		return memberAuthenticationCountzj;
	}

	public void setMemberAuthenticationCountzj(Integer memberAuthenticationCountzj) {
		this.memberAuthenticationCountzj = memberAuthenticationCountzj;
	}

	public Integer getMemberDepositPaymentCountzj() {
		return memberDepositPaymentCountzj;
	}

	public void setMemberDepositPaymentCountzj(Integer memberDepositPaymentCountzj) {
		this.memberDepositPaymentCountzj = memberDepositPaymentCountzj;
	}

	public Integer getMemberRefundAmountCountzj() {
		return memberRefundAmountCountzj;
	}

	public void setMemberRefundAmountCountzj(Integer memberRefundAmountCountzj) {
		this.memberRefundAmountCountzj = memberRefundAmountCountzj;
	}

	public Integer getMemberRegisterCount() {
		return memberRegisterCount;
	}

	public void setMemberRegisterCount(Integer memberRegisterCount) {
		this.memberRegisterCount = memberRegisterCount;
	}

	public Integer getMemberAuthenticationCount() {
		return memberAuthenticationCount;
	}

	public void setMemberAuthenticationCount(Integer memberAuthenticationCount) {
		this.memberAuthenticationCount = memberAuthenticationCount;
	}

	public Integer getMemberDepositPaymentCount() {
		return memberDepositPaymentCount;
	}

	public void setMemberDepositPaymentCount(Integer memberDepositPaymentCount) {
		this.memberDepositPaymentCount = memberDepositPaymentCount;
	}

	public Integer getMemberRefundAmountCount() {
		return memberRefundAmountCount;
	}

	public void setMemberRefundAmountCount(Integer memberRefundAmountCount) {
		this.memberRefundAmountCount = memberRefundAmountCount;
	}

	public String getMemberDay() {
		return memberDay;
	}

	public void setMemberDay(String memberDay) {
		this.memberDay = memberDay;
	}

	 
}
