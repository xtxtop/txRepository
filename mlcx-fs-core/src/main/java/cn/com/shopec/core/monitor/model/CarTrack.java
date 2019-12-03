package cn.com.shopec.core.monitor.model;

import java.util.Date;

import cn.com.shopec.core.common.Entity;

/**
 * 车辆轨迹表 数据实体类
 */
public class CarTrack extends Entity<String> {

	private static final long serialVersionUID = 1l;

	/* Auto generated properties start */

	// 轨迹编号
	private String trackId;
	// 城市id
	private String cityId;
	// 车辆编号
	private String carNo;
	private String carPlateNo;
	// 坐标经度
	private Double longitude;
	// 坐标纬度
	private Double latitude;
	// 航向角度
	private Double courseAngle;
	// speed 速度 double 单位：km/h
	private Double speed;
	// 总里程
	private Double mileage;
	// 车辆启动熄火状态
	private Integer carStatus;
	// 车辆使用状态（0，空闲、1，已预占、2，订单中、3，调度中）
	private Integer carUserageStatus;
	// 车辆使用人
	private String memberName;
	// 使用人手机号
	private String phone;
	// 单据号(订单或者调度单号)
	private String documentNo;
	// 剩余电量或油量
	private String power;
	// 创建时间
	private Date createTime;
	// 创建时间 时间范围起（查询用）
	private Date createTimeStart;
	// 创建时间 时间范围止（查询用）
	private Date createTimeEnd;

	/* Auto generated properties end */

	/* Customized properties start */

	/* Customized properties end */

	/* Auto generated methods start */

	@Override
	public String getPK() {
		return trackId;
	}

	public String getTrackId() {
		return trackId;
	}

	public void setTrackId(String trackId) {
		this.trackId = trackId;
	}

	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	public String getCarNo() {
		return carNo;
	}

	public void setCarNo(String carNo) {
		this.carNo = carNo;
	}

	public String getCarPlateNo() {
		return carPlateNo;
	}

	public void setCarPlateNo(String carPlateNo) {
		this.carPlateNo = carPlateNo;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getCourseAngle() {
		return courseAngle;
	}

	public void setCourseAngle(Double courseAngle) {
		this.courseAngle = courseAngle;
	}

	public Double getSpeed() {
		return speed;
	}

	public void setSpeed(Double speed) {
		this.speed = speed;
	}

	public Integer getCarStatus() {
		return carStatus;
	}

	public void setCarStatus(Integer carStatus) {
		this.carStatus = carStatus;
	}

	public Integer getCarUserageStatus() {
		return carUserageStatus;
	}

	public void setCarUserageStatus(Integer carUserageStatus) {
		this.carUserageStatus = carUserageStatus;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getDocumentNo() {
		return documentNo;
	}

	public void setDocumentNo(String documentNo) {
		this.documentNo = documentNo;
	}

	public String getPower() {
		return power;
	}

	public void setPower(String power) {
		this.power = power;
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

	/* Auto generated methods end */

	/* Customized methods start */

	/* Customized methods end */

	public Double getMileage() {
		return mileage;
	}

	public void setMileage(Double mileage) {
		this.mileage = mileage;
	}

	@Override
	public String toString() {
		return "CarTrack [" + "trackId = " + trackId + ", cityId = " + cityId + ", carNo = " + carNo + ", carPlateNo = "
				+ carPlateNo + ", longitude = " + longitude + ", latitude = " + latitude + ", createTime = "
				+ createTime + ", createTimeStart = " + createTimeStart + ", createTimeEnd = " + createTimeEnd + "]";
	}
}
