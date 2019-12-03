package cn.com.shopec.core.dailyrental.model;

import java.util.Date;

import cn.com.shopec.core.common.Entity;

/** 
 * 车辆库存表 数据实体类
 */
public class CarInventory extends Entity<String> {
	
	private static final long serialVersionUID = 1l;
	
	/*Auto generated properties start*/
	
	//车辆库存id
	private String carInventoryId;
	//车辆品牌
	private String carBrandId;
	//车辆品牌名称
	private String carBrandName;
	//车型
	private String carModelId;
	//车型名称
	private String carModelName;
	//车系id
	private String carSeriesId;
	//车系名称
	private String carSeriesName;
	//适用类型：1、经济型，2、商务型，3、豪华型，4、6至15座商务，5、SUV
	private Integer carType;
	//城市id
	private String cityId;
	//城市名称
	private String cityName;
	//车辆总库存数
	private Integer inventoryTotal;
	//已租借数量
	private Integer leasedQuantity;
	//已预订数量
	private Integer reserveQuantity;
	//可用库存数量
	private Integer availableInventory;
	//创建时间
	private Date createTime;
	//创建时间 时间范围起（查询用）
	private Date createTimeStart;
	//创建时间 时间范围止（查询用）
	private Date createTimeEnd;	
	//修改时间
	private Date updateTime;
	//修改时间 时间范围起（查询用）
	private Date updateTimeStart;
	//修改时间 时间范围止（查询用）
	private Date updateTimeEnd;	
	//操作人id
	private String operatorId;
	//操作人类型
	private Integer operatorType;
	
	/*Auto generated properties end*/

	//以前为新增属性用于后台查询
	//今日租借数量
	private Integer todayLeasedQuantity;
	//明日可用库存数量
	private Integer tomorrowAvailableInventory;
	
	private String ruleId;
	private Double priceOfDay;
	private Double priceOfDayOrdinary;
	private String dailyAveragePrice;
	private Integer seatNumber;
	private String tons;
	private String displacement;
	private Integer gearBox;
	private String carModelUrl;
	private String boxType;
	//选择天数的总价格
	private String  sumDayAmount;
	
	
	/*Customized properties start*/
	
	/*Customized properties end*/
	
	
	
	/*Auto generated methods start*/
	public String getRuleId() {
		return ruleId;
	}

	public void setRuleId(String ruleId) {
		this.ruleId = ruleId;
	}

	public Double getPriceOfDay() {
		return priceOfDay;
	}

	public void setPriceOfDay(Double priceOfDay) {
		this.priceOfDay = priceOfDay;
	}

	public Double getPriceOfDayOrdinary() {
		return priceOfDayOrdinary;
	}

	public void setPriceOfDayOrdinary(Double priceOfDayOrdinary) {
		this.priceOfDayOrdinary = priceOfDayOrdinary;
	}

	public String getDailyAveragePrice() {
		return dailyAveragePrice;
	}

	public void setDailyAveragePrice(String dailyAveragePrice) {
		this.dailyAveragePrice = dailyAveragePrice;
	}

	public Integer getSeatNumber() {
		return seatNumber;
	}

	public void setSeatNumber(Integer seatNumber) {
		this.seatNumber = seatNumber;
	}

	public String getTons() {
		return tons;
	}

	public void setTons(String tons) {
		this.tons = tons;
	}

	public String getDisplacement() {
		return displacement;
	}

	public void setDisplacement(String displacement) {
		this.displacement = displacement;
	}

	public Integer getGearBox() {
		return gearBox;
	}

	public void setGearBox(Integer gearBox) {
		this.gearBox = gearBox;
	}

	public String getCarModelUrl() {
		return carModelUrl;
	}

	public void setCarModelUrl(String carModelUrl) {
		this.carModelUrl = carModelUrl;
	}

	public String getBoxType() {
		return boxType;
	}

	public void setBoxType(String boxType) {
		this.boxType = boxType;
	}
	@Override
	public String getPK(){
		return carInventoryId;
	}
	
	public String getCarInventoryId(){
		return carInventoryId;
	}
	
	public void setCarInventoryId(String carInventoryId){
		this.carInventoryId = carInventoryId;
	}
	
	public String getCarBrandId(){
		return carBrandId;
	}
	
	public void setCarBrandId(String carBrandId){
		this.carBrandId = carBrandId;
	}
	
	public String getCarBrandName(){
		return carBrandName;
	}
	
	public void setCarBrandName(String carBrandName){
		this.carBrandName = carBrandName;
	}
	
	public String getCarModelId(){
		return carModelId;
	}
	
	public void setCarModelId(String carModelId){
		this.carModelId = carModelId;
	}
	
	public String getCarModelName(){
		return carModelName;
	}
	
	public void setCarModelName(String carModelName){
		this.carModelName = carModelName;
	}
	
