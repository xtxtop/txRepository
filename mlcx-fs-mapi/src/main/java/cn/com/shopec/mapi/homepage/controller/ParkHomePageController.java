package cn.com.shopec.mapi.homepage.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.common.constants.Constant;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.ml.model.AdvertMengLong;
import cn.com.shopec.core.ml.model.COperatingCity;
import cn.com.shopec.core.ml.service.AdvertMengLongService;
import cn.com.shopec.core.ml.service.CMatchingService;
import cn.com.shopec.core.ml.service.COperatingCityService;
import cn.com.shopec.core.ml.service.ChargingStationService;
import cn.com.shopec.core.ml.vo.AdvertCenterVo;
import cn.com.shopec.core.ml.vo.AdvertTextVo;
import cn.com.shopec.core.ml.vo.COperatingCityVo;
import cn.com.shopec.core.mlparking.service.CParkingService;
import cn.com.shopec.core.mlparking.vo.CParkingVo;
import cn.com.shopec.mapi.common.controller.BaseController;
import cn.com.shopec.mapi.homepage.vo.AdvertBottomVo;
import cn.com.shopec.mapi.homepage.vo.AdvertTopVo;
import cn.com.shopec.mapi.homepage.vo.HomePageVo;

/**
 * app停车场首页
 *
 * @author Administrator
 */
@Controller
@RequestMapping("/app/parkHomePage/")
public class ParkHomePageController extends BaseController {

	@Resource
	private AdvertMengLongService advertMengLongService;

	@Resource
	private ChargingStationService chargignStationService;

	@Resource
	private COperatingCityService coperatingCityService;

	@Resource
	private CMatchingService cmatchingService;

	@Resource
	private CParkingService cparkingService;

	@Value("$image_path")
	private String sysParam;

	@Value("${image_path}")
	private String IMG_HOST;

	/**
	 * 停车场首页接口
	 *
	 * @param
	 * @param longitude
	 *            经度
	 * @param latitude
	 *            维度
	 */

