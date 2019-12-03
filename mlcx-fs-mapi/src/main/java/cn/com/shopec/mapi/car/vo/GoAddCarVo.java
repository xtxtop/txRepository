package cn.com.shopec.mapi.car.vo;

import java.io.Serializable;
import java.util.List;

import cn.com.shopec.core.car.model.CarAndStatus;

public class GoAddCarVo implements Serializable {

	/**
	 *
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	 */
	private static final long serialVersionUID = 6828172510484748233L;

	private List<CarAndStatus> carAndStatusList;
	private String advert;// 最新的一条促销信息记录，已审核，已发布的一条记录，这里只返回里面的text1；
	private String expenses;// 资费说明
	private String parkNo; // 站点编号
	private String address;// 站点位置
	private String detailAddress;// 站点详细位置
	private String parkName;// 站点名称
	private Integer carNumber;// 车辆数
	private Integer chargingPostNum;// 充电桩数量
	private Double getCarAmount;// 取车费用

	// 坐标经度
	private String longitude;
	// 坐标纬度
	private String latitude;
	// 营业时间
	private String businessHours;
	private Integer paringSpaceNum;// 可用车位数
	private String parkPhotoUrl1;// 场站图片1
	private String parkPhotoUrl2;// 场站图片2
	private String parkPhotoUrl3;// 场站图片3
	private Double returnCarAmount;// 还车费用
	// 是否有红包车辆的场站(0、否 1、是)
	private String isRedPakcetPark;
	// 人到站点的距离
	private Double memberDistance;

	// 充电电桩的快慢类型(电桩类型 慢充、快充）)
	private Integer fast;
	private Integer slow;

	public List<CarAndStatus> getCarAndStatusList() {
		return carAndStatusList;
	}

	public void setCarAndStatusList(List<CarAndStatus> carAndStatusList) {
		this.carAndStatusList = carAndStatusList;
	}

	public String getAdvert() {
		return advert;
	}

	public void setAdvert(String advert) {
		this.advert = advert;
	}

	public String getExpenses() {
		return expenses;
	}

	public void setExpenses(String expenses) {
		this.expenses = expenses;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "GoAddCarVo [carAndStatusList=" + carAndStatusList + ", advert=" + advert + ", expenses=" + expenses
				+ "]";
	}

	public String getParkNo() {
		return parkNo;
	}

	public void setParkNo(String parkNo) {
		this.parkNo = parkNo;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getParkName() {
		return parkName;
	}

	public void setParkName(String parkName) {
		this.parkName = parkName;
	}

	public Integer getChargingPostNum() {
		return chargingPostNum;
	}

	public void setChargingPostNum(Integer chargingPostNum) {
		this.chargingPostNum = chargingPostNum;
	}

	public Integer getCarNumber() {
		return carNumber;
	}

	public void setCarNumber(Integer carNumber) {
		this.carNumber = carNumber;
	}

	public Double getGetCarAmount() {
		return getCarAmount;
	}

	public void setGetCarAmount(Double getCarAmount) {
		this.getCarAmount = getCarAmount;
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

	public Integer getParingSpaceNum() {
		return paringSpaceNum;
	}

	public void setParingSpaceNum(Integer paringSpaceNum) {
		this.paringSpaceNum = paringSpaceNum;
	}

	public String getParkPhotoUrl1() {
		return parkPhotoUrl1;
	}

	public void setParkPhotoUrl1(String parkPhotoUrl1) {
		this.parkPhotoUrl1 = parkPhotoUrl1;
	}

	public String getParkPhotoUrl3() {
		return parkPhotoUrl3;
	}

	public void setParkPhotoUrl3(String parkPhotoUrl3) {
		this.parkPhotoUrl3 = parkPhotoUrl3;
	}

	public String getParkPhotoUrl2() {
		return parkPhotoUrl2;
	}

	public void setParkPhotoUrl2(String parkPhotoUrl2) {
		this.parkPhotoUrl2 = parkPhotoUrl2;
	}

	public Double getReturnCarAmount() {
		return returnCarAmount;
	}

	public void setReturnCarAmount(Double returnCarAmount) {
		this.returnCarAmount = returnCarAmount;
	}

	public String getDetailAddress() {
		return detailAddress;
	}

	public void setDetailAddress(String detailAddress) {
		this.detailAddress = detailAddress;
	}

	public String getIsRedPakcetPark() {
		return isRedPakcetPark;
	}

	public void setIsRedPakcetPark(String isRedPakcetPark) {
		this.isRedPakcetPark = isRedPakcetPark;
	}

	public Double getMemberDistance() {
		return memberDistance;
	}

	public void setMemberDistance(Double memberDistance) {
		this.memberDistance = memberDistance;
	}

	public Integer getFast() {
		return fast;
	}

	public void setFast(Integer fast) {
		this.fast = fast;
	}

	public Integer getSlow() {
		return slow;
	}

	public void setSlow(Integer slow) {
		this.slow = slow;
	}

}
