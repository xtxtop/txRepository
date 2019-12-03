package cn.com.shopec.core.system.model;

import java.util.Date;

import cn.com.shopec.core.common.Entity;

/** 
 * 数据字典的明细项表 数据实体类
 */
public class DataDictItem extends Entity<String> {
	
	private static final long serialVersionUID = 1l;
	
	/*Auto generated properties start*/
	
	//数据字典项id
	private String dataDictItemId;
	//数据字典分类编码（如，城市：CITY）
	private String dataDictCatCode;
	//父级项的id
	private String parentItemId;
	//字典项的值
	private String itemValue;
	//备注
	private String memo;
	//是否可用（1、可用，0、不可用，默认1）
	private Integer isAvailable;
	//可用状态更新时间
	private Date availableUpdateTime;
	//可用状态更新时间 时间范围起（查询用）
	private Date availableUpdateTimeStart;
	//可用状态更新时间 时间范围止（查询用）
	private Date availableUpdateTimeEnd;	
	//是否已删除（1、已删除，0、未删除，默认0）
	private Integer isDeleted;
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
	//操作人id
	private String operatorId;
	//操作人类型（0、系统自动操作，1、平台人员操作，2、商家人员操作，3、会员操作）
	private Integer operatorType;
	//扩展信息1
	private  String info1;
	//扩展信息1
	private  String info2;
	//片区Id
	private String regionId;
	//片区名称
	private String  regionName;
	/*Auto generated properties end*/
	
	
	
	/*Customized properties start*/
	
	/*Customized properties end*/
	
	
	
	/*Auto generated methods start*/
	
	@Override
	public String getPK(){
		return dataDictItemId;
	}
	
	public String getDataDictItemId(){
		return dataDictItemId;
	}
	
	public void setDataDictItemId(String dataDictItemId){
		this.dataDictItemId = dataDictItemId;
	}
	
	public String getDataDictCatCode(){
		return dataDictCatCode;
	}
	
	public void setDataDictCatCode(String dataDictCatCode){
		this.dataDictCatCode = dataDictCatCode;
	}
	
	public String getParentItemId(){
		return parentItemId;
	}
	
	public void setParentItemId(String parentItemId){
		this.parentItemId = parentItemId;
	}
	
	public String getItemValue(){
		return itemValue;
	}
	
	public void setItemValue(String itemValue){
		this.itemValue = itemValue;
	}
	
	public String getMemo(){
		return memo;
	}
	
	public void setMemo(String memo){
		this.memo = memo;
	}
	
	public Integer getIsAvailable(){
		return isAvailable;
	}
	
	public void setIsAvailable(Integer isAvailable){
		this.isAvailable = isAvailable;
	}
	
	public Date getAvailableUpdateTime(){
		return availableUpdateTime;
	}
	
	public void setAvailableUpdateTime(Date availableUpdateTime){
		this.availableUpdateTime = availableUpdateTime;
	}
	
	public Date getAvailableUpdateTimeStart(){
		return availableUpdateTimeStart;
	}
	
	public void setAvailableUpdateTimeStart(Date availableUpdateTimeStart){
		this.availableUpdateTimeStart = availableUpdateTimeStart;
	}
	
	public Date getAvailableUpdateTimeEnd(){
		return availableUpdateTimeEnd;
	}
	
	public void setAvailableUpdateTimeEnd(Date availableUpdateTimeEnd){
		this.availableUpdateTimeEnd = availableUpdateTimeEnd;
	}	
	
	public Integer getIsDeleted(){
		return isDeleted;
	}
	
	public void setIsDeleted(Integer isDeleted){
		this.isDeleted = isDeleted;
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
	
	public String getOperatorId(){
		return operatorId;
	}
	
	public void setOperatorId(String operatorId){
		this.operatorId = operatorId;
	}
	
	public Integer getOperatorType(){
		return operatorType;
	}
	
	public void setOperatorType(Integer operatorType){
		this.operatorType = operatorType;
	}
	
	
	/*Auto generated methods end*/
	
	
	
	/*Customized methods start*/
	
	/*Customized methods end*/
	
	
	@Override
	public String toString() {
		return "DataDictItem ["
		 + "dataDictItemId = " + dataDictItemId + ", dataDictCatCode = " + dataDictCatCode + ", parentItemId = " + parentItemId + ", itemValue = " + itemValue
		 + ", memo = " + memo + ", isAvailable = " + isAvailable + ", availableUpdateTime = " + availableUpdateTime + ", availableUpdateTimeStart = " + availableUpdateTimeStart + ", availableUpdateTimeEnd = " + availableUpdateTimeEnd + ", isDeleted = " + isDeleted
		 + ", createTime = " + createTime + ", createTimeStart = " + createTimeStart + ", createTimeEnd = " + createTimeEnd + ", updateTime = " + updateTime + ", updateTimeStart = " + updateTimeStart + ", updateTimeEnd = " + updateTimeEnd + ", operatorId = " + operatorId + ", operatorType = " + operatorType
		+"]";
	}

	public String getInfo1() {
		return info1;
	}

	public void setInfo1(String info1) {
		this.info1 = info1;
	}

	public String getInfo2() {
		return info2;
	}

	public void setInfo2(String info2) {
		this.info2 = info2;
	}

	public String getRegionId() {
		return regionId;
	}

	public void setRegionId(String regionId) {
		this.regionId = regionId;
	}

	public String getRegionName() {
		return regionName;
	}

	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}
}
