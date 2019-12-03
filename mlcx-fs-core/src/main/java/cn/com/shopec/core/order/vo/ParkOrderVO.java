package cn.com.shopec.core.order.vo;

import java.io.Serializable;

public class ParkOrderVO implements Serializable {

	private static final long serialVersionUID = 1L;

	// 场站编号
	private String parkNo;
	// 场站名称
	private String parkName;
	// 场站地址
	private String address;
	// 坐标经度
	private String longitude;
	// 坐标纬度
	private String latitude;
	//车位数
	private Integer parkingSpaceNumber;
	//电桩数
	private Integer chargerNumber;
	//场站图片url1
	private String parkPicUrl1;
	//场站图片url2
	private String parkPicUrl2;
	//场站图片url3
	private String parkPicUrl3;
	//营业时间
	private String businessHours;
	//节假日 是否营业（1 是 0 否）
	private Integer isBusinessFestival;
	//取车附加费
	private Double serviceFeeGet;
	//还车附件费
	private Double serviceFeeBack;
	//车辆数
	private Integer carNums;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	
	
	public Integer getCarNums() {
		return carNums;
	}



	public void setCarNums(Integer carNums) {
		this.carNums = carNums;
	}



	public String getAddress() {
		return address;
	}



	public void setAddress(String address) {
		this.address = address;
	}



	public Integer getParkingSpaceNumber() {
		return parkingSpaceNumber;
	}



	public void setParkingSpaceNumber(Integer parkingSpaceNumber) {
		this.parkingSpaceNumber = parkingSpaceNumber;
	}



	public Integer getChargerNumber() {
		return chargerNumber;
	}



	public void setChargerNumber(Integer chargerNumber) {
		this.chargerNumber = chargerNumber;
	}



	public String getParkPicUrl1() {
		return parkPicUrl1;
	}



	public void setParkPicUrl1(String parkPicUrl1) {
		this.parkPicUrl1 = parkPicUrl1;
	}



	public String getParkPicUrl2() {
		return parkPicUrl2;
	}



	public void setParkPicUrl2(String parkPicUrl2) {
		this.parkPicUrl2 = parkPicUrl2;
	}



	public String getParkPicUrl3() {
		return parkPicUrl3;
	}



	public void setParkPicUrl3(String parkPicUrl3) {
		this.parkPicUrl3 = parkPicUrl3;
	}



	public String getBusinessHours() {
		return businessHours;
	}



	public void setBusinessHours(String businessHours) {
		this.businessHours = businessHours;
	}



	public Integer getIsBusinessFestival() {
		return isBusinessFestival;
	}



	public void setIsBusinessFestival(Integer isBusinessFestival) {
		this.isBusinessFestival = isBusinessFestival;
	}



	public Double getServiceFeeGet() {
		return serviceFeeGet;
	}



	public void setServiceFeeGet(Double serviceFeeGet) {
		this.serviceFeeGet = serviceFeeGet;
	}



	public Double getServiceFeeBack() {
		return serviceFeeBack;
	}



	public void setServiceFeeBack(Double serviceFeeBack) {
		this.serviceFeeBack = serviceFeeBack;
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

	@Override
	public String toString() {
		return "ParkOrderVO [parkNo=" + parkNo + ", parkName=" + parkName + ", address=" + address + ", longitude="
				+ longitude + ", latitude=" + latitude + ", parkingSpaceNumber=" + parkingSpaceNumber
				+ ", chargerNumber=" + chargerNumber + ", parkPicUrl1=" + parkPicUrl1 + ", parkPicUrl2=" + parkPicUrl2
				+ ", parkPicUrl3=" + parkPicUrl3 + ", businessHours=" + businessHours + ", isBusinessFestival="
				+ isBusinessFestival + ", serviceFeeGet=" + serviceFeeGet + ", serviceFeeBack=" + serviceFeeBack
				+ ", carNums=" + carNums + "]";
	}

}
