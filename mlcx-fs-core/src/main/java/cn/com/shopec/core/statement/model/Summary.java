package cn.com.shopec.core.statement.model;

import java.util.Date;

import cn.com.shopec.core.common.Entity;

/**
 * 汇总表 数据实体类
 */
public class Summary extends Entity<String> {

	private static final long serialVersionUID = 1l;

	/* Auto generated properties start */

	// 订单数
	private Integer orderNum;
	// 时长（分钟）
	private Integer orderDuration;
	// 订单金额（元）
	private Double orderAmount;
	// 实收金额
	private Double alreadyAmount;
	// 实收金额对应时长
	private Integer alreadyDuration;
	// 套餐抵扣订单数
	private Integer packOrderNum;
	// 折扣金额
	private Double discountAmount;
	// 套餐抵扣金额
	private Double packAmount;
	// 套餐抵扣时长
	private Integer packDuration;
	// 冲账订单数
	private Integer strikeBalanceOrderNum;
	// 冲账金额
	private Double strikeBalanceAmount;
	// 微信订单数
	private Integer wxOrderNum;
	// 微信订单金额
	private Double wxAmount;
	// 微信实收金额
	private Double wxRealAmount;
	// 微信手续费
	private Double wxCharge;
	// 支付宝订单数
	private Integer alipayOrderNum;
	// 支付宝订单金额
	private Double alilpayAmount;
	// 支付宝实收金额
	private Double alilpayRealAmount;
	// 支付宝手续费
	private Double alipayCharge;
	//汇总开始时间
	private Date startTime;
	//汇总结束时间
	private Date endTime;
	public Integer getOrderNum() {
		return orderNum;
	}
	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}
	public Integer getOrderDuration() {
		return orderDuration;
	}
	public void setOrderDuration(Integer orderDuration) {
		this.orderDuration = orderDuration;
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
	public Integer getAlreadyDuration() {
		return alreadyDuration;
	}
	public void setAlreadyDuration(Integer alreadyDuration) {
		this.alreadyDuration = alreadyDuration;
	}
	public Integer getPackOrderNum() {
		return packOrderNum;
	}
	public void setPackOrderNum(Integer packOrderNum) {
		this.packOrderNum = packOrderNum;
	}
	public Double getPackAmount() {
		return packAmount;
	}
	public void setPackAmount(Double packAmount) {
		this.packAmount = packAmount;
	}
	public Integer getPackDuration() {
		return packDuration;
	}
	public void setPackDuration(Integer packDuration) {
		this.packDuration = packDuration;
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
	public Integer getWxOrderNum() {
		return wxOrderNum;
	}
	public void setWxOrderNum(Integer wxOrderNum) {
		this.wxOrderNum = wxOrderNum;
	}
	public Double getWxAmount() {
		return wxAmount;
	}
	public void setWxAmount(Double wxAmount) {
		this.wxAmount = wxAmount;
	}
	public Double getWxCharge() {
		return wxCharge;
	}
	public void setWxCharge(Double wxCharge) {
		this.wxCharge = wxCharge;
	}
	public Integer getAlipayOrderNum() {
		return alipayOrderNum;
	}
	public void setAlipayOrderNum(Integer alipayOrderNum) {
		this.alipayOrderNum = alipayOrderNum;
	}
	public Double getAlilpayAmount() {
		return alilpayAmount;
	}
	public void setAlilpayAmount(Double alilpayAmount) {
		this.alilpayAmount = alilpayAmount;
	}
	public Double getAlipayCharge() {
		return alipayCharge;
	}
	public void setAlipayCharge(Double alipayCharge) {
		this.alipayCharge = alipayCharge;
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
	public Double getWxRealAmount() {
		return wxRealAmount;
	}
	public void setWxRealAmount(Double wxRealAmount) {
		this.wxRealAmount = wxRealAmount;
	}
	public Double getAlilpayRealAmount() {
		return alilpayRealAmount;
	}
	public void setAlilpayRealAmount(Double alilpayRealAmount) {
		this.alilpayRealAmount = alilpayRealAmount;
	}
	public Double getDiscountAmount() {
		return discountAmount;
	}
	public void setDiscountAmount(Double discountAmount) {
		this.discountAmount = discountAmount;
	}
	
}
