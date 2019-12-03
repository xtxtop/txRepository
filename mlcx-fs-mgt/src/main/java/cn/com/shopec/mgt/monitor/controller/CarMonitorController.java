package cn.com.shopec.mgt.monitor.controller;

import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.shopec.common.utils.ECDateUtils;
import cn.com.shopec.common.utils.ECGeoCoordinateTransformUtil;
import cn.com.shopec.common.utils.ECStringUtils;
import cn.com.shopec.core.car.model.Car;
import cn.com.shopec.core.car.model.CarStatus;
import cn.com.shopec.core.car.service.CarService;
import cn.com.shopec.core.car.service.CarStatusService;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.map.service.BaiduGeocoderApiService;
import cn.com.shopec.core.monitor.model.CarMonitor;
import cn.com.shopec.core.order.model.Order;
import cn.com.shopec.core.order.service.OrderService;
import cn.com.shopec.core.resource.model.Park;
import cn.com.shopec.core.resource.service.ParkService;
import cn.com.shopec.core.scheduling.model.Worker;
import cn.com.shopec.core.scheduling.model.WorkerOrder;
import cn.com.shopec.core.scheduling.service.WorkerOrderService;
import cn.com.shopec.core.scheduling.service.WorkerService;
import cn.com.shopec.core.system.model.DataDictItem;
import cn.com.shopec.core.system.model.SysParam;
import cn.com.shopec.core.system.service.DataDictItemService;
import cn.com.shopec.core.system.service.SysParamService;
import cn.com.shopec.mgt.common.BaseController;
import cn.com.shopec.mgt.common.ECMgtConstant;

/**
 * 车辆监控
 * 
 * @author machao
 *
 */
@Controller
@RequestMapping("/carMonitor")
public class CarMonitorController extends BaseController {
	@Resource
	private CarService carService;
	@Resource
	private CarStatusService carStatusService;
	@Resource
	private DataDictItemService dataDictItemService;
	@Resource
	private OrderService orderService;
	@Resource
	private WorkerOrderService workerOrderService;
	@Resource
	private WorkerService workerService;
	@Resource
	private ParkService parkService;
	@Resource
	private SysParamService sysParamService;
	@Resource
	private BaiduGeocoderApiService baiduGeocoderApiService;

	/**
	 * 进入车辆监控列表页面
	 * 
	 * @return
	 */
	@RequestMapping("toCarMonitorList")
	public String toCarMonitorList(Model model, String carPlateNo, String flag) {
		List<DataDictItem> cities = dataDictItemService.getDataDictItemListByCatCode("CITY");
		model.addAttribute("cities", cities);
		model.addAttribute("carPlateNo", carPlateNo);
		model.addAttribute("flag", flag);
		model.addAttribute("nowTime", new Date());
		SysParam sysp = sysParamService.getByParamKey(ECMgtConstant.MAP_REFRESH_INTERNAL);
		if (sysp != null && sysp.getParamValue() != null) {
			model.addAttribute("refreshinternal", sysp.getParamValue());
		}
		return "monitor/car_monitor";
	}

	/**
	 * 进入车辆监控列表页面
	 * 
	 * @return
	 */
	@RequestMapping("toCarMonitorListReal")
	public String toCarMonitorList2(Model model, String carPlateNo, String flag) {
		List<DataDictItem> cities = dataDictItemService.getDataDictItemListByCatCode("CITY");
		Car car = carService.getCarByPlateNo(carPlateNo);
		if (car!=null) {
			model.addAttribute("cityId", car.getCityId());
		}
		model.addAttribute("cities", cities);
		model.addAttribute("carPlateNo", carPlateNo);
		model.addAttribute("flag", flag);
		model.addAttribute("nowTime", new Date());
		SysParam sysp = sysParamService.getByParamKey(ECMgtConstant.MAP_REFRESH_INTERNAL);
		if (sysp != null && sysp.getParamValue() != null) {
			model.addAttribute("refreshinternal", sysp.getParamValue());
		}
		return "monitor/car_monitor_real";
	}

