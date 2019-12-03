package cn.com.shopec.mgt.ml.controller;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.common.component.MlToken;
import cn.com.shopec.common.constants.Constant;
import cn.com.shopec.common.dsUtils.MlPost;
import cn.com.shopec.common.dsUtils.MlcxUrl;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.ml.model.BillingScheme;
import cn.com.shopec.core.ml.model.ChargingGunInfo;
import cn.com.shopec.core.ml.model.ChargingPile;
import cn.com.shopec.core.ml.model.ChargingStation;
import cn.com.shopec.core.ml.model.MonitorDataCocurrent;
import cn.com.shopec.core.ml.model.MonitorDataInterflow;
import cn.com.shopec.core.ml.service.BillingSchemeService;
import cn.com.shopec.core.ml.service.ChargingPileService;
import cn.com.shopec.core.ml.service.ChargingStationService;
import cn.com.shopec.core.ml.service.MonitorDataCocurrentService;
import cn.com.shopec.core.ml.service.MonitorDataInterflowService;
import cn.com.shopec.mgt.common.BaseController;

/**
 * @category 充电桩
 */
@Controller
@RequestMapping("chargingPile")
public class ChargingPileController extends BaseController {

	@Resource
	private ChargingPileService chargingPileService;
	@Resource
	private ChargingStationService chargingStationService;
	@Resource
	private BillingSchemeService billingSchemeService;
	@Resource
	private MonitorDataInterflowService monitorDataInterflowService;
	
	@Resource
	private MonitorDataCocurrentService monitorDataCocurrentService;

	@RequestMapping("/getChargingPileList")
	public String getChargingStation(ModelMap model) {
		List<ChargingStation> csList = chargingStationService.getChargingStationList(new Query());
		model.put("csList", csList);
		return "ml/chargingPile_list";
	}

	// 列表展示
	@RequestMapping("/pageListchargingPile")
	@ResponseBody
	public PageFinder<ChargingPile> pageListchargingPile(@ModelAttribute("ChargingPile") ChargingPile chargingPile,
			Query query) throws ParseException {
		Query q = new Query(query.getPageNo(), query.getPageSize(), chargingPile);
		return chargingPileService.getChargingPilePagedList(q);
	}

	// 新增页面
	@RequestMapping("/toAddchargingPile")
	public String toAddchargingPile(ModelMap model) {
		List<ChargingStation> csList = chargingStationService.getChargingStationList(new Query());
		model.put("csList", csList);
		List<BillingScheme> bsList = billingSchemeService.getBillingSchemeList(new Query());
		model.put("bsList", bsList);
		return "ml/chargingPile_add";
	}

	// 新增充电桩操作
	@RequestMapping("/doaddchargingPile")
	@ResponseBody
	public ResultInfo<ChargingPile> doaddchargingPile(@ModelAttribute("ChargingPile") ChargingPile chargingPile) throws Exception {
		ResultInfo<ChargingPile> result = new ResultInfo<ChargingPile>();
		ChargingPile cp = new ChargingPile();
		cp.setChargingPileNo(chargingPile.getChargingPileNo());
		List<ChargingPile> cpList = chargingPileService.getChargingPileList(new Query(cp));
		if (cpList.size() > 0) {
			result.setCode(Constant.FAIL);
			result.setMsg("充电桩名称不能重复");
			return result;
		}
		//同步新增猛龙充电桩
		String Electricity="";
		if(chargingPile.getElectricityType()==0){
			Electricity="交流充电桩";
		}else{
			Electricity="直流充电桩";
		}
		//获取计费方案
		BillingScheme billingScheme = billingSchemeService.getBillingScheme(chargingPile.getBillingScheme());
		JSONObject object=MlPost.post(MlcxUrl.getChargingPile("addcdz",chargingPile.getChargingPileNo(),billingScheme.getSchemeVersions(),
					Electricity,MlToken.getToken()), MlcxUrl.data_url);
		if("token码超时错误".equals(object.get("nr"))||"Token码错误".equals(object.get("nr"))){
			if(MlToken.upToken().getCode()=="1"){
				object=MlPost.post(MlcxUrl.getChargingPile("addcdz",chargingPile.getChargingPileNo(),billingScheme.getSchemeVersions(),
						Electricity,MlToken.getToken()), MlcxUrl.data_url);
			}else{
				result.setCode(Constant.FAIL);
				result.setMsg("下发充电桩信息获取token时,"+MlToken.upToken().getMsg());
				return result;
			}
		}
		if("success".equals(object.get("jl"))){
			
		}else{
			result.setCode(Constant.FAIL);
			result.setMsg("下发充电站信息失败,"+object.get("nr"));
			return result;
		}
		return chargingPileService.addChargingPile(chargingPile, getOperator());
	}

