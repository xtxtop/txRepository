package cn.com.shopec.core.order.service;

import java.util.Date;
import java.util.List;

import cn.com.shopec.common.Operator;
import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.core.common.BaseService;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.order.model.OrderMileage;

/**
 * OrderMileage 服务接口类
 */
public interface OrderMileageService extends BaseService {

	/**
	 * 根据查询条件，查询并返回OrderMileage的列表
	 * @param q 查询条件
	 * @return
	 */
	public List<OrderMileage> getOrderMileageList(Query q);
	
	/**
	 * 根据查询条件，分页查询并返回OrderMileage的分页结果
	 * @param q 查询条件
	 * @return
	 */
	public PageFinder<OrderMileage> getOrderMileagePagedList(Query q);
	
	/**
	 * 根据主键，查询并返回一个OrderMileage对象
	 * @param id 主键id
	 * @return
	 */
	public OrderMileage getOrderMileage(Date orderMileageDate,String orderNo);

	/**
	 * 根据主键数组，查询并返回一组OrderMileage对象
	 * @param ids 主键数组，数组中的主键值应当无重复值，如有重复值时，则返回的列表长度可能小于传入的数组长度
	 * @return
	 */
	public List<OrderMileage> getOrderMileageByIds(String[] ids);
	
	/**
	 * 根据主键，删除特定的OrderMileage记录
	 * @param id 主键id
	 * @param operator 操作人对象
	 * @return
	 */
	public ResultInfo<OrderMileage> delOrderMileage(String id, Operator operator);
	
	/**
	 * 新增一条OrderMileage记录，执行成功后传入对象及返回对象的主键属性值不为null
	 * @param orderMileage 新增的OrderMileage数据（如果无特殊需求，新增对象的主键值请保留为null）
	 * @param operator 操作人对象
	 * @return
	 */
	public ResultInfo<OrderMileage> addOrderMileage(OrderMileage orderMileage, Operator operator);
	
	/**
	 * 根据主键，更新一条OrderMileage记录
	 * @param orderMileage 更新的OrderMileage数据，且其主键不能为空
	 * @param operator 操作人对象
	 * @return
	 */
	public ResultInfo<OrderMileage> updateOrderMileage(OrderMileage orderMileage, Operator operator);

	/**
	 * 生成主键
	 * @return
	 */
	public String generatePK();
	
		/**
	 * 为OrderMileage对象设置默认值
	 * @param obj
	 */
	public void fillDefaultValues(OrderMileage obj);
		
	/**
	 * 根据主键，查询并返回一个OrderMileage对象
	 * @param id 主键id
	 * @return
	 */
	public OrderMileage getNewestOrderMileage(String orderNo);
	/**
	 * 根据时间和订单号获取订单里程数据
	 * @param milageDate
	 * @param orderNo
	 * @return
	 */
}
