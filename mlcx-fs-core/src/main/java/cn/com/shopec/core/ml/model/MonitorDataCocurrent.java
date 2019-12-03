package cn.com.shopec.core.ml.model;

import java.util.Date;

import cn.com.shopec.core.common.Entity;

/** 
 * 监测数据（直流） 数据实体类
 */
public class MonitorDataCocurrent extends Entity<String> {
	
	private static final long serialVersionUID = 1l;
	
	/*Auto generated properties start*/
	
	//直流监测编号
	private String cocurrentNo;
	//充电桩编号
	private String chargingPileNo;
	private String chargingPileName;
	//充电枪编号
	private String chargingGunNo;
	private String chargingGunCode;
	//充电机状态(0001-告警,0002-待机,0003-工作,0004-离线,0005-完成,0006-正在操作充电桩,0007-预约中)
	private Integer batteryChargerStatus;
	//是否连接车辆(0未连接 1-连接)
	private Integer connectCar;
	//累计充电时间
	private String chargeTime;
	//有功总电度
	private String totalActivePower;
	//充电输出电压
	private String outputVoltage;
	//充电输出电流
	private String outputCurrent;
	//SOC
	private String soc;
	//电池组最低温度
	private String batteryTemperatureMin;
	//电池组最高温度
	private String batteryTemperatureMax;
	//单体电池最高电压
	private String voltageMax;
	//单体电池最低电压
	private String voltageMin;
	//全局故障代码
	private String globalError;
	//模块故障代码
	private String modularError;
	//记录时间
	private Date cordTime;
	//记录时间 时间范围起（查询用）
	private Date cordTimeStart;
	//记录时间 时间范围止（查询用）
	private Date cordTimeEnd;	
	//DC+ 温度监控
	private String dcTemperatureP;
	//DC- 温度监控
	private String dcTemperatureN;
	//汽车VIN号
	private String vin;
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
		return cocurrentNo;
	}
	
	public String getChargingPileName() {
		return chargingPileName;
	}

	public void setChargingPileName(String chargingPileName) {
		this.chargingPileName = chargingPileName;
	}

	public String getChargingGunCode() {
		return chargingGunCode;
	}

	public void setChargingGunCode(String chargingGunCode) {
		this.chargingGunCode = chargingGunCode;
	}

	public String getCocurrentNo(){
		return cocurrentNo;
	}
	
	public void setCocurrentNo(String cocurrentNo){
		this.cocurrentNo = cocurrentNo;
	}
	
	public String getChargingPileNo(){
		return chargingPileNo;
	}
	
	public void setChargingPileNo(String chargingPileNo){
		this.chargingPileNo = chargingPileNo;
	}
	
	public String getChargingGunNo(){
		return chargingGunNo;
	}
	
	public void setChargingGunNo(String chargingGunNo){
		this.chargingGunNo = chargingGunNo;
	}
	
	public Integer getBatteryChargerStatus(){
		return batteryChargerStatus;
	}
	
	public void setBatteryChargerStatus(Integer batteryChargerStatus){
		this.batteryChargerStatus = batteryChargerStatus;
	}
	
	public Integer getConnectCar(){
		return connectCar;
	}
	
	public void setConnectCar(Integer connectCar){
		this.connectCar = connectCar;
	}
	
	public String getChargeTime(){
		return chargeTime;
	}
	
	public void setChargeTime(String chargeTime){
		this.chargeTime = chargeTime;
	}
	
	public String getTotalActivePower(){
		return totalActivePower;
	}
	
	public void setTotalActivePower(String totalActivePower){
		this.totalActivePower = totalActivePower;
	}
	
	public String getOutputVoltage(){
		return outputVoltage;
	}
	
	public void setOutputVoltage(String outputVoltage){
		this.outputVoltage = outputVoltage;
	}
	
	public String getOutputCurrent(){
		return outputCurrent;
	}
	
	public void setOutputCurrent(String outputCurrent){
		this.outputCurrent = outputCurrent;
	}
	
	public String getSoc(){
		return soc;
	}
	
	public void setSoc(String soc){
		this.soc = soc;
	}
	
	public String getBatteryTemperatureMin(){
		return batteryTemperatureMin;
	}
	
	public void setBatteryTemperatureMin(String batteryTemperatureMin){
		this.batteryTemperatureMin = batteryTemperatureMin;
	}
	
	public String getBatteryTemperatureMax(){
		return batteryTemperatureMax;
	}
	
	public void setBatteryTemperatureMax(String batteryTemperatureMax){
		this.batteryTemperatureMax = batteryTemperatureMax;
	}
	
	public String getVoltageMax(){
		return voltageMax;
	}
	
	public void setVoltageMax(String voltageMax){
		this.voltageMax = voltageMax;
	}
	
	public String getVoltageMin(){
		return voltageMin;
	}
	
	public void setVoltageMin(String voltageMin){
		this.voltageMin = voltageMin;
	}
	
	public String getGlobalError(){
		return globalError;
	}
	
	public void setGlobalError(String globalError){
		this.globalError = globalError;
	}
	
	public String getModularError(){
		return modularError;
	}
	
	public void setModularError(String modularError){
		this.modularError = modularError;
	}
	
	public Date getCordTime(){
		return cordTime;
	}
	
	public void setCordTime(Date cordTime){
		this.cordTime = cordTime;
	}
	
	public Date getCordTimeStart(){
		return cordTimeStart;
	}
	
	public void setCordTimeStart(Date cordTimeStart){
		this.cordTimeStart = cordTimeStart;
	}
	
	public Date getCordTimeEnd(){
		return cordTimeEnd;
	}
	
	public void setCordTimeEnd(Date cordTimeEnd){
		this.cordTimeEnd = cordTimeEnd;
	}	
	
	public String getDcTemperatureP(){
		return dcTemperatureP;
	}
	
	public void setDcTemperatureP(String dcTemperatureP){
		this.dcTemperatureP = dcTemperatureP;
	}
	
	public String getDcTemperatureN(){
		return dcTemperatureN;
	}
	
	public void setDcTemperatureN(String dcTemperatureN){
		this.dcTemperatureN = dcTemperatureN;
	}
	
	public String getVin(){
		return vin;
	}
	
	public void setVin(String vin){
		this.vin = vin;
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
		return "MonitorDataCocurrent [cocurrentNo=" + cocurrentNo
				+ ", chargingPileNo=" + chargingPileNo + ", chargingPileName="
				+ chargingPileName + ", chargingGunNo=" + chargingGunNo
				+ ", chargingGunCode=" + chargingGunCode
				+ ", batteryChargerStatus=" + batteryChargerStatus
				+ ", connectCar=" + connectCar + ", chargeTime=" + chargeTime
				+ ", totalActivePower=" + totalActivePower + ", outputVoltage="
				+ outputVoltage + ", outputCurrent=" + outputCurrent + ", soc="
				+ soc + ", batteryTemperatureMin=" + batteryTemperatureMin
				+ ", batteryTemperatureMax=" + batteryTemperatureMax
				+ ", voltageMax=" + voltageMax + ", voltageMin=" + voltageMin
				+ ", globalError=" + globalError + ", modularError="
				+ modularError + ", cordTime=" + cordTime + ", cordTimeStart="
				+ cordTimeStart + ", cordTimeEnd=" + cordTimeEnd
				+ ", dcTemperatureP=" + dcTemperatureP + ", dcTemperatureN="
				+ dcTemperatureN + ", vin=" + vin + ", operatorId="
				+ operatorId + ", operatorType=" + operatorType
				+ ", createTime=" + createTime + ", createTimeStart="
				+ createTimeStart + ", createTimeEnd=" + createTimeEnd
				+ ", updateTime=" + updateTime + ", updateTimeStart="
				+ updateTimeStart + ", updateTimeEnd=" + updateTimeEnd + "]";
	}
}
