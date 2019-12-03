package cn.com.shopec.core.order.vo;

public class OrderReportFormVo {
	//新增订单数
	private String orderNumber;
	//取消订单数
	private String orderDeNumber;
	//时间范围
	private String time;
	//订单总额
	private String total;
	//完成订单数
	private String orderFhNumber;
	
	
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
	public String getTotal() {
		return total;
	}
	public void setTotal(String total) {
		this.total = total;
	}
	public String getOrderFhNumber() {
		return orderFhNumber;
	}
	public void setOrderFhNumber(String orderFhNumber) {
		this.orderFhNumber = orderFhNumber;
	}
	

}
