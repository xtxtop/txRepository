package cn.com.shopec.core.member.dao;

import cn.com.shopec.core.common.BaseDao;
import cn.com.shopec.core.member.model.MemberPointsRecord;

/**
 * 会员积分记录表 数据库处理类
 */
public interface MemberPointsRecordDao extends BaseDao<MemberPointsRecord,String> {
/**
 * 根据会员编号查询会员当前的积分数
 * */
	int getPointsByMemberNo(String memberNo);
	
}
