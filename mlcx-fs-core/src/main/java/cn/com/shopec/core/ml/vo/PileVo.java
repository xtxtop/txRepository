package cn.com.shopec.core.ml.vo;

import java.util.List;

public class PileVo {
	private String stationNo;// 所属充电站编号 |
	private String chargingPileNo;// 充电桩编号 |
	private String chargingPileName;// 充电桩名称 |
	private String voltageIn;// 输入电压范围 |
	private String voltageOut;// 输出电压范围 |
	private String kw;// 功率范围 |
	private String electricityType;// 类型（0慢充1快充） |
	private String iso;// 支持的国际标准 |
	private String workStatus;// 工作状态(0-空闲 1-充电中) |
	private List<LockVo> locks;// 地锁列表

	public String getStationNo() {
		return stationNo;
	}

	public void setStationNo(String stationNo) {
		this.stationNo = stationNo;
	}

	public String getChargingPileNo() {
		return chargingPileNo;
	}

	public void setChargingPileNo(String chargingPileNo) {
		this.chargingPileNo = chargingPileNo;
	}

	public String getChargingPileName() {
		return chargingPileName;
	}

	public void setChargingPileName(String chargingPileName) {
		this.chargingPileName = chargingPileName;
	}

	public String getVoltageIn() {
		return voltageIn;
	}

	public void setVoltageIn(String voltageIn) {
		this.voltageIn = voltageIn;
	}

	public String getVoltageOut() {
		return voltageOut;
	}

	public void setVoltageOut(String voltageOut) {
		this.voltageOut = voltageOut;
	}

	public String getKw() {
		return kw;
	}

	public void setKw(String kw) {
		this.kw = kw;
	}

	public String getElectricityType() {
		return electricityType;
	}

	public void setElectricityType(String electricityType) {
		this.electricityType = electricityType;
	}

	public String getIso() {
		return iso;
	}

	public void setIso(String iso) {
		this.iso = iso;
	}

	public String getWorkStatus() {
		return workStatus;
	}

	public void setWorkStatus(String workStatus) {
		this.workStatus = workStatus;
	}

	public List<LockVo> getLocks() {
		return locks;
	}

	public void setLocks(List<LockVo> locks) {
		this.locks = locks;
	}

}
