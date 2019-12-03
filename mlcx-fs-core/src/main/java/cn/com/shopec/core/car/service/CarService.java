package cn.com.shopec.core.car.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import cn.com.shopec.common.Operator;
import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.core.car.model.Car;
import cn.com.shopec.core.car.model.CarAndStatus;
import cn.com.shopec.core.car.vo.CarDetailWkVo;
import cn.com.shopec.core.car.vo.CarOnlineVo;
import cn.com.shopec.core.car.vo.CarWkVo;
import cn.com.shopec.core.common.BaseService;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;




/**
 * Car 服务接口类
 */
public interface CarService extends BaseService {

	/**
	 * 根据查询条件，查询并返回Car的列表
	 * 
	 * @param q
	 *            查询条件
	 * @return
	 */
	public List<Car> getCarList(Query q);

	/**
	 * 根据查询条件，分页查询并返回Car的分页结果
	 * 
	 * @param q
	 *            查询条件
	 * @return
	 */
	public PageFinder<Car> getCarPagedList(Query q);

	/**
	 * @author lj 后台 根据查询条件，分页查询并返回Car的分页结果
	 * @param q
	 *            查询条件
	 * @return
	 */
	public PageFinder<Car> getCarPageList(Query q);

	/**
	 * 根据主键，查询并返回一个Car对象
	 * 
	 * @param id
	 *            主键id
	 * @return
	 */
	public Car getCar(String id);

	/**
	 * @author lj 后台车辆详情 根据主键，查询并返回一个Car对象
	 * @param id
	 *            主键id
	 * @return
	 */
	public Car toCarView(String id);

	/**
	 * 根据主键数组，查询并返回一组Car对象
	 * 
	 * @param ids
	 *            主键数组，数组中的主键值应当无重复值，如有重复值时，则返回的列表长度可能小于传入的数组长度
	 * @return
	 */
	public List<Car> getCarByIds(String[] ids);

	/**
	 * 根据主键，删除特定的Car记录
	 * 
	 * @param id
	 *            主键id
	 * @param operator
	 *            操作人对象
	 * @return
	 */
	public ResultInfo<Car> delCar(String id, Operator operator);

	/**
	 * 新增一条Car记录，执行成功后传入对象及返回对象的主键属性值不为null
	 * 
	 * @param car
	 *            新增的Car数据（如果无特殊需求，新增对象的主键值请保留为null）
	 * @param operator
	 *            操作人对象
	 * @return
	 */
	public ResultInfo<Car> addCar(Car car, Operator operator);

	public ResultInfo<Car> expotCarAdd(MultipartFile multipartFile, Operator operator) throws Exception;

	/**
	 * 根据主键，更新一条Car记录
	 * 
	 * @param car
	 *            更新的Car数据，且其主键不能为空
	 * @param operator
	 *            操作人对象
	 * @return
	 */
	public ResultInfo<Car> updateCar(Car car, Operator operator);

	/**
	 * 生成主键
	 * 
	 * @return
	 */
	public String generatePK();

	/**
	 * 为Car对象设置默认值
	 * 
	 * @param obj
	 */
	public void fillDefaultValues(Car obj);

	/**
	 * 根据场站查询场站内的车辆信息
	 */
	public List<Car> getCarListByParkId(String parkId, String memberNo);

	/**
	 * 根据车牌号，查询并返回一个Car对象
	 * 
	 * @param carPlateNo
	 * @return
	 */
	public Car getCarByPlateNo(String carPlateNo);

	/**
	 * 根据场站ID得到改场站内公用的电量最高的车
	 * @param seaTing 
	 */
	public List<CarAndStatus> getCarByParkNo(String parkNo, double power, String seaTing);

	/**
	 * 根据场站ID和用户查询场站内可用的电量最高的车
	 * @param seaTing 
	 */
	public List<CarAndStatus> getCarByMemberAndParkNo(String companyId, String parkNo, double power, String seaTing);

	/**
	 * 根据场站编号获取该场站当前在场站内的车辆 zln
	 */
	public List<Car> getCarListByParkExist(String parkNo);

	public int countCar(Query q);

	/**
	 * 根据主键，更新一条Car记录
	 * 
	 * @param car
	 *            更新的Car数据，且其主键不能为空
	 * @param operator
	 *            操作人对象
	 * @return
	 */
	public ResultInfo<Car> editCarDevice(Car car, Operator operator);

	/**
	 * 判断当前车是否在订单中，如不是则添加警告信息
	 * 
	 * @param carNo
	 * @return
	 */
	public ResultInfo<Car> judgeCarInOrder(String carNo, String APPKEY, String urls) throws IOException;

	/**
	 * 根据场站ID得到场站内的车
	 */
	public List<CarAndStatus> getCarByParkNoWorker(String parkNo);

	/**
	 * 根据车牌号，查询并返回多个Car对象
	 * 
	 * @param carPlateNo
	 * @return
	 */
	public List<Car> getCarsByPlateNo(String carPlateNo);

	/**
	 * 调度根据车牌搜索车辆
	 */

	public List<CarWkVo> getCarByPlateNoWk(Query q);

	/**
	 * 调度根据车牌号查看车辆详情
	 */
	public CarDetailWkVo carWkDetail(String carNo);

	/**
	 * 调度根据车牌号查看最近一次的上、下线记录
	 */
	public CarOnlineVo offLinerLately(String carNo);

	public long count(Query q);
	
	public Map<String, Object> carServiceMap();
	
	/**
	 * 统计上下线的车辆
	 * 
	 * @return
	 */
	public Map<String, Object> getCarLineCountMap();
	
	/**
	 * 统计不在线的车辆
	 * 
	 * @return
	 */
	public Integer countCarNotLine();
	
	/**
	 * 统计闲置的车辆
	 * 
	 * @return
	 */
	public Integer countCarIdle();
	
	/**
	 * 查询加盟商关联的车辆信息
	 */
	public List<Car> listCar(String franchiseeNo);

	public Integer getcarups();

	public Integer getcarDowns();

	public List<CarWkVo> getCarFaultWk(Query query);

	public List<CarWkVo> getIsOnlineCarStatus(Query query);

	public Integer getcarDownsCityId(String cityId);

	public Integer getIsOnline(String cityId);

	public long getOnlineTimeThreshol();
	
		
	

}
