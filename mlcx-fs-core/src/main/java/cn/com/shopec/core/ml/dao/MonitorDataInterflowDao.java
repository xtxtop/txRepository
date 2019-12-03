package cn.com.shopec.core.ml.dao;

import cn.com.shopec.core.common.BaseDao;
import cn.com.shopec.core.ml.model.MonitorDataInterflow;

/**
 * 监测数据（交流） 数据库处理类
 */
public interface MonitorDataInterflowDao extends BaseDao<MonitorDataInterflow,String> {
	/**
	 * 根据枪编号得到某个对象
	 * @param no
	 */
	public MonitorDataInterflow getTwo(String no);
}
