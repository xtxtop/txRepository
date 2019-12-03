package cn.com.shopec.core.car.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import cn.com.shopec.common.Operator;
import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.common.constants.Constant;
import cn.com.shopec.common.exception.xls.XlsImportException;
import cn.com.shopec.common.sendmsg.MsgConstant;
import cn.com.shopec.common.sendmsg.SendMessage;
import cn.com.shopec.common.sendmsg.SendMsgCommonInterfaceService;
import cn.com.shopec.common.utils.ECCalculateUtils;
import cn.com.shopec.common.utils.ECGeoCoordinateTransformUtil;
import cn.com.shopec.core.car.common.CarConstant;
import cn.com.shopec.core.car.dao.CarDao;
import cn.com.shopec.core.car.dao.CarOwnerDao;
import cn.com.shopec.core.car.dao.CarStatusDao;
import cn.com.shopec.core.car.model.Car;
import cn.com.shopec.core.car.model.CarAndStatus;
import cn.com.shopec.core.car.model.CarOwner;
import cn.com.shopec.core.car.model.CarStatus;
import cn.com.shopec.core.car.service.CarOwnerService;
import cn.com.shopec.core.car.service.CarService;
import cn.com.shopec.core.car.service.CarStatusService;
import cn.com.shopec.core.car.vo.CarDetailWkVo;
import cn.com.shopec.core.car.vo.CarOnlineVo;
import cn.com.shopec.core.car.vo.CarWkVo;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.car.model.CarBrand;
import cn.com.shopec.core.car.model.CarSeries;
import cn.com.shopec.core.car.service.CarBrandService;
import cn.com.shopec.core.car.service.CarSeriesService;
import cn.com.shopec.core.device.dao.DeviceDao;
import cn.com.shopec.core.device.model.Device;
import cn.com.shopec.core.device.service.DeviceService;
import cn.com.shopec.core.map.model.Column;
import cn.com.shopec.core.map.model.Entity;
import cn.com.shopec.core.map.service.BaiduEntityApiService;
import cn.com.shopec.core.member.dao.MemberDao;
import cn.com.shopec.core.monitor.model.Warning;
import cn.com.shopec.core.monitor.service.WarningService;
import cn.com.shopec.core.order.model.Order;
import cn.com.shopec.core.order.service.OrderService;
import cn.com.shopec.core.resource.common.ParkConstant;
import cn.com.shopec.core.resource.dao.ParkDao;
import cn.com.shopec.core.resource.model.Park;
import cn.com.shopec.core.resource.service.ParkService;
import cn.com.shopec.core.scheduling.dao.WorkerOrderDao;
import cn.com.shopec.core.scheduling.model.Worker;
import cn.com.shopec.core.scheduling.model.WorkerOrder;
import cn.com.shopec.core.scheduling.service.WorkerOrderService;
import cn.com.shopec.core.scheduling.service.WorkerService;
import cn.com.shopec.core.search.service.BusinessIndexService;
import cn.com.shopec.core.system.common.PrimarykeyConstant;
import cn.com.shopec.core.system.model.DataDictItem;
import cn.com.shopec.core.system.model.SysParam;
import cn.com.shopec.core.system.model.SysUser;
import cn.com.shopec.core.system.service.DataDictItemService;
import cn.com.shopec.core.system.service.PrimarykeyService;
import cn.com.shopec.core.system.service.SysParamService;
import cn.com.shopec.core.system.service.SysRegionService;
import cn.com.shopec.core.system.service.SysUserService;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

/**
 * Car 服务实现类
 */
@Service
public class CarServiceImpl implements CarService {

	private static final Log log = LogFactory.getLog(CarServiceImpl.class);

	@Resource
	private CarDao carDao;
	@Resource
	private DeviceDao deviceDao;
	@Resource
	private CarStatusDao carStatusDao;
	@Resource
	public SysRegionService sysRegionService;
	@Resource
	private DataDictItemService dataDictItemService;
	@Resource
	private MemberDao memberDao;
	@Resource
	private CarOwnerDao carOwnerDao;
	@Resource
	private BaiduEntityApiService baiduEntityApiService;
	@Resource
	private ParkDao parkDao;

	@Resource
	private SysParamService sysParamService;
	@Resource
	private CarBrandService carBrandService;
	@Resource
	private CarSeriesService carSeriesService;
	@Resource
	private PrimarykeyService primarykeyService;

	@Resource
	private OrderService orderService;

	@Resource
	private WorkerOrderDao workerOrderDao;

	@Resource
	private WarningService warningService;

	@Resource
	private WorkerOrderService workerOrderService;

	@Resource
	private SendMessage sendMsg;
	@Resource
	private DeviceService deviceService;
	@Resource
	private ParkService parkService;
	@Resource
	public CarOwnerService carOwnerService;
	@Resource
	public CarStatusService carStatusService;
	@Resource
	private BusinessIndexService businessIndexService;
	@Resource
	public SysUserService sysUserService;
	@Resource
	public WorkerService workerService;
	@Resource
	private SendMsgCommonInterfaceService sendMsgCommonInterfaceService;
	@Value("${tpl_carWarning_id}")
	private String tplId;

