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
import cn.com.shopec.core.car.model.CarIllegal;
import cn.com.shopec.core.car.service.CarIllegalService;
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
@RequestMapping("carIllegal")
public class CarIllegalController extends BaseController{
	@Resource
	private CarIllegalService carIllegalService;
	@Resource
	private DataDictItemService dataDictItemService;
	@Resource
	private CarService carService;
	@Resource
	private OrderService orderService;
	@Resource
	private WorkerOrderService workerOrderService;
	/*
	 * 显示违章列表页
	 */
	@RequestMapping("/toCarIllegalList")
	public String toCarIllegalList(String documentNo, ModelMap modelMap) {
		modelMap.put("documentNo", documentNo);
		return "car/car_illegal_list";
	}
	/*
	 * 会员页面跳转显示违章列表页
	 */
	@RequestMapping("/getillegalNum")
	public String toCarIllegalList(@ModelAttribute("driverId") String driverId, Model model) {
		CarIllegal c=new CarIllegal();
		c.setDriverId(driverId);
		Query q = new Query(c);
		List<CarIllegal> cs=carIllegalService.getCarIllegalList(q);
		if(cs != null && cs.size()>0){
			model.addAttribute("driverName", cs.get(0).getDriverName());
			model.addAttribute("useCarType", cs.get(0).getUseCarType());
		}else{
			model.addAttribute("driverId", driverId);
		}
		
		return "car/car_illegal_list";
	}
	/*
	 * 显示违章列表分页
	 */
	@RequestMapping("/pageListCarIllegal")
	@ResponseBody
	public PageFinder<CarIllegal> pageListCarIllegal(@ModelAttribute("CarIllegal") CarIllegal carIllegal,Query query) throws ParseException {
		Query q = new Query(query.getPageNo(),query.getPageSize(),carIllegal);
		return carIllegalService.getCarIllegalPagedList(q);
	}
	/**
	 * 违章详情
	 * 
	 * @param illegalId
	 * @return
	 */
	@RequestMapping("toCarIllegalView")
	public String toMemberView(@ModelAttribute("illegalId") String illegalId, Model model) {
		CarIllegal carIllegal = carIllegalService.getCarIllegal(illegalId);
		model.addAttribute("carIllegal", carIllegal);
		return "car/car_illegal_view";
	}
	/**
	 * 违章编辑页面
	 * 
	 * @param illegalId
	 * @return
	 */
	@RequestMapping("toUpdateCarIllegal")
	public String toUpdateCarIllegal(@ModelAttribute("illegalId") String illegalId, Model model) {
		List<DataDictItem> cities = dataDictItemService.getDataDictItemListByCatCode("CITY");
		List<DataDictItem> carModels = dataDictItemService.getDataDictItemListByCatCode("CAR_MODEL");
		model.addAttribute("cities", cities);
		model.addAttribute("carModels", carModels);
		CarIllegal carIllegal = carIllegalService.getCarIllegal(illegalId);
		model.addAttribute("carIllegal",carIllegal);
		return "car/car_illegal_edit";
	}

	/**
	 * 违章修改
	 * 
	 * @param member
	 * @return
	 */
	@RequestMapping("updateCarIllegal")
	@ResponseBody
	public ResultInfo<CarIllegal> updateCarIllegal(@ModelAttribute("CarIllegal")CarIllegal carIllegal) {
		return  carIllegalService.updateCarIllegal(carIllegal, getOperator());
	}
	
