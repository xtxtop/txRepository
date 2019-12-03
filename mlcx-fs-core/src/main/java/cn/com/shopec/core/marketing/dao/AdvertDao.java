package cn.com.shopec.core.marketing.dao;

import java.util.List;

import cn.com.shopec.core.common.BaseDao;
import cn.com.shopec.core.marketing.model.Advert;

/**
 * 广告表 数据库处理类
 */
public interface AdvertDao extends BaseDao<Advert,String> {
	
	
/**
 * 方法说明:
 */
List<Advert> getLatestAdverts();
	
}
