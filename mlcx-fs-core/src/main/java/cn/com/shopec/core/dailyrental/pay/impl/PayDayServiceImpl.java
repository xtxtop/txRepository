package cn.com.shopec.core.dailyrental.pay.impl;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdom.JDOMException;
import org.springframework.stereotype.Service;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;

import cn.com.shopec.common.Operator;
import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.common.cache.CommonCacheUtil;
import cn.com.shopec.common.constants.Constant;
import cn.com.shopec.common.pay.aliPay.AlipayConfig;
import cn.com.shopec.common.pay.aliPay.AlipayNotify;
import cn.com.shopec.common.pay.aliPay.OrderInfoUtil2_0;
import cn.com.shopec.common.pay.wxPay.CommonUtil;
import cn.com.shopec.common.pay.wxPay.PayCommonUtil;
import cn.com.shopec.common.pay.wxPay.ResponseHandler;
import cn.com.shopec.common.pay.wxPay.TenpayUtil;
import cn.com.shopec.common.pay.wxPay.WxpayConfig;
import cn.com.shopec.common.pay.wxPay.XMLUtil;
import cn.com.shopec.common.utils.ECCalculateUtils;
import cn.com.shopec.common.utils.ECDateUtils;
import cn.com.shopec.common.utils.ECNumberUtils;
import cn.com.shopec.common.utils.ECRandomUtils;
import cn.com.shopec.common.utils.Uint32;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.dailyrental.dao.MerchantInventoryDao;
import cn.com.shopec.core.dailyrental.dao.MerchantInventoryDateDao;
import cn.com.shopec.core.dailyrental.model.CarInventory;
import cn.com.shopec.core.dailyrental.model.CarInventoryDate;
import cn.com.shopec.core.dailyrental.model.DelayOrderDay;
import cn.com.shopec.core.dailyrental.model.MerchantInventory;
import cn.com.shopec.core.dailyrental.model.MerchantInventoryDate;
import cn.com.shopec.core.dailyrental.model.OrderDay;
import cn.com.shopec.core.dailyrental.pay.PayDayService;
import cn.com.shopec.core.dailyrental.service.CarInventoryDateService;
import cn.com.shopec.core.dailyrental.service.CarInventoryService;
import cn.com.shopec.core.dailyrental.service.DelayOrderDayService;
import cn.com.shopec.core.dailyrental.service.MerchantInventoryDateService;
import cn.com.shopec.core.dailyrental.service.OrderDayService;
import cn.com.shopec.core.dailyrental.util.AllCouponHolder;
import cn.com.shopec.core.finace.model.PaymentRecord;
import cn.com.shopec.core.finace.service.PaymentRecordService;
import cn.com.shopec.core.marketing.model.Coupon;
import cn.com.shopec.core.marketing.service.CouponService;
import cn.com.shopec.core.member.service.MemberPointsRuleService;
import cn.com.shopec.core.order.common.OrderConstant;
import cn.com.shopec.core.system.model.SysParam;
import cn.com.shopec.core.system.service.SysParamService;

/**
 * 支付
 */
@Service
public class PayDayServiceImpl implements PayDayService {

	private static final Log log = LogFactory.getLog(PayDayServiceImpl.class);

	@Resource
	private OrderDayService orderDayService;

	@Resource
	private SysParamService sysParamService;

	@Resource
	private PaymentRecordService paymentRecordService;

	@Resource
	private MemberPointsRuleService memberPointsRuleService;

	@Resource
	private CommonCacheUtil cacheUtil;

	@Resource
	private CarInventoryService carInventoryService;

	@Resource
	private CarInventoryDateService carInventoryDateService;
	@Resource
	private CouponService couponService;
	@Resource
	private DelayOrderDayService delayOrderDayService;
	@Resource
	private MerchantInventoryDateService merchantInventoryDateService;
	@Resource
	private MerchantInventoryDao merchantInventoryDao;
	@Resource
	private MerchantInventoryDateDao merchantInventoryDateDao;

	/**
	 * 微信支付回调方法
	 */
	@Override
	public void wxUpdateOrder(HttpServletRequest request, HttpServletResponse response, Operator operator) {
		String out_trade_no = "";
		synchronized (this) {
			try {
				String appkey = WxpayConfig.key;// 商户平台上那个自己生成的KEY
				ResponseHandler resHandler = new ResponseHandler(request, response);
				resHandler.setKey(appkey);
				Map postdata = resHandler.getSmap();
				if (resHandler.isWechatSign()) {// 是否微信签名
					// 商户订单号 即 附带随机码的平台内部支付流水号
					String part_trade_flow_no = (String) postdata.get("out_trade_no");
					// 支付订单号
					String trade_no = part_trade_flow_no.substring(0, part_trade_flow_no.lastIndexOf("_"));
					// 微信支付订单号
					String transaction_id = (String) postdata.get("transaction_id");
					// 金额,以分为单位
					Double totalFee = Double.valueOf(postdata.get("total_fee") + "");
					totalFee = totalFee / 100; // 这块需转换
					// 支付完成时间
					String time_end = (String) postdata.get("time_end");
					// 支付结果 业务结果
					String trade_state = (String) postdata.get("result_code");

					// 微信用户openId
					String openId = (String) postdata.get("openid");
					/*
					 * 支付记录表添加数据
					 */
					PaymentRecord pr = new PaymentRecord();

					// 判断签名及结果
					if ("SUCCESS".equals(trade_state)) {
						OrderDay order = orderDayService.getOrderDay(trade_no);
						if (null != order && order.getPayStatus() == 0) {
							if (order.getIsDelayOrder() != null && order.getIsDelayOrder() == 1) {
								DelayOrderDay delayOrderDay = delayOrderDayService
										.getDelayOrderDayByOrderDayNo(order.getOrderNo());
								order.setPayStatus(1);// 支付状态为已支付
								order.setAppointmentReturnTime(delayOrderDay.getFinishTime());
								order.setInsurance(
										ECCalculateUtils.add(order.getInsurance(), delayOrderDay.getInsurance()));
								order.setCarRentalAmount(ECCalculateUtils.add(order.getCarRentalAmount(),
										delayOrderDay.getCarRentalAmount()));
								if (order.getRegardlessFranchise() != null) {
									order.setRegardlessFranchise(ECCalculateUtils.add(order.getRegardlessFranchise(),
											delayOrderDay.getRegardlessFranchise()));
								} else {
									order.setRegardlessFranchise(delayOrderDay.getRegardlessFranchise());
								}
								order.setAmountPaid(ECNumberUtils.roundDoubleWithScale(
										ECCalculateUtils.add(order.getAmountPaid(), totalFee), 2));
								order.setOrderDuration(order.getOrderDuration() + delayOrderDay.getReplenishDays());
								order.setPayableAmount(ECCalculateUtils.add(order.getPayableAmount(),
										delayOrderDay.getTotalReplenishAmount()));
								order.setOrderAmount(ECCalculateUtils.add(order.getOrderAmount(),
										delayOrderDay.getTotalReplenishAmount()));
								order.setIsDelayOrder(2);// 续租支付成功
								orderDayService.updateOrderDay(order, null);

								// 续租订单信息修改
								DelayOrderDay delayOrderDayForUpdate = new DelayOrderDay();
								delayOrderDayForUpdate.setDelayOrderId(delayOrderDay.getDelayOrderId());
								delayOrderDayForUpdate.setPayStatus(1);// 支付状态为已支付
								delayOrderDayForUpdate.setPaymentMethod(2);// 支付方式（1.支付宝，2.微信）
								delayOrderDayForUpdate.setPaymentFlowNo(transaction_id);// 支付流水号（微信支付订单号）
								// 内部支付流水号
								delayOrderDayForUpdate.setPartTradeFlowNo(part_trade_flow_no); //
								if (time_end != null) {
									SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
									String dstr = time_end;
									Date nodate = sdf.parse(dstr);
									delayOrderDayForUpdate.setPaymentTime(nodate);
								} else {
									delayOrderDayForUpdate.setPaymentTime(new Date());
								}
								ResultInfo<DelayOrderDay> res = delayOrderDayService
										.updateDelayOrderDay(delayOrderDayForUpdate, null);
								if ("1".equals(res.getCode())) {
									upateDelayOrderInventory(order, delayOrderDay);
								}

							} else {
								order.setPayStatus(1);// 支付状态为已支付
								order.setPaymentMethod(2);// 支付方式（1.支付宝，2.微信）
								order.setPaymentFlowNo(transaction_id);// 支付流水号（微信支付订单号）
								// 内部支付流水号
								order.setPartTradeFlowNo(part_trade_flow_no); //
								if (time_end != null) {
									SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
									String dstr = time_end;
									Date nodate = sdf.parse(dstr);
									order.setPaymentTime(nodate);
								} else {
									order.setPaymentTime(new Date());
								}
								order.setAmountPaid(ECNumberUtils.roundDoubleWithScale(totalFee, 2));
								orderDayService.updateOrderDay(order, null);

								String couponNo = AllCouponHolder.getInstance().getCoupon(order.getMemberNo());
								// 使用优惠券进行订单抵扣
								if (!"".equals(couponNo)) {
									// 修改此优惠券状态
									Coupon cp = new Coupon();
									cp.setCouponNo(couponNo);
									cp.setUsedStatus(1);
									cp.setUsedTime(order.getAppointmentTakeTime());
									cp.setBizObjNo(order.getOrderNo());
									cp.setBizObjType(1);
									ResultInfo<Coupon> res = couponService.updateCoupon(cp, null);
									if ("1".equals(res.getCode())) {
										AllCouponHolder.getInstance().deleteCoupon(order.getMemberNo());
									}
								}
								// 支付成功后，修改车辆库存
								setCarInventory(order);
							}

							pr.setBizObjType(2);// 订单
							pr.setBizObjNo(order.getOrderNo());
							pr.setPayableAmount(ECNumberUtils.roundDoubleWithScale(order.getPayableAmount(), 2));
							pr.setPaidAmount(ECNumberUtils.roundDoubleWithScale(totalFee, 2));
							pr.setPayStatus(1);
							pr.setPayType(2);
							pr.setPayFlowNo(transaction_id); // 外部支付流水号
							pr.setPartTradeFlowNo(part_trade_flow_no); // 内部支付流水号
							pr.setPaidTime(new Date());
							// 设置支付账户标识
							pr.setBuyerId(openId);
							paymentRecordService.addPaymentRecord(pr, null);
						} else {
							log.info("重复请求");
							return;
						}
						resHandler.sendToCFT("<xml><return_code><![CDATA[SUCCESS]]></return_code></xml>");
					} else {// sha1签名失败
						resHandler.sendToCFT("<xml><return_code><![CDATA[FAIL]]></return_code></xml>");
					}
				} else {// MD5签名失败
					resHandler.sendToCFT("<xml><return_code><![CDATA[FAIL]]></return_code></xml>");
				}

			} catch (Exception e) {
				log.info("exeption");
				e.printStackTrace();
			}
		}
	}

