package cn.com.shopec.core.member.model;

import java.util.Date;

import cn.com.shopec.core.common.Entity;

/**
 * Member 数据实体类
 */
public class Member extends Entity<String> {

	private static final long serialVersionUID = 1l;

	/* Auto generated properties start */

	// 会员编号
	private String memberNo;
	// 头像照片url
	private String memberPhotoUrl;
	// 姓名
	private String memberName;
	// 昵称
	private String memberNick;
	// 手机号
	private String mobilePhone;
	// 密码
	private String password;
	// 性别（0、女，1、男）
	private Integer sex;
	// 身份证
	private String idCard;
	// 驾驶证有效期至
	private Date expirationDate;
	// 驾驶证有效期至 时间范围起（查询用）
	private Date expirationDateStart;
	// 驾驶证有效期至 时间范围止（查询用）
	private Date expirationDateEnd;
	// 会员类型（1、普通会员，2、集团会员，默认1）
	private Integer memberType;
	// 集团id
	private String companyId;
	// 信誉等级
	private Integer memberCreditLevel;
	// 会员级别id
	private String memberLevelId;
	// 会员积分值
	private Integer memberPointsValues;
	// 注册时间
	private Date registerTime;
	// 注册时间 时间范围起（查询用）
	private Date registerTimeStart;
	// 注册时间 时间范围止（查询用）
	private Date registerTimeEnd;
	// 是否加盟（0、未加盟，1、已加盟，2、加盟申请中，默认0）
	private Integer isJoined;
	// 是否黑名单（0、非黑名单，1、黑名单，默认0）
	private Integer isBlacklist;
	// 移入黑名单备注
	private String blacklistMemo;
	// 驾驶证照片url1
	private String driverLicensePhotoUrl1;
	// 驾驶证照片url2
	private String driverLicensePhotoUrl2;
	// 身份证照片url
	private String idCardPhotoUrl;
	// 审核状态（认证状态）（0、未审核/未认证，1、已审核/已认证，2、待审核/待认证，3、未通过，默认0）
	private Integer censorStatus;
	// 审核时间
	private Date cencorTime;
	// 审核时间 时间范围起（查询用）
	private Date cencorTimeStart;
	// 审核时间 时间范围止（查询用）
	private Date cencorTimeEnd;
	// 审核人id
	private String cencorId;
	// 审核备注
	private String cencorMemo;
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
	// 其他证件名称
	private String paperName;
	// 其他证件url
	private String paperUrl;

	/* Auto generated properties end */
	// 剩余套餐金额
	private Double packOrderAmount;
	// 剩余押金
	private Double residueDeposit;
	// 欠款金额
	private Double noPayAmount;
	// 会员订单数
	private Long orderNum;
	// 违章记录数
	private Long illegalNum;
	// 集团名称
	private String companyName;
	// 会员等级名称
	private String levelName;
	/* Customized properties start */
	// 邀请码
	private String invitationCode;
	// 推荐人Id
	private String refereeId;
	// 是否分销商（0 否 1 是）
	private Integer isDistributor;
	/* Customized properties end */
	// 新增属性
	// token
	private String token;
	// token生成时间
	private Date tokenGenerateTime;

	// 剩余押金金额
	private Double deposit;
	// 搜索关键字
	private String keyword;
	// 档案编号
	private String licenseId;
	// 会员编号
	private String clientId;
	// 真实支付总额
	private Double realAmount;
	//余额排序（查询）
	private String amountOrderBy;
	// 已完成订单量(数)
	private Integer orderFinishTotal;
	// 已充值总额
	private Double rechargeTotal;
	// 已消费总额
	private Double orderAccountTotal;
	//会员应支付押金金额
	private Double payableDepositAmount;
	//订单最后时间
	private Date orderFinishTime;

	/* Auto generated methods start */

	@Override
	public String getPK() {
		return memberNo;
	}

	public String getMemberNo() {
		return memberNo;
	}

	public void setMemberNo(String memberNo) {
		this.memberNo = memberNo;
	}

	public String getMemberPhotoUrl() {
		return memberPhotoUrl;
	}

