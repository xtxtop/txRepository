package cn.com.shopec.mgt.customer.controller;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.common.constants.Constant;
import cn.com.shopec.common.exception.xls.XlsImportException;
import cn.com.shopec.core.car.model.CarOwner;
import cn.com.shopec.core.car.service.CarOwnerService;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.customer.model.CustomerFeedback;
import cn.com.shopec.core.customer.service.CustomerFeedbackService;
import cn.com.shopec.core.system.service.DataDictCatService;
import cn.com.shopec.core.system.service.DataDictItemService;
import cn.com.shopec.mgt.common.BaseController;
@Controller
@RequestMapping("/customerFeedback")
public class CustomerFeedbackController extends BaseController{
	@Resource
	public CustomerFeedbackService customerFeedbackService;
	@Resource
	private DataDictCatService dataDictCatService;
	@Resource
	private DataDictItemService dataDictItemService;
	/*
	 * 进入客服回复列表页面
	 */
	@RequestMapping("/toCustomerFeedbackList")
	public String mainPage(String noReply,ModelMap modelMap) {
		if(noReply != null && noReply.equals("yes")){
			modelMap.put("replyStatus", 0);
		}
		return "customer/customerFeedback_list";
	}
	/*
	 * 显示客服回复列表信息
	 */
	@RequestMapping("/pageListCustomerFeedback")
	@ResponseBody
	public PageFinder<CustomerFeedback> pageListCustomerFeedback(@ModelAttribute("customerFeedback") CustomerFeedback customerFeedback,Query query) {
		customerFeedback.setCustomerType(1);//会员
		Query q = new Query(query.getPageNo(),query.getPageSize(),customerFeedback);
		return customerFeedbackService.getCustomerFeedbackPagedList(q);
	}
	/*
	 * 进入客服回复信息详情页
	 */
	@RequestMapping("/toCustomerFeedbackView")
	public String toCustomerFeedbackView(String feedbackNo,ModelMap modelMap) {
		CustomerFeedback customerFeedback=customerFeedbackService.getCustomerFeedback(feedbackNo);
		modelMap.put("customerFeedback", customerFeedback);
		return "customer/customerFeedback_view";
	}
	
	
	/*
	 * 进入客服回复页面
	 */
	@RequestMapping("/toCustomerFeedbackEdit")
	public String toCustomerFeedbackEdit(String feedbackNo,ModelMap modelMap) {
		CustomerFeedback customerFeedback=customerFeedbackService.getCustomerFeedback(feedbackNo);
		modelMap.put("customerFeedback", customerFeedback);
		return "customer/customerFeedback_edit";
	}
	/*
	 * 客服回复提交
	 */
	@RequestMapping("/editCustomerFeedback")
	@ResponseBody
	public ResultInfo<CustomerFeedback> editCustomerFeedback(@ModelAttribute("customerFeedback") CustomerFeedback customerFeedback) {
		customerFeedback.setReplyStatus(1);//已回复
		customerFeedback.setReplyTime(new Date());
		return  customerFeedbackService.updateCustomerFeedback(customerFeedback, getOperator());
	}
	
	
	
	
	/*
	 * 进入客服反馈列表页面
	 */
	@RequestMapping("/toMemberFeedbackList")
	public String mainPages() {
		return "customer/memberFeedback_list";
	}
	/*
	 * 显示客服反馈列表信息
	 */
	@RequestMapping("/pageListMemberFeedback")
	@ResponseBody
	public PageFinder<CustomerFeedback> pageListMemberFeedback(@ModelAttribute("customerFeedback") CustomerFeedback customerFeedback,Query query) {
		customerFeedback.setCustomerType(1);//会员
		Query q = new Query(query.getPageNo(),query.getPageSize(),customerFeedback);
		return customerFeedbackService.getCustomerFeedbackPagedList(q);
	}
	/*
	 * 进入客服反馈详情页
	 */
	@RequestMapping("/toMemberFeedbackView")
	public String toMemberFeedbackView(String feedbackNo,ModelMap modelMap) {
		CustomerFeedback customerFeedback=customerFeedbackService.getCustomerFeedback(feedbackNo);
		modelMap.put("customerFeedback", customerFeedback);
		return "customer/memberFeedback_view";
	}
}
