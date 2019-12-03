package cn.com.shopec.core.order.service.impl;

import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.common.constants.Constant;
import cn.com.shopec.common.dsUtils.ConstantCd;
import cn.com.shopec.common.dsUtils.WgPost;
import cn.com.shopec.common.utils.MapUtils;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.member.dao.MemberDao;
import cn.com.shopec.core.member.model.Member;
import cn.com.shopec.core.ml.dao.COrderDao;
import cn.com.shopec.core.ml.dao.ChargingStationDao;
import cn.com.shopec.core.ml.dao.ParkingLockDao;
import cn.com.shopec.core.ml.model.CAccountBalance;
import cn.com.shopec.core.ml.model.ChargingStation;
import cn.com.shopec.core.ml.model.LockBillingScheme;
import cn.com.shopec.core.ml.model.ParkingLock;
import cn.com.shopec.core.ml.service.CAccountBalanceService;
import cn.com.shopec.core.ml.service.LockBillingSchemeService;
import cn.com.shopec.core.ml.vo.OrderSimpleVo;
import cn.com.shopec.core.mlorder.dao.LockOrderDao;
import cn.com.shopec.core.mlorder.model.LockOrder;
import cn.com.shopec.core.order.service.PlaceAnOrderService;
import cn.com.shopec.core.system.common.PrimarykeyConstant;
import cn.com.shopec.core.system.service.PrimarykeyService;
import cn.com.shopec.core.system.service.SysParamService;
import net.sf.json.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 充电下单 服务实现类
 */
@Service public class PlaceAnOrderServiceImpl implements PlaceAnOrderService {
	@Resource private ParkingLockDao parkingLockDao;
	@Resource private MemberDao memberDao;
	@Resource private COrderDao cOrderDao;
	@Resource private LockOrderDao lockOrderDao;
	@Resource private ChargingStationDao chargingStationDao;
	@Resource private PrimarykeyService primarykeyService;
	@Resource private SysParamService sysParamService;
	@Resource private LockBillingSchemeService lockBillingSchemeServiceImpl;
	@Resource private CAccountBalanceService caccountBalanceService;
	private static final Log log = LogFactory.getLog(PlaceAnOrderServiceImpl.class);

