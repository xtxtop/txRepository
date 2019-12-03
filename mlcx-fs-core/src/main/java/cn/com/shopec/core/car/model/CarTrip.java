package cn.com.shopec.core.car.model;

import java.util.Date;

import cn.com.shopec.core.common.Entity;

/** 
 * 车辆行程表 数据实体类
 */
public class CarTrip extends Entity<String> {
	
	private static final long serialVersionUID = 1l;
	
	/*Auto generated properties start*/
	
	//行程id
	private String tripId;
	//车辆编号
	private String carNo;
	//启动时间
	private Date startupTime;
	//启动时间 时间范围起（查询用）
	private Date startupTimeStart;
	//启动时间 时间范围止（查询用）
	private Date startupTimeEnd;	
	//熄火时间
	private Date shutdownTime;
	//熄火时间 时间范围起（查询用）
	private Date shutdownTimeStart;
	//熄火时间 时间范围止（查询用）
	private Date shutdownTimeEnd;	
	//熄火后小电瓶电压
	private Double auxBatteryVoltage;
	//怠速时长（分）
	private Double idleSpeedDuration;
	//行驶时长（分）
	private Double drivenDuration;
	//行驶里程（米）
	private Double drivenMileage;
	//本次使用电量（百分比）
	private Double usedPower;
	//本次最高速度（Km/h）
	private Double highestSpeed;
	//刹车次数
	private Integer brakingTimes;
	//急加速次数
	private Integer rapidAccelerationTimes;
	//急减速次数
	private Integer rapidDecelerationTimes;
	//总里程（KM）
	private Double totalMileage;
	//剩余电量
	private Double remainPower;
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
	
	/*Auto generated properties end*/
	
	
	
	/*Customized properties start*/
	
	/*Customized properties end*/
	
	
	
	/*Auto generated methods start*/
	
	@Override
	public String getPK(){
		return tripId;
	}
	
	public String getTripId(){
		return tripId;
	}
	
	public void setTripId(String tripId){
		this.tripId = tripId;
	}
	
	public String getCarNo(){
		return carNo;
	}
	
	public void setCarNo(String carNo){
		this.carNo = carNo;
	}
	
	public Date getStartupTime(){
		return startupTime;
	}
	
	public void setStartupTime(Date startupTime){
		this.startupTime = startupTime;
	}
	
	public Date getStartupTimeStart(){
		return startupTimeStart;
	}
	
	public void setStartupTimeStart(Date startupTimeStart){
		this.startupTimeStart = startupTimeStart;
	}
	
	public Date getStartupTimeEnd(){
		return startupTimeEnd;
	}
	
	public void setStartupTimeEnd(Date startupTimeEnd){
		this.startupTimeEnd = startupTimeEnd;
	}	
	
	public Date getShutdownTime(){
		return shutdownTime;
	}
	
	public void setShutdownTime(Date shutdownTime){
		this.shutdownTime = shutdownTime;
	}
	
	public Date getShutdownTimeStart(){
		return shutdownTimeStart;
	}
	
	public void setShutdownTimeStart(Date shutdownTimeStart){
		this.shutdownTimeStart = shutdownTimeStart;
	}
	
	public Date getShutdownTimeEnd(){
		return shutdownTimeEnd;
	}
	
	public void setShutdownTimeEnd(Date shutdownTimeEnd){
		this.shutdownTimeEnd = shutdownTimeEnd;
	}	
	
	public Double getAuxBatteryVoltage(){
		return auxBatteryVoltage;
	}
	
	public void setAuxBatteryVoltage(Double auxBatteryVoltage){
		this.auxBatteryVoltage = auxBatteryVoltage;
	}
	
	public Double getIdleSpeedDuration(){
		return idleSpeedDuration;
	}
	
	public void setIdleSpeedDuration(Double idleSpeedDuration){
		this.idleSpeedDuration = idleSpeedDuration;
	}
	
	public Double getDrivenDuration(){
		return drivenDuration;
	}
	
	public void setDrivenDuration(Double drivenDuration){
		this.drivenDuration = drivenDuration;
	}
	
	public Double getDrivenMileage(){
		return drivenMileage;
	}
	
	public void setDrivenMileage(Double drivenMileage){
		this.drivenMileage = drivenMileage;
	}
	
	public Double getUsedPower(){
		return usedPower;
	}
	
	public void setUsedPower(Double usedPower){
		this.usedPower = usedPower;
	}
	
	public Double getHighestSpeed(){
		return highestSpeed;
	}
	
	public void setHighestSpeed(Double highestSpeed){
		this.highestSpeed = highestSpeed;
	}
	
	public Integer getBrakingTimes(){
		return brakingTimes;
	}
	
	public void setBrakingTimes(Integer brakingTimes){
		this.brakingTimes = brakingTimes;
	}
	
	public Integer getRapidAccelerationTimes(){
		return rapidAccelerationTimes;
	}
	
	public void setRapidAccelerationTimes(Integer rapidAccelerationTimes){
		this.rapidAccelerationTimes = rapidAccelerationTimes;
	}
	
	public Integer getRapidDecelerationTimes(){
		return rapidDecelerationTimes;
	}
	
	public void setRapidDecelerationTimes(Integer rapidDecelerationTimes){
		this.rapidDecelerationTimes = rapidDecelerationTimes;
	}
	
	public Double getTotalMileage(){
		return totalMileage;
	}
	
	public void setTotalMileage(Double totalMileage){
		this.totalMileage = totalMileage;
	}
	
	public Double getRemainPower(){
		return remainPower;
	}
	
	public void setRemainPower(Double remainPower){
		this.remainPower = remainPower;
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
		return "CarTrip ["
		 + "tripId = " + tripId + ", carNo = " + carNo + ", startupTime = " + startupTime + ", startupTimeStart = " + startupTimeStart + ", startupTimeEnd = " + startupTimeEnd + ", shutdownTime = " + shutdownTime + ", shutdownTimeStart = " + shutdownTimeStart + ", shutdownTimeEnd = " + shutdownTimeEnd
		 + ", auxBatteryVoltage = " + auxBatteryVoltage + ", idleSpeedDuration = " + idleSpeedDuration + ", drivenDuration = " + drivenDuration + ", drivenMileage = " + drivenMileage
		 + ", usedPower = " + usedPower + ", highestSpeed = " + highestSpeed + ", brakingTimes = " + brakingTimes + ", rapidAccelerationTimes = " + rapidAccelerationTimes
		 + ", rapidDecelerationTimes = " + rapidDecelerationTimes + ", totalMileage = " + totalMileage + ", remainPower = " + remainPower + ", createTime = " + createTime + ", createTimeStart = " + createTimeStart + ", createTimeEnd = " + createTimeEnd
		 + ", updateTime = " + updateTime + ", updateTimeStart = " + updateTimeStart + ", updateTimeEnd = " + updateTimeEnd
		+"]";
	}
}
