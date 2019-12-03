package cn.com.shopec.core.map.model;

import java.io.Serializable;

/**
 * 地址解析
 *
 */
public class GeocoderResultInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3778441377113611370L;
	
	//状态码,返回状态，0为成功 
	private Integer status;
	
	private GeocoderResult result;

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public GeocoderResult getResult() {
		return result;
	}

	public void setResult(GeocoderResult result) {
		this.result = result;
	}

	@Override
	public String toString() {
		return "GeocoderResultInfo [status=" + status + ", result=" + result + "]";
	}
	
}
