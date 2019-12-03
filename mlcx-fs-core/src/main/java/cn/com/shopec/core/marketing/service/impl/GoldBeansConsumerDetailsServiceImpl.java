package cn.com.shopec.core.marketing.service.impl;

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

import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.common.constants.Constant;
import cn.com.shopec.common.utils.ECDateUtils;
import cn.com.shopec.common.utils.ECRandomUtils;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.marketing.dao.GoldBeansConsumerDetailsDao;
import cn.com.shopec.core.marketing.model.GoldBeansConsumerDetails;
import cn.com.shopec.core.marketing.service.GoldBeansConsumerDetailsService;

/**
 * GoldBeansConsumerDetails 服务实现类
 */
@Service
public class GoldBeansConsumerDetailsServiceImpl implements GoldBeansConsumerDetailsService {

	private static final Log log = LogFactory.getLog(GoldBeansConsumerDetailsServiceImpl.class);
	
	@Resource
	private GoldBeansConsumerDetailsDao goldBeansConsumerDetailsDao;
	

	/**
	 * 根据查询条件，查询并返回GoldBeansConsumerDetails的列表
	 * @param q 查询条件
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<GoldBeansConsumerDetails> getGoldBeansConsumerDetailsList(Query q) {
		List<GoldBeansConsumerDetails> list = null;
		try {
			//直接调用Dao方法进行查询
			list = goldBeansConsumerDetailsDao.queryAll(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		//如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<GoldBeansConsumerDetails>(0) : list;
		return list; 
	}
	
	/**
	 * 根据查询条件，分页查询并返回GoldBeansConsumerDetails的分页结果
	 * @param q 查询条件
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public PageFinder<GoldBeansConsumerDetails> getGoldBeansConsumerDetailsPagedList(Query q) {
		PageFinder<GoldBeansConsumerDetails> page = new PageFinder<GoldBeansConsumerDetails>();
		
		List<GoldBeansConsumerDetails> list = null;
		long rowCount = 0L;
		
		try {
			//调用dao查询满足条件的分页数据
			list = goldBeansConsumerDetailsDao.pageList(q);
			//调用dao统计满足条件的记录总数
			rowCount = goldBeansConsumerDetailsDao.count(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		
		//如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<GoldBeansConsumerDetails>(0) : list;
	
		//将分页数据和记录总数设置到分页结果对象中
		page.setData(list);
		page.setRowCount(rowCount);
		
		return page;
	}	
	
	/**
	 * 根据主键，查询并返回一个GoldBeansConsumerDetails对象
	 * @param id 主键id
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public GoldBeansConsumerDetails getGoldBeansConsumerDetails(String id) {
		GoldBeansConsumerDetails obj = null;
		if (id == null || id.length() == 0) { //传入的主键无效时直接返回null
			log.info(Constant.ERR_MSG_INVALID_ARG + " id = " + id);
			return obj;
		}
		try {
			//调用dao，根据主键查询
			obj = goldBeansConsumerDetailsDao.get(id); 
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		
		return obj;
	}

	/**
	 * 根据主键数组，查询并返回一组GoldBeansConsumerDetails对象
	 * @param ids 主键数组，数组中的主键值应当无重复值，如有重复值时，则返回的列表长度可能小于传入的数组长度
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<GoldBeansConsumerDetails> getGoldBeansConsumerDetailsByIds(String[] ids) {
		List<GoldBeansConsumerDetails> list = null;
		if (ids == null || ids.length == 0) {
			log.info(Constant.ERR_MSG_INVALID_ARG + " ids is null or empty array.");
		} else {
			try {
				//调用dao，根据主键数组查询
				list = goldBeansConsumerDetailsDao.getByIds(ids);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
		}
		
		//如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<GoldBeansConsumerDetails>(0) : list;

		return list;
	}	
	
	/**
	 * 根据主键，删除特定的GoldBeansConsumerDetails记录
	 * @param id 主键id
	 * @return
	 */
	@Transactional
	public ResultInfo<GoldBeansConsumerDetails> delGoldBeansConsumerDetails(String id) {
		ResultInfo<GoldBeansConsumerDetails> resultInfo = new ResultInfo<GoldBeansConsumerDetails>();
		if (id == null || id.length() == 0) { //传入的主键无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG );
			log.info(Constant.ERR_MSG_INVALID_ARG + " id = " + id);
			return resultInfo;
		}
		try {
		    //调用Dao执行删除，并判断删除语句执行结果
			int count = goldBeansConsumerDetailsDao.delete(id);
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
	 * 新增一条GoldBeansConsumerDetails记录，执行成功后传入对象及返回对象的主键属性值不为null
	 * @param goldBeansConsumerDetails 新增的GoldBeansConsumerDetails数据（如果无特殊需求，新增对象的主键值请保留为null）
	 * @return
	 */
	@Transactional
	public ResultInfo<GoldBeansConsumerDetails> addGoldBeansConsumerDetails(GoldBeansConsumerDetails goldBeansConsumerDetails) {
		ResultInfo<GoldBeansConsumerDetails> resultInfo = new ResultInfo<GoldBeansConsumerDetails>();
		
		if (goldBeansConsumerDetails == null) { //传入参数无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG );
			log.info(Constant.ERR_MSG_INVALID_ARG + " goldBeansConsumerDetails = " + goldBeansConsumerDetails);
		} else {
			try {
				//如果传入参数的主键为null，则调用生成主键的方法获取新的主键
				if (goldBeansConsumerDetails.getConsumerDetailsId() == null) {
					goldBeansConsumerDetails.setConsumerDetailsId(this.generatePK());
				}
				
				//设置创建时间和更新时间为当前时间
				Date now = new Date();
				goldBeansConsumerDetails.setCreateTime(now);
				goldBeansConsumerDetails.setUpdateTime(now);
				
				//填充默认值
				this.fillDefaultValues(goldBeansConsumerDetails);
				
				//调用Dao执行插入操作
				goldBeansConsumerDetailsDao.add(goldBeansConsumerDetails);
				resultInfo.setCode(Constant.SECCUESS);
				resultInfo.setData(goldBeansConsumerDetails);
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
	 * 根据主键，更新一条GoldBeansConsumerDetails记录
	 * @param goldBeansConsumerDetails 更新的GoldBeansConsumerDetails数据，且其主键不能为空
	 * @return
	 */
	@Transactional
	public ResultInfo<GoldBeansConsumerDetails> updateGoldBeansConsumerDetails(GoldBeansConsumerDetails goldBeansConsumerDetails) {
		ResultInfo<GoldBeansConsumerDetails> resultInfo = new ResultInfo<GoldBeansConsumerDetails>();
		
		if (goldBeansConsumerDetails == null || goldBeansConsumerDetails.getConsumerDetailsId() == null) { //传入参数无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG );
			log.info(Constant.ERR_MSG_INVALID_ARG + " goldBeansConsumerDetails = " + goldBeansConsumerDetails);
		} else {
			try {
				//设置更新时间为当前时间
				goldBeansConsumerDetails.setUpdateTime(new Date());
				
				//调用Dao执行更新操作，并判断更新语句执行结果
				int count = goldBeansConsumerDetailsDao.update(goldBeansConsumerDetails);			
				if (count > 0) {
					resultInfo.setCode(Constant.SECCUESS);
				} else {
					resultInfo.setCode(Constant.FAIL);
				}
				resultInfo.setData(goldBeansConsumerDetails);
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
		return ECDateUtils.getCurrentDateTime("yyyyMMddHHmmss") + ECRandomUtils.getRandomNumberStr(4);
	}
	
	/**
	 * 为GoldBeansConsumerDetails对象设置默认值
	 * @param obj
	 */
	public void fillDefaultValues(GoldBeansConsumerDetails obj) {
		if (obj != null) {
		}
	}

}
