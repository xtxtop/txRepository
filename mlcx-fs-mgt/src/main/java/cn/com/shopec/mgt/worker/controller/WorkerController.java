package cn.com.shopec.mgt.worker.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.common.constants.Constant;
import cn.com.shopec.common.utils.ECDateUtils;
import cn.com.shopec.common.utils.ECMd5Utils;
import cn.com.shopec.core.car.service.CarService;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.customer.model.CustomerFeedback;
import cn.com.shopec.core.customer.service.CustomerFeedbackService;
import cn.com.shopec.core.resource.service.ParkService;
import cn.com.shopec.core.scheduling.model.Worker;
import cn.com.shopec.core.scheduling.model.WorkerOrder;
import cn.com.shopec.core.scheduling.service.WorkerOrderService;
import cn.com.shopec.core.scheduling.service.WorkerService;
import cn.com.shopec.core.scheduling.vo.WorkerVo;
import cn.com.shopec.core.system.model.DataDictItem;
import cn.com.shopec.core.system.service.DataDictItemService;
import cn.com.shopec.mgt.common.BaseController;

/**
 * 调度员管理
 * 
 * @author wangming
 */
@Controller
@RequestMapping("/worker")
public class WorkerController extends BaseController {

	@Resource
	private WorkerService workerService;
	@Resource
	private WorkerOrderService workerOrderService;
	@Resource
	private CarService carService;
	@Resource
	private ParkService parkService;
	@Resource
	private DataDictItemService dataDictItemService;
	@Resource
	private CustomerFeedbackService customerFeedbackService;

	/**
	 * 进入调度工单列表页面
	 * 
	 * @return
	 */
	@RequestMapping("toWorkerList")
	public String toWorkerList() {
		return "/worker/worker_list";
	}

	/**
	 * 查询调度员列表
	 * 
	 * @param query
	 * @return
	 */
	@RequestMapping("pageListWorker")
	@ResponseBody
	public PageFinder<Worker> pageListWorker(@ModelAttribute("worker") Worker worker,
			@RequestParam("pageNo") int pageNo, @RequestParam("pageSize") int pageSize) {
		Query q = new Query(pageNo, pageSize, worker);
		return workerService.getWorkerPagedList(q);
	}

	/**
	 * 查询片区
	 * 
	 * @param query
	 * @return
	 */
	@RequestMapping("pageListRegion")
	@ResponseBody
	public PageFinder<DataDictItem> pageListRegion(@ModelAttribute("DataDictItem") DataDictItem dataDictItem,
			String cityId, @RequestParam("pageNo") int pageNo, @RequestParam("pageSize") int pageSize) {
		Query q = new Query(pageNo, pageSize, dataDictItem);
		dataDictItem.setDataDictCatCode("WORKER_DOT");
		dataDictItem.setParentItemId(cityId);
		dataDictItem.setIsAvailable(1);
		dataDictItem.setIsDeleted(0);
		return dataDictItemService.getDataDictItemPagedListWorker(q);
	}

	/**
	 * 调度员详情
	 * 
	 * @param workerNo
	 * @return
	 */
	@RequestMapping("toWorkerView")
	public String toWorkerView(@ModelAttribute("workerNo") String workerNo, Model model) {
		Worker worker = workerService.getWorker(workerNo);
		model.addAttribute("worker", worker);
		return "/worker/worker_view";
	}

	/**
	 * 调度员新增页面
	 * 
	 * @param workerNo
	 * @return
	 */
	@RequestMapping("toWorkerAdd")
	public String toWorkerAdd(@ModelAttribute("workerNo") String workerNo, Model model) {
		List<DataDictItem> cities = dataDictItemService.getDataDictItemListByCatCode("CITY");// 城市
		model.addAttribute("cities", cities);
		return "/worker/worker_add";
	}

	/**
	 * 查询调度员列表
	 * 
	 * @param query
	 * @return
	 */
	@RequestMapping("workerAdd")
	@ResponseBody
	public ResultInfo<Worker> pageListWorker(@ModelAttribute("worker") Worker worker) {

		worker.setPassword(ECMd5Utils.encryptMD5(worker.getPassword()));

		ResultInfo<Worker> res = new ResultInfo<Worker>();
		Worker w1 = new Worker();
		w1.setEmpNo(worker.getEmpNo());
		List<Worker> lists1 = workerService.getWorkerList(new Query(w1));
		Worker w2 = new Worker();
		w2.setMobilePhone(worker.getMobilePhone());
		List<Worker> lists2 = workerService.getWorkerList(new Query(w2));
		if (lists1 != null && lists1.size() > 0) {
			res.setCode(Constant.FAIL);
			res.setMsg("工号重复！");
		} else if (lists2 != null && lists2.size() > 0) {
			res.setCode(Constant.FAIL);
			res.setMsg("手机号重复！");
		} else {
			res = workerService.addWorker(worker, getOperator());
		}
		return res;
	}

	/**
	 * 调度员修改页面
	 * 
	 * @param workerNo
	 * @return
	 */
	@RequestMapping("toWorkerEdit")
	public String toWorkerEdit(@ModelAttribute("workerNo") String workerNo, Model model) {
		List<DataDictItem> dots = dataDictItemService.getDataDictItemListByCatCode("WORKER_DOT");// 片区
		List<DataDictItem> cities = dataDictItemService.getDataDictItemListByCatCode("CITY");// 城市
		model.addAttribute("dots", dots);
		model.addAttribute("cities", cities);
		model.addAttribute("worker", workerService.getWorker(workerNo));
		return "/worker/worker_edit";
	}

