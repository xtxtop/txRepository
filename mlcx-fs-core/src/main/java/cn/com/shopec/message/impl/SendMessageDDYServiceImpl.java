package cn.com.shopec.message.impl;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.lang3.StringUtils;

import cn.com.shopec.core.system.model.SendSms;
import cn.com.shopec.core.system.model.SmsTemplate;
import cn.com.shopec.core.system.service.SendSmsService;
import net.sf.json.JSONObject;

/**
 * 叮咚云短信接口实现
 *
 */
//@Component("sendMessageDDYServiceImpl")
public class SendMessageDDYServiceImpl extends SendMsgCommonInterfaceServiceImpl {
	@Resource
	private SendSmsService sendsmsService;
	 // 查账户信息的http地址
    private static String URL_GET_USER_INFO = "https://api.dingdongcloud.com/v1/sms/userinfo";
 
    // 查询账户余额的http地址
    private static String URL_GET_BALANCE = "https://api.dingdongcloud.com/v1/sms/querybalance";
 
    // 验证码短信发送接口的http地址
    private static String URL_SEND_YZM = "https://api.dingdongcloud.com/v1/sms/sendyzm";
 
    private static String URL_SEND_YYYZM = "https://api.dingdongcloud.com/v1/sms/sendyyyzm";
 
    // 通知短信发送接口的http地址
    private static String URL_SEND_TZ = "https://api.dingdongcloud.com/v1/sms/sendtz";
 
    // 营销短信发送接口的http地址
    private static String URL_SEND_YX = "https://api.dingdongcloud.com/v1/sms/sendyx";
 
    // 模板获取
    private static String URL_GET_TPL = "https://api.dingdongcloud.com/v1/template/get";
 
    // 模板添加
    private static String URL_ADD_TPL = "https://api.dingdongcloud.com/v1/template/add";
 
    // 获取营销短信审核状态
    private static String URL_VERIFY_STATUS_YX = "https://api.dingdongcloud.com/v1/verify/add";
 
    // 编码格式。发送编码格式统一用UTF-8
    private static String ENCODING = "UTF-8";
    
    //短息平台apikey
    private String api_key = getString("apikey");
    
 
    /**
     * 查询账户信息接口
     * 
     * @param apikey
     * @return
     */
    public static String getUserInfo(String apikey) {
 
        NameValuePair[] data = { new NameValuePair("apikey", apikey) };
        return doPost(URL_GET_USER_INFO, data);
    }
 
    /**
     * 查询账户余额接口
     * 
     * @param apikey
     * @return
     */
    public static String getBalance(String apikey) {
 
        NameValuePair[] data = { new NameValuePair("apikey", apikey) };
        return doPost(URL_GET_BALANCE, data);
    }
 
