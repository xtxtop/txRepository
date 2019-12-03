package cn.com.shopec.mgt.device.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;

import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.common.constants.Constant;
import cn.com.shopec.common.exception.xls.XlsImportException;
import cn.com.shopec.common.utils.HttpURLRequestUtil;
import cn.com.shopec.core.car.model.Car;
import cn.com.shopec.core.car.service.CarService;
import cn.com.shopec.core.car.service.CarStatusService;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.device.model.Device;
import cn.com.shopec.core.device.model.DeviceParameter;
import cn.com.shopec.core.device.service.DeviceService;
import cn.com.shopec.core.system.model.DataDictItem;
import cn.com.shopec.core.system.model.SysUser;
import cn.com.shopec.core.system.service.DataDictItemService;
import cn.com.shopec.core.system.service.SysParamService;
import cn.com.shopec.core.system.service.SysRoleService;
import cn.com.shopec.core.system.service.SysUserRoleRelService;
import cn.com.shopec.mgt.common.BaseController;

/**
 * 终端设备管理
 * 
 * @author machao
 *
 */
@Controller
@RequestMapping("/device")
public class DeviceController extends BaseController {

	private static final Log log = LogFactory.getLog(DeviceController.class);

	@Resource
	private DeviceService deviceService;

	@Resource
	private CarStatusService carStatusService;

	@Resource
	private DataDictItemService dataDictItemService;

	@Resource
	private CarService carService;

	@Resource
	private SysUserRoleRelService sysUserRoleRelService;

	@Resource
	private SysRoleService sysRoleService;

	@Resource
	private SysParamService sysParamService;

	/**
	 * 进入终端设备列表页面
	 * 
	 * @return
	 */
	@RequestMapping("toDeviceList")
	public String toDeviceList(Model model) {
		List<DataDictItem> cities = dataDictItemService.getDataDictItemListByCatCode("CITY");// 城市
		model.addAttribute("cities", cities);
		return "device/device_list";
	}

	/**
	 * 查询终端设备列表
	 * 
	 * @param query
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping("pageListDevice")
	@ResponseBody
	public PageFinder<Device> pageListDevice(@ModelAttribute("device") Device device,
			@RequestParam("pageNo") int pageNo, @RequestParam("pageSize") int pageSize) throws ParseException {
		SysUser sysUser = getLoginSysUser();
		Integer roleAdminTag = getLoginSysUserRoleAdmin();
		// //判断当前登录用户的角色是不是系统管理员
		// if(sysUser!=null){
		// List<SysUserRoleRel>
		// list=sysUserRoleRelService.getByUserId(sysUser.getUserId());
		// if(list!=null&&list.size()>0){
		// SysRole sysRole=new SysRole();
		// sysRole.setRoleName("系统管理员");
		// List<SysRole> roleList=sysRoleService.queryAll(new Query(sysRole));
		// if(roleList!=null&&roleList.size()>0){
		// sysRole=roleList.get(0);
		// for(SysUserRoleRel surr:list){
		// if(surr.getRoleId().equals(sysRole.getRoleId())){
		// roleAdminTag=1;
		// }
		// }
		// }
		// }
		// }
		Query q = new Query(pageNo, pageSize, device);
		return deviceService.getDevicePagedList(q, roleAdminTag);
	}

	
	/**
	 * 从新增或者编辑车辆查询终端设备列表
	 * 
	 * @param query
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping("pageListDeviceByCar")
	@ResponseBody
	public PageFinder<Device> pageListDeviceByCar(@ModelAttribute("device") Device device,
			@RequestParam("pageNo") int pageNo, @RequestParam("pageSize") int pageSize) throws ParseException {
		Integer roleAdminTag = getLoginSysUserRoleAdmin();
		Query q = new Query(pageNo, pageSize, device);
		return deviceService.pageListDeviceByCar(q, roleAdminTag);
	}

	
	
	/**
	 * 终端设备详情
	 * 
	 * @param terminalDeviceNo
	 * @return
	 */
	@RequestMapping("getDeviceView")
	public String getDeviceView(@ModelAttribute("terminalDeviceNo") String terminalDeviceNo, Model model) {
		Device device = deviceService.getDevice(terminalDeviceNo);
		model.addAttribute("device", device);
		return "device/device_view";
	}

	
	/**
	 * 终端设备终端参数
	 * 
	 * @param terminalDeviceNo
	 * @return
	 */
	@RequestMapping("getDeviceParameter")
	public String getDeviceParameter(@ModelAttribute("terminalDeviceNo") String terminalDeviceNo, Model model) {
		Device device = deviceService.getDevice(terminalDeviceNo);
		if(device.getDeviceParam() != null && !"".equals(device.getDeviceParam())){
			String ss=device.getDeviceParam().replace("|",",");
			String [] sss = ss.split(",");
			for (int i = 0; i < sss.length; i++) {
				String key=sss[i].substring(0, 4);
				 if(key.equals("1001")){
					model.addAttribute("deviceNo", sss[i].substring(5, sss[i].length()));
				}else if(key.equals("1002")){
					model.addAttribute("deviceModel", sss[i].substring(5, sss[i].length()));
				}else if(key.equals("1003")){
					model.addAttribute("brandName", sss[i].substring(5, sss[i].length()));
				}else if(key.equals("1004")){
					model.addAttribute("iccid", sss[i].substring(5, sss[i].length()));
				}else if(key.equals("1005")){
					model.addAttribute("msisdn", sss[i].substring(5, sss[i].length()));
				}else if(key.equals("1006")){
					model.addAttribute("softwareVersion", sss[i].substring(5, sss[i].length()));
				}else if(key.equals("1007")){
					model.addAttribute("hardwareVersion", sss[i].substring(5, sss[i].length()));
				}else if(key.equals("2001")){
					model.addAttribute("vin", sss[i].substring(5, sss[i].length()));
				}else if(key.equals("2002")){
					model.addAttribute("leaseMode", sss[i].substring(5, sss[i].length()));
				}else if(key.equals("3001")){
					model.addAttribute("domainName", sss[i].substring(5, sss[i].length()));
				}else if(key.equals("3002")){
					model.addAttribute("port", sss[i].substring(5, sss[i].length()));
				}else if(key.equals("3003")){
					model.addAttribute("frequency", sss[i].substring(5, sss[i].length()));
				}
			}
		}
		model.addAttribute("device", device);
		return "device/device_parameter";
	}

	
	
