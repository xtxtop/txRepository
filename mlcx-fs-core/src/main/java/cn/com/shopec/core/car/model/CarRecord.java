package cn.com.shopec.core.car.model;

import java.util.Date;

import cn.com.shopec.core.common.Entity;

/** 
 * 用车记录表 数据实体类
 */
public class CarRecord extends Entity<String> {
	
	private static final long serialVersionUID = 1l;
	
	/*Auto generated properties start*/
	
	//记录id
	private String recordId;
	//车牌号
	private String carPlateNo;
	//车型id（具体见数据字典表）
	private String carModelId;
	//车型名称
	private String carModel;
	//品牌id
	private String carBrandId;
	//品牌名称
	private String carBrand;
	//城市id
	private String cityId;
	//城市名称
	private String city;
	//用车类型（1、订单用车，2、调度用车）
	private Integer useCarType;
	//单据号（用车类型为订单时，是订单号、调度用车时，是调度单号）
	private String documentNo;
	//用车人id
	private String driverId;
	//用车人姓名
	private String driverName;
	//起点编号
	private String startParkNo;
	//起点名称
	private String startParkName;
	//终点编号
	private String terminalParkNo;
	//终点名称
	private String terminalParkName;
	//开始时间
	private Date startTime;
	//开始时间 时间范围起（查询用）
	private Date startTimeStart;
	//开始时间 时间范围止（查询用）
	private Date startTimeEnd;	
	//结束时间
	private Date finishTime;
	//结束时间 时间范围起（查询用）
	private Date finishTimeStart;
	//结束时间 时间范围止（查询用）
	private Date finishTimeEnd;	
	//开始电量
	private Double startPower;
	//结束电量
	private Double finishPower;
	//总里程
	private Double totalMileage;
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
	//操作人类型
	private Integer operatorType;
	//操作人id
	private String operatorId;
	//车辆编号
	private String carNo;
	
	/*Auto generated properties end*/
	
	
	
	/*Customized properties start*/
	
	/*Customized properties end*/
	
	
	
	/*Auto generated methods start*/
	
	@Override
	public String getPK(){
		return recordId;
	}
	
	public String getRecordId(){
		return recordId;
	}
	
	public void setRecordId(String recordId){
		this.recordId = recordId;
	}
	
	public String getCarPlateNo(){
		return carPlateNo;
	}
	
	public void setCarPlateNo(String carPlateNo){
		this.carPlateNo = carPlateNo;
	}
	
	public String getCarModelId(){
		return carModelId;
	}
	
	public void setCarModelId(String carModelId){
		this.carModelId = carModelId;
	}
	
	public String getCarModel(){
		return carModel;
	}
	
	public void setCarModel(String carModel){
		this.carModel = carModel;
	}
	
	public String getCarBrandId(){
		return carBrandId;
	}
	
	public void setCarBrandId(String carBrandId){
		this.carBrandId = carBrandId;
	}
	
	public String getCarBrand(){
		return carBrand;
	}
	
	public void setCarBrand(String carBrand){
		this.carBrand = carBrand;
	}
	
	public String getCityId(){
		return cityId;
	}
	
	public void setCityId(String cityId){
		this.cityId = cityId;
	}
	
	public String getCity(){
		return city;
	}
	
	public void setCity(String city){
		this.city = city;
	}
	
	public Integer getUseCarType(){
		return useCarType;
	}
	
	public void setUseCarType(Integer useCarType){
		this.useCarType = useCarType;
	}
	
	public String getDocumentNo(){
		return documentNo;
	}
	
	public void setDocumentNo(String documentNo){
		this.documentNo = documentNo;
	}
	
	public String getDriverId(){
		return driverId;
	}
	
	public void setDriverId(String driverId){
		this.driverId = driverId;
	}
	
	public String getDriverName(){
		return driverName;
	}
	
	public void setDriverName(String driverName){
		this.driverName = driverName;
	}
	
	public String getStartParkNo(){
		return startParkNo;
	}
	
	public void setStartParkNo(String startParkNo){
		this.startParkNo = startParkNo;
	}
	
