package cn.com.shopec.core.mlparking.vo;

import java.util.List;

import cn.com.shopec.core.ml.vo.AdvertTextVo;
import cn.com.shopec.core.ml.vo.BannerVo;

/**
 * app端停车场详情模型two
 * 
 * @author daiyuanbao
 *
 */
public class CParkingDetailVoTwo {
	// 停车场编号
	private String parkingNo;
	// 停车场名称
	private String parkingName;
	// 电话
	private String phone;
	// 评分[0-5]
	private String star;
	// 开放时间/营业时间(00:00-24:00)
	//private String businessHours;
	// 停车场类型
	private Integer parkingType;
	// 停车场状态
	//private String parkingStatus;
	// 运营城市编号
	//private String operatingCityNo;
	// 运营城市名称
	//private String operatingCityName;
	// 顶部广告图
	private List<BannerVo> topBanner;
	// 停车场详情顶部系统参数
	private List<AdvertTextVo> sysParamList;
	// 停车场车位分布
	private List<SpaceNumberList> parkSpaceList;
	// 会员编号
	private String memberNo;
	
	public Integer getParkingType() {
		return parkingType;
	}
	public void setParkingType(Integer parkingType) {
		this.parkingType = parkingType;
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
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getStar() {
		return star;
	}
	public void setStar(String star) {
		this.star = star;
	}
	public List<BannerVo> getTopBanner() {
		return topBanner;
	}
	public void setTopBanner(List<BannerVo> topBanner) {
		this.topBanner = topBanner;
	}
/*	public String getParkingType() {
		return parkingType;
	}
	public void setParkingType(String parkingType) {
		this.parkingType = parkingType;
	}
	public String getParkingStatus() {
		return parkingStatus;
	}
	public void setParkingStatus(String parkingStatus) {
		this.parkingStatus = parkingStatus;
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
	}*/
	public List<AdvertTextVo> getSysParamList() {
		return sysParamList;
	}
	public void setSysParamList(List<AdvertTextVo> sysParamList) {
		this.sysParamList = sysParamList;
	}
	
	public List<SpaceNumberList> getParkSpaceList() {
		return parkSpaceList;
	}
	public void setParkSpaceList(List<SpaceNumberList> parkSpaceList) {
		this.parkSpaceList = parkSpaceList;
	}
	public String getMemberNo() {
		return memberNo;
	}
	public void setMemberNo(String memberNo) {
		this.memberNo = memberNo;
	}
	
}
