package cn.com.shopec.core.dailyrental.service;

import java.util.List;
import java.util.Map;

import cn.com.shopec.common.Operator;
import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.core.common.BaseService;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.dailyrental.model.DelayOrderDay;
import cn.com.shopec.core.dailyrental.model.OrderDay;

/**
 * 日租订单表 服务接口类
 */
public interface OrderDayService extends BaseService {

	/**
	 * 根据查询条件，查询并返回OrderDay的列表
	 * @param q 查询条件
	 * @return
	 */
	public List<OrderDay> getOrderDayList(Query q);
	
	/**
	 * 根据查询条件，分页查询并返回OrderDay的分页结果
	 * @param q 查询条件
	 * @return
	 */
	public PageFinder<OrderDay> getOrderDayPagedList(Query q);
	
	/**
	 * 根据查询条件，分页查询并返回OrderDay的分页结果(后台)
	 * @param q 查询条件
	 * @return
	 */
	public PageFinder<OrderDay> getOrderDayPagedListForMgt(Query q);
	
	/**
	 * 根据主键，查询并返回一个OrderDay对象
	 * @param id 主键id
	 * @return
	 */
	public OrderDay getOrderDay(String id);

	/**
	 * 根据主键数组，查询并返回一组OrderDay对象
	 * @param ids 主键数组，数组中的主键值应当无重复值，如有重复值时，则返回的列表长度可能小于传入的数组长度
	 * @return
	 */
	public List<OrderDay> getOrderDayByIds(String[] ids);
	
	/**
	 * 根据主键，删除特定的OrderDay记录
	 * @param id 主键id
	 * @param operator 操作人对象
	 * @return
	 */
	public ResultInfo<OrderDay> delOrderDay(String id, Operator operator);
	
	/**
	 * 新增一条OrderDay记录，执行成功后传入对象及返回对象的主键属性值不为null
	 * @param orderDay 新增的OrderDay数据（如果无特殊需求，新增对象的主键值请保留为null）
	 * @param operator 操作人对象
	 * @return
	 */
	public ResultInfo<OrderDay> addOrderDay(OrderDay orderDay, Operator operator);
	
	/**
	 * 根据主键，更新一条OrderDay记录
	 * @param orderDay 更新的OrderDay数据，且其主键不能为空
	 * @param operator 操作人对象
	 * @return
	 */
	public ResultInfo<OrderDay> updateOrderDay(OrderDay orderDay, Operator operator);

	/**
	 * 生成主键
	 * @return
	 */
	public String generatePK();
	
	/**
	 * 为OrderDay对象设置默认值
	 * @param obj
	 */
	public void fillDefaultValues(OrderDay obj);
	
	public int count(Query q);	
	/**
	 * 统计会员未结束的订单
	 * @param memberNo
	 * @return
	 */
	public int countOrder(String memberNo);
	/**
	 * 统计对应车型未未完成的订单个数
	 * @param carModelId
	 * @return
	 */
	public int countOrderByCarModelId(String carModelId);

	public int countCancelAmount(Query q);

	public List<OrderDay> getOrderDayCancelAmountList(Query q);

	ResultInfo<OrderDay> orderDayRefundWX(OrderDay orderDay,Double refundAmount,Operator operator);

	ResultInfo<String> signOrder(String orderNo, Map<String, String> tradeInfo);

	ResultInfo<OrderDay> toOrderDayRefundAvoidPwd(String orderNo,Double refundAmount, Operator operator, String remark);

	ResultInfo<DelayOrderDay> delayOrderDay(DelayOrderDay delayOrderDay);

	PageFinder<OrderDay> getOrderDayRefundPagedList(Query q);
}
