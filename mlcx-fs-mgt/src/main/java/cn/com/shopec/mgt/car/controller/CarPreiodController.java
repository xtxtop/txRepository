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
import cn.com.shopec.core.car.model.CarPreiod;
import cn.com.shopec.core.car.model.CarSeries;
import cn.com.shopec.core.car.service.CarBrandService;
import cn.com.shopec.core.car.service.CarModelService;
import cn.com.shopec.core.car.service.CarPreiodService;
import cn.com.shopec.core.car.service.CarSeriesService;
import cn.com.shopec.mgt.common.BaseController;

@Controller
@RequestMapping("carPreiod")
public class CarPreiodController extends BaseController {

	@Resource
	public CarBrandService carBrandService;
	@Resource
	public CarSeriesService  carSeriesService;
	@Resource
	public CarPreiodService  carPreiodService;
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
	 * 显示车辆年代列表页
	 */
	@RequestMapping("toCarPreiodList")
	public String mainPage(ModelMap map) {
		List<CarBrand> carBrandList = carBrandService.getCarBrandList(new Query());
		map.put("carBrandList", carBrandList);
		return "car/car_preiod_list";
	}

	/*
	 * 显示车辆年代列表页
	 */
	@RequestMapping("pageListCarPreiod")
	@ResponseBody
	public PageFinder<CarPreiod> pageListCarPreiod(@ModelAttribute("carPreiod") CarPreiod carPreiod, Query query) {
		Query q = new Query(query.getPageNo(), query.getPageSize(), carPreiod);
		return carPreiodService.getCarPreiodPagedList(q);
	}

	/*
	 * 显示车辆年代增加页
	 */
	@RequestMapping("toCarPreiodAdd")
	public String toCarPreiodAdd(ModelMap map) {
		List<CarBrand> carBrandList = carBrandService.getCarBrandList(new Query());
		map.put("carBrandList", carBrandList);
		return "car/car_preiod_add";
	}

	/*
	 * 显示车辆年代修改页
	 */
	@RequestMapping("toCarPreiodEdit")
	public String toCarPreiodEdit(String carPreiodId, ModelMap map) {
		List<CarBrand> carBrandList = carBrandService.getCarBrandList(new Query());
		map.put("carBrandList", carBrandList);
		CarPreiod carPreiod = carPreiodService.getCarPreiod(carPreiodId);
		map.put("carPreiod", carPreiod);
		return "car/car_preiod_edit";
	}

	/*
	 * 新增或修改车辆年代
	 */
	@RequestMapping("editCarPreiod")
	@ResponseBody
	public ResultInfo<CarPreiod> editCarPreiod(@ModelAttribute("carPreiod") CarPreiod carPreiod) {
		ResultInfo<CarPreiod> resultInfo = new ResultInfo<CarPreiod>();
		if (carPreiod.getCarPeriodId() != null && carPreiod.getCarPeriodId().length() != 0) {
			resultInfo = carPreiodService.updateCarPreiod(carPreiod, getOperator());
		} else {
			resultInfo = carPreiodService.addCarPreiod(carPreiod, getOperator());
		}
		return resultInfo;
	}

	/*
	 * 删除车辆年代
	 */
	@RequestMapping("delCarPreiod")
	@ResponseBody
	public ResultInfo<CarPreiod> delCarPreiod(String carPeriodId) {
		ResultInfo<CarPreiod> result = new ResultInfo<CarPreiod>();
		CarModel carModel = new CarModel();
		carModel.setCarPeriodId(carPeriodId);
		carModel.setOnOffLineStatus(1);
		List<CarModel> carModelList = carModelService.getCarModelList(new Query(carModel));
		if(carModelList.size()>0){
			result.setCode(Constant.FAIL);
			result.setMsg("上架车型中有当前车辆年代，不能删除");
			return result;
		}else{
			return carPreiodService.delCarPreiod(carPeriodId, getOperator());
		}
	}

	/*
	 * 验证车辆年代名称是否重复
	 */
	@RequestMapping("carPeriodNameCheck")
	@ResponseBody
	public ResultInfo<String> carPreiodNameCheck(String carBrandId,String carSeriesId,String carPeriodName,String carPeriodId) {
		ResultInfo<String> resultInfo = new ResultInfo<String>();
		CarPreiod preiod = new CarPreiod();
		preiod.setCarBrandId(carBrandId);
		preiod.setCarSeriesId(carSeriesId);
		preiod.setCarPeriodName(carPeriodName);
		List<CarPreiod> list = carPreiodService.getCarPreiodList(new Query(preiod));
		if (list.size() > 0) {
			if(carPeriodId!=null&&!"".equals(carPeriodId)){
				CarPreiod carPreiod = list.get(0);
				if(carPreiod!=null&&carPeriodId.equals(carPreiod.getCarPeriodId())){
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
	/*
	 * 根据车辆品牌查找车系
	 */
	@RequestMapping("getCarSeriesByBrandId")
	@ResponseBody
	public ResultInfo<List<CarSeries>> getCarSeriesNameByBrandId(String carBrandId) {
		ResultInfo<List<CarSeries>> resultInfo = new ResultInfo<List<CarSeries>>();
		CarSeries series = new CarSeries();
		series.setCarBrandId(carBrandId);
		List<CarSeries> list = carSeriesService.getCarSeriesList(new Query(series));
		if (list.size() > 0) {
			resultInfo.setData(list);
			resultInfo.setCode(Constant.YES + "");
		} else {
			resultInfo.setCode(Constant.NO + "");
		}
		return resultInfo;
	}
	/*
	 * 根据车系查找车辆年代
	 */
	@RequestMapping("getCarPeriodsBySeriesId")
	@ResponseBody
	public ResultInfo<List<CarPreiod>> getCarPeriodsBySeriesId(String carSeriesId) {
		ResultInfo<List<CarPreiod>> resultInfo = new ResultInfo<List<CarPreiod>>();
		CarPreiod period = new CarPreiod();
		period.setCarSeriesId(carSeriesId);;
		List<CarPreiod> list = carPreiodService.getCarPreiodList(new Query(period));
		if (list.size() > 0) {
			resultInfo.setData(list);
			resultInfo.setCode(Constant.YES + "");
		} else {
			resultInfo.setCode(Constant.NO + "");
		}
		return resultInfo;
	}
}
