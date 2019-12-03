package cn.com.shopec.core.mlparking.service.impl;

import java.text.SimpleDateFormat;
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
import org.springframework.util.StringUtils;

import cn.com.shopec.common.Operator;
import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.common.constants.Constant;
import cn.com.shopec.common.utils.ECCalculateUtils;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.member.dao.MemberDao;
import cn.com.shopec.core.member.model.Member;
import cn.com.shopec.core.mlparking.dao.CParkBillingDao;
import cn.com.shopec.core.mlparking.dao.CParkLockDao;
import cn.com.shopec.core.mlparking.dao.CParkOrderDao;
import cn.com.shopec.core.mlparking.dao.CParkingDao;
import cn.com.shopec.core.mlparking.model.CParkBilling;
import cn.com.shopec.core.mlparking.model.CParkLock;
import cn.com.shopec.core.mlparking.model.CParkOrder;
import cn.com.shopec.core.mlparking.service.CParkLockService;
import cn.com.shopec.core.mlparking.vo.Lock;
import cn.com.shopec.core.mlparking.vo.LockOrder;
import cn.com.shopec.core.mlparking.vo.ParkingLockVo;
import cn.com.shopec.core.mlparking.vo.ParkingReservation;
import cn.com.shopec.core.mlparking.vo.ParkingVo;
import cn.com.shopec.core.system.service.PrimarykeyService;

/**
 * 地锁表 服务实现类
 */
@Service
public class CParkLockServiceImpl implements CParkLockService {

	private static final Log log = LogFactory
			.getLog(CParkLockServiceImpl.class);
	@Resource
	private CParkLockDao cParkLockDao;

	@Resource
	private PrimarykeyService primarykeyService;

	@Resource
	private MemberDao memberDao;
	@Resource
	private CParkOrderDao parkOrderDao;
	@Resource
	private CParkingDao parkingDao;
	@Resource
	private CParkBillingDao parkBillingDao;

