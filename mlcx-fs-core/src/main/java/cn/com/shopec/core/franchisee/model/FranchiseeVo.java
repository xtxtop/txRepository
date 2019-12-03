package cn.com.shopec.core.franchisee.model;

public class FranchiseeVo {
	// 加盟商编号
	private String franchiseeNo;
	// 加盟商名称
	private String franchiseeName;
	// 结算周期
	private String cycle;
	//订单号
	private String orderNo;
	// 订单数量
	private Integer orderNumber;
	// 订单总金额
	private Double orderAmount;
	// 分润总额
	private Double profitAmount;

	
	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getFranchiseeNo() {
		return franchiseeNo;
	}

	public void setFranchiseeNo(String franchiseeNo) {
		this.franchiseeNo = franchiseeNo;
	}

	public String getFranchiseeName() {
		return franchiseeName;
	}

	public void setFranchiseeName(String franchiseeName) {
		this.franchiseeName = franchiseeName;
	}

	public String getCycle() {
		return cycle;
	}

	public void setCycle(String cycle) {
		this.cycle = cycle;
	}

	public Integer getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(Integer orderNumber) {
		this.orderNumber = orderNumber;
	}

	public Double getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(Double orderAmount) {
		this.orderAmount = orderAmount;
	}

	public Double getProfitAmount() {
		return profitAmount;
	}

	public void setProfitAmount(Double profitAmount) {
		this.profitAmount = profitAmount;
	}

}
