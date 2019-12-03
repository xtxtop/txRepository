package cn.com.shopec.core.marketing.model;

import java.util.Date;

import cn.com.shopec.core.common.Entity;

/** 
 * Accounts 数据实体类
 */
public class Accounts extends Entity<String> {
	
	private static final long serialVersionUID = 1l;
	
	/*Auto generated properties start*/
	
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
	private Date accountTime;
	//记账时间 时间范围起（查询用）
	private Date accountTimeStart;
	//记账时间 时间范围止（查询用）
	private Date accountTimeEnd;	
	//备注
	private String memo;
	/*Auto generated properties end*/
	
	
	
	/*Customized properties start*/
	
	/*Customized properties end*/
	
	
	
	/*Auto generated methods start*/
	
	@Override
	public String getPK(){
		return accountNo;
	}
	
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
	
	public Date getAccountTime(){
		return accountTime;
	}
	
	public void setAccountTime(Date accountTime){
		this.accountTime = accountTime;
	}
	
	public Date getAccountTimeStart(){
		return accountTimeStart;
	}
	
	public void setAccountTimeStart(Date accountTimeStart){
		this.accountTimeStart = accountTimeStart;
	}
	
	public Date getAccountTimeEnd(){
		return accountTimeEnd;
	}
	
	public void setAccountTimeEnd(Date accountTimeEnd){
		this.accountTimeEnd = accountTimeEnd;
	}	
	
	
	/*Auto generated methods end*/
	
	
	
	/*Customized methods start*/
	
	/*Customized methods end*/
	
	
	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	@Override
	public String toString() {
		return "Accounts ["
		 + "accountNo = " + accountNo + ", memberNo = " + memberNo + ", businessNo = " + businessNo + ", businessType = " + businessType
		 + ", accountType = " + accountType + ", accountMoney = " + accountMoney + ", accountBeforeMoney = " + accountBeforeMoney + ", accountOverMoney = " + accountOverMoney
		 + ", accountTime = " + accountTime + ", accountTimeStart = " + accountTimeStart + ", accountTimeEnd = " + accountTimeEnd
		+"]";
	}
}
