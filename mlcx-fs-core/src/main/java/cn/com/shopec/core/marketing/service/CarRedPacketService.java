package cn.com.shopec.core.marketing.service;

import java.util.List;

import cn.com.shopec.common.Operator;
import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.core.common.BaseService;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.marketing.model.CarRedPacket;

/**
 * CarRedPacket 服务接口类
 */
public interface CarRedPacketService extends BaseService {

	/**
	 * 根据查询条件，查询并返回CarRedPacket的列表
	 * @param q 查询条件
	 * @return
	 */
	public List<CarRedPacket> getCarRedPacketList(Query q);
	
	/**
	 * 根据查询条件，分页查询并返回CarRedPacket的分页结果
	 * @param q 查询条件
	 * @return
	 */
	public PageFinder<CarRedPacket> getCarRedPacketPagedList(Query q);
	
	/**
	 * 根据主键，查询并返回一个CarRedPacket对象
	 * @param id 主键id
	 * @return
	 */
	public CarRedPacket getCarRedPacket(String id);
	/**
	 * 根据红包车状态获取红包车记录
	 * @param carPlateNo
	 * @return
	 */
	public CarRedPacket getCarRedPacketByCarPlateNo(String carPlateNo,String carRedPacketStatus);
	/**
	 * 获取红包车所在的场站编号
	 * @return
	 */
	public String getCarRedParketLocationParkNos(Integer carRedPacketStatus);
	/**
	 * 根据主键数组，查询并返回一组CarRedPacket对象
	 * @param ids 主键数组，数组中的主键值应当无重复值，如有重复值时，则返回的列表长度可能小于传入的数组长度
	 * @return
	 */
	public List<CarRedPacket> getCarRedPacketByIds(String[] ids);
	
	/**
	 * 根据主键，删除特定的CarRedPacket记录
	 * @param id 主键id
	 * @param operator 操作人对象
	 * @return
	 */
	public ResultInfo<CarRedPacket> delCarRedPacket(String id, Operator operator);
	
	/**
	 * 新增一条CarRedPacket记录，执行成功后传入对象及返回对象的主键属性值不为null
	 * @param carRedPacket 新增的CarRedPacket数据（如果无特殊需求，新增对象的主键值请保留为null）
	 * @param operator 操作人对象
	 * @return
	 */
	public ResultInfo<CarRedPacket> addCarRedPacket(CarRedPacket carRedPacket, Operator operator);
	
	/**
	 * 根据主键，更新一条CarRedPacket记录
	 * @param carRedPacket 更新的CarRedPacket数据，且其主键不能为空
	 * @param operator 操作人对象
	 * @return
	 */
	public ResultInfo<CarRedPacket> updateCarRedPacket(CarRedPacket carRedPacket, Operator operator);

	/**
	 * 生成主键
	 * @return
	 */
	public String generatePK();
	
		/**
	 * 为CarRedPacket对象设置默认值
	 * @param obj
	 */
	public void fillDefaultValues(CarRedPacket obj);
	//根据车牌号查询有没有红包已生效或者进行中
	public CarRedPacket getByCarPlateNo(String carPlateNo);

	
		
}
