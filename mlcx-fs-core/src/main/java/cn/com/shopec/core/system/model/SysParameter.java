package cn.com.shopec.core.system.model;

import java.util.Date;

import cn.com.shopec.core.common.Entity;

/**
 * 参数实体
 * 
 * @author 许瑞伟
 *
 */
public class SysParameter extends Entity<String> {
	
	private static final long serialVersionUID = 1l;
	
	//参数id
	private String paramId;
	//参数组
	private String paramGroup;
	//类型
	private String paramType;
	//参数名称（唯一）
	private String paramName;
	//参数键名（唯一）
	private String paramKey;
	//参数值
	private String paramValue;
	//备注
	private String memo;
	//参数值类型
	private String paramValueType;
	//参数值方式
	private String paramValueMode;
	//参数值JSON集合
	private String paramValueJson;
	//序号
	private Integer seq;
	//是否已删除（1、已删除，0、未删除，默认0）
	private Integer isDeleted;
	//创建时间
	private Date createTime;
	//更新时间
	private Date updateTime;
	//操作人类型（0、系统自动操作，1、平台人员操作，2、商家人员操作，3、会员操作）
	private Integer operatorType;
	//操作人id（根据操作人类型会对应不同的表记录）
	private String operatorId;
	
	@Override
	public String getPK(){
		return paramId;
	}
	
	public String getParamId(){
		return paramId;
	}
	
	public void setParamId(String paramId){
		this.paramId = paramId;
	}

	public String getParamGroup() {
		return paramGroup;
	}

	public void setParamGroup(String paramGroup) {
		this.paramGroup = paramGroup;
	}

	public String getParamType() {
		return paramType;
	}

	public void setParamType(String paramType) {
		this.paramType = paramType;
	}

	public String getParamName() {
		return paramName;
	}

	public void setParamName(String paramName) {
		this.paramName = paramName;
	}

	public String getParamKey() {
		return paramKey;
	}

	public void setParamKey(String paramKey) {
		this.paramKey = paramKey;
	}

	public String getParamValue() {
		return paramValue;
	}

	public void setParamValue(String paramValue) {
		this.paramValue = paramValue;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getParamValueType() {
		return paramValueType;
	}

	public void setParamValueType(String paramValueType) {
		this.paramValueType = paramValueType;
	}

	public String getParamValueMode() {
		return paramValueMode;
	}

	public void setParamValueMode(String paramValueMode) {
		this.paramValueMode = paramValueMode;
	}

	public String getParamValueJson() {
		return paramValueJson;
	}

	public void setParamValueJson(String paramValueJson) {
		this.paramValueJson = paramValueJson;
	}		

	public Integer getSeq() {
		return seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	public Integer getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Integer isDeleted) {
		this.isDeleted = isDeleted;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Integer getOperatorType() {
		return operatorType;
	}

	public void setOperatorType(Integer operatorType) {
		this.operatorType = operatorType;
	}

	public String getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(String operatorId) {
		this.operatorId = operatorId;
	}
	
}
