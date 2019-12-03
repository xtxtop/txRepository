package cn.com.shopec.core.finace.service.impl;

import java.math.BigDecimal;
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
import cn.com.shopec.common.utils.ECNumberUtils;
import cn.com.shopec.core.car.model.CarIllegal;
import cn.com.shopec.core.car.service.CarIllegalService;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.finace.common.DepositConstant;
import cn.com.shopec.core.finace.dao.DepositOrderDao;
import cn.com.shopec.core.finace.dao.DepositRefundDao;
import cn.com.shopec.core.finace.dao.PaymentRecordDao;
import cn.com.shopec.core.finace.model.Deposit;
import cn.com.shopec.core.finace.model.DepositOrder;
import cn.com.shopec.core.finace.model.DepositOrderForMgt;
import cn.com.shopec.core.finace.model.DepositRefund;
import cn.com.shopec.core.finace.model.MemberDepositRefund;
import cn.com.shopec.core.finace.model.PaymentRecord;
import cn.com.shopec.core.finace.service.DepositOrderService;
import cn.com.shopec.core.finace.service.DepositRefundService;
import cn.com.shopec.core.marketing.dao.AreaDepositDao;
import cn.com.shopec.core.marketing.dao.DepositZhimaReductionDao;
import cn.com.shopec.core.marketing.model.AreaDeposit;
import cn.com.shopec.core.marketing.model.DepositZhimaReduction;
import cn.com.shopec.core.member.dao.MemberDao;
import cn.com.shopec.core.member.dao.MemberZhimaScoreDao;
import cn.com.shopec.core.member.model.Member;
import cn.com.shopec.core.member.model.MemberZhimaScore;
import cn.com.shopec.core.order.dao.OrderDao;
import cn.com.shopec.core.order.model.Order;
import cn.com.shopec.core.system.common.PrimarykeyConstant;
import cn.com.shopec.core.system.model.SysParam;
import cn.com.shopec.core.system.service.PrimarykeyService;
import cn.com.shopec.core.system.service.SysParamService;

/**
 * 押金支付订单表 服务实现类
 */
@Service
public class DepositOrderServiceImpl implements DepositOrderService {

	private static final Log log = LogFactory.getLog(DepositOrderServiceImpl.class);
	
	@Resource
	private DepositOrderDao depositOrderDao;
	
	@Resource
	private PaymentRecordDao paymentRecordDao;
	
	@Resource
	private MemberDao memberDao;
	
	@Resource
	private OrderDao orderDao;
	
	@Resource
	private DepositRefundDao depositRefundDao;
	
	@Resource
	private DepositRefundService depositRefundService;
	
	@Resource
	private SysParamService sysParamService;
	
	@Resource
	private PrimarykeyService primarykeyService;
	
	@Resource
	private AreaDepositDao areaDepositDao;
	
