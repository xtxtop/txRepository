package cn.com.shopec.core.mlparking.vo;

import java.util.List;

import cn.com.shopec.core.ml.vo.AdvertTextVo;
import cn.com.shopec.core.ml.vo.BannerVo;
import cn.com.shopec.core.mlparking.model.CPliesNumber;

/**
 * app端停车场详情模型
 * 
 * @author Administrator
 *
 */
public class CParkingDetailVo {
	// 停车场编号
	private String parkingNo;
	// 停车场名称
	private String parkingName;
	//电话
	private String phone;
	// 楼层停车场总车位个数
	private Integer parkingTotalNumber;
	// 楼层停车场空闲车位数
	private Integer parkingSpaceNumber;
	// 距离
	private String distantce;
	// 评分[0-5]
	private String star;
	// 经度
	private String longitude;
	// 纬度
	private String latitude;
	// 开放时间/营业时间(00:00-24:00)
	private String businessHours;
	// 顶部广告图
	private List<BannerVo> topBanner;
	//详情图片
	private String  detailsUrl;
	// 计费规则
	private String parkBillingName;
	// 停车场类型
	private Integer parkingType;
	// 停车场状态
	private String parkingStatus;
	// 楼层数
	private String pliesNumber;
	// 运营城市编号
	private String operatingCityNo;
	// 运营城市名称
	private String operatingCityName;
	// 停车场详情顶部系统参数
	private List<AdvertTextVo> sysParamList;
	// 停车场车位分布
	private List<SpaceNumberList> parkSpaceList;
	// 免费时间
	private String freeTime;
	// 每小时费用
	private String hoursCost;
	// 一天封顶费用
	private String dayMaxCost;
	// 地下停车场楼层数
	private Integer undergroundNumber;
	// 地下总车位
	private Integer undergroundParkingSpaceNumber;
	// 地下剩余车位
	private Integer undergroundSurplusSpaceNumber;
	// 地面停车场楼层数
	private Integer groundNumber;
	// 地面总车位
	private Integer groundParkingSpaceNumber;
	// 地面剩余车位
	private Integer groundSurplusSpaceNumber;
	// 会员编号
	private String memberNo;

	public String getDetailsUrl() {
		return detailsUrl;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public void setDetailsUrl(String detailsUrl) {
		this.detailsUrl = detailsUrl;
	}

	public String getFreeTime() {
		return freeTime;
	}

	public void setFreeTime(String freeTime) {
		this.freeTime = freeTime;
	}

	public String getHoursCost() {
		return hoursCost;
	}

	public void setHoursCost(String hoursCost) {
		this.hoursCost = hoursCost;
	}

	public String getDayMaxCost() {
		return dayMaxCost;
	}

	public void setDayMaxCost(String dayMaxCost) {
		this.dayMaxCost = dayMaxCost;
	}

	public Integer getUndergroundNumber() {
		return undergroundNumber;
	}

	public void setUndergroundNumber(Integer undergroundNumber) {
		this.undergroundNumber = undergroundNumber;
	}

	public Integer getUndergroundParkingSpaceNumber() {
		return undergroundParkingSpaceNumber;
	}

	public void setUndergroundParkingSpaceNumber(
			Integer undergroundParkingSpaceNumber) {
		this.undergroundParkingSpaceNumber = undergroundParkingSpaceNumber;
	}

	public Integer getUndergroundSurplusSpaceNumber() {
		return undergroundSurplusSpaceNumber;
	}

	public void setUndergroundSurplusSpaceNumber(
			Integer undergroundSurplusSpaceNumber) {
		this.undergroundSurplusSpaceNumber = undergroundSurplusSpaceNumber;
	}

	public Integer getGroundNumber() {
		return groundNumber;
	}

	public void setGroundNumber(Integer groundNumber) {
		this.groundNumber = groundNumber;
	}

	public Integer getGroundParkingSpaceNumber() {
		return groundParkingSpaceNumber;
	}

	public void setGroundParkingSpaceNumber(Integer groundParkingSpaceNumber) {
		this.groundParkingSpaceNumber = groundParkingSpaceNumber;
	}

	public Integer getGroundSurplusSpaceNumber() {
		return groundSurplusSpaceNumber;
	}

	public void setGroundSurplusSpaceNumber(Integer groundSurplusSpaceNumber) {
		this.groundSurplusSpaceNumber = groundSurplusSpaceNumber;
	}


	public String getParkingNo() {
		return parkingNo;
	}

	public void setParkingNo(String parkingNo) {
		this.parkingNo = parkingNo;
	}

	public String getParkingName() {
		return parkingName;
	}

	public void setParkingName(String parkingName) {
		this.parkingName = parkingName;
	}

	public String getDistantce() {
		return distantce;
	}

	public void setDistantce(String distantce) {
		this.distantce = distantce;
	}

	public String getStar() {
		return star;
	}


	public List<SpaceNumberList> getParkSpaceList() {
		return parkSpaceList;
	}

	public void setParkSpaceList(List<SpaceNumberList> parkSpaceList) {
		this.parkSpaceList = parkSpaceList;
	}

	public void setStar(String star) {
		this.star = star;
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

	public String getBusinessHours() {
		return businessHours;
	}

	public void setBusinessHours(String businessHours) {
		this.businessHours = businessHours;
	}

	public List<BannerVo> getTopBanner() {
		return topBanner;
	}

	public void setTopBanner(List<BannerVo> topBanner) {
		this.topBanner = topBanner;
	}

	public String getParkBillingName() {
		return parkBillingName;
	}

	public void setParkBillingName(String parkBillingName) {
		this.parkBillingName = parkBillingName;
	}

	public Integer getParkingType() {
		return parkingType;
	}

	public void setParkingType(Integer parkingType) {
		this.parkingType = parkingType;
	}

	public String getParkingStatus() {
		return parkingStatus;
	}

	public void setParkingStatus(String parkingStatus) {
		this.parkingStatus = parkingStatus;
	}

	public String getPliesNumber() {
		return pliesNumber;
	}

	public void setPliesNumber(String pliesNumber) {
		this.pliesNumber = pliesNumber;
	}

	public String getOperatingCityNo() {
		return operatingCityNo;
	}

	public void setOperatingCityNo(String operatingCityNo) {
		this.operatingCityNo = operatingCityNo;
	}

	public String getOperatingCityName() {
		return operatingCityName;
	}

	public void setOperatingCityName(String operatingCityName) {
		this.operatingCityName = operatingCityName;
	}

	public List<AdvertTextVo> getSysParamList() {
		return sysParamList;
	}

	public void setSysParamList(List<AdvertTextVo> sysParamList) {
		this.sysParamList = sysParamList;
	}

	public String getMemberNo() {
		return memberNo;
	}

	public void setMemberNo(String memberNo) {
		this.memberNo = memberNo;
	}

	public Integer getParkingTotalNumber() {
		return parkingTotalNumber;
	}

	public void setParkingTotalNumber(Integer parkingTotalNumber) {
		this.parkingTotalNumber = parkingTotalNumber;
	}

	public Integer getParkingSpaceNumber() {
		return parkingSpaceNumber;
	}

	public void setParkingSpaceNumber(Integer parkingSpaceNumber) {
		this.parkingSpaceNumber = parkingSpaceNumber;
	}

}
