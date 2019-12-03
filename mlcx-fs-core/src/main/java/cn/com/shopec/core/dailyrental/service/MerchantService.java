package cn.com.shopec.core.dailyrental.service;

import java.util.List;

import cn.com.shopec.common.Operator;
import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.core.common.BaseService;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.dailyrental.model.Merchant;
import cn.com.shopec.core.dailyrental.model.MerchantInventory;

/**
 * 租赁商家表 服务接口类
 */
public interface MerchantService extends BaseService {

	/**
	 * 根据查询条件，查询并返回Merchant的列表
	 * @param q 查询条件
	 * @return
	 */
	public List<Merchant> getMerchantList(Query q);
	
	/**
	 * 根据查询条件，分页查询并返回Merchant的分页结果
	 * @param q 查询条件
	 * @return
	 */
	public PageFinder<Merchant> getMerchantPagedList(Query q);
	
	/**
	 * 根据主键，查询并返回一个Merchant对象
	 * @param id 主键id
	 * @return
	 */
	public Merchant getMerchant(String id);

	/**
	 * 根据主键数组，查询并返回一组Merchant对象
	 * @param ids 主键数组，数组中的主键值应当无重复值，如有重复值时，则返回的列表长度可能小于传入的数组长度
	 * @return
	 */
	public List<Merchant> getMerchantByIds(String[] ids);
	
	/**
	 * 根据主键，删除特定的Merchant记录
	 * @param id 主键id
	 * @param operator 操作人对象
	 * @return
	 */
	public ResultInfo<Merchant> delMerchant(String id, Operator operator);
	
	/**
	 * 新增一条Merchant记录，执行成功后传入对象及返回对象的主键属性值不为null
	 * @param merchant 新增的Merchant数据（如果无特殊需求，新增对象的主键值请保留为null）
	 * @param operator 操作人对象
	 * @return
	 */
	public ResultInfo<Merchant> addMerchant(Merchant merchant, Operator operator);
	
	/**
	 * 根据主键，更新一条Merchant记录
	 * @param merchant 更新的Merchant数据，且其主键不能为空
	 * @param operator 操作人对象
	 * @return
	 */
	public ResultInfo<Merchant> updateMerchant(Merchant merchant, Operator operator);
	/**
	 * 根据主键，更新一条Merchant记录
	 * @param merchant 更新的Merchant数据，且其主键不能为空
	 * @param operator 操作人对象
	 * @return
	 */
	public ResultInfo<Merchant> updateMerchantForMgt(Merchant merchant, Operator operator);
	/**
	 * 生成主键
	 * @return
	 */
	public String generatePK();
	
		/**
	 * 为Merchant对象设置默认值
	 * @param obj
	 */
	public void fillDefaultValues(Merchant obj);
	/**
	 * 商家端车型下线
	 * @param merchantId
	 * @param carModelId
	 * @param cityId 
	 * @return
	 */
	public ResultInfo<MerchantInventory> merchantInventoryUpdate(String merchantId,String carModelId, String cityId);
	/**
	 * 商家端车型下线
	 * @param merchantId
	 * @return
	 */
	public ResultInfo<Merchant> merchantInventoryOffLine(MerchantInventory merchantInventory);
}
