package cn.com.shopec.mapi.order.vo;

import java.util.Date;

public class OrderDayListVo {

	// 订单号
	private String orderNo;
	// 车型名称
	private String carModelName;
	// 品牌名称
	private String carBrandName;
	// 箱型
	private String boxType;
	// 排量
	private String displacement;
	// 档位：1、手动，2、自动
	private String stall;
	// 座位数
	private Integer seatNumber;
	// 车辆照片url1
	private String carPhotoUrl1;
	// 订单状态（0、已预约，1、进行中，2、已完成，3、已取消，默认0）
	private String orderStatus;
	// 预约取车时间
	private Date appointmentPick;
	// 预约还车时间
	private Date appointmentStill;
	// 取车地点
	private String actualTakeLoacton;
	// 还车地点
	private String actualTerminalParkNo;

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
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

	public String getBoxType() {
		return boxType;
	}

	public void setBoxType(String boxType) {
		this.boxType = boxType;
	}

	public String getDisplacement() {
		return displacement;
	}

	public void setDisplacement(String displacement) {
		this.displacement = displacement;
	}

	public Integer getSeatNumber() {
		return seatNumber;
	}

	public void setSeatNumber(Integer seatNumber) {
		this.seatNumber = seatNumber;
	}

	public String getCarPhotoUrl1() {
		return carPhotoUrl1;
	}

	public void setCarPhotoUrl1(String carPhotoUrl1) {
		this.carPhotoUrl1 = carPhotoUrl1;
	}

	public Date getAppointmentPick() {
		return appointmentPick;
	}

	public void setAppointmentPick(Date appointmentPick) {
		this.appointmentPick = appointmentPick;
	}

	public Date getAppointmentStill() {
		return appointmentStill;
	}

	public void setAppointmentStill(Date appointmentStill) {
		this.appointmentStill = appointmentStill;
	}

	public String getActualTakeLoacton() {
		return actualTakeLoacton;
	}

	public void setActualTakeLoacton(String actualTakeLoacton) {
		this.actualTakeLoacton = actualTakeLoacton;
	}

	public String getActualTerminalParkNo() {
		return actualTerminalParkNo;
	}

	public void setActualTerminalParkNo(String actualTerminalParkNo) {
		this.actualTerminalParkNo = actualTerminalParkNo;
	}

	public String getStall() {
		return stall;
	}

	public void setStall(String stall) {
		this.stall = stall;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

}
