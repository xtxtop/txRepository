package cn.com.shopec.mgt.deviceUploadPkgParser;

import java.io.Serializable;

/**
 * 设备上传数据包
 *
 */
public class DeviceUploadPkg implements Serializable {

	private static final long serialVersionUID = -682060155815391632L;

	private String deviceSn; //设备序列号
	
	private String cmdCode; //命令字
	
	private Integer msgLength; //消息体长度
	
	private String msg; //消息体

	public String getDeviceSn() {
		return deviceSn;
	}

	public void setDeviceSn(String deviceSn) {
		this.deviceSn = deviceSn;
	}

	public String getCmdCode() {
		return cmdCode;
	}

	public void setCmdCode(String cmdCode) {
		this.cmdCode = cmdCode;
	}

	public Integer getMsgLength() {
		return msgLength;
	}

	public void setMsgLength(Integer msgLength) {
		this.msgLength = msgLength;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	@Override
	public String toString() {
		return "DeviceUploadPackage [deviceSn=" + deviceSn + ", cmdCode=" + cmdCode + ", msgLength=" + msgLength
				+ ", msg=" + msg + "]";
	}
	
	/**
	 * 生成消息数据包
	 * @return
	 */
	public String toMsgPkgStr() {
		return ApiConstants.DEVICE_UPLOAD_PACKAGE_HEAD + "," + deviceSn + "," + cmdCode + "," + msgLength + "," + msg + ApiConstants.DEVICE_UPLOAD_PACKAGE_TAIL;
	}
}
