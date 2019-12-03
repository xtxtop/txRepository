package cn.com.shopec.mgt.car.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import cn.com.shopec.common.Operator;
import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.common.constants.Constant;
import cn.com.shopec.common.utils.BinaryFileUtil;
import cn.com.shopec.common.utils.ECGeoCoordinateTransformUtil;
import cn.com.shopec.common.utils.HttpURLRequestUtil;
import cn.com.shopec.common.utils.JsonUtils;
import cn.com.shopec.common.utils.httpClient.HttpClientUtil;
import cn.com.shopec.common.utils.qrcode.QRcodeImageCreater;
import cn.com.shopec.core.car.common.CarConstant;
import cn.com.shopec.core.car.model.Car;
import cn.com.shopec.core.car.model.CarOnlineLog;
import cn.com.shopec.core.car.model.CarOwner;
import cn.com.shopec.core.car.model.CarStatus;
import cn.com.shopec.core.car.service.CarOnlineLogService;
import cn.com.shopec.core.car.service.CarOwnerService;
import cn.com.shopec.core.car.service.CarService;
import cn.com.shopec.core.car.service.CarStatusService;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.car.model.CarBrand;
import cn.com.shopec.core.car.model.CarSeries;
import cn.com.shopec.core.car.service.CarBrandService;
import cn.com.shopec.core.car.service.CarSeriesService;
import cn.com.shopec.core.device.model.Device;
import cn.com.shopec.core.device.service.DeviceService;
import cn.com.shopec.core.franchisee.model.Franchisee;
import cn.com.shopec.core.franchisee.service.FranchiseeService;
import cn.com.shopec.core.marketing.model.PricingRule;
import cn.com.shopec.core.marketing.service.PricingRuleService;
import cn.com.shopec.core.resource.model.Park;
import cn.com.shopec.core.resource.service.ParkService;
import cn.com.shopec.core.resource.service.ParkingSpaceService;
import cn.com.shopec.core.scheduling.service.WorkerService;
import cn.com.shopec.core.system.model.DataDictItem;
import cn.com.shopec.core.system.model.SysUser;
import cn.com.shopec.core.system.service.DataDictCatService;
import cn.com.shopec.core.system.service.DataDictItemService;
import cn.com.shopec.core.system.service.SysParamService;
import cn.com.shopec.core.system.service.SysUserService;
import cn.com.shopec.mgt.car.vo.CarNum;
import cn.com.shopec.mgt.common.BaseController;

@Controller
@RequestMapping("/car")
public class CarController extends BaseController {
	@Resource
	public CarService carService;
	@Resource
	private CarBrandService carBrandService;
	@Resource
	private CarSeriesService carSeriesService;
	@Resource
	public ParkService parkService;
	@Resource
	public CarStatusService carStatusService;
	@Resource
	private DataDictCatService dataDictCatService;
	@Resource
	private DataDictItemService dataDictItemService;
	@Resource
	private CarOwnerService carOwnerService;
	@Resource
	private DeviceService deviceService;
	@Resource
	private ParkingSpaceService parkingSpaceService;
	@Resource
	private PricingRuleService pricingRuleService;

	@Resource
	private SysUserService sysUserService;
	@Resource
	private WorkerService workerService;
	@Resource
	private CarOnlineLogService carOnlineLogService;
	@Resource
	private SysParamService sysParamService;
	@Resource
	private FranchiseeService franchiseeService;
	@Value("${res_path}")
	private String filePath;
	@Value("${share_img_path}")
	private String sharePath;
	@Value("${res_img_path}")
	private String resImgPath;

