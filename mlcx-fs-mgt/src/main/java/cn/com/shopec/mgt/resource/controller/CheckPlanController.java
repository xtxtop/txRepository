package cn.com.shopec.mgt.resource.controller;

import java.util.ArrayList;
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
import cn.com.shopec.core.resource.model.Park;
import cn.com.shopec.core.resource.service.CheckPlanService;
import cn.com.shopec.core.resource.service.ParkService;
import cn.com.shopec.core.scheduling.model.Worker;
import cn.com.shopec.core.scheduling.service.WorkerService;
import cn.com.shopec.core.system.model.DataDictItem;
import cn.com.shopec.core.system.model.SysUser;
import cn.com.shopec.core.system.service.DataDictItemService;
import cn.com.shopec.core.system.service.SysUserService;
import cn.com.shopec.mgt.common.BaseController;
@Controller
@RequestMapping("/checkPlan")
public class CheckPlanController extends BaseController{
    @Resource
    private CheckPlanService checkPlanService;
    @Resource
    private DataDictItemService dataDictItemService;
    @Resource
    private ParkService parkService;
    @Resource
    private SysUserService sysUserService;
    @Resource
	private WorkerService workerService;
    /*
     * 巡检计划页
     */
    @RequestMapping("/toCheckPlan")
    public String toCheckPlan(){
    	return "resource/checkPlan_list";
    }
    /*
     * 巡检计划分页
     */
    @RequestMapping("/pageListCheckPlan")
    @ResponseBody
    public PageFinder<CheckPlan> pageListCheckPlan(@ModelAttribute("checkPlan")CheckPlan checkPlan,Query query){
    	Query q = new Query(query.getPageNo(),query.getPageSize(),checkPlan);
    	return checkPlanService.getCheckPlanPagedList(q);
    }
    /*
     * 增加巡检计划页
     */
    @RequestMapping("/toAddcheckPlan")
    public String toAddcheckPlan(ModelMap modelMap){
    	List<DataDictItem> cities = dataDictItemService.getDataDictItemListByCatCode("CITY");//城市
    	List<DataDictItem> items= dataDictItemService.getDataDictItemListByCatCode("CHECK_ITEM");//巡检项
    	modelMap.put("items", items);
    	modelMap.put("cities", cities);
    	return "resource/checkPlan_add";
    }
    /*
     * 编辑巡检计划页
     */
    @RequestMapping("/toUpdateView")
    public String toUpdateView(String checkPlanNo,ModelMap modelMap){
    	CheckPlan checkPlan=null;
    	if(checkPlanNo!=null&&checkPlanNo.length()!=0){
    		List<DataDictItem> items= dataDictItemService.getDataDictItemListByCatCode("CHECK_ITEM");//巡检项
        	modelMap.put("items", items);
    		List<DataDictItem> cities = dataDictItemService.getDataDictItemListByCatCode("CITY");//城市
    		modelMap.put("cities", cities);
    		checkPlan=checkPlanService.getCheckPlan(checkPlanNo);
    	}
    	modelMap.put("checkPlan", checkPlan);
    	return "resource/checkPlan_edit";
    }
    /*
     * 查看巡检计划页
     */
    @RequestMapping("/toCheckPlanView")
    public String toCheckPlanView(String checkPlanNo,ModelMap modelMap){
    	CheckPlan checkPlan=null;
    	if(checkPlanNo!=null&&checkPlanNo.length()!=0){
    		checkPlan=checkPlanService.getCheckPlan(checkPlanNo);
    	}
    	modelMap.put("checkPlan", checkPlan);
    	return "resource/checkPlan_view";
    }
    /*
     * 增加/更新巡检计划
     */
    @RequestMapping("/updateCheckPlan")
    @ResponseBody
    public ResultInfo<CheckPlan> updateCheckPlan(@ModelAttribute("checkPlan")CheckPlan checkPlan){
    	ResultInfo<CheckPlan> resultInfo=null;
    	if(checkPlan.getCheckPlanNo()!=null&&checkPlan.getCheckPlanNo().length()!=0){
    		resultInfo=checkPlanService.updateCheckPlan(checkPlan, getOperator());
    	}else{
    		resultInfo=checkPlanService.addCheckPlan(checkPlan, getOperator());
    	}
    	return resultInfo;
    }
    /*
     * 查询park对象
     */
    @RequestMapping("/tocheckPark")
    @ResponseBody
    public Park tocheckPark(String parkNo) {
    	Park park=null;
    	if(parkNo!=null&&parkNo.length()!=0){
    		park=parkService.getPark(parkNo);
    		}
    	return park;
    }
    /*
     * 查询巡检人员对象
     */
    @RequestMapping("/tocheckUser")
    @ResponseBody
    public List<String> tocheckUser(String[] ids) {
    	List<String> strings=new ArrayList<String>();
    	 List<Worker> list=workerService.getWorkerByIds(ids);
    	 for(Worker worker:list){
    		 strings.add(worker.getWorkerName());
    	 }
    	return strings;
    }
    /*
     * 查询CheckPlan对象
     */
    @RequestMapping("/toCheckPlanBean")
    @ResponseBody
    public CheckPlan toCheckPlanBean(String checkPlanNo) {
    	CheckPlan checkPlan=null;
    	if(checkPlanNo!=null&&checkPlanNo.length()!=0){
    		checkPlan=checkPlanService.getCheckPlan(checkPlanNo);
    		}
    	return checkPlan;
    }
}
