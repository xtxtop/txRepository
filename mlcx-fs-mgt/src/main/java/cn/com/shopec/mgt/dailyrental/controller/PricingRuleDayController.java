package cn.com.shopec.mgt.dailyrental.controller;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.shopec.common.Operator;
import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.common.constants.Constant;
import cn.com.shopec.common.utils.ECDateUtils;
import cn.com.shopec.core.car.service.CarService;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.car.model.CarModel;
import cn.com.shopec.core.dailyrental.model.PricingRuleCustomDate;
import cn.com.shopec.core.dailyrental.model.PricingRuleDay;
import cn.com.shopec.core.car.service.CarModelService;
import cn.com.shopec.core.dailyrental.service.PricingRuleCustomDateService;
import cn.com.shopec.core.dailyrental.service.PricingRuleDayService;
import cn.com.shopec.core.system.model.DataDictItem;
import cn.com.shopec.core.system.model.SysUser;
import cn.com.shopec.core.system.service.DataDictItemService;
import cn.com.shopec.core.system.service.SysUserService;
import cn.com.shopec.mgt.common.BaseController;

@Controller
@RequestMapping("pricingRuleDay")
public class PricingRuleDayController extends BaseController {

	@Resource
	private DataDictItemService dataDictItemService;
	@Resource
	private PricingRuleDayService pricingRuleDayService;
	@Resource
	private CarModelService carModelService;
	@Resource
	private SysUserService sysUserService;
	@Resource
	private CarService carService;
	@Resource
	private PricingRuleCustomDateService pricingRuleCustomDateService;

	/**
	 * 日租计费规则页面
	 * 
	 * @return
	 */
	@RequestMapping("toPricingRuleDayList")
	public String toPricingRuleDayList(ModelMap modelMap) {
		List<DataDictItem> cities = dataDictItemService.getDataDictItemListByCatCode("CITY");// 城市
		modelMap.put("cities", cities);
		List<CarModel> carModels = carModelService.getCarModelList(new Query());// 汽车型号
		modelMap.put("carModels", carModels);
		return "dailyrental/pricingrule/pricingRuleDay_list";
	}

	/**
	 * 日租计费规则页面分页
	 * 
	 * @return
	 */
	@RequestMapping("pageListPricingRuleDay")
	@ResponseBody
	public PageFinder<PricingRuleDay> pageListPricingRuleDay(
			@ModelAttribute("pricingRuleDay") PricingRuleDay pricingRuleDay, Query query) {
		Query q = new Query(query.getPageNo(), query.getPageSize(), pricingRuleDay);
		return pricingRuleDayService.getPricingRuleDayPagedList(q);
	}
	/**
	 * 日租计费规则详情页面
	 * 
	 * @return
	 */
	@RequestMapping("toPricingRuleDayView")
	public String toPricingRuleDayView(String ruleId, ModelMap modelMap) {
		if (ruleId != null && ruleId.length() != 0) {
			PricingRuleDay pricingRule = pricingRuleDayService.getPricingRuleDay(ruleId);
			modelMap.put("pricingRule", pricingRule);
			if(pricingRule.getCencorId()!=null){
				SysUser sysUser = sysUserService.detail(pricingRule.getCencorId());
				modelMap.put("sysUser", sysUser);
			}
		}
		return "dailyrental/pricingrule/pricingRuleDay_view";
	}
	/**
	 * 计费规则增加页面
	 * 
	 * @return
	 */
	@RequestMapping("toAddPricingRuleDay")
	public String toAddPricingRuleDay(ModelMap modelMap) {
		List<DataDictItem> cities = dataDictItemService.getDataDictItemListByCatCode("CITY");// 城市
		modelMap.put("cities", cities);
		List<CarModel> carModels = carModelService.getCarModelList(new Query());// 汽车型号
		modelMap.put("carModels", carModels);
		return "dailyrental/pricingrule/pricingRuleDay_add";
	}
	/**
	 * 日租计费规则新增和修改
	 * 
	 * @return
	 */
	@RequestMapping("updatePricingRuleDay")
	@ResponseBody
	public ResultInfo<PricingRuleDay> updatePricingRuleDay(@ModelAttribute("pricingRuleDay") PricingRuleDay pricingRuleDay) {
		ResultInfo<PricingRuleDay> resultInfo = new ResultInfo<PricingRuleDay>();
		// 车辆型号
		CarModel carModel = carModelService.getCarModel(pricingRuleDay.getCarModelId());
		if (carModel != null) {
			pricingRuleDay.setCarModelName(carModel.getCarModelName());
			pricingRuleDay.setCarType(carModel.getCarType());
		}
		//目前单城市先不使用城市
		if(pricingRuleDay.getCityId()!=null){
			// 城市
			DataDictItem citie = dataDictItemService.getDataDictItem(pricingRuleDay.getCityId());
			
			if (citie != null) {
				pricingRuleDay.setCityName(citie.getItemValue());
			}
		}

		if (pricingRuleDay.getRuleId() == null || pricingRuleDay.getRuleId().length() <= 0) {

			resultInfo = pricingRuleDayService.addPricingRuleDay(pricingRuleDay, getOperator());

		} else {
			resultInfo = pricingRuleDayService.updatePricingRuleDay(pricingRuleDay, getOperator());
		}

		return resultInfo;
	}
	/**
	 * 判断日租计费规则的唯一性
	 */
	@RequestMapping("uniquePricingRuleDay")
	@ResponseBody
	public String uniquePricingRuleDay(String carModelId, String cityId,String ruleId,Integer carType) {
		PricingRuleDay pr = new PricingRuleDay();
		pr.setCarModelId(carModelId);
		pr.setCityId(cityId);//暂时不使用多城市
//		pr.setCarType(carType);
		Query q = new Query();
		q.setQ(pr);
		List<PricingRuleDay> list = pricingRuleDayService.getPricingRuleDayList(q);
		if (list != null && list.size() > 0) {
			if(ruleId!=null&&!"".equals(ruleId)){
				PricingRuleDay ruleDay = list.get(0);
				if(ruleDay.getRuleId().equals(ruleId)){
					return Constant.FAIL;
				}else{
					return Constant.SECCUESS;
				}
			}else{
				return Constant.SECCUESS;
			}
		} else {
			return Constant.FAIL;
		}
	}
	
