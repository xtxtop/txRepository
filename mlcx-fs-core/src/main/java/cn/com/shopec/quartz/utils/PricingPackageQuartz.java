
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
 * 扫描套餐产品有效期
 * 
 * @author zln
 * @date 2016年12月12日
 */
public class PricingPackageQuartz {

	private Log logger = LogFactory.getLog(PricingPackageQuartz.class);

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
		PricingPackage pricingPackage = new PricingPackage();
		pricingPackage.setIsAvailable(1);//查询上架的套餐产品
		Query q = new Query(pricingPackage);
		List<PricingPackage> list=pricingPackageService.getPricingPackageList(q);
		logger.info("---扫描上架的套餐产品完成，开始更新pricingPackage表信息...");
		if(list!=null&&list.size()>0){
			for (PricingPackage pp : list) {
				if (pp != null) {
						quartzService.dealpricingPackageQuartz(pp);
					}
			}
		}else{
			logger.info("---扫描上架的套餐产品完成，没有满足条件的信息");
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
			logger.info("--------扫描上架套餐产品，开始时间：" + new Date());
			// 执行业务方法
			quartzStart();

			logger.info("--------扫描上架套餐产品定时任务完成...");
		} catch (Exception e) {
			logger.error("--------扫描上架套餐产品出错，错误信息：" + e.getMessage(), e);
		}
	}

}
