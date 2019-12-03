package cn.com.shopec.core.scheduling.vo;

public class WorkerOrderStatusVo {
	// 任务完成时的电量
	private Double power;
	// 备注
	private String memo;
	// 下发原因图片描述1
	private String sendReasonPicUrl1;
	// 下发原因图片描述2
	private String sendReasonPicUrl2;
	// 下发原因图片描述3
	private String sendReasonPicUrl3;

	public Double getPower() {
		return power;
	}

	public void setPower(Double power) {
		this.power = power;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getSendReasonPicUrl1() {
		return sendReasonPicUrl1;
	}

	public void setSendReasonPicUrl1(String sendReasonPicUrl1) {
		this.sendReasonPicUrl1 = sendReasonPicUrl1;
	}

	public String getSendReasonPicUrl2() {
		return sendReasonPicUrl2;
	}

	public void setSendReasonPicUrl2(String sendReasonPicUrl2) {
		this.sendReasonPicUrl2 = sendReasonPicUrl2;
	}

	public String getSendReasonPicUrl3() {
		return sendReasonPicUrl3;
	}

	public void setSendReasonPicUrl3(String sendReasonPicUrl3) {
		this.sendReasonPicUrl3 = sendReasonPicUrl3;
	}

}
