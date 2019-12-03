package cn.com.shopec.core.mlparking.vo;

import java.util.List;

import cn.com.shopec.core.mlparking.vo.Lock;

/**
 * @author daiyuanbao
 * @category 预约停车VO
 *
 */
public class ParkingReservation {

	// 停车场id
	private String parkingNo;
	// 停车场名称
	private String parkingName;
	//总车位
	private Integer totalParkingSpaces;
	//剩余车位
	private Integer remainingSpace;
	//经度
	private String lon;
	//纬度
	private String lat;
	// 免费时长
	private Integer freeTime;
	//计费编号
	private String billingNo;
	//收费时间段
	private String timeNum;
	//收费标准
	private String overtimePrice;
	//最高收费
	private String cappingPrice;
	// 地锁信息
	private List<Lock> lockInfoList;
	

	public Integer getTotalParkingSpaces() {
		return totalParkingSpaces;
	}
	public void setTotalParkingSpaces(Integer totalParkingSpaces) {
		this.totalParkingSpaces = totalParkingSpaces;
	}
	public Integer getRemainingSpace() {
		return remainingSpace;
	}
	public void setRemainingSpace(Integer remainingSpace) {
		this.remainingSpace = remainingSpace;
	}
	public String getTimeNum() {
		return timeNum;
	}
	public void setTimeNum(String timeNum) {
		this.timeNum = timeNum;
	}
	public String getOvertimePrice() {
		return overtimePrice;
	}
	public void setOvertimePrice(String overtimePrice) {
		this.overtimePrice = overtimePrice;
	}
	public String getCappingPrice() {
		return cappingPrice;
	}
	public void setCappingPrice(String cappingPrice) {
		this.cappingPrice = cappingPrice;
	}
	public String getLon() {
		return lon;
	}
	public void setLon(String lon) {
		this.lon = lon;
	}
	public String getLat() {
		return lat;
	}
	public void setLat(String lat) {
		this.lat = lat;
	}
	public String getBillingNo() {
		return billingNo;
	}
	public void setBillingNo(String billingNo) {
		this.billingNo = billingNo;
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
	public Integer getFreeTime() {
		return freeTime;
	}
	public void setFreeTime(Integer freeTime) {
		this.freeTime = freeTime;
	}
	public List<Lock> getLockInfoList() {
		return lockInfoList;
	}
	public void setLockInfoList(List<Lock> lockInfoList) {
		this.lockInfoList = lockInfoList;
	}

}
