package cn.com.shopec.common.zmxy;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.antgroup.zmxy.openplatform.api.DefaultZhimaClient;
import com.antgroup.zmxy.openplatform.api.ZhimaApiException;
import com.antgroup.zmxy.openplatform.api.internal.util.RSACoderUtil;
import com.antgroup.zmxy.openplatform.api.request.ZhimaAuthEngineSmsauthRequest;
import com.antgroup.zmxy.openplatform.api.request.ZhimaAuthInfoAuthorizeRequest;
import com.antgroup.zmxy.openplatform.api.request.ZhimaAuthInfoAuthqueryRequest;
import com.antgroup.zmxy.openplatform.api.request.ZhimaCreditScoreGetRequest;
import com.antgroup.zmxy.openplatform.api.response.ZhimaAuthEngineSmsauthResponse;
import com.antgroup.zmxy.openplatform.api.response.ZhimaAuthInfoAuthqueryResponse;
import com.antgroup.zmxy.openplatform.api.response.ZhimaCreditScoreGetResponse;

public class  ZhimaUtil {
	
    /**
     * 获取目标用户的open_id,没有openid,走授权
     * 返回参数response
     *  response.getAuthorized()//该用户是否已经授权
     *  response.getOpenId()//得到openId
     *  response.getErrorCode()//错误code
     *  response.getErrorMessage()//错误信息
     */
    public static ZhimaAuthInfoAuthqueryResponse getOpenId(String certNo,String name) {
        ZhimaAuthInfoAuthqueryRequest request = new ZhimaAuthInfoAuthqueryRequest();
        request.setIdentityType("2");// 必要参数 
        Map<String, String> identityParams = new HashMap<String, String>();
        identityParams.put("certNo",certNo); // 证件号码
        identityParams.put("name", name); // 姓名        
        identityParams.put("certType", "IDENTITY_CARD"); // 证件类型
        request.setIdentityParam(JSONObject.toJSONString(identityParams));
        DefaultZhimaClient client = getDefaultZhimaClient();
        try {
            ZhimaAuthInfoAuthqueryResponse response = client.execute(request);
            return response;
        } catch (ZhimaApiException e) {
            e.printStackTrace();
        }
        return null;
    }

	private static DefaultZhimaClient getDefaultZhimaClient() {
		DefaultZhimaClient client = new DefaultZhimaClient(ZhimaConfig.GATEWAY,ZhimaConfig.APPID,ZhimaConfig.CHARSET,ZhimaConfig.PRIKEY,ZhimaConfig.PUBKEY);
		return client;
	}
	    
    /**
     * 自动生成页面授权的url(身份证号,姓名)
     * @throws Exception
     */
    public static String getAuthorizePageUrl(String state,String certNo,String name) throws Exception {
        ZhimaAuthInfoAuthorizeRequest request = new ZhimaAuthInfoAuthorizeRequest();
        request.setChannel("apppc"); // PC端
        request.setPlatform("zmop"); // 开放平台
        request.setIdentityType("2");//按照身份证+姓名进行授权
        Map<String, String> identityParams = new HashMap<String, String>();
        identityParams.put("certNo",certNo); // 证件号码
        identityParams.put("name", name); // 姓名
        identityParams.put("certType", "IDENTITY_CARD"); // 证件类型
        request.setIdentityParam(JSONObject.toJSONString(identityParams));
        Map<String, String> bizParams = new HashMap<String, String>();
        bizParams.put("auth_code","M_H5"); // 证件号码
        bizParams.put("channelType", "app"); // 姓名
        bizParams.put("state", state); // 证件类型
        request.setBizParams(JSONObject.toJSONString(bizParams));
        
        DefaultZhimaClient client = getDefaultZhimaClient();
        String pageAuthUrl = client.generatePageRedirectInvokeUrl(request);
        return pageAuthUrl;
    }
    
    
    /**
     * 查询芝麻分
     * 返回参数response
     *  response.getAuthorized()//该用户是否已经授权
     *  response.getZmScore()//得到信用分
     *  response.getErrorCode()//错误code
     *  response.getErrorMessage()//错误信息
     */
    public static ZhimaCreditScoreGetResponse queryScore(String openId,String transactionId) throws ZhimaApiException {
        ZhimaCreditScoreGetRequest request = new ZhimaCreditScoreGetRequest();
        request.setPlatform("zmop"); // 开放平台,zmop代表芝麻开放平台
        request.setChannel("apppc"); // pc端
        //transactionId，该标记是商户每次请求的唯一标识。建议使用uuid进行传递，
        // creditScoreGetRequest.setTransactionId(UUID.randomUUID().toString());
        request.setTransactionId(transactionId);
        request.setProductCode("w1010100100000000001"); // 商户配置那块儿的产品Code
        request.setOpenId(openId); // appid,每个人的标识
        DefaultZhimaClient client = getDefaultZhimaClient();
        // 如果正常返回,直接在对象里面获取结果值
        ZhimaCreditScoreGetResponse creditScoreGetResponse = client.execute(request);
        return creditScoreGetResponse;
    }
    
