package cn.com.shopec.common.pay.wxPay;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

/* *
 *类名：WxpayConfig
 *功能：基础配置类
 *详细：设置帐户有关信息及返回路径
 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 */

public class WxpayConfig {
	// -----------必要的
	// 每次自己Post数据给API的时候都要用这个key来对所有字段进行签名，生成的签名会放在Sign这个字段，API收到Post数据的时候也会用同样的签名算法对Post过来的数据进行签名和验证
	// 收到API的返回的时候也要用这个key来对返回的数据算下签名，跟API的Sign数据进行比较，如果值不一致，有可能数据被第三方给篡改

	public static String key = getString("wxpay.key");

	// 微信分配的应用ID(微信开放平台审核通过的应用AppId)
	public static String appID = getString("wxpay.appid");

	// 微信公众号的异步通知地址
	public static String notify_url = getString("wxpay.notify_url");
	public static String deposit_notify_url = getString("wxpay.deposit_notify_url");
	public static String pricingPack_notify_url = getString("wxpay.pricingPack_notify_url");
	// 微信支付分配的商户号ID（开通公众号的微信支付功能之后可以获取到）
	public static String mchID = getString("wxpay.mchid");
	// 订单详情支付接口异步通知地址
	public static String notify_url_1 = getString("wxpay.notify_url_1");
	// 充值记录微信回调地址
	public static String notify_url_w = getString("wxpay.notify_url_w");
	// -----------必要的
	// 微信公众号的秘钥
	public static String appSECRET = getString("wxpay.appsecret");

	// 受理模式下给子商户分配的子商户号
	public static String subMchID = getString("wxpay.submchid");

	// HTTPS证书的本地路径
	public static String certLocalPath = getString("wxpay.certLocalPath");

	// HTTPS证书密码，默认密码等于商户号MCHID
	public static String certPassword = getString("wxpay.certPassword");

	// 是否使用异步线程的方式来上报API测速，默认为异步模式
	public static boolean useThreadToDoReport = true;

	// 机器IP
	public static String ip = "";

	// 以下是几个API的路径：
	// 1）被扫支付API
	public static String PAY_API = "https://api.mch.weixin.qq.com/pay/micropay";

	// 2）被扫支付查询API
	public static String PAY_QUERY_API = "https://api.mch.weixin.qq.com/pay/orderquery";

	// 3）退款API
	public static String REFUND_API = "https://api.mch.weixin.qq.com/secapi/pay/refund";

	// 4）退款查询API
	public static String REFUND_QUERY_API = "https://api.mch.weixin.qq.com/pay/refundquery";

	// 5）撤销API
	public static String REVERSE_API = "https://api.mch.weixin.qq.com/secapi/pay/reverse";

	// 6）下载对账单API
	public static String DOWNLOAD_BILL_API = "https://api.mch.weixin.qq.com/pay/downloadbill";

	// 7) 统计上报API
	public static String REPORT_API = "https://api.mch.weixin.qq.com/payitil/report";

	// 微信公众帐号应用
	public static String h5_appID = getString("wxpay.h5.appid");
	// 微信公众帐号应用关联的商户号ID
	public static String h5_mchID = getString("wxpay.h5.mchid");
	// 秘钥
	public static String h5_appSECRET = getString("wxpay.h5.appsecret");
	// HTTPS证书的本地路径
	public static String h5_certLocalPath = getString("wxpay.h5.certLocalPath");

	// HTTPS证书密码，默认密码等于商户号MCHID
	public static String h5_certPassword = getString("wxpay.h5.certPassword");

	public static boolean isUseThreadToDoReport() {
		return useThreadToDoReport;
	}

	public static void setUseThreadToDoReport(boolean useThreadToDoReport) {
		WxpayConfig.useThreadToDoReport = useThreadToDoReport;
	}

	public static String HttpsRequestClassName = "com.tencent.common.HttpsRequest";

	public static void setKey(String key) {
		WxpayConfig.key = key;
	}

	public static void setAppID(String appID) {
		WxpayConfig.appID = appID;
	}

	public static void setAppSECRET(String appSECRET) {
		WxpayConfig.appSECRET = appSECRET;
	}

	public static void setMchID(String mchID) {
		WxpayConfig.mchID = mchID;
	}

	public static void setSubMchID(String subMchID) {
		WxpayConfig.subMchID = subMchID;
	}

	public static void setCertLocalPath(String certLocalPath) {
		WxpayConfig.certLocalPath = certLocalPath;
	}

	public static void setCertPassword(String certPassword) {
		WxpayConfig.certPassword = certPassword;
	}

	public static void setIp(String ip) {
		WxpayConfig.ip = ip;
	}

	public static String getKey() {
		return key;
	}

	public static String getAppid() {
		return appID;
	}

	public static String getAppSECRET() {
		return appSECRET;
	}

	public static String getMchid() {
		return mchID;
	}

	public static String getSubMchid() {
		return subMchID;
	}

	public static String getCertLocalPath() {
		return certLocalPath;
	}

	public static String getCertPassword() {
		return certPassword;
	}

	public static String getIP() {
		return ip;
	}

	public static void setHttpsRequestClassName(String name) {
		HttpsRequestClassName = name;
	}

	public static String getString(String key) {
		Properties props = new Properties();
		try {// \tmp0\wtpwebapps\ec-mall\WEB-INF\classes\alipay.properties
				// (系统找不到指定的文件。)
			InputStream in = new BufferedInputStream(new FileInputStream((getAppPath() + "wxpay.properties")));
			props.load(in);
			String value = props.getProperty(key);
			return value;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 获取应用路径
	 * 
	 * @return String
	 * @throws UnsupportedEncodingException
	 */
	public static String getAppPath() throws UnsupportedEncodingException {
		String configPath = WxpayConfig.class.getClassLoader().getResource("/").getPath();
		return java.net.URLDecoder.decode(configPath, "utf-8");
	}

}
