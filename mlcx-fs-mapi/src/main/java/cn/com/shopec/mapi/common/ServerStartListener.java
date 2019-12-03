package cn.com.shopec.mapi.common;

import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ServerStartListener implements ServletContextListener {

	private static final Log log = LogFactory.getLog(ServerStartListener.class);
	
	
	@Override
	public void contextDestroyed(ServletContextEvent context) {
		
	}

	@Override
	public void contextInitialized(ServletContextEvent context) {
		log.info("====================ServerStartListener.contextInitialized()======================");
		ConcurrentHashMap<String, String> map = new ConcurrentHashMap<String, String>();
		//还车uri
		map.put("returnCar", "app/car/returnCar.do");
		//微信支付uri（订单支付，套餐订单支付，押金支付）
		map.put("wxpayURI", "app/pay/wxGetPrepayId.do");
		//微信支付uri（订单支付，套餐订单支付，押金支付）
		map.put("alipayURI", "app/pay/alipayGetOrderStr.do");
		context.getServletContext().setAttribute("interceptorURIMap", map);
		ConcurrentHashMap<String, String> requestURIMap = new ConcurrentHashMap<String, String>();
		context.getServletContext().setAttribute("requestURIMap", requestURIMap);
	}

}
