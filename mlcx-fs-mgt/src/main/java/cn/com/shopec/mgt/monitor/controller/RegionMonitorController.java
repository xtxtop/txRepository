package cn.com.shopec.mgt.monitor.controller;

import java.text.ParseException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.com.shopec.mgt.common.BaseController;

@Controller
@RequestMapping("/regionMonitor")
public class RegionMonitorController extends BaseController {
	
	@RequestMapping("toRegionMonitor")
	public String toRegionList(Model model)
			throws ParseException {
		return "monitor/region_monitor";
	}
}
