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
import cn.com.shopec.core.marketing.dao.CarRedPacketDao;
import cn.com.shopec.core.marketing.model.CarRedPacket;
import cn.com.shopec.core.marketing.service.CarRedPacketService;

/**
 * CarRedPacket 服务实现类
 */
@Service
public class CarRedPacketServiceImpl implements CarRedPacketService {

	private static final Log log = LogFactory.getLog(CarRedPacketServiceImpl.class);
	
	@Resource
	private CarRedPacketDao carRedPacketDao;
	

	/**
	 * 根据查询条件，查询并返回CarRedPacket的列表
	 * @param q 查询条件
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<CarRedPacket> getCarRedPacketList(Query q) {
		List<CarRedPacket> list = null;
		try {
			//直接调用Dao方法进行查询
			list = carRedPacketDao.queryAll(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		//如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<CarRedPacket>(0) : list;
		return list; 
	}
	
	/**
	 * 根据查询条件，分页查询并返回CarRedPacket的分页结果
	 * @param q 查询条件
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public PageFinder<CarRedPacket> getCarRedPacketPagedList(Query q) {
		PageFinder<CarRedPacket> page = new PageFinder<CarRedPacket>();
		
		List<CarRedPacket> list = null;
		long rowCount = 0L;
		
		try {
			//调用dao查询满足条件的分页数据
			list = carRedPacketDao.pageList(q);
			//调用dao统计满足条件的记录总数
			rowCount = carRedPacketDao.count(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		
		//如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<CarRedPacket>(0) : list;
	
		//将分页数据和记录总数设置到分页结果对象中
		page.setData(list);
		page.setRowCount(rowCount);
		
		return page;
	}	
	
	/**
	 * 根据主键，查询并返回一个CarRedPacket对象
	 * @param id 主键id
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public CarRedPacket getCarRedPacket(String id) {
		CarRedPacket obj = null;
		if (id == null || id.length() == 0) { //传入的主键无效时直接返回null
			log.info(Constant.ERR_MSG_INVALID_ARG + " id = " + id);
			return obj;
		}
		try {
			//调用dao，根据主键查询
			obj = carRedPacketDao.get(id); 
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		
		return obj;
	}
	/**
	 * 根据红包车状态获取红包车记录
	 * @param carPlateNo 
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public CarRedPacket getCarRedPacketByCarPlateNo(String carPlateNo,String carRedPacketStatus){
		CarRedPacket obj = null;
		if (carPlateNo == null || carPlateNo.length() == 0) { 
			log.info(Constant.ERR_MSG_INVALID_ARG + " carPlateNo = " + carPlateNo);
			return obj;
		}
		if (carRedPacketStatus == null || carRedPacketStatus.length() == 0) { 
			log.info(Constant.ERR_MSG_INVALID_ARG + " carRedPacketStatus = " + carRedPacketStatus);
			return obj;
		}
		try {
			//调用dao，根据主键查询
			obj = carRedPacketDao.getCarRedPacketByCarPlateNo(carPlateNo,carRedPacketStatus);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		
		return obj;
	}
	/**
	 * 获取红包车所在的场站编号
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public String getCarRedParketLocationParkNos(Integer carRedPacketStatus){
		String obj = null;
		try {
			//调用dao，根据主键查询
			obj = carRedPacketDao.getCarRedParketLocationParkNos(carRedPacketStatus);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		
		return obj;
	}
	
	/**
	 * 根据主键数组，查询并返回一组CarRedPacket对象
	 * @param ids 主键数组，数组中的主键值应当无重复值，如有重复值时，则返回的列表长度可能小于传入的数组长度
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<CarRedPacket> getCarRedPacketByIds(String[] ids) {
		List<CarRedPacket> list = null;
		if (ids == null || ids.length == 0) {
			log.info(Constant.ERR_MSG_INVALID_ARG + " ids is null or empty array.");
		} else {
			try {
				//调用dao，根据主键数组查询
				list = carRedPacketDao.getByIds(ids);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
		}
		
		//如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<CarRedPacket>(0) : list;

		return list;
	}	
	
	/**
	 * 根据主键，删除特定的CarRedPacket记录
	 * @param id 主键id
	 * @param operator 操作人对象
	 * @return
	 */
	@Transactional
	public ResultInfo<CarRedPacket> delCarRedPacket(String id, Operator operator) {
		ResultInfo<CarRedPacket> resultInfo = new ResultInfo<CarRedPacket>();
		if (id == null || id.length() == 0) { //传入的主键无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG );
			log.info(Constant.ERR_MSG_INVALID_ARG + " id = " + id);
			return resultInfo;
		}
		try {
		    //调用Dao执行删除，并判断删除语句执行结果
			int count = carRedPacketDao.delete(id);
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
	 * 新增一条CarRedPacket记录，执行成功后传入对象及返回对象的主键属性值不为null
	 * @param carRedPacket 新增的CarRedPacket数据（如果无特殊需求，新增对象的主键值请保留为null）
	 * @param operator 操作人对象
	 * @return
	 */
	@Transactional
	public ResultInfo<CarRedPacket> addCarRedPacket(CarRedPacket carRedPacket, Operator operator) {
		ResultInfo<CarRedPacket> resultInfo = new ResultInfo<CarRedPacket>();
		
		if (carRedPacket == null) { //传入参数无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG );
			log.info(Constant.ERR_MSG_INVALID_ARG + " carRedPacket = " + carRedPacket);
		} else {
			try {
				//如果传入参数的主键为null，则调用生成主键的方法获取新的主键
				if (carRedPacket.getCarRedPacketId() == null) {
					carRedPacket.setCarRedPacketId(this.generatePK());
				}
				//如果传入的操作人不为null，则设置新增记录的操作人类型和操作人id
				if (operator != null) {
					carRedPacket.setRedPacketOperatorId(operator.getOperatorId());
					carRedPacket.setRedPacketOperatorName(operator.getOperatorUserName());
					carRedPacket.setOperatorType(operator.getOperatorType());
					carRedPacket.setOperatorId(operator.getOperatorId());
				}
				//设置创建时间和更新时间为当前时间
				Date now = new Date();
				carRedPacket.setCreateTime(now);
				carRedPacket.setUpdateTime(now);
				
				//填充默认值
				this.fillDefaultValues(carRedPacket);
				//调用Dao执行插入操作
				carRedPacketDao.add(carRedPacket);
				resultInfo.setCode(Constant.SECCUESS);
				resultInfo.setData(carRedPacket);
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
	 * 根据主键，更新一条CarRedPacket记录
	 * @param carRedPacket 更新的CarRedPacket数据，且其主键不能为空
	 * @param operator 操作人对象
	 * @return
	 */
	@Transactional
	public ResultInfo<CarRedPacket> updateCarRedPacket(CarRedPacket carRedPacket, Operator operator) {
		ResultInfo<CarRedPacket> resultInfo = new ResultInfo<CarRedPacket>();
		
		if (carRedPacket == null || carRedPacket.getCarRedPacketId() == null) { //传入参数无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG );
			log.info(Constant.ERR_MSG_INVALID_ARG + " carRedPacket = " + carRedPacket);
		} else {
			try {
				//如果传入的操作人不为null，则设置更新记录的操作人类型和操作人id
				if (operator != null) {
					carRedPacket.setOperatorType(operator.getOperatorType());
					carRedPacket.setOperatorId(operator.getOperatorId());
					carRedPacket.setRedPacketOperatorId(operator.getOperatorId());
					carRedPacket.setRedPacketOperatorName(operator.getOperatorUserName());
				}
				
				//设置更新时间为当前时间
				carRedPacket.setUpdateTime(new Date());
				
				//调用Dao执行更新操作，并判断更新语句执行结果
				int count = carRedPacketDao.update(carRedPacket);			
				if (count > 0) {
					resultInfo.setCode(Constant.SECCUESS);
				} else {
					resultInfo.setCode(Constant.FAIL);
				}
				resultInfo.setData(carRedPacket);
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
	 * 为CarRedPacket对象设置默认值
	 * @param obj
	 */
	public void fillDefaultValues(CarRedPacket obj) {
		if (obj != null) {
		    if (obj.getIsCharge() == null) {
		    	obj.setIsCharge(0);
		    }
		    if (obj.getIsOrderAmountLimit() == null) {
		    	obj.setIsOrderAmountLimit(0);
		    }
		    if (obj.getRedPacketSendStatus() == null) {
		    	obj.setRedPacketSendStatus(0);
		    }
		    if (obj.getCarRedPacketStatus() == null) {
		    	obj.setCarRedPacketStatus(0);
		    }
		    if (obj.getIsAvailable()==null) {
				obj.setIsAvailable(0);
			}
		}
	}

	@Override
	public CarRedPacket getByCarPlateNo(String carPlateNo) {
		// TODO Auto-generated method stub
		return carRedPacketDao.getByCarPlateNo(carPlateNo);
	}

	

}
