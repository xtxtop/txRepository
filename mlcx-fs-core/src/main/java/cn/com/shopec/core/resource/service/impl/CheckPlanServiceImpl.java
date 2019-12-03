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
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.resource.dao.CheckPlanDao;
import cn.com.shopec.core.resource.dao.ParkDao;
import cn.com.shopec.core.resource.model.CheckPlan;
import cn.com.shopec.core.resource.service.CheckPlanService;
import cn.com.shopec.core.resource.vo.CheckPlanDetailVo;
import cn.com.shopec.core.resource.vo.CpVo;
import cn.com.shopec.core.resource.vo.GetTaskVo;
import cn.com.shopec.core.system.model.DataDictItem;
import cn.com.shopec.core.system.service.DataDictItemService;

/**
 * 巡检计划表 服务实现类
 */
@Service
public class CheckPlanServiceImpl implements CheckPlanService {

	private static final Log log = LogFactory.getLog(CheckPlanServiceImpl.class);

	@Resource
	private CheckPlanDao checkPlanDao;
	@Resource
	private ParkDao parkDao;
	@Resource
	private DataDictItemService dataDictItemService;

	/**
	 * 根据查询条件，查询并返回CheckPlan的列表
	 * 
	 * @param q
	 *            查询条件
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<CheckPlan> getCheckPlanList(Query q) {
		List<CheckPlan> list = null;
		try {
			// 已删除的不查出
			CheckPlan checkPlan = (CheckPlan) q.getQ();
			if (checkPlan != null) {
				checkPlan.setIsDeleted(Constant.DEL_STATE_NO);
			}

			// 直接调用Dao方法进行查询
			list = checkPlanDao.queryAll(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		// 如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<CheckPlan>(0) : list;
		return list;
	}

	/**
	 * 根据查询条件，分页查询并返回CheckPlan的分页结果
	 * 
	 * @param q
	 *            查询条件
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public PageFinder<CheckPlan> getCheckPlanPagedList(Query q) {
		PageFinder<CheckPlan> page = new PageFinder<CheckPlan>();

		List<CheckPlan> list = null;
		long rowCount = 0L;

		try {
			// 已删除的不查出
			CheckPlan checkPlan = (CheckPlan) q.getQ();
			if (checkPlan != null) {
				checkPlan.setIsDeleted(Constant.DEL_STATE_NO);
			}

			// 调用dao查询满足条件的分页数据
			list = checkPlanDao.pageList(q);
			// 调用dao统计满足条件的记录总数
			rowCount = checkPlanDao.count(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		// 如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<CheckPlan>(0) : list;

		// 将分页数据和记录总数设置到分页结果对象中
		page.setData(list);
		page.setRowCount(rowCount);

		return page;
	}

	/**
	 * 根据主键，查询并返回一个CheckPlan对象
	 * 
	 * @param id
	 *            主键id
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public CheckPlan getCheckPlan(String id) {
		CheckPlan obj = null;
		if (id == null || id.length() == 0) { // 传入的主键无效时直接返回null
			log.info(Constant.ERR_MSG_INVALID_ARG + " id = " + id);
			return obj;
		}
		try {
			// 调用dao，根据主键查询
			obj = checkPlanDao.get(id);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		return obj;
	}

	/**
	 * 根据主键数组，查询并返回一组CheckPlan对象
	 * 
	 * @param ids
	 *            主键数组，数组中的主键值应当无重复值，如有重复值时，则返回的列表长度可能小于传入的数组长度
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<CheckPlan> getCheckPlanByIds(String[] ids) {
		List<CheckPlan> list = null;
		if (ids == null || ids.length == 0) {
			log.info(Constant.ERR_MSG_INVALID_ARG + " ids is null or empty array.");
		} else {
			try {
				// 调用dao，根据主键数组查询
				list = checkPlanDao.getByIds(ids);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
		}

		// 如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<CheckPlan>(0) : list;

		return list;
	}

	/**
	 * 根据主键，删除特定的CheckPlan记录
	 * 
	 * @param id
	 *            主键id
	 * @param operator
	 *            操作人对象
	 * @return
	 */
	@Transactional
	public ResultInfo<CheckPlan> delCheckPlan(String id, Operator operator) {
		ResultInfo<CheckPlan> resultInfo = new ResultInfo<CheckPlan>();
		if (id == null || id.length() == 0) { // 传入的主键无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG);
			log.info(Constant.ERR_MSG_INVALID_ARG + " id = " + id);
			return resultInfo;
		}
		try {
			// 逻辑删除，实际上执行update语句，以下设置待更新记录的主键、删除标识、更新时间、操作人信息等
			CheckPlan checkPlan = new CheckPlan();
			checkPlan.setCheckPlanNo(id);
			checkPlan.setIsDeleted(Constant.DEL_STATE_YES);
			checkPlan.setUpdateTime(new Date());
			if (operator != null) { // 最近操作人
				checkPlan.setOperatorType(operator.getOperatorType());
				checkPlan.setOperatorId(operator.getOperatorId());
			}

			// 调用Dao执行更新操作，并判断更新语句执行结果
			int count = checkPlanDao.update(checkPlan);
			if (count > 0) {
				resultInfo.setCode(Constant.SECCUESS);
			} else {
				resultInfo.setCode(Constant.FAIL);
			}
			resultInfo.setData(checkPlan);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_EXCEPTION_CATCHED);
		}
		return resultInfo;
	}

	/**
	 * 新增一条CheckPlan记录，执行成功后传入对象及返回对象的主键属性值不为null
	 * 
	 * @param checkPlan
	 *            新增的CheckPlan数据（如果无特殊需求，新增对象的主键值请保留为null）
	 * @param operator
	 *            操作人对象
	 * @return
	 */
	@Transactional
	public ResultInfo<CheckPlan> addCheckPlan(CheckPlan checkPlan, Operator operator) {
		ResultInfo<CheckPlan> resultInfo = new ResultInfo<CheckPlan>();

		if (checkPlan == null) { // 传入参数无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG);
			log.info(Constant.ERR_MSG_INVALID_ARG + " checkPlan = " + checkPlan);
		} else {
			try {
				// 如果传入参数的主键为null，则调用生成主键的方法获取新的主键
				if (checkPlan.getCheckPlanNo() == null) {
					checkPlan.setCheckPlanNo(this.generatePK());
				}
				// 如果传入的操作人不为null，则设置新增记录的操作人类型和操作人id
				if (operator != null) {
					checkPlan.setOperatorType(operator.getOperatorType());
					checkPlan.setOperatorId(operator.getOperatorId());
				}

				// 设置创建时间和更新时间为当前时间
				Date now = new Date();
				checkPlan.setCreateTime(now);
				checkPlan.setUpdateTime(now);

				// 填充默认值
				this.fillDefaultValues(checkPlan);
				// 城市
				DataDictItem dataDictItemcity = dataDictItemService.getDataDictItem(checkPlan.getCityId());
				if (dataDictItemcity != null) {
					checkPlan.setCityName(dataDictItemcity.getItemValue());
				}
				// 调用Dao执行插入操作
				checkPlanDao.add(checkPlan);
				resultInfo.setCode(Constant.SECCUESS);
				resultInfo.setData(checkPlan);
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
	 * 根据主键，更新一条CheckPlan记录
	 * 
	 * @param checkPlan
	 *            更新的CheckPlan数据，且其主键不能为空
	 * @param operator
	 *            操作人对象
	 * @return
	 */
	@Transactional
	public ResultInfo<CheckPlan> updateCheckPlan(CheckPlan checkPlan, Operator operator) {
		ResultInfo<CheckPlan> resultInfo = new ResultInfo<CheckPlan>();

		if (checkPlan == null || checkPlan.getCheckPlanNo() == null) { // 传入参数无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG);
			log.info(Constant.ERR_MSG_INVALID_ARG + " checkPlan = " + checkPlan);
		} else {
			try {
				// 如果传入的操作人不为null，则设置更新记录的操作人类型和操作人id
				if (operator != null) {
					checkPlan.setOperatorType(operator.getOperatorType());
					checkPlan.setOperatorId(operator.getOperatorId());
				}

				// 设置更新时间为当前时间
				checkPlan.setUpdateTime(new Date());

				// 调用Dao执行更新操作，并判断更新语句执行结果
				int count = checkPlanDao.update(checkPlan);
				if (count > 0) {
					resultInfo.setCode(Constant.SECCUESS);
				} else {
					resultInfo.setCode(Constant.FAIL);
				}
				resultInfo.setData(checkPlan);
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
	 * 为CheckPlan对象设置默认值
	 * 
	 * @param obj
	 */
	public void fillDefaultValues(CheckPlan obj) {
		if (obj != null) {
			if (obj.getPlanStatus() == null) {
				obj.setPlanStatus(1);
			}
			if (obj.getIsDeleted() == null) {
				obj.setIsDeleted(0);
			}
		}
	}

	@Override
	public List<CheckPlan> getCheckPlanListByWorkerNo(String workerNo, Integer planStatus) {
		List<CheckPlan> list = null;
		try {
			// 直接调用Dao方法进行查询
			list = checkPlanDao.getCheckPlanListByWorkerNo(workerNo, planStatus);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		// 如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<CheckPlan>(0) : list;
		return list;
	}

	@Override
	public List<CpVo> queryAllNs(Query q) {
		List<CpVo> list = null;
		try {
			// 已删除的不查出
			GetTaskVo checkPlan = (GetTaskVo) q.getQ();
			if (checkPlan != null) {
				checkPlan.setIsDeleted(Constant.DEL_STATE_NO);
			}

			// 直接调用Dao方法进行查询
			list = checkPlanDao.queryAllNs(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		// 如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<CpVo>(0) : list;
		return list;
	}

	@Override
	public CheckPlanDetailVo getCheckPlanDetails(String longitude, String latitude, String checkPlanNo) {
		return checkPlanDao.getCheckPlanDetails(longitude, latitude, checkPlanNo);
	}

}
