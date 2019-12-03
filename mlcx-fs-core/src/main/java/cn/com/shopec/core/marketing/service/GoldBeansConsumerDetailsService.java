package cn.com.shopec.core.marketing.service;

import java.util.List;

import cn.com.shopec.common.Operator;
import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.core.common.BaseService;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.marketing.model.GoldBeansConsumerDetails;

/**
 * GoldBeansConsumerDetails 服务接口类
 */
public interface GoldBeansConsumerDetailsService extends BaseService {

	/**
	 * 根据查询条件，查询并返回GoldBeansConsumerDetails的列表
	 * @param q 查询条件
	 * @return
	 */
	public List<GoldBeansConsumerDetails> getGoldBeansConsumerDetailsList(Query q);
	
	/**
	 * 根据查询条件，分页查询并返回GoldBeansConsumerDetails的分页结果
	 * @param q 查询条件
	 * @return
	 */
	public PageFinder<GoldBeansConsumerDetails> getGoldBeansConsumerDetailsPagedList(Query q);
	
	/**
	 * 根据主键，查询并返回一个GoldBeansConsumerDetails对象
	 * @param id 主键id
	 * @return
	 */
	public GoldBeansConsumerDetails getGoldBeansConsumerDetails(String id);

	/**
	 * 根据主键数组，查询并返回一组GoldBeansConsumerDetails对象
	 * @param ids 主键数组，数组中的主键值应当无重复值，如有重复值时，则返回的列表长度可能小于传入的数组长度
	 * @return
	 */
	public List<GoldBeansConsumerDetails> getGoldBeansConsumerDetailsByIds(String[] ids);
	
	/**
	 * 根据主键，删除特定的GoldBeansConsumerDetails记录
	 * @param id 主键id
	 * @return
	 */
	public ResultInfo<GoldBeansConsumerDetails> delGoldBeansConsumerDetails(String id);
	
	/**
	 * 新增一条GoldBeansConsumerDetails记录，执行成功后传入对象及返回对象的主键属性值不为null
	 * @param goldBeansConsumerDetails 新增的GoldBeansConsumerDetails数据（如果无特殊需求，新增对象的主键值请保留为null）
	 * @param operator 操作人对象
	 * @return
	 */
	public ResultInfo<GoldBeansConsumerDetails> addGoldBeansConsumerDetails(GoldBeansConsumerDetails goldBeansConsumerDetails);
	
	/**
	 * 根据主键，更新一条GoldBeansConsumerDetails记录
	 * @param goldBeansConsumerDetails 更新的GoldBeansConsumerDetails数据，且其主键不能为空
	 * @param operator 操作人对象
	 * @return
	 */
	public ResultInfo<GoldBeansConsumerDetails> updateGoldBeansConsumerDetails(GoldBeansConsumerDetails goldBeansConsumerDetails);

	/**
	 * 生成主键
	 * @return
	 */
	public String generatePK();
	
		/**
	 * 为GoldBeansConsumerDetails对象设置默认值
	 * @param obj
	 */
	public void fillDefaultValues(GoldBeansConsumerDetails obj);
		
}
