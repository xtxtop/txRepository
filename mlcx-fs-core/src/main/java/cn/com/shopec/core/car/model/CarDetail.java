package cn.com.shopec.core.car.model;

import cn.com.shopec.core.common.Entity;
import cn.com.shopec.core.marketing.model.CarRedPacketVo;

/** 
 * Car 数据实体类
 */
public class CarDetail extends Entity<String> {
	
	private static final long serialVersionUID = 1l;
	
	/*Auto generated properties start*/
	//车辆编号
	private String carNo;
	//车牌号
	private String carPlateNo;
	//品牌id（具体来自数据字典表）
	private String carBrandId;
	//品牌名称
	private String carBrandName;
	//车型id（具体来自数据字典表）
	private String carModelId;
	//车型名称
	private String carModelName;
	//车辆照片url1
	private String carPhotoUrl1;
	//剩余电量百分比(0%~100%)
	private Double power;
	//总里程
	private Double mileage;
	//可续航里程（km）
	private Double rangeMileage;
	//是否默认勾选免赔
    private Integer forceRegardless; 
	//座位数
	private String seaTing;  
	//按时间计费(元/分钟)
	private Double ofMinute;
	//按里程计费(元/公里)
	private Double ofKm;
	//计费封顶（天）
	private Double billingCapPerDay;
	//设置所有车辆能否在场站外还车
	private Integer returnCarStatus;
	//起步价
	private Double  baseFee; 
	//取车附加费
	private Double serviceFeeGet; 
	//坐标经度
	private Double longitude;
	//坐标纬度
	private Double latitude;
	//免费保留时间
	private  Integer dingshi;
	//车是否在  场站内（1 是  0 否）
	private  Integer  isPark;
	//平台是否支持不计免赔
	private Integer isRegardless;
	//不计免赔
	private  Double  regardlessFranchise;
	//保险提示语
	private String  insuranceTips;
	//车辆类型(1 电量 2 燃油)
	private  Integer vehicleType;
	//是否红包车(1 是)
	private String isCarRedPakcet;
	private CarRedPacketVo carRedPacketVo;
	
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
	public String getCarPhotoUrl1() {
		return carPhotoUrl1;
	}
	public void setCarPhotoUrl1(String carPhotoUrl1) {
		this.carPhotoUrl1 = carPhotoUrl1;
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
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	public Double getRangeMileage() {
		return rangeMileage;
	}
	public void setRangeMileage(Double rangeMileage) {
		this.rangeMileage = rangeMileage;
	}
	
	public String getSeaTing() {
		return seaTing;
	}
	public void setSeaTing(String seaTing) {
		this.seaTing = seaTing;
	}
	
	public Double getBillingCapPerDay() {
		return billingCapPerDay;
	}
	public void setBillingCapPerDay(Double billingCapPerDay) {
		this.billingCapPerDay = billingCapPerDay;
	}
	
	public Integer getReturnCarStatus() {
		return returnCarStatus;
	}
	public void setReturnCarStatus(Integer returnCarStatus) {
		this.returnCarStatus = returnCarStatus;
	}
	public Double getBaseFee() {
		return baseFee;
	}
	public void setBaseFee(Double baseFee) {
		this.baseFee = baseFee;
	}
	public Double getServiceFeeGet() {
		return serviceFeeGet;
	}
	public void setServiceFeeGet(Double serviceFeeGet) {
		this.serviceFeeGet = serviceFeeGet;
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
	public Integer getDingshi() {
		return dingshi;
	}
	public void setDingshi(Integer dingshi) {
		this.dingshi = dingshi;
	}
	public Double getOfMinute() {
		return ofMinute;
	}
	public void setOfMinute(Double ofMinute) {
		this.ofMinute = ofMinute;
	}
	public Double getOfKm() {
		return ofKm;
	}
	public void setOfKm(Double ofKm) {
		this.ofKm = ofKm;
	}
	@Override
	public String toString() {
		return "CarDetail [carNo=" + carNo + ", carPlateNo=" + carPlateNo + ", carBrandId=" + carBrandId
				+ ", carBrandName=" + carBrandName + ", carModelId=" + carModelId + ", carModelName=" + carModelName
				+ ", carPhotoUrl1=" + carPhotoUrl1 + ", power=" + power + ", mileage=" + mileage + ", rangeMileage="
				+ rangeMileage + ", seaTing=" + seaTing + ", ofMinute=" + ofMinute + ", ofKm=" + ofKm
				+ ", billingCapPerDay=" + billingCapPerDay + ", returnCarStatus=" + returnCarStatus + ", baseFee="
				+ baseFee + ", serviceFeeGet=" + serviceFeeGet + ", longitude=" + longitude + ", latitude=" + latitude
				+ ", dingshi=" + dingshi + "]";
	}
	public Integer getIsPark() {
		return isPark;
	}
	public void setIsPark(Integer isPark) {
		this.isPark = isPark;
	}
	public Integer getIsRegardless() {
		return isRegardless;
	}
	public void setIsRegardless(Integer isRegardless) {
		this.isRegardless = isRegardless;
	}
	public Double getRegardlessFranchise() {
		return regardlessFranchise;
	}
	public void setRegardlessFranchise(Double regardlessFranchise) {
		this.regardlessFranchise = regardlessFranchise;
	}
	public String getInsuranceTips() {
		return insuranceTips;
	}
	public void setInsuranceTips(String insuranceTips) {
		this.insuranceTips = insuranceTips;
	}
	public Integer getForceRegardless() {
		return forceRegardless;
	}
	public void setForceRegardless(Integer forceRegardless) {
		this.forceRegardless = forceRegardless;
	}
	public Integer getVehicleType() {
		return vehicleType;
	}
	public void setVehicleType(Integer vehicleType) {
		this.vehicleType = vehicleType;
	}
	public String getIsCarRedPakcet() {
		return isCarRedPakcet;
	}
	public void setIsCarRedPakcet(String isCarRedPakcet) {
		this.isCarRedPakcet = isCarRedPakcet;
	}
	public CarRedPacketVo getCarRedPacketVo() {
		return carRedPacketVo;
	}
	public void setCarRedPacketVo(CarRedPacketVo carRedPacketVo) {
		this.carRedPacketVo = carRedPacketVo;
	}
	
	
	
	
}
	
	