package cn.com.shopec.core.car.model;

import java.util.Date;

import cn.com.shopec.core.common.Entity;

/** 
 * CarAccident 数据实体类
 */
public class CarAccident extends Entity<String> {
	
	private static final long serialVersionUID = 1l;
	
	/*Auto generated properties start*/
	
	//事故id
	private String accidentId;
	//车辆编号
	private String carNo;
	//车牌号
	private String carPlateNo;
	//车辆品牌id
	private String carBrandId;
	//车辆品牌
	private String carBrandName;
	//车辆型号id（具体见数据字典表）
	private String carModelId;
	//车辆型号名称
	private String carModelName;
	//城市id（具体见数据字典表）
	private String cityId;
	//城市名称
	private String cityName;
	//事故地点
	private String accidentLocation;
	//保险公司
	private String insuranceCompany;
	//保险进度
	private Integer accidentStatus;
	//记录事故时间
	private Date recordAccidentTime;
	//记录事故时间 时间范围起（查询用）
	private Date recordAccidentTimeStart;
	//记录事故时间 时间范围止（查询用）
	private Date recordAccidentTimeEnd;	
	//事故等级
	private Integer accidentLevel;
	//事故详情
	private String accidentDetail;
	//用车类型（1、订单用车，2、调度用车）
	private Integer useCarType;
	//单据号
	private String documentNo;
	//用车人id
	private String driverId;
	//用车人姓名
	private String driverName;
	//备注
	private String memo;
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
	
	/*Auto generated properties end*/
	
	
	
	/*Customized properties start*/
	
	/*Customized properties end*/
	
	
	
	/*Auto generated methods start*/
	
	@Override
	public String getPK(){
		return accidentId;
	}
	
	public String getAccidentId(){
		return accidentId;
	}
	
	public void setAccidentId(String accidentId){
		this.accidentId = accidentId;
	}
	
	public String getCarNo(){
		return carNo;
	}
	
	public void setCarNo(String carNo){
		this.carNo = carNo;
	}
	
	public String getCarPlateNo(){
		return carPlateNo;
	}
	
	public void setCarPlateNo(String carPlateNo){
		this.carPlateNo = carPlateNo;
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
	
	public String getAccidentLocation(){
		return accidentLocation;
	}
	
	public void setAccidentLocation(String accidentLocation){
		this.accidentLocation = accidentLocation;
	}
	
	public String getInsuranceCompany(){
		return insuranceCompany;
	}
	
	public void setInsuranceCompany(String insuranceCompany){
		this.insuranceCompany = insuranceCompany;
	}
	
	public Integer getAccidentStatus(){
		return accidentStatus;
	}
	
	public void setAccidentStatus(Integer accidentStatus){
		this.accidentStatus = accidentStatus;
	}
	
	public Date getRecordAccidentTime(){
		return recordAccidentTime;
	}
	
	public void setRecordAccidentTime(Date recordAccidentTime){
		this.recordAccidentTime = recordAccidentTime;
	}
	
	public Date getRecordAccidentTimeStart(){
		return recordAccidentTimeStart;
	}
	
	public void setRecordAccidentTimeStart(Date recordAccidentTimeStart){
		this.recordAccidentTimeStart = recordAccidentTimeStart;
	}
	
	public Date getRecordAccidentTimeEnd(){
		return recordAccidentTimeEnd;
	}
	
	public void setRecordAccidentTimeEnd(Date recordAccidentTimeEnd){
		this.recordAccidentTimeEnd = recordAccidentTimeEnd;
	}	
	
	public Integer getAccidentLevel(){
		return accidentLevel;
	}
	
	public void setAccidentLevel(Integer accidentLevel){
		this.accidentLevel = accidentLevel;
	}
	
	public String getAccidentDetail(){
		return accidentDetail;
	}
	
	public void setAccidentDetail(String accidentDetail){
		this.accidentDetail = accidentDetail;
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
	
	public String getMemo(){
		return memo;
	}
	
	public void setMemo(String memo){
		this.memo = memo;
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
		return "CarAccident ["
		 + "accidentId = " + accidentId + ", carNo = " + carNo + ", carPlateNo = " + carPlateNo + ", carBrandId = " + carBrandId
		 + ", carBrandName = " + carBrandName + ", carModelId = " + carModelId + ", carModelName = " + carModelName + ", cityId = " + cityId
		 + ", cityName = " + cityName + ", accidentLocation = " + accidentLocation + ", insuranceCompany = " + insuranceCompany + ", accidentStatus = " + accidentStatus
		 + ", recordAccidentTime = " + recordAccidentTime + ", recordAccidentTimeStart = " + recordAccidentTimeStart + ", recordAccidentTimeEnd = " + recordAccidentTimeEnd + ", accidentLevel = " + accidentLevel + ", accidentDetail = " + accidentDetail + ", useCarType = " + useCarType
		 + ", documentNo = " + documentNo + ", driverId = " + driverId + ", driverName = " + driverName + ", memo = " + memo
		 + ", createTime = " + createTime + ", createTimeStart = " + createTimeStart + ", createTimeEnd = " + createTimeEnd + ", updateTime = " + updateTime + ", updateTimeStart = " + updateTimeStart + ", updateTimeEnd = " + updateTimeEnd + ", operatorType = " + operatorType + ", operatorId = " + operatorId
		+"]";
	}
}
