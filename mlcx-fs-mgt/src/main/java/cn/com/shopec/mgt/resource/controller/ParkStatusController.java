package cn.com.shopec.mgt.resource.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.resource.model.ParkStatus;
import cn.com.shopec.core.resource.service.ParkStatusService;
import cn.com.shopec.mgt.common.BaseController;
@Controller
@RequestMapping("parkStatus")
public class ParkStatusController extends BaseController{
    @Resource
    private ParkStatusService parkStatusService;
    /*
     * 显示场站状态列表页
     */
    @RequestMapping("/mainPage")
    public  String mainPage(){
    	return "resource/parkStatus_list";
    }
    /*
     * 显示场站状态列表分页
     */
    @RequestMapping("/pageListParkStatus")
    @ResponseBody
    public PageFinder<ParkStatus> toParkList(@ModelAttribute("parkStatus") ParkStatus parkStatus,Query query) {
    	Query q = new Query(query.getPageNo(),query.getPageSize(),parkStatus);
    	return parkStatusService.getParkStatusPageList(q);
    }
}
