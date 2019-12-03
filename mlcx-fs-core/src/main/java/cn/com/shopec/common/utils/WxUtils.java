package cn.com.shopec.common.utils;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Formatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

public class WxUtils {
	private static ECPropertiesUtil pu = new ECPropertiesUtil();

	static {
		pu.load(WxUtils.class.getClassLoader().getResourceAsStream("wx.properties"));
	}

	public static String appid = getString("wxapi.appid");

	public static String secret = getString("wxapi.secret");

	public static String accesstokenurl = getString("wxapi.accesstokenurl");

	public static String ticketurl = getString("wxapi.ticketurl");

	public static String appurl = getString("wxapi.appurl");

	public static String deviceidurl = getString("wxapi.deviceidurl");
	
	public static String authorizedeviceurl = getString("wxapi.authorizedeviceurl");

	public static String brandusername = getString("wxapi.brandusername");

	public static String productid = getString("wxapi.productid");
	
	public static String oauth2codeurl = getString("wxapi.oauth2codeurl");
	
	public static String oauth2openidurl = getString("wxapi.oauth2openidurl");
	
	public static String deviceUnbindUrl = getString("wxapi.device.unbind.url");
	
	public static String deviceBindUrl = getString("wxapi.device.bind.url");
	
	public static String deviceCompelUnbindUrl = getString("wxapi.device.compelunbind.url");

	public static String deviceCompelBindUrl = getString("wxapi.device.compelbind.url");
	
	public static String getBindDeviceUrl = getString("wxapi.device.getbinddevice.url");
	

	public static String getString(String key) {
		return pu.getStringProperty(key);
	}

	public static Map<String, String> getAccessToken() {
		String url = accesstokenurl + "&appid=" + appid + "&secret=" + secret;
		System.out.print("url----------:"+url);
		String result = HttpURLRequestUtil.doGet(url, "utf-8", 1000);
		System.out.print("gettoken----------:"+result);
		System.err.print("gettoken2----------:"+result);
		Type type = new TypeToken<Map<String, String>>() {
		}.getType();
		Map<String, String> map = ECJsonUtils.fromJson(result, type);
		Map<String, String> resultMap = new HashMap<String, String>();
		resultMap.put("wx_access_token", map.get("access_token"));
		resultMap.put("wx_access_token_createtime", new Date().getTime() + "");
		resultMap.put("wx_access_token_expire_in", map.get("expires_in"));
		return resultMap;
	}

	public static String getJsapiTicket(String access_token) {
		String url = ticketurl + "&access_token=" + access_token;
		String result = HttpURLRequestUtil.doGet(url, "utf-8", 1000);
		System.out.println("getJsapiTicket="+result);
		Type type = new TypeToken<Map<String, String>>() {
		}.getType();
		Map<String, String> map = ECJsonUtils.fromJson(result, type);
		String ticket = null;
		if (map.get("errcode").equals("0")) {
			ticket = map.get("ticket");
		}
		return ticket;
	}

	public static Map<String, String> getDevice(String access_token) {
		String url = deviceidurl + "&access_token=" + access_token + "&product_id=" + productid;
		String result = HttpURLRequestUtil.doGet(url, "utf-8", 1000);
		JsonParser parser = new JsonParser();
		JsonObject allObject = parser.parse(result).getAsJsonObject();
		Map<String, String> map=new HashMap<String, String>();
		JsonObject resObject = allObject.get("base_resp").getAsJsonObject();
		if(resObject.get("errcode").toString().equals("0")){			
			map.put("deviceid",allObject.get("deviceid").getAsString());
			map.put("qrticket",allObject.get("qrticket").getAsString());
		}
		return map;
	}

