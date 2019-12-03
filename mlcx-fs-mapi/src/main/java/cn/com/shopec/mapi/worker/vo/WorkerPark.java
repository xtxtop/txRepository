package cn.com.shopec.mapi.worker.vo;


import java.util.Date;

import cn.com.shopec.core.common.Entity;

/** 
 * WorkerOrder调度工单 数据实体类
 */
public class WorkerPark extends Entity<String> {
	
	private static final long serialVersionUID = 1l;
	
	/*Auto generated properties start*/
	//场站编号
	private String parkNo;
	//场站地址
	private String address;
	//当前时间
	private Date nowTime;
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Date getNowTime() {
		return nowTime;
	}
	public void setNowTime(Date nowTime) {
		this.nowTime = nowTime;
	}
	public String getParkNo() {
		return parkNo;
	}
	public void setParkNo(String parkNo) {
		this.parkNo = parkNo;
	}
	@Override
	public String toString() {
		return "WorkerPark [parkNo=" + parkNo + ", address=" + address + ", nowTime=" + nowTime + "]";
	}
	
	
}
