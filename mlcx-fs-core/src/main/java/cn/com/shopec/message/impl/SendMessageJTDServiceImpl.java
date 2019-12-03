package cn.com.shopec.message.impl;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import cn.com.shopec.core.system.model.SendSms;
import cn.com.shopec.core.system.model.SmsTemplate;
import cn.com.shopec.core.system.service.SendSmsService;
import cn.com.shopec.core.system.service.SmsTemplateService;



/**
 * 聚通达短信发送接口实现
 */
//@Component("sendMessageJTDServiceImpl")
public class SendMessageJTDServiceImpl extends SendMsgCommonInterfaceServiceImpl{
	@Resource
	private SmsTemplateService smsTemplateService;
	@Resource
	private SendSmsService sendsmsService;
	
	@Override
	public boolean sendMsgGet(String phone, String content,String tplId) {

		boolean flag = false;//发送成功标志
		try{
			String uid=getString("uid");
			String password=getString("upwd");
			String url = getString("usendUrl");
			String inscribed = getString("inscribed");
			//获取短信模板
			SmsTemplate st = null;
			if(null!=tplId&&!"".equals(tplId)){
				st = smsTemplateService.getSmsTemplateByTemplateType(Integer.parseInt(tplId));
				content = getSmsTemplateTypeContent(st,content);
			}
			//先用encode中的格式对短信内容进行编码，再对编码后的字符串进行base64加密，得到的内容作为content
			Map<String, String> params = new LinkedHashMap<String, String>();//请求参数
			params.put("uid", uid);
			params.put("password", getMD5(password));//原始密码做MD5加密，32位大写格式
			params.put("mobile", phone);
			params.put("content",Base64.getEncoder().encodeToString((inscribed+content).getBytes("utf-8")));//先用encode中定义的格式编码，再用base64加密内容
			params.put("encodeType","base64");
			
			String sendParams = convertMapParamToStrParams(params);
			String msgResult = doGet(url,sendParams);
			System.err.println("发送结果为--------------------------------------"+msgResult);
			if(!msgResult.contains("-")){
				flag=true;
		   		System.err.println("恭喜，短信发送成功");
		   		try {
					// 将短信发送的内容存入后台的短信发送列表
					SendSms ss = new SendSms();
					ss.setMobilePhone(phone);
					ss.setSendTime(new Date());
					ss.setSmsContent(content);
					if(st!=null){
						ss.setTemplateId(Long.parseLong(st.getTemplateId()));
						ss.setTemplateType(st.getTemplateType());
					}
					sendsmsService.addSendSms(ss, null);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
		}catch (Exception e){
			e.printStackTrace();
		}
		
		return flag;
	}
	@Override
	public boolean sendMsgPost(String phone, String content,String tplId) {

		boolean flag = false;//发送成功标志
		try{
			String uid=getString("uid");
			String password=getString("upwd");
			String url = getString("usendUrl");
			String inscribed = getString("inscribed");
			//获取短信模板
			SmsTemplate st = null;
			if(null!=tplId&&!"".equals(tplId)){
				st = smsTemplateService.getSmsTemplateByTemplateType(Integer.parseInt(tplId));
				content = getSmsTemplateTypeContent(st,content);
			}
			//先用encode中的格式对短信内容进行编码，再对编码后的字符串进行base64加密，得到的内容作为content
			Map<String, String> params = new LinkedHashMap<String, String>();//请求参数
			params.put("uid", uid);
			params.put("password", getMD5(password));//原始密码做MD5加密，32位大写格式
			params.put("mobile", phone);
			params.put("content",Base64.getEncoder().encodeToString((inscribed+content).getBytes("utf-8")));//先用encode中定义的格式编码，再用base64加密内容
			params.put("encodeType","base64");
			
			String sendParams = convertMapParamToStrParams(params);
			String msgResult = doPost(url,sendParams);
			System.err.println("发送结果为--------------------------------------"+msgResult);
			if(!msgResult.contains("-")){
				flag=true;
		   		System.err.println("恭喜，短信发送成功");
		   		try {
					// 将短信发送的内容存入后台的短信发送列表
					SendSms ss = new SendSms();
					ss.setMobilePhone(phone);
					ss.setSendTime(new Date());
					ss.setSmsContent(content);
					if(st!=null){
						ss.setTemplateId(Long.parseLong(st.getTemplateId()));
						ss.setTemplateType(st.getTemplateType());
					}
					sendsmsService.addSendSms(ss, null);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
		}catch (Exception e){
			e.printStackTrace();
		}
		
		return flag;
	
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
	 * 替换短信模板中的变量内容
	 */
	public String getSmsTemplateTypeContent(SmsTemplate st,String content) {
		String emplateContent = st.getTemplateContent();
		String replaceStr = emplateContent.substring(emplateContent.indexOf("{"), emplateContent.lastIndexOf("}") + 1);
		if (content != null && !"".equals(content)) {
			emplateContent = emplateContent.replace(replaceStr, content);
		}
		return emplateContent;
	}
	public static void main(String[] args) {
		new SendMessageJTDServiceImpl().sendMsgGet("18706756052", "123456", "");
	}
	@Override
	public boolean sendMsgGet(String phone, String content, String tplId, String type) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean sendMsgPost(String phone, String content, String tplId, String type) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}
	
	
}
