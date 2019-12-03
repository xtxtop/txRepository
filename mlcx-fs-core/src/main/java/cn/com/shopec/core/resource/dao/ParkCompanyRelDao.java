package cn.com.shopec.core.resource.dao;

import org.apache.ibatis.annotations.Param;

import cn.com.shopec.core.common.BaseDao;
import cn.com.shopec.core.resource.model.ParkCompanyRel;

/**
 * ParkCompanyRel 数据库处理类
 */
public interface ParkCompanyRelDao extends BaseDao<ParkCompanyRel,String> {

	int deletePCR(@Param("parkId")String id,@Param("companyId") String companyId);
	
}
