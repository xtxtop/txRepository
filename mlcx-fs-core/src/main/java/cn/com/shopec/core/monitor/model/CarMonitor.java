package cn.com.shopec.core.monitor.model;

import cn.com.shopec.core.common.Entity;
import cn.com.shopec.core.order.model.Order;
import cn.com.shopec.core.scheduling.model.WorkerOrder;

/**
 * 车辆状态表 数据实体类
 */
public class CarMonitor extends Entity<String> {

	private static final long serialVersionUID = 1l;

	/* Auto generated properties start */

	// 车辆编号
	private String carNo;
	// 车牌号
	private String carPlateNo;
	// 车辆所属城市id
	private String cityId;
	// 所在场站的编号
	private String locationParkNo;
	// 使用状态（0，空闲、1，已预占、2，订单中、3，调度中）
	private Integer userageStatus;
	// 速度，单位（km/h），取值范围0 ~ 255km/h
	private Integer speed;
	// 剩余电量百分比(0%~100%)
	private Double power;
	// 总里程
	private Double mileage;
	// 汽车电瓶电压，单位（V）取值范围（0.0 ~ 28.0V）
	private Double auxBatteryVoltage;
	// 充电状态，0 –未充电，1正在充电
	private Integer chargeState;
	// 冲电故障状态，0-无故障，1故障
	private Integer chargingFaultStatus;
	// 车辆状态（1、已启动，2、已熄火）
	private Integer carStatus;
	// 坐标经度
	private Double longitude;
	// 坐标纬度
	private Double latitude;
	// 可续航里程（km）
	private Double rangeMileage;
	// 坐标上报的时间差
	private Integer timeDiff;
	// 航向角度
	private Double courseAngle;
	// 标记，从其他位置跳转到监控页面值为1
	private String flag;
	// 当前时间
	private String nowDay;
	// 前一天时间
	private String preDay;
	/* Auto generated properties end */

	private Order order;

	private WorkerOrder workerOrder;

	// 实时报警（1.非订单，2.闲置24小时3.闲置48小时4.闲置72小时）
	private Integer realTimeAlarmType;

	private String warningNo;

	// 位置
	private String location;

	// 上线状态（0、下线，1、上线，默认0）
	private Integer onlineStatus;

	// 终端设备是否电量不足（1.是 0.否 ）
	private Integer isLowPower;

	// 终端状态（1、在线，2、节能，3、待机，4、离线，默认离线）
	private Integer deviceStatus;

	// 用车状态（0、正常用车，1、非正常用车，默认正常用车）
	private Integer useCarStatus;

	@Override
	public String getPK() {
		return carNo;
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

	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	public String getLocationParkNo() {
		return locationParkNo;
	}

	public void setLocationParkNo(String locationParkNo) {
		this.locationParkNo = locationParkNo;
	}

	public Integer getUserageStatus() {
		return userageStatus;
	}

	public void setUserageStatus(Integer userageStatus) {
		this.userageStatus = userageStatus;
	}

	public Integer getSpeed() {
		return speed;
	}

	public void setSpeed(Integer speed) {
		this.speed = speed;
	}

	public Double getPower() {
		return power;
	}

	public void setPower(Double power) {
		this.power = power;
	}

	public Double getMileage() {
		return mileage;
	}

	public void setMileage(Double mileage) {
		this.mileage = mileage;
	}

	public Double getAuxBatteryVoltage() {
		return auxBatteryVoltage;
	}

	public void setAuxBatteryVoltage(Double auxBatteryVoltage) {
		this.auxBatteryVoltage = auxBatteryVoltage;
	}

	public Integer getChargeState() {
		return chargeState;
	}

	public void setChargeState(Integer chargeState) {
		this.chargeState = chargeState;
	}

	public Integer getChargingFaultStatus() {
		return chargingFaultStatus;
	}

	public void setChargingFaultStatus(Integer chargingFaultStatus) {
		this.chargingFaultStatus = chargingFaultStatus;
	}

	public Integer getCarStatus() {
		return carStatus;
	}

	public void setCarStatus(Integer carStatus) {
		this.carStatus = carStatus;
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

	public Double getRangeMileage() {
		return rangeMileage;
	}

	public void setRangeMileage(Double rangeMileage) {
		this.rangeMileage = rangeMileage;
	}

	public Integer getTimeDiff() {
		return timeDiff;
	}

	public void setTimeDiff(Integer timeDiff) {
		this.timeDiff = timeDiff;
	}

	public Double getCourseAngle() {
		return courseAngle;
	}

	public void setCourseAngle(Double courseAngle) {
		this.courseAngle = courseAngle;
	}

	/* Auto generated methods end */

	/* Customized methods start */

	/* Customized methods end */

	@Override
	public String toString() {
		return "CarStatus [" + "carNo = " + carNo + ", carPlateNo = " + carPlateNo + ", cityId = " + cityId
				+ ", locationParkNo = " + locationParkNo + ", userageStatus = " + userageStatus + ", speed = " + speed
				+ ", power = " + power + ", mileage = " + mileage + ", auxBatteryVoltage = " + auxBatteryVoltage
				+ ", chargeState = " + chargeState + ", chargingFaultStatus = " + chargingFaultStatus + ", carStatus = "
				+ carStatus + ", longitude = " + longitude + ", latitude = " + latitude + ", rangeMileage = "
				+ rangeMileage + "]";
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getNowDay() {
		return nowDay;
	}

	public void setNowDay(String nowDay) {
		this.nowDay = nowDay;
	}

	public String getPreDay() {
		return preDay;
	}

	public void setPreDay(String preDay) {
		this.preDay = preDay;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public WorkerOrder getWorkerOrder() {
		return workerOrder;
	}

	public void setWorkerOrder(WorkerOrder workerOrder) {
		this.workerOrder = workerOrder;
	}

	public Integer getRealTimeAlarmType() {
		return realTimeAlarmType;
	}

	public void setRealTimeAlarmType(Integer realTimeAlarmType) {
		this.realTimeAlarmType = realTimeAlarmType;
	}

	public String getWarningNo() {
		return warningNo;
	}

	public void setWarningNo(String warningNo) {
		this.warningNo = warningNo;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Integer getOnlineStatus() {
		return onlineStatus;
	}

	public void setOnlineStatus(Integer onlineStatus) {
		this.onlineStatus = onlineStatus;
	}

	public Integer getIsLowPower() {
		return isLowPower;
	}

	public void setIsLowPower(Integer isLowPower) {
		this.isLowPower = isLowPower;
	}

	public Integer getDeviceStatus() {
		return deviceStatus;
	}

	public void setDeviceStatus(Integer deviceStatus) {
		this.deviceStatus = deviceStatus;
	}

	public Integer getUseCarStatus() {
		if(carStatus != null && carStatus.intValue() == 1 && userageStatus == null && !(userageStatus.intValue() == 2 || userageStatus.intValue() == 3)){
			return 1;
		}
		useCarStatus = 0;
		return useCarStatus;
	}

	public void setUseCarStatus(Integer useCarStatus) {
		this.useCarStatus = useCarStatus;
	}

}
