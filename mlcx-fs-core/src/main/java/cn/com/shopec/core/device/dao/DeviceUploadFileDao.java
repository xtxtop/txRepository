package cn.com.shopec.core.device.dao;

import java.util.List;

import cn.com.shopec.core.common.BaseDao;
import cn.com.shopec.core.device.model.DeviceUploadFile;

/**
 * Device 数据库处理类
 */
public interface DeviceUploadFileDao extends BaseDao<DeviceUploadFile, String> {
	
	public List<DeviceUploadFile> getByDeviceSn(String deviceSn);

}
