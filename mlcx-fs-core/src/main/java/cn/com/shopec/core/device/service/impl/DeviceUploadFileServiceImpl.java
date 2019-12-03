package cn.com.shopec.core.device.service.impl;

import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.common.constants.Constant;
import cn.com.shopec.core.device.dao.DeviceUploadFileDao;
import cn.com.shopec.core.device.model.DeviceUploadFile;
import cn.com.shopec.core.device.service.DeviceUploadFileService;

@Service
public class DeviceUploadFileServiceImpl implements DeviceUploadFileService {

	@Resource
	DeviceUploadFileDao deviceUploadFileDao;
	
	public ResultInfo<DeviceUploadFile> addDeviceUploadFile(DeviceUploadFile deviceUploadFile){
		ResultInfo<DeviceUploadFile> resultInfo = new ResultInfo<>();
		deviceUploadFile.setId(UUID.randomUUID().toString());
		deviceUploadFileDao.add(deviceUploadFile);
		resultInfo.setCode(Constant.SECCUESS);
		return resultInfo;
	}
	
}
