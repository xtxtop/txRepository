package cn.com.shopec.core.ml.vo;

import java.util.List;

public class ChargingStationDetailsVo {
	private String stationNo;// 充电站编号",
	private String stationName;// 充电站名称",
	private String collectStatus;// 收藏状态（0未收藏，1已收藏）",
	private String star;// 评分[0-5]",
	private String chargeFastSum;// 快充总数",
	private String chargeFastIdleSum;// 快充空闲总数",
	private String chargeTrickleSum;// 慢充总数",
	private String chargeTrickleIdleSum;// 慢快充空闲总数",
	private String longitude;// 经度",
	private String latitude;// 纬度",
	private String addrStreet;// 街道详址",
	private String distance;// 街道详址",
	private String paymentMethod;// 支付方式(本APP,微信,支付宝)字符串",
	private String businessHours;// 营业时间(00:00-24:00)",
	private String unitPrice;// 场站充电单价",
	private String electricPrice;// 场站电费单价",
	private String servicePrice;// 场站服务费单价",
	private String managementCompany;// 管理公司",
	private String serviceTel;// 客服电话",
	private Object labels;
	private List<CLabelVo> labelList;// ["自营","对外开放"],
	private Object matchList;//// 周边设施
	private Object centreBanner;//// 中部广告图
	private Object topBanner;//// 顶部广告图
	private String memberNo;// 会员编号
	// private String stationUrl;// 充电站图片

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

	public String getCollectStatus() {
		return collectStatus;
	}

	public void setCollectStatus(String collectStatus) {
		this.collectStatus = collectStatus;
	}

	public String getStar() {
		return star;
	}

	public void setStar(String star) {
		this.star = star;
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

	public String getAddrStreet() {
		return addrStreet;
	}

	public void setAddrStreet(String addrStreet) {
		this.addrStreet = addrStreet;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public String getBusinessHours() {
		return businessHours;
	}

	public void setBusinessHours(String businessHours) {
		this.businessHours = businessHours;
	}

	public String getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(String unitPrice) {
		this.unitPrice = unitPrice;
	}

	public String getElectricPrice() {
		return electricPrice;
	}

	public void setElectricPrice(String electricPrice) {
		this.electricPrice = electricPrice;
	}

	public String getServicePrice() {
		return servicePrice;
	}

	public void setServicePrice(String servicePrice) {
		this.servicePrice = servicePrice;
	}

	public Object getMatchList() {
		return matchList;
	}

	public void setMatchList(Object matchList) {
		this.matchList = matchList;
	}

	public Object getCentreBanner() {
		return centreBanner;
	}

	public void setCentreBanner(Object centreBanner) {
		this.centreBanner = centreBanner;
	}

	/** 校验参数（返回true表示参数无误） */
	public boolean verifyParam() {
		boolean verify = stationNo != null && stationNo.length() > 0;
		if (verify) {
			try {
				Double.valueOf(longitude != null && longitude.length() > 0 ? longitude : "0");
				Double.valueOf(latitude != null && latitude.length() > 0 ? latitude : "0");
			} catch (Exception e) {
				verify = false;
			}
		}
		return verify;
	}

	public String getDistance() {
		return distance;
	}

	public void setDistance(String distance) {
		this.distance = distance;
	}

	public String getManagementCompany() {
		return managementCompany;
	}

	public void setManagementCompany(String managementCompany) {
		this.managementCompany = managementCompany;
	}

	public String getServiceTel() {
		return serviceTel;
	}

	public void setServiceTel(String serviceTel) {
		this.serviceTel = serviceTel;
	}

	public List<CLabelVo> getLabelList() {
		return labelList;
	}

	public void setLabelList(List<CLabelVo> labelList) {
		this.labelList = labelList;
	}

	public Object getLabels() {
		return labels;
	}

	public void setLabels(Object labels) {
		this.labels = labels;
	}

	public Object getTopBanner() {
		return topBanner;
	}

	public void setTopBanner(Object topBanner) {
		this.topBanner = topBanner;
	}

	public String getMemberNo() {
		return memberNo;
	}

	public void setMemberNo(String memberNo) {
		this.memberNo = memberNo;
	}

}