	/**
	 * 违章添加页面
	 * 
	 * @return
	 */
	@RequestMapping("toAddCarIllegal")
	public String toAddCarIllegal(Model model) {
		List<DataDictItem> cities = dataDictItemService.getDataDictItemListByCatCode("CITY");
		List<DataDictItem> carModels = dataDictItemService.getDataDictItemListByCatCode("CAR_MODEL");
		model.addAttribute("cities", cities);
		model.addAttribute("carModels", carModels);
		return "car/car_illegal_add";
	}
	/**
	 * 违章添加搜索
	 * 
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping("toSearchCarIllegal")
	@ResponseBody
	public ResultInfo<CarIllegal> toSearchCarIllegal(String carPlateNo,String illegalTime) throws ParseException {
		ResultInfo<CarIllegal> resultInfo = new ResultInfo<CarIllegal>();
		Order order = new Order();
		order.setCarPlateNo(carPlateNo);
		order.setStartBillingTimeEnd(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(illegalTime));
		order.setFinishTimeStart(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(illegalTime));
		Query q = new Query(order);
		List<Order> orderList = orderService.getOrderList(q);
		CarIllegal carIllegal=new CarIllegal();
		if(orderList!=null&&orderList.size()>0){
			carIllegal.setCarNo(orderList.get(0).getCarNo());
			carIllegal.setCityId(orderList.get(0).getCityId());
			carIllegal.setCityName(orderList.get(0).getCityName());
			carIllegal.setCarModelId(orderList.get(0).getCarModelId());
			carIllegal.setCarModelName(orderList.get(0).getCarModelName());
			carIllegal.setDocumentNo(orderList.get(0).getOrderNo());
			carIllegal.setDriverId(orderList.get(0).getMemberNo());
			carIllegal.setDriverName(orderList.get(0).getMemberName());
			carIllegal.setUseCarType(1);
			resultInfo.setCode("1");
			resultInfo.setData(carIllegal);
		}else{
			WorkerOrder wo = new WorkerOrder();
			wo.setCarPlateNo(carPlateNo);
			wo.setStartTimeEnd(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(illegalTime));
			wo.setFinishTimeStart(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(illegalTime));
			Query q1 = new Query(wo);
			List<WorkerOrder> wokerOrderList = workerOrderService.getWorkerOrderList(q1);
			if(wokerOrderList!=null&&wokerOrderList.size()>0){
				carIllegal.setDocumentNo(wokerOrderList.get(0).getWorkerOrderNo());
				carIllegal.setDriverId(wokerOrderList.get(0).getWorkerId());
				carIllegal.setDriverName(wokerOrderList.get(0).getWorkerName());
				carIllegal.setUseCarType(2);
				Car car = carService.getCarByPlateNo(carPlateNo);
				carIllegal.setCarModelId(car.getCarModelId());
				carIllegal.setCarModelName(car.getCarModelName());
				carIllegal.setCityId(car.getCityId());
				carIllegal.setCityName(car.getCityName());
				resultInfo.setCode("1");
				resultInfo.setData(carIllegal);
			}else{
				resultInfo.setCode("0");
				resultInfo.setMsg("没能成功匹配到相应的订单或调度单信息，请手动输入！");
			}
		}
		return resultInfo;
	}
	/**
	 * 违章添加
	 * 
	 * @param device
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping("addCarIllegal")
	@ResponseBody
	public ResultInfo<CarIllegal> addCarIllegal(@ModelAttribute("carIllegal") CarIllegal carIllegal,String timeIllegal) throws ParseException {
		ResultInfo<CarIllegal> resultInfo = new ResultInfo<CarIllegal>();
    	Car car = carService.getCarByPlateNo(carIllegal.getCarPlateNo());
    	if(car!=null){
    		//查找  同一个车辆同一个时间不能生成重复违章列表！
    		CarIllegal cal= new CarIllegal();
    		cal.setCarPlateNo(carIllegal.getCarPlateNo());
    		cal.setIllegalTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(timeIllegal));
    		Query q= new Query(cal);
    		List<CarIllegal> cals= carIllegalService.getCarIllegalList(q);
    		if(cals != null && cals.size()>0){
    			resultInfo.setCode(Constant.FAIL);
        		resultInfo.setMsg("同一个车辆同一个时间不能生成重复违章列表！");
    		}else{
    			carIllegal.setCarBrandId(car.getCarBrandId());
        		carIllegal.setCarBrandName(car.getCarBrandName());
        		carIllegal.setCarModelId(car.getCarBrandId());
        		carIllegal.setCarModelName(car.getCarModelName());
        		carIllegal.setCarNo(car.getCarNo());
        		carIllegal.setIllegalTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(timeIllegal));
        		resultInfo = carIllegalService.addCarIllegal(carIllegal, getOperator());
        		//根据对应的  订单号  查找出 对应的 订单 改变 是否违章状态
        		if(resultInfo.getCode().equals("1") && resultInfo.getData().getDocumentNo() != null && !"".equals(resultInfo.getData().getDocumentNo()) ){
        			Order order=orderService.getOrder(resultInfo.getData().getDocumentNo());
        			if(order != null ){
        				Order orders= new Order();
        				orders.setIsIllegal(1);
        				orders.setRecordIllegalTime(resultInfo.getData().getIllegalTime());
        				orders.setOrderNo(order.getOrderNo());
        				orderService.updateOrder(orders, getOperator());
        			}
        		}
    		}
    		
    	}else{
    		resultInfo.setCode(Constant.FAIL);
    		resultInfo.setMsg("所输入的车牌号不存在");
    	}
		return resultInfo;
	}

}
