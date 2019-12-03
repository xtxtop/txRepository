package cn.com.shopec.core.ml.model;

import java.util.Date;

import cn.com.shopec.core.common.Entity;

/** 
 * 上传附件表 数据实体类
 */
public class CDoc extends Entity<String> {
	
	private static final long serialVersionUID = 1l;
	
	/*Auto generated properties start*/
	
	//附件编号
	private String docNo;
	//附件名称
	private String docName;
	//文件名
	private String fileName;
	//文件类型：0、图片，1、视频，2、文件
	private Integer fileType;
	//图片位置：0、列表，1、详情
	private Integer position;
	//文件url
	private String fileUrl;
	//业务类型（目前只有场站）
	private Integer bizType;
	//业务id（根据不同的业务类型，对应不同业务表的主键）
	private String bizId;
	//删除状态（0、未删除，1、已删除，默认0）
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
	//跳转链接url
	private String linkUrl;
	
	/*Auto generated properties end*/
	
	
	
	/*Customized properties start*/
	
	/*Customized properties end*/
	
	
	
	/*Auto generated methods start*/
	
	@Override
	public String getPK(){
		return docNo;
	}
	
	public String getDocNo(){
		return docNo;
	}
	
	public void setDocNo(String docNo){
		this.docNo = docNo;
	}
	
	public String getDocName(){
		return docName;
	}
	
	public void setDocName(String docName){
		this.docName = docName;
	}
	
	public String getFileName(){
		return fileName;
	}
	
	public void setFileName(String fileName){
		this.fileName = fileName;
	}
	
	public Integer getFileType(){
		return fileType;
	}
	
	public void setFileType(Integer fileType){
		this.fileType = fileType;
	}
	
	public Integer getPosition(){
		return position;
	}
	
	public void setPosition(Integer position){
		this.position = position;
	}
	
	public String getFileUrl(){
		return fileUrl;
	}
	
	public void setFileUrl(String fileUrl){
		this.fileUrl = fileUrl;
	}
	
	public Integer getBizType(){
		return bizType;
	}
	
	public void setBizType(Integer bizType){
		this.bizType = bizType;
	}
	
	public String getBizId(){
		return bizId;
	}
	
	public void setBizId(String bizId){
		this.bizId = bizId;
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
	
	public String getLinkUrl(){
		return linkUrl;
	}
	
	public void setLinkUrl(String linkUrl){
		this.linkUrl = linkUrl;
	}
	
	
	/*Auto generated methods end*/
	
	
	
	/*Customized methods start*/
	
	/*Customized methods end*/
	
	
	@Override
	public String toString() {
		return "CDoc ["
		 + "docNo = " + docNo + ", docName = " + docName + ", fileName = " + fileName + ", fileType = " + fileType
		 + ", position = " + position + ", fileUrl = " + fileUrl + ", bizType = " + bizType + ", bizId = " + bizId
		 + ", isDeleted = " + isDeleted + ", createTime = " + createTime + ", createTimeStart = " + createTimeStart + ", createTimeEnd = " + createTimeEnd + ", updateTime = " + updateTime + ", updateTimeStart = " + updateTimeStart + ", updateTimeEnd = " + updateTimeEnd + ", operatorType = " + operatorType
		 + ", operatorId = " + operatorId + ", linkUrl = " + linkUrl
		+"]";
	}
}
