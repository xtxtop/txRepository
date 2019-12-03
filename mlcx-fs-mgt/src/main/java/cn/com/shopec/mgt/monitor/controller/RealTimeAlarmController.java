package cn.com.shopec.mgt.monitor.controller;

import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.shopec.common.utils.ECGeoCoordinateTransformUtil;
import cn.com.shopec.core.car.model.CarStatus;
import cn.com.shopec.core.car.service.CarStatusService;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.monitor.model.CarMonitor;
import cn.com.shopec.core.monitor.service.CarTrackService;
import cn.com.shopec.core.order.model.Order;
import cn.com.shopec.core.order.service.OrderService;
import cn.com.shopec.core.resource.model.Park;
import cn.com.shopec.core.resource.service.ParkService;
import cn.com.shopec.core.scheduling.model.Worker;
import cn.com.shopec.core.scheduling.model.WorkerOrder;
import cn.com.shopec.core.scheduling.service.WorkerOrderService;
import cn.com.shopec.core.scheduling.service.WorkerService;
import cn.com.shopec.core.system.model.DataDictItem;
import cn.com.shopec.core.system.service.DataDictItemService;
import cn.com.shopec.mgt.common.BaseController;

/**
 * 车辆监控实时报警
 * 
 * @author zln
 * @param 
 *
 */
@Controller
@RequestMapping("/realTimeAlarm")
public class RealTimeAlarmController extends BaseController {
	@Resource
	private CarStatusService carStatusService;
	/**
	 * 进入车辆监控实时报警页面
	 * 
	 * @return
	 */
	@RequestMapping("toRealTimeAlarmReal")
	public String toRealTimeAlarm() {
		return "monitor/realtime_alarm_real";
	}
	
	/**
	 * 进入车辆监控实时报警页面 -- 演示环境虚拟数据
	 * 
	 * @return
	 */
	@RequestMapping("toRealTimeAlarmFake")
	public String toRealTimeAlarmFake() {
		return "monitor/realtime_alarm_fake";
	}
	
	/**
	 * 查询车辆监控实时报警列表
	 * 
	 * @param query
	 * @return
	 * @throws ParseException
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 */
	@RequestMapping("pageListRealTimeAlarm")
	@ResponseBody
	public List<CarMonitor> pageListRealTimeAlarm(Integer type)throws ParseException, IllegalAccessException, InvocationTargetException {
		List<CarMonitor> list=carStatusService.getRealTimeAlarmList(1,type);
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
