package cn.com.shopec.core.mlparking.service.impl;

import java.text.SimpleDateFormat;
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
import org.springframework.util.StringUtils;

import cn.com.shopec.common.Operator;
import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.common.constants.Constant;
import cn.com.shopec.common.dsUtils.ConstantCd;
import cn.com.shopec.common.dsUtils.WgPost;
import cn.com.shopec.common.utils.ECCalculateUtils;
import cn.com.shopec.common.utils.MapUtils;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.member.dao.MemberDao;
import cn.com.shopec.core.member.model.Member;
import cn.com.shopec.core.ml.dao.CAccountBalanceDao;
import cn.com.shopec.core.ml.model.CAccountBalance;
import cn.com.shopec.core.ml.vo.AccountBalanceVo;
import cn.com.shopec.core.mlparking.dao.CParkBillingDao;
import cn.com.shopec.core.mlparking.dao.CParkLockDao;
import cn.com.shopec.core.mlparking.dao.CParkOrderDao;
import cn.com.shopec.core.mlparking.dao.CParkingDao;
import cn.com.shopec.core.mlparking.dao.CPliesNumberDao;
import cn.com.shopec.core.mlparking.model.CParkBilling;
import cn.com.shopec.core.mlparking.model.CParkLock;
import cn.com.shopec.core.mlparking.model.CParkOrder;
import cn.com.shopec.core.mlparking.model.CParking;
import cn.com.shopec.core.mlparking.service.CParkLockService;
import cn.com.shopec.core.mlparking.service.CParkOrderService;
import cn.com.shopec.core.mlparking.vo.LockOrder;
import cn.com.shopec.core.mlparking.vo.OrderInfo;
import cn.com.shopec.core.mlparking.vo.ParkingLockVo;
import cn.com.shopec.core.system.service.SysParamService;
import net.sf.json.JSONObject;

/**
 * 停车订单表 服务实现类
 */
@Service
public class CParkOrderServiceImpl implements CParkOrderService {

	private static final Log log = LogFactory
			.getLog(CParkOrderServiceImpl.class);

	@Resource
	private CParkOrderDao cParkOrderDao;
	@Resource
	private CAccountBalanceDao accountBalanceDao;
	@Resource
	private SysParamService sysParamService;
	@Resource
	private CParkingDao parkingDao;
	@Resource
	private MemberDao memberDao;
	@Resource
	private CParkLockDao cparklockDao;
	@Resource
	private CParkLockService cParkLockService;
	@Resource
	private CPliesNumberDao pliesNumberDao;
	@Resource
	private CParkBillingDao parkBillingDao;

	/**
	 * 根据查询条件，查询并返回CParkOrder的列表
	 * 
	 * @param q
	 *            查询条件
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<CParkOrder> getCParkOrderList(Query q) {
		List<CParkOrder> list = null;
		try {
			// 直接调用Dao方法进行查询
			list = cParkOrderDao.queryAll(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		// 如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<CParkOrder>(0) : list;
		return list;
	}

	/**
	 * 根据查询条件，分页查询并返回CParkOrder的分页结果
	 * 
	 * @param q
	 *            查询条件
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public PageFinder<CParkOrder> getCParkOrderPagedList(Query q) {
		PageFinder<CParkOrder> page = new PageFinder<CParkOrder>();

		List<CParkOrder> list = null;
		long rowCount = 0L;

		try {
			// 调用dao查询满足条件的分页数据
			list = cParkOrderDao.pageList(q);
			// 调用dao统计满足条件的记录总数
			rowCount = cParkOrderDao.count(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		// 如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<CParkOrder>(0) : list;

		// 将分页数据和记录总数设置到分页结果对象中
		page.setData(list);
		page.setRowCount(rowCount);

		return page;
	}

	/**
	 * 根据主键，查询并返回一个CParkOrder对象
	 * 
	 * @param id
	 *            主键id
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public CParkOrder getCParkOrder(String id) {
		CParkOrder obj = null;
		if (id == null || id.length() == 0) { // 传入的主键无效时直接返回null
			log.info(Constant.ERR_MSG_INVALID_ARG + " id = " + id);
			return obj;
		}
		try {
			// 调用dao，根据主键查询
			obj = cParkOrderDao.get(id);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		return obj;
	}

	/**
	 * 根据主键数组，查询并返回一组CParkOrder对象
	 * 
	 * @param ids
	 *            主键数组，数组中的主键值应当无重复值，如有重复值时，则返回的列表长度可能小于传入的数组长度
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<CParkOrder> getCParkOrderByIds(String[] ids) {
		List<CParkOrder> list = null;
		if (ids == null || ids.length == 0) {
			log.info(Constant.ERR_MSG_INVALID_ARG
					+ " ids is null or empty array.");
		} else {
			try {
				// 调用dao，根据主键数组查询
				list = cParkOrderDao.getByIds(ids);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
		}

		// 如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<CParkOrder>(0) : list;

		return list;
	}

	/**
	 * 根据主键，删除特定的CParkOrder记录
	 * 
	 * @param id
	 *            主键id
	 * @param operator
	 *            操作人对象
	 * @return
	 */
	@Transactional
	public ResultInfo<CParkOrder> delCParkOrder(String id, Operator operator) {
		ResultInfo<CParkOrder> resultInfo = new ResultInfo<CParkOrder>();
		if (id == null || id.length() == 0) { // 传入的主键无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG);
			log.info(Constant.ERR_MSG_INVALID_ARG + " id = " + id);
			return resultInfo;
		}
		try {
			// 调用Dao执行删除，并判断删除语句执行结果
			int count = cParkOrderDao.delete(id);
			if (count > 0) {
				resultInfo.setCode(Constant.SECCUESS);
			} else {
				resultInfo.setCode(Constant.FAIL);
				resultInfo.setMsg(Constant.ERR_MSG_DATA_NOT_EXISTS);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			TransactionAspectSupport.currentTransactionStatus()
					.setRollbackOnly();
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_EXCEPTION_CATCHED);
		}
		return resultInfo;
	}

