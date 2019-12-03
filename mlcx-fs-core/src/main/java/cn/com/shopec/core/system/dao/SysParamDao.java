package cn.com.shopec.core.system.dao;

import cn.com.shopec.core.common.BaseDao;
import cn.com.shopec.core.system.model.SysParam;

/**
 * SysParam 数据库处理类
 */
public interface SysParamDao extends BaseDao<SysParam, String> {
	/**
	 * 根据key得到参数
	 */
	public SysParam getByParamKey(String key);

	/**
	 * 根据参数名查找一条信息
	 */
	public SysParam getByParamName(String paramNames);
	
}
