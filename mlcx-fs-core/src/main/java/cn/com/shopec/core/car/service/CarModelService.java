package cn.com.shopec.core.car.service;

import java.util.List;

import cn.com.shopec.common.Operator;
import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.core.common.BaseService;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.car.model.CarModel;

/**
 * 车辆车型 服务接口类
 */
public interface CarModelService extends BaseService {
	
	/**
	 * 根据查询条件，查询并返回CarModel的列表
	 * @param q 查询条件
	 * @return
	 */
	public List<CarModel> getCarModelForMapiList(Query q);
	/**
	 * 根据查询条件，查询并返回CarModel的列表
	 * @param q 查询条件
	 * @return
	 */
	public List<CarModel> getCarModelList(Query q);
	
	/**
	 * 根据查询条件，分页查询并返回CarModel的分页结果
	 * @param q 查询条件
	 * @return
	 */
	public PageFinder<CarModel> getCarModelPagedList(Query q);
	
	/**
	 * 根据主键，查询并返回一个CarModel对象
	 * @param id 主键id
	 * @return
	 */
	public CarModel getCarModel(String id);

	/**
	 * 根据主键数组，查询并返回一组CarModel对象
	 * @param ids 主键数组，数组中的主键值应当无重复值，如有重复值时，则返回的列表长度可能小于传入的数组长度
	 * @return
	 */
	public List<CarModel> getCarModelByIds(String[] ids);
	
	/**
	 * 根据主键，删除特定的CarModel记录
	 * @param id 主键id
	 * @param operator 操作人对象
	 * @return
	 */
	public ResultInfo<CarModel> delCarModel(String id, Operator operator);
	
	/**
	 * 新增一条CarModel记录，执行成功后传入对象及返回对象的主键属性值不为null
	 * @param carModel 新增的CarModel数据（如果无特殊需求，新增对象的主键值请保留为null）
	 * @param operator 操作人对象
	 * @return
	 */
	public ResultInfo<CarModel> addCarModel(CarModel carModel, Operator operator);
	
	/**
	 * 根据主键，更新一条CarModel记录
	 * @param carModel 更新的CarModel数据，且其主键不能为空
	 * @param operator 操作人对象
	 * @return
	 */
	public ResultInfo<CarModel> updateCarModel(CarModel carModel, Operator operator);

	/**
	 * 生成主键
	 * @return
	 */
	public String generatePK();
	
		/**
	 * 为CarModel对象设置默认值
	 * @param obj
	 */
	public void fillDefaultValues(CarModel obj);
		
}
