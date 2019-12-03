package cn.com.shopec.core.ml.model;

import java.util.Date;

import cn.com.shopec.core.common.Entity;

/** 
 * 监测数据（交流） 数据实体类
 */
public class MonitorDataInterflow extends Entity<String> {
	
	private static final long serialVersionUID = 1l;
	
	/*Auto generated properties start*/
	
	//交流监测编号
	private String interflowNo;
	//充电桩编号
	private String chargingPileNo;
	private String chargingPileName;
	//充电枪编号
	private String chargingGunNo;
	private String chargingGunCode;
	//连接确认开关状态(0关1开)
	private Integer switchState;
	//工作状态(0001-告警 0002-待机 0003-工作 0004-离线 0005-完成 0006-正在操作充电桩 0007-预约中)
	private Integer workState;
	//过压告警(0不过压，1过压)
	private Integer overvoltage;
	//欠压告警(0不欠压，1欠压)
	private Integer undervoltage;
	//过负荷告警(0不过负荷，1过负荷)
	private Integer overload;
	//过流告警(0不过流，1过流)
	private Integer overcurrent;
	//急停告警(0正常，1急停)
	private Integer abruptStop;
	//防雷告警(0正常，1.防雷警告)
	private Integer lightningProtection;
	//电表异常(0正常，1异常)
	private Integer unusual;
	//预留
	private String reserved;
	//充电输出电压
	private String outputVoltage;
	//充电输出电流
	private String outputCurrent;
	//输出继电器状态(0关，1开)
	private Integer relayState;
	//有功总电度
	private String electricity;
	//累计充电时间
	private String time;
	//保存时间
	private Date recordTime;
	//保存时间 时间范围起（查询用）
	private Date recordTimeStart;
	//保存时间 时间范围止（查询用）
	private Date recordTimeEnd;	
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
		return interflowNo;
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

	public String getInterflowNo(){
		return interflowNo;
	}
	
	public void setInterflowNo(String interflowNo){
		this.interflowNo = interflowNo;
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
	
	public Integer getSwitchState(){
		return switchState;
	}
	
	public void setSwitchState(Integer switchState){
		this.switchState = switchState;
	}
	
	public Integer getWorkState(){
		return workState;
	}
	
	public void setWorkState(Integer workState){
		this.workState = workState;
	}
	
	public Integer getOvervoltage(){
		return overvoltage;
	}
	
	public void setOvervoltage(Integer overvoltage){
		this.overvoltage = overvoltage;
	}
	
	public Integer getUndervoltage(){
		return undervoltage;
	}
	
	public void setUndervoltage(Integer undervoltage){
		this.undervoltage = undervoltage;
	}
	
	public Integer getOverload(){
		return overload;
	}
	
	public void setOverload(Integer overload){
		this.overload = overload;
	}
	
	public Integer getOvercurrent(){
		return overcurrent;
	}
	
	public void setOvercurrent(Integer overcurrent){
		this.overcurrent = overcurrent;
	}
	
	public Integer getAbruptStop(){
		return abruptStop;
	}
	
	public void setAbruptStop(Integer abruptStop){
		this.abruptStop = abruptStop;
	}
	
	public Integer getLightningProtection(){
		return lightningProtection;
	}
	
	public void setLightningProtection(Integer lightningProtection){
		this.lightningProtection = lightningProtection;
	}
	
	public Integer getUnusual(){
		return unusual;
	}
	
	public void setUnusual(Integer unusual){
		this.unusual = unusual;
	}
	
	public String getReserved(){
		return reserved;
	}
	
	public void setReserved(String reserved){
		this.reserved = reserved;
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
	
	public Integer getRelayState(){
		return relayState;
	}
	
	public void setRelayState(Integer relayState){
		this.relayState = relayState;
	}
	
	public String getElectricity(){
		return electricity;
	}
	
	public void setElectricity(String electricity){
		this.electricity = electricity;
	}
	
	public String getTime(){
		return time;
	}
	
	public void setTime(String time){
		this.time = time;
	}
	
	
	public Date getRecordTime(){
		return recordTime;
	}
	
	public void setRecordTime(Date recordTime){
		this.recordTime = recordTime;
	}
	
	public Date getRecordTimeStart(){
		return recordTimeStart;
	}
	
	public void setRecordTimeStart(Date recordTimeStart){
		this.recordTimeStart = recordTimeStart;
	}
	
	public Date getRecordTimeEnd(){
		return recordTimeEnd;
	}
	
	public void setRecordTimeEnd(Date recordTimeEnd){
		this.recordTimeEnd = recordTimeEnd;
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
		return "MonitorDataInterflow [interflowNo=" + interflowNo
				+ ", chargingPileNo=" + chargingPileNo + ", chargingPileName="
				+ chargingPileName + ", chargingGunNo=" + chargingGunNo
				+ ", chargingGunCode=" + chargingGunCode + ", switchState="
				+ switchState + ", workState=" + workState + ", overvoltage="
				+ overvoltage + ", undervoltage=" + undervoltage
				+ ", overload=" + overload + ", overcurrent=" + overcurrent
				+ ", abruptStop=" + abruptStop + ", lightningProtection="
				+ lightningProtection + ", unusual=" + unusual + ", reserved="
				+ reserved + ", outputVoltage=" + outputVoltage
				+ ", outputCurrent=" + outputCurrent + ", relayState="
				+ relayState + ", electricity=" + electricity + ", time="
				+ time + ", recordTime=" + recordTime + ", recordTimeStart="
				+ recordTimeStart + ", recordTimeEnd=" + recordTimeEnd
				+ ", operatorId=" + operatorId + ", operatorType="
				+ operatorType + ", createTime=" + createTime
				+ ", createTimeStart=" + createTimeStart + ", createTimeEnd="
				+ createTimeEnd + ", updateTime=" + updateTime
				+ ", updateTimeStart=" + updateTimeStart + ", updateTimeEnd="
				+ updateTimeEnd + "]";
	}
}
