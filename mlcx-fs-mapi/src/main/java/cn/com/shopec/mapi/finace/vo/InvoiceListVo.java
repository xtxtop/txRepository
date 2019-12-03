package cn.com.shopec.mapi.finace.vo;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class InvoiceListVo implements Serializable{

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
	//(1、订单2、套餐)
	private Integer bizObjType;
	//开票时间
	private String invoiceTime;
	//开票状态（0、未开票，1、已开票）
	private Integer invoiceStatus;
	//发票号
	private String invoiceNo;
	//月份
	private Integer month;
	//年
	private Integer year;
	//开票类型 string
	private String invoiceName;
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
	
	public static long getSerialversionuid() {
		return serialVersionUID;
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
	public String getInvoiceName() {
		return invoiceName;
	}
	public void setInvoiceName(String invoiceName) {
		this.invoiceName = invoiceName;
	}
	

	
}
