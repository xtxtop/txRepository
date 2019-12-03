package cn.com.shopec.mapi.common;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdom.JDOMException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
import cn.com.shopec.core.finace.model.PaymentRecord;
import cn.com.shopec.core.finace.service.PaymentRecordService;
import cn.com.shopec.core.ml.model.CAccountBalance;
import cn.com.shopec.core.ml.model.CAccountRecord;
import cn.com.shopec.core.ml.service.CAccountBalanceService;
import cn.com.shopec.core.ml.service.CAccountRecordService;
import cn.com.shopec.core.mlorder.model.ChargeOrder;
import cn.com.shopec.core.mlorder.model.LockOrder;
import cn.com.shopec.core.mlorder.service.ChargeOrderService;
import cn.com.shopec.core.mlorder.service.LockOrderService;
import cn.com.shopec.core.order.common.OrderConstant;
import cn.com.shopec.core.system.dao.SysParamDao;
import cn.com.shopec.core.system.model.SysParam;
import cn.com.shopec.core.system.service.SysParamService;

@Component
public class RechargePayUtil {

	private static final Log log = LogFactory.getLog(RechargePayUtil.class);

	@Autowired
	private CommonCacheUtil cacheUtil;

	@Autowired
	private SysParamService sysParamService;

	@Autowired
	private ChargeOrderService chargeOrderService;

	@Autowired
	private LockOrderService lockOrderService;

	@Autowired
	private SysParamDao sysParamDao;

	@Autowired
	private static RechargePayUtil rechargePayUtil;

	@Resource
	private CAccountRecordService caccountRecordService;

	@Resource
	private PaymentRecordService paymentRecordService;

	@Resource
	private CAccountBalanceService cAccountBalanceService;

	@PostConstruct
	public void init() {
		rechargePayUtil = this;
		rechargePayUtil.cacheUtil = this.cacheUtil;
		rechargePayUtil.chargeOrderService = this.chargeOrderService;
		rechargePayUtil.lockOrderService = this.lockOrderService;
		rechargePayUtil.lockOrderService = this.lockOrderService;
		rechargePayUtil.sysParamDao = this.sysParamDao;
		rechargePayUtil.caccountRecordService = this.caccountRecordService;
		rechargePayUtil.paymentRecordService = this.paymentRecordService;
		rechargePayUtil.cAccountBalanceService = this.cAccountBalanceService;
	}

