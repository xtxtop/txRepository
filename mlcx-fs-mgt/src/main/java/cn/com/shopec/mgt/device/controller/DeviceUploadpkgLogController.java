package cn.com.shopec.mgt.device.controller;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.shopec.common.utils.ECDateUtils;
import cn.com.shopec.core.car.common.CarConstant;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.device.model.Device;
import cn.com.shopec.core.device.service.DeviceService;
import cn.com.shopec.core.system.model.SysParam;
import cn.com.shopec.core.system.service.SysParamService;
import cn.com.shopec.core.uploadpkg.model.DeviceUploadpkgLog;
import cn.com.shopec.core.uploadpkg.service.DeviceUploadpkgLogService;
import cn.com.shopec.mgt.common.BaseController;

/**
 * 终端设备管理
 * 
 * @author machao
 *
 */
@Controller
@RequestMapping("deviceUploadpkgLog")
public class DeviceUploadpkgLogController extends BaseController {

	@Resource
	private DeviceService deviceService;

	@Resource
	private DeviceUploadpkgLogService deviceUploadpkgLogService;

	@Resource
	private SysParamService sysParamService;

	@Value("${res_path}")
	private String resPath;

	/*
	 * 显示设备报文页
	 */
	@RequestMapping("/toDeviceUpLogList")
	public String mainPage(ModelMap modelMap, String carPlateNo) {
		modelMap.put("carPlateNo", carPlateNo);
		return "device/device_log_list";
	}

	/**
	 * 查询硬件 报文日志列表(分组后最新的时间)
	 * 
	 * @param query
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping("pageDeviceLogList")
	@ResponseBody
	public PageFinder<DeviceUploadpkgLog> pageDeviceLogList(
			@ModelAttribute("DeviceUploadpkgLog") DeviceUploadpkgLog deviceUploadpkgLog,
			@RequestParam("pageNo") int pageNo, @RequestParam("pageSize") int pageSize) throws ParseException {
		Query q = new Query(pageNo, pageSize, deviceUploadpkgLog);
		PageFinder<DeviceUploadpkgLog> resultInfo = deviceUploadpkgLogService.getDeviceUploadpkgLogPagedList(q);
		return resultInfo;
	}

	/**
	 * 查询硬件 报文详情 列表
	 * 
	 * @param query
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping("getDeviceUploadpkgLogDetailList")
	@ResponseBody
	public PageFinder<DeviceUploadpkgLog> getDeviceUploadpkgLogDetailList(DeviceUploadpkgLog deviceUploadpkgLog,
			@RequestParam("pageNo") int pageNo, @RequestParam("pageSize") int pageSize) {
		Query q = new Query(pageNo, pageSize, deviceUploadpkgLog);
		PageFinder<DeviceUploadpkgLog> resultInfo = deviceUploadpkgLogService.getDeviceUploadpkgLogDetailList(q);
		resultInfo.setPageNo(q.getPageNo());
		resultInfo.setPageSize(q.getPageSize());
		return resultInfo;
	}

	/**
	 * 进入终端设备列表页面
	 * 
	 * @return
	 */
	@RequestMapping("toDeviceList")
	public String toDeviceList() {
		return "device/device_list";
	}

