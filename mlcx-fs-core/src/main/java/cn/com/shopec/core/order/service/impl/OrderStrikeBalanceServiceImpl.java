package cn.com.shopec.core.order.service.impl;

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
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.member.common.MemberConstant;
import cn.com.shopec.core.member.model.Member;
import cn.com.shopec.core.order.common.OrderConstant;
import cn.com.shopec.core.order.dao.OrderDao;
import cn.com.shopec.core.order.dao.OrderStrikeBalanceDao;
import cn.com.shopec.core.order.model.Order;
import cn.com.shopec.core.order.model.OrderFinish;
import cn.com.shopec.core.order.model.OrderStrikeBalance;
import cn.com.shopec.core.order.service.OrderFinishService;
import cn.com.shopec.core.order.service.OrderService;
import cn.com.shopec.core.order.service.OrderStrikeBalanceService;

/**
 * 订单冲账表 服务实现类
 */
@Service
public class OrderStrikeBalanceServiceImpl implements OrderStrikeBalanceService {

	private static final Log log = LogFactory.getLog(OrderStrikeBalanceServiceImpl.class);
	
	@Resource
	private OrderStrikeBalanceDao orderStrikeBalanceDao;
	
	@Resource
	private OrderService orderService;
	
	@Resource
	private OrderFinishService orderFinishService;

