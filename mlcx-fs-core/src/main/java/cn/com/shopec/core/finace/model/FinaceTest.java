package cn.com.shopec.core.finace.model;

import java.util.Date;

import cn.com.shopec.core.common.Entity;

/** 
 * FinaceTest 数据实体类
 */
public class FinaceTest extends Entity<String> {
	
	private static final long serialVersionUID = 1l;
	
	/*Auto generated properties start*/
	
	//主键
	private String id;
	//支付宝交易号
	private String payflowNo;
	//商户订单号
	private String bizObjNo;
	//业务类型
	private String bizObjType;
	//商品名称
	private String productName;
	//创建时间
	private Date createTime;
	//创建时间 时间范围起（查询用）
	private Date createTimeStart;
	//创建时间 时间范围止（查询用）
	private Date createTimeEnd;	
	//完成时间
	private Date finishTime;
	//完成时间 时间范围起（查询用）
	private Date finishTimeStart;
	//完成时间 时间范围止（查询用）
	private Date finishTimeEnd;	
	//对方账户
	private String payMemberDetail;
	//订单金额（元）
	private Double orderAmount;
	//商家实收（元）
	private Double saleRealAmount;
	//退款批次号/请求号
	private String refundPayNo;
	//服务费（元）
	private Double serviceFee;
	//备注
	private String memo;
	// 支付方式
	private String payMethod;
	
	/*Auto generated properties end*/
	
	
	
	/*Customized properties start*/
	
	/*Customized properties end*/
	
	
	
	/*Auto generated methods start*/
	
	@Override
	public String getPK(){
		return id;
	}
	
	public String getId(){
		return id;
	}
	
	public void setId(String id){
		this.id = id;
	}
	
	public String getPayflowNo(){
		return payflowNo;
	}
	
	public void setPayflowNo(String payflowNo){
		this.payflowNo = payflowNo;
	}
	
	public String getBizObjNo(){
		return bizObjNo;
	}
	
	public void setBizObjNo(String bizObjNo){
		this.bizObjNo = bizObjNo;
	}
	
	public String getBizObjType(){
		return bizObjType;
	}
	
	public void setBizObjType(String bizObjType){
		this.bizObjType = bizObjType;
	}
	
	public String getProductName(){
		return productName;
	}
	
	public void setProductName(String productName){
		this.productName = productName;
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
	
	public Date getFinishTime(){
		return finishTime;
	}
	
	public void setFinishTime(Date finishTime){
		this.finishTime = finishTime;
	}
	
	public Date getFinishTimeStart(){
		return finishTimeStart;
	}
	
	public void setFinishTimeStart(Date finishTimeStart){
		this.finishTimeStart = finishTimeStart;
	}
	
	public Date getFinishTimeEnd(){
		return finishTimeEnd;
	}
	
	public void setFinishTimeEnd(Date finishTimeEnd){
		this.finishTimeEnd = finishTimeEnd;
	}	
	
	public String getPayMemberDetail(){
		return payMemberDetail;
	}
	
	public void setPayMemberDetail(String payMemberDetail){
		this.payMemberDetail = payMemberDetail;
	}
	
	public Double getOrderAmount(){
		return orderAmount;
	}
	
	public void setOrderAmount(Double orderAmount){
		this.orderAmount = orderAmount;
	}
	
	public Double getSaleRealAmount(){
		return saleRealAmount;
	}
	
	public void setSaleRealAmount(Double saleRealAmount){
		this.saleRealAmount = saleRealAmount;
	}
	
	public String getRefundPayNo(){
		return refundPayNo;
	}
	
	public void setRefundPayNo(String refundPayNo){
		this.refundPayNo = refundPayNo;
	}
	
	public Double getServiceFee(){
		return serviceFee;
	}
	
	public void setServiceFee(Double serviceFee){
		this.serviceFee = serviceFee;
	}
	
	public String getMemo(){
		return memo;
	}
	
	public void setMemo(String memo){
		this.memo = memo;
	}
	
	public String getPayMethod() {
		return payMethod;
	}

	public void setPayMethod(String payMethod) {
		this.payMethod = payMethod;
	}

	
	/*Auto generated methods end*/
	
	
	
	/*Customized methods start*/
	
	/*Customized methods end*/
	
	@Override
	public String toString() {
		return "FinaceTest [id=" + id + ", payflowNo=" + payflowNo + ", bizObjNo=" + bizObjNo + ", bizObjType="
				+ bizObjType + ", productName=" + productName + ", createTime=" + createTime + ", createTimeStart="
				+ createTimeStart + ", createTimeEnd=" + createTimeEnd + ", finishTime=" + finishTime
				+ ", finishTimeStart=" + finishTimeStart + ", finishTimeEnd=" + finishTimeEnd + ", payMemberDetail="
				+ payMemberDetail + ", orderAmount=" + orderAmount + ", saleRealAmount=" + saleRealAmount
				+ ", refundPayNo=" + refundPayNo + ", serviceFee=" + serviceFee + ", memo=" + memo + ", payMethod="
				+ payMethod + "]";
	}	
	
	
}
