package cn.com.shopec.mgt.ml.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.common.component.MlToken;
import cn.com.shopec.common.constants.Constant;
import cn.com.shopec.common.dsUtils.MlPost;
import cn.com.shopec.common.dsUtils.MlcxUrl;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.ml.model.ChargingGunInfo;
import cn.com.shopec.core.ml.model.ChargingPile;
import cn.com.shopec.core.ml.model.ChargingStation;
import cn.com.shopec.core.ml.model.MonitorDataCocurrent;
import cn.com.shopec.core.ml.model.MonitorDataInterflow;
import cn.com.shopec.core.ml.service.ChargingGunInfoService;
import cn.com.shopec.core.ml.service.ChargingPileService;
import cn.com.shopec.core.ml.service.ChargingStationService;
import cn.com.shopec.core.ml.service.MonitorDataCocurrentService;
import cn.com.shopec.core.ml.service.MonitorDataInterflowService;
import cn.com.shopec.core.ml.vo.ChargingGunInfoVo;
import cn.com.shopec.core.system.service.DataDictItemService;
import cn.com.shopec.mgt.common.BaseController;

/**
 * 充电枪管理Controller
 * 
 * @author Administrator
 */
@Controller
@RequestMapping("/chargerGun")
public class ChargingGunInfoController extends BaseController {

	@Resource
	private ChargingGunInfoService chargingGunInfoService;

	@Resource
	private ChargingPileService chargingPileService;

	@Resource
	private DataDictItemService dataDictItemService;

	@Resource
	private ChargingStationService chargingStationService;
	@Resource
	private MonitorDataInterflowService monitorDataInterflowService;

	@Resource
	private MonitorDataCocurrentService monitorDataCocurrentService;

	/**
	 * 进入充电枪列表页面
	 * 
	 * @return
	 */
	@RequestMapping("/toChargerGunList")
	public String toChargerList(Model model) {
		model.addAttribute("station", chargingStationService.getChargingStationList(new Query()));
		return "/ml/chargerGun/chargerGun_list";
	}

	/**
	 * 查询充电枪列表
	 * 
	 * @param query
	 * @return
	 */
	@RequestMapping("/pageListChargerGun")
	@ResponseBody
	public PageFinder<ChargingGunInfoVo> pageListCharger(@ModelAttribute("ChargingGunInfoVo") ChargingGunInfoVo chargingGunInfoVo,
			Query query) {
		return chargingGunInfoService.getGunList(new Query(query.getPageNo(), query.getPageSize(), chargingGunInfoVo));
	}

	/**
	 * 新增充电枪页面
	 * 
	 * @param modelMap
	 * 
	 */
	@RequestMapping("/toAddChargerGun")
	public String toAddChargerGun(ModelMap modelMap) {
		List<ChargingStation> stationList = chargingStationService
				.getChargingStationList(new Query(1, Integer.MAX_VALUE));
		modelMap.put("stationList", stationList);
		return "ml/chargerGun/chargerGun_add";
	}

	/**
	 * 新增充电枪操作
	 * 
	 * @param chargingGunInfo
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/addChargingGun")
	@ResponseBody
	public ResultInfo<ChargingGunInfo> addChargingGun(
			@ModelAttribute("chargingGunInfo") ChargingGunInfo chargingGunInfo) throws Exception {
		ResultInfo<ChargingGunInfo> result = new ResultInfo<ChargingGunInfo>();
		ChargingGunInfo gun = new ChargingGunInfo();
		gun.setChargingGunCode(chargingGunInfo.getChargingGunCode());
		List<ChargingGunInfo> chargingGunInfoList = chargingGunInfoService.getChargingGunInfoList(new Query(gun));
		if (chargingGunInfoList.size() > 0) {
			result.setCode(Constant.FAIL);
			result.setMsg("充电枪编码不能重复");
			return result;
		}
		JSONObject object=MlPost.post(MlcxUrl.getChargingGun("addq",chargingGunInfo.getChargingPileNo(),chargingGunInfo.getChargingGunCode(),
				MlToken.getToken()), MlcxUrl.data_url);
		
		if("token码超时错误".equals(object.get("nr"))||"Token码错误".equals(object.get("nr"))){
			if(MlToken.upToken().getCode()=="1"){
				object=MlPost.post(MlcxUrl.getChargingGun("addq",chargingGunInfo.getChargingPileNo(),chargingGunInfo.getChargingGunCode(),
						MlToken.getToken()), MlcxUrl.data_url);
			}else{
				result.setCode(Constant.FAIL);
				result.setMsg("下发充电枪信息获取token时,"+MlToken.upToken().getMsg());
				return result;
			}
		}
		if("success".equals(object.get("jl"))){
			
		}else{
			result.setCode(Constant.FAIL);
			result.setMsg("下发充电枪信息失败,"+object.get("nr"));
			return result;
		}
		
		return chargingGunInfoService.addChargingGunInfo(chargingGunInfo, getOperator());
	}

	/**
	 * 根据场站编号获取充电桩
	 * 
	 * @param stationId
	 * @return
	 */
	@RequestMapping("/getStation")
	@ResponseBody
	public ResultInfo<List<ChargingPile>> getStation(String stationId) {
		ResultInfo<List<ChargingPile>> stationList = new ResultInfo<>();
		ChargingPile chargingPile = new ChargingPile();
		chargingPile.setStationNo(stationId);// 充电桩编号
		Query q = new Query(chargingPile);
		List<ChargingPile> chargingPileList = chargingPileService.getChargingPileList(q);
		chargingPileList = chargingPileList == null ? new ArrayList<ChargingPile>(0) : chargingPileList;
		if (chargingPileList.size() > 0) {
			stationList.setCode(Constant.SECCUESS);
			stationList.setData(chargingPileList);
		}else{
			stationList.setCode(Constant.FAIL);
			stationList.setData(chargingPileList);
		}
		return stationList;
	}

