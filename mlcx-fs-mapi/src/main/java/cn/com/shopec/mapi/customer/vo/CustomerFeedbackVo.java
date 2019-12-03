package cn.com.shopec.mapi.customer.vo;

import java.util.Date;

import cn.com.shopec.core.common.Entity;

/** 
 * 客户反馈表 数据实体类
 */
public class CustomerFeedbackVo extends Entity<String> {
	
	private static final long serialVersionUID = 1l;
	
	/*Auto generated properties start*/
	
	//反馈编号
	private String feedbackNo;
	//反馈人员类型（1、会员2、调度员）
	private Integer customerType;
	//会员编号
	private String memberNo;
	//会员姓名
	private String memberName;
	//手机号
	private String mobilePhone;
	//内容
	private String content;
	//相关图片1
	private String photoUrl1;
	//相关图片2
	private String photoUrl2;
	//相关图片3
	private String photoUrl3;
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
	public String getMemberName() {
		return memberName;
	}
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}
	public String getMobilePhone() {
		return mobilePhone;
	}
	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getPhotoUrl1() {
		return photoUrl1;
	}
	public void setPhotoUrl1(String photoUrl1) {
		this.photoUrl1 = photoUrl1;
	}
	
	public String getPhotoUrl2() {
		return photoUrl2;
	}
	public void setPhotoUrl2(String photoUrl2) {
		this.photoUrl2 = photoUrl2;
	}
	public String getPhotoUrl3() {
		return photoUrl3;
	}
	public void setPhotoUrl3(String photoUrl3) {
		this.photoUrl3 = photoUrl3;
	}
	@Override
	public String toString() {
		return "CustomerFeedbackVo [feedbackNo=" + feedbackNo + ", customerType=" + customerType + ", memberNo="
				+ memberNo + ", memberName=" + memberName + ", mobilePhone=" + mobilePhone + ", content=" + content
				+ ", photoUrl1=" + photoUrl1 + ", photoUrl2=" + photoUrl2 + ", photoUrl3=" + photoUrl3 + "]";
	}
	
	/*Auto generated properties end*/
	
	
	
	/*Customized properties start*/
	
	/*Customized properties end*/
	
	
	
	/*Auto generated methods start*/
	
	
}
