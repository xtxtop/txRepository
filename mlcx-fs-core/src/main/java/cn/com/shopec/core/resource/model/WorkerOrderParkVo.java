package cn.com.shopec.core.resource.model;


import cn.com.shopec.core.common.Entity;

/** 
 * WorkerOrder调度工单 数据实体类
 */
public class WorkerOrderParkVo extends Entity<String> {
	
	private static final long serialVersionUID = 1l;
	
	/*Auto generated properties start*/
	
	//工单编号
	private String workerOrderNo;
	//车型
	private String carModelName;
	//车牌号
	private String carPlateNo;
	//备注（任务描述）
	private String memo;
	//预估里程
	private Double mileage;

	//品牌名称
	private String carBrandName;
	//车辆照片url1
	private String carPhotoUrl1;
	//起点
	private String startParkName;
	//终点
	private String terminalParkName;
	
	public String getWorkerOrderNo() {
		return workerOrderNo;
	}
	public void setWorkerOrderNo(String workerOrderNo) {
		this.workerOrderNo = workerOrderNo;
	}
	public String getCarModelName() {
		return carModelName;
	}
	public void setCarModelName(String carModelName) {
		this.carModelName = carModelName;
	}
	public String getCarPlateNo() {
		return carPlateNo;
	}
	public void setCarPlateNo(String carPlateNo) {
		this.carPlateNo = carPlateNo;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	
	public Double getMileage() {
		return mileage;
	}
	public void setMileage(Double mileage) {
		this.mileage = mileage;
	}
	
	public String getCarBrandName() {
		return carBrandName;
	}
	public void setCarBrandName(String carBrandName) {
		this.carBrandName = carBrandName;
	}
	public String getCarPhotoUrl1() {
		return carPhotoUrl1;
	}
	public void setCarPhotoUrl1(String carPhotoUrl1) {
		this.carPhotoUrl1 = carPhotoUrl1;
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
	@Override
	public String toString() {
		return "WorkerOrderParkVo [workerOrderNo=" + workerOrderNo + ", carModelName=" + carModelName + ", carPlateNo="
				+ carPlateNo + ", memo=" + memo + ", mileage=" + mileage + ", carBrandName=" + carBrandName 
				+ ", carPhotoUrl1=" + carPhotoUrl1 + ", startParkName=" + startParkName + ", terminalParkName=" + terminalParkName + "]";
	}
	
	
	
	


}
