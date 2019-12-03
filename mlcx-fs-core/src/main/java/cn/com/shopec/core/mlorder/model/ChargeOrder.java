package cn.com.shopec.core.mlorder.model;

import java.util.Date;

import cn.com.shopec.core.common.Entity;

/** 
 * 充电订单表 数据实体类
 */
public class ChargeOrder extends Entity<String> {
	
	private static final long serialVersionUID = 1l;
	
	/*Auto generated properties start*/
	
	//订单号
	private String orderNo;
	//充电站编号
	private String stationNo;
	//充电站名称
	private String stationName;
	//充电桩编号
	private String chargingPileNo;
	//充电桩名称
	private String chargingPileName;
	//充电枪编号
	private String chargingGunNo; 
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
	//订单总时长(分钟)
	private Integer orderTimeLength;
	//充电度数
	private Double chargeDegree;
	//充电金额
	private Double chargeAmount;
	//服务费用金额
	private Double serviceAmount;
	//订单总金额
	private Double orderAmount;
	//余额抵扣金额
	private Double discountAmount;
	//未付金额
	private Double nopayAmount;
	//订单状态(0进行中，1待支付，2待评价，3已完成,4.已取消)
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
	//充电结束推送状态(0.已推送 1.未推送)
	public Integer pushType;
	
	
	/*Auto generated properties end*/
	
	
	
	/*Customized properties start*/
	
	/*Customized properties end*/
	
	
	
	/*Auto generated methods start*/
	
	public Integer getPushType() {
		return pushType;
	}

	public void setPushType(Integer pushType) {
		this.pushType = pushType;
	}

	public String getChargingGunNo() {
		return chargingGunNo;
	}

	public void setChargingGunNo(String chargingGunNo) {
		this.chargingGunNo = chargingGunNo;
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
	
	public String getChargingPileNo(){
		return chargingPileNo;
	}
	
	public void setChargingPileNo(String chargingPileNo){
		this.chargingPileNo = chargingPileNo;
	}
	
	public String getChargingPileName(){
		return chargingPileName;
	}
	
	public void setChargingPileName(String chargingPileName){
		this.chargingPileName = chargingPileName;
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
	
	public Double getChargeDegree(){
		return chargeDegree;
	}
	
	public void setChargeDegree(Double chargeDegree){
		this.chargeDegree = chargeDegree;
	}
	
	public Double getChargeAmount(){
		return chargeAmount;
	}
	
	public void setChargeAmount(Double chargeAmount){
		this.chargeAmount = chargeAmount;
	}
	
	public Double getServiceAmount(){
		return serviceAmount;
	}
	
	public void setServiceAmount(Double serviceAmount){
		this.serviceAmount = serviceAmount;
	}
	
	public Double getOrderAmount(){
		return orderAmount;
	}
	
	public void setOrderAmount(Double orderAmount){
		this.orderAmount = orderAmount;
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
		return "ChargeOrder [orderNo=" + orderNo + ", stationNo=" + stationNo
				+ ", stationName=" + stationName + ", chargingPileNo="
				+ chargingPileNo + ", chargingPileName=" + chargingPileName
				+ ", chargingGunNo=" + chargingGunNo + ", memberNo=" + memberNo
				+ ", mobilePhone=" + mobilePhone + ", memberName=" + memberName
				+ ", orderStartTime=" + orderStartTime
				+ ", orderStartTimeStart=" + orderStartTimeStart
				+ ", orderStartTimeEnd=" + orderStartTimeEnd
				+ ", orderEndTime=" + orderEndTime + ", orderEndTimeStart="
				+ orderEndTimeStart + ", orderEndTimeEnd=" + orderEndTimeEnd
				+ ", orderTimeLength=" + orderTimeLength + ", chargeDegree="
				+ chargeDegree + ", chargeAmount=" + chargeAmount
				+ ", serviceAmount=" + serviceAmount + ", orderAmount="
				+ orderAmount + ", discountAmount=" + discountAmount
				+ ", nopayAmount=" + nopayAmount + ", orderStatus="
				+ orderStatus + ", payStatus=" + payStatus + ", payType="
				+ payType + ", paymentMethod=" + paymentMethod
				+ ", paymentTime=" + paymentTime + ", paymentTimeStart="
				+ paymentTimeStart + ", paymentTimeEnd=" + paymentTimeEnd
				+ ", paymentFlowNo=" + paymentFlowNo + ", orderMemo="
				+ orderMemo + ", orderSource=" + orderSource + ", finishType="
				+ finishType + ", finishReason=" + finishReason
				+ ", createTime=" + createTime + ", createTimeStart="
				+ createTimeStart + ", createTimeEnd=" + createTimeEnd
				+ ", updateTime=" + updateTime + ", updateTimeStart="
				+ updateTimeStart + ", updateTimeEnd=" + updateTimeEnd
				+ ", operatorType=" + operatorType + ", operatorId="
				+ operatorId + ", pushType=" + pushType + "]";
	}
}
