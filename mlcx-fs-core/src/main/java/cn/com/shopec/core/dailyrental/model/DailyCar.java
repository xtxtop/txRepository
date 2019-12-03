package cn.com.shopec.core.dailyrental.model;

import java.util.Date;

import cn.com.shopec.core.common.Entity;

/** 
 * DailyCar 数据实体类
 */
public class DailyCar extends Entity<String> {
	
	private static final long serialVersionUID = 1l;
	
	/*Auto generated properties start*/
	
	//车辆编号
	private String carNo;
	//车牌号
	private String carPlateNo;
	//商家id
	private String merchantId;
	//城市id
	private String cityId;
	//城市名称
	private String cityName;
	//所在场站的编号
	private String parkNo;
	//品牌id
	private String carBrandId;
	//品牌名称
	private String carBrandName;
	//车型id
	private String carModelId;
	//车型名称
	private String carModelName;
	//车辆识别号
	private String carIdNo;
	//发动机号
	private String engineNo;
	//颜色
	private String carColor;
	//检验有效期
	private Date validityDate;
	//检验有效期 时间范围起（查询用）
	private Date validityDateStart;
	//检验有效期 时间范围止（查询用）
	private Date validityDateEnd;	
	//车辆档次
	private Double carLevel;
	//座位数
	private String seating;
	//终端编号
	private String deviceId;
	//剩余电量百分比(0%~100%)
	private Double power;
	//总里程
	private Double mileage;
	//坐标经度
	private Double longitude;
	//坐标纬度
	private Double latitude;
	//最后上报时间
	private Date lastReportingTime;
	//最后上报时间 时间范围起（查询用）
	private Date lastReportingTimeStart;
	//最后上报时间 时间范围止（查询用）
	private Date lastReportingTimeEnd;	
	//保险公司
	private String insuranceCompany;
	//投保日期
	private Date enrollmentDate;
	//投保日期 时间范围起（查询用）
	private Date enrollmentDateStart;
	//投保日期 时间范围止（查询用）
	private Date enrollmentDateEnd;	
	//有效保险期
	private Date effectiveInsurancePeriod;
	//有效保险期 时间范围起（查询用）
	private Date effectiveInsurancePeriodStart;
	//有效保险期 时间范围止（查询用）
	private Date effectiveInsurancePeriodEnd;	
	//车辆照片url1
	private String carPhotoUrl1;
	//车辆照片url2
	private String carPhotoUrl2;
	//车辆照片url3
	private String carPhotoUrl3;
	//车辆照片url4
	private String carPhotoUrl4;
	//车辆证件照片url1
	private String carDocPhotoUrl1;
	//车辆证件照片url2
	private String carDocPhotoUrl2;
	//使用状态（0，空闲、1，已预占、2，订单中、3，调度中、4，故障维修）
	private Integer userageStatus;
	//上线状态（0、下线，1、上线，默认0）
	private Integer onlineStatus;
	//上下线理由
	private String onOffLineReason;
	//上线状态更新时间
	private Date onlineStatusUpdateTime;
	//上线状态更新时间 时间范围起（查询用）
	private Date onlineStatusUpdateTimeStart;
	//上线状态更新时间 时间范围止（查询用）
	private Date onlineStatusUpdateTimeEnd;	
	//创建时间
	private Date createTime;
	//创建时间 时间范围起（查询用）
	private Date createTimeStart;
	//创建时间 时间范围止（查询用）
	private Date createTimeEnd;	
	//更新时间
	private Date updateTime;
	//更新时间 时间范围起（查询用）
	private Date updateTimeStart;
	//更新时间 时间范围止（查询用）
	private Date updateTimeEnd;	
	//操作人id
	private String operatorId;
	//操作人类型（0、系统自动操作，1、平台人员操作，2、商家人员操作，3、会员操作）
	private Integer operatorType;
	
	/*Auto generated properties end*/
	
	
	
	/*Customized properties start*/
	
	/*Customized properties end*/
	
	
	
	/*Auto generated methods start*/
	
	@Override
	public String getPK(){
		return carNo;
	}
	
	public String getCarNo(){
		return carNo;
	}
	
	public void setCarNo(String carNo){
		this.carNo = carNo;
	}
	
	public String getCarPlateNo(){
		return carPlateNo;
	}
	
	public void setCarPlateNo(String carPlateNo){
		this.carPlateNo = carPlateNo;
	}
	
