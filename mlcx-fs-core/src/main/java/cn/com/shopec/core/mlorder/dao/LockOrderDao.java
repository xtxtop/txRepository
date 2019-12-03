package cn.com.shopec.core.mlorder.dao;

import cn.com.shopec.core.common.BaseDao;
import cn.com.shopec.core.mlorder.model.LockOrder;

/**
 * 地锁订单表 数据库处理类
 */
public interface LockOrderDao extends BaseDao<LockOrder,String> {
	LockOrder getOrderMember(String member,String lockNo);
}
