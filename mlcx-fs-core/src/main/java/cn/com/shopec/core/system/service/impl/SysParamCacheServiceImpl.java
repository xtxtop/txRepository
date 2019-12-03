package cn.com.shopec.core.system.service.impl;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import cn.com.shopec.core.system.dao.SysParamDao;
import cn.com.shopec.core.system.model.SysParam;
import cn.com.shopec.core.system.service.SysParamCacheService;

/**
 * 系统参数缓存服务实现类
 *
 */
@Service
public class SysParamCacheServiceImpl implements SysParamCacheService {
	
	private static final Log log = LogFactory.getLog(SysParamCacheServiceImpl.class);
	
	 //使用map，实现缓存，key为sysParam的paramKey，value为sysParam对象
	private static final ConcurrentHashMap<String, SysParam> cachedSysParamMap = new ConcurrentHashMap<String, SysParam>();
	
	private static final int DEFAULT_CACHE_UPDATE_INTERVAL_SEC = 300; //缓存更新的时间间隔的默认值（秒）
	
	@Value("${sysparam.cache.update.interval.sec}")
	private int sysParamCacheUpdateIntervalSec; //系统参数缓存更新的时间间隔（秒）
	
	@Value("${sysparam.cache.available}")
	private boolean sysParamCacheAvailable; //系统参数是否缓存化
	
	@Resource
	private SysParamDao sysParamDao;
	
	private static Timer timer;

	/**
	 * 数据初始化
	 */
	@PostConstruct
	private void init() {
		log.info("###### SysParamCacheServiceImpl.init() ######");
		if(sysParamCacheAvailable && null==timer) {
			TimerTask task = new TimerTask() {
				public void run() {
					log.info("###### It's going to load sysParam's list to cache. #####");
					List<SysParam> sysParamList = sysParamDao.queryAll(null);
					if(sysParamList == null || sysParamList.isEmpty()) {
						return;
					}
					for(SysParam sysParam : sysParamList) {
						cachedSysParamMap.put(sysParam.getParamKey(), sysParam);
					}
					
				}
			};
			
			timer = new Timer(true);
			timer.schedule(task, 0, 1000L * (sysParamCacheUpdateIntervalSec <= 0 ? DEFAULT_CACHE_UPDATE_INTERVAL_SEC : sysParamCacheUpdateIntervalSec));
		}
	}
	
	/**
	 * 根据系统参数的key，从缓存中取回系统参数对象
	 * @param key
	 * @return
	 */
	public SysParam getSysParamFromCacheByParamKey(String key) {
		if(key == null) {
			return null;
		}
		return cachedSysParamMap.get(key);
	}
	
	/**
	 * 根据系统参数的key，从缓存中取回系统参数的值
	 * @param key
	 * @return
	 */
	public String getSysParamValueFromCacheByParamKey(String key) {
		if(key == null) {
			return null;
		}
		SysParam sysParam = cachedSysParamMap.get(key);
		return sysParam == null ? null : sysParam.getParamValue();
	}
	
	/**
	 * 将SysParam对象存入缓存
	 * @param sysParam
	 */
	public void putSysParamToCache(SysParam sysParam) {
		if(sysParam != null && sysParam.getParamKey() != null) {
			cachedSysParamMap.put(sysParam.getParamKey(), sysParam);
		}
	}
}
