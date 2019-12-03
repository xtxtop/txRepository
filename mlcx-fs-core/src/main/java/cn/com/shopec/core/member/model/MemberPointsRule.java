package cn.com.shopec.core.member.model;

import java.util.Date;

import cn.com.shopec.core.common.Entity;

/** 
 * 会员积分规则表 数据实体类
 */
public class MemberPointsRule extends Entity<String> {
	
	private static final long serialVersionUID = 1l;
	
	/*Auto generated properties start*/
	
	//积分规则id
	private String ruleId;
	//积分业务类型（1.订单支付，2.套餐支付）
	private Integer businessType;
	//积分类型（0，会员经验积分、1、可用于消费的积分，目前只需要支持会员经验积分即可）
	private Integer pointsType;
	//规则所对应的积分值
	private Integer pointsValue;
	//是否是默认的规则（0，非默认、1，默认，对于同一个business_type的规则，只能有一条规则的is_default值为1）
	private Integer isDefault;
	//是否可用（0、不可用，1、可用，默认1，且is_default为1的，其is_available值也必须为1）
	private Integer isAvailable;
	//是否已删除（0，未删除、1，已删除，默认0,且is_deleted为1的，其is_default值必须为0）
	private Integer isDeleted;
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
	//操作人id（根据操作人类型会对应不同的表记录）
	private String operatorId;
	
	/*Auto generated properties end*/
	
	
	
	/*Customized properties start*/
	
	/*Customized properties end*/
	
	
	
	/*Auto generated methods start*/
	
	@Override
	public String getPK(){
		return ruleId;
	}
	
	public String getRuleId(){
		return ruleId;
	}
	
	public void setRuleId(String ruleId){
		this.ruleId = ruleId;
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
	
	public Integer getPointsValue(){
		return pointsValue;
	}
	
	public void setPointsValue(Integer pointsValue){
		this.pointsValue = pointsValue;
	}
	
	public Integer getIsDefault(){
		return isDefault;
	}
	
	public void setIsDefault(Integer isDefault){
		this.isDefault = isDefault;
	}
	
	public Integer getIsAvailable(){
		return isAvailable;
	}
	
	public void setIsAvailable(Integer isAvailable){
		this.isAvailable = isAvailable;
	}
	
	public Integer getIsDeleted(){
		return isDeleted;
	}
	
	public void setIsDeleted(Integer isDeleted){
		this.isDeleted = isDeleted;
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
		return "MemberPointsRule ["
		 + "ruleId = " + ruleId + ", businessType = " + businessType + ", pointsType = " + pointsType + ", pointsValue = " + pointsValue
		 + ", isDefault = " + isDefault + ", isAvailable = " + isAvailable + ", isDeleted = " + isDeleted + ", createTime = " + createTime + ", createTimeStart = " + createTimeStart + ", createTimeEnd = " + createTimeEnd
		 + ", updateTime = " + updateTime + ", updateTimeStart = " + updateTimeStart + ", updateTimeEnd = " + updateTimeEnd + ", operatorType = " + operatorType + ", operatorId = " + operatorId
		+"]";
	}
}
