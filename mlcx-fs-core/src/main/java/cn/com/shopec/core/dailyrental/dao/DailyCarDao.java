package cn.com.shopec.core.dailyrental.dao;

import cn.com.shopec.core.common.BaseDao;
import cn.com.shopec.core.dailyrental.model.DailyCar;

/**
 * DailyCar 数据库处理类
 */
public interface DailyCarDao extends BaseDao<DailyCar,String> {
	
	/**
	 * 根据车型，商家，车牌修改车辆
	 * @param dailyCar
	 * @return
	 */
	public Integer updateDailyCarByPlateNo(DailyCar dailyCar);
	/**
	 * 根据车牌号获取车辆
	 * @param carPlateNo
	 * @return
	 */
	public DailyCar getDailyCarByCarPlateNo(String carPlateNo);
}
