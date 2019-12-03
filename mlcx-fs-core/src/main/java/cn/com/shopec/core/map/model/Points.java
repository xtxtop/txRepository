package cn.com.shopec.core.map.model;

import java.io.Serializable;
import java.util.List;

import cn.com.shopec.core.map.common.SelfDefileColumnKey;

/**
 * 操作entity属性对象的封装
 *
 */
public class Points extends SelfDefileColumnKey implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5086267241696402364L;
	// location 经纬度 Array
	private List<Double> location;
	// loc_time 该track实时点的上传时间 ,UNIX时间戳
	private Long loc_time;
	// create_time 创建时间 ,格式化时间 ,该时间为服务端时间
	private String create_time;
	// direction 方向
	private Integer direction;
	// speed 速度 单位：km/h
	private Double speed;
	// radius 定位精度 double 单位：m
	private Double radius;
	// 剩余油/电量
	private Double power;
	// 总里程
	private Double mileage;
	// 车辆打火状态
	private Integer carStatus;
	// 车辆使用状态（0，空闲、1，已预占、2，订单中、3，调度中）
	private Integer carUserageStatus;
	// 车辆使用人
	private String memberName;
	// 使用人手机号
	private String phone;
	// 单据号(订单或者调度单号)
	private String documentNo;
	// 位置(中文)
	private String address;

	public List<Double> getLocation() {
		return location;
	}

	public void setLocation(List<Double> location) {
		this.location = location;
	}

	public Long getLoc_time() {
		return loc_time;
	}

	public void setLoc_time(Long loc_time) {
		this.loc_time = loc_time;
	}

	public String getCreate_time() {
		return create_time;
	}

	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}

	public Integer getDirection() {
		return direction;
	}

	public void setDirection(Integer direction) {
		this.direction = direction;
	}

	public Double getSpeed() {
		return speed;
	}

	public void setSpeed(Double speed) {
		this.speed = speed;
	}

	public Double getRadius() {
		return radius;
	}

	public void setRadius(Double radius) {
		this.radius = radius;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "Points [location=" + location + ", loc_time=" + loc_time + ", create_time=" + create_time
				+ ", direction=" + direction + ", speed=" + speed + ", radius=" + radius + "]";
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Double getPower() {
		return power;
	}

	public void setPower(Double power) {
		this.power = power;
	}

	public Double getMileage() {
		return mileage;
	}

	public void setMileage(Double mileage) {
		this.mileage = mileage;
	}

}
