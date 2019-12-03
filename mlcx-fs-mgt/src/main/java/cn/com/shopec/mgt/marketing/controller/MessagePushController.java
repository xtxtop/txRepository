package cn.com.shopec.mgt.marketing.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.common.constants.Constant;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.marketing.model.MessagePush;
import cn.com.shopec.core.marketing.service.MessagePushService;
import cn.com.shopec.core.system.service.SysUserService;
import cn.com.shopec.mgt.common.BaseController;

@Controller
@RequestMapping("messagePush")
public class MessagePushController extends BaseController {
	@Resource
	private MessagePushService messagePushService;
	@Resource
	private SysUserService sysUserService;

	/**
	 * 消息推送列表页面
	 * 
	 * @return
	 */
	@RequestMapping("/toMessagePushList")
	public String toMessagePushList() {
		return "marketing/messagePush_list";
	}

	/**
	 * 消息推送列表页面分页
	 * 
	 * @return
	 */
	@RequestMapping("/pageListMessagePush")
	@ResponseBody
	public PageFinder<MessagePush> pageListMessagePush(@ModelAttribute("messagePush") MessagePush messagePush, Query query) {
		Query q = new Query(query.getPageNo(), query.getPageSize(), messagePush);
		return messagePushService.getMessagePushPagedList(q);
	}

	/**
	 * 消息推送详情页面
	 * 
	 * @return
	 */
	@RequestMapping("/toMessagePushView")
	public String toMessagePushView(String id, ModelMap modelMap) {
		MessagePush messagePush = messagePushService.getMessagePush(id);
		modelMap.put("messagePush", messagePush);
		return "marketing/messagePush_view";
	}

	/**
	 * 消息推送增加页面
	 * 
	 * @return
	 */
	@RequestMapping("/toAddMessagePush")
	public String toAddMessagePush() {
		return "marketing/messagePush_add";
	}

	/**
	 * 消息推送添加
	 * 
	 * @return
	 */
	@RequestMapping("/addMessagePush")
	@ResponseBody
	public ResultInfo<MessagePush> addMessagePush(@ModelAttribute("messagePush") MessagePush messagePush) {
		ResultInfo<MessagePush> result = messagePushService.addMessagePush(messagePush, getOperator());
		return result;
	}

	/**
	 * 消息推送编辑页面
	 * 
	 * @return
	 */
	@RequestMapping("/toUpdateMessagePush")
	public String toUpdateMessagePush(String id, ModelMap model) {
		MessagePush messagePush = messagePushService.getMessagePush(id);
		model.addAttribute("messagePush", messagePush);
		return "marketing/messagePush_edit";
	}

	/**
	 * 消息推送编辑
	 * 
	 * @return
	 */
	@RequestMapping("/updateMessagePush")
	@ResponseBody
	public ResultInfo<MessagePush> updateMessagePush(@ModelAttribute("messagePush") MessagePush messagePush) {
		ResultInfo<MessagePush> result = messagePushService.updateMessagePush(messagePush, getOperator());
		return result;
	}

	/**
	 * 推送消息
	 * 
	 * @return
	 */
	@RequestMapping("/pushMessage")
	@ResponseBody
	public ResultInfo<MessagePush> ushMessage(@ModelAttribute("messagePush") MessagePush messagePush) {
		ResultInfo<MessagePush> result = new ResultInfo<MessagePush>();
		if(messagePush.getId() != null && !messagePush.getId().equals("")){
			messagePush = messagePushService.getMessagePush(messagePush.getId());
			result = messagePushService.pushMessage(messagePush, getOperator());
		}else{
			result.setCode(Constant.FAIL);
			result.setMsg("id不存在");
		}
		return result;
	}
}
