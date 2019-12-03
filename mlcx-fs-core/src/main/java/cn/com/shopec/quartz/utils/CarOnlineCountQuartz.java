
package cn.com.shopec.quartz.utils;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.StringUtils;

import cn.com.shopec.common.utils.ECUuidGenUtils;
import cn.com.shopec.core.car.model.CarOnlineCount;
import cn.com.shopec.core.car.service.CarOnlineCountService;
import cn.com.shopec.core.car.service.CarService;
import cn.com.shopec.core.quartz.service.QuartzService;

/**
 * 车辆上下线统计数据插入
 * 
 * @author 许瑞伟
 * @date 2017年8月4日
 */
public class CarOnlineCountQuartz {

	private Log logger = LogFactory.getLog(CarOnlineCountQuartz.class);

	private static final String ZERO = "0";

	@Resource
	private QuartzService quartzService;
	
	@Resource
	private CarService carService;
	
	@Resource
	private CarOnlineCountService carOnlineCountService;
	
	public void quartzStart() throws Exception {
		Map<String,Object> map = carService.getCarLineCountMap();
		CarOnlineCount carOnlineCount = new CarOnlineCount();
		BigDecimal onlineNum = (BigDecimal)map.get("onlineNum");
		carOnlineCount.setOnlineNum(onlineNum.intValue());
		BigDecimal offlineNum = (BigDecimal)map.get("offlineNum");
		carOnlineCount.setOfflineNum(offlineNum.intValue());
		carOnlineCount.setCreateTime(new Date());
		carOnlineCount.setCarOnlineCountNo(ECUuidGenUtils.genUUID());
		carOnlineCountService.addCarOnlineCount(carOnlineCount);;
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
			logger.info("--------车辆上下线统计数据插入，开始时间：" + new Date());
			// 执行业务方法
			quartzStart();

			logger.info("--------车辆上下线统计数据插入定时任务完成...");
		} catch (Exception e) {
			logger.error("--------车辆上下线统计数据插入出错，错误信息：" + e.getMessage(), e);
		}
	}

}
