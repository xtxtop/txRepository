package cn.com.shopec.core.order.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import cn.com.shopec.core.common.Entity;

/** 
 * 订单运营统计
 */
public class OrderLogctonCountVo extends Entity<String> {
	
	private static final long serialVersionUID = 1l;
	
	/*Auto generated properties start*/
	   
	//取车点场站
	private String logcton;

	private Integer count;

	public String getLogcton() {
		return logcton;
	}

	public void setLogcton(String logcton) {
		this.logcton = logcton;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}
 
}
