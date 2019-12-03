package cn.com.shopec.core.mlparking.vo;

import java.util.List;

/**
 * @author daiyuanbao
 * @category 分层集合
 *
 */
public class SpaceNumberList {
	//停车场入口名称
	private String entranceName;
	//营业时间
	private String businessHours;
	//收费规则
	private String chargingRules;
	//总车位
	private Integer totalSpace;
	//剩余车位
	private Integer surplusSpace;
	//图片
	private String img;
	//距离
	private String distance;
	//经度
	private String lon;
	//纬度
	private String lat;
	//车位分层
	private List<ParkSpaceVo> spaceList;
	//分布类型
	private Integer spaceType;
	
	public Integer getSpaceType() {
		return spaceType;
	}
	public void setSpaceType(Integer spaceType) {
		this.spaceType = spaceType;
	}
	public String getBusinessHours() {
		return businessHours;
	}
	public void setBusinessHours(String businessHours) {
		this.businessHours = businessHours;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public String getDistance() {
		return distance;
	}
	public void setDistance(String distance) {
		this.distance = distance;
	}
	public String getLon() {
		return lon;
	}
	public void setLon(String lon) {
		this.lon = lon;
	}
	public String getLat() {
		return lat;
	}
	public void setLat(String lat) {
		this.lat = lat;
	}
	public String getEntranceName() {
		return entranceName;
	}
	public void setEntranceName(String entranceName) {
		this.entranceName = entranceName;
	}
	public String getChargingRules() {
		return chargingRules;
	}
	public void setChargingRules(String chargingRules) {
		this.chargingRules = chargingRules;
	}
	public Integer getTotalSpace() {
		return totalSpace;
	}
	public void setTotalSpace(Integer totalSpace) {
		this.totalSpace = totalSpace;
	}
	public Integer getSurplusSpace() {
		return surplusSpace;
	}
	public void setSurplusSpace(Integer surplusSpace) {
		this.surplusSpace = surplusSpace;
	}
	public List<ParkSpaceVo> getSpaceList() {
		return spaceList;
	}
	public void setSpaceList(List<ParkSpaceVo> spaceList) {
		this.spaceList = spaceList;
	}
	
}
