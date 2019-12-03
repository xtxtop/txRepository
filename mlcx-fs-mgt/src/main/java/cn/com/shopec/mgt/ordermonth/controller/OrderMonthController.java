package cn.com.shopec.mgt.ordermonth.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.core.car.model.Car;
import cn.com.shopec.core.car.service.CarService;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.member.service.MemberService;
import cn.com.shopec.core.order.model.OrderMonth;
import cn.com.shopec.core.order.model.OrderMonthCar;
import cn.com.shopec.core.order.service.OrderMonthCarService;
import cn.com.shopec.core.order.service.OrderMonthService;
import cn.com.shopec.core.system.model.DataDictItem;
import cn.com.shopec.core.system.service.DataDictItemService;
import cn.com.shopec.mgt.common.BaseController;

@Controller
@RequestMapping("/orderMonth")
public class OrderMonthController extends BaseController {
	@Resource
	public OrderMonthService orderMonthService;
	@Resource
	public MemberService memberService;
	@Resource
	public DataDictItemService dataDictItemService;
	@Resource
	public CarService carService;
	@Resource
	public OrderMonthCarService orderMonthCarService;

	/*
	 * 显示月租列表页
	 */
	@RequestMapping("/toOrderMonthList")
	public String mainPage() {
		return "order/order_month_list";
	}

	/*
	 * 显示月租列表页
	 */
	@RequestMapping("/pageListOrderMonth")
	@ResponseBody
	public PageFinder<OrderMonth> pageListOrderMonth(@ModelAttribute("orderMonth") OrderMonth orderMonth, Query query) {
		Query q = new Query(query.getPageNo(), query.getPageSize(), orderMonth);
		return orderMonthService.pageListNs(q);
	}

	/*
	 * 查找长租车辆
	 */
	@RequestMapping("/pageListOrderMonthCar")
	@ResponseBody
	public PageFinder<Car> pageListOrderMonthCar(@ModelAttribute("car") Car car, Query query) {
		car.setLeaseType(2);
		car.setOnlineStatus(1);
		car.setUserageStatus(0);
		Query q = new Query(query.getPageNo(), query.getPageSize(), car);
		return carService.getCarPagedList(q);
	}

	/*
	 * 显示月租详情页
	 */
	@RequestMapping("/toOrderMonthView")
	public String toOrderMonthView(String orderNo, ModelMap modelMap) {

		OrderMonth order = orderMonthService.getOrderMonth(orderNo);
		modelMap.put("order", order);
		// 会员名称
		// if (order.getMemberNo() != null) {
		// Member member = memberService.getMember(order.getMemberNo());
		// modelMap.put("member", member);
		// }

		return "order/order_month_view";
	}

	/*
	 * 显示月租增加列表页
	 */
	@RequestMapping("/toOrderMonthAdd")
	public String toOrderMonthAdd(ModelMap map) {
		List<DataDictItem> cities = dataDictItemService.getDataDictItemListByCatCode("CITY");// 城市
		map.put("cities", cities);
		return "order/order_month_add";
	}

	/*
	 * 显示月租增加列表页
	 */
	@RequestMapping("/toOrderMonthEdit")
	public String toOrderMonthEdit(String orderNo, ModelMap map) {
		List<DataDictItem> cities = dataDictItemService.getDataDictItemListByCatCode("CITY");// 城市
		map.put("cities", cities);
		OrderMonth orderMonth = orderMonthService.getOrderMonth(orderNo);
		map.put("orderMonth", orderMonth);

		OrderMonthCar car = new OrderMonthCar();
		car.setOrderNo(orderNo);
		List<OrderMonthCar> cl = orderMonthCarService.getOrderMonthCarList(new Query(car));
		if (cl.size() > 0) {
			for (OrderMonthCar oc : cl) {
				Car c = carService.getCar(oc.getCarNo());
				oc.setCarBrandName(c.getCarBrandName());
				oc.setCarModelName(c.getCarModelName());
			}
		}
		map.put("cl", cl);
		return "order/order_month_edit";
	}

	/*
	 * 新增或修改车辆品牌
	 */
	@RequestMapping("/editOrderMonth")
	@ResponseBody
	public ResultInfo<OrderMonth> editOrderMonth(@ModelAttribute("orderMonth") OrderMonth orderMonth) {
		ResultInfo<OrderMonth> resultInfo = new ResultInfo<OrderMonth>();
		if (orderMonth.getOrderNo() != null && orderMonth.getOrderNo().length() != 0) {
			resultInfo = orderMonthService.updateOrderMonth(orderMonth, getOperator());
		} else {

			resultInfo = orderMonthService.addOrderMonth(orderMonth, getOperator());
		}
		return resultInfo;
	}

}