	/**
	 * 进入车辆监控列表页面 -- 虚拟演示环境
	 * 
	 * @return
	 */
	@RequestMapping("toCarMonitorListFake")
	public String toCarMonitorList_fake(Model model, String carPlateNo, String flag) {
		List<DataDictItem> cities = dataDictItemService.getDataDictItemListByCatCode("CITY");
		model.addAttribute("cities", cities);
		model.addAttribute("carPlateNo", carPlateNo);
		model.addAttribute("flag", flag);
		model.addAttribute("nowTime", new Date());
		return "monitor/car_monitor_fake";
	}

	/**
	 * 查询车辆监控列表
	 * 
	 * @param query
	 * @return
	 * @throws ParseException
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 */
	@RequestMapping("pageListCarMonitor")
	@ResponseBody
	public List<CarMonitor> pageListCarMonitor(@ModelAttribute("carStatus") CarStatus carStatus)
			throws ParseException, IllegalAccessException, InvocationTargetException {
		List<CarMonitor> carMonitorList = new ArrayList<CarMonitor>();
		Query q = new Query(carStatus);
		List<CarStatus> carStatusList = carStatusService.getCarStatusListForCarMonitor(q);
		for (CarStatus cs : carStatusList) {
			// 坐标转换
			if (cs.getLongitude() != null && cs.getLatitude() != null && cs.getLongitude().doubleValue() > 0 && cs.getLatitude().doubleValue() > 0) {
				double[] bdCoord = ECGeoCoordinateTransformUtil.wgs84tobd09(cs.getLongitude(), cs.getLatitude());
				cs.setLongitude(bdCoord[0]);// 经度（百度坐标）
				cs.setLatitude(bdCoord[1]);// 纬度（百度坐标）
			}
			CarMonitor carMonitor = new CarMonitor();
			BeanUtils.copyProperties(carMonitor, cs);
			if (cs.getUserageStatus() != null && cs.getUserageStatus() == 2) {
				Order order = new Order();
				order.setOrderStatus(2);// 已计费
				order.setCarNo(cs.getCarNo());
				Query q1 = new Query(order);
				List<Order> orderList = orderService.getOrderList(q1);
				if (orderList != null && orderList.size() > 0) {
					order = orderList.get(0);
					carMonitor.setOrder(order);
				}
			}
			if (cs.getUserageStatus() != null && cs.getUserageStatus() == 3) {
				WorkerOrder workerOrder = new WorkerOrder();
				workerOrder.setWorkOrderStatus(2);// 调度中
				workerOrder.setCarNo(cs.getCarNo());
				Query q2 = new Query(workerOrder);
				List<WorkerOrder> workerOrderList = workerOrderService.getWorkerOrderList(q2);
				if (workerOrderList != null && workerOrderList.size() > 0) {
					workerOrder = workerOrderList.get(0);
					Worker worker = workerService.getWorker(workerOrder.getWorkerId());
					if (worker.getMobilePhone() != null && !"".equals(worker.getMobilePhone())) {
						workerOrder.setMobilePhone(worker.getMobilePhone());
					}

					carMonitor.setWorkerOrder(workerOrder);
				}
			}
			if (cs.getUserageStatus() != null && cs.getUserageStatus() == 1) {
				WorkerOrder workerOrder = new WorkerOrder();
				workerOrder.setWorkOrderStatus(0);
				workerOrder.setCarNo(cs.getCarNo());
				Query q2 = new Query(workerOrder);
				List<WorkerOrder> workerOrderList = workerOrderService.getWorkerOrderList(q2);
				if (workerOrderList != null && workerOrderList.size() > 0) {
					workerOrder = workerOrderList.get(0);
					Worker worker = workerService.getWorker(workerOrder.getWorkerId());
					workerOrder.setMobilePhone(worker.getMobilePhone());
					carMonitor.setWorkerOrder(workerOrder);
				} else {
					Order order = new Order();
					order.setOrderStatus(1);
					order.setCarNo(cs.getCarNo());
					Query q1 = new Query(order);
					List<Order> orderList = orderService.getOrderList(q1);
					if (orderList != null && orderList.size() > 0) {
						order = orderList.get(0);
						carMonitor.setOrder(order);
					}
				}
			}

			if (carStatus != null && carStatus.getFlag() != null) {
				carMonitor.setFlag(carStatus.getFlag());
			}
			carMonitorList.add(carMonitor);
		}
		return carMonitorList;
	}

