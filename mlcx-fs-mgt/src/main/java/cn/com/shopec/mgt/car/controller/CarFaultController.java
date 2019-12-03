package cn.com.shopec.mgt.car.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.common.constants.Constant;
import cn.com.shopec.core.car.model.Car;
import cn.com.shopec.core.car.model.CarFault;
import cn.com.shopec.core.car.model.CarSeries;
import cn.com.shopec.core.car.service.CarFaultService;
import cn.com.shopec.core.car.service.CarSeriesService;
import cn.com.shopec.core.car.service.CarService;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.member.model.Member;
import cn.com.shopec.core.member.service.MemberService;
import cn.com.shopec.core.order.model.Order;
import cn.com.shopec.core.order.service.OrderService;
import cn.com.shopec.core.scheduling.model.WorkerOrder;
import cn.com.shopec.core.scheduling.service.WorkerOrderService;
import cn.com.shopec.core.system.model.DataDictItem;
import cn.com.shopec.core.system.service.DataDictItemService;
import cn.com.shopec.mgt.common.BaseController;
@Controller
@RequestMapping("/carFault")
public class CarFaultController extends BaseController{
	@Resource
	private CarFaultService carFaultService;
	@Resource
	private DataDictItemService dataDictItemService;
	@Resource
	private MemberService memberService;
	@Resource
	private OrderService orderService;
	@Resource
	private WorkerOrderService workerOrderService;
	@Resource
	private CarService carService;
	@Resource
	private CarSeriesService  carSeriesService;
	
