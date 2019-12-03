package cn.com.shopec.mapi.marketing.vo;

import java.util.Date;

import cn.com.shopec.core.common.Entity;

public class SendMessageVo extends Entity<String> {

private static final long serialVersionUID = 1l;
	
	/*Auto generated properties start*/
	
	//推送消息id
	private String messageId;
	//会员编号
	private String memberNo;
	//消息类型(1.已预约2.未支付)
	private Integer messageType;
	//消息内容
	private String messageContent;
	//是否已读（1、已读，0、未读，默认0）
	private Integer isRead;
	//创建时间
	private String createTime;
	//更新时间
	private String updateTime;
	
	
	/*Auto generated properties end*/
	
	
	
	/*Customized properties start*/
	
	/*Customized properties end*/
	
	
	
	/*Auto generated methods start*/
	
	@Override
	public String getPK(){
		return messageId;
	}
	
	public String getMessageId(){
		return messageId;
	}
	
	public void setMessageId(String messageId){
		this.messageId = messageId;
	}
	
	public String getMemberNo(){
		return memberNo;
	}
	
	public void setMemberNo(String memberNo){
		this.memberNo = memberNo;
	}
	
	public Integer getMessageType(){
		return messageType;
	}
	
	public void setMessageType(Integer messageType){
		this.messageType = messageType;
	}
	
	public String getMessageContent(){
		return messageContent;
	}
	
	public void setMessageContent(String messageContent){
		this.messageContent = messageContent;
	}
	
	public Integer getIsRead(){
		return isRead;
	}
	
	public void setIsRead(Integer isRead){
		this.isRead = isRead;
	}
	
	public String getCreateTime(){
		return createTime;
	}
	
	public void setCreateTime(String createTime){
		this.createTime = createTime;
	}
	
	
	public String getUpdateTime(){
		return updateTime;
	}
	
	public void setUpdateTime(String updateTime){
		this.updateTime = updateTime;
	}
	
	
	
	
	/*Auto generated methods end*/
	
	
	
	/*Customized methods start*/
	
	/*Customized methods end*/
	
	
	@Override
	public String toString() {
		return "SendMessage ["
		 + "messageId = " + messageId + ", memberNo = " + memberNo + ", messageType = " + messageType + ", messageContent = " + messageContent
		 + ", isRead = " + isRead + ", createTime = " + createTime
		+"]";
	}
	
}
