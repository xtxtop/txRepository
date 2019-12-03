package cn.com.shopec.mgt.dailyrental.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.common.constants.Constant;
import cn.com.shopec.common.utils.ECDateUtils;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.car.model.CarBrand;
import cn.com.shopec.core.dailyrental.model.CarInventory;
import cn.com.shopec.core.car.model.CarModel;
import cn.com.shopec.core.car.model.CarPreiod;
import cn.com.shopec.core.car.model.CarSeries;
import cn.com.shopec.core.dailyrental.model.DayCarArea;
import cn.com.shopec.core.dailyrental.model.Merchant;
import cn.com.shopec.core.dailyrental.model.MerchantInventory;
import cn.com.shopec.core.dailyrental.model.MerchantUser;
import cn.com.shopec.core.dailyrental.model.ParkDay;
import cn.com.shopec.core.dailyrental.model.PricingRuleDay;
import cn.com.shopec.core.car.service.CarBrandService;
import cn.com.shopec.core.dailyrental.service.CarInventoryService;
import cn.com.shopec.core.car.service.CarModelService;
import cn.com.shopec.core.car.service.CarPreiodService;
import cn.com.shopec.core.car.service.CarSeriesService;
import cn.com.shopec.core.dailyrental.service.DayCarAreaService;
import cn.com.shopec.core.dailyrental.service.MerchantInventoryService;
import cn.com.shopec.core.dailyrental.service.OrderDayService;
import cn.com.shopec.core.dailyrental.service.PricingRuleDayService;
import cn.com.shopec.core.resource.common.ParkConstant;
import cn.com.shopec.core.resource.model.ReturnArea;
import cn.com.shopec.core.system.model.DataDictItem;
import cn.com.shopec.core.system.model.SysParam;
import cn.com.shopec.core.system.model.SysRegion;
import cn.com.shopec.core.system.service.SysParamService;
import cn.com.shopec.core.system.service.SysRegionService;
import cn.com.shopec.mgt.common.BaseController;

@Controller
@RequestMapping("dayCarArea")
public class DayCarAreaController extends BaseController {
	@Resource
	private  DayCarAreaService  dayCarAreaService;
	@Resource
	private SysRegionService sysRegionService;
	
	/*
	 * 显示服务列表页
	 */
	@RequestMapping("/mainPage")
	public String mainPage() {
		return "dailyrental/car/dayCarArea_list";
	}

	
	/*
	 * 显示服务范围列表分页
	 */
	@RequestMapping("/pageListDayCarArea")
	@ResponseBody
	public PageFinder<DayCarArea> pageListDayCarArea(@ModelAttribute("DayCarArea") DayCarArea dayCarArea,Query query) {
		Query q = new Query(query.getPageNo(),query.getPageSize(),dayCarArea);
		return dayCarAreaService.getDayCarAreaPagedList(q);
	}
	
	
	/*
	 * 新增服务区域页
	 */
	@RequestMapping("/toAddDayCarArea")
	public String toAddDayCarArea(ModelMap modelMap) {
		List<SysRegion> provinces = sysRegionService.getProvices();// 省
		modelMap.put("provinces", provinces);
		return "dailyrental/car/dayCarArea_add";
	}
	
	/*
	 * 新增或修改服务片区
	 */
	@RequestMapping("addOrEditDayCarArea")
	@ResponseBody
	public ResultInfo<DayCarArea> addOrEditDayCarArea(@ModelAttribute("dayCarArea") DayCarArea dayCarArea) {
		ResultInfo<DayCarArea> resultInfo = new ResultInfo<DayCarArea>();
		if (dayCarArea.getCarAreaId() != null && !"".equals(dayCarArea.getCarAreaId())) {
			resultInfo=dayCarAreaService.updateDayCarArea(dayCarArea, getOperator());
		} else {
			dayCarArea.setIsAvailable(0);
			resultInfo = dayCarAreaService.addDayCarArea(dayCarArea, getOperator());
		}
		return resultInfo;
	}
	
	
	/*
	 * 停用或启用服务片区
	 */
	@RequestMapping("toEditDayCarArea")
	@ResponseBody
	public ResultInfo<DayCarArea> toEditDayCarArea(
			@ModelAttribute("carAreaId") String  carAreaId,@ModelAttribute("isAvailable") String  isAvailable) {
		ResultInfo<DayCarArea> resultInfo = new ResultInfo<DayCarArea>();
		DayCarArea dayCarArea=new DayCarArea();
		dayCarArea.setIsAvailable(Integer.valueOf(isAvailable));
		dayCarArea.setCarAreaId(carAreaId);
		resultInfo = dayCarAreaService.updateDayCarArea(dayCarArea, getOperator());
		return resultInfo;
	}
	
	/*
	 * 服务区详情页
	 */
	@RequestMapping("/toDayCarAreaView")
	public String toDayCarAreaView(ModelMap modelMap,@ModelAttribute("carAreaId") String  carAreaId) {
		DayCarArea dc=dayCarAreaService.getDayCarArea(carAreaId);
		modelMap.put("dayCarArea", dc);
		return "dailyrental/car/dayCarArea_view";
	}
	
	/*
	 * 修改服务区域页
	 */
	@RequestMapping("/toEditAddDayCarArea")
	public String toEditAddDayCarArea(ModelMap map,String carAreaId) {
		List<SysRegion> provinces = sysRegionService.getProvices();// 省
		map.put("provinces", provinces);
		DayCarArea dc=dayCarAreaService.getDayCarArea(carAreaId);
		List<SysRegion> cities = sysRegionService.getCitys(dc.getAddrRegion1Id());//对应省份下的城市
		map.put("cities", cities);
		List<SysRegion> districts = sysRegionService.getCountrys(dc.getAddrRegion2Id());//对应市下的区域
		map.put("districts", districts);
		map.put("dayCarArea", dc);
		return "dailyrental/car/dayCarArea_edit";
	}
	
	
	
}
