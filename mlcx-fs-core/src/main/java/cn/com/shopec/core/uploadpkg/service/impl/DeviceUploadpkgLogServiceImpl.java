package cn.com.shopec.core.uploadpkg.service.impl;

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
import cn.com.shopec.common.utils.ECDateUtils;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.device.dao.DeviceDao;
import cn.com.shopec.core.uploadpkg.dao.DeviceUploadpkgLogDao;
import cn.com.shopec.core.uploadpkg.model.DeviceUploadpkgLog;
import cn.com.shopec.core.uploadpkg.service.DeviceUploadpkgLogService;

/**
 * DeviceUploadpkgLog 服务实现类
 */
@Service
public class DeviceUploadpkgLogServiceImpl implements DeviceUploadpkgLogService {

	private static final Log log = LogFactory.getLog(DeviceUploadpkgLogServiceImpl.class);
	
	@Resource
	private DeviceUploadpkgLogDao deviceUploadpkgLogDao;
	@Resource
	private DeviceDao deviceDao;
	

	/**
	 * 根据查询条件，查询并返回DeviceUploadpkgLog的列表
	 * @param q 查询条件
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<DeviceUploadpkgLog> getDeviceUploadpkgLogList(Query q) {
		List<DeviceUploadpkgLog> list = null;
		try {
			//直接调用Dao方法进行查询
			list = deviceUploadpkgLogDao.queryAll(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		//如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<DeviceUploadpkgLog>(0) : list;
		return list; 
	}
	
	/**
	 * 根据查询条件，分页查询并返回DeviceUploadpkgLog的分页结果
	 * @param q 查询条件
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public PageFinder<DeviceUploadpkgLog> getDeviceUploadpkgLogPagedList(Query q) {
		PageFinder<DeviceUploadpkgLog> page = new PageFinder<DeviceUploadpkgLog>();
		
		List<DeviceUploadpkgLog> list = null;
		long rowCount = 0L;
		
		try {
			//调用dao查询满足条件的分页数据
			list = deviceUploadpkgLogDao.pageListDeviceUploadPkgLog(q);
			//调用dao统计满足条件的记录总数
			rowCount = deviceUploadpkgLogDao.countListDeviceUploadPkgLog(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		
		//如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<DeviceUploadpkgLog>(0) : list;
	
		//将分页数据和记录总数设置到分页结果对象中
		page.setData(list);
		page.setRowCount(rowCount);
		
		return page;
	}	
	
	/**
	 * 根据主键，查询并返回一个DeviceUploadpkgLog对象
	 * @param id 主键id
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public DeviceUploadpkgLog getDeviceUploadpkgLog(String id) {
		DeviceUploadpkgLog obj = null;
		if (id == null) { //传入的主键无效时直接返回null
			log.info(Constant.ERR_MSG_INVALID_ARG + " id = " + id);
			return obj;
		}
		try {
			//调用dao，根据主键查询
			obj = deviceUploadpkgLogDao.get(id); 
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		
		return obj;
	}

	/**
	 * 根据主键数组，查询并返回一组DeviceUploadpkgLog对象
	 * @param ids 主键数组，数组中的主键值应当无重复值，如有重复值时，则返回的列表长度可能小于传入的数组长度
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<DeviceUploadpkgLog> getDeviceUploadpkgLogByIds(String[] ids) {
		List<DeviceUploadpkgLog> list = null;
		if (ids == null || ids.length == 0) {
			log.info(Constant.ERR_MSG_INVALID_ARG + " ids is null or empty array.");
		} else {
			try {
				//调用dao，根据主键数组查询
				list = deviceUploadpkgLogDao.getByIds(ids);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
		}
		
		//如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<DeviceUploadpkgLog>(0) : list;

		return list;
	}	
	
	/**
	 * 根据主键，删除特定的DeviceUploadpkgLog记录
	 * @param id 主键id
	 * @param operator 操作人对象
	 * @return
	 */
	@Transactional
	public ResultInfo<DeviceUploadpkgLog> delDeviceUploadpkgLog(String id, Operator operator) {
		ResultInfo<DeviceUploadpkgLog> resultInfo = new ResultInfo<DeviceUploadpkgLog>();
		if (id == null) { //传入的主键无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG );
			log.info(Constant.ERR_MSG_INVALID_ARG + " id = " + id);
			return resultInfo;
		}
		try {
		    //调用Dao执行删除，并判断删除语句执行结果
			int count = deviceUploadpkgLogDao.delete(id);
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
	 * 新增一条DeviceUploadpkgLog记录，执行成功后传入对象及返回对象的主键属性值不为null
	 * @param deviceUploadpkgLog 新增的DeviceUploadpkgLog数据（如果无特殊需求，新增对象的主键值请保留为null）
	 * @param operator 操作人对象
	 * @return
	 */
	@Transactional
	public ResultInfo<DeviceUploadpkgLog> addDeviceUploadpkgLog(DeviceUploadpkgLog deviceUploadpkgLog, Operator operator) {
		ResultInfo<DeviceUploadpkgLog> resultInfo = new ResultInfo<DeviceUploadpkgLog>();
		
		if (deviceUploadpkgLog == null) { //传入参数无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG );
			log.info(Constant.ERR_MSG_INVALID_ARG + " deviceUploadpkgLog = " + deviceUploadpkgLog);
		} else {
			try {
				//如果传入参数的主键为null，则调用生成主键的方法获取新的主键
				if (deviceUploadpkgLog.getLogId() == null) {
					deviceUploadpkgLog.setLogId(this.generatePK());
				}
				//如果传入的操作人不为null，则设置新增记录的操作人id
				if (operator != null) {
					deviceUploadpkgLog.setOperatorId(operator.getOperatorId());
				}
				
				//设置创建时间和更新时间为当前时间
				Date now = new Date();
				deviceUploadpkgLog.setCreateTime(now);;
				deviceUploadpkgLog.setUpdateTime(now);
				
				//填充默认值
				this.fillDefaultValues(deviceUploadpkgLog);
				
				//调用Dao执行插入操作
				deviceUploadpkgLogDao.add(deviceUploadpkgLog);
				resultInfo.setCode(Constant.SECCUESS);
				resultInfo.setData(deviceUploadpkgLog);
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
	 * 根据主键，更新一条DeviceUploadpkgLog记录
	 * @param deviceUploadpkgLog 更新的DeviceUploadpkgLog数据，且其主键不能为空
	 * @param operator 操作人对象
	 * @return
	 */
	@Transactional
	public ResultInfo<DeviceUploadpkgLog> updateDeviceUploadpkgLog(DeviceUploadpkgLog deviceUploadpkgLog, Operator operator) {
		ResultInfo<DeviceUploadpkgLog> resultInfo = new ResultInfo<DeviceUploadpkgLog>();
		
		if (deviceUploadpkgLog == null || deviceUploadpkgLog.getLogId() == null) { //传入参数无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG );
			log.info(Constant.ERR_MSG_INVALID_ARG + " deviceUploadpkgLog = " + deviceUploadpkgLog);
		} else {
			try {
				//如果传入的操作人不为null，则设置更新记录的操作人id
				if (operator != null) {
					deviceUploadpkgLog.setOperatorId(operator.getOperatorId());;
				}
				
				//设置更新时间为当前时间
				deviceUploadpkgLog.setUpdateTime(new Date());
				
				//调用Dao执行更新操作，并判断更新语句执行结果
				int count = deviceUploadpkgLogDao.update(deviceUploadpkgLog);			
				if (count > 0) {
					resultInfo.setCode(Constant.SECCUESS);
				} else {
					resultInfo.setCode(Constant.FAIL);
				}
				resultInfo.setData(deviceUploadpkgLog);
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
		return System.nanoTime()+"";
	}
	
	/**
	 * 为DeviceUploadpkgLog对象设置默认值
	 * @param obj
	 */
	public void fillDefaultValues(DeviceUploadpkgLog obj) {
		if (obj != null) {
		}
	}

	//根据条件 查询 硬件报文
	@Override
	public PageFinder<DeviceUploadpkgLog> getDeviceUploadpkgLogDetailList(Query q) {
		PageFinder<DeviceUploadpkgLog> page = new PageFinder<DeviceUploadpkgLog>();
		
		List<DeviceUploadpkgLog> list = null;
		long rowCount = 0L;
		
		try {
			//调用dao查询满足条件的分页数据
			list = deviceUploadpkgLogDao.deviceUploadpkgLogList(q);
			//调用dao统计满足条件的记录总数
			
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		
		//如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<DeviceUploadpkgLog>(0) : list;
	
		//将分页数据和记录总数设置到分页结果对象中
		page.setData(list);
		page.setRowCount(rowCount);
		
		return page;
	}

	@Override
	public void delDeviceUploadpkgLog(int day) {
		List<DeviceUploadpkgLog> list = deviceUploadpkgLogDao.getDeviceUploadpkgLogMaxTime();
		if(!list.isEmpty()){
			for(DeviceUploadpkgLog deviceUploadpkgLog : list){
				if(deviceUploadpkgLog.getCreateTime() == null){
					continue;
				}
				Date createTime = ECDateUtils.getDateBefore(deviceUploadpkgLog.getCreateTime(), day);
				deviceUploadpkgLogDao.delDeviceUploadpkgLog(deviceUploadpkgLog.getDeviceSn(), createTime);
			}
		}
	}

}
