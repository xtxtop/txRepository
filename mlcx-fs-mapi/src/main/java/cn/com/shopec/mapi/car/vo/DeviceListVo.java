package cn.com.shopec.mapi.car.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class DeviceListVo implements Serializable{

	/** 
	*
	* @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么) 
	*/ 
	private static final long serialVersionUID = -369828071333291640L;
	

	// 终端编号
	private String tBoxId;
	// 终端序列号
	private String deviceSn;
	// 品牌名称（厂家名称）
	private String factory;
	// 设备型号
	private String model;
	// SIM卡号
	private String sim;
	// 版本号
	private String version;
	//联网情况  联网情况 INT类型 1联网 2 未联网
	private String connectState;
	//车架号(车辆识别号)
	private String vin;
	//iccid
	private String iccid;
	public String gettBoxId() {
		return tBoxId;
	}
	public void settBoxId(String tBoxId) {
		this.tBoxId = tBoxId;
	}
	public String getDeviceSn() {
		return deviceSn;
	}
	public void setDeviceSn(String deviceSn) {
		this.deviceSn = deviceSn;
	}
	public String getFactory() {
		return factory;
	}
	public void setFactory(String factory) {
		this.factory = factory;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getSim() {
		return sim;
	}
	public void setSim(String sim) {
		this.sim = sim;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getConnectState() {
		return connectState;
	}
	public void setConnectState(String connectState) {
		this.connectState = connectState;
	}
	public String getVin() {
		return vin;
	}
	public void setVin(String vin) {
		this.vin = vin;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getIccid() {
		return iccid;
	}
	public void setIccid(String iccid) {
		this.iccid = iccid;
	}
	
	
	
		
		
		
		
}
