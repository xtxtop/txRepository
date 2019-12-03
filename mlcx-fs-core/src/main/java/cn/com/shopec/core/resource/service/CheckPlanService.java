package cn.com.shopec.core.resource.service;

import java.util.List;

import cn.com.shopec.common.Operator;
import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.core.common.BaseService;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.resource.model.CheckPlan;
import cn.com.shopec.core.resource.vo.CheckPlanDetailVo;
import cn.com.shopec.core.resource.vo.CpVo;

/**
 * 巡检计划表 服务接口类
 */
public interface CheckPlanService extends BaseService {

	/**
	 * 根据查询条件，查询并返回CheckPlan的列表
	 * 
	 * @param q
	 *            查询条件
	 * @return
	 */
	public List<CheckPlan> getCheckPlanList(Query q);

	/**
	 * 根据查询条件，分页查询并返回CheckPlan的分页结果
	 * 
	 * @param q
	 *            查询条件
	 * @return
	 */
	public PageFinder<CheckPlan> getCheckPlanPagedList(Query q);

	/**
	 * 根据主键，查询并返回一个CheckPlan对象
	 * 
	 * @param id
	 *            主键id
	 * @return
	 */
	public CheckPlan getCheckPlan(String id);

	/**
	 * 根据主键数组，查询并返回一组CheckPlan对象
	 * 
	 * @param ids
	 *            主键数组，数组中的主键值应当无重复值，如有重复值时，则返回的列表长度可能小于传入的数组长度
	 * @return
	 */
	public List<CheckPlan> getCheckPlanByIds(String[] ids);

	/**
	 * 根据主键，删除特定的CheckPlan记录
	 * 
	 * @param id
	 *            主键id
	 * @param operator
	 *            操作人对象
	 * @return
	 */
	public ResultInfo<CheckPlan> delCheckPlan(String id, Operator operator);

	/**
	 * 新增一条CheckPlan记录，执行成功后传入对象及返回对象的主键属性值不为null
	 * 
	 * @param checkPlan
	 *            新增的CheckPlan数据（如果无特殊需求，新增对象的主键值请保留为null）
	 * @param operator
	 *            操作人对象
	 * @return
	 */
	public ResultInfo<CheckPlan> addCheckPlan(CheckPlan checkPlan, Operator operator);

	/**
	 * 根据主键，更新一条CheckPlan记录
	 * 
	 * @param checkPlan
	 *            更新的CheckPlan数据，且其主键不能为空
	 * @param operator
	 *            操作人对象
	 * @return
	 */
	public ResultInfo<CheckPlan> updateCheckPlan(CheckPlan checkPlan, Operator operator);

	/**
	 * 生成主键
	 * 
	 * @return
	 */
	public String generatePK();

	/**
	 * 为CheckPlan对象设置默认值
	 * 
	 * @param obj
	 */
	public void fillDefaultValues(CheckPlan obj);

	/**
	 * 获取调度员的巡检列表信息
	 * 
	 * @param planStatus
	 */
	public List<CheckPlan> getCheckPlanListByWorkerNo(String workerNo, Integer planStatus);

	/**
	 * 调度端查询巡检任务
	 */
	public List<CpVo> queryAllNs(Query q);

	/**
	 * 调度巡检详情
	 */
	public CheckPlanDetailVo getCheckPlanDetails(String longitude, String latitude, String checkPlanNo);

}
