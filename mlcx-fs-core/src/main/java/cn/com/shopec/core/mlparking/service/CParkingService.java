package cn.com.shopec.core.mlparking.service;

import java.util.List;

import cn.com.shopec.common.Operator;
import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.core.common.BaseService;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.mlparking.model.CParking;
import cn.com.shopec.core.mlparking.vo.CParkingDetailVo;
import cn.com.shopec.core.mlparking.vo.CParkingVo;
import cn.com.shopec.core.mlparking.vo.ParkingVo;

/**
 * 停车场 服务接口类
 */
public interface CParkingService extends BaseService {

	/**
	 * 根据查询条件，查询并返回CParking的列表
	 * 
	 * @param q
	 *            查询条件
	 * @return
	 */
	public List<CParking> getCParkingList(Query q);

	/**
	 * 根据查询条件，分页查询并返回CParking的分页结果
	 * 
	 * @param q
	 *            查询条件
	 * @return
	 */
	public PageFinder<CParking> getCParkingPagedList(Query q);

	/**
	 * 根据主键，查询并返回一个CParking对象
	 * 
	 * @param id
	 *            主键id
	 * @return
	 */
	public CParking getCParking(String id);

	/**
	 * 根据主键数组，查询并返回一组CParking对象
	 * 
	 * @param ids
	 *            主键数组，数组中的主键值应当无重复值，如有重复值时，则返回的列表长度可能小于传入的数组长度
	 * @return
	 */
	public List<CParking> getCParkingByIds(String[] ids);

	/**
	 * 根据主键，删除特定的CParking记录
	 * 
	 * @param id
	 *            主键id
	 * @param operator
	 *            操作人对象
	 * @return
	 */
	public ResultInfo<CParking> delCParking(String id, Operator operator);

	/**
	 * 新增一条CParking记录，执行成功后传入对象及返回对象的主键属性值不为null
	 * 
	 * @param cParking
	 *            新增的CParking数据（如果无特殊需求，新增对象的主键值请保留为null）
	 * @param operator
	 *            操作人对象
	 * @return
	 */
	public ResultInfo<CParking> addCParking(CParking cParking, Operator operator, String parkingUrl,
			String[] spaceNumber,String  ground_spaceNumber_add,String [] under_spaceNumber_add);

	/**
	 * 根据主键，更新一条CParking记录
	 * 
	 * @param cParking
	 *            更新的CParking数据，且其主键不能为空
	 * @param operator
	 *            操作人对象
	 * @return
	 */
	public ResultInfo<CParking> updateCParking(CParking cParking, Operator operator, String parkingUrl,
			String[] spaceNumber,String  ground_spaceNumber_add,String [] under_spaceNumber_add);

	/**
	 * 生成主键
	 * 
	 * @return
	 */
	public String generatePK();

	/**
	 * 为CParking对象设置默认值
	 * 
	 * @param obj
	 */
	public void fillDefaultValues(CParking obj);

	// 停车场列表vo
	PageFinder<ParkingVo> getParkList(Query q);

	// 停车场详情
	ParkingVo getParkListNo(String id);

	/**
	 * app端的停车场列表
	 * 
	 * @param q
	 * @return
	 */
	public PageFinder<CParkingVo> pageCparkingList(Query q);

	/**
	 * app端停车场详情
	 * 
	 * @param q
	 * @return
	 */
	public CParkingDetailVo getParkingDetailVo(Query q);

	/**
	 * 停车场首页模糊搜索
	 * 
	 * @param q
	 * @return
	 */
	public PageFinder<CParkingVo> searchParkVoList(Query q);

}
