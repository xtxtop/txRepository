package cn.com.shopec.mapi.finace.vo;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class InvoiceTypeVo implements Serializable{

	/** 
	*
	* @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么) 
	*/ 
	private static final long serialVersionUID = 1659101026394568967L;
	
	
	//开票类型（1、增值税普通发票纸质版，2、增值税专用发票3.增值税普通发票电子版）
	private String invoiceType1;
	private String invoiceType2;
	private String invoiceType3;
	public String getInvoiceType1() {
		return invoiceType1;
	}
	public void setInvoiceType1(String invoiceType1) {
		this.invoiceType1 = invoiceType1;
	}
	public String getInvoiceType2() {
		return invoiceType2;
	}
	public void setInvoiceType2(String invoiceType2) {
		this.invoiceType2 = invoiceType2;
	}
	public String getInvoiceType3() {
		return invoiceType3;
	}
	public void setInvoiceType3(String invoiceType3) {
		this.invoiceType3 = invoiceType3;
	}


	
	


	
	

	
}
