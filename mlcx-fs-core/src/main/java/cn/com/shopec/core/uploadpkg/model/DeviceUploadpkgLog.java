package cn.com.shopec.core.uploadpkg.model;

import java.util.Date;

import cn.com.shopec.core.common.Entity;

/** 
 * DeviceUploadpkgLog 数据实体类
 */
public class DeviceUploadpkgLog extends Entity<String> {
	
	private static final long serialVersionUID = 1l;
	
	/*Auto generated properties start*/
	
	//硬件上传数据日志id
	private String logId;
	//硬件序列号
	private String deviceSn;
	//指令类型
	private String cmdType;
	//日志中文内容
	private String chiniseContent;
	//日志原文内容
	private String logContent;
	//操作人类型
	private Integer operatorType;
	//操作人id
	private String operatorId;
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
	
	//车牌号
	private String carPlateNo;
	
	
	/*Auto generated properties end*/
	
	
	
	/*Customized properties start*/
	
	/*Customized properties end*/
	
	
	
	/*Auto generated methods start*/
	
	

	@Override
	public String getPK(){
		return logId;
	}
	
	public String getLogId(){
		return logId;
	}
	
	public void setLogId(String logId){
		this.logId = logId;
	}
	
	public String getDeviceSn(){
		return deviceSn;
	}
	
	public void setDeviceSn(String deviceSn){
		this.deviceSn = deviceSn;
	}
	
	public String getCmdType() {
		return cmdType;
	}

	public void setCmdType(String cmdType) {
		this.cmdType = cmdType;
	}

	public String getChiniseContent() {
		return chiniseContent;
	}

	public void setChiniseContent(String chiniseContent) {
		this.chiniseContent = chiniseContent;
	}

	public String getLogContent(){
		return logContent;
	}
	
	public void setLogContent(String logContent){
		this.logContent = logContent;
	}
	
	public Integer getOperatorType(){
		return operatorType;
	}
	
	public void setOperatorType(Integer operatorType){
		this.operatorType = operatorType;
	}
	
	public String getOperatorId(){
		return operatorId;
	}
	
	public void setOperatorId(String operatorId){
		this.operatorId = operatorId;
	}
	
	public Date getCreateTime(){
		return createTime;
	}
	
	public void setCreateTime(Date createTime){
		this.createTime = createTime;
	}
	
	public Date getCreateTimeStart(){
		return createTimeStart;
	}
	
	public void setCreateTimeStart(Date createTimeStart){
		this.createTimeStart = createTimeStart;
	}
	
	public Date getCreateTimeEnd(){
		return createTimeEnd;
	}
	
	public void setCreateTimeEnd(Date createTimeEnd){
		this.createTimeEnd = createTimeEnd;
	}	
	
	public Date getUpdateTime(){
		return updateTime;
	}
	
	public void setUpdateTime(Date updateTime){
		this.updateTime = updateTime;
	}
	
	public Date getUpdateTimeStart(){
		return updateTimeStart;
	}
	
	public void setUpdateTimeStart(Date updateTimeStart){
		this.updateTimeStart = updateTimeStart;
	}
	
	public Date getUpdateTimeEnd(){
		return updateTimeEnd;
	}
	
	public void setUpdateTimeEnd(Date updateTimeEnd){
		this.updateTimeEnd = updateTimeEnd;
	}
	
	/*Auto generated methods end*/
	
	
	
	/*Customized methods start*/
	
	/*Customized methods end*/

	@Override
	public String toString() {
		return "DeviceUploadpkgLog [logId=" + logId + ", deviceSn=" + deviceSn + ", cmdType=" + cmdType
				+ ", chiniseContent=" + chiniseContent + ", logContent=" + logContent + ", operatorType=" + operatorType
				+ ", operatorId=" + operatorId + ", createTime=" + createTime + ", createTimeStart=" + createTimeStart
				+ ", createTimeEnd=" + createTimeEnd + ", updateTime=" + updateTime + ", updateTimeStart="
				+ updateTimeStart + ", updateTimeEnd=" + updateTimeEnd + "]";
	}

	public String getCarPlateNo() {
		return carPlateNo;
	}

	public void setCarPlateNo(String carPlateNo) {
		this.carPlateNo = carPlateNo;
	}	
	
	
}
