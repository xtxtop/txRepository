package cn.com.shopec.mapi.order.vo;

import java.util.List;

public class PriceCalendarNsVo {
	// 保险费用
	private Double insurance;
	// 每日平均租金
	private Double dailyRent;
	// 预授权
	private Double preLicensing;
	// 预约取车时间
	private String appointmentPick;
	// 预约还车时间
	private String appointmentStill;
	// 车型id
	private String carModelId;
	// 车型名称
	private String carModelName;
	// 品牌id
	private String carBrandId;
	// 品牌名称
	private String carBrandName;
	// 城市ID
	private String cityId;
	// 附加费用编号
	private String extraCostsNo;

	// 价格日历信息
	private List<PriceCalendarVo> priceList;

	public Double getInsurance() {
		return insurance;
	}

	public void setInsurance(Double insurance) {
		this.insurance = insurance;
	}

	public Double getDailyRent() {
		return dailyRent;
	}

	public void setDailyRent(Double dailyRent) {
		this.dailyRent = dailyRent;
	}

	public Double getPreLicensing() {
		return preLicensing;
	}

	public void setPreLicensing(Double preLicensing) {
		this.preLicensing = preLicensing;
	}

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

	public String getCarModelId() {
		return carModelId;
	}

	public void setCarModelId(String carModelId) {
		this.carModelId = carModelId;
	}

	public String getCarBrandId() {
		return carBrandId;
	}

	public void setCarBrandId(String carBrandId) {
		this.carBrandId = carBrandId;
	}

	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	public String getExtraCostsNo() {
		return extraCostsNo;
	}

	public void setExtraCostsNo(String extraCostsNo) {
		this.extraCostsNo = extraCostsNo;
	}

	public List<PriceCalendarVo> getPriceList() {
		return priceList;
	}

	public void setPriceList(List<PriceCalendarVo> priceList) {
		this.priceList = priceList;
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

}
