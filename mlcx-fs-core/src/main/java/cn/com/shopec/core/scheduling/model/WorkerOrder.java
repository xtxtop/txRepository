package cn.com.shopec.core.scheduling.model;

import java.util.Date;

import cn.com.shopec.core.common.Entity;

/**
 * WorkerOrder调度工单 数据实体类
 */
public class WorkerOrder extends Entity<String> {

	private static final long serialVersionUID = 1l;

	/* Auto generated properties start */

	// 工单编号
	private String workerOrderNo;
	// 调度类型 -- 0-调度 1-救援 2-清洁 3-加油 4-维保
	private String type;
	// 起点场站编号
	private String startParkNo;
	// 起点
	private String startParkName;
	// 终点场站编号
	private String terminalParkNo;
	// 终点
	private String terminalParkName;
	// 车辆编号
	private String carNo;
	// 车牌号
	private String carPlateNo;
	// 调度员id
	private String workerId;
	// 调度员
	private String workerName;
	// 指派调度员ID
	private String assignWorkerId;
	// 指派调度员姓名
	private String assignWorkerName;
	// 指派时间
	private Date assignTime;
	// 调度员电话
	private String mobilePhone;
	// 下发时间
	private Date sendTime;
	// 下发时间 时间范围起（查询用）
	private Date sendTimeStart;
	// 下发时间 时间范围止（查询用）
	private Date sendTimeEnd;
	// 开始时间
	private Date startTime;
	// 开始时间 时间范围起（查询用）
	private Date startTimeStart;
	// 开始时间 时间范围止（查询用）
	private Date startTimeEnd;
	// 计划完成时间
	private Date planTime;
	private String pnTime;
	// 首次开门时间
	private Date openCarDoorTime;
	// 完成时间
	private Date finishTime;
	// 完成时间 时间范围起（查询用）
	private Date finishTimeStart;
	// 完成时间 时间范围止（查询用）
	private Date finishTimeEnd;
	// 审核状态 -- 0-未审核 1-已审核 2-审核未通过
	private Integer cencorStatus;
	// 审核备注
	private String cencorMemo;
	// 任务终点经度
	private String endLongitude;
	// 任务终点纬度
	private String endLatitude;
	// 审核时间
	private Date cencorTime;
	// 审核时间 时间范围起（查询用）
	private Date cencorTimeStart;
	// 审核时间 时间范围止（查询用）
	private Date cencorTimeEnd;
	// 审核人
	private String cencorId;
	// 调度工单状态 -- 0-未下发 1-已下发 2-调度中 3-已结束4-已取消
	private Integer workOrderStatus;
	// 备注
	private String memo;
	// 创建日期
	private Date createTime;
	// 创建日期 时间范围起（查询用）
	private Date createTimeStart;
	// 创建日期 时间范围止（查询用）
	private Date createTimeEnd;
	// 更新日期
	private Date updateTime;
	// 更新日期 时间范围起（查询用）
	private Date updateTimeStart;
	// 更新日期 时间范围止（查询用）
	private Date updateTimeEnd;
	// 操作人类型
	private Integer operatorType;
	// 操作人id
	private String operatorId;
	// 下发原因
	private String sendReason;
	// 下发原因图片描述1
	private String sendReasonPicUrl1;
	// 下发原因图片描述2
	private String sendReasonPicUrl2;
	// 下发原因图片描述3
	private String sendReasonPicUrl3;
	// 任务完成时记录电量
	private Double power;
	//实际车地址
		private String currentAddress;
		//实际车经度
		private String currentLongitude;
		//实际车纬度
		private String currentLatitude;
	/* Auto generated properties end */

	/* Customized properties start */

	/* Customized properties end */

	/* Auto generated methods start */

	@Override
	public String getPK() {
		return workerOrderNo;
	}

	public String getWorkerOrderNo() {
		return workerOrderNo;
	}

	public void setWorkerOrderNo(String workerOrderNo) {
		this.workerOrderNo = workerOrderNo;
	}

	public String getStartParkNo() {
		return startParkNo;
	}

	public void setStartParkNo(String startParkNo) {
		this.startParkNo = startParkNo;
	}

	public String getStartParkName() {
		return startParkName;
	}

	public void setStartParkName(String startParkName) {
		this.startParkName = startParkName;
	}

	public String getTerminalParkNo() {
		return terminalParkNo;
	}

	public void setTerminalParkNo(String terminalParkNo) {
		this.terminalParkNo = terminalParkNo;
	}

