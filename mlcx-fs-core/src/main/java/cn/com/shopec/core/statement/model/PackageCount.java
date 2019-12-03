package cn.com.shopec.core.statement.model;

import java.util.Date;

public class PackageCount {

	//套餐编号
	private String packageNo;
	//套餐名称
	private String packageName;
	//套餐单价
	private double price;
	//套餐时长
	private double minutes;
	//类型
	private String packageType;
	//套餐购买量
	private double packOrderCount;
	//套餐充值（元）
	private double packAmount;
	//总实收金额（元）
	private double packRealAmount;
	//总充值金额（元）
	private double totalPackAmount;
	//微信收入
	private double wxAmount;
	//微信手续费
	private double wxAgentFee;
	//支付宝收入
	private double zfbAmount;
	//支付宝手续费
	private double zfbAgentFee;
	//抵扣订单数
	private Integer dicountOrderNum;
	//抵扣订单总额
	private double dicountOrderCount;
	
	//查询条件开始时间
	private Date startTime;
	//查询条件结束时间
	private Date endTime;
	
	public String getPackageNo() {
		return packageNo;
	}
	public void setPackageNo(String packageNo) {
		this.packageNo = packageNo;
	}
	public String getPackageName() {
		return packageName;
	}
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public double getMinutes() {
		return minutes;
	}
	public void setMinutes(double minutes) {
		this.minutes = minutes;
	}
	public String getPackageType() {
		return packageType;
	}
	public void setPackageType(String packageType) {
		this.packageType = packageType;
	}
	public double getPackOrderCount() {
		return packOrderCount;
	}
	public void setPackOrderCount(double packOrderCount) {
		this.packOrderCount = packOrderCount;
	}
	public double getPackAmount() {
		return packAmount;
	}
	public void setPackAmount(double packAmount) {
		this.packAmount = packAmount;
	}
	public double getPackRealAmount() {
		return packRealAmount;
	}
	public void setPackRealAmount(double packRealAmount) {
		this.packRealAmount = packRealAmount;
	}
	public double getWxAmount() {
		return wxAmount;
	}
	public void setWxAmount(double wxAmount) {
		this.wxAmount = wxAmount;
	}
	public double getWxAgentFee() {
		return wxAgentFee;
	}
	public void setWxAgentFee(double wxAgentFee) {
		this.wxAgentFee = wxAgentFee;
	}
	public double getZfbAmount() {
		return zfbAmount;
	}
	public void setZfbAmount(double zfbAmount) {
		this.zfbAmount = zfbAmount;
	}
	public double getZfbAgentFee() {
		return zfbAgentFee;
	}
	public void setZfbAgentFee(double zfbAgentFee) {
		this.zfbAgentFee = zfbAgentFee;
	}
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
	public Integer getDicountOrderNum() {
		return dicountOrderNum;
	}
	public void setDicountOrderNum(Integer dicountOrderNum) {
		this.dicountOrderNum = dicountOrderNum;
	}
	public double getDicountOrderCount() {
		return dicountOrderCount;
	}
	public void setDicountOrderCount(double dicountOrderCount) {
		this.dicountOrderCount = dicountOrderCount;
	}
	public double getTotalPackAmount() {
		return totalPackAmount;
	}
	public void setTotalPackAmount(double totalPackAmount) {
		this.totalPackAmount = totalPackAmount;
	}
	
	
}
