package cn.com.shopec.core.mlparking.service;

import java.util.List;

import cn.com.shopec.common.Operator;
import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.core.car.model.CarSeries;
import cn.com.shopec.core.common.BaseService;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.mlparking.model.CParkLock;
import cn.com.shopec.core.mlparking.vo.ParkingLockVo;

/**
 * 地锁表 服务接口类
 */
public interface CParkLockService extends BaseService {

	/**
	 * 根据查询条件，查询并返回CParkLock的列表
	 * @param q 查询条件
	 * @return
	 */
	public List<CParkLock> getCParkLockList(Query q);
	
	/**
	 * 根据查询条件，分页查询并返回CParkLock的分页结果
	 * @param q 查询条件
	 * @return
	 */
	public PageFinder<CParkLock> getCParkLockPagedList(Query q);
	
	/**
	 * 根据主键，查询并返回一个CParkLock对象
	 * @param id 主键id
	 * @return
	 */
	public CParkLock getCParkLock(String id);

	/**
	 * 根据主键数组，查询并返回一组CParkLock对象
	 * @param ids 主键数组，数组中的主键值应当无重复值，如有重复值时，则返回的列表长度可能小于传入的数组长度
	 * @return
	 */
	public List<CParkLock> getCParkLockByIds(String[] ids);
	
	/**
	 * 根据主键，删除特定的CParkLock记录
	 * @param id 主键id
	 * @param operator 操作人对象
	 * @return
	 */
	public ResultInfo<CParkLock> delCParkLock(String id, Operator operator);
	
	/**
	 * 新增一条CParkLock记录，执行成功后传入对象及返回对象的主键属性值不为null
	 * @param cParkLock 新增的CParkLock数据（如果无特殊需求，新增对象的主键值请保留为null）
	 * @param operator 操作人对象
	 * @return
	 */
	public ResultInfo<CParkLock> addCParkLock(CParkLock cParkLock, Operator operator);
	
	/**
	 * 根据主键，更新一条CParkLock记录
	 * @param cParkLock 更新的CParkLock数据，且其主键不能为空
	 * @param operator 操作人对象
	 * @return
	 */
	public ResultInfo<CParkLock> updateCParkLock(CParkLock cParkLock, Operator operator);

	/**
	 * 生成主键
	 * @return
	 */
	public String generatePK();
	
		/**
	 * 为CParkLock对象设置默认值
	 * @param obj
	 */
	public void fillDefaultValues(CParkLock obj);

	/**
	 * 
	 * @Title: updateCParkLockBegin   
	 * @Description: 进行地锁的修改操作  
	 * @param: @param cParkLock
	 * @param: @param operator
	 * @param: @return      
	 * @return: ResultInfo<CParkLock>
	 * @author: guofei     
	 * @date:   2018年11月7日 下午3:42:21        
	 * @throws
	 */
	public ResultInfo<CParkLock> updateCParkLockBegin(CParkLock cParkLock, Operator operator);
	/**
	 * 
	 * @Title: addCParkLockBegin   
	 * @Description: 添加新的地锁  
	 * @param: @param cParkLock
	 * @param: @param operator
	 * @param: @return      
	 * @return: ResultInfo<CParkLock>
	 * @author: guofei     
	 * @date:   2018年11月7日 下午3:42:26        
	 * @throws
	 */
	public ResultInfo<CParkLock> addCParkLockBegin(CParkLock cParkLock, Operator operator);

	//地锁信息
	public ParkingLockVo getParkLock(String lockNo);	
	public ResultInfo<Object> resultInfoMemberNo(String memberNo,int type);
	public ResultInfo<Object> resultInfoLock(ParkingLockVo cParkLock,boolean flag);
	//预约停车
	public ResultInfo<Object> parkingReservation(Integer pageNo,Integer pageSize,String parkNo);
	//根据停车场和车位号获取地锁信息
	ParkingLockVo getLock(String parkNo,String spaceNo);
}
