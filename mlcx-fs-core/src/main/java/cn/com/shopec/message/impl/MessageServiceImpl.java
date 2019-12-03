package cn.com.shopec.message.impl;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.Properties;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import cn.com.shopec.common.sendmsg.HyInterface;
import cn.com.shopec.delivery.common.DeliveryCommonImpl;



@Component("messageServiceImpl")
public class MessageServiceImpl extends HyInterfaceImpl{
	
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
					(getPath() + "message.properties")));
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
	
	/**
	 * 这块写内容
	 */
	public String sendMsg(String mobile,String content){
		try {
			String userid = getString("userid");
			String account = getString("account");
			String password = getString("password");
			String url = getString("sendUrl");
			
//			String url = "http://hy6.nbark.com:7602/sms.aspx";
			StringBuilder builder = new StringBuilder();
			builder.append("action=send&userid=")
				.append(userid)
				.append("&account=")
				.append(account)
				.append("&password=")
				.append(password)
				.append("&mobile=")
				.append(mobile)
				.append("&content=")
				.append(content);
			
//			String param = "action=send&userid=497&account=yijiankanghy6&password=eY40iLia&mobile="+mobile+"&content="+content;
			String msgResult = doPost(url,builder.toString());
			return msgResult;
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public int sendMsgState(String stateNo, String phoneNo, String content) {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public int getRequest2(String phoneNo, String tpl_value, String tpl_id,String APPKEY, String urls) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	
	
}
