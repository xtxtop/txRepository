package cn.com.shopec.mgt.mlparking.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.ml.model.CLabel;
import cn.com.shopec.core.ml.model.CMatching;
import cn.com.shopec.core.ml.model.ChargingStation;
import cn.com.shopec.core.ml.service.CDocService;
import cn.com.shopec.core.ml.service.CLabelService;
import cn.com.shopec.core.ml.service.CMatchingService;
import cn.com.shopec.core.ml.service.COperatingCityService;
import cn.com.shopec.core.ml.vo.ChargingStationVo;
import cn.com.shopec.core.mlparking.model.CParking;
import cn.com.shopec.core.mlparking.model.CPliesNumber;
import cn.com.shopec.core.mlparking.service.CParkBillingService;
import cn.com.shopec.core.mlparking.service.CParkingService;
import cn.com.shopec.core.mlparking.service.CPliesNumberService;
import cn.com.shopec.core.mlparking.vo.ParkingVo;
import cn.com.shopec.core.mlparking.vo.PliesNumberVo;
import cn.com.shopec.core.system.model.SysRegion;
import cn.com.shopec.core.system.service.SysRegionService;
import cn.com.shopec.mgt.common.BaseController;

/**
 * @author LiuKang
 *
 */
@Controller
@RequestMapping("parking")
public class ParkingController extends BaseController {

	@Resource
	private CParkingService cParkingService;
	@Resource
	private SysRegionService sysRegionServiceImpl;
	@Resource
	private CLabelService cLabelServiceImpl;
	@Resource
	private CMatchingService cMatchingServiceImpl;
	@Resource
	private COperatingCityService operatingCityServiceImpl;
	@Resource
	private CParkBillingService parkBillingService;
	@Resource
	private CDocService docServiceImpl;
	@Resource
	private CPliesNumberService pliesNumberService;

	/**
	 * <p>
	 * Method: getParkingList
	 * </p>
	 * <p>
	 * Description: 进入停车场列表页
	 * </p>
	 * <p>
	 * Author：LiuKang
	 * </p>
	 */
	@RequestMapping("toParkingView")
	public String getParkingList(Model model) {
		model.addAttribute("city",
				operatingCityServiceImpl.getCOperatingCityList(new Query()));
		return "mlpark/parking_list";
	}

	/**
	 * <p>
	 * Method: toParkingList
	 * </p>
	 * <p>
	 * Description: 获取停车场列表数据
	 * </p>
	 * <p>
	 * Author：LiuKang
	 * </p>
	 */
	@RequestMapping("toparkingList")
	@ResponseBody
	public PageFinder<ParkingVo> toParkingList(
			@ModelAttribute("ParkingVo") ParkingVo parkingVo, Query query) {
		return cParkingService.getParkList(new Query(query.getPageNo(), query
				.getPageSize(), parkingVo));
	}

	/**
	 * <p>
	 * Method: toAddParking
	 * </p>
	 * <p>
	 * Description: 进入停车场添加页面
	 * </p>
	 * <p>
	 * Author：LiuKang
	 * </p>
	 */
	@RequestMapping("addparking")
	public String toAddParking(Model model) {
		List<SysRegion> plists = sysRegionServiceImpl.getProvices();// 省
		List<SysRegion> plists2 = sysRegionServiceImpl.getCitys("440000");// 市
		List<SysRegion> plists3 = sysRegionServiceImpl.getCountrys("440300");// 区
		model.addAttribute("plist", plists);
		model.addAttribute("plists3", plists3);
		model.addAttribute("plists2", plists2);
		// 获取标签
		List<CLabel> clabel = cLabelServiceImpl.getCLabelList(new Query());
		model.addAttribute("clabel", clabel);
		// 配套信息
		List<CMatching> cmatching = cMatchingServiceImpl
				.getCMatchingList(new Query());
		model.addAttribute("cmatching", cmatching);
		// 运营城市
		model.addAttribute("operatingCity",
				operatingCityServiceImpl.getCOperatingCityList(new Query()));
		// 计费方案
		model.addAttribute("billingScheme",
				parkBillingService.getCParkBillingList(new Query()));
		return "mlpark/parking_add";
	}

	/**
	 * 查看详情
	 * 
	 * @param stationNo
	 * @param model
	 * @return
	 */
	@RequestMapping("/toparkingView")
	public String toparkingView(String parkingNo, Model model) {
		ParkingVo parkingVo = cParkingService.getParkListNo(parkingNo);
		parkingVo.setFileUrl(docServiceImpl.getCDoc(
				docServiceImpl.getCDocNo(parkingNo)).getFileUrl());
		model.addAttribute("parkingVo", parkingVo);
		// 获取标签
		if (parkingVo.getLabel() != null && !"".equals(parkingVo.getLabel())) {
			String str_clabel[] = parkingVo.getLabel().toString().split(",");
			List<CLabel> clabel = cLabelServiceImpl.getCLabelByIds(str_clabel);
			model.addAttribute("clabel", clabel);
		}
		// 配套信息
		if (parkingVo.getSupportedServices() != null
				&& !"".equals(parkingVo.getSupportedServices())) {
			String str_cmatching[] = parkingVo.getSupportedServices()
					.toString().split(",");
			List<CMatching> cmatching = cMatchingServiceImpl
					.getCMatchingByIds(str_cmatching);
			model.addAttribute("cmatching", cmatching);
		}
		//多层车位分布
		CPliesNumber pn=new CPliesNumber();
		pn.setParkingNo(parkingNo);
		List<CPliesNumber> cPliesNumberList = pliesNumberService.getCPliesNumberList(new Query(pn));
		cPliesNumberList = cPliesNumberList == null ? new ArrayList<CPliesNumber>(0) : cPliesNumberList;
		model.addAttribute("pliesNumberList", cPliesNumberList);
		return "mlpark/parking_view";
	}

