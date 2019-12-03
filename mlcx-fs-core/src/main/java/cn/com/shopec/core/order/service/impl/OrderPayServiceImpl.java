package cn.com.shopec.core.order.service.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.annotation.Resource;
import javax.net.ssl.SSLContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.jdom.JDOMException;
import org.springframework.stereotype.Service;

import cn.com.shopec.common.Operator;
import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.common.constants.Constant;
import cn.com.shopec.common.pay.aliPay.AlipayConfig;
import cn.com.shopec.common.pay.aliPay.AlipayNotify;
import cn.com.shopec.common.pay.aliPay.OrderInfoUtil2_0;
import cn.com.shopec.common.pay.common.MD5Util;
import cn.com.shopec.common.pay.wxPay.CommonUtil;
import cn.com.shopec.common.pay.wxPay.GetWxOrderno;
import cn.com.shopec.common.pay.wxPay.PayCommonUtil;
import cn.com.shopec.common.pay.wxPay.ResponseHandler;
import cn.com.shopec.common.pay.wxPay.TenpayUtil;
import cn.com.shopec.common.pay.wxPay.WxpayConfig;
import cn.com.shopec.common.pay.wxPay.XMLUtil;
import cn.com.shopec.common.utils.ECDateUtils;
import cn.com.shopec.common.utils.Uint32;
import cn.com.shopec.core.order.model.Order;
import cn.com.shopec.core.order.service.OrderPayService;
import cn.com.shopec.core.order.service.OrderService;
import cn.com.shopec.core.system.dao.SysParamDao;
import cn.com.shopec.core.system.model.SysParam;

/**
 * 订单表 服务实现类
 */
@Service
public class OrderPayServiceImpl implements OrderPayService {

	private static final Log log = LogFactory.getLog(OrderPayServiceImpl.class);
	
	@Resource
	private OrderService orderService;
	@Resource
	private SysParamDao sysParamDao;
	

	@Override
	public void wxUpdateOrder(HttpServletRequest request, HttpServletResponse response,Operator operator) {
		String out_trade_no="";
		synchronized (this) {
 			try {
 				String appkey = WxpayConfig.key;// 商户平台上那个自己生成的KEY
 				ResponseHandler resHandler = new ResponseHandler(request, response);
 				resHandler.setKey(appkey);
 				Map postdata = resHandler.getSmap();
 				if (resHandler.isWechatSign()) {// 是否微信签名
 					// 商户订单号
 					out_trade_no = (String) postdata.get("out_trade_no");
 					// 微信支付订单号
 					String transaction_id = (String) postdata.get("transaction_id");
 					// 金额,以分为单位
 					Double totalFee = Double.valueOf(postdata.get("total_fee") + "");
 					totalFee = totalFee / 100; // 这块需转换
 					// 支付完成时间
 					String time_end = (String) postdata.get("time_end");
 					// 支付结果
 					String trade_state = (String) postdata.get("result_code");
 					// 判断签名及结果
 					if ("SUCCESS".equals(trade_state)) {
 						// 即时到账处理业务开始
 						Order order=orderService.getOrder(out_trade_no);
 						order.setPayStatus(1);//支付状态为已支付
 						order.setPaymentMethod(2);//支付方式（1.支付宝，2.微信）
 						order.setPaymentFlowNo(transaction_id);//支付流水号（微信支付订单号）
 						order.setPaymentTime(new Date());
 						orderService.updateOrder(order, null);
 						resHandler.sendToCFT("<xml><return_code><![CDATA[SUCCESS]]></return_code></xml>");
 					} else {// sha1签名失败
 						resHandler.sendToCFT("<xml><return_code><![CDATA[FAIL]]></return_code></xml>");
 					}
 				} else {// MD5签名失败
 					resHandler.sendToCFT("<xml><return_code><![CDATA[FAIL]]></return_code></xml>");
 				}

 			} catch (Exception e) {
 				e.printStackTrace();
 			}
 		}
	}


