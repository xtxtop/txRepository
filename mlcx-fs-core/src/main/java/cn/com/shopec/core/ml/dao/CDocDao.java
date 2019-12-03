package cn.com.shopec.core.ml.dao;

import cn.com.shopec.core.common.BaseDao;
import cn.com.shopec.core.ml.model.CDoc;

/**
 * 上传附件表 数据库处理类
 */
public interface CDocDao extends BaseDao<CDoc,String> {
	//获取图片编号
	String getCDocNo(String biz_id);
	
}
