package cn.com.shopec.core.mlparking.vo;

import java.util.Arrays;

/**
 * APP端 、停车场列表模型
 * 
 * @author Administrator
 *
 */
public class CParkingVo {
	// 停车场编号
	private String parkingNo;
	// 停车场名称
	private String parkingName;
	// 总车位数
	private Integer parkingTotalNumber;
	// 剩余车位数
	private Integer parkingSpaceNumber;
	// 坐标经度
	private String longitude;
	// 坐标纬度
	private String latitude;
	// 停车场开放时间
	private String parkBusinessHours;
	// 总楼层数
	private Integer pliesTotalNumber;
	// 图片路径
	private String fileUrl;
	// 两点之间的距离
	private String distance;
	// 免费时间
	private String freeTime;
	// 每小时费用
	private String hoursCost;
	// 一天封顶费用
	private String dayMaxCost;
	// 运营城市编号
	private String operatingCityNo;
	// 运营城市名称
	private String operatingCityName;
	// 停车场类型(0.闸机 1.地锁,2无设备)
	private String parkingType;
	// 标签集合
	private String[] labelList;
	// 地下停车场楼层数
	private Integer undergroundNumber;
	// 地下总车位
	private Integer undergroundParkingSpaceNumber;
	// 地下剩余车位
	private Integer undergroundSurplusSpaceNumber;
	// 地面停车场楼层数
	private Integer groundNumber;
	// 地面总车位
	private Integer groundParkingSpaceNumber;
	// 地面剩余车位
	private Integer groundSurplusSpaceNumber;

	public Integer getUndergroundNumber() {
		return undergroundNumber;
	}

	public void setUndergroundNumber(Integer undergroundNumber) {
		this.undergroundNumber = undergroundNumber;
	}

	public Integer getUndergroundParkingSpaceNumber() {
		return undergroundParkingSpaceNumber;
	}

	public void setUndergroundParkingSpaceNumber(
			Integer undergroundParkingSpaceNumber) {
		this.undergroundParkingSpaceNumber = undergroundParkingSpaceNumber;
	}

	public Integer getUndergroundSurplusSpaceNumber() {
		return undergroundSurplusSpaceNumber;
	}

	public void setUndergroundSurplusSpaceNumber(
			Integer undergroundSurplusSpaceNumber) {
		this.undergroundSurplusSpaceNumber = undergroundSurplusSpaceNumber;
	}

	public Integer getGroundNumber() {
		return groundNumber;
	}

	public void setGroundNumber(Integer groundNumber) {
		this.groundNumber = groundNumber;
	}

	public Integer getGroundParkingSpaceNumber() {
		return groundParkingSpaceNumber;
	}

	public void setGroundParkingSpaceNumber(Integer groundParkingSpaceNumber) {
		this.groundParkingSpaceNumber = groundParkingSpaceNumber;
	}

	public Integer getGroundSurplusSpaceNumber() {
		return groundSurplusSpaceNumber;
	}

	public void setGroundSurplusSpaceNumber(Integer groundSurplusSpaceNumber) {
		this.groundSurplusSpaceNumber = groundSurplusSpaceNumber;
	}

	@Override
	public String toString() {
		return "CParkingVo [parkingNo=" + parkingNo + ", parkingName="
				+ parkingName + ", parkingTotalNumber=" + parkingTotalNumber
				+ ", parkingSpaceNumber=" + parkingSpaceNumber + ", longitude="
				+ longitude + ", latitude=" + latitude + ", parkBusinessHours="
				+ parkBusinessHours + ", pliesTotalNumber=" + pliesTotalNumber
				+ ", fileUrl=" + fileUrl + ", distance=" + distance
				+ ", freeTime=" + freeTime + ", hoursCost=" + hoursCost
				+ ", dayMaxCost=" + dayMaxCost + ", operatingCityNo="
				+ operatingCityNo + ", operatingCityName=" + operatingCityName
				+ ", parkingType=" + parkingType + ", labelList="
				+ Arrays.toString(labelList) + ", undergroundNumber="
				+ undergroundNumber + ", undergroundParkingSpaceNumber="
				+ undergroundParkingSpaceNumber
				+ ", undergroundSurplusSpaceNumber="
				+ undergroundSurplusSpaceNumber + ", groundNumber="
				+ groundNumber + ", groundParkingSpaceNumber="
				+ groundParkingSpaceNumber + ", groundSurplusSpaceNumber="
				+ groundSurplusSpaceNumber + "]";
	}

	public String getParkingNo() {
		return parkingNo;
	}

	public void setParkingNo(String parkingNo) {
		this.parkingNo = parkingNo;
	}

	public String getParkingName() {
		return parkingName;
	}

	public void setParkingName(String parkingName) {
		this.parkingName = parkingName;
	}

	public Integer getParkingTotalNumber() {
		return parkingTotalNumber;
	}

	public void setParkingTotalNumber(Integer parkingTotalNumber) {
		this.parkingTotalNumber = parkingTotalNumber;
	}

	public Integer getParkingSpaceNumber() {
		return parkingSpaceNumber;
	}

	public void setParkingSpaceNumber(Integer parkingSpaceNumber) {
		this.parkingSpaceNumber = parkingSpaceNumber;
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

	public String getParkBusinessHours() {
		return parkBusinessHours;
	}

	public void setParkBusinessHours(String parkBusinessHours) {
		this.parkBusinessHours = parkBusinessHours;
	}

	public String getFileUrl() {
		return fileUrl;
	}

	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}

	public String getDistance() {
		return distance;
	}

	public void setDistance(String distance) {
		this.distance = distance;
	}

	public String getFreeTime() {
		return freeTime;
	}

	public void setFreeTime(String freeTime) {
		this.freeTime = freeTime;
	}

	public String getHoursCost() {
		return hoursCost;
	}

	public void setHoursCost(String hoursCost) {
		this.hoursCost = hoursCost;
	}

	public String getDayMaxCost() {
		return dayMaxCost;
	}

	public void setDayMaxCost(String dayMaxCost) {
		this.dayMaxCost = dayMaxCost;
	}

	public String getOperatingCityNo() {
		return operatingCityNo;
	}

	public void setOperatingCityNo(String operatingCityNo) {
		this.operatingCityNo = operatingCityNo;
	}

	public String getOperatingCityName() {
		return operatingCityName;
	}

	public void setOperatingCityName(String operatingCityName) {
		this.operatingCityName = operatingCityName;
	}

	public String[] getLabelList() {
		return labelList;
	}

	public void setLabelList(String[] labelList) {
		this.labelList = labelList;
	}

	public Integer getPliesTotalNumber() {
		return pliesTotalNumber;
	}

	public void setPliesTotalNumber(Integer pliesTotalNumber) {
		this.pliesTotalNumber = pliesTotalNumber;
	}

	public String getParkingType() {
		return parkingType;
	}

	public void setParkingType(String parkingType) {
		this.parkingType = parkingType;
	}

}
