package cn.com.shopec.core.marketing.model;

import java.util.Date;

import cn.com.shopec.core.common.Entity;

/** 
 * PricingRuleCustomizedDate 数据实体类
 */
public class PricingRuleCustomizedDate extends Entity<String> {
	
	private static final long serialVersionUID = 1l;
	
	/*Auto generated properties start*/
	
	//自定义日期id
	private String customizedId;
	//计费规则编号
	private String ruleNo;
	//城市id
	private String cityId;
	//城市名称
	private String cityName;
	//车型名称
	private String carModelName;
	//所属集团编号
	private String companyId;
	//自定义日期
	private Date customizedDate;
	//自定义日期 时间范围起（查询用）
	private Date customizedDateStart;
	//自定义日期 时间范围止（查询用）
	private Date customizedDateEnd;	
	//自定义日期按时间计费(元/分钟)
	private Double priceOfMinuteCustomized;
	//自定义日期按公里计费(元/公里)
	private Double priceOfKmCustomized;
	//自定义日期计费封顶（元/天）
	private Double billingCapPerDayCustomized;
	//创建日期
	private Date createTime;
	//创建日期 时间范围起（查询用）
	private Date createTimeStart;
	//创建日期 时间范围止（查询用）
	private Date createTimeEnd;	
	//修改日期
	private Date updateTime;
	//修改日期 时间范围起（查询用）
	private Date updateTimeStart;
	//修改日期 时间范围止（查询用）
	private Date updateTimeEnd;	
	//操作人类型
	private Integer operatorType;
	//操作人id
	private String operatorId;
	//自定义日期（供传值使用）
	private String customizedDateStr;
	//过期或不过期贴换
	private Integer isOverdue;
	
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
	
	public String getRuleNo(){
		return ruleNo;
	}
	
	public void setRuleNo(String ruleNo){
		this.ruleNo = ruleNo;
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
	
	public String getCarModelName(){
		return carModelName;
	}
	
	public void setCarModelName(String carModelName){
		this.carModelName = carModelName;
	}
	
	public String getCompanyId(){
		return companyId;
	}
	
	public void setCompanyId(String companyId){
		this.companyId = companyId;
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
	
	public Double getPriceOfMinuteCustomized(){
		return priceOfMinuteCustomized;
	}
	
	public void setPriceOfMinuteCustomized(Double priceOfMinuteCustomized){
		this.priceOfMinuteCustomized = priceOfMinuteCustomized;
	}
	
	public Double getPriceOfKmCustomized(){
		return priceOfKmCustomized;
	}
	
	public void setPriceOfKmCustomized(Double priceOfKmCustomized){
		this.priceOfKmCustomized = priceOfKmCustomized;
	}
	
	public Double getBillingCapPerDayCustomized(){
		return billingCapPerDayCustomized;
	}
	
	public void setBillingCapPerDayCustomized(Double billingCapPerDayCustomized){
		this.billingCapPerDayCustomized = billingCapPerDayCustomized;
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
	
	
	public String getCustomizedDateStr() {
		return customizedDateStr;
	}

	public void setCustomizedDateStr(String customizedDateStr) {
		this.customizedDateStr = customizedDateStr;
	}

	@Override
	public String toString() {
		return "PricingRuleCustomizedDate ["
		 + "customizedId = " + customizedId + ", ruleNo = " + ruleNo + ", cityId = " + cityId + ", cityName = " + cityName
		 + ", carModelName = " + carModelName + ", companyId = " + companyId + ", customizedDate = " + customizedDate + ", customizedDateStart = " + customizedDateStart + ", customizedDateEnd = " + customizedDateEnd + ", priceOfMinuteCustomized = " + priceOfMinuteCustomized
		 + ", priceOfKmCustomized = " + priceOfKmCustomized + ", billingCapPerDayCustomized = " + billingCapPerDayCustomized + ", createTime = " + createTime + ", createTimeStart = " + createTimeStart + ", createTimeEnd = " + createTimeEnd + ", updateTime = " + updateTime + ", updateTimeStart = " + updateTimeStart + ", updateTimeEnd = " + updateTimeEnd
		 + ", operatorType = " + operatorType + ", operatorId = " + operatorId
		+"]";
	}

	public Integer getIsOverdue() {
		return isOverdue;
	}

	public void setIsOverdue(Integer isOverdue) {
		this.isOverdue = isOverdue;
	}
}
