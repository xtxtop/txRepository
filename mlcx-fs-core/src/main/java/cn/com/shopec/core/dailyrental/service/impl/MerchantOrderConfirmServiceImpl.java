package cn.com.shopec.core.dailyrental.service.impl;

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
import cn.com.shopec.core.dailyrental.dao.MerchantOrderConfirmDao;
import cn.com.shopec.core.dailyrental.model.MerchantOrderConfirm;
import cn.com.shopec.core.dailyrental.service.MerchantOrderConfirmService;

/**
 * 商家确认账单 服务实现类
 */
@Service
public class MerchantOrderConfirmServiceImpl implements MerchantOrderConfirmService {

	private static final Log log = LogFactory.getLog(MerchantOrderConfirmServiceImpl.class);
	
	@Resource
	private MerchantOrderConfirmDao merchantOrderConfirmDao;
	
	@Override
	public List<MerchantOrderConfirm> getListByMerchantId(String merchantId) {
		return merchantOrderConfirmDao.getListByMerchantId(merchantId);
	}

	/**
	 * 根据查询条件，查询并返回MerchantOrderConfirm的列表
	 * @param q 查询条件
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<MerchantOrderConfirm> getMerchantOrderConfirmList(Query q) {
		List<MerchantOrderConfirm> list = null;
		try {
			//直接调用Dao方法进行查询
			list = merchantOrderConfirmDao.queryAll(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		//如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<MerchantOrderConfirm>(0) : list;
		return list; 
	}
	
	/**
	 * 根据查询条件，分页查询并返回MerchantOrderConfirm的分页结果
	 * @param q 查询条件
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public PageFinder<MerchantOrderConfirm> getMerchantOrderConfirmPagedList(Query q) {
		PageFinder<MerchantOrderConfirm> page = new PageFinder<MerchantOrderConfirm>();
		
		List<MerchantOrderConfirm> list = null;
		long rowCount = 0L;
		
		try {
			//调用dao查询满足条件的分页数据
			list = merchantOrderConfirmDao.pageList(q);
			//调用dao统计满足条件的记录总数
			rowCount = merchantOrderConfirmDao.count(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		
		//如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<MerchantOrderConfirm>(0) : list;
	
		//将分页数据和记录总数设置到分页结果对象中
		page.setData(list);
		page.setRowCount(rowCount);
		
		return page;
	}	
	/**
	 * 根据查询条件，分页查询并返回MerchantOrderConfirm的分页结果
	 * @param q 查询条件
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public PageFinder<MerchantOrderConfirm> getMerchantOrderConfirmPagedListForMgt(Query q) {
		PageFinder<MerchantOrderConfirm> page = new PageFinder<MerchantOrderConfirm>();
		
		List<MerchantOrderConfirm> list = null;
		long rowCount = 0L;
		
		try {
			//调用dao查询满足条件的分页数据
			list = merchantOrderConfirmDao.pageListForMgt(q);
			//调用dao统计满足条件的记录总数
			rowCount = merchantOrderConfirmDao.count(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		
		//如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<MerchantOrderConfirm>(0) : list;
	
		//将分页数据和记录总数设置到分页结果对象中
		page.setData(list);
		page.setRowCount(rowCount);
		
		return page;
	}	
	/**
	 * 根据主键，查询并返回一个MerchantOrderConfirm对象
	 * @param id 主键id
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public MerchantOrderConfirm getMerchantOrderConfirm(String id) {
		MerchantOrderConfirm obj = null;
		if (id == null || id.length() == 0) { //传入的主键无效时直接返回null
			log.info(Constant.ERR_MSG_INVALID_ARG + " id = " + id);
			return obj;
		}
		try {
			//调用dao，根据主键查询
			obj = merchantOrderConfirmDao.get(id); 
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		
		return obj;
	}

	/**
	 * 根据主键数组，查询并返回一组MerchantOrderConfirm对象
	 * @param ids 主键数组，数组中的主键值应当无重复值，如有重复值时，则返回的列表长度可能小于传入的数组长度
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<MerchantOrderConfirm> getMerchantOrderConfirmByIds(String[] ids) {
		List<MerchantOrderConfirm> list = null;
		if (ids == null || ids.length == 0) {
			log.info(Constant.ERR_MSG_INVALID_ARG + " ids is null or empty array.");
		} else {
			try {
				//调用dao，根据主键数组查询
				list = merchantOrderConfirmDao.getByIds(ids);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
		}
		
		//如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<MerchantOrderConfirm>(0) : list;

		return list;
	}	
	
	/**
	 * 根据主键，删除特定的MerchantOrderConfirm记录
	 * @param id 主键id
	 * @param operator 操作人对象
	 * @return
	 */
	@Transactional
	public ResultInfo<MerchantOrderConfirm> delMerchantOrderConfirm(String id, Operator operator) {
		ResultInfo<MerchantOrderConfirm> resultInfo = new ResultInfo<MerchantOrderConfirm>();
		if (id == null || id.length() == 0) { //传入的主键无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG );
			log.info(Constant.ERR_MSG_INVALID_ARG + " id = " + id);
			return resultInfo;
		}
		try {
		    //调用Dao执行删除，并判断删除语句执行结果
			int count = merchantOrderConfirmDao.delete(id);
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
	 * 新增一条MerchantOrderConfirm记录，执行成功后传入对象及返回对象的主键属性值不为null
	 * @param merchantOrderConfirm 新增的MerchantOrderConfirm数据（如果无特殊需求，新增对象的主键值请保留为null）
	 * @param operator 操作人对象
	 * @return
	 */
	@Transactional
	public ResultInfo<MerchantOrderConfirm> addMerchantOrderConfirm(MerchantOrderConfirm merchantOrderConfirm, Operator operator) {
		ResultInfo<MerchantOrderConfirm> resultInfo = new ResultInfo<MerchantOrderConfirm>();
		
		if (merchantOrderConfirm == null) { //传入参数无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG );
			log.info(Constant.ERR_MSG_INVALID_ARG + " merchantOrderConfirm = " + merchantOrderConfirm);
		} else {
			try {
				//如果传入参数的主键为null，则调用生成主键的方法获取新的主键
				if (merchantOrderConfirm.getId() == null) {
					merchantOrderConfirm.setId(this.generatePK());
				}
				//如果传入的操作人不为null，则设置新增记录的操作人类型和操作人id
				if (operator != null) {
					merchantOrderConfirm.setOperatorType(operator.getOperatorType());
					merchantOrderConfirm.setOperatorId(operator.getOperatorId());
				}
				
				//设置创建时间和更新时间为当前时间
				Date now = new Date();
				merchantOrderConfirm.setCreateTime(now);
				merchantOrderConfirm.setUpdateTime(now);
				
				//填充默认值
				this.fillDefaultValues(merchantOrderConfirm);
				
				//调用Dao执行插入操作
				merchantOrderConfirmDao.add(merchantOrderConfirm);
				resultInfo.setCode(Constant.SECCUESS);
				resultInfo.setData(merchantOrderConfirm);
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
	 * 根据主键，更新一条MerchantOrderConfirm记录
	 * @param merchantOrderConfirm 更新的MerchantOrderConfirm数据，且其主键不能为空
	 * @param operator 操作人对象
	 * @return
	 */
	@Transactional
	public ResultInfo<MerchantOrderConfirm> updateMerchantOrderConfirm(MerchantOrderConfirm merchantOrderConfirm, Operator operator) {
		ResultInfo<MerchantOrderConfirm> resultInfo = new ResultInfo<MerchantOrderConfirm>();
		
		if (merchantOrderConfirm == null || merchantOrderConfirm.getId() == null) { //传入参数无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG );
			log.info(Constant.ERR_MSG_INVALID_ARG + " merchantOrderConfirm = " + merchantOrderConfirm);
		} else {
			try {
				//如果传入的操作人不为null，则设置更新记录的操作人类型和操作人id
				if (operator != null) {
					merchantOrderConfirm.setOperatorType(operator.getOperatorType());
					merchantOrderConfirm.setOperatorId(operator.getOperatorId());
				}
				
				//设置更新时间为当前时间
				merchantOrderConfirm.setUpdateTime(new Date());
				
				//调用Dao执行更新操作，并判断更新语句执行结果
				int count = merchantOrderConfirmDao.update(merchantOrderConfirm);			
				if (count > 0) {
					resultInfo.setCode(Constant.SECCUESS);
				} else {
					resultInfo.setCode(Constant.FAIL);
				}
				resultInfo.setData(merchantOrderConfirm);
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
	 * 为MerchantOrderConfirm对象设置默认值
	 * @param obj
	 */
	public void fillDefaultValues(MerchantOrderConfirm obj) {
		if (obj != null) {
		}
	}

}
