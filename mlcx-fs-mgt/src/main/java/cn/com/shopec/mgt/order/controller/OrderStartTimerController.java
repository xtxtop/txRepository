package cn.com.shopec.mgt.order.controller;

import java.text.ParseException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;

import cn.com.shopec.common.Operator;
import cn.com.shopec.core.member.service.MemberService;
import cn.com.shopec.core.order.model.Order;
import cn.com.shopec.core.order.service.OrderService;
import cn.com.shopec.core.system.model.SysUser;
import cn.com.shopec.mgt.common.BaseController;


/**
 * 订单计时器
 * */
@Controller
public class OrderStartTimerController extends BaseController{
	private static final String Operator = null;
	@Resource
	private OrderService orderService;
	
	public void execute() throws ParseException {
		
		/**
		 *得到所有在进行中超过配置的时间的订单
		 *条件：  1、订单状态为2（已计费）的订单；
		 *		2、开始计费时间不为空，
		 *		4、下单时间已经超过60（系统参数设置的）分钟；
		 */
		
		@SuppressWarnings("unused")
		Integer Integer = orderService.getAllAutoStartOrderCharging();
		
		
		
		
	}
	
	
	
	
	
}
