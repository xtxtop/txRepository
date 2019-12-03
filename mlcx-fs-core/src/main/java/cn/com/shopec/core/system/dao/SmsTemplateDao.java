package cn.com.shopec.core.system.dao;

import cn.com.shopec.core.common.BaseDao;
import cn.com.shopec.core.system.model.SmsTemplate;

/**
 * 商城短信模板表 数据库处理类
 */
public interface SmsTemplateDao extends BaseDao<SmsTemplate,String> {
/**
 * 根据短信模板类型获取短信模板内容
 * */
	SmsTemplate getSmsTemplateByTemplateType(Integer templateType);
	
}
