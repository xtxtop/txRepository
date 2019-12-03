package cn.com.shopec.core.car.model;

import java.util.Date;

import cn.com.shopec.core.common.Entity;

/** 
 * CarDetailMainData 数据实体类
 */
public class CarDetailMainData extends Entity<String> {
	
	private static final long serialVersionUID = 1l;
	
	/*Auto generated properties start*/
	
	private String deviceSn;
	private String carNo;
	private String carPlateNo;
	private String carSta;
	private String charging;
	private String operMode;
	private String speed;
	private String mileage;
	private String totalVol;
	private String current;
	private String soc;
	private String dcSta;
	private String gear;
	private String resistance;
	private String accPedal;
	private String brakePedalPos;
	private String elecSwitch;
	private String leasingMode;
	private String usbNet;
	private String btSta;
	private String powEnable;
	private String phyKey;
	private String carDoor;
	private String carWindow;
	private String cenctrlLock;
	private String light;
	private String handbrake;
	private String auxBatteryVol;
	private String chargingGun;
	private String locStatus;
	private String gpsMileage;
	private String gpsSpeed;
	private String gpsAngle;
	private String locSateNum;
	private String signalStrength;
	private String leftFrontDoor;
	private String rightFrontDoor;
	private String leftRearDoor;
	private String rightRearDoor;
	private String trunk;
	private String leftFrontWin;
	private String rightFrontWin;
	private String leftRearWin;
	private String rightRearWin;
	private String lonFlag;
	private String lon;
	private String latFlag;
	private String lat;
	private Date lastReportingTime;
	// 时间范围起（查询用）
	private Date lastReportingTimeStart;
	// 时间范围止（查询用）
	private Date lastReportingTimeEnd;	
	private Date createTime;
	// 时间范围起（查询用）
	private Date createTimeStart;
	// 时间范围止（查询用）
	private Date createTimeEnd;	
	private Date updateTime;
	// 时间范围起（查询用）
	private Date updateTimeStart;
	// 时间范围止（查询用）
	private Date updateTimeEnd;	
	
	/*Auto generated properties end*/
	
	
	
	/*Customized properties start*/
	
	/*Customized properties end*/
	
	
	
	/*Auto generated methods start*/
	
	@Override
	public String getPK(){
		return deviceSn;
	}
	
	public String getDeviceSn(){
		return deviceSn;
	}
	
	public void setDeviceSn(String deviceSn){
		this.deviceSn = deviceSn;
	}
	
	public String getCarNo(){
		return carNo;
	}
	
	public void setCarNo(String carNo){
		this.carNo = carNo;
	}
	
	public String getCarPlateNo(){
		return carPlateNo;
	}
	
	public void setCarPlateNo(String carPlateNo){
		this.carPlateNo = carPlateNo;
	}
	
	public String getCarSta(){
		return carSta;
	}
	
	public void setCarSta(String carSta){
		this.carSta = carSta;
	}
	
	public String getCharging(){
		return charging;
	}
	
	public void setCharging(String charging){
		this.charging = charging;
	}
	
	public String getOperMode(){
		return operMode;
	}
	
	public void setOperMode(String operMode){
		this.operMode = operMode;
	}
	
	public String getSpeed(){
		return speed;
	}
	
	public void setSpeed(String speed){
		this.speed = speed;
	}
	
	public String getMileage(){
		return mileage;
	}
	
	public void setMileage(String mileage){
		this.mileage = mileage;
	}
	
	public String getTotalVol(){
		return totalVol;
	}
	
	public void setTotalVol(String totalVol){
		this.totalVol = totalVol;
	}
	
	public String getCurrent(){
		return current;
	}
	
	public void setCurrent(String current){
		this.current = current;
	}
	
	public String getSoc(){
		return soc;
	}
	
	public void setSoc(String soc){
		this.soc = soc;
	}
	
	public String getDcSta(){
		return dcSta;
	}
	
	public void setDcSta(String dcSta){
		this.dcSta = dcSta;
	}
	
	public String getGear(){
		return gear;
	}
	
	public void setGear(String gear){
		this.gear = gear;
	}
	
	public String getResistance(){
		return resistance;
	}
	
	public void setResistance(String resistance){
		this.resistance = resistance;
	}
	
	public String getAccPedal(){
		return accPedal;
	}
	
	public void setAccPedal(String accPedal){
		this.accPedal = accPedal;
	}
	
