package cn.com.shopec.core.mlparking.vo;

import java.util.Date;

/**
 * @author daiyuanbao
 * @category 闸机实体 vo
 *
 */
public class CGateMachineVo {
	//停车场名称
	private String parkingName;

	//闸机id
	private String gateNo;
	//停车场id
	private String parkingNo;
	//闸机编码
	private String gateCode;
	//闸机名称
	private String gateName;
	//升降状态(0.升 1.降)
	private Integer gateStatus;
	//计费规则编号
	private String billingNo;
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
	//创建时间
	private Date createTime;
	//创建时间 时间范围起（查询用）
	private Date createTimeStart;
	//创建时间 时间范围止（查询用）
	private Date createTimeEnd;	
	//状态(0.可用 1.不可用)
	private Integer activeCondition;
	//在线状态(0.在线 1.离线)
	private Integer onlineStatus;
	public String getParkingName() {
		return parkingName;
	}
	public void setParkingName(String parkingName) {
		this.parkingName = parkingName;
	}
	public String getGateNo() {
		return gateNo;
	}
	public void setGateNo(String gateNo) {
		this.gateNo = gateNo;
	}
	public String getParkingNo() {
		return parkingNo;
	}
	public void setParkingNo(String parkingNo) {
		this.parkingNo = parkingNo;
	}
	public String getGateCode() {
		return gateCode;
	}
	public void setGateCode(String gateCode) {
		this.gateCode = gateCode;
	}
	public String getGateName() {
		return gateName;
	}
	public void setGateName(String gateName) {
		this.gateName = gateName;
	}
	public Integer getGateStatus() {
		return gateStatus;
	}
	public void setGateStatus(Integer gateStatus) {
		this.gateStatus = gateStatus;
	}
	public String getBillingNo() {
		return billingNo;
	}
	public void setBillingNo(String billingNo) {
		this.billingNo = billingNo;
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
	public Integer getActiveCondition() {
		return activeCondition;
	}
	public void setActiveCondition(Integer activeCondition) {
		this.activeCondition = activeCondition;
	}
	public Integer getOnlineStatus() {
		return onlineStatus;
	}
	public void setOnlineStatus(Integer onlineStatus) {
		this.onlineStatus = onlineStatus;
	}
	@Override
	public String toString() {
		return "CGateMachineVo [parkingName=" + parkingName + ", gateNo="
				+ gateNo + ", parkingNo=" + parkingNo + ", gateCode="
				+ gateCode + ", gateName=" + gateName + ", gateStatus="
				+ gateStatus + ", billingNo=" + billingNo + ", updateTime="
				+ updateTime + ", updateTimeStart=" + updateTimeStart
				+ ", updateTimeEnd=" + updateTimeEnd + ", operatorId="
				+ operatorId + ", operatorType=" + operatorType
				+ ", createTime=" + createTime + ", createTimeStart="
				+ createTimeStart + ", createTimeEnd=" + createTimeEnd
				+ ", activeCondition=" + activeCondition + ", onlineStatus="
				+ onlineStatus + "]";
	}
	
}
