package cn.com.shopec.common.cache.redis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import cn.com.shopec.common.cache.CommonCacheUtil;
import cn.com.shopec.common.utils.ECSerializeUtil;

/**
 * 公用缓存工具接口的实现类（基于redis实现）
 *
 */
//@Component
public class CommonCacheUtilRedisImpl implements CommonCacheUtil {
	
	private static final Log log = LogFactory.getLog(CommonCacheUtilRedisImpl.class);

	@Resource
	private RedisUtil redisUtil;
	
	/**
	 * 根据key，从缓存中取回字符串值
	 * @param key 缓存key
	 * @return
	 */
	public String get(String key) {
		if(key == null) {
			return null;
		}
		return redisUtil.get(key);
	}
	
	/**
	 * 根据一组key，从缓存中取回一组字符串值
	 * @param keys
	 * @return
	 */
	public String[] get(final String[] keys) {
		String[] resValueArr = null;
		if(keys == null || keys.length == 0) {
			resValueArr = new String[0];
			return resValueArr;
		}
		//使用redis命令管道
		PipelineExecute pe = new PipelineExecute() {
			@Override
			public void execute() throws Exception {
				for(String key : keys) {
					super.get(key);
				}
			}
		};

		List<Object> list = redisUtil.pipelineExecute(pe);
		
		if(list != null) {
			resValueArr = new String[list.size()];
			int i = 0;
			for(Object obj : list) {
				resValueArr[i++] = obj == null ? null : obj.toString();
			}
		}
		
		return resValueArr;
	}

	/**
	 * 根据一组key，从缓存中取回一组字符串值，取回的字符串存在Map中返回。
	 * @param keys
	 * @return
	 */
	public Map<String, String> getToMap(final String[] keys) {
		Map<String, String> resMap = new HashMap<String, String>();
		
		if(keys == null || keys.length == 0) {
			return resMap;
		}
		
		String[] valueArr = this.get(keys);
		if(valueArr != null && valueArr.length != 0) {
			for(int i = 0 ; i < keys.length ; i++) {
				resMap.put(keys[i], valueArr[i]);
			}
		}
		
		return resMap;
	}
	
	/**
	 * 根据key，将字符串值存入缓存，如果之前相同key已存在，则覆盖原有的值
	 * @param key 缓存key
	 * @param value 存入的值
	 * @return
	 */
	public boolean set(String key, String value) {
		boolean res = false;
		if(key == null) {
			return res;
		}
		try {
			redisUtil.set(key, value);
			res = true;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return res;
	}
	
	/**
	 * 根据key，将字符串值存入缓存，如果之前相同key已存在，则覆盖原有的值。
	 * 存入时，为该数据设置一个过期的时间（单位：秒），如果过期时间为负数值，则永久保留。
	 * @param key 缓存key
	 * @param value 存入的值
	 * @param expire 过期时间（秒）
	 * @return
	 */
	public boolean set(String key, String value, int expire) {
		if(expire < 0) {
			return this.set(key, value);
		}
		
		boolean res = false;
		if(key == null) {
			return res;
		} 
		try {
			redisUtil.setEx(key, expire, value);
			res = true;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return res;
	}

	/**
	 * 根据可以，从缓存中取出一个对象
	 * @param key 缓存key
	 * @return
	 */
	public Object getObject(String key) {
		Object res = null;
		if(key == null) {
			return res;
		}
		try {
			res = redisUtil.getObject(key);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return res;
	}
	
	/**
	 * 根据一组key，从缓存中取回一组对象
	 * @param keys
	 * @return
	 */
	public Object[] getObject(final String[] keys) {
		Object[] resValueArr = null;
		if(keys == null || keys.length == 0) {
			resValueArr = new Object[0];
			return resValueArr;
		}
		//使用redis命令管道
		PipelineExecute pe = new PipelineExecute() {
			@Override
			public void execute() throws Exception {
				for(String key : keys) {
					super.get(key.getBytes());
				}
			}
		};

		List<Object> list = redisUtil.pipelineExecute(pe);
		
		if(list != null) {
			resValueArr = new Object[list.size()];
			int i = 0;
			for(Object obj : list) {
				resValueArr[i++] = obj == null ? null : ECSerializeUtil.unserialize((byte[])obj);
			}
		}
		
		return resValueArr;
	}
	
	/**
	 * 根据一组key，从缓存中取回一组对象，取回的对象存在Map中返回。
	 * @param keys
	 * @return
	 */
	public Map<String, Object> getObjectToMap(final String[] keys) {
		Map<String, Object> resMap = new HashMap<String, Object>();
		
		if(keys == null || keys.length == 0) {
			return resMap;
		}
		
		Object[] valueArr = this.getObject(keys);
		if(valueArr != null && valueArr.length != 0) {
			for(int i = 0 ; i < keys.length ; i++) {
				resMap.put(keys[i], valueArr[i]);
			}
		}
		
		return resMap;
	}
	
	/**
	 * 根据key，将一个对象存入缓存，如果之前相同key已存在，则覆盖原有的值
	 * @param key 缓存key
	 * @param objValue 存入的对象
	 * @return
	 */
	public boolean setObject(String key, Object objValue) {
		boolean res = false;
		if(key == null || objValue == null) {
			return res;
		}
		try {
			redisUtil.setObject(key, objValue);
			res = true;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return res;
	}
	
	/**
	 * 根据key，将一个对象存入缓存，如果之前相同key已存在，则覆盖原有的值。
	 * 存入时，为该数据设置一个过期的时间（单位：秒），如果过期时间为负数值，则永久保留。
	 * @param key 缓存key
	 * @param objValue 存入的对象
	 * @param expire expire 过期时间（秒）
	 * @return
	 */
	public boolean setObject(String key, Object objValue, int expire) {
		if(expire < 0) {
			return this.setObject(key, objValue);
		}
		boolean res = false;
		if(key == null || objValue == null) {
			return res;
		}
		
		try {
			redisUtil.setObject(key, expire, objValue);
			res = true;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return res;
	}
	
	/**
	 * 根据key，删除缓存中的值
	 * @param key 缓存key
	 * @return
	 */
	public boolean del(String key) {
		boolean res = false;
		if(key == null) {
			return false;
		}
		try {
			long n = redisUtil.del(key);
			res = n == 1L;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return res;
	}
	
	/**
	 * 根据一组key，删除缓存中的值
	 * @params keys 缓存key数组
	 * @return
	 */
	public long del(String[] keys) {
		long res = 0;
		if(keys == null || keys.length == 0) {
			return res;
		}
		try {
			res = redisUtil.del(keys);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return res;
	}
	
	/**
	 * 根据key，增加缓存中对应key的值（此key之前应该无值，或值为整数值），并返回增加后的值
	 * @param key
	 * @param incrValue
	 * @return
	 */
	public Long incrNumValue(String key, long incrValue) {
		Long res = null;
		if(key == null) {
			return res;
		}
		
		try {
			res = redisUtil.incrBy(key, incrValue);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return res;
	}
	
	/**
	 * 根据key，减少缓存中对应key的值（此key之前应该无值，或值为整数值），并返回减少后的值
	 * @param key
	 * @param decrValue
	 * @return
	 */
	public Long decrNumValue(String key, long decrValue) {
		Long res = null;
		if(key == null) {
			return res;
		}
		
		try {
			res = redisUtil.decrBy(key, decrValue);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return res;
	}
}
