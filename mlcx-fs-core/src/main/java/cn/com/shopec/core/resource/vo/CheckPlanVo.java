package cn.com.shopec.core.resource.vo;

import cn.com.shopec.core.common.Entity;

/**
 * 巡检计划表 数据实体类
 */
public class CheckPlanVo extends Entity<String> {

	private static final long serialVersionUID = 1l;

	/* Auto generated properties start */

	// 巡检计划编号
	private String checkPlanNo;
	// 计划状态，0、待处理，1、处理中，2、已取消，3、已完成 默认0
	private Integer planStatus;
	// 场站名称
	private String parkName;
	// 剩余时间
	private String remainingTime;
	// 距离
	private Double distance;
	// 派单时间
	private String createTime;
	// 计划完成时间
	private String planDate;
	// 计划巡检项名称（各项间用半角逗号进行分割）
	private String checkItem;
	// 坐标经度 圆形场站为中心经度，多边形场站为几何中心经度
	private String longitude;
	// 坐标纬度 圆形场站为中心纬度，多边形场站为几何中心纬度
	private String latitude;

	/* Auto generated properties end */
	public String getCheckPlanNo() {
		return checkPlanNo;
	}

	public void setCheckPlanNo(String checkPlanNo) {
		this.checkPlanNo = checkPlanNo;
	}

	public String getPlanDate() {
		return planDate;
	}

	public void setPlanDate(String planDate) {
		this.planDate = planDate;
	}

	public Integer getPlanStatus() {
		return planStatus;
	}

	public void setPlanStatus(Integer planStatus) {
		this.planStatus = planStatus;
	}

	public String getParkName() {
		return parkName;
	}

	public void setParkName(String parkName) {
		this.parkName = parkName;
	}

	public String getRemainingTime() {
		return remainingTime;
	}

	public void setRemainingTime(String remainingTime) {
		this.remainingTime = remainingTime;
	}

	public Double getDistance() {
		return distance;
	}

	public void setDistance(Double distance) {
		this.distance = distance;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getCheckItem() {
		return checkItem;
	}

	public void setCheckItem(String checkItem) {
		this.checkItem = checkItem;
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

	@Override
	public String toString() {
		return "CheckPlanVo [checkPlanNo=" + checkPlanNo + ", planStatus=" + planStatus + ", parkName=" + parkName
				+ ", remainingTime=" + remainingTime + ", distance=" + distance + ", createTime=" + createTime
				+ ", planDate=" + planDate + ", checkItem=" + checkItem + ", longitude=" + longitude + ", latitude="
				+ latitude + "]";
	}

	/* Customized properties start */

	/* Customized properties end */

	/* Auto generated methods start */

}
