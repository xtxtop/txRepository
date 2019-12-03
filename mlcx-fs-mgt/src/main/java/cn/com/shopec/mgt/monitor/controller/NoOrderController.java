package cn.com.shopec.mgt.monitor.controller;

import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.shopec.core.car.service.CarStatusService;
import cn.com.shopec.core.monitor.model.CarMonitor;
import cn.com.shopec.mgt.common.BaseController;

/**
 * 车辆监控实时报警
 * 
 * @author zln
 * @param 
 *
 */
@Controller
@RequestMapping("/noOrder")
public class NoOrderController extends BaseController {
	@Resource
	private CarStatusService carStatusService;
	/**
	 * 非订单用车
	 * 
	 * @return
	 */
	@RequestMapping("mainPage")
	public String toRealTimeAlarm() {
		return "monitor/no_order";
	}

	/**
	 * 非订单用车 -- 演示环境虚拟数据
	 * 
	 * @return
	 */
	@RequestMapping("mainPageFake")
	public String toRealTimeAlarmFake() {
		return "monitor/no_order_fake";
	}
	
	/**
	 * 非订单用车
	 * 
	 * @return
	 */
	@RequestMapping("mainPageReal")
	public String toRealTimeAlarmReal() {
		return "monitor/no_order_real";
	}

	
	/**
	 * 查询车辆监控实时报警列表
	 * @param type
	 * @return
	 * @throws ParseException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	@RequestMapping("pageListnoOrder")
	@ResponseBody
	public List<CarMonitor> pageListRealTimeAlarm(Integer type)throws ParseException, IllegalAccessException, InvocationTargetException {
		List<CarMonitor> list=carStatusService.getRealTimeAlarmList(2,type);
		return list;
	}
//	/**
//	 * 查询所有场站信息
//	 * 
//	 * @param query
//	 * @return
//	 * @throws ParseException
//	 * @throws InvocationTargetException
//	 * @throws IllegalAccessException
//	 */
//	@RequestMapping("pageListPark")
//	@ResponseBody
//	public List<Park> pageListPark()throws ParseException, IllegalAccessException, InvocationTargetException {
//		Park pSearch=new Park();
//		pSearch.setIsAvailable(1);
//		pSearch.setIsDeleted(0);
//		List<Park> parkList = parkService.getParkList(new Query(pSearch));
//		return parkList;
//	}

}
