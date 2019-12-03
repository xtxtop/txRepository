package cn.com.shopec.core.order.service.impl;

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
import cn.com.shopec.core.order.dao.OrderMonthCarDao;
import cn.com.shopec.core.order.dao.OrderMonthDao;
import cn.com.shopec.core.order.model.OrderMonth;
import cn.com.shopec.core.order.model.OrderMonthCar;
import cn.com.shopec.core.order.service.OrderMonthCarService;
import cn.com.shopec.core.order.service.OrderMonthService;
import cn.com.shopec.core.system.common.PrimarykeyConstant;
import cn.com.shopec.core.system.model.DataDictItem;
import cn.com.shopec.core.system.service.DataDictItemService;
import cn.com.shopec.core.system.service.PrimarykeyService;

/**
 * 月租订单表,一个月租订单内可以有多个车 服务实现类
 */
@Service
public class OrderMonthServiceImpl implements OrderMonthService {

	private static final Log log = LogFactory.getLog(OrderMonthServiceImpl.class);

	@Resource
	private OrderMonthDao orderMonthDao;
	@Resource
	private PrimarykeyService primarykeyService;
	@Resource
	private OrderMonthCarService orderMonthCarService;
	@Resource
	private OrderMonthCarDao orderMonthCarDao;

	@Resource
	private DataDictItemService dataDictItemService;

