package cn.com.shopec.core.marketing.model;

import java.util.Date;

public class MessagePush {
	
	private String id;//主键
	private String title;//标题
	private String content;//内容
	private Integer pushStatus;//推送状态（0.未推送;1.已推送）
	private Integer pushPattern;//推送方式（1.多个用户;2.全部用户;3.群组）
	private String memberNo;//推送的目标会员编号
	private String memberName;//推送的目标会员名称
	
	private Date pushTime;//推送时间
	private Date createTime;//创建时间
	private Date updateTime;//更新时间
	private Integer operatorType;//操作人类型
	private String operatorId;//操作人id
	private Integer isDeleted;//删除状态
	
	private Date pushTimeStart;//推送时间 起始（查询用）
	private Date pushTimeEnd;//推送时间 结束（查询用）
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Integer getPushStatus() {
		return pushStatus;
	}
	public void setPushStatus(Integer pushStatus) {
		this.pushStatus = pushStatus;
	}
	public Integer getPushPattern() {
		return pushPattern;
	}
	public void setPushPattern(Integer pushPattern) {
		this.pushPattern = pushPattern;
	}
	public Date getPushTime() {
		return pushTime;
	}
	public void setPushTime(Date pushTime) {
		this.pushTime = pushTime;
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
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public Integer getOperatorType() {
		return operatorType;
	}
	public void setOperatorType(Integer operatorType) {
		this.operatorType = operatorType;
	}
	public String getOperatorId() {
		return operatorId;
	}
	public void setOperatorId(String operatorId) {
		this.operatorId = operatorId;
	}
	public Integer getIsDeleted() {
		return isDeleted;
	}
	public void setIsDeleted(Integer isDeleted) {
		this.isDeleted = isDeleted;
	}
	
	public Date getPushTimeStart() {
		return pushTimeStart;
	}
	public void setPushTimeStart(Date pushTimeStart) {
		this.pushTimeStart = pushTimeStart;
	}
	public Date getPushTimeEnd() {
		return pushTimeEnd;
	}
	public void setPushTimeEnd(Date pushTimeEnd) {
		this.pushTimeEnd = pushTimeEnd;
	}
	@Override
	public String toString() {
		return "MessagePush [id=" + id + ", title=" + title + ", content=" + content + ", pushStatus=" + pushStatus
				+ ", pushPattern=" + pushPattern + ", memberNo=" + memberNo + ", memberName=" + memberName
				+ ", pushTime=" + pushTime + ", createTime=" + createTime + ", updateTime=" + updateTime
				+ ", operatorType=" + operatorType + ", operatorId=" + operatorId + ", isDeleted=" + isDeleted
				+ ", pushTimeStart=" + pushTimeStart + ", pushTimeEnd=" + pushTimeEnd + "]";
	}
}

