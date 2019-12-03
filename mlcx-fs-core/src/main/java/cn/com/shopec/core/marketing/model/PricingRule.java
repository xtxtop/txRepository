package cn.com.shopec.core.marketing.model;

import java.util.Date;

import cn.com.shopec.core.common.Entity;

/** 
 * 计费规则表 数据实体类
 */
public class PricingRule extends Entity<String> {
	
	private static final long serialVersionUID = 1l;
	
	/*Auto generated properties start*/
	
	//计费规则编号
	private String ruleNo;
	//计费规则名称
	private String ruleName;
	//城市id
	private String cityId;
	//城市名称
	private String cityName;
	//车型名称
	private String carModelName;
	//集团id
	private String companyId;
	//集团折扣率
	private Double discount;
	//工作日按时间计费(元/分钟)
	private Double priceOfMinute;
	//工作日按里程计费(元/公里)
	private Double priceOfKm;
	//周末按时间计费(元/分钟)
	private Double priceOfMinuteOrdinary;
	//周末按里程计费(元/公里)
	private Double priceOfKmOrdinary;
	//周末计费封顶
	private Double billingCapPerDayOrdinary;
	//人员服务按时计费(元/分钟)
	private Double servicePriceOfMinute;
	//人员服务按里程计费(元/公里)
	private Double servicePriceOfKm;
	//免费时长
	private Integer freeMinutes;
	//计费封顶（天）
	private Double billingCapPerDay;
	//是否标准计费（0、非标准，1、标准，默认0）
	private Integer isStandardBilling;
	//优先级（数字大的优先）
	private Integer priority;
	//有效日期起
	private Date availableTime1;
	//有效日期起 时间范围起（查询用）
	private Date availableTime1Start;
	//有效日期起 时间范围止（查询用）
	private Date availableTime1End;	
	//有效日期止
	private Date availableTime2;
	//有效日期止 时间范围起（查询用）
	private Date availableTime2Start;
	//有效日期止 时间范围止（查询用）
	private Date availableTime2End;	
	//可用状态（0，不可用、1，可用，默认1）
	private Integer isAvailable;
	//启用、停用原因
	private String  availableMemo;
	//审核状态（0、未审核，1、已审核，2、待审核，3、未通过，默认0）
	private Integer cencorStatus;
	//审核时间
	private Date cencorTime;
	//审核时间 时间范围起（查询用）
	private Date cencorTimeStart;
	//审核时间 时间范围止（查询用）
	private Date cencorTimeEnd;	
	//审核备注
	private String cencorMemo;
	//审核人id
	private String cencorId;
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
	//集团名称
	private String companyName;
	//起步价(元)
	private  Double  baseFee;
	//工作日计费
	private String priceDay;
	//周末计费
	private String priceDayOrdinary;
	//计费规则类型
	private Integer ruleType;
	//不计免赔费
	private Double regardlessFranchise;
	//保险费(元/天)
	private Double insuranceAmount;
	/*Auto generated properties end*/
	//后台校验计费规则名称是否唯一使用
	private Integer flag;
	
	
	/*Customized properties start*/
	
	/*Customized properties end*/
	
	
	
	/*Auto generated methods start*/
	
	@Override
	public String getPK(){
		return ruleNo;
	}
	
	public String getAvailableMemo() {
		return availableMemo;
	}

	public void setAvailableMemo(String availableMemo) {
		this.availableMemo = availableMemo;
	}

	public String getRuleNo(){
		return ruleNo;
	}
	
	public void setRuleNo(String ruleNo){
		this.ruleNo = ruleNo;
	}
	
	public String getRuleName(){
		return ruleName;
	}
	
