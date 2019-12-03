package cn.com.shopec.core.resource.dao;

import java.util.List;

import cn.com.shopec.core.common.BaseDao;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.resource.model.CheckResult;
import cn.com.shopec.core.resource.vo.CheckPlanResultNsVo;

/**
 * 巡检结果表 数据库处理类
 */
public interface CheckResultDao extends BaseDao<CheckResult, String> {
	/**
	 * 获取巡检计划的巡检结果列表 @author zln
	 */
	List<CheckResult> getCheckResultListByCheckPlanNo(String checkPlanNo);

	/**
	 * 调度查看巡检结果
	 */
	public List<CheckPlanResultNsVo> getCheckResultNs(Query q);

}
