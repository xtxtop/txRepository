package cn.com.shopec.core.car.dao;

import java.util.List;
import java.util.Map;

import cn.com.shopec.core.car.model.CarOnlineLog;
import cn.com.shopec.core.common.BaseDao;
import cn.com.shopec.core.common.Query;


/**
 * CarOnlineLog 数据库处理类
 */
public interface CarOnlineLogDao extends BaseDao<CarOnlineLog,String> {
	//根据查询条件，分页查询并返回CarOnlineLog的分页结果   并且查出操作人
	public	List<CarOnlineLog> pageLists(Query q);

	/**
	 * 获取近10天的车辆上下线数
	 * 
	 * @return
	 */
	List<Map<String, Object>> getCarLineDay10CountMap();
	
}