	public String getStartParkName(){
		return startParkName;
	}
	
	public void setStartParkName(String startParkName){
		this.startParkName = startParkName;
	}
	
	public String getTerminalParkNo(){
		return terminalParkNo;
	}
	
	public void setTerminalParkNo(String terminalParkNo){
		this.terminalParkNo = terminalParkNo;
	}
	
	public String getTerminalParkName(){
		return terminalParkName;
	}
	
	public void setTerminalParkName(String terminalParkName){
		this.terminalParkName = terminalParkName;
	}
	
	public Date getStartTime(){
		return startTime;
	}
	
	public void setStartTime(Date startTime){
		this.startTime = startTime;
	}
	
	public Date getStartTimeStart(){
		return startTimeStart;
	}
	
	public void setStartTimeStart(Date startTimeStart){
		this.startTimeStart = startTimeStart;
	}
	
	public Date getStartTimeEnd(){
		return startTimeEnd;
	}
	
	public void setStartTimeEnd(Date startTimeEnd){
		this.startTimeEnd = startTimeEnd;
	}	
	
	public Date getFinishTime(){
		return finishTime;
	}
	
	public void setFinishTime(Date finishTime){
		this.finishTime = finishTime;
	}
	
	public Date getFinishTimeStart(){
		return finishTimeStart;
	}
	
	public void setFinishTimeStart(Date finishTimeStart){
		this.finishTimeStart = finishTimeStart;
	}
	
	public Date getFinishTimeEnd(){
		return finishTimeEnd;
	}
	
	public void setFinishTimeEnd(Date finishTimeEnd){
		this.finishTimeEnd = finishTimeEnd;
	}	
	
	public Double getStartPower(){
		return startPower;
	}
	
	public void setStartPower(Double startPower){
		this.startPower = startPower;
	}
	
	public Double getFinishPower(){
		return finishPower;
	}
	
	public void setFinishPower(Double finishPower){
		this.finishPower = finishPower;
	}
	
	public Double getTotalMileage(){
		return totalMileage;
	}
	
	public void setTotalMileage(Double totalMileage){
		this.totalMileage = totalMileage;
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
	
	public Integer getOperatorType(){
		return operatorType;
	}
	
	public void setOperatorType(Integer operatorType){
		this.operatorType = operatorType;
	}
	
	public String getOperatorId(){
		return operatorId;
	}
	
	public void setOperatorId(String operatorId){
		this.operatorId = operatorId;
	}
	
	
	/*Auto generated methods end*/
	
	
	
	/*Customized methods start*/
	
	/*Customized methods end*/
	
	
	@Override
	public String toString() {
		return "CarRecord ["
		 + "recordId = " + recordId + ", carPlateNo = " + carPlateNo + ", carModelId = " + carModelId + ", carModel = " + carModel
		 + ", carBrandId = " + carBrandId + ", carBrand = " + carBrand + ", cityId = " + cityId + ", city = " + city
		 + ", useCarType = " + useCarType + ", documentNo = " + documentNo + ", driverId = " + driverId + ", driverName = " + driverName
		 + ", startParkNo = " + startParkNo + ", startParkName = " + startParkName + ", terminalParkNo = " + terminalParkNo + ", terminalParkName = " + terminalParkName
		 + ", startTime = " + startTime + ", startTimeStart = " + startTimeStart + ", startTimeEnd = " + startTimeEnd + ", finishTime = " + finishTime + ", finishTimeStart = " + finishTimeStart + ", finishTimeEnd = " + finishTimeEnd + ", startPower = " + startPower + ", finishPower = " + finishPower
		 + ", totalMileage = " + totalMileage + ", createTime = " + createTime + ", createTimeStart = " + createTimeStart + ", createTimeEnd = " + createTimeEnd + ", updateTime = " + updateTime + ", updateTimeStart = " + updateTimeStart + ", updateTimeEnd = " + updateTimeEnd + ", operatorType = " + operatorType
		 + ", operatorId = " + operatorId
		+"]";
	}

	public String getCarNo() {
		return carNo;
	}

	public void setCarNo(String carNo) {
		this.carNo = carNo;
	}
}