	/**
	 * 跳转编辑页面
	 * 
	 * @param chargingGunNo
	 *            充电枪编号
	 * @param model
	 * @return
	 */
	@RequestMapping("/toEditChargingGun")
	public String toEditChargingGun(String chargingGunNo, Model model) {
		// 充电枪基本信息
		ChargingGunInfo chargingGun = chargingGunInfoService.getChargingGunInfo(chargingGunNo);
		model.addAttribute("chargingGunInfo", chargingGun);
		// 获取充电桩编号
		String chargingPileNo = chargingGun.getChargingPileNo();
		ChargingPile chargingPile = chargingPileService.getChargingPile(chargingPileNo);
		model.addAttribute("chargingPile", chargingPile);
		// 获取具体的场站信息
		String stationNo = chargingPile.getStationNo();
		ChargingStation chargingStation = chargingStationService.getChargingStation(stationNo);
		model.addAttribute("chargingStation", chargingStation);
		// 获取场站下拉框信息
		List<ChargingStation> stationList = chargingStationService
				.getChargingStationList(new Query(1, Integer.MAX_VALUE));
		model.addAttribute("stationList", stationList);

		// 根据选取的场站获取充电桩下拉框的信息
		ChargingPile pile = new ChargingPile();
		pile.setStationNo(chargingPile.getStationNo());
		List<ChargingPile> pileList = chargingPileService.getChargingPileList(new Query(pile));
		model.addAttribute("pileList", pileList);

		return "ml/chargerGun/chargerGun_edit";
	}

	/**
	 * 编辑充电枪
	 * 
	 * @param chargingGunInfo
	 * @return
	 */
	@RequestMapping("/editChargingGun")
	@ResponseBody
	public ResultInfo<ChargingGunInfo> editChargingGun(ChargingGunInfo chargingGunInfo) {
		 ResultInfo<ChargingGunInfo> result = new ResultInfo<ChargingGunInfo>();
		 ChargingGunInfo gun = new ChargingGunInfo();
		 gun.setChargingGunCode(chargingGunInfo.getChargingGunCode());
		 List<ChargingGunInfo> chargingGunInfoList = chargingGunInfoService.getChargingGunInfoList(new Query(gun));
		 if(chargingGunInfoList!=null&&chargingGunInfoList.size()>0){
			 for(ChargingGunInfo c:chargingGunInfoList){
				 if(chargingGunInfo.getChargingGunCode().equals(c.getChargingGunCode())&&
						 !chargingGunInfo.getChargingGunNo().equals(c.getChargingGunNo())	 ){
					result.setCode(Constant.FAIL);
					result.setMsg("充电枪编码不能重复");
					return result;
				 }
			 }
		 }
		 
		 
		return chargingGunInfoService.updateChargingGunInfo(chargingGunInfo, getOperator());
	}

	/**
	 * 查看充电枪详情
	 * 
	 * @param chargingGunNo
	 * @param model
	 * @return
	 */
	@RequestMapping("/toViewChargingGun")
	public String toViewChargingGun(String chargingGunNo, Model model) {
		// 充电枪基本信息
		ChargingGunInfoVo chargingGunInfo = chargingGunInfoService.getGunListNo(chargingGunNo);
		model.addAttribute("chargingGunInfo", chargingGunInfo);

		return "ml/chargerGun/chargerGun_view";
	}