	@RequestMapping("getCarMonitorList")
	@ResponseBody
	public List<Map<String, Object>> getCarMonitorList(@ModelAttribute("carStatus") CarStatus carStatus)
			throws ParseException, IllegalAccessException, InvocationTargetException {
		List<Map<String, Object>> result = new ArrayList<>();
		Query q = new Query(carStatus);
		List<CarStatus> carStatusList = carStatusService.getCarMonitorList(q);
		for (CarStatus status : carStatusList) {
			Map<String, Object> map = new HashMap<>();
			if (status!=null&&status.getLongitude()!=null&&status.getLatitude()!=null) {
				double[] bdCoord = ECGeoCoordinateTransformUtil.wgs84tobd09(status.getLongitude(), status.getLatitude());
				status.setLongitude(bdCoord[0]);// 经度（百度坐标）
				status.setLatitude(bdCoord[1]);// 纬度（百度坐标）
				map.put("carPlateNo", status.getCarPlateNo());
				map.put("cityId", status.getCityId());
				map.put("cityName", status.getCityName());
				map.put("userageStatus", status.getUserageStatus());
				map.put("longitude", status.getLongitude());
				map.put("latitude", status.getLatitude());
				map.put("olineStatus", status.getOnlineStatus());
				map.put("deviceStatus", status.getDeviceStatus());
				if (status.getPower() != null && status.getPower() < 20) {
					map.put("isLowPower", 1);
				} else {
					map.put("isLowPower", 0);
				}
				if (status.getCarStatus() != null && status.getCarStatus().intValue() == 1 && status.getUserageStatus() == null
						&& !(status.getUserageStatus().intValue() == 2 || status.getUserageStatus().intValue() == 3)) {
					map.put("useCarStatus", 1);
				} else {
					map.put("useCarStatus", 0);
				}
				result.add(map);
			}
		}
		return result;
	}

	/**
	 * 查询所有场站信息
	 * 
	 * @param query
	 * @return
	 * @throws ParseException
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 */
	@RequestMapping("pageListPark")
	@ResponseBody
	public List<Map<String,Object>> pageListPark() throws ParseException, IllegalAccessException, InvocationTargetException {
		Park pSearch = new Park();
		pSearch.setIsAvailable(1);
		pSearch.setIsDeleted(0);
		List<Park> parkList = parkService.getParkList(new Query(pSearch));
		List<Map<String,Object>> result = new ArrayList<>();
		for(Park park : parkList){
			Map<String,Object> map = new HashMap<>();
			map.put("parkNo", park.getParkNo());
			map.put("parkName", park.getParkName());
			map.put("ploygonPoints", park.getPloygonPoints());
			result.add(map);
		}
		return result;
	}

