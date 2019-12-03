package cn.com.shopec.core.car.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.com.shopec.core.car.model.Car;
import cn.com.shopec.core.car.model.CarAndStatus;
import cn.com.shopec.core.car.vo.CarDayVo;
import cn.com.shopec.core.car.vo.CarDetailWkVo;
import cn.com.shopec.core.car.vo.CarOnlineVo;
import cn.com.shopec.core.car.vo.CarWkVo;
import cn.com.shopec.core.common.BaseDao;
import cn.com.shopec.core.common.Query;

/**
 * Car 数据库处理类
 */
public interface CarDao extends BaseDao<Car, String> {
	/**
	 * @author zln 查询场站内的可用车辆的列表(普通会员)
	 */
	public List<Car> getCarListByParkId(String locationParkNo, Double power);

	/**
	 * @author lj 后台 根据条件查询数据
	 * @param q
	 * @return
	 */
	public List<Car> carPageList(Query q);

	/**
	 * @author lj 后台 根据条件查询数据条件
	 * @param q
	 * @return
	 */
	public Long carCount(Query q);

	/**
	 * 根据车牌号，查询并返回一个Car对象
	 * 
	 * @param carPlateNo
	 * @return
	 */
	public Car getCarByPlateNo(String carPlateNo);

	/**
	 * @author zln （未登录和普通会员）可用车辆数（得到网点--》场站）
	 */
	public Integer getCarCountByParkMemberAndSeaTing(String parkNo, Double power,@Param("seaTing")String seaTing);

	/**
	 * @author zln （集团会员）可用车辆数（得到网点--》场站）
	 */
	public Integer getCarCountByParkMember1AndSeaTing(String parkNo, String companyId, Double power,@Param("seaTing")String seaTing);
	
	
	/**
	 * @author zln （未登录和普通会员）可用车辆数（得到网点--》场站）
	 */
	public Integer getCarCountByParkMember(String parkNo, Double power);

	/**
	 * @author zln （集团会员）可用车辆数（得到网点--》场站）
	 */
	public Integer getCarCountByParkMember1(String parkNo, String companyId, Double power);
	
	
	

	/**
	 * @author zln 查询场站内的可用车辆的列表（集团会员）
	 */
	public List<Car> getCarListByParkId1(String parkId, String memberNo, Double power);

	/**
	 * @author DYH 根据场站ID得到该场站内公用的电量最高的车(没登录和普通会员)
	 * @param seaTing 
	 */
	public List<CarAndStatus> getCarByParkNo2(@Param("parkNo")String parkNo, @Param("power")Double power, @Param("seaTing")String seaTing);

	/**
	 * @author DYH 查询场站内的可用可用的、电量最高的车辆（集团会员）
	 * @param seaTing 
	 */
	public List<CarAndStatus> getCarByMemberAndParkNo2(@Param("companyId")String companyId, @Param("parkNo")String parkNo, @Param("power")Double power, @Param("seaTing")String seaTing);

	/**
	 * @author zln 获取场站的总车辆数
	 */
	public Integer getCarCountByPark(String parkNo);

	/**
	 * @author zln 获取当前停在场站中的车辆列表
	 */
	public List<Car> getCarListByParkExist(String parkNo);

	/**
	 * 修改对象
	 * 
	 * @param obj
	 */
	public int editCarDevice(Car car);

	/**
	 * 
	 * 根据场站ID得到该场站内的车
	 */
	public List<CarAndStatus> getCarByParkNoWorker(String parkNo);

	public List<Car> getCarsByPlateNo(@Param("carPlateNo") String carPlateNo);

	/**
	 * 根据类型查找车辆
	 */
	public List<CarDayVo> getCarByCarType(Query q);

	/**
	 * 调度根据车牌搜索车辆
	 */

	public List<CarWkVo> getCarByPlateNoWk(Query q);
	
	
	/**
	 * 根据加盟商编号查询关联的车辆信息
	 */
	public List<Car> listCar(String franchiseeNo);

	/**
	 * 调度根据车牌号查看车辆详情
	 */
	public CarDetailWkVo carWkDetail(String carNo);

	/**
	 * 调度根据车牌号查看最近一次的上、下线记录
	 */
	public CarOnlineVo offLinerLately(String carNo);
	
	/**
	 * 获取车辆上下线统计的集合
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

	public Integer getcarups();

	public Integer getcarDowns();

	public List<CarWkVo> getCarFaultWk(Query q);

	public List<CarWkVo> getIsOnlineCarStatus(Query q);

	public Integer getcarDownsCityId(String cityId);

	public Integer getIsOnline(Long onlineTimeThreshol, String cityId);


}