	/**
	 * 新增一条CParkOrder记录，执行成功后传入对象及返回对象的主键属性值不为null
	 * 
	 * @param cParkOrder
	 *            新增的CParkOrder数据（如果无特殊需求，新增对象的主键值请保留为null）
	 * @param operator
	 *            操作人对象
	 * @return
	 */
	@Transactional
	public ResultInfo<CParkOrder> addCParkOrder(CParkOrder cParkOrder,
			Operator operator) {
		ResultInfo<CParkOrder> resultInfo = new ResultInfo<CParkOrder>();

		if (cParkOrder == null) { // 传入参数无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG);
			log.info(Constant.ERR_MSG_INVALID_ARG + " cParkOrder = "
					+ cParkOrder);
		} else {
			try {
				// 如果传入参数的主键为null，则调用生成主键的方法获取新的主键
				if (cParkOrder.getParkOrderNo() == null) {
					cParkOrder.setParkOrderNo(this.generatePK());
				}
				// 如果传入的操作人不为null，则设置新增记录的操作人类型和操作人id
				if (operator != null) {
					cParkOrder.setOperatorType(operator.getOperatorType());
					cParkOrder.setOperatorId(operator.getOperatorId());
				}

				// 设置创建时间和更新时间为当前时间
				Date now = new Date();
				cParkOrder.setCreateTime(now);
				cParkOrder.setUpdateTime(now);

				// 填充默认值
				this.fillDefaultValues(cParkOrder);

				// 调用Dao执行插入操作
				cParkOrderDao.add(cParkOrder);
				resultInfo.setCode(Constant.SECCUESS);
				resultInfo.setData(cParkOrder);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
				TransactionAspectSupport.currentTransactionStatus()
						.setRollbackOnly();
				resultInfo.setCode(Constant.FAIL);
				resultInfo.setMsg(Constant.ERR_MSG_EXCEPTION_CATCHED);
			}
		}

		return resultInfo;
	}