	/*
	 * 显示故障列表页
	 */
	@RequestMapping("/toCarFaultList")
	public String toCarMalfunctionList(String documentNo, ModelMap modelMap) {
		modelMap.put("documentNo", documentNo);
		return "car/car_Fault_list";
	}
	/*
	 * 显示故障列表分页
	 */
	@RequestMapping("/pageListCarFault")
	@ResponseBody
	public PageFinder<CarFault> pageListCarMalfunction(@ModelAttribute("carFault") CarFault carFault,Query query){
		Query q = new Query(query.getPageNo(),query.getPageSize(),carFault);
		return carFaultService.getCarMalfunctionPageList(q);
	}
	/*
	 * 新增故障页面
	 */
	@RequestMapping("/addCarFaultPage")
	public String addCarFaultPage(Query q,ModelMap modelMap) {
		List<DataDictItem> cities = dataDictItemService.getDataDictItemListByCatCode("CITY");//城市
		//List<DataDictItem>  carmodels= dataDictItemService.getDataDictItemListByCatCode("CAR_MODEL");//汽车型号
		List<CarSeries> carServices = carSeriesService.getCarSeriesList(new Query(new CarSeries()));
		List<Member> members=memberService.getMemberList(q);
		modelMap.put("cities", cities);
		modelMap.put("members", members);
		modelMap.put("carmodels", carServices);
		return "car/car_Fault_add";
	}
	/*
	 * 编辑页面
	 */
	@RequestMapping("/toCarFaultEdit")
	public String toCarFaultEdit(String faultId,Query q,ModelMap modelMap) {
		if(faultId!=null&&faultId.length()!=0){
			CarFault carFault=carFaultService.getCarFault(faultId);
			List<DataDictItem> cities = dataDictItemService.getDataDictItemListByCatCode("CITY");//城市
			List<DataDictItem>  carmodels= dataDictItemService.getDataDictItemListByCatCode("CAR_MODEL");//汽车型号
			List<Member> members=memberService.getMemberList(q);
			modelMap.put("cities", cities);
			modelMap.put("members", members);
			modelMap.put("carmodels", carmodels);
			modelMap.put("carFault", carFault);
		}
		return "car/car_Fault_edit";
	}
	/*
	 * 显示故障编辑页View
	 */
	@RequestMapping("/toCarFaultView")
	public String toCarFaultView(String faultId,ModelMap modelMap) {
		if(faultId!=null&&faultId.length()!=0){
			CarFault carFault=carFaultService.getCarFault(faultId);
			modelMap.put("carFault", carFault);
		}
		return "car/car_Fault_view";
	}
	/*
	 * 增加/编辑提交
	 */
	@RequestMapping("/editCarFault")
	@ResponseBody
	public ResultInfo<CarFault> editCarFault(@ModelAttribute("carFault")CarFault carFault){
		ResultInfo<CarFault> resultInfo = new ResultInfo<>();
		Car c = carService.getCarByPlateNo(carFault.getCarPlateNo());
		if(c == null){
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg("车牌号不存在，");
			return resultInfo;
		}
		if(carFault.getUseCarType() == 1){
			Order o = new Order();
			o.setOrderNo(carFault.getDocumentNo());
			o.setCarPlateNo(carFault.getCarPlateNo());
			Query q = new Query(o);
			List<Order> list = orderService.getOrderList(q);
			if(list.isEmpty()){
				resultInfo.setCode(Constant.FAIL);
				resultInfo.setMsg("单据号不存在，");
				return resultInfo;
			}
		}else if(carFault.getUseCarType() == 2){
			WorkerOrder wo = new WorkerOrder();
			wo.setWorkerOrderNo(carFault.getDocumentNo());
			wo.setCarPlateNo(carFault.getCarPlateNo());
			Query q = new Query(wo);
			List<WorkerOrder> list = workerOrderService.getWorkerOrderList(q);
			if(list.isEmpty()){
				resultInfo.setCode(Constant.FAIL);
				resultInfo.setMsg("单据号不存在，");
				return resultInfo;
			}
		}
		if(carFault.getFaultId()!=null&&carFault.getFaultId().length()!=0){
			resultInfo=carFaultService.updateCarFault(carFault, getOperator());
//			//查询有没有 处理完成的  全部处理完成 则改变订单 故障状态
//			CarFault cf=new CarFault();
//			cf.set
//			cf.setProcessingStatus(2);
//			Query q= new Query(cf);
//			List<CarFault> cars =carFaultService.getCarFaultList(q);
//			if(cars != null && cars.size()>0){
//				Order or= new Order();
//				or.setOrderNo(cars.);
//				or.setIsFault(1);
//				orderService.updateOrder(or, getOperator());
//			}
		}else{
			resultInfo=carFaultService.addCarFault(carFault, getOperator());
			if(resultInfo.getCode()=="1" && resultInfo.getData().getDocumentNo() != null &&  !"".equals( resultInfo.getData().getDocumentNo())){
				Order order=orderService.getOrder(resultInfo.getData().getDocumentNo());
				if(order != null){
					Order or= new Order();
					or.setOrderNo(order.getOrderNo());
					or.setRecordFaultTime(resultInfo.getData().getRecordFaultTime());
					or.setIsFault(1);
					orderService.updateOrder(or, getOperator());
				}
			}
		}
		return resultInfo;
	}

	
	//判断新增故障的时候车牌号是否有
	@RequestMapping("/carPlateNoCheck")
	@ResponseBody
	public ResultInfo<String> carPlateNoCheck(String carPlateNo) {
		ResultInfo<String> resultInfo = new ResultInfo<String>();
		Car c=carService.getCar(carPlateNo);
		if(c != null){
			resultInfo.setCode(Constant.YES + "");
		} else {
			resultInfo.setCode(Constant.NO + "");
		}
		return resultInfo;
	}
	
	
	/**
	 * 违章添加搜索
	 * 
	 * @return
	 */
	@RequestMapping("toSearchCarFault")
	@ResponseBody
	public ResultInfo<CarFault> toSearchCarAccident(@ModelAttribute("carFault") CarFault carFault) {
		ResultInfo<CarFault> resultInfo = new ResultInfo<CarFault>();
		Order order = new Order();
		order.setCarPlateNo(carFault.getCarPlateNo());
		order.setStartBillingTimeEnd(carFault.getRecordFaultTime());
		order.setFinishTimeStart(carFault.getRecordFaultTime());
		Query q = new Query(order);
		List<Order> orderList = orderService.getOrderList(q);
		if(orderList!=null&&orderList.size()>0){
			carFault.setCarNo(orderList.get(0).getCarNo());
			carFault.setCityId(orderList.get(0).getCityId());
			carFault.setCityName(orderList.get(0).getCityName());
			carFault.setCarModelId(orderList.get(0).getCarModelId());
			carFault.setCarModelName(orderList.get(0).getCarModelName());
			carFault.setDocumentNo(orderList.get(0).getOrderNo());
			carFault.setDriverId(orderList.get(0).getMemberNo());
			carFault.setDriverName(orderList.get(0).getMemberName());
			carFault.setUseCarType(1);
			resultInfo.setCode("1");
			resultInfo.setData(carFault);
		}else{
			WorkerOrder wo = new WorkerOrder();
			wo.setCarPlateNo(carFault.getCarPlateNo());
			wo.setStartTimeEnd(carFault.getRecordFaultTime());
			wo.setFinishTimeStart(carFault.getRecordFaultTime());
			Query q1 = new Query(wo);
			List<WorkerOrder> wokerOrderList = workerOrderService.getWorkerOrderList(q1);
			if(wokerOrderList!=null&&wokerOrderList.size()>0){
				carFault.setDocumentNo(wokerOrderList.get(0).getWorkerOrderNo());
				carFault.setDriverId(wokerOrderList.get(0).getWorkerId());
				carFault.setDriverName(wokerOrderList.get(0).getWorkerName());
				carFault.setUseCarType(2);
				Car car = carService.getCarByPlateNo(carFault.getCarPlateNo());
				carFault.setCarModelId(car.getCarModelId());
				carFault.setCarModelName(car.getCarModelName());
				carFault.setCityId(car.getCityId());
				carFault.setCityName(car.getCityName());
				resultInfo.setCode("1");
				resultInfo.setData(carFault);
			}else{
				resultInfo.setCode("0");
				resultInfo.setMsg("没能成功匹配到相应的订单信息，请手动输入！");
			}
		}
		return resultInfo;
	}
	/*
	 * 订单详情页面增加故障
	 */
	@RequestMapping("/editOrderCarFault")
	@ResponseBody
	public ResultInfo<CarFault> editOrderCarFault(@ModelAttribute("carFault")CarFault carFault){
		ResultInfo<CarFault> resultInfo=new ResultInfo<CarFault>();
		Query query=new Query();
		query.setQ(carFault);
		List<CarFault> carFaults=carFaultService.getCarFaultList(query);
		if(carFaults.size()>0){
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg("此订单已记录过故障");
		}else{
			resultInfo=carFaultService.addCarFault(carFault, getOperator());
		}
		return resultInfo;
	}
}
