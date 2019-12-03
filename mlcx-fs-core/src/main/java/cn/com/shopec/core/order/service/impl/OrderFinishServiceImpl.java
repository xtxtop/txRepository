package cn.com.shopec.core.order.service.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
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
import cn.com.shopec.common.utils.ECCalculateUtils;
import cn.com.shopec.common.utils.ECDateUtils;
import cn.com.shopec.common.utils.ECNumberUtils;
import cn.com.shopec.core.car.dao.CarAccidentDao;
import cn.com.shopec.core.car.dao.CarFaultDao;
import cn.com.shopec.core.car.model.Car;
import cn.com.shopec.core.car.model.CarStatus;
import cn.com.shopec.core.car.service.CarService;
import cn.com.shopec.core.car.service.CarStatusService;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.franchisee.model.Franchisee;
import cn.com.shopec.core.franchisee.model.FranchiseeProfit;
import cn.com.shopec.core.franchisee.service.FranchiseeProfitService;
import cn.com.shopec.core.franchisee.service.FranchiseeService;
import cn.com.shopec.core.marketing.model.PricingRule;
import cn.com.shopec.core.marketing.model.PricingRuleCustomizedDate;
import cn.com.shopec.core.marketing.service.PricingRuleCustomizedDateService;
import cn.com.shopec.core.marketing.service.PricingRuleService;
import cn.com.shopec.core.member.model.Member;
import cn.com.shopec.core.member.service.MemberService;
import cn.com.shopec.core.order.dao.OrderDao;
import cn.com.shopec.core.order.dao.OrderFinishDao;
import cn.com.shopec.core.order.model.Order;
import cn.com.shopec.core.order.model.OrderFinish;
import cn.com.shopec.core.order.model.OrderMileage;
import cn.com.shopec.core.order.service.OrderFinishService;
import cn.com.shopec.core.order.service.OrderMileageService;
import cn.com.shopec.core.order.service.OrderService;
import cn.com.shopec.core.resource.model.Park;
import cn.com.shopec.core.resource.model.ParkLocation;
import cn.com.shopec.core.resource.service.ParkService;
import cn.com.shopec.core.system.model.SysParam;
import cn.com.shopec.core.system.service.SysParamService;

/**
 * 订单结束表 服务实现类
 */
@Service
public class OrderFinishServiceImpl implements OrderFinishService {

	private static final Log log = LogFactory.getLog(OrderFinishServiceImpl.class);

	@Resource
	private OrderFinishDao orderFinishDao;
	@Resource
	private OrderDao orderDao;
	@Resource
	private CarFaultDao carFaultDao;
	@Resource
	private CarAccidentDao carAccidentDao;

	@Resource
	private OrderService orderService;

	@Resource
	private ParkService parkService;

