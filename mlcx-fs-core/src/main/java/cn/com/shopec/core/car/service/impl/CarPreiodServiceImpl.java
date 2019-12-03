package cn.com.shopec.core.car.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

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
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.car.dao.CarPreiodDao;
import cn.com.shopec.core.car.model.CarPreiod;
import cn.com.shopec.core.car.service.CarPreiodService;

/**
 * 车辆年代表 服务实现类
 */
@Service
public class CarPreiodServiceImpl implements CarPreiodService {

	private static final Log log = LogFactory.getLog(CarPreiodServiceImpl.class);
	
	@Resource
	private CarPreiodDao carPreiodDao;
	

	/**
	 * 根据查询条件，查询并返回CarPreiod的列表
	 * @param q 查询条件
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<CarPreiod> getCarPreiodList(Query q) {
		List<CarPreiod> list = null;
		try {
			//直接调用Dao方法进行查询
			list = carPreiodDao.queryAll(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		//如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<CarPreiod>(0) : list;
		return list; 
	}
	
	/**
	 * 根据查询条件，分页查询并返回CarPreiod的分页结果
	 * @param q 查询条件
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public PageFinder<CarPreiod> getCarPreiodPagedList(Query q) {
		PageFinder<CarPreiod> page = new PageFinder<CarPreiod>();
		
		List<CarPreiod> list = null;
		long rowCount = 0L;
		
		try {
			//调用dao查询满足条件的分页数据
			list = carPreiodDao.pageList(q);
			//调用dao统计满足条件的记录总数
			rowCount = carPreiodDao.count(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		
		//如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<CarPreiod>(0) : list;
	
		//将分页数据和记录总数设置到分页结果对象中
		page.setData(list);
		page.setRowCount(rowCount);
		
		return page;
	}	
	
	/**
	 * 根据主键，查询并返回一个CarPreiod对象
	 * @param id 主键id
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public CarPreiod getCarPreiod(String id) {
		CarPreiod obj = null;
		if (id == null || id.length() == 0) { //传入的主键无效时直接返回null
			log.info(Constant.ERR_MSG_INVALID_ARG + " id = " + id);
			return obj;
		}
		try {
			//调用dao，根据主键查询
			obj = carPreiodDao.get(id); 
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		
		return obj;
	}

	/**
	 * 根据主键数组，查询并返回一组CarPreiod对象
	 * @param ids 主键数组，数组中的主键值应当无重复值，如有重复值时，则返回的列表长度可能小于传入的数组长度
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<CarPreiod> getCarPreiodByIds(String[] ids) {
		List<CarPreiod> list = null;
		if (ids == null || ids.length == 0) {
			log.info(Constant.ERR_MSG_INVALID_ARG + " ids is null or empty array.");
		} else {
			try {
				//调用dao，根据主键数组查询
				list = carPreiodDao.getByIds(ids);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
		}
		
		//如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<CarPreiod>(0) : list;

		return list;
	}	
	
	/**
	 * 根据主键，删除特定的CarPreiod记录
	 * @param id 主键id
	 * @param operator 操作人对象
	 * @return
	 */
	@Transactional
	public ResultInfo<CarPreiod> delCarPreiod(String id, Operator operator) {
		ResultInfo<CarPreiod> resultInfo = new ResultInfo<CarPreiod>();
		if (id == null || id.length() == 0) { //传入的主键无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG );
			log.info(Constant.ERR_MSG_INVALID_ARG + " id = " + id);
			return resultInfo;
		}
		try {
		    //调用Dao执行删除，并判断删除语句执行结果
			int count = carPreiodDao.delete(id);
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
	 * 新增一条CarPreiod记录，执行成功后传入对象及返回对象的主键属性值不为null
	 * @param carPreiod 新增的CarPreiod数据（如果无特殊需求，新增对象的主键值请保留为null）
	 * @param operator 操作人对象
	 * @return
	 */
	@Transactional
	public ResultInfo<CarPreiod> addCarPreiod(CarPreiod carPreiod, Operator operator) {
		ResultInfo<CarPreiod> resultInfo = new ResultInfo<CarPreiod>();
		
		if (carPreiod == null) { //传入参数无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG );
			log.info(Constant.ERR_MSG_INVALID_ARG + " carPreiod = " + carPreiod);
		} else {
			try {
				//如果传入参数的主键为null，则调用生成主键的方法获取新的主键
				if (carPreiod.getCarPeriodId() == null) {
					carPreiod.setCarPeriodId(this.generatePK());
				}
				//如果传入的操作人不为null，则设置新增记录的操作人类型和操作人id
				if (operator != null) {
					carPreiod.setOperatorType(operator.getOperatorType());
					carPreiod.setOperatorId(operator.getOperatorId());
				}
				
				//设置创建时间和更新时间为当前时间
				Date now = new Date();
				carPreiod.setCreateTime(now);
				carPreiod.setUpdateTime(now);
				
				//填充默认值
				this.fillDefaultValues(carPreiod);
				
				//调用Dao执行插入操作
				carPreiodDao.add(carPreiod);
				resultInfo.setCode(Constant.SECCUESS);
				resultInfo.setData(carPreiod);
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
	 * 根据主键，更新一条CarPreiod记录
	 * @param carPreiod 更新的CarPreiod数据，且其主键不能为空
	 * @param operator 操作人对象
	 * @return
	 */
	@Transactional
	public ResultInfo<CarPreiod> updateCarPreiod(CarPreiod carPreiod, Operator operator) {
		ResultInfo<CarPreiod> resultInfo = new ResultInfo<CarPreiod>();
		
		if (carPreiod == null || carPreiod.getCarPeriodId() == null) { //传入参数无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG );
			log.info(Constant.ERR_MSG_INVALID_ARG + " carPreiod = " + carPreiod);
		} else {
			try {
				//如果传入的操作人不为null，则设置更新记录的操作人类型和操作人id
				if (operator != null) {
					carPreiod.setOperatorType(operator.getOperatorType());
					carPreiod.setOperatorId(operator.getOperatorId());
				}
				
				//设置更新时间为当前时间
				carPreiod.setUpdateTime(new Date());
				
				//调用Dao执行更新操作，并判断更新语句执行结果
				int count = carPreiodDao.update(carPreiod);			
				if (count > 0) {
					resultInfo.setCode(Constant.SECCUESS);
				} else {
					resultInfo.setCode(Constant.FAIL);
				}
				resultInfo.setData(carPreiod);
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
		return String.valueOf(new Date().getTime() * 1000000 + new Random().nextInt(1000000));
	}
	
	/**
	 * 为CarPreiod对象设置默认值
	 * @param obj
	 */
	public void fillDefaultValues(CarPreiod obj) {
		if (obj != null) {
		}
	}

}