	public String getBrakePedalPos(){
		return brakePedalPos;
	}
	
	public void setBrakePedalPos(String brakePedalPos){
		this.brakePedalPos = brakePedalPos;
	}
	
	public String getElecSwitch(){
		return elecSwitch;
	}
	
	public void setElecSwitch(String elecSwitch){
		this.elecSwitch = elecSwitch;
	}
	
	public String getLeasingMode(){
		return leasingMode;
	}
	
	public void setLeasingMode(String leasingMode){
		this.leasingMode = leasingMode;
	}
	
	public String getUsbNet() {
		return usbNet;
	}

	public void setUsbNet(String usbNet) {
		this.usbNet = usbNet;
	}

	public String getBtSta() {
		return btSta;
	}

	public void setBtSta(String btSta) {
		this.btSta = btSta;
	}

	public String getPowEnable() {
		return powEnable;
	}

	public void setPowEnable(String powEnable) {
		this.powEnable = powEnable;
	}

	public String getPhyKey() {
		return phyKey;
	}

	public void setPhyKey(String phyKey) {
		this.phyKey = phyKey;
	}

	public String getCarDoor(){
		return carDoor;
	}
	
	public void setCarDoor(String carDoor){
		this.carDoor = carDoor;
	}
	
	public String getCarWindow(){
		return carWindow;
	}
	
	public void setCarWindow(String carWindow){
		this.carWindow = carWindow;
	}
	
	public String getCenctrlLock(){
		return cenctrlLock;
	}
	
	public void setCenctrlLock(String cenctrlLock){
		this.cenctrlLock = cenctrlLock;
	}
	
	public String getLight(){
		return light;
	}
	
	public void setLight(String light){
		this.light = light;
	}
	
	public String getHandbrake(){
		return handbrake;
	}
	
	public void setHandbrake(String handbrake){
		this.handbrake = handbrake;
	}
	
	public String getAuxBatteryVol(){
		return auxBatteryVol;
	}
	
	public void setAuxBatteryVol(String auxBatteryVol){
		this.auxBatteryVol = auxBatteryVol;
	}
	
	public String getChargingGun(){
		return chargingGun;
	}
	
	public void setChargingGun(String chargingGun){
		this.chargingGun = chargingGun;
	}
	
	public String getLocStatus(){
		return locStatus;
	}
	
	public void setLocStatus(String locStatus){
		this.locStatus = locStatus;
	}
	
	public String getGpsMileage(){
		return gpsMileage;
	}
	
	public void setGpsMileage(String gpsMileage){
		this.gpsMileage = gpsMileage;
	}
	
	public String getGpsSpeed(){
		return gpsSpeed;
	}
	
	public void setGpsSpeed(String gpsSpeed){
		this.gpsSpeed = gpsSpeed;
	}
	
	public String getGpsAngle(){
		return gpsAngle;
	}
	
	public void setGpsAngle(String gpsAngle){
		this.gpsAngle = gpsAngle;
	}
	
	public String getLocSateNum(){
		return locSateNum;
	}
	
	public void setLocSateNum(String locSateNum){
		this.locSateNum = locSateNum;
	}
	
	public String getSignalStrength() {
		return signalStrength;
	}

	public void setSignalStrength(String signalStrength) {
		this.signalStrength = signalStrength;
	}

	public String getLeftFrontDoor(){
		return leftFrontDoor;
	}
	
	public void setLeftFrontDoor(String leftFrontDoor){
		this.leftFrontDoor = leftFrontDoor;
	}
	
	public String getRightFrontDoor(){
		return rightFrontDoor;
	}
	
	public void setRightFrontDoor(String rightFrontDoor){
		this.rightFrontDoor = rightFrontDoor;
	}
	
	public String getLeftRearDoor(){
		return leftRearDoor;
	}
	
	public void setLeftRearDoor(String leftRearDoor){
		this.leftRearDoor = leftRearDoor;
	}
	
	public String getRightRearDoor(){
		return rightRearDoor;
	}
	
	public void setRightRearDoor(String rightRearDoor){
		this.rightRearDoor = rightRearDoor;
	}
	
	public String getTrunk(){
		return trunk;
	}
	
	public void setTrunk(String trunk){
		this.trunk = trunk;
	}
	
	public String getLeftFrontWin(){
		return leftFrontWin;
	}
	
