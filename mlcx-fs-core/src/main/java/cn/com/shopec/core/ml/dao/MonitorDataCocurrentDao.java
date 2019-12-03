package cn.com.shopec.core.ml.dao;

import cn.com.shopec.core.common.BaseDao;
import cn.com.shopec.core.ml.model.MonitorDataCocurrent;

/**
 * 监测数据（直流） 数据库处理类
 */
public interface MonitorDataCocurrentDao extends BaseDao<MonitorDataCocurrent,String> {
	
	/**
	 * 根据枪编号得到某个对象
	 * @param no
	 */
	public MonitorDataCocurrent getTwo(String no);
}
