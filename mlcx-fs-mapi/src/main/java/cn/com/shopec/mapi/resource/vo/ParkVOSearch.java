
package cn.com.shopec.mapi.resource.vo;

import java.io.Serializable;
import java.util.List;

import cn.com.shopec.core.car.model.Car;

/**
 * 场站信息VO对象
 * @author fly
 *
 */

public class ParkVOSearch  implements Serializable{

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
		//详细地址
		private String addressDetail;
		//坐标经度
		private String longitude;
		//坐标纬度
		private String latitude;
		//可用车辆数
		private Integer carNum;
		//车辆距场站的距离
		private Double distance;
		//场站内可用车辆集合
		private List<Car> cars;
		//剩余车位
		private Integer parkingSpaces; 
		//地点标识（1 场站 2 地理地点）
		private  Integer addressTag;
		//站点图片指引
		private String parkPicUrl;
		//是否有红包车辆的场站(0、否 1、是)
		private String isRedPakcetPark;
		//是否是红包车目标场站（0、否 1、是）
		private String isRedPacketTargetPark;
		
		

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

	public Integer getCarNum() {
		return carNum;
	}

	public void setCarNum(Integer carNum) {
		this.carNum = carNum;
	}


	public Double getDistance() {
		return distance;
	}

	public void setDistance(Double distance) {
		this.distance = distance;
	}
	
	

	public List<Car> getCars() {
		return cars;
	}

	public void setCars(List<Car> cars) {
		this.cars = cars;
	}

	@Override
	public String toString() {
		return "ParkVOAround [parkNo=" + parkNo + ", parkName=" + parkName + ", parkType=" + parkType
				+ ", addressDetail=" + addressDetail + ", longitude=" + longitude + ", latitude=" + latitude
				+ ", carNum=" + carNum + ", distance=" + distance + ", cars=" + cars + "]";
	}

	public Integer getParkingSpaces() {
		return parkingSpaces;
	}

	public void setParkingSpaces(Integer parkingSpaces) {
		this.parkingSpaces = parkingSpaces;
	}

	public Integer getAddressTag() {
		return addressTag;
	}

	public void setAddressTag(Integer addressTag) {
		this.addressTag = addressTag;
	}

	public String getParkPicUrl() {
		return parkPicUrl;
	}

	public void setParkPicUrl(String parkPicUrl) {
		this.parkPicUrl = parkPicUrl;
	}

	public String getIsRedPakcetPark() {
		return isRedPakcetPark;
	}

	public void setIsRedPakcetPark(String isRedPakcetPark) {
		this.isRedPakcetPark = isRedPakcetPark;
	}

	public String getIsRedPacketTargetPark() {
		return isRedPacketTargetPark;
	}

	public void setIsRedPacketTargetPark(String isRedPacketTargetPark) {
		this.isRedPacketTargetPark = isRedPacketTargetPark;
	}
	
}
