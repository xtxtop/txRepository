package cn.com.shopec.core.map.service;

import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.core.map.model.GeocoderParam;
import cn.com.shopec.core.map.model.GeocoderResultInfo;

/**
 * 百度逆地理编码服务接口的service
 *
 */
public interface BaiduGeocoderApiService {

	/**
	 * 
	 * @param track
	 * @return
	 */
	public ResultInfo<GeocoderResultInfo> geocoder(GeocoderParam geocoder);
	//查找 百度key
	public String getStringBaidu(String key);
	
	String getAddressByGPS(Double latitude, Double longitude);
	
}
