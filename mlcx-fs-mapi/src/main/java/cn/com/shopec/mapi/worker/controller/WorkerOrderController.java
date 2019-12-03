package cn.com.shopec.mapi.worker.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;

import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.common.constants.Constant;
import cn.com.shopec.common.utils.ECDateUtils;
import cn.com.shopec.common.utils.ECGeoCoordinateTransformUtil;
import cn.com.shopec.core.car.common.CarConstant;
import cn.com.shopec.core.car.dao.CarStatusDao;
import cn.com.shopec.core.car.model.Car;
import cn.com.shopec.core.car.model.CarAccident;
import cn.com.shopec.core.car.model.CarAndStatus;
import cn.com.shopec.core.car.model.CarStatus;
import cn.com.shopec.core.car.service.CarRecordService;
import cn.com.shopec.core.car.service.CarService;
import cn.com.shopec.core.car.service.CarStatusService;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.device.model.Device;
import cn.com.shopec.core.device.service.DeviceService;
import cn.com.shopec.core.marketing.model.Advert;
import cn.com.shopec.core.member.model.Member;
import cn.com.shopec.core.order.common.OrderConstant;
import cn.com.shopec.core.resource.common.ParkConstant;
import cn.com.shopec.core.resource.model.Park;
import cn.com.shopec.core.resource.model.ParkRegion;
import cn.com.shopec.core.resource.model.ParkStatus;
import cn.com.shopec.core.resource.model.WorkerRegion;
import cn.com.shopec.core.resource.service.ParkService;
import cn.com.shopec.core.resource.service.ParkStatusService;
import cn.com.shopec.core.scheduling.model.WorkerOrder;
import cn.com.shopec.core.scheduling.service.WorkerOrderService;
import cn.com.shopec.core.scheduling.service.WorkerService;
import cn.com.shopec.core.search.model.ParkSearchCondition;
import cn.com.shopec.core.system.model.DataDictItem;
import cn.com.shopec.core.system.model.SysParam;
import cn.com.shopec.core.system.service.DataDictItemService;
import cn.com.shopec.core.system.service.SysParamService;
import cn.com.shopec.mapi.car.vo.CarVo;
import cn.com.shopec.mapi.car.vo.GoAddCarVo;
import cn.com.shopec.mapi.common.controller.BaseController;
import cn.com.shopec.mapi.resource.vo.ParkVOAround;
import cn.com.shopec.mapi.resource.vo.ParkVOCar;
import cn.com.shopec.mapi.resource.vo.ParkVOCarStatus;
import cn.com.shopec.mapi.worker.vo.WorkerOrderDetailVo;
import cn.com.shopec.mapi.worker.vo.WorkerOrderFinishVo;
import cn.com.shopec.mapi.worker.vo.WorkerOrderPark;
import cn.com.shopec.mapi.worker.vo.WorkerOrderParkVo;
import cn.com.shopec.mapi.worker.vo.WorkerOrderVo;

@Controller
@RequestMapping("/workerApp/workerOrder")
public class WorkerOrderController extends BaseController{
	private static String DEVICE_KEY = "";

	static {
		StringBuilder sb = new StringBuilder("");
		for(int i = 0; i < 128; i++) {
			sb.append('0');
		}
		DEVICE_KEY = sb.toString();
	}

	@Resource
	private WorkerOrderService workerOrderService;

	@Resource
	private ParkService parkService;

	@Resource
	private DataDictItemService dataDictItemService;

	@Resource
	private CarService carService;

	@Resource
	private CarStatusService carStatusService;

	@Resource
	private WorkerService workerService;

	@Resource
	private ParkStatusService parkStatusService;

	@Resource
	private SysParamService sysParamService;

	@Resource
	private CarRecordService carRecordService;

	@Resource
	private DeviceService deviceService;
	@Resource
	private CarStatusDao carStatusDao;

	@Value("${image_path}")
	private String imgPath;
	/**
	 * 调度员的任务列表
	 * */
	@RequestMapping("/myWorkerOrderList")
	@ResponseBody
	public ResultInfo<List<WorkerOrderVo>> myWorkerOrderList(String workerNo,String type) {
		ResultInfo<List<WorkerOrderVo>> result=new ResultInfo<List<WorkerOrderVo>>();
		List<WorkerOrder> list=workerOrderService.getWorkerOrderListByWorkerNo(workerNo,type);
		return workerOrderToVo(result,list);
	}
	/**
	 * 任务详情
	 * */
	@RequestMapping("/workerOrderDetail")
	@ResponseBody
	public ResultInfo<WorkerOrderDetailVo> workerOrderDetail(String workerOrderNo) {
		ResultInfo<WorkerOrderDetailVo> result=new ResultInfo<WorkerOrderDetailVo>();
		WorkerOrder wo=workerOrderService.getWorkerOrder(workerOrderNo);

		return workerOrderDetailToVo(result,wo);
	}