	@RequestMapping("/index")
	@ResponseBody
	public ResultInfo<HomePageVo> homePage(@RequestParam(value = "longitude", required = false) String longitude,
			@RequestParam(value = "latitude", required = false) String latitude) {
		ResultInfo<HomePageVo> result = new ResultInfo<>();
		HomePageVo homePageVo = new HomePageVo();

		// 获取运营城市集合
		COperatingCity cc = new COperatingCity();
		cc.setEnableStatus(1);// 启用状态
		List<COperatingCity> operatingCityList = coperatingCityService.getCOperatingCityList(new Query(cc));
		List<COperatingCityVo> operatingCityVoList = new ArrayList<>();
		if (operatingCityList.size() > 0) {
			for (COperatingCity ctc : operatingCityList) {
				COperatingCityVo cv = new COperatingCityVo();
				cv.setOperatingCityName((ctc.getCityName()));
				cv.setOperatingCityNo(ctc.getOperatingCityNo());
				cv.setLantitude(ctc.getLatitude());
				cv.setLongitude(ctc.getLongitude());
				operatingCityVoList.add(cv);
			}
			homePageVo.setOperatingCityList(operatingCityVoList);
		}

		if (null != longitude && null != latitude) {
			// 获取最近运营城市
			COperatingCityVo operatingCity = getOperatingCity(longitude, latitude);
			if (null != operatingCity) {
				homePageVo.setOperatingCity(operatingCity);
			}
		} else {
			if (operatingCityList.size() > 0) {
				homePageVo.setOperatingCity(operatingCityVoList.get(0));// 经纬度为空时，默认取列表第一个城市
			}
		}

		// 顶部广告数据
		AdvertMengLong advertMengLong = new AdvertMengLong();
		advertMengLong.setType(Constant.TYPE_3);// 停车场类型
		advertMengLong.setAdvertType(1);// 顶部轮播图区域
		advertMengLong.setAdvertPosition("1");// 顶部位置
		List<AdvertMengLong> advertMengLongList = advertMengLongService.getAdvertList(new Query(advertMengLong));
		List<AdvertTopVo> adTopVoList = new ArrayList<>();
		if (advertMengLongList.size() > 0) {
			for (AdvertMengLong ad : advertMengLongList) {
				AdvertTopVo adTopVo = new AdvertTopVo();
				adTopVo.setAdvertName(ad.getAdvertName());
				adTopVo.setLinkUrl(ad.getLinkUrl());
				adTopVo.setLinkType(String.valueOf(ad.getLinkType()));
				adTopVo.setText(ad.getText());
				adTopVo.setAdvertPicUrl(IMG_HOST + "/" + ad.getAdvertPicUrl());
				adTopVoList.add(adTopVo);
			}
			homePageVo.setAdvertTopVoList(adTopVoList);
		}
		// 系统数据
		AdvertMengLong ad = new AdvertMengLong();
		ad.setType(Constant.TYPE_3);// 充电桩类型
		ad.setAdvertType(5);// 滚动文字区域类型
		ad.setAdvertPosition("8");// 系统顶部滚动文字
		List<AdvertMengLong> advertList = advertMengLongService.getAdvertList(new Query(ad));
		List<AdvertTextVo> sysParamList = new ArrayList<>();
		if (advertList.size() > 0) {
			for (AdvertMengLong a : advertList) {
				AdvertTextVo avo = new AdvertTextVo();
				avo.setAdvertName(a.getAdvertName());
				sysParamList.add(avo);
			}
			homePageVo.setAdvertTextList(sysParamList);
		}

		// 获取中间广告数据
		List<AdvertCenterVo> advertCenterVoList = advertCenterList();
		if (advertCenterVoList.size() > 0 && null != advertCenterVoList) {
			homePageVo.setAdvertCenterVoList(advertCenterVoList);
		}
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:MM:SS");
		// 猛龙最新动态
		AdvertMengLong recentAdvert = advertMengLongService.getRecentDevelopment(1, 3);
		if (null != recentAdvert) {
			AdvertBottomVo abv = new AdvertBottomVo();
			abv.setAdvertName(recentAdvert.getAdvertName());
			abv.setAdvertPicUrl(IMG_HOST + "/" + recentAdvert.getAdvertPicUrl());

			if (null != recentAdvert.getUpdateTime()) {
				abv.setUpdateTime(sf.format(recentAdvert.getUpdateTime()));
			} else {
				abv.setUpdateTime(sf.format(new Date()));
			}
			abv.setLinkUrl(recentAdvert.getLinkUrl());
			abv.setLinkType(String.valueOf(recentAdvert.getLinkType()));
			abv.setText(recentAdvert.getText());
			homePageVo.setRecentDevelopment(abv);
		}

		// 外部最新动态
		AdvertMengLong extenelAdvert = advertMengLongService.getRecentDevelopment(2, 3);
		if (null != extenelAdvert) {
			AdvertBottomVo abvo = new AdvertBottomVo();
			abvo.setAdvertName(extenelAdvert.getAdvertName());
			abvo.setAdvertPicUrl(IMG_HOST + "/" + extenelAdvert.getAdvertPicUrl());
			if (null != recentAdvert.getUpdateTime()) {
				abvo.setUpdateTime(sf.format(recentAdvert.getUpdateTime()));
			} else {
				abvo.setUpdateTime(sf.format(new Date()));
			}
			abvo.setLinkUrl(recentAdvert.getLinkUrl());
			abvo.setLinkType(String.valueOf(recentAdvert.getLinkType()));
			abvo.setText(recentAdvert.getText());
			homePageVo.setExternalDevelopment(abvo);
		}
		result.setCode(Constant.SECCUESS);
		result.setMsg("成功返回数据");
		result.setData(homePageVo);
		return result;
	}

	/**
	 * 获取更多动态数据
	 *
	 * @param type
	 *            调用不同获取更多动态数据接口（1、获取猛龙更多动态数据；2、获取外部更多动态数据）
	 * @return
	 */
	@RequestMapping("/getManyDevelopment")
	@ResponseBody
	public ResultInfo<List<AdvertBottomVo>> getManyDevelopment(String pageNo, String pageSize, String type) {
		ResultInfo<List<AdvertBottomVo>> result = new ResultInfo<>();
		// AdvertManyDevelopment manyDevelopment = new AdvertManyDevelopment();
		AdvertMengLong adml = new AdvertMengLong();
		adml.setType(2);// 充电桩类型
		adml.setAdvertType(6);// 区域类型(最新动态)
		if (null != type) {
			if ("1".equals(type)) {
				adml.setAdvertPosition("1");// 猛龙动态类型
			}
			if ("2".equals(type)) {
				adml.setAdvertPosition("2");// 外部动态类型
			}

			PageFinder<AdvertMengLong> advertPage = advertMengLongService
					.getAdvertRecentPagedList(new Query(Integer.valueOf(pageNo), Integer.valueOf(pageSize), adml));
			List<AdvertMengLong> advertList = new ArrayList<>();
			if (advertPage.getData().size() > 0) {
				advertList = advertPage.getData();
			}

			List<AdvertBottomVo> bottomList = new ArrayList<>();
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:MM:SS");
			if (advertList.size() > 0 && !advertList.isEmpty()) {
				for (AdvertMengLong am : advertList) {
					AdvertBottomVo abv = new AdvertBottomVo();
					abv.setAdvertName(am.getAdvertName());
					abv.setAdvertPicUrl(IMG_HOST + "/" + am.getAdvertPicUrl());
					abv.setUpdateTime(sf.format(am.getUpdateTime()));
					abv.setLinkUrl(am.getLinkUrl());
					abv.setLinkType(String.valueOf(am.getLinkType()));
					abv.setText(am.getText());
					bottomList.add(abv);
				}
				// if ("1".equals(type)) {
				// manyDevelopment.setRecentDevelopmentList(bottomList);//
				// 猛龙动态类型
				// }
				// if ("2".equals(type)) {
				// manyDevelopment.setRecentDevelopmentList(bottomList);//
				// 外部动态类型
				// }
				result.setCode(Constant.SECCUESS);
				result.setMsg("成功返回数据");
				result.setData(bottomList);
				return result;
			} else {
				result.setCode(Constant.OTHER);
				result.setMsg("暂时无更多动态数据");
				return result;
			}
		} else {
			result.setCode(Constant.FAIL);
			result.setMsg("获取动态数据错误");
			return result;
		}
	}

