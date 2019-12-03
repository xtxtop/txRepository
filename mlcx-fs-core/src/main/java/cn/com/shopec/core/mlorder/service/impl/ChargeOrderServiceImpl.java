package cn.com.shopec.core.mlorder.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import cn.com.shopec.core.ml.dao.*;
import cn.com.shopec.core.mlorder.service.LockOrderService;
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
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.member.dao.MemberDao;
import cn.com.shopec.core.member.model.Member;
import cn.com.shopec.core.ml.model.CAccountBalance;
import cn.com.shopec.core.ml.model.ChargingGunInfo;
import cn.com.shopec.core.ml.model.ChargingPile;
import cn.com.shopec.core.ml.model.ChargingRecord;
import cn.com.shopec.core.ml.model.ChargingStation;
import cn.com.shopec.core.ml.service.CAccountBalanceService;
import cn.com.shopec.core.ml.service.COrderService;
import cn.com.shopec.core.ml.vo.OrderDetailVo;
import cn.com.shopec.core.ml.vo.OrderSimpleVo;
import cn.com.shopec.core.mlorder.dao.ChargeOrderDao;
import cn.com.shopec.core.mlorder.dao.LockOrderDao;
import cn.com.shopec.core.mlorder.model.ChargeOrder;
import cn.com.shopec.core.mlorder.model.LockOrder;
import cn.com.shopec.core.mlorder.service.ChargeOrderService;
import cn.com.shopec.core.system.common.PrimarykeyConstant;
import cn.com.shopec.core.system.service.PrimarykeyService;
import cn.com.shopec.core.system.service.SysParamService;
import net.sf.json.JSONObject;

/**
 * 充电订单表 服务实现类
 */
@Service
public class ChargeOrderServiceImpl implements ChargeOrderService {
	private static final Log log = LogFactory.getLog(ChargeOrderServiceImpl.class);
	@Resource
	private ChargeOrderDao chargeOrderDao;
	@Resource
	private LockOrderDao lockOrderDao;
	@Resource
	private COrderDao cOrderDao;
	@Resource
	private ChargingPileDao chargingPileDao;
	@Resource
	private ChargingGunInfoDao chargingGunInfoDao;
	@Resource
	private MemberDao memberDao;
	@Resource
	private CAccountBalanceDao cAccountBalanceDao;
	@Resource
	private PrimarykeyService primarykeyService;
	@Resource
	private COrderService cOrderService;
	@Resource
	private ChargingStationDao chargingStationDao;
	@Resource
	private SysParamService sysParamService;
	@Resource
	private CAccountBalanceService caccountBalanceService;
	@Value("${pile_path}")
	private String pile_path;
	@Resource
	private ChargingRecordDao chargingRecordDao;
	@Resource
	private LockBillingSchemeDao lockBillingSchemeDao;
	@Resource
	private LockOrderService lockOrderService;

