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
import cn.com.shopec.core.marketing.dao.AreaDepositDao;
import cn.com.shopec.core.marketing.model.AreaDeposit;
import cn.com.shopec.core.marketing.service.AreaDepositService;

/**
 * 地区应繳押金表 服务实现类
 */
@Service
public class AreaDepositServiceImpl implements AreaDepositService {

	private static final Log log = LogFactory.getLog(AreaDepositServiceImpl.class);
	
	@Resource
	private AreaDepositDao areaDepositDao;
	

	/**
	 * 根据查询条件，查询并返回AreaDeposit的列表
	 * @param q 查询条件
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<AreaDeposit> getAreaDepositList(Query q) {
		List<AreaDeposit> list = null;
		try {
			//已删除的不查出
			AreaDeposit areaDeposit = (AreaDeposit)q.getQ();
			if (areaDeposit != null) {
				areaDeposit.setIsDeleted(Constant.DEL_STATE_NO);
			}
					
			//直接调用Dao方法进行查询
			list = areaDepositDao.queryAll(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		//如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<AreaDeposit>(0) : list;
		return list; 
	}
	
	/**
	 * 根据查询条件，分页查询并返回AreaDeposit的分页结果
	 * @param q 查询条件
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public PageFinder<AreaDeposit> getAreaDepositPagedList(Query q) {
		PageFinder<AreaDeposit> page = new PageFinder<AreaDeposit>();
		
		List<AreaDeposit> list = null;
		long rowCount = 0L;
		
		try {
			//已删除的不查出
			AreaDeposit areaDeposit = (AreaDeposit)q.getQ();
			if (areaDeposit != null) {
				areaDeposit.setIsDeleted(Constant.DEL_STATE_NO);
			}
					
			//调用dao查询满足条件的分页数据
			list = areaDepositDao.pageList(q);
			//调用dao统计满足条件的记录总数
			rowCount = areaDepositDao.count(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		
		//如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<AreaDeposit>(0) : list;
	
		//将分页数据和记录总数设置到分页结果对象中
		page.setData(list);
		page.setRowCount(rowCount);
		
		return page;
	}	
	
	/**
	 * 根据主键，查询并返回一个AreaDeposit对象
	 * @param id 主键id
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public AreaDeposit getAreaDeposit(String id) {
		AreaDeposit obj = null;
		if (id == null || id.length() == 0) { //传入的主键无效时直接返回null
			log.info(Constant.ERR_MSG_INVALID_ARG + " id = " + id);
			return obj;
		}
		try {
			//调用dao，根据主键查询
			obj = areaDepositDao.get(id); 
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		
		return obj;
	}

	/**
	 * 根据主键数组，查询并返回一组AreaDeposit对象
	 * @param ids 主键数组，数组中的主键值应当无重复值，如有重复值时，则返回的列表长度可能小于传入的数组长度
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<AreaDeposit> getAreaDepositByIds(String[] ids) {
		List<AreaDeposit> list = null;
		if (ids == null || ids.length == 0) {
			log.info(Constant.ERR_MSG_INVALID_ARG + " ids is null or empty array.");
		} else {
			try {
				//调用dao，根据主键数组查询
				list = areaDepositDao.getByIds(ids);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
		}
		
		//如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<AreaDeposit>(0) : list;

		return list;
	}	
	
	/**
	 * 根据主键，删除特定的AreaDeposit记录
	 * @param id 主键id
	 * @param operator 操作人对象
	 * @return
	 */
	@Transactional
	public ResultInfo<AreaDeposit> delAreaDeposit(String id, Operator operator) {
		ResultInfo<AreaDeposit> resultInfo = new ResultInfo<AreaDeposit>();
		if (id == null || id.length() == 0) { //传入的主键无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG );
			log.info(Constant.ERR_MSG_INVALID_ARG + " id = " + id);
			return resultInfo;
		}
		try {
			//逻辑删除，实际上执行update语句，以下设置待更新记录的主键、删除标识、更新时间、操作人信息等
			AreaDeposit areaDeposit = new AreaDeposit();
			areaDeposit.setId(id);
			areaDeposit.setIsDeleted(Constant.DEL_STATE_YES);
			areaDeposit.setUpdateTime(new Date());
			if (operator != null) { //最近操作人
				areaDeposit.setOperatorType(operator.getOperatorType());
				areaDeposit.setOperatorId(operator.getOperatorId());
			}
			
			//调用Dao执行更新操作，并判断更新语句执行结果
			int count = areaDepositDao.update(areaDeposit);			
			if (count > 0) {
				resultInfo.setCode(Constant.SECCUESS);
			} else {
				resultInfo.setCode(Constant.FAIL);
			}
			resultInfo.setData(areaDeposit);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_EXCEPTION_CATCHED);
		}
		return resultInfo;
	}
		
	/**
	 * 新增一条AreaDeposit记录，执行成功后传入对象及返回对象的主键属性值不为null
	 * @param areaDeposit 新增的AreaDeposit数据（如果无特殊需求，新增对象的主键值请保留为null）
	 * @param operator 操作人对象
	 * @return
	 */
	@Transactional
	public ResultInfo<AreaDeposit> addAreaDeposit(AreaDeposit areaDeposit, Operator operator) {
		ResultInfo<AreaDeposit> resultInfo = new ResultInfo<AreaDeposit>();
		
		if (areaDeposit == null) { //传入参数无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG );
			log.info(Constant.ERR_MSG_INVALID_ARG + " areaDeposit = " + areaDeposit);
		} else {
			try {
				//如果传入参数的主键为null，则调用生成主键的方法获取新的主键
				if (areaDeposit.getId() == null) {
					String id = this.generatePK();
					areaDeposit.setId(id);
				}
				//如果传入的操作人不为null，则设置新增记录的操作人类型和操作人id
				if (operator != null) {
					areaDeposit.setOperatorType(operator.getOperatorType());
					areaDeposit.setOperatorId(operator.getOperatorId());
				}
				
				//设置创建时间和更新时间为当前时间
				Date now = new Date();
				areaDeposit.setCreateTime(now);
				areaDeposit.setUpdateTime(now);
				
				//填充默认值
				this.fillDefaultValues(areaDeposit);
				
				//调用Dao执行插入操作
				areaDepositDao.add(areaDeposit);
				resultInfo.setCode(Constant.SECCUESS);
				resultInfo.setData(areaDeposit);
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
	 * 根据主键，更新一条AreaDeposit记录
	 * @param areaDeposit 更新的AreaDeposit数据，且其主键不能为空
	 * @param operator 操作人对象
	 * @return
	 */
	@Transactional
	public ResultInfo<AreaDeposit> updateAreaDeposit(AreaDeposit areaDeposit, Operator operator) {
		ResultInfo<AreaDeposit> resultInfo = new ResultInfo<AreaDeposit>();
		
		if (areaDeposit == null || areaDeposit.getId() == null) { //传入参数无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG );
			log.info(Constant.ERR_MSG_INVALID_ARG + " areaDeposit = " + areaDeposit);
		} else {
			try {
				//如果传入的操作人不为null，则设置更新记录的操作人类型和操作人id
				if (operator != null) {
					areaDeposit.setOperatorType(operator.getOperatorType());
					areaDeposit.setOperatorId(operator.getOperatorId());
				}
				
				//设置更新时间为当前时间
				areaDeposit.setUpdateTime(new Date());
				
				//调用Dao执行更新操作，并判断更新语句执行结果
				int count = areaDepositDao.update(areaDeposit);			
				if (count > 0) {
					resultInfo.setCode(Constant.SECCUESS);
				} else {
					resultInfo.setCode(Constant.FAIL);
				}
				resultInfo.setData(areaDeposit);
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
		return  String.valueOf(new Date().getTime() * 1000000 + new Random().nextInt(1000000));
	}
	
	/**
	 * 为AreaDeposit对象设置默认值
	 * @param obj
	 */
	public void fillDefaultValues(AreaDeposit obj) {
		if (obj != null) {
		    if (obj.getIsAvailable() == null) {
		    	obj.setIsAvailable(0);
		    }
		    if (obj.getCensorStatus() == null) {
		    	obj.setCensorStatus(0);
		    }
		    if (obj.getIsDeleted() == null) {
		    	obj.setIsDeleted(0);
		    }
		}
	}

}
