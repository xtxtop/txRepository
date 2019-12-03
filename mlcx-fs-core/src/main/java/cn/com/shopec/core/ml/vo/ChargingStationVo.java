package cn.com.shopec.core.ml.vo;

import java.util.Arrays;
import java.util.Date;

/**
 * @author 充电站VO
 *
 */
public class ChargingStationVo {
	// 充电站编号
	private String stationNo;
	// 充电站名称
	private String stationName;
	// （省）id
	private String provinceId;
	// （市/直辖市区县）id
	private String cityId;
	// （区县）id
	private String districtId;
	// 街道
	private String addrStreet;
	// 充电桩数
	private Integer chargingPileNumber;
	// 备注
	private String memo;
	// 充电站状态（0、停用，1、启用，默认0）
	private Integer isAvailable;
	// 充电站状态更新时间
	private Date availableUpdateTime;
	// 场站图片url
	private String stationUrl;
	// 是否删除（0、未删除，1、已删除，默认0）
	private Integer isDeleted;
	// 创建日期
	private Date createTime;
	// 更新日期
	private Date updateTime;
	// 操作人id
	private String operatorId;
	// 操作人类型（0、系统自动操作，1、平台人员操作）
	private Integer operatorType;
	// 坐标经度
	private String longitude;
	// 坐标纬度
	private String latitude;
	// 省
	private String provinceName;
	// 市
	private String cityName;
	// 区
	private String districtName;
	// 操作人
	private String userName;
	// 车位数
	private Integer parkingSpaceNumber;
	// 场站提供服务（各服务间用半角逗号间隔）
	private String supportedServices;
	// 是否公共开放（0，不开放、1，开放，默认1）
	private Integer isPublic;
	// 场站电费价格(元/度)
	private Double electricPrice;
	// 场站租金(元/月)
	private Double parkRent;
	// 营业时间
	private String businessHours;
	// 节假日 是否营业（1 是 0 否）
	private String isBusinessFestival;
	// 交流充电桩数量
	private String interflowSum;
	// 直流充电桩数量
	private String cocurrentSum;
	// 站类型
	private Integer stationType;
	// 标签
	private String label;
	// 两点之间距离
	private Double distance;
	// 标签数组
	private String[] labelArray;
	// 快充总数",
	private String chargeFastSum;
	// 快充空闲总数",
	private String chargeFastIdleSum;
	// 慢充总数",
	private String chargeTrickleSum;
	// 慢快充空闲总数",
	private String chargeTrickleIdleSum;
	// 运营城市
	private String operatingCityNo;
	// 运营城市
	private String operatingCityName;
	// 用户编码
	private String member;
	// 充电站图片
	private String fileUrl;

	public String getMember() {
		return member;
	}

	public void setMember(String member) {
		this.member = member;
	}

	public String getOperatingCityName() {
		return operatingCityName;
	}

	public void setOperatingCityName(String operatingCityName) {
		this.operatingCityName = operatingCityName;
	}

	public String getOperatingCityNo() {
		return operatingCityNo;
	}

	public void setOperatingCityNo(String operatingCityNo) {
		this.operatingCityNo = operatingCityNo;
	}

	public Double getDistance() {
		return distance;
	}