	public void setLeftFrontWin(String leftFrontWin){
		this.leftFrontWin = leftFrontWin;
	}
	
	public String getRightFrontWin(){
		return rightFrontWin;
	}
	
	public void setRightFrontWin(String rightFrontWin){
		this.rightFrontWin = rightFrontWin;
	}
	
	public String getLeftRearWin(){
		return leftRearWin;
	}
	
	public void setLeftRearWin(String leftRearWin){
		this.leftRearWin = leftRearWin;
	}
	
	public String getRightRearWin(){
		return rightRearWin;
	}
	
	public void setRightRearWin(String rightRearWin){
		this.rightRearWin = rightRearWin;
	}
	
	public String getLonFlag(){
		return lonFlag;
	}
	
	public void setLonFlag(String lonFlag){
		this.lonFlag = lonFlag;
	}
	
	public String getLon(){
		return lon;
	}
	
	public void setLon(String lon){
		this.lon = lon;
	}
	
	public String getLatFlag(){
		return latFlag;
	}
	
	public void setLatFlag(String latFlag){
		this.latFlag = latFlag;
	}
	
	public String getLat(){
		return lat;
	}
	
	public void setLat(String lat){
		this.lat = lat;
	}
	
	public Date getLastReportingTime(){
		return lastReportingTime;
	}
	
	public void setLastReportingTime(Date lastReportingTime){
		this.lastReportingTime = lastReportingTime;
	}
	
	public Date getLastReportingTimeStart(){
		return lastReportingTimeStart;
	}
	
	public void setLastReportingTimeStart(Date lastReportingTimeStart){
		this.lastReportingTimeStart = lastReportingTimeStart;
	}
	
	public Date getLastReportingTimeEnd(){
		return lastReportingTimeEnd;
	}
	
	public void setLastReportingTimeEnd(Date lastReportingTimeEnd){
		this.lastReportingTimeEnd = lastReportingTimeEnd;
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
		return "CarDetailMainData [deviceSn=" + deviceSn + ", carNo=" + carNo + ", carPlateNo=" + carPlateNo
				+ ", carSta=" + carSta + ", charging=" + charging + ", operMode=" + operMode + ", speed=" + speed
				+ ", mileage=" + mileage + ", totalVol=" + totalVol + ", current=" + current + ", soc=" + soc
				+ ", dcSta=" + dcSta + ", gear=" + gear + ", resistance=" + resistance + ", accPedal=" + accPedal
				+ ", brakePedalPos=" + brakePedalPos + ", elecSwitch=" + elecSwitch + ", leasingMode=" + leasingMode
				+ ", usbNet=" + usbNet + ", btSta=" + btSta + ", powEnable=" + powEnable + ", phyKey=" + phyKey
				+ ", carDoor=" + carDoor + ", carWindow=" + carWindow + ", cenctrlLock=" + cenctrlLock + ", light="
				+ light + ", handbrake=" + handbrake + ", auxBatteryVol=" + auxBatteryVol + ", chargingGun="
				+ chargingGun + ", locStatus=" + locStatus + ", gpsMileage=" + gpsMileage + ", gpsSpeed=" + gpsSpeed
				+ ", gpsAngle=" + gpsAngle + ", locSateNum=" + locSateNum + ", signalStrength=" + signalStrength
				+ ", leftFrontDoor=" + leftFrontDoor + ", rightFrontDoor=" + rightFrontDoor + ", leftRearDoor="
				+ leftRearDoor + ", rightRearDoor=" + rightRearDoor + ", trunk=" + trunk + ", leftFrontWin="
				+ leftFrontWin + ", rightFrontWin=" + rightFrontWin + ", leftRearWin=" + leftRearWin + ", rightRearWin="
				+ rightRearWin + ", lonFlag=" + lonFlag + ", lon=" + lon + ", latFlag=" + latFlag + ", lat=" + lat
				+ ", lastReportingTime=" + lastReportingTime + ", lastReportingTimeStart=" + lastReportingTimeStart
				+ ", lastReportingTimeEnd=" + lastReportingTimeEnd + ", createTime=" + createTime + ", createTimeStart="
				+ createTimeStart + ", createTimeEnd=" + createTimeEnd + ", updateTime=" + updateTime
				+ ", updateTimeStart=" + updateTimeStart + ", updateTimeEnd=" + updateTimeEnd + "]";
	}	
	
}
