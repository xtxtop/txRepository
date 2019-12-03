package cn.com.shopec.mapi.car.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;

import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.common.constants.Constant;
import cn.com.shopec.common.utils.ECDateUtils;
import cn.com.shopec.common.utils.ECGeoCoordinateTransformUtil;
import cn.com.shopec.common.utils.ECNumberUtils;
import cn.com.shopec.common.utils.HttpURLRequestUtil;
import cn.com.shopec.common.utils.RankingStr;
import cn.com.shopec.core.car.common.CarConstant;
import cn.com.shopec.core.car.dao.CarDao;
import cn.com.shopec.core.car.model.Car;
import cn.com.shopec.core.car.model.CarAndStatus;
import cn.com.shopec.core.car.model.CarDetail;
import cn.com.shopec.core.car.model.CarIllegal;
import cn.com.shopec.core.car.model.CarSeries;
import cn.com.shopec.core.car.model.CarStatus;
import cn.com.shopec.core.car.service.CarIllegalService;
import cn.com.shopec.core.car.service.CarSeriesService;
import cn.com.shopec.core.car.service.CarService;
import cn.com.shopec.core.car.service.CarStatusService;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.device.common.DeviceConstant;
import cn.com.shopec.core.device.model.Device;
import cn.com.shopec.core.device.service.DeviceService;
import cn.com.shopec.core.finace.common.DepositConstant;
import cn.com.shopec.core.finace.model.Deposit;
import cn.com.shopec.core.finace.model.DepositOrder;
import cn.com.shopec.core.finace.service.DepositOrderService;
import cn.com.shopec.core.map.service.BaiduGeocoderApiService;
import cn.com.shopec.core.marketing.model.Advert;
import cn.com.shopec.core.marketing.model.AppLocation;
import cn.com.shopec.core.marketing.model.CarRedPacket;
import cn.com.shopec.core.marketing.model.CarRedPacketVo;
import cn.com.shopec.core.marketing.model.PeakHours;
import cn.com.shopec.core.marketing.model.PricingRule;
import cn.com.shopec.core.marketing.model.PricingRuleCustomizedDate;
import cn.com.shopec.core.marketing.service.AdvertService;
import cn.com.shopec.core.marketing.service.AppLocationService;
import cn.com.shopec.core.marketing.service.CarRedPacketService;
import cn.com.shopec.core.marketing.service.PeakHoursService;
import cn.com.shopec.core.marketing.service.PricingDeductionRecordService;
import cn.com.shopec.core.marketing.service.PricingRuleCustomizedDateService;
import cn.com.shopec.core.marketing.service.PricingRuleService;
import cn.com.shopec.core.member.common.MemberConstant;
import cn.com.shopec.core.member.model.Member;
import cn.com.shopec.core.member.service.MemberService;
import cn.com.shopec.core.order.common.OrderConstant;
import cn.com.shopec.core.order.model.Order;
import cn.com.shopec.core.order.service.OrderService;
import cn.com.shopec.core.order.service.PricingPackOrderService;
import cn.com.shopec.core.resource.model.Charger;
import cn.com.shopec.core.resource.model.Park;
import cn.com.shopec.core.resource.model.ReturnArea;
import cn.com.shopec.core.resource.service.ChargerService;
import cn.com.shopec.core.resource.service.ParkService;
import cn.com.shopec.core.resource.service.ReturnAreaService;
import cn.com.shopec.core.search.service.BaseIndexService;
import cn.com.shopec.core.search.service.ReturnAreaIndexService;
import cn.com.shopec.core.system.model.DataDictItem;
import cn.com.shopec.core.system.model.SysParam;
import cn.com.shopec.core.system.model.SysRegion;
import cn.com.shopec.core.system.service.DataDictItemService;
import cn.com.shopec.core.system.service.SysParamService;
import cn.com.shopec.core.system.service.SysRegionService;
import cn.com.shopec.mapi.car.vo.CarControlVo;
import cn.com.shopec.mapi.car.vo.CarStatusVo;
import cn.com.shopec.mapi.car.vo.CarVo;
import cn.com.shopec.mapi.car.vo.CityVo;
import cn.com.shopec.mapi.car.vo.DeviceListVo;
import cn.com.shopec.mapi.car.vo.GoAddCarVo;
import cn.com.shopec.mapi.car.vo.PrebookVo;
import cn.com.shopec.mapi.car.vo.PricingRuleVo;
import cn.com.shopec.mapi.car.vo.SeaTingVo;
import cn.com.shopec.mapi.car.vo.parksVo;
import cn.com.shopec.mapi.car.vo.polygonalStationVo;
import cn.com.shopec.mapi.common.controller.BaseController;
import cn.com.shopec.mapi.member.vo.MemberStatusVO;
import cn.com.shopec.mapi.order.vo.OrderVO;
import cn.com.shopec.mapi.resource.vo.AppLocationVOSearchBaiDu;
import cn.com.shopec.mapi.resource.vo.AppLocationVOSearchBaiDu.AppLocationVOBaiDu.AddressComponent;

@Controller
@RequestMapping("/app/car")
public class CarController extends BaseController {

	private static String DEVICE_KEY = "";

	static {
		StringBuilder sb = new StringBuilder("");
		for (int i = 0; i < 128; i++) {
			sb.append('0');
		}
		DEVICE_KEY = sb.toString();

	}

	@Value("${image_path}")
	private String imgPath;
	@Value("${ak}")
	private String ak;

	@Resource
	private CarService carService;

	@Resource
	private CarStatusService carStatusService;

	@Resource
	private MemberService memberService;

	@Resource
	private AdvertService adertService;

	@Resource
	private SysParamService sysParamService;

	@Resource
	private DeviceService deviceService;

	@Resource
	private ParkService parkService;

	@Resource
	private OrderService orderService;
	@Resource
	private DepositOrderService depositOrderService;

	@Resource
	private PricingRuleService pricingRuleService;
	@Resource
	private PricingPackOrderService pricingPackOrderService;
	@Resource
	private PricingDeductionRecordService pricingDeductionRecordService;
	@Resource
	private CarDao carDao;
	@Resource
	private ReturnAreaIndexService returnAreaIndexService;
	@Resource
	private BaiduGeocoderApiService baiduGeocoderApiService;
	@Resource
	private PricingRuleCustomizedDateService pricingRuleCustomizedDateService;
	@Resource
	private BaseIndexService baseIndexService;
	@Resource
	private DataDictItemService dataDictItemService;
	@Resource
	private ReturnAreaService returnAreaService;
	@Resource
	private PeakHoursService peakHoursService;
	@Resource
	private CarSeriesService carSeriesService;
	@Resource
	private CarRedPacketService carRedPacketService;
	@Resource
	private AppLocationService appLocationService;
	@Resource
	private SysRegionService sysRegionService;
	@Resource
	private CarIllegalService carIllegalService;
	@Resource
	private ChargerService chargerService;

	/**
	 * 得到车辆控制接口
	 */
	@RequestMapping("/carControl")
	@ResponseBody
	public ResultInfo<CarControlVo> getCarControl() {
		SysParam s5 = sysParamService.getByParamKey(CarConstant.PHONE);
		SysParam s1 = sysParamService.getByParamKey(CarConstant.FINDCAR);
		SysParam s2 = sysParamService.getByParamKey(CarConstant.UNLOCKCARDOOR);
		SysParam s3 = sysParamService.getByParamKey(CarConstant.LOCKCARDOOR);
		SysParam s4 = sysParamService.getByParamKey(CarConstant.UNLOCKCARDOOR_DISTINCE_LIMIT);
		SysParam s6 = sysParamService.getByParamKey("RANGE");
		SysParam s7 = sysParamService.getByParamKey("IS_SHARE");
		SysParam s8 = sysParamService.getByParamKey("telePhone");
		SysParam s9 = sysParamService.getByParamKey("isDecrypt");
		SysParam s10 = sysParamService.getByParamKey("is_favourable");
		SysParam s11 = sysParamService.getByParamKey("is_seaTing");
		SysParam s12 = sysParamService.getByParamKey("is_car_photo");
		SysParam s13 = sysParamService.getByParamKey("RETURN_CAR_OUTSIDE_SWITCH");
		ResultInfo<CarControlVo> result = new ResultInfo<CarControlVo>();
		CarControlVo vo = new CarControlVo();
		if (s1 != null && s1.getParamValue() != null && !s1.getParamValue().equals("")) {
			vo.setFindCar(s1.getParamValue());
		}
		if (s2 != null && s2.getParamValue() != null && !s2.getParamValue().equals("")) {
			vo.setUnLockCarDoor(s2.getParamValue());
		}
		if (s3 != null && s3.getParamValue() != null && !s3.getParamValue().equals("")) {
			vo.setLockCarDoor(s3.getParamValue());
		}
		if (s4 != null && s4.getParamValue() != null && !s4.getParamValue().equals("")) {
			vo.setUnLockCarDoorDistance(s4.getParamValue());
		}
		if (s5 != null && s5.getParamValue() != null && !s5.getParamValue().equals("")) {
			vo.setPhone(s5.getParamValue());
		}
		if (s6 != null && s6.getParamValue() != null && !s6.getParamValue().equals("")) {
			vo.setRange(Double.valueOf(s6.getParamValue()));
		} else {
			vo.setRange(0d);
		}

		if (s7 != null && s7.getParamValue() != null && !s7.getParamValue().equals("")) {
			vo.setIs_SHARE(Integer.parseInt(s7.getParamValue()));
		} else {
			vo.setRange(0d);
		}

		if (s8 != null && s8.getParamValue() != null && !s8.getParamValue().equals("")) {
			vo.setTelePhone(s8.getParamValue());
		}

		if (s9 != null && s9.getParamValue() != null && !s9.getParamValue().equals("")) {
			vo.setIsDecrypt(s9.getParamValue());
		}

		if (s10 != null && s10.getParamValue() != null && !s10.getParamValue().equals("")) {
			vo.setIsFavourable(Integer.parseInt(s10.getParamValue()));
		} else {
			vo.setIsFavourable(0);
		}
		if (s11 != null && s11.getParamValue() != null && !s11.getParamValue().equals("")) {
			vo.setIsSeaTing(Integer.parseInt(s11.getParamValue()));
		} else {
			vo.setIsSeaTing(0);
		}

		if (s12 != null && s12.getParamValue() != null && !s12.getParamValue().equals("")) {
			vo.setIsCarPhoto(s12.getParamValue());
		} else {
			vo.setIsCarPhoto("0");
		}
		if (s13 != null && s13.getParamValue() != null && !s13.getParamValue().equals("")) {
			vo.setReturnCarStatus(s13.getParamValue());
		}

		// 查找座位数
		List<CarSeries> list = carSeriesService.getSeaTing();
		List<SeaTingVo> svs = new ArrayList<SeaTingVo>();
		if (list != null && list.size() > 0 && list.get(0) != null) {
			for (CarSeries carSeries : list) {
				SeaTingVo sv = new SeaTingVo();
				sv.setSeaTing(carSeries.getSeaTing());
				svs.add(sv);
			}
			vo.setSeaTingList(svs);
			if (list.size() == 1) {
				vo.setIsSeaTing(0);
			}
		} else {
			vo.setSeaTingList(svs);
		}

		// app活动页
		Advert ad = new Advert();
		ad.setIsAvailable(1);
		ad.setIsStartAdvert(1);
		Query q = new Query(ad);
		List<Advert> ads = adertService.getAdvertList(q);
		if (ads != null && ads.size() > 0) {
			vo.setStartPage(imgPath + "/" + ads.get(0).getAdvertPicUrl());
			if (ads.get(0).getExternalLinkUrl() != null && !"".equals(ads.get(0).getExternalLinkUrl())) {
				vo.setExternalLinkUrl(ads.get(0).getExternalLinkUrl());
				vo.setAdvertName(ads.get(0).getAdvertName());
			}
		}

		if (vo != null) {
			result.setCode(CarConstant.success_code);
			result.setData(vo);
			result.setMsg("");
		} else {
			result.setCode(CarConstant.fail_code);
			result.setMsg(CarConstant.fail_msg);
		}
		return result;
	}

	/**
	 * 多边形场站 画图
	 */

	@RequestMapping("/polygonalStationList")
	@ResponseBody
	public ResultInfo<List<polygonalStationVo>> polygonalStationList() {
		ResultInfo<List<polygonalStationVo>> resultInfo = new ResultInfo<List<polygonalStationVo>>();
		List<polygonalStationVo> psv = new ArrayList<polygonalStationVo>();
		Park psk = new Park();
		psk.setIsAvailable(1);
		psk.setIsDeleted(0);
		psk.setIsView(1);
		Query q = new Query(psk);
		List<Park> parks = parkService.getParkList(q);
		if (parks != null && parks.size() > 0) {
			for (Park park : parks) {
				List<parksVo> pvs = new ArrayList<parksVo>();
				if (park.getPloygonPoints() != null && !"".equals(park.getPloygonPoints())) {
					String a[] = park.getPloygonPoints().split(",");
					for (int i = 0; i < a.length; i++) {
						String b = a[i];
						String[] c = b.split(" ");
						parksVo p = new parksVo();
						p.setLongitude(c[0]);
						p.setLatitude(c[1]);
						pvs.add(p);

					}

				}

				polygonalStationVo ps = new polygonalStationVo();
				ps.setPv(pvs);
				psv.add(ps);
			}

		}

		resultInfo.setCode("1");
		resultInfo.setData(psv);
		return resultInfo;

	}

	/**
	 * 区域 画图
	 */

	@RequestMapping("/returnAreaStationList")
	@ResponseBody
	public ResultInfo<List<polygonalStationVo>> returnAreaStationList() {
		ResultInfo<List<polygonalStationVo>> resultInfo = new ResultInfo<List<polygonalStationVo>>();
		List<polygonalStationVo> psv = new ArrayList<polygonalStationVo>();
		ReturnArea returnArea = new ReturnArea();
		returnArea.setIsAvailable(1);
		Query q = new Query(returnArea);
		List<ReturnArea> returnAreas = returnAreaService.getReturnAreaList(q);
		if (returnAreas != null && returnAreas.size() > 0) {
			for (ReturnArea ra : returnAreas) {
				List<parksVo> pvs = new ArrayList<parksVo>();
				if (ra.getPloygonPoints() != null && !"".equals(ra.getPloygonPoints())) {
					String a[] = ra.getPloygonPoints().split(",");
					for (int i = 0; i < a.length; i++) {
						String b = a[i];
						String[] c = b.split(" ");
						parksVo p = new parksVo();
						p.setLongitude(c[0]);
						p.setLatitude(c[1]);
						pvs.add(p);

					}

				}

				polygonalStationVo ps = new polygonalStationVo();
				ps.setPv(pvs);
				psv.add(ps);
			}

		}

		resultInfo.setCode("1");
		resultInfo.setData(psv);
		return resultInfo;

	}

