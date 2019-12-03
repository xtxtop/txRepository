package cn.com.shopec.core.resource.model;

import java.util.Date;

import cn.com.shopec.core.common.Entity;

/** 
 * 车位表 数据实体类
 */
public class ParkingSpace extends Entity<String> {
	
	private static final long serialVersionUID = 1l;
	
	/*Auto generated properties start*/
	
	//车位编号
	private String parkingSpaceNo;
	//城市id（具体来自数据字典表）
	private String cityId;
	//城市名称
	private String cityName;
	//场站编号
	private String parkNo;
	//是否可用（0，不可用、1，可用，默认1）
	private Integer isAvailable;
	//启用状态更新时间
	private Date availableUpdateTime;
	//可用状态更新时间 时间范围起（查询用）
	private Date availableUpdateTimeStart;
	//可用状态更新时间 时间范围止（查询用）
	private Date availableUpdateTimeEnd;	
	//停用状态更新时间
	private Date disabledUpdateTime;
	//停用状态更新时间 时间范围起（查询用）
	private Date disabledUpdateTimeStart;
	//停用状态更新时间 时间范围止（查询用）
	private Date disabledUpdateTimeEnd;
	//是否带电桩（0，不带电桩，1、带电桩）
	private Integer hasCharger;
	//电桩编号
	private String chargerNo;
	//是否已删除（0，未删除、1，已删除，默认0）
	private Integer isDeleted;
	//创建日期
	private Date createTime;
	//创建日期 时间范围起（查询用）
	private Date createTimeStart;
	//创建日期 时间范围止（查询用）
	private Date createTimeEnd;	
	//更新日期
	private Date updateTime;
	//更新日期 时间范围起（查询用）
	private Date updateTimeStart;
	//更新日期 时间范围止（查询用）
	private Date updateTimeEnd;	
	//操作人类型
	private Integer operatorType;
	//操作人id
	private String operatorId;
	
	
	//场站名称
	private String parkName;
	//所属方类型（1、自有，2、租用）
	private Integer ownerType;
	/*Auto generated properties end*/
	
	
	
	/*Customized properties start*/
	
	/*Customized properties end*/
	
	
	
	/*Auto generated methods start*/
	
	@Override
	public String getPK(){
		return parkingSpaceNo;
	}
	
	public String getParkName() {
		return parkName;
	}

	public void setParkName(String parkName) {
		this.parkName = parkName;
	}

	public Integer getOwnerType() {
		return ownerType;
	}

	public void setOwnerType(Integer ownerType) {
		this.ownerType = ownerType;
	}

	public String getParkingSpaceNo(){
		return parkingSpaceNo;
	}
	
	public void setParkingSpaceNo(String parkingSpaceNo){
		this.parkingSpaceNo = parkingSpaceNo;
	}
	
	public String getCityId(){
		return cityId;
	}
	
	public void setCityId(String cityId){
		this.cityId = cityId;
	}
	
	public String getCityName(){
		return cityName;
	}
	
	public void setCityName(String cityName){
		this.cityName = cityName;
	}
	
	public String getParkNo(){
		return parkNo;
	}
	
	public void setParkNo(String parkNo){
		this.parkNo = parkNo;
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
	
	public Integer getHasCharger(){
		return hasCharger;
	}
	
	public void setHasCharger(Integer hasCharger){
		this.hasCharger = hasCharger;
	}
	
	public String getChargerNo(){
		return chargerNo;
	}
	
	public void setChargerNo(String chargerNo){
		this.chargerNo = chargerNo;
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
	
	
	public Date getDisabledUpdateTime() {
		return disabledUpdateTime;
	}

	public void setDisabledUpdateTime(Date disabledUpdateTime) {
		this.disabledUpdateTime = disabledUpdateTime;
	}

	public Date getDisabledUpdateTimeStart() {
		return disabledUpdateTimeStart;
	}

	public void setDisabledUpdateTimeStart(Date disabledUpdateTimeStart) {
		this.disabledUpdateTimeStart = disabledUpdateTimeStart;
	}

	public Date getDisabledUpdateTimeEnd() {
		return disabledUpdateTimeEnd;
	}

	public void setDisabledUpdateTimeEnd(Date disabledUpdateTimeEnd) {
		this.disabledUpdateTimeEnd = disabledUpdateTimeEnd;
	}

	@Override
	public String toString() {
		return "ParkingSpace ["
		 + "parkingSpaceNo = " + parkingSpaceNo + ", cityId = " + cityId + ", cityName = " + cityName + ", parkNo = " + parkNo
		 + ", isAvailable = " + isAvailable + ", availableUpdateTime = " + availableUpdateTime + ", availableUpdateTimeStart = " + availableUpdateTimeStart + ", availableUpdateTimeEnd = " + availableUpdateTimeEnd + ", hasCharger = " + hasCharger + ", chargerNo = " + chargerNo
		 + ", isDeleted = " + isDeleted + ", createTime = " + createTime + ", createTimeStart = " + createTimeStart + ", createTimeEnd = " + createTimeEnd + ", updateTime = " + updateTime + ", updateTimeStart = " + updateTimeStart + ", updateTimeEnd = " + updateTimeEnd + ", operatorType = " + operatorType
		 + ", operatorId = " + operatorId
		+"]";
	}
}
