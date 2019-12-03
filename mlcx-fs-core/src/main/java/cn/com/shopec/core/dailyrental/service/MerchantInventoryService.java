package cn.com.shopec.core.dailyrental.service;

import java.util.List;

import cn.com.shopec.common.Operator;
import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.core.common.BaseService;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.dailyrental.model.MerchantInventory;

/**
 * 商家库存表 服务接口类
 */
public interface MerchantInventoryService extends BaseService {

	/**
	 * 根据查询条件，查询并返回MerchantInventory的列表
	 * @param q 查询条件
	 * @return
	 */
	public List<MerchantInventory> getMerchantInventoryList(Query q);
	
	/**
	 * 根据查询条件，分页查询并返回MerchantInventory的分页结果
	 * @param q 查询条件
	 * @return
	 */
	public PageFinder<MerchantInventory> getMerchantInventoryPagedList(Query q);
	
	/**
	 * 根据主键，查询并返回一个MerchantInventory对象
	 * @param id 主键id
	 * @return
	 */
	public MerchantInventory getMerchantInventory(String id);

	/**
	 * 根据主键数组，查询并返回一组MerchantInventory对象
	 * @param ids 主键数组，数组中的主键值应当无重复值，如有重复值时，则返回的列表长度可能小于传入的数组长度
	 * @return
	 */
	public List<MerchantInventory> getMerchantInventoryByIds(String[] ids);
	
	/**
	 * 根据主键，删除特定的MerchantInventory记录
	 * @param id 主键id
	 * @param operator 操作人对象
	 * @return
	 */
	public ResultInfo<MerchantInventory> delMerchantInventory(String id, Operator operator);
	
	/**
	 * 新增一条MerchantInventory记录，执行成功后传入对象及返回对象的主键属性值不为null
	 * @param merchantInventory 新增的MerchantInventory数据（如果无特殊需求，新增对象的主键值请保留为null）
	 * @param operator 操作人对象
	 * @return
	 */
	public ResultInfo<MerchantInventory> addMerchantInventory(MerchantInventory merchantInventory, Operator operator);
	
	/**
	 * 根据主键，更新一条MerchantInventory记录
	 * @param merchantInventory 更新的MerchantInventory数据，且其主键不能为空
	 * @param operator 操作人对象
	 * @return
	 */
	public ResultInfo<MerchantInventory> updateMerchantInventory(MerchantInventory merchantInventory, Operator operator);

	/**
	 * 生成主键
	 * @return
	 */
	public String generatePK();
	
		/**
	 * 为MerchantInventory对象设置默认值
	 * @param obj
	 */
	public void fillDefaultValues(MerchantInventory obj);
	/**
	 * 根据车型查找商家库存情况-后台
	 * @param carModelId
	 * @return
	 */
	public PageFinder<MerchantInventory> getMerchantInventoryByCarModelId(Query q);
	
	/**
	 * 根据车型查找有对应车型库存的商家
	 * @param carModelId
	 * @param cityId 
	 * @return
	 */
	public List<String> getMerchantsByCarModelId(String carModelId, String cityId);

	public Long getMerchantsByCarCount(String carModelId, String merchantId, String cityId);
	/**
	 * 根据车型,商家，城市查找商家库存记录
	 * @param merchantInventory
	 * @return
	 */
	public MerchantInventory getMerchantInventory(MerchantInventory merchantInventory);
	/**
	 * 根据车型修改商家库存记录
	 * @param merchantInventory
	 * @return
	 */
	public ResultInfo<MerchantInventory> updateMerchantInventoryByCarModelId(MerchantInventory merchantInventory);
	/**
	 * 商家车型库存发布
	 * @param merchantInventory
	 * @return
	 */
	public ResultInfo<MerchantInventory> publishMerchantInventory(MerchantInventory merchantInventory);
	
	/**
	 * 根据品牌,车系、年代查找商家车辆库存对应库存>0的品牌,车系、年代、适用类型
	 * @param q
	 * @return
	 */
	public List<MerchantInventory> getMerchantInventoryCarModel(Query q);
}
