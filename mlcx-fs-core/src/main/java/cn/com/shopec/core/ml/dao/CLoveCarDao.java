package cn.com.shopec.core.ml.dao;

import java.util.List;

import cn.com.shopec.core.common.BaseDao;
import cn.com.shopec.core.ml.model.CLoveCar;

/**
 * CLoveCar 数据库处理类
 */
public interface CLoveCarDao extends BaseDao<CLoveCar,String> {
	//爱车信息
	List<CLoveCar> getLaveCar(String memberNo);
}
