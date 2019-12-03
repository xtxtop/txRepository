package cn.com.shopec.core.dailyrental.model;

import java.util.Date;

import cn.com.shopec.core.common.Entity;

/** 
 * MerchantUser 数据实体类
 */
public class MerchantUser extends Entity<String> {
	
	private static final long serialVersionUID = 1l;
	
	/*Auto generated properties start*/
	
	//商家用户编号
	private String userNo;
	//商家ID
	private String merchantId;
	//头像照片url
	private String userPhotoUrl;
	//姓名
	private String userName;
	//手机号
	private String mobilePhone;
	//密码
	private String password;
	//城市id
	private String cityId;
	//城市名称
	private String cityName;
	//性别（0、女，1、男）
	private Integer sex;
	//身份证
	private String idCard;
	//身份证照片url
	private String idCardPhotoUrl;
	//是否黑名单（0、非黑名单，1、黑名单，默认0）
	private Integer isBlacklist;
	//移入黑名单备注
	private String blacklistMemo;
	//审核状态（认证状态）（0、未审核/未认证，1、已审核/已认证，2、待审核/待认证，3、未通过，默认0）
	private Integer censorStatus;
	//审核时间
	private Date cencorTime;
	//审核时间 时间范围起（查询用）
	private Date cencorTimeStart;
	//审核时间 时间范围止（查询用）
	private Date cencorTimeEnd;	
	//审核人id
	private String cencorId;
	//审核备注
	private String cencorMemo;
	//创建时间
	private Date createTime;
	//创建时间 时间范围起（查询用）
	private Date createTimeStart;
	//创建时间 时间范围止（查询用）
	private Date createTimeEnd;	
	//更新时间
	private Date updateTime;
	//更新时间 时间范围起（查询用）
	private Date updateTimeStart;
	//更新时间 时间范围止（查询用）
	private Date updateTimeEnd;	
	//操作人类型
	private Integer operatorType;
	//操作人id
	private String operatorId;
	private String token;
	private Date tokenGenerateTime;
	// 时间范围起（查询用）
	private Date tokenGenerateTimeStart;
	// 时间范围止（查询用）
	private Date tokenGenerateTimeEnd;	
	//app推送设备标识id
	private String clientId;
	
	/*Auto generated properties end*/
	
	//商家用户名称
	private String merchantName;
	
	/*Customized properties start*/
	
	/*Customized properties end*/
	
	
	
	/*Auto generated methods start*/
	
	@Override
	public String getPK(){
		return userNo;
	}
	
	public String getUserNo(){
		return userNo;
	}
	
	public void setUserNo(String userNo){
		this.userNo = userNo;
	}
	
	public String getMerchantId(){
		return merchantId;
	}
	
	public void setMerchantId(String merchantId){
		this.merchantId = merchantId;
	}
	
	public String getUserPhotoUrl(){
		return userPhotoUrl;
	}
	
	public void setUserPhotoUrl(String userPhotoUrl){
		this.userPhotoUrl = userPhotoUrl;
	}
	
	public String getUserName(){
		return userName;
	}
	
	public void setUserName(String userName){
		this.userName = userName;
	}
	
	public String getMobilePhone(){
		return mobilePhone;
	}
	
	public void setMobilePhone(String mobilePhone){
		this.mobilePhone = mobilePhone;
	}
	
	public String getPassword(){
		return password;
	}
	
	public void setPassword(String password){
		this.password = password;
	}
	
	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public Integer getSex(){
		return sex;
	}
	
	public void setSex(Integer sex){
		this.sex = sex;
	}
	
	public String getIdCard(){
		return idCard;
	}
	
	public void setIdCard(String idCard){
		this.idCard = idCard;
	}
	
	public String getIdCardPhotoUrl(){
		return idCardPhotoUrl;
	}
	
	public void setIdCardPhotoUrl(String idCardPhotoUrl){
		this.idCardPhotoUrl = idCardPhotoUrl;
	}
	
	public Integer getIsBlacklist(){
		return isBlacklist;
	}
	
	public void setIsBlacklist(Integer isBlacklist){
		this.isBlacklist = isBlacklist;
	}
	
	public String getBlacklistMemo(){
		return blacklistMemo;
	}
	
	public void setBlacklistMemo(String blacklistMemo){
		this.blacklistMemo = blacklistMemo;
	}
	
	public Integer getCensorStatus(){
		return censorStatus;
	}
	
	public void setCensorStatus(Integer censorStatus){
		this.censorStatus = censorStatus;
	}
	
	public Date getCencorTime(){
		return cencorTime;
	}
	
	public void setCencorTime(Date cencorTime){
		this.cencorTime = cencorTime;
	}
	
	public Date getCencorTimeStart(){
		return cencorTimeStart;
	}
	
