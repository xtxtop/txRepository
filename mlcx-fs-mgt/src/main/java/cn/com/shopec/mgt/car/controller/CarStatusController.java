package cn.com.shopec.mgt.car.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;

import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.common.constants.Constant;
import cn.com.shopec.common.utils.ECDateUtils;
import cn.com.shopec.common.utils.ECStringUtils;
import cn.com.shopec.core.car.model.CarOperateRecord;
import cn.com.shopec.core.car.model.CarStatus;
import cn.com.shopec.core.car.service.CarOperateRecordService;
import cn.com.shopec.core.car.service.CarService;
import cn.com.shopec.core.car.service.CarStatusService;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.device.model.Device;
import cn.com.shopec.core.device.service.DeviceService;
import cn.com.shopec.core.order.model.ControlPowerLog;
import cn.com.shopec.core.order.model.Order;
import cn.com.shopec.core.order.service.ControlPowerLogService;
import cn.com.shopec.core.order.service.OrderService;
import cn.com.shopec.core.scheduling.service.WorkerOrderService;
import cn.com.shopec.core.system.model.SysParam;
import cn.com.shopec.core.system.model.SysUser;
import cn.com.shopec.core.system.service.DataDictItemService;
import cn.com.shopec.core.system.service.SysParamService;
import cn.com.shopec.core.system.service.SysRoleService;
import cn.com.shopec.core.system.service.SysUserRoleRelService;
import cn.com.shopec.mgt.common.BaseController;

@Controller
@RequestMapping("/carStatus")
public class CarStatusController extends BaseController {

	private static final Log log = LogFactory.getLog(CarStatusController.class);
	
	@Resource
	private CarStatusService carStatusService;
	@Resource
	private DeviceService deviceService;
	@Resource
	private CarService carService;
	@Resource
	private OrderService orderService;
	@Resource
	private WorkerOrderService workerOrderService;
	@Resource
	private SysUserRoleRelService sysUserRoleRelService;
	@Resource
	private SysRoleService sysRoleService;
	@Resource
	private DataDictItemService dataDictItemService;
	@Resource
	private ControlPowerLogService controlPowerLogService;
	@Resource
	private SysParamService sysParamService;
	@Resource
	private CarOperateRecordService carOperateRecordService;
	/*
	 * 显示车辆状态列表页
	 */
	@RequestMapping("/toCarStatusList")
	public String toCarStatusList(CarStatus carStatus, Model model) {
		model.addAttribute("carStatus", carStatus);
		return "car/car_status_list";
	}

	/*
	 * 显示车辆状态列表分页
	 */
	@RequestMapping("/pageListCarStatus")
	@ResponseBody
	public PageFinder<CarStatus> pageListCarStatus(@ModelAttribute("carStatus") CarStatus carStatus, Query query) {
		SysUser sysUser = getLoginSysUser();
		Integer roleAdminTag = getLoginSysUserRoleAdmin();
		Integer param = 20;
		SysParam sysParam = sysParamService.getByParamKey("CarPowerParam");
		if(sysParam != null){
			param = Integer.parseInt(sysParam.getParamValue());
		}
		if(carStatus.getIsLowPower() != null){
			carStatus.setLowPowerValue(param);
		}
		if(carStatus.getIsMinLowPower() != null){
			carStatus.setMinLowPowerValue(5);
		}
		Query q = new Query(query.getPageNo(), query.getPageSize(), carStatus);
		return carStatusService.getCarStatusPageList(q, roleAdminTag);
	}

