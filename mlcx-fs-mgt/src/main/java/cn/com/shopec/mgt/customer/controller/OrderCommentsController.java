package cn.com.shopec.mgt.customer.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.customer.model.OrderComments;
import cn.com.shopec.core.customer.service.OrderCommentsService;
import cn.com.shopec.core.system.service.DataDictCatService;
import cn.com.shopec.core.system.service.DataDictItemService;
import cn.com.shopec.mgt.common.BaseController;

@Controller
@RequestMapping("/orderComments")
public class OrderCommentsController extends BaseController{
	
	@Resource
	public OrderCommentsService orderCommentsService;
	
	@Resource
	private DataDictCatService dataDictCatService;
	
	@Resource
	private DataDictItemService dataDictItemService;
	
	/*
	 * 进入订单评价列表页面
	 */
	@RequestMapping("/toOrderCommentsList")
	public String mainPage() {
		return "customer/orderComments_list";
	}
	
	/*
	 * 显示订单评价列表信息
	 */
	@RequestMapping("/pageListOrderComments")
	@ResponseBody
	public PageFinder<OrderComments> pageListOrderComments(@ModelAttribute("orderComments") OrderComments orderComments,Query query) {
		Query q = new Query(query.getPageNo(),query.getPageSize(),orderComments);
		return orderCommentsService.getOrderCommentsPagedList(q);
	}
	
	/*
	 * 进入订单评价信息详情页
	 */
	@RequestMapping("/toOrderCommentsView")
	public String toOrderCommentsView(String feedbackNo,ModelMap modelMap) {
		OrderComments orderComments=orderCommentsService.getOrderComments(feedbackNo);
		modelMap.put("orderComments", orderComments);
		return "customer/orderComments_view";
	}
	
}
