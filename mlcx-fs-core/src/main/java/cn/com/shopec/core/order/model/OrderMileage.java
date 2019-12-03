package cn.com.shopec.core.order.model;

import java.util.Date;

import cn.com.shopec.core.common.Entity;

/** 
 * OrderMileage 数据实体类
 */
public class OrderMileage extends Entity<Date> {
	
	private static final long serialVersionUID = 1l;
	
	/*Auto generated properties start*/
	
	//订单编号
	private String orderNo;
	//订单里程所属期
	private Date orderMileageDate;
	//订单里程所属日期 时间范围起（查询用）
	private Date orderMileageDateStart;
	//订单里程所属日期 时间范围止（查询用）
	private Date orderMileageDateEnd;	
	//当日订单开始里程
	private Double orderStartMileage;
	//当日订单结束时里程
	private Double orderFinishMileage;
	//当日里程
	private Double mileage;
	//里程金额
	private Double mileageAmount;
	//当天订单结束时间
	private Date orderStartMinute;
	//当天订单结束时间
	private Date orderEndMinute;
	//当日时长
	private Integer minutes;
	//时长金额
	private Double minutesAmount;
	//当天订单金额
	private Double orderAmountOfDay;
	//创建时间
	private Date createTime;
	//创建时间 时间范围起（查询用）
	private Date createTimeStart;
	//创建时间 时间范围止（查询用）
	private Date createTimeEnd;	
	//修改时间
	private Date updateTime;
	//修改时间 时间范围起（查询用）
	private Date updateTimeStart;
	//修改时间 时间范围止（查询用）
	private Date updateTimeEnd;	
	//操作人id
	private String operatorId;
	//操作人类型
	private Integer operatorType;
	//免费分钟内的里程数
	private Double freeMileage;
	//高峰时段费用信息
	private String pearTimeCost;
	//跨天的高峰时段的下一天开始费用信息
	private String pearTimeNextDayCost;
	/*Auto generated properties end*/
	
	
	
	/*Customized properties start*/
	
	/*Customized properties end*/
	
	
	
	/*Auto generated methods start*/
	
	@Override
	public Date getPK(){
		return orderMileageDate;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public Date getOrderMileageDate() {
		return orderMileageDate;
	}

	public void setOrderMileageDate(Date orderMileageDate) {
		this.orderMileageDate = orderMileageDate;
	}

	public Date getOrderMileageDateStart() {
		return orderMileageDateStart;
	}

	public void setOrderMileageDateStart(Date orderMileageDateStart) {
		this.orderMileageDateStart = orderMileageDateStart;
	}

	public Date getOrderMileageDateEnd() {
		return orderMileageDateEnd;
	}

	public void setOrderMileageDateEnd(Date orderMileageDateEnd) {
		this.orderMileageDateEnd = orderMileageDateEnd;
	}

	public Double getOrderStartMileage() {
		return orderStartMileage;
	}

	public void setOrderStartMileage(Double orderStartMileage) {
		this.orderStartMileage = orderStartMileage;
	}

	public Double getOrderFinishMileage() {
		return orderFinishMileage;
	}

	public void setOrderFinishMileage(Double orderFinishMileage) {
		this.orderFinishMileage = orderFinishMileage;
	}

	public Double getMileage() {
		return mileage;
	}
	public void setMileage(Double mileage) {
		this.mileage = mileage;
	}
	public Double getMileageAmount() {
		return mileageAmount;
	}
	public void setMileageAmount(Double mileageAmount) {
		this.mileageAmount = mileageAmount;
	}
	public Date getOrderStartMinute() {
		return orderStartMinute;
	}
	public void setOrderStartMinute(Date orderStartMinute) {
		this.orderStartMinute = orderStartMinute;
	}
	public Date getOrderEndMinute() {
		return orderEndMinute;
	}
	public void setOrderEndMinute(Date orderEndMinute) {
		this.orderEndMinute = orderEndMinute;
	}
	public Integer getMinutes() {
		return minutes;
	}

	public void setMinutes(Integer minutes) {
		this.minutes = minutes;
	}

	public Double getMinutesAmount() {
		return minutesAmount;
	}

	public void setMinutesAmount(Double minutesAmount) {
		this.minutesAmount = minutesAmount;
	}

	public Double getOrderAmountOfDay() {
		return orderAmountOfDay;
	}
	public void setOrderAmountOfDay(Double orderAmountOfDay) {
		this.orderAmountOfDay = orderAmountOfDay;
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
	public Double getFreeMileage() {
		return freeMileage;
	}

	public void setFreeMileage(Double freeMileage) {
		this.freeMileage = freeMileage;
	}


	public String getPearTimeCost() {
		return pearTimeCost;
	}

	public void setPearTimeCost(String pearTimeCost) {
		this.pearTimeCost = pearTimeCost;
	}

	public String getPearTimeNextDayCost() {
		return pearTimeNextDayCost;
	}

	public void setPearTimeNextDayCost(String pearTimeNextDayCost) {
		this.pearTimeNextDayCost = pearTimeNextDayCost;
	}

	@Override
	public String toString() {
		return "OrderMileage [orderNo=" + orderNo + ", orderMileageDate=" + orderMileageDate
				+ ", orderMileageDateStart=" + orderMileageDateStart + ", orderMileageDateEnd=" + orderMileageDateEnd
				+ ", orderStartMileage=" + orderStartMileage + ", orderFinishMileage=" + orderFinishMileage
				+ ", mileage=" + mileage + ", mileageAmount=" + mileageAmount + ", orderStartMinute=" + orderStartMinute
				+ ", orderEndMinute=" + orderEndMinute + ", minutes=" + minutes + ", minutesAmount=" + minutesAmount
				+ ", orderAmountOfDay=" + orderAmountOfDay + ", createTime=" + createTime + ", createTimeStart="
				+ createTimeStart + ", createTimeEnd=" + createTimeEnd + ", updateTime=" + updateTime
				+ ", updateTimeStart=" + updateTimeStart + ", updateTimeEnd=" + updateTimeEnd + ", operatorId="
				+ operatorId + ", operatorType=" + operatorType + "]";
	}

	
	
	/*Auto generated methods end*/
	
	
	
	/*Customized methods start*/
	
	/*Customized methods end*/
	
	
	
}