	/**
	 * Method: toAddParkBilling
	 * <p>
	 * Description: 停车场数据添加
	 * Author：LiuKang
	 * @param spaceNumber_add 楼层
	 * @param ground_spaceNumber_add 地面
	 * @param under_spaceNumber_add 地下
	 */
	@RequestMapping("toAddParking")
	@ResponseBody
	public ResultInfo<CParking> toAddParkBilling(
			@ModelAttribute("CParking") CParking cParking, String parkingUrl,String [] spaceNumber_add
			,String  ground_spaceNumber_add,String [] under_spaceNumber_add) {
		if(spaceNumber_add!=null&&spaceNumber_add.length>0){//添加车位分布
		}else{
			spaceNumber_add=null;
		}
		ResultInfo<CParking> resultInfo = new ResultInfo<CParking>();
		CParking c = new CParking();
		c.setParkingName(cParking.getParkingName());
		List<CParking> park = cParkingService.getCParkingList(new Query(c));
		if (park != null && park.size() > 0) {
			resultInfo.setCode("0");
			resultInfo.setMsg("停车场名称不能重复");
			return resultInfo;
		}
		return cParkingService.addCParking(cParking, getOperator(), parkingUrl,spaceNumber_add,ground_spaceNumber_add,under_spaceNumber_add);
	}

	/**
	 * <p>
	 * Method: toeditParking
	 * </p>
	 * <p>
	 * Description: 停车场编辑页面
	 * </p>
	 * <p>
	 * Author：LiuKang
	 * </p>
	 */
	@RequestMapping("editparking")
	public String toeditParking(ModelMap model, String parkingNo) {
		//获取停车场vo
		ParkingVo parkingVo = cParkingService.getParkListNo(parkingNo);
		parkingVo.setFileUrl(docServiceImpl.getCDoc(
				docServiceImpl.getCDocNo(parkingNo)).getFileUrl());
		model.addAttribute("parkingVo", parkingVo);
		List<SysRegion> plists = sysRegionServiceImpl.getProvices();// 省
		model.addAttribute("plist", plists);
		List<SysRegion> plists2 = sysRegionServiceImpl
				.getCitys(parkingVo.getProvinceId());// 市
		if (parkingVo.getDistrictId() != null) {
			List<SysRegion> plists3 = sysRegionServiceImpl
					.getCountrys(parkingVo.getCityId());// 区
			model.addAttribute("plists3", plists3);
		}

		model.addAttribute("plists2", plists2);
		// 获取标签
		List<CLabel> clabel = cLabelServiceImpl.getCLabelList(new Query());
		model.addAttribute("clabel", clabel);
		// 配套信息
		List<CMatching> cmatching = cMatchingServiceImpl
				.getCMatchingList(new Query());
		model.addAttribute("cmatching", cmatching);
		// 运营城市
		model.addAttribute("operatingCity",
				operatingCityServiceImpl.getCOperatingCityList(new Query()));
		// 计费方案
				model.addAttribute("billingScheme",
						parkBillingService.getCParkBillingList(new Query()));
		//多层车位分布
		CPliesNumber pn=new CPliesNumber();
		pn.setParkingNo(parkingNo);
		List<CPliesNumber> cPliesNumberList = pliesNumberService.getCPliesNumberList(new Query(pn));
		cPliesNumberList = cPliesNumberList == null ? new ArrayList<CPliesNumber>(0) : cPliesNumberList;
		model.addAttribute("pliesNumberList", cPliesNumberList);
		return "mlpark/parking_edit";
	}

	/**
	 * <p>
	 * Method: toEditParkBilling
	 * </p>
	 * <p>
	 * Description: 编辑停车场
	 * </p>
	 * <p>
	 * Author：LiuKang
	 * </p>
	 */
	@RequestMapping("toEditparking")
	@ResponseBody
	public ResultInfo<CParking> toEditParkBilling(
			@ModelAttribute("CParking") CParking cParking,String parkingUrl,String [] spaceNumber
			,String  ground_spaceNumber,String [] under_spaceNumber) {
		if(spaceNumber!=null&&spaceNumber.length>0){//添加车位分布
		}else{
			spaceNumber=null;
		}
		return cParkingService.updateCParking(cParking, getOperator(),parkingUrl,spaceNumber,ground_spaceNumber,under_spaceNumber);
	}

	/**
	 * <p>
	 * Method: delcParking
	 * </p>
	 * <p>
	 * Description: 删除单条停车场记录
	 * </p>
	 * <p>
	 * Author：LiuKang
	 * </p>
	 */
	@RequestMapping("/delParking")
	@ResponseBody
	public ResultInfo<CParking> delcParking(String parkingNo) {
		return cParkingService.delCParking(parkingNo, getOperator());
	}

	/**
	 * 启用停用
	 * 
	 * @param stationNo
	 * @param isAvailable
	 * @return
	 */
	@RequestMapping("/upParking")
	@ResponseBody
	public ResultInfo<CParking> upParking(String parkingNo, String parkingStatus) {
		CParking c = new CParking();
		c.setParkingStatus(Integer.parseInt(parkingStatus));
		c.setParkingNo(parkingNo);
		return cParkingService.updateCParking(c, getOperator(),"",null,null,null);
	}
}
