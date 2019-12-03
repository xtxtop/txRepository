package cn.com.shopec.common.sendmsg;

import java.io.UnsupportedEncodingException;

public interface SimulationHttpInterface {

	public String doPostJson(String urlStr,String param);

	public String myPoint(String jsonParam);
	
	/**
	 * 获取短信接口的配置文件的路径
	 */
	public String getPath() throws UnsupportedEncodingException;
	
	/**
	 * 读取配置文件信息
	 * @return
	 */
	public String getString(String key);

	
}
