package cn.com.shopec.core.marketing.model;

import java.util.Date;

import cn.com.shopec.core.common.Entity;

/** 
 * PeakHours 数据实体类
 */
public class PeakHours extends Entity<String> {
	
	private static final long serialVersionUID = 1l;
	
	/*Auto generated properties start*/
	
	//高峰时段ID
	private String peakHourId;
	//计费规则id
	private String ruleNo;
	//高峰时段
	private String peakHours;
	private String peakStartHours;
	private String peakEndHours;
	//高峰时间计费(元/分钟)
	private Double priceOfMinute;
	//高峰时段按里程计费(元/公里)
	private Double priceOfKm;
	//创建时间
	private Date createTime;
	// 时间范围起（查询用）
	private Date createTimeStart;
	// 时间范围止（查询用）
	private Date createTimeEnd;	
	//更新时间
	private Date updateTime;
	// 时间范围起（查询用）
	private Date updateTimeStart;
	// 时间范围止（查询用）
	private Date updateTimeEnd;	
	
	/*Auto generated properties end*/
	
	
	
	/*Customized properties start*/
	
	/*Customized properties end*/
	
	
	
	/*Auto generated methods start*/
	
	@Override
	public String getPK(){
		return peakHourId;
	}
	
	public String getPeakHourId(){
		return peakHourId;
	}
	
	public void setPeakHourId(String peakHourId){
		this.peakHourId = peakHourId;
	}
	
	public String getRuleNo(){
		return ruleNo;
	}
	
	public void setRuleNo(String ruleNo){
		this.ruleNo = ruleNo;
	}
	
	public String getPeakHours(){
		return peakHours;
	}
	
	public void setPeakHours(String peakHours){
		this.peakHours = peakHours;
	}
	
	public String getPeakStartHours(){
		return peakStartHours;
	}
	
	public void setPeakStartHours(String peakStartHours){
		this.peakStartHours = peakStartHours;
	}
	
	public String getPeakEndHours(){
		return peakEndHours;
	}
	
	public void setPeakEndHours(String peakEndHours){
		this.peakEndHours = peakEndHours;
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
	
	
	/*Auto generated methods end*/
	
	
	
	/*Customized methods start*/
	
	/*Customized methods end*/
	
	
	@Override
	public String toString() {
		return "PeakHours ["
		 + "peakHourId = " + peakHourId + ", ruleNo = " + ruleNo + ", peakHours = " + peakHours + ", peakStartHours = " + peakStartHours
		 + ", peakEndHours = " + peakEndHours + ", priceOfMinute = " + priceOfMinute + ", priceOfKm = " + priceOfKm + ", createTime = " + createTime + ", createTimeStart = " + createTimeStart + ", createTimeEnd = " + createTimeEnd + ", updateTime = " + updateTime + ", updateTimeStart = " + updateTimeStart + ", updateTimeEnd = " + updateTimeEnd
		+"]";
	}
}
