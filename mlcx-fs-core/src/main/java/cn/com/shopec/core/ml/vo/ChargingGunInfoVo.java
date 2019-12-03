package cn.com.shopec.core.ml.vo;

import java.util.Date;

/**
 * @author daiyuanbao
 * @category 充电枪VO
 *
 */
public class ChargingGunInfoVo {
	//充电枪编号
		private String chargingGunNo;
		//充电枪编码
		private String chargingGunCode;
		//工作状态(0001-告警 0002-待机0003-工作 0004-离线0005-完成0006-正在操作充电桩0007-预约中)
		private Integer workStatus;
		//操作人id
		private String operatorId;
		//操作人类型（0、系统自动操作，1、平台人员操作）
		private Integer operatorType;
		//创建日期
		private Date createTime;
		//更新日期
		private Date updateTime;
		//登记人
		private String registrant;
		//登记时间
		private Date registrantTime;
		//充电桩编号
		private String chargingPileNo;
		//充电桩名称
		private String chargingPileName;
		//充电站编号
		private String chargingStationNo;
		//充电站名称
		private String chargingStationName;
		
		public Date getCreateTime() {
			return createTime;
		}
		public void setCreateTime(Date createTime) {
			this.createTime = createTime;
		}
		public String getChargingGunNo() {
			return chargingGunNo;
		}
		public void setChargingGunNo(String chargingGunNo) {
			this.chargingGunNo = chargingGunNo;
		}
		public String getChargingGunCode() {
			return chargingGunCode;
		}
		public void setChargingGunCode(String chargingGunCode) {
			this.chargingGunCode = chargingGunCode;
		}
		public Integer getWorkStatus() {
			return workStatus;
		}
		public void setWorkStatus(Integer workStatus) {
			this.workStatus = workStatus;
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
		public Date getUpdateTime() {
			return updateTime;
		}
		public void setUpdateTime(Date updateTime) {
			this.updateTime = updateTime;
		}
		public String getRegistrant() {
			return registrant;
		}
		public void setRegistrant(String registrant) {
			this.registrant = registrant;
		}
		public Date getRegistrantTime() {
			return registrantTime;
		}
		public void setRegistrantTime(Date registrantTime) {
			this.registrantTime = registrantTime;
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
		public String getChargingStationNo() {
			return chargingStationNo;
		}
		public void setChargingStationNo(String chargingStationNo) {
			this.chargingStationNo = chargingStationNo;
		}
		public String getChargingStationName() {
			return chargingStationName;
		}
		public void setChargingStationName(String chargingStationName) {
			this.chargingStationName = chargingStationName;
		}
		@Override
		public String toString() {
			return "ChargingGunInfoVo [chargingGunNo=" + chargingGunNo
					+ ", chargingGunCode=" + chargingGunCode + ", workStatus="
					+ workStatus + ", operatorId=" + operatorId
					+ ", operatorType=" + operatorType + ", createTime="
					+ createTime + ", updateTime=" + updateTime
					+ ", registrant=" + registrant + ", registrantTime="
					+ registrantTime + ", chargingPileNo=" + chargingPileNo
					+ ", chargingPileName=" + chargingPileName
					+ ", chargingStationNo=" + chargingStationNo
					+ ", chargingStationName=" + chargingStationName + "]";
		}
		
}