	/**
	 * 根据查询条件，查询并返回OrderMonth的列表
	 * 
	 * @param q
	 *            查询条件
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<OrderMonth> getOrderMonthList(Query q) {
		List<OrderMonth> list = null;
		try {
			// 直接调用Dao方法进行查询
			list = orderMonthDao.queryAll(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		// 如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<OrderMonth>(0) : list;
		return list;
	}

	/**
	 * 根据查询条件，分页查询并返回OrderMonth的分页结果
	 * 
	 * @param q
	 *            查询条件
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public PageFinder<OrderMonth> getOrderMonthPagedList(Query q) {
		PageFinder<OrderMonth> page = new PageFinder<OrderMonth>();

		List<OrderMonth> list = null;
		long rowCount = 0L;

		try {
			// 调用dao查询满足条件的分页数据
			list = orderMonthDao.pageList(q);
			// 调用dao统计满足条件的记录总数
			rowCount = orderMonthDao.count(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		// 如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<OrderMonth>(0) : list;

		// 将分页数据和记录总数设置到分页结果对象中
		page.setData(list);
		page.setRowCount(rowCount);

		return page;
	}

	/**
	 * 根据主键，查询并返回一个OrderMonth对象
	 * 
	 * @param id
	 *            主键id
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public OrderMonth getOrderMonth(String id) {
		OrderMonth obj = null;
		if (id == null || id.length() == 0) { // 传入的主键无效时直接返回null
			log.info(Constant.ERR_MSG_INVALID_ARG + " id = " + id);
			return obj;
		}
		try {
			// 调用dao，根据主键查询
			obj = orderMonthDao.get(id);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		return obj;
	}

	/**
	 * 根据主键数组，查询并返回一组OrderMonth对象
	 * 
	 * @param ids
	 *            主键数组，数组中的主键值应当无重复值，如有重复值时，则返回的列表长度可能小于传入的数组长度
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<OrderMonth> getOrderMonthByIds(String[] ids) {
		List<OrderMonth> list = null;
		if (ids == null || ids.length == 0) {
			log.info(Constant.ERR_MSG_INVALID_ARG + " ids is null or empty array.");
		} else {
			try {
				// 调用dao，根据主键数组查询
				list = orderMonthDao.getByIds(ids);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
		}

		// 如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<OrderMonth>(0) : list;

		return list;
	}

	/**
	 * 根据主键，删除特定的OrderMonth记录
	 * 
	 * @param id
	 *            主键id
	 * @param operator
	 *            操作人对象
	 * @return
	 */
	@Transactional
	public ResultInfo<OrderMonth> delOrderMonth(String id, Operator operator) {
		ResultInfo<OrderMonth> resultInfo = new ResultInfo<OrderMonth>();
		if (id == null || id.length() == 0) { // 传入的主键无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG);
			log.info(Constant.ERR_MSG_INVALID_ARG + " id = " + id);
			return resultInfo;
		}
		try {
			// 调用Dao执行删除，并判断删除语句执行结果
			int count = orderMonthDao.delete(id);
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
	 * 新增一条OrderMonth记录，执行成功后传入对象及返回对象的主键属性值不为null
	 * 
	 * @param orderMonth
	 *            新增的OrderMonth数据（如果无特殊需求，新增对象的主键值请保留为null）
	 * @param operator
	 *            操作人对象
	 * @return
	 */
	@Transactional
	public ResultInfo<OrderMonth> addOrderMonth(OrderMonth orderMonth, Operator operator) {
		ResultInfo<OrderMonth> resultInfo = new ResultInfo<OrderMonth>();

		if (orderMonth == null) { // 传入参数无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG);
			log.info(Constant.ERR_MSG_INVALID_ARG + " orderMonth = " + orderMonth);
		} else {
			try {
				// 如果传入参数的主键为null，则调用生成主键的方法获取新的主键
				if (orderMonth.getOrderNo() == null) {
					orderMonth.setOrderNo(this.generatePK());
				}
				// 如果传入的操作人不为null，则设置新增记录的操作人类型和操作人id
				if (operator != null) {
					orderMonth.setOperatorType(operator.getOperatorType());
					orderMonth.setOperatorId(operator.getOperatorId());
				}

				// 设置创建时间和更新时间为当前时间
				Date now = new Date();
				orderMonth.setCreateTime(now);
				orderMonth.setUpdateTime(now);

				// 填充默认值
				this.fillDefaultValues(orderMonth);
				// 城市
				DataDictItem dictItem = dataDictItemService.getDataDictItem(orderMonth.getCityId());
				orderMonth.setCityName(dictItem.getItemValue());

				// 调用Dao执行插入操作
				orderMonthDao.add(orderMonth);
				resultInfo.setCode(Constant.SECCUESS);
				resultInfo.setData(orderMonth);
				// 添加订单车辆
				String[] cars = orderMonth.getCars();
				if (cars != null && cars.length > 0) {
					for (int i = 0; i < cars.length; i++) {
						OrderMonthCar c = new OrderMonthCar();
						c.setCarNo(cars[i]);
						c.setOrderNo(orderMonth.getOrderNo());
						orderMonthCarService.addOrderMonthCar(c, operator);
					}

				}
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
	 * 根据主键，更新一条OrderMonth记录
	 * 
	 * @param orderMonth
	 *            更新的OrderMonth数据，且其主键不能为空
	 * @param operator
	 *            操作人对象
	 * @return
	 */
	@Transactional
	public ResultInfo<OrderMonth> updateOrderMonth(OrderMonth orderMonth, Operator operator) {
		ResultInfo<OrderMonth> resultInfo = new ResultInfo<OrderMonth>();

		if (orderMonth == null || orderMonth.getOrderNo() == null) { // 传入参数无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG);
			log.info(Constant.ERR_MSG_INVALID_ARG + " orderMonth = " + orderMonth);
		} else {
			try {
				// 如果传入的操作人不为null，则设置更新记录的操作人类型和操作人id
				if (operator != null) {
					orderMonth.setOperatorType(operator.getOperatorType());
					orderMonth.setOperatorId(operator.getOperatorId());
				}

				// 设置更新时间为当前时间
				orderMonth.setUpdateTime(new Date());

				// 调用Dao执行更新操作，并判断更新语句执行结果
				int count = orderMonthDao.update(orderMonth);
				if (count > 0) {
					resultInfo.setCode(Constant.SECCUESS);
				} else {
					resultInfo.setCode(Constant.FAIL);
				}

				// 先删除订单车辆
				orderMonthCarDao.deleteNs(orderMonth.getOrderNo());

				// 添加订单车辆
				String[] cars = orderMonth.getCars();
				if (cars != null && cars.length > 0) {
					for (int i = 0; i < cars.length; i++) {
						OrderMonthCar c = new OrderMonthCar();
						c.setCarNo(cars[i]);
						c.setOrderNo(orderMonth.getOrderNo());
						orderMonthCarService.addOrderMonthCar(c, operator);
					}

				}

				resultInfo.setData(orderMonth);
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
	 * 
	 * @return
	 */
	public String generatePK() {
		return primarykeyService.getValueByBizType(PrimarykeyConstant.orderMonthType) + "";
	}

	/**
	 * 为OrderMonth对象设置默认值
	 * 
	 * @param obj
	 */
	public void fillDefaultValues(OrderMonth obj) {
		if (obj != null) {
			if (obj.getOrderStatus() == null) {
				obj.setOrderStatus(0);
			}
			if (obj.getPayStatus() == null) {
				obj.setPayStatus(0);
			}
			if (obj.getIsCancel() == null) {
				obj.setIsCancel(0);
			}
			if (obj.getIsNeedInvoice() == null) {
				obj.setIsNeedInvoice(0);
			}
			if (obj.getIsInvoiceIssued() == null) {
				obj.setIsInvoiceIssued(0);
			}
		}
	}

	@Override
	public PageFinder<OrderMonth> pageListNs(Query q) {
		PageFinder<OrderMonth> page = new PageFinder<OrderMonth>();

		List<OrderMonth> list = null;
		long rowCount = 0L;

		try {
			// 调用dao查询满足条件的分页数据
			list = orderMonthDao.pageListNs(q);
			// 调用dao统计满足条件的记录总数
			rowCount = orderMonthDao.countNs(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		// 如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<OrderMonth>(0) : list;

		// 将分页数据和记录总数设置到分页结果对象中
		page.setData(list);
		page.setRowCount(rowCount);

		return page;
	}

}
