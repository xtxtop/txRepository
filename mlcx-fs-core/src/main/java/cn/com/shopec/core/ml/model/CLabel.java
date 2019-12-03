package cn.com.shopec.core.ml.model;

import java.util.Date;

import cn.com.shopec.core.common.Entity;

/** 
 * 充电站标签表 数据实体类
 */
public class CLabel extends Entity<String> {
	
	private static final long serialVersionUID = 1l;
	
	/*Auto generated properties start*/
	
	//标签ID
	private String labelId;
	//标签名称
	private String labelName;
	//启用状态(0、未启用 1、已启用)
	private Integer enableStatus;
	//创建时间
	private Date createTime;
	//创建时间 时间范围起（查询用）
	private Date createTimeStart;
	//创建时间 时间范围止（查询用）
	private Date createTimeEnd;	
	//更新时间
	private Date updateTime;
	//更新时间 时间范围起（查询用）
	private Date updateTimeStart;
	//更新时间 时间范围止（查询用）
	private Date updateTimeEnd;	
	//操作人类型
	private Integer operatorType;
	//操作人id
	private String operatorId;
	//操作人名称
	private String userName;
	//标签类型(1.充电桩 2.停车场)
	private Integer labelType;
	
	/*Auto generated properties end*/
	
	
	
	/*Customized properties start*/
	
	/*Customized properties end*/
	
	
	
	/*Auto generated methods start*/
	
	public Integer getLabelType() {
		return labelType;
	}

	public void setLabelType(Integer labelType) {
		this.labelType = labelType;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Override
	public String getPK(){
		return labelId;
	}
	
	public String getLabelId(){
		return labelId;
	}
	
	public void setLabelId(String labelId){
		this.labelId = labelId;
	}
	
	public String getLabelName(){
		return labelName;
	}
	
	public void setLabelName(String labelName){
		this.labelName = labelName;
	}
	
	public Integer getEnableStatus(){
		return enableStatus;
	}
	
	public void setEnableStatus(Integer enableStatus){
		this.enableStatus = enableStatus;
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
	
	
	/*Auto generated methods end*/
	
	
	
	/*Customized methods start*/
	
	/*Customized methods end*/
	
	
	@Override
	public String toString() {
		return "CLabel [labelId=" + labelId + ", labelName=" + labelName
				+ ", enableStatus=" + enableStatus + ", createTime="
				+ createTime + ", createTimeStart=" + createTimeStart
				+ ", createTimeEnd=" + createTimeEnd + ", updateTime="
				+ updateTime + ", updateTimeStart=" + updateTimeStart
				+ ", updateTimeEnd=" + updateTimeEnd + ", operatorType="
				+ operatorType + ", operatorId=" + operatorId + ", userName="
				+ userName + ", labelType=" + labelType + "]";
	}
}
