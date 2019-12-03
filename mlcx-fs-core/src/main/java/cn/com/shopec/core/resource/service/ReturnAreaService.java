package cn.com.shopec.core.resource.service;

import java.util.List;

import cn.com.shopec.common.Operator;
import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.core.common.BaseService;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.resource.model.ReturnArea;


/**
 * ReturnArea 服务接口类
 */
public interface ReturnAreaService extends BaseService {

	/**
	 * 根据查询条件，查询并返回ReturnArea的列表
	 * @param q 查询条件
	 * @return
	 */
	public List<ReturnArea> getReturnAreaList(Query q);
	
	/**
	 * 根据查询条件，分页查询并返回ReturnArea的分页结果
	 * @param q 查询条件
	 * @return
	 */
	public PageFinder<ReturnArea> getReturnAreaPagedList(Query q);
	
	/**
	 * 根据主键，查询并返回一个ReturnArea对象
	 * @param id 主键id
	 * @return
	 */
	public ReturnArea getReturnArea(String id);

	/**
	 * 根据主键数组，查询并返回一组ReturnArea对象
	 * @param ids 主键数组，数组中的主键值应当无重复值，如有重复值时，则返回的列表长度可能小于传入的数组长度
	 * @return
	 */
	public List<ReturnArea> getReturnAreaByIds(String[] ids);
	
	/**
	 * 根据主键，删除特定的ReturnArea记录
	 * @param id 主键id
	 * @param operator 操作人对象
	 * @return
	 */
	public ResultInfo<ReturnArea> delReturnArea(String id, Operator operator);
	
	/**
	 * 新增一条ReturnArea记录，执行成功后传入对象及返回对象的主键属性值不为null
	 * @param returnArea 新增的ReturnArea数据（如果无特殊需求，新增对象的主键值请保留为null）
	 * @param operator 操作人对象
	 * @return
	 */
	public ResultInfo<ReturnArea> addReturnArea(ReturnArea returnArea, Operator operator);
	
	/**
	 * 根据主键，更新一条ReturnArea记录
	 * @param returnArea 更新的ReturnArea数据，且其主键不能为空
	 * @param operator 操作人对象
	 * @return
	 */
	public ResultInfo<ReturnArea> updateReturnArea(ReturnArea returnArea, Operator operator);

	/**
	 * 生成主键
	 * @return
	 */
	public String generatePK();
	
		/**
	 * 为ReturnArea对象设置默认值
	 * @param obj
	 */
	public void fillDefaultValues(ReturnArea obj);
		
}
