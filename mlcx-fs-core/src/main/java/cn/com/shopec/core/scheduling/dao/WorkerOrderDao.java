package cn.com.shopec.core.scheduling.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.com.shopec.core.common.BaseDao;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.resource.model.ParkVOAround;
import cn.com.shopec.core.resource.vo.TaskVo;
import cn.com.shopec.core.scheduling.model.WorkerOrder;
import cn.com.shopec.core.scheduling.vo.WorkerOrderDetailsVo;

/**
 * WorkerOrder 数据库处理类
 */
public interface WorkerOrderDao extends BaseDao<WorkerOrder, String> {
	/**
	 * 获取任务列表 zln
	 */
	List<WorkerOrder> getWorkerOrderListByWorkerNo(String workerNo, String type);

	/**
	 * 调度员 网点任务列表 zln
	 */
	List<WorkerOrder> getWorkerOrderListByParkAddr(@Param("workerNo") String workerNo,
			@Param("addrRegion1Name") String addrRegion1Name, @Param("addrRegion2Name") String addrRegion2Name,
			@Param("addrRegion3Name") String addrRegion3Name);

	List<ParkVOAround> getWorkerOrderListNum(@Param("workerNo") String workerNo,
			@Param("addrRegion1Name") String addrRegion1Name, @Param("addrRegion2Name") String addrRegion2Name,
			@Param("addrRegion3Name") String addrRegion3Name);

	/**
	 * 获取场站的实时任务量
	 */
	List<WorkerOrder> getWorkerOrderNumByParkNo(String parkNo, String workerId);

	/**
	 * 调度员 车辆是否存在于下发的调度单中 zln
	 */
	Long carExsitWorkerOrder(String carNo);

	/**
	 * 根据车号，记录调度单开始执行的时间，并同时调度单状态改为“调度中”
	 * 
	 * @param carNo
	 * @param datetime
	 * @return
	 */
	public int startWorkerOrderByCarNo(String carNo, Date datetime);

	/**
	 * 当前调度员，是否还有调度中订单调度单
	 * 
	 * @param workerNo
	 * @return
	 */
	public List<WorkerOrder> exsitWorkerOrder(String workerNo);

	public List<WorkerOrder> esUserWorkerOrder(String workerNo, String carNo);

	/**
	 * 根据查询条件，统计驶出
	 * 
	 * @param q
	 * @return
	 */
	public Long countWorkerOrderPark(Query q);

	/**
	 * 根据车辆编号得到当前最新的调度单信息
	 */
	WorkerOrder getWorkerOrderNowByCarNo(String carNo);

	WorkerOrder getLatelyWorkerOrderByCarNo(String carNo);

	/**
	 * 根据车辆编号查询车辆据现在时间最近的一笔调度单（状态为已完成或者已取消）
	 * 
	 * @param carNo
	 * @return
	 */
	public WorkerOrder getWorkerOrderNewestByCarNo(String carNo);

	public Integer countWorkerOrdersTodoIndexValue();

	/**
	 * 根据车牌号查找已下发（workOrderStatus=1）的调度单
	 * 
	 * @param carNo
	 * @return
	 */
	WorkerOrder getWorderOrderByCarNo(String carNo);

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
	

	public Map<String, Object> countWorkerOrder();
	
	/**
	 * 根据调度员工号查询该调度员任务
	 */
	List<WorkerOrder> listWorkerOrder(Query q);
	long countWokerOrder(Query q);
}
