package cn.com.shopec.core.car.service;

import java.util.Date;
import java.util.List;

import cn.com.shopec.common.Operator;
import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.core.common.BaseService;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.car.model.CarTrip;

/**
 * 车辆行程表 服务接口类
 */
public interface CarTripService extends BaseService {

	/**
	 * 根据查询条件，查询并返回CarTrip的列表
	 * @param q 查询条件
	 * @return
	 */
	public List<CarTrip> getCarTripList(Query q);
	
	/**
	 * 根据查询条件，分页查询并返回CarTrip的分页结果
	 * @param q 查询条件
	 * @return
	 */
	public PageFinder<CarTrip> getCarTripPagedList(Query q);
	
	/**
	 * 根据主键，查询并返回一个CarTrip对象
	 * @param id 主键id
	 * @return
	 */
	public CarTrip getCarTrip(String id);

	/**
	 * 根据主键数组，查询并返回一组CarTrip对象
	 * @param ids 主键数组，数组中的主键值应当无重复值，如有重复值时，则返回的列表长度可能小于传入的数组长度
	 * @return
	 */
	public List<CarTrip> getCarTripByIds(String[] ids);
	
	/**
	 * 根据主键，删除特定的CarTrip记录
	 * @param id 主键id
	 * @param operator 操作人对象
	 * @return
	 */
	public ResultInfo<CarTrip> delCarTrip(String id, Operator operator);
	
	/**
	 * 新增一条CarTrip记录，执行成功后传入对象及返回对象的主键属性值不为null
	 * @param carTrip 新增的CarTrip数据（如果无特殊需求，新增对象的主键值请保留为null）
	 * @param operator 操作人对象
	 * @return
	 */
	public ResultInfo<CarTrip> addCarTrip(CarTrip carTrip, Operator operator);
	
	/**
	 * 根据主键，更新一条CarTrip记录
	 * @param carTrip 更新的CarTrip数据，且其主键不能为空
	 * @param operator 操作人对象
	 * @return
	 */
	public ResultInfo<CarTrip> updateCarTrip(CarTrip carTrip, Operator operator);

	/**
	 * 生成主键
	 * @return
	 */
	public String generatePK();
	
		/**
	 * 为CarTrip对象设置默认值
	 * @param obj
	 */
	public void fillDefaultValues(CarTrip obj);
/**
 * 订单总里程
 * */
	public Double getMileageByOrderTime(String carNo, Date startBillingTime, Date finishTime);
		
}
