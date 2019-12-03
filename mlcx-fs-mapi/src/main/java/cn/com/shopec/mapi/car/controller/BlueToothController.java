package cn.com.shopec.mapi.car.controller;

import java.text.ParseException;
import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;

import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.common.constants.Constant;
import cn.com.shopec.common.utils.ECDateUtils;
import cn.com.shopec.common.utils.ECNumberUtils;
import cn.com.shopec.core.car.common.CarConstant;
import cn.com.shopec.core.car.model.CarStatus;
import cn.com.shopec.core.car.model.CarStatusBlue;
import cn.com.shopec.core.car.model.CarStatusVo;
import cn.com.shopec.core.car.service.BlueToothService;
import cn.com.shopec.core.car.service.CarService;
import cn.com.shopec.core.car.service.CarStatusService;
import cn.com.shopec.core.device.model.Device;
import cn.com.shopec.core.device.service.DeviceService;
import cn.com.shopec.core.marketing.model.PricingRule;
import cn.com.shopec.core.marketing.service.PricingRuleService;
import cn.com.shopec.core.member.service.MemberService;
import cn.com.shopec.core.order.common.OrderConstant;
import cn.com.shopec.core.order.model.Order;
import cn.com.shopec.core.order.service.OrderService;
import cn.com.shopec.core.resource.model.Park;
import cn.com.shopec.core.resource.service.ParkService;
import cn.com.shopec.core.scheduling.service.WorkerOrderService;
import cn.com.shopec.core.system.model.SysParam;
import cn.com.shopec.core.system.service.SysParamService;
import cn.com.shopec.core.uploadpkg.model.DeviceUploadpkgLog;
import cn.com.shopec.core.uploadpkg.service.DeviceUploadpkgLogService;
import cn.com.shopec.mapi.common.controller.BaseController;
import cn.com.shopec.mapi.order.vo.OrderVO;

@Controller
@RequestMapping("/app/blueTooth")
public class BlueToothController extends BaseController{
	
	
	@Resource
	private BlueToothService blueToothService;
	@Resource
	private OrderService orderService;
	@Resource
	private CarService carService;
	@Resource
	private DeviceService deviceService;
	@Resource
	private CarStatusService carStatusService;
	@Resource
	private ParkService parkService;
	@Resource
	private MemberService memberService;
	@Resource
	private PricingRuleService pricingRuleService;
	@Resource
	private WorkerOrderService workerOrderService;
	@Resource
	private SysParamService sysParamService;
	@Resource
	private DeviceUploadpkgLogService deviceUploadpkgLogService;
	
