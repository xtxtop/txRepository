package cn.com.shopec.mgt.monitor.controller;

import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.resource.model.Park;
import cn.com.shopec.core.resource.service.ParkService;
import cn.com.shopec.core.system.model.DataDictItem;
import cn.com.shopec.core.system.service.DataDictItemService;
import cn.com.shopec.mgt.common.BaseController;

/**
 * 场站监控
 * @author lyf
 *
 */
@Controller
@RequestMapping("/parkMonitor")
public class ParkMonitorController extends BaseController{
	@Resource
	private ParkService parkService;
	@Resource
	private DataDictItemService dataDictItemService;
	
	/**
	 * 进入场站监控列表页面
	 * 
	 * @return
	 */
	@RequestMapping("toParkMonitorList")
	public String toParkMonitorList(Model model) {
		//查询全部城市，后期会做出单城市区分
		List<DataDictItem> cities = dataDictItemService.getDataDictItemListByCatCode("CITY");
		model.addAttribute("cities", cities);
		model.addAttribute("nowTime",new Date());
		return "monitor/park_monitor";
	}
	
	/**
	 * 查询所有场站信息 -- 关联查询了车辆数
	 * 
	 * @param query
	 * @return
	 * @throws ParseException
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 */
	@RequestMapping("pageListPark")
	@ResponseBody
	public List<Park> pageListPark(@ModelAttribute("park") Park pSearch)throws ParseException, IllegalAccessException, InvocationTargetException {
		//pSearch.setIsAvailable(1);
		pSearch.setIsDeleted(0);
		List<Park> parkList = parkService.getParkListAndCar(new Query(pSearch));
		return parkList;
	}
	
	@RequestMapping("getMonitorParkList")
	@ResponseBody
	public List<Map<String,Object>> getMonitorParkList(@ModelAttribute("park") Park park){
		park.setIsDeleted(0);
		List<Park> parkList = parkService.getMonitorParkList(new Query(park));
		List<Map<String,Object>> result = new ArrayList<>();
		for(Park o : parkList){
			Map<String,Object> map = new HashMap<>();
			map.put("parkNo", o.getParkNo());
			map.put("parkName", o.getParkName());
			map.put("cityName", o.getCityName());
			map.put("longitude", o.getLongitude());
			map.put("latitude", o.getLatitude());
			map.put("ploygonPoints", o.getPloygonPoints());
			map.put("carNumber", o.getCarNumber());
			map.put("lowPowerCarNumber", o.getLowPowerCarNumber());
			map.put("parkingSpaceNumber", o.getParkingSpaceNumber());
			map.put("chargerNumber", o.getChargerNumber());
			map.put("parkRent", o.getParkRent());
			map.put("supportedServices", o.getSupportedServices());
			String address = o.getAddrStreet() == null ? "" : o.getAddrStreet();
			if(address.indexOf(o.getAddrRegion1Name()) != 0){
				address = o.getAddrRegion1Name() + o.getAddrRegion2Name() + o.getAddrRegion3Name() + address;
			}
			map.put("address", address);
			result.add(map);
		}
		return result;
	}
	
}
