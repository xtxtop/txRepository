package cn.com.shopec.mgt.charger.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.shopec.common.Operator;
import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.common.constants.Constant;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.resource.model.Charger;
import cn.com.shopec.core.resource.model.Park;
import cn.com.shopec.core.resource.service.ChargerService;
import cn.com.shopec.core.resource.service.ParkService;
import cn.com.shopec.core.system.model.DataDictItem;
import cn.com.shopec.core.system.model.SysRegion;
import cn.com.shopec.core.system.service.DataDictItemService;
import cn.com.shopec.core.system.service.SysRegionService;
import cn.com.shopec.mgt.common.BaseController;

/**
 * 充电桩管理
 * 
 * @author wangming
 */
@Controller
@RequestMapping("/charger")
public class ChargerController extends BaseController {

	@Resource
	private ChargerService chargerService;

	@Resource
	private DataDictItemService dataDictItemService;
	@Resource
	private SysRegionService sysRegionService;
	@Resource
	private ParkService parkService;

	/**
	 * 进入充电桩列表页面
	 * 
	 * @return
	 */
	@RequestMapping("toChargerList")
	public String toChargerList(String parkNo, ModelMap modelMap) {
		modelMap.put("parkNo", parkNo);
		return "/charger/charger_list";
	}

	/**
	 * 查询充电桩列表
	 * 
	 * @param query
	 * @return
	 */
	@RequestMapping("pageListCharger")
	@ResponseBody
	public PageFinder<Charger> pageListCharger(@ModelAttribute("charger") Charger charger,
			@RequestParam("pageNo") int pageNo, @RequestParam("pageSize") int pageSize) {

		Query q = new Query(pageNo, pageSize, charger);
		return chargerService.getChargerPagedList(q);
	}

	/*
	 * 新增场站页
	 */
	@RequestMapping("/toAddCharger")
	public String toAddCharger(ModelMap modelMap) {
		List<DataDictItem> cities = dataDictItemService.getDataDictItemListByCatCode("CITY");// 城市
		List<DataDictItem> styles = dataDictItemService.getDataDictItemListByCatCode("PARK_STYLE");// 场站样式
		List<Park> parkList = parkService.getParkList(new Query());// 场站名称
		// 品牌
		List<DataDictItem> brandList = dataDictItemService.getDataDictItemListByCatCode("CHARGER_BRAND");
		// 型号
		List<DataDictItem> modelList = dataDictItemService.getDataDictItemListByCatCode("CHARGER_MODEL");
		List<SysRegion> plists = sysRegionService.getProvices();// 省

		modelMap.put("cities", cities);
		modelMap.put("styles", styles);
		modelMap.put("plist", plists);
		modelMap.put("parkList", parkList);
		modelMap.put("brandList", brandList);
		modelMap.put("modelList", modelList);

		return "charger/charger_add";
	}

	/**
	 * 充电桩添加
	 * 
	 */
	@RequestMapping("addCharger")
	@ResponseBody
	public ResultInfo<Charger> addCharger(Charger charger) {
		ResultInfo<Charger> res = new ResultInfo<Charger>();
		Charger c = new Charger();
		c.setChargerSn(charger.getChargerSn());
		List<Charger> lists = chargerService.getChargerList(new Query(c));
		if(lists!=null&&lists.size()>0){
			res.setCode(Constant.FAIL);
			res.setMsg("充电桩串号重复！");
		}else{
			Park park = null;
			if(charger.getParkNo()!=null&&!charger.getParkNo().equals("")){
				park=parkService.getPark(charger.getParkNo());	
			}
			if (park != null) {
				charger.setParkName(park.getParkName());
				charger.setParkNo(park.getParkNo());
			}
			charger.setChargerNo("CG" + new SimpleDateFormat("HHmmss").format(new Date()));
			res = chargerService.addCharger(charger, getOperator());
			//添加一个 改变一个充电桩数据
			if(res.getCode()=="1"){
				Charger cg=new Charger();
				cg.setParkNo(res.getData().getParkNo());
				cg.setIsAvailable(1);
				cg.setIsDeleted(0);
				List<Charger> chargers=chargerService.getChargerList(new Query(cg));
				Park pa= new Park();
				pa.setParkNo(res.getData().getParkNo());
				if(chargers.size()>=0){
					pa.setChargerNumber(chargers.size());
				}else{
					pa.setChargerNumber(0);
				}
				parkService.updateParkByChargerNumber(pa, getOperator());
			}
			
		}
		return res;

	}

	/**
	 * 充电桩详情
	 * 
	 * @param charger
	 * @return
	 */
	@RequestMapping("toChargerView")
	public String toChargerView(@ModelAttribute("chargerNo") String chargerNo, Model model) {
		Charger charger = chargerService.getCharger(chargerNo);
		model.addAttribute("charger", charger);
		return "/charger/charger_view";
	}

	/*
	 * 查询Charger对象
	 */
	@RequestMapping("/toCharger")
	@ResponseBody
	public Charger toCharger(String parkNo) {
		Charger charger = new Charger();

		return charger;
	}

	/**
	 * 进入充电桩修改页面
	 */
	@RequestMapping("toChargerEdit")
	public String toChargerEdit(String chargerNo, ModelMap modelMap) {
		Charger charger = chargerService.getCharger(chargerNo);
		List<DataDictItem> chargerBrands = dataDictItemService.getDataDictItemListByCatCode("CHARGER_BRAND");// 充电桩品牌
		modelMap.put("charger", charger);
		modelMap.put("chargerBrands", chargerBrands);
		List<DataDictItem> chargerModels = dataDictItemService.getDataDictItemListByCatCode("CHARGER_MODEL");// 充电桩型号
		modelMap.put("chargerModels", chargerModels);
		List<DataDictItem> cities = dataDictItemService.getDataDictItemListByCatCode("CITY");// 城市
		modelMap.put("cities", cities);
		Query query = new Query();
		List<Park> listParks = parkService.getParkList(query);
		modelMap.put("listParks", listParks);
		modelMap.put("cities", cities);
		return "/charger/charger_edit";
	}

	/**
	 * 充电桩修改
	 * 
	 * @param charger
	 * @return
	 */
	@RequestMapping("updateCharger")
	@ResponseBody
	public ResultInfo<Charger> updateWorkerOrder(@ModelAttribute("charger") Charger charger) {
		// 城市
		DataDictItem dataDictItemcity = dataDictItemService.getDataDictItem(charger.getCityId());
		if (dataDictItemcity != null) {
			charger.setCityName(dataDictItemcity.getItemValue());
		}
		Park park =null;
		if(charger.getParkNo()!=null&&!charger.getParkNo().equals("")){
			park = parkService.getPark(charger.getParkNo());
		}
		if (park != null) {
			charger.setParkName(park.getParkName());
		}
		Operator operator = getOperator();
		ResultInfo<Charger> resultInfo = chargerService.updateCharger(charger, operator);
		//添加一个 改变一个充电桩数据
		if(resultInfo.getCode()=="1"){
			Charger cg=new Charger();
			cg.setParkNo(resultInfo.getData().getParkNo());
			cg.setIsAvailable(1);
			cg.setIsDeleted(0);
			List<Charger> chargers=chargerService.getChargerList(new Query(cg));
			Park pa= new Park();
			pa.setParkNo(resultInfo.getData().getParkNo());
			if(chargers.size()>=0){
				pa.setChargerNumber(chargers.size());
			}else{
				pa.setChargerNumber(0);
			}
			parkService.updateParkByChargerNumber(pa, getOperator());
		}
		return resultInfo;
	}

}
