package cn.com.shopec.core.ml.model;

import java.util.Date;

import cn.com.shopec.core.common.Entity;

/** 
 * 计费方案 数据实体类
 */
public class BillingScheme extends Entity<String> {
	
	private static final long serialVersionUID = 1l;
	
	/*Auto generated properties start*/
	
	//计费方案编号
	private String schemeNo;
	//计费方案版本
	private String schemeVersions;
	//计费方案名称
	private String schemeName;
	//操作人id
	private String operatorId;
	//操作人类型（0、系统自动操作，1、平台人员操作）
	private Integer operatorType;
	//登记日期
	private Date createTime;
	//登记日期 时间范围起（查询用）
	private Date createTimeStart;
	//登记日期 时间范围止（查询用）
	private Date createTimeEnd;	
	//更新日期
	private Date updateTime;
	//更新日期 时间范围起（查询用）
	private Date updateTimeStart;
	//更新日期 时间范围止（查询用）
	private Date updateTimeEnd;	
	//生效时间
	private Date effectiveDate;
	//生效时间 时间范围起（查询用）
	private Date effectiveDateStart;
	//生效时间 时间范围止（查询用）
	private Date effectiveDateEnd;	
	//失效时间
	private Date invalidDate;
	//失效时间 时间范围起（查询用）
	private Date invalidDateStart;
	//失效时间 时间范围止（查询用）
	private Date invalidDateEnd;	
	//预冻结金额
	private Double advanceFrozenMoney;
	//最小冻结金额
	private Double minFrozenMoney;
	//时间段
	private String timeNum;
	//尖时段电价
	private Double opintPrice;
	//峰时段电价
	private Double peakPrice;
	//平时段电价
	private Double flatPrice;
	//谷时段电价
	private Double valleyPrice;
	//预约费率
	private Double orderedRate;
	//服务费
	private Double serviceCharge;
	//告警金额
	private Double warmingPrice;
	//状态(0无效1有效)
	private Integer status;
	
	/*Auto generated properties end*/
	
	
	
	/*Customized properties start*/
	
	/*Customized properties end*/
	
	
	
	/*Auto generated methods start*/
	
	@Override
	public String getPK(){
		return schemeNo;
	}
	
	public String getSchemeNo(){
		return schemeNo;
	}
	
	public void setSchemeNo(String schemeNo){
		this.schemeNo = schemeNo;
	}
	
	public String getSchemeVersions(){
		return schemeVersions;
	}
	
	public void setSchemeVersions(String schemeVersions){
		this.schemeVersions = schemeVersions;
	}
	
	public String getSchemeName(){
		return schemeName;
	}
	
	public void setSchemeName(String schemeName){
		this.schemeName = schemeName;
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
	
	public Date getEffectiveDate(){
		return effectiveDate;
	}
	
	public void setEffectiveDate(Date effectiveDate){
		this.effectiveDate = effectiveDate;
	}
	
	public Date getEffectiveDateStart(){
		return effectiveDateStart;
	}
	
	public void setEffectiveDateStart(Date effectiveDateStart){
		this.effectiveDateStart = effectiveDateStart;
	}
	
	public Date getEffectiveDateEnd(){
		return effectiveDateEnd;
	}
	
	public void setEffectiveDateEnd(Date effectiveDateEnd){
		this.effectiveDateEnd = effectiveDateEnd;
	}	
	
	public Date getInvalidDate(){
		return invalidDate;
	}
	
	public void setInvalidDate(Date invalidDate){
		this.invalidDate = invalidDate;
	}
	
	public Date getInvalidDateStart(){
		return invalidDateStart;
	}
	
	public void setInvalidDateStart(Date invalidDateStart){
		this.invalidDateStart = invalidDateStart;
	}
	
	public Date getInvalidDateEnd(){
		return invalidDateEnd;
	}
	
	public void setInvalidDateEnd(Date invalidDateEnd){
		this.invalidDateEnd = invalidDateEnd;
	}	
	
	public Double getAdvanceFrozenMoney(){
		return advanceFrozenMoney;
	}
	
	public void setAdvanceFrozenMoney(Double advanceFrozenMoney){
		this.advanceFrozenMoney = advanceFrozenMoney;
	}
	
	public Double getMinFrozenMoney(){
		return minFrozenMoney;
	}
	
	public void setMinFrozenMoney(Double minFrozenMoney){
		this.minFrozenMoney = minFrozenMoney;
	}
	
	public String getTimeNum(){
		return timeNum;
	}
	
	public void setTimeNum(String timeNum){
		this.timeNum = timeNum;
	}
	
	public Double getOpintPrice(){
		return opintPrice;
	}
	
	public void setOpintPrice(Double opintPrice){
		this.opintPrice = opintPrice;
	}
	
	public Double getPeakPrice(){
		return peakPrice;
	}
	
	public void setPeakPrice(Double peakPrice){
		this.peakPrice = peakPrice;
	}
	
	public Double getFlatPrice(){
		return flatPrice;
	}
	
	public void setFlatPrice(Double flatPrice){
		this.flatPrice = flatPrice;
	}
	
	public Double getValleyPrice(){
		return valleyPrice;
	}
	
	public void setValleyPrice(Double valleyPrice){
		this.valleyPrice = valleyPrice;
	}
	
	public Double getOrderedRate(){
		return orderedRate;
	}
	
	public void setOrderedRate(Double orderedRate){
		this.orderedRate = orderedRate;
	}
	
	public Double getServiceCharge(){
		return serviceCharge;
	}
	
	public void setServiceCharge(Double serviceCharge){
		this.serviceCharge = serviceCharge;
	}
	
	public Double getWarmingPrice(){
		return warmingPrice;
	}
	
	public void setWarmingPrice(Double warmingPrice){
		this.warmingPrice = warmingPrice;
	}
	
	public Integer getStatus(){
		return status;
	}
	
	public void setStatus(Integer status){
		this.status = status;
	}
	
	
	/*Auto generated methods end*/
	
	
	
	/*Customized methods start*/
	
	/*Customized methods end*/
	
	
	@Override
	public String toString() {
		return "BillingScheme ["
		 + "schemeNo = " + schemeNo + ", schemeVersions = " + schemeVersions + ", schemeName = " + schemeName + ", operatorId = " + operatorId
		 + ", operatorType = " + operatorType + ", createTime = " + createTime + ", createTimeStart = " + createTimeStart + ", createTimeEnd = " + createTimeEnd + ", updateTime = " + updateTime + ", updateTimeStart = " + updateTimeStart + ", updateTimeEnd = " + updateTimeEnd + ", effectiveDate = " + effectiveDate + ", effectiveDateStart = " + effectiveDateStart + ", effectiveDateEnd = " + effectiveDateEnd
		 + ", invalidDate = " + invalidDate + ", invalidDateStart = " + invalidDateStart + ", invalidDateEnd = " + invalidDateEnd + ", advanceFrozenMoney = " + advanceFrozenMoney + ", minFrozenMoney = " + minFrozenMoney + ", timeNum = " + timeNum
		 + ", opintPrice = " + opintPrice + ", peakPrice = " + peakPrice + ", flatPrice = " + flatPrice + ", valleyPrice = " + valleyPrice
		 + ", orderedRate = " + orderedRate + ", serviceCharge = " + serviceCharge + ", warmingPrice = " + warmingPrice + ", status = " + status
		+"]";
	}
}
