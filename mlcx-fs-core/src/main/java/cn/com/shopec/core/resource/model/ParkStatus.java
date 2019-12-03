package cn.com.shopec.core.resource.model;

import java.util.Date;

import cn.com.shopec.core.common.Entity;

/** 
 * 场站状态表 数据实体类
 */
public class ParkStatus extends Entity<String> {
	
	private static final long serialVersionUID = 1l;
	
	/*Auto generated properties start*/
	
	//场站编号
	private String parkNo;
	//车位数
	private Integer parkingSpaces;
	//空闲车位
	private Integer freeParking;
	//车辆数
	private Integer carNumber;
	//电桩数
	private Integer chargerNumber;
	//预约数
	private Integer reservedCarNumber;
	//使出（预留）
	private Integer carOut;
	//驶入（预留）
	private Integer carIn;
	//创建时间
	private Date createTime;
	//创建时间 时间范围起（查询用）
	private Date createTimeStart;
	//创建时间 时间范围止（查询用）
	private Date createTimeEnd;	
	//更新时间
	private Date updateTime;
	//更新时间 时间范围起（查询用）
	private Date updateTimeStart;
	//更新时间 时间范围止（查询用）
	private Date updateTimeEnd;	
	
	//场站名称
	private String parkName;
	//警告子类别
	private String warningSubType;
	//城市名称
	private String cityName;
	//场站类型（1、管理类，2、使用类，默认1）
		private Integer parkType;
	/*Auto generated properties end*/
	
	
	
	/*Customized properties start*/
	
	/*Customized properties end*/
	
	
	
	/*Auto generated methods start*/
	
	@Override
	public String getPK(){
		return parkNo;
	}
	
	public Integer getParkType() {
		return parkType;
	}

	public void setParkType(Integer parkType) {
		this.parkType = parkType;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getWarningSubType() {
		return warningSubType;
	}

	public void setWarningSubType(String warningSubType) {
		this.warningSubType = warningSubType;
	}

	public String getParkName() {
		return parkName;
	}

	public void setParkName(String parkName) {
		this.parkName = parkName;
	}

	public String getParkNo(){
		return parkNo;
	}
	
	public void setParkNo(String parkNo){
		this.parkNo = parkNo;
	}
	
	public Integer getParkingSpaces(){
		return parkingSpaces;
	}
	
	public void setParkingSpaces(Integer parkingSpaces){
		this.parkingSpaces = parkingSpaces;
	}
	
	public Integer getFreeParking(){
		return freeParking;
	}
	
	public void setFreeParking(Integer freeParking){
		this.freeParking = freeParking;
	}
	
	public Integer getCarNumber(){
		return carNumber;
	}
	
	public void setCarNumber(Integer carNumber){
		this.carNumber = carNumber;
	}
	
	public Integer getChargerNumber(){
		return chargerNumber;
	}
	
	public void setChargerNumber(Integer chargerNumber){
		this.chargerNumber = chargerNumber;
	}
	
	public Integer getReservedCarNumber(){
		return reservedCarNumber;
	}
	
	public void setReservedCarNumber(Integer reservedCarNumber){
		this.reservedCarNumber = reservedCarNumber;
	}
	
	public Integer getCarOut(){
		return carOut;
	}
	
	public void setCarOut(Integer carOut){
		this.carOut = carOut;
	}
	
	public Integer getCarIn(){
		return carIn;
	}
	
	public void setCarIn(Integer carIn){
		this.carIn = carIn;
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
		return "ParkStatus ["
		 + "parkNo = " + parkNo + ", parkingSpaces = " + parkingSpaces + ", freeParking = " + freeParking + ", carNumber = " + carNumber
		 + ", chargerNumber = " + chargerNumber + ", reservedCarNumber = " + reservedCarNumber + ", carOut = " + carOut + ", carIn = " + carIn
		 + ", createTime = " + createTime + ", createTimeStart = " + createTimeStart + ", createTimeEnd = " + createTimeEnd + ", updateTime = " + updateTime + ", updateTimeStart = " + updateTimeStart + ", updateTimeEnd = " + updateTimeEnd
		+"]";
	}
}
