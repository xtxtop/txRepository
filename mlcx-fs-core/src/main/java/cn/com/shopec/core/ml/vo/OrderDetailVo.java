package cn.com.shopec.core.ml.vo;

import java.util.List;

public class OrderDetailVo {
	private String orderNo;//订单号
	private String memberNo;//会员号
	private String stationName;//站名
	private String orderStatus;//订单状态（0进行中，1待支付，2待评价，3已完成，4未结算，5已取消）
	private String payStatus;//支付状态（0待支付，1已支付）
	private String startTime;//开始时间
	private String endTime;//结束时间
	private String duration;//持续时长
	private String chargeDegree;//充电度数
	private String gratisTime;//免费时长
	private String overstepTime;//超时时间
	private String realPayMoney;//应付总额
	private String overstepMoney;//超时费用
	private String chargeMoney;//充电金额
	private String serviceMoney;//服务费
	private String balanceDeductionMoney;//使用余额
	private String star;//评分
	private String tp;//订单类型（1地锁，2充电）
	private List<BannerVo> banners;//顶部轮播广告

	public List<BannerVo> getBanners() {
		return banners;
	}

	public void setBanners(List<BannerVo> banners) {
		this.banners = banners;
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

	public String getStationName() {
		return stationName;
	}

	public void setStationName(String stationName) {
		this.stationName = stationName;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(String payStatus) {
		this.payStatus = payStatus;
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

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public String getChargeDegree() {
		return chargeDegree;
	}

	public void setChargeDegree(String chargeDegree) {
		this.chargeDegree = chargeDegree;
	}

	public String getGratisTime() {
		return gratisTime;
	}

	public void setGratisTime(String gratisTime) {
		this.gratisTime = gratisTime;
	}

	public String getOverstepTime() {
		return overstepTime;
	}

	public void setOverstepTime(String overstepTime) {
		this.overstepTime = overstepTime;
	}

	public String getRealPayMoney() {
		return realPayMoney;
	}

	public void setRealPayMoney(String realPayMoney) {
		this.realPayMoney = realPayMoney;
	}

	public String getOverstepMoney() {
		return overstepMoney;
	}

	public void setOverstepMoney(String overstepMoney) {
		this.overstepMoney = overstepMoney;
	}

	public String getChargeMoney() {
		return chargeMoney;
	}

	public void setChargeMoney(String chargeMoney) {
		this.chargeMoney = chargeMoney;
	}

	public String getServiceMoney() {
		return serviceMoney;
	}

	public void setServiceMoney(String serviceMoney) {
		this.serviceMoney = serviceMoney;
	}

	public String getBalanceDeductionMoney() {
		return balanceDeductionMoney;
	}

	public void setBalanceDeductionMoney(String balanceDeductionMoney) {
		this.balanceDeductionMoney = balanceDeductionMoney;
	}

	public String getStar() {
		return star;
	}

	public void setStar(String star) {
		this.star = star;
	}

	public String getTp() {
		return tp;
	}

	public void setTp(String tp) {
		this.tp = tp;
	}
}
