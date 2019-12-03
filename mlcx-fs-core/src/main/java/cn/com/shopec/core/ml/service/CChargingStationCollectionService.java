package cn.com.shopec.core.ml.service;

import cn.com.shopec.common.Operator;
import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.core.common.BaseService;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.ml.model.CChargingStationCollection;
import cn.com.shopec.core.ml.vo.ChargingCollectionVo;

import java.util.List;

public interface CChargingStationCollectionService extends BaseService {
	/**
	 * 根据查询条件，查询并返回CChargingStationCollection的列表
	 *
	 * @param q 查询条件
	 * @return
	 */
	public List<CChargingStationCollection> getCChargingStationCollectionList(Query q);

	/**
	 * 根据查询条件，分页查询并返回CChargingStationCollection的分页结果
	 *
	 * @param q 查询条件
	 * @return
	 */
	public PageFinder<CChargingStationCollection> getCChargingStationCollectionPagedList(Query q);

	/**
	 * 根据主键，查询并返回一个CChargingStationCollection对象
	 *
	 * @param id 主键id
	 * @return
	 */
	public CChargingStationCollection getCChargingStationCollection(String id);

	/**
	 * 根据主键数组，查询并返回一组CChargingStationCollection对象
	 *
	 * @param ids 主键数组，数组中的主键值应当无重复值，如有重复值时，则返回的列表长度可能小于传入的数组长度
	 * @return
	 */
	public List<CChargingStationCollection> getCChargingStationCollectionByIds(String[] ids);

	/**
	 * 根据主键，删除特定的CChargingStationCollection记录
	 *
	 * @param id       主键id
	 * @param operator 操作人对象
	 * @return
	 */
	public ResultInfo<CChargingStationCollection> delCChargingStationCollection(String id, Operator operator);

	/**
	 * 新增一条CChargingStationCollection记录，执行成功后传入对象及返回对象的主键属性值不为null
	 *
	 * @param cChargingStationCollection 新增的CChargingStationCollection数据（如果无特殊需求，新增对象的主键值请保留为null）
	 * @param operator                   操作人对象
	 * @return
	 */
	public ResultInfo<CChargingStationCollection> addCChargingStationCollection(
			CChargingStationCollection cChargingStationCollection, Operator operator);

	/**
	 * 根据主键，更新一条CChargingStationCollection记录
	 *
	 * @param cChargingStationCollection 更新的CChargingStationCollection数据，且其主键不能为空
	 * @param operator                   操作人对象
	 * @return
	 */
	public ResultInfo<CChargingStationCollection> updateCChargingStationCollection(
			CChargingStationCollection cChargingStationCollection, Operator operator);

	/**
	 * 生成主键
	 *
	 * @return
	 */
	public String generatePK();

	/**
	 * 为CChargingStationCollection对象设置默认值
	 *
	 * @param obj
	 */
	public void fillDefaultValues(CChargingStationCollection obj);

	PageFinder<ChargingCollectionVo> getCollectionInfo(Query q);

	List<ChargingCollectionVo> getCollectionList(Query q);

	ResultInfo addStationCollection(ResultInfo resultInfo, CChargingStationCollection cChargingStationCollection,
			Operator operator);

	ResultInfo toggleCollectionStatus(String memberNo, String stationNo);
}
