package cn.com.shopec.core.finace.model;

import java.util.Date;

import cn.com.shopec.core.common.Entity;

/** 
 * 发票申请表 数据实体类
 */
public class Invoice extends Entity<String> {
	
	private static final long serialVersionUID = 1l;
	
	/*Auto generated properties start*/
	
	//发票记录id
	private String invoiceId;
	//开票类型（1、增值税普通发票纸质版，2、增值税专用发票3.增值税普通发票电子版）
	private Integer invoiceType;
	//开票金额
	private Double invoiceAmount;
	//开票抬头
	private String invoiceTitle;
	//纳税人识别号
	private String taxpayerNo;

	//收件人姓名
	private String name;
	
	//开户行
	private String accountBank;
	//账号
	private String accountNo;
	//地址
	private String address;
	//电话
	private String phone;
	//传真
	private String fax;
	//邮编
	private String postcode;
	//(1、订单2、套餐)
	private Integer bizObjType;
	//订单或者套餐订单id
	private String bizObjId;
	//订单金额
	private Double orderAmount;
	//订单时间
	private Date orderTime;
	//订单时间 时间范围起（查询用）
	private Date orderTimeStart;
	//订单时间 时间范围止（查询用）
	private Date orderTimeEnd;	
	//开票时间
	private Date invoiceTime;
	//开票时间 时间范围起（查询用）
	private Date invoiceTimeStart;
	//开票时间 时间范围止（查询用）
	private Date invoiceTimeEnd;	
	//开票状态（0、未开票，1、已开票）
	private Integer invoiceStatus;
	//发票号
	private String invoiceNo;
	//纳税人认定通知书
	private String taxpayerNoticePic;
	//开票操作人id
	private String invoiceOperatorId;
	//开票操作人名称
	private String invoiceOperatorName;
	//操作人类型
	private Integer operatorType;
	//操作人人id
	private String operatorId;
	//创建日期
	private Date createTime;
	//创建日期 时间范围起（查询用）
	private Date createTimeStart;
	//创建日期 时间范围止（查询用）
	private Date createTimeEnd;	
	//更新日期
	private Date updateTime;
	//更新日期 时间范围起（查询用）
	private Date updateTimeStart;
	//更新日期 时间范围止（查询用）
	private Date updateTimeEnd;	
	//当前的月份
	private String month;
	//会员编号
	private String memberNo;
	/*Auto generated properties end*/
	//创建周期 时间范围起（查询用）
	private Date invoiceTimeLeadStart;
	//创建周期 时间范围止（查询用）
	private Date invoiceTimeLeadEnd;	
	//邮箱地址
	private String emailAddress;
	//增值税专用发票开票资料
	private String invoiceInfo;
	//增值税专用发票电话
	private String tel;
	//公司地址
	private String companyAddress;
	//抬头类别(1 个人 2 公司)
	private  Integer headerCategories;
	
	/*Customized properties start*/
	
	/*Customized properties end*/
	
	
	
	/*Auto generated methods start*/
	
	@Override
	public String getPK(){
		return invoiceId;
	}
	
	public Date getInvoiceTimeLeadStart() {
		return invoiceTimeLeadStart;
	}

	public void setInvoiceTimeLeadStart(Date invoiceTimeLeadStart) {
		this.invoiceTimeLeadStart = invoiceTimeLeadStart;
	}

	public Date getInvoiceTimeLeadEnd() {
		return invoiceTimeLeadEnd;
	}

	public void setInvoiceTimeLeadEnd(Date invoiceTimeLeadEnd) {
		this.invoiceTimeLeadEnd = invoiceTimeLeadEnd;
	}

	public String getInvoiceId(){
		return invoiceId;
	}
	
	public void setInvoiceId(String invoiceId){
		this.invoiceId = invoiceId;
	}
	
	public Integer getInvoiceType(){
		return invoiceType;
	}
	
	public void setInvoiceType(Integer invoiceType){
		this.invoiceType = invoiceType;
	}
	
