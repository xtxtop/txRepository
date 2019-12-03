package cn.com.shopec.core.system.service;

import cn.com.shopec.core.system.model.SysParam;

/**
 * 系统参数的缓存服务接口
 *
 */
public interface SysParamCacheService {

	/**
	 * 根据系统参数的key，从缓存中取回系统参数对象
	 * @param key
	 * @return
	 */
	public SysParam getSysParamFromCacheByParamKey(String key);
	
	/**
	 * 根据系统参数的key，从缓存中取回系统参数的值
	 * @param key
	 * @return
	 */
	public String getSysParamValueFromCacheByParamKey(String key);
	
	/**
	 * 将SysParam对象存入缓存
	 * @param sysParam
	 */
	public void putSysParamToCache(SysParam sysParam);
}
