package cn.com.shopec.mapi.customer.vo;

import java.util.Date;

import cn.com.shopec.core.common.Entity;

/** 
 * 客户反馈表 数据实体类
 */
public class MemberFeedbackListVo extends Entity<String> {
	
	private static final long serialVersionUID = 1l;
	
	/*Auto generated properties start*/
	
	//反馈编号
	private String feedbackNo;
	//反馈人员类型（1、会员2、调度员）
	private Integer customerType;
	//会员编号
	private String memberNo;
	//内容
	private String content;
	//创建时间
	private String createTime;
	//回复时间
	private String  replyTime;
	//回复内容
	private String replyContent;
	
	public String getFeedbackNo() {
		return feedbackNo;
	}
	public void setFeedbackNo(String feedbackNo) {
		this.feedbackNo = feedbackNo;
	}
	public Integer getCustomerType() {
		return customerType;
	}
	public void setCustomerType(Integer customerType) {
		this.customerType = customerType;
	}
	public String getMemberNo() {
		return memberNo;
	}
	public void setMemberNo(String memberNo) {
		this.memberNo = memberNo;
	}
	
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getReplyTime() {
		return replyTime;
	}
	public void setReplyTime(String replyTime) {
		this.replyTime = replyTime;
	}
	public String getReplyContent() {
		return replyContent;
	}
	public void setReplyContent(String replyContent) {
		this.replyContent = replyContent;
	}

	
	
	/*Auto generated properties end*/
	
	
	
	/*Customized properties start*/
	
	/*Customized properties end*/
	
	
	
	/*Auto generated methods start*/
	
	
}
