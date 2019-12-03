package cn.com.shopec.core.dailyrental.service;

import java.util.List;

import cn.com.shopec.common.Operator;
import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.core.common.BaseService;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.dailyrental.model.MerchantInventoryDate;
import cn.com.shopec.core.dailyrental.model.OrderDay;

/**
 * 商家库存日历表 服务接口类
 */
public interface MerchantInventoryDateService extends BaseService {

	/**
	 * 根据查询条件，查询并返回MerchantInventoryDate的列表
	 * @param q 查询条件
	 * @return
	 */
	public List<MerchantInventoryDate> getMerchantInventoryDateList(Query q);
	
	/**
	 * 根据查询条件，分页查询并返回MerchantInventoryDate的分页结果
	 * @param q 查询条件
	 * @return
	 */
	public PageFinder<MerchantInventoryDate> getMerchantInventoryDatePagedList(Query q);
	
	/**
	 * 根据主键，查询并返回一个MerchantInventoryDate对象
	 * @param id 主键id
	 * @return
	 */
	public MerchantInventoryDate getMerchantInventoryDate(String id);

	/**
	 * 根据主键数组，查询并返回一组MerchantInventoryDate对象
	 * @param ids 主键数组，数组中的主键值应当无重复值，如有重复值时，则返回的列表长度可能小于传入的数组长度
	 * @return
	 */
	public List<MerchantInventoryDate> getMerchantInventoryDateByIds(String[] ids);
	
	/**
	 * 根据主键，删除特定的MerchantInventoryDate记录
	 * @param id 主键id
	 * @param operator 操作人对象
	 * @return
	 */
	public ResultInfo<MerchantInventoryDate> delMerchantInventoryDate(String id, Operator operator);
	
	/**
	 * 新增一条MerchantInventoryDate记录，执行成功后传入对象及返回对象的主键属性值不为null
	 * @param merchantInventoryDate 新增的MerchantInventoryDate数据（如果无特殊需求，新增对象的主键值请保留为null）
	 * @param operator 操作人对象
	 * @return
	 */
	public ResultInfo<MerchantInventoryDate> addMerchantInventoryDate(MerchantInventoryDate merchantInventoryDate, Operator operator);
	
	/**
	 * 根据主键，更新一条MerchantInventoryDate记录
	 * @param merchantInventoryDate 更新的MerchantInventoryDate数据，且其主键不能为空
	 * @param operator 操作人对象
	 * @return
	 */
	public ResultInfo<MerchantInventoryDate> updateMerchantInventoryDate(MerchantInventoryDate merchantInventoryDate, Operator operator);

	
	/**
	 * 根据商家ID,车型,日期段 追加预占库存
	 * @param merchantInventoryDate 更新的MerchantInventoryDate数据，且其主键不能为空
	 * @param operator 操作人对象
	 * @return
	 */
	public ResultInfo<MerchantInventoryDate> superaddleasedQuantity(MerchantInventoryDate merchantInventoryDate,OrderDay orderDay, Operator operator);
	/**
	 * 根据商家ID,车型,日期段 追加预占库存
	 * @param merchantInventoryDate 更新的MerchantInventoryDate数据和carInventory和carInventoryDate数据，且其主键不能为空
	 * @param operator 操作人对象
	 * @return
	 */
	public ResultInfo<MerchantInventoryDate> superaddleasedQuantityForChangeCar(MerchantInventoryDate merchantInventoryDate,OrderDay orderDay, Operator operator);
	/**
	 * 释放预占库存
	 * @param merchantInventoryDate
	 * @param operator
	 * @return
	 */
	public ResultInfo<MerchantInventoryDate> releaseLeasedQuantity(MerchantInventoryDate merchantInventoryDate,OrderDay orderDay, Operator operator);
	
	/**
	 * 生成主键
	 * @return
	 */
	public String generatePK();
	
		/**
	 * 为MerchantInventoryDate对象设置默认值
	 * @param obj
	 */
	public void fillDefaultValues(MerchantInventoryDate obj);
	
	public ResultInfo<String> editMerchantInventoryDate(String jsonData);
	
	public ResultInfo<String> delMerchantInventoryDate(String merInventoryId, String date);
	
	/**
	 * 获取商家库存日历按自定义日期排序
	 * @param q
	 * @return
	 */
	public List<MerchantInventoryDate> getMerchantInventoryDateListByQ(Query q);
	
	/**
	 * 修改库存日历可用库存数量
	 * @param merchantInventoryDate
	 */
	public int updateMerchantInventoryDateByQ(MerchantInventoryDate merchantInventoryDate);

	ResultInfo<MerchantInventoryDate> releaseLeasedQuantityForDelayOrderDay(MerchantInventoryDate merchantInventoryDate,
			OrderDay orderDay, Operator operator);
		
}
