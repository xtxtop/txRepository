package cn.com.shopec.core.dailyrental.service;

import java.util.List;

import cn.com.shopec.common.Operator;
import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.core.common.BaseService;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.dailyrental.model.DelayOrderDay;

/**
 * DelayOrderDay 服务接口类
 */
public interface DelayOrderDayService extends BaseService {

	/**
	 * 根据查询条件，查询并返回DelayOrderDay的列表
	 * @param q 查询条件
	 * @return
	 */
	public List<DelayOrderDay> getDelayOrderDayList(Query q);
	
	/**
	 * 根据查询条件，分页查询并返回DelayOrderDay的分页结果
	 * @param q 查询条件
	 * @return
	 */
	public PageFinder<DelayOrderDay> getDelayOrderDayPagedList(Query q);
	
	/**
	 * 根据主键，查询并返回一个DelayOrderDay对象
	 * @param id 主键id
	 * @return
	 */
	public DelayOrderDay getDelayOrderDay(String id);

	/**
	 * 根据主键数组，查询并返回一组DelayOrderDay对象
	 * @param ids 主键数组，数组中的主键值应当无重复值，如有重复值时，则返回的列表长度可能小于传入的数组长度
	 * @return
	 */
	public List<DelayOrderDay> getDelayOrderDayByIds(String[] ids);
	
	/**
	 * 根据主键，删除特定的DelayOrderDay记录
	 * @param id 主键id
	 * @param operator 操作人对象
	 * @return
	 */
	public ResultInfo<DelayOrderDay> delDelayOrderDay(String id, Operator operator);
	
	/**
	 * 新增一条DelayOrderDay记录，执行成功后传入对象及返回对象的主键属性值不为null
	 * @param delayOrderDay 新增的DelayOrderDay数据（如果无特殊需求，新增对象的主键值请保留为null）
	 * @param operator 操作人对象
	 * @return
	 */
	public ResultInfo<DelayOrderDay> addDelayOrderDay(DelayOrderDay delayOrderDay, Operator operator);
	
	/**
	 * 根据主键，更新一条DelayOrderDay记录
	 * @param delayOrderDay 更新的DelayOrderDay数据，且其主键不能为空
	 * @param operator 操作人对象
	 * @return
	 */
	public ResultInfo<DelayOrderDay> updateDelayOrderDay(DelayOrderDay delayOrderDay, Operator operator);

	/**
	 * 生成主键
	 * @return
	 */
	public String generatePK();
	
		/**
	 * 为DelayOrderDay对象设置默认值
	 * @param obj
	 */
	public void fillDefaultValues(DelayOrderDay obj);
	/**
	 * 通过订单编号获取最新一条续租信息
	 * @param orderNo
	 * @return
	 */
	public DelayOrderDay getDelayOrderDayByOrderDayNo(String orderNo);
		
}
