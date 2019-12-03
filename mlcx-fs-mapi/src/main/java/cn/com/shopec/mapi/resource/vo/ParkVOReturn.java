package cn.com.shopec.mapi.resource.vo;

import java.io.Serializable;

public class ParkVOReturn  implements Serializable{

	/** 
	*
	* @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么) 
	*/ 
	private static final long serialVersionUID = 1L;
	
	//场站编号
		private String parkNo;
		//场站名称
		private String parkName;
		//场站类型（1、管理类，2、使用类，默认1）
		private Integer parkType;
		//街道
		private String addressDetail;
		//坐标经度
		private String longitude;
		//坐标纬度
		private String latitude;
		//可用车位数
		private Integer carSpaceNum;
		//车辆位置距场站的距离
		private Double distance;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getParkNo() {
		return parkNo;
	}

	public void setParkNo(String parkNo) {
		this.parkNo = parkNo;
	}

	public String getParkName() {
		return parkName;
	}

	public void setParkName(String parkName) {
		this.parkName = parkName;
	}

	public Integer getParkType() {
		return parkType;
	}

	public void setParkType(Integer parkType) {
		this.parkType = parkType;
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

	public String getAddressDetail() {
		return addressDetail;
	}

	public void setAddressDetail(String addressDetail) {
		this.addressDetail = addressDetail;
	}

	public Integer getCarSpaceNum() {
		return carSpaceNum;
	}

	public void setCarSpaceNum(Integer carSpaceNum) {
		this.carSpaceNum = carSpaceNum;
	}

	public Double getDistance() {
		return distance;
	}

	public void setDistance(Double distance) {
		this.distance = distance;
	}

	@Override
	public String toString() {
		return "ParkVOReturn [parkNo=" + parkNo + ", parkName=" + parkName + ", parkType=" + parkType
				+ ", addressDetail=" + addressDetail + ", longitude=" + longitude + ", latitude=" + latitude
				+ ", carSpaceNum=" + carSpaceNum + "]";
	}



	

	
	
	
	
}
