package cn.com.shopec.core.statement.model;

import java.util.Date;

import cn.com.shopec.core.common.Entity;

/**
 * 资金 数据实体类
 */
public class Money extends Entity<String> {

	private static final long serialVersionUID = 1l;

	/* Auto generated properties start */

	// 预付款微信收入
	private Double depositWxAmount;
	// 预付款微信退款
	private Double depositWxRefund;
	// 预付款微信平台手续费
	private Double depositWxCharge;
	// 预付款支付宝收入
	private Double depositAlipayAmount;
	// 预付款支付宝退款
	private Double depositAlipayRefund;
	// 预付款支付宝手续费
	private Double depositAlipayCharge;
	// 订单微信收入
	private Double orderWxAmount;
	// 订单微信平台手续费
	private Double orderWxCharge;
	// 订单支付宝收入
	private Double orderAlipayAmount;
	// 订单支付宝手续费
	private Double orderAlipayCharge;
	// 套餐微信收入
	private Double priceWxAmount;
	// 套餐微信平台手续费
	private Double priceWxCharge;
	// 套餐支付宝收入
	private Double priceAlipayAmount;
	// 套餐支付宝手续费
	private Double priceAlipayCharge;
	// 手续费退还
	private Double chargeRefund;
	// 微信合计
	private Double wxAmount;
	// 支付宝合计
	private Double alipayAmount;
	// 开始时间
	private Date startTime;
	// 结束时间
	private Date endTime;
	// 实名认证 返还金额
	private Double certification;
	// 导入的 全部 支付宝 押金收入金额
	private Double depositAlipayAmountWhole;
	// 导入的全部 支付宝 押金 手续费
	private Double depositAlipayChargeWhole;
	// 导入的全部 支付宝 押金退款
	private Double depositAlipayRefundWhole;
	// 导入的全部 支付宝 订单收入
	private Double orderAlipayAmountWhole;
	// 导入 全部 支付宝 订单 手续费
	private Double orderAlipayChargeWhole;
	// 导入 全部 支付宝 套餐 收入
	private Double priceAlipayAmountWhole;
	// 导入 全部 支付宝 套餐 手续费
	private Double priceAlipayChargeWhole;

	// 测试 支付宝 押金收入
	private Double depositAlipayAmountTest;
	// 测试 支付宝 押金退款
	private Double depositAlipayRefundTest;
	// 测试 支付宝 押金手续费
	private Double depositAlipayChargeTest;
	// 测试 支付宝 订单 收入
	private Double orderAlipayAmountTest;
	// 测试 支付宝 订单 手续费
	private Double orderAlipayChargeTest;
	// 测试 支付宝 套餐 收入
	private Double priceAlipayAmountTest;
	// 测试 支付宝 套餐 手续费
	private Double priceAlipayChargeTest;
	// 测试 支付宝合计
	private Double asw;

	// 支付宝全部合计
	private Double awhole;

	// 导入的 全部 微信 押金收入金额
	private Double depositWxAmountWhole;
	// 导入的全部 微信 押金 手续费
	private Double depositWxChargeWhole;
	// 导入的全部 微信 押金退款
	private Double depositWxRefundWhole;
	// 导入的全部 微信 押金退款 手续费返还
	private Double chargeRefundWhole;
	// 导入的全部 微信 订单收入
	private Double orderWxAmountWhole;
	// 导入 全部 微信 订单 手续费
	private Double orderWxChargeWhole;
	// 导入 全部 微信 套餐 收入
	private Double priceWxAmountWhole;
	// 导入 全部 微信 套餐 手续费
	private Double priceWxChargeWhole;

	// 微信全部合计
	private Double awholeWx;

	// 测试 微信 押金收入
	private Double depositWxAmountTest;
	// 测试 微信 押金退款
	private Double depositWxRefundTest;
	// 测试 微信 押金手续费
	private Double depositWxChargeTest;
	// 测试 微信 订单 收入
	private Double orderWxAmountTest;
	// 测试 微信 订单 手续费
	private Double orderWxChargeTest;
	// 测试 微信 套餐 收入
	private Double priceWxAmountTest;
	// 测试 微信 套餐 手续费
	private Double priceWxChargeTest;
	private Double chargeRefundTest;
	// 测试 微信合计
	private Double aswWx;

