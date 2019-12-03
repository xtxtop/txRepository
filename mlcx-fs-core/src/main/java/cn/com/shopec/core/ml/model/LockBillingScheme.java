package cn.com.shopec.core.ml.model;

import java.util.Date;

import cn.com.shopec.core.common.Entity;

/** 
 * 地锁计费方案 数据实体类
 */
public class LockBillingScheme extends Entity<String> {
	
	private static final long serialVersionUID = 1l;
	
	/*Auto generated properties start*/
	
	//计费方案编号
	private String lockSchemeNo;
	//计费方案名称
	private String lockSchemeName;
	//免费时长
	private Integer freeTime;
	//计费时间单位
	private Integer unitTime;
	//超时金额(元/单位时间)
	private Double overtimePrice;
	//状态(0停用1启用)
	private Integer status;
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
	//操作人类型
	private Integer operatorType;
	//操作人id
	private String operatorId;
	
	/*Auto generated properties end*/
	
	
	
	/*Customized properties start*/
	
	/*Customized properties end*/
	
	
	
	/*Auto generated methods start*/
	
	@Override
	public String getPK(){
		return lockSchemeNo;
	}
	
	public String getLockSchemeNo(){
		return lockSchemeNo;
	}
	
	public void setLockSchemeNo(String lockSchemeNo){
		this.lockSchemeNo = lockSchemeNo;
	}
	
	public String getLockSchemeName(){
		return lockSchemeName;
	}
	
	public void setLockSchemeName(String lockSchemeName){
		this.lockSchemeName = lockSchemeName;
	}
	
	public Integer getFreeTime(){
		return freeTime;
	}
	
	public void setFreeTime(Integer freeTime){
		this.freeTime = freeTime;
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
	
	
	/*Auto generated methods end*/
	
	
	
	/*Customized methods start*/
	
	/*Customized methods end*/
	
	
	@Override
	public String toString() {
		return "LockBillingScheme ["
		 + "lockSchemeNo = " + lockSchemeNo + ", lockSchemeName = " + lockSchemeName + ", freeTime = " + freeTime + ", unitTime = " + unitTime
		 + ", overtimePrice = " + overtimePrice + ", status = " + status + ", createTime = " + createTime + ", createTimeStart = " + createTimeStart + ", createTimeEnd = " + createTimeEnd + ", updateTime = " + updateTime + ", updateTimeStart = " + updateTimeStart + ", updateTimeEnd = " + updateTimeEnd
		 + ", operatorType = " + operatorType + ", operatorId = " + operatorId
		+"]";
	}
}
