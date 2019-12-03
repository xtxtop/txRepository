package cn.com.shopec.core.ml.vo;

public class ReceiverLockVo {
	private String Id;//	":"039ea0a71d8e5-03dfefe9-ca90-449b-93e2-37f180a5829e",
	private String LockId;//":"359369082125580",
	private String Time;//	":"2018-11-09 11:39:23",
	private String LockStatusCode;//地锁状态码（01 平躺，02 竖起，03 上升中，04 下降中，EF 未知）
	private String LockStatusMsg;//":"平躺",
	private String DeviceStatusCode;//设备状态码（01 正常，02 电量低，03 担臂故障，04 电机故障，11 升降故障，12 超声波传感故障）
	private String DeviceStatusMsg;//	":"正常",
	private String PlaceStatusCode;//车位状态码（01 空闲，02 占用）
	private String PlaceStatusMsg;//":"占用",
	private String Voltage;//	":6.2,
	private String Version;//	":"V2",
	private String SIM;//	":"460113015469797",
	private String MouduleFactory;//":"中怡数款",
	private String Signal;//":89,
	private String SNR;//	":8
	private String Success;//	":null

	public String getId() {
		return Id;
	}

	public void setId(String id) {
		Id = id;
	}

	public String getLockId() {
		return LockId;
	}

	public void setLockId(String lockId) {
		LockId = lockId;
	}

	public String getTime() {
		return Time;
	}

	public void setTime(String time) {
		Time = time;
	}

	public String getLockStatusCode() {
		return LockStatusCode;
	}

	public void setLockStatusCode(String lockStatusCode) {
		LockStatusCode = lockStatusCode;
	}

	public String getLockStatusMsg() {
		return LockStatusMsg;
	}

	public void setLockStatusMsg(String lockStatusMsg) {
		LockStatusMsg = lockStatusMsg;
	}

	public String getDeviceStatusCode() {
		return DeviceStatusCode;
	}

	public void setDeviceStatusCode(String deviceStatusCode) {
		DeviceStatusCode = deviceStatusCode;
	}

	public String getDeviceStatusMsg() {
		return DeviceStatusMsg;
	}

	public void setDeviceStatusMsg(String deviceStatusMsg) {
		DeviceStatusMsg = deviceStatusMsg;
	}

	public String getPlaceStatusCode() {
		return PlaceStatusCode;
	}

	public void setPlaceStatusCode(String placeStatusCode) {
		PlaceStatusCode = placeStatusCode;
	}

	public String getPlaceStatusMsg() {
		return PlaceStatusMsg;
	}

	public void setPlaceStatusMsg(String placeStatusMsg) {
		PlaceStatusMsg = placeStatusMsg;
	}

	public String getVoltage() {
		return Voltage;
	}

	public void setVoltage(String voltage) {
		Voltage = voltage;
	}

	public String getVersion() {
		return Version;
	}

	public void setVersion(String version) {
		Version = version;
	}

	public String getSIM() {
		return SIM;
	}

	public void setSIM(String SIM) {
		this.SIM = SIM;
	}

	public String getMouduleFactory() {
		return MouduleFactory;
	}

	public void setMouduleFactory(String mouduleFactory) {
		MouduleFactory = mouduleFactory;
	}

	public String getSignal() {
		return Signal;
	}

	public void setSignal(String signal) {
		Signal = signal;
	}

	public String getSNR() {
		return SNR;
	}

	public void setSNR(String SNR) {
		this.SNR = SNR;
	}

	public String getSuccess() {
		return Success;
	}

	public void setSuccess(String success) {
		Success = success;
	}
}
