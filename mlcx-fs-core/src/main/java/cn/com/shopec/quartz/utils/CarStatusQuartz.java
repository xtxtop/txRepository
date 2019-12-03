
package cn.com.shopec.quartz.utils;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.StringUtils;

import cn.com.shopec.core.car.model.CarStatus;
import cn.com.shopec.core.car.service.CarStatusService;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.monitor.service.WarningService;
import cn.com.shopec.core.order.model.Order;
import cn.com.shopec.core.order.service.OrderService;
import cn.com.shopec.core.quartz.service.QuartzService;

/**
 * 扫描是否有进行的订单，进而操作相应的carStatus
 * 
 * @author machao
 * @date 2016年10月26日
 */
public class CarStatusQuartz {

	private Log logger = LogFactory.getLog(CarStatusQuartz.class);

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
	private WarningService warningService;
	
	@Resource
	private CarStatusService carStatusService;
	
	public void quartzStart() throws Exception {
		//当前有订单（为计费中），车辆状态不对应
		Order order  = new Order();
		order.setOrderStatus(2);//已计费
		Query q = new Query(order);
		int orderConut = orderService.countOrder(q);
		int count = orderConut / maxNum + 1;
		for (int i = 1; i <= count; i++) {
			List<Order> orderList = orderService.getOrderList(q);
			if(orderList!=null&&orderList.size()>0){
				for(Order o:orderList){
					CarStatus carStatus = carStatusService.getCarStatus(o.getCarNo());
					if(carStatus!=null&&carStatus.getUserageStatus()!=2){
						CarStatus c = new CarStatus();
						c.setUserageStatus(2);
						c.setCarNo(carStatus.getCarNo());
						carStatusService.updateCarStatus(c, null);
					}
				}
			}else{
				//当前没有订单（为计费中），车辆状态不对应
				CarStatus carStatus = new CarStatus();
				carStatus.setUserageStatus(2);
				List<CarStatus> carStatusLsit = carStatusService.getCarStatusList(new Query(carStatus));
				if(carStatusLsit!=null&&carStatusLsit.size()>0){
					for(CarStatus cs:carStatusLsit){
						CarStatus c = new CarStatus();
						c.setUserageStatus(0);
						c.setCarNo(cs.getCarNo());
						carStatusService.updateCarStatus(c, null);
					}
				}
			}
		}
		
		//当前有订单（已预占），车辆状态不对应
		Order order1  = new Order();
		order1.setOrderStatus(1);//已预占
		Query q1 = new Query(order1);
		int orderConut1 = orderService.countOrder(q1);
		int count1 = orderConut1 / maxNum + 1;
		for (int i = 1; i <= count1; i++) {
			List<Order> orderList1 = orderService.getOrderList(q1);
			if(orderList1!=null&&orderList1.size()>0){
				for(Order o:orderList1){
					CarStatus carStatus = carStatusService.getCarStatus(o.getCarNo());
					if(carStatus!=null&&carStatus.getUserageStatus()!=1){
						CarStatus c = new CarStatus();
						c.setUserageStatus(1);
						c.setCarNo(carStatus.getCarNo());
						carStatusService.updateCarStatus(c, null);
					}
				}
			}else{
				//当前没有订单（已预占），车辆状态不对应
				CarStatus carStatus = new CarStatus();
				carStatus.setUserageStatus(1);
				List<CarStatus> carStatusLsit = carStatusService.getCarStatusList(new Query(carStatus));
				if(carStatusLsit!=null&&carStatusLsit.size()>0){
					for(CarStatus cs:carStatusLsit){
						CarStatus c = new CarStatus();
						c.setUserageStatus(0);
						c.setCarNo(cs.getCarNo());
						carStatusService.updateCarStatus(c, null);
					}
				}
			}
		}
	}

	protected void execute(String arg0) throws Exception {
		// 定时任务开关，0：关 1：开
		String quartzSwitch = ZERO;

		try {
			quartzSwitch = ZERO;
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
			logger.info("--------扫描订单以及车辆状态，开始时间：" + new Date());
			// 执行业务方法
			quartzStart();

			logger.info("--------扫描订单以及车辆状态定时任务完成...");
		} catch (Exception e) {
			logger.error("--------扫描订单以及车辆状态，错误信息：" + e.getMessage(), e);
		}
	}

}
