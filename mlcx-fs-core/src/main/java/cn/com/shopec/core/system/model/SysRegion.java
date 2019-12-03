package cn.com.shopec.core.system.model;


import java.util.Date;

import cn.com.shopec.core.common.Entity;



/**
 * SysRegion 数据库实体类
 */
public class SysRegion extends Entity<String> {
	
	private static final long serialVersionUID = 1l;
	
	//地区id
	private String regionId;
	//地区名称
	private String regionName;
	//级别（省级为1，市级/直辖市区县为2，区县为3）
	private Integer level;
	//父级地区id（省级地名的父级id为0）
	private String parentId;
	//地区编码（建议用国家民政部的编码）
	private String regionCode;
	//邮政编码
	private String postCode;
	//电话区号
	private String diallingCode;
	//简写地名（如：内蒙古）
	private String shotName;
	//地名拼音
	private String pinyinName;
	//拼音简写（一律大写，如北京为BJ）
	private String pinyinShortName;
	//多级地名合并的全名（各级地名间用逗号间隔，值如： 广东省,深圳市,南山区）
	private String mergedName;
	//多级简写地名合并后的全名（各级地名间用逗号间隔，值如：广东,深圳,南山）
	private String mergedShortName;
	//纬度
	private Double latitude;
	//经度
	private Double longitude;
	//是否可用（1、可用，0、不可用，默认1）
	private Integer isAvailable;
	//是否已删除（逻辑删除，1、已删除，0、未删除，默认0）
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
		return regionId;
	}
	
	public String getRegionId(){
		return regionId;
	}
	
	public void setRegionId(String regionId){
		this.regionId = regionId;
	}
	public String getRegionName(){
		return regionName;
	}
	
	public void setRegionName(String regionName){
		this.regionName = regionName;
	}
	public Integer getLevel(){
		return level;
	}
	
	public void setLevel(Integer level){
		this.level = level;
	}
	public String getParentId(){
		return parentId;
	}
	
	public void setParentId(String parentId){
		this.parentId = parentId;
	}
	public String getRegionCode(){
		return regionCode;
	}
	
	public void setRegionCode(String regionCode){
		this.regionCode = regionCode;
	}
	public String getPostCode(){
		return postCode;
	}
	
	public void setPostCode(String postCode){
		this.postCode = postCode;
	}
	public String getDiallingCode(){
		return diallingCode;
	}
	
	public void setDiallingCode(String diallingCode){
		this.diallingCode = diallingCode;
	}
	public String getShotName(){
		return shotName;
	}
	
	public void setShotName(String shotName){
		this.shotName = shotName;
	}
	public String getPinyinName(){
		return pinyinName;
	}
	
	public void setPinyinName(String pinyinName){
		this.pinyinName = pinyinName;
	}
	public String getPinyinShortName(){
		return pinyinShortName;
	}
	
	public void setPinyinShortName(String pinyinShortName){
		this.pinyinShortName = pinyinShortName;
	}
	public String getMergedName(){
		return mergedName;
	}
	
	public void setMergedName(String mergedName){
		this.mergedName = mergedName;
	}
	public String getMergedShortName(){
		return mergedShortName;
	}
	
	public void setMergedShortName(String mergedShortName){
		this.mergedShortName = mergedShortName;
	}
	public Double getLatitude(){
		return latitude;
	}
	
	public void setLatitude(Double latitude){
		this.latitude = latitude;
	}
	public Double getLongitude(){
		return longitude;
	}
	
	public void setLongitude(Double longitude){
		this.longitude = longitude;
	}
	public Integer getIsAvailable(){
		return isAvailable;
	}
	
	public void setIsAvailable(Integer isAvailable){
		this.isAvailable = isAvailable;
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
	public Date getUpdateTime(){
		return updateTime;
	}
	
	public void setUpdateTime(Date updateTime){
		this.updateTime = updateTime;
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
}
