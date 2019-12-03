package cn.com.shopec.core.mlorder.model;

import java.util.Date;

import cn.com.shopec.core.common.Entity;

/** 
 * 地锁订单表 数据实体类
 */
public class LockOrder extends Entity<String> {
	
	private static final long serialVersionUID = 1l;
	
	/*Auto generated properties start*/
	
	//订单号
	private String orderNo;
	//充电站编号
	private String stationNo;
	//充电站名称
	private String stationName;
	//地锁编号
	private String parkingLockNo;
	//地锁名称
	private String parkingLockName;
	//会员id
	private String memberNo;
	//手机号
	private String mobilePhone;
	//会员名称
	private String memberName;
	//订单开始时间
	private Date orderStartTime;
	//订单开始时间 时间范围起（查询用）
	private Date orderStartTimeStart;
	//订单开始时间 时间范围止（查询用）
	private Date orderStartTimeEnd;	
	//订单结束时间
	private Date orderEndTime;
	//订单结束时间 时间范围起（查询用）
	private Date orderEndTimeStart;
	//订单结束时间 时间范围止（查询用）
	private Date orderEndTimeEnd;	
	//订单总时长
	private Integer orderTimeLength;
	//免费时长
	private Integer orderFreeTime;
	//超时时长
	private Integer orderOverTime;
	//订单总金额
	private Double orderAmount;
	//超时总金额
	private Double overtimeAmount;
	//余额抵扣金额
	private Double discountAmount;
	//未付金额
	private Double nopayAmount;
	//订单状态（0进行中，1待支付，2待评价，3已完成,4.未结算）
	private Integer orderStatus;
	//支付状态（0、未支付，1、已支付）
	private Integer payStatus;
	//支付方式（0、支付宝、1、微信）
	private Integer payType;
	//支付方式（0、支付宝、1、微信）
	private Integer paymentMethod;
	//支付时间
	private Date paymentTime;
	//支付时间 时间范围起（查询用）
	private Date paymentTimeStart;
	//支付时间 时间范围止（查询用）
	private Date paymentTimeEnd;	
	//支付流水号
	private String paymentFlowNo;
	//订单备注
	private String orderMemo;
	//订单来源（0、ios 1、安卓）
	private String orderSource;
	//结束类型0、正常结束1、后台结束
	private Integer finishType;
	//结束理由
	private String finishReason;
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
	//计费方案编号
	private String lockBillingSchemeNo;
	//地锁类型(0 充电 1停车)
	private Integer lockType;
	//充电订单编号
	private String chargeOrderNo;
	
	/*Auto generated properties end*/
	
	
	
	/*Customized properties start*/
	
	/*Customized properties end*/
	
	
	
	/*Auto generated methods start*/
	
	public Integer getLockType() {
		return lockType;
	}

	public void setLockType(Integer lockType) {
		this.lockType = lockType;
	}

	public String getChargeOrderNo() {
		return chargeOrderNo;
	}

	public void setChargeOrderNo(String chargeOrderNo) {
		this.chargeOrderNo = chargeOrderNo;
	}

	public String getLockBillingSchemeNo() {
		return lockBillingSchemeNo;
	}

	public void setLockBillingSchemeNo(String lockBillingSchemeNo) {
		this.lockBillingSchemeNo = lockBillingSchemeNo;
	}

	@Override
	public String getPK(){
		return orderNo;
	}
	
	public String getOrderNo(){
		return orderNo;
	}
	
	public void setOrderNo(String orderNo){
		this.orderNo = orderNo;
	}
	
	public String getStationNo(){
		return stationNo;
	}
	
	public void setStationNo(String stationNo){
		this.stationNo = stationNo;
	}
	
	public String getStationName(){
		return stationName;
	}
	
	public void setStationName(String stationName){
		this.stationName = stationName;
	}
	
	public String getParkingLockNo(){
		return parkingLockNo;
	}
	
	public void setParkingLockNo(String parkingLockNo){
		this.parkingLockNo = parkingLockNo;
	}
	
	public String getParkingLockName(){
		return parkingLockName;
	}
	
	public void setParkingLockName(String parkingLockName){
		this.parkingLockName = parkingLockName;
	}
	
	public String getMemberNo(){
		return memberNo;
	}
	
	public void setMemberNo(String memberNo){
		this.memberNo = memberNo;
	}
	
	public String getMobilePhone(){
		return mobilePhone;
	}
	
	public void setMobilePhone(String mobilePhone){
		this.mobilePhone = mobilePhone;
	}
	
	public String getMemberName(){
		return memberName;
	}
	
	public void setMemberName(String memberName){
		this.memberName = memberName;
	}
	
	public Date getOrderStartTime(){
		return orderStartTime;
	}
	
	public void setOrderStartTime(Date orderStartTime){
		this.orderStartTime = orderStartTime;
	}
	
	public Date getOrderStartTimeStart(){
		return orderStartTimeStart;
	}
	
	public void setOrderStartTimeStart(Date orderStartTimeStart){
		this.orderStartTimeStart = orderStartTimeStart;
	}
	
	public Date getOrderStartTimeEnd(){
		return orderStartTimeEnd;
	}
	
	public void setOrderStartTimeEnd(Date orderStartTimeEnd){
		this.orderStartTimeEnd = orderStartTimeEnd;
	}	
	
