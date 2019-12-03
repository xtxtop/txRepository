package cn.com.shopec.core.dailyrental.dao;

import cn.com.shopec.core.common.BaseDao;
import cn.com.shopec.core.dailyrental.model.CarInventoryDate;

/**
 * 车辆库存日历表 数据库处理类
 */
public interface CarInventoryDateDao extends BaseDao<CarInventoryDate,String> {
	
	void updateBatch(CarInventoryDate carInventoryDate);
	
	
	/**
	 * 释放预占车辆
	 * @param carInventoryDate
	 */
	public void releaseLeasedQuantity(CarInventoryDate carInventoryDate);
	/**
	 * 根据条件修改车辆库存日历
	 * @param carInventoryDate
	 * @return
	 */
	public int updateCarInventoryDate(CarInventoryDate carInventoryDate);
}
