package cn.com.shopec.core.order.model;

import java.util.Date;

import cn.com.shopec.core.common.Entity;

/** 
 * 订单冲账表 数据实体类
 */
public class OrderStrikeBalance extends Entity<String> {
	
	private static final long serialVersionUID = 1l;
	
	/*Auto generated properties start*/
	
	//订单冲账单号
	private String strikeBalanceNo;
	//订单编号(订单表主键)
	private String orderNo;
	//客户id
	private String memberId;
	//客户名称
	private String memberName;
	//订单金额
	private Double orderAmount;
	//应付金额
	private Double payAmount;
	//冲账金额
	private Double strikeBalanceAmount;
	//冲账原因
	private Integer strikeBalanceReason;
	//冲账备注
	private String strikeBalanceMemo;
	//提交人
	private String submitId;
	//提交人姓名
	private String submitName;
	//提交时间
	private Date submitTtime;
	//提交时间 时间范围起（查询用）
	private Date submitTtimeStart;
	//提交时间 时间范围止（查询用）
	private Date submitTtimeEnd;	
	//审核状态
	private Integer censorStatus;
	//审核人
	private String censorPersonId;
	//审核人姓名
	private String censorPersonName;
	//审核时间
	private Date censorTime;
	//审核时间 时间范围起（查询用）
	private Date censorTimeStart;
	//审核时间 时间范围止（查询用）
	private Date censorTimeEnd;	
	//审核备注
	private String censorMemo;
	//操作人id
	private String operatorId;
	//操作人类型
	private Integer operatorType;
	//创建时间
	private Date createTime;
	//创建时间 时间范围起（查询用）
	private Date createTimeStart;
	//创建时间 时间范围止（查询用）
	private Date createTimeEnd;	
	//更新时间
	private Date updateTime;
	//更新时间 时间范围起（查询用）
	private Date updateTimeStart;
	//更新时间 时间范围止（查询用）
	private Date updateTimeEnd;	
	//服务费
	private Double serviceFee;
	//用户权限（管理端权限限制用）
	private  Integer roleAdminTag;
	/*Auto generated properties end*/
	
	
	
	/*Customized properties start*/
	
	/*Customized properties end*/
	
	
	
	/*Auto generated methods start*/
	
	@Override
	public String getPK(){
		return strikeBalanceNo;
	}
	
	public String getStrikeBalanceNo(){
		return strikeBalanceNo;
	}
	
	public void setStrikeBalanceNo(String strikeBalanceNo){
		this.strikeBalanceNo = strikeBalanceNo;
	}
	
	public String getOrderNo(){
		return orderNo;
	}
	
	public void setOrderNo(String orderNo){
		this.orderNo = orderNo;
	}
	
	public String getMemberId(){
		return memberId;
	}
	
	public void setMemberId(String memberId){
		this.memberId = memberId;
	}
	
	public String getMemberName(){
		return memberName;
	}
	
	public void setMemberName(String memberName){
		this.memberName = memberName;
	}
	
	public Double getOrderAmount(){
		return orderAmount;
	}
	
	public void setOrderAmount(Double orderAmount){
		this.orderAmount = orderAmount;
	}
	
	public Double getPayAmount(){
		return payAmount;
	}
	
	public void setPayAmount(Double payAmount){
		this.payAmount = payAmount;
	}
	
	public Double getStrikeBalanceAmount(){
		return strikeBalanceAmount;
	}
	
	public void setStrikeBalanceAmount(Double strikeBalanceAmount){
		this.strikeBalanceAmount = strikeBalanceAmount;
	}
	
	public Integer getStrikeBalanceReason(){
		return strikeBalanceReason;
	}
	
	public void setStrikeBalanceReason(Integer strikeBalanceReason){
		this.strikeBalanceReason = strikeBalanceReason;
	}
	
	public String getStrikeBalanceMemo(){
		return strikeBalanceMemo;
	}
	
	public void setStrikeBalanceMemo(String strikeBalanceMemo){
		this.strikeBalanceMemo = strikeBalanceMemo;
	}
	
	public String getSubmitId(){
		return submitId;
	}
	
	public void setSubmitId(String submitId){
		this.submitId = submitId;
	}
	
	public String getSubmitName(){
		return submitName;
	}
	
