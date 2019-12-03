package cn.com.shopec.core.customer.model;

import java.util.Date;

import cn.com.shopec.core.common.Entity;

/**
 * OrderComments 数据实体类
 */
public class OrderComments extends Entity<String> {

	private static final long serialVersionUID = 1l;

	/* Auto generated properties start */

	// 评价表号
	private String commentsNo;
	// 订单号
	private String orderNo;
	// 会员编号
	private String memberNo;
	// 评分项1
	private String scoreItem1;
	// 评分项2
	private String scoreItem2;
	// 评分项3
	private String scoreItem3;
	// 评分项4
	private String scoreItem4;
	// 评分项5（预留）
	private String scoreItem5;
	// 评分项6（预留）
	private String scoreItem6;
	// 综合评分
	private String score;
	// 评语内容
	private String content;
	// 创建时间
	private Date createTime;
	// 创建时间 时间范围起（查询用）
	private Date createTimeStart;
	// 创建时间 时间范围止（查询用）
	private Date createTimeEnd;
	// 更新时间
	private Date updateTime;
	// 更新时间 时间范围起（查询用）
	private Date updateTimeStart;
	// 更新时间 时间范围止（查询用）
	private Date updateTimeEnd;
	// 操作人类型
	private Integer operatorType;
	// 操作人id
	private String operatorId;
	// 其他内容
	private String remarks;
	// 车牌号
	private String carPlateNo;
	// 车型名称
	private String carModelName;
	// 姓名
	private String memberName;
	// 手机号
	private String mobilePhone;

	/* Auto generated properties end */

	@Override
	public String getPK() {
		return commentsNo;
	}

	public String getCommentsNo() {
		return commentsNo;
	}

	public void setCommentsNo(String commentsNo) {
		this.commentsNo = commentsNo;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getMemberNo() {
		return memberNo;
	}

	public void setMemberNo(String memberNo) {
		this.memberNo = memberNo;
	}

	public String getScoreItem1() {
		return scoreItem1;
	}

	public void setScoreItem1(String scoreItem1) {
		this.scoreItem1 = scoreItem1;
	}

	public String getScoreItem2() {
		return scoreItem2;
	}

	public void setScoreItem2(String scoreItem2) {
		this.scoreItem2 = scoreItem2;
	}

	public String getScoreItem3() {
		return scoreItem3;
	}

	public void setScoreItem3(String scoreItem3) {
		this.scoreItem3 = scoreItem3;
	}

	public String getScoreItem4() {
		return scoreItem4;
	}

	public void setScoreItem4(String scoreItem4) {
		this.scoreItem4 = scoreItem4;
	}

	public String getScoreItem5() {
		return scoreItem5;
	}

	public void setScoreItem5(String scoreItem5) {
		this.scoreItem5 = scoreItem5;
	}

	public String getScoreItem6() {
		return scoreItem6;
	}

	public void setScoreItem6(String scoreItem6) {
		this.scoreItem6 = scoreItem6;
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "OrderComments [orderNo=" + orderNo + ", memberNo=" + memberNo + ", scoreItem1=" + scoreItem1
				+ ", scoreItem2=" + scoreItem2 + ", scoreItem3=" + scoreItem3 + ", scoreItem4=" + scoreItem4
				+ ", scoreItem5=" + scoreItem5 + ", scoreItem6=" + scoreItem6 + ", score=" + score + ", content="
				+ content + ", createTime=" + createTime + ", createTimeStart=" + createTimeStart + ", createTimeEnd="
				+ createTimeEnd + ", updateTime=" + updateTime + ", updateTimeStart=" + updateTimeStart
				+ ", updateTimeEnd=" + updateTimeEnd + ", operatorType=" + operatorType + ", operatorId=" + operatorId
				+ "]";
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getCarPlateNo() {
		return carPlateNo;
	}

	public void setCarPlateNo(String carPlateNo) {
		this.carPlateNo = carPlateNo;
	}

	public String getCarModelName() {
		return carModelName;
	}

	public void setCarModelName(String carModelName) {
		this.carModelName = carModelName;
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

}