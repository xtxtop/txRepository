package cn.com.shopec.core.order.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import cn.com.shopec.common.Operator;
import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.common.constants.Constant;
import cn.com.shopec.common.utils.ECCalculateUtils;
import cn.com.shopec.common.utils.ECDateUtils;
import cn.com.shopec.common.utils.ECGeoCoordinateTransformUtil;
import cn.com.shopec.common.utils.ECNumberUtils;
import cn.com.shopec.common.utils.JsonUtils;
import cn.com.shopec.core.car.common.CarConstant;
import cn.com.shopec.core.car.dao.CarDao;
import cn.com.shopec.core.car.dao.CarStatusDao;
import cn.com.shopec.core.car.model.Car;
import cn.com.shopec.core.car.model.CarStatus;
import cn.com.shopec.core.car.service.CarService;
import cn.com.shopec.core.car.service.CarStatusService;
import cn.com.shopec.core.car.service.CarTripService;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.device.model.Device;
import cn.com.shopec.core.device.service.DeviceService;
import cn.com.shopec.core.finace.dao.DepositOrderDao;
import cn.com.shopec.core.finace.dao.FinaceTestDao;
import cn.com.shopec.core.finace.dao.InvoiceDao;
import cn.com.shopec.core.finace.dao.PaymentRecordDao;
import cn.com.shopec.core.finace.model.CheckAccounts;
import cn.com.shopec.core.finace.model.CheckAccountsView;
import cn.com.shopec.core.finace.model.Invoice;
import cn.com.shopec.core.finace.service.InvoiceService;
import cn.com.shopec.core.marketing.model.Accounts;
import cn.com.shopec.core.marketing.model.CarRedPacket;
import cn.com.shopec.core.marketing.model.CarRedPacketVo;
import cn.com.shopec.core.marketing.model.Coupon;
import cn.com.shopec.core.marketing.model.CouponPlan;
import cn.com.shopec.core.marketing.model.GoldBeansConsumerDetails;
import cn.com.shopec.core.marketing.model.GoldBeansSetting;
import cn.com.shopec.core.marketing.model.PeakHours;
import cn.com.shopec.core.marketing.model.PeakHoursCost;
import cn.com.shopec.core.marketing.model.PricingDeductionRecord;
import cn.com.shopec.core.marketing.model.PricingPackage;
import cn.com.shopec.core.marketing.model.PricingRule;
import cn.com.shopec.core.marketing.model.PricingRuleCustomizedDate;
import cn.com.shopec.core.marketing.service.AccountsService;
import cn.com.shopec.core.marketing.service.CarRedPacketService;
import cn.com.shopec.core.marketing.service.CouponPlanService;
import cn.com.shopec.core.marketing.service.CouponService;
import cn.com.shopec.core.marketing.service.GoldBeansConsumerDetailsService;
import cn.com.shopec.core.marketing.service.GoldBeansSettingService;
import cn.com.shopec.core.marketing.service.PeakHoursService;
import cn.com.shopec.core.marketing.service.PricingDeductionRecordService;
import cn.com.shopec.core.marketing.service.PricingPackageService;
import cn.com.shopec.core.marketing.service.PricingRuleCustomizedDateService;
import cn.com.shopec.core.marketing.service.PricingRuleService;
import cn.com.shopec.core.member.common.MemberConstant;
import cn.com.shopec.core.member.dao.CompanyDao;
import cn.com.shopec.core.member.dao.MemberDao;
import cn.com.shopec.core.member.model.Member;
import cn.com.shopec.core.member.model.MemberGlodBeans;
import cn.com.shopec.core.member.model.MemberLevel;
import cn.com.shopec.core.member.service.MemberGlodBeansService;
import cn.com.shopec.core.member.service.MemberLevelService;
import cn.com.shopec.core.member.service.MemberService;
import cn.com.shopec.core.monitor.service.WarningService;
import cn.com.shopec.core.order.common.OrderConstant;
import cn.com.shopec.core.order.dao.OrderDao;
import cn.com.shopec.core.order.model.Order;
import cn.com.shopec.core.order.model.OrderCountVo;
import cn.com.shopec.core.order.model.OrderFinish;
import cn.com.shopec.core.order.model.OrderMileage;
import cn.com.shopec.core.order.model.PricingPackOrder;
import cn.com.shopec.core.order.service.OrderFinishService;
import cn.com.shopec.core.order.service.OrderMileageService;
import cn.com.shopec.core.order.service.OrderService;
import cn.com.shopec.core.order.service.PricingPackOrderService;
import cn.com.shopec.core.order.vo.OrderAmountDayVo;
import cn.com.shopec.core.order.vo.OrderCarUsedVo;
import cn.com.shopec.core.order.vo.OrderReportFormVo;
import cn.com.shopec.core.order.vo.OrderReportStatisticalVo;
import cn.com.shopec.core.order.vo.OrderStatisticalVo;
import cn.com.shopec.core.order.vo.OrderStatusVo;
import cn.com.shopec.core.order.vo.ParkOrderVO;
import cn.com.shopec.core.resource.dao.ParkDao;
import cn.com.shopec.core.resource.model.Park;
import cn.com.shopec.core.resource.model.ParkStatus;
import cn.com.shopec.core.resource.service.ParkService;
import cn.com.shopec.core.resource.service.ParkStatusService;
import cn.com.shopec.core.statement.model.CarRpts;
import cn.com.shopec.core.statement.model.Details;
import cn.com.shopec.core.statement.model.GroupRpts;
import cn.com.shopec.core.statement.model.Money;
import cn.com.shopec.core.statement.model.ParkRpts;
import cn.com.shopec.core.statement.model.Summary;
import cn.com.shopec.core.system.common.PrimarykeyConstant;
import cn.com.shopec.core.system.dao.DataDictItemDao;
import cn.com.shopec.core.system.model.SysParam;
import cn.com.shopec.core.system.service.PrimarykeyService;
import cn.com.shopec.core.system.service.SysParamService;

/**
 * 订单表 服务实现类
 */
@Service
public class OrderServiceImpl implements OrderService {

	private static final Log log = LogFactory.getLog(OrderServiceImpl.class);

	@Value("${image_path}")
	private String imgPath;
	@Value("${alipay.agent.fee}")
	private Double zfbAgentFee;
	@Resource
	private OrderDao orderDao;

	@Resource
	private CarDao carDao;

	@Resource
	private CarStatusDao carStatusDao;
	@Resource
	private MemberDao memberDao;

	@Resource
	private ParkDao parkDao;

	@Resource
	private SysParamService sysParamService;

	@Resource
	private PricingRuleService pricingRuleService;

	@Resource
	private PricingPackOrderService pricingPackOrderService;

	@Resource
	private CarService carService;

	@Resource
	private MemberService memberService;

	@Resource
	private CarStatusService carStatusService;

	@Resource
	private CarTripService carTripService;

	@Resource
	private ParkService parkService;

	@Resource
	private ParkStatusService parkStatusService;

	@Resource
	private PrimarykeyService primarykeyService;

	@Resource
	private InvoiceDao invoiceDao;

	@Resource
	private DeviceService deviceService;

	@Resource
	private InvoiceService invoiceService;

	@Resource
	private CompanyDao companyDao;

	@Resource
	private DepositOrderDao depositOrderDao;

	@Resource
	private PricingPackageService pricingPackageService;

	@Resource
	private PricingDeductionRecordService pricingDeductionRecordService;

	@Resource
	private PaymentRecordDao paymentRecordDao;

	@Resource
	private OrderFinishService orderFinishService;
	@Resource
	private FinaceTestDao finaceTestDao;
	@Resource
	private OrderMileageService orderMileageService;
	@Resource
	private PricingRuleCustomizedDateService pricingRuleCustomizedDateService;
	@Resource
	private AccountsService accountsService;
	@Resource
	private DataDictItemDao dataDictItemDao;
	@Resource
	private WarningService warningService;
	@Resource
	private CouponService couponService;
	@Resource
	private CouponPlanService couponPlanService;
	@Resource
	private MemberLevelService memberLevelService;
	@Resource
	private MemberGlodBeansService memberGlodBeansService;
	@Resource
	private GoldBeansSettingService goldBeansSettingService;
	@Resource
	private GoldBeansConsumerDetailsService goldBeansConsumerDetailsService;
	@Resource
	private PeakHoursService peakHoursService;
	@Resource
	private CarRedPacketService carRedPacketService;

