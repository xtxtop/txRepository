
package cn.com.shopec.quartz.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.com.shopec.common.utils.ECCalculateUtils;
import cn.com.shopec.common.utils.ECDateUtils;
import cn.com.shopec.common.utils.JsonUtils;
import cn.com.shopec.core.car.model.CarStatus;
import cn.com.shopec.core.car.service.CarService;
import cn.com.shopec.core.car.service.CarStatusService;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.marketing.model.PeakHours;
import cn.com.shopec.core.marketing.model.PeakHoursCost;
import cn.com.shopec.core.marketing.model.PricingRule;
import cn.com.shopec.core.marketing.model.PricingRuleCustomizedDate;
import cn.com.shopec.core.marketing.service.PeakHoursService;
import cn.com.shopec.core.marketing.service.PricingRuleCustomizedDateService;
import cn.com.shopec.core.marketing.service.PricingRuleService;
import cn.com.shopec.core.order.model.Order;
import cn.com.shopec.core.order.model.OrderMileage;
import cn.com.shopec.core.order.service.OrderMileageService;
import cn.com.shopec.core.order.service.OrderService;
import cn.com.shopec.core.system.model.SysParam;
import cn.com.shopec.core.system.service.SysParamService;

/**
 * 扫描计费中的订单，更新订单里程
 * 
 * @author machao
 * @date 2017年5月11日
 */
public class OrderMileageQuartz {

	private Log logger = LogFactory.getLog(OrderMileageQuartz.class);

	private static final String ZERO = "0";
	/**
	 * 每次到数据库取数据的最大数
	 */
	private static final int maxNum = 500;


