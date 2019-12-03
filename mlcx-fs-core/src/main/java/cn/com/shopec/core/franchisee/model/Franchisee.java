package cn.com.shopec.core.franchisee.model;

import java.util.Date;

import cn.com.shopec.core.common.Entity;

/**
 * 加盟商表 数据实体类
 */
public class Franchisee extends Entity<String> {

	private static final long serialVersionUID = 1l;

	/* Auto generated properties start */

	// 加盟商编号
	private String franchiseeNo;
	// 加盟商名称
	private String franchiseeName;
	// 加盟商全称
	private String franchiseeFullName;
	// 联系人
	private String contacts;
	// 联系电话
	private String contactsPhone;
	// 联系邮箱
	private String mailbox;
	// 分润比例（按车）
	private Double carProportion;
	// 分润比例（按场站）
	private Double parkProportion;
	// 相关证件1
	private String franchiseePhotoUrl1;
	// 相关证件2
	private String franchiseePhotoUrl2;
	// 相关证件3
	private String franchiseePhotoUrl3;
	// 备注
	private String memo;
	// 审核状态：0、未审核，1、审核已通过，2、审核未通过
	private Integer censorStatus;
	// 审核备注
	private String censorMemo;
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

	/* Auto generated properties end */

	/* Customized properties start */

	/* Customized properties end */

	/* Auto generated methods start */

	@Override
	public String getPK() {
		return franchiseeNo;
	}

	public String getFranchiseeNo() {
		return franchiseeNo;
	}

	public void setFranchiseeNo(String franchiseeNo) {
		this.franchiseeNo = franchiseeNo;
	}

	public String getFranchiseeName() {
		return franchiseeName;
	}

	public void setFranchiseeName(String franchiseeName) {
		this.franchiseeName = franchiseeName;
	}

	public String getFranchiseeFullName() {
		return franchiseeFullName;
	}

	public void setFranchiseeFullName(String franchiseeFullName) {
		this.franchiseeFullName = franchiseeFullName;
	}

	public String getContacts() {
		return contacts;
	}

	public void setContacts(String contacts) {
		this.contacts = contacts;
	}

	public String getContactsPhone() {
		return contactsPhone;
	}

	public void setContactsPhone(String contactsPhone) {
		this.contactsPhone = contactsPhone;
	}

	public String getMailbox() {
		return mailbox;
	}

	public void setMailbox(String mailbox) {
		this.mailbox = mailbox;
	}

	public Double getCarProportion() {
		return carProportion;
	}

	public void setCarProportion(Double carProportion) {
		this.carProportion = carProportion;
	}

	public Double getParkProportion() {
		return parkProportion;
	}

	public void setParkProportion(Double parkProportion) {
		this.parkProportion = parkProportion;
	}

	public String getFranchiseePhotoUrl1() {
		return franchiseePhotoUrl1;
	}

	public void setFranchiseePhotoUrl1(String franchiseePhotoUrl1) {
		this.franchiseePhotoUrl1 = franchiseePhotoUrl1;
	}

	public String getFranchiseePhotoUrl2() {
		return franchiseePhotoUrl2;
	}

	public void setFranchiseePhotoUrl2(String franchiseePhotoUrl2) {
		this.franchiseePhotoUrl2 = franchiseePhotoUrl2;
	}

	public String getFranchiseePhotoUrl3() {
		return franchiseePhotoUrl3;
	}

	public void setFranchiseePhotoUrl3(String franchiseePhotoUrl3) {
		this.franchiseePhotoUrl3 = franchiseePhotoUrl3;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public Integer getCensorStatus() {
		return censorStatus;
	}

	public void setCensorStatus(Integer censorStatus) {
		this.censorStatus = censorStatus;
	}

	public String getCensorMemo() {
		return censorMemo;
	}

	public void setCensorMemo(String censorMemo) {
		this.censorMemo = censorMemo;
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

	/* Auto generated methods end */

	/* Customized methods start */

	/* Customized methods end */

	@Override
	public String toString() {
		return "Franchisee [" + "franchiseeNo = " + franchiseeNo + ", franchiseeName = " + franchiseeName
				+ ", franchiseeFullName = " + franchiseeFullName + ", contacts = " + contacts + ", contactsPhone = "
				+ contactsPhone + ", mailbox = " + mailbox + ", carProportion = " + carProportion
				+ ", parkProportion = " + parkProportion + ", franchiseePhotoUrl1 = " + franchiseePhotoUrl1
				+ ", franchiseePhotoUrl2 = " + franchiseePhotoUrl2 + ", franchiseePhotoUrl3 = " + franchiseePhotoUrl3
				+ ", memo = " + memo + ", censorStatus = " + censorStatus + ", censorMemo = " + censorMemo
				+ ", createTime = " + createTime + ", createTimeStart = " + createTimeStart + ", createTimeEnd = "
				+ createTimeEnd + ", updateTime = " + updateTime + ", updateTimeStart = " + updateTimeStart
				+ ", updateTimeEnd = " + updateTimeEnd + ", operatorType = " + operatorType + ", operatorId = "
				+ operatorId + "]";
	}
}
