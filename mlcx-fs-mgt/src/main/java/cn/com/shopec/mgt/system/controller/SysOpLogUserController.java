package cn.com.shopec.mgt.system.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.system.model.SysOpLog;
import cn.com.shopec.core.system.model.SysUser;
import cn.com.shopec.core.system.service.SysOpLogService;
import cn.com.shopec.mgt.common.BaseController;

/**
 * 登录用户操作日志查询
 * 
 * @author lujian
 *
 */
@Controller
@RequestMapping("/sysOpLogUser")
public class SysOpLogUserController extends BaseController {
	@Resource
	public SysOpLogService sysOpLogService;

	/*
	 * 显示操作日志列表页
	 */
	@RequestMapping("/mainOpLogUser")
	public String sysOpLogUser() {
		return "system/sysOpLogUser";
	}

	/*
	 * 分页展示操作日志
	 */
	@RequestMapping("getSysOpLogUserList")
	@ResponseBody
	public PageFinder<SysOpLog> getSysOpLogUserList(@ModelAttribute("SysOpLog") SysOpLog sysOpLog, Query query) {
		SysUser sysUser = getLoginSysUser();
		sysOpLog.setOperatorUserName(sysUser.getUserName());
		Query q = new Query(query.getPageNo(), query.getPageSize(), sysOpLog);
		return sysOpLogService.pageList(q);

	}

	/*
	 * 详情
	 */
	@RequestMapping("detail")
	@ResponseBody
	public SysOpLog detail(@RequestParam("logId") String logId) {
		return sysOpLogService.detail(logId);
	}
	
}