	/**
	 * 查询调度员列表
	 * 
	 * @param query
	 * @return
	 */
	@RequestMapping("workerEdit")
	@ResponseBody
	public ResultInfo<Worker> workerEdit(@ModelAttribute("worker") Worker worker) {

		worker.setPassword(ECMd5Utils.encryptMD5(worker.getPassword()));

		ResultInfo<Worker> res = new ResultInfo<Worker>();
		Worker w1 = new Worker();
		w1.setEmpNo(worker.getEmpNo());
		;
		List<Worker> lists1 = workerService.getWorkerList(new Query(w1));
		Worker w2 = new Worker();
		w2.setMobilePhone(worker.getMobilePhone());
		List<Worker> lists2 = workerService.getWorkerList(new Query(w2));
		Integer tagEmp = 0;
		if (lists1 != null && lists1.size() > 0) {
			for (Worker worker1 : lists1) {
				if (!worker1.getWorkerNo().equals(worker.getWorkerNo())) {
					tagEmp = 1;
				}
			}
		}
		Integer tagPhone = 0;
		if (lists2 != null && lists2.size() > 0) {
			for (Worker worker2 : lists2) {
				if (!worker2.getWorkerNo().equals(worker.getWorkerNo())) {
					tagPhone = 1;
				}
			}
		}
		if (tagEmp.equals(1)) {
			res.setCode(Constant.FAIL);
			res.setMsg("工号重复！");
		} else if (tagPhone.equals(1)) {
			res.setCode(Constant.FAIL);
			res.setMsg("手机号重复！");
		} else {
			res = workerService.updateWorker(worker, getOperator());
		}
		return res;
	}

	/**
	 * 进入调度员问题反馈列表
	 */
	@RequestMapping("/customerFeedbackList")
	public String mainPage() {
		return "worker/customerFeedback_list";
	}

	/**
	 * 显示调度员问题反馈列表信息
	 */
	@RequestMapping("/pageListCustomerFeedback")
	@ResponseBody
	public PageFinder<CustomerFeedback> pageListCustomerFeedback(
			@ModelAttribute("customerFeedback") CustomerFeedback customerFeedback, Query query) {
		customerFeedback.setCustomerType(2);// 调度员
		Query q = new Query(query.getPageNo(), query.getPageSize(), customerFeedback);
		return customerFeedbackService.getCustomerFeedbackPagedList(q);
	}

	/*
	 * 进入调度员问题反馈详情页
	 */
	@RequestMapping("/toCustomerFeedbackWorkerView")
	public String toCustomerFeedbackWorkerView(String feedbackNo, ModelMap modelMap) {
		CustomerFeedback customerFeedback = customerFeedbackService.getCustomerFeedback(feedbackNo);
		modelMap.put("customerFeedback", customerFeedback);
		return "worker/customerFeedback_view";
	}

	/*
	 * 进入调度员位置查页面
	 */
	@RequestMapping("/getPositionReporting")
	public String getPositionReporting(ModelMap modelMap) {
		return "worker/getPositionReporting";

	}
	
	@RequestMapping("/getWorkerData")
	@ResponseBody
	public ResultInfo<List<WorkerVo>> getWorkerData(String appIsOnline){
		ResultInfo<List<WorkerVo>> result = new ResultInfo<List<WorkerVo>>();
		List<WorkerVo> listVo = new ArrayList<WorkerVo>();
		//查询在线的调度员数据
		Worker worker = new Worker();
		worker.setAppIsOnline(Integer.parseInt(appIsOnline));
		List<Worker> listWorker = workerService.getWorkerList(new Query(worker));
		if(listWorker != null && listWorker.size() > 0){
			for (Worker wk : listWorker) {
				WorkerVo workerVo = new WorkerVo();
				if(wk.getLocationLongitude() != null && wk.getLocationLatitude() != null){
					workerVo.setWorkerNo(wk.getWorkerNo());
					workerVo.setWorkerName(wk.getWorkerName());
					workerVo.setLongitude(String.valueOf(wk.getLocationLongitude()));
					workerVo.setLatitude(String.valueOf(wk.getLocationLatitude()));
					workerVo.setReportTime(ECDateUtils.formatDate(wk.getLocationUpdateTime(), "yyyy/MM/dd HH:mm:ss"));
					listVo.add(workerVo);
				}
			}
			if(listVo != null && listVo.size() > 0){
				result.setData(listVo);
			}
		}
		return result;
	}
	
	@RequestMapping("pageListWorkerJob")
	@ResponseBody
	public PageFinder<WorkerOrder> pageListWorkerJob(@RequestParam("workerId") String workerId, 
			@RequestParam("pageNo") int pageNo, @RequestParam("pageSize") int pageSize) {
		WorkerOrder workerOrder = new WorkerOrder();
		workerOrder.setWorkerId(workerId);
		Query q = new Query(pageNo, pageSize, workerOrder);
		return workerService.pageListWorkerJob(q);
	}
}