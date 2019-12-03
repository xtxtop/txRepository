package cn.com.shopec.core.scheduling.dao;

import java.util.List;

import cn.com.shopec.core.common.BaseDao;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.scheduling.model.Worker;

/**
 * 调度员表 数据库处理类
 */
public interface WorkerDao extends BaseDao<Worker, String> {
	/**
	 * 登录（工号，密码）@author zln
	 */
	Worker getWorkerByEmpNo(String empNo);

	/**
	 * 获取调度员基本信息 @author zln
	 */
	// Worker getWorkerBasicInfo(String workerNo);
	/**
	 * 根据token查找一条调度员信息
	 */
	public Worker getWorkerByToken(String token);

	/**
	 * 根据姓名或工号查询调度员
	 */
	public List<Worker> getWorkerNs(Query q);

	/**
	 * 查找调度员位置
	 */
	public List<Worker> findPositionReporting(Query q);
}
