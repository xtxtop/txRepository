package cn.com.shopec.core.marketing.model;

import java.util.Date;

import cn.com.shopec.core.common.Entity;

/** 
 * 地区应繳押金表 数据实体类
 */
public class AreaDeposit extends Entity<String> {
	
	private static final long serialVersionUID = 8776584888752888184L;

	private String id;//id
	private String addrRegion1Id;// 一级行政区（省）id
	private String addrRegion1Name;// 一级行政区（省）名称
	private String addrRegion2Id;// 二级行政区（市/直辖市区县）id
	private String addrRegion2Name;// 二级行政区（市/直辖市区县）名称
	private String addrRegion3Id;// 三级行政区（区县）id
	private String addrRegion3Name;// 三级行政区（区县）名称
	private Double depositAmount;//押金金额
	private Integer isAvailable;//可用状态（1、可用，0、不可用，默认0）
	private Date availableUpdateTime;//可用状态更新时间
	private Date availableUpdateTimeStart;//可用状态更新时间 时间范围起（查询用）
	private Date availableUpdateTimeEnd;//可用状态更新时间 时间范围止（查询用）	
	private Integer censorStatus;//审核状态（0、未审核，1、已审核，2、待审核，3、未通过，默认0）
	private String censorId;//审核人id
	private Date censorTime;//审核时间
	private Date censorTimeStart;//审核时间 时间范围起（查询用）
	private Date censorTimeEnd;//审核时间 时间范围止（查询用）
	private String censorMemo;//审核备注
	private Integer operatorType;//操作人类型
	private Integer isDeleted;//是否删除（0、未删除，1、已删除，默认0）
	private String operatorId;//操作人id
	private String operatorName;//操作人姓名
	private Date createTime;//创建时间
	private Date createTimeStart;//创建时间 时间范围起（查询用）
	private Date createTimeEnd;	//创建时间 时间范围止（查询用）
	private Date updateTime;//更新时间
	private Date updateTimeStart;//更新时间 时间范围起（查询用）
	private Date updateTimeEnd;	//更新时间 时间范围止（查询用）
	private String addrRegion;//地区名称（查询用）
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getAddrRegion1Id() {
		return addrRegion1Id;
	}
	public void setAddrRegion1Id(String addrRegion1Id) {
		this.addrRegion1Id = addrRegion1Id;
	}
	public String getAddrRegion1Name() {
		return addrRegion1Name;
	}
	public void setAddrRegion1Name(String addrRegion1Name) {
		this.addrRegion1Name = addrRegion1Name;
	}
	public String getAddrRegion2Id() {
		return addrRegion2Id;
	}
	public void setAddrRegion2Id(String addrRegion2Id) {
		this.addrRegion2Id = addrRegion2Id;
	}
	public String getAddrRegion2Name() {
		return addrRegion2Name;
	}
	public void setAddrRegion2Name(String addrRegion2Name) {
		this.addrRegion2Name = addrRegion2Name;
	}
	public String getAddrRegion3Id() {
		return addrRegion3Id;
	}
	public void setAddrRegion3Id(String addrRegion3Id) {
		this.addrRegion3Id = addrRegion3Id;
	}
	public String getAddrRegion3Name() {
		return addrRegion3Name;
	}
	public void setAddrRegion3Name(String addrRegion3Name) {
		this.addrRegion3Name = addrRegion3Name;
	}
	public Double getDepositAmount() {
		return depositAmount;
	}
	public void setDepositAmount(Double depositAmount) {
		this.depositAmount = depositAmount;
	}
	public Integer getIsAvailable() {
		return isAvailable;
	}
	public void setIsAvailable(Integer isAvailable) {
		this.isAvailable = isAvailable;
	}
	public Date getAvailableUpdateTime() {
		return availableUpdateTime;
	}
	public void setAvailableUpdateTime(Date availableUpdateTime) {
		this.availableUpdateTime = availableUpdateTime;
	}
	public Date getAvailableUpdateTimeStart() {
		return availableUpdateTimeStart;
	}
	public void setAvailableUpdateTimeStart(Date availableUpdateTimeStart) {
		this.availableUpdateTimeStart = availableUpdateTimeStart;
	}
	public Date getAvailableUpdateTimeEnd() {
		return availableUpdateTimeEnd;
	}
	public void setAvailableUpdateTimeEnd(Date availableUpdateTimeEnd) {
		this.availableUpdateTimeEnd = availableUpdateTimeEnd;
	}
	public Integer getCensorStatus() {
		return censorStatus;
	}
	public void setCensorStatus(Integer censorStatus) {
		this.censorStatus = censorStatus;
	}
	public String getCensorId() {
		return censorId;
	}
	public void setCensorId(String censorId) {
		this.censorId = censorId;
	}
	public Date getCensorTime() {
		return censorTime;
	}
	public void setCensorTime(Date censorTime) {
		this.censorTime = censorTime;
	}
	public Date getCensorTimeStart() {
		return censorTimeStart;
	}
	public void setCensorTimeStart(Date censorTimeStart) {
		this.censorTimeStart = censorTimeStart;
	}
	public Date getCensorTimeEnd() {
		return censorTimeEnd;
	}
	public void setCensorTimeEnd(Date censorTimeEnd) {
		this.censorTimeEnd = censorTimeEnd;
	}
	public String getCensorMemo() {
		return censorMemo;
	}
	public void setCensorMemo(String censorMemo) {
		this.censorMemo = censorMemo;
	}
	public Integer getOperatorType() {
		return operatorType;
	}
	public void setOperatorType(Integer operatorType) {
		this.operatorType = operatorType;
	}
	public Integer getIsDeleted() {
		return isDeleted;
	}
	public void setIsDeleted(Integer isDeleted) {
		this.isDeleted = isDeleted;
	}
	public String getOperatorId() {
		return operatorId;
	}
	public void setOperatorId(String operatorId) {
		this.operatorId = operatorId;
	}
	public String getOperatorName() {
		return operatorName;
	}
	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
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
	
	public String getAddrRegion() {
		return addrRegion;
	}
	public void setAddrRegion(String addrRegion) {
		this.addrRegion = addrRegion;
	}
	@Override
	public String toString() {
		return "AreaDeposit [id=" + id + ", addrRegion=" + addrRegion + ", addrRegion1Id=" + addrRegion1Id
				+ ", addrRegion1Name=" + addrRegion1Name + ", addrRegion2Id=" + addrRegion2Id + ", addrRegion2Name="
				+ addrRegion2Name + ", addrRegion3Id=" + addrRegion3Id + ", addrRegion3Name=" + addrRegion3Name
				+ ", depositAmount=" + depositAmount + ", isAvailable=" + isAvailable + ", availableUpdateTime="
				+ availableUpdateTime + ", availableUpdateTimeStart=" + availableUpdateTimeStart
				+ ", availableUpdateTimeEnd=" + availableUpdateTimeEnd + ", censorStatus=" + censorStatus
				+ ", censorId=" + censorId + ", censorTime=" + censorTime + ", censorTimeStart=" + censorTimeStart
				+ ", censorTimeEnd=" + censorTimeEnd + ", censorMemo=" + censorMemo + ", operatorType=" + operatorType
				+ ", isDeleted=" + isDeleted + ", operatorId=" + operatorId + ", operatorName=" + operatorName
				+ ", createTime=" + createTime + ", createTimeStart=" + createTimeStart + ", createTimeEnd="
				+ createTimeEnd + ", updateTime=" + updateTime + ", updateTimeStart=" + updateTimeStart
				+ ", updateTimeEnd=" + updateTimeEnd + "]";
	}
}
