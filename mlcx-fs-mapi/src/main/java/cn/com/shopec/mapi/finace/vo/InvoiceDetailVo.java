package cn.com.shopec.mapi.finace.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class InvoiceDetailVo implements Serializable{

	/** 
	*
	* @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么) 
	*/ 
	private static final long serialVersionUID = 1659101026394568967L;
	
	//发票记录id
	private String invoiceId;
	//开票类型（1、增值税普通发票纸质版，2、增值税专用发票3.增值税普通发票电子版）
	private Integer invoiceType;
	//开票金额
	private Double invoiceAmount;
	////纳税人识别号
	private String taxpayerNo;
	//开票抬头
	private String invoiceTitle;
	//地址
	private String address;
	//(1、订单2、套餐)
	private Integer bizObjType;
	//开票时间
	private String invoiceTime;
	//开票状态（0、未开票，1、已开票）
	private Integer invoiceStatus;
	//发票号
	private String invoiceNo;
	//会员编号
	private String memberNo;
	//收件人姓名
	private String name;
	//电话
	private String phone;
	//订单数量
	private  Integer   inOrderSize;
	//抬头类别
	private  Integer headerCategories;
	public String getInvoiceId() {
		return invoiceId;
	}
	public void setInvoiceId(String invoiceId) {
		this.invoiceId = invoiceId;
	}
	public Integer getInvoiceType() {
		return invoiceType;
	}
	public void setInvoiceType(Integer invoiceType) {
		this.invoiceType = invoiceType;
	}
	public Double getInvoiceAmount() {
		return invoiceAmount;
	}
	public void setInvoiceAmount(Double invoiceAmount) {
		this.invoiceAmount = invoiceAmount;
	}
	public String getInvoiceTitle() {
		return invoiceTitle;
	}
	public void setInvoiceTitle(String invoiceTitle) {
		this.invoiceTitle = invoiceTitle;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Integer getBizObjType() {
		return bizObjType;
	}
	public void setBizObjType(Integer bizObjType) {
		this.bizObjType = bizObjType;
	}
	public String getInvoiceTime() {
		return invoiceTime;
	}
	public void setInvoiceTime(String invoiceTime) {
		this.invoiceTime = invoiceTime;
	}
	public Integer getInvoiceStatus() {
		return invoiceStatus;
	}
	public void setInvoiceStatus(Integer invoiceStatus) {
		this.invoiceStatus = invoiceStatus;
	}
	public String getInvoiceNo() {
		return invoiceNo;
	}
	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}
	public String getMemberNo() {
		return memberNo;
	}
	public void setMemberNo(String memberNo) {
		this.memberNo = memberNo;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@Override
	public String toString() {
		return "InvoiceDetailVo [invoiceId=" + invoiceId + ", invoiceType=" + invoiceType + ", invoiceAmount="
				+ invoiceAmount + ", invoiceTitle=" + invoiceTitle + ", address=" + address + ", bizObjType="
				+ bizObjType + ", invoiceTime=" + invoiceTime + ", invoiceStatus=" + invoiceStatus + ", invoiceNo="
				+ invoiceNo + ", memberNo=" + memberNo + ", name=" + name + "]";
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public Integer getInOrderSize() {
		return inOrderSize;
	}
	public void setInOrderSize(Integer inOrderSize) {
		this.inOrderSize = inOrderSize;
	}
	public String getTaxpayerNo() {
		return taxpayerNo;
	}
	public void setTaxpayerNo(String taxpayerNo) {
		this.taxpayerNo = taxpayerNo;
	}
	public Integer getHeaderCategories() {
		return headerCategories;
	}
	public void setHeaderCategories(Integer headerCategories) {
		this.headerCategories = headerCategories;
	}
	
	
	
	
}
