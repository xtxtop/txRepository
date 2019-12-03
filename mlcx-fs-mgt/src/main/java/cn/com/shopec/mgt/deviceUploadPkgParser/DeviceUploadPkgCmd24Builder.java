package cn.com.shopec.mgt.deviceUploadPkgParser;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 设备上报数据包-24命令(设备实时状态数据包）的对象构建器
 *
 */
public class DeviceUploadPkgCmd24Builder extends DeviceUploadPkgBuilder<DeviceUploadPkgCmd24> {

	private static final Log log = LogFactory.getLog(DeviceUploadPkgCmd24Builder.class);
	
	public  static final String builderCmdCode = "24";
	
	/**
	 * 根据输入参数构建设备上报数据包的对象
	 * @param deviceSn
	 * @param cmdCode
	 * @param msgLength
	 * @param msg
	 * @return
	 */
	public DeviceUploadPkgCmd24 buildPackage(String deviceSn, String cmdCode, Integer msgLength, String msg) {
		DeviceUploadPkgCmd24 cmd = null;
		
		if(!builderCmdCode.equals(cmdCode)) {
			log.error("Builder is not matched the cmdCode,cmdCode=" + cmdCode);
			return cmd;
		}
		
		String[] tokens = msg.split(",");
		if(tokens.length >= 17) {
			cmd = new DeviceUploadPkgCmd24();
			cmd.setDeviceSn(deviceSn);
			cmd.setCmdCode(cmdCode);
			cmd.setMsgLength(msgLength);
			cmd.setMsg(msg);

			cmd.setTimestamp(tokens[0]); //上报时间戳
			cmd.setKeyStatus(tokens[1]); //钥匙状态
			cmd.setCarDoorStatus(tokens[2]); //车门状态
			cmd.setGear(tokens[3]); //档位
			cmd.setSpeed(Double.parseDouble(tokens[4])); //当前车速
			cmd.setTotalMileage(Double.parseDouble(tokens[5])); //总里程
			cmd.setAuxBatteryVoltage(Double.parseDouble(tokens[6])); //汽车电瓶电压
			cmd.setVoltage(Double.parseDouble(tokens[7])); //整体电压
			cmd.setCurrent(Double.parseDouble(tokens[8])); //整体电流
			cmd.setRemainPower(Double.parseDouble(tokens[9])); //剩余电量百分比
			cmd.setRange(Double.parseDouble(tokens[10])); //续航里程
			cmd.setChargingStatus(tokens[11]); //充电状态
			cmd.setSignal(Double.parseDouble(tokens[12])); //网络信号值
			cmd.setLocationStatus(tokens[13]); //定位状态
			cmd.setSatelliteNum(Integer.parseInt(tokens[14])); //卫星有效数量
			Double longitude = Double.parseDouble(tokens[15]); //定位经度
			Double latitude = Double.parseDouble(tokens[16]); //定位
			LongitudeAndLatitude location = new LongitudeAndLatitude(longitude, latitude);
			cmd.setLocation(location);
			
			if(tokens.length > 17) {
				cmd.setCourseAngle(Double.parseDouble(tokens[17])); //航向角度
			}
			
		}
		
		return cmd;
	}

	public static void main(String[] args) {
		DeviceUploadPkgBuilder<DeviceUploadPkgCmd24> builder = new DeviceUploadPkgCmd24Builder();
		String msg = "2017-03-31 03:01:24,255,1,255,0,0,12.4,0.0,-300.0,0,0,0,31,1,20,114.348999,22.715929,0.00,0,0";
		DeviceUploadPkgCmd24 cmd = (DeviceUploadPkgCmd24)builder.buildPackage("AB0123456789", "24", msg.length(), msg);
		System.out.println(cmd);
		
		
	}
}
