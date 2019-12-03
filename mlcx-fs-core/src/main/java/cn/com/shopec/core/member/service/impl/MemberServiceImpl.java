package cn.com.shopec.core.member.service.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import cn.com.shopec.common.Operator;
import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.common.cache.CommonCacheUtil;
import cn.com.shopec.common.constants.Constant;
import cn.com.shopec.common.utils.ECDateUtils;
import cn.com.shopec.common.utils.ECNumberUtils;
import cn.com.shopec.common.utils.HttpURLRequestUtil;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.finace.dao.DepositOrderDao;
import cn.com.shopec.core.finace.model.DepositOrder;
import cn.com.shopec.core.finace.service.DepositOrderService;
import cn.com.shopec.core.marketing.dao.CouponDao;
import cn.com.shopec.core.marketing.model.Coupon;
import cn.com.shopec.core.marketing.model.SendMessage;
import cn.com.shopec.core.marketing.service.SendMessageService;
import cn.com.shopec.core.member.common.MemberConstant;
import cn.com.shopec.core.member.dao.MemberDao;
import cn.com.shopec.core.member.dao.MemberLevelDao;
import cn.com.shopec.core.member.model.CompanyPerson;
import cn.com.shopec.core.member.model.Member;
import cn.com.shopec.core.member.model.MemberCompanyPerson;
import cn.com.shopec.core.member.model.MemberCountVo;
import cn.com.shopec.core.member.model.MemberDepositOrder;
import cn.com.shopec.core.member.model.MemberLevel;
import cn.com.shopec.core.member.model.MemberOneDayVO;
import cn.com.shopec.core.member.service.CompanyPersonService;
import cn.com.shopec.core.member.service.MemberService;
import cn.com.shopec.core.order.dao.OrderDao;
import cn.com.shopec.core.order.dao.PricingPackOrderDao;
import cn.com.shopec.core.order.model.Order;
import cn.com.shopec.core.order.model.PricingPackOrder;
import cn.com.shopec.core.order.service.OrderPayService;
import cn.com.shopec.core.system.common.PrimarykeyConstant;
import cn.com.shopec.core.system.service.PrimarykeyService;

/**
 * Member 服务实现类
 */
@Service
public class MemberServiceImpl implements MemberService {

	private static final Log log = LogFactory.getLog(MemberServiceImpl.class);
	
	@Resource
	private MemberDao memberDao;
	@Resource
	private CommonCacheUtil commonCacheUtil;
	@Resource
	private CompanyPersonService companyPersonService;
	@Resource
	private DepositOrderService depositOrderService;
	@Resource
	private OrderPayService orderPayService;
	@Resource
	private OrderDao orderDao;
	@Resource
	private SendMessageService sendMessageService;
	@Resource
	private PricingPackOrderDao pricingPackOrderDao;
	@Resource
	private PrimarykeyService primarykeyService;

	@Resource
	private CouponDao couponDao;

	@Resource
	private DepositOrderDao depositOrderDao;
	
	@Resource
	private MemberLevelDao memberLevelDao;
	
	@Value("${image_path}")
	private String serverPath;
	
	@Value("${res_img_path}")
	private String resImgPath;
	
	@Value("${triggerEvent_path}")
	private String trigger;

