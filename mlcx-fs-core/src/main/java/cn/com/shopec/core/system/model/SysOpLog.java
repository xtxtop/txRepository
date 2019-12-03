package cn.com.shopec.core.system.model;


import java.util.Date;

import cn.com.shopec.core.common.Entity;



/**
 * SysOpLog 数据库实体类
 */
public class SysOpLog extends Entity<String> {
	
	private static final long serialVersionUID = 1l;
	
	//日志id
	private String logId;
	//系统类型，说明是在哪个系统操作产生的日志（0、运营平台，1、商家后台，2、商城前台）
	private Integer systemType;
	//操作类型（C，新建、U，更新、D，删除、O，其他操作/组合操作）
	private String opType;
	//模块名称
	private String moduleName;
	//日志信息（简单信息）
	private String logMsg;
	//备注
	private String memo;
	//业务对象（跟业务对象有关的操作时，可记录到该字段，如修改订单，则业务对象类型为订单，具体见编码见字典）
	private String bizObjType;
	//业务对象id（跟业务对象有关的操作，可记录该字段，对应具体业务对象的id值）
	private String bizObjId;
	//创建时间
	private Date createTime;
	//操作人类型（0、系统自动操作，1、平台人员操作，2、商家人员操作，3、会员操作）
	private Integer operatorType;
	//操作人id（根据操作人类型会对应不同的表记录）
	private String operatorId;
	//操作人用户名（根据操作人类型会对应不同的用户名）
	private String operatorUserName;
	//用来查询时间范围
	private Date startCreateTime;
	//用来查询时间范围
	private Date endCreateTime;
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
	public Integer getSystemType(){
		return systemType;
	}
	
	public void setSystemType(Integer systemType){
		this.systemType = systemType;
	}
	public String getOpType(){
		return opType;
	}
	
	public void setOpType(String opType){
		this.opType = opType;
	}
	public String getModuleName(){
		return moduleName;
	}
	
	public void setModuleName(String moduleName){
		this.moduleName = moduleName;
	}
	public String getLogMsg(){
		return logMsg;
	}
	
	public void setLogMsg(String logMsg){
		this.logMsg = logMsg;
	}
	public String getMemo(){
		return memo;
	}
	
	public void setMemo(String memo){
		this.memo = memo;
	}
	public String getBizObjType(){
		return bizObjType;
	}
	
	public void setBizObjType(String bizObjType){
		this.bizObjType = bizObjType;
	}
	public String getBizObjId(){
		return bizObjId;
	}
	
	public void setBizObjId(String bizObjId){
		this.bizObjId = bizObjId;
	}
	public Date getCreateTime(){
		return createTime;
	}
	
	public void setCreateTime(Date createTime){
		this.createTime = createTime;
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
	public String getOperatorUserName(){
		return operatorUserName;
	}
	
	public void setOperatorUserName(String operatorUserName){
		this.operatorUserName = operatorUserName;
	}

	public Date getStartCreateTime() {
		return startCreateTime;
	}

	public void setStartCreateTime(Date startCreateTime) {
		this.startCreateTime = startCreateTime;
	}

	public Date getEndCreateTime() {
		return endCreateTime;
	}

	public void setEndCreateTime(Date endCreateTime) {
		this.endCreateTime = endCreateTime;
	}
	
}