	/**
	 * 查询车辆信息
	 */
	@RequestMapping("getCarMonitorInfo")
	@ResponseBody
	public Map<String, Object> getCarMonitorInfo(String carPlateNo) {
		Map<String, Object> map = new HashMap<>();
		if (ECStringUtils.isBlank(carPlateNo)) {
			return map;
		}
		CarStatus carStatus = new CarStatus();
		carStatus.setCarPlateNo(carPlateNo);
		Query q = new Query(carStatus);
		List<CarStatus> carStatusList = carStatusService.getCarStatusListForCarMonitor(q);
		if (!carStatusList.isEmpty()) {
			CarStatus o = carStatusList.get(0);
			map.put("carPlateNo", carPlateNo);
			map.put("memberName", "");
			map.put("orderNo", "");
			if (o.getLastReportingTime() != null) {
				map.put("loc_time", ECDateUtils.formatDate(o.getLastReportingTime()));
			} else {
				map.put("loc_time", "");
			}
			map.put("userageStatus", o.getUserageStatus());
			if (o.getUserageStatus() == null) {
				map.put("userageStatus", "");
			} else if (o.getUserageStatus() == 1) {
				WorkerOrder workerOrder = new WorkerOrder();
				workerOrder.setWorkOrderStatus(0); // 已预占
				workerOrder.setCarNo(o.getCarNo());
				q = new Query(workerOrder);
				List<WorkerOrder> workerOrderList = workerOrderService.getWorkerOrderList(q);
				if (workerOrderList != null && workerOrderList.size() > 0) {
					workerOrder = workerOrderList.get(0);
					map.put("memberName", workerOrder.getWorkerName());
					map.put("documentNo", workerOrder.getWorkerOrderNo());
				} else {
					Order order = new Order();
					order.setOrderStatus(1); // 已预占
					workerOrder.setCarNo(o.getCarNo());
					q = new Query(order);
					List<Order> orderList = orderService.getOrderList(q);
					if (orderList != null && orderList.size() > 0) {
						order = orderList.get(0);
						map.put("memberName", order.getMemberName());
						map.put("documentNo", order.getOrderNo());
					}
				}
			} else if (o.getUserageStatus() == 2) {
				Order order = new Order();
				order.setOrderStatus(2);// 已计费
				order.setCarNo(o.getCarNo());
				q = new Query(order);
				List<Order> orderList = orderService.getOrderList(q);
				if (orderList != null && orderList.size() > 0) {
					order = orderList.get(0);
					map.put("memberName", order.getMemberName());
					map.put("documentNo", order.getOrderNo());
				}
			} else if (o.getUserageStatus() == 3) {
				WorkerOrder workerOrder = new WorkerOrder();
				workerOrder.setWorkOrderStatus(2);// 调度中
				workerOrder.setCarNo(o.getCarNo());
				q = new Query(workerOrder);
				List<WorkerOrder> workerOrderList = workerOrderService.getWorkerOrderList(q);
				if (workerOrderList != null && workerOrderList.size() > 0) {
					workerOrder = workerOrderList.get(0);
					map.put("memberName", workerOrder.getWorkerName());
					map.put("documentNo", workerOrder.getWorkerOrderNo());
				}
			}
			if (o.getPower() != null) {
				map.put("power", o.getPower());
			} else {
				map.put("power", "");
			}
			if (o.getMileage() != null) {
				map.put("direction", o.getMileage());
			} else {
				map.put("direction", "");
			}
			if (o.getSpeed() != null) {
				map.put("speed", o.getSpeed());
			} else {
				map.put("speed", "");
			}
			// 坐标转换
			if (o.getLongitude() != null && o.getLatitude() != null && o.getLongitude().doubleValue() > 0 && o.getLatitude().doubleValue() > 0) {
				double[] bdCoord = ECGeoCoordinateTransformUtil.wgs84tobd09(o.getLongitude(), o.getLatitude());
				o.setLongitude(bdCoord[0]);// 经度（百度坐标）
				o.setLatitude(bdCoord[1]);// 纬度（百度坐标）
			}
			if (o.getLongitude() != null) {
				map.put("longitude", o.getLongitude());
			} else {
				map.put("longitude", "");
			}
			if (o.getLatitude() != null) {
				map.put("latitude", o.getLatitude());
			} else {
				map.put("latitude", "");
			}
			if (o.getLatitude() != null && o.getLongitude() != null) {
				String address = baiduGeocoderApiService.getAddressByGPS(o.getLatitude(), o.getLongitude());
				map.put("address", address);
			} else {
				map.put("address", "");
			}
		}
		return map;
	}

}
