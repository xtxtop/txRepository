package cn.com.shopec.mapi.order.vo;

import java.io.Serializable;
import java.util.List;

import cn.com.shopec.core.marketing.model.CarRedPacketVo;

public class OrderVOTrip  implements Serializable{

	
	/** 
	*
	* @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么) 
	*/ 
	private static final long serialVersionUID = 1L;
	//订单号
	private String orderNo;
	//会员编号
	private String memberNo;
	//车牌号
	private String carPlateNo;
	// 充电状态，0 –未充电，1正在充电
	private Integer chargeState;
	//车型名称
	private String carModelName;
	//实际取车地点
	private String actualTakeLoacton;
	//计费时间(开门时间)
	private String startBillingTime;
	//订单里程
	private Double orderMileage;
	//实时计费
	private Double nowAmount;
	//订单时长(分钟数)
	private Integer orderDuration;
	
	// 实际还车地点（场站名称，通过场站编号得到）
	private String actualTerminalLocation;
	// 结束时间
	private String finishTime;
	//积分（在数据库中 暂时没有这个字段，统一默认为0）
	private Integer integral;
	//应付金额
	private Double payableAmount;
	//需支付金额
	private Double needPayaAmount;
	//实际付款金额
	private Double alreadPayAmount;
	//支付状态
	private Integer payStatus;
	//订单状态（0、已提交，1、已预约，2、已计费，3、已结束，4、已取消，默认0）
	private Integer orderStatus;
	//车辆品牌
	private String carBrandName;
	//可行驶里程
	private Double rangeMileage;
	// 剩余电量百分比(0%~100%)
		private Double power;
	//经纬度
	private Double longitude;
	private Double latitude;
	//折扣金额
	private Double  discountAmount;
	//套餐抵扣金额合计
	private Double packMinutesDiscountAmount;
	//套餐订单 集合
	private List<PricingPackOrderInvoiceVo> pricingPackOrderInvoiceVos;
	// 是否有违章（0，无违章、1，有违章，默认0）
	private Integer isIllegal;
	// 是否有事故（0，无事故、1，有事故，默认0）
	private Integer isAccident;
	// 订单是否已评价（0，无评价、1，已评价，默认0）
	private Integer isCommented;
	//实际取车场站编号
	private  String parkNo;
	//时长费用
	private Double minutesAmount;
	//里程费用
	private Double mileageAmount;
	//设置所有车辆能否在场站外还车
	private Integer returnCarStatus;
	//起步价(元)
	private  Double  baseFee;
	//支付方式
	private Integer payType;
	//计费封顶
	private Double billingCapPerDaySum;
	//封顶天数
	private  Integer daySum;
	
