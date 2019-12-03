package cn.com.shopec.core.finace.model;

import java.util.Date;

import cn.com.shopec.core.common.Entity;

/** 
 *财务对账   数据实体类
 */
public class CheckAccounts extends Entity<String> {
	
	private static final long serialVersionUID = 1l;
	
	/*Auto generated properties start*/
	//对账周期开始
	private Date checkDateRange1;
	//对账周期结束
	private Date checkDateRange2;
	//客户编号
	private String memberNo;
	//客户姓名
	private String memberName;
	//手机号
	private String mobilePhone;
	//订单数
	private Integer orderNum;
	//订单金额
	private Double orderAmount;
	//应付金额
	private Double payableAmount;
	//已付金额
	private Double alreadyPayAmount;
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
	public Integer getOrderNum() {
		return orderNum;
	}
	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
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
	public Double getAlreadyPayAmount() {
		return alreadyPayAmount;
	}
	public void setAlreadyPayAmount(Double alreadyPayAmount) {
		this.alreadyPayAmount = alreadyPayAmount;
	}
	public Double getInvoiceAmount() {
		return invoiceAmount;
	}
	public void setInvoiceAmount(Double invoiceAmount) {
		this.invoiceAmount = invoiceAmount;
	}
	
}
