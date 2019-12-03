package cn.com.shopec.core.ml.dao;

import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.ml.vo.OrderDetailVo;
import cn.com.shopec.core.ml.vo.OrderSimpleVo;

import java.util.List;
import java.util.Map;

public interface COrderDao {
	// FIXME 评论状态未判断
	//查询订单
	List<OrderSimpleVo> searchOrderList(Query q);

	//查询订单不区分类别
	List<OrderSimpleVo> searchOrderListNoCategoryNotFinished(Query q);

	//查询订单详情
	OrderDetailVo findOrderDetailByVo(Query q);

	//获取token
	String getToken();

	//评论后更改状态
	Integer updateEvaluateStatus(Map<String, Object> map);
}
