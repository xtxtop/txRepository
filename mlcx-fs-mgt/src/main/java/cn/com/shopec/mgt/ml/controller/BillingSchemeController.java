package cn.com.shopec.mgt.ml.controller;

import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
import cn.com.shopec.core.ml.model.ChargingPile;
import cn.com.shopec.core.ml.service.BillingSchemeService;
import cn.com.shopec.core.ml.service.ChargingPileService;
import cn.com.shopec.core.system.service.SysUserService;
import cn.com.shopec.mgt.common.BaseController;

/**
 * @author 代元宝
 * @category 计费方案
 *
 */
@Controller
@RequestMapping("billingScheme")
public class BillingSchemeController extends BaseController{

	@Resource
	private BillingSchemeService billingSchemeServiceImpl;
	@Resource
	private SysUserService sysUserServiceImpl; 
	@Resource
	private ChargingPileService chargingPileServiceImpl;
	
	/**
	 * 进入计费方案列表页面	
	 * @return
	 */
	@RequestMapping("/getBillingScheme")
	public String getBillingScheme(){
		return "ml/billingScheme_list";
	}
	/**
	 * 获取计费方案列表
	 * @param BillingSchemeVo
	 * @param query
	 * @return
	 */
	@RequestMapping("/pageListBillingScheme")
	@ResponseBody
	public PageFinder<BillingScheme> pageListBillingScheme(@ModelAttribute("BillingScheme") BillingScheme billingScheme,Query query){
		return billingSchemeServiceImpl.getBillingSchemePagedList(new Query(query.getPageNo(),query.getPageSize(),billingScheme));
	}
	/**
	 * 查看详情
	 * @param stationNo
	 * @param model
	 * @return
	 */
	@RequestMapping("/toBillingSchemeView")
	public String toBillingSchemeView(String schemeNo,Model model){
		BillingScheme billingScheme=billingSchemeServiceImpl.getBillingScheme(schemeNo);
		model.addAttribute("billingScheme",billingScheme);
		model.addAttribute("userName", sysUserServiceImpl.getUser(billingScheme.getOperatorId()).getUserName());
		return "ml/billingScheme_view";
	}
	/**
	 * 跳转新增计费方案页面
	 * @param model
	 * @return
	 */
	@RequestMapping("/toAddBillingScheme")
	public String toAddBillingScheme(Model model){
		return "ml/billingScheme_add";
	}
	//新增计费方案
	@RequestMapping("/addBillingScheme")
	@ResponseBody
	public ResultInfo<BillingScheme> addBillingScheme(@ModelAttribute("billingScheme") BillingScheme billingScheme) throws Exception{
		ResultInfo<BillingScheme> resultInfo = new ResultInfo<BillingScheme>();
		BillingScheme b = new BillingScheme();
		b.setSchemeName(billingScheme.getSchemeName());;
		List<BillingScheme> scheme =billingSchemeServiceImpl.getBillingSchemeList(new Query(b));
		if(scheme != null && scheme.size()>0){
			resultInfo.setCode("0");
			resultInfo.setMsg("计费方案名称不能重复");
			return resultInfo;
		}
		JSONObject object=MlPost.post(MlcxUrl.getBillingScheme(billingScheme, MlToken.getToken()), MlcxUrl.data_url);
		if("token码超时错误".equals(object.get("nr"))||"Token码错误".equals(object.get("nr"))){
			if(MlToken.upToken().getCode()=="1"){
				object=MlPost.post(MlcxUrl.getBillingScheme(billingScheme, MlToken.getToken()), MlcxUrl.data_url);
			}else{
				resultInfo.setCode(Constant.FAIL);
				resultInfo.setMsg("下发计费方案信息获取token时,"+MlToken.upToken().getMsg());
				return resultInfo;
			}
		}
		if("success".equals(object.get("jl"))){
			
		}else{
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg("下发计费方案信息失败,"+object.get("nr"));
			return resultInfo;
		}
		return billingSchemeServiceImpl.addBillingScheme(billingScheme, getOperator());
	}
	/**
	 * 跳转编辑计费方案页面
	 * @param stationNo
	 * @param model
	 * @return
	 */
	@RequestMapping("/toBillingSchemeEdit")
	public String toBillingSchemeEdit(String schemeNo,Model model){
		//获取计费方案信息
		BillingScheme billingScheme=billingSchemeServiceImpl.getBillingScheme(schemeNo);
		
		model.addAttribute("billingScheme",billingScheme);
		return "ml/billingScheme_edit";
	}
	/**
	 * 编辑计费方案
	 * @param BillingScheme
	 * @return
	 */
	@RequestMapping("/editBillingScheme")
	@ResponseBody
	public ResultInfo<BillingScheme> editBillingScheme(@ModelAttribute("BillingScheme") BillingScheme BillingScheme){
		return billingSchemeServiceImpl.updateBillingScheme(BillingScheme, getOperator());
	}
	/**
	 * 删除计费方案
	 * @param schemeNo
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/delBillingScheme")
	@ResponseBody
	public ResultInfo<BillingScheme> delBillingScheme(String schemeNo) throws Exception{
		ResultInfo<BillingScheme> result = new ResultInfo<BillingScheme>();
		JSONObject object=MlPost.post(MlcxUrl.delBillingScheme(billingSchemeServiceImpl.getBillingScheme(schemeNo), MlToken.getToken()), MlcxUrl.data_url);
		if("token码超时错误".equals(object.get("nr"))||"Token码错误".equals(object.get("nr"))){
			if(MlToken.upToken().getCode()=="1"){
				object=MlPost.post(MlcxUrl.delBillingScheme(billingSchemeServiceImpl.getBillingScheme(schemeNo), MlToken.getToken()), MlcxUrl.data_url);
			}else{
				result.setCode(Constant.FAIL);
				result.setMsg("删除计费方案信息获取token时,"+MlToken.upToken().getMsg());
				return result;
			}
		}
		if("success".equals(object.get("jl"))){
			return billingSchemeServiceImpl.delBillingScheme(schemeNo, getOperator());
		}else{
			result.setCode(Constant.FAIL);
			result.setMsg("删除计费方案信息失败,"+object.get("nr"));
			return result;
		}
		
	}
	/**
	 *删除和编辑计费方案判断是否在使用
	 * @param schemeNo
	 * @return
	 */
	@RequestMapping("/checkEditBillingScheme")
	@ResponseBody
	public ResultInfo<BillingScheme> checkEditBillingScheme(String schemeNo){
		ResultInfo<BillingScheme> resultInfo = new ResultInfo<BillingScheme>();
		ChargingPile cp=new ChargingPile();
		cp.setBillingScheme(schemeNo);
		List<ChargingPile> cp_list=chargingPileServiceImpl.getChargingPileList(new Query(cp));
		if(cp_list.size()>0){
			resultInfo.setCode("0");
			resultInfo.setMsg("计费方案正在使用不能进行");
		}else{
			resultInfo.setCode("1");
		}
		return resultInfo;
	}
}
