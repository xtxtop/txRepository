package cn.com.shopec.core.car.model;

import java.util.Date;
import java.util.List;

import cn.com.shopec.core.common.Entity;
import cn.com.shopec.core.marketing.model.PricingRule;

/**
 * Car 数据实体类
 */
public class Car extends Entity<String> {

	private static final long serialVersionUID = 1l;

	/* Auto generated properties start */

	// 车辆编号
	private String carNo;
	// 车牌号
	private String carPlateNo;
	// 车主id
	private String carOwnerId;
	// 车主名称
	private String carOwnerName;
	// 品牌id（具体来自数据字典表）
	private String carBrandId;
	// 品牌名称
	private String carBrandName;
	// 车型id（具体来自数据字典表）
	private String carModelId;
	// 车型名称
	private String carModelName;
	// 城市id（具体来自数据字典表）
	private String cityId;
	// 城市名称
	private String cityName;
	// 车辆识别号
	private String carIdNo;
	// 发动机号
	private String engineNo;
	// 颜色
	private String carColor;
	// 租赁类型（1、分时，2、长租）
	private Integer leaseType;
	//车辆类型(1 电量 2 燃油)
	private  Integer vehicleType;
	// 检验有效期
	private Date validityDate;
	// 检验有效期 时间范围起（查询用）
	private Date validityDateStart;
	// 检验有效期 时间范围止（查询用）
	private Date validityDateEnd;
	// 是否专用车辆（只能供某些集团的客户使用，0、非专用，1、专用，默认0）
	private Integer isDedicated;
	// 专用车辆集团公司id
	private String dedicatedCompanyId;
	// 上线状态（0、下线，1、上线，默认0）
	private Integer onlineStatus;
	// 上线状态更新时间
	private Date onlineStatusUpdateTime;
	// 上线状态更新时间 时间范围起（查询用）
	private Date onlineStatusUpdateTimeStart;
	// 上线状态更新时间 时间范围止（查询用）
	private Date onlineStatusUpdateTimeEnd;
	// 使用状态（0，空闲、1，已预占、2，订单中、3，调度中）
	private Integer userageStatus;
	// 使用状态更新时间
	private Date usedStatusUpdateTime;
	// 使用状态更新时间 时间范围起（查询用）
	private Date usedStatusUpdateTimeStart;
	// 使用状态更新时间 时间范围止（查询用）
	private Date usedStatusUpdateTimeEnd;
	// 车辆档次
	private Double carLevel;
	// 终端编号
	private String deviceId;
	// 保险公司
	private String insuranceCompany;
	// 投保日期
	private Date enrollmentDate;
	// 投保日期 时间范围起（查询用）
	private Date enrollmentDateStart;
	// 投保日期 时间范围止（查询用）
	private Date enrollmentDateEnd;
	// 有效保险期
	private Date effectiveInsurancePeriod;
	// 有效保险期 时间范围起（查询用）
	private Date effectiveInsurancePeriodStart;
	// 有效保险期 时间范围止（查询用）
	private Date effectiveInsurancePeriodEnd;
	// 车辆照片url1
	private String carPhotoUrl1;
	// 车辆照片url2
	private String carPhotoUrl2;
	// 车辆照片url3
	private String carPhotoUrl3;
	// 车辆照片url4
	private String carPhotoUrl4;
	// 车辆证件照片url1
	private String carDocPhotoUrl1;
	// 车辆证件照片url2
	private String carDocPhotoUrl2;
	// 车辆证件照片url3
	private String carDocPhotoUrl3;
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
	// 操作人类型（0、系统自动操作，1、平台人员操作，2、商家人员操作，3、会员操作）
	private Integer operatorType;
	// 上下线原因（备注）
	private String onOffLineReason;
	// 终端状态
	private Integer deviceStatus;
	// 剩余电量百分比(0%~100%)
	private Double power;
	// 总里程
	private Double mileage;
	// 所在场站的编号
	private String locationParkNo;
	// 终端序列号
	private String deviceSn;
	// 车辆上下线理由url1
	private String carOnLineOrOffPicUrl1;
	// 车辆上下线理由url2
	private String carOnLineOrOffPicUrl2;
	// 车辆上下线理由url3
	private String carOnLineOrOffPicUrl3;
	/* Auto generated properties end */
	// 操作人
	private String userName;
	// 上/下 线操作人ID
	private String workerNo;
	// 上下操做人 类型（1 后台 2 调度员）
	private Integer carTestType;
	// 上下线操作人 名称
	private String workerName;
	// 计费规则
	private List<PricingRule> pricingRules;
	// 座位数
	private String seaTing;
	// 可续航里程（km）
		private Double rangeMileage;
	// 车辆是否在线（做页面列表显示字段用; 1.在线 2.不在线 ）
	private Integer isOnline;
	// 车辆类型：1、经济型，2、商务型，3、豪华型，4、6至15座商务，5、SUV。
	private Integer carType;
	//信号强度等级值(1、非常差 ，2、差，3、一般，4、好  5、非常好)(>=0 到 < 7  非常差 >=7 到 < 13 差 >=13 到 < 19 一般 >=19 到 < 25 好 >=25 到 <= 31  非常好)
	private Integer signalStrengthLevel;
	// 车辆是否闲置（1.闲置 0.不闲置 ）
	private Integer isIdle;