	/**
	 * 修改调度单状态为调度中
	 * @param workerOrderNo
	 * @return
	 */
	@RequestMapping("/workerOrderUpdate")
	@ResponseBody
	public ResultInfo<WorkerOrderDetailVo> workerOrderUpdate(String workerOrderNo) {
		ResultInfo<WorkerOrderDetailVo> result=new ResultInfo<WorkerOrderDetailVo>();
		WorkerOrder wo=workerOrderService.getWorkerOrder(workerOrderNo);
		if(wo.getStartTime()==null){
			wo.setStartTime(new Date());
		}
		wo.setWorkOrderStatus(2);
		workerOrderService.updateWorkerOrder(wo,getOperator());

		CarStatus carStatus=carStatusDao.get(wo.getCarNo());
		carStatus.setUserageStatus(3);//使用状态（0，空闲、1，已预占、2，订单中、3，调度中）
		CarStatus carStatusUp=new CarStatus();
		carStatusUp.setCarNo(carStatus.getCarNo());
		carStatusUp.setUserageStatus(3);
		carStatusService.updateCarStatus(carStatusUp, getOperator());

		return workerOrderDetailToVo(result,wo);
	}


	/**
	 * 任务完成
	 * */
	@SuppressWarnings("unchecked")
	@RequestMapping("/workerOrderFinish")
	@ResponseBody
	public ResultInfo<WorkerOrderFinishVo> workerOrderFinish(String workerOrderNo,String workerNo) {
		ResultInfo<WorkerOrderFinishVo> result=new ResultInfo<WorkerOrderFinishVo>();
		//租车之前先判断该车辆是否是新设备，有则发送还车的指令,并且等待成功响应
				WorkerOrder wo = workerOrderService.getWorkerOrder(workerOrderNo);
				Device device = deviceService.getDeviceByCarNo(wo.getCarNo());
				if(device!=null&&"1".equals(device.getVersionType())){//为1是新设备，可以使用用车还车指令，且等待响应完成后继续执行后续业务
					ResultInfo<String> userCarResult = new ResultInfo<String>();
					String res = "";
					try {
						res	= carStatusService.returnCarSendCmd(device.getDeviceSn(), null,workerNo,"1");
					} catch (Exception e) { //未正确执行还车指令，也允许结束调度单（暂定）
						return workerOrderFinishToVo(result,workerOrderService.workerOrderFinish(workerOrderNo,workerNo,getOperator1()).getData());
					}
					if(res!=null && !"".equals(res)){
						Gson gson = new Gson();
						userCarResult = gson.fromJson(res, ResultInfo.class);
						if (userCarResult.getCode().equals(Constant.SECCUESS)) {
							workerOrderFinishToVo(result,workerOrderService.workerOrderFinish(workerOrderNo,workerNo,getOperator1()).getData());
						} else if(userCarResult.getCode().equals("-4")) { //发送指令失败，也允许结束（暂定）
							workerOrderFinishToVo(result,workerOrderService.workerOrderFinish(workerOrderNo,workerNo,getOperator1()).getData());
						}  else if(userCarResult.getCode().equals("205")) { //指令执行失败，也允许结束（暂定）
							workerOrderFinishToVo(result,workerOrderService.workerOrderFinish(workerOrderNo,workerNo,getOperator1()).getData());
						}  else if(userCarResult.getCode().equals("206")) { //指令执行超时，也允许结束（暂定）
							workerOrderFinishToVo(result,workerOrderService.workerOrderFinish(workerOrderNo,workerNo,getOperator1()).getData());
						}  else if(userCarResult.getCode().equals("255")) { //设备不支持此指令，也允许结束（暂定）
							workerOrderFinishToVo(result,workerOrderService.workerOrderFinish(workerOrderNo,workerNo,getOperator1()).getData());
						} else if(userCarResult.getCode().equals("208")) { //手刹未拉起，也允许结束（暂定）
							workerOrderFinishToVo(result,workerOrderService.workerOrderFinish(workerOrderNo,workerNo,getOperator1()).getData());
						} else {
							result.setCode(Constant.FAIL);
							result.setMsg(userCarResult.getMsg());
						}
						return result;
					} else {
						//未正确执行还车指令，也允许结束调度单（暂定）
						return workerOrderFinishToVo(result,workerOrderService.workerOrderFinish(workerOrderNo,workerNo,getOperator1()).getData());
					}
				}else{//旧设备按原业务处理
					return workerOrderFinishToVo(result,workerOrderService.workerOrderFinish(workerOrderNo,workerNo,getOperator1()).getData());
				}
	}