	public void setMemberPhotoUrl(String memberPhotoUrl) {
		this.memberPhotoUrl = memberPhotoUrl;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public String getMemberNick() {
		return memberNick;
	}

	public void setMemberNick(String memberNick) {
		this.memberNick = memberNick;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public Date getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}

	public Date getExpirationDateStart() {
		return expirationDateStart;
	}

	public void setExpirationDateStart(Date expirationDateStart) {
		this.expirationDateStart = expirationDateStart;
	}

	public Date getExpirationDateEnd() {
		return expirationDateEnd;
	}

	public void setExpirationDateEnd(Date expirationDateEnd) {
		this.expirationDateEnd = expirationDateEnd;
	}

	public Integer getMemberType() {
		return memberType;
	}

	public void setMemberType(Integer memberType) {
		this.memberType = memberType;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public Integer getMemberCreditLevel() {
		return memberCreditLevel;
	}

	public void setMemberCreditLevel(Integer memberCreditLevel) {
		this.memberCreditLevel = memberCreditLevel;
	}

	public String getMemberLevelId() {
		return memberLevelId;
	}

	public void setMemberLevelId(String memberLevelId) {
		this.memberLevelId = memberLevelId;
	}

	public Integer getMemberPointsValues() {
		return memberPointsValues;
	}

	public void setMemberPointsValues(Integer memberPointsValues) {
		this.memberPointsValues = memberPointsValues;
	}

	public Date getRegisterTime() {
		return registerTime;
	}

	public void setRegisterTime(Date registerTime) {
		this.registerTime = registerTime;
	}

	public Date getRegisterTimeStart() {
		return registerTimeStart;
	}

	public void setRegisterTimeStart(Date registerTimeStart) {
		this.registerTimeStart = registerTimeStart;
	}

	public Date getRegisterTimeEnd() {
		return registerTimeEnd;
	}

	public void setRegisterTimeEnd(Date registerTimeEnd) {
		this.registerTimeEnd = registerTimeEnd;
	}

	public Integer getIsJoined() {
		return isJoined;
	}

	public void setIsJoined(Integer isJoined) {
		this.isJoined = isJoined;
	}

	public Integer getIsBlacklist() {
		return isBlacklist;
	}

	public void setIsBlacklist(Integer isBlacklist) {
		this.isBlacklist = isBlacklist;
	}

	public String getBlacklistMemo() {
		return blacklistMemo;
	}

	public void setBlacklistMemo(String blacklistMemo) {
		this.blacklistMemo = blacklistMemo;
	}

	public String getDriverLicensePhotoUrl1() {
		return driverLicensePhotoUrl1;
	}

	public void setDriverLicensePhotoUrl1(String driverLicensePhotoUrl1) {
		this.driverLicensePhotoUrl1 = driverLicensePhotoUrl1;
	}

	public String getDriverLicensePhotoUrl2() {
		return driverLicensePhotoUrl2;
	}

	public void setDriverLicensePhotoUrl2(String driverLicensePhotoUrl2) {
		this.driverLicensePhotoUrl2 = driverLicensePhotoUrl2;
	}

	public String getIdCardPhotoUrl() {
		return idCardPhotoUrl;
	}

	public void setIdCardPhotoUrl(String idCardPhotoUrl) {
		this.idCardPhotoUrl = idCardPhotoUrl;
	}

	public Integer getCensorStatus() {
		return censorStatus;
	}

	public void setCensorStatus(Integer censorStatus) {
		this.censorStatus = censorStatus;
	}

	public Date getCencorTime() {
		return cencorTime;
	}

	public void setCencorTime(Date cencorTime) {
		this.cencorTime = cencorTime;
	}

	public Date getCencorTimeStart() {
		return cencorTimeStart;
	}

	public void setCencorTimeStart(Date cencorTimeStart) {
		this.cencorTimeStart = cencorTimeStart;
	}

	public Date getCencorTimeEnd() {
		return cencorTimeEnd;
	}

	public void setCencorTimeEnd(Date cencorTimeEnd) {
		this.cencorTimeEnd = cencorTimeEnd;
	}

	public String getCencorId() {
		return cencorId;
	}

	public void setCencorId(String cencorId) {
		this.cencorId = cencorId;
	}

	public String getCencorMemo() {
		return cencorMemo;
	}

	public void setCencorMemo(String cencorMemo) {
		this.cencorMemo = cencorMemo;
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

	public Double getPackOrderAmount() {
		return packOrderAmount;
	}

	public void setPackOrderAmount(Double packOrderAmount) {
		this.packOrderAmount = packOrderAmount;
	}

	public String getLevelName() {
		return levelName;
	}

	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getInvitationCode() {
		return invitationCode;
	}

	public void setInvitationCode(String invitationCode) {
		this.invitationCode = invitationCode;
	}

	@Override
	public String toString() {
		return "Member [" + "memberNo = " + memberNo + ", memberPhotoUrl = " + memberPhotoUrl + ", memberName = "
				+ memberName + ", memberNick = " + memberNick + ", mobilePhone = " + mobilePhone + ", password = "
				+ password + ", sex = " + sex + ", idCard = " + idCard + ", expirationDate = " + expirationDate
				+ ", expirationDateStart = " + expirationDateStart + ", expirationDateEnd = " + expirationDateEnd
				+ ", memberType = " + memberType + ", companyId = " + companyId + ", memberCreditLevel = "
				+ memberCreditLevel + ", memberLevelId = " + memberLevelId + ", registerTime = " + registerTime
				+ ", registerTimeStart = " + registerTimeStart + ", registerTimeEnd = " + registerTimeEnd
				+ ", isJoined = " + isJoined + ", isBlacklist = " + isBlacklist + ", blacklistMemo = " + blacklistMemo
				+ ", driverLicensePhotoUrl1 = " + driverLicensePhotoUrl1 + ", driverLicensePhotoUrl2 = "
				+ driverLicensePhotoUrl2 + ", censorStatus = " + censorStatus + ", cencorTime = " + cencorTime
				+ ", cencorTimeStart = " + cencorTimeStart + ", cencorTimeEnd = " + cencorTimeEnd + ", cencorId = "
				+ cencorId + ", cencorMemo = " + cencorMemo + ", createTime = " + createTime + ", createTimeStart = "
				+ createTimeStart + ", createTimeEnd = " + createTimeEnd + ", updateTime = " + updateTime
				+ ", updateTimeStart = " + updateTimeStart + ", updateTimeEnd = " + updateTimeEnd + ", operatorType = "
				+ operatorType + ", operatorId = " + operatorId + ", refereeId = " + refereeId + "]";
	}

	public Double getResidueDeposit() {
		return residueDeposit;
	}

	public void setResidueDeposit(Double residueDeposit) {
		this.residueDeposit = residueDeposit;
	}

	public Double getNoPayAmount() {
		return noPayAmount;
	}

	public void setNoPayAmount(Double noPayAmount) {
		this.noPayAmount = noPayAmount;
	}

	public Long getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(Long orderNum) {
		this.orderNum = orderNum;
	}

	public Long getIllegalNum() {
		return illegalNum;
	}

	public void setIllegalNum(Long illegalNum) {
		this.illegalNum = illegalNum;
	}

	public String getRefereeId() {
		return refereeId;
	}

	public void setRefereeId(String refereeId) {
		this.refereeId = refereeId;
	}

	public Integer getIsDistributor() {
		return isDistributor;
	}

	public void setIsDistributor(Integer isDistributor) {
		this.isDistributor = isDistributor;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Date getTokenGenerateTime() {
		return tokenGenerateTime;
	}

	public void setTokenGenerateTime(Date tokenGenerateTime) {
		this.tokenGenerateTime = tokenGenerateTime;
	}

	public Double getDeposit() {
		return deposit;
	}

	public void setDeposit(Double deposit) {
		this.deposit = deposit;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getLicenseId() {
		return licenseId;
	}

	public void setLicenseId(String licenseId) {
		this.licenseId = licenseId;
	}

	public String getPaperUrl() {
		return paperUrl;
	}

	public void setPaperUrl(String paperUrl) {
		this.paperUrl = paperUrl;
	}

	public String getPaperName() {
		return paperName;
	}

	public void setPaperName(String paperName) {
		this.paperName = paperName;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public Double getRealAmount() {
		return realAmount;
	}

	public void setRealAmount(Double realAmount) {
		this.realAmount = realAmount;
	}

	public Integer getOrderFinishTotal() {
		return orderFinishTotal;
	}

	public void setOrderFinishTotal(Integer orderFinishTotal) {
		this.orderFinishTotal = orderFinishTotal;
	}

	public Double getRechargeTotal() {
		return rechargeTotal;
	}

	public void setRechargeTotal(Double rechargeTotal) {
		this.rechargeTotal = rechargeTotal;
	}

	public Double getOrderAccountTotal() {
		return orderAccountTotal;
	}

	public void setOrderAccountTotal(Double orderAccountTotal) {
		this.orderAccountTotal = orderAccountTotal;
	}

	public Double getPayableDepositAmount() {
		return payableDepositAmount;
	}

	public void setPayableDepositAmount(Double payableDepositAmount) {
		this.payableDepositAmount = payableDepositAmount;
	}

	public String getAmountOrderBy() {
		return amountOrderBy;
	}

	public void setAmountOrderBy(String amountOrderBy) {
		this.amountOrderBy = amountOrderBy;
	}

	public Date getOrderFinishTime() {
		return orderFinishTime;
	}

	public void setOrderFinishTime(Date orderFinishTime) {
		this.orderFinishTime = orderFinishTime;
	}

}
