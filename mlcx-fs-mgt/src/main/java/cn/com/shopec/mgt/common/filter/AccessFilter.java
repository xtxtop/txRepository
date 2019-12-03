package cn.com.shopec.mgt.common.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.com.shopec.common.utils.ECSpringContextUtils;
import cn.com.shopec.core.system.model.SysPermission;
import cn.com.shopec.core.system.model.SysRole;
import cn.com.shopec.core.system.model.SysUser;
import cn.com.shopec.core.system.model.SysUserRoleRel;
import cn.com.shopec.core.system.service.SysRolePermRelService;
import cn.com.shopec.core.system.service.SysUserRoleRelService;
import cn.com.shopec.mgt.common.BaseController;
import cn.com.shopec.mgt.common.ECMgtConstant;
import cn.com.shopec.mgt.common.PermissionListSingleton;
import cn.com.shopec.mgt.common.SessionUtil;
import cn.com.shopec.mgt.main.controller.HomeController; 

@Controller
@RequestMapping
public class AccessFilter implements Filter {
	
	private static final Log log = LogFactory.getLog(AccessFilter.class);
	
	private SysUserRoleRelService sysUserRoleRelService;
	
	private SysRolePermRelService sysRolePermRelService;

    private final static Set<String> HTML_EXCLUDE_PATHS = new HashSet<String>();

	//不需要拦截的url
	static{
		HTML_EXCLUDE_PATHS.add("login.do");
//		HTML_EXCLUDE_PATHS.add("index.do");
//		HTML_EXCLUDE_PATHS.add("leftPage.do");
		HTML_EXCLUDE_PATHS.add("loginOut.do");
		HTML_EXCLUDE_PATHS.add("depositRefund/depositRefundAlipayUpdate.do");
	}

	
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest requestParam, ServletResponse responseParam, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest)requestParam;
		HttpServletResponse response = (HttpServletResponse)responseParam;
		
        log.info("==============执行顺序: 1、preHandle================");    
        String requestUri = request.getRequestURI();  
        String contextPath = request.getContextPath();  
        String url = requestUri.substring(contextPath.length());
        url = url.substring(1, url.length());
        
        log.info("requestUri:"+requestUri);    
        log.info("contextPath:"+contextPath);    
        log.info("url:"+url);
        SysUser user = SessionUtil.getLoginSysUser(request);
        //不需要拦截的url
        if(HTML_EXCLUDE_PATHS.contains(url)){
        	chain.doFilter(request, response);
        	return;
        }
       
        if(user == null){  
        	 //如果是ajax 
        	boolean isAjaxRequest = this.isAjaxRequest((HttpServletRequest)request);
        	//ajax请求session过期
    		if(isAjaxRequest) {
    			((HttpServletResponse)response).setHeader("session_is_timeout", "1");
    			return;
    		}
            log.info("Interceptor：跳转到login页面！");  
            //request.getRequestDispatcher("/main/login").forward(request, response); 
            response.sendRedirect(contextPath + "/login.do");
            return;  
        }else{
        	//根据用户得到角色的权限SysRole
			List<SysRole> sysRole = user.getSysRole();
			List<SysPermission> perList = new ArrayList<SysPermission>();
			 
			String[] roleIds = new String[sysRole.size()];

			boolean flag = false;
			
			for (int i = 0; i < sysRole.size(); i++) {
				roleIds[i] = sysRole.get(i).getRoleId();
				perList = sysRole.get(i).getPerList();
				
				if (perList != null && perList.size() > 0) {
					for (SysPermission permission : perList) {
						if(url.equals(permission.getPermResource())){
		        			flag = true;
		        			break; 
		        		}
					}
				}
				
			}
 
			//判断是否有url访问权限
//			if(flag){ 
//				chain.doFilter(request, response);
//			}else{
//				((HttpServletResponse)response).setHeader("session_is_jurisdiction", "1");
//			}
			
			chain.doFilter(request, response);
        	return; 
			
			
        	//根据用户得到角色的权限
//        	SysUserRoleRelService sysUserRoleRelService = this.getSysUserRoleRelService();
//        	SysRolePermRelService sysRolePermRelService = this.getSysRolePermRelService();
        	//SysUserRoleRel sysUserRoleRel = sysUserRoleRelService.getByUserId(user.getUserId());
        	//存单例模式
        	//List<SysPermission> perList = sysRolePermRelService.getAllRolePermissions(sysUserRoleRel.getRoleId());
//        	PermissionListSingleton perListSlt = PermissionListSingleton.getInstance(SessionUtil.getDataFromSession(request, ECMgtConstant.SESSION_KEY_OF_LOGIN_SYS_ROLEID, String.class));
//        	boolean flag = false;
//        	if(perListSlt != null && perListSlt.getPerList() != null){
//	        	for(SysPermission permission :perListSlt.getPerList()){
//	        		if(url.equals(permission.getPermResource())){
//	        			flag = true;
//	        			break; 
//	        		}
//	        	}
//        	}
//        	if(perListSlt.getPerUrlMap().containsValue(url)){
//        		flag = true;
//        	}
        	
        	//暂时不考虑权限问题
//        	chain.doFilter(request, response);
//        	return; 
        }
		
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		
	}
	
	private SysUserRoleRelService getSysUserRoleRelService() {
		if(this.sysUserRoleRelService == null) {
			this.sysUserRoleRelService = ECSpringContextUtils.getBean(SysUserRoleRelService.class);
		}
		return this.sysUserRoleRelService;
	}
	

	private SysRolePermRelService getSysRolePermRelService() {
		if(this.sysRolePermRelService == null) {
			this.sysRolePermRelService = ECSpringContextUtils.getBean(SysRolePermRelService.class);
		}
		return this.sysRolePermRelService;
	}
	
	/**
	 * 判断是否ajax请求
	 * @param request
	 * @return
	 */
	private boolean isAjaxRequest(HttpServletRequest request) {
		boolean res = false;
		String requestType = request.getHeader("X-Requested-With");
		if("XMLHttpRequest".equalsIgnoreCase(requestType)) {
			res = true;
		}
		return res;
	}
}
