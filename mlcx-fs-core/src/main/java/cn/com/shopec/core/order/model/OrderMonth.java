package cn.com.shopec.core.order.model;

import java.util.Date;

import cn.com.shopec.core.common.Entity;

/**
 * 月租订单表,一个月租订单内可以有多个车 数据实体类
 */
public class OrderMonth extends Entity<String> {

	private static final long serialVersionUID = 1l;

	/* Auto generated properties start */

	// 订单号
	private String orderNo;
	// 会员id
	private String memberNo;
	// 会员名称
	private String memberName;
	// 手机号
	private String mobilePhone;
	// 车辆编号
	private String carNo;
	// 车牌号
	private String carPlateNo;
	// 车型id
	private String carModelId;
	// 城市id（具体来自数据字典表）
	private String cityId;
	// 城市名称
	private String cityName;
	// 订单状态（1、已提车，2、已还车，3、已结束，4、已取消，默认1）
	private Integer orderStatus;
	// 订单总金额
	private Double orderAmount;
	// 押金金额
	private Double deposit;
	// 保险金额
	private Double insurance;
	// 折扣金额
	private Double discountAmount;
	// 应付金额
	private Double payableAmount;
	// 支付状态（0、未支付，1、已支付，默认0）
	private Integer payStatus;
	// 支付方式（1、支付宝、2、微信，3、线下，4、其他）
	private Integer paymentMethod;
	// 支付时间
	private Date paymentTime;
	// 支付时间 时间范围起（查询用）
	private Date paymentTimeStart;
	// 支付时间 时间范围止（查询用）
	private Date paymentTimeEnd;
	// 支付流水号
	private String paymentFlowNo;
	// 发票id
	private String invoiceId;
	// 约取车地点（场站编号）
	private String startParkNo;
	// 实际取车地点
	private String actualTakeLoacton;
	// 还车地点（场站编号）
	private String terminalParkNo;
	// 实际还车地点（场站编号）
	private String actualTerminalParkNo;
	// 实际还车地点
	private String actualTerminalParkName;
	// 预约时间
	private Date appointmentTime;
	// 预约时间 时间范围起（查询用）
	private Date appointmentTimeStart;
	// 预约时间 时间范围止（查询用）
	private Date appointmentTimeEnd;
	// 计费时间
	private Date startBillingTime;
	// 计费时间 时间范围起（查询用）
	private Date startBillingTimeStart;
	// 计费时间 时间范围止（查询用）
	private Date startBillingTimeEnd;
	// 结束时间
	private Date finishTime;
	// 结束时间 时间范围起（查询用）
	private Date finishTimeStart;
	// 结束时间 时间范围止（查询用）
	private Date finishTimeEnd;
	// 订单时长
	private Integer orderDuration;
	// 订单备注
	private String orderMemo;
	// 是否已取消（0，未取消，1、已取消，默认0）
	private Integer isCancel;
	// 取消时间
	private Date cancelTime;
	// 取消时间 时间范围起（查询用）
	private Date cancelTimeStart;
	// 取消时间 时间范围止（查询用）
	private Date cancelTimeEnd;
	// 订单取消操作人类型
	private Integer cancelOperatorType;
	// 订单取消操作人id
	private String cancelOperatorId;
	// 是否需开票（0，不开票，1、需开票，默认0）
	private Integer isNeedInvoice;
	// 是否已开发票（0，未开票，1，已开票，默认0）
	private Integer isInvoiceIssued;
	// 开票时间
	private Date invoiceTime;
	// 开票时间 时间范围起（查询用）
	private Date invoiceTimeStart;
	// 开票时间 时间范围止（查询用）
	private Date invoiceTimeEnd;
	// 发票号
	private String invioceNo;
	// 备注
	private String memo;
	// 创建时间
	private Date createTime;
	// 创建时间 时间范围起（查询用）
	private Date createTimeStart;
	// 创建时间 时间范围止（查询用）
	private Date createTimeEnd;
	// 更新时间
	private Date updateTime;
	// 更新时间 时间范围起（查询用）
	private Date updateTimeStart;
	// 更新时间 时间范围止（查询用）
	private Date updateTimeEnd;
	// 操作人类型
	private Integer operatorType;
	// 操作人id
	private String operatorId;
	// 公司
	private String company;
	// 提出租数量
	private Integer proposeNumber;
	// 提出开始租时间
	private Date proposeStartTime;
	// 提出开始租时间 时间范围起（查询用）
	private Date proposeStartTimeStart;
	// 提出开始租时间 时间范围止（查询用）
	private Date proposeStartTimeEnd;
	// 提出结束租时间
	private Date proposeEndtTime;
	// 提出结束租时间 时间范围起（查询用）
	private Date proposeEndtTimeStart;
	// 提出结束租时间 时间范围止（查询用）
	private Date proposeEndtTimeEnd;
	// 实际租数量
	private Integer actualNumber;
	// 实际租开始时间
	private Date actualStartTime;
	// 实际租开始时间 时间范围起（查询用）
	private Date actualStartTimeStart;
	// 实际租开始时间 时间范围止（查询用）
	private Date actualStartTimeEnd;
	// 实际结束时间
	private Date actualEndTime;
	// 实际结束时间 时间范围起（查询用）
	private Date actualEndTimeStart;
	// 实际结束时间 时间范围止（查询用）
	private Date actualEndTimeEnd;
	// 订单车辆编号
	private String[] cars;

