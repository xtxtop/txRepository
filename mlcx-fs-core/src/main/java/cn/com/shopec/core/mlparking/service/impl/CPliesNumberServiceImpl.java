package cn.com.shopec.core.mlparking.service.impl;

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
import cn.com.shopec.core.mlparking.dao.CPliesNumberDao;
import cn.com.shopec.core.mlparking.model.CPliesNumber;
import cn.com.shopec.core.mlparking.service.CPliesNumberService;

/**
 * 楼层表 服务实现类
 */
@Service
public class CPliesNumberServiceImpl implements CPliesNumberService {

	private static final Log log = LogFactory.getLog(CPliesNumberServiceImpl.class);
	
	@Resource
	private CPliesNumberDao cPliesNumberDao;
	

	/**
	 * 根据查询条件，查询并返回CPliesNumber的列表
	 * @param q 查询条件
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<CPliesNumber> getCPliesNumberList(Query q) {
		List<CPliesNumber> list = null;
		try {
			//直接调用Dao方法进行查询
			list = cPliesNumberDao.queryAll(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		//如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<CPliesNumber>(0) : list;
		return list; 
	}
	
	/**
	 * 根据查询条件，分页查询并返回CPliesNumber的分页结果
	 * @param q 查询条件
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public PageFinder<CPliesNumber> getCPliesNumberPagedList(Query q) {
		PageFinder<CPliesNumber> page = new PageFinder<CPliesNumber>();
		
		List<CPliesNumber> list = null;
		long rowCount = 0L;
		
		try {
			//调用dao查询满足条件的分页数据
			list = cPliesNumberDao.pageList(q);
			//调用dao统计满足条件的记录总数
			rowCount = cPliesNumberDao.count(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		
		//如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<CPliesNumber>(0) : list;
	
		//将分页数据和记录总数设置到分页结果对象中
		page.setData(list);
		page.setRowCount(rowCount);
		
		return page;
	}	
	
	/**
	 * 根据主键，查询并返回一个CPliesNumber对象
	 * @param id 主键id
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public CPliesNumber getCPliesNumber(String id) {
		CPliesNumber obj = null;
		if (id == null || id.length() == 0) { //传入的主键无效时直接返回null
			log.info(Constant.ERR_MSG_INVALID_ARG + " id = " + id);
			return obj;
		}
		try {
			//调用dao，根据主键查询
			obj = cPliesNumberDao.get(id); 
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		
		return obj;
	}

	/**
	 * 根据主键数组，查询并返回一组CPliesNumber对象
	 * @param ids 主键数组，数组中的主键值应当无重复值，如有重复值时，则返回的列表长度可能小于传入的数组长度
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<CPliesNumber> getCPliesNumberByIds(String[] ids) {
		List<CPliesNumber> list = null;
		if (ids == null || ids.length == 0) {
			log.info(Constant.ERR_MSG_INVALID_ARG + " ids is null or empty array.");
		} else {
			try {
				//调用dao，根据主键数组查询
				list = cPliesNumberDao.getByIds(ids);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
		}
		
		//如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<CPliesNumber>(0) : list;

		return list;
	}	
	
	/**
	 * 根据主键，删除特定的CPliesNumber记录
	 * @param id 主键id
	 * @param operator 操作人对象
	 * @return
	 */
	@Transactional
	public ResultInfo<CPliesNumber> delCPliesNumber(String id, Operator operator) {
		ResultInfo<CPliesNumber> resultInfo = new ResultInfo<CPliesNumber>();
		if (id == null || id.length() == 0) { //传入的主键无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG );
			log.info(Constant.ERR_MSG_INVALID_ARG + " id = " + id);
			return resultInfo;
		}
		try {
		    //调用Dao执行删除，并判断删除语句执行结果
			int count = cPliesNumberDao.delete(id);
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
	 * 新增一条CPliesNumber记录，执行成功后传入对象及返回对象的主键属性值不为null
	 * @param cPliesNumber 新增的CPliesNumber数据（如果无特殊需求，新增对象的主键值请保留为null）
	 * @param operator 操作人对象
	 * @return
	 */
	@Transactional
	public ResultInfo<CPliesNumber> addCPliesNumber(CPliesNumber cPliesNumber, Operator operator) {
		ResultInfo<CPliesNumber> resultInfo = new ResultInfo<CPliesNumber>();
		
		if (cPliesNumber == null) { //传入参数无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG );
			log.info(Constant.ERR_MSG_INVALID_ARG + " cPliesNumber = " + cPliesNumber);
		} else {
			try {
				//如果传入参数的主键为null，则调用生成主键的方法获取新的主键
				if (cPliesNumber.getParkingPliesNo() == null) {
					cPliesNumber.setParkingPliesNo(this.generatePK());
				}
				//如果传入的操作人不为null，则设置新增记录的操作人类型和操作人id
				if (operator != null) {
					cPliesNumber.setOperatorType(operator.getOperatorType());
					cPliesNumber.setOperatorId(operator.getOperatorId());
				}
				
				//设置创建时间和更新时间为当前时间
				Date now = new Date();
				cPliesNumber.setCreateTime(now);
				cPliesNumber.setUpdateTime(now);
				
				//填充默认值
				this.fillDefaultValues(cPliesNumber);
				
				//调用Dao执行插入操作
				cPliesNumberDao.add(cPliesNumber);
				resultInfo.setCode(Constant.SECCUESS);
				resultInfo.setData(cPliesNumber);
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
	 * 根据主键，更新一条CPliesNumber记录
	 * @param cPliesNumber 更新的CPliesNumber数据，且其主键不能为空
	 * @param operator 操作人对象
	 * @return
	 */
	@Transactional
	public ResultInfo<CPliesNumber> updateCPliesNumber(CPliesNumber cPliesNumber, Operator operator) {
		ResultInfo<CPliesNumber> resultInfo = new ResultInfo<CPliesNumber>();
		
		if (cPliesNumber == null || cPliesNumber.getParkingPliesNo() == null) { //传入参数无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG );
			log.info(Constant.ERR_MSG_INVALID_ARG + " cPliesNumber = " + cPliesNumber);
		} else {
			try {
				//如果传入的操作人不为null，则设置更新记录的操作人类型和操作人id
				if (operator != null) {
					cPliesNumber.setOperatorType(operator.getOperatorType());
					cPliesNumber.setOperatorId(operator.getOperatorId());
				}
				
				//设置更新时间为当前时间
				cPliesNumber.setUpdateTime(new Date());
				
				//调用Dao执行更新操作，并判断更新语句执行结果
				int count = cPliesNumberDao.update(cPliesNumber);			
				if (count > 0) {
					resultInfo.setCode(Constant.SECCUESS);
				} else {
					resultInfo.setCode(Constant.FAIL);
				}
				resultInfo.setData(cPliesNumber);
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
		return "N"+String.valueOf(new Date().getTime())+(int)(Math.random()*9999);
	}
	
	/**
	 * 为CPliesNumber对象设置默认值
	 * @param obj
	 */
	public void fillDefaultValues(CPliesNumber obj) {
		if (obj != null) {
		}
	}

}