	@Override
	// tag:0:安卓，1：ios
	public ResultInfo<SortedMap<String, Object>> getCodeUrl(HttpServletRequest request, HttpServletResponse response,
			String payNo, String memberNo, Integer tag) {
		ResultInfo<SortedMap<String, Object>> resultInfo = new ResultInfo<SortedMap<String, Object>>();
		// 总金额以分为单位，不带小数点
		int total_fee = 0;
		// 这里notify_url是 支付完成后微信发给该链接信息，可以判断会员是否支付成功，改变订单状态等。处理完调用的路径
		String notify_url = "";
		// 商品描述根据情况修改
		String body = "";
		// 商户订单号
		String out_trade_no = "";

		OrderDay order = orderDayService.getOrderDay(payNo);
		if (order.getPayStatus().equals(1)) {
			resultInfo.setCode(OrderConstant.alreday_pay);
			resultInfo.setMsg(OrderConstant.alreday_pay_msg);
			return resultInfo;
		}
		if (order.getOrderStatus().equals(4)) {
			resultInfo.setCode("3");
			resultInfo.setMsg("订单已取消");
			return resultInfo;
		}
		if (order.getIsDelayOrder() != null && order.getIsDelayOrder() == 1) {
			DelayOrderDay delayOrderDay = delayOrderDayService.getDelayOrderDayByOrderDayNo(order.getOrderNo());
			// 是否对应车型还有库存
			CarInventory carInventory = carInventoryService.getCarInventoryByCarModelIdAndCity(order.getCarModelId(),
					order.getCityId());
			if (carInventory == null) {
				resultInfo.setCode(Constant.FAIL);
				resultInfo.setMsg("车辆库存信息异常");
				return resultInfo;
			}
			List<String> allDates = ECDateUtils.collectLocalDates(
					ECDateUtils.formatDate(order.getAppointmentTakeTime(), "yyyy-MM-dd"),
					ECDateUtils.formatDate(order.getAppointmentReturnTime(), "yyyy-MM-dd"));
			Date startTime = delayOrderDay.getStartTime();
			Date finishTime = delayOrderDay.getFinishTime();
			// 处理要设置的自定义日期
			if (order.getOrderDuration() == allDates.size()) {
				startTime = ECDateUtils.getDateAfter(startTime, 1);
			}
			if (order.getOrderDuration() < allDates.size()) {
				finishTime = ECDateUtils.getDateBefore(finishTime, 1);
			}
			// 查询续租时间内车辆库存日历表是否存在库存为0的记录
			CarInventoryDate queryCarInventoryDate = new CarInventoryDate();
			queryCarInventoryDate.setCarInventoryId(carInventory.getCarInventoryId());
			queryCarInventoryDate.setAvailableInventory(0);
			queryCarInventoryDate.setInventoryDateStart(startTime);
			queryCarInventoryDate.setInventoryDateEnd(finishTime);
			List<CarInventoryDate> list = carInventoryDateService
					.getCarInventoryDateList(new Query(queryCarInventoryDate));
			if (list.size() > 0) {
				resultInfo.setCode(Constant.FAIL);
				resultInfo.setMsg("车辆库存不足，不能续租");
				return resultInfo;
			}
			total_fee = (int) (delayOrderDay.getTotalReplenishAmount() * 100);
		} else {
			total_fee = (int) (order.getPayableAmount() * 100);
		}
		notify_url = WxpayConfig.notify_url;
		// 商品描述根据情况修改
		body = "猛龙出行-订单支付";
		out_trade_no = payNo;

		SysParam sysp = sysParamService.getByParamKey(OrderConstant.IS_Correct_OrderAmount);
		if (sysp != null && sysp.getParamValue() != null && sysp.getParamValue().equals("0")) {
			total_fee = (int) (1);
		}

		// 调用统一支付接口https://api.mch.weixin.qq.com/pay/unifiedorder
		// 请求参数准备
		String appid = WxpayConfig.appID;// 微信开放平台审核通过的应用APPID
		String mch_id = WxpayConfig.mchID;// 微信支付分配的商户号
		String device_info = "";// 设备号 非必输

		// 生成随机字符串
		String currTime = TenpayUtil.getCurrTime();
		String strTime = currTime.substring(8, currTime.length());// 8位日期
		String strRandom = TenpayUtil.buildRandom(4) + "";// 四位随机数
		String strReq = strTime + strRandom;// 10位序列号,可以自行调整。
		String nonce_str = strReq;

		// 商品描述
		// String body = describe;

		// 附加数据
		// String attach = userId;

		// 订单生成的机器 IP
		String spbill_create_ip = "";
		// 订 单 生 成 时 间 非必输
		String time_start = "";
		// 订单失效时间 非必输
		String time_expire = "";
		// 商品标记 非必输
		String goods_tag = "";

		String trade_type = "APP";// 交易类型

		if (tag.intValue() == 10) {
			appid = WxpayConfig.h5_appID;
			mch_id = WxpayConfig.h5_mchID;
			trade_type = "JSAPI";
		}

		// 生成签名
		SortedMap<String, String> packageParams = new TreeMap<String, String>();
		packageParams.put("appid", appid);
		packageParams.put("mch_id", mch_id);
		packageParams.put("nonce_str", nonce_str);
		packageParams.put("body", body);
		// packageParams.put("attach", attach);

		// 商户交易单号，之前业务为将业务单号作为交易单号传递到微信平台，后发现此种做法有问题会引发二次唤起微信支付失败或者订单失效无法支付的问题，解决方式为每次唤起支付的时候都在支付记录中添加新纪录，新增内部支付流水单号，格式为业务单号_8位随机数
		out_trade_no = out_trade_no + "_" + ECRandomUtils.getRandomAlphamericStr(8);

		packageParams.put("out_trade_no", out_trade_no);
		// TODO 这里写的金额为1 分 通过系统参数变量来控制支付金额通道- 参数建 IS_Correct_OrderAmount
		packageParams.put("total_fee", "" + total_fee);
		packageParams.put("spbill_create_ip", spbill_create_ip);
		packageParams.put("notify_url", notify_url);
		packageParams.put("trade_type", trade_type);
		if (tag.intValue() == 10) {
			String openid = getWxAuthOpenid(memberNo);
			packageParams.put("openid", openid);
			System.out.println("pay openid+++++++++++ " + openid);
		}

		String sign = PayCommonUtil.createSign("UTF-8", packageParams);// 生成签名
		packageParams.put("sign", sign);
		packageParams.remove("key");// 调用统一下单无需key（商户应用密钥）

		String requestXml = PayCommonUtil.getRequestXml(packageParams);// 生成Xml格式的字符串
		String createOrderURL = "https://api.mch.weixin.qq.com/pay/unifiedorder";
		String result = CommonUtil.httpsRequest(createOrderURL, "POST", requestXml);// 以post请求的方式调用统一下单接口

		System.out.println("pay request+++++++++++ " + requestXml);
		System.out.println("pay result++++++++++++ " + result);
		Map map;
		try {
			map = XMLUtil.doXMLParse(result);
			String return_code = (String) map.get("return_code");
			String prepay_id = null;
			if (return_code.contains("SUCCESS")) {
				prepay_id = (String) map.get("prepay_id");// 获取到prepay_id
				String timeStamp = ECDateUtils.formatStringTimeWX(new Date());
				if (tag.equals(0)) {// 安卓
					// 2.签名返回信息
					resultInfo = getCodeUrlApp(request, response, prepay_id, nonce_str, timeStamp);
				} else if (tag.equals(1)) {// ios
					Long time = System.currentTimeMillis() / 1000;
					Uint32 timeStamp1 = new Uint32(time);
					// 2.签名返回信息
					resultInfo = getCodeUrlAppIOS(request, response, prepay_id, nonce_str, timeStamp1);
				} else if (tag == 10) {

					resultInfo = getCodeUrlH5(request, response, prepay_id, nonce_str,
							"" + new Date().getTime() / 1000);
				}

				resultInfo.setCode(Constant.SECCUESS);
			} else {
				resultInfo.setCode(Constant.FAIL);
			}
		} catch (JDOMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			resultInfo.setCode(Constant.FAIL);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			resultInfo.setCode(Constant.FAIL);
		}
		return resultInfo;
	}