	/**
	 * 请求硬件获取终端设备参数
	 * 
	 * @param terminalDeviceNo
	 * @return
	 * @throws IOException 
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("getDeviceParam")
	@ResponseBody
	public ResultInfo<DeviceParameter> getDeviceParam(@ModelAttribute("terminalDeviceNo") String terminalDeviceNo, Model model) throws IOException {
		ResultInfo<DeviceParameter> resultInfo = new ResultInfo<DeviceParameter>();
		DeviceParameter d= new DeviceParameter();
		Device device = deviceService.getDevice(terminalDeviceNo);
		String result = carStatusService.getDeviceParam(device.getDeviceSn());
		log.info("重启设备："+result);
		if(result == null || result.trim().length() == 0){
			resultInfo.setCode(Constant.FAIL);
			return resultInfo;
		}
		Gson gson = new Gson();
		resultInfo = gson.fromJson(result, ResultInfo.class);
		if(resultInfo.getCode().equals("1") && resultInfo.getMsg() != null && !"".equals(resultInfo.getMsg())){
			String ss=resultInfo.getMsg().replace("|",",");
			String [] sss = ss.split(",");
			for (int i = 0; i < sss.length; i++) {
				String key=sss[i].substring(0, 4);
				 if(key.equals("1001")){
					 d.setDeviceSn(sss[i].substring(5, sss[i].length()));
				}else if(key.equals("1002")){
					 d.setDeviceModel(sss[i].substring(5, sss[i].length()));
				}else if(key.equals("1003")){
					 d.setBrandName(sss[i].substring(5, sss[i].length()));
				}else if(key.equals("1004")){
					 d.setIccid(sss[i].substring(5, sss[i].length()));
				}else if(key.equals("1005")){
					 d.setMsisdn(sss[i].substring(5, sss[i].length()));
				}else if(key.equals("1006")){
					 d.setSoftwareVersion(sss[i].substring(5, sss[i].length()));
				}else if(key.equals("1007")){
					 d.setHardwareVersion(sss[i].substring(5, sss[i].length()));
				}else if(key.equals("2001")){
					 d.setVin(sss[i].substring(5, sss[i].length()));
				}else if(key.equals("2002")){
					 d.setLeaseMode(sss[i].substring(5, sss[i].length()));
				}else if(key.equals("3001")){
					 d.setDomainName(sss[i].substring(5, sss[i].length()));
				}else if(key.equals("3002")){
					 d.setPort(sss[i].substring(5, sss[i].length()));
				}else if(key.equals("3003")){
					 d.setFrequency(sss[i].substring(5, sss[i].length()));
					
				}
			}
			resultInfo.setCode("1");
			resultInfo.setData(d);
		}
		
		return resultInfo;
		
	}

	
	
	
	/**
	 * 重置终端设备终端参数
	 * 
	 * @param url
	 * @param deviceSn
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/restartDeviceParameter")
	@ResponseBody
	public ResultInfo<DeviceParameter> restartDeviceParameter(@ModelAttribute("DeviceParameter") DeviceParameter deviceParameter) {
		ResultInfo<DeviceParameter> resultInfo = new ResultInfo<DeviceParameter>();
		try {
			String param="1001:"+deviceParameter.getDeviceSn()+"|1002:"+deviceParameter.getDeviceModel() + "|1003:"+deviceParameter.getBrandName()+
					"|1004:"+deviceParameter.getIccid()+"|1005:"+deviceParameter.getMsisdn()+"|1006:"+deviceParameter.getSoftwareVersion()+
					"|1007:"+deviceParameter.getHardwareVersion()+"|2001:"+deviceParameter.getVin()+"|2002:"+deviceParameter.getLeaseMode()+
					"|3001:"+deviceParameter.getDomainName()+"|3002:"+deviceParameter.getPort()+"|3003:"+deviceParameter.getFrequency();
			String result = carStatusService.restartDeviceParameter(deviceParameter.getDeviceSn(),param);
			log.info("重置终端设备终端参数："+result);
			if(result == null || result.trim().length() == 0){
				resultInfo.setCode(Constant.FAIL);
				return resultInfo;
			}
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
	 * 终端设备编辑页面
	 * 
	 * @param memberNo
	 * @return
	 */
	@RequestMapping("getDeviceDetail")
	public String getDeviceDetail(@ModelAttribute("terminalDeviceNo") String terminalDeviceNo, Model model) {
		Device device = deviceService.getDevice(terminalDeviceNo);
		List<DataDictItem> cities = dataDictItemService.getDataDictItemListByCatCode("CITY");
		List<DataDictItem> deviceModels = dataDictItemService.getDataDictItemListByCatCode("DEVICE_MODEL");
		List<DataDictItem> brands = dataDictItemService.getDataDictItemListByCatCode("DEVICE_BRAND");
		model.addAttribute("device", device);
		model.addAttribute("cities", cities);
		model.addAttribute("deviceModels", deviceModels);
		model.addAttribute("brands", brands);
		return "device/device_edit";
	}

