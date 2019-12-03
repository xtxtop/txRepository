package cn.com.shopec.mapi.station.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.common.constants.Constant;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.ml.model.AdvertMengLong;
import cn.com.shopec.core.ml.model.CLabel;
import cn.com.shopec.core.ml.model.CMatching;
import cn.com.shopec.core.ml.service.AdvertMengLongService;
import cn.com.shopec.core.ml.service.CLabelService;
import cn.com.shopec.core.ml.service.CMatchingService;
import cn.com.shopec.core.ml.service.ChargingStationService;
import cn.com.shopec.core.ml.vo.AdvertTextVo;
import cn.com.shopec.core.ml.vo.CLabelVo;
import cn.com.shopec.core.ml.vo.ChargingStationDetailsVo;
import cn.com.shopec.mapi.common.controller.BaseController;
import cn.com.shopec.mapi.homepage.vo.AdvertTopVo;
import cn.com.shopec.mapi.station.vo.CMatchingVo;
import cn.com.shopec.mapi.station.vo.ChargingStationVo;
import cn.com.shopec.mapi.station.vo.ChargingStationVoResource;

/**
 * 充电站列表接口
 */
@Controller
@RequestMapping("/app/station")
public class ChargingStationController extends BaseController {
	@Resource
	private AdvertMengLongService advertMengLongService;
	@Resource
	private ChargingStationService chargingStationService;
	@Resource
	private CLabelService clabelService;
	@Resource
	private CMatchingService cmatchingService;
	@Value("${image_path}")
	private String imagePath;

	/**
	 * 获取静态资源
	 * 
	 * @return
	 */
	@RequestMapping(value = "/getChargingStationVoResource", method = RequestMethod.POST)
	@ResponseBody
	public ResultInfo<ChargingStationVoResource> getChargingStationVoResource() {
		ResultInfo<ChargingStationVoResource> result = new ResultInfo<ChargingStationVoResource>();
		ChargingStationVoResource resource = new ChargingStationVoResource();
		// 获取广告集合
		AdvertMengLong advert = new AdvertMengLong();
		advert.setType(Constant.TYPE_4);// 广告类型
		advert.setAdvertPosition("1");// 顶部位置轮播图
		List<AdvertMengLong> advertList = advertMengLongService.getAdvertList(new Query(advert));
		List<AdvertTopVo> advertTopVoList = new ArrayList<>();
		if (advertList.size() > 0) {
			for (AdvertMengLong ad : advertList) {
				AdvertTopVo advertTopVo = new AdvertTopVo();
				advertTopVo.setAdvertPicUrl(imagePath + "/" + ad.getAdvertPicUrl());
				advertTopVo.setLinkUrl(ad.getLinkUrl());
				advertTopVo.setAdvertName(ad.getAdvertName());
				advertTopVoList.add(advertTopVo);
			}
			resource.setAdvertList(advertTopVoList);
		}

		// 获取标签数据
		CLabel clabel = new CLabel();
		clabel.setEnableStatus(1);// 启用的标签
		clabel.setLabelType(1);// 停车类型标签
		List<CLabel> labelList = clabelService.getCLabelList(new Query(clabel));
		List<CLabelVo> labelVoList = new ArrayList<>();
		labelVoList.add(new CLabelVo("1", "离我最近"));
		labelVoList.add(new CLabelVo("2", "价格最低"));
		labelVoList.add(new CLabelVo("3", "精品站"));
		labelVoList.add(new CLabelVo("4", "超级站"));
		if (labelList.size() > 0) {
			for (CLabel cla : labelList) {
				CLabelVo laVo = new CLabelVo();
				laVo.setLabelId(cla.getLabelId());
				laVo.setLabelName(cla.getLabelName());
				labelVoList.add(laVo);
			}
			resource.setLabelList(labelVoList);
		}

		// 系统数据(顶部滚动文字)
		AdvertMengLong ad = new AdvertMengLong();
		ad.setType(Constant.TYPE_4);// 充电站类型
		// ad.setAdvertType(5);// 滚动文字区域类型
		// ad.setAdvertPosition("8");// 系统顶部滚动文字
		ad.setAdvertPosition("2");// 充电站列表顶部滚动文字
		List<AdvertMengLong> adList = advertMengLongService.getAdvertList(new Query(ad));
		List<AdvertTextVo> sysParamList = new ArrayList<>();
		if (advertList.size() > 0) {
			for (AdvertMengLong a : adList) {
				AdvertTextVo avo = new AdvertTextVo();
				avo.setAdvertName(a.getAdvertName());
				sysParamList.add(avo);
			}
			resource.setAdvertTextVoList(sysParamList);
		}

		result.setCode(Constant.SECCUESS);
		result.setMsg("成功返回数据");
		result.setData(resource);
		return result;
	}

