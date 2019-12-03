package cn.com.shopec.core.marketing.service.impl;

import java.text.ParseException;
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
import cn.com.shopec.core.marketing.dao.RedeemCodeDao;
import cn.com.shopec.core.marketing.dao.RedeemCouponPlanDao;
import cn.com.shopec.core.marketing.dao.RedeemRecordDao;
import cn.com.shopec.core.marketing.model.Coupon;
import cn.com.shopec.core.marketing.model.CouponPlan;
import cn.com.shopec.core.marketing.model.RedeemCode;
import cn.com.shopec.core.marketing.model.RedeemCouponPlan;
import cn.com.shopec.core.marketing.model.RedeemRecord;
import cn.com.shopec.core.marketing.service.CouponPlanService;
import cn.com.shopec.core.marketing.service.CouponService;
import cn.com.shopec.core.marketing.service.RedeemCodeService;
import cn.com.shopec.core.member.common.MemberConstant;
import cn.com.shopec.core.member.dao.MemberDao;
import cn.com.shopec.core.member.model.Member;
import cn.com.shopec.core.system.service.PrimarykeyService;
import cn.com.shopec.core.system.service.SysParamService;

/**
 * 优惠券表 服务实现类
 */
@Service
public class RedeemCodeServiceImpl implements RedeemCodeService {

	private static final Log log = LogFactory.getLog(RedeemCodeServiceImpl.class);

	@Resource
	private RedeemCodeDao redeemCodeDao;
	@Resource
	private SysParamService sysParamService;
	@Resource
	private CouponPlanDao couponPlanDao;
	@Resource
	private CouponPlanService couponPlanService;
	@Resource
	private CouponDao couponDao;
	
	@Resource
	private MemberDao memberDao;
	
	@Resource
	private PrimarykeyService primarykeyService;
	
	@Resource
	private RedeemRecordDao redeemRecordDao;

	@Resource
	private RedeemCouponPlanDao redeemCouponPlanDao;
	
	@Resource
	private  CouponService couponService;
	
