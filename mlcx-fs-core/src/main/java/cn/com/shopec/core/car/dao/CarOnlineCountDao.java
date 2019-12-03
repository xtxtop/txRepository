package cn.com.shopec.core.car.dao;

import java.util.List;
import java.util.Map;

import cn.com.shopec.core.car.model.CarOnlineCount;
import cn.com.shopec.core.common.BaseDao;


/**
 * 车辆上下线统计 数据库处理类
 */
public interface CarOnlineCountDao extends BaseDao<CarOnlineCount,String> {

	/**
	 * 获取近10天的车辆上下线数
	 * 
	 * @return
	 */
	List<Map<String, Object>> getCarLineDay10CountMap();
	
}
