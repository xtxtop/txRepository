package cn.com.shopec.mgt.workerorder.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;

import cn.com.shopec.common.Operator;
import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.common.constants.Constant;
import cn.com.shopec.common.exception.xls.XlsImportException;
import cn.com.shopec.core.car.common.CarConstant;
import cn.com.shopec.core.car.model.Car;
import cn.com.shopec.core.car.model.CarRecord;
import cn.com.shopec.core.car.model.CarStatus;
import cn.com.shopec.core.car.service.CarRecordService;
import cn.com.shopec.core.car.service.CarService;
import cn.com.shopec.core.car.service.CarStatusService;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.device.model.Device;
import cn.com.shopec.core.device.service.DeviceService;
import cn.com.shopec.core.order.model.OrderFinish;
import cn.com.shopec.core.resource.model.Park;
import cn.com.shopec.core.resource.service.ParkService;
import cn.com.shopec.core.scheduling.model.Worker;
import cn.com.shopec.core.scheduling.model.WorkerOrder;
import cn.com.shopec.core.scheduling.service.WorkerOrderService;
import cn.com.shopec.core.scheduling.service.WorkerService;
import cn.com.shopec.mgt.common.BaseController;

/**
 * 调度工单管理
 * 
 * @author wangming
 */
@Controller
@RequestMapping("/workerOrder")
public class WorkerOrderController extends BaseController {

	@Resource
	private WorkerOrderService workerOrderService;
	@Resource
	private CarService carService;
	@Resource
	private ParkService parkService;
	@Resource
	private WorkerService workerService;
	@Resource
	private CarStatusService carStatusService;
	@Resource
	private CarRecordService carRecordService;
	@Resource
	private DeviceService deviceService;

	/**
	 * 进入调度工单列表页面
	 * 
	 * @return
	 */
	@RequestMapping("toWorkerOrderList")
	public String toWorkerOrderList(String workerNo, String workerOrderNo, String workOrderStatus, Model model) {
		model.addAttribute("workerId", workerNo);
		model.addAttribute("workerOrderNo", workerOrderNo);
		model.addAttribute("workOrderStatus", workOrderStatus);
		return "/workerOrder/workerOrder_list";
	}

	/**
	 * 首页 待办事项 点击 进入调度工单列表页面
	 * 
	 * @return
	 */
	@RequestMapping("toWorkerOrderListTodo")
	public String toWorkerOrderListTodo(String workerNo, Model model) {
		Integer censorStatus = 0;
		model.addAttribute("censorStatus", censorStatus);
		model.addAttribute("workerId", workerNo);
		return "/workerOrder/workerOrder_list";
	}

	// 导出模板
	@RequestMapping("downloadExcelFile")
	public void downloadExcelFile(HttpServletRequest request, HttpServletResponse response, String filepath,
			String newFileName) throws Exception {
		response.setContentType("multipart/form-data");
		// response.setContentType(request.getSession().getServletContext().getMimeType(newFileName));
		response.setCharacterEncoding("UTF-8");
		// 设置Content-Disposition
		response.setHeader("Content-Disposition", "attachment;filename=" + newFileName);
		// 读取目标文件，通过response将目标文件写到客户端
		String path = request.getRealPath("/") + "download" + File.separator + "workerOrder.xls";
		File ff = new File(path);

		InputStream in = new FileInputStream(ff);

		OutputStream out = response.getOutputStream();
		// 写文件
		int b;
		while ((b = in.read()) != -1) {
			out.write(b);
		}
		in.close();
		out.close();
	}
	
	
	
	/**
	 * 查询调度工单列表
	 * 
	 * @param query
	 * @return
	 */
	@RequestMapping("pageListWorkerOrder")
	@ResponseBody
	public PageFinder<WorkerOrder> pageListWorkerOrder(@ModelAttribute("workerOrder") WorkerOrder workerOrder,
			@RequestParam("pageNo") int pageNo, @RequestParam("pageSize") int pageSize) {
		Query q = new Query(pageNo, pageSize, workerOrder);
		return workerOrderService.getWorkerOrderPagedList(q);
	}

	/**
	 * 调度工单详情
	 * 
	 * @param workerOrderNo
	 * @return
	 */
	@RequestMapping("toWorkerOrderView")
	public String toWorkerOrderView(@ModelAttribute("workerOrderNo") String workerOrderNo, Model model) {
		WorkerOrder workerOrder = workerOrderService.getWorkerOrder(workerOrderNo);
		String s = "";
		// 设置任务类型 0-调度 1-救援 2-清洁 3-加油 4-维保
		if (workerOrder.getType().indexOf("0") != -1) {
			s += "调度,";
		}
		if (workerOrder.getType().indexOf("1") != -1) {
			s += "救援,";
		}
		if (workerOrder.getType().indexOf("2") != -1) {
			s += "清洁,";
		}
		if (workerOrder.getType().indexOf("3") != -1) {
			s += "加油,";
		}
		if (workerOrder.getType().indexOf("4") != -1) {
			s += "维保,";
		}
		if( s!= null  && !"".equals(s) ){
			String s2 = s.substring(0, s.length() - 1);
			workerOrder.setType(s2);
		}
		model.addAttribute("workerOrder", workerOrder);
		return "/workerOrder/workerOrder_view";
	}

