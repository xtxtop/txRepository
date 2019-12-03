package cn.com.shopec.core.marketing.model;

import java.util.Date;

import cn.com.shopec.core.common.Entity;

/** 
 * AppLocation 数据实体类
 */
public class AppLocation extends Entity<String> {
	
	private static final long serialVersionUID = 1l;
	
	/*Auto generated properties start*/
	
	//主键id
	private String id;
	//类型（1、app打开时所在位置2、下单时所在位置）
	private Integer type;
	//会员号
	private String memberNo;
	//单据号（type为2时，记录单号）
	private String documentNo;
	//手机所在位置经度
	private Double appLongitude;
	//手机所在位置纬度
	private Double appLatitude;
	//所属城市
	private String cityId;
	//一级行政区（省）id
	private String addrRegion1Id;
	//一级行政区（省）名称
	private String addrRegion1Name;
	//二级行政区（市/直辖市区县）id
	private String addrRegion2Id;
	//二级行政区（市/直辖市区县）名称
	private String addrRegion2Name;
	//三级行政区（区县）id
	private String addrRegion3Id;
	//三级行政区（区县）名称
	private String addrRegion3Name;
	//街道
	private String addrStreet;
	//所属片区id（预留，目前现已地理位置进行统计）
	private String areaId;
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
	//操作人类型（0、系统自动操作 1、会员操作）
	private Integer operatorType;
	
	/*Auto generated properties end*/
	
	
	
	/*Customized properties start*/
	
	/*Customized properties end*/
	
	
	
	/*Auto generated methods start*/
	
	@Override
	public String getPK(){
		return id;
	}
	
	public String getId(){
		return id;
	}
	
	public void setId(String id){
		this.id = id;
	}
	
	public Integer getType(){
		return type;
	}
	
	public void setType(Integer type){
		this.type = type;
	}
	
	public String getMemberNo() {
		return memberNo;
	}

	public void setMemberNo(String memberNo) {
		this.memberNo = memberNo;
	}

	public String getDocumentNo() {
		return documentNo;
	}

	public void setDocumentNo(String documentNo) {
		this.documentNo = documentNo;
	}

	public Double getAppLongitude(){
		return appLongitude;
	}
	
	public void setAppLongitude(Double appLongitude){
		this.appLongitude = appLongitude;
	}
	
	public Double getAppLatitude(){
		return appLatitude;
	}
	
	public void setAppLatitude(Double appLatitude){
		this.appLatitude = appLatitude;
	}
	
	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
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
	
	public String getAddrRegion2Name(){
		return addrRegion2Name;
	}
	
	public void setAddrRegion2Name(String addrRegion2Name){
		this.addrRegion2Name = addrRegion2Name;
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
	
	public String getAddrStreet(){
		return addrStreet;
	}
	
	public void setAddrStreet(String addrStreet){
		this.addrStreet = addrStreet;
	}
	
	public String getAreaId(){
		return areaId;
	}
	
	public void setAreaId(String areaId){
		this.areaId = areaId;
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
		return "AppLocation [id=" + id + ", type=" + type + ", memberNo=" + memberNo + ", documentNo=" + documentNo
				+ ", appLongitude=" + appLongitude + ", appLatitude=" + appLatitude + ", addrRegion1Id=" + addrRegion1Id
				+ ", addrRegion1Name=" + addrRegion1Name + ", addrRegion2Id=" + addrRegion2Id + ", addrRegion2Name="
				+ addrRegion2Name + ", addrRegion3Id=" + addrRegion3Id + ", addrRegion3Name=" + addrRegion3Name
				+ ", addrStreet=" + addrStreet + ", areaId=" + areaId + ", createTime=" + createTime
				+ ", createTimeStart=" + createTimeStart + ", createTimeEnd=" + createTimeEnd + ", updateTime="
				+ updateTime + ", updateTimeStart=" + updateTimeStart + ", updateTimeEnd=" + updateTimeEnd
				+ ", operatorId=" + operatorId + ", operatorType=" + operatorType + "]";
	}
}
