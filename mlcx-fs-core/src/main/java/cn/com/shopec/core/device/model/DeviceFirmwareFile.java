package cn.com.shopec.core.device.model;

import java.util.Date;

import cn.com.shopec.core.common.Entity;

/** 
 * 设备固件文件表 数据实体类
 */
public class DeviceFirmwareFile extends Entity<String> {
	
	private static final long serialVersionUID = 1l;
	
	/*Auto generated properties start*/
	
	//id
	private String id;
	//固件类型（6：E6设备固件，5：Plus盒子固件）
	private Integer firmwareType;
	//车型id（具体值见数据字典表），如果是针对所有车型的，此字段值为空串
	private String carModelId;
	//升级地址（IP或域名）
	private String upAddress;
	//ftp用户名
	private String ftpUsername;
	//ftp密码
	private String ftpPassword;
	//固件文件名
	private String fileName;
	//固件文件路径
	private String filePath;
	//固件文件和校验码
	private String fileChecksum;
	//固件文件版本，格式如：V1.2
	private String fileVersion;
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
		return id;
	}
	
	public String getId(){
		return id;
	}
	
	public void setId(String id){
		this.id = id;
	}
	
	public Integer getFirmwareType(){
		return firmwareType;
	}
	
	public void setFirmwareType(Integer firmwareType){
		this.firmwareType = firmwareType;
	}
	
	public String getCarModelId(){
		return carModelId;
	}
	
	public void setCarModelId(String carModelId){
		this.carModelId = carModelId;
	}
	
	public String getUpAddress(){
		return upAddress;
	}
	
	public void setUpAddress(String upAddress){
		this.upAddress = upAddress;
	}
	
	public String getFtpUsername(){
		return ftpUsername;
	}
	
	public void setFtpUsername(String ftpUsername){
		this.ftpUsername = ftpUsername;
	}
	
	public String getFtpPassword(){
		return ftpPassword;
	}
	
	public void setFtpPassword(String ftpPassword){
		this.ftpPassword = ftpPassword;
	}
	
	public String getFileName(){
		return fileName;
	}
	
	public void setFileName(String fileName){
		this.fileName = fileName;
	}
	
	public String getFilePath(){
		return filePath;
	}
	
	public void setFilePath(String filePath){
		this.filePath = filePath;
	}
	
	public String getFileChecksum(){
		return fileChecksum;
	}
	
	public void setFileChecksum(String fileChecksum){
		this.fileChecksum = fileChecksum;
	}
	
	public String getFileVersion(){
		return fileVersion;
	}
	
	public void setFileVersion(String fileVersion){
		this.fileVersion = fileVersion;
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
	
	
	@Override
	public String toString() {
		return "DeviceFirmwareFile ["
		 + "id = " + id + ", firmwareType = " + firmwareType + ", carModelId = " + carModelId + ", upAddress = " + upAddress
		 + ", ftpUsername = " + ftpUsername + ", ftpPassword = " + ftpPassword + ", fileName = " + fileName + ", filePath = " + filePath
		 + ", fileChecksum = " + fileChecksum + ", fileVersion = " + fileVersion + ", createTime = " + createTime + ", createTimeStart = " + createTimeStart + ", createTimeEnd = " + createTimeEnd + ", updateTime = " + updateTime + ", updateTimeStart = " + updateTimeStart + ", updateTimeEnd = " + updateTimeEnd
		 + ", operatorType = " + operatorType + ", operatorId = " + operatorId
		+"]";
	}
}