	public String getMerchantId(){
		return merchantId;
	}
	
	public void setMerchantId(String merchantId){
		this.merchantId = merchantId;
	}
	
	public String getCityId(){
		return cityId;
	}
	
	public void setCityId(String cityId){
		this.cityId = cityId;
	}
	
	public String getCityName(){
		return cityName;
	}
	
	public void setCityName(String cityName){
		this.cityName = cityName;
	}
	
	public String getParkNo(){
		return parkNo;
	}
	
	public void setParkNo(String parkNo){
		this.parkNo = parkNo;
	}
	
	public String getCarBrandId(){
		return carBrandId;
	}
	
	public void setCarBrandId(String carBrandId){
		this.carBrandId = carBrandId;
	}
	
	public String getCarBrandName(){
		return carBrandName;
	}
	
	public void setCarBrandName(String carBrandName){
		this.carBrandName = carBrandName;
	}
	
	public String getCarModelId(){
		return carModelId;
	}
	
	public void setCarModelId(String carModelId){
		this.carModelId = carModelId;
	}
	
	public String getCarModelName(){
		return carModelName;
	}
	
	public void setCarModelName(String carModelName){
		this.carModelName = carModelName;
	}
	
	public String getCarIdNo(){
		return carIdNo;
	}
	
	public void setCarIdNo(String carIdNo){
		this.carIdNo = carIdNo;
	}
	
	public String getEngineNo(){
		return engineNo;
	}
	
	public void setEngineNo(String engineNo){
		this.engineNo = engineNo;
	}
	
	public String getCarColor(){
		return carColor;
	}
	
	public void setCarColor(String carColor){
		this.carColor = carColor;
	}
	
	public Date getValidityDate(){
		return validityDate;
	}
	
	public void setValidityDate(Date validityDate){
		this.validityDate = validityDate;
	}
	
	public Date getValidityDateStart(){
		return validityDateStart;
	}
	
	public void setValidityDateStart(Date validityDateStart){
		this.validityDateStart = validityDateStart;
	}
	
	public Date getValidityDateEnd(){
		return validityDateEnd;
	}
	
	public void setValidityDateEnd(Date validityDateEnd){
		this.validityDateEnd = validityDateEnd;
	}	
	
	public Double getCarLevel(){
		return carLevel;
	}
	
	public void setCarLevel(Double carLevel){
		this.carLevel = carLevel;
	}
	
	public String getSeating(){
		return seating;
	}
	
	public void setSeating(String seating){
		this.seating = seating;
	}
	
	public String getDeviceId(){
		return deviceId;
	}
	
	public void setDeviceId(String deviceId){
		this.deviceId = deviceId;
	}
	
	public Double getPower(){
		return power;
	}
	
	public void setPower(Double power){
		this.power = power;
	}
	
	public Double getMileage(){
		return mileage;
	}
	
	public void setMileage(Double mileage){
		this.mileage = mileage;
	}
	
	public Double getLongitude(){
		return longitude;
	}
	
	public void setLongitude(Double longitude){
		this.longitude = longitude;
	}
	
	public Double getLatitude(){
		return latitude;
	}
	
	public void setLatitude(Double latitude){
		this.latitude = latitude;
	}
	
	public Date getLastReportingTime(){
		return lastReportingTime;
	}
	
	public void setLastReportingTime(Date lastReportingTime){
		this.lastReportingTime = lastReportingTime;
	}
	
	public Date getLastReportingTimeStart(){
		return lastReportingTimeStart;
	}
	
	public void setLastReportingTimeStart(Date lastReportingTimeStart){
		this.lastReportingTimeStart = lastReportingTimeStart;
	}
	
	public Date getLastReportingTimeEnd(){
		return lastReportingTimeEnd;
	}
	
	public void setLastReportingTimeEnd(Date lastReportingTimeEnd){
		this.lastReportingTimeEnd = lastReportingTimeEnd;
	}	
	
	public String getInsuranceCompany(){
		return insuranceCompany;
	}
	
	public void setInsuranceCompany(String insuranceCompany){
		this.insuranceCompany = insuranceCompany;
	}
	
	public Date getEnrollmentDate(){
		return enrollmentDate;
	}
	
	public void setEnrollmentDate(Date enrollmentDate){
		this.enrollmentDate = enrollmentDate;
	}
	
