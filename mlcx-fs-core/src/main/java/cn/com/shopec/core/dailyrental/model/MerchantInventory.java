package cn.com.shopec.core.dailyrental.model;

import java.util.Date;

import cn.com.shopec.core.common.Entity;

/** 
 * 商家库存表 数据实体类
 */
public class MerchantInventory extends Entity<String> {
	
	private static final long serialVersionUID = 1l;
	
	/*Auto generated properties start*/
	
	//商家库存id
	private String merInventoryId;
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
	//车型年代ID
	private String carPeriodId;
	//车辆年代名称
	private String carPeriodName;
	//适用类型：1、经济型，2、商务型，3、豪华型，4、6至15座商务，5、SUV
	private Integer carType;
	//城市id
	private String cityId;
	//城市名称
	private String cityName;
	//车型图片
	private String carModelUrl;
	//排量
	private String displacement;
	//变速箱1、手动，2、自动3、手自一体
	private Integer gearBox;
	//车身箱型1、两箱2、三箱
	private Integer boxType;
	//座位数
	private Integer seatNumber;
	//所属商家
	private String merchantId;
	//租赁商名称
	private String merchantName;
	//每日库存
	private Integer inventoryDay;
	//是否发布（0，未发布 1、发布）
	private Integer isPublish;
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
	
	
	
	/*Customized properties start*/
	
	/*Customized properties end*/
	
	
	
	/*Auto generated methods start*/
	
	@Override
	public String getPK(){
		return merInventoryId;
	}
	
	public String getMerInventoryId(){
		return merInventoryId;
	}
	
	public void setMerInventoryId(String merInventoryId){
		this.merInventoryId = merInventoryId;
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
	
	public String getCarPeriodId() {
		return carPeriodId;
	}

	public void setCarPeriodId(String carPeriodId) {
		this.carPeriodId = carPeriodId;
	}

	public String getCarPeriodName() {
		return carPeriodName;
	}

	public void setCarPeriodName(String carPeriodName) {
		this.carPeriodName = carPeriodName;
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
	
	public String getCarModelUrl(){
		return carModelUrl;
	}
	
	public void setCarModelUrl(String carModelUrl){
		this.carModelUrl = carModelUrl;
	}
	
	public String getDisplacement(){
		return displacement;
	}
	
	public void setDisplacement(String displacement){
		this.displacement = displacement;
	}
	
	public Integer getGearBox(){
		return gearBox;
	}
	
	public void setGearBox(Integer gearBox){
		this.gearBox = gearBox;
	}
	
	public Integer getBoxType(){
		return boxType;
	}
	
	public void setBoxType(Integer boxType){
		this.boxType = boxType;
	}
	
	public Integer getSeatNumber(){
		return seatNumber;
	}
	
	public void setSeatNumber(Integer seatNumber){
		this.seatNumber = seatNumber;
	}
	
	public String getMerchantId(){
		return merchantId;
	}
	
	public void setMerchantId(String merchantId){
		this.merchantId = merchantId;
	}
	
	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public Integer getInventoryDay(){
		return inventoryDay;
	}
	
	public void setInventoryDay(Integer inventoryDay){
		this.inventoryDay = inventoryDay;
	}
	
	public Integer getIsPublish() {
		return isPublish;
	}

	public void setIsPublish(Integer isPublish) {
		this.isPublish = isPublish;
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
	
	
	@Override
	public String toString() {
		return "MerchantInventory ["
		 + "merInventoryId = " + merInventoryId + ", carBrandId = " + carBrandId + ", carBrandName = " + carBrandName + ", carModelId = " + carModelId
		 + ", carModelName = " + carModelName + ", carSeriesId = " + carSeriesId + ", carSeriesName = " + carSeriesName + ", carType = " + carType
		 + ", cityId = " + cityId + ", cityName = " + cityName + ", carModelUrl = " + carModelUrl + ", displacement = " + displacement
		 + ", gearBox = " + gearBox + ", boxType = " + boxType + ", seatNumber = " + seatNumber + ", merchantId = " + merchantId
		 + ", inventoryDay = " + inventoryDay + ", createTime = " + createTime + ", createTimeStart = " + createTimeStart + ", createTimeEnd = " + createTimeEnd + ", updateTime = " + updateTime + ", updateTimeStart = " + updateTimeStart + ", updateTimeEnd = " + updateTimeEnd + ", operatorId = " + operatorId
		 + ", operatorType = " + operatorType
		+"]";
	}
}
