package cn.com.shopec.core.device.service;

import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.core.common.BaseService;
import cn.com.shopec.core.device.model.DeviceUploadFile;

/**
 * DeviceUploadFileService 服务接口类
 */
public interface DeviceUploadFileService extends BaseService {
	
	public ResultInfo<DeviceUploadFile> addDeviceUploadFile(DeviceUploadFile deviceUploadFile);

}
