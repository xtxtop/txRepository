package cn.com.shopec.common.pay.wxPay;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.com.shopec.core.order.model.Order;


/* *
 *功能：微信支付(商户后台)
 */

public class WXPayServiceUtil {

	/**
	 * 获取本机IP的方法
	 */
	/**
     * 获取本机Ip 
     *  
     *  通过 获取系统所有的networkInterface网络接口 然后遍历 每个网络下的InterfaceAddress组。
     *  获得符合 <code>InetAddress instanceof Inet4Address</code> 条件的一个IpV4地址
     * @return
     */
    @SuppressWarnings("rawtypes")
    private static String localIp(){
        String ip = null;
        Enumeration allNetInterfaces;
        try {
            allNetInterfaces = NetworkInterface.getNetworkInterfaces();            
            while (allNetInterfaces.hasMoreElements()) {
                NetworkInterface netInterface = (NetworkInterface) allNetInterfaces.nextElement();
                List<InterfaceAddress> InterfaceAddress = netInterface.getInterfaceAddresses();
                for (InterfaceAddress add : InterfaceAddress) {
                    InetAddress Ip = add.getAddress();
                    if (Ip != null && Ip instanceof Inet4Address) {
                        ip = Ip.getHostAddress();
                    }
                }
            }
        } catch (SocketException e) {
            // TODO Auto-generated catch block        
           System.out.println("获取本机Ip失败:异常信息:"+e.getMessage());
        }
        return ip;
    }
    
//    /** 
//     * 获取预支付订单的信息（统一下单接口）
//     * @param 
//     * @return 
//     */
//    public static String getCodeUrl(HttpServletRequest request,HttpServletResponse response,Order order) {
//    	//调用统一支付接口https://api.mch.weixin.qq.com/pay/unifiedorder
//		// 请求参数准备
//		String appid = WxpayConfig.appID;//微信开放平台审核通过的应用APPID
//		String mch_id = WxpayConfig.mchID;//微信支付分配的商户号
//		String device_info = "";//设备号 非必输
//		
//		//生成随机字符串
//		String currTime = TenpayUtil.getCurrTime();
//		String strTime = currTime.substring(8, currTime.length());// 8位日期
//		String strRandom = TenpayUtil.buildRandom(4) + "";// 四位随机数
//		String strReq = strTime + strRandom;// 10位序列号,可以自行调整。
//		String nonce_str = strReq;
//		
//		
//		
//		// 商品描述
//		// String body = describe;
//		// 商品描述根据情况修改
//		String body = "行知出行-订单支付";
//		// 附加数据
//		// String attach = userId;
//		// 商户订单号
//		String out_trade_no =order.getOrderNo();
//		// 总金额以分为单位，不带小数点
//		int total_fee = (int)(order.getPayableAmount()*100);
//		// 订单生成的机器 IP
//		String spbill_create_ip = "";
//		// 订 单 生 成 时 间 非必输
//	    String time_start ="";
//		// 订单失效时间 非必输
//		String time_expire = "";
//		// 商品标记 非必输
//		String goods_tag = "";
//		// 这里notify_url是 支付完成后微信发给该链接信息，可以判断会员是否支付成功，改变订单状态等。处理完调用的路径
//		String notify_url = WxpayConfig.notify_url;
//		String trade_type = "APP";//交易类型
//		
//		
//		//生成签名
//		String appsecret=WxpayConfig.appSECRET;
//		String partnerkey = WxpayConfig.key;//在微信商户平台pay.weixin.com里自己生成的那个key
//		SortedMap<String, String> packageParams = new TreeMap<String, String>();
//		packageParams.put("appid", appid);
//		packageParams.put("mch_id", mch_id);
//		packageParams.put("nonce_str", nonce_str);
//		packageParams.put("body", body);
//		// packageParams.put("attach", attach);
//		packageParams.put("out_trade_no", out_trade_no);
//		//TODO 这里写的金额为1 分到时修改
//		packageParams.put("total_fee",""+total_fee);
//		packageParams.put("spbill_create_ip", spbill_create_ip);
//		packageParams.put("notify_url", notify_url);
//		packageParams.put("trade_type", trade_type);
//		RequestHandler reqHandler = new RequestHandler(null, null);
//		reqHandler.init(appid, appsecret, partnerkey);
//		String sign = reqHandler.createSign(packageParams);//得到签名
//		
//		
//		//调用统一下单接口，返回需要的prepay_id
//		//微信生成的预支付回话标识，用于后续接口调用中使用，该值有效期为2小时
//		String xml = "<xml>" 
//				+ "<appid>" + appid + "</appid>" 
//				+ "<body><![CDATA[" + body + "]]></body>"
//				+ "<mch_id>" + mch_id + "</mch_id>" 
//				+ "<nonce_str>" + nonce_str + "</nonce_str>"
//				+ "<notify_url>" + notify_url + "</notify_url>"
//				+ "<out_trade_no>"+ out_trade_no + "</out_trade_no>"
//				+ "<spbill_create_ip>" + spbill_create_ip + "</spbill_create_ip>"
//				+ "<total_fee>"+total_fee+"</total_fee>"
//				+ "<trade_type>" + trade_type + "</trade_type>" 
//				+ "<sign><![CDATA[" + sign + "]]></sign>"
//				+ "</xml>";
//		String prepay_id="";
//		String createOrderURL = "https://api.mch.weixin.qq.com/pay/unifiedorder";
//		try {
//			prepay_id=new GetWxOrderno().getPayNo(createOrderURL, xml);
//			if (prepay_id.equals("")) {
//				System.out.println("统一支付接口获取预支付订单出错");
//			}
//		} catch (Exception e1) {
//			e1.printStackTrace();
//		}
//		return prepay_id;
//    }
// // 微信支付的回调方法
// 	public String wxPay(HttpServletRequest request, HttpServletResponse response) {
// 		String out_trade_no="";
// 		synchronized (this) {
// 			try {
// 				String appkey = WxpayConfig.key;// 商户平台上那个自己生成的KEY
// 				ResponseHandler resHandler = new ResponseHandler(request, response);
// 				// java.io.PrintWriter out = response.getWriter();
// 				resHandler.setKey(appkey);
// 				Map postdata = resHandler.getSmap();
// 				// out.print("---------postdata：" + postdata);
// 				if (resHandler.isWechatSign()) {// 是否微信签名
// 					// 商户订单号
// 					out_trade_no = (String) postdata.get("out_trade_no");
// 					// 微信支付订单号
// 					String transaction_id = (String) postdata.get("transaction_id");
// 					// 金额,以分为单位
// 					Double totalFee = Double.valueOf(postdata.get("total_fee") + "");
// 					totalFee = totalFee / 100; // 这块需转换
// 					// 支付完成时间
// 					String time_end = (String) postdata.get("time_end");
// 					// 支付结果
// 					String trade_state = (String) postdata.get("result_code");
// 					// 判断签名及结果
// 					if ("SUCCESS".equals(trade_state)) {
// 						// 即时到账处理业务开始
// 						// 注意交易单不要重复处理
// 					} else {// sha1签名失败
// 						resHandler.sendToCFT("<xml><return_code><![CDATA[FAIL]]></return_code></xml>");
// 					}
// 				} else {// MD5签名失败
// 					resHandler.sendToCFT("<xml><return_code><![CDATA[FAIL]]></return_code></xml>");
// 				}
//
// 			} catch (Exception e) {
// 				e.printStackTrace();
// 			}
// 		}
// 		return out_trade_no;
// 	}
// 	 /** 
// 	  * app端调起支付所需的签名
//     * 根据统一下单生成的prepay_id，生成签名信息返回给app端
//     * 参与签名的字段名为appId，partnerId，prepayId，nonceStr，timeStamp，package
//     * @param 
//     * @return 
//     */
//    public static String getCodeUrlApp(HttpServletRequest request,HttpServletResponse response,String prepay_id,
//    		String nonceStr,String timeStamp) {
//		// 请求参数准备
//		String appid = WxpayConfig.appID;//微信开放平台审核通过的应用APPID
//		String partnerId = WxpayConfig.mchID;//微信支付分配的商户号
//		
//		//生成签名
//		String appsecret=WxpayConfig.appSECRET;
//		String partnerkey = WxpayConfig.key;//在微信商户平台pay.weixin.com里自己生成的那个key
//		SortedMap<String, String> packageParams = new TreeMap<String, String>();
//		packageParams.put("appid", appid);
//		packageParams.put("partnerid", partnerId);
//		packageParams.put("prepayid", prepay_id);
//		packageParams.put("package", "Sign=WXPay");
//		packageParams.put("noncestr", nonceStr);
//		packageParams.put("timestamp", timeStamp);
//		RequestHandler reqHandler = new RequestHandler(null, null);
//		reqHandler.init(appid, appsecret, partnerkey);
//		String sign = reqHandler.createSign(packageParams);//得到签名
//		return sign;
//    }
}