	@RequestMapping("/workerParkList")
	@ResponseBody
	/**
	 * 获取场站和车辆数据
	 * 备注：入参需要加入场站半径，目前实现使用系统参数
	 * @param distance 场站半径
	 * @param longitude 经纬度
	 * @param latitude	经纬度
	 * @param memberNo　会员编号
	 * @param requestType　发起请求时机	(一种是选车时候，一种是还车时候，返回结果不同)
	 * @return
	 */
	public ResultInfo<ParkVOCar> workerParkList(String distance, String longitude,String latitude,String memberNo,String requestType) {
		//定义返回结果集
		ResultInfo<ParkVOCar> result=new ResultInfo<ParkVOCar>();
		
		//获取场站半径，如果为空默认使用系统参数的场站半径
		Double distanceVal = 0.00;
		if(null == distance){
			SysParam sysParam = sysParamService.getByParamKey(ParkConstant.distance_param_key);
			distanceVal = Double.parseDouble(sysParam.getParamValue());//单位：米
		}else{
			distanceVal = Double.parseDouble(distance);//单位：米
		}

		//获取不在场站内的车辆列表
		Query q=new Query();
		List<CarStatus> noParkCarList = carStatusService.getCarNoPark(longitude,latitude,distanceVal);

		//场站内车辆列表，场站外车列表    转换为ResultInfo<ParkVOCar> 格式数据并返回
		return parkToVoAround(result,parkService.getParkListByWorker(longitude,latitude,distanceVal),noParkCarList);
	}
	/**
	 * 车辆列表--所有车辆（车型，牌照，预估里程，车辆图片）
	 * */
	@RequestMapping("/workerParkCarList")
	@ResponseBody
	public ResultInfo<List<CarVo>> workerParkCarList(String parkNo) {
		ResultInfo<List<CarVo>> result=new ResultInfo<List<CarVo>>();
		List<Car> list=carService.getCarListByParkExist(parkNo);
		return carToVo(result,list);
	}
	/**
	 * 车辆详情（车型，牌照，预估里程，状态，电量，车辆图片）
	 * */
	@RequestMapping("/workerParkCarDetail")
	@ResponseBody
	public ResultInfo<CarVo> workerParkCarDetail(String carNo) {
		ResultInfo<CarVo> result=new ResultInfo<CarVo>();
		Car car=carService.getCar(carNo);
		return carToVoOne(result,car);
	}
	/**
	 * 当前选择的车辆是否在调度单中已经存在
	 * */
	@RequestMapping("/carExsitWorkerOrder")
	@ResponseBody
	public Boolean carExsitWorkerOrder(String carNo) {
		Long num=workerOrderService.carExsitWorkerOrder(carNo);
		if(num==0){
			return false;
		}else{
			return true;
		}
	}
	/**
	 * 车辆任务--具体选择(下调度单)
	 * */
	@RequestMapping("/workerParkCarChoose")
	@ResponseBody
	public ResultInfo<WorkerOrderVo> workerParkCarChoose(String carNo,Integer type,String workerNo,String sendReason,String sendReasonPicUrl1,String sendReasonPicUrl2,String sendReasonPicUrl3) {//调度类型  -- 0-挪车 1-洗车 2-维修 3-保养
		ResultInfo<WorkerOrderVo> result=new ResultInfo<WorkerOrderVo>();
		if(sendReasonPicUrl1!=null&&!sendReasonPicUrl1.equals("")){
			sendReasonPicUrl1=sendReasonPicUrl1.substring(imgPath.length()+1);
		}
		if(sendReasonPicUrl2!=null&&!sendReasonPicUrl2.equals("")){
			sendReasonPicUrl2=sendReasonPicUrl2.substring(imgPath.length()+1);
		}
		if(sendReasonPicUrl3!=null&&!sendReasonPicUrl3.equals("")){
			sendReasonPicUrl3=sendReasonPicUrl3.substring(imgPath.length()+1);
		}
		//判断carNo的状态，不是0不能下调度单
		CarStatus carStatus=carStatusService.getCarStatus(carNo);
		if(carStatus!=null&&!carStatus.getUserageStatus().equals(0)){
			result.setCode(Constant.FAIL);
			result.setMsg(CarConstant.available_car_msg);
		}else{
			//配置系统参数，用于判断车辆是否在场站中（为1.车辆在场站外的也可以下调度单，为0.车辆只有在场站中的才可以下调度单）
			SysParam sysp = sysParamService.getByParamKey(CarConstant.is_allow_scheduling);
			Integer tag=0;
			if (sysp!=null&&sysp.getParamValue()!=null&&!sysp.getParamValue().equals("")) {
				tag = Integer.parseInt(sysp.getParamValue());
			}
			//判断车辆是否在场站中
			CarStatus carS=carStatusService.getCarStatus(carNo);
			
			CarStatus carStatusUp=new CarStatus();
			carStatusUp.setCarNo(carNo);
			carStatusUp.setUpdateTime(new Date());
			carStatusUp.setUserageStatus(1);
			if(tag.equals(0)){//车辆在场站外的不能下调度单
				if(carS!=null){
					if(carS.getLocationParkNo()!=null&&!carS.getLocationParkNo().equals("")){
						//根据修改车辆使用状态的是否成功来判断
						int count = carStatusDao.updateCarUseStatusByCarNo(carStatusUp); //更新车辆状态
						if(count<1){
							result.setCode(Constant.FAIL);
							result.setMsg(CarConstant.usered_car_msg);
							return result;
						}else{
							result=workerOrderToVoOne(result,workerOrderService.workerParkCarChoose(carNo,type,workerNo,getOperator1(),sendReason,sendReasonPicUrl1,sendReasonPicUrl2,sendReasonPicUrl3).getData());
						}
					}else{
						//调用根据车辆当前位置获取场站信息的接口，如果存在场站信息，则可下调度单。否则不能
						String parkNo="";
						parkNo=parkService.getCurrentParkNoByCarLocation(carStatus.getLongitude(),carStatus.getLatitude());
						if("".equals(parkNo)){
							result.setCode(CarConstant.car_NOInPark);
							result.setMsg("车辆不在场站内，不能下调度单！");
							return result;
						}else{
							CarStatus cs=new CarStatus();
							cs.setCarNo(carS.getCarNo());
							cs.setLocationParkNo(parkNo);
							carStatusService.updateCarStatus(cs, getOperator());
							//根据修改车辆使用状态的是否成功来判断
							int count = carStatusDao.updateCarUseStatusByCarNo(carStatusUp); //更新车辆状态
							if(count<1){
								result.setCode(Constant.FAIL);
								result.setMsg(CarConstant.usered_car_msg);
								return result;
							}else{
								result=workerOrderToVoOne(result,workerOrderService.workerParkCarChoose(carNo,type,workerNo,getOperator1(),sendReason,sendReasonPicUrl1,sendReasonPicUrl2,sendReasonPicUrl3).getData());
							}
						}
					}
				}else{
					result.setCode(Constant.FAIL);
					result.setMsg(CarConstant.available_car_msg);
				}
			}else if(tag.equals(1)){//车辆在场站外的也可以下调度单
				if(carS!=null){
					if(carS.getLocationParkNo()!=null&&!carS.getLocationParkNo().equals("")){
						//根据修改车辆使用状态的是否成功来判断
						int count = carStatusDao.updateCarUseStatusByCarNo(carStatusUp); //更新车辆状态
						if(count<1){
							result.setCode(Constant.FAIL);
							result.setMsg(CarConstant.usered_car_msg);
							return result;
						}else{
							result=workerOrderToVoOne(result,workerOrderService.workerParkCarChoose(carNo,type,workerNo,getOperator1(),sendReason,sendReasonPicUrl1,sendReasonPicUrl2,sendReasonPicUrl3).getData());
						}
					}else{
						//调用根据车辆当前位置获取场站信息的接口，如果存在场站信息，则可下调度单。否则不能
						String parkNo="";
						parkNo=parkService.getCurrentParkNoByCarLocation(carStatus.getLongitude(),carStatus.getLatitude());
						if("".equals(parkNo)){
							//根据修改车辆使用状态的是否成功来判断
							int count = carStatusDao.updateCarUseStatusByCarNo(carStatusUp); //更新车辆状态
							if(count<1){
								result.setCode(Constant.FAIL);
								result.setMsg(CarConstant.usered_car_msg);
								return result;
							}else{
								result=workerOrderToVoOne(result,workerOrderService.workerParkCarChoose(carNo,type,workerNo,getOperator1(),sendReason,sendReasonPicUrl1,sendReasonPicUrl2,sendReasonPicUrl3).getData());
							}
						}else{
							CarStatus cs=new CarStatus();
							cs.setCarNo(carS.getCarNo());
							cs.setLocationParkNo(parkNo);
							carStatusService.updateCarStatus(cs, getOperator());
							//根据修改车辆使用状态的是否成功来判断
							int count = carStatusDao.updateCarUseStatusByCarNo(carStatusUp); //更新车辆状态
							if(count<1){
								result.setCode(Constant.FAIL);
								result.setMsg(CarConstant.usered_car_msg);
								return result;
							}else{
								result=workerOrderToVoOne(result,workerOrderService.workerParkCarChoose(carNo,type,workerNo,getOperator1(),sendReason,sendReasonPicUrl1,sendReasonPicUrl2,sendReasonPicUrl3).getData());
							}
						}
					}
				}else{
					result.setCode(Constant.FAIL);
					result.setMsg(CarConstant.available_car_msg);
				}
			}
		}
		return result;
	}
	/**
	 * 网点任务列表
	 * */
	@RequestMapping("/parkWorkerOrderList")
	@ResponseBody
	public ResultInfo<WorkerOrderPark> parkWorkerOrderList(String workerNo,String parkNo) {
		ResultInfo<WorkerOrderPark> result=new ResultInfo<WorkerOrderPark>();

		return workerOrderParkToVo(result,workerNo,parkNo);
	}

