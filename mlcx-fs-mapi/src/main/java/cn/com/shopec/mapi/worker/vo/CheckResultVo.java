package cn.com.shopec.mapi.worker.vo;

import java.util.Date;

import cn.com.shopec.core.common.Entity;

/** 
 * 巡检结果表 数据实体类
 */
public class CheckResultVo extends Entity<String> {
	
	private static final long serialVersionUID = 1l;
	
	/*Auto generated properties start*/
	//巡检结果id
	private String checkResultId;
	//巡检计划编号
	private String checkPlanNo;
	//创建日期
	private String createTime;
	//巡检地点
	private String address;
	public String getCheckResultId() {
		return checkResultId;
	}
	public void setCheckResultId(String checkResultId) {
		this.checkResultId = checkResultId;
	}
	public String getCheckPlanNo() {
		return checkPlanNo;
	}
	public void setCheckPlanNo(String checkPlanNo) {
		this.checkPlanNo = checkPlanNo;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	@Override
	public String toString() {
		return "CheckResultVo [checkResultId=" + checkResultId + ", checkPlanNo=" + checkPlanNo + ", createTime="
				+ createTime + ", address=" + address + "]";
	}
	
	
	
	
}
