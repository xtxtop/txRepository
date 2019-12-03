package cn.com.shopec.core.resource.vo;

import java.util.Date;

/**
 * 巡检计划表 数据实体类
 */
public class CheckPlanDetailVo {

	// 巡检计划编号
	private String checkPlanNo;
	// 派单时间
	private Date sendTime;
	private String sdTime;
	// 计划完成
	private Date planDate;
	private String plDate;
	// 巡检类型:1、场站巡检
	private String planType;
	// 实际结束时间
	private Date actualEndTime;
	private String alEndTime;
	// 距离
	private String distance;
	// 剩余时间
	private String remainingTime;
	// 计划状态，1、待处理，2、处理中，3、已完成，4、已取消， 默认1
	private String planStatus;
	// 场站编号
	private String parkNo;
	// 坐标经度
	private String longitude;
	// 坐标纬度
	private String latitude;
	// 场站名称
	private String parkName;
	// 场站地址
	private String addrStreet;
	// 车位数
	private Integer parkingSpaceNumber;
	// 计划巡检项名称（各项间用半角逗号进行分割）
	private String checkItem;

	// 车辆数
	private String carNumber;
	// 充电桩数量
	private String chargerNumber;
	// 已巡检
	private String checkResult;

	public String getCheckPlanNo() {
		return checkPlanNo;
	}

	public void setCheckPlanNo(String checkPlanNo) {
		this.checkPlanNo = checkPlanNo;
	}

	public Date getSendTime() {
		return sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}

	public String getSdTime() {
		return sdTime;
	}

	public void setSdTime(String sdTime) {
		this.sdTime = sdTime;
	}

	public Date getPlanDate() {
		return planDate;
	}

	public void setPlanDate(Date planDate) {
		this.planDate = planDate;
	}

	public String getPlDate() {
		return plDate;
	}

	public void setPlDate(String plDate) {
		this.plDate = plDate;
	}

	public String getPlanType() {
		return planType;
	}

	public void setPlanType(String planType) {
		this.planType = planType;
	}

	public String getDistance() {
		return distance;
	}

	public void setDistance(String distance) {
		this.distance = distance;
	}

	public String getRemainingTime() {
		return remainingTime;
	}

	public void setRemainingTime(String remainingTime) {
		this.remainingTime = remainingTime;
	}

	public String getPlanStatus() {
		return planStatus;
	}

	public void setPlanStatus(String planStatus) {
		this.planStatus = planStatus;
	}

	public String getParkName() {
		return parkName;
	}

	public void setParkName(String parkName) {
		this.parkName = parkName;
	}

	public String getAddrStreet() {
		return addrStreet;
	}

	public void setAddrStreet(String addrStreet) {
		this.addrStreet = addrStreet;
	}

	public String getCheckItem() {
		return checkItem;
	}

	public void setCheckItem(String checkItem) {
		this.checkItem = checkItem;
	}

	public String getCarNumber() {
		return carNumber;
	}

	public void setCarNumber(String carNumber) {
		this.carNumber = carNumber;
	}

	public String getChargerNumber() {
		return chargerNumber;
	}

	public void setChargerNumber(String chargerNumber) {
		this.chargerNumber = chargerNumber;
	}

	public String getCheckResult() {
		return checkResult;
	}

	public void setCheckResult(String checkResult) {
		this.checkResult = checkResult;
	}

	public Date getActualEndTime() {
		return actualEndTime;
	}

	public void setActualEndTime(Date actualEndTime) {
		this.actualEndTime = actualEndTime;
	}

	public String getAlEndTime() {
		return alEndTime;
	}

	public void setAlEndTime(String alEndTime) {
		this.alEndTime = alEndTime;
	}

	public Integer getParkingSpaceNumber() {
		return parkingSpaceNumber;
	}

	public void setParkingSpaceNumber(Integer parkingSpaceNumber) {
		this.parkingSpaceNumber = parkingSpaceNumber;
	}

	public String getParkNo() {
		return parkNo;
	}

	public void setParkNo(String parkNo) {
		this.parkNo = parkNo;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

}
