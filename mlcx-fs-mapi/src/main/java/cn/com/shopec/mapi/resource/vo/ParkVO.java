package cn.com.shopec.mapi.resource.vo;

import java.io.Serializable;

public class ParkVO  implements Serializable{

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
		//城市（具体值来自字典表）
		private String cityId;
		//城市名称
		private String cityName;
		//一级行政区（省）id
		private String addrRegion1Id;
		//一级行政区（省）名称
		private String addrRegion1Name;
		//二级行政区（市/直辖市区县）id
		private String addrRegion2Id;
		//二级行政区（市/直辖市区县）名称
		private String addrRegion2Name;
		//三级行政区（区县）id
		private String addrRegion3Id;
		//三级行政区（区县）名称
		private String addrRegion3Name;
		//街道
		private String addrStreet;
		//坐标经度
		private String longitude;
		//坐标纬度
		private String latitude;
		//是否公共开放（0，不开放、1，开放，默认1）
		private Integer isPublic;
		//场站状态（0、停用，1、启用，默认0）
		private Integer isAvailable;

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

	public String getAddrRegion1Id() {
		return addrRegion1Id;
	}

	public void setAddrRegion1Id(String addrRegion1Id) {
		this.addrRegion1Id = addrRegion1Id;
	}

	public String getAddrRegion1Name() {
		return addrRegion1Name;
	}

	public void setAddrRegion1Name(String addrRegion1Name) {
		this.addrRegion1Name = addrRegion1Name;
	}

	public String getAddrRegion2Id() {
		return addrRegion2Id;
	}

	public void setAddrRegion2Id(String addrRegion2Id) {
		this.addrRegion2Id = addrRegion2Id;
	}

	public String getAddrRegion2Name() {
		return addrRegion2Name;
	}

	public void setAddrRegion2Name(String addrRegion2Name) {
		this.addrRegion2Name = addrRegion2Name;
	}

	public String getAddrRegion3Id() {
		return addrRegion3Id;
	}

	public void setAddrRegion3Id(String addrRegion3Id) {
		this.addrRegion3Id = addrRegion3Id;
	}

	public String getAddrRegion3Name() {
		return addrRegion3Name;
	}

	public void setAddrRegion3Name(String addrRegion3Name) {
		this.addrRegion3Name = addrRegion3Name;
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

	public Integer getIsPublic() {
		return isPublic;
	}

	public void setIsPublic(Integer isPublic) {
		this.isPublic = isPublic;
	}

	public Integer getIsAvailable() {
		return isAvailable;
	}

	public void setIsAvailable(Integer isAvailable) {
		this.isAvailable = isAvailable;
	}

	@Override
	public String toString() {
		return "ParkVO [parkNo=" + parkNo + ", parkName=" + parkName + ", parkType=" + parkType + ", cityId=" + cityId
				+ ", cityName=" + cityName + ", addrRegion1Id=" + addrRegion1Id + ", addrRegion1Name=" + addrRegion1Name
				+ ", addrRegion2Id=" + addrRegion2Id + ", addrRegion2Name=" + addrRegion2Name + ", addrRegion3Id="
				+ addrRegion3Id + ", addrRegion3Name=" + addrRegion3Name + ", addrStreet=" + addrStreet + ", longitude="
				+ longitude + ", latitude=" + latitude + ", isPublic=" + isPublic + ", isAvailable=" + isAvailable
				+ "]";
	}

	
	
	
	
}
