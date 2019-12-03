package cn.com.shopec.core.device.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import cn.com.shopec.common.Operator;
import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.core.common.BaseService;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.device.model.Device;

/**
 * Device 服务接口类
 */
public interface DeviceService extends BaseService {

	/**
	 * 根据查询条件，查询并返回Device的列表
	 * 
	 * @param q
	 *            查询条件
	 * @return
	 */
	public List<Device> getDeviceList(Query q);

	/**
	 * 根据查询条件，分页查询并返回Device的分页结果
	 * 
	 * @param q
	 *            查询条件
	 * @return
	 */
	public PageFinder<Device> getDevicePagedList(Query q,Integer roleAdminTag);

	/**
	 * 根据主键，查询并返回一个Device对象
	 * 
	 * @param id
	 *            主键id
	 * @return
	 */
	public Device getDevice(String id);

	/**
	 * 根据主键数组，查询并返回一组Device对象
	 * 
	 * @param ids
	 *            主键数组，数组中的主键值应当无重复值，如有重复值时，则返回的列表长度可能小于传入的数组长度
	 * @return
	 */
	public List<Device> getDeviceByIds(String[] ids);

	/**
	 * 根据主键，删除特定的Device记录
	 * 
	 * @param id
	 *            主键id
	 * @param operator
	 *            操作人对象
	 * @return
	 */
	public ResultInfo<Device> delDevice(String id, Operator operator);

	/**
	 * 新增一条Device记录，执行成功后传入对象及返回对象的主键属性值不为null
	 * 
	 * @param device
	 *            新增的Device数据（如果无特殊需求，新增对象的主键值请保留为null）
	 * @param operator
	 *            操作人对象
	 * @return
	 */
	public ResultInfo<Device> addDevice(Device device, Operator operator);

	/**
	 * 根据主键，更新一条Device记录
	 * 
	 * @param device
	 *            更新的Device数据，且其主键不能为空
	 * @param operator
	 *            操作人对象
	 * @return
	 */
	public ResultInfo<Device> updateDevice(Device device, Operator operator);

	/**
	 * 生成主键
	 * 
	 * @return
	 */
	public String generatePK();

	/**
	 * 为Device对象设置默认值
	 * 
	 * @param obj
	 */
	public void fillDefaultValues(Device obj);

	ResultInfo<Device> importDeviceInfo(MultipartFile multipartFile, Operator operator) throws Exception;

	String exportDeviceInfo() throws Exception;

	/**
	 * 根据设备序列号，查询并返回一个Device对象
	 * 
	 * @param deviceSn
	 *            设备序列号
	 * @return
	 */
	public Device getDeviceByDeviceSn(String deviceSn);
	/**
	 * 根据车辆编号查询到设备
	 * @param deviceSn
	 * @return
	 */
	public Device getDeviceByCarNo(String carNo);
	/**
	 * 根据车牌号查询到设备
	 * @param 
	 * @return
	 */
	public Device getDeviceCarPlateNo(String carPlateNo);

	public PageFinder<Device> pageListDeviceByCar(Query q, Integer roleAdminTag);

	public int updateDeviceCar(Device d);
}
