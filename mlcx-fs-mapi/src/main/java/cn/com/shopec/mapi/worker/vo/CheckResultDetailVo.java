package cn.com.shopec.mapi.worker.vo;


import cn.com.shopec.core.common.Entity;

/** 
 * 巡检结果表 数据实体类
 */
public class CheckResultDetailVo extends Entity<String> {
	
	private static final long serialVersionUID = 1l;
	
	/*Auto generated properties start*/
	//巡检结果id
	private String checkResultId;
	//巡检计划编号
	private String checkPlanNo;
	//巡检地点
	private String address;
	//巡检项
	private String checkItemId;
	//设备编号
	private String deviceNo;
	//结果
	private Integer checkResult;
	//问题描述
	private String abnormalDetail;
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
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getCheckItemId() {
		return checkItemId;
	}
	public void setCheckItemId(String checkItemId) {
		this.checkItemId = checkItemId;
	}
	public String getDeviceNo() {
		return deviceNo;
	}
	public void setDeviceNo(String deviceNo) {
		this.deviceNo = deviceNo;
	}
	public Integer getCheckResult() {
		return checkResult;
	}
	public void setCheckResult(Integer checkResult) {
		this.checkResult = checkResult;
	}
	public String getAbnormalDetail() {
		return abnormalDetail;
	}
	public void setAbnormalDetail(String abnormalDetail) {
		this.abnormalDetail = abnormalDetail;
	}
	@Override
	public String toString() {
		return "CheckResultDetailVo [checkResultId=" + checkResultId + ", checkPlanNo=" + checkPlanNo + ", address="
				+ address + ", checkItemId=" + checkItemId + ", deviceNo=" + deviceNo + ", checkResult=" + checkResult
				+ ", abnormalDetail=" + abnormalDetail + "]";
	}
	
	
	
	
	
}
