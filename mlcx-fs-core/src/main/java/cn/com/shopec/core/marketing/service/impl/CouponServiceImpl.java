package cn.com.shopec.core.marketing.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
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
import cn.com.shopec.common.utils.ECDateUtils;
import cn.com.shopec.common.utils.ECNumberUtils;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.marketing.common.MarketingContant;
import cn.com.shopec.core.marketing.dao.CouponDao;
import cn.com.shopec.core.marketing.dao.CouponPlanDao;
import cn.com.shopec.core.marketing.model.Coupon;
import cn.com.shopec.core.marketing.model.CouponPlan;
import cn.com.shopec.core.marketing.service.CouponPlanService;
import cn.com.shopec.core.marketing.service.CouponService;
import cn.com.shopec.core.member.dao.MemberDao;
import cn.com.shopec.core.system.service.SysParamService;

/**
 * 优惠券表 服务实现类
 */
@Service
public class CouponServiceImpl implements CouponService {

	private static final Log log = LogFactory.getLog(CouponServiceImpl.class);
	
	@Resource
	private MemberDao memberDao;
	@Resource
	private CouponDao couponDao;
	@Resource
	private CouponPlanDao couponPlanDao;
	@Resource
	private SysParamService sysParamService;
	@Resource
	private CouponPlanService couponPlanService;
	