	@Resource
	private CarStatusService carStatusService;
	@Resource
	private OrderMileageService orderMileageService;
	@Resource
	private PricingRuleCustomizedDateService pricingRuleCustomizedDateService;	
	@Resource
	private PricingRuleService pricingRuleService;
	@Resource
	private MemberService memberService;
	@Resource
	private SysParamService sysParamService;
	@Resource
	private CarService carService;
	@Resource
	private FranchiseeProfitService franchiseeProfitService;
	@Resource
	private FranchiseeService franchiseeService;
	/**
	 * 根据查询条件，查询并返回OrderFinish的列表
	 * 
	 * @param q
	 *            查询条件
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<OrderFinish> getOrderFinishList(Query q) {
		List<OrderFinish> list = null;
		try {
			// 直接调用Dao方法进行查询
			list = orderFinishDao.queryAll(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		// 如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<OrderFinish>(0) : list;
		return list;
	}

	/**
	 * 根据查询条件，分页查询并返回OrderFinish的分页结果
	 * 
	 * @param q
	 *            查询条件
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public PageFinder<OrderFinish> getOrderFinishPagedList(Query q) {
		PageFinder<OrderFinish> page = new PageFinder<OrderFinish>();

		List<OrderFinish> list = null;
		long rowCount = 0L;

		try {
			// 调用dao查询满足条件的分页数据
			list = orderFinishDao.pageList(q);
			// 调用dao统计满足条件的记录总数
			rowCount = orderFinishDao.count(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		// 如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<OrderFinish>(0) : list;

		// 将分页数据和记录总数设置到分页结果对象中
		page.setData(list);
		page.setRowCount(rowCount);

		return page;
	}

	/**
	 * 根据主键，查询并返回一个OrderFinish对象
	 * 
	 * @param id
	 *            主键id
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public OrderFinish getOrderFinish(String id) {
		OrderFinish obj = null;
		if (id == null || id.length() == 0) { // 传入的主键无效时直接返回null
			log.info(Constant.ERR_MSG_INVALID_ARG + " id = " + id);
			return obj;
		}
		try {
			// 调用dao，根据主键查询
			obj = orderFinishDao.get(id);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		return obj;
	}

	/**
	 * 根据主键数组，查询并返回一组OrderFinish对象
	 * 
	 * @param ids
	 *            主键数组，数组中的主键值应当无重复值，如有重复值时，则返回的列表长度可能小于传入的数组长度
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<OrderFinish> getOrderFinishByIds(String[] ids) {
		List<OrderFinish> list = null;
		if (ids == null || ids.length == 0) {
			log.info(Constant.ERR_MSG_INVALID_ARG + " ids is null or empty array.");
		} else {
			try {
				// 调用dao，根据主键数组查询
				list = orderFinishDao.getByIds(ids);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
		}

		// 如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<OrderFinish>(0) : list;

		return list;
	}

	/**
	 * 根据主键，删除特定的OrderFinish记录
	 * 
	 * @param id
	 *            主键id
	 * @param operator
	 *            操作人对象
	 * @return
	 */
	@Transactional
	public ResultInfo<OrderFinish> delOrderFinish(String id, Operator operator) {
		ResultInfo<OrderFinish> resultInfo = new ResultInfo<OrderFinish>();
		if (id == null || id.length() == 0) { // 传入的主键无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG);
			log.info(Constant.ERR_MSG_INVALID_ARG + " id = " + id);
			return resultInfo;
		}
		try {
			// 调用Dao执行删除，并判断删除语句执行结果
			int count = orderFinishDao.delete(id);
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
	 * 新增一条OrderFinish记录，执行成功后传入对象及返回对象的主键属性值不为null
	 * 
	 * @param orderFinish
	 *            新增的OrderFinish数据（如果无特殊需求，新增对象的主键值请保留为null）
	 * @param operator
	 *            操作人对象
	 * @return
	 */
	@Transactional
	public ResultInfo<OrderFinish> addOrderFinish(OrderFinish orderFinish, Operator operator) {
		ResultInfo<OrderFinish> resultInfo = new ResultInfo<OrderFinish>();

		if (orderFinish == null) { // 传入参数无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG);
			log.info(Constant.ERR_MSG_INVALID_ARG + " orderFinish = " + orderFinish);
		} else {
			try {
				// 如果传入参数的主键为null，则调用生成主键的方法获取新的主键
				if (orderFinish.getOrderNo() == null) {
					orderFinish.setOrderNo(this.generatePK());
				}
				// 如果传入的操作人不为null，则设置新增记录的操作人类型和操作人id
				if (operator != null) {
					orderFinish.setOperatorType(operator.getOperatorType());
					orderFinish.setOperatorId(operator.getOperatorId());
				}

				// 设置创建时间和更新时间为当前时间
				Date now = new Date();
				orderFinish.setCreateTime(now);
				orderFinish.setUpdateTime(now);

				// 填充默认值
				this.fillDefaultValues(orderFinish);

				// 调用Dao执行插入操作
				orderFinishDao.add(orderFinish);
				resultInfo.setCode(Constant.SECCUESS);
				resultInfo.setData(orderFinish);
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
	 * 根据主键，更新一条OrderFinish记录
	 * 
	 * @param orderFinish
	 *            更新的OrderFinish数据，且其主键不能为空
	 * @param operator
	 *            操作人对象
	 * @return
	 */
	@Transactional
	public ResultInfo<OrderFinish> updateOrderFinish(OrderFinish orderFinish, Operator operator) {
		ResultInfo<OrderFinish> resultInfo = new ResultInfo<OrderFinish>();

		if (orderFinish == null || orderFinish.getOrderNo() == null) { // 传入参数无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG);
			log.info(Constant.ERR_MSG_INVALID_ARG + " orderFinish = " + orderFinish);
		} else {
			try {
				// 如果传入的操作人不为null，则设置更新记录的操作人类型和操作人id
				if (operator != null) {
					orderFinish.setOperatorType(operator.getOperatorType());
					orderFinish.setOperatorId(operator.getOperatorId());
				}

				// 设置更新时间为当前时间
				orderFinish.setUpdateTime(new Date());

				// 调用Dao执行更新操作，并判断更新语句执行结果
				int count = orderFinishDao.update(orderFinish);
				if (count > 0) {
					resultInfo.setCode(Constant.SECCUESS);
				} else {
					resultInfo.setCode(Constant.FAIL);
				}
				resultInfo.setData(orderFinish);
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
		return null;
	}

	/**
	 * 为OrderFinish对象设置默认值
	 * 
	 * @param obj
	 */
	public void fillDefaultValues(OrderFinish obj) {
		if (obj != null) {
		}
	}

	/**
	 * 新增一条OrderFinish记录，执行成功后传入对象及返回对象的主键属性值不为null,并且是订单状态为结束
	 * 
	 * @param orderFinish
	 *            新增的OrderFinish数据（如果无特殊需求，新增对象的主键值请保留为null）
	 * @param operator
	 *            操作人对象
	 * @return
	 */
	@Transactional
	public ResultInfo<OrderFinish> orderOver(OrderFinish orderFinish, Operator operator) {
		ResultInfo<OrderFinish> resultInfo = new ResultInfo<OrderFinish>();

		if (orderFinish == null) { // 传入参数无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG);
			log.info(Constant.ERR_MSG_INVALID_ARG + " orderFinish = " + orderFinish);
		} else {
			try {
				// 如果传入参数的主键为null，则调用生成主键的方法获取新的主键
				if (orderFinish.getOrderNo() == null) {
					orderFinish.setOrderNo(this.generatePK());
				}
				// 如果传入的操作人不为null，则设置新增记录的操作人类型和操作人id
				if (operator != null) {
					orderFinish.setOperatorType(operator.getOperatorType());
					orderFinish.setOperatorId(operator.getOperatorId());
				}

				// 设置创建时间和更新时间为当前时间
				Date now = new Date();
				orderFinish.setCreateTime(now);
				orderFinish.setUpdateTime(now);

				// 填充默认值
				this.fillDefaultValues(orderFinish);

				// 查询订单
				Order order = orderDao.get(orderFinish.getOrderNo());
				if (order == null) {// 订单不存在
					resultInfo.setCode(Constant.FAIL);
					resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG);
					return resultInfo;
				} else {
					// 订单结束时，修改订单状态为结束
					// 订单时长（结束时间-计费时间）
					order.setFinishTime(new Date());
					if (order.getStartBillingTime() == null) {
						order.setStartBillingTime(order.getFinishTime());
					}
					// 根据传的车辆位置，查找周边场站距离最近的场站
					ParkLocation parkLocation = parkService.getParkByReturnCarNo(orderFinish.getLongitude().toString(),
							orderFinish.getLatitude().toString());
					String parkNo = "";
					if (parkLocation != null) {
						parkNo = parkLocation.getParkNo();
					}

					CarStatus carStatus = carStatusService.getCarStatus(order.getCarNo());
					carStatusService.beforeOrderFinish(order, carStatus);
					order.setOrderStatus(3);
					order = orderService.orderBalance(order, parkNo).getData();
					if (order == null) {
						resultInfo.setCode(Constant.FAIL);
						resultInfo.setMsg("当前订单的计费规则已过期，请联系技术人员进行处理！");
					} else {
						order.setActualTerminalParkNo(parkNo);
						if (orderFinish.getAdditionFees() != null) {
							// 订单应付金额+附加费
							order.setPayableAmount(ECNumberUtils
									.roundDoubleWithScale(order.getPayableAmount() + orderFinish.getAdditionFees(), 2));
							if (order.getPayableAmount() > 0) {
								order.setPayStatus(0);
							}
						}
						// 客服结束订单，后台结束订单时附加费用更新到订单表和订单结束表，同时订单总金额添加附加费用
						if (orderFinish.getFinishType() == 1) {
							order.setServiceFeeAmount(orderFinish.getAdditionFees());
							order.setFinishType(1);
							order.setOrderAmount(ECCalculateUtils
									.round(ECCalculateUtils.add(order.getOrderAmount(), orderFinish.getAdditionFees()), 2));
						}
						
						int count = orderDao.update(order);
						if (count > 0) {
							//订单结算完成之后给t_order_finish再添加数据
							orderFinishDao.add(orderFinish);
							//修改车辆状态表车辆的使用状态为空闲
							CarStatus c = new CarStatus();
							c.setCarNo(order.getCarNo());
							c.setLocationParkNo(parkNo);
							c.setUserageStatus(0);
							carStatusService.updateCarStatus(c, operator);
							resultInfo.setCode(Constant.SECCUESS);
							
							//异步将订单数据添加到加盟商分润单中
							synchronized (this) {
								try{
									this.addFranchiseeProfit(order);
								}catch(Exception e){
									log.error("fly:异步添加订单给加盟商结算单的时候出现了异常");
									e.printStackTrace();
								}
							}
						} else {
							resultInfo.setCode(Constant.FAIL);
						}
						//结束订单时 向 会员表修改最后结束订单时间
						if(order != null && order.getFinishTime() != null){
							Member mf =memberService.getMember(order.getMemberNo());
							if(mf != null){
								mf.setOrderFinishTime(order.getFinishTime());
								memberService.updateMember(mf, operator);
							}
							CarStatus cst= carStatusService.getCarStatus(order.getCarNo());
							if(cst != null){
								cst.setOrderFinishTime(order.getFinishTime());
								carStatusService.updateCarStatus(cst, operator);
							}
						}
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
	 * 计算订单最后一天时长=订单总时长-之前的时长（避免时长误差）
	 * @param order
	 * @param nowDate 
	 * @return
	 */
	private int cacaulateLastDayMinuteOfOrder(Order order, Date nowDate){
		int beforeTotalMinute = 0;
		int lastDayOfMinutes = 0;
		int orderTotalMinutes = ECDateUtils.differMinutes(order.getStartBillingTime(), order.getFinishTime()).intValue();
		OrderMileage omTemp = new OrderMileage();
		omTemp.setOrderNo(order.getOrderNo());
		List<OrderMileage> orderMileageList = orderMileageService.getOrderMileageList(new Query(omTemp));
		for(OrderMileage om:orderMileageList){
			if(om.getMinutes()!=null&&om.getOrderMileageDate().compareTo(nowDate)<0){
				beforeTotalMinute += om.getMinutes();
			}
		}
		lastDayOfMinutes = orderTotalMinutes - beforeTotalMinute;
		return lastDayOfMinutes;
	}
	private void beforeOrderFinish(Order order,CarStatus carStatus) throws ParseException{
		PricingRule rule = pricingRuleService.getPricingRule(order.getRuleNo());
		//判断订单结束时定时任务还未更新前一天的订单里程数据时，做更新处理
		OrderMileage orderMileage = orderMileageService.getNewestOrderMileage(order.getOrderNo());
		
		if(orderMileage!=null){
			Date nowDate = ECDateUtils.formatDateToDateNew(new Date());
			int diffDays = ECDateUtils.differDays(nowDate, orderMileage.getOrderMileageDate()).intValue();
			//当天数据未添加
			if(diffDays==1){
				Date updateTime = ECDateUtils.formatDateToDateNew(orderMileage.getUpdateTime());;
				diffDays = ECDateUtils.differDays(nowDate, updateTime).intValue();
				//上一天的更新时间与当前时间不属于同一天则更新上一天数据
				if(diffDays==1){
					//更新上一天的数据
					OrderMileage om = new OrderMileage();
					om.setOrderNo(order.getOrderNo());
					om.setOrderMileageDate(orderMileage.getOrderMileageDate());
					
					om.setOrderEndMinute(ECDateUtils.getCurrentDateEndTime(orderMileage.getOrderStartMinute()));
					om.setMinutes(ECDateUtils.differMinutes(om.getOrderEndMinute(), orderMileage.getOrderStartMinute()).intValue());
					
					om.setOrderFinishMileage(carStatus.getMileage());
					om.setMileage(ECCalculateUtils.sub(om.getOrderFinishMileage(),orderMileage.getOrderStartMileage()));
					//确定orderMileage.getOrderMileageDate()是周几
					int dayOfWeek = ECDateUtils.getDayOfWeek(orderMileage.getOrderMileageDate());
					//查询自定义日期
					PricingRuleCustomizedDate pricingRuleCustomizedDate = pricingRuleCustomizedDateService.getPricingRuleCustomizedDate(order.getRuleNo(), orderMileage.getOrderMileageDate());
					if(pricingRuleCustomizedDate!=null){//自定义日期时间金额和里程金额计算
						om.setMinutesAmount(ECCalculateUtils.mul(om.getMinutes(),pricingRuleCustomizedDate.getPriceOfMinuteCustomized()));
						om.setMileageAmount(ECCalculateUtils.mul(om.getMileage(),pricingRuleCustomizedDate.getPriceOfKmCustomized()));
					}else if(dayOfWeek==Calendar.SATURDAY||dayOfWeek==Calendar.SUNDAY){//周末金额和里程金额计算
						om.setMinutesAmount(ECCalculateUtils.mul(om.getMinutes(),rule.getPriceOfMinuteOrdinary()));
						om.setMileageAmount(ECCalculateUtils.mul(om.getMileage(),rule.getPriceOfKmOrdinary()));
					}else{//工作日金额和里程金额计算
						om.setMinutesAmount(ECCalculateUtils.mul(om.getMinutes(),rule.getPriceOfMinute()));
						om.setMileageAmount(ECCalculateUtils.mul(om.getMileage(),rule.getPriceOfKm()));
					}
					om.setOrderAmountOfDay(ECCalculateUtils.add(om.getMileageAmount(), om.getMinutesAmount()));
					orderMileageService.updateOrderMileage(om, null);
					
					//添加当天的数据
					OrderMileage om1 = new OrderMileage();
					om1.setOrderNo(order.getOrderNo());
					om1.setOrderMileageDate(nowDate);
					
					om1.setOrderStartMinute(ECDateUtils.getCurrentDateStartTime(nowDate));//当天时长开始时间
					om1.setOrderEndMinute(order.getFinishTime());//当天时长结束时间
					//计算订单最后一天时长=订单总时长-之前的时长（避免时长误差）
					int lastDayOfMinutes = cacaulateLastDayMinuteOfOrder(order,nowDate);
					om1.setMinutes(lastDayOfMinutes);
					
					om1.setOrderStartMileage(orderMileage.getOrderFinishMileage());//当天开始里程(为上一天结束里程)
					om1.setOrderFinishMileage(carStatus.getMileage());//当天结束里程
					om1.setMileage(ECCalculateUtils.sub(om1.getOrderFinishMileage(),om1.getOrderStartMileage()));
					//确定当天是周几
					dayOfWeek = ECDateUtils.getDayOfWeek(nowDate);
					//查询自定义日期
					pricingRuleCustomizedDate = pricingRuleCustomizedDateService.getPricingRuleCustomizedDate(order.getRuleNo(), nowDate);
					if(pricingRuleCustomizedDate!=null){//自定义日期时间金额和里程金额计算
						om1.setMinutesAmount(ECCalculateUtils.mul(om1.getMinutes(),pricingRuleCustomizedDate.getPriceOfMinuteCustomized()));
						om1.setMileageAmount(ECCalculateUtils.mul(om1.getMileage(),pricingRuleCustomizedDate.getPriceOfKmCustomized()));
					}else if(dayOfWeek==Calendar.SATURDAY||dayOfWeek==Calendar.SUNDAY){//周末金额和里程金额计算
						om1.setMinutesAmount(ECCalculateUtils.mul(om1.getMinutes(),rule.getPriceOfMinuteOrdinary()));
						om1.setMileageAmount(ECCalculateUtils.mul(om1.getMileage(),rule.getPriceOfKmOrdinary()));
					}else{//工作日金额和里程金额计算
						om1.setMinutesAmount(ECCalculateUtils.mul(om1.getMinutes(),rule.getPriceOfMinute()));
						om1.setMileageAmount(ECCalculateUtils.mul(om1.getMileage(),rule.getPriceOfKm()));
					}
					om1.setOrderAmountOfDay(ECCalculateUtils.add(om1.getMileageAmount(), om1.getMinutesAmount()));
					orderMileageService.addOrderMileage(om1, null);
				}else{
					//上一天数据已更新，只添加当天的数据
					OrderMileage om1 = new OrderMileage();
					om1.setOrderNo(order.getOrderNo());
					om1.setOrderMileageDate(nowDate);
					
					om1.setOrderStartMinute(ECDateUtils.getCurrentDateStartTime(nowDate));//当天时长开始时间
					om1.setOrderEndMinute(order.getFinishTime());//当天时长结束时间
					//计算订单最后一天时长=订单总时长-之前的时长（避免时长误差）
					int lastDayOfMinutes = cacaulateLastDayMinuteOfOrder(order, nowDate);
					om1.setMinutes(lastDayOfMinutes);
					
					om1.setOrderStartMileage(orderMileage.getOrderFinishMileage());//当天开始里程(为上一天结束里程)
					om1.setOrderFinishMileage(carStatus.getMileage());//当天结束里程
					om1.setMileage(ECCalculateUtils.sub(om1.getOrderFinishMileage(),om1.getOrderStartMileage()));
					//确定orderMileage.getOrderMileageDate()是周几
					int dayOfWeek = ECDateUtils.getDayOfWeek(orderMileage.getOrderMileageDate());
					//查询自定义日期
					PricingRuleCustomizedDate pricingRuleCustomizedDate = pricingRuleCustomizedDateService.getPricingRuleCustomizedDate(order.getRuleNo(), nowDate);
					if(pricingRuleCustomizedDate!=null){//自定义日期时间金额和里程金额计算
						om1.setMinutesAmount(ECCalculateUtils.mul(om1.getMinutes(),pricingRuleCustomizedDate.getPriceOfMinuteCustomized()));
						om1.setMileageAmount(ECCalculateUtils.mul(om1.getMileage(),pricingRuleCustomizedDate.getPriceOfKmCustomized()));
					}else if(dayOfWeek==Calendar.SATURDAY||dayOfWeek==Calendar.SUNDAY){//周末金额和里程金额计算
						om1.setMinutesAmount(ECCalculateUtils.mul(om1.getMinutes(),rule.getPriceOfMinuteOrdinary()));
						om1.setMileageAmount(ECCalculateUtils.mul(om1.getMileage(),rule.getPriceOfKmOrdinary()));
					}else{//工作日金额和里程金额计算
						om1.setMinutesAmount(ECCalculateUtils.mul(om1.getMinutes(),rule.getPriceOfMinute()));
						om1.setMileageAmount(ECCalculateUtils.mul(om1.getMileage(),rule.getPriceOfKm()));
					}
					om1.setOrderAmountOfDay(ECCalculateUtils.add(om1.getMileageAmount(), om1.getMinutesAmount()));
					orderMileageService.addOrderMileage(om1, null);
				}
			}else{
				//更新当天的数据
				OrderMileage om = new OrderMileage();
				om.setOrderNo(order.getOrderNo());
				om.setOrderMileageDate(orderMileage.getOrderMileageDate());
				
				om.setOrderEndMinute(order.getFinishTime());//当天时长结束时间
				//计算订单最后一天时长=订单总时长-之前的时长（避免时长误差）
				int lastDayOfMinutes = cacaulateLastDayMinuteOfOrder(order, orderMileage.getOrderMileageDate());
				om.setMinutes(lastDayOfMinutes);
				
				om.setOrderFinishMileage(carStatus.getMileage());//当天结束里程
				om.setMileage(ECCalculateUtils.sub(orderMileage.getOrderFinishMileage(),orderMileage.getOrderStartMileage()));
				//确定orderMileage.getOrderMileageDate()是周几
				int dayOfWeek = ECDateUtils.getDayOfWeek(orderMileage.getOrderMileageDate());
				//查询自定义日期
				PricingRuleCustomizedDate pricingRuleCustomizedDate = pricingRuleCustomizedDateService.getPricingRuleCustomizedDate(order.getRuleNo(), orderMileage.getOrderMileageDate());
				if(pricingRuleCustomizedDate!=null){//自定义日期时间金额和里程金额计算
					om.setMinutesAmount(ECCalculateUtils.mul(om.getMinutes(),pricingRuleCustomizedDate.getPriceOfMinuteCustomized()));
					om.setMileageAmount(ECCalculateUtils.mul(om.getMileage(),pricingRuleCustomizedDate.getPriceOfKmCustomized()));
				}else if(dayOfWeek==Calendar.SATURDAY||dayOfWeek==Calendar.SUNDAY){//周末金额和里程金额计算
					om.setMinutesAmount(ECCalculateUtils.mul(om.getMinutes(),rule.getPriceOfMinuteOrdinary()));
					om.setMileageAmount(ECCalculateUtils.mul(om.getMileage(),rule.getPriceOfKmOrdinary()));
				}else{//工作日金额和里程金额计算
					om.setMinutesAmount(ECCalculateUtils.mul(om.getMinutes(),rule.getPriceOfMinute()));
					om.setMileageAmount(ECCalculateUtils.mul(om.getMileage(),rule.getPriceOfKm()));
				}
				om.setOrderAmountOfDay(ECCalculateUtils.add(om.getMileageAmount(), om.getMinutesAmount()));
				orderMileageService.updateOrderMileage(om, null);
			}
		}
	}
	
	private void addFranchiseeProfit(Order order) throws Exception{
		/** 关联新增加盟商收益数据 **/
		Car car = carService.getCar(order.getCarNo());
		Park park = parkService.getPark(order.getStartParkNo());
		if(car != null){
			if(!"".equals(car.getFranchiseeNo()) && car.getFranchiseeNo() != null){
				//如果车辆关联加盟商，添加加盟收益数据
				FranchiseeProfit franchiseeProfit = new FranchiseeProfit();
				franchiseeProfit.setFranchiseeProfitNo(franchiseeProfitService.generatePK());
				franchiseeProfit.setFranchiseeNo(car.getFranchiseeNo());
				franchiseeProfit.setOrderNo(order.getOrderNo());
				franchiseeProfit.setOrderAmount(order.getOrderAmount());
				franchiseeProfit.setProfitType(Constant.PROFIT_CAR_TYPE);
				//查询加盟商信息
				Franchisee franchisee = franchiseeService.getFranchisee(car.getFranchiseeNo());
				if(franchisee != null){
					franchiseeProfit.setFranchiseeName(franchisee.getFranchiseeName());
					franchiseeProfit.setCarProportion(franchisee.getCarProportion());
					Double carProportion = (franchisee.getCarProportion())/100;
					franchiseeProfit.setCarProfit(ECCalculateUtils.round((ECCalculateUtils.mul(order.getOrderAmount(), carProportion)), 2));
					franchiseeProfit.setCarOrParkNo(order.getCarPlateNo());
				}
				franchiseeProfit.setCreateTime(order.getPaymentTime());
				if(franchiseeProfit.getCarProportion()==null){
					franchiseeProfit.setCarProportion(0d);
				}
				
				if(franchiseeProfit.getParkProportion()==null){
					franchiseeProfit.setParkProportion(0d);
				}
				
				
				if(franchiseeProfit.getCarProfit()==null){
					franchiseeProfit.setCarProfit(0d);
				}
				if(franchiseeProfit.getParkProfit() ==null ){
					franchiseeProfit.setParkProfit(0d);
				}
				franchiseeProfitService.addFranchiseeProfit(franchiseeProfit, null);
			}
		}
		if(park != null){
			if(!"".equals(park.getFranchiseeNo()) && park.getFranchiseeNo() != null){
				//如果关联场站关联加盟商，添加收益数据
				FranchiseeProfit franchiseeProfit = new FranchiseeProfit();
				franchiseeProfit.setFranchiseeProfitNo(franchiseeProfitService.generatePK());
				franchiseeProfit.setFranchiseeNo(park.getFranchiseeNo());
				franchiseeProfit.setOrderNo(order.getOrderNo());
				franchiseeProfit.setOrderAmount(order.getOrderAmount());
				franchiseeProfit.setProfitType(Constant.PROFIT_PARK_TYPE);
				//查询加盟商信息
				Franchisee franchisee = franchiseeService.getFranchisee(park.getFranchiseeNo());
				if(franchisee != null){
					franchiseeProfit.setFranchiseeName(franchisee.getFranchiseeName());
					franchiseeProfit.setParkProportion(franchisee.getParkProportion());
					Double carProportion = (franchisee.getParkProportion())/100;
					franchiseeProfit.setParkProfit(ECCalculateUtils.round((ECCalculateUtils.mul(order.getOrderAmount(), carProportion)),2));
					franchiseeProfit.setCarOrParkNo(order.getStartParkNo());
				}
				franchiseeProfit.setCreateTime(order.getPaymentTime());
				if(franchiseeProfit.getCarProfit()==null){
					franchiseeProfit.setCarProfit(0d);
				}
				if(franchiseeProfit.getParkProfit() ==null ){
					franchiseeProfit.setParkProfit(0d);
				}
				if(franchiseeProfit.getCarProportion()==null){
					franchiseeProfit.setCarProportion(0d);
				}
				
				if(franchiseeProfit.getParkProportion()==null){
					franchiseeProfit.setParkProportion(0d);
				}
				franchiseeProfitService.addFranchiseeProfit(franchiseeProfit, null);
			}
		}
	}

}
