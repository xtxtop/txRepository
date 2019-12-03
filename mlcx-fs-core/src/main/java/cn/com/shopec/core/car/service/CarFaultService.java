package cn.com.shopec.core.car.service;

import java.util.List;

import cn.com.shopec.common.Operator;
import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.core.common.BaseService;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.car.model.CarFault;

/**
 * CarFault 服务接口类
 */
public interface CarFaultService extends BaseService {

	/**
	 * 根据查询条件，查询并返回CarFault的列表
	 * @param q 查询条件
	 * @return
	 */
	public List<CarFault> getCarFaultList(Query q);
	
	/**
	 * 根据查询条件，分页查询并返回CarFault的分页结果
	 * @param q 查询条件
	 * @return
	 */
	public PageFinder<CarFault> getCarFaultPagedList(Query q);
	/**
	 * @author lj
	 * 后台
	 * 根据查询条件，分页查询并返回CarFault的分页结果
	 * @param q 查询条件
	 * @return
	 */
	public PageFinder<CarFault> getCarMalfunctionPageList(Query q);
	
	/**
	 * 根据主键，查询并返回一个CarFault对象
	 * @param id 主键id
	 * @return
	 */
	public CarFault getCarFault(String id);

	/**
	 * 根据主键数组，查询并返回一组CarFault对象
	 * @param ids 主键数组，数组中的主键值应当无重复值，如有重复值时，则返回的列表长度可能小于传入的数组长度
	 * @return
	 */
	public List<CarFault> getCarFaultByIds(String[] ids);
	
	/**
	 * 根据主键，删除特定的CarFault记录
	 * @param id 主键id
	 * @param operator 操作人对象
	 * @return
	 */
	public ResultInfo<CarFault> delCarFault(String id, Operator operator);
	
	/**
	 * 新增一条CarFault记录，执行成功后传入对象及返回对象的主键属性值不为null
	 * @param carFault 新增的CarFault数据（如果无特殊需求，新增对象的主键值请保留为null）
	 * @param operator 操作人对象
	 * @return
	 */
	public ResultInfo<CarFault> addCarFault(CarFault carFault, Operator operator);
	
	/**
	 * 根据主键，更新一条CarFault记录
	 * @param carFault 更新的CarFault数据，且其主键不能为空
	 * @param operator 操作人对象
	 * @return
	 */
	public ResultInfo<CarFault> updateCarFault(CarFault carFault, Operator operator);

	/**
	 * 生成主键
	 * @return
	 */
	public String generatePK();
	
		/**
	 * 为CarFault对象设置默认值
	 * @param obj
	 */
	public void fillDefaultValues(CarFault obj);
		
}
