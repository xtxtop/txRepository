package cn.com.shopec.mapi.wx.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.common.cache.CommonCacheUtil;
import cn.com.shopec.common.utils.ECJsonUtils;
import cn.com.shopec.common.utils.WxUtils;
import cn.com.shopec.core.member.model.Member;
import cn.com.shopec.mapi.common.controller.BaseController;

@Controller
@RequestMapping("/app/wx")
public class WxController extends BaseController {

	@Resource
	private CommonCacheUtil cacheUtil;
	@Value("${base_path}")
	private String basePath;

	@RequestMapping("/getWxAccessToken")
	@ResponseBody
	public ResultInfo<Map> getWxAccessToken() {
		ResultInfo<Map> resultInfo = new ResultInfo<Map>();
		Map<String, String> wxMap = getAccessTokenMap();
		resultInfo.setData(wxMap);
		return resultInfo;
	}

	@RequestMapping("/getWxApiConfig")
	@ResponseBody
	public ResultInfo<Map> getWxApiConfig(String url) {
		ResultInfo<Map> resultInfo = new ResultInfo<Map>();
		Map<String, String> wxMap = getAccessTokenMap();
		String accessToken = wxMap.get("wx_access_token");
		String ticket = WxUtils.getJsapiTicket(accessToken);
		Map<String, String> map = WxUtils.sign(ticket,url);
		resultInfo.setData(map);
		return resultInfo;
	}

	@RequestMapping("/getDevice")
	@ResponseBody
	public ResultInfo<Map> getDevice() {
		ResultInfo<Map> resultInfo = new ResultInfo<Map>();
		Map<String, String> wxMap = getAccessTokenMap();
		String accessToken = wxMap.get("wx_access_token");
		Map<String, String> map = WxUtils.getDevice(accessToken);
		resultInfo.setData(map);
		return resultInfo;
	}

	@RequestMapping("/authorizeDevice")
	@ResponseBody
	public ResultInfo<Map> authorizeDevice(@RequestParam("mac") String mac,@RequestParam("deviceId") String deviceId) {
		//mac="20C9D0848172";
		mac=mac.replaceAll(":", "");
		ResultInfo<Map> resultInfo = new ResultInfo<Map>();
		Map<String, String> wxMap = getAccessTokenMap();
		String accessToken = wxMap.get("wx_access_token");
//		String deviceId = getDeviceId(accessToken);
		Map<String, String> map = WxUtils.getAuthorizeDevice(accessToken, deviceId, mac);
		resultInfo.setData(map);
		return resultInfo;
	}

	/*@RequestMapping("/storeWxAuthCode")
	@ResponseBody
	public ResultInfo<Map> storeWxAuthCode(String code) {
		ResultInfo<Map> resultInfo = new ResultInfo<Map>();
		Member member = getLoginMember();
		if (member != null) {
			if (cacheUtil.getObject("wxauth_" + member.getMemberNo()) == null) {
				Map<String, String> authMap = WxUtils.getOauth2(code);
				authMap.put("code", code);
				cacheUtil.setObject("wxauth_" + member.getMemberNo(), authMap);
				resultInfo.setData(authMap);
			}else{
				Map<String, String> authMap = (Map)cacheUtil.getObject("wxauth_" + member.getMemberNo());
				if(authMap.get("code")!=null){
					if(!authMap.get("code").equals(code)){
						authMap = WxUtils.getOauth2(code);
						authMap.put("code", code);
						cacheUtil.setObject("wxauth_" + member.getMemberNo(), authMap);
					}
				}else{
					authMap = WxUtils.getOauth2(code);
					authMap.put("code", code);
					cacheUtil.setObject("wxauth_" + member.getMemberNo(), authMap);
				}
				resultInfo.setData(authMap);
			}
			resultInfo.setCode("1");
			resultInfo.setMsg("code存储成功");
		} else {
			resultInfo.setCode("0");
			resultInfo.setMsg("存储失败，用户未登录！");
		}
		System.out.println("wxAuth callback====="+code);
		return resultInfo;
	}*/
	
