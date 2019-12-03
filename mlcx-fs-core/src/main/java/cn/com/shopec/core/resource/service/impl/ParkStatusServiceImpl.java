package cn.com.shopec.core.resource.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import cn.com.shopec.common.Operator;
import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.common.constants.Constant;
import cn.com.shopec.core.car.model.CarStatus;
import cn.com.shopec.core.car.service.CarStatusService;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.monitor.dao.WarningDao;
import cn.com.shopec.core.monitor.model.Warning;
import cn.com.shopec.core.order.model.Order;
import cn.com.shopec.core.order.service.OrderService;
import cn.com.shopec.core.resource.dao.ParkDao;
import cn.com.shopec.core.resource.dao.ParkStatusDao;
import cn.com.shopec.core.resource.model.Charger;
import cn.com.shopec.core.resource.model.Park;
import cn.com.shopec.core.resource.model.ParkStatus;
import cn.com.shopec.core.resource.model.ParkingSpace;
import cn.com.shopec.core.resource.service.ChargerService;
import cn.com.shopec.core.resource.service.ParkStatusService;
import cn.com.shopec.core.resource.service.ParkingSpaceService;
import cn.com.shopec.core.scheduling.model.WorkerOrder;
import cn.com.shopec.core.scheduling.service.WorkerOrderService;

/**
 * 场站状态表 服务实现类
 */
@Service
public class ParkStatusServiceImpl implements ParkStatusService {

	private static final Log log = LogFactory.getLog(ParkStatusServiceImpl.class);
	
	@Resource
	private ParkStatusDao parkStatusDao;
	@Resource
	private WarningDao warningDao;
	@Resource
	private CarStatusService carStatusService;
	@Resource
	private OrderService orderService;
	@Resource
	private WorkerOrderService workerOrderService;
	@Resource
	private ParkDao parkDao;
	@Resource
	private ParkingSpaceService parkingSpaceService;
	@Resource
	private ChargerService chargerService;
	

