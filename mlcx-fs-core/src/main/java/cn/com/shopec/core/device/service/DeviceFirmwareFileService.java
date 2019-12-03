package cn.com.shopec.core.device.service;

import java.util.List;

import cn.com.shopec.common.Operator;
import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.core.common.BaseService;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.device.model.DeviceFirmwareFile;

/**
 * 设备固件文件表 服务接口类
 */
public interface DeviceFirmwareFileService extends BaseService {

	/**
	 * 根据查询条件，查询并返回DeviceFirmwareFile的列表
	 * @param q 查询条件
	 * @return
	 */
	public List<DeviceFirmwareFile> getDeviceFirmwareFileList(Query q);
	
	/**
	 * 根据查询条件，分页查询并返回DeviceFirmwareFile的分页结果
	 * @param q 查询条件
	 * @return
	 */
	public PageFinder<DeviceFirmwareFile> getDeviceFirmwareFilePagedList(Query q);
	
	/**
	 * 根据主键，查询并返回一个DeviceFirmwareFile对象
	 * @param id 主键id
	 * @return
	 */
	public DeviceFirmwareFile getDeviceFirmwareFile(String id);

	/**
	 * 根据主键数组，查询并返回一组DeviceFirmwareFile对象
	 * @param ids 主键数组，数组中的主键值应当无重复值，如有重复值时，则返回的列表长度可能小于传入的数组长度
	 * @return
	 */
	public List<DeviceFirmwareFile> getDeviceFirmwareFileByIds(String[] ids);
	
	/**
	 * 根据主键，删除特定的DeviceFirmwareFile记录
	 * @param id 主键id
	 * @param operator 操作人对象
	 * @return
	 */
	public ResultInfo<DeviceFirmwareFile> delDeviceFirmwareFile(String id, Operator operator);
	
	/**
	 * 新增一条DeviceFirmwareFile记录，执行成功后传入对象及返回对象的主键属性值不为null
	 * @param deviceFirmwareFile 新增的DeviceFirmwareFile数据（如果无特殊需求，新增对象的主键值请保留为null）
	 * @param operator 操作人对象
	 * @return
	 */
	public ResultInfo<DeviceFirmwareFile> addDeviceFirmwareFile(DeviceFirmwareFile deviceFirmwareFile, Operator operator);
	
	/**
	 * 根据主键，更新一条DeviceFirmwareFile记录
	 * @param deviceFirmwareFile 更新的DeviceFirmwareFile数据，且其主键不能为空
	 * @param operator 操作人对象
	 * @return
	 */
	public ResultInfo<DeviceFirmwareFile> updateDeviceFirmwareFile(DeviceFirmwareFile deviceFirmwareFile, Operator operator);

	/**
	 * 生成主键
	 * @return
	 */
	public String generatePK();
	
		/**
	 * 为DeviceFirmwareFile对象设置默认值
	 * @param obj
	 */
	public void fillDefaultValues(DeviceFirmwareFile obj);
	
	/**
	 * 根据固件类型及车型id，取得对应固件文件的信息
	 * @param firmwareType
	 * @param carModelId
	 * @return
	 */
	public DeviceFirmwareFile getByFirmwareTypeAndCarModelId(int firmwareType, String carModelId);
		
}
