package cn.com.shopec.core.dailyrental.dao;

import java.util.List;

import cn.com.shopec.core.common.BaseDao;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.dailyrental.model.CarInventory;

/**
 * 车辆库存表 数据库处理类
 */
public interface CarInventoryDao extends BaseDao<CarInventory,String> {

	public List<CarInventory> pageListCarModel(Query q);
	
	/**
	 * 根据查询条件统计行数（用户后台）
	 * @param q
	 * @return
	 */
	public Long countForMgt(Query q);
	
	/**
	 * 根据查询条件查询车辆库存（用户后台）
	 * @param q
	 * @return
	 */
	public List<CarInventory> pageListForMgt(Query q);
	
	/**
	 * 根据车型id，查询并返回一个CarInventory对象
	 * @param carModelId
	 */
	public CarInventory getCarInventoryByCarModelId(String carModelId);
	/*
	 * 根据车型和城市查找车型库存
	 * */
	public CarInventory getCarInventoryByCarModelIdAndCity(String carModelId, String cityId);
	
}