	public String getTerminalParkName() {
		return terminalParkName;
	}

	public void setTerminalParkName(String terminalParkName) {
		this.terminalParkName = terminalParkName;
	}

	public String getCarNo() {
		return carNo;
	}

	public void setCarNo(String carNo) {
		this.carNo = carNo;
	}

	public String getCarPlateNo() {
		return carPlateNo;
	}

	public void setCarPlateNo(String carPlateNo) {
		this.carPlateNo = carPlateNo;
	}

	public String getWorkerId() {
		return workerId;
	}

	public void setWorkerId(String workerId) {
		this.workerId = workerId;
	}

	public String getWorkerName() {
		return workerName;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public void setWorkerName(String workerName) {
		this.workerName = workerName;
	}

	public Date getSendTime() {
		return sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}

	public Date getSendTimeStart() {
		return sendTimeStart;
	}

	public void setSendTimeStart(Date sendTimeStart) {
		this.sendTimeStart = sendTimeStart;
	}

	public Date getSendTimeEnd() {
		return sendTimeEnd;
	}

	public void setSendTimeEnd(Date sendTimeEnd) {
		this.sendTimeEnd = sendTimeEnd;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getStartTimeStart() {
		return startTimeStart;
	}

	public void setStartTimeStart(Date startTimeStart) {
		this.startTimeStart = startTimeStart;
	}

	public Date getStartTimeEnd() {
		return startTimeEnd;
	}

	public void setStartTimeEnd(Date startTimeEnd) {
		this.startTimeEnd = startTimeEnd;
	}

	public Date getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(Date finishTime) {
		this.finishTime = finishTime;
	}

	public Date getFinishTimeStart() {
		return finishTimeStart;
	}

	public void setFinishTimeStart(Date finishTimeStart) {
		this.finishTimeStart = finishTimeStart;
	}

	public Date getFinishTimeEnd() {
		return finishTimeEnd;
	}

	public void setFinishTimeEnd(Date finishTimeEnd) {
		this.finishTimeEnd = finishTimeEnd;
	}

	public Integer getCencorStatus() {
		return cencorStatus;
	}

	public void setCencorStatus(Integer cencorStatus) {
		this.cencorStatus = cencorStatus;
	}

	public String getCencorMemo() {
		return cencorMemo;
	}

	public void setCencorMemo(String cencorMemo) {
		this.cencorMemo = cencorMemo;
	}

	public Date getCencorTime() {
		return cencorTime;
	}

	public void setCencorTime(Date cencorTime) {
		this.cencorTime = cencorTime;
	}

	public Date getCencorTimeStart() {
		return cencorTimeStart;
	}

	public void setCencorTimeStart(Date cencorTimeStart) {
		this.cencorTimeStart = cencorTimeStart;
	}

	public Date getCencorTimeEnd() {
		return cencorTimeEnd;
	}

	public void setCencorTimeEnd(Date cencorTimeEnd) {
		this.cencorTimeEnd = cencorTimeEnd;
	}

	public String getCencorId() {
		return cencorId;
	}

	public void setCencorId(String cencorId) {
		this.cencorId = cencorId;
	}

	public Integer getWorkOrderStatus() {
		return workOrderStatus;
	}

	public void setWorkOrderStatus(Integer workOrderStatus) {
		this.workOrderStatus = workOrderStatus;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getCreateTimeStart() {
		return createTimeStart;
	}

	public void setCreateTimeStart(Date createTimeStart) {
		this.createTimeStart = createTimeStart;
	}

	public Date getCreateTimeEnd() {
		return createTimeEnd;
	}

	public void setCreateTimeEnd(Date createTimeEnd) {
		this.createTimeEnd = createTimeEnd;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Date getUpdateTimeStart() {
		return updateTimeStart;
	}

	public void setUpdateTimeStart(Date updateTimeStart) {
		this.updateTimeStart = updateTimeStart;
	}

	public Date getUpdateTimeEnd() {
		return updateTimeEnd;
	}

	public void setUpdateTimeEnd(Date updateTimeEnd) {
		this.updateTimeEnd = updateTimeEnd;
	}

	public Integer getOperatorType() {
		return operatorType;
	}

	public void setOperatorType(Integer operatorType) {
		this.operatorType = operatorType;
	}

	public String getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(String operatorId) {
		this.operatorId = operatorId;
	}

	/* Auto generated methods end */

	/* Customized methods start */

	/* Customized methods end */

	public String getSendReason() {
		return sendReason;
	}

	public void setSendReason(String sendReason) {
		this.sendReason = sendReason;
	}

	public String getSendReasonPicUrl1() {
		return sendReasonPicUrl1;
	}

	public void setSendReasonPicUrl1(String sendReasonPicUrl1) {
		this.sendReasonPicUrl1 = sendReasonPicUrl1;
	}

	public String getSendReasonPicUrl2() {
		return sendReasonPicUrl2;
	}

	public void setSendReasonPicUrl2(String sendReasonPicUrl2) {
		this.sendReasonPicUrl2 = sendReasonPicUrl2;
	}

	public String getSendReasonPicUrl3() {
		return sendReasonPicUrl3;
	}

	public void setSendReasonPicUrl3(String sendReasonPicUrl3) {
		this.sendReasonPicUrl3 = sendReasonPicUrl3;
	}

	@Override
	public String toString() {
		return "WorkerOrder [" + "workerOrderNo = " + workerOrderNo + ", type = " + type + ", startParkNo = "
				+ startParkNo + ", startParkName = " + startParkName + ", terminalParkNo = " + terminalParkNo
				+ ", terminalParkName = " + terminalParkName + ", carNo = " + carNo + ", carPlateNo = " + carPlateNo
				+ ", workerId = " + workerId + ", workerName = " + workerName + ",mobilePhone = " + mobilePhone
				+ ", sendTime = " + sendTime + ", sendTimeStart = " + sendTimeStart + ", sendTimeEnd = " + sendTimeEnd
				+ ", startTime = " + startTime + ", startTimeStart = " + startTimeStart + ", startTimeEnd = "
				+ startTimeEnd + ", finishTime = " + finishTime + ", finishTimeStart = " + finishTimeStart
				+ ", finishTimeEnd = " + finishTimeEnd + ", cencorStatus = " + cencorStatus + ", cencorMemo = "
				+ cencorMemo + ", cencorTime = " + cencorTime + ", cencorTimeStart = " + cencorTimeStart
				+ ", cencorTimeEnd = " + cencorTimeEnd + ", cencorId = " + cencorId + ", workOrderStatus = "
				+ workOrderStatus + ", memo = " + memo + ", createTime = " + createTime + ", createTimeStart = "
				+ createTimeStart + ", createTimeEnd = " + createTimeEnd + ", updateTime = " + updateTime
				+ ", updateTimeStart = " + updateTimeStart + ", updateTimeEnd = " + updateTimeEnd + ", operatorType = "
				+ operatorType + ", operatorId = " + operatorId + "]";
	}

	public Date getPlanTime() {
		return planTime;
	}

	public void setPlanTime(Date planTime) {
		this.planTime = planTime;
	}

	public String getAssignWorkerId() {
		return assignWorkerId;
	}

	public void setAssignWorkerId(String assignWorkerId) {
		this.assignWorkerId = assignWorkerId;
	}

	public String getAssignWorkerName() {
		return assignWorkerName;
	}

	public void setAssignWorkerName(String assignWorkerName) {
		this.assignWorkerName = assignWorkerName;
	}

	public Date getAssignTime() {
		return assignTime;
	}

	public void setAssignTime(Date assignTime) {
		this.assignTime = assignTime;
	}

	public Double getPower() {
		return power;
	}

	public void setPower(Double power) {
		this.power = power;
	}

	public String getPnTime() {
		return pnTime;
	}

	public void setPnTime(String pnTime) {
		this.pnTime = pnTime;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Date getOpenCarDoorTime() {
		return openCarDoorTime;
	}

	public void setOpenCarDoorTime(Date openCarDoorTime) {
		this.openCarDoorTime = openCarDoorTime;
	}

	public String getEndLongitude() {
		return endLongitude;
	}

	public void setEndLongitude(String endLongitude) {
		this.endLongitude = endLongitude;
	}

	public String getEndLatitude() {
		return endLatitude;
	}

	public void setEndLatitude(String endLatitude) {
		this.endLatitude = endLatitude;
	}

	public String getCurrentAddress() {
		return currentAddress;
	}

	public void setCurrentAddress(String currentAddress) {
		this.currentAddress = currentAddress;
	}

	public String getCurrentLongitude() {
		return currentLongitude;
	}

	public void setCurrentLongitude(String currentLongitude) {
		this.currentLongitude = currentLongitude;
	}

	public String getCurrentLatitude() {
		return currentLatitude;
	}

	public void setCurrentLatitude(String currentLatitude) {
		this.currentLatitude = currentLatitude;
	}
}
