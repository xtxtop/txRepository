package cn.com.shopec.core.car.vo;

public class CarWkVo {

	// 车辆编号
	private String carNo;
	// 车牌号
	private String carPlateNo;
	// 调度类型 -- 0-调度 1-救援 2-清洁 3-加油 4-维保
	private String type;
	// 使用状态（0，空闲、1，已预占、2，订单中、3，调度中）
	private Integer userageStatus;
	// 剩余电量百分比(0%~100%)
	private String power;
	// 工单编号
	private String workerOrderNo;
	// 品牌名称
	private String carBrandName;
	// 车型名称
	private String carModelName;
	// 场站名称
	private String parkName;
	// 距离
	private String distance;
	// 车辆照片url1
	private String carPhotoUrl1;
	// 坐标经度 圆形场站为中心经度，多边形场站为几何中心经度
	private String longitude;
	// 坐标纬度 圆形场站为中心纬度，多边形场站为几何中心纬度
	private String latitude;
	//所属片区
	private String regionName;
	// 充电状态，0 –未充电，1正在充电
	private Integer chargeState;
	//场站编号
	private  String locationParkNo;
	//最后上报时间
	private String deviceLastReportingTime;
	

	// 控制跳转 1:车辆详情，2：任务详情
	private String isJump;

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

	public Integer getUserageStatus() {
		return userageStatus;
	}

	public void setUserageStatus(Integer userageStatus) {
		this.userageStatus = userageStatus;
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

	public String getCarPhotoUrl1() {
		return carPhotoUrl1;
	}

	public void setCarPhotoUrl1(String carPhotoUrl1) {
		this.carPhotoUrl1 = carPhotoUrl1;
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

	public String getPower() {
		return power;
	}

	public void setPower(String power) {
		this.power = power;
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

	public String getWorkerOrderNo() {
		return workerOrderNo;
	}

	public void setWorkerOrderNo(String workerOrderNo) {
		this.workerOrderNo = workerOrderNo;
	}

	public String getIsJump() {
		return isJump;
	}

	public void setIsJump(String isJump) {
		this.isJump = isJump;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}


	public Integer getChargeState() {
		return chargeState;
	}

	public void setChargeState(Integer chargeState) {
		this.chargeState = chargeState;
	}

	public String getRegionName() {
		return regionName;
	}

	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}

	public String getLocationParkNo() {
		return locationParkNo;
	}

	public void setLocationParkNo(String locationParkNo) {
		this.locationParkNo = locationParkNo;
	}

	public String getDeviceLastReportingTime() {
		return deviceLastReportingTime;
	}

	public void setDeviceLastReportingTime(String deviceLastReportingTime) {
		this.deviceLastReportingTime = deviceLastReportingTime;
	}



}
