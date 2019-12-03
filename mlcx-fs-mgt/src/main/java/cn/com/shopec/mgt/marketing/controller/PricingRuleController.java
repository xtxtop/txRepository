package cn.com.shopec.mgt.marketing.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.common.constants.Constant;
import cn.com.shopec.common.utils.ECDateUtils;
import cn.com.shopec.core.car.model.Car;
import cn.com.shopec.core.car.model.CarStatus;
import cn.com.shopec.core.car.service.CarService;
import cn.com.shopec.core.car.service.CarStatusService;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.car.model.CarBrand;
import cn.com.shopec.core.car.model.CarSeries;
import cn.com.shopec.core.car.service.CarBrandService;
import cn.com.shopec.core.car.service.CarSeriesService;
import cn.com.shopec.core.marketing.model.PricingRule;
import cn.com.shopec.core.marketing.model.PricingRuleCustomizedDate;
import cn.com.shopec.core.marketing.service.PricingRuleCustomizedDateService;
import cn.com.shopec.core.marketing.service.PricingRuleService;
import cn.com.shopec.core.member.model.Company;
import cn.com.shopec.core.member.service.CompanyService;
import cn.com.shopec.core.system.model.DataDictItem;
import cn.com.shopec.core.system.model.SysParam;
import cn.com.shopec.core.system.model.SysUser;
import cn.com.shopec.core.system.service.DataDictItemService;
import cn.com.shopec.core.system.service.SysParamService;
import cn.com.shopec.core.system.service.SysUserService;
import cn.com.shopec.mgt.common.BaseController;
@Controller
@RequestMapping("pricingRule")
public class PricingRuleController extends BaseController{
@Resource
private PricingRuleService pricingRuleService;
@Resource
private DataDictItemService dataDictItemService;
@Resource
private SysUserService sysUserService;
@Resource
private CompanyService companyService;
@Resource
private CarService carService;
@Resource
private CarBrandService carBrandService;
@Resource
private  CarSeriesService carSeriesService;
@Resource
private CarStatusService carStatusService;
@Resource
private PricingRuleCustomizedDateService pricingRuleCustomizedDateService;
@Resource
private SysParamService sysParamService;
/**
 * 计费规则页面
 * @return
 */
@RequestMapping("/toPricingRuleList")
public String toPricingRuleList(ModelMap modelMap){
	List<Object> pr=new ArrayList<Object>();
	String carModelName="";
	List<DataDictItem> cities = dataDictItemService.getDataDictItemListByCatCode("CITY");//城市
	//车型列表 
	List<DataDictItem> carbrands = dataDictItemService.getDataDictItemListByCatCode("CAR_BRAND");//品牌
	if(carbrands != null && carbrands.size()>0){
		for (DataDictItem dataDictItem : carbrands) {
			DataDictItem d= new DataDictItem();
			d.setParentItemId(dataDictItem.getDataDictItemId());
			d.setIsAvailable(1);
			Query q= new Query(d);
			List<DataDictItem> carmodels=dataDictItemService.getDataDictItemList(q);
			if(carmodels != null){
				for (DataDictItem dataDictItem2 : carmodels) {
					carModelName=dataDictItem.getItemValue()+dataDictItem2.getItemValue();
					pr.add(carModelName);
				}
			}
		}
	}
	modelMap.put("carModelName", pr);
	//集团列表
	Company company = new Company();
	company.setCompanyStatus(1);
	company.setCencorStatus(3);
	Query qc= new Query(company);
	List<Company> companys = companyService.getCompanyList(qc);
	if(companys != null && companys.size()>0){
		modelMap.put("companys", companys);
	}
	modelMap.put("cities", cities);
	return "marketing/pricingRule_list";
}
/**
 * 计费规则页面分页
 * @return
 */
@RequestMapping("/pageListPricingRule")
@ResponseBody
public PageFinder<PricingRule> pageListOrder(@ModelAttribute("pricingRule")PricingRule pricingRule,Query query){
	Query q = new Query(query.getPageNo(),query.getPageSize(),pricingRule);
	return pricingRuleService.getPricingRulePagedList(q);
}

/**
 * 自定义计费规则页面分页
 * @return
 */
@RequestMapping("/pageListPricingRuleCustomizedDate")
@ResponseBody
public PageFinder<PricingRuleCustomizedDate> pageListPricingRuleCustomizedDate(@ModelAttribute("PricingRuleCustomizedDate")PricingRuleCustomizedDate pricingRuleCustomizedDate,Query query){
	Date date= new Date();
	if(pricingRuleCustomizedDate.getIsOverdue()==0){
		try {
			pricingRuleCustomizedDate.setCustomizedDateStart(ECDateUtils.getCurrentDate());
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	if(pricingRuleCustomizedDate.getIsOverdue()==1){
		try {
			pricingRuleCustomizedDate.setCustomizedDateEnd(ECDateUtils.getCurrentDate());
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	Query q = new Query(query.getPageNo(),query.getPageSize(),pricingRuleCustomizedDate);
	return pricingRuleCustomizedDateService.getPricingRuleCustomizedDatePagedList(q);
}

/**
 * 自定义计费规则  删除
 * @return
 */
@RequestMapping("/delPricingRuleCustomizedDate")
@ResponseBody
public ResultInfo<PricingRuleCustomizedDate> delPricingRuleCustomizedDate(@ModelAttribute("customizedId")String customizedId){
	ResultInfo<PricingRuleCustomizedDate> result=new ResultInfo<PricingRuleCustomizedDate>();
	PricingRuleCustomizedDate pricingRuleCustomizedDate=pricingRuleCustomizedDateService.getPricingRuleCustomizedDate(customizedId); 
	if(pricingRuleCustomizedDate != null){
		result= pricingRuleCustomizedDateService.delPricingRuleCustomizedDate(customizedId, getOperator());
		return result;
	}else{
		result.setCode("0");
		return result;
	}
	
	
}


/**
 * 计费规则页面分页  含平台的
 * @return
 */
@RequestMapping("/pageListPricingRules")
@ResponseBody
public PageFinder<PricingRule> pageListOrders(@ModelAttribute("pricingRule")PricingRule pricingRule,Query query){
	Query q = new Query(query.getPageNo(),query.getPageSize(),pricingRule);
	return pricingRuleService.getPricingRulePagedLists(q);
}
/**
 * 计费规则详情页面
 * @return
 */
@RequestMapping("/toPricingRuleView")
public String toPricingRuleView(String ruleNo,ModelMap modelMap){
	if(ruleNo!=null&&ruleNo.length()!=0){
		List<DataDictItem> cities = dataDictItemService.getDataDictItemListByCatCode("CITY");//城市
		modelMap.put("cities", cities);
		PricingRule pricingRule=pricingRuleService.getPricingRule(ruleNo);
		if(pricingRule!=null){
			if(pricingRule.getCompanyId()!=null&&!pricingRule.getCompanyId().equals("")){
				Company company=companyService.getCompany(pricingRule.getCompanyId());
				if(company!=null){
					pricingRule.setCompanyName(company.getCompanyName());
				}
			}
		}
		modelMap.put("pricingRule", pricingRule);
		SysUser sysUser=sysUserService.detail(pricingRule.getOperatorId());
		modelMap.put("sysUser", sysUser);
		SysUser sysUser1=sysUserService.detail(pricingRule.getCencorId());
		modelMap.put("sysUser1", sysUser1);
		int orderCaculateType = 1;
		SysParam sysparam1 = sysParamService.getByParamKey("ORDER_CACULATE_TYPE");
		if(sysparam1!=null&&sysparam1.getParamValue()!=null){
			orderCaculateType = Integer.parseInt(sysparam1.getParamValue());
		}
		modelMap.put("orderCaculateType", orderCaculateType);
	}
	return "marketing/pricingRule_view";
}
/**
 * 计费规则审核页面
 * @return
 */
@RequestMapping("/toPricingRuleCencorView")
public String toPricingRuleCencorView(String ruleNo,ModelMap modelMap){
	if(ruleNo!=null&&ruleNo.length()!=0){
		PricingRule pricingRule=pricingRuleService.getPricingRule(ruleNo);
		if(pricingRule!=null){
			if(pricingRule.getCompanyId()!=null&&!pricingRule.getCompanyId().equals("")){
				Company company=companyService.getCompany(pricingRule.getCompanyId());
				if(company!=null){
					pricingRule.setCompanyName(company.getCompanyName());
				}
			}
			SysUser sysUser=sysUserService.detail(pricingRule.getOperatorId());
			modelMap.put("sysUser", sysUser);
		}
		modelMap.put("pricingRule", pricingRule);
	}
	return "marketing/pricingRule_cencor";
}
/**
 * 计费规则增加页面
 * @return
 */
@RequestMapping("/toAddPricingRule")
public String toAddPricingRule(String ruleNo,ModelMap modelMap){
	List<DataDictItem> cities = dataDictItemService.getDataDictItemListByCatCode("CITY");//城市
	modelMap.put("cities", cities);
//	List<DataDictItem> carBrands = dataDictItemService.getDataDictItemListByCatCode("CAR_BRAND");//汽车品牌
	Query qb = new Query();
	List<CarBrand> carBrands=carBrandService.getCarBrandList(qb);//汽车品牌
	modelMap.put("carBrands", carBrands);
	
	//List<DataDictItem> carModels = dataDictItemService.getDataDictItemListByCatCode("CAR_MODEL");//汽车型号
	List<CarSeries> carModels=carSeriesService.getCarSeriesList(qb);
	modelMap.put("carModels", carModels);
	//审核通过后的集团列表
	Query q=new Query();
	Company company=new Company();
	company.setCencorStatus(3);
	q.setQ(company);
	List<Company> companyList=companyService.getCompanyList(q);
	modelMap.put("companyList", companyList);
	int orderCaculateType = 1;
	SysParam sysparam1 = sysParamService.getByParamKey("ORDER_CACULATE_TYPE");
	if(sysparam1!=null&&sysparam1.getParamValue()!=null){
		orderCaculateType = Integer.parseInt(sysparam1.getParamValue());
	}
	modelMap.put("orderCaculateType", orderCaculateType);
	return "marketing/pricingRule_add";
}
/**
 * 计费规则编辑页面
 * @return
 */
@RequestMapping("/toUpdateView")
public String toUpdateView(String ruleNo,ModelMap modelMap){
	if(ruleNo!=null&&ruleNo.length()!=0){
		PricingRule pricingRule=pricingRuleService.getPricingRule(ruleNo);
		modelMap.put("pricingRule", pricingRule);
		SysUser sysUser=sysUserService.detail(pricingRule.getOperatorId());
		if(sysUser!=null){
		modelMap.put("sysUser", sysUser);
		}
		SysUser sysUser1=sysUserService.detail(pricingRule.getCencorId());
		if(sysUser1!=null){
		modelMap.put("sysUser1", sysUser1);
		}
		List<DataDictItem> cities = dataDictItemService.getDataDictItemListByCatCode("CITY");//城市
		modelMap.put("cities", cities);
//		List<DataDictItem> carBrands = dataDictItemService.getDataDictItemListByCatCode("CAR_BRAND");//汽车品牌
//		modelMap.put("carBrands", carBrands);
		Query qb = new Query();
		List<CarBrand> carBrands=carBrandService.getCarBrandList(qb);//汽车品牌
		modelMap.put("carBrands", carBrands);
		
		List<CarSeries> carModels=carSeriesService.getCarSeriesList(qb);
		modelMap.put("carModels", carModels);
		//审核通过后的集团列表
		Query q=new Query();
		Company company=new Company();
		company.setCencorStatus(3);//已审核
		q.setQ(company);
		List<Company> companyList=companyService.getCompanyList(q);
		modelMap.put("companyList", companyList);
		int orderCaculateType = 1;
		SysParam sysparam1 = sysParamService.getByParamKey("ORDER_CACULATE_TYPE");
		if(sysparam1!=null&&sysparam1.getParamValue()!=null){
			orderCaculateType = Integer.parseInt(sysparam1.getParamValue());
		}
		modelMap.put("orderCaculateType", orderCaculateType);
	}
	return "marketing/pricingRule_edit";
}
/**
 * 计费规则修改
 * @return
 */
@RequestMapping("/updatePricingRule")
@ResponseBody
public ResultInfo<PricingRule> updatePricingRule(@ModelAttribute("pricingRule")PricingRule pricingRule,@RequestParam(value="carBrand",defaultValue="")String carBrand,@RequestParam(value="carModel",defaultValue="")String carModel){
	ResultInfo<PricingRule> resultInfo=new ResultInfo<PricingRule>();
	if(pricingRule.getRuleNo()!=null&&pricingRule.getRuleNo().length()!=0){
		PricingRule pr = new PricingRule();
		pr.setRuleName(pricingRule.getRuleName());
		pr.setCompanyId(pricingRule.getCompanyId());
		pr.setFlag(1);//计费规则名称精确查找
		List<PricingRule> list = pricingRuleService.getPricingRuleList(new Query(pr));
		if (list.size()>0) {
			if (!list.get(0).getRuleNo().equals(pricingRule.getRuleNo())) {
				resultInfo.setCode(Constant.FAIL);
				resultInfo.setMsg(pricingRule.getRuleName()+"已存在!");
				return resultInfo;
			}
		}
		//将车辆品牌和车辆型号合为计费规则的车辆型号
		if(carBrand!=null&&!carBrand.equals("")&&carModel!=null&&!carModel.equals("")){
			String carModelZ=dataDictItemService.getValueOfDataDictItem(carBrand)+dataDictItemService.getValueOfDataDictItem(carModel);
			pricingRule.setCarModelName(carModelZ);
			pricingRule.setCencorStatus(0);
		}
		//停用时判断有没有相应品牌和型号的车已预订或者在订单中
		if(pricingRule.getCarModelName()!=null&&!"".equals(pricingRule.getCarModelName())){
			String brandAndModle = getCarBrandAndModel(pricingRule.getCarModelName());
//			System.err.println(brandAndModle);
			String carBrandName = brandAndModle.split(",")[0];
			String carModelName = brandAndModle.split(",")[1];
			Car car = new Car();
			car.setCarBrandName(carBrandName);
			car.setCarModelName(carModelName);
			List<Car> carList = carService.getCarList(new Query(car));
			if(carList.size()>0){
				for (Car car2 : carList) {
					CarStatus cs= carStatusService.getCarStatus(car2.getCarNo());
					if( cs != null){
						if(cs.getUserageStatus() ==1  || cs.getUserageStatus() ==2){
							resultInfo.setCode(Constant.FAIL);
							resultInfo.setMsg("车型"+pricingRule.getCarModelName()+"有预订或者订单中，请结束后进行操作!");
							return resultInfo;
						}
						
					}
				}
				
			}
			
			Car c = new Car();
			c.setCarBrandName(carBrandName);
			c.setCarModelName(carModelName);
			c.setOnlineStatus(1);
			List<Car> cList = carService.getCarList(new Query(c));
			if(cList.size()>0){
				resultInfo.setCode(Constant.FAIL);
				resultInfo.setMsg("车型"+pricingRule.getCarModelName()+"在线，请下线后进行操作!");
				return resultInfo;
			}
			
		}
		resultInfo=pricingRuleService.updatePricingRule(pricingRule, getOperator());
	}else{
		//将车辆品牌和车辆型号合为计费规则的车辆型号
		if(carBrand!=null&&!carBrand.equals("")&&carModel!=null&&!carModel.equals("")){
			PricingRule pr = new PricingRule();
			pr.setRuleName(pricingRule.getRuleName());
			pr.setCompanyId(pricingRule.getCompanyId());
			pr.setFlag(1);//计费规则名称精确查找
			List<PricingRule> list = pricingRuleService.getPricingRuleList(new Query(pr));
			if (list.size()>0) {
				resultInfo.setCode(Constant.FAIL);
				resultInfo.setMsg(pricingRule.getRuleName()+"已存在!");
				return resultInfo;
			}
			
			String carModelZ=carBrandService.getCarBrand(carBrand).getCarBrandName()+carSeriesService.getCarSeries(carModel).getCarSeriesName();
			pricingRule.setCarModelName(carModelZ);
			Date now = new Date();
			pricingRule.setAvailableTime1(now);
			String dateString = "2099-12-31 00:00:00";
		    
			pricingRule.setAvailableTime2(ECDateUtils.parseDate(dateString, "yyyy-MM-dd HH:mm:ss"));
			int orderCaculateType = 2;
			SysParam sysparam1 = sysParamService.getByParamKey("ORDER_CACULATE_TYPE");
			if(sysparam1!=null&&sysparam1.getParamValue()!=null){
				orderCaculateType = Integer.parseInt(sysparam1.getParamValue());
			}
			pricingRule.setRuleType(orderCaculateType);
			resultInfo=pricingRuleService.addPricingRule(pricingRule, getOperator());
		}
	}
	return resultInfo;
}

/**
 * 计费规则启用（启用之前先停用于原来的）
 * @return
 */
@RequestMapping("/pricingRuleStartup")
@ResponseBody
public ResultInfo<PricingRule> pricingRuleStartup(@ModelAttribute("pricingRule")PricingRule pricingRule,@RequestParam(value="carBrand",defaultValue="")String carBrand,@RequestParam(value="carModel",defaultValue="")String carModel){
	ResultInfo<PricingRule> resultInfo=new ResultInfo<PricingRule>();
	if(pricingRule.getRuleNo()!=null&&pricingRule.getRuleNo().length()!=0){
		PricingRule pricingRules =pricingRuleService.getPricingRule(pricingRule.getRuleNo());
		if(pricingRules != null){
			//停用时判断有没有相应品牌和型号的车上线
//			if(pricingRules.getCarModelName()!=null&&!"".equals(pricingRules.getCarModelName())){
//				String brandAndModle = getCarBrandAndModel(pricingRules.getCarModelName());
//				String carBrandName = brandAndModle.split(",")[0];
//				String carModelName = brandAndModle.split(",")[1];
//				Car car = new Car();
//				car.setCarBrandName(carBrandName);
//				car.setCarModelName(carModelName);
//				car.setOnlineStatus(1);
//				List<Car> carList = carService.getCarList(new Query(car));
//				if(carList.size()>0){
//					resultInfo.setCode(Constant.FAIL);
//					resultInfo.setMsg("车型"+pricingRules.getCarModelName()+"在线，请下线后进行操作!");
//					return resultInfo;
	//			}else{
					//查出原来的 计费规则
					PricingRule pricingRuless=new  PricingRule();
					pricingRuless.setCarModelName(pricingRules.getCarModelName());
					pricingRuless.setCompanyId(pricingRules.getCompanyId());
					pricingRuless.setIsAvailable(1);
					Query q= new Query(pricingRuless);
					List<PricingRule> pss=pricingRuleService.getPricingRuleList(q);
					if(pss != null && pss.size()>0){
						pss.get(0).setIsAvailable(0);
						resultInfo=pricingRuleService.updatePricingRule(pss.get(0), getOperator());
					}
					pricingRules.setIsAvailable(1);
					resultInfo=pricingRuleService.updatePricingRule(pricingRules, getOperator());
				}
			//}
		//}
		
	}else{
		resultInfo.setCode(Constant.FAIL);
		resultInfo.setMsg("操作失败，请稍后重试");
		return resultInfo;
	}
	return resultInfo;
}
/**
 * 计费规则对象
 * @return
 */
@RequestMapping("/toPricingRule")
@ResponseBody
public PricingRule toPricingRule(String ruleNo){
	PricingRule pricingRule=null;
	if(ruleNo!=null&&ruleNo.length()!=0){
		pricingRule=pricingRuleService.getPricingRule(ruleNo);
	}
	
	return pricingRule;
}
/**
 * 判断计费规则的唯一性
 * */
@RequestMapping("/uniquePricingRule")
@ResponseBody
public String uniquePricingRule(String carBrand,String carModel,String companyId,String ruleNo){
	PricingRule pr=new PricingRule();
	pr.setCarModelName(dataDictItemService.getValueOfDataDictItem(carBrand)+dataDictItemService.getValueOfDataDictItem(carModel));
	if(companyId!=null&&!companyId.equals("")){
		pr.setCompanyId(companyId);
	}
	pr.setIsAvailable(1);
	pr.setCencorStatus(1);
	Query q=new Query();
	q.setQ(pr);
	Integer tag=0;
	List<PricingRule> list=pricingRuleService.getPricingRuleList(q);
	if(list!=null&&list.size()>0){
		for(PricingRule co1:list){
			if(!co1.getRuleNo().equals(ruleNo)){
				tag=1;
			}
		}
		if(tag.equals(1)){
			return Constant.SECCUESS;
		}else{
			return Constant.FAIL;
		}
	}else{
		return Constant.FAIL;
	}
}
     
	/**
	 * 根据车辆品牌搜索对应的车型
	 * */
	@RequestMapping("getCarModleByBrand")
	@ResponseBody
	public ResultInfo<List<CarSeries>> getCarModleByBrand(String brandId) {
		ResultInfo<List<CarSeries>> res = new ResultInfo<List<CarSeries>>();
		CarSeries cs= new CarSeries();
		cs.setCarBrandId(brandId);
		Query qcs= new Query(cs);
		List<CarSeries> items = carSeriesService.getCarSeriesList(qcs);
		res.setCode(Constant.SECCUESS);
		res.setData(items);
		return res;
	}

	private String getCarBrandAndModel(String brandAndModel){
		Query qb = new Query();
		List<CarBrand> carBrands=carBrandService.getCarBrandList(qb);//汽车品牌
		String carBrandName = "";
		String carModelName = "";
		for(CarBrand  carBrand:carBrands){
			if(brandAndModel.contains(carBrand.getCarBrandName())){
				carBrandName = carBrand.getCarBrandName();
				break;
			}
		}
		List<CarSeries> carModels =carSeriesService.getCarSeriesList(qb);//汽车型号
		for(CarSeries carSeries:carModels){
			if(brandAndModel.contains(carSeries.getCarSeriesName())){
				carModelName = carSeries.getCarSeriesName();
				break;
			}
		}
		return carBrandName+","+carModelName;
	}
	/**
	 * 根据计费规则获取自定义日期计费信息
	 * */
	@RequestMapping("getPricingRuleCustomizedDate")
	@ResponseBody
	public ResultInfo<PricingRuleCustomizedDate> getPricingRuleCustomizedDate(String ruleNo){
		ResultInfo<PricingRuleCustomizedDate> res = new ResultInfo<>();
		PricingRuleCustomizedDate pc = new PricingRuleCustomizedDate();
		pc.setRuleNo(ruleNo);
		List<PricingRuleCustomizedDate> list = pricingRuleCustomizedDateService.getPricingRuleCustomizedDateList(new Query(pc));
		String dates = "";
		if(list.size()>0){
			for(int i=0;i<list.size();i++){
				if(i<list.size()-1){
					dates += ECDateUtils.formatDate(list.get(i).getCustomizedDate(), "yyyy-MM-dd")+",";
				}else{
					dates += ECDateUtils.formatDate(list.get(list.size()-1).getCustomizedDate(), "yyyy-MM-dd");
				}
			}
			pc = list.get(0);
			pc.setCustomizedDateStr(dates);
		}else{
			PricingRule pr = pricingRuleService.getPricingRule(ruleNo);
			if(pr!=null){
				pc.setRuleNo(pr.getRuleNo());
				pc.setCityId(pr.getCityId());
				pc.setCityName(pr.getCityName());
				pc.setCarModelName(pr.getCarModelName());
				pc.setCompanyId(pr.getCompanyId());
			}
		}
		res.setCode(Constant.SECCUESS);
		res.setData(pc);
		return res;
	}
	
	/**
	 * 根据计费规则获取自定义日期计费信息
	 * */
	@RequestMapping("getPricingRuleCustomizedDateUpdate")
	@ResponseBody
	public ResultInfo<PricingRuleCustomizedDate> getPricingRuleCustomizedDateUpdate(String customizedId){
		ResultInfo<PricingRuleCustomizedDate> res = new ResultInfo<>();
		PricingRuleCustomizedDate pc = pricingRuleCustomizedDateService.getPricingRuleCustomizedDate(customizedId);
		if(pc != null){
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");  
			String str=sdf.format(pc.getCustomizedDate());
			pc.setCustomizedDateStr(str);
			res.setCode(Constant.SECCUESS);
			res.setData(pc);
		}else{
			res.setCode(Constant.FAIL);
		}
		
		return res;
	}
	/**
	 * 添加计费规则的自定义日期及计费规则
	 * */
	@RequestMapping("saveOrEditCustomizdDate")
	@ResponseBody
	public ResultInfo<PricingRuleCustomizedDate> saveOrEditCustomizdDate(PricingRuleCustomizedDate customizedDate){
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		ResultInfo<PricingRuleCustomizedDate> res = new ResultInfo<PricingRuleCustomizedDate>();
		List<PricingRuleCustomizedDate> listps = new ArrayList<PricingRuleCustomizedDate>();
		//修改
		if(customizedDate!=null&& customizedDate.getCustomizedDateStr()!= null && !"".equals(customizedDate.getCustomizedDateStr())){
			String customizedDateStr = customizedDate.getCustomizedDateStr();
			String [] datess = customizedDateStr.split(",");
			String da=sdf.format(new Date());
			for(String date:datess){
				if(date.equals(da)){
					res.setCode(Constant.FAIL);
					res.setMsg("当天时间不能添加"); 
					return res;
				}else{
					PricingRuleCustomizedDate prcd = new PricingRuleCustomizedDate();
					prcd.setCustomizedDate(ECDateUtils.parseDate(date));
					prcd.setRuleNo(customizedDate.getRuleNo());
					Query q= new Query(prcd);
					List<PricingRuleCustomizedDate> temp = pricingRuleCustomizedDateService.getPricingRuleCustomizedDateList(q);
					if(temp!=null && temp.size()>0){
						res.setCode(Constant.FAIL);
						res.setMsg(sdf.format(ECDateUtils.parseDate(date))+"已经添加 不能添加只能去编辑！");  
						return res;
					}
				}
			
			}
		
//			prcd.setPriceOfMinuteCustomized(customizedDate.getPriceOfMinuteCustomized());
//			prcd.setPriceOfKmCustomized(customizedDate.getPriceOfKmCustomized());
//			prcd.setBillingCapPerDayCustomized(customizedDate.getBillingCapPerDayCustomized());
//			prcd.setCustomizedDate(ECDateUtils.parseDate(date));
//			prcd.setCustomizedId(temp.getCustomizedId());
//			res = pricingRuleCustomizedDateService.updatePricingRuleCustomizedDate(prcd, getOperator());
			//添加
			if(customizedDateStr!= null&&!"".equals(customizedDateStr)){
				String [] dates = customizedDateStr.split(",");
				PricingRule pr = pricingRuleService.getPricingRule(customizedDate.getRuleNo());
				for(String date:dates){
					PricingRuleCustomizedDate prcd = new PricingRuleCustomizedDate();
					prcd.setRuleNo(customizedDate.getRuleNo());
					prcd.setCityId(pr.getCityId());
					prcd.setCityName(pr.getCityName());
					prcd.setCarModelName(pr.getCarModelName());
					prcd.setPriceOfMinuteCustomized(customizedDate.getPriceOfMinuteCustomized());
					prcd.setPriceOfKmCustomized(customizedDate.getPriceOfKmCustomized());
					prcd.setBillingCapPerDayCustomized(customizedDate.getBillingCapPerDayCustomized());
					prcd.setCustomizedDate(ECDateUtils.parseDate(date));
					prcd.setCompanyId(customizedDate.getCompanyId());
					prcd.setCustomizedId(pricingRuleCustomizedDateService.generatePK());
					res = pricingRuleCustomizedDateService.addPricingRuleCustomizedDate(prcd, getOperator());
				}
			}else{
				res.setCode(Constant.FAIL);
				res.setMsg("没有自定义时间");  
				return res;
			}
			
			
		}else{
			res.setCode(Constant.FAIL);
			res.setMsg("没有自定义时间");  
			return res;
		}
		
		
		return res;
	}
}
