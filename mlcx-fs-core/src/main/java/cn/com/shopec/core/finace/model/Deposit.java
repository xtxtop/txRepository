package cn.com.shopec.core.finace.model;

import java.util.Date;
import java.util.List;

import cn.com.shopec.core.common.Entity;

/** 
 * 押金支付    数据实体类
 */
public class Deposit extends Entity<String> {
	
	private static final long serialVersionUID = 1l;
	
	/*Auto generated properties start*/
	//会员编号
	private String memberNo;
	//已缴保证金合计
	private Double depositAmount;
	//已被扣除保证金合计
	private Double deductedAmount;
	//剩余保证金合计
	private Double remainAmount;
	//已冻结押金金额（申请退款后会有冻结，退款完成后会解冻，并将冻结金额加入已退款金额）
	private Double frozenAmount;
	// 剩余的可用保证金合计 sum (remain_amount - FROZEN_AMOUNT)
	private Double availableAmount;
	//保证金是否已经缴纳
	private Integer depositStatus;
	//是否存在退款申请
	private Integer refundStatus;
	// 应该缴纳的保证金总金额
	private Double depositAmountNeed;
	//退款表是否有记录
	private Integer returnAmount;
	//平台配置的保障金
	private  Double  carRentalAmount;
	//账号手机号
	private String phone;
	//押金使用
	private String depositUse;
	//押金提现
	private String cashDeposit;
	//交押金的地区
	private String addrRegion;
	//客户端申请退款理由
	private 	List <RefundGroundsVo> refundGroundsListVo;
	public String getMemberNo() {
		return memberNo;
	}
	public void setMemberNo(String memberNo) {
		this.memberNo = memberNo;
	}
	public Double getDepositAmount() {
		return depositAmount;
	}
	public void setDepositAmount(Double depositAmount) {
		this.depositAmount = depositAmount;
	}
	public Double getDeductedAmount() {
		return deductedAmount;
	}
	public void setDeductedAmount(Double deductedAmount) {
		this.deductedAmount = deductedAmount;
	}
	public Double getRemainAmount() {
		return remainAmount;
	}
	public void setRemainAmount(Double remainAmount) {
		this.remainAmount = remainAmount;
	}
	public Double getFrozenAmount() {
		return frozenAmount;
	}
	public void setFrozenAmount(Double frozenAmount) {
		this.frozenAmount = frozenAmount;
	}
	public Double getAvailableAmount() {
		return availableAmount;
	}
	public void setAvailableAmount(Double availableAmount) {
		this.availableAmount = availableAmount;
	}
	public Integer getDepositStatus() {
		return depositStatus;
	}
	public void setDepositStatus(Integer depositStatus) {
		this.depositStatus = depositStatus;
	}
	public Integer getRefundStatus() {
		return refundStatus;
	}
	public void setRefundStatus(Integer refundStatus) {
		this.refundStatus = refundStatus;
	}
	public Double getDepositAmountNeed() {
		return depositAmountNeed;
	}
	public void setDepositAmountNeed(Double depositAmountNeed) {
		this.depositAmountNeed = depositAmountNeed;
	}
	public Integer getReturnAmount() {
		return returnAmount;
	}
	public void setReturnAmount(Integer returnAmount) {
		this.returnAmount = returnAmount;
	}
	
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public Double getCarRentalAmount() {
		return carRentalAmount;
	}
	public void setCarRentalAmount(Double carRentalAmount) {
		this.carRentalAmount = carRentalAmount;
	}
	public String getDepositUse() {
		return depositUse;
	}
	public void setDepositUse(String depositUse) {
		this.depositUse = depositUse;
	}
	public String getCashDeposit() {
		return cashDeposit;
	}
	public void setCashDeposit(String cashDeposit) {
		this.cashDeposit = cashDeposit;
	}
	public String getAddrRegion() {
		return addrRegion;
	}
	public void setAddrRegion(String addrRegion) {
		this.addrRegion = addrRegion;
	}
	public List <RefundGroundsVo> getRefundGroundsListVo() {
		return refundGroundsListVo;
	}
	public void setRefundGroundsListVo(List <RefundGroundsVo> refundGroundsListVo) {
		this.refundGroundsListVo = refundGroundsListVo;
	}
	
	
}
