package cn.com.shopec.core.resource.model;

import java.util.Date;

import cn.com.shopec.core.common.Entity;

/**
 * 巡检结果表 数据实体类
 */
public class CheckResult extends Entity<String> {

	private static final long serialVersionUID = 1l;

	/* Auto generated properties start */

	// 巡检结果id
	private String checkResultId;
	// 巡检计划编号
	private String checkPlanNo;
	// 巡检项目id（车辆 or 充电桩，具体id见数据字典表）
	private String checkItemId;
	// 设备号（车辆为车牌号，充电桩为充电桩编号）
	private String deviceNo;
	// 结果（1、正常，0、异常）
	private Integer checkResult;
	// 异常简述
	private String abnormalBrief;
	// 异常详述
	private String abnormalDetail;
	// 备注（预留）
	private String memo;
	// 巡检结果照片1
	private String photourl1;
	// 巡检结果照片2
	private String photourl2;
	// 巡检结果照片3
	private String photourl3;
	// 删除标识（0、未删除，1、已删除，默认0）
	private Integer isDeleted;
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

	// 场站编号
	private String parkNo;
	// 计划日期
	private Date planDate;
	// 场站名称
	private String parkName;
	// 实际开始时间
	private Date actualStartTime;
	// 实际结束时间
	private Date actualEndTime;
	/* Auto generated properties end */

	/* Customized properties start */

	/* Customized properties end */

	/* Auto generated methods start */

	@Override
	public String getPK() {
		return checkResultId;
	}

	public Date getPlanDate() {
		return planDate;
	}

	public void setPlanDate(Date planDate) {
		this.planDate = planDate;
	}

	public String getParkNo() {
		return parkNo;
	}

	public void setParkNo(String parkNo) {
		this.parkNo = parkNo;
	}

	public String getCheckResultId() {
		return checkResultId;
	}

	public void setCheckResultId(String checkResultId) {
		this.checkResultId = checkResultId;
	}

	public String getCheckPlanNo() {
		return checkPlanNo;
	}

	public void setCheckPlanNo(String checkPlanNo) {
		this.checkPlanNo = checkPlanNo;
	}

	public String getCheckItemId() {
		return checkItemId;
	}

	public void setCheckItemId(String checkItemId) {
		this.checkItemId = checkItemId;
	}

	public String getParkName() {
		return parkName;
	}

	public void setParkName(String parkName) {
		this.parkName = parkName;
	}

	public Date getActualStartTime() {
		return actualStartTime;
	}

	public void setActualStartTime(Date actualStartTime) {
		this.actualStartTime = actualStartTime;
	}

	public Date getActualEndTime() {
		return actualEndTime;
	}

	public void setActualEndTime(Date actualEndTime) {
		this.actualEndTime = actualEndTime;
	}

	public String getDeviceNo() {
		return deviceNo;
	}

	public void setDeviceNo(String deviceNo) {
		this.deviceNo = deviceNo;
	}

	public Integer getCheckResult() {
		return checkResult;
	}

	public void setCheckResult(Integer checkResult) {
		this.checkResult = checkResult;
	}

	public String getAbnormalBrief() {
		return abnormalBrief;
	}

	public void setAbnormalBrief(String abnormalBrief) {
		this.abnormalBrief = abnormalBrief;
	}

	public String getAbnormalDetail() {
		return abnormalDetail;
	}

	public void setAbnormalDetail(String abnormalDetail) {
		this.abnormalDetail = abnormalDetail;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public Integer getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Integer isDeleted) {
		this.isDeleted = isDeleted;
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

	@Override
	public String toString() {
		return "CheckResult [" + "checkResultId = " + checkResultId + ", checkPlanNo = " + checkPlanNo
				+ ", checkItemId = " + checkItemId + ", deviceNo = " + deviceNo + ", checkResult = " + checkResult
				+ ", abnormalBrief = " + abnormalBrief + ", abnormalDetail = " + abnormalDetail + ", memo = " + memo
				+ ", isDeleted = " + isDeleted + ", createTime = " + createTime + ", createTimeStart = "
				+ createTimeStart + ", createTimeEnd = " + createTimeEnd + ", updateTime = " + updateTime
				+ ", updateTimeStart = " + updateTimeStart + ", updateTimeEnd = " + updateTimeEnd + ", operatorType = "
				+ operatorType + ", operatorId = " + operatorId + "]";
	}

	public String getPhotourl1() {
		return photourl1;
	}

	public void setPhotourl1(String photourl1) {
		this.photourl1 = photourl1;
	}

	public String getPhotourl2() {
		return photourl2;
	}

	public void setPhotourl2(String photourl2) {
		this.photourl2 = photourl2;
	}

	public String getPhotourl3() {
		return photourl3;
	}

	public void setPhotourl3(String photourl3) {
		this.photourl3 = photourl3;
	}
}
