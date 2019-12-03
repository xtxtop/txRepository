package cn.com.shopec.core.member.dao;

import cn.com.shopec.core.common.BaseDao;
import cn.com.shopec.core.member.model.MemberZhimaScore;

/**
 * 会员积分规则表 数据库处理类
 */
public interface MemberZhimaScoreDao extends BaseDao<MemberZhimaScore,String> {
	/**
	 * 修改对象
	 * @param obj
	 */
	public int updateForBusiness(MemberZhimaScore obj);
}
