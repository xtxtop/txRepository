package cn.com.shopec.core.marketing.model;

import java.util.Date;

import cn.com.shopec.core.common.Entity;

/** 
 * OrderShareSetting 数据实体类
 */
public class OrderShareSetting extends Entity<String> {
	
	private static final long serialVersionUID = 1l;
	
	/*Auto generated properties start*/
	
	//订单分享配置编号
	private String orderShareSettingNo;
	//订单分享名称
	private String orderShareName;
	//订单分享内容配置
	private String orderShareContent;
	//分享链接的简短内容
	private String orderShareMemo;
	//订单分享图片配置
	private String orderSharePicUrl;
	//订单分享icon地址
	private String orderShareIconUrl;
	//跳转链接url
	private String linkUrl;
	//可用状态（1、可用，0、不可用，默认0）
	private Integer isAvailable;
	private Date createTime;
	// 时间范围起（查询用）
	private Date createTimeStart;
	// 时间范围止（查询用）
	private Date createTimeEnd;	
	private Date updateTime;
	// 时间范围起（查询用）
	private Date updateTimeStart;
	// 时间范围止（查询用）
	private Date updateTimeEnd;	
	private String operatorId;
	private Integer operatorType;
	
	/*Auto generated properties end*/
	
	
	
	/*Customized properties start*/
	
	/*Customized properties end*/
	
	
	
	/*Auto generated methods start*/
	
	@Override
	public String getPK(){
		return orderShareSettingNo;
	}
	
	public String getOrderShareSettingNo(){
		return orderShareSettingNo;
	}
	
	public void setOrderShareSettingNo(String orderShareSettingNo){
		this.orderShareSettingNo = orderShareSettingNo;
	}
	
	public String getOrderShareName(){
		return orderShareName;
	}
	
	public void setOrderShareName(String orderShareName){
		this.orderShareName = orderShareName;
	}
	
	public String getOrderShareMemo() {
		return orderShareMemo;
	}

	public void setOrderShareMemo(String orderShareMemo) {
		this.orderShareMemo = orderShareMemo;
	}

	public String getOrderShareContent(){
		return orderShareContent;
	}
	
	public void setOrderShareContent(String orderShareContent){
		this.orderShareContent = orderShareContent;
	}
	
	public String getOrderSharePicUrl(){
		return orderSharePicUrl;
	}
	
	public void setOrderSharePicUrl(String orderSharePicUrl){
		this.orderSharePicUrl = orderSharePicUrl;
	}
	
	public String getOrderShareIconUrl() {
		return orderShareIconUrl;
	}

	public void setOrderShareIconUrl(String orderShareIconUrl) {
		this.orderShareIconUrl = orderShareIconUrl;
	}

	public String getLinkUrl(){
		return linkUrl;
	}
	
	public void setLinkUrl(String linkUrl){
		this.linkUrl = linkUrl;
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
	
	
	/*Auto generated methods end*/
	
	
	
	/*Customized methods start*/
	
	/*Customized methods end*/
	
	
	@Override
	public String toString() {
		return "OrderShareSetting ["
		 + "orderShareSettingNo = " + orderShareSettingNo + ", orderShareName = " + orderShareName + ", orderShareContent = " + orderShareContent + ", orderSharePicUrl = " + orderSharePicUrl
		 + ", linkUrl = " + linkUrl + ", isAvailable = " + isAvailable + ", createTime = " + createTime + ", createTimeStart = " + createTimeStart + ", createTimeEnd = " + createTimeEnd + ", updateTime = " + updateTime + ", updateTimeStart = " + updateTimeStart + ", updateTimeEnd = " + updateTimeEnd
		 + ", operatorId = " + operatorId + ", operatorType = " + operatorType
		+"]";
	}
}
