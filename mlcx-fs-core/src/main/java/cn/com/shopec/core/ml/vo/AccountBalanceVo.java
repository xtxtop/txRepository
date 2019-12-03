package cn.com.shopec.core.ml.vo;

import java.util.Date;

public class AccountBalanceVo {
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
		// 审核人id
		private String cencorId;
		// 审核备注
		private String cencorMemo;
		// 创建时间
		private Date createTime;
		// 更新时间
		private Date updateTime;
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
		//账户余额编号
		private String accountBalanceNo;
		//充电余额
		private Double chargingBalance;
		//停车余额
		private Double stopBalance;
		//冻结状态
		private Integer isFreeze;
		//冻结原因
		private String freezeReason;
		//冻结人
		private String freezePerson;
		//冻结时间
		private Date freezeTime;
		//冻结时间 时间范围起（查询用）
		private Date freezeTimeStart;
		//冻结时间 时间范围止（查询用）
		private Date freezeTimeEnd;
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
		public String getPaperName() {
			return paperName;
		}
		public void setPaperName(String paperName) {
			this.paperName = paperName;
		}
		public String getPaperUrl() {
			return paperUrl;
		}
		public void setPaperUrl(String paperUrl) {
			this.paperUrl = paperUrl;
		}
		public Double getPackOrderAmount() {
			return packOrderAmount;
		}
		public void setPackOrderAmount(Double packOrderAmount) {
			this.packOrderAmount = packOrderAmount;
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
		public String getCompanyName() {
			return companyName;
		}
		public void setCompanyName(String companyName) {
			this.companyName = companyName;
		}
		public String getLevelName() {
			return levelName;
		}
		public void setLevelName(String levelName) {
			this.levelName = levelName;
		}
		public String getInvitationCode() {
			return invitationCode;
		}
		public void setInvitationCode(String invitationCode) {
			this.invitationCode = invitationCode;
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
		public String getAmountOrderBy() {
			return amountOrderBy;
		}
		public void setAmountOrderBy(String amountOrderBy) {
			this.amountOrderBy = amountOrderBy;
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
		public Date getOrderFinishTime() {
			return orderFinishTime;
		}
		public void setOrderFinishTime(Date orderFinishTime) {
			this.orderFinishTime = orderFinishTime;
		}
		public String getAccountBalanceNo() {
			return accountBalanceNo;
		}
		public void setAccountBalanceNo(String accountBalanceNo) {
			this.accountBalanceNo = accountBalanceNo;
		}
		public Double getChargingBalance() {
			return chargingBalance;
		}
		public void setChargingBalance(Double chargingBalance) {
			this.chargingBalance = chargingBalance;
		}
		public Double getStopBalance() {
			return stopBalance;
		}
		public void setStopBalance(Double stopBalance) {
			this.stopBalance = stopBalance;
		}
		public Integer getIsFreeze() {
			return isFreeze;
		}
		public void setIsFreeze(Integer isFreeze) {
			this.isFreeze = isFreeze;
		}
		public String getFreezeReason() {
			return freezeReason;
		}
		public void setFreezeReason(String freezeReason) {
			this.freezeReason = freezeReason;
		}
		public String getFreezePerson() {
			return freezePerson;
		}
		public void setFreezePerson(String freezePerson) {
			this.freezePerson = freezePerson;
		}
		public Date getFreezeTime() {
			return freezeTime;
		}
		public void setFreezeTime(Date freezeTime) {
			this.freezeTime = freezeTime;
		}
		public Date getFreezeTimeStart() {
			return freezeTimeStart;
		}
		public void setFreezeTimeStart(Date freezeTimeStart) {
			this.freezeTimeStart = freezeTimeStart;
		}
		public Date getFreezeTimeEnd() {
			return freezeTimeEnd;
		}
		public void setFreezeTimeEnd(Date freezeTimeEnd) {
			this.freezeTimeEnd = freezeTimeEnd;
		}
		@Override
		public String toString() {
			return "AccountBalance [memberNo=" + memberNo + ", memberPhotoUrl="
					+ memberPhotoUrl + ", memberName=" + memberName
					+ ", memberNick=" + memberNick + ", mobilePhone="
					+ mobilePhone + ", password=" + password + ", sex=" + sex
					+ ", idCard=" + idCard + ", expirationDate="
					+ expirationDate + ", expirationDateStart="
					+ expirationDateStart + ", expirationDateEnd="
					+ expirationDateEnd + ", memberType=" + memberType
					+ ", companyId=" + companyId + ", memberCreditLevel="
					+ memberCreditLevel + ", memberLevelId=" + memberLevelId
					+ ", memberPointsValues=" + memberPointsValues
					+ ", registerTime=" + registerTime + ", isJoined="
					+ isJoined + ", isBlacklist=" + isBlacklist
					+ ", blacklistMemo=" + blacklistMemo
					+ ", driverLicensePhotoUrl1=" + driverLicensePhotoUrl1
					+ ", driverLicensePhotoUrl2=" + driverLicensePhotoUrl2
					+ ", idCardPhotoUrl=" + idCardPhotoUrl + ", censorStatus="
					+ censorStatus + ", cencorTime=" + cencorTime
					+ ", cencorId=" + cencorId + ", cencorMemo=" + cencorMemo
					+ ", createTime=" + createTime + ", updateTime="
					+ updateTime + ", operatorType=" + operatorType
					+ ", operatorId=" + operatorId + ", paperName=" + paperName
					+ ", paperUrl=" + paperUrl + ", packOrderAmount="
					+ packOrderAmount + ", residueDeposit=" + residueDeposit
					+ ", noPayAmount=" + noPayAmount + ", orderNum=" + orderNum
					+ ", illegalNum=" + illegalNum + ", companyName="
					+ companyName + ", levelName=" + levelName
					+ ", invitationCode=" + invitationCode + ", refereeId="
					+ refereeId + ", isDistributor=" + isDistributor
					+ ", token=" + token + ", tokenGenerateTime="
					+ tokenGenerateTime + ", deposit=" + deposit + ", keyword="
					+ keyword + ", licenseId=" + licenseId + ", clientId="
					+ clientId + ", realAmount=" + realAmount
					+ ", amountOrderBy=" + amountOrderBy
					+ ", orderFinishTotal=" + orderFinishTotal
					+ ", rechargeTotal=" + rechargeTotal
					+ ", orderAccountTotal=" + orderAccountTotal
					+ ", payableDepositAmount=" + payableDepositAmount
					+ ", orderFinishTime=" + orderFinishTime
					+ ", accountBalanceNo=" + accountBalanceNo
					+ ", chargingBalance=" + chargingBalance + ", stopBalance="
					+ stopBalance + ", isFreeze=" + isFreeze
					+ ", freezeReason=" + freezeReason + ", freezePerson="
					+ freezePerson + ", freezeTime=" + freezeTime
					+ ", freezeTimeStart=" + freezeTimeStart
					+ ", freezeTimeEnd=" + freezeTimeEnd + "]";
		}	
		
}
