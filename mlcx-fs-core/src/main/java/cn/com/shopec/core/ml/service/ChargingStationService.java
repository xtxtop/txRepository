package cn.com.shopec.core.ml.service;

import java.util.List;

import cn.com.shopec.common.Operator;
import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.core.common.BaseService;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.ml.model.ChargingStation;
import cn.com.shopec.core.ml.vo.ChargingStationDetailsVo;
import cn.com.shopec.core.ml.vo.ChargingStationVo;

/**
 * 充电站 服务接口类
 */
public interface ChargingStationService extends BaseService {
	/**
	 * 根据查询条件，查询并返回ChargingStation的列表
	 * 
	 * @param q
	 *            查询条件
	 * @return
	 */
	public List<ChargingStation> getChargingStationList(Query q);

	/**
	 * 根据查询条件，分页查询并返回ChargingStation的分页结果
	 * 
	 * @param q
	 *            查询条件
	 * @return
	 */
	public PageFinder<ChargingStation> getChargingStationPagedList(Query q);

	/**
	 * 根据主键，查询并返回一个ChargingStation对象
	 * 
	 * @param id
	 *            主键id
	 * @return
	 */
	public ChargingStation getChargingStation(String id);

	/**
	 * 根据主键数组，查询并返回一组ChargingStation对象
	 * 
	 * @param ids
	 *            主键数组，数组中的主键值应当无重复值，如有重复值时，则返回的列表长度可能小于传入的数组长度
	 * @return
	 */
	public List<ChargingStation> getChargingStationByIds(String[] ids);

	/**
	 * 根据主键，删除特定的ChargingStation记录
	 * 
	 * @param id
	 *            主键id
	 * @param operator
	 *            操作人对象
	 * @return
	 */
	public ResultInfo<ChargingStation> delChargingStation(String id, Operator operator);

	/**
	 * 新增一条ChargingStation记录，执行成功后传入对象及返回对象的主键属性值不为null
	 * 
	 * @param chargingStation
	 *            新增的ChargingStation数据（如果无特殊需求，新增对象的主键值请保留为null）
	 * @param operator
	 *            操作人对象
	 * @return
	 */
	public ResultInfo<ChargingStation> addChargingStation(ChargingStation chargingStation, Operator operator);

	/**
	 * 根据主键，更新一条ChargingStation记录
	 * 
	 * @param chargingStation
	 *            更新的ChargingStation数据，且其主键不能为空
	 * @param operator
	 *            操作人对象
	 * @return
	 */
	public ResultInfo<ChargingStation> updateChargingStation(ChargingStation chargingStation, Operator operator);

	/**
	 * 生成主键
	 * 
	 * @return
	 */
	public String generatePK();

	/**
	 * 为ChargingStation对象设置默认值
	 * 
	 * @param obj
	 */
	public void fillDefaultValues(ChargingStation obj);

	// 获取充电站 VO数据
	public PageFinder<ChargingStationVo> getChargingStationVoList(Query q);

	// 获取充电站详情
	public ChargingStationVo getChargingStationVoList_NO(String id);

	/**
	 * 获取充电站列表数据
	 * 
	 * @param longitude
	 *            经度
	 * @param latitude
	 *            纬度
	 * @param electricPrice
	 *            价格
	 * @param typeSort
	 *            类型（2、超级；1、精品）
	 * @param labelList
	 *            标签集合
	 * @return
	 */
	public List<ChargingStationVo> pageChargingStationVoList(String longitude, String latitude, String cityId,
			String[] labelList, String pageNo, String pageSize);

	/**
	 * 根据主键，查询ChargingStation详情
	 * 
	 * @return
	 */
	public ChargingStationDetailsVo getChargingStationDatils(ChargingStationDetailsVo search);

	public List<ChargingStation> getTypeSort();

	public List<ChargingStationVo> getChargingStationVoListByCollection(Query q);

	/**
	 * 模糊搜索充电站
	 * 
	 * @param q
	 * @return
	 */
	public PageFinder<ChargingStationVo> searchStationVoList(Query q);
}
