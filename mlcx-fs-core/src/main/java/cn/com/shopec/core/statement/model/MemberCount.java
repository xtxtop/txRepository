package cn.com.shopec.core.statement.model;

import java.util.Date;

public class MemberCount {
	//会员姓名
	private String memberName;
	
    //手机号
	private String mobilePhone;
	
	//订单数  列表没有，但是sql有，加上吧
	private Integer orderCount;
	
	//时长（分钟）
	private double orderDuration;

	//订单金额（元）
	private double orderAmount;
	
	//实收金额
	private double paidAmount;
	
	//实收金额对应时长
	private double paidDuration;
	
	//套餐抵扣订单数
	private double packDiscountAmount;
	
	//套餐抵扣金额
	private double packMinuteAmount;
	
	//套餐抵扣时长
	private double packMinuteDuration;
	
	//冲账订单数
	private double strikeDiscountAmount;
	
	//冲账金额
	private double strikeBalanceAmount;
	
	//微信订单数
	private double wxOrderCount;
	
	//微信订单金额
	private double wxOrderAmount;
	
	//微信实收金额
	private double wxpayAmount;
	
	//微信手续费
	private double wxproFee;
	
	//支付宝订单数
	private double zfbOrderCount;
	
	//支付宝订单金额
	private double zfbOrderAmount;
	
	//支付宝实收金额
	private double zfbpayAmount;
	
	//支付宝手续费
	private double zfbproFee;
	
	//查询条件  paidTime
//	private Date paidTime;
	//查询条件开始时间
	private Date startTime;
	//查询条件结束时间
	private Date endTime;
	
	

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public double getOrderDuration() {
		return orderDuration;
	}

	public void setOrderDuration(double orderDuration) {
		this.orderDuration = orderDuration;
	}

	public double getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(double orderAmount) {
		this.orderAmount = orderAmount;
	}

	public double getPaidAmount() {
		return paidAmount;
	}

	public void setPaidAmount(double paidAmount) {
		this.paidAmount = paidAmount;
	}

	public double getPaidDuration() {
		return paidDuration;
	}

	public void setPaidDuration(double paidDuration) {
		this.paidDuration = paidDuration;
	}

	public Integer getOrderCount() {
		return orderCount;
	}

	public void setOrderCount(Integer orderCount) {
		this.orderCount = orderCount;
	}

	public double getPackDiscountAmount() {
		return packDiscountAmount;
	}

	public void setPackDiscountAmount(double packDiscountAmount) {
		this.packDiscountAmount = packDiscountAmount;
	}

	public double getPackMinuteAmount() {
		return packMinuteAmount;
	}

	public void setPackMinuteAmount(double packMinuteAmount) {
		this.packMinuteAmount = packMinuteAmount;
	}

	public double getPackMinuteDuration() {
		return packMinuteDuration;
	}

	public void setPackMinuteDuration(double packMinuteDuration) {
		this.packMinuteDuration = packMinuteDuration;
	}

	public double getStrikeDiscountAmount() {
		return strikeDiscountAmount;
	}

	public void setStrikeDiscountAmount(double strikeDiscountAmount) {
		this.strikeDiscountAmount = strikeDiscountAmount;
	}

	public double getStrikeBalanceAmount() {
		return strikeBalanceAmount;
	}

	public void setStrikeBalanceAmount(double strikeBalanceAmount) {
		this.strikeBalanceAmount = strikeBalanceAmount;
	}

	public double getWxOrderCount() {
		return wxOrderCount;
	}

	public void setWxOrderCount(double wxOrderCount) {
		this.wxOrderCount = wxOrderCount;
	}

	public double getWxOrderAmount() {
		return wxOrderAmount;
	}

	public void setWxOrderAmount(double wxOrderAmount) {
		this.wxOrderAmount = wxOrderAmount;
	}

	public double getWxpayAmount() {
		return wxpayAmount;
	}

	public void setWxpayAmount(double wxpayAmount) {
		this.wxpayAmount = wxpayAmount;
	}

	public double getWxproFee() {
		return wxproFee;
	}

	public void setWxproFee(double wxproFee) {
		this.wxproFee = wxproFee;
	}

	public double getZfbOrderCount() {
		return zfbOrderCount;
	}

	public void setZfbOrderCount(double zfbOrderCount) {
		this.zfbOrderCount = zfbOrderCount;
	}

	public double getZfbOrderAmount() {
		return zfbOrderAmount;
	}

	public void setZfbOrderAmount(double zfbOrderAmount) {
		this.zfbOrderAmount = zfbOrderAmount;
	}

	public double getZfbpayAmount() {
		return zfbpayAmount;
	}

	public void setZfbpayAmount(double zfbpayAmount) {
		this.zfbpayAmount = zfbpayAmount;
	}

	public double getZfbproFee() {
		return zfbproFee;
	}

	public void setZfbproFee(double zfbproFee) {
		this.zfbproFee = zfbproFee;
	}
	
}