	// 订单收入合计
	private Double orderIncomeTotal;

	public Double getDepositWxAmount() {
		return depositWxAmount;
	}

	public void setDepositWxAmount(Double depositWxAmount) {
		this.depositWxAmount = depositWxAmount;
	}

	public Double getDepositWxRefund() {
		return depositWxRefund;
	}

	public void setDepositWxRefund(Double depositWxRefund) {
		this.depositWxRefund = depositWxRefund;
	}

	public Double getDepositWxCharge() {
		return depositWxCharge;
	}

	public void setDepositWxCharge(Double depositWxCharge) {
		this.depositWxCharge = depositWxCharge;
	}

	public Double getDepositAlipayAmount() {
		return depositAlipayAmount;
	}

	public void setDepositAlipayAmount(Double depositAlipayAmount) {
		this.depositAlipayAmount = depositAlipayAmount;
	}

	public Double getDepositAlipayRefund() {
		return depositAlipayRefund;
	}

	public void setDepositAlipayRefund(Double depositAlipayRefund) {
		this.depositAlipayRefund = depositAlipayRefund;
	}

	public Double getDepositAlipayCharge() {
		return depositAlipayCharge;
	}

	public void setDepositAlipayCharge(Double depositAlipayCharge) {
		this.depositAlipayCharge = depositAlipayCharge;
	}

	public Double getOrderWxAmount() {
		return orderWxAmount;
	}

	public void setOrderWxAmount(Double orderWxAmount) {
		this.orderWxAmount = orderWxAmount;
	}

	public Double getOrderWxCharge() {
		return orderWxCharge;
	}

	public void setOrderWxCharge(Double orderWxCharge) {
		this.orderWxCharge = orderWxCharge;
	}

	public Double getOrderAlipayAmount() {
		return orderAlipayAmount;
	}

	public void setOrderAlipayAmount(Double orderAlipayAmount) {
		this.orderAlipayAmount = orderAlipayAmount;
	}

	public Double getOrderAlipayCharge() {
		return orderAlipayCharge;
	}

	public void setOrderAlipayCharge(Double orderAlipayCharge) {
		this.orderAlipayCharge = orderAlipayCharge;
	}

	public Double getPriceWxAmount() {
		return priceWxAmount;
	}

	public void setPriceWxAmount(Double priceWxAmount) {
		this.priceWxAmount = priceWxAmount;
	}

	public Double getPriceWxCharge() {
		return priceWxCharge;
	}

	public void setPriceWxCharge(Double priceWxCharge) {
		this.priceWxCharge = priceWxCharge;
	}

	public Double getPriceAlipayAmount() {
		return priceAlipayAmount;
	}

	public void setPriceAlipayAmount(Double priceAlipayAmount) {
		this.priceAlipayAmount = priceAlipayAmount;
	}

	public Double getPriceAlipayCharge() {
		return priceAlipayCharge;
	}

	public void setPriceAlipayCharge(Double priceAlipayCharge) {
		this.priceAlipayCharge = priceAlipayCharge;
	}

	public Double getChargeRefund() {
		return chargeRefund;
	}

	public void setChargeRefund(Double chargeRefund) {
		this.chargeRefund = chargeRefund;
	}

	public Double getWxAmount() {
		return wxAmount;
	}

	public void setWxAmount(Double wxAmount) {
		this.wxAmount = wxAmount;
	}

	public Double getAlipayAmount() {
		return alipayAmount;
	}

