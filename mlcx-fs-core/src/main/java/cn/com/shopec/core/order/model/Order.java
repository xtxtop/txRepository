package cn.com.shopec.core.order.model;

import java.util.Date;

import cn.com.shopec.core.common.Entity;

/**
 * 订单表 数据实体类
 */
public class Order extends Entity<String> {

	private static final long serialVersionUID = 1l;

	/* Auto generated properties start */

	// 订单号
	private String orderNo;
	// 会员编号
	private String memberNo;
	// 会员编号
	private String memberName;
	// 手机号
	private String mobilePhone;
	// 车辆编号
	private String carNo;
	// 车牌号
	private String carPlateNo;
	// 车型id（具体来自数据字典表）
	private String carModelId;
	// 车型名称
	private String carModelName;
	// 城市id（具体来自数据字典表）
	private String cityId;
	// 城市名称
	private String cityName;
	// 会员类型（1、普通会员，2、集团会员，默认1）
	private Integer memberType;
	// 公务订单（0，非公务/私人订单、1，公务订单，默认0）
	private Integer isBizOrder;
	// 订单状态（0、已提交，1、已预约，2、已计费，3、已结束，4、已取消，默认0）
	private Integer orderStatus;
	// 订单总金额
	private Double orderAmount;
	// 冲账金额
	private Double strikeBalanceAmount;
	// 折扣金额
	private Double discountAmount;
	// 套餐抵扣时长
	private Integer packMinutesDiscount;
	// 套餐时长抵扣金额
	private Double packMinutesDiscountAmount;
	// 金额套餐抵扣金额
	private Double packAmountDiscountAmount;
	// 应付金额
	private Double payableAmount;
	// 支付状态（0、未支付，1、已支付，默认0）
	private Integer payStatus;
	// 支付方式（1、支付宝、2、微信）
	private Integer paymentMethod;
	// 支付时间
	private Date paymentTime;
	// 支付时间 时间范围起（查询用）
	private Date paymentTimeStart;
	// 支付时间 时间范围止（查询用）
	private Date paymentTimeEnd;
	// 外部支付流水号（支付宝 微信 等）
	private String paymentFlowNo;
	// 内部支付流水号
	private String partTradeFlowNo;
	// 约取车地点（场站编号）
	private String startParkNo;
	// 实际取车地点
	private String actualTakeLoacton;
	// 还车地点（场站编号）
	private String terminalParkNo;
	// 实际还车地点（场站编号）
	private String actualTerminalParkNo;
	// 预约时间
	private Date appointmentTime;
	// 预约时间 时间范围起（查询用）
	private Date appointmentTimeStart;
	// 预约时间 时间范围止（查询用）
	private Date appointmentTimeEnd;
	// 首次点火时间
	private Date startEngineTime;
	// 首次点火时间 时间范围起（查询用）
	private Date startEngineTimeStart;
	// 首次点火时间 时间范围止（查询用）
	private Date startEngineTimeEnd;
	// 首次开门时间
	private Date openCarDoorTime;
	// 首次开门时间 时间范围起（查询用）
	private Date openCarDoorTimeStart;
	// 首次开门时间 时间范围止（查询用）
	private Date openCarDoorTimeEnd;
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
	// 订单时长(分钟数)
	private Integer orderDuration;
	// 订单里程
	private Double orderMileage;
	// 是否有违章（0，无违章、1，有违章，默认0）
	private Integer isIllegal;
	// 是否有事故（0，无事故、1，有事故，默认0）
	private Integer isAccident;
	// 记录事故时间
	private Date recordAccidentTime;
	// 记录事故时间 时间范围起（查询用）
	private Date recordAccidentTimeStart;
	// 记录事故时间 时间范围止（查询用）
	private Date recordAccidentTimeEnd;
	// 是否有故障（0，无故障、1，有故障，默认0）
	private Integer isFault;
	// 记录故障时间
	private Date recordFaultTime;
	// 记录故障时间 时间范围起（查询用）
	private Date recordFaultTimeStart;
	// 记录故障时间 时间范围止（查询用）
	private Date recordFaultTimeEnd;
	// 记录违章时间
	private Date recordIllegalTime;
	// 记录违章时间 时间范围起（查询用）
	private Date recordIllegalTimeStart;
	// 记录违章时间 时间范围止（查询用）
	private Date recordIllegalTimeEnd;
	// 订单备注
	private String orderMemo;
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
	// 是否删除
	private Integer isDelete;
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
	// 发票id
	private String invioceId;
	// 发票号
	private String invioceNo;
	// 订单来源（2、ios，3、安卓，0、微信）
	private String orderSource;
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
	// 订单结束类型订单结束类型（0、会员结束，1、客服结束，2、自动结束）
	private Integer finishType;
	// 当前月份（用于订单结束计费，博士后获取本月已用时长）
	private String month;
	// 集团名称
	private String companyName;
	// 实际还车点信息
	private String actualTerminalParkName;
	// 附加费用
	private Double serviceFeeAmount;
	// 计费规则编号
	private String ruleNo;
	// 订单开始时车辆里程
	private Double orderStartMileage;
	// 订单结束时车辆里程
	private Double orderFinishMileage;
	// 集团ID
	private String companyId;
	// 场站名称
	private String parkName;
	// 订单数量（报表用）
	private String orderNumber;
	// 里程金额
	private Double mileageAmount;
	// 时长金额
	private Double minutesAmount;
	//取车时经度
	private Double startLongitude;
	//取车时纬度
	private Double startLatitude;
	//取车时地址
	private String startAddress;
	//还车时经度
	private Double terminalLongitude;
	//还车时纬度
	private Double terminalLatitude;
	//还车时地址
	private String terminalAddress;
	//是否场内取车（0.是 1.不是）
	private Integer isTakeInPark;
	//是否场内还车（0.是 1.不是）
	private Integer isReturnInPark;
	//停车位置(0 地面  1 楼上一层  2  楼上 二层   3 楼上三层   4 地下一层 5  地下二层  6  地下三层 )
	private Integer  carPosition;
	//不计免赔
	private Double regardlessFranchise;
	//订单警告(0 默认  1 警告  2 弹框提示)
	private Integer warningOrder;
	//终端编号
	private String deviceNo;
	//车辆状态（1、已启动，2、已熄火）
	private Integer carStatus;
	//我的余额
	private Double memberBalance;
	//会员等级折扣金额
	private Double memberLevelDiscountAmount; 
	//金豆抵扣金额
	private Double glodBeansDedutionAmount;
	//取车时车辆的照片
	private String carTakeUrl;
	//还车时车辆的照片
	private String carBackUrl;
	//取车时描述的细节
	private String carTakeMemo;
	//还车时描述的细节
	private String carBackMemo;
	
