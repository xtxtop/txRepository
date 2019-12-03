package cn.com.shopec.core.monitor.service;

import java.util.List;
import java.util.Map;

import cn.com.shopec.common.Operator;
import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.core.common.BaseService;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.monitor.model.Warning;

/**
 * 警告表 服务接口类
 */
public interface WarningService extends BaseService {

	/**
	 * 根据查询条件，查询并返回Warning的列表
	 * @param q 查询条件
	 * @return
	 */
	public List<Warning> getWarningList(Query q);
	
	/**
	 * 根据查询条件，分页查询并返回Warning的分页结果
	 * @param q 查询条件
	 * @return
	 */
	public PageFinder<Warning> getWarningPagedList(Query q);
	
	/**
	 * 根据主键，查询并返回一个Warning对象
	 * @param id 主键id
	 * @return
	 */
	public Warning getWarning(String id);

	/**
	 * 根据主键数组，查询并返回一组Warning对象
	 * @param ids 主键数组，数组中的主键值应当无重复值，如有重复值时，则返回的列表长度可能小于传入的数组长度
	 * @return
	 */
	public List<Warning> getWarningByIds(String[] ids);
	
	/**
	 * 根据车辆编号，查询并返回一组Warning对象
	 * @param carNos 主键数组，数组中的主键值应当无重复值，如有重复值时，则返回的列表长度可能小于传入的数组长度
	 * @return
	 */
	public List<Warning> getWarningByCarNos(String[] carNos);
	
	/**
	 * 根据车辆编号，查询并返回Warning对象
	 * @param ids 主键数组，数组中的主键值应当无重复值，如有重复值时，则返回的列表长度可能小于传入的数组长度
	 * @return
	 */
	public Warning getWarningByCarNo(String carNo);
	/**
	 * 根据主键，删除特定的Warning记录
	 * @param id 主键id
	 * @param operator 操作人对象
	 * @return
	 */
	public ResultInfo<Warning> delWarning(String id, Operator operator);
	
	/**
	 * 新增一条Warning记录，执行成功后传入对象及返回对象的主键属性值不为null
	 * @param warning 新增的Warning数据（如果无特殊需求，新增对象的主键值请保留为null）
	 * @param operator 操作人对象
	 * @return
	 */
	public ResultInfo<Warning> addWarning(Warning warning, Operator operator);
	
	/** 
	 * 根据主键，更新一条Warning记录
	 * @param warning 更新的Warning数据，且其主键不能为空
	 * @param operator 操作人对象
	 * @return
	 */
	public ResultInfo<Warning> updateWarning(Warning warning, Operator operator);

	/**
	 * 生成主键
	 * @return
	 */
	public String generatePK();
	
		/**
	 * 为Warning对象设置默认值
	 * @param obj
	 */
	public void fillDefaultValues(Warning obj);

	public ResultInfo<Warning> closeWarning(Warning warning, Operator operator);
	
	/**
	 * 
	 * @param startTime 开始时间
	 * @param endTime	结束时间
	 * @return
	 */
	public List<Map<String, Object>> getRealTimeWarning(String startTime,String endTime);

	public Integer getTodoIndexValue();

	public Long count(Query q);

	public Map<String, Object> getWarningCountMap();
	
	/**
	 * 获取近10天的告警数
	 * 
	 * @return
	 */
	Map<String, Object> getWarningDay10CountMap();

}
