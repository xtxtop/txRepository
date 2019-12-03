package cn.com.shopec.core.ml.vo;

public class TerminalDetailsVo {
	// 充电枪编号
	private String chargingGunNo;
	// 充电枪编码
	private String chargingGunCode;
	// 充电枪编号
	private String chargingGunName;
	// 类型：0慢充、1快充
	private String electricityType;
	// 工作状态(0-空闲 1-充电中) |
	private String workStatus;
	// 支持的国际标准（‘，’分割）
	private String iso;
	// 功率范围
	private String kw;
	// 场站充电单价",
	private String unitPrice;
	// 地锁id
	private String parkingLockNo;
	// 升降状态(0.升 1.降)
	private String parkingLockStatus;

	public String getChargingGunNo() {
		return chargingGunNo;
	}

	public void setChargingGunNo(String chargingGunNo) {
		this.chargingGunNo = chargingGunNo;
	}

	public String getChargingGunCode() {
		return chargingGunCode;
	}

	public void setChargingGunCode(String chargingGunCode) {
		this.chargingGunCode = chargingGunCode;
	}

	public String getElectricityType() {
		return electricityType;
	}

	public void setElectricityType(String electricityType) {
		this.electricityType = electricityType;
	}

	public String getWorkStatus() {
		return workStatus;
	}

	public void setWorkStatus(String workStatus) {
		this.workStatus = workStatus;
	}

	public String getIso() {
		return iso;
	}

	public void setIso(String iso) {
		this.iso = iso;
	}

	public String getKw() {
		return kw;
	}

	public void setKw(String kw) {
		this.kw = kw;
	}

	public String getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(String unitPrice) {
		this.unitPrice = unitPrice;
	}

	public String getParkingLockNo() {
		return parkingLockNo;
	}

	public void setParkingLockNo(String parkingLockNo) {
		this.parkingLockNo = parkingLockNo;
	}

	public String getParkingLockStatus() {
		return parkingLockStatus;
	}

	public void setParkingLockStatus(String parkingLockStatus) {
		this.parkingLockStatus = parkingLockStatus;
	}

	public String getChargingGunName() {
		return chargingGunName;
	}

	public void setChargingGunName(String chargingGunName) {
		this.chargingGunName = chargingGunName;
	}
}
