package cn.com.shopec.core.resource.dao;

import java.util.List;

import cn.com.shopec.core.car.vo.ParkDotVo;
import cn.com.shopec.core.common.BaseDao;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.resource.model.Park;
import cn.com.shopec.core.resource.model.ParkDayVo;
import cn.com.shopec.core.resource.model.ParkLocation;
import cn.com.shopec.core.resource.model.ParkLocationNs;
import cn.com.shopec.core.resource.model.ParkRegion;
import cn.com.shopec.core.resource.model.WorkerRegion;
import cn.com.shopec.core.resource.vo.ParkCarNumVo;

/**
 * Park 数据库处理类
 */
public interface ParkDao extends BaseDao<Park, String> {
	/**
	 * @author lj 后台 根据条件查询数据
	 * @param q
	 * @return
	 */
	public List<Park> parkPageList(Query q);

	/**
	 * @author lj 后台 根据条件查询数据条件
	 * @param q
	 * @return
	 */
	public Long parkCount(Query q);

	/**
	 * @author zln 根据定位信息获取一定范围内的周边场站列表(未登录和普通会员)
	 */
	public List<Park> getParkListByAround(String longitude, String latitude, Double distance);

	/**
	 * @author zln 根据定位信息获取一定范围内的周边场站列表（集团会员）
	 */
	public List<Park> getParkListByAround1(String longitude, String latitude, Double distance, String companyNo);

	/**
	 * @author zln 按区域选择取车点列表
	 */
	public List<Park> getParkListByTake(Query q);

	/**
	 * @author zln 按区域选择还车点列表
	 */
	public List<Park> getParkListByReturn(Query q);

	/**
	 * @author zln 调度员接口（任务选择（点击前）--zln）
	 */
	public List<Park> getParkListByWorker(String longitude, String latitude, Double distance);

	/**
	 * @author zln 获取两点之间的距离
	 */
	public Double getDistanceByPoint(String longitude, String latitude, String longitude2, String latitude2);

	/**
	 * 还车时根据车辆经纬度获取场站信息
	 */
	public ParkLocation getParkByReturnCarNo(String longitude, String latitude);

	/**
	 * 某一省市的所有有场站的区域，可用场站数目
	 */
	public List<ParkRegion> getParkListByCityTake(Query q);

	/**
	 * 某一省市的所有有场站的区域，可用场站数目,直辖市
	 */
	public List<ParkRegion> getParkListByCityTake1(Query q);

	/**
	 * 某一省市的所有有场站的区域，可用场站数目,直辖市
	 */
	public List<WorkerRegion> getWorkerListByCityTake1(String workerId);

	/**
	 * 某一省市的所有有场站的区域，可用场站数目
	 */
	public List<WorkerRegion> getWorkerListByCityTake(String workerId);

	public List<Park> getParkListByTakePT(Query q);

	public List<Park> getParkListByTakeJT(Query q);

	public List<Park> getParkListByReturnPT(Query q);

	public List<Park> getParkListByReturnJT(Query q);

	public List<Park> queryAllAndCar(Query q);

	public List<Park> getParkListBySearch(Query q);

	public int updateByBusiness(Park park);

	public ParkLocationNs getParkByReturnNo(String longitude, String latitude);

	/**
	 * 根据经纬度和城市Id查找场站
	 */
	public List<ParkDayVo> getParkByCityId(String longitude, String latitude, String cityId);

	/**
	 * 调度员查看所属网点
	 */
	public List<ParkDotVo> dotPark(Query q);
	
	public Integer countLotParkingSpace();
	/**
	 * 查询加盟商关联的场站信息
	 */
	public List<Park> listPark(String franchissNo);

	/**
	 * 获取监控场站列表
	 * 
	 * @param q
	 * @return
	 */
	public List<Park> getMonitorParkList(Query q);
	
	/**
	 * 分组查询场站车辆数
	 */
	List<ParkCarNumVo> getParkCarNumList(int userageStatus, int isAvailable, int removeStatus);
	
}
