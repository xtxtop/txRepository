package cn.com.shopec.core.ml.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import cn.com.shopec.core.ml.dao.COrderDao;
import cn.com.shopec.core.ml.dao.COrderEvaluateDao;
import cn.com.shopec.core.ml.model.COrderEvaluate;
import cn.com.shopec.core.ml.service.COrderEvaluateService;

/**
 * 订单评价表 服务实现类
 */
@Service
public class COrderEvaluateServiceImpl implements COrderEvaluateService {

	private static final Log log = LogFactory.getLog(COrderEvaluateServiceImpl.class);

	private static final Integer EVALUATE_ORDER_FINISH_STATUS = 3;

	private static final String LOCK_TABLE_NAME = "lock_order";

	private static final String CHARGE_TABLE_NAME = "charge_order";
	
	private static final String PARK_TABLE_NAME = "c_park_order";

	@Resource
	private COrderEvaluateDao cOrderEvaluateDao;

	@Resource
	private COrderDao cOrderDao;

	/**
	 * 根据查询条件，查询并返回COrderEvaluate的列表
	 * 
	 * @param q
	 *            查询条件
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<COrderEvaluate> getCOrderEvaluateList(Query q) {
		List<COrderEvaluate> list = null;
		try {
			// 直接调用Dao方法进行查询
			list = cOrderEvaluateDao.queryAll(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		// 如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<COrderEvaluate>(0) : list;
		return list;
	}

	/**
	 * 根据查询条件，分页查询并返回COrderEvaluate的分页结果
	 * 
	 * @param q
	 *            查询条件
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public PageFinder<COrderEvaluate> getCOrderEvaluatePagedList(Query q) {
		PageFinder<COrderEvaluate> page = new PageFinder<COrderEvaluate>();

		List<COrderEvaluate> list = null;
		long rowCount = 0L;

		try {
			// 调用dao查询满足条件的分页数据
			list = cOrderEvaluateDao.pageList(q);
			// 调用dao统计满足条件的记录总数
			rowCount = cOrderEvaluateDao.count(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		// 如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<COrderEvaluate>(0) : list;

		// 将分页数据和记录总数设置到分页结果对象中
		page.setData(list);
		page.setRowCount(rowCount);

		return page;
	}

	/**
	 * 根据主键，查询并返回一个COrderEvaluate对象
	 * 
	 * @param id
	 *            主键id
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public COrderEvaluate getCOrderEvaluate(String id) {
		COrderEvaluate obj = null;
		if (id == null || id.length() == 0) { // 传入的主键无效时直接返回null
			log.info(Constant.ERR_MSG_INVALID_ARG + " id = " + id);
			return obj;
		}
		try {
			// 调用dao，根据主键查询
			obj = cOrderEvaluateDao.get(id);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		return obj;
	}

	/**
	 * 根据主键数组，查询并返回一组COrderEvaluate对象
	 * 
	 * @param ids
	 *            主键数组，数组中的主键值应当无重复值，如有重复值时，则返回的列表长度可能小于传入的数组长度
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<COrderEvaluate> getCOrderEvaluateByIds(String[] ids) {
		List<COrderEvaluate> list = null;
		if (ids == null || ids.length == 0) {
			log.info(Constant.ERR_MSG_INVALID_ARG + " ids is null or empty array.");
		} else {
			try {
				// 调用dao，根据主键数组查询
				list = cOrderEvaluateDao.getByIds(ids);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
		}

		// 如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<COrderEvaluate>(0) : list;

		return list;
	}

	/**
	 * 根据主键，删除特定的COrderEvaluate记录
	 * 
	 * @param id
	 *            主键id
	 * @param operator
	 *            操作人对象
	 * @return
	 */
	@Transactional
	public ResultInfo<COrderEvaluate> delCOrderEvaluate(String id, Operator operator) {
		ResultInfo<COrderEvaluate> resultInfo = new ResultInfo<COrderEvaluate>();
		if (id == null || id.length() == 0) { // 传入的主键无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG);
			log.info(Constant.ERR_MSG_INVALID_ARG + " id = " + id);
			return resultInfo;
		}
		try {
			// 调用Dao执行删除，并判断删除语句执行结果
			int count = cOrderEvaluateDao.delete(id);
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
	 * 新增一条COrderEvaluate记录，执行成功后传入对象及返回对象的主键属性值不为null
	 * 
	 * @param cOrderEvaluate
	 *            新增的COrderEvaluate数据（如果无特殊需求，新增对象的主键值请保留为null）
	 * @param operator
	 *            操作人对象
	 * @return
	 */
	@Transactional
	public ResultInfo<COrderEvaluate> addCOrderEvaluate(COrderEvaluate cOrderEvaluate, Operator operator) {
		ResultInfo<COrderEvaluate> resultInfo = new ResultInfo<COrderEvaluate>();

		if (cOrderEvaluate == null) { // 传入参数无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG);
			log.info(Constant.ERR_MSG_INVALID_ARG + " cOrderEvaluate = " + cOrderEvaluate);
		} else {
			try {
				// 如果传入参数的主键为null，则调用生成主键的方法获取新的主键
				if (cOrderEvaluate.getEvaluateNo() == null) {
					cOrderEvaluate.setEvaluateNo(this.generatePK());
				}
				// 如果传入的操作人不为null，则设置新增记录的操作人类型和操作人id
				if (operator != null) {
					cOrderEvaluate.setOperatorType(operator.getOperatorType());
					cOrderEvaluate.setOperatorId(operator.getOperatorId());
				}

				// 设置创建时间和更新时间为当前时间
				Date now = new Date();
				cOrderEvaluate.setCreateTime(now);
				cOrderEvaluate.setUpdateTime(now);

				// 填充默认值
				this.fillDefaultValues(cOrderEvaluate);

				// 调用Dao执行插入操作
				cOrderEvaluateDao.add(cOrderEvaluate);
				resultInfo.setCode(Constant.SECCUESS);
				resultInfo.setData(cOrderEvaluate);
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
	 * 根据主键，更新一条COrderEvaluate记录
	 * 
	 * @param cOrderEvaluate
	 *            更新的COrderEvaluate数据，且其主键不能为空
	 * @param operator
	 *            操作人对象
	 * @return
	 */
	@Transactional
	public ResultInfo<COrderEvaluate> updateCOrderEvaluate(COrderEvaluate cOrderEvaluate, Operator operator) {
		ResultInfo<COrderEvaluate> resultInfo = new ResultInfo<COrderEvaluate>();

		if (cOrderEvaluate == null || cOrderEvaluate.getEvaluateNo() == null) { // 传入参数无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG);
			log.info(Constant.ERR_MSG_INVALID_ARG + " cOrderEvaluate = " + cOrderEvaluate);
		} else {
			try {
				// 如果传入的操作人不为null，则设置更新记录的操作人类型和操作人id
				if (operator != null) {
					cOrderEvaluate.setOperatorType(operator.getOperatorType());
					cOrderEvaluate.setOperatorId(operator.getOperatorId());
				}

				// 设置更新时间为当前时间
				cOrderEvaluate.setUpdateTime(new Date());

				// 调用Dao执行更新操作，并判断更新语句执行结果
				int count = cOrderEvaluateDao.update(cOrderEvaluate);
				if (count > 0) {
					resultInfo.setCode(Constant.SECCUESS);
				} else {
					resultInfo.setCode(Constant.FAIL);
				}
				resultInfo.setData(cOrderEvaluate);
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
		return Long.valueOf(new Date().getTime() + new Random().nextInt(1000000)).toString();
	}

	/**
	 * 为COrderEvaluate对象设置默认值
	 * 
	 * @param obj
	 */
	public void fillDefaultValues(COrderEvaluate obj) {
		if (obj != null) {

		}
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public ResultInfo<Object> toInsertOrderEvaluate(String orderNo, String evaluateGrade, Operator operator) {
		ResultInfo<Object> resultInfo = new ResultInfo<Object>();
		COrderEvaluate orderEvaluate = new COrderEvaluate();
		COrderEvaluate q = new COrderEvaluate();
		q.setOrderNo(orderNo);
		List<COrderEvaluate> list = cOrderEvaluateDao.queryAll(new Query(q));
		if (list != null && list.size() > 0) {
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg("该订单已经存在评价记录，无需再次评价");
			return resultInfo;
		}
		String memberNo = null;
		Integer orderType = null;
		String tableName;
		if (orderNo.startsWith("L")) {
			memberNo = cOrderEvaluateDao.getMemberNoByLock(orderNo);
			tableName = LOCK_TABLE_NAME;
			orderType = 1;
		} else if (orderNo.startsWith("C")) {
			memberNo = cOrderEvaluateDao.getMemberNoByCharge(orderNo);
			tableName = CHARGE_TABLE_NAME;
			orderType = 0;
		} else if (orderNo.startsWith("PO")) {
			memberNo = cOrderEvaluateDao.getMemberNoByPark(orderNo);
			tableName = PARK_TABLE_NAME;
			orderType = 0;
		} else {
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG);
			return resultInfo;
		}
		if (memberNo == null) {
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG);
			return resultInfo;
		}
		// 获取主键
		try {
			Map<String, Object> map = new HashMap<>();
			map.put("tableName", tableName);
			map.put("status", EVALUATE_ORDER_FINISH_STATUS);
			map.put("orderNo", orderNo);
			int i = cOrderDao.updateEvaluateStatus(map);
			if (i > 0) {
				orderEvaluate.setEvaluateNo(this.generatePK());
				orderEvaluate.setOrderType(orderType);
				orderEvaluate.setMemberNo(memberNo);
				orderEvaluate.setOrderNo(orderNo);
				// 设置创建时间和更新时间为当前时间
				Date now = new Date();
				orderEvaluate.setCreateTime(now);
				orderEvaluate.setUpdateTime(now);
				orderEvaluate.setEvaluateGrade(Double.parseDouble(evaluateGrade));
				if (operator != null) {
					orderEvaluate.setOperatorType(operator.getOperatorType());
					orderEvaluate.setOperatorId(operator.getOperatorId());
				}
				cOrderEvaluateDao.add(orderEvaluate);
			} else {
				resultInfo.setCode(Constant.FAIL);
				resultInfo.setMsg(Constant.ERR_MSG_DATA_ALEARD_EXISTS);
				return resultInfo;
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_EXCEPTION_CATCHED);
			return resultInfo;
		}
		resultInfo.setCode(Constant.SECCUESS);
		resultInfo.setMsg("评价成功");
		return resultInfo;
	}
}
