package cn.com.shopec.core.car.service.impl;

import java.text.ParseException;
import java.util.Date;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.common.constants.Constant;
import cn.com.shopec.common.utils.ECDateUtils;
import cn.com.shopec.core.device.model.Device;
import cn.com.shopec.core.device.service.DeviceService;
import cn.com.shopec.core.map.common.BaiDuApiConstants;
import cn.com.shopec.core.map.model.Track;
import cn.com.shopec.core.map.model.TrackResultInfo;
import cn.com.shopec.core.map.service.BaiduTrackApiService;
import cn.com.shopec.core.monitor.model.CarTrack;
import cn.com.shopec.core.monitor.service.CarTrackService;
import cn.com.shopec.core.resource.service.ParkService;
import cn.com.shopec.core.car.common.CarConstant;
import cn.com.shopec.core.car.model.Car;
import cn.com.shopec.core.car.model.CarStatus;
import cn.com.shopec.core.car.model.CarStatusBlue;
import cn.com.shopec.core.car.model.CarStatusVo;
import cn.com.shopec.core.car.model.LongitudeAndLatitude;
import cn.com.shopec.core.car.service.BlueToothService;
import cn.com.shopec.core.car.service.CarService;
import cn.com.shopec.core.car.service.CarStatusService;

/**
 * CarAccident 服务实现类
 */
@Service
public class BlueToothServiceImpl implements BlueToothService {
	
	private static final Log log = LogFactory.getLog(BlueToothServiceImpl.class);
	@Resource
	private CarService carService;
	@Resource
	private DeviceService deviceService;
	@Resource
	private ParkService parkService;
	@Resource
	private CarStatusService carStatusService;
	@Resource
	private CarTrackService carTrackService;
	@Resource
	private BaiduTrackApiService baiduTrackApiService;
	private static String DEVICE_KEY = "";
	
