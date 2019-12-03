package cn.com.shopec.core.car.vo;

import java.util.Date;

public class CarRecordWkVo {
	// 单据号（用车类型为订单时，是订单号、调度用车时，是调度单号）
	private String documentNo;
	// 用车人姓名
	private String driverName;
	// 用车人id
	private String driverId;
	// 开始时间
	private Date startTime;
	private String stTime;
	// 结束时间
	private Date finishTime;
	private String fhTime;
	// 手机
	private String mobilePhone;
	// 用车类型（1、订单用车，2、调度用车）
	private Integer useCarType;
	// 起点名称
	private String startParkName;
	// 终点名称
	private String terminalParkName;

	public String getDocumentNo() {
		return documentNo;
	}

	public void setDocumentNo(String documentNo) {
		this.documentNo = documentNo;
	}

	public String getDriverName() {
		return driverName;
	}

	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}

	public String getDriverId() {
		return driverId;
	}

	public void setDriverId(String driverId) {
		this.driverId = driverId;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public String getStTime() {
		return stTime;
	}

	public void setStTime(String stTime) {
		this.stTime = stTime;
	}

	public Date getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(Date finishTime) {
		this.finishTime = finishTime;
	}

	public String getFhTime() {
		return fhTime;
	}

	public void setFhTime(String fhTime) {
		this.fhTime = fhTime;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public Integer getUseCarType() {
		return useCarType;
	}

	public void setUseCarType(Integer useCarType) {
		this.useCarType = useCarType;
	}

	public String getStartParkName() {
		return startParkName;
	}

	public void setStartParkName(String startParkName) {
		this.startParkName = startParkName;
	}

	public String getTerminalParkName() {
		return terminalParkName;
	}

	public void setTerminalParkName(String terminalParkName) {
		this.terminalParkName = terminalParkName;
	}

}
