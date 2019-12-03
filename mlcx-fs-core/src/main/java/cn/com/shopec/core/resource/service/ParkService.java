package cn.com.shopec.core.resource.service;

import java.util.List;
import java.util.Map;

import cn.com.shopec.common.Operator;
import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.core.car.model.CarOrParkVo;
import cn.com.shopec.core.car.vo.ParkDotVo;
import cn.com.shopec.core.common.BaseService;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.resource.model.Park;
import cn.com.shopec.core.resource.model.ParkDayVo;
import cn.com.shopec.core.resource.model.ParkLocation;
import cn.com.shopec.core.resource.model.ParkLocationNs;
import cn.com.shopec.core.resource.model.ParkRegion;
import cn.com.shopec.core.resource.model.WorkerRegion;
import cn.com.shopec.core.resource.vo.ParkCarNumVo;
import cn.com.shopec.core.search.model.ParkSearchCondition;

/**
 * Park 服务接口类
 */
public interface ParkService extends BaseService {

	/**
	 * 根据查询条件，查询并返回Park的列表
	 * 
	 * @param q
	 *            查询条件
	 * @return
	 */
	public List<Park> getParkList(Query q);

	/**
	 * 根据查询条件，查询并返回Park关联查询了车辆数的列表
	 * 
	 * @param q
	 *            查询条件
	 * @return
	 */
	public List<Park> getParkListAndCar(Query q);

	/**
	 * 根据查询条件，分页查询并返回Park的分页结果
	 * 
	 * @param q
	 *            查询条件
	 * @return
	 */
	public PageFinder<Park> getParkPagedList(Query q);

	/**
	 * @author lj 根据查询条件，分页查询并返回Park的分页结果
	 * @param q
	 *            查询条件
	 * @return
	 */
	public PageFinder<Park> getParkPageList(Query q);
	
	/**
	 * 根据加盟商编号查询关联的场站信息
	 */
	public List<Park> listPark(String franchiseeNo);

	/**
	 * 根据主键，查询并返回一个Park对象
	 * 
	 * @param id
	 *            主键id
	 * @return
	 */
	public Park getPark(String id);

	/**
	 * @author lj 根据主键，查询并返回一个Park对象
	 * @param id
	 *            主键id
	 * @return
	 */
	public Park toParkView(String id);

	/**
	 * 根据主键数组，查询并返回一组Park对象
	 * 
	 * @param ids
	 *            主键数组，数组中的主键值应当无重复值，如有重复值时，则返回的列表长度可能小于传入的数组长度
	 * @return
	 */
	public List<Park> getParkByIds(String[] ids);

	/**
	 * 根据主键，删除特定的Park记录
	 * 
	 * @param id
	 *            主键id
	 * @param operator
	 *            操作人对象
	 * @return
	 */
	public ResultInfo<Park> delPark(String id, Operator operator);

	/**
	 * 新增一条Park记录，执行成功后传入对象及返回对象的主键属性值不为null
	 * 
	 * @param park
	 *            新增的Park数据（如果无特殊需求，新增对象的主键值请保留为null）
	 * @param operator
	 *            操作人对象
	 * @return
	 */
	public ResultInfo<Park> addPark(Park park, Operator operator);

	/**
	 * 根据主键，更新一条Park记录
	 * 
	 * @param park
	 *            更新的Park数据，且其主键不能为空
	 * @param operator
	 *            操作人对象
	 * @return
	 */
	public ResultInfo<Park> updatePark(Park park, Operator operator);

	/**
	 * 改变启用关闭状态
	 * 
	 * @param park
	 * @param operator
	 * @return
	 */
	public ResultInfo<Park> changeParkState(Park park, Operator operator);

	/**
	 * 生成主键
	 * 
	 * @return
	 */
	public String generatePK();

	/**
	 * 为Park对象设置默认值
	 * 
	 * @param obj
	 */
	public void fillDefaultValues(Park obj);

	/**
	 * 定位查询周边场站
	 * 
	 * @param memberNo
	 */
	public List<Park> getParkListByAround(String longitude, String latitude, Double distance, String memberNo);

	/**
	 * 根据所输入地址显示该区域内的场站信息列表（取车）
	 * 
	 * @param memberNo
	 */
	public List<Park> getParkListByTake(Query q, String memberNo, String longitude, String latitude);

	/**
	 * 根据所输入地址显示该区域内的场站信息列表(还车)
	 */
	public List<Park> getParkListByReturn(Query q, String longitude, String latitude, String memberNo);

	/**
	 * 获取场站列表 (调度员接口--任务选择（点击前）) zln
	 */
	public List<Park> getParkListByWorker(String longitude, String latitude, Double distance);

	/**
	 * 根据经纬度计算两点之间的距离
	 */
	public Double getDistanceByPoint(String longitude, String latitude, String longitude2, String latitude2);

	/**
	 * 还车时，根据车辆的经纬度获取场站信息
	 */
	public ParkLocation getParkByReturnCarNo(String longitude, String latitude);

	public String getCurrentParkNoByCarLocation(Double longitude, Double latitude);

	/**
	 * 获取某一省市的所有区域的名称，场站数量
	 */
	public ResultInfo<List<ParkRegion>> getParkListByCityTake(Query q, String longitude, String latitude, Integer tag,
			String memberNo);

	/**
	 * gps坐标转百度坐标
	 */
	public ParkSearchCondition getBaiDuLocation(Double longitude, Double latitude);

	/**
	 * 获取某一省市的所有区域的名称，
	 */
	public ResultInfo<List<WorkerRegion>> getWorkerListByCityTake(String workerNo, Query q, String longitude,
			String latitude, Integer tag);

	/**
	 * 
	 * 根据所输入地址显示该区域内的场站信息列表(搜索)
	 */
	public List<Park> getSearchParkList(String longitude, String latitude, String address, String memberNo,
			Integer isPayment, Integer isLimit,String carPlateNo);

	/**
	 * 统计符合条件的场站数量
	 * 
	 * @param q
	 * @return
	 */
	public Long count(Query q);

	public void updateParkByChargerNumber(Park pa, Operator operator);

	/**
	 * 定位查询周围的场站和车辆（返回结果中get(car)为车辆，get(park)为场站）
	 * 
	 * @param longitude
	 * @param latitude
	 * @param distance
	 * @param memberNo
	 * @param seaTing 
	 * @return
	 */
	Map<String, List<Object>> getCarAndParkByAround(String longitude, String latitude, Double distance,Integer starRow,Integer rowNum,
			String memberNo, String seaTing,String carPlateNo);

	/**
	 * 定位查询周围的场站和车辆（返回距离最近的一个场站或车辆）
	 * 
	 * @param longitude
	 * @param latitude
	 * @param distance
	 * @param memberNo
	 * @param seaTing 
	 * @return
	 */
	CarOrParkVo getNearLimitOne(String longitude, String latitude, Double distance, String memberNo, String seaTing);

	/**
	 * 根据经纬度，获取最近的场站
	 * 
	 * @param longitude
	 * @param latitude
	 * @return
	 */
	public ParkLocationNs getParkByReturnNo(String longitude, String latitude);

	/**
	 * 根据经纬度和城市Id查找场站
	 */
	public List<ParkDayVo> getParkByCityId(String longitude, String latitude, String cityId);

	/**
	 * 调度员查看所属网点
	 */
	public List<ParkDotVo> dotPark(Query q);
	
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
	public List<ParkCarNumVo> getParkCarNumList(int userageStatus, int isAvailable, int removeStatus);
	
}