	/**
	 * 根据查询条件，查询并返回Coupon的列表
	 * 
	 * @param q
	 *            查询条件
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<Coupon> getCouponList(Query q) {
		List<Coupon> list = null;
		try {
			// 直接调用Dao方法进行查询
			list = couponDao.queryAll(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		// 如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<Coupon>(0) : list;
		return list;
	}

	/**
	 * 根据查询条件，分页查询并返回Coupon的分页结果
	 * 
	 * @param q
	 *            查询条件
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public PageFinder<Coupon> getCouponPagedList(Query q) {
		PageFinder<Coupon> page = new PageFinder<Coupon>();
		List<Coupon> list = null;
		long rowCount = 0L;
		try {
			// 调用dao查询满足条件的分页数据
			list = couponDao.pageList(q);
			// 调用dao统计满足条件的记录总数
			rowCount = couponDao.count(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		// 如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<Coupon>(0) : list;
		// 将分页数据和记录总数设置到分页结果对象中
		page.setData(list);
		page.setRowCount(rowCount);
		return page;
	}
	
	@Override
	public PageFinder<Coupon> getMemberCouponPageList(Query q) {
		PageFinder<Coupon> page = new PageFinder<Coupon>();
		List<Coupon> list = null;
		long rowCount = 0L;
		try {
			// 调用dao查询满足条件的分页数据
			list = couponDao.getMemberCouponPageList(q);
			// 调用dao统计满足条件的记录总数
			rowCount = couponDao.getMemberCouponCount(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		// 如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<Coupon>(0) : list;
		// 将分页数据和记录总数设置到分页结果对象中
		page.setData(list);
		page.setRowCount(rowCount);
		return page;
	}

	/**
	 * 根据主键，查询并返回一个Coupon对象
	 * 
	 * @param id
	 *            主键id
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public Coupon getCoupon(String id) {
		Coupon coupon = null;
		if (id == null || id.length() == 0) { // 传入的主键无效时直接返回null
			log.info(Constant.ERR_MSG_INVALID_ARG + " id = " + id);
			return coupon;
		}
		try {
			// 调用dao，根据主键查询
			coupon = couponDao.get(id);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return coupon;
	}

	/**
	 * 根据主键数组，查询并返回一组Coupon对象
	 * 
	 * @param ids
	 *            主键数组，数组中的主键值应当无重复值，如有重复值时，则返回的列表长度可能小于传入的数组长度
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<Coupon> getCouponByIds(String[] ids) {
		List<Coupon> list = null;
		if (ids == null || ids.length == 0) {
			log.info(Constant.ERR_MSG_INVALID_ARG + " ids is null or empty array.");
		} else {
			try {
				// 调用dao，根据主键数组查询
				list = couponDao.getByIds(ids);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
		}
		// 如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<Coupon>(0) : list;
		return list;
	}

	/**
	 * 新增一条Coupon记录，该新增将会无视方案限制数量
	 * @param memberArray 
	 * @param Coupon 新增的Coupon数据
	 * @param operator 操作人对象
	 * @return
	 */
	@Transactional
	public ResultInfo<Coupon> addCoupon(Coupon coupon, Operator operator) {
		ResultInfo<Coupon> resultInfo = new ResultInfo<Coupon>();
		if (coupon == null || coupon.getPlanNo() == null || coupon.getPlanNo() == "") { // 传入参数无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG);
			log.info(Constant.ERR_MSG_INVALID_ARG + " coupon = " + coupon);
		} else {
			CouponPlan couponPlan = couponPlanDao.get(coupon.getPlanNo());
			if(couponPlan  != null){
				try {
					
					
					// 如果传入参数的主键为null，则调用生成主键的方法获取新的主键
					if (coupon.getCouponNo() == null) {
						coupon.setCouponNo(this.generatePK());
					}
					couponPlan.setTitle(couponPlan.getTitle());
					couponPlan.setCouponType(couponPlan.getCouponType());
					couponPlan.setCouponMethod(couponPlan.getCouponMethod());
					couponPlan.setDiscount(couponPlan.getDiscount());
					couponPlan.setDiscountAmount(couponPlan.getDiscountAmount());
					couponPlan.setVailableTime1(couponPlan.getVailableTime1());
					couponPlan.setVailableTime2(couponPlan.getVailableTime2());
					couponPlan.setAvailableDays(couponPlan.getAvailableDays());
				
					//设置手动发放优惠券时的发放人id
					if(operator != null && coupon.getIssueMethod()!= null && coupon.getIssueMethod().intValue() == 3){
						coupon.setIssuerId(operator.getOperatorId());
					}
					
					// 设置发放时间为当前时间
					Date now = new Date();
					coupon.setIssueTime(now);
	
					// 填充其他默认值
					this.fillDefaultValues(coupon);
					
					// 调用Dao执行插入操作
					couponDao.add(coupon);
					resultInfo.setCode(Constant.SECCUESS);
					resultInfo.setData(coupon);
				} catch (Exception e) {
					log.error(e.getMessage(), e);
					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
					resultInfo.setCode(Constant.FAIL);
					resultInfo.setMsg(Constant.ERR_MSG_EXCEPTION_CATCHED);
				}
			}else{
				resultInfo.setCode(Constant.FAIL);
				resultInfo.setMsg(MarketingContant.COUPONPLAN_NO_EXISTENCE);
				log.info(Constant.ERR_MSG_INVALID_ARG + " coupon = " + coupon);
			}
		}
		return resultInfo;
	}
	/**
	 * 根据主键，更新一条Coupon记录
	 * 
	 * @param order
	 *            更新的Coupon数据，且其主键不能为空
	 * @param operator
	 *            操作人对象
	 * @return
	 */
	@Transactional
	public ResultInfo<Coupon> updateCoupon(Coupon coupon, Operator operator) {
		ResultInfo<Coupon> resultInfo = new ResultInfo<Coupon>();

		if (coupon == null || coupon.getCouponNo() == null) { // 传入参数无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG);
			log.info(Constant.ERR_MSG_INVALID_ARG + " coupon = " + coupon);
		} else {
			try {
				// 如果传入的操作人不为null，则设置更新记录的操作人类型和操作人id
				/*if (operator != null) {
					coupon.setOperatorType(operator.getOperatorType());
					coupon.setOperatorId(operator.getOperatorId());
				}*/


				// 调用Dao执行更新操作，并判断更新语句执行结果
				int count = couponDao.update(coupon);
				if (count > 0) {
					resultInfo.setCode(Constant.SECCUESS);
				} else {
					resultInfo.setCode(Constant.FAIL);
				}
				resultInfo.setData(coupon);
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
	 * 根据主键，启动或停用一条Coupon
	 * 
	 * @param coupon
	 *            更新的Coupon数据，且其主键不能为空
	 * @param operator
	 *            操作人对象
	 * @return
	 */
	@Transactional
	public ResultInfo<Coupon> updateCouponAvailable(Coupon coupon, Operator operator) {
		ResultInfo<Coupon> resultInfo = new ResultInfo<Coupon>();
		if (coupon == null || coupon.getCouponNo() == null) { // 传入参数无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG);
			log.info(Constant.ERR_MSG_INVALID_ARG + " coupon = " + coupon);
		} else {
			try {
				Coupon couponTemp = getCoupon(coupon.getCouponNo());
				if (couponTemp != null) {
					if (couponTemp.getIsAvailable().intValue() == coupon.getIsAvailable().intValue()) {
						resultInfo.setCode(Constant.FAIL);// 无需重复启用，停用Coupon
						if (couponTemp.getIsAvailable().intValue() == 1) {
							resultInfo.setMsg(MarketingContant.AVAILABLE_ENABLE);
						} else {
							resultInfo.setMsg(MarketingContant.AVAILABLE_DISABLE);
						}
					} else if (couponTemp.getUsedStatus().intValue() != 1) {
						resultInfo.setCode(Constant.FAIL);// 启用，停用只针对于未使用Coupon
						resultInfo.setMsg(MarketingContant.COUPON_USED);
					} else {
						Date now = new Date();
						coupon.setAvailableUpdateTime(now);
						
						// 调用Dao执行更新操作，并判断更新语句执行结果
						int count = couponDao.update(coupon);
						if (count > 0) {
							resultInfo.setCode(Constant.SECCUESS);
						} else {
							resultInfo.setCode(Constant.FAIL);
						}
					}
				} else {
					resultInfo.setCode(Constant.FAIL);
					resultInfo.setMsg(MarketingContant.COUPON_NO_EXISTENCE);// Coupon不存在
				}
				resultInfo.setData(coupon);
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
	 * 为Coupon对象设置默认值
	 * 
	 * @param obj
	 */
	public void fillDefaultValues(Coupon obj) {
		if (obj != null) {
			if (obj.getIsAvailable() == null) {
				obj.setIsAvailable(1);
				obj.setAvailableUpdateTime(new Date());
			}
			if (obj.getUsedStatus() == null) {
				obj.setUsedStatus(0);
			}
		}
	}

	/**
	 * 生成主键
	 * 
	 * @return
	 */
	@Override
	public String generatePK() {
		Date date = new Date();
		String dateHex = ECNumberUtils.to32Hex(date.getTime());
        long num = Math.abs(new Random().nextLong() % 10000L);
        String random = String.valueOf(num);
        random =  dateHex + random;
		return random;
	}

	/**
	 * 批量增加
	 */
	@Override
	@Transactional
	public ResultInfo<Coupon> addCoupon(Coupon coupon, String[] memberArray, Operator operator) {
		ResultInfo<Coupon> resultInfo = new ResultInfo<Coupon>();

		if (coupon == null || coupon.getPlanNo() == null || "".equals(coupon.getPlanNo()) || coupon.getSendQuantity() == null || coupon.getSendQuantity() == 0 || memberArray == null || memberArray.length == 0) { // 传入参数无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG);
			log.info(Constant.ERR_MSG_INVALID_ARG + " coupon = " + coupon);
		} else {
			CouponPlan couponPlan = couponPlanDao.get(coupon.getPlanNo());
			if(couponPlan  != null){
				try {
					//校验优惠券方案是否启用
					if(couponPlan.getIsAvailable().intValue() == 0){
						resultInfo.setCode(Constant.FAIL);
						resultInfo.setMsg(MarketingContant.COUPONPLAN_NO_DISABLE);
						return resultInfo;
					}
					
					//求出这次发放后优惠券的总数量
					int existingQuantity = couponPlan.getExistingQuantity() == null ? 0 : couponPlan.getExistingQuantity();
					existingQuantity = existingQuantity + memberArray.length*coupon.getSendQuantity();
					
					//判断方案是否有设置发放数量的最大值,如果有并且此次发放后数量超过限制数量，则直接返回无法发放的信息
					if(couponPlan.getMaxQuantity() != null){
						if(existingQuantity > couponPlan.getMaxQuantity().intValue()){
							resultInfo.setCode(Constant.FAIL);
							resultInfo.setMsg(MarketingContant.COUPON_QUANTITY_EXCEEDED);
							return resultInfo;
						}
					}
					
					//更新方案表信息
					CouponPlan couponPlanTemp = new CouponPlan();
					couponPlanTemp.setPlanNo(couponPlan.getPlanNo());
					couponPlanTemp.setExistingQuantity(existingQuantity);
					couponPlanService.fillUpdateValues(couponPlan, operator);
					couponPlanTemp.setOperatorType(couponPlan.getOperatorType());
					couponPlanTemp.setOperatorId(couponPlan.getOperatorId());
					if (couponPlan.getVailableTime2() != null) {
						couponPlanTemp.setVailableTime2(couponPlan.getVailableTime2());
						
					}
					if (couponPlan.getVailableTime1() != null) {
						couponPlanTemp.setVailableTime1(couponPlan.getVailableTime1());
						
					}
					// 设置更新时间为当前时间
					couponPlanTemp.setUpdateTime(new Date());
					if(couponPlanDao.update(couponPlanTemp) <= 0){
						resultInfo.setCode(Constant.FAIL);
						resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG);
						return resultInfo;
					}

					//开始为选择的用户发放优惠券
					String planCode = sysParamService.getParamValueByParamKey("COUPON_CODE");
					List<Coupon> coupons = new ArrayList<Coupon>();	
					for (int i = 0; i < memberArray.length; i++) {
						for(int j = 0; j < coupon.getSendQuantity(); j++){ // 发放数量
							Coupon couponTemp = new Coupon();
							couponTemp.setMemberNo(memberArray[i]);
							
							// 如果传入参数的主键为null，则调用生成主键的方法获取新的主键
							couponTemp.setCouponNo(planCode + this.generatePK());
							couponTemp.setPlanNo(couponPlan.getPlanNo());
							couponTemp.setTitle(couponPlan.getTitle());
							couponTemp.setCouponType(couponPlan.getCouponType());
							couponTemp.setCouponMethod(couponPlan.getCouponMethod());
							couponTemp.setDiscount(couponPlan.getDiscount());
							couponTemp.setDiscountAmount(couponPlan.getDiscountAmount());
							if(couponPlan.getAvailableDays()  != null ){
								Calendar calendar= new GregorianCalendar();
								Date date= new Date();
								calendar.setTime(date);
								couponTemp.setVailableTime1(date);
								couponTemp.setVailableTime2(ECDateUtils.getDateAfter(date, couponPlan.getAvailableDays()));
							}else{
								couponTemp.setVailableTime1(couponPlan.getVailableTime1());
								couponTemp.setVailableTime2(couponPlan.getVailableTime2());
							}
							couponTemp.setAvailableDays(couponPlan.getAvailableDays());
							
							// 设置发放时间为当前时间
							Date now = new Date();
							couponTemp.setIssueTime(now);
			
							// 填充其他默认值
							this.fillDefaultValues(couponTemp);
							//设置手动发放优惠券时的发放人id
	
							couponTemp.setIssueMethod(coupon.getIssueMethod());
							couponTemp.setIssueChannel(coupon.getIssueChannel());
							//如果是系统发放則需要补充发放人
							if(operator != null && coupon.getIssueMethod()!= null && coupon.getIssueMethod().intValue() == 3){
								couponTemp.setIssuerId(operator.getOperatorId());
							}
							coupons.add(couponTemp);
						}
					}
					// 调用Dao执行插入操作
					couponDao.addBatch(coupons);
					resultInfo.setData(coupon);
					resultInfo.setCode(Constant.SECCUESS);
				} catch (Exception e) {
					log.error(e.getMessage(), e);
					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
					resultInfo.setCode(Constant.FAIL);
					resultInfo.setMsg(Constant.ERR_MSG_EXCEPTION_CATCHED);
				}
			}else{
				resultInfo.setCode(Constant.FAIL);
				resultInfo.setMsg(MarketingContant.COUPONPLAN_NO_EXISTENCE);
				log.info(Constant.ERR_MSG_INVALID_ARG + " coupon = " + coupon);
			}
		}
		return resultInfo;
	}

	/**
	 * 根据查询条件，查询并返回Coupon的列表
	 * 
	 * @param q
	 *            查询条件
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<Coupon> getCouponListOrder(Query q) {
		List<Coupon> list = null;
		try {
			// 直接调用Dao方法进行查询
			list = couponDao.queryAllOrder(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		// 如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<Coupon>(0) : list;
		return list;
	}



}