	/**
	 * 得到多城市的列表
	 */
	@RequestMapping("/cityList")
	@ResponseBody
	public ResultInfo<List<CityVo>> cityList() {

		ResultInfo<List<CityVo>> result = new ResultInfo<List<CityVo>>();
		List<CityVo> cv = new ArrayList<CityVo>();

		DataDictItem dd = new DataDictItem();
		dd.setDataDictCatCode("CITY");
		dd.setIsAvailable(1);
		dd.setIsDeleted(0);
		;
		Query q = new Query(dd);
		List<DataDictItem> dataDictItemList = dataDictItemService.getDataDictItemList(q);
		if (dataDictItemList != null && dataDictItemList.size() > 0) {
			for (DataDictItem dataDictItem : dataDictItemList) {
				CityVo vo = new CityVo();
				vo.setCityName(dataDictItem.getItemValue());
				cv.add(vo);
			}
			result.setCode(CarConstant.success_code);
			result.setData(cv);
		} else {
			result.setCode(CarConstant.fail_code);
			result.setMsg("没有开放城市");
		}

		return result;
	}

	/***
	 * 预约时间最大值
	 */
	@RequestMapping("/getPrebookTime")
	@ResponseBody
	public ResultInfo<PrebookVo> getPrebookTime(String parkNo, String carNo) {
		ResultInfo<PrebookVo> result = new ResultInfo<>();
		SysParam sysParam = sysParamService.getByParamKey(CarConstant.prebook_param_key);
		if (sysParam != null) {
			PrebookVo vo = new PrebookVo();
			int minute = Integer.parseInt(sysParam.getParamValue());
			vo.setMinute(minute);
			// 当前时间再加上现有的分钟数
			Calendar now = Calendar.getInstance();
			now.set(Calendar.MINUTE, now.get(Calendar.MINUTE) + minute);
			vo.setPrebookTime(now.getTime());
			result.setData(vo);
			result.setCode(CarConstant.success_code);
			result.setMsg("");
		} else {
			result.setCode(CarConstant.fail_code);
			result.setMsg(CarConstant.fail_msg);
		}
		return result;
	}

	/**
	 * 得到车辆的倒计时时间,以分组算
	 */
	@RequestMapping("/getCountdown")
	@ResponseBody
	public ResultInfo<String> getCountdown(String parkNo, String carNo) {
		ResultInfo<String> result = new ResultInfo<>();
		SysParam sysParam = sysParamService.getByParamKey(CarConstant.countdown_param_key);
		if (sysParam != null) {
			result.setData(sysParam.getParamValue());
			result.setCode(CarConstant.success_code);
			result.setMsg("");
		} else {
			result.setCode(CarConstant.fail_code);
			result.setMsg(CarConstant.fail_msg);
		}
		return result;
	}

	/**
	 * 判断场站是否存在
	 * 
	 * @param memberNo
	 * @param parkNo
	 * @return
	 */
	@RequestMapping("judgeParkIsExist")
	@ResponseBody
	public ResultInfo<GoAddCarVo> judgeParkIsExist(String memberNo, String parkNo) {
		ResultInfo<GoAddCarVo> result = new ResultInfo<GoAddCarVo>();
		// 判断场站 是否隐藏 是否启用 是否删除 是否开放
		Park IsPark = new Park();
		IsPark.setIsAvailable(1);
		IsPark.setIsDeleted(0);
		IsPark.setIsPublic(1);
		IsPark.setIsView(1);
		IsPark.setParkNo(parkNo);
		Query q = new Query(IsPark);
		List<Park> parks = parkService.getParkList(q);
		if (parks != null && parks.size() > 0) {
			result.setCode(CarConstant.success_code);
		} else {
			result.setCode(CarConstant.fail_code_close);
			result.setMsg(CarConstant.available_park_msg_clsoe);
		}
		return result;
	}

	/**
	 * 前往下订单页面(进入下订单页面，得到车辆、促销信息，资费说明) 请求参数：memberNO：会员编号； parkNo：场站ID；
	 * 
	 * @throws ParseException
	 */
	@RequestMapping("/goAddOrder")
	@ResponseBody
	public ResultInfo<GoAddCarVo> goAddOrder(String memberNo, String parkNo, String seaTing, String longitude,
			String latitude) throws ParseException {
		ResultInfo<GoAddCarVo> result = new ResultInfo<GoAddCarVo>();
	
		int orderCaculateType = 1;
		SysParam sysparam1 = sysParamService.getByParamKey("ORDER_CACULATE_TYPE");
		if (sysparam1 != null && sysparam1.getParamValue() != null) {
			orderCaculateType = Integer.parseInt(sysparam1.getParamValue());
		}
		// 获取系统参数，电量限制：POWERLIMIT
		SysParam powerLimit = sysParamService.getByParamKey(CarConstant.power_limit);
		Double power = 20d;
		if (powerLimit != null && powerLimit.getParamValue() != null) {
			power = Double.parseDouble(powerLimit.getParamValue());
		}
		// 根据场站ID查找该场站电量最高，并判断是个人用户还是集团用户；
		// 先判断是否为空gao
		List<CarAndStatus> carList = null;
		PricingRule pricingRule = null;
		carList = carService.getCarByParkNo(parkNo, power, seaTing);
		if (carList != null && carList.size() > 0) {
			// 集团会员
			Member memberss = memberService.getMember(memberNo);
			if (memberss != null && memberss.getMemberType() != null && memberss.getMemberType() == 2
					&& memberss.getCompanyId() != null && !memberss.getCompanyId().equals("")) {
				carList = carService.getCarByMemberAndParkNo(memberss.getCompanyId(), parkNo, power, seaTing);
				for (CarAndStatus carAndStatus : carList) {
					pricingRule = pricingRuleService.getPricingRuleUseByCompanyId(carAndStatus.getCarBrandName(),
							carAndStatus.getCarModelName(), memberss.getCompanyId());
					if (pricingRule != null) {
						// 确定orderMileage.getOrderMileageDate()是周几
						int dayOfWeek = ECDateUtils.getDayOfWeek(new Date());
						// 查询自定义日期
						PricingRuleCustomizedDate pricingRuleCustomizedDate = pricingRuleCustomizedDateService
								.getPricingRuleCustomizedDate(pricingRule.getRuleNo(), new Date());
						if (pricingRuleCustomizedDate != null) {// 自定义日期时间和里程
							carAndStatus.setOfMinute(pricingRuleCustomizedDate.getPriceOfMinuteCustomized() == null ? 0
									: pricingRuleCustomizedDate.getPriceOfMinuteCustomized());
							carAndStatus.setOfKm(pricingRuleCustomizedDate.getPriceOfKmCustomized() == null ? 0
									: pricingRuleCustomizedDate.getPriceOfKmCustomized());
							if (orderCaculateType == 1) {
								carAndStatus.setBillingCapPerDay(
										pricingRuleCustomizedDate.getBillingCapPerDayCustomized() == null ? 0
												: pricingRuleCustomizedDate.getBillingCapPerDayCustomized());
							}
						} else {
							if (orderCaculateType == 2) {

								carAndStatus.setOfMinute(
										pricingRule.getPriceOfMinute() == null ? 0 : pricingRule.getPriceOfMinute());
								carAndStatus
										.setOfKm(pricingRule.getPriceOfKm() == null ? 0 : pricingRule.getPriceOfKm());
								carAndStatus.setBillingCapPerDay(pricingRule.getBillingCapPerDay() == null ? 0
										: pricingRule.getBillingCapPerDay());
							} else if (orderCaculateType == 1) {
								if (dayOfWeek == Calendar.SATURDAY || dayOfWeek == Calendar.SUNDAY) {// 周末时间和里程（周六 周日）
									carAndStatus.setOfMinute(pricingRule.getPriceOfMinuteOrdinary() == null ? 0
											: pricingRule.getPriceOfMinuteOrdinary());
									carAndStatus.setOfKm(pricingRule.getPriceOfKmOrdinary() == null ? 0
											: pricingRule.getPriceOfKmOrdinary());
									carAndStatus
											.setBillingCapPerDay(pricingRule.getBillingCapPerDayOrdinary() == null ? 0
													: pricingRule.getBillingCapPerDayOrdinary());
								} else {// 工作日时间和里程(周一到周五)
									carAndStatus.setOfMinute(pricingRule.getPriceOfMinute() == null ? 0
											: pricingRule.getPriceOfMinute());
									carAndStatus.setOfKm(
											pricingRule.getPriceOfKm() == null ? 0 : pricingRule.getPriceOfKm());
									carAndStatus.setBillingCapPerDay(pricingRule.getBillingCapPerDay() == null ? 0
											: pricingRule.getBillingCapPerDay());

								}
							}
						}
						// 显示高峰时段的计费股则
						PeakHours ph = new PeakHours();
						String date = ECDateUtils.getCurrentTime();
						// 获取当前的时间（小时）
						// GregorianCalendar calendar = new GregorianCalendar();
						// int hour = calendar.get(Calendar.HOUR_OF_DAY);
						// ph.setHours(hour);
						ph.setRuleNo(pricingRule.getRuleNo());
						Query q = new Query(ph);
						List<PeakHours> peakHours = peakHoursService.getPeakHoursList(q);
						if (peakHours != null) {
							for (PeakHours phs : peakHours) {
								SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
								String peakStartHours = phs.getPeakStartHours() + ":00:00";
								String peakEndHours = phs.getPeakEndHours() + ":00:00";
								Date datePeakStartHours = formatter.parse(peakStartHours);// 开始时间
								Date datePeakEndHours = formatter.parse(peakEndHours);// 结束时间
								Date ddd = formatter.parse(date);// 当前时间
								if (datePeakStartHours.getTime() <= ddd.getTime()
										&& datePeakEndHours.getTime() >= ddd.getTime()) {
									carAndStatus
											.setOfMinute(phs.getPriceOfMinute() == null ? 0 : phs.getPriceOfMinute());
									carAndStatus.setOfKm(phs.getPriceOfKm() == null ? 0 : phs.getPriceOfKm());
								}

							}

						}

					}
				}
			}
			if (pricingRule == null) {
				for (CarAndStatus carAndStatus : carList) {

					// 普通会员和未登录的
					pricingRule = pricingRuleService.getPricingRuleUseByCar(carAndStatus.getCarBrandName(),
							carAndStatus.getCarModelName());
					if (pricingRule != null) {
						// 确定orderMileage.getOrderMileageDate()是周几
						int dayOfWeek = ECDateUtils.getDayOfWeek(new Date());
						// 查询自定义日期
						PricingRuleCustomizedDate pricingRuleCustomizedDate = pricingRuleCustomizedDateService
								.getPricingRuleCustomizedDate(pricingRule.getRuleNo(), new Date());
						if (pricingRuleCustomizedDate != null) {// 自定义日期时间和里程
							carAndStatus.setOfMinute(pricingRuleCustomizedDate.getPriceOfMinuteCustomized() == null ? 0
									: pricingRuleCustomizedDate.getPriceOfMinuteCustomized());
							carAndStatus.setOfKm(pricingRuleCustomizedDate.getPriceOfKmCustomized() == null ? 0
									: pricingRuleCustomizedDate.getPriceOfKmCustomized());

							carAndStatus.setBillingCapPerDay(
									pricingRuleCustomizedDate.getBillingCapPerDayCustomized() == null ? 0
											: pricingRuleCustomizedDate.getBillingCapPerDayCustomized());
						} else {
							if (orderCaculateType == 1) {
								if (dayOfWeek == Calendar.SATURDAY || dayOfWeek == Calendar.SUNDAY) {// 周末时间和里程（周六 周日）
									carAndStatus.setOfMinute(pricingRule.getPriceOfMinuteOrdinary() == null ? 0
											: pricingRule.getPriceOfMinuteOrdinary());
									carAndStatus.setOfKm(pricingRule.getPriceOfKmOrdinary() == null ? 0
											: pricingRule.getPriceOfKmOrdinary());
									carAndStatus
											.setBillingCapPerDay(pricingRule.getBillingCapPerDayOrdinary() == null ? 0
													: pricingRule.getBillingCapPerDayOrdinary());
								} else {// 工作日时间和里程(周一到周五)
									carAndStatus.setOfMinute(pricingRule.getPriceOfMinute() == null ? 0
											: pricingRule.getPriceOfMinute());
									carAndStatus.setOfKm(
											pricingRule.getPriceOfKm() == null ? 0 : pricingRule.getPriceOfKm());
									carAndStatus.setBillingCapPerDay(pricingRule.getBillingCapPerDay() == null ? 0
											: pricingRule.getBillingCapPerDay());

								}
							} else if (orderCaculateType == 2) {
								carAndStatus.setOfMinute(
										pricingRule.getPriceOfMinute() == null ? 0 : pricingRule.getPriceOfMinute());
								carAndStatus
										.setOfKm(pricingRule.getPriceOfKm() == null ? 0 : pricingRule.getPriceOfKm());
								carAndStatus.setBillingCapPerDay(pricingRule.getBillingCapPerDay() == null ? 0
										: pricingRule.getBillingCapPerDay());
							}
						}

						// 显示高峰时段的计费股则
						PeakHours ph = new PeakHours();
						String date = ECDateUtils.getCurrentTime();
						// 获取当前的时间（小时）
						// GregorianCalendar calendar = new GregorianCalendar();
						// int hour = calendar.get(Calendar.HOUR_OF_DAY);
						// ph.setHours(hour);
						ph.setRuleNo(pricingRule.getRuleNo());
						Query q = new Query(ph);
						List<PeakHours> peakHours = peakHoursService.getPeakHoursList(q);
						if (peakHours != null) {
							for (PeakHours phs : peakHours) {
								SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
								String peakStartHours = phs.getPeakStartHours() + ":00:00";
								String peakEndHours = phs.getPeakEndHours() + ":00:00";
								Date datePeakStartHours = formatter.parse(peakStartHours);// 开始时间
								Date datePeakEndHours = formatter.parse(peakEndHours);// 结束时间
								Date ddd = formatter.parse(date);// 当前时间
								if (datePeakStartHours.getTime() <= ddd.getTime()
										&& datePeakEndHours.getTime() >= ddd.getTime()) {
									carAndStatus
											.setOfMinute(phs.getPriceOfMinute() == null ? 0 : phs.getPriceOfMinute());
									carAndStatus.setOfKm(phs.getPriceOfKm() == null ? 0 : phs.getPriceOfKm());
								}

							}

						}

					}
				}
			}

		}

		if (carList != null) {

			// 为 预约的最大分钟数：minute；预约时间：prebookTime 赋值
			SysParam sysParam = sysParamService.getByParamKey(CarConstant.prebook_param_key);
			int minute = 0;
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String prebookTime = null;

			if (sysParam != null) {
				minute = Integer.parseInt(sysParam.getParamValue());
				// 当前时间再加上现有的分钟数
				Calendar now = Calendar.getInstance();
				now.setTime(new Date());
				now.add(Calendar.MINUTE, +minute);
				Date date = now.getTime(); // 结果
				prebookTime = sdf.format(date);
			}
			List<CarAndStatus> cList = new ArrayList<>();
			for (CarAndStatus cars : carList) {
				cars.setMinute(minute);
				cars.setCarPhotoUrl1(imgPath + "/" + cars.getCarPhotoUrl1());
				cars.setPrebookTime(prebookTime);
				if (cars.getSeaTing() == null || "".equals(cars.getSeaTing())) {
					cars.setSeaTing("0");
				}
				if (cars.getSeaTing() == null || "".equals(cars.getSeaTing())) {
					cars.setSeaTing("0");
				}
				if (cars.getRangeMileage() == null) {
					cars.setRangeMileage(0.0);
				}
				CarStatus cas = carStatusService.getCarStatus(cars.getCarNo());
				if (cas != null) {
					cars.setVehicleType(cas.getVehicleType());
				}
				CarRedPacket carRedPacket = carRedPacketService.getCarRedPacketByCarPlateNo(cars.getCarPlateNo(), "1");
				if (carRedPacket != null) {
					cars.setIsCarRedPakcet("1");
					CarRedPacketVo carRedPacketVo = new CarRedPacketVo();
					carRedPacketVo.setIsCharge(carRedPacket.getIsCharge());
					carRedPacketVo.setIsOrderAmountLimit(carRedPacket.getIsOrderAmountLimit());
					if (carRedPacket.getIsOrderAmountLimit() == 1) {
						carRedPacketVo.setOrderAmountLimit(carRedPacket.getOrderAmountLimit());
					} else {
						carRedPacketVo.setOrderAmountLimit(0.0);
					}
					cars.setCarRedPacketVo(carRedPacketVo);
				}
				
				cList.add(cars);
			}

			GoAddCarVo goAddCarVO = new GoAddCarVo();
			// 得到最新的广告（促销信息）
			Advert advert = adertService.getLatestAdverts();
			
			CarAndStatus carAndStatus = new CarAndStatus();
			
			
			if (cList.size()>0) {	
				   Collections.sort(cList, new Comparator<CarAndStatus>() {
					@Override
					public int compare(CarAndStatus o1, CarAndStatus o2) {
						// TODO Auto-generated method stub
												
						if (o1.getRangeMileage() > o2.getRangeMileage()) {
							return 1;
						}
						if (o1.getRangeMileage() == o2.getRangeMileage()) {
							return 0;
						}
						return -1;
					}					   
				   });
			}

			goAddCarVO.setCarAndStatusList(cList);
			goAddCarVO.setParkNo(parkNo);
			Park park = parkService.getPark(parkNo);
			if (park != null) {
				goAddCarVO.setAddress(park.getAddrStreet());
				goAddCarVO.setParkName(park.getParkName());
				if (park.getAddrStreet() != null && !"".equals(park.getAddrStreet())
						&& park.getAddrStreet().indexOf("省") == -1) {
					if (park.getAddrRegion3Name() != null && !"".equals(park.getAddrRegion3Name())) {
						goAddCarVO.setDetailAddress(park.getAddrRegion1Name() + park.getAddrRegion2Name()
								+ park.getAddrRegion3Name() + park.getAddrStreet());
					} else {
						goAddCarVO.setDetailAddress(
								park.getAddrRegion1Name() + park.getAddrRegion2Name() + park.getAddrStreet());
					}
				} else {
					goAddCarVO.setDetailAddress(park.getAddrStreet());
				}

				if (memberNo != null && !memberNo.equals("")) {
					Member member = memberService.getMember(memberNo);
					if (member != null) {
						if (member.getMemberType().equals(1)) {// 普通会员

							// 获取可用车辆数
							Integer availableCarNum = carDao.getCarCountByParkMemberAndSeaTing(park.getParkNo(), power,
									seaTing);
							park.setAvailableCarNum(availableCarNum);

						} else if (member.getMemberType().equals(2)) {// 集团会员
							String companyId = member.getCompanyId();

							// 获取可用车辆数
							Integer availableCarNum = carDao.getCarCountByParkMemberAndSeaTing(park.getParkNo(), power,
									seaTing);
							Integer availableCarNum1 = carDao.getCarCountByParkMember1AndSeaTing(park.getParkNo(),
									companyId, power, seaTing);
							park.setAvailableCarNum(availableCarNum + availableCarNum1);

						}
					} else {

						// 获取可用车辆数
						Integer availableCarNum = carDao.getCarCountByParkMemberAndSeaTing(park.getParkNo(), power,
								seaTing);
						park.setAvailableCarNum(availableCarNum);
					}
				} else {
					// 获取可用车辆数
					Integer availableCarNum = carDao.getCarCountByParkMember(park.getParkNo(), power);
					park.setAvailableCarNum(availableCarNum);
				}
				if (park.getAvailableCarNum() != null) {
					goAddCarVO.setCarNumber(park.getAvailableCarNum());
				} else {
					goAddCarVO.setCarNumber(0);
				}

				if (park.getChargerNumber() != null) {
					goAddCarVO.setChargingPostNum(park.getChargerNumber());
				} else {
					goAddCarVO.setChargingPostNum(0);
				}
				if (park.getServiceFeeGet() != null) {
					goAddCarVO.setGetCarAmount(park.getServiceFeeGet());
				} else {
					goAddCarVO.setGetCarAmount(0.00);
				}

				if (!"".equals(park.getLongitude())) {
					goAddCarVO.setLongitude(park.getLongitude());
				} else {
					goAddCarVO.setLongitude("0");
				}
				if (!"".equals(park.getLatitude())) {
					goAddCarVO.setLatitude(park.getLatitude());
				} else {
					goAddCarVO.setLatitude("0");
				}
				// 营业时间
				if (!"".equals(park.getBusinessHours())) {
					goAddCarVO.setBusinessHours(park.getBusinessHours());
				} else {
					goAddCarVO.setBusinessHours("0");
				}
				// 可用车位数
				if (park.getParkingSpaceNumber() != null) {
					goAddCarVO.setParingSpaceNum(park.getParkingSpaceNumber());
				} else {
					goAddCarVO.setParingSpaceNum(0);
				}
				// 还车费用
				if (park.getServiceFeeBack() != null) {
					goAddCarVO.setReturnCarAmount(park.getServiceFeeBack());
				} else {
					goAddCarVO.setReturnCarAmount(0.00);
				}
				if (park.getParkPicUrl1() != null && !"".equals(park.getParkPicUrl1())) {
					String[] arr = park.getParkPicUrl1().split(",");
					if (arr != null && arr.length == 1) {
						goAddCarVO.setParkPhotoUrl1(imgPath + "/" + arr[0]);
					} else if (arr != null && arr.length == 2) {
						goAddCarVO.setParkPhotoUrl1(imgPath + "/" + arr[0]);
						goAddCarVO.setParkPhotoUrl2(imgPath + "/" + arr[1]);
					} else if (arr != null && arr.length == 3) {
						goAddCarVO.setParkPhotoUrl1(imgPath + "/" + arr[0]);
						goAddCarVO.setParkPhotoUrl2(imgPath + "/" + arr[1]);
						goAddCarVO.setParkPhotoUrl3(imgPath + "/" + arr[2]);
					}

				}
				String locationParkNos = carRedPacketService.getCarRedParketLocationParkNos(1);
				if (locationParkNos != null && !"".equals(locationParkNos)) {
					String[] parkNos = locationParkNos.split(",");
					for (int i = 0; i < parkNos.length; i++) {
						if (parkNos[i].equals(parkNo)) {
							goAddCarVO.setIsRedPakcetPark("1");
						}
					}
				}
			}

			if (advert != null) {
				goAddCarVO.setAdvert(advert.getText1());
			}
			// 这里是资费说明
			goAddCarVO.setExpenses(sysParamService.getByParamKey(CarConstant.expenses_param_key).getParamValue());

			// 计算会员到站点的距离------拿到站点下 充电桩 快慢的个数
			Park park2 = parkService.getPark(parkNo);
            if (park2!=null) {
            	Double memberDistance = parkService.getDistanceByPoint(longitude, latitude, park2.getLongitude(),
    					park2.getLatitude());
    			Charger charger = new Charger();
    			charger.setParkNo(parkNo);
    			List<Charger> ls = chargerService.getChargerList(new Query(charger));
    			int fast = 0;
    			int slow = 0;
    			if (ls.size() > 0) {
    				for (Charger item : ls) {
    					if (item.getChargerType() == 1) {
    						slow = slow + 1;
    					} else {
    						fast = fast + 1;
    					}
    				}
    				goAddCarVO.setFast(fast);
    				goAddCarVO.setSlow(slow);
    			} else if (ls.size() == 0) {
    				goAddCarVO.setFast(fast);
    				goAddCarVO.setSlow(slow);
    			}
    			goAddCarVO.setMemberDistance(memberDistance);
			}
			
			result.setCode(CarConstant.success_code);
			result.setMsg("");
			result.setData(goAddCarVO);
		} else {
			result.setCode(CarConstant.fail_code);
			result.setMsg(CarConstant.available_car_msg);
		}

		return result;
	}

