package cn.com.shopec.core.mlparking.service;

import java.util.List;

import cn.com.shopec.common.Operator;
import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.core.common.BaseService;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.mlparking.model.CPliesNumber;

/**
 * 楼层表 服务接口类
 */
public interface CPliesNumberService extends BaseService {

	/**
	 * 根据查询条件，查询并返回CPliesNumber的列表
	 * @param q 查询条件
	 * @return
	 */
	public List<CPliesNumber> getCPliesNumberList(Query q);
	
	/**
	 * 根据查询条件，分页查询并返回CPliesNumber的分页结果
	 * @param q 查询条件
	 * @return
	 */
	public PageFinder<CPliesNumber> getCPliesNumberPagedList(Query q);
	
	/**
	 * 根据主键，查询并返回一个CPliesNumber对象
	 * @param id 主键id
	 * @return
	 */
	public CPliesNumber getCPliesNumber(String id);

	/**
	 * 根据主键数组，查询并返回一组CPliesNumber对象
	 * @param ids 主键数组，数组中的主键值应当无重复值，如有重复值时，则返回的列表长度可能小于传入的数组长度
	 * @return
	 */
	public List<CPliesNumber> getCPliesNumberByIds(String[] ids);
	
	/**
	 * 根据主键，删除特定的CPliesNumber记录
	 * @param id 主键id
	 * @param operator 操作人对象
	 * @return
	 */
	public ResultInfo<CPliesNumber> delCPliesNumber(String id, Operator operator);
	
	/**
	 * 新增一条CPliesNumber记录，执行成功后传入对象及返回对象的主键属性值不为null
	 * @param cPliesNumber 新增的CPliesNumber数据（如果无特殊需求，新增对象的主键值请保留为null）
	 * @param operator 操作人对象
	 * @return
	 */
	public ResultInfo<CPliesNumber> addCPliesNumber(CPliesNumber cPliesNumber, Operator operator);
	
	/**
	 * 根据主键，更新一条CPliesNumber记录
	 * @param cPliesNumber 更新的CPliesNumber数据，且其主键不能为空
	 * @param operator 操作人对象
	 * @return
	 */
	public ResultInfo<CPliesNumber> updateCPliesNumber(CPliesNumber cPliesNumber, Operator operator);

	/**
	 * 生成主键
	 * @return
	 */
	public String generatePK();
	
		/**
	 * 为CPliesNumber对象设置默认值
	 * @param obj
	 */
	public void fillDefaultValues(CPliesNumber obj);
		
}
