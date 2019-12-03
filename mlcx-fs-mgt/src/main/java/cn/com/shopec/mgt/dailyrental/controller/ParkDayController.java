package cn.com.shopec.mgt.dailyrental.controller;

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
import cn.com.shopec.core.car.model.CarStatus;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.dailyrental.model.Merchant;
import cn.com.shopec.core.dailyrental.model.ParkDay;
import cn.com.shopec.core.dailyrental.service.MerchantService;
import cn.com.shopec.core.dailyrental.service.ParkDayService;
import cn.com.shopec.core.franchisee.model.Franchisee;
import cn.com.shopec.core.member.model.Company;
import cn.com.shopec.core.resource.common.ParkConstant;
import cn.com.shopec.core.resource.model.Charger;
import cn.com.shopec.core.resource.model.Park;
import cn.com.shopec.core.resource.model.ParkCompanyRel;
import cn.com.shopec.core.resource.model.ParkingSpace;
import cn.com.shopec.core.resource.service.ParkService;
import cn.com.shopec.core.system.model.DataDictItem;
import cn.com.shopec.core.system.model.SysParam;
import cn.com.shopec.core.system.model.SysRegion;
import cn.com.shopec.core.system.service.DataDictItemService;
import cn.com.shopec.core.system.service.SysParamService;
import cn.com.shopec.mgt.common.BaseController;

@Controller
@RequestMapping("parkDay")
public class ParkDayController extends BaseController {
	@Resource
	private ParkService parkService;
	@Resource
	private DataDictItemService dataDictItemService;
	@Resource
	private SysParamService sysParamService;
	@Resource
	private MerchantService merchantService;
	@Resource
	private ParkDayService parkDayService;
	/*
	 * 显示门店列表页
	 */
	@RequestMapping("toParkDayList")
	public String mainPage(ModelMap modelMap) {
		return "dailyrental/parkday/parkDay_list";
	}

