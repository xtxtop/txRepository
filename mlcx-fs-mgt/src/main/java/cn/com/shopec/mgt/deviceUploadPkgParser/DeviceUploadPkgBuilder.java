package cn.com.shopec.mgt.deviceUploadPkgParser;

/**
 * 设备上报数据包对象的构建器
 *
 */
public abstract class DeviceUploadPkgBuilder<T extends DeviceUploadPkg> {
	
	/**
	 * 根据输入参数构建设备上报数据包的对象
	 * @param deviceSn
	 * @param cmdCode
	 * @param msgLength
	 * @param msg
	 * @return
	 */
	public abstract T buildPackage(String deviceSn, String cmdCode, Integer msgLength, String msg);
	

}
