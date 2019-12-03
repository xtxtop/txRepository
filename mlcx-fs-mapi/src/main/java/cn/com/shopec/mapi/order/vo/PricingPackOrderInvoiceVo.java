package cn.com.shopec.mapi.order.vo;

import java.io.Serializable;
import java.util.Date;

public class PricingPackOrderInvoiceVo implements Serializable{

	/** 
	*
	* @Fields serialVersionUID : TODO未开发票的套餐记录vo
	*/ 
	private static final long serialVersionUID = 1938115545867227145L;

	//套餐订单编号
	private String packOrderNo;
	//应付金额
	private Double payableAmount;
	//支付状态（0、未支付，1、已支付，2、部分支付-预留）
	private Integer payStatus;
	//套餐可用时间起，也就是支付时间
	private String vailableTime1;
	
	private String createTime;
	//月份
	private Integer month;
	//套餐名称
	private String packageName;
	private Integer year;
	
	public String getPackOrderNo() {
		return packOrderNo;
	}
	public void setPackOrderNo(String packOrderNo) {
		this.packOrderNo = packOrderNo;
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

	public String getVailableTime1() {
		return vailableTime1;
	}
	public void setVailableTime1(String vailableTime1) {
		this.vailableTime1 = vailableTime1;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	@Override
	public String toString() {
		return "PricingPackOrderInvoiceVo [packOrderNo=" + packOrderNo + ", payableAmount=" + payableAmount
				+ ", payStatus=" + payStatus + ", vailableTime1=" + vailableTime1 + ", createTime=" + createTime + "]";
	}
	public String getPackageName() {
		return packageName;
	}
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}
	public Integer getMonth() {
		return month;
	}
	public void setMonth(Integer month) {
		this.month = month;
	}
	public Integer getYear() {
		return year;
	}
	public void setYear(Integer year) {
		this.year = year;
	}
	
	
	
}
