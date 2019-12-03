package cn.com.shopec.mapi.marketing.vo;

/** 
 * 
 */
public class OrderShareSettingVo {
	//订单分享名称
	private String orderShareName;
	//订单分享内容配置
	private String orderShareContent;
	//订单分享图片配置
	private String orderSharePicUrl;
	//是否能领取优惠(0、不能领取 1、可以领取)
	private String isOrderGetOffer;
	public String getOrderShareName() {
		return orderShareName;
	}
	public void setOrderShareName(String orderShareName) {
		this.orderShareName = orderShareName;
	}
	public String getOrderShareContent() {
		return orderShareContent;
	}
	public void setOrderShareContent(String orderShareContent) {
		this.orderShareContent = orderShareContent;
	}
	public String getOrderSharePicUrl() {
		return orderSharePicUrl;
	}
	public void setOrderSharePicUrl(String orderSharePicUrl) {
		this.orderSharePicUrl = orderSharePicUrl;
	}
	public String getIsOrderGetOffer() {
		return isOrderGetOffer;
	}
	public void setIsOrderGetOffer(String isOrderGetOffer) {
		this.isOrderGetOffer = isOrderGetOffer;
	}
	
}
