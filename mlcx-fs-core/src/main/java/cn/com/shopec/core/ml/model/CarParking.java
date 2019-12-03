package cn.com.shopec.core.ml.model;

import java.util.Date;

import cn.com.shopec.core.common.Entity;

/**
 * 车位 数据实体类
 */
public class CarParking extends Entity<String> {

	private static final long serialVersionUID = 1l;

	/* Auto generated properties start */

	// 车位id
	private String carParkingNo;
	// 车位编码
	private String carParkingCode;
	// 状态(0.可用 1.不可用)
	private Integer activeCondition;
	// 场站编号
	private String stationNo;
	// 充电桩编号
	private String chargingPileNo;
	// 操作人id
	private String operatorId;
	// 操作人类型（0、系统自动操作，1、平台人员操作）
	private Integer operatorType;
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

	/* Auto generated properties end */

	/* Customized properties start */

	/* Customized properties end */

	/* Auto generated methods start */

	@Override
	public String getPK() {
		return carParkingNo;
	}

	public String getCarParkingNo() {
		return carParkingNo;
	}

	public void setCarParkingNo(String carParkingNo) {
		this.carParkingNo = carParkingNo;
	}

	public String getCarParkingCode() {
		return carParkingCode;
	}

	public void setCarParkingCode(String carParkingCode) {
		this.carParkingCode = carParkingCode;
	}

	public Integer getActiveCondition() {
		return activeCondition;
	}

	public void setActiveCondition(Integer activeCondition) {
		this.activeCondition = activeCondition;
	}

	public String getStationNo() {
		return stationNo;
	}

	public void setStationNo(String stationNo) {
		this.stationNo = stationNo;
	}

	public String getChargingPileNo() {
		return chargingPileNo;
	}

	public void setChargingPileNo(String chargingPileNo) {
		this.chargingPileNo = chargingPileNo;
	}

	public String getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(String operatorId) {
		this.operatorId = operatorId;
	}

	public Integer getOperatorType() {
		return operatorType;
	}

	public void setOperatorType(Integer operatorType) {
		this.operatorType = operatorType;
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

	/* Auto generated methods end */

	/* Customized methods start */

	/* Customized methods end */

	@Override
	public String toString() {
		return "CarParking [" + "carParkingNo = " + carParkingNo + ", carParkingCode = " + carParkingCode
				+ ", activeCondition = " + activeCondition + ", stationNo = " + stationNo + ", chargingPileNo = "
				+ chargingPileNo + ", operatorId = " + operatorId + ", operatorType = " + operatorType
				+ ", createTime = " + createTime + ", createTimeStart = " + createTimeStart + ", createTimeEnd = "
				+ createTimeEnd + ", updateTime = " + updateTime + ", updateTimeStart = " + updateTimeStart
				+ ", updateTimeEnd = " + updateTimeEnd + "]";
	}
}
