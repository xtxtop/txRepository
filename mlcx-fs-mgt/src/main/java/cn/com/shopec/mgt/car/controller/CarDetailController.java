package cn.com.shopec.mgt.car.controller;



import java.io.IOException;

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

import cn.com.shopec.core.car.model.Car;

import cn.com.shopec.core.car.service.CarOnlineLogService;
import cn.com.shopec.core.car.service.CarOwnerService;
import cn.com.shopec.core.car.service.CarService;
import cn.com.shopec.core.car.service.CarStatusService;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.car.model.CarBrand;
import cn.com.shopec.core.car.model.CarDetailMainData;
import cn.com.shopec.core.car.model.CarSeries;
import cn.com.shopec.core.car.service.CarBrandService;
import cn.com.shopec.core.car.service.CarDetailMainDataService;
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
@RequestMapping("/carDetail")
public class CarDetailController extends BaseController {
	@Resource
	public CarDetailMainDataService carDetailMainDataService;




	/*
	 * 显示车辆列表页
	 */
	@RequestMapping("/toCarDetailList")
	public String toCarDetailList(ModelMap modelMap) {
		return "car/car_detail_list";
	}

	/*
	 * 显示车辆列表页
	 */
	@RequestMapping("/pageListCarDetail")
	@ResponseBody
	public PageFinder<CarDetailMainData> pageListCar(@ModelAttribute("carDetailMainData") CarDetailMainData carDetailMainData, Query query) throws IOException {
		Query q = new Query(query.getPageNo(),query.getPageSize(),carDetailMainData);
		return carDetailMainDataService.getCarDetailMainDataPagedList(q);
	}

}