	/* Auto generated properties end */

	/* Customized properties start */

	/* Customized properties end */

	/* Auto generated methods start */

	@Override
	public String getPK() {
		return orderNo;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getMemberNo() {
		return memberNo;
	}

	public void setMemberNo(String memberNo) {
		this.memberNo = memberNo;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public String getCarNo() {
		return carNo;
	}

	public void setCarNo(String carNo) {
		this.carNo = carNo;
	}

	public String getCarPlateNo() {
		return carPlateNo;
	}

	public void setCarPlateNo(String carPlateNo) {
		this.carPlateNo = carPlateNo;
	}

	public String getCarModelId() {
		return carModelId;
	}

	public void setCarModelId(String carModelId) {
		this.carModelId = carModelId;
	}

	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public Integer getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(Integer orderStatus) {
		this.orderStatus = orderStatus;
	}

	public Double getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(Double orderAmount) {
		this.orderAmount = orderAmount;
	}

	public Double getDeposit() {
		return deposit;
	}

	public void setDeposit(Double deposit) {
		this.deposit = deposit;
	}

	public Double getInsurance() {
		return insurance;
	}

	public void setInsurance(Double insurance) {
		this.insurance = insurance;
	}

	public Double getDiscountAmount() {
		return discountAmount;
	}

	public void setDiscountAmount(Double discountAmount) {
		this.discountAmount = discountAmount;
	}

	public Double getPayableAmount() {
		return payableAmount;
	}

	public void setPayableAmount(Double payableAmount) {
		this.payableAmount = payableAmount;
	}

	public Integer getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(Integer payStatus) {
		this.payStatus = payStatus;
	}

	public Integer getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(Integer paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public Date getPaymentTime() {
		return paymentTime;
	}

	public void setPaymentTime(Date paymentTime) {
		this.paymentTime = paymentTime;
	}

	public Date getPaymentTimeStart() {
		return paymentTimeStart;
	}

	public void setPaymentTimeStart(Date paymentTimeStart) {
		this.paymentTimeStart = paymentTimeStart;
	}

	public Date getPaymentTimeEnd() {
		return paymentTimeEnd;
	}

	public void setPaymentTimeEnd(Date paymentTimeEnd) {
		this.paymentTimeEnd = paymentTimeEnd;
	}

	public String getPaymentFlowNo() {
		return paymentFlowNo;
	}

	public void setPaymentFlowNo(String paymentFlowNo) {
		this.paymentFlowNo = paymentFlowNo;
	}

	public String getInvoiceId() {
		return invoiceId;
	}

	public void setInvoiceId(String invoiceId) {
		this.invoiceId = invoiceId;
	}

	public String getStartParkNo() {
		return startParkNo;
	}

	public void setStartParkNo(String startParkNo) {
		this.startParkNo = startParkNo;
	}

	public String getActualTakeLoacton() {
		return actualTakeLoacton;
	}

	public void setActualTakeLoacton(String actualTakeLoacton) {
		this.actualTakeLoacton = actualTakeLoacton;
	}

	public String getTerminalParkNo() {
		return terminalParkNo;
	}

	public void setTerminalParkNo(String terminalParkNo) {
		this.terminalParkNo = terminalParkNo;
	}

	public String getActualTerminalParkNo() {
		return actualTerminalParkNo;
	}

	public void setActualTerminalParkNo(String actualTerminalParkNo) {
		this.actualTerminalParkNo = actualTerminalParkNo;
	}

	public Date getAppointmentTime() {
		return appointmentTime;
	}

	public void setAppointmentTime(Date appointmentTime) {
		this.appointmentTime = appointmentTime;
	}

	public Date getAppointmentTimeStart() {
		return appointmentTimeStart;
	}

	public void setAppointmentTimeStart(Date appointmentTimeStart) {
		this.appointmentTimeStart = appointmentTimeStart;
	}

	public Date getAppointmentTimeEnd() {
		return appointmentTimeEnd;
	}

	public void setAppointmentTimeEnd(Date appointmentTimeEnd) {
		this.appointmentTimeEnd = appointmentTimeEnd;
	}

	public Date getStartBillingTime() {
		return startBillingTime;
	}

	public void setStartBillingTime(Date startBillingTime) {
		this.startBillingTime = startBillingTime;
	}

	public Date getStartBillingTimeStart() {
		return startBillingTimeStart;
	}

	public void setStartBillingTimeStart(Date startBillingTimeStart) {
		this.startBillingTimeStart = startBillingTimeStart;
	}

	public Date getStartBillingTimeEnd() {
		return startBillingTimeEnd;
	}

	public void setStartBillingTimeEnd(Date startBillingTimeEnd) {
		this.startBillingTimeEnd = startBillingTimeEnd;
	}

	public Date getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(Date finishTime) {
		this.finishTime = finishTime;
	}

	public Date getFinishTimeStart() {
		return finishTimeStart;
	}

	public void setFinishTimeStart(Date finishTimeStart) {
		this.finishTimeStart = finishTimeStart;
	}

	public Date getFinishTimeEnd() {
		return finishTimeEnd;
	}

	public void setFinishTimeEnd(Date finishTimeEnd) {
		this.finishTimeEnd = finishTimeEnd;
	}

	public Integer getOrderDuration() {
		return orderDuration;
	}

	public void setOrderDuration(Integer orderDuration) {
		this.orderDuration = orderDuration;
	}

	public String getOrderMemo() {
		return orderMemo;
	}

	public void setOrderMemo(String orderMemo) {
		this.orderMemo = orderMemo;
	}

	public Integer getIsCancel() {
		return isCancel;
	}

	public void setIsCancel(Integer isCancel) {
		this.isCancel = isCancel;
	}

	public Date getCancelTime() {
		return cancelTime;
	}

	public void setCancelTime(Date cancelTime) {
		this.cancelTime = cancelTime;
	}

	public Date getCancelTimeStart() {
		return cancelTimeStart;
	}

	public void setCancelTimeStart(Date cancelTimeStart) {
		this.cancelTimeStart = cancelTimeStart;
	}

	public Date getCancelTimeEnd() {
		return cancelTimeEnd;
	}

	public void setCancelTimeEnd(Date cancelTimeEnd) {
		this.cancelTimeEnd = cancelTimeEnd;
	}

	public Integer getCancelOperatorType() {
		return cancelOperatorType;
	}

	public void setCancelOperatorType(Integer cancelOperatorType) {
		this.cancelOperatorType = cancelOperatorType;
	}

	public String getCancelOperatorId() {
		return cancelOperatorId;
	}

	public void setCancelOperatorId(String cancelOperatorId) {
		this.cancelOperatorId = cancelOperatorId;
	}

	public Integer getIsNeedInvoice() {
		return isNeedInvoice;
	}

	public void setIsNeedInvoice(Integer isNeedInvoice) {
		this.isNeedInvoice = isNeedInvoice;
	}

	public Integer getIsInvoiceIssued() {
		return isInvoiceIssued;
	}

	public void setIsInvoiceIssued(Integer isInvoiceIssued) {
		this.isInvoiceIssued = isInvoiceIssued;
	}

	public Date getInvoiceTime() {
		return invoiceTime;
	}

	public void setInvoiceTime(Date invoiceTime) {
		this.invoiceTime = invoiceTime;
	}

	public Date getInvoiceTimeStart() {
		return invoiceTimeStart;
	}

	public void setInvoiceTimeStart(Date invoiceTimeStart) {
		this.invoiceTimeStart = invoiceTimeStart;
	}

	public Date getInvoiceTimeEnd() {
		return invoiceTimeEnd;
	}

	public void setInvoiceTimeEnd(Date invoiceTimeEnd) {
		this.invoiceTimeEnd = invoiceTimeEnd;
	}

	public String getInvioceNo() {
		return invioceNo;
	}

	public void setInvioceNo(String invioceNo) {
		this.invioceNo = invioceNo;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getCreateTimeStart() {
		return createTimeStart;
	}

	public void setCreateTimeStart(Date createTimeStart) {
		this.createTimeStart = createTimeStart;
	}

	public Date getCreateTimeEnd() {
		return createTimeEnd;
	}

	public void setCreateTimeEnd(Date createTimeEnd) {
		this.createTimeEnd = createTimeEnd;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Date getUpdateTimeStart() {
		return updateTimeStart;
	}

	public void setUpdateTimeStart(Date updateTimeStart) {
		this.updateTimeStart = updateTimeStart;
	}

	public Date getUpdateTimeEnd() {
		return updateTimeEnd;
	}

	public void setUpdateTimeEnd(Date updateTimeEnd) {
		this.updateTimeEnd = updateTimeEnd;
	}

	public Integer getOperatorType() {
		return operatorType;
	}

	public void setOperatorType(Integer operatorType) {
		this.operatorType = operatorType;
	}

	public String getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(String operatorId) {
		this.operatorId = operatorId;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public Integer getProposeNumber() {
		return proposeNumber;
	}

	public void setProposeNumber(Integer proposeNumber) {
		this.proposeNumber = proposeNumber;
	}

	public Date getProposeStartTime() {
		return proposeStartTime;
	}

	public void setProposeStartTime(Date proposeStartTime) {
		this.proposeStartTime = proposeStartTime;
	}

	public Date getProposeStartTimeStart() {
		return proposeStartTimeStart;
	}

	public void setProposeStartTimeStart(Date proposeStartTimeStart) {
		this.proposeStartTimeStart = proposeStartTimeStart;
	}

	public Date getProposeStartTimeEnd() {
		return proposeStartTimeEnd;
	}

	public void setProposeStartTimeEnd(Date proposeStartTimeEnd) {
		this.proposeStartTimeEnd = proposeStartTimeEnd;
	}

	public Date getProposeEndtTime() {
		return proposeEndtTime;
	}

	public void setProposeEndtTime(Date proposeEndtTime) {
		this.proposeEndtTime = proposeEndtTime;
	}

	public Date getProposeEndtTimeStart() {
		return proposeEndtTimeStart;
	}

	public void setProposeEndtTimeStart(Date proposeEndtTimeStart) {
		this.proposeEndtTimeStart = proposeEndtTimeStart;
	}

	public Date getProposeEndtTimeEnd() {
		return proposeEndtTimeEnd;
	}

	public void setProposeEndtTimeEnd(Date proposeEndtTimeEnd) {
		this.proposeEndtTimeEnd = proposeEndtTimeEnd;
	}

	public Integer getActualNumber() {
		return actualNumber;
	}

	public void setActualNumber(Integer actualNumber) {
		this.actualNumber = actualNumber;
	}

	public Date getActualStartTime() {
		return actualStartTime;
	}

	public void setActualStartTime(Date actualStartTime) {
		this.actualStartTime = actualStartTime;
	}

	public Date getActualStartTimeStart() {
		return actualStartTimeStart;
	}

	public void setActualStartTimeStart(Date actualStartTimeStart) {
		this.actualStartTimeStart = actualStartTimeStart;
	}

	public Date getActualStartTimeEnd() {
		return actualStartTimeEnd;
	}

	public void setActualStartTimeEnd(Date actualStartTimeEnd) {
		this.actualStartTimeEnd = actualStartTimeEnd;
	}

	public Date getActualEndTime() {
		return actualEndTime;
	}

	public void setActualEndTime(Date actualEndTime) {
		this.actualEndTime = actualEndTime;
	}

	public Date getActualEndTimeStart() {
		return actualEndTimeStart;
	}

	public void setActualEndTimeStart(Date actualEndTimeStart) {
		this.actualEndTimeStart = actualEndTimeStart;
	}

	public Date getActualEndTimeEnd() {
		return actualEndTimeEnd;
	}

	public void setActualEndTimeEnd(Date actualEndTimeEnd) {
		this.actualEndTimeEnd = actualEndTimeEnd;
	}

	/* Auto generated methods end */

	/* Customized methods start */

	/* Customized methods end */

	@Override
	public String toString() {
		return "OrderMonth [" + "orderNo = " + orderNo + ", memberNo = " + memberNo + ", mobilePhone = " + mobilePhone
				+ ", carNo = " + carNo + ", carPlateNo = " + carPlateNo + ", carModelId = " + carModelId + ", cityId = "
				+ cityId + ", cityName = " + cityName + ", orderStatus = " + orderStatus + ", orderAmount = "
				+ orderAmount + ", deposit = " + deposit + ", insurance = " + insurance + ", discountAmount = "
				+ discountAmount + ", payableAmount = " + payableAmount + ", payStatus = " + payStatus
				+ ", paymentMethod = " + paymentMethod + ", paymentTime = " + paymentTime + ", paymentTimeStart = "
				+ paymentTimeStart + ", paymentTimeEnd = " + paymentTimeEnd + ", paymentFlowNo = " + paymentFlowNo
				+ ", invoiceId = " + invoiceId + ", startParkNo = " + startParkNo + ", actualTakeLoacton = "
				+ actualTakeLoacton + ", terminalParkNo = " + terminalParkNo + ", actualTerminalParkNo = "
				+ actualTerminalParkNo + ", appointmentTime = " + appointmentTime + ", appointmentTimeStart = "
				+ appointmentTimeStart + ", appointmentTimeEnd = " + appointmentTimeEnd + ", startBillingTime = "
				+ startBillingTime + ", startBillingTimeStart = " + startBillingTimeStart + ", startBillingTimeEnd = "
				+ startBillingTimeEnd + ", finishTime = " + finishTime + ", finishTimeStart = " + finishTimeStart
				+ ", finishTimeEnd = " + finishTimeEnd + ", orderDuration = " + orderDuration + ", orderMemo = "
				+ orderMemo + ", isCancel = " + isCancel + ", cancelTime = " + cancelTime + ", cancelTimeStart = "
				+ cancelTimeStart + ", cancelTimeEnd = " + cancelTimeEnd + ", cancelOperatorType = "
				+ cancelOperatorType + ", cancelOperatorId = " + cancelOperatorId + ", isNeedInvoice = " + isNeedInvoice
				+ ", isInvoiceIssued = " + isInvoiceIssued + ", invoiceTime = " + invoiceTime + ", invoiceTimeStart = "
				+ invoiceTimeStart + ", invoiceTimeEnd = " + invoiceTimeEnd + ", invioceNo = " + invioceNo + ", memo = "
				+ memo + ", createTime = " + createTime + ", createTimeStart = " + createTimeStart
				+ ", createTimeEnd = " + createTimeEnd + ", updateTime = " + updateTime + ", updateTimeStart = "
				+ updateTimeStart + ", updateTimeEnd = " + updateTimeEnd + ", operatorType = " + operatorType
				+ ", operatorId = " + operatorId + ", company = " + company + ", proposeNumber = " + proposeNumber
				+ ", proposeStartTime = " + proposeStartTime + ", proposeStartTimeStart = " + proposeStartTimeStart
				+ ", proposeStartTimeEnd = " + proposeStartTimeEnd + ", proposeEndtTime = " + proposeEndtTime
				+ ", proposeEndtTimeStart = " + proposeEndtTimeStart + ", proposeEndtTimeEnd = " + proposeEndtTimeEnd
				+ ", actualNumber = " + actualNumber + ", actualStartTime = " + actualStartTime
				+ ", actualStartTimeStart = " + actualStartTimeStart + ", actualStartTimeEnd = " + actualStartTimeEnd
				+ ", actualEndTime = " + actualEndTime + ", actualEndTimeStart = " + actualEndTimeStart
				+ ", actualEndTimeEnd = " + actualEndTimeEnd + "]";
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public String getActualTerminalParkName() {
		return actualTerminalParkName;
	}

	public void setActualTerminalParkName(String actualTerminalParkName) {
		this.actualTerminalParkName = actualTerminalParkName;
	}

	public String[] getCars() {
		return cars;
	}

	public void setCars(String[] cars) {
		this.cars = cars;
	}
}
