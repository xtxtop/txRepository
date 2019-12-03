package cn.com.shopec.core.pay.impl;

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
import org.springframework.util.StringUtils;

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
import cn.com.shopec.common.utils.HttpURLRequestUtil;
import cn.com.shopec.common.utils.Uint32;
import cn.com.shopec.common.utils.WxUtils;
import cn.com.shopec.core.car.model.Car;
import cn.com.shopec.core.car.service.CarService;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.finace.common.DepositConstant;
import cn.com.shopec.core.finace.model.Deposit;
import cn.com.shopec.core.finace.model.DepositOrder;
import cn.com.shopec.core.finace.model.PaymentRecord;
import cn.com.shopec.core.finace.service.DepositOrderService;
import cn.com.shopec.core.finace.service.PaymentRecordService;
import cn.com.shopec.core.franchisee.model.Franchisee;
import cn.com.shopec.core.franchisee.model.FranchiseeProfit;
import cn.com.shopec.core.franchisee.service.FranchiseeProfitService;
import cn.com.shopec.core.franchisee.service.FranchiseeService;
import cn.com.shopec.core.marketing.model.Accounts;
import cn.com.shopec.core.marketing.model.PricingPackage;
import cn.com.shopec.core.marketing.service.AccountsService;
import cn.com.shopec.core.marketing.service.PricingPackageService;
import cn.com.shopec.core.member.model.Member;
import cn.com.shopec.core.member.model.MemberLevel;
import cn.com.shopec.core.member.model.MemberPointsRecord;
import cn.com.shopec.core.member.model.MemberPointsRule;
import cn.com.shopec.core.member.service.MemberLevelService;
import cn.com.shopec.core.member.service.MemberPointsRecordService;
import cn.com.shopec.core.member.service.MemberPointsRuleService;
import cn.com.shopec.core.member.service.MemberService;
import cn.com.shopec.core.order.common.OrderConstant;
import cn.com.shopec.core.order.dao.OrderDao;
import cn.com.shopec.core.order.model.Order;
import cn.com.shopec.core.order.model.PricingPackOrder;
import cn.com.shopec.core.order.service.OrderService;
import cn.com.shopec.core.order.service.PricingPackOrderService;
import cn.com.shopec.core.pay.PayService;
import cn.com.shopec.core.resource.model.Park;
import cn.com.shopec.core.resource.service.ParkService;
import cn.com.shopec.core.system.dao.SysParamDao;
import cn.com.shopec.core.system.model.SysParam;
import cn.com.shopec.core.system.service.SysParamService;

/**
 * 支付
 */
@Service
public class PayServiceImpl implements PayService {

	private static final Log log = LogFactory.getLog(PayServiceImpl.class);

	@Resource
	private OrderService orderService;
	@Resource
	private OrderDao orderDao;

	@Resource
	private DepositOrderService depositOrderService;

	@Resource
	private PricingPackOrderService pricingPackOrderService;

	@Resource
	private SysParamService sysParamService;
	@Resource
	private SysParamDao sysParamDao;

	@Resource
	private MemberService memberService;

	@Resource
	private PricingPackageService pricingPackageService;

	@Resource
	private PaymentRecordService paymentRecordService;

	@Resource
	private MemberPointsRuleService memberPointsRuleService;

	@Resource
	private MemberPointsRecordService memberPointsRecordService;

	@Resource
	private MemberLevelService memberLevelService;
	
