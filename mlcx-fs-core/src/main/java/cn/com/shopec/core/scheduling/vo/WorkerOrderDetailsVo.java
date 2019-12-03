package cn.com.shopec.core.scheduling.vo;

import java.util.Date;

/**
 * WorkerOrder调度工单 数据实体类
 */
public class WorkerOrderDetailsVo {

	// 工单编号
	private String workerOrderNo;
	// 车辆编号
	private String carNo;
	// 调度员编号
	private String workerNo;
	// 车牌号
	private String carPlateNo;
	// 蓝牙名称
	private String blueToothName;
	// 调度类型 -- 0-挪车 1-洗车 2-维修 3-保养
	private String type;
	// 上线状态（0、下线，1、上线，默认0）
	private Integer onlineStatus;
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
	// MAC地址
	private String macAddr;
	// deviceKey
	private String deviceKey;
	// 终端编号
	private String terminalDeviceNo;
	// 场站地址
	private String addrStreet;
	// 距离
	private String distance;
	// 起点
	private String startParkName;
	// 派单时间
	private Date sendTime;
	private String sdTime;
	// 计划完成时间
	private Date planTime;
	private String pnTime;
	// 完成时间
	private Date finishTime;
	private String fhTime;
	// 剩余时间
	private String remainingTime;
	// 车辆照片url1
	private String carPhotoUrl1;
	// 目的地
	private String terminalParkName;
	// 车辆经度
	private String longitude;
	// 车辆纬度
	private String latitude;
	// 任务终点经度
	private String endLongitude;
	// 任务终点纬度
	private String endLatitude;
	// 终端序列号
	private String deviceSn;
	// 备注
	private String memo;
	// 是否为自己的任务，1是，2不是
	private String isWorker;
	//实际车地址
	private String currentAddress;
	//实际车经度
	private String currentLongitude;
	//实际车纬度
	private String currentLatitude;
	//信号强度等级值(1、非常差 ，2、差，3、一般，4、好  5、非常好)(>=0 到 < 7  非常差 >=7 到 < 13 差 >=13 到 < 19 一般 >=19 到 < 25 好 >=25 到 <= 31  非常好)
		private Integer signalStrengthLevel; 

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

	public Date getPlanTime() {
		return planTime;
	}

	public void setPlanTime(Date planTime) {
		this.planTime = planTime;
	}

	public String getTerminalParkName() {
		return terminalParkName;
	}

	public void setTerminalParkName(String terminalParkName) {
		this.terminalParkName = terminalParkName;
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

	public String getDeviceSn() {
		return deviceSn;
	}

	public void setDeviceSn(String deviceSn) {
		this.deviceSn = deviceSn;
	}

	public Date getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(Date finishTime) {
		this.finishTime = finishTime;
	}

	public String getSdTime() {
		return sdTime;
	}

	public void setSdTime(String sdTime) {
		this.sdTime = sdTime;
	}

	public String getPnTime() {
		return pnTime;
	}

	public void setPnTime(String pnTime) {
		this.pnTime = pnTime;
	}

	public String getFhTime() {
		return fhTime;
	}

	public void setFhTime(String fhTime) {
		this.fhTime = fhTime;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getRemainingTime() {
		return remainingTime;
	}

	public void setRemainingTime(String remainingTime) {
		this.remainingTime = remainingTime;
	}

	public String getCarPhotoUrl1() {
		return carPhotoUrl1;
	}

	public void setCarPhotoUrl1(String carPhotoUrl1) {
		this.carPhotoUrl1 = carPhotoUrl1;
	}

	@Override
	public String toString() {
		return "WorkerOrderDetailsVo [workerOrderNo=" + workerOrderNo + ", carPlateNo=" + carPlateNo + ", type=" + type
				+ ", workOrderStatus=" + workOrderStatus + ", power=" + power + ", rangeMileage=" + rangeMileage
				+ ", carBrandName=" + carBrandName + ", carModelName=" + carModelName + ", parkName=" + parkName
				+ ", distance=" + distance + ", sendTime=" + sendTime + ", sdTime=" + sdTime + ", planTime=" + planTime
				+ ", pnTime=" + pnTime + ", finishTime=" + finishTime + ", fhTime=" + fhTime + ", remainingTime="
				+ remainingTime + ", carPhotoUrl1=" + carPhotoUrl1 + ", terminalParkName=" + terminalParkName
				+ ", longitude=" + longitude + ", latitude=" + latitude + ", deviceSn=" + deviceSn + ", memo=" + memo
				+ "]";
	}

	public String getCarNo() {
		return carNo;
	}

	public void setCarNo(String carNo) {
		this.carNo = carNo;
	}

	public String getAddrStreet() {
		return addrStreet;
	}

	public void setAddrStreet(String addrStreet) {
		this.addrStreet = addrStreet;
	}

	public Integer getOnlineStatus() {
		return onlineStatus;
	}

	public void setOnlineStatus(Integer onlineStatus) {
		this.onlineStatus = onlineStatus;
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

	public String getMacAddr() {
		return macAddr;
	}

	public void setMacAddr(String macAddr) {
		this.macAddr = macAddr;
	}

	public String getDeviceKey() {
		return deviceKey;
	}

	public void setDeviceKey(String deviceKey) {
		this.deviceKey = deviceKey;
	}

	public String getIsWorker() {
		return isWorker;
	}

	public void setIsWorker(String isWorker) {
		this.isWorker = isWorker;
	}

	public String getWorkerNo() {
		return workerNo;
	}

	public void setWorkerNo(String workerNo) {
		this.workerNo = workerNo;
	}

	public String getTerminalDeviceNo() {
		return terminalDeviceNo;
	}

	public void setTerminalDeviceNo(String terminalDeviceNo) {
		this.terminalDeviceNo = terminalDeviceNo;
	}

	public String getBlueToothName() {
		return blueToothName;
	}

	public void setBlueToothName(String blueToothName) {
		this.blueToothName = blueToothName;
	}

	public String getStartParkName() {
		return startParkName;
	}

	public void setStartParkName(String startParkName) {
		this.startParkName = startParkName;
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

	public Integer getSignalStrengthLevel() {
		return signalStrengthLevel;
	}

	public void setSignalStrengthLevel(Integer signalStrengthLevel) {
		this.signalStrengthLevel = signalStrengthLevel;
	}

}
