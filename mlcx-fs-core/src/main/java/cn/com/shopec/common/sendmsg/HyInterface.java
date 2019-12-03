package cn.com.shopec.common.sendmsg;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * 浙江筑望客户短信接口
 * @author Administrator
 *
 */
public interface HyInterface {
	/**
	 * 发送接口
	 */
	public String doPost(String urlStr,String param) throws IOException;
	
	/**
	 * 发送接口
	 */
	public String doGet(String urlStr,String param) throws IOException;
	
	/**
	 * 发送内容
	 * @param string 
	 */
	public String sendMsg(String mobile,String content);
	
	/**
	 * 获取短信接口的配置文件的路径
	 */
	public String getPath() throws UnsupportedEncodingException;
	
	/**
	 * 读取配置文件信息
	 * @return
	 */
	public String getString(String key);

	/**
	 *  发送内容
	 * @param stateNo  国家号
	 * @param phoneNo  手机号
	 * @param string   发送内容
	 * @return
	 */
	public int sendMsgState(String stateNo, String phoneNo, String content);
	
	/**
	 *  聚合发送内容
	 * 
	 * @param phoneNo  手机号
	 * @param string   发送内容
	 * @return 
	 * @return
	 */
	public int getRequest2(String phoneNo,String tpl_value,String tpl_id,String APPKEY,String urls);
}