	/**
	 * 获取充电站列表
	 * 
	 * @param longitude
	 *            经度
	 * @param latitude
	 *            维度
	 * @param cityId
	 *            运营城市ID
	 * @param pageNo
	 *            当前
	 * @param pageSize
	 *            每页显示条数
	 * @param labelList
	 *            标签帅选条件
	 * @return
	 */
	@RequestMapping(value = "/pageChargingStationList")
	@ResponseBody
	public ResultInfo<List<ChargingStationVo>> pageChargingStationList(
			@RequestParam(value = "longitude") String longitude, @RequestParam(value = "latitude") String latitude,
			@RequestParam(value = "cityId") String cityId,
			@RequestParam(value = "pageNo", required = false) String pageNo,
			@RequestParam(value = "pageSize", required = false) String pageSize,
			@RequestParam(value = "labelList", required = false) String[] labelList) {

		ResultInfo<List<ChargingStationVo>> result = new ResultInfo<>();
		if (null == longitude || null == latitude || null == cityId) {
			result.setCode(Constant.FAIL);
			result.setMsg("没有经纬度和最近运营城市ID，无法显示距离您最近的充电站");
			return result;
		} else {
			List<cn.com.shopec.core.ml.vo.ChargingStationVo> list = chargingStationService
					.pageChargingStationVoList(longitude, latitude, cityId, labelList, pageNo, pageSize);
			if (list.size() > 0 && list != null) {
				List<ChargingStationVo> listVo = toChargingStationVo(list);
				result.setData(listVo);
				result.setCode(Constant.SECCUESS);
				result.setMsg("成功返回数据");
				return result;
			} else {
				result.setCode(Constant.OTHER);
				result.setMsg("充电站暂时没有数据");
				return result;
			}
		}
	}

	/**
	 * 充电站收藏列表
	 * 
	 * @param memberNo
	 *            会员编号
	 * @param longitude
	 *            经度
	 * @param latitude
	 *            维度
	 * @param pageNo
	 *            当前页
	 * @param pageSize
	 *            每页显示条数
	 * @return
	 */
	@RequestMapping("getCollectionList")
	@ResponseBody
	public ResultInfo<List<ChargingStationVo>> getCollectionList(@RequestParam String memberNo,
			@RequestParam(value = "longitude", required = false) String longitude,
			@RequestParam(value = "latitude", required = false) String latitude, @RequestParam String pageNo,
			@RequestParam String pageSize) {

		cn.com.shopec.core.ml.vo.ChargingStationVo station = new cn.com.shopec.core.ml.vo.ChargingStationVo();
		ResultInfo<List<ChargingStationVo>> result = new ResultInfo<>();
		if (!StringUtils.isEmpty(longitude) && !StringUtils.isEmpty(latitude)) {
			station.setLongitude(longitude);
			station.setLatitude(latitude);
		}
		station.setMember(memberNo);
		Query query = new Query(Integer.valueOf(pageNo), Integer.valueOf(pageSize), station);
		List<cn.com.shopec.core.ml.vo.ChargingStationVo> list = chargingStationService
				.getChargingStationVoListByCollection(query);
		if (!CollectionUtils.isEmpty(list)) {
			List<ChargingStationVo> listVo = toChargingStationVo(list);
			result.setData(listVo);
			result.setCode(Constant.SECCUESS);
			result.setMsg("成功返回数据");
			return result;
		} else {
			result.setCode(Constant.OTHER);
			result.setMsg("没有收藏数据");
			return result;
		}
	}

