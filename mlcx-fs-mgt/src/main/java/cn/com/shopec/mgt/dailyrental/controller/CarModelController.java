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
import cn.com.shopec.core.dailyrental.model.MerchantInventory;
import cn.com.shopec.core.dailyrental.model.PricingRuleDay;
import cn.com.shopec.core.car.service.CarBrandService;
import cn.com.shopec.core.dailyrental.service.CarInventoryService;
import cn.com.shopec.core.car.service.CarModelService;
import cn.com.shopec.core.car.service.CarPreiodService;
import cn.com.shopec.core.car.service.CarSeriesService;
import cn.com.shopec.core.dailyrental.service.MerchantInventoryService;
import cn.com.shopec.core.dailyrental.service.OrderDayService;
import cn.com.shopec.core.dailyrental.service.PricingRuleDayService;
import cn.com.shopec.mgt.common.BaseController;

@Controller
@RequestMapping("carModel")
public class CarModelController extends BaseController {

	@Resource
	public CarModelService carModelService;
	@Resource
	public CarBrandService carBrandService;
	@Resource
	public OrderDayService orderDayService;
	@Resource
	public PricingRuleDayService pricingRuleDayService;
	@Resource
	public CarSeriesService carSeriesService;
	@Resource
	public CarPreiodService carPreiodService;
	@Resource
	public MerchantInventoryService merchantInventoryService;
	@Resource
	public CarInventoryService carInventoryService;
	