	/**
	 * 当前调度员，是否还有调度中订单调度单
	 * @param workerNo
	 * @return
	 */
	@RequestMapping("/exsitWorkerOrder")
	@ResponseBody
	public ResultInfo<WorkerOrder> exsitWorkerOrder(String workerNo) {
		return workerOrderService.exsitWorkerOrder(workerNo);
	}

	/**
	 * 是否是当前调度员的调度用车
	 * @param workerNo
	 * @param parkNo
	 * @return
	 */
	@RequestMapping("/esUserWorkerOrder")
	@ResponseBody
	public ResultInfo<WorkerOrder> esUserWorkerOrder(String workerNo,String carNo) {
		return workerOrderService.esUserWorkerOrder(workerNo,carNo);
	}

	/**
	 * 方法说明:将按特定条件查询的记录转换成自定vo对象
	 */
	ResultInfo<List<WorkerOrderVo>> workerOrderToVo(ResultInfo<List<WorkerOrderVo>> result,List<WorkerOrder> woList){

		if (woList!=null&&woList.size()>0) {
			List<WorkerOrderVo> voList = new ArrayList<WorkerOrderVo>();
			for (WorkerOrder wo : woList) {
				WorkerOrderVo or = new WorkerOrderVo();
				or.setMemo(wo.getMemo());
				or.setSendTime(ECDateUtils.formatTime(wo.getSendTime()));
				or.setWorkerOrderNo(wo.getWorkerOrderNo());
				Park park=null;
				if(wo.getStartParkNo()!=null&&!wo.getStartParkNo().equals("")){
					park=parkService.getPark(wo.getStartParkNo());
				}
				String address="";
				if(park!=null){
					if(park.getAddrRegion3Name()!=null&&park.getAddrRegion3Name().length()!=0){
						address=park.getAddrRegion1Name()+park.getAddrRegion2Name()+park.getAddrRegion3Name()+park.getAddrStreet();
					}else{
						address=park.getAddrRegion1Name()+park.getAddrRegion2Name()+park.getAddrStreet();
					}
				}
				or.setWorkerAddress(address);
				voList.add(or);
			}
			result.setData(voList);
			result.setCode(CarConstant.success_code);
		}else {
			result.setCode(CarConstant.fail_code);
			result.setMsg(Constant.NO_RECORD);
		}
		return result;
	}
	/**
	 * 方法说明:将按特定条件查询的记录转换成自定vo对象
	 */
	ResultInfo<WorkerOrderVo> workerOrderToVoOne(ResultInfo<WorkerOrderVo> result,WorkerOrder wo){

		if (wo!=null) {
			WorkerOrderVo or = new WorkerOrderVo();
			or.setMemo(wo.getMemo());
			or.setSendTime(ECDateUtils.formatTime(wo.getSendTime()));
			or.setWorkerOrderNo(wo.getWorkerOrderNo());
			Park park=null;
			if(wo.getStartParkNo()!=null&&!wo.getStartParkNo().equals("")){
				park=parkService.getPark(wo.getStartParkNo());
			}

			String address="";
			if(park!=null){
				if(park.getAddrRegion3Name()!=null&&park.getAddrRegion3Name().length()!=0){
					address=park.getAddrRegion1Name()+park.getAddrRegion2Name()+park.getAddrRegion3Name()+park.getAddrStreet();
				}else{
					address=park.getAddrRegion1Name()+park.getAddrRegion2Name()+park.getAddrStreet();
				}	
			}
			or.setWorkerAddress(address);
			result.setData(or);
			result.setCode(CarConstant.success_code);
		}else {
			result.setCode(CarConstant.fail_code);
			result.setMsg(Constant.NO_RECORD);
		}
		return result;
	}
	/**
	 * 方法说明:将按特定条件查询的记录转换成自定vo对象
	 */
	ResultInfo<WorkerOrderDetailVo> workerOrderDetailToVo(ResultInfo<WorkerOrderDetailVo> result,WorkerOrder wo){

		if (wo!=null) {
			WorkerOrderDetailVo or = new WorkerOrderDetailVo();
			Car car=carService.getCar(wo.getCarNo());
			if(car!=null){
				//型号
				DataDictItem dataDictItemModel= dataDictItemService.getDataDictItem(car.getCarModelId());
				if(dataDictItemModel!=null){
					or.setCarModelName(dataDictItemModel.getItemValue());
				}

				or.setCarBrandName(car.getCarBrandName());
			}
			or.setCarPlateNo(wo.getCarPlateNo());

			or.setStartParkName(wo.getStartParkName());
			or.setTerminalParkName(wo.getTerminalParkName());

			CarStatus carStatus=carStatusService.getCarStatus(wo.getCarNo());
			if(carStatus!=null){

				or.setCarStatus(carStatus.getCarStatus());
				or.setPower(carStatus.getPower());

				or.setLocationParkNo(carStatus.getLocationParkNo());
				or.setCarSafeStatus("0");
				or.setMileage(carStatus.getMileage());
				if(carStatus.getRangeMileage()!=null){
					or.setRangeMileage(carStatus.getRangeMileage());
				}else{
					or.setRangeMileage(0.0);
				}
				if(car!=null){
					or.setCarPhotoUrl1(imgPath+"/"+car.getCarPhotoUrl1());
				}

			}
			//key,这里先写死
			or.setDeviceKey(DEVICE_KEY);

			//MAC地址
			Device d = deviceService.getDevice(carStatus.getDeviceNo());
			if (d!=null) {
				or.setMacAddr(d.getMacAddr());
				or.setDeviceSn(d.getDeviceSn());
			}

			or.setWorkOrderStatus(wo.getWorkOrderStatus());
			or.setMemo(wo.getMemo());
			or.setType(wo.getType());
			or.setWorkerOrderNo(wo.getWorkerOrderNo());

			SysParam sysParam = sysParamService.getByParamKey(ParkConstant.power_limit);
			Double dpower=Double.parseDouble(sysParam.getParamValue());//单位：%电量

			or.setDPower(dpower);

			result.setData(or);
			result.setCode(CarConstant.success_code);
		}else {
			result.setCode(CarConstant.fail_code);
			result.setMsg(Constant.NO_RECORD);
		}
		return result;
	}
	/**
	 * 方法说明:将按特定条件查询的记录转换成自定vo对象
	 */
	ResultInfo<WorkerOrderFinishVo> workerOrderFinishToVo(ResultInfo<WorkerOrderFinishVo> result,WorkerOrder wo){
		if (wo!=null) {
			WorkerOrderFinishVo or = new WorkerOrderFinishVo();
			or.setFinishTime(wo.getFinishTime());
			or.setStartTime(wo.getStartTime());
			or.setWorkerOrderNo(wo.getWorkerOrderNo());
			or.setWorkOrderStatus(wo.getWorkOrderStatus());
			or.setWorkerId(wo.getWorkerId());
			or.setWorkerName(wo.getWorkerName());
			result.setData(or);
			result.setCode(CarConstant.success_code);
		}else {
			result.setCode(CarConstant.fail_code);
			result.setMsg(Constant.NO_RECORD);
		}
		return result;
	}