	public static Map<String, String> sign(String ticket,String url) {
		Map<String, String> ret = new HashMap<String, String>();
		String noncestr = createNoncestr();
		String timestamp = createTimestamp();
		String str;
		String signature = "";

		// 注意这里参数名必须全部小写，且必须有序
		str = "jsapi_ticket=" + ticket + "&noncestr=" + noncestr + "&timestamp=" + timestamp + "&url=" + url;
		System.out.println(str);

		try {
			MessageDigest crypt = MessageDigest.getInstance("SHA-1");
			crypt.reset();
			crypt.update(str.getBytes("UTF-8"));
			signature = byteToHex(crypt.digest());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		ret.put("url", appurl);
		ret.put("jsapi_ticket", ticket);
		ret.put("nonceStr", noncestr);
		ret.put("timestamp", timestamp);
		ret.put("signature", signature);
		ret.put("appid", appid);
		ret.put("brandusername", brandusername);

		return ret;
	}
	
	public static Map<String, String>  getAuthorizeDevice(String access_token,String deviceId,String mac){
        String params="{\"device_num\":\"1\",\"device_list\":[{"
	                    +"\"id\":\""+deviceId+"\","
	                    +"\"mac\":\""+mac+"\","
//	                    +"\"connect_protocol\":\"2|1\","
	                    +"\"connect_protocol\":\"3\","
	                    +"\"auth_key\":\"\","
	                    +"\"close_strategy\":\"1\","
	                    +"\"conn_strategy\":\"1\","
	                    +"\"crypt_method\":\"0\","
	                    +"\"auth_ver\":\"0\","
	                    +"\"manu_mac_pos\":\"-1\","
	                    +"\"ser_mac_pos\":\"-2\","
	                    +"\"ble_simple_protocol\": \"0\""
//						+"\"ble_simple_protocol\": \"1\""
	                    + "}],"
	                    +"\"op_type\":\"1\""
	                    + "}";
                    
		String result = HttpURLRequestUtil.doPost(authorizedeviceurl + "access_token=" + access_token + "&product_id=" + productid, params);
		JsonParser parser = new JsonParser();
		JsonObject allObject = parser.parse(result).getAsJsonObject();
		JsonArray jsonArray = allObject.get("resp").getAsJsonArray();
		JsonObject resObject = jsonArray.get(0).getAsJsonObject();
		Map<String, String> map = new HashMap<String, String>();
		if (resObject.get("errcode").toString().equals("0")) {
			JsonObject infoObject = resObject.get("base_info").getAsJsonObject();
			map.put("deviceType", infoObject.get("device_type").getAsString());
			map.put("deviceId", infoObject.get("device_id").getAsString());
		}
		return map;
	}
		
	public static Map<String, String> getOauth2(String code) {
		String result = HttpURLRequestUtil.doGet(oauth2openidurl + "appid=" + appid + "&secret=" + secret + "&code="
				+ code + "&grant_type=authorization_code", "utf-8", 1000);
		Type type = new TypeToken<Map<String, String>>() {
		}.getType();
		Map<String, String> map = ECJsonUtils.fromJson(result, type);
		return map;
	}
	
	private static String byteToHex(final byte[] hash) {
		Formatter formatter = new Formatter();
		for (byte b : hash) {
			formatter.format("%02x", b);
		}
		String result = formatter.toString();
		formatter.close();
		return result;
	}

	private static String createNoncestr() {
		return UUID.randomUUID().toString();
	}

	private static String createTimestamp() {
		return Long.toString(System.currentTimeMillis() / 1000);
	}
	
	/**
	 * 绑定设备与微信用户的关系
	 * @param accessToken
	 * @param ticket
	 * @param deviceId
	 * @param userOpenId
	 * @return
	 */
	public static Map<String, String> deviceBind(String accessToken, String ticket, String deviceId, String userOpenId) {
		 String params="{\"ticket\":\"" + ticket + "\","
				 		+ "\"device_id\":\"" + deviceId + "\","
				 		+ "\"openid\":\"" + userOpenId + "\""
				 		+ "}";

		String result = HttpURLRequestUtil.doPost(deviceBindUrl + "access_token=" + accessToken, params);
		JsonParser parser = new JsonParser();
		JsonObject allObject = parser.parse(result).getAsJsonObject();
		JsonObject respObject = allObject.getAsJsonObject("base_resp");
		String errcode = respObject.get("errcode").toString();
		String errmsg = respObject.get("errmsg").toString();
		
		Map<String, String> resMap = new HashMap<String, String>();
		resMap.put("errcode", errcode);
		resMap.put("errmsg", errmsg);
		
		return resMap;
	}

	
	/**
	 * 解绑设备与微信用户的关系
	 * @param accessToken
	 * @param ticket
	 * @param deviceId
	 * @param userOpenId
	 * @return
	 */
	public static Map<String, String> deviceUnbind(String accessToken, String ticket, String deviceId, String userOpenId) {
		 String params="{\"ticket\":\"" + ticket + "\","
				 		+ "\"device_id\":\"" + deviceId + "\","
				 		+ "\"openid\":\"" + userOpenId + "\""
				 		+ "}";

		String result = HttpURLRequestUtil.doPost(deviceUnbindUrl + "access_token=" + accessToken, params);
		JsonParser parser = new JsonParser();
		JsonObject allObject = parser.parse(result).getAsJsonObject();
		JsonObject respObject = allObject.getAsJsonObject("base_resp");
		String errcode = respObject.get("errcode").toString();
		String errmsg = respObject.get("errmsg").toString();
		
		Map<String, String> resMap = new HashMap<String, String>();
		resMap.put("errcode", errcode);
		resMap.put("errmsg", errmsg);
		
		return resMap;
	}
	
	/**
	 * 强制绑定设备与微信用户的关系
	 * @param accessToken
	 * @param deviceId
	 * @param userOpenId
	 * @return
	 */
	public static Map<String, String> deviceCompelBind(String accessToken, String deviceId, String userOpenId) {

		String params="{\"device_id\":\"" + deviceId + "\","
				 		+ "\"openid\":\"" + userOpenId + "\""
				 		+ "}";

		String result = HttpURLRequestUtil.doPost(deviceCompelBindUrl + "access_token=" + accessToken, params);
		System.err.println("deviceCompelBind,wx result="+result);

		Map<String, String> resMap = new HashMap<String, String>();
		if(result == null || result.equals("")) {
			resMap.put("errcode", "-99");
			resMap.put("errmsg", "invalid result from wechat");
		}
		JsonParser parser = new JsonParser();
		JsonObject allObject = parser.parse(result).getAsJsonObject();
		JsonObject respObject = allObject.getAsJsonObject("base_resp");
		String errcode = respObject.get("errcode").toString();
		String errmsg = respObject.get("errmsg").toString();
		
		resMap.put("errcode", errcode);
		resMap.put("errmsg", errmsg);
		
		return resMap;
	}
	
	/**
	 * 强制解绑设备与微信用户的关系
	 * @param accessToken
	 * @param deviceId
	 * @param userOpenId
	 * @return
	 */
	public static Map<String, String> deviceCompelUnbind(String accessToken, String deviceId, String userOpenId) {
		 String params="{\"device_id\":\"" + deviceId + "\","
				 		+ "\"openid\":\"" + userOpenId + "\""
				 		+ "}";

		String result = HttpURLRequestUtil.doPost(deviceCompelUnbindUrl + "access_token=" + accessToken, params);
		System.err.println("deviceCompelUnbind,wx result="+result);

		Map<String, String> resMap = new HashMap<String, String>();
		if(result == null || result.equals("")) {
			resMap.put("errcode", "-99");
			resMap.put("errmsg", "invalid result from wechat");
		}
		JsonParser parser = new JsonParser();
		JsonObject allObject = parser.parse(result).getAsJsonObject();
		JsonObject respObject = allObject.getAsJsonObject("base_resp");
		String errcode = respObject.get("errcode").toString();
		String errmsg = respObject.get("errmsg").toString();
		
		resMap.put("errcode", errcode);
		resMap.put("errmsg", errmsg);

		
		return resMap;
	}	
	
	/**
	 * 根据微信用户的openid，获取其下已经绑定的设备的id
	 * @param accessToken
	 * @param userOpenId
	 * @return
	 */
	public static Map<String, Object> getBindDevice(String accessToken, String userOpenId) {
		String param = "{}";
		String result = HttpURLRequestUtil.doPost(getBindDeviceUrl + "access_token=" + accessToken + "&openid="+userOpenId,param);
		System.out.println("getBindDevice,wx result="+result);
		Map<String, Object> resMap = new HashMap<String, Object>();
		
		if(result == null || result.equals("")) {
			resMap.put("retcode", "-999");
			resMap.put("errorinfo", "system error");
			return resMap;
		}
		
		JsonParser parser = new JsonParser();
		
		
		JsonObject allObject = parser.parse(result).getAsJsonObject();
		JsonObject objRespMsg = allObject.getAsJsonObject("resp_msg");
		String retCode = objRespMsg.get("ret_code").toString();
		String errorInfo = objRespMsg.get("error_info").toString();
		String retOpenId = allObject.get("openid").toString();
		
		resMap.put("retcode", retCode);
		resMap.put("errorinfo", errorInfo);
		resMap.put("openid", retOpenId);
		
		JsonArray deviceList = allObject.getAsJsonArray("device_list");
		
		if(deviceList != null && deviceList.size() != 0) {
			List<String> deviceIdList = new ArrayList<String>(deviceList.size());
			for(int i = 0 ; i < deviceList.size() ; i++) {
				String deviceId=deviceList.get(i).getAsJsonObject().get("device_id").toString();
				deviceIdList.add(deviceId);
			}
			resMap.put("deviceids", deviceIdList);
		}
		
		return resMap;
	}

	public static void main(String[] args) {
		String result = "{\"base_resp\":{\"errcode\":0,\"errmsg\":\"ok\"}}";
		
		JsonParser parser = new JsonParser();
		JsonObject allObject = parser.parse(result).getAsJsonObject();
		JsonObject respObject = allObject.getAsJsonObject("base_resp");
		String errcode = respObject.get("errcode").toString();
		String errmsg = respObject.get("errmsg").toString();
		
		System.out.println(errcode);
		System.out.println(errmsg);
	}
}