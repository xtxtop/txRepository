package cn.com.shopec.mgt.worker.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.resource.model.Park;
import cn.com.shopec.core.resource.service.ParkService;
import cn.com.shopec.core.scheduling.model.WorkerDot;
import cn.com.shopec.core.scheduling.service.WorkerDotService;
import cn.com.shopec.mgt.common.BaseController;

@Controller
@RequestMapping("/workerDot")
public class WorkerDotController extends BaseController {

	@Resource
	public WorkerDotService workerDotService;
	@Resource
	public ParkService parkService;

	@Override
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(true);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}

	/*
	 * 显示调度员网点页面
	 */
	@RequestMapping("/toWorkerDotList")
	public String mainPage(ModelMap map, String workerId) {
		map.put("workerId", workerId);
		return "worker/workerDot_list";
	}

	/*
	 * 显示调度员网点列表页
	 */
	@RequestMapping("/pageListWorkerDot")
	@ResponseBody
	public PageFinder<WorkerDot> pageListWorkerDot(@ModelAttribute("WorkerDot") WorkerDot WorkerDot, Query query) {
		Query q = new Query(query.getPageNo(), query.getPageSize(), WorkerDot);
		return workerDotService.getWorkerDotPagedList(q);
	}

	/*
	 * 显示场站列表
	 */
	@RequestMapping("/parkList")
	@ResponseBody
	public List<Park> parkList(@ModelAttribute("park") Park park, String workerId, Query query) {

		List<Park> page = parkService.getParkList(new Query(park));
		// 查询调度员在所属网点有没有添加过场站
		for (Park p : page) {
			WorkerDot w = new WorkerDot();
			w.setParkNo(p.getParkNo());
			w.setWorkerId(workerId);
			List<WorkerDot> ls = workerDotService.getWorkerDotList(new Query(w));
			if (ls.size() > 0) {
				// 已经添加过此场站
				p.setIsDot(1);
			} else {
				p.setIsDot(0);
			}

		}

		return page;
	}

	/**
	 * 编辑调度员网点信息
	 */
	@RequestMapping("/editWorkerDot")
	@ResponseBody
	public ResultInfo<WorkerDot> editWorkerDot(String[] parkIds, String workerId) {

		return workerDotService.editWorkerDot(parkIds, workerId, getOperator());
	}

}
