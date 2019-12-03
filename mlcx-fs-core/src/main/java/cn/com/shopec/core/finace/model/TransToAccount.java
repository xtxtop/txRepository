package cn.com.shopec.core.finace.model;

import java.util.Date;

import cn.com.shopec.core.common.Entity;

/**
 * 押金--转账记录表 数据实体类
 */
public class TransToAccount extends Entity<String> {

	private static final long serialVersionUID = 1l;

	//转账单号
	private String transNo;
	//支付平台的转账单账单ID
	private String thirdTransOrderId;
	//押金订单订单号
	private String depositOrderNo;
	//押金退款申请单单号
	private String refundNo;
	//支付平台状态（1、支付宝，2、微信 ）
	private Integer transType;
	//转账金额
	private Double transAmount;
	//转账时间
	private Date transTime;
	//转账结果（0、失败，1、成功 ）
	private Integer transResult;
	//转账请求返回信息记录
	private String transInfo;
	//操作人ID
	private String operatorId;
	
	public String getTransNo() {
		return transNo;
	}
	public void setTransNo(String transNo) {
		this.transNo = transNo;
	}
	public String getThirdTransOrderId() {
		return thirdTransOrderId;
	}
	public void setThirdTransOrderId(String thirdTransOrderId) {
		this.thirdTransOrderId = thirdTransOrderId;
	}
	public String getDepositOrderNo() {
		return depositOrderNo;
	}
	public void setDepositOrderNo(String depositOrderNo) {
		this.depositOrderNo = depositOrderNo;
	}
	public String getRefundNo() {
		return refundNo;
	}
	public void setRefundNo(String refundNo) {
		this.refundNo = refundNo;
	}
	public Integer getTransType() {
		return transType;
	}
	public void setTransType(Integer transType) {
		this.transType = transType;
	}
	public Double getTransAmount() {
		return transAmount;
	}
	public void setTransAmount(Double transAmount) {
		this.transAmount = transAmount;
	}
	public Date getTransTime() {
		return transTime;
	}
	public void setTransTime(Date transTime) {
		this.transTime = transTime;
	}
	public Integer getTransResult() {
		return transResult;
	}
	public void setTransResult(Integer transResult) {
		this.transResult = transResult;
	}
	public String getTransInfo() {
		return transInfo;
	}
	public void setTransInfo(String transInfo) {
		this.transInfo = transInfo;
	}
	public String getOperatorId() {
		return operatorId;
	}
	public void setOperatorId(String operatorId) {
		this.operatorId = operatorId;
	}
	@Override
	public String toString() {
		return "TransToAccount [transNo=" + transNo + ", thirdTransOrderId=" + thirdTransOrderId + ", depositOrderNo="
				+ depositOrderNo + ", refundNo=" + refundNo + ", transType=" + transType + ", transAmount="
				+ transAmount + ", transTime=" + transTime + ", transResult=" + transResult + ", transInfo=" + transInfo
				+ ", operatorId=" + operatorId + "]";
	}
	
	
}