	// 最后上报时间
	private Date lastReportingTime;

	/* Customized properties start */

	/* Customized properties end */

	/* Auto generated methods start */

	// 上下线理由（下拉查询用）
	private String updownWhy;
	//关联加盟商编号FRANCHISEENO
	private String franchiseeNo;
	//二维码
	private String qrcodePicUrl;
	public String getFranchiseeNo() {
		return franchiseeNo;
	}

	public void setFranchiseeNo(String franchiseeNo) {
		this.franchiseeNo = franchiseeNo;
	}

	public String getQrcodePicUrl() {
		return qrcodePicUrl;
	}

	public void setQrcodePicUrl(String qrcodePicUrl) {
		this.qrcodePicUrl = qrcodePicUrl;
	}

	public String getUpdownWhy() {
		return updownWhy;
	}

	public void setUpdownWhy(String updownWhy) {
		this.updownWhy = updownWhy;
	}

	@Override
	public String getPK() {
		return carNo;
	}

	public String getOnOffLineReason() {
		return onOffLineReason;
	}

	public String getLocationParkNo() {
		return locationParkNo;
	}

	public void setLocationParkNo(String locationParkNo) {
		this.locationParkNo = locationParkNo;
	}

	public void setOnOffLineReason(String onOffLineReason) {
		this.onOffLineReason = onOffLineReason;
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

	public Integer getDeviceStatus() {
		return deviceStatus;
	}

	public void setDeviceStatus(Integer deviceStatus) {
		this.deviceStatus = deviceStatus;
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

	public String getCarOwnerId() {
		return carOwnerId;
	}

	public void setCarOwnerId(String carOwnerId) {
		this.carOwnerId = carOwnerId;
	}

	public String getCarOwnerName() {
		return carOwnerName;
	}

	public void setCarOwnerName(String carOwnerName) {
		this.carOwnerName = carOwnerName;
	}

	public String getCarBrandId() {
		return carBrandId;
	}

	public void setCarBrandId(String carBrandId) {
		this.carBrandId = carBrandId;
	}

	public String getCarBrandName() {
		return carBrandName;
	}

	public void setCarBrandName(String carBrandName) {
		this.carBrandName = carBrandName;
	}

	public String getCarModelId() {
		return carModelId;
	}

	public void setCarModelId(String carModelId) {
		this.carModelId = carModelId;
	}

	public String getCarModelName() {
		return carModelName;
	}

	public void setCarModelName(String carModelName) {
		this.carModelName = carModelName;
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

	public String getCarIdNo() {
		return carIdNo;
	}

	public void setCarIdNo(String carIdNo) {
		this.carIdNo = carIdNo;
	}

	public String getEngineNo() {
		return engineNo;
	}

	public void setEngineNo(String engineNo) {
		this.engineNo = engineNo;
	}

	public String getCarColor() {
		return carColor;
	}

	public void setCarColor(String carColor) {
		this.carColor = carColor;
	}

	public Integer getLeaseType() {
		return leaseType;
	}

	public void setLeaseType(Integer leaseType) {
		this.leaseType = leaseType;
	}

	public Date getValidityDate() {
		return validityDate;
	}

	public void setValidityDate(Date validityDate) {
		this.validityDate = validityDate;
	}

	public Date getValidityDateStart() {
		return validityDateStart;
	}

	public void setValidityDateStart(Date validityDateStart) {
		this.validityDateStart = validityDateStart;
	}

	public Date getValidityDateEnd() {
		return validityDateEnd;
	}

	public void setValidityDateEnd(Date validityDateEnd) {
		this.validityDateEnd = validityDateEnd;
	}

	public Integer getIsDedicated() {
		return isDedicated;
	}

	public void setIsDedicated(Integer isDedicated) {
		this.isDedicated = isDedicated;
	}

	public String getDedicatedCompanyId() {
		return dedicatedCompanyId;
	}

	public void setDedicatedCompanyId(String dedicatedCompanyId) {
		this.dedicatedCompanyId = dedicatedCompanyId;
	}

	public Integer getOnlineStatus() {
		return onlineStatus;
	}

	public void setOnlineStatus(Integer onlineStatus) {
		this.onlineStatus = onlineStatus;
	}

	public Date getOnlineStatusUpdateTime() {
		return onlineStatusUpdateTime;
	}

	public void setOnlineStatusUpdateTime(Date onlineStatusUpdateTime) {
		this.onlineStatusUpdateTime = onlineStatusUpdateTime;
	}

	public Date getOnlineStatusUpdateTimeStart() {
		return onlineStatusUpdateTimeStart;
	}

	public void setOnlineStatusUpdateTimeStart(Date onlineStatusUpdateTimeStart) {
		this.onlineStatusUpdateTimeStart = onlineStatusUpdateTimeStart;
	}

	public Integer getUserageStatus() {
		return userageStatus;
	}

	public void setUserageStatus(Integer userageStatus) {
		this.userageStatus = userageStatus;
	}

	public Date getOnlineStatusUpdateTimeEnd() {
		return onlineStatusUpdateTimeEnd;
	}

	public void setOnlineStatusUpdateTimeEnd(Date onlineStatusUpdateTimeEnd) {
		this.onlineStatusUpdateTimeEnd = onlineStatusUpdateTimeEnd;
	}

	public Date getUsedStatusUpdateTime() {
		return usedStatusUpdateTime;
	}

	public void setUsedStatusUpdateTime(Date usedStatusUpdateTime) {
		this.usedStatusUpdateTime = usedStatusUpdateTime;
	}

	public Date getUsedStatusUpdateTimeStart() {
		return usedStatusUpdateTimeStart;
	}

	public void setUsedStatusUpdateTimeStart(Date usedStatusUpdateTimeStart) {
		this.usedStatusUpdateTimeStart = usedStatusUpdateTimeStart;
	}

	public Date getUsedStatusUpdateTimeEnd() {
		return usedStatusUpdateTimeEnd;
	}

	public void setUsedStatusUpdateTimeEnd(Date usedStatusUpdateTimeEnd) {
		this.usedStatusUpdateTimeEnd = usedStatusUpdateTimeEnd;
	}

	public Double getCarLevel() {
		return carLevel;
	}

	public void setCarLevel(Double carLevel) {
		this.carLevel = carLevel;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getInsuranceCompany() {
		return insuranceCompany;
	}

	public void setInsuranceCompany(String insuranceCompany) {
		this.insuranceCompany = insuranceCompany;
	}

	public Date getEnrollmentDate() {
		return enrollmentDate;
	}

	public void setEnrollmentDate(Date enrollmentDate) {
		this.enrollmentDate = enrollmentDate;
	}

	public Date getEnrollmentDateStart() {
		return enrollmentDateStart;
	}

	public void setEnrollmentDateStart(Date enrollmentDateStart) {
		this.enrollmentDateStart = enrollmentDateStart;
	}

	public Date getEnrollmentDateEnd() {
		return enrollmentDateEnd;
	}

	public void setEnrollmentDateEnd(Date enrollmentDateEnd) {
		this.enrollmentDateEnd = enrollmentDateEnd;
	}

	public Date getEffectiveInsurancePeriod() {
		return effectiveInsurancePeriod;
	}

	public void setEffectiveInsurancePeriod(Date effectiveInsurancePeriod) {
		this.effectiveInsurancePeriod = effectiveInsurancePeriod;
	}

	public Date getEffectiveInsurancePeriodStart() {
		return effectiveInsurancePeriodStart;
	}

	public void setEffectiveInsurancePeriodStart(Date effectiveInsurancePeriodStart) {
		this.effectiveInsurancePeriodStart = effectiveInsurancePeriodStart;
	}

	public Date getEffectiveInsurancePeriodEnd() {
		return effectiveInsurancePeriodEnd;
	}

	public void setEffectiveInsurancePeriodEnd(Date effectiveInsurancePeriodEnd) {
		this.effectiveInsurancePeriodEnd = effectiveInsurancePeriodEnd;
	}

	public String getCarPhotoUrl1() {
		return carPhotoUrl1;
	}

	public void setCarPhotoUrl1(String carPhotoUrl1) {
		this.carPhotoUrl1 = carPhotoUrl1;
	}

	public String getCarPhotoUrl2() {
		return carPhotoUrl2;
	}

	public void setCarPhotoUrl2(String carPhotoUrl2) {
		this.carPhotoUrl2 = carPhotoUrl2;
	}

	public String getCarPhotoUrl3() {
		return carPhotoUrl3;
	}

	public void setCarPhotoUrl3(String carPhotoUrl3) {
		this.carPhotoUrl3 = carPhotoUrl3;
	}

	public String getCarPhotoUrl4() {
		return carPhotoUrl4;
	}

	public void setCarPhotoUrl4(String carPhotoUrl4) {
		this.carPhotoUrl4 = carPhotoUrl4;
	}

	public String getCarDocPhotoUrl1() {
		return carDocPhotoUrl1;
	}

	public void setCarDocPhotoUrl1(String carDocPhotoUrl1) {
		this.carDocPhotoUrl1 = carDocPhotoUrl1;
	}

	public String getCarDocPhotoUrl2() {
		return carDocPhotoUrl2;
	}

	public void setCarDocPhotoUrl2(String carDocPhotoUrl2) {
		this.carDocPhotoUrl2 = carDocPhotoUrl2;
	}

	public String getCarDocPhotoUrl3() {
		return carDocPhotoUrl3;
	}

	public void setCarDocPhotoUrl3(String carDocPhotoUrl3) {
		this.carDocPhotoUrl3 = carDocPhotoUrl3;
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

	/* Auto generated methods end */

	/* Customized methods start */

	/* Customized methods end */

	public String getDeviceSn() {
		return deviceSn;
	}

	public void setDeviceSn(String deviceSn) {
		this.deviceSn = deviceSn;
	}

	public String getCarOnLineOrOffPicUrl1() {
		return carOnLineOrOffPicUrl1;
	}

	public void setCarOnLineOrOffPicUrl1(String carOnLineOrOffPicUrl1) {
		this.carOnLineOrOffPicUrl1 = carOnLineOrOffPicUrl1;
	}

	public String getCarOnLineOrOffPicUrl2() {
		return carOnLineOrOffPicUrl2;
	}

	public void setCarOnLineOrOffPicUrl2(String carOnLineOrOffPicUrl2) {
		this.carOnLineOrOffPicUrl2 = carOnLineOrOffPicUrl2;
	}

	public String getCarOnLineOrOffPicUrl3() {
		return carOnLineOrOffPicUrl3;
	}

	public void setCarOnLineOrOffPicUrl3(String carOnLineOrOffPicUrl3) {
		this.carOnLineOrOffPicUrl3 = carOnLineOrOffPicUrl3;
	}

	@Override
	public String toString() {
		return "Car [" + "carNo = " + carNo + ", carPlateNo = " + carPlateNo + ", carOwnerId = " + carOwnerId
				+ ", carOwnerName = " + carOwnerName + ", carBrandId = " + carBrandId + ", carBrandName = "
				+ carBrandName + ", carModelId = " + carModelId + ", carModelName = " + carModelName + ", cityId = "
				+ cityId + ", cityName = " + cityName + ", carIdNo = " + carIdNo + ", engineNo = " + engineNo
				+ ", carColor = " + carColor + ", leaseType = " + leaseType + ", validityDate = " + validityDate
				+ ", validityDateStart = " + validityDateStart + ", validityDateEnd = " + validityDateEnd
				+ ", isDedicated = " + isDedicated + ", dedicatedCompanyId = " + dedicatedCompanyId
				+ ", onlineStatus = " + onlineStatus + ", onlineStatusUpdateTime = " + onlineStatusUpdateTime
				+ ", onlineStatusUpdateTimeStart = " + onlineStatusUpdateTimeStart + ", onlineStatusUpdateTimeEnd = "
				+ onlineStatusUpdateTimeEnd + ", usedStatusUpdateTime = " + usedStatusUpdateTime
				+ ", usedStatusUpdateTimeStart = " + usedStatusUpdateTimeStart + ", usedStatusUpdateTimeEnd = "
				+ usedStatusUpdateTimeEnd + ", carLevel = " + carLevel + ", deviceId = " + deviceId
				+ ", insuranceCompany = " + insuranceCompany + ", enrollmentDate = " + enrollmentDate
				+ ", enrollmentDateStart = " + enrollmentDateStart + ", enrollmentDateEnd = " + enrollmentDateEnd
				+ ", effectiveInsurancePeriod = " + effectiveInsurancePeriod + ", effectiveInsurancePeriodStart = "
				+ effectiveInsurancePeriodStart + ", effectiveInsurancePeriodEnd = " + effectiveInsurancePeriodEnd
				+ ", carPhotoUrl1 = " + carPhotoUrl1 + ", carPhotoUrl2 = " + carPhotoUrl2 + ", carPhotoUrl3 = "
				+ carPhotoUrl3 + ", carPhotoUrl4 = " + carPhotoUrl4 + ", carDocPhotoUrl1 = " + carDocPhotoUrl1
				+ ", carDocPhotoUrl2 = " + carDocPhotoUrl2 + ", carDocPhotoUrl3 = " + carDocPhotoUrl3
				+ ", createTime = " + createTime + ", createTimeStart = " + createTimeStart + ", createTimeEnd = "
				+ createTimeEnd + ", updateTime = " + updateTime + ", updateTimeStart = " + updateTimeStart
				+ ", updateTimeEnd = " + updateTimeEnd + ", operatorId = " + operatorId + ", operatorType = "
				+ operatorType + ", updownWhy  = " + updownWhy + "]";
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Integer getCarTestType() {
		return carTestType;
	}

	public void setCarTestType(Integer carTestType) {
		this.carTestType = carTestType;
	}

	public String getWorkerNo() {
		return workerNo;
	}

	public void setWorkerNo(String workerNo) {
		this.workerNo = workerNo;
	}

	public String getWorkerName() {
		return workerName;
	}

	public void setWorkerName(String workerName) {
		this.workerName = workerName;
	}

	public List<PricingRule> getPricingRules() {
		return pricingRules;
	}

	public void setPricingRules(List<PricingRule> pricingRules) {
		this.pricingRules = pricingRules;
	}

	public String getSeaTing() {
		return seaTing;
	}

	public void setSeaTing(String seaTing) {
		this.seaTing = seaTing;
	}

	public Integer getIsOnline() {
		return isOnline;
	}

	public void setIsOnline(Integer isOnline) {
		this.isOnline = isOnline;
	}

	public Integer getCarType() {
		return carType;
	}

	public void setCarType(Integer carType) {
		this.carType = carType;
	}

	public Integer getIsIdle() {
		return isIdle;
	}

	public void setIsIdle(Integer isIdle) {
		this.isIdle = isIdle;
	}

	public Date getLastReportingTime() {
		return lastReportingTime;
	}

	public void setLastReportingTime(Date lastReportingTime) {
		this.lastReportingTime = lastReportingTime;
	}

	public Integer getVehicleType() {
		return vehicleType;
	}

	public void setVehicleType(Integer vehicleType) {
		this.vehicleType = vehicleType;
	}

	public Double getRangeMileage() {
		return rangeMileage;
	}

	public void setRangeMileage(Double rangeMileage) {
		this.rangeMileage = rangeMileage;
	}

	public Integer getSignalStrengthLevel() {
		return signalStrengthLevel;
	}

	public void setSignalStrengthLevel(Integer signalStrengthLevel) {
		this.signalStrengthLevel = signalStrengthLevel;
	}

}
