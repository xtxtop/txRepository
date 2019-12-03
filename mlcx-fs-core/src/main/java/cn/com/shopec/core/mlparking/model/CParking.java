package cn.com.shopec.core.mlparking.model;

import java.util.Date;

import cn.com.shopec.core.common.Entity;

/** 
 * 停车场 数据实体类
 */
public class CParking extends Entity<String> {
	
	private static final long serialVersionUID = 1l;
	
	/*Auto generated properties start*/
	
	//停车场id
	private String parkingNo;
	//停车场名称
	private String parkingName;
	//省id
	private String provinceId;
	//市id
	private String cityId;
	//(区县)id
	private String districtId;
	//街道
	private String addrStreet;
	//总车位数
	private Integer parkingSpaceNumber;
	//剩余车位数
	private Integer surplusSpaceNumber;
	//停车场类型(0.闸机 1.地锁,2无设备)
	private Integer parkingType;
	//停车场状态(0.使用 1.停用)
	private Integer parkingStatus;
	//是否删除(0删除 1未删除)
	private Integer isDeleted;
	//坐标经度
	private String longitude;
	//坐标纬度
	private String latitude;
	//场站提供服务（各服务间用半角逗号间隔）
	private String supportedServices;
	//是否公共开放（0.不开放、1.开放）
	private Integer isPublic;
	//开放时间
	private String businessHours;
	//节假日 是否营业（1 是 0 否）
	private Integer isBusinessFestival;
	//标签(各标签间用半角逗号间隔）
	private String label;
	//运营城市
	private String operatingCityNo;
	//楼层数
	private Integer pliesNumber;
	//计费规则编号
	private String billingSchemeNo;
	//在线状态(0.在线 1.离线)
	private Integer onlineStatus;
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
	//操作人id
	private String operatorId;
	//操作人类型（0、系统自动操作，1、平台人员操作）
	private Integer operatorType;
	//设备类型(0.单闸机 1.多闸机 2.地锁型)
	private Integer type;
	//地下停车场楼层数
	private Integer undergroundNumber;
	//地下总车位
	private Integer undergroundParkingSpaceNumber;
	//地下剩余车位
	private Integer undergroundSurplusSpaceNumber;
	//地面停车场楼层数
	private Integer groundNumber;
	//地面总车位
	private Integer groundParkingSpaceNumber;
	//地面剩余车位
	private Integer groundSurplusSpaceNumber;
	//多入口层数(楼层)
	private String muchPliesNumber;
	/*Auto generated properties end*/
	
	
	
	/*Customized properties start*/
	
	/*Customized properties end*/
	
	
	
	/*Auto generated methods start*/
	
	@Override
	public String getPK(){
		return parkingNo;
	}
	
	public String getMuchPliesNumber() {
		return muchPliesNumber;
	}

	public void setMuchPliesNumber(String muchPliesNumber) {
		this.muchPliesNumber = muchPliesNumber;
	}

	public String getParkingNo(){
		return parkingNo;
	}
	
	public void setParkingNo(String parkingNo){
		this.parkingNo = parkingNo;
	}
	
	public String getParkingName(){
		return parkingName;
	}
	
	public void setParkingName(String parkingName){
		this.parkingName = parkingName;
	}
	
	public String getProvinceId(){
		return provinceId;
	}
	
	public void setProvinceId(String provinceId){
		this.provinceId = provinceId;
	}
	
	public String getCityId(){
		return cityId;
	}
	
	public void setCityId(String cityId){
		this.cityId = cityId;
	}
	
	public String getDistrictId(){
		return districtId;
	}
	
	public void setDistrictId(String districtId){
		this.districtId = districtId;
	}
	
	public String getAddrStreet(){
		return addrStreet;
	}
	
	public void setAddrStreet(String addrStreet){
		this.addrStreet = addrStreet;
	}
	
	public Integer getParkingSpaceNumber(){
		return parkingSpaceNumber;
	}
	
	public void setParkingSpaceNumber(Integer parkingSpaceNumber){
		this.parkingSpaceNumber = parkingSpaceNumber;
	}
	
	public Integer getSurplusSpaceNumber(){
		return surplusSpaceNumber;
	}
	
	public void setSurplusSpaceNumber(Integer surplusSpaceNumber){
		this.surplusSpaceNumber = surplusSpaceNumber;
	}
	
	public Integer getParkingType(){
		return parkingType;
	}
	
	public void setParkingType(Integer parkingType){
		this.parkingType = parkingType;
	}
	
	public Integer getParkingStatus(){
		return parkingStatus;
	}
	
	public void setParkingStatus(Integer parkingStatus){
		this.parkingStatus = parkingStatus;
	}
	
	public Integer getIsDeleted(){
		return isDeleted;
	}
	
	public void setIsDeleted(Integer isDeleted){
		this.isDeleted = isDeleted;
	}
	
	public String getLongitude(){
		return longitude;
	}
	
	public void setLongitude(String longitude){
		this.longitude = longitude;
	}
	
	public String getLatitude(){
		return latitude;
	}
	
	public void setLatitude(String latitude){
		this.latitude = latitude;
	}
	
	public String getSupportedServices(){
		return supportedServices;
	}
	
	public void setSupportedServices(String supportedServices){
		this.supportedServices = supportedServices;
	}
	