	/**
	 * 获取场站和车辆数据
	 *  方法说明:将按特定条件查询的记录转换成自定vo对象
	 * @param result 存放结果变量
	 * @param paList	场站内车辆集合 ,其中每个场站会附带场站内可用车辆信息
	 * @param noParkCarList	长站外车辆集合
	 * @return
	 */
	ResultInfo<ParkVOCar> parkToVoAround(ResultInfo<ParkVOCar> result,List<Park> paList,List<CarStatus> noParkCarList){

		ParkVOCar parkVOCar = new ParkVOCar();

		
		//循环遍历场站内车辆信息并返回
		if (paList!=null&&paList.size()>0) {
			List<ParkVOAround> voList = new ArrayList<ParkVOAround>();
			for (Park p : paList) {
				ParkVOAround po = new ParkVOAround();
				String addressDetail="";
				if(p.getAddrRegion3Name()!=null&&p.getAddrRegion3Name().length()!=0){
					addressDetail=p.getAddrRegion1Name()+p.getAddrRegion2Name()+p.getAddrRegion3Name()+p.getAddrStreet();
				}else{
					addressDetail=p.getAddrRegion1Name()+p.getAddrRegion2Name()+p.getAddrStreet();
				}
				po.setParkingSpaces(p.getParkingSpaces());
				po.setAddressDetail(addressDetail);
				po.setLatitude(p.getLatitude());
				po.setLongitude(p.getLongitude());
				po.setParkName(p.getParkName());
				po.setParkNo(p.getParkNo());
				po.setParkType(p.getParkType());
				if(p.getAvailableCarNum()!=null){
					po.setCarNum(p.getAvailableCarNum());
				}else{
					po.setCarNum(0);
				}
				//根据场站号获取当前场站内可用车辆信息集合
				po.setCars(carService.getCarListByParkExist(p.getParkNo()));
				voList.add(po);
			}
			parkVOCar.setParkVOAround(voList);

			//循环遍历场站外车辆信息并返回
			List<ParkVOCarStatus> list =  new ArrayList<ParkVOCarStatus>();
			for(CarStatus carst : noParkCarList){
				ParkVOCarStatus ParkVOCarStatus = new ParkVOCarStatus();
				Car car=carService.getCar(carst.getCarNo());
				if(null != car){
					ParkVOCarStatus.setCarBrandName(car.getCarBrandName());
					ParkVOCarStatus.setCarNo(car.getCarNo());
					ParkVOCarStatus.setCarPlateNo(car.getCarPlateNo());
					ParkVOCarStatus.setCarBrandName(car.getCarBrandName());
					ParkVOCarStatus.setCarModelName(car.getCarModelName());
					ParkVOCarStatus.setOnlineStatus(car.getOnlineStatus());
					CarStatus carStatus=carStatusService.getCarStatus(car.getCarNo());
					if(carStatus!=null){
						ParkVOCarStatus.setUserageStatus(carStatus.getUserageStatus());
					}
					ParkVOCarStatus.setCarPhotoUrl1(imgPath+"/"+car.getCarPhotoUrl1());
					ParkVOCarStatus.setPower(car.getPower());
					ParkVOCarStatus.setMileage(car.getMileage());
					ParkVOCarStatus.setLocationParkNo(car.getLocationParkNo());
					ParkVOCarStatus.setSeaTing(car.getSeaTing());
					ParkVOCarStatus.setChargeState(carst.getChargeState());
					ParkVOCarStatus.setChargingFaultStatus(carst.getChargingFaultStatus());
					ParkVOCarStatus.setCarStatus(carst.getCarStatus());

					//GPS经纬度转百度经纬度
					ParkSearchCondition parkSearchCondition = new ParkSearchCondition();
					parkSearchCondition = parkService.getBaiDuLocation(carst.getLongitude(),carst.getLatitude());
					ParkVOCarStatus.setLongitude(parkSearchCondition.getPosLongitude());
					ParkVOCarStatus.setLatitude(parkSearchCondition.getPosLatitude());

					ParkVOCarStatus.setRangeMileage(carst.getRangeMileage());
					ParkVOCarStatus.setPosition(carst.getPosition());
					list.add(ParkVOCarStatus);
				}
			}
			parkVOCar.setNoParkCarStatus(list);

			
			//设置返回结果信息
			result.setData(parkVOCar);
			result.setCode(OrderConstant.success_code);
			result.setMsg("查询成功");
		}else {
			result.setCode(OrderConstant.fail_code);
			result.setMsg(OrderConstant.reminder_msg);
		}
		return result;
	}
	/**
	 * 方法说明:将按特定条件查询的记录转换成自定vo对象
	 */
	ResultInfo<List<WorkerOrderParkVo>> workerOrderParkToVo1(ResultInfo<List<WorkerOrderParkVo>> result,List<WorkerOrder> woList){
		List<WorkerOrderParkVo> list=new ArrayList<WorkerOrderParkVo>();
		if (woList!=null&&woList.size()>0) {
			for(WorkerOrder w:woList){
				WorkerOrderParkVo or = new WorkerOrderParkVo();
				Car car=carService.getCar(w.getCarNo());
				if(car!=null){
					//型号
					DataDictItem dataDictItemModel= dataDictItemService.getDataDictItem(car.getCarModelId());
					if(dataDictItemModel!=null){
						or.setCarModelName(dataDictItemModel.getItemValue());
					}
					or.setCarBrandName(car.getCarBrandName());
				}
				or.setCarPlateNo(w.getCarPlateNo());
				//预估里程（暂未确定）
				or.setMileage(0d);
				or.setMemo(w.getMemo());
				or.setWorkerOrderNo(w.getWorkerOrderNo());
				or.setTerminalParkName(w.getTerminalParkName());
				or.setStartParkName(w.getStartParkName());
				list.add(or);
			}

			result.setData(list);
			result.setCode(CarConstant.success_code);
		}else {
			result.setCode(CarConstant.fail_code);
			result.setMsg(Constant.NO_RECORD);
		}
		return result;
	}
	/**
	 * 方法说明:将按特定条件查询的记录转换成自定vo对象
	 */
	ResultInfo<WorkerOrderPark> workerOrderParkToVo(ResultInfo<WorkerOrderPark> result,String workerNo,String parkNo){

		List<WorkerOrderPark> voList = new ArrayList<WorkerOrderPark>();
		Park p =null;
		if(parkNo!=null){
			p = parkService.getPark(parkNo);
		}

		WorkerOrderPark po = new WorkerOrderPark();
		String addressDetail="";
		if(p!=null){
			if(p.getAddrRegion3Name()!=null&&p.getAddrRegion3Name().length()!=0){
				addressDetail=p.getAddrRegion1Name()+p.getAddrRegion2Name()+p.getAddrRegion3Name()+p.getAddrStreet();
			}else{
				addressDetail=p.getAddrRegion1Name()+p.getAddrRegion2Name()+p.getAddrStreet();
			}
		}
		po.setAddress(addressDetail);

		//获取场站的实时车辆(场站状态的车辆数)
		ParkStatus ps=null;
		if(p!=null){
			if(p.getParkNo()!=null){
				ps=parkStatusService.getParkStatus(p.getParkNo());
			}
		}
		CarStatus car = new CarStatus();
		car.setLocationParkNo(p.getParkNo());
		List<CarStatus> cars=carStatusService.getCarSpaceShortage(new Query(car));
		if(cars!=null&&cars.size()>0){
			po.setNowCarNum(cars.get(0).getCarSpaceShortage());
		}else{
			po.setNowCarNum(0);
		}
		//获取场站的实时任务量
		List<WorkerOrder> workerOList=workerOrderService.getWorkerOrderNumByParkNo(p.getParkNo(),workerNo);
		if(workerOList!=null&&workerOList.size()>0){
			ResultInfo<List<WorkerOrderParkVo>> result1=new ResultInfo<List<WorkerOrderParkVo>>();
			ResultInfo<List<WorkerOrderParkVo>> resultInfo=workerOrderParkToVo1(result1,workerOList);
			po.setWoList(resultInfo.getData());
			po.setNowWorkerOrderNum(workerOList.size());
		}else{
			po.setWoList(null);
			po.setNowWorkerOrderNum(0);
		}
		po.setParkName(p.getParkName());
		po.setParkNo(p.getParkNo());

		result.setData(po);
		result.setCode(OrderConstant.success_code);
		result.setMsg("");

		return result;
	}
	/**
	 * 方法说明:将按特定条件查询的记录转换成自定vo对象
	 */
	ResultInfo<List<CarVo>> carToVo(ResultInfo<List<CarVo>> result,List<Car> carList){

		if (carList!=null&&carList.size()>0) {
			List<CarVo> voList = new ArrayList<CarVo>();
			for (Car car : carList) {
				CarVo or = new CarVo();
				or.setCarModelId(car.getCarModelId());
				or.setCarModelName(car.getCarModelName());
				or.setCarBrandName(car.getCarBrandName());
				or.setCarNo(car.getCarNo());
				or.setCarPhotoUrl1(imgPath+"/"+car.getCarPhotoUrl1());
				or.setCarPlateNo(car.getCarPlateNo());
				or.setMileage(car.getMileage());
				or.setPower(car.getPower());
				CarStatus cs=carStatusService.getCarStatus(car.getCarNo());
				if(cs!=null){
					or.setMileage(cs.getMileage());
					or.setPower(cs.getPower());
					or.setRangeMileage(cs.getRangeMileage());
					or.setUserageStatus(cs.getUserageStatus());
				}
				voList.add(or);
			}
			result.setData(voList);
			result.setCode(CarConstant.success_code);
		}else {
			result.setCode(CarConstant.fail_code);
			result.setMsg(CarConstant.available_car_msg);
		}
		return result;
	}
	/**
	 * 方法说明:将按特定order对象转换成自定vo对象
	 */
	ResultInfo<CarVo> carToVoOne(ResultInfo<CarVo> result,Car car){
		if(car!=null){
			CarVo or = new CarVo();
			or.setCarModelId(car.getCarModelId());
			or.setCarModelName(car.getCarModelName());
			or.setCarBrandName(car.getCarBrandName());
			or.setCarNo(car.getCarNo());
			or.setCarPhotoUrl1(imgPath+"/"+car.getCarPhotoUrl1());
			or.setCarPlateNo(car.getCarPlateNo());
			or.setOnLineStatus(car.getOnlineStatus());
			CarStatus cs=carStatusService.getCarStatus(car.getCarNo());
			if(cs!=null){
				or.setRangeMileage(cs.getRangeMileage());
				or.setUserageStatus(cs.getUserageStatus());
				or.setMileage(cs.getMileage());
				or.setPower(cs.getPower());
			}
			result.setData(or);
			result.setCode(CarConstant.success_code);
			result.setMsg(""); 
		}else{
			result.setCode(CarConstant.fail_code);
			result.setMsg(CarConstant.fail_msg); 
		}

		return result;
	}



