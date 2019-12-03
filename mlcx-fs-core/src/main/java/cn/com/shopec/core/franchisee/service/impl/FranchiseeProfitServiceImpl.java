package cn.com.shopec.core.franchisee.service.impl;

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
import cn.com.shopec.core.franchisee.dao.FranchiseeProfitDao;
import cn.com.shopec.core.franchisee.model.FranchiseeProfit;
import cn.com.shopec.core.franchisee.model.FranchiseeProfitVo;
import cn.com.shopec.core.franchisee.model.FranchiseeVo;
import cn.com.shopec.core.franchisee.service.FranchiseeProfitService;
import cn.com.shopec.core.system.common.PrimarykeyConstant;
import cn.com.shopec.core.system.service.PrimarykeyService;

/**
 * 加盟商收益表 服务实现类
 */
@Service
public class FranchiseeProfitServiceImpl implements FranchiseeProfitService {

	private static final Log log = LogFactory.getLog(FranchiseeProfitServiceImpl.class);

	@Resource
	private FranchiseeProfitDao franchiseeProfitDao;
	@Resource
	private PrimarykeyService primarykeyService;

	/**
	 * 根据查询条件，查询并返回FranchiseeProfit的列表
	 * 
	 * @param q
	 *            查询条件
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<FranchiseeProfit> getFranchiseeProfitList(Query q) {
		List<FranchiseeProfit> list = null;
		try {
			// 直接调用Dao方法进行查询
			list = franchiseeProfitDao.queryAll(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		// 如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<FranchiseeProfit>(0) : list;
		return list;
	}

	/**
	 * 根据查询条件，分页查询并返回FranchiseeProfit的分页结果
	 * 
	 * @param q
	 *            查询条件
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public PageFinder<FranchiseeProfit> getFranchiseeProfitPagedList(Query q) {
		PageFinder<FranchiseeProfit> page = new PageFinder<FranchiseeProfit>();

		List<FranchiseeProfit> list = null;
		long rowCount = 0L;

		try {
			// 调用dao查询满足条件的分页数据
			list = franchiseeProfitDao.pageList(q);
			// 调用dao统计满足条件的记录总数
			rowCount = franchiseeProfitDao.count(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		// 如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<FranchiseeProfit>(0) : list;

		// 将分页数据和记录总数设置到分页结果对象中
		page.setData(list);
		page.setRowCount(rowCount);

		return page;
	}

	/**
	 * 根据主键，查询并返回一个FranchiseeProfit对象
	 * 
	 * @param id
	 *            主键id
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public FranchiseeProfit getFranchiseeProfit(String id) {
		FranchiseeProfit obj = null;
		if (id == null || id.length() == 0) { // 传入的主键无效时直接返回null
			log.info(Constant.ERR_MSG_INVALID_ARG + " id = " + id);
			return obj;
		}
		try {
			// 调用dao，根据主键查询
			obj = franchiseeProfitDao.get(id);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		return obj;
	}

	/**
	 * 根据主键数组，查询并返回一组FranchiseeProfit对象
	 * 
	 * @param ids
	 *            主键数组，数组中的主键值应当无重复值，如有重复值时，则返回的列表长度可能小于传入的数组长度
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<FranchiseeProfit> getFranchiseeProfitByIds(String[] ids) {
		List<FranchiseeProfit> list = null;
		if (ids == null || ids.length == 0) {
			log.info(Constant.ERR_MSG_INVALID_ARG + " ids is null or empty array.");
		} else {
			try {
				// 调用dao，根据主键数组查询
				list = franchiseeProfitDao.getByIds(ids);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
		}

		// 如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<FranchiseeProfit>(0) : list;

		return list;
	}

	/**
	 * 根据主键，删除特定的FranchiseeProfit记录
	 * 
	 * @param id
	 *            主键id
	 * @param operator
	 *            操作人对象
	 * @return
	 */
	@Transactional
	public ResultInfo<FranchiseeProfit> delFranchiseeProfit(String id, Operator operator) {
		ResultInfo<FranchiseeProfit> resultInfo = new ResultInfo<FranchiseeProfit>();
		if (id == null || id.length() == 0) { // 传入的主键无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG);
			log.info(Constant.ERR_MSG_INVALID_ARG + " id = " + id);
			return resultInfo;
		}
		try {
			// 调用Dao执行删除，并判断删除语句执行结果
			int count = franchiseeProfitDao.delete(id);
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
	 * 新增一条FranchiseeProfit记录，执行成功后传入对象及返回对象的主键属性值不为null
	 * 
	 * @param franchiseeProfit
	 *            新增的FranchiseeProfit数据（如果无特殊需求，新增对象的主键值请保留为null）
	 * @param operator
	 *            操作人对象
	 * @return
	 */
	@Transactional
	public ResultInfo<FranchiseeProfit> addFranchiseeProfit(FranchiseeProfit franchiseeProfit, Operator operator) {
		ResultInfo<FranchiseeProfit> resultInfo = new ResultInfo<FranchiseeProfit>();

		if (franchiseeProfit == null) { // 传入参数无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG);
			log.info(Constant.ERR_MSG_INVALID_ARG + " franchiseeProfit = " + franchiseeProfit);
		} else {
			try {
				// 如果传入参数的主键为null，则调用生成主键的方法获取新的主键
				if (franchiseeProfit.getFranchiseeProfitNo() == null) {
					franchiseeProfit.setFranchiseeProfitNo(this.generatePK());
				}
				// 如果传入的操作人不为null，则设置新增记录的操作人类型和操作人id
				if (operator != null) {
					franchiseeProfit.setOperatorType(operator.getOperatorType());
					franchiseeProfit.setOperatorId(operator.getOperatorId());
				}

				// 设置创建时间和更新时间为当前时间
				Date now = new Date();
				franchiseeProfit.setCreateTime(now);
				franchiseeProfit.setUpdateTime(now);

				// 填充默认值
				this.fillDefaultValues(franchiseeProfit);

				// 调用Dao执行插入操作\
				//判断这个订单 有没有生成加盟商明细
				FranchiseeProfit  f=new FranchiseeProfit();
				f.setOrderNo(franchiseeProfit.getOrderNo());
				List<FranchiseeProfit> list =this.getFranchiseeProfitList(new Query(f));
				if(list != null && list.size()>0){
					resultInfo.setCode(Constant.FAIL);
					resultInfo.setData(franchiseeProfit);
					log.info("加盟商"+ franchiseeProfit.getFranchiseeName() + "的订单"+franchiseeProfit.getOrderNo()+"已经分润");
					return resultInfo;
				}
				franchiseeProfitDao.add(franchiseeProfit);
				resultInfo.setCode(Constant.SECCUESS);
				resultInfo.setData(franchiseeProfit);
				log.info("加盟商"+ franchiseeProfit.getFranchiseeName() + "的订单"+franchiseeProfit.getOrderNo()+"已经分润");
			} catch (Exception e) {
				log.error(e.getMessage(), e);
				log.info("加盟商"+ franchiseeProfit.getFranchiseeName() + "的订单"+franchiseeProfit.getOrderNo()+"分润出现异常");
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				resultInfo.setCode(Constant.FAIL);
				resultInfo.setMsg(Constant.ERR_MSG_EXCEPTION_CATCHED);
			}
		}

		return resultInfo;
	}

	/**
	 * 根据主键，更新一条FranchiseeProfit记录
	 * 
	 * @param franchiseeProfit
	 *            更新的FranchiseeProfit数据，且其主键不能为空
	 * @param operator
	 *            操作人对象
	 * @return
	 */
	@Transactional
	public ResultInfo<FranchiseeProfit> updateFranchiseeProfit(FranchiseeProfit franchiseeProfit, Operator operator) {
		ResultInfo<FranchiseeProfit> resultInfo = new ResultInfo<FranchiseeProfit>();

		if (franchiseeProfit == null || franchiseeProfit.getFranchiseeProfitNo() == null) { // 传入参数无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG);
			log.info(Constant.ERR_MSG_INVALID_ARG + " franchiseeProfit = " + franchiseeProfit);
		} else {
			try {
				// 如果传入的操作人不为null，则设置更新记录的操作人类型和操作人id
				if (operator != null) {
					franchiseeProfit.setOperatorType(operator.getOperatorType());
					franchiseeProfit.setOperatorId(operator.getOperatorId());
				}

				// 设置更新时间为当前时间
				franchiseeProfit.setUpdateTime(new Date());

				// 调用Dao执行更新操作，并判断更新语句执行结果
				int count = franchiseeProfitDao.update(franchiseeProfit);
				if (count > 0) {
					resultInfo.setCode(Constant.SECCUESS);
				} else {
					resultInfo.setCode(Constant.FAIL);
				}
				resultInfo.setData(franchiseeProfit);
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
		return "jmssy" + primarykeyService.getValueByBizType(PrimarykeyConstant.franchiseeType);
	}

	/**
	 * 为FranchiseeProfit对象设置默认值
	 * 
	 * @param obj
	 */
	public void fillDefaultValues(FranchiseeProfit obj) {
		if (obj != null) {
		}
	}

	@Override
	public PageFinder<FranchiseeVo> pageListSettlement(Query q) {
		PageFinder<FranchiseeVo> page = new PageFinder<FranchiseeVo>();

		List<FranchiseeVo> list = null;
		long rowCount = 0L;

		try {
			// 调用dao查询满足条件的分页数据
			list = franchiseeProfitDao.pageListSettlement(q);
			// 调用dao统计满足条件的记录总数
			rowCount = franchiseeProfitDao.countSettlement(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		// 如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<FranchiseeVo>(0) : list;

		// 将分页数据和记录总数设置到分页结果对象中
		page.setData(list);
		page.setRowCount(rowCount);

		return page;
	}

	@Override
	public List<FranchiseeVo> listFranchiseeProfit(Query q) {
		List<FranchiseeVo> list = null;
		try {
			// 直接调用Dao方法进行查询
			list = franchiseeProfitDao.listFranchiseeProfit(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		// 如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<FranchiseeVo>(0) : list;
		return list;
	}

	@Override
	public List<FranchiseeProfit> listFranchiseeProfitIseeNo(Query q) {
		List<FranchiseeProfit> list = null;
		try {
			// 直接调用Dao方法进行查询
			list = franchiseeProfitDao.listFranchiseeProfitIseeNo(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		// 如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<FranchiseeProfit>(0) : list;
		return list;
	}

	@Override
	public PageFinder<FranchiseeProfitVo> getFranchiseeProfitPagedListByMonths(Query q) {
		PageFinder<FranchiseeProfitVo> page = new PageFinder<FranchiseeProfitVo>();

		List<FranchiseeProfitVo> list = null;
		long rowCount = 0L;

		try {
			// 调用dao查询满足条件的分页数据
			list = franchiseeProfitDao.pageListFranchiseeProfitByMonths(q);
			// 调用dao统计满足条件的记录总数
			rowCount = franchiseeProfitDao.countFranchiseeProfitByMonths(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		// 如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<FranchiseeProfitVo>(0) : list;

		// 将分页数据和记录总数设置到分页结果对象中
		page.setData(list);
		page.setRowCount(rowCount);

		return page;
	}

	@Override
	public PageFinder<FranchiseeProfitVo> getFranchiseeProfitPagedListByQuarter(Query q) {
		PageFinder<FranchiseeProfitVo> page = new PageFinder<FranchiseeProfitVo>();

		List<FranchiseeProfitVo> list = null;
		long rowCount = 0L;

		try {
			// 调用dao查询满足条件的分页数据
			list = franchiseeProfitDao.pageListFranchiseeProfitByQuarter(q);
			// 调用dao统计满足条件的记录总数
			rowCount = franchiseeProfitDao.countFranchiseeProfitByQuarter(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		// 如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<FranchiseeProfitVo>(0) : list;

		// 将分页数据和记录总数设置到分页结果对象中
		page.setData(list);
		page.setRowCount(rowCount);

		return page;
	}

	@Override
	public PageFinder<FranchiseeProfitVo> getFranchiseeCarStatisticsPagedList(Query q) {
		PageFinder<FranchiseeProfitVo> page = new PageFinder<FranchiseeProfitVo>();

		List<FranchiseeProfitVo> list = null;
		long rowCount = 0L;

		try {
			// 调用dao查询满足条件的分页数据
			list = franchiseeProfitDao.pageListFranchiseeCarStatistics(q);
			// 调用dao统计满足条件的记录总数
			rowCount = franchiseeProfitDao.countFranchiseeCarStatistics(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		// 如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<FranchiseeProfitVo>(0) : list;

		// 将分页数据和记录总数设置到分页结果对象中
		page.setData(list);
		page.setRowCount(rowCount);

		return page;
	}

	@Override
	public PageFinder<FranchiseeProfitVo> getFranchiseeParkStatisticsPagedList(Query q) {
		PageFinder<FranchiseeProfitVo> page = new PageFinder<FranchiseeProfitVo>();

		List<FranchiseeProfitVo> list = null;
		long rowCount = 0L;

		try {
			// 调用dao查询满足条件的分页数据
			list = franchiseeProfitDao.pageListFranchiseeParkStatistics(q);
			// 调用dao统计满足条件的记录总数
			rowCount = franchiseeProfitDao.countFranchiseeParkStatistics(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		// 如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<FranchiseeProfitVo>(0) : list;

		// 将分页数据和记录总数设置到分页结果对象中
		page.setData(list);
		page.setRowCount(rowCount);

		return page;
	}

	@Override
	public FranchiseeProfitVo getFranchiseeProfitVoByMonths(String months,String franchiseeNo) {
		FranchiseeProfitVo obj = null;
		if (months == null || months.length() == 0) { 
			log.info(Constant.ERR_MSG_INVALID_ARG + " months = " + months);
			return obj;
		}
		try {
			obj = franchiseeProfitDao.getFranchiseeProfitVoByMonths(months,franchiseeNo);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		return obj;
	}

	@Override
	public FranchiseeProfitVo getFranchiseeProfitVoByQuarter(String quarter,String year,String franchiseeNo) {
		FranchiseeProfitVo obj = null;
		if (quarter == null || quarter.length() == 0) { 
			log.info(Constant.ERR_MSG_INVALID_ARG + " quarter = " + quarter+"year="+year);
			return obj;
		}
		try {
			obj = franchiseeProfitDao.getFranchiseeProfitVoByQuarter(quarter,year,franchiseeNo);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		return obj;
	}

}