	/**
	 * 根据查询条件，查询并返回RedeemCode的列表
	 * @param q 查询条件
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	@Override
	public List<RedeemCode> getRedeemCodeList(Query q) {
		List<RedeemCode> list = null;
		try {
			RedeemCode redeemCode = (RedeemCode) q.getQ();
			if (redeemCode != null) {
				redeemCode.setIsDeleted(Constant.DEL_STATE_NO);
			}
			// 直接调用Dao方法进行查询
			list = redeemCodeDao.queryAll(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		// 如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<RedeemCode>(0) : list;
		return list;
	}

	/**
	 * 根据查询条件，分页查询并返回RedeemCode的分页结果
	 * @param q 查询条件
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	@Override
	public PageFinder<RedeemCode> getRedeemCodePagedList(Query q) {
		PageFinder<RedeemCode> page = new PageFinder<RedeemCode>();
		List<RedeemCode> list = null;
		long rowCount = 0L;
		try {
			RedeemCode redeemCode = (RedeemCode) q.getQ();
			if (redeemCode != null) {
				redeemCode.setIsDeleted(Constant.DEL_STATE_NO);
			}

			// 调用dao查询满足条件的分页数据
			list = redeemCodeDao.pageList(q);
			// 调用dao统计满足条件的记录总数
			rowCount = redeemCodeDao.count(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		// 如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<RedeemCode>(0) : list;
		// 将分页数据和记录总数设置到分页结果对象中
		page.setData(list);
		page.setRowCount(rowCount);
		return page;
	}

	/**
	 * 根据主键，查询并返回一个RedeemCode对象
	 * @param redCode
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	@Override
	public RedeemCode getRedeemCode(String redCode) {
		RedeemCode obj = null;
		if (redCode == null || redCode.length() == 0) { // 传入的主键无效时直接返回null
			log.info(Constant.ERR_MSG_INVALID_ARG + " redCode = " + redCode);
			return obj;
		}
		try {
			// 调用dao，根据主键查询
			obj = redeemCodeDao.get(redCode);
			if(obj != null){
				
				List<RedeemCouponPlan> redeemCouponPlans = redeemCouponPlanDao.getAllByRedCode(redCode);
				obj.setRedeemCouponPlans(redeemCouponPlans);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return obj;
	}

	/**
	 * 根据主键数组，查询并返回一组RedeemCode对象
	 * 
	 * @param ids
	 *            主键数组，数组中的主键值应当无重复值，如有重复值时，则返回的列表长度可能小于传入的数组长度
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	@Override
	public List<RedeemCode> getRedeemCodeByIds(String[] redCodes) {
		List<RedeemCode> list = null;
		if (redCodes == null || redCodes.length == 0) {
			log.info(Constant.ERR_MSG_INVALID_ARG + " redCodes is null or empty array.");
		} else {
			try {
				// 调用dao，根据主键数组查询
				list = redeemCodeDao.getByIds(redCodes);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
		}
		// 如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<RedeemCode>(0) : list;
		return list;
	}

	/**
	 * 新增一条RedeemCode记录，执行成功后传入对象及返回对象的主键属性值不为null
	 * 
	 * @param RedeemCode
	 * @param operator
	 * @return
	 */
	@Transactional
	@Override
	public ResultInfo<RedeemCode> addRedeemCode(RedeemCode redeemCode, Operator operator) {
		ResultInfo<RedeemCode> resultInfo = new ResultInfo<RedeemCode>();

		if (redeemCode == null) { // 传入参数无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG);
			log.info(Constant.ERR_MSG_INVALID_ARG + " RedeemCode = " + redeemCode);
		} else {
			try {
				// 如果传入参数的主键为null，则调用生成主键的方法获取新的主键
				if (redeemCode.getRedCode() == null) {
					String redCode = this.generateRedCode();
					while(redeemCodeDao.get(redCode) != null){
						redCode = this.generateRedCode();
					}
					redeemCode.setRedCode(this.generateRedCode());
				}
				
				// 如果传入的操作人不为null，则设置新增记录的操作人类型和操作人id
				if (operator != null) {
					redeemCode.setOperatorType(operator.getOperatorType());
					redeemCode.setOperatorId(operator.getOperatorId());
				}

				// 填充默认值
				this.fillDefaultValues(redeemCode);

				// 调用Dao执行插入操作
				redeemCodeDao.add(redeemCode);
				
				//维护兑换券与计划的关系
				List<RedeemCouponPlan> redeemCouponPlans  = redeemCode.getRedeemCouponPlans();
				if(redeemCouponPlans != null){
					for (RedeemCouponPlan redeemCouponPlan : redeemCouponPlans) {
						redeemCouponPlan.setRedCode(redeemCode.getRedCode());
						redeemCouponPlanDao.add(redeemCouponPlan);
					}
				}
				
				resultInfo.setCode(Constant.SECCUESS);
				resultInfo.setData(redeemCode);
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
	 * 根据主键，更新一条RedeemCode
	 * 
	 * @param RedeemCode
	 * @param operator
	 * @return
	 */
	@Transactional
	@Override
	public ResultInfo<RedeemCode> updateRedeemCode(RedeemCode redeemCode, Operator operator) {
		ResultInfo<RedeemCode> resultInfo = new ResultInfo<RedeemCode>();

		if (redeemCode == null || redeemCode.getRedCode() == null) { // 传入参数无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG);
			log.info(Constant.ERR_MSG_INVALID_ARG + " couponPlan = " + redeemCode);
		} else {
			try {
				//设置更新时必须变更的值
				fillUpdateValues(redeemCode, operator);
				
				if (redeemCode.getCensorStatus() != null) {//修改审核时
					//设置审核完成的时间和审核人
					Date now = new Date();
					redeemCode.setCensorTime(now);
					if(operator != null){
						redeemCode.setCensorId(operator.getOperatorId());
					}
					//审核完成后默认为启用状态
					//redeemCode.setIsAvailable(1);
					//redeemCode.setAvailableUpdateTime(now);	
				}
				
				/*if (redeemCode.getIsAvailable() != null) {//修改启用，停用时
					//设置启用，停用更改时间
					redeemCode.setAvailableUpdateTime(new Date());
				}*/
				// 调用Dao执行更新操作，并判断更新语句执行结果
				if (redeemCodeDao.update(redeemCode) > 0) {
					
					//维护兑换券与计划的关系
					List<RedeemCouponPlan> redeemCouponPlans  = redeemCode.getRedeemCouponPlans();
					if(redeemCouponPlans != null){
						redeemCouponPlanDao.deleteByRedCode(redeemCode.getRedCode());
						for (RedeemCouponPlan redeemCouponPlan : redeemCouponPlans) {
							redeemCouponPlan.setRedCode(redeemCode.getRedCode());
							redeemCouponPlanDao.add(redeemCouponPlan);
						}
					}
					resultInfo.setCode(Constant.SECCUESS);
				} else {
					resultInfo.setCode(Constant.FAIL);
				}
				resultInfo.setData(redeemCode);
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
	 * 为RedeemCode对象设置更新时必要变更的值
	 * 
	 * @param obj
	 */
	@Override
	public void fillUpdateValues(RedeemCode redeemCode, Operator operator) {
		if (redeemCode != null) {
			// 如果传入的操作人不为null，则设置更新记录的操作人类型和操作人id
			if (operator != null) {
				redeemCode.setOperatorType(operator.getOperatorType());
				redeemCode.setOperatorId(operator.getOperatorId());
			}
			if (redeemCode.getAvailableTime2() != null) {
				String dateString = ECDateUtils.formatDate(redeemCode.getAvailableTime2(),ECDateUtils.Format_Date);
				try {
					redeemCode.setAvailableTime2( ECDateUtils.parseTime(dateString +" 23:59:59"));
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
			// 设置更新时间为当前时间
			redeemCode.setUpdateTime(new Date());
		}
	}

	/**
	 * 为RedeemCode对象设置默认值
	 * 
	 * @param obj
	 */
	public void fillDefaultValues(RedeemCode obj) {
		
		// 设置创建时间和更新时间为当前时间
		Date now = new Date();
		obj.setCreateTime(now);
		obj.setUpdateTime(now);
		
		if (obj != null) {
			if (obj.getIsAvailable() == null) {
				obj.setIsAvailable(0);
			}
			if (obj.getCensorStatus() == null) {
				obj.setCensorStatus(0);
			}
			if (obj.getIsDeleted() == null) {
				obj.setIsDeleted(Constant.DEL_STATE_NO);
			}
			if (obj.getCensorStatus() == null) {
				obj.setCensorStatus(0);
			}
		}
		
		if (obj.getAvailableTime2() != null) {
			String dateString = ECDateUtils.formatDate(obj.getAvailableTime2(),ECDateUtils.Format_Date);
			try {
				obj.setAvailableTime2( ECDateUtils.parseTime(dateString +" 23:59:59"));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 生成主键（随机8个大写字母与数字组合）
	 * 
	 * @return
	 */
	public String generateRedCode() { 
        Random random = new Random();
        String val = ECNumberUtils.to32Hex(new Date().getTime());//当前时间
        //参数length，表示生成几位随机数  
        for(int i = 0; i < 2; i++) {  
            boolean randomFlag = random.nextInt(2) % 2 == 0;
            String rn = "";
            if(randomFlag) {
            	char r = (char)(random.nextInt(26) + 65);//大写字母
            	if('O'==r){r='0';
            	}else if('Z'==r){r='2';
            	}else if('I'==r || 'L'==r){r='1';}
            	rn =String.valueOf(r);
            } else {  
                rn = String.valueOf(random.nextInt(10));  
            }  
            if(i==0){
            	val = rn+val;
            }else if(i==1){
            	val = val+rn;
            }
        }  
        return val;  
	}
	
	/**
	 * 生成主键
	 * 
	 * @return
	 */
	public String generatePK() {
		return String.valueOf(new Date().getTime() * 1000000 + new Random().nextInt(1000000));
	}

	/**
	 * 兌換优惠券
	 * 
	 * @param RedeemCode
	 * @param operator
	 * @return
	 */
	@Transactional
	@Override
	public ResultInfo<RedeemCode> redeemCoupon(String redCode, String memberNo, Operator operator) {
		ResultInfo<RedeemCode> resultInfo = new ResultInfo<RedeemCode>();

		if (redCode == null || memberNo == null || "".equals(redCode) || "".equals(memberNo)) { // 传入参数无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG);
			log.info(Constant.ERR_MSG_INVALID_ARG + " redCode = " + redCode + " memberNo = " + memberNo);
		} else {
			try {
				
				Member member = memberDao.get(memberNo);
				if (member != null) {
					List<RedeemRecord> rrs = redeemRecordDao.getForRedeem(redCode, memberNo);
					if (rrs != null && rrs.size() > 0) {
						resultInfo.setCode("2");
						resultInfo.setMsg("您已经兑换 请重新填写兑换码");
						return resultInfo;
					}
					RedeemCode rcd = redeemCodeDao.get(redCode);
					if (rcd != null) {
						if (new Date().getTime() > rcd.getAvailableTime2().getTime()) {
							resultInfo.setCode("3");
							resultInfo.setMsg("兑换码已经过期 请重新填写兑换码");
							return resultInfo;
						}

					}
					RedeemCode rc = new RedeemCode();
					rc.setRedCode(redCode);
					rc.setIsAvailable(1);
					rc.setAvailableTime2Start(new Date());
					Query qs = new Query(rc);
					List<RedeemCode> redeemCodes = redeemCodeDao.queryAll(qs);
					if (redeemCodes != null && redeemCodes.size() > 0) {
						List<RedeemRecord> redeemRecord = redeemRecordDao.getForRedeem(redCode, memberNo);
						if (redeemCodes.get(0).getAvailableTimes() != null) {
							if (redeemRecord != null
									&& redeemRecord.size() >= redeemCodes.get(0).getAvailableTimes().intValue()) {
								resultInfo.setCode(Constant.FAIL);// 超过兑换码设置的兑换次数
								resultInfo.setMsg(MarketingContant.COUPON_NO_REDEEM);
							}
						} else {
							// 求出该兑换码拥有哪些方案
							RedeemCouponPlan redeemCouponPlan = new RedeemCouponPlan();
							redeemCouponPlan.setRedCode(redCode);
							Query q = new Query(redeemCouponPlan);
							List<RedeemCouponPlan> redeemCouponPlans = redeemCouponPlanDao.queryAll(q);
							String planCode = sysParamService.getParamValueByParamKey("COUPON_CODE");
							if (redeemCouponPlans != null && redeemCouponPlans.size() > 0) {
								// 构建优惠券
								for (RedeemCouponPlan temp : redeemCouponPlans) {
									CouponPlan couponPlan = couponPlanDao.get(temp.getPlanNo());

									// 校验优惠券方案是否启用,如果方案未启用则忽略，继续下一个方案
									if (couponPlan.getIsAvailable().intValue() == 0) {
										continue;
									}

									if(couponPlan.getVailableTime2() != null ){
										if (new Date().getTime() > couponPlan.getVailableTime2().getTime()) {
											continue;
										}
									}
									
									// 开始校验，计算该方案实际能够兑换的优惠券数量
									int addCounter=0;
									 addCounter = temp.getRedQutity();
									int existingQuantity = couponPlan.getExistingQuantity() == null ? 0 : couponPlan.getExistingQuantity() + addCounter;
									// 判断方案是否有设置发放数量的最大值,如果有并且此次发放后数量超过限制数量，则开始计算实际能够方法数量
									if (couponPlan.getMaxQuantity() != null) {
										int maxQuantity = couponPlan.getMaxQuantity().intValue();
										if (existingQuantity > maxQuantity) {
											addCounter=couponPlan.getMaxQuantity() ==null ?0:couponPlan.getMaxQuantity() -existingQuantity;
											if (addCounter <= 0) {// 数量不够忽略，继续下一个方案
												resultInfo.setCode(Constant.FAIL);// 兑换码不存在
												resultInfo.setMsg("优惠券已经发放完 下次来早点");
												continue;
											}
										}
									}

									// 更新方案表信息
									CouponPlan couponPlanTemp = new CouponPlan();
									couponPlanTemp.setExistingQuantity(existingQuantity);
									couponPlanService.fillUpdateValues(couponPlanTemp, operator);
									couponPlanTemp.setPlanNo(temp.getPlanNo());
									Date  date= new Date();
									
									if(couponPlan.getAvailableDays() != null && couponPlan.getVailableTime2() == null ){
										couponPlanTemp.setVailableTime2(ECDateUtils.getDateAfter(date,couponPlan.getAvailableDays()));
										couponPlanTemp.setVailableTime1(date);
										couponPlanTemp.setAvailableDays(couponPlan.getAvailableDays());
									}else{
										couponPlanTemp.setVailableTime2(couponPlan.getVailableTime2());
										couponPlanTemp.setVailableTime1(couponPlan.getVailableTime1());
									}	
									
									couponPlanTemp.setUpdateTime(new Date());
									couponPlanDao.update(couponPlanTemp);

									// 新增优惠券表信息
									List<Coupon> coupons = new ArrayList<Coupon>();
									for (int i = 0; i < addCounter; i++) {
										Coupon couponTemp = new Coupon();
										couponTemp.setMemberNo(memberNo);
										couponTemp.setCouponNo(planCode + couponService.generatePK());
										couponTemp.setIssueTime(new Date());
										couponTemp.setIssueMethod(2);// 活动发放
										// 如果传入参数的主键为null，则调用生成主键的方法获取新的主键
										couponTemp.setPlanNo(couponPlan.getPlanNo());
										couponTemp.setTitle(couponPlan.getTitle());
										couponTemp.setCouponType(couponPlan.getCouponType());
										couponTemp.setCouponMethod(couponPlan.getCouponMethod());
										couponTemp.setDiscount(couponPlan.getDiscount());
										couponTemp.setDiscountAmount(couponPlan.getDiscountAmount());
										Date  date1= new Date();
										if(couponPlan.getAvailableDays() != null && couponPlan.getVailableTime2() == null ){
											couponTemp.setVailableTime2(ECDateUtils.getDateAfter(date1,couponPlan.getAvailableDays()));
											couponTemp.setVailableTime1(date1);
											couponTemp.setAvailableDays(couponPlan.getAvailableDays());
										}else{
											couponTemp.setVailableTime2(couponPlan.getVailableTime2());
											couponTemp.setVailableTime1(couponPlan.getVailableTime1());
										}	
										
										couponService.fillDefaultValues(couponTemp);// 填充其他默认值
										coupons.add(couponTemp);
									}
									// 调用Dao执行插入操作
									couponDao.addBatch(coupons);

									// 添加记录
									RedeemRecord record = new RedeemRecord();
									record.setId(this.generatePK());
									record.setRedCode(redCode);
									record.setMemberNo(memberNo);
									record.setCreateTime(new Date());
									redeemRecordDao.add(record);
									resultInfo.setCode(Constant.SECCUESS);
								}
							}

						}

						// resultInfo.setCode(Constant.FAIL);//兑换码不存在
					} else {
						resultInfo.setCode(Constant.FAIL);// 兑换码不存在
						resultInfo.setMsg(MarketingContant.REDEEMCODE_NO_EXISTENCE);
					}
				} else {
					resultInfo.setCode(Constant.FAIL);// 会员不存在
					resultInfo.setMsg(MemberConstant.MEMBER_NO_EXISTENCE);
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
}
