package cn.com.shopec.mgt.ml.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
import cn.com.shopec.core.ml.service.ChargingStationService;
import cn.com.shopec.core.ml.service.impl.CDocServiceImpl;
import cn.com.shopec.core.ml.service.impl.CLabelServiceImpl;
import cn.com.shopec.core.ml.service.impl.COperatingCityServiceImpl;
import cn.com.shopec.core.ml.vo.ChargingStationVo;
import cn.com.shopec.core.system.model.SysRegion;
import cn.com.shopec.core.system.service.SysRegionService;
import cn.com.shopec.mgt.common.BaseController;

/**
 * @author 代元宝
 * @category 充电站
 *
 */
@Controller
@RequestMapping("chargingStation")
public class ChargingStationController extends BaseController{

	@Resource
	private ChargingStationService chargingStationServiceImpl;
	@Resource
	private SysRegionService sysRegionServiceImpl;
	@Resource
	private CDocService CDocServiceImpl;
	
	@Resource
	private CLabelService cLabelServiceImpl;
	@Resource 
	private CMatchingService cMatchingServiceImpl;
	@Resource
	private COperatingCityService operatingCityServiceImpl;
	/**
	 * 进入充电站列表页面	
	 * @return
	 */
	@RequestMapping("/getChargingStation")
	public String getChargingStation(Model model){
		model.addAttribute("city", operatingCityServiceImpl.getCOperatingCityList(new Query()));
		return "ml/chargingStation_list";
	}
	/**
	 * 获取充电站列表
	 * @param chargingStationVo
	 * @param query
	 * @return
	 */
	@RequestMapping("/pageListchargingStation")
	@ResponseBody
	public PageFinder<ChargingStationVo> pageListchargingStation(@ModelAttribute("ChargingStationVo") ChargingStationVo chargingStationVo,Query query){
		return chargingStationServiceImpl.getChargingStationVoList(new Query(query.getPageNo(),query.getPageSize(),chargingStationVo));
	}
	/**
	 * 查看详情
	 * @param stationNo
	 * @param model
	 * @return
	 */
	@RequestMapping("/tochargingStationView")
	public String tochargingStationView(String stationNo,Model model){
		ChargingStationVo chargingStationVo=chargingStationServiceImpl.getChargingStationVoList_NO(stationNo);
		chargingStationVo.setStationUrl(CDocServiceImpl.getCDoc(CDocServiceImpl.getCDocNo(chargingStationVo.getStationNo())).getFileUrl());
		model.addAttribute("ChargingStationVo",chargingStationVo);
		//获取标签 
		if(chargingStationVo.getLabel()!=null&&!"".equals(chargingStationVo.getLabel())){
			String str_clabel[]=chargingStationVo.getLabel().toString().split(",");
			List<CLabel>  clabel=cLabelServiceImpl.getCLabelByIds(str_clabel);
			model.addAttribute("clabel", clabel);
		}
		//配套信息
		if(chargingStationVo.getSupportedServices()!=null&&!"".equals(chargingStationVo.getSupportedServices())){
			String str_cmatching[]=chargingStationVo.getSupportedServices().toString().split(",");
			List<CMatching>  cmatching=cMatchingServiceImpl.getCMatchingByIds(str_cmatching);
			model.addAttribute("cmatching", cmatching);
		}
		
		return "ml/chargingStation_view";
	}
	/**
	 * 跳转新增充电站页面
	 * @param model
	 * @return
	 */
	@RequestMapping("/toAddchargingStation")
	public String toAddchargingStation(Model model){
		List<SysRegion> plists = sysRegionServiceImpl.getProvices();// 省
		List<SysRegion> plists2 = sysRegionServiceImpl.getCitys("440000");// 市
		List<SysRegion> plists3 = sysRegionServiceImpl.getCountrys("440300");// 区
		model.addAttribute("plist", plists);
		model.addAttribute("plists3", plists3);
		model.addAttribute("plists2", plists2);
		//获取标签 
		List<CLabel> clabel=cLabelServiceImpl.getCLabelList(new Query());
		model.addAttribute("clabel", clabel);
		//配套信息
		List<CMatching> cmatching=cMatchingServiceImpl.getCMatchingList(new Query());
		model.addAttribute("cmatching", cmatching);
		//运营城市
		model.addAttribute("operatingCity", operatingCityServiceImpl.getCOperatingCityList(new Query()));
		return "ml/chargingStation_add";
	}
	//新增充电站
	@RequestMapping("/addchargingStation")
	@ResponseBody
	public ResultInfo<ChargingStation> addchargingStation(@ModelAttribute("ChargingStation") ChargingStation chargingStation){
		ResultInfo<ChargingStation> resultInfo = new ResultInfo<ChargingStation>();
		ChargingStation c = new ChargingStation();
		c.setStationName(chargingStation.getStationName());
		List<ChargingStation> charging =chargingStationServiceImpl.getChargingStationList(new Query(c));
		if(charging != null && charging.size()>0){
			resultInfo.setCode("0");
			resultInfo.setMsg("充电站名称不能重复");
			return resultInfo;
		}
		chargingStation.setAvailableUpdateTime(new Date());
		return chargingStationServiceImpl.addChargingStation(chargingStation, getOperator());
	}
	/**
	 * 启用停用
	 * @param stationNo
	 * @param isAvailable
	 * @return
	 */
	@RequestMapping("/upChargingStation")
	@ResponseBody
	public ResultInfo<ChargingStation> upChargingStation(String stationNo,String isAvailable){
		ChargingStation c = new ChargingStation();
		c.setIsAvailable(Integer.parseInt(isAvailable));
		c.setStationNo(stationNo);
		return chargingStationServiceImpl.updateChargingStation(c, getOperator());
	}
	/**
	 * 跳转编辑充电站页面
	 * @param stationNo
	 * @param model
	 * @return
	 */
	@RequestMapping("/tochargingStationEdit")
	public String tochargingStationEdit(String stationNo,Model model){
		//获取充电站信息
		ChargingStationVo chargingStationVo=chargingStationServiceImpl.getChargingStationVoList_NO(stationNo);
		//获取图片路径
		chargingStationVo.setStationUrl(CDocServiceImpl.getCDoc(CDocServiceImpl.getCDocNo(chargingStationVo.getStationNo())).getFileUrl());
		List<SysRegion> plists = sysRegionServiceImpl.getProvices();// 省
		model.addAttribute("plist", plists);
		List<SysRegion> plists2 = sysRegionServiceImpl.getCitys(chargingStationVo.getProvinceId());// 市
		if (chargingStationVo.getDistrictId() != null) {
			List<SysRegion> plists3 = sysRegionServiceImpl.getCountrys(chargingStationVo.getCityId());// 区
			model.addAttribute("plists3", plists3);
		}
		
		model.addAttribute("plists2", plists2);
		model.addAttribute("ChargingStationVo",chargingStationVo);
		//获取标签 
		List<CLabel> clabel=cLabelServiceImpl.getCLabelList(new Query());
		model.addAttribute("clabel", clabel);
		//配套信息
		List<CMatching> cmatching=cMatchingServiceImpl.getCMatchingList(new Query());
		model.addAttribute("cmatching", cmatching);
		//运营城市
				model.addAttribute("operatingCity", operatingCityServiceImpl.getCOperatingCityList(new Query()));
		return "ml/chargingStation_edit";
	}
	/**
	 * 编辑充电站
	 * @param chargingStation
	 * @return
	 */
	@RequestMapping("/editChargingStation")
	@ResponseBody
	public ResultInfo<ChargingStation> editChargingStation(@ModelAttribute("ChargingStation") ChargingStation chargingStation){
		return chargingStationServiceImpl.updateChargingStation(chargingStation, getOperator());
	}
	/**
	 * 验证当前运营城市是否存在充电站
	 * @param operatingCityNo
	 * @return
	 */
	@RequestMapping("/getPeratingCity_Employ")
	@ResponseBody
	public Integer getPeratingCity_Employ(String operatingCityNo){
		ChargingStation cs=new ChargingStation();
		cs.setOperatingCityNo(operatingCityNo);
		List<ChargingStation> csList=chargingStationServiceImpl.getChargingStationList(new Query(cs));
		csList = csList == null ? new ArrayList<ChargingStation>(0) : csList;
		return csList.size();
	}
}
