package cn.com.shopec.mgt.car.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.shopec.core.car.model.CarStatus;
import cn.com.shopec.core.car.service.CarStatusService;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.system.model.SysParam;
import cn.com.shopec.core.system.service.SysParamService;
import cn.com.shopec.mgt.common.BaseController;

@Controller
@RequestMapping("carIdleStatus")
public class CarIdleStatusController extends BaseController {

	@Resource
	private CarStatusService carStatusService;
	@Resource
	private SysParamService sysParamService;
	/*
	 * 显示空闲车辆状态列表页
	 */
	@RequestMapping("toCarIdleStatusList")
	public String toCarStatusList(Model model) {
		return "car/car_idle_list";
	}

	/*
	 * 空闲车辆状态列表分页
	 */
	@RequestMapping("pageListCarIdleStatus")
	@ResponseBody
	public PageFinder<CarStatus> pageListCarStatus(@ModelAttribute("carStatus") CarStatus carStatus, Query query) {
		Double param = 20.0;
		SysParam sysParam = sysParamService.getByParamKey("CarPowerParam");
		if(sysParam != null){
			param = Double.parseDouble(sysParam.getParamValue());
		}
		carStatus.setUserageStatus(0);
		carStatus.setPowerSmall(param);
		Query q = new Query(query.getPageNo(), query.getPageSize(), carStatus);
		return carStatusService.getCarIdleStatusPageList(q);
	}
	
}
