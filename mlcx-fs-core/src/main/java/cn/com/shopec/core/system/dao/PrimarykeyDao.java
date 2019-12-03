package cn.com.shopec.core.system.dao;

import cn.com.shopec.core.common.BaseDao;
import cn.com.shopec.core.system.model.Primarykey;

/**
 * Primarykey 数据库处理类
 */
public interface PrimarykeyDao extends BaseDao<Primarykey,String> {

	public Primarykey getValueByBizType(Integer type);
}
