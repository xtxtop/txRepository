package cn.com.shopec.core.resource.model;

import java.util.Date;

import cn.com.shopec.core.common.Entity;

/**
 * Park 数据实体类
 */
public class Park extends Entity<String> {

	private static final long serialVersionUID = 1l;

	/* Auto generated properties start */

	// 场站编号
	private String parkNo;
	// 场站名称
	private String parkName;
	// 所属片区（具体值来自字典表）
	private String regionId;
	// 片区名称
	private String regionName;
	// 场站类型（1、管理类，2、使用类，默认1）
	private Integer parkType;
	// 城市（具体值来自字典表）
	private String cityId;
	// 城市名称
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
	// 坐标经度 圆形场站为中心经度，多边形场站为几何中心经度
	private String longitude;
	// 坐标纬度 圆形场站为中心纬度，多边形场站为几何中心纬度
	private String latitude;
	// 多边形多个点
	private String ploygonPoints;
	// 电子围栏半径 圆形场站存，多边形不存此值
	private Integer electronicFenceRadius;
	// 车位数
	private Integer parkingSpaceNumber;
	// 电桩数
	private Integer chargerNumber;
	// 场站提供服务（各服务间用半角逗号间隔）
	private String supportedServices;
	// 所属方类型（1、自有，2、租用）
	private Integer ownerType;
	// 是否公共开放（0，不开放、1，开放，默认1）
	private Integer isPublic;
	// 样式id（具体来自字典表）
	private String styleId;
	// 样式（具体来自字典表）
	private String style;
	// 是否可见
	private Integer isView;
	// 场站状态（0、停用，1、启用，默认0）
	private Integer isAvailable;
	// 停用，启用 备注
	private String memo;
	// 场站状态更新时间
	private Date availableUpdateTime;
	// 场站状态更新时间 时间范围起（查询用）
	private Date availableUpdateTimeStart;
	// 场站状态更新时间 时间范围止（查询用）
	private Date availableUpdateTimeEnd;
	// 场站图片url1
	private String parkPicUrl1;
	// 场站图片url2
	private String parkPicUrl2;
	// 场站图片url3
	private String parkPicUrl3;
	// 场站图片url4
	private String parkPicUrl4;
	// 场站图片url5
	private String parkPicUrl5;
	// 场站电费价格(元/度)
	private Double electricPrice;
	// 场站租金(元/月)
	private Double parkRent;
	// 交租日期(每月x日)
	private Integer payRentDayOfMonth;
	// 是否删除（0、未删除，1、已删除，默认0）
	private Integer isDeleted;
	// 创建日期
	private Date createTime;
	// 创建日期 时间范围起（查询用）
	private Date createTimeStart;
	// 创建日期 时间范围止（查询用）
	private Date createTimeEnd;
	// 更新日期
	private Date updateTime;
	// 更新日期 时间范围起（查询用）
	private Date updateTimeStart;
	// 更新日期 时间范围止（查询用）
	private Date updateTimeEnd;
	// 操作人id
	private String operatorId;
	// 操作人类型（0、系统自动操作，1、平台人员操作，2、商家人员操作，3、会员操作）
	private Integer operatorType;

	// 可用车辆数（普通会员，集团会员）
	private Integer availableCarNum;
	// 会员编号
	private String memberNo;
	/* Auto generated properties end */
	// 车位数
	private Integer parkingSpaces;
	// 车辆数
	private Integer carNumber;
	// 详细地址
	private String addressAll;

	// 车辆距场站的距离
	private Double distance;

	// 场站所属集团
	private String companyIds;

	// 用于查询
	private String companyId;
	//调度员所属片区编号
		private String wkRegionId;

	// 营业时间
	private String businessHours;
	// 节假日 是否营业（1 是 0 否）
	private Integer isBusinessFestival;
	// 取车附加费
	private Double serviceFeeGet;
	// 还车附件费
	private Double serviceFeeBack;

	// 调度员是否在网点（查询数据）
	private Integer isDot;
	// 调度查询车辆类型 1：停靠车辆 ，2：低电 ，3 调度任务
	private Integer getCarType;
	// 调度电量查询
	private Double powerBig;	

	// 是否车位不足（1.是 0.否 ）
	private Integer isLotParkingSpace;
	
	//关联加盟商编号
	private String franchiseeNo;
	
	// 低电车辆数
	private Integer lowPowerCarNumber;
	//是否有红包车辆的场站(0、否 1、是)
	private String isRedPakcetPark;
	//是否是红包车目标场站（0、否 1、是）
	private String isRedPacketTargetPark;
	/* Customized properties start */

	/* Customized properties end */

	/* Auto generated methods start */

	@Override
	public String getPK() {
		return parkNo;
	}

	
	public String getFranchiseeNo() {
		return franchiseeNo;
	}


