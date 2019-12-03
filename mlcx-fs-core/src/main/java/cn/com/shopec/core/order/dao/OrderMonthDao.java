package cn.com.shopec.core.order.dao;

import java.util.List;

import cn.com.shopec.core.common.BaseDao;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.order.model.OrderMonth;

/**
 * 月租订单表,一个月租订单内可以有多个车 数据库处理类
 */
public interface OrderMonthDao extends BaseDao<OrderMonth, String> {
	/*
	 * 分页展示月租订单
	 */
	public List<OrderMonth> pageListNs(Query q);

	public Long countNs(Query q);

}
