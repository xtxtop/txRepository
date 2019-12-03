package cn.com.shopec.core.order.service;

import java.util.List;
import java.util.Map;

import cn.com.shopec.common.Operator;
import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.core.common.BaseService;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.order.model.OrderStrikeBalance;

/**
 * 订单冲账表 服务接口类
 */
public interface OrderStrikeBalanceService extends BaseService {

	/**
	 * 根据查询条件，查询并返回OrderStrikeBalance的列表
	 * @param q 查询条件
	 * @return
	 */
	public List<OrderStrikeBalance> getOrderStrikeBalanceList(Query q);
	
	/**
	 * 根据查询条件，分页查询并返回OrderStrikeBalance的分页结果
	 * @param q 查询条件
	 * @return
	 */
	public PageFinder<OrderStrikeBalance> getOrderStrikeBalancePagedList(Query q);
	
	/**
	 * 根据主键，查询并返回一个OrderStrikeBalance对象
	 * @param id 主键id
	 * @return
	 */
	public OrderStrikeBalance getOrderStrikeBalance(String id);

	/**
	 * 根据主键数组，查询并返回一组OrderStrikeBalance对象
	 * @param ids 主键数组，数组中的主键值应当无重复值，如有重复值时，则返回的列表长度可能小于传入的数组长度
	 * @return
	 */
	public List<OrderStrikeBalance> getOrderStrikeBalanceByIds(String[] ids);
	
	/**
	 * 根据主键，删除特定的OrderStrikeBalance记录
	 * @param id 主键id
	 * @param operator 操作人对象
	 * @return
	 */
	public ResultInfo<OrderStrikeBalance> delOrderStrikeBalance(String id, Operator operator);
	
	/**
	 * 新增一条OrderStrikeBalance记录，执行成功后传入对象及返回对象的主键属性值不为null
	 * @param orderStrikeBalance 新增的OrderStrikeBalance数据（如果无特殊需求，新增对象的主键值请保留为null）
	 * @param operator 操作人对象
	 * @return
	 */
	public ResultInfo<OrderStrikeBalance> addOrderStrikeBalance(OrderStrikeBalance orderStrikeBalance, Operator operator);
	
	/**
	 * 根据主键，更新一条OrderStrikeBalance记录
	 * @param orderStrikeBalance 更新的OrderStrikeBalance数据，且其主键不能为空
	 * @param operator 操作人对象
	 * @return
	 */
	public ResultInfo<OrderStrikeBalance> updateOrderStrikeBalance(OrderStrikeBalance orderStrikeBalance, Operator operator);

	/**
	 * 生成主键
	 * @return
	 */
	public String generatePK();
	
		/**
	 * 为OrderStrikeBalance对象设置默认值
	 * @param obj
	 */
	public void fillDefaultValues(OrderStrikeBalance obj);
/**
 * 后台添加订单冲账并修改订单表信息  zln
 * */
		public ResultInfo<OrderStrikeBalance> addOrderStrikeBalanceAndUpdateOrder(OrderStrikeBalance orderStrikeBalance,
				Operator operator);
//订单冲账待审核统计
public Integer getTodoIndexValue();
		
}
