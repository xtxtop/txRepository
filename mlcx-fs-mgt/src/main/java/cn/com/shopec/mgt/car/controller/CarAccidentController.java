package cn.com.shopec.mgt.car.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.common.constants.Constant;
import cn.com.shopec.core.car.model.Car;
import cn.com.shopec.core.car.model.CarAccident;
import cn.com.shopec.core.car.model.CarSeries;
import cn.com.shopec.core.car.service.CarAccidentService;
import cn.com.shopec.core.car.service.CarSeriesService;
import cn.com.shopec.core.car.service.CarService;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.order.model.Order;
import cn.com.shopec.core.order.service.OrderService;
import cn.com.shopec.core.scheduling.model.WorkerOrder;
import cn.com.shopec.core.scheduling.service.WorkerOrderService;
import cn.com.shopec.core.system.model.DataDictItem;
import cn.com.shopec.core.system.service.DataDictItemService;
import cn.com.shopec.mgt.common.BaseController;
/**
 * 
 * @author machao
 *
 */
@Controller
@RequestMapping("carAccident")
public class CarAccidentController extends BaseController{
	@Resource
	private CarAccidentService carAccidentService;
	@Resource
	private DataDictItemService dataDictItemService;
	@Resource
	private CarService carService;
	@Resource
	private OrderService orderService;
	@Resource
	private WorkerOrderService workerOrderService;
	@Resource
	private CarSeriesService  carSeriesService;
	
	
	/*
	 * 显示事故列表页
	 */
	@RequestMapping("/toCarAccidentList")
	public String toCarAccidentList(String documentNo, ModelMap modelMap) {
		modelMap.put("documentNo", documentNo);
		return "car/car_accident_list";
	}
	/*
	 * 显示事故列表分页
	 */
	@RequestMapping("/pageListCarAccidentList")
	@ResponseBody
	public PageFinder<CarAccident> pageListCarAccidentList(@ModelAttribute("CarAccident") CarAccident carAccident,Query query) throws ParseException {
		Query q = new Query(query.getPageNo(),query.getPageSize(),carAccident);
		return carAccidentService.getCarAccidentPagedList(q);
	}
	/**
	 * 事故详情
	 * 
	 * @param accidentId
	 * @return
	 */
	@RequestMapping("toCarAccidentView")
	public String toCarAccidentView(@ModelAttribute("accidentId") String accidentId, Model model) {
		CarAccident carAccident = carAccidentService.getCarAccident(accidentId);
		model.addAttribute("carAccident", carAccident);
		return "car/car_accident_view";
	}
	/**
	 * 事故编辑页面
	 * 
	 * @param accidentId
	 * @return
	 */
	@RequestMapping("toUpdateCarAccident")
	public String toUpdateCarAccident(@ModelAttribute("accidentId") String accidentId, Model model) {
		List<DataDictItem> cities = dataDictItemService.getDataDictItemListByCatCode("CITY");
		Query qbcs= new Query();
		List<CarSeries> carmodels=carSeriesService.getCarSeriesList(qbcs);//汽车型号
		model.addAttribute("cities", cities);
		model.addAttribute("carModels", carmodels);
		CarAccident carAccident = carAccidentService.getCarAccident(accidentId);
		model.addAttribute("carAccident", carAccident);
		return "car/car_accident_edit";
	}

	/**
	 * 事故修改
	 * 
	 * @param member
	 * @return
	 */
	@RequestMapping("updateCarAccident")
	@ResponseBody
	public ResultInfo<CarAccident> updateCarAccident(@ModelAttribute("carAccident")CarAccident carAccident) {
		System.out.println(carAccident.getRecordAccidentTime());
		return  carAccidentService.updateCarAccident(carAccident, getOperator());
	}
	