	public Date getEnrollmentDateStart(){
		return enrollmentDateStart;
	}
	
	public void setEnrollmentDateStart(Date enrollmentDateStart){
		this.enrollmentDateStart = enrollmentDateStart;
	}
	
	public Date getEnrollmentDateEnd(){
		return enrollmentDateEnd;
	}
	
	public void setEnrollmentDateEnd(Date enrollmentDateEnd){
		this.enrollmentDateEnd = enrollmentDateEnd;
	}	
	
	public Date getEffectiveInsurancePeriod(){
		return effectiveInsurancePeriod;
	}
	
	public void setEffectiveInsurancePeriod(Date effectiveInsurancePeriod){
		this.effectiveInsurancePeriod = effectiveInsurancePeriod;
	}
	
	public Date getEffectiveInsurancePeriodStart(){
		return effectiveInsurancePeriodStart;
	}
	
	public void setEffectiveInsurancePeriodStart(Date effectiveInsurancePeriodStart){
		this.effectiveInsurancePeriodStart = effectiveInsurancePeriodStart;
	}
	
	public Date getEffectiveInsurancePeriodEnd(){
		return effectiveInsurancePeriodEnd;
	}
	
	public void setEffectiveInsurancePeriodEnd(Date effectiveInsurancePeriodEnd){
		this.effectiveInsurancePeriodEnd = effectiveInsurancePeriodEnd;
	}	
	
	public String getCarPhotoUrl1(){
		return carPhotoUrl1;
	}
	
	public void setCarPhotoUrl1(String carPhotoUrl1){
		this.carPhotoUrl1 = carPhotoUrl1;
	}
	
	public String getCarPhotoUrl2(){
		return carPhotoUrl2;
	}
	
	public void setCarPhotoUrl2(String carPhotoUrl2){
		this.carPhotoUrl2 = carPhotoUrl2;
	}
	
	public String getCarPhotoUrl3(){
		return carPhotoUrl3;
	}
	
	public void setCarPhotoUrl3(String carPhotoUrl3){
		this.carPhotoUrl3 = carPhotoUrl3;
	}
	
	public String getCarPhotoUrl4(){
		return carPhotoUrl4;
	}
	
	public void setCarPhotoUrl4(String carPhotoUrl4){
		this.carPhotoUrl4 = carPhotoUrl4;
	}
	
	public String getCarDocPhotoUrl1(){
		return carDocPhotoUrl1;
	}
	
	public void setCarDocPhotoUrl1(String carDocPhotoUrl1){
		this.carDocPhotoUrl1 = carDocPhotoUrl1;
	}
	
	public String getCarDocPhotoUrl2(){
		return carDocPhotoUrl2;
	}
	
	public void setCarDocPhotoUrl2(String carDocPhotoUrl2){
		this.carDocPhotoUrl2 = carDocPhotoUrl2;
	}
	
	public Integer getUserageStatus(){
		return userageStatus;
	}
	
	public void setUserageStatus(Integer userageStatus){
		this.userageStatus = userageStatus;
	}
	
	public Integer getOnlineStatus(){
		return onlineStatus;
	}
	
	public void setOnlineStatus(Integer onlineStatus){
		this.onlineStatus = onlineStatus;
	}
	
	public String getOnOffLineReason(){
		return onOffLineReason;
	}
	
	public void setOnOffLineReason(String onOffLineReason){
		this.onOffLineReason = onOffLineReason;
	}
	
	public Date getOnlineStatusUpdateTime(){
		return onlineStatusUpdateTime;
	}
	
	public void setOnlineStatusUpdateTime(Date onlineStatusUpdateTime){
		this.onlineStatusUpdateTime = onlineStatusUpdateTime;
	}
	
	public Date getOnlineStatusUpdateTimeStart(){
		return onlineStatusUpdateTimeStart;
	}
	
	public void setOnlineStatusUpdateTimeStart(Date onlineStatusUpdateTimeStart){
		this.onlineStatusUpdateTimeStart = onlineStatusUpdateTimeStart;
	}
	
	public Date getOnlineStatusUpdateTimeEnd(){
		return onlineStatusUpdateTimeEnd;
	}
	
	public void setOnlineStatusUpdateTimeEnd(Date onlineStatusUpdateTimeEnd){
		this.onlineStatusUpdateTimeEnd = onlineStatusUpdateTimeEnd;
	}	
	
