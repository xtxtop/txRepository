package cn.com.shopec.mapi.car.vo;

import java.io.Serializable;

public class CarStatusVo implements Serializable{
	/** 
	*
	* @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么) 
	*/ 
	private static final long serialVersionUID = 1L;
	
	//订单编号
	private String orderNo;
	//车辆编号
	private String carNo;
	//车牌号
	private String carPlateNo;
	//所在场站的编号
	private String locationParkNo;
	//使用状态（0，空闲、1，已预占、2，订单中、3，调度中）
	private Integer userageStatus;
	//速度，单位（km/h），取值范围0 ~ 255km/h
	private Integer speed;
	//剩余电量百分比(0%~100%)
	private Double power;
	//可续航里程（km）
	private Double rangeMileage;
	//充电状态，0 –未充电，1正在充电
	private Integer chargeState;
	//车辆状态（1、已启动，2、已熄火）
	private Integer carStatus;
	//终端编号
	private String deviceNo;
	//左前车门锁（1，已锁、2，未锁）
	private Integer leftFrontDoorLock;
	//右前车门锁（1，已锁、2，未锁）
	private Integer rightFrontDoorLock;
	//左后车门锁（1，已锁、2，未锁）
	private Integer leftRearDoorLock;
	//右右车门锁（1，已锁、2，未锁）
	private Integer rightRearDoorLock;
	//停放位置状态 (0、正常，1、不正常)
	private Integer placeStatus;
	//终端序列号
	private String deviceSn;
	//MAC地址
	private String macAddr;
	//deviceKey
	private String deviceKey;
	//钥匙状态（1，OFF、2，ON、3，ACC、4，START、5，未知）
	private Integer keyStatus;
	//钥匙在线状态（1、在线，0、不在线，默认1）
	private Integer keyOnlineStatus;
	//设备版本类型（0：标准版，1：江淮iev4/iev5专用版）
	private String versionType;
	
	public Integer getKeyStatus() {
		return keyStatus;
	}
	public void setKeyStatus(Integer keyStatus) {
		this.keyStatus = keyStatus;
	}
	public Integer getKeyOnlineStatus() {
		return keyOnlineStatus;
	}
	public void setKeyOnlineStatus(Integer keyOnlineStatus) {
		this.keyOnlineStatus = keyOnlineStatus;
	}
	public String getVersionType() {
		return versionType;
	}
	public void setVersionType(String versionType) {
		this.versionType = versionType;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
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
	public Integer getCarStatus() {
		return carStatus;
	}
	public void setCarStatus(Integer carStatus) {
		this.carStatus = carStatus;
	}
	public String getDeviceNo() {
		return deviceNo;
	}
	public void setDeviceNo(String deviceNo) {
		this.deviceNo = deviceNo;
	}
	
	public Integer getLeftFrontDoorLock() {
		return leftFrontDoorLock;
	}
	public void setLeftFrontDoorLock(Integer leftFrontDoorLock) {
		this.leftFrontDoorLock = leftFrontDoorLock;
	}
	public Integer getRightFrontDoorLock() {
		return rightFrontDoorLock;
	}
	public void setRightFrontDoorLock(Integer rightFrontDoorLock) {
		this.rightFrontDoorLock = rightFrontDoorLock;
	}
	public Integer getLeftRearDoorLock() {
		return leftRearDoorLock;
	}
	public void setLeftRearDoorLock(Integer leftRearDoorLock) {
		this.leftRearDoorLock = leftRearDoorLock;
	}
	public Integer getRightRearDoorLock() {
		return rightRearDoorLock;
	}
	public void setRightRearDoorLock(Integer rightRearDoorLock) {
		this.rightRearDoorLock = rightRearDoorLock;
	}
	public Integer getPlaceStatus() {
		return placeStatus;
	}
	public void setPlaceStatus(Integer placeStatus) {
		this.placeStatus = placeStatus;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	public String getDeviceSn() {
		return deviceSn;
	}
	public void setDeviceSn(String deviceSn) {
		this.deviceSn = deviceSn;
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
	@Override
	public String toString() {
		return "CarStatusVo [orderNo=" + orderNo + ", carNo=" + carNo + ", carPlateNo=" + carPlateNo
				+ ", locationParkNo=" + locationParkNo + ", userageStatus=" + userageStatus + ", speed=" + speed
				+ ", power=" + power + ", rangeMileage=" + rangeMileage + ", chargeState=" + chargeState
				+ ", carStatus=" + carStatus + ", deviceNo=" + deviceNo + ", leftFrontDoorLock=" + leftFrontDoorLock
				+ ", rightFrontDoorLock=" + rightFrontDoorLock + ", leftRearDoorLock=" + leftRearDoorLock
				+ ", rightRearDoorLock=" + rightRearDoorLock + ", placeStatus=" + placeStatus + ", deviceSn=" + deviceSn
				+ ", macAddr=" + macAddr + ", deviceKey=" + deviceKey + ", keyStatus=" + keyStatus
				+ ", keyOnlineStatus=" + keyOnlineStatus + "]";
	}
	
}
