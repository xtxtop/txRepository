package cn.com.shopec.core.car.dao;

import cn.com.shopec.core.car.model.CarDetailMainData;
import cn.com.shopec.core.common.BaseDao;


/**
 * CarDetailMainData 数据库处理类
 */
public interface CarDetailMainDataDao extends BaseDao<CarDetailMainData,String> {
	
	/**
	 * 根据设备序列号，判断是否存在此设备的数据
	 * @param deviceSn
	 * @return
	 */
	public Long isExists(String deviceSn);
	
	/**
	 * 根据设备序号，取最近更新时间信息
	 * @param deviceSn
	 * @return
	 */
	public CarDetailMainData getLastReportingTimeByDeviceSn(String deviceSn);
}