	/**
	 * 根据省市显示当前市的所有有场站的区域(区域名称，调度任务数量)
	 * @param addrRegion1Name
	 * @param addrRegion2Name
	 * @param longitude
	 * @param latitude
	 * @return
	 */
	@RequestMapping("/getWorkerListByCityTake")
	@ResponseBody
	public ResultInfo<List<WorkerRegion>> getWorkerListByCityTake(String workerNo,String addrRegion1Name,String addrRegion2Name,String longitude, String latitude) {
		Integer tag=0;
		Query q=new Query();
		Park park=new Park();
		WorkerOrder workerOrder = new WorkerOrder();
		workerOrder.setWorkerId(workerNo);

		if(addrRegion1Name!=null&&!addrRegion1Name.equals("")){
			park.setAddrRegion1Name(addrRegion1Name);
			if(addrRegion1Name.equals("北京市")||addrRegion1Name.equals("上海市")||addrRegion1Name.equals("天津市")||addrRegion1Name.equals("重庆市")){
				tag=1;
			}
		}
		if(addrRegion2Name!=null&&!addrRegion2Name.equals("")){
			park.setAddrRegion2Name(addrRegion2Name);
		}
		park.setIsAvailable(1);
		park.setIsPublic(1);
		park.setIsDeleted(0);
		q.setQ(workerOrder); 

		return parkService.getWorkerListByCityTake(workerNo,q,longitude,latitude,tag);
	}


