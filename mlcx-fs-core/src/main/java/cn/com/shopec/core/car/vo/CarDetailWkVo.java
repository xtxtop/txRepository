package cn.com.shopec.core.car.vo;

public class CarDetailWkVo {

	// 车辆编号
	private String carNo;
	// 车牌号
	private String carPlateNo;
	// 上线状态（0、下线，1、上线，默认0）
	private Integer onlineStatus;
	// 品牌名称
	private String carBrandName;
	// 车型名称
	private String carModelName;
	// 坐标经度
	private Double longitude;
	// 坐标纬度
	private Double latitude;
	// 使用状态（0，空闲、1，已预占、2，订单中、3，调度中）
	private Integer userageStatus;
	// 车辆状态（1、已启动，2、已熄火）
	private Integer carStatus;
	// 可续航里程（km）
	private Double rangeMileage;
	// 剩余电量百分比(0%~100%)
	private String power;
	// 场站名称
	private String parkName;
	// 车辆照片url1
	private String carPhotoUrl1;
	// 终端状态（1、在线，2、节能，3、待机，4、离线，5、休眠，默认离线）
	private Integer deviceStatus;
	// 充电状态，0 –未充电，1正在充电
		private Integer chargeState;
		//信号强度等级值(1、非常差 ，2、差，3、一般，4、好  5、非常好)(>=0 到 < 7  非常差 >=7 到 < 13 差 >=13 到 < 19 一般 >=19 到 < 25 好 >=25 到 <= 31  非常好)
				private Integer signalStrengthLevel; 
		

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

	public Integer getOnlineStatus() {
		return onlineStatus;
	}

	public void setOnlineStatus(Integer onlineStatus) {
		this.onlineStatus = onlineStatus;
	}

	public Integer getCarStatus() {
		return carStatus;
	}

	public void setCarStatus(Integer carStatus) {
		this.carStatus = carStatus;
	}

	public Double getRangeMileage() {
		return rangeMileage;
	}

	public void setRangeMileage(Double rangeMileage) {
		this.rangeMileage = rangeMileage;
	}

	public String getPower() {
		return power;
	}

	public void setPower(String power) {
		this.power = power;
	}

	public Integer getDeviceStatus() {
		return deviceStatus;
	}

	public void setDeviceStatus(Integer deviceStatus) {
		this.deviceStatus = deviceStatus;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Integer getChargeState() {
		return chargeState;
	}

	public void setChargeState(Integer chargeState) {
		this.chargeState = chargeState;
	}

	public Integer getSignalStrengthLevel() {
		return signalStrengthLevel;
	}

	public void setSignalStrengthLevel(Integer signalStrengthLevel) {
		this.signalStrengthLevel = signalStrengthLevel;
	}
}
