package cn.com.shopec.core.car.service.impl;

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
import cn.com.shopec.common.utils.ECDateUtils;
import cn.com.shopec.core.car.dao.CarOnlineLogDao;
import cn.com.shopec.core.car.model.CarOnlineLog;
import cn.com.shopec.core.car.service.CarOnlineLogService;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;


/**
 * CarOnlineLog 服务实现类
 */
@Service
public class CarOnlineLogServiceImpl implements CarOnlineLogService {

	private static final Log log = LogFactory.getLog(CarOnlineLogServiceImpl.class);
	
	@Resource
	private CarOnlineLogDao carOnlineLogDao;
	

	/**
	 * 根据查询条件，查询并返回CarOnlineLog的列表
	 * @param q 查询条件
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<CarOnlineLog> getCarOnlineLogList(Query q) {
		List<CarOnlineLog> list = null;
		try {
			//直接调用Dao方法进行查询
			list = carOnlineLogDao.queryAll(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		//如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<CarOnlineLog>(0) : list;
		return list; 
	}
	
	/**
	 * 根据查询条件，分页查询并返回CarOnlineLog的分页结果
	 * @param q 查询条件
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public PageFinder<CarOnlineLog> getCarOnlineLogPagedList(Query q) {
		PageFinder<CarOnlineLog> page = new PageFinder<CarOnlineLog>();
		
		List<CarOnlineLog> list = null;
		long rowCount = 0L;
		
		try {
			//调用dao查询满足条件的分页数据
			list = carOnlineLogDao.pageList(q);
			//调用dao统计满足条件的记录总数
			rowCount = carOnlineLogDao.count(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		
		//如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<CarOnlineLog>(0) : list;
	
		//将分页数据和记录总数设置到分页结果对象中
		page.setData(list);
		page.setRowCount(rowCount);
		
		return page;
	}	
	
	
	/**
	 * 根据查询条件，分页查询并返回CarOnlineLog的分页结果   并且查出操作人
	 * @param q 查询条件
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public PageFinder<CarOnlineLog> getCarOnlineLogPagedLists(Query q) {
		PageFinder<CarOnlineLog> page = new PageFinder<CarOnlineLog>();
		
		List<CarOnlineLog> list = null;
		long rowCount = 0L;
		
		try {
			//调用dao查询满足条件的分页数据
			list = carOnlineLogDao.pageLists(q);
			
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		
		//如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<CarOnlineLog>(0) : list;
	
		//将分页数据和记录总数设置到分页结果对象中
		page.setData(list);
		page.setRowCount(rowCount);
		
		return page;
	}	
	
	/**
	 * 根据主键，查询并返回一个CarOnlineLog对象
	 * @param id 主键id
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public CarOnlineLog getCarOnlineLog(String id) {
		CarOnlineLog obj = null;
		if (id == null || id.length() == 0) { //传入的主键无效时直接返回null
			log.info(Constant.ERR_MSG_INVALID_ARG + " id = " + id);
			return obj;
		}
		try {
			//调用dao，根据主键查询
			obj = carOnlineLogDao.get(id); 
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		
		return obj;
	}

	/**
	 * 根据主键数组，查询并返回一组CarOnlineLog对象
	 * @param ids 主键数组，数组中的主键值应当无重复值，如有重复值时，则返回的列表长度可能小于传入的数组长度
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<CarOnlineLog> getCarOnlineLogByIds(String[] ids) {
		List<CarOnlineLog> list = null;
		if (ids == null || ids.length == 0) {
			log.info(Constant.ERR_MSG_INVALID_ARG + " ids is null or empty array.");
		} else {
			try {
				//调用dao，根据主键数组查询
				list = carOnlineLogDao.getByIds(ids);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
		}
		
		//如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<CarOnlineLog>(0) : list;

		return list;
	}	
	
	/**
	 * 根据主键，删除特定的CarOnlineLog记录
	 * @param id 主键id
	 * @param operator 操作人对象
	 * @return
	 */
	@Transactional
	public ResultInfo<CarOnlineLog> delCarOnlineLog(String id, Operator operator) {
		ResultInfo<CarOnlineLog> resultInfo = new ResultInfo<CarOnlineLog>();
		if (id == null || id.length() == 0) { //传入的主键无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG );
			log.info(Constant.ERR_MSG_INVALID_ARG + " id = " + id);
			return resultInfo;
		}
		try {
		    //调用Dao执行删除，并判断删除语句执行结果
			int count = carOnlineLogDao.delete(id);
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
	 * 新增一条CarOnlineLog记录，执行成功后传入对象及返回对象的主键属性值不为null
	 * @param carOnlineLog 新增的CarOnlineLog数据（如果无特殊需求，新增对象的主键值请保留为null）
	 * @param operator 操作人对象
	 * @return
	 */
	@Transactional
	public ResultInfo<CarOnlineLog> addCarOnlineLog(CarOnlineLog carOnlineLog, Operator operator) {
		ResultInfo<CarOnlineLog> resultInfo = new ResultInfo<CarOnlineLog>();
		
		if (carOnlineLog == null) { //传入参数无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG );
			log.info(Constant.ERR_MSG_INVALID_ARG + " carOnlineLog = " + carOnlineLog);
		} else {
			try {
				//如果传入参数的主键为null，则调用生成主键的方法获取新的主键
				if (carOnlineLog.getLogId() == null) {
					carOnlineLog.setLogId(this.generatePK());
				}
				//如果传入的操作人不为null，则设置新增记录的操作人类型和操作人id
				if (operator != null) {
					carOnlineLog.setOperatorType(operator.getOperatorType());
					carOnlineLog.setOperatorId(operator.getOperatorId());
				}
				
				//设置创建时间和更新时间为当前时间
				Date now = new Date();
				carOnlineLog.setCreateTime(now);
				carOnlineLog.setUpdateTime(now);
				
				//填充默认值
				this.fillDefaultValues(carOnlineLog);
				
				//调用Dao执行插入操作
				carOnlineLogDao.add(carOnlineLog);
				resultInfo.setCode(Constant.SECCUESS);
				resultInfo.setData(carOnlineLog);
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
	 * 根据主键，更新一条CarOnlineLog记录
	 * @param carOnlineLog 更新的CarOnlineLog数据，且其主键不能为空
	 * @param operator 操作人对象
	 * @return
	 */
	@Transactional
	public ResultInfo<CarOnlineLog> updateCarOnlineLog(CarOnlineLog carOnlineLog, Operator operator) {
		ResultInfo<CarOnlineLog> resultInfo = new ResultInfo<CarOnlineLog>();
		
		if (carOnlineLog == null || carOnlineLog.getLogId() == null) { //传入参数无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG );
			log.info(Constant.ERR_MSG_INVALID_ARG + " carOnlineLog = " + carOnlineLog);
		} else {
			try {
				//如果传入的操作人不为null，则设置更新记录的操作人类型和操作人id
				if (operator != null) {
					carOnlineLog.setOperatorType(operator.getOperatorType());
					carOnlineLog.setOperatorId(operator.getOperatorId());
				}
				
				//设置更新时间为当前时间
				carOnlineLog.setUpdateTime(new Date());
				
				//调用Dao执行更新操作，并判断更新语句执行结果
				int count = carOnlineLogDao.update(carOnlineLog);			
				if (count > 0) {
					resultInfo.setCode(Constant.SECCUESS);
				} else {
					resultInfo.setCode(Constant.FAIL);
				}
				resultInfo.setData(carOnlineLog);
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
		return String.valueOf(new Date().getTime() * 10000 + new Random().nextInt(10000));
	
	}
	
	/**
	 * 为CarOnlineLog对象设置默认值
	 * @param obj
	 */
	public void fillDefaultValues(CarOnlineLog obj) {
		if (obj != null) {
		}
	}
	
	@Override
	public Map<String, Object> getCarLine10CountMap() {
		String dateArray[] = new String[10];
		Object onlineArray[] = new Object[]{0,0,0,0,0,0,0,0,0,0};
		Object offlineArray[] = new Object[]{0,0,0,0,0,0,0,0,0,0};
		Date date = new Date();
		for(int day = -9; day <= 0; day++){
			String d = ECDateUtils.formatDate(ECDateUtils.getDateAfter(date, day), "MM-dd");
			dateArray[day+9] = d;
		}
		List<Map<String, Object>> data = carOnlineLogDao.getCarLineDay10CountMap();
		if(data != null && !data.isEmpty()){
			for(Map<String, Object> d : data){
				for(int i = 0; i < dateArray.length; i++){
					if(dateArray[i].equals((String)d.get("date"))){
						onlineArray[i] = d.get("onlineNum");
						offlineArray[i] = d.get("offlineNum");
					}
				}
			}
		}
		Map<String, Object> map = new HashMap<>();
		map.put("dateArray", dateArray);
		map.put("onlineArray", onlineArray);
		map.put("offlineArray", offlineArray);
		return map;
	}

}
