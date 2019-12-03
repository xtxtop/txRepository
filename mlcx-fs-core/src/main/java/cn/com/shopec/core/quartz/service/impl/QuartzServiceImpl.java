
package cn.com.shopec.core.quartz.service.impl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import cn.com.shopec.common.Operator;
import cn.com.shopec.common.exception.QuartzException;
import cn.com.shopec.common.sendmsg.HyInterface;
import cn.com.shopec.common.sendmsg.SendMessage;
import cn.com.shopec.common.utils.ECCalculateUtils;
import cn.com.shopec.common.utils.ECDateUtils;
import cn.com.shopec.core.car.model.Car;
import cn.com.shopec.core.car.model.CarStatus;
import cn.com.shopec.core.car.service.CarService;
import cn.com.shopec.core.car.service.CarStatusService;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.finace.service.ImportAccountsFromThirdService;
import cn.com.shopec.core.marketing.model.PricingPackage;
import cn.com.shopec.core.marketing.service.PricingPackageService;
import cn.com.shopec.core.member.dao.MemberDao;
import cn.com.shopec.core.member.service.MemberService;
import cn.com.shopec.core.monitor.model.Warning;
import cn.com.shopec.core.monitor.service.WarningService;
import cn.com.shopec.core.order.dao.OrderDao;
import cn.com.shopec.core.order.model.Order;
import cn.com.shopec.core.order.service.OrderService;
import cn.com.shopec.core.quartz.model.RunDaily;
import cn.com.shopec.core.quartz.service.QuartzService;
import cn.com.shopec.core.resource.model.Park;
import cn.com.shopec.core.resource.service.ParkService;
import cn.com.shopec.core.system.service.SysOpLogService;
import cn.com.shopec.core.system.service.SysParamService;

/**
 * 定时任务服务类
 * 
 * @author machao
 * @date 2016年9月30日
 * @version 1.0
 */
@Service
public class QuartzServiceImpl implements QuartzService {

	private Log logger = LogFactory.getLog(QuartzServiceImpl.class);
	@Resource
	public SysOpLogService opLogService;

	@Resource
	private OrderService orderService;

	@Resource
	private SysParamService sysParamService;

	@Resource
	private MemberService memberService;

	@Resource
	private WarningService warningService;

	@Resource
	private ParkService parkService;
	
	@Resource
	private CarService carService;
	
	@Resource
	private PricingPackageService pricingPackageService;
	
	@Resource
	private CarStatusService carStatusService;
	
	@Resource
	private MemberDao memberDao;
	
	@Resource
	private OrderDao orderDao;
	
	@Resource
	ImportAccountsFromThirdService importService;
	
	
	@Resource
	private SendMessage sendMsg;
	@Resource(name = "messageJTDServiceImpl")
	private HyInterface hyInterface;
	/**
	 * 查找到符合条件的订单后执行的业务逻辑
	 */
	@Override
	public void dealMemberOrderQuartz(Order order) throws QuartzException {
		// 查找到欠费超期3天的订单，记录到警告表
		Warning warning = new Warning();
		warning.setOrderNo(order.getOrderNo());
		List<Warning> lists = warningService.getWarningList(new Query(warning));
		Date nowTime = new Date();
		if (lists != null && lists.size() > 0) {
			warningService.delWarning(lists.get(0).getWarningNo(), null);
			Warning w1 = new Warning();
			w1.setCarPlateNo(order.getCarPlateNo());
			w1.setCityId(order.getCityId());
			w1.setCityName(order.getCityName());
			w1.setIsClosed(0);
			w1.setIsNeedManualClosed(1);
			w1.setMemberName(order.getMemberName());
			w1.setMemberNo(order.getMemberNo());
			w1.setOrderNo(order.getOrderNo());
			long orderPassDay = (nowTime.getTime() - order.getFinishTime().getTime()) / (1000 * 24 * 60 * 60);
			w1.setWarningContent(
					"订单欠费" + order.getPayableAmount() + "元，超期" + ECCalculateUtils.convertsToInt(orderPassDay) + "天");
			w1.setWarningLevel(1);
			w1.setWarningNo(lists.get(0).getWarningNo());
			w1.setWarningType("会员");
			w1.setWarningSubType("欠费超期");
			w1.setWarningTime(nowTime);
			w1.setUpdateTime(nowTime);
			w1.setOperatorType(0);
			Operator operator = new Operator();
			operator.setOperatorType(0);
			warningService.addWarning(w1, operator);
		} else {
			Warning w2 = new Warning();
			w2.setCarPlateNo(order.getCarPlateNo());
			w2.setCityId(order.getCityId());
			w2.setCityName(order.getCityName());
			w2.setIsClosed(0);
			w2.setIsNeedManualClosed(1);
			w2.setMemberName(order.getMemberName());
			w2.setMemberNo(order.getMemberNo());
			w2.setOrderNo(order.getOrderNo());
			long orderPassDay = (nowTime.getTime() - order.getFinishTime().getTime()) / (1000 * 24 * 60 * 60);
			w2.setWarningContent(
					"订单欠费" + order.getPayableAmount() + "元，超期" + ECCalculateUtils.convertsToInt(orderPassDay) + "天");
			w2.setWarningLevel(1);
			w2.setWarningType("会员");
			w2.setWarningSubType("欠费超期");
			w2.setWarningTime(nowTime);
			w2.setCreateTime(nowTime);
			w2.setUpdateTime(nowTime);
			w2.setOperatorType(0);
			Operator operator = new Operator();
			operator.setOperatorType(0);
			warningService.addWarning(w2, operator);
		}
	}

