package cn.com.shopec.mapi.order.vo;

public class PriceCalendarVo {
	// 日期
	private String time;
	// 价格
	private Double price;
	// 当天为周几
	private String week;

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getWeek() {
		return week;
	}

	public void setWeek(String week) {
		this.week = week;
	}

}
