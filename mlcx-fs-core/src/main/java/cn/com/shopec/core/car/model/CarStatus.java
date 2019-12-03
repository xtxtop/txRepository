package cn.com.shopec.core.car.model;

import java.util.Date;

import cn.com.shopec.core.common.Entity;

/**
 * 车辆状态表 数据实体类
 */
public class CarStatus extends Entity<String> {

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
	// 左前车门（3，未关闭、5，关闭）
	private Integer leftFrontDoor;
	// 右前车门（3，未关闭、5，关闭）
	private Integer rightFrontDoor;
	// 左后车门（3，未关闭、5，关闭）
	private Integer leftRearDoor;
	// 右后车门（3，未关闭、5，关闭）
	private Integer rightRearDoor;
	// 后备箱（3，未关闭、5，关闭）
	private Integer trunk;
	// 左前车窗（3，未关闭、5，关闭）
	private Integer leftFrontWindow;
	// 右前车窗（3，未关闭、5，关闭）
	private Integer rightFrontWindow;
	// 左后车窗（3，未关闭、5，关闭）
	private Integer leftRearWindow;
	// 右后车窗（3，未关闭、5，关闭）
	private Integer rightRearWindow;
	// 天窗（3，未关闭、4，半开、5，关闭）
	private Integer sunroof;
	// 左前车门锁（1，已锁、2，未锁）
	private Integer leftFrontDoorLock;
	// 右前车门锁（1，已锁、2，未锁）
	private Integer rightFrontDoorLock;
	// 左后车门锁（1，已锁、2，未锁）
	private Integer leftRearDoorLock;
	// 右右车门锁（1，已锁、2，未锁）
	private Integer rightRearDoorLock;
	// 钥匙状态（1，OFF、2，ON、3，ACC、4，START、5，未知）
	private Integer keyStatus;
	// 终端编号
	private String deviceNo;
	// 最后上报时间
	private Date lastReportingTime;
	// 最后上报时间 时间范围起（查询用）
	private Date lastReportingTimeStart;
	// 最后上报时间 时间范围止（查询用）
	private Date lastReportingTimeEnd;
	// GPS数据最后上报时间
	private Date gpsLastReportingTime;
	// GPS数据最后上报时间 时间范围起（查询用）
	private Date gpsLastReportingTimeStart;
	// GPS数据最后上报时间 时间范围止（查询用）
	private Date gpsLastReportingTimeEnd;
	// BMS数据最后上报时间
	private Date bmsLastReportingTime;
	// BMS数据最后上报时间 时间范围起（查询用）
	private Date bmsLastReportingTimeStart;
	// BMS数据最后上报时间 时间范围止（查询用）
	private Date bmsLastReportingTimeEnd;
	// 车辆启动熄火数据最后上报时间
	private Date csLastReportingTime;
	// 最后经由APP上报车辆状态的时间
	private Date appLastReportingTime;
	// 创建时间
	private Date createTime;
	// 创建时间 时间范围起（查询用）
	private Date createTimeStart;
	// 创建时间 时间范围止（查询用）
	private Date createTimeEnd;
	// 更新时间
	private Date updateTime;
	// 更新时间 时间范围起（查询用）
	private Date updateTimeStart;
	// 更新时间 时间范围止（查询用）
	private Date updateTimeEnd;
	// 操作人id
	private String operatorId;
	// 操作人类型
	private Integer operatorType;
	//车辆类型(1 电量 2 燃油)
	private  Integer vehicleType;
	// 钥匙在线状态（1、在线，0、不在线，默认1）
	private Integer keyOnlineStatus;
	// 是否有电
	private Integer isPower;
	/* Auto generated properties end */

