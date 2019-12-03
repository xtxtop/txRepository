package cn.com.shopec.mgt.order.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.marketing.model.GoldBeansConsumerDetails;
import cn.com.shopec.core.marketing.service.GoldBeansConsumerDetailsService;
import cn.com.shopec.mgt.common.BaseController;

@Controller
@RequestMapping("consumerDetail")
public class GoldBeansConsumerDetailController extends BaseController {
	@Resource
	private GoldBeansConsumerDetailsService consumerDetailsService;

	/**
	 * 订单冲账列表页面
	 * 
	 * @return
	 */
	@RequestMapping("toConsumerDetailsList")
	public String toConsumerDetailsList(ModelMap modelMap) {
		return "order/gold_beans_consumer_detail_list";
	}

	/**
	 * 订单冲账页面分页
	 */
	@RequestMapping("pageListConsumerDetail")
	@ResponseBody
	public PageFinder<GoldBeansConsumerDetails> pageListConsumerDetail(GoldBeansConsumerDetails consumerDetails, Query query) {
		Query q = new Query(query.getPageNo(), query.getPageSize(), consumerDetails);
		return consumerDetailsService.getGoldBeansConsumerDetailsPagedList(q);
	}
}
