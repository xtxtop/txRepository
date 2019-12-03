
package cn.com.shopec.core.dailyrental.quarts;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.StringUtils;

import cn.com.shopec.common.utils.ECCalculateUtils;
import cn.com.shopec.common.utils.ECDateUtils;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.dailyrental.model.Merchant;
import cn.com.shopec.core.dailyrental.model.MerchantOrderConfirm;
import cn.com.shopec.core.dailyrental.model.OrderDay;
import cn.com.shopec.core.dailyrental.service.MerchantOrderConfirmService;
import cn.com.shopec.core.dailyrental.service.MerchantOrderService;
import cn.com.shopec.core.dailyrental.service.MerchantService;
import cn.com.shopec.core.dailyrental.service.OrderDayService;
import cn.com.shopec.core.finace.model.PaymentRecord;
import cn.com.shopec.core.finace.service.PaymentRecordService;
import cn.com.shopec.core.system.model.SysParam;
import cn.com.shopec.core.system.service.SysParamService;

/**
 * 商家订单对账定时任务
 * 
 */
public class MerchantOrderDayAccountsQuartz {

	private Log logger = LogFactory.getLog(MerchantOrderDayAccountsQuartz.class);

	private static final String ZERO = "0";
	/**
	 * 每次到数据库取数据的最大数
	 */
	private static final int maxNum = 500;
	@Resource
	private SysParamService sysParamService;
	@Resource
	private MerchantService merchantService;
	@Resource
	private MerchantOrderService merchantOrderService;
	@Resource
	private OrderDayService orderDayService;
	@Resource
	private PaymentRecordService paymentRecordService;
	@Resource
	private MerchantOrderConfirmService merchantOrderConfirmService;
	
