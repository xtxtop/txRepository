package cn.com.shopec.core.marketing.service.impl;

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
import cn.com.shopec.common.apppush.AppPushUtil;
import cn.com.shopec.common.constants.Constant;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.marketing.dao.MessagePushDao;
import cn.com.shopec.core.marketing.model.MessagePush;
import cn.com.shopec.core.marketing.service.MessagePushService;
import cn.com.shopec.core.member.dao.MemberDao;
import cn.com.shopec.core.member.model.Member;

/**
 * 广告表 服务实现类
 */
@Service
public class MessagePushServiceImpl implements MessagePushService {

	private static final Log log = LogFactory.getLog(MessagePushServiceImpl.class);
	
	@Resource
	private MessagePushDao messagePushDao;
	@Resource
	private MemberDao memberDao;

	/**
	 * 根据查询条件，查询并返回MessagePush的列表
	 * @param q 查询条件
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<MessagePush> getMessagePushList(Query q) {
		List<MessagePush> list = null;
		try {
			//已删除的不查出
			MessagePush messagePush = (MessagePush)q.getQ();
			if (messagePush != null) {
				messagePush.setIsDeleted(Constant.DEL_STATE_NO);
			}
					
			//直接调用Dao方法进行查询
			list = messagePushDao.queryAll(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		//如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<MessagePush>(0) : list;
		return list; 
	}
	
	/**
	 * 根据查询条件，分页查询并返回MessagePush的分页结果
	 * @param q 查询条件
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public PageFinder<MessagePush> getMessagePushPagedList(Query q) {
		PageFinder<MessagePush> page = new PageFinder<MessagePush>();
		
		List<MessagePush> list = null;
		long rowCount = 0L;
		
		try {
			//已删除的不查出
			MessagePush messagePush = (MessagePush)q.getQ();
			if (messagePush != null) {
				messagePush.setIsDeleted(Constant.DEL_STATE_NO);
			}
					
			//调用dao查询满足条件的分页数据
			list = messagePushDao.pageList(q);
			//调用dao统计满足条件的记录总数
			rowCount = messagePushDao.count(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		
		//如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<MessagePush>(0) : list;
	
		//将分页数据和记录总数设置到分页结果对象中
		page.setData(list);
		page.setRowCount(rowCount);
		
		return page;
	}	
	
	/**
	 * 根据主键，查询并返回一个MessagePush对象
	 * @param id 主键id
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public MessagePush getMessagePush(String id) {
		MessagePush obj = null;
		if (id == null || id.length() == 0) { //传入的主键无效时直接返回null
			log.info(Constant.ERR_MSG_INVALID_ARG + " id = " + id);
			return obj;
		}
		try {
			//调用dao，根据主键查询
			obj = messagePushDao.get(id); 
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		
		return obj;
	}

	/**
	 * 根据主键数组，查询并返回一组MessagePush对象
	 * @param ids 主键数组，数组中的主键值应当无重复值，如有重复值时，则返回的列表长度可能小于传入的数组长度
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<MessagePush> getMessagePushByIds(String[] ids) {
		List<MessagePush> list = null;
		if (ids == null || ids.length == 0) {
			log.info(Constant.ERR_MSG_INVALID_ARG + " ids is null or empty array.");
		} else {
			try {
				//调用dao，根据主键数组查询
				list = messagePushDao.getByIds(ids);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
		}
		
		//如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<MessagePush>(0) : list;

		return list;
	}	
	
	/**
	 * 根据主键，删除特定的MessagePush记录
	 * @param id 主键id
	 * @param operator 操作人对象
	 * @return
	 */
	@Transactional
	public ResultInfo<MessagePush> delMessagePush(String id, Operator operator) {
		ResultInfo<MessagePush> resultInfo = new ResultInfo<MessagePush>();
		if (id == null || id.length() == 0) { //传入的主键无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG );
			log.info(Constant.ERR_MSG_INVALID_ARG + " id = " + id);
			return resultInfo;
		}
		try {
			//逻辑删除，实际上执行update语句，以下设置待更新记录的主键、删除标识、更新时间、操作人信息等
			MessagePush messagePush = new MessagePush();
			messagePush.setId(id);
			messagePush.setIsDeleted(Constant.DEL_STATE_YES);
			messagePush.setUpdateTime(new Date());
			if (operator != null) { //最近操作人
				messagePush.setOperatorType(operator.getOperatorType());
				messagePush.setOperatorId(operator.getOperatorId());
			}
			
			//调用Dao执行更新操作，并判断更新语句执行结果
			int count = messagePushDao.update(messagePush);			
			if (count > 0) {
				resultInfo.setCode(Constant.SECCUESS);
			} else {
				resultInfo.setCode(Constant.FAIL);
			}
			resultInfo.setData(messagePush);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_EXCEPTION_CATCHED);
		}
		return resultInfo;
	}
		
	/**
	 * 新增一条MessagePush记录，执行成功后传入对象及返回对象的主键属性值不为null
	 * @param messagePush 新增的MessagePush数据（如果无特殊需求，新增对象的主键值请保留为null）
	 * @param operator 操作人对象
	 * @return
	 */
	@Transactional
	public ResultInfo<MessagePush> addMessagePush(MessagePush messagePush, Operator operator) {
		ResultInfo<MessagePush> resultInfo = new ResultInfo<MessagePush>();
		
		if (messagePush == null) { //传入参数无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG );
			log.info(Constant.ERR_MSG_INVALID_ARG + " messagePush = " + messagePush);
		} else {
			try {
				//如果传入参数的主键为null，则调用生成主键的方法获取新的主键
				if (messagePush.getId() == null) {
					messagePush.setId(this.generatePK());
				}
				//如果传入的操作人不为null，则设置新增记录的操作人类型和操作人id
				if (operator != null) {
					messagePush.setOperatorType(operator.getOperatorType());
					messagePush.setOperatorId(operator.getOperatorId());
				}
				
				//设置创建时间和更新时间为当前时间
				Date now = new Date();
				messagePush.setCreateTime(now);
				messagePush.setUpdateTime(now);
				
				//填充默认值
				this.fillDefaultValues(messagePush);
				
				//调用Dao执行插入操作
				messagePushDao.add(messagePush);
				resultInfo.setCode(Constant.SECCUESS);
				resultInfo.setData(messagePush);
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
	 * 根据主键，更新一条MessagePush记录
	 * @param messagePush 更新的MessagePush数据，且其主键不能为空
	 * @param operator 操作人对象
	 * @return
	 */
	@Transactional
	public ResultInfo<MessagePush> updateMessagePush(MessagePush messagePush, Operator operator) {
		ResultInfo<MessagePush> resultInfo = new ResultInfo<MessagePush>();
		
		if (messagePush == null || messagePush.getId() == null) { //传入参数无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG );
			log.info(Constant.ERR_MSG_INVALID_ARG + " messagePush = " + messagePush);
		} else {
			try {
				//如果传入的操作人不为null，则设置更新记录的操作人类型和操作人id
				if (operator != null) {
					messagePush.setOperatorType(operator.getOperatorType());
					messagePush.setOperatorId(operator.getOperatorId());
				}
				
				//设置更新时间为当前时间
				messagePush.setUpdateTime(new Date());
				
				//调用Dao执行更新操作，并判断更新语句执行结果
				int count = messagePushDao.update(messagePush);			
				if (count > 0) {
					resultInfo.setCode(Constant.SECCUESS);
				} else {
					resultInfo.setCode(Constant.FAIL);
				}
				resultInfo.setData(messagePush);
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
		return  String.valueOf(new Date().getTime() * 1000000 + new Random().nextInt(1000000));
	}
	
	/**
	 * 为MessagePush对象设置默认值
	 * @param obj
	 */
	public void fillDefaultValues(MessagePush obj) {
		if (obj != null) {
		    if (obj.getPushStatus() == null) {
		    	obj.setPushStatus(0);
		    }
		    if (obj.getIsDeleted() == null) {
		    	obj.setIsDeleted(0);
		    }
		}
	}

	@Override
	@Transactional
	public ResultInfo<MessagePush> pushMessage(MessagePush messagePush, Operator operator) {
		ResultInfo<MessagePush> result = new ResultInfo<MessagePush>();
		result.setCode(Constant.FAIL);
		if(messagePush == null){
			result.setMsg("推送的消息不存在");
		}else{
			int pushPattern = messagePush.getPushPattern().intValue();
			if(pushPattern == 1){//多个用户推送
				String[] memberNoArray =  messagePush.getMemberNo().split(",");
				List<Member> memberList = memberDao.getByIds(memberNoArray);
				if(memberList != null && !memberList.isEmpty()){
					
					List<String> clientIds = new ArrayList<String>();
					for (int i = 0; i < memberList.size(); i++) {
						String clientId = memberList.get(i).getClientId();
						if(clientId != null && !clientId.equals("")){
							clientIds.add(clientId);
						}
					}	
					if(!clientIds.isEmpty()){
						AppPushUtil.pushBatchMessage(clientIds, messagePush.getTitle(), messagePush.getContent());
						result.setCode(Constant.SECCUESS);
					}else{
						result.setMsg("未能获得任何会员设备号");
					}
				}else{
					result.setMsg("会员信息不存在");
				}
			}else if(pushPattern == 2){//全部推送
				AppPushUtil.pushAppMessage(messagePush.getTitle(), messagePush.getContent());
				result.setCode(Constant.SECCUESS);
			}
			
		}
		
		if(Constant.SECCUESS.equals(result.getCode())){
			
			//围护
			MessagePush messagvPushTemp = new MessagePush();
			messagvPushTemp.setId(messagePush.getId());
			messagvPushTemp.setPushStatus(1);//已推送
			messagvPushTemp.setPushTime(new Date());
			this.updateMessagePush(messagvPushTemp, operator);
		}
		return result;
	}
}
