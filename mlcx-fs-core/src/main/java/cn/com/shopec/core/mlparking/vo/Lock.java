package cn.com.shopec.core.mlparking.vo;

/**
 * @author daiyuanabo
 * @category APP地锁vo
 *
 */
public class Lock {
	//地锁编号
	private String lockNo;
	//地锁升降状态(0.升 1.降)
	private Integer parkingLockStatus;
	//地锁名称
	private String lockName;
	//地锁使用状态(0.占用 1.空闲 2.预约)
	private Integer lockStatus;
	//车位号
	private String spaceNo;
	// 免费时长
	private Integer freeTime;
	
	public Integer getFreeTime() {
		return freeTime;
	}
	public void setFreeTime(Integer freeTime) {
		this.freeTime = freeTime;
	}
	public String getSpaceNo() {
		return spaceNo;
	}
	public void setSpaceNo(String spaceNo) {
		this.spaceNo = spaceNo;
	}
	public Integer getParkingLockStatus() {
		return parkingLockStatus;
	}
	public void setParkingLockStatus(Integer parkingLockStatus) {
		this.parkingLockStatus = parkingLockStatus;
	}
	public String getLockNo() {
		return lockNo;
	}
	public void setLockNo(String lockNo) {
		this.lockNo = lockNo;
	}
	public Integer getLockStatus() {
		return lockStatus;
	}
	public void setLockStatus(Integer lockStatus) {
		this.lockStatus = lockStatus;
	}
	public String getLockName() {
		return lockName;
	}
	public void setLockName(String lockName) {
		this.lockName = lockName;
	}
	
}