	/**
	 * 
	 * @param workerNo
	 * @param parkNo
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping("/goAddWorkerOrder")
	@ResponseBody
	public ResultInfo<GoAddCarVo> goAddWorkerOrder(String workerNo,String parkNo) throws ParseException{

		//获取系统参数，电量限制：POWERLIMIT
		//			SysParam powerLimit = sysParamService.getByParamKey(CarConstant.power_limit);
		//			Double power = 0d;
		//			if (powerLimit!=null&&powerLimit.getParamValue()!=null) {
		//				power = Double.parseDouble(powerLimit.getParamValue());
		//			}
		//根据场站ID查找该场站电量最高 
		//先判断是否为空
		List<CarAndStatus> carList = null;

		carList = carService.getCarByParkNoWorker(parkNo);

		ResultInfo<GoAddCarVo> result  = new ResultInfo<GoAddCarVo>();
		if (carList!=null) {

			List<CarAndStatus> cList = new ArrayList<>();
			for (CarAndStatus cars : carList) { 
				cars.setCarPhotoUrl1(imgPath+"/"+cars.getCarPhotoUrl1()); 
				cList.add(cars);
			}

			GoAddCarVo goAddCarVO = new GoAddCarVo();

			goAddCarVO.setCarAndStatusList(cList);

			result.setCode(CarConstant.success_code);
			result.setMsg("");
			result.setData(goAddCarVO);
		}else {
			result.setCode(CarConstant.fail_code);
			result.setMsg(CarConstant.available_car_msg);
		}
		return result;
	}
	
	/**
	 * 根据车牌号获取车辆信息
	 * @param parkId
	 * @param memberNo
	 * @return
	 */
	@RequestMapping("getCarInfoByCarPlateNo")
	@ResponseBody
	public ResultInfo<List<CarVo>> getCarInfoByCarPlateNo(String carPlateNo) {
		ResultInfo<List<CarVo>> result=new ResultInfo<List<CarVo>>();
		List<CarVo> datas = new ArrayList<CarVo>();
		List<Car> cars = carService.getCarsByPlateNo(carPlateNo);
		if(null != cars && cars.size()>0){
			for(Car car:cars){
				CarVo or = new CarVo();
				or.setCarModelId(car.getCarModelId());
				or.setCarModelName(car.getCarModelName());
				or.setCarNo(car.getCarNo());
				or.setCarPhotoUrl1(imgPath+"/"+car.getCarPhotoUrl1());
				or.setCarPlateNo(car.getCarPlateNo());
				or.setCarBrandName(car.getCarBrandName());
				CarStatus status  = carStatusService.getCarStatus(car.getCarNo());
				if(status != null){
					if (status.getRangeMileage()!=null) {
						or.setRangeMileage(status.getRangeMileage());
						//这里这样写是因为 ios已经进入提交审核阶段，然后发现和总里程搞混了，所以临时这样写
						or.setMileage(status.getRangeMileage());
					}else{
						or.setMileage(0.0);
					}
					if(status!=null&&status.getLongitude()!=null&&status.getLatitude()!=null){  // 坐标转换
						double[] bdCoord = ECGeoCoordinateTransformUtil.wgs84tobd09(status.getLongitude(), status.getLatitude());
						or.setLongitude(bdCoord[0]);// 经度（百度坐标）
						or.setLatitude(bdCoord[1]);// 纬度（百度坐标）
					}
					//设置车辆使用状态
					or.setUserageStatus(status.getUserageStatus());
				}else{
					or.setRangeMileage(0.0);
					or.setMileage(0.0);
				}
				or.setPower(car.getPower());
				datas.add(or);
			}
			result.setCode(Constant.SECCUESS);
			result.setMsg("查询成功");
			result.setData(datas);
		}else{
			result.setCode(Constant.FAIL);
			result.setMsg("无该车辆信息");
		}
		return result;
	}
	
	
}
