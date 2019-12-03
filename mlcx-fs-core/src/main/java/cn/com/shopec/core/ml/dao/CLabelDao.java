package cn.com.shopec.core.ml.dao;

import cn.com.shopec.core.common.BaseDao;
import cn.com.shopec.core.ml.model.CLabel;

/**
 * 充电站标签表 数据库处理类
 */
public interface CLabelDao extends BaseDao<CLabel, String> {
	String[] searchInLabelNos(String[] ids);
}
