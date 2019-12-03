package cn.com.shopec.core.dailyrental.util;

import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class AllCouponHolder {

	private static final Log log = LogFactory.getLog(AllCouponHolder.class);
	
	private static AllCouponHolder instance = new AllCouponHolder();
	
	private final ConcurrentHashMap<String, String> couponMap  = new ConcurrentHashMap<String, String>(); //保存coupon的Map
	
	private AllCouponHolder() { //单例模式，私有的构造函数
		
	}
	
	/**
	 * 获得ChannelHolder的实例（单例模式）
	 * @return
	 */
	public static AllCouponHolder getInstance() {
		return instance;
	}
	
	public void putCouponNo(String memberNo, String couponNo) {
		couponMap.put(memberNo, couponNo);
	}
	
	public String getCoupon(String memberNo){
		return couponMap.get(memberNo);
	}
	public void deleteCoupon(String memberNo){
		couponMap.remove(memberNo);
	}
	
}