    /**
     * 发送验证码短信
     * 
     * @param apikey
     *            apikey
     * @param mobile
     *            手机号码(唯一，不许多个)
     * @param content
     *            短信发送内容（必须经过utf-8格式编码)
     * @return json格式字符串
     */
    public static String sendYzm(String apikey, String mobile, String content) {
 
        if (StringUtils.isNotBlank(content)) {
            try {
                content = URLEncoder.encode(content, ENCODING);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
 
        NameValuePair[] data = { new NameValuePair("apikey", apikey),
 
        new NameValuePair("mobile", mobile),
 
        new NameValuePair("content", content) };
 
        String result =  doPost(URL_SEND_YZM, data);
        return result;
    }
    
    /**
     * 发送语音验证码
     * 
     * @param apikey
     * @param mobile
     *            手机号码(唯一，不许多个)
     * @param content
     *            短信发送内容(必须纯数字4-6位)
     * @return
     */
    public static String sendYyYzm(String apikey, String mobile, String content) {
 
        NameValuePair[] data = { new NameValuePair("apikey", apikey),
 
        new NameValuePair("mobile", mobile),
 
        new NameValuePair("content", content) };
 
        return doPost(URL_SEND_YYYZM, data);
    }
 
 
    /**
     * 发送通知短信
     * 
     * @param apikey
     *            apikey
     * @param mobile
     *            手机号码（多个号码用英文半角逗号分开，最多可提交1000个）
     * @param content
     *            短信发送内容（必须经过utf-8格式编码)
     * @return json格式字符串
     */
    public static String sendTz(String apikey, String mobile, String content) {
 
        if (StringUtils.isNotBlank(content)) {
            try {
                content = URLEncoder.encode(content, ENCODING);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
 
        NameValuePair[] data = { new NameValuePair("apikey", apikey),
 
        new NameValuePair("mobile", mobile),
 
        new NameValuePair("content", content) };
 
        String result = doPost(URL_SEND_TZ, data);
        return result;
    }
 
    /**
     * 发送营销短信
     * 
     * @param apikey
     *            apikey
     * @param mobile
     *            手机号码（多个号码用英文半角逗号分开，最多可提交1000个）
     * @param content
     *            短信发送内容（必须经过utf-8格式编码，短信末尾必须带有“退订回T”）
     * @return json格式字符串
     */
    public static String sendYx(String apikey, String mobile, String content) {
 
        if (StringUtils.isNotBlank(content)) {
            try {
                content = URLEncoder.encode(content, ENCODING);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
 
        NameValuePair[] data = { new NameValuePair("apikey", apikey),
 
        new NameValuePair("mobile", mobile),
 
        new NameValuePair("content", content) };
 
        return doPost(URL_SEND_YX, data);
    }
 
    /**
     * 获取营销短信审核状态
     * @param apikey
     * @param ser_no
     *              短信批次号
     * @return
     */
     public static String getYxStatus(String apikey, String ser_no){
        NameValuePair[] data = {new NameValuePair("apikey", apikey),
                new NameValuePair("ser_no",ser_no),};
        return doPost(URL_VERIFY_STATUS_YX, data);
    }
 
    /**
     * 获取模板
     * @param apikey
     * @param tpl_id  
     *              模板ID
     * @param type
     *              模板类型
     * @return
     */
    public static String getTpl(String apikey,String tpl_id, String type){
        NameValuePair[] data = {new NameValuePair("apikey", apikey),
                new NameValuePair("tpl_id",tpl_id),
                new NameValuePair("type",type)};
        return doPost(URL_GET_TPL, data);
 
    }
 
    /**
     * 模板添加
     * @param apikey
     * @param tplContent
     *              模板内容
     * @param type
     *              模板类型
     * @return
     */
    public static String addTpl(String apikey,String tplContent,String type){
 
        NameValuePair[] data = {new NameValuePair("apikey", apikey),
                new NameValuePair("content",tplContent),
                new NameValuePair("type",type)};
        return doPost(URL_ADD_TPL, data);
    }
 
    /**
     * 基于HttpClient的post函数
     * 
     * @param url
     *            提交的URL
     * 
     * @param data
     *            提交NameValuePair参数
     * @return 提交响应
     */
    private static String doPost(String url, NameValuePair[] data) {
 
        HttpClient client = new HttpClient();
        PostMethod method = new PostMethod(url);
        // method.setRequestHeader("ContentType",
        // "application/x-www-form-urlencoded;charset=UTF-8");
        method.setRequestBody(data);
       // client.getParams().setContentCharset("UTF-8");
        client.getParams().setConnectionManagerTimeout(10000);
        try {
            client.executeMethod(method);
            return method.getResponseBodyAsString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

	@Override
	public boolean sendMsgGet(String phone, String content, String tplId) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean sendMsgPost(String phone, String content, String tplId) throws Exception {
		return false;
	}

	/**
	 * 短信发送接口，根据type来区分不同的流程类型
	 * type 1
	 */
	@Override
	public boolean sendMsgGet(String phone, String content, String tplId, String type) throws Exception {
		this.sendMsgPost(phone, content, tplId, type);
		return false;
	}

	@Override
	public boolean sendMsgPost(String phone, String content, String tplId, String type) throws Exception {
		
		//发送结果
		boolean res = false;
		String result = "";
		//组织发送内容
		StringBuffer buffer = new StringBuffer();
		//获取商户签名
		String sign = getString("shopsign");
		//获取短信发送模板
		String mob = "";
		switch (type) {
		case "1":	//发送验证码
			mob = getString("yzmContent");
			//替换模板中的内容
			mob = changeMobContent(mob,content);
			buffer.append(sign).append(mob);
			result = sendYzm(api_key, phone, buffer.toString());
			break;
		
		case "2":	// 发送通知信息
			mob = getString("tzContent");
			//替换模板中的内容
			mob = changeMobContent(mob,content);
			buffer.append(sign).append(mob);
			result = sendTz(api_key, phone, buffer.toString());
			break;

		default:	//什么都不做
			break;
		}
		
		System.err.println("send result ----- >"+ result);
		if(null == result || "".equals(result)){
			res = false;
		}else{
			JSONObject object = JSONObject.fromObject(result);
			String code = object.getString("code");
			if("1".equals(code)){
				res = true;
				
				// 将短信发送的内容存入后台的短信发送列表
				try {
					SendSms ss = new SendSms();
					ss.setMobilePhone(phone);
					ss.setSendTime(new Date());
					ss.setSmsContent("content:"+content+";result:"+object.get("result"));
					ss.setTemplateType(Integer.parseInt(type));
					sendsmsService.addSendSms(ss, null);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
		}
		return res;
	}
	
	
	/**
	 * 替换短信模板中的变量内容
	 */
	public String changeMobContent(String mob,String content) {
		String replaceStr = mob.substring(mob.indexOf("{"), mob.lastIndexOf("}") + 1);
		if (content != null && !"".equals(content)) {
			mob = mob.replace(replaceStr, content);
		}
		return mob;
	}
	
}
