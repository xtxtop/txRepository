package cn.com.shopec.mapi.marketing.vo;

/** 
 * 订单分享
 */
public class OrderShareVo {

	//优惠金额
	private String discountAmount;
	//折扣
	private String discount;
	//有效期开始时间
	private String availableTime1;
	//有效期结束时间
	private String availableTime2;
	
	public String getDiscountAmount() {
		return discountAmount;
	}
	public void setDiscountAmount(String discountAmount) {
		this.discountAmount = discountAmount;
	}
	public String getDiscount() {
		return discount;
	}
	public void setDiscount(String discount) {
		this.discount = discount;
	}
	public String getAvailableTime1() {
		return availableTime1;
	}
	public void setAvailableTime1(String availableTime1) {
		this.availableTime1 = availableTime1;
	}
	public String getAvailableTime2() {
		return availableTime2;
	}
	public void setAvailableTime2(String availableTime2) {
		this.availableTime2 = availableTime2;
	}
	
	
}
