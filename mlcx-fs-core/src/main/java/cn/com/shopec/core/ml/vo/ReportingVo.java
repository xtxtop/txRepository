package cn.com.shopec.core.ml.vo;

import java.util.Date;

/**
 * @author daiyuanbao 违停上报VO
 *
 */
public class ReportingVo {
	// 违停上报编号
	private String reportingNo;
	// 会员编号
	private String memberNo;
	// 违停详情
	private String remark;
	// 创建时间
	private Date createTime;
	// 会员名称
	private String memberName;
	// 电话
	private String mobilePhone;
	//地锁编号
	private String lockNo;
	//地锁名称
	private String lockName;
	public String getReportingNo() {
		return reportingNo;
	}
	public void setReportingNo(String reportingNo) {
		this.reportingNo = reportingNo;
	}
	public String getMemberNo() {
		return memberNo;
	}
	public void setMemberNo(String memberNo) {
		this.memberNo = memberNo;
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
	public String getLockNo() {
		return lockNo;
	}
	public void setLockNo(String lockNo) {
		this.lockNo = lockNo;
	}
	public String getLockName() {
		return lockName;
	}
	public void setLockName(String lockName) {
		this.lockName = lockName;
	}
	
}
