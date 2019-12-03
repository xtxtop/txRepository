package cn.com.shopec.mgt.car.controller;

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
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.car.model.CarBrand;
import cn.com.shopec.core.car.model.CarModel;
import cn.com.shopec.core.car.model.CarSeries;
import cn.com.shopec.core.car.service.CarBrandService;
import cn.com.shopec.core.car.service.CarModelService;
import cn.com.shopec.core.car.service.CarSeriesService;
import cn.com.shopec.mgt.common.BaseController;

@Controller
@RequestMapping("/carBrand")
public class CarBrandController extends BaseController {

	@Resource
	public CarBrandService carBrandService;
	@Resource
	public CarSeriesService carSeriesService;
	@Resource
	public CarModelService carModelService;

	@Override
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(true);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}

	/*
	 * 显示车辆品牌列表页
	 */
	@RequestMapping("/toCarBrandList")
	public String mainPage() {
		return "car/car_brand_list";
	}

	/*
	 * 显示车辆品牌列表页
	 */
	@RequestMapping("/pageListCarBrand")
	@ResponseBody
	public PageFinder<CarBrand> pageListCarBrand(@ModelAttribute("carBrand") CarBrand carBrand, Query query) {
		Query q = new Query(query.getPageNo(), query.getPageSize(), carBrand);
		return carBrandService.getCarBrandPagedList(q);
	}

	/*
	 * 显示车辆增加页
	 */
	@RequestMapping("/toCarBrandAdd")
	public String toCarBrandAdd() {

		return "car/car_brand_add";
	}

	/*
	 * 显示车辆修改页
	 */
	@RequestMapping("/toCarBrandEdit")
	public String toCarBrandEdit(String carBrandId, ModelMap map) {
		CarBrand carBrand = carBrandService.getCarBrand(carBrandId);
		map.put("carBrand", carBrand);
		return "car/car_brand_edit";
	}

	/*
	 * 新增或修改车辆品牌
	 */
	@RequestMapping("/editCarBrand")
	@ResponseBody
	public ResultInfo<CarBrand> editCarBrand(@ModelAttribute("carBrand") CarBrand carBrand) {
		ResultInfo<CarBrand> resultInfo = new ResultInfo<CarBrand>();
		if (carBrand.getCarBrandId() != null && carBrand.getCarBrandId().length() != 0) {
			resultInfo = carBrandService.updateCarBrand(carBrand, getOperator());
		} else {
			resultInfo = carBrandService.addCarBrand(carBrand, getOperator());
		}
		return resultInfo;
	}

	/*
	 * 删除车辆品牌
	 */
	@RequestMapping("/delCarBrand")
	@ResponseBody
	public ResultInfo<CarBrand> delCarBrand(String carBrandId) {
		ResultInfo<CarBrand> result = new ResultInfo<CarBrand>();
		CarSeries carSeries = new CarSeries();
		carSeries.setCarBrandId(carBrandId);
		List<CarSeries> carSeriesList = carSeriesService.getCarSeriesList(new Query(carSeries));
		if(carSeriesList.size()>0){
			result.setCode(Constant.FAIL);
			result.setMsg("有车系隶属于当前车辆品牌，不能删除");
			return result;
		}else{
			CarModel carModel = new CarModel();
			carModel.setCarBrandId(carBrandId);
			carModel.setOnOffLineStatus(1);
			List<CarModel> carModelList = carModelService.getCarModelList(new Query(carModel));
			if(carModelList.size()>0){
				result.setCode(Constant.FAIL);
				result.setMsg("上架车型中有当前车辆品牌，不能删除");
				return result;
			}else{
				return carBrandService.delCarBrand(carBrandId, getOperator());
			}
		}
	}

	/*
	 * 验证车辆品牌是否重复
	 */
	@RequestMapping("/carBrandNameCheck")
	@ResponseBody
	public ResultInfo<String> carBrandNameCheck(String carBrandName,String carBrandId) {
		ResultInfo<String> resultInfo = new ResultInfo<String>();
		CarBrand brand = new CarBrand();
		brand.setCarBrandName(carBrandName);
		List<CarBrand> list = carBrandService.getCarBrandList(new Query(brand));
		if (list.size() > 0) {
			if(carBrandId!=null&&!"".equals(carBrandId)){
				CarBrand carBrand = list.get(0);
				if(carBrand!=null&&carBrandId.equals(carBrand.getCarBrandId())){
					resultInfo.setCode(Constant.NO + "");
				}else{
					resultInfo.setCode(Constant.YES + "");
				}
			}else{
				resultInfo.setCode(Constant.YES + "");
			}
		} else {
			resultInfo.setCode(Constant.NO + "");
		}
		return resultInfo;
	}

}