	/**
	 * 微信支付回调方法
	 */
	public void wxUpdateOrder(HttpServletRequest request, HttpServletResponse response, Operator operator) {
		log.info("----------------我的钱包微信充值支付回调开始------------");
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
					// String time_end = (String) postdata.get("time_end");
					// 支付结果 业务结果
					String trade_state = (String) postdata.get("result_code");

					// 微信用户openId
					String openId = (String) postdata.get("openid");

					String trade_type = (String) postdata.get("trade_type");

					/*
					 * 支付记录表添加数据
					 */
					PaymentRecord pr = new PaymentRecord();
					log.info("----------trade_state的结果是：------------" + trade_state);
					// 判断签名及结果
					if ("SUCCESS".equals(trade_state)) {
						// String flag = trade_no.substring(0, 1);
						// 更新充值记录表
						CAccountRecord record = caccountRecordService.getCAccountRecord(trade_no);

						if (null != record) {
							Integer flag = record.getDealType();
							String memberNo = record.getMemberNo();

							if (new Integer(1).equals(flag)) {

								if (new Integer(0).equals(record.getPayStatus())) {
									record.setPayStatus(1);
									record.setPayType(0);// 微信支付方式
									record.setPayflowNo(part_trade_flow_no);
									record.setTrnasactionAmount(totalFee);
									if (null != record.getOriginalAmount()) {
										record.setBalance(ECCalculateUtils
												.round(ECCalculateUtils.add(record.getOriginalAmount(), totalFee), 2));
									} else {
										record.setBalance(totalFee);
									}
									log.info("---------更新充电充值记录-----------");
									caccountRecordService.updateCAccountRecord(record, operator);
								} else {
									log.info("--------订单状态不为待支付--------");
									return;
								}

								CAccountBalance search = new CAccountBalance();
								search.setMemberNo(memberNo);
								List<CAccountBalance> lst = cAccountBalanceService
										.getCAccountBalanceList(new Query(search));
								if (lst != null && lst.size() > 0) {

									CAccountBalance balance = lst.get(0);
									Double money = balance.getChargingBalance();
									if (null != money) {// 余额为空时
										// Double mm = money + totalFee;
										Double mm = ECCalculateUtils.round(ECCalculateUtils.add(money, totalFee), 2);
										balance.setChargingBalance(mm);
									} else {
										balance.setChargingBalance(totalFee);
									}
									log.info("-------------更新会员余额记录-----------------");
									cAccountBalanceService.updateCAccountBalance(balance, operator);
								}

								pr.setBizObjType(1);//
								pr.setBizObjNo(record.getId());// 订单编号
								pr.setPayableAmount(
										ECNumberUtils.roundDoubleWithScale(record.getTrnasactionAmount(), 2));
							} else if (new Integer(2).equals(flag)) {

								if (new Integer(0).equals(record.getPayStatus())) {
									record.setPayStatus(1);
									record.setPayType(0);// 微信支付方式
									record.setPayflowNo(part_trade_flow_no);
									record.setTrnasactionAmount(totalFee);
									if (null != record.getOriginalAmount()) {
										Double balance = ECCalculateUtils
												.round(ECCalculateUtils.add(record.getOriginalAmount(), totalFee), 2);
										record.setBalance(balance);
									} else {
										record.setBalance(totalFee);
									}
									log.info("----------更新停车充值记录------------");
									caccountRecordService.updateCAccountRecord(record, operator);
								} else {
									log.info("----------停车订单状态不为待支付-----------");
									return;
								}

								CAccountBalance search = new CAccountBalance();
								search.setMemberNo(memberNo);
								List<CAccountBalance> lst = cAccountBalanceService
										.getCAccountBalanceList(new Query(search));
								if (lst != null && lst.size() > 0) {

									CAccountBalance balance = lst.get(0);
									Double money = balance.getStopBalance();
									if (null != money) {
										// Double mm = money + totalFee;
										Double mm = ECCalculateUtils.round(ECCalculateUtils.add(money, totalFee), 2);
										balance.setStopBalance(mm);
									} else {
										balance.setStopBalance(totalFee);
									}
									log.info("---------更新会员停车余额记录------------");
									cAccountBalanceService.updateCAccountBalance(balance, operator);
								}

								pr.setBizObjType(2);//
								pr.setBizObjNo(record.getId());// 订单编号
								pr.setPayableAmount(
										ECNumberUtils.roundDoubleWithScale(record.getTrnasactionAmount(), 2));
							}
						} else {
							log.info("----------没有获取到订单-----------");
							return;
						}

						pr.setPaidAmount(ECNumberUtils.roundDoubleWithScale(totalFee, 2));
						pr.setPayStatus(1);
						pr.setPayType(2);
						if ("JSAPI".equals(trade_type)) {
							pr.setPayType(3);
						}
						pr.setPayFlowNo(transaction_id); // 外部支付流水号
						pr.setPartTradeFlowNo(part_trade_flow_no); // 内部支付流水号
						pr.setPaidTime(new Date());
						// 设置支付账户标识
						pr.setBuyerId(openId);
						paymentRecordService.addPaymentRecord(pr, null);

						resHandler.sendToCFT("<xml><return_code><![CDATA[SUCCESS]]></return_code></xml>");
						String returnxml = "<xml>" + "   <return_code><![CDATA[SUCCESS]]></return_code>"
								+ "   <return_msg><![CDATA[OK]]></return_msg>" + "</xml>";

						response.setContentType("text/xml");
						response.getWriter().write(returnxml);
						response.getWriter().flush();

					} else {// sha1签名失败
						resHandler.sendToCFT("<xml><return_code><![CDATA[FAIL]]></return_code></xml>");
						String returnxml = "<xml>" + "   <return_code><![CDATA[SUCCESS]]></return_code>"
								+ "   <return_msg><![CDATA[OK]]></return_msg>" + "</xml>";

						response.setContentType("text/xml");
						response.getWriter().write(returnxml);
						response.getWriter().flush();

					}
				} else {// MD5签名失败
					resHandler.sendToCFT("<xml><return_code><![CDATA[FAIL]]></return_code></xml>");
					String returnxml = "<xml>" + "   <return_code><![CDATA[SUCCESS]]></return_code>"
							+ "   <return_msg><![CDATA[OK]]></return_msg>" + "</xml>";

					response.setContentType("text/xml");
					response.getWriter().write(returnxml);
					response.getWriter().flush();

				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		log.info("-------------我的钱包微信充值支付回调结束-------------");
	}

