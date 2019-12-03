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
import cn.com.shopec.core.car.dao.CarOperateRecordDao;
import cn.com.shopec.core.car.model.CarOperateRecord;
import cn.com.shopec.core.car.service.CarOperateRecordService;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;

/**
 * 用车记录表 服务实现类
 */
@Service
public class CarOperateRecordServiceImpl implements CarOperateRecordService {

	private static final Log log = LogFactory.getLog(CarOperateRecordServiceImpl.class);

	@Resource
	private CarOperateRecordDao carOperateRecordDao;

	/**
	 * 根据查询条件，查询并返回CarOperateRecord的列表
	 * 
	 * @param q
	 *            查询条件
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<CarOperateRecord> getCarOperateRecordList(Query q) {
		List<CarOperateRecord> list = null;
		try {
			// 直接调用Dao方法进行查询
			list = carOperateRecordDao.queryAll(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		// 如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<CarOperateRecord>(0) : list;
		return list;
	}

	/**
	 * 根据查询条件，分页查询并返回CarOperateRecord的分页结果
	 * 
	 * @param q
	 *            查询条件
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public PageFinder<CarOperateRecord> getCarOperateRecordPagedList(Query q) {
		PageFinder<CarOperateRecord> page = new PageFinder<CarOperateRecord>();

		List<CarOperateRecord> list = null;
		long rowCount = 0L;

		try {
			// 调用dao查询满足条件的分页数据
			list = carOperateRecordDao.pageList(q);
			// 调用dao统计满足条件的记录总数
			rowCount = carOperateRecordDao.count(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		// 如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<CarOperateRecord>(0) : list;

		// 将分页数据和记录总数设置到分页结果对象中
		page.setData(list);
		page.setRowCount(rowCount);

		return page;
	}

	/**
	 * 根据主键，查询并返回一个CarOperateRecord对象
	 * 
	 * @param id
	 *            主键id
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public CarOperateRecord getCarOperateRecord(String id) {
		CarOperateRecord obj = null;
		if (id == null || id.length() == 0) { // 传入的主键无效时直接返回null
			log.info(Constant.ERR_MSG_INVALID_ARG + " id = " + id);
			return obj;
		}
		try {
			// 调用dao，根据主键查询
			obj = carOperateRecordDao.get(id);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		return obj;
	}

	/**
	 * 根据主键数组，查询并返回一组CarOperateRecord对象
	 * 
	 * @param ids
	 *            主键数组，数组中的主键值应当无重复值，如有重复值时，则返回的列表长度可能小于传入的数组长度
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<CarOperateRecord> getCarOperateRecordByIds(String[] ids) {
		List<CarOperateRecord> list = null;
		if (ids == null || ids.length == 0) {
			log.info(Constant.ERR_MSG_INVALID_ARG + " ids is null or empty array.");
		} else {
			try {
				// 调用dao，根据主键数组查询
				list = carOperateRecordDao.getByIds(ids);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
		}

		// 如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<CarOperateRecord>(0) : list;

		return list;
	}

	/**
	 * 根据主键，删除特定的CarOperateRecord记录
	 * 
	 * @param id
	 *            主键id
	 * @param operator
	 *            操作人对象
	 * @return
	 */
	@Transactional
	public ResultInfo<CarOperateRecord> delCarOperateRecord(String id, Operator operator) {
		ResultInfo<CarOperateRecord> resultInfo = new ResultInfo<CarOperateRecord>();
		if (id == null || id.length() == 0) { // 传入的主键无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG);
			log.info(Constant.ERR_MSG_INVALID_ARG + " id = " + id);
			return resultInfo;
		}
		try {
			// 调用Dao执行删除，并判断删除语句执行结果
			int count = carOperateRecordDao.delete(id);
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
	 * 新增一条CarOperateRecord记录，执行成功后传入对象及返回对象的主键属性值不为null
	 * 
	 * @param carOperateRecord
	 *            新增的CarOperateRecord数据（如果无特殊需求，新增对象的主键值请保留为null）
	 * @param operator
	 *            操作人对象
	 * @return
	 */
	@Transactional
	public ResultInfo<CarOperateRecord> addCarOperateRecord(CarOperateRecord carOperateRecord, Operator operator) {
		ResultInfo<CarOperateRecord> resultInfo = new ResultInfo<CarOperateRecord>();

		if (carOperateRecord == null) { // 传入参数无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG);
			log.info(Constant.ERR_MSG_INVALID_ARG + " carOperateRecord = " + carOperateRecord);
		} else {
			try {
				// 如果传入参数的主键为null，则调用生成主键的方法获取新的主键
				if (carOperateRecord.getRecordId() == null) {
					carOperateRecord.setRecordId(this.generatePK());
				}
				// 如果传入的操作人不为null，则设置新增记录的操作人类型和操作人id
				if (operator != null) {
					carOperateRecord.setOperatorType(operator.getOperatorType());
					carOperateRecord.setOperatorId(operator.getOperatorId());
					carOperateRecord.setOperatorName(operator.getOperatorRealName());
				}

				// 设置创建时间和更新时间为当前时间
				Date now = new Date();
				carOperateRecord.setCreateTime(now);

				// 填充默认值
				this.fillDefaultValues(carOperateRecord);

				// 调用Dao执行插入操作
				carOperateRecordDao.add(carOperateRecord);
				resultInfo.setCode(Constant.SECCUESS);
				resultInfo.setData(carOperateRecord);
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
	 * 根据主键，更新一条CarOperateRecord记录
	 * 
	 * @param carOperateRecord
	 *            更新的CarOperateRecord数据，且其主键不能为空
	 * @param operator
	 *            操作人对象
	 * @return
	 */
	@Transactional
	public ResultInfo<CarOperateRecord> updateCarOperateRecord(CarOperateRecord carOperateRecord, Operator operator) {
		ResultInfo<CarOperateRecord> resultInfo = new ResultInfo<CarOperateRecord>();

		if (carOperateRecord == null || carOperateRecord.getRecordId() == null) { // 传入参数无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG);
			log.info(Constant.ERR_MSG_INVALID_ARG + " carOperateRecord = " + carOperateRecord);
		} else {
			try {
				// 如果传入的操作人不为null，则设置更新记录的操作人类型和操作人id
				if (operator != null) {
					carOperateRecord.setOperatorType(operator.getOperatorType());
					carOperateRecord.setOperatorId(operator.getOperatorId());
				}

				// 调用Dao执行更新操作，并判断更新语句执行结果
				int count = carOperateRecordDao.update(carOperateRecord);
				if (count > 0) {
					resultInfo.setCode(Constant.SECCUESS);
				} else {
					resultInfo.setCode(Constant.FAIL);
				}
				resultInfo.setData(carOperateRecord);
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
	 * 为CarOperateRecord对象设置默认值
	 * 
	 * @param obj
	 */
	public void fillDefaultValues(CarOperateRecord obj) {
		if (obj != null) {
		}
	}

}
