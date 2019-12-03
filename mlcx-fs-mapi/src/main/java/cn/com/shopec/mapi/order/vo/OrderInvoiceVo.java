package cn.com.shopec.mapi.order.vo;

import java.io.Serializable;
import java.util.Date;

public class OrderInvoiceVo implements Serializable{

	/** 
	*
	* @Fields serialVersionUID : TODO未开发票的订单记录Vo
	*/ 
	private static final long serialVersionUID = 1065780559770148667L;

	// 订单号
	private String orderNo;
	// 应付金额
	private Double payableAmount;
	// 计费时间
	private String startBillingTime;
	// 支付状态（0、未支付，1、已支付，默认0）
	private Integer payStatus;
	//车辆牌号
	private String carPlateNo;
	// 车型名称
	private String carModelName;
	//车型品牌
	private String carBrandName;
	//订单里程
	private Double orderMileage;
	//取车场站
	private String startPark;
	//还车场站
	private String actualTerminalPark;
	//年份
	private  Integer year;  
	//月份
	private Integer month;
	// 订单时长(分钟数)
	private Integer orderDuration;
	//订单的结束时间
	private String finishTime;
	
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public Double getPayableAmount() {
		return payableAmount;
	}
	public void setPayableAmount(Double payableAmount) {
		this.payableAmount = payableAmount;
	}
	public String getStartBillingTime() {
		return startBillingTime;
	}
	public void setStartBillingTime(String startBillingTime) {
		this.startBillingTime = startBillingTime;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public Integer getPayStatus() {
		return payStatus;
	}
	public void setPayStatus(Integer payStatus) {
		this.payStatus = payStatus;
	}
	@Override
	public String toString() {
		return "OrderInvoiceVo [orderNo=" + orderNo + ", payableAmount=" + payableAmount + ", startBillingTime="
				+ startBillingTime + ", payStatus=" + payStatus + "]";
	}
	public String getCarPlateNo() {
		return carPlateNo;
	}
	public void setCarPlateNo(String carPlateNo) {
		this.carPlateNo = carPlateNo;
	}
	public String getCarModelName() {
		return carModelName;
	}
	public void setCarModelName(String carModelName) {
		this.carModelName = carModelName;
	}
	public String getCarBrandName() {
		return carBrandName;
	}
	public void setCarBrandName(String carBrandName) {
		this.carBrandName = carBrandName;
	}
	public Double getOrderMileage() {
		return orderMileage;
	}
	public void setOrderMileage(Double orderMileage) {
		this.orderMileage = orderMileage;
	}
	public String getStartPark() {
		return startPark;
	}
	public void setStartPark(String startPark) {
		this.startPark = startPark;
	}
	public String getActualTerminalPark() {
		return actualTerminalPark;
	}
	public void setActualTerminalPark(String actualTerminalPark) {
		this.actualTerminalPark = actualTerminalPark;
	}
	public Integer getMonth() {
		return month;
	}
	public void setMonth(Integer month) {
		this.month = month;
	}
	public Integer getYear() {
		return year;
	}
	public void setYear(Integer year) {
		this.year = year;
	}
	public Integer getOrderDuration() {
		return orderDuration;
	}
	public void setOrderDuration(Integer orderDuration) {
		this.orderDuration = orderDuration;
	}
	public String getFinishTime() {
		return finishTime;
	}
	public void setFinishTime(String finishTime) {
		this.finishTime = finishTime;
	}
	
}
