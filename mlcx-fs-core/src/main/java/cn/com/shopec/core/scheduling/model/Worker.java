package cn.com.shopec.core.scheduling.model;

import java.util.Date;

import cn.com.shopec.core.common.Entity;

/**
 * 调度员表 数据实体类
 */
public class Worker extends Entity<String> {

	private static final long serialVersionUID = 1l;

	/* Auto generated properties start */

	// 调度员编号
	private String workerNo;
	// 城市id
	private String cityId;
	// 所属片区（具体值来自字典表）
	private String regionId;
	// 片区名称
	private String regionName;
	// 城市
	private String cityName;
	// 工号
	private String empNo;
	// 姓名
	private String workerName;
	// 手机
	private String mobilePhone;
	// 密码
	private String password;
	// 系统用户id（如果与系统用户有关联时）
	private String sysUserId;
	// 上报位置经度
	private Double locationLongitude;
	// 上报位置纬度
	private Double locationLatitude;
	// 最后上报位置时间
	private Date locationUpdateTime;
	// 最后上报位置时间 时间范围起（查询用）
	private Date locationUpdateTimeStart;
	// 最后上报位置时间 时间范围止（查询用）
	private Date locationUpdateTimeEnd;
	// 调度端App是否在线（0、离线，1、在线）
	private Integer appIsOnline;
	// App最后在线时间
	private Date appLastOnlineTime;
	// App最后在线时间 时间范围起（查询用）
	private Date appLastOnlineTimeStart;
	// App最后在线时间 时间范围止（查询用）
	private Date appLastOnlineTimeEnd;
	// 是否可用（0、不可用，1、可用，默认1，）
	private Integer isAvailable;
	// token
	private String token;
	// token生成时间
	private Date tokenGenerateTime;
	// 创建日期
	private Date createTime;
	// 创建日期 时间范围起（查询用）
	private Date createTimeStart;
	// 创建日期 时间范围止（查询用）
	private Date createTimeEnd;
	// 更新日期
	private Date updateTime;
	// 更新日期 时间范围起（查询用）
	private Date updateTimeStart;
	// 更新日期 时间范围止（查询用）
	private Date updateTimeEnd;
	// 操作人类型
	private Integer operatorType;
	// 操作人id
	private String operatorId;

	// 搜索条件 :姓名或工号
	private String nameOrempNo;

	/* Auto generated properties end */

	/* Customized properties start */

	/* Customized properties end */

	/* Auto generated methods start */

	@Override
	public String getPK() {
		return workerNo;
	}

	public String getWorkerNo() {
		return workerNo;
	}

	public void setWorkerNo(String workerNo) {
		this.workerNo = workerNo;
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

	public String getEmpNo() {
		return empNo;
	}

	public void setEmpNo(String empNo) {
		this.empNo = empNo;
	}

	public String getWorkerName() {
		return workerName;
	}

	public void setWorkerName(String workerName) {
		this.workerName = workerName;
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

	public String getSysUserId() {
		return sysUserId;
	}

	public void setSysUserId(String sysUserId) {
		this.sysUserId = sysUserId;
	}

	public Double getLocationLongitude() {
		return locationLongitude;
	}

	public void setLocationLongitude(Double locationLongitude) {
		this.locationLongitude = locationLongitude;
	}

	public Double getLocationLatitude() {
		return locationLatitude;
	}

	public void setLocationLatitude(Double locationLatitude) {
		this.locationLatitude = locationLatitude;
	}

	public Date getLocationUpdateTime() {
		return locationUpdateTime;
	}

	public void setLocationUpdateTime(Date locationUpdateTime) {
		this.locationUpdateTime = locationUpdateTime;
	}

	public Date getLocationUpdateTimeStart() {
		return locationUpdateTimeStart;
	}

	public void setLocationUpdateTimeStart(Date locationUpdateTimeStart) {
		this.locationUpdateTimeStart = locationUpdateTimeStart;
	}

	public Date getLocationUpdateTimeEnd() {
		return locationUpdateTimeEnd;
	}

	public void setLocationUpdateTimeEnd(Date locationUpdateTimeEnd) {
		this.locationUpdateTimeEnd = locationUpdateTimeEnd;
	}

	public Integer getAppIsOnline() {
		return appIsOnline;
	}

	public void setAppIsOnline(Integer appIsOnline) {
		this.appIsOnline = appIsOnline;
	}

	public Date getAppLastOnlineTime() {
		return appLastOnlineTime;
	}

	public void setAppLastOnlineTime(Date appLastOnlineTime) {
		this.appLastOnlineTime = appLastOnlineTime;
	}

	public Date getAppLastOnlineTimeStart() {
		return appLastOnlineTimeStart;
	}

	public void setAppLastOnlineTimeStart(Date appLastOnlineTimeStart) {
		this.appLastOnlineTimeStart = appLastOnlineTimeStart;
	}

	public Date getAppLastOnlineTimeEnd() {
		return appLastOnlineTimeEnd;
	}

	public void setAppLastOnlineTimeEnd(Date appLastOnlineTimeEnd) {
		this.appLastOnlineTimeEnd = appLastOnlineTimeEnd;
	}

	public Integer getIsAvailable() {
		return isAvailable;
	}

	public void setIsAvailable(Integer isAvailable) {
		this.isAvailable = isAvailable;
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
		return "Worker [" + "workerNo = " + workerNo + ", cityId = " + cityId + ", cityName = " + cityName
				+ ", empNo = " + empNo + ", workerName = " + workerName + ", mobilePhone = " + mobilePhone
				+ ", password = " + password + ", sysUserId = " + sysUserId + ", locationLongitude = "
				+ locationLongitude + ", locationLatitude = " + locationLatitude + ", locationUpdateTime = "
				+ locationUpdateTime + ", locationUpdateTimeStart = " + locationUpdateTimeStart
				+ ", locationUpdateTimeEnd = " + locationUpdateTimeEnd + ", appIsOnline = " + appIsOnline
				+ ", appLastOnlineTime = " + appLastOnlineTime + ", appLastOnlineTimeStart = " + appLastOnlineTimeStart
				+ ", appLastOnlineTimeEnd = " + appLastOnlineTimeEnd + ", isAvailable = " + isAvailable
				+ ", createTime = " + createTime + ", createTimeStart = " + createTimeStart + ", createTimeEnd = "
				+ createTimeEnd + ", updateTime = " + updateTime + ", updateTimeStart = " + updateTimeStart
				+ ", updateTimeEnd = " + updateTimeEnd + ", operatorType = " + operatorType + ", operatorId = "
				+ operatorId + "]";
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

	public String getNameOrempNo() {
		return nameOrempNo;
	}

	public void setNameOrempNo(String nameOrempNo) {
		this.nameOrempNo = nameOrempNo;
	}

	public String getRegionId() {
		return regionId;
	}

	public void setRegionId(String regionId) {
		this.regionId = regionId;
	}

	public String getRegionName() {
		return regionName;
	}

	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}
}
