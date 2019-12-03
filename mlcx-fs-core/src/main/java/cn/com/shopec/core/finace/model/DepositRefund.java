package cn.com.shopec.core.finace.model;

import java.util.Date;

import cn.com.shopec.core.common.Entity;

/**
 * 押金退款表 数据实体类
 */
public class DepositRefund extends Entity<String> {

	private static final long serialVersionUID = 1l;

	/* Auto generated properties start */

	// 退款编号
	private String refundNo;
	// 押金支付编号
	private String depositOrderNo;
	// 退款流水号
	private String refundFlowNo;
	// 退款方法（1、支付宝，2,微信 3、微信公众，4、线下退款）
	private Integer refundMethod;
	// 退款时间
	private Date refundTime;
	// 退款时间 时间范围起（查询用）
	private Date refundTimeStart;
	// 退款时间 时间范围止（查询用）
	private Date refundTimeEnd;
	// 退款金额
	private Double refundAmount;
	// 客户编号
	private String memberNo;
	// 客户名称
	private String memberName;
	// 客户手机号
	private String mobilePhone;
	// 退款状态（0、未退款，1、已退款，默认0）
	private Integer refundStatus;
	// 退款操作人id
	private String refundOperatorId;
	// 申请时间
	private Date applyTime;
	// 申请时间 时间范围起（查询用）
	private Date applyTimeStart;
	// 申请时间 时间范围止（查询用）
	private Date applyTimeEnd;
	// 审核人id
	private String cencorId;
	// 审核状态（0、未审核，1、已审核，2、待审核，3、审核不通过，默认0）
	private Integer cencorStatus;
	// 审核通过确认项（多项之间用半角逗号间隔）
	private String cencorConfirmItem;
	// 审核备注
	private String cencorMemo;
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
	// 操作人类型
	private Integer operatorType;
	// 操作人id
	private String operatorId;
	// 线下退款备注
	private String refundMemo;

	// 退款失败信息
	private String refundFailInfo;
	//客户端申请退款理由
	private String refundGrounds;
	//客户端申请退款备注
	private String refundGroundsMemo;
	/* Auto generated properties end */

	/* Customized properties start */

	/* Customized properties end */

	/* Auto generated methods start */

	@Override
	public String getPK() {
		return refundNo;
	}

	public String getRefundNo() {
		return refundNo;
	}

	public void setRefundNo(String refundNo) {
		this.refundNo = refundNo;
	}

	public String getDepositOrderNo() {
		return depositOrderNo;
	}

	public void setDepositOrderNo(String depositOrderNo) {
		this.depositOrderNo = depositOrderNo;
	}

	public String getRefundFlowNo() {
		return refundFlowNo;
	}

	public void setRefundFlowNo(String refundFlowNo) {
		this.refundFlowNo = refundFlowNo;
	}

	public Integer getRefundMethod() {
		return refundMethod;
	}

	public void setRefundMethod(Integer refundMethod) {
		this.refundMethod = refundMethod;
	}

	public Date getRefundTime() {
		return refundTime;
	}

	public void setRefundTime(Date refundTime) {
		this.refundTime = refundTime;
	}

	public Date getRefundTimeStart() {
		return refundTimeStart;
	}

	public void setRefundTimeStart(Date refundTimeStart) {
		this.refundTimeStart = refundTimeStart;
	}

	public Date getRefundTimeEnd() {
		return refundTimeEnd;
	}

	public void setRefundTimeEnd(Date refundTimeEnd) {
		this.refundTimeEnd = refundTimeEnd;
	}

	public Double getRefundAmount() {
		return refundAmount;
	}

	public void setRefundAmount(Double refundAmount) {
		this.refundAmount = refundAmount;
	}

	public String getMemberNo() {
		return memberNo;
	}

	public void setMemberNo(String memberNo) {
		this.memberNo = memberNo;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public Integer getRefundStatus() {
		return refundStatus;
	}

	public void setRefundStatus(Integer refundStatus) {
		this.refundStatus = refundStatus;
	}

	public String getRefundOperatorId() {
		return refundOperatorId;
	}

	public void setRefundOperatorId(String refundOperatorId) {
		this.refundOperatorId = refundOperatorId;
	}

	public Date getApplyTime() {
		return applyTime;
	}

	public void setApplyTime(Date applyTime) {
		this.applyTime = applyTime;
	}

	public Date getApplyTimeStart() {
		return applyTimeStart;
	}

	public void setApplyTimeStart(Date applyTimeStart) {
		this.applyTimeStart = applyTimeStart;
	}

	public Date getApplyTimeEnd() {
		return applyTimeEnd;
	}

	public void setApplyTimeEnd(Date applyTimeEnd) {
		this.applyTimeEnd = applyTimeEnd;
	}

	public String getCencorId() {
		return cencorId;
	}

	public void setCencorId(String cencorId) {
		this.cencorId = cencorId;
	}

	public Integer getCencorStatus() {
		return cencorStatus;
	}

	public void setCencorStatus(Integer cencorStatus) {
		this.cencorStatus = cencorStatus;
	}

	public String getCencorConfirmItem() {
		return cencorConfirmItem;
	}

	public void setCencorConfirmItem(String cencorConfirmItem) {
		this.cencorConfirmItem = cencorConfirmItem;
	}

	public String getCencorMemo() {
		return cencorMemo;
	}

	public void setCencorMemo(String cencorMemo) {
		this.cencorMemo = cencorMemo;
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

	public String getRefundMemo() {
		return refundMemo;
	}

	public void setRefundMemo(String refundMemo) {
		this.refundMemo = refundMemo;
	}

	@Override
	public String toString() {
		return "DepositRefund [" + "refundNo = " + refundNo + ", depositOrderNo = " + depositOrderNo
				+ ", refundFlowNo = " + refundFlowNo + ", refundMethod = " + refundMethod + ", refundTime = "
				+ refundTime + ", refundTimeStart = " + refundTimeStart + ", refundTimeEnd = " + refundTimeEnd
				+ ", refundAmount = " + refundAmount + ", memberNo = " + memberNo + ", memberName = " + memberName
				+ ", mobilePhone = " + mobilePhone + ", refundStatus = " + refundStatus + ", refundOperatorId = "
				+ refundOperatorId + ", applyTime = " + applyTime + ", applyTimeStart = " + applyTimeStart
				+ ", applyTimeEnd = " + applyTimeEnd + ", cencorId = " + cencorId + ", cencorStatus = " + cencorStatus
				+ ", cencorConfirmItem = " + cencorConfirmItem + ", cencorMemo = " + cencorMemo + ", createTime = "
				+ createTime + ", createTimeStart = " + createTimeStart + ", createTimeEnd = " + createTimeEnd
				+ ", updateTime = " + updateTime + ", updateTimeStart = " + updateTimeStart + ", updateTimeEnd = "
				+ updateTimeEnd + ", operatorType = " + operatorType + ", operatorId = " + operatorId + "]";
	}

	public String getRefundFailInfo() {
		return refundFailInfo;
	}

	public void setRefundFailInfo(String refundFailInfo) {
		this.refundFailInfo = refundFailInfo;
	}

	public String getRefundGrounds() {
		return refundGrounds;
	}

	public void setRefundGrounds(String refundGrounds) {
		this.refundGrounds = refundGrounds;
	}

	public String getRefundGroundsMemo() {
		return refundGroundsMemo;
	}

	public void setRefundGroundsMemo(String refundGroundsMemo) {
		this.refundGroundsMemo = refundGroundsMemo;
	}

}