	@Override
	public ResultInfo<SortedMap<String, Object>> getCodeUrlApp(HttpServletRequest request, HttpServletResponse response,
			String prepay_id, String nonceStr, String timeStamp) {
		ResultInfo<SortedMap<String, Object>> resultInfo = new ResultInfo<SortedMap<String, Object>>();
		// 请求参数准备
		String appid = WxpayConfig.appID;// 微信开放平台审核通过的应用APPID
		String partnerId = WxpayConfig.mchID;// 微信支付分配的商户号
		// 生成签名
		SortedMap<String, Object> packageParams = new TreeMap<String, Object>();
		packageParams.put("appid", appid);
		packageParams.put("partnerid", partnerId);
		packageParams.put("prepayid", prepay_id);
		packageParams.put("package", "Sign=WXPay");
		packageParams.put("noncestr", nonceStr);
		packageParams.put("timestamp", timeStamp);
		String sign = PayCommonUtil.createSignIOS("UTF-8", packageParams);
		packageParams.put("sign", sign);
		packageParams.put("packageStr", "Sign=WXPay");// app端package关键字
		if (sign != null && !sign.equals("")) {
			resultInfo.setCode(Constant.SECCUESS);
			resultInfo.setData(packageParams);
		} else {
			resultInfo.setCode(Constant.FAIL);
		}
		return resultInfo;
	}

	public ResultInfo<SortedMap<String, Object>> getCodeUrlAppIOS(HttpServletRequest request,
			HttpServletResponse response, String prepay_id, String nonceStr, Uint32 timeStamp) {
		ResultInfo<SortedMap<String, Object>> resultInfo = new ResultInfo<SortedMap<String, Object>>();
		// 请求参数准备
		String appid = WxpayConfig.appID;// 微信开放平台审核通过的应用APPID
		String partnerId = WxpayConfig.mchID;// 微信支付分配的商户号

		// 生成签名
		SortedMap<String, Object> packageParams = new TreeMap<String, Object>();
		packageParams.put("appid", appid);
		packageParams.put("partnerid", partnerId);
		packageParams.put("prepayid", prepay_id);
		packageParams.put("package", "Sign=WXPay");
		packageParams.put("noncestr", nonceStr);
		packageParams.put("timestamp", timeStamp);
		String sign = PayCommonUtil.createSignIOS("UTF-8", packageParams);
		packageParams.put("sign", sign);
		packageParams.put("packageStr", "Sign=WXPay");// app端package关键字
		packageParams.put("timestamp", timeStamp.toString());
		if (sign != null && !sign.equals("")) {
			resultInfo.setCode(Constant.SECCUESS);
			resultInfo.setData(packageParams);
		} else {
			resultInfo.setCode(Constant.FAIL);
		}
		return resultInfo;
	}

	public ResultInfo<SortedMap<String, Object>> getCodeUrlH5(HttpServletRequest request, HttpServletResponse response,
			String prepay_id, String nonceStr, String timeStamp) {
		ResultInfo<SortedMap<String, Object>> resultInfo = new ResultInfo<SortedMap<String, Object>>();
		// 请求参数准备
		String appid = WxpayConfig.h5_appID;// 微信开放平台审核通过的应用APPID
		String partnerId = WxpayConfig.h5_mchID;// 微信支付分配的商户号

		// 生成签名
		SortedMap<String, Object> packageParams = new TreeMap<String, Object>();
		packageParams.put("appId", appid);
		packageParams.put("timeStamp", timeStamp);
		packageParams.put("nonceStr", nonceStr);
		packageParams.put("package", "prepay_id=" + prepay_id);
		// packageParams.put("prepay_id", prepay_id);
		packageParams.put("signType", "MD5");
		String sign = PayCommonUtil.createSignIOS("UTF-8", packageParams);
		packageParams.put("sign", sign);
		packageParams.put("appid", appid);
		packageParams.put("partnerid", partnerId);
		packageParams.put("prepayid", prepay_id);
		packageParams.put("noncestr", nonceStr);
		packageParams.put("timestamp", timeStamp);
		packageParams.put("packageStr", "Sign=WXPay");// app端package关键字
		packageParams.put("signType", "MD5");
		if (sign != null && !sign.equals("")) {
			resultInfo.setCode(Constant.SECCUESS);
			resultInfo.setData(packageParams);
		} else {
			resultInfo.setCode(Constant.FAIL);
		}
		return resultInfo;
	}

	/**
	 * 调用微信查询订单支付结果的接口
	 */
	@Override
	public ResultInfo<String> wxGetOrderPayResult(HttpServletRequest request, HttpServletResponse response,
			String orderNo) {
		ResultInfo<String> resultInfo = new ResultInfo<String>();
		// 生成随机字符串
		String currTime = TenpayUtil.getCurrTime();
		String strTime = currTime.substring(8, currTime.length());// 8位日期
		String strRandom = TenpayUtil.buildRandom(4) + "";// 四位随机数
		String strReq = strTime + strRandom;// 10位序列号,可以自行调整。
		String nonce_str = strReq;
		String flag = orderNo.substring(0, 1);

		SortedMap<String, String> packageParams = new TreeMap<String, String>();
		packageParams.put("appid", WxpayConfig.appID);
		packageParams.put("mch_id", WxpayConfig.mchID);

		packageParams.put("nonce_str", nonce_str);
		String sign = PayCommonUtil.createSign("UTF-8", packageParams);// 生成签名
		packageParams.put("sign", sign);
		packageParams.remove("key");// 调用统一下单无需key（商户应用密钥）
		String requestXml = PayCommonUtil.getRequestXml(packageParams);// 生成Xml格式的字符串
		String createOrderURL = "https://api.mch.weixin.qq.com/pay/orderquery";
		String result = CommonUtil.httpsRequest(createOrderURL, "POST", requestXml);// 以post请求的方式调用统一下单接口
		Map map;
		String trade_state = "";
		try {
			map = XMLUtil.doXMLParse(result);
			String return_code = (String) map.get("return_code");
			String result_code = (String) map.get("result_code");
			if (return_code.contains("SUCCESS")) {
				if (result_code.contains("SUCCESS")) {
					trade_state = (String) map.get("trade_state");

					/*
					 * SUCCESS—支付成功 REFUND—转入退款 NOTPAY—未支付 CLOSED—已关闭
					 * REVOKED—已撤销（刷卡支付） USERPAYING--用户支付中
					 * PAYERROR--支付失败(其他原因，如银行返回失败)
					 */
					resultInfo.setCode(Constant.SECCUESS);
					resultInfo.setData(trade_state);
				} else {
					resultInfo.setCode(Constant.FAIL);
				}
			} else {
				resultInfo.setCode(Constant.FAIL);
			}
		} catch (JDOMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			resultInfo.setCode(Constant.FAIL);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			resultInfo.setCode(Constant.FAIL);
		}
		return resultInfo;
	}