	@Override @Transactional public ResultInfo<OrderSimpleVo> createAnOrder(String lockid, String memberNo,
			String deviceTp, String longitude, String latitude) {
		ResultInfo<OrderSimpleVo> resultInfo = new ResultInfo<>();
		ParkingLock lock = parkingLockDao.get(lockid);
		Member member = memberDao.get(memberNo);
		String url = null;
		String lockCode = null;
		if (null != member) {
			CAccountBalance balance = new CAccountBalance();
			balance.setMemberNo(memberNo);
			List<CAccountBalance> lst = caccountBalanceService.getCAccountBalanceList(new Query(balance));
			if (lst.size() > 0) {
				CAccountBalance cb = lst.get(0);// 获取会员余额
				Double stopBalanceAmount = cb.getStopBalance();// 停车余额
				String stopAmount = sysParamService.getParamValueByParamKey("STOP_AMOUNT");
				if (Double.valueOf(stopAmount).compareTo(stopBalanceAmount) > 0) {
					resultInfo.setCode(Constant.NOT_SUFFICIENT_FUNDS);
					resultInfo.setMsg("最低余额不能低于" + stopAmount + "元");
					return resultInfo;
				}
			}
			else {
				resultInfo.setCode(Constant.NOT_SUFFICIENT_FUNDS);
				resultInfo.setMsg("此会员没有余额，请先充值，再降锁");
				return resultInfo;
			}
		}
		/* 验证数据 */
		if (lock == null) {// 判断设备
			System.out.println("------未获取到地锁信息---------");
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg("未找到设备，请联系客服。");
			return resultInfo;
		}
		else if (member == null) {// 判断用户信息
			System.out.println("--------未获取到会员信息-----------");
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg("用户信息有误");
			return resultInfo;
		}
		//		else if (new Integer(1).equals(lock.getParkingLockStatus())) {
		//			resultInfo.setCode(Constant.FAIL);
		//			resultInfo.setMsg("该锁已经降下，不需再次降锁");
		//			return resultInfo;
		//		}
		else {// 验证订单
			OrderSimpleVo search = new OrderSimpleVo();
			search.setMemberNo(memberNo);
			List<OrderSimpleVo> orders = cOrderDao.searchOrderListNoCategoryNotFinished(new Query(search));
			try {
				OrderSimpleVo vo = orders.get(0);
				resultInfo.setCode("1".equals(vo.getOrderStatus()) ? Constant.NOPAY :("0".equals(vo.getOrderStatus())?Constant.NOFINSH:Constant.NOSETTLEMENT));
				resultInfo.setMsg("用户当前有未结束订单");
				resultInfo.setData(vo);
				return resultInfo;
			} catch (Exception e) {
				url = new Integer(0).equals(lock.getParkingLockType()) ?
						ConstantCd.moveLockDown :
						new Integer(1).equals(lock.getParkingLockType()) ? ConstantCd.MoveLockDown_NoCheck : "";
				lockCode = lock.getParkingLockCode();
			}
		}
		try {// 验证位置
			ChargingStation station = chargingStationDao.get(lock.getStationNo());
			Double param = Double.valueOf(sysParamService.getParamValueByParamKey("DROP_LOCK_DISTANCE"));
			Double distance = MapUtils.getDistance(Double.valueOf(latitude), Double.valueOf(longitude),
												   Double.valueOf(station.getLatitude()),
												   Double.valueOf(station.getLongitude()));
			if (distance.compareTo(param) > 0) {
				resultInfo.setCode(Constant.FAIL);
				resultInfo.setMsg("请在地所附近操作地锁");
				return resultInfo;
			}
		} catch (NumberFormatException e) {
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg("参数有误");
			return resultInfo;
		}
		if (url == null || url.length() < 1 || lockCode == null) {
			log.info("---------地锁的url为null或者地锁的编码为空-------------");
			System.out.println("---------地锁的url为null或者地锁的编码为空-------------");
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg("地锁信息有误");
			return resultInfo;
		}

		/*--end-- 验证数据 */
		try {
			// 调用第三方地锁接口，执行降锁操作
			log.info("-------开始降锁--------");
			JSONObject rt = WgPost.post(lockCode, url);
			log.info("降锁后返回的数据是：" + rt);
			System.out.println("降锁后输出的数据是：" + rt.getString("Success"));
			// FIXME 临时注释发送指令
			if (rt != null && rt.optBoolean("Success", false)) {
				// 降锁成功，创建地锁订单
				log.info("------------降锁成功，地锁订单创建 BEGIN-----------");
				Date now = new Date();
				LockOrder order = new LockOrder();
				order.setOrderNo(generatePK());
				order.setStationNo(lock.getStationNo());
				order.setStationName(lock.getStationName());
				order.setParkingLockNo(lock.getParkingLockNo());
				order.setParkingLockName(lock.getParkingLockName());
				order.setOrderStatus(0);
				order.setCreateTime(now);
				order.setMemberNo(member.getMemberNo());
				order.setMemberName(member.getMemberName());
				order.setMobilePhone(member.getMobilePhone());
				order.setOrderStartTime(now);
				order.setPayStatus(0);
				order.setOrderSource(deviceTp);
				order.setLockType(0);// 地锁订单默认为充电方式
				// 获取计费方案
				LockBillingScheme lockBillingScheme = lockBillingSchemeServiceImpl
						.getLockBillingScheme(lock.getParkingLockChargingNo());
				order.setOrderFreeTime(lockBillingScheme.getFreeTime());// 免费时长
				order.setLockBillingSchemeNo(lockBillingScheme.getLockSchemeNo());// 计费方案编号
				lockOrderDao.add(order);
				log.info("-------地锁订单创建结束END------------");
				//
				lock.setParkingLockStatus(1);// 降下
				parkingLockDao.update(lock);
				//
				OrderSimpleVo vo = new OrderSimpleVo(order);
				vo.setOrderStatus("0");
				resultInfo.setCode(Constant.SECCUESS);
				resultInfo.setMsg("降锁成功");
				resultInfo.setData(vo);
			}
			else {
				// // 第三方降锁指令失败
				log.info("-------------降锁失败-------------");
				resultInfo.setCode(Constant.FAIL);
				resultInfo.setMsg("设备连接异常，请联系客服。");
			}
		} catch (Exception e) {
			log.error(e);
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg("设备连接异常，请联系客服。");
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
		return "L" + primarykeyService.getValueByBizType(PrimarykeyConstant.orderType);
	}
}
