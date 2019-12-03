package cn.com.shopec.mgt.resource.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.common.constants.Constant;
import cn.com.shopec.common.utils.JsonUtils;
import cn.com.shopec.core.car.model.CarStatus;
import cn.com.shopec.core.car.model.LongitudeAndLatitude;
import cn.com.shopec.core.car.service.CarStatusService;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.franchisee.model.Franchisee;
import cn.com.shopec.core.franchisee.service.FranchiseeService;
import cn.com.shopec.core.member.model.Company;
import cn.com.shopec.core.member.service.CompanyService;
import cn.com.shopec.core.resource.common.ParkConstant;
import cn.com.shopec.core.resource.model.Charger;
import cn.com.shopec.core.resource.model.Park;
import cn.com.shopec.core.resource.model.ParkCompanyRel;
import cn.com.shopec.core.resource.model.ParkingSpace;
import cn.com.shopec.core.resource.service.ChargerService;
import cn.com.shopec.core.resource.service.ParkCompanyRelService;
import cn.com.shopec.core.resource.service.ParkService;
import cn.com.shopec.core.resource.service.ParkStatusService;
import cn.com.shopec.core.resource.service.ParkingSpaceService;
import cn.com.shopec.core.search.model.PoloygonCoordinate;
import cn.com.shopec.core.system.model.DataDictItem;
import cn.com.shopec.core.system.model.SysParam;
import cn.com.shopec.core.system.model.SysRegion;
import cn.com.shopec.core.system.service.DataDictCatService;
import cn.com.shopec.core.system.service.DataDictItemService;
import cn.com.shopec.core.system.service.SysParamService;
import cn.com.shopec.core.system.service.SysRegionService;
import cn.com.shopec.mgt.common.BaseController;

@Controller
@RequestMapping("/park")
public class ParkController extends BaseController {
	@Resource
	private ParkService parkService;
	@Resource
	private DataDictCatService dataDictCatService;
	@Resource
	private DataDictItemService dataDictItemService;
	@Resource
	public SysRegionService sysRegionService;
	@Resource
	private ParkStatusService parkStatusService;
	@Resource
	private CarStatusService carStatusService;
	@Resource
	private ChargerService chargerService;
	@Resource
	private ParkingSpaceService parkingSpaceService;
	@Resource
	private CompanyService companyService;
	@Resource
	private ParkCompanyRelService parkCompanyRelService;
	@Resource
	private SysParamService sysParamService;
	@Resource
	private FranchiseeService franchiseeService;

	/*
	 * 显示场站列表页
	 */
	@RequestMapping("/mainPage")
	public String mainPage(String parkNo, Integer isLotParkingSpace, ModelMap modelMap) {
		modelMap.put("parkNo", parkNo);
		modelMap.put("isLotParkingSpace", isLotParkingSpace);
		return "resource/park_list";
	}

	/*
	 * 显示场站列表分页
	 */
	@RequestMapping("/pageListPark")
	@ResponseBody
	public PageFinder<Park> toParkList(@ModelAttribute("park") Park park, Query query) {
		Query q = new Query(query.getPageNo(), query.getPageSize(), park);
		return parkService.getParkPageList(q);
	}

	/*
	 * 判断场站名称不能重复
	 */
	@RequestMapping("/onlyParkName")
	@ResponseBody
	public ResultInfo<Park> onlyParkName(@ModelAttribute("parkName") String parkName) {
		ResultInfo<Park> resultInfo = new ResultInfo<Park>();
		Park park = new Park();
		park.setParkName(parkName);
		Query q = new Query(park);
		List<Park> res = parkService.getParkList(q);
		if (res != null && res.size() > 0) {
			resultInfo.setCode(Constant.SECCUESS);
		} else {
			resultInfo.setCode(Constant.FAIL);
		}
		return resultInfo;
	}

	/*
	 * 判断场站名称不能重复
	 */
	@RequestMapping("/onlyParkNameEdit")
	@ResponseBody
	public ResultInfo<Park> onlyParkNameEdit(@ModelAttribute("parkName") String parkName, String parkNo) {
		ResultInfo<Park> resultInfo = new ResultInfo<Park>();
		Park park = new Park();
		park.setParkName(parkName);
		park.setParkNo(parkNo);
		Query q = new Query(park);
		List<Park> res = parkService.getParkList(q);
		if (res != null && res.size() > 0) {
			resultInfo.setCode(Constant.SECCUESS);
		} else {
			resultInfo.setCode(Constant.FAIL);
		}
		Park parks = new Park();
		parks.setParkName(parkName);
		Query qs = new Query(parks);
		List<Park> ress = parkService.getParkList(qs);
		if (ress != null && ress.size() > 0) {
			for (Park park2 : ress) {
				if (park2.getParkNo().equals(parkNo)) {
					resultInfo.setCode(Constant.SECCUESS);
				}
			}
		} else {
			resultInfo.setCode(Constant.SECCUESS);
		}
		return resultInfo;
	}

