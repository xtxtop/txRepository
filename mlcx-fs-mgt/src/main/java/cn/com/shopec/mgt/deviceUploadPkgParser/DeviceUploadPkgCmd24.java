package cn.com.shopec.mgt.deviceUploadPkgParser;

/**
 * 设备上报数据包-24命令(设备实时状态数据包）
 *
 */
public class DeviceUploadPkgCmd24 extends DeviceUploadPkg {

	private static final long serialVersionUID = 1366968618235062497L;

	private String timestamp; //上报时间戳

	private String keyStatus; //钥匙状态
	
	private String carDoorStatus; //车门状态
	
	private String gear; //档位
	
	private Double speed; //车速（单位 km/h）
	
	private Double totalMileage; //总里程（单位 km）
	
	private Double auxBatteryVoltage; //汽车电瓶电压（单位 V）
	
	private Double voltage; //总电压 （单位 V）
	
	private Double current; //总电流 （单位 A，+表示充电， -表示放电）

	private Double remainPower; //剩余电量百分比（单位 %）
	
	private Double range; //续航里程 （单位 km）
	
	private String chargingStatus; //充电状态 （0，未充电、1，正在充电）
	
	private Double signal; // 网络信号值
	
	private String locationStatus; //定位状态（0，未定位、1，已定位）
	
	private Integer satelliteNum; //卫星有效数量
	
	private LongitudeAndLatitude location; //当前坐标经纬度
	
	private Double courseAngle; //航向角度;
	
	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public String getKeyStatus() {
		return keyStatus;
	}

	public void setKeyStatus(String keyStatus) {
		this.keyStatus = keyStatus;
	}

	public String getCarDoorStatus() {
		return carDoorStatus;
	}

	public void setCarDoorStatus(String carDoorStatus) {
		this.carDoorStatus = carDoorStatus;
	}

	public String getGear() {
		return gear;
	}

	public void setGear(String gear) {
		this.gear = gear;
	}

	public Double getSpeed() {
		return speed;
	}

	public void setSpeed(Double speed) {
		this.speed = speed;
	}

	public Double getTotalMileage() {
		return totalMileage;
	}

	public void setTotalMileage(Double totalMileage) {
		this.totalMileage = totalMileage;
	}

	public Double getAuxBatteryVoltage() {
		return auxBatteryVoltage;
	}

	public void setAuxBatteryVoltage(Double auxBatteryVoltage) {
		this.auxBatteryVoltage = auxBatteryVoltage;
	}

	public Double getVoltage() {
		return voltage;
	}

	public void setVoltage(Double voltage) {
		this.voltage = voltage;
	}

	public Double getCurrent() {
		return current;
	}

	public void setCurrent(Double current) {
		this.current = current;
	}

	public Double getRemainPower() {
		return remainPower;
	}

	public void setRemainPower(Double remainPower) {
		this.remainPower = remainPower;
	}

	public Double getRange() {
		return range;
	}

	public void setRange(Double range) {
		this.range = range;
	}

	public String getChargingStatus() {
		return chargingStatus;
	}

	public void setChargingStatus(String chargingStatus) {
		this.chargingStatus = chargingStatus;
	}

	public Double getSignal() {
		return signal;
	}

	public void setSignal(Double signal) {
		this.signal = signal;
	}

	public String getLocationStatus() {
		return locationStatus;
	}

	public void setLocationStatus(String locationStatus) {
		this.locationStatus = locationStatus;
	}

	public Integer getSatelliteNum() {
		return satelliteNum;
	}

	public void setSatelliteNum(Integer satelliteNum) {
		this.satelliteNum = satelliteNum;
	}

	public LongitudeAndLatitude getLocation() {
		return location;
	}

	public void setLocation(LongitudeAndLatitude location) {
		this.location = location;
	}

	public Double getCourseAngle() {
		return courseAngle;
	}

	public void setCourseAngle(Double courseAngle) {
		this.courseAngle = courseAngle;
	}

	@Override
	public String toString() {
		return "DeviceUploadPkgCmd24 [timestamp=" + timestamp + ", keyStatus=" + keyStatus + ", carDoorStatus="
				+ carDoorStatus + ", gear=" + gear + ", speed=" + speed + ", totalMileage=" + totalMileage
				+ ", auxBatteryVoltage=" + auxBatteryVoltage + ", voltage=" + voltage + ", current=" + current
				+ ", remainPower=" + remainPower + ", range=" + range + ", chargingStatus=" + chargingStatus
				+ ", signal=" + signal + ", locationStatus=" + locationStatus + ", satelliteNum=" + satelliteNum
				+ ", location=" + location + ", courseAngle=" + courseAngle + "]";
	}

	
}
