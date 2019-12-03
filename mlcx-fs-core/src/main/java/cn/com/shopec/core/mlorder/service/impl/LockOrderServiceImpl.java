package cn.com.shopec.core.mlorder.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import cn.com.shopec.common.Operator;
import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.common.constants.Constant;
import cn.com.shopec.common.dsUtils.ConstantCd;
import cn.com.shopec.common.dsUtils.WgPost;
import cn.com.shopec.common.utils.ECCalculateUtils;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.member.dao.MemberDao;
import cn.com.shopec.core.member.model.Member;
import cn.com.shopec.core.ml.dao.CAccountBalanceDao;
import cn.com.shopec.core.ml.dao.COrderDao;
import cn.com.shopec.core.ml.dao.LockBillingSchemeDao;
import cn.com.shopec.core.ml.dao.ParkingLockDao;
import cn.com.shopec.core.ml.model.CAccountBalance;
import cn.com.shopec.core.ml.model.LockBillingScheme;
import cn.com.shopec.core.ml.model.ParkingLock;
import cn.com.shopec.core.ml.vo.AccountBalanceVo;
import cn.com.shopec.core.ml.vo.OrderSimpleVo;
import cn.com.shopec.core.mlorder.dao.ChargeOrderDao;
import cn.com.shopec.core.mlorder.dao.LockOrderDao;
import cn.com.shopec.core.mlorder.model.ChargeOrder;
import cn.com.shopec.core.mlorder.model.LockOrder;
import cn.com.shopec.core.mlorder.service.LockOrderService;

/**
 * 地锁订单表 服务实现类
 */
@Service
public class LockOrderServiceImpl implements LockOrderService {
	private static final Log log = LogFactory.getLog(LockOrderServiceImpl.class);
	@Resource
	private LockOrderDao lockOrderDao;
	@Resource
	private ChargeOrderDao chargeOrderDao;
	@Resource
	private MemberDao memberDao;
	@Resource
	private ParkingLockDao parkingLockDao;
	@Resource
	private COrderDao cOrderDao;
	@Resource
	private CAccountBalanceDao accountBalanceDao;
	@Resource
	private LockBillingSchemeDao lockBillingSchemeDao;

