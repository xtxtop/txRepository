package cn.com.shopec.core.system.service;

import java.util.List;

import cn.com.shopec.common.Operator;
import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.core.common.BaseService;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.system.model.SmsTemplate;

/**
 * 商城短信模板表 服务接口类
 */
public interface SmsTemplateService extends BaseService {

	/**
	 * 根据查询条件，查询并返回SmsTemplate的列表
	 * @param q 查询条件
	 * @return
	 */
	public List<SmsTemplate> getSmsTemplateList(Query q);
	
	/**
	 * 根据查询条件，分页查询并返回SmsTemplate的分页结果
	 * @param q 查询条件
	 * @return
	 */
	public PageFinder<SmsTemplate> getSmsTemplatePagedList(Query q);
	
	/**
	 * 根据主键，查询并返回一个SmsTemplate对象
	 * @param id 主键id
	 * @return
	 */
	public SmsTemplate getSmsTemplate(String id);

	/**
	 * 根据主键数组，查询并返回一组SmsTemplate对象
	 * @param ids 主键数组，数组中的主键值应当无重复值，如有重复值时，则返回的列表长度可能小于传入的数组长度
	 * @return
	 */
	public List<SmsTemplate> getSmsTemplateByIds(String[] ids);
	
	/**
	 * 根据主键，删除特定的SmsTemplate记录
	 * @param id 主键id
	 * @param operator 操作人对象
	 * @return
	 */
	public ResultInfo<SmsTemplate> delSmsTemplate(String id, Operator operator);
	
	/**
	 * 新增一条SmsTemplate记录，执行成功后传入对象及返回对象的主键属性值不为null
	 * @param smsTemplate 新增的SmsTemplate数据（如果无特殊需求，新增对象的主键值请保留为null）
	 * @param operator 操作人对象
	 * @return
	 */
	public ResultInfo<SmsTemplate> addSmsTemplate(SmsTemplate smsTemplate, Operator operator);
	
	/**
	 * 根据主键，更新一条SmsTemplate记录
	 * @param smsTemplate 更新的SmsTemplate数据，且其主键不能为空
	 * @param operator 操作人对象
	 * @return
	 */
	public ResultInfo<SmsTemplate> updateSmsTemplate(SmsTemplate smsTemplate, Operator operator);

	/**
	 * 生成主键
	 * @return
	 */
	public String generatePK();
	
		/**
	 * 为SmsTemplate对象设置默认值
	 * @param obj
	 */
	public void fillDefaultValues(SmsTemplate obj);
/**
 * @author zln   根据短信模板类型获取短信模板内容
 * */
		public SmsTemplate getSmsTemplateByTemplateType(Integer templateType);
		
}
