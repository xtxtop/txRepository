package cn.com.shopec.mgt.resource.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.resource.model.CheckPlan;
import cn.com.shopec.core.resource.model.CheckResult;
import cn.com.shopec.core.resource.service.CheckPlanService;
import cn.com.shopec.core.resource.service.CheckResultService;
import cn.com.shopec.core.system.model.DataDictItem;
import cn.com.shopec.core.system.service.DataDictItemService;
import cn.com.shopec.mgt.common.BaseController;
@Controller
@RequestMapping("/checkResult")
public class CheckResultController extends BaseController{
	@Resource
	private CheckResultService checkResultService;
	@Resource
	private CheckPlanService checkPlanService;
	@Resource
	private DataDictItemService dataDictItemService;
	/*
     * 巡检结果页
     */
    @RequestMapping("/toCheckResult")
    public String toCheckResult(String checkPlanNo,ModelMap modelMap){
    	if(checkPlanNo!=null&&checkPlanNo.length()!=0){
    		modelMap.put("checkPlanNo", checkPlanNo);
    	}
    	return "resource/checkResult_list";
    }
    /*
     * 巡检结果分页
     */
    @RequestMapping("/pageListCheckResult")
    @ResponseBody
    public PageFinder<CheckResult> pageListCheckPlan(@ModelAttribute("checkResult")CheckResult checkResult,Query query){
    	Query q = new Query(query.getPageNo(),query.getPageSize(),checkResult);
    	return checkResultService.getCheckResultPagedList(q);
    }
    /*
     * 巡检结果增加页
     */
    @RequestMapping("/addCheckResult")
    public String addCheckResult(Query q,ModelMap modelMap){
    	List<DataDictItem> items= dataDictItemService.getDataDictItemListByCatCode("CHECK_ITEM");//巡检项
    	modelMap.put("items", items);
    	return "resource/checkResult_add";
    }
    /*
     * 巡检结果 增加/修改
     */
    @RequestMapping("/updateCheckResult")
    @ResponseBody
    public ResultInfo<CheckResult> updateCheckResult(@ModelAttribute("checkResult")CheckResult checkResult){
    	ResultInfo<CheckResult> resultInfo=new ResultInfo<CheckResult>();
    	if(checkResult!=null){//检查巡检计划信息是否存在
    		CheckPlan checkPlan=checkPlanService.getCheckPlan(checkResult.getCheckPlanNo());
    		if(checkPlan==null){
    			resultInfo.setMsg("巡检计划不存在！");
    			return resultInfo;
    		}
    	}
    	if(checkResult.getCheckResultId()!=null&&checkResult.getCheckResultId().length()!=0){
    		resultInfo=checkResultService.updateCheckResult(checkResult, getOperator());
    	}else{
    		resultInfo=checkResultService.addCheckResult(checkResult, getOperator());
    	}
    	return resultInfo;
    }
    /*
     * 巡检结果详情页
     */
    @RequestMapping("/toCheckResultView")
    public String toCheckResultView(String checkResultId,ModelMap modelMap){
    	if(checkResultId!=null&&checkResultId.length()!=0){
    	CheckResult checkResult=checkResultService.getCheckResult(checkResultId);
    	CheckPlan checkPlan=checkPlanService.getCheckPlan(checkResult.getCheckPlanNo());
    	modelMap.put("checkResult", checkResult);
    	modelMap.put("checkPlan", checkPlan);
    	}
    	return "resource/checkResult_view";
    }
    /*
     * 巡检结果编辑页
     */
    @RequestMapping("/toUpdateView")
    public String toUpdateView(String checkResultId,ModelMap modelMap){
    	if(checkResultId!=null&&checkResultId.length()!=0){
    	CheckResult checkResult=checkResultService.getCheckResult(checkResultId);
    	CheckPlan checkPlan=checkPlanService.getCheckPlan(checkResult.getCheckPlanNo());
    	modelMap.put("checkResult", checkResult);
    	modelMap.put("checkPlan", checkPlan);
    	}
    	return "resource/checkResult_edit";
    }
    /*
     * 巡检结果删除
     */
    @RequestMapping("/delCheckResult")
    @ResponseBody
    public ResultInfo<CheckResult> delCheckResult(String checkResultId){
    	ResultInfo<CheckResult> resultInfo=null;
    	if(checkResultId!=null&&checkResultId.length()!=0){
    		resultInfo=checkResultService.delCheckResult(checkResultId, getOperator());
    	}
    	return resultInfo;
    }
}