	/**
	 * 模型转换
	 * 
	 * @param list
	 * @return
	 */
	private List<ChargingStationVo> toChargingStationVo(List<cn.com.shopec.core.ml.vo.ChargingStationVo> list) {
		List<ChargingStationVo> li = new ArrayList<>();
		for (cn.com.shopec.core.ml.vo.ChargingStationVo staVo : list) {
			ChargingStationVo stVo = new ChargingStationVo();
			stVo.setChargeFastSum(staVo.getChargeFastSum());// 快充总数
			stVo.setChargeFastIdleSum(staVo.getChargeFastIdleSum());// 快充空闲个数
			stVo.setChargeTrickleIdleSum(staVo.getChargeTrickleIdleSum());// 慢充总数
			stVo.setChargeTrickleSum(staVo.getChargeTrickleSum());// 慢充空闲个数
			stVo.setCityId(staVo.getCityId());
			stVo.setCityName(staVo.getCityName());
			stVo.setDistrictName(staVo.getDistrictName());
			stVo.setDistrictId(staVo.getDistrictId());
			if (null != staVo.getElectricPrice()) {
				stVo.setElectricPrice(staVo.getElectricPrice().toString());
			} else {
				stVo.setElectricPrice("0.0");
			}

			if (null != staVo.getIsAvailable()) {
				stVo.setIsAvailable(staVo.getIsAvailable().toString());
			} else {
				stVo.setIsAvailable("0");
			}

			stVo.setLongitude(staVo.getLongitude());
			stVo.setLatitude(staVo.getLatitude());
			stVo.setStationNo(staVo.getStationNo());
			stVo.setStationName(staVo.getStationName());
			stVo.setStationUrl(imagePath + "/" + staVo.getFileUrl());
			if (null != staVo.getDistance()) {
				stVo.setDistance(String.valueOf(staVo.getDistance()));
			} else {
				stVo.setDistance("0");
			}

			// 套餐服务数据
			List<CMatchingVo> cmList = new ArrayList<>();
			String services = staVo.getSupportedServices();
			String[] array1 = services.split(",");
			if (array1.length > 0) {
				for (int i = 0; i < array1.length; i++) {
					CMatching cm = cmatchingService.getCMatching(array1[i]);
					CMatchingVo cmVo = new CMatchingVo();
					cmVo.setMatchingId(cm.getMatchingId());
					cmVo.setMatchingName(cm.getMatchingName());
					cmVo.setMatchingPicUrl(imagePath + "/" + cm.getMatchingPicUrl());
					cmList.add(cmVo);
				}
				stVo.setMatchList(cmList);
			}
			li.add(stVo);
		}
		return li;
	}

	/**
	 * 获取充电站详情
	 * 
	 * @param longitude
	 *            经度
	 * @param latitude
	 *            维度
	 * @param memberNo
	 *            会员编号
	 * @param stationNo
	 *            充电站编号
	 */
	@RequestMapping("/chargingStationDatils")
	@ResponseBody
	public ResultInfo<ChargingStationDetailsVo> chargingStationDatils(
			@RequestParam(value = "longitude", required = false) String longitude,
			@RequestParam(value = "latitude", required = false) String latitude,
			@RequestParam(value = "memberNo") String memberNo, String stationNo) {
		ChargingStationDetailsVo search = new ChargingStationDetailsVo();
		search.setStationNo(stationNo);
		search.setMemberNo(memberNo);
		search.setLongitude(longitude);
		search.setLatitude(latitude);
		ResultInfo<ChargingStationDetailsVo> resultInfo = new ResultInfo<>();
		if (search.verifyParam()) {
			ChargingStationDetailsVo data = chargingStationService.getChargingStationDatils(search);
			if (data == null) {
				resultInfo.setCode(Constant.FAIL);
				resultInfo.setMsg("数据异常");
			} else {
				resultInfo.setData(data);
				resultInfo.setCode(Constant.SECCUESS);
				resultInfo.setMsg("查询成功");
			}
		} else {
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg("参数有误");
		}
		return resultInfo;
	}
}