	/**
	 * 根据查询条件，查询并返回ParkStatus的列表
	 * @param q 查询条件
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<ParkStatus> getParkStatusList(Query q) {
		List<ParkStatus> list = null;
		try {
			//直接调用Dao方法进行查询
			list = parkStatusDao.queryAll(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		//如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<ParkStatus>(0) : list;
		return list; 
	}
	
	/**
	 * 根据查询条件，分页查询并返回ParkStatus的分页结果
	 * @param q 查询条件
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public PageFinder<ParkStatus> getParkStatusPagedList(Query q) {
		PageFinder<ParkStatus> page = new PageFinder<ParkStatus>();
		
		List<ParkStatus> list = null;
		long rowCount = 0L;
		
		try {
			//调用dao查询满足条件的分页数据
			list = parkStatusDao.pageList(q);
			//调用dao统计满足条件的记录总数
			rowCount = parkStatusDao.count(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		
		//如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<ParkStatus>(0) : list;
	
		//将分页数据和记录总数设置到分页结果对象中
		page.setData(list);
		page.setRowCount(rowCount);
		
		return page;
	}	
	/**
	 * @author lj
	 * 根据查询条件，分页查询并返回ParkStatus的分页结果
	 * @param q 查询条件
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public PageFinder<ParkStatus> getParkStatusPageList(Query q) {
		PageFinder<ParkStatus> page = new PageFinder<ParkStatus>();
		
		List<ParkStatus> listPark = null;
		List<Park> list=null;
		long rowCount = 0L;
		Park park=new Park();
		ParkStatus ps=(ParkStatus) q.getQ();
		if(ps!=null){
			park.setParkNo(ps.getParkNo());
			park.setParkName(ps.getParkName());
		}
		Query qP = new Query(q.getPageNo(),q.getPageSize(),park);
		try {
			//调用dao查询满足条件的分页数据
			list = parkDao.parkPageList(qP);
			listPark=new ArrayList<ParkStatus>();
			for(Park p:list){
				ParkStatus parkStatus=new ParkStatus();
				parkStatus.setParkNo(p.getParkNo());
				parkStatus.setParkName(p.getParkName());
				parkStatus.setCityName(p.getCityName());
				parkStatus.setParkType(p.getParkType());
				ParkingSpace ps1 = new ParkingSpace();
				ps1.setParkNo(p.getParkNo());
				List<ParkingSpace> parkingSpaces = parkingSpaceService.getParkingSpaceList(new Query(ps1));
				p.setParkingSpaceNumber(parkingSpaces.size());
				Charger charger=new Charger();
				charger.setParkNo(p.getParkNo());
				List<Charger> chargers=chargerService.getChargerList(new Query(charger));
				if(chargers.size()>=0){
					p.setChargerNumber(chargers.size());
				}
				parkStatus.setParkingSpaces(p.getParkingSpaceNumber());
				parkStatus.setChargerNumber(p.getChargerNumber());
				//查询警告表中对应的警告子类,未关闭的。
				Query query=new Query();
				Warning warning=new Warning();
				warning.setParkNo(parkStatus.getParkNo());
				warning.setIsClosed(0);
				query.setQ(warning);
				List<Warning> warnings=warningDao.pageList(query);
				String warningSubType[]={};
				for (int i=0;i<warnings.size();i++) {
					if(warnings.get(i).getWarningSubType().length()!=0&&warnings.get(i).getWarningSubType()!=null){
					warningSubType=warnings.get(i).getWarningSubType().split(",");
					}
				}
				parkStatus.setWarningSubType(String.join(",", warningSubType));
				CarStatus carStatus=new CarStatus();
				carStatus.setLocationParkNo(parkStatus.getParkNo());
				query.setQ(carStatus);
				parkStatus.setReservedCarNumber(carStatusService.countParkStatusUserage(query).intValue());//预约车辆数
				Order order=new Order();
				order.setStartParkNo(parkStatus.getParkNo());
				query.setQ(order);
				parkStatus.setCarOut(orderService.countscCar(query).intValue());
				WorkerOrder workerOrder=new WorkerOrder();
				workerOrder.setStartParkNo(parkStatus.getParkNo());
				query.setQ(workerOrder);
				parkStatus.setCarOut(workerOrderService.countWorkerOrderPark(query).intValue()+parkStatus.getCarOut());//驶出
				parkStatus.setFreeParking(parkStatus.getParkingSpaces()-(parkStatus.getReservedCarNumber()-parkStatus.getCarOut()));//空闲车位数
				CarStatus car = new CarStatus();
				car.setLocationParkNo(p.getParkNo());
				List<CarStatus> cars=carStatusService.getCarSpaceShortage(new Query(car));
				if(cars!=null&&cars.size()>0){
					parkStatus.setCarNumber(cars.get(0).getCarSpaceShortage());
				}else{
					parkStatus.setCarNumber(0);
				}
				listPark.add(parkStatus);
			}

			//调用dao统计满足条件的记录总数
			rowCount = parkDao.parkCount(qP);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		
		//如list为null时，则改为返回一个空列表
		listPark = listPark == null ? new ArrayList<ParkStatus>(0) : listPark;
	
		//将分页数据和记录总数设置到分页结果对象中
		page.setData(listPark);
		page.setRowCount(rowCount);
		
		return page;
	}	
	
	/**
	 * 根据主键，查询并返回一个ParkStatus对象
	 * @param id 主键id
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public ParkStatus getParkStatus(String id) {
		ParkStatus obj = null;
		if (id == null || id.length() == 0) { //传入的主键无效时直接返回null
			log.info(Constant.ERR_MSG_INVALID_ARG + " id = " + id);
			return obj;
		}
		try {
			//调用dao，根据主键查询
			obj = parkStatusDao.get(id); 
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		
		return obj;
	}

	/**
	 * 根据主键数组，查询并返回一组ParkStatus对象
	 * @param ids 主键数组，数组中的主键值应当无重复值，如有重复值时，则返回的列表长度可能小于传入的数组长度
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<ParkStatus> getParkStatusByIds(String[] ids) {
		List<ParkStatus> list = null;
		if (ids == null || ids.length == 0) {
			log.info(Constant.ERR_MSG_INVALID_ARG + " ids is null or empty array.");
		} else {
			try {
				//调用dao，根据主键数组查询
				list = parkStatusDao.getByIds(ids);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
		}
		
		//如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<ParkStatus>(0) : list;

		return list;
	}	
	
	/**
	 * 根据主键，删除特定的ParkStatus记录
	 * @param id 主键id
	 * @param operator 操作人对象
	 * @return
	 */
	@Transactional
	public ResultInfo<ParkStatus> delParkStatus(String id, Operator operator) {
		ResultInfo<ParkStatus> resultInfo = new ResultInfo<ParkStatus>();
		if (id == null || id.length() == 0) { //传入的主键无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG );
			log.info(Constant.ERR_MSG_INVALID_ARG + " id = " + id);
			return resultInfo;
		}
		try {
		    //调用Dao执行删除，并判断删除语句执行结果
			int count = parkStatusDao.delete(id);
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
	 * 新增一条ParkStatus记录，执行成功后传入对象及返回对象的主键属性值不为null
	 * @param parkStatus 新增的ParkStatus数据（如果无特殊需求，新增对象的主键值请保留为null）
	 * @param operator 操作人对象
	 * @return
	 */
	@Transactional
	public ResultInfo<ParkStatus> addParkStatus(ParkStatus parkStatus, Operator operator) {
		ResultInfo<ParkStatus> resultInfo = new ResultInfo<ParkStatus>();
		
		if (parkStatus == null) { //传入参数无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG );
			log.info(Constant.ERR_MSG_INVALID_ARG + " parkStatus = " + parkStatus);
		} else {
			try {
				//如果传入参数的主键为null，则调用生成主键的方法获取新的主键
				if (parkStatus.getParkNo() == null) {
					parkStatus.setParkNo(this.generatePK());
				}
				//设置创建时间和更新时间为当前时间
				Date now = new Date();
				parkStatus.setCreateTime(now);
				parkStatus.setUpdateTime(now);
				
				//填充默认值
				this.fillDefaultValues(parkStatus);
				
				//调用Dao执行插入操作
				parkStatusDao.add(parkStatus);
				resultInfo.setCode(Constant.SECCUESS);
				resultInfo.setData(parkStatus);
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
	 * 根据主键，更新一条ParkStatus记录
	 * @param parkStatus 更新的ParkStatus数据，且其主键不能为空
	 * @param operator 操作人对象
	 * @return
	 */
	@Transactional
	public ResultInfo<ParkStatus> updateParkStatus(ParkStatus parkStatus, Operator operator) {
		ResultInfo<ParkStatus> resultInfo = new ResultInfo<ParkStatus>();
		
		if (parkStatus == null || parkStatus.getParkNo() == null) { //传入参数无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG );
			log.info(Constant.ERR_MSG_INVALID_ARG + " parkStatus = " + parkStatus);
		} else {
			try {
				//设置更新时间为当前时间
				parkStatus.setUpdateTime(new Date());
				
				//调用Dao执行更新操作，并判断更新语句执行结果
				int count = parkStatusDao.update(parkStatus);			
				if (count > 0) {
					resultInfo.setCode(Constant.SECCUESS);
				} else {
					resultInfo.setCode(Constant.FAIL);
				}
				resultInfo.setData(parkStatus);
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
	 * @return
	 */
	public String generatePK() {
		return null;
	}
	
	/**
	 * 为ParkStatus对象设置默认值
	 * @param obj
	 */
	public void fillDefaultValues(ParkStatus obj) {
		if (obj != null) {
		}
	}

}
