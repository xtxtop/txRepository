package cn.com.shopec.core.dailyrental.model;

import java.util.Date;

import cn.com.shopec.core.common.Entity;

/** 
 * DelayOrderDay 数据实体类
 */
public class DelayOrderDay extends Entity<String> {
	
	private static final long serialVersionUID = 1l;
	
	/*Auto generated properties start*/
	
	private String delayOrderId;
	//续租订单号
	private String orderNo;
	//补交的保险金额
	private Double insurance;
	//补交的不计免赔费
	private Double regardlessFranchise;
	//补交租车金额
	private Double carRentalAmount;
	//其他费用（预留）
	private Double otherAmount;
	//支付状态（0、未支付，1、已支付，默认0）
	private Integer payStatus;
	//支付方式（1、支付宝、2、微信）
	private Integer paymentMethod;
	//支付时间
	private Date paymentTime;
	//支付时间 时间范围起（查询用）
	private Date paymentTimeStart;
	//支付时间 时间范围止（查询用）
	private Date paymentTimeEnd;	
	//支付流水号
	private String paymentFlowNo;
	//内部支付流水号
	private String partTradeFlowNo;
	//续租开始时间
	private Date startTime;
	//续租开始时间 时间范围起（查询用）
	private Date startTimeStart;
	//续租开始时间 时间范围止（查询用）
	private Date startTimeEnd;	
	//续租结束时间
	private Date finishTime;
	//续租结束时间 时间范围起（查询用）
	private Date finishTimeStart;
	//续租结束时间 时间范围止（查询用）
	private Date finishTimeEnd;	
	//续租天数
	private Integer replenishDays;
	//总补交金额
	private Double totalReplenishAmount;
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
		return delayOrderId;
	}
	
	public String getDelayOrderId(){
		return delayOrderId;
	}
	
	public void setDelayOrderId(String delayOrderId){
		this.delayOrderId = delayOrderId;
	}
	
	public String getOrderNo(){
		return orderNo;
	}
	
	public void setOrderNo(String orderNo){
		this.orderNo = orderNo;
	}
	
	public Double getInsurance(){
		return insurance;
	}
	
	public void setInsurance(Double insurance){
		this.insurance = insurance;
	}
	
	public Double getRegardlessFranchise() {
		return regardlessFranchise;
	}

	public void setRegardlessFranchise(Double regardlessFranchise) {
		this.regardlessFranchise = regardlessFranchise;
	}

	public Double getCarRentalAmount(){
		return carRentalAmount;
	}
	
	public void setCarRentalAmount(Double carRentalAmount){
		this.carRentalAmount = carRentalAmount;
	}
	
	public Double getOtherAmount(){
		return otherAmount;
	}
	
	public void setOtherAmount(Double otherAmount){
		this.otherAmount = otherAmount;
	}
	
	public Integer getPayStatus(){
		return payStatus;
	}
	
	public void setPayStatus(Integer payStatus){
		this.payStatus = payStatus;
	}
	
	public Integer getPaymentMethod(){
		return paymentMethod;
	}
	
	public void setPaymentMethod(Integer paymentMethod){
		this.paymentMethod = paymentMethod;
	}
	
	public Date getPaymentTime(){
		return paymentTime;
	}
	
	public void setPaymentTime(Date paymentTime){
		this.paymentTime = paymentTime;
	}
	
	public Date getPaymentTimeStart(){
		return paymentTimeStart;
	}
	
	public void setPaymentTimeStart(Date paymentTimeStart){
		this.paymentTimeStart = paymentTimeStart;
	}
	
	public Date getPaymentTimeEnd(){
		return paymentTimeEnd;
	}
	
	public void setPaymentTimeEnd(Date paymentTimeEnd){
		this.paymentTimeEnd = paymentTimeEnd;
	}	
	
	public String getPaymentFlowNo(){
		return paymentFlowNo;
	}
	
	public void setPaymentFlowNo(String paymentFlowNo){
		this.paymentFlowNo = paymentFlowNo;
	}
	
	public String getPartTradeFlowNo(){
		return partTradeFlowNo;
	}
	
	public void setPartTradeFlowNo(String partTradeFlowNo){
		this.partTradeFlowNo = partTradeFlowNo;
	}
	
	public Date getStartTime(){
		return startTime;
	}
	
	public void setStartTime(Date startTime){
		this.startTime = startTime;
	}
	
	public Date getStartTimeStart(){
		return startTimeStart;
	}
	
	public void setStartTimeStart(Date startTimeStart){
		this.startTimeStart = startTimeStart;
	}
	
	public Date getStartTimeEnd(){
		return startTimeEnd;
	}
	
	public void setStartTimeEnd(Date startTimeEnd){
		this.startTimeEnd = startTimeEnd;
	}	
	
	public Date getFinishTime(){
		return finishTime;
	}
	
	public void setFinishTime(Date finishTime){
		this.finishTime = finishTime;
	}
	
	public Date getFinishTimeStart(){
		return finishTimeStart;
	}
	
	public void setFinishTimeStart(Date finishTimeStart){
		this.finishTimeStart = finishTimeStart;
	}
	
	public Date getFinishTimeEnd(){
		return finishTimeEnd;
	}
	
	public void setFinishTimeEnd(Date finishTimeEnd){
		this.finishTimeEnd = finishTimeEnd;
	}	
	
	public Integer getReplenishDays(){
		return replenishDays;
	}
	
	public void setReplenishDays(Integer replenishDays){
		this.replenishDays = replenishDays;
	}
	
	public Double getTotalReplenishAmount(){
		return totalReplenishAmount;
	}
	
	public void setTotalReplenishAmount(Double totalReplenishAmount){
		this.totalReplenishAmount = totalReplenishAmount;
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
		return "DelayOrderDay ["
		 + "delayOrderId = " + delayOrderId + ", orderNo = " + orderNo + ", insurance = " + insurance + ", carRentalAmount = " + carRentalAmount
		 + ", otherAmount = " + otherAmount + ", payStatus = " + payStatus + ", paymentMethod = " + paymentMethod + ", paymentTime = " + paymentTime + ", paymentTimeStart = " + paymentTimeStart + ", paymentTimeEnd = " + paymentTimeEnd
		 + ", paymentFlowNo = " + paymentFlowNo + ", partTradeFlowNo = " + partTradeFlowNo + ", startTime = " + startTime + ", startTimeStart = " + startTimeStart + ", startTimeEnd = " + startTimeEnd + ", finishTime = " + finishTime + ", finishTimeStart = " + finishTimeStart + ", finishTimeEnd = " + finishTimeEnd
		 + ", replenishDays = " + replenishDays + ", totalReplenishAmount = " + totalReplenishAmount + ", createTime = " + createTime + ", createTimeStart = " + createTimeStart + ", createTimeEnd = " + createTimeEnd + ", updateTime = " + updateTime + ", updateTimeStart = " + updateTimeStart + ", updateTimeEnd = " + updateTimeEnd
		 + ", operatorType = " + operatorType + ", operatorId = " + operatorId
		+"]";
	}
}