	@RequestMapping("/getPark{")
	public String toUpdateParemView(String parkNo, ModelMap modelMap) {
		if (parkNo != null && !parkNo.equals("")) {
			Park park = parkService.getPark(parkNo);
			modelMap.put("park", park);
		}
		return "resource/park_param";
	}

	/**
	 * 显示场站编辑页面
	 * 
	 * @param parkNo
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/toUpdateView")
	public String getParkDetail(String parkNo, ModelMap modelMap) {
		if (parkNo != null && !parkNo.equals("")) {
			Park park = parkService.getPark(parkNo);
			Query q = new Query();
			List<CarStatus> carStatus = carStatusService.getCarSpaceShortage(q);
			if (carStatus.size() >= 0) {
				for (CarStatus carStatus2 : carStatus) {
					if (parkNo.equals(carStatus2.getLocationParkNo())) {
						park.setCarNumber(carStatus2.getCarSpaceShortage());
					}
				}
			}
			ParkingSpace ps = new ParkingSpace();
			ps.setParkNo(parkNo);
			List<ParkingSpace> parkingSpaces = parkingSpaceService.getParkingSpaceList(new Query(ps));
			// park.setParkingSpaceNumber(parkingSpaces.size());
			Charger charger = new Charger();
			charger.setParkNo(parkNo);
			q.setQ(charger);
			List<Charger> chargers = chargerService.getChargerList(q);
			if (chargers.size() > 0) {
				park.setChargerNumber(chargers.size());
			}

			List<DataDictItem> dots = dataDictItemService.getDataDictItemListByCatCode("WORKER_DOT");// 片区
			List<DataDictItem> cities = dataDictItemService.getDataDictItemListByCatCode("CITY");// 城市
			List<DataDictItem> styles = dataDictItemService.getDataDictItemListByCatCode("PARK_STYLE");// 场站样式
			List<SysRegion> plists = sysRegionService.getProvices();// 省
			List<SysRegion> plists2 = sysRegionService.getCitys(park.getAddrRegion1Id());// 市
			if (park.getAddrRegion2Id() != null) {
				List<SysRegion> plists3 = sysRegionService.getCountrys(park.getAddrRegion2Id());// 区
				modelMap.put("plists3", plists3);
			}
			Franchisee franchisee = franchiseeService.getFranchisee(park.getFranchiseeNo());
			if(franchisee != null){
				modelMap.put("franchisee", franchisee);
			}
			modelMap.put("dots", dots);
			modelMap.put("cities", cities);
			modelMap.put("styles", styles);
			modelMap.put("plist", plists);
			modelMap.put("plists2", plists2);
			modelMap.put("usedParkingSpaces", parkingSpaces != null ? parkingSpaces.size() : 0);// 已使用的车辆数
			modelMap.put("park", park);
		}
		// 获取所有集团列表
		Company companySearch = new Company();
		companySearch.setCencorStatus(3);// 已审核
		companySearch.setCompanyStatus(1);// 启用
		List<Company> companyList = companyService.getCompanyList(new Query(companySearch));
		modelMap.put("companyList", companyList);
		// 获取该场站已经所属的集团列表
		ParkCompanyRel parkCompanyRelSearch = new ParkCompanyRel();
		parkCompanyRelSearch.setParkId(parkNo);
		List<ParkCompanyRel> parkCompanyRelList = parkCompanyRelService
				.getParkCompanyRelList(new Query(parkCompanyRelSearch));
		modelMap.put("parkCompanyRelList", parkCompanyRelList);
		SysParam sysParam = sysParamService.getByParamKey(ParkConstant.PARK_TYPE);
		if (sysParam != null && sysParam.getParamValue() != null) {
			modelMap.put("isPloygon", sysParam.getParamValue());
		}
		return "resource/park_edit";
	}

	@RequestMapping("/getDotByRegionId")
	@ResponseBody
	public ResultInfo<List<DataDictItem>> getDotByRegionId(String cityId) {
		ResultInfo<List<DataDictItem>> resultInfo = new ResultInfo<List<DataDictItem>>();
		DataDictItem dm = new DataDictItem();
		dm.setDataDictCatCode("WORKER_DOT");
		dm.setParentItemId(cityId);

		List<DataDictItem> items = dataDictItemService.getDataDictItemList(new Query(dm));
		if (items.size() > 0) {
			resultInfo.setCode(Constant.SECCUESS);
			resultInfo.setData(items);
		} else {
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg("当前城市暂无片区信息");
		}
		return resultInfo;
	}

	/**
	 * 显示场站详情页面
	 * 
	 * @param parkNo
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/toParkView")
	public String toParkView(String parkNo, ModelMap modelMap) {
		if (parkNo != null) {
			Park park = parkService.toParkView(parkNo);
			Query q = new Query();
			List<CarStatus> carStatus = carStatusService.getCarSpaceShortage(q);
			if (carStatus.size() > 0) {
				for (CarStatus carStatus2 : carStatus) {
					if (parkNo.equals(carStatus2.getLocationParkNo())) {
						park.setCarNumber(carStatus2.getCarSpaceShortage());
					}
				}
			}
			ParkingSpace ps = new ParkingSpace();
			ps.setParkNo(parkNo);
			List<ParkingSpace> parkingSpaces = parkingSpaceService.getParkingSpaceList(new Query(ps));
			// park.setParkingSpaceNumber(parkingSpaces.size());
			Charger charger = new Charger();
			charger.setParkNo(parkNo);
			q.setQ(charger);
			List<Charger> chargers = chargerService.getChargerList(q);
			if (chargers.size() >= 0) {
				park.setChargerNumber(chargers.size());
			}
			modelMap.put("park", park);
			if (park.getParkPicUrl1() != null) {
				modelMap.put("picUrls", park.getParkPicUrl1().split(","));
			}
			//获取加盟商信息
			Franchisee franchisee = this.franchiseeService.getFranchisee(park.getFranchiseeNo());
			if(franchisee != null){
				modelMap.put("franchisee", franchisee);
			}
		}

		// 获取该场站已经所属的集团列表
		ParkCompanyRel parkCompanyRelSearch = new ParkCompanyRel();
		parkCompanyRelSearch.setParkId(parkNo);
		List<ParkCompanyRel> parkCompanyRelList = parkCompanyRelService
				.getParkCompanyRelList(new Query(parkCompanyRelSearch));
		modelMap.put("parkCompanyRelList", parkCompanyRelList);
		SysParam sysParam = sysParamService.getByParamKey(ParkConstant.PARK_TYPE);
		if (sysParam != null && sysParam.getParamValue() != null) {
			modelMap.put("isPloygon", sysParam.getParamValue());
		}
		
		return "resource/park_view";
	}

	/*
	 * 新增场站页
	 */
	@RequestMapping("/toAddPark")
	public String toAddPark(ModelMap modelMap) {
		List<DataDictItem> cities = dataDictItemService.getDataDictItemListByCatCode("CITY");// 城市
		List<DataDictItem> styles = dataDictItemService.getDataDictItemListByCatCode("PARK_STYLE");// 场站样式
		List<SysRegion> plists = sysRegionService.getProvices();// 省
		List<SysRegion> plists2 = sysRegionService.getCitys("440000");// 市
		List<SysRegion> plists3 = sysRegionService.getCountrys("440300");// 区
		Park park = new Park();
		park.setAddrRegion1Id("440000");
		park.setAddrRegion2Id("440300");
		park.setAddrRegion3Id("440303");
		modelMap.put("plists3", plists3);
		modelMap.put("plists2", plists2);
		modelMap.put("cities", cities);
		modelMap.put("styles", styles);
		modelMap.put("plist", plists);
		modelMap.put("park", park);
		// 获取集团列表
		Company companySearch = new Company();
		companySearch.setCencorStatus(3);// 已审核
		companySearch.setCompanyStatus(1);// 启用
		List<Company> companyList = companyService.getCompanyList(new Query(companySearch));
		SysParam sysParam = sysParamService.getByParamKey(ParkConstant.PARK_TYPE);
		if (sysParam != null && sysParam.getParamValue() != null) {
			modelMap.put("isPloygon", sysParam.getParamValue());
		}
		modelMap.put("companyList", companyList);
		return "resource/park_add";
	}

