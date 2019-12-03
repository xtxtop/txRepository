package cn.com.shopec.core.map.service.impl;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.logging.Log;

import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.common.constants.Constant;
import cn.com.shopec.common.utils.HttpURLRequestUtil;
import cn.com.shopec.common.utils.httpClient.HttpClientUtil;
import cn.com.shopec.core.map.model.GeocoderParam;
import cn.com.shopec.core.map.model.GeocoderResultInfo;
import cn.com.shopec.core.map.service.BaiduGeocoderApiService;

@Service
public class BaiduGeocoderApiServiceImpl implements BaiduGeocoderApiService {
	private static final Log log = LogFactory.getLog(BaiduGeocoderApiServiceImpl.class);
	// 百度ak
	@Value("${ak}")
	private String AK;
	// 逆地理编码服务uri
	private final String geocoderURI = "http://api.map.baidu.com/geocoder/v2/";

	// http://api.map.baidu.com/geocoder/v2/?ak=E4805d16520de693a3fe707cdc962045&callback=renderReverse&location=39.983424,116.322987&output=json&pois=1
	@Override
	public ResultInfo<GeocoderResultInfo> geocoder(GeocoderParam geocoder) {
		ResultInfo<GeocoderResultInfo> res = new ResultInfo<GeocoderResultInfo>();
		if (geocoder.getCoordtype() == null || "".equals(geocoder.getCoordtype())) {
			res.setCode(Constant.FAIL);
			res.setMsg("请设置坐标类型");
		} else if (geocoder.getLocation() == null || "".equals(geocoder.getLocation())) {
			res.setCode(Constant.FAIL);
			res.setMsg("请设置坐标");
		} else {
			String url = geocoderURI + "?ak=" + AK + "&output=json&pois=0&coordtype="
					+ geocoder.getCoordtype() + "&location=" + geocoder.getLocation();
			System.err.println(url);
			String result = "";
			try {
				result = HttpClientUtil.get(url);
				Gson gson = new Gson();
				GeocoderResultInfo geocoderResultInfo = gson.fromJson(result, GeocoderResultInfo.class);
				res.setCode(Constant.SECCUESS);
				res.setData(geocoderResultInfo);
			} catch (Exception e) {
				log.error("geocoder异常" + result);
			}
		}
		return res;
	}

	public static void main(String[] args) {
		BaiduGeocoderApiServiceImpl b = new BaiduGeocoderApiServiceImpl();
		GeocoderParam geocoder = new GeocoderParam();
		geocoder.setCoordtype("wgs84ll");
		geocoder.setLocation("22.59518100071114,113.96871938999998");
		ResultInfo<GeocoderResultInfo> res = b.geocoder(geocoder);
		System.err.println(res.getCode()+"-"+res.getData().getResult().getFormatted_address());
	}
	
	@Override
	public String getStringBaidu(String key) {
		InputStream in = null;
		try {
			Properties props = new Properties();
			in = new BufferedInputStream(new FileInputStream(
					(getPath() + "baidu.properties")));
			props.load(in);
			String value = props.getProperty(key);
			return value;
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			if (null != in){
				try {
					in.close();
					in = null;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	private String getPath() {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * 根据Gps坐标调用百度接口返回地址
	 * 
	 * @param latitude
	 * @param longitude
	 * @return
	 */
	@Override
	public String getAddressByGPS(Double latitude, Double longitude) {
		if(latitude == null || longitude == null){
			return "";
		}
		GeocoderParam geocoder = new GeocoderParam();
		geocoder.setCoordtype("wgs84ll");
		geocoder.setLocation(latitude + "," + longitude);
		ResultInfo<GeocoderResultInfo> res = this.geocoder(geocoder);
		if (res.getCode().equals("1")) {
			GeocoderResultInfo result = res.getData();
			if (result.getStatus() == 0) {
				return result.getResult().getFormatted_address();
			}
		}
		return "";
	}
}