	public String getCarSeriesId(){
		return carSeriesId;
	}
	
	public void setCarSeriesId(String carSeriesId){
		this.carSeriesId = carSeriesId;
	}
	
	public String getCarSeriesName(){
		return carSeriesName;
	}
	
	public void setCarSeriesName(String carSeriesName){
		this.carSeriesName = carSeriesName;
	}
	
	public Integer getCarType(){
		return carType;
	}
	
	public void setCarType(Integer carType){
		this.carType = carType;
	}
	
	public String getCityId(){
		return cityId;
	}
	
	public void setCityId(String cityId){
		this.cityId = cityId;
	}
	
	public String getCityName(){
		return cityName;
	}
	
	public void setCityName(String cityName){
		this.cityName = cityName;
	}
	
	public Integer getInventoryTotal(){
		return inventoryTotal;
	}
	
	public void setInventoryTotal(Integer inventoryTotal){
		this.inventoryTotal = inventoryTotal;
	}
	
	public Integer getLeasedQuantity(){
		return leasedQuantity;
	}
	
	public void setLeasedQuantity(Integer leasedQuantity){
		this.leasedQuantity = leasedQuantity;
	}
	
	public Integer getReserveQuantity(){
		return reserveQuantity;
	}
	
	public void setReserveQuantity(Integer reserveQuantity){
		this.reserveQuantity = reserveQuantity;
	}
	
	public Integer getAvailableInventory(){
		return availableInventory;
	}
	
	public void setAvailableInventory(Integer availableInventory){
		this.availableInventory = availableInventory;
	}
	
	public Date getCreateTime(){
		return createTime;
	}
	
	public void setCreateTime(Date createTime){
		this.createTime = createTime;
	}
	
	public Date getCreateTimeStart(){
		return createTimeStart;
	}
	
	public void setCreateTimeStart(Date createTimeStart){
		this.createTimeStart = createTimeStart;
	}
	
	public Date getCreateTimeEnd(){
		return createTimeEnd;
	}
	
	public void setCreateTimeEnd(Date createTimeEnd){
		this.createTimeEnd = createTimeEnd;
	}	
	
	public Date getUpdateTime(){
		return updateTime;
	}
	
	public void setUpdateTime(Date updateTime){
		this.updateTime = updateTime;
	}
	
	public Date getUpdateTimeStart(){
		return updateTimeStart;
	}
	
	public void setUpdateTimeStart(Date updateTimeStart){
		this.updateTimeStart = updateTimeStart;
	}
	
	public Date getUpdateTimeEnd(){
		return updateTimeEnd;
	}
	
	public void setUpdateTimeEnd(Date updateTimeEnd){
		this.updateTimeEnd = updateTimeEnd;
	}	
	
	public String getOperatorId(){
		return operatorId;
	}
	
	public void setOperatorId(String operatorId){
		this.operatorId = operatorId;
	}
	
	public Integer getOperatorType(){
		return operatorType;
	}
	
	public void setOperatorType(Integer operatorType){
		this.operatorType = operatorType;
	}
	
	
	/*Auto generated methods end*/
	
	
	
	/*Customized methods start*/
	
	/*Customized methods end*/
	
	
	public Integer getTodayLeasedQuantity() {
		return todayLeasedQuantity;
	}

	public void setTodayLeasedQuantity(Integer todayLeasedQuantity) {
		this.todayLeasedQuantity = todayLeasedQuantity;
	}

	public Integer getTomorrowAvailableInventory() {
		return tomorrowAvailableInventory;
	}

	public void setTomorrowAvailableInventory(Integer tomorrowAvailableInventory) {
		this.tomorrowAvailableInventory = tomorrowAvailableInventory;
	}

	@Override
	public String toString() {
		return "CarInventory ["
		 + "carInventoryId = " + carInventoryId + ", carBrandId = " + carBrandId + ", carBrandName = " + carBrandName + ", carModelId = " + carModelId
		 + ", carModelName = " + carModelName + ", carSeriesId = " + carSeriesId + ", carSeriesName = " + carSeriesName + ", carType = " + carType
		 + ", cityId = " + cityId + ", cityName = " + cityName + ", inventoryTotal = " + inventoryTotal + ", leasedQuantity = " + leasedQuantity
		 + ", reserveQuantity = " + reserveQuantity + ", availableInventory = " + availableInventory + ", createTime = " + createTime + ", createTimeStart = " + createTimeStart + ", createTimeEnd = " + createTimeEnd + ", updateTime = " + updateTime + ", updateTimeStart = " + updateTimeStart + ", updateTimeEnd = " + updateTimeEnd
		 + ", operatorId = " + operatorId + ", operatorType = " + operatorType
		+"]";
	}

	public String getSumDayAmount() {
		return sumDayAmount;
	}

	public void setSumDayAmount(String sumDayAmount) {
		this.sumDayAmount = sumDayAmount;
	}

	
}
