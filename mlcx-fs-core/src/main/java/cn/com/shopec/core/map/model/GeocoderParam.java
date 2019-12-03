package cn.com.shopec.core.map.model;

import java.io.Serializable;
/**
 * 逆地理编码专属请求参数实体类
 * @author machao
 *
 */
public class GeocoderParam implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1818377464815033545L;
	//坐标类型 bd09ll（百度经纬度坐标）、bd09mc（百度米制坐标）、gcj02ll（国测局经纬度坐标）、wgs84ll（ GPS经纬度）
	private String coordtype;
	//坐标 格式：lat<纬度>,lng<经度>,如：38.76623,116.43213
	private String location;
	//是否显示指定位置周边的poi，0为不显示，1为显示。当值为1时，显示周边100米内的poi。
	private Integer pois;
	public String getCoordtype() {
		return coordtype;
	}
	public void setCoordtype(String coordtype) {
		this.coordtype = coordtype;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public Integer getPois() {
		return pois;
	}
	public void setPois(Integer pois) {
		this.pois = pois;
	}
	
}
