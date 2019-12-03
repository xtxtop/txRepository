package cn.com.shopec.core.resource.dao;

import java.util.List;

import cn.com.shopec.core.common.BaseDao;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.resource.model.CheckPlan;
import cn.com.shopec.core.resource.vo.CheckPlanDetailVo;
import cn.com.shopec.core.resource.vo.CpVo;

/**
 * 巡检计划表 数据库处理类
 */
public interface CheckPlanDao extends BaseDao<CheckPlan, String> {
	/**
	 * 获取调度员的巡检列表 @author zln
	 * 
	 * @param planStatus
	 */
	List<CheckPlan> getCheckPlanListByWorkerNo(String workerNo, Integer planStatus);

	/**
	 * 调度端查询巡检任务
	 */
	public List<CpVo> queryAllNs(Query q);

	/**
	 * 调度巡检详情
	 */
	public CheckPlanDetailVo getCheckPlanDetails(String longitude, String latitude, String checkPlanNo);

}
