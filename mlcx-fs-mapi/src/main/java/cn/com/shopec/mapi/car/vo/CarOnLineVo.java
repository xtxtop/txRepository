package cn.com.shopec.mapi.car.vo;

import java.io.Serializable;

public class CarOnLineVo implements Serializable{
	/** 
	*
	* @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么) 
	*/ 
	private static final long serialVersionUID = 1L;
	//车辆编号
	private String carNo;
	//车牌号
	private String carPlateNo;
	//车辆使用状态
	private Integer userageStatus;
	//车辆上下线状态
	private Integer onLineStatus;
	public String getCarNo() {
		return carNo;
	}
	public void setCarNo(String carNo) {
		this.carNo = carNo;
	}
	public String getCarPlateNo() {
		return carPlateNo;
	}
	public void setCarPlateNo(String carPlateNo) {
		this.carPlateNo = carPlateNo;
	}
	public Integer getUserageStatus() {
		return userageStatus;
	}
	public void setUserageStatus(Integer userageStatus) {
		this.userageStatus = userageStatus;
	}
	public Integer getOnLineStatus() {
		return onLineStatus;
	}
	public void setOnLineStatus(Integer onLineStatus) {
		this.onLineStatus = onLineStatus;
	}

	
		
}
