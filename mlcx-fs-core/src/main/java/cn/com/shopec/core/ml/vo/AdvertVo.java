package cn.com.shopec.core.ml.vo;

/**
 * 广告模型
 * 
 * @author Administrator
 *
 */
public class AdvertVo {

	private String advertName;// 广告名称

	private String advertPicUrl;// 广告图片url

	private String linkUrl;// 外部链接

	private String linkType;// 跳转类型

	private String text;// 内容

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

	public String getLinkUrl() {
		return linkUrl;
	}

	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl;
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
