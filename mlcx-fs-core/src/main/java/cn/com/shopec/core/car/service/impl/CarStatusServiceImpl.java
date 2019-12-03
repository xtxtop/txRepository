package cn.com.shopec.core.car.service.impl;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import cn.com.shopec.common.Operator;
import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.common.constants.Constant;
import cn.com.shopec.common.utils.ECCalculateUtils;
import cn.com.shopec.common.utils.ECDateUtils;
import cn.com.shopec.common.utils.ECGeoCoordinateTransformUtil;
import cn.com.shopec.common.utils.ECMd5Utils;
import cn.com.shopec.common.utils.HttpURLRequestUtil;
import cn.com.shopec.core.car.common.CarConstant;
import cn.com.shopec.core.car.dao.CarDao;
import cn.com.shopec.core.car.dao.CarStatusDao;
import cn.com.shopec.core.car.model.Car;
import cn.com.shopec.core.car.model.CarRecord;
import cn.com.shopec.core.car.model.CarStatus;
import cn.com.shopec.core.car.service.CarRecordService;
import cn.com.shopec.core.car.service.CarStatusService;
import cn.com.shopec.core.car.service.CarTripService;
import cn.com.shopec.core.car.vo.CarWkVo;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.device.common.DeviceConstant;
import cn.com.shopec.core.device.dao.DeviceDao;
import cn.com.shopec.core.device.model.Device;
import cn.com.shopec.core.device.service.DeviceService;
import cn.com.shopec.core.franchisee.model.Franchisee;
import cn.com.shopec.core.franchisee.model.FranchiseeProfit;
import cn.com.shopec.core.franchisee.service.FranchiseeProfitService;
import cn.com.shopec.core.franchisee.service.FranchiseeService;
import cn.com.shopec.core.map.service.BaiduGeocoderApiService;
import cn.com.shopec.core.marketing.model.CarRedPacket;
import cn.com.shopec.core.marketing.model.PricingRule;
import cn.com.shopec.core.marketing.model.PricingRuleCustomizedDate;
import cn.com.shopec.core.marketing.service.CarRedPacketService;
import cn.com.shopec.core.marketing.service.PricingPackageService;
import cn.com.shopec.core.marketing.service.PricingRuleCustomizedDateService;
import cn.com.shopec.core.marketing.service.PricingRuleService;
import cn.com.shopec.core.member.dao.MemberDao;
import cn.com.shopec.core.member.model.Member;
import cn.com.shopec.core.monitor.dao.WarningDao;
import cn.com.shopec.core.monitor.model.CarMonitor;
import cn.com.shopec.core.monitor.model.Warning;
import cn.com.shopec.core.monitor.service.WarningService;
import cn.com.shopec.core.order.common.OrderConstant;
import cn.com.shopec.core.order.dao.OrderDao;
import cn.com.shopec.core.order.model.Order;
import cn.com.shopec.core.order.model.OrderMileage;
import cn.com.shopec.core.order.service.OrderMileageService;
import cn.com.shopec.core.order.service.OrderService;
import cn.com.shopec.core.order.service.PricingPackOrderService;
import cn.com.shopec.core.resource.dao.ParkDao;
import cn.com.shopec.core.resource.model.Park;
import cn.com.shopec.core.resource.service.ParkService;
import cn.com.shopec.core.resource.service.ParkStatusService;
import cn.com.shopec.core.scheduling.dao.WorkerOrderDao;
import cn.com.shopec.core.scheduling.model.WorkerOrder;
import cn.com.shopec.core.search.service.BusinessIndexService;
import cn.com.shopec.core.system.model.SysParam;
import cn.com.shopec.core.system.service.DataDictItemService;
import cn.com.shopec.core.system.service.SysParamService;

/**
 * CarStatus 服务实现类
 */
@Service
public class CarStatusServiceImpl implements CarStatusService {

	private static final Log log = LogFactory.getLog(CarStatusServiceImpl.class);

	@Resource
	private CarStatusDao carStatusDao;

	@Resource
	private OrderDao orderDao;

	@Resource
	private CarDao carDao;

	@Resource
	private OrderService orderService;

	@Resource
	private DeviceDao deviceDao;

	@Resource
	private PricingRuleService pricingRuleService;

	@Resource
	private PricingPackOrderService pricingPackOrderService;

	@Resource
	private ParkDao parkDao;

	@Resource
	private CarRecordService carRecordService;

	@Resource
	private DataDictItemService dataDictItemService;

	@Resource
	private MemberDao memberDao;

	@Resource
	private ParkStatusService parkStatusService;

	@Resource
	private ParkService parkService;

	@Resource
	private BaiduGeocoderApiService baiduGeocoderApiService;

	@Resource
	private SysParamService sysParamService;

	@Resource
	private CarTripService carTripService;

	@Resource
	private WorkerOrderDao workerOrderDao;

	@Resource
	private WarningDao warningDao;

	@Resource
	private WarningService warningService;
	@Resource
	private OrderMileageService orderMileageService;
	@Resource
	private PricingRuleCustomizedDateService pricingRuleCustomizedDateService;
	@Resource
	private BusinessIndexService businessIndexService;
	@Resource
	private FranchiseeProfitService franchiseeProfitService;
	@Resource
	private FranchiseeService franchiseeService;
	@Resource
	private CarRedPacketService carRedPacketService;
	@Resource
	private PricingPackageService pricingPackageService;
	@Resource
	private DeviceService deviceService;

	/**
	 * 根据查询条件，查询并返回CarStatus的列表
	 * 
	 * @param q
	 *            查询条件
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<CarStatus> getCarStatusList(Query q) {
		List<CarStatus> list = null;
		try {
			// 直接调用Dao方法进行查询
			list = carStatusDao.queryAll(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		// 如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<CarStatus>(0) : list;
		return list;
	}

	@Transactional(propagation = Propagation.SUPPORTS)
	public List<CarStatus> getCarStatusListForCarMonitor(Query q) {
		List<CarStatus> list = null;
		try {
			// 直接调用Dao方法进行查询
			list = carStatusDao.getCarStatusByUseStatus(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		// 如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<CarStatus>(0) : list;
		return list;
	}

	/**
	 * 根据查询条件，分页查询并返回CarStatus的分页结果
	 * 
	 * @param q
	 *            查询条件
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public PageFinder<CarStatus> getCarStatusPagedList(Query q) {
		PageFinder<CarStatus> page = new PageFinder<CarStatus>();

		List<CarStatus> list = null;
		long rowCount = 0L;

		try {
			// 调用dao查询满足条件的分页数据
			list = carStatusDao.pageList(q);
			// 调用dao统计满足条件的记录总数
			rowCount = carStatusDao.count(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		// 如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<CarStatus>(0) : list;

		// 将分页数据和记录总数设置到分页结果对象中
		page.setData(list);
		page.setRowCount(rowCount);

		return page;
	}

	/**
	 * 根据查询条件，分页查询并返回CarStatus的分页结果
	 * 
	 * @param q
	 *            查询条件
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public PageFinder<CarStatus> getCarStatusPageList(Query q, Integer roleAdminTag) {
		PageFinder<CarStatus> page = new PageFinder<CarStatus>();
		List<CarStatus> list = null;
		long rowCount = 0L;
		try {
			long onlineTimeThreshol = getOnlineTimeThreshols();
			// Calendar calendar = Calendar.getInstance();
			// calendar.add(Calendar.MINUTE, -(int) (onlineTimeThreshol/60000));
			// Date dt= calendar.getTime();
			// if(q.getQ() != null){
			// Device de= (Device)q.getQ();
			// if(de.getIsOnline() != null && !"".equals(de.getIsOnline()) &&
			// de.getIsOnline()==1){
			// de.setLastReportingTimeStart(dt);
			// }
			// if(de.getIsOnline() != null && !"".equals(de.getIsOnline()) &&
			// de.getIsOnline()==2){
			// de.setLastReportingTimeEnd(dt);
			// }
			// }
			list = carStatusDao.carStatusPageList(q);
			for (CarStatus carStatus : list) {
				carStatus.setRoleAdminTag(roleAdminTag);
				if (carStatus.getDeviceSn() != null && !"".equals(carStatus.getDeviceSn())) {
					Device d= deviceService.getDeviceByCarNo(carStatus.getCarNo());
					if (d != null && d.getLastReportingTime() != null) {
						long timeDifference = new Date().getTime() - d.getLastReportingTime().getTime();
						if (onlineTimeThreshol >= timeDifference) {
							carStatus.setIsOnline(1);// 在线
						} else {
							carStatus.setIsOnline(2);// 不在线
						}
					}
				}

				// Car car = carDao.get(carStatus.getCarNo());
				// if (car != null) {
				// carStatus.setCityName(car.getCityName());
				// carStatus.setLeaseType(car.getLeaseType());
				// carStatus.setCarOwnerName(car.getCarOwnerName());
				// carStatus.setOnlineStatus(car.getOnlineStatus());
				// if (carStatus.getLocationParkNo() != null &&
				// !"".equals(carStatus.getLocationParkNo())) {
				// Park park =
				// parkService.getPark(carStatus.getLocationParkNo());
				// carStatus.setPosition(park.getAddrStreet());
				// } else {
				// GeocoderParam geocoder = new GeocoderParam();
				// geocoder.setCoordtype("wgs84ll");
				// geocoder.setLocation(carStatus.getLatitude() + "," +
				// carStatus.getLongitude());
				// ResultInfo<GeocoderResultInfo> res =
				// baiduGeocoderApiService.geocoder(geocoder);
				// if (res.getCode().equals("1")) {
				// GeocoderResultInfo resultInfo = res.getData();
				// if (resultInfo.getStatus() == 0) {
				// carStatus.setPosition(resultInfo.getResult().getFormatted_address());
				// }
				// }
				// }
				// }
			}
			// 调用dao统计满足条件的记录总数
			rowCount = carStatusDao.carStatusCount(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		// 如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<CarStatus>(0) : list;

		// 将分页数据和记录总数设置到分页结果对象中
		page.setData(list);
		page.setRowCount(rowCount);

		return page;
	}

	public PageFinder<CarStatus> getCarIdleStatusPageList(Query q) {
		PageFinder<CarStatus> page = new PageFinder<CarStatus>();
		List<CarStatus> list = null;
		long rowCount = 0L;
		try {
			list = carStatusDao.carIdleStatusPageList(q);
			// 调用dao统计满足条件的记录总数
			rowCount = carStatusDao.carIdleStatusCount(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		if (null != list && list.size() > 0) {
			for (CarStatus carStatus : list) {
				if (carStatus.getIdleTime() != null) {
					String idleTimeStr = carStatus.getIdleTime();
					Integer idleTime = Integer.parseInt(idleTimeStr);
					Integer hours = idleTime / 60;
					Integer minutes = idleTime % 60;
					if (hours > 0) {
						carStatus.setIdleTime(hours + "小时" + minutes + "分钟");
					} else {
						carStatus.setIdleTime(minutes + "分钟");
					}
				}
			}
		}

		// 如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<CarStatus>(0) : list;

		// 将分页数据和记录总数设置到分页结果对象中
		page.setData(list);
		page.setRowCount(rowCount);

		return page;
	}

	/**
	 * 得到系统参数中的终端设备是否在线的阀值
	 * 
	 * @return
	 */
	private long getOnlineTimeThreshols() {
		String paramValue = sysParamService.getParamValueByParamKey(DeviceConstant.device_online_time_threshol);
		if (paramValue != null && !paramValue.equals("")) {
			try {
				return Integer.parseInt(paramValue) * 60000;// 将获取的分钟值转换为毫秒数
			} catch (NumberFormatException e) {
				log.error(e.getMessage(), e);
			}
		}
		return 120000;// 默认两分钟,即120000毫秒
	}