	public void quartzStart() throws Exception {
		Merchant merchantQuery = new Merchant();
		Date nowTime = ECDateUtils.getCurrentDateTime();
		List<Merchant> merchantList = merchantService.getMerchantList(new Query(merchantQuery));
		for(Merchant merchant:merchantList){
			String orderNos = merchantOrderService.getMerhcantOrderNo(merchant.getMerchantId());
			if(orderNos==null||"".equals(orderNos)){
				continue;
			}
			//对账周期(30、一个月（自然月,小于30，按订单创建时间据现在到达reconciliationCycle天为一个周期）
			int reconciliationCycle = 30;
			SysParam param = sysParamService.getByParamKey("MerchantOrderAccountDay");
			if(param.getParamValue()!=null){
				reconciliationCycle = Integer.parseInt(param.getParamValue());
			}
			if(reconciliationCycle<0||reconciliationCycle>30){
				reconciliationCycle = 30;
			}
			List<MerchantOrderConfirm> confirmList = merchantOrderConfirmService.getListByMerchantId(merchant.getMerchantId());
			//已存在商家对账记录
			if(confirmList!=null&&confirmList.size()>0){
				MerchantOrderConfirm conformOrder = confirmList.get(0);
				String date = conformOrder.getDate().substring(conformOrder.getDate().indexOf("至")+1);
				Date paidTimeStart = ECDateUtils.getDateAfter(ECDateUtils.parseDate(date, "yyyy-MM-dd"),1);
				Date paidTimeEnd = ECDateUtils.getCurrentDateEndTime(paidTimeStart);//默认一天
				if(reconciliationCycle<30){
					paidTimeEnd = ECDateUtils.getDateAfter(paidTimeStart, reconciliationCycle-1);
					
					paidTimeEnd = ECDateUtils.getCurrentDateEndTime(paidTimeEnd);
					if(nowTime.compareTo(paidTimeEnd)>0){
						addMerchantOrderConfirmByDay(merchant, paidTimeStart, paidTimeEnd, orderNos);
					}
				}else{
					paidTimeStart = ECDateUtils.getFirstMonthDayOfDate(paidTimeStart);
					paidTimeEnd = ECDateUtils.getLastMonthDayOfDate(paidTimeStart);
					if(nowTime.compareTo(paidTimeEnd)>0){
						addMerchantOrderConfirmByMonth(merchant, paidTimeStart, paidTimeEnd, orderNos);
					} 
				}
			}else{
				//商家对账记录不存在
				//获取商家时间最早的订单，把此订单时间作为对账的起始时间
				String[] orderNoArr = orderNos.split(",");
				if(orderNoArr.length>0){
					//获取商家订单时间最早的订单
					List<OrderDay> orderDayList = orderDayService.getOrderDayByIds(orderNoArr);
					Date paidTimeStart = ECDateUtils.getCurrentDateStartTime(orderDayList.get(0).getCreateTime());
					Date paidTimeEnd = ECDateUtils.getCurrentDateEndTime(paidTimeStart);//默认一天
					if(reconciliationCycle<30){
						paidTimeEnd = ECDateUtils.getDateAfter(paidTimeStart, reconciliationCycle-1);
						
						paidTimeEnd = ECDateUtils.getCurrentDateEndTime(paidTimeEnd);
						if(nowTime.compareTo(paidTimeEnd)>0){
							addMerchantOrderConfirmByDay(merchant, paidTimeStart, paidTimeEnd, orderNos);
						}
					}else{
						paidTimeStart = ECDateUtils.getFirstMonthDayOfDate(paidTimeStart);
						paidTimeEnd = ECDateUtils.getLastMonthDayOfDate(paidTimeStart);
						if(nowTime.compareTo(paidTimeEnd)>0){
							addMerchantOrderConfirmByMonth(merchant, paidTimeStart, paidTimeEnd, orderNos);
						} 
					}
				}
			}
		}
	}
	/**
	 * 按照对账周期添加对账单-天
	 * @param merchant
	 * @param paidTimeStart
	 * @param paidTimeEnd
	 * @param orderNos
	 */
	private void addMerchantOrderConfirmByDay(Merchant merchant,Date paidTimeStart,Date paidTimeEnd,String orderNos){
		MerchantOrderConfirm  orderConfirm = new MerchantOrderConfirm();
		PaymentRecord paymentRecord = new PaymentRecord();
		paymentRecord.setBizObjNo(orderNos);
		paymentRecord.setPaidTimeStart(paidTimeStart);
		paymentRecord.setPaidTimeEnd(paidTimeEnd);
		Map<String, Object> map = paymentRecordService.getMerchantOrderPaidAmount(paymentRecord);
		if (map.size()>0) {
			double profitRatio = 1;
			if(merchant.getProfitRatio()!=null){
				profitRatio = merchant.getProfitRatio();
			}else{
				SysParam param = sysParamService.getByParamKey("PROFIT_RATIO");
				if(param.getParamValue()!=null){
					profitRatio = Double.valueOf(param.getParamValue());
				}
			}
			//业务单号数组
			String bizObjNo = (String)map.get("BIZ_OBJ_NO");
			if (bizObjNo!=null) {
				String[] orderIds = bizObjNo.split(",");
				List<OrderDay> orderDayList = orderDayService.getOrderDayByIds(orderIds);
				Double leftTotalAmount = 0.0;
				for(OrderDay orderDay:orderDayList){
					//退还费用
					if (orderDay.getReturnAmount()!=null) {
						leftTotalAmount = orderDay.getReturnAmount();
						//超时还车费
						if (orderDay.getOvertimeCharge()!=null) {
							leftTotalAmount = ECCalculateUtils.sub(leftTotalAmount, orderDay.getOvertimeCharge());
						}
						//提前还车违约金
						if (orderDay.getServiceFeeAmount()!=null) {
							leftTotalAmount = ECCalculateUtils.sub(leftTotalAmount, orderDay.getServiceFeeAmount());
						}
					//未到店取车违约金
					}else if(orderDay.getCancelAmount()!=null) {
						leftTotalAmount = orderDay.getCancelAmount();
					//送车上门未取车违约金
					}else if (orderDay.getTakeCarDoorAmount()!=null) {
						leftTotalAmount = orderDay.getTakeCarDoorAmount();
					}
				}
				Long num = (Long)map.get("NUM");
				double orderAmount = (Double)map.get("PAYABLE_AMOUNT");
				double paidAmount = (Double)map.get("PAID_AMOUNT");
				if (paidAmount>leftTotalAmount) {
					paidAmount = ECCalculateUtils.sub(paidAmount, leftTotalAmount); 
				}
				double profitAmount = ECCalculateUtils.mul(paidAmount, profitRatio);
				String date = ECDateUtils.formatDate(paidTimeStart,ECDateUtils.Format_Date)+"至"+ECDateUtils.formatDate(paidTimeEnd,ECDateUtils.Format_Date);
				orderConfirm.setDate(date);
				orderConfirm.setConfirmStatus(0);
				orderConfirm.setMerchantId(merchant.getMerchantId());
				orderConfirm.setYear(ECDateUtils.getYearOfDate(paidTimeStart));
				orderConfirm.setMonth(ECDateUtils.getMonthOfDate(paidTimeStart));
				orderConfirm.setNum(num.intValue());
				orderConfirm.setOrderAmount(ECCalculateUtils.round(orderAmount, 2));
				orderConfirm.setProfitRatio(ECCalculateUtils.round(profitRatio,2));
				orderConfirm.setProfitAmount(ECCalculateUtils.round(profitAmount,2));
				orderConfirm.setIsSettled(0);
				merchantOrderConfirmService.addMerchantOrderConfirm(orderConfirm, null);
			}
		}
	}
	/**
	 * 按照对账周期添加对账单-月
	 * @param merchant
	 * @param paidTimeStart
	 * @param paidTimeEnd
	 * @param orderNos
	 */
	private void addMerchantOrderConfirmByMonth(Merchant merchant,Date paidTimeStart,Date paidTimeEnd,String orderNos){
		PaymentRecord paymentRecord = new PaymentRecord();
		paymentRecord.setBizObjNo(orderNos);
		List<Map<String, Object>> mapList = paymentRecordService.getMerchantOrderPaidAmountByMonth(paymentRecord);
		if (mapList.size()>0) {
			double profitRatio = 1;
			if(merchant.getProfitRatio()!=null){
				profitRatio = merchant.getProfitRatio();
			}else{
				SysParam param = sysParamService.getByParamKey("PROFIT_RATIO");
				if(param.getParamValue()!=null){
					profitRatio = Double.valueOf(param.getParamValue());
				}
			}
			for(Map<String, Object> map:mapList){
				//业务单号数组
				String bizObjNo = (String)map.get("BIZ_OBJ_NO");
				if (bizObjNo!=null) {
					String[] orderIds = bizObjNo.split(",");
					List<OrderDay> orderDayList = orderDayService.getOrderDayByIds(orderIds);
					Double leftTotalAmount = 0.0;
					for(OrderDay orderDay:orderDayList){
						//退还费用
						if (orderDay.getReturnAmount()!=null) {
							leftTotalAmount = orderDay.getReturnAmount();
							//超时还车费
							if (orderDay.getOvertimeCharge()!=null) {
								leftTotalAmount = ECCalculateUtils.sub(leftTotalAmount, orderDay.getOvertimeCharge());
							}
							//提前还车违约金
							if (orderDay.getServiceFeeAmount()!=null) {
								leftTotalAmount = ECCalculateUtils.sub(leftTotalAmount, orderDay.getServiceFeeAmount());
							}
						//未到店取车违约金
						}else if(orderDay.getCancelAmount()!=null) {
							leftTotalAmount = orderDay.getCancelAmount();
						//送车上门未取车违约金
						}else if (orderDay.getTakeCarDoorAmount()!=null) {
							leftTotalAmount = orderDay.getTakeCarDoorAmount();
						}
					}
					MerchantOrderConfirm  orderConfirm = new MerchantOrderConfirm();
					Long num = (Long)map.get("NUM");
					double orderAmount = (Double)map.get("PAYABLE_AMOUNT");
					double paidAmount = (Double)map.get("PAID_AMOUNT");
					if (paidAmount>leftTotalAmount) {
						paidAmount = ECCalculateUtils.sub(paidAmount, leftTotalAmount); 
					}
					double profitAmount = ECCalculateUtils.mul(paidAmount, profitRatio);
					String date = ECDateUtils.formatDate(paidTimeStart,ECDateUtils.Format_Date)+"至"+ECDateUtils.formatDate(paidTimeEnd,ECDateUtils.Format_Date);
					orderConfirm.setDate(date);
					orderConfirm.setConfirmStatus(0);
					orderConfirm.setMerchantId(merchant.getMerchantId());
					orderConfirm.setYear(ECDateUtils.getYearOfDate(paidTimeStart));
					orderConfirm.setMonth(ECDateUtils.getMonthOfDate(paidTimeStart));
					orderConfirm.setNum(num.intValue());
					orderConfirm.setOrderAmount(ECCalculateUtils.round(orderAmount, 2));
					orderConfirm.setProfitRatio(ECCalculateUtils.round(profitRatio,2));
					orderConfirm.setProfitAmount(ECCalculateUtils.round(profitAmount,2));
					orderConfirm.setIsSettled(0);
					merchantOrderConfirmService.addMerchantOrderConfirm(orderConfirm, null);
				}
			}
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
			logger.info("--------扫描商家对账，开始时间：" + new Date());
			// 执行业务方法
			quartzStart();

			logger.info("--------扫描商家对账任务完成...");
		} catch (Exception e) {
			logger.error("--------扫描商家对账，错误信息：" + e.getMessage(), e);
		}
	}
	public static void main(String[] args) throws ParseException {
		String date = "2018-01-21至2018-01-23".substring("2018-01-21至2018-01-23".indexOf("至")+1);
		Date paidTimeStart = ECDateUtils.getDateAfter(ECDateUtils.parseDate(date, "yyyy-MM-dd"), 1);
		Date paidTimeEnd = ECDateUtils.getCurrentDateEndTime(ECDateUtils.getDateAfter(paidTimeStart, 2));
			System.err.println(paidTimeStart);
			System.err.println(paidTimeEnd);
	}
}
