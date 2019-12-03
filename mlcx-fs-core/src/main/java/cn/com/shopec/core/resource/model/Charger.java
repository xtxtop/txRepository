package cn.com.shopec.core.resource.model;

import java.util.Date;

import cn.com.shopec.core.common.Entity;

/** 
 * 充电桩表 数据实体类
 */
public class Charger extends Entity<String> {
	
	private static final long serialVersionUID = 1l;
	
	/*Auto generated properties start*/
	
	//充电桩编号
	private String chargerNo;
	//城市id
	private String cityId;
	//城市
	private String cityName;
	//场站编号
	private String parkNo;
	//场站名称
	private String parkName;
	//充电桩品牌id（具体根据数据字典表）
	private String chargerBrandId;
	//充电桩品牌名称
	private String chargerBrandName;
	//型号id
	private String chargerModelId;
	//型号名称
	private String chargerModelName;
	//功率
	private Double chargerPower;
	//电桩设备串号
	private String chargerSn;
	//电桩类型（1、慢充，2、快充）
	private Integer chargerType;
	//可用状态（0、不可用，1、可用，默认1）
	private Integer isAvailable;
	//启用停用备注
	private String availableMemo;
	//可用状态更新时间
	private Date availableUpdateTime;
	//可用状态更新时间 时间范围起（查询用）
	private Date availableUpdateTimeStart;
	//可用状态更新时间 时间范围止（查询用）
	private Date availableUpdateTimeEnd;	
	//删除标识（0、未删除，1、已删除，默认0）
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
	//操作人类型
	private Integer operatorType;
	//操作人id
	private String operatorId;
	
	/*Auto generated properties end*/
	
	
	
	/*Customized properties start*/
	
	/*Customized properties end*/
	
	
	
	/*Auto generated methods start*/
	
	@Override
	public String getPK(){
		return chargerNo;
	}
	
	public String getChargerNo(){
		return chargerNo;
	}
	
	public void setChargerNo(String chargerNo){
		this.chargerNo = chargerNo;
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
	
	public String getParkName(){
		return parkName;
	}
	
	public void setParkName(String parkName){
		this.parkName = parkName;
	}
	
	public String getChargerBrandId(){
		return chargerBrandId;
	}
	
	public void setChargerBrandId(String chargerBrandId){
		this.chargerBrandId = chargerBrandId;
	}
	
	public String getChargerBrandName(){
		return chargerBrandName;
	}
	
	public void setChargerBrandName(String chargerBrandName){
		this.chargerBrandName = chargerBrandName;
	}
	
	public String getChargerModelId() {
		return chargerModelId;
	}

	public void setChargerModelId(String chargerModelId) {
		this.chargerModelId = chargerModelId;
	}

	public String getChargerModelName() {
		return chargerModelName;
	}

	public void setChargerModelName(String chargerModelName) {
		this.chargerModelName = chargerModelName;
	}

	public Double getChargerPower(){
		return chargerPower;
	}
	
	public void setChargerPower(Double chargerPower){
		this.chargerPower = chargerPower;
	}
	
	public String getChargerSn(){
		return chargerSn;
	}
	
	public void setChargerSn(String chargerSn){
		this.chargerSn = chargerSn;
	}
	
	public Integer getChargerType(){
		return chargerType;
	}
	
	public void setChargerType(Integer chargerType){
		this.chargerType = chargerType;
	}
	
	public Integer getIsAvailable(){
		return isAvailable;
	}
	
	public void setIsAvailable(Integer isAvailable){
		this.isAvailable = isAvailable;
	}
	
	public String getAvailableMemo(){
		return availableMemo;
	}
	
	public void setAvailableMemo(String availableMemo){
		this.availableMemo = availableMemo;
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
	@Override
	public String toString() {
		return "Charger [chargerNo=" + chargerNo + ", cityId=" + cityId + ", cityName=" + cityName + ", parkNo="
				+ parkNo + ", parkName=" + parkName + ", chargerBrandId=" + chargerBrandId + ", chargerBrandName="
				+ chargerBrandName + ", chargerModelId=" + chargerModelId + ", chargerModelName=" + chargerModelName
				+ ", chargerPower=" + chargerPower + ", chargerSn=" + chargerSn + ", chargerType=" + chargerType
				+ ", isAvailable=" + isAvailable + ", availableMemo=" + availableMemo + ", availableUpdateTime="
				+ availableUpdateTime + ", availableUpdateTimeStart=" + availableUpdateTimeStart
				+ ", availableUpdateTimeEnd=" + availableUpdateTimeEnd + ", isDeleted=" + isDeleted + ", createTime="
				+ createTime + ", createTimeStart=" + createTimeStart + ", createTimeEnd=" + createTimeEnd
				+ ", updateTime=" + updateTime + ", updateTimeStart=" + updateTimeStart + ", updateTimeEnd="
				+ updateTimeEnd + ", operatorType=" + operatorType + ", operatorId=" + operatorId + "]";
	}
	
	/*Customized methods end*/
	
	
}
