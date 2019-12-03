package cn.com.shopec.mgt.ml.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.ml.model.BillingSchemeDetail;
import cn.com.shopec.core.ml.service.BillingSchemeDetailService;
import cn.com.shopec.core.ml.service.BillingSchemeService;
import cn.com.shopec.mgt.common.BaseController;
/**
 * 
 * @author daiyuanbao
 * @category 明细
 *
 */
@Controller
@RequestMapping("/detail")
public class BillingSchemeDetailController extends BaseController{

	@Resource
	private  BillingSchemeDetailService billingSchemeDetailServiceImpl;
	@Resource
	private BillingSchemeService  billingSchemeServiceImpl;
	/**
	 * 进入明细列表首页
	 * @return
	 */
	@RequestMapping("/getBillingSchemeDetail")
	public String getBillingSchemeDetail(){
		return "ml/billingSchemeDetail_list";
	}
	/**
	 * 获取明细列表
	 * @param BillingSchemeDetail
	 * @param query
	 * @return
	 */
	@RequestMapping("/pageListBillingSchemeDetail")
	@ResponseBody
	public PageFinder<BillingSchemeDetail>  pageListBillingSchemeDetail(@ModelAttribute("BillingSchemeDetail") BillingSchemeDetail BillingSchemeDetail,Query query){
		return billingSchemeDetailServiceImpl.getBillingSchemeDetailPagedList(new Query(query.getPageNo(),query.getPageSize(),BillingSchemeDetail));
	}
	/**
	 * 跳转新增明细页面
	 * @param model
	 * @return
	 */
	@RequestMapping("/toAddBillingSchemeDetail")
	public String toAddBillingSchemeDetail(Model model){
		model.addAttribute("scheme", billingSchemeServiceImpl.getBillingSchemeList(new Query()));
		return "ml/billingSchemeDetail_add";
	}
	
	//新增明细
		@RequestMapping("/addBillingSchemeDetail")
		@ResponseBody
		public ResultInfo<BillingSchemeDetail> addBillingSchemeDetail(@ModelAttribute("BillingSchemeDetail") BillingSchemeDetail billingSchemeDetail){
			return billingSchemeDetailServiceImpl.addBillingSchemeDetail(billingSchemeDetail, getOperator());
		}
	
	/**
	 * 跳转编辑明细页面
	 * @param stationNo
	 * @param model
	 * @return
	 */
	@RequestMapping("/toBillingSchemeDetailEdit")
	public String toBillingSchemeDetailEdit(String detailNo,Model model){
		//获取明细信息
		model.addAttribute("BillingSchemeDetail",billingSchemeDetailServiceImpl.getBillingSchemeDetail(detailNo));
		//获取计费方案
		model.addAttribute("scheme", billingSchemeServiceImpl.getBillingSchemeList(new Query()));
		return "ml/billingSchemeDetail_edit";
	}
	/**
	 * 编辑明细
	 * @param BillingSchemeDetail
	 * @return
	 */
	@RequestMapping("/editBillingSchemeDetail")
	@ResponseBody
	public ResultInfo<BillingSchemeDetail> editBillingSchemeDetail(@ModelAttribute("BillingSchemeDetail") BillingSchemeDetail BillingSchemeDetail){
		return billingSchemeDetailServiceImpl.updateBillingSchemeDetail(BillingSchemeDetail, getOperator());
	}
}
