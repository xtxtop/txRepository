package cn.com.shopec.core.car.model;

import java.util.Date;

import cn.com.shopec.core.common.Entity;

/** 
 * 蓝牙接口传入数据  数据实体类
 */
public class CarStatusBlue extends Entity<String> {
	
	private static final long serialVersionUID = 1l;
	//当前时间，车辆状态（启动，熄火），车门状态，档位，车速，总里程，电瓶电压，
	//总电压，总电流，剩余电量，续航里程，充电状态，网络信号值，定位状态，卫星有效数量，
	//定位经度，定位纬度,车牌号，设备号
	/*Auto generated properties start*/
	//车牌号
	private String carPlateNo;
	//设备号
	private String deviceNo;
	//当前时间
	private String nowDate;
	//车辆状态（启动，熄火)
	private Integer carStatus;
	//车门状态
	private Integer carDoorStatus;
	//档位
	private Integer gears;
	//车速
	private Double speed;
	//总里程
	private Double mileage;
	//电瓶电压
	private Double auxBatteryVoltage;
	//总电压
	private Double voltage;
	//总电流
	private Double electricity;
	//剩余电量
	private Double power;
	//续航里程
	private Double rangeMileage;
	//充电状态
	private Integer chargeState;
	//网络信号值
	private Integer networkSignalValue;
	//定位状态
	private Integer positionStatus;
	//卫星有效数量
	private Integer satelliteNum;
	//坐标经度
	private Double longitude;
	//坐标纬度
	private Double latitude;
	
	public String getCarPlateNo() {
		return carPlateNo;
	}
	public void setCarPlateNo(String carPlateNo) {
		this.carPlateNo = carPlateNo;
	}
	public String getDeviceNo() {
		return deviceNo;
	}
	public void setDeviceNo(String deviceNo) {
		this.deviceNo = deviceNo;
	}
	public String getNowDate() {
		return nowDate;
	}
	public void setNowDate(String nowDate) {
		this.nowDate = nowDate;
	}
	public Integer getCarStatus() {
		return carStatus;
	}
	public void setCarStatus(Integer carStatus) {
		this.carStatus = carStatus;
	}
	public Integer getCarDoorStatus() {
		return carDoorStatus;
	}
	public void setCarDoorStatus(Integer carDoorStatus) {
		this.carDoorStatus = carDoorStatus;
	}
	public Integer getGears() {
		return gears;
	}
	public void setGears(Integer gears) {
		this.gears = gears;
	}
	public Double getSpeed() {
		return speed;
	}
	public void setSpeed(Double speed) {
		this.speed = speed;
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
	public Double getVoltage() {
		return voltage;
	}
	public void setVoltage(Double voltage) {
		this.voltage = voltage;
	}
	public Double getElectricity() {
		return electricity;
	}
	public void setElectricity(Double electricity) {
		this.electricity = electricity;
	}
	public Double getPower() {
		return power;
	}
	public void setPower(Double power) {
		this.power = power;
	}
	public Double getRangeMileage() {
		return rangeMileage;
	}
	public void setRangeMileage(Double rangeMileage) {
		this.rangeMileage = rangeMileage;
	}
	public Integer getChargeState() {
		return chargeState;
	}
	public void setChargeState(Integer chargeState) {
		this.chargeState = chargeState;
	}
	public Integer getNetworkSignalValue() {
		return networkSignalValue;
	}
	public void setNetworkSignalValue(Integer networkSignalValue) {
		this.networkSignalValue = networkSignalValue;
	}
	public Integer getPositionStatus() {
		return positionStatus;
	}
	public void setPositionStatus(Integer positionStatus) {
		this.positionStatus = positionStatus;
	}
	public Integer getSatelliteNum() {
		return satelliteNum;
	}
	public void setSatelliteNum(Integer satelliteNum) {
		this.satelliteNum = satelliteNum;
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
	
}
