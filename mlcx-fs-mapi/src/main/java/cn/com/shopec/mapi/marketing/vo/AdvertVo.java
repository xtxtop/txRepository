package cn.com.shopec.mapi.marketing.vo;

import cn.com.shopec.core.common.Entity;

/** 
 * 广告表 数据实体类
 */
public class AdvertVo extends Entity<String> {
	
	private static final long serialVersionUID = 1l;
	
	/*Auto generated properties start*/
	
	//广告编号
	private String advertNo;
	//广告类型（1、活动广告）
	private Integer advertType;
	//广告名称
	private String advertName;
	//排名（相同广告类型时的排名，数字小的优先，0为最小值）
	private Integer ranking;
	//广告图片url1
	private String advertPicUrl;
	//跳转链接url
	private String linkUrl;
	//可用状态（1、可用，0、不可用，默认0）
	private Integer isAvailable;
	//审核状态（0、未审核，1、已审核，2、待审核，3、未通过，默认0）
	private Integer censorStatus;
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
	
	
	public Integer getIsAvailable(){
		return isAvailable;
	}
	
	public void setIsAvailable(Integer isAvailable){
		this.isAvailable = isAvailable;
	}
	
	
	public Integer getCensorStatus(){
		return censorStatus;
	}
	
	public void setCensorStatus(Integer censorStatus){
		this.censorStatus = censorStatus;
	}

	@Override
	public String toString() {
		return "AdvertVo [advertNo=" + advertNo + ", advertType=" + advertType + ", advertName=" + advertName
				+ ", ranking=" + ranking + ", advertPicUrl=" + advertPicUrl + ", linkUrl=" + linkUrl + ", isAvailable="
				+ isAvailable + ", censorStatus=" + censorStatus + "]";
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