		static {
			StringBuilder sb = new StringBuilder("");
			for(int i = 0; i < 128; i++) {
				sb.append('0');
			}
			DEVICE_KEY = sb.toString();
		}
	@Override
	public ResultInfo<CarStatusVo> blueToothControl(CarStatusBlue carStatusBlue) {
		ResultInfo<CarStatusVo> resultInfo=new ResultInfo<CarStatusVo>();
		//通过传入的车牌号，获取设备信息并修改设备的最新上报时间
		if(carStatusBlue.getCarPlateNo()!=null&&!carStatusBlue.getCarPlateNo().equals("")){
			Car car=carService.getCarByPlateNo(carStatusBlue.getCarPlateNo());
//			if(car!=null&&car.getDeviceId()!=null&&!car.getDeviceId().equals("")){
//				Device device = deviceService.getDevice(car.getDeviceId());
//				if(device == null) {
//					resultInfo.setMsg("Device not found");
//					return resultInfo;
//				}
//				this.updateDeviceLastReportingTime(device.getTerminalDeviceNo()); //更新设备的最近上报数据时间
//						
//			}
			if(car!=null){
				String carNo = car.getCarNo();
				if(carNo == null || carNo.length() == 0) { //设备中没有车辆编号
					resultInfo.setMsg("Invalid carNo of device");
					return resultInfo;
				}
			}
			if(car!=null){
				LongitudeAndLatitude location = new LongitudeAndLatitude(carStatusBlue.getLongitude(), carStatusBlue.getLatitude()); //车辆位置

				CarStatus carStatus = new CarStatus();
				carStatus.setCarNo(car.getCarNo());
				
				Date now = new Date(); //当前时间
				Date uploadTime = null; //报文上报的时间（来自于报文本身）
				if(carStatusBlue.getNowDate() != null) {
					try {
						uploadTime = ECDateUtils.stringTimeToDateTime(carStatusBlue.getNowDate());
					} catch (ParseException e1) {
						log.error(e1.getMessage(), e1);
					}
				}
				uploadTime = uploadTime == null ? now : uploadTime; //如果取不到报文的上报时间，就用当前时间
				
				if(CarConstant.CAR_STATUS_SHUTDOWN.equals(carStatusBlue.getCarStatus())) {
					carStatus.setCarStatus(CarConstant.CAR_STATUS_SHUTDOWN); //已熄火
				} else if(CarConstant.CAR_STATUS_STARTUP.equals(carStatusBlue.getCarStatus())) {
					carStatus.setCarStatus(CarConstant.CAR_STATUS_STARTUP); //已启动
				}
				if(carStatus.getCarStatus() != null) {
					carStatus.setCsLastReportingTime(uploadTime); //车辆状态数据最后上报时间，使用报文时间
				}
				carStatus.setUpdateTime(now);
				
//				carStatus.setLastReportingTime(carStatus.getUpdateTime()); //车辆数据最后上报时间，因为不是由硬件直接上报的，所以此字段不修改
				carStatus.setAppLastReportingTime(now); //经由App上报数据的最后时间
				
				if(location != null && location.getLongitude() != 0.0d && location.getLatitude() != 0.0d) { //坐标有效，才更新车辆的坐标数据和gps上报时间
					carStatus.setGpsLastReportingTime(uploadTime); //GPS数据最后上报时间，使用报文时间
					carStatus.setLongitude(location.getLongitude());
					carStatus.setLatitude(location.getLatitude());

					String curParkNo = location != null ? this.getCurrentParkNoByCarLocation(location.getLongitude(), location.getLatitude()) : null; //车辆当前所在场站
					curParkNo = curParkNo == null ? "" : curParkNo;
					carStatus.setLocationParkNo(curParkNo);
					
					carStatus.setGpsLastReportingTime(now); //gps数据最后更新时间
				}
				
				if(carStatusBlue.getPower() != null) {
					carStatus.setPower((carStatusBlue.getPower() != null && carStatusBlue.getPower() == 0.0d) ? null : carStatusBlue.getPower());
					carStatus.setAuxBatteryVoltage(carStatusBlue.getAuxBatteryVoltage());
					carStatus.setRangeMileage((carStatusBlue.getRangeMileage() != null && carStatusBlue.getRangeMileage() == 0.0d) ? null : carStatusBlue.getRangeMileage());
					carStatus.setMileage((carStatusBlue.getMileage() != null && carStatusBlue.getMileage() == 0.0d) ? null : carStatusBlue.getMileage());
					carStatus.setBmsLastReportingTime(uploadTime); //电量数据最后上报时间，使用报文时间
					
				}
						
				try {
					ResultInfo<CarStatus> resInfoOfUpdateCarStatus = carStatusService.updateCarStatus(carStatus, null); //更新车辆状态
					resultInfo.setCode(resInfoOfUpdateCarStatus.getCode());
					resultInfo.setMsg(resInfoOfUpdateCarStatus.getMsg());
					if(resultInfo.getCode().equals(Constant.SECCUESS)){
						resultInfo.setData(carStatusToVo(resInfoOfUpdateCarStatus.getData()));
					}
				} catch (Exception e) {
					log.error(e.getMessage(), e);
					return resultInfo;
				}
				if(location != null && location.getLongitude() != 0.0d && location.getLatitude() != 0.0d) { //车辆位置转为车辆轨迹对象
					CarTrack carTrack = new CarTrack();
					carTrack.setLongitude(location.getLongitude());
					carTrack.setLatitude(location.getLatitude());
					carTrack.setCarNo(car.getCarNo());
					carTrack.setCarPlateNo(car.getCarPlateNo());
					carTrack.setCityId(car.getCityId());
					carTrack.setCreateTime(uploadTime); //轨迹的产生时间，使用报文的上报时间 
					
					try {
						carTrackService.addCarTrack(carTrack); //新增一条车辆轨迹信息
						
					} catch(Exception e) { //车辆轨迹即使保存失败，也不回归车辆状态的更新
						log.error(e.getMessage(), e);
					}
					carTrack.setPower(carStatusBlue.getPower()+"");
					this.uploadCarTrack2Map(carTrack); //上传轨迹数据到地图平台
				}
				
			}
			
			
		}else{
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg("参数信息不全--车牌号为空");
		}
		return resultInfo;
	}
	/**
	 * 更新设备的最近上报时间
	 * @param deviceNo
	 * @return
	 */
	@Transactional
	private boolean updateDeviceLastReportingTime(String deviceNo) {
		boolean res = false;
		if(deviceNo != null) {
			try {
				Device device = new Device();
				device.setTerminalDeviceNo(deviceNo); //主键
				Date now = new Date();
				device.setLastReportingTime(now);
				device.setUpdateTime(now);
				
				ResultInfo<Device> resInfo = deviceService.updateDevice(device, null);
				if(Constant.SECCUESS.equals(resInfo.getCode())) {
					res = true;
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return res;
	}
	/**
	 * 根据坐标取车辆当前所在场站的编号（不一定有，如果附近没有场站，则返回空串）
	 * @param longitude
	 * @param latitude
	 * @return
	 */
	private String getCurrentParkNoByCarLocation(Double longitude, Double latitude) {
		return parkService.getCurrentParkNoByCarLocation(longitude,latitude);
	}
	/**
	 * 上传车辆轨迹到地图平台
	 * @param carTrack
	 * @return
	 */
	private boolean uploadCarTrack2Map(CarTrack carTrack) {
		boolean res = false;
		if(carTrack != null && carTrack.getCarNo() != null) {
			try {
				Track track = new Track();
				track.setEntityName(carTrack.getCarNo());
				track.setCoordType(BaiDuApiConstants.COORD_TYPE_GPS); //坐标系类型为：GPS坐标系
				track.setLongitude(carTrack.getLongitude());
				track.setLatitude(carTrack.getLatitude());
				track.setLocTime(carTrack.getCreateTime());
				
				ResultInfo<TrackResultInfo> resInfo = baiduTrackApiService.addPoint(track); //调用百度接口服务，新增一个轨迹点
				if(Constant.SECCUESS.equals(resInfo.getCode())) {
					res = true;
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return res;
	}
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
		return vo;
	}
}
