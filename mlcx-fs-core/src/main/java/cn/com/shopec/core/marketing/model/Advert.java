package cn.com.shopec.core.marketing.model;

import java.util.Date;

import cn.com.shopec.core.common.Entity;

/** 
 * 广告表 数据实体类
 */
public class Advert extends Entity<String> {
	
	private static final long serialVersionUID = 1l;
	
	/*Auto generated properties start*/
	
	//广告编号
	private String advertNo;
	//广告类型（1、活动广告）
	private Integer advertType;
	//广告展示类型（0 客户端 1 商家端 2 营销端）
	private Integer advertMemberType;
	//广告名称
	private String advertName;
	//排名（相同广告类型时的排名，数字小的优先，0为最小值）
	private Integer ranking;
	//外部链接url
	private String externalLinkUrl;
	//广告图片url1
	private String advertPicUrl;
	//跳转链接url
	private String linkUrl;
	//文字1（预留）
	private String text1;
	//文字2（预留）
	private String text2;
	//文字3（预留）
	private String text3;
	//可用状态（1、可用，0、不可用，默认0）
	private Integer isAvailable;
	//可用状态更新时间
	private Date availableUpdateTime;
	//可用状态更新时间 时间范围起（查询用）
	private Date availableUpdateTimeStart;
	//可用状态更新时间 时间范围止（查询用）
	private Date availableUpdateTimeEnd;	
	//审核状态（0、未审核，1、已审核，2、待审核，3、未通过，默认0）
	private Integer censorStatus;
	//审核人id
	private String censorId;
	//审核时间
	private Date censorTime;
	//审核时间 时间范围起（查询用）
	private Date censorTimeStart;
	//审核时间 时间范围止（查询用）
	private Date censorTimeEnd;	
	//审核备注
	private String censorMemo;
	//操作人类型
	private Integer operatorType;
	//是否删除（0、未删除，1、已删除，默认0）
	private Integer isDeleted;
	//操作人id
	private String operatorId;
	//操作人姓名
	private String operatorName;
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
	//打开app是否展示活动页(0  否  1 是)
	private Integer  isStartAdvert;
	//活动广告区分跳转类型：①内部链接 ②外部链接 ③文章内容'
	private Integer jumpType;
	//内部链接地址的跳转路径1邀请分享、2交纳押金、3充值余额、4身份认证
	private Integer nativeUrlType;
	
	/*Auto generated properties end*/
	
	
	
	/*Customized properties start*/
	
	/*Customized properties end*/
	
	
	
	/*Auto generated methods start*/
	
	@Override
	public String getPK(){
		return advertNo;
	}
	
	public String getAdvertNo(){
		return advertNo;
	}
	
	public void setAdvertNo(String advertNo){
		this.advertNo = advertNo;
	}
	
	public Integer getAdvertType(){
		return advertType;
	}
	
	public void setAdvertType(Integer advertType){
		this.advertType = advertType;
	}
	
	public String getAdvertName(){
		return advertName;
	}
	
	public void setAdvertName(String advertName){
		this.advertName = advertName;
	}
	
	public Integer getRanking(){
		return ranking;
	}
	
	public void setRanking(Integer ranking){
		this.ranking = ranking;
	}
	
	public String getAdvertPicUrl(){
		return advertPicUrl;
	}
	
	public void setAdvertPicUrl(String advertPicUrl){
		this.advertPicUrl = advertPicUrl;
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
	
	
	/*Auto generated methods end*/
	
	
	
	/*Customized methods start*/
	
	/*Customized methods end*/
	
	
	public String getExternalLinkUrl() {
		return externalLinkUrl;
	}

	public void setExternalLinkUrl(String externalLinkUrl) {
		this.externalLinkUrl = externalLinkUrl;
	}

	@Override
	public String toString() {
		return "Advert [advertNo=" + advertNo + ", advertType=" + advertType + ", advertName=" + advertName
				+ ", ranking=" + ranking + ", externalLinkUrl=" + externalLinkUrl + ", advertPicUrl=" + advertPicUrl
				+ ", linkUrl=" + linkUrl + ", text1=" + text1 + ", text2=" + text2 + ", text3=" + text3
				+ ", isAvailable=" + isAvailable + ", availableUpdateTime=" + availableUpdateTime
				+ ", availableUpdateTimeStart=" + availableUpdateTimeStart + ", availableUpdateTimeEnd="
				+ availableUpdateTimeEnd + ", censorStatus=" + censorStatus + ", censorId=" + censorId + ", censorTime="
				+ censorTime + ", censorTimeStart=" + censorTimeStart + ", censorTimeEnd=" + censorTimeEnd
				+ ", censorMemo=" + censorMemo + ", operatorType=" + operatorType + ", isDeleted=" + isDeleted
				+ ", operatorId=" + operatorId + ", operatorName=" + operatorName + ", createTime=" + createTime
				+ ", createTimeStart=" + createTimeStart + ", createTimeEnd=" + createTimeEnd + ", updateTime="
				+ updateTime + ", updateTimeStart=" + updateTimeStart + ", updateTimeEnd=" + updateTimeEnd + "]";
	}

	public Integer getIsStartAdvert() {
		return isStartAdvert;
	}

	public void setIsStartAdvert(Integer isStartAdvert) {
		this.isStartAdvert = isStartAdvert;
	}

	public Integer getAdvertMemberType() {
		return advertMemberType;
	}

	public void setAdvertMemberType(Integer advertMemberType) {
		this.advertMemberType = advertMemberType;
	}

	public Integer getJumpType() {
		return jumpType;
	}

	public void setJumpType(Integer jumpType) {
		this.jumpType = jumpType;
	}

	public Integer getNativeUrlType() {
		return nativeUrlType;
	}

	public void setNativeUrlType(Integer nativeUrlType) {
		this.nativeUrlType = nativeUrlType;
	}

	
}