	@Override
	public ResultInfo<SortedMap<String, Object>> getCodeUrl(HttpServletRequest request, HttpServletResponse response, Order order,Integer tag) {
				ResultInfo<SortedMap<String, Object>> resultInfo=new ResultInfo<SortedMap<String, Object>>();
				String body="";
				//调用统一支付接口https://api.mch.weixin.qq.com/pay/unifiedorder
				// 请求参数准备
				String appid = WxpayConfig.appID;//微信开放平台审核通过的应用APPID
				String mch_id = WxpayConfig.mchID;//微信支付分配的商户号
				String device_info = "";//设备号 非必输
				
				//生成随机字符串
				String currTime = TenpayUtil.getCurrTime();
				String strTime = currTime.substring(8, currTime.length());// 8位日期
				String strRandom = TenpayUtil.buildRandom(4) + "";// 四位随机数
				String strReq = strTime + strRandom;// 10位序列号,可以自行调整。
				String nonce_str = strReq;
				
				// 商品描述
				// String body = describe;
				// 商品描述根据情况修改
				
				SysParam sys=sysParamDao.getByParamKey("APP_NAME");
				if(sys != null){
					 body = sys.getParamValue()+"-订单支付";
				}else{
					 body = "猛龙出行-订单支付";
				}
				
				// 附加数据
				// String attach = userId;
				// 商户订单号
				String out_trade_no =order.getOrderNo();
				// 总金额以分为单位，不带小数点
				//int total_fee = (int)(order.getPayableAmount()*100);
				int total_fee = (int)(1);
				// 订单生成的机器 IP
				String spbill_create_ip = "";
				// 订 单 生 成 时 间 非必输
			    String time_start ="";
				// 订单失效时间 非必输
				String time_expire = "";
				// 商品标记 非必输
				String goods_tag = "";
				// 这里notify_url是 支付完成后微信发给该链接信息，可以判断会员是否支付成功，改变订单状态等。处理完调用的路径
				String notify_url = WxpayConfig.notify_url;
				String trade_type = "APP";//交易类型
				
				//生成签名
				SortedMap<String, String> packageParams = new TreeMap<String, String>();
				packageParams.put("appid", appid);
				packageParams.put("mch_id", mch_id);
				packageParams.put("nonce_str", nonce_str);
				packageParams.put("body", body);
				// packageParams.put("attach", attach);
				packageParams.put("out_trade_no", out_trade_no);
				//TODO 这里写的金额为1 分到时修改
				packageParams.put("total_fee",""+total_fee);
				packageParams.put("spbill_create_ip", spbill_create_ip);
				packageParams.put("notify_url", notify_url);
				packageParams.put("trade_type", trade_type);
				String sign= PayCommonUtil.createSign("UTF-8", packageParams);//生成签名
				packageParams.put("sign", sign);
				packageParams.remove("key");//调用统一下单无需key（商户应用密钥）
				String requestXml = PayCommonUtil.getRequestXml(packageParams);//生成Xml格式的字符串
				String createOrderURL = "https://api.mch.weixin.qq.com/pay/unifiedorder";
				String result = CommonUtil.httpsRequest(createOrderURL, "POST", requestXml);//以post请求的方式调用统一下单接口
				Map map;
				try {
					map = XMLUtil.doXMLParse(result);
					String return_code=(String) map.get("return_code");
					String prepay_id =null;
					if (return_code.contains("SUCCESS")){
					   prepay_id=(String) map.get("prepay_id");//获取到prepay_id
					   String timeStamp=ECDateUtils.formatStringTimeWX(new Date());
					   if(tag.equals(0)){//安卓
						 //2.签名返回信息
						   resultInfo=getCodeUrlApp(request,response,prepay_id,nonce_str,timeStamp); 
					   }else if(tag.equals(1)){//ios
						   Long time=System.currentTimeMillis()/1000; 
						   Uint32 timeStamp1= new Uint32(time);
						 //2.签名返回信息
						   resultInfo=getCodeUrlAppIOS(request,response,prepay_id,nonce_str,timeStamp1);
					   }
					   
					   resultInfo.setCode(Constant.SECCUESS);
					}else{
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
	public ResultInfo<SortedMap<String, Object>> getCodeUrlApp(HttpServletRequest request, HttpServletResponse response, String prepay_id,
			String nonceStr, String timeStamp) {
				ResultInfo<SortedMap<String, Object>> resultInfo=new ResultInfo<SortedMap<String, Object>>();			
				// 请求参数准备
				String appid = WxpayConfig.appID;//微信开放平台审核通过的应用APPID
				String partnerId = WxpayConfig.mchID;//微信支付分配的商户号
				
				//生成签名
				SortedMap<String, Object> packageParams = new TreeMap<String, Object>();
				packageParams.put("appid", appid);
				packageParams.put("partnerid", partnerId);
				packageParams.put("prepayid", prepay_id);
				packageParams.put("package", "Sign=WXPay");
				packageParams.put("noncestr", nonceStr);
				packageParams.put("timestamp", timeStamp);
				String sign=PayCommonUtil.createSignIOS("UTF-8", packageParams);
				packageParams.put("sign", sign);
				packageParams.put("packageStr", "Sign=WXPay");//app端package关键字
				if(sign!=null&&!sign.equals("")){
					resultInfo.setCode(Constant.SECCUESS);
					resultInfo.setData(packageParams);
				}else{
					resultInfo.setCode(Constant.FAIL);
				}
				return resultInfo;
	}
	public ResultInfo<SortedMap<String, Object>> getCodeUrlAppIOS(HttpServletRequest request, HttpServletResponse response, String prepay_id,
			String nonceStr, Uint32 timeStamp) {
				ResultInfo<SortedMap<String, Object>> resultInfo=new ResultInfo<SortedMap<String, Object>>();			
				// 请求参数准备
				String appid = WxpayConfig.appID;//微信开放平台审核通过的应用APPID
				String partnerId = WxpayConfig.mchID;//微信支付分配的商户号
				
				//生成签名
				SortedMap<String, Object> packageParams = new TreeMap<String, Object>();
				packageParams.put("appid", appid);
				packageParams.put("partnerid", partnerId);
				packageParams.put("prepayid", prepay_id);
				packageParams.put("package", "Sign=WXPay");
				packageParams.put("noncestr", nonceStr);
				packageParams.put("timestamp", timeStamp);
				String sign=PayCommonUtil.createSignIOS("UTF-8", packageParams);
				packageParams.put("sign", sign);
				packageParams.put("packageStr", "Sign=WXPay");//app端package关键字
				packageParams.put("timestamp", timeStamp.toString());
				if(sign!=null&&!sign.equals("")){
					resultInfo.setCode(Constant.SECCUESS);
					resultInfo.setData(packageParams);
				}else{
					resultInfo.setCode(Constant.FAIL);
				}
				return resultInfo;
	}
/**
 * 调用微信查询订单支付结果的接口
 * */
	@Override
	public ResultInfo<String> wxGetOrderPayResult(HttpServletRequest request, HttpServletResponse response,
			String orderNo) {
		ResultInfo<String> resultInfo=new ResultInfo<String>();	
		//生成随机字符串
		String currTime = TenpayUtil.getCurrTime();
		String strTime = currTime.substring(8, currTime.length());// 8位日期
		String strRandom = TenpayUtil.buildRandom(4) + "";// 四位随机数
		String strReq = strTime + strRandom;// 10位序列号,可以自行调整。
		String nonce_str = strReq;
		SortedMap<String, String> packageParams = new TreeMap<String, String>();
		packageParams.put("appid", WxpayConfig.appID);
		packageParams.put("mch_id", WxpayConfig.mchID);
		packageParams.put("out_trade_no", orderNo);
		packageParams.put("nonce_str", nonce_str);
		String sign= PayCommonUtil.createSign("UTF-8", packageParams);//生成签名
		packageParams.put("sign", sign);
		packageParams.remove("key");//调用统一下单无需key（商户应用密钥）
		String requestXml = PayCommonUtil.getRequestXml(packageParams);//生成Xml格式的字符串
		String createOrderURL = "https://api.mch.weixin.qq.com/pay/orderquery";
		String result = CommonUtil.httpsRequest(createOrderURL, "POST", requestXml);//以post请求的方式调用统一下单接口
		Map map;
		String trade_state ="";
		try {
			map = XMLUtil.doXMLParse(result);
			String return_code=(String) map.get("return_code");
			String result_code=(String) map.get("result_code");
			if (return_code.contains("SUCCESS")){
				if(result_code.contains("SUCCESS")){
					trade_state=(String) map.get("trade_state");
					
					/*SUCCESS—支付成功 REFUND—转入退款 NOTPAY—未支付 CLOSED—已关闭 REVOKED—已撤销（刷卡支付）
					 *  USERPAYING--用户支付中 PAYERROR--支付失败(其他原因，如银行返回失败) */
					resultInfo.setCode(Constant.SECCUESS);
					resultInfo.setData(trade_state);
				}else{
					 resultInfo.setCode(Constant.FAIL);	
				}
			}else{
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
	public ResultInfo<String> alipayGetOrderStr(HttpServletRequest request, HttpServletResponse response, Order order) {
		ResultInfo<String> resultInfo=new ResultInfo<String>();
				String subject=order.getOrderNo();//(商品名称)
				String out_trade_no=order.getOrderNo();//订单号
				String total_amount=order.getPayableAmount().toString();//订单总金额，单位为元，精确到小数点后两位
				Map<String, String> authInfoMap = OrderInfoUtil2_0.buildOrderParamMap(AlipayConfig.appId,out_trade_no,AlipayConfig.notify_url,total_amount,subject);
				String orderParam=OrderInfoUtil2_0.buildOrderParam(authInfoMap);
				String sign = OrderInfoUtil2_0.getSign(authInfoMap, AlipayConfig.rsa_private);
				final String orderInfo = orderParam + "&" + sign;
        String result=orderInfo;
		if(result!=null&&!result.equals("")){
			resultInfo.setCode(Constant.SECCUESS);
			resultInfo.setData(result);
		}else{
			resultInfo.setCode(Constant.FAIL);
		}
		return resultInfo;
	}


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
		// 商户订单号
			String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"), "UTF-8");
			// 支付宝交易流水号
			String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"), "UTF-8");
			// 交易状态
			String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"), "UTF-8");
			// 购买时间
			String notify_time = new String(request.getParameter("notify_time").getBytes("ISO-8859-1"), "UTF-8");
			// 获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以上仅供参考)//
			if(AlipayNotify.verify(params)){//验证成功
				//////////////////////////////////////////////////////////////////////////////////////////
				//请在这里加上商户的业务逻辑程序代码

				//——请根据您的业务逻辑来编写程序（以下代码仅作参考）——
				
				if(trade_status.equals("TRADE_FINISHED")){
					//判断该笔订单是否在商户网站中已经做过处理
						//如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
						//如果有做过处理，不执行商户的业务程序
					//该种交易状态只在两种情况下出现
					//——请根据您的业务逻辑来编写程序（以上代码仅作参考）——
					Order order=orderService.getOrder(out_trade_no);
					order.setPayStatus(1);//已支付
					order.setPaymentMethod(1);//支付方式（1.支付宝，2.微信）
					order.setPaymentFlowNo(trade_no);//支付流水号（支付宝交易流水号）
					order.setPaymentTime(new Date());
					orderService.updateOrder(order, null);
					//out.println("success");	//请不要修改或删除
					System.out.println("success");
					//1、开通了普通即时到账，买家付款成功后。
					//2、开通了高级即时到账，从该笔交易成功时间算起，过了签约时的可退款时限（如：三个月以内可退款、一年以内可退款等）后。
				} else if (trade_status.equals("TRADE_SUCCESS")){
					//判断该笔订单是否在商户网站中已经做过处理
						//如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
						//如果有做过处理，不执行商户的业务程序
					//——请根据您的业务逻辑来编写程序（以上代码仅作参考）——
					Order order=orderService.getOrder(out_trade_no);
					order.setPayableAmount(0d);
					order.setPayStatus(1);//已支付
					order.setPaymentMethod(1);//支付方式（1.支付宝，2.微信）
					order.setPaymentFlowNo(trade_no);//支付流水号（支付宝交易流水号）
					order.setPaymentTime(new Date());
					orderService.updateOrder(order, null);
					//out.println("success");	//请不要修改或删除
					System.out.println("success");
					//注意：
					//该种交易状态只在一种情况下出现——开通了高级即时到账，买家付款成功后。
				}

				//注意：
				//////////////////////////////////////////////////////////////////////////////////////////
			}else{//验证失败
				System.out.println("fail");
			}
					
	}
	
	/**
	 * 微信退款
	 * @param transaction_id
	 * @param out_trade_no
	 * @param out_refund_no
	 * @return
	 * @throws Exception
	 */
	public String wxPayRefundRequest(String transaction_id, String out_trade_no,String out_refund_no,int total_fee, int refund_fee, String op_user_id)throws Exception{
		  // ResultInfo<SortedMap<String, Object>> resultInfo=new ResultInfo<SortedMap<String, Object>>();			
			String appid = "wx680d6c88ac75a68b";//微信开放平台审核通过的应用APPID
			String mch_id = "1398876402";//微信支付分配的商户号
			
			String strResponse = null;
			
	       SortedMap<Object,Object> parameters = new TreeMap<Object,Object>();
	       parameters.put("appid", appid);
	       parameters.put("mch_id", mch_id);
	       parameters.put("nonce_str", CreateNoncestr());
	      //在notify_url中解析微信返回的信息获取到 transaction_id，此项不是必填，详细请看上图文档
	       parameters.put("transaction_id", transaction_id);// PaymentFlowNo 支付流水号（微信支付订单号）
	       parameters.put("out_trade_no", out_trade_no);//订单编号
	       parameters.put("out_refund_no", out_refund_no);//我们自己设定的退款申请号，约束为UK
	       parameters.put("total_fee", "1");  //单位为分
	       parameters.put("refund_fee", "1");   //单位为分
	       parameters.put("op_user_id", mch_id);
	       String sign = createSign("UTF-8", parameters);
	       parameters.put("sign", sign);
	       
	       String reuqestXml = getRequestXml(parameters);
	       KeyStore keyStore  = KeyStore.getInstance("PKCS12");
	       String pahtCert = getPath() + "/cert/apiclient_cert.p12";
	       File file =  new File(pahtCert);
	       FileInputStream instream = new FileInputStream(file);////放退款证书的路径
	       try {
	          keyStore.load(instream, mch_id.toCharArray());
	       } finally {
	          instream.close();
	       }

	      SSLContext sslcontext = SSLContexts.custom().loadKeyMaterial(keyStore, mch_id.toCharArray()).build();
	      SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
	              sslcontext,
	              new String[] { "TLSv1" },
	              null,
	              SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
	      CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
	      try {

	          HttpPost httpPost = new HttpPost("https://api.mch.weixin.qq.com/secapi/pay/refund");//退款接口
	          
	          System.out.println("----------executing request--------" + httpPost.getRequestLine());
	          StringEntity  reqEntity  = new StringEntity(reuqestXml);
	          // 设置类型 
	          reqEntity.setContentType("application/x-www-form-urlencoded"); 
	          httpPost.setEntity(reqEntity);
	          CloseableHttpResponse response = httpclient.execute(httpPost);
	          try {
	              HttpEntity entity = response.getEntity();

	              System.out.println("----------------------------------------");
	              System.out.println(response.getStatusLine());
	              if (entity != null) {
	                  System.out.println("Response content length: " + entity.getContentLength());
	                  BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(entity.getContent(),"UTF-8"));
	                  String text;
	                  while ((text = bufferedReader.readLine()) != null) {
	                      System.out.println(text);
	                  }
	                 
	                  SAXReader saxReader = new SAXReader();
	                  Document document = saxReader.read(entity.getContent());
	                  Element rootElt = document.getRootElement();
	                  // 结果码
	                  String returnCode = rootElt.elementText("return_code");
	                  String resultCode = rootElt.elementText("result_code");
	                  if ("SUCCESS".equals(returnCode)&&"SUCCESS".equals(resultCode)) {
	                      strResponse=returnCode;
	                  }else {
	                      strResponse=rootElt.elementText("err_code_des");
	                  }
	              }
	              EntityUtils.consume(entity);
	          } finally {
	              response.close();
	          }
	      } finally {
	          httpclient.close();
	      }
		
		return strResponse;
	}


	/**
	 * 支付宝退款
	 * @param out_trade_no
	 * @param trade_no
	 * @param refund_amount
	 * @return
	 * @throws AlipayApiException 
	 */
    public String alipayRefundRequest(String out_trade_no,String trade_no,double refund_amount) throws Exception{
        // 发送请求
        String strResponse = null;
       
        return strResponse;

    }
    
	public static String CreateNoncestr() {
        String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        String res = "";
        for (int i = 0; i < 16; i++) {
            Random rd = new Random();
            res += chars.charAt(rd.nextInt(chars.length() - 1));
        }
        return res;
	}
	
	public static String createSign(String charSet,SortedMap<Object,Object> parameters){
        StringBuffer sb = new StringBuffer();
        Set es = parameters.entrySet();
        Iterator it = es.iterator();
        while(it.hasNext()) {
            Map.Entry entry = (Map.Entry)it.next();
            String k = (String)entry.getKey();
            Object v = entry.getValue();
            if(null != v && !"".equals(v) 
                    && !"sign".equals(k) && !"key".equals(k)) {
                sb.append(k + "=" + v + "&");
            }
        }
        sb.append("key=" + "lcgx2015yu20161019shezhilcgx2015");
        String sign = MD5Util.MD5Encode(sb.toString(), charSet).toUpperCase();
        return sign;
	}
	
	public static String getRequestXml(SortedMap<Object,Object> parameters){
	    StringBuffer sb = new StringBuffer();
	    sb.append("<xml>");
	    Set es = parameters.entrySet();
	    Iterator it = es.iterator();
	    while(it.hasNext()) {
	        Map.Entry entry = (Map.Entry)it.next();
	        String k = (String)entry.getKey();
	        String v = (String)entry.getValue();
	        if ("attach".equalsIgnoreCase(k)||"body".equalsIgnoreCase(k)||"sign".equalsIgnoreCase(k)) {
	            sb.append("<"+k+">"+"<![CDATA["+v+"]]></"+k+">");
	        }else {
	            sb.append("<"+k+">"+v+"</"+k+">");
	        }
	    }
	    sb.append("</xml>");
	    return sb.toString();
	}

	public static String getPath() throws UnsupportedEncodingException {
		String configPath = GetWxOrderno.class.getResource("/").getPath();
		return java.net.URLDecoder.decode(configPath, "utf-8");
	}
	
}