	@Override
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(true);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}

	/*
	 * 显示车辆列表页
	 */
	@RequestMapping("/toCarList")
	public String mainPage(String carPlateNo, String onlineStatus, Integer isIdle, ModelMap modelMap) {
		List<DataDictItem> carUpWhy = dataDictItemService.getDataDictItemListByCatCode("CAR_UPWHY");// 汽车上线理由
		List<DataDictItem> carDownWhy = dataDictItemService.getDataDictItemListByCatCode("CAR_DOWNWHY");// 汽车下线理由
		modelMap.put("carUpWhy", carUpWhy);
		modelMap.put("carDownWhy", carDownWhy);
		modelMap.put("carPlateNo", carPlateNo);
		modelMap.put("onlineStatus", onlineStatus);
		modelMap.put("isIdle", isIdle);
		return "car/car_list";
	}

	/*
	 * 显示车辆列表页
	 */
	@RequestMapping("/pageListCar")
	@ResponseBody
	public PageFinder<Car> pageListCar(@ModelAttribute("car") Car car, Query query) throws IOException {
		Query q = new Query(query.getPageNo(), query.getPageSize(), car);
		PageFinder<Car> resultInfo = carService.getCarPageList(q);
		if (resultInfo.getData() != null) {
			List<Car> carList = resultInfo.getData();
			if (carList.size() > 0) {
				for (int i = 0; i < carList.size(); i++) {
					Car carTemp = carList.get(i);
					// 二维码地址为空生成
					if (carTemp.getQrcodePicUrl() == null || "".equals(carTemp.getQrcodePicUrl())) {
						String resFilePathName = QRcodeImageCreater.generateQRCode(
								sharePath + "?carNo=" + carTemp.getCarNo(), 10, "jpg", resImgPath + "/",
								"qrcode", carTemp.getCarNo());
						car.setQrcodePicUrl(resFilePathName);
						Car carForUpdate = new Car();
						carForUpdate.setCarNo(carTemp.getCarNo());
						carForUpdate.setQrcodePicUrl(resFilePathName);
						carService.updateCar(carForUpdate, getOperator());
					}
				}
			}
		}
		return resultInfo;
	}

	/*
	 * 显示车辆详情
	 */
	@RequestMapping("/toCarView")
	public String toCarView(String carNo, ModelMap modelMap) throws Exception {
		Car car = carService.getCar(carNo);
		CarStatus carStatus = carStatusService.getCarStatus(car.getCarNo());
		if (carStatus != null) {
			if (carStatus.getLocationParkNo() != null && !carStatus.getLocationParkNo().equals("")) {
				Park park = parkService.getPark(carStatus.getLocationParkNo());// 场站所属
				modelMap.put("park", park);
			}
			Integer isOnline = carStatusService.getCarBrokenLineByCarNo(car.getCarNo());
			carStatus.setIsOnline(isOnline > 0 ? 2 : 1);
			if (carStatus.getLongitude() != null && carStatus.getLatitude() != null) {
				double[] position = ECGeoCoordinateTransformUtil.wgs84tobd09(carStatus.getLongitude(),
						carStatus.getLatitude());
				if (position != null && position.length == 2) {
					modelMap.put("dongitude", position[0]);
					modelMap.put("latitude", position[1]);
				}
			}
		}
		// 二维码地址为空生成
		if (car.getQrcodePicUrl() == null || "".equals(car.getQrcodePicUrl())) {
			String resFilePathName = QRcodeImageCreater.generateQRCode(sharePath + "?carNo=" + car.getCarNo(), 10,
					"jpg", resImgPath + "/", "qrcode", car.getCarNo());
			car.setQrcodePicUrl(resFilePathName);
			Car carForUpdate = new Car();
			carForUpdate.setCarNo(car.getCarNo());
			carForUpdate.setQrcodePicUrl(resFilePathName);
			carService.updateCar(carForUpdate, getOperator());
		}
		//获取该车辆加盟商
		Franchisee franchisee = franchiseeService.getFranchisee(car.getFranchiseeNo());
		if(franchisee != null){
			modelMap.put("franchisee", franchisee);
		}
		modelMap.put("car", car);
		modelMap.put("carStatus", carStatus);
		return "car/car_view";
	}

	/*
	 * 编辑车辆详情页面
	 */
	@RequestMapping("/toCarEdit")
	public String toCarEdit(String carNo, ModelMap modelMap) {
		if (carNo != null && carNo.length() != 0) {
			Car car = carService.getCar(carNo);
			CarStatus carStatus = carStatusService.getCarStatus(car.getCarNo());
			if (carStatus != null) {
				if (carStatus.getLocationParkNo() != null && !carStatus.getLocationParkNo().equals("")) {
					Park park = parkService.getPark(carStatus.getLocationParkNo());// 场站所属
					modelMap.put("park", park);
				}
			}
			Device device = deviceService.getDevice(car.getDeviceId());
			if (device != null) {
				car.setDeviceStatus(device.getDeviceStatus());
				car.setDeviceSn(device.getDeviceSn());
			}
			List<DataDictItem> cities = dataDictItemService.getDataDictItemListByCatCode("CITY");// 城市
			//List<DataDictItem> carBrands = dataDictItemService.getDataDictItemListByCatCode("CAR_BRAND");// 汽车品牌
			//List<DataDictItem> carmodels = dataDictItemService.getDataDictItemListByCatCode("CAR_MODEL");// 汽车型号
			
			
			Query qb = new Query();
			List<CarBrand> carBrands=carBrandService.getCarBrandList(qb);//汽车品牌
			CarSeries carSeries = new CarSeries();
			carSeries.setCarBrandId(car.getCarBrandId());
			Query qbcs= new Query(carSeries);
			List<CarSeries> carmodels=carSeriesService.getCarSeriesList(qbcs);//汽车型号
			List<DataDictItem> carcolors = dataDictItemService.getDataDictItemListByCatCode("CAR_COLOR");// 汽车颜色
			// 查找 场站
			Park pk =new Park();
			pk.setIsAvailable(1);
			pk.setIsView(1);
			pk.setIsPublic(1);
			pk.setCityId(car.getCityId());
			Query qq = new Query(pk);
			List<Park> parks = parkService.getParkList(qq);
			if (parks != null) {
				modelMap.put("parks", parks);
			}
			modelMap.put("cities", cities);
			modelMap.put("carBrands", carBrands);
			modelMap.put("carmodels", carmodels);
			modelMap.put("car", car);
			modelMap.put("carStatus", carStatus);
			modelMap.put("carcolors", carcolors);
			//获取该车辆加盟商
			Franchisee franchisee = franchiseeService.getFranchisee(car.getFranchiseeNo());
			if(franchisee != null){
				modelMap.put("franchisee", franchisee);
			}
		}
		Query q = new Query();
		CarOwner carOwner = new CarOwner();
		carOwner.setIsAvailable(1);// 可用
		carOwner.setCencorStatus(1);// 已审核
		q.setQ(carOwner);
		List<CarOwner> carOwnerList = carOwnerService.getCarOwnerList(q);
		modelMap.put("carOwnerList", carOwnerList);
		return "car/car_edit";
	}

	/*
	 * 查询car对象
	 */
	@RequestMapping("/toCar")
	@ResponseBody
	public Car toCar(String carNo) {
		Car car = null;
		if (carNo != null && carNo.length() != 0) {
			car = carService.getCar(carNo);
		}
		return car;
	}

	@RequestMapping("/toCarOneLineList")
	public String toCarOneLineList(String carNo, ModelMap model) {
		List<DataDictItem> carUpWhy = dataDictItemService.getDataDictItemListByCatCode("CAR_UPWHY");// 汽车上线理由
		List<DataDictItem> carDownWhy = dataDictItemService.getDataDictItemListByCatCode("CAR_DOWNWHY");// 汽车下线理由
		model.put("carUpWhy", carUpWhy);
		model.put("carDownWhy", carDownWhy);
		model.addAttribute("carNo", carNo);
		return "car/car_online_list";
	}

	/*
	 * 提交车辆详情
	 */
	@RequestMapping("/editCar")
	@ResponseBody
	public ResultInfo<Car> editCar(@ModelAttribute("car") Car car) {
		ResultInfo<Car> resultInfo = new ResultInfo<Car>();
		if (car.getCarNo() != null && car.getCarNo().length() != 0) {
			resultInfo = carService.updateCar(car, getOperator());
			// 车辆修改成功 修改终端设备的城市和车辆的城市 保持一致
			if (resultInfo.getCode() == "1") {
				Device device = deviceService.getDevice(car.getDeviceId());
				if (device != null) {
					Device de = new Device();
					de.setTerminalDeviceNo(device.getTerminalDeviceNo());
					de.setCityId(car.getCityId());
					deviceService.updateDevice(de, getOperator());
				}
			}  
			
			if (resultInfo.getCode() == "1") {//根据车牌号 找到所有的 终端和提交的对比 不是一块的 搞成null
				Device device=new Device();
				device.setCarPlateNo(car.getCarPlateNo());
				List<Device> devices = deviceService.getDeviceList(new Query(device));
				if (devices != null) {
					for (Device d : devices) {
						if(!d.getDeviceSn().equals(car.getDeviceSn())){
							d.setCarNo("");
							d.setCarPlateNo("");
							d.setBindingTime(null);
							deviceService.updateDeviceCar(d);
						}
					}
					
				}
			}  
			
			
		}
		return resultInfo;
	}

	/*
	 * 上下线提交
	 */
	@RequestMapping("/editCarTest")
	@ResponseBody
	public ResultInfo<Car> editCarTest(@ModelAttribute("car") Car car) {
		ResultInfo<Car> resultInfo = new ResultInfo<Car>();
		if (car.getCarNo() != null && car.getCarNo().length() != 0 && car.getOnlineStatus() != null) {
			if (car.getOnlineStatus().intValue() == 1) {// 上线才需校验是否绑定终端
				Car carFromDB = carService.getCar(car.getCarNo());
				if (carFromDB != null) {
					if (carFromDB.getDeviceId() == null || carFromDB.getDeviceId().equals("")) {
						resultInfo.setCode("0");
						resultInfo.setMsg("无法上线，该车辆未绑定终端设备！");
						return resultInfo;
					}
				} else {
					resultInfo.setCode("0");
					resultInfo.setMsg("车辆不存在！");
					return resultInfo;
				}
			} else {
				CarStatus carStaus = carStatusService.getCarStatus(car.getCarNo());
				if (carStaus.getUserageStatus() != 0) {
					resultInfo.setCode("0");
					resultInfo.setMsg("车辆不是空闲不能下线！");
					return resultInfo;
				}
			}
			SysUser sysUser = getLoginSysUser();
			if (sysUser != null) {
				car.setWorkerNo(sysUser.getUserId());
				car.setWorkerName(sysUser.getUserName());
			}
			car.setCarTestType(1);
			resultInfo = carService.updateCar(car, getOperator());
			// 向 车辆上下线 日志表添加数据
			if (resultInfo.getCode() == "1") {
				Car cars = carService.getCar(resultInfo.getData().getCarNo());
				if (cars != null) {
					CarOnlineLog carOnlineLog = new CarOnlineLog();
					carOnlineLog.setCarNo(resultInfo.getData().getCarNo());
					carOnlineLog.setCarPlateNo(cars.getCarPlateNo());
					carOnlineLog.setOpType(resultInfo.getData().getOnlineStatus());
					carOnlineLog.setMemo(resultInfo.getData().getOnOffLineReason());
					carOnlineLog.setUpdownWhy(resultInfo.getData().getUpdownWhy());
					Operator operator = new Operator();
					operator.setOperatorId(resultInfo.getData().getOperatorId());
					operator.setOperatorType(resultInfo.getData().getOperatorType());
					carOnlineLogService.addCarOnlineLog(carOnlineLog, operator);
				}

			}
		}
		return resultInfo;
	}

	// 车辆上下线日志列表
	@RequestMapping("/pageListCarOnline")
	@ResponseBody
	public PageFinder<CarOnlineLog> pageListCarOnline(@ModelAttribute("CarOnlineLog") CarOnlineLog carOnlineLog,
			Query query) {
		Query q = new Query(query.getPageNo(), query.getPageSize(), carOnlineLog);
		return carOnlineLogService.getCarOnlineLogPagedLists(q);
	}

	/*
	 * 提交车辆详情
	 */
	@RequestMapping("/addCar")
	@ResponseBody
	public ResultInfo<Car> addCar(@ModelAttribute("car") Car car, String franchiseeNo) throws IOException {
		ResultInfo<Car> resultInfo = new ResultInfo<Car>();
		ResultInfo<Integer> result = this.getCarNum();
//		SysParam sp=sysParamService.getByParamKey(CarConstant.CAR_NUM);
		String str = BinaryFileUtil.dataInputStream(filePath);
		CarNum carNum = JsonUtils.parse2Object(str, CarNum.class);
		if(carNum != null && !("").equals(carNum.getNumLimit())){
			 int a= Integer.parseInt(carNum.getNumLimit());
			 if((a>0 && result != null && result.getCode().equals(Constant.SECCUESS) && result.getData().intValue() <= a) || a==0){
				Car car1 =carService.getCarByPlateNo(car.getCarPlateNo());
		if (car1 != null) {
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg("车牌号重复！");
		} else {
			// 改为添加车辆时，不判断场站车位是否充足，20170103,lule
			// //判断该场站的车位数是否已满
			// Car car2=new Car();
			// car2.setLocationParkNo(car.getLocationParkNo());
			// List<Car> cars=carService.getCarList(new Query(car2));
			// ParkingSpace ps = new ParkingSpace();
			// ps.setParkNo(car.getLocationParkNo());
			// List<ParkingSpace> parkingSpaces =
			// parkingSpaceService.getParkingSpaceList(new Query(ps));
			// if(cars.size()>parkingSpaces.size()){
			// resultInfo.setCode(Constant.FAIL);
			// resultInfo.setMsg("该场站车位已满，请重新选择场站！");
			// }else{
			if (car.getMileage() != null) {
				car.setMileage(car.getMileage());
			} else {
				car.setMileage(0d);
			}

			if (car.getPower() != null) {
				car.setPower(car.getPower());
			} else {
				car.setPower(0d);
			}

			if (car.getRangeMileage() != null) {
				car.setRangeMileage(car.getRangeMileage());
			} else {
				car.setRangeMileage(0d);
			}
			if(StringUtils.isNotBlank(franchiseeNo)){
				car.setFranchiseeNo(franchiseeNo);
			}
			resultInfo = carService.addCar(car, getOperator());
			// 车辆增加成功 修改终端设备的城市和车辆的城市 保持一致
			if (resultInfo.getCode() == "1") {
				Device device = deviceService.getDevice(car.getDeviceId());
				if (device != null) {
					Device de = new Device();
					de.setTerminalDeviceNo(device.getTerminalDeviceNo());
					de.setCityId(car.getCityId());
					deviceService.updateDevice(de, getOperator());
				}
			}
			// }
		}

		 }else{
				resultInfo.setCode(Constant.FAIL);
				resultInfo.setMsg("您的车辆已达上限，请升级后进行操作！");
			}
		}else{
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg("您没有配置汽车添加上限参数,请到系统参数里面去配置！");
		}
			
		return resultInfo;
	}

	/*
	 * 获取当前全部的车辆
	 */
	@RequestMapping("/getCarNum")
	@ResponseBody
	public ResultInfo<Integer> getCarNum() {
		ResultInfo<Integer> result = new ResultInfo<Integer>();
		try {
			List<Car> cars = carService.getCarList(new Query());
			if (null != cars) {
				result.setCode(Constant.SECCUESS);
				result.setMsg("查询成功");
				result.setData(cars.size());
			} else {
				result.setCode(Constant.SECCUESS);
				result.setMsg("查询成功");
				result.setData(0);
			}
		} catch (Exception e) {
			result.setCode(Constant.FAIL);
			result.setMsg("程序异常，请稍后再试");
		}
		return result;
	}

	/*
	 * 显示车辆增加页
	 */
	@RequestMapping("/toCarAdd")
	public String toCarAdd(ModelMap modelMap) {
		List<DataDictItem> cities = dataDictItemService.getDataDictItemListByCatCode("CITY");// 城市
		if(cities != null && cities.size()>0){//根据城市选择 对应的场站
			Park pk=new Park();
			pk.setCityId(cities.get(0).getDataDictItemId());
			pk.setIsAvailable(1);
			pk.setIsView(1);
			pk.setIsDeleted(0);
			Query query = new Query(pk);
			List<Park> listParks = parkService.getParkList(query);
			modelMap.put("listParks", listParks);
		}
		Query qb = new Query();
		List<CarBrand> carBrands=carBrandService.getCarBrandList(qb);//汽车品牌
		if(carBrands != null && carBrands.size()>0){
			CarSeries cs= new CarSeries();
			cs.setCarBrandId(carBrands.get(0).getCarBrandId());
			Query qcs= new Query(cs);
			List<CarSeries> carSeries = carSeriesService.getCarSeriesList(qcs);
			if(carSeries != null && carSeries.size()>0){
				modelMap.put("carmodels", carSeries);
				CarSeries css=carSeries.get(0);
				modelMap.put("dad", css);
				
			}
		}
		List<DataDictItem> carcolors = dataDictItemService.getDataDictItemListByCatCode("CAR_COLOR");// 汽车颜色
		
		modelMap.put("cities", cities);
		modelMap.put("carBrands", carBrands);
		modelMap.put("carcolors", carcolors);
		Query q = new Query();
		CarOwner carOwner = new CarOwner();
		carOwner.setIsAvailable(1);// 可用
		carOwner.setCencorStatus(1);// 已审核
		q.setQ(carOwner);
		List<CarOwner> carOwnerList = carOwnerService.getCarOwnerList(q);
		modelMap.put("carOwnerList", carOwnerList);
		return "car/car_add";
	}

	// 解绑终端
	@RequestMapping("/editCarDevice")
	@ResponseBody
	public ResultInfo<Car> editCarDevice(@ModelAttribute("car") Car car) {
		ResultInfo<Car> resultInfo = new ResultInfo<Car>();
		if (car.getCarNo() != null && car.getCarNo().length() != 0) {
			resultInfo = carService.editCarDevice(car, getOperator());
		}
		return resultInfo;
	}

	// 判断车型是否有计费规则
	@RequestMapping("/carRuleExsit")
	@ResponseBody
	public ResultInfo<String> carRuleExsit(@ModelAttribute("car") Car car) {
		ResultInfo<String> resultInfo = new ResultInfo<String>();
		if (car.getCarNo() != null && car.getCarNo().length() != 0) {
			car = carService.getCar(car.getCarNo());
			PricingRule pr = pricingRuleService.getPricingRuleUseByCar(car.getCarBrandName(), car.getCarModelName());
			if (pr != null) {
				resultInfo.setCode(Constant.SECCUESS);
			} else {
				resultInfo.setCode(Constant.FAIL);
				resultInfo.setMsg(CarConstant.noCarRule);
			}
		} else {
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(CarConstant.fail_msg);
		}
		return resultInfo;
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
		String path = request.getRealPath("/") + "download" + File.separator + "car.xls";
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
	//列表单个二维码下载
	@RequestMapping("downloadCarQrcode")
	public void downloadCarQrcode(HttpServletRequest request, HttpServletResponse response, String carNo) throws Exception {
		Car car = carService.getCar(carNo);
		if (car!=null) {
			String qrCodeUrl = car.getQrcodePicUrl();
			qrCodeUrl = resImgPath + "/" + qrCodeUrl;
			String downloadFilename = URLEncoder.encode(car.getCarPlateNo()+qrCodeUrl.substring(qrCodeUrl.indexOf(".")), "UTF-8");// 转换中文否则可能会产生乱码
			response.setContentType("application/octet-stream");// 指明response的返回对象是文件流
			response.setCharacterEncoding("UTF-8");
			// 设置Content-Disposition
			if (request.getHeader("User-Agent").toLowerCase().indexOf("firefox") > -1){//火狐
				response.setHeader("Content-Disposition", "attachment; filename*=UTF-8''" + downloadFilename);  // 设置在下载框默认显示的文件名
			} else {              
				response.setHeader("Content-Disposition", "attachment;filename=" + downloadFilename); // 设置在下载框默认显示的文件名
			}  
			File ff = new File(qrCodeUrl);
			
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
	}
	// 下载二维码
	/**
	 * carPlateNo userageStatus
	 * 
	 * @param request
	 * @param response
	 * @param car
	 * @throws Exception
	 */
	@RequestMapping("downloadCarQrcodes")
	public void downloadCarQrcodes(HttpServletRequest request, HttpServletResponse response, Car car) throws Exception {
		List<Car> carList = carService.getCarList(new Query(car));
		String[] files = new String[carList.size()];
		Map<String, String> map = new HashMap<>();
		for (int i = 0; i < carList.size(); i++) {
			Car carTemp = carList.get(i);
			String qrCodeUrl = carList.get(i).getQrcodePicUrl();
			String suffix;
			if (qrCodeUrl != null && !"".equals(qrCodeUrl)) {
				suffix = qrCodeUrl.substring(qrCodeUrl.indexOf("."));
				files[i] = resImgPath + "/" + "qrcode/" + carTemp.getCarNo()+suffix;
			} else {
				qrCodeUrl = QRcodeImageCreater.generateQRCode(sharePath + "?carNo=" + carTemp.getCarNo(), 10,"jpg", resImgPath + "/", "qrcode", carTemp.getCarNo());
				suffix = qrCodeUrl.substring(qrCodeUrl.indexOf("."));
				files[i] = resImgPath + "/" + "qrcode/" + carTemp.getCarNo()+suffix;
			}
			map.put(files[i], carTemp.getCarPlateNo()+suffix);
		}
		try {
			String downloadFilename = "车辆二维码.zip";// 文件的名称
			downloadFilename = URLEncoder.encode(downloadFilename, "UTF-8");// 转换中文否则可能会产生乱码
			response.setContentType("application/octet-stream");// 指明response的返回对象是文件流
			if (request.getHeader("User-Agent").toLowerCase().indexOf("firefox") > -1){//火狐
	            response.setHeader("Content-Disposition", "attachment; filename*=UTF-8''" + downloadFilename);  // 设置在下载框默认显示的文件名
	        } else {              
	        	response.setHeader("Content-Disposition", "attachment;filename=" + downloadFilename); // 设置在下载框默认显示的文件名
	        }  
			ZipOutputStream zos = new ZipOutputStream(response.getOutputStream());
			for (int i = 0; i < files.length; i++) {
				String fileName = map.get(files[i]);
				zos.putNextEntry(new ZipEntry(fileName));
				FileInputStream fis = new FileInputStream(new File(files[i]));
				byte[] buffer = new byte[1024];
				int r = 0;
				while ((r = fis.read(buffer)) != -1) {
					zos.write(buffer, 0, r);
				}
				fis.close();
			}
			zos.flush();
			zos.close();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 批量导入车辆信息
	 * 
	 * @param file
	 * @return
	 * @throws Exception
	 */

	@RequestMapping("expotCarAdd")
	@ResponseBody
	public ResultInfo<Car> expotCarAdd(@RequestParam(value = "file", required = false) MultipartFile file)
			throws Exception {
		ResultInfo<Car> resultInfo = new ResultInfo<Car>();
		if (file != null) {
			try {
				resultInfo = carService.expotCarAdd(file, getOperator());
			} catch (Exception e) {
				resultInfo.setCode(Constant.FAIL);
				resultInfo.setMsg(e.getMessage());
			}
		} else {
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg("请选择文件！");
		}

		return resultInfo;
	}

	/**
	 * 根据城市匹配场站
	 */
	@RequestMapping("getCityPark")
	@ResponseBody
	public ResultInfo<List<Park>> getCityPark(String cityId) {
		ResultInfo<List<Park>> res = new ResultInfo<List<Park>>();
		Park pk=new Park();
		pk.setCityId(cityId);
		pk.setIsAvailable(1);
		pk.setIsView(1);
		pk.setIsDeleted(0);
		Query query = new Query(pk);
		List<Park> listParks = parkService.getParkList(query);
		res.setCode(Constant.SECCUESS);
		res.setData(listParks);
		return res;
	}
	
	
	
	/**
	 * 根据车辆品牌搜索对应的车型
	 */
	@RequestMapping("getCarModleByBrand")
	@ResponseBody
	public ResultInfo<List<CarSeries>> getCarModleByBrand(String brandId) {
		ResultInfo<List<CarSeries>> res = new ResultInfo<List<CarSeries>>();
		CarSeries cs= new CarSeries();
		cs.setCarBrandId(brandId);
		Query qcs= new Query(cs);
		List<CarSeries> items = carSeriesService.getCarSeriesList(qcs);
		res.setCode(Constant.SECCUESS);
		res.setData(items);
		return res;
	}

	/**
	 * 根据车辆 型号 搜索对应的车型
	 */
	@RequestMapping("getCarModle")
	@ResponseBody
	public ResultInfo<CarSeries> getCarModle(String carSeriesId) {
		ResultInfo<CarSeries> res = new ResultInfo<CarSeries>();
		CarSeries cs= carSeriesService.getCarSeries(carSeriesId);
		if (cs != null ) {
			res.setCode(Constant.SECCUESS);
			res.setData(cs);
			return res;
		} else {
			res.setCode(Constant.FAIL);
			return res;
		}

	}

	/**
	 * 修改蓝牙名称
	 */
	@RequestMapping("changeBluetooth")
	@ResponseBody
	public String changeBluetooth(String ip, String prefix, String carPlate) {

		if (null == ip || "".equals(ip.trim()) || null == prefix || "".equals(prefix.trim())) {
			return "param error!";
		}

		StringBuffer sb = new StringBuffer();
		List<Car> listCar = null;
		if (null == carPlate || "".equals(carPlate.trim())) {
			listCar = carService.getCarList(new Query());
		} else {
			listCar = new ArrayList<Car>();
			Car car = carService.getCarByPlateNo(carPlate);
			listCar.add(car);
		}
		String url = "http://" + ip + "/fs-device-api/sendRawCmd.jsp";
		for (int i = 0; i < listCar.size(); i++) {
			try {
				Map<String, Object> params = new HashMap<String, Object>();

				String deviceId = listCar.get(i).getDeviceId();
				Device device = deviceService.getDevice(deviceId);
				if (null != device) {
					String deviceSn = device.getDeviceSn();
					deviceSn = null == deviceSn ? "" : deviceSn.trim();
					String plateStr = listCar.get(i).getCarPlateNo();
					String xxx = plateStr.substring(2, plateStr.length());

					// String msg = "AT+SCBTNAME="+prefix+"_"+xxx+"_";
					// params.put("deviceSn",deviceSn);
					// params.put("msg", msg);
					// sb.append(deviceSn + "&nbsp;&nbsp;");
					// sb.append(msg + "&nbsp;&nbsp;");
					// String resultStr = HttpClientUtil.post(url, params);

					String msg = "AT%2BSCBTNAME=" + prefix + "_" + xxx + "_"; // 原报文命令为
					sb.append(deviceSn + "&nbsp;&nbsp;");
					sb.append(msg.replace("%2B", "+") + "&nbsp;&nbsp;");

					String urlStr = url + "?deviceSn=" + deviceSn + "&msg=" + msg;
					String resultStr = HttpURLRequestUtil.doMsgGet(urlStr);

					resultStr = null == resultStr ? "" : resultStr.trim();
					sb.append(resultStr.replaceAll("\r", "").replace("\n", "").trim() + "<br/>");
				}

			} catch (Exception e) {
				sb.append("false<br/>");
				e.printStackTrace();
			}
		}
		return sb.toString();
	}

}
