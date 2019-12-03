package cn.com.shopec.core.ml.model;

import java.util.Date;

import cn.com.shopec.core.common.Entity;

/** 
 * 计费方案明细 数据实体类
 */
public class BillingSchemeDetail extends Entity<String> {
	
	private static final long serialVersionUID = 1l;
	
	/*Auto generated properties start*/
	
	//编号
	private String detailNo;
	//计费方案版本编号
	private String schemeNo;
	private String schemeName;
	//序号
	private String serialNumber;
	//开始时间
	private Date startTime;
	//开始时间 时间范围起（查询用）
	private Date startTimeStart;
	//开始时间 时间范围止（查询用）
	private Date startTimeEnd;	
	//结束时间
	private Date finishTime;
	//结束时间 时间范围起（查询用）
	private Date finishTimeStart;
	//结束时间 时间范围止（查询用）
	private Date finishTimeEnd;	
	private String memo;
	//状态()
	private String status;
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
	//时间段
	private String timeQuantum;
	
	
	/*Auto generated properties end*/
	
	
	
	/*Customized properties start*/
	
	/*Customized properties end*/
	
	
	
	/*Auto generated methods start*/
	
	public String getSchemeName() {
		return schemeName;
	}

	public void setSchemeName(String schemeName) {
		this.schemeName = schemeName;
	}

	public String getTimeQuantum() {
		return timeQuantum;
	}

	public void setTimeQuantum(String timeQuantum) {
		this.timeQuantum = timeQuantum;
	}

	@Override
	public String getPK(){
		return detailNo;
	}
	
	public String getDetailNo(){
		return detailNo;
	}
	
	public void setDetailNo(String detailNo){
		this.detailNo = detailNo;
	}
	
	public String getSchemeNo(){
		return schemeNo;
	}
	
	public void setSchemeNo(String schemeNo){
		this.schemeNo = schemeNo;
	}
	
	public String getSerialNumber(){
		return serialNumber;
	}
	
	public void setSerialNumber(String serialNumber){
		this.serialNumber = serialNumber;
	}
	
	public Date getStartTime(){
		return startTime;
	}
	
	public void setStartTime(Date startTime){
		this.startTime = startTime;
	}
	
	public Date getStartTimeStart(){
		return startTimeStart;
	}
	
	public void setStartTimeStart(Date startTimeStart){
		this.startTimeStart = startTimeStart;
	}
	
	public Date getStartTimeEnd(){
		return startTimeEnd;
	}
	
	public void setStartTimeEnd(Date startTimeEnd){
		this.startTimeEnd = startTimeEnd;
	}	
	
	public Date getFinishTime(){
		return finishTime;
	}
	
	public void setFinishTime(Date finishTime){
		this.finishTime = finishTime;
	}
	
	public Date getFinishTimeStart(){
		return finishTimeStart;
	}
	
	public void setFinishTimeStart(Date finishTimeStart){
		this.finishTimeStart = finishTimeStart;
	}
	
	public Date getFinishTimeEnd(){
		return finishTimeEnd;
	}
	
	public void setFinishTimeEnd(Date finishTimeEnd){
		this.finishTimeEnd = finishTimeEnd;
	}	
	
	public String getMemo(){
		return memo;
	}
	
	public void setMemo(String memo){
		this.memo = memo;
	}
	
	public String getStatus(){
		return status;
	}
	
	public void setStatus(String status){
		this.status = status;
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
	
	
	/*Auto generated methods end*/
	
	
	
	/*Customized methods start*/
	
	/*Customized methods end*/
	
	
	@Override
	public String toString() {
		return "BillingSchemeDetail [detailNo=" + detailNo + ", schemeNo="
				+ schemeNo + ", schemeName=" + schemeName + ", serialNumber="
				+ serialNumber + ", startTime=" + startTime
				+ ", startTimeStart=" + startTimeStart + ", startTimeEnd="
				+ startTimeEnd + ", finishTime=" + finishTime
				+ ", finishTimeStart=" + finishTimeStart + ", finishTimeEnd="
				+ finishTimeEnd + ", memo=" + memo + ", status=" + status
				+ ", operatorId=" + operatorId + ", operatorType="
				+ operatorType + ", createTime=" + createTime
				+ ", createTimeStart=" + createTimeStart + ", createTimeEnd="
				+ createTimeEnd + ", updateTime=" + updateTime
				+ ", updateTimeStart=" + updateTimeStart + ", updateTimeEnd="
				+ updateTimeEnd + ", timeQuantum=" + timeQuantum + "]";
	}
}
