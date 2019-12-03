
package cn.com.shopec.quartz.utils;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.StringUtils;

import cn.com.shopec.core.car.model.Car;
import cn.com.shopec.core.car.model.CarStatus;
import cn.com.shopec.core.car.service.CarService;
import cn.com.shopec.core.car.service.CarStatusService;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.order.model.Order;
import cn.com.shopec.core.order.service.OrderService;
import cn.com.shopec.core.quartz.service.QuartzService;
import cn.com.shopec.core.system.service.SysParamService;

/**
 * 扫描车辆电量
 * 
 * @author machao
 * @date 2016年10月9日
 */
public class CarPowerQuartz {

	private Log logger = LogFactory.getLog(CarPowerQuartz.class);

	private static final String ZERO = "0";
	/**
	 * 每次到数据库取数据的最大数
	 */
	private static final int maxNum = 500;

	@Resource
	private QuartzService quartzService;

	@Resource
	private SysParamService sysParamService;
	
	@Resource
	private CarStatusService carStatusService;
	
	public void quartzStart() throws Exception {
		CarStatus carStaus = new CarStatus();
		//车辆电量阀值
		String powerParam = sysParamService.getByParamKey("CarPowerParam").getParamValue();
		if(powerParam != null && !StringUtils.isEmpty(powerParam)){
			carStaus.setUserageStatus(0);
			Query q = new Query(carStaus);
			int carPower = carStatusService.count(q);
			int count = carPower / maxNum + 1;
			for (int i = 1; i <= count; i++) {
				//查询空闲车辆电量少于系统配置数量的车
				List<CarStatus> lists = carStatusService.getCarStatusList(q);
				logger.info("---扫描车辆电量完成，开始更新car表信息...");
				for (CarStatus c : lists) {
					if (c != null && !StringUtils.isEmpty(c.getCarNo())) {
						quartzService.dealCarPowerQuartz(c);
					}
				}
			}
		}else{
			logger.info("---没有配置车辆电量的系统参数...");
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
			logger.info("--------扫描车辆电量，开始时间：" + new Date());
			// 执行业务方法
			quartzStart();

			logger.info("--------扫描车辆电量定时任务完成...");
		} catch (Exception e) {
			logger.error("--------扫描车辆电量出错，错误信息：" + e.getMessage(), e);
		}
	}

}
