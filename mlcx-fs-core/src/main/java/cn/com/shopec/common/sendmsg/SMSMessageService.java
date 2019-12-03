package cn.com.shopec.common.sendmsg;

import java.io.IOException;

/**
 * 短信推送接口(浪驰)
 * @author Administrator
 *
 */
public interface SMSMessageService {
	
	/**
	 * 登录
	 * @throws IOException 
	 */
	public String login() throws IOException;
	
	/**
	 * 发送单条短信  -- 登录有发送
	 * @throws IOException 
	 */
	public String sendOneMsg(String phone) throws IOException;
	
	/**
	 * 简易版发送单条短信	--
	 * @throws IOException 
	 */
	public String easySendOne(String phone,String content) throws IOException;
	
	/**
	 * 短信群发
	 * @throws IOException 
	 * @throws Exception 
	 */
	public String sendManyMsg(String[] phones) throws Exception;
	
	
	/**
	 * 发送单条短信	--
	 * @throws IOException 
	 */
	public  int sendOneSMS(String phone,String content) throws IOException;
	

}
