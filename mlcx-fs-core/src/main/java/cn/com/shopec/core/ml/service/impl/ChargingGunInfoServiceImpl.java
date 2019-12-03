package cn.com.shopec.core.ml.service.impl;

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
import cn.com.shopec.core.ml.dao.ChargingGunInfoDao;
import cn.com.shopec.core.ml.model.ChargingGunInfo;
import cn.com.shopec.core.ml.service.ChargingGunInfoService;
import cn.com.shopec.core.ml.vo.ChargingGunInfoVo;

/**
 * 充电枪 服务实现类
 */
@Service
public class ChargingGunInfoServiceImpl implements ChargingGunInfoService {

	private static final Log log = LogFactory.getLog(ChargingGunInfoServiceImpl.class);

	@Resource
	private ChargingGunInfoDao chargingGunInfoDao;

	/**
	 * 根据查询条件，查询并返回ChargingGunInfo的列表
	 * 
	 * @param q
	 *            查询条件
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<ChargingGunInfo> getChargingGunInfoList(Query q) {
		List<ChargingGunInfo> list = null;
		try {
			// 直接调用Dao方法进行查询
			list = chargingGunInfoDao.queryAll(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		// 如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<ChargingGunInfo>(0) : list;
		return list;
	}

	/**
	 * 根据查询条件，分页查询并返回ChargingGunInfo的分页结果
	 * 
	 * @param q
	 *            查询条件
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public PageFinder<ChargingGunInfo> getChargingGunInfoPagedList(Query q) {
		PageFinder<ChargingGunInfo> page = new PageFinder<ChargingGunInfo>();

		List<ChargingGunInfo> list = null;
		long rowCount = 0L;

		try {
			// 调用dao查询满足条件的分页数据
			list = chargingGunInfoDao.pageList(q);
			// 调用dao统计满足条件的记录总数
			rowCount = chargingGunInfoDao.count(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		// 如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<ChargingGunInfo>(0) : list;

		// 将分页数据和记录总数设置到分页结果对象中
		page.setData(list);
		page.setRowCount(rowCount);

		return page;
	}

	/**
	 * 根据主键，查询并返回一个ChargingGunInfo对象
	 * 
	 * @param id
	 *            主键id
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public ChargingGunInfo getChargingGunInfo(String id) {
		ChargingGunInfo obj = null;
		if (id == null || id.length() == 0) { // 传入的主键无效时直接返回null
			log.info(Constant.ERR_MSG_INVALID_ARG + " id = " + id);
			return obj;
		}
		try {
			// 调用dao，根据主键查询
			obj = chargingGunInfoDao.get(id);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		return obj;
	}

	/**
	 * 根据主键数组，查询并返回一组ChargingGunInfo对象
	 * 
	 * @param ids
	 *            主键数组，数组中的主键值应当无重复值，如有重复值时，则返回的列表长度可能小于传入的数组长度
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<ChargingGunInfo> getChargingGunInfoByIds(String[] ids) {
		List<ChargingGunInfo> list = null;
		if (ids == null || ids.length == 0) {
			log.info(Constant.ERR_MSG_INVALID_ARG + " ids is null or empty array.");
		} else {
			try {
				// 调用dao，根据主键数组查询
				list = chargingGunInfoDao.getByIds(ids);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
		}

		// 如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<ChargingGunInfo>(0) : list;

		return list;
	}

	/**
	 * 根据主键，删除特定的ChargingGunInfo记录
	 * 
	 * @param id
	 *            主键id
	 * @param operator
	 *            操作人对象
	 * @return
	 */
	@Transactional
	public ResultInfo<ChargingGunInfo> delChargingGunInfo(String id, Operator operator) {
		ResultInfo<ChargingGunInfo> resultInfo = new ResultInfo<ChargingGunInfo>();
		if (id == null || id.length() == 0) { // 传入的主键无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG);
			log.info(Constant.ERR_MSG_INVALID_ARG + " id = " + id);
			return resultInfo;
		}
		try {
			// 调用Dao执行删除，并判断删除语句执行结果
			int count = chargingGunInfoDao.delete(id);
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
	 * 新增一条ChargingGunInfo记录，执行成功后传入对象及返回对象的主键属性值不为null
	 * 
	 * @param chargingGunInfo
	 *            新增的ChargingGunInfo数据（如果无特殊需求，新增对象的主键值请保留为null）
	 * @param operator
	 *            操作人对象
	 * @return
	 */
	@Transactional
	public ResultInfo<ChargingGunInfo> addChargingGunInfo(ChargingGunInfo chargingGunInfo, Operator operator) {
		ResultInfo<ChargingGunInfo> resultInfo = new ResultInfo<ChargingGunInfo>();

		if (chargingGunInfo == null) { // 传入参数无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG);
			log.info(Constant.ERR_MSG_INVALID_ARG + " chargingGunInfo = " + chargingGunInfo);
		} else {
			try {
				// 如果传入参数的主键为null，则调用生成主键的方法获取新的主键
				if (chargingGunInfo.getChargingGunNo() == null) {
					chargingGunInfo.setChargingGunNo(this.generatePK());
				}
				// 如果传入的操作人不为null，则设置新增记录的操作人类型和操作人id
				if (operator != null) {
					chargingGunInfo.setOperatorType(operator.getOperatorType());
					chargingGunInfo.setOperatorId(operator.getOperatorId());
				}

				// 设置创建时间和更新时间为当前时间
				Date now = new Date();
				chargingGunInfo.setCreateTime(now);
				chargingGunInfo.setUpdateTime(now);

				// 填充默认值
				this.fillDefaultValues(chargingGunInfo);

				// 调用Dao执行插入操作
				chargingGunInfoDao.add(chargingGunInfo);
				resultInfo.setCode(Constant.SECCUESS);
				resultInfo.setData(chargingGunInfo);
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
	 * 根据主键，更新一条ChargingGunInfo记录
	 * 
	 * @param chargingGunInfo
	 *            更新的ChargingGunInfo数据，且其主键不能为空
	 * @param operator
	 *            操作人对象
	 * @return
	 */
	@Transactional
	public ResultInfo<ChargingGunInfo> updateChargingGunInfo(ChargingGunInfo chargingGunInfo, Operator operator) {
		ResultInfo<ChargingGunInfo> resultInfo = new ResultInfo<ChargingGunInfo>();

		if (chargingGunInfo == null || chargingGunInfo.getChargingGunNo() == null) { // 传入参数无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG);
			log.info(Constant.ERR_MSG_INVALID_ARG + " chargingGunInfo = " + chargingGunInfo);
		} else {
			try {
				// 如果传入的操作人不为null，则设置更新记录的操作人类型和操作人id
				if (operator != null) {
					chargingGunInfo.setOperatorType(operator.getOperatorType());
					chargingGunInfo.setOperatorId(operator.getOperatorId());
				}

				// 设置更新时间为当前时间
				chargingGunInfo.setUpdateTime(new Date());

				// 调用Dao执行更新操作，并判断更新语句执行结果
				int count = chargingGunInfoDao.update(chargingGunInfo);
				if (count > 0) {
					resultInfo.setCode(Constant.SECCUESS);
				} else {
					resultInfo.setCode(Constant.FAIL);
				}
				resultInfo.setData(chargingGunInfo);
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

		return "CDQ" + String.valueOf(System.nanoTime());
	}

	/**
	 * 为ChargingGunInfo对象设置默认值
	 * 
	 * @param obj
	 */
	public void fillDefaultValues(ChargingGunInfo obj) {
		if (obj != null) {
		}
	}
	/**
	 * 枪列表信息  VO
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public PageFinder<ChargingGunInfoVo> getGunList(Query q) {
		PageFinder<ChargingGunInfoVo> page = new PageFinder<ChargingGunInfoVo>();

		List<ChargingGunInfoVo> list = null;
		long rowCount = 0L;

		try {
			// 调用dao查询满足条件的分页数据
			list = chargingGunInfoDao.getGunList(q);
			// 调用dao统计满足条件的记录总数
			rowCount = chargingGunInfoDao.getGunListCount(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		// 如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<ChargingGunInfoVo>(0) : list;

		// 将分页数据和记录总数设置到分页结果对象中
		page.setData(list);
		page.setRowCount(rowCount);

		return page;
	}

	/**
	 * 枪详情  VO
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public ChargingGunInfoVo getGunListNo(String id) {
		ChargingGunInfoVo obj = null;
		if (id == null || id.length() == 0) { // 传入的主键无效时直接返回null
			log.info(Constant.ERR_MSG_INVALID_ARG + " id = " + id);
			return obj;
		}
		try {
			// 调用dao，根据主键查询
			obj = chargingGunInfoDao.getGunListNo(id);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		return obj;
	}

}
