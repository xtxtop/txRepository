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
import cn.com.shopec.core.ml.dao.ChargingPileDao;
import cn.com.shopec.core.ml.model.ChargingPile;
import cn.com.shopec.core.ml.service.ChargingPileService;
import cn.com.shopec.core.ml.vo.PileVo;
import cn.com.shopec.core.ml.vo.TerminalDetailsVo;

/**
 * 充电桩 服务实现类
 */
@Service
public class ChargingPileServiceImpl implements ChargingPileService {
	private static final Log log = LogFactory.getLog(ChargingPileServiceImpl.class);
	@Resource
	private ChargingPileDao chargingPileDao;

	/**
	 * 根据查询条件，查询并返回ChargingPile的列表
	 * 
	 * @param q
	 *            查询条件
	 * @return
	 */
	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<ChargingPile> getChargingPileList(Query q) {
		List<ChargingPile> list = null;
		try {
			// 直接调用Dao方法进行查询
			list = chargingPileDao.queryAll(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		// 如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<ChargingPile>(0) : list;
		return list;
	}

	/**
	 * 根据查询条件，分页查询并返回ChargingPile的分页结果
	 * 
	 * @param q
	 *            查询条件
	 * @return
	 */
	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public PageFinder<ChargingPile> getChargingPilePagedList(Query q) {
		PageFinder<ChargingPile> page = new PageFinder<ChargingPile>();
		List<ChargingPile> list = null;
		long rowCount = 0L;
		try {
			// 调用dao查询满足条件的分页数据
			list = chargingPileDao.pageList(q);
			// 调用dao统计满足条件的记录总数
			rowCount = chargingPileDao.count(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		// 如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<ChargingPile>(0) : list;
		// 将分页数据和记录总数设置到分页结果对象中
		page.setData(list);
		page.setRowCount(rowCount);
		return page;
	}

	/**
	 * 根据主键，查询并返回一个ChargingPile对象
	 * 
	 * @param id
	 *            主键id
	 * @return
	 */
	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public ChargingPile getChargingPile(String id) {
		ChargingPile obj = null;
		if (id == null || id.length() == 0) { // 传入的主键无效时直接返回null
			log.info(Constant.ERR_MSG_INVALID_ARG + " id = " + id);
			return obj;
		}
		try {
			// 调用dao，根据主键查询
			obj = chargingPileDao.get(id);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return obj;
	}

	/**
	 * 根据主键数组，查询并返回一组ChargingPile对象
	 * 
	 * @param ids
	 *            主键数组，数组中的主键值应当无重复值，如有重复值时，则返回的列表长度可能小于传入的数组长度
	 * @return
	 */
	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<ChargingPile> getChargingPileByIds(String[] ids) {
		List<ChargingPile> list = null;
		if (ids == null || ids.length == 0) {
			log.info(Constant.ERR_MSG_INVALID_ARG + " ids is null or empty array.");
		} else {
			try {
				// 调用dao，根据主键数组查询
				list = chargingPileDao.getByIds(ids);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
		}
		// 如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<ChargingPile>(0) : list;
		return list;
	}

	/**
	 * 根据主键，删除特定的ChargingPile记录
	 * 
	 * @param id
	 *            主键id
	 * @param operator
	 *            操作人对象
	 * @return
	 */
	@Override
	@Transactional
	public ResultInfo<ChargingPile> delChargingPile(String id, Operator operator) {
		ResultInfo<ChargingPile> resultInfo = new ResultInfo<ChargingPile>();
		if (id == null || id.length() == 0) { // 传入的主键无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG);
			log.info(Constant.ERR_MSG_INVALID_ARG + " id = " + id);
			return resultInfo;
		}
		try {
			// 调用Dao执行删除，并判断删除语句执行结果
			int count = chargingPileDao.delete(id);
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
	 * 新增一条ChargingPile记录，执行成功后传入对象及返回对象的主键属性值不为null
	 * 
	 * @param chargingPile
	 *            新增的ChargingPile数据（如果无特殊需求，新增对象的主键值请保留为null）
	 * @param operator
	 *            操作人对象
	 * @return
	 */
	@Override
	@Transactional
	public ResultInfo<ChargingPile> addChargingPile(ChargingPile chargingPile, Operator operator) {
		ResultInfo<ChargingPile> resultInfo = new ResultInfo<ChargingPile>();
		if (chargingPile == null) { // 传入参数无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG);
			log.info(Constant.ERR_MSG_INVALID_ARG + " chargingPile = " + chargingPile);
		} else {
			try {
				// 如果传入参数的主键为null，则调用生成主键的方法获取新的主键
				if (chargingPile.getChargingPileNo() == null) {
					chargingPile.setChargingPileNo(this.generatePK());
				}
				// 如果传入的操作人不为null，则设置新增记录的操作人类型和操作人id
				if (operator != null) {
					chargingPile.setOperatorType(operator.getOperatorType());
					chargingPile.setOperatorId(operator.getOperatorId());
				}
				// 设置创建时间和更新时间为当前时间
				Date now = new Date();
				chargingPile.setCreateTime(now);
				chargingPile.setUpdateTime(now);
				// 填充默认值
				this.fillDefaultValues(chargingPile);
				// 调用Dao执行插入操作
				chargingPileDao.add(chargingPile);
				resultInfo.setCode(Constant.SECCUESS);
				resultInfo.setData(chargingPile);
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
	 * 根据主键，更新一条ChargingPile记录
	 * 
	 * @param chargingPile
	 *            更新的ChargingPile数据，且其主键不能为空
	 * @param operator
	 *            操作人对象
	 * @return
	 */
	@Override
	@Transactional
	public ResultInfo<ChargingPile> updateChargingPile(ChargingPile chargingPile, Operator operator) {
		ResultInfo<ChargingPile> resultInfo = new ResultInfo<ChargingPile>();
		if (chargingPile == null || chargingPile.getChargingPileNo() == null) { // 传入参数无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG);
			log.info(Constant.ERR_MSG_INVALID_ARG + " chargingPile = " + chargingPile);
		} else {
			try {
				// 如果传入的操作人不为null，则设置更新记录的操作人类型和操作人id
				if (operator != null) {
					chargingPile.setOperatorType(operator.getOperatorType());
					chargingPile.setOperatorId(operator.getOperatorId());
				}
				// 设置更新时间为当前时间
				chargingPile.setUpdateTime(new Date());
				// 调用Dao执行更新操作，并判断更新语句执行结果
				int count = chargingPileDao.update(chargingPile);
				if (count > 0) {
					resultInfo.setCode(Constant.SECCUESS);
				} else {
					resultInfo.setCode(Constant.FAIL);
				}
				resultInfo.setData(chargingPile);
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
	@Override
	public String generatePK() {
		return "CP" + String.valueOf(System.nanoTime());
	}

	/**
	 * 为ChargingPile对象设置默认值
	 * 
	 * @param obj
	 */
	@Override
	public void fillDefaultValues(ChargingPile obj) {
		if (obj != null) {
			if (obj.getStatus() == null) {
				obj.setStatus(1);
			}
			if (obj.getTerminalNo() == null) {
				obj.setTerminalNo("1");
			}
		}
	}

	@Override
	public List<PileVo> pagePileQuery(Query q) {
		List<PileVo> lst = chargingPileDao.pagePileQuery(q);
		return lst == null ? new ArrayList<>() : lst;
	}

	@Override
	public TerminalDetailsVo getTerminalDetails(String chargingPileNo, String parkingLockNo) {
		return chargingPileDao.getTerminalDetails(chargingPileNo, parkingLockNo);
	}
}
