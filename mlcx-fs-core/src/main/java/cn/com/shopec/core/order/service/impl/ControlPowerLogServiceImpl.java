package cn.com.shopec.core.order.service.impl;

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
import cn.com.shopec.core.member.dao.MemberDao;
import cn.com.shopec.core.member.model.Member;
import cn.com.shopec.core.member.service.MemberService;
import cn.com.shopec.core.order.dao.ControlPowerLogDao;
import cn.com.shopec.core.order.model.ControlPowerLog;
import cn.com.shopec.core.order.service.ControlPowerLogService;


/**
 * ControlPowerLog 服务实现类
 */
@Service
public class ControlPowerLogServiceImpl implements ControlPowerLogService {

	private static final Log log = LogFactory.getLog(ControlPowerLogServiceImpl.class);
	
	@Resource
	private ControlPowerLogDao controlPowerLogDao;
	@Resource
	private  MemberDao memberDao;
	

	/**
	 * 根据查询条件，查询并返回ControlPowerLog的列表
	 * @param q 查询条件
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<ControlPowerLog> getControlPowerLogList(Query q) {
		List<ControlPowerLog> list = null;
		try {
			//直接调用Dao方法进行查询
			list = controlPowerLogDao.queryAll(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		//如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<ControlPowerLog>(0) : list;
		return list; 
	}
	
	/**
	 * 根据查询条件，分页查询并返回ControlPowerLog的分页结果
	 * @param q 查询条件
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public PageFinder<ControlPowerLog> getControlPowerLogPagedList(Query q) {
		PageFinder<ControlPowerLog> page = new PageFinder<ControlPowerLog>();
		
		List<ControlPowerLog> list = null;
		long rowCount = 0L;
		
		try {
			//调用dao查询满足条件的分页数据
			list = controlPowerLogDao.pageList(q);
			if(list != null && list.size()>0){
				for (ControlPowerLog controlPowerLog : list) {
					if(controlPowerLog.getMemberNo() != null && !"".equals(controlPowerLog.getMemberNo())){
						Member m= memberDao.getMemberBasicInfo(controlPowerLog.getMemberNo());
						if(m!= null){
							controlPowerLog.setMemberName(m.getMemberName());
						}
					}
				}
			}
			//调用dao统计满足条件的记录总数
			rowCount = controlPowerLogDao.count(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		
		//如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<ControlPowerLog>(0) : list;
	
		//将分页数据和记录总数设置到分页结果对象中
		page.setData(list);
		page.setRowCount(rowCount);
		
		return page;
	}	
	
	/**
	 * 根据主键，查询并返回一个ControlPowerLog对象
	 * @param id 主键id
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public ControlPowerLog getControlPowerLog(String  id) {
		ControlPowerLog obj = null;
		if (id == null) { //传入的主键无效时直接返回null
			log.info(Constant.ERR_MSG_INVALID_ARG + " id = " + id);
			return obj;
		}
		try {
			//调用dao，根据主键查询
			obj = controlPowerLogDao.get(id); 
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		
		return obj;
	}

	/**
	 * 根据主键数组，查询并返回一组ControlPowerLog对象
	 * @param ids 主键数组，数组中的主键值应当无重复值，如有重复值时，则返回的列表长度可能小于传入的数组长度
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<ControlPowerLog> getControlPowerLogByIds(String[] ids) {
		List<ControlPowerLog> list = null;
		if (ids == null || ids.length == 0) {
			log.info(Constant.ERR_MSG_INVALID_ARG + " ids is null or empty array.");
		} else {
			try {
				//调用dao，根据主键数组查询
				list = controlPowerLogDao.getByIds(ids);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
		}
		
		//如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<ControlPowerLog>(0) : list;

		return list;
	}	
	
	/**
	 * 根据主键，删除特定的ControlPowerLog记录
	 * @param id 主键id
	 * @param operator 操作人对象
	 * @return
	 */
	@Transactional
	public ResultInfo<ControlPowerLog> delControlPowerLog(String id, Operator operator) {
		ResultInfo<ControlPowerLog> resultInfo = new ResultInfo<ControlPowerLog>();
		if (id == null) { //传入的主键无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG );
			log.info(Constant.ERR_MSG_INVALID_ARG + " id = " + id);
			return resultInfo;
		}
		try {
		    //调用Dao执行删除，并判断删除语句执行结果
			int count = controlPowerLogDao.delete(id);
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
	 * 新增一条ControlPowerLog记录，执行成功后传入对象及返回对象的主键属性值不为null
	 * @param controlPowerLog 新增的ControlPowerLog数据（如果无特殊需求，新增对象的主键值请保留为null）
	 * @param operator 操作人对象
	 * @return
	 */
	@Transactional
	public ResultInfo<ControlPowerLog> addControlPowerLog(ControlPowerLog controlPowerLog, Operator operator) {
		ResultInfo<ControlPowerLog> resultInfo = new ResultInfo<ControlPowerLog>();
		
		if (controlPowerLog == null) { //传入参数无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG );
			log.info(Constant.ERR_MSG_INVALID_ARG + " controlPowerLog = " + controlPowerLog);
		} else {
			try {
				//如果传入参数的主键为null，则调用生成主键的方法获取新的主键
				if (controlPowerLog.getPowerLogNo() == null) {
					controlPowerLog.setPowerLogNo(this.generatePK());
				}
				//如果传入的操作人不为null，则设置新增记录的操作人id
				if (operator != null) {
					
				}
				
				//设置创建时间和更新时间为当前时间
				Date now = new Date();
				
				
				//填充默认值
				this.fillDefaultValues(controlPowerLog);
				
				//调用Dao执行插入操作
				controlPowerLogDao.add(controlPowerLog);
				resultInfo.setCode(Constant.SECCUESS);
				resultInfo.setData(controlPowerLog);
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
	 * 根据主键，更新一条ControlPowerLog记录
	 * @param controlPowerLog 更新的ControlPowerLog数据，且其主键不能为空
	 * @param operator 操作人对象
	 * @return
	 */
	@Transactional
	public ResultInfo<ControlPowerLog> updateControlPowerLog(ControlPowerLog controlPowerLog, Operator operator) {
		ResultInfo<ControlPowerLog> resultInfo = new ResultInfo<ControlPowerLog>();
		
		if (controlPowerLog == null || controlPowerLog.getPowerLogNo() == null) { //传入参数无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG );
			log.info(Constant.ERR_MSG_INVALID_ARG + " controlPowerLog = " + controlPowerLog);
		} else {
			try {
				//如果传入的操作人不为null，则设置更新记录的操作人id
				if (operator != null) {
					
				}
				
				//设置更新时间为当前时间
				
				
				//调用Dao执行更新操作，并判断更新语句执行结果
				int count = controlPowerLogDao.update(controlPowerLog);			
				if (count > 0) {
					resultInfo.setCode(Constant.SECCUESS);
				} else {
					resultInfo.setCode(Constant.FAIL);
				}
				resultInfo.setData(controlPowerLog);
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
	 * 为ControlPowerLog对象设置默认值
	 * @param obj
	 */
	public void fillDefaultValues(ControlPowerLog obj) {
		if (obj != null) {
		}
	}

}
