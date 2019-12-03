package cn.com.shopec.common.authentication.Impl;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;

import com.alibaba.fastjson.JSON;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import cn.com.shopec.common.authentication.AuthenticationService;
import cn.com.shopec.common.constants.Constant;
import cn.com.shopec.common.utils.AliyunBase64ToImg;
import cn.com.shopec.common.utils.AliyunHttpUtils;
import cn.com.shopec.common.utils.ECDateUtils;
import cn.com.shopec.common.utils.ECUuidGenUtils;
import cn.com.shopec.common.utils.JsonUtils;
import cn.com.shopec.core.system.dao.SysParamDao;
import cn.com.shopec.core.system.model.SysParam;
import cn.com.shopec.core.system.service.SysParamService;
/*
 * 阿里云
 * 
 * */
public class AuthenticationMemberALYServiceImpl implements AuthenticationService  {
	@Value("${aliyun_app_code}")
	private  String APPCODE;
	@Value("${image_path}")
	private String serverPath;
	@Resource
	private SysParamDao sysParamDao;
	@Value("${res_img_path}")
	private String resImgPath;
	
	public static final String HOST = "http://jisusfzsb.market.alicloudapi.com";

	public static final String PATH = "/idcardrecognition/recognize";

	public static final String METHOD = "POST";
	@Override
	public Map<String, Object> validationCard(String imgBase64Str, String type) {
		Map <String, Object> data = new HashMap<String, Object>();
	    Map<String, String> headers = new HashMap<String, String>();
	    //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
	    headers.put("Authorization", "APPCODE " + APPCODE);
	    //根据API的要求，定义相对应的Content-Type
	    headers.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
	    Map<String, String> querys = new HashMap<String, String>();
	    querys.put("typeid", type);
	    Map<String, String> bodys = new HashMap<String, String>();
	    bodys.put("pic", imgBase64Str);
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
	    	HttpResponse response = AliyunHttpUtils.doPost(HOST, PATH, METHOD, headers, querys, bodys);
	    	//获取response的body
	    	String responseEntity = EntityUtils.toString(response.getEntity());
	    	if(StringUtils.isBlank(responseEntity)){
	    		return null;
	    	}
	    	Map<String, Object> map = JsonUtils.parse2Object(responseEntity, Map.class);
	    	String msg = (String)map.get("msg");
	    	if(msg == null || !msg.equals("ok")){
	    		return null;
	    	}
	    	Map<String,Object> reulst = (Map<String,Object>)map.get("result");
	    	if(reulst == null){
	    		return null;
	    	}
	    	if("2".equals(type)){
	    		String name = (String)reulst.get("name");
	    		String number = (String)reulst.get("number");
	    		String address = (String)reulst.get("address");
	    		if(StringUtils.isBlank(name) || StringUtils.isBlank(number) || StringUtils.isBlank(address)){
		    		return null;
	    		}
		    	data.put("name", (String)reulst.get("name"));
		    	data.put("cardNo", (String)reulst.get("number"));
	    	}else if("5".equals(type)){
	    		String realname = (String)reulst.get("realname");
	    		String licensenumber = (String)reulst.get("licensenumber");
	    		String startdate = (String)reulst.get("startdate");
	    		String enddate = (String)reulst.get("enddate");
	    		if(StringUtils.isBlank(realname) || StringUtils.isBlank(licensenumber) || StringUtils.isBlank(startdate) || StringUtils.isBlank(enddate)){
		    		return null;
	    		}
		    	data.put("name", realname);
		    	data.put("cardNo", licensenumber);
		    	data.put("startTime", startdate);
		    	data.put("endTime", enddate);
	    	}else{
	    		return null;	
	    	}
	    	data.put("type", type);
			// 新文件名
			String newFileName = ECUuidGenUtils.genUUID();
			// 创建路径
			String paths = newFileName.substring(newFileName.length() - 4, newFileName.length());
			// 散列路径
			String path1 = paths.substring(0, 2);
			// 散列路径
			String path2 = paths.substring(2, paths.length());
			String picPath = serverPath +"/"+ Constant.MEMBER_ICON + "/" + path1 + "/" + path2 + "/" + newFileName + ".jpg";
			String filePath = resImgPath +"/"+ Constant.MEMBER_ICON + "/" + path1 + "/" + path2;
			File file = new File(filePath);
			if(!file.mkdirs()){
				file.createNewFile();
			}
			String picPathFile = filePath + "/" + newFileName + ".jpg";
	    	AliyunBase64ToImg.GenerateImage(imgBase64Str, picPathFile);
	    	data.put("picPath", picPath);
	    	return data;
	    } catch (Exception e) {
	    	e.printStackTrace();
	    }
		return null;
	}

	public boolean sendMsgGetMember(String name, String cert_no, String appkey) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean sendMsgGet(String licenseid, String idCard, String appkey) {
		// TODO Auto-generated method stub
		return false;
	}
	
	//身份证验证
	@Override
	public Map<String, Object> validationCardMember(String memberName, String idCard) {
		HashMap<String, Object> result = new HashMap<String, Object>();
		 String host = "http://idcard.market.alicloudapi.com";
		    String path = "/lianzhuo/idcard";
		    String method = "GET";
		    //AppCode：a6d08089d3a94593a0d36854ca27dadf
		    String appcode = "a6d08089d3a94593a0d36854ca27dadf";
		    Map<String, String> headers = new HashMap<String, String>();
		    //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
		    headers.put("Authorization", "APPCODE " + appcode);
		    Map<String, String> querys = new HashMap<String, String>();
		    querys.put("cardno", idCard);
		    querys.put("name", memberName);


		    try {
		    	HttpResponse response = AliyunHttpUtils.doGet(host, path, method, headers, querys);
		    	//获取response的body
		    	String responseEntity=EntityUtils.toString(response.getEntity());
		    	Map<String, Object> map = JsonUtils.parse2Object(responseEntity, Map.class);
		    	Map<String,Object> rr = (Map<String,Object>)map.get("resp");
		    	String code = rr.get("code").toString();
				if (code != null && code.equals("0")) {
					return result;
				}else{
					String msg = rr.get("desc").toString();
			    	result.put("msg", msg);
			    	return result;
				}
		    	
		    } catch (Exception e) {
		    	e.printStackTrace();
		    }
		return null;
	}
	
	
	//驾驶证验证
	@Override
	public Map<String, Object> validationCardCar(String idCard, String licenseId) {
		HashMap<String, Object> result = new HashMap<String, Object>();
		 String host = "http://jisujszkf.market.alicloudapi.com";
		    String path = "/driverlicense/query";
		    String method = "GET";
		    String appcode = "a6d08089d3a94593a0d36854ca27dadf";
		    Map<String, String> headers = new HashMap<String, String>();
		    //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
		    headers.put("Authorization", "APPCODE " + appcode);
		    Map<String, String> querys = new HashMap<String, String>();
		    querys.put("licenseid", licenseId);
		    querys.put("licensenumber", idCard);


		    try {
		    	HttpResponse response = AliyunHttpUtils.doGet(host, path, method, headers, querys);
		    	String responseEntity=EntityUtils.toString(response.getEntity());
		    	Map<String, Object> map = JsonUtils.parse2Object(responseEntity, Map.class);
		    	String code = map.get("status").toString();
				if (code != null && code.equals("0")) {
					Map<String,Object> rr = (Map<String,Object>)map.get("result");
					String score = rr.get("score").toString();
					String enddate = "";
					String startdate = "";
					
					if((rr.get("enddate")) != null && !"".equals((rr.get("enddate")))  && (rr.get("startdate")) != null && !"".equals((rr.get("startdate"))) ){
						 enddate = rr.get("enddate").toString();
							startdate = rr.get("startdate").toString();
						SimpleDateFormat format =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
						    String time=startdate+" 00:00:00";
						    String time2=enddate+" 23:59:59";
						    Date date=null;
						    Date date2=null;
						    Integer d=365;
						    Integer day=0;
						    SysParam sp=sysParamDao.getByParamKey("licensePeriod");
						    if(sp != null){
						    	 d=Integer.valueOf(sp.getParamValue());
						    }
							try {
								date = format.parse(time);
								date2 = format.parse(time2);
								day=Integer.parseInt(ECDateUtils.differDays(date, date2)+"");
								 if(day<d){
										result.put("code", "0");
								    	result.put("msg", "驾驶证还在实习期，不能提交审核");
								    	return result;
									}
							} catch (ParseException e) {
								e.printStackTrace();
							}
					}
					
					
					if(score.equals("12")){
						result.put("code", "0");
				    	result.put("msg", "驾驶证扣分已经12分,不能提交审核");
				    	return result;
					}else{
						result.put("code", code);
						result.put("enddate", enddate);
						return result;
					}
					
				}else{
					String msg = map.get("msg").toString();
					result.put("code", code);
			    	result.put("msg", msg);
			    	return result;
				}
		    	
		    } catch (Exception e) {
		    	e.printStackTrace();
		    }
			return null;
	}
    
}