	@Override
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(true);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}

	/*
	 * 显示车辆型号列表页
	 */
	@RequestMapping("toCarModelList")
	public String mainPage(ModelMap map) {
		List<CarBrand> lb = carBrandService.getCarBrandList(new Query());
		map.put("lb", lb);
		return "dailyrental/car/car_model_list";
	}

	/*
	 * 显示车辆型号列表页
	 */
	@RequestMapping("pageListCarModel")
	@ResponseBody
	public PageFinder<CarModel> pageListCarModel(@ModelAttribute("carModel") CarModel carModel, Query query) {
		Query q = new Query(query.getPageNo(), query.getPageSize(), carModel);
		return carModelService.getCarModelPagedList(q);
	}

	/*
	 * 显示车辆增加页
	 */
	@RequestMapping("toCarModelAdd")
	public String toCarModelAdd(ModelMap map) {

		List<CarBrand> lb = carBrandService.getCarBrandList(new Query());
		map.put("lb", lb);
		return "dailyrental/car/car_model_add";
	}

	/*
	 * 车型修改页面
	 */

	@RequestMapping("toCarModelEdit")
	public String toCarModelEdit(String carModelId, ModelMap map) {
		CarModel model = carModelService.getCarModel(carModelId);
		map.put("model", model);

		List<CarBrand> lb = carBrandService.getCarBrandList(new Query());
		map.put("lb", lb);

		return "dailyrental/car/car_model_edit";
	}
	/*
	 * 车型详情页面
	 */

	@RequestMapping("toCarModelView")
	public String toCarModelView(String carModelId, ModelMap map) {
		CarModel model = carModelService.getCarModel(carModelId);
		map.put("model", model);
		return "dailyrental/car/car_model_view";
	}

	/*
	 * 新增或修改车辆品牌
	 */
	@RequestMapping("editCarModel")
	@ResponseBody
	public ResultInfo<CarModel> editCarModel(@ModelAttribute("carModel") CarModel carModel) {
		ResultInfo<CarModel> resultInfo = new ResultInfo<CarModel>();
		if (carModel.getCarModelId() != null && carModel.getCarModelId().length() != 0) {
			resultInfo = carModelService.updateCarModel(carModel, getOperator());
			//后台车型基础数据做了修改，同步修改车型库存中的车型
			CarInventory cinventory = carInventoryService.getCarInventoryByCarModelId(carModel.getCarModelId());
			if(cinventory != null){
				cinventory.setCarModelName(carModel.getCarModelName());
				cinventory.setCarType(carModel.getCarType());
				cinventory.setSeatNumber(carModel.getSeatNumber());
				cinventory.setGearBox(carModel.getGearBox());
				cinventory.setDisplacement(carModel.getDisplacement());
				cinventory.setCarModelUrl(carModel.getCarModelUrl());
				carInventoryService.updateCarInventory(cinventory, getOperator());
			}
			//后台车型基础数据做了修改，同步修改商家库存中的车型
			MerchantInventory inventory = new MerchantInventory();
			inventory.setCarModelId(carModel.getCarModelId());
			inventory.setCarModelName(carModel.getCarModelName());
			inventory.setBoxType(Integer.parseInt(carModel.getBoxType()));
			inventory.setCarType(carModel.getCarType());
			inventory.setSeatNumber(carModel.getSeatNumber());
			inventory.setGearBox(carModel.getGearBox());
			inventory.setDisplacement(carModel.getDisplacement());
			inventory.setCarModelUrl(carModel.getCarModelUrl());
			merchantInventoryService.updateMerchantInventoryByCarModelId(inventory);
		} else {
			resultInfo = carModelService.addCarModel(carModel, getOperator());
		}
		return resultInfo;
	}

	/*
	 * 删除车辆型号
	 */
	@RequestMapping("delCarModel")
	@ResponseBody
	public ResultInfo<CarModel> delCarModel(String carModelId) {
		return carModelService.delCarModel(carModelId, getOperator());
	}

	/*
	 * 验证车型是否重复
	 */
	@RequestMapping("carModelNameCheck")
	@ResponseBody
	public ResultInfo<String> carModelNameCheck(String carModelId,String carModelName,String carBrandId,String carSeriesId,String carPeriodId,Integer carType) {
		ResultInfo<String> resultInfo = new ResultInfo<String>();
		CarModel carModel = new CarModel();
		carModel.setCarBrandId(carBrandId);
		carModel.setCarSeriesId(carSeriesId);
		carModel.setCarPeriodId(carPeriodId);
		carModel.setCarType(carType);
		carModel.setOnOffLineStatus(1);
		List<CarModel> list = carModelService.getCarModelList(new Query(carModel));
		if (list.size() > 0) {
			CarModel model = list.get(0);
			if(carModelId!=null&&!"".equals(carModelId)){
				if(carModelId.equals(model.getCarModelId())){
					CarModel carModelQ = new CarModel();
					carModelQ.setCarModelName(carModelName);
					List<CarModel> lists = carModelService.getCarModelList(new Query(carModelQ));
					if (lists.size() > 0) {
						model = lists.get(0);
						if(!carModelId.equals(model.getCarModelId())){
							resultInfo.setCode("2");
						}else{
							resultInfo.setCode(Constant.NO + "");
						}
					}else{
						resultInfo.setCode(Constant.NO + "");
					}
				}else{
					resultInfo.setCode(Constant.YES + "");
				}
			}else{
				resultInfo.setCode(Constant.YES + "");
			}
		} else {
			CarModel carModelQ = new CarModel();
			carModelQ.setCarModelName(carModelName);
			List<CarModel> lists = carModelService.getCarModelList(new Query(carModelQ));
			if (lists.size() > 0) {
				if(carModelId!=null&&!"".equals(carModelId)){
					CarModel model = lists.get(0);
					if(!carModelId.equals(model.getCarModelId())){
						resultInfo.setCode("2");
					}else{
						resultInfo.setCode(Constant.NO + "");
					}
				}else{
					resultInfo.setCode("2");
				}
			}else{
				resultInfo.setCode(Constant.NO + "");
			}
		}
		return resultInfo;
	}
	/**
	 * 获取车型对象
	 * 
	 * @return
	 */
	@RequestMapping("getCarModel")
	@ResponseBody
	public ResultInfo<CarModel> getCarModel(String carModelId) {
		ResultInfo<CarModel> result = new ResultInfo<CarModel>();
		CarModel carModel = null;
		if (carModelId != null && carModelId.length() != 0) {
			carModel = carModelService.getCarModel(carModelId);
			result.setCode(Constant.SECCUESS);
			result.setData(carModel);
		}else{
			result.setCode(Constant.FAIL);
			result.setMsg("车型不存在");
		}
		return result;
	}
	/**
	 * 车型上下架
	 * @return
	 */
	@RequestMapping("onOffLineCarModel")
	@ResponseBody
	public ResultInfo<CarModel> onOffLineCarModel(@ModelAttribute("carModel") CarModel carModel) {
		ResultInfo<CarModel> result = new ResultInfo<CarModel>();
		if(carModel.getOnOffLineStatus()!=null){
			if(carModel.getOnOffLineStatus()==0){
				//对应车型订单中的个数
				int count = orderDayService.countOrderByCarModelId(carModel.getCarModelId());
				if(count>0){
					result.setCode(Constant.FAIL);
					result.setMsg("此车型有进行中订单，无法下架");
					return result;
				}else{
					carModel.setOnOffLineStatusUpdateTime(ECDateUtils.getCurrentDateTime());
					return carModelService.updateCarModel(carModel, getOperator());
				}
			}else{
				CarModel carModelQuery = carModelService.getCarModel(carModel.getCarModelId());
				CarBrand carBrand = carBrandService.getCarBrand(carModelQuery.getCarBrandId());
				if(carBrand==null){
					result.setCode(Constant.FAIL);
					result.setMsg("此车型车辆品牌已删除，无法上架");
					return result;
				}
				CarSeries carSeries = carSeriesService.getCarSeries(carModelQuery.getCarSeriesId());
				if(carSeries==null){
					result.setCode(Constant.FAIL);
					result.setMsg("此车型车系已删除，无法上架");
					return result;
				}
				CarPreiod carPreiod = carPreiodService.getCarPreiod(carModelQuery.getCarPeriodId());
				if(carPreiod==null){
					result.setCode(Constant.FAIL);
					result.setMsg("此车型车辆年代已删除，无法上架");
					return result;
				}
				PricingRuleDay ruleQ = new PricingRuleDay();
				ruleQ.setCarModelId(carModelQuery.getCarModelId());
				ruleQ.setCarType(carModelQuery.getCarType());
				ruleQ.setIsAvailable(1);
				List<PricingRuleDay> rules = pricingRuleDayService.getPricingRuleDayList(new Query(ruleQ));
				if(rules.size()>0){
					carModel.setOnOffLineStatusUpdateTime(ECDateUtils.getCurrentDateTime());
					return carModelService.updateCarModel(carModel, getOperator());
				}else{
					result.setCode(Constant.FAIL);
					result.setMsg("请先添加对应车型的计费规则");
					return result;
				}
			}
		}else{
			result.setCode(Constant.FAIL);
			result.setMsg("无法启用");
			return result;
		}
	}
}
