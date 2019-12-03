package cn.com.shopec.message.impl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.annotation.Resource;

import com.alibaba.fastjson.JSON;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import cn.com.shopec.core.system.model.SendSms;
import cn.com.shopec.core.system.service.SendSmsService;


/**
 * 聚合短信接口实现
 *
 */
//@Component("sendMessageJHServiceImpl")
public class SendMessageJHServiceImpl extends SendMsgCommonInterfaceServiceImpl {
	@Resource
	private SendSmsService sendsmsService;
	@Override
	public boolean sendMsgGet(String phone, String content, String tplId) throws UnsupportedEncodingException {

		boolean flag = false;// 发送成功标志

		String appKey = getString("APPKEY");
		String sendUrl = getString("urls");
		String inscribed = getString("inscribed");
		String result = null;
		Map<String, String> params = new LinkedHashMap<String, String>();// 请求参数
		params.put("inscribed", inscribed);// 运营商签名，如：万众出行
		params.put("mobile", phone);// 接收短信的手机号码
		params.put("tpl_id", tplId);// 短信模板ID，请参考个人中心短信模板设置
		params.put("tpl_value", inscribed+URLEncoder.encode("#code#="+content,"UTF-8"));// 变量名和变量值对。如果你的变量名或者变量值中带有#&=中的任意一个特殊符号，请先分别进行urlencode编码后再传递，<a href="http://www.juhe.cn/news/index/id/50" target="_blank">详细说明></a>
		params.put("key", appKey);// 应用APPKEY(应用详细页查询)
		params.put("dtype", "json");// 返回数据的格式,xml或json，默认json
		try {
			String sendParams = convertMapParamToStrParams(params);
			// response = sendSmsByPost(url, requestJson);
				result = doPost(sendUrl, sendParams);
				//JSONObject object = JSONObject.fromObject(result);
				String requestJson = JSON.toJSONString(sendParams);
				//response.contains("\"error_code\":\"0\"")
				if (requestJson.contains("\"error_code\":\"0\"")) {
				flag = true;
				
				try {
					// 将短信发送的内容存入后台的短信发送列表
					SendSms ss = new SendSms();
					ss.setMobilePhone(phone);
					ss.setSendTime(new Date());
					ss.setSmsContent(content);
					ss.setTemplateType(Integer.parseInt(tplId));
					sendsmsService.addSendSms(ss, null);
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				flag = false;
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public boolean sendMsgPost(String phone, String content, String tplId) throws UnsupportedEncodingException {

		boolean sate = false;// 发送成功标志sate

		String appKey = getString("APPKEY");
		String sendUrl = getString("urls");
		String result = null;
		Map<String, String> params = new LinkedHashMap<String, String>();// 请求参数
		params.put("mobile", phone);// 接收短信的手机号码
		params.put("tpl_id", tplId);// 短信模板ID，请参考个人中心短信模板设置
		params.put("tpl_value",URLEncoder.encode("#code#="+content,"UTF-8"));// 变量名和变量值对。如果你的变量名或者变量值中带有#&=中的任意一个特殊符号，请先分别进行urlencode编码后再传递，<a href="http://www.juhe.cn/news/index/id/50" target="_blank">详细说明></a>
		params.put("key", appKey);// 应用APPKEY(应用详细页查询)
		params.put("dtype", "json");// 返回数据的格式,xml或json，默认json
		try {
			String sendParams = convertMapParamToStrParams(params);
			result = doPost(sendUrl, sendParams);
			String requestJson = JSON.toJSONString(result);
			JsonParser parser=new JsonParser();
			JsonObject allObject = parser.parse(result).getAsJsonObject();
			if (allObject.get("error_code").toString().equals("0")) {
			System.out.println(requestJson);
				sate = true;
				
				try {
					// 将短信发送的内容存入后台的短信发送列表
					SendSms ss = new SendSms();
					ss.setMobilePhone(phone);
					ss.setSendTime(new Date());
					ss.setSmsContent(content);
					ss.setTemplateType(Integer.parseInt(tplId));
					sendsmsService.addSendSms(ss, null);
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				sate = false;
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sate;
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

	public static void main(String[] args) throws UnsupportedEncodingException {
		System.err.println(new SendMessageJHServiceImpl().sendMsgGet("18706756052", "111111", "34963"));
	}
}