	/**
	 * 事故添加页面
	 * 
	 * @return
	 */
	@RequestMapping("toAddCarAccident")
	public String toAddCarAccident(Model model) {
		List<DataDictItem> cities = dataDictItemService.getDataDictItemListByCatCode("CITY");
//		List<DataDictItem> carModels = dataDictItemService.getDataDictItemListByCatCode("CAR_MODEL");
		Query qbcs= new Query();
		List<CarSeries> carmodels=carSeriesService.getCarSeriesList(qbcs);//汽车型号
		model.addAttribute("cities", cities);
		model.addAttribute("carModels", carmodels);
		return "car/car_accident_add";
	}
	/**
	 * 事故添加搜索
	 * 
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping("toSearchCarAccident")
	@ResponseBody
	public ResultInfo<CarAccident> toSearchCarAccident(String carPlateNo,String recordAccidentTime) throws ParseException {
		ResultInfo<CarAccident> resultInfo = new ResultInfo<CarAccident>();
		Order order = new Order();
		order.setCarPlateNo(carPlateNo);
		order.setStartBillingTimeEnd(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(recordAccidentTime));
		order.setFinishTimeStart(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(recordAccidentTime));
		Query q = new Query(order);
		List<Order> orderList = orderService.getOrderList(q);
		CarAccident carAccident=new CarAccident();
		if(orderList!=null&&orderList.size()>0){
			carAccident.setCarNo(orderList.get(0).getCarNo());
			carAccident.setCityId(orderList.get(0).getCityId());
			carAccident.setCityName(orderList.get(0).getCityName());
			carAccident.setCarModelId(orderList.get(0).getCarModelId());
			carAccident.setCarModelName(orderList.get(0).getCarModelName());
			carAccident.setDocumentNo(orderList.get(0).getOrderNo());
			carAccident.setDriverId(orderList.get(0).getMemberNo());
			carAccident.setDriverName(orderList.get(0).getMemberName());
			carAccident.setUseCarType(1);
			resultInfo.setCode("1");
			resultInfo.setData(carAccident);
		}else{
			WorkerOrder wo = new WorkerOrder();
			wo.setCarPlateNo(carAccident.getCarPlateNo());
			wo.setStartTimeEnd(carAccident.getRecordAccidentTime());
			wo.setFinishTimeStart(carAccident.getRecordAccidentTime());
			Query q1 = new Query(wo);
			List<WorkerOrder> wokerOrderList = workerOrderService.getWorkerOrderList(q1);
			if(wokerOrderList!=null&&wokerOrderList.size()>0){
				carAccident.setDocumentNo(wokerOrderList.get(0).getWorkerOrderNo());
				carAccident.setDriverId(wokerOrderList.get(0).getWorkerId());
				carAccident.setDriverName(wokerOrderList.get(0).getWorkerName());
				carAccident.setUseCarType(2);
				Car car = carService.getCarByPlateNo(carPlateNo);
				carAccident.setCarModelId(car.getCarModelId());
				carAccident.setCarModelName(car.getCarModelName());
				carAccident.setCityId(car.getCityId());
				carAccident.setCityName(car.getCityName());
				resultInfo.setCode("1");
				resultInfo.setData(carAccident);
			}else{
				resultInfo.setCode("0");
				resultInfo.setMsg("没能成功匹配到相应的订单信息，请手动输入！");
			}
		}
		return resultInfo;
	}
	/**
	 * 事故添加
	 * 
	 * @param device
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping("addCarAccident")
	@ResponseBody
	public ResultInfo<CarAccident> addCarAccident(@ModelAttribute("carAccident") CarAccident carAccident,String recordAccidentTimes) throws ParseException {
		ResultInfo<CarAccident> resultInfo = new ResultInfo<CarAccident>();
    	Car car = carService.getCarByPlateNo(carAccident.getCarPlateNo());
    	if(car!=null){
    		carAccident.setCarNo(car.getCarNo());
    		carAccident.setRecordAccidentTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(recordAccidentTimes));
    		resultInfo =carAccidentService.addCarAccident(carAccident, getOperator());
    		if(resultInfo.getCode().equals("1") && resultInfo.getData().getDocumentNo() != null && !"".equals(resultInfo.getData().getDocumentNo()) ){
    			Order order=orderService.getOrder(resultInfo.getData().getDocumentNo());
    			if(order != null ){
    				Order orders= new Order();
    				orders.setIsAccident(1);
    				orders.setRecordAccidentTime(resultInfo.getData().getRecordAccidentTime());
    				orders.setOrderNo(order.getOrderNo());
    				orderService.updateOrder(orders, getOperator());
    			}
    		}
    	}else{
    		resultInfo.setCode(Constant.FAIL);
    		resultInfo.setMsg("所输入的车牌号不存在");
    	}
		return resultInfo;
	}
	/**
	 * 订单详情中事故添加
	 * 
	 * @param device
	 * @return
	 */
	@RequestMapping("addOrderCarAccident")
	@ResponseBody
	public ResultInfo<CarAccident> addOrderCarAccident(@ModelAttribute("carAccident") CarAccident carAccident) {
		ResultInfo<CarAccident> resultInfo = new ResultInfo<CarAccident>();
		Query query=new Query();
		query.setQ(carAccident);
		List<CarAccident> carAccidents=carAccidentService.getCarAccidentList(query);
    	if(carAccidents.size()>0){
    		resultInfo.setCode(Constant.FAIL);
    		resultInfo.setMsg("此订单已记录过事故！");
    	}else{
    		resultInfo =carAccidentService.addCarAccident(carAccident, getOperator());
    	}
		return resultInfo;
	}

}