	/**
	 * 日租计费规则审核
	 * 
	 * @return
	 */
	@RequestMapping("cencorPricingRuleDay")
	@ResponseBody
	public ResultInfo<PricingRuleDay> cencorPricingRuleDay(
			@ModelAttribute("pricingRuleDay") PricingRuleDay pricingRuleDay) {
		Operator op = getOperator();
		pricingRuleDay.setCencorId(op.getOperatorId());
		pricingRuleDay.setCencorTime(new Date());
		return pricingRuleDayService.updatePricingRuleDay(pricingRuleDay, op);
	}

	/**
	 * 日租计费规则停用
	 * 
	 * @return
	 */
	@RequestMapping("disablePricingRuleDay")
	@ResponseBody
	public ResultInfo<PricingRuleDay> disablePricingRuleDay(
			@ModelAttribute("pricingRuleDay") PricingRuleDay pricingRuleDay) {
		ResultInfo<PricingRuleDay> resultInfo = new ResultInfo<PricingRuleDay>();
		PricingRuleDay pd = pricingRuleDayService.getPricingRuleDay(pricingRuleDay.getRuleId());
		Operator op = getOperator();

		CarModel carModel = new CarModel();
		carModel.setCarModelName(pd.getCarModelName());
		carModel.setOnOffLineStatus(1);
		List<CarModel> carModelList = carModelService.getCarModelList(new Query(carModel));
		if (carModelList.size() > 0) {
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg("有车型" + pd.getCarModelName() + "上线，该计费规则不能停用！");
			return resultInfo;
		}

		return pricingRuleDayService.updatePricingRuleDay(pricingRuleDay, op);
	}

	/**
	 * 日租计费规则启用
	 * 
	 * @return
	 */
	@RequestMapping("enablePricingRuleDay")
	@ResponseBody
	public ResultInfo<PricingRuleDay> enablePricingRuleDay(
			@ModelAttribute("pricingRuleDay") PricingRuleDay pricingRuleDay) {

		return pricingRuleDayService.updatePricingRuleDay(pricingRuleDay, getOperator());
	}
	
	/**
	 * 日租计费规则对象
	 * 
	 * @return
	 */
	@RequestMapping("toPricingRuleDay")
	@ResponseBody
	public ResultInfo<PricingRuleDay> toPricingRuleDay(String ruleId) {
		ResultInfo<PricingRuleDay> result = new ResultInfo<PricingRuleDay>();
		PricingRuleDay pricingRule = null;
		if (ruleId != null && ruleId.length() != 0) {
			pricingRule = pricingRuleDayService.getPricingRuleDay(ruleId);
			result.setCode(Constant.SECCUESS);
			result.setData(pricingRule);
		}else{
			result.setCode(Constant.FAIL);
			result.setMsg("计费规则不存在");
		}
		return result;
	}

