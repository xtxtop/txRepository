package cn.com.shopec.mapi.worker.vo;

import java.util.Date;
import java.util.List;

import cn.com.shopec.core.common.Entity;

/** 
 * 巡检计划表 数据实体类
 */
public class CheckPlanDetailVo extends Entity<String> {
	
	private static final long serialVersionUID = 1l;
	
	/*Auto generated properties start*/
	
	//巡检计划编号
	private String checkPlanNo;
	//巡检地点
	private String address;
	//计划日期
	private String planDate;
	//计划巡检项名称（各项间用半角逗号进行分割）
	private String checkItem;
	//包含多个人员姓名，各个姓名间用半角逗号进行分割
	private String workerName;
//	//巡检结果列表
//	private List<CheckResultVo> checkResultList;
	public String getCheckPlanNo() {
		return checkPlanNo;
	}
	public void setCheckPlanNo(String checkPlanNo) {
		this.checkPlanNo = checkPlanNo;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPlanDate() {
		return planDate;
	}
	public void setPlanDate(String planDate) {
		this.planDate = planDate;
	}
	public String getCheckItem() {
		return checkItem;
	}
	public void setCheckItem(String checkItem) {
		this.checkItem = checkItem;
	}
	public String getWorkerName() {
		return workerName;
	}
	public void setWorkerName(String workerName) {
		this.workerName = workerName;
	}
//	public List<CheckResultVo> getCheckResultList() {
//		return checkResultList;
//	}
//	public void setCheckResultList(List<CheckResultVo> checkResultList) {
//		this.checkResultList = checkResultList;
//	}
	@Override
	public String toString() {
		return "CheckPlanDetailVo [checkPlanNo=" + checkPlanNo + ", address=" + address + ", planDate=" + planDate
				+ ", checkItem=" + checkItem + ", workerName=" + workerName + "]";
	}
	
	
	
	
	
	
	
}