	// 报表查询时间
	private String eportTime;
	//新增订单
	private String startTime;
	private String endTime;
	//完成订单
	private String fisStartTime;
	private String fisEndTime;
	//取消订单
	private String calStartTime;
	private String calEndTime;
	//支付时间
	private String payMentTimeS;
	private String payMentTimeE;
	//支付记录时间
	private String paidTimeS;
	private String paidTimeE;
	//红包车红包是否已下发
	private Integer isRedPacketSend;
	// 取车  车辆状态
	private Integer pickupcarstatus;
	//还车状态
	private Integer returncarstatus;
	// // 车辆信息
	// private Car car;
	// // 车辆状态信息
	// private CarStatus carStatus;
	/* Auto generated properties end */

	/* Customized properties start */

	/* Customized properties end */

	/* Auto generated methods start */

	public String getCarTakeUrl() {
		return carTakeUrl;
	}

	public void setCarTakeUrl(String carTakeUrl) {
		this.carTakeUrl = carTakeUrl;
	}

	public String getCarBackUrl() {
		return carBackUrl;
	}

	public void setCarBackUrl(String carBackUrl) {
		this.carBackUrl = carBackUrl;
	}

	public String getCarTakeMemo() {
		return carTakeMemo;
	}

	public void setCarTakeMemo(String carTakeMemo) {
		this.carTakeMemo = carTakeMemo;
	}

	public String getCarBackMemo() {
		return carBackMemo;
	}

	public void setCarBackMemo(String carBackMemo) {
		this.carBackMemo = carBackMemo;
	}

	@Override
	public String getPK() {
		return orderNo;
	}

	public String getPartTradeFlowNo() {
		return partTradeFlowNo;
	}

	public void setPartTradeFlowNo(String partTradeFlowNo) {
		this.partTradeFlowNo = partTradeFlowNo;
	}

	public Integer getFinishType() {
		return finishType;
	}

