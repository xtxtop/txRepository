package cn.com.shopec.core.ml.service;

import java.util.List;
import cn.com.shopec.common.Operator;
import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.core.common.BaseService;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.ml.model.ChargingPile;
import cn.com.shopec.core.ml.vo.PileVo;
import cn.com.shopec.core.ml.vo.TerminalDetailsVo;

/**
 * 充电桩 服务接口类
 */
public interface ChargingPileService extends BaseService {
	/**
	 * 根据查询条件，查询并返回ChargingPile的列表
	 * 
	 * @param q
	 *            查询条件
	 * @return
	 */
	public List<ChargingPile> getChargingPileList(Query q);

	/**
	 * 根据查询条件，分页查询并返回ChargingPile的分页结果
	 * 
	 * @param q
	 *            查询条件
	 * @return
	 */
	public PageFinder<ChargingPile> getChargingPilePagedList(Query q);

	/**
	 * 根据主键，查询并返回一个ChargingPile对象
	 * 
	 * @param id
	 *            主键id
	 * @return
	 */
	public ChargingPile getChargingPile(String id);

	/**
	 * 根据主键数组，查询并返回一组ChargingPile对象
	 * 
	 * @param ids
	 *            主键数组，数组中的主键值应当无重复值，如有重复值时，则返回的列表长度可能小于传入的数组长度
	 * @return
	 */
	public List<ChargingPile> getChargingPileByIds(String[] ids);

	/**
	 * 根据主键，删除特定的ChargingPile记录
	 * 
	 * @param id
	 *            主键id
	 * @param operator
	 *            操作人对象
	 * @return
	 */
	public ResultInfo<ChargingPile> delChargingPile(String id, Operator operator);

	/**
	 * 新增一条ChargingPile记录，执行成功后传入对象及返回对象的主键属性值不为null
	 * 
	 * @param chargingPile
	 *            新增的ChargingPile数据（如果无特殊需求，新增对象的主键值请保留为null）
	 * @param operator
	 *            操作人对象
	 * @return
	 */
	public ResultInfo<ChargingPile> addChargingPile(ChargingPile chargingPile, Operator operator);

	/**
	 * 根据主键，更新一条ChargingPile记录
	 * 
	 * @param chargingPile
	 *            更新的ChargingPile数据，且其主键不能为空
	 * @param operator
	 *            操作人对象
	 * @return
	 */
	public ResultInfo<ChargingPile> updateChargingPile(ChargingPile chargingPile, Operator operator);

	/**
	 * 生成主键
	 * 
	 * @return
	 */
	public String generatePK();

	/**
	 * 为ChargingPile对象设置默认值
	 * 
	 * @param obj
	 */
	public void fillDefaultValues(ChargingPile obj);

	/** 获取终端列表 */
	List<PileVo> pagePileQuery(Query q);

	/** 获取终端详情 */
	TerminalDetailsVo getTerminalDetails(String chargingPileNo, String parkingLockNo);
}