	@Override
	public void dealIdleCarQuartz(Order order,Car car) {
		Warning warning = new Warning();
		if(order!=null){
			warning.setCarPlateNo(order.getCarPlateNo());
		}else{
			warning.setCarPlateNo(car.getCarPlateNo());
		}
		List<Warning> lists = warningService.getWarningList(new Query(warning));
		Date nowTime = new Date();
		if (lists != null && lists.size() > 0) {
			Warning w1 = new Warning();
			if(order!=null){
				warningService.delWarning(lists.get(0).getWarningNo(), null);
				w1.setCarPlateNo(order.getCarPlateNo());
				w1.setCityId(order.getCityId());
				w1.setCityName(order.getCityName());
				w1.setParkName(order.getActualTakeLoacton());
				long idleTime = (nowTime.getTime() - order.getFinishTime().getTime()) / (1000 * 60 * 60);
				w1.setWarningContent("车辆闲置时间：" + ECCalculateUtils.convertsToInt(idleTime) + "小时");
			}
			if(car!=null){
				warningService.delWarning(lists.get(0).getWarningNo(), null);
				w1.setCarPlateNo(car.getCarPlateNo());
				w1.setCityId(car.getCityId());
				w1.setCityName(car.getCityName());
				if(carStatusService.getCarStatus(car.getCarNo())!=null){
					CarStatus cs=carStatusService.getCarStatus(car.getCarNo());
					if(cs.getLocationParkNo()!=null&& !"".equals(cs.getLocationParkNo())){
						w1.setParkNo(cs.getLocationParkNo());
						Park park = parkService.getPark(cs.getLocationParkNo());
						w1.setParkName(park.getParkName());	
					}
				}
				long idleTime = (nowTime.getTime() - car.getOnlineStatusUpdateTime().getTime()) / (1000 * 60 * 60);
				w1.setWarningContent("车辆闲置时间：" + ECCalculateUtils.convertsToInt(idleTime) + "小时");
			}
			w1.setIsClosed(0);
			w1.setIsNeedManualClosed(1);
			w1.setWarningLevel(1);
			w1.setWarningNo(lists.get(0).getWarningNo());
			w1.setWarningType("车辆");
			w1.setWarningSubType("闲置时间过长");
			w1.setWarningTime(nowTime);
			w1.setUpdateTime(nowTime);
			w1.setOperatorType(0);
			Operator operator = new Operator();
			operator.setOperatorType(0);
			warningService.addWarning(w1, operator);
		} else {
			Warning w2 = new Warning();
			if(order!=null){
				w2.setCarPlateNo(order.getCarPlateNo());
				w2.setCityId(order.getCityId());
				w2.setCityName(order.getCityName());
				w2.setParkName(order.getActualTakeLoacton());
				long idleTime = (nowTime.getTime() - order.getFinishTime().getTime()) / (1000 * 60 * 60);
				w2.setWarningContent("车辆闲置时间：" + ECCalculateUtils.convertsToInt(idleTime) + "小时");
			}
			if(car!=null){
				w2.setCarPlateNo(car.getCarPlateNo());
				w2.setCityId(car.getCityId());
				w2.setCityName(car.getCityName());
				if(carStatusService.getCarStatus(car.getCarNo())!=null){
					CarStatus cs=carStatusService.getCarStatus(car.getCarNo());
					if(cs.getLocationParkNo()!=null&& !"".equals(cs.getLocationParkNo())){
						w2.setParkNo(cs.getLocationParkNo());
						Park park = parkService.getPark(cs.getLocationParkNo());
						w2.setParkName(park.getParkName());	
					}
				}
				long idleTime = (nowTime.getTime() - car.getOnlineStatusUpdateTime().getTime()) / (1000 * 60 * 60);
				w2.setWarningContent("车辆闲置时间：" + ECCalculateUtils.convertsToInt(idleTime) + "小时");
			}
			w2.setIsClosed(0);
			w2.setIsNeedManualClosed(1);
			w2.setWarningLevel(1);
			w2.setWarningType("车辆");
			w2.setWarningSubType("闲置时间过长");
			w2.setWarningTime(nowTime);
			w2.setCreateTime(nowTime);
			w2.setUpdateTime(nowTime);
			w2.setOperatorType(0);
			Operator operator = new Operator();
			operator.setOperatorType(0);
			warningService.addWarning(w2, operator);
		}

	}

