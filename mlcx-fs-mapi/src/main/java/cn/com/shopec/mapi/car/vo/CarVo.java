package cn.com.shopec.mapi.car.vo;

import java.io.Serializable;

public class CarVo implements Serializable{
	/** 
	*
	* @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么) 
	*/ 
	private static final long serialVersionUID = 1L;
	//车辆编号
	private String carNo;
	//车牌号
	private String carPlateNo;
	//车型id（具体来自数据字典表）
	private String carModelId;
	//车型名称
	private String carModelName;
	//品牌名称
	private String carBrandName;
	//车辆照片url1
	private String carPhotoUrl1;
	//剩余电量百分比(0%~100%)
	private Double power;
	//总里程
	private Double mileage;
	//预估剩余里程
	private Double rangeMileage;
	//车辆使用状态
	private Integer userageStatus;
	//车辆上下线状态
	private Integer onLineStatus;
	//车辆使用人类型	1、会员   2、调度员
	private Integer useingType;
	//使用人名称
	private String useingName;
	//使用人联系方式
	private String useingPhone;
	//坐标经度
	private Double longitude;
	//坐标纬度
	private Double latitude;
	
	
	public Integer getUseingType() {
		return useingType;
	}
	public void setUseingType(Integer useingType) {
		this.useingType = useingType;
	}
	public String getUseingName() {
		return useingName;
	}
	public void setUseingName(String useingName) {
		this.useingName = useingName;
	}
	public String getUseingPhone() {
		return useingPhone;
	}
	public void setUseingPhone(String useingPhone) {
		this.useingPhone = useingPhone;
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
	public Double getRangeMileage() {
		return rangeMileage;
	}
	public void setRangeMileage(Double rangeMileage) {
		this.rangeMileage = rangeMileage;
	}
	
	public Integer getUserageStatus() {
		return userageStatus;
	}
	public void setUserageStatus(Integer userageStatus) {
		this.userageStatus = userageStatus;
	}
	public String getCarBrandName() {
		return carBrandName;
	}
	public void setCarBrandName(String carBrandName) {
		this.carBrandName = carBrandName;
	}
	
	public Integer getOnLineStatus() {
		return onLineStatus;
	}
	public void setOnLineStatus(Integer onLineStatus) {
		this.onLineStatus = onLineStatus;
	}
	@Override
	public String toString() {
		return "CarVo [carNo=" + carNo + ", carPlateNo=" + carPlateNo + ", carModelId=" + carModelId + ", carModelName="
				+ carModelName + ", carBrandName=" + carBrandName + ", carPhotoUrl1=" + carPhotoUrl1 + ", power="
				+ power + ", mileage=" + mileage + ", rangeMileage=" + rangeMileage + ", userageStatus=" + userageStatus
				+ ", onLineStatus=" + onLineStatus + ", useingType=" + useingType + ", useingName=" + useingName
				+ ", useingPhone=" + useingPhone + ", longitude=" + longitude + ", latitude=" + latitude + "]";
	}
	
	
		
}