	public Integer getIsPublic(){
		return isPublic;
	}
	
	public void setIsPublic(Integer isPublic){
		this.isPublic = isPublic;
	}
	
	public String getBusinessHours(){
		return businessHours;
	}
	
	public void setBusinessHours(String businessHours){
		this.businessHours = businessHours;
	}
	
	public Integer getIsBusinessFestival(){
		return isBusinessFestival;
	}
	
	public void setIsBusinessFestival(Integer isBusinessFestival){
		this.isBusinessFestival = isBusinessFestival;
	}
	
	public String getLabel(){
		return label;
	}
	
	public void setLabel(String label){
		this.label = label;
	}
	
	public String getOperatingCityNo(){
		return operatingCityNo;
	}
	
	public void setOperatingCityNo(String operatingCityNo){
		this.operatingCityNo = operatingCityNo;
	}
	
	public Integer getPliesNumber(){
		return pliesNumber;
	}
	
	public void setPliesNumber(Integer pliesNumber){
		this.pliesNumber = pliesNumber;
	}
	
	public String getBillingSchemeNo(){
		return billingSchemeNo;
	}
	
	public void setBillingSchemeNo(String billingSchemeNo){
		this.billingSchemeNo = billingSchemeNo;
	}
	
	public Integer getOnlineStatus(){
		return onlineStatus;
	}
	
	public void setOnlineStatus(Integer onlineStatus){
		this.onlineStatus = onlineStatus;
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
	
	public Integer getType(){
		return type;
	}
	
	public void setType(Integer type){
		this.type = type;
	}
	
	public Integer getUndergroundNumber(){
		return undergroundNumber;
	}
	
	public void setUndergroundNumber(Integer undergroundNumber){
		this.undergroundNumber = undergroundNumber;
	}
	
	public Integer getUndergroundParkingSpaceNumber(){
		return undergroundParkingSpaceNumber;
	}
	
	public void setUndergroundParkingSpaceNumber(Integer undergroundParkingSpaceNumber){
		this.undergroundParkingSpaceNumber = undergroundParkingSpaceNumber;
	}
	
	public Integer getUndergroundSurplusSpaceNumber(){
		return undergroundSurplusSpaceNumber;
	}
	
	public void setUndergroundSurplusSpaceNumber(Integer undergroundSurplusSpaceNumber){
		this.undergroundSurplusSpaceNumber = undergroundSurplusSpaceNumber;
	}
	
	public Integer getGroundNumber(){
		return groundNumber;
	}
	
	public void setGroundNumber(Integer groundNumber){
		this.groundNumber = groundNumber;
	}
	
	public Integer getGroundParkingSpaceNumber(){
		return groundParkingSpaceNumber;
	}
	
	public void setGroundParkingSpaceNumber(Integer groundParkingSpaceNumber){
		this.groundParkingSpaceNumber = groundParkingSpaceNumber;
	}
	
	public Integer getGroundSurplusSpaceNumber(){
		return groundSurplusSpaceNumber;
	}
	
	public void setGroundSurplusSpaceNumber(Integer groundSurplusSpaceNumber){
		this.groundSurplusSpaceNumber = groundSurplusSpaceNumber;
	}
	
	
	/*Auto generated methods end*/
	
	
	
	/*Customized methods start*/
	
	/*Customized methods end*/
	
	
	@Override
	public String toString() {
		return "CParking [parkingNo=" + parkingNo + ", parkingName="
				+ parkingName + ", provinceId=" + provinceId + ", cityId="
				+ cityId + ", districtId=" + districtId + ", addrStreet="
				+ addrStreet + ", parkingSpaceNumber=" + parkingSpaceNumber
				+ ", surplusSpaceNumber=" + surplusSpaceNumber
				+ ", parkingType=" + parkingType + ", parkingStatus="
				+ parkingStatus + ", isDeleted=" + isDeleted + ", longitude="
				+ longitude + ", latitude=" + latitude + ", supportedServices="
				+ supportedServices + ", isPublic=" + isPublic
				+ ", businessHours=" + businessHours + ", isBusinessFestival="
				+ isBusinessFestival + ", label=" + label
				+ ", operatingCityNo=" + operatingCityNo + ", pliesNumber="
				+ pliesNumber + ", billingSchemeNo=" + billingSchemeNo
				+ ", onlineStatus=" + onlineStatus + ", createTime="
				+ createTime + ", createTimeStart=" + createTimeStart
				+ ", createTimeEnd=" + createTimeEnd + ", updateTime="
				+ updateTime + ", updateTimeStart=" + updateTimeStart
				+ ", updateTimeEnd=" + updateTimeEnd + ", operatorId="
				+ operatorId + ", operatorType=" + operatorType + ", type="
				+ type + ", undergroundNumber=" + undergroundNumber
				+ ", undergroundParkingSpaceNumber="
				+ undergroundParkingSpaceNumber
				+ ", undergroundSurplusSpaceNumber="
				+ undergroundSurplusSpaceNumber + ", groundNumber="
				+ groundNumber + ", groundParkingSpaceNumber="
				+ groundParkingSpaceNumber + ", groundSurplusSpaceNumber="
				+ groundSurplusSpaceNumber + ", muchPliesNumber="
				+ muchPliesNumber + "]";
	}
}
