package cn.com.shopec.mapi.park.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.common.constants.Constant;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.ml.model.AdvertMengLong;
import cn.com.shopec.core.ml.model.CLabel;
import cn.com.shopec.core.ml.service.AdvertMengLongService;
import cn.com.shopec.core.ml.service.CLabelService;
import cn.com.shopec.core.ml.service.COperatingCityService;
import cn.com.shopec.core.ml.vo.AdvertTextVo;
import cn.com.shopec.core.ml.vo.BannerVo;
import cn.com.shopec.core.ml.vo.CLabelVo;
import cn.com.shopec.core.mlparking.model.CParkLock;
import cn.com.shopec.core.mlparking.model.CPliesNumber;
import cn.com.shopec.core.mlparking.service.CParkLockService;
import cn.com.shopec.core.mlparking.service.CParkingService;
import cn.com.shopec.core.mlparking.service.CPliesNumberService;
import cn.com.shopec.core.mlparking.vo.CParkingDetailVo;
import cn.com.shopec.core.mlparking.vo.CParkingDetailVoTwo;
import cn.com.shopec.core.mlparking.vo.CParkingVo;
import cn.com.shopec.core.mlparking.vo.Lock;
import cn.com.shopec.core.mlparking.vo.ParkSpaceVo;
import cn.com.shopec.core.mlparking.vo.SpaceNumberList;
import cn.com.shopec.mapi.common.controller.BaseController;
import cn.com.shopec.mapi.park.controller.vo.ParkResource;