	/**
	 * 根据主键，查询并返回一个CarStatus对象
	 * 
	 * @param id
	 *            主键id
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public CarStatus getCarStatus(String id) {
		CarStatus obj = null;
		if (id == null || id.length() == 0) { // 传入的主键无效时直接返回null
			log.info(Constant.ERR_MSG_INVALID_ARG + " id = " + id);
			return obj;
		}
		try {
			// 调用dao，根据主键查询
			obj = carStatusDao.get(id);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		return obj;
	}

	/**
	 * 根据主键数组，查询并返回一组CarStatus对象
	 * 
	 * @param ids
	 *            主键数组，数组中的主键值应当无重复值，如有重复值时，则返回的列表长度可能小于传入的数组长度
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<CarStatus> getCarStatusByIds(String[] ids) {
		List<CarStatus> list = null;
		if (ids == null || ids.length == 0) {
			log.info(Constant.ERR_MSG_INVALID_ARG + " ids is null or empty array.");
		} else {
			try {
				// 调用dao，根据主键数组查询
				list = carStatusDao.getByIds(ids);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
		}

		// 如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<CarStatus>(0) : list;

		return list;
	}

	/**
	 * 根据主键，删除特定的CarStatus记录
	 * 
	 * @param id
	 *            主键id
	 * @param operator
	 *            操作人对象
	 * @return
	 */
	@Transactional
	public ResultInfo<CarStatus> delCarStatus(String id, Operator operator) {
		ResultInfo<CarStatus> resultInfo = new ResultInfo<CarStatus>();
		if (id == null || id.length() == 0) { // 传入的主键无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG);
			log.info(Constant.ERR_MSG_INVALID_ARG + " id = " + id);
			return resultInfo;
		}
		try {
			// 调用Dao执行删除，并判断删除语句执行结果
			int count = carStatusDao.delete(id);
			if (count > 0) {
				resultInfo.setCode(Constant.SECCUESS);
			} else {
				resultInfo.setCode(Constant.FAIL);
				resultInfo.setMsg(Constant.ERR_MSG_DATA_NOT_EXISTS);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_EXCEPTION_CATCHED);
		}
		return resultInfo;
	}

	/**
	 * 新增一条CarStatus记录，执行成功后传入对象及返回对象的主键属性值不为null
	 * 
	 * @param carStatus
	 *            新增的CarStatus数据（如果无特殊需求，新增对象的主键值请保留为null）
	 * @param operator
	 *            操作人对象
	 * @return
	 */
	@Transactional
	public ResultInfo<CarStatus> addCarStatus(CarStatus carStatus, Operator operator) {
		ResultInfo<CarStatus> resultInfo = new ResultInfo<CarStatus>();

		if (carStatus == null) { // 传入参数无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG);
			log.info(Constant.ERR_MSG_INVALID_ARG + " carStatus = " + carStatus);
		} else {
			try {
				// 如果传入参数的主键为null，则调用生成主键的方法获取新的主键
				if (carStatus.getCarNo() == null) {
					carStatus.setCarNo(this.generatePK());
				}
				// 如果传入的操作人不为null，则设置新增记录的操作人类型和操作人id
				if (operator != null) {
					carStatus.setOperatorType(operator.getOperatorType());
					carStatus.setOperatorId(operator.getOperatorId());
				}

				// 设置创建时间和更新时间为当前时间
				Date now = new Date();
				carStatus.setCreateTime(now);
				carStatus.setUpdateTime(now);

				// 填充默认值
				this.fillDefaultValues(carStatus);

				// 调用Dao执行插入操作
				carStatusDao.add(carStatus);
				resultInfo.setCode(Constant.SECCUESS);
				resultInfo.setData(carStatus);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				resultInfo.setCode(Constant.FAIL);
				resultInfo.setMsg(Constant.ERR_MSG_EXCEPTION_CATCHED);
			}
		}

		return resultInfo;
	}

