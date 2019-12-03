package cn.com.shopec.core.ml.model;

import java.util.Date;

import cn.com.shopec.core.common.Entity;

/** 
 * 订单评价表 数据实体类
 */
public class COrderEvaluate extends Entity<String> {
	
	private static final long serialVersionUID = 1l;
	
	/*Auto generated properties start*/
	
	//订单评价ID
	private String evaluateNo;
	//会员编号
	private String memberNo;
	//订单编号
	private String orderNo;
	//订单类型（0:充电订单，1:地锁订单）
	private Integer orderType;
	//评价等级
	private Double evaluateGrade;
	//评价备注
	private String evaluateMemo;
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
	//操作人类型（0、系统自动操作，1、平台人员操作，2、商家人员操作，3、会员操作）
	private Integer operatorType;
	//操作人ID
	private String operatorId;
	
	/*Auto generated properties end*/
	
	
	
	/*Customized properties start*/
	
	/*Customized properties end*/
	
	
	
	/*Auto generated methods start*/
	
	@Override
	public String getPK(){
		return evaluateNo;
	}
	
	public String getEvaluateNo(){
		return evaluateNo;
	}
	
	public void setEvaluateNo(String evaluateNo){
		this.evaluateNo = evaluateNo;
	}
	
	public String getMemberNo(){
		return memberNo;
	}
	
	public void setMemberNo(String memberNo){
		this.memberNo = memberNo;
	}
	
	public String getOrderNo(){
		return orderNo;
	}
	
	public void setOrderNo(String orderNo){
		this.orderNo = orderNo;
	}
	
	public Integer getOrderType(){
		return orderType;
	}
	
	public void setOrderType(Integer orderType){
		this.orderType = orderType;
	}
	
	public Double getEvaluateGrade(){
		return evaluateGrade;
	}
	
	public void setEvaluateGrade(Double evaluateGrade){
		this.evaluateGrade = evaluateGrade;
	}
	
	public String getEvaluateMemo(){
		return evaluateMemo;
	}
	
	public void setEvaluateMemo(String evaluateMemo){
		this.evaluateMemo = evaluateMemo;
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
	
	
	/*Auto generated methods end*/
	
	
	
	/*Customized methods start*/
	
	/*Customized methods end*/
	
	
	@Override
	public String toString() {
		return "COrderEvaluate ["
		 + "evaluateNo = " + evaluateNo + ", memberNo = " + memberNo + ", orderNo = " + orderNo + ", orderType = " + orderType
		 + ", evaluateGrade = " + evaluateGrade + ", evaluateMemo = " + evaluateMemo + ", createTime = " + createTime + ", createTimeStart = " + createTimeStart + ", createTimeEnd = " + createTimeEnd + ", updateTime = " + updateTime + ", updateTimeStart = " + updateTimeStart + ", updateTimeEnd = " + updateTimeEnd
		 + ", operatorType = " + operatorType + ", operatorId = " + operatorId
		+"]";
	}
}
