package cn.com.shopec.mapi.order.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import cn.com.shopec.mapi.resource.vo.ParkVOCarStatus;

public class PearTimeCostVo  implements Serializable{

	/** 
	*
	* @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么) 
	*/ 
	private static final long serialVersionUID = 1L;
	//日期
	private String timeDay;
	//所属时间段
	private String timePeriod;
	//时长
	private String timePeriodMinutes;//
	//时长费用
	private String timePeriodMinutesAmount;
	//里程
	private String timePeriodMileage;
	//里程费用
	private String timePeriodMileageAmount;
	public String getTimePeriodMileageAmount() {
		return timePeriodMileageAmount;
	}
	public void setTimePeriodMileageAmount(String timePeriodMileageAmount) {
		this.timePeriodMileageAmount = timePeriodMileageAmount;
	}
	public String getTimePeriodMileage() {
		return timePeriodMileage;
	}
	public void setTimePeriodMileage(String timePeriodMileage) {
		this.timePeriodMileage = timePeriodMileage;
	}
	public String getTimePeriodMinutesAmount() {
		return timePeriodMinutesAmount;
	}
	public void setTimePeriodMinutesAmount(String timePeriodMinutesAmount) {
		this.timePeriodMinutesAmount = timePeriodMinutesAmount;
	}
	public String getTimePeriodMinutes() {
		return timePeriodMinutes;
	}
	public void setTimePeriodMinutes(String timePeriodMinutes) {
		this.timePeriodMinutes = timePeriodMinutes;
	}
	public String getTimePeriod() {
		return timePeriod;
	}
	public void setTimePeriod(String timePeriod) {
		this.timePeriod = timePeriod;
	}
	public String getTimeDay() {
		return timeDay;
	}
	public void setTimeDay(String timeDay) {
		this.timeDay = timeDay;
	}
}
