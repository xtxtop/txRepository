package cn.com.shopec.core.ml.dao;

import cn.com.shopec.core.common.BaseDao;
import cn.com.shopec.core.ml.model.COperatingCity;

/**
 * COperatingCity 数据库处理类
 */
public interface COperatingCityDao extends BaseDao<COperatingCity, String> {
	/**
	 * 根据经纬度获取最近的运营城市信息
	 * 
	 * @param longitude
	 *            经度
	 * @param latitude
	 *            纬度
	 * @return
	 */
	public COperatingCity getNearOperatingCity(String longitude, String latitude);
}
