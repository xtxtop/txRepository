package cn.com.shopec.core.dailyrental.service;

import java.util.List;

import cn.com.shopec.common.Operator;
import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.core.common.BaseService;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.dailyrental.model.ParkDay;
import cn.com.shopec.core.dailyrental.vo.ParkDayAroundVO;
import cn.com.shopec.core.dailyrental.vo.ParkDayRegionVo;




/**
 * 商家门店 服务接口类
 */
public interface ParkDayService extends BaseService {

	/**
	 * 根据查询条件，查询并返回ParkDay的列表
	 * @param q 查询条件
	 * @return
	 */
	public List<ParkDay> getParkDayList(Query q);
	
	/**
	 * 根据查询条件，分页查询并返回ParkDay的分页结果
	 * @param q 查询条件
	 * @return
	 */
	public PageFinder<ParkDay> getParkDayPagedList(Query q);
	
	/**
	 * 根据查询条件，分页查询并返回ParkDay的分页结果(后台)
	 * @param q 查询条件
	 * @return
	 */
	public PageFinder<ParkDay> getParkDayPagedListForMgt(Query q);
	
	/**
	 * 根据主键，查询并返回一个ParkDay对象
	 * @param id 主键id
	 * @return
	 */
	public ParkDay getParkDay(String id);

	/**
	 * 根据主键数组，查询并返回一组ParkDay对象
	 * @param ids 主键数组，数组中的主键值应当无重复值，如有重复值时，则返回的列表长度可能小于传入的数组长度
	 * @return
	 */
	public List<ParkDay> getParkDayByIds(String[] ids);
	
	/**
	 * 根据主键，删除特定的ParkDay记录
	 * @param id 主键id
	 * @param operator 操作人对象
	 * @return
	 */
	public ResultInfo<ParkDay> delParkDay(String id, Operator operator);
	
	/**
	 * 新增一条ParkDay记录，执行成功后传入对象及返回对象的主键属性值不为null
	 * @param parkDay 新增的ParkDay数据（如果无特殊需求，新增对象的主键值请保留为null）
	 * @param operator 操作人对象
	 * @return
	 */
	public ResultInfo<ParkDay> addParkDay(ParkDay parkDay, Operator operator);
	
	/**
	 * 根据主键，更新一条ParkDay记录
	 * @param parkDay 更新的ParkDay数据，且其主键不能为空
	 * @param operator 操作人对象
	 * @return
	 */
	public ResultInfo<ParkDay> updateParkDay(ParkDay parkDay, Operator operator);

	/**
	 * 生成主键
	 * @return
	 */
	public String generatePK();
	
		/**
	 * 为ParkDay对象设置默认值
	 * @param obj
	 */
	public void fillDefaultValues(ParkDay obj);
	
	/**
	 * 改变启用关闭状态
	 * 
	 * @param park
	 * @param operator
	 * @return
	 */
	public ResultInfo<ParkDay> changeParkDayState(ParkDay parkDay, Operator operator);
	
	//根据城市查出该城市的门店
	public List<ParkDayAroundVO> getParkDayListByAround(String longitude, String latitude,String cityId);
	//根据省市 查出 区域的门店
	public ResultInfo<List<ParkDayRegionVo>> getParkDayListByCityTake(Query q, String longitude, String latitude,
			Integer tag);
	//根据坐标算距离
	public Double getDistanceByPointDay(String longitude, String latitude, String longitude2, String latitude2);
	//根据输入的门店名称搜索 门店
	public List<ParkDay> getSearchParkDayList(String longitude, String latitude, String parkName);
	
	/**
	 * 根据加盟商id和经纬度，查找距经纬度所有门店
	 * （距传入经纬度距离最近的门店去第一个）
	 * @param longitude
	 * @param latitude
	 * @param merchantIds
	 * @return
	 */
	public List<ParkDay> searchParkDayListByMerchantIds(Double longitude, Double latitude, List<String> merchantIds,String cityId);
		
}
