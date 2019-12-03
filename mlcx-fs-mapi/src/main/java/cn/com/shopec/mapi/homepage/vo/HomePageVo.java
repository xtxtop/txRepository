package cn.com.shopec.mapi.homepage.vo;

import java.util.List;

import cn.com.shopec.core.ml.vo.AdvertCenterVo;
import cn.com.shopec.core.ml.vo.AdvertTextVo;
import cn.com.shopec.core.ml.vo.COperatingCityVo;

/**
 * 充电桩首页模型
 * 
 * @author Administrator
 *
 */
public class HomePageVo {

	// 运营城市
	private COperatingCityVo operatingCity;
	// 运营城市集合
	private List<COperatingCityVo> operatingCityList;
	// 系统参数
	private List<AdvertTextVo> advertTextList;
	// 首页顶部广告集合
	private List<AdvertTopVo> advertTopVoList;
	// 首页中间位置广告集合
	private List<AdvertCenterVo> advertCenterVoList;
	// 最新（猛龙）动态
	private AdvertBottomVo recentDevelopment;
	// 最新（外部）动态
	private AdvertBottomVo externalDevelopment;

	public List<AdvertTopVo> getAdvertTopVoList() {
		return advertTopVoList;
	}

	public void setAdvertTopVoList(List<AdvertTopVo> advertTopVoList) {
		this.advertTopVoList = advertTopVoList;
	}

	public AdvertBottomVo getRecentDevelopment() {
		return recentDevelopment;
	}

	public void setRecentDevelopment(AdvertBottomVo recentDevelopment) {
		this.recentDevelopment = recentDevelopment;
	}

	public AdvertBottomVo getExternalDevelopment() {
		return externalDevelopment;
	}

	public void setExternalDevelopment(AdvertBottomVo externalDevelopment) {
		this.externalDevelopment = externalDevelopment;
	}

	public List<AdvertCenterVo> getAdvertCenterVoList() {
		return advertCenterVoList;
	}

	public void setAdvertCenterVoList(List<AdvertCenterVo> advertCenterVoList) {
		this.advertCenterVoList = advertCenterVoList;
	}

	public List<AdvertTextVo> getAdvertTextList() {
		return advertTextList;
	}

	public void setAdvertTextList(List<AdvertTextVo> advertTextList) {
		this.advertTextList = advertTextList;
	}

	public COperatingCityVo getOperatingCity() {
		return operatingCity;
	}

	public void setOperatingCity(COperatingCityVo operatingCity) {
		this.operatingCity = operatingCity;
	}

	public List<COperatingCityVo> getOperatingCityList() {
		return operatingCityList;
	}

	public void setOperatingCityList(List<COperatingCityVo> operatingCityList) {
		this.operatingCityList = operatingCityList;
	}

}