	@Resource
	private CommonCacheUtil cacheUtil;
	@Resource
	private AccountsService accountsService;
	@Resource
	private CarService carService;
	@Resource
	private ParkService parkService;
	@Resource
	private FranchiseeProfitService franchiseeProfitService;
	@Resource
	private FranchiseeService franchiseeService;
	/**
	 * 微信支付回调方法
	 */
	@Override
	public void wxUpdateOrder(HttpServletRequest request, HttpServletResponse response, Operator operator,String trigger) {
		String out_trade_no = "";
		synchronized (this) {
			try {
				String appkey = WxpayConfig.key;// 商户平台上那个自己生成的KEY
				ResponseHandler resHandler = new ResponseHandler(request, response);
				resHandler.setKey(appkey);
				Map postdata = resHandler.getSmap();
				if (resHandler.isWechatSign()) {// 是否微信签名
					//商户订单号  即  附带随机码的平台内部支付流水号
					String part_trade_flow_no = (String) postdata.get("out_trade_no");
					//支付订单号
					//String trade_no = part_trade_flow_no.substring(0, part_trade_flow_no.length()-8);
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
					
					String trade_type = (String) postdata.get("trade_type");
					/*
					 * 支付记录表添加数据
					 */
					PaymentRecord pr = new PaymentRecord();

					// 判断签名及结果
					if ("SUCCESS".equals(trade_state)) {// D(订单),T(套餐),Y(押金)
						// 即时到账处理业务开始
						String flag = trade_no.substring(0, 1);
						if (flag.equals("D")) {
							Order order = orderService.getOrder(trade_no);
							if (null != order && order.getPayStatus() == 0) {
								order.setPayStatus(1);// 支付状态为已支付
								order.setPaymentMethod(2);// 支付方式（1.支付宝，2.微信）
								if("JSAPI".equals(trade_type)){
									order.setPaymentMethod(3);
								}
								order.setPaymentFlowNo(transaction_id);// 支付流水号（微信支付订单号）
								order.setPartTradeFlowNo(part_trade_flow_no);	//内部支付流水号
								if(time_end != null){
									SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss");  
									String dstr=time_end;  
									Date nodate=sdf.parse(dstr);
									order.setPaymentTime(nodate);
								}else{
									order.setPaymentTime(new Date());
								}
								orderService.updateOrder(order, null);

								//微信 订单支付后 添加加盟商收益数据
								this.addFranchiseeProfit(order);
								
								pr.setBizObjType(2);// 订单
								pr.setBizObjNo(order.getOrderNo());
								pr.setPayableAmount(ECNumberUtils.roundDoubleWithScale(order.getPayableAmount(), 2));
								payOverAddRecordOrder(order);
								//更新会员支付总额
								memberService.updateMemberRealAmount(order.getMemberNo(),ECNumberUtils.roundDoubleWithScale(order.getPayableAmount(), 2));
							} else {
								log.info("重复请求");
								return;
							}

						} else if (flag.equals("Y")) {
							DepositOrder dOrder = depositOrderService.getDepositOrder(trade_no);
							if (null != dOrder && dOrder.getPayStatus() == 0) {
								dOrder.setPayStatus(1);// 已支付
								dOrder.setPaymentMethod(2);// 支付方式（1.支付宝，2.微信）
								if("JSAPI".equals(trade_type)){
									dOrder.setPaymentMethod(3);
								}
								if(time_end != null){
									SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss");  
									String dstr=time_end;  
									Date nodate=sdf.parse(dstr);
									dOrder.setPaymentTime(nodate);
								}else{
									dOrder.setPaymentTime(new Date());
								}
								dOrder.setRemainAmount(
										ECNumberUtils.roundDoubleWithScale(dOrder.getDepositAmount(), 2));
								dOrder.setPaymentFlowNo(transaction_id);
								dOrder.setPartTradeFlowNo(part_trade_flow_no);	//设置内部支付流水号编号
								dOrder.setIsAvailable(1);
								depositOrderService.updateDepositOrder(dOrder, null);
								//押金支付成功后，设置会员表的应支付押金金额为dOrder.getPayableAmount()
								Member member = memberService.getMember(dOrder.getMemberNo());
								if (member!=null) {
									Member memberForUpdate = new Member();
									memberForUpdate.setMemberNo(member.getMemberNo());
									memberForUpdate.setPayableDepositAmount(dOrder.getPayableAmount());
									memberService.updateMember(memberForUpdate, null);
								}
								
								pr.setBizObjType(3);// 押金
								pr.setBizObjNo(dOrder.getDepositOrderNo());
								pr.setPayableAmount(ECNumberUtils.roundDoubleWithScale(dOrder.getPayableAmount(), 2));
								//如果是邀请优惠押金配置
								SysParam ssp=sysParamService.getByParamKey("preferential");
								if(ssp != null && "2".equals(ssp.getParamValue())){
									Member mm=memberService.getMember(dOrder.getMemberNo());
										String url="";
										String url2="";
											 try {
												 url=trigger+"marketingEvent/eventHandler?eventNo=4&memberNo="+mm.getMemberNo();
												HttpURLRequestUtil.doMsgGet(url);
												 if(mm.getRefereeId()!= null && !"".equals(mm.getRefereeId())){
													 url2=trigger+"marketingEvent/eventHandler?eventNo=3&memberNo="+mm.getRefereeId();
													HttpURLRequestUtil.doMsgGet(url2);
												 }
												
												
											} catch (Exception e) {
												e.printStackTrace();
											}
									
								}
							} else {
								log.info("重复请求");
								return;
							}

						} else if (flag.equals("T")) {

							PricingPackOrder order = pricingPackOrderService.getPricingPackOrder(trade_no);
							if (null != order && order.getPayStatus() == 0) {
								order.setPayStatus(1);// 已支付
								order.setPaymentMethod(2);// 支付方式（1.支付宝，2.微信）
								if("JSAPI".equals(trade_type)){
									order.setPaymentMethod(3);
								}
								order.setIsAvailable(1);//可用
								order.setPaymentFlowNo(transaction_id);
								order.setPartTradeFlowNo(part_trade_flow_no);	//设置内部支付流水号编号
								pricingPackOrderService.updatePricingPackOrder(order, null);

								//查询我的余额
								Query qr = new Query();
								PricingPackOrder ppor = new PricingPackOrder();
								ppor.setPayStatus(1);// 已经支付的
								ppor.setIsAvailable(1);// 可用
								ppor.setNowTime(new Date());
								ppor.setMemberNo(order.getMemberNo());
								qr.setQ(ppor);
								List<PricingPackOrder>  pporList = pricingPackOrderService.getPricingPackOrderListBypo(qr);
								Double poa=0.0;
								Double uoa=0.0;
								for (PricingPackOrder pricingPackOrder : pporList) {
										if(pricingPackOrder.getPackOrderAmount() != null ){
											poa=ECCalculateUtils.add(poa,pricingPackOrder.getPackOrderAmount());
										}
										if(pricingPackOrder.getUseredOrderAmount() != null){
											uoa=ECCalculateUtils.add(uoa,pricingPackOrder.getUseredOrderAmount());
										
									}
								}
								if(order.getPackAmount()==null){
									order.setPackAmount(0d);
								}
								//金额套餐购买成功后给记账表添加记录
								Double ye = ECCalculateUtils.sub(poa, uoa);
								Accounts ac = new Accounts();
								ac.setMemberNo(order.getMemberNo());
								ac.setBusinessNo(order.getPackOrderNo());
								ac.setBusinessType(2);
								ac.setAccountType(2);
								ac.setAccountMoney(order.getPackOrderAmount());
								ac.setAccountBeforeMoney(ye);
								
								ac.setAccountOverMoney(ECCalculateUtils.add(ye, ac.getAccountMoney()));
								//查询超额的订单
								Order  odr =orderService.getOrderWaring(order.getMemberNo());
								if(odr != null){
									try {
										odr=orderService.orderTripView(odr).getData()== null ? odr:orderService.orderTripView(odr).getData();
									} catch (ParseException e) {
										e.printStackTrace();
									}
									
									Double amount=ECNumberUtils.roundDoubleWithScale(odr.getOrderAmount(),2);//订单金额
									if(ECCalculateUtils.ge(ye,amount)){//账户 余额  大于 订单总金额   消除警告
										Order o= new  Order();
										o.setOrderNo(odr.getOrderNo());
										o.setWarningOrder(0);
										orderDao.update(o);
									}
										
									
								}
								
								ac.setAccountTime(new Date());
								accountsService.addAccounts(ac, null);
								
								
								pr.setBizObjType(1);// 套餐订单
								pr.setBizObjNo(order.getPackOrderNo());
								pr.setPayableAmount(ECNumberUtils.roundDoubleWithScale(order.getPayableAmount(), 2));
								payOverAddRecordPackageOrder(order);
								//更新会员支付总额
								memberService.updateMemberRealAmount(order.getMemberNo(),ECNumberUtils.roundDoubleWithScale(order.getPayableAmount(), 2));
							} else {
								log.info("重复请求");
								return;
							}
						}
						pr.setPaidAmount(ECNumberUtils.roundDoubleWithScale(totalFee, 2));
						pr.setPayStatus(1);
						pr.setPayType(2);
						if("JSAPI".equals(trade_type)){
							pr.setPayType(3);
						}
						pr.setPayFlowNo(transaction_id);	//外部支付流水号
						pr.setPartTradeFlowNo(part_trade_flow_no);	//内部支付流水号
						pr.setPaidTime(new Date());
						// 设置支付账户标识
						pr.setBuyerId(openId);
						paymentRecordService.addPaymentRecord(pr, null);

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
	// type:1：订单；2：押金；3：套餐tag:0:安卓，1：ios
	public ResultInfo<SortedMap<String, Object>> getCodeUrl(HttpServletRequest request, HttpServletResponse response,
			String payNo, String memberNo, String packageNo, Integer type, Integer tag,String addrRegion) {
		ResultInfo<SortedMap<String, Object>> resultInfo = new ResultInfo<SortedMap<String, Object>>();
		// 总金额以分为单位，不带小数点
		int total_fee = 0;
		// 这里notify_url是 支付完成后微信发给该链接信息，可以判断会员是否支付成功，改变订单状态等。处理完调用的路径
		String notify_url = "";
		// 商品描述根据情况修改
		String body = "";
		// 商户订单号
		String out_trade_no = "";
		if (type.equals(1)) {
			Order order = orderService.getOrder(payNo);
			if (order.getPayStatus().equals(1)) {
				resultInfo.setCode(OrderConstant.alreday_pay);
				resultInfo.setMsg(OrderConstant.alreday_pay_msg);
				return resultInfo;
			}
//			total_fee = (int) (order.getPayableAmount() * 100);
			total_fee = (int)ECCalculateUtils.mul(order.getPayableAmount(), 100);
			notify_url = WxpayConfig.notify_url;
			// 商品描述根据情况修改
			SysParam sys=sysParamDao.getByParamKey("APP_NAME");
			if(sys != null){
				 body = sys.getParamValue()+"-支付";
			}else{
				 body = "猛龙出行-订单支付";
			}
			
			out_trade_no = payNo;
		} else if (type.equals(2)) {
			DepositOrder dOrder = new DepositOrder();
			dOrder.setMemberNo(memberNo);
			/*SysParam sysParam = sysParamService.getByParamKey(DepositConstant.deposit_amount_key);
			Double amount = Double.parseDouble(sysParam.getParamValue());*/
			Double amount = depositOrderService.getMemberPayableDeposit(memberNo, addrRegion);// 单位：元
			// 查询该用户的剩余保证金
			Double syAmount = 0d;
			ResultInfo<Deposit> rDeposit = depositOrderService.getDepositByMemberNo(memberNo,addrRegion);
			if (rDeposit.getCode().equals("1")) {
				syAmount = rDeposit.getData().getAvailableAmount();
				addrRegion = rDeposit.getData().getAddrRegion();
			}
			dOrder.setAddrRegion(addrRegion);
			dOrder.setDepositAmount(ECNumberUtils.roundDoubleWithScale(amount - syAmount, 2));
			dOrder.setPayableAmount(ECNumberUtils.roundDoubleWithScale(amount - syAmount, 2));// 测试
			dOrder.setPayStatus(0);
			dOrder.setRemainAmount(0d);
			dOrder.setDeductedAmount(0d);
			dOrder.setRefundAmount(0d);
			dOrder.setFrozenAmount(0d);
			if (memberNo != null) {
				dOrder = depositOrderService.addDepositOrder(dOrder, null).getData();
			}

//			total_fee = (int) (dOrder.getPayableAmount() * 100);
			total_fee = (int)ECCalculateUtils.mul(dOrder.getPayableAmount(), 100);
			notify_url = WxpayConfig.notify_url;
			SysParam sys=sysParamDao.getByParamKey("APP_NAME");
			if(sys != null){
				 body = sys.getParamValue()+"-押金支付";
			}else{
				 body = "猛龙出行-押金支付";
			}
			
			out_trade_no = dOrder.getDepositOrderNo();
		} else if (type.equals(3)) {
			PricingPackOrder ppOrder = new PricingPackOrder();
			ppOrder.setMemberNo(memberNo);
			if (memberNo != null && !memberNo.equals("")) {
				Member member = memberService.getMember(memberNo);
				if (member != null) {
					ppOrder.setMemberName(member.getMemberName());
					ppOrder.setMobilePhone(member.getMobilePhone());
				}
			}
			ppOrder.setPackageId(packageNo);
			if (packageNo != null && !packageNo.equals("")) {
				PricingPackage pp = pricingPackageService.getPricingPackage(packageNo);
				if (pp != null) {
					ppOrder.setPackageName(pp.getPackageName());
					ppOrder.setPackAmount(pp.getPrice());
					if(pp.getPackAmount() != null){
						ppOrder.setPackOrderAmount(pp.getPackAmount());
					}
					ppOrder.setUseredOrderAmount(0d);
					if(pp.getMinutes() != null){
						ppOrder.setPackMinute(pp.getMinutes());
					}
					ppOrder.setUserdMinute(0);
					ppOrder.setPayableAmount(pp.getPrice());
					ppOrder.setPayStatus(0);
					// 有效期的起止时间（当前时间+套餐的有效天数）
					ppOrder.setVailableTime1(new Date());
					String dateString = "2099-12-31 00:00:00";
					ppOrder.setVailableTime2(ECDateUtils.parseDate(dateString, "yyyy-MM-dd HH:mm:ss"));
					ppOrder.setCreateTime(new Date());
					ppOrder = pricingPackOrderService.addPricingPackOrder(ppOrder, null).getData();
				}else{
					resultInfo.setCode(OrderConstant.disalreday_pay);
					resultInfo.setMsg(OrderConstant.disalreday_pay_msg);
					return resultInfo;
					
				}
			}
//			total_fee = (int) (ppOrder.getPayableAmount() * 100);
			total_fee = (int)ECCalculateUtils.mul(ppOrder.getPayableAmount(), 100);
			notify_url = WxpayConfig.notify_url;
			SysParam sys=sysParamDao.getByParamKey("APP_NAME");
			if(sys != null){
				 body =sys.getParamValue()+"-套餐支付";
			}else{
				 body = "猛龙出行-套餐支付";
			}
			
			out_trade_no = ppOrder.getPackOrderNo();
		}
		SysParam sysp = sysParamService.getByParamKey(OrderConstant.IS_Correct_OrderAmount);
		if (sysp != null && sysp.getParamValue() != null && sysp.getParamValue().equals("0")) {
			total_fee = (int)1;
		}
		System.err.println("应支付总金额total_fee="+total_fee);
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
		
		if(tag.intValue()==10){
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
		
		//商户交易单号，之前业务为将业务单号作为交易单号传递到微信平台，后发现此种做法有问题会引发二次唤起微信支付失败或者订单失效无法支付的问题，解决方式为每次唤起支付的时候都在支付记录中添加新纪录，新增内部支付流水单号，格式为业务单号_8位随机数
		out_trade_no = out_trade_no + "_" + ECRandomUtils.getRandomAlphamericStr(8);
		//out_trade_no = out_trade_no  + ECRandomUtils.getRandomAlphamericStr(8);
		packageParams.put("out_trade_no", out_trade_no);
		// TODO 这里写的金额为1 分    通过系统参数变量来控制支付金额通道- 参数建 IS_Correct_OrderAmount
		packageParams.put("total_fee", "" + total_fee);
		packageParams.put("spbill_create_ip", spbill_create_ip);
		packageParams.put("notify_url", notify_url);
		packageParams.put("trade_type", trade_type);
		if(tag.intValue()==10){
			String openid=getWxAuthOpenid(memberNo);
			packageParams.put("openid", openid);
			System.out.println("pay openid+++++++++++ "+openid);
		}
		
		String sign = PayCommonUtil.createSign("UTF-8", packageParams);// 生成签名
		packageParams.put("sign", sign);
		packageParams.remove("key");// 调用统一下单无需key（商户应用密钥）
		

		String requestXml = PayCommonUtil.getRequestXml(packageParams);// 生成Xml格式的字符串
		String createOrderURL = "https://api.mch.weixin.qq.com/pay/unifiedorder";
		String result = CommonUtil.httpsRequest(createOrderURL, "POST", requestXml);// 以post请求的方式调用统一下单接口
		
		System.out.println("pay request+++++++++++ "+requestXml);
		System.out.println("pay result++++++++++++ "+result);
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
				} else if (tag==10){
					
					resultInfo = getCodeUrlH5(request, response, prepay_id, nonce_str, ""+new Date().getTime()/1000);
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
	
	public ResultInfo<SortedMap<String, Object>> getCodeUrlH5(HttpServletRequest request,
			HttpServletResponse response, String prepay_id, String nonceStr, String timeStamp) {
		ResultInfo<SortedMap<String, Object>> resultInfo = new ResultInfo<SortedMap<String, Object>>();
		// 请求参数准备
		String appid = WxpayConfig.h5_appID;// 微信开放平台审核通过的应用APPID
		String partnerId = WxpayConfig.h5_mchID;// 微信支付分配的商户号

		// 生成签名
		SortedMap<String, Object> packageParams = new TreeMap<String, Object>();
		packageParams.put("appId", appid);
		packageParams.put("timeStamp", timeStamp);
		packageParams.put("nonceStr", nonceStr);
		packageParams.put("package", "prepay_id="+prepay_id);
		//packageParams.put("prepay_id", prepay_id);
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
		if (flag.equals("D")) {
			Order order = orderService.getOrder(orderNo);
			if(order!=null && order.getPartTradeFlowNo()!=null){
				packageParams.put("out_trade_no", order.getPartTradeFlowNo());
			}
		}else if(flag.equals("Y")){
			DepositOrder dorder = depositOrderService.getDepositOrder(orderNo);
			if(dorder!=null && dorder.getPartTradeFlowNo()!=null){
				packageParams.put("out_trade_no", dorder.getPartTradeFlowNo());
			}
		}else if(flag.equals("T")){
			PricingPackOrder porder = pricingPackOrderService.getPricingPackOrder(orderNo);
			if(porder!=null && porder.getPartTradeFlowNo()!=null){
				packageParams.put("out_trade_no", porder.getPartTradeFlowNo());
			}
		}
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
			String memberNo, String packageNo, Integer type,String addrRegion) {
		ResultInfo<String> resultInfo = new ResultInfo<String>();
		String subject = "";// (商品名称)
		String out_trade_no = "";// 订单号
		String total_amount = "";// 订单总金额，单位为元，精确到小数点后两位
		String body="";
		if (type.equals(1)) {// 订单
			Order order = orderService.getOrder(payNo);
			if (order.getPayStatus().equals(1)) {
				resultInfo.setCode(OrderConstant.alreday_pay);
				resultInfo.setMsg(OrderConstant.alreday_pay_msg);
				return resultInfo;
			}
			subject = order.getOrderNo();// (商品名称)
			out_trade_no = order.getOrderNo();// 订单号
			total_amount = order.getPayableAmount().toString();// 订单总金额，单位为元，精确到小数点后两位
			SysParam sys=sysParamDao.getByParamKey("APP_NAME");
			if(sys != null){
				 body = sys.getParamValue()+"-订单支付";
			}else{
				 body = "猛龙出行-订单支付";
			}
		} else if (type.equals(2)) {// 押金
			DepositOrder dOrder = new DepositOrder();
			dOrder.setMemberNo(memberNo);
			/*SysParam sysParam = sysParamService.getByParamKey(DepositConstant.deposit_amount_key);
			Double amount = Double.parseDouble(sysParam.getParamValue());*/
			Double amount = depositOrderService.getMemberPayableDeposit(memberNo, addrRegion);// 单位：元
			// 查询该用户的剩余保证金
			Double syAmount = 0d;
			ResultInfo<Deposit> rDeposit = depositOrderService.getDepositByMemberNo(memberNo,addrRegion);
			if (rDeposit.getCode().equals("1")) {
				syAmount = rDeposit.getData().getAvailableAmount();
				addrRegion = rDeposit.getData().getAddrRegion();
			}
			dOrder.setAddrRegion(addrRegion);
			dOrder.setDepositAmount(ECNumberUtils.roundDoubleWithScale(amount - syAmount, 2));
			dOrder.setPayableAmount(ECNumberUtils.roundDoubleWithScale(amount - syAmount, 2));// 测试
			dOrder.setPayStatus(0);
			dOrder.setRemainAmount(0d);
			dOrder.setDeductedAmount(0d);
			dOrder.setRefundAmount(0d);
			dOrder.setFrozenAmount(0d);

			// 有 member_no 才生成订单
			if (memberNo != null) {
				dOrder = depositOrderService.addDepositOrder(dOrder, null).getData();
			}

			subject = dOrder.getDepositOrderNo();// (商品名称)
			out_trade_no = dOrder.getDepositOrderNo();// 订单号
			total_amount = dOrder.getPayableAmount().toString();// 订单总金额，单位为元，精确到小数点后两位
			SysParam sys=sysParamDao.getByParamKey("APP_NAME");
			if(sys != null){
				 body = sys.getParamValue()+"-押金支付";
			}else{
				 body = "猛龙出行-押金支付";
			}
		} else if (type.equals(3)) {// 套餐
			PricingPackOrder order = new PricingPackOrder();
			order.setMemberNo(memberNo);
			if (memberNo != null && !memberNo.equals("")) {
				Member member = memberService.getMember(memberNo);
				if (member != null) {
					order.setMemberName(member.getMemberName());
					order.setMobilePhone(member.getMobilePhone());
				}
			}
			order.setPackageId(packageNo);
			if (packageNo != null && !packageNo.equals("")) {
				PricingPackage pp = pricingPackageService.getPricingPackage(packageNo);
				if (pp != null) {
					order.setPackageName(pp.getPackageName());
					order.setPackAmount(pp.getPrice());
					if(pp.getPackAmount() != null){
						order.setPackOrderAmount(pp.getPackAmount());
					}
					if(pp.getMinutes() != null){
						order.setPackMinute(pp.getMinutes());
					}
					order.setUseredOrderAmount(0d);
					order.setUserdMinute(0);
					order.setPayableAmount(pp.getPrice());
					order.setPayStatus(0);
					// 有效期的起止时间（当前时间+套餐的有效天数）
					order.setVailableTime1(new Date());
					String dateString = "2099-12-31 00:00:00";
					order.setVailableTime2(ECDateUtils.parseDate(dateString, "yyyy-MM-dd HH:mm:ss"));
					order.setCreateTime(new Date());
					order = pricingPackOrderService.addPricingPackOrder(order, null).getData();
				}else{
					resultInfo.setCode(OrderConstant.disalreday_pay);
					resultInfo.setMsg(OrderConstant.disalreday_pay_msg);
					return resultInfo;
				}
			}
			
			subject = order.getPackOrderNo();// (商品名称)
			out_trade_no = order.getPackOrderNo();// 订单号
			total_amount = order.getPayableAmount().toString();// 订单总金额，单位为元，精确到小数点后两位
			SysParam sys=sysParamDao.getByParamKey("APP_NAME");
			if(sys != null){
				 body = sys.getParamValue()+"-套餐支付";
			}else{
				 body = "猛龙出行-套餐支付";
			}
		}
		SysParam sysp = sysParamService.getByParamKey(OrderConstant.IS_Correct_OrderAmount);
		if (sysp != null && sysp.getParamValue() != null && sysp.getParamValue().equals("0")) {
			total_amount = 0.01 + "";// 订单总金额，单位为元，精确到小数点后两位
		}
		
		//商户交易单号，之前业务为将业务单号作为交易单号传递到支付宝平台，后发现此种做法有问题会引发二次唤起支付失败或者订单失效无法支付的问题，解决方式为每次唤起支付的时候都在支付记录中添加新纪录，新增内部支付流水单号，格式为业务单号_8位随机数
		out_trade_no = out_trade_no + "_" + ECRandomUtils.getRandomAlphamericStr(8);

		SysParam spmAlipay= sysParamService.getByParamKey("aliPaySignType");
		if(spmAlipay != null && "2".equals(spmAlipay.getParamValue())){
			// 实例化客户端
			AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do", AlipayConfig.appId,
					AlipayConfig.rsa_private, "json", "UTF-8", AlipayConfig.rsa_public, "RSA2");
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

		}else{
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
	public void alipayUpdateOrder(HttpServletRequest request, HttpServletResponse response,String trigger) throws Exception {
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
		//商户订单号  即  附带随机码的平台内部支付流水号
		String part_trade_flow_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"), "UTF-8");
		//商户订单号
		String shop_order_no = part_trade_flow_no.substring(0, part_trade_flow_no.lastIndexOf("_"));// 交易状态
		// 支付宝交易流水号
		String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"), "UTF-8");
		// 交易状态
		String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"), "UTF-8");
		// 购买时间
		String notify_time = new String(request.getParameter("notify_time").getBytes("ISO-8859-1"), "UTF-8");

		// 买家支付时间
		String gmt_payment = request.getParameter("gmt_payment") != null
				? new String(request.getParameter("gmt_payment").getBytes("ISO-8859-1"), "UTF-8") : null;

		Date paidTime = null;
		if (gmt_payment != null && !gmt_payment.equals("")) { // 使用gmt_payment作为支付时间
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			paidTime = sdf.parse(gmt_payment);
		}
		paidTime = paidTime == null ? new Date() : paidTime;
		// 买家支付宝账号
		// String buyerEmail = new
		// String(request.getParameter("buyer_email").getBytes("ISO-8859-1"),
		// "UTF-8");
		// 买家支付宝账户号
		String buyerId = new String(request.getParameter("buyer_id").getBytes("ISO-8859-1"), "UTF-8");
		// 获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以上仅供参考)//
		SysParam spmAlipay= sysParamService.getByParamKey("aliPaySignType");
		if(spmAlipay != null && "2".equals(spmAlipay.getParamValue())){
			if (AlipaySignature.rsaCheckV1(params, AlipayConfig.rsa_public, "UTF-8", "RSA2")) {// 验证成功
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
					if (pRecord==null) {
						/*
						 * 支付记录表添加数据
						 */
						PaymentRecord pr = new PaymentRecord();
						
						// flag 表示支付的业务类型，  代表订单D， Y 代表押金，T 代表套餐
						String flag = shop_order_no.substring(0, 1);
						if (flag.equals("D")) {
							log.info("the trade_type is order");
							Order order = orderService.getOrder(shop_order_no);
							order.setPayStatus(1);// 已支付
							order.setPaymentMethod(1);// 支付方式（1.支付宝，2.微信）
							order.setPaymentFlowNo(trade_no);// 支付流水号（支付宝交易流水号）
							order.setPartTradeFlowNo(part_trade_flow_no); //内部支付流水号
							order.setPaymentTime(paidTime);
							orderService.updateOrder(order, null);
							
							//支付宝 订单支付后 添加加盟商收益数据
							this.addFranchiseeProfit(order);
							
							pr.setBizObjType(2);// 订单
							pr.setBizObjNo(order.getOrderNo());
							pr.setPayableAmount(ECNumberUtils.roundDoubleWithScale(order.getPayableAmount(), 2));
							pr.setPaidAmount(ECNumberUtils.roundDoubleWithScale(order.getPayableAmount(), 2));
							pr.setPaidTime(order.getPaymentTime());
							payOverAddRecordOrder(order);
							memberService.updateMemberRealAmount(order.getMemberNo(),ECNumberUtils.roundDoubleWithScale(order.getPayableAmount(), 2));
						} else if (flag.equals("Y")) {
							log.info("the trade_type is dist");
							DepositOrder dOrder = depositOrderService.getDepositOrder(shop_order_no);
							dOrder.setPayStatus(1);// 已支付
							dOrder.setPaymentMethod(1);// 支付方式（1.支付宝，2.微信）
							dOrder.setPaymentFlowNo(trade_no);// 支付流水号（支付宝交易流水号）
							dOrder.setPartTradeFlowNo(part_trade_flow_no); //内部支付流水号
							dOrder.setPaymentTime(paidTime);
							dOrder.setRemainAmount(ECNumberUtils.roundDoubleWithScale(dOrder.getDepositAmount(), 2));
							dOrder.setIsAvailable(1);
							depositOrderService.updateDepositOrder(dOrder, null);
							
							//押金支付成功后，设置会员表的应支付押金金额为dOrder.getPayableAmount()
							Member member = memberService.getMember(dOrder.getMemberNo());
							if (member!=null) {
								Member memberForUpdate = new Member();
								memberForUpdate.setMemberNo(member.getMemberNo());
								memberForUpdate.setPayableDepositAmount(dOrder.getPayableAmount());
								memberService.updateMember(memberForUpdate, null);
							}
							
							pr.setBizObjType(3);// 押金
							pr.setBizObjNo(dOrder.getDepositOrderNo());
							pr.setPayableAmount(ECNumberUtils.roundDoubleWithScale(dOrder.getPayableAmount(), 2));
							pr.setPaidAmount(ECNumberUtils.roundDoubleWithScale(dOrder.getPayableAmount(), 2));
							pr.setPaidTime(dOrder.getPaymentTime());
						} else if (flag.equals("T")) {
							log.info("the trade_type is prop");
							PricingPackOrder order = pricingPackOrderService.getPricingPackOrder(shop_order_no);
							order.setPayStatus(1);// 已支付
							order.setPaymentMethod(1);// 支付方式（1.支付宝，2.微信）
							order.setPaymentFlowNo(trade_no);// 支付流水号（支付宝交易流水号）
							order.setPartTradeFlowNo(part_trade_flow_no); //内部支付流水号
							pricingPackOrderService.updatePricingPackOrder(order, null);
							
							pr.setBizObjType(1);// 套餐订单
							pr.setBizObjNo(order.getPackOrderNo());
							pr.setPayableAmount(ECNumberUtils.roundDoubleWithScale(order.getPayableAmount(), 2));
							pr.setPaidAmount(ECNumberUtils.roundDoubleWithScale(order.getPayableAmount(), 2));
							pr.setPaidTime(paidTime);
							
							payOverAddRecordPackageOrder(order);
							memberService.updateMemberRealAmount(order.getMemberNo(),ECNumberUtils.roundDoubleWithScale(order.getPayableAmount(), 2));
						}
						// pr.setPaidAmount(totalFee);
						pr.setPayStatus(1);
						pr.setPayType(1);
						pr.setPayFlowNo(trade_no);
						pr.setPartTradeFlowNo(part_trade_flow_no); //内部支付流水号
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
					String flag = shop_order_no.substring(0, 1);
					if (flag.equals("D")) {
						Order order = orderService.getOrder(shop_order_no);
						if (null != order && order.getPayStatus() == 0) {
							order.setPayStatus(1);// 已支付
							order.setPaymentMethod(1);// 支付方式（1.支付宝，2.微信）
							order.setPaymentFlowNo(trade_no);// 支付流水号（支付宝交易流水号）
							order.setPartTradeFlowNo(part_trade_flow_no); //内部支付流水号
							order.setPaymentTime(paidTime);
							
							orderService.updateOrder(order, null);
							
							//支付宝 订单支付后 添加加盟商收益数据
							this.addFranchiseeProfit(order);

							pr.setBizObjType(2);// 订单
							pr.setBizObjNo(order.getOrderNo());
							pr.setPayableAmount(ECNumberUtils.roundDoubleWithScale(order.getPayableAmount(), 2));
							pr.setPaidAmount(ECNumberUtils.roundDoubleWithScale(order.getPayableAmount(), 2));
							pr.setPaidTime(order.getPaymentTime());
							// 添加积分
							payOverAddRecordOrder(order);
							memberService.updateMemberRealAmount(order.getMemberNo(),ECNumberUtils.roundDoubleWithScale(order.getPayableAmount(), 2));

						} else {
							log.info("重复请求");
							return;
						}
					} else if (flag.equals("Y")) {
						DepositOrder dOrder = depositOrderService.getDepositOrder(shop_order_no);
						if (null != dOrder && dOrder.getPayStatus() == 0) {
							dOrder.setPayStatus(1);// 已支付
							dOrder.setPaymentMethod(1);// 支付方式（1.支付宝，2.微信）
							dOrder.setPaymentFlowNo(trade_no);// 支付流水号（支付宝交易流水号）
							dOrder.setPartTradeFlowNo(part_trade_flow_no); //内部支付流水号
							dOrder.setPaymentTime(new Date());
							
							dOrder.setRemainAmount(ECNumberUtils.roundDoubleWithScale(dOrder.getDepositAmount(), 2));
							dOrder.setIsAvailable(1);
							depositOrderService.updateDepositOrder(dOrder, null);

							//押金支付成功后，设置会员表的已支付押金金额为dOrder.getPayableAmount()
							Member member = memberService.getMember(dOrder.getMemberNo());
							if (member!=null) {
								Member memberForUpdate = new Member();
								memberForUpdate.setMemberNo(member.getMemberNo());
								memberForUpdate.setPayableDepositAmount(dOrder.getPayableAmount());
								memberService.updateMember(memberForUpdate, null);
							}
							
							pr.setBizObjType(3);// 押金
							pr.setBizObjNo(dOrder.getDepositOrderNo());
							pr.setPayableAmount(ECNumberUtils.roundDoubleWithScale(dOrder.getPayableAmount(), 2));
							pr.setPaidAmount(ECNumberUtils.roundDoubleWithScale(dOrder.getPayableAmount(), 2));
							pr.setPaidTime(dOrder.getPaymentTime());
							//如果是邀请优惠押金配置
							SysParam ssp=sysParamService.getByParamKey("preferential");
							if(ssp != null && "2".equals(ssp.getParamValue())){
								Member mm=memberService.getMember(dOrder.getMemberNo());
									String url="";
									String url2="";
										 try {
											 url=trigger+"marketingEvent/eventHandler?eventNo=4&memberNo="+mm.getMemberNo();
											HttpURLRequestUtil.doMsgGet(url);
											 if(mm.getRefereeId()!= null && !"".equals(mm.getRefereeId())){
												 url2=trigger+"marketingEvent/eventHandler?eventNo=3&memberNo="+mm.getRefereeId();
												HttpURLRequestUtil.doMsgGet(url2);
											 }
											
											
										} catch (Exception e) {
											e.printStackTrace();
										}
								
							}
						} else {
							log.info("重复请求");
							return;
						}
					} else if (flag.equals("T")) {
						PricingPackOrder order = pricingPackOrderService.getPricingPackOrder(shop_order_no);
						if (null != order && order.getPayStatus() == 0) {
							order.setPayStatus(1);// 已支付
							order.setPaymentMethod(1);// 支付方式（1.支付宝，2.微信）
							order.setIsAvailable(1);//可用
							order.setPaymentFlowNo(trade_no);// 支付流水号（支付宝交易流水号）
							order.setPartTradeFlowNo(part_trade_flow_no); //内部支付流水号
							pricingPackOrderService.updatePricingPackOrder(order, null);

							//查询我的余额
							Query qr = new Query();
							PricingPackOrder ppor = new PricingPackOrder();
							ppor.setPayStatus(1);// 已经支付的
							ppor.setIsAvailable(1);// 可用
							ppor.setNowTime(new Date());
							ppor.setMemberNo(order.getMemberNo());
							qr.setQ(ppor);
							List<PricingPackOrder>  pporList = pricingPackOrderService.getPricingPackOrderListBypo(qr);
							Double poa=0.0;
							Double uoa=0.0;
							for (PricingPackOrder pricingPackOrder : pporList) {
									if(pricingPackOrder.getPackOrderAmount() != null ){
										poa=ECCalculateUtils.add(poa,pricingPackOrder.getPackOrderAmount());
									}
									if(pricingPackOrder.getUseredOrderAmount() != null){
										uoa=ECCalculateUtils.add(uoa,pricingPackOrder.getUseredOrderAmount());
									}
								
							}
							if(order.getPackAmount()==null){
								order.setPackAmount(0d);
							}
							//金额套餐购买成功后给记账表添加记录
							Double ye = ECCalculateUtils.sub(poa, uoa);
							
							
							
							Accounts ac = new Accounts();
							ac.setMemberNo(order.getMemberNo());
							ac.setBusinessNo(order.getPackOrderNo());
							ac.setBusinessType(2);
							ac.setAccountType(2);
							ac.setAccountMoney(order.getPackOrderAmount());
							ac.setAccountBeforeMoney(ye);
							ac.setAccountOverMoney(ECCalculateUtils.add(ye, ac.getAccountMoney()));
							//查询超额的订单
							Order  odr =orderService.getOrderWaring(order.getMemberNo());
							if(odr != null){
								try {
									odr=orderService.orderTripView(odr).getData()== null ? odr:orderService.orderTripView(odr).getData();
								} catch (ParseException e) {
									e.printStackTrace();
								}
								
								Double amount=ECNumberUtils.roundDoubleWithScale(odr.getOrderAmount(),2);//订单金额
								if(ECCalculateUtils.ge(ye,amount)){//账户 余额  大于 订单总金额   消除警告
									Order o= new  Order();
									o.setOrderNo(odr.getOrderNo());
									o.setWarningOrder(0);
									orderDao.update(o);
								}
							}
							
							ac.setAccountTime(new Date());
							accountsService.addAccounts(ac, null);
							
							pr.setBizObjType(1);// 套餐订单
							pr.setBizObjNo(order.getPackOrderNo());
							pr.setPayableAmount(ECNumberUtils.roundDoubleWithScale(order.getPayableAmount(), 2));
							pr.setPaidAmount(ECNumberUtils.roundDoubleWithScale(order.getPayableAmount(), 2));
							pr.setPaidTime(paidTime);
							payOverAddRecordPackageOrder(order);	
							// 更新真实消费额
							memberService.updateMemberRealAmount(order.getMemberNo(),ECNumberUtils.roundDoubleWithScale(order.getPayableAmount(), 2));
						} else {
							log.info("重复请求");
							return;
						}

					}
					pr.setPayStatus(1);
					pr.setPayType(1);
					pr.setPayFlowNo(trade_no);
					pr.setPartTradeFlowNo(part_trade_flow_no); //内部支付流水号
					// 设置买家支付宝账户号
					if (null != buyerId) {
						pr.setBuyerId(buyerId);
					}
					paymentRecordService.addPaymentRecord(pr, null);
					log.info("the transtion end,result success");

					// out.println("success"); //请不要修改或删除
					response.setContentType("text/html;charset=UTF-8");
					response.setCharacterEncoding("UTF-8");
					response.setStatus(HttpServletResponse.SC_OK);
					PrintWriter out = response.getWriter();
					out.println("success");
					out.flush();
					out.close();
					// System.out.println("success");
					// 注意：
					// 该种交易状态只在一种情况下出现——开通了高级即时到账，买家付款成功后。
				}

				// 注意：
			} else {// 验证失败
				log.error("transtion error,because the sign fail");
				System.out.println("fail");
			}
			//支付宝sign RSA
		}else{
			if (AlipayNotify.verify(params)) {// 验证成功
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

					// flag 表示支付的业务类型，  代表订单D， Y 代表押金，T 代表套餐
					String flag = shop_order_no.substring(0, 1);
					if (flag.equals("D")) {
						log.info("the trade_type is order");
						Order order = orderService.getOrder(shop_order_no);
						order.setPayStatus(1);// 已支付
						order.setPaymentMethod(1);// 支付方式（1.支付宝，2.微信）
						order.setPaymentFlowNo(trade_no);// 支付流水号（支付宝交易流水号）
						order.setPartTradeFlowNo(part_trade_flow_no); //内部支付流水号
						order.setPaymentTime(paidTime);
						orderService.updateOrder(order, null);

						//支付宝 订单支付后 添加加盟商收益数据
						this.addFranchiseeProfit(order);
						
						pr.setBizObjType(2);// 订单
						pr.setBizObjNo(order.getOrderNo());
						pr.setPayableAmount(ECNumberUtils.roundDoubleWithScale(order.getPayableAmount(), 2));
						pr.setPaidAmount(ECNumberUtils.roundDoubleWithScale(order.getPayableAmount(), 2));
						pr.setPaidTime(order.getPaymentTime());
						payOverAddRecordOrder(order);
						memberService.updateMemberRealAmount(order.getMemberNo(),ECNumberUtils.roundDoubleWithScale(order.getPayableAmount(), 2));
					} else if (flag.equals("Y")) {
						log.info("the trade_type is dist");
						DepositOrder dOrder = depositOrderService.getDepositOrder(shop_order_no);
						dOrder.setPayStatus(1);// 已支付
						dOrder.setPaymentMethod(1);// 支付方式（1.支付宝，2.微信）
						dOrder.setPaymentFlowNo(trade_no);// 支付流水号（支付宝交易流水号）
						dOrder.setPartTradeFlowNo(part_trade_flow_no); //内部支付流水号
						dOrder.setPaymentTime(paidTime);
						dOrder.setRemainAmount(ECNumberUtils.roundDoubleWithScale(dOrder.getDepositAmount(), 2));
						dOrder.setIsAvailable(1);
						depositOrderService.updateDepositOrder(dOrder, null);
						
						//押金支付成功后，设置会员表的应支付押金金额为dOrder.getPayableAmount()
						Member member = memberService.getMember(dOrder.getMemberNo());
						if (member!=null) {
							Member memberForUpdate = new Member();
							memberForUpdate.setMemberNo(member.getMemberNo());
							memberForUpdate.setPayableDepositAmount(dOrder.getPayableAmount());
							memberService.updateMember(memberForUpdate, null);
						}
						
						pr.setBizObjType(3);// 押金
						pr.setBizObjNo(dOrder.getDepositOrderNo());
						pr.setPayableAmount(ECNumberUtils.roundDoubleWithScale(dOrder.getPayableAmount(), 2));
						pr.setPaidAmount(ECNumberUtils.roundDoubleWithScale(dOrder.getPayableAmount(), 2));
						pr.setPaidTime(dOrder.getPaymentTime());
					} else if (flag.equals("T")) {
						log.info("the trade_type is prop");
						PricingPackOrder order = pricingPackOrderService.getPricingPackOrder(shop_order_no);
						order.setPayStatus(1);// 已支付
						order.setPaymentMethod(1);// 支付方式（1.支付宝，2.微信）
						order.setPaymentFlowNo(trade_no);// 支付流水号（支付宝交易流水号）
						order.setPartTradeFlowNo(part_trade_flow_no); //内部支付流水号
						pricingPackOrderService.updatePricingPackOrder(order, null);

						pr.setBizObjType(1);// 套餐订单
						pr.setBizObjNo(order.getPackOrderNo());
						pr.setPayableAmount(ECNumberUtils.roundDoubleWithScale(order.getPayableAmount(), 2));
						pr.setPaidAmount(ECNumberUtils.roundDoubleWithScale(order.getPayableAmount(), 2));
						pr.setPaidTime(paidTime);
						
						payOverAddRecordPackageOrder(order);
						memberService.updateMemberRealAmount(order.getMemberNo(),ECNumberUtils.roundDoubleWithScale(order.getPayableAmount(), 2));
					}
					// pr.setPaidAmount(totalFee);
					pr.setPayStatus(1);
					pr.setPayType(1);
					pr.setPayFlowNo(trade_no);
					pr.setPartTradeFlowNo(part_trade_flow_no); //内部支付流水号
					paymentRecordService.addPaymentRecord(pr, null);
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
					String flag = shop_order_no.substring(0, 1);
					if (flag.equals("D")) {
						Order order = orderService.getOrder(shop_order_no);
						if (null != order && order.getPayStatus() == 0) {
							order.setPayStatus(1);// 已支付
							order.setPaymentMethod(1);// 支付方式（1.支付宝，2.微信）
							order.setPaymentFlowNo(trade_no);// 支付流水号（支付宝交易流水号）
							order.setPartTradeFlowNo(part_trade_flow_no); //内部支付流水号
							order.setPaymentTime(paidTime);
							
							orderService.updateOrder(order, null);
							
							//支付宝 订单支付后 添加加盟商收益数据
							this.addFranchiseeProfit(order);

							pr.setBizObjType(2);// 订单
							pr.setBizObjNo(order.getOrderNo());
							pr.setPayableAmount(ECNumberUtils.roundDoubleWithScale(order.getPayableAmount(), 2));
							pr.setPaidAmount(ECNumberUtils.roundDoubleWithScale(order.getPayableAmount(), 2));
							pr.setPaidTime(order.getPaymentTime());
							// 添加积分
							payOverAddRecordOrder(order);
							memberService.updateMemberRealAmount(order.getMemberNo(),ECNumberUtils.roundDoubleWithScale(order.getPayableAmount(), 2));

						} else {
							log.info("重复请求");
							return;
						}
					} else if (flag.equals("Y")) {
						DepositOrder dOrder = depositOrderService.getDepositOrder(shop_order_no);
						if (null != dOrder && dOrder.getPayStatus() == 0) {
							dOrder.setPayStatus(1);// 已支付
							dOrder.setPaymentMethod(1);// 支付方式（1.支付宝，2.微信）
							dOrder.setPaymentFlowNo(trade_no);// 支付流水号（支付宝交易流水号）
							dOrder.setPartTradeFlowNo(part_trade_flow_no); //内部支付流水号
							dOrder.setPaymentTime(new Date());
							
							dOrder.setRemainAmount(ECNumberUtils.roundDoubleWithScale(dOrder.getDepositAmount(), 2));
							dOrder.setIsAvailable(1);
							depositOrderService.updateDepositOrder(dOrder, null);

							//押金支付成功后，设置会员表的已支付押金金额为dOrder.getPayableAmount()
							Member member = memberService.getMember(dOrder.getMemberNo());
							if (member!=null) {
								Member memberForUpdate = new Member();
								memberForUpdate.setMemberNo(member.getMemberNo());
								memberForUpdate.setPayableDepositAmount(dOrder.getPayableAmount());
								memberService.updateMember(memberForUpdate, null);
							}
							
							pr.setBizObjType(3);// 押金
							pr.setBizObjNo(dOrder.getDepositOrderNo());
							pr.setPayableAmount(ECNumberUtils.roundDoubleWithScale(dOrder.getPayableAmount(), 2));
							pr.setPaidAmount(ECNumberUtils.roundDoubleWithScale(dOrder.getPayableAmount(), 2));
							pr.setPaidTime(dOrder.getPaymentTime());
							//如果是邀请优惠押金配置
							SysParam ssp=sysParamService.getByParamKey("preferential");
							if(ssp != null && "2".equals(ssp.getParamValue())){
								Member mm=memberService.getMember(dOrder.getMemberNo());
									String url="";
									String url2="";
										 try {
											 url=trigger+"marketingEvent/eventHandler?eventNo=4&memberNo="+mm.getMemberNo();
											HttpURLRequestUtil.doMsgGet(url);
											 if(mm.getRefereeId()!= null && !"".equals(mm.getRefereeId())){
												 url2=trigger+"marketingEvent/eventHandler?eventNo=3&memberNo="+mm.getRefereeId();
												HttpURLRequestUtil.doMsgGet(url2);
											 }
											
											
										} catch (Exception e) {
											e.printStackTrace();
										}
								
							}
						} else {
							log.info("重复请求");
							return;
						}
					} else if (flag.equals("T")) {
						PricingPackOrder order = pricingPackOrderService.getPricingPackOrder(shop_order_no);
						if (null != order && order.getPayStatus() == 0) {
							order.setPayStatus(1);// 已支付
							order.setPaymentMethod(1);// 支付方式（1.支付宝，2.微信）
							order.setIsAvailable(1);//可用
							order.setPaymentFlowNo(trade_no);// 支付流水号（支付宝交易流水号）
							order.setPartTradeFlowNo(part_trade_flow_no); //内部支付流水号
							pricingPackOrderService.updatePricingPackOrder(order, null);

							//查询我的余额
							Query qr = new Query();
							PricingPackOrder ppor = new PricingPackOrder();
							ppor.setPayStatus(1);// 已经支付的
							ppor.setIsAvailable(1);// 可用
							ppor.setNowTime(new Date());
							ppor.setMemberNo(order.getMemberNo());
							qr.setQ(ppor);
							List<PricingPackOrder>  pporList = pricingPackOrderService.getPricingPackOrderListBypo(qr);
							Double poa=0.0;
							Double uoa=0.0;
							for (PricingPackOrder pricingPackOrder : pporList) {
									if(pricingPackOrder.getPackOrderAmount() != null ){
										poa=ECCalculateUtils.add(poa,pricingPackOrder.getPackOrderAmount());
									}
									if(pricingPackOrder.getUseredOrderAmount() != null){
										uoa=ECCalculateUtils.add(uoa,pricingPackOrder.getUseredOrderAmount());
									}
								
							}
							if(order.getPackAmount()==null){
								order.setPackAmount(0d);
							}
							//金额套餐购买成功后给记账表添加记录
							Double ye = ECCalculateUtils.sub(poa, uoa);
							
							
							
							Accounts ac = new Accounts();
							ac.setMemberNo(order.getMemberNo());
							ac.setBusinessNo(order.getPackOrderNo());
							ac.setBusinessType(2);
							ac.setAccountType(2);
							ac.setAccountMoney(order.getPackOrderAmount());
							ac.setAccountBeforeMoney(ye);
							ac.setAccountOverMoney(ECCalculateUtils.add(ye, ac.getAccountMoney()));
							//查询超额的订单
							Order  odr =orderService.getOrderWaring(order.getMemberNo());
							if(odr != null){
								try {
									odr=orderService.orderTripView(odr).getData()== null ? odr:orderService.orderTripView(odr).getData();
								} catch (ParseException e) {
									e.printStackTrace();
								}
								
								Double amount=ECNumberUtils.roundDoubleWithScale(odr.getOrderAmount(),2);//订单金额
								if(ECCalculateUtils.ge(ye,amount)){//账户 余额  大于 订单总金额   消除警告
									Order o= new  Order();
									o.setOrderNo(odr.getOrderNo());
									o.setWarningOrder(0);
									orderDao.update(o);
								}
							}
							
							ac.setAccountTime(new Date());
							accountsService.addAccounts(ac, null);
							
							pr.setBizObjType(1);// 套餐订单
							pr.setBizObjNo(order.getPackOrderNo());
							pr.setPayableAmount(ECNumberUtils.roundDoubleWithScale(order.getPayableAmount(), 2));
							pr.setPaidAmount(ECNumberUtils.roundDoubleWithScale(order.getPayableAmount(), 2));
							pr.setPaidTime(paidTime);
							payOverAddRecordPackageOrder(order);	
							// 更新真实消费额
							memberService.updateMemberRealAmount(order.getMemberNo(),ECNumberUtils.roundDoubleWithScale(order.getPayableAmount(), 2));
						} else {
							log.info("重复请求");
							return;
						}

					}
					pr.setPayStatus(1);
					pr.setPayType(1);
					pr.setPayFlowNo(trade_no);
					pr.setPartTradeFlowNo(part_trade_flow_no); //内部支付流水号
					// 设置买家支付宝账户号
					if (null != buyerId) {
						pr.setBuyerId(buyerId);
					}
					paymentRecordService.addPaymentRecord(pr, null);
					log.info("the transtion end,result success");

					// out.println("success"); //请不要修改或删除
					response.setContentType("text/html;charset=UTF-8");
					response.setCharacterEncoding("UTF-8");
					response.setStatus(HttpServletResponse.SC_OK);
					PrintWriter out = response.getWriter();
					out.println("success");
					out.flush();
					out.close();
					// System.out.println("success");
					// 注意：
					// 该种交易状态只在一种情况下出现——开通了高级即时到账，买家付款成功后。
				}

				// 注意：
			} else {// 验证失败
				log.error("transtion error,because the sign fail");
				System.out.println("fail");
			}
		}

	}

	/**
	 * 订单支付完成后，添加积分记录，修改会员积分和等级信息
	 */
	private void payOverAddRecordOrder(Order order) {
		// 订单支付完成后，获得积分，记录到积分记录表中，并修改会员表的当前积分值和会员等级id
		MemberPointsRecord mpr = new MemberPointsRecord();
		mpr.setBusinessType(1);// 订单支付
		mpr.setPointsType(0);// 会员经验积分
		mpr.setOpType(1);// 增加积分
		// 获取订单支付积分规则
		Query q = new Query();
		MemberPointsRule mpRule = new MemberPointsRule();
		mpRule.setBusinessType(1);
		mpRule.setIsAvailable(1);
		mpRule.setIsDefault(1);
		mpRule.setIsDeleted(0);
		q.setQ(mpRule);
		List<MemberPointsRule> mpRuleResultList = memberPointsRuleService.getMemberPointsRuleList(q);
		Integer pointsValue = 0;
		if (mpRuleResultList != null && mpRuleResultList.size() > 0) {
			Double amount = order.getPayableAmount();
			String val = amount.toString().substring(0, amount.toString().lastIndexOf("."));
			Integer amount1 = Integer.parseInt(val);
			pointsValue = mpRuleResultList.get(0).getPointsValue() * amount1;
		}
		mpr.setPointsValue(pointsValue);
		mpr.setBusinessData(order.getOrderNo());
		mpr.setMemberNo(order.getMemberNo());
		mpr.setRecordMemo("订单支付获得积分");
		if (mpr.getPointsValue() > 0) {
			List<MemberPointsRecord> list = memberPointsRecordService.getMemberPointsRecordList(new Query(mpr));
			if (list == null || list.size() <= 0) {
				memberPointsRecordService.addMemberPointsRecord(mpr, null);
				Member member = memberService.getMember(order.getMemberNo());
				if (member != null) {
					// 会员当前积分值
					if (member.getMemberPointsValues() == null) {
						member.setMemberPointsValues(0);
					}
					member.setMemberPointsValues(member.getMemberPointsValues() + mpr.getPointsValue());
					Integer pointsValueLevel = memberLevelService.getNowLevelPoints(member.getMemberPointsValues());
					MemberLevel mLevelSearch = new MemberLevel();
					mLevelSearch.setIsAvailable(1);
					mLevelSearch.setIsDeleted(0);
					mLevelSearch.setUpgradePoint(pointsValueLevel);
					List<MemberLevel> mLevelList = memberLevelService.getMemberLevelList(new Query(mLevelSearch));
					if (mLevelList != null && mLevelList.size() > 0) {
						// 会员等级
						member.setMemberLevelId(mLevelList.get(0).getMemberLevelId());
					}
					Member mUp = new Member();
					mUp.setMemberNo(member.getMemberNo());
					mUp.setMemberPointsValues(member.getMemberPointsValues());
//					mUp.setMemberLevelId(member.getMemberLevelId());
					memberService.updateMember(mUp, null);
				}
			}
		}
	}

	/**
	 * 套餐支付完成后，添加积分记录，修改会员积分和等级信息
	 */
	private void payOverAddRecordPackageOrder(PricingPackOrder order) {
		// 订单支付完成后，获得积分，记录到积分记录表中，并修改会员表的当前积分值和会员等级id
		MemberPointsRecord mpr = new MemberPointsRecord();
		mpr.setBusinessType(2);// 套餐支付
		mpr.setPointsType(0);// 会员经验积分
		mpr.setOpType(1);// 增加积分
		// 获取订单支付积分规则
		Query q = new Query();
		MemberPointsRule mpRule = new MemberPointsRule();
		mpRule.setBusinessType(2);
		mpRule.setIsAvailable(1);
		mpRule.setIsDefault(1);
		mpRule.setIsDeleted(0);
		q.setQ(mpRule);
		List<MemberPointsRule> mpRuleResultList = memberPointsRuleService.getMemberPointsRuleList(q);
		Integer pointsValue = 0;
		if (mpRuleResultList != null && mpRuleResultList.size() > 0) {
			Double amount = order.getPayableAmount();
			String val = amount.toString().substring(0, amount.toString().lastIndexOf("."));
			Integer amount1 = Integer.parseInt(val);
			pointsValue = mpRuleResultList.get(0).getPointsValue() * amount1;
		}
		mpr.setPointsValue(pointsValue);
		mpr.setBusinessData(order.getPackOrderNo());
		mpr.setMemberNo(order.getMemberNo());
		mpr.setRecordMemo("套餐支付获得积分");
		if (mpr.getPointsValue() > 0) {
			List<MemberPointsRecord> list = memberPointsRecordService.getMemberPointsRecordList(new Query(mpr));
			if (list == null || list.size() <= 0) {
				memberPointsRecordService.addMemberPointsRecord(mpr, null);
				Member member = memberService.getMember(order.getMemberNo());
				if (member != null) {
					// 会员当前积分值
					if (member.getMemberPointsValues() == null) {
						member.setMemberPointsValues(0);
					}
					member.setMemberPointsValues(member.getMemberPointsValues() + mpr.getPointsValue());
					Integer pointsValueLevel = memberLevelService.getNowLevelPoints(member.getMemberPointsValues());
					MemberLevel mLevelSearch = new MemberLevel();
					mLevelSearch.setIsAvailable(1);
					mLevelSearch.setIsDeleted(0);
					mLevelSearch.setUpgradePoint(pointsValueLevel);
					List<MemberLevel> mLevelList = memberLevelService.getMemberLevelList(new Query(mLevelSearch));
					if (mLevelList != null && mLevelList.size() > 0) {
						// 会员等级
						member.setMemberLevelId(mLevelList.get(0).getMemberLevelId());
					}
					Member mUp = new Member();
					mUp.setMemberNo(member.getMemberNo());
					mUp.setMemberPointsValues(member.getMemberPointsValues());
//					mUp.setMemberLevelId(member.getMemberLevelId());
					memberService.updateMember(mUp, null);
				}
			}
		}
	}
	
	private String getWxAuthOpenid(String memberNo) {
		if(cacheUtil.getObject("wxauth_" + memberNo)!=null){
			Map<String,String> authMap=(Map<String,String>) cacheUtil.getObject("wxauth_" + memberNo); 
			
			return authMap.get("openid");
		}
		return "";
	}
	
	private void addFranchiseeProfit(Order order) throws Exception{
		/** 关联新增加盟商收益数据 **/
		Car car = carService.getCar(order.getCarNo());
		Park park = parkService.getPark(order.getStartParkNo());
		if(car != null){
			if(!"".equals(car.getFranchiseeNo()) && car.getFranchiseeNo() != null){
				//如果车辆关联加盟商，添加加盟收益数据
				FranchiseeProfit franchiseeProfit = new FranchiseeProfit();
				franchiseeProfit.setFranchiseeProfitNo(franchiseeProfitService.generatePK());
				franchiseeProfit.setFranchiseeNo(car.getFranchiseeNo());
				franchiseeProfit.setOrderNo(order.getOrderNo());
				franchiseeProfit.setOrderAmount(order.getOrderAmount());
				franchiseeProfit.setProfitType(Constant.PROFIT_CAR_TYPE);
				//查询加盟商信息
				Franchisee franchisee = franchiseeService.getFranchisee(car.getFranchiseeNo());
				if(franchisee != null){
					franchiseeProfit.setFranchiseeName(franchisee.getFranchiseeName());
					franchiseeProfit.setCarProportion(franchisee.getCarProportion());
					Double carProportion = (franchisee.getCarProportion())/100;
					franchiseeProfit.setCarProfit(ECCalculateUtils.round((ECCalculateUtils.mul(order.getOrderAmount(), carProportion)), 2));
					franchiseeProfit.setCarOrParkNo(order.getCarPlateNo());
				}
				franchiseeProfit.setCreateTime(order.getPaymentTime());
				if(franchiseeProfit.getCarProportion()==null){
					franchiseeProfit.setCarProportion(0d);
				}
				
				if(franchiseeProfit.getParkProportion()==null){
					franchiseeProfit.setParkProportion(0d);
				}
				
				
				if(franchiseeProfit.getCarProfit()==null){
					franchiseeProfit.setCarProfit(0d);
				}
				if(franchiseeProfit.getParkProfit() ==null ){
					franchiseeProfit.setParkProfit(0d);
				}
				franchiseeProfitService.addFranchiseeProfit(franchiseeProfit, null);
				
			}
		}
		if(park != null){
			if(!"".equals(park.getFranchiseeNo()) && park.getFranchiseeNo() != null){
				//如果关联场站关联加盟商，添加收益数据
				FranchiseeProfit franchiseeProfit = new FranchiseeProfit();
				franchiseeProfit.setFranchiseeProfitNo(franchiseeProfitService.generatePK());
				franchiseeProfit.setFranchiseeNo(park.getFranchiseeNo());
				franchiseeProfit.setOrderNo(order.getOrderNo());
				franchiseeProfit.setOrderAmount(order.getOrderAmount());
				franchiseeProfit.setProfitType(Constant.PROFIT_PARK_TYPE);
				//查询加盟商信息
				Franchisee franchisee = franchiseeService.getFranchisee(park.getFranchiseeNo());
				if(franchisee != null){
					franchiseeProfit.setFranchiseeName(franchisee.getFranchiseeName());
					franchiseeProfit.setParkProportion(franchisee.getParkProportion());
					Double carProportion = (franchisee.getParkProportion())/100;
					franchiseeProfit.setParkProfit(ECCalculateUtils.round((ECCalculateUtils.mul(order.getOrderAmount(), carProportion)),2));
					franchiseeProfit.setCarOrParkNo(order.getStartParkNo());
				}
				franchiseeProfit.setCreateTime(order.getPaymentTime());
				if(franchiseeProfit.getCarProfit()==null){
					franchiseeProfit.setCarProfit(0d);
				}
				if(franchiseeProfit.getParkProfit() ==null ){
					franchiseeProfit.setParkProfit(0d);
				}
				if(franchiseeProfit.getCarProportion()==null){
					franchiseeProfit.setCarProportion(0d);
				}
				
				if(franchiseeProfit.getParkProportion()==null){
					franchiseeProfit.setParkProportion(0d);
				}
				franchiseeProfitService.addFranchiseeProfit(franchiseeProfit, null);
			}
		}
	}
}
