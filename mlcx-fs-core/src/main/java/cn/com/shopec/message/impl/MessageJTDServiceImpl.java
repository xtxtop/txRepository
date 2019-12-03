package cn.com.shopec.message.impl;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Properties;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Component;

import cn.com.shopec.delivery.common.DeliveryCommonImpl;



/**
 * 聚通达
 * @author Administrator
 *
 */
@Component("messageJTDServiceImpl")
public class MessageJTDServiceImpl extends HyInterfaceImpl{
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
	@Override
	public int sendMsgState(String stateNo, String phoneNo, String content) {
		int flag=1;//发送成功标志
		try{
//			//国内的接口
//			if("0086".equals(stateNo)){
//				String uid=getString("uid");
//				String password=getString("upwd");
//				String url = getString("usendUrl");
//				StringBuilder builder = new StringBuilder();
//				//先用encode中的格式对短信内容进行编码，再对编码后的字符串进行base64加密，得到的内容作为content
//				builder.append("uid=")
//					.append(uid)
//					.append("&password=")
//					.append(getMD5(password))
//					.append("&mobile=")
//					.append(phoneNo)
//					.append("&encode=")
//					.append("utf-8")
//					.append("&content=")
//					.append(Base64.getEncoder().encodeToString(content.getBytes("utf-8")))
//					.append("&encodeType=")
//					.append("base64");
//				String msgResult = doPost(url,builder.toString());
//				if(!msgResult.contains("-")){
//					flag=0;
//			   		System.err.println("恭喜，短信发送成功");
//				}
//				
//			}else{//国外的接口
//				String username=getString("username");
//				String pswd=getString("password");
//				String sign=DigestUtils.md5Hex(pswd+username+pswd);
//				String url = getString("sendUrl");
//				StringBuilder builder = new StringBuilder();
//				builder.append("username=")
//				.append(username)
//				.append("&sign=")
//				.append(sign)
//				.append("&mobile=")
//				.append(stateNo+phoneNo)
//				.append("&content=")
//				.append(content)
//				.append("&stateNo=")
//				.append(stateNo);
//				String msgResult = doPost(url,builder.toString());
//				if(msgResult.contains("\"msg\":\"success\"")){
//					flag=0;
//			   		System.err.println("恭喜，短信发送成功");
//				}
//				
//			}
			String uid=getString("uid");
			String password=getString("upwd");
			String url = getString("usendUrl");
			StringBuilder builder = new StringBuilder();
			//先用encode中的格式对短信内容进行编码，再对编码后的字符串进行base64加密，得到的内容作为content
			builder.append("uid=")
				.append(uid)
				.append("&password=")
				.append(getMD5(password))
				.append("&mobile=")
				.append(phoneNo)
				.append("&encode=")
				.append("utf-8")
				.append("&content=")
				.append(Base64.getEncoder().encodeToString(content.getBytes("utf-8")))
				.append("&encodeType=")
				.append("base64");
			String msgResult = doPost(url,builder.toString());
			System.err.println("发送结果为--------------------------------------"+msgResult);
			if(!msgResult.contains("-")){
				flag=0;
		   		System.err.println("恭喜，短信发送成功");
			}
			
		}catch (Exception e){
			e.printStackTrace();
		}
		
		return flag;
	}
	public static void main(String[] args) {
		String msgResult="{\"code\":\"0\",\"msg\":\"successs\",\"msgid\":\"79ef97e8-89f5-4813-8c41-d0a820ffccc1\"}";
		if(msgResult.contains("\"msg\":\"success\"")){
			System.err.println("true");
		}
	}
	public static String getMD5(String sourceStr){
		String resultStr = "";
		try {
			byte[] temp = sourceStr.getBytes();
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			md5.update(temp);
			byte[] b = md5.digest();
			for (int i = 0; i < b.length; i++) {
				char[] digit = { '0', '1', '2', '3', '4', '5', '6', '7', '8',
						'9', 'A', 'B', 'C', 'D', 'E', 'F' };
				char[] ob = new char[2];
				ob[0] = digit[(b[i] >>> 4) & 0X0F];
				ob[1] = digit[b[i] & 0X0F];
				resultStr += new String(ob);
			}
			return resultStr;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 这块写内容
	 */
//	public String sendMsgState(String mobile,String content){
//		try {
//			String username=getString("username");
//			String pswd=getString("password");
//			
//			
////			String param = "action=send&userid=497&account=yijiankanghy6&password=eY40iLia&mobile="+mobile+"&content="+content;
//			String msgResult = doPost(url,builder.toString());
//			return msgResult;
//		} catch (MalformedURLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		return null;
//	}

	@Override
	public String sendMsg(String mobile, String content) {
		return null;
	}

	
	@Override
	public int getRequest2(String phoneNo, String tpl_value, String tpl_id,String APPKEY, String urls) {
		// TODO Auto-generated method stub
		return 0;
	}

	
	
}
