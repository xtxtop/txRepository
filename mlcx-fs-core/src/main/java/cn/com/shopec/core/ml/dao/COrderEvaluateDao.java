package cn.com.shopec.core.ml.dao;

import cn.com.shopec.core.common.BaseDao;
import cn.com.shopec.core.ml.model.COrderEvaluate;

/**
 * 订单评价表 数据库处理类
 */
public interface COrderEvaluateDao extends BaseDao<COrderEvaluate,String> {
	String getMemberNoByLock(String orderNo);

	String getMemberNoByCharge(String orderNo);
	String getMemberNoByPark(String orderNo);
}