	@RequestMapping("/storeWxAuthCode")
	@ResponseBody
	public ResultInfo<Map> storeWxAuthCode(String code) {
		ResultInfo<Map> resultInfo = new ResultInfo<Map>();
		Member member = getLoginMember();
		if (member != null) {
			//if (cacheUtil.getObject("wxauth_" + member.getMemberNo()) == null) {
				Map authMap = (Map) cacheUtil.getObject(code);
				if (authMap == null) {
					authMap = WxUtils.getOauth2(code);
					authMap.put("code", code);
					cacheUtil.setObject(code, authMap, 7200);
					resultInfo.setData((Map) authMap);
					resultInfo.setCode("1");
					resultInfo.setMsg("new code存储成功");
				} else {
					resultInfo.setData((Map) authMap);
					resultInfo.setCode("1");
					resultInfo.setMsg("7200 old code获取成功");
				}
				cacheUtil.setObject("wxauth_" + member.getMemberNo(), authMap);
			/*} else {
				resultInfo.setData((Map) cacheUtil.getObject("wxauth_" + member.getMemberNo()));
				resultInfo.setCode("1");
				resultInfo.setMsg("old auth 获取成功");
			}*/
		} else {
			resultInfo.setCode("0");
			resultInfo.setMsg("未存储，用户未登录！");
		}
		return resultInfo;
	}
	
	@RequestMapping("/getWxAuthUrl")
	@ResponseBody
	public ResultInfo<Map> getWxAuthUrl() {
		ResultInfo<Map> resultInfo = new ResultInfo<Map>();
		Map map=new HashMap();
		map.put("url", WxUtils.oauth2codeurl);
		resultInfo.setData(map);
		System.out.println("wxAuth callback====="+WxUtils.oauth2codeurl);
		return resultInfo;
	}
	
	@RequestMapping("/toWxAuth")
	public ModelAndView toWxAuth(RedirectAttributes redirectAttributes){
		//wxapi.oauth2codeurl=https://open.weixin.qq.com/connect/oauth2/authorize?
		//appid=wxb5d4ec2f882089c7&redirect_uri=http://182.92.194.70:8783/fs-mapi-dev/app/wx/storeWxAuthCode.do&
		//response_type=code&scope=snsapi_base&state=STATE#wechat_redirect
		Map attributes=new HashMap();
		attributes.put("appid", WxUtils.appid);
		attributes.put("redirect_uri", basePath+"/app/wx/storeWxAuthCode.do");
		attributes.put("response_type", "code");
		attributes.put("scope", "snsapi_base");
		attributes.put("state", "STATE#wechat_redirect");
		redirectAttributes.addAllAttributes(attributes);
		System.out.println("to wxAuth =====");
		return new ModelAndView("redirect:/app/wx/wxAuth.do");
	}
	
	
	
	/*@RequestMapping("/toWxAuth")
	@ResponseBody
	public ResultInfo<Map> toWxAuth(){
		//wxapi.oauth2codeurl=https://open.weixin.qq.com/connect/oauth2/authorize?
		//appid=wxb5d4ec2f882089c7&redirect_uri=http://182.92.194.70:8783/fs-mapi-dev/app/wx/storeWxAuthCode.do&
		//response_type=code&scope=snsapi_base&state=STATE#wechat_redirect
		ResultInfo<Map> resultInfo = new ResultInfo<Map>();
		Map attributes=new HashMap();
		attributes.put("appid", WxUtils.appid);
		attributes.put("redirect_uri", "http://182.92.194.70:8783/fs-mapi-dev/app/wx/storeWxAuthCode.do");
		attributes.put("response_type", "code");
		attributes.put("scope", "snsapi_base");
		attributes.put("state", "STATE#wechat_redirect");
		resultInfo.setCode("1");
		resultInfo.setMsg("成功！");
		resultInfo.setData(attributes);
		return resultInfo;
	}*/
	
	private Map<String, String> getAccessTokenMap() {
		Map<String, String> wxMap = (Map) cacheUtil.getObject("fs_wxMap");
		if (wxMap != null) {
			Date currentTime = new Date();
			if (wxMap.get("wx_access_token_createtime") != null && wxMap.get("wx_access_token_expire_in") != null) {
				Long wx_access_token_expire_in = Long.valueOf(wxMap.get("wx_access_token_expire_in"));
				Long time = currentTime.getTime() - Long.valueOf(wxMap.get("wx_access_token_createtime"));
				if (wx_access_token_expire_in <= (time/1000)) {
					wxMap = WxUtils.getAccessToken();
					cacheUtil.setObject("fs_wxMap", wxMap, Integer.valueOf(wxMap.get("wx_access_token_expire_in")));
				}
			}
		} else {
			wxMap = WxUtils.getAccessToken();
			cacheUtil.setObject("fs_wxMap", wxMap, Integer.valueOf(wxMap.get("wx_access_token_expire_in")));
		}
		return wxMap;
	}

	private String getDeviceId(String accessToken) {
		Map<String, String> map = WxUtils.getDevice(accessToken);
		return (String) map.get("deviceid");
	}