	@Resource
	private OrderService orderService;
	@Resource
	private CarService carService;
	@Resource
	private CarStatusService carStatusService;
	@Resource
	private PricingRuleService pricingRuleService;
	@Resource
	private OrderMileageService orderMileageService;
	@Resource
	private SysParamService sysParamService;
	@Resource
	private PricingRuleCustomizedDateService pricingRuleCustomizedDateService;
	@Resource
	private PeakHoursService peakHoursService;
	public void quartzStart() throws Exception {
		// 查找已结束的未支付的订单
		Order order = new Order();
		order.setOrderStatus(2);
		List<Order> orderList = orderService.getOrderList(new Query(order));
		if(orderList.size()>0){
			for(Order o:orderList){
				if(o.getRuleNo()!=null){
					PricingRule rule = pricingRuleService.getPricingRule(o.getRuleNo());
					CarStatus carStatus = carStatusService.getCarStatus(o.getCarNo());
					int orderCaculateType = 0;
					try{
						int ruleType = rule.getRuleType();
						if(ruleType ==1 || ruleType ==2){//目前只有两种规则
							orderCaculateType = ruleType;
						}
					}catch(Exception e){
						e.printStackTrace();
					}
					if(orderCaculateType==0){
						SysParam sysparam1 = sysParamService.getByParamKey("ORDER_CACULATE_TYPE");
						if(sysparam1!=null&&sysparam1.getParamValue()!=null){
							orderCaculateType = Integer.parseInt(sysparam1.getParamValue().trim());
						}
					}
					//自定义时间分割点
					String timeDivisionPointStr = "";
					SysParam sysparam2 = sysParamService.getByParamKey("TIME_DIVISION_POINT");
					if(sysparam2!=null){
						timeDivisionPointStr = sysparam2.getParamValue().trim();
					}
					if(timeDivisionPointStr.equals("")){
						timeDivisionPointStr = "0";
						logger.info("--------扫描计费中的订单定时任务，没有配置自定义时间分割点系统参数");
					}
					
					Date currentDateTime = new Date();//当前时间
					Date nowDate = ECDateUtils.formatDateToDateNew(currentDateTime);//得到日期格式（YYYY-MM-DD）
					int timeDivisionPoint = Integer.parseInt(timeDivisionPointStr);//时间分隔点
					if(timeDivisionPoint<=0 ||timeDivisionPoint>23){
						timeDivisionPoint=0;
					}
					if(orderCaculateType==1){
						//得到分隔点前一个时间，算出前一天及前一天结束时间
						Calendar beforTime = Calendar.getInstance();
						beforTime.setTime(nowDate);
						beforTime.set(Calendar.HOUR_OF_DAY, timeDivisionPoint);
						beforTime.add(Calendar.SECOND, -1);//算出前一个时间(结束时间)
						Date beforDayOrderEndMinute = beforTime.getTime();//算出前一天结束时间
						Date beforDate = ECDateUtils.getDateBefore(nowDate, 1);//算出前一天
						
						//得到分隔点下一个时间，算出后一天及后一天后开始时间
						Calendar nextTime = Calendar.getInstance();
						nextTime.setTime(nowDate);
						nextTime.set(Calendar.HOUR_OF_DAY, timeDivisionPoint);//算出下一个时间(开始时间)
						Date nextOrderStartMinute = nextTime.getTime();//算出后一天开始时间
						Date nextDate = ECDateUtils.getDateBefore(nowDate, 0);//算出后一天
						
						//算出当前是时间分隔点的中的哪一天(前一天或后一天),用于比较数据库数据是哪一个时间点的。
						Date compareDate = nextDate;
						if(currentDateTime.before(nextOrderStartMinute)){
							compareDate = beforDate;
						}

						OrderMileage orderMileage = orderMileageService.getNewestOrderMileage(o.getOrderNo());
						if(orderMileage!=null){
							int diffDays = ECDateUtils.differDays2(compareDate, orderMileage.getOrderMileageDate()).intValue();
							//上一天的更新时间与当前时间不属于同一天则更新上一天数据
							if(diffDays>=1){
								//更新上一天的数据
								updateNowDayOrderMileage(carStatus, o, orderMileage, rule, beforDayOrderEndMinute,orderCaculateType);
								OrderMileage om = orderMileageService.getOrderMileage(nowDate, o.getOrderNo());
								if(om==null){
									//上一天数据已更新，添加当天的数据
									OrderMileage om1 = new OrderMileage();
									om1.setOrderNo(o.getOrderNo());
									om1.setOrderMileageDate(nowDate);
									//当天开始里程
									om1.setOrderStartMileage(carStatus.getMileage());
									//当天开始时间
									om1.setOrderStartMinute(nextOrderStartMinute);
									orderMileageService.addOrderMileage(om1, null);
								}
							}else{
								//更新当天数据
								updateNowDayOrderMileage(carStatus, o, orderMileage, rule, currentDateTime,orderCaculateType);
							}
						}
						
					}else if(orderCaculateType==2){
						OrderMileage orderMileage = orderMileageService.getNewestOrderMileage(o.getOrderNo());
						if(orderMileage!=null){
							//当前时间
							Date nowTime = ECDateUtils.getCurrentDateTime();
							//上一天开始时间
							Date lastDayOrderStartMinute = orderMileage.getOrderStartMinute();
							//当前时间与上一天起始时间的时间差
							int diffDays = ECDateUtils.differDays2(nowTime, lastDayOrderStartMinute).intValue();
							//上一天的更新时间与当前时间不属于同一天则更新上一天数据
							if(diffDays>=1){
								//更新上一天的数据,
								//上一天结束时间
								Date lastDayOrderEndMinute = ECDateUtils.getDateAfter(lastDayOrderStartMinute, 1);
								//一天的总金额为null,说明上一天数据还未更新
								Date orderEndMinue = orderMileage.getOrderEndMinute();
								if(null ==orderEndMinue||null ==orderMileage.getOrderAmountOfDay()|| orderEndMinue.before(lastDayOrderEndMinute)){
									updateNowDayOrderMileage(carStatus, o, orderMileage, rule, lastDayOrderEndMinute,orderCaculateType);
								}								
								OrderMileage om = orderMileageService.getOrderMileage(nowDate, o.getOrderNo());
								if(om==null){
									//上一天数据已更新，添加当天的数据
									OrderMileage om1 = new OrderMileage();
									om1.setOrderNo(o.getOrderNo());
									om1.setOrderMileageDate(nowDate);
									//当天开始里程
									om1.setOrderStartMileage(carStatus.getMileage());
									//当天开始时间（为上一天的结束时间）
									om1.setOrderStartMinute(lastDayOrderEndMinute);
									//存储高峰时段费用信息
									List<PeakHoursCost> peakHoursCostList = new ArrayList<PeakHoursCost>();
									Date beforDate =  ECDateUtils.formatDateToDateNew(ECDateUtils.getDateBefore(nowDate, 1));//算出前一天
									OrderMileage beforeOrderMileage = orderMileageService.getOrderMileage(beforDate, o.getOrderNo());
									if (beforeOrderMileage!=null) {
										if (beforeOrderMileage.getPearTimeNextDayCost()!=null) {
											PeakHoursCost peakHoursCost = JsonUtils.parse2Object(beforeOrderMileage.getPearTimeNextDayCost(), PeakHoursCost.class);
											peakHoursCostList.add(peakHoursCost);
										}
									}
									PeakHours peakHoursQuery = new PeakHours();
									peakHoursQuery.setRuleNo(rule.getRuleNo());
									List<PeakHours> peakHoursList = peakHoursService.getPeakHoursList(new Query(peakHoursQuery));
									for (PeakHours peakHours:peakHoursList) {
										Calendar pearHourStart = Calendar.getInstance();
										pearHourStart.setTime(nowTime);
										pearHourStart.set(Calendar.HOUR_OF_DAY, Integer.parseInt(peakHours.getPeakStartHours()));
										pearHourStart.set(Calendar.MINUTE, 0);
										pearHourStart.set(Calendar.SECOND, 0);
										Date pearHourStartTime = pearHourStart.getTime();
										
										Calendar pearHourEnd = Calendar.getInstance();
										pearHourEnd.setTime(nowTime);
										pearHourEnd.set(Calendar.HOUR_OF_DAY, Integer.parseInt(peakHours.getPeakEndHours()));
										pearHourEnd.set(Calendar.MINUTE, 0);
										pearHourEnd.set(Calendar.SECOND, 0);
										Date pearHourEndTime = pearHourEnd.getTime();
										//OrderMileage记录一天的开始时间
										Date startTimeOfDay = lastDayOrderEndMinute;
										//高峰时段开始时间小于当前时间且当天记录开始时间小于高峰时间开始时间
										if (pearHourStartTime.before(nowTime)&&startTimeOfDay.before(pearHourStartTime)) {
											PeakHoursCost peakHoursCost = new PeakHoursCost();
											peakHoursCost.setTimePeriod(peakHours.getPeakHours());
											peakHoursCost.setTimePeriodStartTime(pearHourStartTime);
											peakHoursCost.setTimePeriodStartMileage(carStatus.getMileage());
											peakHoursCostList.add(peakHoursCost);
										//高峰时段开始时间小于当前时间且高峰时间开始时间小于订单开始时间
										}else if (pearHourStartTime.before(nowTime)&&pearHourStartTime.before(startTimeOfDay)&&startTimeOfDay.before(pearHourEndTime)) {
											PeakHoursCost peakHoursCost = new PeakHoursCost();
											peakHoursCost.setTimePeriod(peakHours.getPeakHours());
											peakHoursCost.setTimePeriodStartTime(pearHourStartTime);
											peakHoursCost.setTimePeriodStartMileage(carStatus.getMileage());
											peakHoursCostList.add(peakHoursCost);
										}
									}
									om1.setPearTimeCost(JsonUtils.toJsonString(peakHoursCostList));
									orderMileageService.addOrderMileage(om1, null);
								}
							}else{
								//上一天结束时间
								Date lastDayOrderEndMinute = ECDateUtils.getDateAfter(lastDayOrderStartMinute, 1);
								if (nowTime.compareTo(lastDayOrderEndMinute)>=0) {
									updateNowDayOrderMileage(carStatus, o, orderMileage, rule, lastDayOrderEndMinute,orderCaculateType);
								}else{
									updateNowDayOrderMileage(carStatus, o, orderMileage, rule, nowTime,orderCaculateType);
								}
							}
						}
					}
				}
			}
		}else{
			logger.info("---没有计费中的订单...");
		}
	}
	//更新指定日期的数据
	private void updateNowDayOrderMileage(CarStatus carStatus,Order o,OrderMileage orderMileage,PricingRule rule,Date orderEndMinute,int orderCaculateType) throws Exception{
		OrderMileage om = new OrderMileage();
		om.setOrderNo(o.getOrderNo());
		om.setOrderMileageDate(orderMileage.getOrderMileageDate());
		
		om.setOrderEndMinute(orderEndMinute);
		om.setMinutes(ECDateUtils.differMinutes(om.getOrderEndMinute(), orderMileage.getOrderStartMinute()).intValue());
		
		om.setOrderFinishMileage(carStatus.getMileage());
		om.setMileage(ECCalculateUtils.sub(om.getOrderFinishMileage(),orderMileage.getOrderStartMileage()));
		
		SysParam param = sysParamService.getByParamKey("IS_ORDER_FREEMILEAGE");
		String isOrderFreeMileage = "0";
		if(param!=null&&param.getParamValue()!=null){
			isOrderFreeMileage = param.getParamValue().trim();
		}
		if ("1".equals(isOrderFreeMileage)) {
			param = sysParamService.getByParamKey("FREEMINUTES");
			if(param!=null&&param.getParamValue()!=null){
				int freeMinutes = Integer.parseInt(param.getParamValue().trim());
				if (om.getMinutes()<=freeMinutes) {
					om.setFreeMileage(om.getMileage());
				}
			}
		}
		if(orderCaculateType==1){
			//确定orderMileage.getOrderMileageDate()是周几
			int dayOfWeek = ECDateUtils.getDayOfWeek(orderMileage.getOrderMileageDate());
			//查询自定义日期
			PricingRuleCustomizedDate pricingRuleCustomizedDate = pricingRuleCustomizedDateService.getPricingRuleCustomizedDate(o.getRuleNo(), orderMileage.getOrderMileageDate());
			if(pricingRuleCustomizedDate!=null){//自定义日期时间金额和里程金额计算
				om.setMinutesAmount(ECCalculateUtils.mul(om.getMinutes(),pricingRuleCustomizedDate.getPriceOfMinuteCustomized()));
				om.setMileageAmount(ECCalculateUtils.mul(om.getMileage(),pricingRuleCustomizedDate.getPriceOfKmCustomized()));
			}else if(dayOfWeek==Calendar.SATURDAY||dayOfWeek==Calendar.SUNDAY){//周末金额和里程金额计算
				//若周末计费为空，则使用平日计费
				if(rule.getPriceOfMinuteOrdinary()!=null&&rule.getPriceOfKmOrdinary()!=null){
					om.setMinutesAmount(ECCalculateUtils.mul(om.getMinutes(),rule.getPriceOfMinuteOrdinary()));
					om.setMileageAmount(ECCalculateUtils.mul(om.getMileage(),rule.getPriceOfKmOrdinary()));
				}else{
					om.setMinutesAmount(ECCalculateUtils.mul(om.getMinutes(),rule.getPriceOfMinute()));
					om.setMileageAmount(ECCalculateUtils.mul(om.getMileage(),rule.getPriceOfKm()));	
				}
			}else{//工作日金额和里程金额计算
				om.setMinutesAmount(ECCalculateUtils.mul(om.getMinutes(),rule.getPriceOfMinute()));
				om.setMileageAmount(ECCalculateUtils.mul(om.getMileage(),rule.getPriceOfKm()));
			}
		}else{
			om.setMinutesAmount(ECCalculateUtils.mul(om.getMinutes(),rule.getPriceOfMinute()));
			om.setMileageAmount(ECCalculateUtils.mul(om.getMileage(),rule.getPriceOfKm()));
		}
		om.setMinutesAmount(ECCalculateUtils.round(om.getMinutesAmount(), 2));
		om.setMileageAmount(ECCalculateUtils.round(om.getMileageAmount(), 2));
		om.setOrderAmountOfDay(ECCalculateUtils.add(om.getMileageAmount(), om.getMinutesAmount()));
		om.setOrderAmountOfDay(ECCalculateUtils.round(om.getOrderAmountOfDay(),2));
		if (orderMileage.getPearTimeCost()!=null) {
			//当前要更新的记录中的高峰时段记录
			List<PeakHoursCost> peakHoursCostList = JsonUtils.parse2ListObject(orderMileage.getPearTimeCost(), PeakHoursCost.class);;
			List<PeakHoursCost> peakHoursAddCostList = new ArrayList<PeakHoursCost>();
			PeakHours peakHoursQuery = new PeakHours();
			peakHoursQuery.setRuleNo(rule.getRuleNo());
			List<PeakHours> peakHoursList = peakHoursService.getPeakHoursList(new Query(peakHoursQuery));
			//当前记录的结束时间
			Date orderEndMinuteOfDay = ECDateUtils.getDateAfter(orderMileage.getOrderStartMinute(), 1);
			//当前时间
			Date currentDateTime = ECDateUtils.getCurrentDateTime();
			for (PeakHours peakHours:peakHoursList) {
				Calendar pearHourStart = Calendar.getInstance();
				pearHourStart.setTime(currentDateTime);
				pearHourStart.set(Calendar.HOUR_OF_DAY, Integer.parseInt(peakHours.getPeakStartHours()));
				pearHourStart.set(Calendar.MINUTE, 0);
				pearHourStart.set(Calendar.SECOND, 0);
				Date pearHourStartTime = pearHourStart.getTime();
				
				Calendar pearHourEnd = Calendar.getInstance();
				pearHourEnd.setTime(currentDateTime);
				pearHourEnd.set(Calendar.HOUR_OF_DAY, Integer.parseInt(peakHours.getPeakEndHours()));
				pearHourEnd.set(Calendar.MINUTE, 0);
				pearHourEnd.set(Calendar.SECOND, 0);
				Date pearHourEndTime = pearHourEnd.getTime();
				if (peakHoursCostList!=null&&peakHoursCostList.size()>0) {
					for (PeakHoursCost peakHoursCost:peakHoursCostList) {
						//同一高峰时段
						if (peakHours.getPeakHours().equals(peakHoursCost.getTimePeriod())) {
							//高峰时段结束时间小于或者等于orderEndMinute（当前记录的结束时间）
							if (pearHourEndTime.before(orderEndMinute)||pearHourEndTime.equals(orderEndMinute)) {
								peakHoursCost.setTimePeriodEndTime(pearHourEndTime);
								peakHoursCost.setTimePeriodMinutes(ECDateUtils.differMinutes(peakHoursCost.getTimePeriodEndTime(),peakHoursCost.getTimePeriodStartTime()).intValue());
								peakHoursCost.setTimePeriodMinutesAmount(ECCalculateUtils.mul(peakHours.getPriceOfMinute(), peakHoursCost.getTimePeriodMinutes()));
								peakHoursCost.setTimePeriodEndMileage(carStatus.getMileage());
								peakHoursCost.setTimePeriodMileage(ECCalculateUtils.sub(peakHoursCost.getTimePeriodEndMileage(),peakHoursCost.getTimePeriodStartMileage()));
								peakHoursCost.setTimePeriodMileageAmount(ECCalculateUtils.mul(peakHours.getPriceOfKm(), peakHoursCost.getTimePeriodMileage()));
								//当前的结束时间小于高峰时段结束时间且当前的结束时间=orderEndMinute（当前记录的结束时间）
							}else if (orderEndMinuteOfDay.before(pearHourEndTime)&&orderEndMinuteOfDay.equals(orderEndMinute)) {
								peakHoursCost.setTimePeriodEndTime(orderEndMinuteOfDay);
								peakHoursCost.setTimePeriodMinutes(ECDateUtils.differMinutes(peakHoursCost.getTimePeriodEndTime(),peakHoursCost.getTimePeriodStartTime()).intValue());
								peakHoursCost.setTimePeriodMinutesAmount(ECCalculateUtils.mul(peakHours.getPriceOfMinute(), peakHoursCost.getTimePeriodMinutes()));
								peakHoursCost.setTimePeriodEndMileage(carStatus.getMileage());
								peakHoursCost.setTimePeriodMileage(ECCalculateUtils.sub(peakHoursCost.getTimePeriodEndMileage(),peakHoursCost.getTimePeriodStartMileage()));
								peakHoursCost.setTimePeriodMileageAmount(ECCalculateUtils.mul(peakHours.getPriceOfKm(), peakHoursCost.getTimePeriodMileage()));
								
								PeakHoursCost timePeriodNextDayCost = new PeakHoursCost();
								timePeriodNextDayCost.setTimePeriod(peakHours.getPeakHours());
								timePeriodNextDayCost.setTimePeriodStartTime(orderEndMinuteOfDay);
								timePeriodNextDayCost.setTimePeriodStartMileage(carStatus.getMileage());
								peakHoursAddCostList.add(timePeriodNextDayCost);
								om.setPearTimeNextDayCost(JsonUtils.toJsonString(timePeriodNextDayCost));
							//
							}else if (pearHourStartTime.before(orderEndMinute)&&orderEndMinute.before(pearHourEndTime)) {
								peakHoursCost.setTimePeriodEndTime(orderEndMinute);
								peakHoursCost.setTimePeriodMinutes(ECDateUtils.differMinutes(peakHoursCost.getTimePeriodEndTime(),peakHoursCost.getTimePeriodStartTime()).intValue());
								peakHoursCost.setTimePeriodMinutesAmount(ECCalculateUtils.mul(peakHours.getPriceOfMinute(), peakHoursCost.getTimePeriodMinutes()));
								peakHoursCost.setTimePeriodEndMileage(carStatus.getMileage());
								peakHoursCost.setTimePeriodMileage(ECCalculateUtils.sub(peakHoursCost.getTimePeriodEndMileage(),peakHoursCost.getTimePeriodStartMileage()));
								peakHoursCost.setTimePeriodMileageAmount(ECCalculateUtils.mul(peakHours.getPriceOfKm(), peakHoursCost.getTimePeriodMileage()));
							}
						}else {
							//高峰时段开始时间小于当前时间且当天订单开始时间小于高峰时间开始时间
							if (pearHourStartTime.before(currentDateTime)&&orderMileage.getOrderStartMinute().before(pearHourStartTime)&&currentDateTime.before(pearHourEndTime)) {
								if (!orderMileage.getPearTimeCost().contains("\"timePeriod\":\""+peakHours.getPeakHours()+"\"")) {
									PeakHoursCost peakHoursCostForAdd = new PeakHoursCost();
									peakHoursCostForAdd.setTimePeriod(peakHours.getPeakHours());
									peakHoursCostForAdd.setTimePeriodStartTime(pearHourStartTime);
									peakHoursCostForAdd.setTimePeriodEndTime(pearHourStartTime);
									peakHoursCostForAdd.setTimePeriodMinutes(0);
									peakHoursCostForAdd.setTimePeriodMinutesAmount(0d);
									peakHoursCostForAdd.setTimePeriodStartMileage(carStatus.getMileage());
									peakHoursCostForAdd.setTimePeriodEndMileage(carStatus.getMileage());
									peakHoursCostForAdd.setTimePeriodMileage(0d);
									peakHoursCostForAdd.setTimePeriodMileageAmount(0d);
									peakHoursAddCostList.add(peakHoursCostForAdd);
									
									//将要加入的高峰时段拼入orderMileage.getPearTimeCost()
									List<PeakHoursCost> peakHoursCostTempList = JsonUtils.parse2ListObject(orderMileage.getPearTimeCost(), PeakHoursCost.class);;
									peakHoursCostTempList.add(peakHoursCostForAdd);
									orderMileage.setPearTimeCost(JsonUtils.toJsonString(peakHoursCostTempList));
								}
							}
						}
					}
				}else {
					//高峰时段开始时间小于当前时间且当天订单开始时间小于高峰时间开始时间
					if (pearHourStartTime.before(currentDateTime)&&orderMileage.getOrderStartMinute().before(pearHourStartTime)) {
						PeakHoursCost peakHoursCostForAdd = new PeakHoursCost();
						peakHoursCostForAdd.setTimePeriod(peakHours.getPeakHours());
						peakHoursCostForAdd.setTimePeriodStartTime(pearHourStartTime);
						peakHoursCostForAdd.setTimePeriodEndTime(pearHourStartTime);
						peakHoursCostForAdd.setTimePeriodMinutes(0);
						peakHoursCostForAdd.setTimePeriodMinutesAmount(0d);
						peakHoursCostForAdd.setTimePeriodStartMileage(carStatus.getMileage());
						peakHoursCostForAdd.setTimePeriodEndMileage(carStatus.getMileage());
						peakHoursCostForAdd.setTimePeriodMileage(0d);
						peakHoursCostForAdd.setTimePeriodMileageAmount(0d);
						peakHoursAddCostList.add(peakHoursCostForAdd);
					}
				}
			}
			for(PeakHoursCost peakHoursCost:peakHoursAddCostList){
				peakHoursCostList.add(peakHoursCost);
			}
			om.setPearTimeCost(JsonUtils.toJsonString(peakHoursCostList));
		}
		orderMileageService.updateOrderMileage(om, null);
	}
	protected void execute() throws Exception {
		try {
			logger.info("--------扫描计费中的订单定时任务开始，开始时间：" + new Date());
			// 执行业务方法
			quartzStart();

			logger.info("--------扫描计费中的订单定时任务完成...");
		} catch (Exception e) {
			logger.error("--------扫描计费中的订单任务出错，错误信息：" + e.getMessage(), e);
		}
	}
	
	
	public static void main(String[] args) throws ParseException{
		Date nowDate = ECDateUtils.formatDateToDateNew(new Date());//得到今天
		int timeDivisionPoint = Integer.parseInt("5");
		if(timeDivisionPoint<=0 ||timeDivisionPoint>23){
			timeDivisionPoint=0;
		}
		Calendar beforTime = Calendar.getInstance();
		beforTime.setTime(nowDate);
		beforTime.set(Calendar.HOUR_OF_DAY, timeDivisionPoint);
		beforTime.add(Calendar.SECOND, -1);
		
		Calendar lastTime = Calendar.getInstance();
		lastTime.setTime(nowDate);
		lastTime.set(Calendar.HOUR_OF_DAY, timeDivisionPoint);
		
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.println(df.format(beforTime.getTime()));
		System.out.println(df.format(lastTime.getTime()));
	}

}