	@Override
	public ResultInfo<String> alipayGetOrderStr(HttpServletRequest request, HttpServletResponse response, String payNo,
			String memberNo) {
		ResultInfo<String> resultInfo = new ResultInfo<String>();
		String subject = "";// (商品名称)
		String out_trade_no = "";// 订单号
		String total_amount = "";// 订单总金额，单位为元，精确到小数点后两位

		OrderDay order = orderDayService.getOrderDay(payNo);
		if (order.getPayStatus().equals(1)) {
			resultInfo.setCode(OrderConstant.alreday_pay);
			resultInfo.setMsg(OrderConstant.alreday_pay_msg);
			return resultInfo;
		}
		if (order.getOrderStatus().equals(4)) {
			resultInfo.setCode("3");
			resultInfo.setMsg("订单已取消");
			return resultInfo;
		}
		subject = order.getOrderNo();// (商品名称)
		out_trade_no = order.getOrderNo();// 订单号
		if (order.getIsDelayOrder() != null && order.getIsDelayOrder() == 1) {
			DelayOrderDay delayOrderDay = delayOrderDayService.getDelayOrderDayByOrderDayNo(order.getOrderNo());
			// 是否对应车型还有库存
			CarInventory carInventory = carInventoryService.getCarInventoryByCarModelIdAndCity(order.getCarModelId(),
					order.getCityId());
			if (carInventory == null) {
				resultInfo.setCode(Constant.FAIL);
				resultInfo.setMsg("车辆库存信息异常");
				return resultInfo;
			}
			if (carInventory.getAvailableInventory() == 0) {
				resultInfo.setCode(Constant.FAIL);
				resultInfo.setMsg("车辆库存不足，不能续租");
				return resultInfo;
			}
			// 查询续租时间内车辆库存日历表是否存在库存为0的记录
			CarInventoryDate queryCarInventoryDate = new CarInventoryDate();
			queryCarInventoryDate.setCarInventoryId(carInventory.getCarInventoryId());
			queryCarInventoryDate.setAvailableInventory(0);
			queryCarInventoryDate.setInventoryDateStart(delayOrderDay.getStartTime());
			queryCarInventoryDate.setInventoryDateEnd(delayOrderDay.getFinishTime());
			List<CarInventoryDate> list = carInventoryDateService
					.getCarInventoryDateList(new Query(queryCarInventoryDate));
			if (list.size() > 0) {
				resultInfo.setCode(Constant.FAIL);
				resultInfo.setMsg("车辆库存不足，不能续租");
				return resultInfo;
			}
			total_amount = delayOrderDay.getTotalReplenishAmount().toString();// 订单总金额，单位为元，精确到小数点后两位
		} else {
			total_amount = order.getPayableAmount().toString();// 订单总金额，单位为元，精确到小数点后两位
		}
		SysParam sysp = sysParamService.getByParamKey(OrderConstant.IS_Correct_OrderAmount);
		if (sysp != null && sysp.getParamValue() != null && sysp.getParamValue().equals("0")) {
			total_amount = 0.01 + "";// 订单总金额，单位为元，精确到小数点后两位
		}

		// 商户交易单号，之前业务为将业务单号作为交易单号传递到支付宝平台，后发现此种做法有问题会引发二次唤起支付失败或者订单失效无法支付的问题，解决方式为每次唤起支付的时候都在支付记录中添加新纪录，新增内部支付流水单号，格式为业务单号_8位随机数
		out_trade_no = out_trade_no + "_" + ECRandomUtils.getRandomAlphamericStr(8);
		SysParam spmAlipay = sysParamService.getByParamKey("aliPaySignType");
		if (spmAlipay != null && "2".equals(spmAlipay.getParamValue())) {
			// 实例化客户端
			AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do",
					AlipayConfig.appId, AlipayConfig.rsa_private, "json", "UTF-8", AlipayConfig.rsa_public, "RSA2");
			// 实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.trade.app.pay
			AlipayTradeAppPayRequest re = new AlipayTradeAppPayRequest();
			// SDK已经封装掉了公共参数，这里只需要传入业务参数。以下方法为sdk的model入参方式(model和biz_content同时存在的情况下取biz_content)。
			AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
			model.setBody("漫享出行-订单支付");
			model.setSubject(subject);
			model.setOutTradeNo(out_trade_no);
			model.setTimeoutExpress("30m");
			model.setTotalAmount(total_amount);
			model.setProductCode("QUICK_MSECURITY_PAY");
			re.setBizModel(model);
			re.setNotifyUrl(AlipayConfig.notify_url);
			try {
				// 这里和普通的接口调用不同，使用的是sdkExecute
				AlipayTradeAppPayResponse rs = alipayClient.sdkExecute(re);
				System.out.println(rs.getBody());// 就是orderString
				resultInfo.setCode(Constant.SECCUESS);
				resultInfo.setData(rs.getBody());
				// 可以直接给客户端请求，无需再做处理。
			} catch (AlipayApiException e) {
				resultInfo.setCode(Constant.FAIL);
				e.printStackTrace();
			}

		} else {
			Map<String, String> authInfoMap = OrderInfoUtil2_0.buildOrderParamMap(AlipayConfig.appId, out_trade_no,
					AlipayConfig.notify_url, total_amount, subject);
			String orderParam = OrderInfoUtil2_0.buildOrderParam(authInfoMap);
			String sign = OrderInfoUtil2_0.getSign(authInfoMap, AlipayConfig.rsa_private);
			final String orderInfo = orderParam + "&" + sign;
			String result = orderInfo;
			if (result != null && !result.equals("")) {
				resultInfo.setCode(Constant.SECCUESS);
				resultInfo.setData(result);
			} else {
				resultInfo.setCode(Constant.FAIL);
			}
			return resultInfo;
		}

