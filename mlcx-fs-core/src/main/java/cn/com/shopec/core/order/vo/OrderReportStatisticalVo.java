package cn.com.shopec.core.order.vo;

public class OrderReportStatisticalVo {
	//新增订单数
	private String orderNumber;
	//取消订单数
	private String orderDeNumber;
	//时间范围
	private String time;
	//完成订单数
	private String orderFhNumber;
	
	//订单金额
	private Double orderAmount;
	//订单付款额
	private Double payAmount;
	//余额抵扣额
	private Double balanceAmount;
	
	public String getOrderNumber() {
		return orderNumber;
	}
	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}
	public String getOrderDeNumber() {
		return orderDeNumber;
	}
	public void setOrderDeNumber(String orderDeNumber) {
		this.orderDeNumber = orderDeNumber;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getOrderFhNumber() {
		return orderFhNumber;
	}
	public void setOrderFhNumber(String orderFhNumber) {
		this.orderFhNumber = orderFhNumber;
	}
	public Double getOrderAmount() {
		return orderAmount;
	}
	public void setOrderAmount(Double orderAmount) {
		this.orderAmount = orderAmount;
	}
	public Double getPayAmount() {
		return payAmount;
	}
	public void setPayAmount(Double payAmount) {
		this.payAmount = payAmount;
	}
	public Double getBalanceAmount() {
		return balanceAmount;
	}
	public void setBalanceAmount(Double balanceAmount) {
		this.balanceAmount = balanceAmount;
	}
}