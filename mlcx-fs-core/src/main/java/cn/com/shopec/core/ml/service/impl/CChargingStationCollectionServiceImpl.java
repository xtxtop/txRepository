package cn.com.shopec.core.ml.service.impl;

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
import cn.com.shopec.core.member.dao.MemberDao;
import cn.com.shopec.core.member.model.Member;
import cn.com.shopec.core.ml.dao.CChargingStationCollectionDao;
import cn.com.shopec.core.ml.dao.ChargingStationDao;
import cn.com.shopec.core.ml.model.CChargingStationCollection;
import cn.com.shopec.core.ml.model.ChargingStation;
import cn.com.shopec.core.ml.service.CChargingStationCollectionService;
import cn.com.shopec.core.ml.vo.ChargingCollectionVo;

@Service
public class CChargingStationCollectionServiceImpl implements CChargingStationCollectionService {
	@Resource
	private CChargingStationCollectionDao cChargingStationCollectionDao;
	@Resource
	private ChargingStationDao chargingStationDao;
	@Resource
	private MemberDao memberDao;
	private static final Log log = LogFactory.getLog(ChargingGunInfoServiceImpl.class);
	private static final Integer COLLECT_STATUS = 1;

	/**
	 * 根据查询条件，查询并返回CChargingStationCollection的列表
	 *
	 * @param q
	 *            查询条件
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<CChargingStationCollection> getCChargingStationCollectionList(Query q) {
		List<CChargingStationCollection> list = null;
		try {
			// 直接调用Dao方法进行查询
			list = cChargingStationCollectionDao.queryAll(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		// 如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<CChargingStationCollection>(0) : list;
		return list;
	}

	/**
	 * 根据查询条件，分页查询并返回CChargingStationCollection的分页结果
	 *
	 * @param q
	 *            查询条件
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public PageFinder<CChargingStationCollection> getCChargingStationCollectionPagedList(Query q) {
		PageFinder<CChargingStationCollection> page = new PageFinder<CChargingStationCollection>();
		List<CChargingStationCollection> list = null;
		long rowCount = 0L;
		try {
			// 调用dao查询满足条件的分页数据
			list = cChargingStationCollectionDao.pageList(q);
			// 调用dao统计满足条件的记录总数
			rowCount = cChargingStationCollectionDao.count(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		// 如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<CChargingStationCollection>(0) : list;
		// 将分页数据和记录总数设置到分页结果对象中
		page.setData(list);
		page.setRowCount(rowCount);
		return page;
	}

	/**
	 * 根据主键，查询并返回一个CChargingStationCollection对象
	 *
	 * @param id
	 *            主键id
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public CChargingStationCollection getCChargingStationCollection(String id) {
		CChargingStationCollection obj = null;
		if (id == null || id.length() == 0) { // 传入的主键无效时直接返回null
			log.info(Constant.ERR_MSG_INVALID_ARG + " id = " + id);
			return obj;
		}
		try {
			// 调用dao，根据主键查询
			obj = cChargingStationCollectionDao.get(id);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return obj;
	}

	/**
	 * 根据主键数组，查询并返回一组CChargingStationCollection对象
	 *
	 * @param ids
	 *            主键数组，数组中的主键值应当无重复值，如有重复值时，则返回的列表长度可能小于传入的数组长度
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<CChargingStationCollection> getCChargingStationCollectionByIds(String[] ids) {
		List<CChargingStationCollection> list = null;
		if (ids == null || ids.length == 0) {
			log.info(Constant.ERR_MSG_INVALID_ARG + " ids is null or empty array.");
		} else {
			try {
				// 调用dao，根据主键数组查询
				list = cChargingStationCollectionDao.getByIds(ids);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
		}
		// 如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<CChargingStationCollection>(0) : list;
		return list;
	}

	/**
	 * 根据主键，删除特定的CChargingStationCollection记录
	 *
	 * @param id
	 *            主键id
	 * @param operator
	 *            操作人对象
	 * @return
	 */
	@Transactional
	public ResultInfo<CChargingStationCollection> delCChargingStationCollection(String id, Operator operator) {
		ResultInfo<CChargingStationCollection> resultInfo = new ResultInfo<CChargingStationCollection>();
		if (id == null || id.length() == 0) { // 传入的主键无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG);
			log.info(Constant.ERR_MSG_INVALID_ARG + " id = " + id);
			return resultInfo;
		}
		try {
			// 调用Dao执行删除，并判断删除语句执行结果
			int count = cChargingStationCollectionDao.delete(id);
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
	 * 新增一条CChargingStationCollection记录，执行成功后传入对象及返回对象的主键属性值不为null
	 *
	 * @param cChargingStationCollection
	 *            新增的CChargingStationCollection数据（如果无特殊需求，新增对象的主键值请保留为null）
	 * @param operator
	 *            操作人对象
	 * @return
	 */
	@Transactional
	public ResultInfo<CChargingStationCollection> addCChargingStationCollection(
			CChargingStationCollection cChargingStationCollection, Operator operator) {
		ResultInfo<CChargingStationCollection> resultInfo = new ResultInfo<CChargingStationCollection>();
		if (cChargingStationCollection == null) { // 传入参数无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG);
			log.info(Constant.ERR_MSG_INVALID_ARG + " cChargingStationCollection = " + cChargingStationCollection);
		} else {
			try {
				// 如果传入参数的主键为null，则调用生成主键的方法获取新的主键
				if (cChargingStationCollection.getCollectionNo() == null) {
					cChargingStationCollection.setCollectionNo(this.generatePK());
				}
				// 如果传入的操作人不为null，则设置新增记录的操作人类型和操作人id
				if (operator != null) {
					cChargingStationCollection.setOperatorType(operator.getOperatorType());
					cChargingStationCollection.setOperatorId(operator.getOperatorId());
				}
				// 设置创建时间和更新时间为当前时间
				Date now = new Date();
				cChargingStationCollection.setCreateTime(now);
				cChargingStationCollection.setUpdateTime(now);
				// 填充默认值
				this.fillDefaultValues(cChargingStationCollection);
				// 调用Dao执行插入操作
				cChargingStationCollectionDao.add(cChargingStationCollection);
				resultInfo.setCode(Constant.SECCUESS);
				resultInfo.setData(cChargingStationCollection);
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
	 * 根据主键，更新一条CChargingStationCollection记录
	 *
	 * @param cChargingStationCollection
	 *            更新的CChargingStationCollection数据，且其主键不能为空
	 * @param operator
	 *            操作人对象
	 * @return
	 */
	@Transactional
	public ResultInfo<CChargingStationCollection> updateCChargingStationCollection(
			CChargingStationCollection cChargingStationCollection, Operator operator) {
		ResultInfo<CChargingStationCollection> resultInfo = new ResultInfo<CChargingStationCollection>();
		if (cChargingStationCollection == null || cChargingStationCollection.getCollectionNo() == null) { // 传入参数无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG);
			log.info(Constant.ERR_MSG_INVALID_ARG + " cChargingStationCollection = " + cChargingStationCollection);
		} else {
			try {
				// 如果传入的操作人不为null，则设置更新记录的操作人类型和操作人id
				if (operator != null) {
					cChargingStationCollection.setOperatorType(operator.getOperatorType());
					cChargingStationCollection.setOperatorId(operator.getOperatorId());
				}
				// 设置更新时间为当前时间
				cChargingStationCollection.setUpdateTime(new Date());
				// 调用Dao执行更新操作，并判断更新语句执行结果
				int count = cChargingStationCollectionDao.update(cChargingStationCollection);
				if (count > 0) {
					resultInfo.setCode(Constant.SECCUESS);
				} else {
					resultInfo.setCode(Constant.FAIL);
				}
				resultInfo.setData(cChargingStationCollection);
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
	 * 为CChargingStationCollection对象设置默认值
	 *
	 * @param obj
	 */
	public void fillDefaultValues(CChargingStationCollection obj) {
		if (obj != null) {
		}
	}

	public PageFinder<ChargingCollectionVo> getCollectionInfo(Query q) {
		PageFinder<ChargingCollectionVo> page = new PageFinder<ChargingCollectionVo>();
		List<ChargingCollectionVo> list = null;
		long rowCount = 0L;
		try {
			list = cChargingStationCollectionDao.getCollectionInfo(q);
			rowCount = cChargingStationCollectionDao.getCollectionSum(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		// 如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<ChargingCollectionVo>(0) : list;
		// 将分页数据和记录总数设置到分页结果对象中
		page.setData(list);
		page.setRowCount(rowCount);
		return page;
	}

	@Override
	public List<ChargingCollectionVo> getCollectionList(Query q) {
		List<ChargingCollectionVo> list = null;
		try {
			// 直接调用Dao方法进行查询
			list = cChargingStationCollectionDao.getAllCollection(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		// 如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<ChargingCollectionVo>(0) : list;
		return list;
	}

	@Override
	public ResultInfo addStationCollection(ResultInfo resultInfo, CChargingStationCollection cChargingStationCollection,
			Operator operator) {
		if (cChargingStationCollectionDao.isExist(cChargingStationCollection) > 0) {
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_DATA_ALEARD_EXISTS);
			return resultInfo;
		}
		Date date = new Date();
		cChargingStationCollection.setCollectionStatus(COLLECT_STATUS);
		cChargingStationCollection.setCreateTime(date);
		cChargingStationCollection.setUpdateTime(date);
		if (operator != null) {
			cChargingStationCollection.setOperatorType(operator.getOperatorType());
			cChargingStationCollection.setOperatorId(operator.getOperatorId());
		}
		try {
			cChargingStationCollectionDao.addStationCollection(cChargingStationCollection);
		} catch (Exception e) {
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_EXCEPTION_CATCHED);
			return resultInfo;
		}
		resultInfo.setCode(Constant.SECCUESS);
		return resultInfo;
	}

	@Override
	@Transactional
	public ResultInfo<String> toggleCollectionStatus(String memberNo, String stationNo) {
		ResultInfo<String> resultInfo = new ResultInfo<String>();
		Member member = memberDao.get(memberNo);
		ChargingStation station = chargingStationDao.get(stationNo);
		if (member == null || station == null) {
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg("会员为空或者充电站为空");
		} else {
			CChargingStationCollection search = new CChargingStationCollection();
			search.setMemberNo(memberNo);
			search.setStationNo(stationNo);
			List<CChargingStationCollection> lst = cChargingStationCollectionDao.queryAll(new Query(search));
			CChargingStationCollection collection = null;
			if (lst != null && lst.size() > 0)
				collection = lst.get(0);
			if (collection == null) {
				collection = new CChargingStationCollection();
				collection.setStationNo(stationNo);
				collection.setMemberNo(memberNo);
				collection.setCollectionNo(generatePK());
				collection.setCreateTime(new Date());
				collection.setUpdateTime(collection.getCreateTime());
				collection.setCollectionStatus(1);
				cChargingStationCollectionDao.add(collection);
			} else {
				collection.setUpdateTime(new Date());
				if (null != collection.getCollectionStatus()
						&& new Integer(1).equals(collection.getCollectionStatus())) {
					collection.setCollectionStatus(0);// 已取消
				} else if (null != collection.getCollectionStatus()
						&& new Integer(0).equals(collection.getCollectionStatus())) {
					collection.setCollectionStatus(1);// 已收藏
				} else {
					collection.setCollectionStatus(1);// 已收藏
				}

				cChargingStationCollectionDao.update(collection);
			}
			resultInfo.setCode(Constant.SECCUESS);
			resultInfo.setMsg("操作成功");
			resultInfo.setData(collection.getCollectionStatus().toString());
		}
		return resultInfo;
	}

	/**
	 * 生成主键
	 *
	 * @return
	 */
	public String generatePK() {
		// return "O"+ECDateUtils.formatDate(new
		// Date(),"yyyyMMdd")+primarykeyService.getValueByBizType(PrimarykeyConstant.orderType);
		return "C" + String.valueOf(new Date().getTime() * 1000000 + new Random().nextInt(1000000));
	}

}
