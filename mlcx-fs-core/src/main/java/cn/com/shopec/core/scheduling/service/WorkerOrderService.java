package cn.com.shopec.core.scheduling.service;

import java.util.Date;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import cn.com.shopec.common.Operator;
import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.core.common.BaseService;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.resource.model.Park;
import cn.com.shopec.core.resource.vo.TaskVo;
import cn.com.shopec.core.scheduling.model.WorkerOrder;
import cn.com.shopec.core.scheduling.vo.WorkerOrderDetailsVo;

/**
 * WorkerOrder 服务接口类
 */
public interface WorkerOrderService extends BaseService {

	/**
	 * 根据查询条件，查询并返回WorkerOrder的列表
	 * 
	 * @param q
	 *            查询条件
	 * @return
	 */
	public List<WorkerOrder> getWorkerOrderList(Query q);

	/**
	 * 根据查询条件，分页查询并返回WorkerOrder的分页结果
	 * 
	 * @param q
	 *            查询条件
	 * @return
	 */
	public PageFinder<WorkerOrder> getWorkerOrderPagedList(Query q);

	/**
	 * 根据主键，查询并返回一个WorkerOrder对象
	 * 
	 * @param id
	 *            主键id
	 * @return
	 */
	public WorkerOrder getWorkerOrder(String id);

	/**
	 * 根据主键数组，查询并返回一组WorkerOrder对象
	 * 
	 * @param ids
	 *            主键数组，数组中的主键值应当无重复值，如有重复值时，则返回的列表长度可能小于传入的数组长度
	 * @return
	 */
	public List<WorkerOrder> getWorkerOrderByIds(String[] ids);

	/**
	 * 根据主键，删除特定的WorkerOrder记录
	 * 
	 * @param id
	 *            主键id
	 * @param operator
	 *            操作人对象
	 * @return
	 */
	public ResultInfo<WorkerOrder> delWorkerOrder(String id, Operator operator);

	/**
	 * 新增一条WorkerOrder记录，执行成功后传入对象及返回对象的主键属性值不为null
	 * 
	 * @param workerOrder
	 *            新增的WorkerOrder数据（如果无特殊需求，新增对象的主键值请保留为null）
	 * @param operator
	 *            操作人对象
	 * @return
	 */
	public ResultInfo<WorkerOrder> addWorkerOrder(WorkerOrder workerOrder, Operator operator);

	/**
	 * 添加调度单时先判断车辆使用状态是否成功
	 * 
	 * @param workerOrder
	 * @param operator
	 * @return
	 */
	public ResultInfo<WorkerOrder> addWorkerOrderAndJudgeCarStatus(WorkerOrder workerOrder, Operator operator);

	/**
	 * 根据主键，更新一条WorkerOrder记录
	 * 
	 * @param workerOrder
	 *            更新的WorkerOrder数据，且其主键不能为空
	 * @param operator
	 *            操作人对象
	 * @return
	 */
	public ResultInfo<WorkerOrder> updateWorkerOrder(WorkerOrder workerOrder, Operator operator);

	/**
	 * 生成主键
	 * 
	 * @return
	 */
	public String generatePK();

	/**
	 * 为WorkerOrder对象设置默认值
	 * 
	 * @param obj
	 */
	public void fillDefaultValues(WorkerOrder obj);

	/**
	 * 获取任务列表信息 @author zln
	 */
	public List<WorkerOrder> getWorkerOrderListByWorkerNo(String workerNo, String type);

	/**
	 * 调度员的网点任务列表
	 */
	public List<Park> getWorkerOrderListByParkAddr(String workerNo, String addrRegion1Name, String addrRegion2Name,
			String addrRegion3Name);

	/**
	 * 获取场站的实时任务量
	 */
	public List<WorkerOrder> getWorkerOrderNumByParkNo(String parkNo, String workerId);

	/**
	 * 判断车辆是否存在下发的调度单中 @author zln 调度员
	 */
	public Long carExsitWorkerOrder(String carNo);

	/**
	 * 下调度单（添加调度单，添加用车记录）
	 * 
	 * @return
	 */
	public ResultInfo<WorkerOrder> workerParkCarChoose(String carNo, Integer type, String workerNo, Operator operator1,
			String sendReason, String sendReasonPicUrl1, String sendReasonPicUrl2, String sendReasonPicUrl3);

	/**
	 * 任务完成 zln
	 */
	public ResultInfo<WorkerOrder> workerOrderFinish(String workerOrderNo, String workerNo, Operator operator);

	/**
	 * 导入调度工单
	 */
	public ResultInfo<WorkerOrder> importWorkerOrder(MultipartFile file, Operator operator) throws Exception;

	/**
	 * 根据查询条件，统计
	 * 
	 * @param q
	 * @return
	 */
	public long countWorkerOrder(Query q);

	/**
	 * 根据车号，记录调度单开始执行的时间，并同时调度单状态改为“调度中”
	 * 
	 * @param carNo
	 * @param datetime
	 * @return
	 */
	public ResultInfo<String> startWorkerOrderByCarNo(String carNo, Date datetime);

	/**
	 * 查询当前调度员是否存在调度中的调度单
	 * 
	 * @param workerNo
	 * @return
	 */
	public ResultInfo<WorkerOrder> exsitWorkerOrder(String workerNo);

	/**
	 * 是否是当前调度员的调度用车
	 * 
	 * @param workerNo
	 * @param parkNo
	 * @return
	 */
	public ResultInfo<WorkerOrder> esUserWorkerOrder(String workerNo, String carNo);

	/**
	 * 根据查询条件，统计驶出
	 * 
	 * @param q
	 * @return
	 */
	public Long countWorkerOrderPark(Query q);

	/**
	 * 根据车辆编号得到当前最新的调度单
	 */
	public WorkerOrder getWorkerOrderNowByCarNo(String carNo);

	// 后台统计 调度工单待审核的总和
	public Integer getTodoIndexValue();

	/**
	 * 调度多条件查询调度任务信息
	 */
	public List<TaskVo> queryTask(Query q);

	/**
	 * 调度查找调度任务详情
	 */
	public WorkerOrderDetailsVo getWorkerOrderDetail(String longitude, String latitude, String workerOrderNo);

	/**
	 * 调度员查看任务
	 */
	public List<TaskVo> launchTask(Query q);
}