	public Date getCreateTime(){
		return createTime;
	}
	
	public void setCreateTime(Date createTime){
		this.createTime = createTime;
	}
	
	public Date getCreateTimeStart(){
		return createTimeStart;
	}
	
	public void setCreateTimeStart(Date createTimeStart){
		this.createTimeStart = createTimeStart;
	}
	
	public Date getCreateTimeEnd(){
		return createTimeEnd;
	}
	
	public void setCreateTimeEnd(Date createTimeEnd){
		this.createTimeEnd = createTimeEnd;
	}	
	
	public Date getUpdateTime(){
		return updateTime;
	}
	
	public void setUpdateTime(Date updateTime){
		this.updateTime = updateTime;
	}
	
	public Date getUpdateTimeStart(){
		return updateTimeStart;
	}
	
	public void setUpdateTimeStart(Date updateTimeStart){
		this.updateTimeStart = updateTimeStart;
	}
	
	public Date getUpdateTimeEnd(){
		return updateTimeEnd;
	}
	
	public void setUpdateTimeEnd(Date updateTimeEnd){
		this.updateTimeEnd = updateTimeEnd;
	}	
	
	public String getOperatorId(){
		return operatorId;
	}
	
	public void setOperatorId(String operatorId){
		this.operatorId = operatorId;
	}
	
	public Integer getOperatorType(){
		return operatorType;
	}
	
	public void setOperatorType(Integer operatorType){
		this.operatorType = operatorType;
	}
	
	
	/*Auto generated methods end*/
	
	
	
	/*Customized methods start*/
	
	/*Customized methods end*/
	
	
	@Override
	public String toString() {
		return "DailyCar ["
		 + "carNo = " + carNo + ", carPlateNo = " + carPlateNo + ", merchantId = " + merchantId + ", cityId = " + cityId
		 + ", cityName = " + cityName + ", parkNo = " + parkNo + ", carBrandId = " + carBrandId + ", carBrandName = " + carBrandName
		 + ", carModelId = " + carModelId + ", carModelName = " + carModelName + ", carIdNo = " + carIdNo + ", engineNo = " + engineNo
		 + ", carColor = " + carColor + ", validityDate = " + validityDate + ", validityDateStart = " + validityDateStart + ", validityDateEnd = " + validityDateEnd + ", carLevel = " + carLevel + ", seating = " + seating
		 + ", deviceId = " + deviceId + ", power = " + power + ", mileage = " + mileage + ", longitude = " + longitude
		 + ", latitude = " + latitude + ", lastReportingTime = " + lastReportingTime + ", lastReportingTimeStart = " + lastReportingTimeStart + ", lastReportingTimeEnd = " + lastReportingTimeEnd + ", insuranceCompany = " + insuranceCompany + ", enrollmentDate = " + enrollmentDate + ", enrollmentDateStart = " + enrollmentDateStart + ", enrollmentDateEnd = " + enrollmentDateEnd
		 + ", effectiveInsurancePeriod = " + effectiveInsurancePeriod + ", effectiveInsurancePeriodStart = " + effectiveInsurancePeriodStart + ", effectiveInsurancePeriodEnd = " + effectiveInsurancePeriodEnd + ", carPhotoUrl1 = " + carPhotoUrl1 + ", carPhotoUrl2 = " + carPhotoUrl2 + ", carPhotoUrl3 = " + carPhotoUrl3
		 + ", carPhotoUrl4 = " + carPhotoUrl4 + ", carDocPhotoUrl1 = " + carDocPhotoUrl1 + ", carDocPhotoUrl2 = " + carDocPhotoUrl2 + ", userageStatus = " + userageStatus
		 + ", onlineStatus = " + onlineStatus + ", onOffLineReason = " + onOffLineReason + ", onlineStatusUpdateTime = " + onlineStatusUpdateTime + ", onlineStatusUpdateTimeStart = " + onlineStatusUpdateTimeStart + ", onlineStatusUpdateTimeEnd = " + onlineStatusUpdateTimeEnd + ", createTime = " + createTime + ", createTimeStart = " + createTimeStart + ", createTimeEnd = " + createTimeEnd
		 + ", updateTime = " + updateTime + ", updateTimeStart = " + updateTimeStart + ", updateTimeEnd = " + updateTimeEnd + ", operatorId = " + operatorId + ", operatorType = " + operatorType
		+"]";
	}
}
