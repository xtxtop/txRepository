package cn.com.shopec.core.order.service;

import java.util.List;

import cn.com.shopec.common.Operator;
import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.core.common.BaseService;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.order.model.OrderMonth;

/**
 * 月租订单表,一个月租订单内可以有多个车 服务接口类
 */
public interface OrderMonthService extends BaseService {

	/**
	 * 根据查询条件，查询并返回OrderMonth的列表
	 * 
	 * @param q
	 *            查询条件
	 * @return
	 */
	public List<OrderMonth> getOrderMonthList(Query q);

	/**
	 * 根据查询条件，分页查询并返回OrderMonth的分页结果
	 * 
	 * @param q
	 *            查询条件
	 * @return
	 */
	public PageFinder<OrderMonth> getOrderMonthPagedList(Query q);

	/**
	 * 根据主键，查询并返回一个OrderMonth对象
	 * 
	 * @param id
	 *            主键id
	 * @return
	 */
	public OrderMonth getOrderMonth(String id);

	/**
	 * 根据主键数组，查询并返回一组OrderMonth对象
	 * 
	 * @param ids
	 *            主键数组，数组中的主键值应当无重复值，如有重复值时，则返回的列表长度可能小于传入的数组长度
	 * @return
	 */
	public List<OrderMonth> getOrderMonthByIds(String[] ids);

	/**
	 * 根据主键，删除特定的OrderMonth记录
	 * 
	 * @param id
	 *            主键id
	 * @param operator
	 *            操作人对象
	 * @return
	 */
	public ResultInfo<OrderMonth> delOrderMonth(String id, Operator operator);

	/**
	 * 新增一条OrderMonth记录，执行成功后传入对象及返回对象的主键属性值不为null
	 * 
	 * @param orderMonth
	 *            新增的OrderMonth数据（如果无特殊需求，新增对象的主键值请保留为null）
	 * @param operator
	 *            操作人对象
	 * @return
	 */
	public ResultInfo<OrderMonth> addOrderMonth(OrderMonth orderMonth, Operator operator);

	/**
	 * 根据主键，更新一条OrderMonth记录
	 * 
	 * @param orderMonth
	 *            更新的OrderMonth数据，且其主键不能为空
	 * @param operator
	 *            操作人对象
	 * @return
	 */
	public ResultInfo<OrderMonth> updateOrderMonth(OrderMonth orderMonth, Operator operator);

	/**
	 * 生成主键
	 * 
	 * @return
	 */
	public String generatePK();

	/**
	 * 为OrderMonth对象设置默认值
	 * 
	 * @param obj
	 */
	public void fillDefaultValues(OrderMonth obj);

	/*
	 * 分页展示月租订单
	 */
	public PageFinder<OrderMonth> pageListNs(Query q);

}