	/**
	 * 租车，根据所选场站，显示该场站可用车辆的列表信息
	 */
	@RequestMapping("/rentCarList")
	@ResponseBody
	public ResultInfo<List<CarVo>> rentCarList(String parkId, String memberNo) {
		ResultInfo<List<CarVo>> result = new ResultInfo<List<CarVo>>();
		List<Car> list = carService.getCarListByParkId(parkId, memberNo);
		return carToVo(result, list);
	}

	/**
	 * 租车，修改车辆的使用信息,并生成订单
	 */
	@RequestMapping("/rentCar")
	@ResponseBody
	public ResultInfo<CarStatusVo> rentCar(String memberNo, String carNo, Order order, String parkNo, String longitude,
			String latitude) {
		ResultInfo<CarStatusVo> resultInfo = new ResultInfo<CarStatusVo>();

		CarIllegal c = new CarIllegal();
		c.setDriverId(memberNo);
		List<CarIllegal> list = carIllegalService.getCarIllegalList(new Query(c));
		if (list != null && list.size() > 0) {
			for (CarIllegal carIllegal : list) {
				if (carIllegal.getProcessingStatus() == 0 || carIllegal.getProcessingStatus() == 1) {
					resultInfo.setCode("0");
					resultInfo.setMsg("您有未处理或者处理中的违章，不能预约车");
					return resultInfo;
				}
			}
		}

		String telePhone = "";
		SysParam sp = sysParamService.getByParamKey("telePhone");
		if (sp == null) {
			telePhone = "110";
		} else {
			telePhone = sp.getParamValue();
		}
		ResultInfo<MemberStatusVO> resMember = getStatus(memberNo);
		if (resMember.getData().getCensorStatus() == 1 && resMember.getData().getDepositStatus() == 1) {

			// 预留（租车之前要对该用户的历史订单进行查看，看是否有未付款订单）
			Order orderPD = orderService.getNowadayOrderByMemberNo(memberNo);
			if (orderPD != null) {
				if (orderPD.getOrderStatus().equals(1)) {
					resultInfo.setCode(OrderConstant.yuyueOrder_code);
					resultInfo.setMsg(OrderConstant.yuyueOrder_msg);
					return resultInfo;
				} else if (orderPD.getOrderStatus().equals(2)) {
					resultInfo.setCode(OrderConstant.jifeiOrder_code);
					resultInfo.setMsg(OrderConstant.jifeiOrder_msg);
					return resultInfo;
				} else if (orderPD.getOrderStatus().equals(3)) {
					if (orderPD.getPayStatus().equals(0)) {// 未支付
						resultInfo.setCode(OrderConstant.notPayOrder_code);
						resultInfo.setMsg(OrderConstant.notPayOrder_msg);
						return resultInfo;
					} else {
						// 获取当前登录的用户
						Member member = memberService.getMember(memberNo);
						if (member.getIsBlacklist().equals(1)) {// 会员在黑名单中
							resultInfo.setCode(Constant.FAIL);
							resultInfo.setMsg(MemberConstant.member_isBlackList);
							return resultInfo;
						} else {
							ResultInfo<CarStatus> resultInfo1 = new ResultInfo<CarStatus>();
							resultInfo1 = carStatusService.rentCar(order, member, parkNo, getOperator());
							ResultInfo<CarStatusVo> result = new ResultInfo<CarStatusVo>();
							result = carStatusToVoOne(result, resultInfo1.getData(), longitude, latitude, order);
							return resultSetDate(result, order);
						}
					}
				}
			} else {
				// 获取当前登录的用户
				Member member = memberService.getMember(memberNo);
				if (member.getIsBlacklist().equals(1)) {// 会员在黑名单中
					resultInfo.setCode(Constant.FAIL);
					resultInfo.setMsg(MemberConstant.member_isBlackList);
					return resultInfo;
				} else {
					ResultInfo<CarStatus> resultInfo1 = new ResultInfo<CarStatus>();
					resultInfo1 = carStatusService.rentCar(order, member, parkNo, getOperator());
					ResultInfo<CarStatusVo> result = new ResultInfo<CarStatusVo>();
					result = carStatusToVoOne(result, resultInfo1.getData(), longitude, latitude, order);
					return resultSetDate(result, order);
				}
			}
		} else if (resMember.getData().getCensorStatus() == 0) {
			resultInfo.setCode("6");
			resultInfo.setMsg("抱歉，您的账号未认证，详情咨询客服热线" + telePhone);
			return resultInfo;
		} else if (resMember.getData().getCensorStatus() == 2) {
			resultInfo.setCode("6");
			resultInfo.setMsg("抱歉，您的账号还在待审核，详情咨询客服热线" + telePhone);
			return resultInfo;
		} else if (resMember.getData().getCensorStatus() == 3) {
			resultInfo.setCode("6");
			resultInfo.setMsg("抱歉，您的账号审核未通过，详情咨询客服热线" + telePhone);
			return resultInfo;
		} else if (resMember.getData().getDepositStatus() == 0) {
			// 当押金记录为空时，判断当前系统参数变量中押金的值
			SysParam sysp = sysParamService.getByParamKey(DepositConstant.deposit_amount_key);
			String money = sysp.getParamValue();
			if (null == money || "".equals(money) || "0".equals(money)) {
				// 获取当前登录的用户
				Member member = memberService.getMember(memberNo);
				if (member.getIsBlacklist().equals(1)) {// 会员在黑名单中
					resultInfo.setCode(Constant.FAIL);
					resultInfo.setMsg(MemberConstant.member_isBlackList);
					return resultInfo;
				} else {
					ResultInfo<CarStatus> resultInfo1 = new ResultInfo<CarStatus>();
					resultInfo1 = carStatusService.rentCar(order, member, parkNo, getOperator());
					ResultInfo<CarStatusVo> result = new ResultInfo<CarStatusVo>();
					result = carStatusToVoOne(result, resultInfo1.getData(), longitude, latitude, order);
					return resultSetDate(result, order);
				}
			} else {
				resultInfo.setCode("6");
				resultInfo.setMsg("抱歉，您的账号未支付用车押金，请缴纳押金再用车，详情咨询客服热线" + telePhone);
				return resultInfo;
			}
		} else {
			resultInfo.setCode("6");
			resultInfo.setMsg("抱歉，您的账号有未知情况，详情咨询客服热线" + telePhone);
			return resultInfo;
		}

		return null;
	}

