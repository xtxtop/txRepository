package cn.com.shopec.mapi.mall.vo;

public class MallItemVo {

	// 商品编号
	private String itemNo;

	// 商品名称
	private String picUrl;

	// 商品名称
	private String itemName;

	// 兑换积分
	private Integer points;

	public String getItemNo() {
		return itemNo;
	}

	public void setItemNo(String itemNo) {
		this.itemNo = itemNo;
	}

	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public Integer getPoints() {
		return points;
	}

	public void setPoints(Integer points) {
		this.points = points;
	}

}