	// 城市名称
	private String cityName;
	// 租赁类型（1、分时，2、长租）
	private Integer leaseType;
	// 车主名称
	private String carOwnerName;
	// 上线状态（0、下线，1、上线，默认0）
	private Integer onlineStatus;
	// 位置
	private String position;
	// 终端状态（1、在线，2、节能，3、待机，4、离线，默认离线）
	private Integer deviceStatus;
	// 终端序列号
	private String deviceSn;
	// 剩余电量百分比(0%~100%)(后台查询用勿删)
	private Double powerSmall;
	// 剩余电量百分比(0%~100%)(后台查询用勿删)
	private Double powerBig;
	// 总里程(后台查询用勿删)
	private Double mileageSmall;
	// 总里程(后台查询用勿删)
	private Double mileageBig;

	private Integer carSpaceShortage;
	// 标记，从其他位置跳转到监控页面值为1
	private String flag;
	// 后台车辆状态列表，判断当前用户是不是系统管理员角色（1：是0：不是）
	private Integer roleAdminTag;
	// 后台实时报警类型（1.非订单，2.闲置24小时3.闲置48小时4.闲置72小时）
	private Integer realTimeAlarmType;

	// 警告编号
	private String warningNo;
	
	//终端设备型号
	private String deviceModel;
	
	// 终端设备是否在线（做页面列表显示字段用; 1.在线 2.不在线 ）
	private Integer isOnline;

	// 终端设备是否小电瓶低电（1.是 0.否 ）
	private Integer isMinLowPower;

	// 终端设备小电瓶低电分界值
	private Integer minLowPowerValue;

	// 终端设备是否电量不足（1.是 0.否 ）
	private Integer isLowPower;

	// 终端设备是否电量分界值
	private Integer lowPowerValue;

	// 终端设备是否断线（1.是 0.否 ）
	private Integer isBrokenLine;

	// 最后上报时间
	private Date deviceLastReportingTime;
	//信号强度等级值(1、非常差 ，2、差，3、一般，4、好  5、非常好)(>=0 到 < 7  非常差 >=7 到 < 13 差 >=13 到 < 19 一般 >=19 到 < 25 好 >=25 到 <= 31  非常好)
	private Integer signalStrengthLevel;
	//订单最后时间
	private Date orderFinishTime;
	//空闲时间（后台空闲车辆列表使用）
	private String idleTime;
	//定位状态（0 未定位 1 定位）
	private  Integer locationType;
	/* Customized properties start */

	/* Customized properties end */

	/* Auto generated methods start */

	@Override
	public String getPK() {
		return carNo;
	}

	public Double getPowerBig() {
		return powerBig;
	}

	public void setPowerBig(Double powerBig) {
		this.powerBig = powerBig;
	}

	public Double getMileageBig() {
		return mileageBig;
	}