	/**
	 * 日租计费规则审核页面
	 * 
	 * @return
	 */
	@RequestMapping("toPricingRuleDayCencorView")
	public String toPricingRuleDayCencorView(String ruleId, ModelMap modelMap) {
		if (ruleId != null && ruleId.length() != 0) {
			PricingRuleDay pricingRule = pricingRuleDayService.getPricingRuleDay(ruleId);
			modelMap.put("pricingRule", pricingRule);
		}
		return "dailyrental/pricingrule/pricingRuleDay_cencor";
	}
	
	
	
	
	
	/**
	 * 日租计费规则自定义页面
	 * 
	 * @return
	 */
	@RequestMapping("toPricingRuleCustomDate")
	public String toPricingRuleCustomDate(String ruleId, ModelMap modelMap) {
		modelMap.put("ruleId", ruleId);
		return "dailyrental/pricingrule/pricingRuleCustomDate_list";
	}
	
	/**
	 * 日租自定义计费页面分页
	 * 
	 * @return
	 */
	@RequestMapping("pageListPricingRuleCustomDate")
	@ResponseBody
	public PageFinder<PricingRuleCustomDate> pageListPricingRuleCustomDate(
			@ModelAttribute("PricingRuleCustomDate") PricingRuleCustomDate PricingRuleCustomDate,
			Query query) {
		Query q = new Query(query.getPageNo(), query.getPageSize(), PricingRuleCustomDate);
		return pricingRuleCustomDateService.getPricingRuleCustomDatePagedListForMgt(q);
	}
	
	
	/**
	 * 判断日租自定义计费的唯一性
	 */
	@RequestMapping("uniquePricingRuleCustomDate")
	@ResponseBody
	public String uniquePricingRuleCustomDate(PricingRuleCustomDate PricingRuleCustomDate) {

		List<PricingRuleCustomDate> list = pricingRuleCustomDateService
				.getPricingRuleCustomDateList(new Query(PricingRuleCustomDate));

		if (list != null && list.size() > 0) {

			return Constant.SECCUESS;

		} else {
			return Constant.FAIL;
		}
	}
	
	
	/**
	 * 自定义计费增加页面
	 * 
	 * @return
	 */
	@RequestMapping("toAddPricingRuleCustomDate")
	public String toAddPricingRuleCustomDate(String ruleId, ModelMap modelMap) {
		PricingRuleDay customizedDate = pricingRuleDayService.getPricingRuleDay(ruleId);
		modelMap.put("customizedDate", customizedDate);
		return "dailyrental/pricingrule/pricingRuleCustomDate_add";
	}

	/**
	 * 自定义计费修改页面
	 * 
	 * @return
	 */
	@RequestMapping("toEditPricingRuleCustomDate")
	public String toEditPricingRuleCustomDate(String customizedId, ModelMap modelMap) {
		PricingRuleCustomDate customizedDate = pricingRuleCustomDateService.getPricingRuleCustomDate(customizedId);
		if(customizedDate!=null){
			PricingRuleDay pricingRuleDay = pricingRuleDayService.getPricingRuleDay(customizedDate.getRuleId());
			if(pricingRuleDay!=null){
				customizedDate.setCityName(pricingRuleDay.getCityName());
				customizedDate.setRuleName(pricingRuleDay.getRuleName());
				customizedDate.setCarModelName(pricingRuleDay.getCarModelName());
			}
		}
		modelMap.put("customizedDate", customizedDate);
		return "dailyrental/pricingrule/pricingRuleCustomDate_edit";
	}


	/**
	 * 删除日租自定义计费
	 */
	@RequestMapping("delPricingRuleCustomDate")
	@ResponseBody
	public ResultInfo<PricingRuleCustomDate> delPricingRuleCustomDate(String customizedId) {

		return pricingRuleCustomDateService.delPricingRuleCustomDate(customizedId, getOperator());

	}

	/**
	 * 日租计费规则新增和修改
	 * 
	 * @return
	 */
	@RequestMapping("updatePricingRuleCustomDate")
	@ResponseBody
	public ResultInfo<PricingRuleCustomDate> updatePricingRuleCustomDate(PricingRuleCustomDate p) {
		ResultInfo<PricingRuleCustomDate> resultInfo = new ResultInfo<PricingRuleCustomDate>();

		PricingRuleDay day = pricingRuleDayService.getPricingRuleDay(p.getRuleId());

		if (p.getCustomizedId() == null || p.getCustomizedId().length() <= 0) {
			// 计费规则名称
			p.setRuleId(day.getRuleId());
			resultInfo = pricingRuleCustomDateService.addPricingRuleCustomDate(p, getOperator());

		} else {
			resultInfo = pricingRuleCustomDateService.updatePricingRuleCustomDate(p, getOperator());
		}

		return resultInfo;
	}


