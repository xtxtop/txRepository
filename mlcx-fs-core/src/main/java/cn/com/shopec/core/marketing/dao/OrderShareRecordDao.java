package cn.com.shopec.core.marketing.dao;

import cn.com.shopec.core.common.BaseDao;
import cn.com.shopec.core.marketing.model.OrderShareRecord;

/**
 * OrderShareRecord 数据库处理类
 */
public interface OrderShareRecordDao extends BaseDao<OrderShareRecord,String> {
	
	public OrderShareRecord getOrderShareRecord(String orderNo,String memberNo);
}
