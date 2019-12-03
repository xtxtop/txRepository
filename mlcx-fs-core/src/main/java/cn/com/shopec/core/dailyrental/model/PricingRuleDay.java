package cn.com.shopec.core.dailyrental.model;

import java.util.Date;

import cn.com.shopec.core.common.Entity;

/** 
 * 计费规则表 数据实体类
 */
public class PricingRuleDay extends Entity<String> {
	
	private static final long serialVersionUID = 1l;
	
	/*Auto generated properties start*/
	
	//计费规则id
	private String ruleId;
	//计费规则名称
	private String ruleName;
	//车辆类型Id
	private String carModelId;
	//车辆类型名称
	private String carModelName;
	//适用类型(1、经济型，2、商务型，3、豪华型，4、6至15座商务，5、SUV。)
	private Integer carType;
	//城市id（0为全国统一价）
	private String cityId;
	//城市名称
	private String cityName;
	//区域id
	private String regionId;
	//工作日计费（元/天）
	private Double priceOfDay;
	//周末计费（元/天）
	private Double priceOfDayOrdinary;
	//是否可用（0，不可用1、可用）
	private Integer isAvailable;
	//AVAILABLE_MEMO
	private String availableMemo;
	//审核状态（0、未审核，1、已审核，2、待审核，3、未通过，默认0）
	private Integer cencorStatus;
	//审核人id
	private String cencorId;
	//创建时间
	private Date createTime;
	//创建时间 时间范围起（查询用）
	private Date createTimeStart;
	//创建时间 时间范围止（查询用）
	private Date createTimeEnd;	
	//审核时间
	private Date cencorTime;
	//审核时间 时间范围起（查询用）
	private Date cencorTimeStart;
	//审核时间 时间范围止（查询用）
	private Date cencorTimeEnd;	
	//审核备注
	private String cencorMemo;
	//不计免赔费(元/天)
	private Double regardlessFranchise;
	//保险费(元/天)
	private Double insuranceAmount;
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
		return ruleId;
	}
	
	public String getRuleId(){
		return ruleId;
	}
	
	public void setRuleId(String ruleId){
		this.ruleId = ruleId;
	}
	
	public String getRuleName(){
		return ruleName;
	}
	
	public void setRuleName(String ruleName){
		this.ruleName = ruleName;
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
	
	public Integer getCarType() {
		return carType;
	}

	public void setCarType(Integer carType) {
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
	
	public String getRegionId(){
		return regionId;
	}
	
	public void setRegionId(String regionId){
		this.regionId = regionId;
	}
	
	public Double getPriceOfDay(){
		return priceOfDay;
	}
	
	public void setPriceOfDay(Double priceOfDay){
		this.priceOfDay = priceOfDay;
	}
	
	public Double getPriceOfDayOrdinary(){
		return priceOfDayOrdinary;
	}
	
	public void setPriceOfDayOrdinary(Double priceOfDayOrdinary){
		this.priceOfDayOrdinary = priceOfDayOrdinary;
	}
	
	public Integer getIsAvailable(){
		return isAvailable;
	}
	
	public void setIsAvailable(Integer isAvailable){
		this.isAvailable = isAvailable;
	}
	
	public String getAvailableMemo(){
		return availableMemo;
	}
	
	public void setAvailableMemo(String availableMemo){
		this.availableMemo = availableMemo;
	}
	
	public Integer getCencorStatus(){
		return cencorStatus;
	}
	
	public void setCencorStatus(Integer cencorStatus){
		this.cencorStatus = cencorStatus;
	}
	
	public String getCencorId(){
		return cencorId;
	}
	
	public void setCencorId(String cencorId){
		this.cencorId = cencorId;
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
	
	public Date getCencorTime(){
		return cencorTime;
	}
	
	public void setCencorTime(Date cencorTime){
		this.cencorTime = cencorTime;
	}
	
	public Date getCencorTimeStart(){
		return cencorTimeStart;
	}
	
	public void setCencorTimeStart(Date cencorTimeStart){
		this.cencorTimeStart = cencorTimeStart;
	}
	
	public Date getCencorTimeEnd(){
		return cencorTimeEnd;
	}
	
	public void setCencorTimeEnd(Date cencorTimeEnd){
		this.cencorTimeEnd = cencorTimeEnd;
	}	
	
	public String getCencorMemo(){
		return cencorMemo;
	}
	
	public void setCencorMemo(String cencorMemo){
		this.cencorMemo = cencorMemo;
	}
	
	public Double getRegardlessFranchise() {
		return regardlessFranchise;
	}

	public void setRegardlessFranchise(Double regardlessFranchise) {
		this.regardlessFranchise = regardlessFranchise;
	}

	public Double getInsuranceAmount() {
		return insuranceAmount;
	}

	public void setInsuranceAmount(Double insuranceAmount) {
		this.insuranceAmount = insuranceAmount;
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
		return "PricingRuleDay ["
		 + "ruleId = " + ruleId + ", ruleName = " + ruleName + ", carModelId = " + carModelId + ", carModelName = " + carModelName
		 + ", cityId = " + cityId + ", cityName = " + cityName + ", regionId = " + regionId + ", priceOfDay = " + priceOfDay
		 + ", priceOfDayOrdinary = " + priceOfDayOrdinary + ", isAvailable = " + isAvailable + ", availableMemo = " + availableMemo + ", cencorStatus = " + cencorStatus
		 + ", cencorId = " + cencorId + ", createTime = " + createTime + ", createTimeStart = " + createTimeStart + ", createTimeEnd = " + createTimeEnd + ", cencorTime = " + cencorTime + ", cencorTimeStart = " + cencorTimeStart + ", cencorTimeEnd = " + cencorTimeEnd + ", cencorMemo = " + cencorMemo
		 + ", updateTime = " + updateTime + ", updateTimeStart = " + updateTimeStart + ", updateTimeEnd = " + updateTimeEnd + ", operatorId = " + operatorId + ", operatorType = " + operatorType
		+"]";
	}
}