	public void setFinishType(Integer finishType) {
		this.finishType = finishType;
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

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
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

	public String getCarModelName() {
		return carModelName;
	}

	public void setCarModelName(String carModelName) {
		this.carModelName = carModelName;
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

	public Integer getMemberType() {
		return memberType;
	}

	public void setMemberType(Integer memberType) {
		this.memberType = memberType;
	}

	public Integer getIsBizOrder() {
		return isBizOrder;
	}

	public void setIsBizOrder(Integer isBizOrder) {
		this.isBizOrder = isBizOrder;
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

	public Double getStrikeBalanceAmount() {
		return strikeBalanceAmount;
	}

	public void setStrikeBalanceAmount(Double strikeBalanceAmount) {
		this.strikeBalanceAmount = strikeBalanceAmount;
	}

	public Double getDiscountAmount() {
		return discountAmount;
	}

	public void setDiscountAmount(Double discountAmount) {
		this.discountAmount = discountAmount;
	}

	public Integer getPackMinutesDiscount() {
		return packMinutesDiscount;
	}

	public void setPackMinutesDiscount(Integer packMinutesDiscount) {
		this.packMinutesDiscount = packMinutesDiscount;
	}

	public Double getPackMinutesDiscountAmount() {
		return packMinutesDiscountAmount;
	}

	public void setPackMinutesDiscountAmount(Double packMinutesDiscountAmount) {
		this.packMinutesDiscountAmount = packMinutesDiscountAmount;
	}

	public Double getPackAmountDiscountAmount() {
		return packAmountDiscountAmount;
	}

	public void setPackAmountDiscountAmount(Double packAmountDiscountAmount) {
		this.packAmountDiscountAmount = packAmountDiscountAmount;
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

	public Date getStartEngineTime() {
		return startEngineTime;
	}

	public void setStartEngineTime(Date startEngineTime) {
		this.startEngineTime = startEngineTime;
	}

	public Date getStartEngineTimeStart() {
		return startEngineTimeStart;
	}

	public void setStartEngineTimeStart(Date startEngineTimeStart) {
		this.startEngineTimeStart = startEngineTimeStart;
	}

	public Date getStartEngineTimeEnd() {
		return startEngineTimeEnd;
	}

	public void setStartEngineTimeEnd(Date startEngineTimeEnd) {
		this.startEngineTimeEnd = startEngineTimeEnd;
	}

	public Date getOpenCarDoorTime() {
		return openCarDoorTime;
	}

	public void setOpenCarDoorTime(Date openCarDoorTime) {
		this.openCarDoorTime = openCarDoorTime;
	}

	public Date getOpenCarDoorTimeStart() {
		return openCarDoorTimeStart;
	}

	public void setOpenCarDoorTimeStart(Date openCarDoorTimeStart) {
		this.openCarDoorTimeStart = openCarDoorTimeStart;
	}

	public Date getOpenCarDoorTimeEnd() {
		return openCarDoorTimeEnd;
	}

	public void setOpenCarDoorTimeEnd(Date openCarDoorTimeEnd) {
		this.openCarDoorTimeEnd = openCarDoorTimeEnd;
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

	public Double getOrderMileage() {
		return orderMileage;
	}

	public void setOrderMileage(Double orderMileage) {
		this.orderMileage = orderMileage;
	}

	public Integer getIsIllegal() {
		return isIllegal;
	}

	public void setIsIllegal(Integer isIllegal) {
		this.isIllegal = isIllegal;
	}

	public Integer getIsAccident() {
		return isAccident;
	}

	public void setIsAccident(Integer isAccident) {
		this.isAccident = isAccident;
	}

	public Date getRecordAccidentTime() {
		return recordAccidentTime;
	}

	public void setRecordAccidentTime(Date recordAccidentTime) {
		this.recordAccidentTime = recordAccidentTime;
	}

	public Date getRecordAccidentTimeStart() {
		return recordAccidentTimeStart;
	}

	public void setRecordAccidentTimeStart(Date recordAccidentTimeStart) {
		this.recordAccidentTimeStart = recordAccidentTimeStart;
	}

	public Date getRecordAccidentTimeEnd() {
		return recordAccidentTimeEnd;
	}

	public void setRecordAccidentTimeEnd(Date recordAccidentTimeEnd) {
		this.recordAccidentTimeEnd = recordAccidentTimeEnd;
	}

	public Integer getIsFault() {
		return isFault;
	}

	public void setIsFault(Integer isFault) {
		this.isFault = isFault;
	}

	public Date getRecordFaultTime() {
		return recordFaultTime;
	}

	public void setRecordFaultTime(Date recordFaultTime) {
		this.recordFaultTime = recordFaultTime;
	}

	public Date getRecordFaultTimeStart() {
		return recordFaultTimeStart;
	}

	public void setRecordFaultTimeStart(Date recordFaultTimeStart) {
		this.recordFaultTimeStart = recordFaultTimeStart;
	}

	public Date getRecordFaultTimeEnd() {
		return recordFaultTimeEnd;
	}

	public void setRecordFaultTimeEnd(Date recordFaultTimeEnd) {
		this.recordFaultTimeEnd = recordFaultTimeEnd;
	}

	public Date getRecordIllegalTime() {
		return recordIllegalTime;
	}

	public void setRecordIllegalTime(Date recordIllegalTime) {
		this.recordIllegalTime = recordIllegalTime;
	}

	public Date getRecordIllegalTimeStart() {
		return recordIllegalTimeStart;
	}

	public void setRecordIllegalTimeStart(Date recordIllegalTimeStart) {
		this.recordIllegalTimeStart = recordIllegalTimeStart;
	}

	public Date getRecordIllegalTimeEnd() {
		return recordIllegalTimeEnd;
	}

	public void setRecordIllegalTimeEnd(Date recordIllegalTimeEnd) {
		this.recordIllegalTimeEnd = recordIllegalTimeEnd;
	}

	public String getOrderMemo() {
		return orderMemo;
	}

	public void setOrderMemo(String orderMemo) {
		this.orderMemo = orderMemo;
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

	public Integer getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
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

	public String getInvioceId() {
		return invioceId;
	}

	public void setInvioceId(String invioceId) {
		this.invioceId = invioceId;
	}

	public String getInvioceNo() {
		return invioceNo;
	}

	public void setInvioceNo(String invioceNo) {
		this.invioceNo = invioceNo;
	}

	public String getOrderSource() {
		return orderSource;
	}

	public void setOrderSource(String orderSource) {
		this.orderSource = orderSource;
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

	/* Auto generated methods end */

	/* Customized methods start */

	/* Customized methods end */

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	@Override
	public String toString() {
		return "Order [orderNo=" + orderNo + ", memberNo=" + memberNo + ", memberName=" + memberName + ", mobilePhone="
				+ mobilePhone + ", carNo=" + carNo + ", carPlateNo=" + carPlateNo + ", carModelId=" + carModelId
				+ ", carModelName=" + carModelName + ", cityId=" + cityId + ", cityName=" + cityName + ", memberType="
				+ memberType + ", isBizOrder=" + isBizOrder + ", orderStatus=" + orderStatus + ", orderAmount="
				+ orderAmount + ", strikeBalanceAmount=" + strikeBalanceAmount + ", discountAmount=" + discountAmount
				+ ", packMinutesDiscount=" + packMinutesDiscount + ", packMinutesDiscountAmount="
				+ packMinutesDiscountAmount + ", payableAmount=" + payableAmount + ", payStatus=" + payStatus
				+ ", paymentMethod=" + paymentMethod + ", paymentTime=" + paymentTime + ", paymentTimeStart="
				+ paymentTimeStart + ", paymentTimeEnd=" + paymentTimeEnd + ", paymentFlowNo=" + paymentFlowNo
				+ ", partTradeFlowNo=" + partTradeFlowNo + ", startParkNo=" + startParkNo + ", actualTakeLoacton="
				+ actualTakeLoacton + ", terminalParkNo=" + terminalParkNo + ", actualTerminalParkNo="
				+ actualTerminalParkNo + ", appointmentTime=" + appointmentTime + ", appointmentTimeStart="
				+ appointmentTimeStart + ", appointmentTimeEnd=" + appointmentTimeEnd + ", startEngineTime="
				+ startEngineTime + ", startEngineTimeStart=" + startEngineTimeStart + ", startEngineTimeEnd="
				+ startEngineTimeEnd + ", openCarDoorTime=" + openCarDoorTime + ", openCarDoorTimeStart="
				+ openCarDoorTimeStart + ", openCarDoorTimeEnd=" + openCarDoorTimeEnd + ", startBillingTime="
				+ startBillingTime + ", startBillingTimeStart=" + startBillingTimeStart + ", startBillingTimeEnd="
				+ startBillingTimeEnd + ", finishTime=" + finishTime + ", finishTimeStart=" + finishTimeStart
				+ ", finishTimeEnd=" + finishTimeEnd + ", orderDuration=" + orderDuration + ", orderMileage="
				+ orderMileage + ", isIllegal=" + isIllegal + ", isAccident=" + isAccident + ", recordAccidentTime="
				+ recordAccidentTime + ", recordAccidentTimeStart=" + recordAccidentTimeStart
				+ ", recordAccidentTimeEnd=" + recordAccidentTimeEnd + ", isFault=" + isFault + ", recordFaultTime="
				+ recordFaultTime + ", recordFaultTimeStart=" + recordFaultTimeStart + ", recordFaultTimeEnd="
				+ recordFaultTimeEnd + ", recordIllegalTime=" + recordIllegalTime + ", recordIllegalTimeStart="
				+ recordIllegalTimeStart + ", recordIllegalTimeEnd=" + recordIllegalTimeEnd + ", orderMemo=" + orderMemo
				+ ", cancelTime=" + cancelTime + ", cancelTimeStart=" + cancelTimeStart + ", cancelTimeEnd="
				+ cancelTimeEnd + ", cancelOperatorType=" + cancelOperatorType + ", cancelOperatorId="
				+ cancelOperatorId + ", isDelete=" + isDelete + ", isNeedInvoice=" + isNeedInvoice
				+ ", isInvoiceIssued=" + isInvoiceIssued + ", invoiceTime=" + invoiceTime + ", invoiceTimeStart="
				+ invoiceTimeStart + ", invoiceTimeEnd=" + invoiceTimeEnd + ", invioceId=" + invioceId + ", invioceNo="
				+ invioceNo + ", orderSource=" + orderSource + ", memo=" + memo + ", createTime=" + createTime
				+ ", createTimeStart=" + createTimeStart + ", createTimeEnd=" + createTimeEnd + ", updateTime="
				+ updateTime + ", updateTimeStart=" + updateTimeStart + ", updateTimeEnd=" + updateTimeEnd
				+ ", operatorType=" + operatorType + ", operatorId=" + operatorId + ", finishType=" + finishType
				+ ", month=" + month + ", companyName=" + companyName + ", actualTerminalParkName="
				+ actualTerminalParkName + ", serviceFeeAmount=" + serviceFeeAmount + ", ruleNo=" + ruleNo
				+ ", orderStartMileage=" + orderStartMileage + ", orderFinishMileage=" + orderFinishMileage
				+ ", companyId=" + companyId + ", parkName=" + parkName + ", orderNumber=" + orderNumber
				+ ", mileageAmount=" + mileageAmount + ", minutesAmount=" + minutesAmount + ", eportTime=" + eportTime
				+ ", startTime=" + startTime + ", endTime=" + endTime + "]";
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getActualTerminalParkName() {
		return actualTerminalParkName;
	}

	public void setActualTerminalParkName(String actualTerminalParkName) {
		this.actualTerminalParkName = actualTerminalParkName;
	}

	public Double getServiceFeeAmount() {
		return serviceFeeAmount;
	}

	public void setServiceFeeAmount(Double serviceFeeAmount) {
		this.serviceFeeAmount = serviceFeeAmount;
	}

	public String getRuleNo() {
		return ruleNo;
	}

	public void setRuleNo(String ruleNo) {
		this.ruleNo = ruleNo;
	}

	public Double getOrderStartMileage() {
		return orderStartMileage;
	}

	public void setOrderStartMileage(Double orderStartMileage) {
		this.orderStartMileage = orderStartMileage;
	}

	public Double getOrderFinishMileage() {
		return orderFinishMileage;
	}

	public void setOrderFinishMileage(Double orderFinishMileage) {
		this.orderFinishMileage = orderFinishMileage;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getParkName() {
		return parkName;
	}

	public void setParkName(String parkName) {
		this.parkName = parkName;
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public Double getMileageAmount() {
		return mileageAmount;
	}

	public void setMileageAmount(Double mileageAmount) {
		this.mileageAmount = mileageAmount;
	}

	public Double getMinutesAmount() {
		return minutesAmount;
	}

	public void setMinutesAmount(Double minutesAmount) {
		this.minutesAmount = minutesAmount;
	}

	public String getEportTime() {
		return eportTime;
	}

	public void setEportTime(String eportTime) {
		this.eportTime = eportTime;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public Double getStartLongitude() {
		return startLongitude;
	}

	public void setStartLongitude(Double startLongitude) {
		this.startLongitude = startLongitude;
	}

	public Double getStartLatitude() {
		return startLatitude;
	}

	public void setStartLatitude(Double startLatitude) {
		this.startLatitude = startLatitude;
	}

	public String getStartAddress() {
		return startAddress;
	}

	public void setStartAddress(String startAddress) {
		this.startAddress = startAddress;
	}

	public Double getTerminalLongitude() {
		return terminalLongitude;
	}

	public void setTerminalLongitude(Double terminalLongitude) {
		this.terminalLongitude = terminalLongitude;
	}

	public Double getTerminalLatitude() {
		return terminalLatitude;
	}

	public void setTerminalLatitude(Double terminalLatitude) {
		this.terminalLatitude = terminalLatitude;
	}

	public String getTerminalAddress() {
		return terminalAddress;
	}

	public void setTerminalAddress(String terminalAddress) {
		this.terminalAddress = terminalAddress;
	}

	public Integer getIsTakeInPark() {
		return isTakeInPark;
	}

	public void setIsTakeInPark(Integer isTakeInPark) {
		this.isTakeInPark = isTakeInPark;
	}

	public Integer getIsReturnInPark() {
		return isReturnInPark;
	}

	public void setIsReturnInPark(Integer isReturnInPark) {
		this.isReturnInPark = isReturnInPark;
	}

	public Integer getCarPosition() {
		return carPosition;
	}

	public void setCarPosition(Integer carPosition) {
		this.carPosition = carPosition;
	}

	public Double getRegardlessFranchise() {
		return regardlessFranchise;
	}

	public void setRegardlessFranchise(Double regardlessFranchise) {
		this.regardlessFranchise = regardlessFranchise;
	}

	public Integer getWarningOrder() {
		return warningOrder;
	}

	public void setWarningOrder(Integer warningOrder) {
		this.warningOrder = warningOrder;
	}

	public String getDeviceNo() {
		return deviceNo;
	}

	public void setDeviceNo(String deviceNo) {
		this.deviceNo = deviceNo;
	}

	public Integer getCarStatus() {
		return carStatus;
	}

	public void setCarStatus(Integer carStatus) {
		this.carStatus = carStatus;
	}

	public Double getMemberBalance() {
		return memberBalance;
	}

	public void setMemberBalance(Double memberBalance) {
		this.memberBalance = memberBalance;
	}

	public Double getMemberLevelDiscountAmount() {
		return memberLevelDiscountAmount;
	}

	public void setMemberLevelDiscountAmount(Double memberLevelDiscountAmount) {
		this.memberLevelDiscountAmount = memberLevelDiscountAmount;
	}

	public Double getGlodBeansDedutionAmount() {
		return glodBeansDedutionAmount;
	}

	public void setGlodBeansDedutionAmount(Double glodBeansDedutionAmount) {
		this.glodBeansDedutionAmount = glodBeansDedutionAmount;
	}

	public String getFisStartTime() {
		return fisStartTime;
	}

	public void setFisStartTime(String fisStartTime) {
		this.fisStartTime = fisStartTime;
	}

	public String getFisEndTime() {
		return fisEndTime;
	}

	public void setFisEndTime(String fisEndTime) {
		this.fisEndTime = fisEndTime;
	}

	public String getCalStartTime() {
		return calStartTime;
	}

	public void setCalStartTime(String calStartTime) {
		this.calStartTime = calStartTime;
	}

	public String getCalEndTime() {
		return calEndTime;
	}

	public void setCalEndTime(String calEndTime) {
		this.calEndTime = calEndTime;
	}

	public String getPayMentTimeS() {
		return payMentTimeS;
	}

	public void setPayMentTimeS(String payMentTimeS) {
		this.payMentTimeS = payMentTimeS;
	}

	public String getPayMentTimeE() {
		return payMentTimeE;
	}

	public void setPayMentTimeE(String payMentTimeE) {
		this.payMentTimeE = payMentTimeE;
	}

	public String getPaidTimeS() {
		return paidTimeS;
	}

	public void setPaidTimeS(String paidTimeS) {
		this.paidTimeS = paidTimeS;
	}

	public String getPaidTimeE() {
		return paidTimeE;
	}

	public void setPaidTimeE(String paidTimeE) {
		this.paidTimeE = paidTimeE;
	}

	public Integer getIsRedPacketSend() {
		return isRedPacketSend;
	}

	public void setIsRedPacketSend(Integer isRedPacketSend) {
		this.isRedPacketSend = isRedPacketSend;
	}

	public Integer getPickupcarstatus() {
		return pickupcarstatus;
	}

	public void setPickupcarstatus(Integer pickupcarstatus) {
		this.pickupcarstatus = pickupcarstatus;
	}

	public Integer getReturncarstatus() {
		return returncarstatus;
	}

	public void setReturncarstatus(Integer returncarstatus) {
		this.returncarstatus = returncarstatus;
	}
	
}
