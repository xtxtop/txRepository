package cn.com.shopec.core.marketing.dao;

import cn.com.shopec.core.common.BaseDao;
import cn.com.shopec.core.marketing.model.CarRedPacket;

/**
 * CarRedPacket 数据库处理类
 */
public interface CarRedPacketDao extends BaseDao<CarRedPacket,String> {
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
	//根据车牌号查询有没有红包已生效或者进行中
	public CarRedPacket getByCarPlateNo(String carPlateNo);
	
	
}
