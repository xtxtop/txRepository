package cn.com.shopec.mgt.common.filter;

import java.io.IOException;

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


/**
 * Xss漏洞处理类
 *
 */
public class XssFilter implements Filter {
	
	private static final Log log = LogFactory.getLog(XssFilter.class);
	
	/**
	 * 请求类型
	 */
	private static final String HTTP_METHOD_GET = "GET";
	
	private static final String HTTP_METHOD_POST = "POST";
	

	public void init(FilterConfig conf) throws ServletException {
		
	}

	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest request = (HttpServletRequest)req;
		HttpServletResponse response = (HttpServletResponse)resp;
		
		if(HTTP_METHOD_GET.equalsIgnoreCase(request.getMethod()) ){
			//当HTTP请求为GET时，调用Xss漏洞处理
			request = new XssHttpServletRequestWrapper(request);
		}
		if(HTTP_METHOD_POST.equalsIgnoreCase(request.getMethod())){
			//当HTTP请求为POST时，调用Xss漏洞处理
			request = new XssHttpServletRequestWrapper(request);
		}
		chain.doFilter(request, response);
		return;
	}
	
	public void destroy() {
		
	}

	
	
	
}