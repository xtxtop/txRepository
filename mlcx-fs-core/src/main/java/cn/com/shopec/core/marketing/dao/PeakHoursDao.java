package cn.com.shopec.core.marketing.dao;

import java.util.List;

import cn.com.shopec.core.common.BaseDao;
import cn.com.shopec.core.marketing.model.PeakHours;

/**
 * PeakHours 数据库处理类
 */
public interface PeakHoursDao extends BaseDao<PeakHours,String> {
	List<PeakHours> queryPeakHoursList(String ruleNo,String id);
}