	/**
	 * 调用微信查询订单支付结果的接口
	 */
	public ResultInfo<String> wxGetOrderPayResult(HttpServletRequest request, HttpServletResponse response,
			String orderNo) {
		ResultInfo<String> resultInfo = new ResultInfo<String>();
		// 生成随机字符串
		String currTime = TenpayUtil.getCurrTime();
		String strTime = currTime.substring(8, currTime.length());// 8位日期
		String strRandom = TenpayUtil.buildRandom(4) + "";// 四位随机数
		String strReq = strTime + strRandom;// 10位序列号,可以自行调整。
		String nonce_str = strReq;
		String flag = orderNo.substring(0, 1);// 截取订单，作为类型的判断

		SortedMap<String, String> packageParams = new TreeMap<String, String>();
		packageParams.put("appid", WxpayConfig.appID);
		packageParams.put("mch_id", WxpayConfig.mchID);
		if (flag.equals("C")) {// 充电订单
			ChargeOrder order = chargeOrderService.getChargeOrder(orderNo);
			if (order != null && order.getPaymentFlowNo() != null) {
				packageParams.put("out_trade_no", order.getPaymentFlowNo());
			}
		} else if (flag.equals("B")) {// 地锁、停车订单
			LockOrder dorder = lockOrderService.getLockOrder(orderNo);
			if (dorder != null && dorder.getPaymentFlowNo() != null) {
				packageParams.put("out_trade_no", dorder.getPaymentFlowNo());
			}
		}

		packageParams.put("appid", WxpayConfig.appID);
		packageParams.put("mch_id", WxpayConfig.mchID);

		packageParams.put("out_trade_no", "");
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
			e.printStackTrace();
			resultInfo.setCode(Constant.FAIL);
		} catch (IOException e) {
			e.printStackTrace();
			resultInfo.setCode(Constant.FAIL);
		}
		return resultInfo;
	}

	/**
	 * 
	 * @param request
	 * @param response
	 * @param memberNo
	 * @param tag
	 * @param rechargeAccount
	 * @return
	 */
	public ResultInfo<SortedMap<String, Object>> getCodeUrl(HttpServletRequest request, HttpServletResponse response,
			String memberNo, Integer tag, String dealType, String orderNo, String rechargeAccount) {

		ResultInfo<SortedMap<String, Object>> resultInfo = new ResultInfo<SortedMap<String, Object>>();
		// 总金额以分为单位，不带小数点
		int total_fee = 0;
		// 这里notify_url是 支付完成后微信发给该链接信息，可以判断会员是否支付成功，改变订单状态等。处理完调用的路径
		String notify_url = WxpayConfig.notify_url_w;
		// 商品描述根据情况修改
		String body = "";
		// 商户订单号
		String out_trade_no = "";

		total_fee = Double.valueOf(rechargeAccount).intValue();// 充值金额

		if ("1".equals(dealType)) {// 充电类型
			CAccountRecord order = caccountRecordService.getCAccountRecord(orderNo);
			if (order.getPayStatus().equals(1)) {
				resultInfo.setCode(OrderConstant.alreday_pay);
				resultInfo.setMsg(OrderConstant.alreday_pay_msg);
				return resultInfo;
			}

			// 商品描述根据情况修改(数据字典中配置)
			SysParam sys = sysParamDao.getByParamKey("APP_NAME_1");
			if (sys != null) {
				body = sys.getParamValue() + "-支付";
			} else {
				body = "猛龙出行-订单支付";
			}

			out_trade_no = orderNo;
		} else if ("2".equals(dealType)) {// 停车类型
			CAccountRecord order = caccountRecordService.getCAccountRecord(orderNo);
			if (order.getPayStatus().equals(1)) {
				resultInfo.setCode(OrderConstant.alreday_pay);
				resultInfo.setMsg(OrderConstant.alreday_pay_msg);
				return resultInfo;
			}
			// 商品描述根据情况修改(数据字典中配置)
			SysParam sys = sysParamDao.getByParamKey("APP_NAME_1");
			if (sys != null) {
				body = sys.getParamValue() + "-支付";
			} else {
				body = "猛龙出行-订单支付";
			}

			out_trade_no = orderNo;
		}

		// 调用统一支付接口https://api.mch.weixin.qq.com/pay/unifiedorder
		// 请求参数准备
		String appid = WxpayConfig.appID;// 微信开放平台审核通过的应用APPID
		String mch_id = WxpayConfig.mchID;// 微信支付分配的商户号
		// String device_info = "";// 设备号 非必输

		// 生成随机字符串
		String currTime = TenpayUtil.getCurrTime();
		String strTime = currTime.substring(8, currTime.length());// 8位日期
		String strRandom = TenpayUtil.buildRandom(4) + "";// 四位随机数
		String strReq = strTime + strRandom;// 10位序列号,可以自行调整。
		String nonce_str = strReq;

		// 附加数据
		// String attach = userId;

		// 订单生成的机器 IP
		String spbill_create_ip = "";
		// 订 单 生 成 时 间 非必输
		// String time_start = "";
		// 订单失效时间 非必输
		// String time_expire = "";
		// 商品标记 非必输
		// String goods_tag = "";

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
		// out_trade_no = out_trade_no +
		// ECRandomUtils.getRandomAlphamericStr(8);
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
				resultInfo.setMsg("成功返回数据");
				resultInfo.setCode(Constant.SECCUESS);
			} else {
				resultInfo.setCode(Constant.FAIL);
				resultInfo.setMsg("返回数据失败");
			}
		} catch (JDOMException e) {
			e.printStackTrace();
			resultInfo.setCode(Constant.FAIL);
		} catch (IOException e) {
			e.printStackTrace();
			resultInfo.setCode(Constant.FAIL);
		}
		return resultInfo;
	}

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

	// H5生成签名订单
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

	private String getWxAuthOpenid(String memberNo) {
		if (cacheUtil.getObject("wxauth_" + memberNo) != null) {
			Map<String, String> authMap = (Map<String, String>) cacheUtil.getObject("wxauth_" + memberNo);

			return authMap.get("openid");
		}
		return "";
	}

	public ResultInfo<String> alipayGetOrderStr(HttpServletRequest request, HttpServletResponse response,
			String memberNo, String dealType, String orderNo, String rechargeAccount) {
		ResultInfo<String> resultInfo = new ResultInfo<>();
		SortedMap<String, Object> map = new TreeMap<String, Object>();
		String subject = "猛龙出行支付宝支付";// (商品名称)
		String out_trade_no = "";// 订单号
		String total_amount = "";// 订单总金额，单位为元，精确到小数点后两位
		String body = "";
		total_amount = rechargeAccount;// 充值金额

		CAccountRecord order = caccountRecordService.getCAccountRecord(orderNo);
		if (order.getPayStatus().equals(1)) {
			resultInfo.setCode(OrderConstant.alreday_pay);
			resultInfo.setMsg(OrderConstant.alreday_pay_msg);
			return resultInfo;
		}

		if ("1".equals(dealType)) {// 充电类型

			// 商品描述根据情况修改(数据字典中配置)
			SysParam sys = sysParamDao.getByParamKey("APP_NAME_1");
			if (sys != null) {
				body = sys.getParamValue() + "-支付";
			} else {
				body = "猛龙出行-订单支付";
			}

			out_trade_no = orderNo;
		} else if ("2".equals(dealType)) {// 停车类型

			// 商品描述根据情况修改(数据字典中配置)
			SysParam sys = sysParamDao.getByParamKey("APP_NAME_1");
			if (sys != null) {
				body = sys.getParamValue() + "-支付";
			} else {
				body = "猛龙出行-订单支付";
			}

			out_trade_no = orderNo;
		}

		// 商户交易单号，之前业务为将业务单号作为交易单号传递到支付宝平台，后发现此种做法有问题会引发二次唤起支付失败或者订单失效无法支付的问题，解决方式为每次唤起支付的时候都在支付记录中添加新纪录，新增内部支付流水单号，格式为业务单号_8位随机数
		// out_trade_no = out_trade_no + "_" +
		// ECRandomUtils.getRandomAlphamericStr(8);

		SysParam spmAlipay = sysParamService.getByParamKey("aliPaySignType");
		if (spmAlipay != null && "2".equals(spmAlipay.getParamValue())) {
			// 实例化客户端
			AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do",
					AlipayConfig.appId, AlipayConfig.rsa_private, "json", "UTF-8", AlipayConfig.rsa_public, "RSA2");
			// 实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.trade.app.pay
			AlipayTradeAppPayRequest re = new AlipayTradeAppPayRequest();
			// SDK已经封装掉了公共参数，这里只需要传入业务参数。以下方法为sdk的model入参方式(model和biz_content同时存在的情况下取biz_content)。
			AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
			model.setBody(body);
			model.setSubject(subject);
			model.setOutTradeNo(out_trade_no);
			model.setTimeoutExpress("30m");
			model.setTotalAmount(total_amount);
			model.setProductCode("QUICK_MSECURITY_PAY");
			re.setBizModel(model);
			re.setNotifyUrl(AlipayConfig.notify_url_a);
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
					AlipayConfig.notify_url_a, total_amount, subject);
			String orderParam = OrderInfoUtil2_0.buildOrderParam(authInfoMap);
			String sign = OrderInfoUtil2_0.getSign(authInfoMap, AlipayConfig.rsa_private);
			final String orderInfo = orderParam + "&" + sign;

			map.put("orderParam", orderParam);
			map.put("sign", sign);
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
	public void alipayUpdateOrder(HttpServletRequest request, HttpServletResponse response) throws Exception {
		log.info("-----------我的钱包支付宝充值回调开始--------------");
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
		// 商户订单号 即 附带随机码的平台内部支付流水号
		String part_trade_flow_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"), "UTF-8");
		// 商户订单号
		// String shop_order_no = part_trade_flow_no.substring(0,
		// part_trade_flow_no.lastIndexOf("_"));// 交易状态
		// 支付宝交易流水号
		// String trade_no = new
		// String(request.getParameter("trade_no").getBytes("ISO-8859-1"),
		// "UTF-8");
		// 交易状态
		String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"), "UTF-8");
		// 交易状态
		String totalAmount = new String(request.getParameter("total_amount").getBytes("ISO-8859-1"), "UTF-8");
		// 购买时间
		// String notify_time = new
		// String(request.getParameter("notify_time").getBytes("ISO-8859-1"),
		// "UTF-8");
		// 订单号
		String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"), "utf-8");
		// 买家支付时间
		String gmt_payment = request.getParameter("gmt_payment") != null
				? new String(request.getParameter("gmt_payment").getBytes("ISO-8859-1"), "UTF-8") : null;

		Date paidTime = null;
		if (gmt_payment != null && !gmt_payment.equals("")) { // 使用gmt_payment作为支付时间
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			paidTime = sdf.parse(gmt_payment);
		}
		paidTime = paidTime == null ? new Date() : paidTime;

		SysParam spmAlipay = sysParamService.getByParamKey("aliPaySignType");
		if (spmAlipay != null && "2".equals(spmAlipay.getParamValue())) {
			if (AlipaySignature.rsaCheckV1(params, AlipayConfig.rsa_public, "UTF-8", "RSA2")) {// 验证成功
				if (trade_status.equals("TRADE_SUCCESS")) {

					// flag 表示支付的业务类型， 1、代表充电2、 代表停车
					CAccountRecord record = caccountRecordService.getCAccountRecord(out_trade_no);

					if (null != record) {
						Integer flag = record.getDealType();
						String memberNo = record.getMemberNo();
						if (new Integer(1).equals(flag)) {// 充电
							// 判断订单支付状态
							if (new Integer(0).equals(record.getPayStatus())) {
								record.setPayStatus(1);
								record.setPayType(1);// 支付宝支付方式
								record.setPayflowNo(part_trade_flow_no);
								record.setTrnasactionAmount(Double.valueOf(totalAmount));
								if (null != record.getOriginalAmount()) {
									record.setBalance(ECCalculateUtils.round(ECCalculateUtils
											.add(record.getOriginalAmount(), Double.valueOf(totalAmount)), 2));
								} else {
									record.setBalance(Double.valueOf(totalAmount));
								}

								// 更新充值记录表
								caccountRecordService.updateCAccountRecord(record, null);
							} else {
								log.info("-----------充电支付宝充值订单状态不为待支付-------------");
								return;
							}

							CAccountBalance search = new CAccountBalance();
							search.setMemberNo(memberNo);
							List<CAccountBalance> lst = cAccountBalanceService
									.getCAccountBalanceList(new Query(search));
							if (lst != null && lst.size() > 0) {
								CAccountBalance balance = lst.get(0);
								Double money = balance.getChargingBalance();
								// 余额加上交易金额
								if (null != money) {
									// Double mm = money +
									// Double.valueOf(totalAmount);
									Double mm = ECCalculateUtils
											.round(ECCalculateUtils.add(money, Double.valueOf(totalAmount)), 2);
									balance.setChargingBalance(mm);
								} else {
									balance.setChargingBalance(Double.valueOf(totalAmount));
								}
								// 更新会员余额记录表
								cAccountBalanceService.updateCAccountBalance(balance, null);
							}

						} else if (new Integer(2).equals(flag)) {// 停车
							// 判断支付状态
							if (new Integer(0).equals(record.getPayStatus())) {
								record.setPayStatus(1);
								record.setPayType(1);// 支付宝支付方式
								record.setPayflowNo(part_trade_flow_no);
								record.setTrnasactionAmount(Double.valueOf(totalAmount));
								if (null != record.getOriginalAmount()) {
									record.setBalance(ECCalculateUtils.round(ECCalculateUtils
											.add(record.getOriginalAmount(), Double.valueOf(totalAmount)), 2));
								} else {
									record.setBalance(Double.valueOf(totalAmount));
								}

								// 更新充值记录表
								caccountRecordService.updateCAccountRecord(record, null);
							} else {
								log.info("------------停车支付宝充值订单状态不为待支付-------------");
								return;
							}

							CAccountBalance search = new CAccountBalance();
							search.setMemberNo(memberNo);
							List<CAccountBalance> lst = cAccountBalanceService
									.getCAccountBalanceList(new Query(search));
							if (lst != null && lst.size() > 0) {

								CAccountBalance balance = lst.get(0);
								Double money = balance.getStopBalance();
								if (null != money) {

									Double mm = ECCalculateUtils
											.round(ECCalculateUtils.add(money, Double.valueOf(totalAmount)), 2);
									balance.setStopBalance(mm);
								} else {
									balance.setStopBalance(Double.valueOf(totalAmount));
								}

								cAccountBalanceService.updateCAccountBalance(balance, null);
							}
						}
					} else {
						log.info("----------未获取到订单--------------");
						return;
					}

					response.setContentType("text/html;charset=UTF-8");
					response.setCharacterEncoding("UTF-8");
					response.setStatus(HttpServletResponse.SC_OK);
					PrintWriter out = response.getWriter();
					out.println("success");
					out.flush();
					out.close();
					return;
				}

			} else {// 验证失败
				log.info("---------------支付宝回调过程中验证失败----------------");
				System.out.println("验证失败：fail");
				return;
			}
			// 支付宝sign RSA
		} else {
			if (AlipayNotify.verify(params)) {// 验证成功
				if (trade_status.equals("TRADE_SUCCESS")) {

					// flag 表示支付的业务类型， 1、代表充电2、 代表停车
					CAccountRecord record = caccountRecordService.getCAccountRecord(out_trade_no);
					if (null != record) {
						Integer flag = record.getDealType();
						String memberNo = record.getMemberNo();
						if (new Integer(1).equals(flag)) {// 充电

							if (new Integer(0).equals(record.getPayStatus())) {
								record.setPayStatus(1);
								record.setPayType(1);// 支付宝支付方式
								record.setPayflowNo(part_trade_flow_no);
								record.setTrnasactionAmount(Double.valueOf(totalAmount));
								if (null != record.getOriginalAmount()) {
									record.setBalance(ECCalculateUtils.round(ECCalculateUtils
											.add(record.getOriginalAmount(), Double.valueOf(totalAmount)), 2));
								} else {
									record.setBalance(Double.valueOf(totalAmount));
								}

								// 更新充值记录表
								caccountRecordService.updateCAccountRecord(record, null);
							} else {
								log.info("---------充电支付宝充值订单状态不为待支付------------");
								return;
							}

							CAccountBalance search = new CAccountBalance();
							search.setMemberNo(memberNo);
							List<CAccountBalance> lst = cAccountBalanceService
									.getCAccountBalanceList(new Query(search));
							if (lst != null && lst.size() > 0) {

								CAccountBalance balance = lst.get(0);
								Double money = balance.getChargingBalance();
								// 余额加上交易金额
								if (null != money) {
									// Double mm = money +
									// Double.valueOf(totalAmount);
									Double mm = ECCalculateUtils
											.round(ECCalculateUtils.add(money, Double.valueOf(totalAmount)), 2);
									balance.setChargingBalance(mm);
								} else {
									balance.setChargingBalance(Double.valueOf(totalAmount));
								}

								cAccountBalanceService.updateCAccountBalance(balance, null);
							}
						} else if (new Integer(2).equals(flag)) {// 停车

							if (new Integer(0).equals(record.getPayStatus())) {
								record.setPayStatus(1);
								record.setPayType(1);// 支付宝支付方式
								record.setPayflowNo(part_trade_flow_no);
								record.setTrnasactionAmount(Double.valueOf(totalAmount));
								if (null != record.getOriginalAmount()) {
									record.setBalance(ECCalculateUtils.round(ECCalculateUtils
											.add(record.getOriginalAmount(), Double.valueOf(totalAmount)), 2));
								} else {
									record.setBalance(Double.valueOf(totalAmount));
								}

								// 更新充值记录表
								caccountRecordService.updateCAccountRecord(record, null);
							} else {
								log.info("--------停车支付宝充值订单状态不为待支付----------");
								return;
							}

							CAccountBalance search = new CAccountBalance();
							search.setMemberNo(memberNo);
							List<CAccountBalance> lst = cAccountBalanceService
									.getCAccountBalanceList(new Query(search));
							if (lst != null && lst.size() > 0) {
								CAccountBalance balance = lst.get(0);
								Double money = balance.getStopBalance();
								// 余额加上交易金额
								if (null != money) {
									Double mm = ECCalculateUtils
											.round(ECCalculateUtils.add(money, Double.valueOf(totalAmount)), 2);
									balance.setStopBalance(mm);
								} else {
									balance.setStopBalance(Double.valueOf(totalAmount));
								}

								cAccountBalanceService.updateCAccountBalance(balance, null);
							}
						}
					} else {
						log.info("--------未获取到订单---------");
						return;
					}

					response.setContentType("text/html;charset=UTF-8");
					response.setCharacterEncoding("UTF-8");
					response.setStatus(HttpServletResponse.SC_OK);
					PrintWriter out = response.getWriter();
					out.println("success");
					out.flush();
					out.close();
					return;
				}
			} else {// 验证失败
				log.info("--------支付宝充值回调验证失败-----------");
				System.out.println("支付宝充值回调验证失败：fail");
				return;
			}
		}
		log.info("--------------我的钱包支付宝充值回调结束----------------");
	}
}
