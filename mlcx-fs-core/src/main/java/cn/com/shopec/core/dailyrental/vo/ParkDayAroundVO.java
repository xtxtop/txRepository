package cn.com.shopec.core.dailyrental.vo;

import java.io.Serializable;

public class ParkDayAroundVO  implements Serializable{
	
	
	/** 
	*
	* @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么) 
	*/ 
	private static final long serialVersionUID = 1L;
	
	//门店id
	private String parkId;
	//门店名称
	private String parkName;
	//街道
	private String addrStreet;
	//门店坐标经度
	private String longitude;
	//门店坐标纬度
	private String latitude;
	//营业时间
	private String businessHours;
	// 距离
	private Double distance;
	//是否最近
	private String isNear;
	//地点标识（1 门店 2 地理地点）
	private  String addressTag;
	
	public String getParkId() {
		return parkId;
	}
	public void setParkId(String parkId) {
		this.parkId = parkId;
	}
	public String getParkName() {
		return parkName;
	}
	public void setParkName(String parkName) {
		this.parkName = parkName;
	}
	public String getAddrStreet() {
		return addrStreet;
	}
	public void setAddrStreet(String addrStreet) {
		this.addrStreet = addrStreet;
	}
	
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getBusinessHours() {
		return businessHours;
	}
	public void setBusinessHours(String businessHours) {
		this.businessHours = businessHours;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public Double getDistance() {
		return distance;
	}
	public void setDistance(Double distance) {
		this.distance = distance;
	}
	public String getIsNear() {
		return isNear;
	}
	public void setIsNear(String isNear) {
		this.isNear = isNear;
	}
	public String getAddressTag() {
		return addressTag;
	}
	public void setAddressTag(String addressTag) {
		this.addressTag = addressTag;
	}
	
	
}
