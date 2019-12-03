package cn.com.shopec.core.marketing.model;

import java.util.Date;

import cn.com.shopec.core.common.Entity;

/**
 * PricingDeductionRecord 数据实体类
 */
public class PricingDeductionRecord extends Entity<String> {

	private static final long serialVersionUID = 1l;

	/* Auto generated properties start */

	// 主键
	private String id;
	// 会员编号
	private String memberNo;
	// 订单编号
	private String orderNo;
	// 套餐订单编号
	private String packOrderNo;
	// 套餐产品编号
	private String packageId;
	// 抵扣时长
	private Integer deductionCeiling;
	// 抵扣金额
	private Double deductionAmount;
	// 抵扣时间
	private Date deductionTime;
	// 抵扣时间 时间范围起（查询用）
	private Date deductionTimeStart;
	// 抵扣时间 时间范围止（查询用）
	private Date deductionTimeEnd;
	// 创建时间
	private Date createTime;
	// 创建时间 时间范围起（查询用）
	private Date createTimeStart;
	// 创建时间 时间范围止（查询用）
	private Date createTimeEnd;
	// 更新时间
	private Date updateTime;
	// 更新时间 时间范围起（查询用）
	private Date updateTimeStart;
	// 更新时间 时间范围止（查询用）
	private Date updateTimeEnd;
	// 操作人id
	private String operatorId;
	// 操作人类型
	private Integer operatorType;
	// 报表查询信息
	private String count;
	private String sum;
	private String weeks;
	private String yearNs;
	private String monthNs;
	private String timeMonth;
	private String timeDay;

	/* Auto generated properties end */

	/* Customized properties start */

	/* Customized properties end */

	/* Auto generated methods start */

	@Override
	public String getPK() {
		return id;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMemberNo() {
		return memberNo;
	}

	public void setMemberNo(String memberNo) {
		this.memberNo = memberNo;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getPackOrderNo() {
		return packOrderNo;
	}

	public void setPackOrderNo(String packOrderNo) {
		this.packOrderNo = packOrderNo;
	}

	public String getPackageId() {
		return packageId;
	}

	public void setPackageId(String packageId) {
		this.packageId = packageId;
	}

	public Integer getDeductionCeiling() {
		return deductionCeiling;
	}

	public void setDeductionCeiling(Integer deductionCeiling) {
		this.deductionCeiling = deductionCeiling;
	}

	public Double getDeductionAmount() {
		return deductionAmount;
	}

	public void setDeductionAmount(Double deductionAmount) {
		this.deductionAmount = deductionAmount;
	}

	public Date getDeductionTime() {
		return deductionTime;
	}

	public void setDeductionTime(Date deductionTime) {
		this.deductionTime = deductionTime;
	}

	public Date getDeductionTimeStart() {
		return deductionTimeStart;
	}

	public void setDeductionTimeStart(Date deductionTimeStart) {
		this.deductionTimeStart = deductionTimeStart;
	}

	public Date getDeductionTimeEnd() {
		return deductionTimeEnd;
	}

	public void setDeductionTimeEnd(Date deductionTimeEnd) {
		this.deductionTimeEnd = deductionTimeEnd;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getCreateTimeStart() {
		return createTimeStart;
	}

	public void setCreateTimeStart(Date createTimeStart) {
		this.createTimeStart = createTimeStart;
	}

	public Date getCreateTimeEnd() {
		return createTimeEnd;
	}

	public void setCreateTimeEnd(Date createTimeEnd) {
		this.createTimeEnd = createTimeEnd;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Date getUpdateTimeStart() {
		return updateTimeStart;
	}

	public void setUpdateTimeStart(Date updateTimeStart) {
		this.updateTimeStart = updateTimeStart;
	}

	public Date getUpdateTimeEnd() {
		return updateTimeEnd;
	}

	public void setUpdateTimeEnd(Date updateTimeEnd) {
		this.updateTimeEnd = updateTimeEnd;
	}

	public String getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(String operatorId) {
		this.operatorId = operatorId;
	}

	public Integer getOperatorType() {
		return operatorType;
	}

	public void setOperatorType(Integer operatorType) {
		this.operatorType = operatorType;
	}

	/* Auto generated methods end */

	/* Customized methods start */

	/* Customized methods end */

	@Override
	public String toString() {
		return "PricingDeductionRecord [" + "id = " + id + ", memberNo = " + memberNo + ", orderNo = " + orderNo
				+ ", packOrderNo = " + packOrderNo + ", packageId = " + packageId + ", deductionCeiling = "
				+ deductionCeiling + ", deductionTime = " + deductionTime + ", deductionTimeStart = "
				+ deductionTimeStart + ", deductionTimeEnd = " + deductionTimeEnd + ", createTime = " + createTime
				+ ", createTimeStart = " + createTimeStart + ", createTimeEnd = " + createTimeEnd + ", updateTime = "
				+ updateTime + ", updateTimeStart = " + updateTimeStart + ", updateTimeEnd = " + updateTimeEnd
				+ ", operatorId = " + operatorId + ", operatorType = " + operatorType + "]";
	}

	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}

	public String getSum() {
		return sum;
	}

	public void setSum(String sum) {
		this.sum = sum;
	}

	public String getWeeks() {
		return weeks;
	}

	public void setWeeks(String weeks) {
		this.weeks = weeks;
	}

	public String getYearNs() {
		return yearNs;
	}

	public void setYearNs(String yearNs) {
		this.yearNs = yearNs;
	}

	public String getMonthNs() {
		return monthNs;
	}

	public void setMonthNs(String monthNs) {
		this.monthNs = monthNs;
	}

	public String getTimeMonth() {
		return timeMonth;
	}

	public void setTimeMonth(String timeMonth) {
		this.timeMonth = timeMonth;
	}

	public String getTimeDay() {
		return timeDay;
	}

	public void setTimeDay(String timeDay) {
		this.timeDay = timeDay;
	}
}