	@RequestMapping("/deviceUnbind")
	@ResponseBody
	public ResultInfo<Map> deviceUnbind(String deviceId, String userOpenId) {
		ResultInfo<Map> res = new ResultInfo<Map>();
		if(deviceId != null && deviceId.length() != 0 && userOpenId != null && userOpenId.length() != 0) {
			Map<String, String> wxMap = getAccessTokenMap();
			String accessToken = wxMap.get("wx_access_token");
			String ticket = WxUtils.getJsapiTicket(accessToken);
			
			Map<String,String> map = WxUtils.deviceUnbind(accessToken, ticket, deviceId, userOpenId);
			if("0".equals(map.get("errcode"))) {
				res.setCode("1");
			} else {
				res.setCode("0");
			}
			res.setMsg(map.get("errmsg"));
			res.setData(map);
		} else {
			res.setCode("0");
			res.setMsg("Invalid argument");
		}
		
		return res;
	}

	@RequestMapping("/deviceBind")
	@ResponseBody
	public ResultInfo<Map> deviceBind(String deviceId, String userOpenId) {
		ResultInfo<Map> res = new ResultInfo<Map>();
		if(deviceId != null && deviceId.length() != 0 && userOpenId != null && userOpenId.length() != 0) {
			Map<String, String> wxMap = getAccessTokenMap();
			String accessToken = wxMap.get("wx_access_token");
			String ticket = WxUtils.getJsapiTicket(accessToken);
			
			Map<String,String> map = WxUtils.deviceBind(accessToken, ticket, deviceId, userOpenId);
			if("0".equals(map.get("errcode"))) {
				res.setCode("1");
			} else {
				res.setCode("0");
			}
			res.setMsg(map.get("errmsg"));
			res.setData(map);
		} else {
			res.setCode("0");
			res.setMsg("Invalid argument");
		}
		
		return res;
	}

	@RequestMapping("/deviceCompelUnbind")
	@ResponseBody
	public ResultInfo<Map> deviceCompelUnbind(String deviceId, String userOpenId) {
		ResultInfo<Map> res = new ResultInfo<Map>();
		if(deviceId != null && deviceId.length() != 0 && userOpenId != null && userOpenId.length() != 0) {
			Map<String, String> wxMap = getAccessTokenMap();
			String accessToken = wxMap.get("wx_access_token");
			
			Map<String,String> map = WxUtils.deviceCompelUnbind(accessToken, deviceId, userOpenId);
			if("0".equals(map.get("errcode"))) {
				res.setCode("1");
			} else {
				res.setCode("0");
			}
			res.setMsg(map.get("errmsg"));
			res.setData(map);
		} else {
			res.setCode("0");
			res.setMsg("Invalid argument");
		}
		
		return res;
	}
	
	@RequestMapping("/deviceCompelBind")
	@ResponseBody
	public ResultInfo<Map> deviceCompelBind(String deviceId, String userOpenId) {
		ResultInfo<Map> res = new ResultInfo<Map>();
		if(deviceId != null && deviceId.length() != 0 && userOpenId != null && userOpenId.length() != 0) {
			Map<String, String> wxMap = getAccessTokenMap();
			String accessToken = wxMap.get("wx_access_token");
			
			Map<String,String> map = WxUtils.deviceCompelBind(accessToken, deviceId, userOpenId);
			if("0".equals(map.get("errcode"))) {
				res.setCode("1");
			} else {
				res.setCode("0");
			}
			res.setMsg(map.get("errmsg"));
			res.setData(map);
		} else {
			res.setCode("0");
			res.setMsg("Invalid argument");
		}
		
		return res;
	}
	
	@RequestMapping("/getBindDevice")
	@ResponseBody
	public ResultInfo<Map> getBindDevice(String userOpenId) {
		ResultInfo<Map> res = new ResultInfo<Map>();
		
		if(userOpenId != null && userOpenId.length() != 0) {
			Map<String, String> wxMap = getAccessTokenMap();
			String accessToken = wxMap.get("wx_access_token");
//			String ticket = WxUtils.getJsapiTicket(accessToken);
			
			Map<String, Object> map = WxUtils.getBindDevice(accessToken, userOpenId);
			if("0".equals(map.get("retcode"))) {
				res.setCode("1");
				res.setData(map);
			} else {
				res.setCode("0");
				res.setMsg((String)map.get("errorinfo"));
			}
			
		} else {
			res.setCode("0");
			res.setMsg("Invalid argument");
		}
		
		return res;
	}
}
