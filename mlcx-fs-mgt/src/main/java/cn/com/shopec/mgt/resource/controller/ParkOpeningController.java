package cn.com.shopec.mgt.resource.controller;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.shopec.common.utils.ECDateUtils;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.resource.model.ParkOpening;
import cn.com.shopec.core.resource.service.ParkOpeningService;
import cn.com.shopec.mgt.common.BaseController;

@Controller
@RequestMapping("/parkOpening")
public class ParkOpeningController extends BaseController{

	
	@Resource
	private ParkOpeningService parkOpeningService;
	
	/*
	 * 显示开通网店建议列表页
	 */
	@RequestMapping("/mainPage")
	public String mainPage(String today,ModelMap modelMap) {
		if(today != null && today.equals("yes")){
			modelMap.put("today", ECDateUtils.formatDate(new Date(), ECDateUtils.Format_Date));
		}
		return "resource/parkOpening_list";
	}


/*
 * 显示开通网店建议列表分页
 */
@RequestMapping("/pageListPark")
@ResponseBody
public PageFinder<ParkOpening> toParkList(@ModelAttribute("park") ParkOpening parkOpening,Query query) {
	Query q = new Query(query.getPageNo(),query.getPageSize(),parkOpening);
	return parkOpeningService.getParkOpeningPagedList(q);
}



/**
 * 显示开通网店建议详情页
 * @param parkOpeningNo
 * @param modelMap
 * @return
 */
@RequestMapping("/toParkOpeningView")
public String toParkView(String parkOpeningNo,ModelMap modelMap) {
	ParkOpening pa= parkOpeningService.getParkOpening(parkOpeningNo);
	if(pa != null){
		modelMap.put("pa", pa);
	}
	
	return "resource/parkOpening_view";
}



}
