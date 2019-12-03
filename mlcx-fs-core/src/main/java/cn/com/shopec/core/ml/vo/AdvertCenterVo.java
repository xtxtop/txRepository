package cn.com.shopec.core.ml.vo;

import java.util.List;

/**
 * 充电桩首页中部位置广告模型
 * 
 * @author Administrator
 *
 */
public class AdvertCenterVo {
	// 头条文本集合
	private List<AdvertTextVo> advertTextVoList;
	// banner集合
	private List<BannerVo> bannerVoList;
	// 广告对象
	private AdvertVo advert;
	// 广告图片集合
	private AdvertVo advertPic;

	public List<AdvertTextVo> getAdvertTextVoList() {
		return advertTextVoList;
	}

	public void setAdvertTextVoList(List<AdvertTextVo> advertTextVoList) {
		this.advertTextVoList = advertTextVoList;
	}

	public List<BannerVo> getBannerVoList() {
		return bannerVoList;
	}

	public void setBannerVoList(List<BannerVo> bannerVoList) {
		this.bannerVoList = bannerVoList;
	}

	public AdvertVo getAdvert() {
		return advert;
	}

	public void setAdvert(AdvertVo advert) {
		this.advert = advert;
	}

	public AdvertVo getAdvertPic() {
		return advertPic;
	}

	public void setAdvertPic(AdvertVo advertPic) {
		this.advertPic = advertPic;
	}

}
