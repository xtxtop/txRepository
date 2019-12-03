package cn.com.shopec.core.mlparking.dao;

import java.util.List;

import cn.com.shopec.core.common.BaseDao;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.mlparking.model.CParkLock;
import cn.com.shopec.core.mlparking.vo.ParkingLockVo;

/**
 * 地锁表 数据库处理类
 */
public interface CParkLockDao extends BaseDao<CParkLock,String> {
	/**
	 * 
	 * @Title: pageListCount   
	 * @Description: 地锁表查询数量
	 * @param: @param q
	 * @param: @return      
	 * @return: long
	 * @author: guofei     
	 * @date:   2018年11月7日 下午6:26:45        
	 * @throws
	 */
	long pageListCountCPL(Query q);
	/**
	 * 
	 * @Title: pageListForCPL   
	 * @Description: 查询地锁列表 
	 * @param: @param q
	 * @param: @return      
	 * @return: List<CParkLock>
	 * @author: guofei     
	 * @date:   2018年11月7日 下午6:43:24        
	 * @throws
	 */
	List<CParkLock> pageListForCPL(Query q);
	//地锁信息
	ParkingLockVo getParkLock(String lockNo);
	//根据停车场和车位号获取地锁信息
	ParkingLockVo getLock(String parkNo,String spaceNo);
}
