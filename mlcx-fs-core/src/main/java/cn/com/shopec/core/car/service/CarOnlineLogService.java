package cn.com.shopec.core.car.service;

import java.util.List;
import java.util.Map;

import cn.com.shopec.common.Operator;
import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.core.car.model.CarOnlineLog;
import cn.com.shopec.core.common.BaseService;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;


/**
 * CarOnlineLog 服务接口类
 */
public interface CarOnlineLogService extends BaseService {

	/**
	 * 根据查询条件，查询并返回CarOnlineLog的列表
	 * @param q 查询条件
	 * @return
	 */
	public List<CarOnlineLog> getCarOnlineLogList(Query q);
	
	/**
	 * 根据查询条件，分页查询并返回CarOnlineLog的分页结果
	 * @param q 查询条件
	 * @return
	 */
	public PageFinder<CarOnlineLog> getCarOnlineLogPagedList(Query q);
	
	/**
	 * 根据主键，查询并返回一个CarOnlineLog对象
	 * @param id 主键id
	 * @return
	 */
	public CarOnlineLog getCarOnlineLog(String id);

	/**
	 * 根据主键数组，查询并返回一组CarOnlineLog对象
	 * @param ids 主键数组，数组中的主键值应当无重复值，如有重复值时，则返回的列表长度可能小于传入的数组长度
	 * @return
	 */
	public List<CarOnlineLog> getCarOnlineLogByIds(String[] ids);
	
	/**
	 * 根据主键，删除特定的CarOnlineLog记录
	 * @param id 主键id
	 * @param operator 操作人对象
	 * @return
	 */
	public ResultInfo<CarOnlineLog> delCarOnlineLog(String id, Operator operator);
	
	/**
	 * 新增一条CarOnlineLog记录，执行成功后传入对象及返回对象的主键属性值不为null
	 * @param carOnlineLog 新增的CarOnlineLog数据（如果无特殊需求，新增对象的主键值请保留为null）
	 * @param operator 操作人对象
	 * @return
	 */
	public ResultInfo<CarOnlineLog> addCarOnlineLog(CarOnlineLog carOnlineLog, Operator operator);
	
	/**
	 * 根据主键，更新一条CarOnlineLog记录
	 * @param carOnlineLog 更新的CarOnlineLog数据，且其主键不能为空
	 * @param operator 操作人对象
	 * @return
	 */
	public ResultInfo<CarOnlineLog> updateCarOnlineLog(CarOnlineLog carOnlineLog, Operator operator);

	/**
	 * 生成主键
	 * @return
	 */
	public String generatePK();
	
		/**
	 * 为CarOnlineLog对象设置默认值
	 * @param obj
	 */
	public void fillDefaultValues(CarOnlineLog obj);
	
	//根据查询条件，分页查询并返回CarOnlineLog的分页结果   并且查出操作人
	public PageFinder<CarOnlineLog> getCarOnlineLogPagedLists(Query q);
	
	/**
	 * 获取近10天的车辆上下线数
	 * 
	 * @return
	 */
	Map<String, Object> getCarLine10CountMap();
		
}
