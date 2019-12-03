package cn.com.shopec.core.scheduling.service;

import java.util.List;

import cn.com.shopec.common.Operator;
import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.core.common.BaseService;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.scheduling.model.Worker;
import cn.com.shopec.core.scheduling.model.WorkerOrder;

/**
 * 调度员表 服务接口类
 */
public interface WorkerService extends BaseService {

	/**
	 * 根据查询条件，查询并返回Worker的列表
	 * 
	 * @param q
	 *            查询条件
	 * @return
	 */
	public List<Worker> getWorkerList(Query q);

	/**
	 * 根据查询条件，分页查询并返回Worker的分页结果
	 * 
	 * @param q
	 *            查询条件
	 * @return
	 */
	public PageFinder<Worker> getWorkerPagedList(Query q);

	/**
	 * 根据主键，查询并返回一个Worker对象
	 * 
	 * @param id
	 *            主键id
	 * @return
	 */
	public Worker getWorker(String id);

	/**
	 * 根据主键数组，查询并返回一组Worker对象
	 * 
	 * @param ids
	 *            主键数组，数组中的主键值应当无重复值，如有重复值时，则返回的列表长度可能小于传入的数组长度
	 * @return
	 */
	public List<Worker> getWorkerByIds(String[] ids);

	/**
	 * 根据主键，删除特定的Worker记录
	 * 
	 * @param id
	 *            主键id
	 * @param operator
	 *            操作人对象
	 * @return
	 */
	public ResultInfo<Worker> delWorker(String id, Operator operator);

	/**
	 * 新增一条Worker记录，执行成功后传入对象及返回对象的主键属性值不为null
	 * 
	 * @param worker
	 *            新增的Worker数据（如果无特殊需求，新增对象的主键值请保留为null）
	 * @param operator
	 *            操作人对象
	 * @return
	 */
	public ResultInfo<Worker> addWorker(Worker worker, Operator operator);

	/**
	 * 根据主键，更新一条Worker记录
	 * 
	 * @param worker
	 *            更新的Worker数据，且其主键不能为空
	 * @param operator
	 *            操作人对象
	 * @return
	 */
	public ResultInfo<Worker> updateWorker(Worker worker, Operator operator);

	/**
	 * 生成主键
	 * 
	 * @return
	 */
	public String generatePK();

	/**
	 * 为Worker对象设置默认值
	 * 
	 * @param obj
	 */
	public void fillDefaultValues(Worker obj);

	/**
	 * 登录（工号，密码）@author zln
	 */
	public Worker getWorkerByEmpNo(String empNo);

	/**
	 * 获取调度员基本信息
	 */
	public Worker getWorkerBasicInfo(String workerNo);

	/**
	 * 根据token查找一条调度员信息
	 */
	public Worker getWorkerByToken(String token);

	/**
	 * 根据姓名或工号查询调度员
	 */
	public List<Worker> getWorkerNs(Query q);
	
	/**
	 * 根据调度员工号查询该调度员的调度任务 
	 */
	public PageFinder<WorkerOrder> pageListWorkerJob(Query q);

}
