package cn.com.shopec.mapi.worker.vo;

import java.util.Date;

import cn.com.shopec.core.common.Entity;

/** 
 * 巡检计划表 数据实体类
 */
public class CheckPlanFinishVo extends Entity<String> {
	
	private static final long serialVersionUID = 1l;
	
	/*Auto generated properties start*/
	
	//巡检计划编号
	private String checkPlanNo;
	//城市id（具体见数据字典表）
	private String cityId;
	//城市名称
	private String cityName;
	//计划日期
	private Date planDate;
	//计划日期 时间范围起（查询用）
	private Date planDateStart;
	//计划日期 时间范围止（查询用）
	private Date planDateEnd;	
	//场站编号
	private String parkNo;
	//场站名称
	private String parkName;
	//计划巡检项id，各id间用半角逗号进行分割
	private String checkItemId;
	//计划巡检项名称（各项间用半角逗号进行分割）
	private String checkItem;
	//包含多个人员id，各个id间用半角逗号进行分割
	private String workerId;
	//包含多个人员姓名，各个姓名间用半角逗号进行分割
	private String workerName;
	//实际开始时间
	private Date actualStartTime;
	//实际开始时间 时间范围起（查询用）
	private Date actualStartTimeStart;
	//实际开始时间 时间范围止（查询用）
	private Date actualStartTimeEnd;	
	//实际结束时间
	private Date actualEndTime;
	//实际结束时间 时间范围起（查询用）
	private Date actualEndTimeStart;
	//实际结束时间 时间范围止（查询用）
	private Date actualEndTimeEnd;	
	//计划状态，0、未完成，1、已完成，2、已取消，默认0
	private Integer planStatus;
	//删除标识，0、未删除，1、已删除，默认0
	private Integer isDeleted;
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
		return checkPlanNo;
	}
	
	public String getParkName() {
		return parkName;
	}

	public void setParkName(String parkName) {
		this.parkName = parkName;
	}

	public String getCheckPlanNo(){
		return checkPlanNo;
	}
	
	public void setCheckPlanNo(String checkPlanNo){
		this.checkPlanNo = checkPlanNo;
	}
	
	public String getCityId(){
		return cityId;
	}
	
	public void setCityId(String cityId){
		this.cityId = cityId;
	}
	
	public String getCityName(){
		return cityName;
	}
	
	public void setCityName(String cityName){
		this.cityName = cityName;
	}
	
	public Date getPlanDate(){
		return planDate;
	}
	
	public void setPlanDate(Date planDate){
		this.planDate = planDate;
	}
	
	public Date getPlanDateStart(){
		return planDateStart;
	}
	
	public void setPlanDateStart(Date planDateStart){
		this.planDateStart = planDateStart;
	}
	
	public Date getPlanDateEnd(){
		return planDateEnd;
	}
	
	public void setPlanDateEnd(Date planDateEnd){
		this.planDateEnd = planDateEnd;
	}	
	
	public String getParkNo(){
		return parkNo;
	}
	
	public void setParkNo(String parkNo){
		this.parkNo = parkNo;
	}
	
	public String getCheckItemId(){
		return checkItemId;
	}
	
	public void setCheckItemId(String checkItemId){
		this.checkItemId = checkItemId;
	}
	
	public String getCheckItem(){
		return checkItem;
	}
	
	public void setCheckItem(String checkItem){
		this.checkItem = checkItem;
	}
	
	public String getWorkerId(){
		return workerId;
	}
	
	public void setWorkerId(String workerId){
		this.workerId = workerId;
	}
	
	public String getWorkerName(){
		return workerName;
	}
	
	public void setWorkerName(String workerName){
		this.workerName = workerName;
	}
	
	public Date getActualStartTime(){
		return actualStartTime;
	}
	
	public void setActualStartTime(Date actualStartTime){
		this.actualStartTime = actualStartTime;
	}
	
	public Date getActualStartTimeStart(){
		return actualStartTimeStart;
	}
	
	public void setActualStartTimeStart(Date actualStartTimeStart){
		this.actualStartTimeStart = actualStartTimeStart;
	}
	
	public Date getActualStartTimeEnd(){
		return actualStartTimeEnd;
	}
	
	public void setActualStartTimeEnd(Date actualStartTimeEnd){
		this.actualStartTimeEnd = actualStartTimeEnd;
	}	
	
	public Date getActualEndTime(){
		return actualEndTime;
	}
	
	public void setActualEndTime(Date actualEndTime){
		this.actualEndTime = actualEndTime;
	}
	
	public Date getActualEndTimeStart(){
		return actualEndTimeStart;
	}
	
	public void setActualEndTimeStart(Date actualEndTimeStart){
		this.actualEndTimeStart = actualEndTimeStart;
	}
	
	public Date getActualEndTimeEnd(){
		return actualEndTimeEnd;
	}
	
	public void setActualEndTimeEnd(Date actualEndTimeEnd){
		this.actualEndTimeEnd = actualEndTimeEnd;
	}	
	
	public Integer getPlanStatus(){
		return planStatus;
	}
	
	public void setPlanStatus(Integer planStatus){
		this.planStatus = planStatus;
	}
	
	public Integer getIsDeleted(){
		return isDeleted;
	}
	
	public void setIsDeleted(Integer isDeleted){
		this.isDeleted = isDeleted;
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

	@Override
	public String toString() {
		return "CheckPlan [checkPlanNo=" + checkPlanNo + ", cityId=" + cityId + ", cityName=" + cityName + ", planDate="
				+ planDate + ", planDateStart=" + planDateStart + ", planDateEnd=" + planDateEnd + ", parkNo=" + parkNo
				+ ", parkName=" + parkName + ", checkItemId=" + checkItemId + ", checkItem=" + checkItem + ", workerId="
				+ workerId + ", workerName=" + workerName + ", actualStartTime=" + actualStartTime
				+ ", actualStartTimeStart=" + actualStartTimeStart + ", actualStartTimeEnd=" + actualStartTimeEnd
				+ ", actualEndTime=" + actualEndTime + ", actualEndTimeStart=" + actualEndTimeStart
				+ ", actualEndTimeEnd=" + actualEndTimeEnd + ", planStatus=" + planStatus + ", isDeleted=" + isDeleted
				+ ", createTime=" + createTime + ", createTimeStart=" + createTimeStart + ", createTimeEnd="
				+ createTimeEnd + ", updateTime=" + updateTime + ", updateTimeStart=" + updateTimeStart
				+ ", updateTimeEnd=" + updateTimeEnd + ", operatorType=" + operatorType + ", operatorId=" + operatorId
				+ "]";
	}
	
	
	/*Auto generated methods end*/
	
	
	
	/*Customized methods start*/
	
	/*Customized methods end*/
	
	
	
}
