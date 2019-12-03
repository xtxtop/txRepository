package cn.com.shopec.core.car.service;

import java.util.List;

import cn.com.shopec.common.Operator;
import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.core.car.model.CarRecord;
import cn.com.shopec.core.car.vo.CarRecordWkVo;
import cn.com.shopec.core.common.BaseService;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;

/**
 * 用车记录表 服务接口类
 */
public interface CarRecordService extends BaseService {

	/**
	 * 根据查询条件，查询并返回CarRecord的列表
	 * 
	 * @param q
	 *            查询条件
	 * @return
	 */
	public List<CarRecord> getCarRecordList(Query q);

	/**
	 * 根据查询条件，分页查询并返回CarRecord的分页结果
	 * 
	 * @param q
	 *            查询条件
	 * @return
	 */
	public PageFinder<CarRecord> getCarRecordPagedList(Query q);

	/**
	 * 根据主键，查询并返回一个CarRecord对象
	 * 
	 * @param id
	 *            主键id
	 * @return
	 */
	public CarRecord getCarRecord(String id);

	/**
	 * 根据主键数组，查询并返回一组CarRecord对象
	 * 
	 * @param ids
	 *            主键数组，数组中的主键值应当无重复值，如有重复值时，则返回的列表长度可能小于传入的数组长度
	 * @return
	 */
	public List<CarRecord> getCarRecordByIds(String[] ids);

	/**
	 * 根据主键，删除特定的CarRecord记录
	 * 
	 * @param id
	 *            主键id
	 * @param operator
	 *            操作人对象
	 * @return
	 */
	public ResultInfo<CarRecord> delCarRecord(String id, Operator operator);

	/**
	 * 新增一条CarRecord记录，执行成功后传入对象及返回对象的主键属性值不为null
	 * 
	 * @param carRecord
	 *            新增的CarRecord数据（如果无特殊需求，新增对象的主键值请保留为null）
	 * @param operator
	 *            操作人对象
	 * @return
	 */
	public ResultInfo<CarRecord> addCarRecord(CarRecord carRecord, Operator operator);

	/**
	 * 根据主键，更新一条CarRecord记录
	 * 
	 * @param carRecord
	 *            更新的CarRecord数据，且其主键不能为空
	 * @param operator
	 *            操作人对象
	 * @return
	 */
	public ResultInfo<CarRecord> updateCarRecord(CarRecord carRecord, Operator operator);

	/**
	 * 生成主键
	 * 
	 * @return
	 */
	public String generatePK();

	/**
	 * 为CarRecord对象设置默认值
	 * 
	 * @param obj
	 */
	public void fillDefaultValues(CarRecord obj);

	/**
	 * 根据订单编号查找用车记录 zln
	 */
	public CarRecord getCarRecordByDocumentNo(String orderNo, Integer i);

	/**
	 * 调度端查看用车最近一次的记录
	 */
	public CarRecordWkVo carUsageLog(String carPlateNo);

}
