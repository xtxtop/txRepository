package cn.com.shopec.core.order.dao;

import java.util.List;

import cn.com.shopec.core.common.BaseDao;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.order.model.OrderMonthCar;

/**
 * 长租订单车辆表 数据库处理类
 */
public interface OrderMonthCarDao extends BaseDao<OrderMonthCar, String> {

	public List<OrderMonthCar> pageListNs(Query q);

	public Long countNs(Query q);

	public int deleteNs(String orderNo);

}
