package cn.com.shopec.core.resource.vo;

public class CheckPlanResultNsVo {
	// 设备号（车辆为车牌号，充电桩为充电桩编号）
	private String deviceNo;
	// 结果（1、正常，0、异常）
	private String checkResult;
	// 异常简述
	private String abnormalBrief;
	// 异常详述
	private String abnormalDetail;
	// 备注（预留）
	private String memo;
	// 巡检结果照片1
	private String photourl1;
	// 巡检结果照片2
	private String photourl2;
	// 巡检结果照片3
	private String photourl3;

	public String getDeviceNo() {
		return deviceNo;
	}

	public void setDeviceNo(String deviceNo) {
		this.deviceNo = deviceNo;
	}

	public String getAbnormalBrief() {
		return abnormalBrief;
	}

	public void setAbnormalBrief(String abnormalBrief) {
		this.abnormalBrief = abnormalBrief;
	}

	public String getAbnormalDetail() {
		return abnormalDetail;
	}

	public void setAbnormalDetail(String abnormalDetail) {
		this.abnormalDetail = abnormalDetail;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getPhotourl1() {
		return photourl1;
	}

	public void setPhotourl1(String photourl1) {
		this.photourl1 = photourl1;
	}

	public String getPhotourl2() {
		return photourl2;
	}

	public void setPhotourl2(String photourl2) {
		this.photourl2 = photourl2;
	}

	public String getPhotourl3() {
		return photourl3;
	}

	public void setPhotourl3(String photourl3) {
		this.photourl3 = photourl3;
	}

	public String getCheckResult() {
		return checkResult;
	}

	public void setCheckResult(String checkResult) {
		this.checkResult = checkResult;
	}

}