	/** 判断并添加orderNO字段 */
	private ResultInfo<CarStatusVo> resultSetDate(ResultInfo<CarStatusVo> result, Order order) {
		CarStatusVo date = result.getData();
		if (order != null && order.getOrderNo() != null) {
			date.setOrderNo(order.getOrderNo());
			result.setData(date);
		} else {
			result.setCode(OrderConstant.no_code);
			result.setMsg(OrderConstant.no_msg);
		}
		return result;
	}

	/**
	 * 取车后，修改订单信息和车辆信息
	 */
	@RequestMapping("/takeCar")
	@ResponseBody
	public ResultInfo<CarStatusVo> takeCar(String deviceId) {
		ResultInfo<CarStatus> resultInfo = new ResultInfo<CarStatus>();
		resultInfo = carStatusService.takeCar(deviceId, getOperator());
		ResultInfo<CarStatusVo> result = new ResultInfo<CarStatusVo>();
		return carStatusToVoOne(result, resultInfo.getData(), null, null, null);
	}

	// 还车前的检查
	@RequestMapping("checkBeforeReturnCar")
	@ResponseBody
	public ResultInfo<String> checkBeforeReturnCar(String orderNo) throws ParseException {
		ResultInfo<String> result = new ResultInfo<String>();
		Order order = orderService.getOrder(orderNo);
		CarStatus carStatus = carStatusService.getCarStatus(order.getCarNo());
		String parkNo = carStatus.getLocationParkNo() == null ? "" : carStatus.getLocationParkNo();
		SysParam sysParam = sysParamService.getByParamKey("RETURN_CAR_OUTSIDE_SWITCH");// 1.只能在场内还车；2.场站内外都允许还车；3.没有场站只有区域还车
		Park parkIsa = parkService.getPark(parkNo);
		if (parkIsa != null) {
			if (parkIsa.getIsAvailable() == 0) {
				result.setCode(CarConstant.park_NO);
				result.setMsg("场站已经停用，请到别的网点还车！");
				return result;
			}
		}
		if ("1".equals(sysParam.getParamValue()) && "".equals(parkNo)) { // 只能在场内还车
			result.setCode("0");
			result.setMsg("车辆未在场站内");
			return result;
		} else if ("1".equals(sysParam.getParamValue()) && !"".equals(parkNo)) {
			result.setCode("1");
			return result;
		} else if ("2".equals(sysParam.getParamValue())) { // 场站内外都允许还车,只需要判断在不在区域就可以进行还车业务，需要加场站判断添加还车附加服务器费，此功能这版本不做
			// 先判断是否在场站，再判断是否在还车区域
			if ("".equals(parkNo)) {
				// 此处逻辑还需要修改，先这么写，是否要系统参数？
				boolean res = returnAreaIndexService.searchReturnAreaByPosition(carStatus.getLongitude(),
						carStatus.getLatitude());
				if (!res) {
					result.setCode("0");
					result.setMsg("您尚未进入到合法还车区域，请到指定区域还车");
					return result;
				} else {
					result.setCode("1");
					if ("".equals(parkNo)) {
						// 此时需要计算距离 计算还车附加服务费

					}
					return result;
				}
			} else {
				result.setCode("1");
				return result;
			}

		} else if ("3".equals(sysParam.getParamValue())) { // 没有场站只有区域还车
			boolean res = returnAreaIndexService.searchReturnAreaByPosition(carStatus.getLongitude(),
					carStatus.getLatitude());
			if (!res) {
				result.setCode("0");
				result.setMsg("您尚未进入到合法还车区域，请到指定区域还车");
				return result;
			} else {
				result.setCode("1");
				return result;
			}
		} else {
			result.setCode("0");
			result.setMsg("车辆未在场站内");
			return result;
		}
	}

	/**
	 * 还车后，修改订单信息和车辆信息,结束订单 longitude：经度； latitude:纬度；
	 * 
	 * @throws ParseException
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/returnCar")
	@ResponseBody
	public ResultInfo<OrderVO> returnCar(String orderNo, String memberNo, Integer carPosition) throws ParseException {
		ResultInfo<Order> resultInfo = new ResultInfo<Order>();
		// 预留（还车时，要判断汽车状态是否符合还车要求）
		ResultInfo<OrderVO> result = new ResultInfo<OrderVO>();
		Order order = orderService.getOrder(orderNo);
		// 判断订单状态不为2，不能结算订单，防止重复提交结算
		if (order != null && order.getOrderStatus() != 2) {
			result.setCode(Constant.SECCUESS);// 返回1，客户端提示还车成功
			result.setMsg("判断订单状态不为2，不能结算订单，防止重复提交结算");
			return result;
		}
		CarStatus carStatus = carStatusService.getCarStatus(order.getCarNo());
		// 车辆状态（1、已启动，2、已熄火）
		if (carStatus.getCarStatus() != null && carStatus.getCarStatus() == 1) {
			result.setCode(CarConstant.car_NOFlameout);
			result.setMsg("车辆未熄火");
			return result;
		} else {
			// 根据传的车辆位置，查找周边场站距离最近的场站
			String parkNo = "";
			// 直接用t_car_status中的location_park_no判断即可，不需要在此调用solr，20170511注
			// parkNo=parkService.getCurrentParkNoByCarLocation(carStatus.getLongitude(),carStatus.getLatitude());
			// //solr查询不到场站的，临时处理
			// if(carStatus.getLocationParkNo()!=null){
			parkNo = carStatus.getLocationParkNo() == null ? "" : carStatus.getLocationParkNo();

			// }

			SysParam sysParam = sysParamService.getByParamKey("RETURN_CAR_OUTSIDE_SWITCH");// 1.只能在场内还车；2.场站内外都允许还车；3.没有场站只有区域还车
			if ("1".equals(sysParam.getParamValue()) && "".equals(parkNo)) {
				result.setCode(CarConstant.car_NOInPark);
				result.setMsg("车辆未在场站内");
				return result;
			} else if ("1".equals(sysParam.getParamValue()) && !"".equals(parkNo)) {// 校验通过，可以还车
				// 修改停车位置
				if (order != null) {
					Order o = new Order();
					o.setCarPosition(carPosition);
					o.setOrderNo(orderNo);
					orderService.updateOrder(o, getOperator());
				}
				// 租车之前先判断该车辆是否是新设备，有则发送还车的指令,并且等待成功响应
				Device device = deviceService.getDeviceByCarNo(order.getCarNo());
				if (device != null) {// 为1是新设备，可以使用用车还车指令，且等待响应完成后继续执行后续业务
					ResultInfo<String> userCarResult = new ResultInfo<String>();
					String res = "";
					// -4、-5、-6、206调蓝牙的返回值code
					try {
						// 异常情况不允许还车
						res = carStatusService.returnCarSendCmd(device.getDeviceSn(), order.getMemberNo(), null, "1");
					} catch (Exception e) {
						result.setCode("-6");
						result.setMsg("网络请求异常");
						return result;
					}
					if (res != null && !"".equals(res)) {
						Gson gson = new Gson();
						userCarResult = gson.fromJson(res, ResultInfo.class);
						if (userCarResult.getCode().equals(Constant.SECCUESS)) {
							resultInfo = carStatusService.returnCar(orderNo, parkNo, getOperator());
							result = orderToVoOne(result, resultInfo.getData());
						} else if (userCarResult.getCode().equals("-4")) { // 发送指令失败，也不允许还车
							result.setCode("-4");
							result.setMsg(userCarResult.getMsg());
						} else if (userCarResult.getCode().equals("205")) { // 设备执行指令失败，也不允许还车
							result.setCode("205");
							result.setMsg(userCarResult.getMsg());
						} else if (userCarResult.getCode().equals("206")) { // 设备执行指令超时，也不允许还车
							result.setCode("206");
							result.setMsg(userCarResult.getMsg());
						} else {
							result.setCode(Constant.FAIL);
							result.setMsg(userCarResult.getMsg());
						}
					}
					return result;
				} else {// 旧设备按原业务处理
					resultInfo = carStatusService.returnCar(orderNo, parkNo, getOperator());
					result = orderToVoOne(result, resultInfo.getData());
					return result;
				}
			} else if ("2".equals(sysParam.getParamValue()) || "3".equals(sysParam.getParamValue())) {
				// 修改停车位置
				if (order != null) {
					Order o = new Order();
					o.setCarPosition(carPosition);
					o.setOrderNo(orderNo);
					orderService.updateOrder(o, getOperator());
				}
				// 租车之前先判断该车辆是否是新设备，有则发送还车的指令,并且等待成功响应
				Device device = deviceService.getDeviceByCarNo(order.getCarNo());
				if (device != null) {// 为1是新设备，可以使用用车还车指令，且等待响应完成后继续执行后续业务
					ResultInfo<String> userCarResult = new ResultInfo<String>();
					String res = "";
					// -4、-5、-6、206调蓝牙的返回值code
					try {
						// 异常情况不允许还车
						res = carStatusService.returnCarSendCmd(device.getDeviceSn(), order.getMemberNo(), null, "1");
					} catch (Exception e) {
						result.setCode("-6");
						result.setMsg("网络请求异常");
						return result;
					}
					if (res != null && !"".equals(res)) {
						Gson gson = new Gson();
						userCarResult = gson.fromJson(res, ResultInfo.class);
						if (userCarResult.getCode().equals(Constant.SECCUESS)) {
							resultInfo = carStatusService.returnCar(orderNo, parkNo, getOperator());
							result = orderToVoOne(result, resultInfo.getData());
						} else if (userCarResult.getCode().equals("-4")) { // 发送指令失败，也不允许还车
							result.setCode("-4");
							result.setMsg(userCarResult.getMsg());
						} else if (userCarResult.getCode().equals("205")) { // 设备执行指令失败，也不允许还车
							result.setCode("205");
							result.setMsg(userCarResult.getMsg());
						} else if (userCarResult.getCode().equals("206")) { // 设备执行指令超时，也不允许还车
							result.setCode("206");
							result.setMsg(userCarResult.getMsg());
						} else {
							result.setCode(Constant.FAIL);
							result.setMsg(userCarResult.getMsg());
						}
					}
					return result;
				} else {// 旧设备按原业务处理
					resultInfo = carStatusService.returnCar(orderNo, parkNo, getOperator());
					result = orderToVoOne(result, resultInfo.getData());
					return result;
				}
			} else {
				result.setCode(CarConstant.fail_code);
				result.setMsg("系统参数设置错误");
				return result;
			}
		}
	}

	/**
	 * 方法说明:将按特定条件查询的记录转换成自定vo对象
	 */
	ResultInfo<List<CarVo>> carToVo(ResultInfo<List<CarVo>> result, List<Car> carList) {

		if (carList != null && carList.size() > 0) {
			List<CarVo> voList = new ArrayList<CarVo>();
			for (Car car : carList) {
				CarVo or = new CarVo();
				or.setCarModelId(car.getCarModelId());
				or.setCarModelName(car.getCarModelName());
				or.setCarNo(car.getCarNo());
				or.setCarPhotoUrl1(imgPath + "/" + car.getCarPhotoUrl1());
				or.setCarPlateNo(car.getCarPlateNo());

				CarStatus status = carStatusService.getCarStatus(car.getCarNo());
				if (status != null && status.getRangeMileage() != null) {
					or.setRangeMileage(status.getRangeMileage());
					// 这里这样写是因为 ios已经进入提交审核阶段，然后发现和总里程搞混了，所以临时这样写
					or.setMileage(status.getRangeMileage());
				} else {
					or.setMileage(0.0);
				}
				or.setPower(car.getPower());
				voList.add(or);
			}
			result.setData(voList);
			result.setCode(CarConstant.success_code);
		} else {
			result.setCode(CarConstant.fail_code);
			result.setMsg(CarConstant.available_car_msg);
		}
		return result;
	}