    /**
     * 处理回调后的参数,然后解密params
     * @throws Exception
     */
    public static String parseFromReturnUrl(String url) throws Exception {
        int index = url.indexOf("?");
        String urlParamString = url.substring(index + 1);
        String[] paraPairs = urlParamString.split("&");
        String encryptedParam = "";
        for (String paramPair : paraPairs) {
            String[] splits = paramPair.split("=");
            if ("params".equals(splits[0])) {
                encryptedParam = splits[1];
            }
        }
        String decryptedParam = RSACoderUtil.decrypt(URLDecoder.decode(encryptedParam, ZhimaConfig.CHARSET),ZhimaConfig.PRIKEY, ZhimaConfig.CHARSET);
        //通过浏览器返回时，不需要decode
        decryptedParam = URLDecoder.decode(decryptedParam, ZhimaConfig.CHARSET);
        return decryptedParam;
    }
    
    /**
     * 解析授权返回参数
     * @param params
     * @param sign
     * @return
     * @throws Exception
     */
    public static Map<String,String> parseFromReturn(String params,String sign) throws Exception {
    	if(params.indexOf("%") != -1) {
            params = URLDecoder.decode(params, ZhimaConfig.CHARSET);
        }
        if(sign.indexOf("%") != -1) {
            sign = URLDecoder.decode(sign, ZhimaConfig.CHARSET);
        }
		DefaultZhimaClient client = getDefaultZhimaClient();

		Map<String,String> map = new HashMap<String,String>();
        try {
            String result = client.decryptAndVerifySign(params, sign);
    		String[] stringArray = result.split("&");
    		for (String string : stringArray) {
    			String[] temp = string.split("=");
    			map.put(temp[0], temp[1]);
    		}
            return map;
        } catch (ZhimaApiException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    /**
     * 短信调用支付宝唤起芝麻授权
     * @param certNo
     * @param name
     * @param mobileNo
     * @param state
     * @throws ZhimaApiException 
     */
    public static ZhimaAuthEngineSmsauthResponse testZhimaCustomerCertificationInitialize(String certNo ,String name,String mobileNo,String state) throws ZhimaApiException {
    	 ZhimaAuthEngineSmsauthRequest request = new ZhimaAuthEngineSmsauthRequest();
         request.setChannel("apppc");
         request.setPlatform("zmop");
         request.setIdentityType("2");// 必要参数 
         //request.setIdentityParam("{\"name\":\"张三\",\"certType\":\"IDENTITY_CARD\",\"certNo\":\"330100xxxxxxxxxxxx\",\"mobileNo\":\"15158657683\"}");// 必要参数 
         request.setBizParams("{\"auth_code\":\"M_SMS\",\"channelType\":\"windows\",\"state\":\"abcd\"}");// 必要参数 
        
         
         Map<String, String> identityParams = new HashMap<String, String>();
         identityParams.put("certNo",certNo); // 证件号码
         identityParams.put("name", name); // 姓名
         identityParams.put("mobileNo", mobileNo); // 手机号码
         identityParams.put("certType", "IDENTITY_CARD"); // 证件类型
         request.setIdentityParam(JSONObject.toJSONString(identityParams));
         
         Map<String, String> bizParams = new HashMap<String, String>();
         bizParams.put("auth_code", "M_SMS"); // 
         bizParams.put("channelType", "windows"); //
         bizParams.put("state", "abcd"); //
         request.setBizParams(JSONObject.toJSONString(bizParams));

         DefaultZhimaClient client = getDefaultZhimaClient();
         return (ZhimaAuthEngineSmsauthResponse)client.execute(request);
    }
}
