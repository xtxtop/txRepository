package cn.com.shopec.core.device.model;

import java.util.Date;

import cn.com.shopec.core.common.Entity;

/**
 * Device 数据实体类
 */
public class Device extends Entity<String> {

	private static final long serialVersionUID = 1l;

	/* Auto generated properties start */

	// 终端编号
	private String terminalDeviceNo;
	// 终端序列号
	private String deviceSn;
	// 品牌id
	private String brandId;
	// 品牌名称
	private String brandName;
	// 设备型号Id(具体来自数据字典表)
	private String deviceModelId;
	// 设备型号
	private String deviceModel;
	// MAC地址
	private String macAddr;
	// 蓝牙名称
	private String bluetoothName;
	// SIM卡号
	private String simCardNo;
	// 城市id（具体来自数据字典表）
	private String cityId;
	// 城市名称
	private String cityName;
	// 车辆编号
	private String carNo;
	// 绑定车辆车牌
	private String carPlateNo;
	//车辆品牌
	private String carBrandName;
	//车辆型号
	private String carSeriesName;
	// 绑定时间
	private Date bindingTime;
	// 绑定时间 时间范围起（查询用）
	private Date bindingTimeStart;
	// 绑定时间 时间范围止（查询用）
	private Date bindingTimeEnd;
	// 终端状态（1、在线，2、节能，3、待机，4、离线，5、休眠，默认离线）
	private Integer deviceStatus;
	// 信号强度
	private Integer signalStrength;
	// 信号强度等级值(1、非常差 ，2、差，3、一般，4、好 5、非常好)(>=0 到 < 7 非常差 >=7 到 < 13 差 >=13 到 < 19
	// 一般 >=19 到 < 25 好 >=25 到 <= 31 非常好)
	private Integer signalStrengthLevel;
	// 最后上报时间
	private Date lastReportingTime;
	// 最后上报时间 时间范围起（查询用）
	private Date lastReportingTimeStart;
	// 最后上报时间 时间范围止（查询用）
	private Date lastReportingTimeEnd;
	// 版本号
	private String versionNumber;
	// 设备版本类型（0：标准版，1：江淮iev4/iev5专用版）
	private String versionType;
	// 是否可用（0，不可用、1，可用，默认1）
	private Integer isAvailable;
	// 启用停用备注
	private String availableMemo;
	// 可用状态更新时间
	private Date availableUpdateTime;
	// 可用状态更新时间 时间范围起（查询用）
	private Date availableUpdateTimeStart;
	// 可用状态更新时间 时间范围止（查询用）
	private Date availableUpdateTimeEnd;
	// 创建日期
	private Date createTime;
	// 创建日期 时间范围起（查询用）
	private Date createTimeStart;
	// 创建日期 时间范围止（查询用）
	private Date createTimeEnd;
	// 更新日期
	private Date updateTime;
	// 更新日期 时间范围起（查询用）
	private Date updateTimeStart;
	// 更新日期 时间范围止（查询用）
	private Date updateTimeEnd;
	// 操作人类型
	private Integer operatorType;
	// 操作人id
	private String operatorId;
	// 后台车辆状态列表，判断当前用户是不是系统管理员角色（1：是0：不是）
	private Integer roleAdminTag;
	// 终端设备是否在线（做页面列表显示字段用; 1.在线 2.不在线 ）
	private Integer isOnline;
	// 微信设备id
	private String wxDeviceId;
	//是否注册过(0,否，1、是)
	private String isRegist;
	//sim卡iccid
	private String iccid;
	//注册时间
	private String registTime;
	//vin码
	private String vin;
	//实时数据json
	private String realTimeData;
	//茉莉出行终端参数
	private String deviceParam;
	/* Auto generated properties end */

	/* Customized properties start */

	/* Customized properties end */

	/* Auto generated methods start */

	@Override
	public String getPK() {
		return terminalDeviceNo;
	}

