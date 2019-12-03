package cn.com.shopec.core.uploadpkg.dao;

import java.util.Date;
import java.util.List;

import cn.com.shopec.core.common.BaseDao;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.uploadpkg.model.DeviceUploadpkgLog;

/**
 * DeviceUploadpkgLog 数据库处理类
 */
public interface DeviceUploadpkgLogDao extends BaseDao<DeviceUploadpkgLog,String> {

	public List<DeviceUploadpkgLog> pageListDeviceUploadPkgLog(Query q);

	public List<DeviceUploadpkgLog> deviceUploadpkgLogList(Query q);
	
	public int countListDeviceUploadPkgLog(Query q);

	public List<DeviceUploadpkgLog> getDeviceUploadpkgLogMaxTime();

	public void delDeviceUploadpkgLog(String deviceSn, Date createTime);
}
