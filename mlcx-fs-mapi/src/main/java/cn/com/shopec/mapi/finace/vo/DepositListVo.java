package cn.com.shopec.mapi.finace.vo;

import java.util.Date;
import java.util.List;

import cn.com.shopec.core.common.Entity;

/** 
 * 押金 展示 退款页面
 */
public class DepositListVo extends Entity<String> {
	
	private static final long serialVersionUID = 1l;
	
	/*Auto generated properties start*/
	
	// 剩余的可用保证金合计 sum (remain_amount - FROZEN_AMOUNT)
	private Double availableAmount;

	//退款集合
	private List<DepositRefundListVo> depositRefunds;
	
	
	
	public Double getAvailableAmount() {
		return availableAmount;
	}
	public void setAvailableAmount(Double availableAmount) {
		this.availableAmount = availableAmount;
	}
	public List<DepositRefundListVo> getDepositRefunds() {
		return depositRefunds;
	}
	public void setDepositRefunds(List<DepositRefundListVo> depositRefunds) {
		this.depositRefunds = depositRefunds;
	}
	@Override
	public String toString() {
		return "DepositListVo [availableAmount=" + availableAmount + ", depositRefunds=" + depositRefunds + "]";
	}
	
	
	
}
