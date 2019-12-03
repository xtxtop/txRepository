package cn.com.shopec.mapi.park.controller.vo;

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
	// 免费时长
	private Integer freeTime;
	// 地锁信息
	private List<Lock> lockInfoList;
	//计费编号
	private String billingNo;
	
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
