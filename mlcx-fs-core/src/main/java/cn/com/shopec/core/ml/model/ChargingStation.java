package cn.com.shopec.core.ml.model;

import java.util.Date;

import cn.com.shopec.core.common.Entity;

/** 
 * 充电站 数据实体类
 */
public class ChargingStation extends Entity<String> {
	
	private static final long serialVersionUID = 1l;
	
	/*Auto generated properties start*/
	
	//充电站编号
	private String stationNo;
	//充电站名称
	private String stationName;
	//（省）id
	private String provinceId;
	//（市/直辖市区县）id
	private String cityId;
	//（区县）id
	private String districtId;
	//街道
	private String addrStreet;
	//充电桩数
	private Integer chargingPileNumber;
	//备注
	private String memo;
	//充电站状态（0、停用，1、启用，默认1）
	private Integer isAvailable;
	//充电站状态更新时间
	private Date availableUpdateTime;
	//充电站状态更新时间 时间范围起（查询用）
	private Date availableUpdateTimeStart;
	//充电站状态更新时间 时间范围止（查询用）
	private Date availableUpdateTimeEnd;	
	//场站图片url
	private String stationUrl;
	//是否删除（0、未删除，1、已删除，默认0）
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
	//操作人id
	private String operatorId;
	//操作人类型（0、系统自动操作，1、平台人员操作）
	private Integer operatorType;
	//坐标经度
	private String longitude;
	//坐标纬度
	private String latitude;
	//车位数
	private Integer parkingSpaceNumber;
	//场站提供服务（各服务间用半角逗号间隔）
	private String supportedServices;
	//是否公共开放（0，不开放、1，开放，默认1）
	private Integer isPublic;
	//场站电费价格(元/度)
	private Double electricPrice;
	//场站租金(元/月)
	private Double parkRent;
	//营业时间
	private String businessHours;
	//节假日 是否营业（1 是 0 否）
	private String isBusinessFestival;
	//站类型
	private Integer stationType;
	//标签
	private String label;
	//运营城市
	private String operatingCityNo;
	
	/*Auto generated properties end*/
	
	
	
	/*Customized properties start*/
	
	/*Customized properties end*/
	
	
	
	/*Auto generated methods start*/
	
	public String getOperatingCityNo() {
		return operatingCityNo;
	}

	public void setOperatingCityNo(String operatingCityNo) {
		this.operatingCityNo = operatingCityNo;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public Integer getStationType() {
		return stationType;
	}

	public void setStationType(Integer stationType) {
		this.stationType = stationType;
	}

	@Override
	public String getPK(){
		return stationNo;
	}
	
	public String getStationNo(){
		return stationNo;
	}
	
	public void setStationNo(String stationNo){
		this.stationNo = stationNo;
	}
	
	public String getStationName(){
		return stationName;
	}
	
	public void setStationName(String stationName){
		this.stationName = stationName;
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
	
	public Integer getChargingPileNumber(){
		return chargingPileNumber;
	}
	
	public void setChargingPileNumber(Integer chargingPileNumber){
		this.chargingPileNumber = chargingPileNumber;
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
	
	public String getStationUrl(){
		return stationUrl;
	}
	
	public void setStationUrl(String stationUrl){
		this.stationUrl = stationUrl;
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
	
	public Integer getParkingSpaceNumber(){
		return parkingSpaceNumber;
	}
	
	public void setParkingSpaceNumber(Integer parkingSpaceNumber){
		this.parkingSpaceNumber = parkingSpaceNumber;
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
	
	public Double getElectricPrice(){
		return electricPrice;
	}
	
	public void setElectricPrice(Double electricPrice){
		this.electricPrice = electricPrice;
	}
	
	public Double getParkRent(){
		return parkRent;
	}
	
	public void setParkRent(Double parkRent){
		this.parkRent = parkRent;
	}
	
	public String getBusinessHours(){
		return businessHours;
	}
	
	public void setBusinessHours(String businessHours){
		this.businessHours = businessHours;
	}
	
	public String getIsBusinessFestival(){
		return isBusinessFestival;
	}
	
	public void setIsBusinessFestival(String isBusinessFestival){
		this.isBusinessFestival = isBusinessFestival;
	}
	
	
	/*Auto generated methods end*/
	
	
	
	/*Customized methods start*/
	
	/*Customized methods end*/
	
	
	@Override
	public String toString() {
		return "ChargingStation [stationNo=" + stationNo + ", stationName="
				+ stationName + ", provinceId=" + provinceId + ", cityId="
				+ cityId + ", districtId=" + districtId + ", addrStreet="
				+ addrStreet + ", chargingPileNumber=" + chargingPileNumber
				+ ", memo=" + memo + ", isAvailable=" + isAvailable
				+ ", availableUpdateTime=" + availableUpdateTime
				+ ", availableUpdateTimeStart=" + availableUpdateTimeStart
				+ ", availableUpdateTimeEnd=" + availableUpdateTimeEnd
				+ ", stationUrl=" + stationUrl + ", isDeleted=" + isDeleted
				+ ", createTime=" + createTime + ", createTimeStart="
				+ createTimeStart + ", createTimeEnd=" + createTimeEnd
				+ ", updateTime=" + updateTime + ", updateTimeStart="
				+ updateTimeStart + ", updateTimeEnd=" + updateTimeEnd
				+ ", operatorId=" + operatorId + ", operatorType="
				+ operatorType + ", longitude=" + longitude + ", latitude="
				+ latitude + ", parkingSpaceNumber=" + parkingSpaceNumber
				+ ", supportedServices=" + supportedServices + ", isPublic="
				+ isPublic + ", electricPrice=" + electricPrice + ", parkRent="
				+ parkRent + ", businessHours=" + businessHours
				+ ", isBusinessFestival=" + isBusinessFestival
				+ ", stationType=" + stationType + ", label=" + label
				+ ", operatingCityNo=" + operatingCityNo + "]";
	}
}
