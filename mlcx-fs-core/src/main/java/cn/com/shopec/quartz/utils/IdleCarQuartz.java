
package cn.com.shopec.quartz.utils;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.StringUtils;

import cn.com.shopec.core.car.model.Car;
import cn.com.shopec.core.car.service.CarService;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.order.model.Order;
import cn.com.shopec.core.order.service.OrderService;
import cn.com.shopec.core.quartz.service.QuartzService;
import cn.com.shopec.core.system.service.SysParamService;

/**
 * 扫描订单判断车辆闲置时间
 * 
 * @author machao
 * @date 2016年10月9日
 */
public class IdleCarQuartz {

	private Log logger = LogFactory.getLog(IdleCarQuartz.class);

	private static final String ZERO = "0";
	/**
	 * 每次到数据库取数据的最大数
	 */
	private static final int maxNum = 500;

	@Resource
	private QuartzService quartzService;

	@Resource
	private OrderService orderService;

	@Resource
	private SysParamService sysParamService;
	
	@Resource
	private CarService carService;
	
	public void quartzStart() throws Exception {
		// 查找已结束的未支付的订单
		Order order = new Order();
		//车辆闲置时间
		String idleTime = sysParamService.getByParamKey("CarIdleTime").getParamValue();
		if(idleTime != null && !StringUtils.isEmpty(idleTime)){
			Long time = new Date().getTime() - Integer.parseInt(idleTime) * 60 * 60 * 1000;
			order.setFinishTimeEnd(new Date(time));
			Query q = new Query(order);
			//统计已结束的并且满足欠费超期天数的订单个数
			Car car = new Car();
			car.setOnlineStatus(1);
			car.setOnlineStatusUpdateTimeEnd(new Date(time));
			int orderSum = orderService.countIdleCar(q);
			int carSum = carService.countCar(new Query(car));
			int countCar = carSum / maxNum + 1;
			int countOrder = orderSum / maxNum + 1;
			List<Car> cars = carService.getCarList(new Query(car));
			List<Order> orders = orderService.getIdleCar(q);
			
			if(orders!=null&&cars!=null&&cars.size()>orders.size()){
				for (int i = 1; i <= countCar; i++) {
					//查询已结束的并且满足欠费超期天数的订单
					cars = carService.getCarList(new Query(car));
					logger.info("---扫描闲置车辆完成，开始生成警告信息...");
					for (Car c : cars) {
						if (c != null && !StringUtils.isEmpty(c.getCarNo())) {
							quartzService.dealIdleCarQuartz(null,c);
						}
					}
				}
			}else{
				for (int i = 1; i <= countOrder; i++) {
					//车辆表有订单里查询到的车，那么按订单信息处理
					orders = orderService.getIdleCar(q);
					logger.info("---扫描闲置车辆完成，开始生成警告信息...");
					for (Order o : orders) {
						if (o != null && !StringUtils.isEmpty(o.getOrderNo())&&o.getOrderStatus()!=2) {
							//如果该车辆在Long time = new Date().getTime() - Integer.parseInt(idleTime) * 60 * 60 * 1000;
							//范围内有使用过则不能添加警告
							//startend<time,time<endStart
							//order里含 car的，则不添加警告
							Query qOrder=new Query();
							Order oSearch=new Order();
							oSearch.setAppointmentTimeEnd(new Date(time));
							oSearch.setFinishTimeStart(new Date(time));
							oSearch.setCarNo(o.getCarNo());
							qOrder.setQ(oSearch);
							List<Order> listS=orderService.getIdleCar(qOrder);
							if(listS==null){
								quartzService.dealIdleCarQuartz(o,null);	
							}
						}
					}
				}
			}
		}else{
			logger.info("---没有配置车辆闲置时间的系统参数...");
		}
	}

	protected void execute(String arg0) throws Exception {
		// 定时任务开关，0：关 1：开
		String quartzSwitch = ZERO;

		try {
			quartzSwitch = arg0;
		} catch (Exception e) {
			logger.error("---读取定时任务开关信息出错，错误信息：" + e.getMessage(), e);
			return;
		}

		// 定时任务开关配置为0时，则关闭
		if (StringUtils.isEmpty(quartzSwitch) || quartzSwitch.equals(ZERO)) {
			logger.info("---定时任务为关闭状态...");
			return;
		}

		try {
			logger.info("--------扫描闲置车辆，开始时间：" + new Date());
			// 执行业务方法
			quartzStart();

			logger.info("--------扫描闲置车辆定时任务完成...");
		} catch (Exception e) {
			logger.error("--------扫描闲置车辆任务出错，错误信息：" + e.getMessage(), e);
		}
	}

}
