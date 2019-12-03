package cn.com.shopec.common.pay.aliPay;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Properties;
/* *
 *类名：AlipayConfig
 *功能：基础配置类
 *详细：设置帐户有关信息及返回路径
 *版本：3.3
 *日期：2012-08-10
 *说明：
 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 *该代码仅供学习和研究支付宝接口使用，只是提供一个参考。
	
 *提示：如何获取安全校验码和合作身份者ID
 *1.用您的签约支付宝账号登录支付宝网站(www.alipay.com)
 *2.点击“商家服务”(https://b.alipay.com/order/myOrder.htm)
 *3.点击“查询合作者身份(PID)”、“查询安全校验码(Key)”

 *安全校验码查看时，输入支付密码后，页面呈灰色的现象，怎么办？
 *解决方法：
 *1、检查浏览器配置，不让浏览器做弹框屏蔽设置
 *2、更换浏览器或电脑，重新登录查询。
 */

public class AlipayConfig {
	// 支付宝分配给开发者的应用ID
	public static String appId = getString("alipay.appId");

	// 合作身份者ID，签约账号，以2088开头由16位纯数字组成的字符串，查看地址：https://b.alipay.com/order/pidAndKey.htm
	public static String partner = getString("alipay.partner");

	// 收款支付宝账号，以2088开头由16位纯数字组成的字符串，一般情况下收款账号就是签约账号
	public static String seller_user_id = partner;

	// MD5密钥，安全检验码，由数字和字母组成的32位字符串，查看地址：https://b.alipay.com/order/pidAndKey.htm
	public static String key = getString("alipay.key");

	// 服务器异步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	// public static String notify_url =
	// "http://商户网址/create_direct_pay_by_user-JAVA-UTF-8/notify_url.jsp";
	public static String notify_url = getString("alipay.notify_url");
	// 充值记录支付宝回调地址
	public static String notify_url_a = getString("alipay.notify_url_a");
	// 充电、地锁订单异步通知跳转路径
	public static String notify_url_1 = getString("alipay.notify_url_1");

	public static String deposit_notify_url = getString("alipay.deposit_notify_url");

	public static String pricingPack_notify_url = getString("alipay.pricingPack_notify_url");
	// 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	public static String return_url = getString("alipay.return_url");
	// public static String return_url =
	// "http://127.0.0.1:9000/create_direct_pay_by_user-JAVA-UTF-8/return_url.jsp";

	// 签名方式
	public static String sign_type = "MD5";

	// 调试用，创建TXT日志文件夹路径，见AlipayCore.java类中的logResult(String sWord)打印方法。
	public static String log_path = "C:\\";

	// 字符编码格式 目前支持 gbk 或 utf-8
	public static String input_charset = "utf-8";
	// 调用的接口名，无需修改
	public static String service = "refund_fastpay_by_platform_pwd";

	// 收款支付宝账号
	public static String seller_email = getString("alipay.seller_email");

	// 收款支付宝账号，以2088开头由16位纯数字组成的字符串，一般情况下收款账号就是签约账号
	public static String seller_id = getString("alipay.seller_id");
	// 商户私钥，pkcs8格式
	public static String rsa_private = getString("alipay.rsa_private");
	// 支付宝公钥
	public static String rsa_public = getString("alipay.rsa_public");

	//////////////////////////////////////////////////////////////////////
	/** 支付宝退款所需参数 开始 */
	// 调用的接口名，无需修改
	public static String refundService = "refund_fastpay_by_platform_pwd";
	// 服务器异步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	public static String refund_notify_url = getString("alipay.refund_notify_url");
	// 退款日期 时间格式 yyyy-MM-dd HH:mm:ss
	public static String refund_date = UtilDate.getDateFormatter();

	/** 支付宝退款所需参数 结束 */

	public static String getString(String key) {
		Properties props = new Properties();
		try {// \tmp0\wtpwebapps\ec-mall\WEB-INF\classes\alipay.properties
				// (系统找不到指定的文件。)
			InputStream in = new BufferedInputStream(new FileInputStream((getAppPath() + "alipay.properties")));
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
		String configPath = AlipayConfig.class.getClassLoader().getResource("/").getPath();
		return java.net.URLDecoder.decode(configPath, "utf-8");
	}

}
