package cn.com.shopec.core.resource.model;

public class ParkDayVo {
	// 场站编号
	private String parkNo;
	// 场站名称
	private String parkName;
	// 距离
	private Double distance;

	public String getParkName() {
		return parkName;
	}

	public void setParkName(String parkName) {
		this.parkName = parkName;
	}

	public Double getDistance() {
		return distance;
	}

	public void setDistance(Double distance) {
		this.distance = distance;
	}

	public String getParkNo() {
		return parkNo;
	}

	public void setParkNo(String parkNo) {
		this.parkNo = parkNo;
	}

}
