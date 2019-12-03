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
import cn.com.shopec.common.utils.ECCalculateUtils;
import cn.com.shopec.common.utils.ECDateUtils;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.marketing.model.Accounts;
import cn.com.shopec.core.marketing.model.PricingDeductionRecord;
import cn.com.shopec.core.marketing.model.PricingPackage;
import cn.com.shopec.core.marketing.service.AccountsService;
import cn.com.shopec.core.marketing.service.PricingDeductionRecordService;
import cn.com.shopec.core.marketing.service.PricingPackageService;
import cn.com.shopec.core.member.model.Member;
import cn.com.shopec.core.member.service.MemberService;
import cn.com.shopec.core.order.dao.PricingPackOrderDao;
import cn.com.shopec.core.order.model.PricingPackOrder;
import cn.com.shopec.core.order.service.PricingPackOrderService;
import cn.com.shopec.core.system.common.PrimarykeyConstant;
import cn.com.shopec.core.system.service.PrimarykeyService;

/**
 * 套餐订单表 服务实现类
 */
@Service
public class PricingPackOrderServiceImpl implements PricingPackOrderService {

	private static final Log log = LogFactory.getLog(PricingPackOrderServiceImpl.class);
	
	@Resource
	private PricingPackOrderDao pricingPackOrderDao;
	
