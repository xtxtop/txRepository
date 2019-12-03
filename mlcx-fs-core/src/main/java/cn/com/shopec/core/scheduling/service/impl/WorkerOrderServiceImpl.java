package cn.com.shopec.core.scheduling.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import cn.com.shopec.common.Operator;
import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.common.constants.Constant;
import cn.com.shopec.common.exception.xls.XlsImportException;
import cn.com.shopec.common.utils.ECDateUtils;
import cn.com.shopec.common.utils.ECNumberUtils;
import cn.com.shopec.core.car.common.CarConstant;
import cn.com.shopec.core.car.dao.CarDao;
import cn.com.shopec.core.car.dao.CarStatusDao;
import cn.com.shopec.core.car.model.Car;
import cn.com.shopec.core.car.model.CarRecord;
import cn.com.shopec.core.car.model.CarStatus;
import cn.com.shopec.core.car.service.CarRecordService;
import cn.com.shopec.core.car.service.CarStatusService;
import cn.com.shopec.core.car.service.CarTripService;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.map.service.BaiduGeocoderApiService;
import cn.com.shopec.core.resource.dao.ParkDao;
import cn.com.shopec.core.resource.model.Park;
import cn.com.shopec.core.resource.vo.TaskVo;
import cn.com.shopec.core.scheduling.common.WorkerOrderConstant;
import cn.com.shopec.core.scheduling.dao.WorkerDao;
import cn.com.shopec.core.scheduling.dao.WorkerOrderDao;
import cn.com.shopec.core.scheduling.model.Worker;
import cn.com.shopec.core.scheduling.model.WorkerOrder;
import cn.com.shopec.core.scheduling.service.WorkerOrderService;
import cn.com.shopec.core.scheduling.vo.WorkerOrderDetailsVo;
import cn.com.shopec.core.search.service.BusinessIndexService;
import cn.com.shopec.core.system.service.SysParamService;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

/**
 * WorkerOrder 服务实现类
 */
@Service
public class WorkerOrderServiceImpl implements WorkerOrderService {

	private static final Log log = LogFactory.getLog(WorkerOrderServiceImpl.class);

	@Resource
	private WorkerOrderDao workerOrderDao;

	@Resource
	private ParkDao parkDao;

	@Resource
	private CarDao carDao;
	@Resource
	private SysParamService sysParamService;

	@Resource
	private CarStatusDao carStatusDao;

	@Resource
	private BusinessIndexService businessIndexService;

	@Resource
	private WorkerDao workerDao;

	@Resource
	private CarRecordService carRecordService;

	@Resource
	private CarStatusService carStatusService;

	@Resource
	private CarTripService carTripService;
	@Resource
	private BaiduGeocoderApiService  baiduGeocoderApiService;

