package cn.com.shopec.core.quartz.model;

import java.util.Date;

import cn.com.shopec.core.common.Entity;

/**
 * 订单表 数据实体类
 */
public class RunDaily extends Entity<String> {

	private static final long serialVersionUID = 1l;

	/* Auto generated properties start */
	//会员总数(memberNum)，缴费会员数(memberFeeNum)，已认证会员数（memberCensoredNum），日新注册人数(registNum)，
	//日订单数(orderNum)，实际支付单数(alPayOrderNum)，在线支付金额(payAmount)，时间(time)
	private Long memberNum;
	
	private Long memberFeeNum;
	
	private Long memberCensoredNum;
	
	private Long registNum;
	
	private Long orderNum;
	
	private Long alPayOrderNum;
	
	private Double payAmount;
	
	private String time;
	
	private Long curOnlineCarsNum; //当前上线车辆数

	public Long getMemberNum() {
		return memberNum;
	}

	public void setMemberNum(Long memberNum) {
		this.memberNum = memberNum;
	}

	public Long getMemberFeeNum() {
		return memberFeeNum;
	}

	public void setMemberFeeNum(Long memberFeeNum) {
		this.memberFeeNum = memberFeeNum;
	}
	
	public Long getMemberCensoredNum() {
		return memberCensoredNum;
	}

	public void setMemberCensoredNum(Long memberCensoredNum) {
		this.memberCensoredNum = memberCensoredNum;
	}

	public Long getRegistNum() {
		return registNum;
	}

	public void setRegistNum(Long registNum) {
		this.registNum = registNum;
	}

	public Long getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(Long orderNum) {
		this.orderNum = orderNum;
	}

	public Long getAlPayOrderNum() {
		return alPayOrderNum;
	}

	public void setAlPayOrderNum(Long alPayOrderNum) {
		this.alPayOrderNum = alPayOrderNum;
	}

	public Double getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(Double payAmount) {
		this.payAmount = payAmount;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public Long getCurOnlineCarsNum() {
		return curOnlineCarsNum;
	}

	public void setCurOnlineCarsNum(Long curOnlineCarsNum) {
		this.curOnlineCarsNum = curOnlineCarsNum;
	}
	

}
