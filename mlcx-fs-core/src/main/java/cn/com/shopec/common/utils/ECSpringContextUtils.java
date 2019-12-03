package cn.com.shopec.common.utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * SpringContext的工具类
 *
 */
@Component
public class ECSpringContextUtils implements ApplicationContextAware {
	
	private static ApplicationContext applicationContext;
	private final Log log = LogFactory.getLog(ECSpringContextUtils.class);
	
	@Override
	public void setApplicationContext(ApplicationContext context)
			throws BeansException {
		ECSpringContextUtils.applicationContext = context;
		log.info("setApplicationContext, context = " + ECSpringContextUtils.applicationContext);
	}
	
	public static void setAppContext(ApplicationContext context) {
        applicationContext = context;
	}
	
	public static ApplicationContext getApplicationContext() {
        return applicationContext;
	}
	
	public static Object getBean(String beanName) {
		return applicationContext.getBean(beanName);
	}
	
	public static <T> T getBean(Class<T> requiredType) {
		return applicationContext.getBean(requiredType);
	}
	
}