	public void setCencorTimeStart(Date cencorTimeStart){
		this.cencorTimeStart = cencorTimeStart;
	}
	
	public Date getCencorTimeEnd(){
		return cencorTimeEnd;
	}
	
	public void setCencorTimeEnd(Date cencorTimeEnd){
		this.cencorTimeEnd = cencorTimeEnd;
	}	
	
	public String getCencorId(){
		return cencorId;
	}
	
	public void setCencorId(String cencorId){
		this.cencorId = cencorId;
	}
	
	public String getCencorMemo(){
		return cencorMemo;
	}
	
	public void setCencorMemo(String cencorMemo){
		this.cencorMemo = cencorMemo;
	}
	
	public Date getCreateTime(){
		return createTime;
	}
	
	public void setCreateTime(Date createTime){
		this.createTime = createTime;
	}
	
	public Date getCreateTimeStart(){
		return createTimeStart;
	}
	
	public void setCreateTimeStart(Date createTimeStart){
		this.createTimeStart = createTimeStart;
	}
	
	public Date getCreateTimeEnd(){
		return createTimeEnd;
	}
	
	public void setCreateTimeEnd(Date createTimeEnd){
		this.createTimeEnd = createTimeEnd;
	}	
	
	public Date getUpdateTime(){
		return updateTime;
	}
	
	public void setUpdateTime(Date updateTime){
		this.updateTime = updateTime;
	}
	
	public Date getUpdateTimeStart(){
		return updateTimeStart;
	}
	
	public void setUpdateTimeStart(Date updateTimeStart){
		this.updateTimeStart = updateTimeStart;
	}
	
	public Date getUpdateTimeEnd(){
		return updateTimeEnd;
	}
	
	public void setUpdateTimeEnd(Date updateTimeEnd){
		this.updateTimeEnd = updateTimeEnd;
	}	
	
	public Integer getOperatorType(){
		return operatorType;
	}
	
	public void setOperatorType(Integer operatorType){
		this.operatorType = operatorType;
	}
	
	public String getOperatorId(){
		return operatorId;
	}
	
	public void setOperatorId(String operatorId){
		this.operatorId = operatorId;
	}
	
	public String getToken(){
		return token;
	}
	
	public void setToken(String token){
		this.token = token;
	}
	
	public Date getTokenGenerateTime(){
		return tokenGenerateTime;
	}
	
	public void setTokenGenerateTime(Date tokenGenerateTime){
		this.tokenGenerateTime = tokenGenerateTime;
	}
	
	public Date getTokenGenerateTimeStart(){
		return tokenGenerateTimeStart;
	}
	
	public void setTokenGenerateTimeStart(Date tokenGenerateTimeStart){
		this.tokenGenerateTimeStart = tokenGenerateTimeStart;
	}
	
	public Date getTokenGenerateTimeEnd(){
		return tokenGenerateTimeEnd;
	}
	
	public void setTokenGenerateTimeEnd(Date tokenGenerateTimeEnd){
		this.tokenGenerateTimeEnd = tokenGenerateTimeEnd;
	}	
	
	public String getClientId(){
		return clientId;
	}
	
	public void setClientId(String clientId){
		this.clientId = clientId;
	}
	
	public String getMerchantName() {
		return merchantName;
	}
	
	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}
	
	/*Auto generated methods end*/
	
	
	
	/*Customized methods start*/
	
	/*Customized methods end*/
	
	

	@Override
	public String toString() {
		return "MerchantUser ["
		 + "userNo = " + userNo + ", merchantId = " + merchantId + ", userPhotoUrl = " + userPhotoUrl + ", userName = " + userName
		 + ", mobilePhone = " + mobilePhone + ", password = " + password + ", sex = " + sex + ", idCard = " + idCard
		 + ", idCardPhotoUrl = " + idCardPhotoUrl + ", isBlacklist = " + isBlacklist + ", blacklistMemo = " + blacklistMemo + ", censorStatus = " + censorStatus
		 + ", cencorTime = " + cencorTime + ", cencorTimeStart = " + cencorTimeStart + ", cencorTimeEnd = " + cencorTimeEnd + ", cencorId = " + cencorId + ", cencorMemo = " + cencorMemo + ", createTime = " + createTime + ", createTimeStart = " + createTimeStart + ", createTimeEnd = " + createTimeEnd
		 + ", updateTime = " + updateTime + ", updateTimeStart = " + updateTimeStart + ", updateTimeEnd = " + updateTimeEnd + ", operatorType = " + operatorType + ", operatorId = " + operatorId + ", token = " + token
		 + ", tokenGenerateTime = " + tokenGenerateTime + ", tokenGenerateTimeStart = " + tokenGenerateTimeStart + ", tokenGenerateTimeEnd = " + tokenGenerateTimeEnd + ", clientId = " + clientId
		+"]";
	}
}
