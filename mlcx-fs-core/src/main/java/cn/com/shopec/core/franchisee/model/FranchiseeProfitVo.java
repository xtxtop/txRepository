package cn.com.shopec.core.franchisee.model;

public class FranchiseeProfitVo {
	// 加盟商编号
	private String franchiseeNo;
	// 加盟商名称
	private String franchiseeName;
	// 订单数量
	private Integer orderNumber;
	// 订单总金额
	private Double orderAmount;
	//分润总额
	private Double profitAmount;
	// 收入总金额（扣除优惠）
	private Double incomeAmountAll;
	// 车辆订单数
	private Integer carOrderNumber;
	// 车辆产生分润订单总金额
	private Double orderCarAmount;
	// 车辆产生分润订单收入额
	private Double incomeCarAmount;
	// 车辆产生分润比例
	private Double carProportion;
	// 车辆产生分润总金额
	private Double carProfit;
	// 场站产生分润订单数
	private Integer parkOrderNumber;
	// 场站产生分润订单总金额
	private Double parkCarAmount;
	// 场站产生分润订单收入额
	private Double incomeParkAmount;
	// 场站产生分润比例
	private Double parkProportion;
	// 场站产生分润总金额
	private Double parkProfit;
	// 查询月份
	private String months;
	// 查询年
	private String year;
	//查询季度
	private String quarter;
	//车牌号
	private String carPlateNo;
	//场站名称
	private String actualTakeLoacton;
	//总里程
	private Double orderMileage;
	//总时长
	private Integer orderDuration;
	//平均里程
	private Double avgOrderMileage;
	//平均时长
	private Integer avgOrderDuration;
	//车辆或者场站编号
	private String carOrParkNo;
	
	public String getCarPlateNo() {
		return carPlateNo;
	}

	public void setCarPlateNo(String carPlateNo) {
		this.carPlateNo = carPlateNo;
	}

	public String getActualTakeLoacton() {
		return actualTakeLoacton;
	}

	public void setActualTakeLoacton(String actualTakeLoacton) {
		this.actualTakeLoacton = actualTakeLoacton;
	}

	public Double getOrderMileage() {
		return orderMileage;
	}

	public void setOrderMileage(Double orderMileage) {
		this.orderMileage = orderMileage;
	}

	public Integer getOrderDuration() {
		return orderDuration;
	}

	public void setOrderDuration(Integer orderDuration) {
		this.orderDuration = orderDuration;
	}

	public Double getAvgOrderMileage() {
		return avgOrderMileage;
	}

	public void setAvgOrderMileage(Double avgOrderMileage) {
		this.avgOrderMileage = avgOrderMileage;
	}

	public Integer getAvgOrderDuration() {
		return avgOrderDuration;
	}

	public void setAvgOrderDuration(Integer avgOrderDuration) {
		this.avgOrderDuration = avgOrderDuration;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getQuarter() {
		return quarter;
	}

	public void setQuarter(String quarter) {
		this.quarter = quarter;
	}

	public String getFranchiseeNo() {
		return franchiseeNo;
	}

	public void setFranchiseeNo(String franchiseeNo) {
		this.franchiseeNo = franchiseeNo;
	}

	public Integer getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(Integer orderNumber) {
		this.orderNumber = orderNumber;
	}

	public Double getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(Double orderAmount) {
		this.orderAmount = orderAmount;
	}

	public Double getIncomeAmountAll() {
		return incomeAmountAll;
	}

	public void setIncomeAmountAll(Double incomeAmountAll) {
		this.incomeAmountAll = incomeAmountAll;
	}

	public Integer getCarOrderNumber() {
		return carOrderNumber;
	}

	public void setCarOrderNumber(Integer carOrderNumber) {
		this.carOrderNumber = carOrderNumber;
	}

	public Double getOrderCarAmount() {
		return orderCarAmount;
	}

	public void setOrderCarAmount(Double orderCarAmount) {
		this.orderCarAmount = orderCarAmount;
	}

	public Double getIncomeCarAmount() {
		return incomeCarAmount;
	}

	public void setIncomeCarAmount(Double incomeCarAmount) {
		this.incomeCarAmount = incomeCarAmount;
	}

	public Double getCarProportion() {
		return carProportion;
	}

	public void setCarProportion(Double carProportion) {
		this.carProportion = carProportion;
	}

	public Double getCarProfit() {
		return carProfit;
	}

	public void setCarProfit(Double carProfit) {
		this.carProfit = carProfit;
	}

	public Integer getParkOrderNumber() {
		return parkOrderNumber;
	}

	public void setParkOrderNumber(Integer parkOrderNumber) {
		this.parkOrderNumber = parkOrderNumber;
	}

	public Double getParkCarAmount() {
		return parkCarAmount;
	}

	public void setParkCarAmount(Double parkCarAmount) {
		this.parkCarAmount = parkCarAmount;
	}

	public Double getIncomeParkAmount() {
		return incomeParkAmount;
	}

	public void setIncomeParkAmount(Double incomeParkAmount) {
		this.incomeParkAmount = incomeParkAmount;
	}

	public Double getParkProportion() {
		return parkProportion;
	}

	public void setParkProportion(Double parkProportion) {
		this.parkProportion = parkProportion;
	}

	public Double getParkProfit() {
		return parkProfit;
	}

	public void setParkProfit(Double parkProfit) {
		this.parkProfit = parkProfit;
	}

	public String getFranchiseeName() {
		return franchiseeName;
	}

	public void setFranchiseeName(String franchiseeName) {
		this.franchiseeName = franchiseeName;
	}

	public String getMonths() {
		return months;
	}

	public void setMonths(String months) {
		this.months = months;
	}

	public Double getProfitAmount() {
		return profitAmount;
	}

	public void setProfitAmount(Double profitAmount) {
		this.profitAmount = profitAmount;
	}

	public String getCarOrParkNo() {
		return carOrParkNo;
	}

	public void setCarOrParkNo(String carOrParkNo) {
		this.carOrParkNo = carOrParkNo;
	}
	
}
