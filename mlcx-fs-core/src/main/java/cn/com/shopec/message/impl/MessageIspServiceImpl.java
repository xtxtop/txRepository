package cn.com.shopec.message.impl;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;

import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.common.utils.HttpURLRequestUtil;
import cn.com.shopec.delivery.common.DeliveryCommonImpl;
import net.sf.json.JSONObject;




@Component("messageIspServiceImpl")
public class MessageIspServiceImpl extends HyInterfaceImpl{

	
	
	
	
	
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
	 * username=user&password=pass&from=001&to=13800000000&content=hell&presendTime=2003-02-0312:12:03
	 */
	public String sendMsg(String mobile,String content){
		try {
			String username = getString("username");
			String password = getString("password");
			String from = getString("from");
			String url = getString("sendUrl");
			
//			String url = "http://hy6.nbark.com:7602/sms.aspx";
			StringBuilder builder = new StringBuilder();
			builder
				.append("username=")
				.append(username)
				.append("&password=")
				.append(password)
				.append("&from=")
				.append(from)
				.append("&to=")
				.append(mobile)
				.append("&content=")
				.append(URLEncoder.encode(content,"GBK"));

			String msgResult = doGet(url,builder.toString());
			return msgResult;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public int sendMsgState(String stateNo, String phoneNo, String content) {
		// TODO Auto-generated method stub
		return 0;
	}
	

	
	//屏蔽词检测
//	public static void getRequest1(){
//		ResultInfo<String> object =new ResultInfo<String>();
//        String result =null;
//        String url ="http://v.juhe.cn/sms/black";//请求接口地址
//        Map params = new HashMap();//请求参数
//            params.put("word","");//需要检测的短信内容，需要UTF8 URLENCODE
//            params.put("key",APPKEY);//应用APPKEY(应用详细页查询)
// 
//        try {
//            result =HttpURLRequestUtil.net(url, params, "GET");
//            Gson gson = new Gson();
//            object=gson.fromJson(result, ResultInfo.class);
//			
////           
////            if(object.getInt("error_code")==0){
////                System.out.println(object.get("result"));
////            }else{
////                System.out.println(object.get("error_code")+":"+object.get("reason"));
////            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
	
//2.发送短信
	@SuppressWarnings("unchecked")
	public int getRequest2(String phoneNo,String tpl_value,String tpl_id,String APPKEY,String urls ){
		int sate = 1;
		ResultInfo<String> object1 =new ResultInfo<String>();
		String result=null;
		//String urls ="http://v.juhe.cn/sms/send";//请求接口地址
		Map<String, String> params=new HashMap<String, String>();//请求参数
		params. put("mobile",phoneNo);//接收短信的手机号码
		params.put("tpl_id","tpl_id");//短信模板ID，请参考个人中心短信模板设置
		params.put("tpl_value",tpl_value);//变量名和变量值对。如果你的变量名或者变量值中带有#&=中的任意一个特殊符号，请先分别进行urlencode编码后再传递，<a href="http://www.juhe.cn/news/index/id/50" target="_blank">详细说明></a>
		params.put("key",APPKEY);//应用APPKEY(应用详细页查询)
		params.put("dtype","json");//返回数据的格式,xml或json，默认json
      try {
    	String url=urls+"?mobile="+phoneNo+"&tpl_id="+tpl_id+"&tpl_value="+tpl_value+"&key="+APPKEY;
        result =HttpURLRequestUtil.net(url,"GET");
       JSONObject object = JSONObject.fromObject(result);
        if(object.getInt("error_code")==0){
            System.out.println(object.get("result"));
        }else{
        	sate=0;
            System.out.println(object.get("error_code")+":"+object.get("reason"));
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
	return sate;
	
}
}
