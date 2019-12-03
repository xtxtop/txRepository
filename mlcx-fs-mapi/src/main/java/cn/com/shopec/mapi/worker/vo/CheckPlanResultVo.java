package cn.com.shopec.mapi.worker.vo;

import java.util.Date;
import java.util.List;

import cn.com.shopec.core.common.Entity;
import cn.com.shopec.core.system.model.DataDictItem;

/** 
 * 巡检计划表 数据实体类
 */
public class CheckPlanResultVo extends Entity<String> {
	
	private static final long serialVersionUID = 1l;
	
	/*Auto generated properties start*/
	
	//巡检计划编号
	private String checkPlanNo;
	//计划日期
	private String planDate;
	//巡检地点
	private String parkAddress;
	//计划状态，0、未完成，1、已完成，2、已取消，默认0
	private Integer planStatus;
	//巡检项类型
	private List<DataDictItemVo> checkItems;
	/*Auto generated properties end*/
	public String getCheckPlanNo() {
		return checkPlanNo;
	}
	public void setCheckPlanNo(String checkPlanNo) {
		this.checkPlanNo = checkPlanNo;
	}
	public String getPlanDate() {
		return planDate;
	}
	public void setPlanDate(String planDate) {
		this.planDate = planDate;
	}
	public String getParkAddress() {
		return parkAddress;
	}
	public void setParkAddress(String parkAddress) {
		this.parkAddress = parkAddress;
	}
	public Integer getPlanStatus() {
		return planStatus;
	}
	public void setPlanStatus(Integer planStatus) {
		this.planStatus = planStatus;
	}
	public List<DataDictItemVo> getCheckItems() {
		return checkItems;
	}
	public void setCheckItems(List<DataDictItemVo> checkItems) {
		this.checkItems = checkItems;
	}
	@Override
	public String toString() {
		return "CheckPlanResultVo [checkPlanNo=" + checkPlanNo + ", planDate=" + planDate + ", parkAddress="
				+ parkAddress + ", planStatus=" + planStatus + ", checkItems=" + checkItems + "]";
	}
	
	
	
	
	
	/*Customized properties start*/
	
	/*Customized properties end*/
	
	
	
	/*Auto generated methods start*/
	
	
	
}
