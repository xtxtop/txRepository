package cn.com.shopec.mgt.resource.controller;




import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.common.constants.Constant;
import cn.com.shopec.common.utils.JsonUtils;
import cn.com.shopec.core.car.model.LongitudeAndLatitude;
import cn.com.shopec.core.car.service.CarStatusService;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.member.service.CompanyService;
import cn.com.shopec.core.resource.common.ParkConstant;
import cn.com.shopec.core.resource.model.ReturnArea;
import cn.com.shopec.core.resource.service.ChargerService;
import cn.com.shopec.core.resource.service.ParkCompanyRelService;
import cn.com.shopec.core.resource.service.ParkStatusService;
import cn.com.shopec.core.resource.service.ParkingSpaceService;
import cn.com.shopec.core.resource.service.ReturnAreaService;
import cn.com.shopec.core.search.model.PoloygonCoordinate;
import cn.com.shopec.core.search.service.ReturnAreaIndexService;
import cn.com.shopec.core.system.model.DataDictItem;
import cn.com.shopec.core.system.model.SysParam;
import cn.com.shopec.core.system.model.SysRegion;
import cn.com.shopec.core.system.service.DataDictCatService;
import cn.com.shopec.core.system.service.DataDictItemService;
import cn.com.shopec.core.system.service.SysParamService;
import cn.com.shopec.core.system.service.SysRegionService;
import cn.com.shopec.mgt.common.BaseController;
@Controller
@RequestMapping("/returnArea")
public class ReturnAreaController extends BaseController{

