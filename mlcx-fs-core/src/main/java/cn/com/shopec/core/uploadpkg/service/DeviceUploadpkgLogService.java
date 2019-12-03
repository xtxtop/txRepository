package cn.com.shopec.core.uploadpkg.service;

import java.util.List;

import cn.com.shopec.common.Operator;
import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.core.common.BaseService;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.uploadpkg.model.DeviceUploadpkgLog;

/**
 * DeviceUploadpkgLog 服务接口类
 */
public interface DeviceUploadpkgLogService extends BaseService {

	/**
	 * 根据查询条件，查询并返回DeviceUploadpkgLog的列表
	 * @param q 查询条件
	 * @return
	 */
	public List<DeviceUploadpkgLog> getDeviceUploadpkgLogList(Query q);
	
	/**
	 * 根据查询条件，分页查询并返回DeviceUploadpkgLog的分页结果
	 * @param q 查询条件
	 * @return
	 */
	public PageFinder<DeviceUploadpkgLog> getDeviceUploadpkgLogPagedList(Query q);
	
	/**
	 * 根据主键，查询并返回一个DeviceUploadpkgLog对象
	 * @param id 主键id
	 * @return
	 */
	public DeviceUploadpkgLog getDeviceUploadpkgLog(String id);

	/**
	 * 根据主键数组，查询并返回一组DeviceUploadpkgLog对象
	 * @param ids 主键数组，数组中的主键值应当无重复值，如有重复值时，则返回的列表长度可能小于传入的数组长度
	 * @return
	 */
	public List<DeviceUploadpkgLog> getDeviceUploadpkgLogByIds(String[] ids);
	
	/**
	 * 根据主键，删除特定的DeviceUploadpkgLog记录
	 * @param id 主键id
	 * @param operator 操作人对象
	 * @return
	 */
	public ResultInfo<DeviceUploadpkgLog> delDeviceUploadpkgLog(String id, Operator operator);
	
	/**
	 * 新增一条DeviceUploadpkgLog记录，执行成功后传入对象及返回对象的主键属性值不为null
	 * @param deviceUploadpkgLog 新增的DeviceUploadpkgLog数据（如果无特殊需求，新增对象的主键值请保留为null）
	 * @param operator 操作人对象
	 * @return
	 */
	public ResultInfo<DeviceUploadpkgLog> addDeviceUploadpkgLog(DeviceUploadpkgLog deviceUploadpkgLog, Operator operator);
	
	/**
	 * 根据主键，更新一条DeviceUploadpkgLog记录
	 * @param deviceUploadpkgLog 更新的DeviceUploadpkgLog数据，且其主键不能为空
	 * @param operator 操作人对象
	 * @return
	 */
	public ResultInfo<DeviceUploadpkgLog> updateDeviceUploadpkgLog(DeviceUploadpkgLog deviceUploadpkgLog, Operator operator);

	/**
	 * 生成主键
	 * @return
	 */
	public String generatePK();
	
		/**
	 * 为DeviceUploadpkgLog对象设置默认值
	 * @param obj
	 */
	public void fillDefaultValues(DeviceUploadpkgLog obj);

	public PageFinder<DeviceUploadpkgLog> getDeviceUploadpkgLogDetailList(Query q);

	/**
	 * 清理几天前的车辆上下线日志
	 * 
	 * @param day
	 */
	public void delDeviceUploadpkgLog(int day);

}
