package cn.com.shopec.core.dailyrental.model;
import java.util.Date;

import cn.com.shopec.core.common.Entity;

/** 
 * DayCarArea 数据实体类
 */
public class DayCarArea extends Entity<String> {
	
	private static final long serialVersionUID = 1l;
	
	/*Auto generated properties start*/
	
	//服务范围iD
	private String carAreaId;
	//服务名称
	private String areaName;
	//城市id
	private String cityId;
	//城市名称
	private String cityName;
	//坐标经度
	private String longitude;
	//坐标纬度
	private String latitude;
	//服务区域状态（0、停用，1、启用，默认0）
	private Integer isAvailable;
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
	//操作id
	private String operatorId;
	//操作类型
	private Integer operatorType;
	//备注
	private String memo;
	//一级行政区（省）id
	private String addrRegion1Id;
	//一级行政区（省）名称
	private String addrRegion1Name;
	//二级行政区（市/直辖市区县）id
	private String addrRegion2Id;
	//三级行政区（区县）id
	private String addrRegion3Id;
	//三级行政区（区县）名称
	private String addrRegion3Name;
	//二级行政区（市/直辖市区县）名称
	private String addrRegion2Name;
	//多边形存值
	private String polygonPoints;
	
	/*Auto generated properties end*/
	
	
	
	/*Customized properties start*/
	
	/*Customized properties end*/
	
	
	
	/*Auto generated methods start*/
	
	@Override
	public String getPK(){
		return carAreaId;
	}
	
	public String getCarAreaId(){
		return carAreaId;
	}
	
	public void setCarAreaId(String carAreaId){
		this.carAreaId = carAreaId;
	}
	
	public String getAreaName(){
		return areaName;
	}
	
	public void setAreaName(String areaName){
		this.areaName = areaName;
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
	
	public Integer getIsAvailable(){
		return isAvailable;
	}
	
	public void setIsAvailable(Integer isAvailable){
		this.isAvailable = isAvailable;
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
	
	public String getMemo(){
		return memo;
	}
	
	public void setMemo(String memo){
		this.memo = memo;
	}
	
	public String getAddrRegion1Id(){
		return addrRegion1Id;
	}
	
	public void setAddrRegion1Id(String addrRegion1Id){
		this.addrRegion1Id = addrRegion1Id;
	}
	
	public String getAddrRegion1Name(){
		return addrRegion1Name;
	}
	
	public void setAddrRegion1Name(String addrRegion1Name){
		this.addrRegion1Name = addrRegion1Name;
	}
	
	public String getAddrRegion2Id(){
		return addrRegion2Id;
	}
	
	public void setAddrRegion2Id(String addrRegion2Id){
		this.addrRegion2Id = addrRegion2Id;
	}
	
	public String getAddrRegion3Id(){
		return addrRegion3Id;
	}
	
	public void setAddrRegion3Id(String addrRegion3Id){
		this.addrRegion3Id = addrRegion3Id;
	}
	
	public String getAddrRegion3Name(){
		return addrRegion3Name;
	}
	
	public void setAddrRegion3Name(String addrRegion3Name){
		this.addrRegion3Name = addrRegion3Name;
	}
	
	public String getAddrRegion2Name(){
		return addrRegion2Name;
	}
	
	public void setAddrRegion2Name(String addrRegion2Name){
		this.addrRegion2Name = addrRegion2Name;
	}
	
	public String getPolygonPoints(){
		return polygonPoints;
	}
	
	public void setPolygonPoints(String polygonPoints){
		this.polygonPoints = polygonPoints;
	}
	
	
	/*Auto generated methods end*/
	
	
	
	/*Customized methods start*/
	
	/*Customized methods end*/
	
	
	@Override
	public String toString() {
		return "DayCarArea ["
		 + "carAreaId = " + carAreaId + ", areaName = " + areaName + ", cityId = " + cityId + ", cityName = " + cityName
		 + ", longitude = " + longitude + ", latitude = " + latitude + ", isAvailable = " + isAvailable + ", createTime = " + createTime + ", createTimeStart = " + createTimeStart + ", createTimeEnd = " + createTimeEnd
		 + ", updateTime = " + updateTime + ", updateTimeStart = " + updateTimeStart + ", updateTimeEnd = " + updateTimeEnd + ", operatorId = " + operatorId + ", operatorType = " + operatorType + ", memo = " + memo
		 + ", addrRegion1Id = " + addrRegion1Id + ", addrRegion1Name = " + addrRegion1Name + ", addrRegion2Id = " + addrRegion2Id + ", addrRegion3Id = " + addrRegion3Id
		 + ", addrRegion3Name = " + addrRegion3Name + ", addrRegion2Name = " + addrRegion2Name + ", polygonPoints = " + polygonPoints
		+"]";
	}
}
