package cn.com.shopec.mapi.car.vo;

import java.io.Serializable;
import java.util.Date;

public class PrebookVo implements Serializable{

	/** 
	*
	* @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么) 
	*/ 
	private static final long serialVersionUID = 1L;

	private int minute;
	private Date prebookTime ;
	public int getMinute() {
		return minute;
	}
	public void setMinute(int minute) {
		this.minute = minute;
	}
	public Date getPrebookTime() {
		return prebookTime;
	}
	public void setPrebookTime(Date prebookTime) {
		this.prebookTime = prebookTime;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@Override
	public String toString() {
		return "PrebookVo [minute=" + minute + ", prebookTime=" + prebookTime + "]";
	}
	
}
