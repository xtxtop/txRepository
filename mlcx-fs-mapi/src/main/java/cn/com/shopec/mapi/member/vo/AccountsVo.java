package cn.com.shopec.mapi.member.vo;

import java.util.Date;

import cn.com.shopec.core.common.Entity;

/** 
 * Accounts 数据 vo对象
 */
public class AccountsVo {
	
	//记账单号
	private String accountNo;
	//会员编号
	private String memberNo;
	//业务单号（租车订单单号、充值订单单号等类型）
	private String businessNo;
	//业务单类型（1、租车消费 2、余额充值  等）
	private Integer businessType;
	//记账类型（1、出账  2、入账）
	private Integer accountType;
	//记账金额
	private Double accountMoney;
	//记账前金额
	private Double accountBeforeMoney;
	//记账后金额
	private Double accountOverMoney;
	//记账时间
	private String accountTime;
	/*Auto generated properties end*/
	
	
	
	/*Customized properties start*/
	
	/*Customized properties end*/
	
	
	
	public String getAccountNo(){
		return accountNo;
	}
	
	public void setAccountNo(String accountNo){
		this.accountNo = accountNo;
	}
	
	public String getMemberNo(){
		return memberNo;
	}
	
	public void setMemberNo(String memberNo){
		this.memberNo = memberNo;
	}
	
	public String getBusinessNo(){
		return businessNo;
	}
	
	public void setBusinessNo(String businessNo){
		this.businessNo = businessNo;
	}
	
	public Integer getBusinessType(){
		return businessType;
	}
	
	public void setBusinessType(Integer businessType){
		this.businessType = businessType;
	}
	
	public Integer getAccountType(){
		return accountType;
	}
	
	public void setAccountType(Integer accountType){
		this.accountType = accountType;
	}
	
	public Double getAccountMoney(){
		return accountMoney;
	}
	
	public void setAccountMoney(Double accountMoney){
		this.accountMoney = accountMoney;
	}
	
	public Double getAccountBeforeMoney(){
		return accountBeforeMoney;
	}
	
	public void setAccountBeforeMoney(Double accountBeforeMoney){
		this.accountBeforeMoney = accountBeforeMoney;
	}
	
	public Double getAccountOverMoney(){
		return accountOverMoney;
	}
	
	public void setAccountOverMoney(Double accountOverMoney){
		this.accountOverMoney = accountOverMoney;
	}
	
	public String getAccountTime(){
		return accountTime;
	}
	
	public void setAccountTime(String accountTime){
		this.accountTime = accountTime;
	}
	
	

	
	
	/*Auto generated methods end*/
	
	
	
	/*Customized methods start*/
	
	/*Customized methods end*/
	
	
	@Override
	public String toString() {
		return "AccountsVo [" + (accountNo != null ? "accountNo=" + accountNo + ", " : "")
				+ (memberNo != null ? "memberNo=" + memberNo + ", " : "")
				+ (businessNo != null ? "businessNo=" + businessNo + ", " : "")
				+ (businessType != null ? "businessType=" + businessType + ", " : "")
				+ (accountType != null ? "accountType=" + accountType + ", " : "")
				+ (accountMoney != null ? "accountMoney=" + accountMoney + ", " : "")
				+ (accountBeforeMoney != null ? "accountBeforeMoney=" + accountBeforeMoney + ", " : "")
				+ (accountOverMoney != null ? "accountOverMoney=" + accountOverMoney + ", " : "")
				+ (accountTime != null ? "accountTime=" + accountTime : "") + "]";
	}
}