	@Resource
	private PrimarykeyService primarykeyService;
	@Resource
	private PricingPackageService pricingPackageService;
	@Resource
	private MemberService memberService;
	@Resource
	private AccountsService accountsService;
	@Resource
	private PricingDeductionRecordService pricingDeductionRecordService;
	/**
	 * 根据查询条件，查询并返回PricingPackOrder的列表
	 * @param q 查询条件
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<PricingPackOrder> getPricingPackOrderList(Query q) {
		List<PricingPackOrder> list = null;
		try {
			//直接调用Dao方法进行查询
			list = pricingPackOrderDao.queryAll(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		//如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<PricingPackOrder>(0) : list;
		return list; 
	}
	
	/**
	 * 根据查询条件，分页查询并返回PricingPackOrder的分页结果
	 * @param q 查询条件
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public PageFinder<PricingPackOrder> getPricingPackOrderPagedList(Query q) {
		PageFinder<PricingPackOrder> page = new PageFinder<PricingPackOrder>();
		
		List<PricingPackOrder> list = null;
		long rowCount = 0L;
		
		try {
			//调用dao查询满足条件的分页数据
			list = pricingPackOrderDao.pageLists(q);
			for (PricingPackOrder pricingPackOrder : list) {
				PricingPackage pricingPackage = pricingPackageService.getPricingPackage(pricingPackOrder.getPackageId()); 
				if(pricingPackage.getPackageType()==1){
					pricingPackOrder.setPackageType(1);
				}
				if(pricingPackOrder.getUserdMinute()==null){
					pricingPackOrder.setUserdMinute(0);
				}
				if(pricingPackOrder.getPackMinute()==null){
					pricingPackOrder.setPackMinute(0);
				}
				pricingPackOrder.setResidueMinute(pricingPackOrder.getPackMinute()-pricingPackOrder.getUserdMinute());
				if(pricingPackOrder.getPackOrderAmount()==null){
					pricingPackOrder.setPackOrderAmount(0.0);
				}
				if(pricingPackOrder.getUseredOrderAmount()==null){
					pricingPackOrder.setUseredOrderAmount(0.0);
				}
				pricingPackOrder.setRemainOrderAmount(ECCalculateUtils.sub(pricingPackOrder.getPackOrderAmount(), pricingPackOrder.getUseredOrderAmount()));
			}
			//调用dao统计满足条件的记录总数
			rowCount = pricingPackOrderDao.counts(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		
		//如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<PricingPackOrder>(0) : list;
	
		//将分页数据和记录总数设置到分页结果对象中
		page.setData(list);
		page.setRowCount(rowCount);
		
		return page;
	}	
	
	
	
	/**
	 * 根据查询条件，分页查询并返回PricingPackOrder的分页结果
	 * @param q 查询条件
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public PageFinder<PricingPackOrder> getPricingPackOrderPagedLists(Query q) {
		PageFinder<PricingPackOrder> page = new PageFinder<PricingPackOrder>();
		
		List<PricingPackOrder> list = null;
		long rowCount = 0L;
		
		try {
			//调用dao查询满足条件的分页数据
			list = pricingPackOrderDao.pageList(q);
			//调用dao统计满足条件的记录总数
			rowCount = pricingPackOrderDao.count(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		
		//如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<PricingPackOrder>(0) : list;
	
		//将分页数据和记录总数设置到分页结果对象中
		page.setData(list);
		page.setRowCount(rowCount);
		
		return page;
	}	
	/**
	 * 根据主键，查询并返回一个PricingPackOrder对象
	 * @param id 主键id
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public PricingPackOrder getPricingPackOrder(String id) {
		PricingPackOrder obj = null;
		if (id == null || id.length() == 0) { //传入的主键无效时直接返回null
			log.info(Constant.ERR_MSG_INVALID_ARG + " id = " + id);
			return obj;
		}
		try {
			//调用dao，根据主键查询
			obj = pricingPackOrderDao.get(id); 
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		
		return obj;
	}

	/**
	 * 根据主键数组，查询并返回一组PricingPackOrder对象
	 * @param ids 主键数组，数组中的主键值应当无重复值，如有重复值时，则返回的列表长度可能小于传入的数组长度
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<PricingPackOrder> getPricingPackOrderByIds(String[] ids) {
		List<PricingPackOrder> list = null;
		if (ids == null || ids.length == 0) {
			log.info(Constant.ERR_MSG_INVALID_ARG + " ids is null or empty array.");
		} else {
			try {
				//调用dao，根据主键数组查询
				list = pricingPackOrderDao.getByIds(ids);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
		}
		
		//如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<PricingPackOrder>(0) : list;

		return list;
	}	
	
	/**
	 * 根据主键，删除特定的PricingPackOrder记录
	 * @param id 主键id
	 * @param operator 操作人对象
	 * @return
	 */
	@Transactional
	public ResultInfo<PricingPackOrder> delPricingPackOrder(String id, Operator operator) {
		ResultInfo<PricingPackOrder> resultInfo = new ResultInfo<PricingPackOrder>();
		if (id == null || id.length() == 0) { //传入的主键无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG );
			log.info(Constant.ERR_MSG_INVALID_ARG + " id = " + id);
			return resultInfo;
		}
		try {
		    //调用Dao执行删除，并判断删除语句执行结果
			int count = pricingPackOrderDao.delete(id);
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
	 * 新增一条PricingPackOrder记录，执行成功后传入对象及返回对象的主键属性值不为null
	 * @param pricingPackOrder 新增的PricingPackOrder数据（如果无特殊需求，新增对象的主键值请保留为null）
	 * @param operator 操作人对象
	 * @return
	 */
	@Transactional
	public ResultInfo<PricingPackOrder> addPricingPackOrder(PricingPackOrder pricingPackOrder, Operator operator) {
		ResultInfo<PricingPackOrder> resultInfo = new ResultInfo<PricingPackOrder>();
		
		if (pricingPackOrder == null) { //传入参数无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG );
			log.info(Constant.ERR_MSG_INVALID_ARG + " pricingPackOrder = " + pricingPackOrder);
		} else {
			try {
				//如果传入参数的主键为null，则调用生成主键的方法获取新的主键
				if (pricingPackOrder.getPackOrderNo() == null) {
					pricingPackOrder.setPackOrderNo(this.generatePK());
				}
				//如果传入的操作人不为null，则设置新增记录的操作人类型和操作人id
				if (operator != null) {
					pricingPackOrder.setOperatorType(operator.getOperatorType());
					pricingPackOrder.setOperatorId(operator.getOperatorId());
				}
				
				//设置创建时间和更新时间为当前时间
				Date now = new Date();
				pricingPackOrder.setCreateTime(now);
				pricingPackOrder.setUpdateTime(now);
				
				//填充默认值
				this.fillDefaultValues(pricingPackOrder);
				
				//调用Dao执行插入操作
				pricingPackOrderDao.add(pricingPackOrder);
				resultInfo.setCode(Constant.SECCUESS);
				resultInfo.setData(pricingPackOrder);
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
	 * 根据主键，更新一条PricingPackOrder记录
	 * @param pricingPackOrder 更新的PricingPackOrder数据，且其主键不能为空
	 * @param operator 操作人对象
	 * @return
	 */
	@Transactional
	public ResultInfo<PricingPackOrder> updatePricingPackOrder(PricingPackOrder pricingPackOrder, Operator operator) {
		ResultInfo<PricingPackOrder> resultInfo = new ResultInfo<PricingPackOrder>();
		
		if (pricingPackOrder == null || pricingPackOrder.getPackOrderNo() == null) { //传入参数无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG );
			log.info(Constant.ERR_MSG_INVALID_ARG + " pricingPackOrder = " + pricingPackOrder);
		} else {
			try {
				//如果传入的操作人不为null，则设置更新记录的操作人类型和操作人id
				if (operator != null) {
					pricingPackOrder.setOperatorType(operator.getOperatorType());
					pricingPackOrder.setOperatorId(operator.getOperatorId());
				}
				
				//设置更新时间为当前时间
				pricingPackOrder.setUpdateTime(new Date());
				
				//调用Dao执行更新操作，并判断更新语句执行结果
				int count = pricingPackOrderDao.update(pricingPackOrder);			
				if (count > 0) {
					resultInfo.setCode(Constant.SECCUESS);
				} else {
					resultInfo.setCode(Constant.FAIL);
				}
				resultInfo.setData(pricingPackOrder);
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
	 * @return
	 */
	public String generatePK() {
		//return "P"+ECDateUtils.formatDate(new Date(),"yyyyMMdd")+primarykeyService.getValueByBizType(PrimarykeyConstant.packageOrderType);
		return "T"+primarykeyService.getValueByBizType(PrimarykeyConstant.packageOrderType);
	}
	
	/**
	 * 为PricingPackOrder对象设置默认值
	 * @param obj
	 */
	public void fillDefaultValues(PricingPackOrder obj) {
		if (obj != null) {
		    if (obj.getUserdMinute() == null) {
		    	obj.setUserdMinute(0);
		    }
		    if (obj.getPayStatus() == null) {
		    	obj.setPayStatus(0);
		    }
		    if (obj.getIsAvailable() == null) {
		    	obj.setIsAvailable(1);
		    }
		    if (obj.getInvoiceStatus() == null) {
		    	obj.setInvoiceStatus(0);
		    }
		}
	}

	@Override
	public List<PricingPackOrder> getPricingPackOrderListByUse(Query q) {
		List<PricingPackOrder> list = null;
		try {
			//直接调用Dao方法进行查询
			list = pricingPackOrderDao.getPricingPackOrderListByUse(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		//如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<PricingPackOrder>(0) : list;
		return list; 
	}

	@Override
	public List<PricingPackOrder> getPricingPackOrderListByDiKou(Query q) {
		List<PricingPackOrder> list = null;
		try {
			//直接调用Dao方法进行查询
			list = pricingPackOrderDao.getPricingPackOrderListByDiKou(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		//如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<PricingPackOrder>(0) : list;
		return list; 
	}
	@Override
	public List<PricingPackOrder> getPricingPackOrderAmountListByDiKou(Query q) {
		List<PricingPackOrder> list = null;
		try {
			//直接调用Dao方法进行查询
			list = pricingPackOrderDao.getPricingPackOrderAmountListByDiKou(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		//如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<PricingPackOrder>(0) : list;
		return list;
	}
	@Override
	public List<PricingPackOrder> eportFormPackageList(Query q) {
		List<PricingPackOrder> list = null;
		try {
			//直接调用Dao方法进行查询
			list = pricingPackOrderDao.eportFormPackageList(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		//如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<PricingPackOrder>(0) : list;
		return list; 
	}

	@Override
	public List<PricingPackOrder> eportFormPackageListAll() {
		List<PricingPackOrder> list = null;
		try {
			//直接调用Dao方法进行查询
			list = pricingPackOrderDao.eportFormPackageListAll();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		//如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<PricingPackOrder>(0) : list;
		return list; 
	}

	@Override
	public List<PricingPackOrder> eportFormPackageListMonth(Query q) {
		List<PricingPackOrder> list = null;
		try {
			//直接调用Dao方法进行查询
			list = pricingPackOrderDao.eportFormPackageListMonth(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		//如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<PricingPackOrder>(0) : list;
		return list; 
	}

	@Override
	public List<PricingPackOrder> eportFormPackageListDay(Query q) {
		List<PricingPackOrder> list = null;
		try {
			//直接调用Dao方法进行查询
			list = pricingPackOrderDao.eportFormPackageListDay(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		//如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<PricingPackOrder>(0) : list;
		return list; 
	}

	@Override
	public Double getPackageSum(String packageId) {
		return pricingPackOrderDao.getPackageSum(packageId);
	}

	@Override
	public Long getPackageCount(String packageId) {
		return pricingPackOrderDao.getPackageCount(packageId);
	}

	@Override
	public List<PricingPackOrder> getPricingPackOrderListBypo(Query q) {
		 return pricingPackOrderDao.getPricingPackOrderListBypo(q);
	
	}

	@Override
	public Integer countRecharge() {
		return pricingPackOrderDao.countRecharge();
	}

	

	@Override
	@Transactional
	public void recharge(String memberNo, Double amount, String memo,Operator operator) throws Exception {
		//预先得到已有的套餐
		Query q = new Query();
		PricingPackOrder ppo = new PricingPackOrder();
		ppo.setPayStatus(1);// 已经支付的
		ppo.setIsAvailable(1);// 可用
		ppo.setNowTime(ECDateUtils.getCurrentDateTime());// 使用当前时间
		ppo.setMemberNo(memberNo);
		

		PricingPackage rechargePackage = new PricingPackage();
		rechargePackage.setPackageType(3);//手工充值
		List<PricingPackage> pricingPackageList = pricingPackageService.getPricingPackageList(new Query());
		if(pricingPackageList != null && !pricingPackageList.isEmpty()){
			rechargePackage = pricingPackageList.get(0);
		}
		
		q.setQ(ppo);
		List<PricingPackOrder> list = this.getPricingPackOrderAmountListByDiKou(q);
		
		Member member = memberService.getMember(memberNo);
		PricingPackOrder pricingPackOrder = new PricingPackOrder();
		pricingPackOrder.setMemberNo(member.getMemberNo());
		pricingPackOrder.setMemberName(member.getMemberName());
		pricingPackOrder.setMobilePhone(member.getMobilePhone());
		pricingPackOrder.setPackAmount(0d);//售价0
		pricingPackOrder.setPackOrderAmount(amount);
		if(rechargePackage != null){
			pricingPackOrder.setPackageId(rechargePackage.getPackageNo());
			pricingPackOrder.setPackageName(rechargePackage.getPackageName());
		}
		pricingPackOrder.setPaymentMethod(4); //支付方式为其他。
		pricingPackOrder.setUserdMinute(0);//已用时长
		pricingPackOrder.setPayStatus(1);//支付状态
		pricingPackOrder.setIsAvailable(1);//可用状态
		pricingPackOrder.setUseredOrderAmount(0d);//已用金额
		pricingPackOrder.setPayableAmount(0d);//应付金额
		pricingPackOrder.setInvoiceStatus(0);//发票状态
		
		//有效期的起止时间
		pricingPackOrder.setVailableTime1(new Date());
		pricingPackOrder.setVailableTime2(ECDateUtils.parseDate("2099-12-31"));
		
		ResultInfo<PricingPackOrder> res = this.addPricingPackOrder(pricingPackOrder,operator);
		if(res.getCode().equals(Constant.SECCUESS)){
			Double poa=0.0;
			Double uoa=0.0;
			for (PricingPackOrder packOrder : list) {
				if(packOrder.getPackOrderAmount() != null ){
					poa=ECCalculateUtils.add(poa,packOrder.getPackOrderAmount());
				}
				if(packOrder.getUseredOrderAmount() != null){
					uoa=ECCalculateUtils.add(uoa,packOrder.getUseredOrderAmount());
				}
			}
			//金额套餐赠送成功后给记账表添加记录
			Double beforeMoney = ECCalculateUtils.sub(poa, uoa);
			Double overMoney = ECCalculateUtils.add(beforeMoney,pricingPackOrder.getPackOrderAmount());
			
			Accounts accounts = new Accounts();
			accounts.setMemberNo(pricingPackOrder.getMemberNo());
			accounts.setBusinessNo(pricingPackOrder.getPackOrderNo());
			accounts.setBusinessType(3);//手工充值
			accounts.setAccountType(2);
			accounts.setAccountMoney(pricingPackOrder.getPackOrderAmount());
			accounts.setAccountBeforeMoney(beforeMoney);
			accounts.setAccountOverMoney(overMoney);
			accounts.setAccountTime(new Date());
			accounts.setMemo(memo);
			accountsService.addAccounts(accounts, operator);
		}
	}
	
	@Override
	@Transactional
	public void dikou(String memberNo, Double amount, String memo, Operator operator) throws Exception {
		//预先得到已有的套餐
		Query q = new Query();
		PricingPackOrder ppo = new PricingPackOrder();
		ppo.setPayStatus(1);// 已经支付的
		ppo.setIsAvailable(1);// 可用
		ppo.setNowTime(ECDateUtils.getCurrentDateTime());// 使用当前时间
		ppo.setMemberNo(memberNo);
		q.setQ(ppo);
		List<PricingPackOrder> list = this.getPricingPackOrderAmountListByDiKou(q);
		
		PricingPackage rechargePackage = new PricingPackage();
		rechargePackage.setPackageType(3);//手工充值
		List<PricingPackage> pricingPackageList = pricingPackageService.getPricingPackageList(new Query());
		if(pricingPackageList != null && !pricingPackageList.isEmpty()){
			rechargePackage = pricingPackageList.get(0);
		}
		
		if(list != null){
			Double dikouTotalAmount = 0.00d;//实际抵扣金额
			Double daiDiKou = ECCalculateUtils.mul(-1d, amount);//需要抵扣的金额
			for (PricingPackOrder pricingPackOrder : list) {
				if (pricingPackOrder.getUseredOrderAmount() == null) {
					pricingPackOrder.setUseredOrderAmount(0d);// 将已使用套餐金额的null变为0
				}
				// 得出单个套餐剩余可用套餐余额
				Double useredOrderAmount = ECCalculateUtils.sub(pricingPackOrder.getPackOrderAmount(), pricingPackOrder.getUseredOrderAmount());
				if (useredOrderAmount >= daiDiKou) {
					useredOrderAmount = daiDiKou;
				}
				if (useredOrderAmount >= 0) {
					pricingPackOrder.setUseredOrderAmount(ECCalculateUtils.add(pricingPackOrder.getUseredOrderAmount(), useredOrderAmount));
					this.updatePricingPackOrder(pricingPackOrder, operator);
					
					PricingDeductionRecord pricingDeductionRecord = new PricingDeductionRecord();
					pricingDeductionRecord.setMemberNo(memberNo);
					pricingDeductionRecord.setPackOrderNo(pricingPackOrder.getPackOrderNo());
					if (rechargePackage != null) {
						pricingDeductionRecord.setPackageId(rechargePackage.getPackageNo());
					}
					
					pricingDeductionRecord.setDeductionAmount(useredOrderAmount);
					pricingDeductionRecord.setDeductionTime(new Date());
					pricingDeductionRecordService.addPricingDeductionRecord(pricingDeductionRecord, operator);
					
					
					dikouTotalAmount = ECCalculateUtils.add(dikouTotalAmount, useredOrderAmount);//维护实际抵扣金额
					
					//总抵扣额减去此次抵扣额如果能够抵扣完则结束循环,否则进行下一次扣减
					daiDiKou = ECCalculateUtils.sub(daiDiKou, useredOrderAmount);
					if (daiDiKou.compareTo(0d) == 0) {
						break;
					}
				} 
			}
			dikouTotalAmount = ECCalculateUtils.round(dikouTotalAmount, 2);
			
			if (dikouTotalAmount > 0) {
				
				//求出用户原先的剩余的金额
				Double poa = 0.0;
				Double uoa = 0.0;
				for (PricingPackOrder packOrder : list) {
					if(packOrder.getPackOrderAmount() != null ){
						poa=ECCalculateUtils.add(poa,packOrder.getPackOrderAmount());
					}
					if(packOrder.getUseredOrderAmount() != null){
						uoa=ECCalculateUtils.add(uoa,packOrder.getUseredOrderAmount());
					}
				}
				
				Double overMoney = ECCalculateUtils.sub(poa, uoa);
				Double beforeMoney = ECCalculateUtils.add(overMoney,dikouTotalAmount);
				
				
				Accounts accounts = new Accounts();
				accounts.setMemberNo(memberNo);
				//accounts.setBusinessNo(order.getOrderNo());
				accounts.setBusinessType(4);//手工扣费修改
				accounts.setAccountType(1);//出账
				accounts.setAccountMoney(dikouTotalAmount);
				accounts.setAccountBeforeMoney(beforeMoney);
				accounts.setAccountOverMoney(overMoney);
				accounts.setAccountTime(new Date());
				accounts.setMemo(memo);
				accountsService.addAccounts(accounts, operator);
			}
		}
		
	}
}
