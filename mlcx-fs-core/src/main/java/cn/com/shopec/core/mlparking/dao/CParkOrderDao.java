package cn.com.shopec.core.mlparking.dao;

import java.util.List;

import cn.com.shopec.core.common.BaseDao;
import cn.com.shopec.core.mlparking.model.CParkOrder;
import cn.com.shopec.core.mlparking.vo.OrderInfo;

/**
 * 停车订单表 数据库处理类
 */
public interface CParkOrderDao extends BaseDao<CParkOrder,String> {
	//查询当前会员订单是否存在未支付或者未结束订单
	List<CParkOrder> getOrder(String menberNo);
	//获取订单详情
	OrderInfo getOrderInfo(String parkOrderNo);
	//获取未结束订单
	CParkOrder getOrderOver(String menberNo);
}
