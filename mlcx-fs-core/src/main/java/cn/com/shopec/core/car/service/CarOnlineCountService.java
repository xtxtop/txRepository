package cn.com.shopec.core.car.service;

import java.util.Map;

import cn.com.shopec.core.car.model.CarOnlineCount;
import cn.com.shopec.core.common.BaseService;


/**
 * 车辆上下线统计 服务接口类
 */
public interface CarOnlineCountService extends BaseService {
	
	/**
	 * 添加车辆上下线统计
	 * 
	 * @param carOnlineCount
	 */
	public void addCarOnlineCount(CarOnlineCount carOnlineCount);
	
	/**
	 * 获取近10天的车辆上下线数
	 * 
	 * @return
	 */
	Map<String, Object> getCarLine10CountMap();
		
}
