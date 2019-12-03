package cn.com.shopec.mapi.order.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import cn.com.shopec.mapi.resource.vo.ParkVOCarStatus;

public class OrderVO  implements Serializable{

	/** 
	*
	* @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么) 
	*/ 
	private static final long serialVersionUID = 1L;
	
	//订单号
	private String orderNo;
	//会员编号
	private String memberNo;
	//预约时间
	private String appointmentTime;
	//订单状态（0、已提交，1、已预约，2、已计费，3、已结束，4、已取消，默认0）
	private Integer orderStatus;
	//订单总金额
	private Double orderAmount;
	//应付金额
	private Double payableAmount;
	//需支付金额
	private Double needPayaAmount;
	//实际付款金额(已支付金额)
	private Double alreadPayAmount;
	//支付状态
	private Integer payStatus;
	//创建时间
	private String createTime;
	// 实际还车地点（场站名称，通过场站编号得到）
	private String actualTerminalLocation;
	//实际取车场站
	private String actualTakeLoacton;
	//积分（在数据库中 暂时没有这个字段，统一默认为0）
	private Integer integral;
	//订单里程
	private Double orderMileage;
	//起步价
	private Double baseFee;
	//折扣金额
	private Double  discountAmount;
	//事故状态()
	private Integer  accidentStatus;
	//订单开始时间
	private String startBillingTime;
	//订单结束时间
	private String   finishTime;
	//订单时长
	private Integer orderDuration;
	//违章状态违章状态(0、违章审核中，1、已违章，2、未违章，默认0)
	private  Integer illegalStatus;
	//车牌号
	private String carPlateNo;
	//品牌名称
	private String  carBrandName;
	//车型名称
	private String carModelName;
	//套餐抵扣金额合计
	private Double packMinutesDiscountAmount;
	//套餐订单 集合
	private List<PricingPackOrderInvoiceVo> pricingPackOrderInvoiceVos;
	//订单警告(0 默认  1 警告)
	private Integer warningOrder;
	//红包车红包是否已下发(1，是 0否)
	private Integer isRedPacketSend;
	
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

	public String getAppointmentTime() {
		return appointmentTime;
	}

	public void setAppointmentTime(String appointmentTime) {
		this.appointmentTime = appointmentTime;
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

	public Double getPayableAmount() {
		return payableAmount;
	}

	public void setPayableAmount(Double payableAmount) {
		this.payableAmount = payableAmount;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public Double getNeedPayaAmount() {
		return needPayaAmount;
	}

	public void setNeedPayaAmount(Double needPayaAmount) {
		this.needPayaAmount = needPayaAmount;
	}

	public Double getAlreadPayAmount() {
		return alreadPayAmount;
	}

	public void setAlreadPayAmount(Double alreadPayAmount) {
		this.alreadPayAmount = alreadPayAmount;
	}

	public Integer getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(Integer payStatus) {
		this.payStatus = payStatus;
	}

	public String getActualTerminalLocation() {
		return actualTerminalLocation;
	}

	public void setActualTerminalLocation(String actualTerminalLocation) {
		this.actualTerminalLocation = actualTerminalLocation;
	}

	public Integer getIntegral() {
		return integral;
	}

	public void setIntegral(Integer integral) {
		this.integral = integral;
	}

	public Double getOrderMileage() {
		return orderMileage;
	}

	public void setOrderMileage(Double orderMileage) {
		this.orderMileage = orderMileage;
	}

	@Override
	public String toString() {
		return "OrderVO [orderNo=" + orderNo + ", memberNo=" + memberNo + ", appointmentTime=" + appointmentTime
				+ ", orderStatus=" + orderStatus + ", orderAmount=" + orderAmount + ", payableAmount=" + payableAmount
				+ ", createTime=" + createTime + "]";
	}

	public Double getBaseFee() {
		return baseFee;
	}

	public void setBaseFee(Double baseFee) {
		this.baseFee = baseFee;
	}

	

	public Double getPackMinutesDiscountAmount() {
		return packMinutesDiscountAmount;
	}

	public void setPackMinutesDiscountAmount(Double packMinutesDiscountAmount) {
		this.packMinutesDiscountAmount = packMinutesDiscountAmount;
	}

	public List<PricingPackOrderInvoiceVo> getPricingPackOrderInvoiceVos() {
		return pricingPackOrderInvoiceVos;
	}

	public void setPricingPackOrderInvoiceVos(List<PricingPackOrderInvoiceVo> pricingPackOrderInvoiceVos) {
		this.pricingPackOrderInvoiceVos = pricingPackOrderInvoiceVos;
	}

	public Double getDiscountAmount() {
		return discountAmount;
	}

	public void setDiscountAmount(Double discountAmount) {
		this.discountAmount = discountAmount;
	}

	public String getActualTakeLoacton() {
		return actualTakeLoacton;
	}

	public void setActualTakeLoacton(String actualTakeLoacton) {
		this.actualTakeLoacton = actualTakeLoacton;
	}

	public Integer getAccidentStatus() {
		return accidentStatus;
	}

	public void setAccidentStatus(Integer accidentStatus) {
		this.accidentStatus = accidentStatus;
	}

	



	public Integer getIllegalStatus() {
		return illegalStatus;
	}

	public void setIllegalStatus(Integer illegalStatus) {
		this.illegalStatus = illegalStatus;
	}

	

	public Integer getOrderDuration() {
		return orderDuration;
	}

	public void setOrderDuration(Integer orderDuration) {
		this.orderDuration = orderDuration;
	}

	public String getCarModelName() {
		return carModelName;
	}

	public void setCarModelName(String carModelName) {
		this.carModelName = carModelName;
	}

	public String getCarBrandName() {
		return carBrandName;
	}

	public void setCarBrandName(String carBrandName) {
		this.carBrandName = carBrandName;
	}

	public String getStartBillingTime() {
		return startBillingTime;
	}

	public void setStartBillingTime(String startBillingTime) {
		this.startBillingTime = startBillingTime;
	}

	public String getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(String finishTime) {
		this.finishTime = finishTime;
	}

	public String getCarPlateNo() {
		return carPlateNo;
	}

	public void setCarPlateNo(String carPlateNo) {
		this.carPlateNo = carPlateNo;
	}

	public Integer getWarningOrder() {
		return warningOrder;
	}

	public void setWarningOrder(Integer warningOrder) {
		this.warningOrder = warningOrder;
	}

	public Integer getIsRedPacketSend() {
		return isRedPacketSend;
	}

	public void setIsRedPacketSend(Integer isRedPacketSend) {
		this.isRedPacketSend = isRedPacketSend;
	}
	
}
