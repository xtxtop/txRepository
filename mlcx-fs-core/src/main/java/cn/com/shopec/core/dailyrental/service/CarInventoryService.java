package cn.com.shopec.core.dailyrental.service;

import java.util.List;

import cn.com.shopec.common.Operator;
import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.core.common.BaseService;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.dailyrental.model.CarInventory;

/**
 * 车辆库存表 服务接口类
 */
public interface CarInventoryService extends BaseService {

	/**
	 * 根据查询条件，查询并返回CarInventory的列表
	 * @param q 查询条件
	 * @return
	 */
	public List<CarInventory> getCarInventoryList(Query q);
	
	/**
	 * 根据查询条件，分页查询并返回CarInventory的分页结果
	 * @param q 查询条件
	 * @return
	 */
	public PageFinder<CarInventory> getCarInventoryPagedList(Query q);
	
	/**
	 * 根据主键，查询并返回一个CarInventory对象
	 * @param id 主键id
	 * @return
	 */
	public CarInventory getCarInventory(String id);
	/**
	 * 根据车型id，查询并返回一个CarInventory对象
	 * @param carModelId
	 * @return
	 */
	public CarInventory getCarInventoryByCarModelId(String carModelId);
	/**
	 * 根据主键数组，查询并返回一组CarInventory对象
	 * @param ids 主键数组，数组中的主键值应当无重复值，如有重复值时，则返回的列表长度可能小于传入的数组长度
	 * @return
	 */
	public List<CarInventory> getCarInventoryByIds(String[] ids);
	
	/**
	 * 根据主键，删除特定的CarInventory记录
	 * @param id 主键id
	 * @param operator 操作人对象
	 * @return
	 */
	public ResultInfo<CarInventory> delCarInventory(String id, Operator operator);
	
	/**
	 * 新增一条CarInventory记录，执行成功后传入对象及返回对象的主键属性值不为null
	 * @param carInventory 新增的CarInventory数据（如果无特殊需求，新增对象的主键值请保留为null）
	 * @param operator 操作人对象
	 * @return
	 */
	public ResultInfo<CarInventory> addCarInventory(CarInventory carInventory, Operator operator);
	
	/**
	 * 根据主键，更新一条CarInventory记录
	 * @param carInventory 更新的CarInventory数据，且其主键不能为空
	 * @param operator 操作人对象
	 * @return
	 */
	public ResultInfo<CarInventory> updateCarInventory(CarInventory carInventory, Operator operator);

	/**
	 * 生成主键
	 * @return
	 */
	public String generatePK();
	
		/**
	 * 为CarInventory对象设置默认值
	 * @param obj
	 */
	public void fillDefaultValues(CarInventory obj);
	
	/**
	 * 根据查询条件，分页查询并返回CarInventory的分页结果(后台)
	 * @param q 查询条件
	 * @return
	 */
	public PageFinder<CarInventory> getCarInventoryPagedListForMgt(Query q);

	public PageFinder<CarInventory> getCarModelInventoryPagedList(Query q, String amountType, String startTime, String endTime, Integer day);

	public CarInventory getCarInventoryByCarModelIdAndCity(String carModelId, String cityId);
		
}
