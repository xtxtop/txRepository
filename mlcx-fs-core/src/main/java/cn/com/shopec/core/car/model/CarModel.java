package cn.com.shopec.core.car.model;

import java.util.Date;

import cn.com.shopec.core.common.Entity;

/** 
 * 车辆车型 数据实体类
 */
public class CarModel extends Entity<String> {
	
	private static final long serialVersionUID = 1l;
	
	/*Auto generated properties start*/
	
	//车型id
	private String carModelId;
	//车型名称
	private String carModelName;
	//品牌id
	private String carBrandId;
	//品牌名称
	private String carBrandName;
	//车型年代ID
	private String carPeriodId;
	//车型年代名称
	private String carPeriodName;
	//车系id
	private String carSeriesId;
	//车系名称
	private String carSeriesName;
	//车身箱型1. 2箱 2 三箱 
	private String boxType;
	//配置款型
	private String configModel;
	//适用类型：1、经济型，2、商务型，3、豪华型，4、6至15座商务，5、SUV
	private Integer carType;
	//座位数1.2座 2. 4座  3.5座 4 7座
	private Integer seatNumber;
	//吨位
	private String tons;
	//排量
	private String displacement;
	//变速箱1、手动，2、自动3、手自一体
	private Integer gearBox;
	//车型图片
	private String carModelUrl;
	//款型描述
	private String carModelInfo;
	//上下线状态（0、下架，1、上架，默认0）
	private Integer onOffLineStatus;
	//上下线状态更新时间
	private Date onOffLineStatusUpdateTime;
	//创建时间
	private Date createTime;
	//创建时间 时间范围起（查询用）
	private Date createTimeStart;
	//创建时间 时间范围止（查询用）
	private Date createTimeEnd;	
	//更新时间
	private Date updateTime;
	//更新时间 时间范围起（查询用）
	private Date updateTimeStart;
	//更新时间 时间范围止（查询用）
	private Date updateTimeEnd;	
	//操作人id
	private String operatorId;
	//操作类型
	private Integer operatorType;
	
	/*Auto generated properties end*/
	//车型名称（查询用）
	private String queryCarModelName;
	
	
	/*Customized properties start*/
	
	/*Customized properties end*/
	
	
	
	/*Auto generated methods start*/
	
	@Override
	public String getPK(){
		return carModelId;
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
	
	public String getCarBrandId(){
		return carBrandId;
	}
	
	public void setCarBrandId(String carBrandId){
		this.carBrandId = carBrandId;
	}
	
	public String getCarBrandName() {
		return carBrandName;
	}

	public void setCarBrandName(String carBrandName) {
		this.carBrandName = carBrandName;
	}

	public String getCarPeriodId(){
		return carPeriodId;
	}
	
	public void setCarPeriodId(String carPeriodId){
		this.carPeriodId = carPeriodId;
	}
	
	
	
	public String getCarSeriesName() {
		return carSeriesName;
	}

	public void setCarSeriesName(String carSeriesName) {
		this.carSeriesName = carSeriesName;
	}

	public String getCarSeriesId(){
		return carSeriesId;
	}
	
	public void setCarSeriesId(String carSeriesId){
		this.carSeriesId = carSeriesId;
	}
	
	public String getBoxType(){
		return boxType;
	}
	
	public void setBoxType(String boxType){
		this.boxType = boxType;
	}
	
	public String getConfigModel(){
		return configModel;
	}
	
	public void setConfigModel(String configModel){
		this.configModel = configModel;
	}
	
	public Integer getSeatNumber(){
		return seatNumber;
	}
	
	public void setSeatNumber(Integer seatNumber){
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

	public Integer getGearBox(){
		return gearBox;
	}
	
	public void setGearBox(Integer gearBox){
		this.gearBox = gearBox;
	}
	
	public String getCarModelUrl(){
		return carModelUrl;
	}
	
	public void setCarModelUrl(String carModelUrl){
		this.carModelUrl = carModelUrl;
	}
	
	public String getCarModelInfo(){
		return carModelInfo;
	}
	
	public void setCarModelInfo(String carModelInfo){
		this.carModelInfo = carModelInfo;
	}
	
	public Integer getOnOffLineStatus() {
		return onOffLineStatus;
	}

	public void setOnOffLineStatus(Integer onOffLineStatus) {
		this.onOffLineStatus = onOffLineStatus;
	}

	public Date getOnOffLineStatusUpdateTime() {
		return onOffLineStatusUpdateTime;
	}

	public void setOnOffLineStatusUpdateTime(Date onOffLineStatusUpdateTime) {
		this.onOffLineStatusUpdateTime = onOffLineStatusUpdateTime;
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
	
	public String getCarPeriodName() {
		return carPeriodName;
	}
	
	public void setCarPeriodName(String carPeriodName) {
		this.carPeriodName = carPeriodName;
	}
	
	public Integer getCarType() {
		return carType;
	}
	
	public void setCarType(Integer carType) {
		this.carType = carType;
	}
	
	
	
	
	/*Auto generated methods end*/
	
	
	
	/*Customized methods start*/
	
	/*Customized methods end*/
	


	public String getQueryCarModelName() {
		return queryCarModelName;
	}

	public void setQueryCarModelName(String queryCarModelName) {
		this.queryCarModelName = queryCarModelName;
	}

	@Override
	public String toString() {
		return "CarModel ["
		 + "carModelId = " + carModelId + ", carModelName = " + carModelName + ", carBrandId = " + carBrandId + ", carPeriodId = " + carPeriodId
		 + ", carSeriesId = " + carSeriesId + ", boxType = " + boxType + ", configModel = " + configModel + ", seatNumber = " + seatNumber
		 + ", tons = " + tons + ", displacement = " + displacement + ", gearBox = " + gearBox + ", carModelUrl = " + carModelUrl
		 + ", carModelInfo = " + carModelInfo + ", createTime = " + createTime + ", createTimeStart = " + createTimeStart + ", createTimeEnd = " + createTimeEnd + ", updateTime = " + updateTime + ", updateTimeStart = " + updateTimeStart + ", updateTimeEnd = " + updateTimeEnd + ", operatorId = " + operatorId
		 + ", operatorType = " + operatorType
		+"]";
	}
}