	/**
	 * 根据查询条件，查询并返回Car的列表
	 * 
	 * @param q
	 *            查询条件
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<Car> getCarList(Query q) {
		List<Car> list = null;
		try {
			// 直接调用Dao方法进行查询
			list = carDao.queryAll(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		// 如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<Car>(0) : list;
		return list;
	}

	/**
	 * 根据查询条件，分页查询并返回Car的分页结果
	 * 
	 * @param q
	 *            查询条件
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public PageFinder<Car> getCarPagedList(Query q) {
		PageFinder<Car> page = new PageFinder<Car>();

		List<Car> list = null;
		long rowCount = 0L;

		try {
			// 调用dao查询满足条件的分页数据
			list = carDao.pageList(q);
			// 调用dao统计满足条件的记录总数
			rowCount = carDao.count(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		// 如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<Car>(0) : list;

		// 将分页数据和记录总数设置到分页结果对象中
		page.setData(list);
		page.setRowCount(rowCount);

		return page;
	}

	/**
	 * @author lj 后台 根据查询条件，分页查询并返回Car的分页结果
	 * @param q
	 *            查询条件
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public PageFinder<Car> getCarPageList(Query q) {
		PageFinder<Car> page = new PageFinder<Car>();

		List<Car> list = null;
		long rowCount = 0L;
		long onlineTimeThreshol = getOnlineTimeThreshol();
		try {
			// 调用dao查询满足条件的分页数据
			list = carDao.carPageList(q);
			for (Car car : list) {
				Device d= deviceService.getDeviceByCarNo(car.getCarNo());
				if (d != null && d.getLastReportingTime() != null) {
					long timeDifference = new Date().getTime() - d.getLastReportingTime().getTime();
					if (onlineTimeThreshol >= timeDifference) {
						car.setIsOnline(1);// 在线
					} else {
						car.setIsOnline(2);// 不在线
					}
				}
			}
			
			
			// 调用dao统计满足条件的记录总数
			rowCount = carDao.carCount(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		// 如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<Car>(0) : list;

		// 将分页数据和记录总数设置到分页结果对象中
		page.setData(list);
		page.setRowCount(rowCount);

		return page;
	}

	/**
	 * 得到车辆状态是否在线的阀值
	 * 
	 * @return
	 */
	public long getOnlineTimeThreshol() {
		String paramValue = sysParamService.getParamValueByParamKey(CarConstant.car_online_time_threshol);
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
	 * 根据主键，查询并返回一个Car对象
	 * 
	 * @param id
	 *            主键id
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public Car getCar(String id) {
		Car obj = null;
		if (id == null || id.length() == 0) { // 传入的主键无效时直接返回null
			log.info(Constant.ERR_MSG_INVALID_ARG + " id = " + id);
			return obj;
		}
		try {
			// 调用dao，根据主键查询
			obj = carDao.get(id);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		return obj;
	}

	/**
	 * @author lj 后台车辆列表详情 根据主键，查询并返回一个Car对象
	 * @param id
	 *            主键id
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public Car toCarView(String id) {
		Car obj = null;
		if (id == null || id.length() == 0) { // 传入的主键无效时直接返回null
			log.info(Constant.ERR_MSG_INVALID_ARG + " id = " + id);
			return obj;
		}
		try {
			// 调用dao，根据主键查询
			obj = carDao.get(id);
			Device device = deviceDao.get(obj.getDeviceId());
			if (device != null) {
				obj.setDeviceStatus(device.getDeviceStatus());
			}

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		return obj;
	}

	/**
	 * 根据主键数组，查询并返回一组Car对象
	 * 
	 * @param ids
	 *            主键数组，数组中的主键值应当无重复值，如有重复值时，则返回的列表长度可能小于传入的数组长度
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<Car> getCarByIds(String[] ids) {
		List<Car> list = null;
		if (ids == null || ids.length == 0) {
			log.info(Constant.ERR_MSG_INVALID_ARG + " ids is null or empty array.");
		} else {
			try {
				// 调用dao，根据主键数组查询
				list = carDao.getByIds(ids);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
		}

		// 如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<Car>(0) : list;

		return list;
	}

	/**
	 * 根据主键，删除特定的Car记录
	 * 
	 * @param id
	 *            主键id
	 * @param operator
	 *            操作人对象
	 * @return
	 */
	@Transactional
	public ResultInfo<Car> delCar(String id, Operator operator) {
		ResultInfo<Car> resultInfo = new ResultInfo<Car>();
		if (id == null || id.length() == 0) { // 传入的主键无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG);
			log.info(Constant.ERR_MSG_INVALID_ARG + " id = " + id);
			return resultInfo;
		}
		try {
			// 调用Dao执行删除，并判断删除语句执行结果
			int count = carDao.delete(id);
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
	 * 新增一条Car记录，执行成功后传入对象及返回对象的主键属性值不为null
	 * 
	 * @param car
	 *            新增的Car数据（如果无特殊需求，新增对象的主键值请保留为null）
	 * @param operator
	 *            操作人对象
	 * @return
	 */
	@Transactional
	public ResultInfo<Car> addCar(Car car, Operator operator) {
		ResultInfo<Car> resultInfo = new ResultInfo<Car>();

		if (car == null) { // 传入参数无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG);
			log.info(Constant.ERR_MSG_INVALID_ARG + " car = " + car);
		} else {
			try {
				// 如果传入参数的主键为null，则调用生成主键的方法获取新的主键
				if (car.getCarNo() == null) {
					car.setCarNo(this.generatePK());
				}
				// 如果传入的操作人不为null，则设置新增记录的操作人类型和操作人id
				if (operator != null) {
					car.setOperatorType(operator.getOperatorType());
					car.setOperatorId(operator.getOperatorId());
				}

				// 设置创建时间和更新时间为当前时间
				Date now = new Date();
				car.setCreateTime(now);
				car.setUpdateTime(now);
				car.setOnlineStatusUpdateTime(now);
				// 填充默认值
				this.fillDefaultValues(car);
				// 城市
				DataDictItem dataDictItemcity = dataDictItemService.getDataDictItem(car.getCityId());
				if (dataDictItemcity != null) {
					car.setCityName(dataDictItemcity.getItemValue());
				}
				// 品牌
				CarBrand carBrand = carBrandService.getCarBrand(car.getCarBrandId());
				if (carBrand != null) {
					car.setCarBrandName(carBrand.getCarBrandName());
				}
				// 型号
				CarSeries carSeries = carSeriesService.getCarSeries(car.getCarModelId());
				if (carSeries != null) {
					car.setCarModelName(carSeries.getCarSeriesName());
				}
				// 车主
				CarOwner carOwner = carOwnerDao.get(car.getCarOwnerId());
				if (carOwner != null) {
					car.setCarOwnerName(carOwner.getOwnerName());
				}
				// 调用Dao执行插入操作
				carDao.add(car);
				resultInfo.setCode(Constant.SECCUESS);
				resultInfo.setData(car);
				// 终端更新
				if (car.getDeviceId() != null && car.getDeviceId().length() != 0) {
					Device device = deviceDao.get(car.getDeviceId());
					device.setCarNo(car.getCarNo());
					device.setCarPlateNo(car.getCarPlateNo());
					device.setBindingTime(new Date());
					deviceDao.update(device);
				}
				// 车辆状态增加
				CarStatus carStatus = new CarStatus();
				carStatus.setVehicleType(car.getVehicleType());
				carStatus.setCarNo(car.getCarNo());
				carStatus.setCarPlateNo(car.getCarPlateNo());
				carStatus.setCityId(car.getCityId());
				if (dataDictItemcity != null) {
					carStatus.setCityName(dataDictItemcity.getItemValue());
				}
				carStatus.setLocationParkNo(car.getLocationParkNo());
				carStatus.setPower(car.getPower());
				carStatus.setDeviceNo(car.getDeviceId());
				carStatus.setMileage(car.getMileage());
				carStatus.setUserageStatus(0);
				carStatus.setCarStatus(2);
				Date nowTime = new Date();
				carStatus.setCreateTime(nowTime);
				carStatus.setUpdateTime(nowTime);
				// 绑定场站的车辆位置
				Park park = parkDao.get(car.getLocationParkNo());
				if (park != null) {
					if (park.getLongitude() != null && park.getLatitude() != null) {
						double[] wgs = ECGeoCoordinateTransformUtil.bd09towgs84(Double.parseDouble(park.getLongitude()),
								Double.parseDouble(park.getLatitude()));
						carStatus.setLongitude(wgs[0]);
						carStatus.setLatitude(wgs[1]);
					}
				}
				carStatusDao.add(carStatus);
				// 在百度上添加一个entity
				Column c = new Column();
				c.setColumn_key("car_plate_no");
				c.setColumn_value(car.getCarPlateNo());
				Entity entity = new Entity();
				entity.setEntityName(car.getCarNo());
				List<Column> column = new ArrayList<Column>();
				column.add(c);
				entity.setColumn(column);
				baiduEntityApiService.addEntity(entity, 1);
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
	 * 根据主键，更新一条Car记录
	 * 
	 * @param car
	 *            更新的Car数据，且其主键不能为空
	 * @param operator
	 *            操作人对象
	 * @return
	 */
	@Transactional
	public ResultInfo<Car> updateCar(Car car, Operator operator) {
		ResultInfo<Car> resultInfo = new ResultInfo<Car>();

		if (car == null || car.getCarNo() == null) { // 传入参数无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG);
			log.info(Constant.ERR_MSG_INVALID_ARG + " car = " + car);
		} else {
			try {
				// 如果传入的操作人不为null，则设置更新记录的操作人类型和操作人id
				if (operator != null) {
					car.setOperatorType(operator.getOperatorType());
					car.setOperatorId(operator.getOperatorId());
				}
				// 城市
				DataDictItem dataDictItemcity = dataDictItemService.getDataDictItem(car.getCityId());
				if (dataDictItemcity != null) {
					car.setCityName(dataDictItemcity.getItemValue());
				}
				// 品牌
				CarBrand carBrand = carBrandService.getCarBrand(car.getCarBrandId());
				if (carBrand != null) {
					car.setCarBrandName(carBrand.getCarBrandName());
				}
				// 型号
				CarSeries carSeries = carSeriesService.getCarSeries(car.getCarModelId());
				if (carSeries != null) {
					car.setCarModelName(carSeries.getCarSeriesName());
				}
				// 车主
				CarOwner carOwner = carOwnerDao.get(car.getCarOwnerId());
				if (carOwner != null) {
					car.setCarOwnerName(carOwner.getOwnerName());
				}
				// 上下线时间 修改
				Date data = new Date();
				car.setOnlineStatusUpdateTime(data);

				// 设置更新时间为当前时间
				car.setUpdateTime(new Date());

				// 调用Dao执行更新操作，并判断更新语句执行结果
				int count = carDao.update(car);
				if (count > 0) {
					resultInfo.setCode(Constant.SECCUESS);
					// 终端更新
					if (car.getDeviceId() != null && car.getDeviceId().length() != 0) {
						Device device = deviceDao.get(car.getDeviceId());
						if (device != null) {
							device.setCarNo(car.getCarNo());
							device.setCarPlateNo(car.getCarPlateNo());
							device.setBindingTime(new Date());
							deviceDao.update(device);
							// 更换设备的同时，修改车辆状态表中的终端编号
							CarStatus cs = new CarStatus();
							cs.setVehicleType(car.getVehicleType());
							cs.setCarNo(car.getCarNo());
							cs.setDeviceNo(car.getDeviceId());
							if(car.getLocationParkNo() != null  && !"".equals(car.getLocationParkNo())){
								cs.setLocationParkNo(car.getLocationParkNo());
							}
							carStatusService.updateCarStatus(cs, operator);
						}
					}

					// 默认 模式为场站租还车， 1 场站 2车辆 3场站+车
					String returnCar = sysParamService.getByParamKey("RETURN_CAR_OUTSIDE_SWITCH") == null ? "1"
							: sysParamService.getByParamKey("RETURN_CAR_OUTSIDE_SWITCH").getParamValue();

					if ("2".equals(returnCar) || "3".equals(returnCar)) { // 只有2
																			// 3
																			// 时才推
						businessIndexService.saveOrUpdateCar(car.getCarNo());// 更新到搜索引擎中
					}
					// 车辆下线时百度上删除entity
					// if(car.getOnlineStatus()==0){
					// Entity entity = new Entity();
					// entity.setEntityName(car.getCarNo());
					// baiduEntityApiService.deleteEntity(entity);
					// }
				} else {
					resultInfo.setCode(Constant.FAIL);
				}
				resultInfo.setData(car);
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
		return primarykeyService.getValueByBizType(PrimarykeyConstant.carType) + "";
	}

	/**
	 * 为Car对象设置默认值
	 * 
	 * @param obj
	 */
	public void fillDefaultValues(Car obj) {
		if (obj != null) {
			if (obj.getIsDedicated() == null) {
				obj.setIsDedicated(0);
			}
			if (obj.getOnlineStatus() == null) {
				obj.setOnlineStatus(0);
			}
		}
	}

	@Override
	public List<Car> getCarListByParkId(String parkId, String memberNo) {
		// TODO Auto-generated method stub
		List<Car> list = null;
		try {
			SysParam sysParam = sysParamService.getByParamKey(ParkConstant.power_limit);
			Double power = Double.parseDouble(sysParam.getParamValue());// 单位：%电量
			if (memberNo != null && !memberNo.equals("")) {
				if (memberDao.get(memberNo) != null) {
					if (memberDao.get(memberNo).getMemberType().equals(1)) {// 普通会员
						list = carDao.getCarListByParkId(parkId, power);
					} else if (memberDao.get(memberNo).getMemberType().equals(2)) {// 集团会员
						list = carDao.getCarListByParkId1(parkId, memberDao.get(memberNo).getCompanyId(), power);
					}
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		// 如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<Car>(0) : list;

		return list;
	}

	@Override
	public Car getCarByPlateNo(String carPlateNo) {
		return carDao.getCarByPlateNo(carPlateNo);
	}

	/**
	 * 根据场站ID得到改场站内公用的电量最高的车
	 */
	@Override
	public List<CarAndStatus> getCarByParkNo(String parkNo,double power,String seaTing) {
		return carDao.getCarByParkNo2(parkNo, power,seaTing);
	}

	/**
	 * 根据场站ID和用户查询场站内可用的电量最高的车
	 */
	@Override
	public List<CarAndStatus> getCarByMemberAndParkNo(String companyId, String parkNo, double power ,String seaTing) {
		return carDao.getCarByMemberAndParkNo2(companyId, parkNo, power,seaTing);
	}

	@Override
	public List<Car> getCarListByParkExist(String parkNo) {
		// TODO Auto-generated method stub
		List<Car> list = null;
		try {
			list = carDao.getCarListByParkExist(parkNo);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		// 如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<Car>(0) : list;

		return list;
	}

	@Override
	public int countCar(Query q) {
		int count = 0;
		try {
			count = ECCalculateUtils.convertsToInt(carDao.carCount(q));
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return count;
	}

	@Override
	public ResultInfo<Car> editCarDevice(Car car, Operator operator) {
		ResultInfo<Car> resultInfo = new ResultInfo<Car>();

		if (car == null || car.getCarNo() == null) { // 传入参数无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG);
			log.info(Constant.ERR_MSG_INVALID_ARG + " car = " + car);
		} else {
			try {
				// 如果传入的操作人不为null，则设置更新记录的操作人类型和操作人id
				if (operator != null) {
					car.setOperatorType(operator.getOperatorType());
					car.setOperatorId(operator.getOperatorId());
				}
				Car car2 = carDao.get(car.getCarNo());
				// 调用Dao执行更新操作，并判断更新语句执行结果
				int count = carDao.editCarDevice(car);

				if (count > 0) {
					resultInfo.setCode(Constant.SECCUESS);
					// 终端更新
					if (car2.getDeviceId() != null && car2.getDeviceId().length() != 0) {
						Device device = deviceDao.get(car2.getDeviceId());
						if (device != null) {
							device.setCarNo("");
							device.setCarPlateNo("");
							device.setBindingTime(null);
							deviceDao.updateDeviceCar(device);
						}
					}
				} else {
					resultInfo.setCode(Constant.FAIL);
				}
				resultInfo.setData(car);
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
	 * 1、车辆打火后调此接口 2、通过车辆编号查询订单表和调度，若有订单或者调度单是正常，否则为不正常，给警告表添加信息
	 * 
	 * @throws IOException
	 */
	@Override
	@Transactional
	public ResultInfo<Car> judgeCarInOrder(String carNo, String APPKEY, String urls) throws IOException {
		ResultInfo<Car> resultInfo = new ResultInfo<>();
		if (carNo != null) {
			Order order = new Order();
			order.setCarNo(carNo);
			order.setOrderStatus(2);
			long orderCount = orderService.countOrder(new Query(order));

			WorkerOrder workerOrder = new WorkerOrder();
			workerOrder.setCarNo(carNo);
			workerOrder.setWorkOrderStatus(2);
			long workerOrderCount = workerOrderService.countWorkerOrder(new Query(workerOrder));

			if (orderCount > 0 || workerOrderCount > 0) { // 有匹配的订单、或者有匹配的调度单
				resultInfo.setCode(Constant.SECCUESS);
				resultInfo.setMsg("车辆状态信息正常");
				return resultInfo;
			}

			Car car = carDao.get(carNo); // 根据车辆编号，查询车辆
			if (car == null) {
				resultInfo.setCode(Constant.FAIL);
				resultInfo.setMsg("车辆编号无效");
				return resultInfo;
			}

			Date nowTime = new Date();

			Warning w = new Warning();
			w.setCarPlateNo(car.getCarPlateNo());
			w.setCityId(car.getCityId());
			w.setCityName(car.getCityName());
			if (car.getLocationParkNo() != null) {
				Park park = parkDao.get(car.getLocationParkNo());
				w.setParkNo(park.getParkNo());
				w.setParkName(park.getParkName());
			}
			w.setIsClosed(0);
			w.setIsNeedManualClosed(1);
			w.setWarningContent("车辆打火但是无相关订单或调度单");
			w.setWarningLevel(1);
			w.setWarningType("车辆");
			w.setWarningSubType("车辆状态信息异常");
			w.setWarningTime(nowTime);
			w.setUpdateTime(nowTime);
			w.setCreateTime(nowTime);
			Operator operator = new Operator();
			operator.setOperatorType(0);
			ResultInfo<Warning> res = warningService.addWarning(w, operator);
			if (res.getCode().equals(Constant.SECCUESS)) {
				resultInfo.setCode("-1");
				resultInfo.setMsg("添加警告信息成功");
				resultInfo.setData(car);
				// 发送短信通知
				try {
					String phone = sysParamService.getParamValueByParamKey("CarMobilePhone");
					if (phone != null && !StringUtils.isEmpty(phone)) {
						// sendMsg.easySendMsgTemplate(phone,car.getCarPlateNo(),3,null);
						// 车牌号为:{carPlateNo}的车辆已打火但是无相关订单或调度单，请及时查看！
						boolean sate = false;
						try {
							if (car != null && !"".equals(car.getCarPlateNo())) {
								String carPlateNo = car.getCarPlateNo();
								// 聚合
								 sendMsgCommonInterfaceService.sendMsgPost(phone,
								 carPlateNo, tplId);
								// 聚通达,浪驰
//								sate = sendMsgCommonInterfaceService.sendMsgPost(phone, carPlateNo,
//										MsgConstant.NOORDERTYPE);
								System.err.println("发送结果！！！！！！！！！！！！！！" + sate);
							} else {
								// 聚合
								// sendMsgCommonInterfaceService.sendMsgPost(phone,
								// "", tplId);
								// 聚通达,浪驰
//								sate = sendMsgCommonInterfaceService.sendMsgPost(phone, "", MsgConstant.NOORDERTYPE);
//								System.err.println("发送结果！！！！！！！！！！！！！！" + sate);
							}

						} catch (Exception e) {
							e.printStackTrace();
						}
					}

				} catch (Exception e) {
					log.error(e.getMessage(), e);
				}

			} else {
				resultInfo.setCode(Constant.FAIL);
				resultInfo.setMsg("添加警告信息失败");
			}

		}
		return resultInfo;
	}

	/**
	 * 根据场站ID得到改场站内公用的电量最高的车
	 */
	@Override
	public List<CarAndStatus> getCarByParkNoWorker(String parkNo) {
		return carDao.getCarByParkNoWorker(parkNo);
	}

	@Override
	public ResultInfo<Car> expotCarAdd(MultipartFile multipartFile, Operator operator) throws Exception {
		ResultInfo<Car> resultInfo = new ResultInfo<Car>();
		Sheet[] sheet = null;
		jxl.Workbook rwb = null;
		try {
			HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
					.getRequest();
			String resImgPath = request.getSession().getServletContext().getRealPath("/");
			String filePath = resImgPath + "/xls/";
			File file = new File(filePath);
			if (!file.exists() && !file.isDirectory()) {
				file.mkdirs();
			}
			String filenameReal = filePath + System.currentTimeMillis() + "car.xls";

			File logoSaveFile = new File(filenameReal);
			multipartFile.transferTo(logoSaveFile);

			FileInputStream fis = new FileInputStream(logoSaveFile);
			rwb = Workbook.getWorkbook(fis);
			sheet = rwb.getSheets();
		} catch (Exception e) {
			throw new XlsImportException("文件操作异常！");
		}
		for (int i = 0; i < sheet.length; i++) {
			Sheet rs = rwb.getSheet(i);
			for (int j = 0; j < rs.getRows(); j++) {
				if (j > 0) {
					try {
						// 车牌号，车辆识别号，租凭类型，上线状态，城市，场站名字,车辆品牌,车辆型号，车主
						// 终端序列号，终端品牌，终端型号，终端MAC地址 SIM 卡号
						// carPlateNo,carIdNo,leaseType,onlineStatus,cityName,parkName,carBrandName,carModelName,carOwnerName
						// deviceSn,deviceBrandName,deviceModelName,deviceMACAddr
						Cell[] cells = rs.getRow(j);
						String carPlateNo = "";
						String carIdNo = "";
						String leaseType = "";
						String onlineStatus = "";
						String cityName = "";
						String parkName = "";
						String carBrandName = "";
						String carModelName = "";
						String carOwnerName = "";
						String deviceSn = "";
						String deviceBrandName = "";
						String deviceModelName = "";
						String deviceMACAddr = "";
						String deviceSimCardNo = "";
						String seaTing = "";
						String carPhotoUrl1="";
						Integer num = cells.length;
						carPlateNo = cells[0].getContents().trim();
						carIdNo = cells[1].getContents().trim();
						leaseType = cells[2].getContents().trim();
						// onlineStatus = cells[3].getContents().trim();
						cityName = cells[3].getContents().trim();
						parkName = cells[4].getContents().trim();
						carBrandName = cells[5].getContents().trim();
						carModelName = cells[6].getContents().trim();
						carOwnerName = cells[7].getContents().trim();

						if (8 < num) {
							if (cells[8].getContents() != null && !cells[8].getContents().equals("")) {
								deviceSn = cells[8].getContents().trim();
							} else {
								deviceSn = "";
							}
						}
						if (9 < num) {
							if (cells[9].getContents() != null && !cells[9].getContents().equals("")) {
								deviceBrandName = cells[9].getContents().trim();
							} else {
								deviceBrandName = "";
							}
						}
						if (10 < num) {
							if (cells[10].getContents() != null && !cells[10].getContents().equals("")) {
								deviceModelName = cells[10].getContents().trim();
							} else {
								deviceModelName = "";
							}
						}
						if (11 < num) {
							if (cells[11].getContents() != null && !cells[11].getContents().equals("")) {
								deviceMACAddr = cells[11].getContents().trim();
							} else {
								deviceMACAddr = "";
							}
						}
						if (12 < num) {
							if (cells[12].getContents() != null && !cells[12].getContents().equals("")) {
								deviceSimCardNo = cells[12].getContents().trim();
							} else {
								deviceSimCardNo = "";
							}
						}
						Integer leaseTypeFinal = 0;
						// 租赁类型（1、分时，2、长租）
						if (leaseType.trim().equals("分时")) {
							leaseTypeFinal = 1;
						} else if (leaseType.trim().equals("长租")) {
							leaseTypeFinal = 2;
						}
						// 上线状态（0、下线，1、上线，默认0）
						Integer onlineStatusFinal = 0;

						// 先添加品牌
						String carBrandId = "";
						DataDictItem dataDictItem = new DataDictItem();
//						dataDictItem.setItemValue(carBrandName);
//						List<DataDictItem> dataDictItems1 = dataDictItemService
//								.getDataDictItemList(new Query(dataDictItem));
//						if (dataDictItems1 != null && dataDictItems1.size() > 0) {
//							carBrandId = dataDictItems1.get(0).getDataDictItemId();
//						} else {
//							dataDictItem.setDataDictCatCode("CAR_BRAND");
//							carBrandId = dataDictItemService.addDataDictItem(dataDictItem, operator).getData()
//									.getDataDictItemId();
//						}
						CarBrand cb=new CarBrand();
						cb.setCarBrandName(carBrandName);
						Query qb = new Query(cb);
						List<CarBrand> carBrands=carBrandService.getCarBrandList(qb);//汽车品牌
						if(carBrands != null && carBrands.size()>0 ){
							carBrandId = carBrands.get(0).getCarBrandId();
						}else{
							resultInfo.setCode(Constant.FAIL);
							resultInfo.setMsg("第" + (j + 1) + "品牌" + carBrandName + "没有添加");
							return resultInfo;
						}
						
						// 添加型号
						String carModelId = "";
//						dataDictItem.setItemValue(carModelName);
//						List<DataDictItem> dataDictItems2 = dataDictItemService
//								.getDataDictItemList(new Query(dataDictItem));
//						if (dataDictItems2 != null && dataDictItems2.size() > 0) {
//							carModelId = dataDictItems2.get(0).getDataDictItemId();
//						} else {
//							dataDictItem.setDataDictCatCode("CAR_MODEL");
//							carModelId = dataDictItemService.addDataDictItem(dataDictItem, operator).getData()
//									.getDataDictItemId();
//						}
						CarSeries cs=new CarSeries();
						cs.setCarSeriesName(carModelName);
						Query qc = new Query(cs);
						List<CarSeries> carmodels=carSeriesService.getCarSeriesList(qc);//汽车型号
						if(carmodels != null && carmodels.size()>0){
							carModelId = carmodels.get(0).getCarSeriesId();
							seaTing = carmodels.get(0).getSeaTing();
							carPhotoUrl1 = carmodels.get(0).getCarSeriresPic();
						}else{
							resultInfo.setCode(Constant.FAIL);
							resultInfo.setMsg("第" + (j + 1) + "车型" + carModelName + "没有添加");
							return resultInfo;
						}
						// 添加城市
						String cityId = "";
						dataDictItem.setItemValue(cityName);
						List<DataDictItem> dataDictItems3 = dataDictItemService
								.getDataDictItemList(new Query(dataDictItem));
						if (dataDictItems3 != null && dataDictItems3.size() > 0) {
							cityId = dataDictItems3.get(0).getDataDictItemId();
						} else {
							dataDictItem.setDataDictCatCode("CITY");
							cityId = dataDictItemService.addDataDictItem(dataDictItem, operator).getData()
									.getDataDictItemId();
						}
						// 然后在添加车主
						String carOwnerId = "";
						CarOwner carOwner = new CarOwner();
						carOwner.setOwnerName(carOwnerName);
						List<CarOwner> carOwners = carOwnerService.getCarOwnerList(new Query(carOwner));
						if (carOwners != null && carOwners.size() > 0) {
							carOwnerId = carOwners.get(0).getOwnerId();
						} else {
							carOwnerId = carOwnerService.addCarOwner(carOwner, operator).getData().getOwnerId();
						}
						//dataDictItemService.addDataDictItem(dataDictItem, operator);
						// 然后添加终端
						Device device = new Device();
						// 终端编号
						String terminalDeviceNo = "";
						if (deviceSn != null && deviceSn.length() != 0) {
							device.setDeviceSn(deviceSn);
							List<Device> listDevice = deviceService.getDeviceList(new Query(device));
							if (listDevice != null && listDevice.size() > 0) {
								// 如果存在，判断终端有没有绑定车辆
								if (listDevice.get(0).getCarPlateNo() == null) {
									terminalDeviceNo = listDevice.get(0).getTerminalDeviceNo();
								} else {
									resultInfo.setCode(Constant.FAIL);
									resultInfo.setMsg("第" + (j + 1) + "行终端号为:" + deviceSn + "重复！");
									return resultInfo;
								}
							} else {
								// 判断终端编号，终端品牌，终端型号，终端MAC地址,SIM卡号
								// 是否都填写，都填写则添加设备。否则不添加
								// 终端MAC地址,SIM卡号 也不能重复
								if (deviceBrandName != null && deviceBrandName.length() != 0 && deviceModelName != null
										&& deviceModelName.length() != 0 ) {// ,deviceBrandName,deviceModelName,deviceMACAddr
									// 判断终端品牌，没有则添加
									String deviceBrandId = "";
									DataDictItem dataDictItem1 = new DataDictItem();
									dataDictItem1.setItemValue(deviceBrandName);
									List<DataDictItem> dataDictItems11 = dataDictItemService
											.getDataDictItemList(new Query(dataDictItem1));
									if (dataDictItems11 != null && dataDictItems11.size() > 0) {
										deviceBrandId = dataDictItems11.get(0).getDataDictItemId();
									} else {
										dataDictItem.setDataDictCatCode("DEVICE_BRAND");
										deviceBrandId = dataDictItemService.addDataDictItem(dataDictItem1, operator)
												.getData().getDataDictItemId();
									}
									// 判断终端型号，没有则添加
									String deviceModelId = "";
									dataDictItem1.setItemValue(deviceModelName);
									List<DataDictItem> dataDictItems22 = dataDictItemService
											.getDataDictItemList(new Query(dataDictItem1));
									if (dataDictItems22 != null && dataDictItems22.size() > 0) {
										deviceModelId = dataDictItems22.get(0).getDataDictItemId();
									} else {
										dataDictItem1.setDataDictCatCode("DEVICE_MODEL");
										dataDictItem1.setItemValue(deviceModelName);
										deviceModelId = dataDictItemService.addDataDictItem(dataDictItem1, operator)
												.getData().getDataDictItemId();
									}
									// 判断 终端MAC地址 不能重复
									Device deviceMac = new Device();
									deviceMac.setMacAddr(deviceMACAddr);
									List<Device> listDeviceMacAddr = deviceService.getDeviceList(new Query(deviceMac));
									if (listDeviceMacAddr != null && listDeviceMacAddr.size() > 0) {
										// 如果存在，判断终端有没有绑定车辆
										if (listDeviceMacAddr.get(0).getMacAddr() == null
												&& !listDeviceMacAddr.get(0).getMacAddr().equals("")) {
											deviceMACAddr = listDeviceMacAddr.get(0).getMacAddr();
										} else {
											resultInfo.setCode(Constant.FAIL);
											resultInfo.setMsg("第" + (j + 1) + "行终端MAC地址为:" + deviceMACAddr + "重复！");
											return resultInfo;
										}
									}

									// 判断 SIM 卡号 不能重复
									Device deviceSim = new Device();
									deviceSim.setSimCardNo(deviceSimCardNo);
									List<Device> listDeviceSimCardNo = deviceService
											.getDeviceList(new Query(deviceSim));
									if (listDeviceSimCardNo != null && listDeviceSimCardNo.size() > 0) {
										// 如果存在，判断终端有没有绑定车辆
										if (listDeviceSimCardNo.get(0).getSimCardNo() == null
												&& !listDeviceSimCardNo.get(0).getSimCardNo().equals("")) {
											deviceSimCardNo = listDeviceSimCardNo.get(0).getSimCardNo();
										} else {
											resultInfo.setCode(Constant.FAIL);
											resultInfo.setMsg("第" + (j + 1) + "行SIM卡号为:" + deviceSimCardNo + "重复！");
											return resultInfo;
										}
									}

									device.setBrandId(deviceBrandId);
									device.setBrandName(deviceBrandName);
									device.setDeviceModelId(deviceModelId);
									device.setDeviceModel(deviceModelName);
									device.setMacAddr(deviceMACAddr);
									device.setSimCardNo(deviceSimCardNo);
									device.setCityId(cityId);
									device.setCityName(cityName);
									terminalDeviceNo = deviceService.addDevice(device, operator).getData()
											.getTerminalDeviceNo();

								}
							}
						}

						// 再然后添加场站
						Park park = new Park();
						String parkNo = "";// 场站编号
						if (parkName != null && parkName.length() != 0) {
							park.setParkName(parkName);
							List<Park> listPark = parkService.getParkList(new Query(park));
							if (listPark != null && listPark.size() > 0) {
								parkNo = listPark.get(0).getParkNo();
							} else {
								resultInfo.setCode(Constant.FAIL);
								resultInfo.setMsg("第" + (j + 1) + "行场站名称为:" + parkName + "不存在，请先添加了场站再导入！");
								return resultInfo;
							}
						}
						// 最后添加车辆信息
						Car car = new Car();
						car.setCarPlateNo(carPlateNo);
						List<Car> listCar = getCarList(new Query(car));
						if (listCar != null && listCar.size() > 0) {
							resultInfo.setCode(Constant.FAIL);
							resultInfo.setMsg("第" + (j + 1) + "行车牌号" + carPlateNo + "重复！");
							return resultInfo;
						} else {
							car.setCarIdNo(carIdNo);
							car.setLeaseType(leaseTypeFinal);
							car.setOnlineStatus(onlineStatusFinal);
							if (deviceSn != null && deviceSn.length() != 0 && deviceBrandName != null
									&& deviceBrandName.length() != 0 && deviceModelName != null
									&& deviceModelName.length() != 0) {
								car.setDeviceId(terminalDeviceNo);
							}
							car.setLocationParkNo(parkNo);
							car.setCarBrandId(carBrandId);
							car.setCarBrandName(carBrandName);
							car.setCarModelId(carModelId);
							car.setCarModelName(carModelName);
							car.setCarOwnerId(carOwnerId);
							car.setCarOwnerName(carOwnerName);
							car.setCityId(cityId);
							car.setCityName(cityName);
							car.setSeaTing(seaTing);
							// 获取座位数
//							DataDictItem da = new DataDictItem();
//							da.setDataDictItemId(carModelId);
//							Query q = new Query(da);
//							List<DataDictItem> das = dataDictItemService.getDataDictItemList(q);
//							if (das != null && das.size() > 0) {
//								DataDictItem daa = das.get(0);
//								if (daa.getInfo1() != null && !equals(daa.getInfo1())) {
//									car.setSeaTing(daa.getInfo1());
//								} else {
//									car.setSeaTing("0");
//								}
//
//								if (daa.getInfo2() != null && !equals(daa.getInfo2())) {
//									car.setCarPhotoUrl1(daa.getInfo2());
//								} else {
//									car.setCarPhotoUrl1("");
//								}
//							}
							// 默认车辆电量90%
							car.setPower(Double.valueOf("90"));
							resultInfo = addCar(car, operator);
						}
					} catch (Exception e) {
						resultInfo.setCode(Constant.FAIL);
						if (e instanceof XlsImportException) {
							throw new XlsImportException(((XlsImportException) e).getErrorMsg());
						} else {
							throw new XlsImportException("第" + (j + 1) + "行出错！数据有误！");
						}
					}

				}
			}
		}
		return resultInfo;
	}

	@Override
	public List<Car> getCarsByPlateNo(String carPlateNo) {
		return carDao.getCarsByPlateNo(carPlateNo);
	}

	@Override
	public List<CarWkVo> getCarByPlateNoWk(Query q) {
		List<CarWkVo> list = null;
		try {
			list = carDao.getCarByPlateNoWk(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		// 如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<CarWkVo>(0) : list;

		return list;
	}
	/*\
	 * 故障列表展示 下线车辆列表
	 * */
	@Override
	public List<CarWkVo> getCarFaultWk(Query q) {
		List<CarWkVo> list = null;
		try {
			list = carDao.getCarFaultWk(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		// 如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<CarWkVo>(0) : list;

		return list;
	}
	
	/*\
	 * 故障列表展示 终端不在线车辆列表
	 * */
	@Override
	public List<CarWkVo> getIsOnlineCarStatus(Query q) {
		List<CarWkVo> list = null;
		try {
			
//			if (car.getLastReportingTime() != null) {
//				long timeDifference = new Date().getTime() - car.getLastReportingTime().getTime();
//				if (onlineTimeThreshol >= timeDifference) {
//					car.setIsOnline(1);// 在线
//				} else {
//					car.setIsOnline(2);// 不在线
//				}
//			}	
			list = carDao.getIsOnlineCarStatus(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		// 如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<CarWkVo>(0) : list;

		return list;
	}

	@Override
	public CarDetailWkVo carWkDetail(String carNo) {
		return carDao.carWkDetail(carNo);
	}

	@Override
	public CarOnlineVo offLinerLately(String carNo) {
		return carDao.offLinerLately(carNo);
	}

	@Override
	public long count(Query q) {
		return carDao.count(q);
	}

	@Override
	public Map<String, Object> carServiceMap() {
		Map<String, Object> map = getCarLineCountMap();
		Integer param = 20;
		SysParam sysParam = sysParamService.getByParamKey("CarPowerParam");
		if(sysParam != null){
			param = Integer.parseInt(sysParam.getParamValue());
		}
		Integer carLowPowerNum = carStatusDao.countCarLowPower(param);
		map.put("carLowPowerNum", carLowPowerNum);
		Integer lotParkingSpaceNum = parkDao.countLotParkingSpace();
		map.put("lotParkingSpaceNum", lotParkingSpaceNum);
		Map<String, Object> m = workerOrderDao.countWorkerOrder();
		map.putAll(m);
		return map;
	}

	@Override
	public Integer countCarNotLine() {
		return carDao.countCarNotLine();
	}

	@Override
	public Integer countCarIdle() {
		return carDao.countCarIdle();
	}

	@Override
	public Map<String, Object> getCarLineCountMap() {
		return carDao.getCarLineCountMap();
	}

	@Override
	public List<Car> listCar(String franchiseeNo) {
		List<Car> list = null;
		try {
			list = carDao.listCar(franchiseeNo);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		// 如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<Car>(0) : list;
		return list;
	}
	@Override
	public Integer getcarups() {
		
		Integer carUpNum = 0;
		if (carDao.getcarups() != null) {
			carUpNum = carDao.getcarups();
		}
		return carUpNum;
	}
	
	@Override
	public Integer getcarDowns() {
		
		Integer carUpNum = 0;
		if (carDao.getcarDowns() != null) {
			carUpNum = carDao.getcarDowns();
		}
		return carUpNum;
	}
	
	
	
	
	
	@Override
	public Integer getIsOnline(String cityId) {
		
		Integer carUpNum = 0;
		Long onlineTimeThreshol = getOnlineTimeThreshol();
		carUpNum = carDao.getIsOnline(onlineTimeThreshol,cityId);
		return carUpNum;
	}

	@Override
	public Integer getcarDownsCityId(String cityId) {
			
			Integer carUpNum = 0;
			if (carDao.getcarDowns() != null) {
				carUpNum = carDao.getcarDownsCityId(cityId);
			}
			return carUpNum;
	}
	
	
}
