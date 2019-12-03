package cn.com.shopec.core.system.model;

import java.util.Date;

import cn.com.shopec.core.common.Entity;

/** 
 * 商城短信模板表 数据实体类
 */
public class SmsTemplate extends Entity<String> {
	
	private static final long serialVersionUID = 1l;
	
	/*Auto generated properties start*/
	
	//短信模板id
	private String templateId;
	//短信模板类型（0、系统消息，1、注册，2、修改密码，3、订单通知）
	private Integer templateType;
	//是否本类型的默认模板（0，非默认模板，1，默认模板，默认值0），同一种模板类型，只能有一个默认模板
	private Integer isDefault;
	//短信模板内容
	private String templateContent;
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
		return templateId;
	}
	
	public String getTemplateId(){
		return templateId;
	}
	
	public void setTemplateId(String templateId){
		this.templateId = templateId;
	}
	
	public Integer getTemplateType(){
		return templateType;
	}
	
	public void setTemplateType(Integer templateType){
		this.templateType = templateType;
	}
	
	public Integer getIsDefault(){
		return isDefault;
	}
	
	public void setIsDefault(Integer isDefault){
		this.isDefault = isDefault;
	}
	
	public String getTemplateContent(){
		return templateContent;
	}
	
	public void setTemplateContent(String templateContent){
		this.templateContent = templateContent;
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
		return "SmsTemplate ["
		 + "templateId = " + templateId + ", templateType = " + templateType + ", isDefault = " + isDefault + ", templateContent = " + templateContent
		 + ", createTime = " + createTime + ", createTimeStart = " + createTimeStart + ", createTimeEnd = " + createTimeEnd + ", updateTime = " + updateTime + ", updateTimeStart = " + updateTimeStart + ", updateTimeEnd = " + updateTimeEnd + ", operatorType = " + operatorType + ", operatorId = " + operatorId
		+"]";
	}
}
