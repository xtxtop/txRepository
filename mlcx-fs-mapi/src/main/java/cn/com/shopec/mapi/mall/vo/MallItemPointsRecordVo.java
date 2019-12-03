package cn.com.shopec.mapi.mall.vo;

public class MallItemPointsRecordVo {

	// 记录编号
	private String recordNo;

	// 商品名称
	private String itemName;

	// 商品名称
	private String picUrl;

	// 兑换积分
	private Integer points;

	// 签收状态(0:未签收，1:已签收)
	private Integer status;

	// 收货电话
	private String phone;

	// 收货地址
	private String address;

	public String getRecordNo() {
		return recordNo;
	}

	public void setRecordNo(String recordNo) {
		this.recordNo = recordNo;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

	public Integer getPoints() {
		return points;
	}

	public void setPoints(Integer points) {
		this.points = points;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

}
