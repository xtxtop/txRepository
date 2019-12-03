package cn.com.shopec.core.car.vo;

import java.util.Date;

public class CarOnlineVo {
	// 上线状态（0、下线，1、上线，默认0）
	private String onlineStatus;
	// 操作人id
	private String operatorId;
	// 操作人
	private String operatorName;
	// 操作时间
	private Date createTime;
	private String ceTime;
	// 车辆上下线理由url1
	private String carOnLineOrOffPicUrl1;
	// 车辆上下线理由url2
	private String carOnLineOrOffPicUrl2;
	// 车辆上下线理由url3
	private String carOnLineOrOffPicUrl3;
	// 上下线理由（下拉选择用）
	private String updownWhy;
	// 备注
	private String memo;

	public String getOperatorName() {
		return operatorName;
	}

	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getCarOnLineOrOffPicUrl1() {
		return carOnLineOrOffPicUrl1;
	}

	public void setCarOnLineOrOffPicUrl1(String carOnLineOrOffPicUrl1) {
		this.carOnLineOrOffPicUrl1 = carOnLineOrOffPicUrl1;
	}

	public String getCarOnLineOrOffPicUrl2() {
		return carOnLineOrOffPicUrl2;
	}

	public void setCarOnLineOrOffPicUrl2(String carOnLineOrOffPicUrl2) {
		this.carOnLineOrOffPicUrl2 = carOnLineOrOffPicUrl2;
	}

	public String getCarOnLineOrOffPicUrl3() {
		return carOnLineOrOffPicUrl3;
	}

	public void setCarOnLineOrOffPicUrl3(String carOnLineOrOffPicUrl3) {
		this.carOnLineOrOffPicUrl3 = carOnLineOrOffPicUrl3;
	}

	public String getUpdownWhy() {
		return updownWhy;
	}

	public void setUpdownWhy(String updownWhy) {
		this.updownWhy = updownWhy;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getOnlineStatus() {
		return onlineStatus;
	}

	public void setOnlineStatus(String onlineStatus) {
		this.onlineStatus = onlineStatus;
	}

	public String getCeTime() {
		return ceTime;
	}

	public void setCeTime(String ceTime) {
		this.ceTime = ceTime;
	}

	public String getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(String operatorId) {
		this.operatorId = operatorId;
	}

}
