package cn.com.shopec.core.dailyrental.dao;

import java.util.List;
import java.util.Map;

import cn.com.shopec.core.common.BaseDao;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.dailyrental.model.MerchantOrder;

/**
 * 商家订单表 数据库处理类
 */
public interface MerchantOrderDao extends BaseDao<MerchantOrder,String> {
	
	public List<Map<String,Object>> pageMapList(Query q);
	
	public Long countMap(Query q);
	
	public Map<String,Object> getChangeMerchantOrder(String id);

	public MerchantOrder getOrderDayNo(String orderNo);
	
	public MerchantOrder getOrderDayByOrderNo(String orderNo);
	
	public String getMerhcantOrderNo(String merchantId);
}
