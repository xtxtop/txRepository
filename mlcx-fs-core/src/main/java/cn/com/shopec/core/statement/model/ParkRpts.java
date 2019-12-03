package cn.com.shopec.core.statement.model;

import java.util.Date;

import cn.com.shopec.core.common.Entity;

/**
 * 场站汇总表 数据实体类
 */
public class ParkRpts extends Entity<String> {

	private static final long serialVersionUID = 1L;

	/* Auto generated properties start */
	//场站名称
	private String parkName;
	// 约取车地点（场站编号）
	private String startParkNo;
	// 订单金额（元）
	private Double orderAmount;
	// 实收金额
	private Double alreadyAmount;
	// 订单数
	private Integer orderNum;
	// 冲账订单数
	private Integer strikeBalanceOrderNum;
	// 冲账金额
	private Double strikeBalanceAmount;
	//余额抵扣单数
	private Integer balanceNum;
	//余额抵扣总金额
	private Double balanceDiscountAmount;
	//券抵扣单数
	private Integer discountAmountNum;
	//券抵扣总金额
	private Double discountAmount;
	//场站还车附加费（元）
	private Double serviceFeeBack;
	//场站取车附加费（元）
	private Double serviceFeeGet;
	//汇总开始时间
	private Date startTime;
	//汇总结束时间
	private Date endTime;
	public String getParkName() {
		return parkName;
	}
	public void setParkName(String parkName) {
		this.parkName = parkName;
	}
	public String getStartParkNo() {
		return startParkNo;
	}
	public void setStartParkNo(String startParkNo) {
		this.startParkNo = startParkNo;
	}
	public Double getOrderAmount() {
		return orderAmount;
	}
	public void setOrderAmount(Double orderAmount) {
		this.orderAmount = orderAmount;
	}
	public Double getAlreadyAmount() {
		return alreadyAmount;
	}
	public void setAlreadyAmount(Double alreadyAmount) {
		this.alreadyAmount = alreadyAmount;
	}
	public Integer getOrderNum() {
		return orderNum;
	}
	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
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
	public Double getServiceFeeBack() {
		return serviceFeeBack;
	}
	public void setServiceFeeBack(Double serviceFeeBack) {
		this.serviceFeeBack = serviceFeeBack;
	}
	public Double getServiceFeeGet() {
		return serviceFeeGet;
	}
	public void setServiceFeeGet(Double serviceFeeGet) {
		this.serviceFeeGet = serviceFeeGet;
	}
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
	
}