	/**
	 * 根据主键，更新一条CParkOrder记录
	 * 
	 * @param cParkOrder
	 *            更新的CParkOrder数据，且其主键不能为空
	 * @param operator
	 *            操作人对象
	 * @return
	 */
	@Transactional
	public ResultInfo<CParkOrder> updateCParkOrder(CParkOrder cParkOrder,
			Operator operator) {
		ResultInfo<CParkOrder> resultInfo = new ResultInfo<CParkOrder>();

		if (cParkOrder == null || cParkOrder.getParkOrderNo() == null) { // 传入参数无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG);
			log.info(Constant.ERR_MSG_INVALID_ARG + " cParkOrder = "
					+ cParkOrder);
		} else {
			try {
				// 如果传入的操作人不为null，则设置更新记录的操作人类型和操作人id
				if (operator != null) {
					cParkOrder.setOperatorType(operator.getOperatorType());
					cParkOrder.setOperatorId(operator.getOperatorId());
				}

				// 设置更新时间为当前时间
				cParkOrder.setUpdateTime(new Date());

				// 调用Dao执行更新操作，并判断更新语句执行结果
				int count = cParkOrderDao.update(cParkOrder);
				if (count > 0) {
					resultInfo.setCode(Constant.SECCUESS);
				} else {
					resultInfo.setCode(Constant.FAIL);
				}
				resultInfo.setData(cParkOrder);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
				TransactionAspectSupport.currentTransactionStatus()
						.setRollbackOnly();
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
		return "PO" + String.valueOf(new Date().getTime());
	}

	/**
	 * 为CParkOrder对象设置默认值
	 * 
	 * @param obj
	 */
	public void fillDefaultValues(CParkOrder obj) {
		if (obj != null) {
		}
	}

	/**
	 * 根据主键，更新一条CParkOrder记录
	 * 
	 * @param cParkOrder
	 *            更新的CParkOrder数据，且其主键不能为空
	 * @param operator
	 *            操作人对象
	 * @return
	 */
	@Transactional
	public ResultInfo<CParkOrder> updateCParkOrder_two(CParkOrder parkOrder,
			CParkBilling parkBilling, CParking parking, Operator operator) {
		ResultInfo<CParkOrder> resultInfo = new ResultInfo<CParkOrder>();

		if (parkOrder == null || parkOrder.getParkOrderNo() == null) { // 传入参数无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG);
			log.info(Constant.ERR_MSG_INVALID_ARG + " cParkOrder = "
					+ parkOrder);
		} else {
			try {
				// 如果传入的操作人不为null，则设置更新记录的操作人类型和操作人id
				if (operator != null) {
					parkOrder.setOperatorType(operator.getOperatorType());
					parkOrder.setOperatorId(operator.getOperatorId());
				}

				// 设置更新时间为当前时间
				parkOrder.setUpdateTime(new Date());
				parkOrder.setDepartureTime(new Date());// 订单结束时间

				// 获取账户余额
				AccountBalanceVo accountBalanceVo = accountBalanceDao
						.pageListTwo_No(parkOrder.getMemberNo());
				// 结束订单计算对应费用和时长
				if (parkOrder.getAppointmentTime() != null
						&& !"".equals(parkOrder.getAppointmentTime())) {// 预约时间不为空
					// 计算停车总时间
					long timeNum = (parkOrder.getDepartureTime().getTime() - parkOrder
							.getAppointmentTime().getTime()) % (1000 * 60);
					int timeNums = (int) ((parkOrder.getDepartureTime()
							.getTime() - parkOrder.getAppointmentTime()
							.getTime()) / (1000 * 60));
					// 总时间
					int timeLength = (timeNum == 0 ? timeNums : timeNums + 1);
					parkOrder.setTotalTime(timeLength);// 总时长(分)
					int h = (int) (timeLength % parkBilling.getUnitTime() == 0 ? timeLength
							/ parkBilling.getUnitTime()
							: timeLength / parkBilling.getUnitTime() + 1);// 总时间
					parkOrder.setTotalMoney(ECCalculateUtils.mul(h,
							parkBilling.getOvertimePrice()));// 总金额
					// 计算预约时间//有停车
					if (parkOrder.getEntryTime() != null
							&& !"".equals(parkOrder.getEntryTime())) {// 订单结束时间
						parkOrder.setAppointmentFreeTotal(parkBilling
								.getFreeTime());// 预约免费时长
						// 计算超时预约时间
						double sub = ECCalculateUtils.sub(timeLength,
								parkBilling.getFreeTime());
						if (sub > 0) {
							// 预约时长
							parkOrder.setAppointmentTimeTotal(parkBilling
									.getFreeTime());// 预约时长
							int h1 = (int) (sub % parkBilling.getUnitTime() == 0 ? sub
									/ parkBilling.getUnitTime()
									: sub / parkBilling.getUnitTime() + 1);// 免费时长的总时间
							parkOrder.setParkMoney(ECCalculateUtils.mul(h1,
									parkBilling.getOvertimePrice()));// 停车金额
						} else {
							parkOrder.setAppointmentTimeTotal(timeLength);
							parkOrder.setParkMoney(0.0);
						}
					} else {// 取消预约后计算费用
						parkOrder.setAppointmentTimeTotal(timeLength);// 预约时长
						parkOrder.setParkMoney(ECCalculateUtils.mul(h,
								parkBilling.getOvertimePrice()));// 停车费用以及取消预约金额
						parkOrder.setAppointmentFreeTotal(0);// 预约免费时长
					}
				} else {// 未预约停车
						// 计算停车总时间
					long timeNum_no = (parkOrder.getDepartureTime().getTime() - parkOrder
							.getEntryTime().getTime()) % (1000 * 60);
					int timeNums_no = (int) ((parkOrder.getDepartureTime()
							.getTime() - parkOrder.getEntryTime().getTime()) / (1000 * 60));
					int timeLength_no = (timeNum_no == 0 ? timeNums_no
							: timeNums_no + 1);
					parkOrder.setTotalTime(timeLength_no);// 总时长(分)
					int h_no = (int) (timeLength_no % parkBilling.getUnitTime() == 0 ? timeLength_no
							/ parkBilling.getUnitTime()
							: timeLength_no / parkBilling.getUnitTime() + 1);// 总小时
					parkOrder.setAppointmentMoney(0.0);// 预约金额
					parkOrder.setAppointmentTimeTotal(0);// 预约时长
					parkOrder.setTotalMoney(ECCalculateUtils.mul(h_no,
							parkBilling.getOvertimePrice()));// 总金额
					parkOrder
							.setAppointmentFreeTotal(parkBilling.getFreeTime());// 预约免费时长
					// 计算超时预约时间
					double subs = ECCalculateUtils.sub(timeLength_no,
							parkBilling.getFreeTime());
					if (subs > 0) {
						// 预约时长
						int hs = (int) (subs % parkBilling.getUnitTime() == 0 ? subs
								/ parkBilling.getUnitTime()
								: subs / parkBilling.getUnitTime() + 1);// 总小时
						parkOrder.setParkMoney(ECCalculateUtils.mul(hs,
								parkBilling.getOvertimePrice()));// tingche
																	// 金额
					} else {
						parkOrder.setParkMoney(0.0);
					}
				}
				// 待支付金额
				double add = parkOrder.getParkMoney();
				CAccountBalance ab = new CAccountBalance();// 余额表
				ab.setAccountBalanceNo(accountBalanceVo.getAccountBalanceNo());
				// 余额抵扣金额
				if (ECCalculateUtils.lt(accountBalanceVo.getStopBalance(), add)) {
					parkOrder.setDiscountAmount(accountBalanceVo
							.getStopBalance());
					parkOrder.setPayStatus(0);// 支付状态0 未支付 1已支付
					parkOrder.setOrderStatus(1);// 0进行中，1待支付，2待评价，3已完成
					// 未支付金额
					parkOrder.setNopayAmount(ECCalculateUtils.sub(add,
							accountBalanceVo.getStopBalance()));
					ab.setStopBalance(0.0);// 余额
				} else {// 余额>未支付
					parkOrder.setDiscountAmount(add);
					parkOrder.setPayStatus(1);// 支付状态0 未支付 1已支付
					parkOrder.setOrderStatus(2);// 0进行中，1待支付，2待评价，3已完成
					parkOrder.setNopayAmount(0.0);// 未支付金额
					ab.setStopBalance(ECCalculateUtils.sub(
							accountBalanceVo.getStopBalance(), add));// 余额
				}
				// 更新余额数据
				accountBalanceDao.update(ab);
				// 更新地锁状态
				CParkLock pl = new CParkLock();
				pl.setParkLockNo(parkOrder.getParkLockNo());
				pl.setParkingLockStatus(0);
				pl.setLockStatus(1);
				cparklockDao.update(pl);
				// 车位更新
				// 更新分层车位
				ParkingLockVo parkLock = cparklockDao.getParkLock(parkOrder
						.getParkLockNo());
				pliesNumberDao.upSpaceNumTwo(parkLock.getPliesNumberNo());
				// 更新停车场车位
				parkingDao.upParkSpaceTwo(parkLock.getSpaceType(),
						parkLock.getParkingNo());

				// 调用Dao执行更新操作，并判断更新语句执行结果
				int count = cParkOrderDao.update(parkOrder);
				if (count > 0) {
					resultInfo.setCode(Constant.SECCUESS);
				} else {
					resultInfo.setCode(Constant.FAIL);
				}
				resultInfo.setData(parkOrder);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
				TransactionAspectSupport.currentTransactionStatus()
						.setRollbackOnly();
				resultInfo.setCode(Constant.FAIL);
				resultInfo.setMsg(Constant.ERR_MSG_EXCEPTION_CATCHED);
			}
		}

		return resultInfo;
	}

	// 查询当前会员订单
	@Override
	public List<CParkOrder> getOrder(String menberNo) {
		List<CParkOrder> order = cParkOrderDao.getOrder(menberNo);
		order = order == null ? new ArrayList<CParkOrder>(0) : order;
		return order;
	}

	// 立即--降锁并生成订单
	@Transactional(propagation = Propagation.SUPPORTS)
	public ResultInfo<LockOrder> setOrder(String memberNo, Integer source,
			String lon, String lat, String spaceNo, String parkNo,
			boolean flag, String orderNo) {
		ResultInfo<LockOrder> orderInfo = new ResultInfo<LockOrder>();
		// 获取账户余额
		Member member = memberDao.get(memberNo);
		AccountBalanceVo accountBalanceVo = accountBalanceDao
				.pageListTwo_No(memberNo);
		if (accountBalanceVo != null) {
			String stopAmount = sysParamService
					.getParamValueByParamKey("STOP_AMOUNT");
			if (Double.valueOf(stopAmount).compareTo(
					accountBalanceVo.getStopBalance()) > 0) {
				orderInfo.setCode(Constant.NOT_SUFFICIENT_FUNDS);
				orderInfo.setMsg("最低余额不能低于" + stopAmount + "元");
				return orderInfo;
			}
		} else {
			orderInfo.setCode(Constant.NOT_SUFFICIENT_FUNDS);
			orderInfo.setMsg("此会员没有余额，请先充值，再降锁");
			return orderInfo;
		}

		// 获取地锁信息
		ParkingLockVo cParkLock = cparklockDao.getLock(parkNo, spaceNo);
		// 验证地锁
		ResultInfo<Object> resultInfoLock = cParkLockService.resultInfoLock(
				cParkLock, flag);
		if (resultInfoLock != null
				&& Constant.SECCUESS.equals(resultInfoLock.getCode())) {
			CParking park = parkingDao.get(cParkLock.getParkingNo());
			try {// 验证位置
				if (StringUtils.isEmpty(lat) || StringUtils.isEmpty(lon)) {
					orderInfo.setCode(Constant.FAIL);
					orderInfo.setMsg("无效的经纬度");
					return orderInfo;
				}
				Double param = Double.valueOf(sysParamService
						.getParamValueByParamKey("DROP_LOCK_DISTANCE"));
				Double distance = MapUtils.getDistance(Double.valueOf(lat),
						Double.valueOf(lon),
						Double.valueOf(park.getLatitude()),
						Double.valueOf(park.getLongitude()));
				if (distance.compareTo(param) > 0) {
					orderInfo.setCode(Constant.FAIL);
					orderInfo.setMsg("请在地所附近操作地锁");
					return orderInfo;
				}
			} catch (NumberFormatException e) {
				orderInfo.setCode(Constant.FAIL);
				orderInfo.setMsg("参数有误");
				return orderInfo;
			}
			String url = "";
			if (cParkLock.getParkingLockType() != null) {
				url = cParkLock.getParkingLockType() == 0 ? ConstantCd.moveLockDown
						: cParkLock.getParkingLockType() == 1 ? ConstantCd.MoveLockDown_NoCheck
								: "";
			} else {
				orderInfo.setCode(Constant.FAIL);
				orderInfo.setMsg("参数有误");
				return orderInfo;
			}
			/*--end-- 验证数据 */
			try {
				// 调用第三方地锁接口，执行降锁操作
				log.info("-------开始降锁--------");
				JSONObject rt = WgPost
						.post(cParkLock.getParkingLockCode(), url);
				log.info("降锁后返回的数据是：" + rt);
				System.out.println("降锁后输出的数据是：" + rt.getString("Success"));
				// FIXME 临时注释发送指令
				if (rt != null && rt.optBoolean("Success", false)) {
					// 降锁成功，创建地锁订单
					log.info("------------降锁成功，地锁订单创建 BEGIN-----------");
					// 生成订单
					Date date = new Date();
					CParkOrder parkOrder = new CParkOrder();
					if (flag) {// 预约后降锁更新订单
						parkOrder.setParkOrderNo(orderNo);
						parkOrder.setEntryTime(date);
						parkOrder.setOrderStatus(0);
						parkOrder.setParkingName(park.getParkingName());
						cParkOrderDao.update(parkOrder);
					} else {// 立即停车 降锁
						parkOrder.setParkOrderNo("PO"
								+ String.valueOf(new Date().getTime()));
						parkOrder.setEntryTime(date);
						parkOrder.setOrderStatus(0);
						parkOrder.setPayStatus(0);
						parkOrder.setOrderSource(source);
						parkOrder.setCreateTime(date);
						parkOrder.setUpdateTime(date);
						parkOrder.setParkingNo(park.getParkingNo());
						parkOrder.setParkingName(park.getParkingName());
						parkOrder.setParkType(park.getParkingType());
						parkOrder.setMemberNo(memberNo);
						parkOrder.setMemberName(member.getMemberName());
						parkOrder.setMobilePhone(member.getMobilePhone());
						parkOrder.setFinishType(2);
						parkOrder.setParkLockNo(cParkLock.getParkLockNo());
						cParkOrderDao.add(parkOrder);
						// 更新分层车位
						pliesNumberDao.upSpaceNum(cParkLock.getPliesNumberNo());
						// 更新停车场车位
						parkingDao.upParkSpace(cParkLock.getSpaceType(),
								cParkLock.getParkingNo());
					}
					// 更新地锁状态
					CParkLock pl = new CParkLock();
					pl.setParkLockNo(cParkLock.getParkLockNo());
					pl.setParkingLockStatus(1);
					pl.setLockStatus(0);
					cparklockDao.update(pl);
					LockOrder lo = new LockOrder();
					lo.setMemberNo(memberNo);
					lo.setOrderNo(parkOrder.getParkOrderNo());
					lo.setOrderStatus(parkOrder.getOrderStatus());
					lo.setOrderTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
							.format(parkOrder.getEntryTime()));
					lo.setStationName(parkOrder.getParkingName());
					orderInfo.setCode(Constant.SECCUESS);
					orderInfo.setMsg("降锁成功");
					orderInfo.setData(lo);
					return orderInfo;
				} else {
					// // 第三方降锁指令失败
					log.info("-------------降锁失败-------------");
					orderInfo.setCode(Constant.FAIL);
					orderInfo.setMsg("设备连接异常，请联系客服,异常信息:" + rt.get("Msg"));
					return orderInfo;
				}
			} catch (Exception e) {
				log.error(e);
				orderInfo.setCode(Constant.FAIL);
				orderInfo.setMsg("设备连接异常，请联系客服。");
				return orderInfo;
			}
		} else {
			orderInfo.setMsg(resultInfoLock.getMsg());
			orderInfo.setCode(resultInfoLock.getCode());
			return orderInfo;
		}
	}

	@Transactional(propagation = Propagation.SUPPORTS)
	public OrderInfo getOrderInfo(String parkOrderNo) {
		return cParkOrderDao.getOrderInfo(parkOrderNo);
	}

	// 升锁
	@Transactional
	public ResultInfo<Object> upLock(String orderNo) {
		ResultInfo<Object> info = new ResultInfo<Object>();
		try {
			if (orderNo == null || "".equals(orderNo)) {
				info.setCode(Constant.FAIL);
				info.setMsg("参数异常!");
				return info;
			}
			CParkOrder parkOrder = cParkOrderDao.get(orderNo);
			if (parkOrder != null) {
				if (memberDao.get(parkOrder.getMemberNo()) == null) {
					info.setMsg("用户信息不存在!");
					info.setCode(Constant.OTHER);// 返回 3 无数据
					return info;
				}
				if (parkOrder.getOrderStatus() != 0) {
					info.setMsg("你当前没有可结束的订单!");
					info.setCode(Constant.OTHER);// 返回 3 无数据
					return info;
				}
				if (parkOrder.getEntryTime() == null) {
					info.setMsg("你当前没有可结束的订单!");
					info.setCode(Constant.OTHER);// 返回 3 无数据
					return info;
				}
				ParkingLockVo parkLock = cparklockDao.getParkLock(parkOrder
						.getParkLockNo());
				if (parkLock == null) {
					info.setMsg("地锁信息不存在!");
					info.setCode(Constant.OTHER);// 返回 3 无数据
					return info;
				}
				// 获取 地锁url
				String url = parkLock.getParkingLockType() == 0 ? ConstantCd.moveLockUp
						: parkLock.getParkingLockType() == 1 ? ConstantCd.MoveLockUp_NoCheck
								: "";
				String lockCode = parkLock.getParkingLockCode();
				// 调用第三方地锁接口， //发起升锁
				try {
					JSONObject rt = WgPost.post(lockCode, url);
					log.info("------升锁后的上报数据：------" + rt);
					System.out.println("-----升锁后的上报数据：-------"
							+ rt.getString("Success"));
					if (rt != null && rt.optBoolean("Success", false)) {
						// 计算费用
						parkOrder.setFinishType(0);// 结束类型 1、后台结束 0,自动结束
						CParking cParking = parkingDao.get(parkOrder
								.getParkingNo());
						CParkBilling cParkBilling = parkBillingDao.get(cParking
								.getBillingSchemeNo());
						updateCParkOrder_two(parkOrder, cParkBilling, cParking,
								new Operator());
						LockOrder lo = new LockOrder();
						CParkOrder parkOrders = cParkOrderDao.get(orderNo);
						lo.setMemberNo(parkOrders.getMemberNo());
						lo.setOrderNo(parkOrders.getParkOrderNo());
						lo.setOrderStatus(parkOrders.getOrderStatus());
						lo.setOrderTime(new SimpleDateFormat(
								"yyyy-MM-dd HH:mm:ss").format(parkOrders
								.getEntryTime()));
						lo.setStationName(parkOrders.getParkingName());
						info.setData(lo);
						info.setCode(Constant.SECCUESS);
						info.setMsg("升锁成功");
					} else {
						log.info("----------升锁时下发鉴权失败------------");
						System.out
								.println("----------升锁时下发鉴权失败，升锁后的上报数据：------------"
										+ rt);
						info.setCode(Constant.FAIL);
						info.setMsg("设备连接异常，请联系客服。");
					}
					// FIXME 接收通知
				} catch (Exception e) {
					info.setCode(Constant.FAIL);
					info.setMsg("设备连接异常，请联系客服。");
				}

			} else {
				info.setCode(Constant.OTHER);
				info.setMsg("无订单信息!");
				return info;
			}
			return info;
		} catch (Exception e) {
			e.printStackTrace();
			info.setMsg("接口数据异常");
			info.setCode(Constant.FAIL);
			return info;
		}
	}

	// 预约并生成订单
	@Transactional(propagation = Propagation.SUPPORTS)
	public ResultInfo<LockOrder> setOrderAppointment(String memberNo,
			Integer source, String parkNo, String spaceNo) {
		ResultInfo<LockOrder> orderInfo = new ResultInfo<LockOrder>();
		// 获取账户余额
		Member member = memberDao.get(memberNo);
		AccountBalanceVo accountBalanceVo = accountBalanceDao
				.pageListTwo_No(memberNo);
		if (accountBalanceVo != null) {
			String stopAmount = sysParamService
					.getParamValueByParamKey("STOP_AMOUNT");
			if (Double.valueOf(stopAmount).compareTo(
					accountBalanceVo.getStopBalance()) > 0) {
				orderInfo.setCode(Constant.NOT_SUFFICIENT_FUNDS);
				orderInfo.setMsg("最低余额不能低于" + stopAmount + "元");
				return orderInfo;
			}
		} else {
			orderInfo.setCode(Constant.NOT_SUFFICIENT_FUNDS);
			orderInfo.setMsg("此会员没有余额，请先充值，再降锁");
			return orderInfo;
		}

		// 获取地锁信息
		ParkingLockVo cParkLock = cparklockDao.getLock(parkNo, spaceNo);
		// 验证地锁
		ResultInfo<Object> resultInfoLock = cParkLockService.resultInfoLock(
				cParkLock, false);
		if (resultInfoLock != null
				&& Constant.SECCUESS.equals(resultInfoLock.getCode())) {
			CParking park = parkingDao.get(cParkLock.getParkingNo());
			Date date = new Date();
			CParkOrder parkOrder = new CParkOrder();
			parkOrder.setParkOrderNo("PO"
					+ String.valueOf(new Date().getTime()));
			parkOrder.setAppointmentTime(new Date());
			parkOrder.setOrderStatus(0);
			parkOrder.setPayStatus(0);
			parkOrder.setOrderSource(source);
			parkOrder.setCreateTime(date);
			parkOrder.setUpdateTime(date);
			parkOrder.setParkingNo(park.getParkingNo());
			parkOrder.setParkingName(park.getParkingName());
			parkOrder.setParkType(park.getParkingType());
			parkOrder.setMemberNo(memberNo);
			parkOrder.setMemberName(member.getMemberName());
			parkOrder.setMobilePhone(member.getMobilePhone());
			parkOrder.setFinishType(2);
			parkOrder.setParkLockNo(cParkLock.getParkLockNo());
			cParkOrderDao.add(parkOrder);
			// 更新地锁状态
			CParkLock pl = new CParkLock();
			pl.setParkLockNo(cParkLock.getParkLockNo());
			pl.setLockStatus(2);
			cparklockDao.update(pl);
			// 更新分层车位
			pliesNumberDao.upSpaceNum(cParkLock.getPliesNumberNo());
			// 更新停车场车位
			parkingDao.upParkSpace(cParkLock.getSpaceType(),
					cParkLock.getParkingNo());
			LockOrder lo = new LockOrder();
			lo.setMemberNo(memberNo);
			lo.setOrderNo(parkOrder.getParkOrderNo());
			lo.setOrderStatus(parkOrder.getOrderStatus());
			lo.setOrderTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
					.format(parkOrder.getAppointmentTime()));
			lo.setStationName(parkOrder.getParkingName());
			orderInfo.setCode(Constant.SECCUESS);
			orderInfo.setMsg("预约成功");
			orderInfo.setData(lo);
			return orderInfo;
		} else {
			orderInfo.setMsg(resultInfoLock.getMsg());
			orderInfo.setCode(resultInfoLock.getCode());
			return orderInfo;
		}

	}

