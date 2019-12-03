package cn.com.shopec.core.marketing.model;

import java.io.Serializable;
import java.util.Date;

import cn.com.shopec.core.common.Entity;

/** 
 * 
 */
public class PeakHoursCost implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 404171854331204847L;
	//时段费用所属时段
	private String timePeriod;
	//时段开始时间
	private Date timePeriodStartTime;
	//时段结束时间
	private Date timePeriodEndTime;
	//时间段的时长
	private Integer timePeriodMinutes;
	//所属时段时长费用
	private Double timePeriodMinutesAmount;
	//所属时段开始里程
	private Double timePeriodStartMileage;
	//所属时段结束里程
	private Double timePeriodEndMileage;
	//时间段的里程
	private Double timePeriodMileage;
	//所属时段里程费用
	private Double timePeriodMileageAmount;
	
	public String getTimePeriod() {
		return timePeriod;
	}
	public void setTimePeriod(String timePeriod) {
		this.timePeriod = timePeriod;
	}
	public Date getTimePeriodStartTime() {
		return timePeriodStartTime;
	}
	public void setTimePeriodStartTime(Date timePeriodStartTime) {
		this.timePeriodStartTime = timePeriodStartTime;
	}
	public Date getTimePeriodEndTime() {
		return timePeriodEndTime;
	}
	public void setTimePeriodEndTime(Date timePeriodEndTime) {
		this.timePeriodEndTime = timePeriodEndTime;
	}
	public Integer getTimePeriodMinutes() {
		return timePeriodMinutes;
	}
	public void setTimePeriodMinutes(Integer timePeriodMinutes) {
		this.timePeriodMinutes = timePeriodMinutes;
	}
	public Double getTimePeriodMinutesAmount() {
		return timePeriodMinutesAmount;
	}
	public void setTimePeriodMinutesAmount(Double timePeriodMinutesAmount) {
		this.timePeriodMinutesAmount = timePeriodMinutesAmount;
	}
	public Double getTimePeriodStartMileage() {
		return timePeriodStartMileage;
	}
	public void setTimePeriodStartMileage(Double timePeriodStartMileage) {
		this.timePeriodStartMileage = timePeriodStartMileage;
	}
	public Double getTimePeriodEndMileage() {
		return timePeriodEndMileage;
	}
	public void setTimePeriodEndMileage(Double timePeriodEndMileage) {
		this.timePeriodEndMileage = timePeriodEndMileage;
	}
	public Double getTimePeriodMileage() {
		return timePeriodMileage;
	}
	public void setTimePeriodMileage(Double timePeriodMileage) {
		this.timePeriodMileage = timePeriodMileage;
	}
	public Double getTimePeriodMileageAmount() {
		return timePeriodMileageAmount;
	}
	public void setTimePeriodMileageAmount(Double timePeriodMileageAmount) {
		this.timePeriodMileageAmount = timePeriodMileageAmount;
	}
	
}
