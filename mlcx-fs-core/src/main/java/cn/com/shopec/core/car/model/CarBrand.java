package cn.com.shopec.core.car.model;

import java.util.Date;

import cn.com.shopec.core.common.Entity;

/** 
 * 车辆品牌 数据实体类
 */
public class CarBrand extends Entity<String> {
	
	private static final long serialVersionUID = 1l;
	
	/*Auto generated properties start*/
	
	//品牌id
	private String carBrandId;
	//品牌名称
	private String carBrandName;
	//名称简写
	private String brandShortName;
	//LOGO图片
	private String logoPic;
	//英文名称
	private String engName;
	//官网地址
	private String webSite;
	//所属公司
	private String owerCompany;
	//公司地址
	private String companyAddr;
	//品牌介绍
	private String brandInfo;
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
	//操作人id
	private String operatorId;
	//操作人类型
	private Integer operatorType;
	
	/*Auto generated properties end*/
	
	
	
	/*Customized properties start*/
	
	/*Customized properties end*/
	
	
	
	/*Auto generated methods start*/
	
	@Override
	public String getPK(){
		return carBrandId;
	}
	
	public String getCarBrandId(){
		return carBrandId;
	}
	
	public void setCarBrandId(String carBrandId){
		this.carBrandId = carBrandId;
	}
	
	public String getCarBrandName(){
		return carBrandName;
	}
	
	public void setCarBrandName(String carBrandName){
		this.carBrandName = carBrandName;
	}
	
	public String getBrandShortName(){
		return brandShortName;
	}
	
	public void setBrandShortName(String brandShortName){
		this.brandShortName = brandShortName;
	}
	
	public String getLogoPic() {
		return logoPic;
	}

	public void setLogoPic(String logoPic) {
		this.logoPic = logoPic;
	}

	public String getEngName(){
		return engName;
	}
	
	public void setEngName(String engName){
		this.engName = engName;
	}
	
	public String getWebSite(){
		return webSite;
	}
	
	public void setWebSite(String webSite){
		this.webSite = webSite;
	}
	
	public String getOwerCompany(){
		return owerCompany;
	}
	
	public void setOwerCompany(String owerCompany){
		this.owerCompany = owerCompany;
	}
	
	public String getCompanyAddr(){
		return companyAddr;
	}
	
	public void setCompanyAddr(String companyAddr){
		this.companyAddr = companyAddr;
	}
	
	public String getBrandInfo(){
		return brandInfo;
	}
	
	public void setBrandInfo(String brandInfo){
		this.brandInfo = brandInfo;
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
		return "CarBrand ["
		 + "carBrandId = " + carBrandId + ", carBrandName = " + carBrandName + ", brandShortName = " + brandShortName + ", logoPic = " + logoPic
		 + ", engName = " + engName + ", webSite = " + webSite + ", owerCompany = " + owerCompany + ", companyAddr = " + companyAddr
		 + ", brandInfo = " + brandInfo + ", createTime = " + createTime + ", createTimeStart = " + createTimeStart + ", createTimeEnd = " + createTimeEnd + ", updateTime = " + updateTime + ", updateTimeStart = " + updateTimeStart + ", updateTimeEnd = " + updateTimeEnd + ", operatorId = " + operatorId
		 + ", operatorType = " + operatorType
		+"]";
	}
}
