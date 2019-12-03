package cn.com.shopec.mapi.station.vo;

import java.util.List;

/**
 * 充电站模型
 * 
 * @author Administrator
 *
 */
public class ChargingStationVo {

	// 充电站编号
	private String stationNo;
	// 充电站名称
	private String stationName;
	// 场站电费价格(元/度)
	private String electricPrice;
	// 场站图片url
	private String stationUrl;
	// 充电站状态（0、停用，1、启用，默认1）
	private String isAvailable;
	// 坐标经度
	private String longitude;
	// 坐标纬度
	private String latitude;
	// 周围设施集合
	private List<CMatchingVo> matchList;
	// （市/直辖市区县）id
	private String cityId;
	// （区县）id
	private String districtId;
	// 市
	private String cityName;
	// 区
	private String districtName;
	// 两点之间的距离
	private String distance;
	// 快充总数"
	private String chargeFastSum;
	// 快充空闲总数"
	private String chargeFastIdleSum;
	// 慢充总数"
	private String chargeTrickleSum;
	// 慢快充空闲总数"
	private String chargeTrickleIdleSum;

	public String getDistance() {
		return distance;
	}

	public void setDistance(String distance) {
		this.distance = distance;
	}

	public String getStationNo() {
		return stationNo;
	}

	public void setStationNo(String stationNo) {
		this.stationNo = stationNo;
	}

	public String getStationName() {
		return stationName;
	}

	public void setStationName(String stationName) {
		this.stationName = stationName;
	}

	public String getElectricPrice() {
		return electricPrice;
	}

	public void setElectricPrice(String electricPrice) {
		this.electricPrice = electricPrice;
	}

	public String getStationUrl() {
		return stationUrl;
	}

	public void setStationUrl(String stationUrl) {
		this.stationUrl = stationUrl;
	}

	public String getIsAvailable() {
		return isAvailable;
	}

	public void setIsAvailable(String isAvailable) {
		this.isAvailable = isAvailable;
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

	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	public String getDistrictId() {
		return districtId;
	}

	public void setDistrictId(String districtId) {
		this.districtId = districtId;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getDistrictName() {
		return districtName;
	}

	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}

	public List<CMatchingVo> getMatchList() {
		return matchList;
	}

	public void setMatchList(List<CMatchingVo> matchList) {
		this.matchList = matchList;
	}

	public String getChargeFastSum() {
		return chargeFastSum;
	}

	public void setChargeFastSum(String chargeFastSum) {
		this.chargeFastSum = chargeFastSum;
	}

	public String getChargeFastIdleSum() {
		return chargeFastIdleSum;
	}

	public void setChargeFastIdleSum(String chargeFastIdleSum) {
		this.chargeFastIdleSum = chargeFastIdleSum;
	}

	public String getChargeTrickleSum() {
		return chargeTrickleSum;
	}

	public void setChargeTrickleSum(String chargeTrickleSum) {
		this.chargeTrickleSum = chargeTrickleSum;
	}

	public String getChargeTrickleIdleSum() {
		return chargeTrickleIdleSum;
	}

	public void setChargeTrickleIdleSum(String chargeTrickleIdleSum) {
		this.chargeTrickleIdleSum = chargeTrickleIdleSum;
	}

}
