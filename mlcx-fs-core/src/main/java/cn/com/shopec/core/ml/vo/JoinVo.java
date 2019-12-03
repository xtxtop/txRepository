package cn.com.shopec.core.ml.vo;

import java.util.Date;

/**
 * @author daiyuanbao 
 * 申请加盟VO
 *
 */
public class JoinVo {
	// 申请加盟编号
	private String joinNo;
	// 会员编号
	private String memberNo;
	// 加盟类型(1.充电桩 2.停车场)
	private Integer joinType;
	// 备注
	private String remark;
	// 创建时间
	private Date createTime;
	// 会员名称
	private String memberName;
	// 电话
	private String mobilePhone;
	public String getJoinNo() {
		return joinNo;
	}
	public void setJoinNo(String joinNo) {
		this.joinNo = joinNo;
	}
	public String getMemberNo() {
		return memberNo;
	}
	public void setMemberNo(String memberNo) {
		this.memberNo = memberNo;
	}
	public Integer getJoinType() {
		return joinType;
	}
	public void setJoinType(Integer joinType) {
		this.joinType = joinType;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
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
