package cn.com.shopec.core.marketing.service.impl;

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
import cn.com.shopec.core.marketing.common.MarketingContant;
import cn.com.shopec.core.marketing.dao.PricingPackageDao;
import cn.com.shopec.core.marketing.model.PricingPackage;
import cn.com.shopec.core.marketing.service.PricingPackageService;
import cn.com.shopec.core.marketing.vo.PricingPackageStat;
import cn.com.shopec.core.member.common.MemberConstant;
import cn.com.shopec.core.member.model.Member;
import cn.com.shopec.core.statement.model.Pricing;
import cn.com.shopec.core.system.common.PrimarykeyConstant;
import cn.com.shopec.core.system.dao.DataDictItemDao;
import cn.com.shopec.core.system.model.DataDictItem;
import cn.com.shopec.core.system.service.PrimarykeyService;

/**
 * 套餐产品表 服务实现类
 */
@Service
public class PricingPackageServiceImpl implements PricingPackageService {

	private static final Log log = LogFactory.getLog(PricingPackageServiceImpl.class);
	
	@Resource
	private PricingPackageDao pricingPackageDao;
	
	@Resource
	private PrimarykeyService primarykeyService;
	/**
	 * 根据查询条件，查询并返回PricingPackage的列表
	 * @param q 查询条件
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<PricingPackage> getPricingPackageList(Query q) {
		List<PricingPackage> list = null;
		try {
			//直接调用Dao方法进行查询
			list = pricingPackageDao.queryAll(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		//如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<PricingPackage>(0) : list;
		return list; 
	}
	
	/**
	 * 根据查询条件，分页查询并返回PricingPackage的分页结果
	 * @param q 查询条件
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public PageFinder<PricingPackage> getPricingPackagePagedList(Query q) {
		PageFinder<PricingPackage> page = new PageFinder<PricingPackage>();
		
		List<PricingPackage> list = null;
		long rowCount = 0L;
		
		try {
			//调用dao查询满足条件的分页数据
			list = pricingPackageDao.pageList(q);
			//调用dao统计满足条件的记录总数
			rowCount = pricingPackageDao.count(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		
		//如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<PricingPackage>(0) : list;
	
		//将分页数据和记录总数设置到分页结果对象中
		page.setData(list);
		page.setRowCount(rowCount);
		
		return page;
	}	
	
	/**
	 * 根据主键，查询并返回一个PricingPackage对象
	 * @param id 主键id
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public PricingPackage getPricingPackage(String id) {
		PricingPackage obj = null;
		if (id == null || id.length() == 0) { //传入的主键无效时直接返回null
			log.info(Constant.ERR_MSG_INVALID_ARG + " id = " + id);
			return obj;
		}
		try {
			//调用dao，根据主键查询
			obj = pricingPackageDao.get(id); 
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		
		return obj;
	}
	/**
	 * 根据套餐产品名称，查询并返回一个PricingPackage对象
	 * @param id 主键id
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public PricingPackage getPricingPackageByPackageName(String packageName) {
		PricingPackage pricingPackage = null;
		if (packageName == null || packageName.length() == 0) { //传入的主键无效时直接返回null
			log.info(Constant.ERR_MSG_INVALID_ARG + " packageName = " + packageName);
			return null;
		}
		try {
			//调用dao，根据主键查询
			pricingPackage = pricingPackageDao.getPricingPackageByPackageName(packageName); 
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		
		return pricingPackage;
	}

	/**
	 * 根据主键数组，查询并返回一组PricingPackage对象
	 * @param ids 主键数组，数组中的主键值应当无重复值，如有重复值时，则返回的列表长度可能小于传入的数组长度
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<PricingPackage> getPricingPackageByIds(String[] ids) {
		List<PricingPackage> list = null;
		if (ids == null || ids.length == 0) {
			log.info(Constant.ERR_MSG_INVALID_ARG + " ids is null or empty array.");
		} else {
			try {
				//调用dao，根据主键数组查询
				list = pricingPackageDao.getByIds(ids);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
		}
		
		//如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<PricingPackage>(0) : list;

		return list;
	}	
	
	/**
	 * 根据主键，删除特定的PricingPackage记录
	 * @param id 主键id
	 * @param operator 操作人对象
	 * @return
	 */
	@Transactional
	public ResultInfo<PricingPackage> delPricingPackage(String id, Operator operator) {
		ResultInfo<PricingPackage> resultInfo = new ResultInfo<PricingPackage>();
		if (id == null || id.length() == 0) { //传入的主键无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG );
			log.info(Constant.ERR_MSG_INVALID_ARG + " id = " + id);
			return resultInfo;
		}
		try {
		    //调用Dao执行删除，并判断删除语句执行结果
			int count = pricingPackageDao.delete(id);
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
	 * 新增一条PricingPackage记录，执行成功后传入对象及返回对象的主键属性值不为null
	 * @param pricingPackage 新增的PricingPackage数据（如果无特殊需求，新增对象的主键值请保留为null）
	 * @param operator 操作人对象
	 * @return
	 */
	@Transactional
	public ResultInfo<PricingPackage> addPricingPackage(PricingPackage pricingPackage, Operator operator) {
		ResultInfo<PricingPackage> resultInfo = new ResultInfo<PricingPackage>();
		
		if (pricingPackage == null) { //传入参数无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG );
			log.info(Constant.ERR_MSG_INVALID_ARG + " pricingPackage = " + pricingPackage);
		} else {
			try {
				//如果传入参数的主键为null，则调用生成主键的方法获取新的主键
				if (pricingPackage.getPackageNo() == null) {
					pricingPackage.setPackageNo(this.generatePK());
				}
				//如果传入的操作人不为null，则设置新增记录的操作人类型和操作人id
				if (operator != null) {
					pricingPackage.setOperatorType(operator.getOperatorType());
					pricingPackage.setOperatorId(operator.getOperatorId());
				}
				
				//设置创建时间和更新时间为当前时间
				Date now = new Date();
				pricingPackage.setCreateTime(now);
				pricingPackage.setUpdateTime(now);
				//填充默认值
				this.fillDefaultValues(pricingPackage);
				
				//调用Dao执行插入操作
				pricingPackageDao.add(pricingPackage);
				resultInfo.setCode(Constant.SECCUESS);
				resultInfo.setData(pricingPackage);
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
	 * 根据主键，更新一条PricingPackage记录
	 * @param pricingPackage 更新的PricingPackage数据，且其主键不能为空
	 * @param operator 操作人对象
	 * @return
	 */
	@Transactional
	public ResultInfo<PricingPackage> updatePricingPackage(PricingPackage pricingPackage, Operator operator) {
		ResultInfo<PricingPackage> resultInfo = new ResultInfo<PricingPackage>();
		
		if (pricingPackage == null || pricingPackage.getPackageNo() == null) { //传入参数无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG );
			log.info(Constant.ERR_MSG_INVALID_ARG + " pricingPackage = " + pricingPackage);
		} else {
			try {
				//如果传入的操作人不为null，则设置更新记录的操作人类型和操作人id
				if (operator != null) {
					pricingPackage.setOperatorType(operator.getOperatorType());
					pricingPackage.setOperatorId(operator.getOperatorId());
				}
				
				//设置更新时间为当前时间
				pricingPackage.setUpdateTime(new Date());
				
				//调用Dao执行更新操作，并判断更新语句执行结果
				int count = pricingPackageDao.update(pricingPackage);			
				if (count > 0) {
					resultInfo.setCode(Constant.SECCUESS);
				} else {
					resultInfo.setCode(Constant.FAIL);
				}
				resultInfo.setData(pricingPackage);
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
		return primarykeyService.getValueByBizType(PrimarykeyConstant.packageType)+"";
	}
	
	/**
	 * 为PricingPackage对象设置默认值
	 * @param obj
	 */
	public void fillDefaultValues(PricingPackage obj) {
		if (obj != null) {
		    if (obj.getIsAvailable() == null) {
		    	obj.setIsAvailable(0);
		    }
		    if (obj.getCencorStatus() == null) {
		    	obj.setCencorStatus(0);
		    }
		    if (obj.getIsDeleted() == null) {
		    	obj.setIsDeleted(0);
		    }
		}
	}

	@Override
	public PageFinder<Pricing> getPricingPagedList(Query q) {
		PageFinder<Pricing> page = new PageFinder<Pricing>();
		
		List<Pricing> list = null;
		long rowCount = 0L;
		
		try {
			//调用dao查询满足条件的分页数据
			list = pricingPackageDao.pageListPricing(q);
			//调用dao统计满足条件的记录总数
			rowCount = pricingPackageDao.countPricing(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		
		//如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<Pricing>(0) : list;
	
		//将分页数据和记录总数设置到分页结果对象中
		page.setData(list);
		page.setRowCount(rowCount);
		
		return page;
	}

	@Override
	public Integer getTodoIndexValue() {
		return pricingPackageDao.countPricingPackageCensorStatus();
	}

	@Override
	public PricingPackage salePackageEportMonth(String packageNo, String time) {
		return pricingPackageDao.salePackageEportMonth(packageNo, time); 
	}

	@Override
	public PricingPackage salePackageEportWeek(String packageNo, String createTimeStartWeek, String createTimeEndWeek) {
		return pricingPackageDao.salePackageEportWeek(packageNo, createTimeStartWeek, createTimeEndWeek);
	}
	
	@Override
	public PricingPackageStat getPricingPackageDataDay(String time) {
		return pricingPackageDao.getPricingPackageDataDay(time); 
	}
	
	@Override
	public PricingPackageStat getFirstDayPricingPackageData(String time) {
		return pricingPackageDao.getFirstDayPricingPackageData(time); 
	}
	
	@Override
	public PricingPackageStat getPricingPackageWeekData(String stime,String eTime) {
		return pricingPackageDao.getPricingPackageWeekData(stime,eTime); 
	}
	
	@Override
	public PricingPackageStat getWeekFirstDayPricingPackageData(String time) {
		return pricingPackageDao.getWeekFirstDayPricingPackageData(time); 
	}
	
	@Override
	public PricingPackageStat getPricingPackageDataMonth(String time) {
		return pricingPackageDao.getPricingPackageDataMonth(time); 
	}
	
	@Override
	public PricingPackageStat getMonthFirstPricingPackageData(String time) {
		return pricingPackageDao.getMonthFirstPricingPackageData(time); 
	}

}
