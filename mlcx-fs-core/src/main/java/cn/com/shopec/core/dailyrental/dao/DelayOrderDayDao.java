package cn.com.shopec.core.dailyrental.dao;

import cn.com.shopec.core.common.BaseDao;
import cn.com.shopec.core.dailyrental.model.DelayOrderDay;

/**
 * DelayOrderDay 数据库处理类
 */
public interface DelayOrderDayDao extends BaseDao<DelayOrderDay,String> {
	/**
	 * 通过订单编号获取最新一条续租信息
	 * @param orderNo
	 * @return
	 */
	public DelayOrderDay getDelayOrderDayByOrderDayNo(String orderNo);
}
