package cn.com.shopec.core.dailyrental.model;

import java.util.Date;

import cn.com.shopec.core.common.Entity;

/**
 * 商家门店 数据实体类
 */
public class ParkDay extends Entity<String> {

	private static final long serialVersionUID = 1l;

	/* Auto generated properties start */

	// 门店id
	private String parkId;
	// 门店名称
	private String parkName;
	// 所在城市id
	private String cityId;
	// 所在城市名称
	private String cityName;
	// 一级行政区（省）id
	private String addrRegion1Id;
	// 一级行政区（省）名称
	private String addrRegion1Name;
	// 二级行政区（市/直辖市区县）id
	private String addrRegion2Id;
	// 二级行政区（市/直辖市区县）名称
	private String addrRegion2Name;
	// 三级行政区（区县）id
	private String addrRegion3Id;
	// 三级行政区（区县）名称
	private String addrRegion3Name;
	// 街道
	private String addrStreet;
	// 联系人
	private String cantactPerson;
	// 联系电话
	private String mobilePhone;
	// 门店坐标经度
	private Double longitude;
	// 门店坐标纬度
	private Double latitude;
	// 租赁商ID
	private String merchantId;
	// 租赁商名称
	private String merchantName;
	// 是否启用(0、停用1、启用、默认1)
	private Integer isVailable;
	// 车位数
	private Integer parkingSpaceNumber;
	// 所属方类型（1、自有，2、租用）
	private Integer ownerType;
	// 营业时间
	private String businessHours;
	// 电子围栏坐标
	private String polygonPoints;
	//门店图片
	private String parkDayPhoto;
	// 创建时间
	private Date createTime;
	// 创建时间 时间范围起（查询用）
	private Date createTimeStart;
	// 创建时间 时间范围止（查询用）
	private Date createTimeEnd;
	// 修改时间
	private Date updateTime;
	// 修改时间 时间范围起（查询用）
	private Date updateTimeStart;
	// 修改时间 时间范围止（查询用）
	private Date updateTimeEnd;
	// 操作人id
	private String operatorId;
	// 操作人类型
	private Integer operatorType;

	
	
	// 距离
	private Double distance;
	//地址全称
	private String fullAddr;
	/*Auto generated properties end*/
	
	
	
	/*Customized properties start*/
	
	/*Customized properties end*/
	
	
	
	/*Auto generated methods start*/
	

	@Override
	public String getPK() {
		return parkId;
	}

	public String getParkId() {
		return parkId;
	}

	public void setParkId(String parkId) {
		this.parkId = parkId;
	}

	public String getParkName() {
		return parkName;
	}

	public void setParkName(String parkName) {
		this.parkName = parkName;
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

	public String getAddrRegion1Id() {
		return addrRegion1Id;
	}

	public void setAddrRegion1Id(String addrRegion1Id) {
		this.addrRegion1Id = addrRegion1Id;
	}

	public String getAddrRegion1Name() {
		return addrRegion1Name;
	}

	public void setAddrRegion1Name(String addrRegion1Name) {
		this.addrRegion1Name = addrRegion1Name;
	}

	public String getAddrRegion2Id() {
		return addrRegion2Id;
	}

	public void setAddrRegion2Id(String addrRegion2Id) {
		this.addrRegion2Id = addrRegion2Id;
	}

	public String getAddrRegion2Name() {
		return addrRegion2Name;
	}

	public void setAddrRegion2Name(String addrRegion2Name) {
		this.addrRegion2Name = addrRegion2Name;
	}

	public String getAddrRegion3Id() {
		return addrRegion3Id;
	}

	public void setAddrRegion3Id(String addrRegion3Id) {
		this.addrRegion3Id = addrRegion3Id;
	}

	public String getAddrRegion3Name() {
		return addrRegion3Name;
	}

	public void setAddrRegion3Name(String addrRegion3Name) {
		this.addrRegion3Name = addrRegion3Name;
	}

	public String getAddrStreet() {
		return addrStreet;
	}

	public void setAddrStreet(String addrStreet) {
		this.addrStreet = addrStreet;
	}

	public String getCantactPerson() {
		return cantactPerson;
	}

	public void setCantactPerson(String cantactPerson) {
		this.cantactPerson = cantactPerson;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
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

	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public Integer getIsVailable() {
		return isVailable;
	}

	public void setIsVailable(Integer isVailable) {
		this.isVailable = isVailable;
	}

	public Integer getParkingSpaceNumber() {
		return parkingSpaceNumber;
	}

	public void setParkingSpaceNumber(Integer parkingSpaceNumber) {
		this.parkingSpaceNumber = parkingSpaceNumber;
	}

	public Integer getOwnerType() {
		return ownerType;
	}

	public void setOwnerType(Integer ownerType) {
		this.ownerType = ownerType;
	}

	public String getBusinessHours() {
		return businessHours;
	}

	public void setBusinessHours(String businessHours) {
		this.businessHours = businessHours;
	}

	public String getPolygonPoints() {
		return polygonPoints;
	}

	public void setPolygonPoints(String polygonPoints) {
		this.polygonPoints = polygonPoints;
	}

	public String getParkDayPhoto() {
		return parkDayPhoto;
	}

	public void setParkDayPhoto(String parkDayPhoto) {
		this.parkDayPhoto = parkDayPhoto;
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

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Date getUpdateTimeStart() {
		return updateTimeStart;
	}

	public void setUpdateTimeStart(Date updateTimeStart) {
		this.updateTimeStart = updateTimeStart;
	}

	public Date getUpdateTimeEnd() {
		return updateTimeEnd;
	}

	public void setUpdateTimeEnd(Date updateTimeEnd) {
		this.updateTimeEnd = updateTimeEnd;
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

	/* Auto generated methods end */

	/* Customized methods start */

	/* Customized methods end */

	@Override
	public String toString() {
		return "ParkDay [" + "parkId = " + parkId + ", parkName = " + parkName + ", cityId = " + cityId
				+ ", addrRegion1Id = " + addrRegion1Id + ", addrRegion1Name = " + addrRegion1Name + ", addrRegion2Id = "
				+ addrRegion2Id + ", addrRegion2Name = " + addrRegion2Name + ", addrRegion3Id = " + addrRegion3Id
				+ ", addrRegion3Name = " + addrRegion3Name + ", addrStreet = " + addrStreet + ", longitude = "
				+ longitude + ", latitude = " + latitude + ", merchantId = " + merchantId + ", isVailable = "
				+ isVailable + ", parkingSpaceNumber = " + parkingSpaceNumber + ", ownerType = " + ownerType
				+ ", businessHours = " + businessHours + ", polygonPoints = " + polygonPoints + ", createTime = "
				+ createTime + ", createTimeStart = " + createTimeStart + ", createTimeEnd = " + createTimeEnd
				+ ", updateTime = " + updateTime + ", updateTimeStart = " + updateTimeStart + ", updateTimeEnd = "
				+ updateTimeEnd + ", operatorId = " + operatorId + ", operatorType = " + operatorType + "]";
	}
	public Double getDistance() {
		return distance;
	}

	public void setDistance(Double distance) {
		this.distance = distance;
	}

	public String getFullAddr() {
		return fullAddr;
	}

	public void setFullAddr(String fullAddr) {
		this.fullAddr = fullAddr;
	}
	
}
