package cn.com.shopec.core.resource.model;

import cn.com.shopec.core.common.Entity;

/**
 * Park 数据实体类（用于还车时获取场站信息）
 */
public class ParkLocationNs extends Entity<String> {

	private static final long serialVersionUID = 1l;

	/* Auto generated properties start */

	// 场站编号
	private String parkNo;
	// 场站名称
	private String parkName;
	// 距离
	private Double distance;
	// 城市（具体值来自字典表）
	private String cityId;
	// 城市名称
	private String cityName;
	/* Customized properties start */

	/* Customized properties end */

	/* Auto generated methods start */

	@Override
	public String getPK() {
		return parkNo;
	}

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

	public Double getDistance() {
		return distance;
	}

	public void setDistance(Double distance) {
		this.distance = distance;
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

}
