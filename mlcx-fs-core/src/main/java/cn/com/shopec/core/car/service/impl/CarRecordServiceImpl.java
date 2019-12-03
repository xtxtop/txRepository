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
import cn.com.shopec.core.car.dao.CarDao;
import cn.com.shopec.core.car.dao.CarRecordDao;
import cn.com.shopec.core.car.model.Car;
import cn.com.shopec.core.car.model.CarRecord;
import cn.com.shopec.core.car.service.CarRecordService;
import cn.com.shopec.core.car.vo.CarRecordWkVo;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;

/**
 * 用车记录表 服务实现类
 */
@Service
public class CarRecordServiceImpl implements CarRecordService {

	private static final Log log = LogFactory.getLog(CarRecordServiceImpl.class);

	@Resource
	private CarRecordDao carRecordDao;
	@Resource
	private CarDao  carDao;

	/**
	 * 根据查询条件，查询并返回CarRecord的列表
	 * 
	 * @param q
	 *            查询条件
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<CarRecord> getCarRecordList(Query q) {
		List<CarRecord> list = null;
		try {
			// 直接调用Dao方法进行查询
			list = carRecordDao.queryAll(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		// 如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<CarRecord>(0) : list;
		return list;
	}

	/**
	 * 根据查询条件，分页查询并返回CarRecord的分页结果
	 * 
	 * @param q
	 *            查询条件
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public PageFinder<CarRecord> getCarRecordPagedList(Query q) {
		PageFinder<CarRecord> page = new PageFinder<CarRecord>();

		List<CarRecord> list = null;
		long rowCount = 0L;

		try {
			// 调用dao查询满足条件的分页数据
			list = carRecordDao.pageList(q);
			for (CarRecord carRecord : list) {
				Car car=carDao.getCarByPlateNo(carRecord.getCarPlateNo());
				if(car != null){
					carRecord.setCarNo(car.getCarNo());
				}
			}
			// 调用dao统计满足条件的记录总数
			rowCount = carRecordDao.count(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		// 如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<CarRecord>(0) : list;

		// 将分页数据和记录总数设置到分页结果对象中
		page.setData(list);
		page.setRowCount(rowCount);

		return page;
	}

	/**
	 * 根据主键，查询并返回一个CarRecord对象
	 * 
	 * @param id
	 *            主键id
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public CarRecord getCarRecord(String id) {
		CarRecord obj = null;
		if (id == null || id.length() == 0) { // 传入的主键无效时直接返回null
			log.info(Constant.ERR_MSG_INVALID_ARG + " id = " + id);
			return obj;
		}
		try {
			// 调用dao，根据主键查询
			obj = carRecordDao.get(id);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		return obj;
	}

	/**
	 * 根据主键数组，查询并返回一组CarRecord对象
	 * 
	 * @param ids
	 *            主键数组，数组中的主键值应当无重复值，如有重复值时，则返回的列表长度可能小于传入的数组长度
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<CarRecord> getCarRecordByIds(String[] ids) {
		List<CarRecord> list = null;
		if (ids == null || ids.length == 0) {
			log.info(Constant.ERR_MSG_INVALID_ARG + " ids is null or empty array.");
		} else {
			try {
				// 调用dao，根据主键数组查询
				list = carRecordDao.getByIds(ids);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
		}

		// 如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<CarRecord>(0) : list;

		return list;
	}

	/**
	 * 根据主键，删除特定的CarRecord记录
	 * 
	 * @param id
	 *            主键id
	 * @param operator
	 *            操作人对象
	 * @return
	 */
	@Transactional
	public ResultInfo<CarRecord> delCarRecord(String id, Operator operator) {
		ResultInfo<CarRecord> resultInfo = new ResultInfo<CarRecord>();
		if (id == null || id.length() == 0) { // 传入的主键无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG);
			log.info(Constant.ERR_MSG_INVALID_ARG + " id = " + id);
			return resultInfo;
		}
		try {
			// 调用Dao执行删除，并判断删除语句执行结果
			int count = carRecordDao.delete(id);
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
	 * 新增一条CarRecord记录，执行成功后传入对象及返回对象的主键属性值不为null
	 * 
	 * @param carRecord
	 *            新增的CarRecord数据（如果无特殊需求，新增对象的主键值请保留为null）
	 * @param operator
	 *            操作人对象
	 * @return
	 */
	@Transactional
	public ResultInfo<CarRecord> addCarRecord(CarRecord carRecord, Operator operator) {
		ResultInfo<CarRecord> resultInfo = new ResultInfo<CarRecord>();

		if (carRecord == null) { // 传入参数无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG);
			log.info(Constant.ERR_MSG_INVALID_ARG + " carRecord = " + carRecord);
		} else {
			try {
				// 如果传入参数的主键为null，则调用生成主键的方法获取新的主键
				if (carRecord.getRecordId() == null) {
					carRecord.setRecordId(this.generatePK());
				}
				// 如果传入的操作人不为null，则设置新增记录的操作人类型和操作人id
				if (operator != null) {
					carRecord.setOperatorType(operator.getOperatorType());
					carRecord.setOperatorId(operator.getOperatorId());
				}

				// 设置创建时间和更新时间为当前时间
				Date now = new Date();
				carRecord.setCreateTime(now);
				carRecord.setUpdateTime(now);

				// 填充默认值
				this.fillDefaultValues(carRecord);

				// 调用Dao执行插入操作
				carRecordDao.add(carRecord);
				resultInfo.setCode(Constant.SECCUESS);
				resultInfo.setData(carRecord);
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
	 * 根据主键，更新一条CarRecord记录
	 * 
	 * @param carRecord
	 *            更新的CarRecord数据，且其主键不能为空
	 * @param operator
	 *            操作人对象
	 * @return
	 */
	@Transactional
	public ResultInfo<CarRecord> updateCarRecord(CarRecord carRecord, Operator operator) {
		ResultInfo<CarRecord> resultInfo = new ResultInfo<CarRecord>();

		if (carRecord == null || carRecord.getRecordId() == null) { // 传入参数无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG);
			log.info(Constant.ERR_MSG_INVALID_ARG + " carRecord = " + carRecord);
		} else {
			try {
				// 如果传入的操作人不为null，则设置更新记录的操作人类型和操作人id
				if (operator != null) {
					carRecord.setOperatorType(operator.getOperatorType());
					carRecord.setOperatorId(operator.getOperatorId());
				}

				// 设置更新时间为当前时间
				carRecord.setUpdateTime(new Date());

				// 调用Dao执行更新操作，并判断更新语句执行结果
				int count = carRecordDao.update(carRecord);
				if (count > 0) {
					resultInfo.setCode(Constant.SECCUESS);
				} else {
					resultInfo.setCode(Constant.FAIL);
				}
				resultInfo.setData(carRecord);
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
		return String.valueOf(new Date().getTime() * 1000000 + new Random().nextInt(1000000));
	}

	/**
	 * 为CarRecord对象设置默认值
	 * 
	 * @param obj
	 */
	public void fillDefaultValues(CarRecord obj) {
		if (obj != null) {
		}
	}

	@Override
	public CarRecord getCarRecordByDocumentNo(String orderNo, Integer i) {
		CarRecord obj = null;
		if (orderNo == null || orderNo.length() == 0) { // 传入的主键无效时直接返回null
			log.info(Constant.ERR_MSG_INVALID_ARG + " orderNo = " + orderNo);
			return obj;
		}
		try {
			// 调用dao，根据主键查询
			obj = carRecordDao.getCarRecordByDocumentNo(orderNo, i);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		return obj;
	}

	@Override
	public CarRecordWkVo carUsageLog(String carPlateNo) {
		CarRecordWkVo obj = null;
		if (carPlateNo == null || carPlateNo.length() == 0) { // 传入的主键无效时直接返回null
			log.info(Constant.ERR_MSG_INVALID_ARG + " carPlateNo = " + carPlateNo);
			return obj;
		}
		try {
			// 调用dao，根据主键查询
			obj = carRecordDao.carUsageLog(carPlateNo);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		return obj;
	}

}