	// 取消预约
	@Transactional(propagation = Propagation.SUPPORTS)
	public ResultInfo<Object> cancelAppointment(String orderNo) {
		ResultInfo<Object> info = new ResultInfo<Object>();
		try {
			if (orderNo == null || "".equals(orderNo)) {
				info.setCode(Constant.FAIL);
				info.setMsg("参数异常!");
				return info;
			}
			CParkOrder parkOrder = cParkOrderDao.get(orderNo);
			if (parkOrder != null) {
				if (memberDao.get(parkOrder.getMemberNo()) == null) {
					info.setMsg("用户信息不存在!");
					info.setCode(Constant.OTHER);// 返回 3 无数据
					return info;
				}
				if (parkOrder.getOrderStatus() != 0) {
					info.setMsg("你当前没有可取消的订单!");
					info.setCode(Constant.OTHER);// 返回 3 无数据
					return info;
				} else if (parkOrder.getAppointmentTime() == null) {
					info.setMsg("你当前没有可取消的订单!");
					info.setCode(Constant.OTHER);// 返回 3 无数据
					return info;
				} else if (parkOrder.getAppointmentTime() != null
						&& parkOrder.getEntryTime() != null) {
					info.setMsg("你当前没有可取消的订单!");
					info.setCode(Constant.OTHER);// 返回 3 无数据
					return info;
				}
				ParkingLockVo parkLock = cparklockDao.getParkLock(parkOrder
						.getParkLockNo());
				if (parkLock == null) {
					info.setMsg("地锁信息不存在!");
					info.setCode(Constant.OTHER);// 返回 3 无数据
					return info;
				} else {
					// 计算费用
					parkOrder.setFinishType(0);// 结束类型 1、后台结束 0,自动结束
					CParking cParking = parkingDao
							.get(parkOrder.getParkingNo());
					CParkBilling cParkBilling = parkBillingDao.get(cParking
							.getBillingSchemeNo());
					updateCParkOrder_two(parkOrder, cParkBilling, cParking,
							new Operator());
					LockOrder lo = new LockOrder();
					CParkOrder parkOrders = cParkOrderDao.get(orderNo);
					lo.setMemberNo(parkOrders.getMemberNo());
					lo.setOrderNo(parkOrders.getParkOrderNo());
					lo.setOrderStatus(parkOrders.getOrderStatus());
					lo.setOrderTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
							.format(parkOrders.getAppointmentTime()));
					lo.setStationName(parkOrders.getParkingName());
					info.setData(lo);
					info.setCode(Constant.SECCUESS);
					info.setMsg("取消成功");
					return info;
				}
			} else {
				info.setCode(Constant.OTHER);
				info.setMsg("无订单信息!");
				return info;
			}
		} catch (Exception e) {
			e.printStackTrace();
			info.setMsg("接口数据异常");
			info.setCode(Constant.FAIL);
			return info;
		}
	}

	@Override
	public ResultInfo<CParkOrder> addCParkOrder(CParkOrder cParkOrder) {

		ResultInfo<CParkOrder> resultInfo = new ResultInfo<CParkOrder>();

		if (cParkOrder == null) { // 传入参数无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG);
			log.info(Constant.ERR_MSG_INVALID_ARG + " cParkOrder = "
					+ cParkOrder);
		} else {
			try {
				// 如果传入参数的主键为null，则调用生成主键的方法获取新的主键
				if (cParkOrder.getParkOrderNo() == null) {
					cParkOrder.setParkOrderNo(this.generatePK());
				}
				// 设置创建时间和更新时间为当前时间
				Date now = new Date();
				cParkOrder.setCreateTime(now);
				cParkOrder.setUpdateTime(now);

				// 填充默认值
				this.fillDefaultValues(cParkOrder);

				// 调用Dao执行插入操作
				cParkOrderDao.add(cParkOrder);
				resultInfo.setCode(Constant.SECCUESS);
				resultInfo.setData(cParkOrder);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
				TransactionAspectSupport.currentTransactionStatus()
						.setRollbackOnly();
				resultInfo.setCode(Constant.FAIL);
				resultInfo.setMsg(Constant.ERR_MSG_EXCEPTION_CATCHED);
			}
		}

		return resultInfo;

	}

	@Override
	public ResultInfo<CParkOrder> updateCParkOrder(CParkOrder cParkOrder) {
		ResultInfo<CParkOrder> resultInfo = new ResultInfo<CParkOrder>();

		if (cParkOrder == null || cParkOrder.getParkOrderNo() == null) { // 传入参数无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG);
			log.info(Constant.ERR_MSG_INVALID_ARG + " cParkOrder = "
					+ cParkOrder);
		} else {
			try {
				// 设置更新时间为当前时间
				cParkOrder.setUpdateTime(new Date());
				// 调用Dao执行更新操作，并判断更新语句执行结果
				int count = cParkOrderDao.update(cParkOrder);
				if (count > 0) {
					resultInfo.setCode(Constant.SECCUESS);
				} else {
					resultInfo.setCode(Constant.FAIL);
				}
				resultInfo.setData(cParkOrder);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
				TransactionAspectSupport.currentTransactionStatus()
						.setRollbackOnly();
				resultInfo.setCode(Constant.FAIL);
				resultInfo.setMsg(Constant.ERR_MSG_EXCEPTION_CATCHED);
			}
		}
		return resultInfo;
	}

	@Transactional(propagation = Propagation.SUPPORTS)
	public CParkOrder getOrderOver(String menberNo) {
		CParkOrder obj = null;
		if (menberNo == null || menberNo.length() == 0) { // 传入的主键无效时直接返回null
			log.info(Constant.ERR_MSG_INVALID_ARG + " id = " + menberNo);
			return obj;
		}
		try {
			// 调用dao，根据主键查询
			obj = cParkOrderDao.getOrderOver(menberNo);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		return obj;
	}
}