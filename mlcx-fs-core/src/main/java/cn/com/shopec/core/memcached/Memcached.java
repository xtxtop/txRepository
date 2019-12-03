package cn.com.shopec.core.memcached;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.whalin.MemCached.SockIOPool;

import cn.com.shopec.core.system.service.SysParamService;

public class Memcached {

	public static void mem() throws InterruptedException {
		// memcached should be running on port 11211 but NOT on 11212

		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();

		ServletContext application = request.getSession().getServletContext();
		WebApplicationContext appctx = WebApplicationContextUtils.getWebApplicationContext(application);
		SysParamService sysParamService = appctx.getBean(SysParamService.class);

		String str = sysParamService.getByParamKey("缓存IP与端口").getParamValue();
		String[] servers = str.split(",");

		SockIOPool pool = SockIOPool.getInstance();
		pool.setServers(servers);
		pool.setFailover(true);
		pool.setInitConn(10);
		pool.setMinConn(5);
		pool.setMaxConn(250);
		pool.setMaintSleep(30);// set the sleep for the maint thread it will
								// wake up every x seconds and maintain the pool
								// size
		pool.setNagle(false);// Tcp的规则就是在发送一个包之前，本地机器会等待远程主机对上一次发送的包的确认信息到来；这个方法就可以关闭套接字的缓存，以至这个包准备好了就发；
		pool.setSocketTO(3000);// 连接建立后对超时的控制
		pool.setSocketConnectTO(0);// 连接建立时对超时的控制
		pool.setAliveCheck(true);
		pool.initialize();

		// MemCachedClient mcc = new MemCachedClient();
		//
		// mcc.set("test", "This is a test String", new Date(2000)); // 2秒后过期
		//
		// // 从cache里取值
		// System.out.println(mcc.get("test"));
		// Thread.sleep(2000);
		// System.out.println(mcc.get("test"));
	}

	// public static String getCache() {
	// return sysParamService.getNameByKey("缓存IP与端口").getParamValue();
	// }

}
