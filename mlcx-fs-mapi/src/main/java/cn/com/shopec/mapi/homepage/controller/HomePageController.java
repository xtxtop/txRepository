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
import cn.com.shopec.core.ml.model.CMatching;
import cn.com.shopec.core.ml.model.COperatingCity;
import cn.com.shopec.core.ml.service.AdvertMengLongService;
import cn.com.shopec.core.ml.service.CMatchingService;
import cn.com.shopec.core.ml.service.COperatingCityService;
import cn.com.shopec.core.ml.service.ChargingStationService;
import cn.com.shopec.core.ml.vo.AdvertCenterVo;
import cn.com.shopec.core.ml.vo.AdvertTextVo;
import cn.com.shopec.core.ml.vo.COperatingCityVo;
import cn.com.shopec.core.ml.vo.ChargingStationVo;
import cn.com.shopec.mapi.homepage.vo.AdvertBottomVo;
import cn.com.shopec.mapi.homepage.vo.AdvertTopVo;
import cn.com.shopec.mapi.homepage.vo.HomePageVo;
import cn.com.shopec.mapi.station.vo.CMatchingVo;

/**
 * app充电桩总首页
 *
 * @author Administrator
 */
@Controller
@RequestMapping("/app/homePage/")
public class HomePageController {

	@Resource
	private AdvertMengLongService advertMengLongService;

	@Resource
	private ChargingStationService chargignStationService;

	@Resource
	private COperatingCityService coperatingCityService;

	@Resource
	private CMatchingService cmatchingService;

	@Value("$image_path")
	private String sysParam;

	@Value("${image_path}")
	private String IMG_HOST;

	/**
	 * 充电桩首页接口
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
		advertMengLong.setType(Constant.TYPE_2);// 充电桩类型
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
		ad.setType(Constant.TYPE_2);// 充电桩类型
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
		AdvertMengLong recentAdvert = advertMengLongService.getRecentDevelopment(1, 2);
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
		AdvertMengLong extenelAdvert = advertMengLongService.getRecentDevelopment(2, 2);
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
	 * 充电桩首页模糊搜索
	 *
	 * @param chargingStationName
	 *            站名称
	 * @return
	 */
	@RequestMapping("/searchInfo")
	@ResponseBody
	public ResultInfo<List<cn.com.shopec.mapi.station.vo.ChargingStationVo>> searchInfo(
			@RequestParam(value = "chargingStationName", required = false) String chargingStationName,String longitude,String latitude) {
		ResultInfo<List<cn.com.shopec.mapi.station.vo.ChargingStationVo>> result = new ResultInfo<>();
		ChargingStationVo chargingStation = new ChargingStationVo();
		if(chargingStationName!=null){
			chargingStation.setStationName(chargingStationName);// 站名称
		}
		if(longitude!=null){
			chargingStation.setLongitude(longitude);//经度
		}
		if(latitude!=null){
			chargingStation.setLatitude(latitude);//纬度
		}
		PageFinder<ChargingStationVo> pageList = chargignStationService.searchStationVoList(new Query(chargingStation));
		List<ChargingStationVo> chargingStationList = pageList.getData();
		List<cn.com.shopec.mapi.station.vo.ChargingStationVo> list = new ArrayList<>();
		if (chargingStationList.size() > 0) {
			for (ChargingStationVo cv : chargingStationList) {
				cn.com.shopec.mapi.station.vo.ChargingStationVo csv = new cn.com.shopec.mapi.station.vo.ChargingStationVo();
				csv.setStationNo(cv.getStationNo());
				csv.setStationName(cv.getStationName());
				csv.setStationUrl(IMG_HOST + "/" + cv.getFileUrl());
				csv.setChargeFastIdleSum(cv.getChargeFastIdleSum());
				csv.setChargeFastSum(cv.getChargeFastSum());
				csv.setChargeTrickleIdleSum(cv.getChargeTrickleIdleSum());
				csv.setChargeTrickleSum(cv.getChargeTrickleSum());
				csv.setCityId(cv.getCityId());
				csv.setCityName(cv.getCityName());
				csv.setDistrictId(cv.getDistrictId());
				csv.setDistrictName(cv.getDistrictName());
				if (null != cv.getIsAvailable()) {
					csv.setIsAvailable(String.valueOf(cv.getIsAvailable()));
				}
				// 套餐服务数据
				List<CMatchingVo> cmList = new ArrayList<>();
				String services = cv.getSupportedServices();
				String[] array1 = services.split(",");
				if (array1.length > 0) {
					for (int i = 0; i < array1.length; i++) {
						CMatching cm = cmatchingService.getCMatching(array1[i]);
						CMatchingVo cmVo = new CMatchingVo();
						cmVo.setMatchingId(cm.getMatchingId());
						cmVo.setMatchingName(cm.getMatchingName());
						cmVo.setMatchingPicUrl(IMG_HOST + "/" + cm.getMatchingPicUrl());
						cmList.add(cmVo);
					}
					csv.setMatchList(cmList);
				}
				if (null != cv.getDistance()) {
					csv.setDistance(String.valueOf(cv.getDistance()));
				}
				if (null != cv.getElectricPrice()) {
					csv.setElectricPrice(String.valueOf(cv.getElectricPrice()));
				} else {
					csv.setElectricPrice("0.0");
				}
				csv.setLatitude(cv.getLatitude());
				csv.setLongitude(cv.getLongitude());
				list.add(csv);
			}
			result.setCode(Constant.SECCUESS);
			result.setMsg("成功返回数据");
			result.setData(list);
			return result;
		} else {
			result.setCode(Constant.OTHER);
			result.setMsg("没有匹配到您搜索的目的地");
			return result;
		}
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
		AdvertCenterVo av1 = advertMengLongService.selectByTypeAndPosition(1, IMG_HOST, 2);
		if (null != av1) {
			advertCenterVoList.add(av1);
		}
		// 第二位置
		AdvertCenterVo av2 = advertMengLongService.selectByTypeAndPosition(2, IMG_HOST, 2);
		if (null != av2) {
			advertCenterVoList.add(av2);
		}
		// 第三位置
		AdvertCenterVo av3 = advertMengLongService.selectByTypeAndPosition(3, IMG_HOST, 2);
		if (null != av3) {
			advertCenterVoList.add(av3);
		}
		// 第四位置
		AdvertCenterVo av4 = advertMengLongService.selectByTypeAndPosition(4, IMG_HOST, 2);
		if (null != av4) {
			advertCenterVoList.add(av4);
		}
		// 第五位置
		AdvertCenterVo av5 = advertMengLongService.selectByTypeAndPosition(5, IMG_HOST, 2);
		if (null != av5) {
			advertCenterVoList.add(av5);
		}
		// 第六位置
		AdvertCenterVo av6 = advertMengLongService.selectByTypeAndPosition(6, IMG_HOST, 2);
		if (null != av6) {
			advertCenterVoList.add(av6);
		}
		// 第七位置
		AdvertCenterVo av7 = advertMengLongService.selectByTypeAndPosition(7, IMG_HOST, 2);
		if (null != av7) {
			advertCenterVoList.add(av7);
		}
		if (advertCenterVoList.size() > 0) {
			return advertCenterVoList;
		} else {
			return null;
		}
	}
}
