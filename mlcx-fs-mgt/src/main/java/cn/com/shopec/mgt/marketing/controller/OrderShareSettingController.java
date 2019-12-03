package cn.com.shopec.mgt.marketing.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.common.constants.Constant;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.marketing.model.OrderShareSetting;
import cn.com.shopec.core.marketing.service.OrderShareSettingService;
import cn.com.shopec.core.system.service.SysParamService;
import cn.com.shopec.mgt.common.BaseController;

@Controller
@RequestMapping("orderShareSetting")
public class OrderShareSettingController extends BaseController {
	
	@Resource
	private OrderShareSettingService orderShareSettingService;
	@Resource
	private SysParamService sysParamService;
	
	/**
	 * 订单分享列表页面
	 * 
	 * @return
	 */
	@RequestMapping("toOrderShareSettingList")
	public String toOrderShareSettingList() {
		return "marketing/order_share_setting_list";
	}

	/**
	 * 订单分享列表页面分页
	 * 
	 * @return
	 */
	@RequestMapping("pageListOrderShareSetting")
	@ResponseBody
	public PageFinder<OrderShareSetting> pageListOrderShareSetting(@ModelAttribute("orderShareSetting") OrderShareSetting orderShareSetting, Query query) {
		Query q = new Query(query.getPageNo(), query.getPageSize(), orderShareSetting);
		return orderShareSettingService.getOrderShareSettingPagedList(q);
	}

	/**
	 * 订单分享详情页面
	 * 
	 * @return
	 */
	@RequestMapping("toOrderShareSettingView")
	public String toOrderShareSettingView(String orderShareSettingNo, ModelMap modelMap) {
		OrderShareSetting orderShareSetting = orderShareSettingService.getOrderShareSetting(orderShareSettingNo);
		String days = sysParamService.getParamValueByParamKey("order_share_available_days");
		if (days!=null&&!"".equals(days)) {
			orderShareSetting.setOrderShareContent(orderShareSetting.getOrderShareContent().replace("days", days));
		}
		String orderShareMaxTime = sysParamService.getParamValueByParamKey("order_share_max_time");
		if (orderShareMaxTime!=null&&!"".equals(orderShareMaxTime)) {
			orderShareSetting.setOrderShareContent(orderShareSetting.getOrderShareContent().replace("num", orderShareMaxTime));
		}
		modelMap.put("orderShareSetting", orderShareSetting);
		return "marketing/order_share_setting_view";
	}

	/**
	 * 订单分享增加页面
	 * 
	 * @return
	 */
	@RequestMapping("toAddOrderShareSetting")
	public String toAddOrderShareSetting() {
		return "marketing/order_share_setting_add";
	}

	/**
	 * 订单分享添加
	 * 
	 * @return
	 */
	@RequestMapping("addOrderShareSetting")
	@ResponseBody
	public ResultInfo<OrderShareSetting> addOrderShareSetting(@ModelAttribute("orderShareSetting") OrderShareSetting orderShareSetting) {
		ResultInfo<OrderShareSetting> result = new ResultInfo<>();
		if (orderShareSetting.getOrderShareContent().indexOf("days")==-1||orderShareSetting.getOrderShareContent().indexOf("num")==-1) {
			result.setCode(Constant.FAIL);
			result.setMsg("请按备注说明添加订单分享内容");
			return result;
		}
		orderShareSetting.setIsAvailable(0);
		orderShareSetting.setOrderSharePicUrl(orderShareSetting.getOrderSharePicUrl());
		orderShareSetting.setOrderShareIconUrl(orderShareSetting.getOrderShareIconUrl());
		result = orderShareSettingService.addOrderShareSetting(orderShareSetting, getOperator());
		return result;
	}

	/**
	 * 订单分享编辑页面
	 * 
	 * @return
	 */
	@RequestMapping("toUpdateOrderShareSetting")
	public String toUpdateOrderShareSetting(String orderShareSettingNo, ModelMap model) {
		OrderShareSetting orderShareSetting = orderShareSettingService.getOrderShareSetting(orderShareSettingNo);
		model.addAttribute("orderShareSetting", orderShareSetting);
		return "marketing/order_share_setting_edit";
	}

	/**
	 * 订单分享编辑
	 * 
	 * @return
	 */
	@RequestMapping("updateOrderShareSetting")
	@ResponseBody
	public ResultInfo<OrderShareSetting> updateOrderShareSetting(@ModelAttribute("orderShareSetting") OrderShareSetting orderShareSetting) {
		ResultInfo<OrderShareSetting> result = new ResultInfo<>();
		if (orderShareSetting.getOrderShareContent().indexOf("days")==-1||orderShareSetting.getOrderShareContent().indexOf("num")==-1) {
			result.setCode(Constant.FAIL);
			result.setMsg("请按备注说明编辑订单分享内容");
			return result;
		}
		orderShareSetting.setOrderSharePicUrl(orderShareSetting.getOrderSharePicUrl());
		orderShareSetting.setOrderShareIconUrl(orderShareSetting.getOrderShareIconUrl());
		result = orderShareSettingService.updateOrderShareSetting(orderShareSetting, getOperator());
		return result;
	}

	/**
	 * 订单分享更新启用状态
	 */
	@RequestMapping("changeOrderShareSettingStatus")
	@ResponseBody
	public ResultInfo<OrderShareSetting> updateOrderShareSetting(String orderShareSettingNo, Integer isAvailable) {
		ResultInfo<OrderShareSetting> resultInfo = new ResultInfo<>();
		OrderShareSetting queryOrderShareSetting = new OrderShareSetting();
		queryOrderShareSetting.setIsAvailable(1);
		List<OrderShareSetting> orderShareList = orderShareSettingService.getOrderShareSettingList(new Query(queryOrderShareSetting));
		if (orderShareList.size()>0) {
			if (!orderShareSettingNo.equals(orderShareList.get(0).getOrderShareSettingNo())) {
				resultInfo.setCode(Constant.FAIL);
				resultInfo.setMsg("启动的订单分享设置只能有一个");
				return resultInfo;
			}
		}
		OrderShareSetting orderShareSetting = new OrderShareSetting();
		orderShareSetting.setOrderShareSettingNo(orderShareSettingNo);;
		orderShareSetting.setIsAvailable(isAvailable);
		return orderShareSettingService.updateOrderShareSetting(orderShareSetting, getOperator());
	}

	
}