	/**
	 * 调度工单编辑页面
	 * 
	 * @param workerOrderNo
	 * @return
	 */
	@RequestMapping("toUpdateWorkerOrder")
	public String toUpdateWorkerOrder(@ModelAttribute("workerOrderNo") String workerOrderNo, Model model) {

		WorkerOrder workerOrder = workerOrderService.getWorkerOrder(workerOrderNo);
		// 得到下拉菜单 - 起点终点（场站），调度员，车牌号
		// 车辆表
		Park p =new Park();
		p.setIsAvailable(1);
		p.setIsPublic(1);
		p.setIsView(1);
		Query qp=new Query(p);
		
		// 场站表
		List<Park> parkList = parkService.getParkList(qp);
		// 调度员
		Query q = new Query();
		List<Worker> workerList = workerService.getWorkerList(q);
		CarStatus c = new CarStatus();
		if (workerOrder.getStartParkNo() != null && !"".equals(workerOrder.getStartParkNo())) {
			c.setLocationParkNo(workerOrder.getStartParkNo());
			// List<CarStatus> carStatusLists =
			// carStatusService.getCarSpaceShortage(new Query(c));
			List<CarStatus> carStatusLists = carStatusService.getCarStatusList(new Query(c));
			if (carStatusLists != null && carStatusLists.size() > 0) {
				model.addAttribute("carList", carStatusLists);
			}
		}
		model.addAttribute("parkList", parkList);
		model.addAttribute("workerList", workerList);
		model.addAttribute("workerOrder", workerOrder);

		return "/workerOrder/workerOrder_edit";
	}

	/**
	 * 调度工单添加页面
	 * 
	 * @param workerOrderNo
	 * @return
	 */
	@RequestMapping("toAddWorkerOrder")
	public String toAddWorkerOrder(Model model) {
		// 得到下拉菜单 - 起点终点（场站），调度员，车牌号
		// 车辆表
		Park p =new Park();
		p.setIsAvailable(1);
		p.setIsPublic(1);
		p.setIsView(1);
		Query qp=new Query(p);
		// 场站表
		List<Park> parkList = parkService.getParkList(qp);
		// 调度员
		Query q = new Query();
		List<Worker> workerList = workerService.getWorkerList(q);
		model.addAttribute("parkList", parkList);
		model.addAttribute("workerList", workerList);

		return "/workerOrder/workerOrder_add";
	}

	/**
	 * 通过场站编号查询当前场站的车辆
	 * 
	 * @param parkNo
	 * @return
	 */
	@RequestMapping("getCarByParkNo")
	@ResponseBody
	public ResultInfo<List<CarStatus>> getCarByParkNo(String parkNo) {
		ResultInfo<List<CarStatus>> res = new ResultInfo<List<CarStatus>>();
		CarStatus c = new CarStatus();
		if (parkNo != null && !"".equals(parkNo)) {
			c.setLocationParkNo(parkNo);
			c.setUserageStatus(0);
		} else {
			res.setCode(Constant.FAIL);
			res.setMsg("场站编号为空！");
		}
		List<CarStatus> carList = carStatusService.getCarStatusList(new Query(c));
		// List<CarStatus> carStatusLists =
		// carStatusService.getCarSpaceShortage(new Query(c));
		if (carList != null && carList.size() > 0) {
			res.setCode(Constant.SECCUESS);
			res.setData(carList);
		} else {
			res.setCode(Constant.FAIL);
			res.setMsg("当前场站没有车辆！");
		}
		return res;
	}

