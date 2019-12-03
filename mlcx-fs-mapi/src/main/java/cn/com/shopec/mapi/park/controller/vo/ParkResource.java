package cn.com.shopec.mapi.park.controller.vo;

import java.util.List;

import cn.com.shopec.core.ml.vo.AdvertTextVo;
import cn.com.shopec.core.ml.vo.BannerVo;
import cn.com.shopec.core.ml.vo.CLabelVo;
import cn.com.shopec.core.ml.vo.COperatingCityVo;

/**
 * 
 * 停车场列表静态资源模型
 * 
 * @author Administrator
 *
 */
public class ParkResource {
	// 顶部轮播图
	private List<BannerVo> advertList;
	// 滚动文字
	private List<AdvertTextVo> advertTextVoList;
	// 标签集合
	private List<CLabelVo> labelList;
/*	// 最近运营城市
	private COperatingCityVo coperatingCity;
	// 运营城市集合
	private List<COperatingCityVo> coperatingCityList;*/

	
	public List<BannerVo> getAdvertList() {
		return advertList;
	}

	public void setAdvertList(List<BannerVo> advertList) {
		this.advertList = advertList;
	}

	public List<AdvertTextVo> getAdvertTextVoList() {
		return advertTextVoList;
	}

	public void setAdvertTextVoList(List<AdvertTextVo> advertTextVoList) {
		this.advertTextVoList = advertTextVoList;
	}

	public List<CLabelVo> getLabelList() {
		return labelList;
	}

	public void setLabelList(List<CLabelVo> labelList) {
		this.labelList = labelList;
	}

	/*public COperatingCityVo getCoperatingCity() {
		return coperatingCity;
	}

	public void setCoperatingCity(COperatingCityVo coperatingCity) {
		this.coperatingCity = coperatingCity;
	}

	public List<COperatingCityVo> getCoperatingCityList() {
		return coperatingCityList;
	}

	public void setCoperatingCityList(List<COperatingCityVo> coperatingCityList) {
		this.coperatingCityList = coperatingCityList;
	}
*/
}