	// 编辑页面
	@RequestMapping("/toEditchargingPile")
	public String toEditchargingPile(ModelMap model, String chargingPileNo) {
		ChargingPile cp = chargingPileService.getChargingPile(chargingPileNo);
		model.put("cp", cp);
		List<ChargingStation> csList = chargingStationService.getChargingStationList(new Query());
		model.put("csList", csList);
		List<BillingScheme> bsList = billingSchemeService.getBillingSchemeList(new Query());
		model.put("bsList", bsList);
		return "ml/chargingPile_edit";
	}

	// 编辑充电桩操作
	@RequestMapping("/doeditchargingPile")
	@ResponseBody
	public ResultInfo<ChargingPile> doeditchargingPile(@ModelAttribute("ChargingPile") ChargingPile chargingPile) throws Exception {
		ResultInfo<ChargingPile> result = new ResultInfo<ChargingPile>();
		//同步修改猛龙充电桩
		String Electricity="";
		if(chargingPile.getElectricityType()==0){
			Electricity="交流充电桩";
		}else{
			Electricity="直流充电桩";
		}
		//获取计费方案
		BillingScheme billingScheme = billingSchemeService.getBillingScheme(chargingPile.getBillingScheme());
		JSONObject object=MlPost.post(MlcxUrl.getChargingPile("updatecdz",chargingPile.getChargingPileNo(),billingScheme.getSchemeVersions(),
						Electricity,MlToken.getToken()), MlcxUrl.data_url);
		if("token码超时错误".equals(object.get("nr"))||"Token码错误".equals(object.get("nr"))){
			if(MlToken.upToken().getCode()=="1"){
				object=MlPost.post(MlcxUrl.getChargingPile("updatecdz",chargingPile.getChargingPileNo(),billingScheme.getSchemeVersions(),
						Electricity,MlToken.getToken()), MlcxUrl.data_url);
			}else{
				result.setCode(Constant.FAIL);
				result.setMsg("下编辑充电桩信息获取token时,"+MlToken.upToken().getMsg());
				return result;
			}
		}
		if("success".equals(object.get("jl"))){
			
		}else{
			result.setCode(Constant.FAIL);
			result.setMsg("编辑充电站信息失败,"+object.get("nr"));
			return result;
		}
		return chargingPileService.updateChargingPile(chargingPile, getOperator());
	}
	
	/**
	 * 监测数据
	 * @param chargingGunNo
	 * @return
	 */
	@RequestMapping("/toMonitoringData")
	public String toMonitoringData(String chargingPileNo,Model model){
		//获取桩信息
		ChargingPile chargingPile=chargingPileService.getChargingPile(chargingPileNo);
		model.addAttribute("chargingPileNo", chargingPileNo);
		if(chargingPile.getElectricityType()==1){//直流
			return "ml/chargerGun/cocurrent_list";
		}else{//交流
			return "ml/chargerGun/interflow_list";
		}
	}
	/**
	 * 删除充电桩
	 * @param chargingPileNo
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/delChargingPile")
	@ResponseBody
	public ResultInfo<ChargingPile> delChargingPile(String chargingPileNo) throws Exception{
		ResultInfo<ChargingPile> result = new ResultInfo<ChargingPile>();
		JSONObject object=MlPost.post(MlcxUrl.delChargingPile("deletecdz",chargingPileNo,
				MlToken.getToken()), MlcxUrl.data_url);
		if("token码超时错误".equals(object.get("nr"))||"Token码错误".equals(object.get("nr"))){
			if(MlToken.upToken().getCode()=="1"){
				object=MlPost.post(MlcxUrl.delChargingPile("updatecdz",chargingPileNo,
						MlToken.getToken()), MlcxUrl.data_url);
			}else{
				result.setCode(Constant.FAIL);
				result.setMsg("删除充电桩信息获取token时,"+MlToken.upToken().getMsg());
				return result;
			}
		}
		if("success".equals(object.get("jl"))){
			return chargingPileService.delChargingPile(chargingPileNo, getOperator());
		}else{
			result.setCode(Constant.FAIL);
			result.setMsg("删除充电站信息失败,"+object.get("nr"));
			return result;
		}
	}
}