	/**
	 * 调度工单修改
	 * 
	 * @param workerOrder
	 * @return
	 */
	@RequestMapping("updateWorkerOrder")
	@ResponseBody
	public ResultInfo<WorkerOrder> updateWorkerOrder(@ModelAttribute("workerOrder") WorkerOrder workerOrder) {
		WorkerOrder workerOd = workerOrderService.getWorkerOrder(workerOrder.getWorkerOrderNo());
		Operator operator = getOperator();
		ResultInfo<WorkerOrder> resultInfo = workerOrderService.updateWorkerOrder(workerOrder, operator);
		if ("1".equals(resultInfo.getCode())&&workerOrder.getCarNo()!=null) {
			if (workerOd!=null) {
				//修改调度车原有车辆为空闲
				CarStatus carStatusOld = new CarStatus();
				carStatusOld.setCarNo(workerOd.getCarNo());
				carStatusOld.setUserageStatus(0);
				carStatusService.updateCarStatus(carStatusOld, getOperator());
				//修改新选择的车辆为预占
				CarStatus carStatusNew = new CarStatus();
				carStatusNew.setCarNo(workerOrder.getCarNo());
				carStatusNew.setUserageStatus(1);
				carStatusService.updateCarStatus(carStatusNew, getOperator());
			}
		}
		return resultInfo;
	}

	/**
	 * 调度工单添加
	 * 
	 * @param workerOrder
	 * @return
	 */
	@RequestMapping("addWorkerOrder")
	@ResponseBody
	public ResultInfo<WorkerOrder> addWorkerOrder(@ModelAttribute("workerOrder") WorkerOrder workerOrder) {
		Operator operator = getOperator();
		workerOrder.setWorkOrderStatus(0);// 未认证
		workerOrder.setCencorStatus(0);// 未审核
		if (workerOrder.getTerminalParkNo() != null && !workerOrder.getTerminalParkNo().equals("")) {
			Park p = parkService.getPark(workerOrder.getTerminalParkNo());
			if (p != null) {
				workerOrder.setTerminalParkName(p.getParkName());
			}
		}
		Car car = carService.getCar(workerOrder.getCarNo());
		if (car != null) {
			workerOrder.setCarPlateNo(car.getCarPlateNo());
		}
		ResultInfo<WorkerOrder> resultInfo = workerOrderService.addWorkerOrderAndJudgeCarStatus(workerOrder, operator);
		return resultInfo;
	}

	/*
	 * 查询审核对象
	 */
	@RequestMapping("/toWorkerOrder")
	@ResponseBody
	public WorkerOrder toWorkerOrder(String workerOrderNo) {
		WorkerOrder workerOrder = new WorkerOrder();
		return workerOrder;
	}

	/**
	 * 下发调度单
	 * 
	 * @param workerOrderNo
	 * @return
	 */
	@RequestMapping("/toIssuedWorkerOrder")
	@ResponseBody
	public ResultInfo<WorkerOrder> toIssuedWorkerOrder(@ModelAttribute("workerOrder") WorkerOrder workerOrder) {
		ResultInfo<WorkerOrder> resultInfo = new ResultInfo<WorkerOrder>();
		Operator operator = getOperator();
		workerOrder.setSendTime(new Date());
		workerOrder.setWorkOrderStatus(1);
		WorkerOrder workerOrder1 = workerOrderService.getWorkerOrder(workerOrder.getWorkerOrderNo());
		CarStatus carStatus = carStatusService.getCarStatus(workerOrder1.getCarNo());
		if (carStatus != null && carStatus.getUserageStatus().equals(1)) {// 旧设备按原业务处理
			resultInfo = workerOrderService.updateWorkerOrder(workerOrder, operator);
			workerOrder = workerOrderService.getWorkerOrder(workerOrder.getWorkerOrderNo());
			Car car = carService.getCar(workerOrder.getCarNo());

			// 在用车记录表中添加一条用车记录
			CarRecord cr = new CarRecord();
			if (car != null) {
				cr.setCarBrand(car.getCarBrandName());
				cr.setCarBrandId(car.getCarBrandId());
				cr.setCarModel(car.getCarModelName());
				cr.setCarModelId(car.getCarModelId());
				cr.setCarPlateNo(car.getCarPlateNo());
				cr.setCity(car.getCityName());
				cr.setCityId(car.getCityId());
			}
			if (workerOrder != null) {
				cr.setDocumentNo(workerOrder.getWorkerOrderNo());// 单据号（调度单号）
				cr.setDriverId(workerOrder.getWorkerId());// 用车人id
				cr.setDriverName(workerOrder.getWorkerName());
				cr.setStartParkNo(workerOrder.getStartParkNo());
				if (workerOrder.getStartParkNo() != null && !workerOrder.getStartParkNo().equals("")) {
					Park park = parkService.getPark(workerOrder.getStartParkNo());
					if (park != null) {
						cr.setStartParkName(park.getParkName());
					}
				}
			}
			if (carStatus != null) {
				cr.setStartPower(carStatus.getPower());
			}
			cr.setStartTime(new Date());
			cr.setUseCarType(2);// 调度用车
			// 判断用车记录中是否存在该单的记录，已存在，则修改。不存在，则添加
			if (carRecordService.getCarRecordByDocumentNo(cr.getDocumentNo(), 2) != null) {
				cr.setDocumentNo(cr.getDocumentNo());
				cr.setRecordId(carRecordService.getCarRecordByDocumentNo(cr.getDocumentNo(), 2).getRecordId());
				carRecordService.updateCarRecord(cr, operator);
			} else {
				carRecordService.addCarRecord(cr, operator);
			}
			carStatus.setUserageStatus(1);// 使用状态（0，空闲、1，已预占、2，订单中、3，调度中）
			CarStatus c = new CarStatus();
			c.setCarNo(carStatus.getCarNo());
			c.setUserageStatus(1);
			carStatusService.updateCarStatus(c, operator);
		} else {
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(CarConstant.available_car_msg);
		}
		return resultInfo;
	}

