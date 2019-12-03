package cn.com.shopec.mapi.homepage.vo;

/**
 * 底部广告(动态)
 * 
 * @author Administrator
 *
 */
public class AdvertBottomVo {
	// 广告名称
	private String advertName;
	// 广告图片url
	private String advertPicUrl;
	// 内容
	private String text;
	// 跳转链接url
	private String linkUrl;
	// 跳转内容
	private String linkType;
	// 修改时间
	private String updateTime;

	public String getAdvertName() {
		return advertName;
	}

	public void setAdvertName(String advertName) {
		this.advertName = advertName;
	}

	public String getAdvertPicUrl() {
		return advertPicUrl;
	}

	public void setAdvertPicUrl(String advertPicUrl) {
		this.advertPicUrl = advertPicUrl;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getLinkUrl() {
		return linkUrl;
	}

	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public String getLinkType() {
		return linkType;
	}

	public void setLinkType(String linkType) {
		this.linkType = linkType;
	}

}