	/**
	 * * 场站编辑/增加页面提交
	 * 
	 * @param parkNo
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/updatePark")
	@ResponseBody
	public ResultInfo<Park> updatePark(@ModelAttribute("park") Park park,String franchiseeNo) {
		ResultInfo<Park> resultInfo = new ResultInfo<Park>();
		if (park.getParkNo() != null && park.getParkNo().length() != 0) {
			resultInfo = parkService.updatePark(park, getOperator());
		} else {
			if(StringUtils.isNotBlank(franchiseeNo)){
				park.setFranchiseeNo(franchiseeNo);
			}
			
			resultInfo = parkService.addPark(park, getOperator());
		}
		return resultInfo;
	}

	
	/**
	 * * 获取场站图片
	 * 
	 * @param parkNo
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/getPsrkPitureUrl")
	@ResponseBody
	public String[] getPsrkPitureUrl(String parkNo) {
		if (parkNo != null && parkNo.length() != 0) {
			Park park = parkService.getPark(parkNo);
			if (park.getParkPicUrl1() != null) {
				return park.getParkPicUrl1().split(",");
			}
		}
		return null;
	}
	
	/**
	 * * 场站启用/停用页面提交
	 * 
	 * @param parkNo
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/changeParkState")
	@ResponseBody
	public ResultInfo<Park> changeParkState(@ModelAttribute("park") Park park) {
		ResultInfo<Park> resultInfo = new ResultInfo<Park>();
		if (park.getParkNo() != null && park.getParkNo().length() != 0) {
			resultInfo = parkService.changeParkState(park, getOperator());
		} else {
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg("场站编号不能为空");
		}
		return resultInfo;
	}

	/*
	 * 查询car对象
	 */
	@RequestMapping("/toPark")
	@ResponseBody
	public Park toCar(String parkNo) {
		Park park = null;
		if (parkNo != null && parkNo.length() != 0) {
			park = parkService.getPark(parkNo);
		}
		return park;
	}

