package cn.com.shopec.core.marketing.model;

import java.util.Date;

import cn.com.shopec.core.common.Entity;

/** 
 * GoldBeansSetting 数据实体类
 */
public class GoldBeansSetting extends Entity<String> {
	
	private static final long serialVersionUID = 1l;
	
	/*Auto generated properties start*/
	
	//主键
	private String goldBeansId;
	//m天所获得的金豆数
	private Integer goldBeansNum;
	//涨m个金豆所需天数
	private Integer days;
	//金豆和人民币的比率值
	private Float beansMoneyRatio;
	//订单结算金豆抵扣比率值
	private Float orderBeansRatio;
	//抵扣金额封顶值
	private Double dedutionMaxAmount;
	private Date createTime;
	// 时间范围起（查询用）
	private Date createTimeStart;
	// 时间范围止（查询用）
	private Date createTimeEnd;	
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
		return goldBeansId;
	}
	
	public String getGoldBeansId(){
		return goldBeansId;
	}
	
	public void setGoldBeansId(String goldBeansId){
		this.goldBeansId = goldBeansId;
	}
	
	public Integer getGoldBeansNum() {
		return goldBeansNum;
	}

	public void setGoldBeansNum(Integer goldBeansNum) {
		this.goldBeansNum = goldBeansNum;
	}

	public Integer getDays(){
		return days;
	}
	
	public void setDays(Integer days){
		this.days = days;
	}
	
	public Float getBeansMoneyRatio(){
		return beansMoneyRatio;
	}
	
	public void setBeansMoneyRatio(Float beansMoneyRatio){
		this.beansMoneyRatio = beansMoneyRatio;
	}
	
	public Float getOrderBeansRatio(){
		return orderBeansRatio;
	}
	
	public void setOrderBeansRatio(Float orderBeansRatio){
		this.orderBeansRatio = orderBeansRatio;
	}
	
	public Double getDedutionMaxAmount(){
		return dedutionMaxAmount;
	}
	
	public void setDedutionMaxAmount(Double dedutionMaxAmount){
		this.dedutionMaxAmount = dedutionMaxAmount;
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
		return "GoldBeansSetting ["
		 + "goldBeansId = " + goldBeansId + ", goldBeansNum = " + goldBeansNum + ", days = " + days + ", beansMoneyRatio = " + beansMoneyRatio
		 + ", orderBeansRatio = " + orderBeansRatio + ", dedutionMaxAmount = " + dedutionMaxAmount + ", createTime = " + createTime + ", createTimeStart = " + createTimeStart + ", createTimeEnd = " + createTimeEnd + ", updateTime = " + updateTime + ", updateTimeStart = " + updateTimeStart + ", updateTimeEnd = " + updateTimeEnd
		+"]";
	}
}
