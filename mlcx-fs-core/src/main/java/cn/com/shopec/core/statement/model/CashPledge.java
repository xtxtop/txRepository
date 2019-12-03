package cn.com.shopec.core.statement.model;

import java.util.Date;

public class CashPledge {
	//类型，拼装数据时用   微信、支付宝、其他，合计
	private String type;
	
	//押金
	private String cashPerCount;//缴纳人次
	
	private String income;//收入
	
	private String cashAgentFee;//支付手续费
	
	//退款
	private String cashRefundCount;//退款人次
	
	private String refund;//退款
	
	private String refundAgentFee;//退款手续费

	//查询条件开始时间
	private Date startTime;
	//查询条件结束时间
	private Date endTime;
	//支付宝手续费
	private Double zfbAgentFee;
	
	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Double getZfbAgentFee() {
		return zfbAgentFee;
	}

	public void setZfbAgentFee(Double zfbAgentFee) {
		this.zfbAgentFee = zfbAgentFee;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCashPerCount() {
		return cashPerCount;
	}

	public void setCashPerCount(String cashPerCount) {
		this.cashPerCount = cashPerCount;
	}

	public String getIncome() {
		return income;
	}

	public void setIncome(String income) {
		this.income = income;
	}

	public String getCashAgentFee() {
		return cashAgentFee;
	}

	public void setCashAgentFee(String cashAgentFee) {
		this.cashAgentFee = cashAgentFee;
	}

	public String getCashRefundCount() {
		return cashRefundCount;
	}

	public void setCashRefundCount(String cashRefundCount) {
		this.cashRefundCount = cashRefundCount;
	}

	public String getRefund() {
		return refund;
	}

	public void setRefund(String refund) {
		this.refund = refund;
	}

	public String getRefundAgentFee() {
		return refundAgentFee;
	}

	public void setRefundAgentFee(String refundAgentFee) {
		this.refundAgentFee = refundAgentFee;
	}

	
	
	
	

}
