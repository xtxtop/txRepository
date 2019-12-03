package cn.com.shopec.mapi.order.vo;

import java.util.List;

public class OrderDaySubmitVo {
	// 车型id
	private String carModelId;
	// 品牌id
	private String carBrandId;
	// 品牌名称
	private String carBrandName;
	// 车型名称
	private String carModelName;
	// 城市id（具体来自数据字典表）
	private String cityId;
	// 箱型
	private String boxType;
	// 排量
	private String displacement;
	// 档位
	private String stall;
	// 座位数
	private Integer seatNumber;
	// 约取车地点（场站编号）
	private String startParkNo;
	// 取车场站名称
	private String startParkName;
	// 还车地点（场站编号）
	private String terminalParkNo;
	// 还车场站名称
	private String terminalParkName;
	// 预约取车时间
	private String appointmentPick;
	// 预约还车时间
	private String appointmentStill;
	// 预约取车当天为周几
	private String startWeek;
	// 预约还车当天为周几
	private String stillWeek;
	// 租车天数
	private Integer days;
	// 租车整天费用
	private Double dayAmount;
	// 不足一天的小时数
	private Integer hours;
	// 租车超时费用（不足一天）
	private Double hourAmount;
	// 服务费（每单）
	private Double coverCharge;
	// 保险费用
	private Double insurance;
	// 预授权(押金)
	private Double preLicensing;

	// 合计费用
	private Double amount;
	// 价格日历信息
	private List<PriceCalendarVo> list;

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

	public String getStartParkNo() {
		return startParkNo;
	}

	public void setStartParkNo(String startParkNo) {
		this.startParkNo = startParkNo;
	}

	public String getStartParkName() {
		return startParkName;
	}

	public void setStartParkName(String startParkName) {
		this.startParkName = startParkName;
	}

	public String getTerminalParkNo() {
		return terminalParkNo;
	}

	public void setTerminalParkNo(String terminalParkNo) {
		this.terminalParkNo = terminalParkNo;
	}

	public String getTerminalParkName() {
		return terminalParkName;
	}

	public void setTerminalParkName(String terminalParkName) {
		this.terminalParkName = terminalParkName;
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

	public String getStartWeek() {
		return startWeek;
	}

	public void setStartWeek(String startWeek) {
		this.startWeek = startWeek;
	}

	public String getStillWeek() {
		return stillWeek;
	}

	public void setStillWeek(String stillWeek) {
		this.stillWeek = stillWeek;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getCarModelName() {
		return carModelName;
	}

	public void setCarModelName(String carModelName) {
		this.carModelName = carModelName;
	}

	public String getStall() {
		return stall;
	}

	public void setStall(String stall) {
		this.stall = stall;
	}

	public List<PriceCalendarVo> getList() {
		return list;
	}

	public void setList(List<PriceCalendarVo> list) {
		this.list = list;
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

	public Double getHourAmount() {
		return hourAmount;
	}

	public void setHourAmount(Double hourAmount) {
		this.hourAmount = hourAmount;
	}

	public Double getCoverCharge() {
		return coverCharge;
	}

	public void setCoverCharge(Double coverCharge) {
		this.coverCharge = coverCharge;
	}

	public Double getInsurance() {
		return insurance;
	}

	public void setInsurance(Double insurance) {
		this.insurance = insurance;
	}

	public Integer getDays() {
		return days;
	}

	public void setDays(Integer days) {
		this.days = days;
	}

	public Integer getHours() {
		return hours;
	}

	public void setHours(Integer hours) {
		this.hours = hours;
	}

	public Double getPreLicensing() {
		return preLicensing;
	}

	public void setPreLicensing(Double preLicensing) {
		this.preLicensing = preLicensing;
	}

	public Double getDayAmount() {
		return dayAmount;
	}

	public void setDayAmount(Double dayAmount) {
		this.dayAmount = dayAmount;
	}

	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

}
