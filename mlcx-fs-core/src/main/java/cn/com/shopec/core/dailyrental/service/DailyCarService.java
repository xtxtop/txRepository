package cn.com.shopec.core.dailyrental.service;

import java.util.List;

import cn.com.shopec.common.Operator;
import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.core.common.BaseService;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.dailyrental.model.DailyCar;

/**
 * DailyCar 服务接口类
 */
public interface DailyCarService extends BaseService {

	/**
	 * 根据查询条件，查询并返回DailyCar的列表
	 * @param q 查询条件
	 * @return
	 */
	public List<DailyCar> getDailyCarList(Query q);
	
	/**
	 * 根据查询条件，分页查询并返回DailyCar的分页结果
	 * @param q 查询条件
	 * @return
	 */
	public PageFinder<DailyCar> getDailyCarPagedList(Query q);
	
	/**
	 * 根据主键，查询并返回一个DailyCar对象
	 * @param id 主键id
	 * @return
	 */
	public DailyCar getDailyCar(String id);

	/**
	 * 根据主键数组，查询并返回一组DailyCar对象
	 * @param ids 主键数组，数组中的主键值应当无重复值，如有重复值时，则返回的列表长度可能小于传入的数组长度
	 * @return
	 */
	public List<DailyCar> getDailyCarByIds(String[] ids);
	
	/**
	 * 根据主键，删除特定的DailyCar记录
	 * @param id 主键id
	 * @param operator 操作人对象
	 * @return
	 */
	public ResultInfo<DailyCar> delDailyCar(String id, Operator operator);
	
	/**
	 * 新增一条DailyCar记录，执行成功后传入对象及返回对象的主键属性值不为null
	 * @param dailyCar 新增的DailyCar数据（如果无特殊需求，新增对象的主键值请保留为null）
	 * @param operator 操作人对象
	 * @return
	 */
	public ResultInfo<DailyCar> addDailyCar(DailyCar dailyCar, Operator operator);
	
	/**
	 * 根据主键，更新一条DailyCar记录
	 * @param dailyCar 更新的DailyCar数据，且其主键不能为空
	 * @param operator 操作人对象
	 * @return
	 */
	public ResultInfo<DailyCar> updateDailyCar(DailyCar dailyCar, Operator operator);
	
	/**
	 * 根据车型，商家，车牌修改车辆信息
	 * @param dailyCar 更新的DailyCar数据，且其主键不能为空
	 * @param operator 操作人对象
	 * @return
	 */
	public ResultInfo<DailyCar> updateDailyCarByPlateNo(DailyCar dailyCar, Operator operator);

	/**
	 * 生成主键
	 * @return
	 */
	public String generatePK();
	
		/**
	 * 为DailyCar对象设置默认值
	 * @param obj
	 */
	public void fillDefaultValues(DailyCar obj);
	
	/**
	 * 根据车牌号获取车辆
	 * @param carPlateNo
	 * @return
	 */
	public DailyCar getDailyCarByCarPlateNo(String carPlateNo);
		
	public int countDailyCar(Query q);
}
