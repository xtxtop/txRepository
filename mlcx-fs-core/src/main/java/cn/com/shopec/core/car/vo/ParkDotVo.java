package cn.com.shopec.core.car.vo;

public class ParkDotVo {
	// 场站编号
	private String parkNo;
	// 场站名称
	private String parkName;
	// 车位数
	private String parkingSpaceNumber;
	// 电桩数
	private String chargerNumber;
	// 街道
	private String addrStreet;
	// 距离
	private String distance;
	// 停靠车辆数
	private String stopCar;
	// 调度车辆数
	private String dispatchCar;
	// 低电车辆数
	private String lowPowerCar;

	public String getParkNo() {
		return parkNo;
	}

	public void setParkNo(String parkNo) {
		this.parkNo = parkNo;
	}

	public String getParkName() {
		return parkName;
	}

	public void setParkName(String parkName) {
		this.parkName = parkName;
	}

	public String getParkingSpaceNumber() {
		return parkingSpaceNumber;
	}

	public void setParkingSpaceNumber(String parkingSpaceNumber) {
		this.parkingSpaceNumber = parkingSpaceNumber;
	}

	public String getChargerNumber() {
		return chargerNumber;
	}

	public void setChargerNumber(String chargerNumber) {
		this.chargerNumber = chargerNumber;
	}

	public String getAddrStreet() {
		return addrStreet;
	}

	public void setAddrStreet(String addrStreet) {
		this.addrStreet = addrStreet;
	}

	public String getDistance() {
		return distance;
	}

	public void setDistance(String distance) {
		this.distance = distance;
	}

	public String getStopCar() {
		return stopCar;
	}

	public void setStopCar(String stopCar) {
		this.stopCar = stopCar;
	}

	public String getDispatchCar() {
		return dispatchCar;
	}

	public void setDispatchCar(String dispatchCar) {
		this.dispatchCar = dispatchCar;
	}

	public String getLowPowerCar() {
		return lowPowerCar;
	}

	public void setLowPowerCar(String lowPowerCar) {
		this.lowPowerCar = lowPowerCar;
	}

}