	public void setMileageBig(Double mileageBig) {
		this.mileageBig = mileageBig;
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

	public Integer getLeftFrontDoor() {
		return leftFrontDoor;
	}

	public void setLeftFrontDoor(Integer leftFrontDoor) {
		this.leftFrontDoor = leftFrontDoor;
	}

	public Integer getRightFrontDoor() {
		return rightFrontDoor;
	}

	public void setRightFrontDoor(Integer rightFrontDoor) {
		this.rightFrontDoor = rightFrontDoor;
	}

	public Integer getLeftRearDoor() {
		return leftRearDoor;
	}

	public void setLeftRearDoor(Integer leftRearDoor) {
		this.leftRearDoor = leftRearDoor;
	}

	public Integer getRightRearDoor() {
		return rightRearDoor;
	}

	public void setRightRearDoor(Integer rightRearDoor) {
		this.rightRearDoor = rightRearDoor;
	}

	public Integer getTrunk() {
		return trunk;
	}

	public void setTrunk(Integer trunk) {
		this.trunk = trunk;
	}

	public Integer getLeftFrontWindow() {
		return leftFrontWindow;
	}

	public void setLeftFrontWindow(Integer leftFrontWindow) {
		this.leftFrontWindow = leftFrontWindow;
	}

	public Integer getRightFrontWindow() {
		return rightFrontWindow;
	}

	public void setRightFrontWindow(Integer rightFrontWindow) {
		this.rightFrontWindow = rightFrontWindow;
	}

	public Integer getLeftRearWindow() {
		return leftRearWindow;
	}

	public void setLeftRearWindow(Integer leftRearWindow) {
		this.leftRearWindow = leftRearWindow;
	}

	public Integer getRightRearWindow() {
		return rightRearWindow;
	}

	public void setRightRearWindow(Integer rightRearWindow) {
		this.rightRearWindow = rightRearWindow;
	}

	public Integer getSunroof() {
		return sunroof;
	}

	public void setSunroof(Integer sunroof) {
		this.sunroof = sunroof;
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

	public Integer getKeyStatus() {
		return keyStatus;
	}

	public void setKeyStatus(Integer keyStatus) {
		this.keyStatus = keyStatus;
	}

	public String getDeviceNo() {
		return deviceNo;
	}

	public void setDeviceNo(String deviceNo) {
		this.deviceNo = deviceNo;
	}

	public Date getLastReportingTime() {
		return lastReportingTime;
	}

	public void setLastReportingTime(Date lastReportingTime) {
		this.lastReportingTime = lastReportingTime;
	}

	public Date getLastReportingTimeStart() {
		return lastReportingTimeStart;
	}

	public void setLastReportingTimeStart(Date lastReportingTimeStart) {
		this.lastReportingTimeStart = lastReportingTimeStart;
	}

	public Date getLastReportingTimeEnd() {
		return lastReportingTimeEnd;
	}

	public void setLastReportingTimeEnd(Date lastReportingTimeEnd) {
		this.lastReportingTimeEnd = lastReportingTimeEnd;
	}

	public Date getGpsLastReportingTime() {
		return gpsLastReportingTime;
	}

	public void setGpsLastReportingTime(Date gpsLastReportingTime) {
		this.gpsLastReportingTime = gpsLastReportingTime;
	}

	public Date getGpsLastReportingTimeStart() {
		return gpsLastReportingTimeStart;
	}

	public void setGpsLastReportingTimeStart(Date gpsLastReportingTimeStart) {
		this.gpsLastReportingTimeStart = gpsLastReportingTimeStart;
	}

	public Date getGpsLastReportingTimeEnd() {
		return gpsLastReportingTimeEnd;
	}

	public void setGpsLastReportingTimeEnd(Date gpsLastReportingTimeEnd) {
		this.gpsLastReportingTimeEnd = gpsLastReportingTimeEnd;
	}

	public Date getBmsLastReportingTime() {
		return bmsLastReportingTime;
	}

	public void setBmsLastReportingTime(Date bmsLastReportingTime) {
		this.bmsLastReportingTime = bmsLastReportingTime;
	}

	public Date getBmsLastReportingTimeStart() {
		return bmsLastReportingTimeStart;
	}

	public void setBmsLastReportingTimeStart(Date bmsLastReportingTimeStart) {
		this.bmsLastReportingTimeStart = bmsLastReportingTimeStart;
	}

	public Date getBmsLastReportingTimeEnd() {
		return bmsLastReportingTimeEnd;
	}

	public void setBmsLastReportingTimeEnd(Date bmsLastReportingTimeEnd) {
		this.bmsLastReportingTimeEnd = bmsLastReportingTimeEnd;
	}

	public Date getCsLastReportingTime() {
		return csLastReportingTime;
	}

	public void setCsLastReportingTime(Date csLastReportingTime) {
		this.csLastReportingTime = csLastReportingTime;
	}

	public Date getAppLastReportingTime() {
		return appLastReportingTime;
	}

	public void setAppLastReportingTime(Date appLastReportingTime) {
		this.appLastReportingTime = appLastReportingTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getCreateTimeStart() {
		return createTimeStart;
	}

	public void setCreateTimeStart(Date createTimeStart) {
		this.createTimeStart = createTimeStart;
	}

	public Date getCreateTimeEnd() {
		return createTimeEnd;
	}

	public void setCreateTimeEnd(Date createTimeEnd) {
		this.createTimeEnd = createTimeEnd;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Date getUpdateTimeStart() {
		return updateTimeStart;
	}

	public void setUpdateTimeStart(Date updateTimeStart) {
		this.updateTimeStart = updateTimeStart;
	}

	public Date getUpdateTimeEnd() {
		return updateTimeEnd;
	}

	public void setUpdateTimeEnd(Date updateTimeEnd) {
		this.updateTimeEnd = updateTimeEnd;
	}

	public String getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(String operatorId) {
		this.operatorId = operatorId;
	}

	public Integer getOperatorType() {
		return operatorType;
	}

	public void setOperatorType(Integer operatorType) {
		this.operatorType = operatorType;
	}

	public Integer getKeyOnlineStatus() {
		return keyOnlineStatus;
	}

	public void setKeyOnlineStatus(Integer keyOnlineStatus) {
		this.keyOnlineStatus = keyOnlineStatus;
	}

	/* Auto generated methods end */

	/* Customized methods start */

	/* Customized methods end */

	public Integer getIsPower() {
		return isPower;
	}

	public void setIsPower(Integer isPower) {
		this.isPower = isPower;
	}

	public Integer getRoleAdminTag() {
		return roleAdminTag;
	}

	public void setRoleAdminTag(Integer roleAdminTag) {
		this.roleAdminTag = roleAdminTag;
	}

	@Override
	public String toString() {
		return "CarStatus [carNo=" + carNo + ", carPlateNo=" + carPlateNo + ", cityId=" + cityId + ", locationParkNo="
				+ locationParkNo + ", userageStatus=" + userageStatus + ", speed=" + speed + ", power=" + power
				+ ", mileage=" + mileage + ", auxBatteryVoltage=" + auxBatteryVoltage + ", chargeState=" + chargeState
				+ ", chargingFaultStatus=" + chargingFaultStatus + ", carStatus=" + carStatus + ", longitude="
				+ longitude + ", latitude=" + latitude + ", rangeMileage=" + rangeMileage + ", leftFrontDoor="
				+ leftFrontDoor + ", rightFrontDoor=" + rightFrontDoor + ", leftRearDoor=" + leftRearDoor
				+ ", rightRearDoor=" + rightRearDoor + ", trunk=" + trunk + ", leftFrontWindow=" + leftFrontWindow
				+ ", rightFrontWindow=" + rightFrontWindow + ", leftRearWindow=" + leftRearWindow + ", rightRearWindow="
				+ rightRearWindow + ", sunroof=" + sunroof + ", leftFrontDoorLock=" + leftFrontDoorLock
				+ ", rightFrontDoorLock=" + rightFrontDoorLock + ", leftRearDoorLock=" + leftRearDoorLock
				+ ", rightRearDoorLock=" + rightRearDoorLock + ", keyStatus=" + keyStatus + ", deviceNo=" + deviceNo
				+ ", lastReportingTime=" + lastReportingTime + ", lastReportingTimeStart=" + lastReportingTimeStart
				+ ", lastReportingTimeEnd=" + lastReportingTimeEnd + ", gpsLastReportingTime=" + gpsLastReportingTime
				+ ", gpsLastReportingTimeStart=" + gpsLastReportingTimeStart + ", gpsLastReportingTimeEnd="
				+ gpsLastReportingTimeEnd + ", bmsLastReportingTime=" + bmsLastReportingTime
				+ ", bmsLastReportingTimeStart=" + bmsLastReportingTimeStart + ", bmsLastReportingTimeEnd="
				+ bmsLastReportingTimeEnd + ", csLastReportingTime=" + csLastReportingTime + ", appLastReportingTime="
				+ appLastReportingTime + ", createTime=" + createTime + ", createTimeStart=" + createTimeStart
				+ ", createTimeEnd=" + createTimeEnd + ", updateTime=" + updateTime + ", updateTimeStart="
				+ updateTimeStart + ", updateTimeEnd=" + updateTimeEnd + ", operatorId=" + operatorId
				+ ", operatorType=" + operatorType + ", keyOnlineStatus=" + keyOnlineStatus + ", cityName=" + cityName
				+ ", leaseType=" + leaseType + ", carOwnerName=" + carOwnerName + ", onlineStatus=" + onlineStatus
				+ ", position=" + position + ", deviceStatus=" + deviceStatus + ", powerBig=" + powerBig
				+ ", mileageBig=" + mileageBig + ", carSpaceShortage=" + carSpaceShortage + ", flag=" + flag + "]";
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public Integer getLeaseType() {
		return leaseType;
	}

	public void setLeaseType(Integer leaseType) {
		this.leaseType = leaseType;
	}

	public String getCarOwnerName() {
		return carOwnerName;
	}

	public void setCarOwnerName(String carOwnerName) {
		this.carOwnerName = carOwnerName;
	}

	public Integer getOnlineStatus() {
		return onlineStatus;
	}

	public void setOnlineStatus(Integer onlineStatus) {
		this.onlineStatus = onlineStatus;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public Integer getDeviceStatus() {
		return deviceStatus;
	}

	public void setDeviceStatus(Integer deviceStatus) {
		this.deviceStatus = deviceStatus;
	}

	public String getDeviceSn() {
		return deviceSn;
	}

	public void setDeviceSn(String deviceSn) {
		this.deviceSn = deviceSn;
	}

	public Integer getCarSpaceShortage() {
		return carSpaceShortage;
	}

	public void setCarSpaceShortage(Integer carSpaceShortage) {
		this.carSpaceShortage = carSpaceShortage;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
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

	public Double getPowerSmall() {
		return powerSmall;
	}

	public void setPowerSmall(Double powerSmall) {
		this.powerSmall = powerSmall;
	}

	public Double getMileageSmall() {
		return mileageSmall;
	}

	public void setMileageSmall(Double mileageSmall) {
		this.mileageSmall = mileageSmall;
	}

	public Integer getIsOnline() {
		return isOnline;
	}

	public void setIsOnline(Integer isOnline) {
		this.isOnline = isOnline;
	}

	public Integer getIsMinLowPower() {
		return isMinLowPower;
	}

	public void setIsMinLowPower(Integer isMinLowPower) {
		this.isMinLowPower = isMinLowPower;
	}

	public Integer getIsLowPower() {
		return isLowPower;
	}

	public void setIsLowPower(Integer isLowPower) {
		this.isLowPower = isLowPower;
	}

	public Integer getMinLowPowerValue() {
		return minLowPowerValue;
	}

	public void setMinLowPowerValue(Integer minLowPowerValue) {
		this.minLowPowerValue = minLowPowerValue;
	}

	public Integer getLowPowerValue() {
		return lowPowerValue;
	}

	public void setLowPowerValue(Integer lowPowerValue) {
		this.lowPowerValue = lowPowerValue;
	}

	public Integer getIsBrokenLine() {
		return isBrokenLine;
	}

	public void setIsBrokenLine(Integer isBrokenLine) {
		this.isBrokenLine = isBrokenLine;
	}

	public Date getDeviceLastReportingTime() {
		return deviceLastReportingTime;
	}

	public void setDeviceLastReportingTime(Date deviceLastReportingTime) {
		this.deviceLastReportingTime = deviceLastReportingTime;
	}

	public Integer getVehicleType() {
		return vehicleType;
	}

	public void setVehicleType(Integer vehicleType) {
		this.vehicleType = vehicleType;
	}

	public Integer getSignalStrengthLevel() {
		return signalStrengthLevel;
	}

	public void setSignalStrengthLevel(Integer signalStrengthLevel) {
		this.signalStrengthLevel = signalStrengthLevel;
	}

	public String getDeviceModel() {
		return deviceModel;
	}

	public void setDeviceModel(String deviceModel) {
		this.deviceModel = deviceModel;
	}

	public Date getOrderFinishTime() {
		return orderFinishTime;
	}

	public void setOrderFinishTime(Date orderFinishTime) {
		this.orderFinishTime = orderFinishTime;
	}

	public String getIdleTime() {
		return idleTime;
	}

	public void setIdleTime(String idleTime) {
		this.idleTime = idleTime;
	}

	public Integer getLocationType() {
		return locationType;
	}

	public void setLocationType(Integer locationType) {
		this.locationType = locationType;
	}

}
