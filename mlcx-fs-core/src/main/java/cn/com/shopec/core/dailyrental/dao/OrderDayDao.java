package cn.com.shopec.core.dailyrental.dao;

import java.util.List;
import java.util.Map;

import cn.com.shopec.core.common.BaseDao;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.dailyrental.model.OrderDay;

/**
 * 日租订单表 数据库处理类
 */
public interface OrderDayDao extends BaseDao<OrderDay,String> {
	
	/**
	 * 根据查询条件统计行数（用户后台）
	 * @param q
	 * @return
	 */
	public Long countForMgt(Query q);
	
	/**
	 * 根据查询条件查询订单（用户后台）
	 * @param q
	 * @return
	 */
	public List<OrderDay> pageListForMgt(Query q);
	
	/**
	 * 统计会员未完成的订单个数
	 * @param memberNo
	 * @return
	 */
	public Long countOrder(String memberNo);
	/**
	 * 统计对应车型未未完成的订单个数
	 * @param carModelId
	 * @return
	 */
	public Long countOrderByCarModelId(String carModelId);
	
	/**
	 * 商家财务对账
	 * 
	 * @param merchantId
	 * @return
	 */
	List<Map<String, Object>> balanceOfAccount(String merchantId);
	/**
	 * 订单退款列表
	 * @return
	 */
	public List<OrderDay>  pageListForRefund(Query q);
	/**
	 * 根据查询条件统计订单退款行数
	 * @param q
	 * @return
	 */
	public Long countForRefund(Query q);

	public Long countCancelAmount(Query q);

	public List<OrderDay> getOrderDayCancelAmountList(Query q);
	
}
