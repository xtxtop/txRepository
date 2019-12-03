package cn.com.shopec.core.marketing.service;

import java.util.List;

import cn.com.shopec.common.Operator;
import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.core.common.BaseService;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.marketing.model.MessagePush;

/**
 * 消息推送表 服务接口类
 */
public interface MessagePushService extends BaseService {

	/**
	 * 根据查询条件，查询并返回MessagePush的列表
	 * @param q 查询条件
	 * @return
	 */
	public List<MessagePush> getMessagePushList(Query q);
	
	/**
	 * 根据查询条件，分页查询并返回MessagePush的分页结果
	 * @param q 查询条件
	 * @return
	 */
	public PageFinder<MessagePush> getMessagePushPagedList(Query q);
	
	/**
	 * 根据主键，查询并返回一个MessagePush对象
	 * @param id 主键id
	 * @return
	 */
	public MessagePush getMessagePush(String id);

	/**
	 * 根据主键数组，查询并返回一组MessagePush对象
	 * @param ids 主键数组，数组中的主键值应当无重复值，如有重复值时，则返回的列表长度可能小于传入的数组长度
	 * @return
	 */
	public List<MessagePush> getMessagePushByIds(String[] ids);
	
	/**
	 * 根据主键，删除特定的MessagePush记录
	 * @param id 主键id
	 * @param operator 操作人对象
	 * @return
	 */
	public ResultInfo<MessagePush> delMessagePush(String id, Operator operator);
	
	/**
	 * 新增一条MessagePush记录，执行成功后传入对象及返回对象的主键属性值不为null
	 * @param messagePush 新增的MessagePush数据（如果无特殊需求，新增对象的主键值请保留为null）
	 * @param operator 操作人对象
	 * @return
	 */
	public ResultInfo<MessagePush> addMessagePush(MessagePush messagePush, Operator operator);
	
	/**
	 * 根据主键，更新一条MessagePush记录
	 * @param messagePush 更新的MessagePush数据，且其主键不能为空
	 * @param operator 操作人对象
	 * @return
	 */
	public ResultInfo<MessagePush> updateMessagePush(MessagePush messagePush, Operator operator);

	/**
	 * 生成主键
	 * @return
	 */
	public String generatePK();
	
	/**
	 * 为MessagePush对象设置默认值
	 * @param obj
	 */
	public void fillDefaultValues(MessagePush obj);

	/**
	 * 推送消息
	 * @param messagePush
	 * @param operator
	 * @return
	 */
	public ResultInfo<MessagePush> pushMessage(MessagePush messagePush, Operator operator);
}