	public String getTerminalDeviceNo() {
		return terminalDeviceNo;
	}

	public void setTerminalDeviceNo(String terminalDeviceNo) {
		this.terminalDeviceNo = terminalDeviceNo;
	}

	public String getDeviceSn() {
		return deviceSn;
	}
	
	
	public String getCarBrandName() {
		return carBrandName;
	}

	public void setCarBrandName(String carBrandName) {
		this.carBrandName = carBrandName;
	}

	public String getCarSeriesName() {
		return carSeriesName;
	}

	public void setCarSeriesName(String carSeriesName) {
		this.carSeriesName = carSeriesName;
	}

	public void setDeviceSn(String deviceSn) {
		this.deviceSn = deviceSn;
	}

	public String getBrandId() {
		return brandId;
	}

	public void setBrandId(String brandId) {
		this.brandId = brandId;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public String getDeviceModelId() {
		return deviceModelId;
	}

	public void setDeviceModelId(String deviceModelId) {
		this.deviceModelId = deviceModelId;
	}

	public String getDeviceModel() {
		return deviceModel;
	}

	public void setDeviceModel(String deviceModel) {
		this.deviceModel = deviceModel;
	}

	public String getMacAddr() {
		return macAddr;
	}

	public void setMacAddr(String macAddr) {
		this.macAddr = macAddr;
	}

	public String getSimCardNo() {
		return simCardNo;
	}

	public void setSimCardNo(String simCardNo) {
		this.simCardNo = simCardNo;
	}

	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
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

	public Date getBindingTime() {
		return bindingTime;
	}

	public void setBindingTime(Date bindingTime) {
		this.bindingTime = bindingTime;
	}

	public Date getBindingTimeStart() {
		return bindingTimeStart;
	}

	public void setBindingTimeStart(Date bindingTimeStart) {
		this.bindingTimeStart = bindingTimeStart;
	}

	public Date getBindingTimeEnd() {
		return bindingTimeEnd;
	}

	public void setBindingTimeEnd(Date bindingTimeEnd) {
		this.bindingTimeEnd = bindingTimeEnd;
	}

	public Integer getDeviceStatus() {
		return deviceStatus;
	}

	public void setDeviceStatus(Integer deviceStatus) {
		this.deviceStatus = deviceStatus;
	}

	public void setSignalStrength(Integer signalStrength) {
		this.signalStrength = signalStrength;
	}

	public Integer getSignalStrength() {
		return signalStrength;
	}

	public Integer getSignalStrengthLevel() {
		return signalStrengthLevel;
	}

	public void setSignalStrengthLevel(Integer signalStrengthLevel) {
		this.signalStrengthLevel = signalStrengthLevel;
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

	public String getVersionNumber() {
		return versionNumber;
	}

	public void setVersionNumber(String versionNumber) {
		this.versionNumber = versionNumber;
	}

	public String getVersionType() {
		return versionType;
	}

	public void setVersionType(String versionType) {
		this.versionType = versionType;
	}

	public Integer getIsAvailable() {
		return isAvailable;
	}

	public void setIsAvailable(Integer isAvailable) {
		this.isAvailable = isAvailable;
	}

	public String getAvailableMemo() {
		return availableMemo;
	}

	public void setAvailableMemo(String availableMemo) {
		this.availableMemo = availableMemo;
	}

	public Date getAvailableUpdateTime() {
		return availableUpdateTime;
	}

	public void setAvailableUpdateTime(Date availableUpdateTime) {
		this.availableUpdateTime = availableUpdateTime;
	}

	public Date getAvailableUpdateTimeStart() {
		return availableUpdateTimeStart;
	}

	public void setAvailableUpdateTimeStart(Date availableUpdateTimeStart) {
		this.availableUpdateTimeStart = availableUpdateTimeStart;
	}

	public Date getAvailableUpdateTimeEnd() {
		return availableUpdateTimeEnd;
	}

	public void setAvailableUpdateTimeEnd(Date availableUpdateTimeEnd) {
		this.availableUpdateTimeEnd = availableUpdateTimeEnd;
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

	public Integer getOperatorType() {
		return operatorType;
	}

	public void setOperatorType(Integer operatorType) {
		this.operatorType = operatorType;
	}

	public String getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(String operatorId) {
		this.operatorId = operatorId;
	}

	/* Auto generated methods end */

	/* Customized methods start */

	/* Customized methods end */

	public Integer getRoleAdminTag() {
		return roleAdminTag;
	}

	public void setRoleAdminTag(Integer roleAdminTag) {
		this.roleAdminTag = roleAdminTag;
	}

	public Integer getIsOnline() {
		return isOnline;
	}

	public void setIsOnline(Integer isOnline) {
		this.isOnline = isOnline;
	}

	public String getWxDeviceId() {
		return wxDeviceId;
	}

	public void setWxDeviceId(String wxDeviceId) {
		this.wxDeviceId = wxDeviceId;
	}

	public String getBluetoothName() {
		return bluetoothName;
	}

	public void setBluetoothName(String bluetoothName) {
		this.bluetoothName = bluetoothName;
	}
	public String getIsRegist() {
		return isRegist;
	}

	public void setIsRegist(String isRegist) {
		this.isRegist = isRegist;
	}

	public String getIccid() {
		return iccid;
	}

	public void setIccid(String iccid) {
		this.iccid = iccid;
	}

	public String getRegistTime() {
		return registTime;
	}

	public void setRegistTime(String registTime) {
		this.registTime = registTime;
	}

	public String getVin() {
		return vin;
	}

	public void setVin(String vin) {
		this.vin = vin;
	}

	public String getRealTimeData() {
		return realTimeData;
	}

	public void setRealTimeData(String realTimeData) {
		this.realTimeData = realTimeData;
	}

	@Override
	public String toString() {
		return "Device [" + "terminalDeviceNo = " + terminalDeviceNo + ", deviceSn = " + deviceSn + ", brandId = "
				+ brandId + ", brandName = " + brandName + ", deviceModelId = " + deviceModelId + ", deviceModel = "
				+ deviceModel + ", macAddr = " + macAddr + ", bluetoothName = " + bluetoothName + ", simCardNo = " + simCardNo + ", cityId = " + cityId
				+ ", cityName = " + cityName + ", carNo = " + carNo + ", carPlateNo = " + carPlateNo
				+ ", bindingTime = " + bindingTime + ", bindingTimeStart = " + bindingTimeStart + ", bindingTimeEnd = "
				+ bindingTimeEnd + ", deviceStatus = " + deviceStatus + ", signalStrength = " + signalStrength
				+ ", signalStrengthLevel = " + signalStrengthLevel + ", lastReportingTime = " + lastReportingTime
				+ ", lastReportingTimeStart = " + lastReportingTimeStart + ", lastReportingTimeEnd = "
				+ lastReportingTimeEnd + ", versionNumber = " + versionNumber + ", versionType = " + versionType
				+ ", isAvailable = " + isAvailable + ", availableMemo = " + availableMemo + ", availableUpdateTime = "
				+ availableUpdateTime + ", availableUpdateTimeStart = " + availableUpdateTimeStart
				+ ", availableUpdateTimeEnd = " + availableUpdateTimeEnd + ", createTime = " + createTime
				+ ", createTimeStart = " + createTimeStart + ", createTimeEnd = " + createTimeEnd + ", updateTime = "
				+ updateTime + ", updateTimeStart = " + updateTimeStart + ", updateTimeEnd = " + updateTimeEnd
				+ ", operatorType = " + operatorType + ", operatorId = " + operatorId + ", wxDeviceId = " + wxDeviceId
				+ "]";
	}

	public String getDeviceParam() {
		return deviceParam;
	}

	public void setDeviceParam(String deviceParam) {
		this.deviceParam = deviceParam;
	}

}
