package cn.com.shopec.core.car.model;

import java.util.Date;

import cn.com.shopec.core.common.Entity;

/** 
 * CarIllegal 数据实体类
 */
public class CarIllegal extends Entity<String> {
	
	private static final long serialVersionUID = 1l;
	
	/*Auto generated properties start*/
	
	//违章ID
	private String illegalId;
	//车辆编号
	private String carNo;
	//车牌号
	private String carPlateNo;
	//车辆型号id（具体根据数据字典表而定）
	private String carModelId;
	//车辆型号名称
	private String carModelName;
	//车辆品牌id
	private String carBrandId;
	//车辆品牌名称
	private String carBrandName;
	//城市id（具体根据数据字典表而定）
	private String cityId;
	//城市名称
	private String cityName;
	//违章时间
	private Date illegalTime;
	//违章时间 时间范围起（查询用）
	private Date illegalTimeStart;
	//违章时间 时间范围止（查询用）
	private Date illegalTimeEnd;	
	//违章地点
	private String illegalLocation;
	//违章类型 (1、未系安全带2、压禁止标线 3、违停4、闯红灯5、不服从指挥6、超速行驶7、未设警告标志8、未停车让行9、未保持车距10、未按道行驶)
	private String illegalType;
	//处理机构
	private String processingAgency;
	//违章内容
	private String illegalDetail;
	//罚款金额
	private Double illegalFines;
	//处理状态（0、未处理，1、处理中，2、已处理，默认0）
	private Integer processingStatus;
	//处理状态（0、未处理，1、处理中，2、已处理，默认0）
	//扣分
	private Integer pointsDeduction;
	//缴纳状态（0、未缴款，1、已缴款，默认0）
	private Integer paymentStatus;
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
		return illegalId;
	}
	
	public String getIllegalId(){
		return illegalId;
	}
	
	public void setIllegalId(String illegalId){
		this.illegalId = illegalId;
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
	
	public Date getIllegalTime(){
		return illegalTime;
	}
	
	public void setIllegalTime(Date illegalTime){
		this.illegalTime = illegalTime;
	}
	
	public Date getIllegalTimeStart(){
		return illegalTimeStart;
	}
	
	public void setIllegalTimeStart(Date illegalTimeStart){
		this.illegalTimeStart = illegalTimeStart;
	}
	
	public Date getIllegalTimeEnd(){
		return illegalTimeEnd;
	}
	
	public void setIllegalTimeEnd(Date illegalTimeEnd){
		this.illegalTimeEnd = illegalTimeEnd;
	}	
	
	public String getIllegalLocation(){
		return illegalLocation;
	}
	
	public void setIllegalLocation(String illegalLocation){
		this.illegalLocation = illegalLocation;
	}
	
	public String getIllegalType(){
		return illegalType;
	}
	
	public void setIllegalType(String illegalType){
		this.illegalType = illegalType;
	}
	
	public String getProcessingAgency(){
		return processingAgency;
	}
	
	public void setProcessingAgency(String processingAgency){
		this.processingAgency = processingAgency;
	}
	
	public Double getIllegalFines(){
		return illegalFines;
	}
	
	public void setIllegalFines(Double illegalFines){
		this.illegalFines = illegalFines;
	}
	
	public Integer getProcessingStatus(){
		return processingStatus;
	}
	
	public void setProcessingStatus(Integer processingStatus){
		this.processingStatus = processingStatus;
	}
	
	public Integer getPaymentStatus(){
		return paymentStatus;
	}
	
	public void setPaymentStatus(Integer paymentStatus){
		this.paymentStatus = paymentStatus;
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
	
	
	public Integer getPointsDeduction() {
		return pointsDeduction;
	}

	public void setPointsDeduction(Integer pointsDeduction) {
		this.pointsDeduction = pointsDeduction;
	}

	@Override
	public String toString() {
		return "CarIllegal ["
		 + "illegalId = " + illegalId + ", carNo = " + carNo + ", carPlateNo = " + carPlateNo + ", carModelId = " + carModelId
		 + ", carModelName = " + carModelName + ", carBrandId = " + carBrandId + ", carBrandName = " + carBrandName + ", cityId = " + cityId
		 + ", cityName = " + cityName + ", illegalTime = " + illegalTime + ", illegalTimeStart = " + illegalTimeStart + ", illegalTimeEnd = " + illegalTimeEnd + ", illegalLocation = " + illegalLocation + ", illegalType = " + illegalType
		 + ", processingAgency = " + processingAgency + ", illegalFines = " + illegalFines + ", processingStatus = " + processingStatus + ", paymentStatus = " + paymentStatus
		 + ", useCarType = " + useCarType + ", documentNo = " + documentNo + ", driverId = " + driverId + ", driverName = " + driverName
		 + ", memo = " + memo + ", createTime = " + createTime + ", createTimeStart = " + createTimeStart + ", createTimeEnd = " + createTimeEnd + ", updateTime = " + updateTime + ", updateTimeStart = " + updateTimeStart + ", updateTimeEnd = " + updateTimeEnd + ", operatorType = " + operatorType
		 + ", operatorId = " + operatorId  + ", pointsDeduction = " + pointsDeduction
		+"]";
	}

	public String getIllegalDetail() {
		return illegalDetail;
	}

	public void setIllegalDetail(String illegalDetail) {
		this.illegalDetail = illegalDetail;
	}
}
