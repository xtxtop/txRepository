package cn.com.shopec.core.scheduling.service;

import java.util.List;

import cn.com.shopec.common.Operator;
import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.core.common.BaseService;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.scheduling.model.WorkerDot;

/**
 * 调度员网点表 服务接口类
 */
public interface WorkerDotService extends BaseService {

	/**
	 * 根据查询条件，查询并返回WorkerDot的列表
	 * 
	 * @param q
	 *            查询条件
	 * @return
	 */
	public List<WorkerDot> getWorkerDotList(Query q);

	/**
	 * 根据查询条件，分页查询并返回WorkerDot的分页结果
	 * 
	 * @param q
	 *            查询条件
	 * @return
	 */
	public PageFinder<WorkerDot> getWorkerDotPagedList(Query q);

	/**
	 * 根据主键，查询并返回一个WorkerDot对象
	 * 
	 * @param id
	 *            主键id
	 * @return
	 */
	public WorkerDot getWorkerDot(String id);

	/**
	 * 根据主键数组，查询并返回一组WorkerDot对象
	 * 
	 * @param ids
	 *            主键数组，数组中的主键值应当无重复值，如有重复值时，则返回的列表长度可能小于传入的数组长度
	 * @return
	 */
	public List<WorkerDot> getWorkerDotByIds(String[] ids);

	/**
	 * 根据主键，删除特定的WorkerDot记录
	 * 
	 * @param id
	 *            主键id
	 * @param operator
	 *            操作人对象
	 * @return
	 */
	public ResultInfo<WorkerDot> delWorkerDot(String id, Operator operator);

	/**
	 * 新增一条WorkerDot记录，执行成功后传入对象及返回对象的主键属性值不为null
	 * 
	 * @param workerDot
	 *            新增的WorkerDot数据（如果无特殊需求，新增对象的主键值请保留为null）
	 * @param operator
	 *            操作人对象
	 * @return
	 */
	public ResultInfo<WorkerDot> addWorkerDot(WorkerDot workerDot, Operator operator);

	/**
	 * 根据主键，更新一条WorkerDot记录
	 * 
	 * @param workerDot
	 *            更新的WorkerDot数据，且其主键不能为空
	 * @param operator
	 *            操作人对象
	 * @return
	 */
	public ResultInfo<WorkerDot> updateWorkerDot(WorkerDot workerDot, Operator operator);

	/**
	 * 生成主键
	 * 
	 * @return
	 */
	public String generatePK();

	/**
	 * 为WorkerDot对象设置默认值
	 * 
	 * @param obj
	 */
	public void fillDefaultValues(WorkerDot obj);

	/**
	 * 批量编辑调度员
	 */
	public ResultInfo<WorkerDot> editWorkerDot(String[] parkIds, String workerId, Operator operator);

}
