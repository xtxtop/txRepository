package cn.com.shopec.delivery.common;

import java.io.UnsupportedEncodingException;

import cn.com.shopec.core.order.model.Order;

public interface DeliveryInterface {
	
	/**
	 * 获取物流所在的配置文件的路径
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public String getPath() throws UnsupportedEncodingException;
	
	/**
	 * 读取配置文件信息
	 * @return
	 */
	public String getString(String key);
	
	/**
	 * 获取快递信息
	 * @param obj
	 * @return
	 */
	public String getDeliveryMessage(String url);
	
	/**
	 * 根据订单组装数据
	 */
	public String getKuaiDiInfo(Order order);

}
