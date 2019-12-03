package cn.com.shopec.core.ml.model;

import java.util.Date;

import cn.com.shopec.core.common.Entity;

/** 
 * 充电枪 数据实体类
 */
public class ChargingGunInfo extends Entity<String> {
	
	private static final long serialVersionUID = 1l;
	
	/*Auto generated properties start*/
	
	//充电枪编号
	private String chargingGunNo;
	//充电枪编码
	private String chargingGunCode;
	//工作状态(0001-告警 0002-待机0003-工作 0004-离线0005-完成0006-正在操作充电桩0007-预约中)
	private Integer workStatus;
	//操作人id
	private String operatorId;
	//操作人类型（0、系统自动操作，1、平台人员操作）
	private Integer operatorType;
	//创建日期
	private Date createTime;
	//创建日期 时间范围起（查询用）
	private Date createTimeStart;
	//创建日期 时间范围止（查询用）
	private Date createTimeEnd;	
	//更新日期
	private Date updateTime;
	//更新日期 时间范围起（查询用）
	private Date updateTimeStart;
	//更新日期 时间范围止（查询用）
	private Date updateTimeEnd;	
	//登记人
	private String registrant;
	//登记时间
	private Date registrantTime;
	//登记时间 时间范围起（查询用）
	private Date registrantTimeStart;
	//登记时间 时间范围止（查询用）
	private Date registrantTimeEnd;	
	//充电桩编号
	private String chargingPileNo;
	
	/*Auto generated properties end*/
	
	
	
	/*Customized properties start*/
	
	/*Customized properties end*/
	
	
	
	/*Auto generated methods start*/
	
	@Override
	public String getPK(){
		return chargingGunNo;
	}
	
	public String getChargingGunNo(){
		return chargingGunNo;
	}
	
	public void setChargingGunNo(String chargingGunNo){
		this.chargingGunNo = chargingGunNo;
	}
	
	public String getChargingGunCode(){
		return chargingGunCode;
	}
	
	public void setChargingGunCode(String chargingGunCode){
		this.chargingGunCode = chargingGunCode;
	}
	
	public Integer getWorkStatus(){
		return workStatus;
	}
	
	public void setWorkStatus(Integer workStatus){
		this.workStatus = workStatus;
	}
	
	public String getOperatorId(){
		return operatorId;
	}
	
	public void setOperatorId(String operatorId){
		this.operatorId = operatorId;
	}
	
	public Integer getOperatorType(){
		return operatorType;
	}
	
	public void setOperatorType(Integer operatorType){
		this.operatorType = operatorType;
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
	
	public String getRegistrant(){
		return registrant;
	}
	
	public void setRegistrant(String registrant){
		this.registrant = registrant;
	}
	
	public Date getRegistrantTime(){
		return registrantTime;
	}
	
	public void setRegistrantTime(Date registrantTime){
		this.registrantTime = registrantTime;
	}
	
	public Date getRegistrantTimeStart(){
		return registrantTimeStart;
	}
	
	public void setRegistrantTimeStart(Date registrantTimeStart){
		this.registrantTimeStart = registrantTimeStart;
	}
	
	public Date getRegistrantTimeEnd(){
		return registrantTimeEnd;
	}
	
	public void setRegistrantTimeEnd(Date registrantTimeEnd){
		this.registrantTimeEnd = registrantTimeEnd;
	}	
	
	public String getChargingPileNo(){
		return chargingPileNo;
	}
	
	public void setChargingPileNo(String chargingPileNo){
		this.chargingPileNo = chargingPileNo;
	}
	
	
	/*Auto generated methods end*/
	
	
	
	/*Customized methods start*/
	
	/*Customized methods end*/
	
	
	@Override
	public String toString() {
		return "ChargingGunInfo ["
		 + "chargingGunNo = " + chargingGunNo + ", chargingGunCode = " + chargingGunCode + ", workStatus = " + workStatus + ", operatorId = " + operatorId
		 + ", operatorType = " + operatorType + ", createTime = " + createTime + ", createTimeStart = " + createTimeStart + ", createTimeEnd = " + createTimeEnd + ", updateTime = " + updateTime + ", updateTimeStart = " + updateTimeStart + ", updateTimeEnd = " + updateTimeEnd + ", registrant = " + registrant
		 + ", registrantTime = " + registrantTime + ", registrantTimeStart = " + registrantTimeStart + ", registrantTimeEnd = " + registrantTimeEnd + ", chargingPileNo = " + chargingPileNo
		+"]";
	}
}