	@Resource
	private MemberZhimaScoreDao memberZhimaScoreDao;
	@Resource
	private DepositZhimaReductionDao depositZhimaReductionDao;
	@Resource
	private CarIllegalService  carIllegalService;
	/**
	 * 根据查询条件，查询并返回DepositOrder的列表
	 * @param q 查询条件
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<DepositOrder> getDepositOrderList(Query q) {
		List<DepositOrder> list = null;
		try {
			//直接调用Dao方法进行查询
			list = depositOrderDao.queryAll(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		//如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<DepositOrder>(0) : list;
		return list; 
	}
	
	/**
	 * 根据查询条件，分页查询并返回DepositOrder的分页结果
	 * @param q 查询条件
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public PageFinder<DepositOrder> getDepositOrderPagedList(Query q) {
		PageFinder<DepositOrder> page = new PageFinder<DepositOrder>();
		
		List<DepositOrder> list = null;
		long rowCount = 0L;
		
		try {
			//调用dao查询满足条件的分页数据
			list = depositOrderDao.pageList(q);
			//调用dao统计满足条件的记录总数
			rowCount = depositOrderDao.count(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		
		//如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<DepositOrder>(0) : list;
	
		//将分页数据和记录总数设置到分页结果对象中
		page.setData(list);
		page.setRowCount(rowCount);
		
		return page;
	}	
	
	/**
	 * 根据主键，查询并返回一个DepositOrder对象
	 * @param id 主键id
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public DepositOrder getDepositOrder(String id) {
		DepositOrder obj = null;
		if (id == null || id.length() == 0) { //传入的主键无效时直接返回null
			log.info(Constant.ERR_MSG_INVALID_ARG + " id = " + id);
			return obj;
		}
		try {
			//调用dao，根据主键查询
			obj = depositOrderDao.get(id); 
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		
		return obj;
	}

	/**
	 * 根据主键数组，查询并返回一组DepositOrder对象
	 * @param ids 主键数组，数组中的主键值应当无重复值，如有重复值时，则返回的列表长度可能小于传入的数组长度
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<DepositOrder> getDepositOrderByIds(String[] ids) {
		List<DepositOrder> list = null;
		if (ids == null || ids.length == 0) {
			log.info(Constant.ERR_MSG_INVALID_ARG + " ids is null or empty array.");
		} else {
			try {
				//调用dao，根据主键数组查询
				list = depositOrderDao.getByIds(ids);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
		}
		
		//如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<DepositOrder>(0) : list;

		return list;
	}	
	
	/**
	 * 根据主键，删除特定的DepositOrder记录
	 * @param id 主键id
	 * @param operator 操作人对象
	 * @return
	 */
	@Transactional
	public ResultInfo<DepositOrder> delDepositOrder(String id, Operator operator) {
		ResultInfo<DepositOrder> resultInfo = new ResultInfo<DepositOrder>();
		if (id == null || id.length() == 0) { //传入的主键无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG );
			log.info(Constant.ERR_MSG_INVALID_ARG + " id = " + id);
			return resultInfo;
		}
		try {
		    //调用Dao执行删除，并判断删除语句执行结果
			int count = depositOrderDao.delete(id);
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
	 * 新增一条DepositOrder记录，执行成功后传入对象及返回对象的主键属性值不为null
	 * @param depositOrder 新增的DepositOrder数据（如果无特殊需求，新增对象的主键值请保留为null）
	 * @param operator 操作人对象
	 * @return
	 */
	@Transactional
	public ResultInfo<DepositOrder> addDepositOrder(DepositOrder depositOrder, Operator operator) {
		ResultInfo<DepositOrder> resultInfo = new ResultInfo<DepositOrder>();
		
		if (depositOrder == null) { //传入参数无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG );
			log.info(Constant.ERR_MSG_INVALID_ARG + " depositOrder = " + depositOrder);
		} else {
			try {
				//如果传入参数的主键为null，则调用生成主键的方法获取新的主键
				if (depositOrder.getDepositOrderNo() == null) {
					depositOrder.setDepositOrderNo(this.generatePK());
				}
				//如果传入的操作人不为null，则设置新增记录的操作人类型和操作人id
				if (operator != null) {
					depositOrder.setOperatorType(operator.getOperatorType());
					depositOrder.setOperatorId(operator.getOperatorId());
				}
				
				//设置创建时间和更新时间为当前时间
				Date now = new Date();
				depositOrder.setCreateTime(now);
				depositOrder.setUpdateTime(now);
				
				//填充默认值
				this.fillDefaultValues(depositOrder);
				
				//调用Dao执行插入操作
				depositOrderDao.add(depositOrder);
				resultInfo.setCode(Constant.SECCUESS);
				resultInfo.setData(depositOrder);
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
	 * 根据主键，更新一条DepositOrder记录
	 * @param depositOrder 更新的DepositOrder数据，且其主键不能为空
	 * @param operator 操作人对象
	 * @return
	 */
	@Transactional
	public ResultInfo<DepositOrder> updateDepositOrder(DepositOrder depositOrder, Operator operator) {
		ResultInfo<DepositOrder> resultInfo = new ResultInfo<DepositOrder>();
		
		if (depositOrder == null || depositOrder.getDepositOrderNo() == null) { //传入参数无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG );
			log.info(Constant.ERR_MSG_INVALID_ARG + " depositOrder = " + depositOrder);
		} else {
			try {
				//如果传入的操作人不为null，则设置更新记录的操作人类型和操作人id
				if (operator != null) {
					depositOrder.setOperatorType(operator.getOperatorType());
					depositOrder.setOperatorId(operator.getOperatorId());
				}
				
				//设置更新时间为当前时间
				depositOrder.setUpdateTime(new Date());
				
				//调用Dao执行更新操作，并判断更新语句执行结果
				int count = depositOrderDao.update(depositOrder);			
				if (count > 0) {
					resultInfo.setCode(Constant.SECCUESS);
				} else {
					resultInfo.setCode(Constant.FAIL);
				}
				resultInfo.setData(depositOrder);
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
		//return "D"+String.valueOf(new Date().getTime() * 1000000 + new Random().nextInt(1000000));
		return "Y"+primarykeyService.getValueByBizType(PrimarykeyConstant.depositOrderType);
	}
	
	/**
	 * 为DepositOrder对象设置默认值
	 * @param obj
	 */
	public void fillDefaultValues(DepositOrder obj) {
		if (obj != null) {
		    if (obj.getPayStatus() == null) {
		    	obj.setPayStatus(0);
		    }
		    if (obj.getIsAvailable() == null) {
		    	obj.setIsAvailable(0);
		    }
		}
	}

	@Override
	public Double getAmountByMemberNo(String memberNo) {
		// TODO Auto-generated method stub
		Double residueDeposit=0d;
		if(depositOrderDao.getAmountByMemberNo(memberNo)!=null){
			residueDeposit=depositOrderDao.getAmountByMemberNo(memberNo);
		}
		return residueDeposit;
	}

	@Override
	public DepositOrder getMemberDepositOrder(String memberNo) {
		// TODO Auto-generated method stub
		DepositOrder obj = null;
		if (memberNo == null || memberNo.length() == 0) { //传入的主键无效时直接返回null
			log.info(Constant.ERR_MSG_INVALID_ARG + " memberNo = " + memberNo);
			return obj;
		}
		try {
			//调用dao，根据主键查询
			obj = depositOrderDao.getMemberNo(memberNo); 
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		
		return obj;
	}
	

	@Override
	public ResultInfo<Deposit> getDepositByMemberNo(String memberNo,String addrRegion) {
		ResultInfo<Deposit> resultInfo=new ResultInfo<Deposit>();
		Deposit obj = new Deposit();
		String depositUse="";
		String cashDeposit="";
		addrRegion = convertAddregion(addrRegion);
		Double payableDeposit = this.getMemberPayableDeposit(memberNo, addrRegion);
		
		try {
			SysParam deUse = sysParamService.getByParamKey(DepositConstant.DEPOSIT_USE);
			if(deUse != null && !"".equals(deUse.getParamValue()) && deUse.getParamValue()!= null){
				String[] dd=deUse.getParamValue().split("&");
				depositUse=dd[0];
				cashDeposit=dd[1];
				
			}
			//调用dao，根据主键查询
			Member member=memberDao.get(memberNo);
			if(member ==null){
				resultInfo.setCode("0");
				resultInfo.setMsg("会员编号为空!");
				return resultInfo;
			}
			obj = depositOrderDao.getDepositByMemberNo(memberNo); 
			if(obj==null){
				obj = new Deposit();
				if(member!= null){
					obj.setPhone(member.getMobilePhone());
				}
			}else{
				if(member!= null){
					obj.setPhone(member.getMobilePhone());
				}
				if(obj.getDepositAmount()==null){
					obj.setDepositAmount(0d);
				}
				if(obj.getDeductedAmount()==null){
					obj.setDeductedAmount(0d);
				}
				if(obj.getFrozenAmount()==null){
					obj.setFrozenAmount(0d);
				}
				if(obj.getRemainAmount()==null){
					obj.setRemainAmount(0d);
				}
				if(obj.getAvailableAmount()==null){
					obj.setAvailableAmount(0d);
				}
				
			}
			obj.setDepositUse(depositUse);
			obj.setCashDeposit(cashDeposit);
			//1.判断押金支付情况
			Query q=new Query();
			DepositOrder dOrder=new DepositOrder();
			dOrder.setMemberNo(memberNo);
			dOrder.setPayStatus(1);//已支付
			dOrder.setIsAvailable(1);
			q.setQ(dOrder);
			List<DepositOrder> dOrderPay = getDepositOrderList(q);
			if (dOrderPay != null && dOrderPay.size() > 0) {
				
				if(obj.getAvailableAmount() != null){
					if(!obj.getAvailableAmount().equals(0.0)||!obj.getAvailableAmount().equals(0.00)){
						BigDecimal avaliableAmount = new BigDecimal(obj.getAvailableAmount());//剩余押金
						BigDecimal sysAmount = new BigDecimal(payableDeposit);//应缴押金
						if (member!=null&&member.getPayableDepositAmount()!=null) {
							//会员表中存储的会员应交的押金（第一次押金是根据getMemberPayableDeposit(memberNo, addrRegion)计算的，当支付成功后回调存入会员表）
							Double payableDepositAmount = member.getPayableDepositAmount();
							if (payableDepositAmount.compareTo(0.0) == 1) {
								obj.setDepositStatus(1);
								obj.setDepositAmountNeed(0.0);
							}else {
								obj.setDepositStatus(0);
								//获取需要 补交的金额
								Double need= sysAmount.subtract(avaliableAmount).doubleValue();
								obj.setDepositAmountNeed(ECCalculateUtils.round(need, 2));
							}
						}else {
							if (avaliableAmount.compareTo(sysAmount) == -1) {
								obj.setDepositStatus(0);
							}else{
								obj.setDepositStatus(1);
							}
							//获取需要 补交的金额
							Double need= sysAmount.subtract(avaliableAmount).doubleValue();
							if(need > 0){
								obj.setDepositAmountNeed(ECCalculateUtils.round(need, 2));
							}else{
								obj.setDepositAmountNeed(0.0);
							}
						}
						
						//设置补交押金的地区
						DepositOrder earliestDeposit = depositOrderDao.getEarliestDepositByMemberNo(memberNo);
						if(earliestDeposit != null){
							//按最先一笔押金所在的地区
							addrRegion = earliestDeposit.getAddrRegion();
						}
						
					}else{
						
						//如果补交金额为0时则要重新提交一笔押金
						obj.setDepositStatus(0);
						obj.setAvailableAmount(0.0);
						obj.setDepositAmountNeed(payableDeposit);
					}
				}else{
					
					//如果补交金额不存在时则要重新提交一笔押金
					obj.setDepositStatus(0);
					obj.setAvailableAmount(0.0);
					obj.setDepositAmountNeed(payableDeposit);
				}
			
				Integer tag=0;
				for(DepositOrder d:dOrderPay){
					if(d.getFrozenAmount().equals(0.00)||d.getFrozenAmount().equals(0.0)){
						tag=tag+1;
					}
				}
				//未交，已交，有退款未交，有退款已交
				if(tag.equals(dOrderPay.size())){//已经缴纳，没有处理中的退款
					obj.setRefundStatus(0);
					DepositRefund dRSearch=new DepositRefund();
					dRSearch.setMemberNo(memberNo);
					List<DepositRefund> list=depositRefundService.getDepositRefundList(new Query(dRSearch));
					//最新一条记录是审核不通过，则提示信息修改
					if(list!=null&&list.size()>0){
						if(list.get(0).getCencorStatus().equals(3)){
							resultInfo.setMsg(DepositConstant.pay_deposit_cencor);
						}else{
							resultInfo.setMsg(DepositConstant.pay_deposit);
						}
						
					}else{
						resultInfo.setMsg(DepositConstant.pay_deposit);
						
					}					
				}else if(0<tag&&tag<dOrderPay.size()){//有退款已交
					obj.setRefundStatus(1);
					resultInfo.setMsg(DepositConstant.refund_pay_deposit);
				}else{//有退款未交tag=0;
					obj.setRefundStatus(1);
					resultInfo.setMsg(DepositConstant.refund_no_pay_deposit);
				}
				
				
				
				
			}else {
				obj.setDepositStatus(0);//没有缴纳保证金
				obj.setAvailableAmount(0.0);
				obj.setRefundStatus(0);
				obj.setDepositAmountNeed(payableDeposit);
				resultInfo.setMsg(DepositConstant.no_pay_deposit);
			}
			//查出 有没有 退款记录
			DepositRefund dRSearchs=new DepositRefund();
			dRSearchs.setMemberNo(memberNo);
			List<DepositRefund> lists=depositRefundService.getDepositRefundList(new Query(dRSearchs));
			if(lists != null && lists.size()>0){
				obj.setReturnAmount(1);
				
			}else{
				obj.setReturnAmount(0);
			}
			resultInfo.setCode(Constant.SECCUESS);
			//获取平台设置的 押金金额
			if(payableDeposit != null){
				
				obj.setCarRentalAmount(payableDeposit);
			}else{
				obj.setCarRentalAmount(0.0);
			}
			
			//如果用户有剩余押金，则地区未最先一笔押金所在的地区，否则为入参值
			obj.setAddrRegion(addrRegion);
			
			resultInfo.setData(obj);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return resultInfo;


	}

	//前端有可能会传省级名与市级名相同的地址，需要去除
	private String convertAddregion(String addrRegion) {
		if(addrRegion != null && addrRegion.indexOf(",") != -1){
			String[] tempArray = addrRegion.split(",");
			if(tempArray.length > 1 && tempArray[0].equals(tempArray[1])){
				addrRegion = tempArray[0];
				if(tempArray.length > 2 && tempArray[2] != null || tempArray[2].equals("")){
					addrRegion += "," + tempArray[2];
				}
			}
		}
		return addrRegion;
	}

	@Override
	public ResultInfo<MemberDepositRefund> getDepositRefundByMemberNo(String memberNo,String refundGrounds,String refundGroundsMemo) {
		ResultInfo<MemberDepositRefund> resultInfoM=new ResultInfo<MemberDepositRefund>();
		//加入限制条件，1.一个是有没有已结束，但未支付订单；一个是有没有已创建，但还未结束/未取消的订单，都不能申请退款
		List<Order> listOrder=orderDao.getOrderNoRefundByMemberNo(memberNo);
		if(listOrder!=null&&listOrder.size()>0){
			resultInfoM.setCode(DepositConstant.no_refund);
			resultInfoM.setMsg(DepositConstant.no_refund_msg);
		}else{
			//2.，最近结束/取消的订单，距离当前时间，应当要超过20天。这个20天，应该是来自系统参数。未超过20天的也不能申请退款
			//获取系统参数
			SysParam sysp = sysParamService.getByParamKey(DepositConstant.no_refund_days);
			Order orderNew=orderDao.getOrderNewNoRefundByMemberNo(memberNo);
			Long days=Long.parseLong(sysp.getParamValue());
			Long daysRange=0l;
			if(orderNew!=null){
				daysRange=ECDateUtils.differDays(orderNew.getFinishTime(),new Date());
				if(daysRange<days){
					resultInfoM.setCode(DepositConstant.no_refund1);
					//resultInfoM.setMsg(DepositConstant.no_refund_msg1);
					resultInfoM.setMsg("抱歉，您在最近"+days+"天内有用车记录，不能申请退款！");
				}else{
						//1.查出会员的保证金可以申请退款的记录
						List<DepositOrder> list=depositOrderDao.getDepositRefundByMemberNo(memberNo);
						//2.根据结果中的 支付流水号(PAYMENT_FLOW_NO)、支付方式（PAYMENT_METHOD）等，确认在支付记录表(t_payment_record)中 存在对应匹配的记录
						for(DepositOrder dOrder:list){
							List<PaymentRecord> pRecord=paymentRecordDao.getByDOrder(3,dOrder.getDepositOrderNo(),dOrder.getPaymentMethod());
							//3.如果确认有匹配的支付记录，则生成t_deposit_refund数据，写入t_deposit_refund表。
							if(pRecord!=null&&pRecord.size()>0){
								DepositRefund dRefund=new DepositRefund();
								dRefund.setDepositOrderNo(dOrder.getDepositOrderNo());
								dRefund.setRefundAmount(dOrder.getRemainAmount());
								dRefund.setMemberNo(dOrder.getMemberNo());
								dRefund.setRefundMethod(dOrder.getPaymentMethod());
								dRefund.setApplyTime(new Date());
								Member member=memberDao.get(dOrder.getMemberNo());
								if(member!=null){
									dRefund.setMemberName(member.getMemberName());
									dRefund.setMobilePhone(member.getMobilePhone());
								}
								dRefund.setRefundStatus(0);//未退款
								dRefund.setRefundGrounds(refundGrounds);
								dRefund.setRefundGroundsMemo(refundGroundsMemo);
								ResultInfo<DepositRefund> resultInfo=depositRefundService.addDepositRefund(dRefund, null);
								if(resultInfo.getCode().equals("1")){
									//4.更新t_deposit_order表： 将frozen_amount值，更新为与remain_amount值一致。
									dOrder.setFrozenAmount(dOrder.getRemainAmount());
									depositOrderDao.update(dOrder);
								}
							}
						}
						//5.已申请退款金额 ： sum (本次生成的t_deposit_refund记录的REFUND_AMOUNT字段合计）
						MemberDepositRefund mdRefund=depositRefundDao.getApplyAmountByMemberNo(memberNo);
						if(mdRefund!=null){
							resultInfoM.setCode(Constant.SECCUESS);
							resultInfoM.setData(mdRefund);
						}else{
							resultInfoM.setCode(Constant.FAIL);
						}
					}
				
			}else{
				
					//1.查出会员的保证金可以申请退款的记录
					List<DepositOrder> list=depositOrderDao.getDepositRefundByMemberNo(memberNo);
					//2.根据结果中的 支付流水号(PAYMENT_FLOW_NO)、支付方式（PAYMENT_METHOD）等，确认在支付记录表(t_payment_record)中 存在对应匹配的记录
					for(DepositOrder dOrder:list){
						List<PaymentRecord> pRecord=paymentRecordDao.getByDOrder(3,dOrder.getDepositOrderNo(),dOrder.getPaymentMethod());
						//3.如果确认有匹配的支付记录，则生成t_deposit_refund数据，写入t_deposit_refund表。
						if(pRecord!=null&&pRecord.size()>0){
							DepositRefund dRefund=new DepositRefund();
							dRefund.setDepositOrderNo(dOrder.getDepositOrderNo());
							dRefund.setRefundAmount(dOrder.getRemainAmount());
							dRefund.setMemberNo(dOrder.getMemberNo());
							dRefund.setRefundMethod(dOrder.getPaymentMethod());
							Member member=memberDao.get(dOrder.getMemberNo());
							if(member!=null){
								dRefund.setMemberName(member.getMemberName());
								dRefund.setMobilePhone(member.getMobilePhone());
							}
							dRefund.setRefundStatus(0);//未退款
							dRefund.setRefundGrounds(refundGrounds);
							dRefund.setRefundGroundsMemo(refundGroundsMemo);
							ResultInfo<DepositRefund> resultInfo=depositRefundService.addDepositRefund(dRefund, null);
							if(resultInfo.getCode().equals("1")){
								//4.更新t_deposit_order表： 将frozen_amount值，更新为与remain_amount值一致。
								dOrder.setFrozenAmount(dOrder.getRemainAmount());
								depositOrderDao.update(dOrder);
							}
						}
					}
					//5.已申请退款金额 ： sum (本次生成的t_deposit_refund记录的REFUND_AMOUNT字段合计）
					MemberDepositRefund mdRefund=depositRefundDao.getApplyAmountByMemberNo(memberNo);
					if(mdRefund!=null){
						resultInfoM.setCode(Constant.SECCUESS);
						resultInfoM.setData(mdRefund);
					}else{
						resultInfoM.setCode(Constant.FAIL);
					}
				}
			
		} 
		return resultInfoM;
	}

	@Override
	public ResultInfo<MemberDepositRefund> getIsDepositRefundByMemberNo(String memberNo) {
		ResultInfo<MemberDepositRefund> resultInfoM=new ResultInfo<MemberDepositRefund>();
		Member m=memberDao.get(memberNo);
		if(m != null &&  m.getIsBlacklist() ==1){
			resultInfoM.setCode("0");
			resultInfoM.setMsg("您已经进入黑名单,不能申请退款");
			return resultInfoM;
		}
		
		CarIllegal c=new CarIllegal();
		c.setDriverId(memberNo);
		List<CarIllegal> list =carIllegalService.getCarIllegalList(new Query(c));
		if(list != null && list.size()>0){
			for (CarIllegal carIllegal : list) {
				if(carIllegal.getProcessingStatus()==0 || carIllegal.getProcessingStatus()==1){
					resultInfoM.setCode("0");
					resultInfoM.setMsg("您有未处理或者处理中的违章，不能申请退款");
					return resultInfoM;
				}
			}
		}
		
		//加入限制条件，1.一个是有没有已结束，但未支付订单；一个是有没有已创建，但还未结束/未取消的订单，都不能申请退款
		List<Order> listOrder=orderDao.getOrderNoRefundByMemberNo(memberNo);
		if(listOrder!=null&&listOrder.size()>0){
			resultInfoM.setCode(DepositConstant.no_refund);
			resultInfoM.setMsg(DepositConstant.no_refund_msg);
			return resultInfoM;
		}else{
			//2.，最近结束/取消的订单，距离当前时间，应当要超过20天。这个20天，应该是来自系统参数。未超过20天的也不能申请退款
			//获取系统参数
			SysParam sysp = sysParamService.getByParamKey(DepositConstant.no_refund_days);
			Order orderNew=orderDao.getOrderNewNoRefundByMemberNo(memberNo);
			Long days=Long.parseLong(sysp.getParamValue());
			Long daysRange=0l;
			if(orderNew!=null){
				daysRange=ECDateUtils.differDays(orderNew.getFinishTime(),new Date());
				if(daysRange<days){
					resultInfoM.setCode(DepositConstant.no_refund1);
					//resultInfoM.setMsg(DepositConstant.no_refund_msg1);
					resultInfoM.setMsg("抱歉，您在最近"+days+"天内有用车记录，不能申请退款！");
				}else{
					resultInfoM.setCode(Constant.SECCUESS);
					return resultInfoM;
					}
				}else{
					resultInfoM.setCode(Constant.SECCUESS);
					return resultInfoM;
				}
		}
		return resultInfoM;
		
	}

	@Override
	public Double getMemberPayableDeposit(String memberNo,String addrRegion) {
		SysParam sysParam = sysParamService.getByParamKey(DepositConstant.deposit_amount_key);//默认押金
		Double finalAmount = Double.parseDouble(sysParam.getParamValue());//设置最终返回押金的默认值
		
		//如果用户有剩余押金，则地区未最先一笔押金所在的地区，否则为入参值
		DepositOrder earliestDeposit = depositOrderDao.getEarliestDepositByMemberNo(memberNo);
		if(earliestDeposit != null){
			addrRegion = convertAddregion(earliestDeposit.getAddrRegion());
		}
		
		if(memberNo != null && !memberNo.equals("")){
			Member member = memberDao.get(memberNo);
			if(member != null){
				
				//开始计算地区押金，如果有则按该地区来
				if(addrRegion != null && !addrRegion.trim().equals("")){
					AreaDeposit areaDepositQuery = new AreaDeposit();//查询地区应缴押金
					areaDepositQuery.setAddrRegion(addrRegion.trim().replace(",", ""));
					areaDepositQuery.setCensorStatus(1);//已审核
					areaDepositQuery.setIsAvailable(1);//已使用
					List<AreaDeposit> areaDepositList = areaDepositDao.queryAll(new Query(areaDepositQuery));
					if(areaDepositList != null && !areaDepositList.isEmpty()){//如果地区押金不为空时改变最终返回押金
						AreaDeposit areaDeposit = areaDepositList.get(0);
						finalAmount = areaDeposit.getDepositAmount();
					}
				}
				
				//开始计算会员芝麻信用分
				MemberZhimaScore memberZhimaScore = memberZhimaScoreDao.get(memberNo);//得到会员信用分
				if(memberZhimaScore != null && memberZhimaScore.getScore() != null){
					
					DepositZhimaReduction depositZhimaReduction = depositZhimaReductionDao.getClosestReductionByParameter(memberZhimaScore.getScore().intValue());
					if(depositZhimaReduction != null && depositZhimaReduction.getReductionAmount() != null){
						finalAmount = ECNumberUtils.roundDoubleWithScale(finalAmount - depositZhimaReduction.getReductionAmount(), 2);
					
					}
				}
				
				//系统参数设置的最低信用分金额
				sysParam = sysParamService.getByParamKey("credit_score_min_amount");
				Double amount = Double.parseDouble(sysParam.getParamValue());
				//支付押金不能低于系统设计的最低押金
				if (amount.compareTo(finalAmount) >= 0) {
					finalAmount = amount;
				}
			}
		}
		finalAmount = ECCalculateUtils.round(finalAmount, 2);
		return finalAmount;
	}

	@Override
	public PageFinder<DepositOrderForMgt> getMemberDepositList(Query q) {
		PageFinder<DepositOrderForMgt> page = new PageFinder<DepositOrderForMgt>();
		List<DepositOrderForMgt> list = null;
		long rowCount = 0L;
		try {
			//调用dao查询满足条件的分页数据
			list = depositOrderDao.getMemberDepositList(q);
			//调用dao统计满足条件的记录总数
			rowCount = depositOrderDao.countForMgt(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		//如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<DepositOrderForMgt>(0) : list;
		//将分页数据和记录总数设置到分页结果对象中
		page.setData(list);
		page.setRowCount(rowCount);
		return page;
	}

}
