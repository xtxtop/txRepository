package cn.com.shopec.core.ml.model;

import java.util.Date;

import cn.com.shopec.core.common.Entity;

/**
 * CAccountRecord 数据实体类
 */
public class CAccountRecord extends Entity<String> {

	private static final long serialVersionUID = 1l;

	/* Auto generated properties start */

	// 主键
	private String id;
	// 会员编号
	private String memberNo;
	private String menberName;
	private String mobilePhone;
	// 交易类型(1.充电 2.停车)
	private Integer dealType;
	// 类型（1充值 2赠送 3抵扣 4返还）
	private Integer type;
	// 交易金额
	private Double trnasactionAmount;
	// 原始金额
	private Double originalAmount;
	// 余下金额
	private Double balance;
	// 订单编号
	private String orderNo;
	// 支付流水编号
	private String payflowNo;
	// 创建时间
	private Date createTime;
	// 创建时间 时间范围起（查询用）
	private Date createTimeStart;
	// 创建时间 时间范围止（查询用）
	private Date createTimeEnd;
	// 更新时间
	private Date updateTime;
	// 更新时间 时间范围起（查询用）
	private Date updateTimeStart;
	// 更新时间 时间范围止（查询用）
	private Date updateTimeEnd;
	// 操作人类型（0、系统自动操作，1、平台人员操作，2、商家人员操作，3、会员操作）
	private Integer operatorType;
	// 操作人ID
	private String operatorId;
	// 支付类型
	private Integer payType;
	// 支付状态
	private Integer payStatus;

	/* Auto generated properties end */

	/* Customized properties start */

	/* Customized properties end */

	/* Auto generated methods start */

	public Integer getPayType() {
		return payType;
	}

	public void setPayType(Integer payType) {
		this.payType = payType;
	}

	@Override
	public String getPK() {
		return id;
	}

	public String getMenberName() {
		return menberName;
	}

	public void setMenberName(String menberName) {
		this.menberName = menberName;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMemberNo() {
		return memberNo;
	}

	public void setMemberNo(String memberNo) {
		this.memberNo = memberNo;
	}

	public Integer getDealType() {
		return dealType;
	}

	public void setDealType(Integer dealType) {
		this.dealType = dealType;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Double getTrnasactionAmount() {
		return trnasactionAmount;
	}

	public void setTrnasactionAmount(Double trnasactionAmount) {
		this.trnasactionAmount = trnasactionAmount;
	}

	public Double getOriginalAmount() {
		return originalAmount;
	}

	public void setOriginalAmount(Double originalAmount) {
		this.originalAmount = originalAmount;
	}

	public Double getBalance() {
		return balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getPayflowNo() {
		return payflowNo;
	}

	public void setPayflowNo(String payflowNo) {
		this.payflowNo = payflowNo;
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

	public Integer getOperatorType() {
		return operatorType;
	}

	public void setOperatorType(Integer operatorType) {
		this.operatorType = operatorType;
	}

	public String getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(String operatorId) {
		this.operatorId = operatorId;
	}

	/* Auto generated methods end */

	/* Customized methods start */

	/* Customized methods end */

	@Override
	public String toString() {
		return "CAccountRecord [id=" + id + ", memberNo=" + memberNo + ", menberName=" + menberName + ", mobilePhone="
				+ mobilePhone + ", dealType=" + dealType + ", type=" + type + ", trnasactionAmount=" + trnasactionAmount
				+ ", originalAmount=" + originalAmount + ", balance=" + balance + ", orderNo=" + orderNo
				+ ", payflowNo=" + payflowNo + ", createTime=" + createTime + ", createTimeStart=" + createTimeStart
				+ ", createTimeEnd=" + createTimeEnd + ", updateTime=" + updateTime + ", updateTimeStart="
				+ updateTimeStart + ", updateTimeEnd=" + updateTimeEnd + ", operatorType=" + operatorType
				+ ", operatorId=" + operatorId + ", payType=" + payType + "]";
	}

	public Integer getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(Integer payStatus) {
		this.payStatus = payStatus;
	}
}
