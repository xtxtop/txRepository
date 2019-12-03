package cn.com.shopec.core.statement.model;

import java.util.Date;

import cn.com.shopec.core.common.Entity;

/**
 * 多业务汇总 资金汇总---包括订单、套餐、缴纳押金和退押金数据
 * @author LiHuan
 * Date 2018年4月11日 上午10:15:05
 */
public class MoreBusinessSummary extends Entity<String> {

	private static final long serialVersionUID = 1L;
	
	private Integer orderNum;//订单数
	
	private Double alreadyAmount;//订单数
	
	private Integer packageRechargeNum;//套餐--充值笔数
	
	private Double packageAlreadyAmount;//套餐--充值实收金额
	
	private Integer  payDepositNum;//押金--交押金笔数
	
	private Double payDepositAmount;//押金--交押金金额
	
	private Integer refundDepositNum;//押金--退押金笔数
	
	private Double refundDepositAmount;//押金--退押金金额
	
	private Date startTime;//汇总开始时间
	
	private Date endTime;//汇总结束时间
	
	private String days;
	private String days1;
	private String days2;
	private String days3;
	private String days4;
	
	private String months;
	private String month1;
	private String month2;
	private String month3;
	private String month4;
	
	private String startMonth;
	private String endMonth;

	public String getMonths() {
		return months;
	}

	public void setMonths(String months) {
		this.months = months;
	}

	public Integer getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}

	public Double getAlreadyAmount() {
		return alreadyAmount;
	}

	public void setAlreadyAmount(Double alreadyAmount) {
		this.alreadyAmount = alreadyAmount;
	}

	public Integer getPackageRechargeNum() {
		return packageRechargeNum;
	}

	public void setPackageRechargeNum(Integer packageRechargeNum) {
		this.packageRechargeNum = packageRechargeNum;
	}

	public Double getPackageAlreadyAmount() {
		return packageAlreadyAmount;
	}

	public void setPackageAlreadyAmount(Double packageAlreadyAmount) {
		this.packageAlreadyAmount = packageAlreadyAmount;
	}

	public Integer getPayDepositNum() {
		return payDepositNum;
	}

	public void setPayDepositNum(Integer payDepositNum) {
		this.payDepositNum = payDepositNum;
	}

	public Double getPayDepositAmount() {
		return payDepositAmount;
	}

	public void setPayDepositAmount(Double payDepositAmount) {
		this.payDepositAmount = payDepositAmount;
	}

	public Integer getRefundDepositNum() {
		return refundDepositNum;
	}

	public void setRefundDepositNum(Integer refundDepositNum) {
		this.refundDepositNum = refundDepositNum;
	}

	public Double getRefundDepositAmount() {
		return refundDepositAmount;
	}

	public void setRefundDepositAmount(Double refundDepositAmount) {
		this.refundDepositAmount = refundDepositAmount;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getDays1() {
		return days1;
	}

	public void setDays1(String days1) {
		this.days1 = days1;
	}

	public String getMonth1() {
		return month1;
	}

	public void setMonth1(String month1) {
		this.month1 = month1;
	}

	public String getMonth2() {
		return month2;
	}

	public void setMonth2(String month2) {
		this.month2 = month2;
	}

	public String getMonth3() {
		return month3;
	}

	public void setMonth3(String month3) {
		this.month3 = month3;
	}

	public String getMonth4() {
		return month4;
	}

	public void setMonth4(String month4) {
		this.month4 = month4;
	}

	public String getStartMonth() {
		return startMonth;
	}

	public void setStartMonth(String startMonth) {
		this.startMonth = startMonth;
	}

	public String getEndMonth() {
		return endMonth;
	}

	public void setEndMonth(String endMonth) {
		this.endMonth = endMonth;
	}

	public String getDays() {
		return days;
	}

	public void setDays(String days) {
		this.days = days;
	}

	public String getDays2() {
		return days2;
	}

	public void setDays2(String days2) {
		this.days2 = days2;
	}

	public String getDays3() {
		return days3;
	}

	public void setDays3(String days3) {
		this.days3 = days3;
	}

	public String getDays4() {
		return days4;
	}

	public void setDays4(String days4) {
		this.days4 = days4;
	}
	
}