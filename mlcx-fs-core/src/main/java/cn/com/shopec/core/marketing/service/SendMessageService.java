package cn.com.shopec.core.marketing.service;

import java.util.List;

import cn.com.shopec.common.Operator;
import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.core.common.BaseService;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.marketing.model.SendMessage;

/**
 * 推送消息表 服务接口类
 */
public interface SendMessageService extends BaseService {

	/**
	 * 根据查询条件，查询并返回SendMessage的列表
	 * @param q 查询条件
	 * @return
	 */
	public List<SendMessage> getSendMessageList(Query q);
	
	/**
	 * 根据查询条件，分页查询并返回SendMessage的分页结果
	 * @param q 查询条件
	 * @return
	 */
	public PageFinder<SendMessage> getSendMessagePagedList(Query q);
	
	/**
	 * 根据主键，查询并返回一个SendMessage对象
	 * @param id 主键id
	 * @return
	 */
	public SendMessage getSendMessage(String id);

	/**
	 * 根据主键数组，查询并返回一组SendMessage对象
	 * @param ids 主键数组，数组中的主键值应当无重复值，如有重复值时，则返回的列表长度可能小于传入的数组长度
	 * @return
	 */
	public List<SendMessage> getSendMessageByIds(String[] ids);
	
	/**
	 * 根据主键，删除特定的SendMessage记录
	 * @param id 主键id
	 * @param operator 操作人对象
	 * @return
	 */
	public ResultInfo<SendMessage> delSendMessage(String id, Operator operator);
	
	/**
	 *得到所有已预约的订单,并记录到消息表中
	 */
	public ResultInfo<SendMessage> getorderStatusOne()throws Exception;
	/**
	 *得到所有已结束，未支付的订单,并记录到消息表中
	 */
	public ResultInfo<SendMessage>  getorderStatusThree()throws Exception;
	
	/**
	 * 新增一条SendMessage记录，执行成功后传入对象及返回对象的主键属性值不为null
	 * @param sendMessage 新增的SendMessage数据（如果无特殊需求，新增对象的主键值请保留为null）
	 * @param operator 操作人对象
	 * @return
	 */
	public ResultInfo<SendMessage> addSendMessage(SendMessage sendMessage, Operator operator);
	
	/**
	 * 根据主键，更新一条SendMessage记录
	 * @param sendMessage 更新的SendMessage数据，且其主键不能为空
	 * @param operator 操作人对象
	 * @return
	 */
	public ResultInfo<SendMessage> updateSendMessage(SendMessage sendMessage, Operator operator);

	/**
	 * 生成主键
	 * @return
	 */
	public String generatePK();
	
		/**
	 * 为SendMessage对象设置默认值
	 * @param obj
	 */
	public void fillDefaultValues(SendMessage obj);
		
}
