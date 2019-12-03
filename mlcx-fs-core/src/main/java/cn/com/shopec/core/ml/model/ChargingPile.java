package cn.com.shopec.core.ml.model;

import java.util.Date;
import cn.com.shopec.core.common.Entity;

/**
 * VOLTAGE_IN 充电桩 数据实体类
 */
public class ChargingPile extends Entity<String> {
	private static final long serialVersionUID = 1l;
	/* Auto generated properties start */
	// 充电桩编号
	private String chargingPileNo;
	// 客户号
	private String customerNo;
	// 充电桩名称
	private String chargingPileName;
	// 是否免费:0是，1否
	private Integer ischarging;
	// 计费方案编号
	private String billingScheme;
	// 状态(1有效,0无效)
	private Integer status;
	// 类型：0交流充电桩、1直流充电桩
	private Integer electricityType;
	// 终端编码(非集中器模式，默认1)
	private String terminalNo;
	// 充电站编号
	private String stationNo;
	private String stationName;
	// 操作人id
	private String operatorId;
	// 操作人类型（0、系统自动操作，1、平台人员操作）
	private Integer operatorType;
	// 创建日期
	private Date createTime;
	// 创建日期 时间范围起（查询用）
	private Date createTimeStart;
	// 创建日期 时间范围止（查询用）
	private Date createTimeEnd;
	// 更新日期
	private Date updateTime;
	// 更新日期 时间范围起（查询用）
	private Date updateTimeStart;
	// 更新日期 时间范围止（查询用）
	private Date updateTimeEnd;
	// 功率范围
	private String kw;
	// 输入电压范围
	private String voltageIn;
	// 输出电压范围
	private String voltageOut;
	// 支持的国际标准（‘，’分割）
	private String iso;
	/* Auto generated properties end */
	/* Customized properties start */
	/* Customized properties end */

	/* Auto generated methods start */
	@Override
	public String getPK() {
		return chargingPileNo;
	}

	public String getChargingPileNo() {
		return chargingPileNo;
	}

	public void setChargingPileNo(String chargingPileNo) {
		this.chargingPileNo = chargingPileNo;
	}

	public String getCustomerNo() {
		return customerNo;
	}

	public void setCustomerNo(String customerNo) {
		this.customerNo = customerNo;
	}

	public String getChargingPileName() {
		return chargingPileName;
	}

	public void setChargingPileName(String chargingPileName) {
		this.chargingPileName = chargingPileName;
	}

	public Integer getIscharging() {
		return ischarging;
	}

	public void setIscharging(Integer ischarging) {
		this.ischarging = ischarging;
	}

	public String getBillingScheme() {
		return billingScheme;
	}

	public void setBillingScheme(String billingScheme) {
		this.billingScheme = billingScheme;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getElectricityType() {
		return electricityType;
	}

	public void setElectricityType(Integer electricityType) {
		this.electricityType = electricityType;
	}

	public String getTerminalNo() {
		return terminalNo;
	}

	public void setTerminalNo(String terminalNo) {
		this.terminalNo = terminalNo;
	}

	public String getStationNo() {
		return stationNo;
	}

	public void setStationNo(String stationNo) {
		this.stationNo = stationNo;
	}

	public String getStationName() {
		return stationName;
	}

	public void setStationName(String stationName) {
		this.stationName = stationName;
	}

	public String getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(String operatorId) {
		this.operatorId = operatorId;
	}

	public Integer getOperatorType() {
		return operatorType;
	}

	public void setOperatorType(Integer operatorType) {
		this.operatorType = operatorType;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getCreateTimeStart() {
		return createTimeStart;
	}

	public void setCreateTimeStart(Date createTimeStart) {
		this.createTimeStart = createTimeStart;
	}

	public Date getCreateTimeEnd() {
		return createTimeEnd;
	}

	public void setCreateTimeEnd(Date createTimeEnd) {
		this.createTimeEnd = createTimeEnd;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Date getUpdateTimeStart() {
		return updateTimeStart;
	}

	public void setUpdateTimeStart(Date updateTimeStart) {
		this.updateTimeStart = updateTimeStart;
	}

	public Date getUpdateTimeEnd() {
		return updateTimeEnd;
	}

	public void setUpdateTimeEnd(Date updateTimeEnd) {
		this.updateTimeEnd = updateTimeEnd;
	}
	/* Auto generated methods end */
	/* Customized methods start */
	/* Customized methods end */

	@Override
	public String toString() {
		return "ChargingPile [" + "chargingPileNo = " + chargingPileNo + ", customerNo = " + customerNo
				+ ", chargingPileName = " + chargingPileName + ", ischarging = " + ischarging + ", billingScheme = "
				+ billingScheme + ", status = " + status + ", electricityType = " + electricityType + ", terminalNo = "
				+ terminalNo + ", stationNo = " + stationNo + ", stationName = " + stationName + ", operatorId = "
				+ operatorId + ", operatorType = " + operatorType + ", createTime = " + createTime
				+ ", createTimeStart = " + createTimeStart + ", createTimeEnd = " + createTimeEnd + ", updateTime = "
				+ updateTime + ", updateTimeStart = " + updateTimeStart + ", updateTimeEnd = " + updateTimeEnd + "]";
	}

	public String getKw() {
		return kw;
	}

	public void setKw(String kw) {
		this.kw = kw;
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

	public String getIso() {
		return iso;
	}

	public void setIso(String iso) {
		this.iso = iso;
	}
}