	/**
	 * 终端设备修改
	 * 
	 * @param device
	 * @return
	 */
	@RequestMapping("updateDevice")
	@ResponseBody
	public ResultInfo<Device> updateDevice(@ModelAttribute("device") Device device) {
		ResultInfo<Device> resultInfo = new ResultInfo<Device>();
		Device d = deviceService.getDevice(device.getDeviceSn());
		if (d != null) {
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg("“" + device.getDeviceSn() + "”,此终端设备已添加！");
		} else {
			resultInfo.setCode(Constant.SECCUESS);
			resultInfo = deviceService.updateDevice(device, getOperator());
		}
		return resultInfo;
	}

	/**
	 * 终端设备编辑页面
	 * 
	 * @return
	 */
	@RequestMapping("toAddDevice")
	public String toAddDevice(Model model) {
		List<DataDictItem> cities = dataDictItemService.getDataDictItemListByCatCode("CITY");
		List<DataDictItem> deviceModels = dataDictItemService.getDataDictItemListByCatCode("DEVICE_MODEL");
		List<DataDictItem> brands = dataDictItemService.getDataDictItemListByCatCode("DEVICE_BRAND");
		model.addAttribute("cities", cities);
		model.addAttribute("deviceModels", deviceModels);
		model.addAttribute("brands", brands);
		return "device/device_add";
	}

	/**
	 * 终端设备添加
	 * 
	 * @param device
	 * @return
	 */
	@RequestMapping("addDevice")
	@ResponseBody
	public ResultInfo<Device> addDevice(@ModelAttribute("device") Device device, Model model) {
		ResultInfo<Device> resultInfo = new ResultInfo<Device>();
		Device d = deviceService.getDevice(device.getDeviceSn());
		if (d != null) {
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg("“" + device.getDeviceSn() + "”,此终端设备已添加！");
			return resultInfo;
		} else {
			resultInfo.setCode(Constant.SECCUESS);
		}

		// 判断macAddr地址 是否重复
		List<Device> dm = new ArrayList<Device>();
		Device dvm = new Device();
		dvm.setMacAddr(device.getMacAddr());
		Query q = new Query(dvm);
		dm = deviceService.getDeviceList(q);
		if (dm != null && dm.size() > 0) {
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg("“" + device.getMacAddr() + "”,此MAC地址已经添加！");
			return resultInfo;
		} else {
			resultInfo.setCode(Constant.SECCUESS);
		}

		// 判断SIM卡号 是否重复
		List<Device> ds = new ArrayList<Device>();
		Device dvs = new Device();
		dvs.setSimCardNo(device.getSimCardNo());
		Query qs = new Query(dvs);
		ds = deviceService.getDeviceList(qs);
		if (ds != null && ds.size() > 0) {
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg("“" + device.getSimCardNo() + "”,此SIM卡号已添加！");
			return resultInfo;
		} else {
			resultInfo.setCode(Constant.SECCUESS);
		}
		resultInfo = deviceService.addDevice(device, getOperator());
		return resultInfo;
	}

