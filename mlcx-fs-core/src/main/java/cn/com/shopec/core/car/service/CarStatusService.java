package cn.com.shopec.core.car.service;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import cn.com.shopec.common.Operator;
import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.core.car.model.CarStatus;
import cn.com.shopec.core.car.vo.CarWkVo;
import cn.com.shopec.core.common.BaseService;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.member.model.Member;
import cn.com.shopec.core.monitor.model.CarMonitor;
import cn.com.shopec.core.order.model.Order;

/**
 * CarStatus 服务接口类
 */
public interface CarStatusService extends BaseService {

	/**
	 * 查询订单中和调度中的车辆
	 * 
	 * @param q
	 *            查询条件
	 * @return
	 */
	public List<CarStatus> getCarStatusListForCarMonitor(Query q);

	/**
	 * 根据查询条件，查询并返回CarStatus的列表
	 * 
	 * @param q
	 *            查询条件
	 * @return
	 */
	public List<CarStatus> getCarStatusList(Query q);

	/**
	 * 根据查询条件，分页查询并返回CarStatus的分页结果
	 * 
	 * @param q
	 *            查询条件
	 * @return
	 */
	public PageFinder<CarStatus> getCarStatusPagedList(Query q);

	/**
	 * @author lj 根据查询条件，分页查询并返回CarStatus的分页结果
	 * @param q
	 *            查询条件
	 * @return
	 */
	public PageFinder<CarStatus> getCarStatusPageList(Query q, Integer tag);
	
	/**
	 * @author 根据查询条件，分页查询并返回CarStatus空闲车辆的分页结果
	 * @param q
	 *            查询条件
	 * @return
	 */
	public PageFinder<CarStatus> getCarIdleStatusPageList(Query q);

	/**
	 * 根据主键，查询并返回一个CarStatus对象
	 * 
	 * @param id
	 *            主键id
	 * @return
	 */
	public CarStatus getCarStatus(String id);

	/**
	 * 根据主键数组，查询并返回一组CarStatus对象
	 * 
	 * @param ids
	 *            主键数组，数组中的主键值应当无重复值，如有重复值时，则返回的列表长度可能小于传入的数组长度
	 * @return
	 */
	public List<CarStatus> getCarStatusByIds(String[] ids);

	/**
	 * 根据主键，删除特定的CarStatus记录
	 * 
	 * @param id
	 *            主键id
	 * @param operator
	 *            操作人对象
	 * @return
	 */
	public ResultInfo<CarStatus> delCarStatus(String id, Operator operator);

	/**
	 * 新增一条CarStatus记录，执行成功后传入对象及返回对象的主键属性值不为null
	 * 
	 * @param carStatus
	 *            新增的CarStatus数据（如果无特殊需求，新增对象的主键值请保留为null）
	 * @param operator
	 *            操作人对象
	 * @return
	 */
	public ResultInfo<CarStatus> addCarStatus(CarStatus carStatus, Operator operator);

	/**
	 * 根据主键，更新一条CarStatus记录
	 * 
	 * @param carStatus
	 *            更新的CarStatus数据，且其主键不能为空
	 * @param operator
	 *            操作人对象
	 * @return
	 */
	public ResultInfo<CarStatus> updateCarStatus(CarStatus carStatus, Operator operator);

	/**
	 * 根据主键和车辆使用状态更新车辆使用状态
	 * 
	 * @param carStatus
	 * @param operator
	 * @return
	 */
	public ResultInfo<String> updateCarUseStatusByCarNo(CarStatus carStatus, Operator operator);

	/**
	 * 生成主键
	 * 
	 * @return
	 */
	public String generatePK();

	/**
	 * 为CarStatus对象设置默认值
	 * 
	 * @param obj
	 */
	public void fillDefaultValues(CarStatus obj);

	/**
	 * 取车后修改车辆状态信息并修改订单信息
	 * 
	 * @param operator
	 */
	public ResultInfo<CarStatus> takeCar(String deviceId, Operator operator);

	/**
	 * 租车后修改车辆状态信息并生成订单
	 * 
	 * @param operator
	 * @param latitude 
	 * @param longitude 
	 * @param ak 
	 */
	public ResultInfo<CarStatus> rentCar(Order order, Member member, String parkNo, Operator operator);

	/**
	 * 还车后，修改车辆状态并修改订单信息
	 * 
	 * @param parkNo
	 * 
	 * @param operator
	 * @throws ParseException
	 */
	public ResultInfo<Order> returnCar(String orderNo, String parkNo, Operator operator) throws ParseException;

	/**
	 * 开门锁
	 * 
	 * @param url
	 * @param deviceSn
	 * @return
	 */
	public String openDoor(String deviceSn) throws Exception;

