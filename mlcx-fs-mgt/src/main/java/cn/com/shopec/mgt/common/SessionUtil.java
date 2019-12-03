package cn.com.shopec.mgt.common;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cn.com.shopec.core.system.model.SysUser;

/**
 * SESSION 操作
 * 
 * @ClassName: SessionUtil
 * @Description: TODO
 * 
 */
public class SessionUtil {
	
	
	
	/**
	 * 取HttpSession对象
	 * @return
	 */
	public static HttpSession getSession(HttpServletRequest request) {
		return request.getSession();
	}
	

	/**
	 * 设置用户的登录信息
	 * @Title: setLoginSysUser 
	 * @Description: TODO
	 * @param request
	 * @param sysUser
	 * @return  
	 */
	public static boolean setLoginSysUser(HttpServletRequest request,SysUser sysUser){
		try{
			request.getSession().setAttribute(ECMgtConstant.SESSION_KEY_OF_LOGIN_SYS_USER, sysUser);
			return true;
		}catch(Exception e){
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * 取已登录的系统人员用户对象（数据来自session）
	 * @return 
	 */
	public static SysUser getLoginSysUser(HttpServletRequest request) {
		SysUser sysUser = null;
		HttpSession session = getSession(request);
		if(session != null) {
			sysUser = (SysUser)session.getAttribute(ECMgtConstant.SESSION_KEY_OF_LOGIN_SYS_USER); //从session中取已登录的系统人员用户对象
		}
		return sysUser;
	}

	 /**
	  * 移除登陆用户
	  * @Title: removeSessionAttribut 
	  * @Description: TODO
	  * @param request
	  * @param key  
	  */
	public static void removeSessionUser(HttpServletRequest request){
		 removeSessionAttribut(request,ECMgtConstant.SESSION_KEY_OF_LOGIN_SYS_USER);
	 
	}
	
	/**
	 * 保存信息到SESSION
	 * 
	 * @Title: setBaseInfo
	 * @Description: 保存信息到SESSION
	 * @param @param request
	 * @param @param key
	 * @param @param value
	 * @return void
	 * @throws
	 * @author: yiwen
	 * @date: 2013-6-19 下午08:31:57
	 */
	public static void setDataToSession(HttpServletRequest request, String key, Object value) {
		request.getSession().setAttribute(key, value);
	}

	/**
	 * 获取session信息
	 * 
	 * @Title: getBaseInfo
	 * @Description: TODO
	 * @param request
	 * @param key
	 * @return
	 * @author: yiwen
	 * @date: 2013-6-19 下午08:31:57
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getDataFromSession(HttpServletRequest request, String key, Class<T> clazz) {
		return (T) request.getSession().getAttribute(key);
	}

	/**
	 * 移除session 属性
	 * 
	 * @param request
	 * @param key
	 */
	public static void removeSessionAttribut(HttpServletRequest request, String key) {
		request.getSession().removeAttribute(key);
	}

	/**
	 * 保存访问路径
	 * 
	 * @Title: setLocationUrl
	 * @Description: 设置访问URL
	 * @param request
	 * @author: yiwen
	 * @date: 2013-6-19 下午08:31:57
	 */
	public static void setLocationUrl(HttpServletRequest request) {
		request.getSession().removeAttribute("locationUrl");
		String inurl = request.getServletPath();
		String p = request.getQueryString();
		if("".equals(p) || null ==p || "null".equals(p)){
			p="";
		}else{
			p="?"+p;
		}
		request.getSession().setAttribute("locationUrl", inurl+p);
	}

	/**
	 * 得到访问路径
	 * 
	 * @Title: getLocationUrl
	 * @Description: 得到访问URl
	 * @param request
	 * @author: yiwen
	 * @date: 2013-6-19 下午08:31:57
	 */
	public static String getLocationUrl(HttpServletRequest request) {
		return (String) request.getSession().getAttribute("locationUrl");
	}

	/**
	 * 得到重定向路径
	 * 
	 * @Title: getRedicectURL
	 * @param request
	 * @param path
	 *            跳转相对路径
	 * @author: yiwen
	 * @date: 2013-6-19 下午08:31:57
	 */
	public static String getRedicectURL(HttpServletRequest request, String path) {

		String returnUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + path;
		return returnUrl;
	}
	



	/** 
	 * 重置sessionid，原session中的数据自动转存到新session中 
	 * @param request 
	 */  
	public static void reGenerateSessionId(HttpServletRequest request, HttpServletResponse response){  
	      
	    HttpSession session = request.getSession();  
	      
	    //首先将原session中的数据转移至一临时map中  
	    Map<String,Object> tempMap = new HashMap<String,Object>();  
	    @SuppressWarnings("unchecked")
		Enumeration<String> sessionNames = session.getAttributeNames();  
	    while(sessionNames.hasMoreElements()){  
	        String sessionName = sessionNames.nextElement();  
	        tempMap.put(sessionName, session.getAttribute(sessionName));  
	    }  
	    System.out.println("=#############################session="+session.getId());
	    //注销原session，为的是重置sessionId  
	    session.invalidate();
	    System.out.println("=#############################session="+session);
	    
	    //将临时map中的数据转移至新session  
	    session = request.getSession(true);  

	    System.out.println("=#############################session="+session.getId());
	   
	  
	    for(Map.Entry<String, Object> entry : tempMap.entrySet()){  
	        session.setAttribute(entry.getKey(), entry.getValue());  
	    }  
	}  
}
