package cn.com.shopec.mapi.resource.vo;

import java.io.Serializable;
import java.util.Date;

public class ParkVOCarStatus  implements Serializable{

	/** 
	*
	* @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么) 
	*/ 
	private static final long serialVersionUID = 1L;

	//车辆编号
	private String carNo;
	//车牌号
	private String carPlateNo;
	  
	//品牌名称
	private String carBrandName;
	 
	//车型名称
	private String carModelName;
	     
	//上线状态（0、下线，1、上线，默认0）
	private Integer onlineStatus;
	 
	//使用状态（0，空闲、1，已预占、2，订单中、3，调度中）
	private Integer userageStatus;
	  
	//车辆照片url1
	private String carPhotoUrl1;
	   
	//剩余电量百分比(0%~100%)
	private Double power;
	//总里程
	private Double mileage;
	//所在场站的编号
	private String locationParkNo;
	  
	//充电状态，0 –未充电，1正在充电
	private Integer chargeState;
	//冲电故障状态，0-无故障，1故障
	private Integer chargingFaultStatus;
	//车辆状态（1、已启动，2、已熄火）
	private Integer carStatus;
	//坐标经度
	private Double longitude;
	//坐标纬度
	private Double latitude;
	//可续航里程（km）
	private Double rangeMileage;
	  
	//位置
	private String position; 
	//座位数
	private String  seaTing;
	   
	public Integer getChargeState(){
		return chargeState;
	}
	
	public void setChargeState(Integer chargeState){
		this.chargeState = chargeState;
	}
	
	public Integer getChargingFaultStatus(){
		return chargingFaultStatus;
	}
	
	public void setChargingFaultStatus(Integer chargingFaultStatus){
		this.chargingFaultStatus = chargingFaultStatus;
	}
	
	public Integer getCarStatus(){
		return carStatus;
	}
	
	public void setCarStatus(Integer carStatus){
		this.carStatus = carStatus;
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
	
	public Double getRangeMileage(){
		return rangeMileage;
	}
	
	public void setRangeMileage(Double rangeMileage){
		this.rangeMileage = rangeMileage;
	}
	  
	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}
  
	public String getLocationParkNo() {
		return locationParkNo;
	}

	public void setLocationParkNo(String locationParkNo) {
		this.locationParkNo = locationParkNo;
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
	 
	public String getCarBrandName(){
		return carBrandName;
	}
	
	public void setCarBrandName(String carBrandName){
		this.carBrandName = carBrandName;
	}
	 
	public String getCarModelName(){
		return carModelName;
	}
	
	public void setCarModelName(String carModelName){
		this.carModelName = carModelName;
	}
	 
	public Integer getOnlineStatus(){
		return onlineStatus;
	}
	
	public void setOnlineStatus(Integer onlineStatus){
		this.onlineStatus = onlineStatus;
	}
	 
	public Integer getUserageStatus() {
		return userageStatus;
	}

	public void setUserageStatus(Integer userageStatus) {
		this.userageStatus = userageStatus;
	}
 
	public String getCarPhotoUrl1(){
		return carPhotoUrl1;
	}
	
	public void setCarPhotoUrl1(String carPhotoUrl1){
		this.carPhotoUrl1 = carPhotoUrl1;
	}

	public String getSeaTing() {
		return seaTing;
	}

	public void setSeaTing(String seaTing) {
		this.seaTing = seaTing;
	}
	
}