	/**
	 * 根据查询条件，查询并返回OrderStrikeBalance的列表
	 * @param q 查询条件
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<OrderStrikeBalance> getOrderStrikeBalanceList(Query q) {
		List<OrderStrikeBalance> list = null;
		try {
			//直接调用Dao方法进行查询
			list = orderStrikeBalanceDao.queryAll(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		//如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<OrderStrikeBalance>(0) : list;
		return list; 
	}
	
	/**
	 * 根据查询条件，分页查询并返回OrderStrikeBalance的分页结果
	 * @param q 查询条件
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public PageFinder<OrderStrikeBalance> getOrderStrikeBalancePagedList(Query q) {
		PageFinder<OrderStrikeBalance> page = new PageFinder<OrderStrikeBalance>();
		
		List<OrderStrikeBalance> list = null;
		long rowCount = 0L;
		
		try {
			//调用dao查询满足条件的分页数据
			list = orderStrikeBalanceDao.pageList(q);
			for(OrderStrikeBalance osb:list){
//				OrderFinish orderFinish=new OrderFinish();
//		    	orderFinish.setOrderNo(osb.getOrderNo());
//		    	//orderFinish.setFinishType(0);//正常结束
//		    	List<OrderFinish> finishList=orderFinishService.getOrderFinishList(new Query(orderFinish));
//		    	Double serviceFee=0d;
//		    	if(finishList!=null&&finishList.size()>0){
//		    		for(OrderFinish oFinish:finishList){
//		    			if(oFinish.getAdditionFees()!=null&&oFinish.getAdditionFees()>0){
//		    				serviceFee=serviceFee+oFinish.getAdditionFees();
//		    			}
//		    		}
//		    	}
		    	//获取 订单里面的 附加费用
		    	Order order=new Order();
		    	order.setOrderNo(osb.getOrderNo());
		    	Query query = new Query(order);
		    	Double serviceFeeAmount=0d;
		    	List<Order> orderList=orderService.getOrderList(query);
		    	
		    	if(orderList!=null&&orderList.size()>0){
		    		for(Order orders:orderList){
		    			if(orders.getServiceFeeAmount()!=null&&orders.getServiceFeeAmount()>0){
		    				serviceFeeAmount=serviceFeeAmount+orders.getServiceFeeAmount();
		    			}
		    		}
		    	}
		    	osb.setServiceFee(serviceFeeAmount);
			}
			//调用dao统计满足条件的记录总数
			rowCount = orderStrikeBalanceDao.count(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		
		//如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<OrderStrikeBalance>(0) : list;
	
		//将分页数据和记录总数设置到分页结果对象中
		page.setData(list);
		page.setRowCount(rowCount);
		
		return page;
	}	
	
	/**
	 * 根据主键，查询并返回一个OrderStrikeBalance对象
	 * @param id 主键id
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public OrderStrikeBalance getOrderStrikeBalance(String id) {
		OrderStrikeBalance obj = null;
		if (id == null || id.length() == 0) { //传入的主键无效时直接返回null
			log.info(Constant.ERR_MSG_INVALID_ARG + " id = " + id);
			return obj;
		}
		try {
			//调用dao，根据主键查询
			obj = orderStrikeBalanceDao.get(id); 
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		
		return obj;
	}

	/**
	 * 根据主键数组，查询并返回一组OrderStrikeBalance对象
	 * @param ids 主键数组，数组中的主键值应当无重复值，如有重复值时，则返回的列表长度可能小于传入的数组长度
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<OrderStrikeBalance> getOrderStrikeBalanceByIds(String[] ids) {
		List<OrderStrikeBalance> list = null;
		if (ids == null || ids.length == 0) {
			log.info(Constant.ERR_MSG_INVALID_ARG + " ids is null or empty array.");
		} else {
			try {
				//调用dao，根据主键数组查询
				list = orderStrikeBalanceDao.getByIds(ids);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
		}
		
		//如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<OrderStrikeBalance>(0) : list;

		return list;
	}	
	
	/**
	 * 根据主键，删除特定的OrderStrikeBalance记录
	 * @param id 主键id
	 * @param operator 操作人对象
	 * @return
	 */
	@Transactional
	public ResultInfo<OrderStrikeBalance> delOrderStrikeBalance(String id, Operator operator) {
		ResultInfo<OrderStrikeBalance> resultInfo = new ResultInfo<OrderStrikeBalance>();
		if (id == null || id.length() == 0) { //传入的主键无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG );
			log.info(Constant.ERR_MSG_INVALID_ARG + " id = " + id);
			return resultInfo;
		}
		try {
		    //调用Dao执行删除，并判断删除语句执行结果
			int count = orderStrikeBalanceDao.delete(id);
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
	 * 新增一条OrderStrikeBalance记录，执行成功后传入对象及返回对象的主键属性值不为null
	 * @param orderStrikeBalance 新增的OrderStrikeBalance数据（如果无特殊需求，新增对象的主键值请保留为null）
	 * @param operator 操作人对象
	 * @return
	 */
	@Transactional
	public ResultInfo<OrderStrikeBalance> addOrderStrikeBalance(OrderStrikeBalance orderStrikeBalance, Operator operator) {
		ResultInfo<OrderStrikeBalance> resultInfo = new ResultInfo<OrderStrikeBalance>();
		
		if (orderStrikeBalance == null) { //传入参数无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG );
			log.info(Constant.ERR_MSG_INVALID_ARG + " orderStrikeBalance = " + orderStrikeBalance);
		} else {
			try {
				//如果传入参数的主键为null，则调用生成主键的方法获取新的主键
				if (orderStrikeBalance.getStrikeBalanceNo() == null) {
					orderStrikeBalance.setStrikeBalanceNo(this.generatePK());
				}
				//如果传入的操作人不为null，则设置新增记录的操作人类型和操作人id
				if (operator != null) {
					orderStrikeBalance.setOperatorType(operator.getOperatorType());
					orderStrikeBalance.setOperatorId(operator.getOperatorId());
				}
				
				//设置创建时间和更新时间为当前时间
				Date now = new Date();
				orderStrikeBalance.setCreateTime(now);
				orderStrikeBalance.setUpdateTime(now);
				
				//填充默认值
				this.fillDefaultValues(orderStrikeBalance);
				
				//调用Dao执行插入操作
				orderStrikeBalanceDao.add(orderStrikeBalance);
				resultInfo.setCode(Constant.SECCUESS);
				resultInfo.setData(orderStrikeBalance);
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
	 * 根据主键，更新一条OrderStrikeBalance记录
	 * @param orderStrikeBalance 更新的OrderStrikeBalance数据，且其主键不能为空
	 * @param operator 操作人对象
	 * @return
	 */
	@Transactional
	public ResultInfo<OrderStrikeBalance> updateOrderStrikeBalance(OrderStrikeBalance orderStrikeBalance, Operator operator) {
		ResultInfo<OrderStrikeBalance> resultInfo = new ResultInfo<OrderStrikeBalance>();
		
		if (orderStrikeBalance == null || orderStrikeBalance.getStrikeBalanceNo() == null) { //传入参数无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG );
			log.info(Constant.ERR_MSG_INVALID_ARG + " orderStrikeBalance = " + orderStrikeBalance);
		} else {
			try {
				//如果传入的操作人不为null，则设置更新记录的操作人类型和操作人id
				if (operator != null) {
					orderStrikeBalance.setOperatorType(operator.getOperatorType());
					orderStrikeBalance.setOperatorId(operator.getOperatorId());
				}
				
				//设置更新时间为当前时间
				orderStrikeBalance.setUpdateTime(new Date());
				
				//调用Dao执行更新操作，并判断更新语句执行结果
				int count = orderStrikeBalanceDao.update(orderStrikeBalance);			
				if (count > 0) {
					resultInfo.setCode(Constant.SECCUESS);
				} else {
					resultInfo.setCode(Constant.FAIL);
				}
				resultInfo.setData(orderStrikeBalance);
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
	 * 为OrderStrikeBalance对象设置默认值
	 * @param obj
	 */
	public void fillDefaultValues(OrderStrikeBalance obj) {
		if (obj != null) {
		}
	}

	@Override
	@Transactional
	public ResultInfo<OrderStrikeBalance> addOrderStrikeBalanceAndUpdateOrder(OrderStrikeBalance orderStrikeBalance,
			Operator operator) {
//		Order order=orderService.getOrder(orderStrikeBalance.getOrderNo());
//		if(order.getStrikeBalanceAmount()==null){
//			order.setStrikeBalanceAmount(0d);
//		}
//		order.setStrikeBalanceAmount(order.getStrikeBalanceAmount()+orderStrikeBalance.getStrikeBalanceAmount());
//		orderService.updateOrder(order, operator);
		return addOrderStrikeBalance(orderStrikeBalance, operator);
	}

	@Override
	public Integer getTodoIndexValue() {
		return orderStrikeBalanceDao.countOrderStrikeBalanceTodoIndexValue();
	}

}