	/**
	 * 蓝牙接口
	 * */
	@RequestMapping("/blueToothControl")
	@ResponseBody
	//车辆熄火及GPS信息
	//需要做的功能（一个是更新熄火状态，一个是更新gps、更新gps上报时间、根据gps获取当前所在场站的编号（可能为"")，这些都是需要更新到CarStatus表。
	//然后创建车辆轨迹，保存车辆轨迹、推送车辆轨迹到百度地图）
	public ResultInfo<CarStatusVo> getCarControl(CarStatusBlue carStatusBlue,String carStatusBlueTooth){
		if(!"".equals(carStatusBlueTooth)&&carStatusBlueTooth!=null){
			DeviceUploadpkgLog deviceUploadpkgLog = new DeviceUploadpkgLog();
			deviceUploadpkgLog.setCmdType("24");
			if(carStatusBlue!=null){
				deviceUploadpkgLog.setDeviceSn(carStatusBlue.getDeviceNo());
			}
			deviceUploadpkgLog.setChiniseContent("蓝牙报文");
			deviceUploadpkgLog.setLogContent(carStatusBlueTooth);
			deviceUploadpkgLogService.addDeviceUploadpkgLog(deviceUploadpkgLog, null);
		}
		return blueToothService.blueToothControl(carStatusBlue);
		
	}
	/**
	 * app蓝牙开门成功后转计费回调接口
	 * @param orderNo
	 * @param workerNo
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping("blueToothUpdateOrder")
	@ResponseBody
	public ResultInfo<String> blueToothUpdateOrder(String orderNo,String workerNo,String startBillingTime) throws ParseException{
		ResultInfo<String> result = new ResultInfo<>();
		result.setCode(Constant.FAIL);
		Order order = orderService.getOrder(orderNo);
		String carNo = order.getCarNo();
		ResultInfo<String> res = new ResultInfo<String>();
		//startBillingTime为空开门时间（计费时间）则为当前时间
		Date openDoorTime = ECDateUtils.getCurrentDateTime();
		if(startBillingTime!=null&&!"".equals(startBillingTime)){
			openDoorTime = ECDateUtils.parseTime(startBillingTime);//开门时间也为计费时间
		}
		//倒计时计费分钟数
		SysParam sysp = sysParamService.getByParamKey(CarConstant.cancelordertime_param_key);
		int dingshi = 20;
		if (sysp != null && sysp.getParamValue() != null && !sysp.getParamValue().equals("")) {
			dingshi = Integer.parseInt(sysp.getParamValue());
		}
		//倒计时计费时间
		Date djsTime =  new Date(order.getCreateTime().getTime()+dingshi*60*1000);
		if(openDoorTime.compareTo(order.getCreateTime())<0||openDoorTime.compareTo(djsTime)>0){
			openDoorTime = djsTime;
		}
		if(orderNo!=null&&!"".equals(orderNo)){
			res = orderService.updateFirsttimeOpenCarDoorAndStartBillingByCarNo(carNo, openDoorTime); //根据车辆号，尝试将跟该车辆有关的待计费订单，转为计费状态
			if(Constant.SECCUESS.equals(res.getCode())){
				result.setCode(Constant.SECCUESS);
			}else if(Constant.FAIL.equals(res.getCode())&&OrderConstant.fail_msg.equals(res.getMsg())){
				result.setCode(Constant.FAIL);
			}else{
				result.setCode(Constant.SECCUESS);
			}
		}
		if(workerNo!=null&&!"".equals(workerNo)){
			res = workerOrderService.startWorkerOrderByCarNo(carNo, openDoorTime); //根据车辆号，尝试将该车辆有关的待开始的调度单，转为调度已开始的状态
			if(Constant.SECCUESS.equals(res.getCode())){
				result.setCode(Constant.SECCUESS);
			}else if(Constant.FAIL.equals(res.getCode())&&OrderConstant.fail_msg.equals(res.getMsg())){
				result.setCode(Constant.FAIL);
			}else{
				result.setCode(Constant.SECCUESS);
			}
		}
		return result;
	}
	/**
	 * 蓝牙还车接口（适用于硬件服务器和硬件通信不同，手机和app接口通信通畅）-手机蓝牙发送换车指令成功后条用此接口处理还车后的业务
	 * @throws ParseException 
	 * */
	@RequestMapping("blueToothReturnCar")
	@ResponseBody
	public ResultInfo<OrderVO> blueToothReturnCar(String orderNo,String memberNo) throws ParseException{

		ResultInfo<Order> resultInfo=new ResultInfo<Order>();
		//预留（还车时，要判断汽车状态是否符合还车要求）
		ResultInfo<OrderVO> result=new ResultInfo<OrderVO>();
		Order order = orderService.getOrder(orderNo);
		//判断订单状态不为2，不能结算订单，防止重复提交结算
		if(order!=null&&order.getOrderStatus()!=2){
			result.setCode(Constant.SECCUESS);//返回1，客户端提示还车成功
			return result;
		}
		CarStatus carStatus = carStatusService.getCarStatus(order.getCarNo());
		//根据传的车辆位置，查找周边场站距离最近的场站
		String parkNo="";
		//直接用t_car_status中的location_park_no判断即可，不需要在此调用solr
		//parkNo=parkService.getCurrentParkNoByCarLocation(carStatus.getLongitude(),carStatus.getLatitude());
		//if(carStatus.getLocationParkNo()!=null){
			 parkNo=carStatus.getLocationParkNo() == null ? "" : carStatus.getLocationParkNo();
		//}
		resultInfo=carStatusService.returnCar(orderNo,parkNo,getOperator());
		result =  orderToVoOne(result,resultInfo.getData());
		return result;
	}
	 /**
	 * 方法说明:将按特定order对象转换成自定vo对象
	 */
	 ResultInfo<OrderVO> orderToVoOne(ResultInfo<OrderVO> result,Order order){
		 if(order!=null){
				OrderVO or = new OrderVO();
				or.setAppointmentTime(ECDateUtils.formatTime(order.getAppointmentTime()));
				or.setMemberNo(order.getMemberNo());
				or.setOrderAmount(order.getOrderAmount());
				or.setOrderNo(order.getOrderNo());
				or.setOrderStatus(order.getOrderStatus());
				or.setPayableAmount(order.getPayableAmount());
				or.setPayStatus(order.getPayStatus());
				if(order.getOrderMileage()!=null){
					or.setOrderMileage(ECNumberUtils.roundDoubleWithScale(order.getOrderMileage(),2));
				}else{
					or.setOrderMileage(0d);
				}
				
				if(order.getDiscountAmount()!=null){
					or.setDiscountAmount(order.getDiscountAmount());
				}else{
					or.setDiscountAmount(0d);
				}
				
				if(order.getPackMinutesDiscountAmount()!=null){
					or.setPackMinutesDiscountAmount(order.getPackMinutesDiscountAmount());
				}else{
					or.setPackMinutesDiscountAmount(0d);
				}
				
				or.setNeedPayaAmount(ECNumberUtils.roundDoubleWithScale(order.getOrderAmount()-order.getPackMinutesDiscountAmount(),2));
				if(order.getPayStatus().equals(0)){
					or.setAlreadPayAmount(0d);
				}else if(order.getPayStatus().equals(1)){
					or.setAlreadPayAmount(ECNumberUtils.roundDoubleWithScale(order.getPayableAmount(),2));
				}
				if(order.getActualTerminalParkNo()!=null&&!order.getActualTerminalParkNo().equals("")){
					Park p=parkService.getPark(order.getActualTerminalParkNo());
					if(p!=null){
						String location = p.getAddrRegion1Name()+p.getAddrRegion2Name()+p.getAddrRegion3Name()+p.getAddrStreet();
						or.setActualTerminalLocation(location);
					}
				}
				or.setIntegral(0);
				or.setCreateTime(ECDateUtils.formatTime(order.getCreateTime()));
				//获取 起步价
				if(!"".equals(order.getRuleNo())&&order.getRuleNo() !=null){
					PricingRule pricingRule=pricingRuleService.getPricingRule(order.getRuleNo());
					if(pricingRule != null){
						or.setBaseFee(pricingRule.getBaseFee());
					}else{
						or.setBaseFee(0d);
					}
				}else{
					or.setBaseFee(0d);
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
	
		 
}