	//取车服务费
	private Double startParkAmount;
	//还车服务费
	private Double finishParkAmount;
	// 附加费用
	private Double serviceFeeAmount;
	//是否全部抵扣
	private boolean discount;
	//不计免赔
	private Double regardlessFranchise;
		//订单警告(0 默认  1 警告  2 弹框提示)
	private Integer warningOrder;
	//分享标题
	private String shareTitle;
	//分享内容
	private String shareContent;
	//IconUrl
	private String iconUrl;
	//分享h5url
	private String shareUrl;
	//是否完成(分享功能)
	private String isFinish;
	//会员等级折扣金额
	private Double memberLevelDiscountAmount;
	//订单是否前freeMinutes分钟免费
	private Integer isOrderFreeMinutes;
	//免费时长
	private Integer freeMinutes;
	//金豆抵扣金额
	private Double glodBeansDedutionAmount;
	//	订单封顶金额是否计算里程费用（里程费是否包含在封顶）
	private  Integer isSumFreeMileage;
	//是否红包车(1 是)
	private String isCarRedPakcet;
	private CarRedPacketVo carRedPacketVo;
	//高峰时段的值
	private List<PearTimeCostVo> pearTimeCostVo;
//	//除过高峰时段的时长其余的时长和金额
//	private Integer ordinaryTimeTotalMinutes;
//	private Double ordinaryTimeTotalMinutesAmount;
//	//除过高峰时段的里程
//	private Double ordinaryTimeTotalMileage;
//	private Double ordinaryTimeTotalMileageAmount;
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
	public String getCarPlateNo() {
		return carPlateNo;
	}
	public void setCarPlateNo(String carPlateNo) {
		this.carPlateNo = carPlateNo;
	}
	public String getCarModelName() {
		return carModelName;
	}
	public void setCarModelName(String carModelName) {
		this.carModelName = carModelName;
	}
	public String getActualTakeLoacton() {
		return actualTakeLoacton;
	}
	public void setActualTakeLoacton(String actualTakeLoacton) {
		this.actualTakeLoacton = actualTakeLoacton;
	}
	public String getStartBillingTime() {
		return startBillingTime;
	}
	public void setStartBillingTime(String startBillingTime) {
		this.startBillingTime = startBillingTime;
	}
	public Double getOrderMileage() {
		return orderMileage;
	}
	public void setOrderMileage(Double orderMileage) {
		this.orderMileage = orderMileage;
	}
	public Double getNowAmount() {
		return nowAmount;
	}
	public void setNowAmount(Double nowAmount) {
		this.nowAmount = nowAmount;
	}
	public Integer getOrderDuration() {
		return orderDuration;
	}
	public void setOrderDuration(Integer orderDuration) {
		this.orderDuration = orderDuration;
	}
	public String getActualTerminalLocation() {
		return actualTerminalLocation;
	}
	public void setActualTerminalLocation(String actualTerminalLocation) {
		this.actualTerminalLocation = actualTerminalLocation;
	}
	public String getFinishTime() {
		return finishTime;
	}
	public void setFinishTime(String finishTime) {
		this.finishTime = finishTime;
	}
	public Integer getIntegral() {
		return integral;
	}
	public void setIntegral(Integer integral) {
		this.integral = integral;
	}
	public Double getPayableAmount() {
		return payableAmount;
	}
	public void setPayableAmount(Double payableAmount) {
		this.payableAmount = payableAmount;
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
	public Integer getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(Integer orderStatus) {
		this.orderStatus = orderStatus;
	}
	public String getCarBrandName() {
		return carBrandName;
	}
	public void setCarBrandName(String carBrandName) {
		this.carBrandName = carBrandName;
	}
	public Double getRangeMileage() {
		return rangeMileage;
	}
	public void setRangeMileage(Double rangeMileage) {
		this.rangeMileage = rangeMileage;
	}
	public Double getLongitude() {
		return longitude;
	}
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
	public Double getLatitude() {
		return latitude;
	}
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}
	public Double getDiscountAmount() {
		return discountAmount;
	}
	public void setDiscountAmount(Double discountAmount) {
		this.discountAmount = discountAmount;
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
	public Integer getIsCommented() {
		return isCommented;
	}
	public void setIsCommented(Integer isCommented) {
		this.isCommented = isCommented;
	}
	public String getParkNo() {
		return parkNo;
	}
	public void setParkNo(String parkNo) {
		this.parkNo = parkNo;
	}
	public Double getMinutesAmount() {
		return minutesAmount;
	}
	public void setMinutesAmount(Double minutesAmount) {
		this.minutesAmount = minutesAmount;
	}
	public Double getMileageAmount() {
		return mileageAmount;
	}
	public void setMileageAmount(Double mileageAmount) {
		this.mileageAmount = mileageAmount;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@Override
	public String toString() {
		return "OrderVOTrip [orderNo=" + orderNo + ", memberNo=" + memberNo + ", carPlateNo=" + carPlateNo
				+ ", carModelName=" + carModelName + ", actualTakeLoacton=" + actualTakeLoacton + ", startBillingTime="
				+ startBillingTime + ", orderMileage=" + orderMileage + ", nowAmount=" + nowAmount + ", orderDuration="
				+ orderDuration + ", actualTerminalLocation=" + actualTerminalLocation + ", finishTime=" + finishTime
				+ ", integral=" + integral + ", payableAmount=" + payableAmount + ", needPayaAmount=" + needPayaAmount
				+ ", alreadPayAmount=" + alreadPayAmount + ", payStatus=" + payStatus + ", carBrandName=" + carBrandName
				+ ", rangeMileage=" + rangeMileage + ", longitude=" + longitude + ", latitude=" + latitude
				+ ", discountAmount=" + discountAmount + ", packMinutesDiscountAmount=" + packMinutesDiscountAmount
				+ ", pricingPackOrderInvoiceVos=" + pricingPackOrderInvoiceVos + ", isIllegal=" + isIllegal
				+ ", isAccident=" + isAccident + ", parkNo=" + parkNo + ", minutesAmount=" + minutesAmount
				+ ", mileageAmount=" + mileageAmount + "]";
	}
	public Integer getReturnCarStatus() {
		return returnCarStatus;
	}
	public void setReturnCarStatus(Integer returnCarStatus) {
		this.returnCarStatus = returnCarStatus;
	}
	public Double getBaseFee() {
		return baseFee;
	}
	public void setBaseFee(Double baseFee) {
		this.baseFee = baseFee;
	}
	public Integer getPayType() {
		return payType;
	}
	public void setPayType(Integer payType) {
		this.payType = payType;
	}
	
	public Double getBillingCapPerDaySum() {
		return billingCapPerDaySum;
	}
	public void setBillingCapPerDaySum(Double billingCapPerDaySum) {
		this.billingCapPerDaySum = billingCapPerDaySum;
	}
	public Integer getDaySum() {
		return daySum;
	}
	public void setDaySum(Integer daySum) {
		this.daySum = daySum;
	}
	
	public Double getStartParkAmount() {
		return startParkAmount;
	}
	public void setStartParkAmount(Double startParkAmount) {
		this.startParkAmount = startParkAmount;
	}
	
	public Double getFinishParkAmount() {
		return finishParkAmount;
	}
	public void setFinishParkAmount(Double finishParkAmount) {
		this.finishParkAmount = finishParkAmount;
	}
	public Double getServiceFeeAmount() {
		return serviceFeeAmount;
	}
	public void setServiceFeeAmount(Double serviceFeeAmount) {
		this.serviceFeeAmount = serviceFeeAmount;
	}
	public boolean isDiscount() {
		return discount;
	}
	public void setDiscount(boolean discount) {
		this.discount = discount;
	}
	public Double getRegardlessFranchise() {
		return regardlessFranchise;
	}
	public void setRegardlessFranchise(Double regardlessFranchise) {
		this.regardlessFranchise = regardlessFranchise;
	}
	public Double getPower() {
		return power;
	}
	public void setPower(Double power) {
		this.power = power;
	}
	public Integer getWarningOrder() {
		return warningOrder;
	}
	public void setWarningOrder(Integer warningOrder) {
		this.warningOrder = warningOrder;
	}
	public String getShareTitle() {
		return shareTitle;
	}
	public void setShareTitle(String shareTitle) {
		this.shareTitle = shareTitle;
	}
	public String getShareContent() {
		return shareContent;
	}
	public void setShareContent(String shareContent) {
		this.shareContent = shareContent;
	}
	public String getIconUrl() {
		return iconUrl;
	}
	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}
	public String getShareUrl() {
		return shareUrl;
	}
	public void setShareUrl(String shareUrl) {
		this.shareUrl = shareUrl;
	}
	public String getIsFinish() {
		return isFinish;
	}
	public void setIsFinish(String isFinish) {
		this.isFinish = isFinish;
	}
	public Double getMemberLevelDiscountAmount() {
		return memberLevelDiscountAmount;
	}
	public void setMemberLevelDiscountAmount(Double memberLevelDiscountAmount) {
		this.memberLevelDiscountAmount = memberLevelDiscountAmount;
	}
	public Integer getIsOrderFreeMinutes() {
		return isOrderFreeMinutes;
	}
	public void setIsOrderFreeMinutes(Integer isOrderFreeMinutes) {
		this.isOrderFreeMinutes = isOrderFreeMinutes;
	}
	public Integer getFreeMinutes() {
		return freeMinutes;
	}
	public void setFreeMinutes(Integer freeMinutes) {
		this.freeMinutes = freeMinutes;
	}
	public Double getGlodBeansDedutionAmount() {
		return glodBeansDedutionAmount;
	}
	public void setGlodBeansDedutionAmount(Double glodBeansDedutionAmount) {
		this.glodBeansDedutionAmount = glodBeansDedutionAmount;
	}
	public Integer getIsSumFreeMileage() {
		return isSumFreeMileage;
	}
	public void setIsSumFreeMileage(Integer isSumFreeMileage) {
		this.isSumFreeMileage = isSumFreeMileage;
	}
	public String getIsCarRedPakcet() {
		return isCarRedPakcet;
	}
	public void setIsCarRedPakcet(String isCarRedPakcet) {
		this.isCarRedPakcet = isCarRedPakcet;
	}
	public CarRedPacketVo getCarRedPacketVo() {
		return carRedPacketVo;
	}
	public void setCarRedPacketVo(CarRedPacketVo carRedPacketVo) {
		this.carRedPacketVo = carRedPacketVo;
	}
	public Integer getChargeState() {
		return chargeState;
	}
	public void setChargeState(Integer chargeState) {
		this.chargeState = chargeState;
	}
	public List<PearTimeCostVo> getPearTimeCostVo() {
		return pearTimeCostVo;
	}
	public void setPearTimeCostVo(List<PearTimeCostVo> pearTimeCostVo) {
		this.pearTimeCostVo = pearTimeCostVo;
	}
//	public Integer getOrdinaryTimeTotalMinutes() {
//		return ordinaryTimeTotalMinutes;
//	}
//	public void setOrdinaryTimeTotalMinutes(Integer ordinaryTimeTotalMinutes) {
//		this.ordinaryTimeTotalMinutes = ordinaryTimeTotalMinutes;
//	}
//	public Double getOrdinaryTimeTotalMinutesAmount() {
//		return ordinaryTimeTotalMinutesAmount;
//	}
//	public void setOrdinaryTimeTotalMinutesAmount(Double ordinaryTimeTotalMinutesAmount) {
//		this.ordinaryTimeTotalMinutesAmount = ordinaryTimeTotalMinutesAmount;
//	}
//
//	public Double getOrdinaryTimeTotalMileageAmount() {
//		return ordinaryTimeTotalMileageAmount;
//	}
//	public void setOrdinaryTimeTotalMileageAmount(Double ordinaryTimeTotalMileageAmount) {
//		this.ordinaryTimeTotalMileageAmount = ordinaryTimeTotalMileageAmount;
//	}
//	public Double getOrdinaryTimeTotalMileage() {
//		return ordinaryTimeTotalMileage;
//	}
//	public void setOrdinaryTimeTotalMileage(Double ordinaryTimeTotalMileage) {
//		this.ordinaryTimeTotalMileage = ordinaryTimeTotalMileage;
//	}
}
	

	

