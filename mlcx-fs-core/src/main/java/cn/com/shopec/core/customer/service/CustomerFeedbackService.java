package cn.com.shopec.core.customer.service;

import java.util.List;

import cn.com.shopec.common.Operator;
import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.core.common.BaseService;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.customer.model.CustomerFeedback;

/**
 * 客户反馈表 服务接口类
 */
public interface CustomerFeedbackService extends BaseService {

	/**
	 * 根据查询条件，查询并返回CustomerFeedback的列表
	 * @param q 查询条件
	 * @return
	 */
	public List<CustomerFeedback> getCustomerFeedbackList(Query q);
	
	/**
	 * 根据查询条件，分页查询并返回CustomerFeedback的分页结果
	 * @param q 查询条件
	 * @return
	 */
	public PageFinder<CustomerFeedback> getCustomerFeedbackPagedList(Query q);
	
	/**
	 * 根据主键，查询并返回一个CustomerFeedback对象
	 * @param id 主键id
	 * @return
	 */
	public CustomerFeedback getCustomerFeedback(String id);

	/**
	 * 根据主键数组，查询并返回一组CustomerFeedback对象
	 * @param ids 主键数组，数组中的主键值应当无重复值，如有重复值时，则返回的列表长度可能小于传入的数组长度
	 * @return
	 */
	public List<CustomerFeedback> getCustomerFeedbackByIds(String[] ids);
	
	/**
	 * 根据主键，删除特定的CustomerFeedback记录
	 * @param id 主键id
	 * @param operator 操作人对象
	 * @return
	 */
	public ResultInfo<CustomerFeedback> delCustomerFeedback(String id, Operator operator);
	
	/**
	 * 新增一条CustomerFeedback记录，执行成功后传入对象及返回对象的主键属性值不为null
	 * @param customerFeedback 新增的CustomerFeedback数据（如果无特殊需求，新增对象的主键值请保留为null）
	 * @param operator 操作人对象
	 * @return
	 */
	public ResultInfo<CustomerFeedback> addCustomerFeedback(CustomerFeedback customerFeedback, Operator operator);
	
	/**
	 * 根据主键，更新一条CustomerFeedback记录
	 * @param customerFeedback 更新的CustomerFeedback数据，且其主键不能为空
	 * @param operator 操作人对象
	 * @return
	 */
	public ResultInfo<CustomerFeedback> updateCustomerFeedback(CustomerFeedback customerFeedback, Operator operator);

	/**
	 * 生成主键
	 * @return
	 */
	public String generatePK();
	
		/**
	 * 为CustomerFeedback对象设置默认值
	 * @param obj
	 */
	public void fillDefaultValues(CustomerFeedback obj);

		
	public PageFinder<CustomerFeedback> getCustomerFeedbackPagedLists(Query q);

	/**
	 * 得到客服反馈列表未回复数量
	 * @return
	 */
	public Long countCustomerFeedbackNum();
		
}