	@Resource
	private ReturnAreaService returnAreaService;
	@Resource
	private ReturnAreaIndexService returnAreaIndexService;
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

/*
 * 显示场站列表页
 */
@RequestMapping("/mainPage")
public String mainPage() {
	return "resource/returnArea_list";
}

/*
 * 显示场站列表分页
 */
@RequestMapping("/pageListReturnArea")
@ResponseBody
public PageFinder<ReturnArea> toParkList(@ModelAttribute("returnArea") ReturnArea returnArea,Query query) {
	Query q = new Query(query.getPageNo(),query.getPageSize(),returnArea);
	return returnAreaService.getReturnAreaPagedList(q);
}


/*
 * 判断场站名称不能重复
 */
@RequestMapping("/onlyAreaName")
@ResponseBody
public ResultInfo<ReturnArea> onlyParkName(@ModelAttribute("areaName") String  areaName) {
	ResultInfo<ReturnArea> resultInfo=new ResultInfo<ReturnArea>();
	ReturnArea returnArea= new ReturnArea();
	returnArea.setAreaName(areaName);
	Query q= new Query(returnArea);
	List<ReturnArea> res =returnAreaService.getReturnAreaList(q);
	if(res != null &&  res.size()>0){
		resultInfo.setCode(Constant.SECCUESS);
	}else{
		resultInfo.setCode(Constant.FAIL);
	}
	return resultInfo;
}
/*
 * 判断场站名称不能重复
 */
@RequestMapping("/onlyAreaNameEdit")
@ResponseBody
public ResultInfo<ReturnArea> onlyParkNameEdit(@ModelAttribute("areaName") String  areaName, String  returnAreaId) {
	ResultInfo<ReturnArea> resultInfo=new ResultInfo<ReturnArea>();
	ReturnArea returnArea= new ReturnArea();
	returnArea.setAreaName(areaName);
	returnArea.setReturnAreaId(returnAreaId);
	Query q= new Query(returnArea);
	List<ReturnArea> res =returnAreaService.getReturnAreaList(q);
	if(res != null &&  res.size()>0){
		resultInfo.setCode(Constant.SECCUESS);
	}else{
		resultInfo.setCode(Constant.FAIL);
	}
	ReturnArea returnAreas= new ReturnArea();
	returnAreas.setAreaName(areaName);
	Query qs= new Query(returnAreas);
	List<ReturnArea> ress =returnAreaService.getReturnAreaList(qs);
	if(ress != null &&  ress.size()>0){
		for (ReturnArea returnArea2 : ress) {
			if(returnArea2.getReturnAreaId().equals(returnAreaId)){
				resultInfo.setCode(Constant.SECCUESS);
			}
		}
	}else{
		resultInfo.setCode(Constant.SECCUESS);
	}
	
	return resultInfo;
}
//
//
//
/**
 * 显示场站编辑页面
 * @param parkNo
 * @param modelMap
 * @return
 */
@RequestMapping("/toUpdateView")
public String getParkDetail(String returnAreaId,ModelMap modelMap) {
	if(returnAreaId!=null){
		ReturnArea returnArea=returnAreaService.getReturnArea(returnAreaId);
		if(returnArea!= null){
			modelMap.put("returnArea", returnArea);
		}
		SysParam sysParam = sysParamService.getByParamKey(ParkConstant.PARK_TYPE);
		if(sysParam!=null&&sysParam.getParamValue()!=null){
			modelMap.put("isPloygon", sysParam.getParamValue());
		}
		List<DataDictItem> cities = dataDictItemService.getDataDictItemListByCatCode("CITY");//城市
		modelMap.put("cities", cities);
		
		}
	return "resource/returnArea_edit";
}

/**
 * 还车区域详情
 */
@RequestMapping("/toAreaView")
public String toAreaView(String returnAreaId,ModelMap modelMap) {
	if(returnAreaId!=null){
	ReturnArea returnArea=returnAreaService.getReturnArea(returnAreaId);
	if(returnArea!= null){
		modelMap.put("returnArea", returnArea);
	}
	SysParam sysParam = sysParamService.getByParamKey(ParkConstant.PARK_TYPE);
	if(sysParam!=null&&sysParam.getParamValue()!=null){
		modelMap.put("isPloygon", sysParam.getParamValue());
	}
	
	}
	return "resource/returnArea_view";
}
/*
 * 新增场站页
 */
@RequestMapping("/toAddreturnArea")
public String toAddreturnArea(ModelMap modelMap) {
	List<DataDictItem> cities = dataDictItemService.getDataDictItemListByCatCode("CITY");//城市
	modelMap.put("cities", cities);
	SysParam sysParam = sysParamService.getByParamKey(ParkConstant.PARK_TYPE);
	if(sysParam!=null&&sysParam.getParamValue()!=null){
		modelMap.put("isPloygon", sysParam.getParamValue());
	}
	List<SysRegion> plists = sysRegionService.getProvices();//省
	List<SysRegion> plists2 = sysRegionService.getCitys("440000");//市
	List<SysRegion> plists3 = sysRegionService.getCountrys("440300");//区
	modelMap.put("plists3", plists3);
	modelMap.put("plists2", plists2);
	modelMap.put("plist", plists);
	
	return "resource/returnArea_add";
}
/**
 *  还车区域编辑/增加页面提交
 
 * @param returnArea
 * @return
 */
@RequestMapping("/updateReturnArea")
@ResponseBody
public ResultInfo<ReturnArea> updateReturnArea(@ModelAttribute("returnArea") ReturnArea returnArea) {
	ResultInfo<ReturnArea> resultInfo=new ResultInfo<ReturnArea>();
	//城市
	DataDictItem dataDictItemcity= dataDictItemService.getDataDictItem(returnArea.getCityId());
	if(dataDictItemcity!=null){
		returnArea.setCityName(dataDictItemcity.getItemValue());
	}
	if(returnArea.getReturnAreaId()!=null&&returnArea.getReturnAreaId().length()!=0){
		resultInfo=returnAreaService.updateReturnArea(returnArea, getOperator());
	}else{
		returnArea.setIsAvailable(0);
		resultInfo=returnAreaService.addReturnArea(returnArea, getOperator());
	}
	return resultInfo;
}
//
/**
 * 还车区域启用/停用页面提交
 * @param park
 * @return
 */
@RequestMapping("changeAreaState")
@ResponseBody
public ResultInfo<ReturnArea> changeAreaState(@ModelAttribute("ReturnArea") ReturnArea returnArea) {
	ResultInfo<ReturnArea> resultInfo=new ResultInfo<ReturnArea>();
	if(returnArea.getReturnAreaId()!=null&&returnArea.getReturnAreaId().length()!=0){
		resultInfo=returnAreaService.updateReturnArea(returnArea, getOperator());
		if(resultInfo.getCode().equals(Constant.SECCUESS)){
			if(returnArea.getIsAvailable()!=null){
				if(returnArea.getIsAvailable()==1){//启用，添加还车区域到solr
					boolean res = returnAreaIndexService.saveOrUpdateReturnArea(returnArea.getReturnAreaId());
					if(res){
						resultInfo.setCode(Constant.SECCUESS);
					}else{
						resultInfo.setCode(Constant.FAIL);
					}
				}else{//停用删除
					boolean res = returnAreaIndexService.deleteReturnArea(returnArea.getReturnAreaId());
					if(res){
						resultInfo.setCode(Constant.SECCUESS);
					}else{
						resultInfo.setCode(Constant.FAIL);
					}
				}
			}
		}else{
			resultInfo.setCode(Constant.FAIL);
		}
	}else{
		resultInfo.setCode(Constant.FAIL);
		resultInfo.setMsg("还车区域编号不能为空");
	}
	return resultInfo;
}


/**
 * 根据输入地址获取地址详细信息
 * */
@RequestMapping("/searchAddress")
@ResponseBody
public ResultInfo<String> searchAddress(String addrRegion1Id,String addrRegion2Id,String addrRegion3Id,String addrStreet) {
	ResultInfo<String> resultInfo=new ResultInfo<String>();
	String addrRegion1Name="";
	String addrRegion2Name="";
	String addrRegion3Name="";
	if(addrRegion1Id!=null&&!addrRegion1Id.equals("")){
		SysRegion sysRegion1=sysRegionService.detail(addrRegion1Id);
		if(sysRegion1!=null){
			addrRegion1Name=sysRegion1.getRegionName();
		}
	}
	if(addrRegion2Id!=null&&!addrRegion2Id.equals("")){
		SysRegion sysRegion2=sysRegionService.detail(addrRegion2Id);
		if(sysRegion2!=null){
			addrRegion2Name=sysRegion2.getRegionName();
		}
	}
	if(addrRegion3Id!=null&&!addrRegion3Id.equals("")){
		SysRegion sysRegion3=sysRegionService.detail(addrRegion3Id);
		if(sysRegion3!=null){
			addrRegion3Name=sysRegion3.getRegionName();
		}
	}
	resultInfo.setCode(Constant.SECCUESS);
	if(addrStreet!=null&&!addrStreet.equals("")){
		int index = addrStreet.indexOf(addrRegion1Name+addrRegion2Name+addrRegion3Name);
		if(index > -1){
			resultInfo.setData(addrStreet);
			return resultInfo;
		}
	}
	resultInfo.setData(addrRegion1Name+addrRegion2Name+addrRegion3Name+addrStreet);
	return resultInfo;
}
	/*
	 * 查询是不是多边形对象
	 */
	@RequestMapping("checkPloygonPoints")
	@ResponseBody
	public ResultInfo<LongitudeAndLatitude> checkPloygonPoints(String points) throws Exception {
		ResultInfo<LongitudeAndLatitude> result = new ResultInfo<LongitudeAndLatitude>();
		List<LongitudeAndLatitude> pointsList = JsonUtils.parse2ListObject(points, LongitudeAndLatitude.class);
		if(pointsList!=null&&pointsList.size()>0){
			LongitudeAndLatitude longitudeAndLatitude = PoloygonCoordinate.getPolygonCoordinate(pointsList);
			if(longitudeAndLatitude!=null){
				result.setCode(Constant.SECCUESS);
				result.setData(longitudeAndLatitude);
			}else{
				result.setCode(Constant.FAIL);
				result.setMsg("所选多边形不是规范的多边形");;
			}
		}else{
			result.setCode(Constant.FAIL);
			result.setMsg("请先画多边形");;
		}
		return result;
	}
//	
//	/*
//	 * 查询某城市下全部的场站
//	 */
//	@RequestMapping("/getCityPark")
//	@ResponseBody
//	public ResultInfo<List<Park>> getCityPark(String cityId) {
//		ResultInfo<List<Park>> result = new ResultInfo<List<Park>>();
//		if(cityId!=null&&cityId.length()!=0){
//			List<Park> parks = new ArrayList<Park>();
//			Park park= new Park();
//			park.setCityId(cityId);
//			park.setIsDeleted(0);
//			park.setIsAvailable(1);
//			Query q = new Query(park);
//			parks = parkService.getParkList(q);
//			result.setCode(Constant.SECCUESS);
//			result.setMsg("成功");
//			result.setData(parks);
//		}else{
//			result.setCode(Constant.FAIL);
//			result.setMsg("参数异常");
//		}
//		return result;
//	}
}