/**
 * 猛龙停车场
 * 
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/app/park")
public class CparkController extends BaseController {

	@Resource
	private CParkingService cparkingService;

	@Resource
	private COperatingCityService coperatingCityService;

	@Resource
	private AdvertMengLongService advertMengLongService;

	@Resource
	private CLabelService clabelService;

	@Resource
	private CPliesNumberService cpliesNumberService;
	@Resource
	private CParkLockService parkLockService;

	@Value("${image_path}")
	private String imagePath;

	/**
	 * 获取停车场的静态资源
	 * 
	 * @return
	 */
	@RequestMapping("/getParkingVoResource")
	@ResponseBody
	public ResultInfo<ParkResource> getParkingVoResource() {

		ResultInfo<ParkResource> result = new ResultInfo<>();
		ParkResource parkResource = new ParkResource();

		// 顶部轮播图
		AdvertMengLong advertMengLong = new AdvertMengLong();
		advertMengLong.setType(Constant.TYPE_17);// 停车场列表
		advertMengLong.setAdvertPosition("1");// 顶部轮播图
		List<AdvertMengLong> advertMengLongList = advertMengLongService
				.getAdvertList(new Query(advertMengLong));
		List<BannerVo> bannerList = new ArrayList<>();
		if (advertMengLongList.size() > 0) {
			for (AdvertMengLong ad : advertMengLongList) {
				BannerVo adTopVo = new BannerVo();
				adTopVo.setLinkUrl(ad.getLinkUrl());
				adTopVo.setLinkType(String.valueOf(ad.getLinkType()));
				adTopVo.setText(ad.getText());
				adTopVo.setAdvertPicUrl(imagePath + "/" + ad.getAdvertPicUrl());
				bannerList.add(adTopVo);
			}
			parkResource.setAdvertList(bannerList);
		}

		// 滚动文字
		AdvertMengLong ad = new AdvertMengLong();
		ad.setType(Constant.TYPE_17);// 无人停车场
		ad.setAdvertPosition("2");// 系统滚动文字（如：0-1成低首付）
		List<AdvertMengLong> advertList = advertMengLongService
				.getAdvertList(new Query(ad));
		List<AdvertTextVo> sysParamList = new ArrayList<>();
		if (advertList.size() > 0) {
			for (AdvertMengLong a : advertList) {
				AdvertTextVo avo = new AdvertTextVo();
				avo.setAdvertName(a.getAdvertName());
				sysParamList.add(avo);
			}
			parkResource.setAdvertTextVoList(sysParamList);
		}

		// 获取标签数据
		CLabel clabel = new CLabel();
		clabel.setEnableStatus(1);// 启用的标签
		clabel.setLabelType(2);// 停车类型标签
		List<CLabel> labelList = clabelService.getCLabelList(new Query(clabel));
		List<CLabelVo> labelVoList = new ArrayList<>();
		labelVoList.add(new CLabelVo("L001", "离我最近"));
		labelVoList.add(new CLabelVo("L002", "价格最低"));
		//labelVoList.add(new CLabelVo("L003", "含充电桩"));
		if (labelList.size() > 0) {
			for (CLabel cla : labelList) {
				CLabelVo laVo = new CLabelVo();
				laVo.setLabelId(cla.getLabelId());
				laVo.setLabelName(cla.getLabelName());
				labelVoList.add(laVo);
			}
			parkResource.setLabelList(labelVoList);
		}
		result.setCode(Constant.SECCUESS);
		result.setMsg("成功返回数据");
		result.setData(parkResource);
		return result;
	}

	/**
	 * 停车场列表
	 * 
	 * @param longitude
	 *            经度
	 * @param latitude
	 *            维度
	 * @param cityId
	 *            运营城市ID
	 * @param pageNo
	 * @param pageSize
	 * @param labelList
	 *            标签集合
	 * @return
	 */
	@RequestMapping("/pageParkingVoList")
	@ResponseBody
	public ResultInfo<List<CParkingVo>> pageParkingVoList(String longitude,
			String latitude, String cityId, String pageNo, String pageSize,
			String[] labelList,String parkingName) {
		ResultInfo<List<CParkingVo>> result = new ResultInfo<List<CParkingVo>>();
		if (null == latitude || null == longitude || null == cityId) {
			result.setCode(Constant.FAIL);
			result.setMsg("没有开启定位，获取不到最近运营城市ID，无法显示距离您最近的停车场");
		} else {
			CParkingVo park = new CParkingVo();
			park.setOperatingCityNo(cityId);
			park.setLongitude(longitude);
			park.setLatitude(latitude);
			if (null != labelList && labelList.length > 0) {
				park.setLabelList(labelList);
			}
			if(!StringUtils.isEmpty(parkingName)){
				park.setParkingName(parkingName);
			}
			park.setFileUrl(imagePath);
			PageFinder<CParkingVo> parkingVoList = cparkingService
					.pageCparkingList(new Query(Integer.valueOf(pageNo),
							Integer.valueOf(pageSize), park));
			List<CParkingVo> lst = parkingVoList.getData();
			if (lst.size() > 0) {
				result.setCode(Constant.SECCUESS);
				result.setMsg("成功返回数据");
				result.setData(lst);
				return result;
			} else {
				result.setCode(Constant.OTHER);
				result.setMsg("此条件下筛选无数据");
				return result;
			}
		}

		return result;
	}

	/**
	 * 停车场详情
	 * 
	 * @param memberNo
	 * @param parkingNo
	 *            停车场编号
	 * @param longitude
	 * @param latitude
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	/**
	 * @param memberNo
	 * @param longitude
	 * @param latitude
	 * @param parkingNo
	 * @return
	 */
	@RequestMapping("/getCparkingDetail")
	@ResponseBody
	public ResultInfo<CParkingDetailVoTwo> getCparkingDetail(String memberNo,
			String longitude, String latitude, String parkingNo) {
		ResultInfo<CParkingDetailVoTwo> result = new ResultInfo<CParkingDetailVoTwo>();
		CParkingDetailVo park = new CParkingDetailVo();
		if (!StringUtils.isEmpty(longitude) && !StringUtils.isEmpty(latitude)) {
			park.setLongitude(longitude);
			park.setLatitude(latitude);
		}
		// park.setMemberNo(memberNo);
		if (StringUtils.isEmpty(parkingNo)) {
			result.setCode(Constant.FAIL);
			result.setMsg("查询失败,参数格式不正确!");
			return result;
		}
		park.setParkingNo(parkingNo);
		CParkingDetailVo parkDetailVo = cparkingService
				.getParkingDetailVo(new Query(park));
		// 每个停车场每层楼对应的总车位数和空闲个数
		CPliesNumber pileNumber = new CPliesNumber();
		pileNumber.setParkingNo(parkingNo);
		List<CPliesNumber> pliesNumberList = cpliesNumberService
				.getCPliesNumberList(new Query(pileNumber));
		List<SpaceNumberList> snb = new ArrayList<SpaceNumberList>();// 车位分层处理
		List<ParkSpaceVo> groundList = new ArrayList<ParkSpaceVo>();// 地面
		List<ParkSpaceVo> underList = new ArrayList<ParkSpaceVo>();// 地下
		List<ParkSpaceVo> floorList = new ArrayList<ParkSpaceVo>();// 楼层
		if (pliesNumberList.size() > 0) {
			for (CPliesNumber cp : pliesNumberList) {
				if (cp.getSpaceType() == 1) {// 地下
					ParkSpaceVo pp = new ParkSpaceVo();
					pp.setParkTotalAnyNumber(String.valueOf(cp
							.getParkingSpaceNumber()));
					pp.setParkSpaceAnyNumber(String.valueOf(cp
							.getSurplusSpaceNumber()));
					pp.setSpaceName(cp.getPliesNumber());
					// 获取地锁信息
					if(parkDetailVo.getParkingType()==1){
						CParkLock p = new CParkLock();
						p.setPliesNumberNo(cp.getParkingPliesNo());
						List<CParkLock> cParkLockList = parkLockService
								.getCParkLockList(new Query(p));
						List<Lock> lockList = new ArrayList<Lock>();// 地锁类
						for (CParkLock p1 : cParkLockList) {
							Lock l = new Lock();
							l.setLockNo(p1.getParkLockNo());
							l.setLockName("猛龙共享地锁"+p1.getParkingLockName());
							l.setLockStatus(p1.getLockStatus());
							l.setParkingLockStatus(p1.getParkingLockStatus());
							l.setSpaceNo(p1.getSpaceNo());
							lockList.add(l);
						}
						pp.setLockList(lockList);
					}
					underList.add(pp);
				} else if (cp.getSpaceType() == 2) {// 地面
					ParkSpaceVo pp = new ParkSpaceVo();
					pp.setParkTotalAnyNumber(String.valueOf(cp
							.getParkingSpaceNumber()));
					pp.setParkSpaceAnyNumber(String.valueOf(cp
							.getSurplusSpaceNumber()));
					pp.setSpaceName(cp.getPliesNumber());
					if(parkDetailVo.getParkingType()==1){
						CParkLock p = new CParkLock();
						p.setPliesNumberNo(cp.getParkingPliesNo());
						List<CParkLock> cParkLockList = parkLockService
								.getCParkLockList(new Query(p));
						List<Lock> lockList = new ArrayList<Lock>();
						for (CParkLock p1 : cParkLockList) {
							Lock l = new Lock();
							l.setLockNo(p1.getParkLockNo());
							l.setLockName("猛龙共享地锁"+p1.getParkingLockName());
							l.setLockStatus(p1.getLockStatus());
							l.setParkingLockStatus(p1.getParkingLockStatus());
							l.setSpaceNo(p1.getSpaceNo());
							lockList.add(l);
						}
						pp.setLockList(lockList);
					}
					groundList.add(pp);
				} else {// 楼层
					ParkSpaceVo pp = new ParkSpaceVo();
					pp.setParkTotalAnyNumber(String.valueOf(cp
							.getParkingSpaceNumber()));
					pp.setParkSpaceAnyNumber(String.valueOf(cp
							.getSurplusSpaceNumber()));
					pp.setSpaceName(cp.getPliesNumber());
					if(parkDetailVo.getParkingType()==1){
						CParkLock p = new CParkLock();
						p.setPliesNumberNo(cp.getParkingPliesNo());
						List<CParkLock> cParkLockList = parkLockService
								.getCParkLockList(new Query(p));
						List<Lock> lockList = new ArrayList<Lock>();
						for (CParkLock p1 : cParkLockList) {
							Lock l = new Lock();
							l.setLockNo(p1.getParkLockNo());
							l.setLockName("猛龙共享地锁"+p1.getParkingLockName());
							l.setLockStatus(p1.getLockStatus());
							l.setParkingLockStatus(p1.getParkingLockStatus());
							l.setSpaceNo(p1.getSpaceNo());
							lockList.add(l);
						}
						pp.setLockList(lockList);
					}
					floorList.add(pp);
				}

			}
			if (groundList != null && groundList.size() > 0) {
				SpaceNumberList groundListSn = new SpaceNumberList();// 分层地面
				groundListSn.setEntranceName(parkDetailVo.getParkingName()
						+ "地面入口");
				groundListSn.setChargingRules("免费" + parkDetailVo.getFreeTime()
						+ "分钟,超过" + parkDetailVo.getFreeTime() + "分钟按照每小时"
						+ parkDetailVo.getHoursCost() + "元计费,不满1小时按照1小时计算,"
						+ parkDetailVo.getDayMaxCost() + "(实际收费以停车场为准)");
				groundListSn.setTotalSpace(parkDetailVo
						.getGroundParkingSpaceNumber());
				groundListSn.setSurplusSpace(parkDetailVo
						.getGroundSurplusSpaceNumber());
				groundListSn.setSpaceList(groundList);
				groundListSn.setImg(parkDetailVo.getDetailsUrl());// 图片
				groundListSn.setDistance(parkDetailVo.getDistantce());// 距离
				groundListSn.setLon(parkDetailVo.getLongitude());// 经度
				groundListSn.setLat(parkDetailVo.getLatitude());// 纬度
				groundListSn.setBusinessHours(parkDetailVo.getBusinessHours());
				groundListSn.setSpaceType(2);
				snb.add(groundListSn);
			}
			if (underList != null && underList.size() > 0) {
				SpaceNumberList underListSn = new SpaceNumberList();// 分层地下
				underListSn.setEntranceName(parkDetailVo.getParkingName()
						+ "地下入口");
				underListSn.setChargingRules("免费" + parkDetailVo.getFreeTime()
						+ "分钟,超过" + parkDetailVo.getFreeTime() + "分钟按照每小时"
						+ parkDetailVo.getHoursCost() + "元计费,不满1小时按照1小时计算,"
						+ parkDetailVo.getDayMaxCost() + "(实际收费以停车场为准)");
				underListSn.setTotalSpace(parkDetailVo
						.getUndergroundParkingSpaceNumber());
				underListSn.setSurplusSpace(parkDetailVo
						.getUndergroundSurplusSpaceNumber());
				underListSn.setSpaceList(underList);
				underListSn.setImg(parkDetailVo.getDetailsUrl());// 图片
				underListSn.setDistance(parkDetailVo.getDistantce());// 距离
				underListSn.setLon(parkDetailVo.getLongitude());// 经度
				underListSn.setLat(parkDetailVo.getLatitude());// 纬度
				underListSn.setBusinessHours(parkDetailVo.getBusinessHours());
				underListSn.setSpaceType(1);
				snb.add(underListSn);
			}
			if (floorList != null && floorList.size() > 0) {
				SpaceNumberList floorListSn = new SpaceNumberList();// 分层楼层
				floorListSn.setEntranceName(parkDetailVo.getParkingName()
						+ "楼层入口");
				floorListSn.setChargingRules("免费" + parkDetailVo.getFreeTime()
						+ "分钟,超过" + parkDetailVo.getFreeTime() + "分钟按照每小时"
						+ parkDetailVo.getHoursCost() + "元计费,不满1小时按照1小时计算,"
						+ parkDetailVo.getDayMaxCost() + "(实际收费以停车场为准)");
				floorListSn.setTotalSpace(parkDetailVo.getParkingTotalNumber());
				floorListSn.setSurplusSpace(parkDetailVo
						.getParkingSpaceNumber());
				floorListSn.setSpaceList(floorList);
				floorListSn.setImg(parkDetailVo.getDetailsUrl());// 图片
				floorListSn.setDistance(parkDetailVo.getDistantce());// 距离
				floorListSn.setLon(parkDetailVo.getLongitude());// 经度
				floorListSn.setLat(parkDetailVo.getLatitude());// 纬度
				floorListSn.setBusinessHours(parkDetailVo.getBusinessHours());
				floorListSn.setSpaceType(3);
				snb.add(floorListSn);
			}
		} // 赋值到APP模型
		CParkingDetailVoTwo pdvt = new CParkingDetailVoTwo();
		pdvt.setParkingNo(parkDetailVo.getParkingNo());
		pdvt.setParkingName(parkDetailVo.getParkingName());
		pdvt.setStar(parkDetailVo.getStar().trim());
		pdvt.setPhone("400-880-3711");
		pdvt.setTopBanner(parkDetailVo.getTopBanner());
		pdvt.setParkingType(parkDetailVo.getParkingType());
		/*
		pdvt.setParkingStatus(parkDetailVo.getParkingStatus());
		pdvt.setOperatingCityNo(parkDetailVo.getOperatingCityNo());
		pdvt.setOperatingCityName(parkDetailVo.getOperatingCityName());*/
		pdvt.setSysParamList(parkDetailVo.getSysParamList());
		pdvt.setParkSpaceList(snb);// 车位分布
		pdvt.setMemberNo(memberNo);
		result.setData(pdvt);
		result.setCode(Constant.SECCUESS);
		result.setMsg("查询成功");
		return result;
	}
}