	/**
	 * 根据输入地址获取地址详细信息
	 */
	@RequestMapping("/searchAddress")
	@ResponseBody
	public ResultInfo<String> searchAddress(String addrRegion1Id, String addrRegion2Id, String addrRegion3Id,
			String addrStreet) {
		ResultInfo<String> resultInfo = new ResultInfo<String>();
		String addrRegion1Name = "";
		String addrRegion2Name = "";
		String addrRegion3Name = "";
		if (addrRegion1Id != null && !addrRegion1Id.equals("")) {
			SysRegion sysRegion1 = sysRegionService.detail(addrRegion1Id);
			if (sysRegion1 != null) {
				addrRegion1Name = sysRegion1.getRegionName();
			}
		}
		if (addrRegion2Id != null && !addrRegion2Id.equals("")) {
			SysRegion sysRegion2 = sysRegionService.detail(addrRegion2Id);
			if (sysRegion2 != null) {
				addrRegion2Name = sysRegion2.getRegionName();
			}
		}
		if (addrRegion3Id != null && !addrRegion3Id.equals("")) {
			SysRegion sysRegion3 = sysRegionService.detail(addrRegion3Id);
			if (sysRegion3 != null) {
				addrRegion3Name = sysRegion3.getRegionName();
			}
		}
		resultInfo.setCode(Constant.SECCUESS);
		if (addrStreet != null && !addrStreet.equals("")) {
			int index = addrStreet.indexOf(addrRegion1Name + addrRegion2Name + addrRegion3Name);
			if (index > -1) {
				resultInfo.setData(addrStreet);
				return resultInfo;
			}
		}
		resultInfo.setData(addrRegion1Name + addrRegion2Name + addrRegion3Name + addrStreet);
		return resultInfo;
	}

	/*
	 * 查询car对象
	 */
	@RequestMapping("checkPloygonPoints")
	@ResponseBody
	public ResultInfo<LongitudeAndLatitude> checkPloygonPoints(String points) throws Exception {
		ResultInfo<LongitudeAndLatitude> result = new ResultInfo<LongitudeAndLatitude>();
		List<LongitudeAndLatitude> pointsList = JsonUtils.parse2ListObject(points, LongitudeAndLatitude.class);
		if (pointsList != null && pointsList.size() > 0) {
			LongitudeAndLatitude longitudeAndLatitude = PoloygonCoordinate.getPolygonCoordinate(pointsList);
			if (longitudeAndLatitude != null) {
				result.setCode(Constant.SECCUESS);
				result.setData(longitudeAndLatitude);
			} else {
				result.setCode(Constant.FAIL);
				result.setMsg("所选多边形不是规范的多边形");
				;
			}
		} else {
			result.setCode(Constant.FAIL);
			result.setMsg("请先画多边形");
			;
		}
		return result;
	}

	/*
	 * 查询某城市下全部的场站
	 */
	@RequestMapping("/getCityPark")
	@ResponseBody
	public ResultInfo<List<Park>> getCityPark(String cityId) {
		ResultInfo<List<Park>> result = new ResultInfo<List<Park>>();
		if (cityId != null && cityId.length() != 0) {
			List<Park> parks = new ArrayList<Park>();
			Park park = new Park();
			park.setCityId(cityId);
			park.setIsDeleted(0);
			park.setIsAvailable(1);
			Query q = new Query(park);
			parks = parkService.getParkList(q);
			result.setCode(Constant.SECCUESS);
			result.setMsg("成功");
			result.setData(parks);
		} else {
			result.setCode(Constant.FAIL);
			result.setMsg("参数异常");
		}
		return result;
	}
}