	public Double getInvoiceAmount(){
		return invoiceAmount;
	}
	
	public void setInvoiceAmount(Double invoiceAmount){
		this.invoiceAmount = invoiceAmount;
	}
	
	public String getInvoiceTitle(){
		return invoiceTitle;
	}
	
	public void setInvoiceTitle(String invoiceTitle){
		this.invoiceTitle = invoiceTitle;
	}
	
	public String getTaxpayerNo(){
		return taxpayerNo;
	}
	
	public void setTaxpayerNo(String taxpayerNo){
		this.taxpayerNo = taxpayerNo;
	}
	
	public String getAccountBank(){
		return accountBank;
	}
	
	public void setAccountBank(String accountBank){
		this.accountBank = accountBank;
	}
	
	public String getAccountNo(){
		return accountNo;
	}
	
	public void setAccountNo(String accountNo){
		this.accountNo = accountNo;
	}
	
	public String getAddress(){
		return address;
	}
	
	public void setAddress(String address){
		this.address = address;
	}
	
	public String getPhone(){
		return phone;
	}
	
	public void setPhone(String phone){
		this.phone = phone;
	}
	
	public String getFax(){
		return fax;
	}
	
	public void setFax(String fax){
		this.fax = fax;
	}
	
	public String getPostcode(){
		return postcode;
	}
	
	public void setPostcode(String postcode){
		this.postcode = postcode;
	}
	
	public Integer getBizObjType(){
		return bizObjType;
	}
	
	public void setBizObjType(Integer bizObjType){
		this.bizObjType = bizObjType;
	}
	
	public String getBizObjId(){
		return bizObjId;
	}
	
	public void setBizObjId(String bizObjId){
		this.bizObjId = bizObjId;
	}
	
	public Double getOrderAmount(){
		return orderAmount;
	}
	
	public void setOrderAmount(Double orderAmount){
		this.orderAmount = orderAmount;
	}
	
	public Date getOrderTime(){
		return orderTime;
	}
	
	public void setOrderTime(Date orderTime){
		this.orderTime = orderTime;
	}
	
	public Date getOrderTimeStart(){
		return orderTimeStart;
	}
	
	public void setOrderTimeStart(Date orderTimeStart){
		this.orderTimeStart = orderTimeStart;
	}
	
	public Date getOrderTimeEnd(){
		return orderTimeEnd;
	}
	
	public void setOrderTimeEnd(Date orderTimeEnd){
		this.orderTimeEnd = orderTimeEnd;
	}	
	
	public Date getInvoiceTime(){
		return invoiceTime;
	}
	
	public void setInvoiceTime(Date invoiceTime){
		this.invoiceTime = invoiceTime;
	}
	
	public Date getInvoiceTimeStart(){
		return invoiceTimeStart;
	}
	
	public void setInvoiceTimeStart(Date invoiceTimeStart){
		this.invoiceTimeStart = invoiceTimeStart;
	}
	
	public Date getInvoiceTimeEnd(){
		return invoiceTimeEnd;
	}
	
	public void setInvoiceTimeEnd(Date invoiceTimeEnd){
		this.invoiceTimeEnd = invoiceTimeEnd;
	}	
	
	public Integer getInvoiceStatus(){
		return invoiceStatus;
	}
	
	public void setInvoiceStatus(Integer invoiceStatus){
		this.invoiceStatus = invoiceStatus;
	}
	
	public String getInvoiceNo(){
		return invoiceNo;
	}
	
	public void setInvoiceNo(String invoiceNo){
		this.invoiceNo = invoiceNo;
	}
	
	public String getTaxpayerNoticePic(){
		return taxpayerNoticePic;
	}
	
	public void setTaxpayerNoticePic(String taxpayerNoticePic){
		this.taxpayerNoticePic = taxpayerNoticePic;
	}
	
	public String getInvoiceOperatorId(){
		return invoiceOperatorId;
	}
	
	public void setInvoiceOperatorId(String invoiceOperatorId){
		this.invoiceOperatorId = invoiceOperatorId;
	}
	
