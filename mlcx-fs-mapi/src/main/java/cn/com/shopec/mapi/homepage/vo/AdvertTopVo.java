package cn.com.shopec.mapi.homepage.vo;

/**
 * 顶部广告(轮播图)
 * 
 * @author Administrator
 *
 */
public class AdvertTopVo {
	// 广告图片url
	private String advertPicUrl;
	// 跳转链接
	private String linkUrl;
	// 广告名称
	private String advertName;
	// 跳转链接类型（0、外部跳转；1、内容）
	private String linkType;
	// 广告内容
	private String text;

	public String getAdvertPicUrl() {
		return advertPicUrl;
	}

	public void setAdvertPicUrl(String advertPicUrl) {
		this.advertPicUrl = advertPicUrl;
	}

	public String getLinkUrl() {
		return linkUrl;
	}

	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl;
	}

	public String getAdvertName() {
		return advertName;
	}

	public void setAdvertName(String advertName) {
		this.advertName = advertName;
	}

	public String getLinkType() {
		return linkType;
	}

	public void setLinkType(String linkType) {
		this.linkType = linkType;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

}