	@Override
	public void dealcarSpaceShortageQuartz(CarStatus carStatus,Integer totalCarSpace, Integer remainCarSpace) {
		// 查找到欠费超期3天的订单，记录到警告表
				Warning warning = new Warning();
				warning.setParkNo(carStatus.getLocationParkNo());
				List<Warning> lists = warningService.getWarningList(new Query(warning));
				Date nowTime = new Date();
				if (lists != null && lists.size() > 0) {
					warningService.delWarning(lists.get(0).getWarningNo(), null);
					Warning w1 = new Warning();
					w1.setCityId(carStatus.getCityId());
					w1.setIsClosed(0);
					w1.setIsNeedManualClosed(1);
					if(carStatus!=null){
						if(carStatus.getLocationParkNo()!=null&& !"".equals(carStatus.getLocationParkNo())){
							w1.setParkNo(carStatus.getLocationParkNo());
							Park park = parkService.getPark(carStatus.getLocationParkNo());
							if(park!=null){
								w1.setCityName(park.getCityName());
								w1.setParkName(park.getParkName());
							}
						}
					}
					
					w1.setWarningContent("场站车位数" +totalCarSpace + "个，空闲车位数" + remainCarSpace + "个");
					w1.setWarningLevel(1);
					w1.setWarningNo(lists.get(0).getWarningNo());
					w1.setWarningType("场站");
					w1.setWarningSubType("车位不足");
					w1.setWarningTime(nowTime);
					w1.setUpdateTime(nowTime);
					w1.setOperatorType(0);
					Operator operator = new Operator();
					operator.setOperatorType(0);
					warningService.addWarning(w1, operator);
				} else {
					Warning w2 = new Warning();
					w2.setCityId(carStatus.getCityId());
					w2.setIsClosed(0);
					w2.setIsNeedManualClosed(1);
					if(carStatus.getLocationParkNo()!=null&& !"".equals(carStatus.getLocationParkNo())){
						w2.setParkNo(carStatus.getLocationParkNo());
						Park park = parkService.getPark(carStatus.getLocationParkNo());
						if(park!=null){
							w2.setCityName(park.getCityName());
							w2.setParkName(park.getParkName());
						}
					}
					w2.setWarningContent("场站车位数" +totalCarSpace + "个，空闲车位数" + remainCarSpace + "个");
					w2.setWarningLevel(1);
					w2.setWarningType("场站");
					w2.setWarningSubType("车位不足");
					w2.setWarningTime(nowTime);
					w2.setUpdateTime(nowTime);
					w2.setOperatorType(0);
					Operator operator = new Operator();
					operator.setOperatorType(0);
					warningService.addWarning(w2, operator);
				}
	}

	@Override
	public void dealCarPowerQuartz(CarStatus c) {
		String powerParam = sysParamService.getByParamKey("CarPowerParam").getParamValue();
		Car car = new Car();
		car.setCarNo(c.getCarNo());
		if(powerParam != null && !StringUtils.isEmpty(powerParam)){
			Double power = Double.parseDouble(powerParam);
			if(c.getPower()!=null&&c.getPower()<power){//低于设置电量自动下线 ，不自动上线
				car.setOnlineStatus(0);
				Operator operator = new Operator();
				operator.setOperatorType(0);
				carService.updateCar(car, operator);
			}
//			else{
//				Car carTemp = new Car();
//				carTemp.setCarNo(c.getCarNo());
//				carTemp.setOnlineStatus(1);
//				List<Car> carLists = carService.getCarList(new Query(carTemp));
//				if(carLists==null||carLists.size()==0){
//					car.setOnlineStatus(1);
//					Operator operator = new Operator();
//					operator.setOperatorType(0);
//					carService.updateCar(car, operator);
//				}
//			}
		}
	}

