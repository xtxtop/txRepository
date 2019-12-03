package cn.com.shopec.mapi.car.controller;


import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.core.car.common.CarConstant;
import cn.com.shopec.core.car.model.CarStatus;
import cn.com.shopec.core.car.service.CarService;
import cn.com.shopec.core.car.service.CarStatusService;
import cn.com.shopec.core.device.model.Device;
import cn.com.shopec.core.device.service.DeviceService;
import cn.com.shopec.core.order.model.Order;
import cn.com.shopec.core.order.service.OrderService;
import cn.com.shopec.mapi.car.vo.CarStatusVo;
import cn.com.shopec.mapi.common.controller.BaseController;

@Controller
@RequestMapping("/app/carStatus")
public class CarStatusController extends BaseController{
	
	private static String DEVICE_KEY = "";
	
	static {
		StringBuilder sb = new StringBuilder("");
		for(int i = 0; i < 32; i++) {
			sb.append('0');
		}
		DEVICE_KEY = sb.toString();
	}
	
	@Resource
	private CarService carService;
	
	@Resource
	private CarStatusService carStatusService;
	
	@Resource 
	private OrderService orderService;
	
	@Resource
	private DeviceService deviceService;
		/**
		 * 查看正在行驶中的汽车的汽车状态carStatus
		 * */
		@RequestMapping("/carStatus")
		@ResponseBody
		public ResultInfo<CarStatusVo> carStatus(String orderNo,String memberNo) {
			ResultInfo<CarStatusVo> resultInfo=new ResultInfo<CarStatusVo>();
			Order order = orderService.getOrder(orderNo);
			CarStatus carStatus=carStatusService.getCarStatus(order.getCarNo());
			if(carStatus!=null){
				CarStatusVo vo = carStatusToVo(carStatus);
				resultInfo.setCode(CarConstant.success_code);
				resultInfo.setMsg("");
				vo.setOrderNo(orderNo);
				resultInfo.setData(vo);
			}else{
				resultInfo.setCode(CarConstant.fail_code);
				resultInfo.setMsg(CarConstant.fail_msg);
			}
			return resultInfo;
		}
		/**
		 *carStatus对象转vo对象
		 */
		private CarStatusVo carStatusToVo(CarStatus status) {
			CarStatusVo vo = new CarStatusVo();
			vo.setCarNo(status.getCarNo());
			vo.setCarPlateNo(status.getCarPlateNo());
			vo.setCarStatus(status.getCarStatus());
			vo.setChargeState(status.getChargeState());
			vo.setDeviceNo(status.getDeviceNo());
			vo.setLeftFrontDoorLock(status.getLeftFrontDoorLock());
			vo.setLeftRearDoorLock(status.getLeftRearDoorLock());
			vo.setLocationParkNo(status.getLocationParkNo());
			if(status.getChargeState() != null){
				vo.setChargeState(status.getChargeState());
			}else{
				vo.setChargeState(0);
			}
		
//			vo.setOrderNo();		这里个放上面的方法中处理
//			只有当前场站编号。	location_park_no 	如果这个字段里面有有效的值（非null，非""）的，就算是在场站范围内
			if (status.getLocationParkNo()!=null&&!status.getLocationParkNo().equals("")) {
				vo.setPlaceStatus(0);  
			}else {
				vo.setPlaceStatus(1);  
			}

			vo.setPower(status.getPower());
			vo.setRangeMileage(status.getRangeMileage());
			vo.setRightFrontDoorLock(status.getRightFrontDoorLock());
			vo.setRightRearDoorLock(status.getRightRearDoorLock());
			vo.setSpeed(status.getSpeed());
			vo.setUserageStatus(status.getUserageStatus());
			Device d = deviceService.getDevice(status.getDeviceNo());
			if (d!=null) {
				vo.setDeviceSn(d.getDeviceSn());
				vo.setMacAddr(d.getMacAddr());
				vo.setDeviceKey(DEVICE_KEY);
			}
			//钥匙在线状态（1、在线，0、不在线，默认1）
			vo.setKeyOnlineStatus(status.getKeyOnlineStatus());
			//钥匙状态（1，OFF、2，ON、3，ACC、4，START、5，未知）
			vo.setKeyStatus(1);
			return vo;
		}
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		

}
