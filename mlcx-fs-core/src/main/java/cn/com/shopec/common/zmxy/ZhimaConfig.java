package cn.com.shopec.common.zmxy;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

public class ZhimaConfig {
	 
	//芝麻开放平台地址
    public static final String GATEWAY  = getString("zhima.gateway");
    //芝麻商户应用 Id
    public static final  String APPID  = getString("zhima.appid");
    //芝麻商户 RSA 私钥
    public static final  String PRIKEY  = getString("zhima.prikey");
    //芝麻 RSA 公钥
    public static final  String PUBKEY = getString("zhima.pubkey");
    //统一字符集
    public static final String CHARSET  = getString("zhima.charset");
	 
	public static String getString(String key) {
		Properties props = new Properties();
		try {
			InputStream in = new BufferedInputStream(new FileInputStream(
					(getAppPath() + "zhima.properties")));
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
		String configPath = ZhimaConfig.class.getClassLoader()
				.getResource("/").getPath();
		return java.net.URLDecoder.decode(configPath, "utf-8");
	}

}
