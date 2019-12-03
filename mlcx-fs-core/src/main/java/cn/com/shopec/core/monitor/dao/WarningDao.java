package cn.com.shopec.core.monitor.dao;

import java.util.List;
import java.util.Map;

import cn.com.shopec.core.common.BaseDao;
import cn.com.shopec.core.monitor.model.Warning;

/**
 * 警告表 数据库处理类
 */
public interface WarningDao extends BaseDao<Warning,String> {

	List<Warning> getByCarNos(String[] carNos);

	Warning getWarningByCarNo(String carNo);

	int closeWarning(Warning warning);

	int closeAllWarnng(Warning warning);
	
	List<Map<String, Object>> getRealTimeWarning(String startTime,String endTime);

	Integer countWarning();

	Map<String, Object> getWarningCountMap();
	
	/**
	 * 获取近10天的告警数
	 * 
	 * @return
	 */	
	List<Map<String, Object>> getWarningDay10CountMap();
}
