package cn.com.shopec.mgt.mq;

import java.text.ParseException;
import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;

import com.google.gson.Gson;

import cn.com.shopec.common.utils.ECDateUtils;
import cn.com.shopec.common.utils.ECGeoCoordinateTransformUtil;
import cn.com.shopec.common.utils.JsonUtils;
import cn.com.shopec.core.car.common.CarConstant;
import cn.com.shopec.core.car.model.Car;
import cn.com.shopec.core.car.model.CarStatus;
import cn.com.shopec.core.car.service.CarService;
import cn.com.shopec.core.car.service.CarStatusService;
import cn.com.shopec.core.device.model.Device;
import cn.com.shopec.core.device.service.DeviceService;
import cn.com.shopec.core.monitor.model.CarMonitor;
import cn.com.shopec.core.order.dao.OrderDao;
import cn.com.shopec.core.order.model.Order;
import cn.com.shopec.core.order.service.OrderService;
import cn.com.shopec.core.resource.model.Park;
import cn.com.shopec.core.resource.service.ParkService;
import cn.com.shopec.core.scheduling.dao.WorkerOrderDao;
import cn.com.shopec.core.scheduling.model.WorkerOrder;
import cn.com.shopec.core.scheduling.service.WorkerOrderService;
import cn.com.shopec.mgt.deviceUploadPkgParser.DeviceUploadPkgCmd24;
import cn.com.shopec.mgt.deviceUploadPkgParser.LongitudeAndLatitude;
import cn.com.shopec.mgt.websocket.MyWebSocketHandler;

@Service
public class SendDeviceMsgToClient{
	@Resource
	private DeviceService deviceService;
	@Resource
	private CarStatusService carStatusService;
	@Resource
	private CarService carService;
	@Resource
	private ParkService parkService;
	@Resource
	private OrderService orderService;
	@Resource
	private OrderDao orderDao;
	@Resource
	private WorkerOrderService workerOrderService;
	@Resource
	private WorkerOrderDao workerOrderDao;
	@Resource
	private MyWebSocketHandler myWebSocketHandler;
	
	public boolean sendCmd24MsgToClinet(DeviceUploadPkgCmd24  cmd24){
		String deviceSn = cmd24.getDeviceSn();
		Device device = deviceService.getDeviceByDeviceSn(deviceSn); //根据设备序列号查询设备
		String carNo = device.getCarNo();
		
		//websocket推送客户端对象组装
		CarMonitor carMonitor = new CarMonitor();
		carMonitor.setCarNo(carNo);
		carMonitor.setCarPlateNo(device.getCarPlateNo());
		if(cmd24.getKeyStatus()!=null){
			if("1".equals(cmd24.getKeyStatus())) { //钥匙OFF
				carMonitor.setCarStatus(CarConstant.CAR_STATUS_SHUTDOWN); //熄火
			} else if("2".equals(cmd24.getKeyStatus())) { //钥匙ON
				carMonitor.setCarStatus(CarConstant.CAR_STATUS_STARTUP); //启动
			}
		}else{
			carMonitor.setCarStatus(255);
		}
		carMonitor.setSpeed(cmd24.getSpeed() == null ? null : cmd24.getSpeed().intValue());;
		carMonitor.setPower((cmd24.getRemainPower() != null && cmd24.getRemainPower() == 0.0d) ? null : cmd24.getRemainPower());
		carMonitor.setRangeMileage((cmd24.getRange() != null && cmd24.getRange() == 0.0d) ? null : cmd24.getRange());
		CarStatus cs  = carStatusService.getCarStatus(carNo);
		carMonitor.setUserageStatus(cs.getUserageStatus());
		LongitudeAndLatitude location = cmd24.getLocation();
		double[] bdCoord = ECGeoCoordinateTransformUtil.wgs84tobd09(location.getLongitude(), location.getLatitude());
		carMonitor.setLongitude(bdCoord[0]);//经度（百度坐标）
		carMonitor.setLatitude(bdCoord[1]);//纬度（百度坐标）
		carMonitor.setCourseAngle(cmd24.getCourseAngle());//航向角度
		Date uploadTime = null;
		try {
			uploadTime = ECDateUtils.parseTime(cmd24.getTimestamp());
		} catch (ParseException e) {
			uploadTime = new Date();
		}
		carMonitor.setTimeDiff(ECDateUtils.differSeconds(cs.getGpsLastReportingTime(), uploadTime).intValue());
		if(carMonitor.getUserageStatus()==1){
			Order order = orderDao.getOrderByCarNo(carNo);
			if(order!=null){
				carMonitor.setOrder(order);
			}else{
				WorkerOrder workerOrder = workerOrderDao.getWorderOrderByCarNo(carNo);
				if(workerOrder!=null){
					carMonitor.setWorkerOrder(workerOrder);
				}
			}
		}else if(carMonitor.getUserageStatus()==2){//车辆订单中
			Order order = orderService.getOrderNowByCarNo(carNo);
			carMonitor.setOrder(order);
		}else if(carMonitor.getUserageStatus()==3){//车辆调度单中
			WorkerOrder workerOrder = workerOrderService.getWorkerOrderNowByCarNo(carNo);
			carMonitor.setWorkerOrder(workerOrder);
		}else if(carMonitor.getUserageStatus()==0){//车辆状态空闲，找最近的订单或者调度单
			Order order = orderService.getOrderNewestByCarNo(carNo);
			if(order!=null){
				carMonitor.setOrder(order);
				Park park = parkService.getPark(order.getTerminalParkNo());
				if(park!=null){
					order.setActualTerminalParkName(park.getParkName());
				}
			}else{
				WorkerOrder workerOrder = workerOrderDao.getWorkerOrderNewestByCarNo(carNo);
				if(workerOrder!=null){
					carMonitor.setWorkerOrder(workerOrder);
				}
			}
		}
		Gson gson = new Gson();
		String message = gson.toJson(carMonitor);
		boolean res = myWebSocketHandler.sendMessageToCarMonitorUsers(new TextMessage(message));
		return res;
	}
	public boolean sendWarningMsgToClinet(String msg){
		String carNo = msg.split(",")[0];
		String carPlateNo = msg.split(",")[1];
		CarMonitor carMonitor = new CarMonitor();
		carMonitor.setCarPlateNo(carPlateNo);
		CarStatus cs = carStatusService.getCarStatus(carNo);
		carMonitor.setLongitude(cs.getLongitude());
		carMonitor.setLatitude(cs.getLatitude());
		carMonitor.setPower(cs.getPower());
		carMonitor.setCarStatus(cs.getCarStatus());
		Order order = orderService.getOrderNewestByCarNo(carNo);
		if(order!=null){
			carMonitor.setOrder(order);
		}else{
			WorkerOrder workerOrder = workerOrderDao.getWorkerOrderNewestByCarNo(carNo);
			if(workerOrder!=null){
				carMonitor.setWorkerOrder(workerOrder);
			}
		}
		Gson gson = new Gson();
		String message = gson.toJson(carMonitor);
		boolean res = myWebSocketHandler.sendMessageToNoOrderUsers(new TextMessage(message));
		return res;
	}
}
