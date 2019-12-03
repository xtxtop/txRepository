package cn.com.shopec.mgt.car.vo;

import java.io.Serializable;

public class CarVo implements Serializable{
	//上线车辆
	private Integer onlineCarNum;
	//下线车辆
	private Integer offloneCarNum;
	//订单车辆
	private Integer inOrderCarNum;
	//闲置车辆
	private Integer freeCarNum;
	//调度车辆
	private Integer inWorkerOrderCarNum;
	public Integer getOnlineCarNum() {
		return onlineCarNum;
	}
	public void setOnlineCarNum(Integer onlineCarNum) {
		this.onlineCarNum = onlineCarNum;
	}
	public Integer getOffloneCarNum() {
		return offloneCarNum;
	}
	public void setOffloneCarNum(Integer offloneCarNum) {
		this.offloneCarNum = offloneCarNum;
	}
	public Integer getInOrderCarNum() {
		return inOrderCarNum;
	}
	public void setInOrderCarNum(Integer inOrderCarNum) {
		this.inOrderCarNum = inOrderCarNum;
	}
	public Integer getFreeCarNum() {
		return freeCarNum;
	}
	public void setFreeCarNum(Integer freeCarNum) {
		this.freeCarNum = freeCarNum;
	}
	public Integer getInWorkerOrderCarNum() {
		return inWorkerOrderCarNum;
	}
	public void setInWorkerOrderCarNum(Integer inWorkerOrderCarNum) {
		this.inWorkerOrderCarNum = inWorkerOrderCarNum;
	}
	
}
