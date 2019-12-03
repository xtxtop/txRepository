package cn.com.shopec.core.scheduling.service.impl;

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
import cn.com.shopec.common.cache.CommonCacheUtil;
import cn.com.shopec.common.constants.Constant;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.member.common.MemberConstant;
import cn.com.shopec.core.scheduling.dao.WorkerDao;
import cn.com.shopec.core.scheduling.dao.WorkerOrderDao;
import cn.com.shopec.core.scheduling.model.Worker;
import cn.com.shopec.core.scheduling.model.WorkerOrder;
import cn.com.shopec.core.scheduling.service.WorkerService;
import cn.com.shopec.core.system.model.DataDictItem;
import cn.com.shopec.core.system.service.DataDictItemService;

/**
 * 调度员表 服务实现类
 */
@Service
public class WorkerServiceImpl implements WorkerService {

	private static final Log log = LogFactory.getLog(WorkerServiceImpl.class);

	@Resource
	private WorkerDao workerDao;
	@Resource
	private WorkerOrderDao workerOrderDao;
	@Resource
	private CommonCacheUtil commonCacheUtil;
	@Resource
	private DataDictItemService dataDictItemService;

	/**
	 * 根据查询条件，查询并返回Worker的列表
	 * 
	 * @param q
	 *            查询条件
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<Worker> getWorkerList(Query q) {
		List<Worker> list = null;
		try {
			// 直接调用Dao方法进行查询
			list = workerDao.queryAll(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		// 如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<Worker>(0) : list;
		return list;
	}

	/**
	 * 根据查询条件，分页查询并返回Worker的分页结果
	 * 
	 * @param q
	 *            查询条件
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public PageFinder<Worker> getWorkerPagedList(Query q) {
		PageFinder<Worker> page = new PageFinder<Worker>();

		List<Worker> list = null;
		long rowCount = 0L;

		try {
			// 调用dao查询满足条件的分页数据
			list = workerDao.pageList(q);
			// 调用dao统计满足条件的记录总数
			rowCount = workerDao.count(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		// 如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<Worker>(0) : list;

		// 将分页数据和记录总数设置到分页结果对象中
		page.setData(list);
		page.setRowCount(rowCount);

		return page;
	}

	/**
	 * 根据主键，查询并返回一个Worker对象
	 * 
	 * @param id
	 *            主键id
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public Worker getWorker(String id) {
		Worker obj = null;
		if (id == null || id.length() == 0) { // 传入的主键无效时直接返回null
			log.info(Constant.ERR_MSG_INVALID_ARG + " id = " + id);
			return obj;
		}
		try {
			// 调用dao，根据主键查询
			obj = workerDao.get(id);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		return obj;
	}

	/**
	 * 根据主键数组，查询并返回一组Worker对象
	 * 
	 * @param ids
	 *            主键数组，数组中的主键值应当无重复值，如有重复值时，则返回的列表长度可能小于传入的数组长度
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<Worker> getWorkerByIds(String[] ids) {
		List<Worker> list = null;
		if (ids == null || ids.length == 0) {
			log.info(Constant.ERR_MSG_INVALID_ARG + " ids is null or empty array.");
		} else {
			try {
				// 调用dao，根据主键数组查询
				list = workerDao.getByIds(ids);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
		}

		// 如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<Worker>(0) : list;

		return list;
	}

	/**
	 * 根据主键，删除特定的Worker记录
	 * 
	 * @param id
	 *            主键id
	 * @param operator
	 *            操作人对象
	 * @return
	 */
	@Transactional
	public ResultInfo<Worker> delWorker(String id, Operator operator) {
		ResultInfo<Worker> resultInfo = new ResultInfo<Worker>();
		if (id == null || id.length() == 0) { // 传入的主键无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG);
			log.info(Constant.ERR_MSG_INVALID_ARG + " id = " + id);
			return resultInfo;
		}
		try {
			// 调用Dao执行删除，并判断删除语句执行结果
			int count = workerDao.delete(id);
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
	 * 新增一条Worker记录，执行成功后传入对象及返回对象的主键属性值不为null
	 * 
	 * @param worker
	 *            新增的Worker数据（如果无特殊需求，新增对象的主键值请保留为null）
	 * @param operator
	 *            操作人对象
	 * @return
	 */
	@Transactional
	public ResultInfo<Worker> addWorker(Worker worker, Operator operator) {
		ResultInfo<Worker> resultInfo = new ResultInfo<Worker>();

		if (worker == null) { // 传入参数无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG);
			log.info(Constant.ERR_MSG_INVALID_ARG + " worker = " + worker);
		} else {
			try {
				// 如果传入参数的主键为null，则调用生成主键的方法获取新的主键
				if (worker.getWorkerNo() == null) {
					worker.setWorkerNo(this.generatePK());
				}
				// 如果传入的操作人不为null，则设置新增记录的操作人类型和操作人id
				if (operator != null) {
					worker.setOperatorType(operator.getOperatorType());
					worker.setOperatorId(operator.getOperatorId());
				}

				// 设置创建时间和更新时间为当前时间
				Date now = new Date();
				worker.setCreateTime(now);
				worker.setUpdateTime(now);

				// 填充默认值
				this.fillDefaultValues(worker);
				if (worker.getRegionId() != null) {
					DataDictItem dy = dataDictItemService.getDataDictItem(worker.getRegionId());
					if (dy != null) {
						worker.setRegionName(dy.getItemValue());
					}
				}
				// 城市
				DataDictItem dataDictItemcity = dataDictItemService.getDataDictItem(worker.getCityId());
				if (dataDictItemcity != null) {
					worker.setCityName(dataDictItemcity.getItemValue());
				}
				// 调用Dao执行插入操作
				workerDao.add(worker);
				resultInfo.setCode(Constant.SECCUESS);
				resultInfo.setData(worker);
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
	 * 根据主键，更新一条Worker记录
	 * 
	 * @param worker
	 *            更新的Worker数据，且其主键不能为空
	 * @param operator
	 *            操作人对象
	 * @return
	 */
	@Transactional
	public ResultInfo<Worker> updateWorker(Worker worker, Operator operator) {
		ResultInfo<Worker> resultInfo = new ResultInfo<Worker>();

		if (worker == null || worker.getWorkerNo() == null) { // 传入参数无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG);
			log.info(Constant.ERR_MSG_INVALID_ARG + " worker = " + worker);
		} else {
			try {
				// 如果传入的操作人不为null，则设置更新记录的操作人类型和操作人id
				if (operator != null) {
					worker.setOperatorType(operator.getOperatorType());
					worker.setOperatorId(operator.getOperatorId());
				}

				// 设置更新时间为当前时间
				worker.setUpdateTime(new Date());
				// 城市
				DataDictItem dataDictItemcity = dataDictItemService.getDataDictItem(worker.getCityId());
				if (dataDictItemcity != null) {
					worker.setCityName(dataDictItemcity.getItemValue());
				}
				// 片区
				if (worker.getRegionId() != null) {
					DataDictItem dy = dataDictItemService.getDataDictItem(worker.getRegionId());
					if (dy != null) {
						worker.setRegionName(dy.getItemValue());
					}
				}

				// 调用Dao执行更新操作，并判断更新语句执行结果
				int count = workerDao.update(worker);
				if (count > 0) {
					resultInfo.setCode(Constant.SECCUESS);
				} else {
					resultInfo.setCode(Constant.FAIL);
				}
				resultInfo.setData(worker);
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
		return String.valueOf(System.nanoTime());
	}

	/**
	 * 为Worker对象设置默认值
	 * 
	 * @param obj
	 */
	public void fillDefaultValues(Worker obj) {
		if (obj != null) {
			if (obj.getIsAvailable() == null) {
				obj.setIsAvailable(1);
			}
		}
	}

	@Override
	public Worker getWorkerByEmpNo(String empNo) {
		Worker obj = null;
		if (empNo == null || empNo.length() == 0) { // 传入的主键无效时直接返回null
			log.info(Constant.ERR_MSG_INVALID_ARG + " empNo = " + empNo);
			return obj;
		}
		try {
			// 调用dao，根据主键查询
			obj = workerDao.getWorkerByEmpNo(empNo);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		return obj;
	}

	@Override
	public Worker getWorkerBasicInfo(String workerNo) {
		Worker worker = null;
		if (workerNo == null || workerNo.length() == 0) {
			log.info(Constant.ERR_MSG_INVALID_ARG + " workerNo = " + workerNo);
			return worker;
		}
		String cacheKey = "WORKER_BASIC_" + workerNo;
		worker = (Worker) this.commonCacheUtil.getObject(cacheKey); // 首先尝试从缓存取会员基础信息

		if (worker == null) { // 缓存中没有，则改为从db取
			try {
				worker = workerDao.get(workerNo);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
			if (worker != null) { // db取得的数据，存入cache
				boolean res = this.commonCacheUtil.setObject(cacheKey, worker,
						MemberConstant.EXPIRES_TIME_OF_MEMBER_BASIC_INFO_IN_CACHE);
				if (!res) {
					log.error("Save member basic info to cace failed.");
				}
			}
		}
		return worker;
	}

	@Override
	public Worker getWorkerByToken(String token) {
		return workerDao.getWorkerByToken(token);
	}

	@Override
	public List<Worker> getWorkerNs(Query q) {
		List<Worker> list = null;
		try {
			// 直接调用Dao方法进行查询
			list = workerDao.getWorkerNs(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		// 如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<Worker>(0) : list;
		return list;
	}
	
	@Transactional(propagation = Propagation.SUPPORTS)
	public PageFinder<WorkerOrder> pageListWorkerJob(Query q) {
		PageFinder<WorkerOrder> page = new PageFinder<WorkerOrder>();

		List<WorkerOrder> list = null;
		long rowCount = 0L;

		try {
			// 调用dao查询满足条件的分页数据
			list = workerOrderDao.listWorkerOrder(q);
			// 调用dao统计满足条件的记录总数
			rowCount =workerOrderDao.countWokerOrder(q);
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

}