	public Date getOrderEndTime(){
		return orderEndTime;
	}
	
	public void setOrderEndTime(Date orderEndTime){
		this.orderEndTime = orderEndTime;
	}
	
	public Date getOrderEndTimeStart(){
		return orderEndTimeStart;
	}
	
	public void setOrderEndTimeStart(Date orderEndTimeStart){
		this.orderEndTimeStart = orderEndTimeStart;
	}
	
	public Date getOrderEndTimeEnd(){
		return orderEndTimeEnd;
	}
	
	public void setOrderEndTimeEnd(Date orderEndTimeEnd){
		this.orderEndTimeEnd = orderEndTimeEnd;
	}	
	
	public Integer getOrderTimeLength(){
		return orderTimeLength;
	}
	
	public void setOrderTimeLength(Integer orderTimeLength){
		this.orderTimeLength = orderTimeLength;
	}
	
	public Integer getOrderFreeTime(){
		return orderFreeTime;
	}
	
	public void setOrderFreeTime(Integer orderFreeTime){
		this.orderFreeTime = orderFreeTime;
	}
	
	public Integer getOrderOverTime(){
		return orderOverTime;
	}
	
	public void setOrderOverTime(Integer orderOverTime){
		this.orderOverTime = orderOverTime;
	}
	
	public Double getOrderAmount(){
		return orderAmount;
	}
	
	public void setOrderAmount(Double orderAmount){
		this.orderAmount = orderAmount;
	}
	
	public Double getOvertimeAmount(){
		return overtimeAmount;
	}
	
	public void setOvertimeAmount(Double overtimeAmount){
		this.overtimeAmount = overtimeAmount;
	}
	
	public Double getDiscountAmount(){
		return discountAmount;
	}
	
	public void setDiscountAmount(Double discountAmount){
		this.discountAmount = discountAmount;
	}
	
	public Double getNopayAmount(){
		return nopayAmount;
	}
	
	public void setNopayAmount(Double nopayAmount){
		this.nopayAmount = nopayAmount;
	}
	
	public Integer getOrderStatus(){
		return orderStatus;
	}
	
	public void setOrderStatus(Integer orderStatus){
		this.orderStatus = orderStatus;
	}
	
	public Integer getPayStatus(){
		return payStatus;
	}
	
	public void setPayStatus(Integer payStatus){
		this.payStatus = payStatus;
	}
	
	public Integer getPayType(){
		return payType;
	}
	
	public void setPayType(Integer payType){
		this.payType = payType;
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
	
	public String getOrderMemo(){
		return orderMemo;
	}
	
	public void setOrderMemo(String orderMemo){
		this.orderMemo = orderMemo;
	}
	
	public String getOrderSource(){
		return orderSource;
	}
	
	public void setOrderSource(String orderSource){
		this.orderSource = orderSource;
	}
	
	public Integer getFinishType(){
		return finishType;
	}
	
	public void setFinishType(Integer finishType){
		this.finishType = finishType;
	}
	
	public String getFinishReason(){
		return finishReason;
	}
	
	public void setFinishReason(String finishReason){
		this.finishReason = finishReason;
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
		return "LockOrder [orderNo=" + orderNo + ", stationNo=" + stationNo
				+ ", stationName=" + stationName + ", parkingLockNo="
				+ parkingLockNo + ", parkingLockName=" + parkingLockName
				+ ", memberNo=" + memberNo + ", mobilePhone=" + mobilePhone
				+ ", memberName=" + memberName + ", orderStartTime="
				+ orderStartTime + ", orderStartTimeStart="
				+ orderStartTimeStart + ", orderStartTimeEnd="
				+ orderStartTimeEnd + ", orderEndTime=" + orderEndTime
				+ ", orderEndTimeStart=" + orderEndTimeStart
				+ ", orderEndTimeEnd=" + orderEndTimeEnd + ", orderTimeLength="
				+ orderTimeLength + ", orderFreeTime=" + orderFreeTime
				+ ", orderOvertime=" + orderOverTime + ", orderAmount="
				+ orderAmount + ", overtimeAmount=" + overtimeAmount
				+ ", discountAmount=" + discountAmount + ", nopayAmount="
				+ nopayAmount + ", orderStatus=" + orderStatus + ", payStatus="
				+ payStatus + ", payType=" + payType + ", paymentMethod="
				+ paymentMethod + ", paymentTime=" + paymentTime
				+ ", paymentTimeStart=" + paymentTimeStart
				+ ", paymentTimeEnd=" + paymentTimeEnd + ", paymentFlowNo="
				+ paymentFlowNo + ", orderMemo=" + orderMemo + ", orderSource="
				+ orderSource + ", finishType=" + finishType
				+ ", finishReason=" + finishReason + ", createTime="
				+ createTime + ", createTimeStart=" + createTimeStart
				+ ", createTimeEnd=" + createTimeEnd + ", updateTime="
				+ updateTime + ", updateTimeStart=" + updateTimeStart
				+ ", updateTimeEnd=" + updateTimeEnd + ", operatorType="
				+ operatorType + ", operatorId=" + operatorId
				+ ", lockBillingSchemeNo=" + lockBillingSchemeNo
				+ ", lockType=" + lockType + ", chargeOrderNo="
				+ chargeOrderNo + "]";
	}
}
