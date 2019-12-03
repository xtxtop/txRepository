package cn.com.shopec.core.car.vo;

public class CarDayVo {

	// 车型id
	private String carModelId;
	// 车型名称
	private String carModelName;
	// 品牌id
	private String carBrandId;
	// 品牌名称
	private String carBrandName;
	// 箱型
	private String boxType;
	// 车辆照片url1
	private String carPhotoUrl1;
	// 排量
	private String displacement;
	// 座位数
	private Integer seatNumber;
	// 档位：1、手动，2、自动
	private String stall;
	// 每日平均租金
	private Double dailyRent;
	// 计费规则编号
	private String ruleNo;

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

	public String getCarPhotoUrl1() {
		return carPhotoUrl1;
	}

	public void setCarPhotoUrl1(String carPhotoUrl1) {
		this.carPhotoUrl1 = carPhotoUrl1;
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

	public Double getDailyRent() {
		return dailyRent;
	}

	public void setDailyRent(Double dailyRent) {
		this.dailyRent = dailyRent;
	}

	public String getBoxType() {
		return boxType;
	}

	public void setBoxType(String boxType) {
		this.boxType = boxType;
	}

	public String getStall() {
		return stall;
	}

	public void setStall(String stall) {
		this.stall = stall;
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

	public String getRuleNo() {
		return ruleNo;
	}

	public void setRuleNo(String ruleNo) {
		this.ruleNo = ruleNo;
	}

}
