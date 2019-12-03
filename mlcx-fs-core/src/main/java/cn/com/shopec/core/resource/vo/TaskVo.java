package cn.com.shopec.core.resource.vo;

import java.util.Date;

public class TaskVo {

	// 任务编号：调度或巡检
	private String taskNo;
	// 车牌号
	private String carPlateNo;
	// 任务类型 -- 调度：0-挪车 1-洗车 2-维修 3-保养，巡检 1、场站巡检
	private String type;
	// 任务（调度、巡检）状态 -- 0-未下发 1-已下发 2-调度中 3-已结束4-已取消
	private String taskStatus;
	// 剩余电量
	private String power;
	// 可续航里程（km）
	private String rangeMileage;
	// 品牌名称
	private String carBrandName;
	// 车型名称
	private String carModelName;
	// 场站名称：调度或巡检
	private String parkName;
	// 街道：调度或巡检
	private String addrStreet;
	// 下发时间：调度或巡检
	private Date sendTime;
	private String sdTime;
	// 照片url：调度或巡检
	private String photoUrl;
	// 距离
	private String distance;
	
	public String getTaskNo() {
		return taskNo;
	}

	public void setTaskNo(String taskNo) {
		this.taskNo = taskNo;
	}

	public String getCarPlateNo() {
		return carPlateNo;
	}

	public void setCarPlateNo(String carPlateNo) {
		this.carPlateNo = carPlateNo;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPower() {
		return power;
	}

	public void setPower(String power) {
		this.power = power;
	}

	public String getRangeMileage() {
		return rangeMileage;
	}

	public void setRangeMileage(String rangeMileage) {
		this.rangeMileage = rangeMileage;
	}

	public String getCarBrandName() {
		return carBrandName;
	}

	public void setCarBrandName(String carBrandName) {
		this.carBrandName = carBrandName;
	}

	public String getCarModelName() {
		return carModelName;
	}

	public void setCarModelName(String carModelName) {
		this.carModelName = carModelName;
	}

	public String getParkName() {
		return parkName;
	}

	public void setParkName(String parkName) {
		this.parkName = parkName;
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

	public String getPhotoUrl() {
		return photoUrl;
	}

	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}

	public String getDistance() {
		return distance;
	}

	public void setDistance(String distance) {
		this.distance = distance;
	}

	public String getTaskStatus() {
		return taskStatus;
	}

	public void setTaskStatus(String taskStatus) {
		this.taskStatus = taskStatus;
	}

	@Override
	public String toString() {
		return "TaskVo [taskNo=" + taskNo + ", carPlateNo=" + carPlateNo + ", type=" + type + ", taskStatus="
				+ taskStatus + ", power=" + power + ", rangeMileage=" + rangeMileage + ", carBrandName=" + carBrandName
				+ ", carModelName=" + carModelName + ", parkName=" + parkName + ", sendTime=" + sendTime + ", sdTime="
				+ sdTime + ", photoUrl=" + photoUrl + ", distance=" + distance + "]";
	}

	public String getAddrStreet() {
		return addrStreet;
	}

	public void setAddrStreet(String addrStreet) {
		this.addrStreet = addrStreet;
	}

}
