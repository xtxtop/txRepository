package cn.com.shopec.core.quartz.service;

import cn.com.shopec.common.exception.QuartzException;
import cn.com.shopec.core.car.model.Car;
import cn.com.shopec.core.car.model.CarStatus;
import cn.com.shopec.core.marketing.model.PricingPackage;
import cn.com.shopec.core.order.model.Order;

/**
 * 定时任务服务类
 * @author machao
 * @date 2016年9月30日
 * @version 1.0
 */
public interface QuartzService {

	/**
	 * 扫描已完成订单是否超期任务
	 * @author machao
	 * @date 2016年9月30日
	 * @throws QuartzException
	 */
	public void dealMemberOrderQuartz(Order order) throws QuartzException;

	public void dealIdleCarQuartz(Order order,Car car);

	public void dealcarSpaceShortageQuartz(CarStatus carStatus, Integer totalCarSpace, Integer remainCarSpace);

	public void dealCarPowerQuartz(CarStatus c);

	public void dealpricingPackageQuartz(PricingPackage pp);

	public void dealRunDailySendMsgQuartz();

	public void importAccountsPowerQuartz();
	
}
