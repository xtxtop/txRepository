package cn.com.shopec.mgt.order.controller;

import java.text.ParseException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;

import cn.com.shopec.core.member.service.MemberService;
import cn.com.shopec.core.order.model.Order;
import cn.com.shopec.core.order.service.OrderService;
import cn.com.shopec.mgt.common.BaseController;


/**
 * 订单计时器
 * */
@Controller
public class OrderTimerController extends BaseController{
	@Resource
	private OrderService orderService;
	
	public void execute() throws ParseException {
		/**
		 * 得到所有开始自动计费的订单；
		 * 条件：1、订单状态为1（已预约）的订单；
		 *       2、计费时间为空；
		 *       3、距离下单时间已经达到20（系统参数设置的）分钟；
		 * 		
		 */
//		List<Order> startList = orderService.getAllAutoBeginCharging();
		
		
		
		/**
		 *得到所有自动取消计费的订单
		 *条件：  1、订单状态为2（已计费）的订单；这个条件不要了，直接结束。
		 *		2、开始计费时间不为空，
		 *		3、开门时间为空；
		 *		4、距离下单时间已经达到60（系统参数设置的）分钟；
		 */
		List<Order> endList = orderService.getAllAutoEndCharging();
		
		
		
		
	}
	
	
	
	
	
}