	/*
	 * 调度强制结束前判断
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/checkBeforeOrderForceOver")
	@ResponseBody
	public ResultInfo<OrderFinish> checkBeforeOrderForceOver(String workerOrderNo) throws Exception {
		ResultInfo<OrderFinish> resultInfo = new ResultInfo<OrderFinish>();
		if (workerOrderNo != null && workerOrderNo.length() != 0) {
			// 租车之前先判断该车辆是否是新设备，有则发送还车的指令,并且等待成功响应
			WorkerOrder order = workerOrderService.getWorkerOrder(workerOrderNo);
			Device device = deviceService.getDeviceByCarNo(order.getCarNo());
			if (device != null) {// && "1".equals(device.getVersionType()) 为1是新设备，可以使用用车还车指令，且等待响应完成后继续执行后续业务
				ResultInfo<String> userCarResult = new ResultInfo<String>();
				String res = "";
				try {
					// 发送还车指令出现异常时，允许结束订单
					res = carStatusService.returnCarSendCmd(device.getDeviceSn(), order.getWorkerId(), null, "1");
				} catch (Exception e) {
					resultInfo.setCode(Constant.FAIL);
					resultInfo.setMsg("还车指令发送异常");
					return resultInfo;
				}
				if (!"".equals(res)) {
					Gson gson = new Gson();
					userCarResult = gson.fromJson(res, ResultInfo.class);
					resultInfo.setCode(Constant.FAIL);
					resultInfo.setMsg(userCarResult.getMsg());
					return resultInfo;
				}
			} else {// 旧设备直接返回1
				resultInfo.setCode(Constant.SECCUESS);
			}
		}
		return resultInfo;
	}

	/**
	 * 强制结束
	 * 
	 * @param workerOrderNo
	 *            调度单编号
	 * @return
	 */

	@RequestMapping("/workerOrderOver")
	@ResponseBody
	public ResultInfo<WorkerOrder> workerOrderOver(String workerOrderNo, String workerNo) {

		return workerOrderService.workerOrderFinish(workerOrderNo, workerNo, getOperator());
	}

	/**
	 * 批量导入调度工单信息
	 * 
	 * @param file
	 * @return
	 * @throws Exception
	 */

	@RequestMapping("importWorkerOrder")
	@ResponseBody
	public ResultInfo<WorkerOrder> importWorkerOrder(@RequestParam(value = "file", required = false) MultipartFile file)
			throws Exception {
		ResultInfo<WorkerOrder> resultInfo = new ResultInfo<WorkerOrder>();
		if (file != null) {
			try {
				resultInfo = workerOrderService.importWorkerOrder(file, getOperator());
			} catch (XlsImportException e) {
				resultInfo.setCode(Constant.FAIL);
				resultInfo.setMsg(e.getErrorMsg());
			}
		} else {
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg("请选择文件！");
		}

		return resultInfo;
	}

	/**
	 * 调度工单取消操作
	 */
	@RequestMapping("concelWorkerOrder")
	@ResponseBody
	public ResultInfo<WorkerOrder> concelWorkerOrder(String workerOrderNo) {
		Operator operator = getOperator();
		WorkerOrder wo = new WorkerOrder();
		wo.setWorkerOrderNo(workerOrderNo);
		wo.setWorkOrderStatus(4);// 已取消
		ResultInfo<WorkerOrder> resultInfo = workerOrderService.updateWorkerOrder(wo, operator);
		// 调度单取消将车辆状态表的车辆使用状态改为空闲
		WorkerOrder workerOrder = workerOrderService.getWorkerOrder(workerOrderNo);
		if (workerOrder != null) {
			CarStatus carStatus = new CarStatus();
			carStatus.setCarNo(workerOrder.getCarNo());
			carStatus.setUserageStatus(CarConstant.CAR_USERAGE_STATUS_FREE);
			carStatus.setUpdateTime(new Date());
			carStatusService.updateCarStatus(carStatus, operator); // 更新车辆状态
		}
		return resultInfo;
	}
}
