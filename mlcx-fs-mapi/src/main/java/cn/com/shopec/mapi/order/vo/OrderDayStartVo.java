package cn.com.shopec.mapi.order.vo;

import java.util.List;

import cn.com.shopec.core.system.model.DataDictItem;

public class OrderDayStartVo {
	// 异地还车费
	private Double taxiFare;
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
	// 城市
	private List<DataDictItem> cities;

	public Double getTaxiFare() {
		return taxiFare;
	}

	public void setTaxiFare(Double taxiFare) {
		this.taxiFare = taxiFare;
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

	public List<DataDictItem> getCities() {
		return cities;
	}

	public void setCities(List<DataDictItem> cities) {
		this.cities = cities;
	}

}