	/**
	 * 方法说明:将按特定order对象转换成自定vo对象
	 */
	ResultInfo<CarStatusVo> carStatusToVoOne(ResultInfo<CarStatusVo> result, CarStatus carStatus, String longitude,
			String latitude, Order order) {
		if (carStatus != null) {
			CarStatusVo or = new CarStatusVo();
			or.setCarNo(carStatus.getCarNo());
			or.setCarPlateNo(carStatus.getCarPlateNo());
			or.setCarStatus(carStatus.getCarStatus());
			or.setChargeState(carStatus.getChargeState());
			or.setDeviceNo(carStatus.getDeviceNo());
			or.setLocationParkNo(carStatus.getLocationParkNo());
			if (carStatus.getRangeMileage() != null) {
				or.setRangeMileage(carStatus.getRangeMileage());
			} else {
				or.setRangeMileage(0.0);
			}
			or.setPower(carStatus.getPower());
			or.setSpeed(carStatus.getSpeed());
			or.setUserageStatus(carStatus.getUserageStatus());
			// 得到device
			Device device = deviceService.getDevice(carStatus.getDeviceNo());
			if (device != null) {
				or.setMacAddr(device.getMacAddr());
				or.setDeviceSn(device.getDeviceSn());
				or.setVersionType(device.getVersionType());
			}
			// 提交订单向AppLocation 添加数据（订车）
			if (longitude != null && !"".equals(longitude) && latitude != null && !"".equals(latitude)) {
				AppLocation appLocation = new AppLocation();
				appLocation.setAppLatitude(Double.valueOf(latitude));
				appLocation.setAppLongitude(Double.valueOf(longitude));
				appLocation.setType(2);
				appLocation.setDocumentNo(order.getOrderNo());
				appLocation.setMemberNo(order.getMemberNo());
				try {
					// 根据经纬度反查地址
					String urlString = "http://api.map.baidu.com/geocoder/v2/?ak=" + ak + "&location=" + latitude + ","
							+ longitude + "&output=json&pois=0";
					String result1 = HttpURLRequestUtil.doMsgGet(urlString);
					Gson gson = new Gson();
					AppLocationVOSearchBaiDu appLocationVOSearchBaiDu = gson.fromJson(result1,
							AppLocationVOSearchBaiDu.class);
					if (appLocationVOSearchBaiDu != null && appLocationVOSearchBaiDu.getStatus() == 0
							&& appLocationVOSearchBaiDu.getResult() != null) {
						appLocation.setAddrStreet(appLocationVOSearchBaiDu.getResult().getFormatted_address());
						AddressComponent addressComponent = appLocationVOSearchBaiDu.getResult().getAddressComponent();
						appLocation.setAddrRegion1Name(addressComponent.getProvince());
						SysRegion sr1 = sysRegionService.getRegionIdByRegionName(addressComponent.getCity());
						if (sr1 != null) {
							appLocation.setAddrRegion1Id(sr1.getRegionId());
						}
						if (addressComponent.getDistrict() != null && !"".equals(addressComponent.getDistrict())) {
							appLocation.setAddrRegion2Name(addressComponent.getCity());
							SysRegion sr2 = sysRegionService.getRegionIdByRegionName(addressComponent.getCity());
							if (sr2 != null) {
								appLocation.setAddrRegion2Id(sr2.getRegionId());
							}
							// 根据找到的市区匹配数据字典
							DataDictItem ddi = dataDictItemService.getItemValue(addressComponent.getCity(), "CITY");
							if (ddi != null) {
								appLocation.setCityId(ddi.getDataDictItemId());
							}
						}
						if (addressComponent.getDistrict() != null && !"".equals(addressComponent.getDistrict())) {
							SysRegion sr2 = sysRegionService.getRegionIdByRegionName(addressComponent.getCity());
							appLocation.setAddrRegion3Name(addressComponent.getDistrict());
							SysRegion s = new SysRegion();
							s.setRegionName(addressComponent.getDistrict());
							List<SysRegion> sr3 = sysRegionService.list(new Query(s));
							if (sr3 != null && sr3.size() > 0) {
								appLocation.setAddrRegion3Id(sr3.get(0).getParentId());
							}
						}

						appLocationService.addAppLocation(appLocation, getOperator());
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			or.setDeviceKey(DEVICE_KEY);
			result.setData(or);
			result.setCode(CarConstant.success_code);
			result.setMsg("");
		} else {
			result.setCode(CarConstant.fail_code);
			result.setMsg(CarConstant.fail_msg);
		}

		return result;
	}

	/**
	 * 方法说明:将按特定order对象转换成自定vo对象
	 */
	ResultInfo<OrderVO> orderToVoOne(ResultInfo<OrderVO> result, Order order) {
		if (order != null) {
			// 结束订单时 向 会员表修改最后结束订单时间
			if (order != null && order.getFinishTime() != null) {
				Member mf = memberService.getMember(order.getMemberNo());
				if (mf != null) {
					mf.setOrderFinishTime(order.getFinishTime());
					memberService.updateMember(mf, getOperator());
				}
				CarStatus cst = carStatusService.getCarStatus(order.getCarNo());
				if (cst != null) {
					cst.setOrderFinishTime(order.getFinishTime());
					carStatusService.updateCarStatus(cst, getOperator());
				}
			}
			OrderVO or = new OrderVO();
			or.setAppointmentTime(ECDateUtils.formatTime(order.getAppointmentTime()));
			or.setMemberNo(order.getMemberNo());
			or.setOrderAmount(order.getOrderAmount());
			or.setOrderNo(order.getOrderNo());
			or.setOrderStatus(order.getOrderStatus());
			or.setPayableAmount(order.getPayableAmount());
			or.setPayStatus(order.getPayStatus());
			if (order.getOrderMileage() != null) {
				or.setOrderMileage(ECNumberUtils.roundDoubleWithScale(order.getOrderMileage(), 2));
			} else {
				or.setOrderMileage(0d);
			}
			or.setIsRedPacketSend(order.getIsRedPacketSend());
			if (order.getDiscountAmount() != null) {
				or.setDiscountAmount(order.getDiscountAmount());
			} else {
				or.setDiscountAmount(0d);
			}

			if (order.getPackMinutesDiscountAmount() != null) {
				or.setPackMinutesDiscountAmount(order.getPackMinutesDiscountAmount());
			} else {
				or.setPackMinutesDiscountAmount(0d);
			}

			or.setNeedPayaAmount(ECNumberUtils
					.roundDoubleWithScale(order.getOrderAmount() - order.getPackMinutesDiscountAmount(), 2));
			if (order.getPayStatus().equals(0)) {
				or.setAlreadPayAmount(0d);
			} else if (order.getPayStatus().equals(1)) {
				or.setAlreadPayAmount(ECNumberUtils.roundDoubleWithScale(order.getPayableAmount(), 2));
			}
			if (order.getActualTerminalParkNo() != null && !order.getActualTerminalParkNo().equals("")) {
				Park p = parkService.getPark(order.getActualTerminalParkNo());
				if (p != null) {
					String location = p.getAddrRegion1Name() + p.getAddrRegion2Name() + p.getAddrRegion3Name()
							+ p.getAddrStreet();
					or.setActualTerminalLocation(location);
				}
			}
			or.setIntegral(0);
			or.setCreateTime(ECDateUtils.formatTime(order.getCreateTime()));
			// 获取 起步价
			if (!"".equals(order.getRuleNo()) && order.getRuleNo() != null) {
				PricingRule pricingRule = pricingRuleService.getPricingRule(order.getRuleNo());
				if (pricingRule != null) {
					or.setBaseFee(pricingRule.getBaseFee());
				} else {
					or.setBaseFee(0d);
				}
			} else {
				or.setBaseFee(0d);
			}
			// //查出 套餐集合
			// List<PricingPackOrderInvoiceVo> pricingPackOrderInvoiceVos = new
			// ArrayList<PricingPackOrderInvoiceVo>();
			//
			// PricingDeductionRecord pricingDeductionRecord=new PricingDeductionRecord();
			// pricingDeductionRecord.setMemberNo(order.getMemberNo());
			// pricingDeductionRecord.setOrderNo(order.getOrderNo());
			// Query q= new Query(pricingDeductionRecord);
			// List<PricingDeductionRecord>
			// pricingDeductionRecords=pricingDeductionRecordService.getPricingDeductionRecordList(q);
			// if(pricingDeductionRecords!=null && pricingDeductionRecords.size()>0){
			// for (PricingDeductionRecord pricingDeductionRecord2 :
			// pricingDeductionRecords) {
			// if(pricingDeductionRecord2.getPackOrderNo() != null){
			// PricingPackOrder
			// pricingPackOrder=pricingPackOrderService.getPricingPackOrder(pricingDeductionRecord2.getPackOrderNo());
			// if(pricingPackOrder != null){
			// PricingPackOrderInvoiceVo pricingPackOrderInvoiceVo=new
			// PricingPackOrderInvoiceVo();
			// pricingPackOrderInvoiceVo.setPackOrderNo(pricingPackOrder.getPackOrderNo());
			// pricingPackOrderInvoiceVo.setPackageName(pricingPackOrder.getPackageName());
			// pricingPackOrderInvoiceVos.add(pricingPackOrderInvoiceVo);
			// }
			// }
			//
			//
			// }
			// }
			// or.setPricingPackOrderInvoiceVos(pricingPackOrderInvoiceVos);
			// pricingPackOrder.seto
			// Query q=new Query();
			// List<PricingPackOrder>
			// pricingPackOrders=pricingPackOrderService.getPricingPackageList(q);

			result.setData(or);
			result.setCode(CarConstant.success_code);
			result.setMsg("");
		} else {
			result.setCode(CarConstant.fail_code);
			result.setMsg(CarConstant.fail_msg);
		}

		return result;
	}

	public ResultInfo<MemberStatusVO> getStatus(String memberNo) {

		ResultInfo<MemberStatusVO> result = new ResultInfo<MemberStatusVO>();
		Member member = memberService.getMember(memberNo);
		Query q = new Query();
		DepositOrder dOrder = new DepositOrder();
		dOrder.setMemberNo(memberNo);
		dOrder.setPayStatus(1);// 已支付
		dOrder.setIsAvailable(1);
		q.setQ(dOrder);
		List<DepositOrder> dOrderPay = depositOrderService.getDepositOrderList(q);
		return ToMemberStatusVO(member, dOrderPay, result);
	}

	private ResultInfo<MemberStatusVO> ToMemberStatusVO(Member member, List<DepositOrder> dOrderPay,
			ResultInfo<MemberStatusVO> result) {
		MemberStatusVO vo = new MemberStatusVO();
		result.setCode(MemberConstant.success_code);
		result.setMsg("");
		if (member != null && member.getCensorStatus() != null) {
			vo.setCensorStatus(member.getCensorStatus());
		} else {
			vo.setCensorStatus(-1);
			result.setCode(MemberConstant.fail_code);
			result.setMsg(MemberConstant.fail_msg);
		}
		ResultInfo<Deposit> rDeposit = depositOrderService.getDepositByMemberNo(member.getMemberNo(), null);
		SysParam sysParam = sysParamService.getByParamKey(DepositConstant.deposit_amount_key);// 默认押金
		if (sysParam != null && sysParam.getParamValue().equals("0")) {// 如果押金默认为0 则默认可以不交押金 走订车流程
			vo.setDepositStatus(1);
		} else {
			if (rDeposit.getCode().equals("1")) {
				vo.setDepositStatus(rDeposit.getData().getDepositStatus());
			}
		}

		result.setData(vo);
		return result;
	}

	@RequestMapping("/goAddOrderForCar")
	@ResponseBody
	public ResultInfo<CarAndStatus> goAddOrderForCar(String memberNo, String carNo) throws ParseException {
		ResultInfo<CarAndStatus> result = new ResultInfo<CarAndStatus>();

		Car car = carService.getCar(carNo);
		CarStatus carStatus = carStatusService.getCarStatus(carNo);
		if (car == null || carStatus == null) {

			// 删除错误数据
			try {
				boolean res = baseIndexService.delete("C" + carNo);
				if (res) {
					System.err.println("solr中" + carNo + "数据已被删除");
				} else {
					System.err.println("solr中" + carNo + "数据存在异常，删除失败，请手动删除");
				}
			} catch (Exception e) {
				System.err.println("solr中" + carNo + "数据存在异常，删除失败，请手动删除");
				e.printStackTrace();
			} finally {
				result.setCode(CarConstant.fail_code);
				result.setMsg("抱歉您选择的车辆出现了异常，请更换车辆");
			}

			return result;
		}

		CarAndStatus carAndStatus = new CarAndStatus();
		CarRedPacket carRedPacket = carRedPacketService.getCarRedPacketByCarPlateNo(car.getCarPlateNo(), "1");
		if (carRedPacket != null) {
			carAndStatus.setIsCarRedPakcet("1");
			CarRedPacketVo carRedPacketVo = new CarRedPacketVo();
			carRedPacketVo.setIsCharge(carRedPacket.getIsCharge());
			carRedPacketVo.setIsOrderAmountLimit(carRedPacket.getIsOrderAmountLimit());
			if (carRedPacket.getIsOrderAmountLimit() == 1) {
				carRedPacketVo.setOrderAmountLimit(carRedPacket.getOrderAmountLimit());
			} else {
				carRedPacketVo.setOrderAmountLimit(0.0);
			}
			carAndStatus.setCarRedPacketVo(carRedPacketVo);
		}
		carAndStatus.setCarNo(carNo);
		carAndStatus.setCarPlateNo(car.getCarPlateNo());
		carAndStatus.setCarBrandId(car.getCarBrandId());
		carAndStatus.setCarBrandName(car.getCarBrandName());
		carAndStatus.setCarModelId(car.getCarModelId());
		carAndStatus.setCarModelName(car.getCarModelName());
		carAndStatus.setCarPhotoUrl1(car.getCarPhotoUrl1());
		carAndStatus.setPower(carStatus.getPower());
		carAndStatus.setMileage(carStatus.getMileage());
		carAndStatus.setRangeMileage(carAndStatus.getRangeMileage());
		if (carStatus.getLatitude() != null && carStatus.getLongitude() != null) {
			String address = baiduGeocoderApiService.getAddressByGPS(carStatus.getLatitude(), carStatus.getLongitude());
			carAndStatus.setAddress(address);

			double[] bdCoord = ECGeoCoordinateTransformUtil.wgs84tobd09(carStatus.getLongitude(),
					carStatus.getLatitude());
			carAndStatus.setLongitude(bdCoord[0]);
			carAndStatus.setLatitude(bdCoord[1]);
		} else {
			carAndStatus.setLongitude(0.0);
			carAndStatus.setLatitude(0.0);
		}

		if (car.getSeaTing() == null || "".equals(car.getSeaTing())) {
			carAndStatus.setSeaTing("0");
		} else {
			carAndStatus.setSeaTing(car.getSeaTing());
		}
		if (carStatus.getRangeMileage() == null) {
			carAndStatus.setRangeMileage(0.0);
		} else {
			carAndStatus.setRangeMileage(carStatus.getRangeMileage());
		}
		Member member = null;
		if (memberNo != null && memberNo.trim().length() > 0) {
			member = memberService.getMember(memberNo);
		}

		PricingRule pricingRule = null;

		if (member != null && member.getMemberType() != null && member.getMemberType() == 2
				&& member.getCompanyId() != null && !member.getCompanyId().equals("")) {
			pricingRule = pricingRuleService.getPricingRuleUseByCompanyId(carAndStatus.getCarBrandName(),
					carAndStatus.getCarModelName(), member.getCompanyId());
			if (pricingRule != null) {
				// 确定orderMileage.getOrderMileageDate()是周几
				int dayOfWeek = ECDateUtils.getDayOfWeek(new Date());
				int orderCaculateType = 1;
				SysParam sysparam1 = sysParamService.getByParamKey("ORDER_CACULATE_TYPE");
				if (sysparam1 != null && sysparam1.getParamValue() != null) {
					orderCaculateType = Integer.parseInt(sysparam1.getParamValue());
				}
				// 查询自定义日期
				if (orderCaculateType == 1) {
					PricingRuleCustomizedDate pricingRuleCustomizedDate = pricingRuleCustomizedDateService
							.getPricingRuleCustomizedDate(pricingRule.getRuleNo(), new Date());
					if (pricingRuleCustomizedDate != null) {// 自定义日期时间和里程
						carAndStatus.setOfMinute(pricingRuleCustomizedDate.getPriceOfMinuteCustomized() == null ? 0
								: pricingRuleCustomizedDate.getPriceOfMinuteCustomized());
						carAndStatus.setOfKm(pricingRuleCustomizedDate.getPriceOfKmCustomized() == null ? 0
								: pricingRuleCustomizedDate.getPriceOfKmCustomized());
						// carAndStatus.setBillingCapPerDay(pricingRuleCustomizedDate.getBillingCapPerDayCustomized()==null?0:pricingRuleCustomizedDate.getBillingCapPerDayCustomized());
					} else if (dayOfWeek == Calendar.SATURDAY || dayOfWeek == Calendar.SUNDAY) {// 周末时间和里程（周六 周日）
						carAndStatus.setOfMinute(pricingRule.getPriceOfMinuteOrdinary() == null ? 0
								: pricingRule.getPriceOfMinuteOrdinary());
						carAndStatus.setOfKm(
								pricingRule.getPriceOfKmOrdinary() == null ? 0 : pricingRule.getPriceOfKmOrdinary());
						// carAndStatus.setBillingCapPerDay(pricingRule.getBillingCapPerDayOrdinary()==null?0:pricingRule.getBillingCapPerDayOrdinary());
					} else {// 工作日时间和里程(周一到周五)
						carAndStatus.setOfMinute(
								pricingRule.getPriceOfMinute() == null ? 0 : pricingRule.getPriceOfMinute());
						carAndStatus.setOfKm(pricingRule.getPriceOfKm() == null ? 0 : pricingRule.getPriceOfKm());
						// carAndStatus.setBillingCapPerDay(pricingRule.getBillingCapPerDay()==null?0:pricingRule.getBillingCapPerDay());

					}

				} else if (orderCaculateType == 2) {
					carAndStatus
							.setOfMinute(pricingRule.getPriceOfMinute() == null ? 0 : pricingRule.getPriceOfMinute());
					carAndStatus.setOfKm(pricingRule.getPriceOfKm() == null ? 0 : pricingRule.getPriceOfKm());
				}

				// 显示高峰时段的计费股则
				PeakHours ph = new PeakHours();
				String date = ECDateUtils.getCurrentTime();
				// 获取当前的时间（小时）
				// GregorianCalendar calendar = new GregorianCalendar();
				// int hour = calendar.get(Calendar.HOUR_OF_DAY);
				// ph.setHours(hour);
				ph.setRuleNo(pricingRule.getRuleNo());
				Query q = new Query(ph);
				List<PeakHours> peakHours = peakHoursService.getPeakHoursList(q);
				if (peakHours != null) {
					for (PeakHours phs : peakHours) {
						SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
						String peakStartHours = phs.getPeakStartHours() + ":00:00";
						String peakEndHours = phs.getPeakEndHours() + ":00:00";
						Date datePeakStartHours = formatter.parse(peakStartHours);// 开始时间
						Date datePeakEndHours = formatter.parse(peakEndHours);// 结束时间
						Date ddd = formatter.parse(date);// 当前时间
						if (datePeakStartHours.getTime() <= ddd.getTime()
								&& datePeakEndHours.getTime() >= ddd.getTime()) {
							carAndStatus.setOfMinute(phs.getPriceOfMinute() == null ? 0 : phs.getPriceOfMinute());
							carAndStatus.setOfKm(phs.getPriceOfKm() == null ? 0 : phs.getPriceOfKm());
						}

					}

				}

			}
		}

		if (pricingRule == null) {
			pricingRule = pricingRuleService.getPricingRuleUseByCar(carAndStatus.getCarBrandName(),
					carAndStatus.getCarModelName());
			if (pricingRule != null) {
				// 确定orderMileage.getOrderMileageDate()是周几
				int dayOfWeek = ECDateUtils.getDayOfWeek(new Date());
				// 查询自定义日期

				int orderCaculateType = 1;
				SysParam sysparam1 = sysParamService.getByParamKey("ORDER_CACULATE_TYPE");
				if (sysparam1 != null && sysparam1.getParamValue() != null) {
					orderCaculateType = Integer.parseInt(sysparam1.getParamValue());
				}
				// 查询自定义日期
				if (orderCaculateType == 1) {
					PricingRuleCustomizedDate pricingRuleCustomizedDate = pricingRuleCustomizedDateService
							.getPricingRuleCustomizedDate(pricingRule.getRuleNo(), new Date());
					if (pricingRuleCustomizedDate != null) {// 自定义日期时间和里程
						carAndStatus.setOfMinute(pricingRuleCustomizedDate.getPriceOfMinuteCustomized() == null ? 0
								: pricingRuleCustomizedDate.getPriceOfMinuteCustomized());
						carAndStatus.setOfKm(pricingRuleCustomizedDate.getPriceOfKmCustomized() == null ? 0
								: pricingRuleCustomizedDate.getPriceOfKmCustomized());
						// carAndStatus.setBillingCapPerDay(pricingRuleCustomizedDate.getBillingCapPerDayCustomized()==null?0:pricingRuleCustomizedDate.getBillingCapPerDayCustomized());
					} else if (dayOfWeek == Calendar.SATURDAY || dayOfWeek == Calendar.SUNDAY) {// 周末时间和里程（周六 周日）
						carAndStatus.setOfMinute(pricingRule.getPriceOfMinuteOrdinary() == null ? 0
								: pricingRule.getPriceOfMinuteOrdinary());
						carAndStatus.setOfKm(
								pricingRule.getPriceOfKmOrdinary() == null ? 0 : pricingRule.getPriceOfKmOrdinary());
						// carAndStatus.setBillingCapPerDay(pricingRule.getBillingCapPerDayOrdinary()==null?0:pricingRule.getBillingCapPerDayOrdinary());
					} else {// 工作日时间和里程(周一到周五)
						carAndStatus.setOfMinute(
								pricingRule.getPriceOfMinute() == null ? 0 : pricingRule.getPriceOfMinute());
						carAndStatus.setOfKm(pricingRule.getPriceOfKm() == null ? 0 : pricingRule.getPriceOfKm());
						// carAndStatus.setBillingCapPerDay(pricingRule.getBillingCapPerDay()==null?0:pricingRule.getBillingCapPerDay());

					}

				} else if (orderCaculateType == 2) {
					carAndStatus
							.setOfMinute(pricingRule.getPriceOfMinute() == null ? 0 : pricingRule.getPriceOfMinute());
					carAndStatus.setOfKm(pricingRule.getPriceOfKm() == null ? 0 : pricingRule.getPriceOfKm());
				}

				// 显示高峰时段的计费股则
				PeakHours ph = new PeakHours();
				String date = ECDateUtils.getCurrentTime();
				// 获取当前的时间（小时）
				// GregorianCalendar calendar = new GregorianCalendar();
				// int hour = calendar.get(Calendar.HOUR_OF_DAY);
				// ph.setHours(hour);
				ph.setRuleNo(pricingRule.getRuleNo());
				Query q = new Query(ph);
				List<PeakHours> peakHours = peakHoursService.getPeakHoursList(q);
				if (peakHours != null) {
					for (PeakHours phs : peakHours) {
						SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
						String peakStartHours = phs.getPeakStartHours() + ":00:00";
						String peakEndHours = phs.getPeakEndHours() + ":00:00";
						Date datePeakStartHours = formatter.parse(peakStartHours);// 开始时间
						Date datePeakEndHours = formatter.parse(peakEndHours);// 结束时间
						Date ddd = formatter.parse(date);// 当前时间
						if (datePeakStartHours.getTime() <= ddd.getTime()
								&& datePeakEndHours.getTime() >= ddd.getTime()) {
							carAndStatus.setOfMinute(phs.getPriceOfMinute() == null ? 0 : phs.getPriceOfMinute());
							carAndStatus.setOfKm(phs.getPriceOfKm() == null ? 0 : phs.getPriceOfKm());
						}

					}

				}

			}
		}
		// 为 预约的最大分钟数：minute；预约时间：prebookTime 赋值
		SysParam sysParam = sysParamService.getByParamKey(CarConstant.prebook_param_key);
		int minute = 0;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String prebookTime = null;

		if (sysParam != null) {
			minute = Integer.parseInt(sysParam.getParamValue());
			// 当前时间再加上现有的分钟数
			Calendar now = Calendar.getInstance();
			now.setTime(new Date());
			now.add(Calendar.MINUTE, +minute);
			Date date = now.getTime(); // 结果
			prebookTime = sdf.format(date);
		}
		carAndStatus.setMinute(minute);
		carAndStatus.setCarPhotoUrl1(imgPath + "/" + carAndStatus.getCarPhotoUrl1());
		carAndStatus.setPrebookTime(prebookTime);
		carAndStatus.setVehicleType(carStatus.getVehicleType());
		result.setCode(CarConstant.success_code);
		result.setMsg("");
		result.setData(carAndStatus);
		return result;
	}

	/**
	 * 确认单订车页面 查看车辆信息
	 * 
	 * @param memberNo
	 * @param carNo
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping("/getCarDetail")
	@ResponseBody
	public ResultInfo<CarDetail> getCarDetail(String memberNo, String carNo) throws ParseException {
		ResultInfo<CarDetail> result = new ResultInfo<CarDetail>();

		Car car = carService.getCar(carNo);
		CarStatus carStatus = carStatusService.getCarStatus(carNo);
		if (car == null || carStatus == null) {
			result.setCode(CarConstant.fail_code);
			result.setMsg(CarConstant.available_car_msg);
		}

		CarDetail carDetail = new CarDetail();
		if (carStatus.getLocationParkNo() != null && !"".equals(carStatus.getLocationParkNo())) { // 车辆有所在场站
			carDetail.setIsPark(1);
		}
		CarRedPacket carRedPacket = carRedPacketService.getCarRedPacketByCarPlateNo(car.getCarPlateNo(), "1");
		if (carRedPacket != null) {
			carDetail.setIsCarRedPakcet("1");
			CarRedPacketVo carRedPacketVo = new CarRedPacketVo();
			carRedPacketVo.setIsCharge(carRedPacket.getIsCharge());
			carRedPacketVo.setIsOrderAmountLimit(carRedPacket.getIsOrderAmountLimit());
			if (carRedPacket.getIsOrderAmountLimit() == 1) {
				carRedPacketVo.setOrderAmountLimit(carRedPacket.getOrderAmountLimit());
			} else {
				carRedPacketVo.setOrderAmountLimit(0.0);
			}
			carDetail.setCarRedPacketVo(carRedPacketVo);
		}
		carDetail.setCarNo(carNo);
		carDetail.setCarPlateNo(car.getCarPlateNo());
		carDetail.setVehicleType(carStatus.getVehicleType());
		carDetail.setCarBrandId(car.getCarBrandId());
		carDetail.setCarBrandName(car.getCarBrandName());
		carDetail.setCarModelId(car.getCarModelId());
		carDetail.setCarModelName(car.getCarModelName());
		carDetail.setCarPhotoUrl1(car.getCarPhotoUrl1());
		carDetail.setPower(carStatus.getPower());
		carDetail.setMileage(carStatus.getMileage());
		carDetail.setRangeMileage(carDetail.getRangeMileage());
		if (car.getSeaTing() == null || "".equals(car.getSeaTing())) {
			carDetail.setSeaTing("0");
		} else {
			carDetail.setSeaTing(car.getSeaTing());
		}
		if (carStatus.getRangeMileage() == null) {
			carDetail.setRangeMileage(0.0);
		} else {
			carDetail.setRangeMileage(carStatus.getRangeMileage());
		}
		Member member = null;
		if (memberNo != null && memberNo.trim().length() > 0) {
			member = memberService.getMember(memberNo);
		}

		PricingRule pricingRule = null;
		// 集团会员
		if (member != null && member.getMemberType() != null && member.getMemberType() == 2
				&& member.getCompanyId() != null && !member.getCompanyId().equals("")) {
			pricingRule = pricingRuleService.getPricingRuleUseByCompanyId(carDetail.getCarBrandName(),
					carDetail.getCarModelName(), member.getCompanyId());
			if (pricingRule != null) {

				// 确定orderMileage.getOrderMileageDate()是周几
				int dayOfWeek = ECDateUtils.getDayOfWeek(new Date());

				int orderCaculateType = 1;
				SysParam sysparam1 = sysParamService.getByParamKey("ORDER_CACULATE_TYPE");
				if (sysparam1 != null && sysparam1.getParamValue() != null) {
					orderCaculateType = Integer.parseInt(sysparam1.getParamValue());
				}
				if (orderCaculateType == 1) {
					// 查询自定义日期
					PricingRuleCustomizedDate pricingRuleCustomizedDate = pricingRuleCustomizedDateService
							.getPricingRuleCustomizedDate(pricingRule.getRuleNo(), new Date());
					if (pricingRuleCustomizedDate != null) {// 自定义日期时间和里程
						carDetail.setOfMinute(pricingRuleCustomizedDate.getPriceOfMinuteCustomized() == null ? 0
								: pricingRuleCustomizedDate.getPriceOfMinuteCustomized());
						carDetail.setOfKm(pricingRuleCustomizedDate.getPriceOfKmCustomized() == null ? 0
								: pricingRuleCustomizedDate.getPriceOfKmCustomized());
						carDetail.setBillingCapPerDay(
								pricingRuleCustomizedDate.getBillingCapPerDayCustomized() == null ? 0
										: pricingRuleCustomizedDate.getBillingCapPerDayCustomized());
					} else if (dayOfWeek == Calendar.SATURDAY || dayOfWeek == Calendar.SUNDAY) {// 周末时间和里程（周六 周日）
						carDetail.setOfMinute(pricingRule.getPriceOfMinuteOrdinary() == null ? 0
								: pricingRule.getPriceOfMinuteOrdinary());
						carDetail.setOfKm(
								pricingRule.getPriceOfKmOrdinary() == null ? 0 : pricingRule.getPriceOfKmOrdinary());
						carDetail.setBillingCapPerDay(pricingRule.getBillingCapPerDayOrdinary() == null ? 0
								: pricingRule.getBillingCapPerDayOrdinary());
					} else {// 工作日时间和里程(周一到周五)
						carDetail.setOfMinute(
								pricingRule.getPriceOfMinute() == null ? 0 : pricingRule.getPriceOfMinute());
						carDetail.setOfKm(pricingRule.getPriceOfKm() == null ? 0 : pricingRule.getPriceOfKm());
						carDetail.setBillingCapPerDay(
								pricingRule.getBillingCapPerDay() == null ? 0 : pricingRule.getBillingCapPerDay());

					}
				} else if (orderCaculateType == 2) {
					carDetail.setOfMinute(pricingRule.getPriceOfMinute() == null ? 0 : pricingRule.getPriceOfMinute());
					carDetail.setOfKm(pricingRule.getPriceOfKm() == null ? 0 : pricingRule.getPriceOfKm());
					carDetail.setBillingCapPerDay(
							pricingRule.getBillingCapPerDay() == null ? 0 : pricingRule.getBillingCapPerDay());
				}

				carDetail.setBaseFee(pricingRule.getBaseFee() == null ? 0 : pricingRule.getBaseFee());

				// 显示高峰时段的计费股则
				PeakHours ph = new PeakHours();
				String date = ECDateUtils.getCurrentTime();
				// 获取当前的时间（小时）
				// GregorianCalendar calendar = new GregorianCalendar();
				// int hour = calendar.get(Calendar.HOUR_OF_DAY);
				// ph.setHours(hour);
				ph.setRuleNo(pricingRule.getRuleNo());
				Query q = new Query(ph);
				List<PeakHours> peakHours = peakHoursService.getPeakHoursList(q);
				if (peakHours != null) {
					for (PeakHours phs : peakHours) {
						SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
						String peakStartHours = phs.getPeakStartHours() + ":00:00";
						String peakEndHours = phs.getPeakEndHours() + ":00:00";
						Date datePeakStartHours = formatter.parse(peakStartHours);// 开始时间
						Date datePeakEndHours = formatter.parse(peakEndHours);// 结束时间
						Date ddd = formatter.parse(date);// 当前时间
						if (datePeakStartHours.getTime() <= ddd.getTime()
								&& datePeakEndHours.getTime() >= ddd.getTime()) {
							carDetail.setOfMinute(phs.getPriceOfMinute() == null ? 0 : phs.getPriceOfMinute());
							carDetail.setOfKm(phs.getPriceOfKm() == null ? 0 : phs.getPriceOfKm());
						}

					}

				}

			}

		}
		// 普通会员和未登录
		if (pricingRule == null) {
			pricingRule = pricingRuleService.getPricingRuleUseByCar(carDetail.getCarBrandName(),
					carDetail.getCarModelName());
			if (pricingRule != null) {

				// 确定orderMileage.getOrderMileageDate()是周几
				int dayOfWeek = ECDateUtils.getDayOfWeek(new Date());
				int orderCaculateType = 1;
				SysParam sysparam1 = sysParamService.getByParamKey("ORDER_CACULATE_TYPE");
				if (sysparam1 != null && sysparam1.getParamValue() != null) {
					orderCaculateType = Integer.parseInt(sysparam1.getParamValue());
				}
				if (orderCaculateType == 1) {
					// 查询自定义日期
					PricingRuleCustomizedDate pricingRuleCustomizedDate = pricingRuleCustomizedDateService
							.getPricingRuleCustomizedDate(pricingRule.getRuleNo(), new Date());
					if (pricingRuleCustomizedDate != null) {// 自定义日期时间和里程
						carDetail.setOfMinute(pricingRuleCustomizedDate.getPriceOfMinuteCustomized() == null ? 0
								: pricingRuleCustomizedDate.getPriceOfMinuteCustomized());
						carDetail.setOfKm(pricingRuleCustomizedDate.getPriceOfKmCustomized() == null ? 0
								: pricingRuleCustomizedDate.getPriceOfKmCustomized());
						carDetail.setBillingCapPerDay(
								pricingRuleCustomizedDate.getBillingCapPerDayCustomized() == null ? 0
										: pricingRuleCustomizedDate.getBillingCapPerDayCustomized());
					} else if (dayOfWeek == Calendar.SATURDAY || dayOfWeek == Calendar.SUNDAY) {// 周末时间和里程（周六 周日）
						carDetail.setOfMinute(pricingRule.getPriceOfMinuteOrdinary() == null ? 0
								: pricingRule.getPriceOfMinuteOrdinary());
						carDetail.setOfKm(
								pricingRule.getPriceOfKmOrdinary() == null ? 0 : pricingRule.getPriceOfKmOrdinary());
						carDetail.setBillingCapPerDay(pricingRule.getBillingCapPerDayOrdinary() == null ? 0
								: pricingRule.getBillingCapPerDayOrdinary());
					} else {// 工作日时间和里程(周一到周五)
						carDetail.setOfMinute(
								pricingRule.getPriceOfMinute() == null ? 0 : pricingRule.getPriceOfMinute());
						carDetail.setOfKm(pricingRule.getPriceOfKm() == null ? 0 : pricingRule.getPriceOfKm());
						carDetail.setBillingCapPerDay(
								pricingRule.getBillingCapPerDay() == null ? 0 : pricingRule.getBillingCapPerDay());

					}
				} else if (orderCaculateType == 2) {
					carDetail.setOfMinute(pricingRule.getPriceOfMinute() == null ? 0 : pricingRule.getPriceOfMinute());
					carDetail.setOfKm(pricingRule.getPriceOfKm() == null ? 0 : pricingRule.getPriceOfKm());
					carDetail.setBillingCapPerDay(
							pricingRule.getBillingCapPerDay() == null ? 0 : pricingRule.getBillingCapPerDay());
				}
			}
			carDetail.setBaseFee(pricingRule.getBaseFee() == null ? 0 : pricingRule.getBaseFee());

			// 显示高峰时段的计费股则
			PeakHours ph = new PeakHours();
			String date = ECDateUtils.getCurrentTime();
			// 获取当前的时间（小时）
			// GregorianCalendar calendar = new GregorianCalendar();
			// int hour = calendar.get(Calendar.HOUR_OF_DAY);
			// ph.setHours(hour);
			ph.setRuleNo(pricingRule.getRuleNo());
			Query q = new Query(ph);
			List<PeakHours> peakHours = peakHoursService.getPeakHoursList(q);
			if (peakHours != null) {
				for (PeakHours phs : peakHours) {
					SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
					String peakStartHours = phs.getPeakStartHours() + ":00:00";
					String peakEndHours = phs.getPeakEndHours() + ":00:00";
					Date datePeakStartHours = formatter.parse(peakStartHours);// 开始时间
					Date datePeakEndHours = formatter.parse(peakEndHours);// 结束时间
					Date ddd = formatter.parse(date);// 当前时间
					if (datePeakStartHours.getTime() <= ddd.getTime() && datePeakEndHours.getTime() >= ddd.getTime()) {
						carDetail.setOfMinute(phs.getPriceOfMinute() == null ? 0 : phs.getPriceOfMinute());
						carDetail.setOfKm(phs.getPriceOfKm() == null ? 0 : phs.getPriceOfKm());
					}

				}

			}

		}

		if (carStatus.getLongitude() != null && carStatus.getLatitude() != null) {
			double[] res = ECGeoCoordinateTransformUtil.wgs84tobd09(carStatus.getLongitude(), carStatus.getLatitude());
			carDetail.setLongitude(res[0]);
			carDetail.setLatitude(res[1]);
		} else {
			carDetail.setLongitude(0.0);
			carDetail.setLatitude(0.0);
		}
		// 得到（系统参数设置的）20分钟；
		SysParam sysp = sysParamService.getByParamKey(CarConstant.countdown_param_key);
		int dingshi = 20;
		if (sysp != null && sysp.getParamValue() != null && !sysp.getParamValue().equals("")) {
			dingshi = Integer.parseInt(sysp.getParamValue());
			carDetail.setDingshi(dingshi);
		}

		SysParam sysParams = sysParamService.getByParamKey("RETURN_CAR_OUTSIDE_SWITCH");// 1.只能在场内还车；2.场站内外都允许还车；3.没有场站只有区域还车
		if (sysParams != null) {
			if ("1".equals(sysParams.getParamValue())) {
				carDetail.setReturnCarStatus(1);
			} else if ("2".equals(sysParams.getParamValue())) {
				carDetail.setReturnCarStatus(2);
			} else {
				carDetail.setReturnCarStatus(3);
			}

			if ("1".equals(sysParams.getParamValue()) || "2".equals(sysParams.getParamValue())) {
				if (carStatus != null) {
					Park p = parkService.getPark(carStatus.getLocationParkNo());
					if (p != null) {
						carDetail.setServiceFeeGet(p.getServiceFeeGet());
					}
				}

			}
		}

		// 是否支持不计免赔
		// SysParam syspd = sysParamService.getByParamKey(CarConstant.IS_REGARDLESS);
		// if(syspd != null && syspd.getParamValue() != null &&
		// !"".equals(syspd.getParamValue()) && syspd.getParamValue().equals("1")){
		// carDetail.setIsRegardless(1);
		// }else{
		// carDetail.setIsRegardless(0);
		// }
		if (pricingRule != null) {
			// 不计免赔默认值勾选
			SysParam s7 = sysParamService.getByParamKey("forceRegardless");
			if (s7 != null && s7.getParamValue() != null) {
				carDetail.setForceRegardless(Integer.valueOf(s7.getParamValue()));
			} else {
				carDetail.setForceRegardless(0);
			}
			// 不计金额
			// SysParam syspds =
			// sysParamService.getByParamKey(CarConstant.REGARDLESS_FRANCHISE);
			// if(syspds != null && syspds.getParamValue() != null &&
			// !"".equals(syspds.getParamValue())){
			// carDetail.setRegardlessFranchise(Double.valueOf(syspds.getParamValue()));
			// }else{
			// carDetail.setRegardlessFranchise(0d);
			// }
			// 不计免赔金额改为从计费规则获取
			if (pricingRule.getRegardlessFranchise() != null && pricingRule.getRegardlessFranchise() > 0.0) {
				carDetail.setRegardlessFranchise(pricingRule.getRegardlessFranchise());
				carDetail.setIsRegardless(1);
			} else {
				carDetail.setRegardlessFranchise(0d);
				carDetail.setIsRegardless(0);
			}
			// 保险金额
			// SysParam sp = sysParamService.getByParamKey("INSURANCE_AMOUNT");
			// if(sp != null && sp.getParamValue() != null && !"".equals(sp.getParamValue())
			// ){
			// String
			// InsuranceTip="客户在租赁期间内不计免赔，则无需承担"+sp.getParamValue()+"元的车辆绝对免赔额，更多详细内容可前往帮助中心查看。";
			// carDetail.setInsuranceTips(InsuranceTip);
			// }else{
			// carDetail.setInsuranceTips("");
			// }
			// 保险金额改为从计费规则获取
			if (pricingRule.getInsuranceAmount() != null && pricingRule.getInsuranceAmount() > 0.0) {
				String InsuranceTip = "客户在租赁期间内不计免赔，则无需承担" + pricingRule.getInsuranceAmount()
						+ "元的车辆绝对免赔额，更多详细内容可前往帮助中心查看。";
				carDetail.setInsuranceTips(InsuranceTip);
			} else {
				carDetail.setInsuranceTips("");
			}
		}
		// carDetail.setMinute(minute);
		carDetail.setCarPhotoUrl1(imgPath + "/" + carDetail.getCarPhotoUrl1());
		// carDetail.setPrebookTime(prebookTime);
		result.setCode(CarConstant.success_code);
		result.setMsg("");
		result.setData(carDetail);
		return result;
	}

	/**
	 * 确认单订车页面 弹出的三个
	 * 
	 * @param memberNo
	 * @param carNo
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping("/getPricingRule")
	@ResponseBody
	public ResultInfo<PricingRuleVo> getPricingRule(String memberNo, String carNo) throws ParseException {
		ResultInfo<PricingRuleVo> result = new ResultInfo<PricingRuleVo>();
		// String name="";
		// SysParam sys=sysParamService.getByParamKey("APP_NAME");
		// if(sys != null){
		// name = sys.getParamValue();
		// }else{
		// name = "猛龙出行";
		// }
		//
		PricingRuleVo pricingRuleVo = new PricingRuleVo();
		String pricingRuleBill = "";
		String billingInstructions = "";
		String anyPointExplanation = "";
		SysParam sysParams = sysParamService.getByParamKey("RETURN_CAR_OUTSIDE_SWITCH");// 1.只能在场内还车；2.场站内外都允许还车；3.没有场站只有区域还车
		if (sysParams != null) {
			SysParam syss = sysParamService.getByParamKey("pricingRuleBill");
			if (!"".equals(syss.getParamValue())) {
				pricingRuleBill = syss.getParamValue();
			}
			pricingRuleVo.setReturnCarStatus(Integer.parseInt(sysParams.getParamValue()));
			if ("1".equals(sysParams.getParamValue())) {
				SysParam s = sysParamService.getByParamKey("pricingRuleBill");
				if (s != null && !"".equals(s.getParamValue())) {
					pricingRuleBill = s.getParamValue();
				} else {
					pricingRuleBill = "1.我们拥有大量的服务网点，用户可从各网点取还车辆 |  2.我们不支持随意还车，用户还车时可查询附近网点 |  3.部分网点可能收取服务费用。具体请见网点收费说明";
				}

			} else if ("2".equals(sysParams.getParamValue()) || "3".equals(sysParams.getParamValue())) {
				SysParam ss = sysParamService.getByParamKey("pricingRuleBill2");
				if (ss != null && !"".equals(ss.getParamValue())) {
					pricingRuleBill = ss.getParamValue();
				} else {
					pricingRuleBill = "1.我们拥有多个的服务网点，方便用户随时用车。网点内用车不产生异地费用。| 2.我们也支持随意还车，随意还车只需停放在合法车位，但随意还可能产生一定的服务费用。| 3.随意还车不要停放收费场所，产生费用需用户承担。";
				}
				SysParam sss = sysParamService.getByParamKey("anyPointExplanation");
				if (sss != null && !"".equals(sss.getParamValue())) {
					anyPointExplanation = sss.getParamValue();
				} else {
					anyPointExplanation = "1.车辆必须停放在合法的停车位上。| 2.任意点还车可能会产生一定的附加服务费。 | 3.如车辆停放位置不正确。产生的罚款须客户承担。| 4.车辆不要停放到收费小区和停车场。产生的停车费用将从押金扣除 。";
				}

			}
		}
		PricingRule pricingRule = null;
		Member member = null;
		if (memberNo != null && memberNo.trim().length() > 0) {
			member = memberService.getMember(memberNo);
		}

		Car car = carService.getCar(carNo);
		CarStatus carStatus = carStatusService.getCarStatus(carNo);
		if (car == null || carStatus == null) {
			result.setCode(CarConstant.fail_code);
			result.setMsg(CarConstant.available_car_msg);
			return result;
		}

		// |05-28 ~
		// 05-29:时间(0.5元/分钟)+里程(0.6元/公|里)每日封顶250元|05-30：时间(0.3元/分钟)+里程(0.6元/公里)每日封顶210元
		// 集团会员
		if (member != null && member.getMemberType() != null && member.getMemberType() == 2
				&& member.getCompanyId() != null && !member.getCompanyId().equals("")) {
			pricingRule = pricingRuleService.getPricingRuleUseByCompanyId(car.getCarBrandName(), car.getCarModelName(),
					member.getCompanyId());
			if (pricingRule != null) {
				// 查询自定义日期
				String ruleNo = pricingRule.getRuleNo();
				PeakHours peakHoursQ = new PeakHours();
				peakHoursQ.setRuleNo(ruleNo);
				List<PeakHours> peaKHoursList = peakHoursService.getPeakHoursList(new Query(peakHoursQ));
				List<PricingRuleCustomizedDate> pricingRuleCustomizedDates = new ArrayList<PricingRuleCustomizedDate>();
				SysParam pd = sysParamService.getByParamKey(CarConstant.PRICING_DATE);
				if (pd != null && pd.getParamValue() != null && !"".equals(pd.getParamValue())) {
					Integer day = Integer.parseInt(pd.getParamValue());
					pricingRuleCustomizedDates = pricingRuleCustomizedDateService.getPricingList(ruleNo, day);
				} else {
					Integer day = 7;
					pricingRuleCustomizedDates = pricingRuleCustomizedDateService.getPricingList(ruleNo, day);
				}

				// 按公里和按时间 按封顶 计费的 一样 分组
				if (pricingRuleCustomizedDates != null && pricingRuleCustomizedDates.size() > 0) {// 自定义日期时间和里程
					String pricingdate = "";
					String bil = "";
					String billingInstruc = "";
					for (PricingRuleCustomizedDate prids : pricingRuleCustomizedDates) {
						String[] prcd = prids.getCustomizedDateStr().split("至");
						RankingStr.strSort(prcd);
						if (prcd.length > 0) {
							for (int i = 0; i < prcd.length; i++) {
								String a = prcd[i];
								String str = a.substring(5, a.length());
								prcd[i] = str;

							}
							if (prcd[0].equals(prcd[prcd.length - 1])) {
								pricingdate = prcd[0];
							} else {
								pricingdate = prcd[0] + "~" + prcd[prcd.length - 1];
							}
						}

						bil = pricingdate + " " + prids.getPriceOfMinuteCustomized() + "/分钟+"
								+ prids.getPriceOfKmCustomized() + "/公里|每日封顶" + prids.getBillingCapPerDayCustomized()
								+ "元|";
						billingInstruc += bil;
					}
					// pricingRuleCustomizedDate.getPriceOfMinuteCustomized
					// pricingRuleCustomizedDate.getPriceOfKmCustomized()
					// carAndStatus.setBillingCapPerDay(pricingRuleCustomizedDate.getBillingCapPerDayCustomized()==null?0:pricingRuleCustomizedDate.getBillingCapPerDayCustomized());
					if (pricingRule.getRuleType() == 1) {
						billingInstructions = "平日：" + pricingRule.getPriceOfMinute() + "元/分钟+"
								+ pricingRule.getPriceOfKm() + "元/公里|每日封顶" + pricingRule.getBillingCapPerDay() + "元|周末："
								+ pricingRule.getPriceOfMinuteOrdinary() + "/分钟+" + pricingRule.getPriceOfKmOrdinary()
								+ "元/公里|每日封顶" + pricingRule.getBillingCapPerDayOrdinary() + "元|" + billingInstruc;
					} else {
						billingInstructions = "平日：" + pricingRule.getPriceOfMinute() + "元/分钟+"
								+ pricingRule.getPriceOfKm() + "元/公里|每日封顶" + pricingRule.getBillingCapPerDay() + "元|"
								+ billingInstruc;
					}

				} else {
					if (pricingRule.getRuleType() == 1) {
						billingInstructions = "平日：" + pricingRule.getPriceOfMinute() + "元/分钟+"
								+ pricingRule.getPriceOfKm() + "元/公里|每日封顶" + pricingRule.getBillingCapPerDay() + "元|周末："
								+ pricingRule.getPriceOfMinuteOrdinary() + "/分钟+" + pricingRule.getPriceOfKmOrdinary()
								+ "元/公里|每日封顶" + pricingRule.getBillingCapPerDayOrdinary() + "元|";
					} else {
						billingInstructions = "平日：" + pricingRule.getPriceOfMinute() + "元/分钟+"
								+ pricingRule.getPriceOfKm() + "元/公里|每日封顶" + pricingRule.getBillingCapPerDay() + "元";
						StringBuffer content = new StringBuffer();
						for (int i = 0; i < peaKHoursList.size(); i++) {
							PeakHours item = peaKHoursList.get(i);
							content.append("|高峰时段计费规则:|" + item.getPriceOfMinute() + "元/分钟+" + item.getPriceOfKm()
									+ "元/公里(时间:" + item.getPeakStartHours() + ":00-" + item.getPeakEndHours() + ":00)");
						}
						billingInstructions += content.toString();
					}

				}
			}

		}
		// 普通会员和未登录
		if (pricingRule == null) {
			pricingRule = pricingRuleService.getPricingRuleUseByCar(car.getCarBrandName(), car.getCarModelName());
			if (pricingRule != null) {
				// 查询自定义日期
				String ruleNo = pricingRule.getRuleNo();
				PeakHours peakHoursQ = new PeakHours();
				peakHoursQ.setRuleNo(ruleNo);
				List<PeakHours> peaKHoursList = peakHoursService.getPeakHoursList(new Query(peakHoursQ));
				List<PricingRuleCustomizedDate> pricingRuleCustomizedDates = new ArrayList<PricingRuleCustomizedDate>();
				SysParam pd = sysParamService.getByParamKey(CarConstant.PRICING_DATE);
				if (pd != null && pd.getParamValue() != null && !"".equals(pd.getParamValue())) {
					Integer day = Integer.parseInt(pd.getParamValue());
					pricingRuleCustomizedDates = pricingRuleCustomizedDateService.getPricingList(ruleNo, day);
				} else {
					Integer day = 7;
					pricingRuleCustomizedDates = pricingRuleCustomizedDateService.getPricingList(ruleNo, day);
				}
				// 按公里和按时间 按封顶 计费的 一样 分组
				if (pricingRuleCustomizedDates != null && pricingRuleCustomizedDates.size() > 0) {// 自定义日期时间和里程
					String pricingdate = "";
					String bil = "";
					String billingInstruc = "";
					for (PricingRuleCustomizedDate prids : pricingRuleCustomizedDates) {
						String[] prcd = prids.getCustomizedDateStr().split("至");
						RankingStr.strSort(prcd);
						if (prcd.length > 0) {
							for (int i = 0; i < prcd.length; i++) {
								String a = prcd[i];
								String str = a.substring(5, a.length());
								prcd[i] = str;

							}
							if (prcd[0].equals(prcd[prcd.length - 1])) {
								pricingdate = prcd[0];
							} else {
								pricingdate = prcd[0] + "~" + prcd[prcd.length - 1];
							}
						}

						bil = pricingdate + " " + prids.getPriceOfMinuteCustomized() + "/分钟+"
								+ prids.getPriceOfKmCustomized() + "/公里|每日封顶" + prids.getBillingCapPerDayCustomized()
								+ "元|";
						billingInstruc += bil;
					}
					// pricingRuleCustomizedDate.getPriceOfMinuteCustomized
					// pricingRuleCustomizedDate.getPriceOfKmCustomized()
					// carAndStatus.setBillingCapPerDay(pricingRuleCustomizedDate.getBillingCapPerDayCustomized()==null?0:pricingRuleCustomizedDate.getBillingCapPerDayCustomized());
					if (pricingRule.getRuleType() == 1) {
						billingInstructions = "平日：" + pricingRule.getPriceOfMinute() + "元/分钟+"
								+ pricingRule.getPriceOfKm() + "元/公里|每日封顶" + pricingRule.getBillingCapPerDay() + "元|周末："
								+ pricingRule.getPriceOfMinuteOrdinary() + "/分钟+" + pricingRule.getPriceOfKmOrdinary()
								+ "元/公里|每日封顶" + pricingRule.getBillingCapPerDayOrdinary() + "元|" + billingInstruc;
					} else {
						billingInstructions = "平日：" + pricingRule.getPriceOfMinute() + "元/分钟+"
								+ pricingRule.getPriceOfKm() + "元/公里|每日封顶" + pricingRule.getBillingCapPerDay() + "元|"
								+ billingInstruc;
						StringBuffer content = new StringBuffer();
						for (int i = 0; i < peaKHoursList.size(); i++) {
							PeakHours item = peaKHoursList.get(i);
							content.append("|" + item.getPriceOfMinute() + "元/分钟+" + item.getPriceOfKm() + "元/公里(时间:"
									+ item.getPeakStartHours() + ":00-" + item.getPeakEndHours() + ":00)");
						}
						billingInstructions += content.toString();
					}
				} else {
					if (pricingRule.getRuleType() == 1) {
						billingInstructions = "平日：" + pricingRule.getPriceOfMinute() + "元/分钟+"
								+ pricingRule.getPriceOfKm() + "元/公里|每日封顶" + pricingRule.getBillingCapPerDay() + "元|周末："
								+ pricingRule.getPriceOfMinuteOrdinary() + "/分钟+" + pricingRule.getPriceOfKmOrdinary()
								+ "元/公里|每日封顶" + pricingRule.getBillingCapPerDayOrdinary() + "元|";
					} else {
						billingInstructions = "平日：" + pricingRule.getPriceOfMinute() + "元/分钟+"
								+ pricingRule.getPriceOfKm() + "元/公里|每日封顶" + pricingRule.getBillingCapPerDay() + "元";
						StringBuffer content = new StringBuffer();
						for (int i = 0; i < peaKHoursList.size(); i++) {
							PeakHours item = peaKHoursList.get(i);
							content.append("|高峰时段计费规则:|" + item.getPriceOfMinute() + "元/分钟+" + item.getPriceOfKm()
									+ "元/公里(时间:" + item.getPeakStartHours() + ":00-" + item.getPeakEndHours() + ":00)");
						}
						billingInstructions += content.toString();
					}
				}
			}
		}

		pricingRuleVo.setAnyPointExplanation(anyPointExplanation);
		pricingRuleVo.setBillingInstructions(billingInstructions);
		pricingRuleVo.setPricingRuleBill(pricingRuleBill);
		result.setCode(CarConstant.success_code);
		result.setMsg("");
		result.setData(pricingRuleVo);
		return result;
	}

	public static void main(String[] args) {
		System.out.println(ECDateUtils.getDateAfter(new Date(), 1));

	}

	@RequestMapping("/getCarByCarPlateNo")
	@ResponseBody
	public ResultInfo<String> getCarByCarPlateNo(String carPlateNo) {
		ResultInfo<String> resultInfo = new ResultInfo<>();
		Car car = carService.getCarByPlateNo(carPlateNo);
		if (car != null) {
			resultInfo.setCode(Constant.SECCUESS);
			resultInfo.setData(car.getCarNo());
		} else {
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg("车辆不存在");
			resultInfo.setData("");
		}
		return resultInfo;
	}

	@RequestMapping("/updateCarOrderStatus")
	@ResponseBody
	public ResultInfo<String> updateCarOrderStatus(String deviceSn) {
		ResultInfo<String> resultInfo = new ResultInfo<>();
		Device device = deviceService.getDeviceByDeviceSn(deviceSn); // 根据设备序列号查询设备
		if (device == null) {
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg("设备不存在");
			return resultInfo;
		}
		String carNo = device.getCarNo();
		if (carNo == null || carNo.trim().length() == 0) {
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg("车辆不存在");
			return resultInfo;
		}
		orderService.updateFirsttimeOpenCarDoorAndStartBillingByCarNo(carNo, new Date());
		resultInfo.setCode(Constant.SECCUESS);
		resultInfo.setMsg("更新成功");
		return resultInfo;
	}

	/**
	 * 获取终端数据(茉莉出行专用)
	 * 
	 */
	@RequestMapping("/deviceList")
	@ResponseBody
	public ResultInfo<List<DeviceListVo>> deviceList(@RequestBody String tBoxIds) {
		ResultInfo<List<DeviceListVo>> result = new ResultInfo<List<DeviceListVo>>();
		List<DeviceListVo> dlv = new ArrayList<DeviceListVo>();
		JSONArray object = JSONObject.parseObject(tBoxIds).getJSONArray("tBoxIds");
		for (int i = 0; i < object.size(); i++) {
			String no = (String) object.get(i);
			Device device = deviceService.getDeviceByDeviceSn(no);
			if (device == null) {
				result.setCode("0");
				result.setData(null);
				result.setMsg("无数据");
				continue;
			}
			DeviceListVo dv = new DeviceListVo();
			if (device.getDeviceSn() != null && !"".equals(device.getDeviceSn())) {
				dv.settBoxId(device.getDeviceSn());
			}

			String paramValue = sysParamService.getParamValueByParamKey(DeviceConstant.device_online_time_threshol);
			long onlineTimeThreshol = 120000;
			if (paramValue != null && !paramValue.equals("")) {
				onlineTimeThreshol = Integer.parseInt(paramValue) * 60000;// 将获取的分钟值转换为毫秒数
			}
			if (device.getLastReportingTime() != null) {
				long timeDifference = new Date().getTime() - device.getLastReportingTime().getTime();
				if (onlineTimeThreshol >= timeDifference) {
					dv.setConnectState("1");
				} else {
					dv.setConnectState("2");
				}
			}
			// dv.setDeviceSn(device.getDeviceSn());
			if (device.getBrandName() != null && !"".equals(device.getBrandName())) {
				dv.setFactory(device.getBrandName());
			}
			if (device.getDeviceModel() != null && !"".equals(device.getDeviceModel())) {
				dv.setModel(device.getDeviceModel());
			}
			if (device.getSimCardNo() != null && !"".equals(device.getSimCardNo())) {
				dv.setSim(device.getSimCardNo());
			}
			if (device.getVersionNumber() != null && !"".equals(device.getVersionNumber())) {
				dv.setVersion(device.getVersionNumber());
			}
			if (device.getIccid() != null && !"".equals(device.getIccid())) {
				dv.setIccid(device.getIccid());
			}
			if (device.getVin() != null && !"".equals(device.getVin())) {
				dv.setVin(device.getVin());
			}

			dlv.add(dv);

		}
		result.setCode("0");
		result.setData(dlv);
		result.setMsg("请求成功");
		;
		return result;

	}

}