	/**
	 * 删除充电枪
	 * 
	 * @param chargingGunNo
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/delChargingGun")
	@ResponseBody
	public ResultInfo<ChargingGunInfo> delChargingGun(String chargingGunNo) throws Exception {
		ChargingGunInfo cg=chargingGunInfoService.getChargingGunInfo(chargingGunNo);
		ResultInfo<ChargingGunInfo> result = new ResultInfo<ChargingGunInfo>();
		JSONObject object=MlPost.post(MlcxUrl.getChargingGun("deleteq",cg.getChargingPileNo(),cg.getChargingGunCode(),
				MlToken.getToken()), MlcxUrl.data_url);
		if("token码超时错误".equals(object.get("nr"))||"Token码错误".equals(object.get("nr"))){
			if(MlToken.upToken().getCode()=="1"){
				object=MlPost.post(MlcxUrl.getChargingGun("deleteq",cg.getChargingPileNo(),cg.getChargingGunCode(),
						MlToken.getToken()), MlcxUrl.data_url);
			}else{
				result.setCode(Constant.FAIL);
				result.setMsg("删除充电枪信息获取token时,"+MlToken.upToken().getMsg());
				return result;
			}
		}
		if("success".equals(object.get("jl"))){
			return chargingGunInfoService.delChargingGunInfo(chargingGunNo, getOperator());
		}else{
			result.setCode(Constant.FAIL);
			result.setMsg("删除充电枪信息失败,"+object.get("nr"));
			return result;
		}
		
		
	}

	/**
	 * 监测数据
	 * 
	 * @param chargingGunNo
	 * @return
	 */
	@RequestMapping("/toMonitoringData")
	public String toMonitoringData(String id,Integer type, Model model) {
		String chargingPileName="";
		String chargingGunInfoCode="";
		if(type==1){//交流
			MonitorDataInterflow monitorDataInterflow = monitorDataInterflowService.getMonitorDataInterflow(id);
			model.addAttribute("interflow",monitorDataInterflow);
			//获取桩名称和枪编号
			chargingPileName=chargingPileService.getChargingPile(monitorDataInterflow.getChargingPileNo()).getChargingPileName();
			chargingGunInfoCode=chargingGunInfoService.getChargingGunInfo(monitorDataInterflow.getChargingGunNo()).getChargingGunCode();
			model.addAttribute("chargingPileName", chargingPileName);
			model.addAttribute("chargingGunInfoCode", chargingGunInfoCode);
			return "ml/chargerGun/interflow_view";	
		}else{//直流
			MonitorDataCocurrent monitorDataCocurrent = monitorDataCocurrentService.getMonitorDataCocurrent(id);
			model.addAttribute("cocurrent", monitorDataCocurrent);
			//获取桩名称和枪编号
			chargingPileName=chargingPileService.getChargingPile(monitorDataCocurrent.getChargingPileNo()).getChargingPileName();
			chargingGunInfoCode=chargingGunInfoService.getChargingGunInfo(monitorDataCocurrent.getChargingGunNo()).getChargingGunCode();
			model.addAttribute("chargingPileName", chargingPileName);
			model.addAttribute("chargingGunInfoCode", chargingGunInfoCode);
			return "ml/chargerGun/cocurrent_view";
		}
		
	}

	/**
	 * 查看是否存在检测数据
	 * 
	 * @param chargingGunNo
	 * @return
	 */
	@RequestMapping("/checkChargerGun")
	@ResponseBody
	public ResultInfo<ChargingGunInfo> checkChargerGun(String chargingGunNo) {
		ResultInfo<ChargingGunInfo> resultInfo = new ResultInfo<ChargingGunInfo>();
		// 获取枪信息
		ChargingGunInfo chargingGunInfo = chargingGunInfoService.getChargingGunInfo(chargingGunNo);
		// 获取桩信息
		ChargingPile chargingPile = chargingPileService.getChargingPile(chargingGunInfo.getChargingPileNo());
		if (chargingPile.getElectricityType() == 1) {// 直流
			MonitorDataCocurrent monitorDataCocurrent = monitorDataCocurrentService
					.getMonitorDataCocurrentTwo(chargingGunNo);
			if (monitorDataCocurrent != null) {
				resultInfo.setCode("1");
			} else {
				resultInfo.setCode("0");
				resultInfo.setMsg("暂无监测数据");
			}
		} else {// 交流
			MonitorDataInterflow monitorDataInterflow = monitorDataInterflowService
					.getMonitorDataInterflowTwo(chargingGunNo);
			if (monitorDataInterflow != null) {
				resultInfo.setCode("1");
			} else {
				resultInfo.setCode("0");
				resultInfo.setMsg("暂无监测数据");
			}
		}
		return resultInfo;
	}
}
