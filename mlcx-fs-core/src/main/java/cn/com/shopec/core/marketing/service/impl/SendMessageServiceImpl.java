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
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.marketing.dao.SendMessageDao;
import cn.com.shopec.core.marketing.model.SendMessage;
import cn.com.shopec.core.marketing.service.SendMessageService;
import cn.com.shopec.core.order.dao.OrderDao;
import cn.com.shopec.core.order.model.Order;
import cn.com.shopec.core.system.common.PrimarykeyConstant;

/**
 * 推送消息表 服务实现类
 */
@Service
public class SendMessageServiceImpl implements SendMessageService {

	private static final Log log = LogFactory.getLog(SendMessageServiceImpl.class);
	
	@Resource
	private SendMessageDao sendMessageDao;
	@Resource
	private OrderDao orderDao;
	
	

	/**
	 * 根据查询条件，查询并返回SendMessage的列表
	 * @param q 查询条件
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<SendMessage> getSendMessageList(Query q) {
		List<SendMessage> list = null;
		try {
			//直接调用Dao方法进行查询
			list = sendMessageDao.queryAll(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		//如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<SendMessage>(0) : list;
		return list; 
	}
	
	/**
	 * 根据查询条件，分页查询并返回SendMessage的分页结果
	 * @param q 查询条件
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public PageFinder<SendMessage> getSendMessagePagedList(Query q) {
		PageFinder<SendMessage> page = new PageFinder<SendMessage>();
		
		List<SendMessage> list = null;
		long rowCount = 0L;
		
		try {
			//调用dao查询满足条件的分页数据
			list = sendMessageDao.pageList(q);
			//调用dao统计满足条件的记录总数
			rowCount = sendMessageDao.count(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		
		//如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<SendMessage>(0) : list;
	
		//将分页数据和记录总数设置到分页结果对象中
		page.setData(list);
		page.setRowCount(rowCount);
		
		return page;
	}	
	
	/**
	 * 根据主键，查询并返回一个SendMessage对象
	 * @param id 主键id
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public SendMessage getSendMessage(String id) {
		SendMessage obj = null;
		if (id == null || id.length() == 0) { //传入的主键无效时直接返回null
			log.info(Constant.ERR_MSG_INVALID_ARG + " id = " + id);
			return obj;
		}
		try {
			//调用dao，根据主键查询
			obj = sendMessageDao.get(id); 
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		
		return obj;
	}

	/**
	 * 根据主键数组，查询并返回一组SendMessage对象
	 * @param ids 主键数组，数组中的主键值应当无重复值，如有重复值时，则返回的列表长度可能小于传入的数组长度
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<SendMessage> getSendMessageByIds(String[] ids) {
		List<SendMessage> list = null;
		if (ids == null || ids.length == 0) {
			log.info(Constant.ERR_MSG_INVALID_ARG + " ids is null or empty array.");
		} else {
			try {
				//调用dao，根据主键数组查询
				list = sendMessageDao.getByIds(ids);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
		}
		
		//如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<SendMessage>(0) : list;

		return list;
	}	
	
	/**
	 * 根据主键，删除特定的SendMessage记录
	 * @param id 主键id
	 * @param operator 操作人对象
	 * @return
	 */
	@Transactional
	public ResultInfo<SendMessage> delSendMessage(String id, Operator operator) {
		ResultInfo<SendMessage> resultInfo = new ResultInfo<SendMessage>();
		if (id == null || id.length() == 0) { //传入的主键无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG );
			log.info(Constant.ERR_MSG_INVALID_ARG + " id = " + id);
			return resultInfo;
		}
		try {
		    //调用Dao执行删除，并判断删除语句执行结果
			int count = sendMessageDao.delete(id);
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
	 * 新增一条SendMessage记录，执行成功后传入对象及返回对象的主键属性值不为null
	 * @param sendMessage 新增的SendMessage数据（如果无特殊需求，新增对象的主键值请保留为null）
	 * @param operator 操作人对象
	 * @return
	 */
	@Transactional
	public ResultInfo<SendMessage> addSendMessage(SendMessage sendMessage, Operator operator) {
		ResultInfo<SendMessage> resultInfo = new ResultInfo<SendMessage>();
		
		if (sendMessage == null) { //传入参数无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG );
			log.info(Constant.ERR_MSG_INVALID_ARG + " sendMessage = " + sendMessage);
		} else {
			try {
				//如果传入参数的主键为null，则调用生成主键的方法获取新的主键
				if (sendMessage.getMessageId() == null) {
					sendMessage.setMessageId(this.generatePK());
				}
				//如果传入的操作人不为null，则设置新增记录的操作人类型和操作人id
				if (operator != null) {
					sendMessage.setOperatorType(operator.getOperatorType());
					sendMessage.setOperatorId(operator.getOperatorId());
				}
				
				//设置创建时间和更新时间为当前时间
				Date now = new Date();
				sendMessage.setCreateTime(now);
				sendMessage.setUpdateTime(now);
				
				//填充默认值
				this.fillDefaultValues(sendMessage);
				
				//调用Dao执行插入操作
				sendMessageDao.add(sendMessage);
				resultInfo.setCode(Constant.SECCUESS);
				resultInfo.setData(sendMessage);
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
	 * 根据主键，更新一条SendMessage记录
	 * @param sendMessage 更新的SendMessage数据，且其主键不能为空
	 * @param operator 操作人对象
	 * @return
	 */
	@Transactional
	public ResultInfo<SendMessage> updateSendMessage(SendMessage sendMessage, Operator operator) {
		ResultInfo<SendMessage> resultInfo = new ResultInfo<SendMessage>();
		
		if (sendMessage == null || sendMessage.getMessageId() == null) { //传入参数无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG );
			log.info(Constant.ERR_MSG_INVALID_ARG + " sendMessage = " + sendMessage);
		} else {
			try {
				//如果传入的操作人不为null，则设置更新记录的操作人类型和操作人id
				if (operator != null) {
					sendMessage.setOperatorType(operator.getOperatorType());
					sendMessage.setOperatorId(operator.getOperatorId());
				}
				
				//设置更新时间为当前时间
				sendMessage.setUpdateTime(new Date());
				
				//调用Dao执行更新操作，并判断更新语句执行结果
				int count = sendMessageDao.update(sendMessage);			
				if (count > 0) {
					resultInfo.setCode(Constant.SECCUESS);
				} else {
					resultInfo.setCode(Constant.FAIL);
				}
				resultInfo.setData(sendMessage);
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
		return String.valueOf(new Date().getTime() * 1000000 + new Random().nextInt(1000000));
	}
	
	/**
	 * 为SendMessage对象设置默认值
	 * @param obj
	 */
	public void fillDefaultValues(SendMessage obj) {
		if (obj != null) {
		    if (obj.getIsRead() == null) {
		    	obj.setIsRead(0);
		    }
		}
	}
	/**
	 *得到所有已预约的订单,并记录到消息表中
	 * @throws Exception 
	 */
	@Override
	public ResultInfo<SendMessage> getorderStatusOne() throws Exception {
		ResultInfo<SendMessage> resultInfo=null;
		Query q=new Query();
		Order order=new Order();
		order.setOrderStatus(1);//已预约
		order.setCreateTimeStart(ECDateUtils.getCurrentDateStartTime());//今天零刻的时间
		q.setQ(order);
		List<Order> list=orderDao.queryAll(q);
		if(list!=null&&list.size()>0){
			for(Order order2:list){
				SendMessage sendMessage=new SendMessage();
				sendMessage.setMemberNo(order2.getMemberNo());
				sendMessage.setMessageType(1);
				q.setQ(sendMessage);
				List<SendMessage> list2=getSendMessageList(q);
				if(list2!=null&&list2.size()>0){
					log.info("============此订单已记录,订单号:"+order2.getOrderNo()+"===================");
				}else{
					sendMessage.setMessageContent("您已预约车牌号为"+order2.getCarPlateNo()+"的车辆，请尽快取车。");
					resultInfo=addSendMessage(sendMessage, null);
					if(resultInfo.getCode().equals("1")){
						log.info("============记录已预约未取车的订单消息记录成功,订单号:"+order2.getOrderNo()+"===================");
					}
				}
			}
		}else{
			log.info(ECDateUtils.getCurrentDate()+"============未查到未预约的订单===================");
		}
		
		return resultInfo;
	}
	/**
	 *得到所有已结束，未支付的订单,并记录到消息表中
	 */
	@Override
	public ResultInfo<SendMessage> getorderStatusThree() throws Exception {
		ResultInfo<SendMessage> resultInfo=new ResultInfo<SendMessage>();
		Query q=new Query();
		Order order=new Order();
		order.setOrderStatus(3);//已结束
		order.setPayStatus(0);//未支付
		order.setCreateTimeStart(ECDateUtils.getCurrentDateStartTime());//今天零刻的时间
		q.setQ(order);
		List<Order> list=orderDao.queryAll(q);
		if(list!=null&&list.size()>0){
			for(Order order2:list){
				SendMessage sendMessage=new SendMessage();
				sendMessage.setMemberNo(order2.getMemberNo());
				sendMessage.setMessageType(1);
				q.setQ(sendMessage);
				List<SendMessage> list2=getSendMessageList(q);
				if(list2!=null&&list2.size()>0){
					for(SendMessage message:list2){
						message.setMessageType(2);
						message.setMessageContent("您有订单号为"+order2.getOrderNo()+"的订单未支付，为不影响您的使用，请尽快支付。");
						resultInfo=updateSendMessage(message, null);
						if(resultInfo.getCode().equals("1")){
							log.info("============记录已结束，未支付的订单消息更新成功,订单号:"+order2.getOrderNo()+"===================");
						}
					}
				}else{
					log.info("============记录已结束，未支付的订单消息更新失败,订单号:"+order2.getOrderNo()+"===================会员编号："+order2.getMemberNo());
				}
			}
		}else{
			log.info(ECDateUtils.getCurrentDate()+"============未查到已结束，未支付的订单===================");
		}
		
		return resultInfo;
	}
}