	/**
	 * 根据查询条件，查询并返回WorkerOrder的列表
	 * 
	 * @param q
	 *            查询条件
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<WorkerOrder> getWorkerOrderList(Query q) {
		List<WorkerOrder> list = null;
		try {
			// 直接调用Dao方法进行查询
			list = workerOrderDao.queryAll(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		// 如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<WorkerOrder>(0) : list;
		return list;
	}

	/**
	 * 根据查询条件，分页查询并返回WorkerOrder的分页结果
	 * 
	 * @param q
	 *            查询条件
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public PageFinder<WorkerOrder> getWorkerOrderPagedList(Query q) {
		PageFinder<WorkerOrder> page = new PageFinder<WorkerOrder>();

		List<WorkerOrder> list = null;
		long rowCount = 0L;

		try {
			// 调用dao查询满足条件的分页数据
			list = workerOrderDao.pageList(q);
			for (WorkerOrder w : list) {
				Park park1 = parkDao.get(w.getStartParkNo());
				if (park1 != null) {
					w.setStartParkName(park1.getParkName());
				}
				
				if(w.getCurrentAddress() != null && !"".equals(w.getCurrentAddress())){
					w.setTerminalParkName(w.getCurrentAddress());
				}else{
					Park park2 = parkDao.get(w.getTerminalParkNo());
					if (park2 != null) {
						w.setTerminalParkName(park2.getParkName());
					}
				}
			}
			// 调用dao统计满足条件的记录总数
			rowCount = workerOrderDao.count(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		// 如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<WorkerOrder>(0) : list;

		// 将分页数据和记录总数设置到分页结果对象中
		page.setData(list);
		page.setRowCount(rowCount);

		return page;
	}

	/**
	 * 根据主键，查询并返回一个WorkerOrder对象
	 * 
	 * @param id
	 *            主键id
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public WorkerOrder getWorkerOrder(String id) {
		WorkerOrder obj = null;
		if (id == null || id.length() == 0) { // 传入的主键无效时直接返回null
			log.info(Constant.ERR_MSG_INVALID_ARG + " id = " + id);
			return obj;
		}
		try {
			// 调用dao，根据主键查询
			obj = workerOrderDao.get(id);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		return obj;
	}

	/**
	 * 根据主键数组，查询并返回一组WorkerOrder对象
	 * 
	 * @param ids
	 *            主键数组，数组中的主键值应当无重复值，如有重复值时，则返回的列表长度可能小于传入的数组长度
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<WorkerOrder> getWorkerOrderByIds(String[] ids) {
		List<WorkerOrder> list = null;
		if (ids == null || ids.length == 0) {
			log.info(Constant.ERR_MSG_INVALID_ARG + " ids is null or empty array.");
		} else {
			try {
				// 调用dao，根据主键数组查询
				list = workerOrderDao.getByIds(ids);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
		}

		// 如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<WorkerOrder>(0) : list;

		return list;
	}

	/**
	 * 根据主键，删除特定的WorkerOrder记录
	 * 
	 * @param id
	 *            主键id
	 * @param operator
	 *            操作人对象
	 * @return
	 */
	@Transactional
	public ResultInfo<WorkerOrder> delWorkerOrder(String id, Operator operator) {
		ResultInfo<WorkerOrder> resultInfo = new ResultInfo<WorkerOrder>();
		if (id == null || id.length() == 0) { // 传入的主键无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG);
			log.info(Constant.ERR_MSG_INVALID_ARG + " id = " + id);
			return resultInfo;
		}
		try {
			// 调用Dao执行删除，并判断删除语句执行结果
			int count = workerOrderDao.delete(id);
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
	 * 新增一条WorkerOrder记录，执行成功后传入对象及返回对象的主键属性值不为null
	 * 
	 * @param workerOrder
	 *            新增的WorkerOrder数据（如果无特殊需求，新增对象的主键值请保留为null）
	 * @param operator
	 *            操作人对象
	 * @return
	 */
	@Transactional
	public ResultInfo<WorkerOrder> addWorkerOrder(WorkerOrder workerOrder, Operator operator) {
		ResultInfo<WorkerOrder> resultInfo = new ResultInfo<WorkerOrder>();

		if (workerOrder == null) { // 传入参数无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG);
			log.info(Constant.ERR_MSG_INVALID_ARG + " workerOrder = " + workerOrder);
		} else {
			try {
				// 如果传入参数的主键为null，则调用生成主键的方法获取新的主键
				if (workerOrder.getWorkerOrderNo() == null) {
					workerOrder.setWorkerOrderNo(this.generatePK());
				}
				// 如果传入的操作人不为null，则设置新增记录的操作人类型和操作人id
				if (operator != null) {
					workerOrder.setOperatorType(operator.getOperatorType());
					workerOrder.setOperatorId(operator.getOperatorId());
				}

				// 设置创建时间和更新时间为当前时间
				Date now = new Date();
				workerOrder.setCreateTime(now);
				workerOrder.setUpdateTime(now);

				// 填充默认值
				this.fillDefaultValues(workerOrder);

				// 调用Dao执行插入操作
				workerOrderDao.add(workerOrder);
				resultInfo.setCode(Constant.SECCUESS);
				resultInfo.setData(workerOrder);
				// if(resultInfo.getCode().equals(Constant.SECCUESS)){
				// CarStatus carStatus=carStatusDao.get(workerOrder.getCarNo());
				// //以下将车辆使用状态，转为已预定
				// carStatus.setUserageStatus(CarConstant.CAR_USERAGE_STATUS_RESERVED);
				// carStatus.setUpdateTime(new Date());
				// carStatus.setCarNo(workerOrder.getCarNo());
				// carStatusService.updateCarStatus(carStatus, null); //更新车辆状态
				// }
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
	 * 添加调度单时先判断车辆使用状态是否成功
	 * 
	 * @param workerOrder
	 * @param operator
	 * @return
	 */
	@Transactional
	public ResultInfo<WorkerOrder> addWorkerOrderAndJudgeCarStatus(WorkerOrder workerOrder, Operator operator) {
		ResultInfo<WorkerOrder> resultInfo = new ResultInfo<WorkerOrder>();

		if (workerOrder == null) { // 传入参数无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG);
			log.info(Constant.ERR_MSG_INVALID_ARG + " workerOrder = " + workerOrder);
		} else {
			try {
				CarStatus carStatus = new CarStatus();
				// 以下将车辆使用状态，转为已预定

				carStatus.setUserageStatus(CarConstant.CAR_USERAGE_STATUS_RESERVED);
				carStatus.setUpdateTime(new Date());
				carStatus.setCarNo(workerOrder.getCarNo());
				int count = carStatusDao.updateCarUseStatusByCarNo(carStatus); // 更新车辆状态
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

					// 如果传入参数的主键为null，则调用生成主键的方法获取新的主键
					if (workerOrder.getWorkerOrderNo() == null) {
						workerOrder.setWorkerOrderNo(this.generatePK());
					}
					// 如果传入的操作人不为null，则设置新增记录的操作人类型和操作人id
					if (operator != null) {
						workerOrder.setOperatorType(operator.getOperatorType());
						workerOrder.setOperatorId(operator.getOperatorId());
					}

					// 设置创建时间和更新时间为当前时间
					Date now = new Date();
					workerOrder.setCreateTime(now);
					workerOrder.setUpdateTime(now);

					// 填充默认值
					this.fillDefaultValues(workerOrder);

					// 调用Dao执行插入操作
					workerOrderDao.add(workerOrder);
					resultInfo.setCode(Constant.SECCUESS);
					resultInfo.setData(workerOrder);
				} else if (count == 0) {
					resultInfo.setCode(Constant.FAIL);
					resultInfo.setMsg("车辆已占用");
				} else {
					resultInfo.setCode(Constant.FAIL);
				}
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
	 * 根据主键，更新一条WorkerOrder记录
	 * 
	 * @param workerOrder
	 *            更新的WorkerOrder数据，且其主键不能为空
	 * @param operator
	 *            操作人对象
	 * @return
	 */
	@Transactional
	public ResultInfo<WorkerOrder> updateWorkerOrder(WorkerOrder workerOrder, Operator operator) {
		ResultInfo<WorkerOrder> resultInfo = new ResultInfo<WorkerOrder>();

		if (workerOrder == null || workerOrder.getWorkerOrderNo() == null) { // 传入参数无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG);
			log.info(Constant.ERR_MSG_INVALID_ARG + " workerOrder = " + workerOrder);
		} else {
			try {
				// 如果传入的操作人不为null，则设置更新记录的操作人类型和操作人id
				if (operator != null) {
					workerOrder.setOperatorType(operator.getOperatorType());
					workerOrder.setOperatorId(operator.getOperatorId());
				}

				// 设置更新时间为当前时间
				workerOrder.setUpdateTime(new Date());
				if (workerOrder.getCencorStatus() != null) {
					if (workerOrder.getCencorStatus() == 2) {
						workerOrder.setWorkOrderStatus(4);
					}
				}

				// 调用Dao执行更新操作，并判断更新语句执行结果
				int count = workerOrderDao.update(workerOrder);
				if (count > 0) {
					resultInfo.setCode(Constant.SECCUESS);

					// //以下将车辆使用状态，转为调度中
					// CarStatus carStatus = new CarStatus();
					// carStatus.setCarNo(workerOrder.getCarNo());
					// carStatus.setUserageStatus(CarConstant.CAR_USERAGE_STATUS_FREE);
					// carStatus.setUpdateTime(new Date());
					// carStatusDao.update(carStatus); //更新车辆状态

				} else {
					resultInfo.setCode(Constant.FAIL);
				}
				resultInfo.setData(workerOrder);
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
		Date date = new Date();
		long oNo = date.getTime();
		return String.valueOf(oNo);
	}

	/**
	 * 为WorkerOrder对象设置默认值
	 * 
	 * @param obj
	 */
	public void fillDefaultValues(WorkerOrder obj) {
		if (obj != null) {
			if (obj.getWorkOrderStatus() == null) {
				obj.setWorkOrderStatus(0);
			}
		}
	}

	@Override
	public List<WorkerOrder> getWorkerOrderListByWorkerNo(String workerNo, String type) {
		List<WorkerOrder> list = null;
		try {
			// 直接调用Dao方法进行查询
			list = workerOrderDao.getWorkerOrderListByWorkerNo(workerNo, type);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		// 如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<WorkerOrder>(0) : list;
		return list;
	}

	@Override
	public List<Park> getWorkerOrderListByParkAddr(String workerNo, String addrRegion1Name, String addrRegion2Name,
			String addrRegion3Name) {
		List<WorkerOrder> list = null;
		try {
			// 直接调用Dao方法进行查询
			list = workerOrderDao.getWorkerOrderListByParkAddr(workerNo, addrRegion1Name, addrRegion2Name,
					addrRegion3Name);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		// 如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<WorkerOrder>(0) : list;
		List<Park> pkList = new ArrayList<Park>();
		for (WorkerOrder wo : list) {
			Park park = parkDao.get(wo.getStartParkNo());
			pkList.add(park);
		}
		return pkList;
	}

	@Override
	public List<WorkerOrder> getWorkerOrderNumByParkNo(String parkNo, String workerId) {
		List<WorkerOrder> list = null;
		try {
			// 直接调用Dao方法进行查询
			list = workerOrderDao.getWorkerOrderNumByParkNo(parkNo, workerId);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return list;
	}

	@Override
	public Long carExsitWorkerOrder(String carNo) {
		Long num = 0l;
		try {
			// 直接调用Dao方法进行查询
			num = workerOrderDao.carExsitWorkerOrder(carNo);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return num;
	}

	@Override
	public ResultInfo<WorkerOrder> workerParkCarChoose(String carNo, Integer type, String workerNo, Operator operator1,
			String sendReason, String sendReasonPicUrl1, String sendReasonPicUrl2, String sendReasonPicUrl3) {
		WorkerOrder wo = new WorkerOrder();
		wo.setCarNo(carNo);
		Car car = carDao.get(carNo);
		if (car != null) {
			wo.setCarPlateNo(car.getCarPlateNo());
		}
		CarStatus carStatus = carStatusDao.get(carNo);
		if (carStatus != null) {
			Park park = null;
			if (carStatus.getLocationParkNo() != null) {
				park = parkDao.get(carStatus.getLocationParkNo());
			}
			wo.setStartParkNo(carStatus.getLocationParkNo());
			if (park != null) {
				wo.setStartParkName(park.getParkName());
				wo.setTerminalParkName(park.getParkName());
			}
			wo.setTerminalParkNo(carStatus.getLocationParkNo());
		}
		wo.setType(type + "");
		if (type.equals(0)) {
			wo.setMemo("挪车");
		} else if (type.equals(1)) {
			wo.setMemo("车辆清洗");
		} else if (type.equals(2)) {
			wo.setMemo("车辆维修");
		} else if (type.equals(3)) {
			wo.setMemo("车辆保养");
		} else if (type.equals(4)) {
			wo.setMemo("特殊处理");
		}
		Worker worker = workerDao.get(workerNo);
		if (worker != null) {
			wo.setWorkerId(worker.getWorkerNo());
			wo.setWorkerName(worker.getWorkerName());
		}
		wo.setWorkOrderStatus(2);// 调度工单状态 -- 0-未下发 1-已下发 2-调度中 3-已结束4-已取消
		wo.setSendTime(new Date());
		wo.setCencorStatus(1);
		if (sendReason != null && !sendReason.equals("")) {
			wo.setSendReason(sendReason);
		}
		if (sendReasonPicUrl1 != null && !sendReasonPicUrl1.equals("")) {
			wo.setSendReasonPicUrl1(sendReasonPicUrl1);
		}
		if (sendReasonPicUrl2 != null && !sendReasonPicUrl2.equals("")) {
			wo.setSendReasonPicUrl2(sendReasonPicUrl2);
		}
		if (sendReasonPicUrl3 != null && !sendReasonPicUrl3.equals("")) {
			wo.setSendReasonPicUrl3(sendReasonPicUrl3);
		}
		ResultInfo<WorkerOrder> result = addWorkerOrder(wo, operator1);
		// 在用车记录表中添加一条用车记录
		CarRecord cr = new CarRecord();
		if (car != null) {
			cr.setCarBrand(car.getCarBrandName());
			cr.setCarBrandId(car.getCarBrandId());
			cr.setCarModel(car.getCarModelName());
			cr.setCarModelId(car.getCarModelId());
			cr.setCarPlateNo(car.getCarPlateNo());
			cr.setCity(car.getCityName());
			cr.setCityId(car.getCityId());
		}
		if (result != null && result.getData() != null) {
			cr.setDocumentNo(result.getData().getWorkerOrderNo());// 单据号（调度单号）
		}
		if (wo != null) {
			cr.setDriverId(wo.getWorkerId());// 用车人id
			cr.setDriverName(wo.getWorkerName());
			cr.setStartParkNo(wo.getStartParkNo());
			Park park = parkDao.get(wo.getStartParkNo());
			if (park != null) {
				cr.setStartParkName(park.getParkName());
			}
		}
		if (carStatus != null) {
			cr.setStartPower(carStatus.getPower());
		}
		cr.setStartTime(new Date());
		cr.setUseCarType(2);// 调度用车
		// 判断用车记录中是否存在该单的记录，已存在，则修改。不存在，则添加
		if (carRecordService.getCarRecordByDocumentNo(cr.getDocumentNo(), 2) != null) {
			cr.setDocumentNo(cr.getDocumentNo());
			cr.setRecordId(carRecordService.getCarRecordByDocumentNo(cr.getDocumentNo(), 2).getRecordId());
			carRecordService.updateCarRecord(cr, operator1);
		} else {
			carRecordService.addCarRecord(cr, operator1);
		}
		// carStatus.setUserageStatus(3);//使用状态（0，空闲、1，已预占、2，订单中、3，调度中）
		// CarStatus carStatusUp=new CarStatus();
		// carStatusUp.setCarNo(carStatus.getCarNo());
		// carStatusUp.setUserageStatus(3);
		// carStatusService.updateCarStatus(carStatusUp, operator1);

		return result;
	}

	@Override
	public ResultInfo<WorkerOrder> workerOrderFinish(String workerOrderNo, String workerNo, Operator operator) {
		WorkerOrder wo = getWorkerOrder(workerOrderNo);
		wo.setWorkOrderStatus(3);// 已结束
		wo.setWorkerId(workerNo);
		wo.setWorkerName(workerDao.get(workerNo).getWorkerName());
		wo.setFinishTime(new Date());
		if (wo.getStartTime() == null) {
			wo.setStartTime(wo.getFinishTime());
		}
		
		// 用车记录表中修改用车记录
		CarRecord cr = carRecordService.getCarRecordByDocumentNo(workerOrderNo, 2);
		CarStatus carStatus = carStatusDao.get(wo.getCarNo());
		Double power = 0.0;
		if (carStatus.getPower() != null) {
			power = carStatus.getPower();
		} else {
			power = 0.0;
		}
		wo.setPower(carStatus.getPower());
		cr.setFinishPower(power);
		cr.setFinishTime(new Date());
		cr.setTerminalParkNo(wo.getTerminalParkNo());
		cr.setTerminalParkName(wo.getTerminalParkName());
		Double mileage = 0d;
		if (carTripService.getMileageByOrderTime(wo.getCarNo(), wo.getSendTime(), wo.getFinishTime()) != null) {
			mileage = carTripService.getMileageByOrderTime(wo.getCarNo(), wo.getSendTime(), wo.getFinishTime());
		}
		cr.setTotalMileage(ECNumberUtils.roundDoubleWithScale(mileage / 1000, 3));
		carRecordService.updateCarRecord(cr, operator);

		carStatus.setUserageStatus(0);// 使用状态（0，空闲、1，已预占、2，订单中、3，调度中）
		CarStatus carStatusUp = new CarStatus();
		carStatusUp.setCarNo(carStatus.getCarNo());
		carStatusUp.setUserageStatus(0);
		carStatusUp.setLocationParkNo(wo.getTerminalParkNo());
		carStatusService.updateCarStatus(carStatusUp, operator);
		if(carStatus != null && carStatus.getLocationParkNo() != null && !"".equals(carStatus.getLocationParkNo())){
			Park p=parkDao.get(carStatus.getLocationParkNo());
			if(p != null){
				wo.setCurrentAddress(p.getParkName());
			}
			
			if(!"3".equals(wo.getWorkOrderStatus()) ){
				wo.setCurrentLatitude(carStatus.getLatitude()+"");
				wo.setCurrentLongitude(carStatus.getLongitude()+"");
			}
		}else{
			String address = baiduGeocoderApiService.getAddressByGPS(carStatus.getLatitude(), carStatus.getLongitude());
			wo.setCurrentAddress(address);
		}
		return updateWorkerOrder(wo, operator);
	}

	@Override
	public ResultInfo<WorkerOrder> importWorkerOrder(MultipartFile multipartFile, Operator operator) throws Exception {
		ResultInfo<WorkerOrder> resultInfo = new ResultInfo<WorkerOrder>();
		Sheet[] sheet = null;
		jxl.Workbook rwb = null;
		try {
			HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
					.getRequest();
			try {
				String resImgPath = request.getSession().getServletContext().getRealPath("/");
				String filePath = resImgPath + "/xls/";
				File file = new File(filePath);
				if (!file.exists() && !file.isDirectory()) {
					file.mkdirs();
				}
				String filenameReal = filePath + System.currentTimeMillis() + "workerOrder.xls";

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
				for (int j = 0; j < rs.getRows() - 1; j++) {
					if (j >= 2) {
						// continue;
						try {
							// 工单编号，类型，车牌号，调度员编号，调度员，下发时间，起点场站编号，起点，终点场站编号，终点，开始时间，完成时间，审核状态，工单状态
							// workerOrderNo,type,carPlateNo,workerId,workerName
							// sendTime,startParkNo,startParkName,terminalParkNo,terminalParkName,startTime,
							// finishTime，cencorStatus，workOrderStatus
							Cell[] cells = rs.getRow(j);
							String workerOrderNo = "";
							String carPlateNo = "";
							String workerId = "";
							String workerName = "";
							String sendTime = "";
							String startParkNo = "";
							String startParkName = "";
							String terminalParkNo = "";
							String terminalParkName = "";
							String startTime = "";
							String finishTime = "";
							workerOrderNo = cells[0].getContents().trim();
							carPlateNo = cells[2].getContents().trim();
							workerId = cells[3].getContents().trim();
							workerName = cells[4].getContents().trim();
							sendTime = cells[5].getContents().trim();
							startParkNo = cells[6].getContents().trim();
							startParkName = cells[7].getContents().trim();
							terminalParkNo = cells[8].getContents().trim();
							terminalParkName = cells[9].getContents().trim();
							startTime = cells[10].getContents().trim();
							finishTime = cells[11].getContents().trim();
							Integer workerOrderTypeFinal = 0;
							try {
								if (cells[1].getContents().trim().equals("挪车")) {
									workerOrderTypeFinal = 0;
								} else if (cells[1].getContents().trim().equals("洗车")) {
									workerOrderTypeFinal = 1;
								} else if (cells[1].getContents().trim().equals("维修")) {
									workerOrderTypeFinal = 2;
								} else if (cells[1].getContents().trim().equals("保养")) {
									workerOrderTypeFinal = 3;
								}
							} catch (Exception e) {
								throw new XlsImportException("调度类型错误");
							}
							Integer workerOrderCencorFinal = 0;
							try {
								if (cells[12].getContents().trim().equals("未审核")) {
									workerOrderCencorFinal = 0;
								} else if (cells[12].getContents().trim().equals("已审核")) {
									workerOrderCencorFinal = 1;
								} else if (cells[12].getContents().trim().equals("审核未通过")) {
									workerOrderCencorFinal = 2;
								}
							} catch (Exception e) {
								throw new XlsImportException("审核状态类型错误");
							}
							Integer workerOrderStatusFinal = 0;
							try {
								if (cells[13].getContents().trim().equals("未下发")) {
									workerOrderStatusFinal = 0;
								} else if (cells[13].getContents().trim().equals("已下发")) {
									workerOrderStatusFinal = 1;
								} else if (cells[13].getContents().trim().equals("调度中")) {
									workerOrderStatusFinal = 2;
								} else if (cells[13].getContents().trim().equals("已结束")) {
									workerOrderStatusFinal = 3;
								} else if (cells[13].getContents().trim().equals("已取消")) {
									workerOrderStatusFinal = 4;
								}
							} catch (Exception e) {
								throw new XlsImportException("审核状态类型错误");
							}
							WorkerOrder inA = new WorkerOrder();
							inA.setWorkerOrderNo(workerOrderNo);
							inA.setCarPlateNo(carPlateNo);
							inA.setWorkerId(workerId);
							;
							inA.setWorkerName(workerName);
							inA.setSendTime(ECDateUtils.stringTimeToDateTime(sendTime));
							inA.setStartParkNo(startParkNo);
							inA.setStartParkName(startParkName);
							inA.setTerminalParkNo(terminalParkNo);
							inA.setTerminalParkName(terminalParkName);
							inA.setStartTime(ECDateUtils.stringTimeToDateTime(startTime));
							inA.setStartTime(ECDateUtils.stringTimeToDateTime(startTime));
							inA.setWorkerName(workerName);
							inA.setFinishTime(ECDateUtils.stringTimeToDateTime(finishTime));
							inA.setType(workerOrderTypeFinal + "");
							inA.setCencorStatus(workerOrderCencorFinal);
							inA.setWorkOrderStatus(workerOrderStatusFinal);
							// 调度工单信息重复，不录入
							Query query = new Query();
							query.setQ(inA);
							List<WorkerOrder> invoices = workerOrderDao.queryAll(query);
							if (invoices.size() == 0) {
								addWorkerOrder(inA, operator);
							} else {
								throw new XlsImportException("第" + (j - 1) + "行数据重复！");
							}
						} catch (Exception e) {
							e.printStackTrace();
							resultInfo.setCode(Constant.FAIL);
							if (e instanceof XlsImportException)
								throw new XlsImportException(((XlsImportException) e).getErrorMsg());
							else
								throw new XlsImportException("第" + (j - 1) + "行出错！数据有误！");
						}
					}
				}

			}
			resultInfo.setData(null);
			resultInfo.setCode(Constant.SECCUESS);
		} catch (Exception e) {
			if (e instanceof XlsImportException)
				throw new XlsImportException(((XlsImportException) e).getErrorMsg());
			else
				throw new XlsImportException("数据有误！");
		}
		return resultInfo;
	}

	/**
	 * 根据查询条件，统计
	 * 
	 * @param q
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public long countWorkerOrder(Query q) {
		long count = 0;
		try {
			count = workerOrderDao.count(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return count;
	}

	/**
	 * 根据车号，记录调度单开始执行的时间，并同时调度单状态改为“调度中”
	 * 
	 * @param carNo
	 * @param datetime
	 * @return
	 */
	@Transactional
	public ResultInfo<String> startWorkerOrderByCarNo(String carNo, Date datetime) {
		ResultInfo<String> result = new ResultInfo<String>();
		result.setCode(Constant.FAIL);
		result.setMsg("系统错误");
		int n = -1;
		if (carNo != null && datetime != null) {
			n = workerOrderDao.startWorkerOrderByCarNo(carNo, datetime); // 更新相关调度单的调度开始时间，及调度单状态

			if (n > 0) {
				result.setCode(Constant.SECCUESS);
				result.setMsg("");

				try {
					// 以下将车辆使用状态，转为调度中
					CarStatus carStatus = new CarStatus();
					carStatus.setCarNo(carNo);
					carStatus.setUserageStatus(CarConstant.CAR_USERAGE_STATUS_IN_WORKER_ORDER);
					carStatus.setUpdateTime(new Date());
					carStatusService.updateCarStatus(carStatus, null);

				} catch (Exception e) {
					log.error(e.getMessage(), e);
				}

			} else if (n == 0) {
				result.setCode(Constant.FAIL);
				result.setMsg("暂无可更新的记录");
			} else {
				result.setCode(Constant.FAIL);
				result.setMsg("系统错误");
			}

		}

		return result;
	}

	@Override
	public ResultInfo<WorkerOrder> exsitWorkerOrder(String workerNo) {
		// TODO Auto-generated method stub
		ResultInfo<WorkerOrder> result = new ResultInfo<WorkerOrder>();
		WorkerOrder workerOrder = new WorkerOrder();

		List<WorkerOrder> workerOrderList = workerOrderDao.exsitWorkerOrder(workerNo);
		if (workerOrderList != null && workerOrderList.size() > 0) {
			workerOrder = workerOrderList.get(0);
			result.setData(workerOrder);
			result.setCode(Constant.SECCUESS);
			result.setMsg("有调度中的任务");
		} else {
			result.setCode(Constant.FAIL);
			result.setMsg("没有调度中的任务");
		}

		return result;
	}

	@Override
	public ResultInfo<WorkerOrder> esUserWorkerOrder(String workerNo, String carNo) {
		// TODO Auto-generated method stub
		ResultInfo<WorkerOrder> result = new ResultInfo<WorkerOrder>();
		WorkerOrder workerOrder = new WorkerOrder();

		List<WorkerOrder> workerOrderList = workerOrderDao.esUserWorkerOrder(workerNo, carNo);
		if (workerOrderList != null && workerOrderList.size() > 0) {
			workerOrder = workerOrderList.get(0);
			result.setData(workerOrder);
			result.setCode(Constant.SECCUESS);
			result.setMsg(WorkerOrderConstant.IS_IN_WORKER_ORDER_MSG);
		} else {
			result.setCode(Constant.FAIL);
			result.setMsg(WorkerOrderConstant.ISNOT_IN_WORKER_ORDER_MSG);
		}
		return result;
	}

	@Override
	public Long countWorkerOrderPark(Query q) {
		// TODO Auto-generated method stub
		return workerOrderDao.countWorkerOrderPark(q);
	}

	@Override
	public WorkerOrder getWorkerOrderNowByCarNo(String carNo) {
		// TODO Auto-generated method stub
		return workerOrderDao.getWorkerOrderNowByCarNo(carNo);
	}

	@Override
	public Integer getTodoIndexValue() {
		return workerOrderDao.countWorkerOrdersTodoIndexValue();
	}

	@Override
	public List<TaskVo> queryTask(Query q) {
		List<TaskVo> list = null;
		try {
			// 直接调用Dao方法进行查询
			list = workerOrderDao.queryTask(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		// 如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<TaskVo>(0) : list;
		return list;
	}

	@Override
	public WorkerOrderDetailsVo getWorkerOrderDetail(String longitude, String latitude, String workerOrderNo) {
		return workerOrderDao.getWorkerOrderDetail(longitude, latitude, workerOrderNo);
	}

	@Override
	public List<TaskVo> launchTask(Query q) {
		List<TaskVo> list = null;
		try {
			// 直接调用Dao方法进行查询
			list = workerOrderDao.launchTask(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		// 如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<TaskVo>(0) : list;
		return list;
	}
}
