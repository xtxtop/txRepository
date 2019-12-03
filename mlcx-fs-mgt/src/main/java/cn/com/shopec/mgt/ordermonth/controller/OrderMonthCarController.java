package cn.com.shopec.mgt.ordermonth.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.order.model.OrderMonthCar;
import cn.com.shopec.core.order.service.OrderMonthCarService;
import cn.com.shopec.mgt.common.BaseController;

@Controller
@RequestMapping("/orderMonthCar")
public class OrderMonthCarController extends BaseController {

	@Resource
	public OrderMonthCarService orderMonthCarService;

	@Override
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(true);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}

	/*
	 * 月租订单车辆列表页
	 */
	@RequestMapping("/toOrderMonthCarList")
	public String mainPage(String orderNo, ModelMap map) {
		map.put("orderNo", orderNo);
		return "order/order_month_car_list";
	}

	/*
	 * 显示月租列表页
	 */
	@RequestMapping("/pageListOrderMonthCar")
	@ResponseBody
	public PageFinder<OrderMonthCar> pageListOrderMonthCar(@ModelAttribute("orderMonthCar") OrderMonthCar orderMonthCar,
			Query query) {
		Query q = new Query(query.getPageNo(), query.getPageSize(), orderMonthCar);
		return orderMonthCarService.pageListNs(q);
	}

}
