package cn.com.shopec.mapi.marketing.vo;

/** 
 * 
 */
public class OrderShareSettingForAppVo {
	//订单分享名称
	private String orderShareName;
	//订单分享链接上的尖端内容
	private String orderShareMemo;
	//订单分享icon配置
	private String orderShareIconUrl;
	//订单分享icon配置
	private String OrderShareUrl;
	//订单分享红包个数
	private String orderShareRedPacketNum;
	
	public String getOrderShareName() {
		return orderShareName;
	}
	public void setOrderShareName(String orderShareName) {
		this.orderShareName = orderShareName;
	}
	public String getOrderShareMemo() {
		return orderShareMemo;
	}
	public void setOrderShareMemo(String orderShareMemo) {
		this.orderShareMemo = orderShareMemo;
	}
	public String getOrderShareIconUrl() {
		return orderShareIconUrl;
	}
	public void setOrderShareIconUrl(String orderShareIconUrl) {
		this.orderShareIconUrl = orderShareIconUrl;
	}
	public String getOrderShareUrl() {
		return OrderShareUrl;
	}
	public void setOrderShareUrl(String orderShareUrl) {
		OrderShareUrl = orderShareUrl;
	}
	public String getOrderShareRedPacketNum() {
		return orderShareRedPacketNum;
	}
	public void setOrderShareRedPacketNum(String orderShareRedPacketNum) {
		this.orderShareRedPacketNum = orderShareRedPacketNum;
	}
}
