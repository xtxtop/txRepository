package cn.com.shopec.core.scheduling.dao;

import cn.com.shopec.core.common.BaseDao;
import cn.com.shopec.core.scheduling.model.WorkerDot;

/**
 * 调度员网点表 数据库处理类
 */
public interface WorkerDotDao extends BaseDao<WorkerDot, String> {
	/**
	 * 根据调度员ID删除网点
	 * 
	 */
	public void deleteByWorkerId(String workerId);

}
