package cn.com.shopec.mapi.member.vo;

import java.io.Serializable;
import java.util.Date;

/** 
 * 金豆消费明细返回实体类
 */
public class GoldBeansConsumerDetail implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8715960269079420254L;
	//订单编号
	private String orderNo;
	//消费金豆金额
	private Double consumerBeansAmount;
	//消费时间 （格式yyyy-MM-dd HH:mm:ss）
	private String consumerTime;
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public Double getConsumerBeansAmount() {
		return consumerBeansAmount;
	}
	public void setConsumerBeansAmount(Double consumerBeansAmount) {
		this.consumerBeansAmount = consumerBeansAmount;
	}
	public String getConsumerTime() {
		return consumerTime;
	}
	public void setConsumerTime(String consumerTime) {
		this.consumerTime = consumerTime;
	}
	
}