	/**
	 * 根据经纬度获取最近运营城市信息
	 *
	 * @param longitude
	 *            经度
	 * @param latitude
	 *            维度
	 * @return
	 */
	private COperatingCityVo getOperatingCity(String longitude, String latitude) {
		COperatingCity operatingCity = coperatingCityService.getNearOperatingCity(longitude, latitude);
		COperatingCityVo cv = new COperatingCityVo();
		if (null != operatingCity) {
			cv.setOperatingCityName((operatingCity.getCityName()));
			cv.setOperatingCityNo(operatingCity.getOperatingCityNo());
			cv.setLantitude(operatingCity.getLatitude());
			cv.setLongitude(operatingCity.getLongitude());
			return cv;
		} else {
			return null;
		}
	}

	/**
	 * 中间位置广告数据
	 *
	 * @return
	 */
	private List<AdvertCenterVo> advertCenterList() {
		List<AdvertCenterVo> advertCenterVoList = new ArrayList<>();
		// 第一位置
		AdvertCenterVo av1 = advertMengLongService.selectByTypeAndPosition(1, IMG_HOST, 3);
		if (null != av1) {
			advertCenterVoList.add(av1);
		}
		// 第二位置
		AdvertCenterVo av2 = advertMengLongService.selectByTypeAndPosition(2, IMG_HOST, 3);
		if (null != av2) {
			advertCenterVoList.add(av2);
		}
		// 第三位置
		AdvertCenterVo av3 = advertMengLongService.selectByTypeAndPosition(3, IMG_HOST, 3);
		if (null != av3) {
			advertCenterVoList.add(av3);
		}
		// 第四位置
		AdvertCenterVo av4 = advertMengLongService.selectByTypeAndPosition(4, IMG_HOST, 3);
		if (null != av4) {
			advertCenterVoList.add(av4);
		}
		// 第五位置
		AdvertCenterVo av5 = advertMengLongService.selectByTypeAndPosition(5, IMG_HOST, 3);
		if (null != av5) {
			advertCenterVoList.add(av5);
		}
		// 第六位置
		AdvertCenterVo av6 = advertMengLongService.selectByTypeAndPosition(6, IMG_HOST, 3);
		if (null != av6) {
			advertCenterVoList.add(av6);
		}
		// 第七位置
		AdvertCenterVo av7 = advertMengLongService.selectByTypeAndPosition(7, IMG_HOST, 3);
		if (null != av7) {
			advertCenterVoList.add(av7);
		}
		if (advertCenterVoList.size() > 0) {
			return advertCenterVoList;
		} else {
			return null;
		}
	}

	/**
	 * 停车场首页模糊搜索
	 *
	 * @param parkName
	 *            停车场名称
	 * @return
	 */
	@RequestMapping("/searchInfo")
	@ResponseBody
	public ResultInfo<List<CParkingVo>> searchInfo(String parkName, String pageNo, String pageSize,
			String longitude, String latitude) {
		ResultInfo<List<CParkingVo>> result = new ResultInfo<>();
		CParkingVo park = new CParkingVo();
		if (null != parkName) {
			park.setParkingName(parkName);// 站名称
		}
		if(!StringUtils.isEmpty(longitude)){
			park.setLongitude(longitude);
		}
		if(!StringUtils.isEmpty(latitude)){
			park.setLatitude(latitude);
		}
		PageFinder<CParkingVo> pageList = cparkingService
				.searchParkVoList(new Query(Integer.valueOf(pageNo), Integer.valueOf(pageSize), park));
		List<CParkingVo> parkList = pageList.getData();
		if (parkList.size() > 0) {
			result.setCode(Constant.SECCUESS);
			result.setMsg("成功返回数据");
			result.setData(parkList);
			return result;
		} else {
			result.setCode(Constant.OTHER);
			result.setMsg("没有匹配到您搜索的目的地");
			return result;
		}
	}

}
