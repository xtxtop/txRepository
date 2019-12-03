
package cn.com.shopec.quartz.utils;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.StringUtils;

import cn.com.shopec.core.car.model.CarStatus;
import cn.com.shopec.core.car.service.CarStatusService;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.order.model.Order;
import cn.com.shopec.core.order.service.OrderService;
import cn.com.shopec.core.quartz.service.QuartzService;
import cn.com.shopec.core.resource.model.Park;
import cn.com.shopec.core.resource.model.ParkStatus;
import cn.com.shopec.core.resource.service.ParkService;
import cn.com.shopec.core.resource.service.ParkStatusService;
import cn.com.shopec.core.system.service.SysParamService;

/**
 * 扫描空闲车辆数量
 * 
 * @author machao
 * @date 2016年10月11日
 */
public class CarSpaceShortageQuartz {

	private Log logger = LogFactory.getLog(CarSpaceShortageQuartz.class);

	private static final String ZERO = "0";
	/**
	 * 每次到数据库取数据的最大数
	 */
	private static final int maxNum = 500;
	@Resource
	private SysParamService sysParamService;

	@Resource
	private QuartzService quartzService;

	@Resource
	private CarStatusService carStatusService;

	@Resource
	private ParkStatusService parkStatusService;
	
	@Resource
	private ParkService parkService;
	
	@Resource
	private OrderService orderService;
	
	public void quartzStart() throws Exception {
		//空闲车位数量阀值
		String carSpaceShortageStr = sysParamService.getByParamKey("CarSpaceShortage").getParamValue();
		if(carSpaceShortageStr != null && !StringUtils.isEmpty(carSpaceShortageStr)){
			Integer carSpaceShortage = Integer.parseInt(carSpaceShortageStr);
			//查询场站已占车位数量
			List<CarStatus> carStatusList = carStatusService.getCarSpaceShortage(new Query());
			List<ParkStatus> parkStatusList = parkStatusService.getParkStatusList(new Query());
			logger.info("---扫描场站空闲车位数量完成，开始生成警告信息...");
			if(carStatusList!=null&&parkStatusList!=null&&parkStatusList.size()>0&&carStatusList.size()>0){
				for (CarStatus carStatus : carStatusList) {
						for(ParkStatus parkStatus:parkStatusList){
							if (carStatus != null && !StringUtils.isEmpty(carStatus.getLocationParkNo())) {
								if(parkStatus.getParkNo().equals(carStatus.getLocationParkNo())){
									Park p=parkService.getPark(parkStatus.getParkNo());
									//总车位
									//总车位
									int totalCarSpace=0;
									if(p!=null && p.getParkingSpaceNumber() != null){
										 totalCarSpace = p.getParkingSpaceNumber();
									}
									
									//空闲车位
									//parkStatus.setFreeParking(parkStatus.getParkingSpaces()-(parkStatus.getCarNumber()-parkStatus.getCarOut()));//空闲
									CarStatus carStatusSearch=new CarStatus();
									carStatusSearch.setLocationParkNo(parkStatus.getParkNo());
									int appointCarNum=carStatusService.countParkStatusUserage(new Query(carStatusSearch)).intValue();//预约车辆数
									Order order=new Order();
									order.setStartParkNo(parkStatus.getParkNo());
									int carOutNum=orderService.countscCar(new Query(order)).intValue();//驶出车辆数
									int remainCarSpace= totalCarSpace-(appointCarNum-carOutNum);
									//空闲车位数量
									if(remainCarSpace<=carSpaceShortage&&remainCarSpace>0){
										quartzService.dealcarSpaceShortageQuartz(carStatus,totalCarSpace,remainCarSpace);
									}
								}
							}
						}
				}
			}
		}else{
			logger.info("---没有配置空闲车位数量的系统参数...");
		}
	}

	protected void execute(String arg0) throws Exception {
		// 定时任务开关，0：关 1：开
		String quartzSwitch = ZERO;

		try {
			quartzSwitch = arg0;
		} catch (Exception e) {
			logger.error("---读取定时任务开关信息出错，错误信息：" + e.getMessage(), e);
			return;
		}

		// 定时任务开关配置为0时，则关闭
		if (StringUtils.isEmpty(quartzSwitch) || quartzSwitch.equals(ZERO)) {
			logger.info("---定时任务为关闭状态...");
			return;
		}

		try {
			logger.info("--------扫描空闲车位数量任务，开始时间：" + new Date());
			// 执行业务方法
			quartzStart();

			logger.info("--------扫描空闲车位数量任务完成...");
		} catch (Exception e) {
			logger.error("--------扫描空闲车位数量任务出错，错误信息：" + e.getMessage(), e);
		}
	}

}
