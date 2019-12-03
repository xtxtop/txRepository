package cn.com.shopec.core.mlparking.vo;


/**
 * @author daiyuanbao
 *@category 地锁VO
 */
public class ParkingLockVo {
	//地锁id
	private String parkLockNo;
	//停车场id
	private String parkingNo;
	//地锁编码
	private String parkingLockCode;
	//地锁名称
	private String parkingLockName;
	//地锁类型(0,、网关版、1、UDP版)
	private Integer parkingLockType;
	//地锁序列号
	private String parkingLockSerialNumber;
	//升降状态(0.升 1.降)
	private Integer parkingLockStatus;
	//状态(0.可用 1.不可用)
	private Integer activeCondition;
	//在线状态(0.在线 1.离线)
	private Integer onlineStatus;
	//车位号
	private String spaceNo;
	//楼层数
	private String pliesNumber;
	//停车场名称
	private String parkingName;
	//停车场车位分布类型(1.地下 2.地面 3.楼层)
	private Integer spaceType;
	//楼层编号
	private String pliesNumberNo;
	//地锁使用状态(0.占用 1.空闲 2.预约)
	private Integer lockStatus;
	
	public String getParkLockNo() {
		return parkLockNo;
	}
	public void setParkLockNo(String parkLockNo) {
		this.parkLockNo = parkLockNo;
	}
	public String getParkingNo() {
		return parkingNo;
	}
	public void setParkingNo(String parkingNo) {
		this.parkingNo = parkingNo;
	}
	public String getParkingLockCode() {
		return parkingLockCode;
	}
	public void setParkingLockCode(String parkingLockCode) {
		this.parkingLockCode = parkingLockCode;
	}
	public String getParkingLockName() {
		return parkingLockName;
	}
	public void setParkingLockName(String parkingLockName) {
		this.parkingLockName = parkingLockName;
	}
	public Integer getParkingLockType() {
		return parkingLockType;
	}
	public void setParkingLockType(Integer parkingLockType) {
		this.parkingLockType = parkingLockType;
	}
	public String getParkingLockSerialNumber() {
		return parkingLockSerialNumber;
	}
	public void setParkingLockSerialNumber(String parkingLockSerialNumber) {
		this.parkingLockSerialNumber = parkingLockSerialNumber;
	}
	public Integer getParkingLockStatus() {
		return parkingLockStatus;
	}
	public void setParkingLockStatus(Integer parkingLockStatus) {
		this.parkingLockStatus = parkingLockStatus;
	}
	public Integer getActiveCondition() {
		return activeCondition;
	}
	public void setActiveCondition(Integer activeCondition) {
		this.activeCondition = activeCondition;
	}
	public Integer getOnlineStatus() {
		return onlineStatus;
	}
	public void setOnlineStatus(Integer onlineStatus) {
		this.onlineStatus = onlineStatus;
	}
	public String getSpaceNo() {
		return spaceNo;
	}
	public void setSpaceNo(String spaceNo) {
		this.spaceNo = spaceNo;
	}
	public String getPliesNumber() {
		return pliesNumber;
	}
	public void setPliesNumber(String pliesNumber) {
		this.pliesNumber = pliesNumber;
	}
	public String getParkingName() {
		return parkingName;
	}
	public void setParkingName(String parkingName) {
		this.parkingName = parkingName;
	}
	public Integer getSpaceType() {
		return spaceType;
	}
	public void setSpaceType(Integer spaceType) {
		this.spaceType = spaceType;
	}
	public String getPliesNumberNo() {
		return pliesNumberNo;
	}
	public void setPliesNumberNo(String pliesNumberNo) {
		this.pliesNumberNo = pliesNumberNo;
	}
	public Integer getLockStatus() {
		return lockStatus;
	}
	public void setLockStatus(Integer lockStatus) {
		this.lockStatus = lockStatus;
	}
}
