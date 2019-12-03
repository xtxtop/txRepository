package cn.com.shopec.core.ml.dao;

import java.util.List;

import cn.com.shopec.core.common.BaseDao;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.ml.model.ChargingGunInfo;
import cn.com.shopec.core.ml.vo.ChargingGunInfoVo;

/**
 * 充电枪 数据库处理类
 */
public interface ChargingGunInfoDao extends BaseDao<ChargingGunInfo,String> {
	List<ChargingGunInfoVo> getGunList(Query q);//VO 枪信息
	long getGunListCount(Query q);//VO 枪总数
	ChargingGunInfoVo getGunListNo(String id);//VO 枪详情
}