	@Transactional
	public ResultInfo<String> updateCarUseStatusByCarNo(CarStatus carStatus, Operator operator) {
		ResultInfo<String> resultInfo = new ResultInfo<String>();

		if (carStatus == null || carStatus.getCarNo() == null) { // 传入参数无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG);
			log.info(Constant.ERR_MSG_INVALID_ARG + " carStatus = " + carStatus);
		} else {
			int count = -1;
			try {
				// 如果传入的操作人不为null，则设置更新记录的操作人类型和操作人id
				if (operator != null) {
					carStatus.setOperatorType(operator.getOperatorType());
					carStatus.setOperatorId(operator.getOperatorId());
				}
				// 设置更新时间为当前时间
				carStatus.setUpdateTime(new Date());

				// 调用Dao执行更新操作，并判断更新语句执行结果
				count = carStatusDao.updateCarUseStatusByCarNo(carStatus);
				if (count > 0) {
					resultInfo.setCode(Constant.SECCUESS);
					// 默认 模式为场站租还车， 1 场站 2车辆 3场站+车
					String returnCar = sysParamService.getByParamKey("RETURN_CAR_OUTSIDE_SWITCH") == null ? "1"
							: sysParamService.getByParamKey("RETURN_CAR_OUTSIDE_SWITCH").getParamValue();

					if ("2".equals(returnCar) || "3".equals(returnCar)) { // 只有2
																			// 3
																			// 时才推
						// 更新到搜索引擎中
						businessIndexService.saveOrUpdateCar(carStatus.getCarNo());
					}
				} else if (count == 0) {
					resultInfo.setCode(OrderConstant.fail_code);
					resultInfo.setMsg(OrderConstant.hasRecord);
				} else {
					resultInfo.setCode(Constant.FAIL);
				}
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
		}

		return resultInfo;
	}

	/**
	 * 根据主键，更新一条CarStatus记录
	 * 
	 * @param carStatus
	 *            更新的CarStatus数据，且其主键不能为空
	 * @param operator
	 *            操作人对象
	 * @return
	 */
	@Transactional
	public ResultInfo<CarStatus> updateCarStatus(CarStatus carStatus, Operator operator) {
		ResultInfo<CarStatus> resultInfo = new ResultInfo<CarStatus>();

		if (carStatus == null || carStatus.getCarNo() == null) { // 传入参数无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG);
			log.info(Constant.ERR_MSG_INVALID_ARG + " carStatus = " + carStatus);
		} else {
			try {
				// 如果传入的操作人不为null，则设置更新记录的操作人类型和操作人id
				if (operator != null) {
					carStatus.setOperatorType(operator.getOperatorType());
					carStatus.setOperatorId(operator.getOperatorId());
				}

				// 设置更新时间为当前时间
				carStatus.setUpdateTime(new Date());
				if (carStatus != null && carStatus.getAuxBatteryVoltage() != null) {
					SysParam sys = sysParamService.getByParamKey("CAR_AUX_BATTERY_VOLTAGE");
					if (sys != null) {
						Double s = Double.parseDouble(sys.getParamValue());
						if (carStatus.getAuxBatteryVoltage() < s) {
							Warning warning = new Warning();
							warning.setCarPlateNo(carStatus.getCarPlateNo());
							warning.setIsClosed(0);
							warning.setWarningSubType("车辆电压过低警告");
							List<Warning> lists = warningService.getWarningList(new Query(warning));
							if (lists != null && lists.size() > 0) {
								long between = (lists.get(0).getUpdateTime().getTime() - new Date().getTime()) / 1000;
								long min = between / 60;
								if (min >= 20) {// 超过
									Warning w = new Warning();
									Date nowTime = new Date();
									w.setCarPlateNo(carStatus.getCarPlateNo());
									w.setWarningContent("车辆电压低于平台配置电压");
									w.setCityId(carStatus.getCityId());
									w.setCityName(carStatus.getCityName());
									w.setWarningLevel(1);
									w.setWarningType("车辆");
									w.setWarningSubType("车辆电压过低警告");
									w.setWarningTime(nowTime);
									w.setUpdateTime(nowTime);
									w.setOperatorType(0);
									operator.setOperatorType(0);
									CarStatus cs = getCarStatus(carStatus.getCarNo());
									if (cs.getLocationParkNo() != null && !"".equals(cs.getLocationParkNo())) {
										w.setParkNo(cs.getLocationParkNo());
										Park park = parkService.getPark(cs.getLocationParkNo());
										w.setParkName(park.getParkName());
									}
									w.setIsClosed(0);
									w.setIsNeedManualClosed(1);
									warningService.addWarning(w, operator);
								}

							}

						}
					}
				}
				// 调用Dao执行更新操作，并判断更新语句执行结果
				// if(carStatus.getMileage() != null ){
				// carStatus.setMileage(carStatus.getMileage());
				// }else{
				// carStatus.setMileage(0d);
				// }
				int count = carStatusDao.update(carStatus);
				if (count > 0) {

					// 默认 模式为场站租还车， 1 场站 2车辆 3场站+车
					String returnCar = sysParamService.getByParamKey("RETURN_CAR_OUTSIDE_SWITCH") == null ? "1"
							: sysParamService.getByParamKey("RETURN_CAR_OUTSIDE_SWITCH").getParamValue();

					if ("2".equals(returnCar) || "3".equals(returnCar)) { // 只有2
																			// 3
																			// 时才推
						// 更新到搜索引擎中
						businessIndexService.saveOrUpdateCar(carStatus.getCarNo());
					}
					resultInfo.setCode(Constant.SECCUESS);
				} else {
					resultInfo.setCode(Constant.FAIL);
				}
				resultInfo.setData(carStatus);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				resultInfo.setCode(Constant.FAIL);
				resultInfo.setMsg(Constant.ERR_MSG_EXCEPTION_CATCHED);
			}
		}

		return resultInfo;
	}

	/**
	 * 生成主键
	 * 
	 * @return
	 */
	public String generatePK() {
		return null;
	}

	/**
	 * 为CarStatus对象设置默认值
	 * 
	 * @param obj
	 */
	public void fillDefaultValues(CarStatus obj) {
		if (obj != null) {
		}
	}

	@Override
	@Transactional
	public ResultInfo<CarStatus> takeCar(String deviceId, Operator operator) {
		// TODO Auto-generated method stub
		ResultInfo<CarStatus> resultInfo = new ResultInfo<CarStatus>();
		CarStatus carStatus = carStatusDao.getByDeviceId(deviceId);
		if (carStatus != null) {
			carStatus.setUserageStatus(2);// 车辆使用状态（2：订单中）
			Order order = orderDao.getOrderByCarNo(carStatus.getCarNo());
			if (order != null) {
				order.setOrderStatus(2);// 订单状态（2：计费）
				order.setStartBillingTime(new Date());
				orderService.updateOrder(order, operator);
			}
			Car car = carDao.get(carStatus.getCarNo());
			// 取车后，在用车记录表中添加一条用车记录
			CarRecord cr = new CarRecord();
			cr.setCarBrand(car.getCarBrandName());
			cr.setCarBrandId(car.getCarBrandId());
			cr.setCarModel(car.getCarModelName());
			cr.setCarModelId(car.getCarModelId());
			cr.setCarPlateNo(car.getCarPlateNo());
			cr.setCity(car.getCityName());
			cr.setCityId(car.getCityId());
			cr.setDocumentNo(order.getOrderNo());// 单据号（订单号）
			cr.setDriverId(order.getMemberNo());// 用车人id
			cr.setDriverName(order.getMemberName());
			cr.setStartParkNo(order.getStartParkNo());
			cr.setStartTime(new Date());
			Park park = parkDao.get(order.getStartParkNo());
			if (park != null) {
				cr.setStartParkName(park.getParkName());
			}
			cr.setStartPower(carStatus.getPower());
			cr.setUseCarType(1);// 订单用车
			// 判断用车记录中是否存在该单的记录，已存在，则修改。不存在，则添加
			if (carRecordService.getCarRecordByDocumentNo(order.getOrderNo(), 1) != null) {
				cr.setDocumentNo(order.getOrderNo());
				cr.setRecordId(carRecordService.getCarRecordByDocumentNo(order.getOrderNo(), 1).getRecordId());
				carRecordService.updateCarRecord(cr, operator);
			} else {
				carRecordService.addCarRecord(cr, operator);
			}
			CarStatus carStatusUp = new CarStatus();
			carStatusUp.setCarNo(carStatus.getCarNo());
			carStatusUp.setUserageStatus(2);
			updateCarStatus(carStatusUp, operator);
			resultInfo.setData(carStatus);
			resultInfo.setCode("1");
		} else {
			resultInfo.setCode("0");
		}
		return resultInfo;
	}

	@Override
	@Transactional
	public ResultInfo<CarStatus> rentCar(Order order, Member member, String parkNo, Operator operator) {
		ResultInfo<CarStatus> resultInfo = new ResultInfo<CarStatus>();
		synchronized (Member.class) {
			ResultInfo<Order> orderInfo = new ResultInfo<Order>();
			// 这里要做同步判断
			// 租车后，修改车辆状态并生成订单，这里要做判断，判断车辆的状态是否为空闲状态
			CarStatus carStatus = carStatusDao.get(order.getCarNo());
			Car carLine = carDao.get(order.getCarNo());
			if (carStatus != null && carStatus.getUserageStatus() == 0 && carLine != null
					&& carLine.getOnlineStatus().equals(1)) {
				carStatus.setUserageStatus(1);// 使用状态（1：已预约）
				CarStatus carStatusUp = new CarStatus();
				carStatusUp.setCarNo(carStatus.getCarNo());
				carStatusUp.setUpdateTime(new Date());
				carStatusUp.setUserageStatus(1);
				ResultInfo<String> result = updateCarUseStatusByCarNo(carStatusUp, operator);
				String code = result.getCode();
				// code为1表示更新成功
				if ("1".equals(code)) {
					if (member != null) {
						order.setMemberNo(member.getMemberNo());
						order.setMemberName(member.getMemberName());
						order.setMobilePhone(member.getMobilePhone());
						order.setMemberType(member.getMemberType());
					}

					Car car = carDao.get(order.getCarNo());
					if (car != null) {
						order.setCarPlateNo(car.getCarPlateNo());
						order.setCarModelId(car.getCarModelId());
						order.setCarModelName(car.getCarModelName());
						order.setCityId(car.getCityId());
						order.setCityName(car.getCityName());
						order.setCarModelName(car.getCarModelName());
						// 下单时加入计费规则
						if (member != null) {
							PricingRule rule = null;
							if (member.getMemberType().equals(2)) {// 集团会员
								String companyId = member.getCompanyId();// 集团id
								rule = pricingRuleService.getPricingRuleUseByMM(car.getCarBrandName(),
										order.getCarModelName(), companyId);
								if (rule != null) {
									order.setRuleNo(rule.getRuleNo());
								} else {
									rule = pricingRuleService.getPricingRuleUseByMMP(car.getCarBrandName(),
											order.getCarModelName());
									if (rule != null) {
										order.setRuleNo(rule.getRuleNo());
									}
								}
							} else {// 普通会员
								rule = pricingRuleService.getPricingRuleUseByMMP(car.getCarBrandName(),
										order.getCarModelName());
								if (rule != null) {
									order.setRuleNo(rule.getRuleNo());
								}
							}
						}
					}
					order.setOrderAmount(0.0); // 订单总金额
					order.setStrikeBalanceAmount(0.0); // 冲账金额
					order.setDiscountAmount(0.0); // 折扣金额
					order.setPackMinutesDiscount(0); // 套餐抵扣时长
					order.setPackMinutesDiscountAmount(0.0); // 套餐时长抵扣金额
					order.setPayableAmount(0.0); // 应付金额
					Park park = null;
					if (carStatus != null && carStatus.getLongitude() != null && carStatus.getLatitude() != null) {
						order.setStartLongitude(carStatus.getLongitude());
						order.setStartLatitude(carStatus.getLatitude());
						String startAddress = baiduGeocoderApiService.getAddressByGPS(carStatus.getLatitude(),
								carStatus.getLongitude());
						String[] s = startAddress.split("区");
						order.setStartAddress(s[s.length - 1]);
						String startParkNo = parkService.getCurrentParkNoByCarLocation(carStatus.getLongitude(),
								carStatus.getLatitude());// 判断是否场站范围内
						if (startParkNo == null || startParkNo.equals("")) {
							order.setIsTakeInPark(1);// 场站外
						} else {
							order.setIsTakeInPark(0);// 场站内
							park = parkDao.get(startParkNo);
							if (park != null) {
								order.setStartParkNo(park.getParkNo());
								// 这里应该用查找的park里面去找
								// park.getAddrRegion2Name() +
								// park.getAddrRegion3Name()
								// + park.getAddrStreet();
								String location = park.getParkName();
								order.setActualTakeLoacton(location);
							} else if (car != null && car.getLocationParkNo() != null) {
								order.setStartParkNo(car.getLocationParkNo());
								Park park2 = parkDao.get(car.getLocationParkNo());
								// String location = park2.getAddrRegion1Name()
								// +
								// park2.getAddrRegion2Name()
								// + park2.getAddrRegion3Name() +
								// 2.getAddrStreet();
								String location = park2.getParkName();
								order.setActualTakeLoacton(location);
							}
						}
					}
					order.setOrderStatus(1);// 订单状态（1已预约2已计费3已结束4已取消）
					order.setIsDelete(0); // 订单删除状态
					order.setPayStatus(0);// 支付状态（0未支付1已支付）
					order.setOrderDuration(0); // 订单时长(分钟数)
					order.setAppointmentTime(new Date());// 预约时间
					if (order.getRegardlessFranchise() == null) {
						order.setRegardlessFranchise(0d);
					}
					orderInfo = orderService.addOrder(order, operator);
					if (orderInfo.getCode().equals(Constant.SECCUESS)) {
						order = orderInfo.getData();
						// 租车成功之后要修改场站中的信息，所在场站的车辆要减一，空余车位要加一
						resultInfo.setData(carStatus);
						resultInfo.setCode(OrderConstant.success_code);
						// 下单成功修改对应车的红包记录
						CarRedPacket carRedPacket = carRedPacketService
								.getCarRedPacketByCarPlateNo(carLine.getCarPlateNo(), "1");
						if (carRedPacket != null) {
							CarRedPacket carRedPacketForUpdate = new CarRedPacket();
							carRedPacketForUpdate.setCarRedPacketId(carRedPacket.getCarRedPacketId());
							carRedPacketForUpdate.setOrderNo(order.getOrderNo());
							carRedPacketForUpdate.setCarUserageStatus(1);
							carRedPacketForUpdate.setCarRedPacketStatus(2);
							carRedPacketService.updateCarRedPacket(carRedPacketForUpdate, null);
						}
						// 这里写车辆记录
						CarRecord record = new CarRecord();
						if (car != null) {
							record.setCarPlateNo(car.getCarPlateNo()); // 车牌号
							record.setCarModelId(car.getCarModelId()); // 车型id（具体见数据字典表）
							record.setCarModel(car.getCarModelName()); // 车型名称
							record.setCarBrandId(car.getCarBrandId()); // 品牌id
							record.setCarBrand(car.getCarBrandName()); // 品牌名称
							record.setCityId(car.getCityId()); // 城市id
							record.setCity(car.getCityName()); // 城市名称
						}
						record.setUseCarType(1); // 用车类型（1、订单用车，2、调度用车）
						record.setDocumentNo(order.getOrderNo()); // 单据号（用车类型为订单时，是订单号、调度用车时，是调度单号）
						if (member != null) {
							record.setDriverId(member.getMemberNo()); // 用车人id
							record.setDriverName(member.getMemberName()); // 用车人姓名
						}
						if (order.getStartParkNo() != null && !"null".equals(order.getStartParkNo())
								&& order.getStartParkNo().trim().length() > 0) {
							record.setStartParkNo(order.getStartParkNo()); // 起点编号
							park = parkDao.get(order.getStartParkNo());
							record.setStartParkName(park.getParkName()); // 起点名称
						}

						record.setStartTime(new Date()); // 开始时间
						if (carStatus != null && carStatus.getPower() != null) {
							record.setStartPower(carStatus.getPower()); // 开始电量
						} else {
							record.setStartPower(0.0);
						}
						carRecordService.addCarRecord(record, operator);
					} else {
						resultInfo.setCode(Constant.FAIL);
						resultInfo.setMsg("添加订单失败，由于会员信息获取异常");
					}
				} else {// code为0表示车辆已占用
					resultInfo.setCode(Constant.FAIL);
					resultInfo.setMsg(CarConstant.usered_car_msg);
				}
			} else {
				resultInfo.setCode(Constant.FAIL);
				resultInfo.setMsg(CarConstant.available_car_msg);
			}
		}
		return resultInfo;
	}

	@Override
	@Transactional
	public ResultInfo<Order> returnCar(String orderNo, String parkNo, Operator operator) throws ParseException {
		ResultInfo<Order> resultInfo = new ResultInfo<Order>();
		// 还车后，修改车辆状态并修改订单
		Order order = orderDao.get(orderNo);
		order.setOrderStatus(3);// 订单状态（3：已结束）
		CarStatus carStatus = carStatusDao.get(order.getCarNo());
		if (carStatus != null) {
			carStatus.setUserageStatus(0);// 使用状态（0：空闲）
			order.setFinishTime(new Date());
			// 订单结束时设定订单结束时里程
			if (carStatus.getMileage() != null) {
				order.setOrderFinishMileage(carStatus.getMileage());
			} else {
				order.setOrderFinishMileage(0d);
			}
			// 结束结束之前里程的计算
			beforeOrderFinish(order, carStatus);
			order = orderService.orderBalance(order, parkNo).getData();
			// 订单结算结束
			// 还车之后，对场站的操作
			// 车辆状态最终所在的场站即是车辆还车的场站
			// if (carStatus.getLocationParkNo() != null) {
			// order.setTerminalParkNo(carStatus.getLocationParkNo());
			//// ParkStatus ps =
			// parkStatusService.getParkStatus(carStatus.getLocationParkNo());
			//// if (ps != null) {
			//// order.setActualTerminalParkNo(ps.getParkNo());
			//// ps.setFreeParking(ps.getFreeParking() - 1);
			//// ps.setCarNumber(ps.getCarNumber() + 1);
			//// arkStatusService.updateParkStatus(ps, operator);
			//// }
			// }
			order.setTerminalParkNo(parkNo);
			order.setActualTerminalParkNo(parkNo);
			if (parkNo == null || parkNo.equals("")) {
				order.setIsReturnInPark(1);// 场站外
			} else {
				order.setIsReturnInPark(0);// 场站内
			}
			order.setTerminalLongitude(carStatus.getLongitude());
			order.setTerminalLatitude(carStatus.getLatitude());
			String terminalAddress = baiduGeocoderApiService.getAddressByGPS(carStatus.getLatitude(),
					carStatus.getLongitude());
			String[] s = terminalAddress.split("区");
			order.setTerminalAddress(s[s.length - 1]);

			// 设置订单总里程结束
			// 还车之后，对场站操作结束
			orderService.updateOrder(order, operator);

			// 异步将订单数据添加到加盟商分润单中
			synchronized (this) {
				try {
					this.addFranchiseeProfit(order);
				} catch (Exception e) {
					log.error("fly:异步添加订单给加盟商结算单的时候出现了异常");
					e.printStackTrace();
				}
			}

			// 还车后，在用车记录表中修改该订单的用车记录
			CarRecord cr = carRecordService.getCarRecordByDocumentNo(order.getOrderNo(), 1);// 传参：订单编号，用车类型（订单用车）
			if (cr != null) {
				if (carStatus.getPower() != null) {
					cr.setFinishPower(carStatus.getPower());
				} else {
					cr.setFinishPower(0.0);
				}

				cr.setFinishTime(new Date());
				cr.setTerminalParkNo(order.getActualTerminalParkNo());
				if (order.getActualTerminalParkNo() != null) {
					Park park = parkDao.get(order.getActualTerminalParkNo());
					if (park != null) {
						cr.setTerminalParkName(park.getParkName());
					}
				}
				if (order.getOrderMileage() != null) {
					cr.setTotalMileage(order.getOrderMileage());// 总里程
				} else {
					cr.setTotalMileage(0.0);// 总里程
				}

			}

			carRecordService.updateCarRecord(cr, operator);
			CarStatus carStatusUp = new CarStatus();
			carStatusUp.setCarNo(carStatus.getCarNo());
			carStatusUp.setUserageStatus(0);
			if (order.getIsReturnInPark().intValue() == 1) {// 如果是场站外还车时
				carStatusUp.setLocationParkNo("");
			} else {
				carStatusUp.setLocationParkNo(parkNo);
			}
			updateCarStatus(carStatusUp, operator);

			resultInfo.setData(order);
			resultInfo.setCode(CarConstant.success_code);
			resultInfo.setMsg("");

		} else {
			resultInfo.setCode(CarConstant.fail_code);
			resultInfo.setMsg(CarConstant.fail_msg);

		}
		return resultInfo;
	}

	/**
	 * 计算订单最后一天时长=订单总时长-之前的时长（避免时长误差）
	 * 
	 * @param order
	 * @param nowDate
	 * @return
	 */
	private int cacaulateLastDayMinuteOfOrder(Order order, Date nowDate) {
		int beforeTotalMinute = 0;
		int lastDayOfMinutes = 0;
		int orderTotalMinutes = ECDateUtils.differMinutes(order.getStartBillingTime(), order.getFinishTime())
				.intValue();
		OrderMileage omTemp = new OrderMileage();
		omTemp.setOrderNo(order.getOrderNo());
		List<OrderMileage> orderMileageList = orderMileageService.getOrderMileageList(new Query(omTemp));
		for (OrderMileage om : orderMileageList) {
			if (om.getMinutes() != null && om.getOrderMileageDate().compareTo(nowDate) < 0) {
				beforeTotalMinute += om.getMinutes();
			}
		}
		lastDayOfMinutes = orderTotalMinutes - beforeTotalMinute;
		return lastDayOfMinutes;
	}

	@Override
	public void beforeOrderFinish(Order order, CarStatus carStatus) throws ParseException {
		PricingRule rule = pricingRuleService.getPricingRule(order.getRuleNo());
		// 判断订单结束时定时任务还未更新前一天的订单里程数据时，做更新处理
		OrderMileage orderMileage = orderMileageService.getNewestOrderMileage(order.getOrderNo());
		if (orderMileage != null) {
			int orderCaculateType = 0;
			try {
				int ruleType = rule.getRuleType();
				if (ruleType == 1 || ruleType == 2) {// 目前只有两种规则
					orderCaculateType = ruleType;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (orderCaculateType == 0) {
				SysParam sysparam1 = sysParamService.getByParamKey("ORDER_CACULATE_TYPE");
				if (sysparam1 != null && sysparam1.getParamValue() != null) {
					orderCaculateType = Integer.parseInt(sysparam1.getParamValue().trim());
				}
			}
			if (orderCaculateType == 1) {
				String timeDivisionPointStr = "";
				// 问题，系统参数设置了之后定时任务时间表达式如何动态变化
				SysParam sysparam = sysParamService.getByParamKey("TIME_DIVISION_POINT");
				if (sysparam != null) {
					timeDivisionPointStr = sysparam.getParamValue().trim();
				}
				if (timeDivisionPointStr.equals("")) {
					timeDivisionPointStr = "0";// 默认为0
				}

				Date currentDateTime = new Date();// 当前时间
				Date nowDate = ECDateUtils.formatDateToDateNew(currentDateTime);// 得到日期格式（YYYY-MM-DD）
				int timeDivisionPoint = Integer.parseInt(timeDivisionPointStr);// 时间分隔点
				if (timeDivisionPoint <= 0 || timeDivisionPoint > 23) {
					timeDivisionPoint = 0;
				}
				// 得到分隔点前一个时间，算出前一天及前一天结束时间
				Calendar beforTime = Calendar.getInstance();
				beforTime.setTime(nowDate);
				beforTime.set(Calendar.HOUR_OF_DAY, timeDivisionPoint);
				beforTime.add(Calendar.SECOND, -1);// 算出前一个时间(结束时间)
				Date beforDayOrderEndMinute = beforTime.getTime();// 算出前一天结束时间
				Date beforDate = ECDateUtils.getDateBefore(nowDate, 1);// 算出前一天

				// 得到分隔点下一个时间，算出后一天及后一天后开始时间
				Calendar nextTime = Calendar.getInstance();
				nextTime.setTime(nowDate);
				nextTime.set(Calendar.HOUR_OF_DAY, timeDivisionPoint);// 算出下一个时间(开始时间)
				Date nextOrderStartMinute = nextTime.getTime();// 算出后一天开始时间
				Date nextDate = ECDateUtils.getDateBefore(nowDate, 0);// 算出后一天

				// 算出当前是时间分隔点的中的哪一天(前一天或后一天),用于比较数据库数据是哪一个时间点的。
				Date compareDate = nextDate;
				if (currentDateTime.before(nextOrderStartMinute)) {
					compareDate = beforDate;
				}

				int diffDays = ECDateUtils.differDays2(compareDate, orderMileage.getOrderMileageDate()).intValue();
				// 当天数据未添加
				if (diffDays >= 1) {
					// 更新上一天的数据
					updateNowDayOrderMileage(carStatus, order, orderMileage, rule, beforDayOrderEndMinute,
							orderCaculateType);
					// 并添加当天的数据
					addOrderMileage(carStatus, order, orderMileage, rule, nextOrderStartMinute, orderCaculateType);
				} else {
					// 更新当天的数据
					updateNowDayOrderMileage(carStatus, order, orderMileage, rule, order.getFinishTime(),
							orderCaculateType);
				}
			} else if (orderCaculateType == 2) {
				// 当前时间
				Date nowTime = ECDateUtils.getCurrentDateTime();
				// 上一天开始时间
				Date lastDayOrderStartMinute = orderMileage.getOrderStartMinute();
				int diffDays = ECDateUtils.differDays2(nowTime, lastDayOrderStartMinute).intValue();
				// 上一天的更新时间与当前时间不属于同一天则更新上一天数据
				if (diffDays >= 1) {
					// 更新上一天的数据
					// 上一天结束时间
					Date lastDayOrderEndMinute = ECDateUtils.getDateAfter(lastDayOrderStartMinute, 1);
					// 一天的总金额为null,说明上一天数据还未更新(现在定时任务没有更新总金额)
					Date orderEndMinue = orderMileage.getOrderEndMinute();
					if (null == orderEndMinue || null == orderMileage.getOrderAmountOfDay()
							|| orderEndMinue.before(lastDayOrderEndMinute)) {
						updateNowDayOrderMileage(carStatus, order, orderMileage, rule, lastDayOrderEndMinute,
								orderCaculateType);
					}
					// 并添加当天的数据
					addOrderMileage(carStatus, order, orderMileage, rule, lastDayOrderEndMinute, orderCaculateType);
				} else {
					// 更新当天的数据
					updateNowDayOrderMileage(carStatus, order, orderMileage, rule, order.getFinishTime(),
							orderCaculateType);
				}
			}
		}
	}

	private void addOrderMileage(CarStatus carStatus, Order order, OrderMileage orderMileage, PricingRule rule,
			Date orderStartMinute, int orderCaculateType) {
		// 添加当天的数据
		OrderMileage om1 = new OrderMileage();
		om1.setOrderNo(order.getOrderNo());
		om1.setOrderMileageDate(orderStartMinute);

		om1.setOrderStartMinute(orderStartMinute);// 当天时长开始时间
		om1.setOrderEndMinute(order.getFinishTime());// 当天时长结束时间
		// 计算订单最后一天时长=订单总时长-之前的时长（避免时长误差）
		int lastDayOfMinutes = cacaulateLastDayMinuteOfOrder(order, orderStartMinute);
		om1.setMinutes(lastDayOfMinutes);

		om1.setOrderStartMileage(orderMileage.getOrderFinishMileage());// 当天开始里程(为上一天结束里程)
		om1.setOrderFinishMileage(carStatus.getMileage());// 当天结束里程
		om1.setMileage(ECCalculateUtils.sub(om1.getOrderFinishMileage(), om1.getOrderStartMileage()));
		if (orderCaculateType == 1) {
			// 确定当天是周几
			int dayOfWeek = ECDateUtils.getDayOfWeek(orderStartMinute);
			// 查询自定义日期
			PricingRuleCustomizedDate pricingRuleCustomizedDate = pricingRuleCustomizedDateService
					.getPricingRuleCustomizedDate(order.getRuleNo(), orderStartMinute);
			if (pricingRuleCustomizedDate != null) {// 自定义日期时间金额和里程金额计算
				om1.setMinutesAmount(
						ECCalculateUtils.mul(om1.getMinutes(), pricingRuleCustomizedDate.getPriceOfMinuteCustomized()));
				om1.setMileageAmount(
						ECCalculateUtils.mul(om1.getMileage(), pricingRuleCustomizedDate.getPriceOfKmCustomized()));
			} else if (dayOfWeek == Calendar.SATURDAY || dayOfWeek == Calendar.SUNDAY) {// 周末金额和里程金额计算
				// 判断周末计价是否有值，没有按平日计算
				if (rule.getPriceOfMinuteOrdinary() != null && rule.getPriceOfKmOrdinary() != null) {
					om1.setMinutesAmount(ECCalculateUtils.mul(om1.getMinutes(), rule.getPriceOfMinuteOrdinary()));
					om1.setMileageAmount(ECCalculateUtils.mul(om1.getMileage(), rule.getPriceOfKmOrdinary()));
				} else {
					om1.setMinutesAmount(ECCalculateUtils.mul(om1.getMinutes(), rule.getPriceOfMinute()));
					om1.setMileageAmount(ECCalculateUtils.mul(om1.getMileage(), rule.getPriceOfKm()));
				}
			} else {// 工作日金额和里程金额计算
				om1.setMinutesAmount(ECCalculateUtils.mul(om1.getMinutes(), rule.getPriceOfMinute()));
				om1.setMileageAmount(ECCalculateUtils.mul(om1.getMileage(), rule.getPriceOfKm()));
			}
		} else {
			om1.setMinutesAmount(ECCalculateUtils.mul(om1.getMinutes(), rule.getPriceOfMinute()));
			om1.setMileageAmount(ECCalculateUtils.mul(om1.getMileage(), rule.getPriceOfKm()));
		}
		om1.setMinutesAmount(ECCalculateUtils.round(om1.getMinutesAmount(), 2));
		om1.setMileageAmount(ECCalculateUtils.round(om1.getMileageAmount(), 2));
		om1.setOrderAmountOfDay(ECCalculateUtils.add(om1.getMileageAmount(), om1.getMinutesAmount()));
		om1.setOrderAmountOfDay(ECCalculateUtils.round(om1.getOrderAmountOfDay(), 2));
		orderMileageService.addOrderMileage(om1, null);
	}

	// 更新指定日期的数据
	private void updateNowDayOrderMileage(CarStatus carStatus, Order o, OrderMileage orderMileage, PricingRule rule,
			Date orderEndMinute, int orderCaculateType) {
		OrderMileage om = new OrderMileage();
		om.setOrderNo(o.getOrderNo());
		om.setOrderMileageDate(orderMileage.getOrderMileageDate());

		om.setOrderEndMinute(orderEndMinute);
		om.setMinutes(ECDateUtils.differMinutes(om.getOrderEndMinute(), orderMileage.getOrderStartMinute()).intValue());

		om.setOrderFinishMileage(carStatus.getMileage());
		om.setMileage(ECCalculateUtils.sub(om.getOrderFinishMileage(), orderMileage.getOrderStartMileage()));
		if (orderCaculateType == 1) {
			// 确定orderMileage.getOrderMileageDate()是周几
			int dayOfWeek = ECDateUtils.getDayOfWeek(orderMileage.getOrderMileageDate());
			// 查询自定义日期
			PricingRuleCustomizedDate pricingRuleCustomizedDate = pricingRuleCustomizedDateService
					.getPricingRuleCustomizedDate(o.getRuleNo(), orderMileage.getOrderMileageDate());
			if (pricingRuleCustomizedDate != null) {// 自定义日期时间金额和里程金额计算
				om.setMinutesAmount(
						ECCalculateUtils.mul(om.getMinutes(), pricingRuleCustomizedDate.getPriceOfMinuteCustomized()));
				om.setMileageAmount(
						ECCalculateUtils.mul(om.getMileage(), pricingRuleCustomizedDate.getPriceOfKmCustomized()));
			} else if (dayOfWeek == Calendar.SATURDAY || dayOfWeek == Calendar.SUNDAY) {// 周末金额和里程金额计算
				// 判断周末计价是否有值，没有按平日计算
				if (rule.getPriceOfMinuteOrdinary() != null && rule.getPriceOfKmOrdinary() != null) {
					om.setMinutesAmount(ECCalculateUtils.mul(om.getMinutes(), rule.getPriceOfMinuteOrdinary()));
					om.setMileageAmount(ECCalculateUtils.mul(om.getMileage(), rule.getPriceOfKmOrdinary()));
				} else {
					om.setMinutesAmount(ECCalculateUtils.mul(om.getMinutes(), rule.getPriceOfMinute()));
					om.setMileageAmount(ECCalculateUtils.mul(om.getMileage(), rule.getPriceOfKm()));
				}
			} else {// 工作日金额和里程金额计算
				om.setMinutesAmount(ECCalculateUtils.mul(om.getMinutes(), rule.getPriceOfMinute()));
				om.setMileageAmount(ECCalculateUtils.mul(om.getMileage(), rule.getPriceOfKm()));
			}
		} else {
			om.setMinutesAmount(ECCalculateUtils.mul(om.getMinutes(), rule.getPriceOfMinute()));
			om.setMileageAmount(ECCalculateUtils.mul(om.getMileage(), rule.getPriceOfKm()));
		}
		om.setMinutesAmount(ECCalculateUtils.round(om.getMinutesAmount(), 2));
		om.setMileageAmount(ECCalculateUtils.round(om.getMileageAmount(), 2));
		om.setOrderAmountOfDay(ECCalculateUtils.add(om.getMileageAmount(), om.getMinutesAmount()));
		om.setOrderAmountOfDay(ECCalculateUtils.round(om.getOrderAmountOfDay(), 2));
		orderMileageService.updateOrderMileage(om, null);
	}

	@Override
	public String openDoor(String deviceSn) throws Exception {
		String unlock = sysParamService.getByParamKey("MGT_UNLOCKCARDOOR").getParamValue();
		String s = deviceSn + "@@" + "UJ_APP_DN_KEY";
		String signedDeviceSn = ECMd5Utils.hash(s);
		String param = "ds=" + signedDeviceSn;
		String res = HttpURLRequestUtil.doMsgPost(unlock, param);
		if(res != null) {
			res = new String(res.getBytes(),"UTF-8");
		}
		
		return res;
	}

	@Override
	public String closeDoor(String deviceSn) throws Exception {
		String lock = sysParamService.getByParamKey("MGT_LOCKCARDOOR").getParamValue();
		String s = deviceSn + "@@" + "UJ_APP_DN_KEY";
		String signedDeviceSn = ECMd5Utils.hash(s);
		String param = "ds=" + signedDeviceSn;
		String res = HttpURLRequestUtil.doMsgPost(lock, param);
		if(res != null) {
			res = new String(res.getBytes(),"UTF-8");
		}
		
		return res;
	}

	@Override
	public String findCar(String deviceSn) throws Exception {
		String findcar = sysParamService.getByParamKey("MGT_FINDCAR").getParamValue();
		String s = deviceSn + "@@" + "UJ_APP_DN_KEY";
		String signedDeviceSn = ECMd5Utils.hash(s);
		String param = "ds=" + signedDeviceSn;
		String res = HttpURLRequestUtil.doMsgPost(findcar, param);
		if(res != null) {
			res = new String(res.getBytes(),"UTF-8");
		}
		
		return res;
	}

	@Override
	public String controlPower(String deviceSn, String cmdValue) throws Exception {
		String controlPower = sysParamService.getByParamKey("MGT_CONTROLPOWER").getParamValue();
		if (cmdValue.equals("0")) {
			controlPower = controlPower.replace("2A", "2B");
		}
		String s = deviceSn + "@@" + "UJ_APP_DN_KEY";
		String signedDeviceSn = ECMd5Utils.hash(s);
		String param = "ds=" + signedDeviceSn;
		String res = HttpURLRequestUtil.doMsgPost(controlPower, param);
		if(res != null) {
			res = new String(res.getBytes(),"UTF-8");
		}
		
		return res;
	}

	@Override
	public String setTerminalLeasingMode(String deviceSn, String cmdValue) throws Exception {
		String url = null;
		if("1".equals(cmdValue)) {
			url = sysParamService.getByParamKey("MGT_SET_LEASING_MODE").getParamValue();
		} else if("0".equals(cmdValue)) {
			url = sysParamService.getByParamKey("MGT_SET_NOT_LEASING_MODE").getParamValue();
		}
		if(url == null){
			return null;
		}
		String s = deviceSn + "@@" + "UJ_APP_DN_KEY";
		String signedDeviceSn = ECMd5Utils.hash(s);
		String param = "ds=" + signedDeviceSn;
		String res = HttpURLRequestUtil.doMsgPost(url, param);
		if(res != null) {
			res = new String(res.getBytes(),"UTF-8");
		}
		
		return res;
	}

	@Override
	public String setKeyEnableMode(String deviceSn, String cmdValue) throws Exception {
		String url = null;
		if("1".equals(cmdValue)) {
			url = sysParamService.getByParamKey("MGT_SET_KEY_ENABLE").getParamValue();
		} else if("0".equals(cmdValue)) {
			url = sysParamService.getByParamKey("MGT_SET_KEY_UNABLE").getParamValue();
		}
		if(url == null){
			return null;
		}
		String s = deviceSn + "@@" + "UJ_APP_DN_KEY";
		String signedDeviceSn = ECMd5Utils.hash(s);
		String param = "ds=" + signedDeviceSn;
		String res = HttpURLRequestUtil.doMsgPost(url, param);
		if(res != null) {
			res = new String(res.getBytes(),"UTF-8");
		}
		
		return res;
	}
	
	@Override
	public String userCarSendCmd(String deviceSn, String memberNo, String workerNo, String type) throws Exception {
		String userCar = sysParamService.getByParamKey("APP_CONTROLUSECAR").getParamValue();
		String s = deviceSn + "@@" + "UJ_APP_DN_KEY";
		String signedDeviceSn = ECMd5Utils.hash(s);
		String param = "ds=" + signedDeviceSn;
		String res = HttpURLRequestUtil.doMsgPost(userCar, param);
		if(res != null) {
			res = new String(res.getBytes(),"UTF-8");
		}
		
		return res;
	}

	@Override
	public String returnCarSendCmd(String deviceSn, String memberNo, String workerNo, String type) throws Exception {
		String returnCar = sysParamService.getByParamKey("APP_CONTROLRETURNCAR").getParamValue();
		String s = deviceSn + "@@" + "UJ_APP_DN_KEY";
		String signedDeviceSn = ECMd5Utils.hash(s);
		String param = "ds=" + signedDeviceSn;
		String res = HttpURLRequestUtil.doMsgPost(returnCar, param);
		if(res != null) {
			res = new String(res.getBytes(),"UTF-8");
		}
		
		return res;
	}

	/**
	 * 重启设备接口
	 */
	@Override
	public String restartDevice(String deviceSn) throws Exception {
		String restartDevice = sysParamService.getByParamKey("MGT_RESTARTDEVICE").getParamValue();
		String s = deviceSn + "@@" + "UJ_APP_DN_KEY";
		String signedDeviceSn = ECMd5Utils.hash(s);
		String param = "ds=" + signedDeviceSn;
		String res = HttpURLRequestUtil.doMsgPost(restartDevice, param);
		if(res != null) {
			res = new String(res.getBytes(),"UTF-8");
		}
		
		return res;
	}
	
	/**
	 * 重置终端设备参数
	 * @throws IOException 
	 */
	@Override
	public String restartDeviceParameter(String deviceSn,String param) throws IOException {
		String restartDevice = sysParamService.getByParamKey("MGT_DEVICE_PARAMETER").getParamValue();
		String s = deviceSn + "@@" + "UJ_APP_DN_KEY";
		String signedDeviceSn = ECMd5Utils.hash(s);
		String params = "ds=" + signedDeviceSn+"param=" + param;
		String res = HttpURLRequestUtil.doMsgPost(restartDevice, params);
		if(res != null) {
			res = new String(res.getBytes(),"UTF-8");
		}
		return res;
	}
	
	
	/**
	 * 重置终端设备参数
	 * @throws IOException 
	 */
	@Override
	public String getDeviceParam(String deviceSn) throws IOException {
		String restartDevice = sysParamService.getByParamKey("DEVICE_PARAM").getParamValue();
		String s = deviceSn + "@@" + "UJ_APP_DN_KEY";
		String signedDeviceSn = ECMd5Utils.hash(s);
		String params = "ds=" + signedDeviceSn;
		String res = HttpURLRequestUtil.doMsgPost(restartDevice, params);
		if(res != null) {
			res = new String(res.getBytes(),"UTF-8");
		}
		return res;
	}
	
	
	
	/**
	 * 设备升级
	 * @throws IOException 
	 */
	@Override
	public String deviceUpgrade(String deviceSn) throws IOException {
		String deviceUpgrade = sysParamService.getByParamKey("CONTROL_DEVICE_UPGRADE").getParamValue();
		String s = deviceSn + "@@" + "UJ_APP_DN_KEY";
		String signedDeviceSn = ECMd5Utils.hash(s);
		String param = "ds=" + signedDeviceSn;
		String res = HttpURLRequestUtil.doMsgPost(deviceUpgrade, param);
		if(res != null) {
			res = new String(res.getBytes(),"UTF-8");
		}
		
		return res;
	
	}
	

	/**
	 * 重启蓝牙接口
	 */
	@Override
	public String restartDeviceBluetooth(String deviceSn) throws Exception {
		String restartDeviceBluetooth = sysParamService.getByParamKey("MGT_RESDVICEBLUETOOTH").getParamValue();
		String s = deviceSn + "@@" + "UJ_APP_DN_KEY";
		String signedDeviceSn = ECMd5Utils.hash(s);
		String param = "ds=" + signedDeviceSn;
		return HttpURLRequestUtil.doMsgPost(restartDeviceBluetooth, param);
	}

	/**
	 * 激活/禁用动力开关
	 * 
	 */
	@Override
	public String enablePowerCtrl(String deviceSn, String enable) throws Exception {
		String api = sysParamService.getByParamKey("MGT_SENDRAWCMD").getParamValue();
		String msg = "AT%2BSCPSA=" + enable; // 原报文命令为
												// AT+SCPSA，考虑到url的encoder，这里采用%2B
		String url = api + "?deviceSn=" + deviceSn + "&msg=" + msg;
		return HttpURLRequestUtil.doMsgGet(url);
	}

	@Override
	public List<CarStatus> getCarSpaceShortage(Query q) {
		List<CarStatus> list = null;
		try {
			// 直接调用Dao方法进行查询
			list = carStatusDao.getCarSpaceShortage(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		// 如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<CarStatus>(0) : list;
		return list;
	}

	@Override
	public int count(Query q) {
		int count = 0;
		try {
			count = ECCalculateUtils.convertsToInt(carStatusDao.count(q));
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return count;
	}

	@Override
	public List<CarStatus> getCarNoPark(String longitude, String latitude, Double distance) {
		// TODO Auto-generated method stub
		List<CarStatus> list = null;
		try {
			// 直接调用Dao方法进行查询
			list = carStatusDao.getCarNoPark(longitude, latitude, distance);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		// 如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<CarStatus>(0) : list;
		return list;
	}

	@Override
	public Long countParkStatus(Query q) {
		return carStatusDao.countParkStatus(q);
	}

	// 已预约
	@Override
	public Long countParkStatusUserage(Query q) {
		// TODO Auto-generated method stub
		return carStatusDao.countParkStatusUserage(q);
	}

	/**
	 * 监控管理查询全部车辆报警信息
	 * 
	 * @param menu
	 *            代表菜单类型 1 闲置超时报警 2 非订单用车
	 * @param type
	 *            代表闲置超时时间
	 * @return
	 */
	@Override
	public List<CarMonitor> getRealTimeAlarmList(Integer menu, Integer type) {
		// TODO Auto-generated method stub
		long start = System.currentTimeMillis();
		log.error("the request coming and the type param is " + type + "----------");
		// 定义返回结果集
		List<CarMonitor> results = new ArrayList<CarMonitor>();

		List<CarMonitor> result = new ArrayList<CarMonitor>();

		// 正常查车结果
		List<CarMonitor> list = new ArrayList<CarMonitor>();
		try {
			if (1 == menu) {
				List<CarStatus> freeList = getCarStatusListfree(24, 48, 72);// 闲置超时报警用车
				log.error("the freeList info -------" + freeList.size());
				for (CarStatus cs : freeList) {
					String carPlateNo = cs.getCarPlateNo();
					Warning ww = warningDao.getWarningByCarNo(carPlateNo);
					if (null != ww && ww.getIsClosed() == 0) {
						cs.setWarningNo(ww.getWarningNo());
					}
					if (cs.getLongitude() != null && cs.getLatitude() != null) {
						double[] bdCoord = ECGeoCoordinateTransformUtil.wgs84tobd09(cs.getLongitude(),
								cs.getLatitude());
						cs.setLongitude(bdCoord[0]);// 经度（百度坐标）
						cs.setLatitude(bdCoord[1]);// 纬度（百度坐标）
					}
				}
				if (freeList != null && freeList.size() > 0) {
					List<CarMonitor> list2 = addList(freeList, null);
					if (list2 != null && list2.size() > 0) {
						list.addAll(list2);
					}
				}
				long end = System.currentTimeMillis();
				System.out.println("free car end-start***：" + (end - start) + "ms");
				results = list;
				if (type != null) {
					List<CarMonitor> freeCarList = new ArrayList<CarMonitor>();
					List<CarStatus> freeCarList1 = new ArrayList<CarStatus>();
					for (CarStatus c : freeList) {
						if (c.getRealTimeAlarmType().equals(type)) {
							freeCarList1.add(c);
						}
					}
					if (freeCarList1 != null && freeCarList1.size() > 0) {
						freeCarList = addList(freeCarList1, null);
					}
					long ends = System.currentTimeMillis();
					System.out.println("free car end-start***：" + (ends - start) + "ms");
					results = freeCarList;
				}
			} else if (2 == menu) {
				// List<CarStatus> outOrderList = getCarStatusListOutOrderW();//
				// 非订单车辆（车辆不在订单或调度单中，点火）
				//
				// log.error("the outOrderList info -------" +
				// outOrderList.size());
				// if (outOrderList != null && outOrderList.size() > 0) {
				// List<CarMonitor> list1 = addList(outOrderList, 1);
				// if (list1 != null && list1.size() > 0) {
				// list.addAll(list1);
				// }
				// }
				CarStatus carStatus = new CarStatus();
				carStatus.setUserageStatus(0);
				carStatus.setCarStatus(1);
				List<CarStatus> outOrderList = carStatusDao.queryAll(new Query(carStatus));
				if (outOrderList != null && outOrderList.size() > 0) {
					List<CarMonitor> list1 = addList(outOrderList, 1);
					if (list1 != null && list1.size() > 0) {
						list.addAll(list1);
					}
				}
				long end = System.currentTimeMillis();
				log.error("noorder car time-consuming  end-start***else：" + (end - start) + "ms");
				result = list;
			}
			if (2 != menu) {
				for (Iterator<CarMonitor> it = results.iterator(); it.hasNext();) {
					CarMonitor current = it.next();
					if (current.getCarNo() != null) {
						Order order = orderService.getOrderNewestByCarNo(current.getCarNo());
						if (order != null) {
							current.setOrder(order);
						}
						WorkerOrder workerOrder = workerOrderDao.getWorkerOrderNewestByCarNo(current.getCarNo());
						if (workerOrder != null) {
							current.setWorkerOrder(workerOrder);
						}
					}
					if (null != current.getWarningNo()) {
						result.add(current);
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		log.error("the results info -------" + results.size());

		return result;
	}

	@Override
	public List<CarStatus> getCarStatusListOutOrder() {
		List<CarStatus> list = carStatusDao.getCarStatusListOutOrder();
		for (CarStatus cs : list) {
			Warning ww = warningDao.getWarningByCarNo(cs.getCarPlateNo());
			if (null != ww && ww.getIsClosed() == 0) {
				cs.setWarningNo(ww.getWarningNo());
			}
			if (cs.getLongitude() != null && cs.getLatitude() != null) {
				double[] bdCoord = ECGeoCoordinateTransformUtil.wgs84tobd09(cs.getLongitude(), cs.getLatitude());
				cs.setLongitude(bdCoord[0]);// 经度（百度坐标）
				cs.setLatitude(bdCoord[1]);// 纬度（百度坐标）
			}
		}
		return list;
	}

	@Override
	public List<CarStatus> getCarStatusListOutOrderW() {
		List<CarStatus> list = carStatusDao.getCarStatusListOutOrderW();
		for (CarStatus cs : list) {
			String carPlateNo = cs.getCarPlateNo();
			Warning ww = warningDao.getWarningByCarNo(carPlateNo);
			if (null != ww && ww.getIsClosed() == 0) {
				cs.setWarningNo(ww.getWarningNo());
			}
			if (cs.getLongitude() != null && cs.getLatitude() != null) {
				double[] bdCoord = ECGeoCoordinateTransformUtil.wgs84tobd09(cs.getLongitude(), cs.getLatitude());
				cs.setLongitude(bdCoord[0]);// 经度（百度坐标）
				cs.setLatitude(bdCoord[1]);// 纬度（百度坐标）
			}
		}
		return list;
	}

	public List<CarStatus> getCarStatusListfree(Integer hour1, Integer hour2, Integer hour3) {
		// 查询车辆最后一笔订单和调度单的结束时间距离当前的时间大于24小于48
		CarStatus cs = new CarStatus();
		cs.setUserageStatus(0);
		List<CarStatus> result = new ArrayList<CarStatus>();
		List<CarStatus> listCar = getCarStatusList(new Query(cs));
		for (CarStatus carStatus : listCar) {
			// 判断最近一笔订单
			Order order = orderDao.getLatelyOrderByCarNo(carStatus.getCarNo());
			if (order != null) {
				if ((order.getOrderStatus().equals(3) || order.getOrderStatus().equals(4))
						&& order.getFinishTime() != null) {
					int hour = ECDateUtils.differHours(order.getFinishTime(), new Date()).intValue();
					if (hour1 != null) {
						if (hour2 != null) {
							WorkerOrder wOrder = workerOrderDao.getLatelyWorkerOrderByCarNo(carStatus.getCarNo());
							if (hour >= hour1 && hour < hour2) {// 24
								// 判断最近一笔调度单
								if (wOrder != null) {
									if (wOrder.getWorkOrderStatus().equals(3) && wOrder.getFinishTime() != null) {
										int hourW = ECDateUtils.differHours(wOrder.getFinishTime(), new Date())
												.intValue();
										// 调度闲置时间小于订单闲置时间
										if (hourW <= hour) {
											if (hourW >= hour1 && hourW < hour2) {
												CarStatus cResult = getCarStatus(order.getCarNo());
												cResult.setRealTimeAlarmType(2);
												result.add(cResult);
											}
										} else {
											CarStatus cResult = getCarStatus(order.getCarNo());
											cResult.setRealTimeAlarmType(2);
											result.add(cResult);
										}
									}
								} else {
									CarStatus cResult = getCarStatus(order.getCarNo());
									cResult.setRealTimeAlarmType(2);
									result.add(cResult);
								}
							} else if (hour >= hour2 && hour < hour3) {// 48
								if (wOrder != null) {
									if (wOrder.getWorkOrderStatus().equals(3) && wOrder.getFinishTime() != null) {
										int hourW = ECDateUtils.differHours(wOrder.getFinishTime(), new Date())
												.intValue();
										// 调度闲置时间小于订单闲置时间
										if (hourW <= hour) {
											if (hourW >= hour2 && hourW < hour3) {
												CarStatus cResult = getCarStatus(order.getCarNo());
												cResult.setRealTimeAlarmType(3);
												result.add(cResult);
											}
										} else {
											CarStatus cResult = getCarStatus(order.getCarNo());
											cResult.setRealTimeAlarmType(3);
											result.add(cResult);
										}
									}
								} else {
									CarStatus cResult = getCarStatus(order.getCarNo());
									cResult.setRealTimeAlarmType(3);
									result.add(cResult);
								}
							} else if (hour >= hour3) {// 72
								if (wOrder != null) {
									if (wOrder.getWorkOrderStatus().equals(3) && wOrder.getFinishTime() != null) {
										int hourW = ECDateUtils.differHours(wOrder.getFinishTime(), new Date())
												.intValue();
										// 调度闲置时间小于订单闲置时间
										if (hourW <= hour) {
											if (hourW >= hour3) {
												CarStatus cResult = getCarStatus(order.getCarNo());
												cResult.setRealTimeAlarmType(4);
												result.add(cResult);
											}
										} else {
											CarStatus cResult = getCarStatus(order.getCarNo());
											cResult.setRealTimeAlarmType(4);
											result.add(cResult);
										}
									}
								} else {
									CarStatus cResult = getCarStatus(order.getCarNo());
									cResult.setRealTimeAlarmType(4);
									result.add(cResult);
								}
							}
						}
					}
				}
			}
		}
		for (CarStatus obj : result) {
			if (obj.getLongitude() != null && obj.getLatitude() != null) {
				double[] bdCoord = ECGeoCoordinateTransformUtil.wgs84tobd09(obj.getLongitude(), obj.getLatitude());
				obj.setLongitude(bdCoord[0]);// 经度（百度坐标）
				obj.setLatitude(bdCoord[1]);// 纬度（百度坐标）
			}
		}
		return result;
	}

	private List<CarMonitor> addList(List<CarStatus> list, Integer type) {
		List<CarMonitor> result = new ArrayList<CarMonitor>();
		for (CarStatus cs1 : list) {
			CarMonitor cm = new CarMonitor();
			cm.setAuxBatteryVoltage(cs1.getAuxBatteryVoltage());
			cm.setCarNo(cs1.getCarNo());
			cm.setCarPlateNo(cs1.getCarPlateNo());
			cm.setCarStatus(cs1.getCarStatus());
			cm.setChargeState(cs1.getChargeState());
			cm.setChargingFaultStatus(cs1.getChargingFaultStatus());
			cm.setCityId(cs1.getCityId());
			cm.setLatitude(cs1.getLatitude());
			cm.setLocationParkNo(cs1.getLocationParkNo());
			cm.setLongitude(cs1.getLongitude());
			cm.setMileage(cs1.getMileage());
			cm.setPower(cs1.getPower());
			cm.setRangeMileage(cs1.getRangeMileage());
			cm.setWarningNo(cs1.getWarningNo());
			if (type == null) {
				cm.setRealTimeAlarmType(cs1.getRealTimeAlarmType());
			} else {
				cm.setRealTimeAlarmType(type);
			}
			cm.setSpeed(cs1.getSpeed());
			cm.setUserageStatus(cs1.getUserageStatus());
			cm.setNowDay(ECDateUtils.getCurrentDateTimeAsString());
			Long time = new Date().getTime() - 12 * 60 * 60 * 1000;
			cm.setPreDay(ECDateUtils.formatTime(new Date(time)));
			result.add(cm);
		}
		return result;
	}

	/**
	 * 根据车辆号，取得车辆状态表中，各上报时间字段的值
	 * 
	 * @param carNo
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public CarStatus getCarStatusReportingTime(String carNo) {
		if (carNo == null) {
			return null;
		}
		CarStatus cs = null;
		try {
			cs = carStatusDao.getCarStatusReportingTime(carNo);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		return cs;
	}

	@Override
	public List<CarStatus> getCarStatusPageListForExport(Query q) {
		return carStatusDao.carStatusPageListForExport(q);
	}

	@Override
	public List<CarWkVo> nearbyCar(Query q) {
		List<CarWkVo> list = null;
		try {
			// 直接调用Dao方法进行查询
			list = carStatusDao.nearbyCar(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		// 如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<CarWkVo>(0) : list;
		return list;
	}

	@Override
	public List<CarWkVo> stopCar(Query q) {
		List<CarWkVo> list = null;
		try {
			// 直接调用Dao方法进行查询
			list = carStatusDao.stopCar(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		// 如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<CarWkVo>(0) : list;
		return list;
	}

	@Override
	public Integer countCarBrokenLine() {
		return carStatusDao.countCarBrokenLine();
	}

	@Override
	public Integer countNoOrderUseCar() {
		return carStatusDao.countNoOrderUseCar();
	}

	@Override
	public Integer countCarMinLowPower() {
		String resetBluetoothNameUrl = sysParamService.getByParamKey("CAR_AUX_BATTERY_VOLTAGE").getParamValue();
		return carStatusDao.countCarMinLowPower(resetBluetoothNameUrl);
	}

	@Override
	public Integer getCarBrokenLineByCarNo(String carNo) {
		return carStatusDao.getCarBrokenLineByCarNo(carNo);
	}

	@Override
	public String resetBluetoothName(String deviceSn, String bluetoothName) throws Exception {
		String resetBluetoothNameUrl = sysParamService.getByParamKey("MGT_RESETBLUETOOTHNAME").getParamValue();
//		String d = sysParamService.getByParamKey("CUSTOMER_COMPANY_CODE").getParamValue();

		// 截取车牌号后5位
//		String no = carPlateNo.substring(2, carPlateNo.length());
//
		Device device = deviceDao.getByDeviceSn(deviceSn);
		if (device == null) {
			return null;
		}
//		String value = d + "_" + no + "_";
//		if (StringUtils.isNotBlank(device.getBluetoothName())) {
//			value = device.getBluetoothName();
//		} else {
//			value = d + "_" + no + "_";
//		}

		String s = deviceSn + "@@" + "UJ_APP_DN_KEY";
		String signedDeviceSn = ECMd5Utils.hash(s);

		if (device.getDeviceModel().equals("E6")) {
			String param = "ds=" + signedDeviceSn + "&content=" + bluetoothName;
			return HttpURLRequestUtil.doMsgPost(resetBluetoothNameUrl, param);
		} else if (device.getDeviceModel().equals("02")) {
			String param = "ds=" + signedDeviceSn + "&content=" + bluetoothName;
			return HttpURLRequestUtil.doMsgPost(resetBluetoothNameUrl, param);
		} else if (device.getDeviceModel().equals("04")) {
			String param = "ds=" + signedDeviceSn + "&content=" + bluetoothName;
			return HttpURLRequestUtil.doMsgPost(resetBluetoothNameUrl, param);
		}
		return null;
	}

	@Override
	public List<CarStatus> getLlocationParkNo() {

		return carStatusDao.getLlocationParkNo();
	}

	@Override
	public Integer getDownpowers(Double powers) {
		Integer carDownpower = 0;
		if (carStatusDao.getDownpowers(powers) != null) {
			carDownpower = carStatusDao.getDownpowers(powers);
		}
		return carDownpower;
	}

	private void addFranchiseeProfit(Order order) throws Exception {
		/** 关联新增加盟商收益数据 **/
		Car car = carDao.get(order.getCarNo());
		Park park = parkService.getPark(order.getStartParkNo());
		if (car != null) {
			if (!"".equals(car.getFranchiseeNo()) && car.getFranchiseeNo() != null) {
				// 如果车辆关联加盟商，添加加盟收益数据
				FranchiseeProfit franchiseeProfit = new FranchiseeProfit();
				franchiseeProfit.setFranchiseeProfitNo(franchiseeProfitService.generatePK());
				franchiseeProfit.setFranchiseeNo(car.getFranchiseeNo());
				franchiseeProfit.setOrderNo(order.getOrderNo());
				franchiseeProfit.setOrderAmount(order.getOrderAmount());
				franchiseeProfit.setProfitType(Constant.PROFIT_CAR_TYPE);
				// 查询加盟商信息
				Franchisee franchisee = franchiseeService.getFranchisee(car.getFranchiseeNo());
				if (franchisee != null) {
					franchiseeProfit.setFranchiseeName(franchisee.getFranchiseeName());
					franchiseeProfit.setCarProportion(franchisee.getCarProportion());
					Double carProportion = (franchisee.getCarProportion()) / 100;
					franchiseeProfit.setCarProfit(
							ECCalculateUtils.round((ECCalculateUtils.mul(order.getOrderAmount(), carProportion)), 2));
					franchiseeProfit.setCarOrParkNo(order.getCarPlateNo());
				}
				franchiseeProfit.setCreateTime(order.getPaymentTime());
				if (franchiseeProfit.getCarProportion() == null) {
					franchiseeProfit.setCarProportion(0d);
				}

				if (franchiseeProfit.getParkProportion() == null) {
					franchiseeProfit.setParkProportion(0d);
				}

				if (franchiseeProfit.getCarProfit() == null) {
					franchiseeProfit.setCarProfit(0d);
				}
				if (franchiseeProfit.getParkProfit() == null) {
					franchiseeProfit.setParkProfit(0d);
				}
				franchiseeProfitService.addFranchiseeProfit(franchiseeProfit, null);
			}
		}
		if (park != null) {
			if (!"".equals(park.getFranchiseeNo()) && park.getFranchiseeNo() != null) {
				// 如果关联场站关联加盟商，添加收益数据
				FranchiseeProfit franchiseeProfit = new FranchiseeProfit();
				franchiseeProfit.setFranchiseeProfitNo(franchiseeProfitService.generatePK());
				franchiseeProfit.setFranchiseeNo(park.getFranchiseeNo());
				franchiseeProfit.setOrderNo(order.getOrderNo());
				franchiseeProfit.setOrderAmount(order.getOrderAmount());
				franchiseeProfit.setProfitType(Constant.PROFIT_PARK_TYPE);
				// 查询加盟商信息
				Franchisee franchisee = franchiseeService.getFranchisee(park.getFranchiseeNo());
				if (franchisee != null) {
					franchiseeProfit.setFranchiseeName(franchisee.getFranchiseeName());
					franchiseeProfit.setParkProportion(franchisee.getParkProportion());
					Double carProportion = (franchisee.getParkProportion()) / 100;
					franchiseeProfit.setParkProfit(
							ECCalculateUtils.round((ECCalculateUtils.mul(order.getOrderAmount(), carProportion)), 2));
					franchiseeProfit.setCarOrParkNo(order.getStartParkNo());
				}
				franchiseeProfit.setCreateTime(order.getPaymentTime());
				if (franchiseeProfit.getCarProfit() == null) {
					franchiseeProfit.setCarProfit(0d);
				}
				if (franchiseeProfit.getParkProfit() == null) {
					franchiseeProfit.setParkProfit(0d);
				}
				if (franchiseeProfit.getCarProportion() == null) {
					franchiseeProfit.setCarProportion(0d);
				}

				if (franchiseeProfit.getParkProportion() == null) {
					franchiseeProfit.setParkProportion(0d);
				}
				franchiseeProfitService.addFranchiseeProfit(franchiseeProfit, null);
			}
		}
	}

	@Override
	public List<CarStatus> getCarMonitorList(Query q) {
		List<CarStatus> list = null;
		try {
			// 直接调用Dao方法进行查询
			list = carStatusDao.getCarMonitorList(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		// 如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<CarStatus>(0) : list;
		return list;
	}

}
