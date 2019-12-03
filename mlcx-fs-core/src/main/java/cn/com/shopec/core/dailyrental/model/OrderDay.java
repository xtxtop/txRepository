package cn.com.shopec.core.dailyrental.model;

import java.util.Date;

import cn.com.shopec.core.common.Entity;

/** 
 * 日租订单表 数据实体类
 */
public class OrderDay extends Entity<String> {
	
	private static final long serialVersionUID = 1l;
	
	/*Auto generated properties start*/
	
	//订单号
	private String orderNo;
	//会员id
	private String memberNo;
	//手机号
	private String mobilePhone;
	//会员名称
	private String memberName;
	//车辆编号
	private String carNo;
	//车牌号
	private String carPlateNo;
	//车型id
	private String carModelId;
	//城市id（具体来自数据字典表）
	private String cityId;
	//城市名称
	private String cityName;
	//订单状态（0、已提交，1、已预约，2、进行中，3、已结束，4、已取消，默认0）
	private Integer orderStatus;
	//订单总金额
	private Double orderAmount;
	//异地还车费
	private Double taxiFare;
	//保险费用
	private Double insurance;
	//押金金额
	private Double deposit;
	//冲账金额
	private Double strikeBalanceAmount;
	//折扣金额
	private Double discountAmount;
	//支付方式（1、支付宝、2、微信）
	private Integer depositType;
	//押金支付时间
	private Double depositTime;
	//应付金额
	private Double payableAmount;
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
	// 内部支付流水号
	private String partTradeFlowNo;
	//押金支付状态（0、未支付，1、已支付，默认0）
	private Integer depositStatus;
	//超时服务费
	private Double overtimeCharge;
	//服务费金额
	private Double serviceCharge;
	//租车金额
	private Double carRentalAmount;
	//预约取车时间
	private Date appointmentTakeTime;
	//预约取车时间 时间范围起（查询用）
	private Date appointmentTakeTimeStart;
	//预约取车时间 时间范围止（查询用）
	private Date appointmentTakeTimeEnd;	
	//预约还车时间
	private Date appointmentReturnTime;
	//预约还车时间 时间范围起（查询用）
	private Date appointmentReturnTimeStart;
	//预约还车时间 时间范围止（查询用）
	private Date appointmentReturnTimeEnd;	
	//实际取车时间
	private Date actualTakeTime;
	//实际取车时间 时间范围起（查询用）
	private Date actualTakeTimeStart;
	//实际取车时间 时间范围止（查询用）
	private Date actualTakeTimeEnd;	
	//实际还车时间
	private Date actualReturnTime;
	//实际还车时间 时间范围起（查询用）
	private Date actualReturnTimeStart;
	//实际还车时间 时间范围止（查询用）
	private Date actualReturnTimeEnd;	
	//已付金额
	private Double amountPaid;
	//预约取车地点门店编号
	private String startParkNo;
	//实际取车门店名称
	private String actualTakePakeName;
	//预约还车地点场站编号
	private String terminalParkNo;
	//实际还车点车门店名称
	private String actualTerminalParkName;
	//实际还车地点（场站编号）
	private String actualTerminalParkNo;
	//附加费用(提前还车违约金)
	private Double serviceFeeAmount;
	//附加费用原因
	private String additionReason;
	//不计免赔
	private Double regardlessFranchise;
	//预约时间
	private Date appointmentTime;
	//预约时间 时间范围起（查询用）
	private Date appointmentTimeStart;
	//预约时间 时间范围止（查询用）
	private Date appointmentTimeEnd;	
	//计费时间
	private Date startBillingTime;
	//计费时间 时间范围起（查询用）
	private Date startBillingTimeStart;
	//计费时间 时间范围止（查询用）
	private Date startBillingTimeEnd;	
	//结束时间
	private Date finishTime;
	//结束时间 时间范围起（查询用）
	private Date finishTimeStart;
	//结束时间 时间范围止（查询用）
	private Date finishTimeEnd;	
	//订单时长(天数)
	private Integer orderDuration;
	//是否有违章（0，无违章、1，有违章，默认0）
	private Integer isIllegal;
	//是否有事故（0，无事故、1，有事故，默认0）
	private Integer isAccident;
	//是否有故障（0，无故障、1，有故障，默认0）
	private Integer isFault;
	//订单备注
	private String orderMemo;
	//是否已取消（0，未取消，1、已取消，默认0）
	private Integer isCancel;
	//取消时间
	private Date cancelTime;
	//取消时间 时间范围起（查询用）
	private Date cancelTimeStart;
	//取消时间 时间范围止（查询用）
	private Date cancelTimeEnd;	
	//订单取消操作人类型
	private Integer cancelOperatorType;
	//订单取消操作人id
	private String cancelOperatorId;
	//订单来源（1、ios2、安卓3、h5）
	private String orderSource;
	//结束类型1、正常结束2、自动结束3、后台结束
	private Integer finshiType;
	//结束原因
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
	//租赁商id
	private String merchantId;
	//租赁商名称
	private String merchantName;
	//指派人（1、平台自动指派 2、后台人工操作指派）
	private Integer assignee;
	/*Auto generated properties end*/
	
