package cn.com.shopec.core.car.vo;

import java.util.List;

public class CarDaysVo {
	// 预约取车时间
	private String appointmentPick;
	// 预约还车时间
	private String appointmentStill;
	// 城市Id
	private String cityId;
	// 车辆信息
	private List<CarDayVo> carList;

	public String getAppointmentPick() {
		return appointmentPick;
	}

	public void setAppointmentPick(String appointmentPick) {
		this.appointmentPick = appointmentPick;
	}

	public String getAppointmentStill() {
		return appointmentStill;
	}

	public void setAppointmentStill(String appointmentStill) {
		this.appointmentStill = appointmentStill;
	}

	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	public List<CarDayVo> getCarList() {
		return carList;
	}

	public void setCarList(List<CarDayVo> carList) {
		this.carList = carList;
	}

}
