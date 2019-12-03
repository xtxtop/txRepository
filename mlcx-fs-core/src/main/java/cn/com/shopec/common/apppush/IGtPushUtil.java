package cn.com.shopec.common.apppush;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

import com.gexin.rp.sdk.http.IGtPush;

import cn.com.shopec.common.pay.aliPay.AlipayConfig;

public class IGtPushUtil {

	private static final String appId;
	
	private static final String appKey;
	
	private static final String masterSecret;
	
	private static final String url;
	
	static {
		Properties prop = getMailProperties();
		appId = prop.getProperty("iPush.appId");
		appKey = prop.getProperty("iPush.appKey");
		masterSecret = prop.getProperty("iPush.masterSecret");
		url = prop.getProperty("iPush.url");
		
	    // 配置群推时返回每个用户返回用户状态，可选
		// System.setProperty("gexin_pushList_needDetails", "true");
		// 配置群推时返回每个别名及其对应cid的用户状态，可选
		// System.setProperty("gexin_pushList_needAliasDetails", "true");
	}
	/**
	 * 解析配置文件
	 * @return
	 */
	private static Properties getMailProperties(){
		InputStream inStream = null ;
		Properties prop = new Properties();
		try {
			File file = new File(getAppPath()+"iPush.properties");
			inStream = new BufferedInputStream(new FileInputStream(file));
			prop.load(inStream);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			if (null != inStream){
				try {
					inStream.close();
					inStream = null;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return prop;
	}
	
	/**
	 * 获取应用路径
	 * 
	 * @return String
	 * @throws UnsupportedEncodingException
	 */
	private static String getAppPath() throws UnsupportedEncodingException {
		String configPath = AlipayConfig.class.getClassLoader()
				.getResource("/").getPath();
		return java.net.URLDecoder.decode(configPath, "utf-8");
	}

	public static String getAppId() {
		return appId;
	}

	public static String getAppKey() {
		return appKey;
	}

	public static String getMasterSecret() {
		return masterSecret;
	}

	public static String getUrl() {
		return url;
	}
	 
	public static IGtPush getPush() {
		return  new IGtPush(url, appKey, masterSecret);
	}
	
}
