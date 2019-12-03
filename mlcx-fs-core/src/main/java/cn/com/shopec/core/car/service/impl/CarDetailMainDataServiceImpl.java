package cn.com.shopec.core.car.service.impl;

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
import cn.com.shopec.core.car.dao.CarDetailMainDataDao;
import cn.com.shopec.core.car.model.CarDetailMainData;
import cn.com.shopec.core.car.service.CarDetailMainDataService;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;


/**
 * CarDetailMainData 服务实现类
 */
@Service
public class CarDetailMainDataServiceImpl implements CarDetailMainDataService {

	private static final Log log = LogFactory.getLog(CarDetailMainDataServiceImpl.class);
	
	@Resource
	private CarDetailMainDataDao carDetailMainDataDao;
	

	/**
	 * 根据查询条件，查询并返回CarDetailMainData的列表
	 * @param q 查询条件
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<CarDetailMainData> getCarDetailMainDataList(Query q) {
		List<CarDetailMainData> list = null;
		try {
			//直接调用Dao方法进行查询
			list = carDetailMainDataDao.queryAll(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		//如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<CarDetailMainData>(0) : list;
		return list; 
	}
	
	/**
	 * 根据查询条件，分页查询并返回CarDetailMainData的分页结果
	 * @param q 查询条件
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public PageFinder<CarDetailMainData> getCarDetailMainDataPagedList(Query q) {
		PageFinder<CarDetailMainData> page = new PageFinder<CarDetailMainData>();
		
		List<CarDetailMainData> list = null;
		long rowCount = 0L;
		
		try {
			//调用dao查询满足条件的分页数据
			list = carDetailMainDataDao.pageList(q);
			//调用dao统计满足条件的记录总数
			rowCount = carDetailMainDataDao.count(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		
		//如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<CarDetailMainData>(0) : list;
	
		//将分页数据和记录总数设置到分页结果对象中
		page.setData(list);
		page.setRowCount(rowCount);
		
		return page;
	}	
	
	/**
	 * 根据主键，查询并返回一个CarDetailMainData对象
	 * @param id 主键id
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public CarDetailMainData getCarDetailMainData(String id) {
		CarDetailMainData obj = null;
		if (id == null) { //传入的主键无效时直接返回null
			log.info(Constant.ERR_MSG_INVALID_ARG + " id = " + id);
			return obj;
		}
		try {
			//调用dao，根据主键查询
			obj = carDetailMainDataDao.get(id); 
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		
		return obj;
	}

	/**
	 * 根据主键数组，查询并返回一组CarDetailMainData对象
	 * @param ids 主键数组，数组中的主键值应当无重复值，如有重复值时，则返回的列表长度可能小于传入的数组长度
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<CarDetailMainData> getCarDetailMainDataByIds(String[] ids) {
		List<CarDetailMainData> list = null;
		if (ids == null || ids.length == 0) {
			log.info(Constant.ERR_MSG_INVALID_ARG + " ids is null or empty array.");
		} else {
			try {
				//调用dao，根据主键数组查询
				list = carDetailMainDataDao.getByIds(ids);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
		}
		
		//如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<CarDetailMainData>(0) : list;

		return list;
	}	
	
	/**
	 * 根据主键，删除特定的CarDetailMainData记录
	 * @param id 主键id
	 * @param operator 操作人对象
	 * @return
	 */
	@Transactional
	public ResultInfo<CarDetailMainData> delCarDetailMainData(String id, Operator operator) {
		ResultInfo<CarDetailMainData> resultInfo = new ResultInfo<CarDetailMainData>();
		if (id == null) { //传入的主键无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG );
			log.info(Constant.ERR_MSG_INVALID_ARG + " id = " + id);
			return resultInfo;
		}
		try {
		    //调用Dao执行删除，并判断删除语句执行结果
			int count = carDetailMainDataDao.delete(id);
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
	 * 新增一条CarDetailMainData记录，执行成功后传入对象及返回对象的主键属性值不为null
	 * @param carDetailMainData 新增的CarDetailMainData数据（如果无特殊需求，新增对象的主键值请保留为null）
	 * @param operator 操作人对象
	 * @return
	 */
	@Transactional
	public ResultInfo<CarDetailMainData> addCarDetailMainData(CarDetailMainData carDetailMainData, Operator operator) {
		ResultInfo<CarDetailMainData> resultInfo = new ResultInfo<CarDetailMainData>();
		
		if (carDetailMainData == null) { //传入参数无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG );
			log.info(Constant.ERR_MSG_INVALID_ARG + " carDetailMainData = " + carDetailMainData);
		} else {
			try {
				//如果传入参数的主键为null，则调用生成主键的方法获取新的主键
				if (carDetailMainData.getDeviceSn() == null) {
					carDetailMainData.setDeviceSn(this.generatePK());
				}
				//填充默认值
				this.fillDefaultValues(carDetailMainData);
				
				//调用Dao执行插入操作
				carDetailMainDataDao.add(carDetailMainData);
				resultInfo.setCode(Constant.SECCUESS);
				resultInfo.setData(carDetailMainData);
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
	 * 根据主键，更新一条CarDetailMainData记录
	 * @param carDetailMainData 更新的CarDetailMainData数据，且其主键不能为空
	 * @param operator 操作人对象
	 * @return
	 */
	@Transactional
	public ResultInfo<CarDetailMainData> updateCarDetailMainData(CarDetailMainData carDetailMainData, Operator operator) {
		ResultInfo<CarDetailMainData> resultInfo = new ResultInfo<CarDetailMainData>();
		
		if (carDetailMainData == null || carDetailMainData.getDeviceSn() == null) { //传入参数无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG );
			log.info(Constant.ERR_MSG_INVALID_ARG + " carDetailMainData = " + carDetailMainData);
		} else {
			try {
			
				//调用Dao执行更新操作，并判断更新语句执行结果
				int count = carDetailMainDataDao.update(carDetailMainData);			
				if (count > 0) {
					resultInfo.setCode(Constant.SECCUESS);
				} else {
					resultInfo.setCode(Constant.FAIL);
				}
				resultInfo.setData(carDetailMainData);
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
	 * 为CarDetailMainData对象设置默认值
	 * @param obj
	 */
	public void fillDefaultValues(CarDetailMainData obj) {
		if (obj != null) {
		}
	}

	/**
	 * 判断一个设备序号是否已经存在记录
	 * @param deviceSn
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public boolean isExists(String deviceSn) {
		if (deviceSn == null) { //传入的主键无效时直接返回null
			log.info(Constant.ERR_MSG_INVALID_ARG + " deviceSn = " + deviceSn);
			return false;
		}
		boolean b = false;
		try {
			//调用dao，根据主键查询
			Long res = carDetailMainDataDao.isExists(deviceSn); 
			b = res != null && res == 1;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return b;
	}
	
	/**
	 * 根据设备序号，取最近更新时间信息
	 * @param deviceSn 
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public CarDetailMainData getLastReportingTimeByDeviceSn(String deviceSn) {
		CarDetailMainData obj = null;
		if (deviceSn == null) { //传入的主键无效时直接返回null
			log.info(Constant.ERR_MSG_INVALID_ARG + " deviceSn = " + deviceSn);
			return obj;
		}
		try {
			//调用dao，根据主键查询
			obj = carDetailMainDataDao.getLastReportingTimeByDeviceSn(deviceSn); 
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		
		return obj;
	}
}