	/**
	 * 根据查询条件，查询并返回ChargeOrder的列表
	 *
	 * @param q
	 *            查询条件
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<ChargeOrder> getChargeOrderList(Query q) {
		List<ChargeOrder> list = null;
		try {
			// 直接调用Dao方法进行查询
			list = chargeOrderDao.queryAll(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		// 如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<ChargeOrder>(0) : list;
		return list;
	}

	/**
	 * 根据查询条件，分页查询并返回ChargeOrder的分页结果
	 *
	 * @param q
	 *            查询条件
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public PageFinder<ChargeOrder> getChargeOrderPagedList(Query q) {
		PageFinder<ChargeOrder> page = new PageFinder<ChargeOrder>();
		List<ChargeOrder> list = null;
		long rowCount = 0L;
		try {
			// 调用dao查询满足条件的分页数据
			list = chargeOrderDao.pageList(q);
			// 调用dao统计满足条件的记录总数
			rowCount = chargeOrderDao.count(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		// 如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<ChargeOrder>(0) : list;
		// 将分页数据和记录总数设置到分页结果对象中
		page.setData(list);
		page.setRowCount(rowCount);
		return page;
	}

	/**
	 * 根据主键，查询并返回一个ChargeOrder对象
	 *
	 * @param id
	 *            主键id
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public ChargeOrder getChargeOrder(String id) {
		ChargeOrder obj = null;
		if (id == null || id.length() == 0) { // 传入的主键无效时直接返回null
			log.info(Constant.ERR_MSG_INVALID_ARG + " id = " + id);
			return obj;
		}
		try {
			// 调用dao，根据主键查询
			obj = chargeOrderDao.get(id);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return obj;
	}

	/**
	 * 根据主键数组，查询并返回一组ChargeOrder对象
	 *
	 * @param ids
	 *            主键数组，数组中的主键值应当无重复值，如有重复值时，则返回的列表长度可能小于传入的数组长度
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<ChargeOrder> getChargeOrderByIds(String[] ids) {
		List<ChargeOrder> list = null;
		if (ids == null || ids.length == 0) {
			log.info(Constant.ERR_MSG_INVALID_ARG + " ids is null or empty array.");
		} else {
			try {
				// 调用dao，根据主键数组查询
				list = chargeOrderDao.getByIds(ids);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
		}
		// 如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<ChargeOrder>(0) : list;
		return list;
	}

	/**
	 * 根据主键，删除特定的ChargeOrder记录
	 *
	 * @param id
	 *            主键id
	 * @param operator
	 *            操作人对象
	 * @return
	 */
	@Transactional
	public ResultInfo<ChargeOrder> delChargeOrder(String id, Operator operator) {
		ResultInfo<ChargeOrder> resultInfo = new ResultInfo<ChargeOrder>();
		if (id == null || id.length() == 0) { // 传入的主键无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG);
			log.info(Constant.ERR_MSG_INVALID_ARG + " id = " + id);
			return resultInfo;
		}
		try {
			// 调用Dao执行删除，并判断删除语句执行结果
			int count = chargeOrderDao.delete(id);
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
	 * 新增一条ChargeOrder记录，执行成功后传入对象及返回对象的主键属性值不为null
	 *
	 * @param chargeOrder
	 *            新增的ChargeOrder数据（如果无特殊需求，新增对象的主键值请保留为null）
	 * @param operator
	 *            操作人对象
	 * @return
	 */
	@Transactional
	public ResultInfo<ChargeOrder> addChargeOrder(ChargeOrder chargeOrder, Operator operator) {
		ResultInfo<ChargeOrder> resultInfo = new ResultInfo<ChargeOrder>();
		if (chargeOrder == null) { // 传入参数无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG);
			log.info(Constant.ERR_MSG_INVALID_ARG + " chargeOrder = " + chargeOrder);
		} else {
			try {
				// 如果传入参数的主键为null，则调用生成主键的方法获取新的主键
				if (chargeOrder.getOrderNo() == null) {
					chargeOrder.setOrderNo(this.generatePK());
				}
				// 如果传入的操作人不为null，则设置新增记录的操作人类型和操作人id
				if (operator != null) {
					chargeOrder.setOperatorType(operator.getOperatorType());
					chargeOrder.setOperatorId(operator.getOperatorId());
				}
				// 设置创建时间和更新时间为当前时间
				Date now = new Date();
				chargeOrder.setCreateTime(now);
				chargeOrder.setUpdateTime(now);
				// 填充默认值
				this.fillDefaultValues(chargeOrder);
				// 调用Dao执行插入操作
				chargeOrderDao.add(chargeOrder);
				resultInfo.setCode(Constant.SECCUESS);
				resultInfo.setData(chargeOrder);
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
	 * 根据主键，更新一条ChargeOrder记录
	 *
	 * @param chargeOrder
	 *            更新的ChargeOrder数据，且其主键不能为空
	 * @param operator
	 *            操作人对象
	 * @return
	 */
	@Transactional
	public ResultInfo<ChargeOrder> updateChargeOrder(ChargeOrder chargeOrder, Operator operator) {
		ResultInfo<ChargeOrder> resultInfo = new ResultInfo<ChargeOrder>();
		if (chargeOrder == null || chargeOrder.getOrderNo() == null) { // 传入参数无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG);
			log.info(Constant.ERR_MSG_INVALID_ARG + " chargeOrder = " + chargeOrder);
		} else {
			try {
				// 如果传入的操作人不为null，则设置更新记录的操作人类型和操作人id
				if (operator != null) {
					chargeOrder.setOperatorType(operator.getOperatorType());
					chargeOrder.setOperatorId(operator.getOperatorId());
				}
				// 设置更新时间为当前时间
				chargeOrder.setUpdateTime(new Date());
				// 调用Dao执行更新操作，并判断更新语句执行结果
				int count = chargeOrderDao.update(chargeOrder);
				if (count > 0) {
					resultInfo.setCode(Constant.SECCUESS);
				} else {
					resultInfo.setCode(Constant.FAIL);
				}
				resultInfo.setData(chargeOrder);
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
	 * 根据主键，更新一条ChargeOrder记录 后台结束时操作
	 *
	 * @param chargeOrder
	 *            更新的ChargeOrder数据，且其主键不能为空
	 * @param operator
	 *            操作人对象
	 * @return
	 */
	@Transactional
	public ResultInfo<ChargeOrder> updateChargeOrder_two(ChargeOrder chargeOrder, Operator operator) {
		ResultInfo<ChargeOrder> resultInfo = new ResultInfo<ChargeOrder>();
		if (chargeOrder == null || chargeOrder.getOrderNo() == null) { // 传入参数无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG);
			log.info(Constant.ERR_MSG_INVALID_ARG + " chargeOrder = " + chargeOrder);
		} else {
			try {
				// 如果传入的操作人不为null，则设置更新记录的操作人类型和操作人id
				if (operator != null) {
					chargeOrder.setOperatorType(operator.getOperatorType());
					chargeOrder.setOperatorId(operator.getOperatorId());
				}
				// 设置更新时间为当前时间
				chargeOrder.setUpdateTime(new Date());
				// 结束充电
				chargeOrder.setOrderStatus(1);// 订单状态 1、待支付
				chargeOrder.setOrderEndTime(new Date());// 结束时间
				chargeOrder.setFinishType(1);// 结束类型 1、后台结束
				// 计算时长
				long timeNum = (chargeOrder.getOrderEndTime().getTime() - chargeOrder.getOrderStartTime().getTime())
						% (1000 * 60);
				int timeNums = (int) ((chargeOrder.getOrderEndTime().getTime()
						- chargeOrder.getOrderStartTime().getTime()) / (1000 * 60));
				chargeOrder.setOrderTimeLength(timeNum == 0 ? timeNums : timeNums + 1);
				// 调用Dao执行更新操作，并判断更新语句执行结果
				int count = chargeOrderDao.update(chargeOrder);
				if (count > 0) {
					resultInfo.setCode(Constant.SECCUESS);
				} else {
					resultInfo.setCode(Constant.FAIL);
				}
				resultInfo.setData(chargeOrder);
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
		return "C" + primarykeyService.getValueByBizType(PrimarykeyConstant.orderType);
	}

	/**
	 * 为ChargeOrder对象设置默认值
	 *
	 * @param obj
	 */
	public void fillDefaultValues(ChargeOrder obj) {
		if (obj != null) {
			if (obj.getOrderStatus() == null) {
				obj.setOrderStatus(0);
			}
		}
	}

	@Override
	@Transactional
	public ResultInfo<OrderDetailVo> stopChargeOrder(String orderNo) {
		ResultInfo<OrderDetailVo> resultInfo = new ResultInfo<>();
		ChargeOrder order = chargeOrderDao.get(orderNo);
		if (order == null) {
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg("地锁订单不存在");
		} else if (!new Integer(0).equals(order.getOrderStatus())) {
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg("地锁订单状态有误：状态为：" + order.getOrderStatus());
		} else {
			// 调用停止充电
			JSONObject rt = null;
			ChargingGunInfo gun = null;
			try {
				gun = chargingGunInfoDao.get(order.getChargingGunNo());
				String token = cOrderDao.getToken();
				Map<String, String> params = new HashMap<>();
				params.put("gn", "tzcd");
				params.put("cdzbm", order.getChargingPileNo());
				params.put("qbh", gun.getChargingGunCode());
				params.put("token", token);
				log.info("---------停止充电下发鉴权开始------------");
				rt = cOrderService.issueCdzCmd(pile_path, params);
				log.info("---------停止充电下发鉴权结束，返回结果为：------------" + rt);
			} catch (NullPointerException e) {
				log.info("停止充电下发鉴权返回结果为null");
				resultInfo.setCode(Constant.FAIL);
				resultInfo.setMsg("地锁订单信息有误");
			}
			// 修改订单状
			if (rt == null) {
				resultInfo.setCode(Constant.FAIL);
				resultInfo.setMsg("设备连接异常，由于下发鉴权返回信息为空，请联系客服。");
			} else if ("success".equals(rt.optString("jl", "fail"))) {
				Date now = new Date();
				// order.setOrderStatus(1);// 待支付状态
				order.setUpdateTime(now);
				order.setFinishType(0);
				order.setOrderEndTime(now);
				chargeOrderDao.update(order);
				// FIXME 停止充电
				gun.setWorkStatus(2);
				chargingGunInfoDao.update(gun);
				resultInfo.setCode(Constant.SECCUESS);
				resultInfo.setMsg("操作成功");
			} else {
				resultInfo.setCode(Constant.FAIL);
				resultInfo.setMsg("设备连接异常，由于下发鉴权返回信息为失败，请联系客服。");
			}
		}
		return resultInfo;
	}

	@Override
	@Transactional
	public ResultInfo<OrderSimpleVo> startChargeOrder(String memberNo, String chargingGunNo, String deviceTp) {
		ResultInfo<OrderSimpleVo> resultInfo = new ResultInfo<>();
		ChargingGunInfo gun = chargingGunInfoDao.get(chargingGunNo);
		Member member = memberDao.get(memberNo);
		// 验证参数
		if (gun == null || !new Integer(2).equals(gun.getWorkStatus())) {
			log.info("--------枪为null或者状态不为待机-----------");
			System.out.println("--------枪为null或者状态不为待机-----------");
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg("充电枪信息有误");
		} else if (member == null) {
			log.info("---------没有获取到会员信息----------");
			System.out.println("---------没有获取到会员信息----------");
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg("用户信息异常");
		} else {
			ChargingPile pile = chargingPileDao.get(gun.getChargingPileNo());
			LockOrder lockOrder = null;
			if (pile == null) {
				log.info("-----------充电桩为null----------");
				System.out.println("-----------充电桩为null----------");
				resultInfo.setCode(Constant.FAIL);
				resultInfo.setMsg("充电桩信息有误");
			} else {
				// 验证订单数据
				OrderSimpleVo search = new OrderSimpleVo();
				search.setMemberNo(memberNo);
				List<OrderSimpleVo> orders = cOrderDao.searchOrderListNoCategoryNotFinished(new Query(search));
				try {
					OrderSimpleVo vo = orders.get(0);
					switch (vo.getTp()) {
					case "2": {// 充电订单
						resultInfo.setCode("1".equals(vo.getOrderStatus()) ? Constant.NOPAY : Constant.NOFINSH);
						resultInfo.setMsg("1".equals(vo.getOrderStatus()) ? "充电枪有未支付订单" : "充电枪有未完成订单");
						resultInfo.setData(vo);
						break;
					}
					case "1": {// 地锁订单
						if ("1".equals(vo.getOrderStatus())) {
							resultInfo.setCode(Constant.NOPAY);
							resultInfo.setMsg("地锁有未支付订单");
							resultInfo.setData(vo);
						} else
							lockOrder = lockOrderDao.get(vo.getOrderNo());
						break;
					}
					}
				} catch (IndexOutOfBoundsException e) {
					resultInfo.setCode(Constant.FAIL);
					resultInfo.setMsg("请先停车后再进行充电");
				}

				if (null != member) {
					CAccountBalance balance = new CAccountBalance();
					balance.setMemberNo(memberNo);
					List<CAccountBalance> lst = caccountBalanceService.getCAccountBalanceList(new Query(balance));
					if (lst.size() > 0) {
						CAccountBalance cb = lst.get(0);// 获取会员余额
						Double chargeBalanceAmount = cb.getChargingBalance();// 充电余额
						String chargeAmount = sysParamService.getParamValueByParamKey("CHARGE_AMOUNT");
						if (Double.valueOf(chargeAmount).compareTo(chargeBalanceAmount) > 0) {
							resultInfo.setCode(Constant.NOT_SUFFICIENT_FUNDS);
							resultInfo.setMsg("最低余额不能低于" + chargeBalanceAmount + "元");
							return resultInfo;
						}
					} else {
						resultInfo.setCode(Constant.NOT_SUFFICIENT_FUNDS);
						resultInfo.setMsg("此会员没有余额，请先充值，再充电");
						return resultInfo;
					}
				}

				if (lockOrder != null && lockOrder.getOrderNo() != null)
					try {
						// 请求下发鉴权
						Map<String, String> params = new HashMap<>();
						params.put("gn", "xfjq");
						params.put("cdzbm", pile.getChargingPileNo());
						params.put("qbh", gun.getChargingGunCode());
						params.put("wlkh", "2000000000000000");
						params.put("je", "999");
						params.put("qdfs", "02");
						log.info("---------------充电开始下发鉴权-------------");
						JSONObject rt = cOrderService.issueCdzCmd(pile_path, params);
						log.info("-----------下发鉴权结果是：-----------" + rt);
						System.out.println("-----------下发鉴权结果是：-----------" + rt);
						if (rt == null) {
							log.info("-----------未获取到下发鉴权结果---------");
							System.out.println("-----------未获取到下发鉴权结果---------");
							resultInfo.setCode(Constant.FAIL);
							resultInfo.setMsg("设备连接异常，请联系客服。");
						} else if ("success".equals(rt.optString("jl", "fail"))) {
							// 创建充电订单
							log.info("----------下发鉴权成功，开始创建充电订单----------");
							Date now = new Date();
							ChargeOrder chargeOrder = new ChargeOrder();
							chargeOrder.setOrderNo(generatePK());
							chargeOrder.setStationNo(pile.getStationNo());
							ChargingStation chargingStation = chargingStationDao.get(pile.getStationNo());
							chargeOrder.setStationName(chargingStation.getStationName());
							chargeOrder.setChargingPileNo(pile.getChargingPileNo());
							chargeOrder.setChargingPileName(pile.getChargingPileName());
							chargeOrder.setChargingGunNo(gun.getChargingGunNo());
							chargeOrder.setMemberNo(member.getMemberNo());
							chargeOrder.setMemberName(member.getMemberName());
							chargeOrder.setMobilePhone(member.getMobilePhone());
							chargeOrder.setOrderStartTime(now);
							chargeOrder.setOrderStatus(0);
							chargeOrder.setPayStatus(0);
							chargeOrder.setOrderSource(deviceTp);
							chargeOrder.setCreateTime(now);
							chargeOrder.setServiceAmount(Constant.SERVICE_FEE);// 服务费默认为5
							chargeOrder.setOrderMemo(rt.optString("nr"));// 订单流水号
							chargeOrder.setPushType(0);
							chargeOrderDao.add(chargeOrder);
							// 更新地锁订单在充电后的计费时间
							lockOrder.setChargeOrderNo(chargeOrder.getOrderNo());
							lockOrder.setUpdateTime(now);
							lockOrderDao.update(lockOrder);
							// 更新枪状态为工作中
							gun.setWorkStatus(3);
							chargingGunInfoDao.update(gun);
							// 创建充电记录
							ChargingRecord chargingRecord = new ChargingRecord();
							chargingRecord.setRecordNo("CR"+String.valueOf(new Date().getTime()));
							chargingRecord.setChargingGunNo(lockOrder.getChargeOrderNo());// 枪编号
							chargingRecord.setChargingPileNo(pile.getChargingPileNo());// 充电桩编号
							chargingRecord.setCreateTime(new Date());
							chargingRecord.setSerialNumber(rt.optString("nr"));
							chargingRecordDao.add(chargingRecord);

							// 更新模型
							OrderSimpleVo vo = new OrderSimpleVo(chargeOrder);
							vo.setOrderStatus("0");
							resultInfo.setCode(Constant.SECCUESS);
							resultInfo.setMsg("操作成功");
							resultInfo.setData(vo);
						} else {
							log.info("---------- - 获取到下发鉴权结果为失败-------- - ");
							System.out.println("-----------获取到下发鉴权结果为失败---------");
							resultInfo.setCode(Constant.FAIL);
							resultInfo.setMsg("设备连接异常，请联系客服。");
						}
					} catch (NullPointerException e) {
						resultInfo.setCode(Constant.FAIL);
						resultInfo.setMsg("订单信息有误");
					}
			}
		}
		return resultInfo;
	}

	// @Override
	// @Transactional
	// public void changeReceiverOrder(ReceiverOrderVo receiverOrder) {
	// ChargeOrder search = new ChargeOrder();
	// search.setOrderMemo(receiverOrder.getSerial_number() == null ? "-1111" :
	// receiverOrder.getSerial_number());
	// List<ChargeOrder> lst = chargeOrderDao.queryAll(new Query(search));
	// ChargeOrder order;
	// ChargingGunInfo gun;
	// try {
	// order = lst.get(0);
	// gun = chargingGunInfoDao.get(order.getChargingGunNo());
	// } catch (IndexOutOfBoundsException e) {
	// log.info("订单不存在");
	// return;
	// }
	// CAccountBalance balanceSearch = new CAccountBalance();
	// search.setMemberNo(order.getMemberNo());
	// List<CAccountBalance> balances = cAccountBalanceDao.queryAll(new
	// Query(balanceSearch));
	// CAccountBalance balance = null;
	// if (balances != null && balances.size() > 0)
	// balance = balances.get(0);
	// if (new Integer(1).equals(order.getPayStatus()) || new
	// Integer(1).equals(order.getOrderStatus())) {
	// log.info("重复请求");
	// } else
	// try {
	// Double total_start =
	// ECCalculateUtils.round(Double.valueOf(receiverOrder.getTotal_start()),
	// 2);
	// Double total_finish =
	// ECCalculateUtils.round(Double.valueOf(receiverOrder.getTotal_finish()),
	// 2);
	// Double total_money =
	// ECCalculateUtils.round(Double.valueOf(receiverOrder.getTotal_money()),
	// 2);
	// SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
	// gun.setWorkStatus(2);
	// // order.setOrderStatus(1);
	// // order.setPayStatus(0);
	// order.setOrderStartTime(sdf.parse(receiverOrder.getStart_time()));
	// order.setOrderEndTime(sdf.parse(receiverOrder.getFinish_time()));
	// order.setChargeDegree(ECCalculateUtils.sub(total_finish, total_start));
	// order.setChargeAmount(total_money);
	// // 计算费用
	// if (balance != null) {
	// Double balance_money =
	// ECCalculateUtils.round(balance.getChargingBalance(), 2);
	// if (balance_money.compareTo(total_money) > 0) {
	// order.setOrderAmount(0d);
	// order.setNopayAmount(0d);// 应付金额
	// order.setOrderStatus(2);// 待评价
	// order.setPayStatus(1);// 已支付
	// balance.setChargingBalance(
	// ECCalculateUtils.round(ECCalculateUtils.sub(balance_money, total_money),
	// 2));
	// } else if (balance_money.compareTo(total_money) == 0) {
	// order.setOrderAmount(0d);
	// order.setNopayAmount(0d);// 应付金额
	// order.setOrderStatus(2);// 待评价
	// order.setPayStatus(1);// 已支付
	// balance.setChargingBalance(0d);
	// } else {
	// order.setOrderAmount(
	// ECCalculateUtils.round(ECCalculateUtils.sub(total_money, balance_money),
	// 2));
	// order.setOrderStatus(1);// 待支付
	// order.setPayStatus(0);// 未支付
	// order.setNopayAmount(
	// ECCalculateUtils.round(ECCalculateUtils.sub(total_money, balance_money),
	// 2));// 应付金额
	// balance.setChargingBalance(0d);
	// }
	// cAccountBalanceDao.update(balance);
	// } else
	// order.setOrderAmount(total_money);
	// // 计算时间
	// if (order.getOrderStartTime().compareTo(order.getOrderEndTime()) < 0) {
	// order.setOrderTimeLength(String.valueOf(
	// (order.getOrderEndTime().getTime() - order.getOrderStartTime().getTime())
	// / 60 * 1000d));
	// } else
	// order.setOrderTimeLength("0");
	// chargeOrderDao.update(order);
	// chargingGunInfoDao.update(gun);
	// } catch (ParseException e) {
	// log.info("日期格式有误");
	// log.info(e.getMessage());
	// }
	// }

	@Override
	@Transactional
	public void changeReceiverOrder(String receiverOrder) {
		ChargeOrder search = new ChargeOrder();
		JSONObject jsonobject = JSONObject.fromObject(receiverOrder);
		search.setOrderMemo(
				jsonobject.getString("Serial_number") == null ? "-1111" : jsonobject.getString("Serial_number"));
		List<ChargeOrder> lst = chargeOrderDao.queryAll(new Query(search));
		ChargeOrder order;
		ChargingGunInfo gun;
		Double elePrice;
		try {
			order = lst.get(0);
			String stationNo = order.getStationNo();
			elePrice = chargingStationDao.get(stationNo).getElectricPrice();// 场站的电价
			gun = chargingGunInfoDao.get(order.getChargingGunNo());
		} catch (IndexOutOfBoundsException e) {
			log.info("订单不存在");
			return;
		}
		CAccountBalance balanceSearch = new CAccountBalance();
		search.setMemberNo(order.getMemberNo());
		List<CAccountBalance> balances = cAccountBalanceDao.queryAll(new Query(balanceSearch));
		CAccountBalance balance = null;
		Double moneyBefore = 0d;
		if (balances != null && balances.size() > 0) {
			balance = balances.get(0);
			moneyBefore = balance.getChargingBalance();
		}

		if (new Integer(0).equals(order.getPayStatus()) && new Integer(1).equals(order.getOrderStatus())) {// 停止充电后，订单已是待支付状态
			try {
				Double total_start = ECCalculateUtils.round(Double.valueOf(jsonobject.getString("Total_start")), 2);
				Double total_finish = ECCalculateUtils.round(Double.valueOf(jsonobject.getString("Total_finish")), 2);
				Double total_money = ECCalculateUtils.round(Double.valueOf(jsonobject.getString("Total_money")), 2);
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String record_time = jsonobject.getString("Record_time");// 记录时间
				String serial_number = jsonobject.getString("Serial_number");// 交易流水号
				String start_time = jsonobject.getString("Start_time");// 开始时间
				String finish_time = jsonobject.getString("Finish_time");// 结束时间
				String opint = jsonobject.getString("Opint");// 尖时刻用电量
				// 计算尖时刻的金额
				Double opintAmount = ECCalculateUtils.round(elePrice * Double.valueOf(opint), 2);
				String peak = jsonobject.getString("Peak");// 峰时刻用电量
				// 计算峰时刻用电金额
				Double peakAmount = ECCalculateUtils.round(elePrice * Double.valueOf(peak), 2);
				String flat = jsonobject.getString("Flat");// 平时刻用电量
				// 计算平时刻用电金额
				Double flatAmount = ECCalculateUtils.round(elePrice * Double.valueOf(flat), 2);
				String valley = jsonobject.getString("Valley");// 谷时刻用电量
				// 计算谷时刻用电金额
				Double valleyAmount = ECCalculateUtils.round(elePrice * Double.valueOf(valley), 2);
				String VIN = jsonobject.getString("VIN");// 车VIN号

				gun.setWorkStatus(2);// 待机状态

				order.setOrderStartTime(sdf.parse(start_time));
				order.setOrderEndTime(sdf.parse(finish_time));
				order.setChargeDegree(ECCalculateUtils.round(ECCalculateUtils.sub(total_finish, total_start), 2));// 充电度数
				order.setChargeAmount(total_money);// 充电金额
				order.setPushType(0);

				// 计算费用
				if (balance != null) {
					Double balance_money = ECCalculateUtils.round(balance.getChargingBalance(), 2);
					if (balance_money.compareTo(ECCalculateUtils
							.round(ECCalculateUtils.add(total_money, order.getServiceAmount()), 2)) > 0) {
						order.setOrderAmount(0d);
						order.setNopayAmount(0d);// 应付金额
						order.setDiscountAmount(
								ECCalculateUtils.round(ECCalculateUtils.add(total_money, order.getServiceAmount()), 2));// 余额抵扣
						order.setOrderStatus(2);// 待评价
						order.setPayStatus(1);// 已支付
						balance.setChargingBalance(ECCalculateUtils.round(ECCalculateUtils.sub(balance_money,
								ECCalculateUtils.add(total_money, order.getServiceAmount())), 2));
					} else if (balance_money.compareTo(ECCalculateUtils
							.round(ECCalculateUtils.add(total_money, order.getServiceAmount()), 2)) == 0) {
						order.setOrderAmount(0d);// 订单金额
						order.setNopayAmount(0d);// 应付金额
						order.setDiscountAmount(
								ECCalculateUtils.round(ECCalculateUtils.add(total_money, order.getServiceAmount()), 2));// 余额抵扣
						order.setOrderStatus(2);// 待评价
						order.setPayStatus(1);// 已支付
						balance.setChargingBalance(0d);
					} else {
						order.setOrderAmount(
								ECCalculateUtils.round(ECCalculateUtils.add(total_money, order.getServiceAmount()), 2));// 订单总金额
						order.setDiscountAmount(balance_money);// 余额抵扣金额
						order.setNopayAmount(ECCalculateUtils.round(ECCalculateUtils
								.sub(ECCalculateUtils.add(total_money, order.getServiceAmount()), balance_money), 2));
						order.setOrderStatus(1);// 待支付
						order.setPayStatus(0);// 未支付
						balance.setChargingBalance(0d);
					}
					cAccountBalanceDao.update(balance);
				} else {
					order.setOrderAmount(
							ECCalculateUtils.round(ECCalculateUtils.add(total_money, order.getServiceAmount()), 2));// 订单总金额
					order.setNopayAmount(
							ECCalculateUtils.round(ECCalculateUtils.add(total_money, order.getServiceAmount()), 2));// 应付金额
					order.setDiscountAmount(0d);// 余额抵扣
					order.setOrderStatus(1);// 待支付
					order.setPayStatus(0);// 未支付
				}
				// 计算时间
				if (order.getOrderStartTime().compareTo(order.getOrderEndTime()) < 0) {
					long between = order.getOrderEndTime().getTime() - order.getOrderStartTime().getTime();
					long day = between / (24 * 60 * 60 * 1000);
					long hour = (between / (60 * 60 * 1000) - day * 24);
					long min = ((between / (60 * 1000)) - day * 24 * 60 - hour * 60);
					Double time = ECCalculateUtils
							.round(ECCalculateUtils.add(ECCalculateUtils.add(day * 24 * 60, hour * 60), min), 2);
					order.setOrderTimeLength(time.intValue());
				} else {
					order.setOrderTimeLength(0);
				}
				order.setPushType(0);
				chargeOrderDao.update(order);
				chargingGunInfoDao.update(gun);
				/*-------------结算地锁订单------------------------------*/
				try {
					LockOrder searchLockOrder=new LockOrder();
					searchLockOrder.setChargeOrderNo(order.getOrderNo());
					searchLockOrder.setOrderStatus(4);
					List<LockOrder> lockOrders=lockOrderDao.queryAll(new Query(searchLockOrder));
					LockOrder lockOrder=lockOrders.get(0);
					Date now=new Date();
					lockOrder.setFinishType(0);
					lockOrder.setOrderEndTime(now);
					lockOrder.setUpdateTime(now);
					lockOrderService.updateLockOrder_two(lockOrder,new Operator(),lockBillingSchemeDao.get(lockOrder.getLockBillingSchemeNo()));
				} catch (IndexOutOfBoundsException e){
				} catch (Exception e){
					log.info("地锁结算异常");
					log.info(e.getMessage());
				}
				/************ 以下更新充电记录表开始 *********************/

				ChargingRecord chargingRecord = new ChargingRecord();
				chargingRecord.setChargingGunNo(gun.getChargingGunNo());
				chargingRecord.setChargingPileNo(gun.getChargingPileNo());
				List<ChargingRecord> chargingRecordList = chargingRecordDao.queryAll(new Query(chargingRecord));
				if (chargingRecordList.size() > 0) {
					ChargingRecord cr = chargingRecordList.get(0);
					cr.setFinishTime(sdf.parse(finish_time));
					cr.setStartTime(sdf.parse(start_time));
					cr.setUpdateTime(new Date());
					cr.setSerialNumber(serial_number);
					cr.setVin(VIN);
					cr.setValleyFinish(valleyAmount);// 谷用电金额
					cr.setAlleyStart(valley);
					cr.setRecordTime(sdf.parse(record_time));// 记录时间
					cr.setTotalMoney(total_money);// 总金额
					cr.setPeakFinish(peakAmount);
					cr.setPeakStart(peak);
					cr.setOpintFinish(opintAmount);
					cr.setOpintStart(opint);
					cr.setFlatFinish(flatAmount);
					cr.setFlatStart(flat);
					cr.setMoneyBeforePay(moneyBefore);// 扣之前
					cr.setMoneyAfterPay(balance.getChargingBalance());// 抵扣之后
					chargingRecordDao.update(cr);
				} else {
					log.info("-------没有找到本次的充电记录，无法更新充电最新数据-----------");
					return;
				}
				/****************** 更新充电记录表结束 **************************/
			} catch (ParseException e) {
				log.info("日期格式有误");
				log.info(e.getMessage());
			}
		} else {
			log.info("---------订单的支付状态不是未支付，或者订单状态不是待支付----------");
			return;
		}
	}
}
