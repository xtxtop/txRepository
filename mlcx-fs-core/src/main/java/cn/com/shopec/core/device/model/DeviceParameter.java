package cn.com.shopec.core.device.model;

import java.util.Date;

import cn.com.shopec.core.common.Entity;

/**
 * Device 数据实体类
 */
public class DeviceParameter extends Entity<String> {

	private static final long serialVersionUID = 1l;

	/* Auto generated properties start */


	// 终端序列号
	private String deviceSn;
	// 终端供应商
	private String brandName;
	// 设备型号
	private String deviceModel;
	//sim卡iccid
	private String iccid;
	//msisdn
	private String msisdn;
	//终端软件版本号
	private String softwareVersion;
	//终端硬件版本号
	private String hardwareVersion;
	//vin码
	private String vin;
	// 终端租赁模式（01 非租赁模式  02 租赁模式）
	private String leaseMode;
	//平台域名(IP)
	private String domainName;
	//平台端口
	private String port;
	//实时上报的频率
	private  String frequency;
	public String getDeviceSn() {
		return deviceSn;
	}
	public void setDeviceSn(String deviceSn) {
		this.deviceSn = deviceSn;
	}
	public String getBrandName() {
		return brandName;
	}
	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}
	public String getDeviceModel() {
		return deviceModel;
	}
	public void setDeviceModel(String deviceModel) {
		this.deviceModel = deviceModel;
	}
	public String getIccid() {
		return iccid;
	}
	public void setIccid(String iccid) {
		this.iccid = iccid;
	}
	public String getMsisdn() {
		return msisdn;
	}
	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}
	public String getSoftwareVersion() {
		return softwareVersion;
	}
	public void setSoftwareVersion(String softwareVersion) {
		this.softwareVersion = softwareVersion;
	}
	public String getHardwareVersion() {
		return hardwareVersion;
	}
	public void setHardwareVersion(String hardwareVersion) {
		this.hardwareVersion = hardwareVersion;
	}
	public String getVin() {
		return vin;
	}
	public void setVin(String vin) {
		this.vin = vin;
	}
	
	public String getLeaseMode() {
		return leaseMode;
	}
	public void setLeaseMode(String leaseMode) {
		this.leaseMode = leaseMode;
	}
	public String getDomainName() {
		return domainName;
	}
	public void setDomainName(String domainName) {
		this.domainName = domainName;
	}
	
	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
	}
	public String getFrequency() {
		return frequency;
	}
	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}
		
	
	
	
	

}