	public String getInvoiceOperatorName(){
		return invoiceOperatorName;
	}
	
	public void setInvoiceOperatorName(String invoiceOperatorName){
		this.invoiceOperatorName = invoiceOperatorName;
	}
	
	public Integer getOperatorType(){
		return operatorType;
	}
	
	public void setOperatorType(Integer operatorType){
		this.operatorType = operatorType;
	}
	
	public String getOperatorId(){
		return operatorId;
	}
	
	public void setOperatorId(String operatorId){
		this.operatorId = operatorId;
	}
	
	public Date getCreateTime(){
		return createTime;
	}
	
	public void setCreateTime(Date createTime){
		this.createTime = createTime;
	}
	
	public Date getCreateTimeStart(){
		return createTimeStart;
	}
	
	public void setCreateTimeStart(Date createTimeStart){
		this.createTimeStart = createTimeStart;
	}
	
	public Date getCreateTimeEnd(){
		return createTimeEnd;
	}
	
	public void setCreateTimeEnd(Date createTimeEnd){
		this.createTimeEnd = createTimeEnd;
	}	
	
	public Date getUpdateTime(){
		return updateTime;
	}
	
	public void setUpdateTime(Date updateTime){
		this.updateTime = updateTime;
	}
	
	public Date getUpdateTimeStart(){
		return updateTimeStart;
	}
	
	public void setUpdateTimeStart(Date updateTimeStart){
		this.updateTimeStart = updateTimeStart;
	}
	
	public Date getUpdateTimeEnd(){
		return updateTimeEnd;
	}
	
	public void setUpdateTimeEnd(Date updateTimeEnd){
		this.updateTimeEnd = updateTimeEnd;
	}	
	
	
	/*Auto generated methods end*/
	
	
	
	/*Customized methods start*/
	
	/*Customized methods end*/
	
	
	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
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

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getInvoiceInfo() {
		return invoiceInfo;
	}

	public void setInvoiceInfo(String invoiceInfo) {
		this.invoiceInfo = invoiceInfo;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getCompanyAddress() {
		return companyAddress;
	}

	public void setCompanyAddress(String companyAddress) {
		this.companyAddress = companyAddress;
	}

	@Override
	public String toString() {
		return "Invoice ["
		 + "invoiceId = " + invoiceId + ", invoiceType = " + invoiceType + ", invoiceAmount = " + invoiceAmount + ", invoiceTitle = " + invoiceTitle
		 + ", taxpayerNo = " + taxpayerNo + ", accountBank = " + accountBank + ", accountNo = " + accountNo + ", address = " + address
		 + ", phone = " + phone + ", fax = " + fax + ", postcode = " + postcode + ", bizObjType = " + bizObjType
		 + ", bizObjId = " + bizObjId + ", orderAmount = " + orderAmount + ", orderTime = " + orderTime + ", orderTimeStart = " + orderTimeStart + ", orderTimeEnd = " + orderTimeEnd + ", invoiceTime = " + invoiceTime + ", invoiceTimeStart = " + invoiceTimeStart + ", invoiceTimeEnd = " + invoiceTimeEnd
		 + ", invoiceStatus = " + invoiceStatus + ", invoiceNo = " + invoiceNo + ", taxpayerNoticePic = " + taxpayerNoticePic + ", invoiceOperatorId = " + invoiceOperatorId
		 + ", invoiceOperatorName = " + invoiceOperatorName + ", operatorType = " + operatorType + ", operatorId = " + operatorId + ", createTime = " + createTime + ", createTimeStart = " + createTimeStart + ", createTimeEnd = " + createTimeEnd
		 + ", updateTime = " + updateTime + ", updateTimeStart = " + updateTimeStart + ", updateTimeEnd = " + updateTimeEnd + ", name = " + name
		+"]";
	}

	public Integer getHeaderCategories() {
		return headerCategories;
	}

	public void setHeaderCategories(Integer headerCategories) {
		this.headerCategories = headerCategories;
	}
}