	public void setSubmitName(String submitName){
		this.submitName = submitName;
	}
	
	public Date getSubmitTtime(){
		return submitTtime;
	}
	
	public void setSubmitTtime(Date submitTtime){
		this.submitTtime = submitTtime;
	}
	
	public Date getSubmitTtimeStart(){
		return submitTtimeStart;
	}
	
	public void setSubmitTtimeStart(Date submitTtimeStart){
		this.submitTtimeStart = submitTtimeStart;
	}
	
	public Date getSubmitTtimeEnd(){
		return submitTtimeEnd;
	}
	
	public void setSubmitTtimeEnd(Date submitTtimeEnd){
		this.submitTtimeEnd = submitTtimeEnd;
	}	
	
	public Integer getCensorStatus(){
		return censorStatus;
	}
	
	public void setCensorStatus(Integer censorStatus){
		this.censorStatus = censorStatus;
	}
	
	public String getCensorPersonId(){
		return censorPersonId;
	}
	
	public void setCensorPersonId(String censorPersonId){
		this.censorPersonId = censorPersonId;
	}
	
	public String getCensorPersonName(){
		return censorPersonName;
	}
	
	public void setCensorPersonName(String censorPersonName){
		this.censorPersonName = censorPersonName;
	}
	
	public Date getCensorTime(){
		return censorTime;
	}
	
	public void setCensorTime(Date censorTime){
		this.censorTime = censorTime;
	}
	
	public Date getCensorTimeStart(){
		return censorTimeStart;
	}
	
	public void setCensorTimeStart(Date censorTimeStart){
		this.censorTimeStart = censorTimeStart;
	}
	
	public Date getCensorTimeEnd(){
		return censorTimeEnd;
	}
	
	public void setCensorTimeEnd(Date censorTimeEnd){
		this.censorTimeEnd = censorTimeEnd;
	}	
	
	public String getCensorMemo(){
		return censorMemo;
	}
	
	public void setCensorMemo(String censorMemo){
		this.censorMemo = censorMemo;
	}
	
	public String getOperatorId(){
		return operatorId;
	}
	
	public void setOperatorId(String operatorId){
		this.operatorId = operatorId;
	}
	
	public Integer getOperatorType(){
		return operatorType;
	}
	
	public void setOperatorType(Integer operatorType){
		this.operatorType = operatorType;
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
	
	public Integer getRoleAdminTag() {
		return roleAdminTag;
	}

	public void setRoleAdminTag(Integer roleAdminTag) {
		this.roleAdminTag = roleAdminTag;
	}

	public Double getServiceFee() {
		return serviceFee;
	}

	public void setServiceFee(Double serviceFee) {
		this.serviceFee = serviceFee;
	}

	@Override
	public String toString() {
		return "OrderStrikeBalance [strikeBalanceNo=" + strikeBalanceNo + ", orderNo=" + orderNo + ", memberId="
				+ memberId + ", memberName=" + memberName + ", orderAmount=" + orderAmount + ", payAmount=" + payAmount
				+ ", strikeBalanceAmount=" + strikeBalanceAmount + ", strikeBalanceReason=" + strikeBalanceReason
				+ ", strikeBalanceMemo=" + strikeBalanceMemo + ", submitId=" + submitId + ", submitName=" + submitName
				+ ", submitTtime=" + submitTtime + ", submitTtimeStart=" + submitTtimeStart + ", submitTtimeEnd="
				+ submitTtimeEnd + ", censorStatus=" + censorStatus + ", censorPersonId=" + censorPersonId
				+ ", censorPersonName=" + censorPersonName + ", censorTime=" + censorTime + ", censorTimeStart="
				+ censorTimeStart + ", censorTimeEnd=" + censorTimeEnd + ", censorMemo=" + censorMemo + ", operatorId="
				+ operatorId + ", operatorType=" + operatorType + ", createTime=" + createTime + ", createTimeStart="
				+ createTimeStart + ", createTimeEnd=" + createTimeEnd + ", updateTime=" + updateTime
				+ ", updateTimeStart=" + updateTimeStart + ", updateTimeEnd=" + updateTimeEnd + ", serviceFee="
				+ serviceFee + ", roleAdminTag=" + roleAdminTag + "]";
	}
	
	
}