	/**
	 * 根据查询条件，查询并返回LockOrder的列表
	 *
	 * @param q
	 *            查询条件
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<LockOrder> getLockOrderList(Query q) {
		List<LockOrder> list = null;
		try {
			// 直接调用Dao方法进行查询
			list = lockOrderDao.queryAll(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		// 如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<LockOrder>(0) : list;
		return list;
	}

	/**
	 * 根据查询条件，分页查询并返回LockOrder的分页结果
	 *
	 * @param q
	 *            查询条件
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public PageFinder<LockOrder> getLockOrderPagedList(Query q) {
		PageFinder<LockOrder> page = new PageFinder<LockOrder>();
		List<LockOrder> list = null;
		long rowCount = 0L;
		try {
			// 调用dao查询满足条件的分页数据
			list = lockOrderDao.pageList(q);
			// 调用dao统计满足条件的记录总数
			rowCount = lockOrderDao.count(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		// 如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<LockOrder>(0) : list;
		// 将分页数据和记录总数设置到分页结果对象中
		page.setData(list);
		page.setRowCount(rowCount);
		return page;
	}

	/**
	 * 根据主键，查询并返回一个LockOrder对象
	 *
	 * @param id
	 *            主键id
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public LockOrder getLockOrder(String id) {
		LockOrder obj = null;
		if (id == null || id.length() == 0) { // 传入的主键无效时直接返回null
			log.info(Constant.ERR_MSG_INVALID_ARG + " id = " + id);
			return obj;
		}
		try {
			// 调用dao，根据主键查询
			obj = lockOrderDao.get(id);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return obj;
	}

	/**
	 * 根据主键数组，查询并返回一组LockOrder对象
	 *
	 * @param ids
	 *            主键数组，数组中的主键值应当无重复值，如有重复值时，则返回的列表长度可能小于传入的数组长度
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<LockOrder> getLockOrderByIds(String[] ids) {
		List<LockOrder> list = null;
		if (ids == null || ids.length == 0) {
			log.info(Constant.ERR_MSG_INVALID_ARG + " ids is null or empty array.");
		} else {
			try {
				// 调用dao，根据主键数组查询
				list = lockOrderDao.getByIds(ids);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
		}
		// 如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<LockOrder>(0) : list;
		return list;
	}

	/**
	 * 根据主键，删除特定的LockOrder记录
	 *
	 * @param id
	 *            主键id
	 * @param operator
	 *            操作人对象
	 * @return
	 */
	@Transactional
	public ResultInfo<LockOrder> delLockOrder(String id, Operator operator) {
		ResultInfo<LockOrder> resultInfo = new ResultInfo<LockOrder>();
		if (id == null || id.length() == 0) { // 传入的主键无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG);
			log.info(Constant.ERR_MSG_INVALID_ARG + " id = " + id);
			return resultInfo;
		}
		try {
			// 调用Dao执行删除，并判断删除语句执行结果
			int count = lockOrderDao.delete(id);
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
	 * 新增一条LockOrder记录，执行成功后传入对象及返回对象的主键属性值不为null
	 *
	 * @param lockOrder
	 *            新增的LockOrder数据（如果无特殊需求，新增对象的主键值请保留为null）
	 * @param operator
	 *            操作人对象
	 * @return
	 */
	@Transactional
	public ResultInfo<LockOrder> addLockOrder(LockOrder lockOrder, Operator operator) {
		ResultInfo<LockOrder> resultInfo = new ResultInfo<LockOrder>();
		if (lockOrder == null) { // 传入参数无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG);
			log.info(Constant.ERR_MSG_INVALID_ARG + " lockOrder = " + lockOrder);
		} else {
			try {
				// 如果传入参数的主键为null，则调用生成主键的方法获取新的主键
				if (lockOrder.getOrderNo() == null) {
					lockOrder.setOrderNo(this.generatePK());
				}
				// 如果传入的操作人不为null，则设置新增记录的操作人类型和操作人id
				if (operator != null) {
					lockOrder.setOperatorType(operator.getOperatorType());
					lockOrder.setOperatorId(operator.getOperatorId());
				}
				// 设置创建时间和更新时间为当前时间
				Date now = new Date();
				lockOrder.setCreateTime(now);
				lockOrder.setUpdateTime(now);
				// 填充默认值
				this.fillDefaultValues(lockOrder);
				// 调用Dao执行插入操作
				lockOrderDao.add(lockOrder);
				resultInfo.setCode(Constant.SECCUESS);
				resultInfo.setData(lockOrder);
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
	 * 根据主键，更新一条LockOrder记录
	 *
	 * @param lockOrder
	 *            更新的LockOrder数据，且其主键不能为空
	 * @param operator
	 *            操作人对象
	 * @return
	 */
	@Transactional
	public ResultInfo<LockOrder> updateLockOrder(LockOrder lockOrder, Operator operator) {
		ResultInfo<LockOrder> resultInfo = new ResultInfo<LockOrder>();
		if (lockOrder == null || lockOrder.getOrderNo() == null) { // 传入参数无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG);
			log.info(Constant.ERR_MSG_INVALID_ARG + " lockOrder = " + lockOrder);
		} else {
			try {
				// 如果传入的操作人不为null，则设置更新记录的操作人类型和操作人id
				if (operator != null) {
					lockOrder.setOperatorType(operator.getOperatorType());
					lockOrder.setOperatorId(operator.getOperatorId());
				}
				// 设置更新时间为当前时间
				lockOrder.setUpdateTime(new Date());
				// 调用Dao执行更新操作，并判断更新语句执行结果
				int count = lockOrderDao.update(lockOrder);
				if (count > 0) {
					resultInfo.setCode(Constant.SECCUESS);
				} else {
					resultInfo.setCode(Constant.FAIL);
				}
				resultInfo.setData(lockOrder);
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
	 * 根据主键，更新一条LockOrder记录
	 *
	 * @param lockOrder
	 *            更新的LockOrder数据，且其主键不能为空
	 * @param operator
	 *            操作人对象
	 * @return
	 */
	@Transactional
	public ResultInfo<LockOrder> updateLockOrder_two(LockOrder lockOrder, Operator operator,LockBillingScheme lockBillingScheme ) {
		ResultInfo<LockOrder> resultInfo = new ResultInfo<LockOrder>();
		if (lockOrder == null || lockOrder.getOrderNo() == null) { // 传入参数无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG);
			log.info(Constant.ERR_MSG_INVALID_ARG + " lockOrder = " + lockOrder);
		} else {
			try {
				// 如果传入的操作人不为null，则设置更新记录的操作人类型和操作人id
				if (operator != null) {
					lockOrder.setOperatorType(operator.getOperatorType());
					lockOrder.setOperatorId(operator.getOperatorId());
				}
				// 设置更新时间为当前时间
				lockOrder.setUpdateTime(new Date());
			
				//计算停车总时长(分钟)
				long timeLengt_o=(lockOrder.getOrderEndTime().getTime()-
						lockOrder.getOrderStartTime().getTime())/(1000*60);//商
				long timeLength_t=(lockOrder.getOrderEndTime().getTime()-
						lockOrder.getOrderStartTime().getTime())%(1000*60);//余
				//总时长(分钟)
				int timeLength=(int) (timeLength_t==0?timeLengt_o:timeLengt_o+1);
				lockOrder.setOrderTimeLength(timeLength);//总时长
				//总时长/单位时间  
				int orderAmount=timeLength%lockBillingScheme.getUnitTime()==0?
						timeLength/lockBillingScheme.getUnitTime():timeLength/lockBillingScheme.getUnitTime()+1;
				boolean flag = false;//判断已取消或者不存在充电订单
				boolean flags=true;//判断是否推送
				//判断是否存在充电订单
				if(lockOrder.getChargeOrderNo()!=null&&!"".equals(lockOrder.getChargeOrderNo())){
					//获取充电订单
					ChargeOrder chargeOrder = chargeOrderDao.get(lockOrder.getChargeOrderNo());
					lockOrder.setLockType(0);//订单类型(0.充电,1停车)
					if(chargeOrder.getPushType()==1){//pushType 1.未推送订单 0.已推送订单
						lockOrder.setOrderStatus(4);//订单状态（0进行中，1待支付，2待评价，3已完成,4.未结算）
						flags=false;
					}else{
						if(chargeOrder.getOrderStatus()==4){//订单状态(0进行中，1待支付，2待评价，3已完成,4.已取消)
							lockOrder.setLockType(1);//订单类型(0.充电,1停车)
							flag=true;
						}else{
							lockOrder.setOrderFreeTime(lockBillingScheme.getFreeTime());//免费时长
							if(ECCalculateUtils.lt(timeLength, lockBillingScheme.getFreeTime())){//总时长小于免费时长
								//总时长小于免费时长
								lockOrder.setOrderOverTime(0);//超时时长
								lockOrder.setOrderAmount(0.0);//总金额
								lockOrder.setOvertimeAmount(0.0);//超时金额
							}else{
								//超时时长
								lockOrder.setOrderOverTime((int) ECCalculateUtils.sub(timeLength,lockBillingScheme.getFreeTime()));
								//超时时长/单位时间
								int orderAmount_o=lockOrder.getOrderOverTime()%lockBillingScheme.getUnitTime()==0?
										lockOrder.getOrderOverTime()/lockBillingScheme.getUnitTime():lockOrder.getOrderOverTime()/lockBillingScheme.getUnitTime()+1;
								//总金额
								lockOrder.setOrderAmount(ECCalculateUtils.mul(lockBillingScheme.getOvertimePrice(), orderAmount));
								//超时金额
								lockOrder.setOvertimeAmount(ECCalculateUtils.mul(lockBillingScheme.getOvertimePrice(), orderAmount_o));
							}
						}
						
					}
				}else{//充电订单不存在订单
					flag=true;
				}
				if(flag){//充电订单已取消或者不存在充电订单
					lockOrder.setLockType(1);//订单类型(0.充电,1停车)
					lockOrder.setOrderFreeTime(0);//免费时长
					lockOrder.setOrderOverTime(timeLength);//超时时长
					//总金额
					lockOrder.setOrderAmount(ECCalculateUtils.mul(lockBillingScheme.getOvertimePrice(), orderAmount));
					lockOrder.setOvertimeAmount(ECCalculateUtils.mul(lockBillingScheme.getOvertimePrice(), orderAmount));//超时金额
				}
				if(flags){//充电订单已推送
					CAccountBalance ab = new CAccountBalance();// 余额
					// 获取账户余额
					AccountBalanceVo accountBalanceVo = accountBalanceDao.pageListTwo_No(lockOrder.getMemberNo());
					ab.setAccountBalanceNo(accountBalanceVo.getAccountBalanceNo());
					Double stopBalance = accountBalanceVo.getStopBalance();//账户余额
					if(ECCalculateUtils.eq(lockOrder.getOvertimeAmount(), 0)){//超时金额为0
						lockOrder.setDiscountAmount(0.0);//余额抵扣金额
						lockOrder.setNopayAmount(0.0);//未付金额
						lockOrder.setOrderStatus(2);//订单状态（0进行中，1待支付，2待评价，3已完成,4.未结算）
						lockOrder.setPayStatus(1);//支付状态（0、未支付，1、已支付）
					}else{
						if(ECCalculateUtils.ge(stopBalance, lockOrder.getOvertimeAmount())){
							//余额大于超时金额 ECCalculateUtils.sub(stopBalance, lockOrder.getOvertimeAmount())
							lockOrder.setDiscountAmount(lockOrder.getOvertimeAmount());//余额抵扣金额
							lockOrder.setNopayAmount(0.0);//未付金额
							lockOrder.setOrderStatus(2);//订单状态（0进行中，1待支付，2待评价，3已完成,4.未结算）
							lockOrder.setPayStatus(1);//支付状态（0、未支付，1、已支付）
							//停车余额
							ab.setStopBalance(ECCalculateUtils.sub(stopBalance,lockOrder.getOvertimeAmount()));
						}else{//余额小于超时金额
							lockOrder.setDiscountAmount(stopBalance);//余额抵扣金额
							lockOrder.setNopayAmount(ECCalculateUtils.sub(lockOrder.getOvertimeAmount(),stopBalance));//未付金额
							lockOrder.setOrderStatus(1);//订单状态（0进行中，1待支付，2待评价，3已完成,4.未结算）
							lockOrder.setPayStatus(0);//支付状态（0、未支付，1、已支付）
							ab.setStopBalance(0.0);//停车余额
						}
						//更新余额
						accountBalanceDao.update(ab);
					}
				}
				//更新地锁状态
				ParkingLock parkingLock = new ParkingLock();
				parkingLock.setParkingLockNo(lockOrder.getParkingLockNo());
				parkingLock.setParkingLockStatus(0);
				parkingLockDao.update(parkingLock);
				// 调用Dao执行更新操作，并判断更新语句执行结果
				int count = lockOrderDao.update(lockOrder);
				if (count > 0) {
					resultInfo.setCode(Constant.SECCUESS);
				} else {
					resultInfo.setCode(Constant.FAIL);
				}
				resultInfo.setData(lockOrder);
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
	 * 为LockOrder对象设置默认值
	 *
	 * @param obj
	 */
	public void fillDefaultValues(LockOrder obj) {
		if (obj != null) {
			if (obj.getOrderStatus() == null) {
				obj.setOrderStatus(0);
			}
		}
	}

	@Override @Transactional public ResultInfo<OrderSimpleVo> roseLock(String orderNo) {
		ResultInfo<OrderSimpleVo> resultInfo = new ResultInfo<>();
		// 数据校验
		LockOrder lockOrder = lockOrderDao.get(orderNo);
		Member member = null;
		ParkingLock lock = null;
		if (lockOrder == null) {
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg("未找到订单");
			return resultInfo;
		}
		else if ((member = memberDao.get(lockOrder.getMemberNo())) == null) {
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg("用户信息异常");
			return resultInfo;
		}
		else if ((lock = parkingLockDao.get(lockOrder.getParkingLockNo())) == null) {
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg("订单信息异常");
			return resultInfo;
		}
//		else if (!new Integer(0).equals(lockOrder.getOrderStatus()) && new Integer(0)
//				.equals(lock.getParkingLockStatus())) {
//			resultInfo.setCode(Constant.FAIL);
//			resultInfo.setMsg("请勿重复升锁");
//			return resultInfo;
//		}
		else {
			String url = new Integer(0).equals(lock.getParkingLockType()) ?
					ConstantCd.moveLockUp :
					new Integer(1).equals(lock.getParkingLockType()) ? ConstantCd.MoveLockUp_NoCheck : "";
			String lockCode = lock.getParkingLockCode();
			// 调用第三方地锁接口， //发起升锁
			try {
				JSONObject rt = WgPost.post(lockCode, url);
				log.info("------升锁后的上报数据：------" + rt);
				System.out.println("-----升锁后的上报数据：-------" + rt.getString("Success"));
				if (rt != null && rt.optBoolean("Success", false)) {
					LockBillingScheme scheme = lockBillingSchemeDao.get(lockOrder.getLockBillingSchemeNo());
					Date now = new Date();
					lockOrder.setUpdateTime(now);
					lockOrder.setOrderEndTime(now);
					lockOrder.setFinishType(0);
					//计算费用
					updateLockOrder_two(lockOrder,new Operator(),scheme);
//					// 更新锁状态为升起
//					lock.setParkingLockStatus(0);
//					parkingLockDao.update(lock);
					resultInfo.setCode(Constant.SECCUESS);
					resultInfo.setMsg("升锁成功");
				}
				else {
					log.info("----------升锁时下发鉴权失败------------");
					System.out.println("----------升锁时下发鉴权失败，升锁后的上报数据：------------" + rt);
					resultInfo.setCode(Constant.FAIL);
					resultInfo.setMsg("设备连接异常，请联系客服。");
				}
				// FIXME 接收通知
			} catch (Exception e) {
				resultInfo.setCode(Constant.FAIL);
				resultInfo.setMsg("设备连接异常，请联系客服。");
			}
		}
		return resultInfo;
	}

	// @Override
	// public void changeReceiverOrder(ReceiverLockVo receiverLock) {
	// // 判断是否为升锁回调
	// if ("02".equals(receiverLock.getLockStatusCode())) {// 升起
	// ParkingLock lock = null;
	// try {
	// // 判断地锁是否存在
	// ParkingLock search = new ParkingLock();
	// search.setParkingLockCode(receiverLock.getLockId());
	// List<ParkingLock> lst = parkingLockDao.queryAll(new Query(search));
	// lock = lst.get(0);
	// // 验证本地地锁状态
	// if (new Integer(0).equals(lock.getParkingLockStatus())) {
	// log.info("重复请求");
	// }
	// } catch (IndexOutOfBoundsException e) {
	// log.info("未找到地锁");
	// }
	// // 判断地锁是否存在订单
	// try {
	// LockOrder search = new LockOrder();
	// search.setParkingLockNo(lock.getParkingLockNo());
	// // 判断地锁订单状态
	// search.setOrderStatus(0);
	// List<LockOrder> lst = lockOrderDao.queryAll(new Query(search));
	// LockOrder order = lst.get(0);
	// // 修改地锁及订单状态
	// Date now = new Date();
	// order.setUpdateTime(now);
	// order.setFinishType(0);// 正常结束
	// // 判断地锁订单应付金额是否为0，如果是，则直接跳转待评价，已支付状态
	// if (Double.valueOf(0).equals(order.getNopayAmount())) {
	// order.setOrderStatus(2);// 待评价
	// order.setPayStatus(1);// 已支付
	// lockOrderDao.update(order);
	// } else {
	// order.setOrderStatus(1);// 待评价
	// order.setPayStatus(0);// 未支付
	// lockOrderDao.update(order);
	// }
	//
	// lock.setParkingLockStatus(0);
	// lock.setUpdateTime(now);
	// parkingLockDao.update(lock);
	// } catch (NullPointerException e) {
	// log.info("未找到地锁");
	// } catch (IndexOutOfBoundsException e) {
	// log.info("当前地锁无进行中订单");
	// }
	// } else {
	// log.info("当前地锁状态：" + receiverLock.getDeviceStatusMsg());
	// }
	// }
	//

	@Override public void changeReceiverOrder(String receiverLock) {
		// 判断是否为升锁回调
		JSONObject jsonobject = JSONObject.fromObject(receiverLock);
		if ("02".equals(jsonobject.getString("LockStatusCode"))) {// 地锁升起(竖起)
			ParkingLock lock = null;
			try {
				// 判断地锁是否存在
				ParkingLock search = new ParkingLock();
				search.setParkingLockCode(jsonobject.getString("LockId"));// 地锁编码
				List<ParkingLock> lst = parkingLockDao.queryAll(new Query(search));
				if (lst.size() > 0) {
					lock = lst.get(0);
					// 验证本地地锁状态
					if (new Integer(0).equals(lock.getParkingLockStatus())) {// 地锁处于上升状态
						log.info("地锁处于上升状态");
						return;
					}
					else {
						lock.setParkingLockStatus(0);
					}
				}
			} catch (IndexOutOfBoundsException e) {
				log.info("未找到地锁");
			}
			// 判断地锁是否存在订单
			try {
				LockOrder order = new LockOrder();
				order.setParkingLockNo(lock.getParkingLockNo());
				// 判断地锁订单状态
				order.setOrderStatus(0);// 进行中
				List<LockOrder> lst = lockOrderDao.queryAll(new Query(order));
				if (lst.size() > 0) {
					LockOrder lockOrder = lst.get(0);
					LockBillingScheme scheme = lockBillingSchemeDao.get(lockOrder.getLockBillingSchemeNo());
					// 修改地锁及订单状态
					Date now = new Date();
					//
					lockOrder.setUpdateTime(now);
					lockOrder.setOrderEndTime(now);
					lockOrder.setFinishType(0);
					updateLockOrder_two(lockOrder,new Operator(),scheme);
					//计算费用
					//					lockOrderDao.update(lockOrder);
					//					// 更新锁状态为升起
					//					lock.setParkingLockStatus(0);
					//					parkingLockDao.update(lock);
				}
			} catch (NullPointerException e) {
				log.info("--------地锁为空，未找到地锁---------");
			} catch (IndexOutOfBoundsException e) {
				log.info("当前地锁无进行中订单");
			}
		}
		else {// 地锁处于平躺（降锁中）
			log.info("------当前地锁状态：--------" + jsonobject.get("LockStatusCode"));
			return;
		}
	}

	@Override
	public LockOrder getOrderMember(String member,String lockNo) {
	
		return lockOrderDao.getOrderMember(member,lockNo);
	}
}
