package cn.com.shopec.core.mlorder.service;

import java.util.List;

import cn.com.shopec.common.Operator;
import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.core.common.BaseService;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.ml.model.LockBillingScheme;
import cn.com.shopec.core.ml.vo.OrderSimpleVo;
import cn.com.shopec.core.mlorder.model.LockOrder;

/**
 * 地锁订单表 服务接口类
 */
public interface LockOrderService extends BaseService {

	/**
	 * 根据查询条件，查询并返回LockOrder的列表
	 * 
	 * @param q
	 *            查询条件
	 * @return
	 */
	public List<LockOrder> getLockOrderList(Query q);

	/**
	 * 根据查询条件，分页查询并返回LockOrder的分页结果
	 * 
	 * @param q
	 *            查询条件
	 * @return
	 */
	public PageFinder<LockOrder> getLockOrderPagedList(Query q);

	/**
	 * 根据主键，查询并返回一个LockOrder对象
	 * 
	 * @param id
	 *            主键id
	 * @return
	 */
	public LockOrder getLockOrder(String id);

	/**
	 * 根据主键数组，查询并返回一组LockOrder对象
	 * 
	 * @param ids
	 *            主键数组，数组中的主键值应当无重复值，如有重复值时，则返回的列表长度可能小于传入的数组长度
	 * @return
	 */
	public List<LockOrder> getLockOrderByIds(String[] ids);

	/**
	 * 根据主键，删除特定的LockOrder记录
	 * 
	 * @param id
	 *            主键id
	 * @param operator
	 *            操作人对象
	 * @return
	 */
	public ResultInfo<LockOrder> delLockOrder(String id, Operator operator);

	/**
	 * 新增一条LockOrder记录，执行成功后传入对象及返回对象的主键属性值不为null
	 * 
	 * @param lockOrder
	 *            新增的LockOrder数据（如果无特殊需求，新增对象的主键值请保留为null）
	 * @param operator
	 *            操作人对象
	 * @return
	 */
	public ResultInfo<LockOrder> addLockOrder(LockOrder lockOrder, Operator operator);

	/**
	 * 根据主键，更新一条LockOrder记录
	 * 
	 * @param lockOrder
	 *            更新的LockOrder数据，且其主键不能为空
	 * @param operator
	 *            操作人对象
	 * @return
	 */
	public ResultInfo<LockOrder> updateLockOrder(LockOrder lockOrder, Operator operator);

	public ResultInfo<LockOrder> updateLockOrder_two(LockOrder lockOrder, Operator operator,LockBillingScheme lockBillingScheme );

	/**
	 * 生成主键
	 * 
	 * @return
	 */
	public String generatePK();

	/**
	 * 为LockOrder对象设置默认值
	 * 
	 * @param obj
	 */
	public void fillDefaultValues(LockOrder obj);

	ResultInfo<OrderSimpleVo> roseLock(String orderNo);

	/** 接收到地锁升起回调，修改订单信息 */
	// void changeReceiverOrder(ReceiverLockVo receiverLock);
	void changeReceiverOrder(String receiverLock);
	//根据会员编号获取订单编号
	LockOrder getOrderMember(String member,String lockNo);
}