	/**
	 * 根据查询条件，查询并返回CParkLock的列表
	 * 
	 * @param q
	 *            查询条件
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<CParkLock> getCParkLockList(Query q) {
		List<CParkLock> list = null;
		try {
			// 直接调用Dao方法进行查询
			list = cParkLockDao.queryAll(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		// 如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<CParkLock>(0) : list;
		return list;
	}

	/**
	 * 根据查询条件，分页查询并返回CParkLock的分页结果
	 * 
	 * @param q
	 *            查询条件
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public PageFinder<CParkLock> getCParkLockPagedList(Query q) {
		PageFinder<CParkLock> page = new PageFinder<CParkLock>();

		List<CParkLock> list = null;
		long rowCount = 0L;

		try {
			// 调用dao查询满足条件的分页数据
			list = cParkLockDao.pageListForCPL(q);
			// 调用dao统计满足条件的记录总数
			rowCount = cParkLockDao.pageListCountCPL(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		// 如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<CParkLock>(0) : list;

		// 将分页数据和记录总数设置到分页结果对象中
		page.setData(list);
		page.setRowCount(rowCount);

		return page;
	}

	/**
	 * 根据主键，查询并返回一个CParkLock对象
	 * 
	 * @param id
	 *            主键id
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public CParkLock getCParkLock(String id) {
		CParkLock obj = null;
		if (id == null || id.length() == 0) { // 传入的主键无效时直接返回null
			log.info(Constant.ERR_MSG_INVALID_ARG + " id = " + id);
			return obj;
		}
		try {
			// 调用dao，根据主键查询
			obj = cParkLockDao.get(id);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		return obj;
	}

	/**
	 * 根据主键数组，查询并返回一组CParkLock对象
	 * 
	 * @param ids
	 *            主键数组，数组中的主键值应当无重复值，如有重复值时，则返回的列表长度可能小于传入的数组长度
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<CParkLock> getCParkLockByIds(String[] ids) {
		List<CParkLock> list = null;
		if (ids == null || ids.length == 0) {
			log.info(Constant.ERR_MSG_INVALID_ARG
					+ " ids is null or empty array.");
		} else {
			try {
				// 调用dao，根据主键数组查询
				list = cParkLockDao.getByIds(ids);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
		}

		// 如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<CParkLock>(0) : list;

		return list;
	}

	/**
	 * 根据主键，删除特定的CParkLock记录
	 * 
	 * @param id
	 *            主键id
	 * @param operator
	 *            操作人对象
	 * @return
	 */
	@Transactional
	public ResultInfo<CParkLock> delCParkLock(String id, Operator operator) {
		ResultInfo<CParkLock> resultInfo = new ResultInfo<CParkLock>();
		if (id == null || id.length() == 0) { // 传入的主键无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG);
			log.info(Constant.ERR_MSG_INVALID_ARG + " id = " + id);
			return resultInfo;
		}
		try {
			// 调用Dao执行删除，并判断删除语句执行结果
			int count = cParkLockDao.delete(id);
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
	 * 新增一条CParkLock记录，执行成功后传入对象及返回对象的主键属性值不为null
	 * 
	 * @param cParkLock
	 *            新增的CParkLock数据（如果无特殊需求，新增对象的主键值请保留为null）
	 * @param operator
	 *            操作人对象
	 * @return
	 */
	@Transactional
	public ResultInfo<CParkLock> addCParkLock(CParkLock cParkLock,
			Operator operator) {
		ResultInfo<CParkLock> resultInfo = new ResultInfo<CParkLock>();

		if (cParkLock == null) { // 传入参数无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG);
			log.info(Constant.ERR_MSG_INVALID_ARG);
		} else {
			try {
				// 如果传入参数的主键为null，则调用生成主键的方法获取新的主键
				if (cParkLock.getParkLockNo() == null) {
					cParkLock.setParkLockNo(this.generatePK());
				}
				// 如果传入的操作人不为null，则设置新增记录的操作人类型和操作人id
				if (operator != null) {
					cParkLock.setOperatorType(operator.getOperatorType());
					cParkLock.setOperatorId(operator.getOperatorId());
				}

				// 设置创建时间和更新时间为当前时间
				Date now = new Date();
				cParkLock.setCreateTime(now);
				cParkLock.setUpdateTime(now);

				// 填充默认值
				this.fillDefaultValues(cParkLock);

				// 调用Dao执行插入操作
				cParkLockDao.add(cParkLock);
				resultInfo.setCode(Constant.SECCUESS);
				resultInfo.setData(cParkLock);
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
	 * 根据主键，更新一条CParkLock记录
	 * 
	 * @param cParkLock
	 *            更新的CParkLock数据，且其主键不能为空
	 * @param operator
	 *            操作人对象
	 * @return
	 */
	@Transactional
	public ResultInfo<CParkLock> updateCParkLock(CParkLock cParkLock,
			Operator operator) {
		ResultInfo<CParkLock> resultInfo = new ResultInfo<CParkLock>();

		if (cParkLock == null || cParkLock.getParkLockNo() == null) { // 传入参数无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG);
			log.info(Constant.ERR_MSG_INVALID_ARG + " cParkLock = " + cParkLock);
		} else {
			try {
				// 如果传入的操作人不为null，则设置更新记录的操作人类型和操作人id
				if (operator != null) {
					cParkLock.setOperatorType(operator.getOperatorType());
					cParkLock.setOperatorId(operator.getOperatorId());
				}

				// 设置更新时间为当前时间
				cParkLock.setUpdateTime(new Date());

				// 调用Dao执行更新操作，并判断更新语句执行结果
				int count = cParkLockDao.update(cParkLock);
				if (count > 0) {
					resultInfo.setCode(Constant.SECCUESS);
				} else {
					resultInfo.setCode(Constant.FAIL);
				}
				resultInfo.setData(cParkLock);
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
		return "PL"
				+ Long.valueOf(
						new Date().getTime() + new Random().nextInt(10000))
						.toString();
	}

	/**
	 * 为CParkLock对象设置默认值
	 * 
	 * @param obj
	 */
	public void fillDefaultValues(CParkLock obj) {
		if (obj != null) {

		}
	}

	/**
	 * 
	 * <p>
	 * Title: updateCParkLockBegin
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 修改地锁 如果重复不允许修改
	 * 
	 * @param cParkLock
	 * @param operator
	 * @return
	 * @see cn.com.shopec.core.mlparking.service.CParkLockService#updateCParkLockBegin(cn.com.shopec.core.mlparking.model.CParkLock,
	 *      cn.com.shopec.common.Operator)
	 * @author: guofei
	 */
	@Override
	public ResultInfo<CParkLock> updateCParkLockBegin(CParkLock cParkLock,
			Operator operator) {
		ResultInfo<CParkLock> resultInfo = new ResultInfo<CParkLock>();
		if (cParkLock == null || cParkLock.getParkLockNo() == null) { // 传入参数无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG);
			log.info(Constant.ERR_MSG_INVALID_ARG + " cParkLock = " + cParkLock);
		} else {
			String parkingNo = cParkLock.getParkingNo();
			String[] split = parkingNo.split(",");
			cParkLock.setParkingNo(split[0]);
			log.info(operator.getOperatorUserName() + "开始进行地锁的修改,地锁编号为:"
					+ cParkLock.getParkLockNo());
			boolean flat = this.checkParkLockRepeat(cParkLock);
			if (flat) {
				// 该停车场不存在相同的地锁可以添加
				resultInfo = this.updateCParkLock(cParkLock, operator);
			} else {
				// 该停车场存在相同的地锁不允许添加
				String errMessage = "该停车场存在相同的地锁,请核对信息";
				resultInfo.setCode(Constant.FAIL);
				resultInfo.setMsg(errMessage);
				log.info(errMessage + "地锁编号为:" + cParkLock.getParkLockNo());
			}
		}
		return resultInfo;
	}

	/**
	 * 
	 * <p>
	 * Title: addCParkLockBegin
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 添加地锁存在重复不允许添加
	 * 
	 * @param cParkLock
	 * @param operator
	 * @return
	 * @see cn.com.shopec.core.mlparking.service.CParkLockService#addCParkLockBegin(cn.com.shopec.core.mlparking.model.CParkLock,
	 *      cn.com.shopec.common.Operator)
	 * @author: guofei
	 */
	@Override
	public ResultInfo<CParkLock> addCParkLockBegin(CParkLock cParkLock,
			Operator operator) {
		ResultInfo<CParkLock> resultInfo = new ResultInfo<CParkLock>();
		if (cParkLock == null) { // 传入参数无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG);
			log.info(Constant.ERR_MSG_INVALID_ARG);
		} else {
			String parkingNo = cParkLock.getParkingNo();
			String[] split = parkingNo.split(",");
			cParkLock.setParkingNo(split[0]);
			log.info(operator.getOperatorUserName() + "开始进行地锁的添加");
			boolean flat = this.checkParkLockRepeat(cParkLock);
			if (flat) {
				// 该停车场不存在相同的地锁可以添加
				resultInfo = this.addCParkLock(cParkLock, operator);
			} else {
				// 该停车场存在相同的地锁不允许添加
				String errMessage = "该停车场存在相同的地锁,请核对信息";
				resultInfo.setCode(Constant.FAIL);
				resultInfo.setMsg(errMessage);
				log.info(errMessage);
			}
		}
		return resultInfo;
	}

	/**
	 * 
	 * @Title: checkParkLockRepeat
	 * @Description:判断停车场是否存在对应的地锁
	 * @param: @param cParkLock
	 * @param: @return
	 * @return: boolean
	 * @author: guofei
	 * @date: 2018年11月7日 下午4:22:25
	 * @throws
	 */
	@SuppressWarnings({ "unused", "null" })
	private boolean checkParkLockRepeat(CParkLock cParkLock) {
		boolean flat = true;
		if (cParkLock != null && cParkLock.getParkLockNo() != null) {
			// 判断新修改的地锁是否和数据库中其他地锁重复(同一停车场同一楼层,同一车位号)
			CParkLock cParkLock2 = new CParkLock();
			cParkLock2.setParkingNo(cParkLock.getParkingNo());
			cParkLock2.setSpaceNo(cParkLock.getSpaceNo());
			cParkLock2.setPliesNumber(cParkLock.getPliesNumber());
			List<CParkLock> cParkLockList = this.getCParkLockList(new Query(
					cParkLock2));
			if (null != cParkLockList && cParkLockList.size() != 0) {
				for (CParkLock cParkLock3 : cParkLockList) {
					if (cParkLock3 != null
							&& cParkLock3.getParkLockNo() != null
							&& !cParkLock3.getParkLockNo().equals(
									cParkLock.getParkLockNo())) {
						flat = false;
						return flat;
					}
				}
			} else {
				return flat;
			}

		}
		return flat;
	}

	@Override
	public ParkingLockVo getParkLock(String lockNo) {
		return cParkLockDao.getParkLock(lockNo);
	}

	public ResultInfo<Object> resultInfoMemberNo(String memberNo, int type) {
		ResultInfo<Object> info = new ResultInfo<Object>();
		if (StringUtils.isEmpty(memberNo)) {// 验证会员
			info.setMsg("参数错误!");
			info.setCode(Constant.FAIL);// 返回0无数据
			return info;
		}
		// 验证会员信息
		Member member = memberDao.get(memberNo);
		if (member != null) {
			if (type == 1) {// 1.需验证会员订单信息
				// 获取订单
				List<CParkOrder> orderList = parkOrderDao.getOrder(memberNo);
				if (orderList != null && orderList.size() > 0) {
					for (CParkOrder order : orderList) {
						LockOrder lo=new LockOrder();
						if(order.getEntryTime()==null&&order.getOrderStatus()==0&&
								order.getParkType()==1){
							lo.setOrderStatus(10);//返回 10有预约中的订单
						}else{
							lo.setOrderStatus(order.getOrderStatus());
						}
						lo.setOrderNo(order.getParkOrderNo());
						lo.setMemberNo(memberNo);
						SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-DD HH:mm:ss");
						if(order.getAppointmentTime()!=null){
							lo.setOrderTime(sd.format(order.getAppointmentTime()));
						}else{
							lo.setOrderTime(sd.format(order.getEntryTime()));
						}
						lo.setStationName(order.getParkingName());
						lo.setParkingNo(order.getParkingNo());
						lo.setSpaceNo(cParkLockDao.getParkLock(order.getParkLockNo()).getSpaceNo());
						if (order.getOrderStatus() == 0) {
							info.setData(lo);
							info.setMsg("您当前有未结束订单,请结束后再进行使用!");
							info.setCode(Constant.NOFINSH);// 返回 5有未结束订单
							return info;
						} else if (order.getOrderStatus() == 1) {
							info.setData(lo);
							info.setMsg("您当前有未支付订单,请支付后再进行使用!");
							info.setCode(Constant.NOPAY);// 返回 4有未支付订单
							return info;
						}
					}
				} 
			}
		}else {
			info.setMsg("您不是本公司会员!");
			info.setCode(Constant.NO_AUTHORITY);// 返回-2 未授权
			return info;
		}
		info.setCode(Constant.SECCUESS);
		return info;
	}

	public ResultInfo<Object> resultInfoLock(ParkingLockVo cParkLock,boolean flag) {
		ResultInfo<Object> info = new ResultInfo<Object>();
		if (cParkLock != null) {// (0.占用 1.空闲 2.预约)
			if (cParkLock.getParkingLockStatus() == 1) {
				info.setMsg("当前地锁被占用,请重新选择");
				info.setCode(Constant.NO_OCCUPY);// 返回 99 地锁不可用
				return info;
			} else if (cParkLock.getActiveCondition() == 1) {
				info.setMsg("当前地锁不可用,请重新选择");
				info.setCode(Constant.NO_OCCUPY);// 返回 99 地锁不可用
				return info;
			} else if (cParkLock.getLockStatus() == 0) {
				info.setMsg("当前地锁被占用,请重新选择");
				info.setCode(Constant.NO_OCCUPY);// 返回 99 地锁不可用
				return info;
			} else if (cParkLock.getLockStatus() == 2) {
				if(flag){
					info.setCode(Constant.SECCUESS);
					return info;
				}else{
					info.setMsg("当前地锁被预约,请重新选择");
					info.setCode(Constant.NO_OCCUPY);// 返回 99 地锁不可用
					return info;
				}
			}
		} else {
			info.setMsg("暂无当前车位信息");
			info.setCode(Constant.OTHER);// 返回 3 空数据
			return info;
		}
		info.setCode(Constant.SECCUESS);
		return info;
	}

	@Override
	public ResultInfo<Object> parkingReservation(Integer pageNo,Integer pageSize,String parkNo) {
		ResultInfo<Object> info = new ResultInfo<Object>();
		//获取场站信息
		ParkingVo cParking = parkingDao.getParkListNo(parkNo);
		if(cParking!=null){
			//计费规则
			CParkBilling cParkBilling = parkBillingDao.get(cParking.getBillingSchemeNo());
			CParkLock p=new CParkLock();
			p.setParkingNo(cParking.getParkingNo());
			p.setParkingLockStatus(0);
			p.setActiveCondition(0);
			p.setOnlineStatus(0);
			p.setLockStatus(1);
			List<CParkLock> cParkLockList = this.getCParkLockList(new Query(pageNo,pageSize,p));
			ParkingReservation pr=new ParkingReservation();
			pr.setParkingNo(parkNo);
			pr.setParkingName(cParking.getParkingName());
			pr.setFreeTime(cParkBilling.getFreeTime());
			pr.setBillingNo(cParkBilling.getParkBillingNo());
			pr.setLon(cParking.getLongitude());
			pr.setLat(cParking.getLatitude());
			List<Lock> lockList=new ArrayList<Lock>();
			if(cParkLockList!=null&&cParkLockList.size()>0){
				for(CParkLock pl:cParkLockList){
					Lock lock=new Lock();
					lock.setLockNo(pl.getParkLockNo());
					lock.setLockName(pl.getParkingLockName());
					lock.setSpaceNo(pl.getSpaceNo());
					lock.setLockStatus(pl.getLockStatus());
					lock.setParkingLockStatus(pl.getParkingLockStatus());
					lock.setFreeTime(cParkBilling.getFreeTime());
					lockList.add(lock);
				}
			}
			pr.setTotalParkingSpaces(cParking.getParkingSpaceNumber()+cParking.getUndergroundParkingSpaceNumber()+cParking.getGroundParkingSpaceNumber());
			pr.setRemainingSpace(cParking.getSurplusSpaceNumber()+cParking.getUndergroundSurplusSpaceNumber()+cParking.getGroundSurplusSpaceNumber());
			pr.setLockInfoList(lockList);
			pr.setTimeNum("收费时间段:"+cParking.getBusinessHours());
			int time=60%cParkBilling.getUnitTime()==0?60/cParkBilling.getUnitTime():60/cParkBilling.getUnitTime()+1;
			pr.setOvertimePrice("收费标准:"+ECCalculateUtils.mul(time, cParkBilling.getOvertimePrice())+"/小时");
			pr.setCappingPrice("备注:连续24小时最高收费"+cParkBilling.getCappingPrice()+"元");
			info.setData(pr);
			info.setCode(Constant.SECCUESS);
			info.setMsg("查询成功");
			return info;
		}else{
			info.setCode(Constant.OTHER);
			info.setMsg("暂无停车场信息");
			return info;
		}
	}

	@Override
	public ParkingLockVo getLock(String parkNo, String spaceNo) {
		// TODO Auto-generated method stub
		return cParkLockDao.getLock(parkNo, spaceNo);
	}
}
