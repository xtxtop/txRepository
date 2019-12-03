package cn.com.shopec.core.marketing.vo;

public class PricingPackageStat {

	//销售数
	private Integer salesNumber;
	//销售额
	private Double salesAmount;
	//充值额
	private Double topUpAmount;
	//报表查询时间
	private String time;
	
	public Integer getSalesNumber() {
		return salesNumber;
	}
	public void setSalesNumber(Integer salesNumber) {
		this.salesNumber = salesNumber;
	}
	public Double getSalesAmount() {
		return salesAmount;
	}
	public void setSalesAmount(Double salesAmount) {
		this.salesAmount = salesAmount;
	}
	public Double getTopUpAmount() {
		return topUpAmount;
	}
	public void setTopUpAmount(Double topUpAmount) {
		this.topUpAmount = topUpAmount;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	
	
}