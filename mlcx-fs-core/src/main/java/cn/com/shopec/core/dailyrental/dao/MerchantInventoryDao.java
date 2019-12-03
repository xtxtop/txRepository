package cn.com.shopec.core.dailyrental.dao;

import java.util.List;

import cn.com.shopec.core.common.BaseDao;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.dailyrental.model.MerchantInventory;

/**
 * 商家库存表 数据库处理类
 */
public interface MerchantInventoryDao extends BaseDao<MerchantInventory,String> {
	
	/**
	 * 根据车型查询商家库存(找最大库存用)
	 * @param carModelId
	 * @return
	 */
	public List<MerchantInventory> getMerchantInventoryByCarModelId(Query q);
	
	/**
	 * 根据商家编号和车型编号查询商家车型库存
	 * @param merchantId
	 * @param carModelId
	 * @return
	 */
	public List<MerchantInventory> getInventoryByMerchantAndCarModelAndCityId(String merchantId,String carModelId,String cityId);
	
	/*
	 * 根据车型编号 查找有库存的车
	 * */

	public List<String> getMerchantsByCarModelId(String carModelId, String cityId);
	
	public Long countForMgt(Query q);

	public Long getMerchantsByCarCount(String carModelId, String merchantId, String cityId);
	/**
	 * 根据车型,商家，城市查找商家库存记录
	 * @param merchantInventory
	 * @return
	 */
	public MerchantInventory getMerchantInventory(MerchantInventory merchantInventory);
	
	public int updateMerchantInventory(MerchantInventory merchantInventory);
	/**
	 * 根据车型修改商家库存记录
	 * @param merchantInventory
	 * @return
	 */
	public int updateMerchantInventoryByCarModelId(MerchantInventory merchantInventory);
	/**
	 * 根据品牌,车系、年代查找商家车辆库存对应库存>0的品牌,车系、年代、适用类型
	 * @param q
	 * @return
	 */
	public List<MerchantInventory> getMerchantInventoryCarModel(Query q);
}
