package cn.com.shopec.mgt.deviceUploadPkgParser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 设备上传数据包解析器
 *
 */
public class DeviceUploadPkgParser {
	
	private static final Log log = LogFactory.getLog(DeviceUploadPkgParser.class);
	private static Map<String, DeviceUploadPkgBuilder<? extends DeviceUploadPkg>> builders = new HashMap<String, DeviceUploadPkgBuilder<? extends DeviceUploadPkg>>();
	
	static {
		init();
	}
	/**
	 * 解析字符串，得到DeviceUploadPackage对象
	 * @param packageDataStr 包数据的字符串
	 * @return
	 */
	public static DeviceUploadPkg parseString2DeviceUploadPackage(String packageDataStr) {
		DeviceUploadPkg pack = null;
		if(packageDataStr == null || packageDataStr.length() == 0){
			log.error("Invalid argument, packageDataStr=" + packageDataStr); 
			return pack;
		}
		if(!packageDataStr.startsWith(ApiConstants.DEVICE_UPLOAD_PACKAGE_HEAD)) { //包头不匹配
			log.error("Invalid argument, packageDataStr=" + packageDataStr);
			return pack;
		}
		
		try {
			List<String> tokens = new ArrayList<String>();
			int i = 0, j = 0, n = 0;
			boolean flag = true;
			do {
				j = packageDataStr.indexOf(',', i);
				if(j > 0) {
					String token = packageDataStr.substring(i, j);
					tokens.add(token);
					i = j + 1;
					n++;
					if(n == 4) {
						flag = false;
					}
					
				}
			} while (j > 0 && flag);

			int idxRn = packageDataStr.lastIndexOf(ApiConstants.DEVICE_UPLOAD_PACKAGE_TAIL);
			if(idxRn >=0) {
				tokens.add(packageDataStr.substring(i, idxRn));
			} else {
				tokens.add(packageDataStr.substring(i));
			}
			
			if(tokens.size() == 5) { //数据域足够，尝试进一步解析命令并构建命令对象
				String deviceSn = tokens.get(1);
				String cmdCode = tokens.get(2);
				Integer msgLength = parseStr2Int(tokens.get(3));
				String msg = tokens.get(4);
				
				pack = createPackage(deviceSn, cmdCode, msgLength, msg); //调用数据包对象的工程，构建命令对象
				
			} else {
				log.error("Invalid device upload package, " + packageDataStr);
			}			
		} catch (Exception e) {
			log.error(e.getMessage(), e); 
		}
		
		return pack;
	}
	/**
	 * 根据输入参数构建设备上报数据包的对象
	 * @param deviceSn
	 * @param cmdCode
	 * @param msgLength
	 * @param msg
	 * @return
	 */
	public static DeviceUploadPkg createPackage(String deviceSn, String cmdCode, Integer msgLength, String msg) {
		if(!DeviceUploadPkgParser.isValidDeviceUploadPkgArgument(deviceSn, cmdCode, msgLength, msg)) { //参数无效
			log.error("Invalid argument, deviceSn=" + deviceSn + ", cmdCode=" + cmdCode + ", msgLength=" + msgLength + ", msg="+msg);
			return null;
		}
	
		DeviceUploadPkgBuilder<? extends DeviceUploadPkg> builder = builders.get(cmdCode);
		if(builder == null) {
			log.error("Builder not found, cmdCode=" + cmdCode);
			return null;
		}
		
		DeviceUploadPkg pack = builder.buildPackage(deviceSn, cmdCode, msgLength, msg); //利用得到的构建器，创建上报数据包的对象
		
		return pack;
	}
	/**
	 * 是否有效的DeviceUploadPkg对象参数
	 * @param deviceSn
	 * @param cmdCode
	 * @param msgLength
	 * @param msg
	 * @return
	 */
	public static boolean isValidDeviceUploadPkgArgument(String deviceSn, String cmdCode, Integer msgLength, String msg) {
		boolean res = false;
		if(cmdCode == null || cmdCode.length() != 2) {
			return res;
		}
		if(deviceSn == null || deviceSn.length() != 12) {
			return res;
		}
		if(msgLength == null || msgLength < 0) {
			return res;
		}
		if(msg == null || msg.getBytes().length != msgLength) { //基于字节数组的长度进行判断
			return res;
		}
		res = true;
		return res;
	}
	
	/**
	 * 是否有效的DeviceUploadPkg对象
	 * @param pkg
	 * @return
	 */
	public static boolean isValidDeviceUploadPkg(DeviceUploadPkg pkg) {
		boolean res = false;
		if(pkg == null) {
			return res;
		}
		res = isValidDeviceUploadPkgArgument(pkg.getDeviceSn(), pkg.getCmdCode(), pkg.getMsgLength(), pkg.getMsg());
		
		return res;
	}
	
	/**
	 * 字符串转Integer
	 * @param str
	 * @return
	 */
	private static Integer parseStr2Int(String str) {
		Integer i = null;
		if(str == null || str.length() == 0) {
			return i;
		}
		try {
			i = Integer.parseInt(str);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return i;
	}
	
	public static void main(String[] args) {
//		String msgStr = "$E6,123456789012,00,2,00\r\n"; //valid str
		String msgStr = "\r\n"; //invalid str
		
		List<String> tokens = new ArrayList<String>();
		int i = 0, j = 0, n = 0;
		boolean flag = true;
		do {
			j = msgStr.indexOf(',', i);
			if(j > 0) {
				String token = msgStr.substring(i, j);
				tokens.add(token);
				i = j + 1;
				n++;
				if(n == 4) {
					flag = false;
				}
				
			}
		} while (j > 0 && flag);

		int idxRn = msgStr.lastIndexOf("\r\n");
		if(idxRn >=0) {
			tokens.add(msgStr.substring(i, idxRn));
		}
		System.out.println(tokens);
	}
	/**
	 * 初始化各个构建器 
	 */
	private static void init() {
		
		builders.put(DeviceUploadPkgCmd24Builder.builderCmdCode, new DeviceUploadPkgCmd24Builder());
		
	}
}
