package cn.com.shopec.mapi.finace.vo;

import java.util.Date;

import cn.com.shopec.core.common.Entity;

/** 
 * 押金退款表 数据实体类
 */
public class DepositRefundListVo extends Entity<String> {
	
	private static final long serialVersionUID = 1l;
	
	//退款金额
	private Double refundAmount;
	//审核状态（0、未审核，1、已审核，2、待审核，3、审核不通过，4,已退款 默认0）
	private Integer cencorStatus;
	//接口需要的String 类型申请时间
	private String  stringApplyTime;
	//审核备注
	private String cencorMemo;
	//退款申请理由
	private String refundGrounds;
	public Double getRefundAmount() {
		return refundAmount;
	}
	public void setRefundAmount(Double refundAmount) {
		this.refundAmount = refundAmount;
	}
	public Integer getCencorStatus() {
		return cencorStatus;
	}
	public void setCencorStatus(Integer cencorStatus) {
		this.cencorStatus = cencorStatus;
	}
	public String getStringApplyTime() {
		return stringApplyTime;
	}
	public void setStringApplyTime(String stringApplyTime) {
		this.stringApplyTime = stringApplyTime;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@Override
	public String toString() {
		return "DepositRefundListVo [refundAmount=" + refundAmount + ", cencorStatus=" + cencorStatus
				+ ", stringApplyTime=" + stringApplyTime + "]";
	}
	public String getCencorMemo() {
		return cencorMemo;
	}
	public void setCencorMemo(String cencorMemo) {
		this.cencorMemo = cencorMemo;
	}
	public String getRefundGrounds() {
		return refundGrounds;
	}
	public void setRefundGrounds(String refundGrounds) {
		this.refundGrounds = refundGrounds;
	}
	
	
	
}
