package cn.com.shopec.core.resource.service;

import java.util.List;

import cn.com.shopec.common.Operator;
import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.core.common.BaseService;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.resource.model.ParkingSpace;

/**
 * 车位表 服务接口类
 */
public interface ParkingSpaceService extends BaseService {

	/**
	 * 根据查询条件，查询并返回ParkingSpace的列表
	 * @param q 查询条件
	 * @return
	 */
	public List<ParkingSpace> getParkingSpaceList(Query q);
	
	/**
	 * 根据查询条件，分页查询并返回ParkingSpace的分页结果
	 * @param q 查询条件
	 * @return
	 */
	public PageFinder<ParkingSpace> getParkingSpacePagedList(Query q);
	/**
	 * @author lj
	 * 后台
	 * 根据查询条件，分页查询并返回ParkingSpace的分页结果
	 * @param q 查询条件
	 * @return
	 */
	public PageFinder<ParkingSpace> getParkingSpacePageList(Query q);
	
	/**
	 * 根据主键，查询并返回一个ParkingSpace对象
	 * @param id 主键id
	 * @return
	 */
	public ParkingSpace getParkingSpace(String id);

	/**
	 * 根据主键数组，查询并返回一组ParkingSpace对象
	 * @param ids 主键数组，数组中的主键值应当无重复值，如有重复值时，则返回的列表长度可能小于传入的数组长度
	 * @return
	 */
	public List<ParkingSpace> getParkingSpaceByIds(String[] ids);
	
	/**
	 * 根据主键，删除特定的ParkingSpace记录
	 * @param id 主键id
	 * @param operator 操作人对象
	 * @return
	 */
	public ResultInfo<ParkingSpace> delParkingSpace(String id, Operator operator);
	
	/**
	 * 新增一条ParkingSpace记录，执行成功后传入对象及返回对象的主键属性值不为null
	 * @param parkingSpace 新增的ParkingSpace数据（如果无特殊需求，新增对象的主键值请保留为null）
	 * @param operator 操作人对象
	 * @return
	 */
	public ResultInfo<ParkingSpace> addParkingSpace(ParkingSpace parkingSpace, Operator operator);
	
	/**
	 * 根据主键，更新一条ParkingSpace记录
	 * @param parkingSpace 更新的ParkingSpace数据，且其主键不能为空
	 * @param operator 操作人对象
	 * @return
	 */
	public ResultInfo<ParkingSpace> updateParkingSpace(ParkingSpace parkingSpace, Operator operator);
	/**
	 * @author lj
	 * 后台更新
	 * 根据主键，更新一条ParkingSpace记录
	 * @param parkingSpace 更新的ParkingSpace数据，且其主键不能为空
	 * @param operator 操作人对象
	 * @return
	 */
	public ResultInfo<ParkingSpace> updateParkingSpaces(ParkingSpace parkingSpace, Operator operator);

	/**
	 * 生成主键
	 * @return
	 */
	public String generatePK();
	
		/**
	 * 为ParkingSpace对象设置默认值
	 * @param obj
	 */
	public void fillDefaultValues(ParkingSpace obj);

	/**
	 * 校验场站是否拥有空余车位可以新增
	 * @param parkingSpace
	 * @return
	 */
	public	ResultInfo<ParkingSpace> checkParkingSpaceNuber(ParkingSpace parkingSpace);
		
}
