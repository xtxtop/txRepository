package cn.com.shopec.core.resource.vo;

import java.util.Date;

/**
 * 巡检计划表 数据实体类
 */
public class CpVo {

	/* Auto generated properties start */

	// 巡检计划编号
	private String checkPlanNo;
	// 计划状态，0、待处理，1、处理中，2、已取消，3、已完成 默认0
	private String planStatus;
	// 场站名称
	private String parkName;
	// 距离
	private String distance;
	// 创建时间（下发时间）
	private Date createTime;
	private String ceTime;
	// 计划巡检项名称（各项间用半角逗号进行分割）
	private String checkItem;

	public String getCheckPlanNo() {
		return checkPlanNo;
	}

	public void setCheckPlanNo(String checkPlanNo) {
		this.checkPlanNo = checkPlanNo;
	}

	public String getPlanStatus() {
		return planStatus;
	}

	public void setPlanStatus(String planStatus) {
		this.planStatus = planStatus;
	}

	public String getParkName() {
		return parkName;
	}

	public void setParkName(String parkName) {
		this.parkName = parkName;
	}

	public String getDistance() {
		return distance;
	}

	public void setDistance(String distance) {
		this.distance = distance;
	}

	public String getCheckItem() {
		return checkItem;
	}

	public void setCheckItem(String checkItem) {
		this.checkItem = checkItem;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getCeTime() {
		return ceTime;
	}

	public void setCeTime(String ceTime) {
		this.ceTime = ceTime;
	}

	@Override
	public String toString() {
		return "CpVo [checkPlanNo=" + checkPlanNo + ", planStatus=" + planStatus + ", parkName=" + parkName
				+ ", distance=" + distance + ", createTime=" + createTime + ", ceTime=" + ceTime + ", checkItem="
				+ checkItem + "]";
	}

}
