package cn.com.shopec.core.dailyrental.service.impl;

import java.util.ArrayList;
import java.util.Date;
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
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.dailyrental.common.OrderConstant;
import cn.com.shopec.core.dailyrental.dao.MerchantOrderDao;
import cn.com.shopec.core.dailyrental.dao.OrderDayDao;
import cn.com.shopec.core.dailyrental.dao.ParkDayDao;
import cn.com.shopec.core.dailyrental.model.MerchantOrder;
import cn.com.shopec.core.dailyrental.model.OrderDay;
import cn.com.shopec.core.dailyrental.model.ParkDay;
import cn.com.shopec.core.dailyrental.service.MerchantOrderService;

/**
 * 商家订单表 服务实现类
 */
@Service
public class MerchantOrderServiceImpl implements MerchantOrderService {

	private static final Log log = LogFactory.getLog(MerchantOrderServiceImpl.class);
	
	@Resource
	private MerchantOrderDao merchantOrderDao;
	
	@Resource
	private OrderDayDao orderDayDao;
	@Resource
	private ParkDayDao parkDayDao;

	/**
	 * 根据查询条件，查询并返回MerchantOrder的列表
	 * @param q 查询条件
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<MerchantOrder> getMerchantOrderList(Query q) {
		List<MerchantOrder> list = null;
		try {
			//直接调用Dao方法进行查询
			list = merchantOrderDao.queryAll(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		//如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<MerchantOrder>(0) : list;
		return list; 
	}
	
	/**
	 * 根据查询条件，分页查询并返回MerchantOrder的分页结果
	 * @param q 查询条件
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public PageFinder<MerchantOrder> getMerchantOrderPagedList(Query q) {
		PageFinder<MerchantOrder> page = new PageFinder<MerchantOrder>();
		
		List<MerchantOrder> list = null;
		long rowCount = 0L;
		
		try {
			//调用dao查询满足条件的分页数据
			list = merchantOrderDao.pageList(q);
			//调用dao统计满足条件的记录总数
			rowCount = merchantOrderDao.count(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		
		//如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<MerchantOrder>(0) : list;
	
		//将分页数据和记录总数设置到分页结果对象中
		page.setData(list);
		page.setRowCount(rowCount);
		
		return page;
	}	
	
	/**
	 * 根据主键，查询并返回一个MerchantOrder对象
	 * @param id 主键id
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public MerchantOrder getMerchantOrder(String id) {
		MerchantOrder obj = null;
		if (id == null || id.length() == 0) { //传入的主键无效时直接返回null
			log.info(Constant.ERR_MSG_INVALID_ARG + " id = " + id);
			return obj;
		}
		try {
			//调用dao，根据主键查询
			obj = merchantOrderDao.get(id); 
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		
		return obj;
	}

	/**
	 * 根据主键数组，查询并返回一组MerchantOrder对象
	 * @param ids 主键数组，数组中的主键值应当无重复值，如有重复值时，则返回的列表长度可能小于传入的数组长度
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<MerchantOrder> getMerchantOrderByIds(String[] ids) {
		List<MerchantOrder> list = null;
		if (ids == null || ids.length == 0) {
			log.info(Constant.ERR_MSG_INVALID_ARG + " ids is null or empty array.");
		} else {
			try {
				//调用dao，根据主键数组查询
				list = merchantOrderDao.getByIds(ids);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
		}
		
		//如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<MerchantOrder>(0) : list;

		return list;
	}	
	
	/**
	 * 根据主键，删除特定的MerchantOrder记录
	 * @param id 主键id
	 * @param operator 操作人对象
	 * @return
	 */
	@Transactional
	public ResultInfo<MerchantOrder> delMerchantOrder(String id, Operator operator) {
		ResultInfo<MerchantOrder> resultInfo = new ResultInfo<MerchantOrder>();
		if (id == null || id.length() == 0) { //传入的主键无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG );
			log.info(Constant.ERR_MSG_INVALID_ARG + " id = " + id);
			return resultInfo;
		}
		try {
		    //调用Dao执行删除，并判断删除语句执行结果
			int count = merchantOrderDao.delete(id);
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
	 * 新增一条MerchantOrder记录，执行成功后传入对象及返回对象的主键属性值不为null
	 * @param merchantOrder 新增的MerchantOrder数据（如果无特殊需求，新增对象的主键值请保留为null）
	 * @param operator 操作人对象
	 * @return
	 */
	@Transactional
	public ResultInfo<MerchantOrder> addMerchantOrder(MerchantOrder merchantOrder, Operator operator) {
		ResultInfo<MerchantOrder> resultInfo = new ResultInfo<MerchantOrder>();
		
		if (merchantOrder == null) { //传入参数无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG );
			log.info(Constant.ERR_MSG_INVALID_ARG + " merchantOrder = " + merchantOrder);
		} else {
			try {
				//如果传入参数的主键为null，则调用生成主键的方法获取新的主键
				if (merchantOrder.getMerchantOrderId() == null) {
					merchantOrder.setMerchantOrderId(this.generatePK());
				}
				//如果传入的操作人不为null，则设置新增记录的操作人类型和操作人id
				if (operator != null) {
					merchantOrder.setOperatorType(operator.getOperatorType());
					merchantOrder.setOperatorId(operator.getOperatorId());
				}
				
				//设置创建时间和更新时间为当前时间
				Date now = new Date();
				merchantOrder.setCreateTime(now);
				merchantOrder.setUpdateTime(now);
				
				//填充默认值
				this.fillDefaultValues(merchantOrder);
				
				//调用Dao执行插入操作
				merchantOrderDao.add(merchantOrder);
				resultInfo.setCode(Constant.SECCUESS);
				resultInfo.setData(merchantOrder);
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
	 * 根据主键，更新一条MerchantOrder记录
	 * @param merchantOrder 更新的MerchantOrder数据，且其主键不能为空
	 * @param operator 操作人对象
	 * @return
	 */
	@Transactional
	public ResultInfo<MerchantOrder> updateMerchantOrder(MerchantOrder merchantOrder, Operator operator) {
		ResultInfo<MerchantOrder> resultInfo = new ResultInfo<MerchantOrder>();
		
		if (merchantOrder == null || merchantOrder.getMerchantOrderId() == null) { //传入参数无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG );
			log.info(Constant.ERR_MSG_INVALID_ARG + " merchantOrder = " + merchantOrder);
		} else {
			try {
				//如果传入的操作人不为null，则设置更新记录的操作人类型和操作人id
				if (operator != null) {
					merchantOrder.setOperatorType(operator.getOperatorType());
					merchantOrder.setOperatorId(operator.getOperatorId());
				}
				
				//设置更新时间为当前时间
				merchantOrder.setUpdateTime(new Date());
				
				//调用Dao执行更新操作，并判断更新语句执行结果
				int count = merchantOrderDao.update(merchantOrder);			
				if (count > 0) {
					resultInfo.setCode(Constant.SECCUESS);
				} else {
					resultInfo.setCode(Constant.FAIL);
				}
				resultInfo.setData(merchantOrder);
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
	 * 为MerchantOrder对象设置默认值
	 * @param obj
	 */
	public void fillDefaultValues(MerchantOrder obj) {
		if (obj != null) {
		}
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public PageFinder<Map<String, Object>> getOrderMapPagedList(Query q) {
		PageFinder<Map<String, Object>> page = new PageFinder<>();
		
		List<Map<String, Object>> list = null;
		long rowCount = 0L;
		
		try {
			//调用dao查询满足条件的分页数据
			list = merchantOrderDao.pageMapList(q);
			//调用dao统计满足条件的记录总数
			rowCount = merchantOrderDao.countMap(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		
		//如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<Map<String, Object>>(0) : list;
	
		//将分页数据和记录总数设置到分页结果对象中
		page.setData(list);
		page.setRowCount(rowCount);
		
		return page;
	}

	@Override
	public Map<String, Object> getChangeMerchantOrder(String id) {
		return merchantOrderDao.getChangeMerchantOrder(id);
	}

	@Override
	@Transactional
	public ResultInfo<MerchantOrder> giveMerchantOrder(MerchantOrder merchantOrder, Operator operator) {
		merchantOrder.setMerchantOrderStatus(OrderConstant.MERCHANT_ORDER_STATUS_GIVE);
		ResultInfo<MerchantOrder> result = updateMerchantOrder(merchantOrder, operator);
		if (result.getCode().equals(Constant.SECCUESS)) {
			OrderDay updateOrderDay = new OrderDay();
			updateOrderDay.setOrderNo(merchantOrder.getOrderNo());
			updateOrderDay.setCarPlateNo(merchantOrder.getCarPlateNo());
			updateOrderDay.setActualTakeTime(new Date());
			OrderDay orderDay = orderDayDao.get(merchantOrder.getOrderNo());
			ParkDay startParkDay = parkDayDao.get(orderDay.getStartParkNo());
			if(startParkDay!=null){
				updateOrderDay.setActualTakePakeName(startParkDay.getParkName());
			}
			updateOrderDay.setOrderStatus(OrderConstant.MEMBER_ORDER_STATUS_INUSE);
			orderDayDao.update(updateOrderDay);
			result.setMsg("ok");
		}
		return result;
	}

	@Override
	@Transactional
	public ResultInfo<MerchantOrder> checkMerchantOrder(MerchantOrder merchantOrder, Date actualTakeTime,Double overTimeAmount, Operator operator) {
		merchantOrder.setMerchantOrderStatus(OrderConstant.MERCHANT_ORDER_STATUS_CHECK);
		merchantOrder.setOvertimeAmount(overTimeAmount);
		ResultInfo<MerchantOrder> result = updateMerchantOrder(merchantOrder, operator);
		if (result.getCode().equals(Constant.SECCUESS)) {
			OrderDay updateOrderDay = new OrderDay();
			updateOrderDay.setOrderNo(merchantOrder.getOrderNo());
			updateOrderDay.setActualReturnTime(actualTakeTime);
			updateOrderDay.setOvertimeCharge(overTimeAmount);
			OrderDay orderDay = orderDayDao.get(merchantOrder.getOrderNo());
			if(orderDay!=null&&orderDay.getIsBackCarDoor()==0){
				ParkDay returnParkDay = parkDayDao.get(orderDay.getTerminalParkNo());
				if(returnParkDay!=null){
					updateOrderDay.setActualTerminalParkNo(returnParkDay.getParkId());
					updateOrderDay.setActualTerminalParkName(returnParkDay.getParkName());
				}
			}
			updateOrderDay.setOrderStatus(OrderConstant.MEMBER_ORDER_STATUS_END);
			orderDayDao.update(updateOrderDay);
			result.setMsg("ok");
		}
		return result;
	}

	@Override
	public MerchantOrder getOrderDayNo(String orderNo) {
		return merchantOrderDao.getOrderDayNo(orderNo);
	}
	/**
	 * 根据订单号查找拒单的商家订单
	 * @param orderNo
	 * @return
	 */
	public MerchantOrder getOrderDayByOrderNo(String orderNo){
		return merchantOrderDao.getOrderDayByOrderNo(orderNo);
	}
	@Override
	public List<Map<String, Object>> balanceOfAccount(String merchantId) {
		return orderDayDao.balanceOfAccount(merchantId);
	}

	@Override
	public String getMerhcantOrderNo(String merchantId) {
		return merchantOrderDao.getMerhcantOrderNo(merchantId);
	}

}
