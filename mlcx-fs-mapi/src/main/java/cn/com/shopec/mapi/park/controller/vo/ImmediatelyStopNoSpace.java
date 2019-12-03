package cn.com.shopec.mapi.park.controller.vo;


/**
 * @author 代元宝
 * @category 立即停车 app 模型
 *
 */
public class ImmediatelyStopNoSpace {
	//停车场编号
	private String parkNo;
	// 停车场名称
	private String parkName;
	// 会员编号
	private String memberNo;
	// 免费时长
	private Integer freeTime;
	// 计费编号
	private String billingNo;
	// 收费时间段
	private String timeNum;
	// 收费标准
	private String overtimePrice;
	// 最高收费
	private String cappingPrice;

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

	public Integer getFreeTime() {
		return freeTime;
	}

	public void setFreeTime(Integer freeTime) {
		this.freeTime = freeTime;
	}

	public String getBillingNo() {
		return billingNo;
	}

	public void setBillingNo(String billingNo) {
		this.billingNo = billingNo;
	}

	public String getTimeNum() {
		return timeNum;
	}

	public void setTimeNum(String timeNum) {
		this.timeNum = timeNum;
	}

	public String getOvertimePrice() {
		return overtimePrice;
	}

	public void setOvertimePrice(String overtimePrice) {
		this.overtimePrice = overtimePrice;
	}

	public String getCappingPrice() {
		return cappingPrice;
	}

	public void setCappingPrice(String cappingPrice) {
		this.cappingPrice = cappingPrice;
	}


	public String getMemberNo() {
		return memberNo;
	}

	public void setMemberNo(String memberNo) {
		this.memberNo = memberNo;
	}


}
