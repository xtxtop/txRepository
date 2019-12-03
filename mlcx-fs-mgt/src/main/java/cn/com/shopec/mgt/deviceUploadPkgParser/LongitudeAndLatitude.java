package cn.com.shopec.mgt.deviceUploadPkgParser;

import java.io.Serializable;

/**
 * 经纬度
 *
 */
public class LongitudeAndLatitude implements Serializable {

	private static final long serialVersionUID = 5202332730010488522L;

	private double longitude; //经度
	
	private double latitude; //纬度
	
	public LongitudeAndLatitude() {
		
	}
	
	public LongitudeAndLatitude(double longitude, double latitude) {
		this.longitude = longitude;
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	@Override
	public String toString() {
		return "LongitudeAndLatitude [longitude=" + longitude + ", latitude=" + latitude + "]";
	}
	
	
}
