package cn.com.shopec.core.ml.vo;

/**
 * 充值记录
 * 
 * @author Administrator
 *
 */
public class AccountRecordVo {
	// 交易类型(1.充电 2.停车)
	private String dealType;
	// 余下金额
	private String balance;
	// 订单编号
	private String orderNo;
	// 订单创建时间
	private String createTime;

	public String getDealType() {
		return dealType;
	}

	public void setDealType(String dealType) {
		this.dealType = dealType;
	}

	public String getBalance() {
		return balance;
	}

	public void setBalance(String balance) {
		this.balance = balance;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

}