	public void setRuleName(String ruleName){
		this.ruleName = ruleName;
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
	
	public String getCarModelName() {
		return carModelName;
	}

	public void setCarModelName(String carModelName) {
		this.carModelName = carModelName;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public Double getDiscount() {
		return discount;
	}

	public void setDiscount(Double discount) {
		this.discount = discount;
	}

	public Double getPriceOfMinute(){
		return priceOfMinute;
	}
	
	public void setPriceOfMinute(Double priceOfMinute){
		this.priceOfMinute = priceOfMinute;
	}
	
	public Double getPriceOfKm(){
		return priceOfKm;
	}
	
	public void setPriceOfKm(Double priceOfKm){
		this.priceOfKm = priceOfKm;
	}
	
	public Double getPriceOfMinuteOrdinary() {
		return priceOfMinuteOrdinary;
	}

	public void setPriceOfMinuteOrdinary(Double priceOfMinuteOrdinary) {
		this.priceOfMinuteOrdinary = priceOfMinuteOrdinary;
	}

	public Double getPriceOfKmOrdinary() {
		return priceOfKmOrdinary;
	}

	public void setPriceOfKmOrdinary(Double priceOfKmOrdinary) {
		this.priceOfKmOrdinary = priceOfKmOrdinary;
	}

	public Double getBillingCapPerDayOrdinary() {
		return billingCapPerDayOrdinary;
	}

	public void setBillingCapPerDayOrdinary(Double billingCapPerDayOrdinary) {
		this.billingCapPerDayOrdinary = billingCapPerDayOrdinary;
	}

	public Double getServicePriceOfMinute(){
		return servicePriceOfMinute;
	}
	
	public void setServicePriceOfMinute(Double servicePriceOfMinute){
		this.servicePriceOfMinute = servicePriceOfMinute;
	}
	
	public Double getServicePriceOfKm(){
		return servicePriceOfKm;
	}
	
	public void setServicePriceOfKm(Double servicePriceOfKm){
		this.servicePriceOfKm = servicePriceOfKm;
	}
	
	public Integer getFreeMinutes() {
		return freeMinutes;
	}

	public void setFreeMinutes(Integer freeMinutes) {
		this.freeMinutes = freeMinutes;
	}

	public Double getBillingCapPerDay(){
		return billingCapPerDay;
	}
	
	public void setBillingCapPerDay(Double billingCapPerDay){
		this.billingCapPerDay = billingCapPerDay;
	}
	
	public Integer getIsStandardBilling(){
		return isStandardBilling;
	}
	
	public void setIsStandardBilling(Integer isStandardBilling){
		this.isStandardBilling = isStandardBilling;
	}
	
	public Integer getPriority(){
		return priority;
	}
	
	public void setPriority(Integer priority){
		this.priority = priority;
	}
	
	public Date getAvailableTime1(){
		return availableTime1;
	}
	
	public void setAvailableTime1(Date availableTime1){
		this.availableTime1 = availableTime1;
	}
	
	public Date getAvailableTime1Start(){
		return availableTime1Start;
	}
	
	public void setAvailableTime1Start(Date availableTime1Start){
		this.availableTime1Start = availableTime1Start;
	}
	
	public Date getAvailableTime1End(){
		return availableTime1End;
	}
	
	public void setAvailableTime1End(Date availableTime1End){
		this.availableTime1End = availableTime1End;
	}	
	
	public Date getAvailableTime2(){
		return availableTime2;
	}
	
	public void setAvailableTime2(Date availableTime2){
		this.availableTime2 = availableTime2;
	}
	
	public Date getAvailableTime2Start(){
		return availableTime2Start;
	}
	
	public void setAvailableTime2Start(Date availableTime2Start){
		this.availableTime2Start = availableTime2Start;
	}
	
	public Date getAvailableTime2End(){
		return availableTime2End;
	}
	
	public void setAvailableTime2End(Date availableTime2End){
		this.availableTime2End = availableTime2End;
	}	
	
	public Integer getIsAvailable(){
		return isAvailable;
	}
	
	public void setIsAvailable(Integer isAvailable){
		this.isAvailable = isAvailable;
	}
	
	public Integer getCencorStatus(){
		return cencorStatus;
	}
	
	public void setCencorStatus(Integer cencorStatus){
		this.cencorStatus = cencorStatus;
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

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	@Override
	public String toString() {
		return "PricingRule [ruleNo=" + ruleNo + ", ruleName=" + ruleName + ", cityId=" + cityId + ", cityName="
				+ cityName + ", carModelName=" + carModelName + ", companyId=" + companyId + ", discount=" + discount
				+ ", priceOfMinute=" + priceOfMinute + ", priceOfKm=" + priceOfKm + ", servicePriceOfMinute="
				+ servicePriceOfMinute + ", servicePriceOfKm=" + servicePriceOfKm + ", freeMinutes=" + freeMinutes
				+ ", billingCapPerDay=" + billingCapPerDay + ", isStandardBilling=" + isStandardBilling + ", priority="
				+ priority + ", availableTime1=" + availableTime1 + ", availableTime1Start=" + availableTime1Start
				+ ", availableTime1End=" + availableTime1End + ", availableTime2=" + availableTime2
				+ ", availableTime2Start=" + availableTime2Start + ", availableTime2End=" + availableTime2End
				+ ", isAvailable=" + isAvailable + ", availableMemo=" + availableMemo + ", cencorStatus=" + cencorStatus
				+ ", cencorTime=" + cencorTime + ", cencorTimeStart=" + cencorTimeStart + ", cencorTimeEnd="
				+ cencorTimeEnd + ", cencorMemo=" + cencorMemo + ", cencorId=" + cencorId + ", createTime=" + createTime
				+ ", createTimeStart=" + createTimeStart + ", createTimeEnd=" + createTimeEnd + ", updateTime="
				+ updateTime + ", updateTimeStart=" + updateTimeStart + ", updateTimeEnd=" + updateTimeEnd
				+ ", operatorType=" + operatorType + ", operatorId=" + operatorId + ", companyName=" + companyName
				+ "]";
	}

	public Double getBaseFee() {
		return baseFee;
	}

	public void setBaseFee(Double baseFee) {
		this.baseFee = baseFee;
	}

	public String getPriceDay() {
		return priceDay;
	}

	public void setPriceDay(String priceDay) {
		this.priceDay = priceDay;
	}

	public String getPriceDayOrdinary() {
		return priceDayOrdinary;
	}

	public void setPriceDayOrdinary(String priceDayOrdinary) {
		this.priceDayOrdinary = priceDayOrdinary;
	}

	public Integer getRuleType() {
		return ruleType;
	}

	public void setRuleType(Integer ruleType) {
		this.ruleType = ruleType;
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

	public Integer getFlag() {
		return flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}
	
	
	/*Auto generated methods end*/
	
	
	
	/*Customized methods start*/
	
	/*Customized methods end*/
	
	
	
}