	/*
	 * 开门
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/openDoor")
	@ResponseBody
	public ResultInfo<CarStatus> openDoor(String deviceNo,String memo) {
		ResultInfo<CarStatus> resultInfo = new ResultInfo<CarStatus>();
		Device device = deviceService.getDevice(deviceNo);
		if(device == null){
			resultInfo.setCode("3");
			return resultInfo;
		}
		try {
			String result = carStatusService.openDoor(device.getDeviceSn());
			log.info("开门指令："+result);
			if(result == null || result.trim().length() == 0){
				resultInfo.setCode(Constant.FAIL);
				return resultInfo;
			}
			CarOperateRecord carOperateRecord = new CarOperateRecord();
			carOperateRecord.setCarNo(device.getCarNo());
			carOperateRecord.setCarPlateNo(device.getCarPlateNo());
			carOperateRecord.setOperateType(2);//开门
			carOperateRecord.setMemo(memo);
			carOperateRecordService.addCarOperateRecord(carOperateRecord, this.getOperator());
			Gson gson = new Gson();
			resultInfo = gson.fromJson(result, ResultInfo.class);
			return resultInfo;
		} catch (Exception e) {
			log.error(e);
			resultInfo.setCode("4");
			return resultInfo;
		}
	}

	/*
	 * 关门
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/closeDoor")
	@ResponseBody
	public ResultInfo<CarStatus> closeDoor(String deviceNo,String memo) {
		ResultInfo<CarStatus> resultInfo = new ResultInfo<CarStatus>();
		Device device = deviceService.getDevice(deviceNo);
		if(device == null){
			resultInfo.setCode("3");
			return resultInfo;
		}
		try {
			String result = carStatusService.closeDoor(device.getDeviceSn());
			log.info("关门指令："+result);
			if(result == null || result.trim().length() == 0){
				resultInfo.setCode(Constant.FAIL);
				return resultInfo;
			}
			CarOperateRecord carOperateRecord = new CarOperateRecord();
			carOperateRecord.setCarNo(device.getCarNo());
			carOperateRecord.setCarPlateNo(device.getCarPlateNo());
			carOperateRecord.setOperateType(1);//关门
			carOperateRecord.setMemo(memo);
			carOperateRecordService.addCarOperateRecord(carOperateRecord, this.getOperator());
			Gson gson = new Gson();
			resultInfo = gson.fromJson(result, ResultInfo.class);
			return resultInfo;
		} catch (Exception e) {
			log.error(e);
			resultInfo.setCode("4");
			return resultInfo;
		}
	}

	/*
	 * 寻车操作
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/findCar")
	@ResponseBody
	public ResultInfo<CarStatus> findCar(String deviceNo,String memo) {
		ResultInfo<CarStatus> resultInfo = new ResultInfo<CarStatus>();
		Device device = deviceService.getDevice(deviceNo);
		if(device == null){
			resultInfo.setCode("3");
			return resultInfo;
		}
		try {
			String result = carStatusService.findCar(device.getDeviceSn());
			log.info("寻车指令："+result);
			if(result == null || result.trim().length() == 0){
				resultInfo.setCode(Constant.FAIL);
				return resultInfo;
			}
			CarOperateRecord carOperateRecord = new CarOperateRecord();
			carOperateRecord.setCarNo(device.getCarNo());
			carOperateRecord.setCarPlateNo(device.getCarPlateNo());
			carOperateRecord.setOperateType(5);//寻车
			carOperateRecord.setMemo(memo);
			carOperateRecordService.addCarOperateRecord(carOperateRecord, this.getOperator());
			Gson gson = new Gson();
			resultInfo = gson.fromJson(result, ResultInfo.class);
			return resultInfo;
		} catch (Exception e) {
			log.error(e);
			resultInfo.setCode("4");
			return resultInfo;
		}
	}	
	
	/**
	 * 动力控制
	 * 
	 * @param deviceNo
	 * @param cmdValue
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/controlPower")
	@ResponseBody
	public ResultInfo<CarStatus> controlPower(String deviceNo, String cmdValue,String memo) {
		ResultInfo<CarStatus> resultInfo = new ResultInfo<CarStatus>();
		Device device = deviceService.getDevice(deviceNo);
		if(device == null){
			resultInfo.setCode("3");
			return resultInfo;
		}
		try {
			String result = carStatusService.controlPower(device.getDeviceSn(), cmdValue);
			log.info("动力控制指令："+result);
			if(result == null || result.trim().length() == 0){
				resultInfo.setCode(Constant.FAIL);
				return resultInfo;
			}
			CarOperateRecord carOperateRecord = new CarOperateRecord();
			carOperateRecord.setCarNo(device.getCarNo());
			carOperateRecord.setCarPlateNo(device.getCarPlateNo());
			if("0".equals(cmdValue)){
				carOperateRecord.setOperateType(3);//关闭动力
			}else{
				carOperateRecord.setOperateType(4);//启动动力
			}
			carOperateRecord.setMemo(memo);
			carOperateRecordService.addCarOperateRecord(carOperateRecord, this.getOperator());
			Gson gson = new Gson();
			resultInfo = gson.fromJson(result, ResultInfo.class);
			return resultInfo;
		} catch (Exception e) {
			e.printStackTrace();
			resultInfo.setCode("4");
			return resultInfo;
		}
	}

	/**
	 * 车机修改车辆租赁模式
	 * 
	 * @param deviceNo
	 * @param cmdValue
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/setTerminalLeasingMode")
	@ResponseBody
	public ResultInfo<CarStatus> setTerminalLeasingMode(String deviceNo, String cmdValue,String memo) {
		ResultInfo<CarStatus> resultInfo = new ResultInfo<CarStatus>();
		Device device = deviceService.getDevice(deviceNo);
		if(device == null){
			resultInfo.setCode("3");
			return resultInfo;
		}
		try {
			String result = carStatusService.setTerminalLeasingMode(device.getDeviceSn(), cmdValue);
			log.info("车机修改车辆租赁模式指令："+result);
			if(result == null || result.trim().length() == 0){
				resultInfo.setCode(Constant.FAIL);
				return resultInfo;
			}
			CarOperateRecord carOperateRecord = new CarOperateRecord();
			carOperateRecord.setCarNo(device.getCarNo());
			carOperateRecord.setCarPlateNo(device.getCarPlateNo());
			if("0".equals(cmdValue)){
				carOperateRecord.setOperateType(7);//车机上的车辆租赁模式-改为非租赁模式
			}else if("1".equals(cmdValue)){
				carOperateRecord.setOperateType(6);//车机上的车辆租赁模式-改为租赁模式
			}
			carOperateRecord.setMemo(memo);
			carOperateRecordService.addCarOperateRecord(carOperateRecord, this.getOperator());
			Gson gson = new Gson();
			resultInfo = gson.fromJson(result, ResultInfo.class);
			return resultInfo;
		} catch (Exception e) {
			e.printStackTrace();
			resultInfo.setCode("4");
			return resultInfo;
		}
	}

	/**
	 * 车机修改物理钥匙使能/禁用
	 * 
	 * @param deviceNo
	 * @param cmdValue
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/setKeyEnableMode")
	@ResponseBody
	public ResultInfo<CarStatus> setKeyEnableMode(String deviceNo, String cmdValue,String memo) {
		ResultInfo<CarStatus> resultInfo = new ResultInfo<CarStatus>();
		Device device = deviceService.getDevice(deviceNo);
		if(device == null){
			resultInfo.setCode("3");
			return resultInfo;
		}
		try {
			String result = carStatusService.setKeyEnableMode(device.getDeviceSn(), cmdValue);
			log.info("车机修改物理钥匙使能指令："+result);
			if(result == null || result.trim().length() == 0){
				resultInfo.setCode(Constant.FAIL);
				return resultInfo;
			}
			CarOperateRecord carOperateRecord = new CarOperateRecord();
			carOperateRecord.setCarNo(device.getCarNo());
			carOperateRecord.setCarPlateNo(device.getCarPlateNo());
			if("0".equals(cmdValue)){
				carOperateRecord.setOperateType(9);//物理钥匙禁用
			}else if("1".equals(cmdValue)){
				carOperateRecord.setOperateType(8);//物理钥匙使能
			}
			carOperateRecord.setMemo(memo);
			carOperateRecordService.addCarOperateRecord(carOperateRecord, this.getOperator());
			Gson gson = new Gson();
			resultInfo = gson.fromJson(result, ResultInfo.class);
			return resultInfo;
		} catch (Exception e) {
			e.printStackTrace();
			resultInfo.setCode("4");
			return resultInfo;
		}
	}
	
	/**
	 * 超时订单动力控制
	 * 
	 * @param deviceNo
	 * @param cmdValue
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/overOrdercontrolPower")
	@ResponseBody
	public ResultInfo<CarStatus> overOrdercontrolPower(String cmdValue, String orderNo) {
		ControlPowerLog controlPowerLog = new ControlPowerLog();
		ResultInfo<CarStatus> resultInfo = new ResultInfo<CarStatus>();
		Order order = orderService.getOrder(orderNo);
		if(order == null || order.getDeviceNo() == null){
			resultInfo.setCode("3");
			return resultInfo;
		}
		CarStatus cs = new CarStatus();
		cs.setDeviceNo(order.getDeviceNo());
		Query q = new Query(cs);
		List<CarStatus> cst = carStatusService.getCarStatusList(q);
		if (cst == null || cst.size() == 0) {
			Device device = deviceService.getDevice(order.getDeviceNo());
			if(device == null){
				resultInfo.setCode("3");
				return resultInfo;
			}
			try {
				String result = carStatusService.controlPower(device.getDeviceSn(), cmdValue);
				if(result == null || result.trim().length() == 0){
					resultInfo.setCode(Constant.FAIL);
					return resultInfo;
				}
				Gson gson = new Gson();
				resultInfo = gson.fromJson(result, ResultInfo.class);
			} catch (Exception e) {
				e.printStackTrace();
				resultInfo.setCode("4");
				return resultInfo;
			}
		}
		if (cst.get(0).getCarStatus() != null) {
			controlPowerLog.setCarStatus(cst.get(0).getCarStatus());
			if (cst.get(0).getCarStatus() == 1) {
				resultInfo.setCode("-10");
				resultInfo.setMsg("车辆没有熄火 请熄火后 关闭动力！");
			}

		}		
		controlPowerLog.setCantrolType(Integer.parseInt(cmdValue));
		controlPowerLog.setCreateTime(new Date());
		controlPowerLog.setDeviceNo(order.getDeviceNo());
		controlPowerLog.setOrderNo(order.getOrderNo());
		controlPowerLog.setMemberNo(order.getMemberNo());
		if (resultInfo.getCode() != null && !"".equals(resultInfo.getCode()) && resultInfo.getCode().equals("1")) {
			controlPowerLog.setStatusType(1);
		} else {
			controlPowerLog.setStatusType(2);
		}
		controlPowerLogService.addControlPowerLog(controlPowerLog, getOperator());
		return resultInfo;
	}

	/**
	 * 导出
	 */
	@RequestMapping("toCarStatusExport")
	public void toMemberExport(@ModelAttribute("carStatus") CarStatus carStatus, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			Query q = new Query();
			q.setQ(carStatus);
			List<CarStatus> list = carStatusService.getCarStatusPageListForExport(q);
			// 声明一个工作薄
			String path = request.getRealPath("/") + "res" + File.separator + "carStatus.xls";
			File ff = new File(path);
			InputStream is = new FileInputStream(ff);
			HSSFWorkbook workbook = new HSSFWorkbook(is);
			// 生成一个表格
			HSSFSheet sheet = workbook.getSheetAt(0);
			int i = 1;
			for (CarStatus carStatusData : list) {
				sheet.createRow(i);
				// 车牌号
				String carPlateNo = carStatusData.getCarPlateNo();
				// 城市
				String cityName = carStatusData.getCityName();
				// 租赁类型
				String leaseType = "";
				if (carStatusData.getLeaseType() == 1) {
					leaseType = "分时";
				} else if (carStatusData.getLeaseType() == 2) {
					leaseType = "长租";
				}
				// 车主
				String carOwnerName = carStatusData.getCarOwnerName();
				// 车辆状态
				String carStatus1 = "";
				if (carStatusData.getCarStatus() != null) {
					if (carStatusData.getCarStatus() == 1) {
						carStatus1 = "已启动";
					} else if (carStatusData.getCarStatus() == 2) {
						carStatus1 = "已熄火";
					}
				} else {
					carStatus1 = "已熄火";
				}

				// 上下线状态
				String onLineStatus = "";
				if (carStatusData.getOnlineStatus() == 0) {
					onLineStatus = "下线";
				} else if (carStatusData.getOnlineStatus() == 1) {
					onLineStatus = "上线";
				}

				// 使用状态
				String useStatus = "";
				if (carStatusData.getUserageStatus() == 0) {// 使用状态（0，空闲、1，已预占、2，订单中、3，调度中）
					useStatus = "空闲";
				} else if (carStatusData.getUserageStatus() == 1) {
					useStatus = "已预占";
				} else if (carStatusData.getUserageStatus() == 2) {
					useStatus = "订单中";
				} else if (carStatusData.getUserageStatus() == 3) {
					useStatus = "调度中";
				}
				// 电量
				String power = "";
				if (carStatusData.getPower() != null) {
					power = carStatusData.getPower() + "%";
				} else {
					power = "0%";
				}
				// 里程
				String mileage = "";
				if (carStatusData.getMileage() != null) {
					mileage = carStatusData.getMileage() + "";
				} else {
					mileage = "0";
				}
				// 终端序列号
				String deviceSn = carStatusData.getDeviceSn();
				// 终端状态
				String deviceStatus = "";
				if (carStatusData.getDeviceStatus() != null && !carStatusData.getDeviceStatus().equals("")) {
					if (carStatusData.getDeviceStatus() == 1) {
						deviceStatus = "在线";
					} else if (carStatusData.getDeviceStatus() == 2) {
						deviceStatus = "节能";
					} else if (carStatusData.getDeviceStatus() == 3) {
						deviceStatus = "待机";
					} else if (carStatusData.getDeviceStatus() == 4) {
						deviceStatus = "离线";
					} else {
						deviceStatus = "离线";
					}
				}
				setColumnValue(sheet, i, 0, ECStringUtils.toStringForNull(carPlateNo));
				setColumnValue(sheet, i, 1, ECStringUtils.toStringForNull(cityName));
				setColumnValue(sheet, i, 2, ECStringUtils.toStringForNull(leaseType));
				setColumnValue(sheet, i, 3, ECStringUtils.toStringForNull(carOwnerName));
				setColumnValue(sheet, i, 4, ECStringUtils.toStringForNull(carStatus1));
				setColumnValue(sheet, i, 5, ECStringUtils.toStringForNull(onLineStatus));
				setColumnValue(sheet, i, 6, ECStringUtils.toStringForNull(useStatus));
				setColumnValue(sheet, i, 7, ECStringUtils.toStringForNull(power));
				setColumnValue(sheet, i, 8, ECStringUtils.toStringForNull(mileage));
				setColumnValue(sheet, i, 9, ECStringUtils.toStringForNull(deviceSn));
				setColumnValue(sheet, i, 10, ECStringUtils.toStringForNull(deviceStatus));
				setColumnValue(sheet, i, 11, ECDateUtils.formatDate(carStatusData.getLastReportingTime()));
				i++;
			}
			response.reset();
			response.setContentType("text/plain;charset=UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.addHeader("Content-Disposition", "attachment;filename=carStatus.xls");
			ServletOutputStream os = response.getOutputStream();
			workbook.write(os);
			os.flush();
			os.close(); // 关闭流
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * 显示车辆状态详情
	 */
	@RequestMapping("/getCarStatus")
	@ResponseBody
	public CarStatus getCarStatus(String carNo) {
		return carStatusService.getCarStatus(carNo);

	}
}