	/*
	 * 显示门店列表分页
	 */
	@RequestMapping("pageListParkDay")
	@ResponseBody
	public PageFinder<ParkDay> toParkList(@ModelAttribute("parkDay") ParkDay parkday, Query query) {
		Query q = new Query(query.getPageNo(), query.getPageSize(), parkday);
		return parkDayService.getParkDayPagedListForMgt(q);
	}
	/*
	 * 判断场站名称不能重复
	 */
	@RequestMapping("checkParkName")
	@ResponseBody
	public ResultInfo<String> checkParkName(@ModelAttribute("parkName") String parkName,String parkId) {
		ResultInfo<String> resultInfo = new ResultInfo<String>();
		ParkDay parkDaySearch = new ParkDay();
		parkDaySearch.setParkName(parkName);
		Query q = new Query(parkDaySearch);
		List<ParkDay> res = parkDayService.getParkDayList(q);
		if (res != null && res.size() > 0) {
			ParkDay parkDay = res.get(0);
			if(parkDay.getParkId().equals(parkId)){
				resultInfo.setCode(Constant.FAIL);
			}else{
				resultInfo.setCode(Constant.SECCUESS);
			}
		} else {
			resultInfo.setCode(Constant.FAIL);
		}
		return resultInfo;
	}
	/*
	 * 新增门店页
	 */
	@RequestMapping("toAddParkDay")
	public String toAddPark(ModelMap modelMap) {
		List<Merchant> merchants = merchantService.getMerchantList(new Query());
		modelMap.put("merchants", merchants);
		return "dailyrental/parkday/parkDay_add";
	}
	/**
	 * * 场站编辑/增加页面提交
	 * 
	 * @param parkNo
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("updatePark")
	@ResponseBody
	public ResultInfo<ParkDay> updatePark(@ModelAttribute("parkDay") ParkDay parkDay) {
		ResultInfo<ParkDay> resultInfo = new ResultInfo<ParkDay>();
		if (parkDay.getParkId() != null && parkDay.getParkId().length() != 0) {
			if(parkDay.getFullAddr()!=null&&!"".equals(parkDay.getFullAddr())){
				if(parkDay.getFullAddr().indexOf("undefined")>-1){
					resultInfo.setCode(Constant.FAIL);
					resultInfo.setMsg("地址不合法");
					return resultInfo;
				}
				String[] address = parkDay.getFullAddr().split(",");
				if(address.length==3){
					parkDay.setAddrRegion1Name(address[0].trim());
					parkDay.setAddrRegion2Name(address[1].trim());
					parkDay.setAddrRegion3Name(address[2].trim());
				}else if(address.length==4){
					parkDay.setAddrRegion1Name(address[0].trim());
					parkDay.setAddrRegion2Name(address[1].trim());
					parkDay.setAddrRegion3Name(address[2].trim());
					parkDay.setAddrStreet(address[3].trim());
				}else if(address.length==5){
					parkDay.setAddrRegion1Name(address[0].trim());
					parkDay.setAddrRegion2Name(address[1].trim());
					parkDay.setAddrRegion3Name(address[2].trim());
					parkDay.setAddrStreet(address[3].trim()+address[4].trim());
				}
				List<DataDictItem> cities = dataDictItemService.getDataDictItemListByCatCode("CITY");// 城市
				for(DataDictItem item:cities){
					if(item.getItemValue().trim().equals(parkDay.getAddrRegion2Name())){
						parkDay.setCityId(item.getDataDictItemId());
						break;
					}
				}
				resultInfo = parkDayService.updateParkDay(parkDay, getOperator());
			}else{
				resultInfo.setCode(Constant.FAIL);
				resultInfo.setMsg("地址为空");
				return resultInfo;
			}
		} else {
			if(parkDay.getFullAddr()!=null&&!"".equals(parkDay.getFullAddr())){
				if(parkDay.getFullAddr().indexOf("undefined")>-1){
					resultInfo.setCode(Constant.FAIL);
					resultInfo.setMsg("地址不合法");
					return resultInfo;
				}
				String[] address = parkDay.getFullAddr().split(",");
				if(address.length==3){
					parkDay.setAddrRegion1Name(address[0].trim());
					parkDay.setAddrRegion2Name(address[1].trim());
					parkDay.setAddrRegion3Name(address[2].trim());
				}else if(address.length==4){
					parkDay.setAddrRegion1Name(address[0].trim());
					parkDay.setAddrRegion2Name(address[1].trim());
					parkDay.setAddrRegion3Name(address[2].trim());
					parkDay.setAddrStreet(address[3].trim());
				}else if(address.length==5){
					parkDay.setAddrRegion1Name(address[0].trim());
					parkDay.setAddrRegion2Name(address[1].trim());
					parkDay.setAddrRegion3Name(address[2].trim());
					parkDay.setAddrStreet(address[3].trim()+address[4].trim());
				}
				List<DataDictItem> cities = dataDictItemService.getDataDictItemListByCatCode("CITY");// 城市
				for(DataDictItem item:cities){
					if(item.getItemValue().trim().equals(parkDay.getAddrRegion2Name())){
						parkDay.setCityId(item.getDataDictItemId());
						break;
					}
				}
				resultInfo = parkDayService.addParkDay(parkDay, getOperator());
			}else{
				resultInfo.setCode(Constant.FAIL);
				resultInfo.setMsg("地址为空");
				return resultInfo;
			}
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
	@RequestMapping("toParkDayView")
	public String toParkView(String parkId, ModelMap modelMap) {
		if (parkId != null) {
			ParkDay parkDay = parkDayService.getParkDay(parkId);
			if(parkDay!=null){
				DataDictItem item = dataDictItemService.getDataDictItem(parkDay.getCityId());
				parkDay.setCityName(item.getItemValue());
				Merchant merchant = merchantService.getMerchant(parkDay.getMerchantId());
				parkDay.setMerchantName(merchant.getMerchantName());
				parkDay.setAddrStreet(parkDay.getAddrRegion1Name()+parkDay.getAddrRegion2Name()+parkDay.getAddrRegion3Name()+parkDay.getAddrStreet());
			}
			modelMap.put("parkDay", parkDay);
		}
		
		return "dailyrental/parkday/parkDay_view";
	}
	/**
	 * 显示门店编辑页面
	 * 
	 * @param parkNo
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("toUpdateParkDay")
	public String toUpdateParkDay(String parkId, ModelMap modelMap) {
		if (parkId != null) {
			ParkDay parkDay = parkDayService.getParkDay(parkId);
			if(parkDay!=null){
				List<Merchant> merchants = merchantService.getMerchantList(new Query());
				modelMap.put("merchants", merchants);
				parkDay.setAddrStreet(parkDay.getAddrRegion1Name()+parkDay.getAddrRegion2Name()+parkDay.getAddrRegion3Name()+parkDay.getAddrStreet());
			}
			modelMap.put("parkDay", parkDay);
		}
		
		return "dailyrental/parkday/parkDay_edit";
	}
	/*
	 * 获取parkDay
	 */
	@RequestMapping("getParkDay")
	@ResponseBody
	public ResultInfo<ParkDay> getParkDay(String parkId) {
		ResultInfo<ParkDay> result = new ResultInfo<ParkDay>();
		ParkDay parkDay = null;
		if (parkId != null && parkId.length() != 0) {
			parkDay = parkDayService.getParkDay(parkId);
			result.setCode(Constant.SECCUESS);
			result.setData(parkDay);
		}else{
			result.setCode(Constant.FAIL);
			result.setMsg("门店不存在");;
		}
		return result;
	}
	/**
	 * * 场站启用/停用页面提交
	 * 
	 * @param parkNo
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("changeParkDayState")
	@ResponseBody
	public ResultInfo<ParkDay> changeParkDayState(@ModelAttribute("parkDay") ParkDay parkDay) {
		ResultInfo<ParkDay> resultInfo = new ResultInfo<ParkDay>();
		if (parkDay.getParkId() != null && parkDay.getParkId().length() != 0) {
			//门店启用时，判断所属商家是否启用，未启用提示
			ParkDay parkDaySearch = parkDayService.getParkDay(parkDay.getParkId());
			if(parkDay.getIsVailable()==1){
				Merchant merchant = merchantService.getMerchant(parkDaySearch.getMerchantId());
				if(merchant!=null){
					if(merchant.getIsAvailable()==0){
						resultInfo.setCode(Constant.FAIL);
						resultInfo.setMsg("请先启用门店所属商家");
					}else{
						resultInfo = parkDayService.changeParkDayState(parkDay, getOperator());
					}
				}else{
					resultInfo.setCode(Constant.FAIL);
					resultInfo.setMsg("门店所属商家不存在");
				}
			}else{
				ParkDay queryParkDay = new ParkDay();
				queryParkDay.setMerchantId(parkDaySearch.getMerchantId());
				queryParkDay.setIsVailable(1);
				List<ParkDay> parkDayList = parkDayService.getParkDayList(new Query(queryParkDay));
				if (parkDayList.size()<=1) {
					resultInfo.setCode(Constant.FAIL);
					resultInfo.setMsg("暂时不能停用，若要全部停用请停用商家");
				}else{
					resultInfo = parkDayService.changeParkDayState(parkDay, getOperator());
				}
			}
		} else {
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg("场站编号不能为空");
		}
		return resultInfo;
	}

}