	/**
	 * 日租计费规则编辑页面
	 * 
	 * @return
	 */
	@RequestMapping("toUpdateViewDay")
	public String toUpdateViewDay(String ruleId, ModelMap modelMap) {
		if (ruleId != null && ruleId.length() != 0) {
			PricingRuleDay pricingRule = pricingRuleDayService.getPricingRuleDay(ruleId);
			modelMap.put("pricingRule", pricingRule);
			
			List<DataDictItem> cities = dataDictItemService.getDataDictItemListByCatCode("CITY");// 城市
			modelMap.put("cities", cities);

			List<CarModel> carModels = carModelService.getCarModelList(new Query());// 汽车型号
			modelMap.put("carModels", carModels);
		}
		return "dailyrental/pricingrule/pricingRuleDay_edit";
	}

	/**
	 * 根据日租计费规则获取自定义日期计费信息
	 */
	@RequestMapping("getPricingRuleCustomDate")
	@ResponseBody
	public ResultInfo<PricingRuleCustomDate> getPricingRuleCustomizedDate(String ruleId) {
		ResultInfo<PricingRuleCustomDate> res = new ResultInfo<PricingRuleCustomDate>();
		PricingRuleCustomDate pc = new PricingRuleCustomDate();
		pc.setRuleId(ruleId);
		List<PricingRuleCustomDate> list = pricingRuleCustomDateService
				.getPricingRuleCustomDateList(new Query(pc));
		String dates = "";
		if (list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				if (i < list.size() - 1) {
					dates += ECDateUtils.formatDate(list.get(i).getCustomizedDate(), "yyyy-MM-dd") + ",";
				} else {
					dates += ECDateUtils.formatDate(list.get(list.size() - 1).getCustomizedDate(), "yyyy-MM-dd");
				}
			}
			pc = list.get(0);
			pc.setCustomizedDateStr(dates);
		} else {
			PricingRuleDay pr = pricingRuleDayService.getPricingRuleDay(ruleId);
			if (pr != null) {
				pc.setRuleId(pr.getRuleId());
			}
		}
		res.setCode(Constant.SECCUESS);
		res.setData(pc);
		return res;
	}

	/**
	 * 添加计费规则的自定义日期及计费规则
	 */
	@RequestMapping("saveDayCustomizedDate")
	@ResponseBody
	public ResultInfo<PricingRuleCustomDate> saveDayCustomizedDate(PricingRuleCustomDate customizedDate) {
		ResultInfo<PricingRuleCustomDate> res = new ResultInfo<PricingRuleCustomDate>();
		// 修改
		if (customizedDate != null && customizedDate.getCustomizedId() != null
				&& !"".equals(customizedDate.getCustomizedId())) {
			res = pricingRuleCustomDateService.delPricingRuleCustomDateByRuleId(customizedDate.getRuleId(),
					getOperator());
			if (res.getCode().equals(Constant.SECCUESS)) {
				String customizedDateStr = customizedDate.getCustomizedDateStr();
				if (customizedDateStr != null && customizedDateStr.indexOf(",") == -1) {
					customizedDate.setCustomizedDate(ECDateUtils.parseDate(customizedDateStr));
					res = pricingRuleCustomDateService.updatePricingRuleCustomDate(customizedDate,
							getOperator());
				} else {
					String[] dates = customizedDateStr.split(",");
					for (String date : dates) {
						PricingRuleCustomDate prcd = new PricingRuleCustomDate();

						prcd.setRuleId(customizedDate.getRuleId());
						prcd.setPriceOfDayCustomized(customizedDate.getPriceOfDayCustomized());
						prcd.setCustomizedDate(ECDateUtils.parseDate(date));
						res = pricingRuleCustomDateService.addPricingRuleCustomDate(prcd, getOperator());

					}
				}
			}
			// 添加
		} else {
			String customizedDateStr = customizedDate.getCustomizedDateStr();
			if (customizedDateStr != null && !"".equals(customizedDateStr)) {
				String[] dates = customizedDateStr.split(",");
				PricingRuleDay pr = pricingRuleDayService.getPricingRuleDay(customizedDate.getRuleId());
				for (String date : dates) {
					PricingRuleCustomDate prcd = new PricingRuleCustomDate();
					prcd.setRuleId(customizedDate.getRuleId());
					prcd.setPriceOfDayCustomized(customizedDate.getPriceOfDayCustomized());
					prcd.setCustomizedDate(ECDateUtils.parseDate(date));
					res = pricingRuleCustomDateService.addPricingRuleCustomDate(prcd, getOperator());
				}
			}
		}
		return res;
	}

}
