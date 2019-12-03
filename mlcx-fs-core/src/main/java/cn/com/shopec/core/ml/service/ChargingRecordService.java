package cn.com.shopec.core.ml.service;

import java.util.List;

import cn.com.shopec.common.Operator;
import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.core.common.BaseService;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.ml.model.ChargingRecord;
import cn.com.shopec.core.ml.vo.ChargingRecordVo;

/**
 * 充电记录 服务接口类
 */
public interface ChargingRecordService extends BaseService {

	/**
	 * 根据查询条件，查询并返回ChargingRecord的列表
	 * @param q 查询条件
	 * @return
	 */
	public List<ChargingRecord> getChargingRecordList(Query q);
	
	/**
	 * 根据查询条件，分页查询并返回ChargingRecord的分页结果
	 * @param q 查询条件
	 * @return
	 */
	public PageFinder<ChargingRecord> getChargingRecordPagedList(Query q);
	
	/**
	 * 根据主键，查询并返回一个ChargingRecord对象
	 * @param id 主键id
	 * @return
	 */
	public ChargingRecord getChargingRecord(String id);

	/**
	 * 根据主键数组，查询并返回一组ChargingRecord对象
	 * @param ids 主键数组，数组中的主键值应当无重复值，如有重复值时，则返回的列表长度可能小于传入的数组长度
	 * @return
	 */
	public List<ChargingRecord> getChargingRecordByIds(String[] ids);
	
	/**
	 * 根据主键，删除特定的ChargingRecord记录
	 * @param id 主键id
	 * @param operator 操作人对象
	 * @return
	 */
	public ResultInfo<ChargingRecord> delChargingRecord(String id, Operator operator);
	
	/**
	 * 新增一条ChargingRecord记录，执行成功后传入对象及返回对象的主键属性值不为null
	 * @param chargingRecord 新增的ChargingRecord数据（如果无特殊需求，新增对象的主键值请保留为null）
	 * @param operator 操作人对象
	 * @return
	 */
	public ResultInfo<ChargingRecord> addChargingRecord(ChargingRecord chargingRecord, Operator operator);
	
	/**
	 * 根据主键，更新一条ChargingRecord记录
	 * @param chargingRecord 更新的ChargingRecord数据，且其主键不能为空
	 * @param operator 操作人对象
	 * @return
	 */
	public ResultInfo<ChargingRecord> updateChargingRecord(ChargingRecord chargingRecord, Operator operator);

	/**
	 * 生成主键
	 * @return
	 */
	public String generatePK();
	
		/**
	 * 为ChargingRecord对象设置默认值
	 * @param obj
	 */
	public void fillDefaultValues(ChargingRecord obj);

	PageFinder<ChargingRecordVo> getChargingRecordVoPagedList(Query q);

}