	public void setAlipayAmount(Double alipayAmount) {
		this.alipayAmount = alipayAmount;
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

	@Override
	public String toString() {
		return "Money [depositWxAmount=" + depositWxAmount + ", depositWxRefund=" + depositWxRefund
				+ ", depositWxCharge=" + depositWxCharge + ", depositAlipayAmount=" + depositAlipayAmount
				+ ", depositAlipayRefund=" + depositAlipayRefund + ", depositAlipayCharge=" + depositAlipayCharge
				+ ", orderWxAmount=" + orderWxAmount + ", orderWxCharge=" + orderWxCharge + ", orderAlipayAmount="
				+ orderAlipayAmount + ", orderAlipayCharge=" + orderAlipayCharge + ", priceWxAmount=" + priceWxAmount
				+ ", priceWxCharge=" + priceWxCharge + ", priceAlipayAmount=" + priceAlipayAmount
				+ ", priceAlipayCharge=" + priceAlipayCharge + ", chargeRefund=" + chargeRefund + ", wxAmount="
				+ wxAmount + ", alipayAmount=" + alipayAmount + ", startTime=" + startTime + ", endTime=" + endTime
				+ "]";
	}

	public Double getCertification() {
		return certification;
	}

	public void setCertification(Double certification) {
		this.certification = certification;
	}

	public Double getDepositAlipayAmountWhole() {
		return depositAlipayAmountWhole;
	}

	public void setDepositAlipayAmountWhole(Double depositAlipayAmountWhole) {
		this.depositAlipayAmountWhole = depositAlipayAmountWhole;
	}

	public Double getDepositAlipayChargeWhole() {
		return depositAlipayChargeWhole;
	}

	public void setDepositAlipayChargeWhole(Double depositAlipayChargeWhole) {
		this.depositAlipayChargeWhole = depositAlipayChargeWhole;
	}

	public Double getDepositAlipayRefundWhole() {
		return depositAlipayRefundWhole;
	}

	public void setDepositAlipayRefundWhole(Double depositAlipayRefundWhole) {
		this.depositAlipayRefundWhole = depositAlipayRefundWhole;
	}

	public Double getOrderAlipayAmountWhole() {
		return orderAlipayAmountWhole;
	}

	public void setOrderAlipayAmountWhole(Double orderAlipayAmountWhole) {
		this.orderAlipayAmountWhole = orderAlipayAmountWhole;
	}

	public Double getOrderAlipayChargeWhole() {
		return orderAlipayChargeWhole;
	}

	public void setOrderAlipayChargeWhole(Double orderAlipayChargeWhole) {
		this.orderAlipayChargeWhole = orderAlipayChargeWhole;
	}

	public Double getPriceAlipayAmountWhole() {
		return priceAlipayAmountWhole;
	}

	public void setPriceAlipayAmountWhole(Double priceAlipayAmountWhole) {
		this.priceAlipayAmountWhole = priceAlipayAmountWhole;
	}

	public Double getPriceAlipayChargeWhole() {
		return priceAlipayChargeWhole;
	}

	public void setPriceAlipayChargeWhole(Double priceAlipayChargeWhole) {
		this.priceAlipayChargeWhole = priceAlipayChargeWhole;
	}

	public Double getDepositAlipayAmountTest() {
		return depositAlipayAmountTest;
	}

	public void setDepositAlipayAmountTest(Double depositAlipayAmountTest) {
		this.depositAlipayAmountTest = depositAlipayAmountTest;
	}

	public Double getDepositAlipayRefundTest() {
		return depositAlipayRefundTest;
	}

	public void setDepositAlipayRefundTest(Double depositAlipayRefundTest) {
		this.depositAlipayRefundTest = depositAlipayRefundTest;
	}

	public Double getOrderAlipayAmountTest() {
		return orderAlipayAmountTest;
	}

	public void setOrderAlipayAmountTest(Double orderAlipayAmountTest) {
		this.orderAlipayAmountTest = orderAlipayAmountTest;
	}

	public Double getDepositAlipayChargeTest() {
		return depositAlipayChargeTest;
	}

	public void setDepositAlipayChargeTest(Double depositAlipayChargeTest) {
		this.depositAlipayChargeTest = depositAlipayChargeTest;
	}

	public Double getOrderAlipayChargeTest() {
		return orderAlipayChargeTest;
	}

	public void setOrderAlipayChargeTest(Double orderAlipayChargeTest) {
		this.orderAlipayChargeTest = orderAlipayChargeTest;
	}

	public Double getPriceAlipayAmountTest() {
		return priceAlipayAmountTest;
	}

	public void setPriceAlipayAmountTest(Double priceAlipayAmountTest) {
		this.priceAlipayAmountTest = priceAlipayAmountTest;
	}

	public Double getPriceAlipayChargeTest() {
		return priceAlipayChargeTest;
	}

	public void setPriceAlipayChargeTest(Double priceAlipayChargeTest) {
		this.priceAlipayChargeTest = priceAlipayChargeTest;
	}

	public Double getAwhole() {
		return awhole;
	}

	public void setAwhole(Double awhole) {
		this.awhole = awhole;
	}

	public Double getAsw() {
		return asw;
	}

	public void setAsw(Double asw) {
		this.asw = asw;
	}

	public Double getDepositWxAmountWhole() {
		return depositWxAmountWhole;
	}

	public void setDepositWxAmountWhole(Double depositWxAmountWhole) {
		this.depositWxAmountWhole = depositWxAmountWhole;
	}

	public Double getDepositWxChargeWhole() {
		return depositWxChargeWhole;
	}

	public void setDepositWxChargeWhole(Double depositWxChargeWhole) {
		this.depositWxChargeWhole = depositWxChargeWhole;
	}

	public Double getDepositWxRefundWhole() {
		return depositWxRefundWhole;
	}

	public void setDepositWxRefundWhole(Double depositWxRefundWhole) {
		this.depositWxRefundWhole = depositWxRefundWhole;
	}

	public Double getOrderWxAmountWhole() {
		return orderWxAmountWhole;
	}

	public void setOrderWxAmountWhole(Double orderWxAmountWhole) {
		this.orderWxAmountWhole = orderWxAmountWhole;
	}

	public Double getOrderWxChargeWhole() {
		return orderWxChargeWhole;
	}

	public void setOrderWxChargeWhole(Double orderWxChargeWhole) {
		this.orderWxChargeWhole = orderWxChargeWhole;
	}

	public Double getPriceWxAmountWhole() {
		return priceWxAmountWhole;
	}

	public void setPriceWxAmountWhole(Double priceWxAmountWhole) {
		this.priceWxAmountWhole = priceWxAmountWhole;
	}

	public Double getPriceWxChargeWhole() {
		return priceWxChargeWhole;
	}

	public void setPriceWxChargeWhole(Double priceWxChargeWhole) {
		this.priceWxChargeWhole = priceWxChargeWhole;
	}

	public Double getAwholeWx() {
		return awholeWx;
	}

	public void setAwholeWx(Double awholeWx) {
		this.awholeWx = awholeWx;
	}

	public Double getDepositWxAmountTest() {
		return depositWxAmountTest;
	}

	public void setDepositWxAmountTest(Double depositWxAmountTest) {
		this.depositWxAmountTest = depositWxAmountTest;
	}

	public Double getDepositWxRefundTest() {
		return depositWxRefundTest;
	}

	public void setDepositWxRefundTest(Double depositWxRefundTest) {
		this.depositWxRefundTest = depositWxRefundTest;
	}

	public Double getDepositWxChargeTest() {
		return depositWxChargeTest;
	}

	public void setDepositWxChargeTest(Double depositWxChargeTest) {
		this.depositWxChargeTest = depositWxChargeTest;
	}

	public Double getOrderWxChargeTest() {
		return orderWxChargeTest;
	}

	public void setOrderWxChargeTest(Double orderWxChargeTest) {
		this.orderWxChargeTest = orderWxChargeTest;
	}

	public Double getPriceWxAmountTest() {
		return priceWxAmountTest;
	}

	public void setPriceWxAmountTest(Double priceWxAmountTest) {
		this.priceWxAmountTest = priceWxAmountTest;
	}

	public Double getOrderWxAmountTest() {
		return orderWxAmountTest;
	}

	public void setOrderWxAmountTest(Double orderWxAmountTest) {
		this.orderWxAmountTest = orderWxAmountTest;
	}

	public Double getPriceWxChargeTest() {
		return priceWxChargeTest;
	}

	public void setPriceWxChargeTest(Double priceWxChargeTest) {
		this.priceWxChargeTest = priceWxChargeTest;
	}

	public Double getAswWx() {
		return aswWx;
	}

	public void setAswWx(Double aswWx) {
		this.aswWx = aswWx;
	}

	public Double getChargeRefundWhole() {
		return chargeRefundWhole;
	}

	public void setChargeRefundWhole(Double chargeRefundWhole) {
		this.chargeRefundWhole = chargeRefundWhole;
	}

	public Double getChargeRefundTest() {
		return chargeRefundTest;
	}

	public void setChargeRefundTest(Double chargeRefundTest) {
		this.chargeRefundTest = chargeRefundTest;
	}

	public Double getOrderIncomeTotal() {
		return orderIncomeTotal;
	}

	public void setOrderIncomeTotal(Double orderIncomeTotal) {
		this.orderIncomeTotal = orderIncomeTotal;
	}

}