		return resultInfo;

	}

	/**
	 * 支付宝支付-异步回调后台地址
	 */
	@Override
	public void alipayUpdateOrder(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 获取支付宝POST过来反馈信息
		Map<String, String> params = new HashMap<String, String>();
		Map requestParams = request.getParameterMap();
		for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
			}
			params.put(name, valueStr);
		}

		// 获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//
		// 商户订单号 即 附带随机码的平台内部支付流水号
		String part_trade_flow_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"), "UTF-8");
		// 商户订单号
		String shop_order_no = part_trade_flow_no.substring(0, part_trade_flow_no.lastIndexOf("_"));// 交易状态
		// 支付宝交易流水号
		String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"), "UTF-8");
		// 交易状态
		String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"), "UTF-8");
		// 购买时间
		String notify_time = new String(request.getParameter("notify_time").getBytes("ISO-8859-1"), "UTF-8");
		// 支付总金额金额
		Double total_amount = Double.valueOf(request.getParameter("total_amount") + "");
		// 买家支付时间
		String gmt_payment = request.getParameter("gmt_payment") != null
				? new String(request.getParameter("gmt_payment").getBytes("ISO-8859-1"), "UTF-8") : null;

		Date paidTime = null;
		if (gmt_payment != null && !gmt_payment.equals("")) { // 使用gmt_payment作为支付时间
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			paidTime = sdf.parse(gmt_payment);
		}
		paidTime = paidTime == null ? new Date() : paidTime;
		// 买家支付宝账户号
		String buyerId = new String(request.getParameter("buyer_id").getBytes("ISO-8859-1"), "UTF-8");
		// 获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以上仅供参考)//
		SysParam spmAlipay = sysParamService.getByParamKey("aliPaySignType");
		if (spmAlipay != null && "2".equals(spmAlipay.getParamValue())) {
			if (AlipaySignature.rsaCheckV1(params, AlipayConfig.rsa_public, "UTF-8", "RSA2")) {// 验证成功
				//////////////////////////////////////////////////////////////////////////////////////////
				// 请在这里加上商户的业务逻辑程序代码

				// ——请根据您的业务逻辑来编写程序（以下代码仅作参考）——
				log.info("pay sign success,begin transition");
				log.info("the trade_status is " + trade_status);
				if (trade_status.equals("TRADE_FINISHED")) {
					// 判断该笔订单是否在商户网站中已经做过处理
					// 如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
					// 如果有做过处理，不执行商户的业务程序
					// 该种交易状态只在两种情况下出现
					// ——请根据您的业务逻辑来编写程序（以上代码仅作参考）——
					/*
					 * 支付记录表添加数据
					 */
					PaymentRecord pr = new PaymentRecord();

					// flag 表示支付的业务类型， 代表订单D， Y 代表押金，T 代表套餐
					String flag = shop_order_no.substring(0, 1);

					log.info("the trade_type is order");
					OrderDay order = orderDayService.getOrderDay(shop_order_no);
					if (order.getIsDelayOrder() != null && order.getIsDelayOrder() == 1) {
						DelayOrderDay delayOrderDay = delayOrderDayService
								.getDelayOrderDayByOrderDayNo(order.getOrderNo());
						order.setPayStatus(1);// 支付状态为已支付
						order.setAppointmentReturnTime(delayOrderDay.getFinishTime());
						order.setInsurance(ECCalculateUtils.add(order.getInsurance(), delayOrderDay.getInsurance()));
						order.setCarRentalAmount(
								ECCalculateUtils.add(order.getCarRentalAmount(), delayOrderDay.getCarRentalAmount()));
						if (order.getRegardlessFranchise() != null) {
							order.setRegardlessFranchise(ECCalculateUtils.add(order.getRegardlessFranchise(),
									delayOrderDay.getRegardlessFranchise()));
						} else {
							order.setRegardlessFranchise(delayOrderDay.getRegardlessFranchise());
						}
						if (order.getAmountPaid() != null) {
							order.setAmountPaid(ECNumberUtils.roundDoubleWithScale(
									ECCalculateUtils.add(order.getAmountPaid(), total_amount), 2));
						} else {
							order.setAmountPaid(
									ECNumberUtils.roundDoubleWithScale(ECCalculateUtils.add(0.0, total_amount), 2));
						}

						order.setOrderDuration(order.getOrderDuration() + delayOrderDay.getReplenishDays());
						order.setPayableAmount(ECCalculateUtils.add(order.getPayableAmount(),
								delayOrderDay.getTotalReplenishAmount()));
						order.setOrderAmount(
								ECCalculateUtils.add(order.getOrderAmount(), delayOrderDay.getTotalReplenishAmount()));
						order.setIsDelayOrder(2);// 续租支付成功
						orderDayService.updateOrderDay(order, null);

						// 续租订单信息修改
						DelayOrderDay delayOrderDayForUpdate = new DelayOrderDay();
						delayOrderDayForUpdate.setDelayOrderId(delayOrderDay.getDelayOrderId());
						delayOrderDayForUpdate.setPayStatus(1);// 支付状态为已支付
						delayOrderDayForUpdate.setPaymentMethod(1);// 支付方式（1.支付宝，2.微信）
						delayOrderDayForUpdate.setPaymentFlowNo(part_trade_flow_no);// 支付流水号（微信支付订单号）
						// 内部支付流水号
						delayOrderDayForUpdate.setPartTradeFlowNo(trade_no); //
						delayOrderDayForUpdate.setPaymentTime(paidTime);
						ResultInfo<DelayOrderDay> res = delayOrderDayService.updateDelayOrderDay(delayOrderDayForUpdate,
								null);
						if ("1".equals(res.getCode())) {
							upateDelayOrderInventory(order, delayOrderDay);
						}
					} else {
						order.setPayStatus(1);// 已支付
						order.setPaymentMethod(1);// 支付方式（1.支付宝，2.微信）
						order.setPaymentFlowNo(trade_no);// 支付流水号（支付宝交易流水号）
						order.setPartTradeFlowNo(part_trade_flow_no); // 内部支付流水号
						order.setPaymentTime(paidTime);
						order.setAmountPaid(total_amount);
						orderDayService.updateOrderDay(order, null);

						String couponNo = AllCouponHolder.getInstance().getCoupon(order.getMemberNo());
						// 使用优惠券进行订单抵扣
						if (!"".equals(couponNo)) {
							// 修改此优惠券状态
							Coupon cp = new Coupon();
							cp.setCouponNo(couponNo);
							cp.setUsedStatus(1);
							cp.setUsedTime(order.getAppointmentTakeTime());
							cp.setBizObjNo(order.getOrderNo());
							cp.setBizObjType(1);
							ResultInfo<Coupon> res = couponService.updateCoupon(cp, null);
							if ("1".equals(res.getCode())) {
								AllCouponHolder.getInstance().deleteCoupon(order.getMemberNo());
							}
						}
						setCarInventory(order);
					}

					pr.setBizObjType(2);// 订单
					pr.setBizObjNo(order.getOrderNo());
					pr.setPayableAmount(ECNumberUtils.roundDoubleWithScale(order.getPayableAmount(), 2));
					pr.setPaidAmount(total_amount);
					pr.setPaidTime(order.getPaymentTime());

					pr.setPayStatus(1);
					pr.setPayType(1);
					pr.setPayFlowNo(trade_no);
					pr.setPartTradeFlowNo(part_trade_flow_no); // 内部支付流水号
					paymentRecordService.addPaymentRecord(pr, null);
					// 支付成功后，修改车辆库存

					// out.println("success"); //请不要修改或删除
					response.setContentType("text/html;charset=UTF-8");
					response.setCharacterEncoding("UTF-8");
					response.setStatus(HttpServletResponse.SC_OK);
					PrintWriter out = response.getWriter();
					out.println("success");
					out.flush();
					out.close();
					// System.out.println("success");
					// 1、开通了普通即时到账，买家付款成功后。
					// 2、开通了高级即时到账，从该笔交易成功时间算起，过了签约时的可退款时限（如：三个月以内可退款、一年以内可退款等）后。
				} else if (trade_status.equals("TRADE_SUCCESS")) {
					// 判断该笔订单是否在商户网站中已经做过处理
					// 如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
					// 如果有做过处理，不执行商户的业务程序
					// ——请根据您的业务逻辑来编写程序（以上代码仅作参考）——
					/*
					 * 支付记录表添加数据
					 */
					PaymentRecord pr = new PaymentRecord();
					// String flag = shop_order_no.substring(0, 1);

					OrderDay order = orderDayService.getOrderDay(shop_order_no);
					if (null != order && order.getPayStatus() == 0) {
						if (order.getIsDelayOrder() != null && order.getIsDelayOrder() == 1) {
							DelayOrderDay delayOrderDay = delayOrderDayService
									.getDelayOrderDayByOrderDayNo(order.getOrderNo());
							order.setPayStatus(1);// 支付状态为已支付
							order.setAppointmentReturnTime(delayOrderDay.getFinishTime());
							order.setInsurance(
									ECCalculateUtils.add(order.getInsurance(), delayOrderDay.getInsurance()));
							order.setCarRentalAmount(ECCalculateUtils.add(order.getCarRentalAmount(),
									delayOrderDay.getCarRentalAmount()));
							if (order.getRegardlessFranchise() != null) {
								order.setRegardlessFranchise(ECCalculateUtils.add(order.getRegardlessFranchise(),
										delayOrderDay.getRegardlessFranchise()));
							} else {
								order.setRegardlessFranchise(delayOrderDay.getRegardlessFranchise());
							}

							if (order.getAmountPaid() != null) {
								order.setAmountPaid(ECNumberUtils.roundDoubleWithScale(
										ECCalculateUtils.add(order.getAmountPaid(), total_amount), 2));
							} else {
								order.setAmountPaid(
										ECNumberUtils.roundDoubleWithScale(ECCalculateUtils.add(0.0, total_amount), 2));
							}

							order.setOrderDuration(order.getOrderDuration() + delayOrderDay.getReplenishDays());
							order.setPayableAmount(ECCalculateUtils.add(order.getPayableAmount(),
									delayOrderDay.getTotalReplenishAmount()));
							order.setOrderAmount(ECCalculateUtils.add(order.getOrderAmount(),
									delayOrderDay.getTotalReplenishAmount()));
							order.setIsDelayOrder(2);// 续租支付成功
							orderDayService.updateOrderDay(order, null);

							// 续租订单信息修改
							DelayOrderDay delayOrderDayForUpdate = new DelayOrderDay();
							delayOrderDayForUpdate.setDelayOrderId(delayOrderDay.getDelayOrderId());
							delayOrderDayForUpdate.setPayStatus(1);// 支付状态为已支付
							delayOrderDayForUpdate.setPaymentMethod(1);// 支付方式（1.支付宝，2.微信）
							delayOrderDayForUpdate.setPaymentFlowNo(part_trade_flow_no);// 支付流水号（微信支付订单号）
							// 内部支付流水号
							delayOrderDayForUpdate.setPartTradeFlowNo(trade_no); //
							delayOrderDayForUpdate.setPaymentTime(paidTime);
							ResultInfo<DelayOrderDay> res = delayOrderDayService
									.updateDelayOrderDay(delayOrderDayForUpdate, null);
							if ("1".equals(res.getCode())) {
								upateDelayOrderInventory(order, delayOrderDay);
							}
						} else {
							order.setPayStatus(1);// 已支付
							order.setPaymentMethod(1);// 支付方式（1.支付宝，2.微信）
							order.setPaymentFlowNo(trade_no);// 支付流水号（支付宝交易流水号）
							order.setPartTradeFlowNo(part_trade_flow_no); //
							// 内部支付流水号
							order.setPaymentTime(paidTime);
							order.setAmountPaid(total_amount);
							orderDayService.updateOrderDay(order, null);

							String couponNo = AllCouponHolder.getInstance().getCoupon(order.getMemberNo());
							// 使用优惠券进行订单抵扣
							if (!"".equals(couponNo)) {
								// 修改此优惠券状态
								Coupon cp = new Coupon();
								cp.setCouponNo(couponNo);
								cp.setUsedStatus(1);
								cp.setUsedTime(order.getAppointmentTakeTime());
								cp.setBizObjNo(order.getOrderNo());
								cp.setBizObjType(1);
								ResultInfo<Coupon> res = couponService.updateCoupon(cp, null);
								if ("1".equals(res.getCode())) {
									AllCouponHolder.getInstance().deleteCoupon(order.getMemberNo());
								}
							}
							setCarInventory(order);
						}

						pr.setBizObjType(2);// 订单
						pr.setBizObjNo(order.getOrderNo());
						pr.setPayableAmount(ECNumberUtils.roundDoubleWithScale(order.getPayableAmount(), 2));
						pr.setPaidAmount(total_amount);
						pr.setPaidTime(order.getPaymentTime());
						pr.setPayStatus(1);
						pr.setPayType(1);
						pr.setPayFlowNo(trade_no);
						pr.setPartTradeFlowNo(part_trade_flow_no); // 内部支付流水号
						// 设置买家支付宝账户号
						if (null != buyerId) {
							pr.setBuyerId(buyerId);
						}
						paymentRecordService.addPaymentRecord(pr, null);
						// 支付成功后，修改车辆库存
					} else {
						log.info("重复请求");
						return;
					}

					log.info("the transtion end,result success");

					// out.println("success"); //请不要修改或删除
					response.setContentType("text/html;charset=UTF-8");
					response.setCharacterEncoding("UTF-8");
					response.setStatus(HttpServletResponse.SC_OK);
					PrintWriter out = response.getWriter();
					out.println("success");
					out.flush();
					out.close();
				}

				// 注意：
				//////////////////////////////////////////////////////////////////////////////////////////
			} else {// 验证失败
				log.error("transtion error,because the sign fail");
				System.out.println("fail");
			}
		} else {
			if (AlipayNotify.verify(params)) {// 验证成功
				//////////////////////////////////////////////////////////////////////////////////////////
				// 请在这里加上商户的业务逻辑程序代码

				// ——请根据您的业务逻辑来编写程序（以下代码仅作参考）——
				log.info("pay sign success,begin transition");
				log.info("the trade_status is " + trade_status);
				if (trade_status.equals("TRADE_FINISHED")) {
					// 判断该笔订单是否在商户网站中已经做过处理
					// 如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
					// 如果有做过处理，不执行商户的业务程序
					// 该种交易状态只在两种情况下出现
					// ——请根据您的业务逻辑来编写程序（以上代码仅作参考）——
					PaymentRecord pRecord = paymentRecordService.getPaymentFlowNoById(trade_no);
					if (pRecord == null) {
						/*
						 * 支付记录表添加数据
						 */
						PaymentRecord pr = new PaymentRecord();

						// flag 表示支付的业务类型， 代表订单D， Y 代表押金，T 代表套餐
						String flag = shop_order_no.substring(0, 1);

						log.info("the trade_type is order");
						OrderDay order = orderDayService.getOrderDay(shop_order_no);
						if (order.getIsDelayOrder() != null && order.getIsDelayOrder() == 1) {
							DelayOrderDay delayOrderDay = delayOrderDayService
									.getDelayOrderDayByOrderDayNo(order.getOrderNo());
							order.setPayStatus(1);// 支付状态为已支付
							order.setAppointmentReturnTime(delayOrderDay.getFinishTime());
							order.setInsurance(
									ECCalculateUtils.add(order.getInsurance(), delayOrderDay.getInsurance()));
							order.setCarRentalAmount(ECCalculateUtils.add(order.getCarRentalAmount(),
									delayOrderDay.getCarRentalAmount()));
							if (order.getRegardlessFranchise() != null) {
								order.setRegardlessFranchise(ECCalculateUtils.add(order.getRegardlessFranchise(),
										delayOrderDay.getRegardlessFranchise()));
							} else {
								order.setRegardlessFranchise(delayOrderDay.getRegardlessFranchise());
							}
							order.setAmountPaid(ECNumberUtils.roundDoubleWithScale(
									ECCalculateUtils.add(order.getAmountPaid(), total_amount), 2));
							order.setOrderDuration(order.getOrderDuration() + delayOrderDay.getReplenishDays());
							order.setPayableAmount(ECCalculateUtils.add(order.getPayableAmount(),
									delayOrderDay.getTotalReplenishAmount()));
							order.setOrderAmount(ECCalculateUtils.add(order.getOrderAmount(),
									delayOrderDay.getTotalReplenishAmount()));
							order.setIsDelayOrder(2);// 续租支付成功
							orderDayService.updateOrderDay(order, null);

							// 续租订单信息修改
							DelayOrderDay delayOrderDayForUpdate = new DelayOrderDay();
							delayOrderDayForUpdate.setDelayOrderId(delayOrderDay.getDelayOrderId());
							delayOrderDayForUpdate.setPayStatus(1);// 支付状态为已支付
							delayOrderDayForUpdate.setPaymentMethod(1);// 支付方式（1.支付宝，2.微信）
							delayOrderDayForUpdate.setPaymentFlowNo(part_trade_flow_no);// 支付流水号（微信支付订单号）
							// 内部支付流水号
							delayOrderDayForUpdate.setPartTradeFlowNo(trade_no); //
							delayOrderDayForUpdate.setPaymentTime(paidTime);
							ResultInfo<DelayOrderDay> res = delayOrderDayService
									.updateDelayOrderDay(delayOrderDayForUpdate, null);
							if ("1".equals(res.getCode())) {
								upateDelayOrderInventory(order, delayOrderDay);
							}
						} else {
							order.setPayStatus(1);// 已支付
							order.setPaymentMethod(1);// 支付方式（1.支付宝，2.微信）
							order.setPaymentFlowNo(trade_no);// 支付流水号（支付宝交易流水号）
							order.setPartTradeFlowNo(part_trade_flow_no); // 内部支付流水号
							order.setPaymentTime(paidTime);
							order.setAmountPaid(total_amount);
							orderDayService.updateOrderDay(order, null);

							String couponNo = AllCouponHolder.getInstance().getCoupon(order.getMemberNo());
							// 使用优惠券进行订单抵扣
							if (!"".equals(couponNo)) {
								// 修改此优惠券状态
								Coupon cp = new Coupon();
								cp.setCouponNo(couponNo);
								cp.setUsedStatus(1);
								cp.setUsedTime(order.getAppointmentTakeTime());
								cp.setBizObjNo(order.getOrderNo());
								cp.setBizObjType(1);
								ResultInfo<Coupon> res = couponService.updateCoupon(cp, null);
								if ("1".equals(res.getCode())) {
									AllCouponHolder.getInstance().deleteCoupon(order.getMemberNo());
								}
							}
							setCarInventory(order);
						}
						pr.setBizObjType(2);// 订单
						pr.setBizObjNo(order.getOrderNo());
						pr.setPayableAmount(ECNumberUtils.roundDoubleWithScale(order.getPayableAmount(), 2));
						pr.setPaidAmount(total_amount);
						pr.setPaidTime(order.getPaymentTime());

						pr.setPayStatus(1);
						pr.setPayType(1);
						pr.setPayFlowNo(trade_no);
						pr.setPartTradeFlowNo(part_trade_flow_no); // 内部支付流水号
						paymentRecordService.addPaymentRecord(pr, null);
					}

					// out.println("success"); //请不要修改或删除
					response.setContentType("text/html;charset=UTF-8");
					response.setCharacterEncoding("UTF-8");
					response.setStatus(HttpServletResponse.SC_OK);
					PrintWriter out = response.getWriter();
					out.println("success");
					out.flush();
					out.close();
					// System.out.println("success");
					// 1、开通了普通即时到账，买家付款成功后。
					// 2、开通了高级即时到账，从该笔交易成功时间算起，过了签约时的可退款时限（如：三个月以内可退款、一年以内可退款等）后。
				} else if (trade_status.equals("TRADE_SUCCESS")) {
					// 判断该笔订单是否在商户网站中已经做过处理
					// 如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
					// 如果有做过处理，不执行商户的业务程序
					// ——请根据您的业务逻辑来编写程序（以上代码仅作参考）——
					/*
					 * 支付记录表添加数据
					 */
					PaymentRecord pr = new PaymentRecord();
					// String flag = shop_order_no.substring(0, 1);

					OrderDay order = orderDayService.getOrderDay(shop_order_no);
					if (null != order && order.getPayStatus() == 0) {
						if (order.getIsDelayOrder() != null && order.getIsDelayOrder() == 1) {
							DelayOrderDay delayOrderDay = delayOrderDayService
									.getDelayOrderDayByOrderDayNo(order.getOrderNo());
							order.setPayStatus(1);// 支付状态为已支付
							order.setAppointmentReturnTime(delayOrderDay.getFinishTime());
							order.setInsurance(
									ECCalculateUtils.add(order.getInsurance(), delayOrderDay.getInsurance()));
							order.setCarRentalAmount(ECCalculateUtils.add(order.getCarRentalAmount(),
									delayOrderDay.getCarRentalAmount()));
							if (order.getRegardlessFranchise() != null) {
								order.setRegardlessFranchise(ECCalculateUtils.add(order.getRegardlessFranchise(),
										delayOrderDay.getRegardlessFranchise()));
							} else {
								order.setRegardlessFranchise(delayOrderDay.getRegardlessFranchise());
							}
							order.setAmountPaid(ECNumberUtils.roundDoubleWithScale(
									ECCalculateUtils.add(order.getAmountPaid(), total_amount), 2));
							order.setOrderDuration(order.getOrderDuration() + delayOrderDay.getReplenishDays());
							order.setPayableAmount(ECCalculateUtils.add(order.getPayableAmount(),
									delayOrderDay.getTotalReplenishAmount()));
							order.setOrderAmount(ECCalculateUtils.add(order.getOrderAmount(),
									delayOrderDay.getTotalReplenishAmount()));
							order.setIsDelayOrder(2);// 续租支付成功
							orderDayService.updateOrderDay(order, null);

							// 续租订单信息修改
							DelayOrderDay delayOrderDayForUpdate = new DelayOrderDay();
							delayOrderDayForUpdate.setDelayOrderId(delayOrderDay.getDelayOrderId());
							delayOrderDayForUpdate.setPayStatus(1);// 支付状态为已支付
							delayOrderDayForUpdate.setPaymentMethod(1);// 支付方式（1.支付宝，2.微信）
							delayOrderDayForUpdate.setPaymentFlowNo(part_trade_flow_no);// 支付流水号（微信支付订单号）
							// 内部支付流水号
							delayOrderDayForUpdate.setPartTradeFlowNo(trade_no); //
							delayOrderDayForUpdate.setPaymentTime(paidTime);
							ResultInfo<DelayOrderDay> res = delayOrderDayService
									.updateDelayOrderDay(delayOrderDayForUpdate, null);
							if ("1".equals(res.getCode())) {
								upateDelayOrderInventory(order, delayOrderDay);
							}
						} else {
							order.setPayStatus(1);// 已支付
							order.setPaymentMethod(1);// 支付方式（1.支付宝，2.微信）
							order.setPaymentFlowNo(trade_no);// 支付流水号（支付宝交易流水号）
							order.setPartTradeFlowNo(part_trade_flow_no); //
							// 内部支付流水号
							order.setPaymentTime(paidTime);
							order.setAmountPaid(total_amount);
							orderDayService.updateOrderDay(order, null);

							String couponNo = AllCouponHolder.getInstance().getCoupon(order.getMemberNo());
							// 使用优惠券进行订单抵扣
							if (!"".equals(couponNo)) {
								// 修改此优惠券状态
								Coupon cp = new Coupon();
								cp.setCouponNo(couponNo);
								cp.setUsedStatus(1);
								cp.setUsedTime(order.getAppointmentTakeTime());
								cp.setBizObjNo(order.getOrderNo());
								cp.setBizObjType(1);
								ResultInfo<Coupon> res = couponService.updateCoupon(cp, null);
								if ("1".equals(res.getCode())) {
									AllCouponHolder.getInstance().deleteCoupon(order.getMemberNo());
								}
							}
							setCarInventory(order);
						}

						pr.setBizObjType(2);// 订单
						pr.setBizObjNo(order.getOrderNo());
						pr.setPayableAmount(ECNumberUtils.roundDoubleWithScale(order.getPayableAmount(), 2));
						pr.setPaidAmount(total_amount);
						pr.setPaidTime(order.getPaymentTime());
						pr.setPayStatus(1);
						pr.setPayType(1);
						pr.setPayFlowNo(trade_no);
						pr.setPartTradeFlowNo(part_trade_flow_no); // 内部支付流水号
						// 设置买家支付宝账户号
						if (null != buyerId) {
							pr.setBuyerId(buyerId);
						}
						paymentRecordService.addPaymentRecord(pr, null);
						// 支付成功后，修改车辆库存
					} else {
						log.info("重复请求");
						return;
					}

					log.info("the transtion end,result success");

					// out.println("success"); //请不要修改或删除
					response.setContentType("text/html;charset=UTF-8");
					response.setCharacterEncoding("UTF-8");
					response.setStatus(HttpServletResponse.SC_OK);
					PrintWriter out = response.getWriter();
					out.println("success");
					out.flush();
					out.close();
				}

				// 注意：
				//////////////////////////////////////////////////////////////////////////////////////////
			} else {// 验证失败
				log.error("transtion error,because the sign fail");
				System.out.println("fail");
			}
		}

	}

	private String getWxAuthOpenid(String memberNo) {
		if (cacheUtil.getObject("wxauth_" + memberNo) != null) {
			Map<String, String> authMap = (Map<String, String>) cacheUtil.getObject("wxauth_" + memberNo);
			return authMap.get("openid");
		}
		return "";
	}

	/**
	 * 支付成功后，修改车辆库存
	 * 
	 * @param order
	 */
	private void setCarInventory(OrderDay order) {
		// 支付完成后修改库存
		CarInventory carInventory = carInventoryService.getCarInventoryByCarModelIdAndCity(order.getCarModelId(),
				order.getCityId());
		CarInventory carInventoryForUpdate = new CarInventory();
		carInventoryForUpdate.setCarInventoryId(carInventory.getCarInventoryId());
		// 可用库存数量
		int availableInventory = carInventory.getAvailableInventory() == null ? 0
				: carInventory.getAvailableInventory().intValue();
		// 已租借数量
		int reserveQuantity = carInventory.getLeasedQuantity() == null ? 0
				: carInventory.getLeasedQuantity().intValue();
		carInventoryForUpdate.setAvailableInventory(availableInventory - 1);
		carInventoryForUpdate.setLeasedQuantity(reserveQuantity + 1);
		// 修改车辆库存表
		ResultInfo<CarInventory> result = carInventoryService.updateCarInventory(carInventoryForUpdate, null);
		if ("1".equals(result.getCode())) {
			log.info("修改车辆库存表完成");
			CarInventoryDate cidQ = new CarInventoryDate();
			cidQ.setCarInventoryId(carInventory.getCarInventoryId());
			Query q = new Query(cidQ);
			// 全部车辆库存日历
			List<CarInventoryDate> inventoryDateList = carInventoryDateService.getCarInventoryDateList(q);
			// 起始时间全部日期
			List<String> allDates = ECDateUtils.collectLocalDates(
					ECDateUtils.formatDate(order.getAppointmentTakeTime(), "yyyy-MM-dd"),
					ECDateUtils.formatDate(order.getAppointmentReturnTime(), "yyyy-MM-dd"));
			// 处理要设置的自定义日期
			if (order.getOrderDuration() < allDates.size()) {
				allDates.remove(allDates.size() - 1);
			}
			for (String dateStr : allDates) {
				// 全部日期
				Date date = ECDateUtils.parseDate(dateStr);
				// 自定义日期
				Date flagInventoryDate = null;
				for (CarInventoryDate carInventoryDate : inventoryDateList) {
					Date inventoryDate = ECDateUtils.parseDate(carInventoryDate.getInventoryDate());
					if (date.compareTo(inventoryDate) == 0) {
						CarInventoryDate carInventoryDateForUpdate = new CarInventoryDate();
						carInventoryDateForUpdate.setInventoryDateId(carInventoryDate.getInventoryDateId());
						carInventoryDateForUpdate.setCarInventoryId(carInventoryDate.getCarInventoryId());
						carInventoryDateForUpdate.setCarModelId(carInventoryDate.getCarModelId());
						carInventoryDateForUpdate
								.setAvailableInventory(carInventoryDate.getAvailableInventory().intValue() - 1);
						carInventoryDateForUpdate
								.setLeasedQuantity(carInventoryDate.getLeasedQuantity().intValue() + 1);
						carInventoryDateService.updateCarInventoryDate(carInventoryDateForUpdate, null);
						flagInventoryDate = inventoryDate;
						break;
					}
				}
				// 库存日期未添加对应日期库存记录
				if (flagInventoryDate == null) {
					CarInventoryDate carInventoryDateForUpdate = new CarInventoryDate();
					carInventoryDateForUpdate.setCarInventoryId(carInventory.getCarInventoryId());
					carInventoryDateForUpdate.setCarModelId(carInventory.getCarModelId());
					// 库存总数
					int inventoryTotalDate = carInventory.getInventoryTotal();
					// 已预订数量
					int reserveQuantityDate = 0;
					// 已租借数量
					int leasedQuantityDate = 0;
					// 可用库存总数
					int availableInventoryDate = inventoryTotalDate;
					carInventoryDateForUpdate.setInventoryTotal(inventoryTotalDate);
					carInventoryDateForUpdate.setInventoryDate(dateStr);
					carInventoryDateForUpdate.setAvailableInventory(availableInventoryDate - 1);
					carInventoryDateForUpdate.setReserveQuantity(reserveQuantityDate);
					carInventoryDateForUpdate.setLeasedQuantity(leasedQuantityDate + 1);
					carInventoryDateService.addCarInventoryDate(carInventoryDateForUpdate, null);
				}
			}
			log.info("修改车辆库存日历表完成");
		}
	}

	/**
	 * 续租库存修改
	 * 
	 * @param order
	 * @param delayOrderDay
	 * @throws ParseException
	 */
	private void upateDelayOrderInventory(OrderDay order, DelayOrderDay delayOrderDay) throws ParseException {
		List<String> allDates = ECDateUtils.collectLocalDates(
				ECDateUtils.formatDate(order.getAppointmentTakeTime(), "yyyy-MM-dd"),
				ECDateUtils.formatDate(order.getAppointmentReturnTime(), "yyyy-MM-dd"));
		Date startTime = delayOrderDay.getStartTime();
		startTime = ECDateUtils.formatDateToDateNew(startTime);
		Date finishTime = ECDateUtils.formatDateToDateNew(delayOrderDay.getFinishTime());
		// 处理要设置的自定义日期
		if (order.getOrderDuration() == allDates.size()) {
			int totalHours = ECDateUtils.differHours(delayOrderDay.getStartTime(), delayOrderDay.getFinishTime())
					.intValue();
			int leftHours = totalHours % 24;
			if (leftHours == 0) {
				startTime = ECDateUtils.getDateAfter(startTime, 1);
			}
		}
		if (order.getOrderDuration() > allDates.size()) {
			startTime = ECDateUtils.getDateAfter(startTime, 1);
		}
		if (order.getOrderDuration() < allDates.size()) {
			allDates.remove(allDates.size() - 1);
			finishTime = ECDateUtils.getDateBefore(finishTime, 1);
		}
		CarInventory carInventory = carInventoryService.getCarInventoryByCarModelIdAndCity(order.getCarModelId(),
				order.getCityId());
		CarInventoryDate cidQ = new CarInventoryDate();
		cidQ.setCarInventoryId(carInventory.getCarInventoryId());
		cidQ.setInventoryDateStart(startTime);
		cidQ.setInventoryDateEnd(delayOrderDay.getFinishTime());
		Query q = new Query(cidQ);
		// 全部车辆库存日历
		List<CarInventoryDate> inventoryDateList = carInventoryDateService.getCarInventoryDateList(q);
		for (String dateStr : allDates) {
			// 全部日期
			Date date = ECDateUtils.parseDate(dateStr);
			// 自定义日期
			Date flagInventoryDate = null;
			for (CarInventoryDate carInventoryDate : inventoryDateList) {
				Date inventoryDate = ECDateUtils.parseDate(carInventoryDate.getInventoryDate());
				if (date.compareTo(inventoryDate) == 0) {
					if (date.compareTo(startTime) >= 0) {
						CarInventoryDate carInventoryDateForUpdate = new CarInventoryDate();
						carInventoryDateForUpdate.setInventoryDateId(carInventoryDate.getInventoryDateId());
						carInventoryDateForUpdate.setCarInventoryId(carInventoryDate.getCarInventoryId());
						carInventoryDateForUpdate.setCarModelId(carInventoryDate.getCarModelId());
						carInventoryDateForUpdate
								.setAvailableInventory(carInventoryDate.getAvailableInventory().intValue() - 1);
						carInventoryDateForUpdate
								.setLeasedQuantity(carInventoryDate.getLeasedQuantity().intValue() + 1);
						carInventoryDateService.updateCarInventoryDate(carInventoryDateForUpdate, null);

						MerchantInventoryDate queryInventoryDate = new MerchantInventoryDate();
						queryInventoryDate.setInventoryDate(dateStr);
						queryInventoryDate.setCarModelId(order.getCarModelId());
						queryInventoryDate.setMerchantId(order.getMerchantId());
						List<MerchantInventoryDate> merchantInventoryDateList = merchantInventoryDateService
								.getMerchantInventoryDateList(new Query(queryInventoryDate));
						if (merchantInventoryDateList.size() > 0) {
							// 修改存在的数据。
							merchantInventoryDateDao
									.addLeasedQuantity(merchantInventoryDateList.get(0).getInventoryDateId());
						}
					}
					flagInventoryDate = inventoryDate;
					break;
				}
			}
			// 库存日期未添加对应日期库存记录
			if (flagInventoryDate == null && date.compareTo(startTime) >= 0) {
				CarInventoryDate carInventoryDateForUpdate = new CarInventoryDate();
				carInventoryDateForUpdate.setCarInventoryId(carInventory.getCarInventoryId());
				carInventoryDateForUpdate.setCarModelId(carInventory.getCarModelId());
				// 库存总数
				int inventoryTotalDate = carInventory.getInventoryTotal();
				// 已预订数量
				int reserveQuantityDate = 0;
				// 已租借数量
				int leasedQuantityDate = 0;
				// 可用库存总数
				int availableInventoryDate = inventoryTotalDate;
				carInventoryDateForUpdate.setInventoryTotal(inventoryTotalDate);
				carInventoryDateForUpdate.setInventoryDate(dateStr);
				carInventoryDateForUpdate.setAvailableInventory(availableInventoryDate - 1);
				carInventoryDateForUpdate.setReserveQuantity(reserveQuantityDate);
				carInventoryDateForUpdate.setLeasedQuantity(leasedQuantityDate + 1);
				carInventoryDateService.addCarInventoryDate(carInventoryDateForUpdate, null);

				List<MerchantInventory> inventorys = merchantInventoryDao.getInventoryByMerchantAndCarModelAndCityId(
						order.getMerchantId(), order.getCarModelId(), order.getCityId());
				// 増加
				MerchantInventoryDate merchantInventoryDate = new MerchantInventoryDate();
				merchantInventoryDate.setInventoryDateId(merchantInventoryDateService.generatePK());
				merchantInventoryDate.setCarModelId(order.getCarModelId());
				merchantInventoryDate.setMerchantId(order.getMerchantId());
				merchantInventoryDate.setMerchantId(order.getMerchantId());
				merchantInventoryDate.setMerInventoryId(inventorys.get(0).getMerInventoryId());
				merchantInventoryDate.setInventoryTotal(inventorys.get(0).getInventoryDay());
				merchantInventoryDate.setLeasedQuantity(1);
				merchantInventoryDate.setAvailableInventory(inventorys.get(0).getInventoryDay() - 1);
				merchantInventoryDate.setInventoryDate(dateStr);
				merchantInventoryDate.setCreateTime(new Date());
				merchantInventoryDate.setUpdateTime(new Date());
				merchantInventoryDateDao.add(merchantInventoryDate);
			}
		}
		log.info("修改车辆库存日历表完成");
		log.info("修改商家库存");
	}
}
