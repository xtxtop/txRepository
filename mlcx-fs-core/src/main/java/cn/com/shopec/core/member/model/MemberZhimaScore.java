package cn.com.shopec.core.member.model;

import java.util.Date;

import cn.com.shopec.core.common.Entity;

/** 
 * 会员芝麻信用分表 数据实体类
 */
public class MemberZhimaScore extends Entity<String> {
	
	private static final long serialVersionUID = 1l;
	
	private String memberNo;//会员编号
	private String memberName;//会员名称
	private String mobilePhone;//会员手机号
	
	private String openId;//芝麻openId;
	private String transactionId;//芝麻业务流水凭证号,详请:https://b.zmxy.com.cn/technology/openDoc.htm?relInfo=TRANSACTION_ID_INTRO;
	private Date transactionTime;//芝麻信用分更新时间
	private String responseErrorCode;//调用芝麻接口返回错误码
	private String responseErrorMsg;//调用芝麻接口返回错误信息
	private Integer score;//芝麻信用分;
	private Date createTime;//创建时间
	private Date updateTime;//更新时间
	
	private Date transactionTimeStart;//芝麻信用分更新时间 时间范围起（查询用）
	private Date transactionTimeEnd;//芝麻信用分更新时间 时间范围止（查询用）
	private Date createTimeStart;//创建时间 时间范围起（查询用）
	private Date createTimeEnd;	//创建时间 时间范围止（查询用）
	private Date updateTimeStart;//更新时间 时间范围起（查询用）
	private Date updateTimeEnd;	//更新时间 时间范围止（查询用）
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
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public String getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	public Date getTransactionTime() {
		return transactionTime;
	}
	public void setTransactionTime(Date transactionTime) {
		this.transactionTime = transactionTime;
	}
	public Date getTransactionTimeStart() {
		return transactionTimeStart;
	}
	public void setTransactionTimeStart(Date transactionTimeStart) {
		this.transactionTimeStart = transactionTimeStart;
	}
	public Date getTransactionTimeEnd() {
		return transactionTimeEnd;
	}
	public void setTransactionTimeEnd(Date transactionTimeEnd) {
		this.transactionTimeEnd = transactionTimeEnd;
	}
	public Integer getScore() {
		return score;
	}
	public void setScore(Integer score) {
		this.score = score;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getCreateTimeStart() {
		return createTimeStart;
	}
	public void setCreateTimeStart(Date createTimeStart) {
		this.createTimeStart = createTimeStart;
	}
	public Date getCreateTimeEnd() {
		return createTimeEnd;
	}
	public void setCreateTimeEnd(Date createTimeEnd) {
		this.createTimeEnd = createTimeEnd;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public Date getUpdateTimeStart() {
		return updateTimeStart;
	}
	public void setUpdateTimeStart(Date updateTimeStart) {
		this.updateTimeStart = updateTimeStart;
	}
	public Date getUpdateTimeEnd() {
		return updateTimeEnd;
	}
	public void setUpdateTimeEnd(Date updateTimeEnd) {
		this.updateTimeEnd = updateTimeEnd;
	}
	public String getResponseErrorCode() {
		return responseErrorCode;
	}
	public void setResponseErrorCode(String responseErrorCode) {
		this.responseErrorCode = responseErrorCode;
	}
	public String getResponseErrorMsg() {
		return responseErrorMsg;
	}
	public void setResponseErrorMsg(String responseErrorMsg) {
		this.responseErrorMsg = responseErrorMsg;
	}
	@Override
	public String toString() {
		return "MemberZhimaScore [memberNo=" + memberNo + ", memberName=" + memberName + ", mobilePhone=" + mobilePhone
				+ ", openId=" + openId + ", transactionId=" + transactionId + ", transactionTime=" + transactionTime
				+ ", responseErrorCode=" + responseErrorCode + ", responseErrorMsg=" + responseErrorMsg + ", score="
				+ score + ", createTime=" + createTime + ", updateTime=" + updateTime + ", transactionTimeStart="
				+ transactionTimeStart + ", transactionTimeEnd=" + transactionTimeEnd + ", createTimeStart="
				+ createTimeStart + ", createTimeEnd=" + createTimeEnd + ", updateTimeStart=" + updateTimeStart
				+ ", updateTimeEnd=" + updateTimeEnd + "]";
	}
	
}
