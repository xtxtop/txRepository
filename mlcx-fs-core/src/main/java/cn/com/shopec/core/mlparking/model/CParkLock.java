package cn.com.shopec.core.mlparking.model;

import java.util.Date;

import cn.com.shopec.core.common.Entity;

/** 
 * 地锁表 数据实体类
 */
public class CParkLock extends Entity<String> {
	
	private static final long serialVersionUID = 1l;
	
	/*Auto generated properties start*/
	
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
	//地锁计费规则编号
	private String parkingLockChargingNo;
	//在线状态(0.在线 1.离线)
	private Integer onlineStatus;
	//车位号
	private String spaceNo;
	//楼层数
	private Integer pliesNumber;
	//创建时间
	private Date createTime;
	//创建时间 时间范围起（查询用）
	private Date createTimeStart;
	//创建时间 时间范围止（查询用）
	private Date createTimeEnd;	
	//修改时间
	private Date updateTime;
	//修改时间 时间范围起（查询用）
	private Date updateTimeStart;
	//修改时间 时间范围止（查询用）
	private Date updateTimeEnd;	
	//操作人id
	private String operatorId;
	//操作人类型(0、系统自动操作，1、平台人员操作)
	private Integer operatorType;
	//停车场名称
	private String parkingName;
	//停车场车位分布类型(1.地下 2.地面 3.楼层)
	private Integer spaceType;
	//楼层编号
	private String pliesNumberNo;
	//地锁使用状态(0.占用 1.空闲 2.预约)
	private Integer lockStatus;
	public Integer getLockStatus() {
		return lockStatus;
	}

	public void setLockStatus(Integer lockStatus) {
		this.lockStatus = lockStatus;
	}

	public void setPliesNumberNo(String pliesNumberNo) {
		this.pliesNumberNo = pliesNumberNo;
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

	public void setPliesNumbeRNo(String pliesNumberNo) {
		this.pliesNumberNo = pliesNumberNo;
	}

	@Override
	public String getPK(){
		return parkLockNo;
	}
	
	public String getParkLockNo(){
		return parkLockNo;
	}
	
	public void setParkLockNo(String parkLockNo){
		this.parkLockNo = parkLockNo;
	}
	
	public String getParkingNo(){
		return parkingNo;
	}
	
	public void setParkingNo(String parkingNo){
		this.parkingNo = parkingNo;
	}
	
	public String getParkingLockCode(){
		return parkingLockCode;
	}
	
	public void setParkingLockCode(String parkingLockCode){
		this.parkingLockCode = parkingLockCode;
	}
	
	public String getParkingLockName(){
		return parkingLockName;
	}
	
	public void setParkingLockName(String parkingLockName){
		this.parkingLockName = parkingLockName;
	}
	
	public Integer getParkingLockType(){
		return parkingLockType;
	}
	
	public void setParkingLockType(Integer parkingLockType){
		this.parkingLockType = parkingLockType;
	}
	
	public String getParkingLockSerialNumber(){
		return parkingLockSerialNumber;
	}
	
	public void setParkingLockSerialNumber(String parkingLockSerialNumber){
		this.parkingLockSerialNumber = parkingLockSerialNumber;
	}
	
	public Integer getParkingLockStatus(){
		return parkingLockStatus;
	}
	
	public void setParkingLockStatus(Integer parkingLockStatus){
		this.parkingLockStatus = parkingLockStatus;
	}
	
	public Integer getActiveCondition(){
		return activeCondition;
	}
	
	public void setActiveCondition(Integer activeCondition){
		this.activeCondition = activeCondition;
	}
	
	public String getParkingLockChargingNo(){
		return parkingLockChargingNo;
	}
	
	public void setParkingLockChargingNo(String parkingLockChargingNo){
		this.parkingLockChargingNo = parkingLockChargingNo;
	}
	
	public Integer getOnlineStatus(){
		return onlineStatus;
	}
	
	public void setOnlineStatus(Integer onlineStatus){
		this.onlineStatus = onlineStatus;
	}
	
	public String getSpaceNo(){
		return spaceNo;
	}
	
	public void setSpaceNo(String spaceNo){
		this.spaceNo = spaceNo;
	}
	
	public Integer getPliesNumber(){
		return pliesNumber;
	}
	
	public void setPliesNumber(Integer pliesNumber){
		this.pliesNumber = pliesNumber;
	}
	
	public Date getCreateTime(){
		return createTime;
	}
	
	public void setCreateTime(Date createTime){
		this.createTime = createTime;
	}
	
	public Date getCreateTimeStart(){
		return createTimeStart;
	}
	
	public void setCreateTimeStart(Date createTimeStart){
		this.createTimeStart = createTimeStart;
	}
	
	public Date getCreateTimeEnd(){
		return createTimeEnd;
	}
	
	public void setCreateTimeEnd(Date createTimeEnd){
		this.createTimeEnd = createTimeEnd;
	}	
	
	public Date getUpdateTime(){
		return updateTime;
	}
	
	public void setUpdateTime(Date updateTime){
		this.updateTime = updateTime;
	}
	
	public Date getUpdateTimeStart(){
		return updateTimeStart;
	}
	
	public void setUpdateTimeStart(Date updateTimeStart){
		this.updateTimeStart = updateTimeStart;
	}
	
	public Date getUpdateTimeEnd(){
		return updateTimeEnd;
	}
	
	public void setUpdateTimeEnd(Date updateTimeEnd){
		this.updateTimeEnd = updateTimeEnd;
	}	
	
	public String getOperatorId(){
		return operatorId;
	}
	
	public void setOperatorId(String operatorId){
		this.operatorId = operatorId;
	}
	
	public Integer getOperatorType(){
		return operatorType;
	}
	
	public void setOperatorType(Integer operatorType){
		this.operatorType = operatorType;
	}
	
	
	/*Auto generated methods end*/
	
	
	
	/*Customized methods start*/
	
	/*Customized methods end*/
	
	
	public String getParkingName() {
		return parkingName;
	}

	public void setParkingName(String parkingName) {
		this.parkingName = parkingName;
	}

	@Override
	public String toString() {
		return "CParkLock [parkLockNo=" + parkLockNo + ", parkingNo="
				+ parkingNo + ", parkingLockCode=" + parkingLockCode
				+ ", parkingLockName=" + parkingLockName + ", parkingLockType="
				+ parkingLockType + ", parkingLockSerialNumber="
				+ parkingLockSerialNumber + ", parkingLockStatus="
				+ parkingLockStatus + ", activeCondition=" + activeCondition
				+ ", parkingLockChargingNo=" + parkingLockChargingNo
				+ ", onlineStatus=" + onlineStatus + ", spaceNo=" + spaceNo
				+ ", pliesNumber=" + pliesNumber + ", createTime=" + createTime
				+ ", createTimeStart=" + createTimeStart + ", createTimeEnd="
				+ createTimeEnd + ", updateTime=" + updateTime
				+ ", updateTimeStart=" + updateTimeStart + ", updateTimeEnd="
				+ updateTimeEnd + ", operatorId=" + operatorId
				+ ", operatorType=" + operatorType + ", parkingName="
				+ parkingName + ", spaceType=" + spaceType + ", pliesNumberNo="
				+ pliesNumberNo + ", lockStatus=" + lockStatus + "]";
	}

	
}
