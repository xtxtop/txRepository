package cn.com.shopec.core.mlparking.model;

import java.util.Date;

import cn.com.shopec.core.common.Entity;

/** 
 * 计费规则 数据实体类
 */
public class CParkBilling extends Entity<String> {
	
	private static final long serialVersionUID = 1l;
	
	/*Auto generated properties start*/
	
	//停车计费方案编号
	private String parkBillingNo;
	//计费方案名称
	private String parkBillingName;
	//计费方案版本
	private String billingVersion;
	//停车类型(0.闸 1地锁)
	private Integer parkType;
	//预约免费时长(分钟)
	private Integer freeTime;
	//立即停车免费时长(分钟)
	private Integer stopTime;
	//计费时间单位(分钟)
	private Integer unitTime;
	//金额(元/单位时间)
	private Double overtimePrice;
	//状态(0停用1启用)
	private Integer status;
	//创建时间
	private Date createTime;
	//创建时间 时间范围起（查询用）
	private Date createTimeStart;
	//创建时间 时间范围止（查询用）
	private Date createTimeEnd;	
	//修改时间
	private Date updateTime;
	//修改时间 时间范围起（查询用）
	private Date updateTimeStart;
	//修改时间 时间范围止（查询用）
	private Date updateTimeEnd;	
	//操作人类型(0、系统自动操作，1、平台人员操作)
	private Integer operatorType;
	//操作人
	private String operatorId;
	//停车封顶金额
	private Double cappingPrice;
	//预约停车协议
	private String agreement;
	
	/*Auto generated properties end*/
	
	
	
	/*Customized properties start*/
	
	/*Customized properties end*/
	
	
	
	/*Auto generated methods start*/
	
	@Override
	public String getPK(){
		return parkBillingNo;
	}
	
	public String getParkBillingNo(){
		return parkBillingNo;
	}
	
	public void setParkBillingNo(String parkBillingNo){
		this.parkBillingNo = parkBillingNo;
	}
	
	public String getParkBillingName(){
		return parkBillingName;
	}
	
	public void setParkBillingName(String parkBillingName){
		this.parkBillingName = parkBillingName;
	}
	
	public String getBillingVersion(){
		return billingVersion;
	}
	
	public void setBillingVersion(String billingVersion){
		this.billingVersion = billingVersion;
	}
	
	public Integer getParkType(){
		return parkType;
	}
	
	public void setParkType(Integer parkType){
		this.parkType = parkType;
	}
	
	public Integer getFreeTime(){
		return freeTime;
	}
	
	public void setFreeTime(Integer freeTime){
		this.freeTime = freeTime;
	}
	
	public Integer getStopTime(){
		return stopTime;
	}
	
	public void setStopTime(Integer stopTime){
		this.stopTime = stopTime;
	}
	
	public Integer getUnitTime(){
		return unitTime;
	}
	
	public void setUnitTime(Integer unitTime){
		this.unitTime = unitTime;
	}
	
	public Double getOvertimePrice(){
		return overtimePrice;
	}
	
	public void setOvertimePrice(Double overtimePrice){
		this.overtimePrice = overtimePrice;
	}
	
	public Integer getStatus(){
		return status;
	}
	
	public void setStatus(Integer status){
		this.status = status;
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
	
	public Double getCappingPrice(){
		return cappingPrice;
	}
	
	public void setCappingPrice(Double cappingPrice){
		this.cappingPrice = cappingPrice;
	}
	
	public String getAgreement(){
		return agreement;
	}
	
	public void setAgreement(String agreement){
		this.agreement = agreement;
	}
	
	
	/*Auto generated methods end*/
	
	
	
	/*Customized methods start*/
	
	/*Customized methods end*/
	
	
	@Override
	public String toString() {
		return "CParkBilling ["
		 + "parkBillingNo = " + parkBillingNo + ", parkBillingName = " + parkBillingName + ", billingVersion = " + billingVersion + ", parkType = " + parkType
		 + ", freeTime = " + freeTime + ", stopTime = " + stopTime + ", unitTime = " + unitTime + ", overtimePrice = " + overtimePrice
		 + ", status = " + status + ", createTime = " + createTime + ", createTimeStart = " + createTimeStart + ", createTimeEnd = " + createTimeEnd + ", updateTime = " + updateTime + ", updateTimeStart = " + updateTimeStart + ", updateTimeEnd = " + updateTimeEnd + ", operatorType = " + operatorType
		 + ", operatorId = " + operatorId + ", cappingPrice = " + cappingPrice + ", agreement = " + agreement
		+"]";
	}
}
