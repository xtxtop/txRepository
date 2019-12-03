package cn.com.shopec.core.order.dao;


import java.util.Date;

import cn.com.shopec.core.common.BaseDao;
import cn.com.shopec.core.order.model.OrderMileage;

/**
 * OrderMileage 数据库处理类
 */
public interface OrderMileageDao extends BaseDao<OrderMileage,String> {
	/**
	 * 根据订单编号获取最新的订单里程记录
	 * @param orderNo
	 */
	public OrderMileage getNewestOrderMileage(String orderNo);
	/**
	 * 根据订单号和里程所属期获取订单里程记录
	 * @param orderMileageDate
	 * @param orderNo
	 * @return
	 */
	public OrderMileage getOrderMileage(Date orderMileageDate,String orderNo);
}
