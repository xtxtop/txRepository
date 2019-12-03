package cn.com.shopec.core.device.dao;

import cn.com.shopec.core.common.BaseDao;
import cn.com.shopec.core.device.model.DeviceFirmwareFile;

/**
 * 设备固件文件表 数据库处理类
 */
public interface DeviceFirmwareFileDao extends BaseDao<DeviceFirmwareFile,String> {

	/**
	 * 根据固件类型及车型id，取得对应固件文件的信息
	 * @param firmwareType
	 * @param carModelId
	 * @return
	 */
	public DeviceFirmwareFile getByFirmwareTypeAndCarModelId(int firmwareType, String carModelId);
}
