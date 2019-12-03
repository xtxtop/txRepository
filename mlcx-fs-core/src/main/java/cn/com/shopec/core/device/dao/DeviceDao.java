package cn.com.shopec.core.device.dao;

import java.util.List;

import cn.com.shopec.core.common.BaseDao;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.device.model.Device;

/**
 * Device 数据库处理类
 */
public interface DeviceDao extends BaseDao<Device,String> {

	/**
	 * 根据设备序列号查询到设备
	 * @param deviceSn
	 * @return
	 */
	public Device getByDeviceSn(String deviceSn);
	/**
	 * 修改对象
	 * @param obj
	 */
	public int updateDeviceCar(Device device);
	/**
	 * 根据车辆编号查询到设备
	 * @param deviceSn
	 * @return
	 */
	public Device getDeviceByCarNo(String carNo);
	//导入时 查询
	public Device getDeviceMac(String macAddr);
	public Device getDeviceSim(String simCardNo);
	public Device getDeviceCarPlateNo(String carPlateNo);
	public List<Device> pageListDate(Query q);
	public List<Device> pageListDeviceByCar(Query q);
	public long countByCar(Query q);
}
