package cn.com.shopec.core.member.model;

import java.util.Date;

import cn.com.shopec.core.common.Entity;

/** 
 * 会员积分记录表 数据实体类
 */
public class MemberPointsRecord extends Entity<String> {
	
	private static final long serialVersionUID = 1l;
	
	/*Auto generated properties start*/
	
	//积分记录id
	private String recordId;
	//积分业务类型（具体根据积分规则中的业务类型而定）
	private Integer businessType;
	//积分类型（0，会员经验积分、1、可用于消费的积分，目前只需要支持会员经验积分即可）
	private Integer pointsType;
	//操作类型（0，扣除/使用积分、1，增加/获得积分）
	private Integer opType;
	//积分值
	private Integer pointsValue;
	//业务数据（可能是订单号等）
	private String businessData;
	//会员编号
	private String memberNo;
	//备注
	private String recordMemo;
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
	//操作人类型
	private Integer operatorType;
	//操作人id
	private String operatorId;
	//会员姓名
	private String memberName;
	/*Auto generated properties end*/
	
	
	
	/*Customized properties start*/
	
	/*Customized properties end*/
	
	
	
	/*Auto generated methods start*/
	
	@Override
	public String getPK(){
		return recordId;
	}
	
	public String getRecordId(){
		return recordId;
	}
	
	public void setRecordId(String recordId){
		this.recordId = recordId;
	}
	
	public Integer getBusinessType() {
		return businessType;
	}

	public void setBusinessType(Integer businessType) {
		this.businessType = businessType;
	}

	public Integer getPointsType(){
		return pointsType;
	}
	
	public void setPointsType(Integer pointsType){
		this.pointsType = pointsType;
	}
	
	public Integer getOpType(){
		return opType;
	}
	
	public void setOpType(Integer opType){
		this.opType = opType;
	}
	
	public Integer getPointsValue(){
		return pointsValue;
	}
	
	public void setPointsValue(Integer pointsValue){
		this.pointsValue = pointsValue;
	}
	
	public String getBusinessData(){
		return businessData;
	}
	
	public void setBusinessData(String businessData){
		this.businessData = businessData;
	}
	
	public String getMemberNo(){
		return memberNo;
	}
	
	public void setMemberNo(String memberNo){
		this.memberNo = memberNo;
	}
	
	public String getRecordMemo(){
		return recordMemo;
	}
	
	public void setRecordMemo(String recordMemo){
		this.recordMemo = recordMemo;
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
	
	
	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	@Override
	public String toString() {
		return "MemberPointsRecord ["
		 + "recordId = " + recordId + ", businessType = " + businessType + ", pointsType = " + pointsType + ", opType = " + opType
		 + ", pointsValue = " + pointsValue + ", businessData = " + businessData + ", memberNo = " + memberNo + ", recordMemo = " + recordMemo
		 + ", createTime = " + createTime + ", createTimeStart = " + createTimeStart + ", createTimeEnd = " + createTimeEnd + ", updateTime = " + updateTime + ", updateTimeStart = " + updateTimeStart + ", updateTimeEnd = " + updateTimeEnd + ", operatorType = " + operatorType + ", operatorId = " + operatorId
		+"]";
	}
}
