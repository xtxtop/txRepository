package cn.com.shopec.core.resource.model;


import java.util.List;

import cn.com.shopec.core.common.Entity;
import cn.com.shopec.core.scheduling.model.WorkerOrder;

/** 
 * WorkerOrder调度工单 数据实体类
 */
public class WorkerOrderPark extends Entity<String> {
	
	private static final long serialVersionUID = 1l;
	
	/*Auto generated properties start*/
	//场站编号
	private String parkNo;
	//场站名称
	private String parkName;
	//场站地址
	private String address;
	//实时车辆
	private int nowCarNum;
	//实时任务量
	private int nowWorkerOrderNum;
	//距离
	private Double distance;
	//任务列表
	private List<WorkerOrderParkVo> woList;
	public String getParkNo() {
		return parkNo;
	}
	public void setParkNo(String parkNo) {
		this.parkNo = parkNo;
	}
	public String getParkName() {
		return parkName;
	}
	public void setParkName(String parkName) {
		this.parkName = parkName;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public int getNowCarNum() {
		return nowCarNum;
	}
	public void setNowCarNum(int nowCarNum) {
		this.nowCarNum = nowCarNum;
	}
	public int getNowWorkerOrderNum() {
		return nowWorkerOrderNum;
	}
	public void setNowWorkerOrderNum(int nowWorkerOrderNum) {
		this.nowWorkerOrderNum = nowWorkerOrderNum;
	}
	public Double getDistance() {
		return distance;
	}
	public void setDistance(Double distance) {
		this.distance = distance;
	}
	public List<WorkerOrderParkVo> getWoList() {
		return woList;
	}
	public void setWoList(List<WorkerOrderParkVo> woList) {
		this.woList = woList;
	}
	@Override
	public String toString() {
		return "WorkerOrderPark [parkNo=" + parkNo + ", parkName=" + parkName + ", address=" + address + ", nowCarNum="
				+ nowCarNum + ", nowWorkerOrderNum=" + nowWorkerOrderNum + ", distance=" + distance + ", woList="
				+ woList + "]";
	}
	
	
	
	
	
	
}
