package cn.com.shopec.common.authentication.Impl;
//京东万象认证（身份证  和     驾驶证）
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import cn.com.shopec.common.authentication.AuthenticationService;
import cn.com.shopec.common.utils.HttpURLRequestUtil;
	public class AuthenticationMemberJDWXServiceImpl implements AuthenticationService{
		 boolean sate = false;// 发送成功标志
		
		//身份验证
	    public  boolean sendMsgGetMember(String name, String cert_no,String appkey) {
	    	//https://way.jd.com/Saiman/edatadsfsm?name=张三&cert_no=440112199909090099&appkey=您申请的APPKEY
	       String   url="https://way.jd.com/Saiman/edatadsfsm?name="+name+"&cert_no="+cert_no+"&appkey="+appkey;
	         try {
	             String result = HttpURLRequestUtil.doMsgGet(url);
	             String requestJson = JSON.toJSONString(result);
	 			System.out.println("before request string is: " + requestJson);
	 			JsonParser parser=new JsonParser();
				JsonObject allObject = parser.parse(result).getAsJsonObject();
				JsonElement m=((JsonObject) allObject.get("result")).get("result");
				if (m.getAsBoolean()==true) {
					sate=true;
	 				
	 			}else{
	 				sate=false;
	 			}
				
	         } catch (Exception e) {
	 			e.printStackTrace();
	 			sate=false;
	 		}
	         System.err.println("身份验证"+sate);
			return sate;
			
			
		}
	    //驾驶证认证
	    public  boolean sendMsgGet( String idCard,String licenseId,String appkey) {
	    	boolean sates = false;
	    	 // String msg="";
	    	//https://way.jd.com/jisuapi/licenseid?licensenumber=412336111111111111&licenseid=330000000000&appkey=您申请的APPKEY
	       String   url="https://way.jd.com/jisuapi/licenseid?licensenumber="+idCard+"&licenseid="+licenseId+"&appkey="+appkey;
	         try {
	             String result = HttpURLRequestUtil.doMsgGet(url);
	 			JsonParser parser=new JsonParser();
				JsonObject allObject = parser.parse(result).getAsJsonObject();
				JsonElement n=((JsonObject) allObject.get("result")).get("status");
				if (n.getAsString().equals("0")) {
					sates=true;
	 			}else{
	 				sates=false;
	 			}
				
	         } catch (Exception e) {
	 			e.printStackTrace();
	 			sates=false;
	 		}
	         System.err.println(sates);
			return sates;
			
			
		}

		public Map<String, Object> validationCard(String imgBase64Str, String type) {
			// TODO Auto-generated method stub
			return null;
		}
		@Override
		public Map<String, Object> validationCardMember(String memberName, String idCard) {
			// TODO Auto-generated method stub
			return null;
		}
		@Override
		public Map<String, Object> validationCardCar(String idCard, String licenseId) {
			// TODO Auto-generated method stub
			return null;
		}

//		public static void main(String[] args) {
//			sendMsgGets("6103221993030323217", "230400220904", "4186b71edfc28959ea8000bdc956bb0d");
//		}

	    
	   
	}

