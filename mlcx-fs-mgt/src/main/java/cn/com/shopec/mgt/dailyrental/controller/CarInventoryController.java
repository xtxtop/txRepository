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

import cn.com.shopec.core.car.service.CarExtraCostsService;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.car.model.CarBrand;
import cn.com.shopec.core.dailyrental.model.CarInventory;
import cn.com.shopec.core.car.model.CarModel;
import cn.com.shopec.core.car.service.CarBrandService;
import cn.com.shopec.core.dailyrental.service.CarInventoryService;
import cn.com.shopec.core.car.service.CarModelService;
import cn.com.shopec.core.dailyrental.service.PricingRuleDayService;
import cn.com.shopec.core.resource.service.ParkService;
import cn.com.shopec.core.system.model.DataDictItem;
import cn.com.shopec.core.system.service.DataDictItemService;
import cn.com.shopec.mgt.common.BaseController;

@Controller
@RequestMapping("/carInventory")
public class CarInventoryController extends BaseController {

	@Resource
	public CarInventoryService carInventoryService;
	@Resource
	public DataDictItemService dataDictItemService;
	@Resource
	public CarBrandService carBrandService;
	@Resource
	public CarModelService carModelService;
	@Resource
	public ParkService parkService;
	@Resource
	public PricingRuleDayService pricingRuleDayService;
	@Resource
	public CarExtraCostsService carExtraCostsService;

	@Override
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(true);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}

	/*
	 * 显示车辆库存列表页
	 */
	@RequestMapping("/toCarInventory")
	public String mainPage(ModelMap modelMap) {
		List<DataDictItem> cities = dataDictItemService.getDataDictItemListByCatCode("CITY");// 城市
		modelMap.put("cities", cities);
		List<CarBrand> carBrands = carBrandService.getCarBrandList(new Query());// 汽车品牌
		modelMap.put("carBrands", carBrands);
		List<CarModel> carModels = carModelService.getCarModelList(new Query());// 汽车型号
		modelMap.put("carModels", carModels);
	
		return "dailyrental/car/car_inventory_list";
	}

	/*
	 * 显示车辆库存列表页
	 */
	@RequestMapping("/pageListCarInventory")
	@ResponseBody
	public PageFinder<CarInventory> pageListCarInventory(@ModelAttribute("carInventory") CarInventory carInventory,
			Query query) {
		Query q = new Query(query.getPageNo(), query.getPageSize(), carInventory);
		return carInventoryService.getCarInventoryPagedListForMgt(q);
	}

	/*
	 * 显示车辆库存详情页
	 */
	@RequestMapping("/toCarInventoryView")
	public String toCarInventoryView(String carInventoryId, ModelMap modelMap) {
		CarInventory carInventory = carInventoryService.getCarInventory(carInventoryId);
		modelMap.put("carInventory", carInventory);

		return "dailyrental/car/car_inventory_view";
	}

}
