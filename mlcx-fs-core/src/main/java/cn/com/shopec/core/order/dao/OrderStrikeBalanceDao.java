package cn.com.shopec.core.order.dao;

import java.util.List;

import cn.com.shopec.core.common.BaseDao;
import cn.com.shopec.core.order.model.OrderStrikeBalance;

/**
 * 订单冲账表 数据库处理类
 */
public interface OrderStrikeBalanceDao extends BaseDao<OrderStrikeBalance,String> {

	Integer countOrderStrikeBalanceTodoIndexValue();
	
}
