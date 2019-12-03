package cn.com.shopec.core.map.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 鹰眼轨迹对象track
 *
 */
public class Track implements Serializable {
	private static final long serialVersionUID = -1635101908045984900L;
	// 纬度 double(-90.0 , +90.0) 必选
	private Double latitude;
	// 经度 double(-180.0 , +180.0)必选
	private Double longitude;
	// coord_type坐标类型 int(1-3)必选，1：GPS经纬度坐标2：国测局加密经纬度坐标 3：百度加密经纬度坐标。
	private Integer coordType;
	// speed 速度 double 可选，单位：km/h
	private Double speed;
	// direction方向 int可选，范围为[0,359]，0度为正北方向，顺时针
	private Integer direction;
	// radius 定位精度，GPS或定位SDK返回的值。 double 可选，单位米
	private Double radius;
	// entity唯一标识entity_name 必选
	private String entityName;
	// loc_time 轨迹点采集的GPS时间。 必选。输入的loc_time不能大于当前服务端时间10分钟以上，即不支持存未来的轨迹点。
	private Date locTime;
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
	// 车牌号
	private String carPlateNo;
	// 总里程
	private Double mileage;

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public Integer getCoordType() {
		return coordType;
	}

	public void setCoordType(Integer coordType) {
		this.coordType = coordType;
	}

	public Double getSpeed() {
		return speed;
	}

	public void setSpeed(Double speed) {
		this.speed = speed;
	}

	public Integer getDirection() {
		return direction;
	}

	public void setDirection(Integer direction) {
		this.direction = direction;
	}

	public Double getRadius() {
		return radius;
	}

	public void setRadius(Double radius) {
		this.radius = radius;
	}

	public String getEntityName() {
		return entityName;
	}

	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}

	public Date getLocTime() {
		return locTime;
	}

	public void setLocTime(Date locTime) {
		this.locTime = locTime;
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

	public String getCarPlateNo() {
		return carPlateNo;
	}

	public void setCarPlateNo(String carPlateNo) {
		this.carPlateNo = carPlateNo;
	}

	@Override
	public String toString() {
		return "Track [latitude=" + latitude + ", longitude=" + longitude + ", coordType=" + coordType + ", speed="
				+ speed + ", direction=" + direction + ", radius=" + radius + ", entityName=" + entityName
				+ ", locTime=" + locTime + "]";
	}

	public Double getMileage() {
		return mileage;
	}

	public void setMileage(Double mileage) {
		this.mileage = mileage;
	}

}
