package cn.com.shopec.mgt.ml.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.ml.model.CDoc;
import cn.com.shopec.core.ml.service.CDocService;
import cn.com.shopec.core.ml.service.CReportingService;
import cn.com.shopec.core.ml.vo.ReportingVo;
import cn.com.shopec.mgt.common.BaseController;

/**
 * @author daiyuanabo
 * @category 申请加盟
 *
 */
@Controller
@RequestMapping("reporting")
public class ReportingController extends BaseController{
	
	@Resource
	private CReportingService cReportingServices;
	@Resource
	private CDocService docService;
	/**
	 * 进入加盟列表
	 */
	@RequestMapping("/getReporting")
	public String getreporting(){
		return  "ml/reporting_list";
	}
	/**
	 * 获取加盟列表
	 * @param reportingVo
	 * @param query
	 * @return
	 */
	@RequestMapping("reportingList")
	@ResponseBody
	public PageFinder<ReportingVo> reportingList(ReportingVo reportingVo,Query query){
		return cReportingServices.getReporting(new Query(query.getPageNo(),query.getPageSize(),reportingVo));
	}
	/**
	 * 进入加盟详情
	 * @param reportingNo
	 * @param model
	 * @return
	 */
	@RequestMapping("/getReportingNo")
	public String getreportingNo(String reportingNo,Model model){
		model.addAttribute("reporting", cReportingServices.getReportingNo(reportingNo));
		CDoc cDoc = new CDoc();	
		cDoc.setBizId(reportingNo);
		model.addAttribute("doc",docService.getCDocList(new Query(cDoc)));
		return "ml/reporting_view";
	}
}
