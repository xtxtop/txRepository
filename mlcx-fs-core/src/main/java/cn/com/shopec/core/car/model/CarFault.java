package cn.com.shopec.core.car.model;

import java.util.Date;

import cn.com.shopec.core.common.Entity;

/** 
 * CarFault 数据实体类
 */
public class CarFault extends Entity<String> {
	
	private static final long serialVersionUID = 1l;
	
	/*Auto generated properties start*/
	
	//故障id
	private String faultId;
	//车辆编号
	private String carNo;
	//车牌号
	private String carPlateNo;
	//车辆型号id（具体来自数据字典）
	private String carModelId;
	//车辆型号名称
	private String carModelName;
	//城市id（具体来自数据字典）
	private String cityId;
	private String cityName;
	//记录故障时间
	private Date recordFaultTime;
	//记录故障时间 时间范围起（查询用）
	private Date recordFaultTimeStart;
	//记录故障时间 时间范围止（查询用）
	private Date recordFaultTimeEnd;	
	//故障地点
	private String faultLocation;
	//故障等级 0一级 1二级 2三级
	private Integer faultLevel;
	//处理状态（0、未处理，1、处理中，2、已处理，默认0）
	private Integer processingStatus;
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
		return faultId;
	}
	
	public String getFaultId(){
		return faultId;
	}
	
	public void setFaultId(String faultId){
		this.faultId = faultId;
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
	
	public Date getRecordFaultTime(){
		return recordFaultTime;
	}
	
	public void setRecordFaultTime(Date recordFaultTime){
		this.recordFaultTime = recordFaultTime;
	}
	
	public Date getRecordFaultTimeStart(){
		return recordFaultTimeStart;
	}
	
	public void setRecordFaultTimeStart(Date recordFaultTimeStart){
		this.recordFaultTimeStart = recordFaultTimeStart;
	}
	
	public Date getRecordFaultTimeEnd(){
		return recordFaultTimeEnd;
	}
	
	public void setRecordFaultTimeEnd(Date recordFaultTimeEnd){
		this.recordFaultTimeEnd = recordFaultTimeEnd;
	}	
	
	public String getFaultLocation(){
		return faultLocation;
	}
	
	public void setFaultLocation(String faultLocation){
		this.faultLocation = faultLocation;
	}
	
	public Integer getFaultLevel(){
		return faultLevel;
	}
	
	public void setFaultLevel(Integer faultLevel){
		this.faultLevel = faultLevel;
	}
	
	public Integer getProcessingStatus(){
		return processingStatus;
	}
	
	public void setProcessingStatus(Integer processingStatus){
		this.processingStatus = processingStatus;
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
		return "CarFault ["
		 + "faultId = " + faultId + ", carNo = " + carNo + ", carPlateNo = " + carPlateNo + ", carModelId = " + carModelId
		 + ", carModelName = " + carModelName + ", cityId = " + cityId + ", cityName = " + cityName + ", recordFaultTime = " + recordFaultTime + ", recordFaultTimeStart = " + recordFaultTimeStart + ", recordFaultTimeEnd = " + recordFaultTimeEnd
		 + ", faultLocation = " + faultLocation + ", faultLevel = " + faultLevel + ", processingStatus = " + processingStatus + ", useCarType = " + useCarType
		 + ", documentNo = " + documentNo + ", driverId = " + driverId + ", driverName = " + driverName + ", memo = " + memo
		 + ", createTime = " + createTime + ", createTimeStart = " + createTimeStart + ", createTimeEnd = " + createTimeEnd + ", updateTime = " + updateTime + ", updateTimeStart = " + updateTimeStart + ", updateTimeEnd = " + updateTimeEnd + ", operatorType = " + operatorType + ", operatorId = " + operatorId
		+"]";
	}
}
