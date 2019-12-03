package cn.com.shopec.mgt.resource.controller;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.common.constants.Constant;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.resource.model.Charger;
import cn.com.shopec.core.resource.model.Park;
import cn.com.shopec.core.resource.model.ParkingSpace;
import cn.com.shopec.core.resource.service.ChargerService;
import cn.com.shopec.core.resource.service.ParkService;
import cn.com.shopec.core.resource.service.ParkingSpaceService;
import cn.com.shopec.core.system.model.DataDictItem;
import cn.com.shopec.core.system.service.DataDictItemService;
import cn.com.shopec.mgt.common.BaseController;
@Controller
@RequestMapping("/parkingSpace")
public class ParkingSpaceController extends BaseController{
@Resource
private ParkService parkService;
@Resource
private ParkingSpaceService parkingSpaceService;
@Resource
private DataDictItemService dataDictItemService;
@Resource
private ChargerService chargerService;
	/*
	 * 显示车位列表页
	 */
	@RequestMapping("/toParkingSpace")
	public String toParkingSpace(ModelMap modelMap,String parkNo) {
		List<DataDictItem> cities = dataDictItemService.getDataDictItemListByCatCode("CITY");//城市
		modelMap.put("cities", cities);
		modelMap.put("parkNo", parkNo);
		return "resource/parkingSpace_list";
	}
	/*
	 * 显示场站列表分页
	 */
	@RequestMapping("/pageListPark")
	@ResponseBody
	public PageFinder<ParkingSpace> pageListPark(@ModelAttribute("parkingSpace") ParkingSpace parkingSpace,Query query) {
		Query q = new Query(query.getPageNo(),query.getPageSize(),parkingSpace);
		return parkingSpaceService.getParkingSpacePageList(q);
	}
	/*
	 * 显示车位详情页
	 */
	@RequestMapping("/toParkingSpaceView")
	public String toParkingSpaceView(String parkingSpaceNo,ModelMap modelMap) {
		ParkingSpace parkingSpace=parkingSpaceService.getParkingSpace(parkingSpaceNo);
		Park park=null;
		if(parkingSpace.getParkNo()!=null){
			park=parkService.getPark(parkingSpace.getParkNo());
		}
		if(park!=null){
			parkingSpace.setOwnerType(park.getOwnerType());
		}
		modelMap.put("parkingSpace", parkingSpace);
		modelMap.put("park", park);
		return "resource/parkingSpace_view";
	}
	/*
	 * 显示车位详情编辑页
	 */
	@RequestMapping("/toUpdateView")
	public String toUpdateView(String parkingSpaceNo,ModelMap modelMap) {
		Query query=new Query();
		List<Park> parks=parkService.getParkList(query);
		List<DataDictItem> cities = dataDictItemService.getDataDictItemListByCatCode("CITY");//城市
		ParkingSpace parkingSpace=parkingSpaceService.getParkingSpace(parkingSpaceNo);
		Park park=null;
		if(parkingSpace.getParkNo()!=null){
			park=parkService.getPark(parkingSpace.getParkNo());	
		}
		modelMap.put("parkingSpace", parkingSpace);
		modelMap.put("cities", cities);
		modelMap.put("park", park);
		modelMap.put("parks", parks);
		return "resource/parkingSpace_edit";
	}
	/*
	 * 车位增加/修改
	 */
	@RequestMapping("/updateParkingSpace")
	@ResponseBody
	public ResultInfo<ParkingSpace> updateParkingSpace(@ModelAttribute("parkingSpace") ParkingSpace parkingSpace) {
		ResultInfo<ParkingSpace> resultInfo = parkingSpaceService.checkParkingSpaceNuber(parkingSpace);
		if(Constant.FAIL.equals(resultInfo.getCode())){//校验场站
			return resultInfo;
		}
		resultInfo =  new ResultInfo<ParkingSpace>();
		//停用电桩，把电桩编号只为空
		if(parkingSpace.getHasCharger()==0){
			parkingSpace.setChargerNo(" ");
			resultInfo= parkingSpaceService.updateParkingSpaces(parkingSpace, getOperator());
			
		}
		Charger charger = chargerService.getCharger(parkingSpace.getChargerNo());
		if(parkingSpace.getParkingSpaceNo()!=null){
			if(charger!=null){
				List<ParkingSpace> lists = parkingSpaceService.getParkingSpaceList(new Query(parkingSpace));
				if(lists!=null && lists.size()>0 && !parkingSpace.getParkingSpaceNo().equals(lists.get(0).getParkingSpaceNo())){
					resultInfo.setCode(Constant.FAIL);
					resultInfo.setMsg("充电桩已绑定！");
				}else{
					if(parkingSpace.getIsAvailable()!=null){
						if(parkingSpace.getIsAvailable().equals(0)){
							parkingSpace.setDisabledUpdateTime(new Date());
						}else if(parkingSpace.getIsAvailable().equals(1)){
							parkingSpace.setAvailableUpdateTime(new Date());
						}
					}
					resultInfo= parkingSpaceService.updateParkingSpaces(parkingSpace, getOperator());
				}
			}else{
				if(parkingSpace.getHasCharger().equals(0)){
					if(parkingSpace.getIsAvailable()!=null){
						if(parkingSpace.getIsAvailable().equals(0)){
							parkingSpace.setDisabledUpdateTime(new Date());
						}else if(parkingSpace.getIsAvailable().equals(1)){
							parkingSpace.setAvailableUpdateTime(new Date());
						}
					}
					resultInfo= parkingSpaceService.updateParkingSpaces(parkingSpace, getOperator());
				}else{
					resultInfo.setCode(Constant.FAIL);
					resultInfo.setMsg("充电桩编号不存在！");
				}
			}
		}else{
			if(charger!=null){
				List<ParkingSpace> lists = parkingSpaceService.getParkingSpaceList(new Query(parkingSpace));
				if(lists!=null&&lists.size()>0){
					resultInfo.setCode(Constant.FAIL);
					resultInfo.setMsg("充电桩已绑定！");
				}else{
					parkingSpace.setDisabledUpdateTime(new Date());
					resultInfo=parkingSpaceService.addParkingSpace(parkingSpace,  getOperator());
				}
			}else{
				if(parkingSpace.getHasCharger().equals(0)){
					parkingSpace.setDisabledUpdateTime(new Date());
					resultInfo=parkingSpaceService.addParkingSpace(parkingSpace,  getOperator());
				}else{
					resultInfo.setCode(Constant.FAIL);
					resultInfo.setMsg("充电桩编号不存在！");
				}
			}
		}
		return resultInfo;
	}
	/*
	 * 车位增加/修改
	 */
	@RequestMapping("availableParkingSpace")
	@ResponseBody
	public ResultInfo<ParkingSpace> availableParkingSpace(@ModelAttribute("parkingSpace") ParkingSpace parkingSpace) {
		ResultInfo<ParkingSpace> resultInfo=new ResultInfo<ParkingSpace>();
		if(parkingSpace.getIsAvailable()!=null){
			if(parkingSpace.getIsAvailable().equals(0)){
				parkingSpace.setDisabledUpdateTime(new Date());
			}else if(parkingSpace.getIsAvailable().equals(1)){
				parkingSpace.setAvailableUpdateTime(new Date());
			}
			resultInfo= parkingSpaceService.updateParkingSpaces(parkingSpace, getOperator());
		}else{
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg("启用/停用失败！");
		}
		return resultInfo;
	}
	/*
	 * 新增车位页面
	 */
	@RequestMapping("/addParkingSpacePage")
	public String addParkingSpacePage(ModelMap modelMap,String parkNo) {
		Query query=new Query();
		List<Park> parks = parkService.getParkList(query);
		List<DataDictItem> cities = dataDictItemService.getDataDictItemListByCatCode("CITY");//城市
		modelMap.put("cities", cities);
		modelMap.put("parks", parks);
		if(parkNo != null && !parkNo.equals("")){
			Park park = parkService.getPark(parkNo);
			modelMap.put("parkNo", parkNo);
			modelMap.put("cityId", park.getCityId());
		}
		return "resource/parkingSpace_add";
	}
	/*
	 * 车位增加/修改
	 */
	@RequestMapping("/getParkingSpace")
	@ResponseBody
	public ParkingSpace getParkingSpace(String parkingSpaceNo) {
		ParkingSpace parkingSpace=null;
		if(parkingSpaceNo!=null&&parkingSpaceNo.length()!=0){
			parkingSpace=parkingSpaceService.getParkingSpace(parkingSpaceNo);
		}
		return parkingSpace;
	}
}
