package cn.com.shopec.mapi.worker.vo;


import cn.com.shopec.core.common.Entity;

/** 
 * WorkerOrder调度工单 数据实体类
 */
public class WorkerOrderDetailVo extends Entity<String> {
	
	private static final long serialVersionUID = 1l;
	
	/*Auto generated properties start*/
	
	//低于多少就是低电量
	private Double dPower;
	
	//工单编号
	private String workerOrderNo;
	//调度类型  -- 0-挪车 1-洗车 2-维修 3-保养
	private String type;
	//车型
	private String carModelName;
	//车牌号
	private String carPlateNo;
	//备注（任务描述）
	private String memo;
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	//车辆关门状态
	private Integer doorStatus;
	//车辆充电百分比
	private Double power;
	
	//车辆熄火状态
	//车辆状态（1、已启动，2、已熄火）
	private Integer carStatus;
	
	//车辆停放位置
	private String locationParkNo;
	
	//车辆安全状态
	private String carSafeStatus;
	
	//总里程
	private Double mileage;

	//可行驶里程
	private Double rangeMileage;
	
	//车辆照片url1
	private String carPhotoUrl1;
	
	//调度工单状态 -- 0-未下发 1-已下发 2-调度中 3-已结束4-已取消
	private Integer workOrderStatus;
	
	//MAC地址
	private String macAddr;
	
	//终端序列号
	private String deviceSn;
	
	//deviceKey
	private String deviceKey;

	//起点
	private String startParkName;

	//终点
	private String terminalParkName;
	//品牌名称
	private String carBrandName;
	

	
	public Double getMileage() {
		return mileage;
	}
	public void setMileage(Double mileage) {
		this.mileage = mileage;
	}
	public Double getRangeMileage() {
		return rangeMileage;
	}
	public void setRangeMileage(Double rangeMileage) {
		this.rangeMileage = rangeMileage;
	}
	public Integer getDoorStatus() {
		return doorStatus;
	}
	public void setDoorStatus(Integer doorStatus) {
		this.doorStatus = doorStatus;
	}
	public String getLocationParkNo() {
		return locationParkNo;
	}
	public void setLocationParkNo(String locationParkNo) {
		this.locationParkNo = locationParkNo;
	}
	public String getCarSafeStatus() {
		return carSafeStatus;
	}
	public void setCarSafeStatus(String carSafeStatus) {
		this.carSafeStatus = carSafeStatus;
	}
	
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
	public Integer getCarStatus() {
		return carStatus;
	}
	public void setCarStatus(Integer carStatus) {
		this.carStatus = carStatus;
	}
	public Double getPower() {
		return power;
	}
	public void setPower(Double power) {
		this.power = power;
	}
	public Double getDPower() {
		return dPower;
	}
	public void setDPower(Double dPower) {
		this.dPower = dPower;
	}
	public String getCarPhotoUrl1() {
		return carPhotoUrl1;
	}
	public void setCarPhotoUrl1(String carPhotoUrl1) {
		this.carPhotoUrl1 = carPhotoUrl1;
	}
	
	public Integer getWorkOrderStatus() {
		return workOrderStatus;
	}
	public void setWorkOrderStatus(Integer workOrderStatus) {
		this.workOrderStatus = workOrderStatus;
	}
	
	public String getMacAddr() {
		return macAddr;
	}
	public void setMacAddr(String macAddr) {
		this.macAddr = macAddr;
	}
	public String getDeviceSn() {
		return deviceSn;
	}
	public void setDeviceSn(String deviceSn) {
		this.deviceSn = deviceSn;
	}
	public String getDeviceKey() {
		return deviceKey;
	}
	public void setDeviceKey(String deviceKey) {
		this.deviceKey = deviceKey;
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
	public String getCarBrandName() {
		return carBrandName;
	}
	public void setCarBrandName(String carBrandName) {
		this.carBrandName = carBrandName;
	}
	@Override
	public String toString() {
		return "WorkerOrderDetailVo [workerOrderNo=" + workerOrderNo + ", carModelName=" + carModelName
				+ ", carPlateNo=" + carPlateNo + ", memo=" + memo + ", carStatus=" + carStatus 
				+ ", power=" + power + ", dPower=" + dPower + ", doorStatus=" + doorStatus + ", locationParkNo=" + locationParkNo 
				+ ", carSafeStatus=" + carSafeStatus + ", mileage=" + mileage 
				+ ", rangeMileage=" + rangeMileage + ", carPhotoUrl1=" + carPhotoUrl1 
				+ ", workOrderStatus=" + workOrderStatus + ", macAddr=" + macAddr 
				+ ", deviceSn=" + deviceSn  + ", deviceKey=" + deviceKey  + ", startParkName=" + startParkName  
				+ ", terminalParkName=" + terminalParkName  + ", carBrandName=" + carBrandName  + ", type=" + type  
				+ "]";
	}
	
	
}