	public void setFranchiseeNo(String franchiseeNo) {
		this.franchiseeNo = franchiseeNo;
	}


	public String getAddressAll() {
		return addressAll;
	}

	public void setAddressAll(String addressAll) {
		this.addressAll = addressAll;
	}

	public String getStyleId() {
		return styleId;
	}

	public Integer getCarNumber() {
		return carNumber;
	}

	public void setCarNumber(Integer carNumber) {
		this.carNumber = carNumber;
	}

	public Integer getParkingSpaces() {
		return parkingSpaces;
	}

	public void setParkingSpaces(Integer parkingSpaces) {
		this.parkingSpaces = parkingSpaces;
	}

	public void setStyleId(String styleId) {
		this.styleId = styleId;
	}

	public Integer getIsView() {
		return isView;
	}

	public void setIsView(Integer isView) {
		this.isView = isView;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
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

	public Integer getParkType() {
		return parkType;
	}

	public void setParkType(Integer parkType) {
		this.parkType = parkType;
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

	public String getPloygonPoints() {
		return ploygonPoints;
	}

	public void setPloygonPoints(String ploygonPoints) {
		this.ploygonPoints = ploygonPoints;
	}

	public Integer getElectronicFenceRadius() {
		return electronicFenceRadius;
	}

	public void setElectronicFenceRadius(Integer electronicFenceRadius) {
		this.electronicFenceRadius = electronicFenceRadius;
	}

	public Integer getParkingSpaceNumber() {
		return parkingSpaceNumber;
	}

	public void setParkingSpaceNumber(Integer parkingSpaceNumber) {
		this.parkingSpaceNumber = parkingSpaceNumber;
	}

	public Integer getChargerNumber() {
		return chargerNumber;
	}

	public void setChargerNumber(Integer chargerNumber) {
		this.chargerNumber = chargerNumber;
	}

	public String getSupportedServices() {
		return supportedServices;
	}

	public void setSupportedServices(String supportedServices) {
		this.supportedServices = supportedServices;
	}

	public Integer getOwnerType() {
		return ownerType;
	}

	public void setOwnerType(Integer ownerType) {
		this.ownerType = ownerType;
	}

	public Integer getIsPublic() {
		return isPublic;
	}

	public void setIsPublic(Integer isPublic) {
		this.isPublic = isPublic;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
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

	public Date getAvailableUpdateTimeStart() {
		return availableUpdateTimeStart;
	}

	public void setAvailableUpdateTimeStart(Date availableUpdateTimeStart) {
		this.availableUpdateTimeStart = availableUpdateTimeStart;
	}

	public Date getAvailableUpdateTimeEnd() {
		return availableUpdateTimeEnd;
	}

	public void setAvailableUpdateTimeEnd(Date availableUpdateTimeEnd) {
		this.availableUpdateTimeEnd = availableUpdateTimeEnd;
	}

	public String getParkPicUrl1() {
		return parkPicUrl1;
	}

	public void setParkPicUrl1(String parkPicUrl1) {
		this.parkPicUrl1 = parkPicUrl1;
	}

	public String getParkPicUrl2() {
		return parkPicUrl2;
	}

	public void setParkPicUrl2(String parkPicUrl2) {
		this.parkPicUrl2 = parkPicUrl2;
	}

	public String getParkPicUrl3() {
		return parkPicUrl3;
	}

	public void setParkPicUrl3(String parkPicUrl3) {
		this.parkPicUrl3 = parkPicUrl3;
	}

	public String getParkPicUrl4() {
		return parkPicUrl4;
	}

	public void setParkPicUrl4(String parkPicUrl4) {
		this.parkPicUrl4 = parkPicUrl4;
	}

	public String getParkPicUrl5() {
		return parkPicUrl5;
	}

	public void setParkPicUrl5(String parkPicUrl5) {
		this.parkPicUrl5 = parkPicUrl5;
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

	public Integer getPayRentDayOfMonth() {
		return payRentDayOfMonth;
	}

	public void setPayRentDayOfMonth(Integer payRentDayOfMonth) {
		this.payRentDayOfMonth = payRentDayOfMonth;
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

	public String getCompanyIds() {
		return companyIds;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public void setCompanyIds(String companyIds) {
		this.companyIds = companyIds;
	}

	@Override
	public String toString() {
		return "Park [" + "parkNo = " + parkNo + ", parkName = " + parkName + ", parkType = " + parkType + ", cityId = "
				+ cityId + ", cityName = " + cityName + ", addrRegion1Id = " + addrRegion1Id + ", addrRegion1Name = "
				+ addrRegion1Name + ", addrRegion2Id = " + addrRegion2Id + ", addrRegion2Name = " + addrRegion2Name
				+ ", addrRegion3Id = " + addrRegion3Id + ", addrRegion3Name = " + addrRegion3Name + ", addrStreet = "
				+ addrStreet + ", longitude = " + longitude + ", latitude = " + latitude + ", electronicFenceRadius = "
				+ electronicFenceRadius + ", parkingSpaceNumber = " + parkingSpaceNumber + ", chargerNumber = "
				+ chargerNumber + ", supportedServices = " + supportedServices + ", ownerType = " + ownerType
				+ ", isPublic = " + isPublic + ", style = " + style + ", isAvailable = " + isAvailable
				+ ", availableUpdateTime = " + availableUpdateTime + ", availableUpdateTimeStart = "
				+ availableUpdateTimeStart + ", availableUpdateTimeEnd = " + availableUpdateTimeEnd + ", parkPicUrl1 = "
				+ parkPicUrl1 + ", parkPicUrl2 = " + parkPicUrl2 + ", parkPicUrl3 = " + parkPicUrl3 + ", parkPicUrl4 = "
				+ parkPicUrl4 + ", parkPicUrl5 = " + parkPicUrl5 + ", electricPrice = " + electricPrice
				+ ", parkRent = " + parkRent + ", payRentDayOfMonth = " + payRentDayOfMonth + ", isDeleted = "
				+ isDeleted + ", createTime = " + createTime + ", createTimeStart = " + createTimeStart
				+ ", createTimeEnd = " + createTimeEnd + ", updateTime = " + updateTime + ", updateTimeStart = "
				+ updateTimeStart + ", updateTimeEnd = " + updateTimeEnd + ", operatorId = " + operatorId
				+ ", operatorType = " + operatorType + "]";
	}

	public Double getDistance() {
		return distance;
	}

	public void setDistance(Double distance) {
		this.distance = distance;
	}

	public String getMemberNo() {
		return memberNo;
	}

	public void setMemberNo(String memberNo) {
		this.memberNo = memberNo;
	}

	public Integer getAvailableCarNum() {
		return availableCarNum;
	}

	public void setAvailableCarNum(Integer availableCarNum) {
		this.availableCarNum = availableCarNum;
	}

	public String getBusinessHours() {
		return businessHours;
	}

	public void setBusinessHours(String businessHours) {
		this.businessHours = businessHours;
	}

	public Integer getIsBusinessFestival() {
		return isBusinessFestival;
	}

	public void setIsBusinessFestival(Integer isBusinessFestival) {
		this.isBusinessFestival = isBusinessFestival;
	}

	public Double getServiceFeeGet() {
		return serviceFeeGet;
	}

	public void setServiceFeeGet(Double serviceFeeGet) {
		this.serviceFeeGet = serviceFeeGet;
	}

	public Double getServiceFeeBack() {
		return serviceFeeBack;
	}

	public void setServiceFeeBack(Double serviceFeeBack) {
		this.serviceFeeBack = serviceFeeBack;
	}

	public Integer getIsDot() {
		return isDot;
	}

	public void setIsDot(Integer isDot) {
		this.isDot = isDot;
	}

	public String getRegionId() {
		return regionId;
	}

	public void setRegionId(String regionId) {
		this.regionId = regionId;
	}

	public String getRegionName() {
		return regionName;
	}

	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}

	public Integer getGetCarType() {
		return getCarType;
	}

	public void setGetCarType(Integer getCarType) {
		this.getCarType = getCarType;
	}

	public Double getPowerBig() {
		return powerBig;
	}

	public void setPowerBig(Double powerBig) {
		this.powerBig = powerBig;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Integer getIsLotParkingSpace() {
		return isLotParkingSpace;
	}

	public void setIsLotParkingSpace(Integer isLotParkingSpace) {
		this.isLotParkingSpace = isLotParkingSpace;
	}

	public String getWkRegionId() {
		return wkRegionId;
	}

	public void setWkRegionId(String wkRegionId) {
		this.wkRegionId = wkRegionId;
	}


	public Integer getLowPowerCarNumber() {
		return lowPowerCarNumber;
	}


	public void setLowPowerCarNumber(Integer lowPowerCarNumber) {
		this.lowPowerCarNumber = lowPowerCarNumber;
	}


	public String getIsRedPakcetPark() {
		return isRedPakcetPark;
	}


	public void setIsRedPakcetPark(String isRedPakcetPark) {
		this.isRedPakcetPark = isRedPakcetPark;
	}


	public String getIsRedPacketTargetPark() {
		return isRedPacketTargetPark;
	}


	public void setIsRedPacketTargetPark(String isRedPacketTargetPark) {
		this.isRedPacketTargetPark = isRedPacketTargetPark;
	}

}
