
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
import cn.com.shopec.core.marketing.model.PricingPackage;
import cn.com.shopec.core.marketing.service.PricingPackageService;
import cn.com.shopec.core.order.model.Order;
import cn.com.shopec.core.order.service.OrderService;
import cn.com.shopec.core.quartz.service.QuartzService;
import cn.com.shopec.core.system.service.SysParamService;

/**
 *每天发送运行日报
 * 
 * @author zln
 * @date 2017年1月13日
 */
public class RunDailySendMsgQuartz {

	private Log logger = LogFactory.getLog(RunDailySendMsgQuartz.class);

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
	private PricingPackageService pricingPackageService;
	
	public void quartzStart() throws Exception {
		logger.info("---定时扫描统计信息并发送短信---");
		quartzService.dealRunDailySendMsgQuartz();
		
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
			logger.info("--------扫描上架套餐产品，开始时间：" + new Date());
			// 执行业务方法
			quartzStart();

			logger.info("--------扫描上架套餐产品定时任务完成...");
		} catch (Exception e) {
			logger.error("--------扫描上架套餐产品出错，错误信息：" + e.getMessage(), e);
		}
	}

}