	/**
	 * 根据查询条件，查询并返回Order的列表
	 * 
	 * @param q
	 *            查询条件
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<Order> getOrderList(Query q) {
		List<Order> list = null;
		try {
			// 直接调用Dao方法进行查询
			list = orderDao.queryAll(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		// 如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<Order>(0) : list;
		return list;
	}

	/**
	 * 根据查询条件，查询并返回Order的列表
	 * 
	 * @param q
	 *            查询条件
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<Order> queryAllInvoice(Query q) {
		List<Order> list = null;
		try {
			// 直接调用Dao方法进行查询
			list = orderDao.queryAllInvoice(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		// 如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<Order>(0) : list;
		return list;
	}

	/**
	 * 根据查询条件，分页查询并返回Order的分页结果
	 * 
	 * @param q
	 *            查询条件
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public PageFinder<Order> getOrderPagedList(Query q) {
		PageFinder<Order> page = new PageFinder<Order>();

		List<Order> list = null;
		long rowCount = 0L;

		try {
			// 调用dao查询满足条件的分页数据
			list = orderDao.pageList(q);
			// 调用dao统计满足条件的记录总数
			rowCount = orderDao.count(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		// 如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<Order>(0) : list;

		// 将分页数据和记录总数设置到分页结果对象中
		page.setData(list);
		page.setRowCount(rowCount);

		return page;
	}

	/**
	 * 根据查询条件，分页查询并返回Order的分页结果
	 * 
	 * @param q
	 *            查询条件
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public PageFinder<Order> pageListInvoice(Query q) {
		PageFinder<Order> page = new PageFinder<Order>();

		List<Order> list = null;
		long rowCount = 0L;

		try {
			// 调用dao查询满足条件的分页数据
			list = orderDao.pageListInvoice(q);
			// 调用dao统计满足条件的记录总数
			rowCount = orderDao.countInvoice(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		// 如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<Order>(0) : list;

		// 将分页数据和记录总数设置到分页结果对象中
		page.setData(list);
		page.setRowCount(rowCount);

		return page;
	}

	/**
	 * 根据主键，查询并返回一个Order对象
	 * 
	 * @param id
	 *            主键id
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public Order getOrder(String id) {
		Order obj = null;
		if (id == null || id.length() == 0) { // 传入的主键无效时直接返回null
			log.info(Constant.ERR_MSG_INVALID_ARG + " id = " + id);
			return obj;
		}
		try {
			// 调用dao，根据主键查询
			obj = orderDao.get(id);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		return obj;
	}

	/**
	 * 根据主键数组，查询并返回一组Order对象
	 * 
	 * @param ids
	 *            主键数组，数组中的主键值应当无重复值，如有重复值时，则返回的列表长度可能小于传入的数组长度
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<Order> getOrderByIds(String[] ids) {
		List<Order> list = null;
		if (ids == null || ids.length == 0) {
			log.info(Constant.ERR_MSG_INVALID_ARG + " ids is null or empty array.");
		} else {
			try {
				// 调用dao，根据主键数组查询
				list = orderDao.getByIds(ids);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
		}

		// 如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<Order>(0) : list;

		return list;
	}

	/**
	 * 根据主键，删除特定的Order记录
	 * 
	 * @param id
	 *            主键id
	 * @param operator
	 *            操作人对象
	 * @return
	 */
	@Transactional
	public ResultInfo<Order> delOrder(String id, Operator operator) {
		ResultInfo<Order> resultInfo = new ResultInfo<Order>();
		if (id == null || id.length() == 0) { // 传入的主键无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG);
			log.info(Constant.ERR_MSG_INVALID_ARG + " id = " + id);
			return resultInfo;
		}
		try {
			// 调用Dao执行删除，并判断删除语句执行结果
			int count = orderDao.delete(id);
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
	 * 新增一条Order记录，执行成功后传入对象及返回对象的主键属性值不为null
	 * 
	 * @param order
	 *            新增的Order数据（如果无特殊需求，新增对象的主键值请保留为null）
	 * @param operator
	 *            操作人对象
	 * @return
	 */
	@Transactional
	public ResultInfo<Order> addOrder(Order order, Operator operator) {
		ResultInfo<Order> resultInfo = new ResultInfo<Order>();

		if (order == null) { // 传入参数无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG);
			log.info(Constant.ERR_MSG_INVALID_ARG + " order = " + order);
		} else {
			try {
				// 如果传入参数的主键为null，则调用生成主键的方法获取新的主键
				if (order.getOrderNo() == null) {
					order.setOrderNo(this.generatePK());
				}
				// 如果传入的操作人不为null，则设置新增记录的操作人类型和操作人id
				if (operator != null) {
					order.setOperatorType(operator.getOperatorType());
					order.setOperatorId(operator.getOperatorId());
				}

				// 设置创建时间和更新时间为当前时间
				Date now = new Date();
				order.setCreateTime(now);
				order.setUpdateTime(now);

				// 填充默认值
				this.fillDefaultValues(order);

				// 调用Dao执行插入操作
				if (null == order.getMemberNo()) {
					resultInfo.setCode(Constant.FAIL);
					resultInfo.setMsg("会员信息丢失");
				} else {
					orderDao.add(order);
					resultInfo.setCode(Constant.SECCUESS);
					resultInfo.setData(order);
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
	 * 根据主键，更新一条Order记录
	 * 
	 * @param order
	 *            更新的Order数据，且其主键不能为空
	 * @param operator
	 *            操作人对象
	 * @return
	 */
	@Transactional
	public ResultInfo<Order> updateOrder(Order order, Operator operator) {
		ResultInfo<Order> resultInfo = new ResultInfo<Order>();

		if (order == null || order.getOrderNo() == null) { // 传入参数无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG);
			log.info(Constant.ERR_MSG_INVALID_ARG + " order = " + order);
		} else {
			try {
				// 如果传入的操作人不为null，则设置更新记录的操作人类型和操作人id
				if (operator != null) {
					order.setOperatorType(operator.getOperatorType());
					order.setOperatorId(operator.getOperatorId());
				}

				// 设置更新时间为当前时间
				order.setUpdateTime(new Date());

				// 调用Dao执行更新操作，并判断更新语句执行结果
				int count = orderDao.update(order);
				if (count > 0) {
					resultInfo.setCode(Constant.SECCUESS);
				} else {
					resultInfo.setCode(Constant.FAIL);
				}
				resultInfo.setData(order);
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
		// return "O"+ECDateUtils.formatDate(new
		// Date(),"yyyyMMdd")+primarykeyService.getValueByBizType(PrimarykeyConstant.orderType);
		return "D" + primarykeyService.getValueByBizType(PrimarykeyConstant.orderType);
	}

	/**
	 * 为Order对象设置默认值
	 * 
	 * @param obj
	 */
	public void fillDefaultValues(Order obj) {
		if (obj != null) {
			if (obj.getMemberType() == null) {
				obj.setMemberType(1);
			}
			if (obj.getIsBizOrder() == null) {
				obj.setIsBizOrder(0);
			}
			if (obj.getOrderStatus() == null) {
				obj.setOrderStatus(0);
			}
			if (obj.getPayStatus() == null) {
				obj.setPayStatus(0);
			}
			if (obj.getIsIllegal() == null) {
				obj.setIsIllegal(0);
			}
			if (obj.getIsAccident() == null) {
				obj.setIsAccident(0);
			}
			if (obj.getIsFault() == null) {
				obj.setIsFault(0);
			}
			if (obj.getIsNeedInvoice() == null) {
				obj.setIsNeedInvoice(0);
			}
			if (obj.getIsInvoiceIssued() == null) {
				obj.setIsInvoiceIssued(0);
			}
			if (obj.getOrderSource() == null) {
				obj.setOrderSource("1");
			}
			if (obj.getIsDelete() == null) {
				obj.setIsDelete(0);
			}
		}
	}

	@Override
	public ResultInfo<Order> getTripOrder(String orderNo) {
		ResultInfo<Order> resultInfo = new ResultInfo<Order>();
		Order order = orderDao.get(orderNo);
		int minutes = 0;
		if (order.getStartBillingTime() != null) {
			minutes = ECDateUtils.differMinutes(order.getStartBillingTime(), new Date()).intValue();
		} else {
			// 订单时长（当前时间-计费时间）,为了防止开始计费时间为空，那么这里就直接变成 订单创建时间+20分钟；
			Calendar c = Calendar.getInstance();
			c.setTime(order.getCreateTime());
			c.add(Calendar.MINUTE, 20);
			minutes = ECDateUtils.differMinutes(c.getTime(), new Date()).intValue();
		}
		if (order.getFinishTime() != null) {
			minutes = ECDateUtils.differMinutes(order.getStartBillingTime(), order.getFinishTime()).intValue();
		} else {
			order.setFinishTime(new Date());
		}
		order.setOrderDuration(minutes);
		resultInfo.setData(order);
		resultInfo.setCode("1");
		return resultInfo;
	}

	@Override
	public List<Park> getOrderByMemberNoTake(String memberNo) {
		// TODO Auto-generated method stub
		return orderDao.getByMemberNoTake(memberNo);
	}

	@Override
	@Transactional
	public ResultInfo<Order> cancelOrder(String orderNo) {
		ResultInfo<Order> resultInfo = new ResultInfo<Order>();
		Order order = orderDao.get(orderNo);
		// order.setOrderStatus(4);//订单状态（4：已取消）
		// order.setCancelTime(new Date());
		// order.setFinishTime(order.getCancelTime());
		// orderDao.update(order);
		Date now = new Date();
		int n = orderDao.cancelOrder(orderNo, now);
		if (n > 0) {
			CarStatus carStatus = new CarStatus();// carStatusDao.get(order.getCarNo());
			carStatus.setCarNo(order.getCarNo());
			carStatus.setUserageStatus(0);// 使用状态（0：空闲）
			carStatusService.updateCarStatus(carStatus, null);
			order.setOrderStatus(4);
			order.setRegardlessFranchise(0d);
			order.setFinishTime(now);
			order.setCancelTime(now);
			// 订单取消后修改对应车的红包记录
			CarRedPacket carRedPacket = carRedPacketService.getCarRedPacketByCarPlateNo(order.getCarPlateNo(), "2");
			if (carRedPacket != null) {
				CarRedPacket carRedPacketForUpdate = new CarRedPacket();
				carRedPacketForUpdate.setCarRedPacketId(carRedPacket.getCarRedPacketId());
				carRedPacketForUpdate.setCarUserageStatus(3);
				carRedPacketForUpdate.setCarRedPacketStatus(3);
				;
				carRedPacketService.updateCarRedPacket(carRedPacketForUpdate, null);
			}
			resultInfo.setCode("1");
		} else {
			resultInfo.setCode("0");
		}

		resultInfo.setData(order);
		return resultInfo;
	}

	@Override
	public List<Park> getOrderByMemberNoReturn(String memberNo) {
		// TODO Auto-generated method stub
		return orderDao.getByMemberNoReturn(memberNo);
	}

	// 判断当前是否存在正在进行中的订单
	@Override
	public int judgeNowadayOrder(String memberNO) {
		return orderDao.judgeNowadayOrder(memberNO);
	}

	// 得到当前订单
	@Override
	public Order getNowadayOrderByMemberNo(String memberNo) {
		Order order = null;
		List<Order> orderList = orderDao.getNowadayOrderByMemberNo(memberNo);
		if (orderList != null && orderList.size() > 0) {
			order = orderList.get(0);
		}
		return order;
	}

	/**
	 * 修改首次计费时间
	 */
	@Override
	@Transactional
	public ResultInfo<String> startOrdeOpenCarDoorTime(String orderNo, Date OpenCarDoorTime) {
		ResultInfo<String> result = new ResultInfo<>();
		int n = orderDao.startOrdeOpenCarDoorTime(orderNo, OpenCarDoorTime);
		if (n > 0) {
			result.setCode(OrderConstant.success_code);
			result.setMsg("");
		} else if (n == 0) {
			result.setCode(OrderConstant.fail_code);
			result.setMsg(OrderConstant.hasRecord);
		} else {
			result.setCode(OrderConstant.fail_code);
			result.setMsg(OrderConstant.fail_msg);
		}
		return result;
	}

	/**
	 * 记录计费时间（只记录一次）
	 */
	@Override
	@Transactional
	public ResultInfo<String> setStartBillingTime(String orderNo, Date startBillingTime) {
		ResultInfo<String> result = new ResultInfo<>();
		int n = orderDao.setStartBillingTime(orderNo, startBillingTime);
		if (n > 0) {
			result.setCode(OrderConstant.success_code);
			result.setMsg("");
		} else if (n == 0) {
			result.setCode(OrderConstant.fail_code);
			result.setMsg(OrderConstant.hasRecord);
		} else {
			result.setCode(OrderConstant.fail_code);
			result.setMsg(OrderConstant.fail_msg);
		}
		return result;
	}

	/** 记录取车点 */
	@Override
	public void recordTakeCarLocation(String orderNo) {
		if (orderNo != null) {
			Order order = orderDao.get(orderNo);
			Park park = parkDao.get(order.getStartParkNo());
			// String actual_take_loacton = park == null ? ""
			// : (park.getAddrRegion1Name() + park.getAddrRegion2Name() +
			// park.getAddrRegion3Name()
			// + park.getAddrStreet());
			String actual_take_loacton = park == null ? "" : (park.getParkName());
			// 只更新订单的一个字段
			Order orderForUpdate = new Order();
			orderForUpdate.setOrderNo(orderNo);
			orderForUpdate.setActualTakeLoacton(actual_take_loacton);
			orderDao.update(orderForUpdate);
		}
	}

	/** 记录取车点 */
	@Override
	public void recordTakeCarLocation(Order order) {
		if (order != null && order.getStartParkNo() != null) {
			Park park = parkDao.get(order.getStartParkNo());
			if (park == null) {
				return;
			}
			// String actual_take_loacton = park == null ? ""
			// : (park.getAddrRegion1Name() + park.getAddrRegion2Name() +
			// park.getAddrRegion3Name()
			// + park.getAddrStreet());
			String actual_take_loacton = park == null ? "" : (park.getParkName());
			// 只更新订单的一个字段
			Order orderForUpdate = new Order();
			orderForUpdate.setOrderNo(order.getOrderNo());
			orderForUpdate.setActualTakeLoacton(actual_take_loacton);
			orderDao.update(orderForUpdate);
		}
	}

	/**
	 * 记录第一次开车门时间及开始计费时间，同时订单状态改为“已计费”
	 * 
	 * @param orderNo
	 * @param datetime
	 * @param memberNo
	 * @return
	 */
	@Transactional
	public ResultInfo<String> firsttimeOpenCarDoorAndStartBilling(String orderNo, Date datetime, String memberNo) {
		ResultInfo<String> result = new ResultInfo<String>();
		int n = -1;
		if (orderNo != null && datetime != null) {
			n = orderDao.firsttimeOpenCarDoorAndStartBilling(orderNo, datetime, memberNo); // 更新此订单的首次开车门时间及开始计费时间
			if (n > 0) {
				result.setCode(OrderConstant.success_code);
				result.setMsg("");

				Order order = this.getOrder(orderNo);
				String carNo = order == null ? null : order.getCarNo();
				if (carNo != null && carNo.length() != 0) {
					try {
						// 以下将车辆使用状态，转为订单中
						CarStatus carStatus = new CarStatus();
						carStatus.setCarNo(carNo);
						carStatus.setUserageStatus(CarConstant.CAR_USERAGE_STATUS_IN_ORDER);
						carStatus.setUpdateTime(new Date());
						carStatusService.updateCarStatus(carStatus, null); // 更新车辆状态

					} catch (Exception e) {
						log.error(e.getMessage(), e);
					}
				}

				// try {
				// this.recordTakeCarLocation(orderNo); //记录实际取车点信息
				// } catch (Exception e) {
				// log.error(e.getMessage(), e);
				// }

			} else if (n == 0) {
				result.setCode(OrderConstant.fail_code);
				result.setMsg(OrderConstant.hasRecord);
			} else {
				result.setCode(OrderConstant.fail_code);
				result.setMsg(OrderConstant.fail_msg);
			}
		}
		return result;
	}

	/**
	 * 根据车辆编号，修改订单表，记录第一次开车门时间及开始计费时间，同时订单状态改为“已计费
	 * 
	 * @param carNo
	 * @param datetime
	 * @return
	 */
	@Transactional
	public ResultInfo<String> updateFirsttimeOpenCarDoorAndStartBillingByCarNo(String carNo, Date datetime) {
		ResultInfo<String> result = new ResultInfo<String>();
		result.setCode(OrderConstant.fail_code);
		result.setMsg(OrderConstant.fail_msg);

		int n = -1;
		if (carNo != null && datetime != null) {
			n = orderDao.updateFirsttimeOpenCarDoorAndStartBillingByCarNo(carNo, datetime); // 更新相关订单的首次开车门时间及开始计费时间
			if (n > 0) {
				result.setCode(OrderConstant.success_code);
				result.setMsg("");

				try {
					// 以下将车辆使用状态，转为订单中
					CarStatus carStatus = new CarStatus();
					carStatus.setCarNo(carNo);
					carStatus.setUserageStatus(CarConstant.CAR_USERAGE_STATUS_IN_ORDER);
					carStatus.setUpdateTime(new Date());
					carStatusService.updateCarStatus(carStatus, null); // 更新车辆状态
					// 订单已计费时，存入车辆里程
					CarStatus cs = carStatusService.getCarStatus(carNo);
					// 查询当前车辆对应的订单
					Order o = orderDao.getOrderNowByCarNo(carNo);
					// 根据计费类型和自定义日期判断当前订单属于哪一天
					judgeOrderDate(o, cs);

				} catch (Exception e) {
					log.error(e.getMessage(), e);
				}

			} else if (n == 0) {
				result.setCode(OrderConstant.fail_code);
				result.setMsg(OrderConstant.hasRecord);
			} else {
				result.setCode(OrderConstant.fail_code);
				result.setMsg(OrderConstant.fail_msg);
			}

			// 判断首次开门时间有没有值 没有了 则赋值
			Order or = new Order();
			or.setCarNo(carNo);
			or.setOrderStatus(2);
			List<Order> orders = getOrderList(new Query(or));
			if (orders != null && orders.size() > 0) {
				if (orders.get(0).getOpenCarDoorTime() == null) {
					orders.get(0).setOpenCarDoorTime(new Date());
					updateOrder(orders.get(0), null);
				}
			}

		}
		return result;
	}

	/**
	 * 得到所有开始自动计费的订单,并进行相应的处理 条件：1、订单状态为1（已预约）的订单； 2、计费时间为空；
	 * 3、距离下单时间已经达到20（系统参数设置的）分钟；
	 */
	@Override
	public List<Order> getAllAutoBeginCharging() {
		List<Order> orList = orderDao.getAllAutoBeginCharging();
		log.error("-------统计所有开始自动计费的订单--------");
		if (orList != null && orList.size() > 0) {
			for (Order o : orList) {
				// 计算当前时间和订单 创建的时间的差
				int minute = ECDateUtils.differMinutes(o.getCreateTime(), new Date()).intValue();
				// 得到（系统参数设置的）20分钟；
				SysParam sysp = sysParamService.getByParamKey(CarConstant.countdown_param_key);
				int dingshi = 20;
				if (sysp != null && sysp.getParamValue() != null && !sysp.getParamValue().equals("")) {
					dingshi = Integer.parseInt(sysp.getParamValue());
				}
				if (minute >= dingshi) {
					log.error("-------修改订单开始--------");
					// 如果当前已经超过倒计时的时间，那么则 修改订单状态，让其变为已计费；
					o.setOrderStatus(2);
					o.setStartBillingTime(new Date());
					o.setUpdateTime(new Date());
					orderDao.update(o);
					CarStatus carStatus = new CarStatus();
					carStatus.setUserageStatus(2);
					carStatus.setCarNo(o.getCarNo());
					carStatusService.updateCarStatus(carStatus, null);
					log.error("-------修改订单结束--------");
				}
			}
		}
		return null;
	}

	/**
	 * 得到所有自动取消计费的订单，并进行相应的处理 条件：1、订单状态为2（已计费）的订单； 2、开始计费时间不为空， 3、开门时间为空；
	 * 4、距离下单时间已经达到60（系统参数设置的）分钟；
	 * 
	 * @throws ParseException
	 */
	@Override
	public List<Order> getAllAutoEndCharging() throws ParseException {
		List<Order> orList = orderDao.getAllAutoBeginCharging();
		log.error("-------统计需要取消计费的订单--------");
		if (orList != null && orList.size() > 0) {
			for (Order o : orList) {
				// 计算当前时间和订单 创建的时间的差
				int minute = ECDateUtils.differMinutes(o.getCreateTime(), new Date()).intValue();
				// 得到（系统参数设置的）60分钟，自动取消订单时长；
				SysParam sysp = sysParamService.getByParamKey(CarConstant.cancelordertime_param_key);
				int dingshi = 60;
				if (sysp != null && sysp.getParamValue() != null && !sysp.getParamValue().equals("")) {
					dingshi = Integer.parseInt(sysp.getParamValue());
				}
				if (minute >= dingshi) {

					// 结束订单前判断车辆是否熄火和在场站内
					CarStatus carStatus = carStatusService.getCarStatus(o.getCarNo());
					// 车辆状态（1、已启动，2、已熄火）
					if (carStatus.getCarStatus() != null) {

						// 根据传的车辆位置，查找周边场站距离最近的场站
						String parkNo = "";
						parkNo = parkService.getCurrentParkNoByCarLocation(carStatus.getLongitude(),
								carStatus.getLatitude());
						if ("".equals(parkNo)) {
							log.error("-------订单超时取消时车辆未在场站--------");
							log.error("-------修改订单开始--------");
							// 如果当前已经超过倒计时的时间，那么则 修改订单状态，让其变为已计费；
							o.setOrderStatus(2);
							o.setStartBillingTime(new Date());
							// o.setOpenCarDoorTime(new Date());
							o.setUpdateTime(new Date());
							orderDao.update(o);
							CarStatus carStatus2 = new CarStatus();
							carStatus2.setUserageStatus(2);
							carStatus2.setCarNo(o.getCarNo());
							carStatusService.updateCarStatus(carStatus2, null);
							// 订单已计费时，存入车辆里程
							judgeOrderDate(o, carStatus);
							log.error("-------修改订单结束--------");
						} else {
							SysParam sysps = sysParamService.getByParamKey(CarConstant.order_overTime_charging);
							if (sysps != null && sysps.getParamValue().equals("1")) {
								CarStatus carStatu = new CarStatus();
								carStatu.setUserageStatus(2);
								carStatu.setCarNo(o.getCarNo());
								carStatusService.updateCarStatus(carStatu, null);

								Order or = new Order();
								or.setOrderNo(o.getOrderNo());
								or.setUpdateTime(new Date());
								// or.setOpenCarDoorTime(new Date());
								or.setOrderStatus(2);
								if (o.getStartBillingTime() == null || "".equals(o.getStartBillingTime())) {
									or.setStartBillingTime(new Date());
								}
								orderDao.update(or);
								// 订单已计费时，存入车辆里程
								or.setRuleNo(o.getRuleNo());
								judgeOrderDate(or, carStatus);
							} else {
								// 取消订单时增加关闭动力的指令，但无需等待成功响应
								Device device = deviceService.getDeviceByCarNo(o.getCarNo());
								try {
									if (device != null && "1".equals(device.getVersionType())) {
										carStatusService.controlPower(device.getDeviceSn(), "0");
									}
								} catch (Exception e) {
								}
								// 如果当前已经超过倒计时的时间，那么则 修改订单状态，让其变为已完成；
								o.setOrderStatus(3);
								o.setFinishType(2);
								o.setFinishTime(new Date());
								// 订单结束向结束表添加数据
								if (o != null) {
									OrderFinish orderFinish = new OrderFinish();
									orderFinish.setOrderNo(o.getOrderNo());
									orderFinish.setFinishType(2);
									orderFinish.setCurrentCarLocation(o.getActualTakeLoacton());
									Operator operator = new Operator();
									orderFinishService.addOrderFinish(orderFinish, operator);
								}
								int minutes = 0;
								if (o.getStartBillingTime() != null) {
									minutes = ECDateUtils.differMinutes(o.getStartBillingTime(), new Date()).intValue();
								} else {
									o.setStartBillingTime(o.getFinishTime());
								}
								o.setOrderDuration(minutes);// 设置订单时长

								// ---------------------------------------*****---------------下面是引用订单结算的代码------------------***-----------------------------------------------

								SysParam sp = sysParamService.getByParamKey("ORDER_OVERTIME_CHARGING");
								if (sp != null && sp.getParamValue().equals("0")) {
									o = cancelOrder(o.getOrderNo()).getData();
								} else {
									o = orderBalance(o, parkNo).getData();
								}
								// 订单结算结束
								// ---------------------------------------*****---------------上面是引用订单结算的代码------------------***-----------------------------------------------
								o.setUpdateTime(new Date());
								log.error("-------统计需要取消计费的订单,修改订单--------");
								int row = orderDao.update(o);
								if (row > 0) {
									log.error("-------统计需要取消计费的订单,修改订单完成--------");
									CarStatus c = new CarStatus();
									c.setUserageStatus(0);
									c.setCarNo(o.getCarNo());
									log.error("-------统计需要取消计费的订单,修改车辆状态开始--------");
									ResultInfo<CarStatus> res = carStatusService.updateCarStatus(c, null);
									if (res.getCode().equals(1)) {
										log.error("-------统计需要取消计费的订单,修改车辆状态完成--------");
									}
								}
							}
						}

					}

				}
			}
		}
		return null;
	}

	@Override
	public int countOrder(Query q) {
		int count = 0;
		try {
			count = ECCalculateUtils.convertsToInt(orderDao.count(q));
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return count;
	}

	@Override
	public Double getAmountByMemberNo(String memberNo) {
		Double noPayAmount = 0d;
		if (orderDao.getAmountByMemberNo(memberNo) != null) {
			noPayAmount = orderDao.getAmountByMemberNo(memberNo);
		}
		return noPayAmount;
	}

	@Override
	public Long getOrderNumByMemberNo(String memberNo) {
		Long orderNum = 0l;
		if (orderDao.getOrderNumByMemberNo(memberNo) != null) {
			orderNum = orderDao.getOrderNumByMemberNo(memberNo);
		}
		return orderNum;
	}

	@Transactional(propagation = Propagation.SUPPORTS)
	public List<Order> getIdleCar(Query q) {
		return orderDao.getIdleCar(q);
	}

	@Override
	public int countIdleCar(Query q) {
		int count = 0;
		try {
			Long res = orderDao.countIdleCar(q);
			count = res == null ? 0 : res.intValue();
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return count;
	}

	@Override
	public ResultInfo<Order> orderBalance(Order order, String endParkNo) throws ParseException {

		ResultInfo<Order> resultInfo = new ResultInfo<Order>();
		// 订单时长（结束时间-计费时间）
		int minutes = 0;
		if (order.getStartBillingTime() != null && order.getFinishTime() != null) {
			minutes = ECDateUtils.differMinutes(order.getStartBillingTime(), order.getFinishTime()).intValue();
		}
		order.setOrderDuration(minutes);
		// 起步价
		Double baseFee = 0d;
		// 订单结算开始
		// 计费规则（1.根据车型和会员类型查找计费规则，有：使用计费规则计费；无：根据车型查找计费规则，使用规则计费）
		PricingRule rule = null;
		Car car = carDao.get(order.getCarNo());
		if (car != null) {
			Member member = memberDao.get(order.getMemberNo());
			if (member != null) {
				// 订单总里程
				Double orderMileage = 0d;
				// 订单里程计算
				if (order.getOrderStartMileage() != null && order.getOrderFinishMileage() != null) {
					orderMileage = ECCalculateUtils.sub(order.getOrderFinishMileage(), order.getOrderStartMileage());
				}
				if (orderMileage.compareTo(0d) >= 0) {
					order.setOrderMileage(ECNumberUtils.roundDoubleWithScale(orderMileage, 2));
				}
				if (member.getMemberType().equals(2)) {// 集团会员
					String companyId = member.getCompanyId();// 集团id
					rule = pricingRuleService.getPricingRuleUseByMM(car.getCarBrandName(), order.getCarModelName(),
							companyId);
					if (rule != null) {
						caculateOrderAmount(order, rule);
					} else {// 判断该集团对该车型有没有特殊的计费规则，没有的话，采用和普通会员一样的计费规则
						rule = pricingRuleService.getPricingRuleUseByMMP(car.getCarBrandName(),
								order.getCarModelName());
						if (rule != null) {
							caculateOrderAmount(order, rule);// 计算订单总金额，包含当天封顶金额判断
						} else {
							resultInfo.setCode(OrderConstant.fail_code);
							resultInfo.setMsg(OrderConstant.noRule);
							return resultInfo;
						}
					}
				} else {// 普通会员
					rule = pricingRuleService.getPricingRuleUseByMMP(car.getCarBrandName(), order.getCarModelName());
					if (rule != null) {
						caculateOrderAmount(order, rule);// 计算订单总金额，包含当天封顶金额判断
					} else {
						resultInfo.setCode(OrderConstant.fail_code);
						resultInfo.setMsg(OrderConstant.noRule); // 暂无该车型的计费规则
						return resultInfo;
					}

				}
			}

			if (rule != null) {
				// 添加起步价
				if (rule.getBaseFee() != null) {
					baseFee = rule.getBaseFee();
				}
			}

			// 订单金额加起步价
			order.setOrderAmount(ECCalculateUtils.add(order.getOrderAmount(), baseFee));
			// 取车场站的费用
			Park takePark = parkService.getPark(order.getStartParkNo());
			Double serviceFeeGet = 0d;
			if (takePark != null && null != takePark.getServiceFeeGet()) {
				serviceFeeGet = takePark.getServiceFeeGet();
			}
			// 订单金额加取车场站的费用
			// 添加系统参数 订单时长大于分钟免收取车费
			SysParam spa = sysParamService.getByParamKey("VOID_SERVICE_FEE_GET");
			if (spa != null && "1".equals(spa.getParamValue())) {
				SysParam spav = sysParamService.getByParamKey("VOID_ORDER_DURATION");
				if (spav != null) {
					if (order.getOrderDuration() > Integer.valueOf(spav.getParamValue())) {
						order.setOrderAmount(ECCalculateUtils.add(order.getOrderAmount(), 0d));
					} else {
						order.setOrderAmount(ECCalculateUtils.add(order.getOrderAmount(), serviceFeeGet));
					}
				}
			} else {
				order.setOrderAmount(ECCalculateUtils.add(order.getOrderAmount(), serviceFeeGet));
			}

			// 结束订单时 查看订单时长 和配置的时间比较 如果订单时长 小于 配置时间 则没有取车服务费
			SysParam spm = sysParamService.getByParamKey("SMALL_SERVICE_FEE_GET");
			if (spm != null && "1".equals(spm.getParamValue()) && order.getOrderStatus() == 3) {
				SysParam spam = sysParamService.getByParamKey("SMALL_ORDER_DURATION");
				if (spam != null) {
					if (order.getOrderDuration() < Integer.valueOf(spam.getParamValue())) {
						order.setOrderAmount(ECCalculateUtils.sub(order.getOrderAmount(), serviceFeeGet));
					}
				}
			}

			// 还车场站的费用
			Double serviceFeeBack = 0d;
			if (endParkNo != null && !"".equals(endParkNo)) {
				Park returnPark = parkService.getPark(endParkNo);
				if (serviceFeeBack != null && null != returnPark.getServiceFeeBack()) {
					serviceFeeBack = returnPark.getServiceFeeBack();
				}
			}
			// 订单金额加还车场站的费用
			order.setOrderAmount(ECCalculateUtils.add(order.getOrderAmount(), serviceFeeBack));
			// 订单金额添加不计免赔金额
			if (order.getRegardlessFranchise() != null) {
				order.setOrderAmount(ECCalculateUtils.add(order.getOrderAmount(), order.getRegardlessFranchise()));
			}
			// 对订单总金额进行四舍五入
			order.setOrderAmount(ECCalculateUtils.round(order.getOrderAmount(), 2));
			// 计算优惠券抵扣金额，取抵扣金额最大的进行抵扣
			Double couponAmount = caculateCouponDikouAmount(order);
			// 设置优惠金额
			order.setDiscountAmount(couponAmount);// 优惠券金额以discountAmount入库，便于订单详情查询
			if (order.getOrderAmount().compareTo(0d) == 0) {
				order.setPayStatus(1);
				order.setPaymentTime(new Date());
			} else {
				// 优惠券抵扣后，剩余金额需要进行金豆抵扣计算
				Double diKouAmount = ECCalculateUtils.sub(order.getOrderAmount(), couponAmount);
				if (diKouAmount > 0.0) {
					// 使用金豆抵扣订单金额
					diKouOrderAmoutByGlodBeans(order, diKouAmount);
				}
				// 金豆抵扣金额
				if (order.getGlodBeansDedutionAmount() == null) {
					order.setGlodBeansDedutionAmount(0.0);
				}
				Double glodBeansDiKouAmount = order.getGlodBeansDedutionAmount();
				if (diKouAmount.compareTo(glodBeansDiKouAmount) > 0) {// 如果优惠券抵扣后，剩余金额大于需要金豆抵扣金额
					// 金豆抵扣后剩余的金额
					Double pricingPackDiKouAmount = ECCalculateUtils.sub(diKouAmount, glodBeansDiKouAmount);
					// 金豆抵扣后，剩余金额需要进行会员折扣计算
					Double memberLevelDiscountAmount = caculateMemberLevelDiscoutAmount(member, pricingPackDiKouAmount);
					if (memberLevelDiscountAmount.compareTo(0.0) > 0) {
						order.setMemberLevelDiscountAmount(memberLevelDiscountAmount);
					} else {
						order.setMemberLevelDiscountAmount(0.0);
					}
					// 剩余需要金额套餐抵扣的金额
					diKouAmount = ECCalculateUtils.sub(pricingPackDiKouAmount, memberLevelDiscountAmount);
					if (diKouAmount.compareTo(0.0) > 0) {
						// 金额套餐抵扣
						order = diKouByPricingPackOrderAmout(order, diKouAmount);
					}
				}

				// 判断订单总金额和套餐抵扣金额，设置应付金额
				if (order.getOrderAmount().compareTo(0d) == 0) {
					// 套餐抵扣足以支付订单，订单支付完成
					order.setPayStatus(1);// 已支付
					order.setPayableAmount(0d);
					order.setPaymentTime(new Date());
					// 设置应支付金额=订单总金额-（套餐金额抵扣金额+优惠券抵扣金额+会员折扣金额+金豆抵扣）
				} else {
					if (order.getPackAmountDiscountAmount() == null) {
						order.setPackAmountDiscountAmount(0d);
					}
					if (order.getMemberLevelDiscountAmount() == null) {
						order.setMemberLevelDiscountAmount(0d);
					}
					if (order.getGlodBeansDedutionAmount() == null) {
						order.setGlodBeansDedutionAmount(0d);
					}
					// 总抵扣金额（套餐金额抵扣金额+优惠券抵扣金额+会员折扣金额+金豆抵扣）
					Double totalDikouAmount = ECCalculateUtils.add(order.getDiscountAmount(),
							order.getPackAmountDiscountAmount());
					totalDikouAmount = ECCalculateUtils.add(totalDikouAmount, order.getMemberLevelDiscountAmount());
					totalDikouAmount = ECCalculateUtils.add(totalDikouAmount, order.getGlodBeansDedutionAmount());
					Double payableAmount = ECCalculateUtils.sub(order.getOrderAmount(), totalDikouAmount);
					if (payableAmount.compareTo(0.0) > 0) {
						order.setPayableAmount(payableAmount);
						// 应支付金额保留两位小数
						order.setPayableAmount(ECNumberUtils.roundDoubleWithScale(order.getPayableAmount(), 2));
					}
				}
			}
			caculteCarRedPacket(order, member, endParkNo);
		}
		// 判断应支付金额，如果为0，则修改支付状态为已支付
		if (order.getPayableAmount().equals(0.0)) {
			order.setPayStatus(1);
			order.setPaymentTime(new Date());
		}
		resultInfo.setData(order);
		return resultInfo;
	}

	/**
	 * 红包车发红包业务处理
	 * 
	 * @param order
	 * @param carStatus
	 * @param member
	 */
	private void caculteCarRedPacket(Order order, Member member, String endParkNo) {
		// 订单结束后修改对应车的红包记录
		CarRedPacket carRedPacket = carRedPacketService.getCarRedPacketByCarPlateNo(order.getCarPlateNo(), "2");
		if (carRedPacket != null) {
			boolean flag = false;
			boolean isTargetParkNo = false;
			if (endParkNo != null && !"".equals(endParkNo)) {
				// 红包车目标场站判断
				String carRedPacketTartetParkNoStr = carRedPacket.getParkNo();
				String[] carRedPacketTartetParkNoArr = carRedPacketTartetParkNoStr.split(",");
				for (String targetParkNo : carRedPacketTartetParkNoArr) {
					if (targetParkNo.equals(endParkNo)) {
						isTargetParkNo = true;
						break;
					}
				}
				if (isTargetParkNo) {
					CarStatus carStatus = carStatusDao.get(order.getCarNo());
					// 有订单金额限制
					if (new Integer(1).equals(carRedPacket.getIsOrderAmountLimit())) {
						// 订单金额>配置限额
						if (order.getOrderAmount().compareTo(carRedPacket.getOrderAmountLimit()) >= 0) {
							// 有充电限制
							if (new Integer(1).equals(carRedPacket.getIsCharge())) {
								if (new Integer(1).equals(carStatus.getChargeState())) {
									sendCarRedPacket(carRedPacket, member);
									flag = true;
								}
							} else {
								sendCarRedPacket(carRedPacket, member);
								flag = true;
							}
						}
					} else {
						// 有充电限制
						if (new Integer(1).equals(carRedPacket.getIsCharge())) {
							if (new Integer(1).equals(carStatus.getChargeState())) {
								sendCarRedPacket(carRedPacket, member);
								flag = true;
							}
						} else {
							sendCarRedPacket(carRedPacket, member);
							flag = true;
						}
					}
				}
			}
			CarRedPacket carRedPacketForUpdate = new CarRedPacket();
			if (flag) {
				carRedPacketForUpdate.setRedPacketSendStatus(1);// 红包已下发
				order.setIsRedPacketSend(1);
			} else {
				order.setIsRedPacketSend(0);
			}
			carRedPacketForUpdate.setCarRedPacketId(carRedPacket.getCarRedPacketId());
			carRedPacketForUpdate.setCarUserageStatus(3);
			carRedPacketForUpdate.setCarRedPacketStatus(3);
			carRedPacketService.updateCarRedPacket(carRedPacketForUpdate, null);
		}
	}

	private void sendCarRedPacket(CarRedPacket carRedPacket, Member member) {
		PricingPackage queryPricingPackage = new PricingPackage();
		queryPricingPackage.setPackageType(5);// 红包套餐
		List<PricingPackage> packageList = pricingPackageService.getPricingPackageList(new Query(queryPricingPackage));
		if (packageList.size() > 0) {
			PricingPackage pricePackage = packageList.get(0);
			PricingPackOrder pricingPackOrder = new PricingPackOrder();
			pricingPackOrder.setMemberName(member.getMemberName());
			pricingPackOrder.setMemberNo(member.getMemberNo());
			pricingPackOrder.setMobilePhone(member.getMobilePhone());
			pricingPackOrder.setPackageId(pricePackage.getPackageNo());
			pricingPackOrder.setPackageName(pricePackage.getPackageName());
			pricingPackOrder.setUserdMinute(0);
			pricingPackOrder.setPaymentMethod(4);// 赠送的套餐，支付方式为其他。
			pricingPackOrder.setPayStatus(1);
			;
			pricingPackOrder.setIsAvailable(1);
			pricingPackOrder.setPackAmount(carRedPacket.getRedPacketAmount());
			pricingPackOrder.setPackOrderAmount(carRedPacket.getRedPacketAmount());
			pricingPackOrder.setUseredOrderAmount(0d);
			pricingPackOrder.setPayableAmount(carRedPacket.getRedPacketAmount());
			pricingPackOrder.setInvoiceStatus(0);
			// 有效期的起止时间
			Date availableTime1 = ECDateUtils.getCurrentDateTime();
			pricingPackOrder.setVailableTime1(availableTime1);
			String dateString = "2099-12-31 00:00:00";
			pricingPackOrder.setVailableTime2(ECDateUtils.parseDate(dateString, "yyyy-MM-dd HH:mm:ss"));
			ResultInfo<PricingPackOrder> res = pricingPackOrderService.addPricingPackOrder(pricingPackOrder, null);
			if (res.getCode().equals(Constant.SECCUESS)) {
				// 查询我的余额
				Query qr = new Query();
				PricingPackOrder ppor = new PricingPackOrder();
				ppor.setPayStatus(1);// 已经支付的
				ppor.setIsAvailable(1);// 可用
				ppor.setNowTime(new Date());
				ppor.setMemberNo(pricingPackOrder.getMemberNo());
				qr.setQ(ppor);
				List<PricingPackOrder> pporList = pricingPackOrderService.getPricingPackOrderListBypo(qr);
				Double poa = 0.0;
				Double uoa = 0.0;
				for (PricingPackOrder packOrder : pporList) {
					if (!packOrder.getPackageId().equals(pricingPackOrder.getPackageId())) {
						if (packOrder.getPackOrderAmount() != null) {
							poa = ECCalculateUtils.add(poa, packOrder.getPackOrderAmount());
						}
						if (packOrder.getUseredOrderAmount() != null) {
							uoa = ECCalculateUtils.add(uoa, packOrder.getUseredOrderAmount());
						}
					}
				}
				// 金额套餐赠送成功后给记账表添加记录
				Double ye = ECCalculateUtils.sub(poa, uoa);
				Accounts ac = new Accounts();
				ac.setMemberNo(pricingPackOrder.getMemberNo());
				ac.setBusinessNo(pricingPackOrder.getPackOrderNo());
				ac.setBusinessType(2);
				ac.setAccountType(2);
				ac.setAccountMoney(pricingPackOrder.getPackOrderAmount());
				ac.setAccountBeforeMoney(ye);
				ac.setAccountOverMoney(ECCalculateUtils.add(ye, ac.getAccountMoney()));
				ac.setAccountTime(new Date());
				accountsService.addAccounts(ac, null);
			}
		}

	}

	/**
	 * 根据会员等级计算会员订单打折金额
	 * 
	 * @param member
	 * @param dikouAmount待抵扣金额
	 * @return 返回抵扣金额
	 */
	private Double caculateMemberLevelDiscoutAmount(Member member, Double dikouAmount) {
		if (member != null) {
			String levelId = member.getMemberLevelId();
			if (levelId != null && !"".equals(levelId)) {
				MemberLevel memberLevel = memberLevelService.getMemberLevel(levelId);
				if (memberLevel != null) {
					if (memberLevel.getIsAvailable() == 1 && memberLevel.getIsDeleted() == 0) {
						Double levleDiscount = memberLevel.getLevelDiscount();
						if (levleDiscount.compareTo(1.0) < 0) {// levleDiscount小于1.0才计算
							// 会员等级折扣金额
							Double memberLevelDiscountAmount = ECCalculateUtils.mul(dikouAmount, 1.0 - levleDiscount);
							memberLevelDiscountAmount = ECCalculateUtils.round(memberLevelDiscountAmount, 2);
							return memberLevelDiscountAmount;
						}
					}
				}
			}
		}
		return 0.0;
	}

	/**
	 * 计算订单总金额，包含当天封顶金额判断
	 * 
	 * @param order
	 * @param rule
	 * @return
	 * @throws ParseException
	 */
	private Order caculateOrderAmount(Order order, PricingRule rule) throws ParseException {
		Double dayFD = 0d;
		Double orderAmount = 0d;
		// 里程金额
		Double mileageAmount = 0d;
		// 时长金额
		Double minutesAmount = 0d;
		OrderMileage omQuery = new OrderMileage();
		omQuery.setOrderNo(order.getOrderNo());
		List<OrderMileage> orderMileageList = orderMileageService.getOrderMileageList(new Query(omQuery));
		int i = 0;
		for (OrderMileage om : orderMileageList) {
			if (om != null) {
				int orderCaculateType = 0;
				try {
					int ruleType = rule.getRuleType();
					if (ruleType == 1 || ruleType == 2) {// 目前只有两种规则
						orderCaculateType = ruleType;
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				if (orderCaculateType == 0) {
					SysParam sysparam1 = sysParamService.getByParamKey("ORDER_CACULATE_TYPE");
					if (sysparam1 != null && sysparam1.getParamValue() != null) {
						orderCaculateType = Integer.parseInt(sysparam1.getParamValue().trim());
					}
				}
				Double orderMileageDayAmount = om.getOrderAmountOfDay();
				if (orderCaculateType == 1) {
					// 确定orderMileage.getOrderMileageDate()是周几
					int dayOfWeek = ECDateUtils.getDayOfWeek(om.getOrderMileageDate());
					// 查询自定义日期
					PricingRuleCustomizedDate pricingRuleCustomizedDate = pricingRuleCustomizedDateService
							.getPricingRuleCustomizedDate(rule.getRuleNo(), om.getOrderMileageDate());
					if (pricingRuleCustomizedDate != null) {// 自定义日期封顶金额
						dayFD = pricingRuleCustomizedDate.getBillingCapPerDayCustomized();
					} else if (dayOfWeek == Calendar.SATURDAY || dayOfWeek == Calendar.SUNDAY) {// 周末封顶金额
						if (rule.getBillingCapPerDayOrdinary() != null) {
							dayFD = rule.getBillingCapPerDayOrdinary();
						} else {
							dayFD = rule.getBillingCapPerDay();
						}
					} else {// 工作日封顶金额
						dayFD = rule.getBillingCapPerDay();
					}
				} else if (orderCaculateType == 2) {
					dayFD = rule.getBillingCapPerDay();
					// 计算普通时段分钟单价--元/分钟
					Double priceOfMinute = 0d;
					if (om.getMinutes() > 0.0) {
						priceOfMinute = ECCalculateUtils.div(om.getMinutesAmount(), om.getMinutes());
					}
					// 订单是否前多少分钟免时长费
					SysParam param = sysParamService.getByParamKey("IS_ORDER_FREEMINUTES");
					if (param != null && param.getParamValue() != null) {
						int isOrderFreeMinutes = Integer.parseInt(param.getParamValue().trim());
						// 如果是前FREEMINUTES分钟时长免费，则前FREEMINUTES分钟的时长金额不变，超过免费时长开始计费
						if (isOrderFreeMinutes == 1) {
							param = sysParamService.getByParamKey("FREEMINUTES");
							if (param != null && param.getParamValue() != null) {
								int freeMinutes = Integer.parseInt(param.getParamValue().trim());
								if (i == 0) {
									if (order.getOrderDuration() >= freeMinutes) {
										if (om.getMinutes() > 0) {
											// 时长费减免freeMinutes分钟
											om.setMinutesAmount(
													ECCalculateUtils.mul(om.getMinutes() - freeMinutes, priceOfMinute));
											om.setMinutesAmount(ECCalculateUtils.round(om.getMinutesAmount(), 2));// 取两位小数
											// 重新计算一天费用
											orderMileageDayAmount = ECCalculateUtils.add(om.getMinutesAmount(),
													om.getMileageAmount());
										}
									} else {
										minutesAmount = 0d;
										om.setMinutesAmount(0d);
										// 重新计算一天费用
										orderMileageDayAmount = om.getMileageAmount();
									}
								}
							}
						}
					}
					// 计算普通时段里程单价--元/分钟
					Double priceOfMileage = 0.0;
					if (om.getMileage() > 0.0) {
						priceOfMileage = ECCalculateUtils.div(om.getMileageAmount(), om.getMileage());
					}
					// 订单是否前多少分钟免里程费
					SysParam param1 = sysParamService.getByParamKey("IS_ORDER_FREEMILEAGE");
					// 如果是前FREEMINUTES分钟里程免费，则前FREEMINUTES分钟的里程金额不变，超过免费时长开始计费
					if (param1 != null && param1.getParamValue() != null) {
						int isOrderFreeMileage = Integer.parseInt(param1.getParamValue().trim());
						if (isOrderFreeMileage == 1) {
							param = sysParamService.getByParamKey("FREEMINUTES");
							if (param != null && param.getParamValue() != null) {
								int freeMinutes = Integer.parseInt(param.getParamValue().trim());
								if (i == 0) {
									if (order.getOrderDuration() >= freeMinutes) {
										if (om.getMileage() > 0) {
											if (om.getFreeMileage() == null) {
												om.setFreeMileage(0.0);
											}
											// freeMinutes分钟的里程费
											Double freeMileageAmount = ECCalculateUtils.mul(om.getFreeMileage(),
													priceOfMileage);
											// 里程费减免freeMinutes分钟的里程费
											om.setMileageAmount(
													ECCalculateUtils.sub(om.getMileageAmount(), freeMileageAmount));
											om.setMileageAmount(ECCalculateUtils.round(om.getMileageAmount(), 2));// 取两位小数
											// 重新计算一天费用
											orderMileageDayAmount = ECCalculateUtils.add(om.getMinutesAmount(),
													om.getMileageAmount());
										}
									} else {
										minutesAmount = 0d;
										om.setMileageAmount(0d);
										// 重新计算一天费用
										orderMileageDayAmount = om.getMinutesAmount();
									}
								}
							}
						}
					}
					if (om.getPearTimeCost() != null) {
						int pearkTimeTotalMinutes = 0;
						Double pearkTimeTotalMinutesAmount = 0d;
						Double peakTimeTotalMileage = 0d;
						Double peakTimeTotalMileageAmount = 0d;
						List<PeakHoursCost> peakHoursCostList = JsonUtils.parse2ListObject(om.getPearTimeCost(),
								PeakHoursCost.class);
						;
						if (peakHoursCostList != null && peakHoursCostList.size() > 0) {
							for (PeakHoursCost peakHoursCost : peakHoursCostList) {
								pearkTimeTotalMinutes = pearkTimeTotalMinutes + peakHoursCost.getTimePeriodMinutes();
								pearkTimeTotalMinutesAmount = ECCalculateUtils
										.add(peakHoursCost.getTimePeriodMinutesAmount(), pearkTimeTotalMinutesAmount);
								peakTimeTotalMileage = ECCalculateUtils.add(peakTimeTotalMileage,
										peakHoursCost.getTimePeriodMileage());
								peakTimeTotalMileageAmount = ECCalculateUtils.add(peakTimeTotalMileageAmount,
										peakHoursCost.getTimePeriodMileageAmount());
							}
							int ordinaryTimeTotalMinutes = om.getMinutes() - pearkTimeTotalMinutes;
							Double ordinaryTimeTotalMinutesAmount = ECCalculateUtils.mul(priceOfMinute,
									ordinaryTimeTotalMinutes);
							Double ordinaryTimeTotalMileage = 0d;
							Double ordinaryTimeTotalMileageAmount = 0d;
							if (om.getMileageAmount() > 0.0) {
								ordinaryTimeTotalMileage = ECCalculateUtils.sub(om.getMileage(), peakTimeTotalMileage);
								ordinaryTimeTotalMileageAmount = ECCalculateUtils.mul(priceOfMileage,
										ordinaryTimeTotalMileage);
							}
							om.setMinutesAmount(
									ECCalculateUtils.add(ordinaryTimeTotalMinutesAmount, pearkTimeTotalMinutesAmount));
							om.setMileageAmount(
									ECCalculateUtils.add(ordinaryTimeTotalMileageAmount, peakTimeTotalMileageAmount));
							orderMileageDayAmount = ECCalculateUtils.add(om.getMinutesAmount(), om.getMileageAmount());
						}
					}
				}
				if (orderMileageDayAmount != null) {
					orderMileageDayAmount = ECCalculateUtils.add(om.getMileageAmount(), om.getMinutesAmount());
					SysParam sysp = sysParamService.getByParamKey("IS_SUM_FREEMILEAGE");
					if (sysp != null && sysp.getParamValue() != null
							&& Integer.parseInt(sysp.getParamValue().trim()) == 1) {
						// orderMileageDayAmount
						// =ECCalculateUtils.sub(orderMileageDayAmount,mileageAmount);
						// 如果时长费用超过封顶金额 则总金额=封顶金额+里程费用
						if (om.getMinutesAmount() > dayFD) {
							Double orderAmountm = om.getMileageAmount();
							orderAmount += ECCalculateUtils.add(orderAmountm, dayFD);
						} else {// 如果时长费用没有超过封顶金额 则总金额=时长费用+里程费用
							orderAmount = ECCalculateUtils.add(orderAmount, orderMileageDayAmount);
						}
					} else {
						if (orderMileageDayAmount > dayFD) {
							orderAmount = ECCalculateUtils.add(orderAmount, dayFD);
						} else {
							orderAmount = ECCalculateUtils.add(orderAmount, orderMileageDayAmount);
						}
					}

				}
				minutesAmount = ECCalculateUtils.add(minutesAmount, om.getMinutesAmount());
				mileageAmount = ECCalculateUtils.add(mileageAmount, om.getMileageAmount());

			}
			i++;
		}
		// 里程金额
		order.setMileageAmount(mileageAmount);
		// 时长金额
		order.setMinutesAmount(minutesAmount);
		// 订单总金额
		order.setOrderAmount(orderAmount);

		if (rule.getDiscount() != null) {
			order.setDiscountAmount(ECCalculateUtils.mul(orderAmount, 1 - rule.getDiscount()));
		}
		return order;
	}

	/**
	 * 计算优惠券抵扣的最大金额的优惠券
	 * 
	 * @param order
	 * @return
	 */
	private Double caculateCouponDikouAmount(Order order) {
		Coupon coupon = new Coupon();
		coupon.setMemberNo(order.getMemberNo());
		coupon.setUsedStatus(0);// 未使用
		coupon.setIsAvailable(1);// 可用
		coupon.setAvailableTime2Start(order.getFinishTime());// 结算时只需判断优惠券有效期截止时间是否大于订单完成时间即可
		List<Coupon> couponList = couponService.getCouponList(new Query(coupon));

		Map<Double, String> map = new HashMap<Double, String>();
		for (Coupon cp : couponList) {
			String planNo = cp.getPlanNo();
			if (planNo != null && !"".equals(planNo)) {
				CouponPlan couponPlan = couponPlanService.getCouponPlan(planNo);
				if (couponPlan != null) {
					// 首先根据车型和城市判断优惠券是否可用
					String cityId = order.getCityId();
					String carModelId = order.getCarModelId();
					if (couponPlan.getCarModelId().contains(carModelId) && couponPlan.getCityId().contains(cityId)) {
						// 优惠方式(1.打折，2.直减)
						Integer couponMethod = couponPlan.getCouponMethod();
						// 优惠方式：打折 打折有封顶
						if (couponMethod == 1) {
							// 打折金额
							Double discountAmount = ECCalculateUtils.mul(order.getOrderAmount(),
									ECCalculateUtils.sub(1.0, couponPlan.getDiscount()));
							// 打折封顶金额
							Double discountMaxAmount = couponPlan.getDiscountMaxAmount();
							// 判断是否达到封顶金额
							if (discountAmount.compareTo(discountMaxAmount) > 0) {
								discountAmount = discountMaxAmount;
							}
							map.put(discountAmount, cp.getCouponNo());
						} else if (couponMethod == 2) {// 优惠方式：直减(满减)
														// 满consumeAmount减
														// discountAmount
							// 订单金额满此优惠券满减金额才可进行满减
							if (order.getOrderAmount().compareTo(couponPlan.getConsumeAmount()) >= 0) {
								// 直减金额（满减金额）
								Double discountAmount = couponPlan.getDiscountAmount();
								map.put(discountAmount, cp.getCouponNo());
							}
						}

					}
				}
			}
		}
		Double couponMaxDiscountAmount = 0d;// 优惠券抵扣的最大金额
		String couponNo = "";// 优惠券抵扣的最大金额对应的优惠券编号
		if (map.size() > 0) {
			Map<Double, String> resultMap = bubbleSort(map);
			for (Double discountAmount : resultMap.keySet()) {
				couponNo = resultMap.get(discountAmount);
				couponMaxDiscountAmount = discountAmount;
			}
		}

		// 使用优惠券进行订单抵扣
		if (!"".equals(couponNo)) {
			// 修改此优惠券状态
			Coupon cp = new Coupon();
			cp.setCouponNo(couponNo);
			cp.setUsedStatus(1);
			cp.setUsedTime(ECDateUtils.getCurrentDateTime());
			cp.setBizObjNo(order.getOrderNo());
			cp.setBizObjType(1);
			couponService.updateCoupon(cp, null);
		}

		return couponMaxDiscountAmount;
	}

	private Map<Double, String> bubbleSort(Map<Double, String> map) {
		// doubel值最大的键值对存入此map返回
		Map<Double, String> resultMap = new HashMap<Double, String>();
		double a[] = new double[map.size()];
		int k = 0;
		for (double d : map.keySet()) {
			a[k] = d;
			k++;
		}
		// double
		// a[]={49.60,38.25,65,97,76,13,27,49,78,34,12,64,5,4,62,99.6,98,54,56,17,18,23,34,15,35,25,53,51};
		double temp = 0;
		for (int i = 0; i < a.length - 1; i++) {
			for (int j = 0; j < a.length - 1 - i; j++) {
				if (a[j] > a[j + 1]) {
					temp = a[j];
					a[j] = a[j + 1];
					a[j + 1] = temp;
				}
			}
		}
		// for(int i=0;i<a.length;i++)
		// System.out.println(a[i]);
		Double maxDoule = a[a.length - 1];
		resultMap.put(maxDoule, map.get(maxDoule));
		return resultMap;
	}

	public int getNowDayAvailableDeduction(Order order, PricingPackOrder po, PricingPackage pp, Date time) {
		// 套餐的每天抵扣上限
		int dayDeduction = pp.getDeductionCeiling();
		// 该套餐当天可用的抵扣时长
		int nowDayAvailableDeduction = 0;
		// 当天该套餐已抵扣时长
		int nowDayDeduction = 0;
		PricingDeductionRecord pdrSearch = new PricingDeductionRecord();
		pdrSearch.setMemberNo(order.getMemberNo());
		pdrSearch.setPackOrderNo(po.getPackOrderNo());
		pdrSearch.setPackageId(pp.getPackageNo());
		try {
			pdrSearch.setDeductionTimeStart(ECDateUtils.getCurrentDateStartTime(time));
			pdrSearch.setDeductionTimeEnd(ECDateUtils.getCurrentDateEndTime(time));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List<PricingDeductionRecord> recordList = pricingDeductionRecordService
				.getPricingDeductionRecordList(new Query(pdrSearch));
		if (recordList != null && recordList.size() > 0) {
			for (PricingDeductionRecord pdr : recordList) {
				nowDayDeduction = nowDayDeduction + pdr.getDeductionCeiling();
			}
		}
		nowDayAvailableDeduction = dayDeduction - nowDayDeduction;
		if (nowDayAvailableDeduction < 0) {
			nowDayAvailableDeduction = 0;
		}
		return nowDayAvailableDeduction;
	}

	// 在套餐抵扣记录表中添加一条记录
	public ResultInfo<PricingDeductionRecord> addPricingDeductionRecord(Order order, PricingPackOrder po,
			PricingPackage pp, int minutes, Date date) {
		PricingDeductionRecord pDR = new PricingDeductionRecord();
		pDR.setMemberNo(order.getMemberNo());
		pDR.setOrderNo(order.getOrderNo());
		pDR.setPackOrderNo(po.getPackOrderNo());
		if (pp != null) {
			pDR.setPackageId(pp.getPackageNo());
		}
		pDR.setDeductionCeiling(minutes);
		pDR.setDeductionTime(date);
		if (minutes > 0) {
			return pricingDeductionRecordService.addPricingDeductionRecord(pDR, null);
		} else {
			return null;
		}

	}

	// 在套餐抵扣记录表中添加一条抵扣金额记录
	public ResultInfo<PricingDeductionRecord> addPricingDeductionRecord(Order order, PricingPackOrder po,
			PricingPackage pp, Double deductionAmount, Date date) {
		PricingDeductionRecord pDR = new PricingDeductionRecord();
		pDR.setMemberNo(order.getMemberNo());
		pDR.setOrderNo(order.getOrderNo());
		pDR.setPackOrderNo(po.getPackOrderNo());
		if (pp != null) {
			pDR.setPackageId(pp.getPackageNo());
		}
		pDR.setDeductionAmount(deductionAmount);
		;
		pDR.setDeductionTime(date);
		if (deductionAmount.compareTo(0d) > 0) {
			return pricingDeductionRecordService.addPricingDeductionRecord(pDR, null);
		} else {
			return null;
		}

	}

	public Order diKouByPricingPackOrderAmout(Order order, Double diKouAmount) {
		Query q = new Query();
		PricingPackOrder ppo = new PricingPackOrder();
		ppo.setPayStatus(1);// 已经支付的
		ppo.setIsAvailable(1);// 可用
		ppo.setNowTime(order.getFinishTime());
		ppo.setMemberNo(order.getMemberNo());
		q.setQ(ppo);
		List<PricingPackOrder> list = pricingPackOrderService.getPricingPackOrderAmountListByDiKou(q);
		// 需抵扣总金额
		Double daiDiKou = diKouAmount;
		// 可使用金额套餐总金额
		Double availableAmout = 0.0;
		// 抵扣总额
		Double dikouTotalAmount = 0d;
		int size = list.size();
		if (size > 0) {
			for (int i = 0; i < size; i++) {
				// 获取套餐可用时长
				PricingPackOrder po = list.get(i);
				if (po.getUseredOrderAmount() == null) {
					po.setUseredOrderAmount(0d);
				}
				// 单个套餐剩余可用套餐余额
				Double useredOrderAmount = ECCalculateUtils.sub(po.getPackOrderAmount(), po.getUseredOrderAmount());
				// 总可用套餐余额
				availableAmout = ECCalculateUtils.add(availableAmout, useredOrderAmount);

				if (useredOrderAmount >= daiDiKou) {
					useredOrderAmount = daiDiKou;
				}
				if (useredOrderAmount >= 0) {
					// 修改该套餐的已用金额
					po.setUseredOrderAmount(ECCalculateUtils.add(po.getUseredOrderAmount(), useredOrderAmount));
					pricingPackOrderService.updatePricingPackOrder(po, null);
					PricingPackage pp = pricingPackageService.getPricingPackage(po.getPackageId());
					addPricingDeductionRecord(order, po, pp, useredOrderAmount, order.getFinishTime());
					daiDiKou = ECCalculateUtils.sub(daiDiKou, useredOrderAmount);
					dikouTotalAmount = ECCalculateUtils.add(dikouTotalAmount, useredOrderAmount);
					if (daiDiKou.compareTo(0d) == 0) {
						break;
					}
				} else {
					// 抵扣的金额套餐金额
					order.setPackAmountDiscountAmount(0d);
				}
			}
			dikouTotalAmount = ECCalculateUtils.round(dikouTotalAmount, 2);
			availableAmout = ECCalculateUtils.round(availableAmout, 2);
			// 判断是否有抵扣
			if (dikouTotalAmount > 0) {
				// 抵扣的金额套餐金额
				order.setPackAmountDiscountAmount(dikouTotalAmount);
				if (order.getPackAmountDiscountAmount() > 0) {
					Accounts ac = new Accounts();
					ac.setMemberNo(order.getMemberNo());
					ac.setBusinessNo(order.getOrderNo());
					ac.setBusinessType(1);
					ac.setAccountType(1);
					ac.setAccountMoney(order.getPackAmountDiscountAmount());
					ac.setAccountBeforeMoney(availableAmout);
					ac.setAccountOverMoney(ECCalculateUtils.sub(availableAmout, ac.getAccountMoney()));
					ac.setAccountTime(new Date());
					accountsService.addAccounts(ac, null);
				}
			}

		} else {
			order.setPackAmountDiscountAmount(0d);
		}
		return order;
	}

	/**
	 * 金豆抵扣订单金额
	 * 
	 * @param order
	 * @param diKouAmount
	 *            待抵扣总金额
	 * @return
	 */
	public Order diKouOrderAmoutByGlodBeans(Order order, Double diKouAmount) {
		// 会员账户金豆
		MemberGlodBeans memberGoldBeans = memberGlodBeansService.getMemberGlodBeans(order.getMemberNo());
		// 会员金豆总数
		Integer glodBeansBeforeDikou = 0;
		if (memberGoldBeans != null) {
			glodBeansBeforeDikou = memberGoldBeans.getGoldBeansNum();
		} else {
			glodBeansBeforeDikou = 0;
		}

		if (glodBeansBeforeDikou != null && glodBeansBeforeDikou.intValue() > 0) {
			// 金豆配置
			List<GoldBeansSetting> glodBeansSettingList = goldBeansSettingService.getGoldBeansSettingList(new Query());
			if (glodBeansSettingList.size() > 0) {
				GoldBeansSetting glodBeansSetting = glodBeansSettingList.get(0);
				if (glodBeansSetting != null) {
					// 实际抵扣的金额值
					Double actualDikouAmount = 0d;
					// 抵扣前会员金豆对应总金额
					Double memberBeansAmount = ECCalculateUtils.mul(glodBeansBeforeDikou,
							glodBeansSetting.getBeansMoneyRatio().doubleValue());
					// diKouAmount（待抵扣总金额）*orderBeansRatio（订单结算金豆抵扣比率值）
					// 根据订单金额和订单结算金豆抵扣比率值计算出能抵扣的金额值
					Double keDiKouAmount = ECCalculateUtils.mul(diKouAmount,
							glodBeansSetting.getOrderBeansRatio().doubleValue());
					// 可抵扣最大值
					Double dikouMaxAmount = glodBeansSetting.getDedutionMaxAmount();
					if (keDiKouAmount.compareTo(dikouMaxAmount) > 0) {
						if (memberBeansAmount.compareTo(dikouMaxAmount) > 0) {
							actualDikouAmount = dikouMaxAmount;
						} else {
							actualDikouAmount = memberBeansAmount;
						}
					} else {
						if (memberBeansAmount.compareTo(keDiKouAmount) > 0) {
							actualDikouAmount = keDiKouAmount;
						} else {
							actualDikouAmount = memberBeansAmount;
						}
					}
					actualDikouAmount = ECCalculateUtils.round(actualDikouAmount, 2);
					// 金豆抵扣金额
					order.setGlodBeansDedutionAmount(actualDikouAmount);
					// 抵扣后会员金豆对应总金额
					Double memberBeansAmountAfterDikou = ECCalculateUtils.sub(memberBeansAmount, actualDikouAmount);
					// 抵扣后的金豆数
					Double glodBeansNumAfterDikouDouble = ECCalculateUtils.div(memberBeansAmountAfterDikou,
							glodBeansSetting.getBeansMoneyRatio().doubleValue());
					Integer goldBeansNumAfterDikou = glodBeansNumAfterDikouDouble.intValue();
					// 更新会员金豆
					MemberGlodBeans memberGoldBeansForUpdate = new MemberGlodBeans();
					memberGoldBeansForUpdate.setMemberNo(memberGoldBeans.getMemberNo());
					memberGoldBeansForUpdate.setGoldBeansNum(goldBeansNumAfterDikou);
					memberGlodBeansService.updateMemberGlodBeans(memberGoldBeansForUpdate);
					// 记录抵扣明细
					GoldBeansConsumerDetails detail = new GoldBeansConsumerDetails();
					detail.setMenberNo(order.getMemberNo());
					detail.setMemberName(order.getMemberName());
					detail.setMemberPhone(order.getMobilePhone());
					detail.setOrderAmount(order.getOrderAmount());
					detail.setOrderNo(order.getOrderNo());
					detail.setOrderTime(order.getFinishTime());
					detail.setConsumerBeansNum(glodBeansBeforeDikou - goldBeansNumAfterDikou);
					detail.setConsumerBeansAmount(actualDikouAmount);
					detail.setConsumerTime(ECDateUtils.getCurrentDateTime());
					goldBeansConsumerDetailsService.addGoldBeansConsumerDetails(detail);
				}
			}
		} else {
			// 金豆抵扣金额
			order.setGlodBeansDedutionAmount(0.0);
		}
		return order;
	}

	/**
	 * 还车后金额套餐抵扣
	 * 
	 * @param order
	 * @param diKouAmount
	 *            待抵扣金额
	 * @return
	 * @throws ParseException
	 */
	@Override
	public Order diKouPricingPackOrderAmout(Order order, Double diKouAmount) throws ParseException {
		Query q = new Query();
		PricingPackOrder ppo = new PricingPackOrder();
		ppo.setPayStatus(1);// 已经支付的
		ppo.setIsAvailable(1);// 可用
		ppo.setNowTime(ECDateUtils.getCurrentDateTime());// 使用当前时间
		ppo.setMemberNo(order.getMemberNo());
		q.setQ(ppo);
		List<PricingPackOrder> list = pricingPackOrderService.getPricingPackOrderAmountListByDiKou(q);
		// 需抵扣总金额
		Double daiDiKou = diKouAmount;
		// 可使用金额套餐总金额
		Double availableAmout = 0.0;
		// 抵扣总额
		Double dikouTotalAmount = 0d;
		int size = list.size();
		if (size > 0) {
			for (int i = 0; i < size; i++) {
				// 获取套餐可用时长
				PricingPackOrder po = list.get(i);
				if (po.getUseredOrderAmount() == null) {
					po.setUseredOrderAmount(0d);
				}
				// 单个套餐剩余可用套餐余额
				Double useredOrderAmount = ECCalculateUtils.sub(po.getPackOrderAmount(), po.getUseredOrderAmount());
				// 总可用套餐余额
				availableAmout = ECCalculateUtils.add(availableAmout, useredOrderAmount);

				if (useredOrderAmount >= daiDiKou) {
					useredOrderAmount = daiDiKou;
				}
				if (useredOrderAmount >= 0) {
					// 修改该套餐的已用金额
					po.setUseredOrderAmount(ECCalculateUtils.add(po.getUseredOrderAmount(), useredOrderAmount));
					pricingPackOrderService.updatePricingPackOrder(po, null);
					PricingPackage pp = pricingPackageService.getPricingPackage(po.getPackageId());
					addPricingDeductionRecord(order, po, pp, useredOrderAmount, order.getFinishTime());
					daiDiKou = ECCalculateUtils.sub(daiDiKou, useredOrderAmount);
					dikouTotalAmount = ECCalculateUtils.add(dikouTotalAmount, useredOrderAmount);
					if (daiDiKou.compareTo(0d) == 0) {
						break;
					}
				} else {
					// 抵扣的金额套餐金额
					order.setPackAmountDiscountAmount(0d);
				}
			}
			dikouTotalAmount = ECCalculateUtils.round(dikouTotalAmount, 2);
			availableAmout = ECCalculateUtils.round(availableAmout, 2);
			// 判断是否有抵扣
			if (dikouTotalAmount > 0) {
				// 已有订单抵扣金额+当前总抵扣金额
				order.setPackAmountDiscountAmount(
						ECCalculateUtils.add(dikouTotalAmount, order.getPackAmountDiscountAmount()));
				// 应支付金额=已有应支付金额-现在总抵扣金额
				order.setPayableAmount(ECCalculateUtils.sub(order.getPayableAmount(), dikouTotalAmount));
				if (order.getPackAmountDiscountAmount() > 0) {
					Accounts ac = new Accounts();
					ac.setMemberNo(order.getMemberNo());
					ac.setBusinessNo(order.getOrderNo());
					ac.setBusinessType(1);
					ac.setAccountType(1);
					ac.setAccountMoney(dikouTotalAmount);
					ac.setAccountBeforeMoney(availableAmout);
					ac.setAccountOverMoney(ECCalculateUtils.sub(availableAmout, ac.getAccountMoney()));
					ac.setAccountTime(new Date());
					accountsService.addAccounts(ac, null);
				}
			}

		} else {
			order.setPackAmountDiscountAmount(0d);
		}
		return order;
	}

	public Map<String, Object> dikou(int minutesD1, int totalMinutesDay, Order order, PricingRule rule, int minutes,
			Date date1, Date date2) {
		if (minutesD1 <= totalMinutesDay) {
			Query q = new Query();
			PricingPackOrder ppo = new PricingPackOrder();
			ppo.setPayStatus(1);// 已经支付的
			ppo.setIsAvailable(1);// 可用
			// date1:订单开始，date2:订单结束
			// 可用套餐的判断条件：有效期的开始时间小于订单结束时间，有效期的结束时间大于订单的开始时间
			// （VailableTime1<date2）&&（VailableTime2>date1）
			ppo.setStartTime(date1);
			ppo.setEndTime(date2);
			ppo.setMemberNo(order.getMemberNo());
			if (order.getPackMinutesDiscount() == null) {
				order.setPackMinutesDiscount(0);
			}
			if (order.getPackMinutesDiscountAmount() == null) {
				order.setPackMinutesDiscountAmount(0d);
			}
			q.setQ(ppo);
			List<PricingPackOrder> list = pricingPackOrderService.getPricingPackOrderListByDiKou(q);
			for (int i = 0; i < list.size(); i++) {
				// 获取套餐可用时长
				PricingPackOrder po = list.get(i);
				if (po.getUserdMinute() == null) {
					po.setUserdMinute(0);
				}
				// 套餐可抵扣，订单可抵扣取小的（useMinute（useMinute，pricingDiKou取小），minutesD1（minutesD1，minutes取小））
				int useMinute = po.getPackMinute() - po.getUserdMinute();
				int pricingDiKou = 0;
				if (po.getVailableTime1().getTime() / 1000 <= date1.getTime() / 1000
						&& po.getVailableTime2().getTime() / 1000 > date1.getTime() / 1000
						&& po.getVailableTime2().getTime() / 1000 <= date2.getTime() / 1000) {
					// VailableTime1<=date1<VailableTime2<=date2
					// VailableTime2-date1
					pricingDiKou = ECDateUtils.differMinutes(date1, po.getVailableTime2()).intValue();
				} else if (po.getVailableTime1().getTime() / 1000 <= date1.getTime() / 1000
						&& po.getVailableTime2().getTime() / 1000 > date2.getTime() / 1000) {
					// VailableTime1<=date1<date2<VailableTime2 date2-date1
					pricingDiKou = ECDateUtils.differMinutes(date1, date2).intValue();
				} else if (po.getVailableTime1().getTime() / 1000 > date1.getTime() / 1000
						&& po.getVailableTime1().getTime() / 1000 < date2.getTime() / 1000
						&& po.getVailableTime2().getTime() / 1000 >= date2.getTime() / 1000) {
					// date1<VailableTime1<date2<=VailableTime2
					// date2-VailableTime1
					pricingDiKou = ECDateUtils.differMinutes(po.getVailableTime1(), date2).intValue();
				} else if (po.getVailableTime1().getTime() / 1000 > date1.getTime() / 1000
						&& po.getVailableTime2().getTime() / 1000 < date2.getTime() / 1000) {
					// date1<VailableTime1<VailableTime2<date2
					// VailableTime2-VailableTime1
					pricingDiKou = ECDateUtils.differMinutes(po.getVailableTime1(), po.getVailableTime2()).intValue();
				}
				if (useMinute >= pricingDiKou) {
					useMinute = pricingDiKou;
				}
				// 判断该套餐可用的抵扣时长=每天抵扣上限-当天已抵扣时长
				PricingPackage pp = pricingPackageService.getPricingPackage(po.getPackageId());
				// 该套餐当天可用的抵扣时长
				int nowDayAvailableDeduction = 0;
				if (pp != null) {
					if (pp.getDeductionCeiling() != null) {
						nowDayAvailableDeduction = getNowDayAvailableDeduction(order, po, pp, date1);
					}
				}
				if (minutesD1 >= minutes) {
					minutesD1 = minutes;
				}
				if (useMinute >= nowDayAvailableDeduction) {
					useMinute = nowDayAvailableDeduction;
				}
				if (useMinute >= minutesD1) {
					useMinute = minutesD1;
				}
				// 设置订单的套餐抵扣金额和时长
				order.setPackMinutesDiscount(order.getPackMinutesDiscount() + useMinute);
				if (rule.getDiscount() != null) {
					order.setPackMinutesDiscountAmount(
							ECNumberUtils.roundDoubleWithScale(order.getPackMinutesDiscountAmount()
									+ useMinute * rule.getPriceOfMinute() * rule.getDiscount(), 2));
				} else {
					order.setPackMinutesDiscountAmount(ECNumberUtils.roundDoubleWithScale(
							order.getPackMinutesDiscountAmount() + useMinute * rule.getPriceOfMinute(), 2));
				}
				// 修改该套餐的已用时长
				po.setUserdMinute(po.getUserdMinute() + useMinute);
				pricingPackOrderService.updatePricingPackOrder(po, null);
				// 并在套餐抵扣记录表中添加一条记录
				addPricingDeductionRecord(order, po, pp, useMinute, date1);
				minutes = minutes - useMinute;
				if (minutes == 0) {
					break;
				}
			}
		}
		Map<String, Object> maps = new HashMap<String, Object>();
		maps.put("order", order);
		maps.put("minutes", minutes);
		return maps;
	}

	// 订单计费时，查看订单详情时获取计费数据
	private void beforeOrderFinish(Order order, CarStatus carStatus) throws Exception {
		PricingRule rule = pricingRuleService.getPricingRule(order.getRuleNo());
		if (carStatus.getMileage() == null) {
			carStatus.setMileage(0d);
		}
		OrderMileage orderMileage = orderMileageService.getNewestOrderMileage(order.getOrderNo());
		if (orderMileage != null) {
			// 上一天开始时间
			Date lastDayOrderStartMinute = orderMileage.getOrderStartMinute();
			// 上一天结束时间
			Date lastDayOrderEndMinute = ECDateUtils.getDateAfter(lastDayOrderStartMinute, 1);
			// 更新当天数据
			if (order.getFinishTime().compareTo(lastDayOrderEndMinute) >= 0) {
				updateNowDayOrderMileage(carStatus, order, orderMileage, rule, lastDayOrderEndMinute);
			} else {
				updateNowDayOrderMileage(carStatus, order, orderMileage, rule, order.getFinishTime());
			}
		}

	}

	// 更新指定日期的数据
	private void updateNowDayOrderMileage(CarStatus carStatus, Order o, OrderMileage orderMileage, PricingRule rule,
			Date orderEndMinute) throws Exception {
		OrderMileage om = new OrderMileage();
		om.setOrderNo(o.getOrderNo());
		om.setOrderMileageDate(orderMileage.getOrderMileageDate());

		om.setOrderEndMinute(orderEndMinute);
		om.setMinutes(ECDateUtils.differMinutes(om.getOrderEndMinute(), orderMileage.getOrderStartMinute()).intValue());

		om.setOrderFinishMileage(carStatus.getMileage());
		om.setMileage(ECCalculateUtils.sub(om.getOrderFinishMileage(), orderMileage.getOrderStartMileage()));

		SysParam param = sysParamService.getByParamKey("IS_ORDER_FREEMILEAGE");
		String isOrderFreeMileage = "0";
		if (param != null && param.getParamValue() != null) {
			isOrderFreeMileage = param.getParamValue().trim();
		}
		if ("1".equals(isOrderFreeMileage)) {
			param = sysParamService.getByParamKey("FREEMINUTES");
			if (param != null && param.getParamValue() != null) {
				int freeMinutes = Integer.parseInt(param.getParamValue().trim());
				if (om.getMinutes() <= freeMinutes) {
					om.setFreeMileage(om.getMileage());
				}
			}
		}
		int orderCaculateType = 0;
		try {
			int ruleType = rule.getRuleType();
			if (ruleType == 1 || ruleType == 2) {// 目前只有两种规则
				orderCaculateType = ruleType;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (orderCaculateType == 0) {
			SysParam sysparam1 = sysParamService.getByParamKey("ORDER_CACULATE_TYPE");
			if (sysparam1 != null && sysparam1.getParamValue() != null) {
				orderCaculateType = Integer.parseInt(sysparam1.getParamValue().trim());
			}
		}
		if (orderCaculateType == 1) {
			// 确定orderMileage.getOrderMileageDate()是周几
			int dayOfWeek = ECDateUtils.getDayOfWeek(orderMileage.getOrderMileageDate());
			// 查询自定义日期
			PricingRuleCustomizedDate pricingRuleCustomizedDate = pricingRuleCustomizedDateService
					.getPricingRuleCustomizedDate(o.getRuleNo(), orderMileage.getOrderMileageDate());
			if (pricingRuleCustomizedDate != null) {// 自定义日期时间金额和里程金额计算
				om.setMinutesAmount(
						ECCalculateUtils.mul(om.getMinutes(), pricingRuleCustomizedDate.getPriceOfMinuteCustomized()));
				om.setMileageAmount(
						ECCalculateUtils.mul(om.getMileage(), pricingRuleCustomizedDate.getPriceOfKmCustomized()));
			} else if (dayOfWeek == Calendar.SATURDAY || dayOfWeek == Calendar.SUNDAY) {// 周末金额和里程金额计算
				// 若周末计费为空，则使用平日计费
				if (rule.getPriceOfMinuteOrdinary() != null && rule.getPriceOfKmOrdinary() != null) {
					om.setMinutesAmount(ECCalculateUtils.mul(om.getMinutes(), rule.getPriceOfMinuteOrdinary()));
					om.setMileageAmount(ECCalculateUtils.mul(om.getMileage(), rule.getPriceOfKmOrdinary()));
				} else {
					om.setMinutesAmount(ECCalculateUtils.mul(om.getMinutes(), rule.getPriceOfMinute()));
					om.setMileageAmount(ECCalculateUtils.mul(om.getMileage(), rule.getPriceOfKm()));
				}
			} else {
				om.setMinutesAmount(ECCalculateUtils.mul(om.getMinutes(), rule.getPriceOfMinute()));
				om.setMileageAmount(ECCalculateUtils.mul(om.getMileage(), rule.getPriceOfKm()));
			}
		} else {// 工作日金额和里程金额计算
			om.setMinutesAmount(ECCalculateUtils.mul(om.getMinutes(), rule.getPriceOfMinute()));
			om.setMileageAmount(ECCalculateUtils.mul(om.getMileage(), rule.getPriceOfKm()));
		}
		om.setMinutesAmount(ECCalculateUtils.round(om.getMinutesAmount(), 2));
		om.setMileageAmount(ECCalculateUtils.round(om.getMileageAmount(), 2));
		om.setOrderAmountOfDay(ECCalculateUtils.add(om.getMileageAmount(), om.getMinutesAmount()));
		om.setOrderAmountOfDay(ECCalculateUtils.round(om.getOrderAmountOfDay(), 2));
		if (orderMileage.getPearTimeCost() != null) {
			// 当前要更新的记录中的高峰时段记录
			List<PeakHoursCost> peakHoursCostList = JsonUtils.parse2ListObject(orderMileage.getPearTimeCost(),
					PeakHoursCost.class);
			List<PeakHoursCost> peakHoursAddCostList = new ArrayList<PeakHoursCost>();
			PeakHours peakHoursQuery = new PeakHours();
			peakHoursQuery.setRuleNo(rule.getRuleNo());
			List<PeakHours> peakHoursList = peakHoursService.getPeakHoursList(new Query(peakHoursQuery));
			// 当前记录的结束时间
			Date orderEndMinuteOfDay = ECDateUtils.getDateAfter(orderMileage.getOrderStartMinute(), 1);
			// 当前时间
			Date currentDateTime = ECDateUtils.getCurrentDateTime();
			for (PeakHours peakHours : peakHoursList) {
				Calendar pearHourStart = Calendar.getInstance();
				pearHourStart.setTime(currentDateTime);
				pearHourStart.set(Calendar.HOUR_OF_DAY, Integer.parseInt(peakHours.getPeakStartHours()));
				pearHourStart.set(Calendar.MINUTE, 0);
				pearHourStart.set(Calendar.SECOND, 0);
				Date pearHourStartTime = pearHourStart.getTime();

				Calendar pearHourEnd = Calendar.getInstance();
				pearHourEnd.setTime(currentDateTime);
				pearHourEnd.set(Calendar.HOUR_OF_DAY, Integer.parseInt(peakHours.getPeakEndHours()));
				pearHourEnd.set(Calendar.MINUTE, 0);
				pearHourEnd.set(Calendar.SECOND, 0);
				Date pearHourEndTime = pearHourEnd.getTime();
				if (peakHoursCostList != null && peakHoursCostList.size() > 0) {
					for (PeakHoursCost peakHoursCost : peakHoursCostList) {
						// 同一高峰时段
						if (peakHours.getPeakHours().equals(peakHoursCost.getTimePeriod())) {
							// 高峰时段结束时间小于或者等于orderEndMinute（当前记录的结束时间）
							if (pearHourEndTime.before(orderEndMinute) || pearHourEndTime.equals(orderEndMinute)) {
								peakHoursCost.setTimePeriodEndTime(pearHourEndTime);
								peakHoursCost.setTimePeriodMinutes(
										ECDateUtils.differMinutes(peakHoursCost.getTimePeriodEndTime(),
												peakHoursCost.getTimePeriodStartTime()).intValue());
								peakHoursCost.setTimePeriodMinutesAmount(ECCalculateUtils
										.mul(peakHours.getPriceOfMinute(), peakHoursCost.getTimePeriodMinutes()));
								peakHoursCost.setTimePeriodEndMileage(carStatus.getMileage());
								peakHoursCost.setTimePeriodMileage(
										ECCalculateUtils.sub(peakHoursCost.getTimePeriodEndMileage(),
												peakHoursCost.getTimePeriodStartMileage()));
								peakHoursCost.setTimePeriodMileageAmount(ECCalculateUtils.mul(peakHours.getPriceOfKm(),
										peakHoursCost.getTimePeriodMileage()));
								// 当前的结束时间小于高峰时段结束时间且当前的结束时间=orderEndMinute（当前记录的结束时间）
							} else if (orderEndMinuteOfDay.before(pearHourEndTime)
									&& orderEndMinuteOfDay.equals(orderEndMinute)) {
								peakHoursCost.setTimePeriodEndTime(orderEndMinuteOfDay);
								peakHoursCost.setTimePeriodMinutes(
										ECDateUtils.differMinutes(peakHoursCost.getTimePeriodEndTime(),
												peakHoursCost.getTimePeriodStartTime()).intValue());
								peakHoursCost.setTimePeriodMinutesAmount(ECCalculateUtils
										.mul(peakHours.getPriceOfMinute(), peakHoursCost.getTimePeriodMinutes()));
								peakHoursCost.setTimePeriodEndMileage(carStatus.getMileage());
								peakHoursCost.setTimePeriodMileage(
										ECCalculateUtils.sub(peakHoursCost.getTimePeriodEndMileage(),
												peakHoursCost.getTimePeriodStartMileage()));
								peakHoursCost.setTimePeriodMileageAmount(ECCalculateUtils.mul(peakHours.getPriceOfKm(),
										peakHoursCost.getTimePeriodMileage()));

								PeakHoursCost timePeriodNextDayCost = new PeakHoursCost();
								timePeriodNextDayCost.setTimePeriod(peakHours.getPeakHours());
								timePeriodNextDayCost.setTimePeriodStartTime(orderEndMinuteOfDay);
								timePeriodNextDayCost.setTimePeriodStartMileage(carStatus.getMileage());
								peakHoursAddCostList.add(timePeriodNextDayCost);
								om.setPearTimeNextDayCost(JsonUtils.toJsonString(timePeriodNextDayCost));
								//
							} else if (pearHourStartTime.before(orderEndMinute)
									&& orderEndMinute.before(pearHourEndTime)) {
								peakHoursCost.setTimePeriodEndTime(orderEndMinute);
								peakHoursCost.setTimePeriodMinutes(
										ECDateUtils.differMinutes(peakHoursCost.getTimePeriodEndTime(),
												peakHoursCost.getTimePeriodStartTime()).intValue());
								peakHoursCost.setTimePeriodMinutesAmount(ECCalculateUtils
										.mul(peakHours.getPriceOfMinute(), peakHoursCost.getTimePeriodMinutes()));
								peakHoursCost.setTimePeriodEndMileage(carStatus.getMileage());
								peakHoursCost.setTimePeriodMileage(
										ECCalculateUtils.sub(peakHoursCost.getTimePeriodEndMileage(),
												peakHoursCost.getTimePeriodStartMileage()));
								peakHoursCost.setTimePeriodMileageAmount(ECCalculateUtils.mul(peakHours.getPriceOfKm(),
										peakHoursCost.getTimePeriodMileage()));
							}
						} else {
							// 高峰时段开始时间小于当前时间且订单开始时间小于高峰时间开始时间
							if (pearHourStartTime.before(currentDateTime)
									&& orderMileage.getOrderStartMinute().before(pearHourStartTime)
									&& currentDateTime.before(pearHourEndTime)) {
								if (!orderMileage.getPearTimeCost()
										.contains("\"timePeriod\":\"" + peakHours.getPeakHours() + "\"")) {
									PeakHoursCost peakHoursCostForAdd = new PeakHoursCost();
									peakHoursCostForAdd.setTimePeriod(peakHours.getPeakHours());
									peakHoursCostForAdd.setTimePeriodStartTime(pearHourStartTime);
									peakHoursCostForAdd.setTimePeriodEndTime(pearHourStartTime);
									peakHoursCostForAdd.setTimePeriodMinutes(0);
									peakHoursCostForAdd.setTimePeriodMinutesAmount(0d);
									peakHoursCostForAdd.setTimePeriodStartMileage(carStatus.getMileage());
									peakHoursCostForAdd.setTimePeriodEndMileage(carStatus.getMileage());
									peakHoursCostForAdd.setTimePeriodMileage(0d);
									peakHoursCostForAdd.setTimePeriodMileageAmount(0d);
									peakHoursAddCostList.add(peakHoursCostForAdd);

									// 将要加入的高峰时段拼入orderMileage.getPearTimeCost()
									List<PeakHoursCost> peakHoursCostTempList = JsonUtils
											.parse2ListObject(orderMileage.getPearTimeCost(), PeakHoursCost.class);
									;
									peakHoursCostTempList.add(peakHoursCostForAdd);
									orderMileage.setPearTimeCost(JsonUtils.toJsonString(peakHoursCostTempList));
								}
							}
						}
					}
				} else {
					// 高峰时段开始时间小于当前时间且订单开始时间小于高峰时间开始时间
					if (pearHourStartTime.before(currentDateTime)
							&& orderMileage.getOrderStartMinute().before(pearHourStartTime)) {
						PeakHoursCost peakHoursCostForAdd = new PeakHoursCost();
						peakHoursCostForAdd.setTimePeriod(peakHours.getPeakHours());
						peakHoursCostForAdd.setTimePeriodStartTime(pearHourStartTime);
						peakHoursCostForAdd.setTimePeriodEndTime(pearHourStartTime);
						peakHoursCostForAdd.setTimePeriodMinutes(0);
						peakHoursCostForAdd.setTimePeriodMinutesAmount(0d);
						peakHoursCostForAdd.setTimePeriodStartMileage(carStatus.getMileage());
						peakHoursCostForAdd.setTimePeriodEndMileage(carStatus.getMileage());
						peakHoursCostForAdd.setTimePeriodMileage(0d);
						peakHoursCostForAdd.setTimePeriodMileageAmount(0d);
						peakHoursAddCostList.add(peakHoursCostForAdd);
					}
				}
			}
			for (PeakHoursCost peakHoursCost : peakHoursAddCostList) {
				peakHoursCostList.add(peakHoursCost);
			}
			om.setPearTimeCost(JsonUtils.toJsonString(peakHoursCostList));
		}
		orderMileageService.updateOrderMileage(om, null);
	}

	@Override
	public ResultInfo<Order> orderTripView(Order order) throws ParseException {
		ResultInfo<Order> resultInfo = new ResultInfo<Order>();
		Date nowDate = new Date();
		// 这里的计费时间如果为空的话，那就是在下单时间的基础上往后推迟20分钟
		Date StartBillingTime;
		if (order.getStartBillingTime() != null) {
			StartBillingTime = order.getStartBillingTime();
		} else {
			// 下单时间加20分钟
			Calendar c = Calendar.getInstance();
			c.setTime(order.getCreateTime());
			c.add(Calendar.MINUTE, 20);
			StartBillingTime = c.getTime();
		}
		int minutes = ECDateUtils.differMinutes(StartBillingTime, nowDate).intValue();
		if (order.getOrderDuration() != null && !order.getOrderDuration().equals(0)) {
			minutes = order.getOrderDuration();
		} else {
			minutes = ECDateUtils.differMinutes(StartBillingTime, nowDate).intValue();
		}
		order.setStartBillingTime(StartBillingTime);
		// 为了计算截止当前的订单费用情况，设置订单结束时间
		if (order.getFinishTime() != null) {
			minutes = ECDateUtils.differMinutes(order.getStartBillingTime(), order.getFinishTime()).intValue();
		} else {
			order.setFinishTime(nowDate);
		}
		order.setOrderDuration(minutes);
		// 订单结束时设定订单结束时里程
		CarStatus carStatus = carStatusDao.get(order.getCarNo());
		if (carStatus.getMileage() != null) {
			order.setOrderFinishMileage(carStatus.getMileage());
		} else {
			order.setOrderFinishMileage(0d);
		}
		// 订单里程设置计算
		try {
			beforeOrderFinish(order, carStatus);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 订单结算开始
		// 起步价
		Double baseFee = 0d;
		// 计费规则（1.根据车型和会员类型查找计费规则，有：使用计费规则计费；无：根据车型查找计费规则，使用规则计费）
		PricingRule rule = null;
		Car car = carService.getCar(order.getCarNo());
		if (car != null) {
			Member member = memberService.getMember(order.getMemberNo());
			if (member != null) {
				Double orderMileage = 0d;
				// 订单里程计算
				if (order.getOrderStartMileage() != null && order.getOrderFinishMileage() != null) {
					orderMileage = ECCalculateUtils.sub(order.getOrderFinishMileage(), order.getOrderStartMileage());
				}
				if (orderMileage.compareTo(0d) >= 0) {
					order.setOrderMileage(ECNumberUtils.roundDoubleWithScale(orderMileage, 2));
				}
				if (member.getMemberType().equals(2)) {// 集团会员
					String companyId = member.getCompanyId();// 集团id
					rule = pricingRuleService.getPricingRuleUseByMM(car.getCarBrandName(), order.getCarModelName(),
							companyId);
					if (rule != null) {
						caculateOrderAmount(order, rule);
					} else {// 判断该集团对该车型有没有特殊的计费规则，没有的话，采用和普通会员一样的计费规则
						rule = pricingRuleService.getPricingRuleUseByMMP(car.getCarBrandName(),
								order.getCarModelName());
						if (rule != null) {
							caculateOrderAmount(order, rule);
						} else {
							resultInfo.setCode(OrderConstant.fail_code);
							resultInfo.setMsg(OrderConstant.noRule);
							return resultInfo;
						}
					}
				} else {// 普通会员
					rule = pricingRuleService.getPricingRuleUseByMMP(car.getCarBrandName(), order.getCarModelName());
					if (rule != null) {
						caculateOrderAmount(order, rule);
					} else {
						resultInfo.setCode(OrderConstant.fail_code);
						resultInfo.setMsg(OrderConstant.noRule); // 暂无该车型的计费规则
						return resultInfo;
					}

				}

			}

			if (rule != null) {
				// 添加起步价
				if (rule.getBaseFee() != null) {
					baseFee = rule.getBaseFee();
				}
			}
			if (order.getRegardlessFranchise() == null) {
				order.setRegardlessFranchise(0d);
			}
			// 添加起步价
			order.setOrderAmount(order.getOrderAmount() + baseFee);
			// 添加不计免赔
			order.setOrderAmount(order.getOrderAmount() + order.getRegardlessFranchise());
			// 取车场站的费用
			Park takePark = parkService.getPark(order.getStartParkNo());
			Double serviceFeeGet = 0d;
			if (takePark != null && null != takePark.getServiceFeeGet()) {
				serviceFeeGet = takePark.getServiceFeeGet();
			}
			// 添加系统参数 订单时长大于分钟免收取车费
			SysParam spa = sysParamService.getByParamKey("VOID_SERVICE_FEE_GET");
			if (spa != null && "1".equals(spa.getParamValue())) {
				SysParam spav = sysParamService.getByParamKey("VOID_ORDER_DURATION");
				if (spav != null) {
					if (order.getOrderDuration() > Integer.valueOf(spav.getParamValue())) {
						order.setOrderAmount(order.getOrderAmount() + 0d);
					} else {
						order.setOrderAmount(order.getOrderAmount() + serviceFeeGet);
					}
				}
			} else {
				order.setOrderAmount(order.getOrderAmount() + serviceFeeGet);
			}

			// 对订单总金额进行四舍五入
			order.setOrderAmount(ECCalculateUtils.round(order.getOrderAmount(), 2));
		}
		resultInfo.setCode(Constant.SECCUESS);
		resultInfo.setData(order);
		return resultInfo;
	}

	/**
	 * 前台我的订单根据查询条件，查询并返回Order的列表
	 * 
	 * @param q
	 * 
	 *            查询条件
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<Order> getOrderLists(Query q) {
		List<Order> list = null;
		try {
			// 直接调用Dao方法进行查询
			list = orderDao.queryAlls(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		// 如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<Order>(0) : list;
		return list;
	}

	@Override
	public Long countscCar(Query q) {
		// TODO Auto-generated method stub
		return orderDao.countscCar(q);
	}

	@Override
	public Order getOrderNowByCarNo(String carNo) {
		return orderDao.getOrderNowByCarNo(carNo);
	}

	@Override
	public PageFinder<CheckAccounts> getCheckAccountsPagedList(Query q) {
		PageFinder<CheckAccounts> page = new PageFinder<CheckAccounts>();
		List<String> memList = null;
		List<CheckAccounts> list = new ArrayList<CheckAccounts>();
		long rowCount = 0L;

		try {
			// 调用dao查询满足条件的分页数据
			memList = orderDao.pageListCheckAccounts(q);
			for (String memberNo : memList) {
				CheckAccounts cA = (CheckAccounts) q.getQ();
				CheckAccounts cAResult = new CheckAccounts();
				if (cA == null) {
					cA = new CheckAccounts();
				}
				cA.setMemberNo(memberNo);
				if (memberDao.get(memberNo) != null) {
					cA.setMobilePhone(memberDao.get(memberNo).getMobilePhone());
				}
				cAResult = orderDao.getCheckAccountsByMember(cA);
				cAResult.setCheckDateRange1(cA.getCheckDateRange1());
				cAResult.setCheckDateRange2(cA.getCheckDateRange2());
				if (cAResult.getMemberName() == null || cAResult.getMemberName().equals("")) {
					Member member = memberDao.get(memberNo);
					if (member != null) {
						cAResult.setMemberName(member.getMemberName());
					}
				}
				// 开票金额
				Double invoiceAmount = 0d;
				// 该会员某一时期的订单列表--发票信息--发票金额
				CheckAccountsView cavSearch = new CheckAccountsView();
				cavSearch.setMemberNo(memberNo);
				cavSearch.setMemberName(cAResult.getMemberName());
				cavSearch.setCheckDateRange1(cA.getCheckDateRange1());
				cavSearch.setCheckDateRange2(cA.getCheckDateRange2());
				List<CheckAccountsView> listView = orderDao.listCheckAccountsView(cavSearch);
				List<Invoice> listInvoice = new ArrayList<Invoice>();
				for (CheckAccountsView cav : listView) {
					List<Invoice> invoiceList = invoiceService.getInvoiceByOrderNo(cav.getOrderNo());
					if (invoiceList != null && invoiceList.size() > 0) {
						if (listInvoice.size() == 0) {
							Invoice inV = new Invoice();
							inV.setInvoiceNo(invoiceList.get(0).getInvoiceNo());
							inV.setInvoiceAmount(invoiceList.get(0).getInvoiceAmount());
							invoiceList.add(inV);
							invoiceAmount = invoiceAmount + invoiceList.get(0).getInvoiceAmount();
						} else if (listInvoice.size() > 0) {
							Integer tag = 0;
							String invoiceNo = invoiceList.get(0).getInvoiceNo();
							for (Invoice i : listInvoice) {
								if (i.getInvoiceNo().equals(invoiceNo)) {
									tag = 1;
								}
							}
							if (tag.equals(0)) {
								Invoice inV = new Invoice();
								inV.setInvoiceNo(invoiceList.get(0).getInvoiceNo());
								inV.setInvoiceAmount(invoiceList.get(0).getInvoiceAmount());
								invoiceList.add(inV);
								invoiceAmount = invoiceAmount + invoiceList.get(0).getInvoiceAmount();
							}
						}
					}
				}

				// if(invoiceDao.getInvoiceAmountByMember(cA)!=null){
				// invoiceAmount=invoiceDao.getInvoiceAmountByMember(cA);
				// }
				cAResult.setInvoiceAmount(invoiceAmount);
				list.add(cAResult);
			}
			// 调用dao统计满足条件的记录总数
			CheckAccounts caCount = (CheckAccounts) q.getQ();
			caCount.setMemberNo("");
			caCount.setMobilePhone("");
			rowCount = orderDao.countCheckAccounts(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		// 如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<CheckAccounts>(0) : list;

		// 将分页数据和记录总数设置到分页结果对象中
		page.setData(list);
		page.setRowCount(rowCount);

		return page;
	}

	@Override
	public List<CheckAccounts> getCheckAccountsList(CheckAccounts checkAccounts) {
		List<String> memList = null;
		List<CheckAccounts> list = new ArrayList<CheckAccounts>();
		// 调用dao查询满足条件的数据
		memList = orderDao.listCheckAccounts(checkAccounts);
		for (String memberNo : memList) {
			CheckAccounts cA = checkAccounts;
			CheckAccounts cAResult = new CheckAccounts();
			if (cA == null) {
				cA = new CheckAccounts();
			}
			cA.setMemberNo(memberNo);
			if (memberDao.get(memberNo) != null) {
				cA.setMobilePhone(memberDao.get(memberNo).getMobilePhone());
			}
			cAResult = orderDao.getCheckAccountsByMember(cA);
			cAResult.setCheckDateRange1(cA.getCheckDateRange1());
			cAResult.setCheckDateRange2(cA.getCheckDateRange2());
			if (cAResult.getMemberName() == null || cAResult.getMemberName().equals("")) {
				Member member = memberDao.get(memberNo);
				if (member != null) {
					cAResult.setMemberName(member.getMemberName());
				}
			}
			// 开票金额
			Double invoiceAmount = 0d;
			// 该会员某一时期的订单列表--发票信息--发票金额
			CheckAccountsView cavSearch = new CheckAccountsView();
			cavSearch.setMemberNo(memberNo);
			cavSearch.setMemberName(cAResult.getMemberName());
			cavSearch.setCheckDateRange1(cA.getCheckDateRange1());
			cavSearch.setCheckDateRange2(cA.getCheckDateRange2());
			List<CheckAccountsView> listView = orderDao.listCheckAccountsView(cavSearch);
			List<Invoice> listInvoice = new ArrayList<Invoice>();
			for (CheckAccountsView cav : listView) {
				List<Invoice> invoiceList = invoiceService.getInvoiceByOrderNo(cav.getOrderNo());
				if (invoiceList != null && invoiceList.size() > 0) {
					if (listInvoice.size() == 0) {
						Invoice inV = new Invoice();
						inV.setInvoiceNo(invoiceList.get(0).getInvoiceNo());
						inV.setInvoiceAmount(invoiceList.get(0).getInvoiceAmount());
						invoiceList.add(inV);
						invoiceAmount = invoiceAmount + invoiceList.get(0).getInvoiceAmount();
					} else if (listInvoice.size() > 0) {
						Integer tag = 0;
						String invoiceNo = invoiceList.get(0).getInvoiceNo();
						for (Invoice i : listInvoice) {
							if (i.getInvoiceNo().equals(invoiceNo)) {
								tag = 1;
							}
						}
						if (tag.equals(0)) {
							Invoice inV = new Invoice();
							inV.setInvoiceNo(invoiceList.get(0).getInvoiceNo());
							inV.setInvoiceAmount(invoiceList.get(0).getInvoiceAmount());
							invoiceList.add(inV);
							invoiceAmount = invoiceAmount + invoiceList.get(0).getInvoiceAmount();
						}
					}
				}
			}

			// if(invoiceDao.getInvoiceAmountByMember(cA)!=null){
			// invoiceAmount=invoiceDao.getInvoiceAmountByMember(cA);
			// }
			cAResult.setInvoiceAmount(invoiceAmount);
			list.add(cAResult);
		}
		return list;
	}

	@Override
	public ResultInfo<OrderStatusVo> getNowadayOrder(String memberNo, String DEVICE_KEY) throws ParseException {
		ResultInfo<OrderStatusVo> result = new ResultInfo<OrderStatusVo>();
		// 判断传过来的memberNo是否为空

		if (memberNo != null && memberNo.trim().length() > 0) {
			// 得到当前订单
			Order order = getNowadayOrderByMemberNo(memberNo);
			if (order != null) {
				// 判断当前订单，如果已预约，超时20，则开始计费，如果超时60，则取消订单
				if (order.getOrderStatus().equals(1)) {
					// 计算当前时间和订单 创建的时间的差
					int minute = ECDateUtils.differMinutes(order.getCreateTime(), new Date()).intValue();
					// 得到（系统参数设置的）60分钟，自动取消订单时长；
					SysParam sysp = sysParamService.getByParamKey(CarConstant.cancelordertime_param_key);
					int dingshi = 60;
					if (sysp != null && sysp.getParamValue() != null && !sysp.getParamValue().equals("")) {
						dingshi = Integer.parseInt(sysp.getParamValue());
					}
					// 得到车辆状态
					CarStatus carStatus = carStatusService.getCarStatus(order.getCarNo());
					// 根据车辆状态找到车辆当前所在场站（预约状态下使用）
					Park park = new Park();
					ParkStatus parkStatus = new ParkStatus();
					ParkOrderVO povo = new ParkOrderVO();
					if (null != carStatus && carStatus.getLocationParkNo() != null) { //
						park = parkService.getPark(carStatus.getLocationParkNo());
						parkStatus = parkStatusService.getParkStatus(carStatus.getLocationParkNo());
						// 进行格式转换
						if (null != park) {
							povo.setParkNo(park.getParkNo());
							povo.setParkName(park.getParkName());
							povo.setLongitude(park.getLongitude());
							povo.setLatitude(park.getLatitude());
							povo.setParkPicUrl1(imgPath + "/" + park.getParkPicUrl1());
							povo.setParkPicUrl2(imgPath + "/" + park.getParkPicUrl2());
							povo.setParkPicUrl3(imgPath + "/" + park.getParkPicUrl3());
							povo.setBusinessHours(park.getBusinessHours());
							povo.setServiceFeeGet(park.getServiceFeeGet());
							povo.setServiceFeeBack(park.getServiceFeeBack());
							povo.setChargerNumber(park.getChargerNumber());
							povo.setParkingSpaceNumber(park.getParkingSpaceNumber());
							String location = park.getAddrRegion1Name() + park.getAddrRegion2Name()
									+ park.getAddrRegion3Name() + park.getAddrStreet();
							povo.setAddress(location);
						}
						if (null != parkStatus) {
							povo.setCarNums(parkStatus.getCarNumber());
						}
					}
					if (minute >= dingshi) {
						// 结束订单前判断车辆是否熄火和在场站内
						// 车辆状态（1、已启动，2、已熄火）
						if (carStatus.getCarStatus() != null && carStatus.getCarStatus() == 1) {
							// -------订单超时取消时车辆未熄火--------

							// 如果当前已经超过倒计时的时间，那么则 修改订单状态，让其变为已计费；
							order.setOrderStatus(2);
							order.setStartBillingTime(new Date());
							// order.setOpenCarDoorTime(new Date());
							order.setUpdateTime(new Date());
							updateOrder(order, null);
							// 根据计费类型和时间分割点判断计费时当前时间属于哪一天
							judgeOrderDate(order, carStatus);

							OrderStatusVo orderStatusVo = new OrderStatusVo();
							orderStatusVo = orderToOrderStatusVo(order, orderStatusVo, DEVICE_KEY);
							orderStatusVo.setParkOrderVo(povo);
							result.setCode(OrderConstant.success_code);
							result.setData(orderStatusVo);
							result.setMsg("");
							// 订单已计费时修改车辆状态为订单中
							CarStatus carStatus2 = new CarStatus();
							carStatus2.setUserageStatus(2);
							carStatus2.setCarNo(order.getCarNo());
							carStatusService.updateCarStatus(carStatus2, null);
						} else {

							// 根据传的车辆位置，查找周边场站距离最近的场站
							String parkNo = "";
							parkNo = parkService.getCurrentParkNoByCarLocation(carStatus.getLongitude(),
									carStatus.getLatitude());
							if ("".equals(parkNo)) {
								// -------订单超时取消时车辆未在场站--------

								// 如果当前已经超过倒计时的时间，那么则 修改订单状态，让其变为已计费；
								order.setOrderStatus(2);
								order.setStartBillingTime(new Date());
								// order.setOpenCarDoorTime(new Date());
								order.setUpdateTime(new Date());
								order.setOrderStartMileage(carStatus.getMileage());
								updateOrder(order, null);
								// 根据计费类型和时间分割点判断计费时当前时间属于哪一天
								judgeOrderDate(order, carStatus);

								OrderStatusVo orderStatusVo = new OrderStatusVo();
								orderStatusVo = orderToOrderStatusVo(order, orderStatusVo, DEVICE_KEY);
								orderStatusVo.setParkOrderVo(povo);
								result.setCode(OrderConstant.success_code);
								result.setData(orderStatusVo);
								result.setMsg("");
								// 订单已计费时修改车辆状态为订单中
								CarStatus carStatus2 = new CarStatus();
								carStatus2.setUserageStatus(2);
								carStatus2.setCarNo(order.getCarNo());
								carStatusService.updateCarStatus(carStatus2, null);

							} else {
								// 如果当前已经超过倒计时的时间，那么则 修改订单状态，让其变为已计费；
								SysParam sysps = sysParamService.getByParamKey(CarConstant.order_overTime_charging);
								if (sysps != null && sysps.getParamValue().equals("1")) {
									CarStatus carStatu = new CarStatus();
									carStatu.setUserageStatus(2);
									carStatu.setCarNo(order.getCarNo());
									carStatusService.updateCarStatus(carStatu, null);

									Order or = new Order();
									or.setOrderNo(order.getOrderNo());
									or.setUpdateTime(new Date());
									// or.setOpenCarDoorTime(new Date());
									or.setOrderStartMileage(carStatus.getMileage());
									or.setOrderStatus(2);
									if (order.getStartBillingTime() == null || "".equals(order.getStartBillingTime())) {
										or.setStartBillingTime(new Date());
									}
									orderDao.update(or);

									// 根据计费类型和时间分割点判断计费时当前时间属于哪一天
									or.setRuleNo(order.getRuleNo());
									judgeOrderDate(or, carStatus);

									order.setFinishTime(new Date());
									order = orderTripView(order).getData();
									if (null == order) {
										result.setCode(OrderConstant.fail_code);
										result.setMsg("无对应计费规则");
									} else {
										order = orderDao.get(order.getOrderNo());
										OrderStatusVo orderStatusVo = new OrderStatusVo();
										orderStatusVo = orderToOrderStatusVo(order, orderStatusVo, DEVICE_KEY);
										orderStatusVo.setParkOrderVo(povo);
										result.setCode(OrderConstant.success_code);
										result.setData(orderStatusVo);
										result.setMsg("");
									}
								} else {
									// 如果当前已经超过倒计时的时间，那么则 修改订单状态，让其变为已完成；
									order.setOrderStatus(3);
									order.setFinishType(2);
									order.setFinishTime(new Date());
									int minutes = 0;
									if (order.getStartBillingTime() != null) {
										minutes = ECDateUtils.differMinutes(order.getStartBillingTime(), new Date())
												.intValue();
									} else {
										order.setStartBillingTime(order.getFinishTime());
									}
									order.setOrderDuration(minutes);// 设置订单时长

									// ---------------------------------------*****---------------下面是引用订单结算的代码------------------***-----------------------------------------------
									SysParam sp = sysParamService.getByParamKey("ORDER_OVERTIME_CHARGING");
									if (sp != null && sp.getParamValue().equals("0")) {
										order = cancelOrder(order.getOrderNo()).getData();
									} else {
										order = orderBalance(order, parkNo).getData();
									}

									// 订单结算结束
									// ----------------------------------
									// 订单结束向结束表添加数据
									if (order != null) {
										OrderFinish orderFinish = new OrderFinish();
										orderFinish.setOrderNo(order.getOrderNo());
										orderFinish.setFinishType(2);
										orderFinish.setCurrentCarLocation(order.getActualTakeLoacton());
										Operator operator = new Operator();
										orderFinishService.addOrderFinish(orderFinish, operator);

									}
									order.setUpdateTime(new Date());
									// log.error("-------统计需要取消计费的订单,修改订单--------");
									int row = orderDao.update(order);

									if (row > 0) {
										// log.error("-------统计需要取消计费的订单,修改订单完成--------");
										CarStatus c = new CarStatus();
										c.setUserageStatus(0);
										c.setCarNo(order.getCarNo());
										// log.error("-------统计需要取消计费的订单,修改车辆状态开始--------");
										ResultInfo<CarStatus> res = carStatusService.updateCarStatus(c, null);
										if (res.getCode().equals(1)) {
											// log.error("-------统计需要取消计费的订单,修改车辆状态完成--------");
										}
									}
									SysParam sps = sysParamService.getByParamKey("ORDER_OVERTIME_CHARGING");
									if (sps != null && sps.getParamValue().equals("0")) {
										OrderStatusVo orderStatusVo = new OrderStatusVo();
										orderStatusVo = orderToOrderStatusVo(order, orderStatusVo, DEVICE_KEY);
										orderStatusVo.setParkOrderVo(povo);
										result.setCode(OrderConstant.fail_code);
										result.setMsg(OrderConstant.noNowOrder);
									} else {
										OrderStatusVo orderStatusVo = new OrderStatusVo();
										orderStatusVo = orderToOrderStatusVo(order, orderStatusVo, DEVICE_KEY);
										orderStatusVo.setParkOrderVo(povo);
										result.setCode(OrderConstant.notPayOrder_code);
										result.setData(orderStatusVo);
										result.setMsg(OrderConstant.notPayOrder_msg);
									}

								}
							}
						}

					} else {
						OrderStatusVo orderStatusVo = new OrderStatusVo();
						orderStatusVo = orderToOrderStatusVo(order, orderStatusVo, DEVICE_KEY);
						orderStatusVo.setParkOrderVo(povo);
						result.setCode(OrderConstant.success_code);
						result.setData(orderStatusVo);
						result.setMsg("");
					}
				} else if (order.getOrderStatus().equals(2) && order.getStartBillingTime() != null) {
					if (order.getRuleNo() == null) {
						result.setCode(OrderConstant.fail_code);
						result.setMsg("无对应计费规则");
						return result;
					}
					order.setFinishTime(new Date());
					order = orderTripView(order).getData();
					if (null == order) {
						result.setCode(OrderConstant.fail_code);
						result.setMsg("无对应计费规则");
					} else {
						OrderStatusVo orderStatusVo = new OrderStatusVo();
						orderStatusVo = orderToOrderStatusVo(order, orderStatusVo, DEVICE_KEY);
						result.setCode(OrderConstant.success_code);
						result.setData(orderStatusVo);
						result.setMsg("");
					}
				} else if (order.getOrderStatus().equals(3) && order.getPayStatus().equals(0)) { // 订单已结束，未付费

					OrderStatusVo orderStatusVo = new OrderStatusVo();
					orderStatusVo = orderToOrderStatusVo(order, orderStatusVo, DEVICE_KEY);
					result.setCode(OrderConstant.notPayOrder_code);
					result.setData(orderStatusVo);
					result.setMsg(OrderConstant.notPayOrder_msg);

				} else if (order.getOrderStatus().equals(3) && order.getPayStatus().equals(1)) { // 订单已结束，已付费

					result.setCode(OrderConstant.fail_code);
					result.setMsg(OrderConstant.noNowOrder);
				} else {
					OrderStatusVo orderStatusVo = new OrderStatusVo();
					orderStatusVo = orderToOrderStatusVo(order, orderStatusVo, DEVICE_KEY);
					result.setCode(OrderConstant.success_code);
					result.setData(orderStatusVo);
					result.setMsg("");
				}

			} else if (order == null) {
				result.setCode(OrderConstant.fail_code);
				result.setMsg(OrderConstant.noNowOrder);
			}
		} else {
			result.setCode(MemberConstant.fail_lose_code);
			result.setMsg(MemberConstant.fail_lose_msg);
		}
		return result;
	}

	private void judgeOrderDate(Order order, CarStatus carStatus) throws ParseException {
		if (order.getOrderStartMileage() == null) {
			Order temp = orderDao.get(order.getOrderNo());
			temp.setOrderStartMileage(carStatus.getMileage());
			orderDao.update(temp);
		}

		int orderCaculateType = 0;
		PricingRule rule = pricingRuleService.getPricingRule(order.getRuleNo());
		try {
			int ruleType = rule.getRuleType();
			if (ruleType == 1 || ruleType == 2) {// 目前只有两种规则
				orderCaculateType = ruleType;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (orderCaculateType == 0) {
			SysParam sysparam1 = sysParamService.getByParamKey("ORDER_CACULATE_TYPE");
			if (sysparam1 != null && sysparam1.getParamValue() != null) {
				orderCaculateType = Integer.parseInt(sysparam1.getParamValue().trim());
			}
		}
		SysParam sysparam1 = sysParamService.getByParamKey("ORDER_CACULATE_TYPE");
		if (sysparam1 != null && sysparam1.getParamValue() != null) {
			orderCaculateType = Integer.parseInt(sysparam1.getParamValue());
		}
		if (orderCaculateType == 1) {
			String timeDivisionPointStr = "";
			SysParam sysparam = sysParamService.getByParamKey("TIME_DIVISION_POINT");
			if (sysparam != null) {
				timeDivisionPointStr = sysparam.getParamValue();
			} else {
				timeDivisionPointStr = "0";
			}
			int timeDivisionPoint = Integer.parseInt(timeDivisionPointStr);
			if (timeDivisionPoint <= 0 || timeDivisionPoint > 23) {
				timeDivisionPoint = 0;
			}
			if (!"".equals(timeDivisionPointStr)) {
				Date startBillingTime = order.getStartBillingTime();
				Date nowDate = ECDateUtils.formatDateToDateNew(startBillingTime);// 得到日期格式（YYYY-MM-DD）

				// 得到分隔点前一个时间，算出前一天及前一天结束时间
				Calendar beforTime = Calendar.getInstance();
				beforTime.setTime(nowDate);
				beforTime.set(Calendar.HOUR_OF_DAY, timeDivisionPoint);
				beforTime.add(Calendar.SECOND, -1);// 算出前一个时间(结束时间)
				Date beforDate = ECDateUtils.getDateBefore(nowDate, 1);// 算出前一天

				// 得到分隔点下一个时间，算出后一天及后一天后开始时间
				Calendar nextTime = Calendar.getInstance();
				nextTime.setTime(nowDate);
				nextTime.set(Calendar.HOUR_OF_DAY, timeDivisionPoint);// 算出下一个时间(开始时间)
				Date nextOrderStartMinute = nextTime.getTime();// 算出后一天开始时间
				Date nextDate = ECDateUtils.getDateBefore(nowDate, 0);// 算出后一天

				// 算出订单计费是时间分隔点的中的哪一天(前一天或后一天),用于比较数据库数据是哪一个时间点的。
				Date OrderMileageDate = nextDate;
				if (startBillingTime.before(nextOrderStartMinute)) {
					OrderMileageDate = beforDate;
				}

				OrderMileage orderMileage = orderMileageService.getNewestOrderMileage(order.getOrderNo());
				if (null == orderMileage) {
					OrderMileage om = new OrderMileage();
					om.setOrderNo(order.getOrderNo());
					om.setOrderMileageDate(OrderMileageDate);
					om.setOrderStartMinute(order.getStartBillingTime());
					om.setOrderStartMileage(carStatus.getMileage());
					orderMileageService.addOrderMileage(om, null);
				}
			}
		} else if (orderCaculateType == 2) {
			OrderMileage orderMileage = orderMileageService.getNewestOrderMileage(order.getOrderNo());
			// 更新订单里程详情表
			if (orderMileage == null) {
				OrderMileage om = new OrderMileage();
				om.setOrderNo(order.getOrderNo());
				om.setOrderMileageDate(order.getStartBillingTime());
				om.setOrderStartMinute(order.getStartBillingTime());
				om.setOrderStartMileage(carStatus.getMileage());
				Date currentDateTime = ECDateUtils.getCurrentDateTime();
				// 存储高峰时段费用信息
				List<PeakHoursCost> peakHoursCostList = new ArrayList<PeakHoursCost>();
				PeakHours peakHoursQuery = new PeakHours();
				peakHoursQuery.setRuleNo(rule.getRuleNo());
				List<PeakHours> peakHoursList = peakHoursService.getPeakHoursList(new Query(peakHoursQuery));
				for (PeakHours peakHours : peakHoursList) {
					Calendar pearHourStart = Calendar.getInstance();
					pearHourStart.setTime(currentDateTime);
					pearHourStart.set(Calendar.HOUR_OF_DAY, Integer.parseInt(peakHours.getPeakStartHours()));
					pearHourStart.set(Calendar.MINUTE, 0);
					pearHourStart.set(Calendar.SECOND, 0);
					Date pearHourStartTime = pearHourStart.getTime();
					Calendar pearHourEnd = Calendar.getInstance();
					pearHourEnd.setTime(currentDateTime);
					pearHourEnd.set(Calendar.HOUR_OF_DAY, Integer.parseInt(peakHours.getPeakEndHours()));
					pearHourEnd.set(Calendar.MINUTE, 0);
					pearHourEnd.set(Calendar.SECOND, 0);
					Date pearHourEndTime = pearHourEnd.getTime();
					// 高峰时段开始时间小于当前时间且订单开始时间小于高峰时间开始时间
					if (pearHourStartTime.before(currentDateTime)
							&& order.getStartBillingTime().before(pearHourStartTime)) {
						PeakHoursCost peakHoursCost = new PeakHoursCost();
						peakHoursCost.setTimePeriod(peakHours.getPeakHours());
						peakHoursCost.setTimePeriodStartTime(pearHourStartTime);
						peakHoursCost.setTimePeriodStartMileage(carStatus.getMileage());
						peakHoursCostList.add(peakHoursCost);
						// 高峰时段开始时间小于当前时间且高峰时间开始时间小于订单开始时间
					} else if (pearHourStartTime.before(currentDateTime)
							&& pearHourStartTime.before(order.getStartBillingTime())
							&& order.getStartBillingTime().before(pearHourEndTime)) {
						PeakHoursCost peakHoursCost = new PeakHoursCost();
						peakHoursCost.setTimePeriod(peakHours.getPeakHours());
						peakHoursCost.setTimePeriodStartTime(order.getStartBillingTime());
						peakHoursCost.setTimePeriodStartMileage(carStatus.getMileage());
						peakHoursCostList.add(peakHoursCost);
					}
				}
				try {
					om.setPearTimeCost(JsonUtils.toJsonString(peakHoursCostList));
				} catch (Exception e) {
					e.printStackTrace();
				}
				orderMileageService.addOrderMileage(om, null);
			}
		}
		if (order.getCarPlateNo() == null) {
			order = orderDao.get(order.getOrderNo());
		}
		// 订单计费后修改对应车的红包记录
		CarRedPacket carRedPacket = carRedPacketService.getCarRedPacketByCarPlateNo(order.getCarPlateNo(), "0");
		if (carRedPacket != null) {
			CarRedPacket carRedPacketForUpdate = new CarRedPacket();
			carRedPacketForUpdate.setCarRedPacketId(carRedPacket.getCarRedPacketId());
			carRedPacketForUpdate.setCarUserageStatus(2);
			carRedPacketService.updateCarRedPacket(carRedPacketForUpdate, null);
		}
	}

	/**
	 * 将查出来的Order对象转OrderStatusVo
	 */
	public OrderStatusVo orderToOrderStatusVo(Order order, OrderStatusVo orderStatusVo, String DEVICE_KEY) {
		// 如果首次开门时间不为空，那就开门状态就是已开车门；
		if (order.getOpenCarDoorTime() != null) {
			orderStatusVo.setIsOpenDoor(1);
		} else {
			orderStatusVo.setIsOpenDoor(0);// 是否已经开过门
		}
		Car carz = carService.getCar(order.getCarNo());
		if (carz != null) {
			orderStatusVo.setSeaTing(carz.getSeaTing());
			orderStatusVo.setCarBrandName(carz.getCarBrandName());
			orderStatusVo.setCarModelName(carz.getCarModelName());
			orderStatusVo.setPower(carz.getPower());
			orderStatusVo.setCarPhotoUrl1(imgPath + "/" + carz.getCarPhotoUrl1());
		}
		orderStatusVo.setMemberNo(order.getMemberNo());// 订单ID
		if (order.getWarningOrder() != null) {
			orderStatusVo.setWarningOrder(order.getWarningOrder());
		} else {
			orderStatusVo.setWarningOrder(0);
		}
		orderStatusVo.setOrderNo(order.getOrderNo());// 会员ID
		orderStatusVo.setOrderStatus(order.getOrderStatus());// 订单状态
		orderStatusVo.setCreateTime(ECDateUtils.formatTime(order.getCreateTime()));// 下单时间
																					// formatTime
		orderStatusVo.setOpenCarDoorTime(ECDateUtils.formatTime(order.getOpenCarDoorTime()));// 取车时间
																								// formatTime
		orderStatusVo.setStrtChargingTime(
				sysParamService.getByParamKey(CarConstant.cancelordertime_param_key).getParamValue());// 自动计费时长
		orderStatusVo.setCancelOrderTime(
				sysParamService.getByParamKey(CarConstant.cancelordertime_param_key).getParamValue());// 自动取消订
		orderStatusVo.setNowTime(ECDateUtils.formatTime(new Date()));
		// order=orderService.orderTripView(order).getData();
		orderStatusVo.setOrderDuration(order.getOrderDuration());
		orderStatusVo.setNowAmount(ECNumberUtils.roundDoubleWithScale(order.getOrderAmount(), 2));// 订单计费
		// 车牌号
		orderStatusVo.setCarPlateNo(order.getCarPlateNo());

		// key,这里先写死
		if (order.getCarNo() != null) {
			Car car = carService.getCar(order.getCarNo());
			if (car != null) {
				if (car.getDeviceId() != null) {
					Device device = deviceService.getDevice(car.getDeviceId());
					if (device != null) {
						if (device.equals("E6")) {
							orderStatusVo.setDeviceType(1);
						} else if (device.equals("04")) {
							orderStatusVo.setDeviceType(2);
						} else {
							orderStatusVo.setDeviceType(1);
						}
						orderStatusVo.setDeviceKey(DEVICE_KEY);
						SysParam sysparm = sysParamService.getByParamKey("CUSTOMER_COMPANY_CODE");
						if (sysparm != null && sysparm.getParamValue() != null && !"".equals(sysparm.getParamValue())) {
							String carPlateNo_last5 = car.getCarPlateNo().substring(2, car.getCarPlateNo().length());
							String blueToothName = "";
							if ("04".equals(device.getDeviceModel())) {
								if (device.getBluetoothName() != null && device.getBluetoothName().length() > 0) {
									blueToothName = device.getBluetoothName();
								} else {
									blueToothName = sysparm.getParamValue() + "_" + carPlateNo_last5;
								}
							} else {
								if (device.getBluetoothName() != null && device.getBluetoothName().length() > 0) {
									blueToothName = device.getBluetoothName();
								} else {
									String macAddr = device.getMacAddr().replace(":", "");
									String mac_last5 = macAddr.substring(macAddr.length() - 6, macAddr.length());
									blueToothName = sysparm.getParamValue() + "_" + carPlateNo_last5 + "_" + mac_last5;
									blueToothName = sysparm.getParamValue() + "_" + carPlateNo_last5;
								}
							}
							orderStatusVo.setBlueToothName(blueToothName);
						}
					}
				}

			}
		}

		// TERMINAL_DEVICE_NO
		// 根据车牌号得到车辆状态
		CarStatus carStatus = carStatusService.getCarStatus(order.getCarNo());
		if (carStatus != null) {
			orderStatusVo.setRangeMileage(carStatus.getRangeMileage());// 可续航里程
			if (carStatus.getPower() != null) {
				orderStatusVo.setPower(carStatus.getPower());
			} else {
				orderStatusVo.setPower(0d);
			}
			if (order.getOrderStatus() == 1 || order.getOrderStatus() == 2) {
				CarRedPacket carRedPacket = carRedPacketService.getCarRedPacketByCarPlateNo(order.getCarPlateNo(), "2");
				if (carRedPacket != null) {
					orderStatusVo.setIsCarRedPakcet("1");
					CarRedPacketVo carRedPacketVo = new CarRedPacketVo();
					carRedPacketVo.setIsCharge(carRedPacket.getIsCharge());
					carRedPacketVo.setIsOrderAmountLimit(carRedPacket.getIsOrderAmountLimit());
					if (carRedPacket.getIsOrderAmountLimit() == 1) {
						carRedPacketVo.setOrderAmountLimit(carRedPacket.getOrderAmountLimit());
					} else {
						carRedPacketVo.setOrderAmountLimit(0.0);
					}
					orderStatusVo.setCarRedPacketVo(carRedPacketVo);
				}
			}
			// 坐标转换
			if (carStatus.getLongitude() != null && carStatus.getLatitude() != null) {
				double[] bdCoord = ECGeoCoordinateTransformUtil.wgs84tobd09(carStatus.getLongitude(),
						carStatus.getLatitude());
				orderStatusVo.setLongitude(bdCoord[0]);// 经度（百度坐标）
				orderStatusVo.setLatitude(bdCoord[1]);// 纬度（百度坐标）
			}
			// MAC地址
			Device d = deviceService.getDevice(carStatus.getDeviceNo());
			if (d != null) {
				orderStatusVo.setMacAddr(d.getMacAddr());
				orderStatusVo.setDeviceSn(d.getDeviceSn());
				orderStatusVo.setWxDeviceId(d.getWxDeviceId());
			}
		}
		return orderStatusVo;
	}

	@Override
	public PageFinder<CheckAccountsView> getCheckAccountsViewPagedList(Query q) {
		PageFinder<CheckAccountsView> page = new PageFinder<CheckAccountsView>();
		List<CheckAccountsView> list = new ArrayList<CheckAccountsView>();
		long rowCount = 0L;

		try {
			// 调用dao查询满足条件的分页数据
			list = orderDao.pageListCheckAccountsView(q);
			for (CheckAccountsView cav : list) {
				List<Invoice> invoiceList = invoiceService.getInvoiceByOrderNo(cav.getOrderNo());
				if (invoiceList != null && invoiceList.size() > 0) {
					cav.setInvoiceNo(invoiceList.get(0).getInvoiceNo());
					cav.setInvoiceAmount(invoiceList.get(0).getInvoiceAmount());
				}
			}
			// 调用dao统计满足条件的记录总数
			rowCount = orderDao.countCheckAccountsView(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		// 如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<CheckAccountsView>(0) : list;

		// 将分页数据和记录总数设置到分页结果对象中
		page.setData(list);
		page.setRowCount(rowCount);

		return page;
	}

	@Override
	public List<CheckAccountsView> getCheckAccountsViewList(CheckAccountsView checkAccountsView) {
		List<CheckAccountsView> list = new ArrayList<CheckAccountsView>();
		try {
			// 调用dao查询满足条件的分页数据
			list = orderDao.listCheckAccountsView(checkAccountsView);
			for (CheckAccountsView cav : list) {
				List<Invoice> invoiceList = invoiceService.getInvoiceByOrderNo(cav.getOrderNo());
				if (invoiceList != null && invoiceList.size() > 0) {
					cav.setInvoiceNo(invoiceList.get(0).getInvoiceNo());
					cav.setInvoiceAmount(invoiceList.get(0).getInvoiceAmount());
				}
			}
			// 调用dao统计满足条件的记录总数
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		// 如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<CheckAccountsView>(0) : list;
		return list;
	}

	@Override
	public PageFinder<Summary> getSummaryPagedList(Query q) {
		PageFinder<Summary> page = new PageFinder<Summary>();
		List<Summary> list = new ArrayList<Summary>();
		long rowCount = 1L;

		try {
			// 调用dao查询满足条件的分页数据
			list = orderDao.pageListSummary(q);
			// 调用dao统计满足条件的记录总数
			rowCount = orderDao.countSummary(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		// 如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<Summary>(0) : list;

		// 将分页数据和记录总数设置到分页结果对象中
		page.setData(list);
		page.setRowCount(rowCount);
		return page;
	}

	@Override
	public List<Summary> getSummaryList(Summary summary) {
		List<Summary> list = new ArrayList<Summary>();
		try {
			// 调用dao查询满足条件的分页数据
			list = orderDao.getSummaryList(summary);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		// 如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<Summary>(0) : list;
		return list;
	}

	public Order diCoupon(Order order) {
		Coupon c = new Coupon();
		c.setIsAvailable(1);
		c.setUsedStatus(0);
		c.setAvailableTime2Start(new Date());
		c.setMemberNo(order.getMemberNo());
		Query q = new Query(c);
		List<Coupon> coupons = couponService.getCouponListOrder(q);
		if (coupons != null && coupons.size() > 0) {
			for (int i = 0; i < coupons.size(); i++) {// 判断满额
				CouponPlan cp = couponPlanService.getCouponPlan(coupons.get(i).getPlanNo());
				if (cp != null && cp.getCouponMethod() == 1) {// 打折
					if (ECCalculateUtils.ge(order.getOrderAmount(), cp.getDiscountMaxAmount())) {
						if (coupons.get(i).getCouponMethod() == 1) {
							Double dd = ECCalculateUtils.mul(0.01, (10 - coupons.get(0).getDiscount()));
							Double d = ECCalculateUtils.mul(order.getOrderAmount(), dd);
							if (order.getOrderAmount() > d) {
								order.setDiscountAmount(d);
								order.setPayableAmount(ECCalculateUtils.sub(order.getOrderAmount(), d));
							} else {
								order.setDiscountAmount(order.getOrderAmount());
								order.setPayableAmount(0d);
								order.setPayStatus(1);
							}

						}
						// 修改 优惠券表
						Coupon cs = new Coupon();
						cs.setBizObjNo(order.getOrderNo());
						cs.setCouponNo(coupons.get(i).getCouponNo());
						cs.setUsedStatus(1);
						cs.setIsAvailable(1);
						cs.setDeductibleAmount(order.getDiscountAmount());
						Operator operator = new Operator();
						couponService.updateCouponAvailable(cs, operator);
						break;
					}
				} else if (cp != null && cp.getCouponMethod() == 2) {// 满减
					if (ECCalculateUtils.ge(order.getOrderAmount(), cp.getConsumeAmount())) {
						if (coupons.get(i).getCouponMethod() == 2) {
							if (order.getOrderAmount() > coupons.get(i).getDiscountAmount()) {
								order.setDiscountAmount(coupons.get(i).getDiscountAmount());
								order.setPayableAmount(ECCalculateUtils.sub(order.getOrderAmount(),
										coupons.get(i).getDiscountAmount()));
							} else {
								order.setDiscountAmount(order.getOrderAmount());
								order.setPayableAmount(0d);
								order.setPayStatus(1);
							}
							//
						}
						Coupon cs = new Coupon();
						cs.setBizObjNo(order.getOrderNo());
						cs.setCouponNo(coupons.get(i).getCouponNo());
						cs.setUsedStatus(1);
						cs.setIsAvailable(1);
						cs.setDeductibleAmount(order.getDiscountAmount());
						Operator operator = new Operator();
						couponService.updateCouponAvailable(cs, operator);
						break;
					}

				}

			}

		}

		// if (order.getOrderAmount() < 0) {
		// order.setOrderAmount(0d);
		// }
		return order;
	}

	@Override
	public PageFinder<Details> getDetailsPagedList(Query q) {
		PageFinder<Details> page = new PageFinder<Details>();
		List<Details> list = new ArrayList<Details>();
		long rowCount = 1L;

		try {
			// 调用dao查询满足条件的分页数据
			list = orderDao.pageListDetails(q);
			// 调用dao统计满足条件的记录总数
			rowCount = orderDao.countDetails(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		// 如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<Details>(0) : list;

		// 将分页数据和记录总数设置到分页结果对象中
		page.setData(list);
		page.setRowCount(rowCount);
		return page;
	}

	@Override
	public List<Details> getDetailsList(Details details) {
		List<Details> list = new ArrayList<Details>();

		try {
			// 调用dao查询满足条件的分页数据
			list = orderDao.getDetailsList(details);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		// 如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<Details>(0) : list;
		return list;
	}

	@Override
	public ResultInfo<Money> getMoneyDetails(Money money) {

		Double depositAlipayAmount = 0d;

		Double depositAlipayAmountWhole = 0d;

		Double depositAlipayRefund = 0d;

		Double depositAlipayRefundWhole = 0d;

		Double orderAlipayAmount = 0d;

		Double orderAlipayAmountWhole = 0d;

		Double priceAlipayAmount = 0d;

		Double priceAlipayAmountWhole = 0d;

		Double alipayAmount = 0d;

		Double awhole = 0d;

		Double depositWxAmount = 0d;

		Double depositWxAmountWhole = 0d;

		Double depositWxRefund = 0d;

		Double depositWxRefundWhole = 0d;

		Double orderWxAmount = 0d;

		Double orderWxAmountWhole = 0d;

		Double priceWxAmount = 0d;

		Double priceWxAmountWhole = 0d;

		Double wxAmount = 0d;

		Double awholeWx = 0d;

		Map<String, Object> alipayIncomeMap = paymentRecordDao.getAlipayIncomeMap(money);

		if (alipayIncomeMap != null) {
			orderAlipayAmount = (Double) alipayIncomeMap.get("orderAlipayAmount");
			if (orderAlipayAmount != null) {
				money.setOrderAlipayAmount(orderAlipayAmount);
				money.setOrderAlipayCharge(ECCalculateUtils.mul(orderAlipayAmount, zfbAgentFee));
			} else {
				orderAlipayAmount = 0d;
				money.setOrderAlipayAmount(0d);
				money.setOrderAlipayCharge(0d);
			}
			priceAlipayAmount = (Double) alipayIncomeMap.get("priceAlipayAmount");
			if (priceAlipayAmount != null) {
				money.setPriceAlipayAmount(priceAlipayAmount);
				money.setPriceAlipayCharge(ECCalculateUtils.mul(priceAlipayAmount, zfbAgentFee));
			} else {
				priceAlipayAmount = 0d;
				money.setPriceAlipayAmount(0d);
				money.setPriceAlipayCharge(0d);
			}
			depositAlipayAmount = (Double) alipayIncomeMap.get("depositAlipayAmount");
			if (depositAlipayAmount != null) {
				money.setDepositAlipayAmount(depositAlipayAmount);
				money.setDepositAlipayCharge(ECCalculateUtils.mul(depositAlipayAmount, zfbAgentFee));
			} else {
				depositAlipayAmount = 0d;
				money.setDepositAlipayAmount(0d);
				money.setDepositAlipayCharge(0d);
			}

			depositAlipayRefund = depositOrderDao.getDepositAlipayRefund(money);
			if (depositAlipayRefund == null) {
				depositAlipayRefund = 0d;
			}
			depositAlipayRefund = depositAlipayRefund * (-1);
			money.setDepositAlipayRefund(depositAlipayRefund);

			Double certification = 0d;
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date d1;
			try {
				d1 = df.parse("2016-09-06 17:08:30");
				if (d1.getTime() >= money.getStartTime().getTime() && d1.getTime() <= money.getEndTime().getTime()) {
					certification = 5.3;
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}
			double depositAlipayCharge = money.getDepositAlipayCharge() == null ? 0d : money.getDepositAlipayCharge();
			double orderAlipayCharge = money.getOrderAlipayCharge() == null ? 0d : money.getOrderAlipayCharge();
			double priceAlipayCharge = money.getPriceAlipayCharge() == null ? 0d : money.getPriceAlipayCharge();
			// 正式环境 支付宝合计
			alipayAmount = 0d;
			alipayAmount = ECNumberUtils
					.roundDoubleWithScale(
							depositAlipayAmount + orderAlipayAmount + priceAlipayAmount + certification
									+ depositAlipayRefund + depositAlipayCharge + orderAlipayCharge + priceAlipayCharge,
							2);
			money.setAlipayAmount(alipayAmount);
		}

		Map<String, Object> wxIncomeMap = paymentRecordDao.getWxIncomeMap(money);

		if (wxIncomeMap != null) {
			orderWxAmount = (Double) wxIncomeMap.get("orderWxAmount");
			if (orderWxAmount != null) {
				money.setOrderWxAmount(orderWxAmount);
				money.setOrderWxCharge(orderWxAmount * 0.006);
			} else {
				orderWxAmount = 0d;
				money.setOrderWxAmount(0d);
				money.setOrderWxCharge(0d);
			}
			priceWxAmount = (Double) wxIncomeMap.get("priceWxAmount");
			if (priceWxAmount != null) {
				money.setPriceWxAmount(priceWxAmount);
				money.setPriceWxCharge(priceWxAmount * 0.006);
			} else {
				priceWxAmount = 0d;
				money.setPriceWxAmount(0d);
				money.setPriceWxCharge(0d);
			}
			depositWxAmount = (Double) wxIncomeMap.get("depositWxAmount");
			if (depositWxAmount != null) {
				money.setDepositWxAmount(depositWxAmount);
				money.setDepositWxCharge(depositWxAmount * 0.006);
			} else {
				depositWxAmount = 0d;
				money.setDepositWxAmount(0d);
				money.setDepositWxCharge(0d);
			}

			// 正式环境 预付款微信退款
			depositWxRefund = depositOrderDao.getDepositWxRefund(money);
			if (depositWxRefund == null) {
				depositWxRefund = 0d;
			}
			depositWxRefund = depositWxRefund * (-1);
			money.setDepositWxRefund(depositWxRefund);
			// 正式环境 预付款微信退款手续费返还
			money.setChargeRefund((ECNumberUtils.roundDoubleWithScale(depositWxRefund * 0.006, 2)) * (-1));

			// 正式环境 微信合计
			double depositWxCharge = money.getDepositWxCharge() == null ? 0d : money.getDepositWxCharge();
			double orderWxCharge = money.getOrderWxCharge() == null ? 0d : money.getOrderWxCharge();
			double priceWxCharge = money.getPriceWxCharge() == null ? 0d : money.getPriceWxCharge();
			double chargeRefund = money.getChargeRefund() == null ? 0d : money.getChargeRefund();
			wxAmount = ECNumberUtils
					.roundDoubleWithScale(
							money.getDepositWxAmount() + money.getOrderWxAmount() + money.getPriceWxAmount()
									+ depositWxRefund + depositWxCharge + orderWxCharge + priceWxCharge + chargeRefund,
							2);
			money.setWxAmount(wxAmount);
		}

		Map<String, Object> alipayIncomeWholeMap = finaceTestDao.getAlipayIncomeWholeMap(money);

		if (alipayIncomeWholeMap != null) {
			orderAlipayAmountWhole = (Double) alipayIncomeWholeMap.get("orderAlipayAmountWhole");
			if (orderAlipayAmountWhole != null) {
				money.setOrderAlipayAmountWhole(orderAlipayAmountWhole);
				money.setOrderAlipayChargeWhole(orderAlipayAmountWhole * 0.006);
			} else {
				orderAlipayAmountWhole = 0d;
				money.setOrderAlipayAmountWhole(0d);
				money.setOrderAlipayChargeWhole(0d);
			}
			priceAlipayAmountWhole = (Double) alipayIncomeWholeMap.get("priceAlipayAmountWhole");
			if (priceAlipayAmountWhole != null) {
				money.setPriceAlipayAmountWhole(priceAlipayAmountWhole);
				money.setPriceAlipayChargeWhole(priceAlipayAmountWhole * 0.006);
			} else {
				priceAlipayAmountWhole = 0d;
				money.setPriceAlipayAmountWhole(0d);
				money.setPriceAlipayChargeWhole(0d);
			}
			depositAlipayAmountWhole = (Double) alipayIncomeWholeMap.get("depositAlipayAmountWhole");
			if (depositAlipayAmountWhole != null) {
				money.setDepositAlipayAmountWhole(depositAlipayAmountWhole);
				money.setDepositAlipayChargeWhole(depositAlipayAmountWhole * 0.006);
			} else {
				depositAlipayAmountWhole = 0d;
				money.setDepositAlipayAmountWhole(0d);
				money.setDepositAlipayChargeWhole(0d);
			}

			// 全部 押金支付宝退款（押金 退款 支付宝）
			depositAlipayRefundWhole = finaceTestDao.getDepositAlipayRefundWhole(money);
			if (depositAlipayRefundWhole == null) {
				depositAlipayRefundWhole = 0d;
			}
			Double alipayTransRefundWhole = finaceTestDao.getAlipayTransRefundWhole(money);
			if (alipayTransRefundWhole == null) {
				alipayTransRefundWhole = 0d;
			}
			depositAlipayRefundWhole += alipayTransRefundWhole;// 押金退款，包括支付宝原路退款+支付宝转账退款
			money.setDepositAlipayRefundWhole(depositAlipayRefundWhole);

			Double certification = 0d;
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date d1;
			try {
				d1 = df.parse("2016-09-06 17:08:30");
				if (d1.getTime() >= money.getStartTime().getTime() && d1.getTime() <= money.getEndTime().getTime()) {
					certification = 5.3;
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}
			double depositAlipayChargeWhole = money.getDepositAlipayChargeWhole() == null ? 0d
					: money.getDepositAlipayChargeWhole();
			double orderAlipayChargeWhole = money.getOrderAlipayChargeWhole() == null ? 0d
					: money.getOrderAlipayChargeWhole();
			double priceAlipayChargeWhole = money.getPriceAlipayChargeWhole() == null ? 0d
					: money.getPriceAlipayChargeWhole();
			// 全部合计
			awhole = 0d;
			awhole = ECNumberUtils.roundDoubleWithScale(depositAlipayAmountWhole + orderAlipayAmountWhole
					+ priceAlipayAmountWhole + certification + depositAlipayRefundWhole + depositAlipayChargeWhole
					+ orderAlipayChargeWhole + priceAlipayChargeWhole, 2);
			money.setAwhole(awhole);
		}

		// 测试 支付宝 押金收入
		Double depositAlipayAmountTest = 0d;
		if (depositAlipayAmountWhole != 0.0) {
			depositAlipayAmountTest = ECNumberUtils.roundDoubleWithScale(depositAlipayAmountWhole - depositAlipayAmount,
					2);

		}
		money.setDepositAlipayAmountTest(depositAlipayAmountTest);
		// 测试 支付宝 押金退款
		Double depositAlipayRefundTest = 0d;
		if (depositAlipayRefundWhole != 0.0) {
			depositAlipayRefundTest = ECNumberUtils.roundDoubleWithScale(depositAlipayRefundWhole - depositAlipayRefund,
					2);

		}
		money.setDepositAlipayRefundTest(depositAlipayRefundTest);
		// 测试 支付宝 押金手续费
		Double depositAlipayChargeTest = 0d;
		if (money.getDepositAlipayChargeWhole() != 0.0) {
			depositAlipayChargeTest = ECNumberUtils
					.roundDoubleWithScale(money.getDepositAlipayChargeWhole() - money.getDepositAlipayCharge(), 2);
		}

		money.setDepositAlipayChargeTest(depositAlipayChargeTest);
		// 测试 支付宝 订单 收入
		Double orderAlipayAmountTest = 0d;
		if (orderAlipayAmountWhole != 0.0) {
			orderAlipayAmountTest = ECNumberUtils.roundDoubleWithScale(orderAlipayAmountWhole - orderAlipayAmount, 2);
		}

		money.setOrderAlipayAmountTest(orderAlipayAmountTest);
		// 测试 支付宝 订单 手续费
		Double orderAlipayChargeTest = 0d;
		if (money.getOrderAlipayChargeWhole() != 0.0) {
			orderAlipayChargeTest = ECNumberUtils
					.roundDoubleWithScale(money.getOrderAlipayChargeWhole() - money.getOrderAlipayCharge(), 2);
		}

		money.setOrderAlipayChargeTest(orderAlipayChargeTest);
		// 测试 支付宝 套餐 收入
		Double priceAlipayAmountTest = 0d;
		if (priceAlipayAmountWhole != 0.0) {
			priceAlipayAmountTest = ECNumberUtils.roundDoubleWithScale(priceAlipayAmountWhole - priceAlipayAmount, 2);
		}

		money.setPriceAlipayAmountTest(priceAlipayAmountTest);
		// 测试 支付宝 套餐 手续费
		Double priceAlipayChargeTest = 0d;
		if (money.getPriceAlipayChargeWhole() != 0.0) {
			priceAlipayChargeTest = ECNumberUtils
					.roundDoubleWithScale(money.getPriceAlipayChargeWhole() - money.getPriceAlipayCharge(), 2);
		}

		money.setPriceAlipayChargeTest(priceAlipayChargeTest);
		// 测试 支付宝合计
		Double asw = 0d;
		if (awhole != 0.0) {
			asw = ECNumberUtils.roundDoubleWithScale(awhole - alipayAmount, 2);

		} else {
			asw = ECNumberUtils.roundDoubleWithScale(0, 2);
		}
		money.setAsw(asw);

		Map<String, Object> wxIncomeWholeMap = finaceTestDao.getWxIncomeWholeMap(money);

		if (wxIncomeWholeMap != null) {
			orderWxAmountWhole = (Double) wxIncomeWholeMap.get("orderWxAmountWhole");
			if (orderWxAmountWhole != null) {
				money.setOrderWxAmountWhole(orderWxAmountWhole);
				money.setOrderWxChargeWhole(orderWxAmountWhole * 0.006);
			} else {
				orderWxAmountWhole = 0d;
				money.setOrderWxAmountWhole(0d);
				money.setOrderWxChargeWhole(0d);
			}
			priceWxAmountWhole = (Double) wxIncomeWholeMap.get("priceWxAmountWhole");
			if (priceWxAmountWhole != null) {
				money.setPriceWxAmountWhole(priceWxAmountWhole);
				money.setPriceWxChargeWhole(priceWxAmountWhole * 0.006);
			} else {
				priceWxAmountWhole = 0d;
				money.setPriceWxAmountWhole(0d);
				money.setPriceWxChargeWhole(0d);
			}
			depositWxAmountWhole = (Double) wxIncomeWholeMap.get("depositWxAmountWhole");
			if (depositWxAmountWhole != null) {
				money.setDepositWxAmountWhole(depositWxAmountWhole);
				money.setDepositWxChargeWhole(depositWxAmountWhole * 0.006);
			} else {
				depositWxAmountWhole = 0d;
				money.setDepositWxAmountWhole(0d);
				money.setDepositWxChargeWhole(0d);
			}

			// 全部 押金微信退款
			depositWxRefundWhole = finaceTestDao.getDepositWxRefundWhole(money);
			if (depositWxRefundWhole == null) {
				depositWxRefundWhole = 0d;
			}
			depositWxRefundWhole = depositWxRefundWhole * (-1);
			money.setDepositWxRefundWhole(depositWxRefundWhole);
			// 全部环境 预付款微信退款手续费返还
			money.setChargeRefundWhole((ECNumberUtils.roundDoubleWithScale(depositWxRefundWhole * 0.006, 2)) * (-1));

			// 微信 全部合计
			awholeWx = 0d;
			awholeWx = ECNumberUtils.roundDoubleWithScale(depositWxAmountWhole + orderWxAmountWhole + priceWxAmountWhole
					+ depositWxRefundWhole + money.getDepositWxChargeWhole() + money.getOrderWxChargeWhole()
					+ money.getPriceWxChargeWhole() + money.getChargeRefundWhole(), 2);
			money.setAwholeWx(awholeWx);
		}

		// 测试 微信 押金收入
		Double depositWxAmountTest = 0d;
		if (depositWxAmountWhole != 0.0) {
			depositWxAmountTest = ECNumberUtils.roundDoubleWithScale(depositWxAmountWhole - depositWxAmount, 2);
		}

		money.setDepositWxAmountTest(depositWxAmountTest);
		// 测试 微信 押金退款
		Double depositWxRefundTest = 0d;
		if (depositWxRefundWhole != 0.0) {
			depositWxRefundTest = ECNumberUtils.roundDoubleWithScale(depositWxRefundWhole - depositWxRefund, 2);
		}

		money.setDepositWxRefundTest(depositWxRefundTest);
		// 测试 微信 押金手续费
		Double depositWxChargeTest = 0d;
		if (money.getDepositWxChargeWhole() != 0.0) {
			depositWxChargeTest = ECNumberUtils
					.roundDoubleWithScale(money.getDepositWxChargeWhole() - money.getDepositWxCharge(), 2);
		}

		money.setDepositWxChargeTest(depositWxChargeTest);
		// 测试 微信 订单 收入
		Double orderWxAmountTest = 0d;
		if (orderWxAmountWhole != 0.0) {
			orderWxAmountTest = ECNumberUtils.roundDoubleWithScale(orderWxAmountWhole - orderWxAmount, 2);
		}

		money.setOrderWxAmountTest(orderWxAmountTest);
		// 测试 微信 订单 手续费
		Double orderWxChargeTest = 0d;
		if (money.getOrderWxChargeWhole() != 0.0) {
			orderWxChargeTest = ECNumberUtils
					.roundDoubleWithScale(money.getOrderWxChargeWhole() - money.getOrderWxCharge(), 2);
		}

		money.setOrderWxChargeTest(orderWxChargeTest);
		// 测试 微信 套餐 收入
		Double priceWxAmountTest = 0d;
		if (priceWxAmountWhole != 0.0) {
			priceWxAmountTest = ECNumberUtils.roundDoubleWithScale(priceWxAmountWhole - priceWxAmount, 2);
		}

		money.setPriceWxAmountTest(priceWxAmountTest);
		// 测试 微信 套餐 手续费
		Double priceWxChargeTest = 0d;
		if (money.getPriceWxChargeWhole() != 0.0) {
			priceWxChargeTest = ECNumberUtils
					.roundDoubleWithScale(money.getPriceWxChargeWhole() - money.getPriceWxCharge(), 2);
		}

		money.setPriceWxChargeTest(priceWxChargeTest);
		// 测试微信 手续费返还
		if (money.getChargeRefundWhole() != 0.0) {
			money.setChargeRefundTest(
					ECNumberUtils.roundDoubleWithScale(money.getChargeRefundWhole() - money.getChargeRefund(), 2));
		} else {
			money.setChargeRefundTest(0d);
		}

		// 测试 微信合计
		Double aswWx = 0d;
		if (awholeWx != 0.0) {

			aswWx = ECNumberUtils.roundDoubleWithScale(awholeWx - wxAmount, 2);
		} else {

			aswWx = ECNumberUtils.roundDoubleWithScale(0, 2);
		}
		money.setAswWx(aswWx);

		ResultInfo<Money> resultInfo = new ResultInfo<Money>();
		resultInfo.setCode(Constant.SECCUESS);
		resultInfo.setData(money);
		return resultInfo;
	}

	// 财务场站汇总信息
	@Override
	public PageFinder<ParkRpts> getParkRptsPagedList(Query q) {
		PageFinder<ParkRpts> page = new PageFinder<ParkRpts>();
		List<ParkRpts> list = new ArrayList<ParkRpts>();
		long rowCount = 1L;

		try {
			// 调用dao查询满足条件的分页数据
			list = orderDao.pageListParkRpts(q);
			// 调用dao统计满足条件的记录总数
			// rowCount = orderDao.countSummary(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		// 如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<ParkRpts>(0) : list;

		// 将分页数据和记录总数设置到分页结果对象中
		page.setData(list);
		page.setRowCount(rowCount);
		return page;

	}

	// 导出场站
	@Override
	public List<ParkRpts> getParkRptsList(ParkRpts parkRpts) {
		List<ParkRpts> list = new ArrayList<ParkRpts>();

		try {
			// 调用dao查询满足条件的分页数据
			list = orderDao.getParkRptsList(parkRpts);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		// 如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<ParkRpts>(0) : list;
		return list;
	}

	/*
	 * 财务车辆信息展示
	 * 
	 */
	@Override
	public PageFinder<CarRpts> getCarRptsPagedList(Query q) {
		PageFinder<CarRpts> page = new PageFinder<CarRpts>();
		List<CarRpts> list = new ArrayList<CarRpts>();
		long rowCount = 1L;

		try {
			// 调用dao查询满足条件的分页数据
			list = orderDao.pageListCarRpts(q);
			// 调用dao统计满足条件的记录总数
			// rowCount = orderDao.countSummary(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		// 如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<CarRpts>(0) : list;

		// 将分页数据和记录总数设置到分页结果对象中
		page.setData(list);
		page.setRowCount(rowCount);
		return page;
	}

	// 财务信息 车辆 汇总 导出
	@Override
	public List<CarRpts> getCarRptsList(CarRpts carRpts) {
		List<CarRpts> list = new ArrayList<CarRpts>();

		try {
			// 调用dao查询满足条件的分页数据
			list = orderDao.getCarRptsList(carRpts);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		// 如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<CarRpts>(0) : list;
		return list;
	}

	// 财务集团 信息
	@Override
	public PageFinder<GroupRpts> getGroupRptsPagedList(Query q) {
		PageFinder<GroupRpts> page = new PageFinder<GroupRpts>();
		List<GroupRpts> list = new ArrayList<GroupRpts>();
		long rowCount = 1L;

		try {
			// 调用dao查询满足条件的分页数据
			list = orderDao.pageListGroupRpts(q);
			// 调用dao统计满足条件的记录总数
			// rowCount = orderDao.countSummary(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		// 如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<GroupRpts>(0) : list;

		// 将分页数据和记录总数设置到分页结果对象中
		page.setData(list);
		page.setRowCount(rowCount);
		return page;
	}

	// 财务报表 集团 导出
	@Override
	public List<GroupRpts> getGroupRptsList(GroupRpts groupRpts) {
		List<GroupRpts> list = new ArrayList<GroupRpts>();

		try {
			// 调用dao查询满足条件的分页数据
			list = orderDao.getGroupRptsList(groupRpts);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		// 如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<GroupRpts>(0) : list;
		return list;
	}

	@Override
	public Order getOrderNewestByCarNo(String carNo) {
		return orderDao.getOrderNewestByCarNo(carNo);
	}

	/**
	 * 根据请求时间查询出订单的相关数据
	 * 
	 * @param startTime
	 *            当天开始时间 0分0秒
	 * @param endTime
	 *            请求时间
	 * @param open
	 *            是否开启查询隐私数据 1 开启 0 关闭
	 * @return Map<String, Integer>
	 */
	@Override
	public Map<String, Integer> getIndexValue(String startTime, String endTime, int i) {
		Map<String, Integer> result = new HashMap<String, Integer>();
		// 查询出指定时间段内的全部订单
		List<Order> orders = orderDao.getNowDayAllOrder(startTime, endTime);
		if (null != orders && orders.size() > 0) {
			// 已取消的订单
			List<Order> escOrders = new ArrayList<Order>();
			// 已结束的订单
			List<Order> finOrders = new ArrayList<Order>();
			// 已支付的订单
			List<Order> payedOrders = new ArrayList<Order>();
			for (Order order : orders) {
				if (4 == order.getOrderStatus()) {
					escOrders.add(order);
				} else if (3 == order.getOrderStatus() && 0 == order.getPayStatus()) {
					finOrders.add(order);
				} else if (3 == order.getOrderStatus() && 1 == order.getPayStatus()) {
					payedOrders.add(order);
				}
			}
			// 取消订单
			result.put(OrderConstant.ESC_ORDER, escOrders.size());
			// 结束订单
			result.put(OrderConstant.FIN_ORDER, finOrders.size());
			// 支付订单
			result.put(OrderConstant.PAY_ORDER, payedOrders.size());
		}
		// 返回集合中当日全部新增的订单
		result.put(OrderConstant.NEW_ORDER, orders.size());
		return result;
	}

	/**
	 * 订单运营数据统计-日
	 * 
	 * @param endTime
	 * @param dayParmaeter
	 * @return
	 */
	@Override
	public OrderCountVo orderOperateCount(Date startTime1, Date endTime, int dayParmaeter, List<String> list) {
		// TODO Auto-generated method stub
		OrderCountVo orderCountVo = new OrderCountVo();

		String startTimejs = ECDateUtils.getSpecifiedDayBefore(endTime, dayParmaeter);

		SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		c.setTime(endTime);
		int day = c.get(Calendar.DATE);
		c.set(Calendar.DATE, day + 1);

		String endTimejs = dft.format(c.getTime());

		dayParmaeter += 1;

		List<String> orderlogcton = new ArrayList<String>();

		if (list.size() > 0) {
			orderlogcton = list;
		} else {
			orderlogcton = orderDao.getorderCount(startTimejs, endTimejs);
		}

		String option = " ";
		String option2 = " ";

		String optionMoney = "";
		String optionMoneyzj = "";

		option += "   		{    ";
		option += "   			    \"title\": {    ";
		option += "   		        \"text\": \"\"    ";
		option += "   			    },    ";
		option += "   			    \"tooltip\": {    ";
		option += "   		        \"trigger\": \"axis\"    ";
		option += "   			    },     ";
		option += "   			    \"legend\": {    ";
		option += "  \"data\":[ ";

		optionMoney += "   		{    ";
		optionMoney += "   			    \"title\": {    ";
		optionMoney += "   		        \"text\": \"\"    ";
		optionMoney += "   			    },    ";
		optionMoney += "   			    \"tooltip\": {    ";
		optionMoney += "   		        \"trigger\": \"axis\"    ";
		optionMoney += "   			    },     ";
		optionMoney += "   			    \"legend\": {    ";
		optionMoney += "  \"data\":[ ";

		optionMoneyzj += "   		{    ";
		optionMoneyzj += "   			    \"title\": {    ";
		optionMoneyzj += "   		        \"text\": \"\"    ";
		optionMoneyzj += "   			    },    ";
		optionMoneyzj += "   			    \"tooltip\": {    ";
		optionMoneyzj += "   		        \"trigger\": \"axis\"    ";
		optionMoneyzj += "   			    },     ";
		optionMoneyzj += "   			    \"legend\": {    ";
		optionMoneyzj += "  \"data\":[ ";

		for (int j = 0; j < orderlogcton.size(); j++) {
			option += "  \"" + orderlogcton.get(j) + "\"";
			optionMoney += "  \"" + orderlogcton.get(j) + "\"";
			optionMoneyzj += "  \"" + orderlogcton.get(j) + "\"";
			if (j + 1 < orderlogcton.size()) {
				option += "  , ";
				optionMoney += "  , ";
				optionMoneyzj += "  , ";
			}
		}
		option += "  ]    ";
		optionMoney += "  ]    ";
		optionMoneyzj += "  ]    ";

		option += "   			    },    ";
		option += "   		    \"grid\": {    ";
		option += "   			        \"left\": \"3%\",    ";
		option += "   		        \"right\": \"4%\",    ";
		option += "   			        \"bottom\": \"3%\",    ";
		option += "   		        \"containLabel\": true    ";
		option += "   		    },    ";
		option += "   		    \"toolbox\": {    ";
		option += "   		        \"feature\": {    ";
		option += "   		            \"saveAsImage\": {}    ";
		option += "   		        }    ";
		option += "   		    },    ";

		optionMoney += "   			    },    ";
		optionMoney += "   		    \"grid\": {    ";
		optionMoney += "   			        \"left\": \"3%\",    ";
		optionMoney += "   		        \"right\": \"4%\",    ";
		optionMoney += "   			        \"bottom\": \"3%\",    ";
		optionMoney += "   		        \"containLabel\": true    ";
		optionMoney += "   		    },    ";
		optionMoney += "   		    \"toolbox\": {    ";
		optionMoney += "   		        \"feature\": {    ";
		optionMoney += "   		            \"saveAsImage\": {}    ";
		optionMoney += "   		        }    ";
		optionMoney += "   		    },    ";

		optionMoneyzj += "   			    },    ";
		optionMoneyzj += "   		    \"grid\": {    ";
		optionMoneyzj += "   			        \"left\": \"3%\",    ";
		optionMoneyzj += "   		        \"right\": \"4%\",    ";
		optionMoneyzj += "   			        \"bottom\": \"3%\",    ";
		optionMoneyzj += "   		        \"containLabel\": true    ";
		optionMoneyzj += "   		    },    ";
		optionMoneyzj += "   		    \"toolbox\": {    ";
		optionMoneyzj += "   		        \"feature\": {    ";
		optionMoneyzj += "   		            \"saveAsImage\": {}    ";
		optionMoneyzj += "   		        }    ";
		optionMoneyzj += "   		    },    ";

		option2 += "   		{    ";
		option2 += "   			    \"title\": {    ";
		option2 += "   		        \"text\": \"\"    ";
		option2 += "   			    },    ";
		option2 += "   			    \"tooltip\": {    ";
		option2 += "   		        \"trigger\": \"axis\"    ";
		option2 += "   			    },     ";
		option2 += "   			    \"legend\": {    ";

		option2 += "  \"data\":[ ";

		for (int j = 0; j < orderlogcton.size(); j++) {
			option2 += "  \"" + orderlogcton.get(j) + "\"";
			if (j + 1 < orderlogcton.size()) {
				option2 += "  , ";
			}
		}
		option2 += "  ]    ";

		option2 += "   			    },    ";
		option2 += "   		    \"grid\": {    ";
		option2 += "   			        \"left\": \"3%\",    ";
		option2 += "   		        \"right\": \"4%\",    ";
		option2 += "   			        \"bottom\": \"3%\",    ";
		option2 += "   		        \"containLabel\": true    ";
		option2 += "   		    },    ";
		option2 += "   		    \"toolbox\": {    ";
		option2 += "   		        \"feature\": {    ";
		option2 += "   		            \"saveAsImage\": {}    ";
		option2 += "   		        }    ";
		option2 += "   		    },    ";

		option += "   		   \"xAxis\": {    ";
		option += "   		        \"type\": \"category\",    ";
		option += "   		        \"boundaryGap\": false,    ";
		option += "  \"data\":[ ";

		option2 += "   		   \"xAxis\": {    ";
		option2 += "   		        \"type\": \"category\",    ";
		option2 += "   		        \"boundaryGap\": false,    ";
		option2 += "  \"data\":[ ";

		optionMoney += "   		   \"xAxis\": {    ";
		optionMoney += "   		        \"type\": \"category\",    ";
		optionMoney += "   		        \"boundaryGap\": false,    ";
		optionMoney += "  \"data\":[ ";

		optionMoneyzj += "   		   \"xAxis\": {    ";
		optionMoneyzj += "   		        \"type\": \"category\",    ";
		optionMoneyzj += "   		        \"boundaryGap\": false,    ";
		optionMoneyzj += "  \"data\":[ ";

		for (int i = dayParmaeter; i > 0; i--) {
			String startTime = ECDateUtils.getSpecifiedDayBefore(endTime, i - 1);
			option += "   			         \"" + startTime + "\"    ";
			option2 += "   			         \"" + startTime + "\"    ";
			optionMoney += "   			         \"" + startTime + "\"    ";
			optionMoneyzj += "   			         \"" + startTime + "\"    ";
			if (i > 1) {
				option += "  , ";
				option2 += "  , ";
				optionMoney += "  , ";
				optionMoneyzj += "  , ";
			}
		}
		option += "  ]    ";
		option2 += "  ]    ";
		optionMoney += "  ]    ";
		optionMoneyzj += "  ]    ";

		option += "   		    },    ";
		option += "   		    \"yAxis\": {    ";
		option += "   		        \"type\": \"value\"    ";
		option += "   		    },    ";
		option += "   			    \"series\": [    ";

		option2 += "   		    },    ";
		option2 += "   		    \"yAxis\": {    ";
		option2 += "   		        \"type\": \"value\"    ";
		option2 += "   		    },    ";
		option2 += "   			    \"series\": [    ";

		optionMoney += "   		    },    ";
		optionMoney += "   		    \"yAxis\": {    ";
		optionMoney += "   		        \"type\": \"value\"    ";
		optionMoney += "   		    },    ";
		optionMoney += "   			    \"series\": [    ";

		optionMoneyzj += "   		    },    ";
		optionMoneyzj += "   		    \"yAxis\": {    ";
		optionMoneyzj += "   		        \"type\": \"value\"    ";
		optionMoneyzj += "   		    },    ";
		optionMoneyzj += "   			    \"series\": [    ";

		for (int j = 0; j < orderlogcton.size(); j++) {
			option += "   		        {    ";
			option += "   		            \"name\":\"" + orderlogcton.get(j) + "\",    ";
			option += "   		            \"type\":\"line\",    ";
			option += "   		            \"stack\": \"总量\",    ";
			option += "  \"data\":[ ";

			option2 += "   		        {    ";
			option2 += "   		            \"name\":\"" + orderlogcton.get(j) + "\",    ";
			option2 += "   		            \"type\":\"line\",    ";
			option2 += "   		            \"stack\": \"总量\",    ";
			option2 += "  \"data\":[ ";

			optionMoney += "   		        {    ";
			optionMoney += "   		            \"name\":\"" + orderlogcton.get(j) + "\",    ";
			optionMoney += "   		            \"type\":\"line\",    ";
			optionMoney += "   		            \"stack\": \"总量\",    ";
			optionMoney += "  \"data\":[ ";

			optionMoneyzj += "   		        {    ";
			optionMoneyzj += "   		            \"name\":\"" + orderlogcton.get(j) + "\",    ";
			optionMoneyzj += "   		            \"type\":\"line\",    ";
			optionMoneyzj += "   		            \"stack\": \"总量\",    ";
			optionMoneyzj += "  \"data\":[ ";

			for (int i = dayParmaeter; i > 0; i--) {
				Integer count = 0;
				Integer countzj = 0;
				Integer orderMoney = 0;
				Integer orderMoneyzj = 0;

				// 获取查询开始日期
				String startTime = ECDateUtils.getSpecifiedDayBefore(endTime, i - 1);

				if (i == 1) {
					count = orderDao.getorderCountlogctonList(startTime, endTimejs, orderlogcton.get(j));
					countzj = orderDao.getorderCountlogctonList(startTimejs, endTimejs, orderlogcton.get(j));

					orderMoney = orderDao.getorderMoneyCountlogctonList(startTime, endTimejs, orderlogcton.get(j));
					orderMoneyzj = orderDao.getorderMoneyCountlogctonList(startTimejs, endTimejs, orderlogcton.get(j));

				} else {
					count = orderDao.getorderCountlogctonList(startTime,
							ECDateUtils.getSpecifiedDayBefore(endTime, i - 2), orderlogcton.get(j));

					orderMoney = orderDao.getorderMoneyCountlogctonList(startTime,
							ECDateUtils.getSpecifiedDayBefore(endTime, i - 2), orderlogcton.get(j));

					if (i == dayParmaeter) {
						countzj = orderDao.getorderCountlogctonList(startTime,
								ECDateUtils.getSpecifiedDayBefore(endTime, i - 2), orderlogcton.get(j));
						orderMoneyzj = orderDao.getorderMoneyCountlogctonList(startTime,
								ECDateUtils.getSpecifiedDayBefore(endTime, i - 2), orderlogcton.get(j));
					} else {
						countzj = orderDao.getorderCountlogctonList(startTimejs,
								ECDateUtils.getSpecifiedDayBefore(endTime, i - 2), orderlogcton.get(j));
						orderMoneyzj = orderDao.getorderMoneyCountlogctonList(startTimejs,
								ECDateUtils.getSpecifiedDayBefore(endTime, i - 2), orderlogcton.get(j));
					}

				}

				if (count == null) {
					count = 0;
				}
				if (countzj == null) {
					countzj = 0;
				}

				if (orderMoney == null) {
					orderMoney = 0;
				}
				if (orderMoneyzj == null) {
					orderMoneyzj = 0;
				}
				option += count;
				option2 += countzj;
				optionMoney += orderMoney;
				optionMoneyzj += orderMoneyzj;
				if (i > 1) {
					option += "  , ";
					option2 += "  , ";
					optionMoney += "  , ";
					optionMoneyzj += "  , ";
				}

			}

			option += "  ]    ";
			option2 += "  ]    ";

			option += "   }    ";
			option2 += "   }    ";

			optionMoney += "  ]    ";
			optionMoneyzj += "  ]    ";

			optionMoney += "   }    ";
			optionMoneyzj += "   }    ";

			if (j + 1 < orderlogcton.size()) {
				option += "  , ";
				option2 += "  , ";
				optionMoney += "  , ";
				optionMoneyzj += "  , ";
			}
		}

		option += "   		    ]    ";
		option += "   		}    ";

		option2 += "   		    ]    ";
		option2 += "   		}    ";

		optionMoney += "   		    ]    ";
		optionMoney += "   		}    ";

		optionMoneyzj += "   		    ]    ";
		optionMoneyzj += "   		}    ";

		orderCountVo.setOption(option);
		orderCountVo.setOption2(option2);

		orderCountVo.setOptionMoney(optionMoney);
		orderCountVo.setOptionMoney2(optionMoneyzj);

		return orderCountVo;
	}

	/**
	 * 订单运营数据统计-周
	 * 
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	@Override
	public OrderCountVo weekOrderOperateCount(Date startTime1, Date endTime, int weekParmaeter, List<String> list) {
		// TODO Auto-generated method stub
		OrderCountVo orderCountVo = new OrderCountVo();

		String startTimejs = ECDateUtils.getSpecifiedWeek(endTime, weekParmaeter);

		SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		c.setTime(endTime);
		int day = c.get(Calendar.DATE);
		c.set(Calendar.DATE, day * 7);

		String endTimejs = dft.format(c.getTime());

		List<String> orderlogcton = new ArrayList<String>();

		if (list.size() > 0) {
			orderlogcton = list;
		} else {
			orderlogcton = orderDao.getorderCount(startTimejs, endTimejs);
		}

		String option = " ";
		String option2 = " ";

		String optionMoney = "";
		String optionMoneyzj = "";

		option += "   		{    ";
		option += "   			    \"title\": {    ";
		option += "   		        \"text\": \"\"    ";
		option += "   			    },    ";
		option += "   			    \"tooltip\": {    ";
		option += "   		        \"trigger\": \"axis\"    ";
		option += "   			    },     ";
		option += "   			    \"legend\": {    ";
		option += "  \"data\":[ ";

		optionMoney += "   		{    ";
		optionMoney += "   			    \"title\": {    ";
		optionMoney += "   		        \"text\": \"\"    ";
		optionMoney += "   			    },    ";
		optionMoney += "   			    \"tooltip\": {    ";
		optionMoney += "   		        \"trigger\": \"axis\"    ";
		optionMoney += "   			    },     ";
		optionMoney += "   			    \"legend\": {    ";
		optionMoney += "  \"data\":[ ";

		optionMoneyzj += "   		{    ";
		optionMoneyzj += "   			    \"title\": {    ";
		optionMoneyzj += "   		        \"text\": \"\"    ";
		optionMoneyzj += "   			    },    ";
		optionMoneyzj += "   			    \"tooltip\": {    ";
		optionMoneyzj += "   		        \"trigger\": \"axis\"    ";
		optionMoneyzj += "   			    },     ";
		optionMoneyzj += "   			    \"legend\": {    ";
		optionMoneyzj += "  \"data\":[ ";

		for (int j = 0; j < orderlogcton.size(); j++) {
			option += "  \"" + orderlogcton.get(j) + "\"";
			optionMoney += "  \"" + orderlogcton.get(j) + "\"";
			optionMoneyzj += "  \"" + orderlogcton.get(j) + "\"";
			if (j + 1 < orderlogcton.size()) {
				option += "  , ";
				optionMoney += "  , ";
				optionMoneyzj += "  , ";
			}
		}
		option += "  ]    ";
		optionMoney += "  ]    ";
		optionMoneyzj += "  ]    ";

		option += "   			    },    ";
		option += "   		    \"grid\": {    ";
		option += "   			        \"left\": \"3%\",    ";
		option += "   		        \"right\": \"4%\",    ";
		option += "   			        \"bottom\": \"3%\",    ";
		option += "   		        \"containLabel\": true    ";
		option += "   		    },    ";
		option += "   		    \"toolbox\": {    ";
		option += "   		        \"feature\": {    ";
		option += "   		            \"saveAsImage\": {}    ";
		option += "   		        }    ";
		option += "   		    },    ";

		optionMoney += "   			    },    ";
		optionMoney += "   		    \"grid\": {    ";
		optionMoney += "   			        \"left\": \"3%\",    ";
		optionMoney += "   		        \"right\": \"4%\",    ";
		optionMoney += "   			        \"bottom\": \"3%\",    ";
		optionMoney += "   		        \"containLabel\": true    ";
		optionMoney += "   		    },    ";
		optionMoney += "   		    \"toolbox\": {    ";
		optionMoney += "   		        \"feature\": {    ";
		optionMoney += "   		            \"saveAsImage\": {}    ";
		optionMoney += "   		        }    ";
		optionMoney += "   		    },    ";

		optionMoneyzj += "   			    },    ";
		optionMoneyzj += "   		    \"grid\": {    ";
		optionMoneyzj += "   			        \"left\": \"3%\",    ";
		optionMoneyzj += "   		        \"right\": \"4%\",    ";
		optionMoneyzj += "   			        \"bottom\": \"3%\",    ";
		optionMoneyzj += "   		        \"containLabel\": true    ";
		optionMoneyzj += "   		    },    ";
		optionMoneyzj += "   		    \"toolbox\": {    ";
		optionMoneyzj += "   		        \"feature\": {    ";
		optionMoneyzj += "   		            \"saveAsImage\": {}    ";
		optionMoneyzj += "   		        }    ";
		optionMoneyzj += "   		    },    ";

		option2 += "   		{    ";
		option2 += "   			    \"title\": {    ";
		option2 += "   		        \"text\": \"\"    ";
		option2 += "   			    },    ";
		option2 += "   			    \"tooltip\": {    ";
		option2 += "   		        \"trigger\": \"axis\"    ";
		option2 += "   			    },     ";
		option2 += "   			    \"legend\": {    ";

		option2 += "  \"data\":[ ";

		for (int j = 0; j < orderlogcton.size(); j++) {
			option2 += "  \"" + orderlogcton.get(j) + "\"";
			if (j + 1 < orderlogcton.size()) {
				option2 += "  , ";
			}
		}
		option2 += "  ]    ";

		option2 += "   			    },    ";
		option2 += "   		    \"grid\": {    ";
		option2 += "   			        \"left\": \"3%\",    ";
		option2 += "   		        \"right\": \"4%\",    ";
		option2 += "   			        \"bottom\": \"3%\",    ";
		option2 += "   		        \"containLabel\": true    ";
		option2 += "   		    },    ";
		option2 += "   		    \"toolbox\": {    ";
		option2 += "   		        \"feature\": {    ";
		option2 += "   		            \"saveAsImage\": {}    ";
		option2 += "   		        }    ";
		option2 += "   		    },    ";

		option += "   		   \"xAxis\": {    ";
		option += "   		        \"type\": \"category\",    ";
		option += "   		        \"boundaryGap\": false,    ";
		option += "  \"data\":[ ";

		option2 += "   		   \"xAxis\": {    ";
		option2 += "   		        \"type\": \"category\",    ";
		option2 += "   		        \"boundaryGap\": false,    ";
		option2 += "  \"data\":[ ";

		optionMoney += "   		   \"xAxis\": {    ";
		optionMoney += "   		        \"type\": \"category\",    ";
		optionMoney += "   		        \"boundaryGap\": false,    ";
		optionMoney += "  \"data\":[ ";

		optionMoneyzj += "   		   \"xAxis\": {    ";
		optionMoneyzj += "   		        \"type\": \"category\",    ";
		optionMoneyzj += "   		        \"boundaryGap\": false,    ";
		optionMoneyzj += "  \"data\":[ ";

		for (int i = weekParmaeter; i > 0; i--) {
			String startTime = ECDateUtils.getSpecifiedWeek(endTime, i);
			String startTime2 = ECDateUtils.getSpecifiedWeek(endTime, i - 1);

			option += "   			         \"" + ECDateUtils.getSpecifiedWeek(endTime, i - 1) + "\"    ";
			option2 += "   			         \"" + ECDateUtils.getSpecifiedWeek(endTime, i - 1) + "\"    ";
			optionMoney += "   			         \"" + ECDateUtils.getSpecifiedWeek(endTime, i - 1) + "\"    ";
			optionMoneyzj += "   			         \"" + ECDateUtils.getSpecifiedWeek(endTime, i - 1) + "\"    ";
			if (i > 1) {
				option += "  , ";
				option2 += "  , ";
				optionMoney += "  , ";
				optionMoneyzj += "  , ";
			}
		}
		option += "  ]    ";
		option2 += "  ]    ";
		optionMoney += "  ]    ";
		optionMoneyzj += "  ]    ";

		option += "   		    },    ";
		option += "   		    \"yAxis\": {    ";
		option += "   		        \"type\": \"value\"    ";
		option += "   		    },    ";
		option += "   			    \"series\": [    ";

		option2 += "   		    },    ";
		option2 += "   		    \"yAxis\": {    ";
		option2 += "   		        \"type\": \"value\"    ";
		option2 += "   		    },    ";
		option2 += "   			    \"series\": [    ";

		optionMoney += "   		    },    ";
		optionMoney += "   		    \"yAxis\": {    ";
		optionMoney += "   		        \"type\": \"value\"    ";
		optionMoney += "   		    },    ";
		optionMoney += "   			    \"series\": [    ";

		optionMoneyzj += "   		    },    ";
		optionMoneyzj += "   		    \"yAxis\": {    ";
		optionMoneyzj += "   		        \"type\": \"value\"    ";
		optionMoneyzj += "   		    },    ";
		optionMoneyzj += "   			    \"series\": [    ";

		for (int j = 0; j < orderlogcton.size(); j++) {
			option += "   		        {    ";
			option += "   		            \"name\":\"" + orderlogcton.get(j) + "\",    ";
			option += "   		            \"type\":\"line\",    ";
			option += "   		            \"stack\": \"总量\",    ";
			option += "  \"data\":[ ";

			option2 += "   		        {    ";
			option2 += "   		            \"name\":\"" + orderlogcton.get(j) + "\",    ";
			option2 += "   		            \"type\":\"line\",    ";
			option2 += "   		            \"stack\": \"总量\",    ";
			option2 += "  \"data\":[ ";

			optionMoney += "   		        {    ";
			optionMoney += "   		            \"name\":\"" + orderlogcton.get(j) + "\",    ";
			optionMoney += "   		            \"type\":\"line\",    ";
			optionMoney += "   		            \"stack\": \"总量\",    ";
			optionMoney += "  \"data\":[ ";

			optionMoneyzj += "   		        {    ";
			optionMoneyzj += "   		            \"name\":\"" + orderlogcton.get(j) + "\",    ";
			optionMoneyzj += "   		            \"type\":\"line\",    ";
			optionMoneyzj += "   		            \"stack\": \"总量\",    ";
			optionMoneyzj += "  \"data\":[ ";

			for (int i = weekParmaeter; i > 0; i--) {
				Integer count = 0;
				Integer countzj = 0;
				Integer orderMoney = 0;
				Integer orderMoneyzj = 0;

				// 获取查询开始日期
				String startTime = ECDateUtils.getSpecifiedWeek(endTime, i);

				if (i == 1) {
					count = orderDao.getorderCountlogctonList(startTime, endTimejs, orderlogcton.get(j));
					countzj = orderDao.getorderCountlogctonList(startTimejs, endTimejs, orderlogcton.get(j));

					orderMoney = orderDao.getorderMoneyCountlogctonList(startTime, endTimejs, orderlogcton.get(j));
					orderMoneyzj = orderDao.getorderMoneyCountlogctonList(startTimejs, endTimejs, orderlogcton.get(j));

				} else {
					count = orderDao.getorderCountlogctonList(startTime, ECDateUtils.getSpecifiedWeek(endTime, i - 1),
							orderlogcton.get(j));

					orderMoney = orderDao.getorderMoneyCountlogctonList(startTime,
							ECDateUtils.getSpecifiedWeek(endTime, i - 1), orderlogcton.get(j));

					if (i == weekParmaeter) {
						countzj = orderDao.getorderCountlogctonList(startTime,
								ECDateUtils.getSpecifiedWeek(endTime, i - 1), orderlogcton.get(j));
						orderMoneyzj = orderDao.getorderMoneyCountlogctonList(startTime,
								ECDateUtils.getSpecifiedWeek(endTime, i - 1), orderlogcton.get(j));
					} else {
						countzj = orderDao.getorderCountlogctonList(startTimejs,
								ECDateUtils.getSpecifiedWeek(endTime, i - 1), orderlogcton.get(j));
						orderMoneyzj = orderDao.getorderMoneyCountlogctonList(startTimejs,
								ECDateUtils.getSpecifiedWeek(endTime, i - 1), orderlogcton.get(j));
					}

				}

				if (count == null) {
					count = 0;
				}
				if (countzj == null) {
					countzj = 0;
				}

				if (orderMoney == null) {
					orderMoney = 0;
				}
				if (orderMoneyzj == null) {
					orderMoneyzj = 0;
				}
				option += count;
				option2 += countzj;
				optionMoney += orderMoney;
				optionMoneyzj += orderMoneyzj;
				if (i > 1) {
					option += "  , ";
					option2 += "  , ";
					optionMoney += "  , ";
					optionMoneyzj += "  , ";
				}

			}

			option += "  ]    ";
			option2 += "  ]    ";

			option += "   }    ";
			option2 += "   }    ";

			optionMoney += "  ]    ";
			optionMoneyzj += "  ]    ";

			optionMoney += "   }    ";
			optionMoneyzj += "   }    ";

			if (j + 1 < orderlogcton.size()) {
				option += "  , ";
				option2 += "  , ";
				optionMoney += "  , ";
				optionMoneyzj += "  , ";
			}
		}

		option += "   		    ]    ";
		option += "   		}    ";

		option2 += "   		    ]    ";
		option2 += "   		}    ";

		optionMoney += "   		    ]    ";
		optionMoney += "   		}    ";

		optionMoneyzj += "   		    ]    ";
		optionMoneyzj += "   		}    ";

		orderCountVo.setOption(option);
		orderCountVo.setOption2(option2);

		orderCountVo.setOptionMoney(optionMoney);
		orderCountVo.setOptionMoney2(optionMoneyzj);

		return orderCountVo;
	}

	/**
	 * 订单运营数据统计-月
	 * 
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	@Override
	public OrderCountVo monthOrderOperateCount(Date startTime1, Date endTime, int memberParmaeter, List<String> list) {
		// TODO Auto-generated method stub
		OrderCountVo orderCountVo = new OrderCountVo();

		SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM");
		String startTimejs = dft.format(startTime1);

		Calendar c = Calendar.getInstance();
		c.setTime(endTime);
		int month = c.get(Calendar.MONTH);
		c.set(Calendar.MONTH, month + 1);

		String endTimejs = dft.format(c.getTime());

		List<String> orderlogcton = new ArrayList<String>();

		if (list.size() > 0) {
			orderlogcton = list;
		} else {
			orderlogcton = orderDao.getorderCount(startTimejs, endTimejs);
		}

		String option = " ";
		String option2 = " ";
		String optionMoney = "";
		String optionMoneyzj = "";

		option += "   		{    ";
		option += "   			    \"title\": {    ";
		option += "   		        \"text\": \"\"    ";
		option += "   			    },    ";
		option += "   			    \"tooltip\": {    ";
		option += "   		        \"trigger\": \"axis\"    ";
		option += "   			    },     ";
		option += "   			    \"legend\": {    ";
		option += "  \"data\":[ ";

		optionMoney += "   		{    ";
		optionMoney += "   			    \"title\": {    ";
		optionMoney += "   		        \"text\": \"\"    ";
		optionMoney += "   			    },    ";
		optionMoney += "   			    \"tooltip\": {    ";
		optionMoney += "   		        \"trigger\": \"axis\"    ";
		optionMoney += "   			    },     ";
		optionMoney += "   			    \"legend\": {    ";
		optionMoney += "  \"data\":[ ";

		optionMoneyzj += "   		{    ";
		optionMoneyzj += "   			    \"title\": {    ";
		optionMoneyzj += "   		        \"text\": \"\"    ";
		optionMoneyzj += "   			    },    ";
		optionMoneyzj += "   			    \"tooltip\": {    ";
		optionMoneyzj += "   		        \"trigger\": \"axis\"    ";
		optionMoneyzj += "   			    },     ";
		optionMoneyzj += "   			    \"legend\": {    ";
		optionMoneyzj += "  \"data\":[ ";

		for (int j = 0; j < orderlogcton.size(); j++) {
			option += "  \"" + orderlogcton.get(j) + "\"";
			optionMoney += "  \"" + orderlogcton.get(j) + "\"";
			optionMoneyzj += "  \"" + orderlogcton.get(j) + "\"";
			if (j + 1 < orderlogcton.size()) {
				option += "  , ";
				optionMoney += "  , ";
				optionMoneyzj += "  , ";
			}
		}
		option += "  ]    ";
		optionMoney += "  ]    ";
		optionMoneyzj += "  ]    ";

		option += "   			    },    ";
		option += "   		    \"grid\": {    ";
		option += "   			        \"left\": \"3%\",    ";
		option += "   		        \"right\": \"4%\",    ";
		option += "   			        \"bottom\": \"3%\",    ";
		option += "   		        \"containLabel\": true    ";
		option += "   		    },    ";
		option += "   		    \"toolbox\": {    ";
		option += "   		        \"feature\": {    ";
		option += "   		            \"saveAsImage\": {}    ";
		option += "   		        }    ";
		option += "   		    },    ";

		optionMoney += "   			    },    ";
		optionMoney += "   		    \"grid\": {    ";
		optionMoney += "   			        \"left\": \"3%\",    ";
		optionMoney += "   		        \"right\": \"4%\",    ";
		optionMoney += "   			        \"bottom\": \"3%\",    ";
		optionMoney += "   		        \"containLabel\": true    ";
		optionMoney += "   		    },    ";
		optionMoney += "   		    \"toolbox\": {    ";
		optionMoney += "   		        \"feature\": {    ";
		optionMoney += "   		            \"saveAsImage\": {}    ";
		optionMoney += "   		        }    ";
		optionMoney += "   		    },    ";

		optionMoneyzj += "   			    },    ";
		optionMoneyzj += "   		    \"grid\": {    ";
		optionMoneyzj += "   			        \"left\": \"3%\",    ";
		optionMoneyzj += "   		        \"right\": \"4%\",    ";
		optionMoneyzj += "   			        \"bottom\": \"3%\",    ";
		optionMoneyzj += "   		        \"containLabel\": true    ";
		optionMoneyzj += "   		    },    ";
		optionMoneyzj += "   		    \"toolbox\": {    ";
		optionMoneyzj += "   		        \"feature\": {    ";
		optionMoneyzj += "   		            \"saveAsImage\": {}    ";
		optionMoneyzj += "   		        }    ";
		optionMoneyzj += "   		    },    ";

		option2 += "   		{    ";
		option2 += "   			    \"title\": {    ";
		option2 += "   		        \"text\": \"\"    ";
		option2 += "   			    },    ";
		option2 += "   			    \"tooltip\": {    ";
		option2 += "   		        \"trigger\": \"axis\"    ";
		option2 += "   			    },     ";
		option2 += "   			    \"legend\": {    ";

		option2 += "  \"data\":[ ";

		for (int j = 0; j < orderlogcton.size(); j++) {
			option2 += "  \"" + orderlogcton.get(j) + "\"";
			if (j + 1 < orderlogcton.size()) {
				option2 += "  , ";
			}
		}
		option2 += "  ]    ";

		option2 += "   			    },    ";
		option2 += "   		    \"grid\": {    ";
		option2 += "   			        \"left\": \"3%\",    ";
		option2 += "   		        \"right\": \"4%\",    ";
		option2 += "   			        \"bottom\": \"3%\",    ";
		option2 += "   		        \"containLabel\": true    ";
		option2 += "   		    },    ";
		option2 += "   		    \"toolbox\": {    ";
		option2 += "   		        \"feature\": {    ";
		option2 += "   		            \"saveAsImage\": {}    ";
		option2 += "   		        }    ";
		option2 += "   		    },    ";

		option += "   		   \"xAxis\": {    ";
		option += "   		        \"type\": \"category\",    ";
		option += "   		        \"boundaryGap\": false,    ";
		option += "  \"data\":[ ";

		option2 += "   		   \"xAxis\": {    ";
		option2 += "   		        \"type\": \"category\",    ";
		option2 += "   		        \"boundaryGap\": false,    ";
		option2 += "  \"data\":[ ";

		optionMoney += "   		   \"xAxis\": {    ";
		optionMoney += "   		        \"type\": \"category\",    ";
		optionMoney += "   		        \"boundaryGap\": false,    ";
		optionMoney += "  \"data\":[ ";

		optionMoneyzj += "   		   \"xAxis\": {    ";
		optionMoneyzj += "   		        \"type\": \"category\",    ";
		optionMoneyzj += "   		        \"boundaryGap\": false,    ";
		optionMoneyzj += "  \"data\":[ ";

		for (int i = memberParmaeter; i > 0; i--) {
			String startTime = ECDateUtils.getSpecifiedmonth(endTime, i - 1);
			option += "   			         \"" + startTime + "\"    ";
			option2 += "   			         \"" + startTime + "\"    ";
			optionMoney += "   			         \"" + startTime + "\"    ";
			optionMoneyzj += "   			         \"" + startTime + "\"    ";
			if (i > 1) {
				option += "  , ";
				option2 += "  , ";
				optionMoney += "  , ";
				optionMoneyzj += "  , ";
			}
		}
		option += "  ]    ";
		option2 += "  ]    ";
		optionMoney += "  ]    ";
		optionMoneyzj += "  ]    ";

		option += "   		    },    ";
		option += "   		    \"yAxis\": {    ";
		option += "   		        \"type\": \"value\"    ";
		option += "   		    },    ";
		option += "   			    \"series\": [    ";

		option2 += "   		    },    ";
		option2 += "   		    \"yAxis\": {    ";
		option2 += "   		        \"type\": \"value\"    ";
		option2 += "   		    },    ";
		option2 += "   			    \"series\": [    ";

		optionMoney += "   		    },    ";
		optionMoney += "   		    \"yAxis\": {    ";
		optionMoney += "   		        \"type\": \"value\"    ";
		optionMoney += "   		    },    ";
		optionMoney += "   			    \"series\": [    ";

		optionMoneyzj += "   		    },    ";
		optionMoneyzj += "   		    \"yAxis\": {    ";
		optionMoneyzj += "   		        \"type\": \"value\"    ";
		optionMoneyzj += "   		    },    ";
		optionMoneyzj += "   			    \"series\": [    ";

		for (int j = 0; j < orderlogcton.size(); j++) {
			option += "   		        {    ";
			option += "   		            \"name\":\"" + orderlogcton.get(j) + "\",    ";
			option += "   		            \"type\":\"line\",    ";
			option += "   		            \"stack\": \"总量\",    ";
			option += "  \"data\":[ ";

			option2 += "   		        {    ";
			option2 += "   		            \"name\":\"" + orderlogcton.get(j) + "\",    ";
			option2 += "   		            \"type\":\"line\",    ";
			option2 += "   		            \"stack\": \"总量\",    ";
			option2 += "  \"data\":[ ";

			optionMoney += "   		        {    ";
			optionMoney += "   		            \"name\":\"" + orderlogcton.get(j) + "\",    ";
			optionMoney += "   		            \"type\":\"line\",    ";
			optionMoney += "   		            \"stack\": \"总量\",    ";
			optionMoney += "  \"data\":[ ";

			optionMoneyzj += "   		        {    ";
			optionMoneyzj += "   		            \"name\":\"" + orderlogcton.get(j) + "\",    ";
			optionMoneyzj += "   		            \"type\":\"line\",    ";
			optionMoneyzj += "   		            \"stack\": \"总量\",    ";
			optionMoneyzj += "  \"data\":[ ";

			for (int i = memberParmaeter; i > 0; i--) {
				Integer count = 0;
				Integer countzj = 0;
				Integer orderMoney = 0;
				Integer orderMoneyzj = 0;

				// 获取查询开始日期
				String startTime = ECDateUtils.getSpecifiedmonth(endTime, i - 1);

				if (i == 1) {
					count = orderDao.getorderCountlogctonList(startTime, endTimejs, orderlogcton.get(j));
					countzj = orderDao.getorderCountlogctonList(startTimejs, endTimejs, orderlogcton.get(j));

					orderMoney = orderDao.getorderMoneyCountlogctonList(startTime, endTimejs, orderlogcton.get(j));
					orderMoneyzj = orderDao.getorderMoneyCountlogctonList(startTimejs, endTimejs, orderlogcton.get(j));

				} else {
					count = orderDao.getorderCountlogctonList(startTime, ECDateUtils.getSpecifiedmonth(endTime, i - 2),
							orderlogcton.get(j));

					orderMoney = orderDao.getorderMoneyCountlogctonList(startTime,
							ECDateUtils.getSpecifiedmonth(endTime, i - 2), orderlogcton.get(j));

					if (i == memberParmaeter) {
						countzj = orderDao.getorderCountlogctonList(startTime,
								ECDateUtils.getSpecifiedmonth(endTime, i - 2), orderlogcton.get(j));
						orderMoneyzj = orderDao.getorderMoneyCountlogctonList(startTime,
								ECDateUtils.getSpecifiedmonth(endTime, i - 2), orderlogcton.get(j));
					} else {
						countzj = orderDao.getorderCountlogctonList(startTimejs,
								ECDateUtils.getSpecifiedmonth(endTime, i - 2), orderlogcton.get(j));
						orderMoneyzj = orderDao.getorderMoneyCountlogctonList(startTimejs,
								ECDateUtils.getSpecifiedmonth(endTime, i - 2), orderlogcton.get(j));
					}

				}

				if (count == null) {
					count = 0;
				}
				if (countzj == null) {
					countzj = 0;
				}

				if (orderMoney == null) {
					orderMoney = 0;
				}
				if (orderMoneyzj == null) {
					orderMoneyzj = 0;
				}
				option += count;
				option2 += countzj;
				optionMoney += orderMoney;
				optionMoneyzj += orderMoneyzj;
				if (i > 1) {
					option += "  , ";
					option2 += "  , ";
					optionMoney += "  , ";
					optionMoneyzj += "  , ";
				}

			}

			option += "  ]    ";
			option2 += "  ]    ";

			option += "   }    ";
			option2 += "   }    ";

			optionMoney += "  ]    ";
			optionMoneyzj += "  ]    ";

			optionMoney += "   }    ";
			optionMoneyzj += "   }    ";

			if (j + 1 < orderlogcton.size()) {
				option += "  , ";
				option2 += "  , ";
				optionMoney += "  , ";
				optionMoneyzj += "  , ";
			}
		}

		option += "   		    ]    ";
		option += "   		}    ";

		option2 += "   		    ]    ";
		option2 += "   		}    ";

		optionMoney += "   		    ]    ";
		optionMoney += "   		}    ";

		optionMoneyzj += "   		    ]    ";
		optionMoneyzj += "   		}    ";

		orderCountVo.setOption(option);
		orderCountVo.setOption2(option2);

		orderCountVo.setOptionMoney(optionMoney);
		orderCountVo.setOptionMoney2(optionMoneyzj);

		return orderCountVo;
	}

	/**
	 * 订单运营数据统计-年
	 * 
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	@Override
	public OrderCountVo yearOrderOperateCount(Date startTime1, Date endTime, int yearParmaeter, List<String> list) {
		// TODO Auto-generated method stub
		OrderCountVo orderCountVo = new OrderCountVo();

		SimpleDateFormat dft = new SimpleDateFormat("yyyy");
		String startTimejs = dft.format(startTime1);

		Calendar c = Calendar.getInstance();
		c.setTime(endTime);
		int year = c.get(Calendar.YEAR);
		c.set(Calendar.YEAR, year + 1);

		String endTimejs = dft.format(c.getTime());

		List<String> orderlogcton = new ArrayList<String>();

		if (list.size() > 0) {
			orderlogcton = list;
		} else {
			orderlogcton = orderDao.getorderCount(startTimejs, endTimejs);
		}

		String option = " ";
		String option2 = " ";

		String optionMoney = "";
		String optionMoneyzj = "";

		option += "   		{    ";
		option += "   			    \"title\": {    ";
		option += "   		        \"text\": \"\"    ";
		option += "   			    },    ";
		option += "   			    \"tooltip\": {    ";
		option += "   		        \"trigger\": \"axis\"    ";
		option += "   			    },     ";
		option += "   			    \"legend\": {    ";
		option += "  \"data\":[ ";

		optionMoney += "   		{    ";
		optionMoney += "   			    \"title\": {    ";
		optionMoney += "   		        \"text\": \"\"    ";
		optionMoney += "   			    },    ";
		optionMoney += "   			    \"tooltip\": {    ";
		optionMoney += "   		        \"trigger\": \"axis\"    ";
		optionMoney += "   			    },     ";
		optionMoney += "   			    \"legend\": {    ";
		optionMoney += "  \"data\":[ ";

		optionMoneyzj += "   		{    ";
		optionMoneyzj += "   			    \"title\": {    ";
		optionMoneyzj += "   		        \"text\": \"\"    ";
		optionMoneyzj += "   			    },    ";
		optionMoneyzj += "   			    \"tooltip\": {    ";
		optionMoneyzj += "   		        \"trigger\": \"axis\"    ";
		optionMoneyzj += "   			    },     ";
		optionMoneyzj += "   			    \"legend\": {    ";
		optionMoneyzj += "  \"data\":[ ";

		for (int j = 0; j < orderlogcton.size(); j++) {
			option += "  \"" + orderlogcton.get(j) + "\"";
			optionMoney += "  \"" + orderlogcton.get(j) + "\"";
			optionMoneyzj += "  \"" + orderlogcton.get(j) + "\"";
			if (j + 1 < orderlogcton.size()) {
				option += "  , ";
				optionMoney += "  , ";
				optionMoneyzj += "  , ";
			}
		}
		option += "  ]    ";
		optionMoney += "  ]    ";
		optionMoneyzj += "  ]    ";

		option += "   			    },    ";
		option += "   		    \"grid\": {    ";
		option += "   			        \"left\": \"3%\",    ";
		option += "   		        \"right\": \"4%\",    ";
		option += "   			        \"bottom\": \"3%\",    ";
		option += "   		        \"containLabel\": true    ";
		option += "   		    },    ";
		option += "   		    \"toolbox\": {    ";
		option += "   		        \"feature\": {    ";
		option += "   		            \"saveAsImage\": {}    ";
		option += "   		        }    ";
		option += "   		    },    ";

		optionMoney += "   			    },    ";
		optionMoney += "   		    \"grid\": {    ";
		optionMoney += "   			        \"left\": \"3%\",    ";
		optionMoney += "   		        \"right\": \"4%\",    ";
		optionMoney += "   			        \"bottom\": \"3%\",    ";
		optionMoney += "   		        \"containLabel\": true    ";
		optionMoney += "   		    },    ";
		optionMoney += "   		    \"toolbox\": {    ";
		optionMoney += "   		        \"feature\": {    ";
		optionMoney += "   		            \"saveAsImage\": {}    ";
		optionMoney += "   		        }    ";
		optionMoney += "   		    },    ";

		optionMoneyzj += "   			    },    ";
		optionMoneyzj += "   		    \"grid\": {    ";
		optionMoneyzj += "   			        \"left\": \"3%\",    ";
		optionMoneyzj += "   		        \"right\": \"4%\",    ";
		optionMoneyzj += "   			        \"bottom\": \"3%\",    ";
		optionMoneyzj += "   		        \"containLabel\": true    ";
		optionMoneyzj += "   		    },    ";
		optionMoneyzj += "   		    \"toolbox\": {    ";
		optionMoneyzj += "   		        \"feature\": {    ";
		optionMoneyzj += "   		            \"saveAsImage\": {}    ";
		optionMoneyzj += "   		        }    ";
		optionMoneyzj += "   		    },    ";

		option2 += "   		{    ";
		option2 += "   			    \"title\": {    ";
		option2 += "   		        \"text\": \"\"    ";
		option2 += "   			    },    ";
		option2 += "   			    \"tooltip\": {    ";
		option2 += "   		        \"trigger\": \"axis\"    ";
		option2 += "   			    },     ";
		option2 += "   			    \"legend\": {    ";

		option2 += "  \"data\":[ ";

		for (int j = 0; j < orderlogcton.size(); j++) {
			option2 += "  \"" + orderlogcton.get(j) + "\"";
			if (j + 1 < orderlogcton.size()) {
				option2 += "  , ";
			}
		}
		option2 += "  ]    ";

		option2 += "   			    },    ";
		option2 += "   		    \"grid\": {    ";
		option2 += "   			        \"left\": \"3%\",    ";
		option2 += "   		        \"right\": \"4%\",    ";
		option2 += "   			        \"bottom\": \"3%\",    ";
		option2 += "   		        \"containLabel\": true    ";
		option2 += "   		    },    ";
		option2 += "   		    \"toolbox\": {    ";
		option2 += "   		        \"feature\": {    ";
		option2 += "   		            \"saveAsImage\": {}    ";
		option2 += "   		        }    ";
		option2 += "   		    },    ";

		option += "   		   \"xAxis\": {    ";
		option += "   		        \"type\": \"category\",    ";
		option += "   		        \"boundaryGap\": false,    ";
		option += "  \"data\":[ ";

		option2 += "   		   \"xAxis\": {    ";
		option2 += "   		        \"type\": \"category\",    ";
		option2 += "   		        \"boundaryGap\": false,    ";
		option2 += "  \"data\":[ ";

		optionMoney += "   		   \"xAxis\": {    ";
		optionMoney += "   		        \"type\": \"category\",    ";
		optionMoney += "   		        \"boundaryGap\": false,    ";
		optionMoney += "  \"data\":[ ";

		optionMoneyzj += "   		   \"xAxis\": {    ";
		optionMoneyzj += "   		        \"type\": \"category\",    ";
		optionMoneyzj += "   		        \"boundaryGap\": false,    ";
		optionMoneyzj += "  \"data\":[ ";

		for (int i = yearParmaeter; i > 0; i--) {
			String startTime = ECDateUtils.getSpecifiedYear(endTime, i - 1);
			option += "   			         \"" + startTime + "\"    ";
			option2 += "   			         \"" + startTime + "\"    ";
			optionMoney += "   			         \"" + startTime + "\"    ";
			optionMoneyzj += "   			         \"" + startTime + "\"    ";
			if (i > 1) {
				option += "  , ";
				option2 += "  , ";
				optionMoney += "  , ";
				optionMoneyzj += "  , ";
			}
		}
		option += "  ]    ";
		option2 += "  ]    ";
		optionMoney += "  ]    ";
		optionMoneyzj += "  ]    ";

		option += "   		    },    ";
		option += "   		    \"yAxis\": {    ";
		option += "   		        \"type\": \"value\"    ";
		option += "   		    },    ";
		option += "   			    \"series\": [    ";

		option2 += "   		    },    ";
		option2 += "   		    \"yAxis\": {    ";
		option2 += "   		        \"type\": \"value\"    ";
		option2 += "   		    },    ";
		option2 += "   			    \"series\": [    ";

		optionMoney += "   		    },    ";
		optionMoney += "   		    \"yAxis\": {    ";
		optionMoney += "   		        \"type\": \"value\"    ";
		optionMoney += "   		    },    ";
		optionMoney += "   			    \"series\": [    ";

		optionMoneyzj += "   		    },    ";
		optionMoneyzj += "   		    \"yAxis\": {    ";
		optionMoneyzj += "   		        \"type\": \"value\"    ";
		optionMoneyzj += "   		    },    ";
		optionMoneyzj += "   			    \"series\": [    ";

		for (int j = 0; j < orderlogcton.size(); j++) {
			option += "   		        {    ";
			option += "   		            \"name\":\"" + orderlogcton.get(j) + "\",    ";
			option += "   		            \"type\":\"line\",    ";
			option += "   		            \"stack\": \"总量\",    ";
			option += "  \"data\":[ ";

			option2 += "   		        {    ";
			option2 += "   		            \"name\":\"" + orderlogcton.get(j) + "\",    ";
			option2 += "   		            \"type\":\"line\",    ";
			option2 += "   		            \"stack\": \"总量\",    ";
			option2 += "  \"data\":[ ";

			optionMoney += "   		        {    ";
			optionMoney += "   		            \"name\":\"" + orderlogcton.get(j) + "\",    ";
			optionMoney += "   		            \"type\":\"line\",    ";
			optionMoney += "   		            \"stack\": \"总量\",    ";
			optionMoney += "  \"data\":[ ";

			optionMoneyzj += "   		        {    ";
			optionMoneyzj += "   		            \"name\":\"" + orderlogcton.get(j) + "\",    ";
			optionMoneyzj += "   		            \"type\":\"line\",    ";
			optionMoneyzj += "   		            \"stack\": \"总量\",    ";
			optionMoneyzj += "  \"data\":[ ";

			for (int i = yearParmaeter; i > 0; i--) {
				Integer count = 0;
				Integer countzj = 0;
				Integer orderMoney = 0;
				Integer orderMoneyzj = 0;

				// 获取查询开始日期
				String startTime = ECDateUtils.getSpecifiedYear(endTime, i - 1);

				if (i == 1) {
					count = orderDao.getorderCountlogctonList(startTime, endTimejs, orderlogcton.get(j));
					countzj = orderDao.getorderCountlogctonList(startTimejs, endTimejs, orderlogcton.get(j));

					orderMoney = orderDao.getorderMoneyCountlogctonList(startTime, endTimejs, orderlogcton.get(j));
					orderMoneyzj = orderDao.getorderMoneyCountlogctonList(startTimejs, endTimejs, orderlogcton.get(j));

				} else {
					count = orderDao.getorderCountlogctonList(startTime, ECDateUtils.getSpecifiedYear(endTime, i - 2),
							orderlogcton.get(j));

					orderMoney = orderDao.getorderMoneyCountlogctonList(startTime,
							ECDateUtils.getSpecifiedYear(endTime, i - 2), orderlogcton.get(j));

					if (i == yearParmaeter) {
						countzj = orderDao.getorderCountlogctonList(startTime,
								ECDateUtils.getSpecifiedYear(endTime, i - 2), orderlogcton.get(j));
						orderMoneyzj = orderDao.getorderMoneyCountlogctonList(startTime,
								ECDateUtils.getSpecifiedYear(endTime, i - 2), orderlogcton.get(j));
					} else {
						countzj = orderDao.getorderCountlogctonList(startTimejs,
								ECDateUtils.getSpecifiedYear(endTime, i - 2), orderlogcton.get(j));
						orderMoneyzj = orderDao.getorderMoneyCountlogctonList(startTimejs,
								ECDateUtils.getSpecifiedYear(endTime, i - 2), orderlogcton.get(j));
					}

				}

				if (count == null) {
					count = 0;
				}
				if (countzj == null) {
					countzj = 0;
				}

				if (orderMoney == null) {
					orderMoney = 0;
				}
				if (orderMoneyzj == null) {
					orderMoneyzj = 0;
				}
				option += count;
				option2 += countzj;
				optionMoney += orderMoney;
				optionMoneyzj += orderMoneyzj;
				if (i > 1) {
					option += "  , ";
					option2 += "  , ";
					optionMoney += "  , ";
					optionMoneyzj += "  , ";
				}

			}

			option += "  ]    ";
			option2 += "  ]    ";

			option += "   }    ";
			option2 += "   }    ";

			optionMoney += "  ]    ";
			optionMoneyzj += "  ]    ";

			optionMoney += "   }    ";
			optionMoneyzj += "   }    ";

			if (j + 1 < orderlogcton.size()) {
				option += "  , ";
				option2 += "  , ";
				optionMoney += "  , ";
				optionMoneyzj += "  , ";
			}
		}

		option += "   		    ]    ";
		option += "   		}    ";

		option2 += "   		    ]    ";
		option2 += "   		}    ";

		optionMoney += "   		    ]    ";
		optionMoney += "   		}    ";

		optionMoneyzj += "   		    ]    ";
		optionMoneyzj += "   		}    ";

		orderCountVo.setOption(option);
		orderCountVo.setOption2(option2);

		orderCountVo.setOptionMoney(optionMoney);
		orderCountVo.setOptionMoney2(optionMoneyzj);

		return orderCountVo;
	}

	/**
	 * 客户订单运营报表信息-按日 订单客单价、平均时长
	 */
	@Override
	public List<OrderCountVo> orderUserOperateCount(Date startTime1, Date endTime, int dayParmaeter) {
		// TODO Auto-generated method stub

		List<OrderCountVo> result = new ArrayList<OrderCountVo>();

		String startTimejs = ECDateUtils.getSpecifiedDayBefore(endTime, dayParmaeter);

		SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		c.setTime(endTime);
		int day = c.get(Calendar.DATE);
		c.set(Calendar.DATE, day + 1);

		String endTimejs = dft.format(c.getTime());

		dayParmaeter += 1;

		for (int i = dayParmaeter; i > 0; i--) {
			OrderCountVo orderCountVo = new OrderCountVo();
			// 获取查询开始日期
			String startTime = ECDateUtils.getSpecifiedDayBefore(endTime, i - 1);

			// 查询当前时间的总订单数
			Integer orderUserCount = 0;

			// 查询当前时间的总订单金额
			Integer orderUserMoney = 0;

			// 查询当前时间的总订单时长
			Integer orderUserTime = 0;

			// 订单客单价
			Integer orderUserAmountpj = 0;
			// 平均时长
			Integer orderUserTimepj = 0;

			if (i == 1) {
				orderUserCount = orderDao.getorderUserCountList(startTime, endTimejs);
				orderUserMoney = orderDao.getorderUserMoneyList(startTime, endTimejs);
				orderUserTime = orderDao.getorderUserTimeList(startTime, endTimejs);
			} else {
				orderUserCount = orderDao.getorderUserCountList(startTime,
						ECDateUtils.getSpecifiedDayBefore(endTime, i - 2));
				orderUserMoney = orderDao.getorderUserMoneyList(startTime,
						ECDateUtils.getSpecifiedDayBefore(endTime, i - 2));
				orderUserTime = orderDao.getorderUserTimeList(startTime,
						ECDateUtils.getSpecifiedDayBefore(endTime, i - 2));
			}

			if (null != orderUserCount && orderUserCount > 0) {

				if (null != orderUserMoney && orderUserMoney > 0) {
					orderUserAmountpj = orderUserMoney / orderUserCount;
					orderCountVo.setOrderUserAmount(orderUserAmountpj);
				} else {
					orderCountVo.setOrderUserAmount(0);
				}

				if (null != orderUserTime && orderUserTime > 0) {
					orderUserTimepj = orderUserTime / orderUserCount;
					orderCountVo.setOrderUserTime(orderUserTimepj);
				} else {
					orderCountVo.setOrderUserTime(0);
				}

			} else {
				orderCountVo.setOrderUserAmount(0);
				orderCountVo.setOrderUserTime(0);
			}

			orderCountVo.setOrderDay(startTime);

			result.add(orderCountVo);
		}

		return result;
	}

	/**
	 * 客户订单运营报表信息-按周
	 */
	@Override
	public List<OrderCountVo> weekUserOrderOperateCount(Date startTime1, Date endTime, int weekParmaeter) {
		// TODO Auto-generated method stub

		List<OrderCountVo> result = new ArrayList<OrderCountVo>();

		String startTimejs = ECDateUtils.getSpecifiedWeek(endTime, weekParmaeter);

		SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		c.setTime(endTime);
		int day = c.get(Calendar.DATE);
		c.set(Calendar.DATE, day * 7);

		String endTimejs = dft.format(c.getTime());

		for (int i = weekParmaeter; i > 0; i--) {
			OrderCountVo orderCountVo = new OrderCountVo();
			// 获取查询开始日期
			String startTime = ECDateUtils.getSpecifiedWeek(endTime, i);

			// 查询当前时间的总订单数
			Integer orderUserCount = 0;

			// 查询当前时间的总订单金额
			Integer orderUserMoney = 0;

			// 查询当前时间的总订单时长
			Integer orderUserTime = 0;

			// 订单客单价
			Integer orderUserAmountpj = 0;
			// 平均时长
			Integer orderUserTimepj = 0;

			if (i == 1) {
				orderUserCount = orderDao.getorderUserCountList(startTime, endTimejs);
				orderUserMoney = orderDao.getorderUserMoneyList(startTime, endTimejs);
				orderUserTime = orderDao.getorderUserTimeList(startTime, endTimejs);
			} else {
				orderUserCount = orderDao.getorderUserCountList(startTime,
						ECDateUtils.getSpecifiedWeek(endTime, i - 1));
				orderUserMoney = orderDao.getorderUserMoneyList(startTime,
						ECDateUtils.getSpecifiedWeek(endTime, i - 1));
				orderUserTime = orderDao.getorderUserTimeList(startTime, ECDateUtils.getSpecifiedWeek(endTime, i - 1));
			}

			if (null != orderUserCount && orderUserCount > 0) {

				if (null != orderUserMoney && orderUserMoney > 0) {
					orderUserAmountpj = orderUserMoney / orderUserCount;
					orderCountVo.setOrderUserAmount(orderUserAmountpj);
				} else {
					orderCountVo.setOrderUserAmount(0);
				}

				if (null != orderUserTime && orderUserTime > 0) {
					orderUserTimepj = orderUserTime / orderUserCount;
					orderCountVo.setOrderUserTime(orderUserTimepj);
				} else {
					orderCountVo.setOrderUserTime(0);
				}

			} else {
				orderCountVo.setOrderUserAmount(0);
				orderCountVo.setOrderUserTime(0);
			}

			orderCountVo.setOrderDay(ECDateUtils.getSpecifiedWeek(endTime, i - 1));

			result.add(orderCountVo);
		}

		return result;
	}

	/**
	 * 客户订单运营报表信息-按月
	 */
	@Override
	public List<OrderCountVo> monthUserOrderOperateCount(Date startTime1, Date endTime, int monthParmaeter) {
		// TODO Auto-generated method stub

		List<OrderCountVo> result = new ArrayList<OrderCountVo>();

		String startTimejs = ECDateUtils.getSpecifiedmonth(endTime, monthParmaeter);

		SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM");
		Calendar c = Calendar.getInstance();
		c.setTime(endTime);
		int month = c.get(Calendar.MONTH);
		c.set(Calendar.MONTH, month + 1);

		String endTimejs = dft.format(c.getTime());

		for (int i = monthParmaeter; i > 0; i--) {
			OrderCountVo orderCountVo = new OrderCountVo();
			// 获取查询开始日期
			String startTime = ECDateUtils.getSpecifiedmonth(endTime, i - 1);

			// 查询当前时间的总订单数
			Integer orderUserCount = 0;

			// 查询当前时间的总订单金额
			Integer orderUserMoney = 0;

			// 查询当前时间的总订单时长
			Integer orderUserTime = 0;

			// 订单客单价
			Integer orderUserAmountpj = 0;
			// 平均时长
			Integer orderUserTimepj = 0;

			if (i == 1) {
				orderUserCount = orderDao.getorderUserCountList(startTime, endTimejs);
				orderUserMoney = orderDao.getorderUserMoneyList(startTime, endTimejs);
				orderUserTime = orderDao.getorderUserTimeList(startTime, endTimejs);
			} else {
				orderUserCount = orderDao.getorderUserCountList(startTime,
						ECDateUtils.getSpecifiedmonth(endTime, i - 2));
				orderUserMoney = orderDao.getorderUserMoneyList(startTime,
						ECDateUtils.getSpecifiedmonth(endTime, i - 2));
				orderUserTime = orderDao.getorderUserTimeList(startTime, ECDateUtils.getSpecifiedmonth(endTime, i - 2));
			}

			if (null != orderUserCount && orderUserCount > 0) {

				if (null != orderUserMoney && orderUserMoney > 0) {
					orderUserAmountpj = orderUserMoney / orderUserCount;
					orderCountVo.setOrderUserAmount(orderUserAmountpj);
				} else {
					orderCountVo.setOrderUserAmount(0);
				}

				if (null != orderUserTime && orderUserTime > 0) {
					orderUserTimepj = orderUserTime / orderUserCount;
					orderCountVo.setOrderUserTime(orderUserTimepj);
				} else {
					orderCountVo.setOrderUserTime(0);
				}

			} else {
				orderCountVo.setOrderUserAmount(0);
				orderCountVo.setOrderUserTime(0);
			}

			orderCountVo.setOrderDay(startTime);

			result.add(orderCountVo);
		}

		return result;
	}

	/**
	 * 客户订单运营报表信息-按年
	 */
	@Override
	public List<OrderCountVo> yearUserOrderOperateCount(Date startTime1, Date endTime, int yearParmaeter) {
		// TODO Auto-generated method stub

		List<OrderCountVo> result = new ArrayList<OrderCountVo>();

		String startTimejs = ECDateUtils.getSpecifiedDayBefore(endTime, yearParmaeter);

		SimpleDateFormat dft = new SimpleDateFormat("yyyy");
		Calendar c = Calendar.getInstance();
		c.setTime(endTime);
		int year = c.get(Calendar.YEAR);
		c.set(Calendar.YEAR, year + 1);

		String endTimejs = dft.format(c.getTime());

		for (int i = yearParmaeter; i > 0; i--) {
			OrderCountVo orderCountVo = new OrderCountVo();
			// 获取查询开始日期
			String startTime = ECDateUtils.getSpecifiedYear(endTime, i - 1);

			// 查询当前时间的总订单数
			Integer orderUserCount = 0;

			// 查询当前时间的总订单金额
			Integer orderUserMoney = 0;

			// 查询当前时间的总订单时长
			Integer orderUserTime = 0;

			// 订单客单价
			Integer orderUserAmountpj = 0;
			// 平均时长
			Integer orderUserTimepj = 0;

			if (i == 1) {
				orderUserCount = orderDao.getorderUserCountList(startTime, endTimejs);
				orderUserMoney = orderDao.getorderUserMoneyList(startTime, endTimejs);
				orderUserTime = orderDao.getorderUserTimeList(startTime, endTimejs);
			} else {
				orderUserCount = orderDao.getorderUserCountList(startTime,
						ECDateUtils.getSpecifiedYear(endTime, i - 2));
				orderUserMoney = orderDao.getorderUserMoneyList(startTime,
						ECDateUtils.getSpecifiedYear(endTime, i - 2));
				orderUserTime = orderDao.getorderUserTimeList(startTime, ECDateUtils.getSpecifiedYear(endTime, i - 2));
			}

			if (null != orderUserCount && orderUserCount > 0) {

				if (null != orderUserMoney && orderUserMoney > 0) {
					orderUserAmountpj = orderUserMoney / orderUserCount;
					orderCountVo.setOrderUserAmount(orderUserAmountpj);
				} else {
					orderCountVo.setOrderUserAmount(0);
				}

				if (null != orderUserTime && orderUserTime > 0) {
					orderUserTimepj = orderUserTime / orderUserCount;
					orderCountVo.setOrderUserTime(orderUserTimepj);
				} else {
					orderCountVo.setOrderUserTime(0);
				}

			} else {
				orderCountVo.setOrderUserAmount(0);
				orderCountVo.setOrderUserTime(0);
			}

			orderCountVo.setOrderDay(startTime);

			result.add(orderCountVo);
		}

		return result;
	}

	@Override
	public List<Map<String, Object>> getRealTimeInOrderNum() {
		return orderDao.getRealTimeInOrderNum();
	}

	@Override
	public OrderReportFormVo orderNumberNs(Query q) {

		return orderDao.orderNumberNs(q);
	}

	@Override
	public OrderReportFormVo orderNumberReport(Query q) {
		return orderDao.orderNumberReport(q);
	}

	@Override
	public OrderReportFormVo getOrderFirstDayData(Query q) {
		return orderDao.getOrderFirstDayData(q);
	}

	@Override
	public OrderReportFormVo orderNumberReportWeek(Query q) {
		return orderDao.orderNumberReportWeek(q);
	}

	@Override
	public OrderReportStatisticalVo orderNumberReportStatWeek(Query q) {
		return orderDao.orderNumberReportStatWeek(q);
	}

	@Override
	public OrderReportFormVo orderNumberReportNs(Query q) {
		return orderDao.orderNumberReportNs(q);
	}

	@Transactional(propagation = Propagation.SUPPORTS)
	public PageFinder<Order> getOrderPagedListPayableAmount(Query q) {
		PageFinder<Order> page = new PageFinder<Order>();

		List<Order> list = null;
		long rowCount = 0L;

		try {
			// 调用dao查询满足条件的分页数据
			list = orderDao.getOrderPagedListPayableAmount(q);
			// 调用dao统计满足条件的记录总数
			rowCount = orderDao.count(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		// 如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<Order>(0) : list;

		// 将分页数据和记录总数设置到分页结果对象中
		page.setData(list);
		page.setRowCount(rowCount);

		return page;
	}

	@Override
	public Integer getAllAutoStartOrderCharging() {
		List<Order> orderList = orderDao.getAllAutoStartOrderChargings();
		Double myAmount = 0d;
		if (orderList != null && orderList.size() > 0) {
			List<Order> list = new ArrayList<Order>();
			// 获取超时配置的时长
			SysParam sp = sysParamService.getByParamKey("OVER_ORDER_TIME");
			if (sp != null) {
				Double ds = Double.parseDouble(sp.getParamValue());
				for (Order order : orderList) {
					// 我的余额
					Query qr = new Query();
					PricingPackOrder ppor = new PricingPackOrder();
					ppor.setPayStatus(1);// 已经支付的
					ppor.setIsAvailable(1);// 可用
					ppor.setNowTime(new Date());
					ppor.setMemberNo(order.getMemberNo());
					qr.setQ(ppor);
					List<PricingPackOrder> pporList = pricingPackOrderService.getPricingPackOrderListBypo(qr);
					Double poa = 0.0;
					Double uoa = 0.0;
					for (PricingPackOrder pricingPackOrder : pporList) {
						if (pricingPackOrder.getPackOrderAmount() != null) {
							poa = ECCalculateUtils.add(poa, pricingPackOrder.getPackOrderAmount());
							if (pricingPackOrder.getUseredOrderAmount() != null) {
								uoa = ECCalculateUtils.add(uoa, pricingPackOrder.getUseredOrderAmount());
							}

						}
					}
					try {
						order = orderTripView(order).getData() == null ? order : orderTripView(order).getData();
					} catch (ParseException e) {
						e.printStackTrace();
					}
					Double amount = ECNumberUtils.roundDoubleWithScale(order.getOrderAmount(), 2);
					// 顶单金额 减去 余额
					myAmount = ECCalculateUtils.sub(amount, ECCalculateUtils.sub(poa, uoa));
					// 如果订单金额减去充值金额大于配置的值 则 超额
					if (ECCalculateUtils.ge(myAmount, ds)) {
						list.add(order);
					}
				}
				if (list != null && list.size() > 0) {
					orderDao.updateOrderOvers(list);
				}

			}

		} else {
			return null;
		}

		return null;

	}

	@Override
	public ResultInfo<Money> getIncomeTotal(Money money) {

		Map<String, Object> alipayIncomeMap = paymentRecordDao.getAlipayIncomeMap(money);

		if (alipayIncomeMap != null) {
			Double orderAlipayAmount = (Double) alipayIncomeMap.get("orderAlipayAmount");
			if (orderAlipayAmount != null) {
				money.setOrderAlipayAmount(orderAlipayAmount);
				money.setOrderAlipayCharge(ECCalculateUtils.mul(orderAlipayAmount, zfbAgentFee));
			} else {
				money.setOrderAlipayAmount(0d);
				money.setOrderAlipayCharge(0d);
			}
			Double priceAlipayAmount = (Double) alipayIncomeMap.get("priceAlipayAmount");
			if (priceAlipayAmount != null) {
				money.setPriceAlipayAmount(priceAlipayAmount);
				money.setPriceAlipayCharge(ECCalculateUtils.mul(priceAlipayAmount, zfbAgentFee));
			} else {
				money.setPriceAlipayAmount(0d);
				money.setPriceAlipayCharge(0d);
			}
			Double depositAlipayAmount = (Double) alipayIncomeMap.get("depositAlipayAmount");
			if (depositAlipayAmount != null) {
				money.setDepositAlipayAmount(depositAlipayAmount);
				money.setDepositAlipayCharge(ECCalculateUtils.mul(depositAlipayAmount, zfbAgentFee));
			} else {
				money.setDepositAlipayAmount(0d);
				money.setDepositAlipayCharge(0d);
			}
		}

		Map<String, Object> wxIncomeMap = paymentRecordDao.getWxIncomeMap(money);

		if (wxIncomeMap != null) {
			Double orderWxAmount = (Double) wxIncomeMap.get("orderWxAmount");
			if (orderWxAmount != null) {
				money.setOrderWxAmount(orderWxAmount);
				money.setOrderWxCharge(orderWxAmount * 0.006);
			} else {
				money.setOrderWxAmount(0d);
				money.setOrderWxCharge(0d);
			}
			Double priceWxAmount = (Double) wxIncomeMap.get("priceWxAmount");
			if (priceWxAmount != null) {
				money.setPriceWxAmount(priceWxAmount);
				money.setPriceWxCharge(priceWxAmount * 0.006);
			} else {
				money.setPriceWxAmount(0d);
				money.setPriceWxCharge(0d);
			}
			Double depositWxAmount = (Double) wxIncomeMap.get("depositWxAmount");
			if (depositWxAmount != null) {
				money.setDepositWxAmount(depositWxAmount);
				money.setDepositWxCharge(depositWxAmount * 0.006);
			} else {
				money.setDepositWxAmount(0d);
				money.setDepositWxCharge(0d);
			}
		}

		Double depositAlipayRefund = depositOrderDao.getDepositAlipayRefund(money);
		if (depositAlipayRefund != null) {
			money.setDepositAlipayRefund(depositAlipayRefund);
		} else {
			money.setDepositAlipayRefund(0d);
		}

		Double depositWxRefund = depositOrderDao.getDepositWxRefund(money);
		if (depositWxRefund != null) {
			money.setDepositWxRefund(depositWxRefund);
		} else {
			money.setDepositWxRefund(0d);
		}

		Double orderIncomeTotal = orderDao.getOrderIncomeTotal(money);
		if (orderIncomeTotal != null) {
			money.setOrderIncomeTotal(orderIncomeTotal);
		} else {
			money.setOrderIncomeTotal(0d);
		}

		ResultInfo<Money> resultInfo = new ResultInfo<Money>();
		resultInfo.setCode(Constant.SECCUESS);
		resultInfo.setData(money);
		return resultInfo;
	}

	@Override
	public Integer countOrderMember() {
		return orderDao.countOrderMember();
	}

	@Override
	public Map<String, Object> getTransactionCountMap() {
		return orderDao.getTransactionCountMap();
	}

	@Override
	public Map<String, Object> getOrderDay10CountMap() {
		String dateArray[] = new String[10];
		long numArray[] = new long[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		Date date = new Date();
		for (int day = -9; day <= 0; day++) {
			String d = ECDateUtils.formatDate(ECDateUtils.getDateAfter(date, day), "MM-dd");
			dateArray[day + 9] = d;
		}
		List<Map<String, Object>> data = orderDao.getOrderDay10CountMap();
		if (data != null && !data.isEmpty()) {
			for (Map<String, Object> d : data) {
				for (int i = 0; i < dateArray.length; i++) {
					if (dateArray[i].equals((String) d.get("date"))) {
						numArray[i] = (Long) d.get("num");
					}
				}
			}
		}
		Map<String, Object> map = new HashMap<>();
		map.put("dateArray", dateArray);
		map.put("numArray", numArray);
		return map;
	}

	@Override
	public Integer countExcessOrder() {
		return orderDao.countExcessOrder();
	}

	@Override
	public Order getOrderWaring(String memberNo) {

		return orderDao.getOrderWaring(memberNo);
	}

	@Override
	public PageFinder<Order> getOrderPagedListW(Query q) {
		PageFinder<Order> page = new PageFinder<Order>();

		List<Order> list = null;
		long rowCount = 0L;

		try {
			// 调用dao查询满足条件的分页数据
			list = orderDao.pageListW(q);
			// 调用dao统计满足条件的记录总数
			rowCount = orderDao.count(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		// 如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<Order>(0) : list;

		// 将分页数据和记录总数设置到分页结果对象中
		page.setData(list);
		page.setRowCount(rowCount);

		return page;
	}

	@Override
	public PageFinder<OrderStatisticalVo> pageListOrderStat(Query q) {
		PageFinder<OrderStatisticalVo> page = new PageFinder<OrderStatisticalVo>();

		List<OrderStatisticalVo> list = null;
		long rowCount = 0L;

		try {
			// 调用dao查询满足条件的分页数据
			list = orderDao.getOrderStatisticalList(q);
			// 调用dao统计满足条件的记录总数
			rowCount = orderDao.countOrderStatistical(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		// 如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<OrderStatisticalVo>(0) : list;

		// 将分页数据和记录总数设置到分页结果对象中
		page.setData(list);
		page.setRowCount(rowCount);

		return page;
	}

	@Override
	public List<OrderStatisticalVo> getOrderExportStatList(OrderStatisticalVo orderVo) {
		List<OrderStatisticalVo> list = new ArrayList<OrderStatisticalVo>();
		try {
			// 调用dao查询满足条件的分页数据
			list = orderDao.getOrderExportStatList(orderVo);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		// 如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<OrderStatisticalVo>(0) : list;
		return list;
	}

	@Override
	public PageFinder<ParkRpts> pageListParkSummary(Query q) {
		PageFinder<ParkRpts> page = new PageFinder<ParkRpts>();
		List<ParkRpts> list = new ArrayList<ParkRpts>();
		long rowCount = 1L;

		try {
			// 调用dao查询满足条件的分页数据
			list = orderDao.listParkSummary(q);
			// 调用dao统计满足条件的记录总数
			rowCount = orderDao.countParkSummary(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		// 如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<ParkRpts>(0) : list;

		// 将分页数据和记录总数设置到分页结果对象中
		page.setData(list);
		page.setRowCount(rowCount);
		return page;
	}

	@Override
	public List<ParkRpts> listParkSummaryExport(ParkRpts parkRpts) {
		List<ParkRpts> list = new ArrayList<ParkRpts>();
		try {
			// 调用dao查询满足条件的分页数据
			list = orderDao.listParkSummaryExport(parkRpts);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		// 如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<ParkRpts>(0) : list;
		return list;
	}

	@Override
	public OrderAmountDayVo getOrderPayAmountData(String time) {
		return orderDao.getOrderPayAmountData(time);
	}

	@Override
	public OrderAmountDayVo getOrderAmountAndBalanceData(String time) {
		return orderDao.getOrderAmountAndBalanceData(time);
	}

	@Override
	public OrderAmountDayVo getOrderPayAmountDataFirst(String time) {
		return orderDao.getOrderPayAmountDataFirst(time);
	}

	@Override
	public OrderAmountDayVo getOrderAmountAndBalanceDataFirst(String time) {
		return orderDao.getOrderAmountAndBalanceDataFirst(time);
	}

	@Override
	public OrderReportStatisticalVo getOrderPayAmountWeekData(Query q) {
		return orderDao.getOrderPayAmountWeekData(q);
	}

	@Override
	public OrderReportStatisticalVo getOrderAmountAndBalanceWeekData(Query q) {
		return orderDao.getOrderAmountAndBalanceWeekData(q);
	}

	@Override
	public OrderReportStatisticalVo orderNumberReportMonth(Query q) {
		return orderDao.orderNumberReportMonth(q);
	}

	@Override
	public OrderReportStatisticalVo getOrderPayAmountMonthData(String time) {
		return orderDao.getOrderPayAmountMonthData(time);
	}

	@Override
	public OrderReportStatisticalVo getOrderAmountAndBalanceMonthData(String time) {
		return orderDao.getOrderAmountAndBalanceMonthData(time);
	}

	@Override
	public OrderReportStatisticalVo getFirstMonthOrderData(Query q) {
		return orderDao.getFirstMonthOrderData(q);
	}

	@Override
	public OrderReportStatisticalVo getOrderPayAmountFirstMonth(String time) {
		return orderDao.getOrderPayAmountFirstMonth(time);
	}

	@Override
	public OrderReportStatisticalVo getOrderAmountAndBalanceFirstMonth(String time) {
		return orderDao.getOrderAmountAndBalanceFirstMonth(time);
	}

	@Override
	public OrderReportFormVo getOrderFirstWeekDayData(Query q) {
		return orderDao.getOrderFirstWeekDayData(q);
	}

	@Override
	public OrderAmountDayVo getOrderAmountAndBalanceWeekDataFirst(String time) {
		return orderDao.getOrderAmountAndBalanceWeekDataFirst(time);
	}

	@Override
	public OrderAmountDayVo getOrderPayAmountWeekDataFirst(String time) {
		return orderDao.getOrderPayAmountWeekDataFirst(time);
	}

	@Override
	public Map<String, Object> getCarOrderCountMap() {
		Map<String, Object> map = new HashMap<>();
		List<OrderCarUsedVo> data = orderDao.getCarOrderCountMap();
		int len = data.size();
		Integer dateArray[] = new Integer[len];
		String numArray[] = new String[len];
		if (data != null && !data.isEmpty()) {
			for (int j = 0; j < data.size(); j++) {
				dateArray[j] = data.get(j).getCount1();
				numArray[j] = data.get(j).getCarModelName();
			}
		}
		map.put("dateArray", dateArray);
		map.put("numArray", numArray);
		return map;
	}
}