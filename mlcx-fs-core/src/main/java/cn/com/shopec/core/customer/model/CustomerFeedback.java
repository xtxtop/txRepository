package cn.com.shopec.core.customer.model;

import java.util.Date;

import cn.com.shopec.core.common.Entity;

/** 
 * 客户反馈表 数据实体类
 */
public class CustomerFeedback extends Entity<String> {
	
	private static final long serialVersionUID = 1l;
	
	/*Auto generated properties start*/
	
	//反馈编号
	private String feedbackNo;
	//反馈类型（1、咨询，2.投诉，3.建议）
	private Integer feedbackType;
	//反馈人员类型（1、会员2、调度员）
	private Integer customerType;
	//会员编号
	private String memberNo;
	//会员姓名
	private String memberName;
	//手机号
	private String mobilePhone;
	//标题
	private String title;
	//内容
	private String content;
	//相关图片1
	private String photoUrl1;
	//相关图片2
	private String photoUrl2;
	//相关图片3
	private String photoUrl3;
	//回复状态（0、未回复，1、已回复，默认0）
	private Integer replyStatus;
	//客服回复时间
	private Date replyTime;
	//客服回复时间 时间范围起（查询用）
	private Date replyTimeStart;
	//客服回复时间 时间范围止（查询用）
	private Date replyTimeEnd;	
	//客服回复内容
	private String replyContent;
	//操作人类型
	private Integer operatorType;
	//操作人id
	private String operatorId;
	//操作人姓名
	private String operatorName;
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
	
	/*Auto generated properties end*/
	
	
	
	/*Customized properties start*/
	
	/*Customized properties end*/
	
	
	
	/*Auto generated methods start*/
	
	@Override
	public String getPK(){
		return feedbackNo;
	}
	
	public String getFeedbackNo(){
		return feedbackNo;
	}
	
	public void setFeedbackNo(String feedbackNo){
		this.feedbackNo = feedbackNo;
	}
	
	public Integer getFeedbackType(){
		return feedbackType;
	}
	
	public void setFeedbackType(Integer feedbackType){
		this.feedbackType = feedbackType;
	}
	
	public Integer getCustomerType(){
		return customerType;
	}
	
	public void setCustomerType(Integer customerType){
		this.customerType = customerType;
	}
	
	public String getMemberNo(){
		return memberNo;
	}
	
	public void setMemberNo(String memberNo){
		this.memberNo = memberNo;
	}
	
	public String getMemberName(){
		return memberName;
	}
	
	public void setMemberName(String memberName){
		this.memberName = memberName;
	}
	
	public String getMobilePhone(){
		return mobilePhone;
	}
	
	public void setMobilePhone(String mobilePhone){
		this.mobilePhone = mobilePhone;
	}
	
	public String getTitle(){
		return title;
	}
	
	public void setTitle(String title){
		this.title = title;
	}
	
	public String getContent(){
		return content;
	}
	
	public void setContent(String content){
		this.content = content;
	}
	
	public String getPhotoUrl1(){
		return photoUrl1;
	}
	
	public void setPhotoUrl1(String photoUrl1){
		this.photoUrl1 = photoUrl1;
	}
	
	public String getPhotoUrl2(){
		return photoUrl2;
	}
	
	public void setPhotoUrl2(String photoUrl2){
		this.photoUrl2 = photoUrl2;
	}
	
	public String getPhotoUrl3(){
		return photoUrl3;
	}
	
	public void setPhotoUrl3(String photoUrl3){
		this.photoUrl3 = photoUrl3;
	}
	
	public Integer getReplyStatus(){
		return replyStatus;
	}
	
	public void setReplyStatus(Integer replyStatus){
		this.replyStatus = replyStatus;
	}
	
	public Date getReplyTime(){
		return replyTime;
	}
	
	public void setReplyTime(Date replyTime){
		this.replyTime = replyTime;
	}
	
	public Date getReplyTimeStart(){
		return replyTimeStart;
	}
	
	public void setReplyTimeStart(Date replyTimeStart){
		this.replyTimeStart = replyTimeStart;
	}
	
	public Date getReplyTimeEnd(){
		return replyTimeEnd;
	}
	
	public void setReplyTimeEnd(Date replyTimeEnd){
		this.replyTimeEnd = replyTimeEnd;
	}	
	
	public String getReplyContent(){
		return replyContent;
	}
	
	public void setReplyContent(String replyContent){
		this.replyContent = replyContent;
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
	
	public String getOperatorName(){
		return operatorName;
	}
	
	public void setOperatorName(String operatorName){
		this.operatorName = operatorName;
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
		return "CustomerFeedback ["
		 + "feedbackNo = " + feedbackNo + ", feedbackType = " + feedbackType + ", customerType = " + customerType + ", memberNo = " + memberNo
		 + ", memberName = " + memberName + ", mobilePhone = " + mobilePhone + ", title = " + title + ", content = " + content
		 + ", photoUrl1 = " + photoUrl1 + ", photoUrl2 = " + photoUrl2 + ", photoUrl3 = " + photoUrl3 + ", replyStatus = " + replyStatus
		 + ", replyTime = " + replyTime + ", replyTimeStart = " + replyTimeStart + ", replyTimeEnd = " + replyTimeEnd + ", replyContent = " + replyContent + ", operatorType = " + operatorType + ", operatorId = " + operatorId
		 + ", operatorName = " + operatorName + ", createTime = " + createTime + ", createTimeStart = " + createTimeStart + ", createTimeEnd = " + createTimeEnd + ", updateTime = " + updateTime + ", updateTimeStart = " + updateTimeStart + ", updateTimeEnd = " + updateTimeEnd
		+"]";
	}
}
