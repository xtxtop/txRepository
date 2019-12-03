package cn.com.shopec.core.finace.model;

import java.util.Date;

import cn.com.shopec.core.common.Entity;

/** 
 *财务对账   数据实体类
 */
public class CheckAccountsView extends Entity<String> {
	
	private static final long serialVersionUID = 1l;
	
	/*Auto generated properties start*/
	//对账周期开始
	private Date checkDateRange1;
	//对账周期结束
	private Date checkDateRange2;
	//订单号
	private String orderNo;
	//客户编号
	private String memberNo;
	//客户姓名
	private String memberName;
	//手机号
	private String mobilePhone;
	//城市
	private String cityName;
	//车型
	private String carModelName;
	//车牌号
	private String carPlateNo;
	//开始时间
	private Date startTime;
	//结束时间
	private Date finishTime;
	//订单状态
	private Integer orderStatus;
	//订单金额
	private Double orderAmount;
	//应付金额
	private Double payableAmount;
	//支付状态
	private Integer payStatus;
	//发票号
	private String invoiceNo;
	//开票金额
	private Double invoiceAmount;
	public Date getCheckDateRange1() {
		return checkDateRange1;
	}
	public void setCheckDateRange1(Date checkDateRange1) {
		this.checkDateRange1 = checkDateRange1;
	}
	public Date getCheckDateRange2() {
		return checkDateRange2;
	}
	public void setCheckDateRange2(Date checkDateRange2) {
		this.checkDateRange2 = checkDateRange2;
	}
	public String getMemberNo() {
		return memberNo;
	}
	public void setMemberNo(String memberNo) {
		this.memberNo = memberNo;
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
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public String getCarModelName() {
		return carModelName;
	}
	public void setCarModelName(String carModelName) {
		this.carModelName = carModelName;
	}
	public String getCarPlateNo() {
		return carPlateNo;
	}
	public void setCarPlateNo(String carPlateNo) {
		this.carPlateNo = carPlateNo;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getFinishTime() {
		return finishTime;
	}
	public void setFinishTime(Date finishTime) {
		this.finishTime = finishTime;
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
	public String getInvoiceNo() {
		return invoiceNo;
	}
	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}
	public Double getInvoiceAmount() {
		return invoiceAmount;
	}
	public void setInvoiceAmount(Double invoiceAmount) {
		this.invoiceAmount = invoiceAmount;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public Integer getPayStatus() {
		return payStatus;
	}
	public void setPayStatus(Integer payStatus) {
		this.payStatus = payStatus;
	}
	
	
	
}