	/**
	 * 空请求
	 * 
	 * @return
	 */
	@RequestMapping("ajaxText")
	@ResponseBody
	public ResultInfo<Device> getDeviceByDeviceNo() {
		ResultInfo<Device> resultInfo = new ResultInfo<Device>();
		resultInfo.setCode("1");
		return resultInfo;
	}

	/**
	 * 终端设备启用停用
	 * 
	 * @param device
	 * @return
	 */
	@RequestMapping("enableDevice")
	@ResponseBody
	public ResultInfo<Device> enableDevice(@ModelAttribute("device") Device device) {
		return deviceService.updateDevice(device, getOperator());
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
		String path = request.getRealPath("/") + "download" + File.separator + "device.xls";
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
	 * 批量导入设备信息
	 * 
	 * @param file
	 * @return
	 * @throws Exception
	 */

	@RequestMapping("importDevice")
	@ResponseBody
	public ResultInfo<Device> importItems(@RequestParam(value = "file", required = false) MultipartFile file)
			throws Exception {
		ResultInfo<Device> resultInfo = new ResultInfo<Device>();
		if (file != null) {
			try {
				resultInfo = deviceService.importDeviceInfo(file, getOperator());
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
	 * 设备信息模板导出
	 * 
	 * @param ids
	 * @param request
	 * @return
	 * @throws Exception
	 */

	@RequestMapping("exportDevice")
	public ResponseEntity<byte[]> exportItems(Long[] ids, HttpServletRequest request) throws Exception {
		String filename = deviceService.exportDeviceInfo();
		File file = new File(filename);
		byte[] bytes = FileUtils.readFileToByteArray(file);
		if (file.exists()) {
			file.delete();
		}
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		headers.setContentDispositionFormData("attachment", new String("device.xls".getBytes("UTF-8"), "ISO8859-1")); // 解决文件名中文乱码问题
		return new ResponseEntity<byte[]>(bytes, headers, HttpStatus.OK);
	}

	/**
	 * @author lj 后台查看有没有存在绑定的车辆终端
	 */
	@RequestMapping("getDevice")
	@ResponseBody
	public ResultInfo<Device> getDevice(String terminalDeviceNo) {
		ResultInfo<Device> res = new ResultInfo<Device>();
		if (terminalDeviceNo != null && terminalDeviceNo.length() != 0) {
			Device device = deviceService.getDevice(terminalDeviceNo);
			if (device != null && device.getCarNo() != null && device.getCarNo().length() != 0) {
				Car car = carService.getCar(device.getCarNo());
				if (car != null) {
					res.setCode(Constant.FAIL);
				}
			} else {
				res.setCode(Constant.SECCUESS);
				res.setData(device);
			}
		} else {
			res.setCode(Constant.FAIL);
		}
		return res;
	}

	/**
	 * 重启设备接口
	 * 
	 * @param url
	 * @param deviceSn
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/restartDevice")
	@ResponseBody
	public ResultInfo<Device> restartDevice(String deviceNo) {
		ResultInfo<Device> resultInfo = new ResultInfo<Device>();
		Device device = deviceService.getDevice(deviceNo);
		if(device == null){
			resultInfo.setCode("3");
			return resultInfo;
		}
		try {
			String result = carStatusService.restartDevice(device.getDeviceSn());
			log.info("重启设备："+result);
			if(result == null || result.trim().length() == 0){
				resultInfo.setCode(Constant.FAIL);
				return resultInfo;
			}
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
	 * 设备升级
	 * 
	 * @param url
	 * @param deviceSn
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/deviceUpgrade")
	@ResponseBody
	public ResultInfo<Device> deviceUpgrade(String deviceNo) {
		ResultInfo<Device> resultInfo = new ResultInfo<Device>();
		Device device = deviceService.getDevice(deviceNo);
		if(device == null){
			resultInfo.setCode("3");
			return resultInfo;
		}
		try {
			String result = carStatusService.deviceUpgrade(device.getDeviceSn());
			log.info("设备升级："+result);
			if(result == null || result.trim().length() == 0){
				resultInfo.setCode(Constant.FAIL);
				return resultInfo;
			}
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
	 * 重启蓝牙接口
	 * 
	 * @param url
	 * @param deviceSn
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/restartDeviceBluetooth")
	@ResponseBody
	public ResultInfo<Device> restartDeviceBluetooth(String deviceNo) {
		ResultInfo<Device> resultInfo = new ResultInfo<Device>();
		Device device = deviceService.getDevice(deviceNo);
		if(device == null){
			resultInfo.setCode("3");
			return resultInfo;
		}
		try {
			String result = carStatusService.restartDeviceBluetooth(device.getDeviceSn());
			log.info("重启蓝牙："+result);
			if(result == null || result.trim().length() == 0){
				resultInfo.setCode(Constant.FAIL);
				return resultInfo;
			}
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
	 * 激活/禁用动力开关
	 * 
	 * @param deviceSn
	 * @return
	 */
	@RequestMapping("/enablePowerCtrl")
	@ResponseBody
	public ResultInfo<Device> enablePowerCtrl(String deviceNo, String enable) {
		ResultInfo<Device> resultInfo = new ResultInfo<Device>();
		Device device = deviceService.getDevice(deviceNo);
		String result;
		if (device != null) {
			try {
				result = carStatusService.enablePowerCtrl(device.getDeviceSn(), enable);
				if (result != null && !"".equals(result.trim())) {
					if (Boolean.parseBoolean(result.trim())) {
						resultInfo.setCode(Constant.SECCUESS);
					} else {
						resultInfo.setCode(Constant.FAIL);
					}
				} else {
					resultInfo.setCode(Constant.FAIL);
				}
			} catch (Exception e) {
				resultInfo.setCode(Constant.FAIL);
				e.printStackTrace();
			}
		} else {
			resultInfo.setCode("3");
		}
		return resultInfo;
	}
	
	/**
	 * 重置蓝牙名
	 * 
	 * @param deviceSn
	 * @return
	 */
	@RequestMapping("/getBluetoothName")
	@ResponseBody
	public ResultInfo<Device> getBluetoothName(String deviceNo, String carPlateNo) {
		ResultInfo<Device> resultInfo = new ResultInfo<Device>();
		Device device = deviceService.getDevice(deviceNo);
		String d = sysParamService.getByParamKey("CUSTOMER_COMPANY_CODE").getParamValue();
		String blueToothName = null;
		String carPlateNo_last5 =carPlateNo.substring(2, carPlateNo.length());
		if (device.getDeviceModel().equals("E6")) {//速锐德
			String macAddr = device.getMacAddr().replace(":", "");
			String mac_last5 = macAddr.substring(macAddr.length() - 6, macAddr.length());
			blueToothName = d + "_" + carPlateNo_last5 + "_" + mac_last5;
		} else if (device.getDeviceModel().equals("04")) {//领航
			blueToothName = d + "_" + carPlateNo_last5;
		}else  {//其他类型，暂定
			blueToothName = d + "_" + carPlateNo_last5;
		} 
		device.setBluetoothName(blueToothName);
		resultInfo.setCode(Constant.SECCUESS);
		resultInfo.setData(device);
		return resultInfo;
	}
	/**
	 * 重置蓝牙名
	 * 
	 * @param deviceSn
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/resetBluetoothName")
	@ResponseBody
	public ResultInfo<Device> resetBluetoothName(String deviceNo, String bluetoothName) {
		ResultInfo<Device> resultInfo = new ResultInfo<Device>();
		Device device = deviceService.getDevice(deviceNo);
		if(device == null){
			resultInfo.setCode("3");
			return resultInfo;
		}
		try {
			String result = carStatusService.resetBluetoothName(device.getDeviceSn(), bluetoothName);
			log.info("重置蓝牙名："+result);
			if(result == null || result.trim().length() == 0){
				resultInfo.setCode(Constant.FAIL);
				return resultInfo;
			}
			Gson gson = new Gson();
			resultInfo = gson.fromJson(result, ResultInfo.class);
			if ("1".equals(resultInfo.getCode())) {
				Device deviceForUpdate = new Device();
				deviceForUpdate.setTerminalDeviceNo(deviceNo);
				deviceForUpdate.setBluetoothName(bluetoothName);
				deviceService.updateDevice(deviceForUpdate, getOperator());
			}
			return resultInfo;
		} catch (Exception e) {
			log.error(e);
			resultInfo.setCode("4");
			return resultInfo;
		}
	}

}
