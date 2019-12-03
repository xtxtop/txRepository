package cn.com.shopec.core.ml.model;

import java.util.Date;

import cn.com.shopec.core.common.Entity;

/** 
 * 地锁 数据实体类
 */
public class ParkingLock extends Entity<String> {
	
	private static final long serialVersionUID = 1l;
	
	/*Auto generated properties start*/
	
	//地锁id
	private String parkingLockNo;
	//地锁名称
	private String parkingLockName;
	//地锁编码
	private String parkingLockCode;
	//地锁序列号
	private String parkingLockSerialNumber;
	//地锁产品类型：0,、网关版、1、UDP版
	private Integer parkingLockType;
	//地锁使用类型：0停车，1停车并充电
	private Integer parkingLockUsertype;
	//升降状态(0.升 1.降)
	private Integer parkingLockStatus;
	//状态(0.可用 1.不可用)
	private Integer activeCondition;
	//地锁计费规则编号
	private String parkingLockChargingNo;
	//充电站编号
	private String stationNo;
	private String stationName;
	//充电桩编号
	private String chargingPileNo;
	private String chargingPileName;
	//操作人id
	private String operatorId;
	//操作人类型（0、系统自动操作，1、平台人员操作）
	private Integer operatorType;
	//创建日期
	private Date createTime;
	//创建日期 时间范围起（查询用）
	private Date createTimeStart;
	//创建日期 时间范围止（查询用）
	private Date createTimeEnd;	
	//更新日期
	private Date updateTime;
	//更新日期 时间范围起（查询用）
	private Date updateTimeStart;
	//更新日期 时间范围止（查询用）
	private Date updateTimeEnd;	
	
	/*Auto generated properties end*/
	
	
	
	/*Customized properties start*/
	
	/*Customized properties end*/
	
	
	
	/*Auto generated methods start*/
	
	@Override
	public String getPK(){
		return parkingLockNo;
	}
	
	public String getParkingLockNo(){
		return parkingLockNo;
	}
	
	public void setParkingLockNo(String parkingLockNo){
		this.parkingLockNo = parkingLockNo;
	}
	
	public String getParkingLockName(){
		return parkingLockName;
	}
	
	public void setParkingLockName(String parkingLockName){
		this.parkingLockName = parkingLockName;
	}
	
	public String getParkingLockCode(){
		return parkingLockCode;
	}
	
	public void setParkingLockCode(String parkingLockCode){
		this.parkingLockCode = parkingLockCode;
	}
	
	public String getParkingLockSerialNumber(){
		return parkingLockSerialNumber;
	}
	
	public void setParkingLockSerialNumber(String parkingLockSerialNumber){
		this.parkingLockSerialNumber = parkingLockSerialNumber;
	}
	
	public Integer getParkingLockType(){
		return parkingLockType;
	}
	
	public void setParkingLockType(Integer parkingLockType){
		this.parkingLockType = parkingLockType;
	}
	
	public Integer getParkingLockUsertype(){
		return parkingLockUsertype;
	}
	
	public void setParkingLockUsertype(Integer parkingLockUsertype){
		this.parkingLockUsertype = parkingLockUsertype;
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
	
	public String getStationNo(){
		return stationNo;
	}
	
	public void setStationNo(String stationNo){
		this.stationNo = stationNo;
	}
	
	public String getStationName(){
		return stationName;
	}
	
	public void setStationName(String stationName){
		this.stationName = stationName;
	}
	
	public String getChargingPileNo(){
		return chargingPileNo;
	}
	
	public void setChargingPileNo(String chargingPileNo){
		this.chargingPileNo = chargingPileNo;
	}
	
	public String getChargingPileName(){
		return chargingPileName;
	}
	
	public void setChargingPileName(String chargingPileName){
		this.chargingPileName = chargingPileName;
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
	
	
	/*Auto generated methods end*/
	
	
	
	/*Customized methods start*/
	
	/*Customized methods end*/
	
	
	@Override
	public String toString() {
		return "ParkingLock ["
		 + "parkingLockNo = " + parkingLockNo + ", parkingLockName = " + parkingLockName + ", parkingLockCode = " + parkingLockCode + ", parkingLockSerialNumber = " + parkingLockSerialNumber
		 + ", parkingLockType = " + parkingLockType + ", parkingLockUsertype = " + parkingLockUsertype + ", parkingLockStatus = " + parkingLockStatus + ", activeCondition = " + activeCondition
		 + ", parkingLockChargingNo = " + parkingLockChargingNo + ", stationNo = " + stationNo + ", stationName = " + stationName + ", chargingPileNo = " + chargingPileNo
		 + ", chargingPileName = " + chargingPileName + ", operatorId = " + operatorId + ", operatorType = " + operatorType + ", createTime = " + createTime + ", createTimeStart = " + createTimeStart + ", createTimeEnd = " + createTimeEnd
		 + ", updateTime = " + updateTime + ", updateTimeStart = " + updateTimeStart + ", updateTimeEnd = " + updateTimeEnd
		+"]";
	}
}
