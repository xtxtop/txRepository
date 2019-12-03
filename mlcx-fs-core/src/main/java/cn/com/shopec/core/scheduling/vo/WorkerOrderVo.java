package cn.com.shopec.core.scheduling.vo;

import java.util.Date;

/**
 * WorkerOrder调度工单 数据实体类
 */
public class WorkerOrderVo {

	// 工单编号
	private String workerOrderNo;
	// 车牌号
	private String carPlateNo;
	// 调度类型 -- 0-挪车 1-洗车 2-维修 3-保养
	private String type;
	// 调度工单状态 -- 0-未下发 1-已下发 2-调度中 3-已结束4-已取消
	private String workOrderStatus;
	// 剩余电量
	private String power;
	// 可续航里程（km）
	private String rangeMileage;
	// 品牌名称
	private String carBrandName;
	// 车型名称
	private String carModelName;
	// 调度场站名称
	private String parkName;
	// 下发时间
	private Date sendTime;
	private String sdTime;
	// 车辆照片url1
	private String carPhotoUrl1;
	// 距离
	private String distance;

	public String getWorkerOrderNo() {
		return workerOrderNo;
	}

	public void setWorkerOrderNo(String workerOrderNo) {
		this.workerOrderNo = workerOrderNo;
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

	public String getWorkOrderStatus() {
		return workOrderStatus;
	}

	public void setWorkOrderStatus(String workOrderStatus) {
		this.workOrderStatus = workOrderStatus;
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

	public String getDistance() {
		return distance;
	}

	public void setDistance(String distance) {
		this.distance = distance;
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

	public String getCarPhotoUrl1() {
		return carPhotoUrl1;
	}

	public void setCarPhotoUrl1(String carPhotoUrl1) {
		this.carPhotoUrl1 = carPhotoUrl1;
	}

	@Override
	public String toString() {
		return "WorkerOrderVo [workerOrderNo=" + workerOrderNo + ", carPlateNo=" + carPlateNo + ", type=" + type
				+ ", workOrderStatus=" + workOrderStatus + ", power=" + power + ", rangeMileage=" + rangeMileage
				+ ", carBrandName=" + carBrandName + ", carModelName=" + carModelName + ", parkName=" + parkName
				+ ", sendTime=" + sendTime + ", sdTime=" + sdTime + ", carPhotoUrl1=" + carPhotoUrl1 + ", distance="
				+ distance + "]";
	}

}
