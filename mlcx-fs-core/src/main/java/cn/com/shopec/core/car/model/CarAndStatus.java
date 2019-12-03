package cn.com.shopec.core.car.model;

import cn.com.shopec.core.common.Entity;
import cn.com.shopec.core.marketing.model.CarRedPacketVo;

/**
 * Car 数据实体类
 */
public class CarAndStatus extends Entity<String> {

	private static final long serialVersionUID = 1l;

	/* Auto generated properties start */
	// 车辆编号
	private String carNo;
	// 车牌号
	private String carPlateNo;
	// 品牌id（具体来自数据字典表）
	private String carBrandId;
	// 品牌名称
	private String carBrandName;
	// 车型id（具体来自数据字典表）
	private String carModelId;
	// 车型名称
	private String carModelName;
	// 车辆照片url1
	private String carPhotoUrl1;
	// 剩余电量百分比(0%~100%)
	private Double power;
	// 总里程
	private Double mileage;
	// 预约的最大分钟数
	private int minute;
	// 预约时间
	private String prebookTime;
	// 可续航里程（km）
	private Double rangeMileage;
	// 座位数
	private String seaTing;
	// 人员服务按时计费(元/分钟)
	private Double servicePriceOfMinute;
	// 人员服务按里程计费(元/公里)
	private Double servicePriceOfKm;
	// 工作日按时间计费(元/分钟)
	private Double priceOfMinute;
	// 工作日按里程计费(元/公里)
	private Double priceOfKm;
	// 计费封顶（天）
	private Double billingCapPerDay;
	// 车辆地址
	private String address;
	//坐标经度
	private Double longitude;
	//坐标纬度
	private Double latitude;
	//按时间计费(元/分钟)
	private Double ofMinute;
	//按里程计费(元/公里)
	private Double ofKm;
	//车辆类型(1 电量 2 燃油)
	private  Integer vehicleType;
	//是否红包车辆(0、否 1、是)
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

	public int getMinute() {
		return minute;
	}

	public void setMinute(int minute) {
		this.minute = minute;
	}

	public String getPrebookTime() {
		return prebookTime;
	}

	public void setPrebookTime(String prebookTime) {
		this.prebookTime = prebookTime;
	}

	public Double getRangeMileage() {
		return rangeMileage;
	}

	public void setRangeMileage(Double rangeMileage) {
		this.rangeMileage = rangeMileage;
	}

	@Override
	public String toString() {
		return "CarAndStatus [carNo=" + carNo + ", carPlateNo=" + carPlateNo + ", carBrandId=" + carBrandId
				+ ", carBrandName=" + carBrandName + ", carModelId=" + carModelId + ", carModelName=" + carModelName
				+ ", carPhotoUrl1=" + carPhotoUrl1 + ", power=" + power + ", mileage=" + mileage + ", minute=" + minute
				+ ", prebookTime=" + prebookTime + ", rangeMileage=" + rangeMileage + "]";
	}

	public String getSeaTing() {
		return seaTing;
	}

	public void setSeaTing(String seaTing) {
		this.seaTing = seaTing;
	}

	public Double getServicePriceOfMinute() {
		return servicePriceOfMinute;
	}

	public void setServicePriceOfMinute(Double servicePriceOfMinute) {
		this.servicePriceOfMinute = servicePriceOfMinute;
	}

	public Double getServicePriceOfKm() {
		return servicePriceOfKm;
	}

	public void setServicePriceOfKm(Double servicePriceOfKm) {
		this.servicePriceOfKm = servicePriceOfKm;
	}

	public Double getBillingCapPerDay() {
		return billingCapPerDay;
	}

	public void setBillingCapPerDay(Double billingCapPerDay) {
		this.billingCapPerDay = billingCapPerDay;
	}

	public Double getPriceOfMinute() {
		return priceOfMinute;
	}

	public void setPriceOfMinute(Double priceOfMinute) {
		this.priceOfMinute = priceOfMinute;
	}

	public Double getPriceOfKm() {
		return priceOfKm;
	}

	public void setPriceOfKm(Double priceOfKm) {
		this.priceOfKm = priceOfKm;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
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