	//商家订单状态
	private Integer merchantOrderStatus;
	//起始门店
	private String startParkName;
	//还车门店
	private String returnParkName;
	//是否送车上门（1  是  0 否）
	private Integer isTakeCarDoor;
	//是否上门取车（1是  0 否）
	private Integer isBackCarDoor;
	//是否续租订单（0 否(默认)，1、 申请续租成功 2、支付成功）
	private Integer isDelayOrder;
	//是否提前还车订单（0 否(默认)，1、 申请提前还车成功）
	private Integer isBeforeOrder;
	//退还费用
	private Double returnAmount;
	//退款状态
	private Integer refundStatus;
	//退款时间
	private Date refundTime;
	//未到店取车违约金
	private Double cancelAmount;
	//送车上门未取车违约金
	private Double takeCarDoorAmount;
	//退款金额（查询用）
	private Double refundAmount;
	/*Customized properties start*/
	
	/*Customized properties end*/
	
	
	
	/*Auto generated methods start*/
	
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
	
	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public String getCarNo(){
		return carNo;
	}
	
	public void setCarNo(String carNo){
		this.carNo = carNo;
	}
	
	public String getCarPlateNo(){
		return carPlateNo;
	}
	
	public void setCarPlateNo(String carPlateNo){
		this.carPlateNo = carPlateNo;
	}
	
	public String getCarModelId(){
		return carModelId;
	}
	
