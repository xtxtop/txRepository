package cn.com.shopec.core.car.dao;

import java.util.List;

import cn.com.shopec.core.car.model.CarStatus;
import cn.com.shopec.core.car.vo.CarWkVo;
import cn.com.shopec.core.common.BaseDao;
import cn.com.shopec.core.common.Query;

/**
 * CarStatus 数据库处理类
 */
public interface CarStatusDao extends BaseDao<CarStatus, String> {
	/**
	 * @author zln 根据终端编号查找车辆信息
	 */
	public CarStatus getByDeviceId(String deviceId);

	/**
	 * @author machao 后台 根据用车状态查询数据
	 * @param q
	 * @return
	 */
	public List<CarStatus> getCarStatusByUseStatus(Query q);

	/**
	 * @author lj 后台 根据条件查询数据
	 * @param q
	 * @return
	 */
	public List<CarStatus> carStatusPageList(Query q);

	/**
	 * @author lj 后台 根据条件查询数据条件
	 * @param q
	 * @return
	 */
	public Long carStatusCount(Query q);

	/**
	 * 统计数量
	 * 
	 * @param q
	 * @return
	 */
	public Long countParkStatus(Query q);

	/**
	 * 统计数量
	 * 
	 * @param q
	 * @return
	 */
	public Long countParkStatusUserage(Query q);

	/**
	 * 查询场站空闲空位数
	 * 
	 * @param q
	 * @return
	 */
	public List<CarStatus> getCarSpaceShortage(Query q);

	/**
	 * 查询不在场站的车辆信息
	 * 
	 * @param q
	 * @return
	 */
	public List<CarStatus> getCarNoPark(String longitude, String latitude, Double distance);

	/**
	 * 获取非订单车辆信息
	 */
	public List<CarStatus> getCarStatusListOutOrder();

	public List<CarStatus> getCarStatusListfree24(int hour1, int hour2);

	/**
	 * 根据车辆号，取得车辆状态表中，各上报时间字段的值
	 * 
	 * @param carNo
	 * @return
	 */
	public CarStatus getCarStatusReportingTime(String carNo);

	public List<CarStatus> getCarStatusListOutOrderW();

	/**
	 * 后台 根据条件查询数据 并导出
	 * 
	 * @param q
	 * @return
	 */
	public List<CarStatus> carStatusPageListForExport(Query q);

	public int updateCarUseStatusByCarNo(CarStatus carStatus);

	/**
	 * 调度查找附近的车
	 */
	public List<CarWkVo> nearbyCar(Query q);

	/**
	 * 网点停靠车辆
	 */
	public List<CarWkVo> stopCar(Query q);
	
	/**
	 * 低电量车辆
	 * 
	 * @param param
	 * @return
	 */
	public Integer countCarLowPower(Integer param);
	
	/**
	 * 断线车辆
	 * 
	 * @return
	 */
	public Integer countCarBrokenLine();
	
	/**
	 * 统计非订单用车
	 * 
	 * @return
	 */
	public Integer countNoOrderUseCar();
	
	/**
	 * 小电瓶低电
	 * 
	 * @param resetBluetoothNameUrl
	 * @return
	 */
	public Integer countCarMinLowPower(String resetBluetoothNameUrl);
	
	/**
	 * 根据车辆编号获取断线车辆个数（用来判断车辆是否在线）
	 * 
	 * @return
	 */
	public Integer getCarBrokenLineByCarNo(String carNo);

	public List<CarStatus> getLlocationParkNo();

	public Integer getDownpowers(Double powers);	
	
	/**
	 * 获取车辆监控列表
	 * 
	 * @param q
	 * @return
	 */
	public List<CarStatus> getCarMonitorList(Query q);
	
	/**
	 * 获取空闲车辆列表
	 * 
	 * @param q
	 * @return
	 */
	public List<CarStatus> carIdleStatusPageList(Query q);
	
	/**
	 * 统计空闲车辆数量
	 * 
	 * @param q
	 * @return
	 */
	public Long carIdleStatusCount(Query q);
}
