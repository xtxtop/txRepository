package cn.com.shopec.core.ml.vo;

public class LockVo {

	// 地锁id
	private String parkingLockNo;
	// 地锁名称
	private String parkingLockName;
	// 空闲状态(0.空闲1.占用)
	private String parkingLockStatus;
	//是否存在进行中的订单(1.存在 2.不存在)
	private Integer type;
	//订单编号
	private String orderNo;

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getParkingLockNo() {
		return parkingLockNo;
	}

	public void setParkingLockNo(String parkingLockNo) {
		this.parkingLockNo = parkingLockNo;
	}

	public String getParkingLockName() {
		return parkingLockName;
	}

	public void setParkingLockName(String parkingLockName) {
		this.parkingLockName = parkingLockName;
	}

	public String getParkingLockStatus() {
		return parkingLockStatus;
	}

	public void setParkingLockStatus(String parkingLockStatus) {
		this.parkingLockStatus = parkingLockStatus;
	}

}
