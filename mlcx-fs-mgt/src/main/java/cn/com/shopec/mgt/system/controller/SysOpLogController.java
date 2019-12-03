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
import cn.com.shopec.core.system.service.SysOpLogService;
import cn.com.shopec.mgt.common.BaseController;
import freemarker.core._RegexBuiltins.replace_reBI;

@Controller
@RequestMapping("/sysOpLog")
public class SysOpLogController extends BaseController {
	@Resource
	public SysOpLogService sysOpLogService;

	/*
	 * 显示系统日志列表页
	 */
	@RequestMapping("/mainPage")
	public String sysOpLog() {
		return "system/sysOpLog";
	}

	
	/*
	 * 分页展示系统日志
	 */
	@RequestMapping("getSysOpLogList")
	@ResponseBody
	public PageFinder<SysOpLog> getSysOpLogList(@ModelAttribute("SysOpLog") SysOpLog sysOpLog,
			Query query) {				
		Query q = new Query(query.getPageNo(),query.getPageSize(),sysOpLog);
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
