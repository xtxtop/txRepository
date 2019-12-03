package cn.com.shopec.core.marketing.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import cn.com.shopec.core.marketing.dao.PeakHoursDao;
import cn.com.shopec.core.marketing.model.PeakHours;
import cn.com.shopec.core.marketing.service.PeakHoursService;

/**
 * PeakHours 服务实现类
 */
@Service
public class PeakHoursServiceImpl implements PeakHoursService {

	Logger log = LoggerFactory.getLogger(getClass());
	
	@Resource
	private PeakHoursDao peakHoursDao;
	

	/**
	 * 根据查询条件，查询并返回PeakHours的列表
	 * @param q 查询条件
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<PeakHours> getPeakHoursList(Query q) {
		List<PeakHours> list = null;
		try {
			//直接调用Dao方法进行查询
			list = peakHoursDao.queryAll(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		//如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<PeakHours>(0) : list;
		return list; 
	}
	
	/**
	 * 根据查询条件，分页查询并返回PeakHours的分页结果
	 * @param q 查询条件
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public PageFinder<PeakHours> getPeakHoursPagedList(Query q) {
		PageFinder<PeakHours> page = new PageFinder<PeakHours>();
		
		List<PeakHours> list = null;
		long rowCount = 0L;
		
		try {
			//调用dao查询满足条件的分页数据
			list = peakHoursDao.pageList(q);
			//调用dao统计满足条件的记录总数
			rowCount = peakHoursDao.count(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		
		//如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<PeakHours>(0) : list;
	
		//将分页数据和记录总数设置到分页结果对象中
		page.setData(list);
		page.setRowCount(rowCount);
		
		return page;
	}	
	
	/**
	 * 根据主键，查询并返回一个PeakHours对象
	 * @param id 主键id
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public PeakHours getPeakHours(String id) {
		PeakHours obj = null;
		if (id == null || id.length() == 0) { //传入的主键无效时直接返回null
			log.info(Constant.ERR_MSG_INVALID_ARG + " id = " + id);
			return obj;
		}
		try {
			//调用dao，根据主键查询
			obj = peakHoursDao.get(id); 
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		
		return obj;
	}

	/**
	 * 根据主键数组，查询并返回一组PeakHours对象
	 * @param ids 主键数组，数组中的主键值应当无重复值，如有重复值时，则返回的列表长度可能小于传入的数组长度
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<PeakHours> getPeakHoursByIds(String[] ids) {
		List<PeakHours> list = null;
		if (ids == null || ids.length == 0) {
			log.info(Constant.ERR_MSG_INVALID_ARG + " ids is null or empty array.");
		} else {
			try {
				//调用dao，根据主键数组查询
				list = peakHoursDao.getByIds(ids);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
		}
		
		//如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<PeakHours>(0) : list;

		return list;
	}	
	
	/**
	 * 根据主键，删除特定的PeakHours记录
	 * @param id 主键id
	 * @return
	 */
	@Transactional
	public ResultInfo<PeakHours> delPeakHours(String id) {
		ResultInfo<PeakHours> resultInfo = new ResultInfo<PeakHours>();
		if (id == null || id.length() == 0) { //传入的主键无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG );
			log.info(Constant.ERR_MSG_INVALID_ARG + " id = " + id);
			return resultInfo;
		}
		try {
		    //调用Dao执行删除，并判断删除语句执行结果
			int count = peakHoursDao.delete(id);
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
	 * 新增一条PeakHours记录，执行成功后传入对象及返回对象的主键属性值不为null
	 * @param peakHours 新增的PeakHours数据（如果无特殊需求，新增对象的主键值请保留为null）
	 * @return
	 */
	@Transactional
	public ResultInfo<PeakHours> addPeakHours(PeakHours peakHours) {
		ResultInfo<PeakHours> resultInfo = new ResultInfo<PeakHours>();
		
		if (peakHours == null) { //传入参数无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG );
			log.info(Constant.ERR_MSG_INVALID_ARG + " peakHours = " + peakHours);
		} else {
			try {
				//如果传入参数的主键为null，则调用生成主键的方法获取新的主键
				if (peakHours.getPeakHourId() == null) {
					peakHours.setPeakHourId(this.generatePK());
				}
				
				//设置创建时间和更新时间为当前时间
				Date now = new Date();
				peakHours.setCreateTime(now);
				peakHours.setUpdateTime(now);
				
				//填充默认值
				this.fillDefaultValues(peakHours);
				
				//调用Dao执行插入操作
				peakHoursDao.add(peakHours);
				resultInfo.setCode(Constant.SECCUESS);
				resultInfo.setData(peakHours);
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
	 * 根据主键，更新一条PeakHours记录
	 * @param peakHours 更新的PeakHours数据，且其主键不能为空
	 * @return
	 */
	@Transactional
	public ResultInfo<PeakHours> updatePeakHours(PeakHours peakHours) {
		ResultInfo<PeakHours> resultInfo = new ResultInfo<PeakHours>();
		
		if (peakHours == null || peakHours.getPeakHourId() == null) { //传入参数无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG );
			log.info(Constant.ERR_MSG_INVALID_ARG + " peakHours = " + peakHours);
		} else {
			try {
				
				//设置更新时间为当前时间
				peakHours.setUpdateTime(new Date());
				
				//调用Dao执行更新操作，并判断更新语句执行结果
				int count = peakHoursDao.update(peakHours);			
				if (count > 0) {
					resultInfo.setCode(Constant.SECCUESS);
				} else {
					resultInfo.setCode(Constant.FAIL);
				}
				resultInfo.setData(peakHours);
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
		return ECDateUtils.getCurrentDateTime("yyyyMMddHHmmss")+ECRandomUtils.getRandomNumberStr(4);
	}
	
	/**
	 * 为PeakHours对象设置默认值
	 * @param obj
	 */
	public void fillDefaultValues(PeakHours obj) {
		if (obj != null) {
		}
	}

	@Override
	public List<PeakHours> queryPeakHoursList(String ruleNo,String id) {
		List<PeakHours> list = null;
		try {
			//直接调用Dao方法进行查询
			list = peakHoursDao.queryPeakHoursList(ruleNo,id);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		//如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<PeakHours>(0) : list;
		return list; 
	}

}