	/**
	 * 终端设备报文详情页面
	 * 
	 * @param
	 * @return
	 */
	@RequestMapping("getDeviceLogView")
	public String getDeviceLogView(@ModelAttribute("DeviceUploadpkgLog") DeviceUploadpkgLog deviceUploadpkgLog,
			Model model) {
		model.addAttribute("deviceSn", deviceUploadpkgLog.getDeviceSn());
		// 从车辆状态 点击 过来
		if (deviceUploadpkgLog.getCarPlateNo() != null && !"".equals(deviceUploadpkgLog.getCarPlateNo())) {
			Device device = deviceService.getDeviceCarPlateNo(deviceUploadpkgLog.getCarPlateNo());
			if (device != null) {
				model.addAttribute("deviceSn", device.getDeviceSn());
			}
			model.addAttribute("carPlateNo", deviceUploadpkgLog.getCarPlateNo());
			// 从终端设备日志列表点击查看
		} else if (deviceUploadpkgLog.getDeviceSn() != null && !"".equals(deviceUploadpkgLog.getDeviceSn())) {
			Device device = deviceService.getDeviceByDeviceSn(deviceUploadpkgLog.getDeviceSn());
			if (device != null) {
				model.addAttribute("deviceSn", device.getDeviceSn());
				model.addAttribute("carPlateNo", device.getCarPlateNo());
			}
		}
		if (deviceUploadpkgLog.getCreateTimeStart() == null && deviceUploadpkgLog.getCreateTimeEnd() == null) {
			// 配置 倒退时间 以 小时为单位
			SysParam sysps = sysParamService.getByParamKey(CarConstant.push_down_time);
			if (sysps != null && !"".equals(sysps.getParamValue())) {
				long times = Long.valueOf(sysps.getParamValue()).longValue();
				long timeLose = System.currentTimeMillis() - times * 60 * 60 * 1000;
				Date createTimeStarts = new Date(timeLose);
				Date date = new Date();
				deviceUploadpkgLog.setCreateTimeStart(createTimeStarts);
				deviceUploadpkgLog.setCreateTimeEnd(date);
			}
			SysParam sysparam = sysParamService.getByParamKey(CarConstant.deviceUploadlogPagesize);
			if (sysparam != null && !"".equals(sysparam.getParamValue())) {
				String pageSize = sysparam.getParamValue();
				model.addAttribute("pageSize", pageSize);
			} else {
				model.addAttribute("pageSize", 20);
			}
		}
		model.addAttribute("createTimeStart", deviceUploadpkgLog.getCreateTimeStart());
		model.addAttribute("createTimeEnd", deviceUploadpkgLog.getCreateTimeEnd());
		return "device/device_log_view";
	}

	/**
	 * export导出文件
	 */
	@RequestMapping("exportDeviceLogData")
	public void exportDeviceLogData(HttpServletRequest request, HttpServletResponse response,
			DeviceUploadpkgLog deviceUploadpkgLog, @RequestParam("pageNo") int pageNo,
			@RequestParam("pageSize") int pageSize) {
		// 导出txt文件
		response.setContentType("text/plain");
		String fileName = "deviceLogData";
		try {
			fileName = URLEncoder.encode("deviceLogData", "UTF-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		response.setHeader("Content-Disposition", "attachment; filename=" + fileName + ".txt");
		BufferedOutputStream buff = null;
		StringBuffer write = new StringBuffer();
		ServletOutputStream outSTr = null;
		try {
			outSTr = response.getOutputStream(); // 建立
			buff = new BufferedOutputStream(outSTr);

			Query q = new Query(1, pageNo * pageSize, deviceUploadpkgLog);
			PageFinder<DeviceUploadpkgLog> resultInfo = deviceUploadpkgLogService.getDeviceUploadpkgLogDetailList(q);
			if (resultInfo != null && !resultInfo.getData().isEmpty()) {
				List<DeviceUploadpkgLog> list = resultInfo.getData();
				for (DeviceUploadpkgLog log : list) {
					String createTime = ECDateUtils.formatDate(log.getCreateTime());
					write.append(createTime + " " + log.getLogContent() + "\r\n");
				}
			}
			buff.write(write.toString().getBytes("UTF-8"));
			buff.flush();
			buff.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				buff.close();
				outSTr.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * 导出原日记文件
	 */
	@RequestMapping("exportOldLogData")
	public void exportOldLogData(HttpServletRequest request, HttpServletResponse response, String deviceSn,
			String date) {
		date = date.substring(0, 10);
		// 导出txt文件
		response.setContentType("text/plain");
		String fileName = deviceSn + "-" + date;
		response.setHeader("Content-Disposition", "attachment; filename=" + fileName + ".txt");
		BufferedOutputStream buff = null;
		StringBuffer write = new StringBuffer();
		ServletOutputStream outSTr = null;
		BufferedReader reader = null;
		try {
			outSTr = response.getOutputStream(); // 建立
			buff = new BufferedOutputStream(outSTr);
			FileReader fileReader = new FileReader(resPath + "/log/" + date + "/" + deviceSn + ".log");
			if (fileReader != null) {
				reader = new BufferedReader(fileReader);
				String tempString = null;
				// 一次读入一行，直到读入null为文件结束
				while ((tempString = reader.readLine()) != null) {
					write.append(tempString + "\r\n");
				}
				reader.close();
			}
			buff.write(write.toString().getBytes("UTF-8"));
			buff.flush();
			buff.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (reader != null) {
					reader.close();
				}
				buff.close();
				outSTr.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
