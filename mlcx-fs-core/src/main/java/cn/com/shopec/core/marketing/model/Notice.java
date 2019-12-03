package cn.com.shopec.core.marketing.model;

import java.util.Date;

import cn.com.shopec.core.common.Entity;

/** 
 * Notice 数据实体类
 */
public class Notice extends Entity<String> {
	
	private static final long serialVersionUID = 1l;
	
	/*Auto generated properties start*/
	
	//公告编号
	private String noticeNo;
	//公告类型（1、活动公告告）
	private Integer noticeType;
	//公告名称
	private String noticeName;
	//公告排名
	private Integer ranking;
	//公告图片
	private String noticePicUrl;
	//跳转链接
	private String linkUrl;
	//内容
	private String text1;
	private String text2;
	private String text3;
	private Integer isAvailable;
	private Date availableUpdateTime;
	// 时间范围起（查询用）
	private Date availableUpdateTimeStart;
	// 时间范围止（查询用）
	private Date availableUpdateTimeEnd;	
	private Integer censorStatus;
	private String censorId;
	private Date censorTime;
	// 时间范围起（查询用）
	private Date censorTimeStart;
	// 时间范围止（查询用）
	private Date censorTimeEnd;	
	private String censorMemo;
	private Integer operatorType;
	private Integer isDeleted;
	private String operatorId;
	private String operatorName;
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
	//外部链接url
	private String externalLinkUrl;
	//0 客户端 1商家端
	private Integer noticeMemberType;

	
	
	public String getNoticeNo(){
		return noticeNo;
	}
	
	public void setNoticeNo(String noticeNo){
		this.noticeNo = noticeNo;
	}
	
	
	
	public Integer getRanking(){
		return ranking;
	}
	
	public void setRanking(Integer ranking){
		this.ranking = ranking;
	}
	
	
	public String getLinkUrl(){
		return linkUrl;
	}
	
	public void setLinkUrl(String linkUrl){
		this.linkUrl = linkUrl;
	}
	
	public String getText1(){
		return text1;
	}
	
	public void setText1(String text1){
		this.text1 = text1;
	}
	
	public String getText2(){
		return text2;
	}
	
	public void setText2(String text2){
		this.text2 = text2;
	}
	
	public String getText3(){
		return text3;
	}
	
	public void setText3(String text3){
		this.text3 = text3;
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
	
	public Integer getCensorStatus(){
		return censorStatus;
	}
	
	public void setCensorStatus(Integer censorStatus){
		this.censorStatus = censorStatus;
	}
	
	public String getCensorId(){
		return censorId;
	}
	
	public void setCensorId(String censorId){
		this.censorId = censorId;
	}
	
	public Date getCensorTime(){
		return censorTime;
	}
	
	public void setCensorTime(Date censorTime){
		this.censorTime = censorTime;
	}
	
	public Date getCensorTimeStart(){
		return censorTimeStart;
	}
	
	public void setCensorTimeStart(Date censorTimeStart){
		this.censorTimeStart = censorTimeStart;
	}
	
	public Date getCensorTimeEnd(){
		return censorTimeEnd;
	}
	
	public void setCensorTimeEnd(Date censorTimeEnd){
		this.censorTimeEnd = censorTimeEnd;
	}	
	
	public String getCensorMemo(){
		return censorMemo;
	}
	
	public void setCensorMemo(String censorMemo){
		this.censorMemo = censorMemo;
	}
	
	public Integer getOperatorType(){
		return operatorType;
	}
	
	public void setOperatorType(Integer operatorType){
		this.operatorType = operatorType;
	}
	
	public Integer getIsDeleted(){
		return isDeleted;
	}
	
	public void setIsDeleted(Integer isDeleted){
		this.isDeleted = isDeleted;
	}
	
	public String getOperatorId(){
		return operatorId;
	}
	
	public void setOperatorId(String operatorId){
		this.operatorId = operatorId;
	}
	
	public String getOperatorName(){
		return operatorName;
	}
	
	public void setOperatorName(String operatorName){
		this.operatorName = operatorName;
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
	
	public String getExternalLinkUrl(){
		return externalLinkUrl;
	}
	
	public void setExternalLinkUrl(String externalLinkUrl){
		this.externalLinkUrl = externalLinkUrl;
	}
	

	
	

	public Integer getNoticeType() {
		return noticeType;
	}

	public String getNoticePicUrl() {
		return noticePicUrl;
	}

	public void setNoticePicUrl(String noticePicUrl) {
		this.noticePicUrl = noticePicUrl;
	}

	public void setNoticeType(Integer noticeType) {
		this.noticeType = noticeType;
	}

	public String getNoticeName() {
		return noticeName;
	}

	public void setNoticeName(String noticeName) {
		this.noticeName = noticeName;
	}

	public Integer getNoticeMemberType() {
		return noticeMemberType;
	}

	public void setNoticeMemberType(Integer noticeMemberType) {
		this.noticeMemberType = noticeMemberType;
	}

	@Override
	public String toString() {
		return "Notice [noticeNo=" + noticeNo + ", noticeType=" + noticeType + ", noticeName=" + noticeName
				+ ", ranking=" + ranking + ", noticePicUrl=" + noticePicUrl + ", linkUrl=" + linkUrl + ", text1="
				+ text1 + ", text2=" + text2 + ", text3=" + text3 + ", isAvailable=" + isAvailable
				+ ", availableUpdateTime=" + availableUpdateTime + ", availableUpdateTimeStart="
				+ availableUpdateTimeStart + ", availableUpdateTimeEnd=" + availableUpdateTimeEnd + ", censorStatus="
				+ censorStatus + ", censorId=" + censorId + ", censorTime=" + censorTime + ", censorTimeStart="
				+ censorTimeStart + ", censorTimeEnd=" + censorTimeEnd + ", censorMemo=" + censorMemo
				+ ", operatorType=" + operatorType + ", isDeleted=" + isDeleted + ", operatorId=" + operatorId
				+ ", operatorName=" + operatorName + ", createTime=" + createTime + ", createTimeStart="
				+ createTimeStart + ", createTimeEnd=" + createTimeEnd + ", updateTime=" + updateTime
				+ ", updateTimeStart=" + updateTimeStart + ", updateTimeEnd=" + updateTimeEnd + ", externalLinkUrl="
				+ externalLinkUrl + ", noticeMemberType=" + noticeMemberType + "]";
	}
	
	
	
}
