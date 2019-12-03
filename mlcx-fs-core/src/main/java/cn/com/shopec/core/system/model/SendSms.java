package cn.com.shopec.core.system.model;

import java.util.Date;

import cn.com.shopec.core.common.Entity;

/** 
 * 商城发送短信记录表 数据实体类
 */
public class SendSms extends Entity<String> {
	
	private static final long serialVersionUID = 1l;
	
	/*Auto generated properties start*/
	
	//短信记录id
	private String smsId;
	//接收人类型（1、商家，2、会员）
	private Integer receiverType;
	//接收人id
	private Long receiverId;
	//手机号
	private String mobilePhone;
	//短信模板类型（0、系统消息，1、注册，2、修改密码，3、订单通知）
	private Integer templateType;
	//短信模板id
	private Long templateId;
	//短信内容
	private String smsContent;
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
	//发送时间
	private Date sendTime;
	//发送时间 时间范围起（查询用）
	private Date sendTimeStart;
	//发送时间 时间范围止（查询用）
	private Date sendTimeEnd;	
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
		return smsId;
	}
	
	public String getSmsId(){
		return smsId;
	}
	
	public void setSmsId(String smsId){
		this.smsId = smsId;
	}
	
	public Integer getReceiverType(){
		return receiverType;
	}
	
	public void setReceiverType(Integer receiverType){
		this.receiverType = receiverType;
	}
	
	public Long getReceiverId(){
		return receiverId;
	}
	
	public void setReceiverId(Long receiverId){
		this.receiverId = receiverId;
	}
	
	public String getMobilePhone(){
		return mobilePhone;
	}
	
	public void setMobilePhone(String mobilePhone){
		this.mobilePhone = mobilePhone;
	}
	
	public Integer getTemplateType(){
		return templateType;
	}
	
	public void setTemplateType(Integer templateType){
		this.templateType = templateType;
	}
	
	public Long getTemplateId(){
		return templateId;
	}
	
	public void setTemplateId(Long templateId){
		this.templateId = templateId;
	}
	
	public String getSmsContent(){
		return smsContent;
	}
	
	public void setSmsContent(String smsContent){
		this.smsContent = smsContent;
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
	
	public Date getSendTime(){
		return sendTime;
	}
	
	public void setSendTime(Date sendTime){
		this.sendTime = sendTime;
	}
	
	public Date getSendTimeStart(){
		return sendTimeStart;
	}
	
	public void setSendTimeStart(Date sendTimeStart){
		this.sendTimeStart = sendTimeStart;
	}
	
	public Date getSendTimeEnd(){
		return sendTimeEnd;
	}
	
	public void setSendTimeEnd(Date sendTimeEnd){
		this.sendTimeEnd = sendTimeEnd;
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
		return "SendSms ["
		 + "smsId = " + smsId + ", receiverType = " + receiverType + ", receiverId = " + receiverId + ", mobilePhone = " + mobilePhone
		 + ", templateType = " + templateType + ", templateId = " + templateId + ", smsContent = " + smsContent + ", createTime = " + createTime + ", createTimeStart = " + createTimeStart + ", createTimeEnd = " + createTimeEnd
		 + ", updateTime = " + updateTime + ", updateTimeStart = " + updateTimeStart + ", updateTimeEnd = " + updateTimeEnd + ", sendTime = " + sendTime + ", sendTimeStart = " + sendTimeStart + ", sendTimeEnd = " + sendTimeEnd + ", operatorType = " + operatorType + ", operatorId = " + operatorId
		+"]";
	}
}
