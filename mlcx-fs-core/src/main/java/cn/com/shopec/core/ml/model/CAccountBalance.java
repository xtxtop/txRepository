package cn.com.shopec.core.ml.model;

import java.util.Date;

import cn.com.shopec.core.common.Entity;

/** 
 * CAccountBalance 数据实体类
 */
public class CAccountBalance extends Entity<String> {
	
	private static final long serialVersionUID = 1l;
	
	/*Auto generated properties start*/
	
	//账户余额编号
	private String accountBalanceNo;
	//会员编号
	private String memberNo;
	//充电余额
	private Double chargingBalance;
	//停车余额
	private Double stopBalance;
	//冻结状态
	private Integer isFreeze;
	//冻结原因
	private String freezeReason;
	//冻结人
	private String freezePerson;
	//冻结时间
	private Date freezeTime;
	//冻结时间 时间范围起（查询用）
	private Date freezeTimeStart;
	//冻结时间 时间范围止（查询用）
	private Date freezeTimeEnd;	
	//创建时间
	private Date createTime;
	//创建时间 时间范围起（查询用）
	private Date createTimeStart;
	//创建时间 时间范围止（查询用）
	private Date createTimeEnd;	
	//更新时间
	private Date updateTime;
	//更新时间 时间范围起（查询用）
	private Date updateTimeStart;
	//更新时间 时间范围止（查询用）
	private Date updateTimeEnd;	
	//操作人类型（0、系统自动操作，1、平台人员操作，2、商家人员操作，3、会员操作）
	private Integer operatorType;
	//操作人ID
	private String operatorId;
	
	/*Auto generated properties end*/
	
	
	
	/*Customized properties start*/
	
	/*Customized properties end*/
	
	
	
	/*Auto generated methods start*/
	
	@Override
	public String getPK(){
		return accountBalanceNo;
	}
	
	public String getAccountBalanceNo(){
		return accountBalanceNo;
	}
	
	public void setAccountBalanceNo(String accountBalanceNo){
		this.accountBalanceNo = accountBalanceNo;
	}
	
	public String getMemberNo(){
		return memberNo;
	}
	
	public void setMemberNo(String memberNo){
		this.memberNo = memberNo;
	}
	
	public Double getChargingBalance(){
		return chargingBalance;
	}
	
	public void setChargingBalance(Double chargingBalance){
		this.chargingBalance = chargingBalance;
	}
	
	public Double getStopBalance(){
		return stopBalance;
	}
	
	public void setStopBalance(Double stopBalance){
		this.stopBalance = stopBalance;
	}
	
	public Integer getIsFreeze(){
		return isFreeze;
	}
	
	public void setIsFreeze(Integer isFreeze){
		this.isFreeze = isFreeze;
	}
	
	public String getFreezeReason(){
		return freezeReason;
	}
	
	public void setFreezeReason(String freezeReason){
		this.freezeReason = freezeReason;
	}
	
	public String getFreezePerson(){
		return freezePerson;
	}
	
	public void setFreezePerson(String freezePerson){
		this.freezePerson = freezePerson;
	}
	
	public Date getFreezeTime(){
		return freezeTime;
	}
	
	public void setFreezeTime(Date freezeTime){
		this.freezeTime = freezeTime;
	}
	
	public Date getFreezeTimeStart(){
		return freezeTimeStart;
	}
	
	public void setFreezeTimeStart(Date freezeTimeStart){
		this.freezeTimeStart = freezeTimeStart;
	}
	
	public Date getFreezeTimeEnd(){
		return freezeTimeEnd;
	}
	
	public void setFreezeTimeEnd(Date freezeTimeEnd){
		this.freezeTimeEnd = freezeTimeEnd;
	}	
	
	public Date getCreateTime(){
		return createTime;
	}
	
	public void setCreateTime(Date createTime){
		this.createTime = createTime;
	}
	
	public Date getCreateTimeStart(){
		return createTimeStart;
	}
	
	public void setCreateTimeStart(Date createTimeStart){
		this.createTimeStart = createTimeStart;
	}
	
	public Date getCreateTimeEnd(){
		return createTimeEnd;
	}
	
	public void setCreateTimeEnd(Date createTimeEnd){
		this.createTimeEnd = createTimeEnd;
	}	
	
	public Date getUpdateTime(){
		return updateTime;
	}
	
	public void setUpdateTime(Date updateTime){
		this.updateTime = updateTime;
	}
	
	public Date getUpdateTimeStart(){
		return updateTimeStart;
	}
	
	public void setUpdateTimeStart(Date updateTimeStart){
		this.updateTimeStart = updateTimeStart;
	}
	
	public Date getUpdateTimeEnd(){
		return updateTimeEnd;
	}
	
	public void setUpdateTimeEnd(Date updateTimeEnd){
		this.updateTimeEnd = updateTimeEnd;
	}	
	
	public Integer getOperatorType(){
		return operatorType;
	}
	
	public void setOperatorType(Integer operatorType){
		this.operatorType = operatorType;
	}
	
	public String getOperatorId(){
		return operatorId;
	}
	
	public void setOperatorId(String operatorId){
		this.operatorId = operatorId;
	}
	
	
	/*Auto generated methods end*/
	
	
	
	/*Customized methods start*/
	
	/*Customized methods end*/
	
	
	@Override
	public String toString() {
		return "CAccountBalance ["
		 + "accountBalanceNo = " + accountBalanceNo + ", memberNo = " + memberNo + ", chargingBalance = " + chargingBalance + ", stopBalance = " + stopBalance
		 + ", isFreeze = " + isFreeze + ", freezeReason = " + freezeReason + ", freezePerson = " + freezePerson + ", freezeTime = " + freezeTime + ", freezeTimeStart = " + freezeTimeStart + ", freezeTimeEnd = " + freezeTimeEnd
		 + ", createTime = " + createTime + ", createTimeStart = " + createTimeStart + ", createTimeEnd = " + createTimeEnd + ", updateTime = " + updateTime + ", updateTimeStart = " + updateTimeStart + ", updateTimeEnd = " + updateTimeEnd + ", operatorType = " + operatorType + ", operatorId = " + operatorId
		+"]";
	}
}
