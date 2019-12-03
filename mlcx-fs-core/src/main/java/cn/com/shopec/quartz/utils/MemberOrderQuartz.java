
package cn.com.shopec.quartz.utils;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.StringUtils;

import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.order.model.Order;
import cn.com.shopec.core.order.service.OrderService;
import cn.com.shopec.core.quartz.service.QuartzService;
import cn.com.shopec.core.system.service.SysParamService;

/**
 * 扫描已完成订单判断，是否超期任务
 * 
 * @author machao
 * @date 2016年9月30日
 */
public class MemberOrderQuartz {

	private Log logger = LogFactory.getLog(MemberOrderQuartz.class);

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
	
	public void quartzStart() throws Exception {
		// 查找已结束的未支付的订单
		Order order = new Order();
		order.setOrderStatus(3);
		order.setPayStatus(0);
		//订单超期天数
		String orderPassDay = sysParamService.getByParamKey("MemberOrderPassDay").getParamValue();
		if(orderPassDay != null && !StringUtils.isEmpty(orderPassDay)){
			Long time = new Date().getTime() - Integer.parseInt(orderPassDay) * 24 * 60 * 60 * 1000;
			order.setFinishTimeEnd(new Date(time));
			Query q = new Query(order);
			//统计已结束的并且满足欠费超期天数的订单个数
			int sum = orderService.countOrder(q);
			
			int count = sum / maxNum + 1;
			
			for (int i = 1; i <= count; i++) {
				//查询已结束的并且满足欠费超期天数的订单
				List<Order> list = orderService.getOrderList(q);
				logger.info("---扫描欠费超期订单完成，开始生成警告信息...");
				for (Order o : list) {
					if (o != null && !StringUtils.isEmpty(o.getOrderNo())) {
						quartzService.dealMemberOrderQuartz(o);
					}
				}
			}
		}else{
			logger.info("---没有配置订单欠费超期天数的系统参数...");
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
			logger.info("--------扫描已完成订单是否超期任务，开始时间：" + new Date());
			// 执行业务方法
			quartzStart();

			logger.info("--------扫描订单超期定时任务完成...");
		} catch (Exception e) {
			logger.error("--------扫描订单超期任务出错，错误信息：" + e.getMessage(), e);
		}
	}

}
