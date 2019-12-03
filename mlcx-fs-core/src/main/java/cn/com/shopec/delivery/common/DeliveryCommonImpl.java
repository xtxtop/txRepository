package cn.com.shopec.delivery.common;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

public abstract class DeliveryCommonImpl implements DeliveryInterface {
	
	@Override
	public String getPath() throws UnsupportedEncodingException {
		String configPath = DeliveryCommonImpl.class.getResource("/").getPath();
		return java.net.URLDecoder.decode(configPath, "utf-8");
	}

	@Override
	public String getString(String key) {
		InputStream in = null;
		try {
			Properties props = new Properties();
			in = new BufferedInputStream(new FileInputStream(
					(getPath() + "delivery.properties")));
			props.load(in);
			String value = props.getProperty(key);
			return value;
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			if (null != in){
				try {
					in.close();
					in = null;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}
}
