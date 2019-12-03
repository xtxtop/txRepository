package cn.com.shopec.core.dailyrental.model;

import java.util.Date;

import cn.com.shopec.core.common.Entity;

/** 
 * 自定义日期价格表 数据实体类
 */
public class PricingRuleCustomDate extends Entity<String> {
	
	private static final long serialVersionUID = 1l;
	
	/*Auto generated properties start*/
	
	//自定义日期id
	private String customizedId;
	//计费规则id
	private String ruleId;
	//计费规则名称
	private String ruleName;
	//城市名称
	private String cityName;
	//车型
	private String carModelName;
	//自定义日期
	private Date customizedDate;
	//自定义日期 时间范围起（查询用）
	private Date customizedDateStart;
	//自定义日期 时间范围止（查询用）
	private Date customizedDateEnd;	
	//自定义日期价格(元/天)
	private Double priceOfDayCustomized;
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
	private String customizedDateStr;
	/*Auto generated properties end*/
	
	
	
	/*Customized properties start*/
	
	/*Customized properties end*/
	
	
	
	/*Auto generated methods start*/
	
	@Override
	public String getPK(){
		return customizedId;
	}
	
	public String getCustomizedId(){
		return customizedId;
	}
	
	public void setCustomizedId(String customizedId){
		this.customizedId = customizedId;
	}
	
	public String getRuleId(){
		return ruleId;
	}
	
	public void setRuleId(String ruleId){
		this.ruleId = ruleId;
	}
	
	public String getRuleName() {
		return ruleName;
	}

	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getCarModelName() {
		return carModelName;
	}

	public void setCarModelName(String carModelName) {
		this.carModelName = carModelName;
	}

	public Date getCustomizedDate(){
		return customizedDate;
	}
	
	public void setCustomizedDate(Date customizedDate){
		this.customizedDate = customizedDate;
	}
	
	public Date getCustomizedDateStart(){
		return customizedDateStart;
	}
	
	public void setCustomizedDateStart(Date customizedDateStart){
		this.customizedDateStart = customizedDateStart;
	}
	
	public Date getCustomizedDateEnd(){
		return customizedDateEnd;
	}
	
	public void setCustomizedDateEnd(Date customizedDateEnd){
		this.customizedDateEnd = customizedDateEnd;
	}	
	
	public Double getPriceOfDayCustomized(){
		return priceOfDayCustomized;
	}
	
	public void setPriceOfDayCustomized(Double priceOfDayCustomized){
		this.priceOfDayCustomized = priceOfDayCustomized;
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
	
	
	public String getCustomizedDateStr() {
		return customizedDateStr;
	}

	public void setCustomizedDateStr(String customizedDateStr) {
		this.customizedDateStr = customizedDateStr;
	}

	@Override
	public String toString() {
		return "PricingRuleCustomDate ["
		 + "customizedId = " + customizedId + ", ruleId = " + ruleId + ", customizedDate = " + customizedDate + ", customizedDateStart = " + customizedDateStart + ", customizedDateEnd = " + customizedDateEnd + ", priceOfDayCustomized = " + priceOfDayCustomized
		 + ", createTime = " + createTime + ", createTimeStart = " + createTimeStart + ", createTimeEnd = " + createTimeEnd + ", updateTime = " + updateTime + ", updateTimeStart = " + updateTimeStart + ", updateTimeEnd = " + updateTimeEnd + ", operatorId = " + operatorId + ", operatorType = " + operatorType
		+"]";
	}
}
