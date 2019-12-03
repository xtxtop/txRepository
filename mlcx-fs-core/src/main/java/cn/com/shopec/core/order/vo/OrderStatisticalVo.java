package cn.com.shopec.core.order.vo;

import cn.com.shopec.core.common.Entity;

/**
 * 订单统计
 * @author LiHuan
 * Date 2018年4月4日 下午2:14:51
 */
public class OrderStatisticalVo extends Entity<String>{
	
	private static final long serialVersionUID = 1l;
	
	private String orderNo;//订单编号
	
	private Double orderAmount;//订单总金额
	
	private Double payableAmount;//已实收金额==实际支付金额
	
	private Integer strikeBalanceOrderNum;//冲账单数
	
	private Double strikeBalanceAmount;//冲账总金额
	
	private Integer noPayOrderNum;//未支付单数
	
	private Double noPayAmount;//未支付总金额
	
	private Integer balanceNum;//余额抵扣单数
	
	private Double balanceDiscountAmount;//余额抵扣总金额
	
	private Integer discountAmountNum;//优惠券抵扣单数
	
	private Double discountAmount;//优惠券抵扣总金额
	//订单查询时间
	private String endTime;
	private String startTime;

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public Double getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(Double orderAmount) {
		this.orderAmount = orderAmount;
	}

	public Double getPayableAmount() {
		return payableAmount;
	}

	public void setPayableAmount(Double payableAmount) {
		this.payableAmount = payableAmount;
	}

	public Integer getStrikeBalanceOrderNum() {
		return strikeBalanceOrderNum;
	}

	public void setStrikeBalanceOrderNum(Integer strikeBalanceOrderNum) {
		this.strikeBalanceOrderNum = strikeBalanceOrderNum;
	}

	public Double getStrikeBalanceAmount() {
		return strikeBalanceAmount;
	}

	public void setStrikeBalanceAmount(Double strikeBalanceAmount) {
		this.strikeBalanceAmount = strikeBalanceAmount;
	}

	public Integer getNoPayOrderNum() {
		return noPayOrderNum;
	}

	public void setNoPayOrderNum(Integer noPayOrderNum) {
		this.noPayOrderNum = noPayOrderNum;
	}

	public Double getNoPayAmount() {
		return noPayAmount;
	}

	public void setNoPayAmount(Double noPayAmount) {
		this.noPayAmount = noPayAmount;
	}

	public Integer getBalanceNum() {
		return balanceNum;
	}

	public void setBalanceNum(Integer balanceNum) {
		this.balanceNum = balanceNum;
	}

	public Double getBalanceDiscountAmount() {
		return balanceDiscountAmount;
	}

	public void setBalanceDiscountAmount(Double balanceDiscountAmount) {
		this.balanceDiscountAmount = balanceDiscountAmount;
	}

	public Integer getDiscountAmountNum() {
		return discountAmountNum;
	}

	public void setDiscountAmountNum(Integer discountAmountNum) {
		this.discountAmountNum = discountAmountNum;
	}

	public Double getDiscountAmount() {
		return discountAmount;
	}

	public void setDiscountAmount(Double discountAmount) {
		this.discountAmount = discountAmount;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	
}