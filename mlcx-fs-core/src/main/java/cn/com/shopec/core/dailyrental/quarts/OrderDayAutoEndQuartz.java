
package cn.com.shopec.core.dailyrental.quarts;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.StringUtils;

import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.common.utils.ECDateUtils;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.dailyrental.model.DelayOrderDay;
import cn.com.shopec.core.dailyrental.model.MerchantOrder;
import cn.com.shopec.core.dailyrental.model.OrderDay;
import cn.com.shopec.core.dailyrental.service.DelayOrderDayService;
import cn.com.shopec.core.dailyrental.service.MerchantOrderService;
import cn.com.shopec.core.dailyrental.service.OrderDayService;
import cn.com.shopec.core.system.service.SysParamService;

/**
 * 日租订单超过配置时间未支付自动取消
 * 
 */
public class OrderDayAutoEndQuartz {

	private Log logger = LogFactory.getLog(OrderDayAutoEndQuartz.class);

	private static final String ZERO = "0";
	/**
	 * 每次到数据库取数据的最大数
	 */
	private static final int maxNum = 500;

	@Resource
	private SysParamService sysParamService;
	
	@Resource
	private OrderDayService orderDayService;
	@Resource
	private MerchantOrderService merchantOrderService;
	@Resource
	private DelayOrderDayService delayOrderDayService;
	
	public void quartzStart() throws Exception {
		//订单超时时间(分钟数)
		String overTimeParam = sysParamService.getByParamKey("OrderDayOverTimeParam").getParamValue();
		if(overTimeParam != null && !StringUtils.isEmpty(overTimeParam)){
			OrderDay orderDay = new OrderDay();
			//超时时间
			int overTime = Integer.parseInt(overTimeParam);
			orderDay.setOrderStatus(1);
			orderDay.setPayStatus(0);
			Query q = new Query(orderDay);
			int orderDayCount = orderDayService.count(q);
			int count = orderDayCount / maxNum + 1;
			for (int i = 1; i <= count; i++) {
				List<OrderDay> lists = orderDayService.getOrderDayList(q);
				logger.info("---扫描日租订单完成");
				for (OrderDay order : lists) {
					int diffMinutes = ECDateUtils.differMinutes(ECDateUtils.getCurrentDateTime(), order.getAppointmentTime()).intValue();
					if(diffMinutes>=overTime){
						OrderDay orderDayForUpdate = new OrderDay();
						orderDayForUpdate.setOrderNo(order.getOrderNo());
						orderDayForUpdate.setDiscountAmount(0.0);
						orderDayForUpdate.setOrderStatus(4);
						ResultInfo<OrderDay> result = orderDayService.updateOrderDay(orderDayForUpdate, null);
						if("1".equals(result.getCode())){
							MerchantOrder merchantOrder = merchantOrderService.getOrderDayNo(order.getOrderNo());
							if(merchantOrder != null){
								MerchantOrder merchantOrderForUpdate = new MerchantOrder();
								merchantOrderForUpdate.setMerchantOrderId(merchantOrder.getMerchantOrderId());
								merchantOrderForUpdate.setMerchantOrderStatus(6);	
								merchantOrderService.updateMerchantOrder(merchantOrderForUpdate,null);
							}
							logger.info("---取消成功--");
						}
					}
				}
			}
		}else{
			logger.info("---没有配置日租订单超时系统参数...");
		}
		//系统参数日租续租订单保留时间值
		String delayOrderRemainTimeStr = sysParamService.getByParamKey("delayOrderRemainTime").getParamValue();
		if(delayOrderRemainTimeStr != null && !StringUtils.isEmpty(delayOrderRemainTimeStr)){
			//续租订单保留时间值
			int delayOrderRemainTime = Integer.parseInt(delayOrderRemainTimeStr);
			OrderDay orderDay = new OrderDay();
			orderDay.setOrderStatus(2);
			orderDay.setPayStatus(0);
			orderDay.setIsDelayOrder(1);
			Query q = new Query(orderDay);
			int orderDayCount = orderDayService.count(q);
			int count = orderDayCount / maxNum + 1;
			for (int i = 1; i <= count; i++) {
				List<OrderDay> lists = orderDayService.getOrderDayList(q);
				logger.info("---扫描续租日租订单完成");
				for (OrderDay order : lists) {
					DelayOrderDay delayOrder = delayOrderDayService.getDelayOrderDayByOrderDayNo(order.getOrderNo());
					int diffMinutes = ECDateUtils.differMinutes(ECDateUtils.getCurrentDateTime(), delayOrder.getCreateTime()).intValue();
					if(diffMinutes>=delayOrderRemainTime){
						OrderDay orderDayForUpdate = new OrderDay();
						orderDayForUpdate.setOrderNo(order.getOrderNo());
						orderDayForUpdate.setIsDelayOrder(0);
						orderDayForUpdate.setPayStatus(1);
						orderDayService.updateOrderDay(orderDayForUpdate, null);
					}
				}
			}
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
			logger.info("--------扫描日租超时订单，开始时间：" + new Date());
			// 执行业务方法
			quartzStart();

			logger.info("--------扫描日租超时订单任务完成...");
		} catch (Exception e) {
			logger.error("--------扫描日租超时订单，错误信息：" + e.getMessage(), e);
		}
	}

}
