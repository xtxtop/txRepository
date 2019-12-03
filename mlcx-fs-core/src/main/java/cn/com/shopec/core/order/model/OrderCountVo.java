package cn.com.shopec.core.order.model;

import java.util.List;

import cn.com.shopec.core.common.Entity;

/** 
 * 订单运营统计
 */
public class OrderCountVo extends Entity<String> {
	
	private static final long serialVersionUID = 1l;
	
	/*Auto generated properties start*/
	
	private String orderDay;
	
	//取车点场站
	private List<OrderLogctonCountVo> orderLogctonCountVo;
	
	private List<OrderLogctonCountVo> orderLogctonCountVozj;

	private String option;
	
	private String option2;

	private String optionMoney;
	
	private String optionMoney2;
	//订单客单价 
	private Integer orderUserAmount;
	//平均时长
	private Integer orderUserTime;
   
	
	
	public Integer getOrderUserAmount() {
		return orderUserAmount;
	}

	public void setOrderUserAmount(Integer orderUserAmount) {
		this.orderUserAmount = orderUserAmount;
	}

	public Integer getOrderUserTime() {
		return orderUserTime;
	}

	public void setOrderUserTime(Integer orderUserTime) {
		this.orderUserTime = orderUserTime;
	}

	public String getOptionMoney() {
		return optionMoney;
	}

	public void setOptionMoney(String optionMoney) {
		this.optionMoney = optionMoney;
	}

	public String getOptionMoney2() {
		return optionMoney2;
	}

	public void setOptionMoney2(String optionMoney2) {
		this.optionMoney2 = optionMoney2;
	}

	public String getOption2() {
		return option2;
	}

	public void setOption2(String option2) {
		this.option2 = option2;
	}

	public String getOption() {
		return option;
	}

	public void setOption(String option) {
		this.option = option;
	}

	public List<OrderLogctonCountVo> getOrderLogctonCountVo() {
		return orderLogctonCountVo;
	}

	public void setOrderLogctonCountVo(List<OrderLogctonCountVo> orderLogctonCountVo) {
		this.orderLogctonCountVo = orderLogctonCountVo;
	}

	public List<OrderLogctonCountVo> getOrderLogctonCountVozj() {
		return orderLogctonCountVozj;
	}

	public void setOrderLogctonCountVozj(List<OrderLogctonCountVo> orderLogctonCountVozj) {
		this.orderLogctonCountVozj = orderLogctonCountVozj;
	}

	public String getOrderDay() {
		return orderDay;
	}

	public void setOrderDay(String orderDay) {
		this.orderDay = orderDay;
	}
 
}
