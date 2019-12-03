package cn.com.shopec.mgt.common.filter;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
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
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.com.shopec.common.utils.ECPropertiesUtil;

/**
 * 会话劫持（csrf跨站请求伪造）
 *
 */
public class SessionFilter implements Filter {

	private static final Log log = LogFactory.getLog(SessionFilter.class);

	/**
	 * csrf跨站请求伪造的url的SET
	 */
	private final static Set<String> HTML_EXCLUDE_PATHS = new HashSet<String>();

	/**
	 * session会话标识未更新需要过滤的url（一般是登录url）
	 */
	private final static Set<String> HTML_LOGIN_PATHS = new HashSet<String>();

	private final static Set<String> HTML_EXPATHS = new HashSet<String>();

	static {
		// csrf跨站请求伪造url
		HTML_EXCLUDE_PATHS.add("/login.do");// 登录

		// 会话标识未更新需要过滤的url
		HTML_LOGIN_PATHS.add("/login.do");
		HTML_EXPATHS.add("/index.do");
	}
	public String domain;
	public String[] domains;

	public void init(FilterConfig conf) throws ServletException {
		ECPropertiesUtil propertiesUtil = new ECPropertiesUtil();
		propertiesUtil.load(SessionFilter.class.getClassLoader().getResource("/").getPath() + "freemarker.properties");
		domain = propertiesUtil.getStringProperty("domains");
		domains = domain.split(",");
	}

	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;

		String referer = request.getHeader("referer");
		// 请求路径
		String path = request.getServletPath();

		if (referer != null && domains != null && domains.length > 0 && !HTML_EXPATHS.contains(path)) {
			boolean flag = false;
			for (int i = 0; i < domains.length; i++) {
				if (referer.startsWith(domains[i])) {
					flag = true;
					break;
				}
			}
			if (!flag) {
				response.sendRedirect("error");
				return;
			}
		}
		// session参数(可以自己生成也可以用sessionid),防止登录session更新
		String serverSessionId = (String) request.getSession().getId();
		// 如果是需要拦截的登录url
		if (HTML_LOGIN_PATHS.contains(path)) {
			HttpSession session = request.getSession();
			// 首先将原session中的数据转移到一临时map中
			Map<String, Object> tempMap = new HashMap();
			Enumeration<String> sessionNames = session.getAttributeNames();
			while (sessionNames.hasMoreElements()) {
				String sessionName = sessionNames.nextElement();
				tempMap.put(sessionName, session.getAttribute(sessionName));
			}
			log.info("######################################################OldSession=" + session.getId());

			// 注销原session，为的是重置sessionId
			session.invalidate();
			log.info("######################################################session=" + session.getId());

			// 将临时map中的数据转移至新session中
			session = request.getSession(true);
			log.info("######################################################NewSession=" + session.getId());

			for (Map.Entry<String, Object> entry : tempMap.entrySet()) {
				session.setAttribute(entry.getKey(), entry.getValue());
			}

		}

		// 页面参数
		String clientSessionId = req.getParameter("ssid");

		// 更新后重新设置
		request.getSession().setAttribute("skey", (String) request.getSession().getId());

		if (HTML_EXCLUDE_PATHS.contains(path)) {
			// 如果不相等则可以判断是跨站请求伪造
			if (!serverSessionId.equals(clientSessionId)) {
				if (HTML_LOGIN_PATHS.contains(path)) {
					// 跳转到错误页面
					response.sendRedirect("/fs-mgt/index.do");
					return;
				} else {
					// 跳转到错误页面
					response.sendRedirect("error");
					return;
				}
			}
		}

		chain.doFilter(request, response);
		return;
	}

	public void destroy() {

	}

}