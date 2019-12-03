package cn.com.shopec.core.dailyrental.model;

import java.util.Date;

import cn.com.shopec.core.common.Entity;

/** 
 * 商家确认账单 数据实体类
 */
public class MerchantOrderConfirm extends Entity<String> {
	
	private static final long serialVersionUID = 1l;
	
	/*Auto generated properties start*/
	
	//主键
	private String id;
	//商家编号
	private String merchantId;
	//商家名称
	private String merchantName;
	//年份
	private Integer year;
	//月份
	private Integer month;
	//对账周期
	private String date;
	//订单数量
	private Integer num;
	//订单总金额
	private Double orderAmount;
	//分润金额
	private Double profitAmount;
	//账单确认状态(0、未确认1、已确认)
	private Integer confirmStatus;
	//账单确认时间
	private Date confirmTime;
	//账单确认时间 时间范围起（查询用）
	private Date confirmTimeStart;
	//账单确认时间 时间范围止（查询用）
	private Date confirmTimeEnd;	
	//分润比(商家占总金额比例)
	private Double profitRatio;
	//对账单是否结算（0、否 1、是）
	private Integer isSettled;
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
	
	/*Auto generated properties end*/
	
	
	
	/*Customized properties start*/
	
	/*Customized properties end*/
	
	
	
	/*Auto generated methods start*/
	
	@Override
	public String getPK(){
		return id;
	}
	
	public String getId(){
		return id;
	}
	
	public void setId(String id){
		this.id = id;
	}
	
	public String getMerchantId(){
		return merchantId;
	}
	
	public void setMerchantId(String merchantId){
		this.merchantId = merchantId;
	}
	
	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public Integer getYear(){
		return year;
	}
	
	public void setYear(Integer year){
		this.year = year;
	}
	
	public Integer getMonth(){
		return month;
	}
	
	public void setMonth(Integer month){
		this.month = month;
	}
	
	public String getDate(){
		return date;
	}
	
	public void setDate(String date){
		this.date = date;
	}
	
	public Integer getNum(){
		return num;
	}
	
	public void setNum(Integer num){
		this.num = num;
	}
	
	public Double getOrderAmount(){
		return orderAmount;
	}
	
	public void setOrderAmount(Double orderAmount){
		this.orderAmount = orderAmount;
	}
	
	public Double getProfitAmount(){
		return profitAmount;
	}
	
	public void setProfitAmount(Double profitAmount){
		this.profitAmount = profitAmount;
	}
	
	public Integer getConfirmStatus(){
		return confirmStatus;
	}
	
	public void setConfirmStatus(Integer confirmStatus){
		this.confirmStatus = confirmStatus;
	}
	
	public Date getConfirmTime(){
		return confirmTime;
	}
	
	public void setConfirmTime(Date confirmTime){
		this.confirmTime = confirmTime;
	}
	
	public Date getConfirmTimeStart(){
		return confirmTimeStart;
	}
	
	public void setConfirmTimeStart(Date confirmTimeStart){
		this.confirmTimeStart = confirmTimeStart;
	}
	
	public Date getConfirmTimeEnd(){
		return confirmTimeEnd;
	}
	
	public void setConfirmTimeEnd(Date confirmTimeEnd){
		this.confirmTimeEnd = confirmTimeEnd;
	}	
	
	public Double getProfitRatio(){
		return profitRatio;
	}
	
	public void setProfitRatio(Double profitRatio){
		this.profitRatio = profitRatio;
	}
	
	public Integer getIsSettled() {
		return isSettled;
	}

	public void setIsSettled(Integer isSettled) {
		this.isSettled = isSettled;
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
	
	
	@Override
	public String toString() {
		return "MerchantOrderConfirm ["
		 + "id = " + id + ", merchantId = " + merchantId + ", year = " + year + ", month = " + month
		 + ", date = " + date + ", num = " + num + ", orderAmount = " + orderAmount + ", profitAmount = " + profitAmount
		 + ", confirmStatus = " + confirmStatus + ", confirmTime = " + confirmTime + ", confirmTimeStart = " + confirmTimeStart + ", confirmTimeEnd = " + confirmTimeEnd + ", profitRatio = " + profitRatio + ", createTime = " + createTime + ", createTimeStart = " + createTimeStart + ", createTimeEnd = " + createTimeEnd
		 + ", updateTime = " + updateTime + ", updateTimeStart = " + updateTimeStart + ", updateTimeEnd = " + updateTimeEnd + ", operatorId = " + operatorId + ", operatorType = " + operatorType
		+"]";
	}
}
