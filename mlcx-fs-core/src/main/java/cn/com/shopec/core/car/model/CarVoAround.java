package cn.com.shopec.core.car.model;

import java.io.Serializable;

import cn.com.shopec.core.marketing.model.CarRedPacketVo;

public class CarVoAround implements Serializable {
	/**
	 *
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	 */
	private static final long serialVersionUID = 1L;

	// 车辆编号
	private String carNo;
	// 车牌号
	private String carPlateNo;
	// 车型名称
	private String carModelName;
	// 坐标经度
	private String longitude;
	// 坐标纬度
	private String latitude;
	// 地址
	private String address;
	// 距离
	private Double distance;
	// 车辆剩余电量
	private Double power;
	//是否有红包车辆的场站(0、否 1、是)
	private String isCarRedPakcet;
	private CarRedPacketVo  carRedPacketVo;

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

	public String getCarModelName() {
		return carModelName;
	}

	public void setCarModelName(String carModelName) {
		this.carModelName = carModelName;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Double getDistance() {
		return distance;
	}

	public void setDistance(Double distance) {
		this.distance = distance;
	}

	public Double getPower() {
		return power;
	}

	public void setPower(Double power) {
		this.power = power;
	}

	public String getIsCarRedPakcet() {
		return isCarRedPakcet;
	}

	public void setIsCarRedPakcet(String isCarRedPakcet) {
		this.isCarRedPakcet = isCarRedPakcet;
	}

	public CarRedPacketVo getCarRedPacketVo() {
		return carRedPacketVo;
	}

	public void setCarRedPacketVo(CarRedPacketVo carRedPacketVo) {
		this.carRedPacketVo = carRedPacketVo;
	}

}
