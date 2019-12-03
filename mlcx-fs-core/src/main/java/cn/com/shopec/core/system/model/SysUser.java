package cn.com.shopec.core.system.model;

import java.util.Date;
import java.util.List;

import cn.com.shopec.core.common.Entity;

/**
 * SysUser 数据库实体类
 */
public class SysUser extends Entity<String> {

	private static final long serialVersionUID = 1l;

	// 用户id
	private String userId;
	// 用户名
	private String userName;
	// 密码
	private String password;
	// 真实姓名
	private String realName;
	// email
	private String email;
	// 手机
	private String mobilePhone;
	// 电话
	private String telPhone;
	// 最近登陆时间
	private Date lastLoginTime;
	// 备注
	private String memo;
	// 账户是否可用（1、可用，0、不可用，默认1）
	private Integer isAvailable;
	// 是否已删除（逻辑删除，1、已删除，0、未删除，默认0）
	private Integer isDeleted;
	// 创建时间
	private Date createTime;
	// 更新时间
	private Date updateTime;
	// 操作人类型（0、系统自动操作，1、平台人员操作，2、商家人员操作，3、会员操作）
	private Integer operatorType;
	// 操作人id（根据操作人类型会对应不同的表记录）
	private String operatorId;
	private List<SysRole> sysRole;

	// 用户标识 （1平台 2运营城市）
	private Integer identification;
	// 城市编号
	private List<SysUserCity> sysUserCitys;

	@Override
	public String getPK() {
		return userId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public String getTelPhone() {
		return telPhone;
	}

	public void setTelPhone(String telPhone) {
		this.telPhone = telPhone;
	}

	public Date getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public Integer getIsAvailable() {
		return isAvailable;
	}

	public void setIsAvailable(Integer isAvailable) {
		this.isAvailable = isAvailable;
	}

	public Integer getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Integer isDeleted) {
		this.isDeleted = isDeleted;
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

	public List<SysRole> getSysRole() {
		return sysRole;
	}

	public void setSysRole(List<SysRole> sysRole) {
		this.sysRole = sysRole;
	}

	public Integer getIdentification() {
		return identification;
	}

	public void setIdentification(Integer identification) {
		this.identification = identification;
	}

	public List<SysUserCity> getSysUserCitys() {
		return sysUserCitys;
	}

	public void setSysUserCitys(List<SysUserCity> sysUserCitys) {
		this.sysUserCitys = sysUserCitys;
	}

}
