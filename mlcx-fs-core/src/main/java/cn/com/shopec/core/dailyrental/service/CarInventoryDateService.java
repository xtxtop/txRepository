package cn.com.shopec.core.dailyrental.service;

import java.util.List;

import cn.com.shopec.common.Operator;
import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.core.common.BaseService;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.dailyrental.model.CarInventoryDate;

/**
 * 车辆库存日历表 服务接口类
 */
public interface CarInventoryDateService extends BaseService {

	/**
	 * 根据查询条件，查询并返回CarInventoryDate的列表
	 * @param q 查询条件
	 * @return
	 */
	public List<CarInventoryDate> getCarInventoryDateList(Query q);
	
	/**
	 * 根据查询条件，分页查询并返回CarInventoryDate的分页结果
	 * @param q 查询条件
	 * @return
	 */
	public PageFinder<CarInventoryDate> getCarInventoryDatePagedList(Query q);
	
	/**
	 * 根据主键，查询并返回一个CarInventoryDate对象
	 * @param id 主键id
	 * @return
	 */
	public CarInventoryDate getCarInventoryDate(String id);

	/**
	 * 根据主键数组，查询并返回一组CarInventoryDate对象
	 * @param ids 主键数组，数组中的主键值应当无重复值，如有重复值时，则返回的列表长度可能小于传入的数组长度
	 * @return
	 */
	public List<CarInventoryDate> getCarInventoryDateByIds(String[] ids);
	
	/**
	 * 根据主键，删除特定的CarInventoryDate记录
	 * @param id 主键id
	 * @param operator 操作人对象
	 * @return
	 */
	public ResultInfo<CarInventoryDate> delCarInventoryDate(String id, Operator operator);
	
	/**
	 * 新增一条CarInventoryDate记录，执行成功后传入对象及返回对象的主键属性值不为null
	 * @param carInventoryDate 新增的CarInventoryDate数据（如果无特殊需求，新增对象的主键值请保留为null）
	 * @param operator 操作人对象
	 * @return
	 */
	public ResultInfo<CarInventoryDate> addCarInventoryDate(CarInventoryDate carInventoryDate, Operator operator);
	
	/**
	 * 根据主键，更新一条CarInventoryDate记录
	 * @param carInventoryDate 更新的CarInventoryDate数据，且其主键不能为空
	 * @param operator 操作人对象
	 * @return
	 */
	public ResultInfo<CarInventoryDate> updateCarInventoryDate(CarInventoryDate carInventoryDate, Operator operator);

	/**
	 * 生成主键
	 * @return
	 */
	public String generatePK();
	
		/**
	 * 为CarInventoryDate对象设置默认值
	 * @param obj
	 */
	public void fillDefaultValues(CarInventoryDate obj);
	
	public void updateBatchCarInventoryDate(CarInventoryDate carInventoryDate, Operator operator);
	
	/**
	 * 释放预占库存
	 * @param carInventoryDate
	 */
	public void releaseLeasedQuantity(CarInventoryDate carInventoryDate, Operator operator);
	
	/**
	 * 根据条件修改车辆库存日历
	 * @param carInventoryDate
	 * @return
	 */
	public int updateCarInventoryDateByQ(CarInventoryDate carInventoryDate);
		
}
