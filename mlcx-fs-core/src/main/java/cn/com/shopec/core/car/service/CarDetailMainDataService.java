package cn.com.shopec.core.car.service;

import java.util.List;

import cn.com.shopec.common.Operator;
import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.core.car.model.CarDetailMainData;
import cn.com.shopec.core.common.BaseService;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;


/**
 * CarDetailMainData 服务接口类
 */
public interface CarDetailMainDataService extends BaseService {

	/**
	 * 根据查询条件，查询并返回CarDetailMainData的列表
	 * @param q 查询条件
	 * @return
	 */
	public List<CarDetailMainData> getCarDetailMainDataList(Query q);
	
	/**
	 * 根据查询条件，分页查询并返回CarDetailMainData的分页结果
	 * @param q 查询条件
	 * @return
	 */
	public PageFinder<CarDetailMainData> getCarDetailMainDataPagedList(Query q);
	
	/**
	 * 根据主键，查询并返回一个CarDetailMainData对象
	 * @param id 主键id
	 * @return
	 */
	public CarDetailMainData getCarDetailMainData(String id);

	/**
	 * 根据主键数组，查询并返回一组CarDetailMainData对象
	 * @param ids 主键数组，数组中的主键值应当无重复值，如有重复值时，则返回的列表长度可能小于传入的数组长度
	 * @return
	 */
	public List<CarDetailMainData> getCarDetailMainDataByIds(String[] ids);
	
	/**
	 * 根据主键，删除特定的CarDetailMainData记录
	 * @param id 主键id
	 * @param operator 操作人对象
	 * @return
	 */
	public ResultInfo<CarDetailMainData> delCarDetailMainData(String id, Operator operator);
	
	/**
	 * 新增一条CarDetailMainData记录，执行成功后传入对象及返回对象的主键属性值不为null
	 * @param carDetailMainData 新增的CarDetailMainData数据（如果无特殊需求，新增对象的主键值请保留为null）
	 * @param operator 操作人对象
	 * @return
	 */
	public ResultInfo<CarDetailMainData> addCarDetailMainData(CarDetailMainData carDetailMainData, Operator operator);
	
	/**
	 * 根据主键，更新一条CarDetailMainData记录
	 * @param carDetailMainData 更新的CarDetailMainData数据，且其主键不能为空
	 * @param operator 操作人对象
	 * @return
	 */
	public ResultInfo<CarDetailMainData> updateCarDetailMainData(CarDetailMainData carDetailMainData, Operator operator);

	/**
	 * 生成主键
	 * @return
	 */
	public String generatePK();
	
		/**
	 * 为CarDetailMainData对象设置默认值
	 * @param obj
	 */
	public void fillDefaultValues(CarDetailMainData obj);
		
	/**
	 * 判断一个设备序号是否已经存在记录
	 * @param deviceSn
	 * @return
	 */
	public boolean isExists(String deviceSn);
	
	/**
	 * 根据设备序号，取最近更新时间信息
	 * @param deviceSn 
	 * @return
	 */
	public CarDetailMainData getLastReportingTimeByDeviceSn(String deviceSn);
}