	/**
	 * 关门锁
	 * 
	 * @param url
	 * @param deviceSn
	 * @return
	 */
	public String closeDoor(String deviceSn) throws Exception;

	/**
	 * 寻车指令
	 * 
	 * @param deviceSn
	 * @return
	 * @throws Exception
	 */
	public String findCar(String deviceSn) throws Exception;

	/**
	 * 查询场站已占空位数
	 * 
	 * @param q
	 * @return
	 */
	public List<CarStatus> getCarSpaceShortage(Query q);

	/**
	 * 统计数量
	 * 
	 * @param q
	 * @return
	 */
	public int count(Query q);

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
	 * 重启设备接口
	 * 
	 * @param url
	 * @param deviceSn
	 * @return
	 */
	public String restartDevice(String deviceSn) throws Exception;

	/**
	 * 重启蓝牙接口
	 * 
	 * @param url
	 * @param deviceSn
	 * @return
	 */
	public String restartDeviceBluetooth(String deviceSn) throws Exception;

	/**
	 * 激活/禁用动力开关
	 * 
	 * @param deviceSn
	 * @param enable
	 * @return
	 */
	public String enablePowerCtrl(String deviceSn, String enable) throws Exception;

	/**
	 * 查不在场站的车辆信息
	 * 
	 * @param q
	 * @return
	 */
	public List<CarStatus> getCarNoPark(String longitude, String latitude, Double distance);

	/**
	 * 监控管理查询全部车辆报警信息
	 * 
	 * @param menu
	 *            代表菜单类型 1 闲置超时报警 2 非订单用车
	 * @param type
	 * @return
	 */
	public List<CarMonitor> getRealTimeAlarmList(Integer menu, Integer type);

	public List<CarStatus> getCarStatusListOutOrder();

	/**
	 * 根据车辆号，取得车辆状态表中，各上报时间字段的值
	 * 
	 * @param carNo
	 * @return
	 */
	public CarStatus getCarStatusReportingTime(String carNo);

	public List<CarStatus> getCarStatusListOutOrderW();

	/**
	 * 远程动力控制
	 * 
	 * @param deviceSn
	 * @param cmdValue
	 * @return
	 */
	public String controlPower(String deviceSn, String cmdValue) throws Exception;
	
	/**
	 * 远程控制，设置车机的车辆租赁模式
	 * @param deviceSn 车机识别号
	 * @param cmdValue 值1：租赁模式，值0：非租赁模式
	 * @return
	 * @throws Exception
	 */
	public String setTerminalLeasingMode(String deviceSn, String cmdValue) throws Exception;
	
	/**
	 * 设置物理钥匙使能/禁用
	 * @param deviceSn 车机识别号
	 * @param cmdValue 值1：钥匙使能，值0：钥匙禁用
	 * @return
	 * @throws Exception
	 */
	public String setKeyEnableMode(String deviceSn, String cmdValue) throws Exception;

	/**
	 * 用车指令
	 * 
	 * @param signedDeviceSn
	 * @param oprtNo
	 * @param opType
	 * @return
	 * @throws Exception
	 */
	public String userCarSendCmd(String signedDeviceSn, String mebmerNo, String workerNo, String type) throws Exception;

	/**
	 * 还车指令
	 * 
	 * @param signedDeviceSn
	 * @param oprtNo
	 * @param opType
	 * @return
	 * @throws Exception
	 */
	public String returnCarSendCmd(String signedDeviceSn, String mebmerNo, String workerNo, String type)
			throws Exception;

	public List<CarStatus> getCarStatusPageListForExport(Query q);

	public void beforeOrderFinish(Order order, CarStatus carStatus) throws ParseException;

	/**
	 * 调度查找附近的车
	 */
	public List<CarWkVo> nearbyCar(Query q);

	/**
	 * 网点停靠车辆
	 */
	public List<CarWkVo> stopCar(Query q);

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
	 * 统计小电瓶低电
	 * 
	 * @return
	 */
	public Integer countCarMinLowPower();

	/**
	 * 根据车辆编号获取断线车辆个数（用来判断车辆是否在线）
	 * 
	 * @return
	 */
	public Integer getCarBrokenLineByCarNo(String carNo);

	/**
	 * 重置蓝牙名
	 * 
	 * @param deviceSn
	 * @param enable
	 * @return
	 */
	public String resetBluetoothName(String deviceSn, String bluetoothName) throws Exception;

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
	 * 设备升级
	 * 
	 * @param deviceSn
	 * @param enable
	 * @return
	 * @throws IOException 
	 */
	public String deviceUpgrade(String deviceSn) throws IOException;

	public String restartDeviceParameter(String deviceSn,String param) throws IOException;

	public String getDeviceParam(String deviceSn) throws IOException;
	
}
