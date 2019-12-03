package cn.com.shopec.core.marketing.service.impl;

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
import cn.com.shopec.core.marketing.dao.PricingRuleDao;
import cn.com.shopec.core.marketing.model.PricingRule;
import cn.com.shopec.core.marketing.service.PricingRuleService;
import cn.com.shopec.core.member.model.Company;
import cn.com.shopec.core.member.service.CompanyService;
import cn.com.shopec.core.system.dao.DataDictItemDao;
import cn.com.shopec.core.system.model.DataDictItem;
import cn.com.shopec.core.system.service.DataDictItemService;

/**
 * 计费规则表 服务实现类
 */
@Service
public class PricingRuleServiceImpl implements PricingRuleService {

	private static final Log log = LogFactory.getLog(PricingRuleServiceImpl.class);
	
	@Resource
	private PricingRuleDao pricingRuleDao;
	@Resource
	private DataDictItemDao dataDictItemDao;
	@Resource
	private CompanyService companyService;

	/**
	 * 根据查询条件，查询并返回PricingRule的列表
	 * @param q 查询条件
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<PricingRule> getPricingRuleList(Query q) {
		List<PricingRule> list = null;
		try {
			//直接调用Dao方法进行查询
			list = pricingRuleDao.queryAll(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		//如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<PricingRule>(0) : list;
		return list; 
	}
	
	/**
	 * 根据查询条件，分页查询并返回PricingRule的分页结果
	 * @param q 查询条件
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public PageFinder<PricingRule> getPricingRulePagedList(Query q) {
		PageFinder<PricingRule> page = new PageFinder<PricingRule>();
		
		List<PricingRule> list = null;
		long rowCount = 0L;
		
		try {
			//调用dao查询满足条件的分页数据
			list = pricingRuleDao.pageList(q);
			for(PricingRule pr:list){
				if(pr.getCompanyId()!=null&&!pr.getCompanyId().equals("")){
					Company c=companyService.getCompany(pr.getCompanyId());
					if(c!=null){
						pr.setCompanyName(c.getCompanyName());
					}
				}
				
				pr.setPriceDay(pr.getPriceOfMinute()+"元/分钟  "+pr.getPriceOfKm()+"元/公里");
				pr.setPriceDayOrdinary(pr.getPriceOfMinuteOrdinary()+"元/分钟  "+pr.getPriceOfKmOrdinary()+"元/公里");
			}
			//调用dao统计满足条件的记录总数
			rowCount = pricingRuleDao.count(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		
		//如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<PricingRule>(0) : list;
	
		//将分页数据和记录总数设置到分页结果对象中
		page.setData(list);
		page.setRowCount(rowCount);
		
		return page;
	}	
	
	/**
	 * 根据主键，查询并返回一个PricingRule对象
	 * @param id 主键id
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public PricingRule getPricingRule(String id) {
		PricingRule obj = null;
		if (id == null || id.length() == 0) { //传入的主键无效时直接返回null
			log.info(Constant.ERR_MSG_INVALID_ARG + " id = " + id);
			return obj;
		}
		try {
			//调用dao，根据主键查询
			obj = pricingRuleDao.get(id); 
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		
		return obj;
	}

	/**
	 * 根据主键数组，查询并返回一组PricingRule对象
	 * @param ids 主键数组，数组中的主键值应当无重复值，如有重复值时，则返回的列表长度可能小于传入的数组长度
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<PricingRule> getPricingRuleByIds(String[] ids) {
		List<PricingRule> list = null;
		if (ids == null || ids.length == 0) {
			log.info(Constant.ERR_MSG_INVALID_ARG + " ids is null or empty array.");
		} else {
			try {
				//调用dao，根据主键数组查询
				list = pricingRuleDao.getByIds(ids);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
		}
		
		//如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<PricingRule>(0) : list;

		return list;
	}	
	
	/**
	 * 根据主键，删除特定的PricingRule记录
	 * @param id 主键id
	 * @param operator 操作人对象
	 * @return
	 */
	@Transactional
	public ResultInfo<PricingRule> delPricingRule(String id, Operator operator) {
		ResultInfo<PricingRule> resultInfo = new ResultInfo<PricingRule>();
		if (id == null || id.length() == 0) { //传入的主键无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG );
			log.info(Constant.ERR_MSG_INVALID_ARG + " id = " + id);
			return resultInfo;
		}
		try {
		    //调用Dao执行删除，并判断删除语句执行结果
			int count = pricingRuleDao.delete(id);
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
	 * 新增一条PricingRule记录，执行成功后传入对象及返回对象的主键属性值不为null
	 * @param pricingRule 新增的PricingRule数据（如果无特殊需求，新增对象的主键值请保留为null）
	 * @param operator 操作人对象
	 * @return
	 */
	@Transactional
	public ResultInfo<PricingRule> addPricingRule(PricingRule pricingRule, Operator operator) {
		ResultInfo<PricingRule> resultInfo = new ResultInfo<PricingRule>();
		
		if (pricingRule == null) { //传入参数无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG );
			log.info(Constant.ERR_MSG_INVALID_ARG + " pricingRule = " + pricingRule);
		} else {
			try {
				//如果传入参数的主键为null，则调用生成主键的方法获取新的主键
				if (pricingRule.getRuleNo() == null) {
					pricingRule.setRuleNo(this.generatePK());
				}
				//如果传入的操作人不为null，则设置新增记录的操作人类型和操作人id
				if (operator != null) {
					pricingRule.setOperatorType(operator.getOperatorType());
					pricingRule.setOperatorId(operator.getOperatorId());
				}
				
				//设置创建时间和更新时间为当前时间
				Date now = new Date();
				pricingRule.setCreateTime(now);
				pricingRule.setUpdateTime(now);
				DataDictItem dataDictItem=dataDictItemDao.get(pricingRule.getCityId());
				if(dataDictItem!=null){
				pricingRule.setCityName(dataDictItem.getItemValue());
				}
				//填充默认值
				this.fillDefaultValues(pricingRule);
				
				//调用Dao执行插入操作
				pricingRuleDao.add(pricingRule);
				resultInfo.setCode(Constant.SECCUESS);
				resultInfo.setData(pricingRule);
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
	 * 根据主键，更新一条PricingRule记录
	 * @param pricingRule 更新的PricingRule数据，且其主键不能为空
	 * @param operator 操作人对象
	 * @return
	 */
	@Transactional
	public ResultInfo<PricingRule> updatePricingRule(PricingRule pricingRule, Operator operator) {
		ResultInfo<PricingRule> resultInfo = new ResultInfo<PricingRule>();
		
		if (pricingRule == null || pricingRule.getRuleNo() == null) { //传入参数无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG );
			log.info(Constant.ERR_MSG_INVALID_ARG + " pricingRule = " + pricingRule);
		} else {
			try {
				//如果传入的操作人不为null，则设置更新记录的操作人类型和操作人id
				if (operator != null) {
					pricingRule.setOperatorType(operator.getOperatorType());
					pricingRule.setOperatorId(operator.getOperatorId());
					pricingRule.setCencorId(operator.getOperatorId());
				}
				
				//设置更新时间为当前时间
				pricingRule.setUpdateTime(new Date());
				pricingRule.setCencorTime(new Date());
				DataDictItem dataDictItem=dataDictItemDao.get(pricingRule.getCityId());
				if(dataDictItem!=null){
				pricingRule.setCityName(dataDictItem.getItemValue());
				}
				//调用Dao执行更新操作，并判断更新语句执行结果
				int count = pricingRuleDao.update(pricingRule);			
				if (count > 0) {
					resultInfo.setCode(Constant.SECCUESS);
				} else {
					resultInfo.setCode(Constant.FAIL);
				}
				resultInfo.setData(pricingRule);
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
		return String.valueOf(new Date().getTime() * 1000000 + new Random().nextInt(1000000));
	}
	
	/**
	 * 为PricingRule对象设置默认值
	 * @param obj
	 */
	public void fillDefaultValues(PricingRule obj) {
		if (obj != null) {
		    if (obj.getIsAvailable() == null) {
		    	obj.setIsAvailable(-1);//isAvailable默认为-1 表示可以编辑，启用变为1，停用为0
		    }
		    if (obj.getCencorStatus() == null) {
		    	obj.setCencorStatus(0);
		    }
		}
	}

	@Override
	public PricingRule getPricingRuleUse() {
		// TODO Auto-generated method stub
		return pricingRuleDao.getPricingRuleUse();
	}

	@Override
	public PricingRule getPricingRuleUseByMM(String carBrandName,String carModelName,String companyId) {
		PricingRule obj = null;
		try {
			Date nowTime=new Date();
			//调用dao，根据主键查询
			obj = pricingRuleDao.getPricingRuleUseByMM(carBrandName+carModelName,companyId,nowTime); 
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		
		return obj;
	}

	@Override
	public PricingRule getPricingRuleUseByMMP(String carBrandName, String carModelName) {
		PricingRule obj = null;
		try {
			//调用dao，根据主键查询
			Date nowTime=new Date();
			obj = pricingRuleDao.getPricingRuleUseByMMP(carBrandName+carModelName,nowTime); 
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		
		return obj;
	}

	@Override
	public PricingRule getPricingRuleUseByCar(String carBrandName, String carModelName) {
		// TODO Auto-generated method stub
		PricingRule obj = null;
		try {
			//调用dao，根据主键查询
			obj = pricingRuleDao.getPricingRuleUseByCar(carBrandName+carModelName); 
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		
		return obj;
	}

	@Override
	public PricingRule getPricingRuleUseByCompanyId(String carBrandName, String carModelName, String companyId) {
		// TODO Auto-generated method stub
		return pricingRuleDao.getPricingRuleUseByCompanyId(carBrandName+carModelName,companyId);
	}

	@Override
	public PageFinder<PricingRule> getPricingRulePagedLists(Query q) {
		PageFinder<PricingRule> page = new PageFinder<PricingRule>();
		
		List<PricingRule> list = null;
		long rowCount = 0L;
		
		try {
			//调用dao查询满足条件的分页数据
			list = pricingRuleDao.pageLists(q);
			for(PricingRule pr:list){
				if(pr.getCompanyId()!=null&&!pr.getCompanyId().equals("")){
					Company c=companyService.getCompany(pr.getCompanyId());
					if(c!=null){
						pr.setCompanyName(c.getCompanyName());
					}
				}
				
				pr.setPriceDay(pr.getPriceOfMinute()+"元/分钟  "+pr.getPriceOfKm()+"元/公里");
				if(pr.getPriceOfMinuteOrdinary()!=null){
					pr.setPriceDayOrdinary(pr.getPriceOfMinuteOrdinary()+"元/分钟  "+pr.getPriceOfKmOrdinary()+"元/公里");
				}else{
					pr.setPriceDayOrdinary("0元/分钟  "+"0元/公里");
				}
			}
			//调用dao统计满足条件的记录总数
			rowCount = pricingRuleDao.countPagedLists(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		
		//如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<PricingRule>(0) : list;
	
		//将分页数据和记录总数设置到分页结果对象中
		page.setData(list);
		page.setRowCount(rowCount);
		
		return page;
	}


}