	public void setCarModelId(String carModelId){
		this.carModelId = carModelId;
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
	
	public Integer getOrderStatus(){
		return orderStatus;
	}
	
	public void setOrderStatus(Integer orderStatus){
		this.orderStatus = orderStatus;
	}
	
	public Double getOrderAmount(){
		return orderAmount;
	}
	
	public void setOrderAmount(Double orderAmount){
		this.orderAmount = orderAmount;
	}
	
	public Double getTaxiFare(){
		return taxiFare;
	}
	
	public void setTaxiFare(Double taxiFare){
		this.taxiFare = taxiFare;
	}
	
	public Double getInsurance(){
		return insurance;
	}
	
	public void setInsurance(Double insurance){
		this.insurance = insurance;
	}
	
	public Double getDeposit(){
		return deposit;
	}
	
	public void setDeposit(Double deposit){
		this.deposit = deposit;
	}
	
	public Double getStrikeBalanceAmount(){
		return strikeBalanceAmount;
	}
	
	public void setStrikeBalanceAmount(Double strikeBalanceAmount){
		this.strikeBalanceAmount = strikeBalanceAmount;
	}
	
	public Double getDiscountAmount(){
		return discountAmount;
	}
	
	public void setDiscountAmount(Double discountAmount){
		this.discountAmount = discountAmount;
	}
	
	public Integer getDepositType(){
		return depositType;
	}
	
	public void setDepositType(Integer depositType){
		this.depositType = depositType;
	}
	
	public Double getDepositTime(){
		return depositTime;
	}
	
	public void setDepositTime(Double depositTime){
		this.depositTime = depositTime;
	}
	
	public Double getPayableAmount(){
		return payableAmount;
	}
	
	public void setPayableAmount(Double payableAmount){
		this.payableAmount = payableAmount;
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
	
	public String getPartTradeFlowNo() {
		return partTradeFlowNo;
	}

	public void setPartTradeFlowNo(String partTradeFlowNo) {
		this.partTradeFlowNo = partTradeFlowNo;
	}

	public Integer getDepositStatus(){
		return depositStatus;
	}
	
	public void setDepositStatus(Integer depositStatus){
		this.depositStatus = depositStatus;
	}
	
	public Double getOvertimeCharge(){
		return overtimeCharge;
	}
	
	public void setOvertimeCharge(Double overtimeCharge){
		this.overtimeCharge = overtimeCharge;
	}
	
	public Double getServiceCharge(){
		return serviceCharge;
	}
	
	public void setServiceCharge(Double serviceCharge){
		this.serviceCharge = serviceCharge;
	}
	
	public Double getCarRentalAmount(){
		return carRentalAmount;
	}
	
	public void setCarRentalAmount(Double carRentalAmount){
		this.carRentalAmount = carRentalAmount;
	}
	
	public Date getAppointmentTakeTime(){
		return appointmentTakeTime;
	}
	
	public void setAppointmentTakeTime(Date appointmentTakeTime){
		this.appointmentTakeTime = appointmentTakeTime;
	}
	
	public Date getAppointmentTakeTimeStart(){
		return appointmentTakeTimeStart;
	}
	
	public void setAppointmentTakeTimeStart(Date appointmentTakeTimeStart){
		this.appointmentTakeTimeStart = appointmentTakeTimeStart;
	}
	
	public Date getAppointmentTakeTimeEnd(){
		return appointmentTakeTimeEnd;
	}
	
	public void setAppointmentTakeTimeEnd(Date appointmentTakeTimeEnd){
		this.appointmentTakeTimeEnd = appointmentTakeTimeEnd;
	}	
	
	public Date getAppointmentReturnTime(){
		return appointmentReturnTime;
	}
	
	public void setAppointmentReturnTime(Date appointmentReturnTime){
		this.appointmentReturnTime = appointmentReturnTime;
	}
	
	public Date getAppointmentReturnTimeStart(){
		return appointmentReturnTimeStart;
	}
	
	public void setAppointmentReturnTimeStart(Date appointmentReturnTimeStart){
		this.appointmentReturnTimeStart = appointmentReturnTimeStart;
	}
	
	public Date getAppointmentReturnTimeEnd(){
		return appointmentReturnTimeEnd;
	}
	
	public void setAppointmentReturnTimeEnd(Date appointmentReturnTimeEnd){
		this.appointmentReturnTimeEnd = appointmentReturnTimeEnd;
	}	
	
	public Date getActualTakeTime(){
		return actualTakeTime;
	}
	
	public void setActualTakeTime(Date actualTakeTime){
		this.actualTakeTime = actualTakeTime;
	}
	
	public Date getActualTakeTimeStart(){
		return actualTakeTimeStart;
	}
	
	public void setActualTakeTimeStart(Date actualTakeTimeStart){
		this.actualTakeTimeStart = actualTakeTimeStart;
	}
	
	public Date getActualTakeTimeEnd(){
		return actualTakeTimeEnd;
	}
	
	public void setActualTakeTimeEnd(Date actualTakeTimeEnd){
		this.actualTakeTimeEnd = actualTakeTimeEnd;
	}	
	
	public Date getActualReturnTime(){
		return actualReturnTime;
	}
	
	public void setActualReturnTime(Date actualReturnTime){
		this.actualReturnTime = actualReturnTime;
	}
	
	public Date getActualReturnTimeStart(){
		return actualReturnTimeStart;
	}
	
	public void setActualReturnTimeStart(Date actualReturnTimeStart){
		this.actualReturnTimeStart = actualReturnTimeStart;
	}
	
	public Date getActualReturnTimeEnd(){
		return actualReturnTimeEnd;
	}
	
	public void setActualReturnTimeEnd(Date actualReturnTimeEnd){
		this.actualReturnTimeEnd = actualReturnTimeEnd;
	}	
	
	public Double getAmountPaid(){
		return amountPaid;
	}
	
	public void setAmountPaid(Double amountPaid){
		this.amountPaid = amountPaid;
	}
	
	public String getStartParkNo(){
		return startParkNo;
	}
	
	public void setStartParkNo(String startParkNo){
		this.startParkNo = startParkNo;
	}
	
	public String getActualTakePakeName(){
		return actualTakePakeName;
	}
	
	public void setActualTakePakeName(String actualTakePakeName){
		this.actualTakePakeName = actualTakePakeName;
	}
	
	public String getTerminalParkNo(){
		return terminalParkNo;
	}
	
	public void setTerminalParkNo(String terminalParkNo){
		this.terminalParkNo = terminalParkNo;
	}
	
	public String getActualTerminalParkName(){
		return actualTerminalParkName;
	}
	
	public void setActualTerminalParkName(String actualTerminalParkName){
		this.actualTerminalParkName = actualTerminalParkName;
	}
	
	public String getActualTerminalParkNo(){
		return actualTerminalParkNo;
	}
	
	public void setActualTerminalParkNo(String actualTerminalParkNo){
		this.actualTerminalParkNo = actualTerminalParkNo;
	}
	
	public Double getServiceFeeAmount(){
		return serviceFeeAmount;
	}
	
	public void setServiceFeeAmount(Double serviceFeeAmount){
		this.serviceFeeAmount = serviceFeeAmount;
	}
	
	public String getAdditionReason() {
		return additionReason;
	}

	public void setAdditionReason(String additionReason) {
		this.additionReason = additionReason;
	}

	public Double getRegardlessFranchise() {
		return regardlessFranchise;
	}

	public void setRegardlessFranchise(Double regardlessFranchise) {
		this.regardlessFranchise = regardlessFranchise;
	}

	public Date getAppointmentTime(){
		return appointmentTime;
	}
	
	public void setAppointmentTime(Date appointmentTime){
		this.appointmentTime = appointmentTime;
	}
	
	public Date getAppointmentTimeStart(){
		return appointmentTimeStart;
	}
	
	public void setAppointmentTimeStart(Date appointmentTimeStart){
		this.appointmentTimeStart = appointmentTimeStart;
	}
	
	public Date getAppointmentTimeEnd(){
		return appointmentTimeEnd;
	}
	
	public void setAppointmentTimeEnd(Date appointmentTimeEnd){
		this.appointmentTimeEnd = appointmentTimeEnd;
	}	
	
	public Date getStartBillingTime(){
		return startBillingTime;
	}
	
	public void setStartBillingTime(Date startBillingTime){
		this.startBillingTime = startBillingTime;
	}
	
	public Date getStartBillingTimeStart(){
		return startBillingTimeStart;
	}
	
	public void setStartBillingTimeStart(Date startBillingTimeStart){
		this.startBillingTimeStart = startBillingTimeStart;
	}
	
	public Date getStartBillingTimeEnd(){
		return startBillingTimeEnd;
	}
	
	public void setStartBillingTimeEnd(Date startBillingTimeEnd){
		this.startBillingTimeEnd = startBillingTimeEnd;
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
	
	public Integer getOrderDuration(){
		return orderDuration;
	}
	
	public void setOrderDuration(Integer orderDuration){
		this.orderDuration = orderDuration;
	}
	
	public Integer getIsIllegal(){
		return isIllegal;
	}
	
	public void setIsIllegal(Integer isIllegal){
		this.isIllegal = isIllegal;
	}
	
	public Integer getIsAccident(){
		return isAccident;
	}
	
	public void setIsAccident(Integer isAccident){
		this.isAccident = isAccident;
	}
	
	public Integer getIsFault(){
		return isFault;
	}
	
	public void setIsFault(Integer isFault){
		this.isFault = isFault;
	}
	
	public String getOrderMemo(){
		return orderMemo;
	}
	
	public void setOrderMemo(String orderMemo){
		this.orderMemo = orderMemo;
	}
	
	public Integer getIsCancel(){
		return isCancel;
	}
	
	public void setIsCancel(Integer isCancel){
		this.isCancel = isCancel;
	}
	
	public Date getCancelTime(){
		return cancelTime;
	}
	
	public void setCancelTime(Date cancelTime){
		this.cancelTime = cancelTime;
	}
	
	public Date getCancelTimeStart(){
		return cancelTimeStart;
	}
	
	public void setCancelTimeStart(Date cancelTimeStart){
		this.cancelTimeStart = cancelTimeStart;
	}
	
	public Date getCancelTimeEnd(){
		return cancelTimeEnd;
	}
	
	public void setCancelTimeEnd(Date cancelTimeEnd){
		this.cancelTimeEnd = cancelTimeEnd;
	}	
	
	public Integer getCancelOperatorType(){
		return cancelOperatorType;
	}
	
	public void setCancelOperatorType(Integer cancelOperatorType){
		this.cancelOperatorType = cancelOperatorType;
	}
	
	public String getCancelOperatorId(){
		return cancelOperatorId;
	}
	
	public void setCancelOperatorId(String cancelOperatorId){
		this.cancelOperatorId = cancelOperatorId;
	}
	
	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public Integer getAssignee() {
		return assignee;
	}

	public void setAssignee(Integer assignee) {
		this.assignee = assignee;
	}

	public Integer getMerchantOrderStatus() {
		return merchantOrderStatus;
	}

	public void setMerchantOrderStatus(Integer merchantOrderStatus) {
		this.merchantOrderStatus = merchantOrderStatus;
	}

	public String getStartParkName() {
		return startParkName;
	}

	public void setStartParkName(String startParkName) {
		this.startParkName = startParkName;
	}

	public String getReturnParkName() {
		return returnParkName;
	}

	public void setReturnParkName(String returnParkName) {
		this.returnParkName = returnParkName;
	}

	public String getOrderSource(){
		return orderSource;
	}
	
	public void setOrderSource(String orderSource){
		this.orderSource = orderSource;
	}
	
	public Integer getFinshiType() {
		return finshiType;
	}

	public void setFinshiType(Integer finshiType) {
		this.finshiType = finshiType;
	}

	public String getFinishReason() {
		return finishReason;
	}

	public void setFinishReason(String finishReason) {
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
		return "OrderDay [orderNo=" + orderNo + ", memberNo=" + memberNo + ", mobilePhone=" + mobilePhone
				+ ", memberName=" + memberName + ", carNo=" + carNo + ", carPlateNo=" + carPlateNo + ", carModelId="
				+ carModelId + ", cityId=" + cityId + ", cityName=" + cityName + ", orderStatus=" + orderStatus
				+ ", orderAmount=" + orderAmount + ", taxiFare=" + taxiFare + ", insurance=" + insurance + ", deposit="
				+ deposit + ", strikeBalanceAmount=" + strikeBalanceAmount + ", discountAmount=" + discountAmount
				+ ", depositType=" + depositType + ", depositTime=" + depositTime + ", payableAmount=" + payableAmount
				+ ", payStatus=" + payStatus + ", paymentMethod=" + paymentMethod + ", paymentTime=" + paymentTime
				+ ", paymentTimeStart=" + paymentTimeStart + ", paymentTimeEnd=" + paymentTimeEnd + ", paymentFlowNo="
				+ paymentFlowNo + ", partTradeFlowNo=" + partTradeFlowNo + ", depositStatus=" + depositStatus
				+ ", overtimeCharge=" + overtimeCharge + ", serviceCharge=" + serviceCharge + ", carRentalAmount="
				+ carRentalAmount + ", appointmentTakeTime=" + appointmentTakeTime + ", appointmentTakeTimeStart="
				+ appointmentTakeTimeStart + ", appointmentTakeTimeEnd=" + appointmentTakeTimeEnd
				+ ", appointmentReturnTime=" + appointmentReturnTime + ", appointmentReturnTimeStart="
				+ appointmentReturnTimeStart + ", appointmentReturnTimeEnd=" + appointmentReturnTimeEnd
				+ ", actualTakeTime=" + actualTakeTime + ", actualTakeTimeStart=" + actualTakeTimeStart
				+ ", actualTakeTimeEnd=" + actualTakeTimeEnd + ", actualReturnTime=" + actualReturnTime
				+ ", actualReturnTimeStart=" + actualReturnTimeStart + ", actualReturnTimeEnd=" + actualReturnTimeEnd
				+ ", amountPaid=" + amountPaid + ", startParkNo=" + startParkNo + ", actualTakePakeName="
				+ actualTakePakeName + ", terminalParkNo=" + terminalParkNo + ", actualTerminalParkName="
				+ actualTerminalParkName + ", actualTerminalParkNo=" + actualTerminalParkNo + ", serviceFeeAmount="
				+ serviceFeeAmount + ", regardlessFranchise=" + regardlessFranchise + ", appointmentTime="
				+ appointmentTime + ", appointmentTimeStart=" + appointmentTimeStart + ", appointmentTimeEnd="
				+ appointmentTimeEnd + ", startBillingTime=" + startBillingTime + ", startBillingTimeStart="
				+ startBillingTimeStart + ", startBillingTimeEnd=" + startBillingTimeEnd + ", finishTime=" + finishTime
				+ ", finishTimeStart=" + finishTimeStart + ", finishTimeEnd=" + finishTimeEnd + ", orderDuration="
				+ orderDuration + ", isIllegal=" + isIllegal + ", isAccident=" + isAccident + ", isFault=" + isFault
				+ ", orderMemo=" + orderMemo + ", isCancel=" + isCancel + ", cancelTime=" + cancelTime
				+ ", cancelTimeStart=" + cancelTimeStart + ", cancelTimeEnd=" + cancelTimeEnd + ", cancelOperatorType="
				+ cancelOperatorType + ", cancelOperatorId=" + cancelOperatorId + ", orderSource=" + orderSource
				+ ", finshiType=" + finshiType + ", finishReason=" + finishReason + ", createTime=" + createTime
				+ ", createTimeStart=" + createTimeStart + ", createTimeEnd=" + createTimeEnd + ", updateTime="
				+ updateTime + ", updateTimeStart=" + updateTimeStart + ", updateTimeEnd=" + updateTimeEnd
				+ ", operatorType=" + operatorType + ", operatorId=" + operatorId + ", merchantId=" + merchantId
				+ ", merchantName=" + merchantName + ", assignee=" + assignee + ", merchantOrderStatus="
				+ merchantOrderStatus + "]";
	}

	public Integer getIsTakeCarDoor() {
		return isTakeCarDoor;
	}

	public void setIsTakeCarDoor(Integer isTakeCarDoor) {
		this.isTakeCarDoor = isTakeCarDoor;
	}

	public Integer getIsBackCarDoor() {
		return isBackCarDoor;
	}

	public void setIsBackCarDoor(Integer isBackCarDoor) {
		this.isBackCarDoor = isBackCarDoor;
	}

	public Integer getIsDelayOrder() {
		return isDelayOrder;
	}

	public void setIsDelayOrder(Integer isDelayOrder) {
		this.isDelayOrder = isDelayOrder;
	}

	public Integer getIsBeforeOrder() {
		return isBeforeOrder;
	}

	public void setIsBeforeOrder(Integer isBeforeOrder) {
		this.isBeforeOrder = isBeforeOrder;
	}

	public Double getReturnAmount() {
		return returnAmount;
	}

	public void setReturnAmount(Double returnAmount) {
		this.returnAmount = returnAmount;
	}

	public Integer getRefundStatus() {
		return refundStatus;
	}

	public void setRefundStatus(Integer refundStatus) {
		this.refundStatus = refundStatus;
	}

	public Date getRefundTime() {
		return refundTime;
	}

	public void setRefundTime(Date refundTime) {
		this.refundTime = refundTime;
	}

	public Double getCancelAmount() {
		return cancelAmount;
	}

	public void setCancelAmount(Double cancelAmount) {
		this.cancelAmount = cancelAmount;
	}

	public Double getRefundAmount() {
		return refundAmount;
	}

	public void setRefundAmount(Double refundAmount) {
		this.refundAmount = refundAmount;
	}

	public Double getTakeCarDoorAmount() {
		return takeCarDoorAmount;
	}

	public void setTakeCarDoorAmount(Double takeCarDoorAmount) {
		this.takeCarDoorAmount = takeCarDoorAmount;
	}
	
}