	/**
	 * 根据查询条件，查询并返回Member的列表
	 * @param q 查询条件
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<Member> getMemberList(Query q) {
		List<Member> list = null;
		try {
			//直接调用Dao方法进行查询
			list = memberDao.queryAll(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		//如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<Member>(0) : list;
		return list; 
	}
	
	/**
	 * 根据查询条件，分页查询并返回Member的分页结果
	 * @param q 查询条件
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public PageFinder<Member> getMemberPagedList(Query q) {
		PageFinder<Member> page = new PageFinder<Member>();
		
		List<Member> list = null;
		long rowCount = 0L;
		
		try {
			//调用dao查询满足条件的分页数据
			list = memberDao.getMemberPagedList(q);
			//调用dao统计满足条件的记录总数
			rowCount = memberDao.getMemberPagedListCount(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		
		//如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<Member>(0) : list;
	
		//将分页数据和记录总数设置到分页结果对象中
		page.setData(list);
		page.setRowCount(rowCount);
		
		return page;
	}	
	
	/**
	 * 根据查询条件，分页查询并返回Member的分页结果
	 * @param q 查询条件
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public PageFinder<Member> getMemberBalancePagedList(Query q) {
		PageFinder<Member> page = new PageFinder<Member>();
		
		List<Member> list = null;
		long rowCount = 0L;
		
		try {
			//调用dao查询满足条件的分页数据
			list = memberDao.getMemberBalancePagedList(q);
			//调用dao统计满足条件的记录总数
			rowCount = memberDao.getMemberPagedListCount(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		
		//如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<Member>(0) : list;
	
		//将分页数据和记录总数设置到分页结果对象中
		page.setData(list);
		page.setRowCount(rowCount);
		
		return page;
	}
	
	/**
	 * 根据主键，查询并返回一个Member对象
	 * @param id 主键id
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public Member getMember(String id) {
		Member obj = null;
		if (id == null || id.length() == 0) { //传入的主键无效时直接返回null
			log.info(Constant.ERR_MSG_INVALID_ARG + " id = " + id);
			return obj;
		}
		try {
			//调用dao，根据主键查询
			obj = memberDao.get(id); 
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		
		return obj;
	}

	/**
	 * 根据主键数组，查询并返回一组Member对象
	 * @param ids 主键数组，数组中的主键值应当无重复值，如有重复值时，则返回的列表长度可能小于传入的数组长度
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<Member> getMemberByIds(String[] ids) {
		List<Member> list = null;
		if (ids == null || ids.length == 0) {
			log.info(Constant.ERR_MSG_INVALID_ARG + " ids is null or empty array.");
		} else {
			try {
				//调用dao，根据主键数组查询
				list = memberDao.getByIds(ids);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
		}
		
		//如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<Member>(0) : list;

		return list;
	}	
	
	/**
	 * 根据主键，删除特定的Member记录
	 * @param id 主键id
	 * @param operator 操作人对象
	 * @return
	 */
	@Transactional
	public ResultInfo<Member> delMember(String id, Operator operator) {
		ResultInfo<Member> resultInfo = new ResultInfo<Member>();
		if (id == null || id.length() == 0) { //传入的主键无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG );
			log.info(Constant.ERR_MSG_INVALID_ARG + " id = " + id);
			return resultInfo;
		}
		try {
		    //调用Dao执行删除，并判断删除语句执行结果
			int count = memberDao.delete(id);
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
	 * 新增一条Member记录，执行成功后传入对象及返回对象的主键属性值不为null
	 * @param member 新增的Member数据（如果无特殊需求，新增对象的主键值请保留为null）
	 * @param operator 操作人对象
	 * @return
	 */
	@Transactional
	public ResultInfo<Member> addMember(Member member, Operator operator) {
		ResultInfo<Member> resultInfo = new ResultInfo<Member>();
		
		if (member == null) { //传入参数无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG );
			log.info(Constant.ERR_MSG_INVALID_ARG + " member = " + member);
		} else {
			try {
				//如果传入参数的主键为null，则调用生成主键的方法获取新的主键
				if (member.getMemberNo() == null) {
					member.setMemberNo(this.generatePK());
				}
				//如果传入的操作人不为null，则设置新增记录的操作人类型和操作人id
				if (operator != null) {
					member.setOperatorType(operator.getOperatorType());
					member.setOperatorId(operator.getOperatorId());
				}
				
				//设置创建时间和更新时间为当前时间
				Date now = new Date();
				member.setCreateTime(now);
				member.setUpdateTime(now);
				if(member.getRegisterTime() == null) {
					member.setRegisterTime(now);
				}
				//填充默认值
				this.fillDefaultValues(member);
				
				//调用Dao执行插入操作
				memberDao.add(member);
				resultInfo.setCode(Constant.SECCUESS);
				resultInfo.setData(member);
				//会员注册成功后，在集团员工表通过电话匹配，匹配到的话，在集团员工表中更新member_no
				if(resultInfo.getCode().equals("1")){
					CompanyPerson cp = new CompanyPerson();
					cp.setMobilePhone(member.getMobilePhone());
					List<CompanyPerson> lists = companyPersonService.getCompanyPersonList(new Query(cp));
					if(lists!=null&&lists.size()>0){
						cp.setId(lists.get(0).getId());
						cp.setMemberNo(member.getMemberNo());
						cp.setRegisterStatus(1);
						companyPersonService.updateCompanyPerson(cp, operator);
						member.setCompanyId(lists.get(0).getCompanyId());
						memberDao.update(member);
					}
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
	 * 根据主键，更新一条Member记录
	 * @param member 更新的Member数据，且其主键不能为空
	 * @param operator 操作人对象
	 * @return
	 */
	@Transactional
	public ResultInfo<Member> updateMember(Member member, Operator operator) {
		ResultInfo<Member> resultInfo = new ResultInfo<Member>();
		
		if (member == null || member.getMemberNo() == null) { //传入参数无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG );
			log.info(Constant.ERR_MSG_INVALID_ARG + " member = " + member);
		} else {
			try {
				//如果传入的操作人不为null，则设置更新记录的操作人类型和操作人id
				if (operator != null) {
//					member.setOperatorType(operator.getOperatorType());
//					member.setOperatorId(operator.getOperatorId());
					
					member.setOperatorType(operator.getOperatorType());
					member.setCencorId(operator.getOperatorId());
				}
				
				//设置更新时间为当前时间
				member.setUpdateTime(new Date());
				
				//调用Dao执行更新操作，并判断更新语句执行结果
				int count = memberDao.update(member);			
				if (count > 0) {
					resultInfo.setCode(Constant.SECCUESS);
				} else {
					resultInfo.setCode(Constant.FAIL);
				}
				resultInfo.setData(member);
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
//		return String.valueOf(new Date().getTime() * 1000000 + new Random().nextInt(1000000));
		return primarykeyService.getValueByBizType(PrimarykeyConstant.memberType)+"";
	}
	
	/**
	 * 为Member对象设置默认值
	 * @param obj
	 */
	public void fillDefaultValues(Member obj) {
		if (obj != null) {
		    if (obj.getMemberType() == null) {
		    	obj.setMemberType(1);
		    }
		    if (obj.getIsJoined() == null) {
		    	obj.setIsJoined(0);
		    }
		    if (obj.getIsBlacklist() == null) {
		    	obj.setIsBlacklist(0);
		    }
		    if (obj.getMemberPointsValues() == null) {
		    	obj.setMemberPointsValues(0);
		    }
		}
	}
/**
 * 验证注册手机号的唯一性
 * */
	@Override
	public Member getMemberByPhone(String phone) {
		// TODO Auto-generated method stub
		Member obj = null;
		if (phone == null || phone.length() == 0) { //传入的主键无效时直接返回null
			log.info(Constant.ERR_MSG_INVALID_ARG + " phone = " + phone);
			return obj;
		}
		try {
			//调用dao，根据主键查询
			obj =memberDao.getByPhone(phone); 
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return obj;
	}

	/**
	 * 根据会员表主键，取的会员的常用基础信息
	 * @param memberNo
	 * @return
	 */
	public Member getMemberBasicInfo(String memberNo) {
		Member member = null;
		if(memberNo == null || memberNo.length() == 0) {
			log.info(Constant.ERR_MSG_INVALID_ARG + " memberNo = " + memberNo);
			return member;
		}
		String cacheKey = "MEMBER_BASIC_" + memberNo;
		member = (Member)this.commonCacheUtil.getObject(cacheKey); //首先尝试从缓存取会员基础信息

		if(member == null) { //缓存中没有，则改为从db取
			try {
				member = memberDao.getMemberBasicInfo(memberNo);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
			if(member != null) { //db取得的数据，存入cache
				boolean res = this.commonCacheUtil.setObject(cacheKey, member, MemberConstant.EXPIRES_TIME_OF_MEMBER_BASIC_INFO_IN_CACHE);
				if(!res) {
					log.error("Save member basic info to cace failed.");
				}
			}
		}
		return member;
	}
	/**
	 *通过会员ID得到认证状态 
	 */
	@Override
	public ResultInfo<Integer> getCensorStatus(String memberNo) {
		ResultInfo<Integer> result = new ResultInfo<>();
		Member member = memberDao.get(memberNo);
		if (member!=null&&member.getCensorStatus()!=null) {
			result.setData(member.getCensorStatus());
			result.setCode(MemberConstant.success_code);
			result.setMsg("");
		}else {
			result.setCode(MemberConstant.fail_code);
			result.setMsg(MemberConstant.fail_msg);
		}
		return result;
	}

	@Override
	public ResultInfo<String> uploadLicense(String memberNo, String idCard, String memberName) {
		ResultInfo<String> result = new ResultInfo<>();
		result.setData("");
		//判断身份证号码是否为空或者是否是18位
		if (idCard==null||idCard.trim().length()!=18) {
			result.setCode(MemberConstant.fail_code);
			result.setMsg(MemberConstant.fail_idCard_Check);
			return result;
		}
		if (memberName==null||memberName.trim().length()==0) {
			result.setCode(MemberConstant.fail_code);
			result.setMsg(MemberConstant.fail_memberName_Check);
			return result;
		}
		
		Member member = memberDao.get(memberNo);
		if (member!=null) {
			if(member.getCensorStatus()==3 || member.getCensorStatus()==0 ){
				member.setCensorStatus(2);
			}
			member.setIdCard(idCard);
			member.setMemberName(memberName);
			memberDao.update(member);
			result.setCode(MemberConstant.success_code);
			result.setMsg("");
		}else {
			result.setCode(MemberConstant.fail_code);
			result.setMsg(MemberConstant.fail_msg);
		}
		return result;
	}

	
	
	
	
	
	@Override
	public ResultInfo<Member> getMemberCompany(Query q) {
		// TODO Auto-generated method stub 
		ResultInfo<Member> result = new ResultInfo<>();
		 
		List<MemberCompanyPerson> list = memberDao.getMemberCompany(q);
		if(list.size() > 0){
			for(MemberCompanyPerson memberPerson : list){
				Member member = memberDao.get(memberPerson.getMemberNo());
				if (member!=null) {
					member.setMemberType(2);
					member.setCompanyId(memberPerson.getCompanyId());
					memberDao.update(member);
					result.setCode(MemberConstant.success_code);
					result.setMsg("操作成功！");
				}else {
					result.setCode(MemberConstant.fail_code);
					result.setMsg(MemberConstant.fail_msg);
				}
			}
		}else{
			result.setCode(MemberConstant.fail_code);
			result.setMsg("没有信息！");
		}
		return result ;
	}

	/**
	 * 退回保证金功能
	 */
	@Override
	public ResultInfo<Member> payRefund(String memberNo,String out_refund_no,String refundNo) {
		// TODO Auto-generated method stub
		ResultInfo<Member> result = new ResultInfo<>();
		MemberDepositOrder memberDepositOrder = new MemberDepositOrder();
		String transaction_id="";
		String out_trade_no=""; 
		int total_fee=0;
		int refund_fee=0;
		String op_user_id="";
		String returnCode ="";
		
		
		memberDepositOrder = memberDao.payRefund(memberNo);
		
		if(memberDepositOrder != null){//查询到会员有交保证金
			  transaction_id=memberDepositOrder.getPaymentFlowNo();
			  out_trade_no=memberDepositOrder.getBizObjNo(); 
			  total_fee=Integer.parseInt(memberDepositOrder.getDepositAmount());
			  refund_fee=Integer.parseInt(memberDepositOrder.getDepositAmount());
			  op_user_id=memberNo; 
			
			List<Order> listorder = orderDao.notPaidOrder(memberNo);//查询最新订单
			if(listorder.size() > 0){
			 Order order = listorder.get(0);
			  
			 Long diffDays = ECDateUtils.differDays(order.getUpdateTime(),new Date());
				
				if(order.getPayStatus() == 0){//有未支付订单
					result.setCode(MemberConstant.fail_code);
					result.setMsg("有未支付订单！");
				}else if(diffDays < 20 ){//在20天内有订单
					result.setCode(MemberConstant.fail_code);
					result.setMsg("在20天内有使用车辆！");
				}else{
					DepositOrder depositOrder = new DepositOrder();
					depositOrder = depositOrderService.getMemberDepositOrder(memberNo);//根据会员编号查询保证金信息
					
					if(depositOrder.getPaymentMethod().equals(2)){//微信退款
						try {
							returnCode = orderPayService.wxPayRefundRequest(transaction_id, out_trade_no, out_refund_no, total_fee, refund_fee, op_user_id);

			                  if ("SUCCESS".equals(returnCode)) {
			                	
			  					depositOrder.setIsAvailable(0);
			  					//修改保证金支付记录
			  					depositOrderService.updateDepositOrder(depositOrder, null);
			  					
			  					//修改退款记录
			  					
			  					
			  					result.setCode(MemberConstant.success_code);
			  					result.setMsg("微信退款成功！");
			                  }else{

				  				result.setCode(MemberConstant.fail_code);
				  				result.setMsg("微信退款失败！");
			                  }
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							result.setCode(MemberConstant.fail_code);
			  				result.setMsg("微信退款失败！");
						}
						
					}else if(depositOrder.getPaymentMethod().equals(1)){//支付宝退款
					}
				}
			}
		}else{
			result.setCode(MemberConstant.fail_code);
			result.setMsg("没有交保证金信息！");
		}
		
		return result;
	}

	@Override
	public void getDriverLicense(Date nowDate) {
		// TODO Auto-generated method stub
		Member memberSearch=new Member();
		memberSearch.setExpirationDateEnd(nowDate);//有效期在当前日期之前的
		memberSearch.setCensorStatus(1);//已审核通过的
		Query q=new Query();
		q.setQ(memberSearch);
		List<Member> memberList=memberDao.queryAll(q);
		if(memberList!=null&&memberList.size()>0){
			for(Member member:memberList){
				Member memberNow=new Member();
				memberNow.setMemberNo(member.getMemberNo());
				memberNow.setCensorStatus(0);//未审核
				updateMember(memberNow, null);
			}
		}
	}

	@Override
	public void getDriverLicenseOneDay(Date nowDateOneDay,Integer days) {
		ResultInfo<SendMessage> resultInfo=new ResultInfo<SendMessage>();
		// TODO Auto-generated method stub
		Member memberSearch=new Member();
		memberSearch.setExpirationDateEnd(nowDateOneDay);//有效期在当前日期+1天之前的
		memberSearch.setCensorStatus(1);//已审核通过的
		Query q=new Query();
		q.setQ(memberSearch);
		List<Member> memberList=memberDao.queryAll(q);
		if(memberList!=null&&memberList.size()>0){
			for(Member member:memberList){
				SendMessage sendMessage=new SendMessage();
				sendMessage.setMemberNo(member.getMemberNo());
				sendMessage.setMessageType(3);//驾驶证快过期了
				q.setQ(sendMessage);
				List<SendMessage> list2=sendMessageService.getSendMessageList(q);
				if(list2!=null&&list2.size()>0){
					for(SendMessage message:list2){
						message.setMessageType(3);
						message.setMessageContent("您的驾驶证还有"+days+"天就到期了，为不影响您的使用，请重新上传驾驶证图片或联系客服。");
						resultInfo=sendMessageService.updateSendMessage(message, null);
						if(resultInfo.getCode().equals("1")){
							log.info("============记录已结束");
						}
					}
				}else{
					sendMessage.setMessageContent("您的驾驶证还有"+days+"天就到期了，为不影响您的使用，请重新上传驾驶证图片或联系客服。");
					resultInfo=sendMessageService.addSendMessage(sendMessage, null);
					if(resultInfo.getCode().equals("1")){
						log.info("============记录已结束");
					}
				}
			}
		}
		
	}

	@Override
	public ResultInfo<List<PricingPackOrder>> availablePackageList(String memberNo, int pageNo, int pageSize) {
		ResultInfo<List<PricingPackOrder>> result = new ResultInfo<List<PricingPackOrder>>();
		if(StringUtils.isEmpty(memberNo)){
			result.setCode(Constant.FAIL);
			result.setMsg("没有会员编号");
			return result;
		}
		try{
			PricingPackOrder qo = new PricingPackOrder();
			qo.setMemberNo(memberNo);
			List<PricingPackOrder> list = pricingPackOrderDao.availablePackageList(new Query(pageNo,pageSize,qo));
			if(list !=null && list.size()>0){
				for (PricingPackOrder pricingPackOrder : list) {
					pricingPackOrder.setIsAvailable(1);
				}
			}
			result.setCode(Constant.SECCUESS);
			result.setMsg("success.");
			result.setData(list);
		}catch(Exception e){
			e.printStackTrace();
			result.setCode(Constant.FAIL);
			result.setMsg("unknown exception.");
		}
		
		return result;
	}
	//查出可用的 优惠券	
	@Override
	public ResultInfo<List<Coupon>>memberCouponList(String memberNo, int pageNo, int pageSize,Integer type) {
		ResultInfo<List<Coupon>> result = new ResultInfo<List<Coupon>>();
		if(StringUtils.isEmpty(memberNo)){
			result.setCode(Constant.FAIL);
			result.setMsg("没有会员编号");
			return result;
		}
		try{
			Coupon cp = new Coupon();
			cp.setMemberNo(memberNo);
			cp.setIsAvailable(1);
//			cp.setCouponType(1);
			cp.setUsedStatus(0);
			if(type!=null &&  type == 1){//过期的优惠卷
				cp.setAvailableTime2End(new Date());
			}else{
				cp.setAvailableTime2Start(new Date());
			}
			List<Coupon> list = couponDao.pageList(new Query(pageNo,pageSize,cp));
			result.setCode(Constant.SECCUESS);
			result.setMsg("success.");
			result.setData(list);
		}catch(Exception e){
			e.printStackTrace();
			result.setCode(Constant.FAIL);
			result.setMsg("unknown exception.");
		}
		
		return result;
	}

	
	
	@Override
	public ResultInfo<List<PricingPackOrder>> disablePackageList(String memberNo, int pageNo, int pageSize) {
		ResultInfo<List<PricingPackOrder>> result = new ResultInfo<List<PricingPackOrder>>();
		if(StringUtils.isEmpty(memberNo)){
			result.setCode(Constant.FAIL);
			result.setMsg("param is error.");
			return result;
		}
		try{
			Date data=new Date();
			PricingPackOrder qo = new PricingPackOrder();
			qo.setMemberNo(memberNo);
			List<PricingPackOrder> list = pricingPackOrderDao.disablePackageList(new Query(pageNo,pageSize,qo));
			for (PricingPackOrder pricingPackOrder : list) {
				if(pricingPackOrder.getVailableTime2() !=null && pricingPackOrder.getVailableTime1()!= null){
					if(pricingPackOrder.getVailableTime2().getTime() < data.getTime() ){
						pricingPackOrder.setIsAvailable(2);
					}
					if((pricingPackOrder.getUserdMinute()>=pricingPackOrder.getPackMinute()) && pricingPackOrder.getPayStatus()==1 ){
						pricingPackOrder.setIsAvailable(3);
					}
					if(pricingPackOrder.getVailableTime1().getTime()>data.getTime() || pricingPackOrder.getPayStatus()==0 || pricingPackOrder.getIsAvailable()==0){
						pricingPackOrder.setIsAvailable(0);
					}
				}else{
					pricingPackOrder.setIsAvailable(0);
				}
				
			
			}
			
		//	List<PricingPackOrder> listss = pricingPackOrderDao.disablePackageList(new Query(pageNo,pageSize,qo));
			result.setCode(Constant.SECCUESS);
			result.setMsg("success.");
			result.setData(list);
		}catch(Exception e){
			e.printStackTrace();
			result.setCode(Constant.FAIL);
			result.setMsg("unknown exception.");
		}
		
		return result;
	}

	//根据邀请码    获取推荐人id 
	public Member getInvitationCode(String invitationCode) {
		return memberDao.getInvitationCode(invitationCode);
	}
	

	//根据推荐人id  查出推荐人的用户名 真实姓名 手机号码  电话号码
	public Member getInvitationNamePhone(String id) {
		return  memberDao.getInvitationNamePhone(id);
	}

	@Override
	public Member getMemberByToken(String token) {
		return memberDao.getMemberByToken(token);
	}

	/**
	 * 根据请求时间查询出会员的相关数据
	 * @param startTime   当天开始时间  0分0秒
	 * @param endTime	请求时间
	 */
	@Override
	public MemberOneDayVO getIndexValue(String startTime, String endTime) {
		MemberOneDayVO result = new MemberOneDayVO();
		//查询出指定时间段内的全部会员
		List<Member> members = memberDao.getmemberListByTime(startTime,endTime);
		if(null != members && members.size() > 0){
			//当日已缴费认证的会员
			List<Member> isRealMb = new ArrayList<Member>();
			for(Member member:members){
				if(1 == member.getCensorStatus()){
					isRealMb.add(member);
				}
			}
			result.setNewRealViewNum(isRealMb.size());
		}else{
			result.setNewRealViewNum(0);
		}
		//返回集合中存放新注册会员信息
		result.setNewRegisterNum(members.size());
		return result;
	}
		
	
	/**
	 * 根据请求查询出会员认证 待审核的
	 * 
	 */
	@Override
	public Integer getTodoIndexValue() {
		return memberDao.countMemberCensorStatus();
	}

	/**
	 * 按日统计
	 */
	@Override
	public List<MemberCountVo> memberOperateCount(Date endTime, int dayParmaeter) {
		// TODO Auto-generated method stub
		List<MemberCountVo> result = new ArrayList<MemberCountVo>();

		String startTimejs = getSpecifiedDayBefore(endTime,dayParmaeter);

		SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance(); 
        c.setTime(endTime);
        int day = c.get(Calendar.DATE);
        c.set(Calendar.DATE, day+1);

        String endTimejs = dft.format(c.getTime());
         
		dayParmaeter +=1;
		for(int i=dayParmaeter;i>0;i--){
			MemberCountVo memberCountVo = new MemberCountVo();
			// 获取查询开始日期
			String startTime = getSpecifiedDayBefore(endTime,i-1);

			  Integer memberRegisterCount =  0;
			  Integer memberAuthenticationCount =  0;
			  Integer memberDepositPaymentCount =  0;
			  Integer memberRefundAmountCount =  0;

			  Integer memberRegisterCountzj =  0;
			  Integer memberAuthenticationCountzj =  0;
			  Integer memberDepositPaymentCountzj = 0;
			  Integer memberRefundAmountCountzj = 0;

			  if(i ==1){
					memberRegisterCount = memberDao.getmemberListByTime1(startTime,endTimejs);
					memberAuthenticationCount = memberDao.getmemberListByTime2(startTime,endTimejs);
				    memberDepositPaymentCount =  memberDao.getmemberDepositAmountByTime(startTime,endTimejs);
				    memberRefundAmountCount =  memberDao.getmemberRefundAmountByTime(startTime,endTimejs);

				    memberRegisterCountzj = memberDao.getmemberListByTime1(startTimejs,endTimejs);
				    memberAuthenticationCountzj = memberDao.getmemberListByTime1(startTimejs,endTimejs);
				    memberDepositPaymentCountzj =  memberDao.getmemberDepositAmountByTime(startTimejs,endTimejs);
					memberRefundAmountCountzj =  memberDao.getmemberRefundAmountByTime(startTimejs,endTimejs);
				}else{

					if(i==dayParmaeter){
						memberRegisterCount = memberDao.getmemberListByTime1(startTime,getSpecifiedDayBefore(endTime,i-2));
						memberAuthenticationCount = memberDao.getmemberListByTime2(startTime,getSpecifiedDayBefore(endTime,i-2));
					    memberDepositPaymentCount =  memberDao.getmemberDepositAmountByTime(startTime,getSpecifiedDayBefore(endTime,i-2));
					    memberRefundAmountCount =  memberDao.getmemberRefundAmountByTime(startTime,getSpecifiedDayBefore(endTime,i-2));

					    memberRegisterCountzj = memberRegisterCount;
					    memberAuthenticationCountzj = memberAuthenticationCount;
					    memberDepositPaymentCountzj = memberDepositPaymentCount;
						memberRefundAmountCountzj = memberRefundAmountCount;
							
					}else{ 
		
						memberRegisterCount = memberDao.getmemberListByTime1(startTime,getSpecifiedDayBefore(endTime,i-2));
						memberAuthenticationCount = memberDao.getmemberListByTime2(startTime,getSpecifiedDayBefore(endTime,i-2));
					    memberDepositPaymentCount =  memberDao.getmemberDepositAmountByTime(startTime,getSpecifiedDayBefore(endTime,i-2));
					    memberRefundAmountCount =  memberDao.getmemberRefundAmountByTime(startTime,getSpecifiedDayBefore(endTime,i-2));

					    memberRegisterCountzj = memberDao.getmemberListByTime1(startTimejs,getSpecifiedDayBefore(endTime,i-2));
					    memberAuthenticationCountzj = memberDao.getmemberListByTime2(startTimejs,getSpecifiedDayBefore(endTime,i-2));
					    memberDepositPaymentCountzj =  memberDao.getmemberDepositAmountByTime(startTimejs,getSpecifiedDayBefore(endTime,i-2));
						memberRefundAmountCountzj =  memberDao.getmemberRefundAmountByTime(startTimejs,getSpecifiedDayBefore(endTime,i-2));
						
					}
					
				}
			   

				if(null != memberRegisterCount && memberRegisterCount > 0){
					//返回集合中存放注册会员信息
					memberCountVo.setMemberRegisterCount(memberRegisterCount); 
				}else{ 
					memberCountVo.setMemberRegisterCount(0); 
				}
				
				if(null != memberAuthenticationCount && memberAuthenticationCount > 0){
					//当日已缴费认证的会员
					memberCountVo.setMemberAuthenticationCount(memberAuthenticationCount); 
				}else{
					memberCountVo.setMemberAuthenticationCount(0); 
				}
					 
				if(null != memberRegisterCountzj && memberRegisterCountzj > 0){
					//返回集合中存放注册会员信息 
					memberCountVo.setMemberRegisterCountzj(memberRegisterCountzj);
				}else{  
					memberCountVo.setMemberRegisterCountzj(0);
				}
				
				if(null != memberAuthenticationCountzj && memberAuthenticationCountzj > 0){
					//当日已缴费认证的会员 
					memberCountVo.setMemberAuthenticationCountzj(memberAuthenticationCountzj);
				}else{ 
					memberCountVo.setMemberAuthenticationCountzj(0); 
				}
					 
			  if(null == memberDepositPaymentCount ||  memberDepositPaymentCount <= 0){
				  memberDepositPaymentCount = 0;
			  }
			  if(null == memberRefundAmountCount ||  memberRefundAmountCount <= 0){
				  memberRefundAmountCount = 0;
			  }

			  if(null == memberDepositPaymentCountzj ||  memberDepositPaymentCountzj <= 0){
				  memberDepositPaymentCountzj = 0;
			  }
			  if(null == memberRefundAmountCountzj ||  memberRefundAmountCountzj <= 0){
				  memberRefundAmountCountzj = 0;
			  }
			 
			  memberCountVo.setMemberDepositPaymentCount(memberDepositPaymentCount);
			  memberCountVo.setMemberRefundAmountCount(memberRefundAmountCount);

			  memberCountVo.setMemberDepositPaymentCountzj(memberDepositPaymentCountzj);
			  memberCountVo.setMemberRefundAmountCountzj(memberRefundAmountCountzj);
			    
			  memberCountVo.setMemberDay(startTime);
			  
			  result.add(memberCountVo);
		}
		
		return result;
	}

	
	
    public static String getSpecifiedDayBefore(Date specifiedDay,int i) {
        Calendar c = Calendar.getInstance(); 
        c.setTime(specifiedDay);
        int day = c.get(Calendar.DATE);
        c.set(Calendar.DATE, day - i);

        String dayBefore = new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
        return dayBefore;
    }

	
    public static String getSpecifiedWeek(Date specifiedDay,int i) {
        Calendar c = Calendar.getInstance(); 
        c.setTime(specifiedDay);
        int day = c.get(Calendar.DATE);
        c.set(Calendar.DATE, day - i*7);

        String dayBefore = new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
        return dayBefore;
    }

    public static String getSpecifiedmonth(Date specifiedDay,int i) {
        Calendar c = Calendar.getInstance(); 
        c.setTime(specifiedDay);
        int month = c.get(Calendar.MONTH);
        c.set(Calendar.MONTH, month - i);

        String dayBefore = new SimpleDateFormat("yyyy-MM").format(c.getTime());
        return dayBefore;
    }

    public static String getSpecifiedYear(Date specifiedDay,int i) {
        Calendar c = Calendar.getInstance(); 
        c.setTime(specifiedDay);
        int year = c.get(Calendar.YEAR);
        c.set(Calendar.YEAR, year - i);

        String dayBefore = new SimpleDateFormat("yyyy").format(c.getTime());
        return dayBefore;
    }
    
    /**
     * 按周查询
     */
	@Override
	public List<MemberCountVo> weekMemberOperateCount(Date startTime1,Date endTime, int weekParmaeter) {
		// TODO Auto-generated method stub
		List<MemberCountVo> result = new ArrayList<MemberCountVo>();

		String startTimejs = getSpecifiedWeek(endTime,weekParmaeter);

		SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance(); 
        c.setTime(endTime);
        int day = c.get(Calendar.DATE);
        c.set(Calendar.DATE, day*7);

        String endTimejs = dft.format(c.getTime());
         
		for(int i=weekParmaeter;i>0;i--){
			MemberCountVo memberCountVo = new MemberCountVo();
			// 获取查询开始日期
			String startTime = getSpecifiedWeek(endTime,i);

			  Integer memberRegisterCount =  0;
			  Integer memberAuthenticationCount =  0;
			  Integer memberDepositPaymentCount =  0;
			  Integer memberRefundAmountCount =  0;

			  Integer memberRegisterCountzj =  0;
			  Integer memberAuthenticationCountzj =  0;
			  Integer memberDepositPaymentCountzj = 0;
			  Integer memberRefundAmountCountzj = 0;

			  if(i ==1){
					memberRegisterCount = memberDao.getmemberListByTime1(startTime,endTimejs);
					memberAuthenticationCount = memberDao.getmemberListByTime2(startTime,endTimejs);
				    memberDepositPaymentCount =  memberDao.getmemberDepositAmountByTime(startTime,endTimejs);
				    memberRefundAmountCount =  memberDao.getmemberRefundAmountByTime(startTime,endTimejs);

				    memberRegisterCountzj = memberDao.getmemberListByTime1(startTimejs,endTimejs);
				    memberAuthenticationCountzj = memberDao.getmemberListByTime1(startTimejs,endTimejs);
				    memberDepositPaymentCountzj =  memberDao.getmemberDepositAmountByTime(startTimejs,endTimejs);
					memberRefundAmountCountzj =  memberDao.getmemberRefundAmountByTime(startTimejs,endTimejs);
				}else{

					if(i==weekParmaeter){
						memberRegisterCount = memberDao.getmemberListByTime1(startTime,getSpecifiedWeek(endTime,i-1));
						memberAuthenticationCount = memberDao.getmemberListByTime2(startTime,getSpecifiedWeek(endTime,i-1));
					    memberDepositPaymentCount =  memberDao.getmemberDepositAmountByTime(startTime,getSpecifiedWeek(endTime,i-1));
					    memberRefundAmountCount =  memberDao.getmemberRefundAmountByTime(startTime,getSpecifiedWeek(endTime,i-1));
		
					    memberRegisterCountzj = memberRegisterCount;
					    memberAuthenticationCountzj = memberAuthenticationCount;
					    memberDepositPaymentCountzj = memberDepositPaymentCount;
						memberRefundAmountCountzj = memberRefundAmountCount;
							
					}else{ 
		
						memberRegisterCount = memberDao.getmemberListByTime1(startTime,getSpecifiedWeek(endTime,i-1));
						memberAuthenticationCount = memberDao.getmemberListByTime2(startTime,getSpecifiedWeek(endTime,i-1));
					    memberDepositPaymentCount =  memberDao.getmemberDepositAmountByTime(startTime,getSpecifiedWeek(endTime,i-1));
					    memberRefundAmountCount =  memberDao.getmemberRefundAmountByTime(startTime,getSpecifiedWeek(endTime,i-1));
		
					    memberRegisterCountzj = memberDao.getmemberListByTime1(startTimejs,getSpecifiedWeek(endTime,i-1));
					    memberAuthenticationCountzj = memberDao.getmemberListByTime1(startTimejs,getSpecifiedWeek(endTime,i-1));
					    memberDepositPaymentCountzj =  memberDao.getmemberDepositAmountByTime(startTimejs,getSpecifiedWeek(endTime,i-1));
						memberRefundAmountCountzj =  memberDao.getmemberRefundAmountByTime(startTimejs,getSpecifiedWeek(endTime,i-1));
					      
					}
					
				}
			 

				if(null != memberRegisterCount && memberRegisterCount > 0){
					//返回集合中存放注册会员信息
					memberCountVo.setMemberRegisterCount(memberRegisterCount); 
				}else{ 
					memberCountVo.setMemberRegisterCount(0); 
				}
				
				if(null != memberAuthenticationCount && memberAuthenticationCount > 0){
					//当日已缴费认证的会员
					memberCountVo.setMemberAuthenticationCount(memberAuthenticationCount); 
				}else{
					memberCountVo.setMemberAuthenticationCount(0); 
				}
					 
				if(null != memberRegisterCountzj && memberRegisterCountzj > 0){
					//返回集合中存放注册会员信息 
					memberCountVo.setMemberRegisterCountzj(memberRegisterCountzj);
				}else{  
					memberCountVo.setMemberRegisterCountzj(0);
				}
				
				if(null != memberAuthenticationCountzj && memberAuthenticationCountzj > 0){
					//当日已缴费认证的会员 
					memberCountVo.setMemberAuthenticationCountzj(memberAuthenticationCountzj);
				}else{ 
					memberCountVo.setMemberAuthenticationCountzj(0); 
				}
					 
			  if(null == memberDepositPaymentCount ||  memberDepositPaymentCount <= 0){
				  memberDepositPaymentCount = 0;
			  }
			  if(null == memberRefundAmountCount ||  memberRefundAmountCount <= 0){
				  memberRefundAmountCount = 0;
			  }

			  if(null == memberDepositPaymentCountzj ||  memberDepositPaymentCountzj <= 0){
				  memberDepositPaymentCountzj = 0;
			  }
			  if(null == memberRefundAmountCountzj ||  memberRefundAmountCountzj <= 0){
				  memberRefundAmountCountzj = 0;
			  }
			 
			  memberCountVo.setMemberDepositPaymentCount(memberDepositPaymentCount);
			  memberCountVo.setMemberRefundAmountCount(memberRefundAmountCount);

			  memberCountVo.setMemberDepositPaymentCountzj(memberDepositPaymentCountzj);
			  memberCountVo.setMemberRefundAmountCountzj(memberRefundAmountCountzj);
			    
			  memberCountVo.setMemberDay(getSpecifiedWeek(endTime,i-1));
			  
			  result.add(memberCountVo);
		}
		
		return result;
	}

	/**
	 * 按月查询
	 */
	@Override
	public List<MemberCountVo> monthMemberOperateCount(Date startTime1,Date endTime, int monthParmaeter) {
		// TODO Auto-generated method stub

		List<MemberCountVo> result = new ArrayList<MemberCountVo>();

		SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM");
		String startTimejs = dft.format(startTime1);
//		String endTimejs = dft.format(endTime);

		Calendar c = Calendar.getInstance(); 
        c.setTime(endTime);
        int month = c.get(Calendar.MONTH);
        c.set(Calendar.MONTH, month+1);

        String endTimejs = dft.format(c.getTime());
        
		for(int i=monthParmaeter;i>0;i--){
			MemberCountVo memberCountVo = new MemberCountVo();
			// 获取查询开始日期
			String startTime = getSpecifiedmonth(endTime,i-1);

			  Integer memberRegisterCount =  0;
			  Integer memberAuthenticationCount =  0;
			  Integer memberDepositPaymentCount =  0;
			  Integer memberRefundAmountCount =  0;

			  Integer memberRegisterCountzj =  0;
			  Integer memberAuthenticationCountzj =  0;
			  Integer memberDepositPaymentCountzj = 0;
			  Integer memberRefundAmountCountzj = 0;
 
			if(i ==1){
				memberRegisterCount = memberDao.getmemberListByTime1(startTime,endTimejs);
				memberAuthenticationCount = memberDao.getmemberListByTime2(startTime,endTimejs);
			    memberDepositPaymentCount =  memberDao.getmemberDepositAmountByTime(startTime,endTimejs);
			    memberRefundAmountCount =  memberDao.getmemberRefundAmountByTime(startTime,endTimejs);

			    memberRegisterCountzj = memberDao.getmemberListByTime1(startTimejs,endTimejs);
			    memberAuthenticationCountzj = memberDao.getmemberListByTime1(startTimejs,endTimejs);
			    memberDepositPaymentCountzj =  memberDao.getmemberDepositAmountByTime(startTimejs,endTimejs);
				memberRefundAmountCountzj =  memberDao.getmemberRefundAmountByTime(startTimejs,endTimejs);
			}else{

				if(i==monthParmaeter){
					memberRegisterCount = memberDao.getmemberListByTime1(startTime,getSpecifiedmonth(endTime,i-2));
					memberAuthenticationCount = memberDao.getmemberListByTime2(startTime,getSpecifiedmonth(endTime,i-2));
				    memberDepositPaymentCount =  memberDao.getmemberDepositAmountByTime(startTime,getSpecifiedmonth(endTime,i-2));
				    memberRefundAmountCount =  memberDao.getmemberRefundAmountByTime(startTime,getSpecifiedmonth(endTime,i-2));

				    memberRegisterCountzj = memberRegisterCount;
				    memberAuthenticationCountzj = memberAuthenticationCount;
				    memberDepositPaymentCountzj = memberDepositPaymentCount;
					memberRefundAmountCountzj = memberRefundAmountCount;
						
				}else{

					memberRegisterCount = memberDao.getmemberListByTime1(startTime,getSpecifiedmonth(endTime,i-2));
					memberAuthenticationCount = memberDao.getmemberListByTime2(startTime,getSpecifiedmonth(endTime,i-2));
				    memberDepositPaymentCount =  memberDao.getmemberDepositAmountByTime(startTime,getSpecifiedmonth(endTime,i-2));
				    memberRefundAmountCount =  memberDao.getmemberRefundAmountByTime(startTime,getSpecifiedmonth(endTime,i-2));

				    memberRegisterCountzj = memberDao.getmemberListByTime1(startTimejs,getSpecifiedmonth(endTime,i-2));
				    memberAuthenticationCountzj = memberDao.getmemberListByTime1(startTimejs,getSpecifiedmonth(endTime,i-2));
				    memberDepositPaymentCountzj =  memberDao.getmemberDepositAmountByTime(startTimejs,getSpecifiedmonth(endTime,i-2));
					memberRefundAmountCountzj =  memberDao.getmemberRefundAmountByTime(startTimejs,getSpecifiedmonth(endTime,i-2));
				      
				}
			      
			}
			
				if(null != memberRegisterCount && memberRegisterCount > 0){
					//返回集合中存放注册会员信息
					memberCountVo.setMemberRegisterCount(memberRegisterCount); 
				}else{ 
					memberCountVo.setMemberRegisterCount(0); 
				}
				
				if(null != memberAuthenticationCount && memberAuthenticationCount > 0){
					//当日已缴费认证的会员
					memberCountVo.setMemberAuthenticationCount(memberAuthenticationCount); 
				}else{
					memberCountVo.setMemberAuthenticationCount(0); 
				}
					 
				if(null != memberRegisterCountzj && memberRegisterCountzj > 0){
					//返回集合中存放注册会员信息 
					memberCountVo.setMemberRegisterCountzj(memberRegisterCountzj);
				}else{  
					memberCountVo.setMemberRegisterCountzj(0);
				}
				
				if(null != memberAuthenticationCountzj && memberAuthenticationCountzj > 0){
					//当日已缴费认证的会员 
					memberCountVo.setMemberAuthenticationCountzj(memberAuthenticationCountzj);
				}else{ 
					memberCountVo.setMemberAuthenticationCountzj(0); 
				}
					 
			  if(null == memberDepositPaymentCount ||  memberDepositPaymentCount <= 0){
				  memberDepositPaymentCount = 0;
			  }
			  if(null == memberRefundAmountCount ||  memberRefundAmountCount <= 0){
				  memberRefundAmountCount = 0;
			  }

			  if(null == memberDepositPaymentCountzj ||  memberDepositPaymentCountzj <= 0){
				  memberDepositPaymentCountzj = 0;
			  }
			  if(null == memberRefundAmountCountzj ||  memberRefundAmountCountzj <= 0){
				  memberRefundAmountCountzj = 0;
			  }
			 
			  memberCountVo.setMemberDepositPaymentCount(memberDepositPaymentCount);
			  memberCountVo.setMemberRefundAmountCount(memberRefundAmountCount);

			  memberCountVo.setMemberDepositPaymentCountzj(memberDepositPaymentCountzj);
			  memberCountVo.setMemberRefundAmountCountzj(memberRefundAmountCountzj);
			    
			  memberCountVo.setMemberDay(startTime);
			  
			  result.add(memberCountVo);
		}
		
		return result;
	}
 
	/**
	 * 按年查询
	 */
	@Override
	public List<MemberCountVo> yearMemberOperateCount(Date startTime1,Date endTime, int yearParmaeter) {
		// TODO Auto-generated method stub

		List<MemberCountVo> result = new ArrayList<MemberCountVo>();

		SimpleDateFormat dft = new SimpleDateFormat("yyyy");
		String startTimejs = dft.format(startTime1);
//		String endTimejs = dft.format(endTime);
		
		Calendar c = Calendar.getInstance(); 
        c.setTime(endTime);
        int year = c.get(Calendar.YEAR);
        c.set(Calendar.YEAR, year+1);

        String endTimejs = dft.format(c.getTime());
		
		for(int i=yearParmaeter;i>0;i--){
			MemberCountVo memberCountVo = new MemberCountVo();
			// 获取查询开始日期
			String startTime = getSpecifiedYear(endTime,i-1);

			  Integer memberRegisterCount =  0;
			  Integer memberAuthenticationCount =  0;
			  Integer memberDepositPaymentCount =  0;
			  Integer memberRefundAmountCount =  0;

			  Integer memberRegisterCountzj =  0;
			  Integer memberAuthenticationCountzj =  0;
			  Integer memberDepositPaymentCountzj = 0;
			  Integer memberRefundAmountCountzj = 0;
 
			if(i ==1){
				memberRegisterCount = memberDao.getmemberListByTime1(startTime,endTimejs);
				memberAuthenticationCount = memberDao.getmemberListByTime2(startTime,endTimejs);
			    memberDepositPaymentCount =  memberDao.getmemberDepositAmountByTime(startTime,endTimejs);
			    memberRefundAmountCount =  memberDao.getmemberRefundAmountByTime(startTime,endTimejs);

			    memberRegisterCountzj = memberDao.getmemberListByTime1(startTimejs,endTimejs);
			    memberAuthenticationCountzj = memberDao.getmemberListByTime1(startTimejs,endTimejs);
			    memberDepositPaymentCountzj =  memberDao.getmemberDepositAmountByTime(startTimejs,endTimejs);
				memberRefundAmountCountzj =  memberDao.getmemberRefundAmountByTime(startTimejs,endTimejs);
			}else{

				if(i==yearParmaeter){
					memberRegisterCount = memberDao.getmemberListByTime1(startTime,getSpecifiedYear(endTime,i-2));
					memberAuthenticationCount = memberDao.getmemberListByTime2(startTime,getSpecifiedYear(endTime,i-2));
				    memberDepositPaymentCount =  memberDao.getmemberDepositAmountByTime(startTime,getSpecifiedYear(endTime,i-2));
				    memberRefundAmountCount =  memberDao.getmemberRefundAmountByTime(startTime,getSpecifiedYear(endTime,i-2));

				    memberRegisterCountzj = memberRegisterCount;
				    memberAuthenticationCountzj = memberAuthenticationCount;
				    memberDepositPaymentCountzj = memberDepositPaymentCount;
					memberRefundAmountCountzj = memberRefundAmountCount;
						
				}else{

					memberRegisterCount = memberDao.getmemberListByTime1(startTime,getSpecifiedYear(endTime,i-2));
					memberAuthenticationCount = memberDao.getmemberListByTime2(startTime,getSpecifiedYear(endTime,i-2));
				    memberDepositPaymentCount =  memberDao.getmemberDepositAmountByTime(startTime,getSpecifiedYear(endTime,i-2));
				    memberRefundAmountCount =  memberDao.getmemberRefundAmountByTime(startTime,getSpecifiedYear(endTime,i-2));

				    memberRegisterCountzj = memberDao.getmemberListByTime1(startTimejs,getSpecifiedYear(endTime,i-2));
				    memberAuthenticationCountzj = memberDao.getmemberListByTime1(startTimejs,getSpecifiedYear(endTime,i-2));
				    memberDepositPaymentCountzj =  memberDao.getmemberDepositAmountByTime(startTimejs,getSpecifiedYear(endTime,i-2));
					memberRefundAmountCountzj =  memberDao.getmemberRefundAmountByTime(startTimejs,getSpecifiedYear(endTime,i-2));
				      
				}
			      
			}
			
				if(null != memberRegisterCount && memberRegisterCount > 0){
					//返回集合中存放注册会员信息
					memberCountVo.setMemberRegisterCount(memberRegisterCount); 
				}else{ 
					memberCountVo.setMemberRegisterCount(0); 
				}
				
				if(null != memberAuthenticationCount && memberAuthenticationCount > 0){
					//当日已缴费认证的会员
					memberCountVo.setMemberAuthenticationCount(memberAuthenticationCount); 
				}else{
					memberCountVo.setMemberAuthenticationCount(0); 
				}
					 
				if(null != memberRegisterCountzj && memberRegisterCountzj > 0){
					//返回集合中存放注册会员信息 
					memberCountVo.setMemberRegisterCountzj(memberRegisterCountzj);
				}else{  
					memberCountVo.setMemberRegisterCountzj(0);
				}
				
				if(null != memberAuthenticationCountzj && memberAuthenticationCountzj > 0){
					//当日已缴费认证的会员 
					memberCountVo.setMemberAuthenticationCountzj(memberAuthenticationCountzj);
				}else{ 
					memberCountVo.setMemberAuthenticationCountzj(0); 
				}
					 
			  if(null == memberDepositPaymentCount ||  memberDepositPaymentCount <= 0){
				  memberDepositPaymentCount = 0;
			  }
			  if(null == memberRefundAmountCount ||  memberRefundAmountCount <= 0){
				  memberRefundAmountCount = 0;
			  }

			  if(null == memberDepositPaymentCountzj ||  memberDepositPaymentCountzj <= 0){
				  memberDepositPaymentCountzj = 0;
			  }
			  if(null == memberRefundAmountCountzj ||  memberRefundAmountCountzj <= 0){
				  memberRefundAmountCountzj = 0;
			  }
			 
			  memberCountVo.setMemberDepositPaymentCount(memberDepositPaymentCount);
			  memberCountVo.setMemberRefundAmountCount(memberRefundAmountCount);

			  memberCountVo.setMemberDepositPaymentCountzj(memberDepositPaymentCountzj);
			  memberCountVo.setMemberRefundAmountCountzj(memberRefundAmountCountzj);
			    
			  memberCountVo.setMemberDay(startTime);
			  
			  result.add(memberCountVo);
		}
		
		return result;
	}

	@Override
	public PageFinder<Member> getMemberAccountAmount(Query q) {
		PageFinder<Member> page = new PageFinder<Member>();
		List<Member> list = null;
		long rowCount = 0L;
		try {
			list = memberDao.getMemberAccountAmount(q);
			for(Member member:list){
				if(member.getPackOrderAmount()==null){
					member.setPackOrderAmount(0.0);
				}
				if(member.getMemberName()==null){
					member.setMemberName("");
				}
				
				//计算订单完成量，和消费金额
				Order order = new Order();
				order.setMemberNo(member.getMemberNo());//会员编号
				order.setPayStatus(1);//已支付
				List<Order> orderList = orderDao.queryAll(new Query(order));
				
				Integer orderFinishTotal = 0;
				Double orderAccountTotal = 0d;
				if(orderList != null && !orderList.isEmpty()){
					for (Order temp : orderList) {
						
						if(temp.getPayableAmount() != null){
							BigDecimal b1 = new BigDecimal(orderAccountTotal);
					        BigDecimal b2 = new BigDecimal(temp.getPayableAmount());
					        orderAccountTotal =  b1.add(b2).doubleValue();
						}
					}
					orderFinishTotal = orderList.size();
				}
				member.setOrderFinishTotal(orderFinishTotal);
				member.setOrderAccountTotal(ECNumberUtils.roundDoubleWithScale(orderAccountTotal,2));
				
				//计算购买套餐充值的总额
				PricingPackOrder pricingPackOrder = new PricingPackOrder();
				pricingPackOrder.setMemberNo(member.getMemberNo());//会员编号
				pricingPackOrder.setPackageType(2);//销售套餐
				pricingPackOrder.setPayStatus(1);//已支付
				List<PricingPackOrder> pricingPackOrderList = pricingPackOrderDao.queryAllForPackageType(new Query(pricingPackOrder));
				
				Double rechargeTotal = 0d;
				if(pricingPackOrderList != null && !pricingPackOrderList.isEmpty()){
					for (PricingPackOrder temp : pricingPackOrderList) {
						if(temp.getPackAmount() != null){
							BigDecimal b1 = new BigDecimal(rechargeTotal);
					        BigDecimal b2 = new BigDecimal(temp.getPackAmount());
					        rechargeTotal =  b1.add(b2).doubleValue();
						}
					}
				}
				
				member.setRechargeTotal(ECNumberUtils.roundDoubleWithScale(rechargeTotal,2));
			}
			//调用dao统计满足条件的记录总数
			rowCount = memberDao.countMemberAccountAmount(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		
		//如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<Member>(0) : list;
	
		//将分页数据和记录总数设置到分页结果对象中
		page.setData(list);
		page.setRowCount(rowCount);
		
		return page; 
	}

	@Override
	public List<Member> getMemberListCenStatus() {
		// TODO Auto-generated method stub
		return memberDao.getMemberListCenStatus();
	}

	

	@Override
	public Integer countMemberByCensorStatus(Integer censorStatus){
		return memberDao.countMemberByCensorStatus(censorStatus);
	}

	@Override
	public Map<String, Object> getMemberDay10CountMap() {
		String dateArray[] = new String[10];
		long registerArray[] = new long[]{0,0,0,0,0,0,0,0,0,0};
		long cencorArray[] = new long[]{0,0,0,0,0,0,0,0,0,0};
		long depositArray[] = new long[]{0,0,0,0,0,0,0,0,0,0};
		Date date = new Date();
		for(int day = -9; day <= 0; day++){
			String d = ECDateUtils.formatDate(ECDateUtils.getDateAfter(date, day), "MM-dd");
			dateArray[day+9] = d;
		}
		List<Map<String, Object>> data = memberDao.getRegisterDay10CountMap();
		if(data != null && !data.isEmpty()){
			for(Map<String, Object> d : data){
				for(int i = 0; i < dateArray.length; i++){
					if(dateArray[i].equals((String)d.get("date"))){
						registerArray[i] = (Long)d.get("num");
					}
				}
			}
		}
		data = memberDao.getCencorDay10CountMap();
		if(data != null && !data.isEmpty()){
			for(Map<String, Object> d : data){
				for(int i = 0; i < dateArray.length; i++){
					if(dateArray[i].equals((String)d.get("date"))){
						cencorArray[i] = (Long)d.get("num");
					}
				}
			}
		}
		data = depositOrderDao.getDepositMemberDay10CountMap();
		if(data != null && !data.isEmpty()){
			for(Map<String, Object> d : data){
				for(int i = 0; i < dateArray.length; i++){
					if(dateArray[i].equals((String)d.get("date"))){
						depositArray[i] = (Long)d.get("num");
					}
				}
			}
		}
		Map<String, Object> map = new HashMap<>();
		map.put("dateArray", dateArray);
		map.put("registerArray", registerArray);
		map.put("cencorArray", cencorArray);
		map.put("depositArray", depositArray);
		return map;
	}

	@Override
	public Long count(Query q) {
		return memberDao.count(q);
	}

	@Override
	public ResultInfo<String> uploadLicenseAndUrl(String memberNo, String idCard, String memberName, String pathUrl1,
			String pathUrl2,String imgPaths,String licenseId,String type,String paperName,String paperUrl,String enddate) {
		ResultInfo<String> result = new ResultInfo<>();
		result.setData("");
		//判断身份证号码是否为空或者是否是18位
		if (idCard==null||idCard.trim().length()!=18) {
			result.setCode(MemberConstant.fail_code);
			result.setMsg(MemberConstant.fail_idCard_Check);
			return result;
		}
		if (memberName==null||memberName.trim().length()==0) {
			result.setCode(MemberConstant.fail_code);
			result.setMsg(MemberConstant.fail_memberName_Check);
			return result;
		}
		
		Member member = memberDao.get(memberNo);
		if (member!=null) {
			if(type != null && !"".equals(type)){
				if("1".equals(type)){
					if(enddate != null && !"".equals(enddate)){
						 SimpleDateFormat format =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
						    String time=enddate+" 23:59:59";  
						    Date date=null;
							try {
								date = format.parse(time);
							} catch (ParseException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						member.setExpirationDate(date);
						member.setCensorStatus(1);
					}else{
						member.setCensorStatus(2);
					}
					
				}else{
					if(member.getCensorStatus()==3 || member.getCensorStatus()==0 ){
						member.setCensorStatus(2);
					}
				}
			}else{
				if(member.getCensorStatus()==3 || member.getCensorStatus()==0 ){
					member.setCensorStatus(2);
				}
			}
			
			
			if(pathUrl1!=null&&!pathUrl1.equals("")){
				String pathUrl11=pathUrl1.substring(imgPaths.length()+1);
				member.setDriverLicensePhotoUrl1(pathUrl11);
			}
			if(pathUrl2!=null&&!pathUrl2.equals("")){
				String pathUrl21=pathUrl2.substring(imgPaths.length()+1);
				member.setIdCardPhotoUrl(pathUrl21);;
			}
			if(paperUrl!=null&&!paperUrl.equals("")){
				String paperUrl1=paperUrl.substring(imgPaths.length()+1);
				member.setPaperUrl(paperUrl1);;
			}
			member.setPaperName(paperName);
			member.setLicenseId(licenseId);
			member.setIdCard(idCard);
			member.setMemberName(memberName);
			memberDao.update(member);
			result.setCode(MemberConstant.success_code);
			if(member.getCensorStatus()!= null && member.getCensorStatus()==1){
				result.setMsg("审核已通过！");
			}else{
				result.setMsg("提交成功等待审核！");
			}
		}else {
			result.setCode(MemberConstant.fail_code);
			result.setMsg("认证失败,重新认证!");
		}
		return result;
		
	}


	@Override
	public void updateMemberRealAmount(String memberNo, double payableAmount) {
		if(memberNo == null){
			return;
		}
		Member member = memberDao.get(memberNo);
		if(member == null){
			return;
		}
		hanldeMemberRefereeEvent(member);
		double realAmount = member.getRealAmount() == null? 0 : member.getRealAmount();
		realAmount += payableAmount;
		member = new Member();
		member.setMemberNo(memberNo);
		member.setRealAmount(realAmount);
		List<MemberLevel> list = memberLevelDao.queryAll(new Query());
		int tempAmount = 0;
		for(MemberLevel level : list){
			if(level.getUpgradePoint() == null){
				continue;
			}
			if(realAmount >= level.getUpgradePoint() && level.getUpgradePoint() >= tempAmount){
				member.setMemberLevelId(level.getMemberLevelId());
				tempAmount = level.getUpgradePoint();
			}
		}
		memberDao.update(member);
	}

	@Override
	public Member getIdCardMember(String idCard) {
		return memberDao.getIdCardMember(idCard);
	}

	/**
	 * 处理推荐人事件
	 * 
	 * @param member
	 */
	public void hanldeMemberRefereeEvent(Member member) {
		if(member == null || (member.getRealAmount() != null && member.getRealAmount() > 0) || member.getRefereeId() == null || member.getRefereeId().trim().length() == 0){
			return;
		}
		String url = trigger+"marketingEvent/eventHandler?eventNo=3&memberNo="+member.getRefereeId();
		try {
			HttpURLRequestUtil.doMsgGet(url);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}
 
}
