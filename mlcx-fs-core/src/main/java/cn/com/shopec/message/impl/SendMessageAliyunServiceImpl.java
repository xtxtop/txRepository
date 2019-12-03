package cn.com.shopec.message.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

import cn.com.shopec.common.utils.AliyunHttpUtils;

public class SendMessageAliyunServiceImpl extends SendMsgCommonInterfaceServiceImpl {

	public static final String HOST = "http://sms.market.alicloudapi.com";

	public static final String PATH = "/singleSendSms";

	public static final String METHOD = "GET";
    
    //阿里云短信APPCODE
    private String APPCODE = getString("aliyun_app_code");

	
	@Override
	public boolean sendMsgGet(String phone, String content, String tplId) throws Exception {
	    Map<String, String> headers = new HashMap<String, String>();
	    //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
	    headers.put("Authorization", "APPCODE " + APPCODE);
	    Map<String, String> querys = new HashMap<String, String>();
	    String paramString = "{\"code\":\""+content+"\"}";
	    querys.put("ParamString", paramString);
	    querys.put("RecNum", phone);
	    querys.put("SignName", "TOX途客");
	    querys.put("TemplateCode", tplId);


	    try {
	    	/**
	    	* 重要提示如下:
	    	* HttpUtils请从
	    	* https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java
	    	* 下载
	    	*
	    	* 相应的依赖请参照
	    	* https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
	    	*/
	    	HttpResponse response = AliyunHttpUtils.doGet(HOST, PATH, METHOD, headers, querys);
	    	if(EntityUtils.toString(response.getEntity()).indexOf("true") != -1){
	    		return true;
	    	}
	    } catch (Exception e) {
	    	e.printStackTrace();
	    }
		return false;
	}

	@Override
	public boolean sendMsgPost(String phone, String content, String tplId) throws Exception {
		return sendMsgGet(phone, content, tplId);
	}

	@Override
	public boolean sendMsgGet(String phone, String content, String tplId, String type) throws Exception {
		return false;
	}

	@Override
	public boolean sendMsgPost(String phone, String content, String tplId, String type) throws Exception {
		return false;
	}

}