	public void setDistance(Double distance) {
		this.distance = distance;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public Integer getStationType() {
		return stationType;
	}

	public void setStationType(Integer stationType) {
		this.stationType = stationType;
	}

	public String getInterflowSum() {
		return interflowSum;
	}

	public void setInterflowSum(String interflowSum) {
		this.interflowSum = interflowSum;
	}

	public String getCocurrentSum() {
		return cocurrentSum;
	}

	public void setCocurrentSum(String cocurrentSum) {
		this.cocurrentSum = cocurrentSum;
	}

	public Integer getParkingSpaceNumber() {
		return parkingSpaceNumber;
	}

	public void setParkingSpaceNumber(Integer parkingSpaceNumber) {
		this.parkingSpaceNumber = parkingSpaceNumber;
	}

	public String getSupportedServices() {
		return supportedServices;
	}

	public void setSupportedServices(String supportedServices) {
		this.supportedServices = supportedServices;
	}

	public Integer getIsPublic() {
		return isPublic;
	}

	public void setIsPublic(Integer isPublic) {
		this.isPublic = isPublic;
	}

	public Double getElectricPrice() {
		return electricPrice;
	}

	public void setElectricPrice(Double electricPrice) {
		this.electricPrice = electricPrice;
	}

	public Double getParkRent() {
		return parkRent;
	}

	public void setParkRent(Double parkRent) {
		this.parkRent = parkRent;
	}

	public String getBusinessHours() {
		return businessHours;
	}

	public void setBusinessHours(String businessHours) {
		this.businessHours = businessHours;
	}

	public String getIsBusinessFestival() {
		return isBusinessFestival;
	}

	public void setIsBusinessFestival(String isBusinessFestival) {
		this.isBusinessFestival = isBusinessFestival;
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

	public String getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(String provinceId) {
		this.provinceId = provinceId;
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

	public String getAddrStreet() {
		return addrStreet;
	}

	public void setAddrStreet(String addrStreet) {
		this.addrStreet = addrStreet;
	}

	public Integer getChargingPileNumber() {
		return chargingPileNumber;
	}

	public void setChargingPileNumber(Integer chargingPileNumber) {
		this.chargingPileNumber = chargingPileNumber;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public Integer getIsAvailable() {
		return isAvailable;
	}

	public void setIsAvailable(Integer isAvailable) {
		this.isAvailable = isAvailable;
	}

	public Date getAvailableUpdateTime() {
		return availableUpdateTime;
	}

	public void setAvailableUpdateTime(Date availableUpdateTime) {
		this.availableUpdateTime = availableUpdateTime;
	}

	public String getStationUrl() {
		return stationUrl;
	}

	public void setStationUrl(String stationUrl) {
		this.stationUrl = stationUrl;
	}

	public Integer getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Integer isDeleted) {
		this.isDeleted = isDeleted;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(String operatorId) {
		this.operatorId = operatorId;
	}

	public Integer getOperatorType() {
		return operatorType;
	}

	public void setOperatorType(Integer operatorType) {
		this.operatorType = operatorType;
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

	public String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
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

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String[] getLabelArray() {
		return labelArray;
	}

	public void setLabelArray(String[] labelArray) {
		this.labelArray = labelArray;
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

	@Override
	public String toString() {
		return "ChargingStationVo [stationNo=" + stationNo + ", stationName=" + stationName + ", provinceId="
				+ provinceId + ", cityId=" + cityId + ", districtId=" + districtId + ", addrStreet=" + addrStreet
				+ ", chargingPileNumber=" + chargingPileNumber + ", memo=" + memo + ", isAvailable=" + isAvailable
				+ ", availableUpdateTime=" + availableUpdateTime + ", stationUrl=" + stationUrl + ", isDeleted="
				+ isDeleted + ", createTime=" + createTime + ", updateTime=" + updateTime + ", operatorId=" + operatorId
				+ ", operatorType=" + operatorType + ", longitude=" + longitude + ", latitude=" + latitude
				+ ", provinceName=" + provinceName + ", cityName=" + cityName + ", districtName=" + districtName
				+ ", userName=" + userName + ", parkingSpaceNumber=" + parkingSpaceNumber + ", supportedServices="
				+ supportedServices + ", isPublic=" + isPublic + ", electricPrice=" + electricPrice + ", parkRent="
				+ parkRent + ", businessHours=" + businessHours + ", isBusinessFestival=" + isBusinessFestival
				+ ", interflowSum=" + interflowSum + ", cocurrentSum=" + cocurrentSum + ", stationType=" + stationType
				+ ", label=" + label + ", distance=" + distance + ", labelArray=" + Arrays.toString(labelArray)
				+ ", chargeFastSum=" + chargeFastSum + ", chargeFastIdleSum=" + chargeFastIdleSum
				+ ", chargeTrickleSum=" + chargeTrickleSum + ", chargeTrickleIdleSum=" + chargeTrickleIdleSum
				+ ", operatingCityNo=" + operatingCityNo + ", operatingCityName=" + operatingCityName + "]";
	}

	public String getFileUrl() {
		return fileUrl;
	}

	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}

}
