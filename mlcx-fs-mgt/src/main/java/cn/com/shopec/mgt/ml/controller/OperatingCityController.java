package cn.com.shopec.mgt.ml.controller;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;

import org.apache.poi.ss.formula.functions.T;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.ml.model.CLabel;
import cn.com.shopec.core.ml.model.COperatingCity;
import cn.com.shopec.core.ml.model.ChargingStation;
import cn.com.shopec.core.ml.service.COperatingCityService;
import cn.com.shopec.core.ml.vo.AccountBalanceVo;
import cn.com.shopec.core.system.model.SysRegion;
import cn.com.shopec.core.system.service.SysRegionService;
import cn.com.shopec.mgt.common.BaseController;
import cn.com.shopec.mgt.common.GetLatAndLngByBaidu;

/**
 * @author daiyuanbao
 * @category 运营城市
 *
 */
@Controller
@RequestMapping("operatingCity")
public class OperatingCityController extends BaseController{
	
	@Resource
	private COperatingCityService operatingCityService;
	@Resource
	private SysRegionService sysRegionServiceImpl;
	/**
	 * 进入运营城市列表页 
	 * @return
	 */
	@RequestMapping("/getOperatingCity")
	public String getOperatingCity(){
		return "ml/operatingCity_list";
	}
	/**
	 * 获取运营城市列表
	 * @param 
	 * @param query
	 * @return
	 */
	@RequestMapping("/PageFinderOperatingCity")
	@ResponseBody
	public PageFinder<COperatingCity> PageFinderOperatingCity(@ModelAttribute("operatingCity") COperatingCity operatingCity,Query query){
		
		return operatingCityService.getCOperatingCityPagedList(new Query(query.getPageNo(), query.getPageSize(),operatingCity));
	}
	/**
	 * 新增运营城市
	 * @param operatingCity
	 * @return
	 */
	@RequestMapping("/addOperatingCity")
	@ResponseBody
	public ResultInfo<COperatingCity> addOperatingCity(@ModelAttribute("operatingCity") COperatingCity operatingCity){
		ResultInfo<COperatingCity> resultInfo = new ResultInfo<COperatingCity>();
		COperatingCity pc=new COperatingCity();
		pc.setOperatingCityName(operatingCity.getOperatingCityName());
		List<COperatingCity> pList=operatingCityService.getCOperatingCityList(new Query(pc));
		if(pList.size()>0){
			resultInfo.setCode("0");
			resultInfo.setMsg("运营城市已存在,不能进行新增!");
			return resultInfo;
		}
		return operatingCityService.addCOperatingCity(operatingCity, getOperator());
	}
	
	/**
	 * 跳转新增运营城市页面
	 * @param model
	 * @return
	 */
	@RequestMapping("/toAddOperatingCity")
	public String toAddlabel(Model model){
		List<SysRegion> plists = sysRegionServiceImpl.getProvices();// 省
		List<SysRegion> plists2 = sysRegionServiceImpl.getCitys("440000");// 市
		model.addAttribute("plist", plists);
		model.addAttribute("plists2", plists2);
		return "ml/operatingCity_add";
	}
	/**
	 * 跳转编辑运营城市页面
	 * @param stationNo
	 * @param model
	 * @return
	 */
	@RequestMapping("/toOperatingCityEdit")
	public String tolabelEdit(String operatingCityNo,Model model){
		COperatingCity pc= operatingCityService.getCOperatingCity(operatingCityNo);
		model.addAttribute("coperatingCity", pc);
		String city[]=pc.getOperatingCityName().split("-");
		List<SysRegion> plists = sysRegionServiceImpl.getProvices();// 省
		SysRegion s=sysRegionServiceImpl.getRegionIdByRegionName(city[0].trim());//省
		model.addAttribute("province", city[0].trim());
		model.addAttribute("city", city[1].trim());
		List<SysRegion> plists2 = sysRegionServiceImpl.getCitys(s.getRegionId());// 市
		model.addAttribute("plist", plists);
		model.addAttribute("plists2", plists2);
		return "ml/operatingCity_edit";
	}
	/**
	 * 获取经纬度
	 * @param province
	 * @param city
	 * @return
	 */
	@RequestMapping("/getLonAndLat")
	@ResponseBody
	public ResultInfo<Object> getLonAndLat(String province,String city){
		ResultInfo<Object> resultInfo = new ResultInfo<Object>();
		GetLatAndLngByBaidu getLatAndLngByBaidu = new GetLatAndLngByBaidu();
		Object[] o = null;
		try {
			o = getLatAndLngByBaidu.getCoordinate(province+city);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(o!=null&&o.length==0){
			resultInfo.setCode("0");
		}else{
			resultInfo.setCode("1");
			resultInfo.setData(o);
		}
		return resultInfo;
		
	}
	/**
	 * 停用启用
	 * @param operatingCityId
	 * @param enableStatus
	 * @return
	 */
	@RequestMapping("/upOperatingCity")
	@ResponseBody
	public ResultInfo<COperatingCity> upOperatingCity(String operatingCityNo,String enableStatus){
		COperatingCity pc=new COperatingCity();
		pc.setOperatingCityNo(operatingCityNo);
		pc.setEnableStatus(Integer.parseInt(enableStatus));
		return operatingCityService.updateCOperatingCity(pc, getOperator());
	}
	/**
	 * 编辑保存
	 * @param operatingCity
	 * @return
	 */
	@RequestMapping("/editoperatingCity")
	@ResponseBody
	public ResultInfo<COperatingCity> editoperatingCity(@ModelAttribute("operatingCity") COperatingCity operatingCity){
		return operatingCityService.updateCOperatingCity(operatingCity, getOperator());
	}
}