	@Override
	public void dealpricingPackageQuartz(PricingPackage pp) {
		// 查找到套餐上架产品，判断是否过期，过期的产品则修改套餐产品表，下架
		Date date=ECDateUtils.getDateAfter(pp.getUpdateTime(),pp.getAvailableDays());
		Date dateNow=new Date();
		int result=date.compareTo(dateNow);
		if(result>0){
			PricingPackage pP=new PricingPackage();
			pP.setPackageNo(pp.getPackageNo());
			pP.setAvailabelUpdateTime(dateNow);
			pP.setAvailableReason("套餐产品过期，自动下架");
			pP.setIsAvailable(0);//下架
			pricingPackageService.updatePricingPackage(pP, null);
		}
	}

	@Override
	public void dealRunDailySendMsgQuartz() {
		// 截止前一天的统计信息获取并发送短信
		//会员总数(memberNum)，缴费会员数(memberFeeNum)，日新注册人数(registNum)，
		//日订单数(orderNum)，实际支付单数(alPayOrderNum)，在线支付金额(payAmount)，时间(time)
		Date date1=ECDateUtils.getNextDay(new Date());//获取前一天的截止时间
		Date date2=ECDateUtils.getNextDayFrist(new Date());//获取前一天的开始时间
		Long memberNum=memberDao.getMemberNum(date1);
		Long memberFeeNum=memberDao.getMemberFeeNum(date1);
		Long memberCensoredNum = memberDao.getCensoredMemberNum(date1);
		Long registNum=memberDao.getRegistNum(date2,date1);
		Long orderNum=orderDao.getOrderNum(date2,date1);
		Query q = new Query();
		Car carQ = new Car();
		carQ.setOnlineStatus(1); //当前上线的
		q.setQ(carQ);
		long curOnlineCarsNum=carService.countCar(q);
		
		RunDaily rd=orderDao.getRunDaily(date2,date1);
		if(rd.getPayAmount()==null){
			rd.setPayAmount(0d);
		}
		rd.setMemberFeeNum(memberFeeNum);
		rd.setMemberNum(memberNum);
		rd.setMemberCensoredNum(memberCensoredNum);
		rd.setOrderNum(orderNum);
		rd.setRegistNum(registNum);
		rd.setCurOnlineCarsNum(curOnlineCarsNum);
		rd.setTime(ECDateUtils.toString(new Date()));
		//系统参数中获取要发送的手机号
		String powerParam = sysParamService.getByParamKey("RUNDAILY_PHONES").getParamValue();
		String[] phones=powerParam.split(",");
		
		for(String phone:phones){
			int  sate =1;
			try {
				
				String phoneNo=phone;
				String stateNo=null;
//运行日报：会员总数：{memberNum}人；缴费会员数：{memberFeeNum}人；日新注册：{registNum}人；日订单：{orderNum}单；实际支付：{alPayOrderNum}单；在线支付{payAmount}元。{time}

//				sate =  hyInterface.sendMsgState(stateNo,phoneNo,  "【万众出行】" + "运行日报：会员总数："
//						+memberNum+ "人；缴费会员数：" + memberFeeNum+ "人；日新注册：" +registNum+ "人；日订单：" +orderNum+ "单；实际支付：" 
//						+alPayOrderNum+  "单；在线支付" +payAmount+ "元。" +time);

				//sate =  hyInterface.sendMsgState(stateNo,phoneNo,  "【天津公众智通】"+"运行日报：会员总数："+memberNum+"人；缴费会员数："+memberFeeNum+"人；日新注册："+registNum+"人；日订单："+orderNum+"单；实际支付："+rd.getAlPayOrderNum()+"单；在线支付"+rd.getPayAmount()+"元。");

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public void importAccountsPowerQuartz() {
		Calendar cal=Calendar.getInstance();
		cal.add(Calendar.DATE,-1);
		String time =new SimpleDateFormat(ECDateUtils.Format_Date).format(cal.getTime());
		importService.importFromAlipay(time);
		importService.importFromWChart(time);
	}
	
}
