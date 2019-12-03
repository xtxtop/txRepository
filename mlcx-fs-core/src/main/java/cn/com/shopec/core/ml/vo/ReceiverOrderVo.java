package cn.com.shopec.core.ml.vo;

public class ReceiverOrderVo {
	private String CDZ_Number;// 充电桩编码 ": "3300000021000011",
	private String qbh;// 枪编号 ": "01",
	private String Record_time;// 记录时间 ": "2018-01-03 14:49:14",
	private String Serial_number;// 流水号 ": "33000000000000211544131612170002",
	private String Start_time;// 开始时间 ": "18-01-03 15:02:59.525",
	private String Finish_time;// 结束时间 ": "18-01-03 15:04:04.260",
	private String Total_start;// 总开始电量 ": "155.77",
	private String Total_finish;// 总结束电量 ": "155.96",
	private String Opint;// 尖电量 ": "155.96",
	private String Peak;// 峰电量 ": "155.96",
	private String Flat;// 平电量 ": "155.96",
	private String Valley;// 谷电量 ": "155.96",
	private String Total_money;// 总金额 ": "0.26",
	private String VIN;// VIN号 ": "0000000000000000000000000000000000"

	public String getCDZ_Number() {
		return CDZ_Number;
	}

	public void setCDZ_Number(String CDZ_Number) {
		this.CDZ_Number = CDZ_Number;
	}

	public String getQbh() {
		return qbh;
	}

	public void setQbh(String qbh) {
		this.qbh = qbh;
	}

	public String getRecord_time() {
		return Record_time;
	}

	public void setRecord_time(String record_time) {
		Record_time = record_time;
	}

	public String getSerial_number() {
		return Serial_number;
	}

	public void setSerial_number(String serial_number) {
		Serial_number = serial_number;
	}

	public String getStart_time() {
		return Start_time;
	}

	public void setStart_time(String start_time) {
		Start_time = start_time;
	}

	public String getFinish_time() {
		return Finish_time;
	}

	public void setFinish_time(String finish_time) {
		Finish_time = finish_time;
	}

	public String getTotal_start() {
		return Total_start;
	}

	public void setTotal_start(String total_start) {
		Total_start = total_start;
	}

	public String getTotal_finish() {
		return Total_finish;
	}

	public void setTotal_finish(String total_finish) {
		Total_finish = total_finish;
	}

	public String getOpint() {
		return Opint;
	}

	public void setOpint(String opint) {
		Opint = opint;
	}

	public String getPeak() {
		return Peak;
	}

	public void setPeak(String peak) {
		Peak = peak;
	}

	public String getFlat() {
		return Flat;
	}

	public void setFlat(String flat) {
		Flat = flat;
	}

	public String getValley() {
		return Valley;
	}

	public void setValley(String valley) {
		Valley = valley;
	}

	public String getTotal_money() {
		return Total_money;
	}

	public void setTotal_money(String total_money) {
		Total_money = total_money;
	}

	public String getVIN() {
		return VIN;
	}

	public void setVIN(String VIN) {
		this.VIN = VIN;
	}
}
