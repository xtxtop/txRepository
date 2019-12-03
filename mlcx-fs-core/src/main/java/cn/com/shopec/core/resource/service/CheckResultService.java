package cn.com.shopec.core.resource.service;

import java.util.List;

import cn.com.shopec.common.Operator;
import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.core.common.BaseService;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.resource.model.CheckResult;
import cn.com.shopec.core.resource.vo.CheckPlanResultNsVo;

/**
 * 巡检结果表 服务接口类
 */
public interface CheckResultService extends BaseService {

	/**
	 * 根据查询条件，查询并返回CheckResult的列表
	 * 
	 * @param q
	 *            查询条件
	 * @return
	 */
	public List<CheckResult> getCheckResultList(Query q);

	/**
	 * 根据查询条件，分页查询并返回CheckResult的分页结果
	 * 
	 * @param q
	 *            查询条件
	 * @return
	 */
	public PageFinder<CheckResult> getCheckResultPagedList(Query q);

	/**
	 * 根据主键，查询并返回一个CheckResult对象
	 * 
	 * @param id
	 *            主键id
	 * @return
	 */
	public CheckResult getCheckResult(String id);

	/**
	 * 根据主键数组，查询并返回一组CheckResult对象
	 * 
	 * @param ids
	 *            主键数组，数组中的主键值应当无重复值，如有重复值时，则返回的列表长度可能小于传入的数组长度
	 * @return
	 */
	public List<CheckResult> getCheckResultByIds(String[] ids);

	/**
	 * 根据主键，删除特定的CheckResult记录
	 * 
	 * @param id
	 *            主键id
	 * @param operator
	 *            操作人对象
	 * @return
	 */
	public ResultInfo<CheckResult> delCheckResult(String id, Operator operator);

	/**
	 * 新增一条CheckResult记录，执行成功后传入对象及返回对象的主键属性值不为null
	 * 
	 * @param checkResult
	 *            新增的CheckResult数据（如果无特殊需求，新增对象的主键值请保留为null）
	 * @param operator
	 *            操作人对象
	 * @return
	 */
	public ResultInfo<CheckResult> addCheckResult(CheckResult checkResult, Operator operator);

	/**
	 * 根据主键，更新一条CheckResult记录
	 * 
	 * @param checkResult
	 *            更新的CheckResult数据，且其主键不能为空
	 * @param operator
	 *            操作人对象
	 * @return
	 */
	public ResultInfo<CheckResult> updateCheckResult(CheckResult checkResult, Operator operator);

	/**
	 * 生成主键
	 * 
	 * @return
	 */
	public String generatePK();

	/**
	 * 为CheckResult对象设置默认值
	 * 
	 * @param obj
	 */
	public void fillDefaultValues(CheckResult obj);

	/**
	 * 获取巡检计划的巡检结果列表 @author zln
	 */
	public List<CheckResult> getCheckResultListByCheckPlanNo(String checkPlanNo);

	/**
	 * 调度查看巡检结果
	 */
	public List<CheckPlanResultNsVo> getCheckResultNs(Query q);

}
