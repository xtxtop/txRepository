package cn.com.shopec.core.mlparking.vo;


public class OrderInfo {
	private String orderNo;//订单号
	private String memberNo;//会员号
	private String parkName;//站名
	private Integer orderStatus;//订单状态（0进行中，1待支付，2待评价，3已完成）
	private Integer payStatus;//支付状态（0待支付，1已支付）
	private String startTime;//开始时间
	private String entryTime;
	private String endTime;//结束时间
	private Integer duration;//总时长
	private Integer gratisTime;//免费时长
	private Integer overstepTime;//超时时间
	private Double realPayMoney;//应付总额
	private Double overstepMoney;//超时费用
	private Double balanceDeductionMoney;//使用余额
	private Integer star;//评分
	private Integer parkType;//停车场类型(0.闸机 1.地锁,2无设备)
	
	public String getEntryTime() {
		return entryTime;
	}
	public void setEntryTime(String entryTime) {
		this.entryTime = entryTime;
	}
	public Integer getOrderStatus() {
		return orderStatus;
	}
	public Integer getPayStatus() {
		return payStatus;
	}
	public Integer getDuration() {
		return duration;
	}
	public Integer getGratisTime() {
		return gratisTime;
	}
	public Integer getOverstepTime() {
		return overstepTime;
	}
	public Double getRealPayMoney() {
		return realPayMoney;
	}
	public Double getOverstepMoney() {
		return overstepMoney;
	}
	public Double getBalanceDeductionMoney() {
		return balanceDeductionMoney;
	}
	public Integer getStar() {
		return star;
	}
	public Integer getParkType() {
		return parkType;
	}
	public void setOrderStatus(Integer orderStatus) {
		this.orderStatus = orderStatus;
	}
	public void setPayStatus(Integer payStatus) {
		this.payStatus = payStatus;
	}
	public void setDuration(Integer duration) {
		this.duration = duration;
	}
	public void setGratisTime(Integer gratisTime) {
		this.gratisTime = gratisTime;
	}
	public void setOverstepTime(Integer overstepTime) {
		this.overstepTime = overstepTime;
	}
	public void setRealPayMoney(Double realPayMoney) {
		this.realPayMoney = realPayMoney;
	}
	public void setOverstepMoney(Double overstepMoney) {
		this.overstepMoney = overstepMoney;
	}
	public void setBalanceDeductionMoney(Double balanceDeductionMoney) {
		this.balanceDeductionMoney = balanceDeductionMoney;
	}
	public void setStar(Integer star) {
		this.star = star;
	}
	public void setParkType(Integer parkType) {
		this.parkType = parkType;
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
	public String getParkName() {
		return parkName;
	}
	public void setParkName(String parkName) {
		this.parkName = parkName;
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
	
}
