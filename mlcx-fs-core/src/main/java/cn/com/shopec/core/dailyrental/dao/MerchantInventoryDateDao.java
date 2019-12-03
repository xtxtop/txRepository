package cn.com.shopec.core.dailyrental.dao;

import java.util.List;

import cn.com.shopec.core.common.BaseDao;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.dailyrental.model.MerchantInventoryDate;

/**
 * 商家库存日历表 数据库处理类
 */
public interface MerchantInventoryDateDao extends BaseDao<MerchantInventoryDate,String> {

	/**
	 * 按日期删除
	 * 
	 * @param merInventoryId
	 * @param date
	 */
	void deleteByDate(String merInventoryId, String date);

	/**
	 * 批量添加
	 * 
	 * @param list
	 */
	void addBatch(List<MerchantInventoryDate> list);
	
	/**
	 * 商家接单时，増加预占车辆。
	 * @param inventoryDateId
	 */
	public void addLeasedQuantity(String inventoryDateId);
	
	/**
	 * 释放预占车辆
	 * @param c
	 */
	public void releaseLeasedQuantity(MerchantInventoryDate c);
	/**
	 * 获取商家库存日历按自定义日期排序
	 * @param q
	 * @return
	 */
	public List<MerchantInventoryDate> getMerchantInventoryDateList(Query q);
	/**
	 * 修改库存日历可用库存数量
	 * @param merchantInventoryDate
	 */
	public int updateMerchantInventoryDate(MerchantInventoryDate merchantInventoryDate);
	/**
	 * 商家端发布时调用方法
	 * @param merchantInventoryDate
	 * @return
	 */
	public int updateMerchantInventoryDateForPublish(MerchantInventoryDate merchantInventoryDate);
}
