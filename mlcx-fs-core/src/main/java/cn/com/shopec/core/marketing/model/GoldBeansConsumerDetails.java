package cn.com.shopec.core.marketing.model;

import java.util.Date;

import cn.com.shopec.core.common.Entity;

/** 
 * GoldBeansConsumerDetails 数据实体类
 */
public class GoldBeansConsumerDetails extends Entity<String> {
	
	private static final long serialVersionUID = 1l;
	
	/*Auto generated properties start*/
	
	//消费编号
	private String consumerDetailsId;
	//会员编号
	private String menberNo;
	//会员名称
	private String memberName;
	//会员手机号
	private String memberPhone;
	//订单编号
	private String orderNo;
	//订单金额
	private Double orderAmount;
	//订单时间
	private Date orderTime;
	//订单时间 时间范围起（查询用）
	private Date orderTimeStart;
	//订单时间 时间范围止（查询用）
	private Date orderTimeEnd;	
	//消费金豆数
	private Integer consumerBeansNum;
	//消费金豆金额
	private Double consumerBeansAmount;
	//消费时间
	private Date consumerTime;
	//消费时间 时间范围起（查询用）
	private Date consumerTimeStart;
	//消费时间 时间范围止（查询用）
	private Date consumerTimeEnd;	
	//创建时间
	private Date createTime;
	//创建时间 时间范围起（查询用）
	private Date createTimeStart;
	//创建时间 时间范围止（查询用）
	private Date createTimeEnd;	
	//修改时间
	private Date updateTime;
	//修改时间 时间范围起（查询用）
	private Date updateTimeStart;
	//修改时间 时间范围止（查询用）
	private Date updateTimeEnd;	
	
	/*Auto generated properties end*/
	
	
	
	/*Customized properties start*/
	
	/*Customized properties end*/
	
	
	
	/*Auto generated methods start*/
	
	@Override
	public String getPK(){
		return consumerDetailsId;
	}
	
	public String getConsumerDetailsId(){
		return consumerDetailsId;
	}
	
	public void setConsumerDetailsId(String consumerDetailsId){
		this.consumerDetailsId = consumerDetailsId;
	}
	
	public String getMenberNo(){
		return menberNo;
	}
	
	public void setMenberNo(String menberNo){
		this.menberNo = menberNo;
	}
	
	public String getMemberName(){
		return memberName;
	}
	
	public void setMemberName(String memberName){
		this.memberName = memberName;
	}
	
	public String getMemberPhone(){
		return memberPhone;
	}
	
	public void setMemberPhone(String memberPhone){
		this.memberPhone = memberPhone;
	}
	
	public String getOrderNo(){
		return orderNo;
	}
	
	public void setOrderNo(String orderNo){
		this.orderNo = orderNo;
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
	
	public Integer getConsumerBeansNum(){
		return consumerBeansNum;
	}
	
	public void setConsumerBeansNum(Integer consumerBeansNum){
		this.consumerBeansNum = consumerBeansNum;
	}
	
	public Double getConsumerBeansAmount() {
		return consumerBeansAmount;
	}

	public void setConsumerBeansAmount(Double consumerBeansAmount) {
		this.consumerBeansAmount = consumerBeansAmount;
	}

	public Date getConsumerTime(){
		return consumerTime;
	}
	
	public void setConsumerTime(Date consumerTime){
		this.consumerTime = consumerTime;
	}
	
	public Date getConsumerTimeStart(){
		return consumerTimeStart;
	}
	
	public void setConsumerTimeStart(Date consumerTimeStart){
		this.consumerTimeStart = consumerTimeStart;
	}
	
	public Date getConsumerTimeEnd(){
		return consumerTimeEnd;
	}
	
	public void setConsumerTimeEnd(Date consumerTimeEnd){
		this.consumerTimeEnd = consumerTimeEnd;
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
	
	
	@Override
	public String toString() {
		return "GoldBeansConsumerDetails ["
		 + "consumerDetailsId = " + consumerDetailsId + ", menberNo = " + menberNo + ", memberName = " + memberName + ", memberPhone = " + memberPhone
		 + ", orderNo = " + orderNo + ", orderAmount = " + orderAmount + ", orderTime = " + orderTime + ", orderTimeStart = " + orderTimeStart + ", orderTimeEnd = " + orderTimeEnd + ", consumerBeansNum = " + consumerBeansNum
		 + ", consumerTime = " + consumerTime + ", consumerTimeStart = " + consumerTimeStart + ", consumerTimeEnd = " + consumerTimeEnd + ", createTime = " + createTime + ", createTimeStart = " + createTimeStart + ", createTimeEnd = " + createTimeEnd + ", updateTime = " + updateTime + ", updateTimeStart = " + updateTimeStart + ", updateTimeEnd = " + updateTimeEnd
		+"]";
	}
}
