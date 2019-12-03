package cn.com.shopec.core.resource.service.impl;

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
import cn.com.shopec.core.resource.dao.ChargerDao;
import cn.com.shopec.core.resource.model.Charger;
import cn.com.shopec.core.resource.model.Park;
import cn.com.shopec.core.resource.service.ChargerService;
import cn.com.shopec.core.resource.service.ParkService;
import cn.com.shopec.core.system.model.DataDictItem;
import cn.com.shopec.core.system.service.DataDictItemService;

/**
 * 充电桩表 服务实现类
 */
@Service
public class ChargerServiceImpl implements ChargerService {

	private static final Log log = LogFactory.getLog(ChargerServiceImpl.class);
	
	@Resource
	private ChargerDao chargerDao;
	
	@Resource
	private ParkService parkService;
	

	@Resource
	private DataDictItemService dataDictItemService;
	/**
	 * 根据查询条件，查询并返回Charger的列表
	 * @param q 查询条件
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<Charger> getChargerList(Query q) {
		List<Charger> list = null;
		try {
			//已删除的不查出
			Charger charger = (Charger)q.getQ();
			if (charger != null) {
				charger.setIsDeleted(Constant.DEL_STATE_NO);
			}
					
			//直接调用Dao方法进行查询
			list = chargerDao.queryAll(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		//如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<Charger>(0) : list;
		return list; 
	}
	
	/**
	 * 根据查询条件，分页查询并返回Charger的分页结果
	 * @param q 查询条件
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public PageFinder<Charger> getChargerPagedList(Query q) {
		PageFinder<Charger> page = new PageFinder<Charger>();
		
		List<Charger> list = null;
		long rowCount = 0L;
		
		try {
			//已删除的不查出
			Charger charger = (Charger)q.getQ();
			if (charger != null) {
				charger.setIsDeleted(Constant.DEL_STATE_NO);
			}
					
			//调用dao查询满足条件的分页数据
			list = chargerDao.pageList(q);
			for(Charger c:list){
				if(c.getParkNo()!=null&& !"".equals(c.getParkNo())){
					Park park = parkService.getPark(c.getParkNo());
					if(park!=null){
						c.setParkName(park.getParkName());
					}
				}
			}
			//调用dao统计满足条件的记录总数
			rowCount = chargerDao.count(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		
		//如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<Charger>(0) : list;
	
		//将分页数据和记录总数设置到分页结果对象中
		page.setData(list);
		page.setRowCount(rowCount);
		
		return page;
	}	
	
	/**
	 * 根据主键，查询并返回一个Charger对象
	 * @param id 主键id
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public Charger getCharger(String id) {
		Charger obj = null;
		if (id == null || id.length() == 0) { //传入的主键无效时直接返回null
			log.info(Constant.ERR_MSG_INVALID_ARG + " id = " + id);
			return obj;
		}
		try {
			//调用dao，根据主键查询
			obj = chargerDao.get(id); 
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		
		return obj;
	}

	/**
	 * 根据主键数组，查询并返回一组Charger对象
	 * @param ids 主键数组，数组中的主键值应当无重复值，如有重复值时，则返回的列表长度可能小于传入的数组长度
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<Charger> getChargerByIds(String[] ids) {
		List<Charger> list = null;
		if (ids == null || ids.length == 0) {
			log.info(Constant.ERR_MSG_INVALID_ARG + " ids is null or empty array.");
		} else {
			try {
				//调用dao，根据主键数组查询
				list = chargerDao.getByIds(ids);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
		}
		
		//如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<Charger>(0) : list;

		return list;
	}	
	
	/**
	 * 根据主键，删除特定的Charger记录
	 * @param id 主键id
	 * @param operator 操作人对象
	 * @return
	 */
	@Transactional
	public ResultInfo<Charger> delCharger(String id, Operator operator) {
		ResultInfo<Charger> resultInfo = new ResultInfo<Charger>();
		if (id == null || id.length() == 0) { //传入的主键无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG );
			log.info(Constant.ERR_MSG_INVALID_ARG + " id = " + id);
			return resultInfo;
		}
		try {
			//逻辑删除，实际上执行update语句，以下设置待更新记录的主键、删除标识、更新时间、操作人信息等
			Charger charger = new Charger();
			charger.setChargerNo(id);
			charger.setIsDeleted(Constant.DEL_STATE_YES);
			charger.setUpdateTime(new Date());
			if (operator != null) { //最近操作人
				charger.setOperatorType(operator.getOperatorType());
				charger.setOperatorId(operator.getOperatorId());
			}
			
			//调用Dao执行更新操作，并判断更新语句执行结果
			int count = chargerDao.update(charger);			
			if (count > 0) {
				resultInfo.setCode(Constant.SECCUESS);
			} else {
				resultInfo.setCode(Constant.FAIL);
			}
			resultInfo.setData(charger);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_EXCEPTION_CATCHED);
		}
		return resultInfo;
	}
		
	/**
	 * 新增一条Charger记录，执行成功后传入对象及返回对象的主键属性值不为null
	 * @param charger 新增的Charger数据（如果无特殊需求，新增对象的主键值请保留为null）
	 * @param operator 操作人对象
	 * @return
	 */
	@Transactional
	public ResultInfo<Charger> addCharger(Charger charger, Operator operator) {
		ResultInfo<Charger> resultInfo = new ResultInfo<Charger>();
		
		if (charger == null) { //传入参数无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG );
			log.info(Constant.ERR_MSG_INVALID_ARG + " charger = " + charger);
		} else {
			try {
				//如果传入参数的主键为null，则调用生成主键的方法获取新的主键
				if (charger.getChargerNo() == null) {
					charger.setChargerNo(this.generatePK());
				}
				//如果传入的操作人不为null，则设置新增记录的操作人类型和操作人id
				if (operator != null) {
					charger.setOperatorType(operator.getOperatorType());
					charger.setOperatorId(operator.getOperatorId());
				}
				// 城市
				DataDictItem dataDictItemcity = dataDictItemService.getDataDictItem(charger.getCityId());
				if (dataDictItemcity != null) {
					charger.setCityName(dataDictItemcity.getItemValue());
				}
				//设置创建时间和更新时间为当前时间
				Date now = new Date();
				charger.setCreateTime(now);
				charger.setUpdateTime(now);
				
				//填充默认值
				this.fillDefaultValues(charger);
				
				//调用Dao执行插入操作
				chargerDao.add(charger);
				resultInfo.setCode(Constant.SECCUESS);
				resultInfo.setData(charger);
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
	 * 根据主键，更新一条Charger记录
	 * @param charger 更新的Charger数据，且其主键不能为空
	 * @param operator 操作人对象
	 * @return
	 */
	@Transactional
	public ResultInfo<Charger> updateCharger(Charger charger, Operator operator) {
		ResultInfo<Charger> resultInfo = new ResultInfo<Charger>();
		
		if (charger == null || charger.getChargerNo() == null) { //传入参数无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG );
			log.info(Constant.ERR_MSG_INVALID_ARG + " charger = " + charger);
		} else {
			try {
				//如果传入的操作人不为null，则设置更新记录的操作人类型和操作人id
				if (operator != null) {
					charger.setOperatorType(operator.getOperatorType());
					charger.setOperatorId(operator.getOperatorId());
				}
				
				//设置更新时间为当前时间
				charger.setUpdateTime(new Date());
				
				//调用Dao执行更新操作，并判断更新语句执行结果
				int count = chargerDao.update(charger);			
				if (count > 0) {
					resultInfo.setCode(Constant.SECCUESS);
				} else {
					resultInfo.setCode(Constant.FAIL);
				}
				resultInfo.setData(charger);
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
		return null;
	}
	
	/**
	 * 为Charger对象设置默认值
	 * @param obj
	 */
	public void fillDefaultValues(Charger obj) {
		if (obj != null) {
		    if (obj.getIsAvailable() == null) {
		    	obj.setIsAvailable(1);
		    }
		    if (obj.getIsDeleted() == null) {
		    	obj.setIsDeleted(0);
		    }
		}
	}

}
